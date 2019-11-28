/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.state;

import controller.Controller;
import controller.command.RemoveDeliveryCommand;
import controller.command.SwapDeliveriesCommand;
import model.delivery.DeliveryI;
import model.delivery.DeliveryJourneyI;
import model.delivery.RoadMap;
import view.Console;
import view.DetailledDeliveryInformation;
import view.Window;

/**
 * State after the selection of a DetailledDeliveryInformation. Authorize operations
 * relative to this ddi, such as upping and downing, in addition to keeping most of
 * DeliveryJourneyComputedState liberties
 * @author jerome
 */
public class DDISelectedState extends DeliveriesJourneyComputedState{
    
    private DetailledDeliveryInformation ddi = null;
    
    /**
     * create the state
     * @param ddi reference to the DetailledDeliveryInformation to be saved
     */
    public DDISelectedState(DetailledDeliveryInformation ddi) {
        ddi.clicked(true);
        this.ddi = ddi;
    }
    
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
        ddi.clicked(false);
        ctrl.setCurrentState(Controller.ADMS);
    }
    
    @Override
    public void undo(Controller ctrl, Window view, DeliveryJourneyI deliveryJourney) {
    	try {
            cm.undo();
        } catch (Exception e) {
            Console.getConsole().createText(e.getMessage()); 
            return;
        } 
    	view.setDeliveriesRequest(deliveryJourney.getRequest(), deliveryJourney);
    	view.SetJourney(deliveryJourney);
        ctrl.setCurrentState(this);
    }
    
    @Override
    public void redo(Controller ctrl, Window view, DeliveryJourneyI deliveryJourney) {
    	try {
            cm.redo();
        } catch (Exception e) {
            Console.getConsole().createText(e.getMessage()); 
            return;
        }
    	view.setDeliveriesRequest(deliveryJourney.getRequest(), deliveryJourney);
    	view.SetJourney(deliveryJourney);
        ctrl.setCurrentState(this);
    }
	
    @Override
    public void deleteDelivery(Controller ctrl, Window view, DeliveryJourneyI deliveryJourney, DeliveryI delivery) {
        RemoveDeliveryCommand removeDeliveryCommand;
        try {
            removeDeliveryCommand = new RemoveDeliveryCommand(deliveryJourney, delivery);

            DefaultState.cm.addCommand(removeDeliveryCommand);
            view.setDeliveriesRequest(deliveryJourney.getRequest(), deliveryJourney);
            view.SetJourney(deliveryJourney);
            view.repaint();
            ddi.clicked(false);
            ctrl.setCurrentState(Controller.DJCS);
        } catch (Exception e) {
            Console.getConsole().createText(e.getMessage());
            return;
        }   
    }
    
    @Override
    public void upDelivery(Controller ctrl, Window view, DeliveryJourneyI deliveryJourney) {
    	SwapDeliveriesCommand swapDeliveriesCommand;
        try {
            DeliveryI previousDelivery = deliveryJourney.getPreviousDelivery(ddi.getDelivery());
            swapDeliveriesCommand = new SwapDeliveriesCommand(deliveryJourney, ddi.getDelivery(), previousDelivery);

        DefaultState.cm.addCommand(swapDeliveriesCommand);
        } catch (Exception e) {
            Console.getConsole().createText(e.getMessage());
            return;
        }
        view.setDeliveriesRequest(deliveryJourney.getRequest(), deliveryJourney);
    	view.SetJourney(deliveryJourney);
    	view.repaint();
        ctrl.setCurrentState(Controller.DJCS);
    }
    
    @Override
    public void downDelivery(Controller ctrl, Window view, DeliveryJourneyI deliveryJourney) {
    	SwapDeliveriesCommand swapDeliveriesCommand;
        try {
            DeliveryI nextDelivery = deliveryJourney.getNextDelivery(ddi.getDelivery());
            swapDeliveriesCommand = new SwapDeliveriesCommand(deliveryJourney, nextDelivery, ddi.getDelivery());

        DefaultState.cm.addCommand(swapDeliveriesCommand);
        } catch (Exception e) {
            Console.getConsole().createText(e.getMessage());
            return;
        }
        view.setDeliveriesRequest(deliveryJourney.getRequest(), deliveryJourney);
    	view.SetJourney(deliveryJourney);
    	view.repaint();
        ctrl.setCurrentState(Controller.DJCS);
    }
    
    @Override
    public void changeDelivery(Controller ctrl, DetailledDeliveryInformation delivery) {
        ddi.clicked(false);
        if(ddi.getDelivery() == delivery.getDelivery()) {
            ctrl.setCurrentState(Controller.DJCS);
            return;
        }
        ctrl.setCurrentState(new DDISelectedState(delivery));
    }
    
    @Override
    public void prepareModifying(Controller ctrl) {
        ctrl.setCurrentState(Controller.MODS);
    }

    @Override
    public void accept(StateVisitor visitor) {
        visitor.visit(this);
    }
    
}
