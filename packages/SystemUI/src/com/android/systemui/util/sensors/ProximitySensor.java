package com.android.systemui.util.sensors;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface ProximitySensor extends ThresholdSensor {
    void alertListeners();

    void destroy();

    Boolean isNear();

    boolean isRegistered();

    void setSecondarySafe(boolean z);
}
