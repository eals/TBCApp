package com.azwraith.apps.tbcapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private List<ChatMessage> list = new ArrayList<>();
    private RecyclerView recycleView;
    private ChatAdapter cAdapter;
    private EditText messageBox;
    private Button sendButton;
    private int count;
    TextView txt;

    String user,course;

    SharedPreferences sp;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        txt =(TextView) findViewById(R.id.course);

        Toolbar chatToolbar = (Toolbar)findViewById(R.id.chat_toolbar);
        setSupportActionBar(chatToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        sp= PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();

        user = sp.getString("username", "default");
        course =  sp.getString("courses","courses");
        if(user.equals("default"))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
            builder.setTitle("Enter your username");
            final EditText edit = new EditText(ChatActivity.this);
            builder.setView(edit);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    editor.putString("username", edit.getText().toString());
                        editor.apply();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    Intent intent = NavUtils.getParentActivityIntent(ChatActivity.this);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    NavUtils.navigateUpTo(ChatActivity.this, intent);
                }
            });
            builder.show();
        }
        if(course.equals("courses"))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
            builder.setTitle("Enter your course");
            final EditText edit = new EditText(ChatActivity.this);
            builder.setView(edit);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    editor.putString("courses", edit.getText().toString());
                    editor.apply();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    Intent intent = NavUtils.getParentActivityIntent(ChatActivity.this);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    NavUtils.navigateUpTo(ChatActivity.this, intent);
                }
            });
            builder.show();
        }
        txt.setText(course);

        Firebase.setAndroidContext(ChatActivity.this);

        Firebase ref = null;

        switch (course){
            case "bba":
                ref = new Firebase(Config.FIREBASE_URL_bba);
                break;
            case "a level":
                ref = new Firebase(Config.FIREBASE_URL_alevel);
                break;
            case "bsc":
                ref = new Firebase(Config.FIREBASE_URL_bsc);
                break;
            default:
                ref = new Firebase(Config.FIREBASE_URL_others);
                break;
        }
        messageBox = (EditText)findViewById(R.id.chatMessageBox);
        sendButton = (Button)findViewById(R.id.sendButton);



        recycleView = (RecyclerView)findViewById(R.id.recycler_view);
        cAdapter = new ChatAdapter(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setItemAnimator(new DefaultItemAnimator());
        recycleView.setAdapter(cAdapter);


        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                list.add(chatMessage);
                count = list.size();
                cAdapter.notifyDataSetChanged();
                if(!(list.size() == 0))
                {
                    recycleView.scrollToPosition(list.size() - 1);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        final Firebase finalRef = ref;
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageBox.getText().toString();

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setUsername(user);
                chatMessage.setMessage(message);
                chatMessage.setCourses(course);
                SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss");
                String currentDateandTime = sdf.format(new Date());
                chatMessage.setDate(currentDateandTime);

                finalRef.push().setValue(chatMessage);
                messageBox.setText("");

            }
        });



    }


}
