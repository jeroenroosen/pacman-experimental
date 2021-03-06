package nl.tudelft.jpacman.board;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import nl.tudelft.jpacman.level.Direction;
import nl.tudelft.jpacman.sprite.Sprite;

import com.google.common.collect.ImmutableList;

/**
 * A square on a board.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public abstract class Square {

	/**
	 * The map containing the adjacent square for every direction.
	 */
	private final Map<Direction, Square> adjacentSquares;

	/**
	 * The occupants in order of occupation, with the first occupant as the
	 * first element in the list.
	 */
	private final List<Occupant> occupants;

	/**
	 * Creates a new square.
	 */
	public Square() {
		this.occupants = new ArrayList<>();
		this.adjacentSquares = new EnumMap<>(Direction.class);
	}

	/**
	 * @return <code>true</code> iff the occupants of this square all have this
	 *         square as the square they are occupying.
	 */
	protected boolean invariant() {
		for (Occupant o : getOccupants()) {
			if (o.getSquare() != this) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return An immutable list of occupants, in the order they occupied this
	 *         square.
	 */
	public List<Occupant> getOccupants() {
		return ImmutableList.copyOf(occupants);
	}

	/**
	 * Places an occupant on this square iff the square type allows for it, or
	 * does nothing if the occupant was already on this square. When a
	 * {@link Pellet} is added, it is placed at the bottom of the stack instead
	 * of on top.
	 * 
	 * @param occupant
	 *            The occupant to put on this square.
	 * @return <code>true</code> iff the occupant occupies this square.
	 */
	boolean put(Occupant occupant) {
		if (isAccessibleTo(occupant)) {
			if (!occupants.contains(occupant)) {
				// TODO replace instanceof
				if (occupant instanceof Pellet) {
					occupants.add(0, occupant);
				} else {
					occupants.add(occupant);
				}
			}
			assert invariant();
			return true;
		}
		return false;
	}

	/**
	 * Removes an occupant from this square, or does nothing if the occupant was
	 * not occupying this square.
	 * 
	 * @param occupant
	 *            The occupant to remove from this square.
	 * @return The occupant that was removed from this square, or
	 *         <code>null</code> if the occupant was not occupying this square.
	 */
	Occupant remove(Occupant occupant) {
		if (occupants.remove(occupant)) {
			assert invariant();
			return occupant;
		}
		assert invariant();
		return null;
	}

	/**
	 * Returns the adjacent square in the given direction, as seen from this
	 * square.
	 * 
	 * @param direction
	 *            The direction of the square.
	 * @return The adjacent square in the direction as seen from this square.
	 */
	public Square getSquareAt(Direction direction) {
		return adjacentSquares.get(direction);
	}

	/**
	 * Returns <code>true</code> iff this square may be occupied by the
	 * occupant.
	 * 
	 * @param occupant
	 *            The occupant that wants to occupy this square.
	 * @return <code>true</code> iff this square may be occupied by the
	 *         occupant.
	 */
	abstract public boolean isAccessibleTo(Occupant occupant);

	/**
	 * @return This square's graphical representation.
	 */
	abstract public Sprite getSprite();

	/**
	 * Attach an adjacent square to this square, eventually forming a connected
	 * graph of all squares on a board.
	 * 
	 * @param node
	 *            The adjacent square.
	 * @param edge
	 *            The direction the square is in as seen from this square.
	 */
	void attach(Square node, Direction edge) {
		adjacentSquares.put(edge, node);
	}

}
