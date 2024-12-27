package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Trace;
import android.util.IndentingPrintWriter;
import android.util.MathUtils;
import android.view.Choreographer;
import android.view.View;
import android.view.ViewRootImpl;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.app.animation.Interpolators;
import com.android.systemui.Dumpable;
import com.android.systemui.animation.ShadeInterpolation;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shared.navigationbar.RegionSamplingHelper;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import com.android.systemui.util.WallpaperController;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class NotificationShadeDepthController implements ShadeExpansionListener, Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BiometricUnlockController biometricUnlockController;
    public final BlurUtils blurUtils;
    public boolean blursDisabledForAppLaunch;
    public boolean blursDisabledForUnlock;
    public final DepthAnimation brightnessMirrorSpring;
    public final Context context;
    public final DozeParameters dozeParameters;
    public boolean isBlurred;
    public Animator keyguardAnimator;
    public final KeyguardFastBioUnlockController keyguardFastBioUnlockController;
    public final KeyguardStateController keyguardStateController;
    public int lastAppliedBlur;
    public final NotificationShadeWindowController notificationShadeWindowController;
    public View root;
    public boolean scrimsVisible;
    public final DepthAnimation shadeAnimation;
    public final SplitShadeStateController splitShadeStateController;
    public final StatusBarStateController statusBarStateController;
    public float transitionToFullShadeProgress;
    public final WallpaperController wallpaperController;
    public boolean isClosed = true;
    public final List listeners = new ArrayList();

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final class DepthAnimation {
        public int pendingRadius = -1;
        public float radius;
        public final SpringAnimation springAnimation;

        public DepthAnimation() {
            SpringAnimation springAnimation = new SpringAnimation(this, new FloatPropertyCompat() { // from class: com.android.systemui.statusbar.NotificationShadeDepthController$DepthAnimation$springAnimation$1
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
                    r2.getClass();
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

    public NotificationShadeDepthController(KeyguardFastBioUnlockController keyguardFastBioUnlockController, StatusBarStateController statusBarStateController, BlurUtils blurUtils, BiometricUnlockController biometricUnlockController, KeyguardStateController keyguardStateController, Choreographer choreographer, WallpaperController wallpaperController, NotificationShadeWindowController notificationShadeWindowController, DozeParameters dozeParameters, Context context, SplitShadeStateController splitShadeStateController, DumpManager dumpManager, ConfigurationController configurationController) {
        this.keyguardFastBioUnlockController = keyguardFastBioUnlockController;
        this.statusBarStateController = statusBarStateController;
        this.blurUtils = blurUtils;
        this.biometricUnlockController = biometricUnlockController;
        this.keyguardStateController = keyguardStateController;
        this.wallpaperController = wallpaperController;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.dozeParameters = dozeParameters;
        this.context = context;
        this.splitShadeStateController = splitShadeStateController;
        DepthAnimation depthAnimation = new DepthAnimation();
        this.shadeAnimation = depthAnimation;
        this.brightnessMirrorSpring = new DepthAnimation();
        new Choreographer.FrameCallback() { // from class: com.android.systemui.statusbar.NotificationShadeDepthController$updateBlurCallback$1
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                NotificationShadeDepthController notificationShadeDepthController = NotificationShadeDepthController.this;
                notificationShadeDepthController.getClass();
                float f = notificationShadeDepthController.shadeAnimation.radius;
                BlurUtils blurUtils2 = notificationShadeDepthController.blurUtils;
                float f2 = blurUtils2.minBlurRadius;
                int i = blurUtils2.maxBlurRadius;
                float constrain = MathUtils.constrain(f, f2, i);
                notificationShadeDepthController.shouldApplyShadeBlur();
                float f3 = 0.0f;
                float max = Math.max(Math.max(Math.max((constrain * 0.19999999f) + (blurUtils2.blurRadiusOfRatio(ShadeInterpolation.getNotificationScrimAlpha(0.0f)) * 0.8f), blurUtils2.blurRadiusOfRatio(ShadeInterpolation.getNotificationScrimAlpha(0.0f) * 0.0f)), blurUtils2.blurRadiusOfRatio(notificationShadeDepthController.transitionToFullShadeProgress)), 0.0f);
                if (notificationShadeDepthController.blursDisabledForAppLaunch || notificationShadeDepthController.blursDisabledForUnlock) {
                    max = 0.0f;
                }
                float saturate = MathUtils.saturate(max == 0.0f ? 0.0f : MathUtils.map(blurUtils2.minBlurRadius, i, 0.0f, 1.0f, max));
                int i2 = (int) max;
                if (notificationShadeDepthController.scrimsVisible) {
                    saturate = 0.0f;
                    i2 = 0;
                }
                if (!blurUtils2.supportsBlursOnWindows()) {
                    i2 = 0;
                }
                float f4 = i2;
                NotificationShadeDepthController.DepthAnimation depthAnimation2 = notificationShadeDepthController.brightnessMirrorSpring;
                BlurUtils blurUtils3 = NotificationShadeDepthController.this.blurUtils;
                float f5 = depthAnimation2.radius;
                if (f5 == 0.0f) {
                    blurUtils3.getClass();
                } else {
                    f3 = MathUtils.map(blurUtils3.minBlurRadius, blurUtils3.maxBlurRadius, 0.0f, 1.0f, f5);
                }
                Pair pair = new Pair(Integer.valueOf((int) ((1.0f - f3) * f4)), Float.valueOf(saturate));
                int intValue = ((Number) pair.component1()).intValue();
                float floatValue = ((Number) pair.component2()).floatValue();
                NotificationShadeDepthController notificationShadeDepthController2 = NotificationShadeDepthController.this;
                if (notificationShadeDepthController2.scrimsVisible) {
                    boolean z = notificationShadeDepthController2.blursDisabledForAppLaunch;
                }
                Trace.traceCounter(4096L, "shade_blur_radius", intValue);
                NotificationShadeDepthController notificationShadeDepthController3 = NotificationShadeDepthController.this;
                BlurUtils blurUtils4 = notificationShadeDepthController3.blurUtils;
                View view = notificationShadeDepthController3.root;
                if (view == null) {
                    view = null;
                }
                ViewRootImpl viewRootImpl = view.getViewRootImpl();
                blurUtils4.getClass();
                if (viewRootImpl != null) {
                    viewRootImpl.getSurfaceControl().isValid();
                }
                NotificationShadeDepthController notificationShadeDepthController4 = NotificationShadeDepthController.this;
                notificationShadeDepthController4.lastAppliedBlur = intValue;
                notificationShadeDepthController4.wallpaperController.setNotificationShadeZoom(floatValue);
                Iterator it = ((ArrayList) NotificationShadeDepthController.this.listeners).iterator();
                while (it.hasNext()) {
                    NavigationBar.AnonymousClass6 anonymousClass6 = (NavigationBar.AnonymousClass6) it.next();
                    anonymousClass6.getClass();
                    boolean z2 = intValue != 0;
                    if (z2 != anonymousClass6.mHasBlurs) {
                        anonymousClass6.mHasBlurs = z2;
                        RegionSamplingHelper regionSamplingHelper = NavigationBar.this.mRegionSamplingHelper;
                        regionSamplingHelper.mWindowHasBlurs = z2;
                        regionSamplingHelper.updateSamplingListener();
                    }
                }
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) NotificationShadeDepthController.this.notificationShadeWindowController;
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
                if (((KeyguardStateControllerImpl) notificationShadeDepthController.keyguardStateController).mKeyguardFadingAway && notificationShadeDepthController.biometricUnlockController.mMode == 1 && !notificationShadeDepthController.keyguardFastBioUnlockController.isMode(KeyguardFastBioUnlockController.MODE_FLAG_ENABLED)) {
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
                notificationShadeDepthController.getClass();
                if (!notificationShadeDepthController.shouldApplyShadeBlur()) {
                    notificationShadeDepthController.animateBlur(false);
                    notificationShadeDepthController.isClosed = true;
                } else {
                    if (notificationShadeDepthController.isClosed) {
                        return;
                    }
                    notificationShadeDepthController.isClosed = true;
                    if (notificationShadeDepthController.isBlurred) {
                        notificationShadeDepthController.animateBlur(false);
                    }
                }
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
            }
        };
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) notificationShadeWindowController;
        if (notificationShadeWindowControllerImpl.mScrimsVisibilityListener != consumer) {
            notificationShadeWindowControllerImpl.mScrimsVisibilityListener = consumer;
        }
        depthAnimation.springAnimation.mSpring.setStiffness(200.0f);
        depthAnimation.springAnimation.mSpring.setDampingRatio(1.0f);
        context.getResources();
        ((SplitShadeStateControllerImpl) splitShadeStateController).shouldUseSplitNotificationShade();
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.NotificationShadeDepthController.2
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                int i = NotificationShadeDepthController.$r8$clinit;
                NotificationShadeDepthController notificationShadeDepthController = NotificationShadeDepthController.this;
                notificationShadeDepthController.context.getResources();
                ((SplitShadeStateControllerImpl) notificationShadeDepthController.splitShadeStateController).shouldUseSplitNotificationShade();
            }
        });
    }

    public final void animateBlur(boolean z) {
        this.isBlurred = z;
        float f = (z && shouldApplyShadeBlur()) ? 1.0f : 0.0f;
        DepthAnimation depthAnimation = this.shadeAnimation;
        depthAnimation.springAnimation.mVelocity = 0.0f;
        int blurRadiusOfRatio = (int) this.blurUtils.blurRadiusOfRatio(f);
        if (depthAnimation.pendingRadius == blurRadiusOfRatio) {
            return;
        }
        depthAnimation.pendingRadius = blurRadiusOfRatio;
        depthAnimation.springAnimation.animateToFinalPosition(blurRadiusOfRatio);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("StatusBarWindowBlurController:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("shadeExpansion: 0.0");
        indentingPrintWriter.println("shouldApplyShadeBlur: " + shouldApplyShadeBlur());
        indentingPrintWriter.println("shadeAnimation: " + this.shadeAnimation.radius);
        indentingPrintWriter.println("brightnessMirrorRadius: " + this.brightnessMirrorSpring.radius);
        indentingPrintWriter.println("wakeAndUnlockBlur: 0.0");
        indentingPrintWriter.println("blursDisabledForAppLaunch: " + this.blursDisabledForAppLaunch);
        indentingPrintWriter.println("qsPanelExpansion: 0.0");
        indentingPrintWriter.println("transitionToFullShadeProgress: " + this.transitionToFullShadeProgress);
        indentingPrintWriter.println("lastAppliedBlur: " + this.lastAppliedBlur);
    }

    public final void setBlursDisabledForAppLaunch(boolean z) {
        if (this.blursDisabledForAppLaunch == z) {
            return;
        }
        this.blursDisabledForAppLaunch = z;
        DepthAnimation depthAnimation = this.shadeAnimation;
        if (depthAnimation.radius != 0.0f && z) {
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

    public static /* synthetic */ void getBrightnessMirrorSpring$annotations() {
    }

    public static /* synthetic */ void getShadeExpansion$annotations() {
    }

    public static /* synthetic */ void getUpdateBlurCallback$annotations() {
    }

    @Override // com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
    }
}
