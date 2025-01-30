package com.android.server.wm;

import android.os.Debug;
import android.os.Handler;
import android.os.Trace;
import android.util.ArraySet;
import android.util.Slog;
import android.view.SurfaceControl;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda1;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class BLASTSyncEngine {
  public final ArrayList mActiveSyncs;
  public int mDeferDepth;
  public final Handler mHandler;
  public int mNextSyncId;
  public final ArrayList mOnIdleListeners;
  public final ArrayList mPendingSyncSets;
  public final ArrayList mTmpFinishQueue;
  public final ArrayList mTmpFringe;
  public final WindowManagerService mWm;

  public class PendingSyncSet {
    public Runnable mApplySync;
    public Runnable mStartSync;

    public PendingSyncSet() {}
  }

  public interface TransactionReadyListener {
    void onTransactionReady(int i, SurfaceControl.Transaction transaction);
  }

  public class SyncGroup {
    public static final ArrayList NO_DEPENDENCIES = new ArrayList();
    public boolean mAborted;
    public ArrayList mDependencies;
    public boolean mIgnoreIndirectMembers;
    public final TransactionReadyListener mListener;
    public final Runnable mOnTimeout;
    public SurfaceControl.Transaction mOrphanTransaction;
    public boolean mReady;
    public final ArraySet mRootMembers;
    public final int mSyncId;
    public int mSyncMethod;
    public String mTraceName;

    public SyncGroup(TransactionReadyListener transactionReadyListener, int i, String str) {
      this.mSyncMethod = 1;
      this.mReady = false;
      this.mRootMembers = new ArraySet();
      this.mOrphanTransaction = null;
      this.mAborted = false;
      this.mIgnoreIndirectMembers = false;
      this.mDependencies = NO_DEPENDENCIES;
      this.mSyncId = i;
      this.mListener = transactionReadyListener;
      if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
        Slog.d(
            StartingSurfaceController.TAG,
            "SyncGroup is created, id=" + i + ", name=" + str + ", caller=" + Debug.getCallers(7));
      }
      this.mOnTimeout =
          new Runnable() { // from class:
                           // com.android.server.wm.BLASTSyncEngine$SyncGroup$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              BLASTSyncEngine.SyncGroup.this.lambda$new$0();
            }
          };
      if (Trace.isTagEnabled(32L)) {
        String str2 = str + "SyncGroupReady";
        this.mTraceName = str2;
        Trace.asyncTraceBegin(32L, str2, i);
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
      Slog.w(StartingSurfaceController.TAG, "Sync group " + this.mSyncId + " timeout");
      WindowManagerGlobalLock windowManagerGlobalLock = BLASTSyncEngine.this.mWm.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          onTimeout();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    public SurfaceControl.Transaction getOrphanTransaction() {
      if (this.mOrphanTransaction == null) {
        this.mOrphanTransaction =
            (SurfaceControl.Transaction) BLASTSyncEngine.this.mWm.mTransactionFactory.get();
      }
      return this.mOrphanTransaction;
    }

    public boolean isIgnoring(WindowContainer windowContainer) {
      return this.mIgnoreIndirectMembers
          && windowContainer.asWindowState() == null
          && windowContainer.mSyncGroup != this;
    }

    public final boolean tryFinish() {
      if (!this.mReady) {
        return false;
      }
      if (ProtoLogCache.WM_DEBUG_SYNC_ENGINE_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_SYNC_ENGINE,
            966569777,
            1,
            (String) null,
            new Object[] {Long.valueOf(this.mSyncId), String.valueOf(this.mRootMembers)});
      }
      if (!this.mDependencies.isEmpty()) {
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
          Slog.d(
              StartingSurfaceController.TAG,
              "SyncGroup " + this.mSyncId + ":  Unfinished dependencies: " + this.mDependencies);
        } else if (ProtoLogCache.WM_DEBUG_SYNC_ENGINE_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_SYNC_ENGINE,
              1820873642,
              1,
              (String) null,
              new Object[] {Long.valueOf(this.mSyncId), String.valueOf(this.mDependencies)});
        }
        return false;
      }
      for (int size = this.mRootMembers.size() - 1; size >= 0; size--) {
        WindowContainer windowContainer = (WindowContainer) this.mRootMembers.valueAt(size);
        if (!windowContainer.isSyncFinished(this)) {
          if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
            Slog.d(
                StartingSurfaceController.TAG,
                "SyncGroup "
                    + this.mSyncId
                    + ":  Unfinished container: "
                    + windowContainer
                    + " mSyncState="
                    + windowContainer.mSyncState);
          } else if (ProtoLogCache.WM_DEBUG_SYNC_ENGINE_enabled) {
            ProtoLogImpl.v(
                ProtoLogGroup.WM_DEBUG_SYNC_ENGINE,
                -230587670,
                1,
                (String) null,
                new Object[] {Long.valueOf(this.mSyncId), String.valueOf(windowContainer)});
          }
          return false;
        }
      }
      finishNow();
      return true;
    }

    public final void finishNow() {
      String str = this.mTraceName;
      if (str != null) {
        Trace.asyncTraceEnd(32L, str, this.mSyncId);
      }
      if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
        if (ProtoLogCache.WM_FORCE_DEBUG_SYNC_ENGINE_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_FORCE_DEBUG_SYNC_ENGINE,
              -333895161,
              1,
              "SyncGroup %d: Finished!",
              new Object[] {Long.valueOf(this.mSyncId)});
        }
      } else if (ProtoLogCache.WM_DEBUG_SYNC_ENGINE_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_SYNC_ENGINE,
            -1905191109,
            1,
            (String) null,
            new Object[] {Long.valueOf(this.mSyncId)});
      }
      SurfaceControl.Transaction transaction =
          (SurfaceControl.Transaction) BLASTSyncEngine.this.mWm.mTransactionFactory.get();
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
      final C1CommitCallback c1CommitCallback = new C1CommitCallback(arraySet, transaction);
      transaction.addTransactionCommittedListener(
          new SystemServerInitThreadPool$$ExternalSyntheticLambda1(),
          new SurfaceControl
              .TransactionCommittedListener() { // from class:
                                                // com.android.server.wm.BLASTSyncEngine$SyncGroup$$ExternalSyntheticLambda1
            @Override // android.view.SurfaceControl.TransactionCommittedListener
            public final void onTransactionCommitted() {
              BLASTSyncEngine.SyncGroup.lambda$finishNow$1(
                  BLASTSyncEngine.SyncGroup.C1CommitCallback.this);
            }
          });
      BLASTSyncEngine.this.mHandler.postDelayed(c1CommitCallback, 5000L);
      Trace.traceBegin(32L, "onTransactionReady");
      this.mListener.onTransactionReady(this.mSyncId, transaction);
      Trace.traceEnd(32L);
      BLASTSyncEngine.this.mActiveSyncs.remove(this);
      BLASTSyncEngine.this.mHandler.removeCallbacks(this.mOnTimeout);
      if (BLASTSyncEngine.this.mActiveSyncs.size() == 0
          && !BLASTSyncEngine.this.mPendingSyncSets.isEmpty()) {
        if (ProtoLogCache.WM_DEBUG_SYNC_ENGINE_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, 1730300180, 0, (String) null, (Object[]) null);
        }
        final PendingSyncSet pendingSyncSet =
            (PendingSyncSet) BLASTSyncEngine.this.mPendingSyncSets.remove(0);
        pendingSyncSet.mStartSync.run();
        if (BLASTSyncEngine.this.mActiveSyncs.size() == 0) {
          throw new IllegalStateException("Pending Sync Set didn't start a sync.");
        }
        BLASTSyncEngine.this.mHandler.post(
            new Runnable() { // from class:
                             // com.android.server.wm.BLASTSyncEngine$SyncGroup$$ExternalSyntheticLambda2
              @Override // java.lang.Runnable
              public final void run() {
                BLASTSyncEngine.SyncGroup.this.lambda$finishNow$2(pendingSyncSet);
              }
            });
      }
      for (int size = BLASTSyncEngine.this.mOnIdleListeners.size() - 1;
          size >= 0 && BLASTSyncEngine.this.mActiveSyncs.size() <= 0;
          size--) {
        ((Runnable) BLASTSyncEngine.this.mOnIdleListeners.get(size)).run();
      }
    }

    /* renamed from: com.android.server.wm.BLASTSyncEngine$SyncGroup$1CommitCallback, reason: invalid class name */
    public class C1CommitCallback implements Runnable {
      public boolean ran = false;
      public final /* synthetic */ SurfaceControl.Transaction val$merged;
      public final /* synthetic */ ArraySet val$wcAwaitingCommit;

      public C1CommitCallback(ArraySet arraySet, SurfaceControl.Transaction transaction) {
        this.val$wcAwaitingCommit = arraySet;
        this.val$merged = transaction;
      }

      public void onCommitted(SurfaceControl.Transaction transaction) {
        WindowManagerGlobalLock windowManagerGlobalLock = BLASTSyncEngine.this.mWm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
          try {
            if (this.ran) {
              WindowManagerService.resetPriorityAfterLockedSection();
              return;
            }
            BLASTSyncEngine.this.mHandler.removeCallbacks(this);
            this.ran = true;
            Iterator it = this.val$wcAwaitingCommit.iterator();
            while (it.hasNext()) {
              ((WindowContainer) it.next()).onSyncTransactionCommitted(transaction);
            }
            transaction.apply();
            this.val$wcAwaitingCommit.clear();
            BLASTSyncEngine.this
                .mWm
                .mAtmService
                .getTransitionController()
                .setHasTopUiIfNeeded(false);
            WindowManagerService.resetPriorityAfterLockedSection();
          } catch (Throwable th) {
            WindowManagerService.resetPriorityAfterLockedSection();
            throw th;
          }
        }
      }

      @Override // java.lang.Runnable
      public void run() {
        Trace.traceBegin(32L, "onTransactionCommitTimeout");
        Slog.e(
            StartingSurfaceController.TAG,
            "WM sent Transaction to organized, but never received commit callback. Application ANR"
                + " likely to follow.");
        Trace.traceEnd(32L);
        WindowManagerGlobalLock windowManagerGlobalLock = BLASTSyncEngine.this.mWm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
          try {
            SurfaceControl.Transaction transaction = this.val$merged;
            if (transaction.mNativeObject == 0) {
              transaction =
                  (SurfaceControl.Transaction) BLASTSyncEngine.this.mWm.mTransactionFactory.get();
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

    public static /* synthetic */ void lambda$finishNow$1(C1CommitCallback c1CommitCallback) {
      c1CommitCallback.onCommitted(new SurfaceControl.Transaction());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishNow$2(PendingSyncSet pendingSyncSet) {
      WindowManagerGlobalLock windowManagerGlobalLock = BLASTSyncEngine.this.mWm.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          pendingSyncSet.mApplySync.run();
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final boolean setReady(boolean z) {
      if (this.mReady == z) {
        return false;
      }
      if (ProtoLogCache.WM_DEBUG_SYNC_ENGINE_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_SYNC_ENGINE,
            -874087484,
            13,
            (String) null,
            new Object[] {Long.valueOf(this.mSyncId), Boolean.valueOf(z)});
      }
      this.mReady = z;
      if (!z) {
        return true;
      }
      BLASTSyncEngine.this.mWm.mWindowPlacerLocked.requestTraversal();
      return true;
    }

    public final void addToSync(WindowContainer windowContainer) {
      if (this.mRootMembers.contains(windowContainer)) {
        return;
      }
      if (ProtoLogCache.WM_DEBUG_SYNC_ENGINE_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_SYNC_ENGINE,
            -1973119651,
            1,
            (String) null,
            new Object[] {Long.valueOf(this.mSyncId), String.valueOf(windowContainer)});
      }
      SyncGroup syncGroup = windowContainer.getSyncGroup();
      if (syncGroup != null && syncGroup != this && !syncGroup.isIgnoring(windowContainer)) {
        Slog.w(
            StartingSurfaceController.TAG,
            "SyncGroup "
                + this.mSyncId
                + " conflicts with "
                + syncGroup.mSyncId
                + ": Making "
                + this.mSyncId
                + " depend on "
                + syncGroup.mSyncId);
        if (!this.mDependencies.contains(syncGroup)) {
          if (syncGroup.dependsOn(this)) {
            Slog.w(
                StartingSurfaceController.TAG,
                " Detected dependency cycle between "
                    + this.mSyncId
                    + " and "
                    + syncGroup.mSyncId
                    + ": Moving "
                    + windowContainer
                    + " to "
                    + this.mSyncId);
            SyncGroup syncGroup2 = windowContainer.mSyncGroup;
            if (syncGroup2 == null) {
              windowContainer.setSyncGroup(this);
            } else {
              syncGroup2.mRootMembers.remove(windowContainer);
              this.mRootMembers.add(windowContainer);
              windowContainer.mSyncGroup = this;
            }
          } else {
            if (this.mDependencies == NO_DEPENDENCIES) {
              this.mDependencies = new ArrayList();
            }
            this.mDependencies.add(syncGroup);
          }
        }
      } else {
        this.mRootMembers.add(windowContainer);
        windowContainer.setSyncGroup(this);
      }
      windowContainer.prepareSync();
      if (this.mReady) {
        BLASTSyncEngine.this.mWm.mWindowPlacerLocked.requestTraversal();
      }
    }

    public final boolean dependsOn(SyncGroup syncGroup) {
      if (this.mDependencies.isEmpty()) {
        return false;
      }
      ArrayList arrayList = BLASTSyncEngine.this.mTmpFringe;
      arrayList.clear();
      arrayList.add(this);
      for (int i = 0; i < arrayList.size(); i++) {
        SyncGroup syncGroup2 = (SyncGroup) arrayList.get(i);
        if (syncGroup2 == syncGroup) {
          arrayList.clear();
          return true;
        }
        for (int i2 = 0; i2 < syncGroup2.mDependencies.size(); i2++) {
          if (!arrayList.contains(syncGroup2.mDependencies.get(i2))) {
            arrayList.add((SyncGroup) syncGroup2.mDependencies.get(i2));
          }
        }
      }
      arrayList.clear();
      return false;
    }

    public void onCancelSync(WindowContainer windowContainer) {
      this.mRootMembers.remove(windowContainer);
    }

    public final void onTimeout() {
      if (BLASTSyncEngine.this.mActiveSyncs.contains(this)) {
        ArrayList arrayList = new ArrayList();
        boolean z = true;
        for (int size = this.mRootMembers.size() - 1; size >= 0; size--) {
          WindowContainer windowContainer = (WindowContainer) this.mRootMembers.valueAt(size);
          if (!windowContainer.isSyncFinished(this)) {
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
              Slog.i(
                  StartingSurfaceController.TAG,
                  "Unfinished container: "
                      + windowContainer
                      + " mSyncState="
                      + windowContainer.mSyncState
                      + (windowContainer.asActivityRecord() != null
                          ? " mPendingRelaunchCount="
                              + windowContainer.asActivityRecord().mPendingRelaunchCount
                          : ""));
              if (windowContainer.getDisplayContent() != null
                  && !arrayList.contains(windowContainer.getDisplayContent())) {
                arrayList.add(windowContainer.getDisplayContent());
              }
            } else {
              Slog.i(StartingSurfaceController.TAG, "Unfinished container: " + windowContainer);
            }
            z = false;
          }
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
          Iterator it = arrayList.iterator();
          while (it.hasNext()) {
            DisplayContent displayContent = (DisplayContent) it.next();
            if (!displayContent.mUnknownAppVisibilityController.allResolved()) {
              Slog.i(
                  StartingSurfaceController.TAG,
                  "Unfinished unknown apps: "
                      + displayContent.mUnknownAppVisibilityController.getDebugMessage());
            }
          }
        }
        int size2 = this.mDependencies.size() - 1;
        while (size2 >= 0) {
          Slog.i(
              StartingSurfaceController.TAG,
              "Unfinished dependency: " + ((SyncGroup) this.mDependencies.get(size2)).mSyncId);
          size2 += -1;
          z = false;
        }
        if (z && !this.mReady) {
          Slog.w(
              StartingSurfaceController.TAG,
              "Sync group "
                  + this.mSyncId
                  + " timed-out because not ready. If you see this, please file a bug.");
        }
        finishNow();
        BLASTSyncEngine.this.removeFromDependencies(this);
      }
    }
  }

  public BLASTSyncEngine(WindowManagerService windowManagerService) {
    this(windowManagerService, windowManagerService.f1749mH);
  }

  public BLASTSyncEngine(WindowManagerService windowManagerService, Handler handler) {
    this.mNextSyncId = 0;
    this.mActiveSyncs = new ArrayList();
    this.mPendingSyncSets = new ArrayList();
    this.mOnIdleListeners = new ArrayList();
    this.mTmpFinishQueue = new ArrayList();
    this.mTmpFringe = new ArrayList();
    this.mDeferDepth = 0;
    this.mWm = windowManagerService;
    this.mHandler = handler;
  }

  public SyncGroup prepareSyncSet(TransactionReadyListener transactionReadyListener, String str) {
    int i = this.mNextSyncId;
    this.mNextSyncId = i + 1;
    return new SyncGroup(transactionReadyListener, i, str);
  }

  public int startSyncSet(
      TransactionReadyListener transactionReadyListener, long j, String str, boolean z) {
    SyncGroup prepareSyncSet = prepareSyncSet(transactionReadyListener, str);
    startSyncSet(prepareSyncSet, j, z);
    return prepareSyncSet.mSyncId;
  }

  public void startSyncSet(SyncGroup syncGroup) {
    startSyncSet(syncGroup, 5000L, false);
  }

  public void startSyncSet(SyncGroup syncGroup, long j, boolean z) {
    boolean z2 = this.mActiveSyncs.size() > 0;
    if (!z && z2) {
      Slog.e(
          StartingSurfaceController.TAG,
          "SyncGroup " + syncGroup.mSyncId + ": Started when there is other active SyncGroup");
    }
    this.mActiveSyncs.add(syncGroup);
    syncGroup.mIgnoreIndirectMembers = z;
    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
      if (ProtoLogCache.WM_DEBUG_SYNC_ENGINE_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_SYNC_ENGINE,
            558153532,
            1,
            (String) null,
            new Object[] {
              Long.valueOf(syncGroup.mSyncId),
              String.valueOf(syncGroup.mListener),
              String.valueOf(Debug.getCallers(8))
            });
      }
    } else if (ProtoLogCache.WM_DEBUG_SYNC_ENGINE_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_SYNC_ENGINE,
          -1828118576,
          1,
          (String) null,
          new Object[] {
            Long.valueOf(syncGroup.mSyncId),
            (z && z2) ? "(in parallel) " : "",
            String.valueOf(syncGroup.mListener)
          });
    }
    scheduleTimeout(syncGroup, j);
  }

  public SyncGroup getSyncSet(int i) {
    for (int i2 = 0; i2 < this.mActiveSyncs.size(); i2++) {
      if (((SyncGroup) this.mActiveSyncs.get(i2)).mSyncId == i) {
        return (SyncGroup) this.mActiveSyncs.get(i2);
      }
    }
    return null;
  }

  public boolean hasActiveSync() {
    return this.mActiveSyncs.size() != 0;
  }

  public void scheduleTimeout(SyncGroup syncGroup, long j) {
    this.mHandler.postDelayed(syncGroup.mOnTimeout, j);
  }

  public void addToSyncSet(int i, WindowContainer windowContainer) {
    getSyncGroup(i).addToSync(windowContainer);
  }

  public void setSyncMethod(int i, int i2) {
    SyncGroup syncGroup = getSyncGroup(i);
    if (!syncGroup.mRootMembers.isEmpty()) {
      throw new IllegalStateException(
          "Not allow to change sync method after adding group member, id=" + i);
    }
    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
      Slog.w(
          StartingSurfaceController.TAG,
          "setSyncMethod, id=" + i + ", method=" + i2 + ", caller=" + Debug.getCallers(3));
    }
    syncGroup.mSyncMethod = i2;
  }

  public boolean setReady(int i, boolean z) {
    return getSyncGroup(i).setReady(z);
  }

  public void setReady(int i) {
    setReady(i, true);
  }

  public void abort(int i) {
    SyncGroup syncGroup = getSyncGroup(i);
    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && this.mDeferDepth > 0) {
      Slog.d(StartingSurfaceController.TAG, "Deferring abort, id=" + i);
      syncGroup.mAborted = true;
      return;
    }
    syncGroup.finishNow();
    removeFromDependencies(syncGroup);
  }

  public final SyncGroup getSyncGroup(int i) {
    SyncGroup syncSet = getSyncSet(i);
    if (syncSet != null) {
      return syncSet;
    }
    throw new IllegalStateException("SyncGroup is not started yet id=" + i);
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

  public void onSurfacePlacement() {
    if (this.mActiveSyncs.isEmpty()) {
      return;
    }
    if ((CoreRune.MW_SHELL_TRANSITION_BUG_FIX || CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX)
        && this.mDeferDepth > 0) {
      Slog.d(
          StartingSurfaceController.TAG,
          "Deferring tryFinish, activeSyncs="
              + Arrays.toString(
                  this.mActiveSyncs.stream()
                      .map(
                          new Function() { // from class:
                                           // com.android.server.wm.BLASTSyncEngine$$ExternalSyntheticLambda0
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                              Integer lambda$onSurfacePlacement$0;
                              lambda$onSurfacePlacement$0 =
                                  BLASTSyncEngine.lambda$onSurfacePlacement$0(
                                      (BLASTSyncEngine.SyncGroup) obj);
                              return lambda$onSurfacePlacement$0;
                            }
                          })
                      .toArray()));
      return;
    }
    this.mTmpFinishQueue.addAll(this.mActiveSyncs);
    int size = ((this.mActiveSyncs.size() + 1) * this.mActiveSyncs.size()) / 2;
    while (!this.mTmpFinishQueue.isEmpty()) {
      if (size <= 0) {
        Slog.e(
            StartingSurfaceController.TAG,
            "Trying to finish more syncs than theoretically possible. This should never happen."
                + " Most likely a dependency cycle wasn't detected.");
      }
      size--;
      SyncGroup syncGroup = (SyncGroup) this.mTmpFinishQueue.remove(0);
      int indexOf = this.mActiveSyncs.indexOf(syncGroup);
      if (indexOf >= 0) {
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && syncGroup.mAborted) {
          syncGroup.finishNow();
        } else if (syncGroup.tryFinish()) {
          int i = 0;
          for (int i2 = 0; i2 < this.mActiveSyncs.size(); i2++) {
            SyncGroup syncGroup2 = (SyncGroup) this.mActiveSyncs.get(i2);
            if (syncGroup2.mDependencies.remove(syncGroup)
                && i2 < indexOf
                && syncGroup2.mDependencies.isEmpty()) {
              this.mTmpFinishQueue.add(i, (SyncGroup) this.mActiveSyncs.get(i2));
              i++;
            }
          }
        }
      }
    }
  }

  public static /* synthetic */ Integer lambda$onSurfacePlacement$0(SyncGroup syncGroup) {
    return Integer.valueOf(syncGroup.mSyncId);
  }

  public void queueSyncSet(Runnable runnable, Runnable runnable2) {
    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
      Slog.d(StartingSurfaceController.TAG, "queueSyncSet, caller=" + Debug.getCallers(6));
    }
    PendingSyncSet pendingSyncSet = new PendingSyncSet();
    pendingSyncSet.mStartSync = runnable;
    pendingSyncSet.mApplySync = runnable2;
    this.mPendingSyncSets.add(pendingSyncSet);
  }

  public boolean hasPendingSyncSets() {
    return !this.mPendingSyncSets.isEmpty();
  }

  public void addOnIdleListener(Runnable runnable) {
    this.mOnIdleListeners.add(runnable);
  }

  public void pause() {
    this.mDeferDepth++;
  }

  public void resume() {
    this.mDeferDepth--;
  }
}
