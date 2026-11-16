package com.translatorshouse.event;

import com.translatorshouse.core.GameWorld;

/** Observer. Reacts to an event of type E. */
public interface EventListener<E extends WorldEvent> {
    void onEvent(E event, GameWorld world);
}
