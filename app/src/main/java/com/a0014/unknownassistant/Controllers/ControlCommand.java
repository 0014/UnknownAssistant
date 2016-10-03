package com.a0014.unknownassistant.Controllers;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.a0014.unknownassistant.Activities.Activityest;

/**
 * Created by arifg on 10/1/2016.
 */
public class ControlCommand {

    private static final int APP_IDLE = 100;
    private static final int APP_EMAIL = 101;
    private static final int APP_TEXT = 102;
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
    Context c;

    int currentApplication;

    public ControlCommand(Context c) {
        currentApplication = APP_IDLE;
        tvController = new ControlTv(c);
        this.c = c;
    }

    public boolean ApplyCommand(String command, Context c)
    {
        Command = command;

        if(Command.contains("test")){
            // create the intent
            Intent myIntent = new Intent(c, Activityest.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            c.startActivity(myIntent);

        }else if(Command.contains("tv") || currentApplication == APP_TV){
            currentApplication = APP_TV;
            tvController.ApplyCommand(command);
            return true;
        }

        return false;
    }
}