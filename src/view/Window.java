package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import controller.state.AddDeliveryModeState;
import controller.state.AddDeliveryModeStepDeliveryInformationState;
import controller.state.AddDeliveryModeStepDeliveryLocationState;
import controller.Controller;
import controller.state.DDISelectedState;
import controller.state.DeliveriesJourneyComputedState;
import controller.state.DeliveriesRequestLoadedState;
import controller.state.InitState;
import controller.state.MapLoadedState;
import controller.state.ModifyState;
import controller.state.StateVisitor;
import model.delivery.DeliveryI;
import model.delivery.DeliveryJourneyI;
import model.delivery.DeliveryRequestI;
import model.graph.NodeI;
import model.map.MapI;

/**
 * class responsible of the display. It's really the window
 * of the application. Is the entry point of the Controller reference
 * 
 * @author jerome
 *
 */
public class Window extends JFrame implements ActionListener, MouseListener, WindowListener, StateVisitor{

    /**
     * 
     */
    private static final long serialVersionUID = -5540273607353580767L;


    private Controller ctrl;
    private JPanel contentLeft;
    private JPanel contentRight;

    private String curDir = null;

    private static final int BORDERS[] = {10, 10, 10, 10}; // top, left, bottom, right
    private static final int MIDDLE_OFFSET_H = 30;

    /**
     * Process general display operations : adding subPanels, setting Application's default font
     * @param ctrl a reference to the Controller
     * @param dp a DisplayMap to show by default
     */
    public Window(Controller ctrl, DisplayedMap dp) {

        super("Optimod");
        try {
            UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 12));
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.ctrl = ctrl;
            this.contentLeft = new ContentLeft(this);
            this.contentRight = new ContentRight(this);

            setSize(800, 600);

            JPanel contentWhole = new JPanel();
            contentWhole.setLayout(new BoxLayout(contentWhole, BoxLayout.X_AXIS));
            contentWhole.setBorder(BorderFactory.createEmptyBorder(BORDERS[0], BORDERS[1], BORDERS[2], BORDERS[3]));
            contentWhole.add(this.contentLeft);
            contentWhole.add(Box.createRigidArea(new Dimension(MIDDLE_OFFSET_H, 0)));
            contentWhole.add(this.contentRight);
            this.setContentPane(contentWhole);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Send a message to display on the Console
     * @param text the text to be displayed
     */
    public void writeMessage(String text) {
        ((ContentLeft) this.contentLeft).writeMessage(text);
    }
	
    /**
     * Transmission of a message to the DisplayedMap through ContentLeft. Change the Map to be displayed
     * @param map the Map as defined in the model
     * @param scaledWidth the width as defined as a Property of Map, (the size of the Map, not the DisplayedMap)
     * @param scaledHeight the height as defined as a Property of Map, (the size of the Map, not the DisplayedMap)
     */
    public void setMap(MapI map, int scaledWidth, int scaledHeight) {
        ((ContentLeft) this.contentLeft).setMap(map, scaledWidth, scaledHeight);
        this.setDeliveriesRequest(null);
    }

    /**
     * Transmission of a message to the DisplayedMap through ContentLeft 
     * and to DDIContainer through ContentRight. Change the Deliveries to be displayed, on the image
     * and on the textInformations
     * @param deliveries the DeliveryRequest to be displayed
     */
    public void setDeliveriesRequest(DeliveryRequestI deliveries) {
        
        List<PointDrawer> drawers = new LinkedList<>();
        
        if(deliveries != null) {
            Iterator<DeliveryI> it = deliveries.deliveryIterator();

            while(it.hasNext()) {
                drawers.add(new DeliveryDrawer(it.next(), this));
            }

            drawers.add(new StoreNodeDrawer(deliveries.getStoreAddress().getId(), ((ContentRight) this.contentRight).getAdress(), ((ContentRight) this.contentRight).getTimes()));
        }
        ((ContentLeft) this.contentLeft).setDeliveriesRequest(drawers);
        ((ContentRight) this.contentRight).setDeliveriesRequest(drawers, this);
    }
    
    /**
     * Transmission of a message to the DisplayedMap through ContentLeft 
     * and to DDIContainer through ContentRight. Change the Deliveries to be displayed, on the image
     * and on the textInformations, with those relatives to the computation
     * @param deliveries the DeliveryRequest to be displayed
     * @param dj where to find computation information
     */
    public void setDeliveriesRequest(DeliveryRequestI deliveries, DeliveryJourneyI dj) {
       List<PointDrawer> drawers = new LinkedList<>();
       if(deliveries != null) {
           Iterator<DeliveryI> it = deliveries.deliveryIterator();

           while(it.hasNext()) {
               drawers.add(new DeliveryDrawer(it.next(), this, dj));
           }

           drawers.add(new StoreNodeDrawer(deliveries.getStoreAddress().getId(), dj, ((ContentRight) this.contentRight).getAdress(), ((ContentRight) this.contentRight).getTimes()));
       }
       ((ContentLeft) this.contentLeft).setDeliveriesRequest(drawers);
       ((ContentRight) this.contentRight).setDeliveriesRequest(drawers, this);
    }
     
    
    
    /**
     * Transmission of a message to the DisplayedMap through ContentLeft 
     * and to DDIContainer through ContentRight. Add the informations of the deliveryJourney 
     * (path to the left, time to the right (not implemented yet))
     * @param journey the journey after computing
     */
    public void SetJourney(DeliveryJourneyI journey) {

        this.setDeliveriesRequest(journey.getRequest(), journey);
        ((ContentLeft) this.contentLeft).SetJourney(journey);
    }
    
