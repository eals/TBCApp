/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.capstone.invoice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

 /*
  * Item_view is a the class that allows for the item data to view to the UI along with add, update and delete
  * an item from the view and the database
  */

public class Items_view extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText IName;
    private EditText IRate;
    private ListView itemListView;
    private ItemViewListAdapter itemViewAdapter;
    private int itemID=0;
    private Database dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_view);
        dbHandler = new Database(this, null, null, 1);

        IName = (EditText) findViewById(R.id.itemName);
        IRate = (EditText) findViewById(R.id.itemRate);

        // Access the ListView
        itemListView = (ListView) findViewById(R.id.main_listview);
        Log.d("itemListView:", "created");
        // create a itemViewListAdapter for the ListView
        itemViewAdapter = new ItemViewListAdapter(this, getLayoutInflater());
        Log.d("itemViewAdapter:", "created");
        // Set ListView to use the ArrayAdapter
        itemListView.setAdapter(itemViewAdapter);
        Log.d("itemsetAdapter:", "created");
        itemViewAdapter.updateData(dbHandler.getItemList());
        itemListView.setOnItemClickListener(this);


    }


    public void newItem (View view) {

            Database dbHandler = new Database(this, null, null, 1);
        Item item;
        int rate;
        try {
                rate =
                        (int) (Double.parseDouble(IRate.getText().toString()) * 100);
                item =
                        new Item(IName.getText().toString(), rate);
            if (item.getItemName().equals("")){Log.d("Empty Value", "no string");}
            else {
            dbHandler.addItem(item);
            //ArrayList<Item> test = new ArrayList<Item>();
            //test = dbHandler.getItemList();
            IRate.setText("");
            IName.setText("");
            itemViewAdapter.updateData(dbHandler.getItemList());
            Log.d("item updated", "ok");}
            }catch (Exception e) {
            Log.d("Empty Value", e.toString());
        }
    }

    public void updateItem(View view){
        if(itemID != 0){
            Item item = new Item();
            int rate;
            try {
                String s= IRate.getText().toString();
                rate =(int) (Double.parseDouble(s.substring(1)) * 100);


                item.setItemId(itemID);
                item.setItemName(IName.getText().toString());
                item.setItemRate(rate);
                dbHandler.updateItem(item);

            }catch (Exception e) {
                Log.d("InvoiceItemView", e.toString());
            }
            itemID = 0;
            IRate.setText("");
            IName.setText("");
            itemViewAdapter.updateData(dbHandler.getItemList());

        }
    }

    public void deleteItem(View view){
        if(itemID != 0){
            dbHandler.deleteItem(itemID);
            itemID = 0;
            IRate.setText("");
            IName.setText("");
            itemViewAdapter.updateData(dbHandler.getItemList());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_items_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Item item = itemViewAdapter.getItem(position);
        Item item2 = dbHandler.findItem(item.getItemName());
        itemID = item2.getItemId();
        IName.setText(item.getItemName());
        IRate.setText(moneyFormat(item));
        Log.d("In here", "here" + position);
    }

    private String moneyFormat(Item item){
        Double temp = item.getItemRate()*.01;
        String s = String.format("$%.2f", temp );
        return s;
    }
}
