/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.state;


import controller.Controller;
import controller.command.AddDeliveryCommand;
import model.delivery.DeliveryJourneyI;
import model.graph.NodeI;
import view.Console;
import view.DeliveryForm;
import view.Window;

/**
 * State after the location for the new delivery is selected. Allow to fill
 * the informations for the new delivery
 * @author jerome
 */
public class AddDeliveryModeStepDeliveryInformationState extends DefaultState{
    
    private int deliveryId;
    private NodeI location;
    
    /**
     * Create the state
     * @param deliveryId the result of AddDeliveryMode to be saved before being sent to the model
     * @param location the result of AddDeliveryModeStepDeliveryLocation to be saved before being sent to the model
     */
    public AddDeliveryModeStepDeliveryInformationState(int deliveryId, NodeI location) {
        this.deliveryId = deliveryId;
        this.location = location;
    }
    
    @Override
    public void accept(StateVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public void addNewDelivery(Controller ctrl, Window view, DeliveryForm deliveryForm, DeliveryJourneyI deliveryJourney) {
        AddDeliveryCommand addDeliveryCommand;
        try {
            addDeliveryCommand = new AddDeliveryCommand(deliveryJourney, deliveryForm, location, deliveryId);
        DefaultState.cm.addCommand(addDeliveryCommand);
        } catch (Exception e) {
            Console.getConsole().createText(e.getMessage());
            ctrl.setCurrentState(Controller.DJCS);
            return;
        }
    	view.setDeliveriesRequest(deliveryJourney.getRequest(), deliveryJourney);
    	view.SetJourney(deliveryJourney);
        ctrl.setCurrentState(Controller.DJCS);
        Console.getConsole().createText(Console.ADD_DELIVERY_SUCCESS);        
    }
    
    @Override
    public void cancelAddingDelivery(Controller ctrl) {
        ctrl.setCurrentState(Controller.DJCS);
    }
    
}
