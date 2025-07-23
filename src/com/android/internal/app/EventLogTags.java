package com.android.internal.app;

import android.util.EventLog;

/* loaded from: classes5.dex */
public class EventLogTags {
    public static final int HARMFUL_APP_WARNING_LAUNCH_ANYWAY = 53001;
    public static final int HARMFUL_APP_WARNING_UNINSTALL = 53000;

    private EventLogTags() {
    }

    public static void writeHarmfulAppWarningUninstall(String str) {
        EventLog.writeEvent(HARMFUL_APP_WARNING_UNINSTALL, str);
    }

    public static void writeHarmfulAppWarningLaunchAnyway(String str) {
        EventLog.writeEvent(HARMFUL_APP_WARNING_LAUNCH_ANYWAY, str);
    }
}
