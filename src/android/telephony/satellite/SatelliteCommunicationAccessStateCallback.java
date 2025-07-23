package android.telephony.satellite;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes4.dex */
public interface SatelliteCommunicationAccessStateCallback {
    void onAccessAllowedStateChanged(boolean z);

    default void onAccessConfigurationChanged(SatelliteAccessConfiguration satelliteAccessConfiguration) {
    }
}
