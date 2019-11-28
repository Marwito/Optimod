package controller.command;

import model.delivery.DeliveryI;
import model.delivery.DeliveryJourneyI;

/**
 * Command exchange the position of two given deliveries of a given delivery journey.
 * @author Benoit
 *
 */
public class SwapDeliveriesCommand extends Command {
	private DeliveryJourneyI deliveryJourney;
	private DeliveryI firstDelivery;
	private DeliveryI secondDelivery;
	
	/**
	 * Default Constructor, saves all necessary info to do and undo the command.
	 * @param deliveryJourney the delivery journey to modify
	 * @param firstDelivery the first delivery to swap with the second
	 * @param secondDelivery the second delivery to swap with the first
	 */
	public SwapDeliveriesCommand(DeliveryJourneyI deliveryJourney, DeliveryI firstDelivery, DeliveryI secondDelivery) {
		this.deliveryJourney = deliveryJourney;
		this.firstDelivery = firstDelivery;
		this.secondDelivery = secondDelivery;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doCommand() throws Exception {
		deliveryJourney.swapDeliveries(firstDelivery, secondDelivery);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void undoCommand() throws Exception {
		deliveryJourney.swapDeliveries(secondDelivery, firstDelivery);
	}
}
