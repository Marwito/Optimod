package controller.command;

import model.delivery.DeliveryI;
import model.delivery.DeliveryJourneyI;

/**
 * Command to remove a given delivery from a given delivery journey.
 * @author Benoit
 *
 */
public class RemoveDeliveryCommand extends Command {
	private DeliveryJourneyI deliveryJourney;
	private DeliveryI removedDelivery;
	private DeliveryI previousDelivery;
	
	/**
	 * Default Constructor, saves all necessary info to do and undo the command.
	 * @param deliveryJourney the delivery journey to modify
	 * @param removedDelivery the delivery to remove
	 * @throws Exception the exception sent by getPreviousDelivery if it can't find it
	 */
	public RemoveDeliveryCommand(DeliveryJourneyI deliveryJourney, DeliveryI removedDelivery) throws Exception {
		this.deliveryJourney = deliveryJourney;
		this.removedDelivery = removedDelivery;
		this.previousDelivery = deliveryJourney.getPreviousDelivery(removedDelivery);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doCommand() throws Exception {
		deliveryJourney.removeDelivery(removedDelivery);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void undoCommand() throws Exception {
		deliveryJourney.addDelivery(removedDelivery, previousDelivery);
	}
}
