package model.computer;

/**
 * This exception is thrown if a computer of a delivery journey exceeds his max
 * compute time.
 */
public class ComputeDeliveryJourneyException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a deliveryJourneyException with a given max time.
	 * 
	 * @param maxTime
	 *            the time exceeded by the computer of the delivery journey
	 */
	public ComputeDeliveryJourneyException(int maxTime) {
		super("Could not compute the delivery journey in the given time " + maxTime / 1000 + " seconds.");
	}
}
