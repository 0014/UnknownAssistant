package com.a0014.unknownassistant.Controllers;

import android.content.Context;
import com.a0014.unknownassistant.Models.RemoteControlModel;

/**
 * Created by arifg on 10/2/2016.
 * This class is responsible for controlling the tv
 */
public class ControlTv {

    RemoteControlModel rmc;

    public ControlTv(Context c) {
        rmc = new RemoteControlModel(c);
    }

    public void ApplyCommand(String command)
    {
        if(command.contains("channel") ){
            rmc.OpenChannel(ControlUtils.GetChannel(command));
        }else if(command.contains("on") || command.contains("off") || command.contains("open") || command.contains("close")) {
            rmc.TogglePower();
        }else if(command.contains("volume") && (command.contains("up") || command.contains("increase"))){
            if(command.contains("times")){
                rmc.IncreaseVolume(ControlUtils.IterationAmount(command));
            }else{
                rmc.IncreaseVolume( );
            }
        }else if(command.contains("volume") && (command.contains("down") || command.contains("decrease"))){
            if(command.contains("times")){
                rmc.DecreaseVolume(ControlUtils.IterationAmount(command));
            }else{
                rmc.DecreaseVolume( );
            }
        }
    }
}