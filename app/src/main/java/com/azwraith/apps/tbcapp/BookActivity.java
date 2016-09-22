package com.azwraith.apps.tbcapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class BookActivity extends AppCompatActivity {

    Button downloadButton;
    File localFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);


        final FirebaseStorage storage = FirebaseStorage.getInstance();

        downloadButton = (Button)findViewById(R.id.downloadButton);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StorageReference storageReference = storage.getReferenceFromUrl("gs://tbchat-ddb46.appspot.com");

                StorageReference fileReference = storageReference.child("sample.txt");

                try{
                    localFile = File.createTempFile("sample", "txt");

                }catch (IOException exception){exception.printStackTrace();}

                fileReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(BookActivity.this, "File has been downloaded!", Toast.LENGTH_SHORT).show();
                        // Local temp file has been created
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        exception.printStackTrace();
                        // Handle any errors
                    }
                });
            }
        });



    }
}
