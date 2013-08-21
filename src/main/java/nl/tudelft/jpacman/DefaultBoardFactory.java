package nl.tudelft.jpacman;

import java.util.List;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.OpenSquare;
import nl.tudelft.jpacman.board.Pellet;
import nl.tudelft.jpacman.board.SmallPellet;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.WallSquare;
import nl.tudelft.jpacman.sprite.PacManSprites;

/**
 * Board factory that provides default implementations of the board elements.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public class DefaultBoardFactory implements BoardFactory {

	/**
	 * The sprite store that will provide the sprites for the elements.
	 */
	private final PacManSprites sprites;

	/**
	 * Creates a new factory that will create elements for the board.
	 * 
	 * @param spriteStore
	 *            The sprite store that will provide sprites for the elements.
	 */
	public DefaultBoardFactory(PacManSprites spriteStore) {
		this.sprites = spriteStore;
	}

	@Override
	public Square newEmptySquare() {
		return new OpenSquare(sprites.getFloorSprite());
	}

	@Override
	public Square newWall() {
		return new WallSquare(sprites.getWallSprite());
	}

	@Override
	public Pellet newPellet() {
		return new SmallPellet(sprites.getPelletSprite());
	}

	@Override
	public Board newBoard(Square[][] grid, List<Square> playerStartPositions,
			List<Square> ghostStartPositions) {
		return new Board(grid, playerStartPositions, ghostStartPositions);
	}

}
