package com.android.systemui.statusbar.pipeline.satellite.data;

import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface DeviceBasedSatelliteRepository {
    StateFlow getConnectionState();

    StateFlow getSignalStrength();

    boolean isOpportunisticSatelliteIconEnabled();

    StateFlow isSatelliteAllowedForCurrentLocation();

    StateFlow isSatelliteProvisioned();
}
