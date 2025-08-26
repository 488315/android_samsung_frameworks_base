package com.android.wm.shell.transition;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.os.IBinder;
import android.util.Log;
import android.util.Pair;
import android.view.SurfaceControl;
import android.window.DesktopModeFlags;
import android.window.RemoteTransition;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.wm.shell.activityembedding.ActivityEmbeddingController;
import com.android.wm.shell.common.ComponentUtils;
import com.android.wm.shell.common.pip.PipUtils;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.keyguard.KeyguardTransitionHandler;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.RecentsTransitionHandler;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda17;
import com.android.wm.shell.splitscreen.StageTaskListener;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.taskview.TaskViewTransitions;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.unfold.UnfoldTransitionHandler;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Optional;

/* loaded from: classes3.dex */
public class DefaultMixedHandler implements MixedTransitionHandler, Transitions.TransitionHandler {
    public final ArrayList mActiveTransitions = new ArrayList();
    public ActivityEmbeddingController mActivityEmbeddingController;
    public DesktopTasksController mDesktopTasksController;
    public final KeyguardTransitionHandler mKeyguardHandler;
    public PipTransitionController mPipHandler;
    public final Transitions mPlayer;
    public RecentsTransitionHandler mRecentsHandler;
    public StageCoordinator mSplitHandler;
    public TaskViewTransitions mTaskViewTransitions;
    public UnfoldTransitionHandler mUnfoldHandler;

    public abstract class MixedTransition {
        public boolean mClosingSplitScreenWithEnterPip;
        public boolean mHasRequestToRemote;
        public final KeyguardTransitionHandler mKeyguardHandler;
        public final MixedTransitionHandler mMixedHandler;
        public final PipTransitionController mPipHandler;
        public final Transitions mPlayer;
        public final StageCoordinator mSplitHandler;
        public TaskViewTransitions mTaskViewTransitions;
        public final IBinder mTransition;
        public final int mType;
        public int mAnimType = 0;
        public Transitions.TransitionHandler mLeftoversHandler = null;
        public TransitionInfo mInfo = null;
        public WindowContainerTransaction mFinishWCT = null;
        public SurfaceControl.Transaction mFinishT = null;
        public Transitions.TransitionFinishCallback mFinishCB = null;
        public int mInFlightSubAnimations = 0;

        public MixedTransition(int i, IBinder iBinder, Transitions transitions, MixedTransitionHandler mixedTransitionHandler, PipTransitionController pipTransitionController, StageCoordinator stageCoordinator, KeyguardTransitionHandler keyguardTransitionHandler) {
            this.mType = i;
            this.mTransition = iBinder;
            this.mPlayer = transitions;
            this.mMixedHandler = mixedTransitionHandler;
            this.mPipHandler = pipTransitionController;
            this.mSplitHandler = stageCoordinator;
            this.mKeyguardHandler = keyguardTransitionHandler;
        }

        public final void joinFinishArgs(WindowContainerTransaction windowContainerTransaction) {
            if (windowContainerTransaction != null) {
                WindowContainerTransaction windowContainerTransaction2 = this.mFinishWCT;
                if (windowContainerTransaction2 == null) {
                    this.mFinishWCT = windowContainerTransaction;
                } else {
                    windowContainerTransaction2.merge(windowContainerTransaction, true);
                }
            }
        }

        public abstract void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback);

