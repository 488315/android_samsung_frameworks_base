package com.android.systemui.statusbar;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.util.DeviceType;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LockscreenNotificationManagerLogger {
    public final boolean DEBUG = DeviceType.isEngOrUTBinary();
    public final LogBuffer buffer;

    public LockscreenNotificationManagerLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
