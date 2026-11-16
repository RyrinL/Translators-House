package com.translatorshouse.core;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Memento. Captures the whole mutable game state immutably, without exposing internals.
 *  Used for two purposes: in-session undo, and save-to-file. */
public final class GameSnapshot {
    private final String currentRoomId;
    private final Map<String, Object> flags;
    private final List<String> inventoryIds;
    private final Map<String, Boolean> containerOpen;
    private final Map<String, List<String>> containerContents;
    private final List<String> journalEntries;
    private final Instant savedAt;

    public GameSnapshot(String currentRoomId,
                        Map<String, Object> flags,
                        List<String> inventoryIds,
                        Map<String, Boolean> containerOpen,
                        Map<String, List<String>> containerContents,
                        List<String> journalEntries,
                        Instant savedAt) {
        // defensive copies — the memento is immutable
        this.currentRoomId = currentRoomId;
        this.flags = new LinkedHashMap<>(flags);
        this.inventoryIds = List.copyOf(inventoryIds);
        this.containerOpen = new LinkedHashMap<>(containerOpen);
        Map<String, List<String>> cc = new LinkedHashMap<>();
        containerContents.forEach((k, v) -> cc.put(k, List.copyOf(v)));
        this.containerContents = cc;
        this.journalEntries = List.copyOf(journalEntries);
        this.savedAt = savedAt;
    }

    // package-private originator access (GameWorld restores); public only for the timestamp
    public Instant getSavedAt() { return savedAt; }
    String currentRoomId() { return currentRoomId; }
    Map<String, Object> flags() { return flags; }
    List<String> inventoryIds() { return inventoryIds; }
    Map<String, Boolean> containerOpen() { return containerOpen; }
    Map<String, List<String>> containerContents() { return containerContents; }
    List<String> journalEntries() { return journalEntries; }
}
