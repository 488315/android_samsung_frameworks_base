package com.android.server.wm;

import android.app.WindowConfiguration;
import android.content.res.Configuration;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.ArrayMap;
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
import android.window.RemoteTransition;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.wm.BLASTSyncEngine;
import com.android.server.wm.BLASTSyncEngine.SyncGroup;
import com.android.server.wm.DexSizeCompatController;
import com.android.server.wm.SizeCompatPolicyManager;
import com.android.server.wm.TaskFragmentOrganizerController;
import com.android.server.wm.Transition;
import com.android.server.wm.TransitionController;
import com.android.server.wm.WindowOrganizerController;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntSupplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WindowOrganizerController extends IWindowOrganizerController.Stub implements BLASTSyncEngine.TransactionReadyListener {
    public final DisplayAreaOrganizerController mDisplayAreaOrganizerController;
    public final WindowManagerGlobalLock mGlobalLock;
    public final ActivityTaskManagerService mService;
    public final TaskFragmentOrganizerController mTaskFragmentOrganizerController;
    public final TaskOrganizerController mTaskOrganizerController;
    public final TransitionController mTransitionController;
    public final HashMap mTransactionCallbacksByPendingSyncId = new HashMap();
    final ArrayMap mLaunchTaskFragments = new ArrayMap();
    public final HashSet mEnterSplitWithSingleStage = new HashSet();
    public final Rect mTmpBounds0 = new Rect();
    public final Rect mTmpBounds1 = new Rect();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CallerInfo {
        public final int mPid = Binder.getCallingPid();
        public final int mUid = Binder.getCallingUid();
    }

    public WindowOrganizerController(ActivityTaskManagerService activityTaskManagerService) {
        this.mService = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
        this.mTaskOrganizerController = new TaskOrganizerController(activityTaskManagerService);
        this.mDisplayAreaOrganizerController = new DisplayAreaOrganizerController(activityTaskManagerService);
        this.mTaskFragmentOrganizerController = new TaskFragmentOrganizerController(activityTaskManagerService, this);
        this.mTransitionController = new TransitionController(activityTaskManagerService);
    }

    public static boolean configurationsAreEqualForOrganizer(Configuration configuration, Configuration configuration2) {
        if (configuration2 == null) {
            return false;
        }
        int diff = configuration.diff(configuration2);
        if ((((536870912 & diff) != 0 ? (int) configuration.windowConfiguration.diff(configuration2.windowConfiguration, true) : 0) & 10485763) == 0) {
            diff &= -536870913;
        }
        return (536886272 & diff) == 0;
    }

    public void addToSyncSet(int i, WindowContainer windowContainer) {
        this.mService.mWindowManager.mSyncEngine.addToSyncSet(i, windowContainer);
    }

    public final int applyChanges(WindowContainer windowContainer, WindowContainerTransaction.Change change) {
        int i;
        Task asTask;
        int i2;
        boolean z = true;
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION && change.hasChangeTransitMode()) {
            ChangeTransitionController changeTransitionController = this.mService.mChangeTransitController;
            changeTransitionController.getClass();
            Task asTask2 = windowContainer.asTask();
            int changeTransitMode = change.getChangeTransitMode();
            int changeTransitFlags = change.getChangeTransitFlags();
            Rect changeTransitStartBounds = change.getChangeTransitStartBounds();
            String changeTransitReason = change.getChangeTransitReason();
            if (asTask2 == null) {
                Slog.w("ChangeTransitionController", "handleWindowContainerTransaction: failed, we support Task only now!, wc=" + windowContainer + ", reason=" + changeTransitReason);
            } else {
                if (asTask2.inPinnedWindowingMode() && "pip_to_split".equals(changeTransitReason)) {
                    asTask2.mIsChangingPipToSplit = true;
                }
                Task task = asTask2.isLeafTask() ? asTask2 : asTask2.getTask(new ChangeTransitionController$$ExternalSyntheticLambda4(1));
                if (task == null || task.isChangeTransitionBlockedByCommonPolicy()) {
                    StringBuilder sb = new StringBuilder("handleWindowContainerTransaction: failed, tid #");
                    sb.append(task != null ? Integer.valueOf(task.mTaskId) : "null");
                    sb.append(", reason=");
                    sb.append(changeTransitReason);
                    Slog.w("ChangeTransitionController", sb.toString());
                } else {
                    Slog.d("ChangeTransitionController", "handleWindowContainerTransaction: requested #" + asTask2.mTaskId + ", target=" + task.mTaskId + ", startBounds=" + changeTransitStartBounds + ", reason=" + changeTransitReason);
                    if (changeTransitStartBounds == null) {
                        changeTransitStartBounds = task.getBounds();
                    }
                    changeTransitionController.requestChangeTransition(task, changeTransitMode, task.getWindowingMode(), new Rect(changeTransitStartBounds), changeTransitReason, changeTransitFlags);
                    if (CoreRune.MW_NATURAL_SWITCHING_PIP && task.inPinnedWindowingMode()) {
                        Transition.ChangeInfo findCollectingChangeInfo = changeTransitionController.findCollectingChangeInfo(task);
                        Transition transition = changeTransitionController.mTransitionController.mCollectingTransition;
                        if (transition != null && findCollectingChangeInfo != null && findCollectingChangeInfo.mChangeLeash != null) {
                            changeTransitionController.mTransitionToChangingPipTask.put(transition, task);
                            DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("addToChangingPipTaskIfPossible: tid #"), task.mTaskId, "ChangeTransitionController");
                        }
                    }
                }
            }
        }
        float f = 1.0f;
        if (change.hasChangeFreeformStashMode() || change.hasChangeFreeformStashScale()) {
            MultiTaskingController multiTaskingController = this.mService.mMultiTaskingController;
            multiTaskingController.getClass();
            Task asTask3 = windowContainer.asTask();
            if (asTask3 == null) {
                Slog.w("MultiTaskingController", "updateFocusForFreeformStash: failed, we support Task only now!, wc=" + windowContainer);
            } else {
                if (!asTask3.isLeafTask()) {
                    asTask3 = asTask3.getTask(new MultiTaskingController$$ExternalSyntheticLambda0(1));
                }
                if (asTask3 == null || !asTask3.inFreeformWindowingMode()) {
                    StringBuilder sb2 = new StringBuilder("updateFocusForFreeformStash: failed, tid #");
                    sb2.append(asTask3 != null ? Integer.valueOf(asTask3.mTaskId) : "null");
                    Slog.w("MultiTaskingController", sb2.toString());
                } else {
                    if (change.hasChangeFreeformStashScale()) {
                        float changeFreeformStashScale = change.getChangeFreeformStashScale();
                        if (asTask3.mFreeformStashScale != changeFreeformStashScale) {
                            asTask3.mFreeformStashScale = changeFreeformStashScale;
                        }
                    }
                    if (change.hasChangeFreeformStashMode() && !asTask3.isMinimized()) {
                        int changeFreeformStashMode = change.getChangeFreeformStashMode();
                        if (asTask3.mFreeformStashMode != changeFreeformStashMode) {
                            asTask3.mFreeformStashMode = changeFreeformStashMode;
                        }
                        if (changeFreeformStashMode == 2) {
                            asTask3.adjustFocusToNextFocusableTask("setStashScaled", false, true);
                        } else {
                            if (asTask3.mFreeformStashScale != 1.0f) {
                                asTask3.mFreeformStashScale = 1.0f;
                            }
                            int i3 = asTask3.mTaskId;
                            ActivityTaskManagerService activityTaskManagerService = multiTaskingController.mAtm;
                            activityTaskManagerService.setFocusedTask(i3);
                            activityTaskManagerService.mTaskSupervisor.updateTopResumedActivityIfNeeded("stash-update");
                        }
                    }
                    if (change.isForceTaskInfoChangeRequested()) {
                        Slog.d("MultiTaskingController", "updateFreeformStashState: force taskInfoChanged , t #" + asTask3.mTaskId);
                        asTask3.dispatchTaskInfoChangedIfNeeded(true);
                    }
                }
            }
        }
        if (windowContainer.asTask() != null && change.isOrganizedTaskViewTask()) {
            windowContainer.asTask().mTaskViewTaskOrganizerTaskId = change.getTaskViewTaskOrganizerTaskId();
        }
        int configSetMask = change.getConfigSetMask() & 536886272;
        int windowSetMask = change.getWindowSetMask() & 10485763;
        int windowingMode = change.getWindowingMode();
        if (((windowContainer.asTask() == null || windowContainer.getTaskDisplayArea() == null || windowContainer.getParent() == null || !windowContainer.getParent().inSplitScreenWindowingMode()) ? false : true) && windowingMode == 5) {
            windowContainer.reparent(windowContainer.getTaskDisplayArea(), Integer.MAX_VALUE);
        } else if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY && this.mService.getConfiguration().isNewDexMode() && windowContainer.asTask() != null && windowContainer.getTaskDisplayArea() != null && windowContainer.getParent() != null && windowContainer.getParent().inSplitScreenWindowingMode() && windowingMode == 1) {
            TaskDisplayArea taskDisplayArea = windowContainer.getTaskDisplayArea();
            windowContainer.reparent(taskDisplayArea, taskDisplayArea.mChildren.indexOf(windowContainer.asTask().getRootTask()) + 1);
        }
        if (CoreRune.MT_SIZE_COMPAT_POLICY) {
            SizeCompatPolicyManager sizeCompatPolicyManager = SizeCompatPolicyManager.LazyHolder.sManager;
            Task asTask4 = windowContainer.asTask();
            sizeCompatPolicyManager.getClass();
            DexSizeCompatController.DexSizeCompatPolicy compatPolicy = SizeCompatPolicyManager.getCompatPolicy(asTask4, false);
            if (compatPolicy != null) {
                int windowingMode2 = change.getWindowingMode();
                WindowConfiguration windowConfiguration = change.getConfiguration().windowConfiguration;
                Task task2 = compatPolicy.mTask;
                if (task2.mDisplayContent == null) {
                    Slog.w("SizeCompatPolicy", "ensureTransaction: DisplayContent is null. task=" + task2);
                } else {
                    if (windowingMode2 >= 0 && windowingMode2 != task2.getWindowingMode()) {
                        if (windowingMode2 != 1) {
                            if (windowingMode2 == 5) {
                                f = DexSizeCompatController.LazyHolder.sInstance.mDefaultScale;
                            } else {
                                Slog.w("SizeCompatPolicy", "ensureTransaction: Unsupported windowing mode, mode=" + WindowConfiguration.windowingModeToString(windowingMode2) + ", task=" + task2);
                                compatPolicy.mEnabled = false;
                            }
                        }
                        i2 = compatPolicy.mUserOrientation;
                    } else if (task2.inFreeformWindowingMode()) {
                        if (CoreRune.MT_SIZE_COMPAT_POLICY_DRAG && configSetMask != 0 && task2.inFreeformWindowingMode()) {
                            DexSizeCompatController.DexSizeCompatPolicy dexSizeCompatPolicy = CoreRune.MT_DEX_SIZE_COMPAT_DRAG ? compatPolicy : null;
                            if (dexSizeCompatPolicy != null) {
                                dexSizeCompatPolicy.ensureDragBounds(windowConfiguration.getBounds());
                            }
                        }
                        f = compatPolicy.mUserScale;
                        i2 = compatPolicy.mUserOrientation;
                    } else {
                        i2 = 0;
                    }
                    compatPolicy.setUserScale(f);
                    compatPolicy.mUserOrientation = i2;
                }
            }
        }
        if (configSetMask != 0) {
            if (windowingMode <= -1 || windowingMode == windowContainer.getWindowingMode()) {
                Configuration configuration = new Configuration(windowContainer.getRequestedOverrideConfiguration());
                configuration.setTo(change.getConfiguration(), configSetMask, windowSetMask);
                if (windowContainer.getWindowingMode() == 6 && (change.getConfigSetMask() & 1024) != 0 && (asTask = windowContainer.asTask()) != null) {
                    if (configuration.screenWidthDp == 0 || configuration.screenHeightDp == 0) {
                        asTask.mOffsetYForInsets = 0;
                        asTask.mOffsetXForInsets = 0;
                    } else {
                        Rect requestedOverrideBounds = windowContainer.getRequestedOverrideBounds();
                        Rect bounds = change.getConfiguration().windowConfiguration.getBounds();
                        if (requestedOverrideBounds.width() == bounds.width() && requestedOverrideBounds.height() == bounds.height()) {
                            asTask.mOffsetXForInsets = requestedOverrideBounds.left - bounds.left;
                            asTask.mOffsetYForInsets = requestedOverrideBounds.top - bounds.top;
                        } else {
                            asTask.mOffsetYForInsets = 0;
                            asTask.mOffsetXForInsets = 0;
                        }
                    }
                }
                windowContainer.onRequestedOverrideConfigurationChanged(configuration);
            } else {
                windowContainer.getRequestedOverrideConfiguration().setTo(change.getConfiguration(), configSetMask, windowSetMask);
            }
            i = (windowSetMask == 0 || !windowContainer.isEmbedded()) ? 1 : 3;
        } else {
            i = 0;
        }
        if ((change.getChangeMask() & 1) != 0 && windowContainer.setFocusable(change.getFocusable())) {
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
        if (windowingMode == 5 && change.getChangeTransitMode() == 4 && windowContainer.asTask() != null) {
            windowContainer.asTask().mSkipLayoutTask = true;
        } else {
            z = false;
        }
        int requestedOverrideWindowingMode = windowContainer.getRequestedOverrideWindowingMode();
        windowContainer.setWindowingMode(windowingMode);
        if (z) {
            windowContainer.asTask().mSkipLayoutTask = false;
        }
        return requestedOverrideWindowingMode != windowContainer.getWindowingMode() ? i | 2 : i;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:581:0x0b14, code lost:
    
        if (r5 != (-1)) goto L506;
     */
    /* JADX WARN: Code restructure failed: missing block: B:598:0x053d, code lost:
    
        if (validateTaskFragment((com.android.server.wm.TaskFragment) r25.mLaunchTaskFragments.get(r4), r15, r32, r33) == false) goto L244;
     */
    /* JADX WARN: Removed duplicated region for block: B:569:0x0b42  */
    /* JADX WARN: Removed duplicated region for block: B:574:0x0b5b  */
    /* JADX WARN: Removed duplicated region for block: B:577:0x0b78  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0cc4  */
    /* JADX WARN: Removed duplicated region for block: B:68:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int applyHierarchyOp(final android.window.WindowContainerTransaction.HierarchyOp r26, int r27, int r28, com.android.server.wm.Transition r29, boolean r30, final com.android.server.wm.WindowOrganizerController.CallerInfo r31, final android.os.IBinder r32, android.window.ITaskFragmentOrganizer r33, com.android.server.wm.Transition r34, boolean r35) {
        /*
            Method dump skipped, instructions count: 3664
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowOrganizerController.applyHierarchyOp(android.window.WindowContainerTransaction$HierarchyOp, int, int, com.android.server.wm.Transition, boolean, com.android.server.wm.WindowOrganizerController$CallerInfo, android.os.IBinder, android.window.ITaskFragmentOrganizer, com.android.server.wm.Transition, boolean):int");
    }

    /* JADX WARN: Finally extract failed */
    public final int applySyncTransaction(final WindowContainerTransaction windowContainerTransaction, IWindowContainerTransactionCallback iWindowContainerTransactionCallback) {
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
                        activityManagerPerformance.onActivityRelaunchLocked(activityRecord);
                    }
                    if (iWindowContainerTransactionCallback == null) {
                        applyTransaction(windowContainerTransaction, -1, (Transition) null, callerInfo, (Transition) null);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -1;
                    }
                    BLASTSyncEngine bLASTSyncEngine = this.mService.mWindowManager.mSyncEngine;
                    bLASTSyncEngine.getClass();
                    int i = bLASTSyncEngine.mNextSyncId;
                    bLASTSyncEngine.mNextSyncId = i + 1;
                    final BLASTSyncEngine.SyncGroup syncGroup = bLASTSyncEngine.new SyncGroup(this, i, "Organizer");
                    this.mTransactionCallbacksByPendingSyncId.put(Integer.valueOf(syncGroup.mSyncId), iWindowContainerTransactionCallback);
                    final int i2 = syncGroup.mSyncId;
                    if (this.mTransitionController.isShellTransitionsEnabled()) {
                        this.mTransitionController.startLegacySyncOrQueue(syncGroup, new WindowOrganizerController$$ExternalSyntheticLambda1(this, windowContainerTransaction, i2, callerInfo));
                    } else if (this.mService.mWindowManager.mSyncEngine.hasActiveSync()) {
                        BLASTSyncEngine bLASTSyncEngine2 = this.mService.mWindowManager.mSyncEngine;
                        Runnable runnable = new Runnable() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                WindowOrganizerController windowOrganizerController = WindowOrganizerController.this;
                                windowOrganizerController.mService.mWindowManager.mSyncEngine.startSyncSet(syncGroup, 5000L, false);
                            }
                        };
                        Runnable runnable2 = new Runnable() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                WindowOrganizerController windowOrganizerController = WindowOrganizerController.this;
                                WindowContainerTransaction windowContainerTransaction2 = windowContainerTransaction;
                                int i3 = i2;
                                windowOrganizerController.applyTransaction(windowContainerTransaction2, i3, (Transition) null, callerInfo, (Transition) null);
                                windowOrganizerController.setSyncReady(i3);
                            }
                        };
                        bLASTSyncEngine2.getClass();
                        if (CoreRune.FW_SHELL_TRANSITION_LOG) {
                            ActivityManagerService$$ExternalSyntheticOutline0.m(6, new StringBuilder("queueSyncSet, caller="), "BLASTSyncEngine");
                        }
                        BLASTSyncEngine.PendingSyncSet pendingSyncSet = new BLASTSyncEngine.PendingSyncSet();
                        pendingSyncSet.mStartSync = runnable;
                        pendingSyncSet.mApplySync = runnable2;
                        bLASTSyncEngine2.mPendingSyncSets.add(pendingSyncSet);
                    } else {
                        this.mService.mWindowManager.mSyncEngine.startSyncSet(syncGroup, 5000L, false);
                        applyTransaction(windowContainerTransaction, i2, (Transition) null, callerInfo, (Transition) null);
                        setSyncReady(i2);
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return i2;
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

    public final void applyTaskFragmentTransactionLocked(final WindowContainerTransaction windowContainerTransaction, int i, boolean z, final RemoteTransition remoteTransition) {
        int i2;
        int i3;
        ITaskFragmentOrganizer taskFragmentOrganizer = windowContainerTransaction.getTaskFragmentOrganizer();
        Objects.requireNonNull(taskFragmentOrganizer);
        for (Map.Entry entry : windowContainerTransaction.getChanges().entrySet()) {
            WindowContainer fromBinder = WindowContainer.fromBinder((IBinder) entry.getKey());
            WindowContainerTransaction.Change change = (WindowContainerTransaction.Change) entry.getValue();
            if (fromBinder == null) {
                Slog.e("WindowOrganizerController", "Attempt to operate on task fragment that no longer exists");
            } else {
                TaskFragment asTaskFragment = fromBinder.asTaskFragment();
                if (asTaskFragment == null || asTaskFragment.mTaskFragmentOrganizer == null || !taskFragmentOrganizer.asBinder().equals(asTaskFragment.mTaskFragmentOrganizer.asBinder())) {
                    String str = "Permission Denial: applyTaskFragmentTransaction() from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " trying to modify window container not belonging to the TaskFragmentOrganizer=" + taskFragmentOrganizer;
                    Slog.w("WindowOrganizerController", str);
                    throw new SecurityException(str);
                }
                int changeMask = change.getChangeMask();
                int configSetMask = change.getConfigSetMask();
                int windowSetMask = change.getWindowSetMask();
                int i4 = this.mTaskFragmentOrganizerController.isSystemOrganizer(taskFragmentOrganizer.asBinder()) ? changeMask & (-138) : changeMask;
                if ((i4 & 512) == 0 || (536870912 & configSetMask) == 0 || (windowSetMask & 1) == 0) {
                    i2 = windowSetMask;
                    i3 = configSetMask;
                } else {
                    i4 &= -513;
                    i3 = (-536870913) & configSetMask;
                    i2 = windowSetMask & (-2);
                }
                if (CoreRune.MW_EMBED_ACTIVITY_MODE && (8388608 & i2) != 0) {
                    i2 &= -8388609;
                }
                if (i4 != 0 || i3 != 0 || i2 != 0) {
                    StringBuilder sb = new StringBuilder("Permission Denial: applyTaskFragmentTransaction() from pid=");
                    sb.append(Binder.getCallingPid());
                    sb.append(", uid=");
                    sb.append(Binder.getCallingUid());
                    sb.append(" trying to apply changes of changeMask=");
                    sb.append(changeMask);
                    sb.append(" configSetMask=");
                    ServiceKeeper$$ExternalSyntheticOutline0.m(configSetMask, windowSetMask, " windowSetMask=", " to TaskFragment=", sb);
                    sb.append(asTaskFragment);
                    sb.append(" TaskFragmentOrganizer=");
                    sb.append(taskFragmentOrganizer);
                    String sb2 = sb.toString();
                    Slog.w("WindowOrganizerController", sb2);
                    throw new SecurityException(sb2);
                }
            }
        }
        List hierarchyOps = windowContainerTransaction.getHierarchyOps();
        for (int size = hierarchyOps.size() - 1; size >= 0; size--) {
            WindowContainerTransaction.HierarchyOp hierarchyOp = (WindowContainerTransaction.HierarchyOp) hierarchyOps.get(size);
            int type = hierarchyOp.getType();
            if (type != 14) {
                if (type != 17) {
                    String str2 = "Permission Denial: applyTaskFragmentTransaction() from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " trying to apply a hierarchy change that is not allowed for TaskFragmentOrganizer=" + taskFragmentOrganizer;
                    Slog.w("WindowOrganizerController", str2);
                    throw new SecurityException(str2);
                }
                enforceTaskFragmentOrganized(hierarchyOp.getContainer(), taskFragmentOrganizer);
                if (hierarchyOp.getTaskFragmentOperation() != null && hierarchyOp.getTaskFragmentOperation().getSecondaryFragmentToken() != null) {
                    enforceTaskFragmentOrganized(hierarchyOp.getTaskFragmentOperation().getSecondaryFragmentToken(), taskFragmentOrganizer);
                }
            }
        }
        if (remoteTransition != null && !this.mTaskFragmentOrganizerController.isSystemOrganizer(windowContainerTransaction.getTaskFragmentOrganizer().asBinder())) {
            throw new SecurityException("Only a system organizer is allowed to use remote transition!");
        }
        final CallerInfo callerInfo = new CallerInfo();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (this.mTransitionController.getTransitionPlayer() == null) {
                applyTransaction(windowContainerTransaction, -1, (Transition) null, callerInfo, (Transition) null);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return;
            }
            if (!this.mService.mWindowManager.mSyncEngine.hasActiveSync() || z) {
                final Transition transition = new Transition(i, 0, this.mTransitionController, this.mService.mWindowManager.mSyncEngine);
                this.mTransitionController.startCollectOrQueue(transition, new TransitionController.OnStartCollect() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda4
                    @Override // com.android.server.wm.TransitionController.OnStartCollect
                    public final void onCollectStarted(boolean z2) {
                        WindowContainerTransaction windowContainerTransaction2 = windowContainerTransaction;
                        RemoteTransition remoteTransition2 = remoteTransition;
                        WindowOrganizerController windowOrganizerController = WindowOrganizerController.this;
                        Transition transition2 = transition;
                        if (z2 && !windowOrganizerController.mTaskFragmentOrganizerController.isValidTransaction(windowContainerTransaction2)) {
                            transition2.abort();
                        } else if (windowOrganizerController.applyTransaction(windowContainerTransaction2, -1, transition2, callerInfo, z2) == 0 && transition2.mParticipants.isEmpty()) {
                            transition2.abort();
                        } else {
                            windowOrganizerController.mTransitionController.requestStartTransition(transition2, null, remoteTransition2, null);
                            windowOrganizerController.setAllReadyIfNeeded(transition2, windowContainerTransaction2);
                        }
                    }
                });
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } else {
                Transition transition2 = this.mTransitionController.mCollectingTransition;
                if (transition2 == null && ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[3]) {
                    ProtoLogImpl_54989576.w(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 6110791601270766802L, 0, null, null);
                }
                applyTransaction(windowContainerTransaction, -1, transition2, callerInfo, (Transition) null);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:131:0x02fc A[Catch: all -> 0x01ab, TryCatch #5 {all -> 0x01ab, blocks: (B:301:0x0132, B:302:0x013e, B:307:0x014a, B:310:0x0168, B:315:0x0177, B:317:0x0183, B:320:0x018a, B:322:0x0161, B:46:0x01ad, B:50:0x01c2, B:53:0x01ca, B:55:0x01dc, B:64:0x01e9, B:66:0x01ef, B:68:0x01ff, B:70:0x020b, B:72:0x0210, B:73:0x0214, B:90:0x0230, B:91:0x0232, B:93:0x0238, B:95:0x023e, B:99:0x024f, B:103:0x0277, B:104:0x0261, B:108:0x0270, B:113:0x028a, B:114:0x02a8, B:116:0x02b9, B:117:0x02c2, B:119:0x02c6, B:121:0x02ca, B:59:0x02d7, B:128:0x02f1, B:129:0x02f6, B:131:0x02fc, B:138:0x0304, B:141:0x030b, B:142:0x0317, B:148:0x033d, B:151:0x0345, B:153:0x034b, B:155:0x035a, B:157:0x0364, B:159:0x036e, B:161:0x0374, B:163:0x037a, B:173:0x0387, B:174:0x038f, B:176:0x0395, B:178:0x03c4, B:180:0x03c8, B:182:0x03ce, B:277:0x039b, B:279:0x03a1, B:281:0x03a7, B:283:0x03b1, B:285:0x03b9), top: B:300:0x0132 }] */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0329  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x033d A[Catch: all -> 0x01ab, TRY_ENTER, TryCatch #5 {all -> 0x01ab, blocks: (B:301:0x0132, B:302:0x013e, B:307:0x014a, B:310:0x0168, B:315:0x0177, B:317:0x0183, B:320:0x018a, B:322:0x0161, B:46:0x01ad, B:50:0x01c2, B:53:0x01ca, B:55:0x01dc, B:64:0x01e9, B:66:0x01ef, B:68:0x01ff, B:70:0x020b, B:72:0x0210, B:73:0x0214, B:90:0x0230, B:91:0x0232, B:93:0x0238, B:95:0x023e, B:99:0x024f, B:103:0x0277, B:104:0x0261, B:108:0x0270, B:113:0x028a, B:114:0x02a8, B:116:0x02b9, B:117:0x02c2, B:119:0x02c6, B:121:0x02ca, B:59:0x02d7, B:128:0x02f1, B:129:0x02f6, B:131:0x02fc, B:138:0x0304, B:141:0x030b, B:142:0x0317, B:148:0x033d, B:151:0x0345, B:153:0x034b, B:155:0x035a, B:157:0x0364, B:159:0x036e, B:161:0x0374, B:163:0x037a, B:173:0x0387, B:174:0x038f, B:176:0x0395, B:178:0x03c4, B:180:0x03c8, B:182:0x03ce, B:277:0x039b, B:279:0x03a1, B:281:0x03a7, B:283:0x03b1, B:285:0x03b9), top: B:300:0x0132 }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x007c A[Catch: all -> 0x0087, TryCatch #2 {all -> 0x0087, blocks: (B:12:0x0075, B:14:0x007c, B:17:0x008d, B:19:0x0093, B:21:0x00a7, B:22:0x00b4, B:24:0x00ba, B:26:0x00c7, B:28:0x00cc, B:30:0x00d2, B:32:0x00f6, B:34:0x00fc, B:35:0x00ff, B:37:0x0103, B:39:0x0109, B:40:0x0118, B:43:0x011e, B:44:0x0124, B:304:0x0144, B:309:0x0150, B:292:0x0322, B:145:0x032a, B:323:0x00c0), top: B:11:0x0075 }] */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0395 A[Catch: all -> 0x01ab, TryCatch #5 {all -> 0x01ab, blocks: (B:301:0x0132, B:302:0x013e, B:307:0x014a, B:310:0x0168, B:315:0x0177, B:317:0x0183, B:320:0x018a, B:322:0x0161, B:46:0x01ad, B:50:0x01c2, B:53:0x01ca, B:55:0x01dc, B:64:0x01e9, B:66:0x01ef, B:68:0x01ff, B:70:0x020b, B:72:0x0210, B:73:0x0214, B:90:0x0230, B:91:0x0232, B:93:0x0238, B:95:0x023e, B:99:0x024f, B:103:0x0277, B:104:0x0261, B:108:0x0270, B:113:0x028a, B:114:0x02a8, B:116:0x02b9, B:117:0x02c2, B:119:0x02c6, B:121:0x02ca, B:59:0x02d7, B:128:0x02f1, B:129:0x02f6, B:131:0x02fc, B:138:0x0304, B:141:0x030b, B:142:0x0317, B:148:0x033d, B:151:0x0345, B:153:0x034b, B:155:0x035a, B:157:0x0364, B:159:0x036e, B:161:0x0374, B:163:0x037a, B:173:0x0387, B:174:0x038f, B:176:0x0395, B:178:0x03c4, B:180:0x03c8, B:182:0x03ce, B:277:0x039b, B:279:0x03a1, B:281:0x03a7, B:283:0x03b1, B:285:0x03b9), top: B:300:0x0132 }] */
    /* JADX WARN: Removed duplicated region for block: B:180:0x03c8 A[Catch: all -> 0x01ab, TryCatch #5 {all -> 0x01ab, blocks: (B:301:0x0132, B:302:0x013e, B:307:0x014a, B:310:0x0168, B:315:0x0177, B:317:0x0183, B:320:0x018a, B:322:0x0161, B:46:0x01ad, B:50:0x01c2, B:53:0x01ca, B:55:0x01dc, B:64:0x01e9, B:66:0x01ef, B:68:0x01ff, B:70:0x020b, B:72:0x0210, B:73:0x0214, B:90:0x0230, B:91:0x0232, B:93:0x0238, B:95:0x023e, B:99:0x024f, B:103:0x0277, B:104:0x0261, B:108:0x0270, B:113:0x028a, B:114:0x02a8, B:116:0x02b9, B:117:0x02c2, B:119:0x02c6, B:121:0x02ca, B:59:0x02d7, B:128:0x02f1, B:129:0x02f6, B:131:0x02fc, B:138:0x0304, B:141:0x030b, B:142:0x0317, B:148:0x033d, B:151:0x0345, B:153:0x034b, B:155:0x035a, B:157:0x0364, B:159:0x036e, B:161:0x0374, B:163:0x037a, B:173:0x0387, B:174:0x038f, B:176:0x0395, B:178:0x03c4, B:180:0x03c8, B:182:0x03ce, B:277:0x039b, B:279:0x03a1, B:281:0x03a7, B:283:0x03b1, B:285:0x03b9), top: B:300:0x0132 }] */
    /* JADX WARN: Removed duplicated region for block: B:190:0x045c A[Catch: all -> 0x043b, TryCatch #4 {all -> 0x043b, blocks: (B:270:0x041f, B:188:0x0445, B:190:0x045c, B:192:0x0466, B:193:0x0469, B:194:0x0475, B:196:0x047b, B:198:0x048d, B:206:0x0495, B:209:0x04a5, B:214:0x04ae, B:216:0x04b4, B:218:0x04ba, B:220:0x04c6, B:224:0x04cb, B:225:0x04e1, B:228:0x04e2, B:230:0x04f8, B:231:0x0507, B:233:0x04fc, B:202:0x050c, B:241:0x0524, B:243:0x0528, B:245:0x0575, B:250:0x053b, B:252:0x053f, B:254:0x0547, B:256:0x0555, B:258:0x0559, B:260:0x056e, B:261:0x0560), top: B:269:0x041f }] */
    /* JADX WARN: Removed duplicated region for block: B:196:0x047b A[Catch: all -> 0x043b, TryCatch #4 {all -> 0x043b, blocks: (B:270:0x041f, B:188:0x0445, B:190:0x045c, B:192:0x0466, B:193:0x0469, B:194:0x0475, B:196:0x047b, B:198:0x048d, B:206:0x0495, B:209:0x04a5, B:214:0x04ae, B:216:0x04b4, B:218:0x04ba, B:220:0x04c6, B:224:0x04cb, B:225:0x04e1, B:228:0x04e2, B:230:0x04f8, B:231:0x0507, B:233:0x04fc, B:202:0x050c, B:241:0x0524, B:243:0x0528, B:245:0x0575, B:250:0x053b, B:252:0x053f, B:254:0x0547, B:256:0x0555, B:258:0x0559, B:260:0x056e, B:261:0x0560), top: B:269:0x041f }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0093 A[Catch: all -> 0x0087, TryCatch #2 {all -> 0x0087, blocks: (B:12:0x0075, B:14:0x007c, B:17:0x008d, B:19:0x0093, B:21:0x00a7, B:22:0x00b4, B:24:0x00ba, B:26:0x00c7, B:28:0x00cc, B:30:0x00d2, B:32:0x00f6, B:34:0x00fc, B:35:0x00ff, B:37:0x0103, B:39:0x0109, B:40:0x0118, B:43:0x011e, B:44:0x0124, B:304:0x0144, B:309:0x0150, B:292:0x0322, B:145:0x032a, B:323:0x00c0), top: B:11:0x0075 }] */
    /* JADX WARN: Removed duplicated region for block: B:243:0x0528 A[Catch: all -> 0x043b, TryCatch #4 {all -> 0x043b, blocks: (B:270:0x041f, B:188:0x0445, B:190:0x045c, B:192:0x0466, B:193:0x0469, B:194:0x0475, B:196:0x047b, B:198:0x048d, B:206:0x0495, B:209:0x04a5, B:214:0x04ae, B:216:0x04b4, B:218:0x04ba, B:220:0x04c6, B:224:0x04cb, B:225:0x04e1, B:228:0x04e2, B:230:0x04f8, B:231:0x0507, B:233:0x04fc, B:202:0x050c, B:241:0x0524, B:243:0x0528, B:245:0x0575, B:250:0x053b, B:252:0x053f, B:254:0x0547, B:256:0x0555, B:258:0x0559, B:260:0x056e, B:261:0x0560), top: B:269:0x041f }] */
    /* JADX WARN: Removed duplicated region for block: B:245:0x0575 A[Catch: all -> 0x043b, TRY_LEAVE, TryCatch #4 {all -> 0x043b, blocks: (B:270:0x041f, B:188:0x0445, B:190:0x045c, B:192:0x0466, B:193:0x0469, B:194:0x0475, B:196:0x047b, B:198:0x048d, B:206:0x0495, B:209:0x04a5, B:214:0x04ae, B:216:0x04b4, B:218:0x04ba, B:220:0x04c6, B:224:0x04cb, B:225:0x04e1, B:228:0x04e2, B:230:0x04f8, B:231:0x0507, B:233:0x04fc, B:202:0x050c, B:241:0x0524, B:243:0x0528, B:245:0x0575, B:250:0x053b, B:252:0x053f, B:254:0x0547, B:256:0x0555, B:258:0x0559, B:260:0x056e, B:261:0x0560), top: B:269:0x041f }] */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0580  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00ba A[Catch: all -> 0x0087, TryCatch #2 {all -> 0x0087, blocks: (B:12:0x0075, B:14:0x007c, B:17:0x008d, B:19:0x0093, B:21:0x00a7, B:22:0x00b4, B:24:0x00ba, B:26:0x00c7, B:28:0x00cc, B:30:0x00d2, B:32:0x00f6, B:34:0x00fc, B:35:0x00ff, B:37:0x0103, B:39:0x0109, B:40:0x0118, B:43:0x011e, B:44:0x0124, B:304:0x0144, B:309:0x0150, B:292:0x0322, B:145:0x032a, B:323:0x00c0), top: B:11:0x0075 }] */
    /* JADX WARN: Removed duplicated region for block: B:250:0x053b A[Catch: all -> 0x043b, TryCatch #4 {all -> 0x043b, blocks: (B:270:0x041f, B:188:0x0445, B:190:0x045c, B:192:0x0466, B:193:0x0469, B:194:0x0475, B:196:0x047b, B:198:0x048d, B:206:0x0495, B:209:0x04a5, B:214:0x04ae, B:216:0x04b4, B:218:0x04ba, B:220:0x04c6, B:224:0x04cb, B:225:0x04e1, B:228:0x04e2, B:230:0x04f8, B:231:0x0507, B:233:0x04fc, B:202:0x050c, B:241:0x0524, B:243:0x0528, B:245:0x0575, B:250:0x053b, B:252:0x053f, B:254:0x0547, B:256:0x0555, B:258:0x0559, B:260:0x056e, B:261:0x0560), top: B:269:0x041f }] */
    /* JADX WARN: Removed duplicated region for block: B:264:0x03dd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:283:0x03b1 A[Catch: all -> 0x01ab, TryCatch #5 {all -> 0x01ab, blocks: (B:301:0x0132, B:302:0x013e, B:307:0x014a, B:310:0x0168, B:315:0x0177, B:317:0x0183, B:320:0x018a, B:322:0x0161, B:46:0x01ad, B:50:0x01c2, B:53:0x01ca, B:55:0x01dc, B:64:0x01e9, B:66:0x01ef, B:68:0x01ff, B:70:0x020b, B:72:0x0210, B:73:0x0214, B:90:0x0230, B:91:0x0232, B:93:0x0238, B:95:0x023e, B:99:0x024f, B:103:0x0277, B:104:0x0261, B:108:0x0270, B:113:0x028a, B:114:0x02a8, B:116:0x02b9, B:117:0x02c2, B:119:0x02c6, B:121:0x02ca, B:59:0x02d7, B:128:0x02f1, B:129:0x02f6, B:131:0x02fc, B:138:0x0304, B:141:0x030b, B:142:0x0317, B:148:0x033d, B:151:0x0345, B:153:0x034b, B:155:0x035a, B:157:0x0364, B:159:0x036e, B:161:0x0374, B:163:0x037a, B:173:0x0387, B:174:0x038f, B:176:0x0395, B:178:0x03c4, B:180:0x03c8, B:182:0x03ce, B:277:0x039b, B:279:0x03a1, B:281:0x03a7, B:283:0x03b1, B:285:0x03b9), top: B:300:0x0132 }] */
    /* JADX WARN: Removed duplicated region for block: B:285:0x03b9 A[Catch: all -> 0x01ab, TryCatch #5 {all -> 0x01ab, blocks: (B:301:0x0132, B:302:0x013e, B:307:0x014a, B:310:0x0168, B:315:0x0177, B:317:0x0183, B:320:0x018a, B:322:0x0161, B:46:0x01ad, B:50:0x01c2, B:53:0x01ca, B:55:0x01dc, B:64:0x01e9, B:66:0x01ef, B:68:0x01ff, B:70:0x020b, B:72:0x0210, B:73:0x0214, B:90:0x0230, B:91:0x0232, B:93:0x0238, B:95:0x023e, B:99:0x024f, B:103:0x0277, B:104:0x0261, B:108:0x0270, B:113:0x028a, B:114:0x02a8, B:116:0x02b9, B:117:0x02c2, B:119:0x02c6, B:121:0x02ca, B:59:0x02d7, B:128:0x02f1, B:129:0x02f6, B:131:0x02fc, B:138:0x0304, B:141:0x030b, B:142:0x0317, B:148:0x033d, B:151:0x0345, B:153:0x034b, B:155:0x035a, B:157:0x0364, B:159:0x036e, B:161:0x0374, B:163:0x037a, B:173:0x0387, B:174:0x038f, B:176:0x0395, B:178:0x03c4, B:180:0x03c8, B:182:0x03ce, B:277:0x039b, B:279:0x03a1, B:281:0x03a7, B:283:0x03b1, B:285:0x03b9), top: B:300:0x0132 }] */
    /* JADX WARN: Removed duplicated region for block: B:286:0x03b6  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x031d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00cc A[Catch: all -> 0x0087, TryCatch #2 {all -> 0x0087, blocks: (B:12:0x0075, B:14:0x007c, B:17:0x008d, B:19:0x0093, B:21:0x00a7, B:22:0x00b4, B:24:0x00ba, B:26:0x00c7, B:28:0x00cc, B:30:0x00d2, B:32:0x00f6, B:34:0x00fc, B:35:0x00ff, B:37:0x0103, B:39:0x0109, B:40:0x0118, B:43:0x011e, B:44:0x0124, B:304:0x0144, B:309:0x0150, B:292:0x0322, B:145:0x032a, B:323:0x00c0), top: B:11:0x0075 }] */
    /* JADX WARN: Removed duplicated region for block: B:298:0x0317 A[EDGE_INSN: B:298:0x0317->B:142:0x0317 BREAK  A[LOOP:2: B:129:0x02f6->B:136:0x02f6], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:299:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x0132 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:324:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00f6 A[Catch: all -> 0x0087, TryCatch #2 {all -> 0x0087, blocks: (B:12:0x0075, B:14:0x007c, B:17:0x008d, B:19:0x0093, B:21:0x00a7, B:22:0x00b4, B:24:0x00ba, B:26:0x00c7, B:28:0x00cc, B:30:0x00d2, B:32:0x00f6, B:34:0x00fc, B:35:0x00ff, B:37:0x0103, B:39:0x0109, B:40:0x0118, B:43:0x011e, B:44:0x0124, B:304:0x0144, B:309:0x0150, B:292:0x0322, B:145:0x032a, B:323:0x00c0), top: B:11:0x0075 }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0103 A[Catch: all -> 0x0087, TryCatch #2 {all -> 0x0087, blocks: (B:12:0x0075, B:14:0x007c, B:17:0x008d, B:19:0x0093, B:21:0x00a7, B:22:0x00b4, B:24:0x00ba, B:26:0x00c7, B:28:0x00cc, B:30:0x00d2, B:32:0x00f6, B:34:0x00fc, B:35:0x00ff, B:37:0x0103, B:39:0x0109, B:40:0x0118, B:43:0x011e, B:44:0x0124, B:304:0x0144, B:309:0x0150, B:292:0x0322, B:145:0x032a, B:323:0x00c0), top: B:11:0x0075 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x011c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01ca A[Catch: all -> 0x01ab, TRY_ENTER, TryCatch #5 {all -> 0x01ab, blocks: (B:301:0x0132, B:302:0x013e, B:307:0x014a, B:310:0x0168, B:315:0x0177, B:317:0x0183, B:320:0x018a, B:322:0x0161, B:46:0x01ad, B:50:0x01c2, B:53:0x01ca, B:55:0x01dc, B:64:0x01e9, B:66:0x01ef, B:68:0x01ff, B:70:0x020b, B:72:0x0210, B:73:0x0214, B:90:0x0230, B:91:0x0232, B:93:0x0238, B:95:0x023e, B:99:0x024f, B:103:0x0277, B:104:0x0261, B:108:0x0270, B:113:0x028a, B:114:0x02a8, B:116:0x02b9, B:117:0x02c2, B:119:0x02c6, B:121:0x02ca, B:59:0x02d7, B:128:0x02f1, B:129:0x02f6, B:131:0x02fc, B:138:0x0304, B:141:0x030b, B:142:0x0317, B:148:0x033d, B:151:0x0345, B:153:0x034b, B:155:0x035a, B:157:0x0364, B:159:0x036e, B:161:0x0374, B:163:0x037a, B:173:0x0387, B:174:0x038f, B:176:0x0395, B:178:0x03c4, B:180:0x03c8, B:182:0x03ce, B:277:0x039b, B:279:0x03a1, B:281:0x03a7, B:283:0x03b1, B:285:0x03b9), top: B:300:0x0132 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x059d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int applyTransaction(android.window.WindowContainerTransaction r28, int r29, com.android.server.wm.Transition r30, com.android.server.wm.WindowOrganizerController.CallerInfo r31, com.android.server.wm.Transition r32) {
        /*
            Method dump skipped, instructions count: 1462
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowOrganizerController.applyTransaction(android.window.WindowContainerTransaction, int, com.android.server.wm.Transition, com.android.server.wm.WindowOrganizerController$CallerInfo, com.android.server.wm.Transition):int");
    }

    public final int applyTransaction(WindowContainerTransaction windowContainerTransaction, int i, Transition transition, CallerInfo callerInfo, boolean z) {
        if (!z) {
            return applyTransaction(windowContainerTransaction, i, transition, callerInfo, (Transition) null);
        }
        try {
            return applyTransaction(windowContainerTransaction, i, transition, callerInfo, (Transition) null);
        } catch (RuntimeException e) {
            Slog.e("WindowOrganizerController", "Failed to execute deferred applyTransaction", e);
            return 0;
        }
    }

    public final void applyTransaction(WindowContainerTransaction windowContainerTransaction) {
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
                    applyTransaction(windowContainerTransaction, -1, (Transition) null, callerInfo, (Transition) null);
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

    /* JADX WARN: Code restructure failed: missing block: B:104:0x023e, code lost:
    
        if (r0.height() == r13.height()) goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0202, code lost:
    
        if (r0 != false) goto L105;
     */
    /* JADX WARN: Removed duplicated region for block: B:106:0x024c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int applyWindowContainerChange(com.android.server.wm.WindowContainer r11, final android.window.WindowContainerTransaction.Change r12, android.os.IBinder r13) {
        /*
            Method dump skipped, instructions count: 613
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowOrganizerController.applyWindowContainerChange(com.android.server.wm.WindowContainer, android.window.WindowContainerTransaction$Change, android.os.IBinder):int");
    }

    public final void enforceTaskFragmentOrganized(IBinder iBinder, ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        Objects.requireNonNull(iBinder);
        TaskFragment taskFragment = (TaskFragment) this.mLaunchTaskFragments.get(iBinder);
        if (taskFragment != null) {
            if (taskFragment.mTaskFragmentOrganizer == null || !iTaskFragmentOrganizer.asBinder().equals(taskFragment.mTaskFragmentOrganizer.asBinder())) {
                String str = "Permission Denial: applyTaskFragmentTransaction() from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " trying to modify TaskFragment not belonging to the TaskFragmentOrganizer=" + iTaskFragmentOrganizer;
                Slog.w("WindowOrganizerController", str);
                throw new SecurityException(str);
            }
        }
    }

    public final void finishAllTransitions(IBinder iBinder, WindowContainerTransaction windowContainerTransaction, WindowContainerTransaction windowContainerTransaction2) {
        ArrayList transferTransitionTokens;
        if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX) {
            ActivityTaskManagerService.enforceTaskPermission("finishAllTransitions()");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    if (windowContainerTransaction2 != null) {
                        try {
                            transferTransitionTokens = windowContainerTransaction2.getTransferTransitionTokens();
                        } catch (Throwable th) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    } else {
                        transferTransitionTokens = null;
                    }
                    ArrayList mergedTransitionTokens = windowContainerTransaction2 != null ? windowContainerTransaction2.getMergedTransitionTokens() : null;
                    Iterator it = transferTransitionTokens.iterator();
                    while (it.hasNext()) {
                        finishTransition((IBinder) it.next(), null);
                    }
                    finishTransition(iBinder, windowContainerTransaction);
                    Iterator it2 = mergedTransitionTokens.iterator();
                    while (it2.hasNext()) {
                        finishTransition((IBinder) it2.next(), null);
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void finishTransition(IBinder iBinder, WindowContainerTransaction windowContainerTransaction) {
        ActivityTaskManagerService.enforceTaskPermission("finishTransition()");
        CallerInfo callerInfo = new CallerInfo();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Transition fromBinder = Transition.fromBinder(iBinder);
                    if (windowContainerTransaction != null) {
                        this.mTransitionController.mFinishingTransition = fromBinder;
                        applyTransaction(windowContainerTransaction, -1, (Transition) null, callerInfo, fromBinder);
                    }
                    this.mTransitionController.finishTransition(fromBinder);
                    this.mTransitionController.mFinishingTransition = null;
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

    public final IBinder getApplyToken() {
        ActivityTaskManagerService.enforceTaskPermission("getApplyToken()");
        return SurfaceControl.Transaction.getDefaultApplyToken();
    }

    public final IDisplayAreaOrganizerController getDisplayAreaOrganizerController() {
        ActivityTaskManagerService.enforceTaskPermission("getDisplayAreaOrganizerController()");
        return this.mDisplayAreaOrganizerController;
    }

    public final ITaskFragmentOrganizerController getTaskFragmentOrganizerController() {
        return this.mTaskFragmentOrganizerController;
    }

    public final ITaskOrganizerController getTaskOrganizerController() {
        ActivityTaskManagerService.enforceTaskPermission("getTaskOrganizerController()");
        return this.mTaskOrganizerController;
    }

    public final ITransitionMetricsReporter getTransitionMetricsReporter() {
        return this.mTransitionController.mTransitionMetricsReporter;
    }

    public final boolean isLockTaskModeViolation(WindowContainer windowContainer, Task task, boolean z) {
        if (!z || windowContainer == null || task == null) {
            return false;
        }
        LockTaskController lockTaskController = this.mService.mLockTaskController;
        boolean isLockTaskModeViolation = lockTaskController.isLockTaskModeViolation(task, false);
        if (!isLockTaskModeViolation && windowContainer.asTask() != null) {
            isLockTaskModeViolation = lockTaskController.isLockTaskModeViolation(windowContainer.asTask(), false);
        }
        if (isLockTaskModeViolation) {
            Slog.w("WindowOrganizerController", "Can't support the operation since in lock task mode violation.  Task: " + task + " Parent : " + windowContainer);
        }
        return isLockTaskModeViolation;
    }

    public final void onEnterSplitWithSingleStageFinished(String str) {
        this.mEnterSplitWithSingleStage.remove(str);
    }

    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (RuntimeException e) {
            ActivityTaskManagerService.logAndRethrowRuntimeExceptionOnTransact(e, "WindowOrganizerController");
            throw null;
        }
    }

    @Override // com.android.server.wm.BLASTSyncEngine.TransactionReadyListener
    public final void onTransactionReady(SurfaceControl.Transaction transaction, int i) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, 6552038620140878489L, 1, null, Long.valueOf(i));
        }
        try {
            ((IWindowContainerTransactionCallback) this.mTransactionCallbacksByPendingSyncId.get(Integer.valueOf(i))).onTransactionReady(i, transaction);
        } catch (RemoteException e) {
            Slog.e("WindowOrganizerController", "Failed to notify transaction (" + i + ") ready", e);
            transaction.apply();
        }
        this.mTransactionCallbacksByPendingSyncId.remove(Integer.valueOf(i));
    }

    public final void registerTransitionPlayer(ITransitionPlayer iTransitionPlayer) {
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

    /* JADX WARN: Code restructure failed: missing block: B:92:0x01c9, code lost:
    
        if (r23 != false) goto L83;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int reparentChildrenTasksHierarchyOp(final android.window.WindowContainerTransaction.HierarchyOp r19, com.android.server.wm.Transition r20, int r21, final boolean r22, boolean r23) {
        /*
            Method dump skipped, instructions count: 538
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowOrganizerController.reparentChildrenTasksHierarchyOp(android.window.WindowContainerTransaction$HierarchyOp, com.android.server.wm.Transition, int, boolean, boolean):int");
    }

    public final int sanitizeAndApplyHierarchyOp(WindowContainer windowContainer, WindowContainerTransaction.HierarchyOp hierarchyOp) {
        ActivityRecord activityRecord;
        Task asTask = windowContainer.asTask();
        if (asTask == null) {
            throw new IllegalArgumentException("Invalid container in hierarchy op");
        }
        DisplayContent displayContent = asTask.getDisplayContent();
        if (displayContent == null) {
            Slog.w("WindowOrganizerController", "Container is no longer attached: " + asTask);
            return 0;
        }
        if (!hierarchyOp.isReparent()) {
            if (hierarchyOp.getToTop() && asTask.isRootTask()) {
                Task.enableEnterPipOnTaskSwitch(null, Task.findEnterPipOnTaskSwitchCandidate(asTask.getDisplayArea().getTopRootTask()), null, asTask);
            }
            asTask.getParent().positionChildAt(hierarchyOp.getToTop() ? Integer.MAX_VALUE : Integer.MIN_VALUE, asTask, hierarchyOp.includingParents());
            TaskDisplayArea defaultTaskDisplayArea = displayContent.getDefaultTaskDisplayArea();
            if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE && (activityRecord = displayContent.mFocusedApp) != null && activityRecord.mIsFlexPanel && defaultTaskDisplayArea != null) {
                Task task = defaultTaskDisplayArea.mRootMainStageTask;
                Task task2 = defaultTaskDisplayArea.mRootSideStageTask;
                ActivityRecord activityRecord2 = task2 != null ? task2.topRunningActivity(false) : null;
                if (task != null && activityRecord2 != null && activityRecord2.mIsFlexPanel) {
                    this.mService.setFocusedTask(task.mTaskId);
                }
            }
            if (!hierarchyOp.getToTop() || defaultTaskDisplayArea == null || !asTask.inSplitScreenWindowingMode() || defaultTaskDisplayArea.getTopRootTaskInStageType(asTask.getStageType()) == null) {
                return 2;
            }
            Transition transition = this.mTransitionController.mFinishingTransition;
            if (transition != null && transition.isInTransientHide(asTask.getRootTask())) {
                return 2;
            }
            this.mService.setFocusedTask(defaultTaskDisplayArea.getTopRootTaskInStageType(asTask.getStageType()).mTaskId);
            return 2;
        }
        if (!asTask.isRootTask() && !asTask.getParent().asTask().mCreatedByOrganizer) {
            throw new RuntimeException("Reparenting leaf Tasks is not supported now. " + asTask);
        }
        WindowContainer defaultTaskDisplayArea2 = hierarchyOp.getNewParent() == null ? displayContent.getDefaultTaskDisplayArea() : WindowContainer.fromBinder(hierarchyOp.getNewParent());
        if (defaultTaskDisplayArea2 == null) {
            Slog.e("WindowOrganizerController", "Can't resolve parent window from token");
            return 0;
        }
        if (asTask.getParent() == defaultTaskDisplayArea2) {
            if (defaultTaskDisplayArea2 instanceof TaskDisplayArea) {
                defaultTaskDisplayArea2 = asTask.getRootTask();
            }
            asTask.getDisplayArea().positionChildAt(hierarchyOp.getToTop() ? Integer.MAX_VALUE : Integer.MIN_VALUE, (Task) defaultTaskDisplayArea2, false);
            return 2;
        }
        if (defaultTaskDisplayArea2.asTaskDisplayArea() != null) {
            asTask.reparent(defaultTaskDisplayArea2.asTaskDisplayArea(), hierarchyOp.getToTop());
            return 2;
        }
        if (defaultTaskDisplayArea2.asTask() == null) {
            throw new RuntimeException("Can only reparent task to another task or taskDisplayArea, but not " + defaultTaskDisplayArea2);
        }
        if (defaultTaskDisplayArea2.inMultiWindowMode() && asTask.isLeafTask()) {
            if (defaultTaskDisplayArea2.inPinnedWindowingMode()) {
                Slog.w("WindowOrganizerController", "Can't support moving a task to another PIP window... newParent=" + defaultTaskDisplayArea2 + " task=" + asTask);
                return 0;
            }
            if (!asTask.supportsMultiWindowInDisplayArea(defaultTaskDisplayArea2.asTask().getDisplayArea(), false)) {
                Slog.w("WindowOrganizerController", "Can't support task that doesn't support multi-window mode in multi-window mode... newParent=" + defaultTaskDisplayArea2 + " task=" + asTask);
                return 0;
            }
        }
        asTask.reparent(hierarchyOp.getToTop() ? Integer.MAX_VALUE : Integer.MIN_VALUE, (Task) defaultTaskDisplayArea2, "sanitizeAndApplyHierarchyOp", false);
        return 2;
    }

    public final void sendTaskFragmentOperationFailure(ITaskFragmentOrganizer iTaskFragmentOrganizer, IBinder iBinder, TaskFragment taskFragment, int i, Throwable th) {
        if (iTaskFragmentOrganizer == null) {
            throw new IllegalArgumentException("Not allowed to operate with invalid organizer");
        }
        TaskFragmentOrganizerController taskFragmentOrganizerController = this.mService.mTaskFragmentOrganizerController;
        if (taskFragment != null) {
            taskFragmentOrganizerController.getClass();
            if (taskFragment.mTaskFragmentVanishedSent) {
                return;
            }
        }
        taskFragmentOrganizerController.validateAndGetState(iTaskFragmentOrganizer);
        Slog.w("TaskFragmentOrganizerController", "onTaskFragmentError ", th);
        taskFragmentOrganizerController.addPendingEvent(new TaskFragmentOrganizerController.PendingTaskFragmentEvent(4, iTaskFragmentOrganizer, taskFragment, null, iBinder, th, null, null, null, i));
        taskFragmentOrganizerController.mAtmService.mWindowManager.mWindowPlacerLocked.requestTraversal();
    }

    public final void setAllReadyIfNeeded(Transition transition, WindowContainerTransaction windowContainerTransaction) {
        int i = 0;
        while (true) {
            if (i >= windowContainerTransaction.getHierarchyOps().size()) {
                break;
            }
            if (((WindowContainerTransaction.HierarchyOp) windowContainerTransaction.getHierarchyOps().get(i)).getType() != 5) {
                i++;
            } else if (!this.mService.mRootWindowContainer.allPausedActivitiesComplete()) {
                return;
            }
        }
        for (int i2 = 0; i2 < windowContainerTransaction.getHierarchyOps().size(); i2++) {
            WindowContainerTransaction.HierarchyOp hierarchyOp = (WindowContainerTransaction.HierarchyOp) windowContainerTransaction.getHierarchyOps().get(i2);
            if (hierarchyOp.getType() == 17 && hierarchyOp.getTaskFragmentOperation().getOpType() == 0) {
                TaskFragment taskFragment = (TaskFragment) this.mLaunchTaskFragments.get(hierarchyOp.getTaskFragmentOperation().getTaskFragmentCreationParams().getFragmentToken());
                if (taskFragment != null && !taskFragment.isReadyToTransit()) {
                    return;
                }
            }
        }
        transition.setAllReady();
    }

    public void setSyncReady(int i) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, 433446585990132440L, 1, null, Long.valueOf(i));
        }
        this.mService.mWindowManager.mSyncEngine.setReady(i, true);
    }

    /* JADX WARN: Finally extract failed */
    public final int startLegacyTransition(int i, RemoteAnimationAdapter remoteAnimationAdapter, IWindowContainerTransactionCallback iWindowContainerTransactionCallback, WindowContainerTransaction windowContainerTransaction) {
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
                    if (displayContent.mAppTransition.isTransitionSet()) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -1;
                    }
                    remoteAnimationAdapter.setCallingPidUid(callerInfo.mPid, callerInfo.mUid);
                    displayContent.prepareAppTransition(i, 0);
                    displayContent.mAppTransition.overridePendingAppTransitionRemote(remoteAnimationAdapter, true, false);
                    int startSyncWithOrganizer = startSyncWithOrganizer(iWindowContainerTransactionCallback);
                    applyTransaction(windowContainerTransaction, startSyncWithOrganizer, (Transition) null, callerInfo, (Transition) null);
                    setSyncReady(startSyncWithOrganizer);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return startSyncWithOrganizer;
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

    public final IBinder startNewTransition(int i, WindowContainerTransaction windowContainerTransaction) {
        return startTransition(i, null, windowContainerTransaction);
    }

    public int startSyncWithOrganizer(IWindowContainerTransactionCallback iWindowContainerTransactionCallback) {
        BLASTSyncEngine bLASTSyncEngine = this.mService.mWindowManager.mSyncEngine;
        bLASTSyncEngine.getClass();
        int i = bLASTSyncEngine.mNextSyncId;
        bLASTSyncEngine.mNextSyncId = i + 1;
        BLASTSyncEngine.SyncGroup syncGroup = bLASTSyncEngine.new SyncGroup(this, i, "Organizer");
        HashMap hashMap = this.mTransactionCallbacksByPendingSyncId;
        int i2 = syncGroup.mSyncId;
        hashMap.put(Integer.valueOf(i2), iWindowContainerTransactionCallback);
        this.mService.mWindowManager.mSyncEngine.startSyncSet(syncGroup, 5000L, false);
        return i2;
    }

    /* JADX WARN: Finally extract failed */
    public final Transition.Token startTransition(int i, IBinder iBinder, WindowContainerTransaction windowContainerTransaction) {
        Transition.ReadyCondition readyCondition;
        Transition.Token token;
        ActivityTaskManagerService.enforceTaskPermission("startTransition()");
        final CallerInfo callerInfo = new CallerInfo();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    final Transition fromBinder = Transition.fromBinder(iBinder);
                    if (this.mTransitionController.getTransitionPlayer() == null && fromBinder == null) {
                        Slog.w("WindowOrganizerController", "Using shell transitions API for legacy transitions.");
                        if (windowContainerTransaction == null) {
                            throw new IllegalArgumentException("Can't use legacy transitions in compatibility mode with no WCT.");
                        }
                        applyTransaction(windowContainerTransaction, -1, (Transition) null, callerInfo, (Transition) null);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    final WindowContainerTransaction windowContainerTransaction2 = windowContainerTransaction != null ? windowContainerTransaction : new WindowContainerTransaction();
                    if (fromBinder != null) {
                        if (windowContainerTransaction != null) {
                            readyCondition = new Transition.ReadyCondition("start WCT applied");
                            fromBinder.mReadyTracker.add(readyCondition);
                        } else {
                            readyCondition = null;
                        }
                        if (fromBinder.isCollecting() || fromBinder.mForcePlaying) {
                            fromBinder.mLogger.mStartWCT = windowContainerTransaction2;
                            if (fromBinder.shouldApplyOnDisplayThread()) {
                                final Transition.ReadyCondition readyCondition2 = readyCondition;
                                this.mService.mH.post(new Runnable() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda6
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        WindowOrganizerController windowOrganizerController = WindowOrganizerController.this;
                                        Transition transition = fromBinder;
                                        WindowContainerTransaction windowContainerTransaction3 = windowContainerTransaction2;
                                        WindowOrganizerController.CallerInfo callerInfo2 = callerInfo;
                                        Transition.ReadyCondition readyCondition3 = readyCondition2;
                                        WindowManagerGlobalLock windowManagerGlobalLock2 = windowOrganizerController.mService.mGlobalLock;
                                        WindowManagerService.boostPriorityForLockedSection();
                                        synchronized (windowManagerGlobalLock2) {
                                            try {
                                                transition.start();
                                                windowOrganizerController.applyTransaction(windowContainerTransaction3, -1, transition, callerInfo2, (Transition) null);
                                                if (readyCondition3 != null) {
                                                    readyCondition3.meet();
                                                }
                                            } catch (Throwable th) {
                                                WindowManagerService.resetPriorityAfterLockedSection();
                                                throw th;
                                            }
                                        }
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                    }
                                });
                            } else {
                                fromBinder.start();
                                applyTransaction(windowContainerTransaction2, -1, fromBinder, callerInfo, (Transition) null);
                                if (readyCondition != null) {
                                    readyCondition.meet();
                                }
                            }
                            token = fromBinder.mToken;
                        } else {
                            Slog.e("WindowOrganizerController", "Trying to start a transition that isn't collecting. This probably means Shell took too long to respond to a request. WM State may be incorrect now, please file a bug");
                            applyTransaction(windowContainerTransaction2, -1, (Transition) null, callerInfo, (Transition) null);
                            if (readyCondition != null) {
                                readyCondition.meet();
                            }
                            token = fromBinder.mToken;
                        }
                    } else {
                        if (i < 0) {
                            throw new IllegalArgumentException("Can't create transition with no type");
                        }
                        final Transition.ReadyCondition readyCondition3 = new Transition.ReadyCondition("start WCT applied");
                        final boolean z = (windowContainerTransaction == null || (CoreRune.MW_SHELL_TRANSITION && windowContainerTransaction.avoidReady())) ? false : true;
                        final Transition transition = new Transition(i, 0, this.mTransitionController, this.mService.mWindowManager.mSyncEngine);
                        transition.mReadyTracker.add(readyCondition3);
                        transition.calcParallelCollectType(windowContainerTransaction2);
                        this.mTransitionController.startCollectOrQueue(transition, new TransitionController.OnStartCollect() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda5
                            @Override // com.android.server.wm.TransitionController.OnStartCollect
                            public final void onCollectStarted(boolean z2) {
                                WindowContainerTransaction windowContainerTransaction3 = windowContainerTransaction2;
                                WindowOrganizerController windowOrganizerController = WindowOrganizerController.this;
                                windowOrganizerController.getClass();
                                Transition transition2 = transition;
                                transition2.start();
                                transition2.mLogger.mStartWCT = windowContainerTransaction3;
                                windowOrganizerController.applyTransaction(windowContainerTransaction3, -1, transition2, callerInfo, z2);
                                readyCondition3.meet();
                                if (z) {
                                    windowOrganizerController.setAllReadyIfNeeded(transition2, windowContainerTransaction3);
                                }
                            }
                        });
                        token = transition.mToken;
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

    public final void startTransition(IBinder iBinder, WindowContainerTransaction windowContainerTransaction) {
        startTransition(-1, iBinder, windowContainerTransaction);
    }

    public final void unregisterTransitionPlayer(ITransitionPlayer iTransitionPlayer) {
        ActivityTaskManagerService.enforceTaskPermission("unregisterTransitionPlayer()");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mTransitionController.unregisterTransitionPlayer(iTransitionPlayer);
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

    public final boolean validateTaskFragment(TaskFragment taskFragment, int i, IBinder iBinder, ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        if (taskFragment == null || !taskFragment.isAttached()) {
            sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment, i, new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Not allowed to apply operation on invalid fragment tokens opType=")));
            return false;
        }
        if (!taskFragment.isEmbeddedTaskFragmentInPip() || (i == 1 && taskFragment.getTopNonFinishingActivity(true, true) == null)) {
            return true;
        }
        sendTaskFragmentOperationFailure(iTaskFragmentOrganizer, iBinder, taskFragment, i, new IllegalArgumentException("Not allowed to apply operation on PIP TaskFragment"));
        return false;
    }

    public final int waitAsyncStart(final IntSupplier intSupplier) {
        final Integer[] numArr = {null};
        (Looper.myLooper() == this.mService.mH.getLooper() ? this.mService.mWindowManager.mAnimationHandler : this.mService.mH).post(new Runnable() { // from class: com.android.server.wm.WindowOrganizerController$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                WindowOrganizerController windowOrganizerController = WindowOrganizerController.this;
                Integer[] numArr2 = numArr;
                IntSupplier intSupplier2 = intSupplier;
                windowOrganizerController.getClass();
                try {
                    numArr2[0] = Integer.valueOf(intSupplier2.getAsInt());
                } catch (Throwable th) {
                    numArr2[0] = -96;
                    Slog.w("WindowOrganizerController", th);
                }
                WindowManagerGlobalLock windowManagerGlobalLock = windowOrganizerController.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        windowOrganizerController.mGlobalLock.notifyAll();
                    } catch (Throwable th2) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th2;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        });
        while (true) {
            Integer num = numArr[0];
            if (num != null) {
                return num.intValue();
            }
            try {
                this.mGlobalLock.wait();
            } catch (InterruptedException unused) {
            }
        }
    }
}
