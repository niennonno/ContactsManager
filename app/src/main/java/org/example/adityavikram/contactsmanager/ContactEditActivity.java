package org.example.adityavikram.contactsmanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ContactEditActivity extends ActionBarActivity {
    public static final String EXTRA = "CEA_EXTRA";
    public static final String TAG = "ContactEditActivity";

    private Contact mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);

        int position = getIntent().getIntExtra(EXTRA,0);
        mContact=ContactList.getsInstance().get(position);

        Toolbar toolbar = (Toolbar) findViewById(R.id.contact_edit_toolbar);
        toolbar.setTitle("Edit Contact");
        toolbar.setNavigationIcon(R.drawable.ic_done);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editName = (EditText) findViewById(R.id.edit_name);
                mContact.setCName(editName.getText().toString());

                mContact.ContactNum = getSectionValues(R.id.phone_number_section);

            }
        });

        EditText nEditName = (EditText) findViewById(R.id.edit_name);
        nEditName.setText(mContact.getCName());

        LinearLayout phoneNumberSection = (LinearLayout) findViewById(R.id.phone_number_section);
        for (int i = 0; i < mContact.ContactNum.size(); ++i) {
            EditText et = new EditText(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            et.setLayoutParams(lp);
            et.setText(mContact.ContactNum.get(i));
            phoneNumberSection.addView(et);
        }
        TextView addNewPhoneNumber = (TextView) findViewById(R.id.add_new_phone);
        addNewPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToSection(R.id.phone_number_section, "");

                Toast.makeText(ContactEditActivity.this, "Contact Saved", Toast.LENGTH_LONG)
                        .show();

                finish();
            }
        });
    }

    private ArrayList<String> getSectionValues(int sectionID) {
        ArrayList<String> values = new ArrayList<String>();
        LinearLayout section = (LinearLayout) findViewById(sectionID);
        for (int i = 0; i < section.getChildCount(); i++) {
            EditText editNumber = (EditText) section.getChildAt(i);
            values.add(editNumber.getText().toString());
        }
        return values;
    }

    private void addToSection(int sectionID, String value) {
        LinearLayout section = (LinearLayout) findViewById(sectionID);
        EditText et = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        et.setLayoutParams(lp);
        et.setText(value);
        section.addView(et);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_edit, menu);
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
