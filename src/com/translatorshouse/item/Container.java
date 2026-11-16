package com.translatorshouse.item;

import java.util.ArrayList;
import java.util.List;

/** Composite node: an Item that holds other Items (which may themselves be Containers). */
public class Container implements Item {
    private final String id, name, description;
    private final List<Item> contents = new ArrayList<>();
    private boolean open;
    private boolean locked;

    public Container(String id, String name, String description, boolean open, boolean locked) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.open = open;
        this.locked = locked;
    }

    public void add(Item item) { contents.add(item); }

    /** Visible contents: empty unless the container is open. */
    public List<Item> getContents() { return open ? List.copyOf(contents) : List.of(); }

    /** Raw contents regardless of open state — used by snapshots and the iterator. */
    public List<Item> getAllContents() { return List.copyOf(contents); }

    public void open() { this.open = true; }
    public void setOpen(boolean open) { this.open = open; }
    public boolean isOpen() { return open; }

    public void unlock() { this.locked = false; }
    public boolean isLocked() { return locked; }

    public boolean removeItem(String itemId) {
        return contents.removeIf(i -> i.getId().equals(itemId));
    }

    public void clear() { contents.clear(); }

    /** Recursive search through open sub-containers. */
    public Item find(String itemId) {
        for (Item i : contents) {
            if (i.getId().equals(itemId)) return i;
            if (i instanceof Container c && c.open) {
                Item found = c.find(itemId);
                if (found != null) return found;
            }
        }
        return null;
    }

    /** Recursive removal through open sub-containers. */
    public boolean removeRecursive(String itemId) {
        if (removeItem(itemId)) return true;
        for (Item i : contents) {
            if (i instanceof Container c && c.open && c.removeRecursive(itemId)) return true;
        }
        return false;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isTakeable() { return false; }
}
