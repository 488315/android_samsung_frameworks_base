package android.telephony.satellite;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes4.dex */
public interface SatelliteModemStateCallback {
    void onSatelliteModemStateChanged(int i);

    default void onEmergencyModeChanged(boolean isEmergency) {
    }

    default void onRegistrationFailure(int causeCode) {
    }

    default void onTerrestrialNetworkAvailableChanged(boolean isAvailable) {
    }
}
