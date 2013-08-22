package nl.tudelft.jpacman.level;

import java.util.Map;
import java.util.Random;

import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * A ghost.
 * 
 * @author Jeroen Roosen <j.roosen@topdesk.com>
 */
public class Ghost extends DirectionalOccupant {

	/**
	 * Creates a new Ghost.
	 * 
	 * @param spriteMap
	 *            The sprites of this ghost.
	 */
	public Ghost(Map<Direction, Sprite> spriteMap) {
		super(spriteMap);
	}

	/**
	 * Determines the next move this ghost will make. By default this method
	 * will return a random direction the ghost can move to (i.e. not a wall
	 * square.) If no moves are available this method will return
	 * <code>null</code>.
	 * 
	 * @return The direction this ghost wants to move in next, or
	 *         <code>null</code> if no direction could be determined.
	 */
	public Direction nextMove() {
		Square square = getSquare();
		Random r = new Random();
		Direction[] directions = Direction.values();
		int directionAmount = directions.length;
		int i = r.nextInt(directionAmount);
		for (int j = 0; j < directionAmount; j++) {
			int p = (i + j) % directionAmount;
			Direction d = directions[p];
			if (square.getSquareAt(d).isAccessibleTo(this)) {
				return d;
			}
			j++;
		}
		return null;
	}

}
