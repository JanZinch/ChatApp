package com.ivan.chatapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserMessage implements Parcelable {

    public String owner;
    public String avatarUri;
    public String text;

    public UserMessage(){

        owner = avatarUri = text = "";
    }

    public UserMessage(String owner, String avatarUri, String text){

        this.owner = owner;
        this.avatarUri = avatarUri;
        this.text = text;
    }

    protected UserMessage(Parcel in){

        owner = in.readString();
        avatarUri = in.readString();
        text = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(owner);
        dest.writeString(avatarUri);
        dest.writeString(text);
    }

    @Override
    public int describeContents() { return 0; }

    public static final Creator<UserMessage> CREATOR = new Creator<UserMessage>() {

        @Override
        public UserMessage createFromParcel(Parcel in) { return new UserMessage(in); }

        @Override
        public UserMessage[] newArray(int size) { return new UserMessage[size]; }
    };


    @NonNull
    @Override
    public String toString() {

        return "Owner: " + owner + " Text: " + text;
    }
}
