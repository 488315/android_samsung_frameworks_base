package android.net;

import android.util.EventLog;

/* loaded from: classes3.dex */
public class EventLogTags {
    public static final int NTP_FAILURE = 50081;
    public static final int NTP_SUCCESS = 50080;

    private EventLogTags() {
    }

    public static void writeNtpSuccess(String str, long j, long j2) {
        EventLog.writeEvent(50080, str, Long.valueOf(j), Long.valueOf(j2));
    }

    public static void writeNtpFailure(String str, String str2) {
        EventLog.writeEvent(50081, str, str2);
    }
}
