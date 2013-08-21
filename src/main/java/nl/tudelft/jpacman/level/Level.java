package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Occupant;

/**
 * A Pac-Man level, handling all interactions with the board and the results
 * thereof.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public interface Level {

	/**
	 * @return The board of this level.
	 */
	Board getBoard();

	/**
	 * Registers a player to participate in this level.
	 * 
	 * @param p
	 *            The player to add to the board.
	 */
	void registerPlayer(Player p);

	/**
	 * Moves an {@link Occupant} around on the board and handles the collisions
	 * if any occur.
	 * 
	 * @param occupant
	 *            The occupant to move.
	 * @param direction
	 *            The direction to move the occupant in.
	 */
	void move(Occupant occupant, Direction direction);

	/**
	 * @return <code>true</code> iff all the pellets on the board have been
	 *         eaten and at least one player is alive.
	 */
	boolean isCompleted();

	/**
	 * Adds an observer that will be notified upon events.
	 * 
	 * @param observer
	 *            The observer to notify.
	 */
	void addObserver(LevelObserver observer);

	/**
	 * Removes an observer if it was present.
	 * 
	 * @param observer
	 *            The observer to remove.
	 */
	void removeObserver(LevelObserver observer);

}
