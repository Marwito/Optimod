package model.delivery;

import java.util.Date;

/**
 * A time frame has a starting time and an ending time.
 *
 */
public class TimeFrame {
	private Date startingTime;
	private Date endingTime;

	/**
	 * Creates a time frame with a given starting and ending time.
	 * 
	 * @param startingTime
	 *            the starting time of the time frame
	 * @param endingTime
	 *            the ending time of the time frame
	 */
	public TimeFrame(Date startingTime, Date endingTime) {
		this.startingTime = startingTime;
		this.endingTime = endingTime;
	}

	/**
	 * Returns the starting time of the time frame.
	 * 
	 * @return the starting time of the time frame
	 */
	public Date getStartingTime() {
		return this.startingTime;
	}

	/**
	 * Returns the ending time of the time frame.
	 * 
	 * @return the ending time of the time frame
	 */
	public Date getEndingTime() {
		return this.endingTime;
	}
}
