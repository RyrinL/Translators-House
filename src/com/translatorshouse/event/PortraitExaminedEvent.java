package com.translatorshouse.event;

public class PortraitExaminedEvent implements WorldEvent {
    private final int count;
    public PortraitExaminedEvent(int count) { this.count = count; }
    public int getCount() { return count; }
    public String name() { return "PortraitExamined"; }
}
