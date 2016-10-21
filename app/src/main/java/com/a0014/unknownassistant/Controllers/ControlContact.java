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
        }else if(command.contains("message")){
            ContactModel cm = CreateContact(command, "message");
            if(cm != null) cm.SendText("test");
        }else if(command.contains("text")){
            ContactModel cm = CreateContact(command, "text");
            if(cm != null) cm.SendText("test");
        }
    }

    private ContactModel CreateContact(String command, String key)
    {
        int contactNumber = -1;
        try{
            contactNumber = Integer.parseInt(ControlUtils.GetNumberAfterKeyword(command, key)) - 1;
        }catch(Exception e){
            return null;
        }

        String name = contacts.get(contactNumber).split(", ")[0];
        String phone = contacts.get(contactNumber).split(", ")[1];

        return new ContactModel(c, name, phone);
    }
}
