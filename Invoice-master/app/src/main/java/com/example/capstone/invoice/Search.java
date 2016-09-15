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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

 /*
  * The Search class has all invoices listed in the search view. Once an invoice is clicked the
  * search class will send the invoice to the invoice view for updating.
  */

public class Search extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Invoice invoice;
    private Customer customer;
    private CustomerInvoice customerInvoice;
    private ArrayList<Invoice> invoiceList;
    private ArrayList<CustomerInvoice> CI;
    private Database dbHandler;
    private SearchViewListAdapter SVLadapter;
    private ListView CSListview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        CI = new ArrayList();
        customerInvoice = new CustomerInvoice();

        //Access the ListView
        CSListview = (ListView) findViewById(R.id.Search_listview);

        // create a itemViewListAdapter for the ListView
        SVLadapter = new SearchViewListAdapter(this, getLayoutInflater());

        // Set ListView to use the Listview Array adapter
        CSListview.setAdapter(SVLadapter);

        getSearchList();
        SVLadapter.updateData(CI);
        CSListview.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

    private void getSearchList(){
        dbHandler = new Database(this, null, null, 1);
        Invoice temp = new Invoice();
        Customer cust = new Customer();
        invoiceList = dbHandler.getInvoiceList();
        for (int i=0; i<invoiceList.size(); i++){
            temp=invoiceList.get(i);
            CustomerInvoice temp2 = new CustomerInvoice();
            temp2.setInvoiceID(temp.getInvoiceID());
            temp2.setInvoiceDate(temp.getInvoiceDate());
            temp2.setCustomerID(temp.getInvoiceID());
            cust = dbHandler.getCustomer(temp.getCustomerID());
            temp2.setFirstName(cust.getCustomerFirstName());
            temp2.setLastName(cust.getCustomerLastName());
            CI.add(temp2);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        customerInvoice = SVLadapter.getItem(position);
        Intent intentBundle = new Intent(Search.this, Invoice_view.class);
        Bundle bundle = new Bundle();
        bundle.putInt("SendInvoiceID", customerInvoice.getInvoiceID());
        intentBundle.putExtras(bundle);
        Toast.makeText(Search.this, "clicked", Toast.LENGTH_SHORT).show();
        //startActivity(intentBundle);
        startActivityForResult(intentBundle, 1);
        finish();
    }
}
