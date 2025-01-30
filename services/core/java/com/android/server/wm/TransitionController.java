package com.android.server.wm;

import android.app.ActivityManager;
import android.app.IApplicationThread;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import android.view.SurfaceControl;
import android.window.ITransitionMetricsReporter;
import android.window.ITransitionPlayer;
import android.window.RemoteTransition;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.server.FgThread;
import com.android.server.wm.BLASTSyncEngine;
import com.android.server.wm.Transition;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

/* loaded from: classes3.dex */
public class TransitionController {
    public static final boolean SHELL_TRANSITIONS_ROTATION = SystemProperties.getBoolean("persist.wm.debug.shell_transit_rotate", false);
    public static final int SYNC_METHOD = SystemProperties.getBoolean("persist.wm.debug.shell_transit_blast", false) ? 1 : 0;
    public final ActivityTaskManagerService mAtm;
    public Transition mFinishingTransition;
    public final RemotePlayer mRemotePlayer;
    public SnapshotController mSnapshotController;
    public BLASTSyncEngine mSyncEngine;
    public ITransitionPlayer mTransitionPlayer;
    public final IBinder.DeathRecipient mTransitionPlayerDeath;
    public WindowProcessController mTransitionPlayerProc;
    public TransitionTracer mTransitionTracer;
    public final TransitionMetricsReporter mTransitionMetricsReporter = new TransitionMetricsReporter();
    public final ArrayList mLegacyListeners = new ArrayList();
    public final ArrayList mStateValidators = new ArrayList();
    public final ArrayList mValidateCommitVis = new ArrayList();
    public final ArrayList mValidateActivityCompat = new ArrayList();
    public final ArrayList mValidateDisplayVis = new ArrayList();
    public final ArrayList mPlayingTransitions = new ArrayList();
    public int mTrackCount = 0;
    public final ArrayList mAnimatingExitWindows = new ArrayList();
    public final Lock mRunningLock = new Lock();
    public final ArrayList mQueuedTransitions = new ArrayList();
    public Transition mCollectingTransition = null;
    public final ArrayList mWaitingTransitions = new ArrayList();
    public final SparseArray mLatestOnTopTasksReported = new SparseArray();
    public boolean mBuildingFinishLayers = false;
    public boolean mNavigationBarAttachedToApp = false;
    public boolean mAnimatingState = false;
    public final Handler mLoggerHandler = FgThread.getHandler();
    public boolean mIsWaitingForDisplayEnabled = false;
    public ArrayList mCleanUpRunnables = new ArrayList();
    public boolean mRequestHasTopUi = false;

    public interface OnStartCollect {
        void onCollectStarted(boolean z);
    }

    public static boolean isExistenceType(int i) {
        return i == 1 || i == 2;
    }

    public class QueuedTransition {
        public final BLASTSyncEngine.SyncGroup mLegacySync;
        public final OnStartCollect mOnStartCollect;
        public final Transition mTransition;

        public QueuedTransition(Transition transition, OnStartCollect onStartCollect) {
            this.mTransition = transition;
            this.mOnStartCollect = onStartCollect;
            this.mLegacySync = null;
        }

        public QueuedTransition(BLASTSyncEngine.SyncGroup syncGroup, OnStartCollect onStartCollect) {
            this.mTransition = null;
            this.mOnStartCollect = onStartCollect;
            this.mLegacySync = syncGroup;
        }
    }

