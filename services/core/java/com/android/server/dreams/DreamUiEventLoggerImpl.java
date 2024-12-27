package com.android.server.dreams;

import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.FrameworkStatsLog;

public final class DreamUiEventLoggerImpl {
    public final String[] mLoggableDreamPrefixes;

    public DreamUiEventLoggerImpl(String[] strArr) {
        this.mLoggableDreamPrefixes = strArr;
    }

    public final void log(UiEventLogger.UiEventEnum uiEventEnum, String str) {
        int id = uiEventEnum.getId();
        if (id <= 0) {
            return;
        }
        int i = 0;
        while (true) {
            String[] strArr = this.mLoggableDreamPrefixes;
            if (i >= strArr.length) {
                str = "other";
                break;
            } else if (str.startsWith(strArr[i])) {
                break;
            } else {
                i++;
            }
        }
        FrameworkStatsLog.write(FrameworkStatsLog.DREAM_UI_EVENT_REPORTED, 0, id, 0, str);
    }
}
