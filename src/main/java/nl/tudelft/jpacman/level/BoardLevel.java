package nl.tudelft.jpacman.level;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Occupant;
import nl.tudelft.jpacman.board.Pellet;
import nl.tudelft.jpacman.board.Square;

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
		this.observers = new ArrayList<>();
		this.spawnPointIndex = 0;
		this.players = new ArrayList<>();
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

				occupant.occupy(destination);
				occupant.setDirection(direction);

				Pellet pellet = destination.getPellet();
				if (pellet != null && occupant instanceof Player) { // TODO get rid of instanceof
					pellet.consumedBy((Player) occupant);
				}
				
				// TODO handle collisions.
				
				if (isCompleted()) {
					notifyObserversOnCompletion();
				}

			}
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
		// TODO this method is very inefficient, it would be better to do a
		// headcount at the start and keep track of the number.
		for (int y = 0; y < board.getHeight(); y++) {
			for (int x = 0; x < board.getWidth(); x++) {
				if (board.squareAt(x, y).getPellet() != null) {
					return false;
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
