package com.vishnu.android.reminder.testingmodule.alarm;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.vishnu.android.reminder.MainActivity;
import com.vishnu.android.reminder.R;
import com.vishnu.android.reminder.Real.AlertAlarm;

/**
 * Created by vishnu on 7/10/2017 - 2:23 PM.
 */

public class AlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        Toast.makeText(context, intent.getStringExtra("id"), Toast.LENGTH_LONG).show();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(intent.getStringExtra("s"))
                        .setContentText(intent.getStringExtra("s"));
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());
        Intent intent1 = new Intent(context, AlertAlarm.class);
        intent1.putExtra("id", intent.getStringExtra("id"));
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);

    }
}
