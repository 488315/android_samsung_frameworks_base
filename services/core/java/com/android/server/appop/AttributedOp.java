package com.android.server.appop;

import android.app.AppOpsManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.LongSparseArray;
import android.util.Pools;
import android.util.Slog;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledRunnable;
import com.android.server.appop.AppOpsService;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AttributedOp {
    public LongSparseArray mAccessEvents;
    public final AppOpsService mAppOpsService;
    public ArrayMap mInProgressEvents;
    public ArrayMap mPausedInProgressEvents;
    public LongSparseArray mRejectEvents;
    public final AppOpsService.Op parent;
    public final String tag;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InProgressStartOpEvent implements IBinder.DeathRecipient {
        public int mAttributionChainId;
        public int mAttributionFlags;
        public IBinder mClientId;
        public int mFlags;
        public int mNumUnfinishedStarts;
        public Runnable mOnDeath;
        public AppOpsManager.OpEventProxyInfo mProxy;
        public long mStartElapsedTime;
        public long mStartTime;
        public int mUidState;
        public int mVirtualDeviceId;

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            this.mOnDeath.run();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InProgressStartOpEventPool extends Pools.SimplePool {
        public OpEventProxyInfoPool mOpEventProxyInfoPool;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OpEventProxyInfoPool extends Pools.SimplePool {
    }

    public AttributedOp(AppOpsService appOpsService, String str, AppOpsService.Op op) {
        this.mAppOpsService = appOpsService;
        this.tag = str;
        this.parent = op;
    }

    public static LongSparseArray add(LongSparseArray longSparseArray, LongSparseArray longSparseArray2) {
        if (longSparseArray == null) {
            return longSparseArray2;
        }
        if (longSparseArray2 == null) {
            return longSparseArray;
        }
        int size = longSparseArray2.size();
        for (int i = 0; i < size; i++) {
            long keyAt = longSparseArray2.keyAt(i);
            AppOpsManager.NoteOpEvent noteOpEvent = (AppOpsManager.NoteOpEvent) longSparseArray2.valueAt(i);
            AppOpsManager.NoteOpEvent noteOpEvent2 = (AppOpsManager.NoteOpEvent) longSparseArray.get(keyAt);
            if (noteOpEvent2 == null || noteOpEvent.getNoteTime() > noteOpEvent2.getNoteTime()) {
                longSparseArray.put(keyAt, noteOpEvent);
            }
        }
        return longSparseArray;
    }

    public static LongSparseArray deepClone(LongSparseArray longSparseArray) {
        if (longSparseArray == null) {
            return longSparseArray;
        }
        int size = longSparseArray.size();
        LongSparseArray longSparseArray2 = new LongSparseArray(size);
        for (int i = 0; i < size; i++) {
            longSparseArray2.put(longSparseArray.keyAt(i), new AppOpsManager.NoteOpEvent((AppOpsManager.NoteOpEvent) longSparseArray.valueAt(i)));
        }
        return longSparseArray2;
    }

    public final void accessed(int i, int i2, String str, String str2, String str3, int i3) {
        long currentTimeMillis = System.currentTimeMillis();
        accessed(currentTimeMillis, -1L, i, str, str2, str3, i2, i3);
        HistoricalRegistry historicalRegistry = this.mAppOpsService.mHistoricalRegistry;
        AppOpsService.Op op = this.parent;
        historicalRegistry.incrementOpAccessedCount(op.op, op.uid, op.packageName, this.tag, i2, i3, currentTimeMillis, 0, -1);
        AppOpsService appOpsService = this.mAppOpsService;
        AppOpsService.Op op2 = this.parent;
        int i4 = op2.op;
        int i5 = op2.uid;
        String str4 = op2.packageName;
        String str5 = this.tag;
        appOpsService.writePermissionAccessInformation(i4, i5, str4, str, str5 == null ? str2 : str5, i2);
    }

    public final void accessed(long j, long j2, int i, String str, String str2, String str3, int i2, int i3) {
        AppOpsManager.OpEventProxyInfo opEventProxyInfo;
        long makeKey = AppOpsManager.makeKey(i2, i3);
        if (this.mAccessEvents == null) {
            this.mAccessEvents = new LongSparseArray(1);
        }
        AppOpsService appOpsService = this.mAppOpsService;
        if (i != -1) {
            opEventProxyInfo = (AppOpsManager.OpEventProxyInfo) appOpsService.mOpEventProxyInfoPool.acquire();
            if (opEventProxyInfo != null) {
                opEventProxyInfo.reinit(i, str, str2, str3);
            } else {
                opEventProxyInfo = new AppOpsManager.OpEventProxyInfo(i, str, str2, str3);
            }
        } else {
            opEventProxyInfo = null;
        }
        AppOpsManager.NoteOpEvent noteOpEvent = (AppOpsManager.NoteOpEvent) this.mAccessEvents.get(makeKey);
        if (noteOpEvent == null) {
            this.mAccessEvents.put(makeKey, new AppOpsManager.NoteOpEvent(j, j2, opEventProxyInfo));
        } else {
            noteOpEvent.reinit(j, j2, opEventProxyInfo, appOpsService.mOpEventProxyInfoPool);
        }
    }

    public final AppOpsManager.AttributedOpEntry createAttributedOpEntryLocked() {
        LongSparseArray deepClone = deepClone(this.mAccessEvents);
        if (isRunning()) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            int size = this.mInProgressEvents.size();
            if (deepClone == null) {
                deepClone = new LongSparseArray(size);
            }
            int i = 0;
            while (i < size) {
                InProgressStartOpEvent inProgressStartOpEvent = (InProgressStartOpEvent) this.mInProgressEvents.valueAt(i);
                deepClone.append(AppOpsManager.makeKey(inProgressStartOpEvent.mUidState, inProgressStartOpEvent.mFlags), new AppOpsManager.NoteOpEvent(inProgressStartOpEvent.mStartTime, Math.max(elapsedRealtime - inProgressStartOpEvent.mStartElapsedTime, 0L), inProgressStartOpEvent.mProxy));
                i++;
                elapsedRealtime = elapsedRealtime;
            }
        }
        return new AppOpsManager.AttributedOpEntry(this.parent.op, isRunning(), deepClone, deepClone(this.mRejectEvents));
    }

    public final void doForAllInProgressStartOpEvents(Consumer consumer) {
        ArrayMap arrayMap = isPaused() ? this.mPausedInProgressEvents : this.mInProgressEvents;
        if (arrayMap == null) {
            return;
        }
        int size = arrayMap.size();
        ArraySet arraySet = new ArraySet(arrayMap.keySet());
        for (int i = 0; i < size; i++) {
            consumer.accept((InProgressStartOpEvent) arrayMap.get(arraySet.valueAt(i)));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:50:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:61:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void finishOrPause(android.os.IBinder r27, boolean r28, boolean r29) {
        /*
            Method dump skipped, instructions count: 350
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appop.AttributedOp.finishOrPause(android.os.IBinder, boolean, boolean):void");
    }

    public final boolean isPaused() {
        ArrayMap arrayMap = this.mPausedInProgressEvents;
        return (arrayMap == null || arrayMap.isEmpty()) ? false : true;
    }

    public final boolean isRunning() {
        ArrayMap arrayMap = this.mInProgressEvents;
        return (arrayMap == null || arrayMap.isEmpty()) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0140  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onUidStateChanged(int r32) {
        /*
            Method dump skipped, instructions count: 381
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appop.AttributedOp.onUidStateChanged(int):void");
    }

    public final void rejected(int i, int i2) {
        rejected(i, i2, System.currentTimeMillis());
        HistoricalRegistry historicalRegistry = this.mAppOpsService.mHistoricalRegistry;
        AppOpsService.Op op = this.parent;
        int i3 = op.op;
        int i4 = op.uid;
        String str = op.packageName;
        String str2 = this.tag;
        synchronized (historicalRegistry.mInMemoryLock) {
            try {
                if (historicalRegistry.mMode == 1) {
                    if (!historicalRegistry.isPersistenceInitializedMLocked()) {
                        Slog.v(HistoricalRegistry.LOG_TAG, "Interaction before persistence initialized");
                        return;
                    }
                    historicalRegistry.getUpdatedPendingHistoricalOpsMLocked(System.currentTimeMillis()).increaseRejectCount(i3, i4, str, str2, i, i2, 1L);
                }
            } finally {
            }
        }
    }

    public final void rejected(int i, int i2, long j) {
        long makeKey = AppOpsManager.makeKey(i, i2);
        if (this.mRejectEvents == null) {
            this.mRejectEvents = new LongSparseArray(1);
        }
        AppOpsManager.NoteOpEvent noteOpEvent = (AppOpsManager.NoteOpEvent) this.mRejectEvents.get(makeKey);
        if (noteOpEvent != null) {
            noteOpEvent.reinit(j, -1L, (AppOpsManager.OpEventProxyInfo) null, this.mAppOpsService.mOpEventProxyInfoPool);
        } else {
            this.mRejectEvents.put(makeKey, new AppOpsManager.NoteOpEvent(j, -1L, (AppOpsManager.OpEventProxyInfo) null));
        }
    }

    public final void startedOrPaused(IBinder iBinder, int i, int i2, String str, String str2, String str3, int i3, int i4, int i5, int i6, boolean z, boolean z2) {
        AppOpsManager.OpEventProxyInfo opEventProxyInfo;
        InProgressStartOpEvent inProgressStartOpEvent;
        String str4 = str2;
        if (!z && !this.parent.isRunning() && z2) {
            AppOpsService appOpsService = this.mAppOpsService;
            AppOpsService.Op op = this.parent;
            appOpsService.scheduleOpActiveChangedIfNeededLocked(op.op, op.uid, op.packageName, this.tag, i, true, i5, i6);
        }
        if (z2 && this.mInProgressEvents == null) {
            this.mInProgressEvents = new ArrayMap(1);
        } else if (!z2 && this.mPausedInProgressEvents == null) {
            this.mPausedInProgressEvents = new ArrayMap(1);
        }
        ArrayMap arrayMap = z2 ? this.mInProgressEvents : this.mPausedInProgressEvents;
        long currentTimeMillis = System.currentTimeMillis();
        InProgressStartOpEvent inProgressStartOpEvent2 = (InProgressStartOpEvent) arrayMap.get(iBinder);
        if (inProgressStartOpEvent2 == null) {
            InProgressStartOpEventPool inProgressStartOpEventPool = this.mAppOpsService.mInProgressStartOpEventPool;
            long elapsedRealtime = SystemClock.elapsedRealtime();
            PooledRunnable obtainRunnable = PooledLambda.obtainRunnable(new AttributedOp$$ExternalSyntheticLambda0(), this, iBinder);
            InProgressStartOpEvent inProgressStartOpEvent3 = (InProgressStartOpEvent) inProgressStartOpEventPool.acquire();
            if (i2 != -1) {
                opEventProxyInfo = (AppOpsManager.OpEventProxyInfo) inProgressStartOpEventPool.mOpEventProxyInfoPool.acquire();
                if (opEventProxyInfo != null) {
                    opEventProxyInfo.reinit(i2, str, str4, str3);
                } else {
                    opEventProxyInfo = new AppOpsManager.OpEventProxyInfo(i2, str, str4, str3);
                }
            } else {
                opEventProxyInfo = null;
            }
            if (inProgressStartOpEvent3 != null) {
                OpEventProxyInfoPool opEventProxyInfoPool = inProgressStartOpEventPool.mOpEventProxyInfoPool;
                inProgressStartOpEvent3.mStartTime = currentTimeMillis;
                inProgressStartOpEvent3.mStartElapsedTime = elapsedRealtime;
                inProgressStartOpEvent3.mClientId = iBinder;
                inProgressStartOpEvent3.mOnDeath = obtainRunnable;
                inProgressStartOpEvent3.mVirtualDeviceId = i;
                inProgressStartOpEvent3.mUidState = i3;
                inProgressStartOpEvent3.mFlags = i4;
                AppOpsManager.OpEventProxyInfo opEventProxyInfo2 = inProgressStartOpEvent3.mProxy;
                if (opEventProxyInfo2 != null) {
                    opEventProxyInfoPool.release(opEventProxyInfo2);
                }
                inProgressStartOpEvent3.mProxy = opEventProxyInfo;
                inProgressStartOpEvent3.mAttributionFlags = i5;
                inProgressStartOpEvent3.mAttributionChainId = i6;
                iBinder.linkToDeath(inProgressStartOpEvent3, 0);
                inProgressStartOpEvent = inProgressStartOpEvent3;
            } else {
                inProgressStartOpEvent = new InProgressStartOpEvent();
                inProgressStartOpEvent.mStartTime = currentTimeMillis;
                inProgressStartOpEvent.mStartElapsedTime = elapsedRealtime;
                inProgressStartOpEvent.mClientId = iBinder;
                inProgressStartOpEvent.mVirtualDeviceId = i;
                inProgressStartOpEvent.mOnDeath = obtainRunnable;
                inProgressStartOpEvent.mUidState = i3;
                inProgressStartOpEvent.mProxy = opEventProxyInfo;
                inProgressStartOpEvent.mFlags = i4;
                inProgressStartOpEvent.mAttributionFlags = i5;
                inProgressStartOpEvent.mAttributionChainId = i6;
                iBinder.linkToDeath(inProgressStartOpEvent, 0);
            }
            arrayMap.put(iBinder, inProgressStartOpEvent);
            inProgressStartOpEvent2 = inProgressStartOpEvent;
        } else if (i3 != inProgressStartOpEvent2.mUidState) {
            onUidStateChanged(i3);
        }
        inProgressStartOpEvent2.mNumUnfinishedStarts++;
        if (z2) {
            HistoricalRegistry historicalRegistry = this.mAppOpsService.mHistoricalRegistry;
            AppOpsService.Op op2 = this.parent;
            historicalRegistry.incrementOpAccessedCount(op2.op, op2.uid, op2.packageName, this.tag, i3, i4, currentTimeMillis, i5, i6);
            AppOpsService appOpsService2 = this.mAppOpsService;
            AppOpsService.Op op3 = this.parent;
            int i7 = op3.op;
            int i8 = op3.uid;
            String str5 = op3.packageName;
            String str6 = this.tag;
            if (str6 != null) {
                str4 = str6;
            }
            appOpsService2.writePermissionAccessInformation(i7, i8, str5, null, str4, i3);
        }
    }
}
