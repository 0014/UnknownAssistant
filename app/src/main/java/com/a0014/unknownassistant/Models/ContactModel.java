package com.a0014.unknownassistant.Models;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;

import com.a0014.unknownassistant.Activities.CommandActivity;

/**
 * Created by arif.gencosmanoglu on 10/21/2016.
 */
public class ContactModel {

    private String Name;
    private String Number;
    private String Tag;
    private int Position;
    private Context Context;

    public ContactModel(Context c, String name, String number, int position) {
        Context = c;
        Name = name;
        Position = position;
        // get the number in the correct format
        Number = number.replace("+", "");
        Number = Number.replace(" ", "");
        Number = Number.replace("-", "");
    }

    public ContactModel(Context c, String name, String number, int position, String tag) {
        Context = c;
        // set the name
        Name = name;
        // set the number in the correct format
        Number = number.replace("+", "");
        Number = Number.replace(" ", "");
        Number = Number.replace("-", "");
        // set the tag
        Tag = tag;
        // set the position
        Position = position;
    }

    public String getName() {
        return Name;
    }

    public String getNumber() {
        return Number;
    }

    public String getTag() {
        return Tag;
    }

    public int getContactNo() {
        return Position;
    }

    public void Call() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        if (ActivityCompat.checkSelfPermission(Context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) return;

                        String number = "tel:" + Number.trim();
                        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                        Context.startActivity(callIntent);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(Context);
        builder.setMessage("Are you sure you want to call " + Name + "?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public void SendText(final String message)
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                            SendSms(message);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(Context);
        builder.setMessage("Are you sure you want to text to " + Name + "?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private boolean SendSms(String message)
    {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(Number, null, message, null, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
