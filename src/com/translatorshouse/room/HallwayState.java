package com.translatorshouse.room;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.item.Item;
import com.translatorshouse.text.Messages;

import java.util.ArrayList;
import java.util.List;

public class HallwayState implements Room {
    public String getId() { return "hallway"; }

    public String describe(GameWorld world) { return Messages.ROOM_HALLWAY_DESCRIPTION; }

    public List<String> availableExits(GameWorld world) {
        List<String> exits = new ArrayList<>(List.of("south", "east", "west", "north"));
        if (world.getBooleanFlag("mirror_revealed")) exits.add("mirror");
        return exits;
    }

    public List<Item> visibleItems(GameWorld world) { return world.visibleItemsIn(getId()); }

    public void onEnter(GameWorld world) {}
    public void onExit(GameWorld world) {}

    public boolean canMoveTo(String exit, GameWorld world) {
        return switch (exit) {
            case "south", "east", "west", "north" -> true;
            case "mirror" -> world.getBooleanFlag("mirror_revealed");
            default -> false;
        };
    }

    public String destinationFor(String exit, GameWorld world) {
        return switch (exit) {
            case "south" -> "bedroom";
            case "east" -> "kitchen";
            case "west" -> "library";
            case "north" -> "garden";
            case "mirror" -> "mirrorroom";
            default -> "";
        };
    }
}
