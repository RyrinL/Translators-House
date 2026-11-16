package com.translatorshouse.core;

import com.translatorshouse.event.*;
import com.translatorshouse.item.Container;
import com.translatorshouse.item.Inventory;
import com.translatorshouse.item.Item;
import com.translatorshouse.item.ItemFactory;
import com.translatorshouse.puzzle.FreezerPuzzle;
import com.translatorshouse.puzzle.LibraryRiddlePuzzle;
import com.translatorshouse.puzzle.MirrorRoomPuzzle;
import com.translatorshouse.persistence.SaveFormat;
import com.translatorshouse.room.Room;
import com.translatorshouse.room.RoomRegistry;
import com.translatorshouse.text.Messages;
import com.translatorshouse.util.ConsoleIO;

import java.io.IOException;
import java.time.Instant;
import java.util.*;


//  SINGLETON. The one shared mutable world: current room, player, event bus, flags, containers,
//  the undo stack, and the puzzles. Also the Memento ORIGINATOR (snapshot / restore).

public final class GameWorld {
    private static GameWorld instance;

    private Room currentRoom;
    private final Player player = new Player();
    private final WorldEventBus eventBus = new WorldEventBus();
    private final Map<String, Object> flags = new HashMap<>();
    private final Map<String, Container> containers = new LinkedHashMap<>();
    private final Deque<GameSnapshot> undoStack = new ArrayDeque<>();
    private final List<String> journalEntries = new ArrayList<>();
    private boolean over = false;
    private ConsoleIO console;

    private FreezerPuzzle freezerPuzzle;
    private LibraryRiddlePuzzle libraryPuzzle;
    private MirrorRoomPuzzle mirrorPuzzle;

    private static final int UNDO_CAP = 20;
    private static final List<String> ROOM_IDS =
            List.of("bedroom", "hallway", "kitchen", "library", "mirrorroom", "garden");

    private GameWorld() {}

    public static GameWorld getInstance() {
        if (instance == null) instance = new GameWorld();
        return instance;
    }

    /** Reset for tests / fresh game (not strictly needed for the single-session demo). */
    public static void reset() { instance = null; }

    public void initialize() {
        setupWorld();
        this.freezerPuzzle = new FreezerPuzzle();
        this.libraryPuzzle = new LibraryRiddlePuzzle();
        this.mirrorPuzzle = new MirrorRoomPuzzle();
        registerDefaultListeners();
        this.currentRoom = RoomRegistry.create("bedroom");
        this.currentRoom.onEnter(this);
    }

    private void setupWorld() {
        // room "floor" containers, always open
        for (String id : ROOM_IDS) {
            containers.put(id, new Container(id, id, "the room", true, false));
        }
        floor("bedroom").add(ItemFactory.create("journal"));

        floor("hallway").add(ItemFactory.create("portrait"));

        Container freezer = (Container) ItemFactory.create("freezer");
        freezer.setOpen(true); // you can see the key through the frost
        freezer.add(ItemFactory.create("frozen-key"));
        containers.put("freezer", freezer);
        floor("kitchen").add(ItemFactory.create("matchbook"));
        floor("kitchen").add(ItemFactory.create("dishtowel"));
        floor("kitchen").add(ItemFactory.create("teapot"));
        floor("kitchen").add(freezer);

        Container shelf = (Container) ItemFactory.create("library-shelf");
        shelf.add(ItemFactory.create("diary-1"));
        shelf.add(ItemFactory.create("diary-2"));
        shelf.add(ItemFactory.create("diary-3"));
        containers.put("library-shelf", shelf);
        Container drawer = (Container) ItemFactory.create("library-drawer");
        drawer.add(ItemFactory.create("small-mirror"));
        containers.put("library-drawer", drawer);
        floor("library").add(shelf);
        floor("library").add(drawer);

        floor("mirrorroom").add(ItemFactory.create("reflection-left"));
        floor("mirrorroom").add(ItemFactory.create("reflection-center"));
        floor("mirrorroom").add(ItemFactory.create("reflection-right"));

        floor("garden").add(ItemFactory.create("fountain"));
        floor("garden").add(ItemFactory.create("gate"));
    }

