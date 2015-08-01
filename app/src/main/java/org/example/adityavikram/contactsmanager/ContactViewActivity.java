package org.example.adityavikram.contactsmanager;

import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class ContactViewActivity extends ActionBarActivity {

    public static final String EXTRA = "CVA_Contact";
    private static final String TAG = "ContactViewActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        RelativeLayout headerSection = (RelativeLayout)findViewById(R.id.contact_view_header);
        Display display= getWindowManager().getDefaultDisplay();
        Point point=new Point();
        display.getSize(point);

        int width=point.x;
        int height=point.y;
        headerSection.setLayoutParams(new LinearLayout.LayoutParams(width, (int) (width * (9.0 / 16.0))));


        Contact contact = (Contact) getIntent().getSerializableExtra(EXTRA);
        TextView contactName=(TextView)findViewById(R.id.contact_view_name);
        contactName.setText(contact.getCName());

        Toolbar toolbar=(Toolbar)findViewById(R.id.contact_view_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.contact_view_edit) {
                    return true;
                }
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.menu_contact_view);
        ListView listView= (ListView)findViewById(R.id.contact_view_field);
        listView.setAdapter(new FieldsAdapter(contact.ContactNum));
    }

    private class FieldsAdapter extends BaseAdapter{
        ArrayList<String> numbers;
        FieldsAdapter(ArrayList <String> numbers){
            this.numbers=numbers;
        }

        @Override
        public int getCount() {

            return numbers.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=ContactViewActivity.this.getLayoutInflater().inflate(R.layout.contact_view_field_row,parent,false);
            }

            String value=(String)getItem(position);
            TextView contactValue = (TextView)convertView.findViewById(R.id.contact_view_row_field);
            contactValue.setText(value);

            ImageView iv= (ImageView)convertView.findViewById(R.id.field_icon);
            iv.setImageResource(R.drawable.ic_call);


            return convertView;
        }

        private boolean isFirst( int position){
            if (position==0||position==numbers.size()){
                return true;
            }else{
                return false;

            }

        }

        @Override
        public long getItemId(int position) {

            return 0;
        }

        @Override
        public Object getItem(int position) {

            return numbers.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_view, menu);
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
