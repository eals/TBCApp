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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/*
 * This class allows the invoice application to list, add, update, and delete an invoice item
 *
 */

public class AddInvoiceItemView extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{
    private boolean update = false;
    private boolean mFromList = true;
    private EditText IName;
    private EditText IRate;
    private EditText QFront;
    private EditText QLeft;
    private EditText QRight;
    private EditText QBack;
    private ListView itemListView;
    private ItemViewListAdapter itemViewAdapter;
    private int invoiceId;
    private ArrayList<InvoiceItem> AInvoiceItem;
    private Database dbHandler;
    private int invoiceIID;
    private InvoiceItem mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invoice_item_view);
        dbHandler = new Database(this, null, null, 1);

        mItem = new InvoiceItem();

        IName = (EditText) findViewById(R.id._itemName);
        IRate = (EditText) findViewById(R.id.itemRate);
        QFront = (EditText) findViewById(R.id.FQuantity);
        QBack = (EditText) findViewById(R.id.BQuantity);
        QLeft = (EditText) findViewById(R.id.LQuantity);
        QRight = (EditText) findViewById(R.id.RQuantity);
        QFront.setText("0");
        QBack.setText("0");
        QLeft.setText("0");
        QRight.setText("0");

        //get Bundle from intent and extract the invoiceId
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (extrasBundle != null){
            Log.d("bundle", "");
            //we have a bundle

            if(extrasBundle.getInt("SendInvoiceID")!=0){
                invoiceId = extrasBundle.getInt("SendInvoiceID");
                Log.d("bundle", "" + invoiceId);}

            if(extrasBundle.getInt("SendInvoiceItem")!=0){

                InvoiceItem iitem = new InvoiceItem();
                invoiceIID = extrasBundle.getInt("SendInvoiceItem");
                iitem = dbHandler.getInvoiceItem(invoiceIID);
                IName.setText(iitem.getInvoiceItemName());
                IRate.setText(moneyFormat(iitem));
                QFront.setText(Integer.toString(iitem.getInvoiceItemFQuantity()));
                QBack.setText(Integer.toString(iitem.getInvoiceItemBQuantity()));
                QLeft.setText(Integer.toString(iitem.getInvoiceItemLQuantity()));
                QRight.setText(Integer.toString(iitem.getInvoiceItemRQuantity()));
                update = true;
                }
        }

        itemListView = (ListView) findViewById(R.id.main_item_listview);
        itemListView.setOnItemClickListener(this);
        Log.d("InvoiceItemView", "itemListView: created");

        // create a itemViewListAdapter for the ListView
        itemViewAdapter = new ItemViewListAdapter(this, getLayoutInflater());
        Log.d("InvoiceItemView", "itemViewAdapter: created");

        // Set ListView to use the ArrayAdapter
        itemListView.setAdapter(itemViewAdapter);
        Log.d("InvoiceItemView", "itemsetAdapter: created");
        itemViewAdapter.updateData(dbHandler.getItemList());

    }

    // Adds an invoice item to the database and then exits the view
    public void AddInvoiceItem(View view){
        Database dbHandler = new Database(this, null, null, 1);

        if (IName.getText().toString().equals("") || IRate.getText().toString().equals("") || update){
            Toast.makeText(getApplicationContext(), "You must add \n Invoice Item from list", Toast.LENGTH_SHORT).show();
        }else{

            int rate, QF, QB, QL, QR;
            try {
                String s= IRate.getText().toString();
                if (s.charAt(0)=='$'){
                    rate =(int) (Double.parseDouble(s.substring(1)) * 100);
                } else {
                    rate = (int) (Double.parseDouble(s) * 100);
                }
                QF =(Integer.parseInt(QFront.getText().toString()));
                QB =(Integer.parseInt(QBack.getText().toString()));
                QL =(Integer.parseInt(QLeft.getText().toString()));
                QR =(Integer.parseInt(QRight.getText().toString()));

                mItem = new InvoiceItem(invoiceId, IName.getText().toString(), rate, QF, QB, QL, QR);

                    dbHandler.addInvoiceItem(mItem);


            }catch (Exception e) {
                Log.d("InvoiceItemView", e.toString());
            }
            finish();
        }

    }

    // Updates an invoice item and ends the view
    public void updateInvoiceItem(View view){
        if (update){
            InvoiceItem iitem;
            int rate, QF, QB, QL, QR;
            try {
                String s= IRate.getText().toString();if (s.charAt(0)=='$'){
                    rate =(int) (Double.parseDouble(s.substring(1)) * 100);
                } else {
                    rate = (int) (Double.parseDouble(s) * 100);
                }
                QF = (Integer.parseInt(QFront.getText().toString()));
                QB = (Integer.parseInt(QBack.getText().toString()));
                QL = (Integer.parseInt(QLeft.getText().toString()));
                QR = (Integer.parseInt(QRight.getText().toString()));

                iitem = new InvoiceItem(invoiceId, IName.getText().toString(), rate, QF, QB, QL, QR);
                iitem.setInvoiceItemId(invoiceIID);
                dbHandler.updateInvoiceItem(iitem);

            }catch (Exception e) {
                Log.d("InvoiceItemView", e.toString());
            }
        }
        finish();
    }

    public void deleteInvoiceItem(View view){
        dbHandler.deleteInvoiceItem(invoiceIID);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_invoice_item_view, menu);
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
        IName.setText(item.getItemName());
        IRate.setText(moneyFormat(item));
        Log.d("In here", "here" + position);
    }

    private String moneyFormat(Item item){
        Double temp = item.getItemRate()*.01;
        String s = String.format("$%.2f", temp );
        return s;
    }

    private String moneyFormat(InvoiceItem item){
        Double temp = item.getInvoiceItemRate()*.01;
        String s = String.format("$%.2f", temp );
        return s;
    }

}
