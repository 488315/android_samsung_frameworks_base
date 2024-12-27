package com.android.systemui.unfold;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.Flags;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.domain.interactor.ToAodFoldTransitionInteractor;
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
import java.util.Iterator;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    public ShadeViewController shadeViewController;
    public boolean shouldPlayAnimation;
    public final WakefulnessLifecycle wakefulnessLifecycle;
    public boolean isFoldHandled = true;
    public final ArrayList statusListeners = new ArrayList();
    public final FoldToAodLatencyTracker foldToAodLatencyTracker = new FoldToAodLatencyTracker();
    public final FoldAodAnimationController$startAnimationRunnable$1 startAnimationRunnable = new Runnable() { // from class: com.android.systemui.unfold.FoldAodAnimationController$startAnimationRunnable$1
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.unfold.FoldAodAnimationController$startAnimationRunnable$1$1] */
        /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.unfold.FoldAodAnimationController$startAnimationRunnable$1$2] */
        /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.unfold.FoldAodAnimationController$startAnimationRunnable$1$3] */
        @Override // java.lang.Runnable
        public final void run() {
            ShadeFoldAnimator shadeFoldAnimator$1 = FoldAodAnimationController.this.getShadeFoldAnimator$1();
            final FoldAodAnimationController foldAodAnimationController = FoldAodAnimationController.this;
            ?? r1 = new Runnable() { // from class: com.android.systemui.unfold.FoldAodAnimationController$startAnimationRunnable$1.1
                @Override // java.lang.Runnable
                public final void run() {
                    FoldAodAnimationController.this.latencyTracker.onActionEnd(18);
                }
            };
            final FoldAodAnimationController foldAodAnimationController2 = FoldAodAnimationController.this;
            ?? r2 = new Runnable() { // from class: com.android.systemui.unfold.FoldAodAnimationController$startAnimationRunnable$1.2
                @Override // java.lang.Runnable
                public final void run() {
                    FoldAodAnimationController.this.setAnimationState(false);
                }
            };
            final FoldAodAnimationController foldAodAnimationController3 = FoldAodAnimationController.this;
            shadeFoldAnimator$1.startFoldToAodAnimation(r1, r2, new Runnable() { // from class: com.android.systemui.unfold.FoldAodAnimationController$startAnimationRunnable$1.3
                @Override // java.lang.Runnable
                public final void run() {
                    FoldAodAnimationController.this.setAnimationState(false);
                }
            });
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface FoldAodAnimationStatus {
        void onFoldToAodAnimationChanged();
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public final ShadeFoldAnimator getShadeFoldAnimator$1() {
        Flags.migrateClocksToBlueprint();
        ShadeViewController shadeViewController = this.shadeViewController;
        if (shadeViewController == null) {
            shadeViewController = null;
        }
        return shadeViewController.getShadeFoldAnimator();
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final void initialize(CentralSurfaces centralSurfaces, ShadeViewController shadeViewController, LightRevealScrim lightRevealScrim) {
        this.shadeViewController = shadeViewController;
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
            getShadeFoldAnimator$1().cancelFoldToAodAnimation();
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
        Iterator it = this.statusListeners.iterator();
        while (it.hasNext()) {
            ((FoldAodAnimationStatus) it.next()).onFoldToAodAnimationChanged();
        }
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldAnimateClockChange() {
        return !this.isAnimationPlaying;
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

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldShowAodIconsWhenShade() {
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
        getShadeFoldAnimator$1().prepareFoldToAodAnimation();
        return true;
    }
}
