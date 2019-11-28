package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import controller.state.AddDeliveryModeState;
import controller.state.AddDeliveryModeStepDeliveryInformationState;
import controller.state.AddDeliveryModeStepDeliveryLocationState;
import controller.state.DDISelectedState;
import controller.state.DeliveriesJourneyComputedState;
import controller.state.DeliveriesRequestLoadedState;
import controller.state.InitState;
import controller.state.MapLoadedState;
import controller.state.ModifyState;
import controller.state.StateVisitor;
import model.delivery.DeliveryJourneyI;
import model.graph.EdgeI;
import model.graph.NodeI;
import model.map.MapI;

/**
 * The upperLeft part of the window, display the map, the deliveries and the path
 * It's the entry point of models references
 * 
 * @author jerome
 */
public class DisplayedMap extends JPanel implements ComponentListener, MouseWheelListener, MouseMotionListener, MouseListener, StateVisitor {
    
    private Window uniqueListener = null;
    
    private static final long serialVersionUID = 5040474471820274008L;
	
    private MapI map;
    private List<PointDrawer> points = new LinkedList<>();
    private DeliveryJourneyI journey;
    private float scaleX;
    private float scaleY;
    private float factor = 1f;
    private int scaledHeight;
    private int scaledWidth;
    
    // used to degrade the color
    private int numberOfEdges = 0;
    private ColorCurve[] primaries;
    
    //used to curve crossing edges
    private Set<EdgeI> drawn = new HashSet<>();
    
    private static final int DELIVERY_NODE_SIZE = 15;
    private static final int SPACE_FOR_LEGEND = 25;
    private static final String START = "START";
    private static final String END = "END";
    private static final int START_OFFSET = 20;
    private static final int END_OFFSET = 80;
    private static final int VERTICAL_OFFSET = 5;
    
    private static final Color BACKGROUND_COLOR = Color.lightGray;
    private static final Color EDGE_COLOR = Color.gray;
    private static final Color BORDER_COLOR = Color.black;
    private static final Color TEXT_COLOR = Color.black;
    
    private static final int EDGE_ARROW = 0x00000001;
    private static final int EDGE_BOLD = 0x00000010;
    private static final int EDGE_CURVE = 0x00001000;
    
    private enum Mode {
        NORMAL,
        ADD_MODE_ENABLED,
        END_NODES_CHOSEN, 
        LOCATION_CHOSEN
    }
    
    private Mode mode = Mode.NORMAL;
  
    /**
     * Constructor with a default map, call setMap in the method
     * 
     * @param w the Window as a listener
     * @param map the Map as defined in the model
     * @param scaledWidth the width as defined as a Property of Map, (the size of the Map, not the DisplayedMap)
     * @param scaledHeight the height as defined as a Property of Map, (the size of the Map, not the DisplayedMap)
     */
    public DisplayedMap(Window w, int scaledWidth, int scaledHeight, MapI map) {
    	this(w);
        this.setMap(map, scaledWidth, scaledHeight);
    }
    
    /**
     * Default constructor
     * set everything to null
     * @param w the Window as a listener
     */
    public DisplayedMap(Window w) {
    	super();
        this.uniqueListener = w;
        this.addMouseWheelListener(this);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.map = null;
    }

/*********************INFORMATION TRANSMISSION FROM UPPER LEVEL*****************************************/
    
    /**
     * Change the map reference, revalidate the dimensions, delete the references to
     * the DeliveryRequest and to the Journey. Repaint.
     * @param map the Map as defined in the model
     * @param scaledWidth the width as defined as a Property of Map, (the size of the Map, not the DisplayedMap)
     * @param scaledHeight the height as defined as a Property of Map, (the size of the Map, not the DisplayedMap)
     */
    public final void setMap(MapI map, int scaledWidth, int scaledHeight) {
    	this.scaledWidth = scaledWidth;
        this.scaledHeight = scaledHeight;
        this.setScale();
        this.map = map;
        Iterator<NodeI> it = this.map.nodeIterator();
        this.points = new LinkedList<>();
        while(it.hasNext()) {
        	NodeDrawer drawer = new NodeDrawer(it.next());
        	this.points.add(drawer);
        }
        
        this.journey = null;
        repaint();
    }
    
