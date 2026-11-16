package com.translatorshouse.command;

import com.translatorshouse.core.GameWorld;
import com.translatorshouse.item.Inventory;
import com.translatorshouse.item.Item;
import com.translatorshouse.text.Messages;

/** Walks the inventory with its Iterator (over the Composite tree). */
public class InventoryCommand implements Command {

    public void execute(GameWorld world) {
        Inventory inv = world.getPlayer().getInventory();
        if (inv.isEmpty()) {
            world.getConsole().println(Messages.INVENTORY_EMPTY);
            return;
        }
        world.getConsole().println(Messages.INVENTORY_HEADER);
        for (Item item : inv) {  // InventoryIterator
            world.getConsole().println("  - " + item.getName());
        }
    }

    public String describe() { return "inventory"; }
}
