package nl.tudelft.jpacman.level;

import java.util.EnumMap;
import java.util.Map;

import nl.tudelft.jpacman.board.AbstractOccupant;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * An occupant with a directional sprite.
 * 
 * @author Jeroen Roosen <j.roosen@topdesk.com>
 */
public abstract class DirectionalOccupant extends AbstractOccupant {

	/**
	 * The map of sprites for each direction.
	 */
	private final Map<Direction, Sprite> sprites;

	/**
	 * Creates a new occupant with sprites for all directions.
	 * 
	 * @param spriteMap
	 *            The map of sprites for every direction.
	 */
	public DirectionalOccupant(Map<Direction, Sprite> spriteMap) {
		assert spriteMap != null;
		this.sprites = new EnumMap<>(spriteMap);
		assert sprites.size() == Direction.values().length;
	}

	@Override
	public Sprite getSprite() {
		return sprites.get(getDirection());
	}

}
