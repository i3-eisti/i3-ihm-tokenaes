package org.blackpanther.aestokenizer.gui.events;

/**
 * @author MACHIZAUD Andréa
 * @version 19/09/11
 */
public class AESGuiEvent {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(AESGuiEvent.class.getCanonicalName());

    public enum EventType {
        ERROR,
        SUCCESS,
        PROCESSING
    }

    private final EventType type;
    private final String message;

    public AESGuiEvent(
            final EventType type,
            final String message
    ) {
        this.type = type;
        this.message = message;
    }

    public EventType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
