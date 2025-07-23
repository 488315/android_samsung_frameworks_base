package com.android.wm.shell.transition;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.Log;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.R;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.split.SplitState;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.keyguard.KeyguardTransitionHandler;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.RecentsTransitionHandler;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.shared.desktopmode.DesktopConfigImpl;
import com.android.wm.shell.splitscreen.SplitBackgroundController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.splitscreen.StageTaskListener;
import com.android.wm.shell.splitscreen.StageTaskListener$$ExternalSyntheticLambda0;
import com.android.wm.shell.transition.DefaultMixedHandler;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class RecentsMixedTransition extends DefaultMixedHandler.MixedTransition {
    public final DesktopTasksController mDesktopTasksController;
    public final RecentsTransitionHandler mRecentsHandler;

    public RecentsMixedTransition(int i, IBinder iBinder, Transitions transitions, MixedTransitionHandler mixedTransitionHandler, PipTransitionController pipTransitionController, StageCoordinator stageCoordinator, KeyguardTransitionHandler keyguardTransitionHandler, RecentsTransitionHandler recentsTransitionHandler, DesktopTasksController desktopTasksController) {
        super(i, iBinder, transitions, mixedTransitionHandler, pipTransitionController, stageCoordinator, keyguardTransitionHandler);
        this.mRecentsHandler = recentsTransitionHandler;
        this.mDesktopTasksController = desktopTasksController;
        this.mLeftoversHandler = recentsTransitionHandler;
    }

    @Override // com.android.wm.shell.transition.DefaultMixedHandler.MixedTransition
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        TransitionInfo transitionInfo2;
        RecentsMixedTransition recentsMixedTransition;
        int i = this.mType;
        if (i == 4) {
            if (this.mSplitHandler.mSplitTransitions.isPendingEnter(iBinder)) {
                this.mAnimType = 1;
            }
            this.mLeftoversHandler.mergeAnimation(iBinder, transitionInfo, transaction, transaction2, iBinder2, transitionFinishCallback);
            return;
        }
        if (i != 6) {
            if (i != 7) {
                throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Playing a Recents mixed transition with unknown or illegal type: "));
            }
            this.mLeftoversHandler.mergeAnimation(iBinder, transitionInfo, transaction, transaction2, iBinder2, transitionFinishCallback);
            return;
        }
        SurfaceControl.Transaction transaction3 = transaction;
        SurfaceControl.Transaction transaction4 = transaction2;
        if ((transitionInfo.getFlags() & 8192) != 0) {
            TransitionInfo transitionInfo3 = this.mInfo;
            for (int rootCount = transitionInfo.getRootCount() - 1; rootCount >= 0; rootCount--) {
                transaction3.show(transitionInfo.getRoot(rootCount).getLeash());
            }
            ArrayMap arrayMap = new ArrayMap();
            for (TransitionInfo.Change change : transitionInfo3.getChanges()) {
                if (change.getContainer() != null) {
                    arrayMap.put(change.getContainer(), change);
                }
            }
            for (TransitionInfo.Change change2 : transitionInfo.getChanges()) {
                if (arrayMap.containsKey(change2.getContainer())) {
                    TransitionInfo.Change change3 = (TransitionInfo.Change) arrayMap.get(change2.getContainer());
                    transaction3.reparent(change2.getLeash(), null);
                    change2.setLeash(change3.getLeash());
                }
            }
            recentsMixedTransition = this;
            boolean animateKeyguard = MixedTransitionHelper.animateKeyguard(recentsMixedTransition, transitionInfo, transaction3, transaction4, this.mFinishCB, this.mKeyguardHandler, this.mPipHandler);
            transitionInfo2 = transitionInfo;
            transaction3 = transaction3;
            transaction4 = transaction4;
            if (animateKeyguard) {
                transitionFinishCallback.onTransitionFinished(null);
            }
        } else {
            transitionInfo2 = transitionInfo;
            recentsMixedTransition = this;
        }
        recentsMixedTransition.mLeftoversHandler.mergeAnimation(iBinder, transitionInfo2, transaction3, transaction4, iBinder2, transitionFinishCallback);
    }

    @Override // com.android.wm.shell.transition.DefaultMixedHandler.MixedTransition
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        int i = this.mType;
        if (i == 4 || i == 6 || i == 7) {
            this.mLeftoversHandler.onTransitionConsumed(iBinder, z, transaction);
        }
        if (this.mHasRequestToRemote) {
            this.mPlayer.mRemoteTransitionHandler.onTransitionConsumed(iBinder, z, transaction);
        }
    }

    @Override // com.android.wm.shell.transition.DefaultMixedHandler.MixedTransition
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, final SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        int i;
        Transitions.TransitionFinishCallback transitionFinishCallback2 = transitionFinishCallback;
        int i2 = 0;
        int i3 = this.mType;
        if (i3 != 4) {
            if (i3 == 6) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -7297861622461350897L, 1, Long.valueOf(transitionInfo.getDebugId()));
                }
                KeyguardTransitionHandler keyguardTransitionHandler = this.mKeyguardHandler;
                if (keyguardTransitionHandler.mKeyguardShowing && keyguardTransitionHandler.mStartedTransitions.isEmpty()) {
                    if (this.mInfo == null) {
                        this.mInfo = transitionInfo;
                        this.mFinishT = transaction2;
                        this.mFinishCB = transitionFinishCallback2;
                    }
                    return startSubAnimation(this.mRecentsHandler, transitionInfo, transaction, transaction2);
                }
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[3]) {
                    ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -5448931542140636427L, 1, Long.valueOf(transitionInfo.getDebugId()));
                }
            } else {
                if (i3 != 7) {
                    throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i3, "Starting Recents mixed animation with unknown or illegal type: "));
                }
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -4327489982751689646L, 1, Long.valueOf(transitionInfo.getDebugId()));
                }
                if (this.mInfo == null) {
                    this.mInfo = transitionInfo;
                    this.mFinishT = transaction2;
                    this.mFinishCB = transitionFinishCallback2;
                }
                final DefaultMixedHandler$$ExternalSyntheticLambda4 defaultMixedHandler$$ExternalSyntheticLambda4 = (DefaultMixedHandler$$ExternalSyntheticLambda4) transitionFinishCallback2;
                Transitions.TransitionFinishCallback transitionFinishCallback3 = new Transitions.TransitionFinishCallback() { // from class: com.android.wm.shell.transition.RecentsMixedTransition$$ExternalSyntheticLambda0
                    @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
                    public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
                        RecentsMixedTransition recentsMixedTransition = RecentsMixedTransition.this;
                        int i4 = recentsMixedTransition.mInFlightSubAnimations - 1;
                        recentsMixedTransition.mInFlightSubAnimations = i4;
                        if (i4 == 0) {
                            defaultMixedHandler$$ExternalSyntheticLambda4.onTransitionFinished(windowContainerTransaction);
                        }
                    }
                };
                this.mInFlightSubAnimations++;
                if (!this.mRecentsHandler.startAnimation(this.mTransition, transitionInfo, transaction, transaction2, transitionFinishCallback3)) {
                    this.mInFlightSubAnimations--;
                    return false;
                }
                DesktopTasksController desktopTasksController = this.mDesktopTasksController;
                if (desktopTasksController != null) {
                    if (((DesktopConfigImpl) desktopTasksController.desktopConfig).useRoundedCorners) {
                        float dimensionPixelSize = desktopTasksController.context.getResources().getDimensionPixelSize(R.dimen.desktop_windowing_freeform_rounded_corner_radius);
                        List changes = transitionInfo.getChanges();
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : changes) {
                            ActivityManager.RunningTaskInfo taskInfo = ((TransitionInfo.Change) obj).getTaskInfo();
                            if (taskInfo != null && taskInfo.getWindowingMode() == 5) {
                                arrayList.add(obj);
                            }
                        }
                        int size = arrayList.size();
                        while (i2 < size) {
                            Object obj2 = arrayList.get(i2);
                            i2++;
                            transaction2.setCornerRadius(((TransitionInfo.Change) obj2).getLeash(), dimensionPixelSize);
                        }
                    }
                    return true;
                }
            }
            return false;
        }
        SurfaceControl.Transaction transaction3 = transaction;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -7057538322415863605L, 1, Long.valueOf(transitionInfo.getDebugId()));
        }
        int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1);
        while (true) {
            StageCoordinator stageCoordinator = this.mSplitHandler;
            if (m < 0) {
                final DefaultMixedHandler$$ExternalSyntheticLambda4 defaultMixedHandler$$ExternalSyntheticLambda42 = (DefaultMixedHandler$$ExternalSyntheticLambda4) transitionFinishCallback;
                Transitions.TransitionFinishCallback transitionFinishCallback4 = new Transitions.TransitionFinishCallback() { // from class: com.android.wm.shell.transition.RecentsMixedTransition$$ExternalSyntheticLambda1
                    @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
                    public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
                        ActivityManager.RunningTaskInfo runningTaskInfo;
                        boolean z;
                        SurfaceControl.Transaction transaction4 = transaction2;
                        RecentsMixedTransition recentsMixedTransition = RecentsMixedTransition.this;
                        boolean z2 = false;
                        recentsMixedTransition.mInFlightSubAnimations = 0;
                        if (windowContainerTransaction == null) {
                            windowContainerTransaction = new WindowContainerTransaction();
                        }
                        int i4 = recentsMixedTransition.mAnimType;
                        StageCoordinator stageCoordinator2 = recentsMixedTransition.mSplitHandler;
                        if (i4 == 1) {
                            stageCoordinator2.getClass();
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 3838958226342231619L, 0, null);
                            }
                            for (int size2 = stageCoordinator2.mPausingTasks.size() - 1; size2 >= 0; size2--) {
                                Integer num = (Integer) stageCoordinator2.mPausingTasks.get(size2);
                                int intValue = num.intValue();
                                StageTaskListener stageTaskListener = stageCoordinator2.mMainStage;
                                if (stageTaskListener.mChildrenTaskInfo.mTaskIds.contains(num)) {
                                    ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) stageTaskListener.mChildrenTaskInfo.get(intValue);
                                    if (runningTaskInfo2 != null) {
                                        StageTaskListener.evictChild(windowContainerTransaction, runningTaskInfo2, "recentsPairToPair");
                                    }
                                } else {
                                    StageTaskListener stageTaskListener2 = stageCoordinator2.mSideStage;
                                    if (stageTaskListener2.mChildrenTaskInfo.mTaskIds.contains(num) && (runningTaskInfo = (ActivityManager.RunningTaskInfo) stageTaskListener2.mChildrenTaskInfo.get(intValue)) != null) {
                                        StageTaskListener.evictChild(windowContainerTransaction, runningTaskInfo, "recentsPairToPair");
                                    }
                                }
                            }
                            if (stageCoordinator2.mSplitTransitions.mPendingEnter == null) {
                                stageCoordinator2.mPausingTasks.clear();
                                stageCoordinator2.updateRecentTasksSplitPair();
                            }
                            stageCoordinator2.mSplitBackgroundController.onRecentsInSplitAnimationFinish(true);
                        } else if (recentsMixedTransition.mRecentsHandler != recentsMixedTransition.mLeftoversHandler) {
                            stageCoordinator2.getClass();
                            int i5 = 0;
                            while (true) {
                                if (i5 >= windowContainerTransaction.getHierarchyOps().size()) {
                                    break;
                                }
                                WindowContainerTransaction.HierarchyOp hierarchyOp = (WindowContainerTransaction.HierarchyOp) windowContainerTransaction.getHierarchyOps().get(i5);
                                IBinder container = hierarchyOp.getContainer();
                                StageTaskListener stageTaskListener3 = stageCoordinator2.mMainStage;
                                stageTaskListener3.getClass();
                                if (!stageTaskListener3.contains(new StageTaskListener$$ExternalSyntheticLambda0(container, 1))) {
                                    StageTaskListener stageTaskListener4 = stageCoordinator2.mSideStage;
                                    stageTaskListener4.getClass();
                                    if (!stageTaskListener4.contains(new StageTaskListener$$ExternalSyntheticLambda0(container, 1))) {
                                        z = false;
                                        if (hierarchyOp.getType() != 1 && hierarchyOp.getToTop() && z) {
                                            z2 = true;
                                            break;
                                        }
                                        i5++;
                                    }
                                }
                                z = true;
                                if (hierarchyOp.getType() != 1) {
                                }
                                i5++;
                            }
                            stageCoordinator2.onRecentsInSplitAnimationFinishing(z2, windowContainerTransaction, transaction4);
                        }
                        stageCoordinator2.onTransitionAnimationComplete();
                        defaultMixedHandler$$ExternalSyntheticLambda42.onTransitionFinished(windowContainerTransaction);
                    }
                };
                this.mInFlightSubAnimations = 1;
                stageCoordinator.getClass();
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -5213330322118069807L, 1, Long.valueOf(transitionInfo.getDebugId()));
                }
                boolean isSplitScreenVisible = stageCoordinator.isSplitScreenVisible();
                StageTaskListener stageTaskListener = stageCoordinator.mSideStage;
                StageTaskListener stageTaskListener2 = stageCoordinator.mMainStage;
                if (isSplitScreenVisible) {
                    for (int i4 = 0; i4 < transitionInfo.getChanges().size(); i4++) {
                        TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i4);
                        if (TransitionUtil.isClosingType(change.getMode()) && change.getTaskInfo() != null && (stageTaskListener2.getTopVisibleChildTaskId() == (i = change.getTaskInfo().taskId) || stageTaskListener.getTopVisibleChildTaskId() == i || (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageCoordinator.isMultiSplitScreenVisible() && stageCoordinator.mCellStage.getTopVisibleChildTaskId() == i))) {
                            stageCoordinator.mPausingTasks.add(Integer.valueOf(i));
                            if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                                Rect startAbsBounds = change.getStartAbsBounds();
                                int i5 = stageCoordinator.mSplitLayout.mImePositionProcessor.mYOffsetForIme;
                                boolean z = (change.getTaskInfo().getConfiguration().windowConfiguration.getStagePosition() & 64) != 0;
                                if (i5 != 0 && z) {
                                    startAbsBounds.top = Math.abs(i5) + startAbsBounds.top;
                                    startAbsBounds.bottom = Math.abs(i5) + startAbsBounds.bottom;
                                    change.setStartAbsBounds(startAbsBounds);
                                    change.setEndAbsBounds(startAbsBounds);
                                }
                            }
                        }
                    }
                }
                boolean z2 = CoreRune.MW_SPLIT_SHELL_TRANSITION;
                SplitState splitState = stageCoordinator.mSplitState;
                if (!z2 || stageCoordinator.isSplitScreenVisible()) {
                    stageCoordinator.addDividerBarToTransition(transitionInfo, false);
                    int i6 = splitState.mState;
                } else {
                    Log.d("StageCoordinator", "onRecentsInSplitAnimationStart: skip divider, reason=split_invisible");
                }
                if (splitState.isSplitStashed()) {
                    TransactionPool transactionPool = stageCoordinator.mTransactionPool;
                    SurfaceControl.Transaction acquire = transactionPool.acquire();
                    acquire.setAlpha(stageTaskListener2.mDimLayer, 0.0f).hide(stageTaskListener2.mDimLayer);
                    acquire.setAlpha(stageTaskListener.mDimLayer, 0.0f).hide(stageTaskListener.mDimLayer);
                    acquire.apply();
                    transactionPool.release(acquire);
                    splitState.mState = 10;
                }
                if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && stageCoordinator.isMultiSplitScreenVisible()) {
                    stageCoordinator.addCellDividerBarToTransition(transitionInfo, false);
                }
                SplitBackgroundController splitBackgroundController = stageCoordinator.mSplitBackgroundController;
                splitBackgroundController.mHiddenWhileRecentsTransition = true;
                splitBackgroundController.updateVisibility(false, true);
                if (z2) {
                    stageCoordinator.mIsRecentsInSplitAnimating = true;
                }
                boolean startAnimation = this.mLeftoversHandler.startAnimation(this.mTransition, transitionInfo, transaction, transaction2, transitionFinishCallback4);
                if (!startAnimation) {
                    stageCoordinator.onRecentsInSplitAnimationCanceled();
                }
                return startAnimation;
            }
            TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(m);
            if (this.mPipHandler.isEnteringPip$1(change2, transitionInfo.getType()) && stageCoordinator.getSplitItemPosition(change2.getLastParent()) != -1) {
                return MixedTransitionHelper.animateEnterPipFromSplit(this, transitionInfo, transaction3, transaction2, transitionFinishCallback2, this.mPlayer, this.mMixedHandler, this.mPipHandler, this.mSplitHandler, false);
            }
            m--;
            transaction3 = transaction;
            transitionFinishCallback2 = transitionFinishCallback;
        }
    }
}
