package com.android.systemui.animation;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.app.animation.Interpolators;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.wm.shell.startingsurface.SplashscreenContentDrawer;
import com.android.wm.shell.startingsurface.StartingWindowController;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda12;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.util.Iterator;
import java.util.LinkedHashSet;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ActivityLaunchAnimator {
    public static final long ANIMATION_DELAY_NAV_FADE_IN;
    public static final Companion Companion = new Companion(null);
    public static final LaunchAnimator DEFAULT_DIALOG_TO_APP_ANIMATOR;
    public static final LaunchAnimator DEFAULT_LAUNCH_ANIMATOR;
    public static final LaunchAnimator.Interpolators INTERPOLATORS;
    public static final Interpolator NAV_FADE_IN_INTERPOLATOR;
    public static final PathInterpolator NAV_FADE_OUT_INTERPOLATOR;
    public static final LaunchAnimator.Timings TIMINGS;
    public Callback callback;
    public final LaunchAnimator dialogToAppAnimator;
    public final LaunchAnimator launchAnimator;
    public final ActivityLaunchAnimator$lifecycleListener$1 lifecycleListener;
    public final LinkedHashSet listeners;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AnimationDelegate {
        public LaunchAnimator$startAnimation$3 animation;
        public final Callback callback;
        public boolean cancelled;
        public final Context context;
        public final Controller controller;
        public final Matrix invertMatrix;
        public final LaunchAnimator launchAnimator;
        public final ViewGroup launchContainer;
        public final Listener listener;
        public final Matrix matrix;
        public final ActivityLaunchAnimator$AnimationDelegate$onTimeout$1 onTimeout;
        public boolean timedOut;
        public final SyncRtSurfaceTransactionApplier transactionApplier;
        public final View transactionApplierView;
        public final Rect windowCrop;
        public final RectF windowCropF;

        public AnimationDelegate(Controller controller, Callback callback) {
            this(controller, callback, null, null, 12, null);
        }

        public AnimationDelegate(Controller controller, Callback callback, Listener listener) {
            this(controller, callback, listener, null, 8, null);
        }

        public /* synthetic */ AnimationDelegate(Controller controller, Callback callback, Listener listener, LaunchAnimator launchAnimator, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(controller, callback, (i & 4) != 0 ? null : listener, (i & 8) != 0 ? ActivityLaunchAnimator.DEFAULT_LAUNCH_ANIMATOR : launchAnimator);
        }

        /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.animation.ActivityLaunchAnimator$AnimationDelegate$onTimeout$1] */
        public AnimationDelegate(Controller controller, Callback callback, Listener listener, LaunchAnimator launchAnimator) {
            this.controller = controller;
            this.callback = callback;
            this.listener = listener;
            this.launchAnimator = launchAnimator;
            ViewGroup launchContainer = controller.getLaunchContainer();
            this.launchContainer = launchContainer;
            this.context = launchContainer.getContext();
            View openingWindowSyncView = controller.getOpeningWindowSyncView();
            openingWindowSyncView = openingWindowSyncView == null ? controller.getLaunchContainer() : openingWindowSyncView;
            this.transactionApplierView = openingWindowSyncView;
            this.transactionApplier = new SyncRtSurfaceTransactionApplier(openingWindowSyncView);
            this.matrix = new Matrix();
            this.invertMatrix = new Matrix();
            this.windowCrop = new Rect();
            this.windowCropF = new RectF();
            this.onTimeout = new Runnable() { // from class: com.android.systemui.animation.ActivityLaunchAnimator$AnimationDelegate$onTimeout$1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityLaunchAnimator.AnimationDelegate animationDelegate = ActivityLaunchAnimator.AnimationDelegate.this;
                    if (animationDelegate.cancelled) {
                        return;
                    }
                    Log.i("ActivityLaunchAnimator", "Remote animation timed out");
                    animationDelegate.timedOut = true;
                    ActivityLaunchAnimator.Controller.Companion companion = ActivityLaunchAnimator.Controller.Companion;
                    animationDelegate.controller.onLaunchAnimationCancelled(null);
                }
            };
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface PendingIntentStarter {
        int startPendingIntent(RemoteAnimationAdapter remoteAnimationAdapter);
    }

    static {
        LaunchAnimator.Timings timings = new LaunchAnimator.Timings(500L, 0L, 150L, 150L, 183L);
        TIMINGS = timings;
        LaunchAnimator.Timings timings2 = new LaunchAnimator.Timings(timings.totalDuration, timings.contentBeforeFadeOutDelay, 200L, 200L, timings.contentAfterFadeInDuration);
        Interpolator interpolator = Interpolators.EMPHASIZED;
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.cubicTo(0.1217f, 0.0462f, 0.15f, 0.4686f, 0.1667f, 0.66f);
        path.cubicTo(0.1834f, 0.8878f, 0.1667f, 1.0f, 1.0f, 1.0f);
        LaunchAnimator.Interpolators interpolators = new LaunchAnimator.Interpolators(interpolator, new PathInterpolator(path), Interpolators.LINEAR_OUT_SLOW_IN, new PathInterpolator(0.0f, 0.0f, 0.6f, 1.0f));
        INTERPOLATORS = interpolators;
        DEFAULT_LAUNCH_ANIMATOR = new LaunchAnimator(timings, interpolators);
        DEFAULT_DIALOG_TO_APP_ANIMATOR = new LaunchAnimator(timings2, interpolators);
        ANIMATION_DELAY_NAV_FADE_IN = timings.totalDuration - 266;
        NAV_FADE_IN_INTERPOLATOR = Interpolators.STANDARD_DECELERATE;
        NAV_FADE_OUT_INTERPOLATOR = new PathInterpolator(0.2f, 0.0f, 1.0f, 1.0f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ActivityLaunchAnimator() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public static void callOnIntentStartedOnMainThread(final Controller controller, final boolean z) {
        if (Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            controller.onIntentStarted(z);
        } else {
            controller.getLaunchContainer().getContext().getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.animation.ActivityLaunchAnimator$callOnIntentStartedOnMainThread$1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityLaunchAnimator.Controller.this.onIntentStarted(z);
                }
            });
        }
    }

    public final Runner createRunner(Controller controller) {
        LaunchAnimator launchAnimator = controller.isDialogLaunch() ? this.dialogToAppAnimator : this.launchAnimator;
        Callback callback = this.callback;
        Intrinsics.checkNotNull(callback);
        return new Runner(this, controller, callback, launchAnimator, this.lifecycleListener);
    }

    public final void startIntentWithAnimation(Controller controller, boolean z, String str, boolean z2, Function1 function1) {
        RemoteAnimationAdapter remoteAnimationAdapter;
        boolean z3 = false;
        if (controller == null || !z) {
            Log.i("ActivityLaunchAnimator", "Starting intent with no animation");
            function1.invoke(null);
            if (controller != null) {
                callOnIntentStartedOnMainThread(controller, false);
                return;
            }
            return;
        }
        Callback callback = this.callback;
        if (callback == null) {
            throw new IllegalStateException("ActivityLaunchAnimator.callback must be set before using this animator");
        }
        Runner createRunner = createRunner(controller);
        CentralSurfacesImpl.C301724 c301724 = (CentralSurfacesImpl.C301724) callback;
        CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
        boolean z4 = ((KeyguardStateControllerImpl) centralSurfacesImpl.mKeyguardStateController).mShowing && !z2;
        if (z4) {
            remoteAnimationAdapter = null;
        } else {
            long j = TIMINGS.totalDuration;
            remoteAnimationAdapter = new RemoteAnimationAdapter(createRunner, j, j - 150);
        }
        if (str != null && remoteAnimationAdapter != null) {
            try {
                ActivityTaskManager.getService().registerRemoteAnimationForNextActivityStart(str, remoteAnimationAdapter, (IBinder) null);
            } catch (RemoteException e) {
                Log.w("ActivityLaunchAnimator", "Unable to register the remote animation", e);
            }
        }
        int intValue = ((Number) function1.invoke(remoteAnimationAdapter)).intValue();
        int i = 2;
        if (intValue == 2 || intValue == 0 || (intValue == 3 && z4)) {
            z3 = true;
        }
        StringBuilder m76m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m76m("launchResult=", intValue, " willAnimate=", z3, " hideKeyguardWithAnimation=");
        m76m.append(z4);
        Log.i("ActivityLaunchAnimator", m76m.toString());
        callOnIntentStartedOnMainThread(controller, z3);
        if (z3) {
            AnimationDelegate animationDelegate = createRunner.delegate;
            animationDelegate.launchContainer.postDelayed(animationDelegate.onTimeout, 1000L);
            if (z4) {
                ((ExecutorImpl) centralSurfacesImpl.mMainExecutor).execute(new CentralSurfacesImpl$$ExternalSyntheticLambda12(i, c301724, createRunner));
            }
        }
    }

    public final void startPendingIntentWithAnimation(Controller controller, boolean z, String str, final PendingIntentStarter pendingIntentStarter) {
        startIntentWithAnimation(controller, z, str, false, new Function1() { // from class: com.android.systemui.animation.ActivityLaunchAnimator$startPendingIntentWithAnimation$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Integer.valueOf(ActivityLaunchAnimator.PendingIntentStarter.this.startPendingIntent((RemoteAnimationAdapter) obj));
            }
        });
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Runner extends IRemoteAnimationRunner.Stub {
        public final Context context;
        public final AnimationDelegate delegate;

        public /* synthetic */ Runner(ActivityLaunchAnimator activityLaunchAnimator, Controller controller, Callback callback, LaunchAnimator launchAnimator, Listener listener, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(activityLaunchAnimator, controller, callback, (i & 4) != 0 ? ActivityLaunchAnimator.DEFAULT_LAUNCH_ANIMATOR : launchAnimator, (i & 8) != 0 ? null : listener);
        }

        public final void onAnimationCancelled() {
            this.context.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.animation.ActivityLaunchAnimator$Runner$onAnimationCancelled$1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityLaunchAnimator.AnimationDelegate animationDelegate = ActivityLaunchAnimator.Runner.this.delegate;
                    if (animationDelegate.timedOut) {
                        return;
                    }
                    Log.i("ActivityLaunchAnimator", "Remote animation was cancelled");
                    animationDelegate.cancelled = true;
                    animationDelegate.launchContainer.removeCallbacks(animationDelegate.onTimeout);
                    LaunchAnimator$startAnimation$3 launchAnimator$startAnimation$3 = animationDelegate.animation;
                    if (launchAnimator$startAnimation$3 != null) {
                        launchAnimator$startAnimation$3.$cancelled.element = true;
                        launchAnimator$startAnimation$3.$animator.cancel();
                    }
                    ActivityLaunchAnimator.Controller.Companion companion = ActivityLaunchAnimator.Controller.Companion;
                    animationDelegate.controller.onLaunchAnimationCancelled(null);
                }
            });
        }

        public final void onAnimationStart(final int i, final RemoteAnimationTarget[] remoteAnimationTargetArr, final RemoteAnimationTarget[] remoteAnimationTargetArr2, final RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            this.context.getMainExecutor().execute(new Runnable(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback) { // from class: com.android.systemui.animation.ActivityLaunchAnimator$Runner$onAnimationStart$1
                public final /* synthetic */ RemoteAnimationTarget[] $apps;
                public final /* synthetic */ IRemoteAnimationFinishedCallback $finishedCallback;
                public final /* synthetic */ RemoteAnimationTarget[] $nonApps;

                {
                    this.$apps = remoteAnimationTargetArr;
                    this.$nonApps = remoteAnimationTargetArr3;
                    this.$finishedCallback = iRemoteAnimationFinishedCallback;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    RemoteAnimationTarget remoteAnimationTarget;
                    RemoteAnimationTarget remoteAnimationTarget2;
                    int i2;
                    ActivityLaunchAnimator.AnimationDelegate animationDelegate = ActivityLaunchAnimator.Runner.this.delegate;
                    RemoteAnimationTarget[] remoteAnimationTargetArr4 = this.$apps;
                    RemoteAnimationTarget[] remoteAnimationTargetArr5 = this.$nonApps;
                    IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = this.$finishedCallback;
                    animationDelegate.launchContainer.removeCallbacks(animationDelegate.onTimeout);
                    if (animationDelegate.timedOut) {
                        if (iRemoteAnimationFinishedCallback2 != null) {
                            try {
                                iRemoteAnimationFinishedCallback2.onAnimationFinished();
                                return;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        return;
                    }
                    if (animationDelegate.cancelled) {
                        return;
                    }
                    if (remoteAnimationTargetArr4 != null) {
                        ArrayIterator arrayIterator = new ArrayIterator(remoteAnimationTargetArr4);
                        RemoteAnimationTarget remoteAnimationTarget3 = null;
                        while (true) {
                            if (!arrayIterator.hasNext()) {
                                remoteAnimationTarget = remoteAnimationTarget3;
                                break;
                            }
                            remoteAnimationTarget = (RemoteAnimationTarget) arrayIterator.next();
                            if (remoteAnimationTarget.mode == 0) {
                                if (remoteAnimationTarget.taskInfo != null && !remoteAnimationTarget.hasAnimatingParent) {
                                    break;
                                } else if (remoteAnimationTarget3 == null) {
                                    remoteAnimationTarget3 = remoteAnimationTarget;
                                }
                            }
                        }
                    } else {
                        remoteAnimationTarget = null;
                    }
                    ActivityLaunchAnimator.Controller controller = animationDelegate.controller;
                    if (remoteAnimationTarget == null) {
                        Log.i("ActivityLaunchAnimator", "Aborting the animation as no window is opening");
                        animationDelegate.launchContainer.removeCallbacks(animationDelegate.onTimeout);
                        if (iRemoteAnimationFinishedCallback2 != null) {
                            try {
                                iRemoteAnimationFinishedCallback2.onAnimationFinished();
                            } catch (RemoteException e2) {
                                e2.printStackTrace();
                            }
                        }
                        ActivityLaunchAnimator.Controller.Companion companion = ActivityLaunchAnimator.Controller.Companion;
                        controller.onLaunchAnimationCancelled(null);
                        return;
                    }
                    if (remoteAnimationTargetArr5 != null) {
                        for (RemoteAnimationTarget remoteAnimationTarget4 : remoteAnimationTargetArr5) {
                            if (remoteAnimationTarget4.windowType == 2019) {
                                remoteAnimationTarget2 = remoteAnimationTarget4;
                                break;
                            }
                        }
                    }
                    remoteAnimationTarget2 = null;
                    Rect rect = remoteAnimationTarget.screenSpaceBounds;
                    LaunchAnimator.State state = new LaunchAnimator.State(rect.top, rect.bottom, rect.left, rect.right, 0.0f, 0.0f, 48, null);
                    ActivityManager.RunningTaskInfo runningTaskInfo = remoteAnimationTarget.taskInfo;
                    if (runningTaskInfo != null) {
                        CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                        if (centralSurfacesImpl.mStartingSurfaceOptional.isPresent()) {
                            i2 = ((StartingWindowController.StartingSurfaceImpl) centralSurfacesImpl.mStartingSurfaceOptional.get()).getBackgroundColor(runningTaskInfo);
                        } else {
                            Log.w("CentralSurfaces", "No starting surface, defaulting to SystemBGColor");
                            i2 = SplashscreenContentDrawer.getSystemBGColor();
                        }
                    } else {
                        i2 = remoteAnimationTarget.backgroundColor;
                    }
                    int i3 = i2;
                    float windowCornerRadius = animationDelegate.launchAnimator.m96xb114753a(controller.getLaunchContainer(), state) ? ScreenDecorationsUtils.getWindowCornerRadius(animationDelegate.context) : 0.0f;
                    state.topCornerRadius = windowCornerRadius;
                    state.bottomCornerRadius = windowCornerRadius;
                    animationDelegate.animation = animationDelegate.launchAnimator.startAnimation(new ActivityLaunchAnimator.Controller(animationDelegate, iRemoteAnimationFinishedCallback2, remoteAnimationTarget, remoteAnimationTarget2) { // from class: com.android.systemui.animation.ActivityLaunchAnimator$AnimationDelegate$startAnimation$controller$1
                        public final /* synthetic */ ActivityLaunchAnimator.Controller $$delegate_0;
                        public final /* synthetic */ IRemoteAnimationFinishedCallback $iCallback;
                        public final /* synthetic */ RemoteAnimationTarget $navigationBar;
                        public final /* synthetic */ RemoteAnimationTarget $window;
                        public final /* synthetic */ ActivityLaunchAnimator.AnimationDelegate this$0;

                        {
                            this.this$0 = animationDelegate;
                            this.$iCallback = iRemoteAnimationFinishedCallback2;
                            this.$window = remoteAnimationTarget;
                            this.$navigationBar = remoteAnimationTarget2;
                            this.$$delegate_0 = ActivityLaunchAnimator.Controller.this;
                        }

                        @Override // com.android.systemui.animation.LaunchAnimator.Controller
                        public final LaunchAnimator.State createAnimatorState() {
                            return this.$$delegate_0.createAnimatorState();
                        }

                        @Override // com.android.systemui.animation.LaunchAnimator.Controller
                        public final ViewGroup getLaunchContainer() {
                            return this.$$delegate_0.getLaunchContainer();
                        }

                        @Override // com.android.systemui.animation.LaunchAnimator.Controller
                        public final View getOpeningWindowSyncView() {
                            return this.$$delegate_0.getOpeningWindowSyncView();
                        }

                        @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
                        public final boolean isBelowAnimatingWindow() {
                            return this.$$delegate_0.isBelowAnimatingWindow();
                        }

                        @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
                        public final boolean isDialogLaunch() {
                            return this.$$delegate_0.isDialogLaunch();
                        }

                        @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
                        public final void onIntentStarted(boolean z) {
                            this.$$delegate_0.onIntentStarted(z);
                        }

                        @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
                        public final void onLaunchAnimationCancelled(Boolean bool) {
                            this.$$delegate_0.onLaunchAnimationCancelled(bool);
                        }

                        @Override // com.android.systemui.animation.LaunchAnimator.Controller
                        public final void onLaunchAnimationEnd(boolean z) {
                            ActivityLaunchAnimator.Listener listener = this.this$0.listener;
                            if (listener != null) {
                                listener.onLaunchAnimationEnd();
                            }
                            IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback3 = this.$iCallback;
                            if (iRemoteAnimationFinishedCallback3 != null) {
                                try {
                                    iRemoteAnimationFinishedCallback3.onAnimationFinished();
                                } catch (RemoteException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            ActivityLaunchAnimator.Controller.this.onLaunchAnimationEnd(z);
                        }

                        @Override // com.android.systemui.animation.LaunchAnimator.Controller
                        public final void onLaunchAnimationProgress(LaunchAnimator.State state2, float f, float f2) {
                            float f3;
                            boolean z = state2.visible;
                            ActivityLaunchAnimator.AnimationDelegate animationDelegate2 = this.this$0;
                            if (!z && animationDelegate2.transactionApplierView.getViewRootImpl() != null) {
                                RemoteAnimationTarget remoteAnimationTarget5 = this.$window;
                                if (remoteAnimationTarget5.leash.isValid()) {
                                    Rect rect2 = remoteAnimationTarget5.screenSpaceBounds;
                                    int i4 = rect2.left;
                                    int i5 = rect2.right;
                                    float f4 = (i4 + i5) / 2.0f;
                                    int i6 = rect2.top;
                                    float f5 = (i6 + r13) / 2.0f;
                                    float f6 = rect2.bottom - i6;
                                    float max = Math.max((state2.right - state2.left) / (i5 - i4), (state2.bottom - state2.top) / f6);
                                    Matrix matrix = animationDelegate2.matrix;
                                    matrix.reset();
                                    matrix.setScale(max, max, f4, f5);
                                    int i7 = state2.left;
                                    matrix.postTranslate((((state2.right - i7) / 2.0f) + i7) - f4, (((f6 * max) - f6) / 2.0f) + (state2.top - rect2.top));
                                    float f7 = state2.left - rect2.left;
                                    float f8 = state2.top - rect2.top;
                                    RectF rectF = animationDelegate2.windowCropF;
                                    rectF.set(f7, f8, (state2.right - r3) + f7, (state2.bottom - r5) + f8);
                                    Matrix matrix2 = animationDelegate2.invertMatrix;
                                    matrix.invert(matrix2);
                                    matrix2.mapRect(rectF);
                                    int roundToInt = MathKt__MathJVMKt.roundToInt(rectF.left);
                                    int roundToInt2 = MathKt__MathJVMKt.roundToInt(rectF.top);
                                    int roundToInt3 = MathKt__MathJVMKt.roundToInt(rectF.right);
                                    int roundToInt4 = MathKt__MathJVMKt.roundToInt(rectF.bottom);
                                    Rect rect3 = animationDelegate2.windowCrop;
                                    rect3.set(roundToInt, roundToInt2, roundToInt3, roundToInt4);
                                    if (animationDelegate2.controller.isBelowAnimatingWindow()) {
                                        LaunchAnimator.Companion companion2 = LaunchAnimator.Companion;
                                        LaunchAnimator.Timings timings = ActivityLaunchAnimator.TIMINGS;
                                        long j = timings.contentAfterFadeInDelay;
                                        long j2 = timings.contentAfterFadeInDuration;
                                        companion2.getClass();
                                        float progress = LaunchAnimator.Companion.getProgress(timings, f2, j, j2);
                                        ActivityLaunchAnimator.Companion.getClass();
                                        f3 = ActivityLaunchAnimator.INTERPOLATORS.contentAfterFadeInInterpolator.getInterpolation(progress);
                                    } else {
                                        f3 = 1.0f;
                                    }
                                    animationDelegate2.transactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget5.leash).withAlpha(f3).withMatrix(matrix).withWindowCrop(rect3).withCornerRadius(Math.max(state2.topCornerRadius, state2.bottomCornerRadius) / max).withVisibility(true).build()});
                                }
                            }
                            RemoteAnimationTarget remoteAnimationTarget6 = this.$navigationBar;
                            if (remoteAnimationTarget6 != null && animationDelegate2.transactionApplierView.getViewRootImpl() != null && remoteAnimationTarget6.leash.isValid()) {
                                LaunchAnimator.Companion companion3 = LaunchAnimator.Companion;
                                LaunchAnimator.Timings timings2 = ActivityLaunchAnimator.TIMINGS;
                                long j3 = ActivityLaunchAnimator.ANIMATION_DELAY_NAV_FADE_IN;
                                companion3.getClass();
                                float progress2 = LaunchAnimator.Companion.getProgress(timings2, f2, j3, 133L);
                                SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builder = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget6.leash);
                                if (progress2 > 0.0f) {
                                    Matrix matrix3 = animationDelegate2.matrix;
                                    matrix3.reset();
                                    matrix3.setTranslate(0.0f, state2.top - remoteAnimationTarget6.sourceContainerBounds.top);
                                    int i8 = state2.left;
                                    int i9 = state2.right;
                                    int i10 = state2.bottom - state2.top;
                                    Rect rect4 = animationDelegate2.windowCrop;
                                    rect4.set(i8, 0, i9, i10);
                                    builder.withAlpha(((PathInterpolator) ActivityLaunchAnimator.NAV_FADE_IN_INTERPOLATOR).getInterpolation(progress2)).withMatrix(matrix3).withWindowCrop(rect4).withVisibility(true);
                                } else {
                                    builder.withAlpha(1.0f - ActivityLaunchAnimator.NAV_FADE_OUT_INTERPOLATOR.getInterpolation(LaunchAnimator.Companion.getProgress(timings2, f2, 0L, 133L)));
                                }
                                animationDelegate2.transactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{builder.build()});
                            }
                            ActivityLaunchAnimator.Listener listener = animationDelegate2.listener;
                            if (listener != null) {
                                listener.onLaunchAnimationProgress(f2);
                            }
                            ActivityLaunchAnimator.Controller.this.onLaunchAnimationProgress(state2, f, f2);
                        }

                        @Override // com.android.systemui.animation.LaunchAnimator.Controller
                        public final void onLaunchAnimationStart(boolean z) {
                            ActivityLaunchAnimator.Listener listener = this.this$0.listener;
                            if (listener != null) {
                                listener.onLaunchAnimationStart();
                            }
                            ActivityLaunchAnimator.Controller.this.onLaunchAnimationStart(z);
                        }

                        @Override // com.android.systemui.animation.LaunchAnimator.Controller
                        public final void setLaunchContainer(ViewGroup viewGroup) {
                            this.$$delegate_0.setLaunchContainer(viewGroup);
                        }
                    }, state, i3, !r9.isBelowAnimatingWindow(), !r9.isBelowAnimatingWindow());
                }
            });
        }

        public Runner(ActivityLaunchAnimator activityLaunchAnimator, Controller controller, Callback callback, LaunchAnimator launchAnimator, Listener listener) {
            this.context = controller.getLaunchContainer().getContext();
            this.delegate = new AnimationDelegate(controller, callback, listener, launchAnimator);
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.animation.ActivityLaunchAnimator$lifecycleListener$1] */
    public ActivityLaunchAnimator(LaunchAnimator launchAnimator, LaunchAnimator launchAnimator2) {
        this.launchAnimator = launchAnimator;
        this.dialogToAppAnimator = launchAnimator2;
        this.listeners = new LinkedHashSet();
        this.lifecycleListener = new Listener() { // from class: com.android.systemui.animation.ActivityLaunchAnimator$lifecycleListener$1
            @Override // com.android.systemui.animation.ActivityLaunchAnimator.Listener
            public final void onLaunchAnimationEnd() {
                Iterator it = ActivityLaunchAnimator.this.listeners.iterator();
                while (it.hasNext()) {
                    ((ActivityLaunchAnimator.Listener) it.next()).onLaunchAnimationEnd();
                }
            }

            @Override // com.android.systemui.animation.ActivityLaunchAnimator.Listener
            public final void onLaunchAnimationProgress(float f) {
                Iterator it = ActivityLaunchAnimator.this.listeners.iterator();
                while (it.hasNext()) {
                    ((ActivityLaunchAnimator.Listener) it.next()).onLaunchAnimationProgress(f);
                }
            }

            @Override // com.android.systemui.animation.ActivityLaunchAnimator.Listener
            public final void onLaunchAnimationStart() {
                Iterator it = ActivityLaunchAnimator.this.listeners.iterator();
                while (it.hasNext()) {
                    ((ActivityLaunchAnimator.Listener) it.next()).onLaunchAnimationStart();
                }
            }
        };
    }

    public /* synthetic */ ActivityLaunchAnimator(LaunchAnimator launchAnimator, LaunchAnimator launchAnimator2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? DEFAULT_LAUNCH_ANIMATOR : launchAnimator, (i & 2) != 0 ? DEFAULT_DIALOG_TO_APP_ANIMATOR : launchAnimator2);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Controller extends LaunchAnimator.Controller {
        public static final Companion Companion = Companion.$$INSTANCE;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Companion {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }

            public static GhostedViewLaunchAnimatorController fromView(View view, Integer num) {
                if (!(view instanceof LaunchableView)) {
                    throw new IllegalArgumentException("An ActivityLaunchAnimator.Controller was created from a View that does not implement LaunchableView. This can lead to subtle bugs where the visibility of the View we are launching from is not what we expected.");
                }
                if (view.getParent() instanceof ViewGroup) {
                    return new GhostedViewLaunchAnimatorController(view, num, null, 4, null);
                }
                Log.e("ActivityLaunchAnimator", "Skipping animation as view " + view + " is not attached to a ViewGroup", new Exception());
                return null;
            }
        }

        static GhostedViewLaunchAnimatorController fromView(View view, Integer num) {
            Companion.getClass();
            return Companion.fromView(view, num);
        }

        default boolean isBelowAnimatingWindow() {
            return false;
        }

        default boolean isDialogLaunch() {
            return false;
        }

        default void onIntentStarted(boolean z) {
        }

        default void onLaunchAnimationCancelled(Boolean bool) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Listener {
        void onLaunchAnimationEnd();

        void onLaunchAnimationStart();

        default void onLaunchAnimationProgress(float f) {
        }
    }
}
