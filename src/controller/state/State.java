package controller.state;

import controller.Controller;
import model.delivery.DeliveryI;
import model.delivery.DeliveryJourneyI;
import model.delivery.DeliveryRequestI;
import model.graph.NodeI;
import model.map.MapI;
import view.DeliveryForm;
import view.DetailledDeliveryInformation;
import view.Window;

/**
 * Default interface for the state pattern. All function should
 * be called only be called inside the same named function of the Controller
 * @author jliermann
 *
 */
public interface State {
	
	/**
	 * Load a DeliveryRequest as specified from an XML file
	 * @param ctrl reference to the Controller
	 * @param view reference to the Window
	 * @param map reference to the map previously loaded
	 * @param deliveries the previously loaded DeliveryRequest in case of null return
	 * @return the DeliveryRequest loaded, null if impossible
	 */
	public DeliveryRequestI loadDeliveryRequest(Controller ctrl, Window view, MapI map, DeliveryRequestI deliveries);
	
	/**
	 * Compute a journey through the Deliveries and make checkups relatively of the Map loaded
	 * @param ctrl reference to the Controller
	 * @param view Reference to the Window
	 * @param deliveryRequest reference to the DeliveryRequest in which there are the deliveries needed to compute
	 * @param map reference to the associated Map
         * @param timeout time in millis after which stopping the computing
	 * @return the deliveryJourney computed
	 * @throws Exception 
	 */
	public DeliveryJourneyI computeDeliveryJourney(Controller ctrl, Window view, DeliveryRequestI deliveryRequest, MapI map, int timeout);
	
	/**
	 * Load a map from an XML file
	 * @param ctrl reference to the controller
	 * @param view reference to the view
	 * @param previousMap the previously loaded map in case of null return
	 * @return the map loaded, null if impossible
	 */
	public MapI loadXMLMap(Controller ctrl, Window view, MapI previousMap);
     
        /**
         * Generate a text file describing the journey
         * @param deliveryJourney the journey to convert
         */
	public void generateRoadmap(DeliveryJourneyI deliveryJourney);
	
        /**
         * Start a procedure of adding a delivery
         * @param ctrl reference to the controller
         * @param view reference to the view
         */
        public void gotoAddDeliveryMode(Controller ctrl, Window view);

        /**
         * select a delivery as point to add the new one, switch to AddDeliveryModeStepDeliveryLocationState
         * @param ctrl reference to the controller
         * @param view reference to the view
         * @param deliveryId the deliveryId prior to the one to add
         */
        public void selectShortestPathToModify(Controller ctrl, Window view, int deliveryId);
     
        /**
         * select a node as a node over which adding the delivery, switch to AddDeliveryModeStepDeliveryInformationState
         * @param ctrl reference to the controller
         * @param view reference to the view
         * @param node the node anchor for the new delivery
         */
        public void selectNewDeliveryLocation(Controller ctrl, Window view, NodeI node);
        
        /**
         * Add a new Delivery to the journey
         * @param ctrl reference to the controller
         * @param view reference to the view
         * @param delivery the informations of the delivery
         * @param deliveryJourney the journey on which adding the delivery
         */
        public void addNewDelivery(Controller ctrl, Window view, DeliveryForm delivery, DeliveryJourneyI deliveryJourney);

        
        /**
         * Undo the last Command (see controller.command for more informations)
         * @param ctrl reference to the controller
         * @param view reference to the view
         * @param deliveryJourney current journey, needed for various informations
         */
        public void undo(Controller ctrl, Window view, DeliveryJourneyI deliveryJourney);
        
        /**
         * Redo the last Command (see controller.command for more informations)
         * @param ctrl reference to the controller
         * @param view reference to the view
         * @param deliveryJourney current journey, needed for various informations
         */
        public void redo(Controller ctrl, Window view, DeliveryJourneyI deliveryJourney);
        
        /**
         * Delete a delivery from the journey
         * @param ctrl reference to the controller
         * @param view reference to the view
         * @param deliveryJourney the journey to modify
         * @param delivery the delivery to delete
         */
        public void deleteDelivery(Controller ctrl, Window view, DeliveryJourneyI deliveryJourney, DeliveryI delivery);

        /**
         * Decrement the index of a delivery in the list in the journey
         * @param ctrl reference to the controller
         * @param view reference to the view
         * @param deliveryJourney the journey to modify
         */
        public void upDelivery(Controller ctrl, Window view, DeliveryJourneyI deliveryJourney);  
        
        /**
         * Increment the index of a delivery in the list in the journey
         * @param ctrl reference to the controller
         * @param view reference to the view
         * @param deliveryJourney the journey to modify
         */
        public void downDelivery(Controller ctrl, Window view, DeliveryJourneyI deliveryJourney);

        /**
         * Select a delivery for further operations
         * @param ctrl reference to the controller
         * @param delivery the delivery to select
         */
        public void changeDelivery(Controller ctrl, DetailledDeliveryInformation delivery);

        /**
         * Cancel the operation of adding a delivery
         * @param ctrl reference to the controller
         */
        public void cancelAddingDelivery(Controller ctrl);
        
        /**
         * Finalize the modification of a delivery
         * @param ctrl reference to the controller
         * @param view reference to the view
         * @param deliveryForm the informations of the delivery
         * @param deliveryJourney the journey to modify
         * @param delivery the delivery to modify
         */
        public void modifyDelivery(Controller ctrl, Window view, DeliveryForm deliveryForm, DeliveryJourneyI deliveryJourney, DeliveryI delivery);
        
        /**
         * Initialiaze the modification of a delivery
         * @param ctrl reference to the controller 
         */
        public void prepareModifying(Controller ctrl);
        
        /**
         * call the right visit function from the StateVisitor
         * @param visitor 
         */
	public void accept(StateVisitor visitor);
}
