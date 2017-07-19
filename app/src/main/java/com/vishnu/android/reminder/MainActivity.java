package com.vishnu.android.reminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vishnu.android.reminder.Real.DailyRoutineActivity;
import com.vishnu.android.reminder.testingmodule.alarm.AlarmTester;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Intent intent = new Intent(this, AlarmTester.class);*/
        Intent intent = new Intent(this, DailyRoutineActivity.class);
        startActivity(intent);
    }
}
