package com.azwraith.apps.tbcapp;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by B50i7D on 9/29/2016.
 */
public class MathsActivity extends AppCompatActivity {
    ImageView downOne;
    LinearLayout dirNotify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maths_activity);
                downOne = (ImageView) findViewById(R.id.downloadOne);
        dirNotify = (LinearLayout) findViewById(R.id.dirnotify);
        final String http = "https://firebasestorage.googleapis.com/v0/b/shuvamjewelery.appspot.com/o/alishCoverPage.docx?alt=media&token=e22c9d4a-bdca-4f25-8ca3-e821b088bee7";
        final String nameOfFile = URLUtil.guessFileName(http,null,
                MimeTypeMap.getFileExtensionFromUrl(http));
        dirNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MathsActivity.this,"Destination:sdcard/"+Environment.DIRECTORY_DOWNLOADS+"/"+nameOfFile,Toast.LENGTH_SHORT).show();
            }
        });

                downOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(http));
                        request.setTitle("File download");
                        request.setDescription("file is being downloaded......");
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);

                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,nameOfFile);
                        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        manager.enqueue(request);
                    }
                });
        }
    }
