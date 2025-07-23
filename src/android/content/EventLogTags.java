package android.content;

import android.util.EventLog;

/* loaded from: classes.dex */
public class EventLogTags {
    public static final int BINDER_SAMPLE = 52004;
    public static final int CONTENT_QUERY_SAMPLE = 52002;
    public static final int CONTENT_UPDATE_SAMPLE = 52003;

    private EventLogTags() {
    }

    public static void writeContentQuerySample(String str, String str2, String str3, String str4, int i, String str5, int i2) {
        EventLog.writeEvent(CONTENT_QUERY_SAMPLE, str, str2, str3, str4, Integer.valueOf(i), str5, Integer.valueOf(i2));
    }

    public static void writeContentUpdateSample(String str, String str2, String str3, int i, String str4, int i2) {
        EventLog.writeEvent(CONTENT_UPDATE_SAMPLE, str, str2, str3, Integer.valueOf(i), str4, Integer.valueOf(i2));
    }

    public static void writeBinderSample(String str, int i, int i2, String str2, int i3) {
        EventLog.writeEvent(BINDER_SAMPLE, str, Integer.valueOf(i), Integer.valueOf(i2), str2, Integer.valueOf(i3));
    }
}
