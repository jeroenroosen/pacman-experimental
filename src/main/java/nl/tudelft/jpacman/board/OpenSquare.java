package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.sprite.Sprite;

/**
 * An open square which can be accessed by anyone.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public class OpenSquare extends Square {

	/**
	 * The visual representation of this tile.
	 */
	private final Sprite sprite;

	/**
	 * Creates a new, empty open square.
	 * 
	 * @param s
	 *            The sprite of this square.
	 */
	public OpenSquare(Sprite s) {
		this.sprite = s;
	}

	@Override
	public boolean isAccessibleTo(Occupant occupant) {
		return true;
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

}
