package nl.tudelft.jpacman.level;

/**
 * Observes a level that is being played.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public interface LevelObserver {

	/**
	 * Handles the death of a player.
	 * 
	 * @param p
	 *            The player that died.
	 */
	void playerDied(Player p);

	/**
	 * Handles the completion of a level.
	 */
	void levelCompleted();
}
