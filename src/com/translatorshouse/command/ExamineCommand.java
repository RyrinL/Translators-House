package com.translatorshouse.command;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.event.PortraitExaminedEvent;
import com.translatorshouse.item.Item;
import com.translatorshouse.text.Messages;

public class ExamineCommand implements Command {
    private final String target;

    public ExamineCommand(String target) { this.target = target == null ? "" : target; }

    public void execute(GameWorld world) {
        switch (target) {
            case "portrait" -> examinePortrait(world);
            case "fountain" -> world.getConsole().println(Messages.FOUNTAIN);
            case "journal"  -> world.getConsole().println(Messages.JOURNAL_INTRO);
            default -> examineGeneric(world);
        }
    }

    private void examinePortrait(GameWorld world) {
        if (!world.getCurrentRoom().getId().equals("hallway")) {
            world.getConsole().println(Messages.CANT_FIND_ITEM);
            return;
        }
        int count = world.getIntFlag("portrait_examined_count") + 1;
        world.setFlag("portrait_examined_count", count);
        if (count == 1) {
            world.getConsole().println(Messages.PORTRAIT_FIRST);
        } else if (count > 2) {
            world.getConsole().println(Messages.PORTRAIT_AFTER);
        }
        // the listener reveals the hidden door on the second look
        world.getEventBus().publish(new PortraitExaminedEvent(count));
    }

    private void examineGeneric(GameWorld world) {
        Item item = world.findItemInRoom(target);
        if (item == null) item = world.getPlayer().getInventory().get(target);
        if (item == null) {
            world.getConsole().println(Messages.CANT_FIND_ITEM);
            return;
        }
        world.getConsole().println(item.getDescription());
    }

    public String describe() { return "examine " + target; }
}
