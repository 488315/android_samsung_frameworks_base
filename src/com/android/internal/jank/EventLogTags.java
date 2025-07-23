package com.android.internal.jank;

import android.util.EventLog;

/* loaded from: classes5.dex */
public class EventLogTags {
    public static final int JANK_CUJ_EVENTS_BEGIN_REQUEST = 37001;
    public static final int JANK_CUJ_EVENTS_CANCEL_REQUEST = 37003;
    public static final int JANK_CUJ_EVENTS_END_REQUEST = 37002;

    private EventLogTags() {
    }

    public static void writeJankCujEventsBeginRequest(int i, long j, long j2, long j3, String str) {
        EventLog.writeEvent(JANK_CUJ_EVENTS_BEGIN_REQUEST, Integer.valueOf(i), Long.valueOf(j), Long.valueOf(j2), Long.valueOf(j3), str);
    }

    public static void writeJankCujEventsEndRequest(int i, long j, long j2, long j3) {
        EventLog.writeEvent(JANK_CUJ_EVENTS_END_REQUEST, Integer.valueOf(i), Long.valueOf(j), Long.valueOf(j2), Long.valueOf(j3));
    }

    public static void writeJankCujEventsCancelRequest(int i, long j, long j2, long j3) {
        EventLog.writeEvent(JANK_CUJ_EVENTS_CANCEL_REQUEST, Integer.valueOf(i), Long.valueOf(j), Long.valueOf(j2), Long.valueOf(j3));
    }
}
