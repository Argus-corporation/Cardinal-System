package net.argus.event;

import net.argus.util.Listener;
import net.argus.util.ListenerManager;

public abstract class Event<T extends Listener> {
	
	private ListenerManager<T> listenerManager = new ListenerManager<T>();
	
	public void addListener(T listener) {listenerManager.addListener(listener);}
	public void removeListener(T listener) {listenerManager.removeListener(listener);}
	
	public void startEvent(int event, Object ... objs) {
		for(T lis : listenerManager)
			event(lis, event, objs);
	}
	
	public abstract void event(T listener, int event, Object ... objs);
	
}
