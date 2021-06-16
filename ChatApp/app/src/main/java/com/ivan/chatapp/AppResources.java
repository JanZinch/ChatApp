package com.ivan.chatapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AppResources {

    public static final String AVATAR_TYPE = "image/*";
    public static final int PICK_IMAGE_REQUEST = 1;

    public static final String ADMIN_LOGIN = "admin@com";
    public static final String ADMIN_PASSWORD = "111111";
    public static final String ADMIN_NAME = "Administrator";
    public static final String ADMIN_AVATAR = "android.resource://com.ivan.chatapp/drawable/admin_avatar";

    public static final String USER_AVATAR = "android.resource://com.ivan.chatapp/drawable/user_avatar";

    public static final int MAX_MESSAGE_SIZE = 1000;

    public static FirebaseDatabase database = null;
    public static DatabaseReference messagesDBRef = null;
    public static DatabaseReference usersDBRef = null;


}
