package com.azwraith.apps.tbcapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView list;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] mPanelTitles;
    private ActionBarDrawerToggle mDrawerToggle;
    private ImageView calendarButton;
    private ImageView chatButton;
    TextView txt_title;
    private ImageView notesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayout);
        Typeface mytypeface = Typeface.createFromAsset(getAssets(),"HelloAli.ttf");
        txt_title = (TextView) findViewById(R.id.home_title);
        txt_title.setTypeface(mytypeface);

        Toolbar myToolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        list = (ListView)findViewById(R.id.list);

        String[] values = new String[]{"Event Details 1", "Event Details 2", "Event Details 3", "Event Details 4", "Event Details 5"};

        calendarButton = (ImageView)findViewById(R.id.calendarButton);

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, CalendarActivity.class));
            }
        });

        notesButton = (ImageView)findViewById(R.id.notesButton);
        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, NotesActivity.class));
            }
        });

        chatButton = (ImageView)findViewById(R.id.chatButton);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, ChatActivity.class));
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.black));
                return view;
            }
        };

        list.setAdapter(adapter);

        mPanelTitles = new String[]{"College", "Personal", "Timetable", "Finance", "About Us", "Settings"};
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.leftlist);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mPanelTitles)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.black));
                return view;
            }
        };

        mDrawerList.setAdapter(adapter1);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close)
        {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            switch (position) {
                case 2:
                    startActivity(new Intent(MainActivity.this, Timetable.class));
                    break;

                case 4:
                    startActivity(new Intent(MainActivity.this, About.class));
                    break;
            }
            setTitle(mPanelTitles[position]);
        }

    }
}
