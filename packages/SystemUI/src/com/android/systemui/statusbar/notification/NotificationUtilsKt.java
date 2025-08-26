package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.collection.PipelineEntry;

/* loaded from: classes3.dex */
public abstract class NotificationUtilsKt {
    public static final String getLogKey(PipelineEntry pipelineEntry) {
        if (pipelineEntry != null) {
            return NotificationUtils.logKey(pipelineEntry);
        }
        return null;
    }
}
