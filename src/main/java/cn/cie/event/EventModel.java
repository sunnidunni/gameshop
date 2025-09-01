package cn.cie.event;

import java.util.HashMap;
import java.util.Map;

/**
 * Event entity
 */
public class EventModel {

    /**
     * Key for event queue in cache
     */
    public static final String EVENT_KEY = "event";

    /**
     * Event type
     */
    private EventType eventType;
    /**
     * Event sender id
     */
    private int fromId;
    /**
     * Event receiver id
     */
    private int toId;
    /**
     * Entity that triggered the event, e.g., comment like
     */
    private int entityId;
    /**
     * Entity owner
     */
    private int entityOwnerId;
    /**
     * Possible additional information
     */
    private Map<String ,String> exts = new HashMap<String, String>();

    public EventModel() {

    }

    public EventModel(EventType eventType) {
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return eventType;
    }

    public EventModel setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public int getFromId() {
        return fromId;
    }

    public EventModel setFromId(int fromId) {
        this.fromId = fromId;
        return this;
    }

    public int getToId() {
        return toId;
    }

    public EventModel setToId(int toId) {
        this.toId = toId;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public String getExts(String key) {
        return exts.get(key);
    }

    public EventModel setExts(String key, String value) {
        this.exts.put(key, value);
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public void setExts(Map<String, String> exts) {
        this.exts = exts;
    }
}
