package com.ivan.chatapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.ViewHolder> {


    protected LinkedList<UserMessage> _messages;
    protected Context _context;



    public MessagesListAdapter() {

        _context = null;
        _messages = null;
    }

    public MessagesListAdapter(Context context, LinkedList<UserMessage> messages) {

        _context = context;
        _messages = messages;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView owner;
        public ImageView avatar;
        public TextView text;


        public ViewHolder(View item){

            super(item);
            owner = (TextView) item.findViewById(R.id.companionName);
            avatar = (ImageView) item.findViewById(R.id.companionAvatar);
            text = (TextView) item.findViewById(R.id.messageText);
            item.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

            if(position != RecyclerView.NO_POSITION) {

                //-----
            }
        }




    }


    @Override
    public MessagesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View pictureView = inflater.inflate(R.layout.messages_list_item, parent, false);
        MessagesListAdapter.ViewHolder viewHolder = new  MessagesListAdapter.ViewHolder(pictureView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessagesListAdapter.ViewHolder holder, int position) {

        UserMessage message = _messages.get(position);

        ImageView avatarView = holder.avatar;
        TextView ownerView = holder.owner;
        TextView messageView = holder.text;

        try{

            //Log.d(MainActivity.TAG, "INP");

            Uri uri = Uri.parse(message.avatarUri);

            //Log.d(MainActivity.TAG, post.getPictureUri());

            Glide.with(_context).load(uri).into(avatarView);

            ownerView.setText(message.owner);
            messageView.setText(message.text);

            Debug.Log("URI: " + uri);

        }
        catch (Exception ex){

            Debug.Log(ex.getMessage());
            //Log.d(MainActivity.TAG, ex.getMessage());
        }



    }


    @Override
    public int getItemCount() { return _messages.size(); }

}
