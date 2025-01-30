package com.android.systemui.temporarydisplay;

import com.android.internal.logging.InstanceId;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
