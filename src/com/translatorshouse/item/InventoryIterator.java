package com.translatorshouse.item;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** Iterator that walks the Composite tree, descending into open containers. */
public class InventoryIterator implements Iterator<Item> {
    private final Deque<Iterator<Item>> stack = new ArrayDeque<>();

    public InventoryIterator(Inventory root) {
        stack.push(root.topLevel().iterator());
    }

    @Override
    public boolean hasNext() {
        while (!stack.isEmpty() && !stack.peek().hasNext()) {
            stack.pop();
        }
        return !stack.isEmpty();
    }

    @Override
    public Item next() {
        if (!hasNext()) throw new NoSuchElementException();
        Item item = stack.peek().next();
        if (item instanceof Container c && c.isOpen() && !c.getContents().isEmpty()) {
            stack.push(c.getContents().iterator());
        }
        return item;
    }
}
