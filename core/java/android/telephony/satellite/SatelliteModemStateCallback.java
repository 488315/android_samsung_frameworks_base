package android.telephony.satellite;

import android.annotation.SystemApi;

@SystemApi
public interface SatelliteModemStateCallback {
    void onSatelliteModemStateChanged(int i);

    default void onEmergencyModeChanged(boolean isEmergency) {}

    default void onRegistrationFailure(int causeCode) {}

    default void onTerrestrialNetworkAvailableChanged(boolean isAvailable) {}
}
