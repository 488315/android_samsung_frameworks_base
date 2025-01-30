package com.android.systemui.statusbar.notification;

import com.android.systemui.log.LogBuffer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationWakeUpCoordinatorLogger {
    public final LogBuffer buffer;
    public boolean lastOnDozeAmountChangedLogWasFractional;
    public boolean lastSetDelayDozeAmountOverrideLogWasFractional;
    public boolean lastSetDozeAmountLogDelayWasFractional;
    public boolean lastSetDozeAmountLogInputWasFractional;
    public Float lastSetHardOverride;
    public boolean lastSetHideAmountLogWasFractional;
    public boolean lastSetVisibilityAmountLogWasFractional;
    public final boolean allowThrottle = true;
    public int lastSetDozeAmountLogState = -1;
    public float lastSetHideAmount = -1.0f;

    public NotificationWakeUpCoordinatorLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
