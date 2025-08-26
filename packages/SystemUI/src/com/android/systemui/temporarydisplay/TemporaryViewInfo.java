package com.android.systemui.temporarydisplay;

import com.android.internal.logging.InstanceId;

/* loaded from: classes3.dex */
public abstract class TemporaryViewInfo {
    public abstract String getId();

    public abstract InstanceId getInstanceId();

    public abstract ViewPriority getPriority();

    public int getTimeoutMs() {
        return 10000;
    }

    public abstract String getWakeReason();

    public abstract String getWindowTitle();
}
