package com.ivan.chatapp;

import android.util.Log;

public class Debug {

    public static final String TAG = "AppLogs";

    public static void Log(String message){

        Log.d(TAG, message);
    }

}
