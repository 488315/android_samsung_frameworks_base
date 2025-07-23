package com.android.wm.shell.transition;

import android.animation.Animator;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import com.android.wm.shell.activityembedding.ActivityEmbeddingController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.keyguard.KeyguardTransitionHandler;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.splitscreen.SplitScreenTransitions;
import com.android.wm.shell.splitscreen.SplitScreenTransitions$$ExternalSyntheticLambda2;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.transition.DefaultMixedHandler;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.unfold.UnfoldTransitionHandler;
import com.samsung.android.rune.CoreRune;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    /* JADX WARN: Code restructure failed: missing block: B:181:0x0410, code lost:
    
        if (r2.startAnimation(r18.mTransition, r20, r21, r22, r4) != false) goto L163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00a8, code lost:
    
        if (r1.startAnimation(r18.mTransition, r20, r21, r22, r5) != false) goto L114;
     */
    /* JADX WARN: Removed duplicated region for block: B:185:0x054f  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x055b  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x04c8  */
    @Override // com.android.wm.shell.transition.DefaultMixedHandler.MixedTransition
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean startAnimation(android.os.IBinder r19, android.window.TransitionInfo r20, android.view.SurfaceControl.Transaction r21, android.view.SurfaceControl.Transaction r22, final com.android.wm.shell.transition.Transitions.TransitionFinishCallback r23) {
        /*
            Method dump skipped, instructions count: 1428
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.transition.DefaultMixedTransition.startAnimation(android.os.IBinder, android.window.TransitionInfo, android.view.SurfaceControl$Transaction, android.view.SurfaceControl$Transaction, com.android.wm.shell.transition.Transitions$TransitionFinishCallback):boolean");
    }
}
