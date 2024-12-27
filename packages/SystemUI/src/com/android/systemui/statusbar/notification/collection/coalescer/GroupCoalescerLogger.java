package com.android.systemui.statusbar.notification.collection.coalescer;

import com.android.systemui.log.LogBuffer;

public final class GroupCoalescerLogger {
    public final LogBuffer buffer;

    public GroupCoalescerLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
