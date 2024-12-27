package com.android.server.display.brightness.clamper;

import android.os.IThermalService;
import android.os.ServiceManager;
import android.util.Slog;

import com.android.server.LocalServices;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.powerstats.PowerStatsService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class PmicMonitor {
    public ScheduledFuture mPmicMonitorFuture;
    public final BrightnessPowerClamper$$ExternalSyntheticLambda4 mPowerChangeListener;
    public final long mPowerMonitorPeriodConfigSecs;
    public float mLastEnergyConsumed = FullScreenMagnificationGestureHandler.MAX_SCALE;
    public long mCurrentTimestampMillis = 0;
    public final PowerStatsService.LocalService mPowerStatsInternal =
            (PowerStatsService.LocalService)
                    LocalServices.getService(PowerStatsService.LocalService.class);
    final IThermalService mThermalService =
            IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice"));
    public final ScheduledExecutorService mExecutor = Executors.newSingleThreadScheduledExecutor();

    public PmicMonitor(
            BrightnessPowerClamper$$ExternalSyntheticLambda4
                    brightnessPowerClamper$$ExternalSyntheticLambda4,
            int i) {
        this.mPowerChangeListener = brightnessPowerClamper$$ExternalSyntheticLambda4;
        this.mPowerMonitorPeriodConfigSecs = i;
    }

    public final void start() {
        if (this.mPowerStatsInternal == null) {
            Slog.w("PmicMonitor", "Power stats service not found for monitoring.");
            return;
        }
        if (this.mThermalService == null) {
            Slog.w("PmicMonitor", "Thermal service not found.");
            return;
        }
        if (this.mPmicMonitorFuture != null) {
            Slog.e("PmicMonitor", "already scheduled, stop() called before start.");
            return;
        }
        ScheduledExecutorService scheduledExecutorService = this.mExecutor;
        Runnable runnable = new Runnable() { // from class:
                    // com.android.server.display.brightness.clamper.PmicMonitor$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void run() {
                        /*
                            r11 = this;
                            com.android.server.display.brightness.clamper.PmicMonitor r11 = com.android.server.display.brightness.clamper.PmicMonitor.this
                            com.android.server.powerstats.PowerStatsService$LocalService r0 = r11.mPowerStatsInternal
                            com.android.server.powerstats.PowerStatsService r1 = com.android.server.powerstats.PowerStatsService.this
                            com.android.server.powerstats.PowerStatsHALWrapper$IPowerStatsHALWrapper r1 = r1.getPowerStatsHal()
                            android.hardware.power.stats.EnergyConsumer[] r1 = r1.getEnergyConsumerInfo()
                            if (r1 == 0) goto Lc0
                            int r2 = r1.length
                            if (r2 != 0) goto L15
                            goto Lc0
                        L15:
                            android.util.IntArray r2 = new android.util.IntArray
                            r2.<init>()
                            r3 = 0
                            r4 = r3
                        L1c:
                            int r5 = r1.length
                            r6 = 3
                            if (r4 >= r5) goto L2e
                            r5 = r1[r4]
                            byte r7 = r5.type
                            if (r7 != r6) goto L2b
                            int r5 = r5.id
                            r2.add(r5)
                        L2b:
                            int r4 = r4 + 1
                            goto L1c
                        L2e:
                            int r1 = r2.size()
                            java.lang.String r4 = "PmicMonitor"
                            if (r1 != 0) goto L3d
                            java.lang.String r11 = "DISPLAY energyConsumerIds size is null"
                            android.util.Slog.w(r4, r11)
                            goto Lc0
                        L3d:
                            int[] r1 = r2.toArray()
                            java.util.concurrent.CompletableFuture r0 = r0.getEnergyConsumedAsync(r1)
                            r1 = 0
                            java.lang.Object r0 = r0.get()     // Catch: java.util.concurrent.ExecutionException -> L4d java.lang.InterruptedException -> L4f
                            android.hardware.power.stats.EnergyConsumerResult[] r0 = (android.hardware.power.stats.EnergyConsumerResult[]) r0     // Catch: java.util.concurrent.ExecutionException -> L4d java.lang.InterruptedException -> L4f
                            goto L60
                        L4d:
                            r0 = move-exception
                            goto L51
                        L4f:
                            r0 = move-exception
                            goto L59
                        L51:
                            java.lang.String r2 = "exception reading getEnergyConsumedAsync: "
                            android.util.Slog.wtf(r4, r2, r0)
                        L57:
                            r0 = r1
                            goto L60
                        L59:
                            java.lang.String r2 = "timeout or interrupt reading getEnergyConsumedAsync failed"
                            android.util.Slog.w(r4, r2, r0)
                            goto L57
                        L60:
                            if (r0 == 0) goto Lba
                            int r2 = r0.length
                            if (r2 != 0) goto L66
                            goto Lba
                        L66:
                            r2 = r0[r3]
                            long r7 = r2.energyUWs
                            float r5 = (float) r7
                            float r7 = r11.mLastEnergyConsumed
                            float r5 = r5 - r7
                            long r7 = r2.timestampMs
                            long r9 = r11.mCurrentTimestampMillis
                            long r7 = r7 - r9
                            float r2 = (float) r7
                            r7 = 1148846080(0x447a0000, float:1000.0)
                            float r2 = r2 / r7
                            float r5 = r5 / r2
                            float r5 = r5 / r7
                            android.os.IThermalService r2 = r11.mThermalService     // Catch: android.os.RemoteException -> L89
                            android.os.Temperature[] r2 = r2.getCurrentTemperaturesWithType(r6)     // Catch: android.os.RemoteException -> L89
                            int r6 = r2.length     // Catch: android.os.RemoteException -> L89
                            r7 = 1
                            if (r6 <= r7) goto L8b
                            java.lang.String r6 = "Multiple skin temperatures not allowed!"
                            android.util.Slog.w(r4, r6)     // Catch: android.os.RemoteException -> L89
                            goto L8b
                        L89:
                            r2 = move-exception
                            goto L91
                        L8b:
                            int r6 = r2.length     // Catch: android.os.RemoteException -> L89
                            if (r6 <= 0) goto L97
                            r1 = r2[r3]     // Catch: android.os.RemoteException -> L89
                            goto L97
                        L91:
                            java.lang.String r6 = "getDisplayTemperature failed"
                            com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0.m(r6, r2, r4)
                        L97:
                            r0 = r0[r3]
                            long r2 = r0.energyUWs
                            float r2 = (float) r2
                            r11.mLastEnergyConsumed = r2
                            long r2 = r0.timestampMs
                            r11.mCurrentTimestampMillis = r2
                            if (r1 == 0) goto Lc0
                            int r0 = r1.getStatus()
                            com.android.server.display.brightness.clamper.BrightnessPowerClamper$$ExternalSyntheticLambda4 r11 = r11.mPowerChangeListener
                            com.android.server.display.brightness.clamper.BrightnessPowerClamper r11 = r11.f$0
                            r11.getClass()
                            com.android.server.display.brightness.clamper.BrightnessPowerClamper$$ExternalSyntheticLambda6 r1 = new com.android.server.display.brightness.clamper.BrightnessPowerClamper$$ExternalSyntheticLambda6
                            r1.<init>()
                            android.os.Handler r11 = r11.mHandler
                            r11.post(r1)
                            goto Lc0
                        Lba:
                            java.lang.String r11 = "displayResults are null"
                            android.util.Slog.w(r4, r11)
                        Lc0:
                            return
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.server.display.brightness.clamper.PmicMonitor$$ExternalSyntheticLambda0.run():void");
                    }
                };
        long j = this.mPowerMonitorPeriodConfigSecs;
        this.mPmicMonitorFuture =
                scheduledExecutorService.scheduleAtFixedRate(runnable, j, j, TimeUnit.SECONDS);
    }
}