        public abstract void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction);

        public abstract boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback);

        public final boolean startSubAnimation(Transitions.TransitionHandler transitionHandler, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
            if (this.mInfo != null && ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 2342603963533375943L, 5, Long.valueOf(r0.getDebugId()), Long.valueOf(transitionInfo.getDebugId()));
            }
            this.mInFlightSubAnimations++;
            if (transitionHandler.startAnimation(this.mTransition, transitionInfo, transaction, transaction2, new DefaultMixedHandler$$ExternalSyntheticLambda5(2, this, transitionInfo))) {
                return true;
            }
            this.mInFlightSubAnimations--;
            return false;
        }
    }

    public DefaultMixedHandler(ShellInit shellInit, Transitions transitions, final Optional<SplitScreenController> optional, final PipTransitionController pipTransitionController, final Optional<RecentsTransitionHandler> optional2, KeyguardTransitionHandler keyguardTransitionHandler, final Optional<DesktopTasksController> optional3, final Optional<UnfoldTransitionHandler> optional4, final Optional<ActivityEmbeddingController> optional5, final TaskViewTransitions taskViewTransitions) {
        this.mPlayer = transitions;
        this.mKeyguardHandler = keyguardTransitionHandler;
        if (Transitions.ENABLE_SHELL_TRANSITIONS && pipTransitionController != null && optional.isPresent()) {
            shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.transition.DefaultMixedHandler$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DefaultMixedHandler defaultMixedHandler = this.f$0;
                    PipTransitionController pipTransitionController2 = pipTransitionController;
                    Optional optional6 = optional;
                    Optional optional7 = optional2;
                    Optional optional8 = optional3;
                    Optional optional9 = optional4;
                    Optional optional10 = optional5;
                    TaskViewTransitions taskViewTransitions2 = taskViewTransitions;
                    defaultMixedHandler.mPipHandler = pipTransitionController2;
                    pipTransitionController2.mMixedHandler = defaultMixedHandler;
                    defaultMixedHandler.mSplitHandler = ((SplitScreenController) optional6.get()).getTransitionHandler();
                    defaultMixedHandler.mPlayer.addHandler(defaultMixedHandler);
                    StageCoordinator stageCoordinator = defaultMixedHandler.mSplitHandler;
                    if (stageCoordinator != null) {
                        stageCoordinator.mMixedHandler = defaultMixedHandler;
                    }
                    RecentsTransitionHandler recentsTransitionHandler = (RecentsTransitionHandler) optional7.orElse(null);
                    defaultMixedHandler.mRecentsHandler = recentsTransitionHandler;
                    if (recentsTransitionHandler != null) {
                        recentsTransitionHandler.mMixers.add(defaultMixedHandler);
                    }
                    defaultMixedHandler.mDesktopTasksController = (DesktopTasksController) optional8.orElse(null);
                    defaultMixedHandler.mUnfoldHandler = (UnfoldTransitionHandler) optional9.orElse(null);
                    defaultMixedHandler.mActivityEmbeddingController = (ActivityEmbeddingController) optional10.orElse(null);
                    defaultMixedHandler.mTaskViewTransitions = taskViewTransitions2;
                    if (taskViewTransitions2 != null) {
                        taskViewTransitions2.mMixedHandler = defaultMixedHandler;
                    }
                }
            }, this);
        }
    }

    public static void excludeForceHidingChanges(TransitionInfo transitionInfo) {
        for (int iM = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); iM >= 0; iM--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(iM);
            if (change.isForceHidingEnter()) {
                Log.d("DefaultMixedHandler", "excludeForceHidingChanges: " + change + ", reason=animateKeyguard");
                transitionInfo.getChanges().remove(change);
            }
        }
    }

    public static TransitionInfo subCopy(TransitionInfo transitionInfo, int i, boolean z) {
        TransitionInfo transitionInfo2 = new TransitionInfo(i, z ? transitionInfo.getFlags() : 0);
        transitionInfo2.setTrack(transitionInfo.getTrack());
        transitionInfo2.setDebugId(transitionInfo.getDebugId());
        if (z) {
            for (int i2 = 0; i2 < transitionInfo.getChanges().size(); i2++) {
                transitionInfo2.getChanges().add((TransitionInfo.Change) transitionInfo.getChanges().get(i2));
            }
        }
        for (int i3 = 0; i3 < transitionInfo.getRootCount(); i3++) {
            transitionInfo2.addRoot(transitionInfo.getRoot(i3));
        }
        return transitionInfo2;
    }

    public final boolean animatePendingEnterPipFromSplit(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback, boolean z, boolean z2) {
        DefaultMixedTransition defaultMixedTransitionCreateDefaultMixedTransition = createDefaultMixedTransition(iBinder, z ? 10 : 1);
        this.mActiveTransitions.add(defaultMixedTransitionCreateDefaultMixedTransition);
        DefaultMixedHandler$$ExternalSyntheticLambda4 defaultMixedHandler$$ExternalSyntheticLambda4 = new DefaultMixedHandler$$ExternalSyntheticLambda4(this, defaultMixedTransitionCreateDefaultMixedTransition, transitionFinishCallback, 3);
        if (CoreRune.MW_PIP_SHELL_TRANSITION && z2 && !z) {
            defaultMixedTransitionCreateDefaultMixedTransition.mClosingSplitScreenWithEnterPip = true;
        }
        return defaultMixedTransitionCreateDefaultMixedTransition.startAnimation(iBinder, transitionInfo, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda4);
    }

    public final boolean animatePendingSplitWithDisplayChange(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        boolean z;
        TransitionInfo transitionInfoSubCopy = subCopy(transitionInfo, transitionInfo.getType(), true);
        TransitionInfo transitionInfoSubCopy2 = subCopy(transitionInfo, 6, false);
        for (int iM = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); iM >= 0; iM--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(iM);
            TransitionInfo.Change change2 = change;
            while (change2 != null) {
                if (change2.getTaskInfo() == null) {
                    if (change2.getParent() == null) {
                        break;
                    }
                    change2 = transitionInfo.getChange(change2.getParent());
                }
            }
            transitionInfoSubCopy2.addChange(change);
            transitionInfoSubCopy.getChanges().remove(iM);
        }
        if (transitionInfoSubCopy2.getChanges().isEmpty()) {
            return false;
        }
        for (int i = 0; i < transitionInfoSubCopy.getChanges().size(); i++) {
            TransitionInfo.Change change3 = (TransitionInfo.Change) transitionInfoSubCopy.getChanges().get(i);
            if (change3.getParent() != null && transitionInfoSubCopy.getChange(change3.getParent()) == null) {
                ((TransitionInfo.Change) transitionInfoSubCopy.getChanges().get(i)).setParent((WindowContainerToken) null);
            }
        }
        if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION) {
            if (transitionInfoSubCopy2.hasCustomDisplayChangeTransition()) {
                transitionInfoSubCopy.setSeparatedFromCustomDisplayChange(true);
            }
            transitionInfoSubCopy2.setAnimatePendingSplitWithDisplayChange(true);
        }
        DefaultMixedTransition defaultMixedTransitionCreateDefaultMixedTransition = createDefaultMixedTransition(iBinder, 2);
        this.mActiveTransitions.add(defaultMixedTransitionCreateDefaultMixedTransition);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -3728697233680287407L, 0, null);
        }
        defaultMixedTransitionCreateDefaultMixedTransition.mInFlightSubAnimations = 2;
        if (CoreRune.MW_PIP_SHELL_TRANSITION) {
            for (int iM2 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); iM2 >= 0; iM2--) {
                if (this.mPipHandler.isEnteringPip$1((TransitionInfo.Change) transitionInfo.getChanges().get(iM2), transitionInfo.getType())) {
                    defaultMixedTransitionCreateDefaultMixedTransition.mInFlightSubAnimations++;
                    z = true;
                    break;
                }
            }
            z = false;
        } else {
            z = false;
        }
        if (this.mSplitHandler.mSplitTransitions.isPendingDismiss(iBinder)) {
            for (int iM3 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfoSubCopy, 1); iM3 >= 0; iM3--) {
                TransitionInfo.Change change4 = (TransitionInfo.Change) transitionInfo.getChanges().get(iM3);
                SurfaceControl leash = change4.getLeash();
                if (leash != null && change4.getMode() == 4) {
                    transaction.hide(leash);
                }
            }
        }
        DefaultMixedHandler$$ExternalSyntheticLambda4 defaultMixedHandler$$ExternalSyntheticLambda4 = new DefaultMixedHandler$$ExternalSyntheticLambda4(this, defaultMixedTransitionCreateDefaultMixedTransition, transitionFinishCallback, 1);
        defaultMixedTransitionCreateDefaultMixedTransition.mLeftoversHandler = this.mPlayer.dispatchTransition(defaultMixedTransitionCreateDefaultMixedTransition.mTransition, transitionInfoSubCopy2, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda4, this.mSplitHandler, this.mPipHandler);
        if (!CoreRune.MW_SHELL_TRANSITION_BUG_FIX) {
            this.mSplitHandler.startPendingAnimation(iBinder, transitionInfoSubCopy, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda4);
        } else if (!this.mSplitHandler.startPendingAnimation(iBinder, transitionInfoSubCopy, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda4)) {
            defaultMixedHandler$$ExternalSyntheticLambda4.onTransitionFinished(null);
        }
        if (CoreRune.MW_PIP_SHELL_TRANSITION && z) {
            this.mPipHandler.startAnimation(iBinder, transitionInfoSubCopy, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda4);
        }
        return true;
    }

    public final DefaultMixedTransition createDefaultMixedTransition(IBinder iBinder, int i) {
        return new DefaultMixedTransition(i, iBinder, this.mPlayer, this, this.mPipHandler, this.mSplitHandler, this.mKeyguardHandler, this.mUnfoldHandler, this.mActivityEmbeddingController, this.mDesktopTasksController);
    }

    public final RecentsMixedTransition createRecentsMixedTransition(IBinder iBinder, int i) {
        return new RecentsMixedTransition(i, iBinder, this.mPlayer, this, this.mPipHandler, this.mSplitHandler, this.mKeyguardHandler, this.mRecentsHandler, this.mDesktopTasksController);
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0086, code lost:
    
        if (r12.getChildCount() == 0) goto L34;
     */
    /* JADX WARN: Removed duplicated region for block: B:114:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x030f  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x035b  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0396  */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        boolean z;
        ActivityManager.RunningTaskInfo triggerTask;
        boolean zShouldFullscreenTaskLaunchSwitchToDesktop;
        int i;
        int i2;
        boolean z2;
        StageCoordinator stageCoordinator = this.mSplitHandler;
        StageTaskListener stageTaskListener = stageCoordinator.mMainStage;
        if (stageTaskListener.mIsActive) {
            stageCoordinator.mMixedHandler.mPipHandler.getClass();
            if (transitionRequestInfo.getType() == 10) {
                int i3 = -1;
                if ((transitionRequestInfo.getTriggerTask() == null || stageCoordinator.getSplitPosition(transitionRequestInfo.getTriggerTask().taskId) == -1) && (!PipUtils.isPip2ExperimentEnabled() || transitionRequestInfo.getPipChange() == null || stageCoordinator.getSplitPosition(transitionRequestInfo.getPipChange().getTaskInfo().taskId) == -1)) {
                    boolean z3 = CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER;
                    StageTaskListener stageTaskListener2 = stageCoordinator.mCellStage;
                    if (z3) {
                        if (transitionRequestInfo.getTriggerTask() == null || transitionRequestInfo.getTriggerTask().taskId != stageTaskListener2.getTopVisibleChildTaskId()) {
                        }
                    }
                    if (stageTaskListener.getChildCount() != 0) {
                        if (stageCoordinator.mSideStage.getChildCount() != 0) {
                            if (z3) {
                                if (stageCoordinator.isMultiSplitActive()) {
                                }
                            }
                        }
                    }
                }
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -2686821093972033600L, 0, null);
                }
                if (transitionRequestInfo.getRemoteTransition() != null) {
                    throw new IllegalStateException("Unexpected remote transition inpip-enter-from-split request");
                }
                this.mActiveTransitions.add(createDefaultMixedTransition(iBinder, 1));
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                this.mPipHandler.augmentRequest(iBinder, transitionRequestInfo, windowContainerTransaction);
                StageCoordinator stageCoordinator2 = this.mSplitHandler;
                stageCoordinator2.getClass();
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 2903785196408028946L, 1, Long.valueOf(transitionRequestInfo.getDebugId()));
                }
                ActivityManager.RunningTaskInfo triggerTask2 = transitionRequestInfo.getTriggerTask();
                int i4 = stageCoordinator2.mDisplayId;
                if (triggerTask2 == null || triggerTask2.displayId == i4) {
                    int type = transitionRequestInfo.getType();
                    int stageOfTask = triggerTask2 != null ? stageCoordinator2.getStageOfTask(triggerTask2.taskId) : -1;
                    StageTaskListener stageTaskListener3 = stageCoordinator2.mMainStage;
                    if (stageTaskListener3.mIsActive && !TransitionUtil.isOpeningType(type)) {
                        int childCount = stageTaskListener3.getChildCount();
                        StageTaskListener stageTaskListener4 = stageCoordinator2.mCellStage;
                        StageTaskListener stageTaskListener5 = stageCoordinator2.mSideStage;
                        if (childCount == 0 || stageTaskListener5.getChildCount() == 0 || (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageCoordinator2.isMultiSplitActive() && stageTaskListener4.getChildCount() == 0)) {
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 5247503639166465764L, 5, Long.valueOf(stageTaskListener3.getChildCount()), Long.valueOf(stageTaskListener5.getChildCount()));
                            }
                            if (triggerTask2 != null) {
                                stageCoordinator2.mRecentTasks.ifPresent(new StageCoordinator$$ExternalSyntheticLambda17(triggerTask2, 1));
                                stageCoordinator2.logExit(9);
                            }
                            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageCoordinator2.isMultiSplitScreenVisible()) {
                                if (stageOfTask == -1) {
                                    if (stageTaskListener3.getChildCount() == 0) {
                                        stageOfTask = 0;
                                    } else if (stageTaskListener5.getChildCount() == 0) {
                                        stageOfTask = 1;
                                    } else if (stageTaskListener4.getChildCount() == 0) {
                                        stageOfTask = 5;
                                    }
                                }
                                StageTaskListener stageTaskListener6 = stageTaskListener4.mHost;
                                if (stageTaskListener6 == stageTaskListener3) {
                                    stageTaskListener3 = stageTaskListener5;
                                }
                                if (stageOfTask == 5) {
                                    stageCoordinator2.prepareExitMultiSplitScreen(windowContainerTransaction, false);
                                } else if (stageOfTask == stageCoordinator2.getCellHostStageType()) {
                                    stageCoordinator2.reparentCellToMainOrSide(windowContainerTransaction, stageTaskListener6, true);
                                } else {
                                    int cellHostStageType = stageCoordinator2.getCellHostStageType();
                                    if (stageOfTask == (cellHostStageType == 0 ? 1 : cellHostStageType == 1 ? 0 : -1)) {
                                        stageCoordinator2.reparentCellToMainOrSide(windowContainerTransaction, stageTaskListener3, true);
                                    }
                                }
                                windowContainerTransaction.setDisplayIdForChangeTransition(i4, "enter_pip_with_multi_split");
                                return windowContainerTransaction;
                            }
                            if (stageCoordinator2.isSplitScreenVisible()) {
                                if (stageTaskListener3.getChildCount() != 0 && stageTaskListener5.getChildCount() == 0) {
                                    i2 = 0;
                                    z2 = true;
                                    i3 = 0;
                                } else if (stageTaskListener5.getChildCount() != 0 && stageTaskListener3.getChildCount() == 0) {
                                    i2 = 0;
                                    z2 = true;
                                    i3 = 1;
                                }
                                stageCoordinator2.prepareExitSplitScreen(i3, i2, windowContainerTransaction, z2);
                            } else {
                                i2 = 0;
                                z2 = true;
                                stageCoordinator2.prepareExitSplitScreen(i3, i2, windowContainerTransaction, z2);
                            }
                        }
                    }
                }
                return windowContainerTransaction;
            }
        }
        if (transitionRequestInfo.getType() == 10 && (transitionRequestInfo.getFlags() & 512) != 0 && this.mActivityEmbeddingController != null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 8328820818454394116L, 0, null);
            }
            this.mActiveTransitions.add(createDefaultMixedTransition(iBinder, 9));
            WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
            this.mPipHandler.augmentRequest(iBinder, transitionRequestInfo, windowContainerTransaction2);
            return windowContainerTransaction2;
        }
        RemoteTransition remoteTransition = transitionRequestInfo.getRemoteTransition();
        Transitions transitions = this.mPlayer;
        if (remoteTransition != null && TransitionUtil.isOpeningType(transitionRequestInfo.getType()) && (transitionRequestInfo.getTriggerTask() == null || (transitionRequestInfo.getTriggerTask().topActivityType != 2 && transitionRequestInfo.getTriggerTask().topActivityType != 3))) {
            Pair pairDispatchRequest = transitions.dispatchRequest(iBinder, transitionRequestInfo, this);
            if (pairDispatchRequest != null) {
                DefaultMixedTransition defaultMixedTransitionCreateDefaultMixedTransition = createDefaultMixedTransition(iBinder, 3);
                defaultMixedTransitionCreateDefaultMixedTransition.mLeftoversHandler = (Transitions.TransitionHandler) pairDispatchRequest.first;
                this.mActiveTransitions.add(defaultMixedTransitionCreateDefaultMixedTransition);
                Transitions.TransitionHandler transitionHandler = defaultMixedTransitionCreateDefaultMixedTransition.mLeftoversHandler;
                RemoteTransitionHandler remoteTransitionHandler = transitions.mRemoteTransitionHandler;
                if (transitionHandler != remoteTransitionHandler) {
                    defaultMixedTransitionCreateDefaultMixedTransition.mHasRequestToRemote = true;
                    remoteTransitionHandler.handleRequest(iBinder, transitionRequestInfo);
                }
                return (WindowContainerTransaction) pairDispatchRequest.second;
            }
        } else {
            if (this.mSplitHandler.isSplitScreenVisible() && TransitionUtil.isOpeningType(transitionRequestInfo.getType()) && transitionRequestInfo.getTriggerTask() != null && transitionRequestInfo.getTriggerTask().getWindowingMode() == 1 && (transitionRequestInfo.getTriggerTask().getActivityType() == 2 || transitionRequestInfo.getTriggerTask().getActivityType() == 3)) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                    i = 0;
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 533703778315874542L, 0, null);
                } else {
                    i = 0;
                }
                Pair pairDispatchRequest2 = transitions.dispatchRequest(iBinder, transitionRequestInfo, this);
                if (pairDispatchRequest2 == null) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1432596578455044936L, i, null);
                    }
                    pairDispatchRequest2 = new Pair(transitions.mRemoteTransitionHandler, new WindowContainerTransaction());
                }
                RecentsMixedTransition recentsMixedTransitionCreateRecentsMixedTransition = createRecentsMixedTransition(iBinder, 4);
                recentsMixedTransitionCreateRecentsMixedTransition.mLeftoversHandler = (Transitions.TransitionHandler) pairDispatchRequest2.first;
                this.mActiveTransitions.add(recentsMixedTransitionCreateRecentsMixedTransition);
                return (WindowContainerTransaction) pairDispatchRequest2.second;
            }
            if (this.mUnfoldHandler != null && UnfoldTransitionHandler.shouldPlayUnfoldAnimation(transitionRequestInfo)) {
                WindowContainerTransaction windowContainerTransactionHandleRequest = this.mUnfoldHandler.handleRequest(iBinder, transitionRequestInfo);
                if (windowContainerTransactionHandleRequest != null) {
                    this.mActiveTransitions.add(createDefaultMixedTransition(iBinder, 8));
                }
                return windowContainerTransactionHandleRequest;
            }
            DesktopTasksController desktopTasksController = this.mDesktopTasksController;
            if (desktopTasksController == null) {
                if (CoreRune.MW_SPLIT_CONTINUITY_MODE && this.mSplitHandler.shouldkeyguardUnlockWithUpdateSplit(transitionRequestInfo.getFlags())) {
                    Log.d("DefaultMixedHandler", "keyguard going away with update split");
                    WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
                    StageCoordinator stageCoordinator3 = this.mSplitHandler;
                    if (stageCoordinator3.updateCoverDisplaySplitLayoutIfNeeded()) {
                        z = true;
                        stageCoordinator3.mUpdateCoverDisplaySplitLayout = true;
                        stageCoordinator3.mSplitLayout.update(null, true);
                        stageCoordinator3.mUpdateCoverDisplaySplitLayout = false;
                        stageCoordinator3.updateStagePositionIfNeeded(windowContainerTransaction3);
                        stageCoordinator3.updateWindowBounds(stageCoordinator3.mSplitLayout, windowContainerTransaction3, false);
                    } else {
                        z = false;
                    }
                    if (z) {
                        this.mActiveTransitions.add(createDefaultMixedTransition(iBinder, 102));
                        this.mKeyguardHandler.handleRequest(iBinder, transitionRequestInfo);
                        return windowContainerTransaction3;
                    }
                }
            } else if (DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue() && (triggerTask = transitionRequestInfo.getTriggerTask()) != null && desktopTasksController.isDesktopModeShowing(triggerTask.displayId) && TransitionUtil.isOpeningType(transitionRequestInfo.getType())) {
                DesktopRepository.Desk activeDesk = desktopTasksController.taskRepository.desktopData.getActiveDesk(triggerTask.displayId);
                if ((activeDesk != null ? activeDesk.fullImmersiveTaskId : null) == null) {
                    zShouldFullscreenTaskLaunchSwitchToDesktop = false;
                    if (!zShouldFullscreenTaskLaunchSwitchToDesktop) {
                        Pair pairDispatchRequest3 = transitions.dispatchRequest(iBinder, transitionRequestInfo, this);
                        if (pairDispatchRequest3 != null) {
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 7723280085640558664L, 0, String.valueOf(pairDispatchRequest3.first));
                            }
                            DefaultMixedTransition defaultMixedTransitionCreateDefaultMixedTransition2 = createDefaultMixedTransition(iBinder, 12);
                            defaultMixedTransitionCreateDefaultMixedTransition2.mLeftoversHandler = (Transitions.TransitionHandler) pairDispatchRequest3.first;
                            this.mActiveTransitions.add(defaultMixedTransitionCreateDefaultMixedTransition2);
                            return (WindowContainerTransaction) pairDispatchRequest3.second;
                        }
                    }
                } else {
                    if (triggerTask.getWindowingMode() == 1) {
                        zShouldFullscreenTaskLaunchSwitchToDesktop = desktopTasksController.shouldFullscreenTaskLaunchSwitchToDesktop(triggerTask);
                    } else if (triggerTask.isFreeform() && desktopTasksController.isDesktopModeShowing(triggerTask.displayId)) {
                        zShouldFullscreenTaskLaunchSwitchToDesktop = true;
                    }
                    if (!zShouldFullscreenTaskLaunchSwitchToDesktop) {
                    }
                }
            }
        }
        return null;
    }

    public final boolean isIntentInPip(PendingIntent pendingIntent) {
        PipTransitionController pipTransitionController = this.mPipHandler;
        if (pipTransitionController != null) {
            return pipTransitionController.isPackageActiveInPip(ComponentUtils.getPackageName(pendingIntent.getIntent()));
        }
        return false;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        IBinder iBinder3;
        TransitionInfo transitionInfo2;
        SurfaceControl.Transaction transaction3;
        SurfaceControl.Transaction transaction4;
        IBinder iBinder4;
        Transitions.TransitionFinishCallback transitionFinishCallback2;
        int i = 0;
        while (i < this.mActiveTransitions.size()) {
            if (((MixedTransition) this.mActiveTransitions.get(i)).mTransition != iBinder2) {
                iBinder3 = iBinder;
                transitionInfo2 = transitionInfo;
                transaction3 = transaction;
                transaction4 = transaction2;
                iBinder4 = iBinder2;
                transitionFinishCallback2 = transitionFinishCallback;
            } else {
                MixedTransition mixedTransition = (MixedTransition) this.mActiveTransitions.get(i);
                if (mixedTransition.mInFlightSubAnimations <= 0) {
                    return;
                }
                iBinder3 = iBinder;
                transitionInfo2 = transitionInfo;
                transaction3 = transaction;
                transaction4 = transaction2;
                iBinder4 = iBinder2;
                transitionFinishCallback2 = transitionFinishCallback;
                mixedTransition.mergeAnimation(iBinder3, transitionInfo2, transaction3, transaction4, iBinder4, transitionFinishCallback2);
            }
            i++;
            iBinder = iBinder3;
            transitionInfo = transitionInfo2;
            transaction = transaction3;
            transaction2 = transaction4;
            iBinder2 = iBinder4;
            transitionFinishCallback = transitionFinishCallback2;
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        MixedTransition mixedTransition;
        int size = this.mActiveTransitions.size() - 1;
        while (true) {
            if (size < 0) {
                mixedTransition = null;
                break;
            } else {
                if (((MixedTransition) this.mActiveTransitions.get(size)).mTransition == iBinder) {
                    mixedTransition = (MixedTransition) this.mActiveTransitions.remove(size);
                    break;
                }
                size--;
            }
        }
        if (mixedTransition != null) {
            mixedTransition.onTransitionConsumed(iBinder, z, transaction);
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        TransitionInfo.Change change;
        MixedTransition mixedTransition;
        int i;
        int size = this.mActiveTransitions.size() - 1;
        while (true) {
            change = null;
            if (size < 0) {
                mixedTransition = null;
                break;
            }
            if (((MixedTransition) this.mActiveTransitions.get(size)).mTransition == iBinder) {
                mixedTransition = (MixedTransition) this.mActiveTransitions.get(size);
                break;
            }
            size--;
        }
        if (KeyguardTransitionHandler.handles(transitionInfo)) {
            if (mixedTransition == null || (i = mixedTransition.mType) == 5) {
                PipTransitionController pipTransitionController = this.mPipHandler;
                if (pipTransitionController != null) {
                    pipTransitionController.syncPipSurfaceState(transitionInfo, transaction, transaction2);
                }
            } else {
                DefaultMixedTransition defaultMixedTransitionCreateDefaultMixedTransition = createDefaultMixedTransition(iBinder, 5);
                this.mActiveTransitions.add(defaultMixedTransitionCreateDefaultMixedTransition);
                DefaultMixedHandler$$ExternalSyntheticLambda4 defaultMixedHandler$$ExternalSyntheticLambda4 = new DefaultMixedHandler$$ExternalSyntheticLambda4(this, defaultMixedTransitionCreateDefaultMixedTransition, transitionFinishCallback, 6);
                if (CoreRune.MW_SPLIT_CONTINUITY_MODE && i == 102 && this.mSplitHandler.shouldkeyguardUnlockWithUpdateSplit(transitionInfo.getFlags())) {
                    Log.d("DefaultMixedHandler", "update split surface before going away");
                    StageCoordinator stageCoordinator = this.mSplitHandler;
                    stageCoordinator.updateSurfaceBounds(stageCoordinator.mSplitLayout, transaction, false);
                }
                PipTransitionController pipTransitionController2 = this.mPipHandler;
                if (defaultMixedTransitionCreateDefaultMixedTransition.mFinishT == null) {
                    defaultMixedTransitionCreateDefaultMixedTransition.mFinishT = transaction2;
                    defaultMixedTransitionCreateDefaultMixedTransition.mFinishCB = defaultMixedHandler$$ExternalSyntheticLambda4;
                }
                if (pipTransitionController2 != null) {
                    pipTransitionController2.syncPipSurfaceState(transitionInfo, transaction, transaction2);
                }
                if (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION) {
                    excludeForceHidingChanges(transitionInfo);
                }
                if (defaultMixedTransitionCreateDefaultMixedTransition.startSubAnimation(this.mKeyguardHandler, transitionInfo, transaction, transaction2)) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[3]) {
                        ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 2769322071729320114L, 0, null);
                    }
                    this.mActiveTransitions.remove(mixedTransition);
                    mixedTransition.onTransitionConsumed(iBinder, false, null);
                    if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && i == 8 && this.mSplitHandler.isSplitScreenVisible()) {
                        StageCoordinator stageCoordinator2 = this.mSplitHandler;
                        stageCoordinator2.updateSurfaceBounds(stageCoordinator2.mSplitLayout, transaction2, false);
                        stageCoordinator2.mSplitLayout.update(transaction2, true);
                    }
                    return true;
                }
                this.mActiveTransitions.remove(defaultMixedTransitionCreateDefaultMixedTransition);
            }
            if (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION) {
                excludeForceHidingChanges(transitionInfo);
            }
        }
        if (mixedTransition != null) {
            MixedTransition mixedTransition2 = mixedTransition;
            boolean zStartAnimation = mixedTransition2.startAnimation(iBinder, transitionInfo, transaction, transaction2, new DefaultMixedHandler$$ExternalSyntheticLambda4(this, mixedTransition, transitionFinishCallback, 7));
            if (!zStartAnimation) {
                this.mActiveTransitions.remove(mixedTransition2);
            }
            return zStartAnimation;
        }
        if (CoreRune.MW_PIP_SHELL_TRANSITION && CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
            boolean z = false;
            boolean z2 = false;
            for (int iM = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); iM >= 0; iM--) {
                TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(iM);
                if (change2.isEnteringPinnedMode() && change2.getTaskInfo() != null && change2.getTaskInfo().getWindowingMode() == 2) {
                    z2 = true;
                }
                if (change2.getMinimizeAnimState() != 0) {
                    z = true;
                }
            }
            if (z && z2) {
                int iM2 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1);
                while (true) {
                    if (iM2 < 0) {
                        break;
                    }
                    TransitionInfo.Change change3 = (TransitionInfo.Change) transitionInfo.getChanges().get(iM2);
                    if (change3.isEnteringPinnedMode() && change3.getTaskInfo() != null && change3.getTaskInfo().getWindowingMode() == 2) {
                        change = change3;
                        break;
                    }
                    iM2--;
                }
                if (change == null) {
                    Log.w("DefaultMixedHandler", "animateEnterPipWithDefaultTransition: failed, cannot find pipChange");
                    return false;
                }
                TransitionInfo transitionInfoSubCopy = subCopy(transitionInfo, transitionInfo.getType(), true);
                TransitionInfo transitionInfoSubCopy2 = subCopy(transitionInfo, transitionInfo.getType(), false);
                transitionInfoSubCopy.getChanges().remove(change);
                transitionInfoSubCopy2.addChange(change);
                if (transitionInfoSubCopy.getChanges().isEmpty()) {
                    Log.w("DefaultMixedHandler", "animateEnterPipWithDefaultTransition: failed, default part is empty");
                    return false;
                }
                if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION && transitionInfoSubCopy.hasCustomDisplayChangeTransition()) {
                    transitionInfoSubCopy.setSeparatedFromCustomDisplayChange(true);
                }
                DefaultMixedTransition defaultMixedTransitionCreateDefaultMixedTransition2 = createDefaultMixedTransition(iBinder, 100);
                this.mActiveTransitions.add(defaultMixedTransitionCreateDefaultMixedTransition2);
                defaultMixedTransitionCreateDefaultMixedTransition2.mInFlightSubAnimations = 2;
                Log.d("DefaultMixedHandler", "animateEnterPipWithDefaultTransition: enterPipPart=" + transitionInfoSubCopy2 + ", defaultPart=" + transitionInfoSubCopy);
                DefaultMixedHandler$$ExternalSyntheticLambda4 defaultMixedHandler$$ExternalSyntheticLambda42 = new DefaultMixedHandler$$ExternalSyntheticLambda4(this, defaultMixedTransitionCreateDefaultMixedTransition2, transitionFinishCallback, 2);
                defaultMixedTransitionCreateDefaultMixedTransition2.mLeftoversHandler = this.mPlayer.dispatchTransition(defaultMixedTransitionCreateDefaultMixedTransition2.mTransition, transitionInfoSubCopy, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda42, this.mPipHandler, this);
                this.mPipHandler.startAnimation(iBinder, transitionInfoSubCopy2, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda42);
                return true;
            }
        }
        return false;
    }
}
