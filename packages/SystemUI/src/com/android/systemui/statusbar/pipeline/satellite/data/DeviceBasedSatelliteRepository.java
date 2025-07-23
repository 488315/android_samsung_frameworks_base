package com.android.systemui.statusbar.pipeline.satellite.data;

import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface DeviceBasedSatelliteRepository {
    StateFlow getConnectionState();

    StateFlow getSignalStrength();

    boolean isOpportunisticSatelliteIconEnabled();

    StateFlow isSatelliteAllowedForCurrentLocation();

    StateFlow isSatelliteProvisioned();
}
