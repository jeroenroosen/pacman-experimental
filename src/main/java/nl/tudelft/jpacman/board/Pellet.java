package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.level.Player;

/**
 * The dots Pac-Man has to eat to finish a level.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public interface Pellet extends Occupant {

	/**
	 * Have the player consume this pellet and handle the result, then remove
	 * the pellet from the board.
	 * 
	 * @param consumer
	 *            The player that consumed this pellet.
	 */
	void consumedBy(Player consumer);

	/**
	 * @return The amount of points this pellet is worth upon consumption.
	 */
	int getPoints();
}
