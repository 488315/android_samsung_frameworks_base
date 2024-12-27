package com.android.systemui.temporarydisplay;

import com.android.internal.logging.InstanceId;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
