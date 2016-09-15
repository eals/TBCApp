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

import java.util.Comparator;

/**
 * Item is an object of Item
 */
public class Item implements Comparable<Item>{

    private int itemId;
    private String itemName;
    private int itemRate;

    public Item (){}

    public Item (int id, String name, int Rate){
        this.itemId=id;
        this.itemName=name;
        this.itemRate=Rate;
    }

    public Item (String name, int Rate){
        this.itemName=name;
        this.itemRate=Rate;
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemRate() {
        return this.itemRate;
    }

    public void setItemRate(int itemRate) {
        this.itemRate = itemRate;
    }

    @Override
    public int compareTo(Item another) {

        int compareQuantity = (another).getItemRate();
        return this.itemRate - compareQuantity;
    }

    public static Comparator<Item> ItemNameComparator
            = new Comparator<Item>() {

        public int compare(Item item1, Item item2) {

            String itemName1 = item1.getItemName().toUpperCase();
            String itemName2 = item2.getItemName().toUpperCase();

            //ascending order
            return itemName1.compareTo(itemName2);

            //descending order
            //return fruitName2.compareTo(fruitName1);
        }
    };
}

