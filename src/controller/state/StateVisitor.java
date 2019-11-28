/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.state;

/**
 * Visitor interface for the State implementations
 * used to know which state is up by the view
 * @author jerome
 */
public interface StateVisitor {
    
    /**
     * @param state
     */
    public void visit(InitState state);
    
    /**
     * @param state
     */
    public void visit(MapLoadedState state);
    
    /**
     * @param state
     */
    public void visit(DeliveriesRequestLoadedState state);
    
    /**
     * @param state
     */
    public void visit(DeliveriesJourneyComputedState state);
    
    /**
     * @param state
     */
    public void visit(AddDeliveryModeState state);
    
    /**
     * @param state
     */
    public void visit(AddDeliveryModeStepDeliveryLocationState state);
    
    /**
     * @param state
     */
    public void visit(AddDeliveryModeStepDeliveryInformationState state);
    
    /**
     * @param state
     */
    public void visit(DDISelectedState state);
    
    /**
     * @param state
     */
    public void visit(ModifyState state);
    
}
