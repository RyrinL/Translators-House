package com.translatorshouse.item;

/** Composite leaf: an item with no children. */
public class SimpleItem implements Item {
    private final String id, name, description;
    private final boolean takeable;

    public SimpleItem(String id, String name, String description, boolean takeable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.takeable = takeable;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isTakeable() { return takeable; }
}
