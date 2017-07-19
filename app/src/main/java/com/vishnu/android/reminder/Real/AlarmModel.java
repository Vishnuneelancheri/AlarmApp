package com.vishnu.android.reminder.Real;

import java.util.Calendar;

/**
 * Created by vishnu on 7/18/2017 - 4:21 PM.
 */

public class AlarmModel {
    public boolean  mFlag;
    public Calendar mcalender;

    public Calendar getMcalender() {
        return mcalender;
    }

    public void setMcalender(Calendar mcalender) {
        this.mcalender = mcalender;
    }

    public boolean ismFlag() {
        return mFlag;
    }

    public void setmFlag(boolean mFlag) {
        this.mFlag = mFlag;
    }


}
