package com.android.systemui.temporarydisplay;

import com.android.internal.logging.InstanceId;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
