package com.android.systemui.util.time;

import android.content.Context;
import android.text.format.DateFormat;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;

public class DateFormatUtil {
    private final Context mContext;
    private final UserTracker mUserTracker;

    public DateFormatUtil(Context context, UserTracker userTracker) {
        this.mContext = context;
        this.mUserTracker = userTracker;
    }

    public boolean is24HourFormat() {
        return DateFormat.is24HourFormat(this.mContext, ((UserTrackerImpl) this.mUserTracker).getUserId());
    }
}
