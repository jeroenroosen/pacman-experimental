package nl.tudelft.jpacman.board;

/**
 * Factory that creates Pellet instances.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 * 
 */
public interface PelletFactory {

	/**
	 * @return A new Pellet.
	 */
	Pellet createPellet();

}
