package com.vishnu.android.reminder.Real;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by vishnu on 7/19/2017 - 4:12 PM.
 */

public class AlarmCalenderDbModel {
    private String mRingtonUri;
    private String mImageUri;
    private String mAlarmDescription;
    private ArrayList<Calendar> mCalenderArrayList;
    public ArrayList<Calendar> getmCalenderArrayList() {
        return mCalenderArrayList;
    }

    public void setmCalenderArrayList(ArrayList<Calendar> mCalenderArrayList) {
        this.mCalenderArrayList = mCalenderArrayList;
    }



    public String getmAlarmName() {
        return mAlarmName;
    }

    public void setmAlarmName(String mAlarmName) {
        this.mAlarmName = mAlarmName;
    }

    private String              mAlarmName;

    public String getmAlarmDescription() {
        return mAlarmDescription;
    }

    public void setmAlarmDescription(String mAlarmDescription) {
        this.mAlarmDescription = mAlarmDescription;
    }



    public String getmImageUri() {
        return mImageUri;
    }

    public void setmImageUri(String mImageUri) {
        this.mImageUri = mImageUri;
    }



    public String getmRingtonUri() {
        return mRingtonUri;
    }

    public void setmRingtonUri(String mRingtonUri) {
        this.mRingtonUri = mRingtonUri;
    }



}
