package com.vishnu.android.reminder.Real;

/**
 * Created by vishnu on 7/22/2017 - 3:20 PM.
 */

public class ShowAlarmModel {
    private String mAlarmRingtoneUri;
    private String mAlarmName;
    private String mAlarmDescription;
    private String mAlarmImageUri;

    public String getmAlarmName() {
        return mAlarmName;
    }

    public void setmAlarmName(String mAlarmName) {
        this.mAlarmName = mAlarmName;
    }


    public String getmAlarmDescription() {
        return mAlarmDescription;
    }

    public void setmAlarmDescription(String mAlarmDescription) {
        this.mAlarmDescription = mAlarmDescription;
    }


    public String getmAlarmImageUri() {
        return mAlarmImageUri;
    }

    public void setmAlarmImageUri(String mAlarmImageUri) {
        this.mAlarmImageUri = mAlarmImageUri;
    }


    public String getmAlarmRingtoneUri() {
        return mAlarmRingtoneUri;
    }

    public void setmAlarmRingtoneUri(String mAlarmRingtoneUri) {
        this.mAlarmRingtoneUri = mAlarmRingtoneUri;
    }

}
