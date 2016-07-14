package com.epicodus.androidmessenger;

import org.parceler.Parcel;

/**
 * Created by Guest on 7/14/16.
 */

@Parcel
public class Contact {
    String contact;
    String email;
    String uid;
    private String pushId;

    public Contact() {}

    public Contact(String contact, String email, String uid) {
        this.contact = contact;
        this.email = email;
        this.uid = uid;
    }

    public String getContact() {
        return contact;
    }

    public String getPushId() {
        return pushId;
    }
    public String getUid() {
        return uid;
    }
    public String getEmail() {
        return email;
    }

    public void setPushId() {
        this.pushId = pushId;
    }

}
