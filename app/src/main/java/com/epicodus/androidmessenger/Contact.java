package com.epicodus.androidmessenger;

import org.parceler.Parcel;

/**
 * Created by Guest on 7/14/16.
 */

@Parcel
public class Contact {
    String contact;
    private String pushId;

    public Contact() {}

    public Contact(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId() {
        this.pushId = pushId;
    }

}
