package com.azwraith.apps.tbcapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dell on 7/15/2016.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder>{
    private List<ChatMessage> chatMessages;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username, message,date;

        public MyViewHolder(View view)
        {
            super(view);

            username = (TextView) view.findViewById(R.id.username);
            message = (TextView) view.findViewById(R.id.chatMessage);
            date = (TextView) view.findViewById(R.id.date);
        }
    }

    public ChatAdapter(List<ChatMessage> chatMessages)
    {
        this.chatMessages = chatMessages;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ChatMessage chat = chatMessages.get(position);
        holder.username.setText(chat.getUsername());
        holder.message.setText(chat.getMessage());
        holder.date.setText(chat.getDate());
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

}
