package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.keyguard.KeyguardSecVisibilityHelper$mSetVisibleEndRunnable$1;
import com.android.systemui.DejankUtils;
import com.android.systemui.LsRune;
import com.android.systemui.Rune;
import com.android.systemui.aod.AODTouchModeManager;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractorKt;
import com.android.systemui.plugins.aod.PluginAOD;
import com.android.systemui.plugins.clocks.ClockConfig;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.scene.ui.view.WindowRootView;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor;
import com.android.systemui.statusbar.CircleReveal;
import com.android.systemui.statusbar.LightRevealScrim;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class UnlockedScreenOffAnimationController implements WakefulnessLifecycle.Observer, ScreenOffAnimation {
    public float animatorDurationScale = 1.0f;
    public final UnlockedScreenOffAnimationController$animatorDurationScaleObserver$1 animatorDurationScaleObserver;
    public CentralSurfacesImpl centralSurfaces;
    public final Context context;
    public Boolean decidedToAnimateGoingToSleep;
    public final Lazy dozeParameters;
    public final GlobalSettings globalSettings;
    public final Handler handler;
    public final SecUnlockedScreenOffAnimationHelper helper;
    public boolean initialized;
    public final InteractionJankMonitor interactionJankMonitor;
    public final KeyguardStateController keyguardStateController;
    public final Lazy keyguardViewMediatorLazy;
    public boolean lightRevealAnimationPlaying;
    public final ValueAnimator lightRevealAnimator;
    public LightRevealScrim lightRevealScrim;
    public final Lazy notifShadeWindowControllerLazy;
    public final Lazy panelExpansionInteractorLazy;
    public final PowerManager powerManager;
    public final Lazy shadeLockscreenInteractorLazy;
    public boolean shouldAnimateInKeyguard;
    public final UnlockedScreenOffAnimationController$special$$inlined$namedRunnable$1 startLightRevealCallback;
    public final StatusBarStateControllerImpl statusBarStateControllerImpl;
    public final WakefulnessLifecycle wakefulnessLifecycle;

    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$animatorDurationScaleObserver$1] */
    public UnlockedScreenOffAnimationController(SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper, Context context, WakefulnessLifecycle wakefulnessLifecycle, StatusBarStateControllerImpl statusBarStateControllerImpl, Lazy lazy, KeyguardStateController keyguardStateController, Lazy lazy2, GlobalSettings globalSettings, Lazy lazy3, InteractionJankMonitor interactionJankMonitor, PowerManager powerManager, Lazy lazy4, Lazy lazy5, Handler handler) {
        this.helper = secUnlockedScreenOffAnimationHelper;
        this.context = context;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.statusBarStateControllerImpl = statusBarStateControllerImpl;
        this.keyguardViewMediatorLazy = lazy;
        this.keyguardStateController = keyguardStateController;
        this.dozeParameters = lazy2;
        this.globalSettings = globalSettings;
        this.notifShadeWindowControllerLazy = lazy3;
        this.interactionJankMonitor = interactionJankMonitor;
        this.powerManager = powerManager;
        this.shadeLockscreenInteractorLazy = lazy4;
        this.panelExpansionInteractorLazy = lazy5;
        this.handler = handler;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimatorOfFloat.setDuration(LsRune.AOD_FULLSCREEN ? 650L : 500L);
        valueAnimatorOfFloat.setInterpolator(Interpolators.LINEAR);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$lightRevealAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                LightRevealScrim lightRevealScrim = this.this$0.lightRevealScrim;
                if (!((lightRevealScrim == null ? null : lightRevealScrim).revealEffect instanceof CircleReveal)) {
                    if (lightRevealScrim == null) {
                        lightRevealScrim = null;
                    }
                    lightRevealScrim.setRevealAmount(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    boolean z = LsRune.AOD_FULLSCREEN;
                    final SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper2 = this.this$0.helper;
                    Consumer consumer = new Consumer() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$lightRevealAnimator$1$1.1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            secUnlockedScreenOffAnimationHelper2.onAmountChanged(((Number) obj).floatValue());
                        }
                    };
                    Float f = (Float) valueAnimator.getAnimatedValue();
                    f.floatValue();
                    if (z) {
                        consumer.accept(f);
                    }
                }
                UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = this.this$0;
                LightRevealScrim lightRevealScrim2 = unlockedScreenOffAnimationController.lightRevealScrim;
                if ((lightRevealScrim2 != null ? lightRevealScrim2 : null).interpolatedRevealAmount >= 0.1f || !unlockedScreenOffAnimationController.interactionJankMonitor.isInstrumenting(40)) {
                    return;
                }
                this.this$0.interactionJankMonitor.end(40);
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$lightRevealAnimator$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                LightRevealScrim lightRevealScrim = this.this$0.lightRevealScrim;
                if (!((lightRevealScrim == null ? null : lightRevealScrim).revealEffect instanceof CircleReveal)) {
                    if (lightRevealScrim == null) {
                        lightRevealScrim = null;
                    }
                    lightRevealScrim.setRevealAmount(1.0f);
                }
                boolean z = LsRune.AOD_FULLSCREEN;
                final SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper2 = this.this$0.helper;
                Rune.runIf(new Runnable() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$lightRevealAnimator$1$2$onAnimationCancel$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper3 = secUnlockedScreenOffAnimationHelper2;
                        SecUnlockedScreenOffAnimationHelper.logD("onAnimationCancel deviceInteractive=" + secUnlockedScreenOffAnimationHelper3.deviceInteractive);
                        secUnlockedScreenOffAnimationHelper3.onAmountChanged(secUnlockedScreenOffAnimationHelper3.deviceInteractive ? 1.0f : 0.0f);
                    }
                }, z);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = this.this$0;
                unlockedScreenOffAnimationController.lightRevealAnimationPlaying = false;
                unlockedScreenOffAnimationController.interactionJankMonitor.end(40);
                boolean z = LsRune.AOD_FULLSCREEN;
                final SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper2 = this.this$0.helper;
                Rune.runIf(new Runnable() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$lightRevealAnimator$1$2$onAnimationEnd$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper3 = secUnlockedScreenOffAnimationHelper2;
                        secUnlockedScreenOffAnimationHelper3.getClass();
                        if (LsRune.AOD_TSP_CONTROL && secUnlockedScreenOffAnimationHelper3.aodAmbientWallpaperHelper.isAODFullScreenMode()) {
                            secUnlockedScreenOffAnimationHelper3.aodTouchModeManager.setTouchMode(AODTouchModeManager.TouchMode.DOUBLE);
                        }
                        PluginAODManager pluginAODManager = (PluginAODManager) secUnlockedScreenOffAnimationHelper3.pluginAODManagerLazy.get();
                        Log.d("PluginAODManager", "onUnlockedScreenOffAnimationEnd: mAODPlugin=" + pluginAODManager.mAODPlugin);
                        PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
                        if (pluginAOD != null) {
                            pluginAOD.onUnlockedScreenOffAnimationEnd();
                        }
                        SecUnlockedScreenOffAnimationHelper.logD("updatePendingLock");
                        ((KeyguardViewMediator) secUnlockedScreenOffAnimationHelper3.keyguardViewMediatorLazy.get()).maybeHandlePendingLock();
                        CentralSurfacesImpl centralSurfacesImpl = secUnlockedScreenOffAnimationHelper3.centralSurfaces;
                        if (centralSurfacesImpl == null) {
                            centralSurfacesImpl = null;
                        }
                        centralSurfacesImpl.updateIsKeyguard(false);
                        UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0 unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = secUnlockedScreenOffAnimationHelper3.clearDecidedToAnimateGoingToSleep;
                        (unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 != null ? unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 : null).invoke();
                    }
                }, z);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = this.this$0;
                unlockedScreenOffAnimationController.interactionJankMonitor.begin(((NotificationShadeWindowControllerImpl) ((NotificationShadeWindowController) unlockedScreenOffAnimationController.notifShadeWindowControllerLazy.get())).mWindowRootView, 40);
                boolean z = LsRune.AOD_FULLSCREEN;
                final SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper2 = this.this$0.helper;
                Rune.runIf(new Runnable() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$lightRevealAnimator$1$2$onAnimationStart$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper3 = secUnlockedScreenOffAnimationHelper2;
                        SecUnlockedScreenOffAnimationHelper.logD("onAnimationStart needUpdateSetLockScreenShown=" + secUnlockedScreenOffAnimationHelper3.needUpdateSetLockScreenShown);
                        if (secUnlockedScreenOffAnimationHelper3.needUpdateSetLockScreenShown) {
                            return;
                        }
                        secUnlockedScreenOffAnimationHelper3.needUpdateSetLockScreenShown = true;
                        secUnlockedScreenOffAnimationHelper3.updateSetLockScreenShown(false);
                        secUnlockedScreenOffAnimationHelper3.playWallpaperAnimation();
                    }
                }, z);
            }
        });
        this.lightRevealAnimator = valueAnimatorOfFloat;
        this.startLightRevealCallback = new UnlockedScreenOffAnimationController$special$$inlined$namedRunnable$1("startLightReveal", this);
        this.animatorDurationScaleObserver = new ContentObserver() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$animatorDurationScaleObserver$1
            {
                super(null);
            }

            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = this.this$0;
                unlockedScreenOffAnimationController.animatorDurationScale = WindowManager.fixScale(unlockedScreenOffAnimationController.globalSettings.getFloat(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE, 1.0f));
            }
        };
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final void animateInKeyguard(View view, final KeyguardSecVisibilityHelper$mSetVisibleEndRunnable$1 keyguardSecVisibilityHelper$mSetVisibleEndRunnable$1) {
        String id;
        ClockConfig config;
        this.shouldAnimateInKeyguard = false;
        view.setAlpha(0.0f);
        view.setVisibility(0);
        float y = view.getY();
        view.setY(y - (view.getHeight() * 0.1f));
        AnimatableProperty.AnonymousClass7 anonymousClass7 = AnimatableProperty.Y;
        PropertyAnimator.cancelAnimation(view, anonymousClass7);
        AnimationProperties animationProperties = new AnimationProperties();
        long j = 500;
        animationProperties.duration = j;
        PropertyAnimator.setProperty(view, anonymousClass7, y, animationProperties, true);
        this.interactionJankMonitor.cancel(41);
        AnimatableProperty.AnonymousClass7 anonymousClass72 = AnimatableProperty.ALPHA;
        PropertyAnimator.cancelAnimation(view, anonymousClass72);
        AnimationProperties animationProperties2 = new AnimationProperties();
        animationProperties2.delay = 0L;
        animationProperties2.duration = j;
        animationProperties2.mAnimationEndAction = new Consumer() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController.animateInKeyguard.1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((KeyguardViewMediator) UnlockedScreenOffAnimationController.this.keyguardViewMediatorLazy.get()).maybeHandlePendingLock();
                CentralSurfacesImpl centralSurfacesImpl = UnlockedScreenOffAnimationController.this.centralSurfaces;
                if (centralSurfacesImpl == null) {
                    centralSurfacesImpl = null;
                }
                centralSurfacesImpl.updateIsKeyguard(false);
                keyguardSecVisibilityHelper$mSetVisibleEndRunnable$1.run();
                UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = UnlockedScreenOffAnimationController.this;
                unlockedScreenOffAnimationController.decidedToAnimateGoingToSleep = null;
                unlockedScreenOffAnimationController.interactionJankMonitor.end(41);
            }
        };
        animationProperties2.mAnimationCancelAction = new Consumer() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController.animateInKeyguard.2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = UnlockedScreenOffAnimationController.this;
                unlockedScreenOffAnimationController.decidedToAnimateGoingToSleep = null;
                unlockedScreenOffAnimationController.interactionJankMonitor.cancel(41);
            }
        };
        animationProperties2.setCustomInterpolator(View.ALPHA, Interpolators.FAST_OUT_SLOW_IN);
        PropertyAnimator.setProperty(view, anonymousClass72, 1.0f, animationProperties2, true);
        WindowRootView windowRootView = ((NotificationShadeWindowControllerImpl) ((NotificationShadeWindowController) this.notifShadeWindowControllerLazy.get())).mWindowRootView;
        if (windowRootView == null) {
            throw new IllegalStateException("Required value was null.");
        }
        InteractionJankMonitor.Configuration.Builder builderWithView = InteractionJankMonitor.Configuration.Builder.withView(41, windowRootView);
        ClockController clockController = ((KeyguardClockInteractor) this.statusBarStateControllerImpl.mKeyguardClockInteractorLazy.get()).clock$receiver.clock;
        if (clockController == null || (config = clockController.getConfig()) == null || (id = config.getId()) == null) {
            Log.e(KeyguardClockInteractorKt.TAG, "No clock is available");
            id = "MISSING_CLOCK_ID";
        }
        this.interactionJankMonitor.begin(builderWithView.setTag(id));
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final void initialize(CentralSurfaces centralSurfaces, ShadeViewController shadeViewController, LightRevealScrim lightRevealScrim) {
        if (LsRune.AOD_FULLSCREEN) {
            CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfaces;
            this.helper.init(centralSurfacesImpl, new UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0(this, 0), new UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0(this, 1));
            this.initialized = true;
            this.lightRevealScrim = lightRevealScrim;
            this.centralSurfaces = centralSurfacesImpl;
            GlobalSettings globalSettings = this.globalSettings;
            this.animatorDurationScale = WindowManager.fixScale(globalSettings.getFloat(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE, 1.0f));
            globalSettings.registerContentObserverSync(Settings.Global.getUriFor(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE), false, (ContentObserver) this.animatorDurationScaleObserver);
            this.wakefulnessLifecycle.addObserver(this);
        }
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean isAnimationPlaying() {
        return this.lightRevealAnimationPlaying;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean isKeyguardShowDelayed() {
        return this.lightRevealAnimationPlaying;
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onFinishedWakingUp() {
        if (((DozeParameters) this.dozeParameters.get()).canControlUnlockedScreenOff()) {
            CentralSurfacesImpl centralSurfacesImpl = this.centralSurfaces;
            if (centralSurfacesImpl == null) {
                centralSurfacesImpl = null;
            }
            centralSurfacesImpl.updateIsKeyguard(true);
        }
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onStartedWakingUp() {
        this.decidedToAnimateGoingToSleep = null;
        this.shouldAnimateInKeyguard = false;
        boolean z = DejankUtils.STRICT_MODE_ENABLED;
        Assert.isMainThread();
        ArrayList arrayList = DejankUtils.sPendingRunnables;
        UnlockedScreenOffAnimationController$special$$inlined$namedRunnable$1 unlockedScreenOffAnimationController$special$$inlined$namedRunnable$1 = this.startLightRevealCallback;
        arrayList.remove(unlockedScreenOffAnimationController$special$$inlined$namedRunnable$1);
        DejankUtils.sHandler.removeCallbacks(unlockedScreenOffAnimationController$special$$inlined$namedRunnable$1);
        this.lightRevealAnimator.cancel();
        this.lightRevealAnimationPlaying = false;
        this.handler.removeCallbacksAndMessages(null);
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean overrideNotificationsDozeAmount() {
        return shouldPlayUnlockedScreenOffAnimation() && this.lightRevealAnimationPlaying;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldAnimateInKeyguard() {
        return this.shouldAnimateInKeyguard;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldDelayDisplayDozeTransition() {
        return shouldPlayUnlockedScreenOffAnimation();
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldDelayKeyguardShow() {
        return shouldPlayUnlockedScreenOffAnimation();
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldHideScrimOnWakeUp() {
        return this.lightRevealAnimationPlaying;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldPlayAnimation() {
        return shouldPlayUnlockedScreenOffAnimation();
    }

    public final boolean shouldPlayUnlockedScreenOffAnimation() {
        if (!this.initialized) {
            return false;
        }
        if (LsRune.AOD_FULLSCREEN) {
            return this.helper.shouldPlayUnlockedScreenOffAnimation();
        }
        if (!((DozeParameters) this.dozeParameters.get()).canControlUnlockedScreenOff() || Intrinsics.areEqual(this.decidedToAnimateGoingToSleep, Boolean.FALSE) || Intrinsics.areEqual(Settings.Global.getString(this.context.getContentResolver(), SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE), "0.0") || this.statusBarStateControllerImpl.mState != 0) {
            return false;
        }
        if ((this.centralSurfaces == null || ((PanelExpansionInteractor) this.panelExpansionInteractorLazy.get()).isPanelExpanded()) && !this.lightRevealAnimationPlaying) {
            return false;
        }
        if (DeviceState.shouldEnableKeyguardScreenRotation(((KeyguardStateControllerImpl) this.keyguardStateController).mContext)) {
            return true;
        }
        Display display = this.context.getDisplay();
        return display != null && display.getRotation() == 0;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean startAnimation() {
        if (!shouldPlayUnlockedScreenOffAnimation()) {
            this.decidedToAnimateGoingToSleep = Boolean.FALSE;
            return false;
        }
        boolean z = LsRune.AOD_FULLSCREEN;
        final SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = this.helper;
        Rune.runIf(new Runnable() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController.startAnimation.1
            @Override // java.lang.Runnable
            public final void run() {
                secUnlockedScreenOffAnimationHelper.onPrepare();
            }
        }, z);
        this.decidedToAnimateGoingToSleep = Boolean.TRUE;
        this.shouldAnimateInKeyguard = !z;
        this.lightRevealAnimationPlaying = z;
        DejankUtils.postAfterTraversal(this.startLightRevealCallback);
        this.handler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController.startAnimation.2
            @Override // java.lang.Runnable
            public final void run() {
                if (UnlockedScreenOffAnimationController.this.powerManager.isInteractive(0)) {
                    return;
                }
                if (LsRune.AOD_FULLSCREEN || UnlockedScreenOffAnimationController.this.shouldAnimateInKeyguard) {
                    ((ShadeLockscreenInteractor) UnlockedScreenOffAnimationController.this.shadeLockscreenInteractorLazy.get()).showAodUi();
                }
            }
        }, (long) ((z ? 300L : 600L) * this.animatorDurationScale));
        return true;
    }
}
