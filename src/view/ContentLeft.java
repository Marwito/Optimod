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
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import model.delivery.DeliveryJourneyI;
import model.map.MapI;

/**
 * The left part of the window, contains the Console and the DisplayedMap
 * 
 * @author jerome
 *
 */
public class ContentLeft extends JPanel implements StateVisitor{

    private static final long serialVersionUID = -4267797179785262807L;

    private Console console = Console.getConsole();
    private DisplayedMap displayedMap = null;

    private static final int MIDDLE_OFFSET_V = 10;

    /**
     * Constructor
     * Set the layout and add the components
     * @param w This is the window, that the content is added to
     */
    public ContentLeft(Window w) {
        this.displayedMap = new DisplayedMap(w);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.addComponents();

    }

    /**
     * Transmit a message to the Console to display a text
     * 
     * @param text the text to be displayed
     */
    public void writeMessage(String text) {
        this.console.createText(text);
    }
	
    /**
     * Transmission of a message to the DisplayedMap. Change the Map to be displayed
     * 
     * @param map The Map as defined in the model
     * @param scaledWidth The width as defined as a Property of Map (the size of the Map, not the DisplayedMap)
     * @param scaledHeight The height as defined as a Property of Map (the size of the Map, not the DisplayedMap)
     */
    public void setMap(MapI map, int scaledWidth, int scaledHeight) {
        this.displayedMap.setMap(map, scaledWidth, scaledHeight);
    }

    /**
     * Transmission of a message to the DisplayedMap
     * Change the Deliveries to be displayed on the image
     * 
     * @param deliveries the DeliveryRequest to be displayed
     */    
    public void setDeliveriesRequest(List<PointDrawer> deliveries) {
        this.displayedMap.setDeliveriesRequest(deliveries);
    }
    
    /**
     * Transmission of a message to the DisplayedMap
     * Add the informations of the path of the deliveryJourney
     * 
     * @param journey the journey after computing
     */
    public void SetJourney(DeliveryJourneyI journey) {
        this.displayedMap.setJourney(journey);
    }
	
    /**
     * Add the components to this panel
     */
    private void addComponents() {
        this.removeAll();
        this.add(this.displayedMap);
        this.addComponentListener(this.displayedMap);
        this.add(Box.createRigidArea(new Dimension(0, MIDDLE_OFFSET_V)));
        this.add(console);
    }
    
/************************************STATE VISITOR*******************************************************/

    @Override
    public void visit(InitState state) {
        this.displayedMap.visit(state);
    }

    @Override
    public void visit(MapLoadedState state) {
        this.displayedMap.visit(state);
    }

    @Override
    public void visit(DeliveriesRequestLoadedState state) {
        this.displayedMap.visit(state);
    }

    @Override
    public void visit(DeliveriesJourneyComputedState state) {
        this.displayedMap.visit(state);
    }

    @Override
    public void visit(AddDeliveryModeState state) {
        this.displayedMap.visit(state);
    }

    @Override
    public void visit(AddDeliveryModeStepDeliveryLocationState state) {
        this.displayedMap.visit(state);
    }

    @Override
    public void visit(AddDeliveryModeStepDeliveryInformationState state) {
        this.displayedMap.visit(state);
    }
    
    @Override
    public void visit(DDISelectedState state) {
        this.displayedMap.visit(state);
    }

    @Override
    public void visit(ModifyState state) {
        this.displayedMap.visit(state);
    }
	
}
