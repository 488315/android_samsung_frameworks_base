package com.android.server.powerstats;

import android.app.StatsManager;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.power.stats.Channel;
import android.hardware.power.stats.EnergyMeasurement;
import android.hardware.power.stats.StateResidency;
import android.hardware.power.stats.StateResidencyResult;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public final class StatsPullAtomCallbackImpl implements StatsManager.StatsPullAtomCallback {
    public Map mChannels;
    public Map mEntityNames;
    public PowerStatsService.LocalService mPowerStatsInternal;
    public Map mStateNames;

    public final int onPullAtom(int i, List list) {
        if (i == 10005) {
            try {
                StateResidencyResult[] stateResidencyResultArr =
                        (StateResidencyResult[])
                                this.mPowerStatsInternal
                                        .getStateResidencyAsync(new int[0])
                                        .get(2000L, TimeUnit.MILLISECONDS);
                if (stateResidencyResultArr != null) {
                    for (StateResidencyResult stateResidencyResult : stateResidencyResultArr) {
                        int i2 = 0;
                        while (true) {
                            StateResidency[] stateResidencyArr =
                                    stateResidencyResult.stateResidencyData;
                            if (i2 < stateResidencyArr.length) {
                                StateResidency stateResidency = stateResidencyArr[i2];
                                list.add(
                                        FrameworkStatsLog.buildStatsEvent(
                                                i,
                                                (String)
                                                        ((HashMap) this.mEntityNames)
                                                                .get(
                                                                        Integer.valueOf(
                                                                                stateResidencyResult
                                                                                        .id)),
                                                (String)
                                                        ((Map)
                                                                        ((HashMap) this.mStateNames)
                                                                                .get(
                                                                                        Integer
                                                                                                .valueOf(
                                                                                                        stateResidencyResult
                                                                                                                .id)))
                                                                .get(
                                                                        Integer.valueOf(
                                                                                stateResidency.id)),
                                                stateResidency.totalStateEntryCount,
                                                stateResidency.totalTimeInStateMs));
                                i2++;
                            }
                        }
                    }
                    return 0;
                }
            } catch (Exception e) {
                Slog.e("StatsPullAtomCallbackImpl", "Failed to getStateResidencyAsync", e);
            }
            return 1;
        }
        if (i != 10038) {
            throw new UnsupportedOperationException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown tagId="));
        }
        try {
            PowerStatsService.LocalService localService = this.mPowerStatsInternal;
            localService.getClass();
            CompletableFuture completableFuture = new CompletableFuture();
            PowerStatsService.m850$$Nest$mgetHandler(PowerStatsService.this)
                    .post(
                            new PowerStatsService$LocalService$$ExternalSyntheticLambda0(
                                    localService, completableFuture, new int[0], 1));
            EnergyMeasurement[] energyMeasurementArr =
                    (EnergyMeasurement[]) completableFuture.get(2000L, TimeUnit.MILLISECONDS);
            if (energyMeasurementArr != null) {
                for (EnergyMeasurement energyMeasurement : energyMeasurementArr) {
                    if (energyMeasurement.durationMs == energyMeasurement.timestampMs) {
                        list.add(
                                FrameworkStatsLog.buildStatsEvent(
                                        i,
                                        ((Channel)
                                                        ((HashMap) this.mChannels)
                                                                .get(
                                                                        Integer.valueOf(
                                                                                energyMeasurement
                                                                                        .id)))
                                                .subsystem,
                                        ((Channel)
                                                        ((HashMap) this.mChannels)
                                                                .get(
                                                                        Integer.valueOf(
                                                                                energyMeasurement
                                                                                        .id)))
                                                .name,
                                        energyMeasurement.durationMs,
                                        energyMeasurement.energyUWs));
                    }
                }
                return 0;
            }
        } catch (Exception e2) {
            Slog.e("StatsPullAtomCallbackImpl", "Failed to readEnergyMeterAsync", e2);
        }
        return 1;
    }
}
