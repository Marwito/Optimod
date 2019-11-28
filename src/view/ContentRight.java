package view;

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
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * The right part of the window.
 * Contains the buttons, and the DDIContainer
 * @author jerome
 */
public class ContentRight extends JPanel implements StateVisitor{

    private static final long serialVersionUID = -1118499055683094318L;

    private Button loadMap          = new Button(LOAD_MAP_NAME);
    private Button loadDeliveries   = new Button(LOAD_DELIVERIES_NAME);
    private Button compute          = new Button(COMPUTE_NAME);
    private Button roadmap          = new Button(ROADMAP_NAME);
    
    private ImageButton addButton   = new ImageButton(ADD_BUTTON_NAME);
    private ImageButton undoButton  = new ImageButton(UNDO_BUTTON_NAME);
    private ImageButton redoButton  = new ImageButton(REDO_BUTTON_NAME);
    private ImageButton upButton    = new ImageButton(UP_BUTTON_NAME);
    private ImageButton downButton  = new ImageButton(DOWN_BUTTON_NAME);
    
    private JLabel address          = new JLabel("");
    private JLabel times            = new JLabel(""); 
    private JLabel desiredTimeOut   = new JLabel("Timeout (s):");
    
    private JFormattedTextField timeout = null;

    private DetailledDeliveryInformationContainer ddiContainer = new DetailledDeliveryInformationContainer(WIDTH);
    private JScrollPane ddiScrollPane;

    private static final int MIDDLE_OFFSET_V            = 10;
    private static final Dimension DDI_SCROLLPANE_DIM   = new Dimension(350, 0);
    private static final int TIMEOUT_WIDTH              = 100;
    private static final int IMG_BUTTON_HEIGHT          = 40;
    
    private static final int WIDTH                      = 260;
    private static final int HEIGHT                     = 2000;
    
    public static final String LOAD_MAP_NAME            = "load map";
    public static final String LOAD_DELIVERIES_NAME     = "load deliveries";
    public static final String COMPUTE_NAME             = "compute";
    public static final String ADD_BUTTON_NAME          = "add";
    public static final String UNDO_BUTTON_NAME         = "undo";
    public static final String REDO_BUTTON_NAME         = "redo";
    public static final String ROADMAP_NAME             = "generate roadmap";
    public static final String UP_BUTTON_NAME           = "up";
    public static final String DOWN_BUTTON_NAME         = "down";

    /**
     * add the listener and the components and set the scrollPane
     * 
     * @param w The window, to add the ActionListener from the buttons
     */
    public ContentRight(Window w) {
        this.loadMap.addActionListener(w);
        this.loadDeliveries.addActionListener(w);
        this.compute.addActionListener(w);
        this.addButton.addMouseListener(w);
        this.roadmap.addActionListener(w);
        this.undoButton.addMouseListener(w);
        this.redoButton.addMouseListener(w);
        this.upButton.addMouseListener(w);
        this.downButton.addMouseListener(w);
        this.ddiContainer.addListener(w);

        this.ddiScrollPane = new JScrollPane(ddiContainer);
        this.ddiScrollPane.setPreferredSize(DDI_SCROLLPANE_DIM);
        this.ddiScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.ddiScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(0);
        timeout = new JFormattedTextField(format);
        timeout.setPreferredSize(new Dimension(TIMEOUT_WIDTH, this.compute.getHeight()));
        timeout.setText("010");
        this.addComponents();

        this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
    }

    /**
     * Transmission of a message to DDIContainer
     * Change the Deliveries to be displayed on the textInformations
     * 
     * @param request The DeliveryRequest to be displayed
     * @param listener the listener for the delete and modify button, specific to each
     * DDI
     */
    public void setDeliveriesRequest(List<PointDrawer> request, Window listener) {
        this.ddiContainer.removeAll();
        
        if(request.isEmpty()) {
            this.ddiScrollPane.revalidate();
            return;
        }
            
        Iterator<PointDrawer> it = request.iterator();
        
        while(it.hasNext()) {
            PointDrawer node = it.next();
            if(node instanceof DeliveryDrawer) {
                this.ddiContainer.add(((DeliveryDrawer)node).getDetailledDeliveryInformation());
            }
        }
        this.ddiContainer.addListener(listener);
        this.ddiScrollPane.revalidate();
        
    }
    
