package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.level.Direction;

/**
 * Abstract implementation of the occupant interface to ease future
 * implementation.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public abstract class AbstractOccupant implements Occupant {

	/**
	 * The square this occupant is currently occupying, or <code>null</code> if
	 * this occupant hasn't been placed on a board.
	 */
	private Square base;

	/**
	 * The direction this occupant is currently facing.
	 */
	private Direction direction;

	/**
	 * Creates a new occupant facing west.
	 */
	public AbstractOccupant() {
		this.direction = Direction.WEST;
	}

	/**
	 * @return <code>true</code> iff the square this occupant is occupying
	 *         indeed contains this occupant.
	 */
	protected boolean invariant() {
		Square square = getSquare();
		if (square != null) {
			return square.getOccupants().contains(this);
		}
		return true;
	}

	@Override
	public boolean occupy(Square square) {
		assert square != null;
		boolean result = false;
		if (square.isAccessibleTo(this)) {
			leaveCurrentSquare();
			square.put(this);
			base = square;
			result = true;
		}
		assert invariant();
		return result;
	}

	@Override
	public void leaveCurrentSquare() {
		Square currentBase = getSquare();
		if (currentBase != null) {
			currentBase.remove(this);
			currentBase = null;
		}
		assert invariant();
	}

	@Override
	public Square getSquare() {
		return base;
	}

	@Override
	public Direction getDirection() {
		return direction;
	}

	@Override
	public void setDirection(Direction dir) {
		direction = dir;
	}
}
