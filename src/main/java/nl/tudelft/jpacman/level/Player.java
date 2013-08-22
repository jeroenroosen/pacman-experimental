package nl.tudelft.jpacman.level;

import java.util.Map;

import nl.tudelft.jpacman.sprite.AnimatedSprite;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * A participant in a game of Pac-Man.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public class Player extends DirectionalOccupant {

	private final AnimatedSprite dyingAnimation;

	/**
	 * The player's score.
	 */
	private int score;

	/**
	 * <code>true</code> iff this player is alive.
	 */
	private boolean alive;

	public Player(Map<Direction, Sprite> pacSprites,
			AnimatedSprite deathAnimation) {
		super(pacSprites);
		score = 0;
		alive = true;
		dyingAnimation = deathAnimation;
	}

	/**
	 * @return <code>true</code> iff this player is alive.
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * Sets whether this player is alive or not.
	 * 
	 * @param flag
	 *            <code>true</code> for alive, <code>false</code> for death.
	 */
	public void setAlive(boolean flag) {
		resetDeathAnimation(flag);
		this.alive = flag;
	}

	private void resetDeathAnimation(boolean flag) {
		if (isAlive() && flag == false) {
			dyingAnimation.restart();
		}
	}

	/**
	 * @return The score (amount of accumulated points) of the player.
	 */
	public int getScore() {
		return score;
	}

	@Override
	public Sprite getSprite() {
		if (isAlive()) {
			return super.getSprite();
		} else {
			return dyingAnimation;
		}
	}

	/**
	 * Adds points to the score of this player.
	 * 
	 * @param points
	 *            The amount of points to add.
	 */
	public void addPoints(int points) {
		score += points;
	}

}
