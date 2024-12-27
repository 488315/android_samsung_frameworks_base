package com.android.server.am;

import android.util.EventLog;

public abstract class EventLogTags {
    public static void writeAmMeminfo(long j, long j2, long j3, long j4, long j5) {
        EventLog.writeEvent(
                30046,
                Long.valueOf(j),
                Long.valueOf(j2),
                Long.valueOf(j3),
                Long.valueOf(j4),
                Long.valueOf(j5));
    }

    public static void writeAmOomAdjMisc(int i, int i2, int i3, int i4, int i5, String str) {
        EventLog.writeEvent(
                30113,
                Integer.valueOf(i),
                Integer.valueOf(i2),
                Integer.valueOf(i3),
                Integer.valueOf(i4),
                Integer.valueOf(i5),
                0,
                str);
    }

    public static void writeAmPss(
            int i, int i2, String str, long j, long j2, long j3, long j4, int i3, int i4, long j5) {
        EventLog.writeEvent(
                30047,
                Integer.valueOf(i),
                Integer.valueOf(i2),
                str,
                Long.valueOf(j),
                Long.valueOf(j2),
                Long.valueOf(j3),
                Long.valueOf(j4),
                Integer.valueOf(i3),
                Integer.valueOf(i4),
                Long.valueOf(j5));
    }

    public static void writeBootProgressAmsState(int i, int i2, int i3, String str, String str2) {
        EventLog.writeEvent(
                1000100, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), str, str2);
    }
}
