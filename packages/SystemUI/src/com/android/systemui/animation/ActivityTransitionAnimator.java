package com.android.systemui.animation;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
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
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.shared.Flags;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.wm.shell.shared.IShellTransitions;
import com.android.wm.shell.shared.ShellTransitions;
import com.android.wm.shell.startingsurface.SplashscreenContentDrawer;
import com.android.wm.shell.startingsurface.StartingWindowController;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

public final class ActivityTransitionAnimator {
    public static final long ANIMATION_DELAY_NAV_FADE_IN;
    public static final Companion Companion = new Companion(null);
    public static final boolean DEBUG_TRANSITION_ANIMATION;
    public static final TransitionAnimator.Timings DIALOG_TIMINGS;
    public static final TransitionAnimator.Interpolators INTERPOLATORS;
    public static final Interpolator NAV_FADE_IN_INTERPOLATOR;
    public static final PathInterpolator NAV_FADE_OUT_INTERPOLATOR;
    public static final TransitionAnimator.Timings TIMINGS;
    public Callback callback;
    public final TransitionAnimator dialogToAppAnimator;
    public final boolean disableWmTimeout;
    public final ActivityTransitionAnimator$lifecycleListener$1 lifecycleListener;
    public final LinkedHashSet listeners;
    public final Executor mainExecutor;
    public final TransitionAnimator transitionAnimator;

    public final class AnimationDelegate {
        public TransitionAnimator$startAnimation$1 animation;
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
        public boolean timedOut;
        public final Handler timeoutHandler;
        public final SyncRtSurfaceTransactionApplier transactionApplier;
        public final View transactionApplierView;
        public final TransitionAnimator transitionAnimator;
        public final ViewGroup transitionContainer;
        public final Rect windowCrop;
        public final RectF windowCropF;

        public AnimationDelegate(Executor executor, Controller controller, Callback callback) {
            this(executor, controller, callback, null, null, false, 56, null);
        }

        public AnimationDelegate(Executor executor, Controller controller, Callback callback, Listener listener) {
            this(executor, controller, callback, listener, null, false, 48, null);
        }

        public AnimationDelegate(Executor executor, Controller controller, Callback callback, Listener listener, TransitionAnimator transitionAnimator) {
            this(executor, controller, callback, listener, transitionAnimator, false, 32, null);
        }

        /* JADX WARN: Type inference failed for: r1v11, types: [com.android.systemui.animation.ActivityTransitionAnimator$AnimationDelegate$onTimeout$1] */
        public AnimationDelegate(Executor executor, Controller controller, Callback callback, Listener listener, TransitionAnimator transitionAnimator, boolean z) {
            this.mainExecutor = executor;
            this.controller = controller;
            this.callback = callback;
            this.listener = listener;
            this.transitionAnimator = transitionAnimator;
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
                    ActivityTransitionAnimator.AnimationDelegate animationDelegate = ActivityTransitionAnimator.AnimationDelegate.this;
                    if (animationDelegate.cancelled) {
                        return;
                    }
                    Log.w("ActivityTransitionAnimator", "Remote animation timed out");
                    animationDelegate.timedOut = true;
                    if (ActivityTransitionAnimator.DEBUG_TRANSITION_ANIMATION) {
                        Log.d("ActivityTransitionAnimator", "Calling controller.onTransitionAnimationCancelled() [animation timed out]");
                    }
                    ActivityTransitionAnimator.Controller.Companion companion = ActivityTransitionAnimator.Controller.Companion;
                    animationDelegate.controller.onTransitionAnimationCancelled(null);
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
            Flags.returnAnimationFrameworkLibrary();
            throw new IllegalStateException("isLaunching cannot be false when the returnAnimationFrameworkLibrary flag is disabled".toString());
        }

        public /* synthetic */ AnimationDelegate(Executor executor, Controller controller, Callback callback, Listener listener, TransitionAnimator transitionAnimator, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(executor, controller, callback, (i & 8) != 0 ? null : listener, (i & 16) != 0 ? Companion.access$defaultTransitionAnimator(ActivityTransitionAnimator.Companion, executor) : transitionAnimator, (i & 32) != 0 ? false : z);
        }
    }

    public interface Callback {
    }

    public final class Companion {
        private Companion() {
        }

