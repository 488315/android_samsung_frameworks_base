package com.android.server.appop;

import android.app.AppOpsManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Slog;

import com.android.internal.os.AtomicDirectory;
import com.android.internal.util.function.DodecConsumer;

import java.util.ArrayList;
import java.util.LinkedList;

public final /* synthetic */ class AppOpsService$$ExternalSyntheticLambda0
        implements DodecConsumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ AppOpsService$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    public final void accept(
            Object obj,
            Object obj2,
            Object obj3,
            Object obj4,
            Object obj5,
            Object obj6,
            Object obj7,
            Object obj8,
            Object obj9,
            Object obj10,
            Object obj11,
            Object obj12) {
        int i;
        String[] strArr;
        RemoteCallback remoteCallback;
        RemoteCallback remoteCallback2;
        Bundle bundle;
        long j;
        long j2;
        long j3;
        long j4;
        switch (this.$r8$classId) {
            case 0:
                HistoricalRegistry historicalRegistry = (HistoricalRegistry) obj;
                int intValue = ((Integer) obj2).intValue();
                String str = (String) obj3;
                String str2 = (String) obj4;
                String[] strArr2 = (String[]) obj5;
                int intValue2 = ((Integer) obj6).intValue();
                int intValue3 = ((Integer) obj7).intValue();
                long longValue = ((Long) obj8).longValue();
                long longValue2 = ((Long) obj9).longValue();
                int intValue4 = ((Integer) obj10).intValue();
                String[] strArr3 = (String[]) obj11;
                RemoteCallback remoteCallback3 = (RemoteCallback) obj12;
                historicalRegistry.getClass();
                AppOpsManager.HistoricalOps historicalOps =
                        new AppOpsManager.HistoricalOps(longValue, longValue2);
                if ((intValue2 & 1) != 0) {
                    synchronized (historicalRegistry.mOnDiskLock) {
                        strArr = strArr3;
                        synchronized (historicalRegistry.mInMemoryLock) {
                            if (!historicalRegistry.isPersistenceInitializedMLocked()) {
                                Slog.e(
                                        HistoricalRegistry.LOG_TAG,
                                        "Interaction before persistence initialized");
                                remoteCallback3.sendResult(new Bundle());
                                return;
                            }
                            HistoricalRegistry.Persistence persistence =
                                    historicalRegistry.mPersistence;
                            AtomicDirectory atomicDirectory =
                                    HistoricalRegistry.Persistence.sHistoricalAppOpsDir;
                            LinkedList collectHistoricalOpsBaseDLocked =
                                    persistence.collectHistoricalOpsBaseDLocked(
                                            intValue,
                                            str,
                                            str2,
                                            strArr2,
                                            intValue3,
                                            longValue,
                                            longValue2,
                                            intValue4);
                            if (collectHistoricalOpsBaseDLocked != null) {
                                remoteCallback = remoteCallback3;
                                int size = collectHistoricalOpsBaseDLocked.size();
                                i = intValue4;
                                int i2 = 0;
                                while (i2 < size) {
                                    historicalOps.merge(
                                            (AppOpsManager.HistoricalOps)
                                                    collectHistoricalOpsBaseDLocked.get(i2));
                                    i2++;
                                    collectHistoricalOpsBaseDLocked =
                                            collectHistoricalOpsBaseDLocked;
                                }
                            } else {
                                i = intValue4;
                                remoteCallback = remoteCallback3;
                            }
                        }
                    }
                } else {
                    i = intValue4;
                    strArr = strArr3;
                    remoteCallback = remoteCallback3;
                }
                if ((intValue2 & 2) != 0) {
                    historicalRegistry.mDiscreteRegistry.addFilteredDiscreteOpsToHistoricalOps(
                            historicalOps,
                            longValue,
                            longValue2,
                            intValue3,
                            intValue,
                            str,
                            strArr2,
                            str2,
                            i,
                            new ArraySet(strArr));
                }
                Bundle bundle2 = new Bundle();
                bundle2.putParcelable("historical_ops", historicalOps);
                remoteCallback.sendResult(bundle2);
                return;
            case 1:
                HistoricalRegistry historicalRegistry2 = (HistoricalRegistry) obj;
                int intValue5 = ((Integer) obj2).intValue();
                String str3 = (String) obj3;
                String str4 = (String) obj4;
                String[] strArr4 = (String[]) obj5;
                int intValue6 = ((Integer) obj6).intValue();
                int intValue7 = ((Integer) obj7).intValue();
                long longValue3 = ((Long) obj8).longValue();
                long longValue4 = ((Long) obj9).longValue();
                int intValue8 = ((Integer) obj10).intValue();
                String[] strArr5 = (String[]) obj11;
                RemoteCallback remoteCallback4 = (RemoteCallback) obj12;
                historicalRegistry2.getClass();
                long currentTimeMillis = System.currentTimeMillis();
                if (longValue4 == Long.MAX_VALUE) {
                    remoteCallback2 = remoteCallback4;
                    longValue4 = currentTimeMillis;
                } else {
                    remoteCallback2 = remoteCallback4;
                }
                Bundle bundle3 = new Bundle();
                long max = Math.max(currentTimeMillis - longValue4, 0L);
                long j5 = longValue4;
                long max2 = Math.max(currentTimeMillis - longValue3, 0L);
                AppOpsManager.HistoricalOps historicalOps2 =
                        new AppOpsManager.HistoricalOps(max, max2);
                if ((intValue6 & 2) != 0) {
                    bundle = bundle3;
                    historicalRegistry2.mDiscreteRegistry.addFilteredDiscreteOpsToHistoricalOps(
                            historicalOps2,
                            longValue3,
                            j5,
                            intValue7,
                            intValue5,
                            str3,
                            strArr4,
                            str4,
                            intValue8,
                            new ArraySet(strArr5));
                } else {
                    bundle = bundle3;
                }
                if ((intValue6 & 1) != 0) {
                    synchronized (historicalRegistry2.mOnDiskLock) {
                        synchronized (historicalRegistry2.mInMemoryLock) {
                            if (!historicalRegistry2.isPersistenceInitializedMLocked()) {
                                Slog.e(
                                        HistoricalRegistry.LOG_TAG,
                                        "Interaction before persistence initialized");
                                remoteCallback2.sendResult(new Bundle());
                                return;
                            }
                            AppOpsManager.HistoricalOps updatedPendingHistoricalOpsMLocked =
                                    historicalRegistry2.getUpdatedPendingHistoricalOpsMLocked(
                                            currentTimeMillis);
                            if (max >= updatedPendingHistoricalOpsMLocked.getEndTimeMillis()
                                    || max2
                                            <= updatedPendingHistoricalOpsMLocked
                                                    .getBeginTimeMillis()) {
                                j3 = longValue3;
                            } else {
                                j3 = longValue3;
                                AppOpsManager.HistoricalOps historicalOps3 =
                                        new AppOpsManager.HistoricalOps(
                                                updatedPendingHistoricalOpsMLocked);
                                historicalOps3.filter(
                                        intValue5, str3, str4, strArr4, intValue6, intValue7, max,
                                        max2);
                                historicalOps2.merge(historicalOps3);
                            }
                            ArrayList arrayList = new ArrayList(historicalRegistry2.mPendingWrites);
                            historicalRegistry2.mPendingWrites.clear();
                            boolean z =
                                    max2 > updatedPendingHistoricalOpsMLocked.getEndTimeMillis();
                            if (z) {
                                historicalRegistry2.persistPendingHistory(arrayList);
                                long j6 =
                                        (currentTimeMillis
                                                        - historicalRegistry2
                                                                .mNextPersistDueTimeMillis)
                                                + historicalRegistry2.mBaseSnapshotInterval;
                                long max3 = Math.max(max - j6, 0L);
                                long max4 = Math.max(max2 - j6, 0L);
                                HistoricalRegistry.Persistence persistence2 =
                                        historicalRegistry2.mPersistence;
                                AtomicDirectory atomicDirectory2 =
                                        HistoricalRegistry.Persistence.sHistoricalAppOpsDir;
                                LinkedList collectHistoricalOpsBaseDLocked2 =
                                        persistence2.collectHistoricalOpsBaseDLocked(
                                                intValue5, str3, str4, strArr4, intValue7, max3,
                                                max4, intValue8);
                                if (collectHistoricalOpsBaseDLocked2 != null) {
                                    int size2 = collectHistoricalOpsBaseDLocked2.size();
                                    for (int i3 = 0; i3 < size2; i3++) {
                                        historicalOps2.merge(
                                                (AppOpsManager.HistoricalOps)
                                                        collectHistoricalOpsBaseDLocked2.get(i3));
                                    }
                                }
                            }
                            j2 = j5;
                            j = j3;
                        }
                    }
                } else {
                    j = longValue3;
                    j2 = j5;
                }
                historicalOps2.setBeginAndEndTime(j, j2);
                Bundle bundle4 = bundle;
                bundle4.putParcelable("historical_ops", historicalOps2);
                remoteCallback2.sendResult(bundle4);
                return;
            default:
                AppOpsService appOpsService = (AppOpsService) obj;
                ArraySet arraySet = (ArraySet) obj2;
                int intValue9 = ((Integer) obj3).intValue();
                int intValue10 = ((Integer) obj4).intValue();
                String str5 = (String) obj5;
                String str6 = (String) obj6;
                int intValue11 = ((Integer) obj7).intValue();
                int intValue12 = ((Integer) obj8).intValue();
                int intValue13 = ((Integer) obj9).intValue();
                int intValue14 = ((Integer) obj10).intValue();
                int intValue15 = ((Integer) obj11).intValue();
                int intValue16 = ((Integer) obj12).intValue();
                appOpsService.getClass();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    int size3 = arraySet.size();
                    int i4 = 0;
                    while (i4 < size3) {
                        int i5 = size3;
                        AppOpsService.StartedCallback startedCallback =
                                (AppOpsService.StartedCallback) arraySet.valueAt(i4);
                        ArraySet arraySet2 = arraySet;
                        try {
                            j4 = clearCallingIdentity;
                            try {
                                if (!appOpsService.shouldIgnoreCallback(
                                        intValue9,
                                        startedCallback.mCallingPid,
                                        startedCallback.mCallingUid)) {
                                    startedCallback.mCallback.opStarted(
                                            intValue9,
                                            intValue10,
                                            str5,
                                            str6,
                                            intValue11,
                                            intValue12,
                                            intValue13,
                                            intValue14,
                                            intValue15,
                                            intValue16);
                                }
                            } catch (RemoteException unused) {
                            } catch (Throwable th) {
                                th = th;
                                Binder.restoreCallingIdentity(j4);
                                throw th;
                            }
                        } catch (RemoteException unused2) {
                            j4 = clearCallingIdentity;
                        }
                        i4++;
                        size3 = i5;
                        arraySet = arraySet2;
                        clearCallingIdentity = j4;
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return;
                } catch (Throwable th2) {
                    th = th2;
                    j4 = clearCallingIdentity;
                }
        }
    }
}
