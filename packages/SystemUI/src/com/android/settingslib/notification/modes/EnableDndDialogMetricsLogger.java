package com.android.settingslib.notification.modes;

import android.content.Context;
import com.android.internal.logging.MetricsLogger;

/* loaded from: classes.dex */
public class EnableDndDialogMetricsLogger {
    public final Context mContext;

    public EnableDndDialogMetricsLogger(Context context) {
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
