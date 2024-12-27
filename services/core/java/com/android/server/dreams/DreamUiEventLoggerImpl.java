package com.android.server.dreams;

import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.FrameworkStatsLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
