package com.vishnu.android.reminder.Real.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vishnu.android.reminder.Real.AlarmCalenderDbModel;
import com.vishnu.android.reminder.Real.ShowAlarmModel;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by vishnu on 7/19/2017 - 12:29 PM.
 */

public class ReminderDataBase extends SQLiteOpenHelper {
    private static final String DB_FILE_NAME =   "reminder.db";
    private static final int     version     =   1;
    private static ReminderDataBase          mReminderDataBase;
    private SQLiteDatabase                   mDatabase  ;

    private String TABLE_ALARM_DESCRIPTION    =   "alarm_table_description";
    private String FIELD_ID                   =   "_id";
    private String FIELD_NAME_OF_ALARM        =   "name_of_alarm";
    private String FIELD_DESCRIPTION          =   "description";
    private String FIELD_IMAGE_URI            =   "image_uri";
    private String FIELD_RINGTONE_URI         =   "ringtone_uri";
    private String QUERY_ALRM_TABLE    =   "CREATE TABLE IF NOT EXISTS "+TABLE_ALARM_DESCRIPTION+" ("+
            FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            FIELD_NAME_OF_ALARM+" TEXT ,"+
            FIELD_DESCRIPTION+" TEXT ,"+
            FIELD_IMAGE_URI+" TEXT ,"+
            FIELD_RINGTONE_URI+" TEXT )";

    private String TABLE_ALARM_ID           =   "alarm_id_store";

    private String TABLE_ALARM_ID_ID        =   "_id";
    private String TABLE_ALARM_ID_DESCR_ID  =   "alarm_description_id";
    private String TABLE_ALARM_ID_DAY       =   "day_number";

    private String QUERY_ALARM_TABLE_ID     =   "CREATE TABLE IF NOT EXISTS "+ TABLE_ALARM_ID+ " ("+
            TABLE_ALARM_ID_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+
            TABLE_ALARM_ID_DESCR_ID+" TEXT, "+
            TABLE_ALARM_ID_DAY+" TEXT )";
    public ReminderDataBase(Context context){
        super(context, DB_FILE_NAME, null, version);
    }

    public static ReminderDataBase getInstance(Context context){
        if (mReminderDataBase   ==  null){
            /*mReminderDataBase   =   new ReminderDataBase(context);*/
            synchronized (ReminderDataBase.class){
                if (mReminderDataBase   == null){
                    mReminderDataBase   =   new ReminderDataBase(context);
                }
            }
        }
        return mReminderDataBase;

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(QUERY_ALRM_TABLE);
        sqLiteDatabase.execSQL(QUERY_ALARM_TABLE_ID);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){}

    public long insertAlarmDetails(AlarmCalenderDbModel alarmCalenderDbModels){
        long id  ;
        mDatabase   =   this.getWritableDatabase();
        ContentValues   contentValues   =   new ContentValues();
        contentValues.put(FIELD_NAME_OF_ALARM,alarmCalenderDbModels.getmAlarmName());
        contentValues.put(FIELD_DESCRIPTION, alarmCalenderDbModels.getmAlarmDescription());
        contentValues.put(FIELD_IMAGE_URI, alarmCalenderDbModels.getmImageUri());
        contentValues.put(FIELD_RINGTONE_URI, alarmCalenderDbModels.getmRingtonUri());
        id  =   mDatabase.insert(TABLE_ALARM_DESCRIPTION, null, contentValues);
        return id;
    }
    public long insertAlarmTimeWithId(String descrId, int day){
        long id;
        mDatabase   =   this.getWritableDatabase();
        ContentValues contentValues =   new ContentValues();
        contentValues.put(TABLE_ALARM_ID_DESCR_ID,descrId);
        contentValues.put(TABLE_ALARM_ID_DAY, Integer.toString(day));
        id  =   mDatabase.insert(TABLE_ALARM_ID, null, contentValues);
        return id;
    }
    public String getTables(){
        Cursor c = mDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        String name = "";
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                name += c.getString(0);
                c.moveToNext();
            }
        }
        return name;
    }

    public ShowAlarmModel getAlarmDataForAlert(String id){
        ShowAlarmModel  showAlarmModel  =   new ShowAlarmModel();
        SQLiteDatabase db = mReminderDataBase.getReadableDatabase();
        String selection  =   TABLE_ALARM_ID_ID+" = ?";
        String[] selectionArgs = {id};
        Cursor cursor  =  db.query(TABLE_ALARM_ID, null, selection, selectionArgs, null, null, null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            String alarmId  =   cursor.getString(cursor.getColumnIndex(TABLE_ALARM_ID_DESCR_ID));
            selection   =   FIELD_ID+" =?";
            String[] selectionArgsForDesc   =   {alarmId};
            Cursor  cursorDescr = db.query(TABLE_ALARM_DESCRIPTION, null, selection, selectionArgsForDesc, null, null, null);
            if (cursorDescr.moveToFirst() && cursor.getCount()>0){
                showAlarmModel.setmAlarmName(cursorDescr.getString(cursorDescr.getColumnIndex(FIELD_NAME_OF_ALARM)));
                  showAlarmModel.setmAlarmDescription(cursorDescr.getString(cursorDescr.getColumnIndex(FIELD_DESCRIPTION)));
                showAlarmModel.setmAlarmImageUri(cursorDescr.getString(cursorDescr.getColumnIndex(FIELD_IMAGE_URI)));
                showAlarmModel.setmAlarmRingtoneUri(cursorDescr.getString(cursorDescr.getColumnIndex(FIELD_RINGTONE_URI)));
            }
        }

        return showAlarmModel;
    }
}
