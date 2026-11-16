package com.translatorshouse.room;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.item.Item;
import com.translatorshouse.text.Messages;

import java.util.List;

public class LibraryState implements Room {
    public String getId() { return "library"; }

    public String describe(GameWorld world) { return Messages.ROOM_LIBRARY_DESCRIPTION; }

    public List<String> availableExits(GameWorld world) { return List.of("east"); }

    public List<Item> visibleItems(GameWorld world) { return world.visibleItemsIn(getId()); }

    public void onEnter(GameWorld world) {}
    public void onExit(GameWorld world) {}

    public boolean canMoveTo(String exit, GameWorld world) { return exit.equals("east"); }

    public String destinationFor(String exit, GameWorld world) {
        return exit.equals("east") ? "hallway" : "";
    }
}
