package com.android.systemui.statusbar;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LockscreenNotificationManagerLogger {
    public final boolean DEBUG = DeviceType.isEngOrUTBinary();
    public final LogBuffer buffer;

    public LockscreenNotificationManagerLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
