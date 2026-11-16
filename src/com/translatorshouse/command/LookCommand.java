package com.translatorshouse.command;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.item.Item;
import com.translatorshouse.util.ConsoleIO;

import java.util.List;

public class LookCommand implements Command {

    public void execute(GameWorld world) {
        render(world);
    }

    /** Shared room renderer, also used after moving. */
    public static void render(GameWorld world) {
        ConsoleIO c = world.getConsole();
        c.blank();
        c.println(world.getCurrentRoom().describe(world));
        List<Item> items = world.getCurrentRoom().visibleItems(world);
        if (!items.isEmpty()) {
            StringBuilder sb = new StringBuilder("You see: ");
            for (int i = 0; i < items.size(); i++) {
                sb.append(items.get(i).getName());
                if (i < items.size() - 1) sb.append(", ");
            }
            c.println(sb.toString());
        }
        c.println("Exits: " + String.join(", ", world.getCurrentRoom().availableExits(world)));
    }

    public String describe() { return "look"; }
}
