package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HeadsUpCoordinatorLogger {
    public final LogBuffer buffer;
    public final boolean verbose;

    public HeadsUpCoordinatorLogger(LogBuffer logBuffer, boolean z) {
        this.buffer = logBuffer;
        this.verbose = z;
    }

    public HeadsUpCoordinatorLogger(LogBuffer logBuffer) {
        this(logBuffer, false);
    }
}
