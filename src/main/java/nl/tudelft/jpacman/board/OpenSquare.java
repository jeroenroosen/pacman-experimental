package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.sprite.Sprite;

/**
 * An open square which can be accessed by anyone.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public class OpenSquare extends Square {

	private final Sprite sprite;

	/**
	 * Creates a new open square.
	 * 
	 * @param s
	 *            The sprite of this square.
	 */
	public OpenSquare(Sprite s) {
		this(null, s);
	}

	/**
	 * Creates a new open square with a pellet on it.
	 * 
	 * @param pellet
	 *            The pellet to put on this square.
	 * @param s
	 *            The sprite of this square.
	 */
	public OpenSquare(Pellet pellet, Sprite s) {
		super(pellet);
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
