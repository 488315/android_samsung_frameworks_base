package com.android.systemui.util.sensors;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface ProximitySensor extends ThresholdSensor {
    void alertListeners();

    void destroy();

    Boolean isNear();

    boolean isRegistered();

    void setSecondarySafe(boolean z);
}
