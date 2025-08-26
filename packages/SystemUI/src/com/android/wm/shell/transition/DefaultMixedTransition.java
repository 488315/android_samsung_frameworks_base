package com.android.wm.shell.transition;

import android.animation.Animator;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.DesktopModeFlags;
import android.window.TransitionInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.wm.shell.activityembedding.ActivityEmbeddingController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.desktopmode.DesktopImmersiveController;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.keyguard.KeyguardTransitionHandler;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.pip.PipTransitionState;
import com.android.wm.shell.pip2.phone.transition.PipTransitionUtils;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.splitscreen.SplitScreenTransitions;
import com.android.wm.shell.splitscreen.SplitScreenTransitions$$ExternalSyntheticLambda2;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.taskview.TaskViewTransitions;
import com.android.wm.shell.transition.DefaultMixedHandler;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.unfold.UnfoldTransitionHandler;
import com.samsung.android.rune.CoreRune;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;

/* loaded from: classes3.dex */
public class DefaultMixedTransition extends DefaultMixedHandler.MixedTransition {
    public final ActivityEmbeddingController mActivityEmbeddingController;
    public final DesktopTasksController mDesktopTasksController;
    public final UnfoldTransitionHandler mUnfoldHandler;

    public DefaultMixedTransition(int i, IBinder iBinder, Transitions transitions, MixedTransitionHandler mixedTransitionHandler, PipTransitionController pipTransitionController, StageCoordinator stageCoordinator, KeyguardTransitionHandler keyguardTransitionHandler, UnfoldTransitionHandler unfoldTransitionHandler, ActivityEmbeddingController activityEmbeddingController, DesktopTasksController desktopTasksController) {
        super(i, iBinder, transitions, mixedTransitionHandler, pipTransitionController, stageCoordinator, keyguardTransitionHandler);
        this.mUnfoldHandler = unfoldTransitionHandler;
        this.mActivityEmbeddingController = activityEmbeddingController;
        this.mDesktopTasksController = desktopTasksController;
        if (i != 8) {
            return;
        }
        this.mLeftoversHandler = unfoldTransitionHandler;
    }

