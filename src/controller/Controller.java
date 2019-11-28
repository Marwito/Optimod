package controller;

import controller.state.AddDeliveryModeState;
import controller.state.MapLoadedState;
import controller.state.InitState;
import controller.state.DeliveriesRequestLoadedState;
import controller.state.DeliveriesJourneyComputedState;
import controller.state.State;
import controller.state.ModifyState;
import model.delivery.DeliveryI;
import model.delivery.DeliveryJourneyI;
import model.delivery.DeliveryRequestI;
import model.map.MapI;
import model.graph.NodeI;
import view.DeliveryForm;
import view.DetailledDeliveryInformation;
import view.Window;

/**
 * Make the link between model and view
 * @author jliermann
 *
 */
public class Controller {
	
    private Window view;
    private MapI map = null;
    private DeliveryRequestI deliveryRequest = null;
    private DeliveryJourneyI deliveryJourney = null;
    private State state = null;

    public static final DeliveriesJourneyComputedState DJCS = new DeliveriesJourneyComputedState();
    public static final DeliveriesRequestLoadedState DRLS = new DeliveriesRequestLoadedState();
    public static final MapLoadedState MLS = new MapLoadedState();
    public static final InitState INIT = new InitState();        
    public static final AddDeliveryModeState ADMS = new AddDeliveryModeState();
    public static final ModifyState MODS = new ModifyState();

    public Controller() {

        view = new Window(this, null);
        view.setVisible(true);
        this.setCurrentState(INIT);
    }

    /**
     * Change the state of the application
     * @param state the new state
     */
    public final void setCurrentState(State state) {
        this.state = state;
        this.state.accept(this.view);
    }

    /**
     * Called when the user ask to load a map.
     * Should normally display a JFileChooser
     */
    public void loadXMLMap() {
        map = state.loadXMLMap(this, view, map);
    }

    /**
     * Called when the user ask to load a DeliveryRequest
     * Should normally display a JFileChooser
     */
    public void loadXMLDeliveryRequest() {
        deliveryRequest = state.loadDeliveryRequest(this, view, map, deliveryRequest);
    }

    /**
     * Called when the user ask to compute the journey
     * @param timeout timeout from a field filled by the user
     */
    public void computeDeliveryJourney(int timeout) {
        deliveryJourney = state.computeDeliveryJourney(this, view, deliveryRequest, map, timeout);
    }

    /**
     * Called when the user ask to generate the roadmap
     * save a text file on the machine
     */
    public void generateRoadmap() {
        state.generateRoadmap(deliveryJourney);
    }
        
    /**
     * Called when the user click on the + button, start the addDelivery procedure
     */
    public void gotoAddDeliveryMode() {
        state.gotoAddDeliveryMode(this, view);
    }
    

    /**
     * Called when the user click on a delivery after starting the addDelivery procedure,
     * select the delivery prior to the new one
     * @param deliveryId the id of the delivery prior to the one to add
     */
    public void selectShortestPathToModify(int deliveryId) {
        state.selectShortestPathToModify(this, view, deliveryId);

    }
    
    /**
     * Called when the user click on a node after selecting the prior delivery,
     * select the node anchor for the new delivery
     * @param node the address of the new delivery
     */
    public void selectNewDeliveryLocation(NodeI node) {
        state.selectNewDeliveryLocation(this, view, node);
    }
    
    /**
     * Called after selection the address of the new delivery, complete its informations
     * and add them to the journey
     * @param delivery the informations of the new delivery
     */
    public void addNewDelivery(DeliveryForm delivery) {
        state.addNewDelivery(this, view, delivery, deliveryJourney);
    }
    
    /**
     * Called on click on the undo button, undo the last command
     */
    public void undo() {
        state.undo(this, view, deliveryJourney);
    }
    
    /**
     * Called on click on the redo button, redo the last command
     */
    public void redo() {
        state.redo(this, view, deliveryJourney);
    }
    
    /**
     * Called after the click on the delete button of one of the DetailledDeliveryInformation
     * delete the delivery associated
     * @param delivery the delivery to delete, linked with the ddi responsible of the click
     */
    public void deleteDelivery(DeliveryI delivery) {
        state.deleteDelivery(this, view, deliveryJourney, delivery);
    }
    
    /**
     * Called after the click on the up button, up the selected delivery in the list
     */
    public void upDelivery() {
        state.upDelivery(this, view, deliveryJourney);
    }
    
    /**
     * Called after the click on the down button, down the selected delivery in the list
     */
    public void downDelivery() {
        state.downDelivery(this, view, deliveryJourney);
    }
    
    /**
     * Called after the click on the validate button of the popup loaded after the prepareModifying method
     * modify the delivery associated
     * @param delivery the delivery to delete
     * @param form the new informations of the delivery
     */
    public void modifyDelivery(DeliveryI delivery, DeliveryForm form) {
    	state.modifyDelivery(this, view, form, deliveryJourney, delivery);
    }
    
    /**
     * Called after the click on a DetailledDeliveryInformation, (Un)select a delivery 
     * @param delivery the delivery to (un)select
     */
    public void changeDelivery(DetailledDeliveryInformation delivery) {
        state.changeDelivery(this, delivery);
    }
    
    /**
     * Called after the click on the modify button of one of the DetailledDeliveryInformation
     * prepare modificatins for the delivery associated
     */
    public void prepareModifying() {
        state.prepareModifying(this);
    }
    
    /**
     * Cancel the adding of a delivery
     */
    public void cancelAddingDelivery() {
        state.cancelAddingDelivery(this);
    }
}
