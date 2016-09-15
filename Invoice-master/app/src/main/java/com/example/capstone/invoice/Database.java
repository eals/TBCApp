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


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

/*
 * Database is the database handler class that extends SQLiteOpenHelper and allows the application
 * to manipulate data within the database.
 */
public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "productDB.db";

    // db table constants for Item
    private static final String TABLE_ITEM = "itemTABLE";
    private static final String ITEM_ID = "_itemID";
    private static final String ITEM_NAME = "_itemName";
    private static final String ITEM_RATE = "_itemRate";

    // db table constants for InvoiceItem
    private static final String TABLE_INVOICE_ITEM = "InvoiceItemTABLE";
    private static final String INVOICE_ITEM_ID = "_invoiceitemID";
    private static final String INVOICE_L_ID = "Invoice_ID";
    private static final String INVOICE_ITEM_NAME = "_invoiceitemName";
    private static final String INVOICE_ITEM_RATE = "_invoiceitemRate";
    private static final String INVOICE_FQUANTITY = "_FQuantity";
    private static final String INVOICE_BQUANTITY = "_BQuantity";
    private static final String INVOICE_LQUANTITY = "_LQuantity";
    private static final String INVOICE_RQUANTITY = "_RQuantity";

    // db table constants for Invoice
    private static final String TABLE_INVOICE = "invoiceTABLE";
    private static final String INVOICE_ID = "_Invoice_ID";
    private static final String INVOICE_DATE = "Date";
    private static final String INVOICE_NOTES = "Notes";
    private static final String INVOICE_CUSTOMER_ID = "CustID";

    // db table constants for Customer
    private static final String TABLE_CUSTOMER = "CustomerTABLE";
    private static final String CUSTOMER_ID = "CustID";
    private static final String CUSTOMER_FIRST_NAME = "CustFName";
    private static final String CUSTOMER_LAST_NAME = "CustLName";
    private static final String CUSTOMER_PHONE1 = "CustPhone";
    private static final String CUSTOMER_STREET = "CustStreet";
    private static final String CUSTOMER_ZIP = "CustZip";
    private static final String CUSTOMER_STATE = "CustState";
    private static final String CUSTOMER_CITY = "CustCity";
    private static final String CUSTOMER_NOTES = "CustNotes";



    private static final String TABLE_PRODUCTS = "products";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "productname";
    public static final String COLUMN_QUANTITY = "quantity";

    // database constructor: when you call use current context
    // example Database db = new Database(this, null, null, 1);
    public Database(Context context, String name,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_PRODUCTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_PRODUCTNAME
                + " TEXT," + COLUMN_QUANTITY + " INTEGER" + ")";

        String CREATE_INVOICE_TABLE = "CREATE TABLE " +
                TABLE_INVOICE + "("
                + INVOICE_ID + " INTEGER PRIMARY KEY," + INVOICE_CUSTOMER_ID
                + " INTEGER," + INVOICE_DATE + " TEXT," + INVOICE_NOTES + " TEXT" + ")";

        String CREATE_ITEMS_TABLE = "CREATE TABLE " +
                TABLE_ITEM + "("
                + ITEM_ID + " INTEGER PRIMARY KEY," + ITEM_NAME
                + " TEXT," + ITEM_RATE + " INTEGER" + ")";

        String CREATE_INVOICE_ITEMS_TABLE = "CREATE TABLE " +
                TABLE_INVOICE_ITEM + "("
                + INVOICE_ITEM_ID + " INTEGER PRIMARY KEY," + INVOICE_ID + " INTEGER," + INVOICE_ITEM_NAME
                + " TEXT," + INVOICE_ITEM_RATE + " INTEGER," + INVOICE_FQUANTITY + " INTEGER," + INVOICE_BQUANTITY + " INTEGER,"
                + INVOICE_LQUANTITY + " INTEGER," + INVOICE_RQUANTITY + " INTEGER" + ")";

        String CREATE_CUSTOMER_TABLE = "CREATE TABLE " +
                TABLE_CUSTOMER + "("
                + CUSTOMER_ID + " INTEGER PRIMARY KEY," + CUSTOMER_FIRST_NAME
                + " TEXT," + CUSTOMER_LAST_NAME + " TEXT," + CUSTOMER_STREET + " TEXT," + CUSTOMER_CITY + " TEXT," + CUSTOMER_ZIP + " TEXT,"
                + CUSTOMER_STATE + " TEXT," + CUSTOMER_PHONE1 + " TEXT,"+ CUSTOMER_NOTES + " TEXT" + ")";

        db.execSQL(CREATE_INVOICE_TABLE);
        db.execSQL(CREATE_INVOICE_ITEMS_TABLE);
        db.execSQL(CREATE_CUSTOMER_TABLE);
        db.execSQL(CREATE_ITEMS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public void addInvoiceItem(InvoiceItem invoiceitem) {

        ContentValues values = new ContentValues();
        values.put(INVOICE_ID, invoiceitem.getInvoiceId());
        values.put(INVOICE_ITEM_NAME, invoiceitem.getInvoiceItemName());
        values.put(INVOICE_ITEM_RATE, invoiceitem.getInvoiceItemRate());
        values.put(INVOICE_FQUANTITY, invoiceitem.getInvoiceItemFQuantity());
        values.put(INVOICE_BQUANTITY, invoiceitem.getInvoiceItemBQuantity());
        values.put(INVOICE_LQUANTITY, invoiceitem.getInvoiceItemLQuantity());
        values.put(INVOICE_RQUANTITY, invoiceitem.getInvoiceItemRQuantity());
        Log.d("adding: ", "Invoice Item inserting...");
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_INVOICE_ITEM, null, values);
        Log.d("adding: ", "Invoice Item inserted...");
        db.close();
    }

    public InvoiceItem findInvoiceItem(String itemname) {
        String query = "Select * FROM " + TABLE_INVOICE_ITEM + " WHERE " + INVOICE_ITEM_NAME + " =  \"" + itemname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        InvoiceItem invoiceitem = new InvoiceItem();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            invoiceitem.setInvoiceItemId(Integer.parseInt(cursor.getString(0)));
            invoiceitem.setInvoiceId(Integer.parseInt(cursor.getString(1)));
            invoiceitem.setInvoiceItemName(cursor.getString(2));
            invoiceitem.setInvoiceItemRate(Integer.parseInt(cursor.getString(3)));
            invoiceitem.setInvoiceItemFQuantity(Integer.parseInt(cursor.getString(4)));
            invoiceitem.setInvoiceItemBQuantity(Integer.parseInt(cursor.getString(5)));
            invoiceitem.setInvoiceItemLQuantity(Integer.parseInt(cursor.getString(6)));
            invoiceitem.setInvoiceItemRQuantity(Integer.parseInt(cursor.getString(7)));
            cursor.close();
        } else {
            invoiceitem = null;
        }
        db.close();
        return invoiceitem;
    }

    public ArrayList<InvoiceItem> getInvoiceItemList(int invoiceID) {
        ArrayList<InvoiceItem> invoiceitemlist = new ArrayList<>();
        String query = "Select * FROM " + TABLE_INVOICE_ITEM + " WHERE " + INVOICE_ID + " =  \"" + Integer.toString(invoiceID) + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                InvoiceItem invoiceitem = new InvoiceItem();
                invoiceitem.setInvoiceItemId(Integer.parseInt(cursor.getString(0)));
                invoiceitem.setInvoiceId(Integer.parseInt(cursor.getString(1)));
                invoiceitem.setInvoiceItemName(cursor.getString(2));
                invoiceitem.setInvoiceItemRate(Integer.parseInt(cursor.getString(3)));
                invoiceitem.setInvoiceItemFQuantity(Integer.parseInt(cursor.getString(4)));
                invoiceitem.setInvoiceItemBQuantity(Integer.parseInt(cursor.getString(5)));
                invoiceitem.setInvoiceItemLQuantity(Integer.parseInt(cursor.getString(6)));
                invoiceitem.setInvoiceItemRQuantity(Integer.parseInt(cursor.getString(7)));
                invoiceitemlist.add(invoiceitem);
                cursor.moveToNext();
            }
            cursor.close();
        } else {
            //invoiceitemlist = null; //creates a null pointer error
        }
        db.close();
        return invoiceitemlist;
    }

    public void addItem(Item item) {

        ContentValues values = new ContentValues();
        values.put(ITEM_NAME, item.getItemName());
        values.put(ITEM_RATE, item.getItemRate());
        Log.d("adding: ", "inserting...");
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_ITEM, null, values);
        db.close();
    }

    public void addInvoice(Invoice invoice) {

        ContentValues values = new ContentValues();
        values.put(INVOICE_CUSTOMER_ID, invoice.getCustomerID());
        values.put(INVOICE_DATE, invoice.getInvoiceDate());
        values.put(INVOICE_NOTES, invoice.getInvoiceNotes());
        Log.d("adding: ", "inserting...");
        SQLiteDatabase db = this.getWritableDatabase();
        boolean test;
        db.insert(TABLE_INVOICE, null, values);
        db.close();
    }

    public void addCustomer(Customer customer) {

        ContentValues values = new ContentValues();
        values.put(CUSTOMER_FIRST_NAME, customer.getCustomerFirstName());
        values.put(CUSTOMER_LAST_NAME, customer.getCustomerLastName());
        values.put(CUSTOMER_PHONE1, customer.getCustomerPhone());
        values.put(CUSTOMER_STREET, customer.getCustomerStreet());
        values.put(CUSTOMER_CITY, customer.getCustomerCity());
        values.put(CUSTOMER_ZIP, customer.getCustomerZip());
        values.put(CUSTOMER_STATE, customer.getCustomerState());
        values.put(CUSTOMER_NOTES, customer.getCustomerNotes());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_CUSTOMER, null, values);
        db.close();
    }

    public boolean updateInvoice(Invoice invoice) {

        ContentValues values = new ContentValues();
        values.put(INVOICE_CUSTOMER_ID, invoice.getCustomerID());
        values.put(INVOICE_DATE, invoice.getInvoiceDate());
        values.put(INVOICE_NOTES, invoice.getInvoiceNotes());
        Log.d("adding: ", "inserting...");
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = db.update(TABLE_INVOICE, values, INVOICE_ID + "=" + invoice.getInvoiceID() ,null) > 0;
        db.close();
        return result;
    }

    public boolean updateItem(Item item) {

        ContentValues values = new ContentValues();
        values.put(ITEM_NAME, item.getItemName());
        values.put(ITEM_RATE, item.getItemRate());
        Log.d("adding: ", "inserting...");
        SQLiteDatabase db = this.getWritableDatabase();

        boolean result = db.update(TABLE_ITEM, values, ITEM_ID + "=" + item.getItemId() ,null) > 0;
        db.close();
        return result;
    }

    public boolean updateCustomer(Customer customer) {
        ContentValues values = new ContentValues();
        values.put(CUSTOMER_FIRST_NAME, customer.getCustomerFirstName());
        values.put(CUSTOMER_LAST_NAME, customer.getCustomerLastName());
        values.put(CUSTOMER_PHONE1, customer.getCustomerPhone());
        values.put(CUSTOMER_STREET, customer.getCustomerStreet());
        values.put(CUSTOMER_CITY, customer.getCustomerCity());
        values.put(CUSTOMER_ZIP, customer.getCustomerZip());
        values.put(CUSTOMER_STATE, customer.getCustomerState());
        values.put(CUSTOMER_NOTES, customer.getCustomerNotes());

        SQLiteDatabase db = this.getWritableDatabase();

        boolean result = db.update(TABLE_CUSTOMER, values, CUSTOMER_ID + "=" + customer.getCustomerID() ,null) > 0;
        db.close();
        return result;
    }

    public boolean updateInvoiceItem(InvoiceItem invoiceitem) {
        ContentValues values = new ContentValues();
        values.put(INVOICE_ITEM_NAME, invoiceitem.getInvoiceItemName());
        values.put(INVOICE_ITEM_RATE, invoiceitem.getInvoiceItemRate());
        values.put(INVOICE_FQUANTITY, invoiceitem.getInvoiceItemFQuantity());
        values.put(INVOICE_BQUANTITY, invoiceitem.getInvoiceItemBQuantity());
        values.put(INVOICE_LQUANTITY, invoiceitem.getInvoiceItemLQuantity());
        values.put(INVOICE_RQUANTITY, invoiceitem.getInvoiceItemRQuantity());

        SQLiteDatabase db = this.getWritableDatabase();

        boolean result = db.update(TABLE_INVOICE_ITEM, values, INVOICE_ITEM_ID + "=" + invoiceitem.getInvoiceItemId() ,null) > 0;
        db.close();
        return result;
    }



    // use this method to find invoice by customer id
    public Invoice findInvoice(int custid) {
        String query = "Select * FROM " + TABLE_INVOICE + " WHERE " + INVOICE_CUSTOMER_ID + " =  \"" + custid + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Invoice invoice = new Invoice();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            invoice.setInvoiceID(Integer.parseInt(cursor.getString(0)));
            invoice.setCustomerID(Integer.parseInt(cursor.getString(1)));
            invoice.setInvoiceDate(cursor.getString(2));
            invoice.setInvoiceNotes(cursor.getString(3));
            cursor.close();
        } else {
            invoice = null;
        }
        db.close();
        return invoice;
    }

    // use this method to find invoice by invoice id
    public Invoice getInvoice(int invoiceid) {
        String query = "Select * FROM " + TABLE_INVOICE + " WHERE " + INVOICE_ID + " =  \"" + invoiceid + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Invoice invoice = new Invoice();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            invoice.setInvoiceID(Integer.parseInt(cursor.getString(0)));
            invoice.setCustomerID(Integer.parseInt(cursor.getString(1)));
            invoice.setInvoiceDate(cursor.getString(2));
            invoice.setInvoiceNotes(cursor.getString(3));
            cursor.close();
        } else {
            invoice = null;
        }
        db.close();
        return invoice;
    }

    public Invoice getLastInvoice() {
        String query = "Select * FROM " + TABLE_INVOICE;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Invoice invoice = new Invoice();

        if (cursor.moveToFirst()) {
            cursor.moveToLast();
            invoice.setInvoiceID(Integer.parseInt(cursor.getString(0)));
            invoice.setCustomerID(Integer.parseInt(cursor.getString(1)));
            invoice.setInvoiceDate(cursor.getString(2));
            invoice.setInvoiceNotes(cursor.getString(3));
            cursor.close();
        } else {
            invoice = null;
        }
        db.close();
        return invoice;
    }

    // use this method to find invoice by invoice id
    public ArrayList<Invoice> getInvoiceList() {
        String query = "Select * FROM " + TABLE_INVOICE;
        ArrayList<Invoice> invoiceList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Invoice invoice = new Invoice();
                invoice.setInvoiceID(Integer.parseInt(cursor.getString(0)));
                invoice.setCustomerID(Integer.parseInt(cursor.getString(1)));
                invoice.setInvoiceDate(cursor.getString(2));
                invoice.setInvoiceNotes(cursor.getString(3));
                invoiceList.add(invoice);
                cursor.moveToNext();
            }
            cursor.close();
        } else {
            //itemList = null; //creates a null pointer error
        }
        db.close();
        return invoiceList;
    }

    public Customer getLastCustomer(){
        String query = "Select * FROM " + TABLE_CUSTOMER;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Customer cust = new Customer();

        if (cursor.moveToFirst()) {
            cursor.moveToLast();
            cust.setCustomerID(Integer.parseInt(cursor.getString(0)));
            cust.setCustomerFirstName(cursor.getString(1));
            cust.setCustomerLastName(cursor.getString(2));
            cust.setCustomerStreet(cursor.getString(3));
            cust.setCustomerCity(cursor.getString(4));
            cust.setCustomerZip(cursor.getString(5));
            cust.setCustomerState(cursor.getString(6));
            cust.setCustomerPhone(cursor.getString(7));
            cust.setCustomerNotes(cursor.getString(8));
            cursor.close();
        } else {
            cust = null;
        }
        db.close();

        return cust;
    }

    public Customer findCustomer(String customerName){
        String query = "Select * FROM " + TABLE_CUSTOMER + " WHERE " + CUSTOMER_LAST_NAME + " =  \"" + customerName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Customer cust = new Customer();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            cust.setCustomerID(Integer.parseInt(cursor.getString(0)));
            cust.setCustomerFirstName(cursor.getString(1));
            cust.setCustomerLastName(cursor.getString(2));
            cust.setCustomerStreet(cursor.getString(3));
            cust.setCustomerCity(cursor.getString(4));
            cust.setCustomerZip(cursor.getString(5));
            cust.setCustomerState(cursor.getString(6));
            cust.setCustomerPhone(cursor.getString(7));
            cust.setCustomerNotes(cursor.getString(8));
            cursor.close();
        } else {
            cust = null;
        }
        db.close();

        return cust;
    }

    public InvoiceItem getInvoiceItem(int invoiceitemID){
        String query = "Select * FROM " + TABLE_INVOICE_ITEM + " WHERE " + INVOICE_ITEM_ID + " =  \"" + invoiceitemID + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        InvoiceItem iitem = new InvoiceItem();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            iitem.setInvoiceItemId(Integer.parseInt(cursor.getString(0)));
            iitem.setInvoiceId(Integer.parseInt(cursor.getString(1)));
            iitem.setInvoiceItemName(cursor.getString(2));
            iitem.setInvoiceItemRate(Integer.parseInt(cursor.getString(3)));
            iitem.setInvoiceItemFQuantity(Integer.parseInt(cursor.getString(4)));
            iitem.setInvoiceItemBQuantity(Integer.parseInt(cursor.getString(5)));
            iitem.setInvoiceItemLQuantity(Integer.parseInt(cursor.getString(6)));
            iitem.setInvoiceItemRQuantity(Integer.parseInt(cursor.getString(7)));
            cursor.close();
        } else {
            iitem = null;
        }
        db.close();

        return iitem;
    }

    public Customer getCustomer(int customerID){
        String query = "Select * FROM " + TABLE_CUSTOMER + " WHERE " + CUSTOMER_ID + " =  \"" + customerID + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Customer cust = new Customer();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            cust.setCustomerID(Integer.parseInt(cursor.getString(0)));
            cust.setCustomerFirstName(cursor.getString(1));
            cust.setCustomerLastName(cursor.getString(2));
            cust.setCustomerStreet(cursor.getString(3));
            cust.setCustomerCity(cursor.getString(4));
            cust.setCustomerZip(cursor.getString(5));
            cust.setCustomerState(cursor.getString(6));
            cust.setCustomerPhone(cursor.getString(7));
            cust.setCustomerNotes(cursor.getString(8));
            cursor.close();
        } else {
            cust = null;
        }
        db.close();

        return cust;
    }

    public Item findItem(String itemname) {
        String query = "Select * FROM " + TABLE_ITEM + " WHERE " + ITEM_NAME + " =  \"" + itemname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Item item = new Item();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            item.setItemId(Integer.parseInt(cursor.getString(0)));
            item.setItemName(cursor.getString(1));
            item.setItemRate(Integer.parseInt(cursor.getString(2)));
            cursor.close();
        } else {
            item = null;
        }
        db.close();
        return item;
    }

    // Returns an ArrayList of items, if no items then returns null;
    public ArrayList<Item> getItemList() {
        ArrayList<Item> itemList = new ArrayList<>();
        String query = "Select * FROM " + TABLE_ITEM;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Item item = new Item();
                item.setItemId(Integer.parseInt(cursor.getString(0)));
                item.setItemName(cursor.getString(1));
                item.setItemRate(Integer.parseInt(cursor.getString(2)));
                itemList.add(item);
                cursor.moveToNext();
            }
            cursor.close();
        } else {
            //itemList = null; //creates a null pointer error
        }
        db.close();
        return itemList;
    }

    public boolean deleteItem(String itemname) {

        boolean result = false;

        String query = "Select * FROM " + TABLE_ITEM + " WHERE " + ITEM_NAME + " =  \"" + itemname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Item item = new Item();

        if (cursor.moveToFirst()) {
            item.setItemId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_ITEM, ITEM_ID + " = ?",
                    new String[] { String.valueOf(item.getItemId()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean deleteItem(int itemid) {

        boolean result;
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(TABLE_ITEM, ITEM_ID + " = " + itemid, null) > 0;
        db.close();
        return result;

    }

    public boolean deleteInvoiceItem(int invoiceitemid) {

        boolean result;
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(TABLE_INVOICE_ITEM, INVOICE_ITEM_ID + " = " + invoiceitemid, null) > 0;
        db.close();
        return result;

    }

    public boolean deleteInvoice(int invoiceid) {

        boolean result;
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(TABLE_INVOICE, INVOICE_ID + " = " + invoiceid, null) > 0;
        db.close();
        return result;

    }

}
