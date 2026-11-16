package com.translatorshouse.room;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.item.Item;
import com.translatorshouse.text.Messages;

import java.util.ArrayList;
import java.util.List;

public class GardenState implements Room {
    public String getId() { return "garden"; }

    public String describe(GameWorld world) { return Messages.ROOM_GARDEN_DESCRIPTION; }

    public List<String> availableExits(GameWorld world) {
        List<String> exits = new ArrayList<>(List.of("south"));
        if (world.getBooleanFlag("gate_open")) exits.add("gate");
        return exits;
    }

    public List<Item> visibleItems(GameWorld world) { return world.visibleItemsIn(getId()); }

    public void onEnter(GameWorld world) {}
    public void onExit(GameWorld world) {}

    public boolean canMoveTo(String exit, GameWorld world) {
        if (exit.equals("south")) return true;
        if (exit.equals("gate")) {
            if (world.getBooleanFlag("gate_open")) return true;
            world.getConsole().println(Messages.GATE_LOCKED);
            return false;
        }
        return false;
    }

    public String destinationFor(String exit, GameWorld world) {
        if (exit.equals("south")) return "hallway";
        if (exit.equals("gate")) return "END";
        return "";
    }
}
