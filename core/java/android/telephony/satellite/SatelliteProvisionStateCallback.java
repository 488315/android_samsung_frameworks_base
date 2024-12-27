package android.telephony.satellite;

import android.annotation.SystemApi;

import java.util.List;

@SystemApi
public interface SatelliteProvisionStateCallback {
    void onSatelliteProvisionStateChanged(boolean z);

    default void onSatelliteSubscriptionProvisionStateChanged(
            List<SatelliteSubscriberProvisionStatus> satelliteSubscriberProvisionStatus) {}
}
