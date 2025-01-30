package com.android.systemui.unfold;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.view.View;
import android.view.ViewPropertyAnimator;
import com.android.app.animation.Interpolators;
import com.android.internal.util.LatencyTracker;
import com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda0;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.shade.NotificationPanelView;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda21;
import com.android.systemui.statusbar.LightRevealScrim;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.ScreenOffAnimation;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.GlobalSettingsImpl;
import com.sec.ims.configuration.DATA;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FoldAodAnimationController implements CallbackController, ScreenOffAnimation, WakefulnessLifecycle.Observer {
    public boolean alwaysOnEnabled;
    public Runnable cancelAnimation;
    public CentralSurfaces centralSurfaces;
    public final Context context;
    public final DeviceStateManager deviceStateManager;
    public final GlobalSettings globalSettings;
    public boolean isAnimationPlaying;
    public boolean isDozing;
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
            NotificationPanelViewController.ShadeFoldAnimatorImpl shadeFoldAnimator = FoldAodAnimationController.this.getShadeFoldAnimator();
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
            ViewPropertyAnimator animate = NotificationPanelViewController.this.mView.animate();
            animate.cancel();
            animate.translationX(0.0f).alpha(1.0f).setDuration(600L).setInterpolator(Interpolators.EMPHASIZED_DECELERATE).setListener(new AnimatorListenerAdapter(shadeFoldAnimator, runnable, runnable3, runnable2, animate) { // from class: com.android.systemui.shade.NotificationPanelViewController.ShadeFoldAnimatorImpl.1
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
            }).setUpdateListener(new NotificationPanelViewController$$ExternalSyntheticLambda21(shadeFoldAnimator, 2)).start();
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface FoldAodAnimationStatus {
        void onFoldToAodAnimationChanged();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FoldToAodLatencyTracker {
        public FoldToAodLatencyTracker() {
        }
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.unfold.FoldAodAnimationController$startAnimationRunnable$1] */
    public FoldAodAnimationController(DelayableExecutor delayableExecutor, Context context, DeviceStateManager deviceStateManager, WakefulnessLifecycle wakefulnessLifecycle, GlobalSettings globalSettings, LatencyTracker latencyTracker, Lazy lazy) {
        this.mainExecutor = delayableExecutor;
        this.context = context;
        this.deviceStateManager = deviceStateManager;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.globalSettings = globalSettings;
        this.latencyTracker = latencyTracker;
        this.keyguardInteractor = lazy;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        this.statusListeners.add((FoldAodAnimationStatus) obj);
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final void animateInKeyguard(View view, KeyguardVisibilityHelper$$ExternalSyntheticLambda0 keyguardVisibilityHelper$$ExternalSyntheticLambda0) {
        keyguardVisibilityHelper$$ExternalSyntheticLambda0.run();
    }

    public final NotificationPanelViewController.ShadeFoldAnimatorImpl getShadeFoldAnimator() {
        CentralSurfaces centralSurfaces = this.centralSurfaces;
        if (centralSurfaces == null) {
            centralSurfaces = null;
        }
        return ((NotificationPanelViewController) ((CentralSurfacesImpl) centralSurfaces).getShadeViewController()).mShadeFoldAnimator;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final void initialize(CentralSurfaces centralSurfaces, LightRevealScrim lightRevealScrim) {
        this.centralSurfaces = centralSurfaces;
        this.deviceStateManager.registerCallback(this.mainExecutor, new FoldListener(this));
        this.wakefulnessLifecycle.addObserver(this);
        RepeatWhenAttachedKt.repeatWhenAttached(NotificationPanelViewController.this.mView, EmptyCoroutineContext.INSTANCE, new FoldAodAnimationController$initialize$1(this, null));
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
    public final boolean isKeyguardShowDelayed() {
        return false;
    }

    /* renamed from: listenForDozing$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final Object m222xde5cc31b(CoroutineScope coroutineScope, Continuation<? super Job> continuation) {
        return BuildersKt.launch$default(coroutineScope, null, null, new FoldAodAnimationController$listenForDozing$2(this, null), 3);
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
            NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
            notificationPanelViewController.mView.animate().cancel();
            NotificationPanelView notificationPanelView = notificationPanelViewController.mView;
            notificationPanelView.setAlpha(1.0f);
            notificationPanelView.setTranslationX(0.0f);
        }
        setAnimationState(false);
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean overrideNotificationsDozeAmount() {
        return false;
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
    public final boolean shouldAnimateAodIcons() {
        return !this.shouldPlayAnimation;
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
    public final boolean shouldAnimateInKeyguard() {
        return false;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldDelayDisplayDozeTransition() {
        return this.shouldPlayAnimation;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldDelayKeyguardShow() {
        return false;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean shouldHideScrimOnWakeUp() {
        return false;
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
        if (this.alwaysOnEnabled && this.wakefulnessLifecycle.mLastSleepReason == 13) {
            GlobalSettings globalSettings = this.globalSettings;
            if (!Intrinsics.areEqual(((GlobalSettingsImpl) globalSettings).getStringForUser(globalSettings.getUserId(), "animator_duration_scale"), DATA.DM_FIELD_INDEX.PCSCF_DOMAIN)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.phone.ScreenOffAnimation
    public final boolean startAnimation() {
        if (!shouldStartAnimation()) {
            setAnimationState(false);
            return false;
        }
        setAnimationState(true);
        getShadeFoldAnimator().prepareFoldToAodAnimation();
        return true;
    }
}
