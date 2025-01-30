package android.telephony.satellite;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public interface SatelliteTransmissionUpdateCallback {
    void onReceiveDatagramStateChanged(int i, int i2, int i3);

    void onSatellitePositionChanged(PointingInfo pointingInfo);

    void onSendDatagramStateChanged(int i, int i2, int i3);
}
