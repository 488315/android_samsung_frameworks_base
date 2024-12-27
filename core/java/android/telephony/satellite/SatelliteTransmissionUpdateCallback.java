package android.telephony.satellite;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes4.dex */
public interface SatelliteTransmissionUpdateCallback {
    void onReceiveDatagramStateChanged(int i, int i2, int i3);

    void onSatellitePositionChanged(PointingInfo pointingInfo);

    void onSendDatagramStateChanged(int i, int i2, int i3);

    void onSendDatagramStateChanged(int i, int i2, int i3, int i4);

    default void onSendDatagramRequested(int datagramType) {}
}
