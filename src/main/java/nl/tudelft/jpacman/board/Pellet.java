package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * The dots Pac-Man has to eat to finish a level.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public interface Pellet {

	/**
	 * @return The graphical representation of this pellet.
	 */
	Sprite getSprite();

	/**
	 * Have the player consume this pellet and handle the result.
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
