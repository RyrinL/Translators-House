package com.translatorshouse.event;

public class KeyObtainedEvent implements WorldEvent {
    private final String keyId;
    public KeyObtainedEvent(String keyId) { this.keyId = keyId; }
    public String getKeyId() { return keyId; }
    public String name() { return "KeyObtained"; }
}
