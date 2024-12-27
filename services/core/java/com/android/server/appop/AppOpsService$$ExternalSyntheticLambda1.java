package com.android.server.appop;

import android.app.AppOpsManager;
import android.util.Slog;

import com.android.internal.util.function.TriConsumer;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AppOpsService$$ExternalSyntheticLambda1 implements TriConsumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ AppOpsService$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj, Object obj2, Object obj3) {
        DiscreteRegistry.DiscreteOps allDiscreteOps;
        switch (this.$r8$classId) {
            case 0:
                ((AppOpsService) obj)
                        .notifyWatchersOnDefaultDevice(
                                ((Integer) obj2).intValue(), ((Integer) obj3).intValue());
                return;
            case 1:
                HistoricalRegistry historicalRegistry = (HistoricalRegistry) obj;
                int intValue = ((Integer) obj2).intValue();
                String str = (String) obj3;
                synchronized (historicalRegistry.mOnDiskLock) {
                    synchronized (historicalRegistry.mInMemoryLock) {
                        if (!historicalRegistry.isPersistenceInitializedMLocked()) {
                            Slog.d(
                                    HistoricalRegistry.LOG_TAG,
                                    "Interaction before persistence initialized");
                            return;
                        }
                        if (historicalRegistry.mMode != 1) {
                            return;
                        }
                        for (int i = 0; i < historicalRegistry.mPendingWrites.size(); i++) {
                            ((AppOpsManager.HistoricalOps) historicalRegistry.mPendingWrites.get(i))
                                    .clearHistory(intValue, str);
                        }
                        historicalRegistry
                                .getUpdatedPendingHistoricalOpsMLocked(System.currentTimeMillis())
                                .clearHistory(intValue, str);
                        HistoricalRegistry.Persistence persistence =
                                historicalRegistry.mPersistence;
                        List readHistoryDLocked = persistence.readHistoryDLocked();
                        if (readHistoryDLocked != null) {
                            for (int i2 = 0; i2 < readHistoryDLocked.size(); i2++) {
                                ((AppOpsManager.HistoricalOps) readHistoryDLocked.get(i2))
                                        .clearHistory(intValue, str);
                            }
                            HistoricalRegistry.Persistence.clearHistoryDLocked$1();
                            persistence.persistHistoricalOpsDLocked(readHistoryDLocked);
                        }
                        DiscreteRegistry discreteRegistry = historicalRegistry.mDiscreteRegistry;
                        synchronized (discreteRegistry.mOnDiskLock) {
                            synchronized (discreteRegistry.mInMemoryLock) {
                                allDiscreteOps = discreteRegistry.getAllDiscreteOps();
                                discreteRegistry.clearHistory();
                            }
                            DiscreteRegistry.DiscreteOps.m240$$Nest$mclearHistory(
                                    allDiscreteOps, intValue, str);
                            discreteRegistry.persistDiscreteOpsLocked(allDiscreteOps);
                        }
                        return;
                    }
                }
            default:
                int intValue2 = ((Integer) obj2).intValue();
                int intValue3 = ((Integer) obj3).intValue();
                boolean z = AppOpsService.DEBUG;
                ((AppOpsService) obj).notifyWatchersOnDefaultDevice(intValue2, intValue3);
                return;
        }
    }
}
