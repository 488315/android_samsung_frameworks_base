package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.log.LogBuffer;

public final class NotifInflaterLogger {
    public final LogBuffer buffer;

    public NotifInflaterLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
