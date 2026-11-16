package com.translatorshouse.item;

/** Composite component. Leaves (SimpleItem) and composites (Container, Inventory) all implement this. */
public interface Item {
    String getId();
    String getName();
    String getDescription();
    boolean isTakeable();
}
