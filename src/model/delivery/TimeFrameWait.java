package model.delivery;

import java.util.Date;

/**
 * This class is like a TimeFrame, but it in addition a waiting time.
 *
 */
public class TimeFrameWait extends TimeFrame {

	private int waitingTime;

	/**
	 * Creates a time frame with a given starting and ending time.
	 * 
	 * @param startingTime
	 *            the starting time of the time frame
	 * @param endingTime
	 *            the ending time of the time frame
	 * @param waitingTime
	 *            the waiting time of the frame (in seconds)
	 */
	public TimeFrameWait(Date startingTime, Date endingTime, int waitingTime) {
		super(startingTime, endingTime);
		this.waitingTime = waitingTime;
	}

	/**
	 * Returns the waiting time of the time frame.
	 * 
	 * @return the waiting time of the time frame (in seconds)
	 */
	public int getWaitingTime() {
		return this.waitingTime;
	}

}
