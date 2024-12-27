package com.android.systemui.statusbar;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.util.DeviceType;

public final class LockscreenNotificationManagerLogger {
    public final boolean DEBUG = DeviceType.isEngOrUTBinary();
    public final LogBuffer buffer;

    public LockscreenNotificationManagerLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
