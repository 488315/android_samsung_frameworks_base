package com.android.wm.shell.back;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.window.BackAnimationAdapter;
import android.window.BackMotionEvent;
import android.window.BackNavigationInfo;
import android.window.BackTouchTracker;
import android.window.DesktopExperienceFlags;
import android.window.IBackAnimationFinishedCallback;
import android.window.IBackAnimationHandoffHandler;
import android.window.IBackAnimationRunner;
import android.window.IOnBackInvokedCallback;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowAnimationState;
import android.window.WindowContainerTransaction;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.R;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda10;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.back.BackAnimationRunner;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.sysui.ConfigurationChangeListener;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BackAnimationController implements RemoteCallable, ConfigurationChangeListener {
    public IOnBackInvokedCallback mActiveCallback;
    public final IActivityTaskManager mActivityTaskManager;
    public final BackAnimationBackground mAnimationBackground;
    public final BackAnimationController$$ExternalSyntheticLambda2 mAnimationTimeoutRunnable;
    RemoteAnimationTarget[] mApps;
    public final BackAnimationImpl mBackAnimation;
    BackAnimationAdapter mBackAnimationAdapter;
    public IBackAnimationFinishedCallback mBackAnimationFinishedCallback;
    public boolean mBackGestureStarted;
    public BackNavigationInfo mBackNavigationInfo;
    final BackTransitionHandler mBackTransitionHandler;
    public final BackTransitionObserver mBackTransitionObserver;
    public final Context mContext;
    public BackTouchTracker mCurrentTracker;
    public EdgeBackGestureHandler$$ExternalSyntheticLambda10 mCustomizer;
    public final Handler mHandler;
    public final AnonymousClass2 mHandoffHandler;
    public final LatencyTracker mLatencyTracker;
    final RemoteCallback mNavigationObserver;
    public boolean mOnBackStartDispatched;
    public Runnable mPilferPointerCallback;
    public boolean mPointersPilfered;
    public boolean mPostCommitAnimationInProgress;
    public int mPreviousNavigationType;
    public BackTouchTracker mQueuedTracker;
    public boolean mRealCallbackInvoked;
    public boolean mReceivedNullNavigationInfo;
    public EdgeBackGestureHandler$$ExternalSyntheticLambda10 mRequestTopUiCallback;
    public final boolean mRequirePointerPilfer;
    public final ShellBackAnimationRegistry mShellBackAnimationRegistry;
    public final ShellCommandHandler mShellCommandHandler;
    public final ShellController mShellController;
    public final ShellExecutor mShellExecutor;
    public boolean mShouldStartOnNextMoveEvent;
    public boolean mThresholdCrossed;
    final Rect mTouchableArea;
    public boolean mTrackingLatency;
    public final WindowManager mWindowManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.back.BackAnimationController$1, reason: invalid class name */
    public class AnonymousClass1 implements RemoteCallback.OnResultListener {
        public AnonymousClass1() {
        }

        public final void onResult(Bundle bundle) {
            BackAnimationController.this.mShellExecutor.execute(new BackAnimationController$1$$ExternalSyntheticLambda0(0, this, bundle));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.back.BackAnimationController$3, reason: invalid class name */
    public class AnonymousClass3 extends IBackAnimationRunner.Stub {
        public AnonymousClass3() {
        }

        public final void onAnimationCancelled() {
            BackAnimationController.this.mShellExecutor.execute(new BackAnimationController$3$$ExternalSyntheticLambda1(this, 0));
        }

        public final void onAnimationStart(final RemoteAnimationTarget[] remoteAnimationTargetArr, final IBinder iBinder, final IBackAnimationFinishedCallback iBackAnimationFinishedCallback) {
            BackAnimationController.this.mShellExecutor.execute(new Runnable() { // from class: com.android.wm.shell.back.BackAnimationController$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BackAnimationController.AnonymousClass3 anonymousClass3 = BackAnimationController.AnonymousClass3.this;
                    RemoteAnimationTarget[] remoteAnimationTargetArr2 = remoteAnimationTargetArr;
                    IBackAnimationFinishedCallback iBackAnimationFinishedCallback2 = iBackAnimationFinishedCallback;
                    IBinder iBinder2 = iBinder;
                    BackAnimationController backAnimationController = BackAnimationController.this;
                    if (backAnimationController.mTrackingLatency) {
                        backAnimationController.mLatencyTracker.onActionEnd(25);
                        backAnimationController.mTrackingLatency = false;
                    }
                    if (!BackAnimationController.validateAnimationTargets(remoteAnimationTargetArr2)) {
                        Log.e("ShellBackPreview", "Invalid animation targets!");
                        return;
                    }
                    BackAnimationController backAnimationController2 = BackAnimationController.this;
                    backAnimationController2.mBackAnimationFinishedCallback = iBackAnimationFinishedCallback2;
                    backAnimationController2.mApps = remoteAnimationTargetArr2;
                    if (iBinder2 != null) {
                        return;
                    }
                    backAnimationController2.startSystemAnimation();
                    backAnimationController2.dispatchOnBackProgressed(backAnimationController2.mActiveCallback, backAnimationController2.mCurrentTracker.createProgressEvent());
                    if (backAnimationController2.mCurrentTracker.isFinished()) {
                        backAnimationController2.startPostCommitAnimation();
                    }
                }
            });
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BackAnimationImpl {
        public /* synthetic */ BackAnimationImpl(BackAnimationController backAnimationController, int i) {
            this();
        }

        public final void onBackMotion(final int i, final int i2, final int i3, final float f, final float f2) {
            BackAnimationController.this.mShellExecutor.execute(new Runnable() { // from class: com.android.wm.shell.back.BackAnimationController$BackAnimationImpl$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    BackAnimationController.BackAnimationImpl backAnimationImpl = BackAnimationController.BackAnimationImpl.this;
                    float f3 = f;
                    float f4 = f2;
                    int i4 = i;
                    int i5 = i2;
                    int i6 = i3;
                    BackAnimationController backAnimationController = BackAnimationController.this;
                    backAnimationController.mBackAnimationAdapter.mOriginDisplayId = i6;
                    BackTouchTracker activeTracker = backAnimationController.getActiveTracker();
                    if (activeTracker != null) {
                        activeTracker.update(f3, f4);
                    }
                    if (backAnimationController.mCurrentTracker.isFinished() && backAnimationController.mQueuedTracker.isFinished()) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -2731934872007265096L, 0, null);
                            return;
                        }
                        return;
                    }
                    if (backAnimationController.mBackGestureStarted && backAnimationController.mCurrentTracker.isInInitialState() && backAnimationController.mQueuedTracker.isInInitialState()) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[4]) {
                            ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 5984358004837069330L, 0, null);
                        }
                        backAnimationController.mBackGestureStarted = false;
                    }
                    if (i4 == 0) {
                        if (backAnimationController.mBackGestureStarted) {
                            return;
                        }
                        if (i5 != 2) {
                            backAnimationController.mShouldStartOnNextMoveEvent = true;
                            return;
                        }
                        backAnimationController.mThresholdCrossed = true;
                        backAnimationController.mPointersPilfered = true;
                        backAnimationController.onGestureStarted(f3, f4, i5);
                        backAnimationController.mShouldStartOnNextMoveEvent = false;
                        return;
                    }
                    if (i4 == 2) {
                        if (!backAnimationController.mBackGestureStarted && backAnimationController.mShouldStartOnNextMoveEvent) {
                            backAnimationController.onGestureStarted(f3, f4, i5);
                            backAnimationController.mShouldStartOnNextMoveEvent = false;
                        }
                        if (!backAnimationController.mBackGestureStarted || backAnimationController.mBackNavigationInfo == null || backAnimationController.mActiveCallback == null || !backAnimationController.mOnBackStartDispatched || backAnimationController.mQueuedTracker.isActive()) {
                            return;
                        }
                        backAnimationController.dispatchOnBackProgressed(backAnimationController.mActiveCallback, backAnimationController.mCurrentTracker.createProgressEvent());
                        return;
                    }
                    if (i4 == 1 || i4 == 3) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 8967565580839694775L, 1, Long.valueOf(i4));
                        }
                        if (i4 == 3) {
                            backAnimationController.setTriggerBack(false);
                        }
                        BackTouchTracker activeTracker2 = backAnimationController.getActiveTracker();
                        if (!backAnimationController.mBackGestureStarted || activeTracker2 == null) {
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
                                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -250489720305009514L, 0, null);
                                return;
                            }
                            return;
                        }
                        boolean triggerBack = activeTracker2.getTriggerBack();
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 1940238233150763812L, 0, String.valueOf(triggerBack));
                        }
                        if (triggerBack) {
                            BackNavigationInfo backNavigationInfo = backAnimationController.mBackNavigationInfo;
                            backAnimationController.mBackTransitionObserver.mFocusedTaskId = backNavigationInfo != null ? backNavigationInfo.getFocusedTaskId() : -1;
                        }
                        backAnimationController.mThresholdCrossed = false;
                        backAnimationController.mPointersPilfered = false;
                        backAnimationController.mBackGestureStarted = false;
                        activeTracker2.setState(BackTouchTracker.TouchTrackerState.FINISHED);
                        if (backAnimationController.mPostCommitAnimationInProgress) {
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[3]) {
                                ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 6833772699392816046L, 0, null);
                                return;
                            }
                            return;
                        }
                        BackNavigationInfo backNavigationInfo2 = backAnimationController.mBackNavigationInfo;
                        if (backNavigationInfo2 == null) {
                            if (!backAnimationController.mQueuedTracker.isInInitialState() && ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[4]) {
                                ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 5250769310321425521L, 0, null);
                            }
                            backAnimationController.mCurrentTracker.reset();
                            if (triggerBack) {
                                int i7 = backAnimationController.mBackAnimationAdapter.mOriginDisplayId;
                                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
                                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -1127091700925973460L, 0, null);
                                }
                                backAnimationController.sendBackEvent(0, i7);
                                backAnimationController.sendBackEvent(1, i7);
                            }
                            backAnimationController.finishBackNavigation(triggerBack);
                            return;
                        }
                        int type = backNavigationInfo2.getType();
                        if (backAnimationController.shouldDispatchToAnimator()) {
                            ShellBackAnimationRegistry shellBackAnimationRegistry = backAnimationController.mShellBackAnimationRegistry;
                            BackAnimationRunner backAnimationRunner = (BackAnimationRunner) shellBackAnimationRegistry.mAnimationDefinition.get(type);
                            if (!(backAnimationRunner == null ? true : backAnimationRunner.mAnimationCancelled)) {
                                BackAnimationRunner backAnimationRunner2 = (BackAnimationRunner) shellBackAnimationRegistry.mAnimationDefinition.get(type);
                                if (!(backAnimationRunner2 == null ? false : backAnimationRunner2.mWaitingAnimation)) {
                                    backAnimationController.startPostCommitAnimation();
                                    return;
                                }
                                if (CoreRune.FW_PREDICTIVE_BACK_ANIM_OPTIONAL_DELAY && type == 1) {
                                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[3]) {
                                        ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 4108187327729713059L, 0, null);
                                    }
                                    backAnimationController.finishBackAnimation();
                                    return;
                                } else {
                                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[3]) {
                                        ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -8669768627275025840L, 0, null);
                                    }
                                    ((HandlerExecutor) backAnimationController.mShellExecutor).executeDelayed(backAnimationController.mAnimationTimeoutRunnable, DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
                                    return;
                                }
                            }
                        }
                        if (CoreRune.FW_PREDICTIVE_BACK_ANIM_OPTIONAL_DELAY) {
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
                                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -9176911455426233342L, 0, String.valueOf(backAnimationController.mBackNavigationInfo));
                            }
                        } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -2719961778730356545L, 0, null);
                        }
                        backAnimationController.invokeOrCancelBack(backAnimationController.mCurrentTracker);
                        backAnimationController.mCurrentTracker.reset();
                    }
                }
            });
        }

        public final void setTriggerBack(final boolean z) {
            BackAnimationController.this.mShellExecutor.execute(new Runnable() { // from class: com.android.wm.shell.back.BackAnimationController$BackAnimationImpl$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    BackAnimationController.BackAnimationImpl backAnimationImpl = BackAnimationController.BackAnimationImpl.this;
                    BackAnimationController.this.setTriggerBack(z);
                }
            });
        }

        private BackAnimationImpl() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BackTransitionHandler implements Transitions.TransitionHandler {
        public IBinder mClosePrepareTransition;
        public boolean mCloseTransitionRequested;
        public SurfaceControl.Transaction mFinishOpenTransaction;
        public Transitions.TransitionFinishCallback mFinishOpenTransitionCallback;
        public Runnable mOnAnimationFinishCallback;
        public TransitionInfo mOpenTransitionInfo;
        public IBinder mPrepareOpenTransition;
        public Transitions.TransitionHandler mTakeoverHandler;
        public final Transitions mTransitions;

        public BackTransitionHandler(Transitions transitions) {
            this.mTransitions = transitions;
        }

        public final void applyAndFinish(SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
            applyFinishOpenTransition();
            transaction.apply();
            transaction2.apply();
            transitionFinishCallback.onTransitionFinished(null);
            this.mCloseTransitionRequested = false;
        }

        public final void applyFinishOpenTransition() {
            SurfaceControl.Transaction transaction = this.mFinishOpenTransaction;
            if (transaction != null) {
                transaction.apply();
            }
            Transitions.TransitionFinishCallback transitionFinishCallback = this.mFinishOpenTransitionCallback;
            if (transitionFinishCallback != null) {
                transitionFinishCallback.onTransitionFinished(null);
            }
            this.mOpenTransitionInfo = null;
            this.mPrepareOpenTransition = null;
            this.mFinishOpenTransaction = null;
            this.mFinishOpenTransitionCallback = null;
            this.mTakeoverHandler = null;
        }

        public final void createClosePrepareTransition() {
            if (this.mClosePrepareTransition != null) {
                Log.e("ShellBackPreview", "Re-create close prepare transition");
                return;
            }
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.restoreBackNavi();
            this.mClosePrepareTransition = this.mTransitions.startTransition(14, windowContainerTransaction, BackAnimationController.this.mBackTransitionHandler);
        }

        public boolean handleCloseTransition(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, final SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback) {
            if (!this.mCloseTransitionRequested || !BackAnimationController.hasAnimationInMode(transitionInfo, new BackAnimationController$$ExternalSyntheticLambda6(1))) {
                return false;
            }
            BackAnimationController backAnimationController = BackAnimationController.this;
            RemoteAnimationTarget[] remoteAnimationTargetArr = backAnimationController.mApps;
            if (remoteAnimationTargetArr == null) {
                applyAndFinish(transaction, transaction2, transitionFinishCallback);
                return true;
            }
            SurfaceControl surfaceControl = null;
            SurfaceControl surfaceControl2 = null;
            for (int length = remoteAnimationTargetArr.length - 1; length >= 0; length--) {
                RemoteAnimationTarget remoteAnimationTarget = backAnimationController.mApps[length];
                int i = remoteAnimationTarget.mode;
                if (i == 0) {
                    surfaceControl = remoteAnimationTarget.leash;
                }
                if (i == 1) {
                    surfaceControl2 = remoteAnimationTarget.leash;
                }
            }
            if (surfaceControl != null && surfaceControl2 != null) {
                for (int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); m >= 0; m--) {
                    TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m);
                    if (change.hasFlags(2)) {
                        transaction.setAlpha(change.getLeash(), 1.0f);
                    } else if (TransitionUtil.isOpeningMode(change.getMode())) {
                        Point endRelOffset = change.getEndRelOffset();
                        transaction.setPosition(change.getLeash(), endRelOffset.x, endRelOffset.y);
                        transaction.reparent(change.getLeash(), surfaceControl);
                        transaction.setAlpha(change.getLeash(), 1.0f);
                    } else if (TransitionUtil.isClosingMode(change.getMode())) {
                        transaction.reparent(change.getLeash(), surfaceControl2);
                    }
                }
            }
            transaction.apply();
            this.mOnAnimationFinishCallback = new Runnable() { // from class: com.android.wm.shell.back.BackAnimationController$BackTransitionHandler$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    BackAnimationController.BackTransitionHandler backTransitionHandler = BackAnimationController.BackTransitionHandler.this;
                    SurfaceControl.Transaction transaction3 = transaction2;
                    Transitions.TransitionFinishCallback transitionFinishCallback2 = transitionFinishCallback;
                    backTransitionHandler.getClass();
                    transaction3.apply();
                    transitionFinishCallback2.onTransitionFinished(null);
                    backTransitionHandler.mCloseTransitionRequested = false;
                }
            };
            return true;
        }

        public boolean handlePrepareTransition(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
            SurfaceControl surfaceControl;
            if (transitionInfo.getType() != 13 || BackAnimationController.hasAnimationInMode(transitionInfo, new BackAnimationController$$ExternalSyntheticLambda6(1)) || !BackAnimationController.hasAnimationInMode(transitionInfo, new BackAnimationController$$ExternalSyntheticLambda6(2))) {
                return false;
            }
            BackAnimationController backAnimationController = BackAnimationController.this;
            RemoteAnimationTarget[] remoteAnimationTargetArr = backAnimationController.mApps;
            SurfaceControl surfaceControl2 = null;
            if (remoteAnimationTargetArr != null) {
                surfaceControl = null;
                for (int length = remoteAnimationTargetArr.length - 1; length >= 0; length--) {
                    RemoteAnimationTarget remoteAnimationTarget = backAnimationController.mApps[length];
                    int i = remoteAnimationTarget.mode;
                    if (i == 0) {
                        surfaceControl2 = remoteAnimationTarget.leash;
                    } else if (i == 1) {
                        surfaceControl = remoteAnimationTarget.leash;
                    }
                }
            } else {
                surfaceControl = null;
            }
            if (surfaceControl2 != null && surfaceControl != null) {
                int i2 = -1;
                for (int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); m >= 0; m--) {
                    TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m);
                    if (!CoreRune.FW_REMOTE_WALLPAPER_ANIM || !TransitionUtil.isWallpaper(change)) {
                        if (TransitionUtil.isOpeningMode(change.getMode())) {
                            Point endRelOffset = change.getEndRelOffset();
                            transaction.setPosition(change.getLeash(), endRelOffset.x, endRelOffset.y);
                            transaction.reparent(change.getLeash(), surfaceControl2);
                            transaction.setAlpha(change.getLeash(), 1.0f);
                            i2 = TransitionUtil.rootIndexFor(change, transitionInfo);
                        } else if (change.hasFlags(131072) && change.getMode() == 6) {
                            transaction.reparent(change.getLeash(), surfaceControl);
                        }
                    }
                }
                if (i2 >= 0 && transitionInfo.getRootCount() > 0) {
                    transaction.setLayer(transitionInfo.getRoot(i2).getLeash(), -1);
                }
            }
            transaction.apply();
            this.mPrepareOpenTransition = iBinder;
            this.mFinishOpenTransaction = transaction2;
            this.mFinishOpenTransitionCallback = transitionFinishCallback;
            this.mOpenTransitionInfo = transitionInfo;
            return true;
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
        public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
            int type = transitionRequestInfo.getType();
            if (type == 13) {
                this.mPrepareOpenTransition = iBinder;
                return new WindowContainerTransaction();
            }
            if (type == 14) {
                return new WindowContainerTransaction();
            }
            if (TransitionUtil.isClosingType(transitionRequestInfo.getType()) && this.mCloseTransitionRequested) {
                return new WindowContainerTransaction();
            }
            return null;
        }

        /* JADX WARN: Removed duplicated region for block: B:133:0x01f2  */
        /* JADX WARN: Removed duplicated region for block: B:136:0x0200 A[SYNTHETIC] */
        @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void mergeAnimation(android.os.IBinder r18, android.window.TransitionInfo r19, android.view.SurfaceControl.Transaction r20, android.view.SurfaceControl.Transaction r21, android.os.IBinder r22, com.android.wm.shell.transition.Transitions.TransitionFinishCallback r23) {
            /*
                Method dump skipped, instructions count: 701
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.back.BackAnimationController.BackTransitionHandler.mergeAnimation(android.os.IBinder, android.window.TransitionInfo, android.view.SurfaceControl$Transaction, android.view.SurfaceControl$Transaction, android.os.IBinder, com.android.wm.shell.transition.Transitions$TransitionFinishCallback):void");
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
        public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
            SurfaceControl.Transaction transaction2;
            if (iBinder == this.mClosePrepareTransition && z) {
                this.mClosePrepareTransition = null;
                applyFinishOpenTransition();
            } else {
                if (z || (transaction2 = this.mFinishOpenTransaction) == null || transaction == null) {
                    return;
                }
                transaction2.merge(transaction);
            }
        }

        public final boolean shouldCancelAnimation(TransitionInfo transitionInfo) {
            boolean z = false;
            boolean z2 = !this.mCloseTransitionRequested && transitionInfo.getType() == 13;
            boolean z3 = false;
            boolean z4 = false;
            for (int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); m >= 0; m--) {
                TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m);
                boolean hasFlags = change.hasFlags(131072);
                if (!hasFlags && !change.hasFlags(2)) {
                    z3 = true;
                    z4 = true;
                } else if (z2 && hasFlags && TransitionUtil.isClosingMode(change.getMode())) {
                    z3 = true;
                }
            }
            if (!z3) {
                return false;
            }
            if (z4 && (TransitionUtil.isOpeningType(transitionInfo.getType()) || TransitionUtil.isClosingType(transitionInfo.getType()))) {
                for (int m2 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); m2 >= 0; m2--) {
                    TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(m2);
                    if (change2.hasFlags(131072) && TransitionUtil.isOpeningMode(change2.getMode())) {
                        transitionInfo.getChanges().remove(m2);
                        z |= change2.hasFlags(1);
                    }
                }
                if (z) {
                    for (int m3 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); m3 >= 0; m3--) {
                        if (((TransitionInfo.Change) transitionInfo.getChanges().get(m3)).hasFlags(2)) {
                            transitionInfo.getChanges().remove(m3);
                        }
                    }
                }
            }
            return true;
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
        public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
            Transitions.TransitionHandler transitionHandler = null;
            int i = 0;
            if (transitionInfo.getType() == 14) {
                if (this.mClosePrepareTransition != null) {
                    this.mClosePrepareTransition = null;
                    applyAndFinish(transaction, transaction2, transitionFinishCallback);
                    return true;
                }
            } else if (transitionInfo.getType() == 13 || BackAnimationController.hasAnimationInMode(transitionInfo, new BackAnimationController$$ExternalSyntheticLambda6(0))) {
                if (shouldCancelAnimation(transitionInfo)) {
                    this.mPrepareOpenTransition = null;
                    return false;
                }
                BackAnimationController backAnimationController = BackAnimationController.this;
                RemoteAnimationTarget[] remoteAnimationTargetArr = backAnimationController.mApps;
                if (remoteAnimationTargetArr == null || remoteAnimationTargetArr.length == 0) {
                    if (this.mCloseTransitionRequested) {
                        applyAndFinish(transaction, transaction2, transitionFinishCallback);
                        return true;
                    }
                    if (this.mClosePrepareTransition == null && transitionInfo.getType() == 13) {
                        createClosePrepareTransition();
                    }
                }
                if (!handlePrepareTransition(iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback)) {
                    return handleCloseTransition(transitionInfo, transaction, transaction2, transitionFinishCallback);
                }
                TransitionAnimator.Companion.getClass();
                ArrayList arrayList = this.mTransitions.mHandlers;
                int size = arrayList.size();
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    Object obj = arrayList.get(i);
                    i++;
                    Transitions.TransitionHandler handlerForTakeover = ((Transitions.TransitionHandler) obj).getHandlerForTakeover(iBinder, transitionInfo);
                    if (handlerForTakeover != null) {
                        transitionHandler = handlerForTakeover;
                        break;
                    }
                }
                this.mTakeoverHandler = transitionHandler;
                backAnimationController.startSystemAnimation();
                backAnimationController.dispatchOnBackProgressed(backAnimationController.mActiveCallback, backAnimationController.mCurrentTracker.createProgressEvent());
                if (backAnimationController.mCurrentTracker.isFinished()) {
                    backAnimationController.startPostCommitAnimation();
                }
                return true;
            }
            return false;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BackTransitionObserver implements Transitions.TransitionObserver {
        public BackTransitionHandler mBackTransitionHandler;
        public IBinder mFocusTaskMonitorToken;
        public int mFocusedTaskId = -1;

        @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
        public final void onTransitionFinished(IBinder iBinder, boolean z) {
            if (this.mFocusTaskMonitorToken == iBinder) {
                this.mFocusedTaskId = -1;
            }
            BackTransitionHandler backTransitionHandler = this.mBackTransitionHandler;
            if (backTransitionHandler.mClosePrepareTransition == iBinder) {
                backTransitionHandler.mClosePrepareTransition = null;
            }
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
        public final void onTransitionMerged(IBinder iBinder, IBinder iBinder2) {
            if (this.mFocusTaskMonitorToken == iBinder) {
                this.mFocusTaskMonitorToken = iBinder2;
            }
            BackTransitionHandler backTransitionHandler = this.mBackTransitionHandler;
            if (backTransitionHandler.mClosePrepareTransition == iBinder) {
                backTransitionHandler.mClosePrepareTransition = null;
            }
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
        public final void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
            if (this.mFocusedTaskId == -1) {
                return;
            }
            int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1);
            while (true) {
                if (m < 0) {
                    break;
                }
                TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m);
                if (change.getTaskInfo() != null && change.getTaskInfo().taskId == this.mFocusedTaskId) {
                    this.mFocusTaskMonitorToken = iBinder;
                    break;
                }
                m--;
            }
            if (this.mFocusTaskMonitorToken == null) {
                this.mFocusedTaskId = -1;
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class IBackAnimationImpl extends IBackAnimation$Stub implements ExternalInterfaceBinder {
        public static final /* synthetic */ int $r8$clinit = 0;
        public BackAnimationController mController;

        public IBackAnimationImpl(BackAnimationController backAnimationController) {
            this.mController = backAnimationController;
        }

        @Override // com.android.wm.shell.common.ExternalInterfaceBinder
        public final void invalidate() {
            this.mController = null;
        }
    }

    public BackAnimationController(ShellInit shellInit, ShellController shellController, ShellExecutor shellExecutor, Context context, BackAnimationBackground backAnimationBackground, ShellBackAnimationRegistry shellBackAnimationRegistry, ShellCommandHandler shellCommandHandler, Transitions transitions, Handler handler) {
        this(shellInit, shellController, shellExecutor, ActivityTaskManager.getService(), context, backAnimationBackground, shellBackAnimationRegistry, shellCommandHandler, transitions, handler);
    }

    public static void dispatchOnBackInvoked(IOnBackInvokedCallback iOnBackInvokedCallback) {
        if (iOnBackInvokedCallback == null) {
            return;
        }
        try {
            if (CoreRune.FW_PREDICTIVE_BACK_ANIM_LOG) {
                Log.d("ShellBackPreview", "dispatchOnBackInvoked, caller=" + Debug.getCallers(3));
            }
            iOnBackInvokedCallback.onBackInvoked();
        } catch (RemoteException e) {
            Log.e("ShellBackPreview", "dispatchOnBackInvoked error: ", e);
        }
    }

    public static boolean hasAnimationInMode(TransitionInfo transitionInfo, Predicate predicate) {
        for (int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); m >= 0; m--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m);
            if (change.hasFlags(131072) && predicate.test(Integer.valueOf(change.getMode()))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOpenSurfaceMatched(ArrayList arrayList, TransitionInfo.Change change) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (((SurfaceControl) arrayList.get(size)).isSameSurface(change.getLeash())) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateAnimationTargets(RemoteAnimationTarget[] remoteAnimationTargetArr) {
        if (remoteAnimationTargetArr == null || remoteAnimationTargetArr.length == 0) {
            return false;
        }
        for (int length = remoteAnimationTargetArr.length - 1; length >= 0; length--) {
            if (!remoteAnimationTargetArr[length].leash.isValid()) {
                return false;
            }
        }
        return true;
    }

    public final void cancelLatencyTracking() {
        if (this.mTrackingLatency) {
            this.mLatencyTracker.onActionCancel(25);
            this.mTrackingLatency = false;
        }
    }

    public final void dispatchOnBackProgressed(IOnBackInvokedCallback iOnBackInvokedCallback, BackMotionEvent backMotionEvent) {
        if (iOnBackInvokedCallback != null) {
            if (shouldDispatchToAnimator() || this.mBackNavigationInfo == null || !isAppProgressGenerationAllowed()) {
                try {
                    iOnBackInvokedCallback.onBackProgressed(backMotionEvent);
                } catch (RemoteException e) {
                    Log.e("ShellBackPreview", "dispatchOnBackProgressed error: ", e);
                }
            }
        }
    }

    public final void dispatchOnBackStarted(IOnBackInvokedCallback iOnBackInvokedCallback, BackMotionEvent backMotionEvent) {
        if (iOnBackInvokedCallback == null) {
            return;
        }
        try {
            if (CoreRune.FW_PREDICTIVE_BACK_ANIM_LOG) {
                Log.i("ShellBackPreview", "dispatchOnBackStarted, caller=" + Debug.getCallers(3));
            }
            iOnBackInvokedCallback.onBackStarted(backMotionEvent);
            BackTransitionHandler backTransitionHandler = this.mBackTransitionHandler;
            backTransitionHandler.getClass();
            TransitionAnimator.Companion.getClass();
            if (backTransitionHandler.mTakeoverHandler != null) {
                iOnBackInvokedCallback.setHandoffHandler(this.mHandoffHandler);
            } else {
                iOnBackInvokedCallback.setHandoffHandler((IBackAnimationHandoffHandler) null);
            }
            this.mOnBackStartDispatched = true;
        } catch (RemoteException e) {
            Log.e("ShellBackPreview", "dispatchOnBackStarted error: ", e);
        }
    }

    public final void finishBackAnimation() {
        ((HandlerExecutor) this.mShellExecutor).removeCallbacks(this.mAnimationTimeoutRunnable);
        this.mPostCommitAnimationInProgress = false;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -8055404622877214646L, 0, null);
        }
        if (this.mCurrentTracker.isActive() || this.mCurrentTracker.isFinished()) {
            invokeOrCancelBack(this.mCurrentTracker);
        } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -4972748429022513286L, 0, null);
        }
        resetTouchTracker();
        BackTransitionHandler backTransitionHandler = this.mBackTransitionHandler;
        if (!backTransitionHandler.mCloseTransitionRequested && backTransitionHandler.mPrepareOpenTransition != null) {
            backTransitionHandler.createClosePrepareTransition();
        }
        Runnable runnable = backTransitionHandler.mOnAnimationFinishCallback;
        if (runnable != null) {
            runnable.run();
            backTransitionHandler.mOnAnimationFinishCallback = null;
        }
    }

    public void finishBackNavigation(boolean z) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 2915969425923977423L, 0, null);
        }
        this.mActiveCallback = null;
        this.mApps = null;
        this.mOnBackStartDispatched = false;
        this.mThresholdCrossed = false;
        this.mPointersPilfered = false;
        ShellBackAnimationRegistry shellBackAnimationRegistry = this.mShellBackAnimationRegistry;
        if (shellBackAnimationRegistry.mDefaultCrossActivityAnimation != null && shellBackAnimationRegistry.mAnimationDefinition.contains(2)) {
            shellBackAnimationRegistry.mAnimationDefinition.set(2, shellBackAnimationRegistry.mDefaultCrossActivityAnimation.getRunner());
        }
        cancelLatencyTracking();
        this.mReceivedNullNavigationInfo = false;
        BackNavigationInfo backNavigationInfo = this.mBackNavigationInfo;
        if (backNavigationInfo != null) {
            this.mPreviousNavigationType = backNavigationInfo.getType();
            this.mBackNavigationInfo.onBackNavigationFinished(z);
            this.mBackNavigationInfo = null;
            int i = this.mPreviousNavigationType;
            EdgeBackGestureHandler$$ExternalSyntheticLambda10 edgeBackGestureHandler$$ExternalSyntheticLambda10 = this.mRequestTopUiCallback;
            if (edgeBackGestureHandler$$ExternalSyntheticLambda10 != null) {
                if (i == 3 || i == 2) {
                    edgeBackGestureHandler$$ExternalSyntheticLambda10.requestTopUi(false);
                }
            }
        }
    }

    public final BackTouchTracker getActiveTracker() {
        if (this.mCurrentTracker.isActive()) {
            return this.mCurrentTracker;
        }
        if (this.mQueuedTracker.isActive()) {
            return this.mQueuedTracker;
        }
        return null;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final Context getContext() {
        return this.mContext;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final ShellExecutor getRemoteCallExecutor() {
        return this.mShellExecutor;
    }

    public final void invokeOrCancelBack(BackTouchTracker backTouchTracker) {
        IBackAnimationFinishedCallback iBackAnimationFinishedCallback = this.mBackAnimationFinishedCallback;
        if (iBackAnimationFinishedCallback != null) {
            try {
                iBackAnimationFinishedCallback.onAnimationFinished(backTouchTracker.getTriggerBack());
            } catch (RemoteException e) {
                Log.e("ShellBackPreview", "Failed call IBackAnimationFinishedCallback", e);
            }
            this.mBackAnimationFinishedCallback = null;
        }
        BackNavigationInfo backNavigationInfo = this.mBackNavigationInfo;
        if (backNavigationInfo != null && !this.mRealCallbackInvoked) {
            IOnBackInvokedCallback onBackInvokedCallback = backNavigationInfo.getOnBackInvokedCallback();
            if (backTouchTracker.getTriggerBack()) {
                dispatchOnBackInvoked(onBackInvokedCallback);
            } else {
                tryDispatchOnBackCancelled(onBackInvokedCallback);
            }
        }
        this.mRealCallbackInvoked = false;
        finishBackNavigation(backTouchTracker.getTriggerBack());
    }

    public final boolean isAppProgressGenerationAllowed() {
        return this.mBackNavigationInfo.isAppProgressGenerationAllowed() && this.mBackNavigationInfo.getTouchableRegion().equals(this.mTouchableArea);
    }

    public void onBackAnimationFinished() {
        if (this.mPostCommitAnimationInProgress) {
            finishBackAnimation();
        }
    }

    public final void onBackNavigationInfoReceived(BackNavigationInfo backNavigationInfo, BackTouchTracker backTouchTracker) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -2720921649798499212L, 0, String.valueOf(backNavigationInfo));
        }
        if (backNavigationInfo == null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[4]) {
                ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -5836110555656668314L, 0, null);
            }
            this.mReceivedNullNavigationInfo = true;
            cancelLatencyTracking();
            tryPilferPointers();
            return;
        }
        int type = backNavigationInfo.getType();
        if (shouldDispatchToAnimator()) {
            BackAnimationRunner backAnimationRunner = (BackAnimationRunner) this.mShellBackAnimationRegistry.mAnimationDefinition.get(type);
            if (backAnimationRunner == null) {
                this.mActiveCallback = null;
            } else {
                backAnimationRunner.mWaitingAnimation = true;
                backAnimationRunner.mAnimationCancelled = false;
            }
            EdgeBackGestureHandler$$ExternalSyntheticLambda10 edgeBackGestureHandler$$ExternalSyntheticLambda10 = this.mRequestTopUiCallback;
            if (edgeBackGestureHandler$$ExternalSyntheticLambda10 != null && (type == 3 || type == 2)) {
                edgeBackGestureHandler$$ExternalSyntheticLambda10.requestTopUi(true);
            }
            tryPilferPointers();
            return;
        }
        this.mActiveCallback = this.mBackNavigationInfo.getOnBackInvokedCallback();
        cancelLatencyTracking();
        IOnBackInvokedCallback iOnBackInvokedCallback = this.mActiveCallback;
        BackMotionEvent createStartEvent = backTouchTracker.createStartEvent((RemoteAnimationTarget) null);
        if (!this.mOnBackStartDispatched && iOnBackInvokedCallback != null && (this.mThresholdCrossed || !this.mRequirePointerPilfer)) {
            dispatchOnBackStarted(iOnBackInvokedCallback, createStartEvent);
        }
        if (isAppProgressGenerationAllowed()) {
            return;
        }
        tryPilferPointers();
    }

    @Override // com.android.wm.shell.sysui.ConfigurationChangeListener
    public final void onConfigurationChanged(Configuration configuration) {
        ShellBackAnimationRegistry shellBackAnimationRegistry = this.mShellBackAnimationRegistry;
        ShellBackAnimation shellBackAnimation = shellBackAnimationRegistry.mCustomizeActivityAnimation;
        if (shellBackAnimation != null) {
            shellBackAnimation.onConfigurationChanged();
        }
        ShellBackAnimation shellBackAnimation2 = shellBackAnimationRegistry.mDefaultCrossActivityAnimation;
        if (shellBackAnimation2 != null) {
            shellBackAnimation2.onConfigurationChanged();
        }
        ShellBackAnimation shellBackAnimation3 = shellBackAnimationRegistry.mCrossTaskAnimation;
        if (shellBackAnimation3 != null) {
            shellBackAnimation3.onConfigurationChanged();
        }
        this.mTouchableArea.set(this.mWindowManager.getCurrentWindowMetrics().getBounds());
    }

    public final void onGestureStarted(float f, float f2, int i) {
        BackTouchTracker backTouchTracker;
        boolean z = this.mPostCommitAnimationInProgress && this.mCurrentTracker.isFinished() && !this.mCurrentTracker.getTriggerBack() && this.mQueuedTracker.isInInitialState();
        if (z) {
            resetTouchTracker();
        }
        if (this.mCurrentTracker.isInInitialState()) {
            backTouchTracker = this.mCurrentTracker;
        } else {
            if (!this.mQueuedTracker.isInInitialState()) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[3]) {
                    ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -4950819966474305888L, 0, null);
                    return;
                }
                return;
            }
            backTouchTracker = this.mQueuedTracker;
        }
        backTouchTracker.setGestureStartLocation(f, f2, i);
        backTouchTracker.setState(BackTouchTracker.TouchTrackerState.ACTIVE);
        this.mBackGestureStarted = true;
        if (z) {
            this.mPostCommitAnimationInProgress = false;
            ((HandlerExecutor) this.mShellExecutor).removeCallbacks(this.mAnimationTimeoutRunnable);
            startSystemAnimation();
            return;
        }
        if (backTouchTracker != this.mCurrentTracker) {
            return;
        }
        try {
            if (this.mTrackingLatency) {
                cancelLatencyTracking();
            }
            this.mLatencyTracker.onActionStart(25);
            this.mTrackingLatency = true;
            BackAnimationAdapter backAnimationAdapter = this.mBackAnimationAdapter;
            if (backAnimationAdapter != null) {
                ShellBackAnimationRegistry shellBackAnimationRegistry = this.mShellBackAnimationRegistry;
                if (shellBackAnimationRegistry.mSupportedAnimatorsChanged) {
                    shellBackAnimationRegistry.mSupportedAnimatorsChanged = false;
                    backAnimationAdapter.updateSupportedAnimators(shellBackAnimationRegistry.mSupportedAnimators);
                }
            }
            BackNavigationInfo startBackNavigation = this.mActivityTaskManager.startBackNavigation(this.mNavigationObserver, this.mBackAnimationAdapter);
            this.mBackNavigationInfo = startBackNavigation;
            onBackNavigationInfoReceived(startBackNavigation, backTouchTracker);
        } catch (RemoteException e) {
            Log.e("ShellBackPreview", "Failed to initAnimation", e);
            finishBackNavigation(backTouchTracker.getTriggerBack());
        }
    }

    public void onThresholdCrossed() {
        this.mThresholdCrossed = true;
        getActiveTracker();
        if (this.mBackNavigationInfo == null && this.mReceivedNullNavigationInfo) {
            tryPilferPointers();
            return;
        }
        boolean shouldDispatchToAnimator = shouldDispatchToAnimator();
        if (shouldDispatchToAnimator || this.mActiveCallback == null) {
            if (shouldDispatchToAnimator) {
                tryPilferPointers();
                return;
            }
            return;
        }
        this.mCurrentTracker.updateStartLocation();
        IOnBackInvokedCallback iOnBackInvokedCallback = this.mActiveCallback;
        BackMotionEvent createStartEvent = this.mCurrentTracker.createStartEvent((RemoteAnimationTarget) null);
        if (!this.mOnBackStartDispatched && iOnBackInvokedCallback != null && (this.mThresholdCrossed || !this.mRequirePointerPilfer)) {
            dispatchOnBackStarted(iOnBackInvokedCallback, createStartEvent);
        }
        if (this.mBackNavigationInfo == null || isAppProgressGenerationAllowed()) {
            return;
        }
        tryPilferPointers();
    }

    public final void resetTouchTracker() {
        BackTouchTracker backTouchTracker = this.mCurrentTracker;
        this.mCurrentTracker = this.mQueuedTracker;
        backTouchTracker.reset();
        this.mQueuedTracker = backTouchTracker;
        if (this.mCurrentTracker.isInInitialState()) {
            if (!this.mBackGestureStarted) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -8000539581969842372L, 0, null);
                    return;
                }
                return;
            } else {
                this.mBackGestureStarted = false;
                tryDispatchOnBackCancelled(this.mActiveCallback);
                finishBackNavigation(false);
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 2388634208657817241L, 0, null);
                    return;
                }
                return;
            }
        }
        if (!this.mCurrentTracker.isFinished() || !this.mCurrentTracker.getTriggerBack()) {
            if (this.mCurrentTracker.isFinished()) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 3253074859835012044L, 0, null);
                }
                this.mCurrentTracker.reset();
                return;
            } else {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -1010179004277836389L, 0, null);
                    return;
                }
                return;
            }
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 3664543358694404916L, 0, null);
        }
        int i = this.mBackAnimationAdapter.mOriginDisplayId;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -1127091700925973460L, 0, null);
        }
        sendBackEvent(0, i);
        sendBackEvent(1, i);
        finishBackNavigation(true);
        this.mCurrentTracker.reset();
    }

    public final void sendBackEvent(int i, int i2) {
        long uptimeMillis = SystemClock.uptimeMillis();
        KeyEvent keyEvent = new KeyEvent(uptimeMillis, uptimeMillis, i, 4, 0, 0, -1, 0, 8, 257);
        if (DesktopExperienceFlags.ENABLE_INDEPENDENT_BACK_IN_PROJECTED.isTrue()) {
            keyEvent.setDisplayId(i2);
        }
        if (((InputManager) this.mContext.getSystemService(InputManager.class)).injectInputEvent(keyEvent, 0) || !ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[4]) {
            return;
        }
        ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 351589376826358494L, 0, null);
    }

    public final void setTriggerBack(boolean z) {
        IOnBackInvokedCallback iOnBackInvokedCallback = this.mActiveCallback;
        if (iOnBackInvokedCallback != null) {
            try {
                iOnBackInvokedCallback.setTriggerBack(z);
            } catch (RemoteException e) {
                Log.e("ShellBackPreview", "remote setTriggerBack error: ", e);
            }
        }
        BackTouchTracker activeTracker = getActiveTracker();
        if (activeTracker != null) {
            activeTracker.setTriggerBack(z);
        }
    }

    public final boolean shouldDispatchToAnimator() {
        BackNavigationInfo backNavigationInfo = this.mBackNavigationInfo;
        return backNavigationInfo != null && backNavigationInfo.isPrepareRemoteAnimation();
    }

    public final void startPostCommitAnimation() {
        int type;
        if (this.mPostCommitAnimationInProgress) {
            return;
        }
        HandlerExecutor handlerExecutor = (HandlerExecutor) this.mShellExecutor;
        BackAnimationController$$ExternalSyntheticLambda2 backAnimationController$$ExternalSyntheticLambda2 = this.mAnimationTimeoutRunnable;
        handlerExecutor.removeCallbacks(backAnimationController$$ExternalSyntheticLambda2);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 1780102699045957800L, 0, null);
        }
        this.mPostCommitAnimationInProgress = true;
        handlerExecutor.executeDelayed(backAnimationController$$ExternalSyntheticLambda2, DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
        if (!this.mCurrentTracker.getTriggerBack()) {
            tryDispatchOnBackCancelled(this.mActiveCallback);
            return;
        }
        BackNavigationInfo backNavigationInfo = this.mBackNavigationInfo;
        if (backNavigationInfo != null && ((type = backNavigationInfo.getType()) == 1 || type == 3 || type == 2)) {
            this.mBackTransitionHandler.mCloseTransitionRequested = true;
            dispatchOnBackInvoked(this.mBackNavigationInfo.getOnBackInvokedCallback());
            this.mRealCallbackInvoked = true;
        }
        dispatchOnBackInvoked(this.mActiveCallback);
    }

    public final void startSystemAnimation() {
        if (this.mBackNavigationInfo == null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[4]) {
                ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -7766921714291428237L, 0, null);
                return;
            }
            return;
        }
        if (!validateAnimationTargets(this.mApps)) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[3]) {
                ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 6041743927302633703L, 0, null);
                return;
            }
            return;
        }
        ShellBackAnimationRegistry shellBackAnimationRegistry = this.mShellBackAnimationRegistry;
        BackNavigationInfo backNavigationInfo = this.mBackNavigationInfo;
        shellBackAnimationRegistry.getClass();
        int type = backNavigationInfo.getType();
        if (type == 2 && shellBackAnimationRegistry.mAnimationDefinition.contains(type)) {
            ShellBackAnimation shellBackAnimation = shellBackAnimationRegistry.mCustomizeActivityAnimation;
            if (shellBackAnimation == null || !shellBackAnimation.prepareNextAnimation(backNavigationInfo.getCustomAnimationInfo(), 0)) {
                ShellBackAnimation shellBackAnimation2 = shellBackAnimationRegistry.mDefaultCrossActivityAnimation;
                if (shellBackAnimation2 != null) {
                    shellBackAnimation2.prepareNextAnimation(null, backNavigationInfo.getLetterboxColor());
                }
            } else {
                ((BackAnimationRunner) shellBackAnimationRegistry.mAnimationDefinition.get(type)).mWaitingAnimation = false;
                shellBackAnimationRegistry.mAnimationDefinition.set(2, shellBackAnimation.getRunner());
            }
        }
        BackAnimationRunner backAnimationRunner = (BackAnimationRunner) shellBackAnimationRegistry.mAnimationDefinition.get(type);
        if (backAnimationRunner == null) {
            Log.e("ShellBackPreview", "Animation didn't be defined for type " + BackNavigationInfo.typeToString(type));
        }
        if (backAnimationRunner == null) {
            IBackAnimationFinishedCallback iBackAnimationFinishedCallback = this.mBackAnimationFinishedCallback;
            if (iBackAnimationFinishedCallback != null) {
                try {
                    iBackAnimationFinishedCallback.onAnimationFinished(false);
                    return;
                } catch (RemoteException e) {
                    Log.w("ShellBackPreview", "Failed call IBackNaviAnimationController", e);
                    return;
                }
            }
            return;
        }
        this.mActiveCallback = backAnimationRunner.mCallback;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 6216410113069956923L, 0, null);
        }
        RemoteAnimationTarget[] remoteAnimationTargetArr = this.mApps;
        BackAnimationController$$ExternalSyntheticLambda2 backAnimationController$$ExternalSyntheticLambda2 = new BackAnimationController$$ExternalSyntheticLambda2(this, 2);
        BackAnimationRunner.RemoteAnimationFinishedStub remoteAnimationFinishedStub = backAnimationRunner.mRemoteCallback;
        if (remoteAnimationFinishedStub != null) {
            synchronized (remoteAnimationFinishedStub) {
                try {
                    remoteAnimationFinishedStub.mAbandoned = true;
                    BackAnimationRunner backAnimationRunner2 = (BackAnimationRunner) remoteAnimationFinishedStub.mRunnerRef.get();
                    if (backAnimationRunner2 != null) {
                        if (backAnimationRunner2.shouldMonitorCUJ(backAnimationRunner2.mApps)) {
                            InteractionJankMonitor.getInstance().end(backAnimationRunner2.mCujType);
                        }
                    }
                } finally {
                }
            }
            backAnimationRunner.mRemoteCallback = null;
        }
        backAnimationRunner.mRemoteCallback = new BackAnimationRunner.RemoteAnimationFinishedStub(backAnimationRunner, 0);
        backAnimationRunner.mFinishedCallback = backAnimationController$$ExternalSyntheticLambda2;
        backAnimationRunner.mApps = remoteAnimationTargetArr;
        backAnimationRunner.mWaitingAnimation = false;
        if (backAnimationRunner.shouldMonitorCUJ(remoteAnimationTargetArr)) {
            InteractionJankMonitor.getInstance().begin(remoteAnimationTargetArr[0].leash, backAnimationRunner.mContext, backAnimationRunner.mHandler, backAnimationRunner.mCujType);
        }
        try {
            backAnimationRunner.mRunner.onAnimationStart(-1, remoteAnimationTargetArr, (RemoteAnimationTarget[]) null, (RemoteAnimationTarget[]) null, backAnimationRunner.mRemoteCallback);
        } catch (RemoteException e2) {
            Log.w("ShellBackPreview", "Failed call onAnimationStart", e2);
        }
        RemoteAnimationTarget[] remoteAnimationTargetArr2 = this.mApps;
        if (remoteAnimationTargetArr2.length >= 1) {
            BackMotionEvent createStartEvent = this.mCurrentTracker.createStartEvent(remoteAnimationTargetArr2[0]);
            dispatchOnBackStarted(this.mActiveCallback, createStartEvent);
            if (createStartEvent.getSwipeEdge() == 2) {
                dispatchOnBackStarted(this.mBackNavigationInfo.getOnBackInvokedCallback(), createStartEvent);
            }
        }
    }

    public final void tryDispatchOnBackCancelled(IOnBackInvokedCallback iOnBackInvokedCallback) {
        if (!this.mOnBackStartDispatched) {
            Log.d("ShellBackPreview", "Skipping dispatching onBackCancelled. Start was never dispatched.");
            return;
        }
        if (iOnBackInvokedCallback == null) {
            return;
        }
        try {
            if (CoreRune.FW_PREDICTIVE_BACK_ANIM_LOG) {
                Log.d("ShellBackPreview", "tryDispatchOnBackCancelled, caller=" + Debug.getCallers(3));
            }
            iOnBackInvokedCallback.onBackCancelled();
        } catch (RemoteException e) {
            Log.e("ShellBackPreview", "dispatchOnBackCancelled error: ", e);
        }
    }

    public final void tryPilferPointers() {
        if (this.mPointersPilfered || !this.mThresholdCrossed) {
            return;
        }
        Runnable runnable = this.mPilferPointerCallback;
        if (runnable != null) {
            runnable.run();
        }
        this.mPointersPilfered = true;
    }

    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.wm.shell.back.BackAnimationController$2] */
    public BackAnimationController(ShellInit shellInit, ShellController shellController, ShellExecutor shellExecutor, IActivityTaskManager iActivityTaskManager, Context context, BackAnimationBackground backAnimationBackground, ShellBackAnimationRegistry shellBackAnimationRegistry, ShellCommandHandler shellCommandHandler, Transitions transitions, Handler handler) {
        this.mBackGestureStarted = false;
        this.mPostCommitAnimationInProgress = false;
        this.mRealCallbackInvoked = false;
        this.mShouldStartOnNextMoveEvent = false;
        this.mOnBackStartDispatched = false;
        this.mThresholdCrossed = false;
        this.mPointersPilfered = false;
        this.mReceivedNullNavigationInfo = false;
        Rect rect = new Rect();
        this.mTouchableArea = rect;
        this.mCurrentTracker = new BackTouchTracker();
        this.mQueuedTracker = new BackTouchTracker();
        BackTransitionObserver backTransitionObserver = new BackTransitionObserver();
        this.mBackTransitionObserver = backTransitionObserver;
        this.mAnimationTimeoutRunnable = new BackAnimationController$$ExternalSyntheticLambda2(this, 1);
        this.mNavigationObserver = new RemoteCallback(new AnonymousClass1());
        this.mHandoffHandler = new IBackAnimationHandoffHandler.Stub() { // from class: com.android.wm.shell.back.BackAnimationController.2
            public final void handOffAnimation(RemoteAnimationTarget[] remoteAnimationTargetArr, WindowAnimationState[] windowAnimationStateArr) {
                BackTransitionHandler backTransitionHandler = BackAnimationController.this.mBackTransitionHandler;
                backTransitionHandler.getClass();
                TransitionAnimator.Companion.getClass();
                if (backTransitionHandler.mTakeoverHandler == null) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[4]) {
                        ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -8708076958855907050L, 0, null);
                        return;
                    }
                    return;
                }
                if (remoteAnimationTargetArr.length != windowAnimationStateArr.length) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[4]) {
                        ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -9134176282565263384L, 0, null);
                        return;
                    }
                    return;
                }
                WindowAnimationState[] windowAnimationStateArr2 = new WindowAnimationState[backTransitionHandler.mOpenTransitionInfo.getChanges().size()];
                for (int i = 0; i < backTransitionHandler.mOpenTransitionInfo.getChanges().size(); i++) {
                    ActivityManager.RunningTaskInfo taskInfo = ((TransitionInfo.Change) backTransitionHandler.mOpenTransitionInfo.getChanges().get(i)).getTaskInfo();
                    if (taskInfo != null) {
                        int i2 = 0;
                        while (true) {
                            if (i2 >= remoteAnimationTargetArr.length) {
                                break;
                            }
                            if (taskInfo.taskId == remoteAnimationTargetArr[i2].taskId) {
                                windowAnimationStateArr2[i] = windowAnimationStateArr[i2];
                                break;
                            }
                            i2++;
                        }
                    }
                }
                backTransitionHandler.mTakeoverHandler.takeOverAnimation(backTransitionHandler.mPrepareOpenTransition, backTransitionHandler.mOpenTransitionInfo, new SurfaceControl.Transaction(), backTransitionHandler.mFinishOpenTransitionCallback, windowAnimationStateArr2);
                backTransitionHandler.mOpenTransitionInfo = null;
                backTransitionHandler.mPrepareOpenTransition = null;
                backTransitionHandler.mFinishOpenTransaction = null;
                backTransitionHandler.mFinishOpenTransitionCallback = null;
                backTransitionHandler.mTakeoverHandler = null;
            }
        };
        this.mBackAnimation = new BackAnimationImpl(this, 0);
        this.mShellController = shellController;
        this.mShellExecutor = shellExecutor;
        this.mActivityTaskManager = iActivityTaskManager;
        this.mContext = context;
        this.mRequirePointerPilfer = context.getResources().getBoolean(R.bool.config_backAnimationRequiresPointerPilfer);
        shellInit.addInitCallback(new BackAnimationController$$ExternalSyntheticLambda2(this, 0), this);
        this.mAnimationBackground = backAnimationBackground;
        this.mShellBackAnimationRegistry = shellBackAnimationRegistry;
        this.mLatencyTracker = LatencyTracker.getInstance(context);
        this.mShellCommandHandler = shellCommandHandler;
        WindowManager windowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mWindowManager = windowManager;
        BackTransitionHandler backTransitionHandler = new BackTransitionHandler(transitions);
        this.mBackTransitionHandler = backTransitionHandler;
        transitions.addHandler(backTransitionHandler);
        this.mHandler = handler;
        transitions.registerObserver(backTransitionObserver);
        backTransitionObserver.mBackTransitionHandler = backTransitionHandler;
        rect.set(windowManager.getCurrentWindowMetrics().getBounds());
    }
}
