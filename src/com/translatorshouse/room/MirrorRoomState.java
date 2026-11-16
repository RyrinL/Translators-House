package com.translatorshouse.room;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.item.Item;
import com.translatorshouse.text.Messages;

import java.util.List;

public class MirrorRoomState implements Room {
    public String getId() { return "mirrorroom"; }

    public String describe(GameWorld world) { return Messages.ROOM_MIRROR_DESCRIPTION; }

    public List<String> availableExits(GameWorld world) { return List.of("out"); }

    public List<Item> visibleItems(GameWorld world) { return world.visibleItemsIn(getId()); }

    public void onEnter(GameWorld world) {}
    public void onExit(GameWorld world) {}

    public boolean canMoveTo(String exit, GameWorld world) {
        return exit.equals("out") || exit.equals("back") || exit.equals("south");
    }

    public String destinationFor(String exit, GameWorld world) {
        return canMoveTo(exit, world) ? "hallway" : "";
    }
}
