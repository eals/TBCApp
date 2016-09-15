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

/**
 *  Invoice is an object class to create an invoice object
 */
public class Invoice {
    private int InvoiceID;
    private int CustomerID;
    private String InvoiceDate;
    private String InvoiceNotes;

    public Invoice(){}

    public Invoice(int invoiceID, int customerID, int invoiceItemID, String invoiceDate, String invoiceNotes) {
        InvoiceID = invoiceID;
        CustomerID = customerID;
        InvoiceDate = invoiceDate;
        InvoiceNotes = invoiceNotes;
    }

    public int getInvoiceID() {
        return InvoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        InvoiceID = invoiceID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }



    public String getInvoiceDate() {
        return InvoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        InvoiceDate = invoiceDate;
    }

    public String getInvoiceNotes() {
        return InvoiceNotes;
    }

    public void setInvoiceNotes(String invoiceNotes) {
        InvoiceNotes = invoiceNotes;
    }
}