    public TransitionController(ActivityTaskManagerService activityTaskManagerService, final Runnable runnable) {
        this.mAtm = activityTaskManagerService;
        this.mRemotePlayer = new RemotePlayer(activityTaskManagerService);
        this.mTransitionPlayerDeath = new IBinder.DeathRecipient() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda3
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                TransitionController.this.lambda$new$0(runnable);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Runnable runnable) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                detachPlayer();
                if (runnable != null) {
                    runnable.run();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void setWindowManager(WindowManagerService windowManagerService) {
        this.mSnapshotController = windowManagerService.mSnapshotController;
        this.mTransitionTracer = windowManagerService.mTransitionTracer;
        this.mIsWaitingForDisplayEnabled = !windowManagerService.mDisplayEnabled;
        registerLegacyListener(windowManagerService.mActivityManagerAppTransitionNotifier);
        setSyncEngine(windowManagerService.mSyncEngine);
    }

    public void setSyncEngine(BLASTSyncEngine bLASTSyncEngine) {
        this.mSyncEngine = bLASTSyncEngine;
        bLASTSyncEngine.addOnIdleListener(new Runnable() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                TransitionController.this.tryStartCollectFromQueue();
            }
        });
    }

    public final void detachPlayer() {
        if (this.mTransitionPlayer == null) {
            return;
        }
        this.mTransitionPlayer = null;
        for (int i = 0; i < this.mPlayingTransitions.size(); i++) {
            ((Transition) this.mPlayingTransitions.get(i)).cleanUpOnFailure();
        }
        this.mPlayingTransitions.clear();
        for (int i2 = 0; i2 < this.mWaitingTransitions.size(); i2++) {
            ((Transition) this.mWaitingTransitions.get(i2)).abort();
        }
        this.mWaitingTransitions.clear();
        Transition transition = this.mCollectingTransition;
        if (transition != null) {
            transition.abort();
        }
        setHasTopUiIfNeeded(false);
        this.mAtm.f1734mH.post(new Runnable() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                TransitionController.this.lambda$detachPlayer$1();
            }
        });
        this.mTransitionPlayerProc = null;
        this.mRemotePlayer.clear();
        this.mRunningLock.doNotifyLocked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$detachPlayer$1() {
        this.mAtm.mAmInternal.updateTransitionPlayerPid(-1);
    }

    public Transition createTransition(int i) {
        return createTransition(i, 0);
    }

    public final Transition createTransition(int i, int i2) {
        if (this.mTransitionPlayer == null) {
            throw new IllegalStateException("Shell Transitions not enabled");
        }
        if (this.mCollectingTransition != null) {
            throw new IllegalStateException("Trying to directly start transition collection while  collection is already ongoing. Use {@link #startCollectOrQueue} if possible.");
        }
        Transition transition = new Transition(i, i2, this, this.mSyncEngine);
        if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 259206414, 0, (String) null, new Object[]{String.valueOf(transition)});
        }
        moveToCollecting(transition);
        return transition;
    }

    public void moveToCollecting(Transition transition) {
        if (this.mCollectingTransition != null) {
            throw new IllegalStateException("Simultaneous transition collection not supported.");
        }
        if (this.mTransitionPlayer == null) {
            transition.abort();
            return;
        }
        this.mCollectingTransition = transition;
        transition.startCollecting(transition.mType == 6 ? 2000L : 5000L);
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
            if (ProtoLogCache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS, 1811081988, 0, "Start collecting in Transition: %s, caller=%s", new Object[]{String.valueOf(this.mCollectingTransition), String.valueOf(Debug.getCallers(5))});
            }
        } else if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -1764792832, 0, (String) null, new Object[]{String.valueOf(this.mCollectingTransition)});
        }
        dispatchLegacyAppTransitionPending();
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
            transition.mNeedsCleanupLegacyAppTransitionPending = true;
        }
    }

    public void registerTransitionPlayer(ITransitionPlayer iTransitionPlayer, final WindowProcessController windowProcessController) {
        try {
            ITransitionPlayer iTransitionPlayer2 = this.mTransitionPlayer;
            if (iTransitionPlayer2 != null) {
                if (iTransitionPlayer2.asBinder() != null) {
                    this.mTransitionPlayer.asBinder().unlinkToDeath(this.mTransitionPlayerDeath, 0);
                }
                detachPlayer();
            }
            if (iTransitionPlayer.asBinder() != null) {
                iTransitionPlayer.asBinder().linkToDeath(this.mTransitionPlayerDeath, 0);
            }
            this.mAtm.f1734mH.post(new Runnable() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    TransitionController.this.lambda$registerTransitionPlayer$2(windowProcessController);
                }
            });
            this.mTransitionPlayer = iTransitionPlayer;
            this.mTransitionPlayerProc = windowProcessController;
        } catch (RemoteException unused) {
            throw new RuntimeException("Unable to set transition player");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerTransitionPlayer$2(WindowProcessController windowProcessController) {
        this.mAtm.mAmInternal.updateTransitionPlayerPid(windowProcessController != null ? windowProcessController.getPid() : -1);
    }

    public ITransitionPlayer getTransitionPlayer() {
        return this.mTransitionPlayer;
    }

    public boolean isShellTransitionsEnabled() {
        return this.mTransitionPlayer != null;
    }

    public boolean useShellTransitionsRotation() {
        return isShellTransitionsEnabled() && SHELL_TRANSITIONS_ROTATION;
    }

    public boolean isCollecting() {
        return this.mCollectingTransition != null;
    }

    public Transition getCollectingTransition() {
        return this.mCollectingTransition;
    }

    public int getCollectingTransitionId() {
        Transition transition = this.mCollectingTransition;
        if (transition == null) {
            throw new IllegalStateException("There is no collecting transition");
        }
        return transition.getSyncId();
    }

    public boolean isCollecting(WindowContainer windowContainer) {
        Transition transition = this.mCollectingTransition;
        if (transition == null) {
            return false;
        }
        if (transition.mParticipants.contains(windowContainer)) {
            return true;
        }
        for (int i = 0; i < this.mWaitingTransitions.size(); i++) {
            if (((Transition) this.mWaitingTransitions.get(i)).mParticipants.contains(windowContainer)) {
                return true;
            }
        }
        return false;
    }

    public boolean inCollectingTransition(WindowContainer windowContainer) {
        if (!isCollecting()) {
            return false;
        }
        if (this.mCollectingTransition.isInTransition(windowContainer)) {
            return true;
        }
        for (int i = 0; i < this.mWaitingTransitions.size(); i++) {
            if (((Transition) this.mWaitingTransitions.get(i)).isInTransition(windowContainer)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPlaying() {
        return !this.mPlayingTransitions.isEmpty();
    }

    public boolean inPlayingTransition(WindowContainer windowContainer) {
        for (int size = this.mPlayingTransitions.size() - 1; size >= 0; size--) {
            if (((Transition) this.mPlayingTransitions.get(size)).isInTransition(windowContainer)) {
                return true;
            }
        }
        return false;
    }

    public Transition getTransitionOf(WindowContainer windowContainer) {
        Transition transition = this.mCollectingTransition;
        if (transition != null && transition.isInTransition(windowContainer)) {
            return this.mCollectingTransition;
        }
        for (int size = this.mPlayingTransitions.size() - 1; size >= 0; size--) {
            Transition transition2 = (Transition) this.mPlayingTransitions.get(size);
            if (transition2.isInTransition(windowContainer)) {
                return transition2;
            }
        }
        return null;
    }

    public boolean inPlayingTransition(WindowContainer windowContainer, boolean z) {
        TransitionInfo transitionInfo;
        for (int size = this.mPlayingTransitions.size() - 1; size >= 0; size--) {
            Transition transition = (Transition) this.mPlayingTransitions.get(size);
            if (z && (transitionInfo = transition.mLogger.mInfo) != null && transitionInfo.getChanges().isEmpty() && transition.mType >= 1000) {
                Slog.d("TransitionController", "continue inPlayingTransition checkCustomTransition playing=" + transition);
            } else if (transition.isInTransition(windowContainer)) {
                return true;
            }
        }
        return false;
    }

    public boolean inPlayingPipTransition(WindowContainer windowContainer) {
        for (int size = this.mPlayingTransitions.size() - 1; size >= 0; size--) {
            Transition transition = (Transition) this.mPlayingTransitions.get(size);
            if (transition.mType == 10 && transition.isInTransition(windowContainer)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isValidPlaying() {
        for (int size = this.mPlayingTransitions.size() - 1; size >= 0; size--) {
            TransitionInfo transitionInfo = ((Transition) this.mPlayingTransitions.get(size)).mLogger.mInfo;
            if (transitionInfo != null && transitionInfo.getRootCount() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean inFinishingTransition(WindowContainer windowContainer) {
        Transition transition = this.mFinishingTransition;
        return transition != null && transition.isInTransition(windowContainer);
    }

    public boolean inTransition() {
        return isCollecting() || isPlaying() || !this.mQueuedTransitions.isEmpty();
    }

    public boolean inTransition(WindowContainer windowContainer) {
        return inCollectingTransition(windowContainer) || inPlayingTransition(windowContainer);
    }

    public boolean inTransition(int i) {
        Transition transition = this.mCollectingTransition;
        if (transition != null && transition.getSyncId() == i) {
            return true;
        }
        for (int size = this.mPlayingTransitions.size() - 1; size >= 0; size--) {
            if (((Transition) this.mPlayingTransitions.get(size)).getSyncId() == i) {
                return true;
            }
        }
        return false;
    }

    public boolean isTransitionOnDisplay(DisplayContent displayContent) {
        Transition transition = this.mCollectingTransition;
        if (transition != null && transition.isOnDisplay(displayContent)) {
            return true;
        }
        for (int size = this.mWaitingTransitions.size() - 1; size >= 0; size--) {
            if (((Transition) this.mWaitingTransitions.get(size)).isOnDisplay(displayContent)) {
                return true;
            }
        }
        for (int size2 = this.mPlayingTransitions.size() - 1; size2 >= 0; size2--) {
            if (((Transition) this.mPlayingTransitions.get(size2)).isOnDisplay(displayContent)) {
                return true;
            }
        }
        return false;
    }

    public boolean isTransientHide(Task task) {
        Transition transition = this.mCollectingTransition;
        if (transition != null && transition.isInTransientHide(task)) {
            return true;
        }
        for (int size = this.mWaitingTransitions.size() - 1; size >= 0; size--) {
            if (((Transition) this.mWaitingTransitions.get(size)).isInTransientHide(task)) {
                return true;
            }
        }
        for (int size2 = this.mPlayingTransitions.size() - 1; size2 >= 0; size2--) {
            if (((Transition) this.mPlayingTransitions.get(size2)).isInTransientHide(task)) {
                return true;
            }
        }
        return false;
    }

    public boolean canApplyDim(Task task) {
        if (task == null) {
            return true;
        }
        for (int size = this.mPlayingTransitions.size() - 1; size >= 0; size--) {
            if (!((Transition) this.mPlayingTransitions.get(size)).canApplyDim(task)) {
                return false;
            }
        }
        return true;
    }

    public boolean shouldKeepFocus(WindowContainer windowContainer) {
        if (this.mCollectingTransition != null) {
            if (this.mPlayingTransitions.isEmpty()) {
                return this.mCollectingTransition.isInTransientHide(windowContainer);
            }
            return false;
        }
        if (this.mPlayingTransitions.size() == 1) {
            return ((Transition) this.mPlayingTransitions.get(0)).isInTransientHide(windowContainer);
        }
        return false;
    }

    public boolean isTransientCollect(ActivityRecord activityRecord) {
        Transition transition = this.mCollectingTransition;
        return transition != null && transition.isTransientLaunch(activityRecord);
    }

    public boolean isTransientLaunch(ActivityRecord activityRecord) {
        if (isTransientCollect(activityRecord)) {
            return true;
        }
        for (int size = this.mPlayingTransitions.size() - 1; size >= 0; size--) {
            if (((Transition) this.mPlayingTransitions.get(size)).isTransientLaunch(activityRecord)) {
                return true;
            }
        }
        return false;
    }

    public boolean canAssignLayers(WindowContainer windowContainer) {
        Transition transition;
        Transition.ChangeInfo changeInfo;
        if (this.mBuildingFinishLayers) {
            return windowContainer.asWindowState() == null;
        }
        if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION && (transition = this.mCollectingTransition) != null && (changeInfo = (Transition.ChangeInfo) transition.mChanges.get(windowContainer)) != null && changeInfo.mMinimizeAnimState == 2) {
            return true;
        }
        if (CoreRune.MW_NATURAL_SWITCHING_PIP && windowContainer.asTask() != null && this.mAtm.mNaturalSwitchingController.isNaturalSwitchingPipTask(windowContainer.asTask())) {
            return false;
        }
        if (CoreRune.MD_DEX_SHELL_TRANSITION && windowContainer.asTask() != null && windowContainer.inFreeformWindowingMode() && windowContainer.isDesktopModeEnabled() && !isValidPlaying()) {
            return true;
        }
        return (windowContainer.asWindowState() == null && isPlaying()) ? false : true;
    }

    public int getWindowingModeAtStart(WindowContainer windowContainer) {
        Transition transition = this.mCollectingTransition;
        if (transition == null) {
            return windowContainer.getWindowingMode();
        }
        Transition.ChangeInfo changeInfo = (Transition.ChangeInfo) transition.mChanges.get(windowContainer);
        if (changeInfo == null) {
            return windowContainer.getWindowingMode();
        }
        return changeInfo.mWindowingMode;
    }

    public int getCollectingTransitionType() {
        Transition transition = this.mCollectingTransition;
        if (transition != null) {
            return transition.mType;
        }
        return 0;
    }

    public Transition requestTransitionIfNeeded(int i, WindowContainer windowContainer) {
        return requestTransitionIfNeeded(i, 0, windowContainer, windowContainer);
    }

    public Transition requestTransitionIfNeeded(int i, int i2, WindowContainer windowContainer, WindowContainer windowContainer2) {
        return requestTransitionIfNeeded(i, i2, windowContainer, windowContainer2, null, null);
    }

    public final void setDisplaySyncMethod(TransitionRequestInfo.DisplayChange displayChange, Transition transition, DisplayContent displayContent) {
        int startRotation = displayChange.getStartRotation();
        int endRotation = displayChange.getEndRotation();
        if (startRotation != endRotation && (startRotation + endRotation) % 2 == 0) {
            this.mSyncEngine.setSyncMethod(transition.getSyncId(), 1);
        }
        Rect startAbsBounds = displayChange.getStartAbsBounds();
        Rect endAbsBounds = displayChange.getEndAbsBounds();
        if (startAbsBounds == null || endAbsBounds == null) {
            return;
        }
        int width = startAbsBounds.width();
        int height = startAbsBounds.height();
        int width2 = endAbsBounds.width();
        int height2 = endAbsBounds.height();
        if ((width2 > width) == (height2 > height)) {
            if (width2 == width && height2 == height) {
                return;
            }
            displayContent.forAllWindows(new Consumer() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TransitionController.lambda$setDisplaySyncMethod$3((WindowState) obj);
                }
            }, true);
        }
    }

    public static /* synthetic */ void lambda$setDisplaySyncMethod$3(WindowState windowState) {
        if (windowState.mToken.mRoundedCornerOverlay && windowState.mHasSurface) {
            windowState.mSyncMethodOverride = 1;
        }
    }

    public Transition requestTransitionIfNeeded(int i, int i2, WindowContainer windowContainer, WindowContainer windowContainer2, RemoteTransition remoteTransition, TransitionRequestInfo.DisplayChange displayChange) {
        r1 = null;
        r1 = null;
        r1 = null;
        Transition requestStartTransition = null;
        if (this.mTransitionPlayer == null) {
            return null;
        }
        if (isCollecting()) {
            if (displayChange != null) {
                Slog.e("TransitionController", "Provided displayChange for a non-new request", new Throwable());
            }
            this.mCollectingTransition.setReady(windowContainer2, false);
            int i3 = i2 & 14592;
            if (i3 != 0) {
                this.mCollectingTransition.addFlag(i3);
                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && (i2 & 2) != 0) {
                    this.mCollectingTransition.addFlag(2);
                }
            }
        } else {
            requestStartTransition = requestStartTransition(createTransition(i, i2), windowContainer != null ? windowContainer.asTask() : null, remoteTransition, displayChange);
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BLACK_SNAPSHOT && windowContainer != null && windowContainer.asDisplayContent() != null && windowContainer.asDisplayContent().mHasBlackSnapshot) {
                Slog.d("TransitionController", "Ignore to set the blast method by black snapshot");
            } else if (requestStartTransition != null && displayChange != null && windowContainer != null && windowContainer.asDisplayContent() != null) {
                setDisplaySyncMethod(displayChange, requestStartTransition, windowContainer.asDisplayContent());
            }
        }
        if (windowContainer != null) {
            if (isExistenceType(i)) {
                collectExistenceChange(windowContainer);
            } else {
                collect(windowContainer);
            }
        }
        return requestStartTransition;
    }

    public Transition requestStartTransition(final Transition transition, Task task, RemoteTransition remoteTransition, TransitionRequestInfo.DisplayChange displayChange) {
        ActivityManager.RunningTaskInfo runningTaskInfo = null;
        if (this.mIsWaitingForDisplayEnabled) {
            if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 1282992082, 1, (String) null, new Object[]{Long.valueOf(transition.getSyncId())});
            }
            transition.mIsPlayerEnabled = false;
            transition.mLogger.mRequestTimeNs = SystemClock.uptimeNanos();
            this.mAtm.f1734mH.post(new Runnable() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    TransitionController.this.lambda$requestStartTransition$4(transition);
                }
            });
            return transition;
        }
        if (this.mTransitionPlayer == null || transition.isAborted()) {
            if (transition.isCollecting()) {
                transition.abort();
            }
            return transition;
        }
        try {
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
                if (ProtoLogCache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS, 2128359968, 0, "Requesting StartTransition: %s, caller=%s", new Object[]{String.valueOf(transition), String.valueOf(Debug.getCallers(5))});
                }
            } else if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 1794249572, 0, (String) null, new Object[]{String.valueOf(transition)});
            }
            if (task != null) {
                runningTaskInfo = new ActivityManager.RunningTaskInfo();
                task.fillTaskInfo(runningTaskInfo);
            }
            TransitionRequestInfo transitionRequestInfo = new TransitionRequestInfo(transition.mType, runningTaskInfo, remoteTransition, displayChange);
            transition.mLogger.mRequestTimeNs = SystemClock.elapsedRealtimeNanos();
            transition.mLogger.mRequest = transitionRequestInfo;
            this.mTransitionPlayer.requestStartTransition(transition.getToken(), transitionRequestInfo);
            if (remoteTransition != null) {
                transition.setRemoteAnimationApp(remoteTransition.getAppThread());
            }
        } catch (RemoteException e) {
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
                transition.mIsFailedStartTransition = true;
            }
            Slog.e("TransitionController", "Error requesting transition", e);
            transition.start();
        }
        return transition;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestStartTransition$4(Transition transition) {
        this.mAtm.mWindowOrganizerController.startTransition(transition.getToken(), null);
    }

    public Transition requestCloseTransitionIfNeeded(WindowContainer windowContainer) {
        Task asTask;
        if (this.mTransitionPlayer == null) {
            return null;
        }
        Task task = windowContainer.asActivityRecord() != null ? windowContainer.asActivityRecord().getTask() : windowContainer.asTask();
        if (task != null && task.isFreeformForceHidden()) {
            Slog.d("TransitionController", "requestCloseTransitionIfNeeded: skip, force hidden, t=" + task);
            return null;
        }
        ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
        if (asActivityRecord != null) {
            asTask = asActivityRecord.getTask();
        } else {
            asTask = windowContainer.asTask();
            if (asTask != null) {
                asActivityRecord = asTask.getTopActivity(true, false);
            }
        }
        if (asTask != null && ((asTask.mHiddenWhileActivatingDrag || asTask.mIsAnimatingByRecentsAndDragSourceTask) && asActivityRecord != null && asActivityRecord.isFinishing())) {
            Slog.d("TransitionController", "requestCloseTransitionIfNeeded: skip, force hidden by dragging, t=" + asTask);
            return null;
        }
        if (windowContainer.isVisibleRequested()) {
            r1 = isCollecting() ? null : requestStartTransition(createTransition(2, 0), windowContainer.asTask(), null, null);
            collectExistenceChange(windowContainer);
        } else {
            collect(windowContainer);
        }
        return r1;
    }

    public void collect(WindowContainer windowContainer) {
        Transition transition = this.mCollectingTransition;
        if (transition == null) {
            return;
        }
        transition.collect(windowContainer);
    }

    public void setHideWhileTwoHandDragging(WindowContainer windowContainer) {
        Transition.ChangeInfo changeInfo;
        Transition transition = this.mCollectingTransition;
        if (transition == null || (changeInfo = (Transition.ChangeInfo) transition.mChanges.get(windowContainer)) == null) {
            return;
        }
        changeInfo.mHideWhileTwoHandDragging = true;
        Slog.d("TransitionController", "setHideWhileTwoHandDragging: wc=" + windowContainer);
    }

    public void setForceHidingTransit(WindowContainer windowContainer, int i) {
        Transition.ChangeInfo changeInfo;
        Transition transition = this.mCollectingTransition;
        if (transition == null || (changeInfo = (Transition.ChangeInfo) transition.mChanges.get(windowContainer)) == null) {
            return;
        }
        changeInfo.mForceHidingTransit = i;
    }

    public void setResumedAffordance(WindowContainer windowContainer) {
        Transition transition = this.mCollectingTransition;
        if (transition == null || transition.mChanges.get(windowContainer) == null) {
            return;
        }
        ((Transition.ChangeInfo) this.mCollectingTransition.mChanges.get(windowContainer)).mResumedAffordance = true;
    }

    public void setAffordanceTargetFreeformTask(WindowContainer windowContainer) {
        Transition transition = this.mCollectingTransition;
        if (transition == null || transition.mChanges.get(windowContainer) == null) {
            return;
        }
        ((Transition.ChangeInfo) this.mCollectingTransition.mChanges.get(windowContainer)).mAffordanceTargetFreeformTask = true;
    }

    public void collectExistenceChange(WindowContainer windowContainer) {
        Transition transition = this.mCollectingTransition;
        if (transition == null) {
            return;
        }
        transition.collectExistenceChange(windowContainer);
    }

    public void recordTaskOrder(WindowContainer windowContainer) {
        Transition transition = this.mCollectingTransition;
        if (transition == null) {
            return;
        }
        transition.recordTaskOrder(windowContainer);
    }

    public void collectForDisplayAreaChange(DisplayArea displayArea) {
        final Transition transition = this.mCollectingTransition;
        if (transition == null || !transition.mParticipants.contains(displayArea)) {
            return;
        }
        transition.collectVisibleChange(displayArea);
        displayArea.forAllLeafTasks(new Consumer() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TransitionController.lambda$collectForDisplayAreaChange$5(Transition.this, (Task) obj);
            }
        }, true);
        DisplayContent asDisplayContent = displayArea.asDisplayContent();
        if (asDisplayContent != null) {
            final boolean z = asDisplayContent.getAsyncRotationController() == null;
            displayArea.forAllWindows(new Consumer() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TransitionController.this.lambda$collectForDisplayAreaChange$6(z, transition, (WindowState) obj);
                }
            }, true);
        }
    }

    public static /* synthetic */ void lambda$collectForDisplayAreaChange$5(Transition transition, Task task) {
        if (task.isVisible()) {
            transition.collect(task);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$collectForDisplayAreaChange$6(boolean z, Transition transition, WindowState windowState) {
        if (windowState.mActivityRecord == null && windowState.isVisible() && !isCollecting(windowState.mToken)) {
            if (z || !AsyncRotationController.canBeAsync(windowState.mToken)) {
                transition.collect(windowState.mToken);
            }
        }
    }

    public void collectVisibleChange(WindowContainer windowContainer) {
        if (isCollecting()) {
            this.mCollectingTransition.collectVisibleChange(windowContainer);
        }
    }

    public void collectReparentChange(WindowContainer windowContainer, WindowContainer windowContainer2) {
        if (isCollecting()) {
            this.mCollectingTransition.collectReparentChange(windowContainer, windowContainer2);
        }
    }

    public void setStatusBarTransitionDelay(long j) {
        Transition transition = this.mCollectingTransition;
        if (transition == null) {
            return;
        }
        transition.mStatusBarTransitionDelay = j;
    }

    public void setOverrideAnimation(TransitionInfo.AnimationOptions animationOptions, IRemoteCallback iRemoteCallback, IRemoteCallback iRemoteCallback2) {
        Transition transition = this.mCollectingTransition;
        if (transition == null) {
            return;
        }
        transition.setOverrideAnimation(animationOptions, iRemoteCallback, iRemoteCallback2);
    }

    public void setNoAnimation(WindowContainer windowContainer) {
        Transition transition = this.mCollectingTransition;
        if (transition == null) {
            return;
        }
        transition.setNoAnimation(windowContainer);
    }

    public void setReady(WindowContainer windowContainer, boolean z) {
        Transition transition = this.mCollectingTransition;
        if (transition == null) {
            return;
        }
        transition.setReady(windowContainer, z);
    }

    public void setReady(WindowContainer windowContainer) {
        setReady(windowContainer, true);
    }

    public void deferTransitionReady() {
        if (isShellTransitionsEnabled()) {
            Transition transition = this.mCollectingTransition;
            if (transition == null) {
                throw new IllegalStateException("No collecting transition to defer readiness for.");
            }
            transition.deferTransitionReady();
        }
    }

    public void continueTransitionReady() {
        if (isShellTransitionsEnabled()) {
            Transition transition = this.mCollectingTransition;
            if (transition == null) {
                throw new IllegalStateException("No collecting transition to defer readiness for.");
            }
            transition.continueTransitionReady();
        }
    }

    public void finishTransition(Transition transition) {
        this.mTransitionMetricsReporter.reportAnimationStart(transition.getToken(), 0L);
        this.mAtm.endLaunchPowerMode(2);
        if (!this.mPlayingTransitions.contains(transition)) {
            Slog.e("TransitionController", "Trying to finish a non-playing transition " + transition);
            return;
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
            if (ProtoLogCache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS, 2054808080, 0, "Finish Transition: %s, caller=%s", new Object[]{String.valueOf(transition), String.valueOf(Debug.getCallers(3))});
            }
        } else if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -622017164, 0, (String) null, new Object[]{String.valueOf(transition)});
        }
        this.mPlayingTransitions.remove(transition);
        if (!inTransition()) {
            this.mTrackCount = 0;
        }
        updateRunningRemoteAnimation(transition, false);
        transition.finishTransition();
        for (int size = this.mAnimatingExitWindows.size() - 1; size >= 0; size--) {
            WindowState windowState = (WindowState) this.mAnimatingExitWindows.get(size);
            if (windowState.mAnimatingExit && windowState.mHasSurface && !windowState.inTransition()) {
                windowState.onExitAnimationDone();
            }
            if (!windowState.mAnimatingExit || !windowState.mHasSurface) {
                this.mAnimatingExitWindows.remove(size);
            }
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
            if (!inTransition()) {
                this.mAtm.mWindowManager.mFocusMayChange = true;
            }
            this.mAtm.mWindowManager.requestTraversal();
        }
        this.mRunningLock.doNotifyLocked();
        if (inTransition()) {
            return;
        }
        validateStates();
        this.mAtm.mWindowManager.onAnimationFinished();
    }

    public void onCommittedInvisibles() {
        Transition transition = this.mCollectingTransition;
        if (transition != null) {
            transition.mPriorVisibilityMightBeDirty = true;
        }
        for (int size = this.mWaitingTransitions.size() - 1; size >= 0; size--) {
            ((Transition) this.mWaitingTransitions.get(size)).mPriorVisibilityMightBeDirty = true;
        }
    }

    public final void validateStates() {
        ActivityRecord activityRecord;
        for (int i = 0; i < this.mStateValidators.size(); i++) {
            ((Runnable) this.mStateValidators.get(i)).run();
            if (inTransition()) {
                this.mStateValidators.subList(0, i + 1).clear();
                return;
            }
        }
        this.mStateValidators.clear();
        for (int i2 = 0; i2 < this.mValidateCommitVis.size(); i2++) {
            ActivityRecord activityRecord2 = (ActivityRecord) this.mValidateCommitVis.get(i2);
            if (!activityRecord2.isVisibleRequested() && activityRecord2.isVisible()) {
                Slog.e("TransitionController", "Uncommitted visibility change: " + activityRecord2);
                activityRecord2.commitVisibility(activityRecord2.isVisibleRequested(), false, false);
            }
        }
        this.mValidateCommitVis.clear();
        for (int i3 = 0; i3 < this.mValidateActivityCompat.size(); i3++) {
            ActivityRecord activityRecord3 = (ActivityRecord) this.mValidateActivityCompat.get(i3);
            if (activityRecord3.getSurfaceControl() != null) {
                activityRecord3.getRelativePosition(new Point());
                activityRecord3.getSyncTransaction().setPosition(activityRecord3.getSurfaceControl(), r3.x, r3.y);
            }
        }
        this.mValidateActivityCompat.clear();
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
            for (int i4 = 0; i4 < this.mValidateDisplayVis.size(); i4++) {
                DisplayContent displayContent = this.mAtm.mWindowManager.mRoot.getDisplayContent(((Integer) this.mValidateDisplayVis.get(i4)).intValue());
                if (displayContent != null && (activityRecord = displayContent.mFocusedApp) != null && activityRecord.isVisibleRequested()) {
                    enforceSurfaceVisible(activityRecord);
                }
            }
            this.mValidateDisplayVis.clear();
        }
        for (int i5 = 0; i5 < this.mCleanUpRunnables.size(); i5++) {
            ((Runnable) this.mCleanUpRunnables.get(i5)).run();
        }
        this.mCleanUpRunnables.clear();
    }

    public void onVisibleWithoutCollectingTransition(final WindowContainer windowContainer, String str) {
        boolean z = !this.mPlayingTransitions.isEmpty();
        Slog.e("TransitionController", "Set visible without transition " + windowContainer + " playing=" + z + " caller=" + str);
        if (!z) {
            enforceSurfaceVisible(windowContainer);
        } else {
            this.mStateValidators.add(new Runnable() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    TransitionController.this.lambda$onVisibleWithoutCollectingTransition$7(windowContainer);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onVisibleWithoutCollectingTransition$7(WindowContainer windowContainer) {
        if (windowContainer.isVisibleRequested()) {
            enforceSurfaceVisible(windowContainer);
        }
    }

    public void enforceSurfaceVisible(WindowContainer windowContainer) {
        if (windowContainer.mSurfaceControl == null) {
            return;
        }
        windowContainer.getSyncTransaction().show(windowContainer.mSurfaceControl);
        ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
        if (asActivityRecord != null) {
            asActivityRecord.mLastSurfaceShowing = true;
        }
        for (WindowContainer parent = windowContainer.getParent(); parent != null && parent != windowContainer.mDisplayContent; parent = parent.getParent()) {
            if (parent.mSurfaceControl != null) {
                parent.getSyncTransaction().show(parent.mSurfaceControl);
                Task asTask = parent.asTask();
                if (asTask != null) {
                    asTask.mLastSurfaceShowing = true;
                }
            }
        }
        windowContainer.scheduleAnimation();
    }

    public void onTransitionPopulated(Transition transition) {
        tryStartCollectFromQueue();
    }

    public final boolean canStartCollectingNow(Transition transition) {
        Transition transition2 = this.mCollectingTransition;
        if (transition2 == null) {
            return true;
        }
        if (!transition2.isPopulated() || !getCanBeIndependent(this.mCollectingTransition, transition)) {
            return false;
        }
        for (int i = 0; i < this.mWaitingTransitions.size(); i++) {
            if (!getCanBeIndependent((Transition) this.mWaitingTransitions.get(i), transition)) {
                return false;
            }
        }
        return true;
    }

    public void tryStartCollectFromQueue() {
        if (this.mQueuedTransitions.isEmpty()) {
            return;
        }
        final QueuedTransition queuedTransition = (QueuedTransition) this.mQueuedTransitions.get(0);
        if (this.mCollectingTransition != null) {
            Transition transition = queuedTransition.mTransition;
            if (transition == null || !canStartCollectingNow(transition)) {
                return;
            }
            if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, -266707683, 1, "Moving #%d from collecting to waiting.", new Object[]{Long.valueOf(this.mCollectingTransition.getSyncId())});
            }
            this.mWaitingTransitions.add(this.mCollectingTransition);
            this.mCollectingTransition = null;
        } else if (this.mSyncEngine.hasActiveSync()) {
            return;
        }
        this.mQueuedTransitions.remove(0);
        Transition transition2 = queuedTransition.mTransition;
        if (transition2 != null) {
            moveToCollecting(transition2);
        } else {
            this.mSyncEngine.startSyncSet(queuedTransition.mLegacySync);
        }
        Transition transition3 = queuedTransition.mTransition;
        if (transition3 != null && transition3.mType == 12) {
            queuedTransition.mOnStartCollect.onCollectStarted(true);
        } else {
            this.mAtm.f1734mH.post(new Runnable() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    TransitionController.this.lambda$tryStartCollectFromQueue$8(queuedTransition);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$tryStartCollectFromQueue$8(QueuedTransition queuedTransition) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                queuedTransition.mOnStartCollect.onCollectStarted(true);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void moveToPlaying(Transition transition) {
        if (transition == this.mCollectingTransition) {
            this.mCollectingTransition = null;
            if (!this.mWaitingTransitions.isEmpty()) {
                this.mCollectingTransition = (Transition) this.mWaitingTransitions.remove(0);
            }
            if (this.mCollectingTransition == null) {
                this.mLatestOnTopTasksReported.clear();
            }
        } else if (!this.mWaitingTransitions.remove(transition)) {
            Slog.e("TransitionController", "moveToPlaying: Trying to move non-collecting transition to playing, mWaitingTransitions=" + this.mWaitingTransitions + ", transition=" + transition + ", syncId=" + transition.getSyncId() + ", Callers=" + Debug.getCallers(7));
        }
        this.mPlayingTransitions.add(transition);
        updateRunningRemoteAnimation(transition, true);
    }

    public boolean getCanBeIndependent(Transition transition, Transition transition2) {
        if (transition2 != null && transition2.mParallelCollectType == 1 && transition.mParallelCollectType == 1) {
            return true;
        }
        if (transition2 != null && transition2.mParallelCollectType == 2) {
            if (transition.mParallelCollectType == 2) {
                return false;
            }
            for (int i = 0; i < transition.mParticipants.size(); i++) {
                WindowContainer windowContainer = (WindowContainer) transition.mParticipants.valueAt(i);
                ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
                if (asActivityRecord == null && windowContainer.asWindowState() == null && windowContainer.asWindowToken() == null) {
                    return false;
                }
                if (asActivityRecord != null && asActivityRecord.isActivityTypeHomeOrRecents()) {
                    return false;
                }
            }
            return true;
        }
        int i2 = transition.mParallelCollectType;
        if (i2 == 2) {
            return true;
        }
        return CoreRune.FW_CUSTOM_SHELL_TRANSITION_LATE_TRANSIENT_LAUNCH && i2 == 3;
    }

    public static boolean getIsIndependent(Transition transition, Transition transition2) {
        int i = transition.mParallelCollectType;
        if (i == 1 && transition2.mParallelCollectType == 1) {
            return true;
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LATE_TRANSIENT_LAUNCH && i == 3 && transition.hasLateTransientLaunch()) {
            int i2 = transition2.mParallelCollectType;
            return (i2 == 2 || i2 == 3) ? false : true;
        }
        if (transition.mParallelCollectType == 2 && transition.hasTransientLaunch()) {
            if (transition2.mParallelCollectType == 2) {
                return false;
            }
            transition2 = transition;
            transition = transition2;
        } else if (transition2.mParallelCollectType != 2 || !transition2.hasTransientLaunch()) {
            return false;
        }
        for (int i3 = 0; i3 < transition.mTargets.size(); i3++) {
            WindowContainer windowContainer = ((Transition.ChangeInfo) transition.mTargets.get(i3)).mContainer;
            ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
            if (asActivityRecord == null && windowContainer.asWindowState() == null && windowContainer.asWindowToken() == null) {
                return false;
            }
            if (asActivityRecord != null && transition2.isTransientLaunch(asActivityRecord)) {
                return false;
            }
        }
        return true;
    }

    public void assignTrack(Transition transition, TransitionInfo transitionInfo) {
        boolean z;
        int i = -1;
        int i2 = 0;
        while (true) {
            if (i2 >= this.mPlayingTransitions.size()) {
                break;
            }
            if (this.mPlayingTransitions.get(i2) != transition && !getIsIndependent((Transition) this.mPlayingTransitions.get(i2), transition)) {
                if (i >= 0) {
                    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && i2 == this.mPlayingTransitions.size() - 1) {
                        Transition transition2 = (Transition) this.mPlayingTransitions.get(i2);
                        if (transition2.mParallelCollectType == 2) {
                            i = transition2.mAnimationTrack;
                            Slog.d(StartingSurfaceController.TAG, "Not mark as SYNC for SyncId#" + transition.getSyncId() + " due to transient launch on track#" + i);
                        }
                    }
                    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG && CoreRune.SAFE_DEBUG) {
                        Slog.d(StartingSurfaceController.TAG, "Possible to mark as SYNC for SyncId#" + transition.getSyncId() + " due to " + ((Transition) this.mPlayingTransitions.get(i2)).getSyncId() + " on track#" + ((Transition) this.mPlayingTransitions.get(i2)).mAnimationTrack);
                        StringBuilder sb = new StringBuilder();
                        sb.append("Checking playing transitions=");
                        sb.append(this.mPlayingTransitions);
                        Slog.d(StartingSurfaceController.TAG, sb.toString());
                    }
                    z = true;
                } else {
                    i = ((Transition) this.mPlayingTransitions.get(i2)).mAnimationTrack;
                }
            }
            i2++;
        }
        z = false;
        int i3 = z ? 0 : i;
        if (i3 < 0 && (i3 = this.mTrackCount) > 0) {
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
                if (ProtoLogCache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS, 2057242484, 5, "Playing #%d in parallel on track #%d", new Object[]{Long.valueOf(transition.getSyncId()), Long.valueOf(i3)});
                }
            } else if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -1005167552, 5, (String) null, new Object[]{Long.valueOf(transition.getSyncId()), Long.valueOf(i3)});
            }
        }
        transition.mAnimationTrack = i3;
        transitionInfo.setTrack(i3);
        int max = Math.max(this.mTrackCount, i3 + 1);
        this.mTrackCount = max;
        if (!z || max <= 1) {
            return;
        }
        transitionInfo.setFlags(transitionInfo.getFlags() | 2097152);
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
            if (ProtoLogCache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS, -641235516, 1, "Marking #%d animation as SYNC.", new Object[]{Long.valueOf(transition.getSyncId())});
            }
        } else if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -774908272, 1, (String) null, new Object[]{Long.valueOf(transition.getSyncId())});
        }
    }

    public void updateAnimatingState(SurfaceControl.Transaction transaction) {
        Transition transition;
        boolean z = !this.mPlayingTransitions.isEmpty() || ((transition = this.mCollectingTransition) != null && transition.isStarted());
        if (z && !this.mAnimatingState) {
            transaction.setEarlyWakeupStart();
            this.mSnapshotController.setPause(true);
            this.mAnimatingState = true;
            Transition.asyncTraceBegin("animating", 68942577);
            return;
        }
        if (z || !this.mAnimatingState) {
            return;
        }
        transaction.setEarlyWakeupEnd();
        this.mSnapshotController.setPause(false);
        this.mAnimatingState = false;
        Transition.asyncTraceEnd(68942577);
    }

    public final void updateRunningRemoteAnimation(Transition transition, boolean z) {
        WindowProcessController processController;
        WindowProcessController windowProcessController = this.mTransitionPlayerProc;
        if (windowProcessController == null) {
            return;
        }
        if (z) {
            windowProcessController.setRunningRemoteAnimation(true);
        } else if (this.mPlayingTransitions.isEmpty()) {
            this.mTransitionPlayerProc.setRunningRemoteAnimation(false);
            this.mRemotePlayer.clear();
            return;
        }
        IApplicationThread remoteAnimationApp = transition.getRemoteAnimationApp();
        if (remoteAnimationApp == null || remoteAnimationApp == this.mTransitionPlayerProc.getThread() || (processController = this.mAtm.getProcessController(remoteAnimationApp)) == null) {
            return;
        }
        this.mRemotePlayer.update(processController, z, true);
    }

    public void onAbort(Transition transition) {
        if (transition != this.mCollectingTransition) {
            int indexOf = this.mWaitingTransitions.indexOf(transition);
            if (indexOf < 0) {
                throw new IllegalStateException("Too late for abort.");
            }
            this.mWaitingTransitions.remove(indexOf);
        } else {
            this.mCollectingTransition = null;
            if (!this.mWaitingTransitions.isEmpty()) {
                this.mCollectingTransition = (Transition) this.mWaitingTransitions.remove(0);
            }
            if (this.mCollectingTransition == null) {
                this.mLatestOnTopTasksReported.clear();
            }
        }
        if (inTransition()) {
            return;
        }
        for (int i = 0; i < this.mCleanUpRunnables.size(); i++) {
            ((Runnable) this.mCleanUpRunnables.get(i)).run();
        }
        this.mCleanUpRunnables.clear();
    }

    public void setTransientLaunch(ActivityRecord activityRecord, Task task) {
        Transition transition = this.mCollectingTransition;
        if (transition == null) {
            return;
        }
        transition.setTransientLaunch(activityRecord, task);
        if (activityRecord.isActivityTypeHomeOrRecents()) {
            this.mCollectingTransition.addFlag(128);
            activityRecord.getTask().setCanAffectSystemUiFlags(false);
        }
    }

    public void setLateTransientLaunch(ActivityRecord activityRecord) {
        Transition transition = this.mCollectingTransition;
        if (transition == null) {
            return;
        }
        transition.setLateTransientLaunch(activityRecord);
    }

    public boolean isLateTransientLaunch(ActivityRecord activityRecord) {
        if (isLateTransientCollect(activityRecord)) {
            return true;
        }
        for (int size = this.mPlayingTransitions.size() - 1; size >= 0; size--) {
            if (((Transition) this.mPlayingTransitions.get(size)).isLateTransientLaunch(activityRecord)) {
                return true;
            }
        }
        return false;
    }

    public boolean isLateTransientCollect(ActivityRecord activityRecord) {
        Transition transition = this.mCollectingTransition;
        return transition != null && transition.isLateTransientLaunch(activityRecord);
    }

    public void setCanPipOnFinish(boolean z) {
        Transition transition = this.mCollectingTransition;
        if (transition == null) {
            return;
        }
        transition.setCanPipOnFinish(z);
    }

    public void legacyDetachNavigationBarFromApp(IBinder iBinder) {
        Transition fromBinder = Transition.fromBinder(iBinder);
        if (fromBinder == null || !this.mPlayingTransitions.contains(fromBinder)) {
            Slog.e("TransitionController", "Transition isn't playing: " + iBinder);
            return;
        }
        fromBinder.legacyRestoreNavigationBarFromApp();
    }

    public void registerLegacyListener(WindowManagerInternal.AppTransitionListener appTransitionListener) {
        this.mLegacyListeners.add(appTransitionListener);
    }

    public void unregisterLegacyListener(WindowManagerInternal.AppTransitionListener appTransitionListener) {
        this.mLegacyListeners.remove(appTransitionListener);
    }

    public void dispatchLegacyAppTransitionPending() {
        for (int i = 0; i < this.mLegacyListeners.size(); i++) {
            ((WindowManagerInternal.AppTransitionListener) this.mLegacyListeners.get(i)).onAppTransitionPendingLocked();
        }
    }

    public void dispatchLegacyAppTransitionStarting(TransitionInfo transitionInfo, long j) {
        for (int i = 0; i < this.mLegacyListeners.size(); i++) {
            ((WindowManagerInternal.AppTransitionListener) this.mLegacyListeners.get(i)).onAppTransitionStartingLocked(SystemClock.uptimeMillis() + j, 120L);
        }
    }

    public void dispatchLegacyAppTransitionFinished(ActivityRecord activityRecord) {
        for (int i = 0; i < this.mLegacyListeners.size(); i++) {
            ((WindowManagerInternal.AppTransitionListener) this.mLegacyListeners.get(i)).onAppTransitionFinishedLocked(activityRecord.token);
        }
    }

    public void dispatchLegacyAppTransitionCancelled() {
        for (int i = 0; i < this.mLegacyListeners.size(); i++) {
            ((WindowManagerInternal.AppTransitionListener) this.mLegacyListeners.get(i)).onAppTransitionCancelledLocked(false);
        }
    }

    public void dumpDebugLegacy(ProtoOutputStream protoOutputStream, long j) {
        int i;
        long start = protoOutputStream.start(j);
        if (this.mPlayingTransitions.isEmpty()) {
            Transition transition = this.mCollectingTransition;
            i = ((transition == null || !transition.getLegacyIsReady()) && !this.mSyncEngine.hasPendingSyncSets()) ? 0 : 1;
        } else {
            i = 2;
        }
        protoOutputStream.write(1159641169921L, i);
        protoOutputStream.end(start);
    }

    public final void queueTransition(Transition transition, OnStartCollect onStartCollect) {
        this.mQueuedTransitions.add(new QueuedTransition(transition, onStartCollect));
        if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, 1735199721, 0, "Queueing transition: %s", new Object[]{String.valueOf(transition)});
        }
    }

    public boolean startCollectOrQueue(Transition transition, OnStartCollect onStartCollect) {
        if (!this.mQueuedTransitions.isEmpty()) {
            queueTransition(transition, onStartCollect);
            return false;
        }
        if (this.mSyncEngine.hasActiveSync()) {
            if (isCollecting()) {
                if (canStartCollectingNow(transition)) {
                    if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, -266707683, 1, "Moving #%d from collecting to waiting.", new Object[]{Long.valueOf(this.mCollectingTransition.getSyncId())});
                    }
                    this.mWaitingTransitions.add(this.mCollectingTransition);
                    this.mCollectingTransition = null;
                    moveToCollecting(transition);
                    onStartCollect.onCollectStarted(false);
                    return true;
                }
            } else {
                Slog.w("TransitionController", "Ongoing Sync outside of transition.");
            }
            queueTransition(transition, onStartCollect);
            return false;
        }
        moveToCollecting(transition);
        onStartCollect.onCollectStarted(false);
        return true;
    }

    public Transition createAndStartCollecting(int i) {
        if (this.mTransitionPlayer == null || !this.mQueuedTransitions.isEmpty()) {
            return null;
        }
        if (this.mSyncEngine.hasActiveSync()) {
            if (isCollecting()) {
                if (canStartCollectingNow(null)) {
                    if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled) {
                        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, -266707683, 1, "Moving #%d from collecting to waiting.", new Object[]{Long.valueOf(this.mCollectingTransition.getSyncId())});
                    }
                    this.mWaitingTransitions.add(this.mCollectingTransition);
                    this.mCollectingTransition = null;
                    Transition transition = new Transition(i, 0, this, this.mSyncEngine);
                    moveToCollecting(transition);
                    return transition;
                }
            } else {
                Slog.w("TransitionController", "Ongoing Sync outside of transition.");
            }
            return null;
        }
        Transition transition2 = new Transition(i, 0, this, this.mSyncEngine);
        moveToCollecting(transition2);
        return transition2;
    }

    public void startLegacySyncOrQueue(BLASTSyncEngine.SyncGroup syncGroup, final Consumer consumer) {
        if (!this.mQueuedTransitions.isEmpty() || this.mSyncEngine.hasActiveSync()) {
            this.mQueuedTransitions.add(new QueuedTransition(syncGroup, new OnStartCollect() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda6
                @Override // com.android.server.wm.TransitionController.OnStartCollect
                public final void onCollectStarted(boolean z) {
                    TransitionController.lambda$startLegacySyncOrQueue$9(consumer, z);
                }
            }));
            if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, 1463355909, 0, "Queueing legacy sync-set: %s", new Object[]{String.valueOf(syncGroup.mSyncId)});
                return;
            }
            return;
        }
        this.mSyncEngine.startSyncSet(syncGroup);
        consumer.accept(Boolean.FALSE);
    }

    public static /* synthetic */ void lambda$startLegacySyncOrQueue$9(Consumer consumer, boolean z) {
        consumer.accept(Boolean.TRUE);
    }

    public boolean shouldWallpaperBeVisible() {
        Transition transition = this.mCollectingTransition;
        if (transition != null && transition.shouldWallpaperBeVisible()) {
            return true;
        }
        for (int size = this.mPlayingTransitions.size() - 1; size >= 0; size--) {
            if (((Transition) this.mPlayingTransitions.get(size)).shouldWallpaperBeVisible()) {
                return true;
            }
        }
        return false;
    }

    public void notifyAbort(Transition transition) {
        ITransitionPlayer iTransitionPlayer;
        if (transition.mLogger.mRequest == null || (iTransitionPlayer = this.mTransitionPlayer) == null) {
            return;
        }
        try {
            iTransitionPlayer.transitionAborted(transition.getToken());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setCleanupRunnable(Runnable runnable) {
        this.mCleanUpRunnables.add(runnable);
    }

    public void setHasTopUiIfNeeded(final boolean z) {
        if (this.mRequestHasTopUi != z) {
            WindowProcessController windowProcessController = this.mTransitionPlayerProc;
            if (windowProcessController != null) {
                final int pid = windowProcessController.getPid();
                this.mAtm.f1734mH.post(new Runnable() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        TransitionController.this.lambda$setHasTopUiIfNeeded$10(pid, z);
                    }
                });
            }
            this.mRequestHasTopUi = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setHasTopUiIfNeeded$10(int i, boolean z) {
        this.mAtm.mAmInternal.setHasTopUiInternal(i, z);
    }

    public boolean hasTransientLaunch() {
        Transition transition = this.mCollectingTransition;
        if (transition != null && transition.hasTransientLaunch()) {
            return true;
        }
        for (int size = this.mWaitingTransitions.size() - 1; size >= 0; size--) {
            if (((Transition) this.mWaitingTransitions.get(size)).hasTransientLaunch()) {
                return true;
            }
        }
        for (int size2 = this.mPlayingTransitions.size() - 1; size2 >= 0; size2--) {
            if (((Transition) this.mPlayingTransitions.get(size2)).hasTransientLaunch()) {
                return true;
            }
        }
        return false;
    }

    public class RemotePlayer {
        public final ActivityTaskManagerService mAtm;
        public final ArrayMap mDelegateProcesses = new ArrayMap();

        public class DelegateProcess implements Runnable {
            public boolean mNeedReport;
            public final WindowProcessController mProc;

            public DelegateProcess(WindowProcessController windowProcessController) {
                this.mProc = windowProcessController;
            }

            @Override // java.lang.Runnable
            public void run() {
                synchronized (RemotePlayer.this.mAtm.mGlobalLockWithoutBoost) {
                    RemotePlayer.this.update(this.mProc, false, false);
                }
            }
        }

        public RemotePlayer(ActivityTaskManagerService activityTaskManagerService) {
            this.mAtm = activityTaskManagerService;
        }

        public void update(WindowProcessController windowProcessController, boolean z, boolean z2) {
            boolean z3 = true;
            if (!z) {
                synchronized (this.mDelegateProcesses) {
                    int size = this.mDelegateProcesses.size() - 1;
                    while (true) {
                        if (size < 0) {
                            z3 = false;
                            break;
                        } else {
                            if (((DelegateProcess) this.mDelegateProcesses.valueAt(size)).mProc == windowProcessController) {
                                this.mDelegateProcesses.removeAt(size);
                                break;
                            }
                            size--;
                        }
                    }
                    if (z3) {
                        windowProcessController.setRunningRemoteAnimation(false);
                        return;
                    }
                    return;
                }
            }
            if (windowProcessController.isRunningRemoteTransition() || !windowProcessController.hasThread()) {
                return;
            }
            windowProcessController.setRunningRemoteAnimation(true);
            DelegateProcess delegateProcess = new DelegateProcess(windowProcessController);
            if (z2) {
                delegateProcess.mNeedReport = true;
                this.mAtm.f1734mH.postDelayed(delegateProcess, 100L);
            }
            synchronized (this.mDelegateProcesses) {
                this.mDelegateProcesses.put(windowProcessController.getThread().asBinder(), delegateProcess);
            }
        }

        public void clear() {
            synchronized (this.mDelegateProcesses) {
                for (int size = this.mDelegateProcesses.size() - 1; size >= 0; size--) {
                    ((DelegateProcess) this.mDelegateProcesses.valueAt(size)).mProc.setRunningRemoteAnimation(false);
                }
                this.mDelegateProcesses.clear();
            }
        }

        public boolean reportRunning(IApplicationThread iApplicationThread) {
            DelegateProcess delegateProcess;
            synchronized (this.mDelegateProcesses) {
                delegateProcess = (DelegateProcess) this.mDelegateProcesses.get(iApplicationThread.asBinder());
                if (delegateProcess != null && delegateProcess.mNeedReport) {
                    delegateProcess.mNeedReport = false;
                    this.mAtm.f1734mH.removeCallbacks(delegateProcess);
                }
            }
            return delegateProcess != null;
        }
    }

    public class Logger implements Runnable {
        public long mAbortTimeNs;
        public long mCollectTimeNs;
        public long mCreateTimeNs;
        public long mCreateWallTimeMs;
        public long mFinishTimeNs;
        public TransitionInfo mInfo;
        public long mReadyTimeNs;
        public TransitionRequestInfo mRequest;
        public long mRequestTimeNs;
        public long mSendTimeNs;
        public long mStartTimeNs;
        public WindowContainerTransaction mStartWCT;
        public int mSyncId;

        public final String buildOnSendLog() {
            StringBuilder sb = new StringBuilder("Sent Transition #");
            sb.append(this.mSyncId);
            sb.append(" createdAt=");
            sb.append(TimeUtils.logTimeOfDay(this.mCreateWallTimeMs));
            if (this.mRequest != null) {
                sb.append(" via request=");
                sb.append(this.mRequest);
            }
            return sb.toString();
        }

        public void logOnSendAsync(Handler handler) {
            handler.post(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                logOnSend();
            } catch (Exception e) {
                Slog.w("TransitionController", "Failed to log transition", e);
            }
        }

        public void logOnSend() {
            if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, 2021079047, 0, "%s", new Object[]{String.valueOf(buildOnSendLog())});
            }
            if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, 1621562070, 0, "    startWCT=%s", new Object[]{String.valueOf(this.mStartWCT)});
            }
            if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, 273212558, 0, "    info=%s", new Object[]{String.valueOf(this.mInfo.toString("    "))});
            }
        }

        public static String toMsString(long j) {
            return (Math.round(j / 1000.0d) / 1000.0d) + "ms";
        }

        public final String buildOnFinishLog() {
            StringBuilder sb = new StringBuilder("Finish Transition #");
            sb.append(this.mSyncId);
            sb.append(": created at ");
            sb.append(TimeUtils.logTimeOfDay(this.mCreateWallTimeMs));
            sb.append(" collect-started=");
            sb.append(toMsString(this.mCollectTimeNs - this.mCreateTimeNs));
            if (this.mRequestTimeNs != 0) {
                sb.append(" request-sent=");
                sb.append(toMsString(this.mRequestTimeNs - this.mCreateTimeNs));
            }
            sb.append(" started=");
            sb.append(toMsString(this.mStartTimeNs - this.mCreateTimeNs));
            sb.append(" ready=");
            sb.append(toMsString(this.mReadyTimeNs - this.mCreateTimeNs));
            sb.append(" sent=");
            sb.append(toMsString(this.mSendTimeNs - this.mCreateTimeNs));
            sb.append(" finished=");
            sb.append(toMsString(this.mFinishTimeNs - this.mCreateTimeNs));
            return sb.toString();
        }

        public void logOnFinish() {
            if (ProtoLogCache.WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, 2021079047, 0, "%s", new Object[]{String.valueOf(buildOnFinishLog())});
            }
        }
    }

    public class TransitionMetricsReporter extends ITransitionMetricsReporter.Stub {
        public final ArrayMap mMetricConsumers = new ArrayMap();

        public void associate(IBinder iBinder, LongConsumer longConsumer) {
            synchronized (this.mMetricConsumers) {
                this.mMetricConsumers.put(iBinder, longConsumer);
            }
        }

        public void reportAnimationStart(IBinder iBinder, long j) {
            synchronized (this.mMetricConsumers) {
                if (this.mMetricConsumers.isEmpty()) {
                    return;
                }
                LongConsumer longConsumer = (LongConsumer) this.mMetricConsumers.remove(iBinder);
                if (longConsumer != null) {
                    longConsumer.accept(j);
                }
            }
        }
    }

    public class Lock {
        public int mTransitionWaiters = 0;

        public Lock() {
        }

        public void runWhenIdle(long j, Runnable runnable) {
            WindowManagerGlobalLock windowManagerGlobalLock = TransitionController.this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (!TransitionController.this.inTransition()) {
                        runnable.run();
                        return;
                    }
                    this.mTransitionWaiters++;
                    WindowManagerService.resetPriorityAfterLockedSection();
                    long uptimeMillis = SystemClock.uptimeMillis() + j;
                    while (true) {
                        WindowManagerGlobalLock windowManagerGlobalLock2 = TransitionController.this.mAtm.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock2) {
                            try {
                                if (!TransitionController.this.inTransition() || SystemClock.uptimeMillis() > uptimeMillis) {
                                    break;
                                }
                            } finally {
                                WindowManagerService.resetPriorityAfterLockedSection();
                            }
                        }
                        synchronized (this) {
                            try {
                                try {
                                    wait(j);
                                } catch (InterruptedException unused) {
                                    return;
                                }
                            } finally {
                            }
                        }
                    }
                    this.mTransitionWaiters--;
                    runnable.run();
                    WindowManagerService.resetPriorityAfterLockedSection();
                } finally {
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            }
        }

        public void doNotifyLocked() {
            synchronized (this) {
                if (this.mTransitionWaiters > 0) {
                    notifyAll();
                }
            }
        }
    }
}
