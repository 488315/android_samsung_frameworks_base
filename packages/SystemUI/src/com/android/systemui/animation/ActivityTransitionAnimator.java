package com.android.systemui.animation;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.window.IRemoteTransition;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.RemoteTransition;
import android.window.TransitionFilter;
import android.window.TransitionInfo;
import android.window.WindowAnimationState;
import android.window.WindowContainerTransaction;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.RemoteAnimationRunnerCompat;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.shared.IShellTransitions;
import com.android.wm.shell.shared.ShellTransitions;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.startingsurface.SplashscreenContentDrawer;
import com.android.wm.shell.startingsurface.StartingWindowController;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.transition.Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda0;
import com.android.wm.shell.transition.Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda1;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public final class ActivityTransitionAnimator {
    public static final long ANIMATION_DELAY_NAV_FADE_IN;
    public static final Companion Companion = new Companion(null);
    public static final boolean DEBUG_TRANSITION_ANIMATION;
    public static final TransitionAnimator.Timings DIALOG_TIMINGS;
    public static final TransitionAnimator.Interpolators INTERPOLATORS;
    public static final Interpolator NAV_FADE_IN_INTERPOLATOR;
    public static final PathInterpolator NAV_FADE_OUT_INTERPOLATOR;
    public static final TransitionAnimator.Interpolators SPRING_INTERPOLATORS;
    public static final TransitionAnimator.SpringTimings SPRING_TIMINGS;
    public static final TransitionAnimator.Timings TIMINGS;
    public CentralSurfacesImpl.AnonymousClass19 callback;
    public final TransitionAnimator dialogToAppAnimator;
    public final boolean disableWmTimeout;
    public final ActivityTransitionAnimator$lifecycleListener$1 lifecycleListener;
    public final LinkedHashSet listeners;
    public final HashMap longLivedTransitions;
    public final Executor mainExecutor;
    public final boolean skipReparentTransaction;
    public final TransitionAnimator transitionAnimator;
    public final TransitionRegister transitionRegister;

    public final class AnimationDelegate {
        public TransitionAnimator.Animation animation;
        public final Callback callback;
        public boolean cancelled;
        public final Context context;
        public final Controller controller;
        public final Matrix invertMatrix;
        public final Listener listener;
        public final Executor mainExecutor;
        public final Matrix matrix;
        public final ActivityTransitionAnimator$AnimationDelegate$onLongTimeout$1 onLongTimeout;
        public final ActivityTransitionAnimator$AnimationDelegate$onTimeout$1 onTimeout;
        public final boolean skipReparentTransaction;
        public boolean timedOut;
        public final Handler timeoutHandler;
        public final SyncRtSurfaceTransactionApplier transactionApplier;
        public final View transactionApplierView;
        public final TransitionAnimator transitionAnimator;
        public final ViewGroup transitionContainer;
        public final Rect windowCrop;
        public final RectF windowCropF;

        public AnimationDelegate(Executor executor, Controller controller, Callback callback) {
            this(executor, controller, callback, null, null, false, false, 120, null);
        }

        public final void applyStateToWindow(RemoteAnimationTarget remoteAnimationTarget, TransitionAnimator.State state, float f, boolean z, SurfaceControl.Transaction transaction) {
            long j;
            long j2;
            TransitionAnimator.Interpolators interpolators;
            float progress;
            float f2;
            float f3;
            if (this.transactionApplierView.getViewRootImpl() == null || !remoteAnimationTarget.leash.isValid()) {
                return;
            }
            Rect rect = remoteAnimationTarget.screenSpaceBounds;
            int i = rect.left;
            int i2 = rect.right;
            float f4 = (i + i2) / 2.0f;
            int i3 = rect.top;
            float f5 = (i3 + r10) / 2.0f;
            float f6 = rect.bottom - i3;
            float fMax = Math.max(state.getWidth() / (i2 - i), state.getHeight() / f6);
            this.matrix.reset();
            this.matrix.setScale(fMax, fMax, f4, f5);
            this.matrix.postTranslate(state.getCenterX() - f4, (((f6 * fMax) - f6) / 2.0f) + (state.top - rect.top));
            float f7 = state.left - rect.left;
            float f8 = state.top - rect.top;
            this.windowCropF.set(f7, f8, state.getWidth() + f7, state.getHeight() + f8);
            this.matrix.invert(this.invertMatrix);
            this.invertMatrix.mapRect(this.windowCropF);
            this.windowCrop.set(MathKt__MathJVMKt.roundToInt(this.windowCropF.left), MathKt__MathJVMKt.roundToInt(this.windowCropF.top), MathKt__MathJVMKt.roundToInt(this.windowCropF.right), MathKt__MathJVMKt.roundToInt(this.windowCropF.bottom));
            Controller controller = this.controller;
            if (z) {
                if (controller.isLaunching()) {
                    ActivityTransitionAnimator.Companion.getClass();
                    TransitionAnimator.SpringTimings springTimings = ActivityTransitionAnimator.SPRING_TIMINGS;
                    f2 = springTimings.contentAfterFadeInDelay;
                    f3 = springTimings.contentAfterFadeInDuration;
                } else {
                    ActivityTransitionAnimator.Companion.getClass();
                    TransitionAnimator.SpringTimings springTimings2 = ActivityTransitionAnimator.SPRING_TIMINGS;
                    f2 = springTimings2.contentBeforeFadeOutDelay;
                    f3 = springTimings2.contentBeforeFadeOutDuration;
                }
                ActivityTransitionAnimator.Companion.getClass();
                interpolators = ActivityTransitionAnimator.SPRING_INTERPOLATORS;
                TransitionAnimator.Companion.getClass();
                progress = TransitionAnimator.Companion.getProgressInternal(1.0f, f, f2, f3);
            } else {
                if (controller.isLaunching()) {
                    TransitionAnimator.Timings timings = ActivityTransitionAnimator.TIMINGS;
                    j = timings.contentAfterFadeInDelay;
                    j2 = timings.contentAfterFadeInDuration;
                } else {
                    TransitionAnimator.Timings timings2 = ActivityTransitionAnimator.TIMINGS;
                    j = timings2.contentBeforeFadeOutDelay;
                    j2 = timings2.contentBeforeFadeOutDuration;
                }
                long j3 = j;
                long j4 = j2;
                ActivityTransitionAnimator.Companion.getClass();
                interpolators = ActivityTransitionAnimator.INTERPOLATORS;
                TransitionAnimator.Companion companion = TransitionAnimator.Companion;
                TransitionAnimator.Timings timings3 = ActivityTransitionAnimator.TIMINGS;
                companion.getClass();
                progress = TransitionAnimator.Companion.getProgress(timings3, f, j3, j4);
            }
            SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builderWithVisibility = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget.leash).withAlpha(controller.isBelowAnimatingWindow() ? controller.isLaunching() ? interpolators.contentAfterFadeInInterpolator.getInterpolation(progress) : 1 - interpolators.contentBeforeFadeOutInterpolator.getInterpolation(progress) : 1.0f).withMatrix(this.matrix).withWindowCrop(this.windowCrop).withCornerRadius(Math.max(state.topCornerRadius, state.bottomCornerRadius) / fMax).withVisibility(true);
            if (transaction != null) {
                builderWithVisibility.withMergeTransaction(transaction);
            }
            this.transactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{builderWithVisibility.build()});
        }

        public final RemoteAnimationTarget setUpAnimation(RemoteAnimationTarget[] remoteAnimationTargetArr, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            RemoteAnimationTarget remoteAnimationTarget;
            boolean z;
            ActivityManager.RunningTaskInfo runningTaskInfo;
            ArrayList arrayList;
            Handler handler = this.timeoutHandler;
            if (handler != null) {
                handler.removeCallbacks(this.onTimeout);
                handler.removeCallbacks(this.onLongTimeout);
            }
            if (this.timedOut) {
                if (iRemoteAnimationFinishedCallback != null) {
                    try {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            } else if (!this.cancelled) {
                Controller controller = this.controller;
                if (remoteAnimationTargetArr == null) {
                    remoteAnimationTarget = null;
                } else {
                    boolean z2 = !controller.isLaunching();
                    ArrayIterator arrayIterator = new ArrayIterator(remoteAnimationTargetArr);
                    remoteAnimationTarget = null;
                    while (arrayIterator.hasNext()) {
                        RemoteAnimationTarget remoteAnimationTarget2 = (RemoteAnimationTarget) arrayIterator.next();
                        if (remoteAnimationTarget2.mode == z2) {
                            TransitionAnimator.Companion.getClass();
                            if (controller.getTransitionCookie() != null && ((runningTaskInfo = remoteAnimationTarget2.taskInfo) == null || (arrayList = runningTaskInfo.launchCookies) == null || !arrayList.contains(controller.getTransitionCookie()))) {
                                if (controller.getComponent() != null) {
                                    ActivityManager.RunningTaskInfo runningTaskInfo2 = remoteAnimationTarget2.taskInfo;
                                    if (!Intrinsics.areEqual(runningTaskInfo2 != null ? runningTaskInfo2.topActivity : null, controller.getComponent())) {
                                    }
                                }
                            }
                            if (remoteAnimationTarget != null && ((z = remoteAnimationTarget2.hasAnimatingParent) || !remoteAnimationTarget.hasAnimatingParent)) {
                                if (!z) {
                                    Rect rect = remoteAnimationTarget2.screenSpaceBounds;
                                    Rect rect2 = remoteAnimationTarget.screenSpaceBounds;
                                    if (rect.height() * rect.width() > rect2.height() * rect2.width()) {
                                    }
                                }
                            }
                            remoteAnimationTarget = remoteAnimationTarget2;
                        }
                    }
                }
                if (remoteAnimationTarget != null) {
                    return remoteAnimationTarget;
                }
                Log.i("ActivityTransitionAnimator", "Aborting the animation as no window is opening");
                if (iRemoteAnimationFinishedCallback != null) {
                    try {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                    }
                }
                if (ActivityTransitionAnimator.DEBUG_TRANSITION_ANIMATION) {
                    Log.d("ActivityTransitionAnimator", "Calling controller.onTransitionAnimationCancelled() [no window opening]");
                }
                Controller.Companion companion = Controller.Companion;
                controller.onTransitionAnimationCancelled();
                Listener listener = this.listener;
                if (listener != null) {
                    listener.onTransitionAnimationCancelled();
                }
            }
            return null;
        }

        public final void startAnimation(RemoteAnimationTarget remoteAnimationTarget, RemoteAnimationTarget remoteAnimationTarget2, boolean z, WindowAnimationState windowAnimationState, SurfaceControl.Transaction transaction, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            TransitionAnimator.State stateCreateAnimatorState;
            int backgroundColor;
            PointF pointF;
            Rect rect = remoteAnimationTarget.screenSpaceBounds;
            Controller controller = this.controller;
            boolean zIsLaunching = controller.isLaunching();
            TransitionAnimator transitionAnimator = this.transitionAnimator;
            if (zIsLaunching) {
                WindowAnimationState windowAnimatorState = controller.getWindowAnimatorState();
                if (windowAnimatorState != null) {
                    TransitionAnimator.Companion.getClass();
                    stateCreateAnimatorState = new TransitionAnimator.State(0, 0, 0, 0, 0.0f, 0.0f, 63, null);
                    RectF rectF = windowAnimatorState.bounds;
                    if (rectF != null) {
                        stateCreateAnimatorState.top = MathKt__MathJVMKt.roundToInt(rectF.top);
                        stateCreateAnimatorState.left = MathKt__MathJVMKt.roundToInt(rectF.left);
                        stateCreateAnimatorState.bottom = MathKt__MathJVMKt.roundToInt(rectF.bottom);
                        stateCreateAnimatorState.right = MathKt__MathJVMKt.roundToInt(rectF.right);
                    }
                    float f = 2;
                    stateCreateAnimatorState.bottomCornerRadius = (windowAnimatorState.bottomLeftRadius + windowAnimatorState.bottomRightRadius) / f;
                    stateCreateAnimatorState.topCornerRadius = (windowAnimatorState.topLeftRadius + windowAnimatorState.topRightRadius) / f;
                } else {
                    TransitionAnimator.State state = new TransitionAnimator.State(rect.top, rect.bottom, rect.left, rect.right, 0.0f, 0.0f, 48, null);
                    float windowCornerRadius = transitionAnimator.isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib(controller.getTransitionContainer(), state) ? ScreenDecorationsUtils.getWindowCornerRadius(this.context) : 0.0f;
                    state.topCornerRadius = windowCornerRadius;
                    state.bottomCornerRadius = windowCornerRadius;
                    stateCreateAnimatorState = state;
                }
            } else {
                stateCreateAnimatorState = controller.createAnimatorState();
            }
            if (remoteAnimationTarget.isTranslucent) {
                backgroundColor = 0;
            } else {
                ActivityManager.RunningTaskInfo runningTaskInfo = remoteAnimationTarget.taskInfo;
                if (runningTaskInfo != null) {
                    CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                    if (centralSurfacesImpl.mStartingSurfaceOptional.isPresent()) {
                        backgroundColor = ((StartingWindowController.StartingSurfaceImpl) centralSurfacesImpl.mStartingSurfaceOptional.get()).getBackgroundColor(runningTaskInfo);
                    } else {
                        Log.w("CentralSurfaces", "No starting surface, defaulting to SystemBGColor");
                        backgroundColor = SplashscreenContentDrawer.getSystemBGColor();
                    }
                } else {
                    backgroundColor = remoteAnimationTarget.backgroundColor;
                }
            }
            int i = backgroundColor;
            boolean zIsExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib = transitionAnimator.isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib(controller.getTransitionContainer(), stateCreateAnimatorState);
            WindowAnimationState windowAnimatorState2 = windowAnimationState == null ? controller.getWindowAnimatorState() : windowAnimationState;
            controller.isLaunching();
            ViewRootImpl viewRootImpl = controller.getTransitionContainer().getViewRootImpl();
            if (!this.skipReparentTransaction) {
                remoteAnimationTarget.leash.isValid();
            }
            ActivityTransitionAnimator$AnimationDelegate$startAnimation$controller$1 activityTransitionAnimator$AnimationDelegate$startAnimation$controller$1 = new ActivityTransitionAnimator$AnimationDelegate$startAnimation$controller$1(this.controller, this, zIsExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib, windowAnimatorState2, rect, transaction, remoteAnimationTarget, z, viewRootImpl, iRemoteAnimationFinishedCallback, remoteAnimationTarget2);
            TransitionAnimator.Companion.getClass();
            if ((windowAnimatorState2 != null ? windowAnimatorState2.velocityPxPerMs : null) != null) {
                PointF pointF2 = windowAnimatorState2.velocityPxPerMs;
                float f2 = 1000;
                pointF = new PointF(pointF2.x * f2, pointF2.y * f2);
            } else {
                pointF = z ? new PointF(0.0f, 0.0f) : null;
            }
            Controller controller2 = activityTransitionAnimator$AnimationDelegate$startAnimation$controller$1.$$delegate_0;
            this.animation = this.transitionAnimator.startAnimation(activityTransitionAnimator$AnimationDelegate$startAnimation$controller$1, stateCreateAnimatorState, i, !controller2.isBelowAnimatingWindow(), !controller2.isBelowAnimatingWindow(), pointF, windowAnimatorState2 != null ? windowAnimatorState2.timestamp : -1L);
        }

        public AnimationDelegate(Executor executor, Controller controller, Callback callback, Listener listener) {
            this(executor, controller, callback, listener, null, false, false, 112, null);
        }

        public AnimationDelegate(Executor executor, Controller controller, Callback callback, Listener listener, TransitionAnimator transitionAnimator) {
            this(executor, controller, callback, listener, transitionAnimator, false, false, 96, null);
        }

        public AnimationDelegate(Executor executor, Controller controller, Callback callback, Listener listener, TransitionAnimator transitionAnimator, boolean z) {
            this(executor, controller, callback, listener, transitionAnimator, z, false, 64, null);
        }

        /* JADX WARN: Type inference failed for: r1v11, types: [com.android.systemui.animation.ActivityTransitionAnimator$AnimationDelegate$onTimeout$1] */
        public AnimationDelegate(Executor executor, Controller controller, Callback callback, Listener listener, TransitionAnimator transitionAnimator, boolean z, boolean z2) {
            this.mainExecutor = executor;
            this.controller = controller;
            this.callback = callback;
            this.listener = listener;
            this.transitionAnimator = transitionAnimator;
            this.skipReparentTransaction = z2;
            ViewGroup transitionContainer = controller.getTransitionContainer();
            this.transitionContainer = transitionContainer;
            this.context = transitionContainer.getContext();
            View openingWindowSyncView = controller.getOpeningWindowSyncView();
            openingWindowSyncView = openingWindowSyncView == null ? controller.getTransitionContainer() : openingWindowSyncView;
            this.transactionApplierView = openingWindowSyncView;
            this.transactionApplier = new SyncRtSurfaceTransactionApplier(openingWindowSyncView);
            this.timeoutHandler = !z ? new Handler(Looper.getMainLooper()) : null;
            this.matrix = new Matrix();
            this.invertMatrix = new Matrix();
            this.windowCrop = new Rect();
            this.windowCropF = new RectF();
            this.onTimeout = new Runnable() { // from class: com.android.systemui.animation.ActivityTransitionAnimator$AnimationDelegate$onTimeout$1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityTransitionAnimator.AnimationDelegate animationDelegate = this.this$0;
                    if (animationDelegate.cancelled) {
                        return;
                    }
                    Log.w("ActivityTransitionAnimator", "Remote animation timed out");
                    animationDelegate.timedOut = true;
                    if (ActivityTransitionAnimator.DEBUG_TRANSITION_ANIMATION) {
                        Log.d("ActivityTransitionAnimator", "Calling controller.onTransitionAnimationCancelled() [animation timed out]");
                    }
                    ActivityTransitionAnimator.Controller.Companion companion = ActivityTransitionAnimator.Controller.Companion;
                    animationDelegate.controller.onTransitionAnimationCancelled();
                    ActivityTransitionAnimator.Listener listener2 = animationDelegate.listener;
                    if (listener2 != null) {
                        listener2.onTransitionAnimationCancelled();
                    }
                }
            };
            this.onLongTimeout = ActivityTransitionAnimator$AnimationDelegate$onLongTimeout$1.INSTANCE;
            if (controller.isLaunching()) {
                return;
            }
            TransitionAnimator.Companion.getClass();
        }

        public /* synthetic */ AnimationDelegate(Executor executor, Controller controller, Callback callback, Listener listener, TransitionAnimator transitionAnimator, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(executor, controller, callback, (i & 8) != 0 ? null : listener, (i & 16) != 0 ? Companion.access$defaultTransitionAnimator(ActivityTransitionAnimator.Companion, executor) : transitionAnimator, (i & 32) != 0 ? false : z, (i & 64) != 0 ? false : z2);
        }
    }

    public interface Callback {
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final TransitionAnimator access$defaultDialogToAppAnimator(Companion companion, Executor executor) {
            companion.getClass();
            return new TransitionAnimator(executor, ActivityTransitionAnimator.DIALOG_TIMINGS, ActivityTransitionAnimator.INTERPOLATORS, null, null, null, 56, null);
        }

        public static final TransitionAnimator access$defaultTransitionAnimator(Companion companion, Executor executor) {
            companion.getClass();
            return new TransitionAnimator(executor, ActivityTransitionAnimator.TIMINGS, ActivityTransitionAnimator.INTERPOLATORS, ActivityTransitionAnimator.SPRING_TIMINGS, ActivityTransitionAnimator.SPRING_INTERPOLATORS, null, 32, null);
        }

        private Companion() {
        }
    }

    public final class DelegatingAnimationCompletionListener implements Listener {
        public boolean cancelled;
        public final Listener delegate;
        public final Function0 onAnimationComplete;

        public DelegatingAnimationCompletionListener(ActivityTransitionAnimator activityTransitionAnimator, Listener listener, Function0 function0) {
            this.delegate = listener;
            this.onAnimationComplete = function0;
        }

        @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
        public final void onTransitionAnimationCancelled() {
            this.cancelled = true;
            Listener listener = this.delegate;
            if (listener != null) {
                listener.onTransitionAnimationCancelled();
            }
            this.onAnimationComplete.invoke();
        }

        @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
        public final void onTransitionAnimationEnd() {
            Listener listener = this.delegate;
            if (listener != null) {
                listener.onTransitionAnimationEnd();
            }
            if (this.cancelled) {
                return;
            }
            this.onAnimationComplete.invoke();
        }

        @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
        public final void onTransitionAnimationProgress(float f) {
            Listener listener = this.delegate;
            if (listener != null) {
                listener.onTransitionAnimationProgress(f);
            }
        }

        @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
        public final void onTransitionAnimationStart() {
            Listener listener = this.delegate;
            if (listener != null) {
                listener.onTransitionAnimationStart();
            }
        }
    }

    public final class OriginTransition implements IRemoteTransition {
        public final RemoteAnimationRunnerCompat.AnonymousClass1 delegate;
        public final Runner runner;

        public OriginTransition(ActivityTransitionAnimator activityTransitionAnimator, Runner runner) {
            this.runner = runner;
            boolean z = RemoteAnimationRunnerCompat.IS_SHELL_TRANSITION_ENABLED;
            this.delegate = new RemoteAnimationRunnerCompat.AnonymousClass1(runner);
            TransitionAnimator.Companion.getClass();
        }

        public final IBinder asBinder() {
            return this.delegate.asBinder();
        }

        public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            this.delegate.mergeAnimation(iBinder, transitionInfo, transaction, iBinder2, iRemoteTransitionFinishedCallback);
        }

        public final void onTransitionConsumed(IBinder iBinder, boolean z) {
            this.delegate.onTransitionConsumed(iBinder, z);
        }

        public final void startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            this.delegate.startAnimation(iBinder, transitionInfo, transaction, iRemoteTransitionFinishedCallback);
        }

        public final void takeOverAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, final IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback, WindowAnimationState[] windowAnimationStateArr) {
            char c;
            if (transitionInfo == null || transaction == null) {
                Log.e("ActivityTransitionAnimator", "Skipping the animation takeover because the required data is missing: info=" + transitionInfo + ", transaction=" + transaction);
                return;
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            final ArrayMap arrayMap = new ArrayMap();
            TransitionUtil.LeafTaskFilter leafTaskFilter = new TransitionUtil.LeafTaskFilter();
            int size = transitionInfo.getChanges().size();
            char c2 = 2;
            int size2 = transitionInfo.getChanges().size() * 2;
            int size3 = transitionInfo.getChanges().size();
            int i = 0;
            while (i < size3) {
                TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
                if (change != null && change.getTaskInfo() != null) {
                    ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
                    if (TransitionUtil.isWallpaper(change)) {
                        transaction.setAlpha(TransitionUtil.newTarget(change, size - i, false, transitionInfo, transaction, arrayMap).leash, 1.0f);
                    } else {
                        if (leafTaskFilter.test(change)) {
                            int i2 = size - i;
                            RemoteAnimationTarget remoteAnimationTargetNewTarget = TransitionUtil.newTarget(change, i2, false, transitionInfo, transaction, arrayMap);
                            arrayList.add(remoteAnimationTargetNewTarget);
                            arrayList2.add(windowAnimationStateArr[i]);
                            transaction.setAlpha(remoteAnimationTargetNewTarget.leash, 1.0f);
                            if (TransitionUtil.isClosingType(change.getMode())) {
                                if (taskInfo != null) {
                                    c = 2;
                                    if (taskInfo.topActivityType == 2) {
                                    }
                                } else {
                                    c = 2;
                                }
                                transaction.setLayer(remoteAnimationTargetNewTarget.leash, size2 - i);
                            } else {
                                c = 2;
                            }
                            if (TransitionUtil.isOpeningType(change.getMode())) {
                                transaction.setLayer(remoteAnimationTargetNewTarget.leash, i2);
                            }
                        } else {
                            c = c2;
                            if (TransitionInfo.isIndependent(change, transitionInfo)) {
                                if (TransitionUtil.isClosingType(change.getMode())) {
                                    transaction.setLayer(change.getLeash(), size2 - i);
                                } else if (TransitionUtil.isOpeningType(change.getMode())) {
                                    transaction.setLayer(change.getLeash(), size - i);
                                }
                            } else if (TransitionUtil.isDividerBar(change)) {
                                arrayList.add(TransitionUtil.newTarget(change, size - i, false, transitionInfo, transaction, arrayMap));
                                arrayList2.add(windowAnimationStateArr[i]);
                            }
                        }
                        i++;
                        c2 = c;
                    }
                }
                c = c2;
                i++;
                c2 = c;
            }
            this.runner.takeOverAnimation((RemoteAnimationTarget[]) arrayList.toArray(new RemoteAnimationTarget[0]), (WindowAnimationState[]) arrayList2.toArray(new WindowAnimationState[0]), transaction, new IRemoteAnimationFinishedCallback.Stub() { // from class: com.android.systemui.animation.ActivityTransitionAnimator$OriginTransition$takeOverAnimation$wrappedCallback$1
                public final void onAnimationFinished() {
                    arrayMap.clear();
                    SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
                    IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback2 = iRemoteTransitionFinishedCallback;
                    if (iRemoteTransitionFinishedCallback2 != null) {
                        iRemoteTransitionFinishedCallback2.onTransitionFinished((WindowContainerTransaction) null, transaction2);
                    }
                    transaction2.close();
                }
            });
        }
    }

    public interface PendingIntentStarter {
        int startPendingIntent(RemoteAnimationAdapter remoteAnimationAdapter);
    }

    public final class TransitionCookie extends Binder {
        public final String cookie;

        public TransitionCookie(String str) {
            this.cookie = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof TransitionCookie) && Intrinsics.areEqual(this.cookie, ((TransitionCookie) obj).cookie);
        }

        public final int hashCode() {
            return this.cookie.hashCode();
        }

        public final String toString() {
            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("TransitionCookie(cookie=", this.cookie, ")");
        }
    }

    /* renamed from: com.android.systemui.animation.ActivityTransitionAnimator$createLongLivedRunner$1, reason: invalid class name and case insensitive filesystem */
    final class C07921 extends SuspendLambda implements Function1 {
        final /* synthetic */ ControllerFactory $controllerFactory;
        final /* synthetic */ boolean $forLaunch;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C07921(ControllerFactory controllerFactory, boolean z, Continuation continuation) {
            super(1, continuation);
            this.$controllerFactory = controllerFactory;
            this.$forLaunch = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Continuation continuation) {
            return new C07921(this.$controllerFactory, this.$forLaunch, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return ((C07921) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            ControllerFactory controllerFactory = this.$controllerFactory;
            boolean z = this.$forLaunch;
            this.label = 1;
            Object objCreateController = controllerFactory.createController(z, this);
            return objCreateController == coroutineSingletons ? coroutineSingletons : objCreateController;
        }
    }

    static {
        TransitionAnimator.Timings timings = new TransitionAnimator.Timings(500L, 0L, 150L, 150L, 183L);
        TIMINGS = timings;
        SPRING_TIMINGS = new TransitionAnimator.SpringTimings(0.0f, 0.8f, 0.85f, 0.135f);
        DIALOG_TIMINGS = new TransitionAnimator.Timings(timings.totalDuration, timings.contentBeforeFadeOutDelay, 200L, 200L, timings.contentAfterFadeInDuration);
        TransitionAnimator.Interpolators interpolators = new TransitionAnimator.Interpolators(Interpolators.EMPHASIZED, Interpolators.EMPHASIZED_COMPLEMENT, Interpolators.LINEAR_OUT_SLOW_IN, new PathInterpolator(0.0f, 0.0f, 0.6f, 1.0f));
        INTERPOLATORS = interpolators;
        SPRING_INTERPOLATORS = TransitionAnimator.Interpolators.copy$default(interpolators, null, Interpolators.DECELERATE_1_5, Interpolators.SLOW_OUT_LINEAR_IN, 3);
        DEBUG_TRANSITION_ANIMATION = Build.IS_DEBUGGABLE;
        ANIMATION_DELAY_NAV_FADE_IN = timings.totalDuration - 266;
        NAV_FADE_IN_INTERPOLATOR = Interpolators.STANDARD_DECELERATE;
        NAV_FADE_OUT_INTERPOLATOR = new PathInterpolator(0.2f, 0.0f, 1.0f, 1.0f);
    }

    public ActivityTransitionAnimator(Executor executor) {
        this(executor, null, null, null, false, false, 62, null);
    }

    public final void callOnIntentStartedOnMainThread(final Controller controller, final boolean z) {
        if (!Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.animation.ActivityTransitionAnimator.callOnIntentStartedOnMainThread.1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityTransitionAnimator activityTransitionAnimator = ActivityTransitionAnimator.this;
                    Controller controller2 = controller;
                    boolean z2 = z;
                    Companion companion = ActivityTransitionAnimator.Companion;
                    activityTransitionAnimator.callOnIntentStartedOnMainThread(controller2, z2);
                }
            });
            return;
        }
        if (DEBUG_TRANSITION_ANIMATION) {
            Log.d("ActivityTransitionAnimator", "Calling controller.onIntentStarted(willAnimate=" + z + ") [controller=" + controller + "]");
        }
        controller.onIntentStarted(z);
    }

    public final Runner createEphemeralRunner(Controller controller) {
        TransitionAnimator transitionAnimator = controller.isDialogLaunch() ? this.dialogToAppAnimator : this.transitionAnimator;
        CentralSurfacesImpl.AnonymousClass19 anonymousClass19 = this.callback;
        anonymousClass19.getClass();
        return new Runner(this, controller, anonymousClass19, transitionAnimator, this.lifecycleListener);
    }

    public final Runner createLongLivedRunner(ControllerFactory controllerFactory, CoroutineScope coroutineScope, boolean z) {
        TransitionAnimator.Companion.getClass();
        CentralSurfacesImpl.AnonymousClass19 anonymousClass19 = this.callback;
        anonymousClass19.getClass();
        return new Runner(this, coroutineScope, anonymousClass19, this.transitionAnimator, this.lifecycleListener, new C07921(controllerFactory, z, null));
    }

    public final void register(TransitionCookie transitionCookie, ControllerFactory controllerFactory, CoroutineScope coroutineScope) {
        TransitionAnimator.Companion.getClass();
        TransitionRegister transitionRegister = this.transitionRegister;
        if (transitionRegister == null) {
            throw new IllegalStateException("A RemoteTransitionRegister must be provided when creating this animator in order to use long-lived animations");
        }
        ComponentName componentName = controllerFactory.component;
        if (componentName == null) {
            throw new IllegalStateException("A component must be defined in order to use long-lived animations");
        }
        unregister(transitionCookie);
        TransitionFilter transitionFilter = new TransitionFilter();
        TransitionFilter.Requirement requirement = new TransitionFilter.Requirement();
        requirement.mActivityType = 1;
        requirement.mModes = new int[]{1, 3};
        requirement.mTopActivity = componentName;
        Unit unit = Unit.INSTANCE;
        transitionFilter.mRequirements = new TransitionFilter.Requirement[]{requirement};
        RemoteTransition remoteTransition = new RemoteTransition(new OriginTransition(this, createLongLivedRunner(controllerFactory, coroutineScope, true)), transitionCookie + "_launchTransition");
        transitionRegister.register$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib(transitionFilter, remoteTransition);
        TransitionFilter transitionFilter2 = new TransitionFilter();
        TransitionFilter.Requirement requirement2 = new TransitionFilter.Requirement();
        requirement2.mActivityType = 1;
        requirement2.mModes = new int[]{2, 4};
        requirement2.mTopActivity = componentName;
        TransitionFilter.Requirement requirement3 = new TransitionFilter.Requirement();
        requirement3.mActivityType = 2;
        requirement3.mModes = new int[]{1, 3};
        transitionFilter2.mRequirements = new TransitionFilter.Requirement[]{requirement2, requirement3};
        RemoteTransition remoteTransition2 = new RemoteTransition(new OriginTransition(this, createLongLivedRunner(controllerFactory, coroutineScope, false)), transitionCookie + "_returnTransition");
        transitionRegister.register$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib(transitionFilter2, remoteTransition2);
        this.longLivedTransitions.put(transitionCookie, new Pair(remoteTransition, remoteTransition2));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v1, types: [T, com.android.systemui.animation.ActivityTransitionAnimator$registerEphemeralReturnAnimation$1] */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r16v2 */
    /* JADX WARN: Type inference failed for: r16v3 */
    /* JADX WARN: Type inference failed for: r24v0, types: [kotlin.jvm.functions.Function1] */
    public final void startIntentWithAnimation(final Controller controller, boolean z, String str, boolean z2, Function1 function1) {
        CentralSurfacesImpl.AnonymousClass19 anonymousClass19;
        boolean z3;
        ?? r16;
        RemoteAnimationAdapter remoteAnimationAdapter;
        if (controller == null || !z) {
            Log.i("ActivityTransitionAnimator", "Starting intent with no animation");
            function1.mo781invoke(null);
            if (controller != null) {
                callOnIntentStartedOnMainThread(controller, false);
                return;
            }
            return;
        }
        CentralSurfacesImpl.AnonymousClass19 anonymousClass192 = this.callback;
        if (anonymousClass192 == null) {
            throw new IllegalStateException("ActivityTransitionAnimator.callback must be set before using this animator");
        }
        Runner runnerCreateEphemeralRunner = createEphemeralRunner(controller);
        AnimationDelegate animationDelegate = runnerCreateEphemeralRunner.delegate;
        CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
        boolean z4 = ((KeyguardStateControllerImpl) centralSurfacesImpl.mKeyguardStateController).mShowing && !z2;
        if (z4) {
            anonymousClass19 = anonymousClass192;
            z3 = z4;
            r16 = 0;
            remoteAnimationAdapter = null;
        } else {
            long j = TIMINGS.totalDuration;
            anonymousClass19 = anonymousClass192;
            r16 = 0;
            z3 = z4;
            remoteAnimationAdapter = new RemoteAnimationAdapter(runnerCreateEphemeralRunner, j, j - 150);
        }
        if (str != null && remoteAnimationAdapter != null) {
            try {
                ActivityTaskManager.getService().registerRemoteAnimationForNextActivityStart(str, remoteAnimationAdapter, (IBinder) null);
            } catch (RemoteException e) {
                Log.w("ActivityTransitionAnimator", "Unable to register the remote animation", e);
            }
        }
        if (remoteAnimationAdapter != null && controller.getTransitionCookie() != null) {
            TransitionAnimator.Companion.getClass();
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            Runner runnerCreateEphemeralRunner2 = createEphemeralRunner(new DelegateTransitionAnimatorController(controller) { // from class: com.android.systemui.animation.ActivityTransitionAnimator$registerEphemeralReturnAnimation$returnRunner$1
                @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.TransitionAnimator.Controller
                public final boolean isLaunching() {
                    return false;
                }

                @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.ActivityTransitionAnimator.Controller
                public final void onDispose() {
                    super.onDispose();
                    Runnable runnable = (Runnable) ref$ObjectRef.element;
                    if (runnable != null) {
                        runnable.run();
                    }
                }

                @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.ActivityTransitionAnimator.Controller
                public final void onTransitionAnimationCancelled() {
                    super.onTransitionAnimationCancelled();
                    onDispose();
                }

                @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.TransitionAnimator.Controller
                public final void onTransitionAnimationEnd(boolean z5) {
                    super.onTransitionAnimationEnd(z5);
                    onDispose();
                }
            });
            TransitionFilter transitionFilter = new TransitionFilter();
            transitionFilter.mTypeSet = new int[]{2, 4};
            TransitionFilter.Requirement[] requirementArr = new TransitionFilter.Requirement[1];
            TransitionFilter.Requirement requirement = new TransitionFilter.Requirement();
            requirement.mLaunchCookie = controller.getTransitionCookie();
            requirement.mModes = new int[]{2, 4};
            Unit unit = Unit.INSTANCE;
            requirementArr[r16] = requirement;
            transitionFilter.mRequirements = requirementArr;
            boolean z5 = RemoteAnimationRunnerCompat.IS_SHELL_TRANSITION_ENABLED;
            final RemoteTransition remoteTransition = new RemoteTransition(new RemoteAnimationRunnerCompat.AnonymousClass1(runnerCreateEphemeralRunner2), controller.getTransitionCookie() + "_returnTransition");
            final TransitionRegister transitionRegister = this.transitionRegister;
            if (transitionRegister != null) {
                transitionRegister.register$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib(transitionFilter, remoteTransition);
            }
            ref$ObjectRef.element = new Runnable() { // from class: com.android.systemui.animation.ActivityTransitionAnimator$registerEphemeralReturnAnimation$1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityTransitionAnimator.TransitionRegister transitionRegister2 = transitionRegister;
                    if (transitionRegister2 != null) {
                        transitionRegister2.unregister$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib(remoteTransition);
                    }
                }
            };
        }
        int iIntValue = ((Number) function1.mo781invoke(remoteAnimationAdapter)).intValue();
        boolean z6 = (iIntValue == 2 || iIntValue == 0 || (iIntValue == 3 && z3)) ? true : r16;
        KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("launchResult=", iIntValue, " willAnimate=", z6, " hideKeyguardWithAnimation="), z3, "ActivityTransitionAnimator");
        callOnIntentStartedOnMainThread(controller, z6);
        if (!z6) {
            ActivityTransitionAnimator.this.mainExecutor.execute(new ActivityTransitionAnimator$Runner$dispose$1(runnerCreateEphemeralRunner));
            return;
        }
        TransitionAnimator.Companion.getClass();
        runnerCreateEphemeralRunner.postTimeouts();
        if (z3) {
            centralSurfacesImpl.mMainExecutor.execute(new CentralSurfacesImpl$$ExternalSyntheticLambda1(1, anonymousClass19, runnerCreateEphemeralRunner));
        }
    }

    public final void unregister(TransitionCookie transitionCookie) {
        Pair pair = (Pair) this.longLivedTransitions.get(transitionCookie);
        if (pair == null) {
            return;
        }
        TransitionRegister transitionRegister = this.transitionRegister;
        if (transitionRegister != null) {
            transitionRegister.unregister$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib((RemoteTransition) pair.getFirst());
        }
        if (transitionRegister != null) {
            transitionRegister.unregister$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib((RemoteTransition) pair.getSecond());
        }
        this.longLivedTransitions.remove(transitionCookie);
    }

    public ActivityTransitionAnimator(Executor executor, TransitionRegister transitionRegister) {
        this(executor, transitionRegister, null, null, false, false, 60, null);
    }

    public final class TransitionRegister {
        public static final Companion Companion = new Companion(null);
        public final IShellTransitions iShellTransitions;
        public final ShellTransitions shellTransitions;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        private TransitionRegister(ShellTransitions shellTransitions, IShellTransitions iShellTransitions) {
            this.shellTransitions = shellTransitions;
            this.iShellTransitions = iShellTransitions;
        }

        public final void register$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib(TransitionFilter transitionFilter, RemoteTransition remoteTransition) {
            ShellTransitions shellTransitions = this.shellTransitions;
            if (shellTransitions != null) {
                shellTransitions.registerRemote(transitionFilter, remoteTransition);
            }
            IShellTransitions iShellTransitions = this.iShellTransitions;
            if (iShellTransitions != null) {
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((Transitions.IShellTransitionsImpl) iShellTransitions).mTransitions, "registerRemote", new Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda0(transitionFilter, remoteTransition, 0), false);
            }
        }

        public final void unregister$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib(RemoteTransition remoteTransition) {
            ShellTransitions shellTransitions = this.shellTransitions;
            if (shellTransitions != null) {
                shellTransitions.unregisterRemote(remoteTransition);
            }
            IShellTransitions iShellTransitions = this.iShellTransitions;
            if (iShellTransitions != null) {
                ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((Transitions.IShellTransitionsImpl) iShellTransitions).mTransitions, "unregisterRemote", new Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda1(remoteTransition, 0), false);
            }
        }

        public /* synthetic */ TransitionRegister(ShellTransitions shellTransitions, IShellTransitions iShellTransitions, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : shellTransitions, (i & 2) != 0 ? null : iShellTransitions);
        }
    }

    public ActivityTransitionAnimator(Executor executor, TransitionRegister transitionRegister, TransitionAnimator transitionAnimator) {
        this(executor, transitionRegister, transitionAnimator, null, false, false, 56, null);
    }

    public ActivityTransitionAnimator(Executor executor, TransitionRegister transitionRegister, TransitionAnimator transitionAnimator, TransitionAnimator transitionAnimator2) {
        this(executor, transitionRegister, transitionAnimator, transitionAnimator2, false, false, 48, null);
    }

    public abstract class ControllerFactory {
        public final ComponentName component;
        public final TransitionCookie cookie;
        public final Integer launchCujType;
        public final Integer returnCujType;

        public ControllerFactory(TransitionCookie transitionCookie, ComponentName componentName, Integer num, Integer num2) {
            this.cookie = transitionCookie;
            this.component = componentName;
            this.launchCujType = num;
            this.returnCujType = num2;
        }

        public abstract Object createController(boolean z, ContinuationImpl continuationImpl);

        public /* synthetic */ ControllerFactory(TransitionCookie transitionCookie, ComponentName componentName, Integer num, Integer num2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(transitionCookie, componentName, (i & 4) != 0 ? null : num, (i & 8) != 0 ? null : num2);
        }
    }

    public ActivityTransitionAnimator(Executor executor, TransitionRegister transitionRegister, TransitionAnimator transitionAnimator, TransitionAnimator transitionAnimator2, boolean z) {
        this(executor, transitionRegister, transitionAnimator, transitionAnimator2, z, false, 32, null);
    }

    public ActivityTransitionAnimator(Executor executor, IShellTransitions iShellTransitions) {
        this(executor, iShellTransitions, (TransitionAnimator) null, (TransitionAnimator) null, false, 28, (DefaultConstructorMarker) null);
    }

    public ActivityTransitionAnimator(Executor executor, IShellTransitions iShellTransitions, TransitionAnimator transitionAnimator) {
        this(executor, iShellTransitions, transitionAnimator, (TransitionAnimator) null, false, 24, (DefaultConstructorMarker) null);
    }

    public ActivityTransitionAnimator(Executor executor, IShellTransitions iShellTransitions, TransitionAnimator transitionAnimator, TransitionAnimator transitionAnimator2) {
        this(executor, iShellTransitions, transitionAnimator, transitionAnimator2, false, 16, (DefaultConstructorMarker) null);
    }

    public ActivityTransitionAnimator(Executor executor, ShellTransitions shellTransitions) {
        this(executor, shellTransitions, (TransitionAnimator) null, (TransitionAnimator) null, false, 28, (DefaultConstructorMarker) null);
    }

    public final class Runner extends IRemoteAnimationRunner.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final Callback callback;
        public Controller controller;
        public final Function1 controllerFactory;
        public AnimationDelegate delegate;
        public final Listener listener;
        public final CoroutineScope scope;
        public final TransitionAnimator transitionAnimator;

        private Runner(Controller controller, Function1 function1, CoroutineScope coroutineScope, Callback callback, TransitionAnimator transitionAnimator, Listener listener) {
            this.controller = controller;
            this.controllerFactory = function1;
            this.scope = coroutineScope;
            this.callback = callback;
            this.transitionAnimator = transitionAnimator;
            this.listener = listener;
            this.delegate = null;
            if (controller != null) {
                createDelegate(controller);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static final Object access$setUp(Runner runner, Function1 function1, ContinuationImpl continuationImpl) {
            ActivityTransitionAnimator$Runner$setUp$1 activityTransitionAnimator$Runner$setUp$1;
            runner.getClass();
            if (continuationImpl instanceof ActivityTransitionAnimator$Runner$setUp$1) {
                activityTransitionAnimator$Runner$setUp$1 = (ActivityTransitionAnimator$Runner$setUp$1) continuationImpl;
                int i = activityTransitionAnimator$Runner$setUp$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    activityTransitionAnimator$Runner$setUp$1.label = i - Integer.MIN_VALUE;
                } else {
                    activityTransitionAnimator$Runner$setUp$1 = new ActivityTransitionAnimator$Runner$setUp$1(runner, continuationImpl);
                }
            }
            Object objMo781invoke = activityTransitionAnimator$Runner$setUp$1.result;
            Object obj = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = activityTransitionAnimator$Runner$setUp$1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(objMo781invoke);
                activityTransitionAnimator$Runner$setUp$1.L$0 = runner;
                activityTransitionAnimator$Runner$setUp$1.label = 1;
                objMo781invoke = function1.mo781invoke(activityTransitionAnimator$Runner$setUp$1);
                if (objMo781invoke == obj) {
                    return obj;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                runner = (Runner) activityTransitionAnimator$Runner$setUp$1.L$0;
                ResultKt.throwOnFailure(objMo781invoke);
            }
            runner.createDelegate((Controller) objMo781invoke);
            return Unit.INSTANCE;
        }

        public final void createDelegate(Controller controller) {
            ActivityTransitionAnimator activityTransitionAnimator = ActivityTransitionAnimator.this;
            Executor executor = activityTransitionAnimator.mainExecutor;
            Callback callback = this.callback;
            DelegatingAnimationCompletionListener delegatingAnimationCompletionListener = new DelegatingAnimationCompletionListener(activityTransitionAnimator, this.listener, new ActivityTransitionAnimator$Runner$createDelegate$1(this));
            TransitionAnimator transitionAnimator = this.transitionAnimator;
            ActivityTransitionAnimator activityTransitionAnimator2 = ActivityTransitionAnimator.this;
            this.delegate = new AnimationDelegate(executor, controller, callback, delegatingAnimationCompletionListener, transitionAnimator, activityTransitionAnimator2.disableWmTimeout, activityTransitionAnimator2.skipReparentTransaction);
        }

        public final void initAndRun(IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback, Function1 function1) {
            Controller controller = this.controller;
            Function1 function12 = this.controllerFactory;
            if (controller == null) {
                if (function12 == null) {
                    if (iRemoteAnimationFinishedCallback != null) {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                        return;
                    }
                    return;
                } else {
                    CoroutineScope coroutineScope = this.scope;
                    if (coroutineScope != null) {
                        BuildersKt.launch$default(coroutineScope, null, null, new ActivityTransitionAnimator$Runner$initAndRun$1(iRemoteAnimationFinishedCallback, this, function12, function1, null), 3);
                        return;
                    }
                    return;
                }
            }
            if (this.delegate == null) {
                createDelegate(controller);
            }
            AnimationDelegate animationDelegate = this.delegate;
            if (animationDelegate != null) {
                ActivityTransitionAnimator.this.mainExecutor.execute(new ActivityTransitionAnimator$Runner$startAnimation$1(function1, animationDelegate));
                return;
            }
            Log.i("ActivityTransitionAnimator", "startAnimation called after completion");
            if (iRemoteAnimationFinishedCallback != null) {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            }
        }

        public final void onAnimationCancelled() {
            final AnimationDelegate animationDelegate = this.delegate;
            if (animationDelegate != null) {
                ActivityTransitionAnimator.this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.animation.ActivityTransitionAnimator$Runner$onAnimationCancelled$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActivityTransitionAnimator.AnimationDelegate animationDelegate2 = animationDelegate;
                        Handler handler = animationDelegate2.timeoutHandler;
                        if (handler != null) {
                            handler.removeCallbacks(animationDelegate2.onTimeout);
                            handler.removeCallbacks(animationDelegate2.onLongTimeout);
                        }
                        if (animationDelegate2.timedOut) {
                            return;
                        }
                        Log.i("ActivityTransitionAnimator", "Remote animation was cancelled");
                        animationDelegate2.cancelled = true;
                        TransitionAnimator.Animation animation = animationDelegate2.animation;
                        if (animation != null) {
                            animation.cancel();
                        }
                        if (ActivityTransitionAnimator.DEBUG_TRANSITION_ANIMATION) {
                            Log.d("ActivityTransitionAnimator", "Calling controller.onTransitionAnimationCancelled() [remote animation cancelled]");
                        }
                        ActivityTransitionAnimator.Controller.Companion companion = ActivityTransitionAnimator.Controller.Companion;
                        animationDelegate2.controller.onTransitionAnimationCancelled();
                        ActivityTransitionAnimator.Listener listener = animationDelegate2.listener;
                        if (listener != null) {
                            listener.onTransitionAnimationCancelled();
                        }
                    }
                });
            } else {
                Log.wtf("ActivityTransitionAnimator", "onAnimationCancelled called after completion");
            }
        }

        public final void onAnimationStart(final int i, final RemoteAnimationTarget[] remoteAnimationTargetArr, final RemoteAnimationTarget[] remoteAnimationTargetArr2, final RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            initAndRun(iRemoteAnimationFinishedCallback, new Function1(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback) { // from class: com.android.systemui.animation.ActivityTransitionAnimator$Runner$$ExternalSyntheticLambda0
                public final /* synthetic */ RemoteAnimationTarget[] f$1;
                public final /* synthetic */ RemoteAnimationTarget[] f$3;
                public final /* synthetic */ IRemoteAnimationFinishedCallback f$4;

                {
                    this.f$1 = remoteAnimationTargetArr;
                    this.f$3 = remoteAnimationTargetArr3;
                    this.f$4 = iRemoteAnimationFinishedCallback;
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    RemoteAnimationTarget remoteAnimationTarget;
                    RemoteAnimationTarget[] remoteAnimationTargetArr4 = this.f$1;
                    RemoteAnimationTarget[] remoteAnimationTargetArr5 = this.f$3;
                    IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = this.f$4;
                    ActivityTransitionAnimator.AnimationDelegate animationDelegate = (ActivityTransitionAnimator.AnimationDelegate) obj;
                    int i2 = ActivityTransitionAnimator.Runner.$r8$clinit;
                    RemoteAnimationTarget upAnimation = animationDelegate.setUpAnimation(remoteAnimationTargetArr4, iRemoteAnimationFinishedCallback2);
                    if (upAnimation != null) {
                        ActivityTransitionAnimator.Controller controller = animationDelegate.controller;
                        if (controller.getWindowAnimatorState() != null) {
                            TransitionAnimator.Companion.getClass();
                            controller.isLaunching();
                            animationDelegate.startAnimation(upAnimation, null, false, null, null, iRemoteAnimationFinishedCallback2);
                        } else if (remoteAnimationTargetArr5 != null) {
                            for (RemoteAnimationTarget remoteAnimationTarget2 : remoteAnimationTargetArr5) {
                                if (remoteAnimationTarget2.windowType == 2019) {
                                    remoteAnimationTarget = remoteAnimationTarget2;
                                    break;
                                }
                            }
                            remoteAnimationTarget = null;
                            animationDelegate.startAnimation(upAnimation, remoteAnimationTarget, false, null, null, iRemoteAnimationFinishedCallback2);
                        } else {
                            remoteAnimationTarget = null;
                            animationDelegate.startAnimation(upAnimation, remoteAnimationTarget, false, null, null, iRemoteAnimationFinishedCallback2);
                        }
                    }
                    return Unit.INSTANCE;
                }
            });
        }

        public final void postTimeouts() {
            Handler handler;
            Controller controller = this.controller;
            if (controller != null && this.delegate == null) {
                createDelegate(controller);
            }
            AnimationDelegate animationDelegate = this.delegate;
            if (animationDelegate == null || (handler = animationDelegate.timeoutHandler) == null) {
                return;
            }
            handler.postDelayed(animationDelegate.onTimeout, 1000L);
            handler.postDelayed(animationDelegate.onLongTimeout, 5000L);
        }

        public final void takeOverAnimation(final RemoteAnimationTarget[] remoteAnimationTargetArr, final WindowAnimationState[] windowAnimationStateArr, final SurfaceControl.Transaction transaction, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            TransitionAnimator.Companion.getClass();
            initAndRun(iRemoteAnimationFinishedCallback, new Function1() { // from class: com.android.systemui.animation.ActivityTransitionAnimator$Runner$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    RemoteAnimationTarget[] remoteAnimationTargetArr2 = remoteAnimationTargetArr;
                    WindowAnimationState[] windowAnimationStateArr2 = windowAnimationStateArr;
                    SurfaceControl.Transaction transaction2 = transaction;
                    IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = iRemoteAnimationFinishedCallback;
                    ActivityTransitionAnimator.AnimationDelegate animationDelegate = (ActivityTransitionAnimator.AnimationDelegate) obj;
                    int i = ActivityTransitionAnimator.Runner.$r8$clinit;
                    RemoteAnimationTarget upAnimation = animationDelegate.setUpAnimation(remoteAnimationTargetArr2, iRemoteAnimationFinishedCallback2);
                    if (upAnimation != null) {
                        remoteAnimationTargetArr2.getClass();
                        WindowAnimationState windowAnimationState = windowAnimationStateArr2[ArraysKt___ArraysKt.indexOf(remoteAnimationTargetArr2, upAnimation)];
                        animationDelegate.startAnimation(upAnimation, null, (animationDelegate.controller.isLaunching() || windowAnimationState == null || transaction2 == null) ? false : true, windowAnimationState, transaction2, iRemoteAnimationFinishedCallback2);
                    }
                    return Unit.INSTANCE;
                }
            });
        }

        public /* synthetic */ Runner(ActivityTransitionAnimator activityTransitionAnimator, Controller controller, Function1 function1, CoroutineScope coroutineScope, Callback callback, TransitionAnimator transitionAnimator, Listener listener, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(controller, function1, (i & 4) != 0 ? null : coroutineScope, callback, transitionAnimator, listener);
        }

        public /* synthetic */ Runner(ActivityTransitionAnimator activityTransitionAnimator, Controller controller, Callback callback, TransitionAnimator transitionAnimator, Listener listener, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(activityTransitionAnimator, controller, callback, transitionAnimator, (i & 8) != 0 ? null : listener);
        }

        public Runner(ActivityTransitionAnimator activityTransitionAnimator, Controller controller, Callback callback, TransitionAnimator transitionAnimator, Listener listener) {
            this(activityTransitionAnimator, controller, null, null, callback, transitionAnimator, listener, 4, null);
        }

        public /* synthetic */ Runner(ActivityTransitionAnimator activityTransitionAnimator, CoroutineScope coroutineScope, Callback callback, TransitionAnimator transitionAnimator, Listener listener, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(activityTransitionAnimator, coroutineScope, callback, transitionAnimator, (i & 8) != 0 ? null : listener, function1);
        }

        public Runner(ActivityTransitionAnimator activityTransitionAnimator, CoroutineScope coroutineScope, Callback callback, TransitionAnimator transitionAnimator, Listener listener, Function1 function1) {
            this((Controller) null, function1, coroutineScope, callback, transitionAnimator, listener);
        }

        public static /* synthetic */ void getDelegate$annotations() {
        }
    }

    public ActivityTransitionAnimator(Executor executor, ShellTransitions shellTransitions, TransitionAnimator transitionAnimator) {
        this(executor, shellTransitions, transitionAnimator, (TransitionAnimator) null, false, 24, (DefaultConstructorMarker) null);
    }

    public ActivityTransitionAnimator(Executor executor, ShellTransitions shellTransitions, TransitionAnimator transitionAnimator, TransitionAnimator transitionAnimator2) {
        this(executor, shellTransitions, transitionAnimator, transitionAnimator2, false, 16, (DefaultConstructorMarker) null);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.animation.ActivityTransitionAnimator$lifecycleListener$1] */
    public ActivityTransitionAnimator(Executor executor, TransitionRegister transitionRegister, TransitionAnimator transitionAnimator, TransitionAnimator transitionAnimator2, boolean z, boolean z2) {
        this.mainExecutor = executor;
        this.transitionRegister = transitionRegister;
        this.transitionAnimator = transitionAnimator;
        this.dialogToAppAnimator = transitionAnimator2;
        this.disableWmTimeout = z;
        this.skipReparentTransaction = z2;
        this.listeners = new LinkedHashSet();
        this.lifecycleListener = new Listener() { // from class: com.android.systemui.animation.ActivityTransitionAnimator$lifecycleListener$1
            @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
            public final void onTransitionAnimationCancelled() {
                Iterator it = new LinkedHashSet(this.this$0.listeners).iterator();
                while (it.hasNext()) {
                    ((ActivityTransitionAnimator.Listener) it.next()).onTransitionAnimationCancelled();
                }
            }

            @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
            public final void onTransitionAnimationEnd() {
                Iterator it = new LinkedHashSet(this.this$0.listeners).iterator();
                while (it.hasNext()) {
                    ((ActivityTransitionAnimator.Listener) it.next()).onTransitionAnimationEnd();
                }
            }

            @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
            public final void onTransitionAnimationProgress(float f) {
                Iterator it = new LinkedHashSet(this.this$0.listeners).iterator();
                while (it.hasNext()) {
                    ((ActivityTransitionAnimator.Listener) it.next()).onTransitionAnimationProgress(f);
                }
            }

            @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
            public final void onTransitionAnimationStart() {
                Iterator it = new LinkedHashSet(this.this$0.listeners).iterator();
                while (it.hasNext()) {
                    ((ActivityTransitionAnimator.Listener) it.next()).onTransitionAnimationStart();
                }
            }
        };
        this.longLivedTransitions = new HashMap();
    }

    public interface Controller extends TransitionAnimator.Controller {
        public static final Companion Companion = Companion.$$INSTANCE;

        public final class Companion {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }

            public static GhostedViewTransitionAnimatorController fromView(View view, Integer num, TransitionCookie transitionCookie, ComponentName componentName, Integer num2, boolean z) {
                if (!(view instanceof LaunchableView)) {
                    throw new IllegalArgumentException("An ActivityTransitionAnimator.Controller was created from a View that does not implement LaunchableView. This can lead to subtle bugs where the visibility of the View we are launching from is not what we expected.");
                }
                if (view.getParent() instanceof ViewGroup) {
                    return new GhostedViewTransitionAnimatorController(view, num, transitionCookie, componentName, num2, z, null, null, 192, null);
                }
                Log.e("ActivityTransitionAnimator", "Skipping animation as view " + view + " is not attached to a ViewGroup", new Exception());
                return null;
            }

            public static /* synthetic */ GhostedViewTransitionAnimatorController fromView$default(Companion companion, View view, Integer num, int i) {
                if ((i & 2) != 0) {
                    num = null;
                }
                companion.getClass();
                return fromView(view, num, null, null, null, true);
            }
        }

        default ComponentName getComponent() {
            return null;
        }

        default TransitionCookie getTransitionCookie() {
            return null;
        }

        default boolean isBelowAnimatingWindow() {
            return false;
        }

        default boolean isDialogLaunch() {
            return false;
        }

        default void onDispose() {
        }

        default void onTransitionAnimationCancelled() {
        }

        default void onIntentStarted(boolean z) {
        }
    }

    public interface Listener {
        void onTransitionAnimationEnd();

        void onTransitionAnimationProgress(float f);

        void onTransitionAnimationStart();

        default void onTransitionAnimationCancelled() {
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ ActivityTransitionAnimator(Executor executor, TransitionRegister transitionRegister, TransitionAnimator transitionAnimator, TransitionAnimator transitionAnimator2, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        TransitionRegister transitionRegister2 = (i & 2) != 0 ? null : transitionRegister;
        int i2 = i & 4;
        Companion companion = Companion;
        this(executor, transitionRegister2, i2 != 0 ? Companion.access$defaultTransitionAnimator(companion, executor) : transitionAnimator, (i & 8) != 0 ? Companion.access$defaultDialogToAppAnimator(companion, executor) : transitionAnimator2, (i & 16) != 0 ? false : z, (i & 32) != 0 ? false : z2);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ ActivityTransitionAnimator(Executor executor, ShellTransitions shellTransitions, TransitionAnimator transitionAnimator, TransitionAnimator transitionAnimator2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        int i2 = i & 4;
        Companion companion = Companion;
        this(executor, shellTransitions, i2 != 0 ? Companion.access$defaultTransitionAnimator(companion, executor) : transitionAnimator, (i & 8) != 0 ? Companion.access$defaultDialogToAppAnimator(companion, executor) : transitionAnimator2, (i & 16) != 0 ? false : z);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ActivityTransitionAnimator(Executor executor, ShellTransitions shellTransitions, TransitionAnimator transitionAnimator, TransitionAnimator transitionAnimator2, boolean z) {
        this(executor, new TransitionRegister(shellTransitions, null, 2, 0 == true ? 1 : 0), transitionAnimator, transitionAnimator2, z, false, 32, null);
        TransitionRegister.Companion.getClass();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ ActivityTransitionAnimator(Executor executor, IShellTransitions iShellTransitions, TransitionAnimator transitionAnimator, TransitionAnimator transitionAnimator2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        int i2 = i & 4;
        Companion companion = Companion;
        this(executor, iShellTransitions, i2 != 0 ? Companion.access$defaultTransitionAnimator(companion, executor) : transitionAnimator, (i & 8) != 0 ? Companion.access$defaultDialogToAppAnimator(companion, executor) : transitionAnimator2, (i & 16) != 0 ? false : z);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ActivityTransitionAnimator(Executor executor, IShellTransitions iShellTransitions, TransitionAnimator transitionAnimator, TransitionAnimator transitionAnimator2, boolean z) {
        this(executor, new TransitionRegister(null, iShellTransitions, 1, 0 == true ? 1 : 0), transitionAnimator, transitionAnimator2, z, false, 32, null);
        TransitionRegister.Companion.getClass();
    }
}
