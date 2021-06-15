package com.ivan.chatapp;

import androidx.annotation.NonNull;

public class User {

    public final String name;
    public final String login;
    public final String password;
    public final UserAccessLevel accessLevel;
    public final String avatar;

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
