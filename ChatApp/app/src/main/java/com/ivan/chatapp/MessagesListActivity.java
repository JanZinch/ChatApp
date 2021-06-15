package com.ivan.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.LinkedList;

public class MessagesListActivity extends AppCompatActivity {

    private LinkedList<UserMessage> _messages = null;
    private RecyclerView _recyclerView = null;

    private MessagesListAdapter _adapter = null;

    public static User currentUser = null;

    public static MessagesListActivity instance = null;


    private Button _sendButton = null;
    private EditText _writingArea = null;


    protected void InitMessageListView(){

        _adapter = new MessagesListAdapter(this, _messages);
        _recyclerView.setAdapter(_adapter);
    }

    protected void UpdateMessageListView(){

        _adapter.notifyDataSetChanged();
        _recyclerView.smoothScrollToPosition(_adapter.getItemCount() - 1);
    }

    protected void ReadMessagesFromDatabase(){




    }

    protected void WriteMessageToDatabase(UserMessage message, FirebaseCallback callback){

        try{

            AppResources.dbRef = AppResources.database.getReference("message").push();
            AppResources.dbRef.setValue(message);
            //AppResources.dbRef.child("owner").setValue(message.owner);
            //AppResources.dbRef.child("avatarUri").setValue(message.avatarUri);
            //AppResources.dbRef.child("text").setValue(message.text);
            callback.OnCallback(true);
        }
        catch (Exception ex){

            Debug.Log("Database updating failed!");
            callback.OnCallback(false);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_list);

        //instance = this;

        _sendButton = (Button) findViewById(R.id.sendButton);
        _writingArea = (EditText) findViewById(R.id.writingArea);

        _recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        _recyclerView.setHasFixedSize(true);
        _recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }

    @Override
    protected void onStart() {

        super.onStart();

        if(currentUser == null){

            Debug.Log("User not set!");
            return;
        }

        try{

            AppResources.dbRef = AppResources.database.getReference("message");
            _messages = new LinkedList<>();


            InitMessageListView();


        }
        catch (Exception ex){

            Debug.Log(ex.getMessage());

        }







        _sendButton.setOnClickListener(v -> {

            String writtenText = _writingArea.getText().toString();

            if (writtenText.trim().equals("")){

                Toast.makeText(getApplicationContext(), "Input message...", Toast.LENGTH_SHORT).show();
            }
            else if (writtenText.trim().length() > AppResources.MAX_MESSAGE_SIZE){

                Toast.makeText(getApplicationContext(), "Your message is too long!", Toast.LENGTH_SHORT).show();
            }
            else {

                WriteMessageToDatabase(new UserMessage(currentUser.name, currentUser.avatar, writtenText), result -> {

                    if (!result){

                        Toast.makeText(getApplicationContext(), "Error! Message not sent.", Toast.LENGTH_SHORT).show();
                    }
                });

                _writingArea.setText("");


            }


        });



        AppResources.dbRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Debug.Log("MSG ADDED");
                UserMessage um = snapshot.getValue(UserMessage.class);
                _messages.addLast(um);
                UpdateMessageListView();
                Debug.Log("SUCCESSFULL: " + um);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}