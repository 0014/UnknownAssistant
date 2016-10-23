package com.a0014.unknownassistant.Activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.a0014.unknownassistant.Models.CustomContactsListViewAdapter;
import com.a0014.unknownassistant.Models.TinyDB;
import com.a0014.unknownassistant.R;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    static final int PICK_CONTACT=1;

    Button btnAdd, btnDone, btnRemove;
    ListView lstContacts;

    CustomContactsListViewAdapter adapter;
    TinyDB tinydb; // our database where the contacts are stored

    ArrayList<String> contacts = new ArrayList<String>();
    int selectedItem = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDone = (Button) findViewById(R.id.btnDone);
        btnRemove = (Button) findViewById(R.id.btnRemove);
        lstContacts = (ListView) findViewById(R.id.lstContacts);

        // Get template names and templates
        tinydb = new TinyDB(this);
        contacts = tinydb.getListString("contacts");
        HighlightSelected();
        // Set the ArrayAdapter as the ListView's adapter.
        adapter = new CustomContactsListViewAdapter(this, contacts); // set the adapter
        lstContacts.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // pass the command to the controller to apply the command
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // pass the command to the controller to apply the command
                if(selectedItem == -1)
                    return;

                contacts.remove(selectedItem);
                tinydb.putListString("contacts", contacts);
                selectedItem = -1;

                adapter.notifyDataSetChanged();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // pass the command to the controller to apply the command
                finish();
            }
        });

        lstContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == -1) return; // if empty is chosen do nothing

                selectedItem = i; // choose the selected item
                HighlightSelected(); // highlight the selected item from the list

                adapter.notifyDataSetChanged(); // refresh list view
            }
        });
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c =  managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {

                        String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                    null, null);
                            phones.moveToFirst();
                            String cNumber = phones.getString(phones.getColumnIndex("data1"));
                            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                            contacts.add(name + ", " + cNumber + ", " + "colorNotChanged");
                            tinydb.putListString("contacts", contacts);

                            adapter.notifyDataSetChanged();
                        }
                    }
                }
                break;
        }
    }

    private void HighlightSelected()
    {
        for (int j = 0; j < contacts.size(); j++){
            if(selectedItem == j)
                contacts.set(j, contacts.get(j).replace("colorNotChanged", "colorChanged"));
            else
                contacts.set(j, contacts.get(j).replace("colorChanged", "colorNotChanged"));

            tinydb.putListString("contacts", contacts); // refresh the database informing selected item
        }
    }
}
