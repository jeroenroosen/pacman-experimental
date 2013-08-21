package nl.tudelft.jpacman.board;

import java.util.List;

import nl.tudelft.jpacman.level.Direction;

import com.google.common.collect.ImmutableList;

/**
 * A grid of squares that make up the game board.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public class Board {

	/**
	 * The internal grid.
	 */
	private final Square[][] grid;

	/**
	 * The list of player starting squares.
	 */
	private final List<Square> players;

	/**
	 * The list of ghost starting squares.
	 */
	private final List<Square> ghosts;

	/**
	 * Creates a new board.
	 * 
	 * @param squares
	 *            The grid of this board.
	 * @param playerStartPositions
	 *            The player starting squares.
	 * @param ghostStartPositions
	 *            The ghost starting squares.
	 */
	public Board(Square[][] squares, List<Square> playerStartPositions,
			List<Square> ghostStartPositions) {
		this.grid = squares;
		this.players = playerStartPositions;
		this.ghosts = ghostStartPositions;
	}

	/**
	 * Connects all the {@link Square} on this board, creating a connected
	 * graph of all cells.
	 */
	void connectGrid() {
		int w = getWidth();
		int h = getHeight();

		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				Square square = squareAt(x, y);
				for (Direction dir : Direction.values()) {
					int dirX = (w + x + dir.getDeltaX()) % w;
					int dirY = (h + y + dir.getDeltaY()) % h;

					Square neighbour = squareAt(dirX, dirY);
					square.attach(neighbour, dir);
				}
			}
		}
	}

	/**
	 * @return The amount of squares on the x axis.
	 */
	public int getWidth() {
		return grid.length;
	}

	/**
	 * @return The amount of squares on the y axis.
	 */
	public int getHeight() {
		return grid[0].length;
	}

	/**
	 * Returns the square at a specific coordinate.
	 * 
	 * @param x
	 *            The x position in the grid.
	 * @param y
	 *            The y position in the grid.
	 * @return The square at (x,y).
	 */
	public Square squareAt(int x, int y) {
		return grid[x][y];
	}

	/**
	 * @return An immutable list of possible starting squares for players.
	 */
	public List<Square> getPlayerStartPositions() {
		return ImmutableList.copyOf(players);
	}

	/**
	 * @return An immutable list of possible starting squares for ghosts.
	 */
	public List<Square> getGhostStartPositions() {
		return ImmutableList.copyOf(ghosts);
	}

}