        public static final TransitionAnimator access$defaultTransitionAnimator(Companion companion, Executor executor) {
            companion.getClass();
            return new TransitionAnimator(executor, ActivityTransitionAnimator.TIMINGS, ActivityTransitionAnimator.INTERPOLATORS);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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

    public final class TransitionRegister {
        public static final Companion Companion = new Companion(null);
        public final IShellTransitions iShellTransitions;
        public final ShellTransitions shellTransitions;

        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        private TransitionRegister(ShellTransitions shellTransitions, IShellTransitions iShellTransitions) {
        }

        public /* synthetic */ TransitionRegister(ShellTransitions shellTransitions, IShellTransitions iShellTransitions, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : shellTransitions, (i & 2) != 0 ? null : iShellTransitions);
        }
    }

    static {
        TransitionAnimator.Timings timings = new TransitionAnimator.Timings(500L, 0L, 150L, 150L, 183L);
        TIMINGS = timings;
        DIALOG_TIMINGS = new TransitionAnimator.Timings(timings.totalDuration, timings.contentBeforeFadeOutDelay, 200L, 200L, timings.contentAfterFadeInDuration);
        INTERPOLATORS = new TransitionAnimator.Interpolators(Interpolators.EMPHASIZED, Interpolators.EMPHASIZED_COMPLEMENT, Interpolators.LINEAR_OUT_SLOW_IN, new PathInterpolator(0.0f, 0.0f, 0.6f, 1.0f));
        DEBUG_TRANSITION_ANIMATION = Build.IS_DEBUGGABLE;
        ANIMATION_DELAY_NAV_FADE_IN = timings.totalDuration - 266;
        NAV_FADE_IN_INTERPOLATOR = Interpolators.STANDARD_DECELERATE;
        NAV_FADE_OUT_INTERPOLATOR = new PathInterpolator(0.2f, 0.0f, 1.0f, 1.0f);
    }

    public ActivityTransitionAnimator(Executor executor) {
        this(executor, (TransitionRegister) null, (TransitionAnimator) null, (TransitionAnimator) null, false, 30, (DefaultConstructorMarker) null);
    }

