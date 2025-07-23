package android.telephony.satellite;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes4.dex */
public interface SatelliteModemStateCallback {
    default void onEmergencyModeChanged(boolean z) {
    }

    default void onRegistrationFailure(int i) {
    }

    void onSatelliteModemStateChanged(int i);

    default void onTerrestrialNetworkAvailableChanged(boolean z) {
    }
}
