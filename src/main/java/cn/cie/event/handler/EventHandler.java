package cn.cie.event.handler;

import cn.cie.event.EventModel;
import cn.cie.event.EventType;

import java.util.List;

/**
 * Event handler interface that includes event handling and all events of interest
 */
public interface EventHandler {

    /**
     * Handle event
     */
    void doHandler(EventModel eventModel);

    /**
     * Get supported event types
     * @return
     */
    List<EventType> getSupportEvent();

}
