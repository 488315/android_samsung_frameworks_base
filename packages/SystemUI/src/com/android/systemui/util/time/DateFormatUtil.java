package com.android.systemui.util.time;

import android.content.Context;
import android.text.format.DateFormat;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
