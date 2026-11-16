package com.translatorshouse.core;

import com.translatorshouse.item.Inventory;

/** The player: little more than a name for the inventory, by design. */
public class Player {
    private final Inventory inventory = new Inventory();

    public Inventory getInventory() { return inventory; }
}
