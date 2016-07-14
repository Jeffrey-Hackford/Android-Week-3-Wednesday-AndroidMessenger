package com.epicodus.androidmessenger;

import org.parceler.Parcel;

/**
 * Created by Guest on 7/14/16.
 */
@Parcel
public class Message {
    String message;
    String pushId;
    String uid;

    public Message() {}

    public Message(String message, String uid) {
        this.message = message;
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public String getUid() {
        return uid;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
