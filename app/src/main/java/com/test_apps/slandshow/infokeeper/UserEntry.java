package com.test_apps.slandshow.infokeeper;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 23.05.2017.
 */

public class UserEntry implements Parcelable {

    private String mail, pass, site;
    private DataBaseHandler dataBaseHandler;
    private long id;

    public UserEntry(String mail, String pass, String site) {
        this.mail = mail;
        this.pass = pass;
        this.site = site;
    }

    public UserEntry(String mail, String pass, String site, long id) {
        this.mail = mail;
        this.pass = pass;
        this.site = site;
        this.id = id;
    }

    public UserEntry() {
    }

    protected UserEntry(Parcel in) {
        mail = in.readString();
        pass = in.readString();
        site = in.readString();
    }

    public static final Creator<UserEntry> CREATOR = new Creator<UserEntry>() {
        @Override
        public UserEntry createFromParcel(Parcel in) {
            return new UserEntry(in);
        }

        @Override
        public UserEntry[] newArray(int size) {
            return new UserEntry[size];
        }
    };

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getMail() {
        return mail;
    }

    public String getPass() {
        return pass;
    }

    public String getSite() {
        return site;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mail);
        dest.writeString(pass);
        dest.writeString(site);
    }

    public void setDataBaseHandler(DataBaseHandler dataBaseHandler) {
        this.dataBaseHandler = dataBaseHandler;
    }

    public DataBaseHandler getDataBaseHandler() {
        return dataBaseHandler;
    }
}
