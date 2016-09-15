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
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

 /*
  * InvoiceView allows for the UI to access the invoice object and display it to the user.
  * It allows to add, update, addInvoiceItem, display sum, display sum by sides, add customer
  *
  */

public class Invoice_view extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Customer customer;
    private Invoice invoice;
    private EditText CFirstName;
    private EditText CLastName;
    private EditText CStreet;
    private EditText CCity;
    private EditText CState;
    private EditText CZip;
    private EditText CPhone;
    private EditText CustID;
    private EditText InvoiceDate;
    private EditText InvoiceID;
    private EditText CNotes;
    private Database dbHandler;
    private ListView invoiceItemListView;
    private InvoiceItemViewListAdapter invoiceItemViewAdapter;
    private CheckBox FCheck;
    private CheckBox BCheck;
    private CheckBox LCheck;
    private CheckBox RCheck;
    private TextView fTotal;
    private TextView bTotal;
    private TextView rTotal;
    private TextView lTotal;
    private TextView finalTotal;
    private ArrayList<InvoiceItem> IItemList;
    private Button _PrintView;
    private int InvoiceTotal;
    private boolean mSumRightCheck;
    private boolean mSumLeftCheck;
    private boolean mSumFrontCheck;
    private boolean mSumBackCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_view);
        dbHandler = new Database(this, null, null, 1);
        invoice = new Invoice();
        customer = new Customer();
        editTextHolder(); //create fields to the UI IDs

        Button _AddInvoiceItem = (Button) findViewById(R.id.ConstaddInvoiceItem);
        _PrintView = (Button) findViewById(R.id.PrintViewButton);

        // Initialize the Invoice total
        InvoiceTotal = 0;



        // Access the ListView
        invoiceItemListView = (ListView) findViewById(R.id.FInvoice_Item_listview);

        // Collect intent when page loads to determine if old invoice or new
        // If old get collect invoice and populate objs
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (extrasBundle != null){
            Log.d("bundle", "yes a bundle");
            //we have a bundle
            int invoiceID;
            invoiceID = extrasBundle.getInt("SendInvoiceID");
            Log.d("bundle", "" + invoiceID);
            invoice = dbHandler.getInvoice(invoiceID);
            customer = dbHandler.getCustomer(invoice.getCustomerID());
            setRecurringInvoice();
        } else {

            dbHandler.addInvoice(invoice); // create new invoice and input in database to assign invoice ID
            invoice = dbHandler.getLastInvoice(); // collect invoice and assign invoice ID
            GetDate(); // create date for new invoice
            InvoiceID.setText(Integer.toString(invoice.getInvoiceID()));
        }



        // shuts off scrolling within the main view during the list view
        invoiceItemListView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });


        // create a itemViewListAdapter for the ListView
        invoiceItemViewAdapter = new InvoiceItemViewListAdapter(this, getLayoutInflater());

        // Set ListView to use the ArrayAdapter
        invoiceItemListView.setAdapter(invoiceItemViewAdapter);

        // update the list view using the listview adapter
        invoiceItemViewAdapter.updateData(dbHandler.getInvoiceItemList(invoice.getInvoiceID()));
        invoiceItemListView.setOnItemClickListener(this);

        // method adds invoice items to the invoice
        // calls view that allows for items to be added and passes the invoiceID
        _AddInvoiceItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentBundle = new Intent(Invoice_view.this, AddInvoiceItemView.class);
                Bundle bundle = new Bundle();
                bundle.putInt("SendInvoiceID", invoice.getInvoiceID());
                intentBundle.putExtras(bundle);
                //startActivity(intentBundle);
                startActivityForResult(intentBundle, 1);
            }
        });

        _PrintView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (customer.getCustomerID() != 0){

                    Intent intentBundle = new Intent(Invoice_view.this, InvoicePrintView.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("SendInvoiceID", invoice.getInvoiceID());
                    bundle.putInt("Total", InvoiceTotal);
                    intentBundle.putExtras(bundle);
                    //startActivity(intentBundle);
                    startActivityForResult(intentBundle, 1);
                } else {
                    Toast.makeText(getApplicationContext(), "Add Invoice First", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        mSumRightCheck = true;
//        RCheck.setChecked(mSumRightCheck);
//        CheckTotal();
    }

    public void addInvoiceCustomer (View view) {

        try{

            if (customer.getCustomerID() == 0) {
                setCustomerObject();
                dbHandler.addCustomer(customer);
                customer = dbHandler.getLastCustomer();
                Log.d("tag cust", "initial customer got added");
            }else{
                setCustomerObject();
                boolean result = dbHandler.updateCustomer(customer);
                Log.d("tag cust", "result of updateCustomer = " + result);
            }
        }
        catch (Exception e){
            Log.d("Error", "Invoice not added");
        }

        Log.d("Value of last ID", customer.getCustomerID() + "");

        if(customer == null){
            Log.d("no customer", "");
        } else {

        Log.d("Test Value of ID", customer.getCustomerID()+"");
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_invoice, menu);
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

    public void AddInvoice(View v){

        addInvoiceCustomer(v);
        setInvoiceObject();
        dbHandler.updateInvoice(invoice);
        finish();
    }

    @Override
    public void onClick(View v) {
        CheckTotal();
    }

    private void CheckTotal(){
        int sumB, sumF, sumL, sumR, sumTotal;

        if(BCheck.isChecked()){
            sumB = getSideSum("B");
            bTotal.setText(formatSum(sumB));

        }else {
            sumB = 0;
            bTotal.setText(formatSum(sumB));}

        if(FCheck.isChecked()){
            sumF = getSideSum("F");
            fTotal.setText(formatSum(sumF));

        }else {
            sumF = 0;
            fTotal.setText(formatSum(sumF));}

        if(LCheck.isChecked()){
            sumL = getSideSum("L");
            lTotal.setText(formatSum(sumL));

        }else {
            sumL = 0;
            lTotal.setText(formatSum(sumL));
        }

        if(RCheck.isChecked()){
            sumR = getSideSum("R");
            rTotal.setText(formatSum(sumR));

        }else {
            sumR=0;
            rTotal.setText(formatSum(sumR));}

        sumTotal = sumB + sumF + sumL + sumR;

        finalTotal.setText(formatSum(sumTotal));
        InvoiceTotal=sumTotal;
    }

    private String formatSum(int num){
        double dnum = num * .01;
        String s = String.format("$%.2f", dnum);
        return s;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        InvoiceItem iitem = invoiceItemViewAdapter.getItem(position);
        Intent intentBundle = new Intent(Invoice_view.this, AddInvoiceItemView.class);
        Bundle bundle = new Bundle();
        bundle.putInt("SendInvoiceID", invoice.getInvoiceID());
        bundle.putInt("SendInvoiceItem", iitem.getInvoiceItemId());
        intentBundle.putExtras(bundle);
        //startActivity(intentBundle);
        startActivityForResult(intentBundle, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                invoiceItemViewAdapter.updateData(dbHandler.getInvoiceItemList(invoice.getInvoiceID()));
                // Do something with the contact here (bigger example below)
            }
        }
    }

    @Override
    protected void onResume(){
        invoiceItemViewAdapter.updateData(dbHandler.getInvoiceItemList(invoice.getInvoiceID()));
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if(customer.getCustomerID()== 0){dbHandler.deleteInvoice(invoice.getInvoiceID());}
        super.onDestroy();
    }


    private void GetDate(){

        final Calendar cal = Calendar.getInstance();
        int dd = cal.get(Calendar.DAY_OF_MONTH);
        int mm = cal.get(Calendar.MONTH);
        int yy = cal.get(Calendar.YEAR);
// set current date into textview
        InvoiceDate.setText(new StringBuilder().append(mm + 1).append("").append("-").append(dd).append("-").append(yy));

    }

    private void setRecurringInvoice() {
        InvoiceDate.setText(invoice.getInvoiceDate());
        InvoiceID.setText(Integer.toString(invoice.getInvoiceID()));

        CFirstName.setText(customer.getCustomerFirstName());
        CLastName.setText(customer.getCustomerLastName());
        CStreet.setText(customer.getCustomerStreet());
        CPhone.setText(customer.getCustomerPhone());
        CState.setText(customer.getCustomerState());
        CZip.setText(customer.getCustomerZip());
        CNotes.setText(customer.getCustomerNotes());
        CCity.setText(customer.getCustomerCity());
    }

    private void setCustomerObject(){
        customer.setCustomerFirstName(CFirstName.getText().toString());
        customer.setCustomerLastName(CLastName.getText().toString());
        customer.setCustomerStreet(CStreet.getText().toString());
        customer.setCustomerZip(CZip.getText().toString());
        customer.setCustomerState(CState.getText().toString());
        customer.setCustomerPhone(CPhone.getText().toString());
        customer.setCustomerNotes(CNotes.getText().toString());
        customer.setCustomerCity(CCity.getText().toString());
    }

    private void setInvoiceObject(){
        invoice.setInvoiceDate(InvoiceDate.getText().toString());
        invoice.setCustomerID(customer.getCustomerID());
    }

    private void editTextHolder(){
        // attach text to invoice fields
        InvoiceDate = (EditText) findViewById(R.id.DateText);
        InvoiceID = (EditText) findViewById(R.id.InvoiceIDText);
        //InvoiceID.setText(Integer.toString(invoice.getInvoiceID()));

        //attach edittext to customer fields
        CFirstName = (EditText) findViewById(R.id.FirstNameText);
        CLastName = (EditText) findViewById(R.id.LastNameText);
        CStreet = (EditText) findViewById(R.id.StreetText);
        CCity = (EditText) findViewById(R.id.CityText);
        CState = (EditText) findViewById(R.id.StateText);
        CZip = (EditText) findViewById(R.id.ZipText);
        CPhone = (EditText) findViewById(R.id.PhoneText);
        //CEmail = (EditText) findViewById(R.id.EmailText);
        CNotes = (EditText) findViewById(R.id.NotesText);
        //CustID = (EditText) findViewById(R.id.CustIDText);

        FCheck=(CheckBox)findViewById(R.id.CheckFront);
        BCheck=(CheckBox)findViewById(R.id.CheckBack);
        LCheck=(CheckBox)findViewById(R.id.CheckLeft);
        RCheck=(CheckBox)findViewById(R.id.CheckRight);
        fTotal=(TextView)findViewById(R.id.FTotalValue);
        bTotal=(TextView)findViewById(R.id.BTotalValue);
        rTotal=(TextView)findViewById(R.id.RTotalValue);
        lTotal=(TextView)findViewById(R.id.LTotalValue);
        finalTotal=(TextView)findViewById(R.id.FinalTotal);

        FCheck.setOnClickListener(this);
        BCheck.setOnClickListener(this);
        LCheck.setOnClickListener(this);
        RCheck.setOnClickListener(this);
    }

    private int getSideSum(String Side){
        int sum=0;
        IItemList = dbHandler.getInvoiceItemList(invoice.getInvoiceID());
        InvoiceItem temp;

        if(Side.equals("F")) {
            for (int i = 0; i < IItemList.size(); i++) {
                temp = IItemList.get(i);
                sum += temp.getInvoiceItemFQuantity() * temp.getInvoiceItemRate();
            }
        }
        if(Side.equals("B")) {
            for (int i = 0; i < IItemList.size(); i++) {
                temp = IItemList.get(i);
                sum += temp.getInvoiceItemBQuantity() * temp.getInvoiceItemRate();
            }
        }
        if(Side.equals("L")) {
            for (int i = 0; i < IItemList.size(); i++) {
                temp = IItemList.get(i);
                sum += temp.getInvoiceItemLQuantity() * temp.getInvoiceItemRate();
            }
        }
        if(Side.equals("R")) {
            for (int i = 0; i < IItemList.size(); i++) {
                temp = IItemList.get(i);
                sum += temp.getInvoiceItemRQuantity() * temp.getInvoiceItemRate();
            }
        }
        return sum;
    }

    private void setTotalCheck(){
        FCheck.isChecked();
    }

    public void refresh(View view){
        //dbHandler = new Database(this, null, null, 1);
        invoiceItemViewAdapter.updateData(dbHandler.getInvoiceItemList(invoice.getInvoiceID()));
    }


}
