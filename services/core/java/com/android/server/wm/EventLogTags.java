package com.android.server.wm;

import android.util.EventLog;

public abstract class EventLogTags {
    public static void writeWmSetKeyguardShown(int i, int i2, int i3, int i4, int i5, String str) {
        EventLog.writeEvent(
                30067,
                Integer.valueOf(i),
                Integer.valueOf(i2),
                Integer.valueOf(i3),
                Integer.valueOf(i4),
                Integer.valueOf(i5),
                str);
    }
}
