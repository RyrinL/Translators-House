package com.translatorshouse.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** Composite root for the player's carried items. Iterable, so it can be walked uniformly. */
public class Inventory implements Item, Iterable<Item> {
    private final List<Item> items = new ArrayList<>();

    public void add(Item item) { items.add(item); }

    public boolean remove(String itemId) {
        return items.removeIf(i -> i.getId().equals(itemId));
    }

    public boolean contains(String itemId) {
        for (Item i : items) {
            if (i.getId().equals(itemId)) return true;
        }
        return false;
    }

    public Item get(String itemId) {
        for (Item i : items) {
            if (i.getId().equals(itemId)) return i;
        }
        return null;
    }

    public List<Item> topLevel() { return List.copyOf(items); }

    public void clear() { items.clear(); }

    public boolean isEmpty() { return items.isEmpty(); }

    @Override
    public Iterator<Item> iterator() { return new InventoryIterator(this); }

    public String getId() { return "inventory"; }
    public String getName() { return "inventory"; }
    public String getDescription() { return "the things you carry"; }
    public boolean isTakeable() { return false; }
}
