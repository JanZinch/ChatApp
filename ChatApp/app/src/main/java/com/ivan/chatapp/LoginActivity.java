package com.ivan.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {



    private Button _signIn;
    private Button _signUp;
    private EditText loginField;
    private EditText passwordField;
    private TextView resultMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _signIn = findViewById(R.id.sign_in_btn);
        _signUp = findViewById(R.id.sign_up_btn);
        loginField = findViewById(R.id.login_field);
        passwordField = findViewById(R.id.password_field);
        resultMessage = findViewById(R.id.result_msg);
    }

    @Override
    protected void onStart() {
        super.onStart();

        AppResources.database = FirebaseDatabase.getInstance();

        _signIn.setOnClickListener(v->{

            MessagesListActivity.currentUser = new User(AppResources.ADMIN_NAME, AppResources.ADMIN_LOGIN, AppResources.ADMIN_PASSWORD,
                    AppResources.ADMIN_AVATAR , UserAccessLevel.ADMIN);

            startActivity(new Intent(getApplicationContext(), MessagesListActivity.class));
        });

        _signUp.setOnClickListener(v->{

            startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
        });
    }
}