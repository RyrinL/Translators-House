package com.translatorshouse.room;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.item.Item;
import com.translatorshouse.text.Messages;

import java.util.List;

public class BedroomState implements Room {
    public String getId() { return "bedroom"; }

    public String describe(GameWorld world) { return Messages.ROOM_BEDROOM_DESCRIPTION; }

    public List<String> availableExits(GameWorld world) { return List.of("north"); }

    public List<Item> visibleItems(GameWorld world) { return world.visibleItemsIn(getId()); }

    public void onEnter(GameWorld world) {}
    public void onExit(GameWorld world) {}

    public boolean canMoveTo(String exit, GameWorld world) {
        if (exit.equals("north")) {
            if (world.getBooleanFlag("journal_read")) return true;
            world.getConsole().println(Messages.BEDROOM_DOOR_LOCKED);
            return false;
        }
        return false;
    }

    public String destinationFor(String exit, GameWorld world) {
        return exit.equals("north") ? "hallway" : "";
    }
}
