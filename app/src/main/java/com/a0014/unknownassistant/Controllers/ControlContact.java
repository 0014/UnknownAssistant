package com.a0014.unknownassistant.Controllers;

import android.content.Context;

import com.a0014.unknownassistant.Models.ContactModel;
import com.a0014.unknownassistant.Models.TinyDB;

import java.util.ArrayList;

/**
 * Created by arif.gencosmanoglu on 10/21/2016.
 */

public class ControlContact {
    private Context c;
    private TinyDB tinyDb;
    private ArrayList<String> contacts = new ArrayList<String>();

    public ControlContact(Context c) {
        this.c = c;

        tinyDb = new TinyDB(c);
        contacts = tinyDb.getListString("contacts");
    }

    public void ApplyCommand(String command)
    {
        if(command.contains("call") ){
            ContactModel cm = CreateContact(command, "call");
            if(cm != null) cm.Call();
        }else if(command.contains("message to")){
            ContactModel cm = CreateContact(command, "message to");
            if(cm != null) cm.SendText(ControlUtils.GetStringAfterKey(command, String.valueOf(cm.getContactNo())));
        }else if(command.contains("text to")){
            ContactModel cm = CreateContact(command, "text to");
            if(cm != null) cm.SendText(ControlUtils.GetStringAfterKey(command, String.valueOf(cm.getContactNo())));
        }
    }

    private ContactModel CreateContact(String command, String key)
    {
        int contactNumber;

        try{
            contactNumber = Integer.parseInt(ControlUtils.GetNumberAfterKeyword(command, key));
        }catch(Exception e){
            return null;
        }

        String name = contacts.get(contactNumber / 100 - 1).split(", ")[0];
        String phone = contacts.get(contactNumber / 100 - 1).split(", ")[1];

        return new ContactModel(c, name, phone, contactNumber);
    }
}
