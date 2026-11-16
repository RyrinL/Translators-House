package com.translatorshouse.command;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.item.Inventory;
import com.translatorshouse.puzzle.PuzzleResult;
import com.translatorshouse.text.Messages;

public class UseCommand implements Command {
    private final String raw;

    public UseCommand(String raw) { this.raw = raw == null ? "" : raw.toLowerCase(); }

    public static UseCommand fromTokens(String[] args) {
        return new UseCommand(String.join(" ", args));
    }

    public void execute(GameWorld world) {
        Inventory inv = world.getPlayer().getInventory();

        // light the fireplace with the matchbook
        if (raw.contains("matchbook") && raw.contains("fireplace")) {
            if (world.getBooleanFlag("fireplace_lit")) {
                world.getConsole().println(Messages.FIREPLACE_ALREADY);
            } else if (inv.contains("matchbook")) {
                world.setFlag("fireplace_lit", true);
                inv.remove("matchbook"); // consumed
                world.getConsole().println(Messages.FIREPLACE_LIT);
            } else {
                world.getConsole().println(Messages.NO_MATCH);
            }
            return;
        }

        // melt the frozen key on the (lit) fireplace -> freezer puzzle
        if (raw.contains("frozen-key") && raw.contains("fireplace")) {
            if (!inv.contains("frozen-key")) {
                world.getConsole().println("You need to take the frozen key first.");
                return;
            }
            PuzzleResult r = world.freezerPuzzle().attempt(raw, world);
            world.getConsole().println(r.message());
            return;
        }

        // open the garden gate with brass key + moonstone
        if (raw.contains("gate")) {
            if (world.getBooleanFlag("gate_open")) {
                world.getConsole().println(Messages.GATE_OPENS);
            } else if (inv.contains("brass-key") && inv.contains("moonstone")) {
                world.setFlag("gate_open", true);
                world.getConsole().println(Messages.GATE_OPENS);
            } else {
                world.getConsole().println(Messages.GATE_LOCKED);
            }
            return;
        }

        world.getConsole().println(Messages.NOTHING_HAPPENS);
    }

    public String describe() { return "use " + raw; }
}