    private void registerDefaultListeners() {
        eventBus.subscribe(PortraitExaminedEvent.class, (e, w) -> {
            if (e.getCount() >= 2 && !w.getBooleanFlag("mirror_revealed")) {
                w.setFlag("mirror_revealed", true);
                w.getConsole().println(Messages.PORTRAIT_SECOND);
                w.addJournalEntry("A hidden room behind the portrait.");
            }
        });
        eventBus.subscribe(KeyObtainedEvent.class, (e, w) ->
                w.addJournalEntry("Found the brass key in the kitchen ice."));
        eventBus.subscribe(PuzzleSolvedEvent.class, (e, w) ->
                w.addJournalEntry("Solved: " + e.getPuzzleId() + "."));
        eventBus.subscribe(GameEndEvent.class, (e, w) -> {
            w.getConsole().println(Messages.ENDING);
            w.setOver(true);
        });
    }

    // ---- accessors ----
    public Room getCurrentRoom() { return currentRoom; }
    public Player getPlayer() { return player; }
    public WorldEventBus getEventBus() { return eventBus; }
    public ConsoleIO getConsole() { return console; }
    public void setConsole(ConsoleIO c) { this.console = c; }
    public boolean isOver() { return over; }
    public void setOver(boolean o) { this.over = o; }
    public List<String> getJournalEntries() { return List.copyOf(journalEntries); }

    public FreezerPuzzle freezerPuzzle() { return freezerPuzzle; }
    public LibraryRiddlePuzzle libraryPuzzle() { return libraryPuzzle; }
    public MirrorRoomPuzzle mirrorPuzzle() { return mirrorPuzzle; }

    // ---- flags ----
    public void setFlag(String key, Object value) { flags.put(key, value); }
    public boolean hasFlag(String key) { return flags.containsKey(key); }
    public boolean getBooleanFlag(String key) { return Boolean.TRUE.equals(flags.get(key)); }
    public int getIntFlag(String key) {
        Object v = flags.get(key);
        return v instanceof Integer i ? i : 0;
    }
    public String getStringFlag(String key) {
        Object v = flags.get(key);
        return v == null ? "" : v.toString();
    }

    // ---- containers / items ----
    public Container floor(String roomId) { return containers.get(roomId); }
    public Container container(String id) { return containers.get(id); }

    public List<Item> visibleItemsIn(String roomId) {
        return floor(roomId).getAllContents();
    }

    public Item findItemInRoom(String id) {
        return floor(currentRoom.getId()).find(id);
    }

    public boolean removeItemFromRoom(String id) {
        return floor(currentRoom.getId()).removeRecursive(id);
    }

    public void addItemToRoom(String roomId, Item item) {
        floor(roomId).add(item);
    }

    public void addJournalEntry(String entry) {
        journalEntries.add(entry);
        if (console != null) console.println(Messages.JOURNAL_ENTRY_PREFIX + entry);
    }

    // ---- movement ----
    public void moveTo(String roomId) {
        currentRoom.onExit(this);
        currentRoom = RoomRegistry.create(roomId);
        currentRoom.onEnter(this);
    }

    // ---- MEMENTO: snapshot / restore ----
    public GameSnapshot createSnapshot() {
        List<String> inv = new ArrayList<>();
        for (Item i : player.getInventory().topLevel()) inv.add(i.getId());

        Map<String, Boolean> open = new LinkedHashMap<>();
        Map<String, List<String>> contents = new LinkedHashMap<>();
        for (Map.Entry<String, Container> e : containers.entrySet()) {
            open.put(e.getKey(), e.getValue().isOpen());
            List<String> ids = new ArrayList<>();
            for (Item i : e.getValue().getAllContents()) ids.add(i.getId());
            contents.put(e.getKey(), ids);
        }
        return new GameSnapshot(currentRoom.getId(), flags, inv, open, contents,
                journalEntries, Instant.now());
    }

    public void snapshotForUndo() {
        undoStack.push(createSnapshot());
        while (undoStack.size() > UNDO_CAP) undoStack.pollLast();
    }

