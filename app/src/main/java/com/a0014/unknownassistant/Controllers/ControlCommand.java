package com.a0014.unknownassistant.Controllers;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.a0014.unknownassistant.Activities.Activityest;
import com.a0014.unknownassistant.Activities.SettingsActivity;

/**
 * Created by arifg on 10/1/2016.
 */
public class ControlCommand {

    private static final int APP_IDLE = 100;
    private static final int APP_EMAIL = 101;
    private static final int APP_CONTACT = 102;
    private static final int APP_PC = 103;
    private static final int APP_TV = 103;

    private String Command;
    public String getCommand() {
        return Command;
    }
    public void setCommand(String command) {
        Command = command;
    }

    ControlTv tvController;
    ControlContact contactController;
    ControlEmail emailController;
    ControlPc pcController;
    Context appContext, activityContext;

    int currentApplication;

    public ControlCommand(Context cApp, Context cActivity) {
        currentApplication = APP_IDLE;
        appContext = cApp;
        activityContext = cActivity;
        // instantiate the controller classes
        tvController = new ControlTv(appContext);
        contactController = new ControlContact(activityContext);
        emailController = new ControlEmail();
        pcController = new ControlPc();
    }

    public boolean ApplyCommand(String command, Context c)
    {
        Command = command;

        if(Command.contains("tv") || currentApplication == APP_TV){
            currentApplication = APP_TV;
            tvController.ApplyCommand(command);
            return true;
        }else if(Command.contains("email") || currentApplication == APP_EMAIL){
            currentApplication = APP_EMAIL;
            return true;
        }else if(Command.contains("text") || Command.contains("call") || Command.contains("message") || currentApplication == APP_CONTACT) {
            currentApplication = APP_CONTACT;
            contactController.ApplyCommand(command);
            return true;
        }else if (Command.contains("computer") || Command.contains("pc") || currentApplication == APP_PC) {
            currentApplication = APP_PC;
            return true;
        }else if (Command.contains("settings")) {
            Intent myIntent = new Intent(c, SettingsActivity.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            c.startActivity(myIntent);
            return true;
        }else if(Command.contains("test")){
            // create the intent
            //Intent myIntent = new Intent(c, Activityest.class);
            //myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //c.startActivity(myIntent);
        }
        return false;
    }
}
