package android.telephony.satellite;

import android.annotation.SystemApi;

import java.util.function.Consumer;

@SystemApi
public interface SatelliteDatagramCallback {
    void onSatelliteDatagramReceived(
            long j, SatelliteDatagram satelliteDatagram, int i, Consumer<Void> consumer);
}