    /**
     * Change the Deliveries to be displayed, delete the reference to the Journey, repaint
     * 
     * @param deliveries the DeliveryRequest to be displayed
     */  
    public void setDeliveriesRequest(List<PointDrawer> deliveries) {
    	this.setMap(this.map, this.scaledWidth, this.scaledHeight);
        this.points.addAll(deliveries);
        this.journey = null;
        
        Iterator<PointDrawer> it = deliveries.iterator();
        while(it.hasNext()) {
            it.next().setContainer(this);
        }
        
        repaint();
    }
    
    /**
     * Add the information of the path of the deliveryJourney and repaint
     * @param journey The journey after computing
     */
    public void setJourney(DeliveryJourneyI journey) {
        this.journey = journey;
        repaint();
    }

/*********************SIZE PROBLEMS RESOLUTION***********************************************************/
    
    /**
     * change the scale according to the real size of the map and a zoom factor
     * so the map fits on the screen or the desired zone is zoomed in (second part not implemented).
     */
    public final void setScale() {
        this.scaleX = factor*((float) this.getWidth()) / this.scaledWidth;
        this.scaleY = factor*((float) this.getHeight()) / this.scaledHeight;
    }
    
    /**
     * change the scale and repaint
     */
    public void setRelativeSize() {
        setScale();
        repaint();
    }

/**********************PAINT*****************************************************************************/
    
    /**
     * Method called on repaint. draw the border, call the method to draw specifics parts
     * (nodes, edges, deliveries, ...) if the related reference to the model is linked (not null)
     */
    @Override
    public void paintComponent(Graphics g) {
        
        Graphics2D g2D = (Graphics2D) g;
        
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        this.setBackgroundColor(g);
        g.setColor(BORDER_COLOR);
        g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
        
        if(this.map != null) {
	    this.drawEdges(g2D);
        }

        if(this.journey != null) {
            this.drawPath(g2D);
            this.drawLegend(g2D);
        }   
        
        if(this.map != null) {
	    this.drawNodes(g2D);
        }

    }
    
    /**
     * Paint a background rectangle on the panel
     * @param g transmission of the graphics object
     */
    private void setBackgroundColor(Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        int width = this.getWidth();
        int height = this.getHeight();
        g.fillRect(0, 0, width, height);   
    }
    
    /**
     * Draw a legend line under the map to explain which way the journey goes
     * @param g2D transmission of the graphics object
     */
    private void drawLegend(Graphics2D g2D) {
        g2D.setStroke ( new BasicStroke ( 3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND ) );
        this.numberOfEdges = 30;
        ColorCurve[] colors = {new ColorCurve(Color.red, this.numberOfEdges),new ColorCurve(Color.green, this.numberOfEdges),new ColorCurve(Color.blue, this.numberOfEdges)};
        this.primaries = colors;
        Point begin = new Point((int) (2.5*SPACE_FOR_LEGEND), this.getHeight() - SPACE_FOR_LEGEND/2);
        Point end = new Point((int) (this.getWidth() - 2.5*SPACE_FOR_LEGEND), this.getHeight() - SPACE_FOR_LEGEND/2);
        int length = this.getWidth() - 2* SPACE_FOR_LEGEND;
        int interval = length / this.numberOfEdges;
        
        for(Point tps = new Point(begin.x+interval, begin.y); tps.x < end.x - 2*interval; begin.translate(interval, 0), tps.translate(interval, 0)) {
            this.degradeColor(g2D, begin, tps);
            g2D.drawLine(begin.x, begin.y, tps.x, tps.y);
        }
        
        g2D.setColor(TEXT_COLOR);
        g2D.drawString(START, START_OFFSET, this.getHeight() - SPACE_FOR_LEGEND/2+VERTICAL_OFFSET);
        g2D.drawString(END, this.getWidth() - END_OFFSET, this.getHeight() - SPACE_FOR_LEGEND/2+VERTICAL_OFFSET);
    }
    
