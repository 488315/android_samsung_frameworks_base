package com.android.server.wm;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Debug;
import android.os.Handler;
import android.os.Trace;
import android.util.ArraySet;
import android.util.Slog;
import android.view.SurfaceControl;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.rune.CoreRune;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class BLASTSyncEngine {
    public final Handler mHandler;
    public final WindowManagerService mWm;
    public int mNextSyncId = 0;
    public final ArrayList mActiveSyncs = new ArrayList();
    public final ArrayList mPendingSyncSets = new ArrayList();
    public final ArrayList mOnIdleListeners = new ArrayList();
    public final ArrayList mTmpFinishQueue = new ArrayList();
    public final ArrayList mTmpFringe = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PendingSyncSet {
        public Runnable mApplySync;
        public Runnable mStartSync;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SyncGroup {
        public static final ArrayList NO_DEPENDENCIES = new ArrayList();
        public final TransactionReadyListener mListener;
        public final BLASTSyncEngine$SyncGroup$$ExternalSyntheticLambda2 mOnTimeout;
        public final int mSyncId;
        public final String mSyncName;
        public final String mTraceName;
        public int mSyncMethod = 1;
        public boolean mReady = false;
        public final ArraySet mRootMembers = new ArraySet();
        public SurfaceControl.Transaction mOrphanTransaction = null;
        public boolean mIgnoreIndirectMembers = false;
        public ArrayList mDependencies = NO_DEPENDENCIES;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.wm.BLASTSyncEngine$SyncGroup$1CommitCallback, reason: invalid class name */
        public final class C1CommitCallback implements Runnable {
            public boolean ran = false;
            public final /* synthetic */ SurfaceControl.Transaction val$merged;
            public final /* synthetic */ long val$mergedTxId;
            public final /* synthetic */ int val$syncId;
            public final /* synthetic */ String val$syncName;
            public final /* synthetic */ ArraySet val$wcAwaitingCommit;

            public C1CommitCallback(
                    ArraySet arraySet,
                    int i,
                    String str,
                    long j,
                    SurfaceControl.Transaction transaction) {
                this.val$wcAwaitingCommit = arraySet;
                this.val$syncId = i;
                this.val$syncName = str;
                this.val$mergedTxId = j;
                this.val$merged = transaction;
            }

            public final void onCommitted(SurfaceControl.Transaction transaction) {
                BLASTSyncEngine.this.mHandler.removeCallbacks(this);
                WindowManagerGlobalLock windowManagerGlobalLock =
                        BLASTSyncEngine.this.mWm.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        if (this.ran) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        this.ran = true;
                        Iterator it = this.val$wcAwaitingCommit.iterator();
                        while (it.hasNext()) {
                            ((WindowContainer) it.next()).onSyncTransactionCommitted(transaction);
                        }
                        transaction.apply();
                        this.val$wcAwaitingCommit.clear();
                        WindowManagerService.resetPriorityAfterLockedSection();
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
            }

            @Override // java.lang.Runnable
            public final void run() {
                Trace.traceBegin(32L, "onTransactionCommitTimeout");
                Slog.e(
                        "BLASTSyncEngine",
                        "WM sent Transaction (#"
                                + this.val$syncId
                                + ", "
                                + this.val$syncName
                                + ", tx="
                                + this.val$mergedTxId
                                + ") to organizer, but never received commit callback. Application"
                                + " ANR likely to follow.");
                Trace.traceEnd(32L);
                WindowManagerGlobalLock windowManagerGlobalLock =
                        BLASTSyncEngine.this.mWm.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        SyncGroup.this.mListener.onTransactionCommitTimeout();
                        SurfaceControl.Transaction transaction = this.val$merged;
                        if (transaction.mNativeObject == 0) {
                            transaction =
                                    (SurfaceControl.Transaction)
                                            BLASTSyncEngine.this.mWm.mTransactionFactory.get();
                        }
                        onCommitted(transaction);
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }

        /* JADX WARN: Type inference failed for: r2v5, types: [com.android.server.wm.BLASTSyncEngine$SyncGroup$$ExternalSyntheticLambda2] */
        public SyncGroup(TransactionReadyListener transactionReadyListener, int i, String str) {
            this.mSyncId = i;
            this.mSyncName = str;
            this.mListener = transactionReadyListener;
            if (CoreRune.FW_SHELL_TRANSITION_LOG) {
                ActivityManagerService$$ExternalSyntheticOutline0.m(
                        7,
                        DirEncryptService$$ExternalSyntheticOutline0.m(
                                i, "SyncGroup is created, id=", ", name=", str, ", caller="),
                        "BLASTSyncEngine");
            }
            this.mOnTimeout =
                    new Runnable() { // from class:
                                     // com.android.server.wm.BLASTSyncEngine$SyncGroup$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            BLASTSyncEngine.SyncGroup syncGroup = BLASTSyncEngine.SyncGroup.this;
                            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("Sync group "),
                                    syncGroup.mSyncId,
                                    " timeout",
                                    "BLASTSyncEngine");
                            WindowManagerGlobalLock windowManagerGlobalLock =
                                    BLASTSyncEngine.this.mWm.mGlobalLock;
                            WindowManagerService.boostPriorityForLockedSection();
                            synchronized (windowManagerGlobalLock) {
                                try {
                                    syncGroup.onTimeout();
                                } catch (Throwable th) {
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                    throw th;
                                }
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    };
            if (Trace.isTagEnabled(32L)) {
                String m$1 =
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                str, "SyncGroupReady");
                this.mTraceName = m$1;
                Trace.asyncTraceBegin(32L, m$1, i);
            }
        }

        public final void finishNow() {
            String str = this.mTraceName;
            int i = this.mSyncId;
            if (str != null) {
                Trace.asyncTraceEnd(32L, str, i);
            }
            boolean z = CoreRune.FW_SHELL_TRANSITION_LOG;
            boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_SYNC_ENGINE_enabled;
            if (z) {
                if (ProtoLogImpl_54989576.Cache.WM_FORCE_DEBUG_SYNC_ENGINE_enabled[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_FORCE_DEBUG_SYNC_ENGINE,
                            -8466836686903624078L,
                            1,
                            "SyncGroup %d: Finished!",
                            Long.valueOf(i));
                }
            } else if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_SYNC_ENGINE,
                        6649777898123506907L,
                        1,
                        null,
                        Long.valueOf(i));
            }
            BLASTSyncEngine bLASTSyncEngine = BLASTSyncEngine.this;
            SurfaceControl.Transaction transaction =
                    (SurfaceControl.Transaction) bLASTSyncEngine.mWm.mTransactionFactory.get();
            SurfaceControl.Transaction transaction2 = this.mOrphanTransaction;
            if (transaction2 != null) {
                transaction.merge(transaction2);
            }
            Iterator it = this.mRootMembers.iterator();
            while (it.hasNext()) {
                ((WindowContainer) it.next()).finishSync(transaction, this, false);
            }
            ArraySet arraySet = new ArraySet();
            Iterator it2 = this.mRootMembers.iterator();
            while (it2.hasNext()) {
                ((WindowContainer) it2.next()).waitForSyncTransactionCommit(arraySet);
            }
            final C1CommitCallback c1CommitCallback =
                    new C1CommitCallback(
                            arraySet,
                            this.mSyncId,
                            this.mSyncName,
                            transaction.getId(),
                            transaction);
            transaction.addTransactionCommittedListener(
                    new SystemServerInitThreadPool$$ExternalSyntheticLambda0(),
                    new SurfaceControl
                            .TransactionCommittedListener() { // from class:
                                                              // com.android.server.wm.BLASTSyncEngine$SyncGroup$$ExternalSyntheticLambda0
                        @Override // android.view.SurfaceControl.TransactionCommittedListener
                        public final void onTransactionCommitted() {
                            BLASTSyncEngine.SyncGroup.C1CommitCallback.this.onCommitted(
                                    new SurfaceControl.Transaction());
                        }
                    });
            Handler handler = bLASTSyncEngine.mHandler;
            handler.postDelayed(c1CommitCallback, 5000L);
            Trace.traceBegin(32L, "onTransactionReady");
            this.mListener.onTransactionReady(transaction, i);
            Trace.traceEnd(32L);
            bLASTSyncEngine.mActiveSyncs.remove(this);
            handler.removeCallbacks(this.mOnTimeout);
            if (bLASTSyncEngine.mActiveSyncs.size() == 0
                    && !bLASTSyncEngine.mPendingSyncSets.isEmpty()) {
                if (zArr[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_SYNC_ENGINE,
                            4174320302463990554L,
                            0,
                            null,
                            null);
                }
                final PendingSyncSet pendingSyncSet =
                        (PendingSyncSet) bLASTSyncEngine.mPendingSyncSets.remove(0);
                pendingSyncSet.mStartSync.run();
                if (bLASTSyncEngine.mActiveSyncs.size() == 0) {
                    throw new IllegalStateException("Pending Sync Set didn't start a sync.");
                }
                handler.post(
                        new Runnable() { // from class:
                                         // com.android.server.wm.BLASTSyncEngine$SyncGroup$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                BLASTSyncEngine.SyncGroup syncGroup =
                                        BLASTSyncEngine.SyncGroup.this;
                                BLASTSyncEngine.PendingSyncSet pendingSyncSet2 = pendingSyncSet;
                                WindowManagerGlobalLock windowManagerGlobalLock =
                                        BLASTSyncEngine.this.mWm.mGlobalLock;
                                WindowManagerService.boostPriorityForLockedSection();
                                synchronized (windowManagerGlobalLock) {
                                    try {
                                        pendingSyncSet2.mApplySync.run();
                                    } catch (Throwable th) {
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                        throw th;
                                    }
                                }
                                WindowManagerService.resetPriorityAfterLockedSection();
                            }
                        });
            }
            for (int size = bLASTSyncEngine.mOnIdleListeners.size() - 1;
                    size >= 0 && bLASTSyncEngine.mActiveSyncs.size() <= 0;
                    size--) {
                ((Runnable) bLASTSyncEngine.mOnIdleListeners.get(size)).run();
            }
        }

        public final void onTimeout() {
            BLASTSyncEngine bLASTSyncEngine = BLASTSyncEngine.this;
            if (bLASTSyncEngine.mActiveSyncs.contains(this)) {
                boolean z = true;
                for (int size = this.mRootMembers.size() - 1; size >= 0; size--) {
                    WindowContainer windowContainer =
                            (WindowContainer) this.mRootMembers.valueAt(size);
                    if (!windowContainer.isSyncFinished(this)) {
                        Slog.i("BLASTSyncEngine", "Unfinished container: " + windowContainer);
                        windowContainer.forAllActivities(
                                new BLASTSyncEngine$SyncGroup$$ExternalSyntheticLambda3(0));
                        z = false;
                    }
                }
                int size2 = this.mDependencies.size() - 1;
                while (size2 >= 0) {
                    Slog.i(
                            "BLASTSyncEngine",
                            "Unfinished dependency: "
                                    + ((SyncGroup) this.mDependencies.get(size2)).mSyncId);
                    size2 += -1;
                    z = false;
                }
                if (z && !this.mReady) {
                    UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Sync group "),
                            this.mSyncId,
                            " timed-out because not ready. If you see this, please file a bug.",
                            "BLASTSyncEngine");
                    this.mListener.onReadyTimeout();
                }
                finishNow();
                bLASTSyncEngine.removeFromDependencies(this);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface TransactionReadyListener {
        default void onReadyTimeout() {}

        default void onTransactionCommitTimeout() {}

        void onTransactionReady(SurfaceControl.Transaction transaction, int i);
    }

    public BLASTSyncEngine(WindowManagerService windowManagerService, Handler handler) {
        this.mWm = windowManagerService;
        this.mHandler = handler;
    }

    public final void addToSyncSet(int i, WindowContainer windowContainer) {
        SyncGroup syncGroup = getSyncGroup(i);
        if (syncGroup.mRootMembers.contains(windowContainer)) {
            return;
        }
        boolean z = ProtoLogImpl_54989576.Cache.WM_DEBUG_SYNC_ENGINE_enabled[1];
        int i2 = syncGroup.mSyncId;
        if (z) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_SYNC_ENGINE,
                    -476337038362199951L,
                    1,
                    null,
                    Long.valueOf(i2),
                    String.valueOf(windowContainer));
        }
        SyncGroup syncGroup2 = windowContainer.getSyncGroup();
        if (syncGroup2 == null
                || syncGroup2 == syncGroup
                || (syncGroup2.mIgnoreIndirectMembers
                        && windowContainer.asWindowState() == null
                        && windowContainer.mSyncGroup != syncGroup2)) {
            syncGroup.mRootMembers.add(windowContainer);
            windowContainer.setSyncGroup(syncGroup);
        } else {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i2, "SyncGroup ", " conflicts with ");
            int i3 = syncGroup2.mSyncId;
            ServiceKeeper$$ExternalSyntheticOutline0.m(i3, i2, ": Making ", " depend on ", m);
            HeapdumpWatcher$$ExternalSyntheticOutline0.m(m, i3, "BLASTSyncEngine");
            if (!syncGroup.mDependencies.contains(syncGroup2)) {
                if (!syncGroup2.mDependencies.isEmpty()) {
                    ArrayList arrayList = BLASTSyncEngine.this.mTmpFringe;
                    arrayList.clear();
                    arrayList.add(syncGroup2);
                    for (int i4 = 0; i4 < arrayList.size(); i4++) {
                        SyncGroup syncGroup3 = (SyncGroup) arrayList.get(i4);
                        if (syncGroup3 == syncGroup) {
                            arrayList.clear();
                            Slog.w(
                                    "BLASTSyncEngine",
                                    " Detected dependency cycle between "
                                            + i2
                                            + " and "
                                            + i3
                                            + ": Moving "
                                            + windowContainer
                                            + " to "
                                            + i2);
                            SyncGroup syncGroup4 = windowContainer.mSyncGroup;
                            if (syncGroup4 == null) {
                                windowContainer.setSyncGroup(syncGroup);
                            } else {
                                syncGroup4.mRootMembers.remove(windowContainer);
                                syncGroup.mRootMembers.add(windowContainer);
                                windowContainer.mSyncGroup = syncGroup;
                            }
                        } else {
                            for (int i5 = 0; i5 < syncGroup3.mDependencies.size(); i5++) {
                                if (!arrayList.contains(syncGroup3.mDependencies.get(i5))) {
                                    arrayList.add((SyncGroup) syncGroup3.mDependencies.get(i5));
                                }
                            }
                        }
                    }
                    arrayList.clear();
                }
                if (syncGroup.mDependencies == SyncGroup.NO_DEPENDENCIES) {
                    syncGroup.mDependencies = new ArrayList();
                }
                syncGroup.mDependencies.add(syncGroup2);
            }
        }
        windowContainer.prepareSync();
        if (windowContainer.mSyncState == 0 && windowContainer.mSyncGroup != null) {
            Slog.w(
                    "BLASTSyncEngine",
                    "addToSync: unset SyncGroup "
                            + windowContainer.mSyncGroup.mSyncId
                            + " for non-sync "
                            + windowContainer);
            windowContainer.mSyncGroup = null;
        }
        if (syncGroup.mReady) {
            BLASTSyncEngine.this.mWm.mWindowPlacerLocked.requestTraversal();
        }
    }

    public final SyncGroup getSyncGroup(int i) {
        SyncGroup syncSet = getSyncSet(i);
        if (syncSet != null) {
            return syncSet;
        }
        throw new IllegalStateException(
                VibrationParam$1$$ExternalSyntheticOutline0.m(
                        i, "SyncGroup is not started yet id="));
    }

    public final SyncGroup getSyncSet(int i) {
        for (int i2 = 0; i2 < this.mActiveSyncs.size(); i2++) {
            if (((SyncGroup) this.mActiveSyncs.get(i2)).mSyncId == i) {
                return (SyncGroup) this.mActiveSyncs.get(i2);
            }
        }
        return null;
    }

    public final boolean hasActiveSync() {
        return this.mActiveSyncs.size() != 0;
    }

    public final void onSurfacePlacement() {
        if (this.mActiveSyncs.isEmpty()) {
            return;
        }
        this.mTmpFinishQueue.addAll(this.mActiveSyncs);
        int size = (this.mActiveSyncs.size() * (this.mActiveSyncs.size() + 1)) / 2;
        while (!this.mTmpFinishQueue.isEmpty()) {
            if (size <= 0) {
                Slog.e(
                        "BLASTSyncEngine",
                        "Trying to finish more syncs than theoretically possible. This should never"
                            + " happen. Most likely a dependency cycle wasn't detected.");
            }
            size--;
            SyncGroup syncGroup = (SyncGroup) this.mTmpFinishQueue.remove(0);
            int indexOf = this.mActiveSyncs.indexOf(syncGroup);
            if (indexOf >= 0 && syncGroup.mReady) {
                boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_SYNC_ENGINE_enabled;
                boolean z = zArr[1];
                int i = syncGroup.mSyncId;
                if (z) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_SYNC_ENGINE,
                            495867940519492701L,
                            1,
                            null,
                            Long.valueOf(i),
                            String.valueOf(syncGroup.mRootMembers));
                }
                if (syncGroup.mDependencies.isEmpty()) {
                    int size2 = syncGroup.mRootMembers.size() - 1;
                    while (true) {
                        if (size2 >= 0) {
                            WindowContainer windowContainer =
                                    (WindowContainer) syncGroup.mRootMembers.valueAt(size2);
                            if (windowContainer.isSyncFinished(syncGroup)) {
                                size2--;
                            } else if (CoreRune.FW_SHELL_TRANSITION_LOG) {
                                StringBuilder sb = new StringBuilder("SyncGroup ");
                                sb.append(i);
                                sb.append(":  Unfinished container: ");
                                sb.append(windowContainer);
                                sb.append(" mSyncState=");
                                DeviceIdleController$$ExternalSyntheticOutline0.m(
                                        sb, windowContainer.mSyncState, "BLASTSyncEngine");
                            } else if (zArr[1]) {
                                ProtoLogImpl_54989576.v(
                                        ProtoLogGroup.WM_DEBUG_SYNC_ENGINE,
                                        616739530932040800L,
                                        1,
                                        null,
                                        Long.valueOf(i),
                                        String.valueOf(windowContainer));
                            }
                        } else {
                            syncGroup.finishNow();
                            int i2 = 0;
                            for (int i3 = 0; i3 < this.mActiveSyncs.size(); i3++) {
                                SyncGroup syncGroup2 = (SyncGroup) this.mActiveSyncs.get(i3);
                                if (syncGroup2.mDependencies.remove(syncGroup)
                                        && i3 < indexOf
                                        && syncGroup2.mDependencies.isEmpty()) {
                                    this.mTmpFinishQueue.add(
                                            i2, (SyncGroup) this.mActiveSyncs.get(i3));
                                    i2++;
                                }
                            }
                        }
                    }
                } else if (CoreRune.FW_SHELL_TRANSITION_LOG) {
                    StringBuilder m =
                            BatteryService$$ExternalSyntheticOutline0.m(
                                    i, "SyncGroup ", ":  Unfinished dependencies: ");
                    m.append(syncGroup.mDependencies);
                    Slog.d("BLASTSyncEngine", m.toString());
                } else if (zArr[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_SYNC_ENGINE,
                            8452501904614439940L,
                            1,
                            null,
                            Long.valueOf(i),
                            String.valueOf(syncGroup.mDependencies));
                }
            }
        }
    }

    public final void removeFromDependencies(SyncGroup syncGroup) {
        boolean z = false;
        for (int i = 0; i < this.mActiveSyncs.size(); i++) {
            SyncGroup syncGroup2 = (SyncGroup) this.mActiveSyncs.get(i);
            if (syncGroup2.mDependencies.remove(syncGroup) && syncGroup2.mDependencies.isEmpty()) {
                z = true;
            }
        }
        if (z) {
            this.mWm.mWindowPlacerLocked.requestTraversal();
        }
    }

    public void scheduleTimeout(SyncGroup syncGroup, long j) {
        this.mHandler.postDelayed(syncGroup.mOnTimeout, j);
    }

    public final boolean setReady(int i, boolean z) {
        SyncGroup syncGroup = getSyncGroup(i);
        if (syncGroup.mReady == z) {
            return false;
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_SYNC_ENGINE_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_SYNC_ENGINE,
                    6310906192788668020L,
                    13,
                    null,
                    Long.valueOf(syncGroup.mSyncId),
                    Boolean.valueOf(z));
        }
        syncGroup.mReady = z;
        if (z) {
            BLASTSyncEngine.this.mWm.mWindowPlacerLocked.requestTraversal();
        }
        return true;
    }

    public final void setSyncMethod(int i, int i2) {
        SyncGroup syncGroup = getSyncGroup(i);
        if (!syncGroup.mRootMembers.isEmpty()) {
            throw new IllegalStateException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            i, "Not allow to change sync method after adding group member, id="));
        }
        if (CoreRune.FW_SHELL_TRANSITION_LOG) {
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i, i2, "setSyncMethod, id=", ", method=", ", caller=");
            m.append(Debug.getCallers(3));
            Slog.w("BLASTSyncEngine", m.toString());
        }
        syncGroup.mSyncMethod = i2;
    }

    public final void startSyncSet(SyncGroup syncGroup, long j, boolean z) {
        boolean z2 = this.mActiveSyncs.size() > 0;
        if (!z && z2) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    new StringBuilder("SyncGroup "),
                    syncGroup.mSyncId,
                    ": Started when there is other active SyncGroup",
                    "BLASTSyncEngine");
        }
        this.mActiveSyncs.add(syncGroup);
        syncGroup.mIgnoreIndirectMembers = z;
        boolean z3 = CoreRune.FW_SHELL_TRANSITION_LOG;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_SYNC_ENGINE_enabled;
        TransactionReadyListener transactionReadyListener = syncGroup.mListener;
        int i = syncGroup.mSyncId;
        if (z3) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_SYNC_ENGINE,
                        -7770445535786616414L,
                        1,
                        null,
                        Long.valueOf(i),
                        String.valueOf(transactionReadyListener),
                        String.valueOf(Debug.getCallers(8)));
            }
        } else if (zArr[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_SYNC_ENGINE,
                    -2978812352001196863L,
                    1,
                    null,
                    Long.valueOf(i),
                    (z && z2) ? "(in parallel) " : "",
                    String.valueOf(transactionReadyListener));
        }
        scheduleTimeout(syncGroup, j);
    }
}
