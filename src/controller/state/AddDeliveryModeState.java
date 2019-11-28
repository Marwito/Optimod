/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.state;

import controller.Controller;
import view.Console;
import view.Window;

/**
 * State after initializing adding of a delivery (after button + is pressed)
 * Allow to select a delivery as the adding point of the new one
 * @author jerome
 */
public class AddDeliveryModeState extends DefaultState{

    @Override
    public void accept(StateVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public void selectShortestPathToModify(Controller ctrl, Window view, int deliveryId) {
        Console.getConsole().createText(Console.SELECT_NEW_DELIVERY_LOCATION);
        ctrl.setCurrentState(new AddDeliveryModeStepDeliveryLocationState(deliveryId));

    }
    
    @Override
    public void cancelAddingDelivery(Controller ctrl) {
        ctrl.setCurrentState(Controller.DJCS);
    }
    
}
