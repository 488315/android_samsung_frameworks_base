package com.android.systemui.temporarydisplay;

import com.android.internal.logging.InstanceId;

public abstract class TemporaryViewInfo {
    public final int timeoutMs = 10000;

    public abstract String getId();

    public abstract InstanceId getInstanceId();

    public abstract ViewPriority getPriority();

    public int getTimeoutMs() {
        return this.timeoutMs;
    }

    public abstract String getWakeReason();

    public abstract String getWindowTitle();
}
