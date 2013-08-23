package nl.tudelft.jpacman.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.tudelft.jpacman.board.Occupant;

public class CollisionInteractions {
	
	private final Map<Class<? extends Occupant>, Map<Class<? extends Occupant>, CollisionHandler<?, ?>>> handlers;
	
	public CollisionInteractions() {
		this.handlers = new HashMap<Class<? extends Occupant>, Map<Class<? extends Occupant>, CollisionHandler<?, ?>>>();
	}
	
	public <C1 extends Occupant, C2 extends Occupant> void onCollision(Class<C1> collider, Class<C2> collidee, CollisionHandler<C1, C2> handler) {
		onCollision(collider, collidee, true, handler);
	}
	
	public <C1 extends Occupant, C2 extends Occupant> void onCollision(Class<C1> collider, Class<C2> collidee, boolean symetric, CollisionHandler<C1, C2> handler) {
		addHandler(collider, collidee, handler);
		if (symetric) {
			addHandler(collidee, collider, new InverseCollisionHandler<C2, C1>(handler));
		}
	}

	private void addHandler(Class<? extends Occupant> collider, Class<? extends Occupant> collidee, CollisionHandler<?, ?> handler) {
		if (!handlers.containsKey(collider)) {
			handlers.put(collider, new HashMap<Class<? extends Occupant>, CollisionHandler<?, ?>>());
		}
		
		Map<Class<? extends Occupant>, CollisionHandler<?, ?>> map = handlers.get(collider);
		map.put(collidee, handler);
	}
	
	@SuppressWarnings("unchecked")
	public <C1 extends Occupant, C2 extends Occupant> void handleCollision(C1 collider, C2 collidee) {
		Class<? extends Occupant> colliderKey = getMostSpecificClass(handlers, collider.getClass());
		if (colliderKey == null) {
			return;
		}
		
		Map<Class<? extends Occupant>, CollisionHandler<?, ?>> map = handlers.get(colliderKey);
		Class<? extends Occupant> collideeKey = getMostSpecificClass(map, collidee.getClass());
		if (collideeKey == null) {
			return;
		}
		
		CollisionHandler<C1, C2> collisionHandler = (CollisionHandler<C1, C2>) map.get(collideeKey);
		if (collisionHandler == null) {
			return;
		}
		
		collisionHandler.handleCollision(collider, collidee);
	}
	
	private Class<? extends Occupant> getMostSpecificClass(Map<Class<? extends Occupant>, ?> map, Class<? extends Occupant> key) {
		List<Class<? extends Occupant>> collideeInheritance = getInheritance(key);
		for (Class<? extends Occupant> pointer : collideeInheritance) {
			if (map.containsKey(pointer)) {
				return pointer;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private List<Class<? extends Occupant>> getInheritance(Class<? extends Occupant> clazz) {
		List<Class<? extends Occupant>> found = new ArrayList<Class<? extends Occupant>>();
		found.add(clazz);
		
		int index = 0;
		while (found.size() > index) {
			Class<?> current = found.get(index);
			Class<?> superClass = current.getSuperclass();
			if (superClass != null && Occupant.class.isAssignableFrom(superClass)) {
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
	
	public static interface CollisionHandler<C1 extends Occupant, C2 extends Occupant> {
		void handleCollision(C1 collider, C2 collidee);
	}
	
	private static class InverseCollisionHandler<C1 extends Occupant, C2 extends Occupant> implements CollisionHandler<C1, C2> {
		
		private final CollisionHandler<C2, C1> handler;
		
		public InverseCollisionHandler(CollisionHandler<C2, C1> handler) {
			this.handler = handler;
		}

		@Override
		public void handleCollision(C1 collider, C2 collidee) {
			handler.handleCollision(collidee, collider);
		}
		
	}
	
}
