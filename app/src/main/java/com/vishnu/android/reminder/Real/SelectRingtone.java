package com.vishnu.android.reminder.Real;

import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vishnu.android.reminder.R;

public class SelectRingtone extends AppCompatActivity {
    private ListView mListViewRingTone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ringtone);
        mListViewRingTone   =   (ListView)  findViewById(R.id.list_ring_tone);

        RingtoneManager ringtoneManager =   new RingtoneManager(this);
        Cursor cursor   =   ringtoneManager.getCursor();
        String[] uris  =   new String[cursor.getCount()];
        while (cursor.moveToNext()){
            int position    =   cursor.getPosition();
           /* uris[position]  =   ringtoneManager.getRingtoneUri(position);*/
            Ringtone ringtone = RingtoneManager.getRingtone(this, ringtoneManager.getRingtoneUri(position));
            String title = ringtone.getTitle(this);
            uris[position]  =   title;
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.listview,R.id.txt_view_list, uris);
        mListViewRingTone.setAdapter(adapter);
    }
}
