package com.a0014.unknownassistant.Models;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.a0014.unknownassistant.R;

public class CustomContactsListViewAdapter extends ArrayAdapter<String>{

    final Context c;
    ArrayList<String> strings;

    public CustomContactsListViewAdapter(Context context, ArrayList<String> strings) {
        super(context, R.layout.custom_contacts_list_view, strings);
        this.c = context;
        this.strings = strings;
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(R.layout.custom_contacts_list_view, parent, false);

        TextView txtName = (TextView) row.findViewById(R.id.txt_name);
        TextView txtPhoneNumber = (TextView) row.findViewById(R.id.txt_phone_number);

        String name = strings.get(position).split(", ")[0];
        String phone = strings.get(position).split(", ")[1];

        txtName.setText("\t" + name);
        txtPhoneNumber.setText("\t" + phone);

        return row;
    }
}
