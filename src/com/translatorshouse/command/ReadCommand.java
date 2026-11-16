package com.translatorshouse.command;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.item.Item;
import com.translatorshouse.puzzle.PuzzleResult;
import com.translatorshouse.text.Messages;

public class ReadCommand implements Command {
    private final String itemId;

    public ReadCommand(String itemId) { this.itemId = itemId == null ? "" : itemId; }

    public void execute(GameWorld world) {
        if (itemId.equals("journal")) {
            readJournal(world);
            return;
        }
        if (itemId.startsWith("diary-")) {
            readDictionary(world);
            return;
        }
        if (itemId.equals("riddle-scroll")) {
            Item scroll = locate(world);
            world.getConsole().println(scroll != null ? scroll.getDescription() : Messages.CANT_FIND_ITEM);
            return;
        }
        Item item = locate(world);
        world.getConsole().println(item != null ? item.getDescription() : Messages.CANT_FIND_ITEM);
    }

    private void readJournal(GameWorld world) {
        world.getConsole().println(Messages.JOURNAL_INTRO);
        for (String entry : world.getJournalEntries()) {
            world.getConsole().println(Messages.JOURNAL_ENTRY_PREFIX + entry);
        }
        boolean firstRead = !world.getBooleanFlag("journal_read");
        world.setFlag("journal_read", true);
        if (firstRead && world.getCurrentRoom().getId().equals("bedroom")) {
            world.getConsole().println(Messages.BEDROOM_DOOR_OPENS);
        }
    }

    private void readDictionary(GameWorld world) {
        if (locate(world) == null) {
            world.getConsole().println(Messages.CANT_FIND_ITEM);
            return;
        }
        world.getConsole().println(Messages.DICT_READ);
        String seq = world.getStringFlag("lib_sequence");
        seq = seq.isEmpty() ? itemId : seq + "," + itemId;
        world.setFlag("lib_sequence", seq);
        PuzzleResult r = world.libraryPuzzle().attempt(seq, world);
        if (r.isSolved()) {
            world.getConsole().println(r.message());
        }
    }

    private Item locate(GameWorld world) {
        Item item = world.findItemInRoom(itemId);
        if (item == null) item = world.getPlayer().getInventory().get(itemId);
        return item;
    }

    public String describe() { return "read " + itemId; }
}
