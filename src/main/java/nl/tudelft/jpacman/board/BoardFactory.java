package nl.tudelft.jpacman.board;

import java.util.List;

/**
 * Factory that creates all elements for boards.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public interface BoardFactory {

	/**
	 * Creates a new floor tile.
	 * 
	 * @return A new instance of an open square.
	 */
	Square newEmptySquare();

	/**
	 * Creates a new wall tile.
	 * 
	 * @return A new instance of a closed wall square.
	 */
	Square newWall();

	/**
	 * Creates a new pellet.
	 * 
	 * @return A new instance of a pellet.
	 */
	Pellet newPellet();

	/**
	 * Create a new board.
	 * 
	 * @param grid
	 *            The grid of squares.
	 * @param playerStartPositions
	 *            The list of squares on which players may be positioned at the
	 *            start of a game.
	 * @param ghostStartPositions
	 *            The list of squares on which ghosts may be positioned at the
	 *            start of a game.
	 * @return A new board wrapping the grid of squares.
	 */
	Board newBoard(Square[][] grid, List<Square> playerStartPositions,
			List<Square> ghostStartPositions);

}
