package com.android.server.wm;

import android.util.EventLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
