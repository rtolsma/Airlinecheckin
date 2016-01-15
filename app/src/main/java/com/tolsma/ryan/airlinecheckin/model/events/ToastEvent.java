package com.tolsma.ryan.airlinecheckin.model.events;

import android.widget.Toast;

/**
 * Created by ryan on 1/10/16.
 */
public class ToastEvent {
    private String message;
    private int length;

    public ToastEvent(String msg, int length) {
        this.length = length;
        this.message = msg;
    }

    public ToastEvent() {
        this(null, Toast.LENGTH_SHORT);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
