package com.android.wm.shell.splitscreen;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.RemoteTransition;
import android.window.WindowContainerTransaction;
import android.window.WindowContainerTransactionCallback;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.wm.shell.animation.Interpolators;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.TransactionPool;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.transition.MultiTaskingTransitionProvider;
import com.android.wm.shell.transition.OneShotRemoteHandler;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SplitScreenTransitions {
    public final ShellExecutor mAnimExecutor;
    public ValueAnimator mDividerFadeAnimation;
    public SurfaceControl.Transaction mFinishTransaction;
    public final MultiTaskingTransitionProvider mMultiTaskingTransitions;
    public final Runnable mOnFinish;
    public final StageCoordinator mStageCoordinator;
    public final TransactionPool mTransactionPool;
    public final Transitions mTransitions;
    public DismissSession mPendingDismiss = null;
    public EnterSession mPendingEnter = null;
    public TransitSession mPendingResize = null;
    public IBinder mAnimatingTransition = null;
    public OneShotRemoteHandler mActiveRemoteHandler = null;
    public final SplitScreenTransitions$$ExternalSyntheticLambda0 mRemoteFinishCB = new Transitions.TransitionFinishCallback() { // from class: com.android.wm.shell.splitscreen.SplitScreenTransitions$$ExternalSyntheticLambda0
        @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
        public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, WindowContainerTransactionCallback windowContainerTransactionCallback) {
            SplitScreenTransitions.this.onFinish(windowContainerTransaction, windowContainerTransactionCallback);
        }
    };
    public final ArrayList mAnimations = new ArrayList();
    public Transitions.TransitionFinishCallback mFinishCallback = null;
    public float mDurationScale = 1.0f;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.splitscreen.SplitScreenTransitions$1 */
    public final class C41131 extends AnimatorListenerAdapter {
        public final /* synthetic */ float val$end;
        public final /* synthetic */ SurfaceControl val$leash;
        public final /* synthetic */ SurfaceControl.Transaction val$transaction;
        public final /* synthetic */ ValueAnimator val$va;

        public C41131(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, float f, ValueAnimator valueAnimator) {
            this.val$transaction = transaction;
            this.val$leash = surfaceControl;
            this.val$end = f;
            this.val$va = valueAnimator;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            this.val$transaction.setAlpha(this.val$leash, this.val$end);
            this.val$transaction.apply();
            SplitScreenTransitions.this.mTransactionPool.release(this.val$transaction);
            ((HandlerExecutor) SplitScreenTransitions.this.mTransitions.mMainExecutor).execute(new SplitScreenTransitions$$ExternalSyntheticLambda7(this, this.val$va, 1));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DismissSession extends TransitSession {
        public final int mDismissTop;
        public final boolean mIsMultiSplitDismissed;
        public final int mReason;

        public DismissSession(SplitScreenTransitions splitScreenTransitions, IBinder iBinder, int i, int i2) {
            this(splitScreenTransitions, iBinder, i, i2, false);
        }

        public DismissSession(SplitScreenTransitions splitScreenTransitions, IBinder iBinder, int i, int i2, boolean z) {
            super(splitScreenTransitions, iBinder, null, null);
            this.mReason = i;
            this.mDismissTop = i2;
            this.mIsMultiSplitDismissed = CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER ? z : false;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EnterSession extends TransitSession {
        public final boolean mResizeAnim;

        public EnterSession(SplitScreenTransitions splitScreenTransitions, IBinder iBinder, RemoteTransition remoteTransition, int i, boolean z) {
            super(iBinder, null, null, remoteTransition, i);
            this.mResizeAnim = CoreRune.MW_SPLIT_SHELL_TRANSITION ? false : z;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class TransitSession {
        public boolean mCanceled;
        public final TransitionConsumedCallback mConsumedCallback;
        public final int mExtraTransitType;
        public TransitionFinishedCallback mFinishedCallback;
        public final OneShotRemoteHandler mRemoteHandler;
        public final IBinder mTransition;

        public TransitSession(SplitScreenTransitions splitScreenTransitions, IBinder iBinder, TransitionConsumedCallback transitionConsumedCallback, TransitionFinishedCallback transitionFinishedCallback) {
            this(iBinder, transitionConsumedCallback, transitionFinishedCallback, null, 0);
        }

        public final void onConsumed() {
            TransitionConsumedCallback transitionConsumedCallback = this.mConsumedCallback;
            if (transitionConsumedCallback != null) {
                ((StageCoordinator$$ExternalSyntheticLambda1) transitionConsumedCallback).f$0.mSplitLayout.setDividerInteractive("handleLayoutSizeChange", true, false);
            }
        }

        public TransitSession(IBinder iBinder, TransitionConsumedCallback transitionConsumedCallback, TransitionFinishedCallback transitionFinishedCallback, RemoteTransition remoteTransition, int i) {
            this.mTransition = iBinder;
            this.mConsumedCallback = transitionConsumedCallback;
            this.mFinishedCallback = transitionFinishedCallback;
            if (remoteTransition != null) {
                OneShotRemoteHandler oneShotRemoteHandler = new OneShotRemoteHandler(SplitScreenTransitions.this.mTransitions.mMainExecutor, remoteTransition);
                this.mRemoteHandler = oneShotRemoteHandler;
                oneShotRemoteHandler.mTransition = iBinder;
                if (!CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && i == 1004) {
                    SplitScreenTransitions$$ExternalSyntheticLambda3 splitScreenTransitions$$ExternalSyntheticLambda3 = new SplitScreenTransitions$$ExternalSyntheticLambda3(this, 1);
                    SplitScreenTransitions$$ExternalSyntheticLambda3 splitScreenTransitions$$ExternalSyntheticLambda32 = new SplitScreenTransitions$$ExternalSyntheticLambda3(this, 2);
                    oneShotRemoteHandler.mStartedCallbackForSplitScreen = splitScreenTransitions$$ExternalSyntheticLambda3;
                    oneShotRemoteHandler.mFinishedCallbackForSplitScreen = splitScreenTransitions$$ExternalSyntheticLambda32;
                }
                if (CoreRune.MW_SHELL_TRANSITION) {
                    oneShotRemoteHandler.mMultiTaskingTransitions = SplitScreenTransitions.this.mMultiTaskingTransitions;
                    oneShotRemoteHandler.mAnimExecutor = SplitScreenTransitions.this.mAnimExecutor;
                }
            }
            this.mExtraTransitType = i;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface TransitionConsumedCallback {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface TransitionFinishedCallback {
        void onFinished(WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.wm.shell.splitscreen.SplitScreenTransitions$$ExternalSyntheticLambda0] */
    public SplitScreenTransitions(TransactionPool transactionPool, Transitions transitions, Runnable runnable, StageCoordinator stageCoordinator) {
        this.mTransactionPool = transactionPool;
        this.mTransitions = transitions;
        this.mOnFinish = runnable;
        this.mStageCoordinator = stageCoordinator;
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
            this.mMultiTaskingTransitions = transitions.mMultiTaskingTransitProvider;
            this.mAnimExecutor = transitions.mAnimExecutor;
        }
    }

    public final TransitSession getPendingTransition(IBinder iBinder) {
        if (isPendingEnter(iBinder)) {
            return this.mPendingEnter;
        }
        if (isPendingDismiss(iBinder)) {
            return this.mPendingDismiss;
        }
        if (isPendingResize(iBinder)) {
            return this.mPendingResize;
        }
        return null;
    }

    public final boolean isPendingDismiss(IBinder iBinder) {
        DismissSession dismissSession = this.mPendingDismiss;
        return dismissSession != null && dismissSession.mTransition == iBinder;
    }

    public final boolean isPendingEnter(IBinder iBinder) {
        EnterSession enterSession = this.mPendingEnter;
        return enterSession != null && enterSession.mTransition == iBinder;
    }

    public final boolean isPendingResize(IBinder iBinder) {
        TransitSession transitSession = this.mPendingResize;
        return transitSession != null && transitSession.mTransition == iBinder;
    }

    public final void onFinish(WindowContainerTransaction windowContainerTransaction, WindowContainerTransactionCallback windowContainerTransactionCallback) {
        if (this.mAnimations.isEmpty()) {
            if (windowContainerTransaction == null) {
                windowContainerTransaction = new WindowContainerTransaction();
            }
            if (isPendingEnter(this.mAnimatingTransition)) {
                EnterSession enterSession = this.mPendingEnter;
                SurfaceControl.Transaction transaction = this.mFinishTransaction;
                TransitionFinishedCallback transitionFinishedCallback = enterSession.mFinishedCallback;
                if (transitionFinishedCallback != null) {
                    transitionFinishedCallback.onFinished(windowContainerTransaction, transaction);
                }
                this.mPendingEnter = null;
            } else if (isPendingDismiss(this.mAnimatingTransition)) {
                DismissSession dismissSession = this.mPendingDismiss;
                SurfaceControl.Transaction transaction2 = this.mFinishTransaction;
                TransitionFinishedCallback transitionFinishedCallback2 = dismissSession.mFinishedCallback;
                if (transitionFinishedCallback2 != null) {
                    transitionFinishedCallback2.onFinished(windowContainerTransaction, transaction2);
                }
                this.mPendingDismiss = null;
            } else if (isPendingResize(this.mAnimatingTransition)) {
                TransitSession transitSession = this.mPendingResize;
                SurfaceControl.Transaction transaction3 = this.mFinishTransaction;
                TransitionFinishedCallback transitionFinishedCallback3 = transitSession.mFinishedCallback;
                if (transitionFinishedCallback3 != null) {
                    transitionFinishedCallback3.onFinished(windowContainerTransaction, transaction3);
                }
                this.mPendingResize = null;
            }
            this.mActiveRemoteHandler = null;
            this.mAnimatingTransition = null;
            this.mOnFinish.run();
            Transitions.TransitionFinishCallback transitionFinishCallback = this.mFinishCallback;
            if (transitionFinishCallback != null) {
                if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                    this.mFinishCallback = null;
                    transitionFinishCallback.onTransitionFinished(windowContainerTransaction, windowContainerTransactionCallback);
                } else {
                    transitionFinishCallback.onTransitionFinished(windowContainerTransaction, windowContainerTransactionCallback);
                    this.mFinishCallback = null;
                }
            }
        }
    }

    public final void setDismissTransition(IBinder iBinder, int i, int i2, boolean z) {
        this.mPendingDismiss = new DismissSession(this, iBinder, i2, i, z);
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 1852066478, 0, "  splitTransition  deduced Dismiss due to %s. toTop=%s", String.valueOf(SplitScreenController.exitReasonToString(i2)), String.valueOf(SplitScreen.stageTypeToString(i)));
        }
    }

    public final void setEnterTransition(IBinder iBinder, RemoteTransition remoteTransition, int i, boolean z) {
        this.mPendingEnter = new EnterSession(this, iBinder, remoteTransition, i, z);
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 648504948, 0, "  splitTransition  deduced Enter split screen", null);
        }
    }

    public final void startCustomFadeAnimation(final SurfaceControl surfaceControl, boolean z, boolean z2) {
        float f = z ? 1.0f : 0.0f;
        float f2 = 1.0f - f;
        final SurfaceControl.Transaction acquire = this.mTransactionPool.acquire();
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(f2, f);
        if (z2) {
            ofFloat.setDuration((long) (this.mDurationScale * 100.0f));
            ofFloat.setStartDelay((long) (this.mDurationScale * 300.0f));
            acquire.setAlpha(surfaceControl, 0.0f).apply();
            this.mDividerFadeAnimation = ofFloat;
        } else {
            ofFloat.setDuration((long) (this.mDurationScale * 133.0f));
        }
        ofFloat.setInterpolator(z ? Interpolators.ALPHA_IN : Interpolators.ALPHA_OUT);
        final SplitScreenTransitions$$ExternalSyntheticLambda2 splitScreenTransitions$$ExternalSyntheticLambda2 = new SplitScreenTransitions$$ExternalSyntheticLambda2(f2, f, 1, acquire, surfaceControl);
        ofFloat.addUpdateListener(splitScreenTransitions$$ExternalSyntheticLambda2);
        final float f3 = f;
        final Runnable runnable = new Runnable() { // from class: com.android.wm.shell.splitscreen.SplitScreenTransitions$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                SplitScreenTransitions splitScreenTransitions = SplitScreenTransitions.this;
                SurfaceControl.Transaction transaction = acquire;
                SurfaceControl surfaceControl2 = surfaceControl;
                float f4 = f3;
                ValueAnimator valueAnimator = ofFloat;
                splitScreenTransitions.getClass();
                transaction.setAlpha(surfaceControl2, f4);
                transaction.apply();
                splitScreenTransitions.mTransactionPool.release(transaction);
                ((HandlerExecutor) splitScreenTransitions.mTransitions.mMainExecutor).execute(new SplitScreenTransitions$$ExternalSyntheticLambda7(splitScreenTransitions, valueAnimator, 0));
            }
        };
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.splitscreen.SplitScreenTransitions.2
            public boolean mFinished = false;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                onAnimationFinished();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                onAnimationFinished();
            }

            public final void onAnimationFinished() {
                if (this.mFinished) {
                    return;
                }
                this.mFinished = true;
                runnable.run();
                ofFloat.removeUpdateListener(splitScreenTransitions$$ExternalSyntheticLambda2);
                SplitScreenTransitions splitScreenTransitions = SplitScreenTransitions.this;
                if (splitScreenTransitions.mDividerFadeAnimation != null) {
                    splitScreenTransitions.mDividerFadeAnimation = null;
                }
            }
        });
        this.mAnimations.add(ofFloat);
        StringBuilder sb = new StringBuilder("startFadeAnimation: leash=");
        sb.append(surfaceControl);
        sb.append(", show=");
        sb.append(z);
        ExifInterface$$ExternalSyntheticOutline0.m35m(sb, z2 ? ", isDividerChange=true" : "", "SplitScreenTransitions");
    }

    public final void startDismissTransition(WindowContainerTransaction windowContainerTransaction, Transitions.TransitionHandler transitionHandler, int i, int i2) {
        startDismissTransition(windowContainerTransaction, transitionHandler, i, i2, false);
    }

    public final void startEnterTransition(WindowContainerTransaction windowContainerTransaction, RemoteTransition remoteTransition, Transitions.TransitionHandler transitionHandler, int i, boolean z) {
        if (this.mPendingEnter == null) {
            setEnterTransition(this.mTransitions.startTransition(3, windowContainerTransaction, transitionHandler), remoteTransition, i, z);
        } else if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1241080475, 0, "  splitTransition  skip to start enter split transition since it already exist. ", null);
        }
    }

    public final void startFadeAnimation(SurfaceControl surfaceControl) {
        int i = 0;
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
            startCustomFadeAnimation(surfaceControl, false, false);
            return;
        }
        SurfaceControl.Transaction acquire = this.mTransactionPool.acquire();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat.setDuration(133L);
        ofFloat.setInterpolator(Interpolators.ALPHA_OUT);
        ofFloat.addUpdateListener(new SplitScreenTransitions$$ExternalSyntheticLambda2(1.0f, 0.0f, 0, acquire, surfaceControl));
        ofFloat.addListener(new C41131(acquire, surfaceControl, 0.0f, ofFloat));
        this.mAnimations.add(ofFloat);
        ((HandlerExecutor) this.mTransitions.mAnimExecutor).execute(new SplitScreenTransitions$$ExternalSyntheticLambda3(ofFloat, i));
    }

    public final void startFullscreenTransition(WindowContainerTransaction windowContainerTransaction, RemoteTransition remoteTransition) {
        Transitions transitions = this.mTransitions;
        transitions.startTransition(1, windowContainerTransaction, new OneShotRemoteHandler(transitions.mMainExecutor, remoteTransition));
    }

    public final IBinder startDismissTransition(WindowContainerTransaction windowContainerTransaction, Transitions.TransitionHandler transitionHandler, int i, int i2, boolean z) {
        if (this.mPendingDismiss != null) {
            if (!ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                return null;
            }
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -141233121, 0, "  splitTransition  skip to start dismiss split transition since it already exist. reason to  dismiss = %s", String.valueOf(SplitScreenController.exitReasonToString(i2)));
            return null;
        }
        IBinder startTransition = this.mTransitions.startTransition(i2 == 4 ? 1006 : 1007, windowContainerTransaction, transitionHandler);
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            setDismissTransition(startTransition, i, i2, z);
        } else {
            setDismissTransition(startTransition, i, i2, false);
        }
        return startTransition;
    }
}
