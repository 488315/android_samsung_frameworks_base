package com.android.systemui.unfold;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.view.ViewPropertyAnimator;
import com.android.app.animation.Interpolators;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.R;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.domain.interactor.ToAodFoldTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.ToAodFoldTransitionInteractor$foldAnimator$1;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.shade.NotificationPanelView;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.ShadeFoldAnimator;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.LightRevealScrim;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.ScreenOffAnimation;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FoldAodAnimationController implements CallbackController, ScreenOffAnimation, WakefulnessLifecycle.Observer {
    public boolean alwaysOnEnabled;
    public Runnable cancelAnimation;
    public final Context context;
    public final DeviceStateManager deviceStateManager;
    public final Lazy foldTransitionInteractor;
    public final GlobalSettings globalSettings;
    public boolean isAnimationPlaying;
    public boolean isFolded;
    public boolean isScrimOpaque;
    public final Lazy keyguardInteractor;
    public final LatencyTracker latencyTracker;
    public final DelayableExecutor mainExecutor;
    public Runnable pendingScrimReadyCallback;
    public boolean shouldPlayAnimation;
    public final WakefulnessLifecycle wakefulnessLifecycle;
    public boolean isFoldHandled = true;
    public final ArrayList statusListeners = new ArrayList();
    public final FoldToAodLatencyTracker foldToAodLatencyTracker = new FoldToAodLatencyTracker();
    public final FoldAodAnimationController$startAnimationRunnable$1 startAnimationRunnable = new Runnable() { // from class: com.android.systemui.unfold.FoldAodAnimationController$startAnimationRunnable$1
        @Override // java.lang.Runnable
        public final void run() {
            ToAodFoldTransitionInteractor$foldAnimator$1 toAodFoldTransitionInteractor$foldAnimator$1 = ((ToAodFoldTransitionInteractor) FoldAodAnimationController.this.foldTransitionInteractor.get()).foldAnimator;
            final FoldAodAnimationController foldAodAnimationController = FoldAodAnimationController.this;
            Runnable runnable = new Runnable() { // from class: com.android.systemui.unfold.FoldAodAnimationController$startAnimationRunnable$1.1
                @Override // java.lang.Runnable
                public final void run() {
                    FoldAodAnimationController.this.latencyTracker.onActionEnd(18);
                }
            };
            final FoldAodAnimationController foldAodAnimationController2 = FoldAodAnimationController.this;
            Runnable runnable2 = new Runnable() { // from class: com.android.systemui.unfold.FoldAodAnimationController$startAnimationRunnable$1.2
                @Override // java.lang.Runnable
                public final void run() {
                    FoldAodAnimationController.this.setAnimationState(false);
                }
            };
            final FoldAodAnimationController foldAodAnimationController3 = FoldAodAnimationController.this;
            Runnable runnable3 = new Runnable() { // from class: com.android.systemui.unfold.FoldAodAnimationController$startAnimationRunnable$1.3
                @Override // java.lang.Runnable
                public final void run() {
                    FoldAodAnimationController.this.setAnimationState(false);
                }
            };
            final ToAodFoldTransitionInteractor toAodFoldTransitionInteractor = toAodFoldTransitionInteractor$foldAnimator$1.this$0;
            NotificationPanelViewController.ShadeFoldAnimatorImpl shadeFoldAnimatorImpl = toAodFoldTransitionInteractor.parentAnimator;
            if (shadeFoldAnimatorImpl != null) {
                ViewPropertyAnimator animate = NotificationPanelViewController.this.mView.animate();
                animate.cancel();
                animate.translationX(0.0f).alpha(1.0f).setDuration(600L).setInterpolator(Interpolators.EMPHASIZED_DECELERATE).setListener(new AnimatorListenerAdapter(shadeFoldAnimatorImpl, runnable, runnable3, runnable2, animate) { // from class: com.android.systemui.shade.NotificationPanelViewController.ShadeFoldAnimatorImpl.1
                    public final /* synthetic */ Runnable val$cancelAction;
                    public final /* synthetic */ Runnable val$endAction;
                    public final /* synthetic */ Runnable val$startAction;
                    public final /* synthetic */ ViewPropertyAnimator val$viewAnimator;

                    {
                        this.val$startAction = runnable;
                        this.val$cancelAction = runnable3;
                        this.val$endAction = runnable2;
                        this.val$viewAnimator = animate;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        this.val$cancelAction.run();
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        this.val$endAction.run();
                        this.val$viewAnimator.setListener(null);
                        this.val$viewAnimator.setUpdateListener(null);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        this.val$startAction.run();
                    }
                }).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.domain.interactor.ToAodFoldTransitionInteractor$foldAnimator$1$startFoldToAodAnimation$1$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        KeyguardClockInteractor keyguardClockInteractor = ToAodFoldTransitionInteractor.this.keyguardClockInteractor;
                        float animatedFraction = valueAnimator.getAnimatedFraction();
                        ClockController clockController = keyguardClockInteractor.clock$receiver.clock;
                        if (clockController != null) {
                            clockController.getSmallClock().getAnimations().fold(animatedFraction);
                            clockController.getLargeClock().getAnimations().fold(animatedFraction);
                        }
                    }
                }).start();
            }
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface FoldAodAnimationStatus {
        void onFoldToAodAnimationChanged();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FoldListener extends DeviceStateManager.FoldStateListener {
        public FoldListener(final FoldAodAnimationController foldAodAnimationController) {
            super(foldAodAnimationController.context, new Consumer() { // from class: com.android.systemui.unfold.FoldAodAnimationController.FoldListener.1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Boolean bool = (Boolean) obj;
                    if (!bool.booleanValue()) {
                        FoldAodAnimationController.this.isFoldHandled = false;
                    }
                    FoldAodAnimationController.this.isFolded = bool.booleanValue();
                    if (bool.booleanValue()) {
                        FoldAodAnimationController foldAodAnimationController2 = FoldAodAnimationController.this;
                        if (foldAodAnimationController2.shouldStartAnimation()) {
                            foldAodAnimationController2.latencyTracker.onActionStart(18);
                        }
                    }
                }
            });
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FoldToAodLatencyTracker {
        public FoldToAodLatencyTracker() {
        }
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.unfold.FoldAodAnimationController$startAnimationRunnable$1] */
    public FoldAodAnimationController(DelayableExecutor delayableExecutor, Context context, DeviceStateManager deviceStateManager, WakefulnessLifecycle wakefulnessLifecycle, GlobalSettings globalSettings, LatencyTracker latencyTracker, Lazy lazy, Lazy lazy2) {
        this.mainExecutor = delayableExecutor;
        this.context = context;
        this.deviceStateManager = deviceStateManager;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.globalSettings = globalSettings;
        this.latencyTracker = latencyTracker;
        this.keyguardInteractor = lazy;
        this.foldTransitionInteractor = lazy2;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        this.statusListeners.add((FoldAodAnimationStatus) obj);
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final void initialize(CentralSurfaces centralSurfaces, ShadeViewController shadeViewController, LightRevealScrim lightRevealScrim) {
        ToAodFoldTransitionInteractor toAodFoldTransitionInteractor = (ToAodFoldTransitionInteractor) this.foldTransitionInteractor.get();
        ShadeFoldAnimator shadeFoldAnimator = shadeViewController.getShadeFoldAnimator();
        toAodFoldTransitionInteractor.getClass();
        toAodFoldTransitionInteractor.parentAnimator = shadeFoldAnimator instanceof NotificationPanelViewController.ShadeFoldAnimatorImpl ? (NotificationPanelViewController.ShadeFoldAnimatorImpl) shadeFoldAnimator : null;
        this.deviceStateManager.registerCallback(this.mainExecutor, new FoldListener(this));
        this.wakefulnessLifecycle.addObserver(this);
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean isAnimationPlaying() {
        return this.isAnimationPlaying;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean isKeyguardHideDelayed() {
        return this.isAnimationPlaying;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final void onAlwaysOnChanged(boolean z) {
        this.alwaysOnEnabled = z;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final void onScrimOpaqueChanged(boolean z) {
        this.isScrimOpaque = z;
        if (z) {
            Runnable runnable = this.pendingScrimReadyCallback;
            if (runnable != null) {
                runnable.run();
            }
            this.pendingScrimReadyCallback = null;
        }
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onStartedWakingUp() {
        if (this.isAnimationPlaying) {
            FoldAodAnimationController.this.latencyTracker.onActionCancel(18);
            Runnable runnable = this.cancelAnimation;
            if (runnable != null) {
                runnable.run();
            }
            NotificationPanelViewController.ShadeFoldAnimatorImpl shadeFoldAnimatorImpl = ((ToAodFoldTransitionInteractor) this.foldTransitionInteractor.get()).foldAnimator.this$0.parentAnimator;
            if (shadeFoldAnimatorImpl != null) {
                NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
                notificationPanelViewController.cancelAnimation();
                notificationPanelViewController.resetAlpha();
                notificationPanelViewController.resetTranslation();
            }
        }
        setAnimationState(false);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.statusListeners.remove((FoldAodAnimationStatus) obj);
    }

    public final void setAnimationState(boolean z) {
        this.shouldPlayAnimation = z;
        this.isAnimationPlaying = z;
        ArrayList arrayList = this.statusListeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((FoldAodAnimationStatus) obj).onFoldToAodAnimationChanged();
        }
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldAnimateDozingChange() {
        return !this.shouldPlayAnimation;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldDelayDisplayDozeTransition() {
        return this.shouldPlayAnimation;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldPlayAnimation() {
        return this.shouldPlayAnimation;
    }

    public final boolean shouldStartAnimation() {
        return this.alwaysOnEnabled && this.wakefulnessLifecycle.mLastSleepReason == 13 && !Intrinsics.areEqual(this.globalSettings.getString(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE), "0");
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean startAnimation() {
        if (!shouldStartAnimation()) {
            setAnimationState(false);
            return false;
        }
        setAnimationState(true);
        NotificationPanelViewController.ShadeFoldAnimatorImpl shadeFoldAnimatorImpl = ((ToAodFoldTransitionInteractor) this.foldTransitionInteractor.get()).foldAnimator.this$0.parentAnimator;
        if (shadeFoldAnimatorImpl != null) {
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.setDozing(true, false);
            NotificationPanelView notificationPanelView = notificationPanelViewController.mView;
            notificationPanelView.setTranslationX(-notificationPanelView.getResources().getDimensionPixelSize(R.dimen.below_clock_padding_start));
            notificationPanelView.setAlpha(0.0f);
        }
        return true;
    }
}
