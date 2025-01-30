package com.android.server.wm;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.WindowConfiguration;
import android.content.ActivityNotFoundException;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.AndroidRuntimeException;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.view.RemoteAnimationAdapter;
import android.view.SurfaceControl;
import android.window.IDisplayAreaOrganizerController;
import android.window.ITaskFragmentOrganizer;
import android.window.ITaskFragmentOrganizerController;
import android.window.ITaskOrganizerController;
import android.window.ITransitionMetricsReporter;
import android.window.ITransitionPlayer;
import android.window.IWindowContainerTransactionCallback;
import android.window.IWindowOrganizerController;
import android.window.TaskFragmentAnimationParams;
import android.window.TaskFragmentCreationParams;
import android.window.TaskFragmentOperation;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.ArrayUtils;
import com.android.server.LocalServices;
import com.android.server.pm.LauncherAppsService;
import com.android.server.wm.BLASTSyncEngine;
import com.android.server.wm.TransitionController;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class WindowOrganizerController extends IWindowOrganizerController.Stub implements BLASTSyncEngine.TransactionReadyListener {
    public final DisplayAreaOrganizerController mDisplayAreaOrganizerController;
    public final WindowManagerGlobalLock mGlobalLock;
    public final ActivityTaskManagerService mService;
    public final TaskFragmentOrganizerController mTaskFragmentOrganizerController;
    public final TaskOrganizerController mTaskOrganizerController;
    public final TransitionController mTransitionController;
    public final HashMap mTransactionCallbacksByPendingSyncId = new HashMap();
    final ArrayMap mLaunchTaskFragments = new ArrayMap();
    public HashSet mEnterSplitWithSingleStage = new HashSet();
    public final Rect mTmpBounds0 = new Rect();
    public final Rect mTmpBounds1 = new Rect();
    public long mLastAppliedTransactionSeq = -1;

    public class CallerInfo {
        public final int mPid = Binder.getCallingPid();
        public final int mUid = Binder.getCallingUid();
    }

    public WindowOrganizerController(ActivityTaskManagerService activityTaskManagerService) {
        this.mService = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
        this.mTaskOrganizerController = new TaskOrganizerController(activityTaskManagerService);
        this.mDisplayAreaOrganizerController = new DisplayAreaOrganizerController(activityTaskManagerService);
        this.mTaskFragmentOrganizerController = new TaskFragmentOrganizerController(activityTaskManagerService, this);
        this.mTransitionController = new TransitionController(activityTaskManagerService, new Runnable() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                WindowOrganizerController.this.lambda$new$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.mLastAppliedTransactionSeq = -1L;
    }

    public TransitionController getTransitionController() {
        return this.mTransitionController;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (RuntimeException e) {
            throw ActivityTaskManagerService.logAndRethrowRuntimeExceptionOnTransact("WindowOrganizerController", e);
        }
    }

    public void applyTransaction(WindowContainerTransaction windowContainerTransaction) {
        if (windowContainerTransaction == null) {
            throw new IllegalArgumentException("Null transaction passed to applyTransaction");
        }
        ActivityTaskManagerService.enforceTaskPermission("applyTransaction()");
        CallerInfo callerInfo = new CallerInfo();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    applyTransaction(windowContainerTransaction, -1, null, callerInfo);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int applySyncTransaction(final WindowContainerTransaction windowContainerTransaction, IWindowContainerTransactionCallback iWindowContainerTransactionCallback) {
        ActivityRecord activityRecord;
        if (windowContainerTransaction == null) {
            throw new IllegalArgumentException("Null transaction passed to applySyncTransaction");
        }
        ActivityTaskManagerService.enforceTaskPermission("applySyncTransaction()");
        final CallerInfo callerInfo = new CallerInfo();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityTaskManagerService activityTaskManagerService = this.mService;
                    ActivityManagerPerformance activityManagerPerformance = activityTaskManagerService.mAMBooster;
                    if (activityManagerPerformance != null && (activityRecord = activityTaskManagerService.mLastResumedActivity) != null) {
                        activityManagerPerformance.onActivityRelaunchLocked(activityRecord, true);
                    }
                    if (iWindowContainerTransactionCallback == null) {
                        applyTransaction(windowContainerTransaction, -1, null, callerInfo);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return -1;
                    }
                    final BLASTSyncEngine.SyncGroup prepareSyncWithOrganizer = prepareSyncWithOrganizer(iWindowContainerTransactionCallback);
                    final int i = prepareSyncWithOrganizer.mSyncId;
                    if (this.mTransitionController.isShellTransitionsEnabled()) {
                        this.mTransitionController.startLegacySyncOrQueue(prepareSyncWithOrganizer, new Consumer() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                WindowOrganizerController.this.lambda$applySyncTransaction$1(windowContainerTransaction, i, callerInfo, (Boolean) obj);
                            }
                        });
                    } else if (!this.mService.mWindowManager.mSyncEngine.hasActiveSync()) {
                        this.mService.mWindowManager.mSyncEngine.startSyncSet(prepareSyncWithOrganizer);
                        applyTransaction(windowContainerTransaction, i, null, callerInfo);
                        setSyncReady(i);
                    } else {
                        this.mService.mWindowManager.mSyncEngine.queueSyncSet(new Runnable() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                WindowOrganizerController.this.lambda$applySyncTransaction$2(prepareSyncWithOrganizer);
                            }
                        }, new Runnable() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                WindowOrganizerController.this.lambda$applySyncTransaction$3(windowContainerTransaction, i, callerInfo);
                            }
                        });
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return i;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applySyncTransaction$1(WindowContainerTransaction windowContainerTransaction, int i, CallerInfo callerInfo, Boolean bool) {
        applyTransaction(windowContainerTransaction, i, (Transition) null, callerInfo, bool.booleanValue());
        setSyncReady(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applySyncTransaction$2(BLASTSyncEngine.SyncGroup syncGroup) {
        this.mService.mWindowManager.mSyncEngine.startSyncSet(syncGroup);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applySyncTransaction$3(WindowContainerTransaction windowContainerTransaction, int i, CallerInfo callerInfo) {
        applyTransaction(windowContainerTransaction, i, null, callerInfo);
        setSyncReady(i);
    }

    public IBinder startNewTransition(int i, WindowContainerTransaction windowContainerTransaction) {
        return startTransition(i, null, windowContainerTransaction);
    }

    public void startTransition(IBinder iBinder, WindowContainerTransaction windowContainerTransaction) {
        startTransition(-1, iBinder, windowContainerTransaction);
    }

    /* JADX WARN: Finally extract failed */
    public final IBinder startTransition(int i, IBinder iBinder, WindowContainerTransaction windowContainerTransaction) {
        IBinder token;
        ActivityTaskManagerService.enforceTaskPermission("startTransition()");
        final CallerInfo callerInfo = new CallerInfo();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Transition fromBinder = Transition.fromBinder(iBinder);
                    if (this.mTransitionController.getTransitionPlayer() == null && fromBinder == null) {
                        Slog.w("WindowOrganizerController", "Using shell transitions API for legacy transitions.");
                        if (windowContainerTransaction == null) {
                            throw new IllegalArgumentException("Can't use legacy transitions in compatibility mode with no WCT.");
                        }
                        applyTransaction(windowContainerTransaction, -1, null, callerInfo);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    final WindowContainerTransaction windowContainerTransaction2 = windowContainerTransaction != null ? windowContainerTransaction : new WindowContainerTransaction();
                    if (fromBinder == null) {
                        if (i < 0) {
                            throw new IllegalArgumentException("Can't create transition with no type");
                        }
                        final boolean z = windowContainerTransaction != null;
                        final Transition transition = new Transition(i, 0, this.mTransitionController, this.mService.mWindowManager.mSyncEngine);
                        transition.calcParallelCollectType(windowContainerTransaction2);
                        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
                            this.mService.mWindowManager.mSyncEngine.pause();
                        }
                        try {
                            this.mTransitionController.startCollectOrQueue(transition, new TransitionController.OnStartCollect() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda3
                                @Override // com.android.server.wm.TransitionController.OnStartCollect
                                public final void onCollectStarted(boolean z2) {
                                    WindowOrganizerController.this.lambda$startTransition$4(transition, windowContainerTransaction2, callerInfo, z, z2);
                                }
                            });
                            token = transition.getToken();
                        } finally {
                            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
                                this.mService.mWindowManager.mSyncEngine.resume();
                            }
                        }
                    } else if (!fromBinder.isCollecting() && !fromBinder.isForcePlaying()) {
                        Slog.e("WindowOrganizerController", "Trying to start a transition that isn't collecting. This probably means Shell took too long to respond to a request. WM State may be incorrect now, please file a bug");
                        applyTransaction(windowContainerTransaction2, -1, null, callerInfo);
                        token = fromBinder.getToken();
                    } else {
                        fromBinder.start();
                        fromBinder.mLogger.mStartWCT = windowContainerTransaction2;
                        applyTransaction(windowContainerTransaction2, -1, fromBinder, callerInfo);
                        token = fromBinder.getToken();
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return token;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startTransition$4(Transition transition, WindowContainerTransaction windowContainerTransaction, CallerInfo callerInfo, boolean z, boolean z2) {
        transition.start();
        transition.mLogger.mStartWCT = windowContainerTransaction;
        applyTransaction(windowContainerTransaction, -1, transition, callerInfo, z2);
        if (z) {
            transition.setAllReady();
        }
    }

    /* JADX WARN: Finally extract failed */
    public int startLegacyTransition(int i, RemoteAnimationAdapter remoteAnimationAdapter, IWindowContainerTransactionCallback iWindowContainerTransactionCallback, WindowContainerTransaction windowContainerTransaction) {
        ActivityTaskManagerService.enforceTaskPermission("startLegacyTransition()");
        CallerInfo callerInfo = new CallerInfo();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (i < 0) {
                        throw new IllegalArgumentException("Can't create transition with no type");
                    }
                    if (this.mTransitionController.getTransitionPlayer() != null) {
                        throw new IllegalArgumentException("Can't use legacy transitions in when shell transitions are enabled.");
                    }
                    DisplayContent displayContent = this.mService.mRootWindowContainer.getDisplayContent(0);
                    if (!displayContent.mAppTransition.isTransitionSet()) {
                        remoteAnimationAdapter.setCallingPidUid(callerInfo.mPid, callerInfo.mUid);
                        displayContent.prepareAppTransition(i);
                        displayContent.mAppTransition.overridePendingAppTransitionRemote(remoteAnimationAdapter, true, false);
                        int startSyncWithOrganizer = startSyncWithOrganizer(iWindowContainerTransactionCallback);
                        applyTransaction(windowContainerTransaction, startSyncWithOrganizer, null, callerInfo);
                        setSyncReady(startSyncWithOrganizer);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return startSyncWithOrganizer;
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return -1;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } catch (Throwable th2) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    public int finishTransition(IBinder iBinder, WindowContainerTransaction windowContainerTransaction, IWindowContainerTransactionCallback iWindowContainerTransactionCallback) {
        int i;
        ActivityTaskManagerService.enforceTaskPermission("finishTransition()");
        CallerInfo callerInfo = new CallerInfo();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                if (windowContainerTransaction == null || iWindowContainerTransactionCallback == null) {
                    i = -1;
                } else {
                    try {
                        i = startSyncWithOrganizer(iWindowContainerTransactionCallback);
                    } finally {
                    }
                }
                Transition fromBinder = Transition.fromBinder(iBinder);
                if (windowContainerTransaction != null) {
                    this.mTransitionController.mFinishingTransition = fromBinder;
                    applyTransaction(windowContainerTransaction, i, (Transition) null, callerInfo, fromBinder);
                }
                this.mTransitionController.finishTransition(fromBinder);
                this.mTransitionController.mFinishingTransition = null;
                if (i >= 0) {
                    setSyncReady(i);
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return i;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void applyTaskFragmentTransactionLocked(final WindowContainerTransaction windowContainerTransaction, int i, boolean z) {
        ITaskFragmentOrganizer taskFragmentOrganizer = windowContainerTransaction.getTaskFragmentOrganizer();
        Objects.requireNonNull(taskFragmentOrganizer);
        enforceTaskFragmentOrganizerPermission("applyTaskFragmentTransaction()", taskFragmentOrganizer, windowContainerTransaction);
        final CallerInfo callerInfo = new CallerInfo();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mTransitionController.getTransitionPlayer() == null) {
                applyTransaction(windowContainerTransaction, -1, null, callerInfo);
                return;
            }
            if (this.mService.mWindowManager.mSyncEngine.hasActiveSync() && !z) {
                Transition collectingTransition = this.mTransitionController.getCollectingTransition();
                if (collectingTransition == null && ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                    ProtoLogImpl.w(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -353495930, 0, (String) null, (Object[]) null);
                }
                applyTransaction(windowContainerTransaction, -1, collectingTransition, callerInfo);
                return;
            }
            final Transition transition = new Transition(i, 0, this.mTransitionController, this.mService.mWindowManager.mSyncEngine);
            this.mTransitionController.startCollectOrQueue(transition, new TransitionController.OnStartCollect() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda4
                @Override // com.android.server.wm.TransitionController.OnStartCollect
                public final void onCollectStarted(boolean z2) {
                    WindowOrganizerController.this.lambda$applyTaskFragmentTransactionLocked$5(windowContainerTransaction, transition, callerInfo, z2);
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyTaskFragmentTransactionLocked$5(WindowContainerTransaction windowContainerTransaction, Transition transition, CallerInfo callerInfo, boolean z) {
        if (z && !this.mTaskFragmentOrganizerController.isValidTransaction(windowContainerTransaction)) {
            transition.abort();
        } else if (applyTransaction(windowContainerTransaction, -1, transition, callerInfo, z) == 0 && transition.mParticipants.isEmpty()) {
            transition.abort();
        } else {
            this.mTransitionController.requestStartTransition(transition, null, null, null);
            transition.setAllReady();
        }
    }

    public final int applyTransaction(WindowContainerTransaction windowContainerTransaction, int i, Transition transition, CallerInfo callerInfo) {
        return applyTransaction(windowContainerTransaction, i, transition, callerInfo, (Transition) null);
    }

    public final int applyTransaction(WindowContainerTransaction windowContainerTransaction, int i, Transition transition, CallerInfo callerInfo, boolean z) {
        if (z) {
            try {
                return applyTransaction(windowContainerTransaction, i, transition, callerInfo);
            } catch (RuntimeException e) {
                Slog.e("WindowOrganizerController", "Failed to execute deferred applyTransaction", e);
                return 0;
            }
        }
        return applyTransaction(windowContainerTransaction, i, transition, callerInfo);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r0v21, types: [android.graphics.Rect, com.android.server.wm.ActivityRecord] */
    /* JADX WARN: Type inference failed for: r0v34 */
    public final int applyTransaction(WindowContainerTransaction windowContainerTransaction, int i, Transition transition, CallerInfo callerInfo, Transition transition2) {
        String str;
        boolean z;
        Map.Entry entry;
        int i2;
        List list;
        int i3;
        List list2;
        Task task;
        boolean z2;
        Task focusedRootTask;
        String str2;
        TaskDisplayArea taskDisplayArea;
        ArraySet arraySet;
        String str3;
        ?? r0;
        Task task2 = null;
        if (ProtoLogCache.WM_DEBUG_WINDOW_ORGANIZER_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, 906215061, 1, (String) null, new Object[]{Long.valueOf(i)});
        }
        this.mService.deferWindowLayout();
        this.mService.mTaskSupervisor.setDeferRootVisibilityUpdate(true);
        try {
            if (windowContainerTransaction.hasSeqForAsyncTransaction()) {
                this.mLastAppliedTransactionSeq = windowContainerTransaction.getSeqForAsyncTransaction();
            }
            windowContainerTransaction.isStartTasksType();
            if (windowContainerTransaction.isStartIntentsType()) {
                this.mService.mMultiInstanceController.adjustStartIntents(windowContainerTransaction, callerInfo);
                this.mService.mMultiInstanceController.adjustStartIntentsForSingleInstancePerTask(windowContainerTransaction);
                if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY) {
                    this.mService.mMultiWindowFoldController.initFoldingState("start_intents");
                }
            }
            if (windowContainerTransaction.isStartIntentsType() || windowContainerTransaction.isStartTasksType()) {
                this.mService.mAppPairController.handleAutoPipIfNeededLocked(windowContainerTransaction);
            }
            if (transition != null) {
                transition.applyDisplayChangeIfNeeded();
            }
            if (CoreRune.MW_SHELL_CHANGE_TRANSITION && windowContainerTransaction.changeTransitionRequested()) {
                this.mService.mChangeTransitController.handleChangeTransitionRequest(windowContainerTransaction.getChangeTransitionRequest());
            }
            if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION && windowContainerTransaction.displayChangeTransitionRequested()) {
                this.mService.mChangeTransitController.requestDisplayChangeTransition(windowContainerTransaction.getDisplayIdForChangeTransition(), windowContainerTransaction.getDisplayChangeTransitionReason());
            }
            List hierarchyOps = windowContainerTransaction.getHierarchyOps();
            List changeList = windowContainerTransaction.getChangeList();
            int size = hierarchyOps.size();
            ArraySet arraySet2 = new ArraySet();
            Iterator it = windowContainerTransaction.getChanges().entrySet().iterator();
            boolean z3 = this.mService.mAMBooster == null;
            int i4 = 0;
            while (true) {
                str = "Attempt to operate on detached container: ";
                if (!it.hasNext() && changeList.size() <= 0) {
                    break;
                }
                int i5 = size;
                List list3 = hierarchyOps;
                Task task3 = task2;
                ArraySet arraySet3 = arraySet2;
                boolean z4 = true;
                if (changeList.size() > 0) {
                    z = false;
                    final WindowContainerTransaction.ContainerChange containerChange = (WindowContainerTransaction.ContainerChange) changeList.get(0);
                    entry = new Map.Entry() { // from class: com.android.server.wm.WindowOrganizerController.1
                        @Override // java.util.Map.Entry
                        public WindowContainerTransaction.Change setValue(WindowContainerTransaction.Change change) {
                            return null;
                        }

                        @Override // java.util.Map.Entry
                        public IBinder getKey() {
                            return containerChange.getToken();
                        }

                        @Override // java.util.Map.Entry
                        public WindowContainerTransaction.Change getValue() {
                            return containerChange.getChange();
                        }
                    };
                    changeList.remove(0);
                } else {
                    z = false;
                    entry = (Map.Entry) it.next();
                }
                WindowContainer fromBinder = WindowContainer.fromBinder((IBinder) entry.getKey());
                if (fromBinder != null && fromBinder.isAttached()) {
                    if (!z3 && fromBinder.inSplitScreenWindowingMode() && (((WindowContainerTransaction.Change) entry.getValue()).getConfigSetMask() & 536870912) != 0) {
                        ActivityTaskManagerService activityTaskManagerService = this.mService;
                        activityTaskManagerService.mAMBooster.onActivityRelaunchLocked(activityTaskManagerService.mLastResumedActivity, true);
                        z3 = true;
                    }
                    if (i >= 0) {
                        addToSyncSet(i, fromBinder);
                    }
                    boolean z5 = z;
                    if (transition != null) {
                        transition.collect(fromBinder);
                    }
                    if ((((WindowContainerTransaction.Change) entry.getValue()).getChangeMask() & 64) != 0) {
                        if (transition2 != null) {
                            transition2.setCanPipOnFinish(z5);
                        } else if (transition != null) {
                            transition.setCanPipOnFinish(z5);
                        }
                    }
                    if (fromBinder.asTask() != null && fromBinder.inPinnedWindowingMode() && (((WindowContainerTransaction.Change) entry.getValue()).getWindowingMode() == 0 || (CoreRune.MT_NEW_DEX_PIP && fromBinder.isNewDexMode() && ((WindowContainerTransaction.Change) entry.getValue()).getWindowingMode() == 1))) {
                        int i6 = z5 ? 1 : 0;
                        i3 = i6;
                        i2 = i5;
                        while (i6 < i2) {
                            List list4 = list3;
                            WindowContainerTransaction.HierarchyOp hierarchyOp = (WindowContainerTransaction.HierarchyOp) list4.get(i6);
                            if (hierarchyOp.getType() == 1 && fromBinder.equals(WindowContainer.fromBinder(hierarchyOp.getContainer()))) {
                                i3 = !hierarchyOp.getToTop() ? 1 : 0;
                            }
                            i6++;
                            list3 = list4;
                        }
                        list = list3;
                    } else {
                        i2 = i5;
                        list = list3;
                        i3 = 0;
                    }
                    if (i3 != 0) {
                        fromBinder.asTask().setForceHidden(1, true);
                        list2 = changeList;
                        task = null;
                        fromBinder.asTask().ensureActivitiesVisible(null, 0, true);
                        fromBinder.asTask().mTaskSupervisor.processStoppingAndFinishingActivities(null, false, "force-stop-on-removing-pip");
                    } else {
                        list2 = changeList;
                        task = null;
                    }
                    int applyWindowContainerChange = applyWindowContainerChange(fromBinder, (WindowContainerTransaction.Change) entry.getValue(), windowContainerTransaction.getErrorCallbackToken());
                    i4 |= applyWindowContainerChange;
                    if (i3 != 0) {
                        z4 = true;
                        fromBinder.asTask().setForceHidden(1, false);
                    } else {
                        z4 = true;
                    }
                    if ((i4 & 2) == 0 && (applyWindowContainerChange & 1) != 0) {
                        arraySet3.add(fromBinder);
                    }
                    hierarchyOps = list;
                    task2 = task;
                    changeList = list2;
                    arraySet2 = arraySet3;
                    size = i2;
                }
                List list5 = changeList;
                i2 = i5;
                Slog.e("WindowOrganizerController", "Attempt to operate on detached container: " + fromBinder);
                hierarchyOps = list3;
                changeList = list5;
                task2 = task3;
                arraySet2 = arraySet3;
                size = i2;
            }
            Iterator it2 = hierarchyOps.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z2 = true;
                    break;
                }
                WindowContainerTransaction.HierarchyOp hierarchyOp2 = (WindowContainerTransaction.HierarchyOp) it2.next();
                if (!z3 && hierarchyOp2.getType() == 2) {
                    ActivityTaskManagerService activityTaskManagerService2 = this.mService;
                    z2 = true;
                    activityTaskManagerService2.mAMBooster.onActivityRelaunchLocked(activityTaskManagerService2.mLastResumedActivity, true);
                    break;
                }
            }
            if (windowContainerTransaction.isStagePositionChanged()) {
                this.mService.mMultiTaskingController.continueEnsureConfig();
            }
            TaskDisplayArea defaultTaskDisplayArea = this.mService.mRootWindowContainer.getDefaultTaskDisplayArea();
            String str4 = "wct";
            if (((defaultTaskDisplayArea.hasChildTaskInSideStage() || defaultTaskDisplayArea.isSplitScreenModeActivated()) ? false : z2) && isEnterSplitWithSingleStage(hierarchyOps)) {
                onEnterSplitWithSingleStageStarted("wct");
            }
            if ((windowContainerTransaction.isStartTaskAndIntentType() || windowContainerTransaction.isStartTasksFromRecentType()) && (focusedRootTask = defaultTaskDisplayArea.getFocusedRootTask()) != null && focusedRootTask.isActivityTypeRecents()) {
                defaultTaskDisplayArea.moveHomeRootTaskToFront("split(edge_all_apps)");
                Task rootHomeTask = defaultTaskDisplayArea.getRootHomeTask();
                Task topLeafTask = rootHomeTask != null ? rootHomeTask.getTopLeafTask() : task2;
                if (topLeafTask != null) {
                    this.mService.getTaskChangeNotificationController().notifyTaskMovedToFront(topLeafTask.getTaskInfo());
                }
            }
            if (CoreRune.MT_NEW_DEX_DEFAULT_DISPLAY_LAUNCH_POLICY && windowContainerTransaction.isUpdateDesktopModeActive()) {
                this.mService.mNewDexController.updateDesktopModeActive();
            }
            if (size > 0) {
                if (CoreRune.MW_SHELL_TRANSITION_BUG_FIX && windowContainerTransaction.isStartTaskAndIntentType()) {
                    this.mService.mWindowManager.mSyncEngine.pause();
                }
                try {
                    boolean isInLockTaskMode = this.mService.isInLockTaskMode();
                    int i7 = i4;
                    int i8 = 0;
                    while (i8 < size) {
                        String str5 = str4;
                        TaskDisplayArea taskDisplayArea2 = defaultTaskDisplayArea;
                        ArraySet arraySet4 = arraySet2;
                        int i9 = size;
                        List list6 = hierarchyOps;
                        int i10 = i8;
                        String str6 = str;
                        Task task4 = task2;
                        i7 |= applyHierarchyOp((WindowContainerTransaction.HierarchyOp) hierarchyOps.get(i8), i7, i, transition, isInLockTaskMode, callerInfo, windowContainerTransaction.getErrorCallbackToken(), windowContainerTransaction.getTaskFragmentOrganizer(), transition2, windowContainerTransaction.isDismissSplit());
                        i8 = i10 + 1;
                        task2 = task4;
                        str = str6;
                        str4 = str5;
                        defaultTaskDisplayArea = taskDisplayArea2;
                        arraySet2 = arraySet4;
                        size = i9;
                        hierarchyOps = list6;
                    }
                    str2 = str4;
                    taskDisplayArea = defaultTaskDisplayArea;
                    arraySet = arraySet2;
                    str3 = str;
                    r0 = task2;
                    i4 = i7;
                } finally {
                    if (CoreRune.MW_SHELL_TRANSITION_BUG_FIX && windowContainerTransaction.isStartTaskAndIntentType()) {
                        this.mService.mWindowManager.mSyncEngine.resume();
                    }
                }
            } else {
                str2 = "wct";
                taskDisplayArea = defaultTaskDisplayArea;
                arraySet = arraySet2;
                str3 = "Attempt to operate on detached container: ";
                r0 = task2;
            }
            if (whileEnterSplitWithSingleStage() && !(taskDisplayArea.hasChildTaskInSideStage() ^ taskDisplayArea.isSplitScreenModeActivated())) {
                onEnterSplitWithSingleStageFinished(str2);
            }
            for (Map.Entry entry2 : windowContainerTransaction.getChanges().entrySet()) {
                WindowContainer fromBinder2 = WindowContainer.fromBinder((IBinder) entry2.getKey());
                if (fromBinder2 != null && fromBinder2.isAttached()) {
                    Task asTask = fromBinder2.asTask();
                    Rect boundsChangeSurfaceBounds = ((WindowContainerTransaction.Change) entry2.getValue()).getBoundsChangeSurfaceBounds();
                    if (asTask != null && asTask.isAttached() && boundsChangeSurfaceBounds != null) {
                        if (!asTask.isOrganized()) {
                            Task asTask2 = asTask.getParent() != null ? asTask.getParent().asTask() : r0;
                            if (asTask2 == null || !asTask2.mCreatedByOrganizer) {
                                throw new IllegalArgumentException("Can't manipulate non-organized task surface " + asTask);
                            }
                        }
                        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                        SurfaceControl surfaceControl = asTask.getSurfaceControl();
                        transaction.setPosition(surfaceControl, boundsChangeSurfaceBounds.left, boundsChangeSurfaceBounds.top);
                        if (boundsChangeSurfaceBounds.isEmpty()) {
                            transaction.setWindowCrop(surfaceControl, r0);
                        } else {
                            transaction.setWindowCrop(surfaceControl, boundsChangeSurfaceBounds.width(), boundsChangeSurfaceBounds.height());
                        }
                        asTask.setMainWindowSizeChangeTransaction(transaction);
                    }
                }
                Slog.e("WindowOrganizerController", str3 + fromBinder2);
            }
            if ((i4 & 2) != 0) {
                this.mService.mTaskSupervisor.setDeferRootVisibilityUpdate(false);
                this.mService.mRootWindowContainer.ensureActivitiesVisible(r0, 0, true);
                this.mService.mRootWindowContainer.resumeFocusedTasksTopActivities();
            } else if ((i4 & 1) != 0) {
                int size2 = arraySet.size() - 1;
                while (size2 >= 0) {
                    ArraySet arraySet5 = arraySet;
                    Task asTask3 = ((WindowContainer) arraySet5.valueAt(size2)).asTask();
                    if (asTask3 != 0 && asTask3.mPendingEnsureVisibleForPopOver) {
                        asTask3.mPendingEnsureVisibleForPopOver = false;
                        asTask3.ensureActivitiesVisible(r0, 0, true);
                    } else {
                        ((WindowContainer) arraySet5.valueAt(size2)).forAllActivities(new Consumer() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda6
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ((ActivityRecord) obj).ensureActivityConfiguration(0, true);
                            }
                        });
                    }
                    size2--;
                    arraySet = arraySet5;
                }
            }
            if (i4 != 0) {
                this.mService.mWindowManager.mWindowPlacerLocked.requestTraversal();
            }
            return i4;
        } finally {
            windowContainerTransaction.isStartTasksType();
            boolean z6 = false;
            this.mService.mAppPairController.setShouldAutoPipByAppPair(z6);
            this.mService.mTaskSupervisor.setDeferRootVisibilityUpdate(z6);
            this.mService.continueWindowLayout();
        }
    }

    public final int applyChanges(WindowContainer windowContainer, WindowContainerTransaction.Change change) {
        int i;
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION && change.hasChangeTransitMode()) {
            this.mService.mChangeTransitController.handleWindowContainerTransaction(windowContainer, change);
        }
        if (change.hasChangeFreeformStashMode() || change.hasChangeFreeformStashScale()) {
            this.mService.mMultiTaskingController.updateFreeformStashState(windowContainer, change);
        }
        int configSetMask = change.getConfigSetMask() & 536886272;
        int windowSetMask = change.getWindowSetMask() & 77594627;
        int windowingMode = change.getWindowingMode();
        boolean z = true;
        if (isSplitScreenChildTask(windowContainer) && windowingMode == 5) {
            windowContainer.reparent(windowContainer.getTaskDisplayArea(), Integer.MAX_VALUE);
        } else if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY && this.mService.getConfiguration().isNewDexMode() && isSplitScreenChildTask(windowContainer) && windowingMode == 1) {
            TaskDisplayArea taskDisplayArea = windowContainer.getTaskDisplayArea();
            windowContainer.reparent(taskDisplayArea, taskDisplayArea.getRootTaskIndex(windowContainer.asTask().getRootTask()) + 1);
        }
        if (CoreRune.MT_SUPPORT_SIZE_COMPAT) {
            SizeCompatPolicyManager.get().ensureTransaction(windowContainer.asTask(), change, configSetMask);
        }
        if (configSetMask != 0) {
            if (windowingMode > -1 && windowingMode != windowContainer.getWindowingMode()) {
                windowContainer.getRequestedOverrideConfiguration().setTo(change.getConfiguration(), configSetMask, windowSetMask);
            } else {
                Configuration configuration = new Configuration(windowContainer.getRequestedOverrideConfiguration());
                configuration.setTo(change.getConfiguration(), configSetMask, windowSetMask);
                windowContainer.onRequestedOverrideConfigurationChanged(configuration);
            }
            i = (windowSetMask == 0 || !windowContainer.isEmbedded()) ? 1 : 3;
        } else {
            i = 0;
        }
        if ((change.getChangeMask() & 1) != 0 && windowContainer.setFocusable(change.getFocusable())) {
            i |= 2;
        }
        if (change.hasChangeFreeformStashMode() && windowContainer.inFreeformWindowingMode() && windowContainer.asTask() != null && !windowContainer.asTask().isMinimized() && CoreRune.MT_NEW_DEX && windowContainer.isNewDexMode() && change.getChangeFreeformStashMode() == 1) {
            i |= 2;
        }
        if (windowingMode <= -1) {
            return i;
        }
        if (this.mService.isInLockTaskMode() && WindowConfiguration.inMultiWindowMode(windowingMode) && (!CoreRune.MW_EMBED_ACTIVITY || !windowContainer.isEmbedded() || windowContainer.asTaskFragment() == null || windowContainer.getParent() == null || WindowConfiguration.inMultiWindowMode(windowContainer.getParent().getWindowingMode()))) {
            Slog.w("WindowOrganizerController", "Dropping unsupported request to set multi-window windowing mode during locked task mode.");
            return i;
        }
        if (windowingMode == 2) {
            return i;
        }
        int requestedOverrideWindowingMode = windowContainer.getRequestedOverrideWindowingMode();
        if (windowingMode == 5 && change.getChangeTransitMode() == 4 && windowContainer.asTask() != null) {
            windowContainer.asTask().mSkipLayoutTask = true;
        } else {
            z = false;
        }
        windowContainer.setWindowingMode(windowingMode);
        if (z) {
            windowContainer.asTask().mSkipLayoutTask = false;
        }
        return requestedOverrideWindowingMode != windowContainer.getWindowingMode() ? i | 2 : i;
    }

    public final int applyTaskChanges(Task task, WindowContainerTransaction.Change change) {
        ActivityRecord topNonFinishingActivity;
        int applyChanges = applyChanges(task, change);
        SurfaceControl.Transaction boundsChangeTransaction = change.getBoundsChangeTransaction();
        if ((change.getChangeMask() & 8) != 0 && task.setForceHidden(2, change.getHidden())) {
            applyChanges = 2;
        }
        if ((change.getChangeMask() & 128) != 0) {
            task.setForceTranslucent(change.getForceTranslucent());
            applyChanges = 2;
        }
        if ((change.getChangeMask() & 256) != 0) {
            task.setDragResizing(change.getDragResizing());
        }
        final int activityWindowingMode = change.getActivityWindowingMode();
        if (activityWindowingMode > -1) {
            task.forAllActivities(new Consumer() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((ActivityRecord) obj).setWindowingMode(activityWindowingMode);
                }
            });
        }
        if (boundsChangeTransaction != null) {
            task.setMainWindowSizeChangeTransaction(boundsChangeTransaction);
        }
        Rect enterPipBounds = change.getEnterPipBounds();
        if (enterPipBounds != null) {
            task.mDisplayContent.mPinnedTaskController.setEnterPipBounds(enterPipBounds);
        }
        if (change.getWindowingMode() == 2 && !task.inPinnedWindowingMode() && (topNonFinishingActivity = task.getTopNonFinishingActivity()) != null) {
            boolean z = topNonFinishingActivity.supportsEnterPipOnTaskSwitch;
            topNonFinishingActivity.supportsEnterPipOnTaskSwitch = true;
            boolean checkEnterPictureInPictureState = topNonFinishingActivity.checkEnterPictureInPictureState("applyTaskChanges", true);
            if (checkEnterPictureInPictureState) {
                checkEnterPictureInPictureState = this.mService.mActivityClientController.requestPictureInPictureMode(topNonFinishingActivity);
            }
            if (!checkEnterPictureInPictureState) {
                topNonFinishingActivity.supportsEnterPipOnTaskSwitch = z;
            }
        }
        return applyChanges;
    }

    public final int applyDisplayAreaChanges(DisplayArea displayArea, final WindowContainerTransaction.Change change) {
        final int[] iArr = {applyChanges(displayArea, change)};
        if ((change.getChangeMask() & 32) != 0 && displayArea.setIgnoreOrientationRequest(change.getIgnoreOrientationRequest())) {
            iArr[0] = iArr[0] | 2;
        }
        displayArea.forAllTasks(new Consumer() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                WindowOrganizerController.lambda$applyDisplayAreaChanges$8(change, iArr, obj);
            }
        });
        return iArr[0];
    }

    public static /* synthetic */ void lambda$applyDisplayAreaChanges$8(WindowContainerTransaction.Change change, int[] iArr, Object obj) {
        Task task = (Task) obj;
        if ((change.getChangeMask() & 8) == 0 || !task.setForceHidden(2, change.getHidden())) {
            return;
        }
        iArr[0] = iArr[0] | 2;
    }

    public final int applyTaskFragmentChanges(TaskFragment taskFragment, WindowContainerTransaction.Change change, IBinder iBinder) {
        if (taskFragment.isEmbeddedTaskFragmentInPip()) {
            return 0;
        }
        this.mTmpBounds0.set(taskFragment.getBounds());
        this.mTmpBounds1.set(taskFragment.getRelativeEmbeddedBounds());
        taskFragment.deferOrganizedTaskFragmentSurfaceUpdate();
        Rect relativeBounds = change.getRelativeBounds();
        if (relativeBounds != null) {
            adjustTaskFragmentRelativeBoundsForMinDimensionsIfNeeded(taskFragment, relativeBounds, iBinder);
            change.getConfiguration().windowConfiguration.setBounds(taskFragment.translateRelativeBoundsToAbsoluteBounds(relativeBounds, taskFragment.getParent().getBounds()));
            taskFragment.setRelativeEmbeddedBounds(relativeBounds);
        }
        int applyChanges = applyChanges(taskFragment, change);
        if (taskFragment.shouldStartChangeTransition(this.mTmpBounds0, this.mTmpBounds1)) {
            taskFragment.initializeChangeTransition(this.mTmpBounds0);
        }
        taskFragment.continueOrganizedTaskFragmentSurfaceUpdate();
        return applyChanges;
    }

    public final void adjustTaskFragmentRelativeBoundsForMinDimensionsIfNeeded(TaskFragment taskFragment, Rect rect, IBinder iBinder) {
        if (rect.isEmpty()) {
            return;
        }
        Point calculateMinDimension = taskFragment.calculateMinDimension();
        if (rect.width() < calculateMinDimension.x || rect.height() < calculateMinDimension.y) {
            sendTaskFragmentOperationFailure(taskFragment.getTaskFragmentOrganizer(), iBinder, taskFragment, 9, new SecurityException("The requested relative bounds:" + rect + " does not satisfy minimum dimensions:" + calculateMinDimension));
            rect.setEmpty();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x031a  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x044c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int applyHierarchyOp(final WindowContainerTransaction.HierarchyOp hierarchyOp, int i, int i2, Transition transition, boolean z, final CallerInfo callerInfo, IBinder iBinder, ITaskFragmentOrganizer iTaskFragmentOrganizer, Transition transition2, boolean z2) {
        int applyTaskFragmentOperation;
        int i3;
        WindowContainer fromBinder;
        Task transientLaunchRestoreTarget;
        int type = hierarchyOp.getType();
        if (type != 17) {
            switch (type) {
                case 0:
                case 1:
                    WindowContainer fromBinder2 = WindowContainer.fromBinder(hierarchyOp.getContainer());
                    if (fromBinder2 == null || !fromBinder2.isAttached()) {
                        Slog.e("WindowOrganizerController", "Attempt to operate on detached container: " + fromBinder2);
                    } else if (z && type == 0) {
                        Slog.w("WindowOrganizerController", "Skip applying hierarchy operation " + hierarchyOp + " while in lock task mode");
                    } else if (!isLockTaskModeViolation(fromBinder2.getParent(), fromBinder2.asTask(), z)) {
                        if (i2 >= 0) {
                            addToSyncSet(i2, fromBinder2);
                        }
                        if (transition != null) {
                            transition.collect(fromBinder2);
                            if (hierarchyOp.isReparent()) {
                                if (fromBinder2.getParent() != null) {
                                    transition.collect(fromBinder2.getParent());
                                }
                                if (hierarchyOp.getNewParent() != null) {
                                    WindowContainer fromBinder3 = WindowContainer.fromBinder(hierarchyOp.getNewParent());
                                    if (fromBinder3 == null) {
                                        Slog.e("WindowOrganizerController", "Can't resolve parent window from token");
                                    } else {
                                        transition.collect(fromBinder3);
                                    }
                                }
                            }
                        }
                        applyTaskFragmentOperation = sanitizeAndApplyHierarchyOp(fromBinder2, hierarchyOp);
                        break;
                    }
                    i3 = i;
                    if (type != 16) {
                        switch (type) {
                            case 8:
                                Bundle launchOptions = hierarchyOp.getLaunchOptions();
                                String string = launchOptions.getString("android:transaction.hop.shortcut_calling_package");
                                launchOptions.remove("android:transaction.hop.shortcut_calling_package");
                                if (!((LauncherAppsService.LauncherAppsServiceInternal) LocalServices.getService(LauncherAppsService.LauncherAppsServiceInternal.class)).startShortcut(callerInfo.mUid, callerInfo.mPid, string, hierarchyOp.getShortcutInfo().getPackage(), null, hierarchyOp.getShortcutInfo().getId(), null, launchOptions, hierarchyOp.getShortcutInfo().getUserId())) {
                                    return i3;
                                }
                                break;
                            case 9:
                                if (transition2 == null || (fromBinder = WindowContainer.fromBinder(hierarchyOp.getContainer())) == null) {
                                    return i3;
                                }
                                Task task = fromBinder.asActivityRecord() != null ? fromBinder.asActivityRecord().getTask() : fromBinder.asTask();
                                if (task == null || (transientLaunchRestoreTarget = transition2.getTransientLaunchRestoreTarget(fromBinder)) == null) {
                                    return i3;
                                }
                                task.getTaskDisplayArea().moveRootTaskBehindRootTask(task.getRootTask(), transientLaunchRestoreTarget);
                                return i3;
                            case 10:
                                WindowContainer fromBinder4 = WindowContainer.fromBinder(hierarchyOp.getContainer());
                                if (fromBinder4 == null) {
                                    Slog.e("WindowOrganizerController", "Attempt to add local insets source provider on unknown: " + fromBinder4);
                                    return i3;
                                }
                                fromBinder4.addLocalInsetsFrameProvider(hierarchyOp.getInsetsFrameProvider());
                                return i3;
                            case 11:
                                WindowContainer fromBinder5 = WindowContainer.fromBinder(hierarchyOp.getContainer());
                                if (fromBinder5 == null) {
                                    Slog.e("WindowOrganizerController", "Attempt to remove local insets source provider from unknown: " + fromBinder5);
                                    return i3;
                                }
                                fromBinder5.removeLocalInsetsFrameProvider(hierarchyOp.getInsetsFrameProvider());
                                return i3;
                            case 12:
                                WindowContainer fromBinder6 = WindowContainer.fromBinder(hierarchyOp.getContainer());
                                if (fromBinder6 == null || fromBinder6.asDisplayArea() == null || !fromBinder6.isAttached()) {
                                    if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY && fromBinder6 != null && fromBinder6.isAttached() && fromBinder6.asTask() != null) {
                                        fromBinder6.setAlwaysOnTop(hierarchyOp.isAlwaysOnTop());
                                        break;
                                    } else {
                                        Slog.e("WindowOrganizerController", "Attempt to operate on unknown or detached display area: " + fromBinder6);
                                        return i3;
                                    }
                                } else {
                                    fromBinder6.setAlwaysOnTop(hierarchyOp.isAlwaysOnTop());
                                    break;
                                }
                                break;
                            default:
                                return i3;
                        }
                        return i3 | 2;
                    }
                    WindowContainer fromBinder7 = WindowContainer.fromBinder(hierarchyOp.getContainer());
                    Task asTask = fromBinder7 != null ? fromBinder7.asTask() : null;
                    if (asTask == null || !asTask.isAttached()) {
                        Slog.e("WindowOrganizerController", "Attempt to operate on unknown or detached container: " + fromBinder7);
                        return i3;
                    }
                    if (!asTask.mCreatedByOrganizer) {
                        throw new UnsupportedOperationException("Cannot set reparent leaf task flag on non-organized task : " + asTask);
                    }
                    if (!asTask.isRootTask()) {
                        throw new UnsupportedOperationException("Cannot set reparent leaf task flag on non-root task : " + asTask);
                    }
                    asTask.setReparentLeafTaskIfRelaunch(hierarchyOp.isReparentLeafTaskIfRelaunch());
                    return i3;
                case 2:
                    applyTaskFragmentOperation = reparentChildrenTasksHierarchyOp(hierarchyOp, transition, i2, z, z2);
                    break;
                case 3:
                    WindowContainer fromBinder8 = WindowContainer.fromBinder(hierarchyOp.getContainer());
                    if (fromBinder8 == null || !fromBinder8.isAttached()) {
                        Slog.e("WindowOrganizerController", "Attempt to set launch root to a detached container: " + fromBinder8);
                    } else {
                        Task asTask2 = fromBinder8.asTask();
                        if (asTask2 == null) {
                            throw new IllegalArgumentException("Cannot set non-task as launch root: " + fromBinder8);
                        }
                        if (asTask2.getTaskDisplayArea() == null) {
                            throw new IllegalArgumentException("Cannot set a task without display area as launch root: " + fromBinder8);
                        }
                        asTask2.getDisplayArea().setLaunchRootTask(asTask2, hierarchyOp.getWindowingModes(), hierarchyOp.getActivityTypes());
                    }
                    i3 = i;
                    if (type != 16) {
                    }
                    break;
                case 4:
                    applyTaskFragmentOperation = setAdjacentRootsHierarchyOp(hierarchyOp);
                    break;
                case 5:
                    this.mService.mAmInternal.enforceCallingPermission("android.permission.START_TASKS_FROM_RECENTS", "launchTask HierarchyOp");
                    Bundle launchOptions2 = hierarchyOp.getLaunchOptions();
                    final int i4 = launchOptions2.getInt("android:transaction.hop.taskId");
                    launchOptions2.remove("android:transaction.hop.taskId");
                    final SafeActivityOptions fromBundle = SafeActivityOptions.fromBundle(launchOptions2, callerInfo.mPid, callerInfo.mUid);
                    waitAsyncStart(new IntSupplier() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda8
                        @Override // java.util.function.IntSupplier
                        public final int getAsInt() {
                            int lambda$applyHierarchyOp$9;
                            lambda$applyHierarchyOp$9 = WindowOrganizerController.this.lambda$applyHierarchyOp$9(callerInfo, i4, fromBundle);
                            return lambda$applyHierarchyOp$9;
                        }
                    });
                    i3 = i;
                    if (type != 16) {
                    }
                    break;
                case 6:
                    WindowContainer fromBinder9 = WindowContainer.fromBinder(hierarchyOp.getContainer());
                    if (fromBinder9 == null || !fromBinder9.isAttached()) {
                        Slog.e("WindowOrganizerController", "Attempt to set launch adjacent to a detached container: " + fromBinder9);
                    } else {
                        Task asTask3 = fromBinder9.asTask();
                        boolean toTop = hierarchyOp.getToTop();
                        if (asTask3 == null) {
                            throw new IllegalArgumentException("Cannot set non-task as launch root: " + fromBinder9);
                        }
                        if (!asTask3.mCreatedByOrganizer) {
                            throw new UnsupportedOperationException("Cannot set non-organized task as adjacent flag root: " + fromBinder9);
                        }
                        if (asTask3.getAdjacentTaskFragment() == null && !toTop) {
                            throw new UnsupportedOperationException("Cannot set non-adjacent task as adjacent flag root: " + fromBinder9);
                        }
                        TaskDisplayArea displayArea = asTask3.getDisplayArea();
                        if (toTop) {
                            asTask3 = null;
                        }
                        displayArea.setLaunchAdjacentFlagRootTask(asTask3);
                    }
                    i3 = i;
                    if (type != 16) {
                    }
                    break;
                case 7:
                    Bundle launchOptions3 = hierarchyOp.getLaunchOptions();
                    ActivityOptions activityOptions = launchOptions3 != null ? new ActivityOptions(launchOptions3) : null;
                    if (activityOptions == null || !activityOptions.getTransientLaunch() || !this.mService.isCallerRecents(hierarchyOp.getPendingIntent().getCreatorUid()) || !this.mService.getActivityStartController().startExistingRecentsIfPossible(hierarchyOp.getActivityIntent(), activityOptions)) {
                        final String resolveTypeIfNeeded = hierarchyOp.getActivityIntent() != null ? hierarchyOp.getActivityIntent().resolveTypeIfNeeded(this.mService.mContext.getContentResolver()) : null;
                        if (hierarchyOp.getPendingIntent().isActivity()) {
                            if (activityOptions == null) {
                                activityOptions = ActivityOptions.makeBasic();
                            }
                            activityOptions.setCallerDisplayId(0);
                        }
                        final Bundle bundle = activityOptions != null ? activityOptions.toBundle() : null;
                        if (ActivityManager.isStartResultSuccessful(waitAsyncStart(new IntSupplier() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda9
                            @Override // java.util.function.IntSupplier
                            public final int getAsInt() {
                                int lambda$applyHierarchyOp$10;
                                lambda$applyHierarchyOp$10 = WindowOrganizerController.this.lambda$applyHierarchyOp$10(hierarchyOp, resolveTypeIfNeeded, bundle, callerInfo);
                                return lambda$applyHierarchyOp$10;
                            }
                        }))) {
                            i3 = i | 2;
                            if (type != 16) {
                            }
                        }
                    }
                    i3 = i;
                    if (type != 16) {
                    }
                    break;
                default:
                    switch (type) {
                        case 13:
                            WindowContainer fromBinder10 = WindowContainer.fromBinder(hierarchyOp.getContainer());
                            if (fromBinder10 == null || fromBinder10.asTask() == null || !fromBinder10.isAttached()) {
                                Slog.e("WindowOrganizerController", "Attempt to remove invalid task: " + fromBinder10);
                                i3 = i;
                                if (type != 16) {
                                }
                            } else {
                                Task asTask4 = fromBinder10.asTask();
                                int i5 = (CoreRune.MW_SHELL_TRANSITION_BUG_FIX && asTask4.hasActivity()) ? i | 2 : i;
                                asTask4.remove(true, "Applying remove task Hierarchy Op");
                                i3 = i5;
                                if (type != 16) {
                                }
                            }
                            break;
                        case 14:
                            ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(hierarchyOp.getContainer());
                            if (forTokenLocked != null && !forTokenLocked.finishing) {
                                if (forTokenLocked.isVisible() || forTokenLocked.isVisibleRequested()) {
                                    forTokenLocked.finishIfPossible("finish-activity-op", false);
                                } else {
                                    forTokenLocked.destroyIfPossible("finish-activity-op");
                                }
                            }
                            i3 = i;
                            if (type != 16) {
                            }
                            break;
                        case 15:
                            applyTaskFragmentOperation = clearAdjacentRootsHierarchyOp(hierarchyOp);
                            break;
                        default:
                            if (z) {
                                Slog.w("WindowOrganizerController", "Skip applying hierarchy operation " + hierarchyOp + " while in lock task mode");
                                return i;
                            }
                            i3 = i;
                            if (type != 16) {
                            }
                            break;
                    }
            }
        } else {
            applyTaskFragmentOperation = applyTaskFragmentOperation(hierarchyOp, transition, z, callerInfo, iBinder, iTaskFragmentOrganizer);
        }
        i3 = i | applyTaskFragmentOperation;
        if (type != 16) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$applyHierarchyOp$9(CallerInfo callerInfo, int i, SafeActivityOptions safeActivityOptions) {
        return this.mService.mTaskSupervisor.startActivityFromRecents(callerInfo.mPid, callerInfo.mUid, i, safeActivityOptions);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$applyHierarchyOp$10(WindowContainerTransaction.HierarchyOp hierarchyOp, String str, Bundle bundle, CallerInfo callerInfo) {
        return this.mService.mAmInternal.sendIntentSender(hierarchyOp.getPendingIntent().getTarget(), hierarchyOp.getPendingIntent().getWhitelistToken(), 0, hierarchyOp.getActivityIntent(), str, (IIntentReceiver) null, (String) null, bundle, callerInfo.mPid, callerInfo.mUid);
    }

    public final int applyTaskFragmentOperation(WindowContainerTransaction.HierarchyOp hierarchyOp, Transition transition, boolean z, CallerInfo callerInfo, IBinder iBinder, ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        ActivityRecord activity;
        boolean z2 = false;
        if (!validateTaskFragmentOperation(hierarchyOp, iBinder, iTaskFragmentOrganizer)) {
            return 0;
        }
        TaskFragment taskFragment = (TaskFragment) this.mLaunchTaskFragments.get(hierarchyOp.getContainer());
        TaskFragmentOperation taskFragmentOperation = hierarchyOp.getTaskFragmentOperation();
        int opType = taskFragmentOperation.getOpType();
        int i = 2;
        switch (opType) {
            case 0:
                TaskFragmentCreationParams taskFragmentCreationParams = taskFragmentOperation.getTaskFragmentCreationParams();
                if (taskFragmentCreationParams == null) {
                    sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment, opType, new IllegalArgumentException("TaskFragmentCreationParams must be non-null"));
                    return 0;
                }
                createTaskFragment(taskFragmentCreationParams, iBinder, callerInfo, transition);
                return 0;
            case 1:
                if (z && (activity = taskFragment.getActivity(new Predicate() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$applyTaskFragmentOperation$12;
                        lambda$applyTaskFragmentOperation$12 = WindowOrganizerController.lambda$applyTaskFragmentOperation$12((ActivityRecord) obj);
                        return lambda$applyTaskFragmentOperation$12;
                    }
                }, false)) != null && this.mService.getLockTaskController().activityBlockedFromFinish(activity)) {
                    Slog.w("WindowOrganizerController", "Skip removing TaskFragment due in lock task mode.");
                    sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment, opType, new IllegalStateException("Not allow to delete task fragment in lock task mode."));
                    return 0;
                }
                return 0 | deleteTaskFragment(taskFragment, transition);
            case 2:
                IBinder activityToken = taskFragmentOperation.getActivityToken();
                Intent activityIntent = taskFragmentOperation.getActivityIntent();
                int startActivityInTaskFragment = this.mService.getActivityStartController().startActivityInTaskFragment(taskFragment, activityIntent, taskFragmentOperation.getBundle(), activityToken, callerInfo.mUid, callerInfo.mPid, iBinder);
                if (!ActivityManager.isStartResultSuccessful(startActivityInTaskFragment)) {
                    sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment, opType, convertStartFailureToThrowable(startActivityInTaskFragment, activityIntent));
                    return 0;
                }
                break;
            case 3:
                IBinder activityToken2 = taskFragmentOperation.getActivityToken();
                ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(activityToken2);
                ActivityRecord activityRecord = forTokenLocked;
                if (forTokenLocked == null) {
                    activityRecord = this.mTaskFragmentOrganizerController.getReparentActivityFromTemporaryToken(iTaskFragmentOrganizer, activityToken2);
                }
                if (activityRecord == null) {
                    sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment, opType, new IllegalArgumentException("Not allowed to operate with invalid activity."));
                    return 0;
                }
                if (taskFragment.isAllowedToEmbedActivity(activityRecord) != 0) {
                    sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment, opType, new SecurityException("The task fragment is not allowed to embed the given activity."));
                    return 0;
                }
                if (taskFragment.getTask() != activityRecord.getTask()) {
                    sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment, opType, new SecurityException("The reparented activity is not in the same Task as the target TaskFragment."));
                    return 0;
                }
                if (CoreRune.MW_EMBED_ACTIVITY) {
                    if (activityRecord.getParent() == null) {
                        Slog.d("WindowOrganizerController", "Skip reparent activity to TaskFragment " + activityRecord.shortComponentName + " is current parent is null");
                        return 0;
                    }
                    if (taskFragment == activityRecord.getTaskFragment()) {
                        Slog.d("WindowOrganizerController", "Skip reparent activity to TaskFragment " + activityRecord.shortComponentName + " is already in the same TaskFragment");
                        return 0;
                    }
                }
                if (transition != null) {
                    transition.collect(activityRecord);
                    if (activityRecord.getParent() != null) {
                        transition.collect(activityRecord.getParent());
                    }
                    transition.collect(taskFragment);
                }
                if (CoreRune.MW_EMBED_ACTIVITY && taskFragment.isSplitEmbedded() && activityRecord.isFixedRotationTransforming()) {
                    activityRecord.finishFixedRotationTransform();
                }
                activityRecord.reparent(taskFragment, Integer.MAX_VALUE);
                break;
                break;
            case 4:
                TaskFragment taskFragment2 = (TaskFragment) this.mLaunchTaskFragments.get(taskFragmentOperation.getSecondaryFragmentToken());
                if (taskFragment2 == null) {
                    sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment, opType, new IllegalArgumentException("SecondaryFragmentToken must be set for setAdjacentTaskFragments."));
                    return 0;
                }
                if (taskFragment.getAdjacentTaskFragment() != taskFragment2) {
                    taskFragment.setAdjacentTaskFragment(taskFragment2);
                } else {
                    i = 0;
                }
                if (CoreRune.MW_EMBED_ACTIVITY_MODE) {
                    taskFragment.setEmbedActivityMode(taskFragment.calculateEmbedActivityMode(taskFragment.getTask(), taskFragment.getBounds()));
                    taskFragment2.setEmbedActivityMode(taskFragment2.calculateEmbedActivityMode(taskFragment2.getTask(), taskFragment2.getBounds()));
                }
                Bundle launchOptions = hierarchyOp.getLaunchOptions();
                TaskFragment taskFragmentAdjacentParams = launchOptions != null ? new WindowContainerTransaction.TaskFragmentAdjacentParams(launchOptions) : null;
                taskFragment.setDelayLastActivityRemoval(taskFragmentAdjacentParams != null && taskFragmentAdjacentParams.shouldDelayPrimaryLastActivityRemoval());
                if (taskFragmentAdjacentParams != null && taskFragmentAdjacentParams.shouldDelaySecondaryLastActivityRemoval()) {
                    z2 = true;
                }
                taskFragment2.setDelayLastActivityRemoval(z2);
                break;
            case 5:
                TaskFragment adjacentTaskFragment = taskFragment.getAdjacentTaskFragment();
                if (adjacentTaskFragment != null) {
                    taskFragment.resetAdjacentTaskFragment();
                    ActivityRecord activityRecord2 = taskFragment.getDisplayContent().mFocusedApp;
                    TaskFragment taskFragment3 = activityRecord2 != null ? activityRecord2.getTaskFragment() : null;
                    if ((taskFragment3 == taskFragment || taskFragment3 == adjacentTaskFragment) && !taskFragment3.shouldBeVisible(null)) {
                        taskFragment3.getDisplayContent().setFocusedApp(null);
                        break;
                    }
                } else {
                    return 0;
                }
                break;
            case 6:
                ActivityRecord activityRecord3 = taskFragment.getDisplayContent().mFocusedApp;
                if (activityRecord3 != null && activityRecord3.getTaskFragment() == taskFragment) {
                    Slog.d("WindowOrganizerController", "The requested TaskFragment already has the focus.");
                    return 0;
                }
                if (activityRecord3 != null && activityRecord3.getTask() != taskFragment.getTask()) {
                    Slog.d("WindowOrganizerController", "The Task of the requested TaskFragment doesn't have focus.");
                    return 0;
                }
                ActivityRecord topResumedActivity = taskFragment.getTopResumedActivity();
                if (topResumedActivity == null) {
                    Slog.d("WindowOrganizerController", "There is no resumed activity in the requested TaskFragment.");
                    return 0;
                }
                taskFragment.getDisplayContent().setFocusedApp(topResumedActivity);
                return 0;
            case 7:
                IBinder secondaryFragmentToken = taskFragmentOperation.getSecondaryFragmentToken();
                taskFragment.setCompanionTaskFragment(secondaryFragmentToken != null ? (TaskFragment) this.mLaunchTaskFragments.get(secondaryFragmentToken) : null);
                return 0;
            case 8:
                TaskFragmentAnimationParams animationParams = taskFragmentOperation.getAnimationParams();
                if (animationParams == null) {
                    sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment, opType, new IllegalArgumentException("TaskFragmentAnimationParams must be non-null"));
                    return 0;
                }
                taskFragment.setAnimationParams(animationParams);
                return 0;
            default:
                return 0;
        }
        return i;
    }

    public static /* synthetic */ boolean lambda$applyTaskFragmentOperation$12(ActivityRecord activityRecord) {
        return !activityRecord.finishing;
    }

    public final boolean validateTaskFragmentOperation(WindowContainerTransaction.HierarchyOp hierarchyOp, IBinder iBinder, ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        TaskFragmentOperation taskFragmentOperation = hierarchyOp.getTaskFragmentOperation();
        TaskFragment taskFragment = (TaskFragment) this.mLaunchTaskFragments.get(hierarchyOp.getContainer());
        if (taskFragmentOperation == null) {
            sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment, -1, new IllegalArgumentException("TaskFragmentOperation must be non-null"));
            return false;
        }
        int opType = taskFragmentOperation.getOpType();
        if (opType == 0) {
            return true;
        }
        if (!validateTaskFragment(taskFragment, opType, iBinder, iTaskFragmentOrganizer)) {
            return false;
        }
        IBinder secondaryFragmentToken = taskFragmentOperation.getSecondaryFragmentToken();
        return secondaryFragmentToken == null || validateTaskFragment((TaskFragment) this.mLaunchTaskFragments.get(secondaryFragmentToken), opType, iBinder, iTaskFragmentOrganizer);
    }

    public final boolean validateTaskFragment(TaskFragment taskFragment, int i, IBinder iBinder, ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        if (taskFragment == null || !taskFragment.isAttached()) {
            sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment, i, new IllegalArgumentException("Not allowed to apply operation on invalid fragment tokens opType=" + i));
            return false;
        }
        if (!taskFragment.isEmbeddedTaskFragmentInPip() || (i == 1 && taskFragment.getTopNonFinishingActivity() == null)) {
            return true;
        }
        sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment, i, new IllegalArgumentException("Not allowed to apply operation on PIP TaskFragment"));
        return false;
    }

    public final int waitAsyncStart(final IntSupplier intSupplier) {
        Handler handler;
        final Integer[] numArr = {null};
        if (Looper.myLooper() == this.mService.f1734mH.getLooper()) {
            handler = this.mService.mWindowManager.mAnimationHandler;
        } else {
            handler = this.mService.f1734mH;
        }
        handler.post(new Runnable() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                WindowOrganizerController.this.lambda$waitAsyncStart$13(numArr, intSupplier);
            }
        });
        while (true) {
            Integer num = numArr[0];
            if (num == null) {
                try {
                    this.mGlobalLock.wait();
                } catch (InterruptedException unused) {
                }
            } else {
                return num.intValue();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$waitAsyncStart$13(Integer[] numArr, IntSupplier intSupplier) {
        try {
            numArr[0] = Integer.valueOf(intSupplier.getAsInt());
        } catch (Throwable th) {
            numArr[0] = -96;
            Slog.w("WindowOrganizerController", th);
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mGlobalLock.notifyAll();
            } catch (Throwable th2) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final int sanitizeAndApplyHierarchyOp(WindowContainer windowContainer, WindowContainerTransaction.HierarchyOp hierarchyOp) {
        WindowContainer fromBinder;
        Task asTask = windowContainer.asTask();
        if (asTask == null) {
            throw new IllegalArgumentException("Invalid container in hierarchy op");
        }
        DisplayContent displayContent = asTask.getDisplayContent();
        boolean z = false;
        if (displayContent == null) {
            Slog.w("WindowOrganizerController", "Container is no longer attached: " + asTask);
            return 0;
        }
        boolean z2 = true;
        if (hierarchyOp.isReparent()) {
            if (!asTask.isRootTask() && !asTask.getParent().asTask().mCreatedByOrganizer) {
                z2 = false;
            }
            if (z2) {
                if (hierarchyOp.getNewParent() == null) {
                    fromBinder = displayContent.getDefaultTaskDisplayArea();
                } else {
                    fromBinder = WindowContainer.fromBinder(hierarchyOp.getNewParent());
                }
                if (fromBinder == null) {
                    Slog.e("WindowOrganizerController", "Can't resolve parent window from token");
                    return 0;
                }
                if (asTask.getParent() != fromBinder) {
                    if (fromBinder.asTaskDisplayArea() != null) {
                        asTask.reparent(fromBinder.asTaskDisplayArea(), hierarchyOp.getToTop());
                        return 2;
                    }
                    if (fromBinder.asTask() != null) {
                        if (fromBinder.inMultiWindowMode() && asTask.isLeafTask()) {
                            if (fromBinder.inPinnedWindowingMode()) {
                                Slog.w("WindowOrganizerController", "Can't support moving a task to another PIP window... newParent=" + fromBinder + " task=" + asTask);
                                return 0;
                            }
                            if (!asTask.supportsMultiWindowInDisplayArea(fromBinder.asTask().getDisplayArea())) {
                                Slog.w("WindowOrganizerController", "Can't support task that doesn't support multi-window mode in multi-window mode... newParent=" + fromBinder + " task=" + asTask);
                                return 0;
                            }
                        }
                        asTask.reparent((Task) fromBinder, hierarchyOp.getToTop() ? Integer.MAX_VALUE : Integer.MIN_VALUE, false, "sanitizeAndApplyHierarchyOp");
                        return 2;
                    }
                    throw new RuntimeException("Can only reparent task to another task or taskDisplayArea, but not " + fromBinder);
                }
                if (fromBinder instanceof TaskDisplayArea) {
                    fromBinder = asTask.getRootTask();
                }
                asTask.getDisplayArea().positionChildAt(hierarchyOp.getToTop() ? Integer.MAX_VALUE : Integer.MIN_VALUE, (Task) fromBinder, false);
                return 2;
            }
            throw new RuntimeException("Reparenting leaf Tasks is not supported now. " + asTask);
        }
        asTask.getParent().positionChildAt(hierarchyOp.getToTop() ? Integer.MAX_VALUE : Integer.MIN_VALUE, asTask, false);
        TaskDisplayArea defaultTaskDisplayArea = displayContent.getDefaultTaskDisplayArea();
        if (!hierarchyOp.getToTop() || defaultTaskDisplayArea == null || !asTask.inSplitScreenWindowingMode() || defaultTaskDisplayArea.getTopRootTaskInStageType(asTask.getStageType()) == null) {
            return 2;
        }
        Transition transition = this.mTransitionController.mFinishingTransition;
        if (transition != null && transition.isInTransientHide(asTask.getRootTask())) {
            z = true;
        }
        if (z) {
            return 2;
        }
        this.mService.setFocusedTask(defaultTaskDisplayArea.getTopRootTaskInStageType(asTask.getStageType()).mTaskId);
        return 2;
    }

    public final boolean isLockTaskModeViolation(WindowContainer windowContainer, Task task, boolean z) {
        if (!z || windowContainer == null || task == null) {
            return false;
        }
        LockTaskController lockTaskController = this.mService.getLockTaskController();
        boolean isLockTaskModeViolation = lockTaskController.isLockTaskModeViolation(task);
        if (!isLockTaskModeViolation && windowContainer.asTask() != null) {
            isLockTaskModeViolation = lockTaskController.isLockTaskModeViolation(windowContainer.asTask());
        }
        if (isLockTaskModeViolation) {
            Slog.w("WindowOrganizerController", "Can't support the operation since in lock task mode violation.  Task: " + task + " Parent : " + windowContainer);
        }
        return isLockTaskModeViolation;
    }

    public final int reparentChildrenTasksHierarchyOp(final WindowContainerTransaction.HierarchyOp hierarchyOp, Transition transition, int i, final boolean z, boolean z2) {
        TaskDisplayArea asTaskDisplayArea;
        ActivityRecord activityRecord;
        WindowContainer fromBinder = hierarchyOp.getContainer() != null ? WindowContainer.fromBinder(hierarchyOp.getContainer()) : null;
        WindowContainer fromBinder2 = hierarchyOp.getNewParent() != null ? WindowContainer.fromBinder(hierarchyOp.getNewParent()) : null;
        if (fromBinder == null && fromBinder2 == null) {
            throw new IllegalArgumentException("reparentChildrenTasksHierarchyOp: " + hierarchyOp);
        }
        if (fromBinder == null) {
            fromBinder = fromBinder2.asTask().getDisplayContent().getDefaultTaskDisplayArea();
        } else if (fromBinder2 == null) {
            fromBinder2 = fromBinder.asTask().getDisplayContent().getDefaultTaskDisplayArea();
        }
        final WindowContainer windowContainer = fromBinder;
        final WindowContainer windowContainer2 = fromBinder2;
        if (windowContainer == windowContainer2) {
            Slog.e("WindowOrganizerController", "reparentChildrenTasksHierarchyOp parent not changing: " + hierarchyOp);
            return 0;
        }
        if (!windowContainer.isAttached()) {
            Slog.e("WindowOrganizerController", "reparentChildrenTasksHierarchyOp currentParent detached=" + windowContainer + " hop=" + hierarchyOp);
            return 0;
        }
        if (!windowContainer2.isAttached()) {
            Slog.e("WindowOrganizerController", "reparentChildrenTasksHierarchyOp newParent detached=" + windowContainer2 + " hop=" + hierarchyOp);
            return 0;
        }
        if (windowContainer2.inPinnedWindowingMode()) {
            Slog.e("WindowOrganizerController", "reparentChildrenTasksHierarchyOp newParent in PIP=" + windowContainer2 + " hop=" + hierarchyOp);
            return 0;
        }
        final boolean inMultiWindowMode = windowContainer2.inMultiWindowMode();
        if (windowContainer2.asTask() != null) {
            asTaskDisplayArea = windowContainer2.asTask().getDisplayArea();
        } else {
            asTaskDisplayArea = windowContainer2.asTaskDisplayArea();
        }
        final TaskDisplayArea taskDisplayArea = asTaskDisplayArea;
        Slog.i("WindowOrganizerController", "reparentChildrenTasksHierarchyOp currentParent=" + windowContainer + " newParent=" + windowContainer2 + " hop=" + hierarchyOp);
        final ArrayList arrayList = new ArrayList();
        windowContainer.forAllTasks(new Predicate() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$reparentChildrenTasksHierarchyOp$14;
                lambda$reparentChildrenTasksHierarchyOp$14 = WindowOrganizerController.this.lambda$reparentChildrenTasksHierarchyOp$14(windowContainer, inMultiWindowMode, taskDisplayArea, hierarchyOp, windowContainer2, z, arrayList, (Task) obj);
                return lambda$reparentChildrenTasksHierarchyOp$14;
            }
        });
        int size = arrayList.size();
        int i2 = 0;
        Task task = null;
        Task task2 = null;
        while (i2 < size) {
            ArrayList arrayList2 = arrayList;
            Task task3 = (Task) arrayList2.get(i2);
            if (i >= 0) {
                addToSyncSet(i, task3);
            }
            if (transition != null) {
                transition.collect(task3);
            }
            if (windowContainer2 instanceof TaskDisplayArea) {
                if (task2 == null) {
                    task2 = ((TaskDisplayArea) windowContainer2).getRootTask(1, 0);
                }
                Task task4 = task2;
                boolean z3 = (CoreRune.MT_NEW_DEX_TASK_ORDERING && task4 != null && task4.isNewDexMode() && (activityRecord = task4.topRunningActivity()) != null && activityRecord.getLastReportedConfiguration().getMergedConfiguration().windowConfiguration.isSplitScreen()) ? false : true;
                Task rootTask = task3.getRootTask();
                if (rootTask != null && rootTask.isFullscreenRootForStageTask() && rootTask != task4 && hierarchyOp.getToTop() && z3) {
                    task3.reparent(windowContainer2, ((TaskDisplayArea) windowContainer2).getRootTaskIndex(rootTask) + i2 + 1);
                } else {
                    task3.reparent((TaskDisplayArea) windowContainer2, hierarchyOp.getToTop());
                    if (rootTask != null && rootTask.isFullscreenRootForStageTask()) {
                        task3.clearSplitLaunchRootTask();
                    }
                }
                task2 = task4;
            } else {
                task3.reparent((Task) windowContainer2, hierarchyOp.getToTop() ? Integer.MAX_VALUE : Integer.MIN_VALUE, false, "processChildrenTaskReparentHierarchyOp");
                if (hierarchyOp.getToTop() && z2) {
                    task = task3;
                }
            }
            i2++;
            arrayList = arrayList2;
        }
        if (task != null) {
            this.mService.mTaskSupervisor.mRecentTasks.adjustTopExcludeFromRecentTaskOrder(task);
        }
        if (transition == null) {
            return 2;
        }
        transition.collect(windowContainer2);
        return 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$reparentChildrenTasksHierarchyOp$14(WindowContainer windowContainer, boolean z, TaskDisplayArea taskDisplayArea, WindowContainerTransaction.HierarchyOp hierarchyOp, WindowContainer windowContainer2, boolean z2, ArrayList arrayList, Task task) {
        Slog.i("WindowOrganizerController", " Processing task=" + task);
        if (task.mCreatedByOrganizer || task.getParent() != windowContainer) {
            return false;
        }
        if (z && !task.supportsMultiWindowInDisplayArea(taskDisplayArea) && (!CoreRune.MT_NEW_DEX_LAUNCH_POLICY || !taskDisplayArea.isNewDexMode() || !task.inSplitScreenWindowingMode())) {
            Slog.e("WindowOrganizerController", "reparentChildrenTasksHierarchyOp non-resizeable task to multi window, task=" + task);
            return false;
        }
        if (!ArrayUtils.isEmpty(hierarchyOp.getActivityTypes()) && !ArrayUtils.contains(hierarchyOp.getActivityTypes(), task.getActivityType())) {
            return false;
        }
        if ((!ArrayUtils.isEmpty(hierarchyOp.getWindowingModes()) && !ArrayUtils.contains(hierarchyOp.getWindowingModes(), task.getWindowingMode())) || isLockTaskModeViolation(windowContainer2, task, z2)) {
            return false;
        }
        if (hierarchyOp.getToTop()) {
            arrayList.add(0, task);
        } else {
            arrayList.add(task);
        }
        return hierarchyOp.getReparentTopOnly() && arrayList.size() == 1;
    }

    public final int setAdjacentRootsHierarchyOp(WindowContainerTransaction.HierarchyOp hierarchyOp) {
        WindowContainer fromBinder = WindowContainer.fromBinder(hierarchyOp.getContainer());
        WindowContainer fromBinder2 = WindowContainer.fromBinder(hierarchyOp.getAdjacentRoot());
        if (fromBinder == null || fromBinder2 == null) {
            Slog.e("WindowOrganizerController", "setAdjacentRootsHierarchyOp: Cannot find container, wc=" + fromBinder + ", adj=" + fromBinder2 + ", Callers=" + Debug.getCallers(10));
            return 0;
        }
        TaskFragment asTaskFragment = fromBinder.asTaskFragment();
        TaskFragment asTaskFragment2 = fromBinder2.asTaskFragment();
        if (!asTaskFragment.mCreatedByOrganizer || !asTaskFragment2.mCreatedByOrganizer) {
            throw new IllegalArgumentException("setAdjacentRootsHierarchyOp: Not created by organizer root1=" + asTaskFragment + " root2=" + asTaskFragment2);
        }
        if (asTaskFragment.getAdjacentTaskFragment() == asTaskFragment2) {
            return 0;
        }
        asTaskFragment.setAdjacentTaskFragment(asTaskFragment2);
        return 2;
    }

    public final int clearAdjacentRootsHierarchyOp(WindowContainerTransaction.HierarchyOp hierarchyOp) {
        TaskFragment asTaskFragment = WindowContainer.fromBinder(hierarchyOp.getContainer()).asTaskFragment();
        if (!asTaskFragment.mCreatedByOrganizer) {
            throw new IllegalArgumentException("clearAdjacentRootsHierarchyOp: Not created by organizer root=" + asTaskFragment);
        }
        if (asTaskFragment.getAdjacentTaskFragment() == null) {
            return 0;
        }
        asTaskFragment.resetAdjacentTaskFragment();
        return 2;
    }

    public final void sanitizeWindowContainer(WindowContainer windowContainer) {
        if (!(windowContainer instanceof TaskFragment) && !(windowContainer instanceof DisplayArea)) {
            throw new RuntimeException("Invalid token in task fragment or displayArea transaction");
        }
    }

    public final int applyWindowContainerChange(WindowContainer windowContainer, WindowContainerTransaction.Change change, IBinder iBinder) {
        sanitizeWindowContainer(windowContainer);
        if (windowContainer.asDisplayArea() != null) {
            return applyDisplayAreaChanges(windowContainer.asDisplayArea(), change);
        }
        if (windowContainer.asTask() != null) {
            return applyTaskChanges(windowContainer.asTask(), change);
        }
        if (windowContainer.asTaskFragment() != null && windowContainer.asTaskFragment().isEmbedded()) {
            return applyTaskFragmentChanges(windowContainer.asTaskFragment(), change, iBinder);
        }
        return applyChanges(windowContainer, change);
    }

    public ITaskOrganizerController getTaskOrganizerController() {
        ActivityTaskManagerService.enforceTaskPermission("getTaskOrganizerController()");
        return this.mTaskOrganizerController;
    }

    public IDisplayAreaOrganizerController getDisplayAreaOrganizerController() {
        ActivityTaskManagerService.enforceTaskPermission("getDisplayAreaOrganizerController()");
        return this.mDisplayAreaOrganizerController;
    }

    public ITaskFragmentOrganizerController getTaskFragmentOrganizerController() {
        return this.mTaskFragmentOrganizerController;
    }

    public final BLASTSyncEngine.SyncGroup prepareSyncWithOrganizer(IWindowContainerTransactionCallback iWindowContainerTransactionCallback) {
        BLASTSyncEngine.SyncGroup prepareSyncSet = this.mService.mWindowManager.mSyncEngine.prepareSyncSet(this, "Organizer");
        this.mTransactionCallbacksByPendingSyncId.put(Integer.valueOf(prepareSyncSet.mSyncId), iWindowContainerTransactionCallback);
        return prepareSyncSet;
    }

    public int startSyncWithOrganizer(IWindowContainerTransactionCallback iWindowContainerTransactionCallback) {
        BLASTSyncEngine.SyncGroup prepareSyncWithOrganizer = prepareSyncWithOrganizer(iWindowContainerTransactionCallback);
        this.mService.mWindowManager.mSyncEngine.startSyncSet(prepareSyncWithOrganizer);
        return prepareSyncWithOrganizer.mSyncId;
    }

    public void setSyncReady(int i) {
        if (ProtoLogCache.WM_DEBUG_WINDOW_ORGANIZER_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, -930893991, 1, (String) null, new Object[]{Long.valueOf(i)});
        }
        this.mService.mWindowManager.mSyncEngine.setReady(i);
    }

    public void addToSyncSet(int i, WindowContainer windowContainer) {
        this.mService.mWindowManager.mSyncEngine.addToSyncSet(i, windowContainer);
    }

    @Override // com.android.server.wm.BLASTSyncEngine.TransactionReadyListener
    public void onTransactionReady(int i, SurfaceControl.Transaction transaction) {
        if (ProtoLogCache.WM_DEBUG_WINDOW_ORGANIZER_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, -497620140, 1, (String) null, new Object[]{Long.valueOf(i)});
        }
        try {
            ((IWindowContainerTransactionCallback) this.mTransactionCallbacksByPendingSyncId.get(Integer.valueOf(i))).onTransactionReady(i, transaction);
        } catch (RemoteException unused) {
            transaction.apply();
        }
        this.mTransactionCallbacksByPendingSyncId.remove(Integer.valueOf(i));
    }

    public void registerTransitionPlayer(ITransitionPlayer iTransitionPlayer) {
        ActivityTaskManagerService.enforceTaskPermission("registerTransitionPlayer()");
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mTransitionController.registerTransitionPlayer(iTransitionPlayer, this.mService.getProcessController(callingPid, callingUid));
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public ITransitionMetricsReporter getTransitionMetricsReporter() {
        return this.mTransitionController.mTransitionMetricsReporter;
    }

    public IBinder getApplyToken() {
        ActivityTaskManagerService.enforceTaskPermission("getApplyToken()");
        return SurfaceControl.Transaction.getDefaultApplyToken();
    }

    public static boolean configurationsAreEqualForOrganizer(Configuration configuration, Configuration configuration2) {
        if (configuration2 == null) {
            return false;
        }
        int diff = configuration.diff(configuration2);
        if ((((536870912 & diff) != 0 ? (int) configuration.windowConfiguration.diff(configuration2.windowConfiguration, true) : 0) & 77594627) == 0) {
            diff &= -536870913;
        }
        return (536886272 & diff) == 0;
    }

    public final void enforceTaskFragmentOrganizerPermission(String str, ITaskFragmentOrganizer iTaskFragmentOrganizer, WindowContainerTransaction windowContainerTransaction) {
        for (Map.Entry entry : windowContainerTransaction.getChanges().entrySet()) {
            enforceTaskFragmentConfigChangeAllowed(str, WindowContainer.fromBinder((IBinder) entry.getKey()), (WindowContainerTransaction.Change) entry.getValue(), iTaskFragmentOrganizer);
        }
        List hierarchyOps = windowContainerTransaction.getHierarchyOps();
        for (int size = hierarchyOps.size() - 1; size >= 0; size--) {
            WindowContainerTransaction.HierarchyOp hierarchyOp = (WindowContainerTransaction.HierarchyOp) hierarchyOps.get(size);
            int type = hierarchyOp.getType();
            if (type != 14) {
                if (type == 17) {
                    enforceTaskFragmentOrganized(str, hierarchyOp.getContainer(), iTaskFragmentOrganizer);
                    if (hierarchyOp.getTaskFragmentOperation() != null && hierarchyOp.getTaskFragmentOperation().getSecondaryFragmentToken() != null) {
                        enforceTaskFragmentOrganized(str, hierarchyOp.getTaskFragmentOperation().getSecondaryFragmentToken(), iTaskFragmentOrganizer);
                    }
                } else {
                    String str2 = "Permission Denial: " + str + " from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " trying to apply a hierarchy change that is not allowed for TaskFragmentOrganizer=" + iTaskFragmentOrganizer;
                    Slog.w("WindowOrganizerController", str2);
                    throw new SecurityException(str2);
                }
            }
        }
    }

    public final void enforceTaskFragmentOrganized(String str, IBinder iBinder, ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        Objects.requireNonNull(iBinder);
        TaskFragment taskFragment = (TaskFragment) this.mLaunchTaskFragments.get(iBinder);
        if (taskFragment == null || taskFragment.hasTaskFragmentOrganizer(iTaskFragmentOrganizer)) {
            return;
        }
        String str2 = "Permission Denial: " + str + " from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " trying to modify TaskFragment not belonging to the TaskFragmentOrganizer=" + iTaskFragmentOrganizer;
        Slog.w("WindowOrganizerController", str2);
        throw new SecurityException(str2);
    }

    public final void enforceTaskFragmentConfigChangeAllowed(String str, WindowContainer windowContainer, WindowContainerTransaction.Change change, ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        if (windowContainer == null) {
            Slog.e("WindowOrganizerController", "Attempt to operate on task fragment that no longer exists");
            return;
        }
        TaskFragment asTaskFragment = windowContainer.asTaskFragment();
        if (asTaskFragment == null || !asTaskFragment.hasTaskFragmentOrganizer(iTaskFragmentOrganizer)) {
            String str2 = "Permission Denial: " + str + " from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " trying to modify window container not belonging to the TaskFragmentOrganizer=" + iTaskFragmentOrganizer;
            Slog.w("WindowOrganizerController", str2);
            throw new SecurityException(str2);
        }
        int changeMask = change.getChangeMask();
        int configSetMask = change.getConfigSetMask();
        int windowSetMask = change.getWindowSetMask();
        if (changeMask == 0 && configSetMask == 0 && windowSetMask == 0 && change.getWindowingMode() >= 0) {
            return;
        }
        boolean z = CoreRune.MW_EMBED_ACTIVITY_MODE && (8388608 & windowSetMask) != 0;
        if (changeMask == 512 && configSetMask == 536870912 && (windowSetMask == 1 || z)) {
            return;
        }
        String str3 = "Permission Denial: " + str + " from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " trying to apply changes of changeMask=" + changeMask + " configSetMask=" + configSetMask + " windowSetMask=" + windowSetMask + " to TaskFragment=" + asTaskFragment + " TaskFragmentOrganizer=" + iTaskFragmentOrganizer;
        Slog.w("WindowOrganizerController", str3);
        throw new SecurityException(str3);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x00aa, code lost:
    
        if (r11 != (-1)) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00ac, code lost:
    
        r4 = r11 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00c3, code lost:
    
        if (r11 != (-1)) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void createTaskFragment(TaskFragmentCreationParams taskFragmentCreationParams, IBinder iBinder, CallerInfo callerInfo, Transition transition) {
        int indexOf;
        ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(taskFragmentCreationParams.getOwnerToken());
        ITaskFragmentOrganizer asInterface = ITaskFragmentOrganizer.Stub.asInterface(taskFragmentCreationParams.getOrganizer().asBinder());
        if (this.mLaunchTaskFragments.containsKey(taskFragmentCreationParams.getFragmentToken())) {
            sendTaskFragmentOperationFailure(asInterface, iBinder, null, 0, new IllegalArgumentException("TaskFragment token must be unique"));
            return;
        }
        if (forTokenLocked == null || forTokenLocked.getTask() == null) {
            sendTaskFragmentOperationFailure(asInterface, iBinder, null, 0, new IllegalArgumentException("Not allowed to operate with invalid ownerToken"));
            return;
        }
        if (!forTokenLocked.isResizeable()) {
            sendTaskFragmentOperationFailure(asInterface, iBinder, null, 0, new IllegalArgumentException("Not allowed to operate with non-resizable owner Activity"));
            return;
        }
        Task task = forTokenLocked.getTask();
        if (task.effectiveUid != forTokenLocked.getUid() || task.effectiveUid != callerInfo.mUid) {
            sendTaskFragmentOperationFailure(asInterface, iBinder, null, 0, new SecurityException("Not allowed to operate with the ownerToken while the root activity of the target task belong to the different app"));
            return;
        }
        if (task.inPinnedWindowingMode()) {
            sendTaskFragmentOperationFailure(asInterface, iBinder, null, 0, new IllegalArgumentException("Not allowed to create TaskFragment in PIP Task"));
            return;
        }
        TaskFragment taskFragment = new TaskFragment(this.mService, taskFragmentCreationParams.getFragmentToken(), true);
        taskFragment.setTaskFragmentOrganizer(taskFragmentCreationParams.getOrganizer(), forTokenLocked.getUid(), forTokenLocked.info.processName);
        int i = Integer.MAX_VALUE;
        if (taskFragmentCreationParams.getPairedPrimaryFragmentToken() != null) {
            indexOf = task.mChildren.indexOf(getTaskFragment(taskFragmentCreationParams.getPairedPrimaryFragmentToken()));
        } else {
            if (taskFragmentCreationParams.getPairedActivityToken() != null) {
                indexOf = task.mChildren.indexOf(ActivityRecord.forTokenLocked(taskFragmentCreationParams.getPairedActivityToken()));
            }
            task.addChild(taskFragment, i);
            taskFragment.setWindowingMode(taskFragmentCreationParams.getWindowingMode());
            if (!taskFragmentCreationParams.getInitialRelativeBounds().isEmpty()) {
                taskFragment.setRelativeEmbeddedBounds(taskFragmentCreationParams.getInitialRelativeBounds());
                taskFragment.recomputeConfiguration();
            }
            if (CoreRune.MW_EMBED_ACTIVITY_MODE) {
                taskFragment.setActivityType(forTokenLocked.getActivityType());
                taskFragment.setEmbedActivityMode(taskFragment.calculateEmbedActivityMode(task, taskFragmentCreationParams.getInitialRelativeBounds()));
            }
            this.mLaunchTaskFragments.put(taskFragmentCreationParams.getFragmentToken(), taskFragment);
            if (transition != null) {
                transition.collectExistenceChange(taskFragment);
            }
        }
    }

    public final int deleteTaskFragment(TaskFragment taskFragment, Transition transition) {
        if (transition != null) {
            transition.collectExistenceChange(taskFragment);
        }
        this.mLaunchTaskFragments.remove(taskFragment.getFragmentToken());
        taskFragment.remove(true, "deleteTaskFragment");
        return 2;
    }

    public TaskFragment getTaskFragment(IBinder iBinder) {
        return (TaskFragment) this.mLaunchTaskFragments.get(iBinder);
    }

    public void cleanUpEmbeddedTaskFragment(TaskFragment taskFragment) {
        this.mLaunchTaskFragments.remove(taskFragment.getFragmentToken());
    }

    public void sendTaskFragmentOperationFailure(ITaskFragmentOrganizer iTaskFragmentOrganizer, IBinder iBinder, TaskFragment taskFragment, int i, Throwable th) {
        if (iTaskFragmentOrganizer == null) {
            throw new IllegalArgumentException("Not allowed to operate with invalid organizer");
        }
        this.mService.mTaskFragmentOrganizerController.onTaskFragmentError(iTaskFragmentOrganizer, iBinder, taskFragment, i, th);
    }

    public final Throwable convertStartFailureToThrowable(int i, Intent intent) {
        if (i == -96) {
            return new AndroidRuntimeException("Activity could not be started for " + intent + " with error code : " + i);
        }
        if (i == -94) {
            return new SecurityException("Permission denied and not allowed to start activity " + intent);
        }
        if (i == -92 || i == -91) {
            return new ActivityNotFoundException("No Activity found to handle " + intent);
        }
        return new AndroidRuntimeException("Start activity failed with error code : " + i + " when starting " + intent);
    }

    public void onEnterSplitWithSingleStageStarted(String str) {
        this.mEnterSplitWithSingleStage.add(str);
        if (CoreRune.SAFE_DEBUG) {
            Slog.d("WindowOrganizerController", "onEnterSplitWithSingleStageStarted: c=" + this.mEnterSplitWithSingleStage + " reason=" + str);
        }
    }

    public void onEnterSplitWithSingleStageFinished(String str) {
        this.mEnterSplitWithSingleStage.remove(str);
        if (CoreRune.SAFE_DEBUG) {
            Slog.d("WindowOrganizerController", "onEnterSplitWithSingleStageFinished: c=" + this.mEnterSplitWithSingleStage + " reason=" + str);
        }
    }

    public boolean whileEnterSplitWithSingleStage() {
        return !this.mEnterSplitWithSingleStage.isEmpty();
    }

    public final boolean isEnterSplitWithSingleStage(List list) {
        ActivityOptions fromBundle;
        Task fromWindowContainerToken;
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            WindowContainerTransaction.HierarchyOp hierarchyOp = (WindowContainerTransaction.HierarchyOp) list.get(i2);
            if (hierarchyOp.getType() == 7 && (fromBundle = ActivityOptions.fromBundle(hierarchyOp.getLaunchOptions())) != null && (fromWindowContainerToken = Task.fromWindowContainerToken(fromBundle.getLaunchRootTask())) != null && fromWindowContainerToken.inSplitScreenWindowingMode()) {
                i |= fromWindowContainerToken.getStageType();
            }
        }
        return i > 0 && i < 3;
    }

    public final boolean isSplitScreenChildTask(WindowContainer windowContainer) {
        return (windowContainer.asTask() == null || windowContainer.getTaskDisplayArea() == null || windowContainer.getParent() == null || !windowContainer.getParent().inSplitScreenWindowingMode()) ? false : true;
    }
}
