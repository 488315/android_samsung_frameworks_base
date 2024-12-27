package android.telephony.satellite;

import android.annotation.SystemApi;

@SystemApi
public interface SatelliteCapabilitiesCallback {
    void onSatelliteCapabilitiesChanged(SatelliteCapabilities satelliteCapabilities);
}
