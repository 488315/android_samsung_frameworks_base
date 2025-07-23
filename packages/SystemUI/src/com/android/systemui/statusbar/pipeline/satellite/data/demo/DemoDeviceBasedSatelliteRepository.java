package com.android.systemui.statusbar.pipeline.satellite.data.demo;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepository;
import com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DemoDeviceBasedSatelliteRepository implements DeviceBasedSatelliteRepository {
    public final StateFlowImpl connectionState;
    public final DemoDeviceBasedSatelliteDataSource dataSource;
    public final StandaloneCoroutine demoCommandJob;
    public final boolean isOpportunisticSatelliteIconEnabled;
    public final StateFlowImpl isSatelliteAllowedForCurrentLocation;
    public final StateFlowImpl isSatelliteProvisioned;
    public final CoroutineScope scope;
    public final StateFlowImpl signalStrength;

    public DemoDeviceBasedSatelliteRepository(DemoDeviceBasedSatelliteDataSource demoDeviceBasedSatelliteDataSource, CoroutineScope coroutineScope, Resources resources) {
        this.dataSource = demoDeviceBasedSatelliteDataSource;
        this.scope = coroutineScope;
        this.isOpportunisticSatelliteIconEnabled = resources.getBoolean(R.bool.config_showOpportunisticSatelliteIcon);
        Boolean bool = Boolean.TRUE;
        this.isSatelliteProvisioned = StateFlowKt.MutableStateFlow(bool);
        this.connectionState = StateFlowKt.MutableStateFlow(SatelliteConnectionState.Unknown);
        this.signalStrength = StateFlowKt.MutableStateFlow(0);
        this.isSatelliteAllowedForCurrentLocation = StateFlowKt.MutableStateFlow(bool);
    }

    @Override // com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepository
    public final StateFlow getConnectionState() {
        return this.connectionState;
    }

    @Override // com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepository
    public final StateFlow getSignalStrength() {
        return this.signalStrength;
    }

    @Override // com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepository
    public final boolean isOpportunisticSatelliteIconEnabled() {
        return this.isOpportunisticSatelliteIconEnabled;
    }

    @Override // com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepository
    public final StateFlow isSatelliteAllowedForCurrentLocation() {
        return this.isSatelliteAllowedForCurrentLocation;
    }

    @Override // com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepository
    public final StateFlow isSatelliteProvisioned() {
        return this.isSatelliteProvisioned;
    }
}
