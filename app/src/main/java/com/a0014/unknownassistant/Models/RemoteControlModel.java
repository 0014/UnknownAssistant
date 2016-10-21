package com.a0014.unknownassistant.Models;

import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by arifg on 10/2/2016.
 * This model currently only works for Samsung brand tvs
 */
public class RemoteControlModel {

    ConsumerIrManager mCIR;

    final Handler handler;
    final int delay = 750; // delay in ms

    private static final String POWER_PATTERN = "0000 006d 0022 0003 00a9 00a8 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0040 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 0702 00a9 00a8 0015 0015 0015 0e6e";
    private static final String NUM_1_PATTERN = "0000 006d 0022 0003 00a9 00a8 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 0702 00a9 00a8 0015 0015 0015 0e6e";
    private static final String NUM_2_PATTERN= "0000 006c 0022 0003 00ab 00aa 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0040 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0016 0015 003f 0015 0015 0015 003f 0015 003f 0015 0040 0015 003f 0015 003f 0015 0713 00ab 00aa 0015 0015 0015 0e91";
    private static final String NUM_3_PATTERN = "0000 006c 0022 0003 00ab 00aa 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0040 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 003f 0015 003f 0015 0040 0015 003f 0015 003f 0015 0714 00ab 00aa 0015 0015 0015 0e91";
    private static final String NUM_4_PATTERN = "0000 006d 0022 0003 00a9 00a8 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 0702 00a9 00a8 0015 0015 0015 0e6e";
    private static final String NUM_5_PATTERN = "0000 006d 0022 0003 00a9 00a8 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0016 0015 003f 0015 003f 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 0702 00a9 00a8 0015 0015 0015 0e6e";
    private static final String NUM_6_PATTERN = "0000 006d 0022 0003 00a9 00a8 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 003f 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 0703 00a9 00a8 0015 0015 0015 0e6e";
    private static final String NUM_7_PATTERN= "0000 006d 0023 0003 0001 5a59 00a9 00a8 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 0703 00a9 00a8 0015 0015 0015 0e6e";
    private static final String NUM_8_PATTERN = "0000 006d 0022 0003 00a9 00a8 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 0703 00a9 00a8 0015 0015 0015 0e6e";
    private static final String NUM_9_PATTERN = "0000 006d 0022 0003 00a9 00a8 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 0703 00a9 00a8 0015 0015 0015 0e6e";
    private static final String NUM_0_PATTERN = "0000 006c 0022 0003 00ab 00aa 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0040 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 0016 0015 003f 0015 003f 0015 003f 0015 0015 0015 0040 0015 003f 0015 003f 0015 0713 00ab 00aa 0015 0015 0015 0e91";
    private static final String VOL_UP_PATTERN = "0000 006d 0022 0003 00a9 00a8 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 0702 00a9 00a8 0015 0015 0015 0e6e";
    private static final String VOL_DOWN_PATTERN = "0000 006d 0022 0003 00a9 00a8 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 0702 00a9 00a8 0015 0015 0015 0e6e";

    int frequency = 0;

    public RemoteControlModel(Context c) {
        mCIR = (ConsumerIrManager) c.getSystemService(Context.CONSUMER_IR_SERVICE);

        handler = new Handler();
    }

    public void TogglePower()
    {
        TransmitData(POWER_PATTERN);
    }

    public void OpenChannel(String number)
    {
        char[] digits = number.toCharArray();

        for(int i = 0; i < digits.length; i++){

            PressNumber(digits[i]);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do nothing for .5s
                }
            }, delay);
        }
    }

    public void IncreaseVolume()
    {
        TransmitData(VOL_UP_PATTERN);
    }

    public void IncreaseVolume(int amount)
    {
        for (int i = 0; i < amount; i ++)
        {
            TransmitData(VOL_UP_PATTERN);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do nothing for .5s
                }
            }, delay);
        }
    }

    public void DecreaseVolume()
    {
        TransmitData(VOL_DOWN_PATTERN);
    }

    public void DecreaseVolume(int amount)
    {
        for (int i = 0; i < amount; i ++)
        {
            TransmitData(VOL_DOWN_PATTERN);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do nothing for .5s
                }
            }, delay);
        }
    }

    private void PressNumber(char digit) {
        switch (digit) {
            case '0':
                TransmitData(NUM_0_PATTERN);
                break;
            case '1':
                TransmitData(NUM_1_PATTERN);
                break;
            case '2':
                TransmitData(NUM_2_PATTERN);
                break;
            case '3':
                TransmitData(NUM_3_PATTERN);
                break;
            case '4':
                TransmitData(NUM_4_PATTERN);
                break;
            case '5':
                TransmitData(NUM_5_PATTERN);
                break;
            case '6':
                TransmitData(NUM_6_PATTERN);
                break;
            case '7':
                TransmitData(NUM_7_PATTERN);
                break;
            case '8':
                TransmitData(NUM_8_PATTERN);
                break;
            case '9':
                TransmitData(NUM_9_PATTERN);
                break;
        }
    }

    private String hex2dec(String irData) {
        List<String> list = new ArrayList<String>(Arrays.asList(irData.split(" ")));
        list.remove(0); // dummy
        frequency = Integer.parseInt(list.remove(0), 16); // frequency
        list.remove(0); // seq1
        list.remove(0); // seq2

        for (int i = 0; i < list.size(); i++) {
            list.set(i, Integer.toString(Integer.parseInt(list.get(i), 16)));
        }

        frequency = (int) (1000000 / (frequency * 0.241246));
        list.add(0, Integer.toString(frequency));

        irData = "";
        for (String s : list) {
            irData += s + ",";
        }
        return irData;
    }

    private int[] count2duration(String countPattern) {
        List<String> list = new ArrayList<String>(Arrays.asList(countPattern.split(",")));
        int frequency = Integer.parseInt(list.get(0));
        int pulses = 1000000/frequency;
        int count;
        int duration;

        list.remove(0);

        for (int i = 0; i < list.size(); i++) {
            count = Integer.parseInt(list.get(i));
            duration = count * pulses;
            list.set(i, Integer.toString(duration));
        }

        int[] durationPattern = new int[list.size()]; int i = 0;

        for (String s : list) {
            durationPattern[i] = Integer.parseInt(s);
            i++;
        }

        return durationPattern;
    }

    private void TransmitData(String pattern)
    {
        int[] data = count2duration(hex2dec(pattern));

        mCIR.transmit(frequency, data);
    }
}
