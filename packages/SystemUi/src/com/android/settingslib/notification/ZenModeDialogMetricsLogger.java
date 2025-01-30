package com.android.settingslib.notification;

import android.content.Context;
import com.android.internal.logging.MetricsLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ZenModeDialogMetricsLogger {
    public final Context mContext;

    public ZenModeDialogMetricsLogger(Context context) {
        this.mContext = context;
    }

    public void logOnClickTimeButton(boolean z) {
        MetricsLogger.action(this.mContext, 163, z);
    }

    public void logOnConditionSelected() {
        MetricsLogger.action(this.mContext, 164);
    }

    public void logOnEnableZenModeForever() {
        MetricsLogger.action(this.mContext, 1259);
    }

    public void logOnEnableZenModeUntilAlarm() {
        MetricsLogger.action(this.mContext, 1261);
    }

    public void logOnEnableZenModeUntilCountdown() {
        MetricsLogger.action(this.mContext, 1260);
    }
}
