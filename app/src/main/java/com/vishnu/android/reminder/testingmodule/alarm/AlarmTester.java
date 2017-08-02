package com.vishnu.android.reminder.testingmodule.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.vishnu.android.reminder.R;

import java.util.Calendar;

public class AlarmTester extends AppCompatActivity {
    private TimePicker          mTimePicker;
    private TextView            mTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_tester);
        mTimePicker     =   (TimePicker)    findViewById(R.id.timePicker);
        mTxtView        =   (TextView)      findViewById(R.id.txt_show_time);
        Button mBtnSet = (Button) findViewById(R.id.btn_set_date);

        mBtnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCalenderTime(mTimePicker.getCurrentHour(), mTimePicker.getCurrentMinute());
            }
        });


    }
    public void setCalenderTime(int hour, int minute){
        Calendar calendar   =   Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        AlarmManager mAlarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent       =   new Intent(getApplicationContext(), AlarmReciever.class);
        intent.putExtra("s",Integer.toString(minute));
        PendingIntent mAlarmIntent = PendingIntent.getBroadcast(getApplicationContext(), minute, intent, 0);
        /*mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),  1000 * 60 * 20, mAlarmIntent);*/
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), mAlarmIntent);
        mTxtView.setText(calendar.getTime().toString());
    }

    @Override
    public void onAttachedToWindow() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    }


}
