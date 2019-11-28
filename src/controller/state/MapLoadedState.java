package controller.state;

import controller.Controller;
import model.delivery.DeliveryRequestI;
import model.fileLoader.DeliveryRLoaderXML;
import model.map.MapI;
import view.Console;
import view.Window;

/**
 * The state loaded when the map is loaded, authorize a map reloading 
 * or a deliveryRequest loading
 * @author jerome
 */
public class MapLoadedState extends InitState {
	
    @Override
    public DeliveryRequestI loadDeliveryRequest(Controller ctrl, Window view, MapI map, DeliveryRequestI deliveries) {

        DeliveryRLoaderXML deliveryRLoaderXML = new DeliveryRLoaderXML();

        DeliveryRequestI deliveryRequest;
        try {
                deliveryRequest = deliveryRLoaderXML.loadDeliveryRequest(view.getFileName(), map);
        } catch (Throwable e) {
                if(e.getLocalizedMessage() != null)
                        Console.getConsole().createText(e.getLocalizedMessage());
                else
                        Console.getConsole().createText(Console.LOAD_DELIVERIES_FAILURE);
                return deliveries;
        }

        view.setDeliveriesRequest(deliveryRequest);
        ctrl.setCurrentState(Controller.DRLS);
        Console.getConsole().createText(Console.LOAD_DELIVERIES_SUCCESS);
        return deliveryRequest;

    }
        
        
    @Override
    public void accept(StateVisitor visitor) {
        visitor.visit(this);
    }
    
}
