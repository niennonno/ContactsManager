package org.example.adityavikram.contactsmanager;

import java.util.ArrayList;

/**
 * Created by Aditya Vikram on 8/5/2015.
 */
public class ContactList extends ArrayList<Contact> {
    private static ContactList sInstance=null;

    private ContactList(){}

    public static ContactList getsInstance(){
        if(sInstance==null){
            sInstance=new ContactList();
        }
        return sInstance;
    }

}
