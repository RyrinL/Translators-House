package com.translatorshouse.room;

/** Maps a room id to a fresh State object — used on entry and when restoring a snapshot. */
public final class RoomRegistry {
    private RoomRegistry() {}

    public static Room create(String id) {
        return switch (id) {
            case "bedroom"    -> new BedroomState();
            case "hallway"    -> new HallwayState();
            case "kitchen"    -> new KitchenState();
            case "library"    -> new LibraryState();
            case "mirrorroom" -> new MirrorRoomState();
            case "garden"     -> new GardenState();
            default -> throw new IllegalArgumentException("Unknown room: " + id);
        };
    }
}
