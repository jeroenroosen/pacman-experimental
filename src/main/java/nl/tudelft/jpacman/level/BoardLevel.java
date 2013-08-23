package nl.tudelft.jpacman.level;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Occupant;
import nl.tudelft.jpacman.board.Pellet;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.CollisionInteractions.CollisionHandler;
import nl.tudelft.jpacman.sprite.PacManSprites;

/**
 * Basic implementation of a level.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public class BoardLevel implements Level {

	/**
	 * The board.
	 */
	private final Board board;

	/**
	 * The observers of this level.
	 */
	private final List<LevelObserver> observers;

	/**
	 * The current point for the list of starting positions.
	 */
	private int spawnPointIndex;

	/**
	 * A locking object to ensure moves are not executed at the same time.
	 */
	private final Object moveLock = new Object();

	private final CollisionInteractions collisionsInteractions;

	private final List<Player> players;

	/**
	 * Creates a new level based on a board.
	 * 
	 * @param levelBoard
	 *            The board for this level.
	 */
	public BoardLevel(Board levelBoard) {
		assert levelBoard != null;

		this.board = levelBoard;
		this.collisionsInteractions = new CollisionInteractions();
		this.observers = new ArrayList<>();
		this.spawnPointIndex = 0;
		this.players = new ArrayList<>();

		Square square = board.getGhostStartPositions().get(0);
		new Ghost(new PacManSprites().getGhostSprite(GhostColor.RED))
				.occupy(square);

		collisionsInteractions.onCollision(Player.class, Ghost.class, true,
				new CollisionHandler<Player, Ghost>() {
					@Override
					public void handleCollision(Player player, Ghost ghost) {
						player.setAlive(false);
						notifyObserversOnDeath(player);
					}
				});

		collisionsInteractions.onCollision(Player.class, Pellet.class,
				new CollisionHandler<Player, Pellet>() {
					@Override
					public void handleCollision(Player player, Pellet pallet) {
						pallet.consumedBy(player);
					}
				});
	}

	@Override
	public Board getBoard() {
		return board;
	}

	@Override
	public void registerPlayer(Player p) {
		p.occupy(nextSpawnPoint());
		players.add(p);
	}

	/**
	 * @return The next starting position for a player.
	 */
	private Square nextSpawnPoint() {
		List<Square> spawnPoints = board.getPlayerStartPositions();
		Square square = spawnPoints.get(spawnPointIndex);
		spawnPointIndex++;
		spawnPointIndex %= spawnPoints.size();
		return square;
	}

	@Override
	public void move(Occupant occupant, Direction direction) {
		synchronized (moveLock) {
			if (!isCompleted()) {
				Square square = occupant.getSquare();
				Square destination = square.getSquareAt(direction);

				boolean moved = occupant.occupy(destination);
				occupant.setDirection(direction);

				if (moved) {
					handleMove(occupant, destination);
				}
			}
		}
	}

	/**
	 * Handles the results of an occupant's move.
	 * 
	 * @param movedOccupant
	 *            The occupant that moved onto the square.
	 * @param destination
	 *            The destination square.
	 */
	private void handleMove(Occupant movedOccupant, Square destination) {
		doCollisions(destination, movedOccupant);

		if (isCompleted()) {
			notifyObserversOnCompletion();
		}
	}

	/**
	 * Handles all collisions a new occupant may run into.
	 * 
	 * @param destination
	 *            The square the new occupant entered.
	 * @param movedOccupant
	 *            The new occupant.
	 */
	private void doCollisions(Square destination, Occupant movedOccupant) {
		for (Occupant o : destination.getOccupants()) {
			collide(movedOccupant, o);
		}
	}

	/**
	 * Handles the collision between two occupants trying to occupy the same
	 * square.
	 * 
	 * @param collider
	 *            The new occupant.
	 * @param collidee
	 *            The existing occupant.
	 */
	private void collide(Occupant collider, Occupant collidee) {
		collisionsInteractions.handleCollision(collider, collidee);
	}

	/**
	 * Notify all observers that a player has died.
	 * 
	 * @param p
	 *            The player that died.
	 */
	private void notifyObserversOnDeath(Player p) {
		for (LevelObserver o : observers) {
			o.playerDied(p);
		}
	}

	/**
	 * Notifies all observers that this level has been completed.
	 */
	private void notifyObserversOnCompletion() {
		for (LevelObserver o : observers) {
			o.levelCompleted();
		}
	}

	@Override
	public boolean isCompleted() {
		if (allPlayersDead()) {
			return false;
		}
		return allPelletsConsumed();
	}

	/**
	 * @return <code>true</code> iff all pellets on the board have been
	 *         consumed.
	 */
	private boolean allPelletsConsumed() {
		// TODO get rid of instanceof check.
		// TODO very inefficient, think of something different (e.g. scan once,
		// subtract when needed.)
		Board b = getBoard();
		for (int x = 0; x < b.getWidth(); x++) {
			for (int y = 0; y < b.getHeight(); y++) {
				Square square = b.squareAt(x, y);
				for (Occupant o : square.getOccupants()) {
					if (o instanceof Pellet) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * @return <code>true</code> iff all participating players are dead.
	 */
	private boolean allPlayersDead() {
		boolean alldeath = true;
		for (Player p : players) {
			if (p.isAlive()) {
				alldeath = false;
				break;
			}
		}
		return alldeath;
	}

	@Override
	public void addObserver(LevelObserver observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(LevelObserver observer) {
		observers.remove(observer);
	}

}
