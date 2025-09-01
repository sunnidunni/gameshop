package cn.cie.event;

/**
 * Async event type
 */
public enum EventType {

    SEND_VALIDATE_EMAIL(1),
    SEND_FIND_PWD_EMAIL(2);

    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
