package controller.state;

import controller.Controller;
import controller.command.RemoveDeliveryCommand;
import model.delivery.DeliveryI;
import model.delivery.DeliveryJourneyI;
import model.delivery.RoadMap;
import view.Console;
import view.DetailledDeliveryInformation;
import view.Window;

/**
 * The state loaded after a computation, root state to come back after finishing 
 * any operation but loading map or deliveryRequest. Authorize initializing adding,
 * modification, performing delete,undo, redo, selection or generation of roadmap
 * @author jerome
 */
public class DeliveriesJourneyComputedState extends DeliveriesRequestLoadedState {
    
    @Override
    public void generateRoadmap(DeliveryJourneyI deliveryJourney) {
    	try {   
            new RoadMap(deliveryJourney);
            Console.getConsole().createText(Console.CREATE_ROADMAP_SUCCESS);
        } catch (Exception e) {
            Console.getConsole().createText(Console.CREATE_ROADMAP_FAILURE);
            Console.getConsole().createText(e.getMessage());
        }
    }

    @Override
    public void gotoAddDeliveryMode(Controller ctrl, Window view) {
    	Console.getConsole().createText(Console.SELECT_DELIVERY);
        ctrl.setCurrentState(Controller.ADMS);
    }
    
    @Override
    public void undo(Controller ctrl, Window view, DeliveryJourneyI deliveryJourney) {
    	try {
            cm.undo();
        } catch (Exception e) {
            Console.getConsole().createText(e.getMessage()); 
        } 
    	view.setDeliveriesRequest(deliveryJourney.getRequest(), deliveryJourney);
    	view.SetJourney(deliveryJourney);
        ctrl.setCurrentState(Controller.DJCS);
    }
    
    @Override
    public void redo(Controller ctrl, Window view, DeliveryJourneyI deliveryJourney) {
    	try {
            cm.redo();
        } catch (Exception e) {
            Console.getConsole().createText(e.getMessage()); 
        }
    	view.setDeliveriesRequest(deliveryJourney.getRequest(), deliveryJourney);
    	view.SetJourney(deliveryJourney);
        ctrl.setCurrentState(Controller.DJCS);
    }
	
    @Override
    public void deleteDelivery(Controller ctrl, Window view, DeliveryJourneyI deliveryJourney, DeliveryI delivery) {
        RemoveDeliveryCommand removeDeliveryCommand;
        try {
            removeDeliveryCommand = new RemoveDeliveryCommand(deliveryJourney, delivery);		
            DefaultState.cm.addCommand(removeDeliveryCommand);
        } catch (Exception e) {
            Console.getConsole().createText(e.getMessage());
        }
        view.setDeliveriesRequest(deliveryJourney.getRequest(), deliveryJourney);
    	view.SetJourney(deliveryJourney);
    	view.repaint();
        ctrl.setCurrentState(Controller.DJCS);
    }
    
    @Override
    public void prepareModifying(Controller ctrl) {
        ctrl.setCurrentState(Controller.MODS);
    }
    
    @Override
    public void changeDelivery(Controller ctrl, DetailledDeliveryInformation delivery) {
        ctrl.setCurrentState(new DDISelectedState(delivery));
    }
    
    @Override
    public void accept(StateVisitor visitor) {
        visitor.visit(this);
    }
	
}
