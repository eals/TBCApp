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
 * ItemViewListAdapter is the adapter that allows the item view data to be converted to an array
 * that the item ListView will understand
 */
public class ItemViewListAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Item> itemAList;

    public ItemViewListAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mInflater = inflater;
        itemAList = new ArrayList();
    }


    @Override
    public int getCount() {
        return itemAList.size();
    }

    @Override
    public Item getItem(int position) {
        return itemAList.get(position);
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
            convertView = mInflater.inflate(R.layout.item_list_view, null);

            //create a "Holder" with subviews
            holder = new ViewHolder();
            holder.titleNameView = (TextView) convertView.findViewById(R.id.text_item_name);
            holder.titleRateView = (TextView) convertView.findViewById(R.id.text_item_rate);

            // hang on to this holder for future recyclage
            convertView.setTag(holder);
        } else {
            //skip all the expensive inflation/findViewById
            //and just get the holder you already made
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the curent item
        Log.d("View positon:", "" + position);
        Item item = getItem(position);

        String itemTitle = "";
        String rateTitle = "";

        if (item.getItemName()!= null){
            itemTitle = item.getItemName();
        }

        rateTitle = moneyFormat(item);
        // Send these Strings to the TextViews for display
        holder.titleNameView.setText(itemTitle);
        holder.titleRateView.setText(rateTitle);


        return convertView;
    }

    private String moneyFormat(Item item){
        Double temp = item.getItemRate()*.01;
        String s = String.format("$%.2f", temp );
        return s;
    }

    public void updateData(ArrayList<Item> aitem){
        itemAList = aitem;
        notifyDataSetChanged();
    }
    // this is used so you only ever have to do
// inflation and finding by ID once ever per View
    private static class ViewHolder {
        public TextView titleNameView;
        public TextView titleRateView;
    }
}
