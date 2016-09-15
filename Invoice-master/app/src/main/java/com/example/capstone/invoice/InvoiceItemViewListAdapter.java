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

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * InvoiceItemViewListAdapter is an adapter to take Invoice Item data and convert it to an array
 * that the Invoice Item List view understands
 */
public class InvoiceItemViewListAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<InvoiceItem> invoiceItemAList;

    public InvoiceItemViewListAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mInflater = inflater;
        invoiceItemAList = new ArrayList();
    }


    @Override
    public int getCount() {
        return invoiceItemAList.size();
    }

    @Override
    public InvoiceItem getItem(int position) {
        return invoiceItemAList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            //Inflate the custom row layout from your XML.
            convertView = mInflater.inflate(R.layout.invoice_item_list_view, null);

            //create a "Holder" with subviews
            holder = new ViewHolder();
            holder.titleNameView = (TextView) convertView.findViewById(R.id.text_item_name);
            holder.titleRateView = (TextView) convertView.findViewById(R.id.text_item_rate);
            holder.titleRightQuantity = (TextView) convertView.findViewById(R.id.text_InvoiceRQuantity);
            holder.titleLeftQuantity = (TextView) convertView.findViewById(R.id.text_InvoiceLQuantity);
            holder.titleFrontQuantity = (TextView) convertView.findViewById(R.id.text_InvoiceFQuantity);
            holder.titleBackQuantity = (TextView) convertView.findViewById(R.id.text_InvoiceBQuantity);

            // hang on to this holder for future recyclage
            convertView.setTag(holder);
        } else {
            //skip all the expensive inflation/findViewById
            //and just get the holder you already made
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the curent item
        Log.d("View positon:", "" + position);
        InvoiceItem Iitem = getItem(position);

        String itemTitle = "";
        String rateTitle = "";
        String qFront ="";
        String qBack ="";
        String qLeft ="";
        String qRight ="";

        if (Iitem.getInvoiceItemName()!= null){
            itemTitle = Iitem.getInvoiceItemName();
        }

        rateTitle = moneyFormat(Iitem);
        qFront = Integer.toString(Iitem.getInvoiceItemFQuantity());
        qBack = Integer.toString(Iitem.getInvoiceItemBQuantity());
        qLeft = Integer.toString(Iitem.getInvoiceItemLQuantity());
        qRight = Integer.toString(Iitem.getInvoiceItemRQuantity());
        // Send these Strings to the TextViews for display
        holder.titleNameView.setText(itemTitle);
        holder.titleRateView.setText(rateTitle);
        holder.titleFrontQuantity.setText(qFront);
        holder.titleBackQuantity.setText(qBack);
        holder.titleLeftQuantity.setText(qLeft);
        holder.titleRightQuantity.setText(qRight);


        return convertView;
    }

    private String moneyFormat(InvoiceItem item){
        Double temp = item.getInvoiceItemRate()*.01;
        String s = String.format("$%.2f", temp );
        return s;
    }

    public void updateData(ArrayList<InvoiceItem> aitem){
        invoiceItemAList = aitem;
        notifyDataSetChanged();
    }
    // this is used so you only ever have to do
// inflation and finding by ID once ever per View
    private static class ViewHolder {
        public TextView titleNameView;
        public TextView titleRateView;
        public TextView titleRightQuantity;
        public TextView titleLeftQuantity;
        public TextView titleFrontQuantity;
        public TextView titleBackQuantity;
    }
}
