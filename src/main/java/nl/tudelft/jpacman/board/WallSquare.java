package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.sprite.Sprite;

/**
 * A wall square which cannot be occupied by anyone.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public class WallSquare extends Square {

	/**
	 * The sprite for the (back)ground of this square.
	 */
	private final Sprite sprite;

	/**
	 * Create a new wall square.
	 * 
	 * @param s
	 *            The sprite for this square.
	 */
	public WallSquare(Sprite s) {
		this.sprite = s;
	}

	@Override
	public boolean isAccessibleTo(Occupant occupant) {
		return false;
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

}
