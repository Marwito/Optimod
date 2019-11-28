/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.state;

import controller.Controller;
import model.graph.NodeI;
import view.Console;
import view.Window;

/**
 * State after the inserting point for the new delivery is selected. Allow to select
 * the location for the new delivery
 * @author jerome
 */
public class AddDeliveryModeStepDeliveryLocationState extends DefaultState {
    
    private int deliveryId;
    
    /**
     * Create the state
     * @param deliveryId the result of AddDeliveryMode to be saved before being sent to the model
     */
    public AddDeliveryModeStepDeliveryLocationState(int deliveryId) {
        this.deliveryId = deliveryId;
    }
   
    @Override
    public void selectNewDeliveryLocation(Controller ctrl, Window view, NodeI node) {
        Console.getConsole().createText(Console.FILL_DELIVERY_INFORMATION);
        ctrl.setCurrentState(new AddDeliveryModeStepDeliveryInformationState(deliveryId, node));

    }
    
    @Override
    public void cancelAddingDelivery(Controller ctrl) {
        ctrl.setCurrentState(Controller.DJCS);
    }
    
    @Override
    public void accept(StateVisitor visitor) {
        visitor.visit(this);
    }
}
