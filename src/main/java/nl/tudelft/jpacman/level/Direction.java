package nl.tudelft.jpacman.level;

/**
 * Enumeration of north, south, east and west to denote directions elements are
 * facing on the board.
 * 
 * @author Jeroen Roosen
 */
public enum Direction {

	/**
	 * North, or up.
	 */
	NORTH(0, -1),

	/**
	 * South, or down.
	 */
	SOUTH(0, 1),

	/**
	 * West, or left.
	 */
	WEST(-1, 0),

	/**
	 * East, or right.
	 */
	EAST(1, 0);

	/**
	 * The delta x to an element in the direction in a matrix with 0,0 (x,y) as
	 * its topleft element.
	 */
	private final int dx;

	/**
	 * The delta y to an element in the direction in a matrix with 0,0 (x,y) as
	 * its topleft element.
	 */
	private final int dy;

	/**
	 * Creates a new Direction with the given parameters.
	 * 
	 * @param deltaX
	 *            The delta x to an element in the direction in a matrix with
	 *            0,0 (x,y) as its topleft element.
	 * @param deltaY
	 *            The delta y to an element in the direction in a matrix with
	 *            0,0 (x,y) as its topleft element.
	 */
	private Direction(int deltaX, int deltaY) {
		this.dx = deltaX;
		this.dy = deltaY;
	}

	/**
	 * @return The delta x for a single step in this direction, in a matrix with
	 *         0,0 (x,y) as its topleft element.
	 */
	public int getDeltaX() {
		return dx;
	}

	/**
	 * @return The delta y for a single step in this direction, in a matrix with
	 *         0,0 (x,y) as its topleft element.
	 */
	public int getDeltaY() {
		return dy;
	}
}