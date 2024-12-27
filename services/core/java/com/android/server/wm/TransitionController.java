package com.android.server.wm;

import android.app.ActivityManager;
import android.app.IApplicationThread;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.window.ITransitionMetricsReporter;
import android.window.ITransitionPlayer;
import android.window.RemoteTransition;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.FgThread;
import com.android.server.wm.BLASTSyncEngine.SyncGroup;

import com.samsung.android.rune.CoreRune;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TransitionController {
    public static final boolean SHELL_TRANSITIONS_ROTATION =
            SystemProperties.getBoolean("persist.wm.debug.shell_transit_rotate", false);
    public static final int SYNC_METHOD =
            SystemProperties.getBoolean("persist.wm.debug.shell_transit_blast", false) ? 1 : 0;
    public final ActivityTaskManagerService mAtm;
    public Transition mFinishingTransition;
    public final RemotePlayer mRemotePlayer;
    public SnapshotController mSnapshotController;
    public BLASTSyncEngine mSyncEngine;
    public TransitionTracer mTransitionTracer;
    public final ArrayList mTransitionPlayers = new ArrayList();
    public final TransitionMetricsReporter mTransitionMetricsReporter =
            new TransitionMetricsReporter();
    public boolean mFullReadyTracking = false;
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
    public final ArrayList mCleanUpRunnableList = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lock {
        public int mTransitionWaiters = 0;

        public Lock() {}

        public final void runWhenIdle(Runnable runnable) {
            WindowManagerGlobalLock windowManagerGlobalLock =
                    TransitionController.this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (!TransitionController.this.inTransition()) {
                        runnable.run();
                        return;
                    }
                    this.mTransitionWaiters++;
                    WindowManagerService.resetPriorityAfterLockedSection();
                    long uptimeMillis = SystemClock.uptimeMillis() + 1000;
                    while (true) {
                        WindowManagerGlobalLock windowManagerGlobalLock2 =
                                TransitionController.this.mAtm.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock2) {
                            try {
                                if (!TransitionController.this.inTransition()
                                        || SystemClock.uptimeMillis() > uptimeMillis) {
                                    break;
                                }
                            } finally {
                                WindowManagerService.resetPriorityAfterLockedSection();
                            }
                        }
                        synchronized (this) {
                            try {
                                try {
                                    wait(1000L);
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
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Logger implements Runnable {
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

        public static String toMsString(long j) {
            return (Math.round(j / 1000.0d) / 1000.0d) + "ms";
        }

        public final void logOnSend() {
            boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled;
            if (zArr[1]) {
                StringBuilder sb = new StringBuilder("Sent Transition (#");
                sb.append(this.mSyncId);
                sb.append(") createdAt=");
                sb.append(TimeUtils.logTimeOfDay(this.mCreateWallTimeMs));
                if (this.mRequest != null) {
                    sb.append(" via request=");
                    sb.append(this.mRequest);
                }
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN,
                        -5051723169912572741L,
                        0,
                        "%s",
                        String.valueOf(sb.toString()));
            }
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN,
                        4281568181321808508L,
                        0,
                        "    startWCT=%s",
                        String.valueOf(this.mStartWCT));
            }
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN,
                        5141999957143860655L,
                        0,
                        "    info=%s",
                        String.valueOf(this.mInfo.toString("    ")));
            }
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                logOnSend();
            } catch (Exception e) {
                Slog.w("TransitionController", "Failed to log transition", e);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface OnStartCollect {
        void onCollectStarted(boolean z);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class QueuedTransition {
        public final BLASTSyncEngine.SyncGroup mLegacySync;
        public final OnStartCollect mOnStartCollect;
        public final Transition mTransition;

        public QueuedTransition(
                BLASTSyncEngine.SyncGroup syncGroup,
                TransitionController$$ExternalSyntheticLambda6
                        transitionController$$ExternalSyntheticLambda6) {
            this.mTransition = null;
            this.mOnStartCollect = transitionController$$ExternalSyntheticLambda6;
            this.mLegacySync = syncGroup;
        }

        public QueuedTransition(Transition transition, OnStartCollect onStartCollect) {
            this.mTransition = transition;
            this.mOnStartCollect = onStartCollect;
            this.mLegacySync = null;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RemotePlayer {
        public final ActivityTaskManagerService mAtm;
        public final ArrayMap mDelegateProcesses = new ArrayMap();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class DelegateProcess implements Runnable {
            public boolean mNeedReport;
            public final WindowProcessController mProc;

            public DelegateProcess(WindowProcessController windowProcessController) {
                this.mProc = windowProcessController;
            }

            @Override // java.lang.Runnable
            public final void run() {
                synchronized (RemotePlayer.this.mAtm.mGlobalLockWithoutBoost) {
                    RemotePlayer.this.update(this.mProc, false, false);
                }
            }
        }

        public RemotePlayer(ActivityTaskManagerService activityTaskManagerService) {
            this.mAtm = activityTaskManagerService;
        }

        public final void clear() {
            synchronized (this.mDelegateProcesses) {
                try {
                    for (int size = this.mDelegateProcesses.size() - 1; size >= 0; size--) {
                        ((DelegateProcess) this.mDelegateProcesses.valueAt(size))
                                .mProc.removeAnimatingReason(1);
                    }
                    this.mDelegateProcesses.clear();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void update(
                WindowProcessController windowProcessController, boolean z, boolean z2) {
            boolean z3 = true;
            if (!z) {
                synchronized (this.mDelegateProcesses) {
                    for (int size = this.mDelegateProcesses.size() - 1; size >= 0; size--) {
                        if (((DelegateProcess) this.mDelegateProcesses.valueAt(size)).mProc
                                == windowProcessController) {
                            this.mDelegateProcesses.removeAt(size);
                            windowProcessController.removeAnimatingReason(1);
                            return;
                        }
                    }
                    return;
                }
            }
            if ((windowProcessController.mAnimatingReasons & 1) == 0
                    && windowProcessController.hasThread()) {
                int i = windowProcessController.mAnimatingReasons;
                windowProcessController.mAnimatingReasons = i | 1;
                if (i == 0) {
                    windowProcessController.mAtm.mH.post(
                            new WindowProcessController$$ExternalSyntheticLambda1(
                                    windowProcessController, z3));
                }
                DelegateProcess delegateProcess = new DelegateProcess(windowProcessController);
                if (z2) {
                    delegateProcess.mNeedReport = true;
                    this.mAtm.mH.postDelayed(delegateProcess, 200L);
                }
                synchronized (this.mDelegateProcesses) {
                    this.mDelegateProcesses.put(
                            windowProcessController.mThread.asBinder(), delegateProcess);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TransitionMetricsReporter extends ITransitionMetricsReporter.Stub {
        public final ArrayMap mMetricConsumers = new ArrayMap();

        public final void reportAnimationStart(IBinder iBinder, long j) {
            synchronized (this.mMetricConsumers) {
                try {
                    if (this.mMetricConsumers.isEmpty()) {
                        return;
                    }
                    LongConsumer longConsumer =
                            (LongConsumer) this.mMetricConsumers.remove(iBinder);
                    if (longConsumer != null) {
                        longConsumer.accept(j);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TransitionPlayerRecord {
        public TransitionController$TransitionPlayerRecord$$ExternalSyntheticLambda0 mDeath;
        public final ITransitionPlayer mPlayer;
        public final WindowProcessController mPlayerProc;

        /* JADX WARN: Type inference failed for: r1v4, types: [com.android.server.wm.TransitionController$TransitionPlayerRecord$$ExternalSyntheticLambda0] */
        public TransitionPlayerRecord(
                ITransitionPlayer iTransitionPlayer,
                WindowProcessController windowProcessController) {
            this.mDeath = null;
            this.mPlayer = iTransitionPlayer;
            this.mPlayerProc = windowProcessController;
            try {
                if (iTransitionPlayer.asBinder() == null) {
                    return;
                }
                this.mDeath =
                        new IBinder
                                .DeathRecipient() { // from class:
                                                    // com.android.server.wm.TransitionController$TransitionPlayerRecord$$ExternalSyntheticLambda0
                            @Override // android.os.IBinder.DeathRecipient
                            public final void binderDied() {
                                TransitionController.TransitionPlayerRecord transitionPlayerRecord =
                                        TransitionController.TransitionPlayerRecord.this;
                                WindowManagerGlobalLock windowManagerGlobalLock =
                                        TransitionController.this.mAtm.mGlobalLock;
                                WindowManagerService.boostPriorityForLockedSection();
                                synchronized (windowManagerGlobalLock) {
                                    try {
                                        TransitionController.this.unregisterTransitionPlayer(
                                                transitionPlayerRecord.mPlayer);
                                    } catch (Throwable th) {
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                        throw th;
                                    }
                                }
                                WindowManagerService.resetPriorityAfterLockedSection();
                            }
                        };
                iTransitionPlayer.asBinder().linkToDeath(this.mDeath, 0);
            } catch (RemoteException unused) {
                throw new RuntimeException("Unable to set transition player");
            }
        }
    }

    public TransitionController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
        this.mRemotePlayer = new RemotePlayer(activityTaskManagerService);
    }

    public static boolean getCanBeIndependent(Transition transition, Transition transition2) {
        if (transition2 != null
                && transition2.mParallelCollectType == 1
                && transition.mParallelCollectType == 1) {
            return true;
        }
        if (transition2 == null || transition2.mParallelCollectType != 2) {
            if (transition.mParallelCollectType == 2) {
                return transition2 == null || (transition2.mFlags & 524288) == 0;
            }
            return false;
        }
        if (transition.mParallelCollectType == 2) {
            return false;
        }
        for (int i = 0; i < transition.mParticipants.size(); i++) {
            WindowContainer windowContainer = (WindowContainer) transition.mParticipants.valueAt(i);
            ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
            if (asActivityRecord == null
                    && windowContainer.asWindowState() == null
                    && windowContainer.asWindowToken() == null) {
                return false;
            }
            if (asActivityRecord != null && asActivityRecord.isActivityTypeHomeOrRecents()) {
                return false;
            }
        }
        return true;
    }

    public static void setDisplaySyncMethod(Rect rect, Rect rect2, DisplayContent displayContent) {
        int width = rect.width();
        int height = rect.height();
        int width2 = rect2.width();
        int height2 = rect2.height();
        if ((width2 > width) == (height2 > height)) {
            if (width2 == width && height2 == height) {
                return;
            }
            displayContent.forAllWindows(
                    (Consumer) new TransitionController$$ExternalSyntheticLambda7(), true);
        }
    }

    public final boolean canStartCollectingNow(Transition transition) {
        Transition transition2 = this.mCollectingTransition;
        if (transition2 == null) {
            return true;
        }
        if (transition2.mState < 1
                || !transition2.mReadyTrackerOld.allReady()
                || !getCanBeIndependent(this.mCollectingTransition, transition)) {
            return false;
        }
        for (int i = 0; i < this.mWaitingTransitions.size(); i++) {
            if (!getCanBeIndependent((Transition) this.mWaitingTransitions.get(i), transition)) {
                return false;
            }
        }
        return true;
    }

    public final void collect(WindowContainer windowContainer) {
        Transition transition = this.mCollectingTransition;
        if (transition == null) {
            return;
        }
        transition.collect(windowContainer, false);
    }

    public final void collectForDisplayAreaChange(DisplayArea displayArea) {
        final Transition transition = this.mCollectingTransition;
        if (transition == null || !transition.mParticipants.contains(displayArea)) {
            return;
        }
        transition.collectVisibleChange(displayArea);
        displayArea.forAllLeafTasks(
                new TransitionController$$ExternalSyntheticLambda2(transition, 0), true);
        DisplayContent asDisplayContent = displayArea.asDisplayContent();
        if (asDisplayContent != null) {
            final boolean z = asDisplayContent.getAsyncRotationController() == null;
            displayArea.forAllWindows(
                    new Consumer() { // from class:
                                     // com.android.server.wm.TransitionController$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            TransitionController transitionController = TransitionController.this;
                            boolean z2 = z;
                            Transition transition2 = transition;
                            WindowState windowState = (WindowState) obj;
                            transitionController.getClass();
                            if (windowState.mActivityRecord == null
                                    && windowState.isVisible()
                                    && !transitionController.isCollecting(windowState.mToken)) {
                                if (z2 || !AsyncRotationController.canBeAsync(windowState.mToken)) {
                                    transition2.collect(windowState.mToken, false);
                                }
                            }
                        }
                    },
                    true);
        }
    }

    public final void collectVisibleChange(WindowContainer windowContainer) {
        if (isCollecting()) {
            this.mCollectingTransition.collectVisibleChange(windowContainer);
        }
    }

    public final void continueTransitionReady() {
        if (isShellTransitionsEnabled()) {
            Transition transition = this.mCollectingTransition;
            if (transition == null) {
                throw new IllegalStateException("No collecting transition to defer readiness for.");
            }
            Transition.ReadyTrackerOld readyTrackerOld = transition.mReadyTrackerOld;
            readyTrackerOld.mDeferReadyDepth--;
            transition.applyReady();
        }
    }

    public final Transition createTransition(int i, int i2) {
        if (this.mTransitionPlayers.isEmpty()) {
            throw new IllegalStateException("Shell Transitions not enabled");
        }
        if (this.mCollectingTransition != null) {
            throw new IllegalStateException(
                    "Trying to directly start transition collection while  collection is already"
                        + " ongoing. Use {@link #startCollectOrQueue} if possible.");
        }
        Transition transition = new Transition(i, i2, this, this.mSyncEngine);
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                    -233096875591058130L,
                    0,
                    null,
                    String.valueOf(transition));
        }
        moveToCollecting(transition);
        return transition;
    }

    public final void deferTransitionReady() {
        if (isShellTransitionsEnabled()) {
            Transition transition = this.mCollectingTransition;
            if (transition == null) {
                throw new IllegalStateException("No collecting transition to defer readiness for.");
            }
            transition.mReadyTrackerOld.mDeferReadyDepth++;
            transition.mSyncEngine.setReady(transition.mSyncId, false);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:284:0x04a0, code lost:

       if (r4.windowsCanBeWallpaperTarget() != false) goto L292;
    */
    /* JADX WARN: Code restructure failed: missing block: B:295:0x04a5, code lost:

       if (r7 == false) goto L291;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0287  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x0319  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x036a  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x03db A[ADDED_TO_REGION, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v101 */
    /* JADX WARN: Type inference failed for: r0v125 */
    /* JADX WARN: Type inference failed for: r0v23, types: [com.android.server.wm.BackNavigationController] */
    /* JADX WARN: Type inference failed for: r0v80 */
    /* JADX WARN: Type inference failed for: r0v81 */
    /* JADX WARN: Type inference failed for: r3v10, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v41 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void finishTransition(final com.android.server.wm.Transition r28) {
        /*
            Method dump skipped, instructions count: 2850
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.TransitionController.finishTransition(com.android.server.wm.Transition):void");
    }

    public final void flushRunningTransitions() {
        ArrayList arrayList = new ArrayList(this.mTransitionPlayers);
        this.mTransitionPlayers.clear();
        for (int size = this.mPlayingTransitions.size() - 1; size >= 0; size--) {
            Transition transition = (Transition) this.mPlayingTransitions.get(size);
            if (transition.mState >= 2) {
                SurfaceControl.Transaction transaction = transition.mStartTransaction;
                if (transaction != null) {
                    transaction.apply();
                }
                SurfaceControl.Transaction transaction2 = transition.mFinishTransaction;
                if (transaction2 != null) {
                    transaction2.apply();
                }
                transition.mController.finishTransition(transition);
            }
        }
        this.mPlayingTransitions.clear();
        for (int size2 = this.mWaitingTransitions.size() - 1; size2 >= 0; size2--) {
            ((Transition) this.mWaitingTransitions.get(size2)).abort();
        }
        this.mWaitingTransitions.clear();
        Transition transition2 = this.mCollectingTransition;
        if (transition2 != null) {
            transition2.abort();
        }
        this.mRemotePlayer.clear();
        Lock lock = this.mRunningLock;
        synchronized (lock) {
            try {
                if (lock.mTransitionWaiters > 0) {
                    lock.notifyAll();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mTransitionPlayers.addAll(arrayList);
    }

    public final int getCollectingTransitionId() {
        Transition transition = this.mCollectingTransition;
        if (transition != null) {
            return transition.mSyncId;
        }
        throw new IllegalStateException("There is no collecting transition");
    }

    public final int getCollectingTransitionType() {
        Transition transition = this.mCollectingTransition;
        if (transition != null) {
            return transition.mType;
        }
        return 0;
    }

    public final ITransitionPlayer getTransitionPlayer() {
        if (this.mTransitionPlayers.isEmpty()) {
            return null;
        }
        return ((TransitionPlayerRecord) this.mTransitionPlayers.getLast()).mPlayer;
    }

    public final boolean hasCollectingRotationChange(int i, WindowContainer windowContainer) {
        Transition.ChangeInfo changeInfo;
        Transition transition = this.mCollectingTransition;
        return (transition == null
                        || !transition.mParticipants.contains(windowContainer)
                        || (changeInfo =
                                        (Transition.ChangeInfo)
                                                transition.mChanges.get(windowContainer))
                                == null
                        || changeInfo.mRotation == i)
                ? false
                : true;
    }

    public final boolean hasTransientLaunch(DisplayContent displayContent) {
        Transition transition = this.mCollectingTransition;
        if (transition != null
                && transition.hasTransientLaunch()
                && this.mCollectingTransition.mTargetDisplays.contains(displayContent)) {
            return true;
        }
        for (int size = this.mWaitingTransitions.size() - 1; size >= 0; size--) {
            Transition transition2 = (Transition) this.mWaitingTransitions.get(size);
            if (transition2.hasTransientLaunch()
                    && transition2.mTargetDisplays.contains(displayContent)) {
                return true;
            }
        }
        for (int size2 = this.mPlayingTransitions.size() - 1; size2 >= 0; size2--) {
            Transition transition3 = (Transition) this.mPlayingTransitions.get(size2);
            if (transition3.hasTransientLaunch()
                    && transition3.mTargetDisplays.contains(displayContent)) {
                return true;
            }
        }
        return false;
    }

    public final boolean inCollectingTransition(WindowContainer windowContainer) {
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

    public final boolean inFinishingTransition(WindowContainer windowContainer) {
        Transition transition = this.mFinishingTransition;
        return transition != null && transition.isInTransition(windowContainer);
    }

    public final boolean inPlayingTransition(WindowContainer windowContainer) {
        for (int size = this.mPlayingTransitions.size() - 1; size >= 0; size--) {
            if (((Transition) this.mPlayingTransitions.get(size)).isInTransition(windowContainer)) {
                return true;
            }
        }
        return false;
    }

    public final boolean inTransition() {
        return isCollecting()
                || (this.mPlayingTransitions.isEmpty() ^ true)
                || !this.mQueuedTransitions.isEmpty();
    }

    public final boolean inTransition(int i) {
        Transition transition = this.mCollectingTransition;
        if (transition != null && transition.mSyncId == i) {
            return true;
        }
        for (int size = this.mPlayingTransitions.size() - 1; size >= 0; size--) {
            if (((Transition) this.mPlayingTransitions.get(size)).mSyncId == i) {
                return true;
            }
        }
        return false;
    }

    public final boolean inTransition(WindowContainer windowContainer) {
        return inCollectingTransition(windowContainer) || inPlayingTransition(windowContainer);
    }

    public final boolean isCollecting() {
        return this.mCollectingTransition != null;
    }

    public final boolean isCollecting(WindowContainer windowContainer) {
        Transition transition = this.mCollectingTransition;
        if (transition == null) {
            return false;
        }
        if (transition.mParticipants.contains(windowContainer)) {
            return true;
        }
        for (int i = 0; i < this.mWaitingTransitions.size(); i++) {
            if (((Transition) this.mWaitingTransitions.get(i))
                    .mParticipants.contains(windowContainer)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isShellTransitionsEnabled() {
        return !this.mTransitionPlayers.isEmpty();
    }

    public final boolean isTransientHide(Task task) {
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

    public final boolean isTransientLaunch(ActivityRecord activityRecord) {
        Transition transition = this.mCollectingTransition;
        if (transition != null && transition.isTransientLaunch(activityRecord)) {
            return true;
        }
        for (int size = this.mWaitingTransitions.size() - 1; size >= 0; size--) {
            if (((Transition) this.mWaitingTransitions.get(size))
                    .isTransientLaunch(activityRecord)) {
                return true;
            }
        }
        for (int size2 = this.mPlayingTransitions.size() - 1; size2 >= 0; size2--) {
            if (((Transition) this.mPlayingTransitions.get(size2))
                    .isTransientLaunch(activityRecord)) {
                return true;
            }
        }
        return false;
    }

    public final void moveToCollecting(Transition transition) {
        if (this.mCollectingTransition != null) {
            throw new IllegalStateException("Simultaneous transition collection not supported.");
        }
        if (this.mTransitionPlayers.isEmpty()) {
            transition.abort();
            return;
        }
        this.mCollectingTransition = transition;
        long j = transition.mType == 6 ? 2000L : 5000L;
        if (transition.mState != -1) {
            throw new IllegalStateException("Attempting to re-use a transition");
        }
        transition.mState = 0;
        String str = "Transition-" + WindowManager.transitTypeToString(transition.mType);
        boolean z = transition.mParallelCollectType != 0;
        BLASTSyncEngine bLASTSyncEngine = transition.mSyncEngine;
        bLASTSyncEngine.getClass();
        int i = bLASTSyncEngine.mNextSyncId;
        bLASTSyncEngine.mNextSyncId = i + 1;
        BLASTSyncEngine.SyncGroup syncGroup = bLASTSyncEngine.new SyncGroup(transition, i, str);
        bLASTSyncEngine.startSyncSet(syncGroup, j, z);
        int i2 = syncGroup.mSyncId;
        transition.mSyncId = i2;
        bLASTSyncEngine.setSyncMethod(i2, SYNC_METHOD);
        int i3 = transition.mSyncId;
        Logger logger = transition.mLogger;
        logger.mSyncId = i3;
        logger.mCollectTimeNs = SystemClock.elapsedRealtimeNanos();
        if (CoreRune.FW_SHELL_TRANSITION_LOG) {
            if (ProtoLogImpl_54989576.Cache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS,
                        6474003188052606594L,
                        0,
                        "Start collecting in Transition: %s, caller=%s",
                        String.valueOf(this.mCollectingTransition),
                        String.valueOf(Debug.getCallers(5)));
            }
        } else if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                    2154694726162725342L,
                    0,
                    null,
                    String.valueOf(this.mCollectingTransition));
        }
        for (int i4 = 0; i4 < this.mLegacyListeners.size(); i4++) {
            ((WindowManagerInternal.AppTransitionListener) this.mLegacyListeners.get(i4))
                    .onAppTransitionPendingLocked();
        }
    }

    public final void queueTransition(Transition transition, OnStartCollect onStartCollect) {
        this.mQueuedTransitions.add(new QueuedTransition(transition, onStartCollect));
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN,
                    -5509640937151643757L,
                    0,
                    "Queueing transition: %s",
                    String.valueOf(transition));
        }
    }

    public final void registerLegacyListener(
            WindowManagerInternal.AppTransitionListener appTransitionListener) {
        this.mLegacyListeners.add(appTransitionListener);
    }

    public final void registerTransitionPlayer(
            ITransitionPlayer iTransitionPlayer, WindowProcessController windowProcessController) {
        boolean isEmpty = this.mTransitionPlayers.isEmpty();
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled;
        if (!isEmpty) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN,
                        -4546322749928357965L,
                        4,
                        "Registering transition player %s over %d other players",
                        String.valueOf(iTransitionPlayer.asBinder()),
                        Long.valueOf(this.mTransitionPlayers.size()));
            }
            flushRunningTransitions();
        } else if (zArr[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN,
                    -4250307779892136611L,
                    0,
                    "Registering transition player %s ",
                    String.valueOf(iTransitionPlayer.asBinder()));
        }
        this.mTransitionPlayers.add(
                new TransitionPlayerRecord(iTransitionPlayer, windowProcessController));
    }

    public final Transition requestCloseTransitionIfNeeded(WindowContainer windowContainer) {
        if (this.mTransitionPlayers.isEmpty() || isCollecting()) {
            return null;
        }
        Task asTask =
                windowContainer.asActivityRecord() != null
                        ? windowContainer.asActivityRecord().task
                        : windowContainer.asTask();
        if (asTask == null || !asTask.isFreeformForceHidden()) {
            if (windowContainer.isVisibleRequested()) {
                return requestStartTransition(
                        createTransition(2, 0), windowContainer.asTask(), null, null);
            }
            return null;
        }
        Slog.d(
                "TransitionController",
                "requestCloseTransitionIfNeeded: skip, force hidden, t=" + asTask);
        return null;
    }

    public final Transition requestStartTransition(
            Transition transition,
            Task task,
            RemoteTransition remoteTransition,
            TransitionRequestInfo.DisplayChange displayChange) {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        boolean z = this.mIsWaitingForDisplayEnabled;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled;
        if (z) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                        -4235778637051052061L,
                        1,
                        null,
                        Long.valueOf(transition.mSyncId));
            }
            transition.mIsPlayerEnabled = false;
            transition.mLogger.mRequestTimeNs = SystemClock.uptimeNanos();
            this.mAtm.mH.post(
                    new TransitionController$$ExternalSyntheticLambda0(this, transition, 0));
            return transition;
        }
        if (!this.mTransitionPlayers.isEmpty()) {
            boolean z2 = transition.mState == 3;
            Logger logger = transition.mLogger;
            if (!z2) {
                try {
                    if (CoreRune.FW_SHELL_TRANSITION_LOG) {
                        if (ProtoLogImpl_54989576.Cache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled[
                                1]) {
                            ProtoLogImpl_54989576.v(
                                    ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS,
                                    7440374942941917421L,
                                    0,
                                    "Requesting StartTransition: %s, caller=%s",
                                    String.valueOf(transition),
                                    String.valueOf(Debug.getCallers(5)));
                        }
                    } else if (zArr[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                                4005704720444963797L,
                                0,
                                null,
                                String.valueOf(transition));
                    }
                    ActivityManager.RunningTaskInfo taskInfo =
                            task != null ? task.getTaskInfo() : null;
                    ActivityRecord activityRecord = transition.mPipActivity;
                    if (activityRecord != null) {
                        ActivityManager.RunningTaskInfo taskInfo2 =
                                activityRecord.task.getTaskInfo();
                        transition.mPipActivity = null;
                        runningTaskInfo = taskInfo2;
                    } else {
                        runningTaskInfo = null;
                    }
                    TransitionRequestInfo transitionRequestInfo =
                            new TransitionRequestInfo(
                                    transition.mType,
                                    taskInfo,
                                    runningTaskInfo,
                                    remoteTransition,
                                    displayChange,
                                    transition.mFlags,
                                    transition.mSyncId);
                    logger.mRequestTimeNs = SystemClock.elapsedRealtimeNanos();
                    logger.mRequest = transitionRequestInfo;
                    ((TransitionPlayerRecord) this.mTransitionPlayers.getLast())
                            .mPlayer.requestStartTransition(
                                    transition.mToken, transitionRequestInfo);
                    if (remoteTransition != null) {
                        IApplicationThread appThread = remoteTransition.getAppThread();
                        TransitionController transitionController = transition.mController;
                        WindowProcessController processController =
                                transitionController.mAtm.getProcessController(appThread);
                        if (processController != null) {
                            transitionController.mRemotePlayer.update(
                                    processController, true, true);
                        }
                    }
                } catch (RemoteException e) {
                    Slog.e("TransitionController", "Error requesting transition", e);
                    transition.start();
                }
                return transition;
            }
        }
        if (transition.isCollecting()) {
            transition.abort();
        }
        return transition;
    }

    public final Transition requestTransitionIfNeeded(
            int i, int i2, WindowContainer windowContainer, WindowContainer windowContainer2) {
        if (this.mTransitionPlayers.isEmpty()) {
            return null;
        }
        if (!isCollecting()) {
            return requestStartTransition(
                    createTransition(i, i2),
                    windowContainer != null ? windowContainer.asTask() : null,
                    null,
                    null);
        }
        this.mCollectingTransition.setReady(windowContainer2, false);
        int i3 = 276736 & i2;
        if (i3 == 0) {
            return null;
        }
        this.mCollectingTransition.addFlag(i3);
        return null;
    }

    public final void setHideWhileTwoHandDragging(WindowContainer windowContainer) {
        Transition.ChangeInfo changeInfo;
        Transition transition = this.mCollectingTransition;
        if (transition == null
                || (changeInfo = (Transition.ChangeInfo) transition.mChanges.get(windowContainer))
                        == null) {
            return;
        }
        changeInfo.mHideWhileTwoHandDragging = true;
        Slog.d("TransitionController", "setHideWhileTwoHandDragging: wc=" + windowContainer);
    }

    public final void setNoAnimation(WindowContainer windowContainer) {
        Transition transition = this.mCollectingTransition;
        if (transition == null) {
            return;
        }
        Transition.ChangeInfo changeInfo =
                (Transition.ChangeInfo) transition.mChanges.get(windowContainer);
        if (changeInfo == null) {
            throw new IllegalStateException("Can't set no-animation property of non-participant");
        }
        changeInfo.mFlags |= 8;
    }

    public final void setReady(WindowContainer windowContainer, boolean z) {
        Transition transition = this.mCollectingTransition;
        if (transition == null) {
            return;
        }
        transition.setReady(windowContainer, z);
    }

    public final void setResumedAffordance(WindowContainer windowContainer) {
        Transition.ChangeInfo changeInfo;
        Transition transition = this.mCollectingTransition;
        if (transition == null
                || (changeInfo = (Transition.ChangeInfo) transition.mChanges.get(windowContainer))
                        == null) {
            return;
        }
        changeInfo.mResumedAffordance = true;
    }

    public void setSyncEngine(BLASTSyncEngine bLASTSyncEngine) {
        this.mSyncEngine = bLASTSyncEngine;
        bLASTSyncEngine.mOnIdleListeners.add(
                new TransitionController$$ExternalSyntheticLambda4(0, this));
    }

    public final void setTransientLaunch(final Task task, ActivityRecord activityRecord) {
        final Transition transition = this.mCollectingTransition;
        if (transition == null) {
            return;
        }
        if (transition.mTransientLaunches == null) {
            transition.mTransientLaunches = new ArrayMap();
            transition.mTransientHideTasks = new ArrayList();
        }
        transition.mTransientLaunches.put(activityRecord, task);
        for (WindowContainer windowContainer = activityRecord;
                windowContainer != null
                        && transition.mChanges.containsKey(windowContainer)
                        && (windowContainer.asTask() != null
                                || windowContainer.asActivityRecord() != null);
                windowContainer = windowContainer.getParent()) {
            ((Transition.ChangeInfo) transition.mChanges.get(windowContainer)).mFlags |= 2;
        }
        final Task rootTask = activityRecord.getRootTask();
        WindowContainer parent =
                task != null ? task.getParent() : rootTask != null ? rootTask.getParent() : null;
        if (parent != null) {
            parent.forAllTasks(
                    new Predicate() { // from class:
                        // com.android.server.wm.Transition$$ExternalSyntheticLambda15
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            Transition transition2 = Transition.this;
                            Task task2 = rootTask;
                            Task task3 = task;
                            Task task4 = (Task) obj;
                            transition2.getClass();
                            if (task4 == task2) {
                                return false;
                            }
                            if (task4.isVisibleRequested() && !task4.isAlwaysOnTop()) {
                                if (task4.isRootTask()) {
                                    if (CoreRune.FW_SHELL_TRANSITION_LOG
                                            && ProtoLogImpl_54989576.Cache
                                                    .WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
                                        ProtoLogImpl_54989576.v(
                                                ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS,
                                                7803104304874209275L,
                                                1,
                                                "Transition %d: Set %s as transient-hidden-task,"
                                                    + " caller=%s",
                                                Long.valueOf(transition2.mSyncId),
                                                String.valueOf(task4),
                                                String.valueOf(Debug.getCallers(3)));
                                    }
                                    transition2.mTransientHideTasks.add(task4);
                                }
                                if (task4.isLeafTask()) {
                                    transition2.collect(task4, false);
                                }
                            }
                            if (task3 != null) {
                                if (task4 != task3) {
                                    return false;
                                }
                            } else if (!task4.isRootTask() || !task4.fillsParent()) {
                                return false;
                            }
                            return true;
                        }
                    });
            for (int size = transition.mChanges.size() - 1; size >= 0; size--) {
                transition.updateTransientFlags(
                        (Transition.ChangeInfo) transition.mChanges.valueAt(size));
            }
        }
        if (CoreRune.FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY
                && activityRecord.getDisplayContent() != null) {
            ArrayList arrayList = activityRecord.getDisplayContent().mTransientLaunchOverlayTokens;
            for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                ((TransientLaunchOverlayToken) arrayList.get(size2)).setVisibility(true);
            }
        }
        if (CoreRune.FW_SHELL_TRANSITION_LOG) {
            if (ProtoLogImpl_54989576.Cache.WM_FORCE_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_FORCE_DEBUG_WINDOW_TRANSITIONS,
                        -2959015646734633259L,
                        1,
                        "Transition %d: Set %s as transient-launch, caller=%s",
                        Long.valueOf(transition.mSyncId),
                        String.valueOf(activityRecord),
                        String.valueOf(Debug.getCallers(3)));
            }
        } else if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS,
                    -8676279589273455859L,
                    1,
                    null,
                    Long.valueOf(transition.mSyncId),
                    String.valueOf(activityRecord));
        }
        if (activityRecord.isActivityTypeHomeOrRecents()) {
            this.mCollectingTransition.addFlag(128);
            activityRecord.task.mCanAffectSystemUiFlags = false;
        }
        if (inTransition()) {
            return;
        }
        for (int i = 0; i < this.mCleanUpRunnableList.size(); i++) {
            ((Runnable) this.mCleanUpRunnableList.get(i)).run();
        }
        this.mCleanUpRunnableList.clear();
    }

    public final boolean shouldKeepFocus(WindowContainer windowContainer) {
        if (this.mCollectingTransition != null) {
            if (this.mPlayingTransitions.isEmpty()) {
                return this.mCollectingTransition.isInTransientHide(windowContainer);
            }
            return false;
        }
        if (this.mPlayingTransitions.size() == 1) {
            return ((Transition) this.mPlayingTransitions.get(0))
                    .isInTransientHide(windowContainer);
        }
        return false;
    }

    public final void startCollectOrQueue(Transition transition, OnStartCollect onStartCollect) {
        if (!this.mQueuedTransitions.isEmpty()) {
            queueTransition(transition, onStartCollect);
            return;
        }
        if (!this.mSyncEngine.hasActiveSync()) {
            moveToCollecting(transition);
            onStartCollect.onCollectStarted(false);
            return;
        }
        if (!isCollecting()) {
            Slog.w("TransitionController", "Ongoing Sync outside of transition.");
        } else if (canStartCollectingNow(transition)) {
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN,
                        -1611886029896664304L,
                        1,
                        "Moving #%d from collecting to waiting.",
                        Long.valueOf(this.mCollectingTransition.mSyncId));
            }
            this.mWaitingTransitions.add(this.mCollectingTransition);
            this.mCollectingTransition = null;
            moveToCollecting(transition);
            onStartCollect.onCollectStarted(false);
            return;
        }
        queueTransition(transition, onStartCollect);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.wm.TransitionController$$ExternalSyntheticLambda6] */
    public final void startLegacySyncOrQueue(
            BLASTSyncEngine.SyncGroup syncGroup,
            final WindowOrganizerController$$ExternalSyntheticLambda1
                    windowOrganizerController$$ExternalSyntheticLambda1) {
        if (this.mQueuedTransitions.isEmpty() && !this.mSyncEngine.hasActiveSync()) {
            this.mSyncEngine.startSyncSet(syncGroup, 5000L, false);
            windowOrganizerController$$ExternalSyntheticLambda1.accept(Boolean.FALSE);
            return;
        }
        this.mQueuedTransitions.add(
                new QueuedTransition(
                        syncGroup,
                        (TransitionController$$ExternalSyntheticLambda6)
                                new OnStartCollect() { // from class:
                                                       // com.android.server.wm.TransitionController$$ExternalSyntheticLambda6
                                    @Override // com.android.server.wm.TransitionController.OnStartCollect
                                    public final void onCollectStarted(boolean z) {
                                        windowOrganizerController$$ExternalSyntheticLambda1.accept(
                                                Boolean.TRUE);
                                    }
                                }));
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN,
                    -2741593375634604522L,
                    0,
                    "Queueing legacy sync-set: %s",
                    String.valueOf(syncGroup.mSyncId));
        }
    }

    public final void tryStartCollectFromQueue() {
        if (this.mQueuedTransitions.isEmpty()) {
            return;
        }
        QueuedTransition queuedTransition = (QueuedTransition) this.mQueuedTransitions.get(0);
        if (this.mCollectingTransition != null) {
            Transition transition = queuedTransition.mTransition;
            if (transition == null || !canStartCollectingNow(transition)) {
                return;
            }
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN,
                        -1611886029896664304L,
                        1,
                        "Moving #%d from collecting to waiting.",
                        Long.valueOf(this.mCollectingTransition.mSyncId));
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
            this.mSyncEngine.startSyncSet(queuedTransition.mLegacySync, 5000L, false);
        }
        Transition transition3 = queuedTransition.mTransition;
        if (transition3 == null || transition3.mType != 12) {
            this.mAtm.mH.post(
                    new TransitionController$$ExternalSyntheticLambda0(this, queuedTransition, 1));
        } else {
            queuedTransition.mOnStartCollect.onCollectStarted(true);
        }
    }

    public void unregisterTransitionPlayer(ITransitionPlayer iTransitionPlayer) {
        int size = this.mTransitionPlayers.size() - 1;
        while (size >= 0
                && ((TransitionPlayerRecord) this.mTransitionPlayers.get(size)).mPlayer.asBinder()
                        != iTransitionPlayer.asBinder()) {
            size--;
        }
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_TRANSITIONS_MIN_enabled;
        if (size < 0) {
            if (zArr[3]) {
                ProtoLogImpl_54989576.w(
                        ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN,
                        3242771541905259983L,
                        0,
                        "Attempt to unregister transition player %s but it isn't registered",
                        String.valueOf(iTransitionPlayer.asBinder()));
                return;
            }
            return;
        }
        boolean z = size == this.mTransitionPlayers.size() - 1;
        TransitionPlayerRecord transitionPlayerRecord =
                (TransitionPlayerRecord) this.mTransitionPlayers.remove(size);
        if (z) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN,
                        3691912781236221027L,
                        20,
                        "Unregistering active transition player %s at index=%d leaving %d in stack",
                        String.valueOf(iTransitionPlayer.asBinder()),
                        Long.valueOf(size),
                        Long.valueOf(this.mTransitionPlayers.size()));
            }
        } else if (zArr[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN,
                    -2879980134100946679L,
                    20,
                    "Unregistering transition player %s at index=%d leaving %d in stack",
                    String.valueOf(iTransitionPlayer.asBinder()),
                    Long.valueOf(size),
                    Long.valueOf(this.mTransitionPlayers.size()));
        }
        if (transitionPlayerRecord.mPlayer.asBinder() != null
                && transitionPlayerRecord.mDeath != null) {
            transitionPlayerRecord
                    .mPlayer
                    .asBinder()
                    .unlinkToDeath(transitionPlayerRecord.mDeath, 0);
            transitionPlayerRecord.mDeath = null;
        }
        if (z) {
            flushRunningTransitions();
        }
    }

    public final void updateAnimatingState() {
        Transition transition;
        boolean z =
                !this.mPlayingTransitions.isEmpty()
                        || ((transition = this.mCollectingTransition) != null
                                && transition.mState == 1);
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        if (!z || this.mAnimatingState) {
            if (z || !this.mAnimatingState) {
                return;
            }
            for (int childCount =
                            activityTaskManagerService.mRootWindowContainer.getChildCount() - 1;
                    childCount >= 0;
                    childCount--) {
                ((DisplayContent)
                                activityTaskManagerService.mRootWindowContainer.getChildAt(
                                        childCount))
                        .enableHighPerfTransition(false);
            }
            activityTaskManagerService.mWindowManager.scheduleAnimationLocked();
            this.mSnapshotController.setPause(false);
            this.mAnimatingState = false;
            Trace.asyncTraceForTrackEnd(32L, "Transition", 68942577);
            return;
        }
        for (int childCount2 = activityTaskManagerService.mRootWindowContainer.getChildCount() - 1;
                childCount2 >= 0;
                childCount2--) {
            DisplayContent displayContent =
                    (DisplayContent)
                            activityTaskManagerService.mRootWindowContainer.getChildAt(childCount2);
            Transition transition2 = this.mCollectingTransition;
            if (transition2 == null || !transition2.shouldUsePerfHint(displayContent)) {
                int size = this.mPlayingTransitions.size() - 1;
                while (true) {
                    if (size < 0) {
                        break;
                    }
                    if (((Transition) this.mPlayingTransitions.get(size))
                            .shouldUsePerfHint(displayContent)) {
                        displayContent.enableHighPerfTransition(true);
                        break;
                    }
                    size--;
                }
            } else {
                displayContent.enableHighPerfTransition(true);
            }
        }
        this.mSnapshotController.setPause(true);
        this.mAnimatingState = true;
        Trace.asyncTraceForTrackBegin(32L, "Transition", "animating", 68942577);
    }

    public final void updateRunningRemoteAnimation(boolean z) {
        TransitionPlayerRecord transitionPlayerRecord;
        WindowProcessController windowProcessController;
        if (this.mTransitionPlayers.isEmpty()
                || (windowProcessController =
                                (transitionPlayerRecord =
                                                (TransitionPlayerRecord)
                                                        this.mTransitionPlayers.getLast())
                                        .mPlayerProc)
                        == null) {
            return;
        }
        boolean z2 = true;
        if (!z) {
            if (this.mPlayingTransitions.isEmpty()) {
                transitionPlayerRecord.mPlayerProc.removeAnimatingReason(1);
                this.mRemotePlayer.clear();
                return;
            }
            return;
        }
        int i = windowProcessController.mAnimatingReasons;
        windowProcessController.mAnimatingReasons = i | 1;
        if (i == 0) {
            windowProcessController.mAtm.mH.post(
                    new WindowProcessController$$ExternalSyntheticLambda1(
                            windowProcessController, z2));
        }
    }

    public final boolean useShellTransitionsRotation() {
        return isShellTransitionsEnabled() && SHELL_TRANSITIONS_ROTATION;
    }

    public final void validateStates() {
        for (int i = 0; i < this.mStateValidators.size(); i++) {
            ((Runnable) this.mStateValidators.get(i)).run();
            if (inTransition()) {
                this.mStateValidators.subList(0, i + 1).clear();
                return;
            }
        }
        this.mStateValidators.clear();
        for (int i2 = 0; i2 < this.mValidateCommitVis.size(); i2++) {
            ActivityRecord activityRecord = (ActivityRecord) this.mValidateCommitVis.get(i2);
            if (!activityRecord.isVisibleRequested() && activityRecord.mVisible) {
                Slog.e("TransitionController", "Uncommitted visibility change: " + activityRecord);
                activityRecord.commitVisibility(activityRecord.isVisibleRequested(), false, false);
            }
        }
        this.mValidateCommitVis.clear();
        for (int i3 = 0; i3 < this.mValidateActivityCompat.size(); i3++) {
            ActivityRecord activityRecord2 = (ActivityRecord) this.mValidateActivityCompat.get(i3);
            if (activityRecord2.mSurfaceControl != null) {
                activityRecord2.getRelativePosition(new Point());
                activityRecord2
                        .getSyncTransaction()
                        .setPosition(activityRecord2.mSurfaceControl, r4.x, r4.y);
            }
        }
        this.mValidateActivityCompat.clear();
        for (int i4 = 0; i4 < this.mValidateDisplayVis.size(); i4++) {
            DisplayArea displayArea = (DisplayArea) this.mValidateDisplayVis.get(i4);
            if (displayArea.isAttached()
                    && displayArea.getSurfaceControl() != null
                    && displayArea.isVisibleRequested()) {
                Slog.e(
                        "TransitionController",
                        "DisplayArea became visible outside of a transition: " + displayArea);
                displayArea.getSyncTransaction().show(displayArea.getSurfaceControl());
            }
        }
        this.mValidateDisplayVis.clear();
        for (int i5 = 0; i5 < this.mCleanUpRunnableList.size(); i5++) {
            ((Runnable) this.mCleanUpRunnableList.get(i5)).run();
        }
        this.mCleanUpRunnableList.clear();
    }

    public final void waitFor(Transition.ReadyCondition readyCondition) {
        Transition transition = this.mCollectingTransition;
        if (transition != null) {
            transition.mReadyTracker.add(readyCondition);
            return;
        }
        Slog.e(
                "TransitionController",
                "No collecting transition available to wait for " + readyCondition);
        readyCondition.mTracker = Transition.ReadyTracker.NULL_TRACKER;
    }
}
