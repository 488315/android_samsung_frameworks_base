package com.android.server.appop;

import android.app.AppOpsManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.LongSparseArray;
import android.util.Pools;
import android.util.Slog;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.appop.AppOpsService;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public final class AttributedOp {
    public LongSparseArray mAccessEvents;
    public final AppOpsService mAppOpsService;
    public ArrayMap mInProgressEvents;
    public ArrayMap mPausedInProgressEvents;
    public LongSparseArray mRejectEvents;
    public final AppOpsService.C0712Op parent;
    public final String tag;

    public AttributedOp(AppOpsService appOpsService, String str, AppOpsService.C0712Op c0712Op) {
        this.mAppOpsService = appOpsService;
        this.tag = str;
        this.parent = c0712Op;
    }

    public void accessed(int i, String str, String str2, int i2, int i3) {
        long currentTimeMillis = System.currentTimeMillis();
        accessed(currentTimeMillis, -1L, i, str, str2, i2, i3);
        HistoricalRegistry historicalRegistry = this.mAppOpsService.mHistoricalRegistry;
        AppOpsService.C0712Op c0712Op = this.parent;
        historicalRegistry.incrementOpAccessedCount(c0712Op.f1638op, c0712Op.uid, c0712Op.packageName, this.tag, i2, i3, currentTimeMillis, 0, -1);
        AppOpsService appOpsService = this.mAppOpsService;
        AppOpsService.C0712Op c0712Op2 = this.parent;
        int i4 = c0712Op2.f1638op;
        int i5 = c0712Op2.uid;
        String str3 = c0712Op2.packageName;
        String str4 = this.tag;
        appOpsService.writePermissionAccessInformation(i4, i5, str3, str, str4 == null ? str2 : str4, i2);
    }

    public void accessed(long j, long j2, int i, String str, String str2, int i2, int i3) {
        long makeKey = AppOpsManager.makeKey(i2, i3);
        if (this.mAccessEvents == null) {
            this.mAccessEvents = new LongSparseArray(1);
        }
        AppOpsManager.OpEventProxyInfo acquire = i != -1 ? this.mAppOpsService.mOpEventProxyInfoPool.acquire(i, str, str2) : null;
        AppOpsManager.NoteOpEvent noteOpEvent = (AppOpsManager.NoteOpEvent) this.mAccessEvents.get(makeKey);
        if (noteOpEvent != null) {
            noteOpEvent.reinit(j, j2, acquire, this.mAppOpsService.mOpEventProxyInfoPool);
        } else {
            this.mAccessEvents.put(makeKey, new AppOpsManager.NoteOpEvent(j, j2, acquire));
        }
    }

    public void rejected(int i, int i2) {
        rejected(System.currentTimeMillis(), i, i2);
        HistoricalRegistry historicalRegistry = this.mAppOpsService.mHistoricalRegistry;
        AppOpsService.C0712Op c0712Op = this.parent;
        historicalRegistry.incrementOpRejected(c0712Op.f1638op, c0712Op.uid, c0712Op.packageName, this.tag, i, i2);
    }

    public void rejected(long j, int i, int i2) {
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

    public void started(IBinder iBinder, int i, String str, String str2, int i2, int i3, int i4, int i5) {
        started(iBinder, i, str, str2, i2, i3, true, i4, i5);
    }

    public final void started(IBinder iBinder, int i, String str, String str2, int i2, int i3, boolean z, int i4, int i5) {
        startedOrPaused(iBinder, i, str, str2, i2, i3, z, true, i4, i5);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startedOrPaused(IBinder iBinder, int i, String str, String str2, int i2, int i3, boolean z, boolean z2, int i4, int i5) {
        AttributedOp attributedOp;
        if (z && !this.parent.isRunning() && z2) {
            AppOpsService appOpsService = this.mAppOpsService;
            AppOpsService.C0712Op c0712Op = this.parent;
            appOpsService.scheduleOpActiveChangedIfNeededLocked(c0712Op.f1638op, c0712Op.uid, c0712Op.packageName, this.tag, true, i4, i5);
        }
        if (z2 && this.mInProgressEvents == null) {
            this.mInProgressEvents = new ArrayMap(1);
        } else if (!z2 && this.mPausedInProgressEvents == null) {
            this.mPausedInProgressEvents = new ArrayMap(1);
        }
        ArrayMap arrayMap = z2 ? this.mInProgressEvents : this.mPausedInProgressEvents;
        long currentTimeMillis = System.currentTimeMillis();
        InProgressStartOpEvent inProgressStartOpEvent = (InProgressStartOpEvent) arrayMap.get(iBinder);
        if (inProgressStartOpEvent == null) {
            inProgressStartOpEvent = this.mAppOpsService.mInProgressStartOpEventPool.acquire(currentTimeMillis, SystemClock.elapsedRealtime(), iBinder, this.tag, PooledLambda.obtainRunnable(new BiConsumer() { // from class: com.android.server.appop.AttributedOp$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    AppOpsService.onClientDeath((AttributedOp) obj, (IBinder) obj2);
                }
            }, this, iBinder), i, str, str2, i2, i3, i4, i5);
            arrayMap.put(iBinder, inProgressStartOpEvent);
        } else if (i2 != inProgressStartOpEvent.getUidState()) {
            attributedOp = this;
            attributedOp.onUidStateChanged(i2);
            inProgressStartOpEvent.mNumUnfinishedStarts++;
            if (z2) {
                return;
            }
            HistoricalRegistry historicalRegistry = attributedOp.mAppOpsService.mHistoricalRegistry;
            AppOpsService.C0712Op c0712Op2 = attributedOp.parent;
            historicalRegistry.incrementOpAccessedCount(c0712Op2.f1638op, c0712Op2.uid, c0712Op2.packageName, attributedOp.tag, i2, i3, currentTimeMillis, i4, i5);
            AppOpsService appOpsService2 = attributedOp.mAppOpsService;
            AppOpsService.C0712Op c0712Op3 = attributedOp.parent;
            int i6 = c0712Op3.f1638op;
            int i7 = c0712Op3.uid;
            String str3 = c0712Op3.packageName;
            String str4 = attributedOp.tag;
            appOpsService2.writePermissionAccessInformation(i6, i7, str3, null, str4 == null ? str2 : str4, i2);
            return;
        }
        attributedOp = this;
        inProgressStartOpEvent.mNumUnfinishedStarts++;
        if (z2) {
        }
    }

    public void finished(IBinder iBinder) {
        finished(iBinder, true);
    }

    public final void finished(IBinder iBinder, boolean z) {
        finishOrPause(iBinder, z, false);
    }

    public final void finishOrPause(IBinder iBinder, boolean z, boolean z2) {
        int indexOfKey = isRunning() ? this.mInProgressEvents.indexOfKey(iBinder) : -1;
        if (indexOfKey < 0) {
            finishPossiblyPaused(iBinder, z2);
            return;
        }
        InProgressStartOpEvent inProgressStartOpEvent = (InProgressStartOpEvent) this.mInProgressEvents.valueAt(indexOfKey);
        if (!z2) {
            inProgressStartOpEvent.mNumUnfinishedStarts--;
        }
        if (inProgressStartOpEvent.mNumUnfinishedStarts == 0 || z2) {
            if (!z2) {
                inProgressStartOpEvent.finish();
                this.mInProgressEvents.removeAt(indexOfKey);
            }
            if (this.mAccessEvents == null) {
                this.mAccessEvents = new LongSparseArray(1);
            }
            AppOpsManager.NoteOpEvent noteOpEvent = new AppOpsManager.NoteOpEvent(inProgressStartOpEvent.getStartTime(), SystemClock.elapsedRealtime() - inProgressStartOpEvent.getStartElapsedTime(), inProgressStartOpEvent.getProxy() != null ? new AppOpsManager.OpEventProxyInfo(inProgressStartOpEvent.getProxy()) : null);
            this.mAccessEvents.put(AppOpsManager.makeKey(inProgressStartOpEvent.getUidState(), inProgressStartOpEvent.getFlags()), noteOpEvent);
            HistoricalRegistry historicalRegistry = this.mAppOpsService.mHistoricalRegistry;
            AppOpsService.C0712Op c0712Op = this.parent;
            historicalRegistry.increaseOpAccessDuration(c0712Op.f1638op, c0712Op.uid, c0712Op.packageName, this.tag, inProgressStartOpEvent.getUidState(), inProgressStartOpEvent.getFlags(), noteOpEvent.getNoteTime(), noteOpEvent.getDuration(), inProgressStartOpEvent.getAttributionFlags(), inProgressStartOpEvent.getAttributionChainId());
            if (z2) {
                return;
            }
            this.mAppOpsService.mInProgressStartOpEventPool.release(inProgressStartOpEvent);
            if (this.mInProgressEvents.isEmpty()) {
                this.mInProgressEvents = null;
                if (!z || this.parent.isRunning()) {
                    return;
                }
                AppOpsService appOpsService = this.mAppOpsService;
                AppOpsService.C0712Op c0712Op2 = this.parent;
                appOpsService.scheduleOpActiveChangedIfNeededLocked(c0712Op2.f1638op, c0712Op2.uid, c0712Op2.packageName, this.tag, false, inProgressStartOpEvent.getAttributionFlags(), inProgressStartOpEvent.getAttributionChainId());
            }
        }
    }

    public final void finishPossiblyPaused(IBinder iBinder, boolean z) {
        if (!isPaused()) {
            Slog.wtf("AppOps", "No ops running or paused");
            return;
        }
        int indexOfKey = this.mPausedInProgressEvents.indexOfKey(iBinder);
        if (indexOfKey < 0) {
            Slog.wtf("AppOps", "No op running or paused for the client");
            return;
        }
        if (z) {
            return;
        }
        InProgressStartOpEvent inProgressStartOpEvent = (InProgressStartOpEvent) this.mPausedInProgressEvents.valueAt(indexOfKey);
        int i = inProgressStartOpEvent.mNumUnfinishedStarts - 1;
        inProgressStartOpEvent.mNumUnfinishedStarts = i;
        if (i == 0) {
            this.mPausedInProgressEvents.removeAt(indexOfKey);
            this.mAppOpsService.mInProgressStartOpEventPool.release(inProgressStartOpEvent);
            if (this.mPausedInProgressEvents.isEmpty()) {
                this.mPausedInProgressEvents = null;
            }
        }
    }

    public void createPaused(IBinder iBinder, int i, String str, String str2, int i2, int i3, int i4, int i5) {
        startedOrPaused(iBinder, i, str, str2, i2, i3, true, false, i4, i5);
    }

    public void pause() {
        if (isRunning()) {
            if (this.mPausedInProgressEvents == null) {
                this.mPausedInProgressEvents = new ArrayMap(1);
            }
            for (int i = 0; i < this.mInProgressEvents.size(); i++) {
                InProgressStartOpEvent inProgressStartOpEvent = (InProgressStartOpEvent) this.mInProgressEvents.valueAt(i);
                this.mPausedInProgressEvents.put(inProgressStartOpEvent.getClientId(), inProgressStartOpEvent);
                finishOrPause(inProgressStartOpEvent.getClientId(), true, true);
                AppOpsService appOpsService = this.mAppOpsService;
                AppOpsService.C0712Op c0712Op = this.parent;
                appOpsService.scheduleOpActiveChangedIfNeededLocked(c0712Op.f1638op, c0712Op.uid, c0712Op.packageName, this.tag, false, inProgressStartOpEvent.getAttributionFlags(), inProgressStartOpEvent.getAttributionChainId());
            }
            this.mInProgressEvents = null;
        }
    }

    public void resume() {
        if (isPaused()) {
            if (this.mInProgressEvents == null) {
                this.mInProgressEvents = new ArrayMap(this.mPausedInProgressEvents.size());
            }
            boolean z = !this.mPausedInProgressEvents.isEmpty() && this.mInProgressEvents.isEmpty();
            long currentTimeMillis = System.currentTimeMillis();
            for (int i = 0; i < this.mPausedInProgressEvents.size(); i++) {
                InProgressStartOpEvent inProgressStartOpEvent = (InProgressStartOpEvent) this.mPausedInProgressEvents.valueAt(i);
                this.mInProgressEvents.put(inProgressStartOpEvent.getClientId(), inProgressStartOpEvent);
                inProgressStartOpEvent.setStartElapsedTime(SystemClock.elapsedRealtime());
                inProgressStartOpEvent.setStartTime(currentTimeMillis);
                HistoricalRegistry historicalRegistry = this.mAppOpsService.mHistoricalRegistry;
                AppOpsService.C0712Op c0712Op = this.parent;
                historicalRegistry.incrementOpAccessedCount(c0712Op.f1638op, c0712Op.uid, c0712Op.packageName, this.tag, inProgressStartOpEvent.getUidState(), inProgressStartOpEvent.getFlags(), currentTimeMillis, inProgressStartOpEvent.getAttributionFlags(), inProgressStartOpEvent.getAttributionChainId());
                if (z) {
                    AppOpsService appOpsService = this.mAppOpsService;
                    AppOpsService.C0712Op c0712Op2 = this.parent;
                    appOpsService.scheduleOpActiveChangedIfNeededLocked(c0712Op2.f1638op, c0712Op2.uid, c0712Op2.packageName, this.tag, true, inProgressStartOpEvent.getAttributionFlags(), inProgressStartOpEvent.getAttributionChainId());
                }
                AppOpsService appOpsService2 = this.mAppOpsService;
                AppOpsService.C0712Op c0712Op3 = this.parent;
                appOpsService2.scheduleOpStartedIfNeededLocked(c0712Op3.f1638op, c0712Op3.uid, c0712Op3.packageName, this.tag, inProgressStartOpEvent.getFlags(), 0, 2, inProgressStartOpEvent.getAttributionFlags(), inProgressStartOpEvent.getAttributionChainId());
            }
            this.mPausedInProgressEvents = null;
        }
    }

    public void onClientDeath(IBinder iBinder) {
        synchronized (this.mAppOpsService) {
            if (isPaused() || isRunning()) {
                InProgressStartOpEvent inProgressStartOpEvent = (InProgressStartOpEvent) (isPaused() ? this.mPausedInProgressEvents : this.mInProgressEvents).get(iBinder);
                if (inProgressStartOpEvent != null) {
                    inProgressStartOpEvent.mNumUnfinishedStarts = 1;
                }
                finished(iBinder);
            }
        }
    }

    public void onUidStateChanged(int i) {
        int i2;
        ArrayMap arrayMap;
        int i3;
        if (isPaused() || isRunning()) {
            boolean isRunning = isRunning();
            ArrayMap arrayMap2 = isRunning ? this.mInProgressEvents : this.mPausedInProgressEvents;
            int size = arrayMap2.size();
            ArrayList arrayList = new ArrayList(arrayMap2.keySet());
            boolean z = false;
            ArrayMap arrayMap3 = arrayMap2;
            int i4 = 0;
            while (i4 < size) {
                InProgressStartOpEvent inProgressStartOpEvent = (InProgressStartOpEvent) arrayMap3.get(arrayList.get(i4));
                if (inProgressStartOpEvent == null || inProgressStartOpEvent.getUidState() == i) {
                    i2 = i4;
                    arrayMap = arrayMap3;
                } else {
                    try {
                        int i5 = inProgressStartOpEvent.mNumUnfinishedStarts;
                        inProgressStartOpEvent.mNumUnfinishedStarts = 1;
                        AppOpsManager.OpEventProxyInfo proxy = inProgressStartOpEvent.getProxy();
                        finished(inProgressStartOpEvent.getClientId(), z);
                        if (proxy != null) {
                            i3 = i5;
                            i2 = i4;
                            arrayMap = arrayMap3;
                            try {
                                startedOrPaused(inProgressStartOpEvent.getClientId(), proxy.getUid(), proxy.getPackageName(), proxy.getAttributionTag(), i, inProgressStartOpEvent.getFlags(), false, isRunning, inProgressStartOpEvent.getAttributionFlags(), inProgressStartOpEvent.getAttributionChainId());
                            } catch (RemoteException unused) {
                            }
                        } else {
                            i3 = i5;
                            i2 = i4;
                            startedOrPaused(inProgressStartOpEvent.getClientId(), -1, null, null, i, inProgressStartOpEvent.getFlags(), false, isRunning, inProgressStartOpEvent.getAttributionFlags(), inProgressStartOpEvent.getAttributionChainId());
                        }
                        arrayMap3 = isRunning ? this.mInProgressEvents : this.mPausedInProgressEvents;
                        try {
                            InProgressStartOpEvent inProgressStartOpEvent2 = (InProgressStartOpEvent) arrayMap3.get(arrayList.get(i2));
                            if (inProgressStartOpEvent2 != null) {
                                inProgressStartOpEvent2.mNumUnfinishedStarts += i3 - 1;
                            }
                        } catch (RemoteException unused2) {
                        }
                    } catch (RemoteException unused3) {
                        i2 = i4;
                    }
                    i4 = i2 + 1;
                    z = false;
                }
                arrayMap3 = arrayMap;
                i4 = i2 + 1;
                z = false;
            }
        }
    }

    public final LongSparseArray add(LongSparseArray longSparseArray, LongSparseArray longSparseArray2) {
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

    public void add(AttributedOp attributedOp) {
        if (attributedOp.isRunning() || attributedOp.isPaused()) {
            ArrayMap arrayMap = attributedOp.isRunning() ? attributedOp.mInProgressEvents : attributedOp.mPausedInProgressEvents;
            Slog.w("AppOps", "Ignoring " + arrayMap.size() + " app-ops, running: " + attributedOp.isRunning());
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                InProgressStartOpEvent inProgressStartOpEvent = (InProgressStartOpEvent) arrayMap.valueAt(i);
                inProgressStartOpEvent.finish();
                this.mAppOpsService.mInProgressStartOpEventPool.release(inProgressStartOpEvent);
            }
        }
        this.mAccessEvents = add(this.mAccessEvents, attributedOp.mAccessEvents);
        this.mRejectEvents = add(this.mRejectEvents, attributedOp.mRejectEvents);
    }

    public boolean isRunning() {
        ArrayMap arrayMap = this.mInProgressEvents;
        return (arrayMap == null || arrayMap.isEmpty()) ? false : true;
    }

    public boolean isPaused() {
        ArrayMap arrayMap = this.mPausedInProgressEvents;
        return (arrayMap == null || arrayMap.isEmpty()) ? false : true;
    }

    public boolean hasAnyTime() {
        LongSparseArray longSparseArray;
        LongSparseArray longSparseArray2 = this.mAccessEvents;
        return (longSparseArray2 != null && longSparseArray2.size() > 0) || ((longSparseArray = this.mRejectEvents) != null && longSparseArray.size() > 0);
    }

    public final LongSparseArray deepClone(LongSparseArray longSparseArray) {
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

    public AppOpsManager.AttributedOpEntry createAttributedOpEntryLocked() {
        LongSparseArray deepClone = deepClone(this.mAccessEvents);
        if (isRunning()) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            int size = this.mInProgressEvents.size();
            if (deepClone == null) {
                deepClone = new LongSparseArray(size);
            }
            for (int i = 0; i < size; i++) {
                InProgressStartOpEvent inProgressStartOpEvent = (InProgressStartOpEvent) this.mInProgressEvents.valueAt(i);
                deepClone.append(AppOpsManager.makeKey(inProgressStartOpEvent.getUidState(), inProgressStartOpEvent.getFlags()), new AppOpsManager.NoteOpEvent(inProgressStartOpEvent.getStartTime(), elapsedRealtime - inProgressStartOpEvent.getStartElapsedTime(), inProgressStartOpEvent.getProxy()));
            }
        }
        return new AppOpsManager.AttributedOpEntry(this.parent.f1638op, isRunning(), deepClone, deepClone(this.mRejectEvents));
    }

    public final class InProgressStartOpEvent implements IBinder.DeathRecipient {
        public int mAttributionChainId;
        public int mAttributionFlags;
        public String mAttributionTag;
        public IBinder mClientId;
        public int mFlags;
        public int mNumUnfinishedStarts;
        public Runnable mOnDeath;
        public AppOpsManager.OpEventProxyInfo mProxy;
        public long mStartElapsedTime;
        public long mStartTime;
        public int mUidState;

        public InProgressStartOpEvent(long j, long j2, IBinder iBinder, String str, Runnable runnable, int i, AppOpsManager.OpEventProxyInfo opEventProxyInfo, int i2, int i3, int i4) {
            this.mStartTime = j;
            this.mStartElapsedTime = j2;
            this.mClientId = iBinder;
            this.mAttributionTag = str;
            this.mOnDeath = runnable;
            this.mUidState = i;
            this.mProxy = opEventProxyInfo;
            this.mFlags = i2;
            this.mAttributionFlags = i3;
            this.mAttributionChainId = i4;
            iBinder.linkToDeath(this, 0);
        }

        public void finish() {
            try {
                this.mClientId.unlinkToDeath(this, 0);
            } catch (NoSuchElementException unused) {
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.mOnDeath.run();
        }

        public void reinit(long j, long j2, IBinder iBinder, String str, Runnable runnable, int i, int i2, AppOpsManager.OpEventProxyInfo opEventProxyInfo, int i3, int i4, Pools.Pool pool) {
            this.mStartTime = j;
            this.mStartElapsedTime = j2;
            this.mClientId = iBinder;
            this.mAttributionTag = str;
            this.mOnDeath = runnable;
            this.mUidState = i;
            this.mFlags = i2;
            AppOpsManager.OpEventProxyInfo opEventProxyInfo2 = this.mProxy;
            if (opEventProxyInfo2 != null) {
                pool.release(opEventProxyInfo2);
            }
            this.mProxy = opEventProxyInfo;
            this.mAttributionFlags = i3;
            this.mAttributionChainId = i4;
            iBinder.linkToDeath(this, 0);
        }

        public long getStartTime() {
            return this.mStartTime;
        }

        public long getStartElapsedTime() {
            return this.mStartElapsedTime;
        }

        public IBinder getClientId() {
            return this.mClientId;
        }

        public int getUidState() {
            return this.mUidState;
        }

        public AppOpsManager.OpEventProxyInfo getProxy() {
            return this.mProxy;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public int getAttributionFlags() {
            return this.mAttributionFlags;
        }

        public int getAttributionChainId() {
            return this.mAttributionChainId;
        }

        public void setStartTime(long j) {
            this.mStartTime = j;
        }

        public void setStartElapsedTime(long j) {
            this.mStartElapsedTime = j;
        }
    }

    public class InProgressStartOpEventPool extends Pools.SimplePool {
        public OpEventProxyInfoPool mOpEventProxyInfoPool;

        public InProgressStartOpEventPool(OpEventProxyInfoPool opEventProxyInfoPool, int i) {
            super(i);
            this.mOpEventProxyInfoPool = opEventProxyInfoPool;
        }

        public InProgressStartOpEvent acquire(long j, long j2, IBinder iBinder, String str, Runnable runnable, int i, String str2, String str3, int i2, int i3, int i4, int i5) {
            InProgressStartOpEvent inProgressStartOpEvent = (InProgressStartOpEvent) acquire();
            AppOpsManager.OpEventProxyInfo acquire = i != -1 ? this.mOpEventProxyInfoPool.acquire(i, str2, str3) : null;
            if (inProgressStartOpEvent != null) {
                inProgressStartOpEvent.reinit(j, j2, iBinder, str, runnable, i2, i3, acquire, i4, i5, this.mOpEventProxyInfoPool);
                return inProgressStartOpEvent;
            }
            return new InProgressStartOpEvent(j, j2, iBinder, str, runnable, i2, acquire, i3, i4, i5);
        }
    }

    public class OpEventProxyInfoPool extends Pools.SimplePool {
        public OpEventProxyInfoPool(int i) {
            super(i);
        }

        public AppOpsManager.OpEventProxyInfo acquire(int i, String str, String str2) {
            AppOpsManager.OpEventProxyInfo opEventProxyInfo = (AppOpsManager.OpEventProxyInfo) acquire();
            if (opEventProxyInfo != null) {
                opEventProxyInfo.reinit(i, str, str2);
                return opEventProxyInfo;
            }
            return new AppOpsManager.OpEventProxyInfo(i, str, str2);
        }
    }
}
