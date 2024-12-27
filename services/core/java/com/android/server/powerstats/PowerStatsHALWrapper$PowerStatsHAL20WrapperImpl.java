package com.android.server.powerstats;

import android.hardware.power.stats.Channel;
import android.hardware.power.stats.EnergyConsumer;
import android.hardware.power.stats.EnergyConsumerResult;
import android.hardware.power.stats.EnergyMeasurement;
import android.hardware.power.stats.IPowerStats;
import android.hardware.power.stats.PowerEntity;
import android.hardware.power.stats.StateResidencyResult;
import android.os.RemoteException;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PowerStatsHALWrapper$PowerStatsHAL20WrapperImpl
        implements PowerStatsHALWrapper$IPowerStatsHALWrapper {
    public static PowerStatsHALWrapper$VintfHalCache sVintfPowerStats;

    @Override // com.android.server.powerstats.PowerStatsHALWrapper$IPowerStatsHALWrapper
    public final EnergyConsumerResult[] getEnergyConsumed(int[] iArr) {
        PowerStatsHALWrapper$VintfHalCache powerStatsHALWrapper$VintfHalCache = sVintfPowerStats;
        if (powerStatsHALWrapper$VintfHalCache != null) {
            try {
                return ((IPowerStats.Stub.Proxy)
                                ((IPowerStats) powerStatsHALWrapper$VintfHalCache.get()))
                        .getEnergyConsumed(iArr);
            } catch (RemoteException e) {
                Slog.w("PowerStatsHALWrapper", "Failed to get energy consumer results: ", e);
            }
        }
        return null;
    }

    @Override // com.android.server.powerstats.PowerStatsHALWrapper$IPowerStatsHALWrapper
    public final EnergyConsumer[] getEnergyConsumerInfo() {
        PowerStatsHALWrapper$VintfHalCache powerStatsHALWrapper$VintfHalCache = sVintfPowerStats;
        if (powerStatsHALWrapper$VintfHalCache != null) {
            try {
                return ((IPowerStats.Stub.Proxy)
                                ((IPowerStats) powerStatsHALWrapper$VintfHalCache.get()))
                        .getEnergyConsumerInfo();
            } catch (RemoteException e) {
                Slog.w("PowerStatsHALWrapper", "Failed to get energy consumer info: ", e);
            }
        }
        return null;
    }

    @Override // com.android.server.powerstats.PowerStatsHALWrapper$IPowerStatsHALWrapper
    public final Channel[] getEnergyMeterInfo() {
        PowerStatsHALWrapper$VintfHalCache powerStatsHALWrapper$VintfHalCache = sVintfPowerStats;
        if (powerStatsHALWrapper$VintfHalCache != null) {
            try {
                return ((IPowerStats.Stub.Proxy)
                                ((IPowerStats) powerStatsHALWrapper$VintfHalCache.get()))
                        .getEnergyMeterInfo();
            } catch (RemoteException e) {
                Slog.w("PowerStatsHALWrapper", "Failed to get energy meter info: ", e);
            }
        }
        return null;
    }

    @Override // com.android.server.powerstats.PowerStatsHALWrapper$IPowerStatsHALWrapper
    public final PowerEntity[] getPowerEntityInfo() {
        PowerStatsHALWrapper$VintfHalCache powerStatsHALWrapper$VintfHalCache = sVintfPowerStats;
        if (powerStatsHALWrapper$VintfHalCache != null) {
            try {
                return ((IPowerStats.Stub.Proxy)
                                ((IPowerStats) powerStatsHALWrapper$VintfHalCache.get()))
                        .getPowerEntityInfo();
            } catch (RemoteException e) {
                Slog.w("PowerStatsHALWrapper", "Failed to get power entity info: ", e);
            }
        }
        return null;
    }

    @Override // com.android.server.powerstats.PowerStatsHALWrapper$IPowerStatsHALWrapper
    public final StateResidencyResult[] getStateResidency(int[] iArr) {
        PowerStatsHALWrapper$VintfHalCache powerStatsHALWrapper$VintfHalCache = sVintfPowerStats;
        if (powerStatsHALWrapper$VintfHalCache != null) {
            try {
                return ((IPowerStats.Stub.Proxy)
                                ((IPowerStats) powerStatsHALWrapper$VintfHalCache.get()))
                        .getStateResidency(iArr);
            } catch (RemoteException e) {
                Slog.w("PowerStatsHALWrapper", "Failed to get state residency: ", e);
            }
        }
        return null;
    }

    @Override // com.android.server.powerstats.PowerStatsHALWrapper$IPowerStatsHALWrapper
    public final boolean isInitialized() {
        return sVintfPowerStats != null;
    }

    @Override // com.android.server.powerstats.PowerStatsHALWrapper$IPowerStatsHALWrapper
    public final EnergyMeasurement[] readEnergyMeter(int[] iArr) {
        PowerStatsHALWrapper$VintfHalCache powerStatsHALWrapper$VintfHalCache = sVintfPowerStats;
        if (powerStatsHALWrapper$VintfHalCache != null) {
            try {
                return ((IPowerStats.Stub.Proxy)
                                ((IPowerStats) powerStatsHALWrapper$VintfHalCache.get()))
                        .readEnergyMeter(iArr);
            } catch (RemoteException e) {
                Slog.w("PowerStatsHALWrapper", "Failed to get energy measurements: ", e);
            }
        }
        return null;
    }
}
