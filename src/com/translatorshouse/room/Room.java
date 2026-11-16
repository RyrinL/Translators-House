package com.translatorshouse.room;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.item.Item;

import java.util.List;

// STATE. The game's behavior changes as the current Room changes.
public interface Room {
    String getId();
    String describe(GameWorld world);
    List<String> availableExits(GameWorld world);
    List<Item> visibleItems(GameWorld world);
    void onEnter(GameWorld world);
    void onExit(GameWorld world);
    boolean canMoveTo(String exit, GameWorld world);
    // Destination room id for an exit, or "END" to finish the game, or "" if none
    String destinationFor(String exit, GameWorld world);
}