    public final void callOnIntentStartedOnMainThread(final Controller controller, final boolean z) {
        if (!Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.animation.ActivityTransitionAnimator$callOnIntentStartedOnMainThread$1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityTransitionAnimator activityTransitionAnimator = ActivityTransitionAnimator.this;
                    ActivityTransitionAnimator.Controller controller2 = controller;
                    boolean z2 = z;
                    ActivityTransitionAnimator.Companion companion = ActivityTransitionAnimator.Companion;
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

    public final Runner createRunner(Controller controller) {
        TransitionAnimator transitionAnimator = controller.isDialogLaunch() ? this.dialogToAppAnimator : this.transitionAnimator;
        Callback callback = this.callback;
        Intrinsics.checkNotNull(callback);
        return new Runner(controller, callback, transitionAnimator, this.lifecycleListener);
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0080, code lost:
    
        if (r3 != false) goto L37;
     */
    /* JADX WARN: Removed duplicated region for block: B:33:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00bd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startIntentWithAnimation(com.android.systemui.animation.ActivityTransitionAnimator.Controller r22, boolean r23, java.lang.String r24, boolean r25, kotlin.jvm.functions.Function1 r26) {
        /*
            Method dump skipped, instructions count: 225
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.ActivityTransitionAnimator.startIntentWithAnimation(com.android.systemui.animation.ActivityTransitionAnimator$Controller, boolean, java.lang.String, boolean, kotlin.jvm.functions.Function1):void");
    }

    public final void startPendingIntentWithAnimation(Controller controller, boolean z, String str, boolean z2, final PendingIntentStarter pendingIntentStarter) {
        startIntentWithAnimation(controller, z, str, z2, new Function1() { // from class: com.android.systemui.animation.ActivityTransitionAnimator$startPendingIntentWithAnimation$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Integer.valueOf(ActivityTransitionAnimator.PendingIntentStarter.this.startPendingIntent((RemoteAnimationAdapter) obj));
            }
        });
    }

    public ActivityTransitionAnimator(Executor executor, TransitionRegister transitionRegister) {
        this(executor, transitionRegister, (TransitionAnimator) null, (TransitionAnimator) null, false, 28, (DefaultConstructorMarker) null);
    }

    public ActivityTransitionAnimator(Executor executor, TransitionRegister transitionRegister, TransitionAnimator transitionAnimator) {
        this(executor, transitionRegister, transitionAnimator, (TransitionAnimator) null, false, 24, (DefaultConstructorMarker) null);
    }

    public ActivityTransitionAnimator(Executor executor, TransitionRegister transitionRegister, TransitionAnimator transitionAnimator, TransitionAnimator transitionAnimator2) {
        this(executor, transitionRegister, transitionAnimator, transitionAnimator2, false, 16, (DefaultConstructorMarker) null);
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

    public final class Runner extends IRemoteAnimationRunner.Stub {
        public AnimationDelegate delegate;

        /* renamed from: com.android.systemui.animation.ActivityTransitionAnimator$Runner$1, reason: invalid class name */
        final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function0 {
            public AnonymousClass1(Object obj) {
                super(0, obj, Runner.class, "dispose", "dispose()V", 0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Runner runner = (Runner) this.receiver;
                ActivityTransitionAnimator.this.mainExecutor.execute(new ActivityTransitionAnimator$Runner$dispose$1(runner));
                return Unit.INSTANCE;
            }
        }

        public Runner(Controller controller, Callback callback, TransitionAnimator transitionAnimator, Listener listener) {
            this.delegate = new AnimationDelegate(ActivityTransitionAnimator.this.mainExecutor, controller, callback, new DelegatingAnimationCompletionListener(ActivityTransitionAnimator.this, listener, new AnonymousClass1(this)), transitionAnimator, ActivityTransitionAnimator.this.disableWmTimeout);
        }

        public final void onAnimationCancelled() {
            final AnimationDelegate animationDelegate = this.delegate;
            ActivityTransitionAnimator.this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.animation.ActivityTransitionAnimator$Runner$onAnimationCancelled$1
                @Override // java.lang.Runnable
                public final void run() {
                    if (ActivityTransitionAnimator.AnimationDelegate.this == null) {
                        Log.wtf("ActivityTransitionAnimator", "onAnimationCancelled called after completion");
                    }
                    ActivityTransitionAnimator.AnimationDelegate animationDelegate2 = ActivityTransitionAnimator.AnimationDelegate.this;
                    if (animationDelegate2 != null) {
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
                        TransitionAnimator$startAnimation$1 transitionAnimator$startAnimation$1 = animationDelegate2.animation;
                        if (transitionAnimator$startAnimation$1 != null) {
                            transitionAnimator$startAnimation$1.$animator.cancel();
                        }
                        if (ActivityTransitionAnimator.DEBUG_TRANSITION_ANIMATION) {
                            Log.d("ActivityTransitionAnimator", "Calling controller.onTransitionAnimationCancelled() [remote animation cancelled]");
                        }
                        ActivityTransitionAnimator.Controller.Companion companion = ActivityTransitionAnimator.Controller.Companion;
                        animationDelegate2.controller.onTransitionAnimationCancelled(null);
                        ActivityTransitionAnimator.Listener listener = animationDelegate2.listener;
                        if (listener != null) {
                            listener.onTransitionAnimationCancelled();
                        }
                    }
                }
            });
        }

        public final void onAnimationStart(final int i, final RemoteAnimationTarget[] remoteAnimationTargetArr, final RemoteAnimationTarget[] remoteAnimationTargetArr2, final RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            final AnimationDelegate animationDelegate = this.delegate;
            ActivityTransitionAnimator.this.mainExecutor.execute(new Runnable(iRemoteAnimationFinishedCallback, i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3) { // from class: com.android.systemui.animation.ActivityTransitionAnimator$Runner$onAnimationStart$1
                public final /* synthetic */ RemoteAnimationTarget[] $apps;
                public final /* synthetic */ IRemoteAnimationFinishedCallback $finishedCallback;
                public final /* synthetic */ RemoteAnimationTarget[] $nonApps;

                {
                    this.$apps = remoteAnimationTargetArr;
                    this.$nonApps = remoteAnimationTargetArr3;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    RemoteAnimationTarget remoteAnimationTarget;
                    boolean z;
                    RemoteAnimationTarget remoteAnimationTarget2;
                    ActivityTransitionAnimator.AnimationDelegate animationDelegate2 = ActivityTransitionAnimator.AnimationDelegate.this;
                    if (animationDelegate2 == null) {
                        Log.i("ActivityTransitionAnimator", "onAnimationStart called after completion");
                        IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = this.$finishedCallback;
                        if (iRemoteAnimationFinishedCallback2 != null) {
                            iRemoteAnimationFinishedCallback2.onAnimationFinished();
                            return;
                        }
                        return;
                    }
                    RemoteAnimationTarget[] remoteAnimationTargetArr4 = this.$apps;
                    RemoteAnimationTarget[] remoteAnimationTargetArr5 = this.$nonApps;
                    IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback3 = this.$finishedCallback;
                    Handler handler = animationDelegate2.timeoutHandler;
                    if (handler != null) {
                        handler.removeCallbacks(animationDelegate2.onTimeout);
                        handler.removeCallbacks(animationDelegate2.onLongTimeout);
                    }
                    if (animationDelegate2.timedOut) {
                        if (iRemoteAnimationFinishedCallback3 != null) {
                            try {
                                iRemoteAnimationFinishedCallback3.onAnimationFinished();
                                return;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        return;
                    }
                    if (animationDelegate2.cancelled) {
                        return;
                    }
                    ActivityTransitionAnimator.Controller controller = animationDelegate2.controller;
                    if (remoteAnimationTargetArr4 == null) {
                        remoteAnimationTarget = null;
                    } else {
                        boolean z2 = !controller.isLaunching();
                        ArrayIterator arrayIterator = new ArrayIterator(remoteAnimationTargetArr4);
                        RemoteAnimationTarget remoteAnimationTarget3 = null;
                        while (arrayIterator.hasNext()) {
                            RemoteAnimationTarget remoteAnimationTarget4 = (RemoteAnimationTarget) arrayIterator.next();
                            if (remoteAnimationTarget4.mode == z2) {
                                com.android.systemui.Flags.FEATURE_FLAGS.getClass();
                                Flags.returnAnimationFrameworkLibrary();
                                if (remoteAnimationTarget3 != null && ((z = remoteAnimationTarget4.hasAnimatingParent) || !remoteAnimationTarget3.hasAnimatingParent)) {
                                    if (!z) {
                                        Rect rect = remoteAnimationTarget4.screenSpaceBounds;
                                        Rect rect2 = remoteAnimationTarget3.screenSpaceBounds;
                                        if (rect.height() * rect.width() > rect2.height() * rect2.width()) {
                                        }
                                    }
                                }
                                remoteAnimationTarget3 = remoteAnimationTarget4;
                            }
                        }
                        remoteAnimationTarget = remoteAnimationTarget3;
                    }
                    if (remoteAnimationTarget == null) {
                        Log.i("ActivityTransitionAnimator", "Aborting the animation as no window is opening");
                        if (iRemoteAnimationFinishedCallback3 != null) {
                            try {
                                iRemoteAnimationFinishedCallback3.onAnimationFinished();
                            } catch (RemoteException e2) {
                                e2.printStackTrace();
                            }
                        }
                        if (ActivityTransitionAnimator.DEBUG_TRANSITION_ANIMATION) {
                            Log.d("ActivityTransitionAnimator", "Calling controller.onTransitionAnimationCancelled() [no window opening]");
                        }
                        ActivityTransitionAnimator.Controller.Companion companion = ActivityTransitionAnimator.Controller.Companion;
                        controller.onTransitionAnimationCancelled(null);
                        ActivityTransitionAnimator.Listener listener = animationDelegate2.listener;
                        if (listener != null) {
                            listener.onTransitionAnimationCancelled();
                            return;
                        }
                        return;
                    }
                    int i2 = 0;
                    if (remoteAnimationTargetArr5 != null) {
                        int length = remoteAnimationTargetArr5.length;
                        for (int i3 = 0; i3 < length; i3++) {
                            remoteAnimationTarget2 = remoteAnimationTargetArr5[i3];
                            if (remoteAnimationTarget2.windowType == 2019) {
                                break;
                            }
                        }
                    }
                    remoteAnimationTarget2 = null;
                    Rect rect3 = remoteAnimationTarget.screenSpaceBounds;
                    TransitionAnimator.State state = controller.isLaunching() ? new TransitionAnimator.State(rect3.top, rect3.bottom, rect3.left, rect3.right, 0.0f, 0.0f, 48, null) : controller.createAnimatorState();
                    com.android.systemui.Flags.FEATURE_FLAGS.getClass();
                    if (!remoteAnimationTarget.isTranslucent) {
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
                    }
                    int i4 = i2;
                    boolean isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib = animationDelegate2.transitionAnimator.isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib(controller.getTransitionContainer(), state);
                    if (controller.isLaunching()) {
                        float windowCornerRadius = isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib ? ScreenDecorationsUtils.getWindowCornerRadius(animationDelegate2.context) : 0.0f;
                        state.topCornerRadius = windowCornerRadius;
                        state.bottomCornerRadius = windowCornerRadius;
                    }
                    ActivityTransitionAnimator$AnimationDelegate$startAnimation$controller$1 activityTransitionAnimator$AnimationDelegate$startAnimation$controller$1 = new ActivityTransitionAnimator$AnimationDelegate$startAnimation$controller$1(animationDelegate2.controller, animationDelegate2, isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib, rect3, iRemoteAnimationFinishedCallback3, remoteAnimationTarget, remoteAnimationTarget2);
                    ActivityTransitionAnimator.Controller controller2 = activityTransitionAnimator$AnimationDelegate$startAnimation$controller$1.$$delegate_0;
                    animationDelegate2.animation = animationDelegate2.transitionAnimator.startAnimation(activityTransitionAnimator$AnimationDelegate$startAnimation$controller$1, state, i4, !controller2.isBelowAnimatingWindow(), !controller2.isBelowAnimatingWindow());
                }
            });
        }

        public /* synthetic */ Runner(ActivityTransitionAnimator activityTransitionAnimator, Controller controller, Callback callback, TransitionAnimator transitionAnimator, Listener listener, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(controller, callback, transitionAnimator, (i & 8) != 0 ? null : listener);
        }

        public static /* synthetic */ void getDelegate$annotations() {
        }
    }

    public ActivityTransitionAnimator(Executor executor, ShellTransitions shellTransitions) {
        this(executor, shellTransitions, (TransitionAnimator) null, (TransitionAnimator) null, false, 28, (DefaultConstructorMarker) null);
    }

    public ActivityTransitionAnimator(Executor executor, ShellTransitions shellTransitions, TransitionAnimator transitionAnimator) {
        this(executor, shellTransitions, transitionAnimator, (TransitionAnimator) null, false, 24, (DefaultConstructorMarker) null);
    }

    public ActivityTransitionAnimator(Executor executor, ShellTransitions shellTransitions, TransitionAnimator transitionAnimator, TransitionAnimator transitionAnimator2) {
        this(executor, shellTransitions, transitionAnimator, transitionAnimator2, false, 16, (DefaultConstructorMarker) null);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.animation.ActivityTransitionAnimator$lifecycleListener$1] */
    public ActivityTransitionAnimator(Executor executor, TransitionRegister transitionRegister, TransitionAnimator transitionAnimator, TransitionAnimator transitionAnimator2, boolean z) {
        this.mainExecutor = executor;
        this.transitionAnimator = transitionAnimator;
        this.dialogToAppAnimator = transitionAnimator2;
        this.disableWmTimeout = z;
        this.listeners = new LinkedHashSet();
        this.lifecycleListener = new Listener() { // from class: com.android.systemui.animation.ActivityTransitionAnimator$lifecycleListener$1
            @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
            public final void onTransitionAnimationCancelled() {
                Iterator it = ActivityTransitionAnimator.this.listeners.iterator();
                while (it.hasNext()) {
                    ((ActivityTransitionAnimator.Listener) it.next()).onTransitionAnimationCancelled();
                }
            }

            @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
            public final void onTransitionAnimationEnd() {
                Iterator it = ActivityTransitionAnimator.this.listeners.iterator();
                while (it.hasNext()) {
                    ((ActivityTransitionAnimator.Listener) it.next()).onTransitionAnimationEnd();
                }
            }

            @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
            public final void onTransitionAnimationProgress(float f) {
                Iterator it = ActivityTransitionAnimator.this.listeners.iterator();
                while (it.hasNext()) {
                    ((ActivityTransitionAnimator.Listener) it.next()).onTransitionAnimationProgress(f);
                }
            }

            @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
            public final void onTransitionAnimationStart() {
                Iterator it = ActivityTransitionAnimator.this.listeners.iterator();
                while (it.hasNext()) {
                    ((ActivityTransitionAnimator.Listener) it.next()).onTransitionAnimationStart();
                }
            }
        };
        new HashMap();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ActivityTransitionAnimator(java.util.concurrent.Executor r7, com.android.systemui.animation.ActivityTransitionAnimator.TransitionRegister r8, com.android.systemui.animation.TransitionAnimator r9, com.android.systemui.animation.TransitionAnimator r10, boolean r11, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
        /*
            r6 = this;
            r13 = r12 & 2
            if (r13 == 0) goto L5
            r8 = 0
        L5:
            r2 = r8
            r8 = r12 & 4
            com.android.systemui.animation.ActivityTransitionAnimator$Companion r13 = com.android.systemui.animation.ActivityTransitionAnimator.Companion
            if (r8 == 0) goto L10
            com.android.systemui.animation.TransitionAnimator r9 = com.android.systemui.animation.ActivityTransitionAnimator.Companion.access$defaultTransitionAnimator(r13, r7)
        L10:
            r3 = r9
            r8 = r12 & 8
            if (r8 == 0) goto L21
            r13.getClass()
            com.android.systemui.animation.TransitionAnimator r10 = new com.android.systemui.animation.TransitionAnimator
            com.android.systemui.animation.TransitionAnimator$Timings r8 = com.android.systemui.animation.ActivityTransitionAnimator.DIALOG_TIMINGS
            com.android.systemui.animation.TransitionAnimator$Interpolators r9 = com.android.systemui.animation.ActivityTransitionAnimator.INTERPOLATORS
            r10.<init>(r7, r8, r9)
        L21:
            r4 = r10
            r8 = r12 & 16
            if (r8 == 0) goto L27
            r11 = 0
        L27:
            r5 = r11
            r0 = r6
            r1 = r7
            r0.<init>(r1, r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.ActivityTransitionAnimator.<init>(java.util.concurrent.Executor, com.android.systemui.animation.ActivityTransitionAnimator$TransitionRegister, com.android.systemui.animation.TransitionAnimator, com.android.systemui.animation.TransitionAnimator, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public interface Listener {
        void onTransitionAnimationEnd();

        void onTransitionAnimationStart();

        default void onTransitionAnimationCancelled() {
        }

        default void onTransitionAnimationProgress(float f) {
        }
    }

    public interface Controller extends TransitionAnimator.Controller {
        public static final Companion Companion = Companion.$$INSTANCE;

        public final class Companion {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }

            public static GhostedViewTransitionAnimatorController fromView(View view, Integer num, TransitionCookie transitionCookie, ComponentName componentName, Integer num2) {
                if (!(view instanceof LaunchableView)) {
                    throw new IllegalArgumentException("An ActivityTransitionAnimator.Controller was created from a View that does not implement LaunchableView. This can lead to subtle bugs where the visibility of the View we are launching from is not what we expected.");
                }
                if (view.getParent() instanceof ViewGroup) {
                    return new GhostedViewTransitionAnimatorController(view, num, transitionCookie, componentName, num2, null, 32, null);
                }
                Log.e("ActivityTransitionAnimator", "Skipping animation as view " + view + " is not attached to a ViewGroup", new Exception());
                return null;
            }

            public static /* synthetic */ GhostedViewTransitionAnimatorController fromView$default(Companion companion, View view, Integer num, int i) {
                if ((i & 2) != 0) {
                    num = null;
                }
                companion.getClass();
                return fromView(view, num, null, null, null);
            }
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

        default void onIntentStarted(boolean z) {
        }

        default void onTransitionAnimationCancelled(Boolean bool) {
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ActivityTransitionAnimator(java.util.concurrent.Executor r8, com.android.wm.shell.shared.ShellTransitions r9, com.android.systemui.animation.TransitionAnimator r10, com.android.systemui.animation.TransitionAnimator r11, boolean r12, int r13, kotlin.jvm.internal.DefaultConstructorMarker r14) {
        /*
            r7 = this;
            r14 = r13 & 4
            com.android.systemui.animation.ActivityTransitionAnimator$Companion r0 = com.android.systemui.animation.ActivityTransitionAnimator.Companion
            if (r14 == 0) goto La
            com.android.systemui.animation.TransitionAnimator r10 = com.android.systemui.animation.ActivityTransitionAnimator.Companion.access$defaultTransitionAnimator(r0, r8)
        La:
            r4 = r10
            r10 = r13 & 8
            if (r10 == 0) goto L1b
            r0.getClass()
            com.android.systemui.animation.TransitionAnimator r11 = new com.android.systemui.animation.TransitionAnimator
            com.android.systemui.animation.TransitionAnimator$Timings r10 = com.android.systemui.animation.ActivityTransitionAnimator.DIALOG_TIMINGS
            com.android.systemui.animation.TransitionAnimator$Interpolators r14 = com.android.systemui.animation.ActivityTransitionAnimator.INTERPOLATORS
            r11.<init>(r8, r10, r14)
        L1b:
            r5 = r11
            r10 = r13 & 16
            if (r10 == 0) goto L21
            r12 = 0
        L21:
            r6 = r12
            r1 = r7
            r2 = r8
            r3 = r9
            r1.<init>(r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.ActivityTransitionAnimator.<init>(java.util.concurrent.Executor, com.android.wm.shell.shared.ShellTransitions, com.android.systemui.animation.TransitionAnimator, com.android.systemui.animation.TransitionAnimator, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ActivityTransitionAnimator(Executor executor, ShellTransitions shellTransitions, TransitionAnimator transitionAnimator, TransitionAnimator transitionAnimator2, boolean z) {
        this(executor, new TransitionRegister(shellTransitions, null, 2, 0 == true ? 1 : 0), transitionAnimator, transitionAnimator2, z);
        TransitionRegister.Companion.getClass();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ActivityTransitionAnimator(java.util.concurrent.Executor r8, com.android.wm.shell.shared.IShellTransitions r9, com.android.systemui.animation.TransitionAnimator r10, com.android.systemui.animation.TransitionAnimator r11, boolean r12, int r13, kotlin.jvm.internal.DefaultConstructorMarker r14) {
        /*
            r7 = this;
            r14 = r13 & 4
            com.android.systemui.animation.ActivityTransitionAnimator$Companion r0 = com.android.systemui.animation.ActivityTransitionAnimator.Companion
            if (r14 == 0) goto La
            com.android.systemui.animation.TransitionAnimator r10 = com.android.systemui.animation.ActivityTransitionAnimator.Companion.access$defaultTransitionAnimator(r0, r8)
        La:
            r4 = r10
            r10 = r13 & 8
            if (r10 == 0) goto L1b
            r0.getClass()
            com.android.systemui.animation.TransitionAnimator r11 = new com.android.systemui.animation.TransitionAnimator
            com.android.systemui.animation.TransitionAnimator$Timings r10 = com.android.systemui.animation.ActivityTransitionAnimator.DIALOG_TIMINGS
            com.android.systemui.animation.TransitionAnimator$Interpolators r14 = com.android.systemui.animation.ActivityTransitionAnimator.INTERPOLATORS
            r11.<init>(r8, r10, r14)
        L1b:
            r5 = r11
            r10 = r13 & 16
            if (r10 == 0) goto L21
            r12 = 0
        L21:
            r6 = r12
            r1 = r7
            r2 = r8
            r3 = r9
            r1.<init>(r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.ActivityTransitionAnimator.<init>(java.util.concurrent.Executor, com.android.wm.shell.shared.IShellTransitions, com.android.systemui.animation.TransitionAnimator, com.android.systemui.animation.TransitionAnimator, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ActivityTransitionAnimator(Executor executor, IShellTransitions iShellTransitions, TransitionAnimator transitionAnimator, TransitionAnimator transitionAnimator2, boolean z) {
        this(executor, new TransitionRegister(null, iShellTransitions, 1, 0 == true ? 1 : 0), transitionAnimator, transitionAnimator2, z);
        TransitionRegister.Companion.getClass();
    }
}
