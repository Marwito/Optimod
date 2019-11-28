/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.state;

import controller.Controller;
import controller.command.ModifyDeliveryCommand;
import model.delivery.DeliveryI;
import model.delivery.DeliveryJourneyI;
import view.Console;
import view.DeliveryForm;
import view.Window;

/**
 * State after the beginning of the modification of a delivery. Allow the validation
 * or the cancelling of the modifications
 * @author jerome
 */
public class ModifyState extends DefaultState{
    
    @Override
    public void modifyDelivery(Controller ctrl, Window view, DeliveryForm deliveryForm, DeliveryJourneyI deliveryJourney, DeliveryI delivery) {
        ModifyDeliveryCommand modifyDeliveryCommand;
        try {
            modifyDeliveryCommand = new ModifyDeliveryCommand(deliveryJourney, delivery, deliveryForm);
            DefaultState.cm.addCommand(modifyDeliveryCommand);
        } catch (Exception e) {
            if(e.getMessage() != null && !e.getMessage().equals("")) {
                Console.getConsole().createText(e.getMessage());
            }
            else {
                Console.getConsole().createText(Console.MODIFY_FAILURE);
            }
            ctrl.setCurrentState(Controller.DJCS);
            return;
        }
     	view.setDeliveriesRequest(deliveryJourney.getRequest(), deliveryJourney);
     	view.SetJourney(deliveryJourney);
        ctrl.setCurrentState(Controller.DJCS);
        Console.getConsole().createText(Console.MODIFY_SUCCESS);
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