    /**
     * Draw the nodes contained in the map
     * @param g transmission of the graphics object
     */
    private void drawNodes(Graphics g) {
    	Iterator<PointDrawer> itPoints = this.points.iterator();
        
        while(itPoints.hasNext()) {
            PointDrawer point = itPoints.next();
            
            if(point instanceof NodeDrawer) {
            	NodeDrawer nDrawer = ((NodeDrawer) point);
            	int x = (int) (nDrawer.getX()*this.scaleX);
                int y = (int) (nDrawer.getY()*this.scaleY);
            	nDrawer.drawPoint(g, x, y);
            }
            else {
            
	            Iterator<NodeI> itNode = this.map.nodeIterator();
	            
	            boolean found = false;
	            while(itNode.hasNext() && !found) {
	                NodeI node = itNode.next();
	                
	                if(node.getId() == point.getId()) {
	                    int x = (int) (node.getX()*this.scaleX);
	                    int y = (int) (node.getY()*this.scaleY);
	                    
	                    point.drawPoint(g, x, y);
	                    found = true;
	                }
	            }
            
            }
            
        }
    }
    
    /**
     * Draw the edges contained in the map
     * @param g transmission of the graphics object
     */
    private void drawEdges(Graphics2D g) {
        g.setColor(EDGE_COLOR);
        this.drawGeneralEdge(g, this.map.edgeIterator());
     
    }
    
    /**
     * Draw the edges contained in the journey (path)
     * @param g transmission of the graphics object
     */
    private void drawPath(Graphics2D g) {
        
        this.numberOfEdges = 0;
        Iterator<EdgeI> it = this.journey.edgeIterator();
        while(it.hasNext()) {
            it.next();
            this.numberOfEdges++;
        }
        
        ColorCurve[] tps = {new ColorCurve(Color.red, this.numberOfEdges),new ColorCurve(Color.green, this.numberOfEdges),new ColorCurve(Color.blue, this.numberOfEdges)};
        this.primaries = tps;
        
        this.drawGeneralEdge(g, this.journey.edgeIterator(), EDGE_BOLD | EDGE_ARROW | EDGE_CURVE);
    }
    
    /**
     * Draw any types of edges got by the iterator it. 
     * Algorithm :
     * 		for each edge :
     * 			for each node (until begin and end are found):
     * 				if the node is either the begin or the end, keep it
     * 			end for each node
     * 			draw a line between the two nodes
     * 		end for each edge
     * @param g transmission of the graphics object
     * @param it iterator to get the edges
     */
    private void drawGeneralEdge(Graphics2D g, Iterator<EdgeI> it) {
    	this.drawGeneralEdge(g, it, 0);
    }
    
    /**
     * Draw any types of edges got by the iterator it. 
     * Algorithm :
     * 		for each edge :
     * 			for each node (until begin and end are found):
     * 				if the node is either the begin or the end, keep it
     * 			end for each node
     * 			draw a line between the two nodes
     * 		end for each edge
     * @param g transmission of the graphics object
     * @param it iterator to get the edges
     * @param flags options to draw the edges
     */
    private void drawGeneralEdge(Graphics2D g, Iterator<EdgeI> it, int flags) {
        
        while(it.hasNext()) {
            
            EdgeI edge = it.next();
            NodeI nodeBegin = edge.getStartNode();
            NodeI nodeEnd = edge.getEndNode();
            int xBegin = -1;
            int yBegin = -1;
            int xEnd = -1;
            int yEnd = -1;
            xBegin = (int) (nodeBegin.getX()*this.scaleX);
            yBegin = (int) (nodeBegin.getY()*this.scaleY);
            xEnd = (int) (nodeEnd.getX()*this.scaleX);
            yEnd = (int) (nodeEnd.getY()*this.scaleY);
            
            if((flags & EDGE_ARROW) == EDGE_ARROW) {
                degradeColor(g, new Point(xBegin, yBegin), new Point(xEnd, yEnd));
            }
            
            if((flags & EDGE_BOLD) == EDGE_BOLD) {	
                g.setStroke ( new BasicStroke ( 3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND ) );
            }
            
            this.drawUnitEdge(g, xBegin, yBegin, xEnd, yEnd, edge, flags);          
            
        }
        
        this.drawn = new HashSet<>();
    }
    
