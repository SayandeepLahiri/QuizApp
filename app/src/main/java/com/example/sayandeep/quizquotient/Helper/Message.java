package com.example.sayandeep.quizquotient.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by mukhe on 10-Apr-18.
 * This is the class to make the Toast or the Log Message.
 */

public class Message {
    /**
     * This is the method to make the Toast message for the app.
     *
     * @param context: The context where the Toast is to be displayed.
     * @param msg:     The message that is to be displayed in the Toast.
     * @param delay:   The duration of the Toast Message. Send "" for Short else
     *                 anything else for long.
     */
    public static void makeToastMessage(Context context, String msg, String delay) {
        if (delay.equals("")) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This is the method to make the Log Message for the code.
     *
     * @param TAG: The tag for the Log.
     * @param msg: The message that is to be displayed in the log.
     */
    public static void makeLogMessages(String TAG, String msg) {
        Log.d(TAG, msg);
    }
}
