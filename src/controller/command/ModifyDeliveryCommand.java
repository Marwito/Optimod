package controller.command;

import java.util.Date;

import model.delivery.DeliveryI;
import model.delivery.DeliveryJourneyI;
import view.DeliveryForm;

/**
 * Command to modify the duration or time window of a given delivery of a given delivery journey.
 * @author Benoit
 *
 */
public class ModifyDeliveryCommand extends Command{
	private DeliveryJourneyI deliveryJourney;
	private DeliveryI delivery;
	private int newDeliveryDuration;
	private Date newMinTime;
	private Date newMaxTime;
	private int previousDeliveryDuration;
	private Date previousMinTime;
	private Date previousMaxTime;
	
	/**
	 * Default Constructor, saves all necessary info to do and undo the command.
	 * @param deliveryJourney the delivery journey to modify
	 * @param newDelivery the delivery to modify
	 * @param deliveryForm the object containing duration and time window data for the modification
	 * @throws Exception if the duration of the delivery form is not strictly positive
	 */
	public ModifyDeliveryCommand(DeliveryJourneyI deliveryJourney, DeliveryI delivery, DeliveryForm deliveryForm) throws Exception {
        if(deliveryForm.duration <= 0) {
            throw new Exception("Impossible to modify the delivery, fields incorrect");
        }
        
        this.deliveryJourney = deliveryJourney;
        this.delivery = delivery;
		
		this.newDeliveryDuration = deliveryForm.duration * 60;
		this.newMinTime = deliveryForm.minTime;
		this.newMaxTime = deliveryForm.maxTime;
		
		this.previousDeliveryDuration = delivery.getDeliveryDuration();
		this.previousMinTime = delivery.getMinTime();
		this.previousMaxTime = delivery.getMaxTime();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doCommand() throws Exception {
		deliveryJourney.updateDelivery(delivery, newDeliveryDuration, newMinTime, newMaxTime);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void undoCommand() throws Exception {
		deliveryJourney.updateDelivery(delivery, previousDeliveryDuration, previousMinTime, previousMaxTime);
	}
}