    /**
     * Add the components to the panel
     * 
     */
    private void addComponents() {
        this.removeAll();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1,2));
        p1.setMaximumSize(new Dimension(DDI_SCROLLPANE_DIM.width, this.loadMap.getHeight()));
        p1.add(this.address);
        p1.add(this.loadMap);
        this.loadMap.setAlignmentX(1);
        this.add(p1);
        
        this.add(Box.createRigidArea(new Dimension(0, MIDDLE_OFFSET_V)));   
        
        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(1,2));
        p2.setMaximumSize(new Dimension(DDI_SCROLLPANE_DIM.width, this.loadMap.getHeight()));
        p2.add(this.times);
        p2.add(this.loadDeliveries);
        this.add(p2);
        
        this.add(Box.createRigidArea(new Dimension(0, MIDDLE_OFFSET_V)));        
        
        this.add(this.ddiScrollPane);
        
        JPanel imgButtonPanel = new JPanel();
        imgButtonPanel.setMaximumSize(new Dimension(DDI_SCROLLPANE_DIM.width, IMG_BUTTON_HEIGHT));
        imgButtonPanel.setMinimumSize(new Dimension(DDI_SCROLLPANE_DIM.width, IMG_BUTTON_HEIGHT));
        imgButtonPanel.add(this.addButton);
        imgButtonPanel.add(this.undoButton);
        imgButtonPanel.add(this.redoButton);
        imgButtonPanel.add(this.upButton);
        imgButtonPanel.add(this.downButton);
        this.add(imgButtonPanel);
        
        this.add(Box.createRigidArea(new Dimension(0, MIDDLE_OFFSET_V)));
        
        JPanel p3 = new JPanel();
        p3.setLayout(new GridBagLayout());
        p3.setMaximumSize(new Dimension(DDI_SCROLLPANE_DIM.width, 3*this.loadMap.getHeight()+ MIDDLE_OFFSET_V));
        p3.setMinimumSize(new Dimension(DDI_SCROLLPANE_DIM.width, 3*this.loadMap.getHeight()+ MIDDLE_OFFSET_V));
        
        java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
        c.gridx=0; c.gridy=0; c.weighty=1; c.weightx=1;
        c.fill = java.awt.GridBagConstraints.BOTH;
        p3.add(this.desiredTimeOut, c);
        
        java.awt.GridBagConstraints c2 = new java.awt.GridBagConstraints();
        c2.gridx=1; c2.gridy=0; c2.weighty=1; c2.weightx=1;
        c2.fill = java.awt.GridBagConstraints.BOTH;
        p3.add(this.timeout, c2);
        
        java.awt.GridBagConstraints c3 = new java.awt.GridBagConstraints();
        c3.gridx=2; c3.gridy=0; c3.weighty=1; c3.weightx=2;
        c3.fill = java.awt.GridBagConstraints.BOTH;
        p3.add(this.compute, c3);
        
        java.awt.GridBagConstraints c4 = new java.awt.GridBagConstraints();
        c4.gridx=0; c4.gridy=1; c4.weighty=1; c4.weightx=4;
        c4.fill = java.awt.GridBagConstraints.BOTH;
        p3.add(Box.createRigidArea(new Dimension(0, MIDDLE_OFFSET_V)), c4);
        
        java.awt.GridBagConstraints c5 = new java.awt.GridBagConstraints();
        c5.gridx=2; c5.gridy=2; c5.weighty=1; c5.weightx=2;
        c5.fill = java.awt.GridBagConstraints.BOTH;
        p3.add(this.roadmap, c5);
        
        java.awt.GridBagConstraints c6 = new java.awt.GridBagConstraints();
        c6.gridx=0; c6.gridy=3; c6.weighty=1; c6.weightx=4;
        c6.fill = java.awt.GridBagConstraints.BOTH;
        p3.add(Box.createRigidArea(new Dimension(0, MIDDLE_OFFSET_V)), c6);
        
        this.add(p3);
    }

/*****************GETTER*************************************************/
    
    public JLabel getAdress() {
        return this.address;
    }
    
    public JLabel getTimes() {
        return this.times;
    }
    
    public int getTimeout() {
    	try {	
            return Integer.parseInt(this.timeout.getText())* 1000;
    	}
    	catch(Exception e) {
    	}
    	return 10 * 1000;
    }
  
    
