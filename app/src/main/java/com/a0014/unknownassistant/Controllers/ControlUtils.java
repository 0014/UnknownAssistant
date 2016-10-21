package com.a0014.unknownassistant.Controllers;

/**
 * Created by arifg on 10/2/2016.
 * This class contains static methods to use all over the project
 */
public class ControlUtils {

    // get the number before that is before the "times" message
    public static int GetNumberBeforeKeyword(String message, String key)
    {
        int value = 0; int indexStart = 0;
        int indexEnd = message.indexOf(key) - 1;

        for(int i = indexEnd - 1; i > 0; i--){
            if(message.charAt(i) == ' '){
                indexStart = i + 1;
                break;
            }
        }

        try{
            value = Integer.parseInt(message.substring(indexStart, indexEnd));
        }catch(Exception e){
            // do nothing
        }

        return value;
}

    public static String GetNumberAfterKeyword(String message, String key)
    {
        int indexStart = message.indexOf(key) + key.length() + 1;
        int indexEnd = message.length();

        for(int i = indexStart; i < message.length(); i++){
            if(message.charAt(i) == ' '){
                indexEnd = i - 1;
                break;
            }
        }

        return message.substring(indexStart, indexEnd);
    }
}
