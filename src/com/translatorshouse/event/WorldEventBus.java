package com.translatorshouse.event;

import com.translatorshouse.core.GameWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Observer subject. Listeners subscribe by event type; publish notifies all of that type.
public class WorldEventBus {
    private final Map<Class<? extends WorldEvent>, List<EventListener<?>>> listeners = new HashMap<>();

    public <E extends WorldEvent> void subscribe(Class<E> type, EventListener<E> listener) {
        listeners.computeIfAbsent(type, k -> new ArrayList<>()).add(listener);
    }

    @SuppressWarnings("unchecked")
    public <E extends WorldEvent> void publish(E event) {
        List<EventListener<?>> subs = listeners.getOrDefault(event.getClass(), List.of());
        for (EventListener<?> l : subs) {
            ((EventListener<E>) l).onEvent(event, GameWorld.getInstance());
        }
    }
}
