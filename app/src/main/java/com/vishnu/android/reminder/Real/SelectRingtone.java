package com.vishnu.android.reminder.Real;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.vishnu.android.reminder.R;

import java.util.ArrayList;

public class SelectRingtone extends AppCompatActivity implements View.OnClickListener{
    private ListView mListViewRingTone;
    String[] uris;
    ArrayList<Uri> alarmUriList =   new ArrayList<>();
    private Button mBtnChooseRingTone;
    private int ringtonePosition    =   0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ringtone);
        mListViewRingTone   =   (ListView)  findViewById(R.id.list_ring_tone);
        mBtnChooseRingTone  =   (Button)    findViewById(R.id.btn_ringtone_choose);
        mBtnChooseRingTone.setOnClickListener(this);
        ProgressDialog progressDialog   =   new ProgressDialog(this);
        progressDialog.setMessage("Its loading....");
        progressDialog.setTitle("ProgressDialog bar example");
        progressDialog.show();

        RingtoneManager ringtoneManager =   new RingtoneManager(this);
        ringtoneManager.setType(RingtoneManager.TYPE_ALARM);
        Cursor cursor   =   ringtoneManager.getCursor();
         uris  =   new String[cursor.getCount()];
        while (cursor.moveToNext()){
            int position    =   cursor.getPosition();
           /* uris[position]  =   ringtoneManager.getRingtoneUri(position);*/
            Ringtone ringtone = RingtoneManager.getRingtone(this, ringtoneManager.getRingtoneUri(position));
            String title = ringtone.getTitle(this);
            alarmUriList.add(ringtoneManager.getRingtoneUri(position));

            uris[position]  =   title;
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.listview,R.id.txt_view_list, uris);
        mListViewRingTone.setAdapter(adapter);

        progressDialog.dismiss();

        mListViewRingTone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), uris[position], Toast.LENGTH_LONG).show();
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), alarmUriList.get(position));
                ringtonePosition    =   position;
                r.play();
            }
        });
    }
    @Override
    public void onClick(View view){
        if (view.getId()    ==  R.id.btn_ringtone_choose){
            Intent  ringtoneData    =   new Intent();
            ringtoneData.putExtra("ringtone_uri", alarmUriList.get(ringtonePosition).toString());
            ringtoneData.putExtra("ringtone_name", uris[ringtonePosition].toString());
            setResult(RESULT_OK, ringtoneData);
            finish();
        }
    }
}
