package android.telephony.satellite;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes4.dex */
public interface SatelliteTransmissionUpdateCallback {
    void onReceiveDatagramStateChanged(int i, int i2, int i3);

    void onSatellitePositionChanged(PointingInfo pointingInfo);

    default void onSendDatagramRequested(int i) {
    }

    void onSendDatagramStateChanged(int i, int i2, int i3);

    default void onSendDatagramStateChanged(int i, int i2, int i3, int i4) {
    }
}