    /**
     * Draw a single edge, function of its position, of whether or not it is already drawn
     * and of the flags passed
     * @param g transmission of the graphics object
     * @param xBegin
     * @param yBegin
     * @param xEnd
     * @param yEnd
     * @param edge
     * @param flags 
     */
    private void drawUnitEdge(Graphics2D g, int xBegin, int yBegin, int xEnd, int yEnd, EdgeI edge, int flags) {
        
        if((flags & EDGE_CURVE) == EDGE_CURVE) {
            Iterator<EdgeI> it = this.drawn.iterator();
            boolean contains = false;
            while(it.hasNext()) {
                EdgeI e2 = it.next();
                if(e2.getEndNode().getId() == edge.getStartNode().getId() && 
                        e2.getStartNode().getId() == edge.getEndNode().getId() ) {
                    contains = true;
                    break;
                }
            }
            if(contains) {
                g.drawLine(xBegin+2, yBegin+2, xEnd+2, yEnd+2);
            }
            else {
                g.drawLine(xBegin-2, yBegin-2, xEnd-2, yEnd-2);
                this.drawn.add(edge);
            }         
            return;
        }
        g.drawLine(xBegin, yBegin, xEnd, yEnd);
    }
    
    /**
     * Set a gradient of color between two points, depending on what was the last gradient
     * @param g transmission of the graphics object
     * @param begin Point to begin
     * @param end Point to end
     */
    private void degradeColor(Graphics g, Point begin, Point end) {
        Graphics2D g2 = (Graphics2D) g;
        Color first = new Color(primaries[0].getIntensity(), primaries[1].getIntensity(), primaries[2].getIntensity());
        primaries[0].next();
        primaries[1].next();
        primaries[2].next();
        Color second = new Color(primaries[0].getIntensity(), primaries[1].getIntensity(), primaries[2].getIntensity());
        g2.setPaint(new GradientPaint(begin.x, begin.y, first, end.x, end.y, second));
        
    }
    
    /**
     * Test if the mouse is over the node
     * @param e the event whithin you find the mouse
     * @param node
     * @return true if the mouse hover the node
     */
    private boolean mouseOnNode(MouseEvent e, NodeI node) {
        Point mouse = e.getPoint();
        mouse.translate(-DELIVERY_NODE_SIZE/2, -DELIVERY_NODE_SIZE/2);
        Rectangle bounds = new Rectangle(mouse, new Dimension(DELIVERY_NODE_SIZE, DELIVERY_NODE_SIZE));
        
        return bounds.contains(new Point((int) (node.getX()*this.scaleX), (int) (node.getY()*this.scaleY)));
        
    }
    
    /**
     * Function called when the add delivery procedure is on, in order to select a delivery
     * @param e mouse event
     */
    private void onAddModeEnabledCliked(MouseEvent e) {
        
        Iterator<NodeI> it = this.map.nodeIterator();
        while(it.hasNext()) {
            NodeI node = it.next();
            
            if(this.mouseOnNode(e, node)) {
                
                Iterator<PointDrawer> itDeliveries = points.iterator();
                while(itDeliveries.hasNext()) {
                    PointDrawer delivery = itDeliveries.next();
                    if((delivery instanceof DeliveryDrawer || delivery instanceof StoreNodeDrawer) && delivery.getId() == node.getId()) {
                        this.uniqueListener.nodeSelected(node.getId());
                        delivery.setStayHovered(true);
                        return;
                    }
                }
            }
        }
        
    }
    
