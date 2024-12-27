package com.android.systemui.util.sensors;

public interface ProximitySensor extends ThresholdSensor {
    void alertListeners();

    void destroy();

    Boolean isNear();

    boolean isRegistered();

    void setSecondarySafe(boolean z);
}
