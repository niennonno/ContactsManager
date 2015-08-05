package org.example.adityavikram.contactsmanager;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ContactListActivity extends ActionBarActivity {
    private static final String tag = "ContactListActivity";

    private ContactList PhoneContact;
    private ContactAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Contact temp=new Contact();
        PhoneContact=ContactList.getsInstance();

        ContentResolver cont= getContentResolver();
        Cursor c= cont.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);


        ArrayList<String> contacts = new ArrayList<>();         //Storing ContactNumber
        ArrayList<String> name = new ArrayList<>();             //Storing ContactName

        if(c.moveToFirst()) {

            do {String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))>0) {
                    Cursor num = cont.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contactId}, null);
                    while (num.moveToNext()) {
                        String contactNum = num.getString(num.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contacts.add(contactNum);
                        String contactName=num.getString(num.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        name.add(contactName); }
                    num.close();
                }
            }
            while (c.moveToNext());

        }

        for (int i = 0; i < contacts.size(); i++) {
            Log.d(tag, contacts.get(i));
            Log.d(tag, name.get(i));
            temp.setCNum(contacts.get(i));
            temp.setCName(name.get(i));
            PhoneContact.add(temp);
        }

        ListView listView = (ListView) findViewById(R.id.Contact_list_view);
        mAdapter=new ContactAdapter(PhoneContact);
        listView.setAdapter(mAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int previousFirstItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem > previousFirstItem) {
                    getSupportActionBar().hide();
                }
                else if (firstVisibleItem < previousFirstItem) {
                    getSupportActionBar().show();
                }

                previousFirstItem = firstVisibleItem;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ContactListActivity.this,ContactViewActivity.class);
                i.putExtra(ContactViewActivity.EXTRA, position);
                startActivity(i);
            }
        });

        c.close();

    }



    private class ContactAdapter extends ArrayAdapter<Contact> {
        ContactAdapter(ArrayList<Contact> PhoneContact) {
            super(ContactListActivity.this, R.layout.contact_row, R.id.row_name, PhoneContact);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);

            Contact contact = getItem(position);

            TextView nameTextView = (TextView) convertView.findViewById(R.id.row_name);
            nameTextView.setText(contact.getCName());
            return convertView;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
