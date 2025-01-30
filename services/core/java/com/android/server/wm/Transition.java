package com.android.server.wm;

import android.app.ActivityManager;
import android.app.IApplicationThread;
import android.app.PictureInPictureParams;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.IInstalld;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.window.ScreenCapture;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.policy.TransitionAnimation;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.display.DisplayPowerController2;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.BLASTSyncEngine;
import com.android.server.wm.DisplayArea;
import com.android.server.wm.Transition;
import com.android.server.wm.TransitionController;
import com.android.server.wm.WindowContainer;
import com.android.server.wm.utils.InsetUtils;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.samsung.android.rune.CoreRune;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class Transition implements BLASTSyncEngine.TransactionReadyListener {
    public int mAnimationTrack;
    public ChangeTransitionController mChangeTransitController;
    public final TransitionController mController;
    public int mFlags;
    public boolean mForcePlaying;
    public boolean mHasBlackSnapshot;
    public boolean mIsFailedStartTransition;
    public boolean mIsPlayerEnabled;
    public ArrayList mLateTransientLaunches;
    public final TransitionController.Logger mLogger;
    public boolean mNeedsCleanupLegacyAppTransitionPending;
    public TransitionInfo.AnimationOptions mOverrideOptions;
    public int mOverrideTransitionType;
    public int mParallelCollectType;
    public IApplicationThread mRemoteAnimApp;
    public boolean mSkipMergeAnimation;
    public long mStatusBarTransitionDelay;
    public final BLASTSyncEngine mSyncEngine;
    public ArrayList mTargets;
    public final Token mToken;
    public ArrayList mTransientHideTasks;
    public final int mType;
    public int mSyncId = -1;
    public SurfaceControl.Transaction mStartTransaction = null;
    public SurfaceControl.Transaction mFinishTransaction = null;
    public SurfaceControl.Transaction mCleanupTransaction = null;
    public final ArrayMap mChanges = new ArrayMap();
    public final ArraySet mParticipants = new ArraySet();
    public final ArrayList mTargetDisplays = new ArrayList();
    public final ArrayList mOnTopTasksStart = new ArrayList();
    public final ArrayList mOnTopTasksAtReady = new ArrayList();
    public final ArraySet mVisibleAtTransitionEndTokens = new ArraySet();
    public ArrayMap mTransientLaunches = null;
    public IRemoteCallback mClientAnimationStartCallback = null;
    public IRemoteCallback mClientAnimationFinishCallback = null;
    public int mState = -1;
    public final ReadyTracker mReadyTracker = new ReadyTracker();
    public int mRecentsDisplayId = -1;
    public boolean mCanPipOnFinish = true;
    public boolean mIsSeamlessRotation = false;
    public IContainerFreezer mContainerFreezer = null;
    public final SurfaceControl.Transaction mTmpTransaction = new SurfaceControl.Transaction();
    public boolean mPriorVisibilityMightBeDirty = false;

    public interface IContainerFreezer {
        void cleanUp(SurfaceControl.Transaction transaction);

        boolean freeze(WindowContainer windowContainer, Rect rect);
    }

    public static int reduceMode(int i) {
        if (i == 3) {
            return 1;
        }
        if (i != 4) {
            return i;
        }
        return 2;
    }

    public Transition(int i, int i2, TransitionController transitionController, BLASTSyncEngine bLASTSyncEngine) {
        TransitionController.Logger logger = new TransitionController.Logger();
        this.mLogger = logger;
        this.mForcePlaying = false;
        this.mIsPlayerEnabled = true;
        this.mOverrideTransitionType = 0;
        this.mParallelCollectType = 0;
        this.mAnimationTrack = 0;
        this.mType = i;
        this.mFlags = i2;
        this.mController = transitionController;
        this.mSyncEngine = bLASTSyncEngine;
        this.mToken = new Token(this);
        logger.mCreateWallTimeMs = System.currentTimeMillis();
        logger.mCreateTimeNs = SystemClock.elapsedRealtimeNanos();
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
            this.mChangeTransitController = transitionController.mAtm.mChangeTransitController;
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
            Slog.d(StartingSurfaceController.TAG, "Transition is created, t=" + this + ", caller=" + Debug.getCallers(5));
        }
    }

    public static Transition fromBinder(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        try {
            return (Transition) ((Token) iBinder).mTransition.get();
        } catch (ClassCastException e) {
            Slog.w("Transition", "Invalid transition token: " + iBinder, e);
            return null;
        }
    }

    public IBinder getToken() {
        return this.mToken;
    }

    public void addFlag(int i) {
        this.mFlags = i | this.mFlags;
    }

    public void calcParallelCollectType(WindowContainerTransaction windowContainerTransaction) {
        Bundle launchOptions;
        for (int i = 0; i < windowContainerTransaction.getHierarchyOps().size(); i++) {
            WindowContainerTransaction.HierarchyOp hierarchyOp = (WindowContainerTransaction.HierarchyOp) windowContainerTransaction.getHierarchyOps().get(i);
            if (hierarchyOp.getType() == 7 && (launchOptions = hierarchyOp.getLaunchOptions()) != null && !launchOptions.isEmpty() && launchOptions.getBoolean("android.activity.transientLaunch")) {
                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
                    if (ProtoLogCache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS, 40419095, 0, "Starting a Recents transition which can be parallel.", (Object[]) null);
                    }
                } else if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -186693085, 0, (String) null, (Object[]) null);
                }
                this.mParallelCollectType = 2;
            }
        }
    }

    public void setTransientLaunch(ActivityRecord activityRecord, final Task task) {
        if (this.mTransientLaunches == null) {
            this.mTransientLaunches = new ArrayMap();
            this.mTransientHideTasks = new ArrayList();
        }
        this.mTransientLaunches.put(activityRecord, task);
        setTransientLaunchToChanges(activityRecord);
        if (task != null) {
            final Task rootTask = activityRecord.getRootTask();
            task.getParent().forAllTasks(new Predicate() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda14
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$setTransientLaunch$0;
                    lambda$setTransientLaunch$0 = Transition.this.lambda$setTransientLaunch$0(rootTask, task, (Task) obj);
                    return lambda$setTransientLaunch$0;
                }
            });
            for (int size = this.mChanges.size() - 1; size >= 0; size--) {
                updateTransientFlags((ChangeInfo) this.mChanges.valueAt(size));
            }
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && activityRecord.getDisplayContent() != null) {
            ArrayList arrayList = activityRecord.getDisplayContent().mTransientLaunchOverlayTokens;
            for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                ((TransientLaunchOverlayToken) arrayList.get(size2)).setVisibility(true);
            }
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
            if (ProtoLogCache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS, 233615010, 1, "Transition %d: Set %s as transient-launch, caller=%s", new Object[]{Long.valueOf(this.mSyncId), String.valueOf(activityRecord), String.valueOf(Debug.getCallers(3))});
                return;
            }
            return;
        }
        if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -779535710, 1, (String) null, new Object[]{Long.valueOf(this.mSyncId), String.valueOf(activityRecord)});
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setTransientLaunch$0(Task task, Task task2, Task task3) {
        if (task3.isVisibleRequested() && !task3.isAlwaysOnTop() && !task3.getWindowConfiguration().tasksAreFloating()) {
            if (task3.isRootTask() && task3 != task) {
                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG && ProtoLogCache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS, 1920933991, 1, "Transition %d: Set %s as transient-hidden-task, caller=%s", new Object[]{Long.valueOf(this.mSyncId), String.valueOf(task3), String.valueOf(Debug.getCallers(3))});
                }
                this.mTransientHideTasks.add(task3);
            }
            if (task3.isLeafTask()) {
                collect(task3);
            }
        }
        return task3 == task2;
    }

    public boolean isInTransientHide(WindowContainer windowContainer) {
        ArrayList arrayList = this.mTransientHideTasks;
        if (arrayList == null) {
            return false;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            Task task = (Task) this.mTransientHideTasks.get(size);
            if (windowContainer == task || windowContainer.isDescendantOf(task)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInKeyguardTransition() {
        return (this.mFlags & 14592) != 0;
    }

    public boolean canApplyDim(Task task) {
        if (this.mTransientLaunches == null) {
            return true;
        }
        Dimmer dimmer = task.getDimmer();
        WindowContainer host = dimmer != null ? dimmer.getHost() : null;
        if (host == null) {
            return false;
        }
        if (isInTransientHide(host)) {
            return true;
        }
        for (int size = this.mTransientLaunches.size() - 1; size >= 0; size--) {
            Task task2 = ((ActivityRecord) this.mTransientLaunches.keyAt(size)).getTask();
            if (task2 != null && task2.canAffectSystemUiFlags()) {
                return false;
            }
        }
        return true;
    }

    public boolean hasTransientLaunch() {
        ArrayMap arrayMap = this.mTransientLaunches;
        return (arrayMap == null || arrayMap.isEmpty()) ? false : true;
    }

    public boolean isTransientLaunch(ActivityRecord activityRecord) {
        ArrayMap arrayMap = this.mTransientLaunches;
        return arrayMap != null && arrayMap.containsKey(activityRecord);
    }

    public Task getTransientLaunchRestoreTarget(WindowContainer windowContainer) {
        if (this.mTransientLaunches == null) {
            return null;
        }
        for (int i = 0; i < this.mTransientLaunches.size(); i++) {
            if (((ActivityRecord) this.mTransientLaunches.keyAt(i)).isDescendantOf(windowContainer)) {
                return (Task) this.mTransientLaunches.valueAt(i);
            }
        }
        return null;
    }

    public void setLateTransientLaunch(ActivityRecord activityRecord) {
        ArrayList arrayList = this.mLateTransientLaunches;
        if (arrayList == null) {
            this.mLateTransientLaunches = new ArrayList();
        } else if (arrayList.contains(activityRecord)) {
            return;
        }
        Slog.d(StartingSurfaceController.TAG, "setLateTransientLaunch, Starting a late transient transition which can be parallel, r=" + activityRecord + ", caller=" + Debug.getCallers(5));
        this.mParallelCollectType = 3;
        this.mLateTransientLaunches.add(activityRecord);
        activityRecord.mIsLateTransientLaunch = true;
    }

    public boolean hasLateTransientLaunch() {
        ArrayList arrayList = this.mLateTransientLaunches;
        return (arrayList == null || arrayList.isEmpty()) ? false : true;
    }

    public boolean isLateTransientLaunch(ActivityRecord activityRecord) {
        ArrayList arrayList = this.mLateTransientLaunches;
        return arrayList != null && arrayList.contains(activityRecord);
    }

    public static boolean isTransientLaunchOverlay(WindowContainer windowContainer) {
        return windowContainer.asTransientLaunchOverlay() != null;
    }

    public boolean isOnDisplay(DisplayContent displayContent) {
        return this.mTargetDisplays.contains(displayContent);
    }

    public void setSeamlessRotation(WindowContainer windowContainer) {
        ChangeInfo changeInfo = (ChangeInfo) this.mChanges.get(windowContainer);
        if (changeInfo == null) {
            return;
        }
        changeInfo.mFlags |= 1;
        onSeamlessRotating(windowContainer.getDisplayContent());
    }

    public void onSeamlessRotating(DisplayContent displayContent) {
        if (this.mSyncEngine.getSyncSet(this.mSyncId).mSyncMethod == 1) {
            return;
        }
        if (this.mContainerFreezer == null) {
            this.mContainerFreezer = new ScreenshotFreezer();
        }
        WindowState topFullscreenOpaqueWindow = displayContent.getDisplayPolicy().getTopFullscreenOpaqueWindow();
        if (topFullscreenOpaqueWindow != null) {
            this.mIsSeamlessRotation = true;
            topFullscreenOpaqueWindow.mSyncMethodOverride = 1;
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
                if (ProtoLogCache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS, 945789237, 0, "Override sync-method for %s because seamless rotating, caller=%s", new Object[]{String.valueOf(topFullscreenOpaqueWindow.getName()), String.valueOf(Debug.getCallers(5))});
                    return;
                }
                return;
            }
            if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 2004282287, 0, (String) null, new Object[]{String.valueOf(topFullscreenOpaqueWindow.getName())});
            }
        }
    }

    public void setHasBlackSnapshot() {
        this.mHasBlackSnapshot = true;
    }

    public final void setTransientLaunchToChanges(WindowContainer windowContainer) {
        while (windowContainer != null && this.mChanges.containsKey(windowContainer)) {
            if (windowContainer.asTask() == null && windowContainer.asActivityRecord() == null) {
                return;
            }
            ((ChangeInfo) this.mChanges.get(windowContainer)).mFlags |= 2;
            windowContainer = windowContainer.getParent();
        }
    }

    public int getSyncId() {
        return this.mSyncId;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public SurfaceControl.Transaction getStartTransaction() {
        return this.mStartTransaction;
    }

    public SurfaceControl.Transaction getFinishTransaction() {
        return this.mFinishTransaction;
    }

    public boolean isCollecting() {
        int i = this.mState;
        return i == 0 || i == 1;
    }

    public boolean isAborted() {
        return this.mState == 3;
    }

    public boolean isStarted() {
        return this.mState == 1;
    }

    public void startCollecting(long j) {
        if (this.mState != -1) {
            throw new IllegalStateException("Attempting to re-use a transition");
        }
        this.mState = 0;
        int startSyncSet = this.mSyncEngine.startSyncSet(this, j, "Transition", this.mParallelCollectType != 0);
        this.mSyncId = startSyncSet;
        this.mSyncEngine.setSyncMethod(startSyncSet, TransitionController.SYNC_METHOD);
        TransitionController.Logger logger = this.mLogger;
        logger.mSyncId = this.mSyncId;
        logger.mCollectTimeNs = SystemClock.elapsedRealtimeNanos();
    }

    public void start() {
        int i = this.mState;
        if (i < 0) {
            throw new IllegalStateException("Can't start Transition which isn't collecting.");
        }
        if (i >= 1) {
            Slog.w("Transition", "Transition already started id=" + this.mSyncId + " state=" + this.mState);
            return;
        }
        this.mState = 1;
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
            if (ProtoLogCache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS, -1736322568, 1, "Starting Transition %d, caller=%s", new Object[]{Long.valueOf(this.mSyncId), String.valueOf(Debug.getCallers(3))});
            }
        } else if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 996960396, 1, (String) null, new Object[]{Long.valueOf(this.mSyncId)});
        }
        applyReady();
        this.mLogger.mStartTimeNs = SystemClock.elapsedRealtimeNanos();
        this.mController.updateAnimatingState(this.mTmpTransaction);
        SurfaceControl.mergeToGlobalTransaction(this.mTmpTransaction);
    }

    public void collect(WindowContainer windowContainer) {
        collect(windowContainer, false);
    }

    public void collect(WindowContainer windowContainer, boolean z) {
        if (this.mState < 0) {
            throw new IllegalStateException("Transition hasn't started collecting.");
        }
        if (isCollecting()) {
            if (isWallpaper(windowContainer) && !isOnDisplay(windowContainer.mDisplayContent)) {
                Slog.d("Transition", "don't collect wallpaper of other display.");
                return;
            }
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
                if (ProtoLogCache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS, -1140296873, 1, "Collecting in transition %d: %s, caller=%s", new Object[]{Long.valueOf(this.mSyncId), String.valueOf(windowContainer), String.valueOf(Debug.getCallers(5))});
                }
            } else if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -1567866547, 1, (String) null, new Object[]{Long.valueOf(this.mSyncId), String.valueOf(windowContainer)});
            }
            for (WindowContainer animatableParent = getAnimatableParent(windowContainer); animatableParent != null && !this.mChanges.containsKey(animatableParent); animatableParent = getAnimatableParent(animatableParent)) {
                ChangeInfo changeInfo = new ChangeInfo(animatableParent);
                updateTransientFlags(changeInfo);
                this.mChanges.put(animatableParent, changeInfo);
                if (isReadyGroup(animatableParent)) {
                    this.mReadyTracker.addGroup(animatableParent);
                    if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -1442613680, 1, (String) null, new Object[]{Long.valueOf(this.mSyncId), String.valueOf(animatableParent)});
                    }
                }
            }
            if (!this.mParticipants.contains(windowContainer) || z) {
                boolean z2 = (!isWallpaper(windowContainer) || this.mParticipants.contains(windowContainer.mDisplayContent) || (CoreRune.FW_LOCK_ONLY_LIVE_WALLPAPER && isInKeyguardTransition())) && !isInTransientHide(windowContainer) && (!CoreRune.FW_CUSTOM_SHELL_TRANSITION || needSyncForRedraw(windowContainer));
                if (z2) {
                    this.mSyncEngine.addToSyncSet(this.mSyncId, windowContainer);
                }
                ChangeInfo changeInfo2 = (ChangeInfo) this.mChanges.get(windowContainer);
                if (changeInfo2 == null) {
                    changeInfo2 = new ChangeInfo(windowContainer);
                    updateTransientFlags(changeInfo2);
                    updatePopOverFlags(changeInfo2);
                    if (CoreRune.FW_REMOTE_WALLPAPER_ANIM && !z2 && isWallpaper(windowContainer)) {
                        updateRemoteWallpaperAnimFlags(changeInfo2);
                    }
                    this.mChanges.put(windowContainer, changeInfo2);
                }
                this.mParticipants.add(windowContainer);
                recordDisplay(windowContainer.getDisplayContent());
                if (changeInfo2.mShowWallpaper) {
                    List allTopWallpapers = windowContainer.getDisplayContent().mWallpaperController.getAllTopWallpapers();
                    for (int size = allTopWallpapers.size() - 1; size >= 0; size--) {
                        collect(((WindowState) allTopWallpapers.get(size)).mToken);
                    }
                }
            }
        }
    }

    public final boolean needSyncForRedraw(WindowContainer windowContainer) {
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && windowContainer.asTransientLaunchOverlay() != null) {
            return this.mParticipants.contains(windowContainer.mDisplayContent);
        }
        Task asTask = windowContainer.asTask();
        return asTask == null || !asTask.isFreeformForceHidden();
    }

    public final void updateTransientFlags(ChangeInfo changeInfo) {
        WindowContainer windowContainer = changeInfo.mContainer;
        if (!(windowContainer.asTaskFragment() == null && windowContainer.asActivityRecord() == null) && isInTransientHide(windowContainer)) {
            changeInfo.mFlags |= 4;
        }
    }

    public final void updatePopOverFlags(ChangeInfo changeInfo) {
        WindowContainer windowContainer = changeInfo.mContainer;
        if (windowContainer.asActivityRecord() == null || !isUnfoldTransition() || windowContainer.asActivityRecord().getTask() == null || windowContainer.asActivityRecord().getTask().topRunningActivity() == null || !windowContainer.asActivityRecord().getTask().topRunningActivity().mPopOverState.isActivated()) {
            return;
        }
        changeInfo.mFlags |= 262144;
    }

    public final void recordDisplay(DisplayContent displayContent) {
        if (displayContent == null || this.mTargetDisplays.contains(displayContent)) {
            return;
        }
        this.mTargetDisplays.add(displayContent);
        addOnTopTasks(displayContent, this.mOnTopTasksStart);
    }

    public void recordTaskOrder(WindowContainer windowContainer) {
        recordDisplay(windowContainer.getDisplayContent());
    }

    public static void addOnTopTasks(Task task, ArrayList arrayList) {
        for (int childCount = task.getChildCount() - 1; childCount >= 0; childCount--) {
            Task asTask = task.getChildAt(childCount).asTask();
            if (asTask == null) {
                return;
            }
            if (!asTask.getWindowConfiguration().isAlwaysOnTop()) {
                arrayList.add(asTask);
                addOnTopTasks(asTask, arrayList);
                return;
            }
        }
    }

    public static void addOnTopTasks(DisplayContent displayContent, ArrayList arrayList) {
        Task rootTask = displayContent.getRootTask(new Predicate() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$addOnTopTasks$1;
                lambda$addOnTopTasks$1 = Transition.lambda$addOnTopTasks$1((Task) obj);
                return lambda$addOnTopTasks$1;
            }
        });
        if (rootTask == null) {
            return;
        }
        arrayList.add(rootTask);
        addOnTopTasks(rootTask, arrayList);
    }

    public static /* synthetic */ boolean lambda$addOnTopTasks$1(Task task) {
        return (task.getWindowConfiguration().isAlwaysOnTop() || task.inFreeformWindowingMode()) ? false : true;
    }

    public void collectExistenceChange(WindowContainer windowContainer) {
        if (this.mState >= 2) {
            return;
        }
        if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -354571697, 1, (String) null, new Object[]{Long.valueOf(this.mSyncId), String.valueOf(windowContainer)});
        }
        collect(windowContainer);
        ((ChangeInfo) this.mChanges.get(windowContainer)).mExistenceChanged = true;
    }

    public void collectVisibleChange(WindowContainer windowContainer) {
        StartingData startingData;
        if (this.mSyncEngine.getSyncSet(this.mSyncId).mSyncMethod == 1 || windowContainer.mDisplayContent == null || !isInTransition(windowContainer)) {
            return;
        }
        if (!windowContainer.mDisplayContent.getDisplayPolicy().isScreenOnFully() || windowContainer.mDisplayContent.getDisplayInfo().state == 1) {
            this.mFlags |= 1024;
            return;
        }
        if (windowContainer.asActivityRecord() == null || (startingData = windowContainer.asActivityRecord().mStartingData) == null || startingData.mAssociatedTask == null) {
            if (this.mContainerFreezer == null) {
                this.mContainerFreezer = new ScreenshotFreezer();
            }
            ChangeInfo changeInfo = (ChangeInfo) this.mChanges.get(windowContainer);
            if ((changeInfo != null && changeInfo.mVisible && windowContainer.isVisibleRequested()) || (CoreRune.MW_SHELL_CHANGE_TRANSITION && changeInfo != null && changeInfo.shouldFreezeByChangeTransition())) {
                if (CoreRune.MW_FREEFORM_SHELL_TRANSITION && changeInfo.mWindowingMode == 5) {
                    changeInfo.mCornerRadius = ChangeTransitionController.getFreeformCornerRadius(windowContainer.mDisplayContent.getDisplayMetrics());
                }
                this.mContainerFreezer.freeze(windowContainer, changeInfo.mAbsoluteBounds);
            }
        }
    }

    public void collectReparentChange(WindowContainer windowContainer, WindowContainer windowContainer2) {
        WindowContainer windowContainer3;
        if (this.mChanges.containsKey(windowContainer)) {
            ChangeInfo changeInfo = (ChangeInfo) this.mChanges.get(windowContainer);
            WindowContainer windowContainer4 = changeInfo.mStartParent;
            if (windowContainer4 == null || windowContainer4.isAttached()) {
                windowContainer3 = changeInfo.mStartParent;
            } else {
                windowContainer3 = changeInfo.mCommonAncestor;
            }
            if (windowContainer3 == null || !windowContainer3.isAttached()) {
                Slog.w("Transition", "Trying to collect reparenting of a window after the previous parent has been detached: " + windowContainer);
                return;
            }
            if (windowContainer3 == windowContainer2) {
                Slog.w("Transition", "Trying to collect reparenting of a window that has not been reparented: " + windowContainer);
                return;
            }
            if (!windowContainer2.isAttached()) {
                Slog.w("Transition", "Trying to collect reparenting of a window that is not attached after reparenting: " + windowContainer);
                return;
            }
            while (windowContainer3 != windowContainer2 && !windowContainer3.isDescendantOf(windowContainer2)) {
                windowContainer2 = windowContainer2.getParent();
            }
            changeInfo.mCommonAncestor = windowContainer2;
        }
    }

    public boolean isInTransition(WindowContainer windowContainer) {
        while (windowContainer != null) {
            if (this.mParticipants.contains(windowContainer)) {
                return true;
            }
            windowContainer = windowContainer.getParent();
        }
        return false;
    }

    public void setKnownConfigChanges(WindowContainer windowContainer, int i) {
        ChangeInfo changeInfo = (ChangeInfo) this.mChanges.get(windowContainer);
        if (changeInfo != null) {
            changeInfo.mKnownConfigChanges = i;
        }
    }

    public final void sendRemoteCallback(IRemoteCallback iRemoteCallback) {
        if (iRemoteCallback == null) {
            return;
        }
        this.mController.mAtm.f1734mH.sendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Transition.lambda$sendRemoteCallback$2((IRemoteCallback) obj);
            }
        }, iRemoteCallback));
    }

    public static /* synthetic */ void lambda$sendRemoteCallback$2(IRemoteCallback iRemoteCallback) {
        try {
            iRemoteCallback.sendResult((Bundle) null);
        } catch (RemoteException unused) {
        }
    }

    public void setOverrideAnimation(TransitionInfo.AnimationOptions animationOptions, IRemoteCallback iRemoteCallback, IRemoteCallback iRemoteCallback2) {
        if (isCollecting()) {
            this.mOverrideOptions = animationOptions;
            sendRemoteCallback(this.mClientAnimationStartCallback);
            this.mClientAnimationStartCallback = iRemoteCallback;
            this.mClientAnimationFinishCallback = iRemoteCallback2;
        }
    }

    public void setReady(WindowContainer windowContainer, boolean z) {
        if (!isCollecting() || this.mSyncId < 0) {
            return;
        }
        this.mReadyTracker.setReadyFrom(windowContainer, z);
        applyReady();
    }

    public final void applyReady() {
        if (this.mState < 1) {
            return;
        }
        boolean allReady = this.mReadyTracker.allReady();
        if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -874888131, 7, (String) null, new Object[]{Boolean.valueOf(allReady), Long.valueOf(this.mSyncId)});
        }
        if (this.mSyncEngine.setReady(this.mSyncId, allReady) && allReady) {
            this.mLogger.mReadyTimeNs = SystemClock.elapsedRealtimeNanos();
            this.mOnTopTasksAtReady.clear();
            for (int i = 0; i < this.mTargetDisplays.size(); i++) {
                addOnTopTasks((DisplayContent) this.mTargetDisplays.get(i), this.mOnTopTasksAtReady);
            }
            this.mController.onTransitionPopulated(this);
        }
    }

    public void setAllReady() {
        if (!isCollecting() || this.mSyncId < 0) {
            return;
        }
        this.mReadyTracker.setAllReady();
        applyReady();
    }

    public boolean allReady() {
        return this.mReadyTracker.allReady();
    }

    public boolean isPopulated() {
        return this.mState >= 1 && this.mReadyTracker.allReady();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v1, types: [int] */
    /* JADX WARN: Type inference failed for: r3v8 */
    public final void buildFinishTransaction(SurfaceControl.Transaction transaction, TransitionInfo transitionInfo) {
        ?? r3;
        float f;
        Point point = new Point();
        ArraySet arraySet = new ArraySet();
        boolean z = true;
        int size = this.mTargets.size() - 1;
        while (true) {
            r3 = 0;
            r3 = false;
            boolean z2 = false;
            if (size < 0) {
                break;
            }
            WindowContainer windowContainer = ((ChangeInfo) this.mTargets.get(size)).mContainer;
            if (windowContainer.getParent() != null) {
                SurfaceControl leashSurface = getLeashSurface(windowContainer, null);
                transaction.reparent(leashSurface, getOrigParentSurface(windowContainer));
                transaction.setLayer(leashSurface, windowContainer.getLastLayer());
                windowContainer.getRelativePosition(point);
                transaction.setPosition(leashSurface, point.x, point.y);
                Task asTask = windowContainer.asTask();
                boolean z3 = (asTask == null || !asTask.inFreeformWindowingMode()) ? false : z;
                if (CoreRune.MW_FREEFORM_SHELL_TRANSITION && z3) {
                    transaction.setCrop(leashSurface, null);
                } else if (windowContainer.asTaskFragment() == null) {
                    transaction.setCrop(leashSurface, null);
                } else {
                    Rect resolvedOverrideBounds = windowContainer.getResolvedOverrideBounds();
                    transaction.setWindowCrop(leashSurface, resolvedOverrideBounds.width(), resolvedOverrideBounds.height());
                }
                boolean z4 = (asTask == null || !asTask.inPinnedWindowingMode()) ? false : z;
                if (asTask != null && asTask.isStageRootTask()) {
                    z2 = z;
                }
                if ((!CoreRune.MW_PIP_SHELL_TRANSITION || !z4) && (!CoreRune.MW_MULTI_SPLIT_ROUNDED_CORNER || !z2)) {
                    transaction.setCornerRadius(leashSurface, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                    transaction.setShadowRadius(leashSurface, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                }
                if (z3 && asTask.isFreeformStashed()) {
                    f = 0.0f;
                } else {
                    f = 0.0f;
                    transaction.setMatrix(leashSurface, 1.0f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f);
                }
                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && isTransientLaunchOverlay(windowContainer)) {
                    transaction.hide(leashSurface);
                }
                if (z3 && asTask.isFreeformForceHidden()) {
                    transaction.setAlpha(leashSurface, f);
                } else {
                    transaction.setAlpha(leashSurface, 1.0f);
                }
                if (windowContainer.isOrganized() && windowContainer.matchParentBounds()) {
                    transaction.setWindowCrop(leashSurface, -1, -1);
                }
                arraySet.add(windowContainer.getDisplayContent());
            }
            size--;
            z = true;
        }
        IContainerFreezer iContainerFreezer = this.mContainerFreezer;
        if (iContainerFreezer != null) {
            iContainerFreezer.cleanUp(transaction);
        }
        this.mController.mBuildingFinishLayers = true;
        try {
            for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
                if (arraySet.valueAt(size2) != null) {
                    ((DisplayContent) arraySet.valueAt(size2)).assignChildLayers(transaction);
                }
            }
            while (r3 < transitionInfo.getRootCount()) {
                transaction.reparent(transitionInfo.getRoot((int) r3).getLeash(), null);
                r3++;
            }
        } finally {
            this.mController.mBuildingFinishLayers = false;
        }
    }

    public static void buildCleanupTransaction(SurfaceControl.Transaction transaction, TransitionInfo transitionInfo) {
        int size = transitionInfo.getChanges().size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(size);
            if (change.getSnapshot() != null) {
                transaction.reparent(change.getSnapshot(), null);
            }
            if (change.hasFlags(32) && change.getStartRotation() != change.getEndRotation() && change.getContainer() != null) {
                transaction.unsetFixedTransformHint(WindowContainer.fromBinder(change.getContainer().asBinder()).asDisplayContent().mSurfaceControl);
            }
        }
        for (int rootCount = transitionInfo.getRootCount() - 1; rootCount >= 0; rootCount--) {
            SurfaceControl leash = transitionInfo.getRoot(rootCount).getLeash();
            if (leash != null) {
                transaction.reparent(leash, null);
            }
        }
    }

    public void setCanPipOnFinish(boolean z) {
        this.mCanPipOnFinish = z;
    }

    public final boolean didCommitTransientLaunch() {
        if (this.mTransientLaunches == null) {
            return false;
        }
        for (int i = 0; i < this.mTransientLaunches.size(); i++) {
            if (((ActivityRecord) this.mTransientLaunches.keyAt(i)).isVisibleRequested()) {
                return true;
            }
        }
        return false;
    }

    public final boolean checkEnterPipOnFinish(ActivityRecord activityRecord) {
        if (!this.mCanPipOnFinish || !activityRecord.isVisible() || activityRecord.getTask() == null || !activityRecord.isState(ActivityRecord.State.RESUMED)) {
            return false;
        }
        if (CoreRune.MW_PIP_SHELL_TRANSITION && activityRecord.inFreeformWindowingMode()) {
            return false;
        }
        PictureInPictureParams pictureInPictureParams = activityRecord.pictureInPictureArgs;
        if (pictureInPictureParams != null && pictureInPictureParams.isAutoEnterEnabled()) {
            if (!activityRecord.getTask().isVisibleRequested() || didCommitTransientLaunch()) {
                activityRecord.supportsEnterPipOnTaskSwitch = true;
            }
            if (!activityRecord.checkEnterPictureInPictureState("enterPictureInPictureMode", true)) {
                return false;
            }
            int windowingMode = activityRecord.getTask().getWindowingMode();
            boolean enterPictureInPictureMode = this.mController.mAtm.enterPictureInPictureMode(activityRecord, activityRecord.pictureInPictureArgs, false, true);
            int windowingMode2 = activityRecord.getTask().getWindowingMode();
            if (windowingMode == 1 && windowingMode2 == 2 && this.mTransientLaunches != null && activityRecord.mDisplayContent.hasTopFixedRotationLaunchingApp()) {
                activityRecord.mDisplayContent.mPinnedTaskController.setEnterPipTransaction(null);
            }
            return enterPictureInPictureMode;
        }
        if ((!activityRecord.getTask().isVisibleRequested() || didCommitTransientLaunch()) && activityRecord.supportsPictureInPicture()) {
            activityRecord.supportsEnterPipOnTaskSwitch = true;
        }
        try {
            this.mController.mAtm.mTaskSupervisor.mUserLeaving = true;
            activityRecord.getTaskFragment().startPausing(false, null, "finishTransition");
            return false;
        } finally {
            this.mController.mAtm.mTaskSupervisor.mUserLeaving = false;
        }
    }

    public void finishTransition() {
        WindowState windowState;
        TaskDisplayArea taskDisplayArea;
        ChangeInfo changeInfo;
        ActivityRecord topNonFinishingActivity;
        TransientLaunchOverlayToken asTransientLaunchOverlay;
        if (Trace.isTagEnabled(32L) && this.mIsPlayerEnabled) {
            asyncTraceEnd(System.identityHashCode(this));
        }
        this.mLogger.mFinishTimeNs = SystemClock.elapsedRealtimeNanos();
        Handler handler = this.mController.mLoggerHandler;
        final TransitionController.Logger logger = this.mLogger;
        Objects.requireNonNull(logger);
        handler.post(new Runnable() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                TransitionController.Logger.this.logOnFinish();
            }
        });
        this.mController.mTransitionTracer.logFinishedTransition(this);
        SurfaceControl.Transaction transaction = this.mStartTransaction;
        if (transaction != null) {
            transaction.close();
        }
        SurfaceControl.Transaction transaction2 = this.mFinishTransaction;
        if (transaction2 != null) {
            transaction2.close();
        }
        this.mFinishTransaction = null;
        this.mStartTransaction = null;
        SurfaceControl.Transaction transaction3 = this.mCleanupTransaction;
        if (transaction3 != null) {
            transaction3.apply();
            this.mCleanupTransaction = null;
        }
        if (this.mState < 2) {
            throw new IllegalStateException("Can't finish a non-playing transition " + this.mSyncId);
        }
        this.mController.mFinishingTransition = this;
        ArrayList arrayList = this.mTransientHideTasks;
        if (arrayList != null && !arrayList.isEmpty()) {
            this.mController.mAtm.mRootWindowContainer.ensureActivitiesVisible(null, 0, true);
            for (int i = 0; i < this.mTransientHideTasks.size(); i++) {
                final Task task = (Task) this.mTransientHideTasks.get(i);
                task.forAllActivities(new Consumer() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        Transition.this.lambda$finishTransition$3(task, (ActivityRecord) obj);
                    }
                });
            }
        }
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        for (int i2 = 0; i2 < this.mParticipants.size(); i2++) {
            WindowContainer windowContainer = (WindowContainer) this.mParticipants.valueAt(i2);
            ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
            if (asActivityRecord != null) {
                final Task task2 = asActivityRecord.getTask();
                if (task2 != null) {
                    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LATE_TRANSIENT_LAUNCH) {
                        asActivityRecord.mIsLateTransientLaunch = false;
                    }
                    boolean contains = this.mVisibleAtTransitionEndTokens.contains(asActivityRecord);
                    if (isTransientLaunch(asActivityRecord) && !asActivityRecord.isVisibleRequested() && this.mController.inCollectingTransition(asActivityRecord)) {
                        contains = true;
                    }
                    DisplayContent displayContent = asActivityRecord.mDisplayContent;
                    boolean z5 = displayContent == null || displayContent.getDisplayInfo().state == 1;
                    if ((!contains || z5) && !asActivityRecord.isVisibleRequested()) {
                        if (!checkEnterPipOnFinish(asActivityRecord)) {
                            if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -532081937, 0, (String) null, new Object[]{String.valueOf(asActivityRecord)});
                            }
                            SnapshotController snapshotController = this.mController.mSnapshotController;
                            if (this.mTransientLaunches != null && !task2.isVisibleRequested()) {
                                if (snapshotController.mTaskSnapshotController.getSnapshotCaptureTime(task2.mTaskId) < this.mLogger.mSendTimeNs) {
                                    snapshotController.mTaskSnapshotController.recordSnapshot(task2, false);
                                } else if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -1060529098, 1, (String) null, new Object[]{Long.valueOf(task2.mTaskId)});
                                }
                                snapshotController.mActivitySnapshotController.notifyAppVisibilityChanged(asActivityRecord, false);
                            }
                            asActivityRecord.commitVisibility(false, false, true);
                            z3 = true;
                        } else {
                            z2 = true;
                        }
                    }
                    ChangeInfo changeInfo2 = (ChangeInfo) this.mChanges.get(asActivityRecord);
                    if (changeInfo2 != null && changeInfo2.mVisible != contains) {
                        asActivityRecord.mEnteringAnimation = contains;
                    } else {
                        ArrayMap arrayMap = this.mTransientLaunches;
                        if (arrayMap != null && arrayMap.containsKey(asActivityRecord) && asActivityRecord.isVisible()) {
                            asActivityRecord.mEnteringAnimation = true;
                            if (!task2.isFocused() && asActivityRecord.isTopRunningActivity()) {
                                this.mController.mAtm.setLastResumedActivityUncheckLocked(asActivityRecord, "transitionFinished");
                            }
                            z = true;
                        }
                    }
                    if (CoreRune.MW_SA_LOGGING && task2.mNeedToSendFreeformLogging) {
                        this.mController.mLoggerHandler.post(new Runnable() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda7
                            @Override // java.lang.Runnable
                            public final void run() {
                                Task.this.sendFreeformLogging();
                            }
                        });
                    }
                }
            } else if (windowContainer.asDisplayContent() != null) {
                z4 = true;
            } else {
                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && (asTransientLaunchOverlay = windowContainer.asTransientLaunchOverlay()) != null) {
                    asTransientLaunchOverlay.setVisibility(false);
                }
                final Task asTask = windowContainer.asTask();
                if (asTask != null && asTask.isVisibleRequested() && asTask.inPinnedWindowingMode() && (topNonFinishingActivity = asTask.getTopNonFinishingActivity()) != null && !topNonFinishingActivity.inPinnedWindowingMode()) {
                    this.mController.mStateValidators.add(new Runnable() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            Transition.lambda$finishTransition$5(Task.this);
                        }
                    });
                }
                if (CoreRune.MW_SA_LOGGING && asTask != null && asTask.mNeedToSendFreeformLogging) {
                    this.mController.mLoggerHandler.post(new Runnable() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            Task.this.sendFreeformLogging();
                        }
                    });
                }
            }
        }
        for (int size = this.mParticipants.size() - 1; size >= 0; size--) {
            WallpaperWindowToken asWallpaperToken = ((WindowContainer) this.mParticipants.valueAt(size)).asWallpaperToken();
            if (asWallpaperToken != null) {
                WindowState wallpaperTarget = asWallpaperToken.mDisplayContent.mWallpaperController.getWallpaperTarget();
                boolean z6 = wallpaperTarget == null || !wallpaperTarget.mToken.isVisible();
                boolean z7 = asWallpaperToken.isVisibleRequested() || this.mVisibleAtTransitionEndTokens.contains(asWallpaperToken);
                if (z6 || !z7) {
                    if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 691515534, 0, (String) null, new Object[]{String.valueOf(asWallpaperToken)});
                    }
                    asWallpaperToken.commitVisibility(false);
                }
                if (z6) {
                    DisplayContent displayContent2 = asWallpaperToken.mDisplayContent;
                    displayContent2.pendingLayoutChanges = 4 | displayContent2.pendingLayoutChanges;
                }
            }
        }
        if (z3) {
            this.mController.onCommittedInvisibles();
        }
        if (z) {
            if (z2) {
                this.mController.mAtm.getTaskChangeNotificationController().notifyTaskStackChanged();
            }
            this.mController.mAtm.stopAppSwitches();
            this.mController.mAtm.mRootWindowContainer.rankTaskLayers();
        }
        for (int i3 = 0; i3 < this.mParticipants.size(); i3++) {
            ActivityRecord asActivityRecord2 = ((WindowContainer) this.mParticipants.valueAt(i3)).asActivityRecord();
            if (asActivityRecord2 != null && (asActivityRecord2.isVisibleRequested() || !asActivityRecord2.isState(ActivityRecord.State.INITIALIZING) || (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && asActivityRecord2.hasFixedRotationTransform()))) {
                this.mController.dispatchLegacyAppTransitionFinished(asActivityRecord2);
                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
                    this.mNeedsCleanupLegacyAppTransitionPending = false;
                }
            }
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && this.mNeedsCleanupLegacyAppTransitionPending) {
            this.mController.dispatchLegacyAppTransitionCancelled();
        }
        SurfaceControl.Transaction transaction4 = null;
        for (int i4 = 0; i4 < this.mParticipants.size(); i4++) {
            ActivityRecord asActivityRecord3 = ((WindowContainer) this.mParticipants.valueAt(i4)).asActivityRecord();
            if (asActivityRecord3 != null && asActivityRecord3.isVisible() && asActivityRecord3.getParent() != null) {
                if (transaction4 == null) {
                    transaction4 = (SurfaceControl.Transaction) asActivityRecord3.mWmService.mTransactionFactory.get();
                }
                asActivityRecord3.mActivityRecordInputSink.applyChangesToSurfaceIfChanged(transaction4);
            }
        }
        if (transaction4 != null) {
            transaction4.apply();
        }
        this.mController.mAtm.mTaskSupervisor.scheduleProcessStoppingAndFinishingActivitiesIfNeeded();
        sendRemoteCallback(this.mClientAnimationFinishCallback);
        legacyRestoreNavigationBarFromApp();
        int i5 = this.mRecentsDisplayId;
        if (i5 != -1) {
            this.mController.mAtm.mRootWindowContainer.getDisplayContent(i5).getInputMonitor().setActiveRecents(null, null);
        }
        ArrayMap arrayMap2 = this.mTransientLaunches;
        if (arrayMap2 != null) {
            for (int size2 = arrayMap2.size() - 1; size2 >= 0; size2--) {
                Task task3 = ((ActivityRecord) this.mTransientLaunches.keyAt(size2)).getTask();
                if (task3 != null) {
                    task3.setCanAffectSystemUiFlags(true);
                }
            }
        }
        for (int i6 = 0; i6 < this.mTargetDisplays.size(); i6++) {
            DisplayContent displayContent3 = (DisplayContent) this.mTargetDisplays.get(i6);
            AsyncRotationController asyncRotationController = displayContent3.getAsyncRotationController();
            if (asyncRotationController != null && containsChangeFor(displayContent3, this.mTargets)) {
                if (CoreRune.MW_SHELL_TRANSITION_BUG_FIX) {
                    try {
                        asyncRotationController.setSyncId(this.mSyncId);
                        asyncRotationController.onTransitionFinished();
                    } finally {
                        asyncRotationController.resetSyncId();
                    }
                } else {
                    asyncRotationController.onTransitionFinished();
                }
            }
            if (z4 && displayContent3.mDisplayRotationCompatPolicy != null && (changeInfo = (ChangeInfo) this.mChanges.get(displayContent3)) != null && changeInfo.mRotation != displayContent3.getWindowConfiguration().getRotation()) {
                displayContent3.mDisplayRotationCompatPolicy.onScreenRotationAnimationFinished();
            }
            if (this.mTransientLaunches != null) {
                InsetsControlTarget imeTarget = displayContent3.getImeTarget(0);
                int i7 = 0;
                while (true) {
                    if (i7 >= this.mTransientLaunches.size()) {
                        windowState = null;
                        taskDisplayArea = null;
                        break;
                    } else {
                        if (((ActivityRecord) this.mTransientLaunches.keyAt(i7)).getDisplayContent() == displayContent3) {
                            windowState = displayContent3.computeImeTarget(true);
                            taskDisplayArea = ((ActivityRecord) this.mTransientLaunches.keyAt(i6)).getTaskDisplayArea();
                            break;
                        }
                        i7++;
                    }
                }
                if (this.mRecentsDisplayId != -1 && imeTarget == windowState) {
                    InputMethodManagerInternal.get().updateImeWindowStatus(false);
                }
                if (!z && taskDisplayArea != null) {
                    taskDisplayArea.pauseBackTasks(null);
                }
                if (CoreRune.FW_SUPPORT_OCCLUDES_PARENT_CHANGE_CALLBACK && displayContent3.isDefaultDisplay) {
                    displayContent3.mWmService.mExt.updateOccludeTargetIfNeeded(displayContent3);
                }
            }
            displayContent3.removeImeSurfaceImmediately();
            displayContent3.handleCompleteDeferredRemoval();
        }
        validateKeyguardOcclusion();
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
            validateDisplayVisibility();
            validateReparentToDisplay();
            validateWallpaperVisibility();
        }
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
            this.mChangeTransitController.onTransitionFinished(this);
        }
        this.mState = 4;
        if (z4 && !this.mController.useShellTransitionsRotation()) {
            this.mController.mAtm.mWindowManager.updateRotation(false, false);
        }
        cleanUpInternal();
        this.mController.updateAnimatingState(this.mTmpTransaction);
        this.mTmpTransaction.apply();
        this.mController.mAtm.mBackNavigationController.onTransitionFinish(this.mTargets, this);
        this.mController.mFinishingTransition = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishTransition$3(Task task, ActivityRecord activityRecord) {
        if (this.mParticipants.contains(activityRecord.getTask())) {
            if (task.isVisibleRequested()) {
                if (activityRecord.isVisibleRequested()) {
                    return;
                }
                this.mController.mValidateCommitVis.add(activityRecord);
                return;
            }
            this.mParticipants.add(activityRecord);
        }
    }

    public static /* synthetic */ void lambda$finishTransition$5(Task task) {
        if (task.isAttached() && task.isVisibleRequested() && task.inPinnedWindowingMode()) {
            ActivityRecord topNonFinishingActivity = task.getTopNonFinishingActivity();
            if (topNonFinishingActivity.inPinnedWindowingMode()) {
                return;
            }
            Slog.e("Transition", "Enter-PIP was started but not completed, this is a Shell/SysUI bug. This state breaks gesture-nav, so attempting clean-up.");
            task.abortPipEnter(topNonFinishingActivity);
        }
    }

    public void abort() {
        int i = this.mState;
        if (i == 3) {
            return;
        }
        if (i == -1) {
            Slog.d(StartingSurfaceController.TAG, "Aborting Transition: " + this.mSyncId + " in STATE_PENDING called from" + Debug.getCaller());
            this.mState = 3;
            return;
        }
        if (i != 0 && i != 1) {
            throw new IllegalStateException("Too late to abort. state=" + this.mState);
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
            Slog.d(StartingSurfaceController.TAG, "Aborting Transition: " + this.mSyncId + " in state " + this.mState + " called from " + Debug.getCaller());
        } else if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -863438038, 1, (String) null, new Object[]{Long.valueOf(this.mSyncId)});
        }
        this.mState = 3;
        this.mLogger.mAbortTimeNs = SystemClock.elapsedRealtimeNanos();
        this.mController.mTransitionTracer.logAbortedTransition(this);
        this.mSyncEngine.abort(this.mSyncId);
        this.mController.dispatchLegacyAppTransitionCancelled();
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
            this.mNeedsCleanupLegacyAppTransitionPending = false;
        }
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
            this.mChangeTransitController.onTransitionAborted(this);
        }
        if (CoreRune.MW_SHELL_TRANSITION) {
            this.mController.notifyAbort(this);
        }
    }

    public void playNow() {
        int i = this.mState;
        if (i == 0 || i == 1) {
            if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -894942237, 1, (String) null, new Object[]{Long.valueOf(this.mSyncId)});
            }
            this.mForcePlaying = true;
            setAllReady();
            if (this.mState == 0) {
                start();
            }
            this.mSyncEngine.onSurfacePlacement();
        }
    }

    public boolean isForcePlaying() {
        return this.mForcePlaying;
    }

    public void setRemoteAnimationApp(IApplicationThread iApplicationThread) {
        this.mRemoteAnimApp = iApplicationThread;
    }

    public IApplicationThread getRemoteAnimationApp() {
        return this.mRemoteAnimApp;
    }

    public void setNoAnimation(WindowContainer windowContainer) {
        ChangeInfo changeInfo = (ChangeInfo) this.mChanges.get(windowContainer);
        if (changeInfo == null) {
            throw new IllegalStateException("Can't set no-animation property of non-participant");
        }
        changeInfo.mFlags |= 8;
    }

    public void setDisplayChangeTransitionFlag(WindowContainer windowContainer, boolean z) {
        ChangeInfo changeInfo = (ChangeInfo) this.mChanges.get(windowContainer);
        if (changeInfo != null) {
            int i = changeInfo.mFlags | 65536;
            changeInfo.mFlags = i;
            if (z) {
                changeInfo.mFlags = i | IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
            }
        }
    }

    public static boolean containsChangeFor(WindowContainer windowContainer, ArrayList arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (((ChangeInfo) arrayList.get(size)).mContainer == windowContainer) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.wm.BLASTSyncEngine.TransactionReadyListener
    public void onTransactionReady(int i, SurfaceControl.Transaction transaction) {
        TransitionInfo calculateTransitionInfo;
        PowerManagerInternal powerManagerInternal;
        ChangeInfo changeInfo;
        int i2;
        if (i != this.mSyncId) {
            Slog.e("Transition", "Unexpected Sync ID " + i + ". Expected " + this.mSyncId);
            return;
        }
        commitVisibleActivities(transaction);
        commitVisibleWallpapers();
        DisplayContent defaultDisplay = !this.mTargetDisplays.isEmpty() ? (DisplayContent) this.mTargetDisplays.get(0) : this.mController.mAtm.mRootWindowContainer.getDefaultDisplay();
        if (this.mState == 3) {
            this.mController.onAbort(this);
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
                if (CoreRune.FW_SURFACE_DEBUG_APPLY && CoreRune.FW_CUSTOM_SHELL_TRANSITION) {
                    transaction.addDebugName("AbortTransition_SyncId<" + this.mSyncId + ">");
                }
                for (int size = this.mParticipants.size() - 1; size >= 0; size--) {
                    ActivityRecord asActivityRecord = ((WindowContainer) this.mParticipants.valueAt(size)).asActivityRecord();
                    if (asActivityRecord != null && asActivityRecord.mSurfaceControl != null && asActivityRecord.getParent() != null && asActivityRecord.isVisibleRequested() && !asActivityRecord.inTransition() && !this.mController.inFinishingTransition(asActivityRecord)) {
                        transaction.show(asActivityRecord.mSurfaceControl);
                        for (WindowContainer parent = asActivityRecord.getParent(); parent != null && parent != asActivityRecord.getDisplayContent(); parent = parent.getParent()) {
                            SurfaceControl surfaceControl = parent.mSurfaceControl;
                            if (surfaceControl != null) {
                                transaction.show(surfaceControl);
                            }
                        }
                    }
                }
            }
            defaultDisplay.getPendingTransaction().merge(transaction);
            this.mSyncId = -1;
            this.mOverrideOptions = null;
            cleanUpInternal();
            return;
        }
        this.mState = 2;
        this.mStartTransaction = transaction;
        this.mFinishTransaction = (SurfaceControl.Transaction) this.mController.mAtm.mWindowManager.mTransactionFactory.get();
        if (CoreRune.FW_SURFACE_DEBUG_APPLY && CoreRune.FW_CUSTOM_SHELL_TRANSITION) {
            this.mStartTransaction.addDebugName("StartTransaction_SyncId<" + this.mSyncId + ">");
            this.mFinishTransaction.addDebugName("FinishTransaction_SyncId<" + this.mSyncId + ">");
        }
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
            this.mChangeTransitController.onTransactionReady(this);
        }
        if (defaultDisplay.isKeyguardLocked()) {
            this.mFlags |= 64;
        }
        collectOrderChanges(this.mController.mWaitingTransitions.isEmpty());
        if (this.mPriorVisibilityMightBeDirty) {
            updatePriorVisibility();
        }
        ArrayList calculateTargets = calculateTargets(this.mParticipants, this.mChanges, this.mType);
        this.mTargets = calculateTargets;
        this.mController.mAtm.mBackNavigationController.onTransactionReady(this, calculateTargets);
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_OVERRIDE_TYPE && (i2 = this.mOverrideTransitionType) != 0 && this.mOverrideOptions == null) {
            calculateTransitionInfo = calculateTransitionInfo(i2, this.mFlags, this.mTargets, transaction);
            Slog.d(StartingSurfaceController.TAG, "Override transition type, mType=" + this.mType + ", newType=" + this.mOverrideTransitionType);
        } else {
            calculateTransitionInfo = calculateTransitionInfo(this.mType, this.mFlags, this.mTargets, transaction);
        }
        calculateTransitionInfo.setDebugId(this.mSyncId);
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE && this.mSkipMergeAnimation) {
            calculateTransitionInfo.setSkipMergeAnimation();
        }
        this.mController.assignTrack(this, calculateTransitionInfo);
        this.mController.moveToPlaying(this);
        this.mTargetDisplays.clear();
        for (int i3 = 0; i3 < calculateTransitionInfo.getRootCount(); i3++) {
            this.mTargetDisplays.add(this.mController.mAtm.mRootWindowContainer.getDisplayContent(calculateTransitionInfo.getRoot(i3).getDisplayId()));
        }
        TransitionInfo.AnimationOptions animationOptions = this.mOverrideOptions;
        if (animationOptions != null) {
            calculateTransitionInfo.setAnimationOptions(animationOptions);
            if (this.mOverrideOptions.getType() == 12) {
                int i4 = 0;
                while (true) {
                    if (i4 >= this.mTargets.size()) {
                        break;
                    }
                    TransitionInfo.Change change = (TransitionInfo.Change) calculateTransitionInfo.getChanges().get(i4);
                    ActivityRecord asActivityRecord2 = ((ChangeInfo) this.mTargets.get(i4)).mContainer.asActivityRecord();
                    if (asActivityRecord2 == null || change.getMode() != 1) {
                        i4++;
                    } else {
                        change.setFlags(change.getFlags() | (asActivityRecord2.mUserId == asActivityRecord2.mWmService.mCurrentUserId ? IInstalld.FLAG_USE_QUOTA : IInstalld.FLAG_FORCE));
                    }
                }
            }
        }
        for (int i5 = 0; i5 < this.mTargetDisplays.size(); i5++) {
            handleLegacyRecentsStartBehavior((DisplayContent) this.mTargetDisplays.get(i5), calculateTransitionInfo);
            if (this.mRecentsDisplayId != -1) {
                break;
            }
        }
        sendRemoteCallback(this.mClientAnimationStartCallback);
        for (int size2 = this.mParticipants.size() - 1; size2 >= 0; size2--) {
            ActivityRecord asActivityRecord3 = ((WindowContainer) this.mParticipants.valueAt(size2)).asActivityRecord();
            if (asActivityRecord3 != null && asActivityRecord3.isVisibleRequested()) {
                transaction.show(asActivityRecord3.getSurfaceControl());
                for (WindowContainer parent2 = asActivityRecord3.getParent(); parent2 != null && !containsChangeFor(parent2, this.mTargets); parent2 = parent2.getParent()) {
                    if (parent2.getSurfaceControl() != null) {
                        transaction.show(parent2.getSurfaceControl());
                    }
                }
            }
        }
        if (this.mTransientLaunches == null) {
            for (int size3 = this.mParticipants.size() - 1; size3 >= 0; size3--) {
                WindowContainer windowContainer = (WindowContainer) this.mParticipants.valueAt(size3);
                if (windowContainer.asWindowToken() != null && windowContainer.isVisibleRequested()) {
                    this.mVisibleAtTransitionEndTokens.add(windowContainer.asWindowToken());
                }
            }
        }
        if (this.mTransientLaunches == null) {
            for (int size4 = this.mParticipants.size() - 1; size4 >= 0; size4--) {
                ActivityRecord asActivityRecord4 = ((WindowContainer) this.mParticipants.valueAt(size4)).asActivityRecord();
                if (asActivityRecord4 != null && asActivityRecord4.getTask() != null && !asActivityRecord4.getTask().isVisibleRequested() && ((changeInfo = (ChangeInfo) this.mChanges.get(asActivityRecord4)) == null || changeInfo.mWindowingMode != 2)) {
                    if (isInSkipClosingAppSnapshotTasks(asActivityRecord4.getTask())) {
                        removeSkipClosingAppSnapshotTasks(asActivityRecord4.getTask());
                    } else {
                        this.mController.mSnapshotController.mTaskSnapshotController.recordSnapshot(asActivityRecord4.getTask(), false);
                    }
                }
            }
        }
        for (int i6 = 0; i6 < this.mTargetDisplays.size(); i6++) {
            DisplayContent displayContent = (DisplayContent) this.mTargetDisplays.get(i6);
            AsyncRotationController asyncRotationController = displayContent.getAsyncRotationController();
            if (asyncRotationController != null && containsChangeFor(displayContent, this.mTargets)) {
                asyncRotationController.setupStartTransaction(transaction);
            }
        }
        buildFinishTransaction(this.mFinishTransaction, calculateTransitionInfo);
        SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) this.mController.mAtm.mWindowManager.mTransactionFactory.get();
        this.mCleanupTransaction = transaction2;
        buildCleanupTransaction(transaction2, calculateTransitionInfo);
        if (this.mController.getTransitionPlayer() != null && this.mIsPlayerEnabled) {
            this.mController.dispatchLegacyAppTransitionStarting(calculateTransitionInfo, this.mStatusBarTransitionDelay);
            try {
                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
                    this.mNeedsCleanupLegacyAppTransitionPending = false;
                }
                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
                    if (ProtoLogCache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS, -1244520099, 0, "Calling onTransitionReady: %s", new Object[]{String.valueOf(calculateTransitionInfo.toString("    "))});
                    }
                } else if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 1115248873, 0, (String) null, new Object[]{String.valueOf(calculateTransitionInfo)});
                }
                this.mLogger.mSendTimeNs = SystemClock.elapsedRealtimeNanos();
                this.mLogger.mInfo = calculateTransitionInfo;
                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && this.mIsFailedStartTransition) {
                    postCleanupOnFailure();
                } else {
                    this.mController.setHasTopUiIfNeeded(true);
                    this.mController.getTransitionPlayer().onTransitionReady(this.mToken, calculateTransitionInfo, transaction, this.mFinishTransaction);
                    if (CoreRune.FW_VRR_SEAMLESS && (powerManagerInternal = this.mController.mAtm.mWindowManager.mPowerManagerInternal) != null) {
                        powerManagerInternal.setPowerBoost(0, 0);
                    }
                }
                if (Trace.isTagEnabled(32L)) {
                    asyncTraceBegin("playing", System.identityHashCode(this));
                }
            } catch (RemoteException unused) {
                postCleanupOnFailure();
            }
            for (int i7 = 0; i7 < this.mTargetDisplays.size(); i7++) {
                DisplayContent displayContent2 = (DisplayContent) this.mTargetDisplays.get(i7);
                AccessibilityController accessibilityController = displayContent2.mWmService.mAccessibilityController;
                if (accessibilityController.hasCallbacks()) {
                    accessibilityController.onWMTransition(displayContent2.getDisplayId(), this.mType);
                }
            }
        } else {
            if (!this.mIsPlayerEnabled) {
                this.mLogger.mSendTimeNs = SystemClock.uptimeNanos();
                if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 378890013, 1, (String) null, new Object[]{Long.valueOf(this.mSyncId)});
                }
            }
            postCleanupOnFailure();
        }
        this.mOverrideOptions = null;
        reportStartReasonsToLogger();
        calculateTransitionInfo.releaseAnimSurfaces();
        this.mLogger.logOnSendAsync(this.mController.mLoggerHandler);
        if (this.mLogger.mInfo != null) {
            this.mController.mTransitionTracer.logSentTransition(this, this.mTargets);
        }
    }

    public final boolean isInSkipClosingAppSnapshotTasks(Task task) {
        return this.mController.mSnapshotController.mTaskSnapshotController.isInSkipClosingAppSnapshotTasks(task);
    }

    public final void removeSkipClosingAppSnapshotTasks(Task task) {
        this.mController.mSnapshotController.mTaskSnapshotController.removeSkipClosingAppSnapshotTasks(task);
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x00bd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void collectOrderChanges(boolean z) {
        boolean z2;
        int indexOfKey;
        boolean z3;
        if (this.mOnTopTasksStart.isEmpty()) {
            return;
        }
        int i = 0;
        while (true) {
            if (i >= this.mOnTopTasksAtReady.size()) {
                z2 = false;
                break;
            }
            if (!this.mOnTopTasksStart.contains((Task) this.mOnTopTasksAtReady.get(i))) {
                z2 = true;
                break;
            }
            i++;
        }
        if (z2 || z) {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.mTargetDisplays.size(); i2++) {
                addOnTopTasks((DisplayContent) this.mTargetDisplays.get(i2), arrayList);
                int i3 = ((DisplayContent) this.mTargetDisplays.get(i2)).mDisplayId;
                ArrayList arrayList2 = (ArrayList) this.mController.mLatestOnTopTasksReported.get(i3);
                Task task = null;
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    Task task2 = (Task) arrayList.get(size);
                    if (task2.getDisplayId() == i3) {
                        if (arrayList2 == null) {
                            if (!this.mOnTopTasksStart.contains(task2)) {
                                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
                                    Iterator it = this.mOnTopTasksStart.iterator();
                                    while (true) {
                                        if (it.hasNext()) {
                                            if (((Task) it.next()).needsZBoost()) {
                                                z3 = true;
                                                break;
                                            }
                                        } else {
                                            z3 = false;
                                            break;
                                        }
                                    }
                                    if (z3) {
                                    }
                                }
                                if (task != null && task.getParent() == task2) {
                                }
                                this.mParticipants.add(task2);
                                indexOfKey = this.mChanges.indexOfKey(task2);
                                if (indexOfKey < 0) {
                                    this.mChanges.put(task2, new ChangeInfo(task2));
                                    indexOfKey = this.mChanges.indexOfKey(task2);
                                }
                                ((ChangeInfo) this.mChanges.valueAt(indexOfKey)).mFlags |= 32;
                                if (task2.inSplitScreenWindowingMode() && ((ChangeInfo) this.mChanges.valueAt(indexOfKey)).mResumedAffordance) {
                                    task = task2;
                                }
                            }
                        } else {
                            if (arrayList2.contains(task2)) {
                            }
                            this.mParticipants.add(task2);
                            indexOfKey = this.mChanges.indexOfKey(task2);
                            if (indexOfKey < 0) {
                            }
                            ((ChangeInfo) this.mChanges.valueAt(indexOfKey)).mFlags |= 32;
                            if (task2.inSplitScreenWindowingMode()) {
                                task = task2;
                            }
                        }
                    }
                }
                this.mController.mLatestOnTopTasksReported.put(i3, arrayList);
                arrayList = arrayList2 != null ? arrayList2 : new ArrayList();
                arrayList.clear();
            }
        }
    }

    public final void postCleanupOnFailure() {
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
            Slog.d("Transition", "postCleanupOnFailure, transit=" + this + ", caller=" + Debug.getCallers(3));
        }
        this.mController.mAtm.f1734mH.post(new Runnable() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                Transition.this.lambda$postCleanupOnFailure$7();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postCleanupOnFailure$7() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mController.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                cleanUpOnFailure();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void cleanUpOnFailure() {
        if (this.mState < 2) {
            return;
        }
        SurfaceControl.Transaction transaction = this.mStartTransaction;
        if (transaction != null) {
            transaction.apply();
        }
        SurfaceControl.Transaction transaction2 = this.mFinishTransaction;
        if (transaction2 != null) {
            transaction2.apply();
        }
        this.mController.finishTransition(this);
    }

    public final void cleanUpInternal() {
        SurfaceControl surfaceControl;
        for (int i = 0; i < this.mChanges.size(); i++) {
            ChangeInfo changeInfo = (ChangeInfo) this.mChanges.valueAt(i);
            SurfaceControl surfaceControl2 = changeInfo.mSnapshot;
            if (surfaceControl2 != null) {
                surfaceControl2.release();
            }
            if (CoreRune.MW_SHELL_CHANGE_TRANSITION && (surfaceControl = changeInfo.mChangeLeash) != null) {
                surfaceControl.release();
                changeInfo.mChangeLeash = null;
            }
            if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
                changeInfo.mMinimizeAnimState = 0;
                changeInfo.mMinimizePoint.set(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
            }
            Task asTask = changeInfo.mContainer.asTask();
            if (asTask != null) {
                asTask.setBoostTaskLayerForFreeform(false);
            }
        }
        SurfaceControl.Transaction transaction = this.mCleanupTransaction;
        if (transaction != null) {
            transaction.apply();
            this.mCleanupTransaction = null;
        }
    }

    public final void commitVisibleActivities(SurfaceControl.Transaction transaction) {
        for (int size = this.mParticipants.size() - 1; size >= 0; size--) {
            ActivityRecord asActivityRecord = ((WindowContainer) this.mParticipants.valueAt(size)).asActivityRecord();
            if (asActivityRecord != null && asActivityRecord.getTask() != null) {
                if (asActivityRecord.isVisibleRequested()) {
                    asActivityRecord.commitVisibility(true, false, true);
                    asActivityRecord.commitFinishDrawing(transaction);
                }
                asActivityRecord.getTask().setDeferTaskAppear(false);
            }
        }
    }

    public final void commitVisibleWallpapers() {
        boolean shouldWallpaperBeVisible = shouldWallpaperBeVisible();
        for (int size = this.mParticipants.size() - 1; size >= 0; size--) {
            WallpaperWindowToken asWallpaperToken = ((WindowContainer) this.mParticipants.valueAt(size)).asWallpaperToken();
            if (asWallpaperToken != null) {
                asWallpaperToken.waitingToShow = false;
                if (!asWallpaperToken.isVisible() && asWallpaperToken.isVisibleRequested()) {
                    asWallpaperToken.commitVisibility(shouldWallpaperBeVisible);
                }
                if (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED && shouldWallpaperBeVisible) {
                    CustomLetterboxConfiguration.performEnhancedControllerIfNonNull(asWallpaperToken.mDisplayContent, new Consumer() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda18
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((CustomLetterboxEnhancedController) obj).onCommitVisibleWallpapers();
                        }
                    });
                }
            }
        }
    }

    public boolean shouldWallpaperBeVisible() {
        for (int size = this.mParticipants.size() - 1; size >= 0; size--) {
            if (((WindowContainer) this.mParticipants.valueAt(size)).showWallpaper()) {
                return true;
            }
        }
        return false;
    }

    public final void handleLegacyRecentsStartBehavior(DisplayContent displayContent, TransitionInfo transitionInfo) {
        WindowState navigationBar;
        WindowToken windowToken;
        Task fromWindowContainerToken;
        if ((this.mFlags & 128) == 0) {
            return;
        }
        InputConsumerImpl inputConsumer = displayContent.getInputMonitor().getInputConsumer("recents_animation_input_consumer");
        WindowContainer windowContainer = null;
        ActivityRecord activityRecord = null;
        if (inputConsumer != null) {
            ActivityRecord activityRecord2 = null;
            for (int i = 0; i < transitionInfo.getChanges().size(); i++) {
                TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
                if (change.getTaskInfo() != null && (fromWindowContainerToken = Task.fromWindowContainerToken(((TransitionInfo.Change) transitionInfo.getChanges().get(i)).getTaskInfo().token)) != null) {
                    int i2 = change.getTaskInfo().topActivityType;
                    boolean z = i2 == 2 || i2 == 3;
                    if (z && activityRecord == null) {
                        activityRecord = fromWindowContainerToken.getTopVisibleActivity();
                    } else if (!z && activityRecord2 == null) {
                        activityRecord2 = fromWindowContainerToken.getTopNonFinishingActivity();
                    }
                }
            }
            if (activityRecord != null && activityRecord2 != null) {
                inputConsumer.mWindowHandle.touchableRegion.set(activityRecord2.getBounds());
                displayContent.getInputMonitor().setActiveRecents(activityRecord, activityRecord2);
            }
        }
        if (activityRecord == null) {
            return;
        }
        this.mRecentsDisplayId = displayContent.mDisplayId;
        if (displayContent.getDisplayPolicy().shouldAttachNavBarToAppDuringTransition() && displayContent.getAsyncRotationController() == null) {
            for (int i3 = 0; i3 < transitionInfo.getChanges().size(); i3++) {
                TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(i3);
                if (change2.getTaskInfo() != null && change2.getTaskInfo().displayId == this.mRecentsDisplayId && change2.getTaskInfo().getActivityType() == 1 && (change2.getMode() == 2 || change2.getMode() == 4)) {
                    windowContainer = WindowContainer.fromBinder(change2.getContainer().asBinder());
                    break;
                }
            }
            if (windowContainer == null || windowContainer.inMultiWindowMode() || (navigationBar = displayContent.getDisplayPolicy().getNavigationBar()) == null || (windowToken = navigationBar.mToken) == null) {
                return;
            }
            this.mController.mNavigationBarAttachedToApp = true;
            windowToken.cancelAnimation();
            SurfaceControl.Transaction pendingTransaction = navigationBar.mToken.getPendingTransaction();
            SurfaceControl surfaceControl = navigationBar.mToken.getSurfaceControl();
            pendingTransaction.reparent(surfaceControl, windowContainer.getSurfaceControl());
            pendingTransaction.show(surfaceControl);
            DisplayArea.Tokens imeContainer = displayContent.getImeContainer();
            if (imeContainer.isVisible()) {
                pendingTransaction.setRelativeLayer(surfaceControl, imeContainer.getSurfaceControl(), 1);
            } else {
                pendingTransaction.setLayer(surfaceControl, Integer.MAX_VALUE);
            }
            StatusBarManagerInternal statusBarManagerInternal = displayContent.getDisplayPolicy().getStatusBarManagerInternal();
            if (statusBarManagerInternal != null) {
                statusBarManagerInternal.setNavigationBarLumaSamplingEnabled(this.mRecentsDisplayId, false);
            }
        }
    }

    public void legacyRestoreNavigationBarFromApp() {
        TransitionController transitionController = this.mController;
        if (transitionController.mNavigationBarAttachedToApp) {
            boolean z = false;
            transitionController.mNavigationBarAttachedToApp = false;
            if (this.mRecentsDisplayId == -1) {
                Slog.e("Transition", "Reparented navigation bar without a valid display");
                this.mRecentsDisplayId = 0;
            }
            DisplayContent displayContent = this.mController.mAtm.mRootWindowContainer.getDisplayContent(this.mRecentsDisplayId);
            StatusBarManagerInternal statusBarManagerInternal = displayContent.getDisplayPolicy().getStatusBarManagerInternal();
            if (statusBarManagerInternal != null) {
                statusBarManagerInternal.setNavigationBarLumaSamplingEnabled(this.mRecentsDisplayId, true);
            }
            WindowState navigationBar = displayContent.getDisplayPolicy().getNavigationBar();
            if (navigationBar == null) {
                return;
            }
            navigationBar.setSurfaceTranslationY(0);
            WindowToken windowToken = navigationBar.mToken;
            if (windowToken == null) {
                return;
            }
            SurfaceControl.Transaction pendingTransaction = displayContent.getPendingTransaction();
            WindowContainer parent = windowToken.getParent();
            pendingTransaction.setLayer(windowToken.getSurfaceControl(), windowToken.getLastLayer());
            int i = 0;
            while (true) {
                if (i < this.mTargets.size()) {
                    Task asTask = ((ChangeInfo) this.mTargets.get(i)).mContainer.asTask();
                    if (asTask != null && asTask.isActivityTypeHomeOrRecents()) {
                        z = asTask.isVisibleRequested();
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
            if (z) {
                new NavBarFadeAnimationController(displayContent).fadeWindowToken(true);
            } else {
                pendingTransaction.reparent(windowToken.getSurfaceControl(), parent.getSurfaceControl());
            }
            displayContent.mWmService.scheduleAnimationLocked();
        }
    }

    public final void reportStartReasonsToLogger() {
        int i;
        ArrayMap arrayMap = new ArrayMap();
        for (int size = this.mParticipants.size() - 1; size >= 0; size--) {
            ActivityRecord asActivityRecord = ((WindowContainer) this.mParticipants.valueAt(size)).asActivityRecord();
            if (asActivityRecord != null && asActivityRecord.isVisibleRequested()) {
                if (!(asActivityRecord.mStartingData instanceof SplashScreenStartingData) || asActivityRecord.mLastAllReadyAtSync) {
                    i = (asActivityRecord.isActivityTypeHomeOrRecents() && isTransientLaunch(asActivityRecord)) ? 5 : 2;
                } else {
                    i = 1;
                }
                arrayMap.put(asActivityRecord, Integer.valueOf(i));
            }
        }
        this.mController.mAtm.mTaskSupervisor.getActivityMetricsLogger().notifyTransitionStarting(arrayMap);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("TransitionRecord{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" id=" + this.mSyncId);
        sb.append(" type=" + WindowManager.transitTypeToString(this.mType));
        sb.append(" flags=0x" + Integer.toHexString(this.mFlags));
        sb.append('}');
        return sb.toString();
    }

    public static WindowContainer getAnimatableParent(WindowContainer windowContainer) {
        WindowContainer parent = windowContainer.getParent();
        while (parent != null && !parent.canCreateRemoteAnimationTarget() && !parent.isOrganized()) {
            parent = parent.getParent();
        }
        return parent;
    }

    public static boolean reportIfNotTop(WindowContainer windowContainer) {
        return windowContainer.isOrganized();
    }

    public static boolean isWallpaper(WindowContainer windowContainer) {
        return windowContainer.asWallpaperToken() != null;
    }

    public static boolean isInputMethod(WindowContainer windowContainer) {
        return windowContainer.getWindowType() == 2011;
    }

    public static boolean isTranslucent(WindowContainer windowContainer) {
        TaskFragment asTaskFragment = windowContainer.asTaskFragment();
        if (asTaskFragment == null) {
            return !windowContainer.fillsParent();
        }
        if (asTaskFragment.isTranslucentForTransition()) {
            return true;
        }
        TaskFragment adjacentTaskFragment = asTaskFragment.getAdjacentTaskFragment();
        if (adjacentTaskFragment != null) {
            return adjacentTaskFragment.isTranslucentForTransition();
        }
        return !windowContainer.fillsParent();
    }

    public final void updatePriorVisibility() {
        for (int i = 0; i < this.mChanges.size(); i++) {
            ChangeInfo changeInfo = (ChangeInfo) this.mChanges.valueAt(i);
            if ((changeInfo.mContainer.asActivityRecord() != null || changeInfo.mContainer.asTask() != null) && changeInfo.mVisible) {
                changeInfo.mVisible = changeInfo.mContainer.isVisible();
            }
        }
    }

    public static boolean canPromote(ChangeInfo changeInfo, Targets targets, ArrayMap arrayMap) {
        WindowContainer windowContainer = changeInfo.mContainer;
        WindowContainer parent = windowContainer.getParent();
        ChangeInfo changeInfo2 = (ChangeInfo) arrayMap.get(parent);
        if (!parent.canCreateRemoteAnimationTarget() || changeInfo2 == null || !changeInfo2.hasChanged()) {
            if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 744171317, 0, (String) null, new Object[]{String.valueOf("parent can't be target " + parent)});
            }
            return false;
        }
        if (isWallpaper(windowContainer)) {
            if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -2036671725, 0, (String) null, (Object[]) null);
            }
            return false;
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && isTransientLaunchOverlay(windowContainer)) {
            return false;
        }
        if (changeInfo.mStartParent != null && windowContainer.getParent() != changeInfo.mStartParent) {
            return false;
        }
        int transitMode = changeInfo.getTransitMode(windowContainer);
        for (int childCount = parent.getChildCount() - 1; childCount >= 0; childCount--) {
            WindowContainer childAt = parent.getChildAt(childCount);
            if (windowContainer != childAt) {
                if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -703543418, 0, (String) null, new Object[]{String.valueOf(childAt)});
                }
                ChangeInfo changeInfo3 = (ChangeInfo) arrayMap.get(childAt);
                if (changeInfo3 == null || !targets.wasParticipated(changeInfo3)) {
                    if (childAt.isVisibleRequested()) {
                        if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 793568608, 0, (String) null, (Object[]) null);
                        }
                        return false;
                    }
                    if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -1728919185, 0, (String) null, new Object[]{String.valueOf(childAt)});
                    }
                } else {
                    int transitMode2 = changeInfo3.getTransitMode(childAt);
                    if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -779095785, 0, (String) null, new Object[]{String.valueOf(TransitionInfo.modeToString(transitMode2))});
                    }
                    if (reduceMode(transitMode) != reduceMode(transitMode2)) {
                        if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 1469310004, 0, (String) null, new Object[]{String.valueOf(TransitionInfo.modeToString(transitMode))});
                        }
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void tryPromote(Targets targets, ArrayMap arrayMap) {
        int size = targets.mArray.size() - 1;
        WindowContainer windowContainer = null;
        while (size >= 0) {
            ChangeInfo changeInfo = (ChangeInfo) targets.mArray.valueAt(size);
            WindowContainer windowContainer2 = changeInfo.mContainer;
            if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -509601642, 0, (String) null, new Object[]{String.valueOf(windowContainer2)});
            }
            WindowContainer parent = windowContainer2.getParent();
            if (parent == windowContainer) {
                if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 112145970, 0, (String) null, (Object[]) null);
                }
            } else if (canPromote(changeInfo, targets, arrayMap)) {
                if (reportIfNotTop(windowContainer2)) {
                    if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 528150092, 0, (String) null, new Object[]{String.valueOf(windowContainer2)});
                    }
                } else {
                    if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 182319432, 0, (String) null, new Object[]{String.valueOf(windowContainer2)});
                    }
                    targets.remove(size);
                }
                ChangeInfo changeInfo2 = (ChangeInfo) arrayMap.get(parent);
                if (targets.mArray.indexOfValue(changeInfo2) < 0) {
                    if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -1452274694, 0, (String) null, new Object[]{String.valueOf(parent)});
                    }
                    size++;
                    targets.add(changeInfo2);
                }
                if ((changeInfo.mFlags & 8) != 0) {
                    changeInfo2.mFlags |= 8;
                } else {
                    changeInfo2.mFlags |= 16;
                }
            } else {
                windowContainer = parent;
            }
            size--;
        }
    }

    public boolean isUnfoldTransition() {
        TransitionRequestInfo transitionRequestInfo = this.mLogger.mRequest;
        if (transitionRequestInfo == null || transitionRequestInfo.getType() != 6 || this.mLogger.mRequest.getDisplayChange() == null) {
            return false;
        }
        return this.mLogger.mRequest.getDisplayChange().isPhysicalDisplayChanged();
    }

    public static ArrayList calculateTargets(ArraySet arraySet, ArrayMap arrayMap) {
        return calculateTargets(arraySet, arrayMap, 0);
    }

    public static ArrayList calculateTargets(ArraySet arraySet, ArrayMap arrayMap, int i) {
        ActivityRecord activityRecord;
        ActivityRecord activityRecord2;
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
            if (ProtoLogCache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS, -1900737188, 0, "Start calculating TransitionInfo based on participants: %s", new Object[]{String.valueOf(arraySet)});
            }
        } else if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 184610856, 0, (String) null, new Object[]{String.valueOf(arraySet)});
        }
        Targets targets = new Targets();
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer = (WindowContainer) arraySet.valueAt(size);
            if (!windowContainer.isAttached()) {
                if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 1494644409, 0, (String) null, new Object[]{String.valueOf(windowContainer)});
                }
            } else if (windowContainer.asWindowState() == null) {
                ChangeInfo changeInfo = (ChangeInfo) arrayMap.get(windowContainer);
                if (!changeInfo.hasChanged()) {
                    if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -672355406, 0, (String) null, new Object[]{String.valueOf(windowContainer)});
                    }
                } else {
                    WindowContainer parent = windowContainer.getParent();
                    if (parent == null || arrayMap.get(parent) == null || !((ChangeInfo) arrayMap.get(parent)).hasChanged() || (changeInfo.mFlags & 262144) == 0) {
                        Task asTask = windowContainer.asTask();
                        if (CoreRune.MW_PIP_SHELL_TRANSITION && i == 6 && asTask != null && asTask.inPinnedWindowingMode() && (activityRecord2 = asTask.topRunningActivityLocked()) != null && activityRecord2.mWaitForEnteringPinnedMode) {
                            Slog.d("Transition", "calculateTargets: Skip to collect entering pip, " + windowContainer + " transit=" + i);
                        } else if (CoreRune.MW_PIP_SHELL_TRANSITION && i == 6 && asTask != null && asTask.inPinnedWindowingMode() && (activityRecord = asTask.topRunningActivityLocked()) != null && activityRecord.isHiddenWhileEnteringPinnedMode()) {
                            Slog.d("Transition", "calculateTargets: Skip to collect entering pip, " + windowContainer);
                        } else if (CoreRune.MW_PIP_SHELL_TRANSITION && parent != null && parent.inPinnedWindowingMode() && parent.isEmbedded() && arrayMap.get(parent) != null && !((ChangeInfo) arrayMap.get(parent)).hasChanged()) {
                            Slog.d("Transition", "skip promote to task fragment. parents has no changes. wc=" + windowContainer);
                        } else {
                            ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
                            if (CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT && i == 6 && asActivityRecord != null && asActivityRecord.inFullscreenWindowingMode() && !asActivityRecord.providesOrientation() && asActivityRecord.mCompatRecord.mIsTaskOrientationMismatched) {
                                Slog.d("Transition", "calculateTargets: skip to collect during orientation compat. wc=" + windowContainer);
                            } else if (asActivityRecord != null && asActivityRecord.getTask() != null && asActivityRecord.getTask().mIsAnimatingByRecentsAndDragSourceTask) {
                                asActivityRecord.getTask().mIsAnimatingByRecentsAndDragSourceTask = false;
                                Slog.d("Transition", "calculateTargets: skip to collect during drag-and-drop. wc=" + windowContainer);
                            } else {
                                targets.add(changeInfo);
                            }
                        }
                    }
                }
            }
        }
        if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -1844540996, 0, (String) null, new Object[]{String.valueOf(targets.mArray)});
        }
        tryPromote(targets, arrayMap);
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION && targets.mRemovedTargets != null) {
            targets.mRemovedTargets.stream().forEach(new Consumer() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Transition.lambda$calculateTargets$9((Transition.ChangeInfo) obj);
                }
            });
        }
        populateParentChanges(targets, arrayMap);
        ArrayList listSortedByZ = targets.getListSortedByZ();
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
            if (ProtoLogCache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS, 294214946, 0, "  Final targets: %s", new Object[]{String.valueOf(listSortedByZ)});
            }
        } else if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 405146734, 0, (String) null, new Object[]{String.valueOf(listSortedByZ)});
        }
        return listSortedByZ;
    }

    public static /* synthetic */ void lambda$calculateTargets$9(ChangeInfo changeInfo) {
        ChangeTransitionController.restoreFromChangeLeash(changeInfo, new BiFunction() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                SurfaceControl leashSurface;
                leashSurface = Transition.getLeashSurface((WindowContainer) obj, (SurfaceControl.Transaction) obj2);
                return leashSurface;
            }
        }, new Function() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                SurfaceControl origParentSurface;
                origParentSurface = Transition.getOrigParentSurface((WindowContainer) obj);
                return origParentSurface;
            }
        });
    }

    public static void populateParentChanges(Targets targets, ArrayMap arrayMap) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList(targets.mArray.size());
        for (int size = targets.mArray.size() - 1; size >= 0; size--) {
            arrayList2.add((ChangeInfo) targets.mArray.valueAt(size));
        }
        for (int size2 = arrayList2.size() - 1; size2 >= 0; size2--) {
            ChangeInfo changeInfo = (ChangeInfo) arrayList2.get(size2);
            WindowContainer windowContainer = changeInfo.mContainer;
            int i = 0;
            boolean z2 = isWallpaper(windowContainer) || (CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && isTransientLaunchOverlay(windowContainer));
            arrayList.clear();
            WindowContainer animatableParent = getAnimatableParent(windowContainer);
            while (true) {
                if (animatableParent == null) {
                    break;
                }
                ChangeInfo changeInfo2 = (ChangeInfo) arrayMap.get(animatableParent);
                if (changeInfo2 == null || !changeInfo2.hasChanged()) {
                    break;
                }
                if (animatableParent.mRemoteToken != null) {
                    if (changeInfo2.mEndParent != null && !z2) {
                        changeInfo.mEndParent = animatableParent;
                        break;
                    }
                    if (arrayList2.contains(changeInfo2)) {
                        if (z2) {
                            changeInfo.mEndParent = animatableParent;
                        } else {
                            arrayList.add(changeInfo2);
                        }
                        z = true;
                    } else if (reportIfNotTop(animatableParent) && !z2) {
                        arrayList.add(changeInfo2);
                    }
                }
                animatableParent = getAnimatableParent(animatableParent);
            }
            z = false;
            if (z && !arrayList.isEmpty()) {
                changeInfo.mEndParent = ((ChangeInfo) arrayList.get(0)).mContainer;
                while (i < arrayList.size() - 1) {
                    ChangeInfo changeInfo3 = (ChangeInfo) arrayList.get(i);
                    i++;
                    changeInfo3.mEndParent = ((ChangeInfo) arrayList.get(i)).mContainer;
                    targets.add(changeInfo3);
                }
            }
        }
    }

    public static SurfaceControl getLeashSurface(WindowContainer windowContainer, SurfaceControl.Transaction transaction) {
        WindowToken asWindowToken;
        SurfaceControl fixedRotationLeash;
        DisplayContent asDisplayContent = windowContainer.asDisplayContent();
        if (asDisplayContent != null) {
            return asDisplayContent.getWindowingLayer();
        }
        if (!windowContainer.mTransitionController.useShellTransitionsRotation() && (asWindowToken = windowContainer.asWindowToken()) != null) {
            if (transaction != null) {
                fixedRotationLeash = asWindowToken.getOrCreateFixedRotationLeash(transaction);
            } else {
                fixedRotationLeash = asWindowToken.getFixedRotationLeash();
            }
            if (fixedRotationLeash != null) {
                return fixedRotationLeash;
            }
        }
        return windowContainer.getSurfaceControl();
    }

    public static SurfaceControl getOrigParentSurface(WindowContainer windowContainer) {
        if (windowContainer.asDisplayContent() != null) {
            return windowContainer.getSurfaceControl();
        }
        if (windowContainer.getParent().asDisplayContent() != null) {
            return windowContainer.getParent().asDisplayContent().getWindowingLayer();
        }
        return windowContainer.getParent().getSurfaceControl();
    }

    public static boolean isReadyGroup(WindowContainer windowContainer) {
        return windowContainer instanceof DisplayContent;
    }

    public static int getDisplayId(WindowContainer windowContainer) {
        if (windowContainer.getDisplayContent() != null) {
            return windowContainer.getDisplayContent().getDisplayId();
        }
        return -1;
    }

    public static void calculateTransitionRoots(TransitionInfo transitionInfo, ArrayList arrayList, SurfaceControl.Transaction transaction) {
        int displayId;
        for (int i = 0; i < arrayList.size(); i++) {
            WindowContainer windowContainer = ((ChangeInfo) arrayList.get(i)).mContainer;
            if (!isWallpaper(windowContainer) && ((!CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY || !isTransientLaunchOverlay(windowContainer)) && (displayId = getDisplayId(windowContainer)) >= 0 && transitionInfo.findRootIndex(displayId) < 0)) {
                WindowContainer findCommonAncestor = findCommonAncestor(arrayList, windowContainer);
                if (!windowContainer.isDescendantOf(findCommonAncestor)) {
                    Slog.e("Transition", "Did not find common ancestor! Ancestor= " + findCommonAncestor + " target= " + windowContainer);
                } else {
                    while (windowContainer.getParent() != findCommonAncestor) {
                        windowContainer = windowContainer.getParent();
                    }
                }
                SurfaceControl build = windowContainer.makeAnimationLeash().setName("Transition Root: " + windowContainer.getName()).build();
                build.setUnreleasedWarningCallSite("Transition.calculateTransitionRoots");
                transaction.setLayer(build, windowContainer.getLastLayer());
                if (CoreRune.MW_SHELL_TRANSITION) {
                    transitionInfo.addRootLeash(displayId, build, findCommonAncestor.getBounds().left, findCommonAncestor.getBounds().top, windowContainer.getConfiguration(), windowContainer.asActivityRecord() != null);
                } else {
                    transitionInfo.addRootLeash(displayId, build, findCommonAncestor.getBounds().left, findCommonAncestor.getBounds().top);
                }
            }
        }
    }

    public static TransitionInfo calculateTransitionInfo(int i, int i2, ArrayList arrayList, SurfaceControl.Transaction transaction) {
        WindowContainer windowContainer;
        TransitionInfo.AnimationOptions animationOptions;
        TaskFragment organizedTaskFragment;
        Task task;
        int backgroundColor;
        ArrayList arrayList2 = arrayList;
        TransitionInfo transitionInfo = new TransitionInfo(i, i2);
        calculateTransitionRoots(transitionInfo, arrayList2, transaction);
        if (transitionInfo.getRootCount() == 0) {
            return transitionInfo;
        }
        WindowManager.LayoutParams layoutParamsForAnimationsStyle = getLayoutParamsForAnimationsStyle(i, arrayList2);
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            ChangeInfo changeInfo = (ChangeInfo) arrayList2.get(i3);
            WindowContainer windowContainer2 = changeInfo.mContainer;
            WindowContainer.RemoteToken remoteToken = windowContainer2.mRemoteToken;
            TransitionInfo.Change change = new TransitionInfo.Change(remoteToken != null ? remoteToken.toWindowContainerToken() : null, getLeashSurface(windowContainer2, transaction));
            if (changeInfo.mResumedAffordance) {
                change.setResumedAffordance(true);
            }
            if (changeInfo.mResumedAffordance) {
                change.setAffordanceTargetFreeformTask(changeInfo.mAffordanceTargetFreeformTask);
            }
            WindowContainer windowContainer3 = changeInfo.mEndParent;
            if (windowContainer3 != null) {
                change.setParent(windowContainer3.mRemoteToken.toWindowContainerToken());
            }
            WindowContainer windowContainer4 = changeInfo.mStartParent;
            if (windowContainer4 != null && windowContainer4.mRemoteToken != null) {
                WindowContainer parent = windowContainer2.getParent();
                WindowContainer windowContainer5 = changeInfo.mStartParent;
                if (parent != windowContainer5) {
                    change.setLastParent(windowContainer5.mRemoteToken.toWindowContainerToken());
                }
            }
            change.setMode(changeInfo.getTransitMode(windowContainer2));
            if (CoreRune.MW_SPLIT_SHELL_TRANSITION && changeInfo.needChangeForSplitTask() && change.getMode() != 1 && change.getMode() != 3 && change.getMode() != 6) {
                change.setMode(6);
            }
            changeInfo.mReadyMode = change.getMode();
            change.setStartAbsBounds(changeInfo.mAbsoluteBounds);
            change.setFlags(changeInfo.getChangeFlags(windowContainer2));
            changeInfo.mReadyFlags = change.getFlags();
            change.setDisplayId(changeInfo.mDisplayId, getDisplayId(windowContainer2));
            Task asTask = windowContainer2.asTask();
            TaskFragment asTaskFragment = windowContainer2.asTaskFragment();
            ActivityRecord asActivityRecord = windowContainer2.asActivityRecord();
            if (asTask != null) {
                ActivityManager.RunningTaskInfo runningTaskInfo = new ActivityManager.RunningTaskInfo();
                asTask.fillTaskInfo(runningTaskInfo);
                change.setTaskInfo(runningTaskInfo);
                change.setRotationAnimation(getTaskRotationAnimation(asTask));
                ActivityRecord activityRecord = asTask.topRunningActivity();
                if (activityRecord != null) {
                    if (activityRecord.info.supportsPictureInPicture()) {
                        change.setAllowEnterPip(activityRecord.checkEnterPictureInPictureAppOpsState());
                    }
                    setEndFixedRotationIfNeeded(change, asTask, activityRecord);
                }
                Rect loadInsetsForRecentTransition = loadInsetsForRecentTransition(asTask);
                if (loadInsetsForRecentTransition != null) {
                    change.setInsetsForRecentsTransition(loadInsetsForRecentTransition);
                }
            } else if ((changeInfo.mFlags & 1) != 0) {
                change.setRotationAnimation(3);
            }
            WindowContainer parent2 = windowContainer2.getParent();
            Rect bounds = windowContainer2.getBounds();
            Rect bounds2 = parent2.getBounds();
            int i4 = size;
            int i5 = i3;
            change.setEndRelOffset(bounds.left - bounds2.left, bounds.top - bounds2.top);
            int rotation = windowContainer2.getWindowConfiguration().getRotation();
            windowContainer2.getWindowConfiguration();
            if (asActivityRecord != null && !asActivityRecord.mPopOverState.isActivated()) {
                change.setEndAbsBounds(bounds2);
                if (asActivityRecord.getRelativeDisplayRotation() != 0 && !asActivityRecord.mTransitionController.useShellTransitionsRotation()) {
                    rotation = parent2.getWindowConfiguration().getRotation();
                }
            } else {
                change.setEndAbsBounds(bounds);
            }
            if (asActivityRecord != null && asActivityRecord.mPopOverState.isActivated()) {
                boolean z = i == 1 && change.getMode() == 2;
                ActivityRecord topMostActivity = asActivityRecord.getTask() != null ? asActivityRecord.getTask().getTopMostActivity() : null;
                change.setPopOverAnimationNeeded(!((z || (topMostActivity != null ? topMostActivity.mPopOverState.isActivated() : false)) ? false : true));
            }
            if (changeInfo.mChangeTransitMode == 5 && asTask == null && asActivityRecord != null && asActivityRecord.getTask() != null) {
                change.setEndAbsBounds(bounds);
            }
            if (asActivityRecord != null || (asTaskFragment != null && asTaskFragment.isEmbedded())) {
                if (asActivityRecord != null) {
                    organizedTaskFragment = asActivityRecord.getOrganizedTaskFragment();
                } else {
                    organizedTaskFragment = asTaskFragment.getOrganizedTaskFragment();
                }
                if (organizedTaskFragment != null && organizedTaskFragment.getAnimationParams().getAnimationBackgroundColor() != 0) {
                    backgroundColor = organizedTaskFragment.getAnimationParams().getAnimationBackgroundColor();
                } else {
                    if (asActivityRecord != null) {
                        task = asActivityRecord.getTask();
                    } else {
                        task = asTaskFragment.getTask();
                    }
                    backgroundColor = task.getTaskDescription().getBackgroundColor();
                }
                change.setBackgroundColor(ColorUtils.setAlphaComponent(backgroundColor, IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT));
            }
            change.setRotation(changeInfo.mRotation, rotation);
            SurfaceControl surfaceControl = changeInfo.mSnapshot;
            if (surfaceControl != null) {
                change.setSnapshot(surfaceControl, changeInfo.mSnapshotLuma);
            }
            DisplayContent asDisplayContent = windowContainer2.asDisplayContent();
            if (CoreRune.MW_SHELL_TRANSITION) {
                change.setConfiguration(windowContainer2.getConfiguration());
            }
            if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                change.setChangeLeash(changeInfo.mChangeLeash);
                change.setChangeTransitMode(changeInfo.mChangeTransitMode);
                windowContainer2.mWmService.mAtmService.mChangeTransitController.updateChangeOutsetsIfNeeded(change, changeInfo, windowContainer2);
            }
            if (CoreRune.MW_PIP_SHELL_TRANSITION && asTask != null && asTask.inPinnedWindowingMode() && asTask.getActivity(new Predicate() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean z2;
                    z2 = ((ActivityRecord) obj).mWaitForEnteringPinnedMode;
                    return z2;
                }
            }) != null) {
                change.setEnteringPinnedMode(true);
            }
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_WITH_DIM && windowContainer2.mDimAnimator.canCreateDimAnimationLayer(i, windowContainer2.isVisibleRequested(), layoutParamsForAnimationsStyle, change)) {
                change.setTransitionWithDim(true);
            }
            if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
                change.setMinimizeAnimState(changeInfo.mMinimizeAnimState);
                change.setMinimizePoint(changeInfo.mMinimizePoint);
            }
            if (asDisplayContent != null && asDisplayContent.isFadeInOutAnimationNeeded()) {
                change.setFadeInOutRotationNeeded();
                asDisplayContent.setFadeInOutAnimationNeeded(false, "transition_info_created");
            }
            if (asTask != null && asTask.inFreeformWindowingMode() && asTask.isFreeformStashed()) {
                change.setFreeformStashScale(asTask.getFreeformStashScale());
            }
            if (changeInfo.mForceHidingTransit != 0) {
                TaskDisplayArea taskDisplayArea = windowContainer2.getTaskDisplayArea();
                change.setForceHidingTransit(changeInfo.mForceHidingTransit);
                if (taskDisplayArea != null && !taskDisplayArea.isNewDexMode()) {
                    transaction.reparent(change.getLeash(), taskDisplayArea.mFloatingLeashAnchor);
                    transaction.setLayer(change.getLeash(), windowContainer2.getLastLayer());
                }
            }
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION && windowContainer2.shouldSkipTransition()) {
                transitionInfo.setFlags(transitionInfo.getFlags() | 1024);
            }
            transitionInfo.addChange(change);
            i3 = i5 + 1;
            arrayList2 = arrayList;
            size = i4;
        }
        for (int i6 = 0; i6 < arrayList.size(); i6++) {
            if (!isWallpaper(((ChangeInfo) arrayList.get(i6)).mContainer) && (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY || !isTransientLaunchOverlay(((ChangeInfo) arrayList.get(i6)).mContainer))) {
                windowContainer = ((ChangeInfo) arrayList.get(i6)).mContainer;
                break;
            }
        }
        windowContainer = null;
        if (windowContainer.asActivityRecord() != null) {
            ActivityRecord asActivityRecord2 = windowContainer.asActivityRecord();
            animationOptions = addCustomActivityTransition(asActivityRecord2, false, addCustomActivityTransition(asActivityRecord2, true, null));
        } else {
            animationOptions = null;
        }
        if (layoutParamsForAnimationsStyle != null && layoutParamsForAnimationsStyle.type != 3 && (layoutParamsForAnimationsStyle.windowAnimations != 0 || CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX)) {
            if (animationOptions != null) {
                animationOptions.addOptionsFromLayoutParameters(layoutParamsForAnimationsStyle);
            } else {
                animationOptions = TransitionInfo.AnimationOptions.makeAnimOptionsFromLayoutParameters(layoutParamsForAnimationsStyle);
            }
        }
        if (animationOptions != null) {
            transitionInfo.setAnimationOptions(animationOptions);
        }
        return transitionInfo;
    }

    public static TransitionInfo.AnimationOptions addCustomActivityTransition(ActivityRecord activityRecord, boolean z, TransitionInfo.AnimationOptions animationOptions) {
        ActivityRecord.CustomAppTransition customAnimation = activityRecord.getCustomAnimation(z);
        if (customAnimation != null) {
            if (animationOptions == null) {
                animationOptions = TransitionInfo.AnimationOptions.makeCommonAnimOptions(activityRecord.packageName);
            }
            animationOptions.addCustomActivityTransition(z, customAnimation.mEnterAnim, customAnimation.mExitAnim, customAnimation.mBackgroundColor);
        }
        return animationOptions;
    }

    public static void setEndFixedRotationIfNeeded(TransitionInfo.Change change, Task task, ActivityRecord activityRecord) {
        WindowContainer lastOrientationSource;
        int displayRotation;
        if (activityRecord.isVisibleRequested()) {
            if (task.inMultiWindowMode() && activityRecord.inMultiWindowMode()) {
                return;
            }
            int displayRotation2 = task.getWindowConfiguration().getDisplayRotation();
            int displayRotation3 = activityRecord.getWindowConfiguration().getDisplayRotation();
            if (displayRotation2 != displayRotation3) {
                change.setEndFixedRotation(displayRotation3);
            } else {
                if (!task.inPinnedWindowingMode() || activityRecord.mDisplayContent.inTransition() || (lastOrientationSource = activityRecord.mDisplayContent.getLastOrientationSource()) == null || displayRotation2 == (displayRotation = lastOrientationSource.getWindowConfiguration().getDisplayRotation())) {
                    return;
                }
                change.setEndFixedRotation(displayRotation);
            }
        }
    }

    public static WindowContainer findCommonAncestor(ArrayList arrayList, WindowContainer windowContainer) {
        int transitMode;
        int displayId = getDisplayId(windowContainer);
        WindowContainer parent = windowContainer.getParent();
        if (CoreRune.MT_NEW_DEX_SHELL_TRANSITION && windowContainer.isNewDexMode() && useCommonAncestorFromAffordanceTask(arrayList, windowContainer)) {
            Slog.d("Transition", "useCommonAncestorFromAffordanceTask: " + windowContainer);
            return parent;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ChangeInfo changeInfo = (ChangeInfo) arrayList.get(size);
            WindowContainer windowContainer2 = changeInfo.mContainer;
            if (!isWallpaper(windowContainer2) && getDisplayId(windowContainer2) == displayId && (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY || !isTransientLaunchOverlay(windowContainer2))) {
                if (changeInfo.mStartParent != null && windowContainer2.getParent() != null && changeInfo.mStartParent.isAttached() && windowContainer2.getParent() != changeInfo.mStartParent && size == arrayList.size() - 1 && ((transitMode = changeInfo.getTransitMode(windowContainer2)) == 2 || transitMode == 4)) {
                    parent = changeInfo.mStartParent;
                } else {
                    while (!windowContainer2.isDescendantOf(parent)) {
                        parent = parent.getParent();
                    }
                    WindowContainer windowContainer3 = changeInfo.mCommonAncestor;
                    if (windowContainer3 != null && windowContainer3.isAttached()) {
                        while (windowContainer3 != parent && !windowContainer3.isDescendantOf(parent)) {
                            parent = parent.getParent();
                        }
                    }
                }
            }
        }
        return parent;
    }

    public static boolean useCommonAncestorFromAffordanceTask(ArrayList arrayList, WindowContainer windowContainer) {
        boolean z = false;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ChangeInfo changeInfo = (ChangeInfo) arrayList.get(size);
            if (changeInfo.mContainer.inSplitScreenWindowingMode() && changeInfo.mResumedAffordance && changeInfo.mContainer == windowContainer) {
                z = true;
            } else if (!changeInfo.mContainer.inFreeformWindowingMode()) {
                return false;
            }
        }
        return z;
    }

    public static WindowManager.LayoutParams getLayoutParamsForAnimationsStyle(int i, ArrayList arrayList) {
        ArraySet arraySet = new ArraySet();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            WindowContainer windowContainer = ((ChangeInfo) arrayList.get(i2)).mContainer;
            if (windowContainer.asActivityRecord() != null) {
                arraySet.add(Integer.valueOf(windowContainer.getActivityType()));
            } else if (windowContainer.asWindowToken() == null && windowContainer.asWindowState() == null) {
                return null;
            }
        }
        if (arraySet.isEmpty()) {
            return null;
        }
        ActivityRecord findAnimLayoutParamsActivityRecord = findAnimLayoutParamsActivityRecord(arrayList, i, arraySet);
        WindowState findMainWindow = findAnimLayoutParamsActivityRecord != null ? findAnimLayoutParamsActivityRecord.findMainWindow() : null;
        if (findMainWindow != null) {
            return findMainWindow.mAttrs;
        }
        return null;
    }

    public static ActivityRecord findAnimLayoutParamsActivityRecord(List list, final int i, final ArraySet arraySet) {
        ActivityRecord lookForTopWindowWithFilter = lookForTopWindowWithFilter(list, new Predicate() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$findAnimLayoutParamsActivityRecord$11;
                lambda$findAnimLayoutParamsActivityRecord$11 = Transition.lambda$findAnimLayoutParamsActivityRecord$11(i, arraySet, (ActivityRecord) obj);
                return lambda$findAnimLayoutParamsActivityRecord$11;
            }
        });
        if (lookForTopWindowWithFilter != null) {
            return lookForTopWindowWithFilter;
        }
        ActivityRecord lookForTopWindowWithFilter2 = lookForTopWindowWithFilter(list, new Predicate() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$findAnimLayoutParamsActivityRecord$12;
                lambda$findAnimLayoutParamsActivityRecord$12 = Transition.lambda$findAnimLayoutParamsActivityRecord$12((ActivityRecord) obj);
                return lambda$findAnimLayoutParamsActivityRecord$12;
            }
        });
        return lookForTopWindowWithFilter2 != null ? lookForTopWindowWithFilter2 : lookForTopWindowWithFilter(list, new Predicate() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$findAnimLayoutParamsActivityRecord$13;
                lambda$findAnimLayoutParamsActivityRecord$13 = Transition.lambda$findAnimLayoutParamsActivityRecord$13((ActivityRecord) obj);
                return lambda$findAnimLayoutParamsActivityRecord$13;
            }
        });
    }

    public static /* synthetic */ boolean lambda$findAnimLayoutParamsActivityRecord$11(int i, ArraySet arraySet, ActivityRecord activityRecord) {
        return activityRecord.getRemoteAnimationDefinition() != null && activityRecord.getRemoteAnimationDefinition().hasTransition(i, arraySet);
    }

    public static /* synthetic */ boolean lambda$findAnimLayoutParamsActivityRecord$12(ActivityRecord activityRecord) {
        return activityRecord.fillsParent() && activityRecord.findMainWindow() != null;
    }

    public static /* synthetic */ boolean lambda$findAnimLayoutParamsActivityRecord$13(ActivityRecord activityRecord) {
        return activityRecord.findMainWindow() != null;
    }

    public static ActivityRecord lookForTopWindowWithFilter(List list, Predicate predicate) {
        ActivityRecord asActivityRecord;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            WindowContainer windowContainer = ((ChangeInfo) list.get(i)).mContainer;
            if (windowContainer.asTaskFragment() != null) {
                asActivityRecord = windowContainer.asTaskFragment().getTopNonFinishingActivity();
            } else {
                asActivityRecord = windowContainer.asActivityRecord();
            }
            if (asActivityRecord != null && predicate.test(asActivityRecord)) {
                return asActivityRecord;
            }
        }
        return null;
    }

    public static int getTaskRotationAnimation(Task task) {
        WindowState findMainWindow;
        ActivityRecord topVisibleActivity = task.getTopVisibleActivity();
        if (topVisibleActivity == null || (findMainWindow = topVisibleActivity.findMainWindow(false)) == null) {
            return -1;
        }
        int rotationAnimationHint = findMainWindow.getRotationAnimationHint();
        if (rotationAnimationHint >= 0) {
            return rotationAnimationHint;
        }
        int i = findMainWindow.getAttrs().rotationAnimation;
        if (i != 3) {
            return i;
        }
        if (findMainWindow != task.mDisplayContent.getDisplayPolicy().getTopFullscreenOpaqueWindow() || !topVisibleActivity.matchParentBounds()) {
            return -1;
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && !task.mDisplayContent.getDisplayPolicy().isTopLayoutFullscreen()) {
            return -1;
        }
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION && task.mAtmService.mChangeTransitController.isInChangeTransition(task)) {
            return -1;
        }
        return findMainWindow.getAttrs().rotationAnimation;
    }

    public final void validateKeyguardOcclusion() {
        if ((this.mFlags & 14592) != 0) {
            TransitionController transitionController = this.mController;
            ArrayList arrayList = transitionController.mStateValidators;
            WindowManagerPolicy windowManagerPolicy = transitionController.mAtm.mWindowManager.mPolicy;
            Objects.requireNonNull(windowManagerPolicy);
            arrayList.add(new KeyguardController$$ExternalSyntheticLambda1(windowManagerPolicy));
        }
    }

    public final void validateDisplayVisibility() {
        for (int i = 0; i < this.mTargetDisplays.size(); i++) {
            DisplayContent displayContent = (DisplayContent) this.mTargetDisplays.get(i);
            if (!this.mController.mValidateDisplayVis.contains(Integer.valueOf(displayContent.mDisplayId))) {
                this.mController.mValidateDisplayVis.add(Integer.valueOf(displayContent.mDisplayId));
            }
        }
    }

    public final void validateReparentToDisplay() {
        this.mController.mStateValidators.add(new Runnable() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                Transition.this.lambda$validateReparentToDisplay$14();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$validateReparentToDisplay$14() {
        for (int size = this.mTargets.size() - 1; size >= 0; size--) {
            ChangeInfo changeInfo = (ChangeInfo) this.mTargets.get(size);
            WindowContainer windowContainer = changeInfo.mContainer;
            if (windowContainer.asTask() != null && windowContainer.mSurfaceControl != null && windowContainer.getDisplayContent() != null && windowContainer.getParent() != null && windowContainer.getParent().mSurfaceControl != null && changeInfo.mDisplayId != windowContainer.getDisplayContent().getDisplayId()) {
                Slog.e("Transition", "Force reparent to parent surface for task to move from old display " + changeInfo.mDisplayId + " to current display " + windowContainer.getDisplayContent().getDisplayId() + ", t=" + windowContainer);
                SurfaceControl.Transaction syncTransaction = windowContainer.getSyncTransaction();
                windowContainer.reparentSurfaceControl(syncTransaction, windowContainer.getParent().mSurfaceControl);
                windowContainer.reassignLayer(syncTransaction);
                windowContainer.scheduleAnimation();
            }
        }
    }

    public final void validateWallpaperVisibility() {
        for (int i = 0; i < this.mTargetDisplays.size(); i++) {
            final DisplayContent displayContent = (DisplayContent) this.mTargetDisplays.get(i);
            if (displayContent.isReady() && displayContent.isKeyguardLocked() && displayContent.mWallpaperController.useTopWallpaperAsTarget()) {
                this.mController.mStateValidators.add(new Runnable() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda16
                    @Override // java.lang.Runnable
                    public final void run() {
                        Transition.lambda$validateWallpaperVisibility$15(DisplayContent.this);
                    }
                });
            }
        }
    }

    public static /* synthetic */ void lambda$validateWallpaperVisibility$15(DisplayContent displayContent) {
        displayContent.pendingLayoutChanges |= 4;
        displayContent.mWmService.requestTraversal();
    }

    public void applyDisplayChangeIfNeeded() {
        for (int size = this.mParticipants.size() - 1; size >= 0; size--) {
            DisplayContent asDisplayContent = ((WindowContainer) this.mParticipants.valueAt(size)).asDisplayContent();
            if (asDisplayContent != null && ((ChangeInfo) this.mChanges.get(asDisplayContent)).hasChanged()) {
                asDisplayContent.sendNewConfiguration();
                if (!this.mReadyTracker.mUsed) {
                    setReady(asDisplayContent, true);
                }
            }
        }
    }

    public boolean getLegacyIsReady() {
        return isCollecting() && this.mSyncId >= 0;
    }

    public static void asyncTraceBegin(String str, int i) {
        Trace.asyncTraceForTrackBegin(32L, "Transition", str, i);
    }

    public static void asyncTraceEnd(int i) {
        Trace.asyncTraceForTrackEnd(32L, "Transition", i);
    }

    class ChangeInfo {
        public final Rect mAbsoluteBounds;
        public boolean mAffordanceTargetFreeformTask;
        public SurfaceControl mChangeLeash;
        public int mChangeTransitFlags;
        public int mChangeTransitMode;
        public WindowContainer mCommonAncestor;
        public final WindowContainer mContainer;
        public float mCornerRadius;
        public int mDisplayId;
        public WindowContainer mEndParent;
        public boolean mExistenceChanged;
        public int mFlags;
        public boolean mForceChangeSplitTask;
        public boolean mForceChanged;
        public int mForceHidingTransit;
        public final Rect mFreezeOutsets;
        public boolean mHideWhileTwoHandDragging;
        public boolean mHidingContainerLeash;
        public int mKnownConfigChanges;
        public int mMinimizeAnimState;
        public final PointF mMinimizePoint;
        public boolean mNeedRemoteWallpaperAnim;
        public int mReadyFlags;
        public int mReadyMode;
        public boolean mResumedAffordance;
        public int mRotation;
        public boolean mShowWallpaper;
        public SurfaceControl mSnapshot;
        public float mSnapshotLuma;
        public WindowContainer mStartParent;
        public boolean mVisible;
        public int mWindowingMode;

        public ChangeInfo(WindowContainer windowContainer) {
            this.mExistenceChanged = false;
            Rect rect = new Rect();
            this.mAbsoluteBounds = rect;
            this.mRotation = -1;
            this.mDisplayId = -1;
            this.mFlags = 0;
            this.mChangeLeash = null;
            this.mChangeTransitMode = 0;
            this.mFreezeOutsets = new Rect();
            this.mForceChanged = false;
            this.mMinimizeAnimState = 0;
            this.mMinimizePoint = new PointF();
            this.mResumedAffordance = false;
            this.mAffordanceTargetFreeformTask = false;
            this.mCornerRadius = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
            this.mForceHidingTransit = 0;
            this.mForceChangeSplitTask = false;
            this.mNeedRemoteWallpaperAnim = false;
            this.mContainer = windowContainer;
            this.mVisible = windowContainer.isVisibleRequested();
            this.mWindowingMode = windowContainer.getWindowingMode();
            rect.set(windowContainer.getBounds());
            this.mShowWallpaper = windowContainer.showWallpaper();
            this.mRotation = windowContainer.getWindowConfiguration().getRotation();
            this.mStartParent = windowContainer.getParent();
            this.mDisplayId = Transition.getDisplayId(windowContainer);
        }

        public ChangeInfo(WindowContainer windowContainer, boolean z, boolean z2) {
            this(windowContainer);
            this.mVisible = z;
            this.mExistenceChanged = z2;
            this.mShowWallpaper = false;
        }

        public String toString() {
            return this.mContainer.toString();
        }

        public boolean hasChanged() {
            int i = this.mFlags;
            if ((i & 2) != 0 || (i & 4) != 0) {
                return true;
            }
            if (CoreRune.MW_SPLIT_SHELL_TRANSITION && needChangeForSplitTask()) {
                return true;
            }
            if (CoreRune.FW_REMOTE_WALLPAPER_ANIM && this.mNeedRemoteWallpaperAnim) {
                return false;
            }
            boolean isVisibleRequested = this.mContainer.isVisibleRequested();
            boolean z = this.mVisible;
            if (isVisibleRequested == z && !z) {
                return false;
            }
            if (isVisibleRequested != z || this.mKnownConfigChanges != 0) {
                return true;
            }
            if ((this.mWindowingMode != 0 && this.mContainer.getWindowingMode() != this.mWindowingMode) || !this.mContainer.getBounds().equals(this.mAbsoluteBounds) || this.mRotation != this.mContainer.getWindowConfiguration().getRotation() || this.mDisplayId != Transition.getDisplayId(this.mContainer) || (this.mFlags & 32) != 0) {
                return true;
            }
            if (!CoreRune.MW_SHELL_CHANGE_TRANSITION || this.mChangeLeash == null) {
                return (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION && this.mForceChanged) || this.mResumedAffordance || this.mHideWhileTwoHandDragging || this.mForceHidingTransit != 0;
            }
            return true;
        }

        public int getTransitMode(WindowContainer windowContainer) {
            if ((this.mFlags & 4) != 0) {
                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && windowContainer.getDisplayContent() != null && windowContainer.isVisibleRequested()) {
                    ArrayList arrayList = (ArrayList) windowContainer.mTransitionController.mLatestOnTopTasksReported.get(windowContainer.getDisplayContent().getDisplayId());
                    if (arrayList != null && arrayList.contains(windowContainer)) {
                        if (this.mVisible == windowContainer.isVisibleRequested()) {
                            return 6;
                        }
                        return this.mExistenceChanged ? 1 : 3;
                    }
                }
                return this.mExistenceChanged ? 2 : 4;
            }
            if (this.mHideWhileTwoHandDragging) {
                return 4;
            }
            boolean isVisibleRequested = windowContainer.isVisibleRequested();
            if (isVisibleRequested == this.mVisible) {
                return 6;
            }
            return this.mExistenceChanged ? isVisibleRequested ? 1 : 2 : isVisibleRequested ? 3 : 4;
        }

        public int getChangeFlags(WindowContainer windowContainer) {
            Task task;
            int transitMode;
            int i = (this.mShowWallpaper || windowContainer.showWallpaper()) ? 1 : 0;
            if (Transition.isTranslucent(windowContainer)) {
                i |= 4;
            }
            if (windowContainer.mWmService.mAtmService.mBackNavigationController.isMonitorTransitionTarget(windowContainer)) {
                i |= IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
            }
            Task asTask = windowContainer.asTask();
            if (asTask != null) {
                ActivityRecord topNonFinishingActivity = asTask.getTopNonFinishingActivity();
                if (topNonFinishingActivity != null) {
                    StartingData startingData = topNonFinishingActivity.mStartingData;
                    if (startingData != null && startingData.hasImeSurface()) {
                        i |= IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES;
                    }
                    if (topNonFinishingActivity.mLaunchTaskBehind) {
                        Slog.e("Transition", "Unexpected launch-task-behind operation in shell transition");
                        i |= 524288;
                    }
                    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LATE_TRANSIENT_LAUNCH && topNonFinishingActivity.mIsLateTransientLaunch) {
                        i |= 67108864;
                    }
                    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && (this.mFlags & 4) != 0) {
                        i |= 1073741824;
                    }
                    if (CoreRune.FW_CUSTOM_LETTERBOX && (i & 4) == 0 && CustomLetterboxConfiguration.hasWallpaperBackgroundForLetterbox(topNonFinishingActivity) && ((transitMode = getTransitMode(windowContainer)) == 1 || transitMode == 3)) {
                        i |= 4;
                    }
                }
                if (asTask.voiceSession != null) {
                    i |= 16;
                }
            }
            ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
            if (asActivityRecord != null) {
                task = asActivityRecord.getTask();
                if (asActivityRecord.mVoiceInteraction) {
                    i |= 16;
                }
                i |= asActivityRecord.mTransitionChangeFlags;
                if (CoreRune.MW_SHELL_TRANSITION) {
                    i |= 8388608;
                }
                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LATE_TRANSIENT_LAUNCH && asActivityRecord.mIsLateTransientLaunch) {
                    i |= 67108864;
                }
            } else {
                task = null;
            }
            TaskFragment asTaskFragment = windowContainer.asTaskFragment();
            if (asTaskFragment != null && asTask == null) {
                task = asTaskFragment.getTask();
            }
            if (task != null) {
                if (task.forAllLeafTaskFragments(new ActivityRecord$$ExternalSyntheticLambda31())) {
                    i |= 512;
                }
                if (task.forAllActivities(new Transition$ChangeInfo$$ExternalSyntheticLambda0())) {
                    i |= 16384;
                }
                if (isWindowFillingTask(windowContainer, task)) {
                    i |= 1024;
                }
            } else {
                DisplayContent asDisplayContent = windowContainer.asDisplayContent();
                if (asDisplayContent != null) {
                    i |= 32;
                    if (asDisplayContent.hasAlertWindowSurfaces()) {
                        i |= 128;
                    }
                } else if (Transition.isWallpaper(windowContainer)) {
                    i |= 2;
                } else if (Transition.isInputMethod(windowContainer)) {
                    i |= 256;
                } else {
                    int windowType = windowContainer.getWindowType();
                    if (windowType >= 2000 && windowType <= 2999) {
                        i |= 65536;
                    }
                }
            }
            int i2 = this.mFlags;
            if ((i2 & 8) != 0 && (i2 & 16) == 0) {
                i |= 262144;
            }
            if ((i2 & 32) != 0) {
                i |= 1048576;
            }
            if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION) {
                if ((65536 & i2) != 0) {
                    i |= 268435456;
                }
                if ((i2 & IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES) != 0) {
                    i |= 536870912;
                }
            }
            if (CoreRune.MW_SHELL_TRANSITION && windowContainer.asTaskDisplayArea() != null) {
                i |= 16777216;
            }
            if (!windowContainer.allowEdgeExtension()) {
                i |= 33554432;
            }
            return (CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && Transition.isTransientLaunchOverlay(windowContainer)) ? i | 134217728 : i;
        }

        public final boolean isWindowFillingTask(WindowContainer windowContainer, Task task) {
            Rect bounds = task.getBounds();
            int width = bounds.width();
            int height = bounds.height();
            Rect rect = this.mAbsoluteBounds;
            Rect bounds2 = windowContainer.getBounds();
            return (!this.mVisible || (width == rect.width() && height == rect.height())) && (!windowContainer.isVisibleRequested() || (width == bounds2.width() && height == bounds2.height()));
        }

        public boolean shouldFreezeByChangeTransition() {
            return this.mVisible && this.mChangeTransitMode != 0;
        }

        public boolean needChangeForSplitTask() {
            if (this.mContainer.asTask() == null || !this.mContainer.inSplitScreenWindowingMode()) {
                return false;
            }
            return this.mForceChangeSplitTask;
        }

        public void updateForceChangeSplitTask(int i, boolean z) {
            if (z) {
                this.mForceChangeSplitTask = true;
            } else {
                this.mForceChangeSplitTask = i > 0;
            }
        }
    }

    public void deferTransitionReady() {
        this.mReadyTracker.mDeferReadyDepth++;
        this.mSyncEngine.setReady(this.mSyncId, false);
    }

    public void continueTransitionReady() {
        ReadyTracker readyTracker = this.mReadyTracker;
        readyTracker.mDeferReadyDepth--;
        applyReady();
    }

    public static Rect loadInsetsForRecentTransition(Task task) {
        ActivityRecord topRealVisibleActivity = task.getTopRealVisibleActivity();
        if (topRealVisibleActivity == null) {
            topRealVisibleActivity = task.getTopVisibleActivity(false, false);
        }
        WindowState findMainWindow = topRealVisibleActivity != null ? topRealVisibleActivity.findMainWindow() : null;
        if (findMainWindow == null) {
            return null;
        }
        Rect rect = findMainWindow.getInsetsStateWithVisibilityOverride().calculateInsets(task.getBounds(), WindowInsets.Type.systemBarsWithoutCaptionBar(), false).toRect();
        InsetUtils.addInsets(rect, findMainWindow.mActivityRecord.getLetterboxInsets());
        rect.set(Insets.max(Insets.of(rect), findMainWindow.getInsetsStateWithVisibilityOverride().calculateInsets(task.getBounds(), WindowInsets.Type.displayCutout(), false)).toRect());
        return rect;
    }

    public void setSkipMergeAnimation() {
        this.mSkipMergeAnimation = true;
    }

    public final void updateRemoteWallpaperAnimFlags(ChangeInfo changeInfo) {
        WindowContainer windowContainer = changeInfo.mContainer;
        if (isWallpaper(windowContainer)) {
            while (windowContainer != null) {
                DisplayArea asDisplayArea = windowContainer.asDisplayArea();
                if (asDisplayArea == null || asDisplayArea.mFeatureId != 10002) {
                    windowContainer = windowContainer.getParent();
                } else {
                    changeInfo.mNeedRemoteWallpaperAnim = true;
                    return;
                }
            }
        }
    }

    public class ReadyTracker {
        public int mDeferReadyDepth;
        public final ArrayMap mReadyGroups;
        public boolean mReadyOverride;
        public boolean mUsed;

        public ReadyTracker() {
            this.mReadyGroups = new ArrayMap();
            this.mUsed = false;
            this.mReadyOverride = false;
            this.mDeferReadyDepth = 0;
        }

        public void addGroup(WindowContainer windowContainer) {
            if (this.mReadyGroups.containsKey(windowContainer)) {
                Slog.e("Transition", "Trying to add a ready-group twice: " + windowContainer);
                return;
            }
            this.mReadyGroups.put(windowContainer, Boolean.FALSE);
        }

        public void setReadyFrom(WindowContainer windowContainer, boolean z) {
            this.mUsed = true;
            for (WindowContainer windowContainer2 = windowContainer; windowContainer2 != null; windowContainer2 = windowContainer2.getParent()) {
                if (Transition.isReadyGroup(windowContainer2)) {
                    this.mReadyGroups.put(windowContainer2, Boolean.valueOf(z));
                    if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -1924376693, 3, (String) null, new Object[]{Boolean.valueOf(z), String.valueOf(windowContainer2), String.valueOf(windowContainer)});
                        return;
                    }
                    return;
                }
            }
        }

        public void setAllReady() {
            if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 1670933628, 0, (String) null, (Object[]) null);
            }
            this.mUsed = true;
            this.mReadyOverride = true;
        }

        public boolean allReady() {
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
                Slog.v("Transition", " allReady query: used=" + this.mUsed + ", override=" + this.mReadyOverride + ", defer=" + this.mDeferReadyDepth + ", states=" + groupsToString());
            }
            if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -1383884640, 31, (String) null, new Object[]{Boolean.valueOf(this.mUsed), Boolean.valueOf(this.mReadyOverride), Long.valueOf(this.mDeferReadyDepth), String.valueOf(groupsToString())});
            }
            if (!this.mUsed || this.mDeferReadyDepth > 0) {
                return false;
            }
            if (this.mReadyOverride) {
                return true;
            }
            for (int size = this.mReadyGroups.size() - 1; size >= 0; size--) {
                WindowContainer windowContainer = (WindowContainer) this.mReadyGroups.keyAt(size);
                if (windowContainer.isAttached() && windowContainer.isVisibleRequested() && !((Boolean) this.mReadyGroups.valueAt(size)).booleanValue()) {
                    return false;
                }
            }
            return true;
        }

        public final String groupsToString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.mReadyGroups.size(); i++) {
                if (i != 0) {
                    sb.append(',');
                }
                sb.append(this.mReadyGroups.keyAt(i));
                sb.append(':');
                sb.append(this.mReadyGroups.valueAt(i));
            }
            return sb.toString();
        }
    }

    public class Targets {
        public final SparseArray mArray;
        public int mDepthFactor;
        public ArrayList mRemovedTargets;

        public Targets() {
            this.mArray = new SparseArray();
        }

        public void add(ChangeInfo changeInfo) {
            if (this.mDepthFactor == 0) {
                this.mDepthFactor = changeInfo.mContainer.mWmService.mRoot.getTreeWeight() + 1;
            }
            int prefixOrderIndex = changeInfo.mContainer.getPrefixOrderIndex();
            WindowContainer windowContainer = changeInfo.mContainer;
            while (windowContainer != null) {
                windowContainer = windowContainer.getParent();
                if (windowContainer != null) {
                    prefixOrderIndex += this.mDepthFactor;
                }
            }
            this.mArray.put(prefixOrderIndex, changeInfo);
        }

        public void remove(int i) {
            ChangeInfo changeInfo = (ChangeInfo) this.mArray.valueAt(i);
            this.mArray.removeAt(i);
            if (this.mRemovedTargets == null) {
                this.mRemovedTargets = new ArrayList();
            }
            this.mRemovedTargets.add(changeInfo);
        }

        public boolean wasParticipated(ChangeInfo changeInfo) {
            ArrayList arrayList;
            return this.mArray.indexOfValue(changeInfo) >= 0 || ((arrayList = this.mRemovedTargets) != null && arrayList.contains(changeInfo));
        }

        public ArrayList getListSortedByZ() {
            SparseArray sparseArray = new SparseArray(this.mArray.size());
            for (int size = this.mArray.size() - 1; size >= 0; size--) {
                sparseArray.put(this.mArray.keyAt(size) % this.mDepthFactor, (ChangeInfo) this.mArray.valueAt(size));
            }
            return getCustomListSortedByZ(sparseArray);
        }

        public ArrayList getCustomListSortedByZ(SparseArray sparseArray) {
            ArrayList arrayList = new ArrayList(sparseArray.size());
            ArrayList arrayList2 = new ArrayList(sparseArray.size());
            int i = -1;
            for (int size = sparseArray.size() - 1; size >= 0; size--) {
                ChangeInfo changeInfo = (ChangeInfo) sparseArray.valueAt(size);
                Task asTask = changeInfo.mContainer.asTask();
                if (asTask != null) {
                    if (asTask.mBoostRootTaskLayerForFreeform) {
                        if (i == -1) {
                            arrayList.add(changeInfo);
                        } else {
                            arrayList2.add(changeInfo);
                        }
                    } else if (i == -1 && !asTask.inPinnedWindowingMode()) {
                        i = arrayList.size();
                    }
                }
                arrayList.add(changeInfo);
            }
            if (i != -1) {
                for (int size2 = arrayList2.size() - 1; size2 >= 0; size2 += -1) {
                    ChangeInfo changeInfo2 = (ChangeInfo) arrayList2.get(size2);
                    arrayList.add(i, changeInfo2);
                    Slog.d("Transition", "getCustomListSortedByZ: boost, index=" + i + ", " + changeInfo2);
                }
            }
            return arrayList;
        }
    }

    class ScreenshotFreezer implements IContainerFreezer {
        public final ArraySet mFrozen;

        public ScreenshotFreezer() {
            this.mFrozen = new ArraySet();
        }

        /* JADX WARN: Code restructure failed: missing block: B:88:0x0235, code lost:
        
            if (com.android.server.wm.CustomLetterboxConfiguration.isCustomLetterboxEnabled(r11) != false) goto L98;
         */
        @Override // com.android.server.wm.Transition.IContainerFreezer
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean freeze(WindowContainer windowContainer, Rect rect) {
            ScreenCapture.LayerCaptureArgs build;
            String str;
            SurfaceControl surfaceControl;
            ActivityRecord asActivityRecord;
            boolean z = false;
            if (!windowContainer.isVisibleRequested() && (!CoreRune.MW_SHELL_CHANGE_TRANSITION || !((ChangeInfo) Transition.this.mChanges.get(windowContainer)).shouldFreezeByChangeTransition())) {
                return false;
            }
            for (WindowContainer windowContainer2 = windowContainer; windowContainer2 != null; windowContainer2 = windowContainer2.getParent()) {
                if ((!CoreRune.MW_SHELL_CHANGE_TRANSITION || windowContainer2.asDisplayContent() == null || !this.mFrozen.contains(windowContainer2) || ((ChangeInfo) Transition.this.mChanges.get(windowContainer)).mChangeTransitMode == 0) && this.mFrozen.contains(windowContainer2)) {
                    return false;
                }
            }
            if (Transition.this.mIsSeamlessRotation) {
                WindowState topFullscreenOpaqueWindow = windowContainer.getDisplayContent() == null ? null : windowContainer.getDisplayContent().getDisplayPolicy().getTopFullscreenOpaqueWindow();
                if (topFullscreenOpaqueWindow != null && (topFullscreenOpaqueWindow == windowContainer || topFullscreenOpaqueWindow.isDescendantOf(windowContainer))) {
                    this.mFrozen.add(windowContainer);
                    return true;
                }
            }
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
                if (ProtoLogCache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS, -574682264, 0, "Screenshotting %s [%s], caller=%s", new Object[]{String.valueOf(windowContainer.toString()), String.valueOf(rect.toString()), String.valueOf(Debug.getCallers(5))});
                }
            } else if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 390947100, 0, (String) null, new Object[]{String.valueOf(windowContainer.toString()), String.valueOf(rect.toString())});
            }
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BLACK_SNAPSHOT && Transition.this.mHasBlackSnapshot) {
                surfaceControl = windowContainer.makeAnimationLeash().setName("BlackSnapshotLayer_WmCore").setOpaque(true).setParent(windowContainer.getSurfaceControl()).setColorLayer().setCallsite("Transition.ScreenshotSync").build();
                this.mFrozen.add(windowContainer);
                ChangeInfo changeInfo = (ChangeInfo) Transition.this.mChanges.get(windowContainer);
                Objects.requireNonNull(changeInfo);
                changeInfo.mSnapshot = surfaceControl;
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) windowContainer.mWmService.mTransactionFactory.get();
                transaction.show(surfaceControl);
                transaction.setLayer(surfaceControl, Integer.MAX_VALUE);
                transaction.apply();
            } else {
                Rect rect2 = new Rect(rect);
                rect2.offsetTo(0, 0);
                boolean z2 = windowContainer.asDisplayContent() != null && windowContainer.asDisplayContent().isRotationChanging();
                if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                    ArrayList excludeLayers = CoreRune.MW_CAPTION_SHELL ? windowContainer.mWmService.getExcludeLayers(windowContainer) : new ArrayList();
                    Transition.this.mChangeTransitController.collectExcludeLayersFromSnapshot(windowContainer, excludeLayers);
                    Transition.this.mChangeTransitController.adjustFreezeBoundsIfNeeded(windowContainer, rect2);
                    build = new ScreenCapture.LayerCaptureArgs.Builder(windowContainer.getSurfaceControl()).setSourceCrop(rect2).setCaptureSecureLayers(true).setAllowProtected(true).setExcludeLayers((SurfaceControl[]) excludeLayers.toArray(new IntFunction() { // from class: com.android.server.wm.Transition$ScreenshotFreezer$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntFunction
                        public final Object apply(int i) {
                            SurfaceControl[] lambda$freeze$0;
                            lambda$freeze$0 = Transition.ScreenshotFreezer.lambda$freeze$0(i);
                            return lambda$freeze$0;
                        }
                    })).build();
                } else {
                    build = new ScreenCapture.LayerCaptureArgs.Builder(windowContainer.getSurfaceControl()).setSourceCrop(rect2).setCaptureSecureLayers(true).setAllowProtected(true).build();
                }
                ScreenCapture.ScreenshotHardwareBuffer captureLayers = ScreenCapture.captureLayers(build);
                HardwareBuffer hardwareBuffer = captureLayers == null ? null : captureLayers.getHardwareBuffer();
                if (hardwareBuffer == null || hardwareBuffer.getWidth() <= 1 || hardwareBuffer.getHeight() <= 1) {
                    Slog.w("Transition", "Failed to capture screenshot for " + windowContainer);
                    return false;
                }
                if (z2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("RotationLayer");
                    sb.append(CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG ? "_WmCore" : "");
                    str = sb.toString();
                } else {
                    str = "transition snapshot: " + windowContainer;
                }
                boolean fillsParent = windowContainer.fillsParent();
                if (CoreRune.MW_SHELL_CHANGE_TRANSITION && fillsParent && ChangeTransitionController.isTransparentSnapshotTarget((ChangeInfo) Transition.this.mChanges.get(windowContainer))) {
                    fillsParent = false;
                }
                if (CoreRune.FW_CUSTOM_LETTERBOX && fillsParent) {
                    Task asTask = windowContainer.asTask();
                    if (asTask != null) {
                        asActivityRecord = asTask.getTopNonFinishingActivity();
                    } else {
                        asActivityRecord = windowContainer.asActivityRecord();
                    }
                }
                z = fillsParent;
                SurfaceControl.Builder name = windowContainer.makeAnimationLeash().setName(str);
                if (!CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                    z = windowContainer.fillsParent();
                }
                SurfaceControl build2 = name.setOpaque(z).setParent(windowContainer.getSurfaceControl()).setSecure(captureLayers.containsSecureLayers()).setCallsite("Transition.ScreenshotSync").setBLASTLayer().build();
                this.mFrozen.add(windowContainer);
                ChangeInfo changeInfo2 = (ChangeInfo) Transition.this.mChanges.get(windowContainer);
                Objects.requireNonNull(changeInfo2);
                changeInfo2.mSnapshot = build2;
                if (z2) {
                    changeInfo2.mSnapshotLuma = TransitionAnimation.getBorderLuma(hardwareBuffer, captureLayers.getColorSpace());
                }
                SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) windowContainer.mWmService.mTransactionFactory.get();
                TransitionAnimation.configureScreenshotLayer(transaction2, build2, captureLayers);
                transaction2.show(build2);
                if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                    rect2.offset(rect.left, rect.top);
                    Transition.this.mChangeTransitController.createChangeLeashIfNeeded(windowContainer, transaction2, rect2);
                }
                transaction2.setLayer(build2, Integer.MAX_VALUE);
                transaction2.apply();
                transaction2.close();
                hardwareBuffer.close();
                if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                    Transition.this.mChangeTransitController.onSnapshotSurfaceCreated(windowContainer, rect2, changeInfo2);
                }
                surfaceControl = build2;
            }
            windowContainer.getSyncTransaction().reparent(surfaceControl, null);
            return true;
        }

        public static /* synthetic */ SurfaceControl[] lambda$freeze$0(int i) {
            return new SurfaceControl[i];
        }

        @Override // com.android.server.wm.Transition.IContainerFreezer
        public void cleanUp(SurfaceControl.Transaction transaction) {
            for (int i = 0; i < this.mFrozen.size(); i++) {
                ChangeInfo changeInfo = (ChangeInfo) Transition.this.mChanges.get(this.mFrozen.valueAt(i));
                Objects.requireNonNull(changeInfo);
                SurfaceControl surfaceControl = changeInfo.mSnapshot;
                SurfaceControl surfaceControl2 = ((ChangeInfo) Transition.this.mChanges.get(this.mFrozen.valueAt(i))).mChangeLeash;
                if (surfaceControl2 != null) {
                    transaction.reparent(surfaceControl2, null);
                }
                if (surfaceControl != null) {
                    transaction.reparent(surfaceControl, null);
                }
            }
        }
    }

    public class Token extends Binder {
        public final WeakReference mTransition;

        public Token(Transition transition) {
            this.mTransition = new WeakReference(transition);
        }

        public String toString() {
            return "Token{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.mTransition.get() + "}";
        }
    }
}
