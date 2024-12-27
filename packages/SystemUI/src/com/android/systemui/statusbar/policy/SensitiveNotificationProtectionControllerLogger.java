package com.android.systemui.statusbar.policy;

import com.android.systemui.log.LogBuffer;

public final class SensitiveNotificationProtectionControllerLogger {
    public final LogBuffer buffer;

    public SensitiveNotificationProtectionControllerLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
