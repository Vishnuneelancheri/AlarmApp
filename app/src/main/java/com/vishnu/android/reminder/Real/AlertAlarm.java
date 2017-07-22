package com.vishnu.android.reminder.Real;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.vishnu.android.reminder.R;
import com.vishnu.android.reminder.Real.db.ReminderDataBase;

public class AlertAlarm extends AppCompatActivity {
    private TextView mTxtViewvAlarmName, mTxtViewvAlarmDescription, mTxtViewImageUri, mTxtViewRingtoneUri;
    private ImageView mImageViewShowContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_alarm);
        mTxtViewvAlarmName             =   (TextView)  findViewById(R.id.txt_view_alarm_name);
        mTxtViewvAlarmDescription      =   (TextView)  findViewById(R.id.txt_view_description);
        mTxtViewImageUri               =   (TextView)  findViewById(R.id.txt_view_image_uri);
        mTxtViewRingtoneUri            =   (TextView)  findViewById(R.id.txt_view_ringtone_uri);
        mImageViewShowContent          =   (ImageView)  findViewById(R.id.img_view_show_content);

        Intent intent   =   getIntent();
        String alarmId  =   intent.getStringExtra("id");

        ShowAlarmModel  showAlarmModel  = ReminderDataBase.getInstance(this).getAlarmData(alarmId);
        if (showAlarmModel != null){
            mTxtViewvAlarmName.setText(showAlarmModel.getmAlarmName());
            mTxtViewvAlarmDescription.setText(showAlarmModel.getmAlarmDescription());
            mTxtViewImageUri.setText(showAlarmModel.getmAlarmImageUri());
            mTxtViewRingtoneUri.setText(showAlarmModel.getmAlarmRingtoneUri());

            Uri imageUri    =   Uri.parse(showAlarmModel.getmAlarmImageUri());
            mImageViewShowContent.setImageURI(imageUri);

            Uri ringtoneUri =   Uri.parse(showAlarmModel.getmAlarmRingtoneUri());
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ringtoneUri);

            r.play();
        }
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
