package com.android.systemui.statusbar.pipeline.satellite.data;

import kotlinx.coroutines.flow.StateFlow;

public interface DeviceBasedSatelliteRepository {
    StateFlow getConnectionState();

    StateFlow getSignalStrength();

    boolean isOpportunisticSatelliteIconEnabled();

    StateFlow isSatelliteAllowedForCurrentLocation();

    StateFlow isSatelliteProvisioned();
}