/****************************STATE VISITOR*************************************/
    
    @Override
    public void visit(InitState state) {
        this.loadDeliveries.setEnabled(false);
        this.compute.setEnabled(false);
        this.loadMap.setEnabled(true);
        this.addButton.setEnabled(false);
        this.roadmap.setEnabled(false);
        this.undoButton.setEnabled(false);
        this.redoButton.setEnabled(false);
        this.upButton.setEnabled(false);
        this.downButton.setEnabled(false);
        this.timeout.setEnabled(false);
    }

    @Override
    public void visit(MapLoadedState state) {
        this.loadDeliveries.setEnabled(true);
        this.compute.setEnabled(false);
        this.loadMap.setEnabled(true);
        this.addButton.setEnabled(false);
        this.roadmap.setEnabled(false);
        this.undoButton.setEnabled(false);
        this.redoButton.setEnabled(false);
        this.upButton.setEnabled(false);
        this.downButton.setEnabled(false);
        this.ddiContainer.setEnabled(false);
        this.timeout.setEnabled(false);
    }

    @Override
    public void visit(DeliveriesRequestLoadedState state) {
        this.loadDeliveries.setEnabled(true);
        this.compute.setEnabled(true);
        this.loadMap.setEnabled(true);
        this.addButton.setEnabled(false);
        this.roadmap.setEnabled(false);
        this.undoButton.setEnabled(false);
        this.redoButton.setEnabled(false);
        this.upButton.setEnabled(false);
        this.downButton.setEnabled(false);
        this.ddiContainer.setEnabled(false);
        this.timeout.setEnabled(true);
    }

    @Override
    public void visit(DeliveriesJourneyComputedState state) {    		
        this.loadDeliveries.setEnabled(true);
        this.compute.setEnabled(false);
        this.loadMap.setEnabled(true);
        this.addButton.setEnabled(true);
        this.roadmap.setEnabled(true);
        this.undoButton.setEnabled(true);
        this.redoButton.setEnabled(true);
        this.upButton.setEnabled(false);
        this.downButton.setEnabled(false);
        this.ddiContainer.setEnabled(true);
        this.timeout.setEnabled(false);
    }

    @Override
    public void visit(AddDeliveryModeState state) {
        this.loadDeliveries.setEnabled(false);
        this.compute.setEnabled(false);
        this.loadMap.setEnabled(false);
        this.addButton.setEnabled(false);
        this.roadmap.setEnabled(false);
        this.undoButton.setEnabled(false);
        this.redoButton.setEnabled(false);
        this.upButton.setEnabled(false);
        this.downButton.setEnabled(false);
        this.ddiContainer.setEnabled(false);
        this.timeout.setEnabled(false);
    }

    @Override
    public void visit(AddDeliveryModeStepDeliveryLocationState state) {
    	this.loadDeliveries.setEnabled(false);
        this.compute.setEnabled(false);
        this.loadMap.setEnabled(false);
        this.addButton.setEnabled(false);
        this.roadmap.setEnabled(false);
        this.undoButton.setEnabled(false);
        this.redoButton.setEnabled(false);
        this.upButton.setEnabled(false);
        this.downButton.setEnabled(false);
        this.ddiContainer.setEnabled(false);
        this.timeout.setEnabled(false);
    }

    @Override
    public void visit(AddDeliveryModeStepDeliveryInformationState state) {
    	this.loadDeliveries.setEnabled(false);
        this.compute.setEnabled(false);
        this.loadMap.setEnabled(false);
        this.addButton.setEnabled(false);
        this.roadmap.setEnabled(false);
        this.undoButton.setEnabled(false);
        this.redoButton.setEnabled(false);
        this.upButton.setEnabled(false);
        this.downButton.setEnabled(false);
        this.ddiContainer.setEnabled(false);
        this.timeout.setEnabled(false);
    }
    
    @Override
    public void visit(DDISelectedState state) {
    	this.loadDeliveries.setEnabled(true);
        this.compute.setEnabled(false);
        this.loadMap.setEnabled(true);
        this.addButton.setEnabled(true);
        this.roadmap.setEnabled(true);
        this.undoButton.setEnabled(true);
        this.redoButton.setEnabled(true);
        this.upButton.setEnabled(true);
        this.downButton.setEnabled(true);
        this.ddiContainer.setEnabled(true);
        this.timeout.setEnabled(false);
    }

    @Override
    public void visit(ModifyState state) {
        this.loadDeliveries.setEnabled(false);
        this.compute.setEnabled(false);
        this.loadMap.setEnabled(false);
        this.addButton.setEnabled(false);
        this.roadmap.setEnabled(false);
        this.undoButton.setEnabled(false);
        this.redoButton.setEnabled(false);
        this.upButton.setEnabled(false);
        this.downButton.setEnabled(false);
        this.ddiContainer.setEnabled(false);
        this.timeout.setEnabled(false);
    }
    
}
