package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * The default small pellet on the board, worth 10 points.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public class SmallPellet implements Pellet {

	/**
	 * Amount of points obtained by consuming this pellet.
	 */
	private static final int POINTS = 10;

	/**
	 * The sprite of this pellet.
	 */
	private final Sprite sprite;

	/**
	 * Creates a new pellet.
	 * 
	 * @param s
	 *            The sprite of this pellet.
	 */
	public SmallPellet(Sprite s) {
		this.sprite = s;
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void consumedBy(Player consumer) {
		consumer.addPoints(getPoints());
	}

	@Override
	public int getPoints() {
		return POINTS;
	}

}
