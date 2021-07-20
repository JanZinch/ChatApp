package com.ivan.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private Button _signInBtn;
    private Button _signUpBtn;
    private EditText loginField;
    private EditText passwordField;
    private TextView resultMessage;


    protected void SignIn(User inputData){

        Query query = AppResources.usersDBRef.orderByChild("login").equalTo(inputData.login);

        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        User cursor = dataSnapshot.getValue(User.class);

                        if (cursor.password.trim().equals(inputData.password.trim())) {

                            inputData.name = cursor.name.trim();
                            inputData.avatar = cursor.avatar.trim();

                            startActivity(new Intent(getApplicationContext(), MessagesListActivity.class));
                        }
                        else {

                            resultMessage.setText("Incorrect password!");
                        }
                    }
                }
                else {

                    resultMessage.setText("Incorrect login!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getApplicationContext(), "Error! Server not responding.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        _signInBtn = findViewById(R.id.sign_in_button);
        _signUpBtn = findViewById(R.id.sign_up_button);
        loginField = findViewById(R.id.login_field);
        passwordField = findViewById(R.id.password_field);
        resultMessage = findViewById(R.id.result_msg);
    }

    @Override
    protected void onStart() {
        super.onStart();

        AppResources.database = FirebaseDatabase.getInstance();
        AppResources.usersDBRef = AppResources.database.getReference("user");
        AppResources.messagesDBRef = AppResources.database.getReference("message");

        MessagesListActivity.currentUser = new User(AppResources.ADMIN_NAME, AppResources.ADMIN_LOGIN, AppResources.ADMIN_PASSWORD,
                AppResources.ADMIN_AVATAR , UserAccessLevel.ADMIN);


        _signInBtn.setOnClickListener(v->{

            MessagesListActivity.currentUser = new User(AppResources.ADMIN_NAME, loginField.getText().toString(), passwordField.getText().toString(),
                    AppResources.ADMIN_AVATAR , UserAccessLevel.ADMIN);

            SignIn(MessagesListActivity.currentUser);

        });

        _signUpBtn.setOnClickListener(v->{

            //startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            //startActivityForResult(new Intent(getApplicationContext(), SignUpActivity.class), 0);
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });
    }

    @Override
    protected void onResume() {

        super.onResume();
        //if (AppResources.startMessage == 1) Toast.makeText(getApplicationContext(), AppResources.startMessage, Toast.LENGTH_SHORT).show();
        //Debug.Log("STR: " + AppResources.startMessage);
        //startMessage = null;
    }


}