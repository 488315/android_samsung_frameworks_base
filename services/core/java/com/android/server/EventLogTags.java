package com.android.server;

import android.util.EventLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class EventLogTags {
    public static void writeNotificationAdjusted(String str, String str2, String str3) {
        EventLog.writeEvent(27535, str, str2, str3);
    }

    public static void writeNotificationCancel(
            int i,
            int i2,
            String str,
            int i3,
            String str2,
            int i4,
            int i5,
            int i6,
            int i7,
            String str3) {
        EventLog.writeEvent(
                2751,
                Integer.valueOf(i),
                Integer.valueOf(i2),
                str,
                Integer.valueOf(i3),
                str2,
                Integer.valueOf(i4),
                Integer.valueOf(i5),
                Integer.valueOf(i6),
                Integer.valueOf(i7),
                str3);
    }
}
