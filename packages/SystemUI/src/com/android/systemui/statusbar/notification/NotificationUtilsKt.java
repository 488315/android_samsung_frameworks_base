package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.collection.PipelineEntry;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class NotificationUtilsKt {
    public static final String getLogKey(PipelineEntry pipelineEntry) {
        if (pipelineEntry != null) {
            return NotificationUtils.logKey(pipelineEntry);
        }
        return null;
    }
}
