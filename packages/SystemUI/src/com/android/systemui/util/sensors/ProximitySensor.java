package com.android.systemui.util.sensors;

/* loaded from: classes3.dex */
public interface ProximitySensor extends ThresholdSensor {
    void alertListeners();

    void destroy();

    Boolean isNear();

    boolean isRegistered();

    void setSecondarySafe(boolean z);
}
