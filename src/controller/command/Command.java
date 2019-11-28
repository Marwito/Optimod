package controller.command;

/**
 * Abstract class representing a Command.
 * @author Benoit
 *
 */
public abstract class Command {
	/**
	 * Executes the command, throws any exception sent by any inside call to another method.
	 * @throws Exception the exception sent by any inside call to another method.
	 */
	public abstract void doCommand() throws Exception;
	
	/**
	 * Undoes the command, throws any exception sent by any inside call to another method.
	 * @throws Exception the exception sent by any inside call to another method.
	 */
	public abstract void undoCommand() throws Exception;
}
