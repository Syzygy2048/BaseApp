package io.github.syzygy2048.events;

/**
 * Created by Syzygy on 12.07.16.
 */
public class ClickedEvent {
    String eventMessage;

    public ClickedEvent(String eventMessage){
        this.eventMessage = eventMessage;
    }

    public String getEventMessage(){
        return eventMessage;
    }
}
