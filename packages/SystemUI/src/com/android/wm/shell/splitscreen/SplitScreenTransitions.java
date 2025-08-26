package com.android.wm.shell.splitscreen;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.window.RemoteTransition;
import android.window.TransitionInfo;
import android.window.WindowContainerTransaction;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.wmshell.WMShell;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.split.SplitDecorManager;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.transition.AnimationLoader;
import com.android.wm.shell.transition.MultiTaskingTransitionProvider;
import com.android.wm.shell.transition.OneShotRemoteHandler;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class SplitScreenTransitions {
    public final ShellExecutor mAnimExecutor;
    public ValueAnimator mCellDividerFadeAnimation;
    public ValueAnimator mDividerFadeAnimation;
    public SurfaceControl.Transaction mFinishTransaction;
    public final MultiTaskingTransitionProvider mMultiTaskingTransitions;
    public final Runnable mOnFinish;
    public WMShell.AnonymousClass10 mSplitInvocationListener;
    public Executor mSplitInvocationListenerExecutor;
    public final StageCoordinator mStageCoordinator;
    public final TransactionPool mTransactionPool;
    public final Transitions mTransitions;
    public DismissSession mPendingDismiss = null;
    public EnterSession mPendingEnter = null;
    public TransitSession mPendingResize = null;
    public TransitSession mPendingRemotePassthrough = null;
    public IBinder mAnimatingTransition = null;
    public OneShotRemoteHandler mActiveRemoteHandler = null;
    public final SplitScreenTransitions$$ExternalSyntheticLambda1 mRemoteFinishCB = new Transitions.TransitionFinishCallback() { // from class: com.android.wm.shell.splitscreen.SplitScreenTransitions$$ExternalSyntheticLambda1
        @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
        public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
            this.f$0.onFinish(windowContainerTransaction);
        }
    };
    public final ArrayList mAnimations = new ArrayList();
    public Transitions.TransitionFinishCallback mFinishCallback = null;
    public float mDurationScale = 1.0f;

    /* renamed from: com.android.wm.shell.splitscreen.SplitScreenTransitions$1, reason: invalid class name */
    public class AnonymousClass1 extends AnimatorListenerAdapter {
        public final /* synthetic */ float val$end;
        public final /* synthetic */ SurfaceControl val$leash;
        public final /* synthetic */ ValueAnimator val$va;

        public AnonymousClass1(SurfaceControl surfaceControl, float f, ValueAnimator valueAnimator) {
            this.val$leash = surfaceControl;
            this.val$end = f;
            this.val$va = valueAnimator;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            SurfaceControl.Transaction transactionAcquire = SplitScreenTransitions.this.mTransactionPool.acquire();
            transactionAcquire.setAlpha(this.val$leash, this.val$end);
            transactionAcquire.apply();
            SplitScreenTransitions.this.mTransactionPool.release(transactionAcquire);
            SplitScreenTransitions.this.mTransitions.mMainExecutor.execute(new SplitScreenTransitions$$ExternalSyntheticLambda13(this, this.val$va, 1));
        }
    }

    public class DismissSession extends TransitSession {
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

    public class EnterSession extends TransitSession {
        public final long mPendingStartedTime;
        public final boolean mResizeAnim;

        public EnterSession(SplitScreenTransitions splitScreenTransitions, IBinder iBinder, RemoteTransition remoteTransition, int i, boolean z, int i2) {
            super(iBinder, null, null, remoteTransition, i);
            z = CoreRune.MW_SPLIT_SHELL_TRANSITION ? false : z;
            this.mPendingStartedTime = SystemClock.uptimeMillis();
            this.mResizeAnim = z;
        }
    }

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

        public TransitSession(IBinder iBinder, TransitionConsumedCallback transitionConsumedCallback, TransitionFinishedCallback transitionFinishedCallback, RemoteTransition remoteTransition, int i) {
            this.mTransition = iBinder;
            this.mConsumedCallback = transitionConsumedCallback;
            this.mFinishedCallback = transitionFinishedCallback;
            if (remoteTransition != null) {
                OneShotRemoteHandler oneShotRemoteHandler = new OneShotRemoteHandler(SplitScreenTransitions.this.mTransitions.mMainExecutor, remoteTransition);
                this.mRemoteHandler = oneShotRemoteHandler;
                oneShotRemoteHandler.mTransition = iBinder;
                if (!CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && i == 1004) {
                    SplitScreenTransitions$TransitSession$$ExternalSyntheticLambda0 splitScreenTransitions$TransitSession$$ExternalSyntheticLambda0 = new SplitScreenTransitions$TransitSession$$ExternalSyntheticLambda0(this, 0);
                    SplitScreenTransitions$TransitSession$$ExternalSyntheticLambda0 splitScreenTransitions$TransitSession$$ExternalSyntheticLambda02 = new SplitScreenTransitions$TransitSession$$ExternalSyntheticLambda0(this, 1);
                    oneShotRemoteHandler.mStartedCallbackForSplitScreen = splitScreenTransitions$TransitSession$$ExternalSyntheticLambda0;
                    oneShotRemoteHandler.mFinishedCallbackForSplitScreen = splitScreenTransitions$TransitSession$$ExternalSyntheticLambda02;
                }
                if (CoreRune.MW_SHELL_TRANSITION) {
                    oneShotRemoteHandler.mMultiTaskingTransitions = SplitScreenTransitions.this.mMultiTaskingTransitions;
                    oneShotRemoteHandler.mAnimExecutor = SplitScreenTransitions.this.mAnimExecutor;
                }
            }
            this.mExtraTransitType = i;
        }
    }

    public interface TransitionConsumedCallback {
        void onConsumed();
    }

    public interface TransitionFinishedCallback {
        void onFinished(WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.wm.shell.splitscreen.SplitScreenTransitions$$ExternalSyntheticLambda1] */
    public SplitScreenTransitions(TransactionPool transactionPool, Transitions transitions, Runnable runnable, StageCoordinator stageCoordinator) {
        this.mTransactionPool = transactionPool;
        this.mTransitions = transitions;
        this.mOnFinish = runnable;
        this.mStageCoordinator = stageCoordinator;
        if (CoreRune.MW_SHELL_TRANSITION) {
            this.mMultiTaskingTransitions = transitions.mMultiTaskingTransitProvider;
            this.mAnimExecutor = transitions.mAnimExecutor;
        }
    }

    public final void buildSurfaceAnimation(TransitionInfo.Change change, SurfaceControl surfaceControl, int i) {
        Log.d("SplitScreenTransitions", "buildSurfaceAnimation: leash=" + surfaceControl);
        SplitScreenTransitions$$ExternalSyntheticLambda0 splitScreenTransitions$$ExternalSyntheticLambda0 = new SplitScreenTransitions$$ExternalSyntheticLambda0(this, 2);
        Rect endAbsBounds = change.getEndAbsBounds();
        MultiTaskingTransitionProvider multiTaskingTransitionProvider = this.mMultiTaskingTransitions;
        Animation animationLoadAnimationFromResources = multiTaskingTransitionProvider.loadAnimationFromResources(i, endAbsBounds);
        Context displayContext = multiTaskingTransitionProvider.mDisplayController.getDisplayContext(change.getEndDisplayId());
        AnimationLoader animationLoader = (AnimationLoader) multiTaskingTransitionProvider.mAnimationLoaderMap.get(1);
        if (displayContext != null && animationLoader != null && animationLoader.getCornerRadius(displayContext) > 0.0f && (animationLoadAnimationFromResources instanceof AnimationSet)) {
            animationLoader.addRoundedClipAnimation(endAbsBounds, (AnimationSet) animationLoadAnimationFromResources, displayContext);
        }
        float roundedCornerRadius = animationLoadAnimationFromResources.hasRoundedCornerRadius() ? animationLoadAnimationFromResources.getRoundedCornerRadius() : 0.0f;
        Point point = new Point(0, 0);
        Rect rect = new Rect(endAbsBounds);
        rect.offsetTo(0, 0);
        multiTaskingTransitionProvider.buildSurfaceAnimator(this.mAnimations, animationLoadAnimationFromResources, surfaceControl, splitScreenTransitions$$ExternalSyntheticLambda0, point, roundedCornerRadius, rect, false);
    }

    public final void cancelDividerFadeAnimation() {
        ValueAnimator valueAnimator;
        ValueAnimator valueAnimator2 = this.mDividerFadeAnimation;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
        }
        if (!CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER || (valueAnimator = this.mCellDividerFadeAnimation) == null) {
            return;
        }
        valueAnimator.cancel();
    }

    public final TransitSession getPendingTransition(IBinder iBinder) {
        if (isPendingEnter(iBinder)) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 3929944391949955522L, 0, null);
            }
            return this.mPendingEnter;
        }
        if (isPendingDismiss(iBinder)) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -1314223692559991014L, 0, null);
            }
            return this.mPendingDismiss;
        }
        if (isPendingResize(iBinder)) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -7711313386716908935L, 0, null);
            }
            return this.mPendingResize;
        }
        TransitSession transitSession = this.mPendingRemotePassthrough;
        if (transitSession == null || transitSession.mTransition != iBinder) {
            return null;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1627459327008717711L, 0, null);
        }
        return this.mPendingRemotePassthrough;
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

    public final void onFinish(WindowContainerTransaction windowContainerTransaction) {
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
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 619325938945047095L, 0, null);
                }
            } else if (isPendingDismiss(this.mAnimatingTransition)) {
                DismissSession dismissSession = this.mPendingDismiss;
                SurfaceControl.Transaction transaction2 = this.mFinishTransaction;
                TransitionFinishedCallback transitionFinishedCallback2 = dismissSession.mFinishedCallback;
                if (transitionFinishedCallback2 != null) {
                    transitionFinishedCallback2.onFinished(windowContainerTransaction, transaction2);
                }
                this.mPendingDismiss = null;
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 3953471079119598503L, 0, null);
                }
            } else if (isPendingResize(this.mAnimatingTransition)) {
                TransitSession transitSession = this.mPendingResize;
                SurfaceControl.Transaction transaction3 = this.mFinishTransaction;
                TransitionFinishedCallback transitionFinishedCallback3 = transitSession.mFinishedCallback;
                if (transitionFinishedCallback3 != null) {
                    transitionFinishedCallback3.onFinished(windowContainerTransaction, transaction3);
                }
                this.mPendingResize = null;
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -5975192824424219031L, 0, null);
                }
            } else {
                IBinder iBinder = this.mAnimatingTransition;
                TransitSession transitSession2 = this.mPendingRemotePassthrough;
                if (transitSession2 != null && transitSession2.mTransition == iBinder) {
                    SurfaceControl.Transaction transaction4 = this.mFinishTransaction;
                    TransitionFinishedCallback transitionFinishedCallback4 = transitSession2.mFinishedCallback;
                    if (transitionFinishedCallback4 != null) {
                        transitionFinishedCallback4.onFinished(windowContainerTransaction, transaction4);
                    }
                    this.mPendingRemotePassthrough = null;
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 6666552884816591818L, 0, null);
                    }
                }
            }
            this.mActiveRemoteHandler = null;
            this.mAnimatingTransition = null;
            this.mOnFinish.run();
            Transitions.TransitionFinishCallback transitionFinishCallback = this.mFinishCallback;
            if (transitionFinishCallback != null) {
                this.mFinishCallback = null;
                transitionFinishCallback.onTransitionFinished(windowContainerTransaction);
            }
        }
    }

    public final void setDismissTransition(IBinder iBinder, int i, int i2, boolean z) {
        this.mPendingDismiss = new DismissSession(this, iBinder, i2, i, z);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -2018661040764077535L, 0, String.valueOf(SplitScreenController.exitReasonToString(i2)), String.valueOf(SplitScreen.stageTypeToString(i)));
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -8087615531821482454L, 0, String.valueOf(SplitScreenController.exitReasonToString(i2)), String.valueOf(SplitScreen.stageTypeToString(i)));
        }
    }

    public final void setEnterTransition(IBinder iBinder, RemoteTransition remoteTransition, int i, boolean z, int i2) {
        this.mPendingEnter = new EnterSession(this, iBinder, remoteTransition, i, z, i2);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 7174451032024956397L, 0, null);
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -2436541215950487753L, 13, Long.valueOf(i), Boolean.valueOf(z));
        }
    }

    public final void startCustomFadeAnimation(final SurfaceControl surfaceControl, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        final float f = z ? 1.0f : 0.0f;
        float f2 = 1.0f - f;
        final SurfaceControl.Transaction transactionAcquire = this.mTransactionPool.acquire();
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f2, f);
        if (z4) {
            valueAnimatorOfFloat.setDuration((long) (this.mDurationScale * 100.0f));
            valueAnimatorOfFloat.setStartDelay((long) (this.mDurationScale * 200.0f));
        } else if (!z2 || z3) {
            valueAnimatorOfFloat.setDuration((long) (this.mDurationScale * 133.0f));
        } else {
            valueAnimatorOfFloat.setDuration((long) (this.mDurationScale * 100.0f));
            valueAnimatorOfFloat.setStartDelay((long) (this.mDurationScale * 300.0f));
            transactionAcquire.setAlpha(surfaceControl, 0.0f).apply();
            if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && z5) {
                this.mCellDividerFadeAnimation = valueAnimatorOfFloat;
            } else {
                this.mDividerFadeAnimation = valueAnimatorOfFloat;
            }
        }
        valueAnimatorOfFloat.setInterpolator(z ? Interpolators.ALPHA_IN : Interpolators.ALPHA_OUT);
        if (z3) {
            if (z2) {
                valueAnimatorOfFloat.setDuration(100L);
                this.mDividerFadeAnimation = valueAnimatorOfFloat;
            } else {
                valueAnimatorOfFloat.setDuration(300L);
                valueAnimatorOfFloat.setInterpolator(InterpolatorUtils.SINE_OUT_60);
            }
        }
        final SplitScreenTransitions$$ExternalSyntheticLambda4 splitScreenTransitions$$ExternalSyntheticLambda4 = new SplitScreenTransitions$$ExternalSyntheticLambda4(transactionAcquire, surfaceControl, f2, f, 1);
        valueAnimatorOfFloat.addUpdateListener(splitScreenTransitions$$ExternalSyntheticLambda4);
        final Runnable runnable = new Runnable() { // from class: com.android.wm.shell.splitscreen.SplitScreenTransitions$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                SplitScreenTransitions splitScreenTransitions = this.f$0;
                SurfaceControl.Transaction transaction = transactionAcquire;
                SurfaceControl surfaceControl2 = surfaceControl;
                float f3 = f;
                ValueAnimator valueAnimator = valueAnimatorOfFloat;
                splitScreenTransitions.getClass();
                transaction.setAlpha(surfaceControl2, f3);
                transaction.apply();
                splitScreenTransitions.mTransactionPool.release(transaction);
                splitScreenTransitions.mTransitions.mMainExecutor.execute(new SplitScreenTransitions$$ExternalSyntheticLambda13(splitScreenTransitions, valueAnimator, 0));
            }
        };
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.splitscreen.SplitScreenTransitions.2
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
                valueAnimatorOfFloat.removeUpdateListener(splitScreenTransitions$$ExternalSyntheticLambda4);
                SplitScreenTransitions splitScreenTransitions = SplitScreenTransitions.this;
                if (splitScreenTransitions.mDividerFadeAnimation != null) {
                    splitScreenTransitions.mDividerFadeAnimation = null;
                }
                if (!CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER || splitScreenTransitions.mCellDividerFadeAnimation == null) {
                    return;
                }
                splitScreenTransitions.mCellDividerFadeAnimation = null;
            }
        });
        this.mAnimations.add(valueAnimatorOfFloat);
        StringBuilder sb = new StringBuilder("startFadeAnimation: leash=");
        sb.append(surfaceControl);
        sb.append(", show=");
        sb.append(z);
        ExifInterface$$ExternalSyntheticOutline0.m(sb, z2 ? ", isDividerChange=true" : "", "SplitScreenTransitions");
    }

    public final IBinder startDismissTransition(WindowContainerTransaction windowContainerTransaction, Transitions.TransitionHandler transitionHandler, int i, int i2, boolean z) {
        if (this.mPendingDismiss != null) {
            if (!ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                return null;
            }
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -954926772565557968L, 0, String.valueOf(SplitScreenController.exitReasonToString(i2)));
            return null;
        }
        int i3 = i2 == 4 ? 1006 : 1007;
        if (CoreRune.MW_FREEFORM_SHELL_TRANSITION && windowContainerTransaction.isDismissSplitWithFreeform()) {
            i3 = VolteConstants.ErrorCode.CALL_STATUS_CONF_START_SESSION_FAILURE;
        }
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION && windowContainerTransaction.isDismissSplitWithAllApps()) {
            i3 = VolteConstants.ErrorCode.CALL_STATUS_CONF_ADD_USER_TO_SESSION_FAILURE;
        }
        IBinder iBinderStartTransition = this.mTransitions.startTransition(i3, windowContainerTransaction, transitionHandler);
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            setDismissTransition(iBinderStartTransition, i, i2, z);
            return iBinderStartTransition;
        }
        setDismissTransition(iBinderStartTransition, i, i2, false);
        return iBinderStartTransition;
    }

    public final void startEnterTransition(WindowContainerTransaction windowContainerTransaction, RemoteTransition remoteTransition, Transitions.TransitionHandler transitionHandler, int i, boolean z, int i2) {
        if (this.mPendingEnter != null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -6020188994636268501L, 0, null);
            }
            if (!CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                return;
            }
            EnterSession enterSession = this.mPendingEnter;
            enterSession.getClass();
            if (SystemClock.uptimeMillis() - enterSession.mPendingStartedTime <= 5000) {
                return;
            }
            Log.d("SplitScreenTransitions", "ignore to skip pending enter forcibly, pending=" + this.mPendingEnter.mTransition + ", transition=" + this.mAnimatingTransition);
            onFinish(null);
            this.mPendingEnter = null;
        }
        Executor executor = this.mSplitInvocationListenerExecutor;
        if (executor != null && this.mSplitInvocationListener != null) {
            executor.execute(new SplitScreenTransitions$$ExternalSyntheticLambda0(this, 0));
        }
        setEnterTransition(this.mTransitions.startTransition(3, windowContainerTransaction, transitionHandler), remoteTransition, i, z, i2);
    }

    public final void startFadeAnimation(SurfaceControl surfaceControl) {
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
            startCustomFadeAnimation(surfaceControl, false, false, false, false, false);
            return;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimatorOfFloat.setDuration(133L);
        valueAnimatorOfFloat.setInterpolator(Interpolators.ALPHA_OUT);
        valueAnimatorOfFloat.addUpdateListener(new SplitScreenTransitions$$ExternalSyntheticLambda4(this, surfaceControl, 1.0f, 0.0f, 0));
        valueAnimatorOfFloat.addListener(new AnonymousClass1(surfaceControl, 0.0f, valueAnimatorOfFloat));
        this.mAnimations.add(valueAnimatorOfFloat);
        this.mTransitions.mAnimExecutor.execute(new SplitScreenTransitions$$ExternalSyntheticLambda2(valueAnimatorOfFloat));
    }

    public final void startFullscreenTransition(WindowContainerTransaction windowContainerTransaction, RemoteTransition remoteTransition) {
        Transitions transitions = this.mTransitions;
        OneShotRemoteHandler oneShotRemoteHandler = new OneShotRemoteHandler(transitions.mMainExecutor, remoteTransition);
        oneShotRemoteHandler.mTransition = transitions.startTransition(1, windowContainerTransaction, oneShotRemoteHandler);
    }

    public final void startResizeTransition(WindowContainerTransaction windowContainerTransaction, Transitions.TransitionHandler transitionHandler, TransitionConsumedCallback transitionConsumedCallback, TransitionFinishedCallback transitionFinishedCallback, SplitDecorManager splitDecorManager, SplitDecorManager splitDecorManager2) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 4324715850741661479L, 0, null);
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 8318624295462056497L, 3, Boolean.valueOf(this.mPendingResize != null));
        }
        TransitSession transitSession = this.mPendingResize;
        if (transitSession != null) {
            transitSession.mCanceled = true;
            transitSession.mFinishedCallback = null;
            splitDecorManager.cancelRunningAnimations();
            splitDecorManager2.cancelRunningAnimations();
            this.mAnimations.clear();
            onFinish(null);
        }
        this.mPendingResize = new TransitSession(this, this.mTransitions.startTransition(6, windowContainerTransaction, transitionHandler), transitionConsumedCallback, transitionFinishedCallback);
    }
}