    public void restoreFromSnapshot(GameSnapshot s) {
        // flags
        flags.clear();
        flags.putAll(s.flags());
        // containers
        for (Map.Entry<String, List<String>> e : s.containerContents().entrySet()) {
            Container c = containers.get(e.getKey());
            if (c == null) continue;
            c.clear();
            for (String id : e.getValue()) {
                Item child = containers.containsKey(id) ? containers.get(id) : ItemFactory.create(id);
                c.add(child);
            }
        }
        for (Map.Entry<String, Boolean> e : s.containerOpen().entrySet()) {
            Container c = containers.get(e.getKey());
            if (c == null) continue;
            c.setOpen(e.getValue());
            if (e.getValue()) c.unlock();
        }
        // inventory
        player.getInventory().clear();
        for (String id : s.inventoryIds()) player.getInventory().add(ItemFactory.create(id));
        // journal
        journalEntries.clear();
        journalEntries.addAll(s.journalEntries());
        // room
        currentRoom = RoomRegistry.create(s.currentRoomId());
    }

    /** UNDO: discard the snapshot taken before the undo command, restore the one before that. */
    public boolean undo() {
        if (undoStack.size() < 2) return false;
        undoStack.pop();                 // pre-undo state (discard)
        restoreFromSnapshot(undoStack.peek());
        return true;
    }

    /** Memento-backed dream-fade: restore the pre-command state, then bump the player to the bedroom. */
    public void dreamFade() {
        if (!undoStack.isEmpty()) restoreFromSnapshot(undoStack.peek());
        currentRoom = RoomRegistry.create("bedroom");
    }

    // ---- MEMENTO use B: save-to-file / load-from-file ----
    public void saveGame(String path) throws IOException {
        GameSnapshot s = createSnapshot();
        Map<String, String> f = new LinkedHashMap<>();
        f.put("version", "1");
        f.put("saved_at", s.getSavedAt().toString());
        f.put("current_room", s.currentRoomId());
        for (Map.Entry<String, Object> e : s.flags().entrySet()) {
            f.put("flag." + e.getKey(), String.valueOf(e.getValue()));
        }
        f.put("inventory", String.join(",", s.inventoryIds()));
        for (Map.Entry<String, Boolean> e : s.containerOpen().entrySet()) {
            f.put("container." + e.getKey() + ".open", String.valueOf(e.getValue()));
            f.put("container." + e.getKey() + ".contents",
                    String.join(",", s.containerContents().getOrDefault(e.getKey(), List.of())));
        }
        f.put("journal", String.join(" || ", s.journalEntries()));
        SaveFormat.write(path, f);
    }

    public void loadGame(String path) throws IOException {
        Map<String, String> f = SaveFormat.read(path);

        Map<String, Object> loadedFlags = new LinkedHashMap<>();
        Map<String, Boolean> open = new LinkedHashMap<>();
        Map<String, List<String>> contents = new LinkedHashMap<>();
        for (Map.Entry<String, String> e : f.entrySet()) {
            String k = e.getKey(), v = e.getValue();
            if (k.startsWith("flag.")) {
                loadedFlags.put(k.substring(5), parseValue(v));
            } else if (k.startsWith("container.") && k.endsWith(".open")) {
                open.put(k.substring(10, k.length() - 5), Boolean.parseBoolean(v));
            } else if (k.startsWith("container.") && k.endsWith(".contents")) {
                contents.put(k.substring(10, k.length() - 9), splitCsv(v));
            }
        }
        List<String> inv = splitCsv(f.getOrDefault("inventory", ""));
        List<String> journal = f.getOrDefault("journal", "").isBlank()
                ? List.of() : Arrays.asList(f.get("journal").split(" \\|\\| "));
        Instant when = parseInstant(f.get("saved_at"));

        GameSnapshot s = new GameSnapshot(f.getOrDefault("current_room", "bedroom"),
                loadedFlags, inv, open, contents, journal, when);
        restoreFromSnapshot(s);
    }

    private static List<String> splitCsv(String v) {
        if (v == null || v.isBlank()) return List.of();
        return new ArrayList<>(Arrays.asList(v.split(",")));
    }

    private static Object parseValue(String v) {
        if (v.equals("true") || v.equals("false")) return Boolean.parseBoolean(v);
        if (v.matches("-?\\d+")) return Integer.parseInt(v);
        return v;
    }

    private static Instant parseInstant(String v) {
        try { return Instant.parse(v); } catch (Exception e) { return Instant.now(); }
    }
}