    /**
     * Transmit a message to the controller, when the prior delivery is selected
     * @param deliveryId the id of the delivery
     */
    public void nodeSelected(int deliveryId) {
        this.ctrl.selectShortestPathToModify(deliveryId);
    }
    
    /**
     * Transmit a message to the controller, when a location is set for a new delivery
     * @param location 
     */
    public void locationSelected(NodeI location) {
        this.ctrl.selectNewDeliveryLocation(location);
    }
    
    
  	
    /**
     * Open a JFileChooser and return the file selected
     * @return String, absolute path of the file selected, null if canceled
     */
    public String getFileName() {

        JFileChooser chooser = new JFileChooser();
        if(curDir != null)
                chooser.setCurrentDirectory(new File(curDir));
        chooser.showOpenDialog(this);
        File file = chooser.getSelectedFile();
        String filename = null;
        if(file != null) {
                filename = file.getAbsolutePath();
        }
        curDir = filename;
        return filename;
    }
    
/**********************ACTION AND MOUSE LISTENER FOR TRANSMISSION TO CONTROLLER*************************/

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source instanceof Button) {
                Button button = (Button)source;
                switch(button.getText()) {

                case ContentRight.LOAD_MAP_NAME:
                    this.ctrl.loadXMLMap();
                    break;

                case ContentRight.LOAD_DELIVERIES_NAME:
                    this.ctrl.loadXMLDeliveryRequest();
                    break;

                case ContentRight.COMPUTE_NAME:
                    this.ctrl.computeDeliveryJourney(((ContentRight)this.contentRight).getTimeout());
                    break;

                case ContentRight.ROADMAP_NAME:
                    this.ctrl.generateRoadmap();
                    break;
                }
        }
    }
        
    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();
        if(source instanceof ImageButton) {
            ImageButton button = (ImageButton)source;
            switch(button.getButtonName()) {
            case ContentRight.ADD_BUTTON_NAME:
                this.ctrl.gotoAddDeliveryMode();
                break;
            case ContentRight.REDO_BUTTON_NAME:
                    this.ctrl.redo();
                break;
            case ContentRight.UNDO_BUTTON_NAME:
                    this.ctrl.undo();
                break;

            case ContentRight.UP_BUTTON_NAME:
                this.ctrl.upDelivery();
                break;
            case ContentRight.DOWN_BUTTON_NAME:
                this.ctrl.downDelivery();
            }
        }
        if(source instanceof DetailledDeliveryInformation) {
            DetailledDeliveryInformation ddi = (DetailledDeliveryInformation) source;
            switch(ddi.getLastButtonCalledName()) {
            case DetailledDeliveryInformation.DELETE_BUTTON_NAME:
                this.ctrl.deleteDelivery(ddi.getDelivery());
                break;
            case DetailledDeliveryInformation.MODIFY_BUTTON_NAME:
                DeliveryFormWindow dfw = new DeliveryFormWindow(this.ctrl, ddi.getDelivery());
                dfw.addWindowListener(this);
                dfw.setVisible(true);
                this.ctrl.prepareModifying();
                break;

            default:
                this.ctrl.changeDelivery(ddi);
                break;
            }      	
        }
    }
    
/*****************************STATE VISITOR*************************************/
    
    @Override
    public void visit(InitState state) {
        ((ContentRight) this.contentRight).visit(state);
        ((ContentLeft) this.contentLeft).visit(state);
    }

    @Override
    public void visit(MapLoadedState state) {
        ((ContentRight) this.contentRight).visit(state);
        ((ContentLeft) this.contentLeft).visit(state);
    }

    @Override
    public void visit(DeliveriesRequestLoadedState state) {
        ((ContentRight) this.contentRight).visit(state);
        ((ContentLeft) this.contentLeft).visit(state);
    }

    @Override
    public void visit(DeliveriesJourneyComputedState state) {
        ((ContentRight) this.contentRight).visit(state);
        ((ContentLeft) this.contentLeft).visit(state);
    }

    @Override
    public void visit(AddDeliveryModeState state) {
        ((ContentRight) this.contentRight).visit(state);
        ((ContentLeft) this.contentLeft).visit(state);
    }

    @Override
    public void visit(AddDeliveryModeStepDeliveryLocationState state) {
        ((ContentRight) this.contentRight).visit(state);
        ((ContentLeft) this.contentLeft).visit(state);
    }

    @Override
    public void visit(AddDeliveryModeStepDeliveryInformationState state) {
        //this.setEnabled(false);
        DeliveryFormWindow dfw = new DeliveryFormWindow(this.ctrl);
        dfw.addWindowListener(this);
        dfw.setVisible(true);
        ((ContentRight) this.contentRight).visit(state);
        ((ContentLeft) this.contentLeft).visit(state);
    }
    
    @Override
    public void visit(DDISelectedState state) {
        ((ContentRight) this.contentRight).visit(state);
        ((ContentLeft) this.contentLeft).visit(state);
    }    
    
    @Override
    public void visit(ModifyState state) {
        ((ContentRight) this.contentRight).visit(state);
        ((ContentLeft) this.contentLeft).visit(state);
    }
    
/************************WINDOW LISTENER**********************************/
    
    @Override
    public void windowClosing(WindowEvent e) {
        this.ctrl.cancelAddingDelivery();
    }
    
    @Override
    public void windowClosed(WindowEvent e) {
        
        this.ctrl.cancelAddingDelivery();
        
    }

/***********************UNUSED METHODS********************************/
    
    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
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

    @Override
    public void windowOpened(WindowEvent e) {
    }

}


