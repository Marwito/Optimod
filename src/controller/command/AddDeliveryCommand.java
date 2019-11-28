package controller.command;

import model.delivery.Delivery;
import model.delivery.DeliveryI;
import model.delivery.DeliveryJourneyI;
import model.graph.NodeI;
import view.DeliveryForm;

/**
 * Command to add a delivery to a given delivery journey.
 * @author Benoit
 *
 */
public class AddDeliveryCommand extends Command {
	private DeliveryJourneyI deliveryJourney;
	private DeliveryI newDelivery;
	private DeliveryI previousDelivery;
	
	/**
	 * Default Constructor, saves all necessary info to do and undo the command.
	 * @param deliveryJourney the delivery journey to modify
	 * @param newDelivery the delivery to add
	 * @param previousDelivery the delivery that is to be before the new one in the delivery journey.
	 */
	public AddDeliveryCommand(DeliveryJourneyI deliveryJourney, DeliveryI newDelivery, DeliveryI previousDelivery) {
		this.deliveryJourney = deliveryJourney;
		this.newDelivery = newDelivery;
		this.previousDelivery = previousDelivery;
	}
	
	/**
	 * Constructor for the AddDeliveryModeStepDeliveryInformationState,
	 * saves all necessary info to do and undo the command.
	 * @param deliveryJourney the delivery journey to modify
	 * @param deliveryForm the object containing duration and time window data for the new delivery
	 * @param newDeliveryNode the node to be associated with the new delivery
	 * @param previousDeliveryNodeId the id of the node of the delivery that is to be before the new one in the delivery journey.
	 * @throws Exception
	 */
	public AddDeliveryCommand(DeliveryJourneyI deliveryJourney, DeliveryForm deliveryForm, NodeI newDeliveryNode, int previousDeliveryNodeId) throws Exception {
            if(deliveryForm.duration <= 0) {
                throw new Exception("Impossible to add a delivery, fields incorrect");
            }
            this.deliveryJourney = deliveryJourney;

            this.newDelivery = new Delivery(deliveryForm.duration * 60,
                            deliveryForm.minTime, deliveryForm.maxTime, newDeliveryNode);

            this.previousDelivery = deliveryJourney.getDeliveryById(previousDeliveryNodeId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doCommand() throws Exception {
		deliveryJourney.addDelivery(newDelivery, previousDelivery);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void undoCommand() throws Exception {
		deliveryJourney.removeDelivery(newDelivery);
	}
}
