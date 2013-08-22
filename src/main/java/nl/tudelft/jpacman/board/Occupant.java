package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.level.Direction;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * A playing piece on the game board that occupies a square.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public interface Occupant {

	/**
	 * @return The graphical representation of this occupant.
	 */
	Sprite getSprite();

	/**
	 * Moves this occupant to the square iff the square type allows for it.
	 * 
	 * @param square
	 *            The square to place this occupant on.
	 * @return <code>true</code> iff the move was successful.
	 */
	boolean occupy(Square square);

	/**
	 * Removes this occupant from the board.
	 */
	void leaveCurrentSquare();

	/**
	 * @return The square this occupant is occupying, or <code>null</code> if
	 *         this occupant is not placed on a board.
	 */
	Square getSquare();

	/**
	 * @return The direction this occupant is currently facing.
	 */
	Direction getDirection();

	/**
	 * Turns this occupant into the given direction.
	 * 
	 * @param direction
	 *            The new direction this occupant is facing.
	 */
	void setDirection(Direction direction);
}
