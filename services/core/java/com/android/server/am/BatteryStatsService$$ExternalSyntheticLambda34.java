package com.android.server.am;

import com.android.server.power.stats.BatteryExternalStatsWorker;
import com.android.server.power.stats.BatteryStatsImpl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.RejectedExecutionException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda34
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsService f$0;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda34(
            BatteryStatsService batteryStatsService, int i) {
        this.$r8$classId = i;
        this.f$0 = batteryStatsService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        BatteryStatsService batteryStatsService = this.f$0;
        switch (i) {
            case 0:
                BatteryExternalStatsWorker batteryExternalStatsWorker = batteryStatsService.mWorker;
                synchronized (batteryExternalStatsWorker) {
                    if (batteryExternalStatsWorker.mExecutorService.isShutdown()) {
                        CompletableFuture.failedFuture(
                                new IllegalStateException("worker shutdown"));
                        return;
                    }
                    batteryExternalStatsWorker.scheduleSyncLocked(127, "write");
                    try {
                        batteryExternalStatsWorker.mExecutorService.submit(
                                batteryExternalStatsWorker.mWriteTask);
                        return;
                    } catch (RejectedExecutionException e) {
                        CompletableFuture.failedFuture(e);
                        return;
                    }
                }
            case 1:
                synchronized (batteryStatsService.mStats) {
                    BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
                    long elapsedRealtime = batteryStatsImpl.mClock.elapsedRealtime();
                    batteryStatsImpl.mClock.uptimeMillis();
                    if (!batteryStatsImpl.mTxPowerSharingOn) {
                        batteryStatsImpl.mTxPowerSharingOn = true;
                        batteryStatsImpl.mTxPowerSharingTimer.startRunningLocked(elapsedRealtime);
                    }
                }
                return;
            case 2:
                synchronized (batteryStatsService.mStats) {
                    batteryStatsService.mStats.noteStopTxPowerSharingLocked();
                }
                return;
            default:
                batteryStatsService.mWorker.scheduleSync(6, "network-stats-enabled");
                return;
        }
    }
}
