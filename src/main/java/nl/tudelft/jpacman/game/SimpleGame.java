package nl.tudelft.jpacman.game;

import java.util.List;

import nl.tudelft.jpacman.level.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelObserver;
import nl.tudelft.jpacman.level.Player;

import com.google.common.collect.ImmutableList;

/**
 * A single player game with a single level, hence a simple game.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public class SimpleGame implements Game, LevelObserver {

	/**
	 * The player.
	 */
	private final Player player;

	/**
	 * The level.
	 */
	private final Level level;

	/**
	 * <code>true</code> iff the game was started or resumed.
	 */
	private boolean inProgress;

	/**
	 * Object to lock the move methods.
	 */
	private final Object moveLock = new Object();

	/**
	 * Creates a new game.
	 * 
	 * @param p
	 *            The player playing this game.
	 * @param l
	 *            The level to be played.
	 */
	public SimpleGame(Player p, Level l) {
		this.player = p;
		this.level = l;
		level.registerPlayer(player);
		level.addObserver(this);
	}

	/**
	 * Moves the player one square upwards.
	 */
	public void up() {
		move(player, Direction.NORTH);
	}

	/**
	 * Moves the player one square downwards.
	 */
	public void down() {
		move(player, Direction.SOUTH);
	}

	/**
	 * Moves the player one square to the left.
	 */
	public void left() {
		move(player, Direction.WEST);
	}

	/**
	 * Moves the player one square to the right.
	 */
	public void right() {
		move(player, Direction.EAST);
	}

	/**
	 * Moves the player on square in the given direction and handles the result
	 * of the move.
	 * 
	 * @param player
	 *            The player to move.
	 * @param direction
	 *            The direction to move in.
	 */
	protected void move(Player player, Direction direction) {
		if (isInProgress()) {
			synchronized (moveLock) {
				getLevel().move(player, direction);
			}
		}
	}

	@Override
	public void start() {
		inProgress = true;
	}

	@Override
	public void stop() {
		inProgress = false;
	}

	@Override
	public boolean isInProgress() {
		return inProgress;
	}

	@Override
	public List<Player> getPlayers() {
		return ImmutableList.of(player);
	}

	@Override
	public Level getLevel() {
		return level;
	}

	@Override
	public void playerDied(Player p) {
		stop();
	}

	@Override
	public void levelCompleted() {
		stop();
	}
}
