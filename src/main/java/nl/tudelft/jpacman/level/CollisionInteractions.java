package nl.tudelft.jpacman.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.tudelft.jpacman.board.Occupant;

/**
 * Handles collisions based on a provided set.
 * 
 * @author Michael de Jong <m.dejong-2@student.tudelft.nl>
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public class CollisionInteractions {

	/**
	 * The collection of collision handlers.
	 */
	private final Map<Class<? extends Occupant>, Map<Class<? extends Occupant>, CollisionHandler<?, ?>>> handlers;

	/**
	 * Creates a new, empty collision interactions object.
	 */
	public CollisionInteractions() {
		this.handlers = new HashMap<Class<? extends Occupant>, Map<Class<? extends Occupant>, CollisionHandler<?, ?>>>();
	}

	/**
	 * Adds a collision interaction to this collection.
	 * 
	 * @param collider
	 *            The collider type.
	 * @param collidee
	 *            The collidee type.
	 * @param handler
	 *            The handler that handles the collision.
	 */
	public <C1 extends Occupant, C2 extends Occupant> void onCollision(
			Class<C1> collider, Class<C2> collidee,
			CollisionHandler<C1, C2> handler) {
		onCollision(collider, collidee, true, handler);
	}

	/**
	 * Adds a collision interaction to this collection.
	 * 
	 * @param collider
	 *            The collider type.
	 * @param collidee
	 *            The collidee type.
	 * @param symetric
	 *            Whether this collision is symmetric or not, i.e. it can be
	 *            used both ways.
	 * @param handler
	 *            The handler that handles the collision.
	 */
	public <C1 extends Occupant, C2 extends Occupant> void onCollision(
			Class<C1> collider, Class<C2> collidee, boolean symetric,
			CollisionHandler<C1, C2> handler) {
		addHandler(collider, collidee, handler);
		if (symetric) {
			addHandler(collidee, collider, new InverseCollisionHandler<C2, C1>(
					handler));
		}
	}

	/**
	 * Adds the collision interaction..
	 * 
	 * @param collider
	 *            The collider type.
	 * @param collidee
	 *            The collidee type.
	 * @param handler
	 *            The handler that handles the collision.
	 */
	private void addHandler(Class<? extends Occupant> collider,
			Class<? extends Occupant> collidee, CollisionHandler<?, ?> handler) {
		if (!handlers.containsKey(collider)) {
			handlers.put(
					collider,
					new HashMap<Class<? extends Occupant>, CollisionHandler<?, ?>>());
		}

		Map<Class<? extends Occupant>, CollisionHandler<?, ?>> map = handlers
				.get(collider);
		map.put(collidee, handler);
	}

	/**
	 * Handles the collision between two colliding parties, if a suitable
	 * collision handler is listed.
	 * 
	 * @param collider
	 *            The collider.
	 * @param collidee
	 *            The collidee.
	 */
	@SuppressWarnings("unchecked")
	public <C1 extends Occupant, C2 extends Occupant> void handleCollision(
			C1 collider, C2 collidee) {
		Class<? extends Occupant> colliderKey = getMostSpecificClass(handlers,
				collider.getClass());
		if (colliderKey == null) {
			return;
		}

		Map<Class<? extends Occupant>, CollisionHandler<?, ?>> map = handlers
				.get(colliderKey);
		Class<? extends Occupant> collideeKey = getMostSpecificClass(map,
				collidee.getClass());
		if (collideeKey == null) {
			return;
		}

		CollisionHandler<C1, C2> collisionHandler = (CollisionHandler<C1, C2>) map
				.get(collideeKey);
		if (collisionHandler == null) {
			return;
		}

		collisionHandler.handleCollision(collider, collidee);
	}

	/**
	 * Figures out the most specific class that is listed in the map. I.e. if A
	 * extends B and B is listed while requesting A, then B will be returned.
	 * 
	 * @param map
	 *            The map with the key collection to find a matching class in.
	 * @param key
	 *            The class to search the most suitable key for.
	 * @return The most specific class from the key collection.
	 */
	private Class<? extends Occupant> getMostSpecificClass(
			Map<Class<? extends Occupant>, ?> map, Class<? extends Occupant> key) {
		List<Class<? extends Occupant>> collideeInheritance = getInheritance(key);
		for (Class<? extends Occupant> pointer : collideeInheritance) {
			if (map.containsKey(pointer)) {
				return pointer;
			}
		}
		return null;
	}

	/**
	 * Returns a list of all classes and interfaces the class inherits.
	 * 
	 * @param clazz
	 *            The class to create a list of super classes and interfaces
	 *            for.
	 * @return A list of all classes and interfaces the class inherits.
	 */
	@SuppressWarnings("unchecked")
	private List<Class<? extends Occupant>> getInheritance(
			Class<? extends Occupant> clazz) {
		List<Class<? extends Occupant>> found = new ArrayList<Class<? extends Occupant>>();
		found.add(clazz);

		int index = 0;
		while (found.size() > index) {
			Class<?> current = found.get(index);
			Class<?> superClass = current.getSuperclass();
			if (superClass != null
					&& Occupant.class.isAssignableFrom(superClass)) {
				found.add((Class<? extends Occupant>) superClass);
			}
			for (Class<?> classInterface : current.getInterfaces()) {
				if (Occupant.class.isAssignableFrom(classInterface)) {
					found.add((Class<? extends Occupant>) classInterface);
				}
			}
			index++;
		}

		return found;
	}

	/**
	 * Handles the collision between two colliding parties.
	 * 
	 * @author Michael de Jong <m.dejong-2@student.tudelft.nl>
	 * 
	 * @param <C1>
	 *            The collider type.
	 * @param <C2>
	 *            The collidee type.
	 */
	public static interface CollisionHandler<C1 extends Occupant, C2 extends Occupant> {

		/**
		 * Handles the collision between two colliding parties.
		 * 
		 * @param collider
		 *            The collider.
		 * @param collidee
		 *            The collidee.
		 */
		void handleCollision(C1 collider, C2 collidee);
	}

	/**
	 * An symmetrical copy of a collision hander.
	 * 
	 * @author Michael de Jong <m.dejong-2@student.tudelft.nl>
	 * 
	 * @param <C1>
	 *            The collider type.
	 * @param <C2>
	 *            The collidee type.
	 */
	private static class InverseCollisionHandler<C1 extends Occupant, C2 extends Occupant>
			implements CollisionHandler<C1, C2> {

		/**
		 * The handler of this collision.
		 */
		private final CollisionHandler<C2, C1> handler;

		/**
		 * Creates a new collision handler.
		 * 
		 * @param handler
		 *            The symmetric handler for this collision.
		 */
		public InverseCollisionHandler(CollisionHandler<C2, C1> handler) {
			this.handler = handler;
		}

		/**
		 * Handles this collision by flipping the collider and collidee, making
		 * it compatible with the initial collision.
		 */
		@Override
		public void handleCollision(C1 collider, C2 collidee) {
			handler.handleCollision(collidee, collider);
		}

	}

}
