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
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class InvoicePrintView extends AppCompatActivity {

    private TextView mFirstName;
    private TextView mLastName;
    private TextView mCity;
    private TextView mState;
    private TextView mZip;
    private TextView mNotes;
    private TextView mPhone;
    private TextView mTotal;
    private Invoice mInvoice;
    private Customer mCustomer;
    private int mInvoiceId=0;
    private int mInvoiceTotal;
    private Database mDbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_print_view);
        mDbHandler = new Database(this, null, null, 1);
        mFirstName = (TextView) findViewById(R.id.CustomerFirst);
        mLastName = (TextView) findViewById(R.id.CustomerLast);
        mCity = (TextView) findViewById(R.id.City);
        mState = (TextView) findViewById(R.id.State);
        mZip = (TextView) findViewById(R.id.Zip);
        mNotes = (TextView) findViewById(R.id.Notes);
        mPhone = (TextView) findViewById(R.id.Phone);
        mTotal = (TextView) findViewById(R.id.Total);
        mInvoice = new Invoice();
        mCustomer = new Customer();

        //get Bundle from intent and extract the invoiceId
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (extrasBundle != null){
            Log.d("bundle", "");
            //we have a bundle

            if(extrasBundle.getInt("SendInvoiceID")!=0){
                mInvoiceId = extrasBundle.getInt("SendInvoiceID");
                mInvoice = mDbHandler.getInvoice(mInvoiceId);
                mCustomer = mDbHandler.getCustomer(mInvoice.getCustomerID());
                setCustomer();

                Log.d("bundle", "" + mInvoiceId);}

            if(extrasBundle.getInt("Total")!=0){
                mInvoiceTotal = extrasBundle.getInt("Total");
                double total = mInvoiceTotal * .01;
                String s = String.format("$%.2f", total);
                mTotal.setText(s);

            }
        }

        //Test method
        findViewById(R.id.print).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = takeScreenshot();
                saveBitmap(bitmap);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_invoice_print_view, menu);
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

    // Test screenshot Here
    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot" + mInvoiceId +".png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }
    // Test screenshot Here

    private void setCustomer(){
        mFirstName.setText(mCustomer.getCustomerFirstName());
        mLastName.setText(mCustomer.getCustomerLastName());
        mCity.setText(mCustomer.getCustomerCity() + ",");
        mState.setText(mCustomer.getCustomerState());
        mZip.setText(mCustomer.getCustomerZip());
        mNotes.setText(mCustomer.getCustomerNotes());
        mPhone.setText(mCustomer.getCustomerPhone());
    }
}
