package com.ivan.chatapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class SignUpActivity extends AppCompatActivity {

    private ImageView _avatarPreview = null;
    private Button _selectButton = null;
    private EditText name = null;
    private EditText login = null;
    private EditText password = null;
    private EditText confirmPassword = null;
    private TextView resultMessage = null;
    private Button enterButton = null;

    private String currentPictureUri = AppResources.USER_AVATAR;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        try{

            switch(requestCode){

                case AppResources.PICK_IMAGE_REQUEST:

                    if (resultCode == RESULT_OK){

                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        _avatarPreview.setImageBitmap(selectedImage);
                        currentPictureUri = imageUri.toString();

                        Glide.with(getApplicationContext()).load(currentPictureUri).into(_avatarPreview);

                        Debug.Log("OK!");
                    }

                    if (requestCode == RESULT_CANCELED){

                        Debug.Log("CANCELED!");
                    }
            }
        }
        catch (FileNotFoundException ex){

            currentPictureUri = AppResources.USER_AVATAR;
            Debug.Log("Image not found!");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        _avatarPreview = (ImageView) findViewById(R.id.selected_picture);
        _selectButton = (Button) findViewById(R.id.select_button);
        name = findViewById(R.id.name_field);
        login = findViewById(R.id.login_field);
        password = findViewById(R.id.password_field);
        confirmPassword = findViewById(R.id.confirm_password_field);
        resultMessage = findViewById(R.id.result_msg);
        enterButton = findViewById(R.id.enter_button);

    }

    @Override
    protected void onStart() {

        super.onStart();

        Glide.with(getApplicationContext()).load(currentPictureUri).into(_avatarPreview);

        _selectButton.setOnClickListener(v->{

            Intent pickImage = new Intent(Intent.ACTION_PICK);
            pickImage.setType(AppResources.AVATAR_TYPE);
            startActivityForResult(pickImage, AppResources.PICK_IMAGE_REQUEST);
        });

        enterButton.setOnClickListener(v->{

            String loginText = login.getText().toString();
            String passwordText = password.getText().toString();
            String confirmPasswordText = confirmPassword.getText().toString();

            if(loginText.trim().equals("") || passwordText.trim().equals("") || confirmPasswordText.trim().equals("")){

                resultMessage.setText("Some fields are empty!");
                return;
            }

            if(!passwordText.trim().equals(confirmPasswordText.trim())){

                resultMessage.setText("Passwords mismatch!");
                return;
            }

            String nameText = name.getText().toString();

            MessagesListActivity.currentUser = new User(nameText, loginText, passwordText, currentPictureUri, UserAccessLevel.USER);

            startActivity(new Intent(getApplicationContext(), MessagesListActivity.class));

        });
    }
}