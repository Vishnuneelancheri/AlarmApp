package com.vishnu.android.reminder.Real.db;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vishnu on 7/19/2017 - 12:29 PM.
 */

public class ReminderDataBase extends SQLiteOpenHelper {
    private static final String DB_FILE_NAME =   "reminder.db";
    private static final int     version     =   1;
    private static ReminderDataBase          mReminderDataBase;

    private String TABLE                =   "alarm";
    private String FIELD_ID                   =   "_id";
    private String FIELD_NAME_OF_ALARM        =   "name_of_alarm";
    private String FIELD_DESCRIPTION          =   "description";
    private String FIELD_IMAGE_URI            =   "image_uri";
    private String FIELD_RINGTONE_URI         =   "ringtone_uri";
    private String QUERY_ALRM_TABLE    =   "CREATE TABLE IF NOT EXISTS "+TABLE+" ("+
            FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            FIELD_NAME_OF_ALARM+" TEXT "+
            FIELD_DESCRIPTION+" TEXT "+
            FIELD_IMAGE_URI+" TEXT "+
            FIELD_RINGTONE_URI+" TEXT )";
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){}

    public int insertAlarm(){
        int id  =   0;
        return id;
    }

}
