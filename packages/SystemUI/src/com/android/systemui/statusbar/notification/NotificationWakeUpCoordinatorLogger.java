package com.android.systemui.statusbar.notification;

import com.android.systemui.log.LogBuffer;

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
