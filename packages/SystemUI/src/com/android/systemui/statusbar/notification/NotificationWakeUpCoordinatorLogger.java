package com.android.systemui.statusbar.notification;

import com.android.systemui.log.LogBuffer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationWakeUpCoordinatorLogger {
    public final LogBuffer buffer;
    public boolean lastOnDozeAmountChangedLogWasFractional;
    public boolean lastSetDozeAmountLogInputWasFractional;
    public Float lastSetHardOverride;
    public boolean lastSetHideAmountLogWasFractional;
    public boolean lastSetVisibilityAmountLogWasFractional;
    public int lastSetDozeAmountLogState = -1;
    public float lastSetHideAmount = -1.0f;

    public NotificationWakeUpCoordinatorLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
