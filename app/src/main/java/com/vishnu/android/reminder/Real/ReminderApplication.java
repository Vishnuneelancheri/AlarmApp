package com.vishnu.android.reminder.Real;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;

import com.vishnu.android.reminder.Real.db.ReminderDataBase;

/**
 * Created by vishnu on 7/13/2017 - 11:18 AM.
 */

public class ReminderApplication extends Application{
    @Override
    public void onCreate(){
        super.onCreate();
        ReminderDataBase.getInstance(getApplicationContext());
    }
}
