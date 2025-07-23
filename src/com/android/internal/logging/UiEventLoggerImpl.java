package com.android.internal.logging;

import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.FrameworkStatsLog;

/* loaded from: classes5.dex */
public class UiEventLoggerImpl implements UiEventLogger {
    @Override // com.android.internal.logging.UiEventLogger
    public void log(UiEventLogger.UiEventEnum uiEventEnum) {
        log(uiEventEnum, 0, null);
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void log(UiEventLogger.UiEventEnum uiEventEnum, int i, String str) {
        int id = uiEventEnum.getId();
        if (id > 0) {
            FrameworkStatsLog.write(90, id, i, str, 0);
        }
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void log(UiEventLogger.UiEventEnum uiEventEnum, InstanceId instanceId) {
        logWithInstanceId(uiEventEnum, 0, null, instanceId);
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void logWithInstanceId(UiEventLogger.UiEventEnum uiEventEnum, int i, String str, InstanceId instanceId) {
        int id = uiEventEnum.getId();
        if (id > 0 && instanceId != null) {
            FrameworkStatsLog.write(90, id, i, str, instanceId.getId());
        } else if (id > 0) {
            log(uiEventEnum, i, str);
        }
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void logWithPosition(UiEventLogger.UiEventEnum uiEventEnum, int i, String str, int i2) {
        int id = uiEventEnum.getId();
        if (id > 0) {
            FrameworkStatsLog.write(260, id, str, 0, i2, false);
        }
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void logWithInstanceIdAndPosition(UiEventLogger.UiEventEnum uiEventEnum, int i, String str, InstanceId instanceId, int i2) {
        int id = uiEventEnum.getId();
        if (id > 0 && instanceId != null) {
            FrameworkStatsLog.write(260, id, str, instanceId.getId(), i2, false);
        } else if (id > 0) {
            logWithPosition(uiEventEnum, i, str, i2);
        }
    }
}
