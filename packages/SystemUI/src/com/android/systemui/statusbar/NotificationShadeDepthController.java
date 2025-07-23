package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.SystemClock;
import android.os.Trace;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.MathUtils;
import android.view.Choreographer;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.ViewRootImpl;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.app.animation.Interpolators;
import com.android.app.tracing.coroutines.TrackTracer;
import com.android.systemui.CoverScreenDecorHwcLayer$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.navigationbar.views.NavigationBar;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.WallpaperController;
import com.android.systemui.wallpapers.domain.interactor.WallpaperInteractor;
import com.android.systemui.window.domain.interactor.WindowRootViewBlurInteractor;
import com.android.wm.shell.appzoomout.AppZoomOutController;
import com.android.wm.shell.shared.handles.RegionSamplingHelper;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationShadeDepthController implements ShadeExpansionListener, Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean appLaunchTransitionIsInProgress;
    public final NotificationShadeDepthController$applyZoomOutForFrame$1 applyZoomOutForFrame;
    public final BiometricUnlockController biometricUnlockController;
    public final BlurUtils blurUtils;
    public boolean blursDisabledForAppLaunch;
    public boolean blursDisabledForUnlock;
    public final DepthAnimation brightnessMirrorSpring;
    public final Choreographer choreographer;
    public final DozeParameters dozeParameters;
    public boolean isBlurred;
    public boolean isOpen;
    public Animator keyguardAnimator;
    public final KeyguardFastBioUnlockController keyguardFastBioUnlockController;
    public final KeyguardStateController keyguardStateController;
    public int lastAppliedBlur;
    public final NotificationShadeWindowController notificationShadeWindowController;
    public float panelPullDownMinFraction;
    public float prevDozeAmount;
    public int prevShadeDirection;
    public float prevShadeVelocity;
    public boolean prevTracking;
    public float qsPanelExpansion;
    public NotificationShadeWindowView root;
    public boolean scrimsVisible;
    public final DepthAnimation shadeAnimation;
    public final Lazy shadeDisplaysRepository;
    public float shadeExpansion;
    public final ShadeModeInteractor shadeModeInteractor;
    public final StatusBarStateController statusBarStateController;
    public float transitionToFullShadeProgress;
    public final NotificationShadeDepthController$updateBlurCallback$1 updateBlurCallback;
    public boolean updateScheduled;
    public final WakeAndUnlockBlurData wakeAndUnlockBlurData;
    public final WallpaperController wallpaperController;
    public final WallpaperInteractor wallpaperInteractor;
    public boolean wallpaperSupportsAmbientMode;
    public final WindowRootViewBlurInteractor windowRootViewBlurInteractor;
    public boolean isClosed = true;
    public final List listeners = new ArrayList();
    public long prevTimestamp = -1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.NotificationShadeDepthController$1, reason: invalid class name */
    public final class AnonymousClass1 implements Consumer {
        public AnonymousClass1() {
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            Integer num = (Integer) obj;
            NotificationShadeDepthController notificationShadeDepthController = NotificationShadeDepthController.this;
            boolean z = num != null && num.intValue() == 2;
            if (notificationShadeDepthController.scrimsVisible == z) {
                return;
            }
            notificationShadeDepthController.scrimsVisible = z;
            notificationShadeDepthController.scheduleUpdate();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.NotificationShadeDepthController$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return NotificationShadeDepthController.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final NotificationShadeDepthController notificationShadeDepthController = NotificationShadeDepthController.this;
                Flow flow = notificationShadeDepthController.wallpaperInteractor.wallpaperSupportsAmbientMode;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.NotificationShadeDepthController.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        NotificationShadeDepthController notificationShadeDepthController2 = NotificationShadeDepthController.this;
                        notificationShadeDepthController2.wallpaperSupportsAmbientMode = booleanValue;
                        float f = notificationShadeDepthController2.prevDozeAmount;
                        BlurUtils blurUtils = notificationShadeDepthController2.blurUtils;
                        float blurRadiusOfRatio = !booleanValue ? 0.0f : blurUtils.blurRadiusOfRatio(f);
                        WakeAndUnlockBlurData wakeAndUnlockBlurData = notificationShadeDepthController2.wakeAndUnlockBlurData;
                        if (blurRadiusOfRatio == wakeAndUnlockBlurData.radius && !wakeAndUnlockBlurData.useZoom) {
                            new WakeAndUnlockBlurData(notificationShadeDepthController2.wallpaperSupportsAmbientMode ? blurUtils.blurRadiusOfRatio(notificationShadeDepthController2.prevDozeAmount) : 0.0f, false);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DepthAnimation {
        public int pendingRadius = -1;
        public float radius;
        public final SpringAnimation springAnimation;

        public DepthAnimation() {
            SpringAnimation springAnimation = new SpringAnimation(this, new FloatPropertyCompat() { // from class: com.android.systemui.statusbar.NotificationShadeDepthController$DepthAnimation$springAnimation$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super("blurRadius");
                }

                @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
                public final float getValue(Object obj) {
                    return NotificationShadeDepthController.DepthAnimation.this.radius;
                }

                @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
                public final void setValue(Object obj, float f) {
                    NotificationShadeDepthController.DepthAnimation.this.radius = f;
                    int i = NotificationShadeDepthController.$r8$clinit;
                    r2.scheduleUpdate();
                }
            });
            this.springAnimation = springAnimation;
            SpringForce springForce = new SpringForce(0.0f);
            springAnimation.mSpring = springForce;
            springForce.setDampingRatio(1.0f);
            springAnimation.mSpring.setStiffness(10000.0f);
            springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.statusbar.NotificationShadeDepthController.DepthAnimation.1
                @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
                public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
                    DepthAnimation.this.pendingRadius = -1;
                }
            });
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class WakeAndUnlockBlurData {
        public final float radius;
        public final boolean useZoom;

        public WakeAndUnlockBlurData(float f, boolean z) {
            this.radius = f;
            this.useZoom = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof WakeAndUnlockBlurData)) {
                return false;
            }
            WakeAndUnlockBlurData wakeAndUnlockBlurData = (WakeAndUnlockBlurData) obj;
            return Float.compare(this.radius, wakeAndUnlockBlurData.radius) == 0 && this.useZoom == wakeAndUnlockBlurData.useZoom;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.useZoom) + (Float.hashCode(this.radius) * 31);
        }

        public final String toString() {
            return "WakeAndUnlockBlurData(radius=" + this.radius + ", useZoom=" + this.useZoom + ")";
        }

        public /* synthetic */ WakeAndUnlockBlurData(float f, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(f, (i & 2) != 0 ? true : z);
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r6v3, types: [com.android.systemui.statusbar.NotificationShadeDepthController$updateBlurCallback$1] */
    /* JADX WARN: Type inference failed for: r6v4, types: [com.android.systemui.statusbar.NotificationShadeDepthController$applyZoomOutForFrame$1] */
    public NotificationShadeDepthController(KeyguardFastBioUnlockController keyguardFastBioUnlockController, StatusBarStateController statusBarStateController, BlurUtils blurUtils, BiometricUnlockController biometricUnlockController, KeyguardStateController keyguardStateController, KeyguardInteractor keyguardInteractor, Choreographer choreographer, WallpaperController wallpaperController, WallpaperInteractor wallpaperInteractor, NotificationShadeWindowController notificationShadeWindowController, DozeParameters dozeParameters, ShadeModeInteractor shadeModeInteractor, WindowRootViewBlurInteractor windowRootViewBlurInteractor, Optional<AppZoomOutController.AppZoomOutImpl> optional, CoroutineScope coroutineScope, DumpManager dumpManager, Lazy lazy) {
        this.keyguardFastBioUnlockController = keyguardFastBioUnlockController;
        this.statusBarStateController = statusBarStateController;
        this.blurUtils = blurUtils;
        this.biometricUnlockController = biometricUnlockController;
        this.keyguardStateController = keyguardStateController;
        this.choreographer = choreographer;
        this.wallpaperController = wallpaperController;
        this.wallpaperInteractor = wallpaperInteractor;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.dozeParameters = dozeParameters;
        this.shadeModeInteractor = shadeModeInteractor;
        this.windowRootViewBlurInteractor = windowRootViewBlurInteractor;
        this.shadeDisplaysRepository = lazy;
        blurUtils.getClass();
        DepthAnimation depthAnimation = new DepthAnimation();
        this.shadeAnimation = depthAnimation;
        this.brightnessMirrorSpring = new DepthAnimation();
        this.wakeAndUnlockBlurData = new WakeAndUnlockBlurData(0.0f, false, 2, null);
        this.updateBlurCallback = new Choreographer.FrameCallback() { // from class: com.android.systemui.statusbar.NotificationShadeDepthController$updateBlurCallback$1
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                NotificationShadeDepthController notificationShadeDepthController = NotificationShadeDepthController.this;
                notificationShadeDepthController.updateScheduled = false;
                Pair computeBlurAndZoomOut = notificationShadeDepthController.computeBlurAndZoomOut();
                int intValue = ((Number) computeBlurAndZoomOut.component1()).intValue();
                float floatValue = ((Number) computeBlurAndZoomOut.component2()).floatValue();
                NotificationShadeDepthController.this.getClass();
                TrackTracer.Companion.getClass();
                TrackTracer.Companion.instantForGroup(intValue, "shade", "shade_blur_radius");
                NotificationShadeDepthController notificationShadeDepthController2 = NotificationShadeDepthController.this;
                BlurUtils blurUtils2 = notificationShadeDepthController2.blurUtils;
                NotificationShadeWindowView notificationShadeWindowView = notificationShadeDepthController2.root;
                if (notificationShadeWindowView == null) {
                    notificationShadeWindowView = null;
                }
                ViewRootImpl viewRootImpl = notificationShadeWindowView.getViewRootImpl();
                blurUtils2.getClass();
                if (viewRootImpl != null) {
                    viewRootImpl.getSurfaceControl().isValid();
                }
                NotificationShadeDepthController notificationShadeDepthController3 = NotificationShadeDepthController.this;
                notificationShadeDepthController3.lastAppliedBlur = intValue;
                TrackTracer.Companion.getClass();
                TrackTracer.Companion.instantForGroup((int) (100 * floatValue), "shade", "zoom_out");
                notificationShadeDepthController3.wallpaperController.setNotificationShadeZoom(floatValue);
                ArrayList arrayList = (ArrayList) notificationShadeDepthController3.listeners;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    NavigationBar.AnonymousClass6 anonymousClass6 = (NavigationBar.AnonymousClass6) obj;
                    anonymousClass6.getClass();
                    boolean z = intValue != 0;
                    if (z != anonymousClass6.mHasBlurs) {
                        anonymousClass6.mHasBlurs = z;
                        RegionSamplingHelper regionSamplingHelper = NavigationBar.this.mRegionSamplingHelper;
                        regionSamplingHelper.mWindowHasBlurs = z;
                        regionSamplingHelper.updateSamplingListener();
                    }
                }
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) notificationShadeDepthController3.notificationShadeWindowController;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                if (notificationShadeWindowState.backgroundBlurRadius == intValue) {
                    return;
                }
                notificationShadeWindowState.backgroundBlurRadius = intValue;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
            }
        };
        this.applyZoomOutForFrame = new Choreographer.FrameCallback() { // from class: com.android.systemui.statusbar.NotificationShadeDepthController$applyZoomOutForFrame$1
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                NotificationShadeDepthController notificationShadeDepthController = NotificationShadeDepthController.this;
                notificationShadeDepthController.updateScheduled = false;
                float floatValue = ((Number) notificationShadeDepthController.computeBlurAndZoomOut().component2()).floatValue();
                NotificationShadeDepthController notificationShadeDepthController2 = NotificationShadeDepthController.this;
                notificationShadeDepthController2.getClass();
                TrackTracer.Companion.getClass();
                TrackTracer.Companion.instantForGroup((int) (100 * floatValue), "shade", "zoom_out");
                notificationShadeDepthController2.wallpaperController.setNotificationShadeZoom(floatValue);
            }
        };
        KeyguardStateController.Callback callback = new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.NotificationShadeDepthController$keyguardStateCallback$1
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardFadingAwayChanged() {
                final NotificationShadeDepthController notificationShadeDepthController = NotificationShadeDepthController.this;
                if (((KeyguardStateControllerImpl) notificationShadeDepthController.keyguardStateController).mKeyguardFadingAway && notificationShadeDepthController.biometricUnlockController.mMode == 1) {
                    if (notificationShadeDepthController.keyguardFastBioUnlockController.isMode(KeyguardFastBioUnlockController.MODE_FLAG_ENABLED)) {
                        return;
                    }
                    Animator animator = notificationShadeDepthController.keyguardAnimator;
                    if (animator != null) {
                        animator.cancel();
                    }
                    ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
                    ofFloat.setDuration(notificationShadeDepthController.dozeParameters.mAlwaysOnPolicy.wallpaperFadeOutDuration);
                    ofFloat.setStartDelay(((KeyguardStateControllerImpl) notificationShadeDepthController.keyguardStateController).mKeyguardFadingAwayDelay);
                    ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.NotificationShadeDepthController$keyguardStateCallback$1$onKeyguardFadingAwayChanged$1$1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            NotificationShadeDepthController notificationShadeDepthController2 = NotificationShadeDepthController.this;
                            new NotificationShadeDepthController.WakeAndUnlockBlurData(notificationShadeDepthController2.blurUtils.blurRadiusOfRatio(((Float) valueAnimator.getAnimatedValue()).floatValue()), false, 2, null);
                            notificationShadeDepthController2.getClass();
                        }
                    });
                    ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.NotificationShadeDepthController$keyguardStateCallback$1$onKeyguardFadingAwayChanged$1$2
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator2) {
                            NotificationShadeDepthController notificationShadeDepthController2 = NotificationShadeDepthController.this;
                            notificationShadeDepthController2.keyguardAnimator = null;
                            float f = 0.0f;
                            new NotificationShadeDepthController.WakeAndUnlockBlurData(f, false, 2, null);
                            notificationShadeDepthController2.getClass();
                        }
                    });
                    ofFloat.start();
                    notificationShadeDepthController.keyguardAnimator = ofFloat;
                }
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardShowingChanged() {
                Animator animator;
                NotificationShadeDepthController notificationShadeDepthController = NotificationShadeDepthController.this;
                if (!((KeyguardStateControllerImpl) notificationShadeDepthController.keyguardStateController).mShowing || (animator = notificationShadeDepthController.keyguardAnimator) == null) {
                    return;
                }
                animator.cancel();
            }
        };
        StatusBarStateController.StateListener stateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.NotificationShadeDepthController$statusBarStateCallback$1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozeAmountChanged(float f, float f2) {
                NotificationShadeDepthController notificationShadeDepthController = NotificationShadeDepthController.this;
                notificationShadeDepthController.prevDozeAmount = f2;
                new NotificationShadeDepthController.WakeAndUnlockBlurData(!notificationShadeDepthController.wallpaperSupportsAmbientMode ? 0.0f : notificationShadeDepthController.blurUtils.blurRadiusOfRatio(f2), false);
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozingChanged(boolean z) {
                if (z) {
                    NotificationShadeDepthController notificationShadeDepthController = NotificationShadeDepthController.this;
                    SpringAnimation springAnimation = notificationShadeDepthController.shadeAnimation.springAnimation;
                    if (springAnimation.mRunning) {
                        springAnimation.skipToEnd();
                    }
                    SpringAnimation springAnimation2 = notificationShadeDepthController.brightnessMirrorSpring.springAnimation;
                    if (springAnimation2.mRunning) {
                        springAnimation2.skipToEnd();
                    }
                }
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                NotificationShadeDepthController notificationShadeDepthController = NotificationShadeDepthController.this;
                notificationShadeDepthController.updateShadeAnimationBlur(notificationShadeDepthController.shadeExpansion, notificationShadeDepthController.prevShadeVelocity, notificationShadeDepthController.prevShadeDirection, notificationShadeDepthController.prevTracking);
                notificationShadeDepthController.scheduleUpdate();
            }
        };
        dumpManager.registerCriticalDumpable(NotificationShadeDepthController.class.getName(), this);
        ((KeyguardStateControllerImpl) keyguardStateController).addCallback(callback);
        statusBarStateController.addCallback(stateListener);
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) notificationShadeWindowController;
        if (notificationShadeWindowControllerImpl.mScrimsVisibilityListener != anonymousClass1) {
            notificationShadeWindowControllerImpl.mScrimsVisibilityListener = anonymousClass1;
        }
        depthAnimation.springAnimation.mSpring.setStiffness(200.0f);
        depthAnimation.springAnimation.mSpring.setDampingRatio(1.0f);
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(null), 3);
    }

    public final void animateBlur(float f, boolean z) {
        this.isBlurred = z;
        float f2 = (z && shouldApplyShadeBlur()) ? 1.0f : 0.0f;
        DepthAnimation depthAnimation = this.shadeAnimation;
        depthAnimation.springAnimation.mVelocity = f;
        int blurRadiusOfRatio = (int) this.blurUtils.blurRadiusOfRatio(f2);
        if (depthAnimation.pendingRadius == blurRadiusOfRatio) {
            return;
        }
        depthAnimation.pendingRadius = blurRadiusOfRatio;
        depthAnimation.springAnimation.animateToFinalPosition(blurRadiusOfRatio);
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00a6, code lost:
    
        if (r8.scrimsVisible != false) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final kotlin.Pair computeBlurAndZoomOut() {
        /*
            Method dump skipped, instructions count: 225
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationShadeDepthController.computeBlurAndZoomOut():kotlin.Pair");
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("StatusBarWindowBlurController:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("shadeExpansion: " + this.shadeExpansion);
        CoverScreenDecorHwcLayer$$ExternalSyntheticOutline0.m("shouldApplyShadeBlur: ", shouldApplyShadeBlur(), indentingPrintWriter);
        indentingPrintWriter.println("shadeAnimation: " + this.shadeAnimation.radius);
        indentingPrintWriter.println("brightnessMirrorRadius: " + this.brightnessMirrorSpring.radius);
        WakeAndUnlockBlurData wakeAndUnlockBlurData = this.wakeAndUnlockBlurData;
        indentingPrintWriter.println("wakeAndUnlockBlurRadius: " + wakeAndUnlockBlurData.radius);
        indentingPrintWriter.println("wakeAndUnlockBlurUsesZoom: " + wakeAndUnlockBlurData.useZoom);
        CoverScreenDecorHwcLayer$$ExternalSyntheticOutline0.m("blursDisabledForAppLaunch: ", this.blursDisabledForAppLaunch, indentingPrintWriter);
        CoverScreenDecorHwcLayer$$ExternalSyntheticOutline0.m("appLaunchTransitionIsInProgress: ", this.appLaunchTransitionIsInProgress, indentingPrintWriter);
        indentingPrintWriter.println("qsPanelExpansion: " + this.qsPanelExpansion);
        indentingPrintWriter.println("transitionToFullShadeProgress: " + this.transitionToFullShadeProgress);
        indentingPrintWriter.println("lastAppliedBlur: " + this.lastAppliedBlur);
    }

    @Override // com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
        float f = this.panelPullDownMinFraction;
        float f2 = 1.0f;
        float saturate = MathUtils.saturate((shadeExpansionChangeEvent.fraction - f) / (1.0f - f));
        float f3 = this.shadeExpansion;
        boolean z = shadeExpansionChangeEvent.tracking;
        if (f3 == saturate && this.prevTracking == z) {
            this.prevTimestamp = elapsedRealtimeNanos;
            return;
        }
        if (this.prevTimestamp < 0) {
            this.prevTimestamp = elapsedRealtimeNanos;
        } else {
            f2 = MathUtils.constrain((float) ((elapsedRealtimeNanos - r5) / 1.0E9d), 1.0E-5f, 1.0f);
        }
        float f4 = saturate - this.shadeExpansion;
        int signum = (int) Math.signum(f4);
        float constrain = MathUtils.constrain((f4 * 100.0f) / f2, -3000.0f, 3000.0f);
        if (saturate == 0.0f && this.appLaunchTransitionIsInProgress && !this.blursDisabledForAppLaunch) {
            Log.d("DepthController", "appLaunchTransitionIsInProgress is now false from shade expansion event");
            this.appLaunchTransitionIsInProgress = false;
        }
        updateShadeAnimationBlur(saturate, constrain, signum, z);
        this.prevShadeDirection = signum;
        this.prevShadeVelocity = constrain;
        this.shadeExpansion = saturate;
        this.prevTracking = z;
        this.prevTimestamp = elapsedRealtimeNanos;
        scheduleUpdate();
    }

    public final void scheduleUpdate() {
        Pair computeBlurAndZoomOut = computeBlurAndZoomOut();
        int intValue = ((Number) computeBlurAndZoomOut.component1()).intValue();
        ((Number) computeBlurAndZoomOut.component2()).floatValue();
        if (this.updateScheduled) {
            return;
        }
        this.updateScheduled = true;
        NotificationShadeWindowView notificationShadeWindowView = this.root;
        if (notificationShadeWindowView == null) {
            notificationShadeWindowView = null;
        }
        ViewRootImpl viewRootImpl = notificationShadeWindowView.getViewRootImpl();
        BlurUtils blurUtils = this.blurUtils;
        blurUtils.getClass();
        if (viewRootImpl != null && viewRootImpl.getSurfaceControl().isValid() && blurUtils.supportsBlursOnWindows() && !blurUtils.earlyWakeupEnabled) {
            if (!Intrinsics.areEqual(blurUtils.lastTargetViewRootImpl, viewRootImpl)) {
                blurUtils._transactionApplier = new SyncRtSurfaceTransactionApplier(viewRootImpl.getView());
                blurUtils.lastTargetViewRootImpl = viewRootImpl;
            }
            SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builder = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(viewRootImpl.getSurfaceControl());
            if (intValue != 0) {
                Trace.asyncTraceForTrackBegin(4096L, "BlurUtils", "eEarlyWakeup (prepareBlur)", 0);
                builder.withEarlyWakeupStart();
                blurUtils.earlyWakeupEnabled = true;
                blurUtils._transactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{builder.build()});
            }
        }
        this.choreographer.postFrameCallback(this.updateBlurCallback);
    }

    public final void setBlursDisabledForAppLaunch(boolean z) {
        if (this.blursDisabledForAppLaunch == z) {
            return;
        }
        if (z) {
            this.appLaunchTransitionIsInProgress = true;
        } else if (this.shadeExpansion == 0.0f) {
            this.appLaunchTransitionIsInProgress = false;
        }
        this.blursDisabledForAppLaunch = z;
        scheduleUpdate();
        float f = this.shadeExpansion;
        DepthAnimation depthAnimation = this.shadeAnimation;
        if (!(f == 0.0f && depthAnimation.radius == 0.0f) && z) {
            if (depthAnimation.pendingRadius != 0) {
                depthAnimation.pendingRadius = 0;
                depthAnimation.springAnimation.animateToFinalPosition(0);
            }
            SpringAnimation springAnimation = depthAnimation.springAnimation;
            if (springAnimation.mRunning) {
                springAnimation.skipToEnd();
            }
        }
    }

    public final boolean shouldApplyShadeBlur() {
        int state = this.statusBarStateController.getState();
        if (this.keyguardFastBioUnlockController.isMode(KeyguardFastBioUnlockController.MODE_FLAG_ENABLED) && state == 0) {
            return false;
        }
        return (state == 0 || state == 2) && !((KeyguardStateControllerImpl) this.keyguardStateController).mKeyguardFadingAway;
    }

    public final void updateShadeAnimationBlur(float f, float f2, int i, boolean z) {
        if (!shouldApplyShadeBlur()) {
            animateBlur(0.0f, false);
            this.isClosed = true;
            this.isOpen = false;
            return;
        }
        if (f <= 0.0f) {
            if (this.isClosed) {
                return;
            }
            this.isClosed = true;
            if (this.isBlurred) {
                animateBlur(f2, false);
                return;
            }
            return;
        }
        if (this.isClosed) {
            animateBlur(f2, true);
            this.isClosed = false;
        }
        if (z && !this.isBlurred) {
            animateBlur(0.0f, true);
        }
        if (!z && i < 0 && this.isBlurred) {
            animateBlur(f2, false);
        }
        if (f != 1.0f) {
            this.isOpen = false;
        } else {
            if (this.isOpen) {
                return;
            }
            this.isOpen = true;
            if (this.isBlurred) {
                return;
            }
            animateBlur(f2, true);
        }
    }

    public static /* synthetic */ void getBrightnessMirrorSpring$annotations() {
    }

    public static /* synthetic */ void getShadeExpansion$annotations() {
    }

    public static /* synthetic */ void getUpdateBlurCallback$annotations() {
    }

    public static /* synthetic */ void getWallpaperSupportsAmbientMode$annotations() {
    }
}
