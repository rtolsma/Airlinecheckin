package com.tolsma.ryan.airlinecheckin.model.events;

/**
 * Created by ryan on 1/10/16.
 */
public class DeleteLoginEvent {

    private String primary;

    public DeleteLoginEvent(String code) {
        this.primary = code;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }
}
