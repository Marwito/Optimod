package controller.command;

import java.util.LinkedList;
import java.util.List;

/**
 * Class that contains and manages the lists of all done and undone commands
 * @author Benoit
 *
 */
public class CommandManager {
	private List<Command> doneCommands = new LinkedList<>();
	private List<Command> undoneCommands = new LinkedList<>();
	
	/**
	 * Default constructor.
	 */
	public CommandManager() {}
	
	/**
	 * Tries to execute the passed in command, throws its exception if it fails,
	 * or if it succeeds, memorizes the command reset the list of undone commands.
	 * @param command
	 * @throws Exception The exception sent by doCommand
	 */
	public void addCommand(Command command) throws Exception {
		command.doCommand();
		
		doneCommands.add(command);
		
		undoneCommands.clear();
	}
	
	/**
	 * Tries to undo the last executed command.
	 * If there is no command to undo, throws an exception.
	 * Throws the undoCommand exception if it fails.
	 * @throws Exception either to tell that there is no command to undo, or the one of
	 * 		undoCommand if it fails
	 */
	public void undo() throws Exception {
		if (doneCommands.size() == 0) throw new Exception("There is no command to undo.");
		
		Command commandToUndo = doneCommands.get(doneCommands.size() - 1);
		
		commandToUndo.undoCommand();
		
		undoneCommands.add(commandToUndo);
		doneCommands.remove(commandToUndo);
	}
	
	/**
	 * Tries to redo the last undone command.
	 * If there is no command to redo, throws an exception.
	 * Throws the doCommand exception if it fails.
	 * @throws Exception either to tell that there is no command to redo, or the one of
	 * 		doCommand if it fails
	 */
	public void redo() throws Exception {
		if (undoneCommands.size() == 0) throw new Exception("There is no command to redo.");
		
		Command commandToRedo = undoneCommands.get(undoneCommands.size() - 1);
		
		commandToRedo.doCommand();
		
		doneCommands.add(commandToRedo);
		undoneCommands.remove(commandToRedo);
	}
}
