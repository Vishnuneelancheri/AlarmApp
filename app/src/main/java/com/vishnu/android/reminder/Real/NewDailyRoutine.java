package com.vishnu.android.reminder.Real;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.vishnu.android.reminder.R;
import com.vishnu.android.reminder.Real.db.ReminderDataBase;
import com.vishnu.android.reminder.testingmodule.alarm.AlarmReciever;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;


public class NewDailyRoutine extends AppCompatActivity implements View.OnClickListener {
    private ImageView                   mImgViewClock, mImgViewChooseImage, mImgViewChooseMusic, mImgViewResultImage, mImgViewSave;
    private TextView                    mTxtViewShowTime, mTxtViewImageUri, mTxtViewRingToneUri;
    private final int                   PICK_IMAGE_REQUEST    =   1, CHOOSE_RINGTONE_REQUEST    =   2;
    private Button                      mBtnSn, mBtnMn, mBtnwd, mBtnTu, mBtnTh, mBtnFr, mBtnSa;
    private int[]                       dateSelected ;
    private LinearLayout                mLinearTimeContainer;
    private int                         hour    =   0;
    private ExpandableHeightGridView    mGridTimeContainer;
    private ArrayList<AlarmModel>       mAlarmModelArray=   new ArrayList<>() ;
    private AlarmModel                  mAlarmModel;
    private TimeGridAdapter             timeGridAdapter;
    private EditText                    mEdtTxtName, mEdtTxtDescription;
    private String                      mImageUriString= "temp", mRingToneUriString= "temp";
    private AlarmManager                mAlarmManager;
    private PendingIntent               mAlarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_daily_routine);
        mImgViewClock           =   (ImageView)     findViewById(R.id.img_clock);
        mTxtViewShowTime        =   (TextView)      findViewById(R.id.txt_time);
        mImgViewClock.setOnClickListener(this);
        mImgViewChooseImage     =   (ImageView)     findViewById(R.id.img_choose_image);
        mImgViewChooseImage.setOnClickListener(this);
        mImgViewChooseMusic     =   (ImageView)     findViewById(R.id.img_view_music);
        mImgViewChooseMusic.setOnClickListener(this);
        mImgViewResultImage     =   (ImageView)     findViewById(R.id.img_view_result_image);
        mTxtViewImageUri        =   (TextView)      findViewById(R.id.txt_image_uri);
        mImgViewSave            =   (ImageView)        findViewById(R.id.img_view_save);
        mImgViewSave.setOnClickListener(this);
        mBtnSn                  =   (Button)        findViewById(R.id.btn_sun);
        mBtnSn.setOnClickListener(this);
        mBtnMn                  =   (Button)        findViewById(R.id.btn_mon);
        mBtnMn.setOnClickListener(this);
        mBtnTu                  =   (Button)        findViewById(R.id.btn_tue);
        mBtnTu.setOnClickListener(this);
        mBtnwd                  =   (Button)        findViewById(R.id.btn_wed);
        mBtnwd.setOnClickListener(this);
        mBtnTh                  =   (Button)        findViewById(R.id.btn_thu);
        mBtnTh.setOnClickListener(this);
        mBtnFr                  =   (Button)        findViewById(R.id.btn_fri);
        mBtnFr.setOnClickListener(this);
        mBtnSa                  =   (Button)        findViewById(R.id.btn_sat);
        mBtnSa.setOnClickListener(this);
        mEdtTxtDescription      =   (EditText)      findViewById(R.id.edt_txt_alarm_description);
        mEdtTxtName             =   (EditText)      findViewById(R.id.edt_txt_alarm_name);
        mTxtViewRingToneUri     =   (TextView)      findViewById(R.id.textView8);
        dateSelected            =   new int[7];

        for (int i = 0; i < dateSelected.length; i++){
            dateSelected[i]     =   0;
        }

        AlarmModel alarmModel   =   new AlarmModel();
        alarmModel.setMcalender(null);
        alarmModel.setmFlag(false);
        mAlarmModelArray.add(alarmModel);
        mLinearTimeContainer    =   (LinearLayout)  findViewById(R.id.linear_time_container);
        mGridTimeContainer      =   (ExpandableHeightGridView)      findViewById(R.id.grid_view_time_container);
        timeGridAdapter =   new TimeGridAdapter(this, mAlarmModelArray, new TimeGridAdapter.OnClickItemDeleted() {
            @Override
            public void changeItem(int position) {
                mAlarmModelArray.remove(position);
                mGridTimeContainer.setAdapter(timeGridAdapter);
            }
        });
        mGridTimeContainer.setAdapter(timeGridAdapter);
        mGridTimeContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the GridView selected/clicked item text
                if (position    ==  mAlarmModelArray.size()-1){
                    setTime();
                }
            }
        });
    }

    @Override
    public void onClick(View view){
        int id  =   view.getId();
        switch (id){
            case R.id.img_clock:
                setTime();
                break;
            case R.id.img_choose_image:
                browseItems("image");
                break;
            case R.id.img_view_music:
                Intent intent   =   new Intent(this, SelectRingtone.class);
                startActivityForResult(intent, CHOOSE_RINGTONE_REQUEST);
                break;
            case R.id.btn_sun:
                selectUnselectDate(0, mBtnSn);
                break;
            case R.id.btn_mon:
                selectUnselectDate(1, mBtnMn);
                break;
            case R.id.btn_tue:
                selectUnselectDate(2, mBtnTu);
                break;
            case R.id.btn_wed:
                selectUnselectDate(3, mBtnwd);
                break;
            case R.id.btn_thu:
                selectUnselectDate(4, mBtnTh);
                break;
            case R.id.btn_fri:
                selectUnselectDate(5, mBtnFr);
                break;
            case R.id.btn_sat:
                selectUnselectDate(6, mBtnSa);
                break;
            case R.id.img_view_save:
                /*gatherAlldata();*/
                recieveAllData();
                break;
        }
    }
    public void selectUnselectDate(int position, Button btn){
        if (dateSelected[position]  ==  0 ){
            dateSelected[position]  =   1;
           btn.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        else {
            btn.setTextColor(getResources().getColor(R.color.black));
            dateSelected[position]  =   0;
        }
    }

    public void setTime(){
        final Calendar calendar   =   Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        mTxtViewShowTime.setText(Integer.toString(hourOfDay)+" : "+Integer.toString(minute)+" PM");
                        hour            =   hourOfDay;
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        addTimeContainerView(calendar);
                    }
                },calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
        timePickerDialog.show();

    }
    public void addTimeContainerView(Calendar calendar){
        Boolean flagDuplicate = false;
        LayoutInflater  layoutInflater  =   (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View  addView             =   layoutInflater.inflate(R.layout.time_container_item, null);
        addView.setMinimumWidth(5);
        addView.setMinimumHeight(5);
        TextView    textView            =   (TextView)addView.findViewById(R.id.txt_view_time_container_item);
        ImageView   imgViewClose        =   (ImageView)addView.findViewById(R.id.img_view_time_container_remove);
        textView.setText(Integer.toString(Calendar.MINUTE));
        imgViewClose.setImageResource(R.mipmap.close);
        mLinearTimeContainer.addView(addView);
        imgViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinearLayout)addView.getParent()).removeView(addView);
            }
        });

        try{
            for (int i = 0; i < mAlarmModelArray.size()-1; i++){
                if (mAlarmModelArray.size() > 0){
                    if (mAlarmModelArray.get(i).getMcalender().get(Calendar.HOUR_OF_DAY) == calendar.get(Calendar.HOUR_OF_DAY)
                            &&
                            mAlarmModelArray.get(i).getMcalender().get(Calendar.MINUTE) == calendar.get(Calendar.MINUTE) ){
                        flagDuplicate   =   true;
                        break;
                    }
                }
            }
        }catch (Exception e){
        }

        if (flagDuplicate){
            Toast.makeText(getApplicationContext(), "Duplication of time is not allowed", Toast.LENGTH_LONG).show();
            return;
        }

        mAlarmModel     =   new AlarmModel();
        mAlarmModel.setMcalender(calendar);
        mAlarmModel.setmFlag(true);
        mAlarmModelArray.add(mAlarmModel);


        for (int i  =   0; i    < mAlarmModelArray.size()-1; i++){
            if (!mAlarmModelArray.get(i).ismFlag())
                mAlarmModelArray.remove(i);
        }

        mAlarmModel     =   new AlarmModel();
        mAlarmModel.setmFlag(false);
        mAlarmModel.setMcalender(null);
        mAlarmModelArray.add(mAlarmModel);

        mGridTimeContainer.setAdapter(timeGridAdapter);
    }

    public void browseItems(String fileType){
        /*Intent intent = new Intent();
        if (fileType.equals("image"))
            intent.setType("image*//*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE_REQUEST);*/
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int  requestCode, int resultCode, Intent resultIntent){
        super.onActivityResult(requestCode, resultCode, resultIntent);
        if (requestCode ==  PICK_IMAGE_REQUEST && resultCode    ==  RESULT_OK && resultIntent   !=  null
                &&  resultIntent.getData()  !=  null){
            Uri uri     =   resultIntent.getData();
            Toast.makeText(NewDailyRoutine.this, uri.toString(), Toast.LENGTH_SHORT).show();
            mTxtViewImageUri.setText("Image choosen:");
            try{
                Bitmap bitmap   = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                mImgViewResultImage.setImageBitmap(bitmap);
                saveImageFile(bitmap);

            }catch (IOException e){

            }
        }
        else if(requestCode ==  CHOOSE_RINGTONE_REQUEST && resultCode   ==  RESULT_OK && resultIntent != null){
            mRingToneUriString      =   resultIntent.getStringExtra("ringtone_uri").toString();
            mTxtViewImageUri.setText(mRingToneUriString);
            mTxtViewRingToneUri.setText(resultIntent.getStringExtra("ringtone_name").toString());
        }
    }
    public String saveImageFile(Bitmap bitmap) {
        FileOutputStream out ;
        String filename = getFilename();
        try {
            out         = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setimage(filename);
        return filename;
    }

    private String getFilename() {
        File file       = new File(Environment.getExternalStorageDirectory()
                .getPath(), "ReminderFolder");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/"
                + System.currentTimeMillis() + ".png");
        return uriSting;
    }
    public void setimage(String filename){
         Uri  uri           =   Uri.fromFile(new File(filename));
        mImageUriString     =   uri.toString();
        mRingToneUriString  =   uri.toString();
        try{
            Bitmap bitmap   = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            mImgViewClock.setImageBitmap(bitmap);
        }catch (IOException e){
            mTxtViewImageUri.setText(e.toString());
        }

    }
    public void gatherAlldata(){
        AlarmCalenderDbModel alarmCalenderModel =   new AlarmCalenderDbModel();
        ArrayList<Calendar> alarmCalenderList   =   new ArrayList<>();
        Calendar calendar   =   Calendar.getInstance();
        for (int i  =   0; i    < dateSelected.length; i++){
            if (dateSelected[i] == 1){
                switch (i){
                    case 0:
                        calendar.set(Calendar.DATE, Calendar.SUNDAY);
                        break;
                    case 1:
                        calendar.set(Calendar.DATE, Calendar.MONDAY);
                        break;
                    case 2:
                        calendar.set(Calendar.DATE, Calendar.TUESDAY);
                        break;
                    case 3:
                        calendar.set(Calendar.DATE, Calendar.WEDNESDAY);
                        break;
                    case 4:
                        calendar.set(Calendar.DATE, Calendar.THURSDAY);
                        break;
                    case 5:
                        calendar.set(Calendar.DATE, Calendar.FRIDAY);
                        break;
                    case 6:
                        calendar.set(Calendar.DATE, Calendar.SATURDAY);
                        break;
                }
                for (int j=0; j<mAlarmModelArray.size()-1; j++){
                    calendar.set(Calendar.HOUR_OF_DAY, mAlarmModelArray.get(j).getMcalender().get(Calendar.HOUR_OF_DAY));
                    int temp    =   calendar.get(Calendar.MINUTE);
                    int day =   calendar.get(Calendar.DATE);
                    day++;
                    alarmCalenderList.add(calendar);
                    alarmCalenderModel.setmCalenderArrayList(alarmCalenderList);
                    alarmCalenderModel.setmAlarmDescription(mEdtTxtDescription.getText().toString());
                    alarmCalenderModel.setmAlarmName(mEdtTxtName.getText().toString());
                    alarmCalenderModel.setmImageUri(mImageUriString);
                    alarmCalenderModel.setmRingtonUri(mRingToneUriString);
                    long alarmId =   ReminderDataBase.getInstance(this).insertAlarmDetails(alarmCalenderModel );
                    setCalenderTime(calendar, alarmId);
                }
            }else {}
        }
    }
    public void recieveAllData(){
        AlarmCalenderDbModel alarmCalenderDbModel   =   new AlarmCalenderDbModel();
        alarmCalenderDbModel.setmImageUri(mImageUriString);
        alarmCalenderDbModel.setmRingtonUri(mRingToneUriString);
        alarmCalenderDbModel.setmAlarmName(mEdtTxtName.getText().toString());
        alarmCalenderDbModel.setmAlarmName(mEdtTxtDescription.getText().toString());
        long alarmDescriptionId =   ReminderDataBase.getInstance(this).insertAlarmDetails(alarmCalenderDbModel);

        Calendar calendar   =   Calendar.getInstance();
        for (int i  =   0; i    < dateSelected.length; i++){
            if (dateSelected[i] == 1){
                switch (i){
                    case 0:
                        calendar.set(Calendar.DATE, Calendar.SUNDAY);
                        break;
                    case 1:
                        calendar.set(Calendar.DATE, Calendar.MONDAY);
                        break;
                    case 2:
                        calendar.set(Calendar.DATE, Calendar.TUESDAY);
                        break;
                    case 3:
                        calendar.set(Calendar.DATE, Calendar.WEDNESDAY);
                        break;
                    case 4:
                        calendar.set(Calendar.DATE, Calendar.THURSDAY);
                        break;
                    case 5:
                        calendar.set(Calendar.DATE, Calendar.FRIDAY);
                        break;
                    case 6:
                        calendar.set(Calendar.DATE, Calendar.SATURDAY);
                        break;
                }
                for (int j=0; j<mAlarmModelArray.size()-1; j++){
                    calendar.set(Calendar.HOUR_OF_DAY, mAlarmModelArray.get(j).getMcalender().get(Calendar.HOUR_OF_DAY));
                    int temp    =   calendar.get(Calendar.MINUTE);
                    int day =   calendar.get(Calendar.DATE);
                    day++;
                    long alarmId = ReminderDataBase.getInstance(this).insertAlarmTimeWithId(Long.toString(alarmDescriptionId), calendar.get(Calendar.DAY_OF_WEEK));
                    setCalenderTime(calendar, alarmId);
                }
            }else {}
        }
    }
    public void setCalenderTime(Calendar calender, long id){
        Integer alarmId     =    (int)(long) id;
        mAlarmManager       =   (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent       =   new Intent(getApplicationContext(), AlarmReciever.class);
        intent.putExtra("id", Integer.toString(alarmId));
        mAlarmIntent        =   PendingIntent.getBroadcast(getApplicationContext(),alarmId, intent, 0);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(),  1000 * 60 * 60 * 24, mAlarmIntent);
        /*mAlarmManager.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), mAlarmIntent);*/
        Toast.makeText(this, ReminderDataBase.getInstance(this).getTables(), Toast.LENGTH_LONG).show();
    }
    public void   insertAlarm(AlarmCalenderDbModel alarmCalenderDbModels, Context context){

    }

}
