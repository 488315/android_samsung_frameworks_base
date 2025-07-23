package android.os;

import android.util.EventLog;

/* loaded from: classes3.dex */
public class EventLogTags {
    public static final int SERVICE_MANAGER_SLOW = 230001;
    public static final int SERVICE_MANAGER_STATS = 230000;

    private EventLogTags() {
    }

    public static void writeServiceManagerStats(int i, int i2, int i3) {
        EventLog.writeEvent(SERVICE_MANAGER_STATS, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
    }

    public static void writeServiceManagerSlow(int i, String str) {
        EventLog.writeEvent(SERVICE_MANAGER_SLOW, Integer.valueOf(i), str);
    }
}
