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
import com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda0;
import com.android.systemui.DejankUtils;
import com.android.systemui.Flags;
import com.android.systemui.LsRune;
import com.android.systemui.Rune;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.aod.PluginAOD;
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
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class UnlockedScreenOffAnimationController implements WakefulnessLifecycle.Observer, ScreenOffAnimation {
    public float animatorDurationScale;
    public final UnlockedScreenOffAnimationController$animatorDurationScaleObserver$1 animatorDurationScaleObserver;
    public boolean aodUiAnimationPlaying;
    public CentralSurfaces centralSurfaces;
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
    public final Lazy lazyBlurController;
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

    public UnlockedScreenOffAnimationController(SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper, Context context, WakefulnessLifecycle wakefulnessLifecycle, StatusBarStateControllerImpl statusBarStateControllerImpl, Lazy lazy, KeyguardStateController keyguardStateController, Lazy lazy2, GlobalSettings globalSettings, Lazy lazy3, InteractionJankMonitor interactionJankMonitor, PowerManager powerManager, Lazy lazy4, Lazy lazy5, Handler handler, Lazy lazy6) {
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
        this.lazyBlurController = lazy6;
        this.animatorDurationScale = 1.0f;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat.setDuration(LsRune.AOD_FULLSCREEN ? 650L : 500L);
        ofFloat.setInterpolator(Interpolators.LINEAR);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$lightRevealAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                Flags.lightRevealMigration();
                LightRevealScrim lightRevealScrim = UnlockedScreenOffAnimationController.this.lightRevealScrim;
                if (!((lightRevealScrim == null ? null : lightRevealScrim).revealEffect instanceof CircleReveal)) {
                    if (lightRevealScrim == null) {
                        lightRevealScrim = null;
                    }
                    lightRevealScrim.setRevealAmount(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    boolean z = LsRune.AOD_FULLSCREEN;
                    final SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper2 = UnlockedScreenOffAnimationController.this.helper;
                    Consumer consumer = new Consumer() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$lightRevealAnimator$1$1.1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            SecUnlockedScreenOffAnimationHelper.this.onAmountChanged(((Number) obj).floatValue());
                        }
                    };
                    Float f = (Float) valueAnimator.getAnimatedValue();
                    if (z) {
                        consumer.accept(f);
                    }
                }
                UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = UnlockedScreenOffAnimationController.this;
                LightRevealScrim lightRevealScrim2 = unlockedScreenOffAnimationController.lightRevealScrim;
                if ((lightRevealScrim2 != null ? lightRevealScrim2 : null).interpolatedRevealAmount >= 0.1f || !unlockedScreenOffAnimationController.interactionJankMonitor.isInstrumenting(40)) {
                    return;
                }
                UnlockedScreenOffAnimationController.this.interactionJankMonitor.end(40);
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$lightRevealAnimator$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                Flags.lightRevealMigration();
                LightRevealScrim lightRevealScrim = UnlockedScreenOffAnimationController.this.lightRevealScrim;
                if (!((lightRevealScrim == null ? null : lightRevealScrim).revealEffect instanceof CircleReveal)) {
                    if (lightRevealScrim == null) {
                        lightRevealScrim = null;
                    }
                    lightRevealScrim.setRevealAmount(1.0f);
                }
                boolean z = LsRune.AOD_FULLSCREEN;
                final SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper2 = UnlockedScreenOffAnimationController.this.helper;
                Rune.runIf(new Runnable() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$lightRevealAnimator$1$2$onAnimationCancel$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper3 = SecUnlockedScreenOffAnimationHelper.this;
                        SecUnlockedScreenOffAnimationHelper.logD("onAnimationCancel deviceInteractive=" + secUnlockedScreenOffAnimationHelper3.deviceInteractive);
                        secUnlockedScreenOffAnimationHelper3.onAmountChanged(secUnlockedScreenOffAnimationHelper3.deviceInteractive ? 1.0f : 0.0f);
                    }
                }, z);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = UnlockedScreenOffAnimationController.this;
                unlockedScreenOffAnimationController.lightRevealAnimationPlaying = false;
                unlockedScreenOffAnimationController.interactionJankMonitor.end(40);
                boolean z = LsRune.AOD_FULLSCREEN;
                final SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper2 = UnlockedScreenOffAnimationController.this.helper;
                Consumer consumer = new Consumer() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$lightRevealAnimator$1$2$onAnimationEnd$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        boolean booleanValue = ((Boolean) obj).booleanValue();
                        SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper3 = SecUnlockedScreenOffAnimationHelper.this;
                        secUnlockedScreenOffAnimationHelper3.getClass();
                        SecUnlockedScreenOffAnimationHelper.logD("onAnimationEnd aodUiAnimationPlaying=" + booleanValue);
                        if (LsRune.AOD_TSP_CONTROL && secUnlockedScreenOffAnimationHelper3.aodAmbientWallpaperHelper.isAODFullScreenMode()) {
                            secUnlockedScreenOffAnimationHelper3.aodTouchModeManager.setTouchMode(0);
                        }
                        PluginAODManager pluginAODManager = (PluginAODManager) secUnlockedScreenOffAnimationHelper3.pluginAODManagerLazy.get();
                        Log.d("PluginAODManager", "onUnlockedScreenOffAnimationEnd: mAODPlugin=" + pluginAODManager.mAODPlugin);
                        PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
                        if (pluginAOD != null) {
                            pluginAOD.onUnlockedScreenOffAnimationEnd();
                        }
                        SecUnlockedScreenOffAnimationHelper.logD("updatePendingLock");
                        ((KeyguardViewMediator) secUnlockedScreenOffAnimationHelper3.keyguardViewMediatorLazy.get()).maybeHandlePendingLock();
                        CentralSurfaces centralSurfaces = secUnlockedScreenOffAnimationHelper3.centralSurfaces;
                        if (centralSurfaces == null) {
                            centralSurfaces = null;
                        }
                        ((CentralSurfacesImpl) centralSurfaces).updateIsKeyguard(false);
                        Function0 function0 = secUnlockedScreenOffAnimationHelper3.clearDecidedToAnimateGoingToSleep;
                        (function0 != null ? function0 : null).invoke();
                    }
                };
                boolean z2 = UnlockedScreenOffAnimationController.this.aodUiAnimationPlaying;
                if (z) {
                    consumer.accept(Boolean.valueOf(z2));
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = UnlockedScreenOffAnimationController.this;
                unlockedScreenOffAnimationController.interactionJankMonitor.begin(((NotificationShadeWindowControllerImpl) ((NotificationShadeWindowController) unlockedScreenOffAnimationController.notifShadeWindowControllerLazy.get())).mWindowRootView, 40);
                boolean z = LsRune.AOD_FULLSCREEN;
                final SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper2 = UnlockedScreenOffAnimationController.this.helper;
                Rune.runIf(new Runnable() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$lightRevealAnimator$1$2$onAnimationStart$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper3 = SecUnlockedScreenOffAnimationHelper.this;
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
        this.lightRevealAnimator = ofFloat;
        this.startLightRevealCallback = new UnlockedScreenOffAnimationController$special$$inlined$namedRunnable$1("startLightReveal", this);
        this.animatorDurationScaleObserver = new ContentObserver() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$animatorDurationScaleObserver$1
            {
                super(null);
            }

            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = UnlockedScreenOffAnimationController.this;
                unlockedScreenOffAnimationController.animatorDurationScale = WindowManager.fixScale(unlockedScreenOffAnimationController.globalSettings.getFloat(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE, 1.0f));
            }
        };
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final void animateInKeyguard(View view, final KeyguardVisibilityHelper$$ExternalSyntheticLambda0 keyguardVisibilityHelper$$ExternalSyntheticLambda0) {
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
        animationProperties2.mAnimationEndAction = new Consumer() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$animateInKeyguard$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = UnlockedScreenOffAnimationController.this;
                unlockedScreenOffAnimationController.aodUiAnimationPlaying = false;
                ((KeyguardViewMediator) unlockedScreenOffAnimationController.keyguardViewMediatorLazy.get()).maybeHandlePendingLock();
                CentralSurfaces centralSurfaces = UnlockedScreenOffAnimationController.this.centralSurfaces;
                if (centralSurfaces == null) {
                    centralSurfaces = null;
                }
                ((CentralSurfacesImpl) centralSurfaces).updateIsKeyguard(false);
                keyguardVisibilityHelper$$ExternalSyntheticLambda0.run();
                UnlockedScreenOffAnimationController unlockedScreenOffAnimationController2 = UnlockedScreenOffAnimationController.this;
                unlockedScreenOffAnimationController2.decidedToAnimateGoingToSleep = null;
                unlockedScreenOffAnimationController2.interactionJankMonitor.end(41);
            }
        };
        animationProperties2.mAnimationCancelAction = new Consumer() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$animateInKeyguard$2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = UnlockedScreenOffAnimationController.this;
                unlockedScreenOffAnimationController.aodUiAnimationPlaying = false;
                unlockedScreenOffAnimationController.decidedToAnimateGoingToSleep = null;
                unlockedScreenOffAnimationController.interactionJankMonitor.cancel(41);
            }
        };
        animationProperties2.setCustomInterpolator(View.ALPHA, Interpolators.FAST_OUT_SLOW_IN);
        PropertyAnimator.setProperty(view, anonymousClass72, 1.0f, animationProperties2, true);
        WindowRootView windowRootView = ((NotificationShadeWindowControllerImpl) ((NotificationShadeWindowController) this.notifShadeWindowControllerLazy.get())).mWindowRootView;
        if (windowRootView == null) {
            throw new IllegalStateException("Required value was null.".toString());
        }
        this.interactionJankMonitor.begin(InteractionJankMonitor.Configuration.Builder.withView(41, windowRootView).setTag(this.statusBarStateControllerImpl.getClockId()));
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final void initialize(CentralSurfaces centralSurfaces, ShadeViewController shadeViewController, LightRevealScrim lightRevealScrim) {
        if (LsRune.AOD_FULLSCREEN) {
            this.helper.init(centralSurfaces, new Function0() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$initialize$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return Boolean.valueOf(Intrinsics.areEqual(UnlockedScreenOffAnimationController.this.decidedToAnimateGoingToSleep, Boolean.FALSE));
                }
            }, new Function0() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$initialize$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    UnlockedScreenOffAnimationController.this.decidedToAnimateGoingToSleep = null;
                    return Unit.INSTANCE;
                }
            });
            this.initialized = true;
            this.lightRevealScrim = lightRevealScrim;
            this.centralSurfaces = centralSurfaces;
            GlobalSettings globalSettings = this.globalSettings;
            this.animatorDurationScale = WindowManager.fixScale(globalSettings.getFloat(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE, 1.0f));
            globalSettings.registerContentObserverSync(Settings.Global.getUriFor(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE), false, (ContentObserver) this.animatorDurationScaleObserver);
            this.wakefulnessLifecycle.addObserver(this);
        }
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean isAnimationPlaying() {
        return this.lightRevealAnimationPlaying || this.aodUiAnimationPlaying;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean isKeyguardShowDelayed() {
        return isAnimationPlaying();
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onFinishedWakingUp() {
        this.aodUiAnimationPlaying = false;
        if (((DozeParameters) this.dozeParameters.get()).canControlUnlockedScreenOff()) {
            CentralSurfaces centralSurfaces = this.centralSurfaces;
            if (centralSurfaces == null) {
                centralSurfaces = null;
            }
            ((CentralSurfacesImpl) centralSurfaces).updateIsKeyguard(true);
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
        return shouldPlayUnlockedScreenOffAnimation() && isAnimationPlaying();
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
        if ((this.centralSurfaces == null || ((PanelExpansionInteractor) this.panelExpansionInteractorLazy.get()).isPanelExpanded()) && !isAnimationPlaying()) {
            return false;
        }
        if (DeviceState.shouldEnableKeyguardScreenRotation(((KeyguardStateControllerImpl) this.keyguardStateController).mContext)) {
            return true;
        }
        Display display = this.context.getDisplay();
        return display != null && display.getRotation() == 0;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldShowAodIconsWhenShade() {
        return isAnimationPlaying();
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean startAnimation() {
        if (!shouldPlayUnlockedScreenOffAnimation()) {
            this.decidedToAnimateGoingToSleep = Boolean.FALSE;
            return false;
        }
        boolean z = LsRune.AOD_FULLSCREEN;
        final SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = this.helper;
        Rune.runIf(new Runnable() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$startAnimation$1
            @Override // java.lang.Runnable
            public final void run() {
                SecUnlockedScreenOffAnimationHelper.this.onPrepare();
            }
        }, z);
        this.decidedToAnimateGoingToSleep = Boolean.TRUE;
        this.shouldAnimateInKeyguard = !z;
        this.lightRevealAnimationPlaying = z;
        DejankUtils.postAfterTraversal(this.startLightRevealCallback);
        this.handler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController$startAnimation$2
            @Override // java.lang.Runnable
            public final void run() {
                if (UnlockedScreenOffAnimationController.this.powerManager.isInteractive(0)) {
                    return;
                }
                boolean z2 = LsRune.AOD_FULLSCREEN;
                if (z2 || UnlockedScreenOffAnimationController.this.shouldAnimateInKeyguard) {
                    Flags.migrateClocksToBlueprint();
                    UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = UnlockedScreenOffAnimationController.this;
                    unlockedScreenOffAnimationController.aodUiAnimationPlaying = !z2;
                    ((ShadeLockscreenInteractor) unlockedScreenOffAnimationController.shadeLockscreenInteractorLazy.get()).showAodUi();
                }
            }
        }, (long) ((z ? 300L : 600L) * this.animatorDurationScale));
        SecPanelBackgroundController secPanelBackgroundController = ((SecQpBlurController) this.lazyBlurController.get()).mBackgroundController;
        if (secPanelBackgroundController == null) {
            return true;
        }
        secPanelBackgroundController.mView.setAlpha(0.0f * secPanelBackgroundController.mMaxAlpha);
        return true;
    }

    public /* synthetic */ UnlockedScreenOffAnimationController(SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper, Context context, WakefulnessLifecycle wakefulnessLifecycle, StatusBarStateControllerImpl statusBarStateControllerImpl, Lazy lazy, KeyguardStateController keyguardStateController, Lazy lazy2, GlobalSettings globalSettings, Lazy lazy3, InteractionJankMonitor interactionJankMonitor, PowerManager powerManager, Lazy lazy4, Lazy lazy5, Handler handler, Lazy lazy6, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(secUnlockedScreenOffAnimationHelper, context, wakefulnessLifecycle, statusBarStateControllerImpl, lazy, keyguardStateController, lazy2, globalSettings, lazy3, interactionJankMonitor, powerManager, lazy4, lazy5, (i & 8192) != 0 ? new Handler() : handler, lazy6);
    }
}
