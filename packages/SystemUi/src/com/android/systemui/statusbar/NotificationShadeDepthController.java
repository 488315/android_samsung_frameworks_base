package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.os.SystemClock;
import android.os.Trace;
import android.util.IndentingPrintWriter;
import android.util.MathUtils;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewRootImpl;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.app.animation.Interpolators;
import com.android.systemui.DisplayCutoutBaseView$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.QpRune;
import com.android.systemui.animation.ShadeInterpolation;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.LargeScreenUtils;
import com.android.systemui.util.WallpaperController;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.p055io.CloseableKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationShadeDepthController implements ShadeExpansionListener, Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BiometricUnlockController biometricUnlockController;
    public final BlurUtils blurUtils;
    public boolean blursDisabledForAppLaunch;
    public boolean blursDisabledForUnlock;
    public final DepthAnimation brightnessMirrorSpring;
    public final Choreographer choreographer;
    public final Context context;
    public final DozeParameters dozeParameters;
    public boolean inSplitShade;
    public boolean isBlurred;
    public boolean isOpen;
    public Animator keyguardAnimator;
    public final KeyguardFastBioUnlockController keyguardFastBioUnlockController;
    public final KeyguardStateController keyguardStateController;
    public int lastAppliedBlur;
    public final NotificationShadeWindowController notificationShadeWindowController;
    public float panelPullDownMinFraction;
    public int prevShadeDirection;
    public float prevShadeVelocity;
    public boolean prevTracking;
    public float qsPanelExpansion;
    public View root;
    public boolean scrimsVisible;
    public final DepthAnimation shadeAnimation;
    public float shadeExpansion;
    public final StatusBarStateController statusBarStateController;
    public float transitionToFullShadeProgress;
    public final NotificationShadeDepthController$updateBlurCallback$1 updateBlurCallback;
    public boolean updateScheduled;
    public boolean isClosed = true;
    public final List listeners = new ArrayList();
    public long prevTimestamp = -1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.systemui.statusbar.NotificationShadeDepthController$updateBlurCallback$1] */
    public NotificationShadeDepthController(KeyguardFastBioUnlockController keyguardFastBioUnlockController, StatusBarStateController statusBarStateController, BlurUtils blurUtils, BiometricUnlockController biometricUnlockController, KeyguardStateController keyguardStateController, Choreographer choreographer, WallpaperController wallpaperController, NotificationShadeWindowController notificationShadeWindowController, DozeParameters dozeParameters, Context context, DumpManager dumpManager, ConfigurationController configurationController) {
        this.keyguardFastBioUnlockController = keyguardFastBioUnlockController;
        this.statusBarStateController = statusBarStateController;
        this.blurUtils = blurUtils;
        this.biometricUnlockController = biometricUnlockController;
        this.keyguardStateController = keyguardStateController;
        this.choreographer = choreographer;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.dozeParameters = dozeParameters;
        this.context = context;
        DepthAnimation depthAnimation = new DepthAnimation();
        this.shadeAnimation = depthAnimation;
        this.brightnessMirrorSpring = new DepthAnimation();
        this.updateBlurCallback = new Choreographer.FrameCallback() { // from class: com.android.systemui.statusbar.NotificationShadeDepthController$updateBlurCallback$1
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                NotificationShadeDepthController notificationShadeDepthController = NotificationShadeDepthController.this;
                boolean z = false;
                notificationShadeDepthController.updateScheduled = false;
                Pair computeBlurAndZoomOut = notificationShadeDepthController.computeBlurAndZoomOut();
                int intValue = ((Number) computeBlurAndZoomOut.component1()).intValue();
                ((Number) computeBlurAndZoomOut.component2()).floatValue();
                NotificationShadeDepthController notificationShadeDepthController2 = NotificationShadeDepthController.this;
                if (notificationShadeDepthController2.scrimsVisible && !notificationShadeDepthController2.blursDisabledForAppLaunch) {
                    z = true;
                }
                Trace.traceCounter(4096L, "shade_blur_radius", intValue);
                NotificationShadeDepthController notificationShadeDepthController3 = NotificationShadeDepthController.this;
                BlurUtils blurUtils2 = notificationShadeDepthController3.blurUtils;
                View view = notificationShadeDepthController3.root;
                if (view == null) {
                    view = null;
                }
                blurUtils2.applyBlur(view.getViewRootImpl(), intValue, z);
                NotificationShadeDepthController notificationShadeDepthController4 = NotificationShadeDepthController.this;
                notificationShadeDepthController4.lastAppliedBlur = intValue;
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) notificationShadeDepthController4.notificationShadeWindowController;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                if (notificationShadeWindowState.backgroundBlurRadius == intValue) {
                    return;
                }
                notificationShadeWindowState.backgroundBlurRadius = intValue;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
            }
        };
        KeyguardStateController.Callback callback = new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.NotificationShadeDepthController$keyguardStateCallback$1
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardFadingAwayChanged() {
                final NotificationShadeDepthController notificationShadeDepthController = NotificationShadeDepthController.this;
                if (((KeyguardStateControllerImpl) notificationShadeDepthController.keyguardStateController).mKeyguardFadingAway && notificationShadeDepthController.biometricUnlockController.mMode == 1 && !notificationShadeDepthController.keyguardFastBioUnlockController.isEnabled()) {
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
                            notificationShadeDepthController2.blurUtils.blurRadiusOfRatio(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            notificationShadeDepthController2.getClass();
                        }
                    });
                    ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.NotificationShadeDepthController$keyguardStateCallback$1$onKeyguardFadingAwayChanged$1$2
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator2) {
                            NotificationShadeDepthController notificationShadeDepthController2 = NotificationShadeDepthController.this;
                            notificationShadeDepthController2.keyguardAnimator = null;
                            notificationShadeDepthController2.getClass();
                        }
                    });
                    ofFloat.start();
                    notificationShadeDepthController.keyguardAnimator = ofFloat;
                }
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardShowingChanged() {
                NotificationShadeDepthController notificationShadeDepthController = NotificationShadeDepthController.this;
                if (((KeyguardStateControllerImpl) notificationShadeDepthController.keyguardStateController).mShowing) {
                    Animator animator = notificationShadeDepthController.keyguardAnimator;
                    if (animator != null) {
                        animator.cancel();
                    }
                    notificationShadeDepthController.getClass();
                }
            }
        };
        StatusBarStateController.StateListener stateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.NotificationShadeDepthController$statusBarStateCallback$1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozeAmountChanged(float f, float f2) {
                NotificationShadeDepthController notificationShadeDepthController = NotificationShadeDepthController.this;
                notificationShadeDepthController.blurUtils.blurRadiusOfRatio(f2);
                notificationShadeDepthController.getClass();
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
        Consumer consumer = new Consumer() { // from class: com.android.systemui.statusbar.NotificationShadeDepthController.1
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
        };
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) notificationShadeWindowController;
        if (notificationShadeWindowControllerImpl.mScrimsVisibilityListener != consumer) {
            notificationShadeWindowControllerImpl.mScrimsVisibilityListener = consumer;
        }
        SpringAnimation springAnimation = depthAnimation.springAnimation;
        springAnimation.mSpring.setStiffness(200.0f);
        springAnimation.mSpring.setDampingRatio(1.0f);
        this.inSplitShade = LargeScreenUtils.shouldUseSplitNotificationShade(context.getResources());
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.NotificationShadeDepthController.2
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                int i = NotificationShadeDepthController.$r8$clinit;
                NotificationShadeDepthController notificationShadeDepthController = NotificationShadeDepthController.this;
                notificationShadeDepthController.inSplitShade = LargeScreenUtils.shouldUseSplitNotificationShade(notificationShadeDepthController.context.getResources());
            }
        });
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

    public final Pair computeBlurAndZoomOut() {
        float f = this.shadeAnimation.radius;
        BlurUtils blurUtils = this.blurUtils;
        float f2 = blurUtils.minBlurRadius;
        int i = blurUtils.maxBlurRadius;
        float max = Math.max(Math.max(Math.max((MathUtils.constrain(f, f2, i) * 0.19999999f) + (blurUtils.blurRadiusOfRatio(ShadeInterpolation.getNotificationScrimAlpha(shouldApplyShadeBlur() ? this.shadeExpansion : 0.0f)) * 0.8f), blurUtils.blurRadiusOfRatio(ShadeInterpolation.getNotificationScrimAlpha(this.qsPanelExpansion) * this.shadeExpansion)), blurUtils.blurRadiusOfRatio(this.transitionToFullShadeProgress)), 0.0f);
        if (this.blursDisabledForAppLaunch || this.blursDisabledForUnlock) {
            max = 0.0f;
        }
        float saturate = MathUtils.saturate((max > 0.0f ? 1 : (max == 0.0f ? 0 : -1)) == 0 ? 0.0f : MathUtils.map(blurUtils.minBlurRadius, i, 0.0f, 1.0f, max));
        int i2 = (int) max;
        if (this.inSplitShade) {
            saturate = 0.0f;
        }
        if (this.scrimsVisible) {
            saturate = 0.0f;
            i2 = 0;
        }
        if (!blurUtils.supportsBlursOnWindows()) {
            i2 = 0;
        }
        float f3 = i2;
        DepthAnimation depthAnimation = this.brightnessMirrorSpring;
        BlurUtils blurUtils2 = NotificationShadeDepthController.this.blurUtils;
        float f4 = depthAnimation.radius;
        blurUtils2.getClass();
        return new Pair(Integer.valueOf((int) ((1.0f - (f4 == 0.0f ? 0.0f : MathUtils.map(blurUtils2.minBlurRadius, blurUtils2.maxBlurRadius, 0.0f, 1.0f, f4))) * f3)), Float.valueOf(saturate));
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("StatusBarWindowBlurController:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("shadeExpansion: " + this.shadeExpansion);
        DisplayCutoutBaseView$$ExternalSyntheticOutline0.m93m("shouldApplyShadeBlur: ", shouldApplyShadeBlur(), indentingPrintWriter);
        indentingPrintWriter.println("shadeAnimation: " + this.shadeAnimation.radius);
        indentingPrintWriter.println("brightnessMirrorRadius: " + this.brightnessMirrorSpring.radius);
        indentingPrintWriter.println("wakeAndUnlockBlur: 0.0");
        DisplayCutoutBaseView$$ExternalSyntheticOutline0.m93m("blursDisabledForAppLaunch: ", this.blursDisabledForAppLaunch, indentingPrintWriter);
        indentingPrintWriter.println("qsPanelExpansion: " + this.qsPanelExpansion);
        indentingPrintWriter.println("transitionToFullShadeProgress: " + this.transitionToFullShadeProgress);
        indentingPrintWriter.println("lastAppliedBlur: " + this.lastAppliedBlur);
    }

    @Override // com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        if (QpRune.QUICK_PANEL_BLUR) {
            return;
        }
        long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
        float f = this.panelPullDownMinFraction;
        float f2 = 1.0f;
        float saturate = MathUtils.saturate((shadeExpansionChangeEvent.fraction - f) / (1.0f - f));
        boolean z = this.shadeExpansion == saturate;
        boolean z2 = shadeExpansionChangeEvent.tracking;
        if (z && this.prevTracking == z2) {
            this.prevTimestamp = elapsedRealtimeNanos;
            return;
        }
        if (this.prevTimestamp < 0) {
            this.prevTimestamp = elapsedRealtimeNanos;
        } else {
            f2 = MathUtils.constrain((float) ((elapsedRealtimeNanos - r5) / 1.0E9d), 1.0E-5f, 1.0f);
        }
        float f3 = saturate - this.shadeExpansion;
        int signum = (int) Math.signum(f3);
        float constrain = MathUtils.constrain((f3 * 100.0f) / f2, -3000.0f, 3000.0f);
        updateShadeAnimationBlur(saturate, constrain, signum, z2);
        this.prevShadeDirection = signum;
        this.prevShadeVelocity = constrain;
        this.shadeExpansion = saturate;
        this.prevTracking = z2;
        this.prevTimestamp = elapsedRealtimeNanos;
        scheduleUpdate();
    }

    public final void scheduleUpdate() {
        if (this.updateScheduled || QpRune.QUICK_PANEL_BLUR) {
            return;
        }
        if (this.statusBarStateController.getState() == 1 && ((KeyguardStateControllerImpl) this.keyguardStateController).mKeyguardGoingAway) {
            return;
        }
        this.updateScheduled = true;
        int intValue = ((Number) computeBlurAndZoomOut().component1()).intValue();
        View view = this.root;
        if (view == null) {
            view = null;
        }
        ViewRootImpl viewRootImpl = view.getViewRootImpl();
        BlurUtils blurUtils = this.blurUtils;
        blurUtils.getClass();
        if (viewRootImpl != null && viewRootImpl.getSurfaceControl().isValid() && blurUtils.supportsBlursOnWindows() && !blurUtils.earlyWakeupEnabled && blurUtils.lastAppliedBlur == 0 && intValue != 0) {
            Trace.asyncTraceForTrackBegin(4096L, "BlurUtils", "eEarlyWakeup (prepareBlur)", 0);
            blurUtils.earlyWakeupEnabled = true;
            SurfaceControl.Transaction createTransaction = blurUtils.createTransaction();
            try {
                createTransaction.setEarlyWakeupStart();
                createTransaction.apply();
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(createTransaction, null);
            } finally {
            }
        }
        this.choreographer.postFrameCallback(this.updateBlurCallback);
    }

    public final void setBlursDisabledForAppLaunch(boolean z) {
        if (this.blursDisabledForAppLaunch == z) {
            return;
        }
        this.blursDisabledForAppLaunch = z;
        scheduleUpdate();
        boolean z2 = this.shadeExpansion == 0.0f;
        DepthAnimation depthAnimation = this.shadeAnimation;
        if (z2) {
            if (depthAnimation.radius == 0.0f) {
                return;
            }
        }
        if (z) {
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
        if (this.keyguardFastBioUnlockController.isEnabled() && state == 0) {
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
        if (!(f == 1.0f)) {
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
}
