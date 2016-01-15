package com.tolsma.ryan.airlinecheckin.model.events;

import android.app.PendingIntent;
import android.widget.Toast;

/**
 * Created by ryan on 1/10/16.
 */
public class NotificationEvent {

    private String message;
    private String title;
    private int id;

    private PendingIntent pi;

    public NotificationEvent(int id, String msg, String title, PendingIntent pi) {
        this.message = msg;
        this.title = title;
        this.pi = pi;
        this.id = id;
    }

    public NotificationEvent() {
        this(-1, null, null, null);
    }

    public ToastEvent getToastEvent(int length) {
        return new ToastEvent(this.getMessage(), length);
    }

    public ToastEvent getToastEvent() {
        return getToastEvent(Toast.LENGTH_SHORT);
    }

    public ToastEvent getTitleToastEvent(int length) {
        return new ToastEvent(this.getTitle(), length);
    }

    public ToastEvent getTitleToastEvent() {
        return getTitleToastEvent(Toast.LENGTH_SHORT);
    }

    public PendingIntent getIntent() {
        return pi;
    }

    public void setIntent(PendingIntent pi) {
        this.pi = pi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
