package controller.state;

import controller.Controller;
import controller.command.CommandManager;
import model.delivery.DeliveryI;
import model.delivery.DeliveryJourneyI;
import model.delivery.DeliveryRequestI;
import model.map.MapI;
import model.graph.NodeI;
import view.DeliveryForm;
import view.DetailledDeliveryInformation;
import view.Window;

/**
 * The default implementation of the State interface, used to avoid bugs when only a few
 * methods are wished
 * @author jerome
 */
public abstract class DefaultState implements State{

    /**
     * The CommandManager for the undo/redo
     */
    protected static CommandManager cm = new CommandManager();

    @Override
    public MapI loadXMLMap(Controller ctrl, Window view, MapI previousMap) {
            return null;
    }

    @Override
    public DeliveryRequestI loadDeliveryRequest(Controller ctrl, Window view, MapI map, DeliveryRequestI deliveries) {
            return null;
    }

    @Override
    public DeliveryJourneyI computeDeliveryJourney(Controller ctrl, Window view, DeliveryRequestI deliveryRequest, MapI map, int timeout) {
            return null;
    }

    @Override
    public void generateRoadmap(DeliveryJourneyI deliveryJourney) {
    }
	
    @Override
    public void gotoAddDeliveryMode(Controller ctrl, Window view) {
    }

    @Override
    public void selectShortestPathToModify(Controller ctrl, Window view, int deliveryId) {
    }

    @Override
    public void selectNewDeliveryLocation(Controller ctrl, Window view, NodeI node) {
    }

    @Override
    public void addNewDelivery(Controller ctrl, Window view, DeliveryForm delivery, DeliveryJourneyI deliveryJourney) {
    }   

    @Override
    public void undo(Controller ctrl, Window view, DeliveryJourneyI deliveryJourney) {
    }

    @Override
    public void redo(Controller ctrl, Window view, DeliveryJourneyI deliveryJourney) {
    }
    
    @Override
    public void deleteDelivery(Controller ctrl, Window view, DeliveryJourneyI deliveryJourney, DeliveryI delivery) {
        
    }

    @Override
    public void upDelivery(Controller ctrl, Window view, DeliveryJourneyI deliveryJourney) {
        
    }
    
    @Override
    public void downDelivery(Controller ctrl, Window view, DeliveryJourneyI deliveryJourney) {
        
    }
    
    @Override
    public void changeDelivery(Controller ctrl, DetailledDeliveryInformation delivery) {
        
    }
    
    @Override
    public void cancelAddingDelivery(Controller ctrl) {
        
    }
    
    @Override
    public void modifyDelivery(Controller ctrl, Window view, DeliveryForm deliveryForm, DeliveryJourneyI deliveryJourney, DeliveryI delivery) {
        
    }
    
    @Override
    public void prepareModifying(Controller ctrl) {
        
    }
    
    @Override
    public abstract void accept(StateVisitor visitor);

}
