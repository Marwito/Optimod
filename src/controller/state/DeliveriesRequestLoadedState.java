package controller.state;

import controller.Controller;
import model.delivery.DeliveryJourney;
import model.delivery.DeliveryJourneyI;
import model.delivery.DeliveryRequestI;
import model.map.MapI;
import view.Console;
import view.Window;

/**
 * The state loaded after a deliveryRequest is loaded, authorize the reloading 
 * of a map or a deliveryRequest, or a computation of a journey
 * @author jerome
 */
public class DeliveriesRequestLoadedState extends MapLoadedState {
    
    @Override
    public DeliveryJourneyI computeDeliveryJourney(Controller ctrl, Window view, DeliveryRequestI deliveryRequest, MapI map, int timeout) {
        
        DeliveryJourneyI dj = null;
        
        try {
                dj = new DeliveryJourney(deliveryRequest, map, timeout);
                view.SetJourney(dj);
        Console.getConsole().createText(Console.COMPUTE_SUCCESS);
        } catch (Exception e) {
                if(e.getLocalizedMessage() != null) {
        Console.getConsole().createText(e.getLocalizedMessage());
        }
        else {
                Console.getConsole().createText(Console.COMPUTE_FAILURE);
        }
                return dj;
        }
        ctrl.setCurrentState(Controller.DJCS);
        return dj;

    }
    
    @Override
    public void accept(StateVisitor visitor) {
        visitor.visit(this);
    }

}
