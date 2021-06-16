package com.ivan.chatapp;

import androidx.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String name;
    public String login;
    public String password;
    public UserAccessLevel accessLevel;
    public String avatar;

    public User(){

        name = login = password = "";
        accessLevel = UserAccessLevel.USER;
        avatar = "";
    }

    public User(String name, String login, String password, String avatar, UserAccessLevel accessLevel){

        this.name = name;
        this.login = login;
        this.password = password;
        this.accessLevel = accessLevel;
        this.avatar = avatar;
    }

    @NonNull
    @Override
    public String toString() {

        return "Name: " + name + "  Login: " + login + "  Password: " + password;
    }

}