    @Override // com.android.wm.shell.transition.DefaultMixedHandler.MixedTransition
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        PipTransitionController pipTransitionController = this.mPipHandler;
        int i = this.mType;
        if (i == 1) {
            if (this.mAnimType == 1) {
                SplitScreenTransitions splitScreenTransitions = this.mSplitHandler.mSplitTransitions;
                if (splitScreenTransitions.mActiveRemoteHandler != null) {
                    return;
                }
                for (int size = splitScreenTransitions.mAnimations.size() - 1; size >= 0; size--) {
                    Animator animator = (Animator) splitScreenTransitions.mAnimations.get(size);
                    ShellExecutor shellExecutor = splitScreenTransitions.mTransitions.mAnimExecutor;
                    Objects.requireNonNull(animator);
                    shellExecutor.execute(new SplitScreenTransitions$$ExternalSyntheticLambda2(animator, 0));
                }
                pipTransitionController.end();
                Transitions.TransitionHandler transitionHandler = this.mLeftoversHandler;
                if (transitionHandler != null) {
                    transitionHandler.mergeAnimation(iBinder, transitionInfo, transaction, transaction2, iBinder2, transitionFinishCallback);
                    return;
                }
                return;
            }
            return;
        }
        if (i != 2) {
            if (i == 3) {
                pipTransitionController.end();
                Transitions.TransitionHandler transitionHandler2 = this.mLeftoversHandler;
                if (transitionHandler2 != null) {
                    transitionHandler2.mergeAnimation(iBinder, transitionInfo, transaction, transaction2, iBinder2, transitionFinishCallback);
                    return;
                }
                return;
            }
            if (i == 5) {
                this.mKeyguardHandler.mergeAnimation(iBinder, transitionInfo, transaction, transaction2, iBinder2, transitionFinishCallback);
                return;
            }
            if (i != 100) {
                if (i == 8) {
                    this.mUnfoldHandler.mergeAnimation(iBinder, transitionInfo, transaction, transaction2, iBinder2, transitionFinishCallback);
                    return;
                }
                if (i == 9) {
                    pipTransitionController.end();
                    this.mActivityEmbeddingController.mergeAnimation(iBinder, transitionInfo, transaction, transaction2, iBinder2, transitionFinishCallback);
                    return;
                } else {
                    if (i == 11) {
                        return;
                    }
                    if (i == 12) {
                        this.mDesktopTasksController.getClass();
                        return;
                    }
                }
            } else if (CoreRune.MW_PIP_SHELL_TRANSITION) {
                Log.d("DefaultMixedTransition", "mergeAnimation: mixed for pip is running, queueing info=" + transitionInfo + ", mergeTarget=" + iBinder2);
                return;
            }
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Playing a default mixed transition with unknown or illegal type: "));
        }
    }

    @Override // com.android.wm.shell.transition.DefaultMixedHandler.MixedTransition
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        PipTransitionController pipTransitionController = this.mPipHandler;
        int i = this.mType;
        if (i == 1) {
            pipTransitionController.onTransitionConsumed(iBinder, z, transaction);
        } else if (i == 3) {
            this.mLeftoversHandler.onTransitionConsumed(iBinder, z, transaction);
        } else if (i == 5) {
            this.mKeyguardHandler.onTransitionConsumed(iBinder, z, transaction);
        } else if (i == 12) {
            this.mDesktopTasksController.getClass();
        } else if (i == 8) {
            this.mUnfoldHandler.getClass();
        } else if (i == 9) {
            pipTransitionController.onTransitionConsumed(iBinder, z, transaction);
            this.mActivityEmbeddingController.getClass();
        }
        if (this.mHasRequestToRemote) {
            this.mPlayer.mRemoteTransitionHandler.onTransitionConsumed(iBinder, z, transaction);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x0410, code lost:
    
        if (r2.startAnimation(r18.mTransition, r20, r21, r22, r4) != false) goto L163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00a8, code lost:
    
        if (r1.startAnimation(r18.mTransition, r20, r21, r22, r5) != false) goto L114;
     */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0497  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x054f  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x055b  */
    @Override // com.android.wm.shell.transition.DefaultMixedHandler.MixedTransition
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback) {
        String str;
        SurfaceControl.Transaction transaction3;
        boolean z;
        TransitionInfo transitionInfo2;
        Transitions.TransitionHandler transitionHandler;
        Transitions.TransitionHandler transitionHandler2;
        RemoteTransitionHandler remoteTransitionHandler;
        Transitions.TransitionFinishCallback transitionFinishCallback2;
        int i = 1;
        int i2 = this.mType;
        if (i2 == 1) {
            return MixedTransitionHelper.animateEnterPipFromSplit(this, transitionInfo, transaction, transaction2, transitionFinishCallback, this.mPlayer, this.mMixedHandler, this.mPipHandler, this.mSplitHandler, false);
        }
        if (i2 != 2) {
            DesktopTasksController desktopTasksController = this.mDesktopTasksController;
            PipTransitionController pipTransitionController = this.mPipHandler;
            TransitionInfo.Change deferConfigActivityChange = null;
            if (i2 == 3) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                    str = "More than 1 pip-entering changes in one transition? ";
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 3729383924039431110L, 1, Long.valueOf(transitionInfo.getDebugId()));
                } else {
                    str = "More than 1 pip-entering changes in one transition? ";
                }
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -4548418617625529791L, 0, null);
                }
                int iM = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1);
                TransitionInfo.Change change = null;
                TransitionInfo.Change change2 = null;
                while (iM >= 0) {
                    TransitionInfo.Change change3 = (TransitionInfo.Change) transitionInfo.getChanges().get(iM);
                    if (pipTransitionController.isEnteringPip$1(change3, transitionInfo.getType())) {
                        if (change != null) {
                            throw new IllegalStateException(str + transitionInfo);
                        }
                        transitionInfo.getChanges().remove(iM);
                        change = change3;
                    } else if (change3.getTaskInfo() == null && change3.getParent() != null && change != null && change3.getParent().equals(change.getContainer())) {
                        change2 = change3;
                    }
                    iM--;
                    i = 1;
                }
                TransitionInfo.Change change4 = null;
                for (int iM2 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, i); iM2 >= 0; iM2--) {
                    TransitionInfo.Change change5 = (TransitionInfo.Change) transitionInfo.getChanges().get(iM2);
                    if (desktopTasksController != null) {
                        IBinder iBinder2 = this.mTransition;
                        if (DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue() && desktopTasksController.desktopImmersiveController.isImmersiveChange(iBinder2, change5)) {
                            if (change4 != null) {
                                throw new IllegalStateException("More than 1 desktop changes in one transition? " + transitionInfo);
                            }
                            transitionInfo.getChanges().remove(iM2);
                            change4 = change5;
                        }
                    }
                }
                final int i3 = 3;
                Transitions.TransitionFinishCallback transitionFinishCallback3 = new Transitions.TransitionFinishCallback(this) { // from class: com.android.wm.shell.transition.DefaultMixedTransition$$ExternalSyntheticLambda0
                    public final /* synthetic */ DefaultMixedTransition f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
                    public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
                        switch (i3) {
                            case 0:
                                DefaultMixedTransition defaultMixedTransition = this.f$0;
                                defaultMixedTransition.mInFlightSubAnimations--;
                                defaultMixedTransition.joinFinishArgs(windowContainerTransaction);
                                if (defaultMixedTransition.mInFlightSubAnimations <= 0) {
                                    transitionFinishCallback.onTransitionFinished(defaultMixedTransition.mFinishWCT);
                                    break;
                                }
                                break;
                            case 1:
                                DefaultMixedTransition defaultMixedTransition2 = this.f$0;
                                defaultMixedTransition2.mInFlightSubAnimations--;
                                defaultMixedTransition2.joinFinishArgs(windowContainerTransaction);
                                if (defaultMixedTransition2.mInFlightSubAnimations <= 0) {
                                    transitionFinishCallback.onTransitionFinished(defaultMixedTransition2.mFinishWCT);
                                    break;
                                }
                                break;
                            case 2:
                                DefaultMixedTransition defaultMixedTransition3 = this.f$0;
                                int i4 = defaultMixedTransition3.mInFlightSubAnimations - 1;
                                defaultMixedTransition3.mInFlightSubAnimations = i4;
                                if (i4 <= 0) {
                                    transitionFinishCallback.onTransitionFinished(windowContainerTransaction);
                                    break;
                                }
                                break;
                            case 3:
                                DefaultMixedTransition defaultMixedTransition4 = this.f$0;
                                defaultMixedTransition4.mInFlightSubAnimations--;
                                defaultMixedTransition4.joinFinishArgs(windowContainerTransaction);
                                if (defaultMixedTransition4.mInFlightSubAnimations <= 0) {
                                    transitionFinishCallback.onTransitionFinished(defaultMixedTransition4.mFinishWCT);
                                    break;
                                }
                                break;
                            default:
                                DefaultMixedTransition defaultMixedTransition5 = this.f$0;
                                defaultMixedTransition5.mInFlightSubAnimations--;
                                defaultMixedTransition5.joinFinishArgs(windowContainerTransaction);
                                if (defaultMixedTransition5.mInFlightSubAnimations <= 0) {
                                    transitionFinishCallback.onTransitionFinished(defaultMixedTransition5.mFinishWCT);
                                    break;
                                }
                                break;
                        }
                    }
                };
                if ((change == null && change4 == null) || (change != null && change4 != null)) {
                    Transitions.TransitionHandler transitionHandler3 = this.mLeftoversHandler;
                    if (transitionHandler3 != null) {
                        this.mInFlightSubAnimations = 1;
                    }
                    transaction3 = null;
                    z = false;
                    if (z) {
                    }
                    return z;
                }
                if (change == null || change4 != null) {
                    transaction3 = null;
                    if (change != null || change4 == null) {
                        throw new IllegalStateException("All PIP and Immersive combinations should've been handled");
                    }
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 3431072179921369533L, 1, Long.valueOf(transitionInfo.getDebugId()));
                    }
                    this.mInFlightSubAnimations = 2;
                    SurfaceControl.Transaction transaction4 = new SurfaceControl.Transaction();
                    IBinder iBinder3 = this.mTransition;
                    DesktopImmersiveController desktopImmersiveController = desktopTasksController.desktopImmersiveController;
                    if (!desktopImmersiveController.isImmersiveChange(iBinder3, change4)) {
                        throw new IllegalStateException("Only immersive changes support desktop mixed transitions");
                    }
                    desktopImmersiveController.animateResizeChange(change4, transaction4, transaction2, transitionFinishCallback3);
                    Transitions.TransitionHandler transitionHandler4 = this.mLeftoversHandler;
                    if (transitionHandler4 == null || !transitionHandler4.startAnimation(this.mTransition, transitionInfo, transaction, transaction2, transitionFinishCallback3)) {
                        this.mLeftoversHandler = this.mPlayer.dispatchTransition(this.mTransition, transitionInfo, transaction, transaction2, transitionFinishCallback3, this.mMixedHandler, null);
                    }
                    z = true;
                    if (z && this.mHasRequestToRemote) {
                        transitionHandler2 = this.mLeftoversHandler;
                        remoteTransitionHandler = this.mPlayer.mRemoteTransitionHandler;
                        if (transitionHandler2 != remoteTransitionHandler) {
                            remoteTransitionHandler.onTransitionConsumed(iBinder, false, transaction3);
                        }
                    }
                    return z;
                }
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 2748168339669728004L, 1, Long.valueOf(transitionInfo.getDebugId()));
                }
                this.mInFlightSubAnimations = 2;
                SurfaceControl.Transaction transaction5 = new SurfaceControl.Transaction();
                if (CoreRune.MW_PIP_SHELL_TRANSITION) {
                    PipTransitionState pipTransitionState = pipTransitionController.mPipOrganizer.mPipTransitionState;
                    if (pipTransitionState.mInSwipePipToHomeTransition && pipTransitionState.mState == 1) {
                        SurfaceControl.Transaction transaction6 = new SurfaceControl.Transaction();
                        transaction5.addDebugName("PipStartTransaction");
                        transaction6.addDebugName("PipFinishTransaction");
                        transaction5.setAlpha(change.getLeash(), 0.0f);
                        transaction2.setAlpha(change.getLeash(), 1.0f);
                        Log.w("DefaultMixedTransition", "animateOpenIntentWithRemoteAndPip: new finishT, pipChange=" + change + ", inSwipeHome=true");
                        pipTransitionController.startEnterAnimation(change, transaction5, transaction6, transitionFinishCallback3);
                    } else if (change2 == null) {
                        pipTransitionController.startEnterAnimation(change, transaction5, transaction2, transitionFinishCallback3);
                    } else {
                        transitionInfo.getChanges().remove(change2);
                        transitionInfo2 = transitionInfo;
                        TransitionInfo transitionInfoSubCopy = DefaultMixedHandler.subCopy(transitionInfo2, 10, false);
                        transitionInfoSubCopy.getChanges().addAll(List.of(change, change2));
                        this.mPipHandler.startAnimation(this.mTransition, transitionInfoSubCopy, transaction, transaction2, transitionFinishCallback3);
                        transitionHandler = this.mLeftoversHandler;
                        if (transitionHandler == null && transitionHandler.startAnimation(this.mTransition, transitionInfo2, transaction, transaction2, transitionFinishCallback3)) {
                            transaction3 = null;
                        } else {
                            transaction3 = null;
                            this.mLeftoversHandler = this.mPlayer.dispatchTransition(this.mTransition, transitionInfo, transaction, transaction2, transitionFinishCallback3, this.mMixedHandler, null);
                        }
                    }
                    transitionInfo2 = transitionInfo;
                    transitionHandler = this.mLeftoversHandler;
                    if (transitionHandler == null) {
                    }
                    transaction3 = null;
                    this.mLeftoversHandler = this.mPlayer.dispatchTransition(this.mTransition, transitionInfo, transaction, transaction2, transitionFinishCallback3, this.mMixedHandler, null);
                }
                if (z) {
                    transitionHandler2 = this.mLeftoversHandler;
                    remoteTransitionHandler = this.mPlayer.mRemoteTransitionHandler;
                    if (transitionHandler2 != remoteTransitionHandler) {
                    }
                }
                return z;
                z = true;
                if (z) {
                }
                return z;
            }
            if (i2 == 5) {
                return MixedTransitionHelper.animateKeyguard(this, transitionInfo, transaction, transaction2, transitionFinishCallback, this.mKeyguardHandler, pipTransitionController);
            }
            if (i2 != 101) {
                switch (i2) {
                    case 8:
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1296414416203729424L, 1, Long.valueOf(transitionInfo.getDebugId()));
                        }
                        final int i4 = 2;
                        Transitions.TransitionFinishCallback transitionFinishCallback4 = new Transitions.TransitionFinishCallback(this) { // from class: com.android.wm.shell.transition.DefaultMixedTransition$$ExternalSyntheticLambda0
                            public final /* synthetic */ DefaultMixedTransition f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
                            public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
                                switch (i4) {
                                    case 0:
                                        DefaultMixedTransition defaultMixedTransition = this.f$0;
                                        defaultMixedTransition.mInFlightSubAnimations--;
                                        defaultMixedTransition.joinFinishArgs(windowContainerTransaction);
                                        if (defaultMixedTransition.mInFlightSubAnimations <= 0) {
                                            transitionFinishCallback.onTransitionFinished(defaultMixedTransition.mFinishWCT);
                                            break;
                                        }
                                        break;
                                    case 1:
                                        DefaultMixedTransition defaultMixedTransition2 = this.f$0;
                                        defaultMixedTransition2.mInFlightSubAnimations--;
                                        defaultMixedTransition2.joinFinishArgs(windowContainerTransaction);
                                        if (defaultMixedTransition2.mInFlightSubAnimations <= 0) {
                                            transitionFinishCallback.onTransitionFinished(defaultMixedTransition2.mFinishWCT);
                                            break;
                                        }
                                        break;
                                    case 2:
                                        DefaultMixedTransition defaultMixedTransition3 = this.f$0;
                                        int i42 = defaultMixedTransition3.mInFlightSubAnimations - 1;
                                        defaultMixedTransition3.mInFlightSubAnimations = i42;
                                        if (i42 <= 0) {
                                            transitionFinishCallback.onTransitionFinished(windowContainerTransaction);
                                            break;
                                        }
                                        break;
                                    case 3:
                                        DefaultMixedTransition defaultMixedTransition4 = this.f$0;
                                        defaultMixedTransition4.mInFlightSubAnimations--;
                                        defaultMixedTransition4.joinFinishArgs(windowContainerTransaction);
                                        if (defaultMixedTransition4.mInFlightSubAnimations <= 0) {
                                            transitionFinishCallback.onTransitionFinished(defaultMixedTransition4.mFinishWCT);
                                            break;
                                        }
                                        break;
                                    default:
                                        DefaultMixedTransition defaultMixedTransition5 = this.f$0;
                                        defaultMixedTransition5.mInFlightSubAnimations--;
                                        defaultMixedTransition5.joinFinishArgs(windowContainerTransaction);
                                        if (defaultMixedTransition5.mInFlightSubAnimations <= 0) {
                                            transitionFinishCallback.onTransitionFinished(defaultMixedTransition5.mFinishWCT);
                                            break;
                                        }
                                        break;
                                }
                            }
                        };
                        this.mInFlightSubAnimations = 1;
                        if (pipTransitionController != null) {
                            pipTransitionController.syncPipSurfaceState(transitionInfo, transaction, transaction2);
                        }
                        StageCoordinator stageCoordinator = this.mSplitHandler;
                        if (stageCoordinator != null && stageCoordinator.mMainStage.mIsActive) {
                            stageCoordinator.updateSurfaceBounds(stageCoordinator.mSplitLayout, transaction, false);
                            stageCoordinator.mSplitLayout.update(transaction, true);
                        }
                        return this.mUnfoldHandler.startAnimation(this.mTransition, transitionInfo, transaction, transaction2, transitionFinishCallback4);
                    case 9:
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 5943843376640900060L, 1, Long.valueOf(transitionInfo.getDebugId()));
                        }
                        TransitionInfo transitionInfoSubCopy2 = DefaultMixedHandler.subCopy(transitionInfo, 4, true);
                        TransitionInfo.Change change6 = null;
                        for (int iM3 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); iM3 >= 0; iM3--) {
                            TransitionInfo.Change change7 = (TransitionInfo.Change) transitionInfo.getChanges().get(iM3);
                            if (pipTransitionController.isEnteringPip$1(change7, transitionInfo.getType())) {
                                if (change6 != null) {
                                    throw new IllegalStateException("More than 1 pip-entering changes in one transition? " + transitionInfo);
                                }
                                transitionInfoSubCopy2.getChanges().remove(iM3);
                                change6 = change7;
                            }
                        }
                        if (change6 != null) {
                            deferConfigActivityChange = PipTransitionUtils.getDeferConfigActivityChange(transitionInfo, change6.getContainer());
                            transitionInfoSubCopy2.getChanges().remove(deferConfigActivityChange);
                        }
                        final int i5 = 0;
                        Transitions.TransitionFinishCallback transitionFinishCallback5 = new Transitions.TransitionFinishCallback(this) { // from class: com.android.wm.shell.transition.DefaultMixedTransition$$ExternalSyntheticLambda0
                            public final /* synthetic */ DefaultMixedTransition f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
                            public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
                                switch (i5) {
                                    case 0:
                                        DefaultMixedTransition defaultMixedTransition = this.f$0;
                                        defaultMixedTransition.mInFlightSubAnimations--;
                                        defaultMixedTransition.joinFinishArgs(windowContainerTransaction);
                                        if (defaultMixedTransition.mInFlightSubAnimations <= 0) {
                                            transitionFinishCallback.onTransitionFinished(defaultMixedTransition.mFinishWCT);
                                            break;
                                        }
                                        break;
                                    case 1:
                                        DefaultMixedTransition defaultMixedTransition2 = this.f$0;
                                        defaultMixedTransition2.mInFlightSubAnimations--;
                                        defaultMixedTransition2.joinFinishArgs(windowContainerTransaction);
                                        if (defaultMixedTransition2.mInFlightSubAnimations <= 0) {
                                            transitionFinishCallback.onTransitionFinished(defaultMixedTransition2.mFinishWCT);
                                            break;
                                        }
                                        break;
                                    case 2:
                                        DefaultMixedTransition defaultMixedTransition3 = this.f$0;
                                        int i42 = defaultMixedTransition3.mInFlightSubAnimations - 1;
                                        defaultMixedTransition3.mInFlightSubAnimations = i42;
                                        if (i42 <= 0) {
                                            transitionFinishCallback.onTransitionFinished(windowContainerTransaction);
                                            break;
                                        }
                                        break;
                                    case 3:
                                        DefaultMixedTransition defaultMixedTransition4 = this.f$0;
                                        defaultMixedTransition4.mInFlightSubAnimations--;
                                        defaultMixedTransition4.joinFinishArgs(windowContainerTransaction);
                                        if (defaultMixedTransition4.mInFlightSubAnimations <= 0) {
                                            transitionFinishCallback.onTransitionFinished(defaultMixedTransition4.mFinishWCT);
                                            break;
                                        }
                                        break;
                                    default:
                                        DefaultMixedTransition defaultMixedTransition5 = this.f$0;
                                        defaultMixedTransition5.mInFlightSubAnimations--;
                                        defaultMixedTransition5.joinFinishArgs(windowContainerTransaction);
                                        if (defaultMixedTransition5.mInFlightSubAnimations <= 0) {
                                            transitionFinishCallback.onTransitionFinished(defaultMixedTransition5.mFinishWCT);
                                            break;
                                        }
                                        break;
                                }
                            }
                        };
                        this.mActivityEmbeddingController.getClass();
                        if (ActivityEmbeddingController.shouldAnimate(transitionInfoSubCopy2)) {
                            if (change6 != null && deferConfigActivityChange == null) {
                                this.mInFlightSubAnimations = 2;
                                pipTransitionController.startEnterAnimation(change6, transaction.setLayer(change6.getLeash(), Integer.MAX_VALUE), transaction2, transitionFinishCallback5);
                                transitionFinishCallback2 = transitionFinishCallback5;
                            } else if (deferConfigActivityChange != null) {
                                this.mInFlightSubAnimations = 2;
                                TransitionInfo transitionInfoSubCopy3 = DefaultMixedHandler.subCopy(transitionInfo, 10, false);
                                transitionInfoSubCopy3.getChanges().addAll(List.of(change6, deferConfigActivityChange));
                                transitionFinishCallback2 = transitionFinishCallback5;
                                this.mPipHandler.startAnimation(this.mTransition, transitionInfoSubCopy3, transaction.setLayer(change6.getLeash(), Integer.MAX_VALUE), transaction2, transitionFinishCallback2);
                            } else {
                                transitionFinishCallback2 = transitionFinishCallback5;
                                this.mInFlightSubAnimations = 1;
                            }
                            this.mActivityEmbeddingController.startAnimation(this.mTransition, transitionInfoSubCopy2, transaction, transaction2, transitionFinishCallback2);
                            return true;
                        }
                        break;
                    case 10:
                        return MixedTransitionHelper.animateEnterPipFromSplit(this, transitionInfo, transaction, transaction2, transitionFinishCallback, this.mPlayer, this.mMixedHandler, pipTransitionController, this.mSplitHandler, true);
                    case 11:
                        break;
                    case 12:
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 8548083066899020117L, 0, null);
                        }
                        for (int iM4 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); iM4 >= 0; iM4--) {
                            TransitionInfo.Change change8 = (TransitionInfo.Change) transitionInfo.getChanges().get(iM4);
                            IBinder iBinder4 = this.mTransition;
                            desktopTasksController.getClass();
                            if (DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue() && desktopTasksController.desktopImmersiveController.isImmersiveChange(iBinder4, change8)) {
                                if (deferConfigActivityChange != null) {
                                    throw new IllegalStateException("More than 1 desktop changes in one transition? " + transitionInfo);
                                }
                                transitionInfo.getChanges().remove(iM4);
                                deferConfigActivityChange = change8;
                            }
                        }
                        final int i6 = 1;
                        Transitions.TransitionFinishCallback transitionFinishCallback6 = new Transitions.TransitionFinishCallback(this) { // from class: com.android.wm.shell.transition.DefaultMixedTransition$$ExternalSyntheticLambda0
                            public final /* synthetic */ DefaultMixedTransition f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
                            public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
                                switch (i6) {
                                    case 0:
                                        DefaultMixedTransition defaultMixedTransition = this.f$0;
                                        defaultMixedTransition.mInFlightSubAnimations--;
                                        defaultMixedTransition.joinFinishArgs(windowContainerTransaction);
                                        if (defaultMixedTransition.mInFlightSubAnimations <= 0) {
                                            transitionFinishCallback.onTransitionFinished(defaultMixedTransition.mFinishWCT);
                                            break;
                                        }
                                        break;
                                    case 1:
                                        DefaultMixedTransition defaultMixedTransition2 = this.f$0;
                                        defaultMixedTransition2.mInFlightSubAnimations--;
                                        defaultMixedTransition2.joinFinishArgs(windowContainerTransaction);
                                        if (defaultMixedTransition2.mInFlightSubAnimations <= 0) {
                                            transitionFinishCallback.onTransitionFinished(defaultMixedTransition2.mFinishWCT);
                                            break;
                                        }
                                        break;
                                    case 2:
                                        DefaultMixedTransition defaultMixedTransition3 = this.f$0;
                                        int i42 = defaultMixedTransition3.mInFlightSubAnimations - 1;
                                        defaultMixedTransition3.mInFlightSubAnimations = i42;
                                        if (i42 <= 0) {
                                            transitionFinishCallback.onTransitionFinished(windowContainerTransaction);
                                            break;
                                        }
                                        break;
                                    case 3:
                                        DefaultMixedTransition defaultMixedTransition4 = this.f$0;
                                        defaultMixedTransition4.mInFlightSubAnimations--;
                                        defaultMixedTransition4.joinFinishArgs(windowContainerTransaction);
                                        if (defaultMixedTransition4.mInFlightSubAnimations <= 0) {
                                            transitionFinishCallback.onTransitionFinished(defaultMixedTransition4.mFinishWCT);
                                            break;
                                        }
                                        break;
                                    default:
                                        DefaultMixedTransition defaultMixedTransition5 = this.f$0;
                                        defaultMixedTransition5.mInFlightSubAnimations--;
                                        defaultMixedTransition5.joinFinishArgs(windowContainerTransaction);
                                        if (defaultMixedTransition5.mInFlightSubAnimations <= 0) {
                                            transitionFinishCallback.onTransitionFinished(defaultMixedTransition5.mFinishWCT);
                                            break;
                                        }
                                        break;
                                }
                            }
                        };
                        if (deferConfigActivityChange != null) {
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 2941158815916571903L, 1, Long.valueOf(transitionInfo.getDebugId()));
                            }
                            this.mInFlightSubAnimations = 2;
                            DesktopImmersiveController desktopImmersiveController2 = desktopTasksController.desktopImmersiveController;
                            if (!desktopImmersiveController2.isImmersiveChange(iBinder, deferConfigActivityChange)) {
                                throw new IllegalStateException("Only immersive changes support desktop mixed transitions");
                            }
                            desktopImmersiveController2.animateResizeChange(deferConfigActivityChange, transaction, transaction2, transitionFinishCallback6);
                            this.mLeftoversHandler = this.mPlayer.dispatchTransition(this.mTransition, transitionInfo, transaction, transaction2, transitionFinishCallback6, this.mMixedHandler, null);
                            return true;
                        }
                        Transitions.TransitionHandler transitionHandler5 = this.mLeftoversHandler;
                        if (transitionHandler5 != null) {
                            this.mInFlightSubAnimations = 1;
                            break;
                        }
                        break;
                    default:
                        throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "Starting default mixed animation with unknown or illegal type: "));
                }
            } else if (this.mTaskViewTransitions != null) {
                final int i7 = 4;
                Transitions.TransitionFinishCallback transitionFinishCallback7 = new Transitions.TransitionFinishCallback(this) { // from class: com.android.wm.shell.transition.DefaultMixedTransition$$ExternalSyntheticLambda0
                    public final /* synthetic */ DefaultMixedTransition f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
                    public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
                        switch (i7) {
                            case 0:
                                DefaultMixedTransition defaultMixedTransition = this.f$0;
                                defaultMixedTransition.mInFlightSubAnimations--;
                                defaultMixedTransition.joinFinishArgs(windowContainerTransaction);
                                if (defaultMixedTransition.mInFlightSubAnimations <= 0) {
                                    transitionFinishCallback.onTransitionFinished(defaultMixedTransition.mFinishWCT);
                                    break;
                                }
                                break;
                            case 1:
                                DefaultMixedTransition defaultMixedTransition2 = this.f$0;
                                defaultMixedTransition2.mInFlightSubAnimations--;
                                defaultMixedTransition2.joinFinishArgs(windowContainerTransaction);
                                if (defaultMixedTransition2.mInFlightSubAnimations <= 0) {
                                    transitionFinishCallback.onTransitionFinished(defaultMixedTransition2.mFinishWCT);
                                    break;
                                }
                                break;
                            case 2:
                                DefaultMixedTransition defaultMixedTransition3 = this.f$0;
                                int i42 = defaultMixedTransition3.mInFlightSubAnimations - 1;
                                defaultMixedTransition3.mInFlightSubAnimations = i42;
                                if (i42 <= 0) {
                                    transitionFinishCallback.onTransitionFinished(windowContainerTransaction);
                                    break;
                                }
                                break;
                            case 3:
                                DefaultMixedTransition defaultMixedTransition4 = this.f$0;
                                defaultMixedTransition4.mInFlightSubAnimations--;
                                defaultMixedTransition4.joinFinishArgs(windowContainerTransaction);
                                if (defaultMixedTransition4.mInFlightSubAnimations <= 0) {
                                    transitionFinishCallback.onTransitionFinished(defaultMixedTransition4.mFinishWCT);
                                    break;
                                }
                                break;
                            default:
                                DefaultMixedTransition defaultMixedTransition5 = this.f$0;
                                defaultMixedTransition5.mInFlightSubAnimations--;
                                defaultMixedTransition5.joinFinishArgs(windowContainerTransaction);
                                if (defaultMixedTransition5.mInFlightSubAnimations <= 0) {
                                    transitionFinishCallback.onTransitionFinished(defaultMixedTransition5.mFinishWCT);
                                    break;
                                }
                                break;
                        }
                    }
                };
                TransitionInfo transitionInfoSubCopy4 = DefaultMixedHandler.subCopy(transitionInfo, transitionInfo.getType(), true);
                transitionInfoSubCopy4.setFlags(transitionInfoSubCopy4.getFlags() & (-8257));
                TransitionInfo transitionInfoSubCopy5 = DefaultMixedHandler.subCopy(transitionInfo, transitionInfo.getType(), true);
                TaskViewTransitions taskViewTransitions = this.mTaskViewTransitions;
                IBinder iBinder5 = this.mTransition;
                if (taskViewTransitions.findPending(iBinder5) == null) {
                    Slog.w("TaskViewTransitions", "addTransparentFlagForConsumedChange: failed, cannot find " + iBinder5);
                } else if (((WeakHashMap) taskViewTransitions.mTaskViews).isEmpty()) {
                    Slog.w("TaskViewTransitions", "addTransparentFlagForConsumedChange: failed, taskView is empty");
                } else {
                    for (int i8 = 0; i8 < transitionInfoSubCopy5.getChanges().size(); i8++) {
                        TransitionInfo.Change change9 = (TransitionInfo.Change) transitionInfoSubCopy5.getChanges().get(i8);
                        if (change9.getTaskInfo() != null && TransitionUtil.isClosingType(change9.getMode()) && taskViewTransitions.findTaskView(change9.getTaskInfo()) != null) {
                            change9.addFlags2(1);
                        }
                    }
                }
                this.mInFlightSubAnimations = 2;
                if (!this.mTaskViewTransitions.startAnimation(this.mTransition, transitionInfoSubCopy4, transaction, transaction2, transitionFinishCallback7)) {
                    Log.e("DefaultMixedTransition", "animateKeyguardUnOccludingWithTaskView: failed to start taskView transit");
                    transitionFinishCallback7.onTransitionFinished(null);
                }
                if (!this.mKeyguardHandler.startAnimation(this.mTransition, transitionInfoSubCopy5, transaction, transaction2, transitionFinishCallback7)) {
                    Log.w("DefaultMixedTransition", "animateKeyguardUnOccludingWithTaskView: failed to start keyguard transit");
                    this.mLeftoversHandler = this.mPlayer.dispatchTransition(this.mTransition, transitionInfo, transaction, transaction2, transitionFinishCallback7, this.mTaskViewTransitions, this.mMixedHandler);
                }
                return true;
            }
        }
        return false;
    }
}
