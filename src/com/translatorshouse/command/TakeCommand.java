package com.translatorshouse.command;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.event.KeyObtainedEvent;
import com.translatorshouse.item.Item;
import com.translatorshouse.puzzle.PuzzleResult;
import com.translatorshouse.text.Messages;

public class TakeCommand implements Command {
    private final String itemId;

    public TakeCommand(String itemId) { this.itemId = itemId == null ? "" : itemId; }

    public void execute(GameWorld world) {
        // taking a reflection in the Mirror Room is the mirror puzzle
        if (itemId.startsWith("reflection-")
                && world.getCurrentRoom().getId().equals("mirrorroom")) {
            PuzzleResult r = world.mirrorPuzzle().attempt(itemId, world);
            world.getConsole().println(r.message());
            if (r.isSolved()) {
                world.getConsole().println(Messages.TAKEN + "moonstone");
            } else if (r.isWrong()) {
                world.getConsole().println(Messages.MIRROR_WRONG);
                world.dreamFade();
            }
            return;
        }

        Item item = world.findItemInRoom(itemId);
        if (item == null) {
            world.getConsole().println(Messages.CANT_FIND_ITEM);
            return;
        }
        if (!item.isTakeable()) {
            world.getConsole().println(Messages.CANT_TAKE_THAT);
            return;
        }
        world.removeItemFromRoom(itemId);
        world.getPlayer().getInventory().add(item);
        world.getConsole().println(Messages.TAKEN + item.getName());

        if (itemId.equals("brass-key")) {
            world.setFlag("brass_key_obtained", true);
            world.getEventBus().publish(new KeyObtainedEvent("brass-key"));
        }
    }

    public String describe() { return "take " + itemId; }
}