    /**
     * Function called when the add delivery procedure is on, in order to select a location
     * for the new delivery
     * @param e mouse event
     */
    private void onEndNodesChosenClicked(MouseEvent e) {
        Iterator<NodeI> it = this.map.nodeIterator();
        while(it.hasNext()) {
            NodeI node = it.next();
            
            if(this.mouseOnNode(e, node)) {
                
                Iterator<PointDrawer> itPoint = points.iterator();
                while(itPoint.hasNext()) {
                    PointDrawer point = itPoint.next();
                    if(point instanceof NodeDrawer && point.getId() == node.getId()) {
                        Iterator<PointDrawer> itDeliveries = points.iterator();
                        while(itDeliveries.hasNext()) {
                            PointDrawer delivery = itDeliveries.next();
                            if((delivery instanceof DeliveryDrawer || delivery instanceof StoreNodeDrawer) && delivery.getId() == point.getId()) {
                                return;
                            }
                        }
                        this.uniqueListener.locationSelected(node);
                        point.setStayHovered(true);
                        return;
                    }
                }
                return;
                   
            }
        }
    }

/***************************COMPONENT LISTENER************************************************/    
    
    @Override
    public void componentResized(ComponentEvent e) {
        
        setRelativeSize();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
        
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
            //this.factor += 0.1*e.getWheelRotation();
            //this.setRelativeSize();
    }

/***************************MOUSE MOTION LISTENER************************************************/    
    
    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
        if(this.map == null)
            return;
        
        Iterator<PointDrawer> itPoint = points.iterator();
        while(itPoint.hasNext()) {
            PointDrawer point = itPoint.next();
                point.setSelected(false);
        }
        
        Iterator<NodeI> it = this.map.nodeIterator();
        while(it.hasNext()) {
            NodeI node = it.next();
            if(this.mouseOnNode(e, node)) {
                Iterator<PointDrawer> itPoint2 = points.iterator();
                while(itPoint2.hasNext()) {
                    PointDrawer point = itPoint2.next();
                    if(point.getId() == node.getId()) {
                        point.setSelected(true);
                        
                    }
                }
                repaint();
                return;
            }
            
        }
        
    }

/**********************MOUSELISTENER************************/
    
    @Override
    public void mouseClicked(MouseEvent e) {
        switch(mode) {
            case ADD_MODE_ENABLED:
                this.onAddModeEnabledCliked(e);
                break;
            case END_NODES_CHOSEN:
                this.onEndNodesChosenClicked(e);
                break;
            default:
		break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

/**********************STATE VISITOR*******************************************************/
    
    @Override
    public void visit(InitState state) {
        mode = Mode.NORMAL;
    }

    @Override
    public void visit(MapLoadedState state) {
        mode = Mode.NORMAL;
    }

    @Override
    public void visit(DeliveriesRequestLoadedState state) {
        mode = Mode.NORMAL;
    }

    @Override
    public void visit(DeliveriesJourneyComputedState state) {
        mode = Mode.NORMAL;
        Iterator<PointDrawer> it = this.points.iterator();
        while(it.hasNext()) {
        	it.next().setStayHovered(false);
        }
    }

    @Override
    public void visit(AddDeliveryModeState state) {
        mode = Mode.ADD_MODE_ENABLED;
    }

    @Override
    public void visit(AddDeliveryModeStepDeliveryLocationState state) {
        mode = Mode.END_NODES_CHOSEN;
    }

    @Override
    public void visit(AddDeliveryModeStepDeliveryInformationState state) {
        mode = Mode.LOCATION_CHOSEN;
    }
    
    @Override
    public void visit(DDISelectedState state) {
        mode = Mode.NORMAL;
    }
    
    @Override
    public void visit(ModifyState state) {
        mode = Mode.LOCATION_CHOSEN;
    }
    
}
