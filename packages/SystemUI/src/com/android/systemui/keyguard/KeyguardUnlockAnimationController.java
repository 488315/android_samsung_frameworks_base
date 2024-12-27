package com.android.systemui.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.WallpaperManager;
import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.PowerManager;
import android.os.RemoteException;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.core.math.MathUtils;
import com.android.app.animation.Interpolators;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.Flags;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController$Stub$Proxy;
import com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController;
import com.android.systemui.shared.system.smartspace.SmartspaceState;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class KeyguardUnlockAnimationController extends ISysuiUnlockAnimationController.Stub implements KeyguardStateController.Callback, KeyguardSecUnlockAnimationController {
    public static final Companion Companion = null;
    public final Lazy biometricUnlockControllerLazy;
    public boolean dismissAmountThresholdsReached;
    public final FeatureFlags featureFlags;
    public final Handler handler;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardViewController keyguardViewController;
    public final Lazy keyguardViewMediator;
    public ILauncherUnlockAnimationController$Stub$Proxy launcherUnlockController;
    public View lockscreenSmartspace;
    public final NotificationShadeWindowController notificationShadeWindowController;
    public boolean playingCannedUnlockAnimation;
    public final PowerManager powerManager;
    public final Resources resources;
    public final float roundedCornerRadius;
    public final SysuiStatusBarStateController statusBarStateController;
    public final ValueAnimator surfaceBehindAlphaAnimator;
    public final ValueAnimator surfaceBehindEntryAnimator;
    public final Matrix surfaceBehindMatrix;
    public long surfaceBehindRemoteAnimationStartTime;
    public RemoteAnimationTarget[] surfaceBehindRemoteAnimationTargets;
    public SyncRtSurfaceTransactionApplier surfaceTransactionApplier;
    public final float[] tmpFloat;
    public final ValueAnimator wallpaperCannedUnlockAnimator;
    public RemoteAnimationTarget[] wallpaperTargets;
    public final ArrayList listeners = new ArrayList();
    public float surfaceBehindAlpha = 1.0f;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardUnlockAnimationController(WindowManager windowManager, Resources resources, KeyguardStateController keyguardStateController, Lazy lazy, KeyguardViewController keyguardViewController, FeatureFlags featureFlags, Lazy lazy2, SysuiStatusBarStateController sysuiStatusBarStateController, NotificationShadeWindowController notificationShadeWindowController, PowerManager powerManager, WallpaperManager wallpaperManager) {
        this.keyguardStateController = keyguardStateController;
        this.keyguardViewMediator = lazy;
        this.keyguardViewController = keyguardViewController;
        this.featureFlags = featureFlags;
        this.biometricUnlockControllerLazy = lazy2;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.powerManager = powerManager;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.surfaceBehindAlphaAnimator = ofFloat;
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.wallpaperCannedUnlockAnimator = ofFloat2;
        this.surfaceBehindMatrix = new Matrix();
        ValueAnimator ofFloat3 = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.surfaceBehindEntryAnimator = ofFloat3;
        this.handler = new Handler();
        this.tmpFloat = new float[9];
        Flags.fastUnlockTransition();
        ofFloat.setDuration(175L);
        ofFloat.setInterpolator(Interpolators.LINEAR);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardUnlockAnimationController.this.surfaceBehindAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                KeyguardUnlockAnimationController.this.updateSurfaceBehindAppearAmount();
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                float f = KeyguardUnlockAnimationController.this.surfaceBehindAlpha;
                if (f != 0.0f) {
                    android.util.Log.d("KeyguardUnlock", "skip finishSurfaceBehindRemoteAnimation surfaceBehindAlpha=" + f);
                } else {
                    android.util.Log.d("KeyguardUnlock", "surfaceBehindAlphaAnimator#onAnimationEnd");
                    KeyguardUnlockAnimationController keyguardUnlockAnimationController = KeyguardUnlockAnimationController.this;
                    keyguardUnlockAnimationController.surfaceBehindRemoteAnimationTargets = null;
                    keyguardUnlockAnimationController.wallpaperTargets = null;
                    ((KeyguardViewMediator) keyguardUnlockAnimationController.keyguardViewMediator.get()).finishSurfaceBehindRemoteAnimation(false);
                }
            }
        });
        Flags.fastUnlockTransition();
        ofFloat2.setDuration(633L);
        Flags.fastUnlockTransition();
        ofFloat2.setInterpolator(Interpolators.ALPHA_OUT);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$2$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardUnlockAnimationController.this.setWallpaperAppearAmount(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$2$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                android.util.Log.d("KeyguardUnlock", "wallpaperCannedUnlockAnimator#onAnimationEnd");
                ((KeyguardViewMediator) KeyguardUnlockAnimationController.this.keyguardViewMediator.get()).exitKeyguardAndFinishSurfaceBehindRemoteAnimation();
            }
        });
        Flags.fastUnlockTransition();
        ofFloat3.setDuration(200L);
        Flags.fastUnlockTransition();
        ofFloat3.setStartDelay(75L);
        ofFloat3.setInterpolator(Interpolators.TOUCH_RESPONSE);
        ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$3$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardUnlockAnimationController.this.surfaceBehindAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                KeyguardUnlockAnimationController.this.setSurfaceBehindAppearAmount(((Float) valueAnimator.getAnimatedValue()).floatValue(), true);
            }
        });
        ofFloat3.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$3$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                android.util.Log.d("KeyguardUnlock", "surfaceBehindEntryAnimator#onAnimationEnd");
                KeyguardUnlockAnimationController keyguardUnlockAnimationController = KeyguardUnlockAnimationController.this;
                keyguardUnlockAnimationController.playingCannedUnlockAnimation = false;
                ((KeyguardViewMediator) keyguardUnlockAnimationController.keyguardViewMediator.get()).exitKeyguardAndFinishSurfaceBehindRemoteAnimation();
            }
        });
        ((KeyguardStateControllerImpl) keyguardStateController).addCallback(this);
        this.roundedCornerRadius = resources.getDimensionPixelSize(17105816);
    }

    public final void finishKeyguardExitRemoteAnimationIfReachThreshold() {
        if (((KeyguardStateControllerImpl) this.keyguardStateController).mShowing && !this.dismissAmountThresholdsReached && ((KeyguardViewMediator) this.keyguardViewMediator.get()).requestedShowSurfaceBehindKeyguard() && ((KeyguardViewMediator) this.keyguardViewMediator.get()).isAnimatingBetweenKeyguardAndSurfaceBehindOrWillBe()) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
            float f = keyguardStateControllerImpl.mDismissAmount;
            if (f >= 1.0f || (keyguardStateControllerImpl.mDismissingFromTouch && !keyguardStateControllerImpl.mFlingingToDismissKeyguardDuringSwipeGesture && f >= 0.3f)) {
                setSurfaceBehindAppearAmount(1.0f, true);
                this.dismissAmountThresholdsReached = true;
                ((KeyguardViewMediator) this.keyguardViewMediator.get()).exitKeyguardAndFinishSurfaceBehindRemoteAnimation();
            }
        }
    }

    public final void hideKeyguardViewAfterRemoteAnimation() {
        if (!((KeyguardStateControllerImpl) this.keyguardStateController).mShowing) {
            android.util.Log.i("KeyguardUnlock", "#hideKeyguardViewAfterRemoteAnimation called when keyguard view is not showing. Ignoring...");
        } else {
            Flags.keyguardWmStateRefactor();
            this.keyguardViewController.hide(this.surfaceBehindRemoteAnimationStartTime, 0L);
        }
    }

    public void notifyFinishedKeyguardExitAnimation(boolean z) {
        View view;
        this.handler.removeCallbacksAndMessages(null);
        View view2 = this.lockscreenSmartspace;
        if (view2 != null && view2.getVisibility() == 4 && (view = this.lockscreenSmartspace) != null) {
            view.setVisibility(0);
        }
        if (!z) {
            this.surfaceBehindAlpha = 1.0f;
            setSurfaceBehindAppearAmount(1.0f, true);
            try {
                ILauncherUnlockAnimationController$Stub$Proxy iLauncherUnlockAnimationController$Stub$Proxy = this.launcherUnlockController;
                if (iLauncherUnlockAnimationController$Stub$Proxy != null) {
                    iLauncherUnlockAnimationController$Stub$Proxy.setUnlockAmount(1.0f, false);
                }
            } catch (RemoteException e) {
                android.util.Log.e("KeyguardUnlock", "Remote exception in notifyFinishedKeyguardExitAnimation", e);
            }
        }
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((KeyguardUnlockAnimationListener) it.next()).onUnlockAnimationFinished();
        }
        this.surfaceBehindAlphaAnimator.cancel();
        this.surfaceBehindEntryAnimator.cancel();
        this.wallpaperCannedUnlockAnimator.cancel();
        this.surfaceBehindRemoteAnimationTargets = null;
        this.wallpaperTargets = null;
        this.playingCannedUnlockAnimation = false;
        this.dismissAmountThresholdsReached = false;
    }

    public void notifyStartSurfaceBehindRemoteAnimation(RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, long j, boolean z) {
        if (this.surfaceTransactionApplier == null) {
            this.surfaceTransactionApplier = new SyncRtSurfaceTransactionApplier(this.keyguardViewController.getViewRootImpl().getView());
        }
        this.surfaceBehindRemoteAnimationTargets = remoteAnimationTargetArr;
        this.wallpaperTargets = remoteAnimationTargetArr2;
        this.surfaceBehindRemoteAnimationStartTime = j;
        if (z) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
            if (keyguardStateControllerImpl.mFlingingToDismissKeyguard) {
                playCannedUnlockAnimation();
            } else {
                boolean z2 = keyguardStateControllerImpl.mDismissingFromTouch;
                android.util.Log.d("KeyguardUnlock", "fadeInSurfaceBehind");
                this.surfaceBehindAlphaAnimator.cancel();
                this.surfaceBehindAlphaAnimator.start();
            }
        } else {
            playCannedUnlockAnimation();
        }
        boolean z3 = ((BiometricUnlockController) this.biometricUnlockControllerLazy.get()).isWakeAndUnlock() && ((BiometricUnlockController) this.biometricUnlockControllerLazy.get()).mMode != 6;
        Flags.fastUnlockTransition();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            KeyguardUnlockAnimationListener keyguardUnlockAnimationListener = (KeyguardUnlockAnimationListener) it.next();
            boolean z4 = this.playingCannedUnlockAnimation;
            Flags.fastUnlockTransition();
            keyguardUnlockAnimationListener.onUnlockAnimationStarted(z4, z3);
        }
        if (this.playingCannedUnlockAnimation) {
            return;
        }
        finishKeyguardExitRemoteAnimationIfReachThreshold();
    }

    public void onKeyguardDismissAmountChanged() {
        if (!((KeyguardStateControllerImpl) this.keyguardStateController).mShowing || this.playingCannedUnlockAnimation) {
            return;
        }
        FeatureFlags featureFlags = this.featureFlags;
        com.android.systemui.flags.Flags.INSTANCE.getClass();
        if (((FeatureFlagsClassicRelease) featureFlags).isEnabled(com.android.systemui.flags.Flags.NEW_UNLOCK_SWIPE_ANIMATION) && !this.playingCannedUnlockAnimation && !this.dismissAmountThresholdsReached) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
            if (keyguardStateControllerImpl.mShowing) {
                float f = keyguardStateControllerImpl.mDismissAmount;
                if (f >= 0.15f && !((KeyguardViewMediator) this.keyguardViewMediator.get()).requestedShowSurfaceBehindKeyguard()) {
                    ((KeyguardViewMediator) this.keyguardViewMediator.get()).showSurfaceBehindKeyguard();
                } else if (f < 0.15f && ((KeyguardViewMediator) this.keyguardViewMediator.get()).requestedShowSurfaceBehindKeyguard()) {
                    ((KeyguardViewMediator) this.keyguardViewMediator.get()).hideSurfaceBehindKeyguard();
                    android.util.Log.d("KeyguardUnlock", "fadeOutSurfaceBehind");
                    this.surfaceBehindAlphaAnimator.cancel();
                    this.surfaceBehindAlphaAnimator.reverse();
                }
                finishKeyguardExitRemoteAnimationIfReachThreshold();
            }
        }
        if ((((KeyguardViewMediator) this.keyguardViewMediator.get()).requestedShowSurfaceBehindKeyguard() || ((KeyguardViewMediator) this.keyguardViewMediator.get()).isAnimatingBetweenKeyguardAndSurfaceBehindOrWillBe()) && !this.playingCannedUnlockAnimation) {
            updateSurfaceBehindAppearAmount();
        }
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public final void onKeyguardGoingAwayChanged() {
        if (((KeyguardStateControllerImpl) this.keyguardStateController).mKeyguardGoingAway && !((StatusBarStateControllerImpl) this.statusBarStateController).mLeaveOpenOnKeyguardHide) {
            Flags.keyguardWmStateRefactor();
        }
        boolean z = ((KeyguardStateControllerImpl) this.keyguardStateController).mKeyguardGoingAway;
    }

    public void playCannedUnlockAnimation() {
        android.util.Log.d("KeyguardUnlock", "playCannedUnlockAnimation");
        this.playingCannedUnlockAnimation = true;
        if (!((BiometricUnlockController) this.biometricUnlockControllerLazy.get()).isWakeAndUnlock()) {
            android.util.Log.d("KeyguardUnlock", "playCannedUnlockAnimation, surfaceBehindEntryAnimator#start");
            this.surfaceBehindEntryAnimator.start();
        } else {
            android.util.Log.d("KeyguardUnlock", "playCannedUnlockAnimation, isWakeAndUnlock");
            setSurfaceBehindAppearAmount(1.0f, true);
            ((KeyguardViewMediator) this.keyguardViewMediator.get()).exitKeyguardAndFinishSurfaceBehindRemoteAnimation();
        }
    }

    @Override // com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController
    public final void setLauncherUnlockController(String str, ILauncherUnlockAnimationController$Stub$Proxy iLauncherUnlockAnimationController$Stub$Proxy) {
        this.launcherUnlockController = iLauncherUnlockAnimationController$Stub$Proxy;
    }

    public void setSurfaceBehindAppearAmount(float f, boolean z) {
        float f2 = ((KeyguardStateControllerImpl) this.keyguardStateController).mSnappingKeyguardBackAfterSwipe ? f : !this.powerManager.isInteractive() ? 0.0f : this.surfaceBehindAlpha;
        RemoteAnimationTarget[] remoteAnimationTargetArr = this.surfaceBehindRemoteAnimationTargets;
        if (remoteAnimationTargetArr != null) {
            for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr) {
                Flags.keyguardWmStateRefactor();
                int height = remoteAnimationTarget.screenSpaceBounds.height();
                float clamp = (MathUtils.clamp(f, 0.0f, 1.0f) * 0.050000012f) + 0.95f;
                boolean z2 = ((KeyguardStateControllerImpl) this.keyguardStateController).mDismissingFromTouch;
                Matrix matrix = this.surfaceBehindMatrix;
                Rect rect = remoteAnimationTarget.screenSpaceBounds;
                float f3 = height;
                matrix.setTranslate(rect.left, DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(1.0f, f, 0.05f * f3, rect.top));
                this.surfaceBehindMatrix.postScale(clamp, clamp, this.keyguardViewController.getViewRootImpl().getWidth() / 2.0f, f3 * 0.66f);
                SurfaceControl surfaceControl = remoteAnimationTarget.leash;
                View view = this.keyguardViewController.getViewRootImpl().getView();
                if ((view == null || view.getVisibility() != 0) && surfaceControl != null && surfaceControl.isValid()) {
                    SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                    transaction.setMatrix(surfaceControl, this.surfaceBehindMatrix, this.tmpFloat);
                    transaction.setCornerRadius(surfaceControl, this.roundedCornerRadius);
                    transaction.setAlpha(surfaceControl, f2);
                    transaction.apply();
                } else {
                    SyncRtSurfaceTransactionApplier.SurfaceParams build = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget.leash).withMatrix(this.surfaceBehindMatrix).withCornerRadius(this.roundedCornerRadius).withAlpha(f2).build();
                    SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = this.surfaceTransactionApplier;
                    Intrinsics.checkNotNull(syncRtSurfaceTransactionApplier);
                    syncRtSurfaceTransactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{build});
                }
            }
        }
        if (z) {
            setWallpaperAppearAmount(f);
        }
    }

    public final void setWallpaperAppearAmount(float f) {
        RemoteAnimationTarget[] remoteAnimationTargetArr = this.wallpaperTargets;
        if (remoteAnimationTargetArr != null) {
            for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr) {
                SurfaceControl surfaceControl = remoteAnimationTarget.leash;
                View view = this.keyguardViewController.getViewRootImpl().getView();
                if ((view == null || view.getVisibility() != 0) && surfaceControl != null && surfaceControl.isValid()) {
                    SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                    transaction.setAlpha(surfaceControl, f);
                    transaction.apply();
                } else {
                    SyncRtSurfaceTransactionApplier.SurfaceParams build = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget.leash).withAlpha(f).build();
                    SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = this.surfaceTransactionApplier;
                    Intrinsics.checkNotNull(syncRtSurfaceTransactionApplier);
                    syncRtSurfaceTransactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{build});
                }
            }
        }
    }

    public final void unlockToLauncherWithInWindowAnimations() {
        View view;
        this.surfaceBehindAlpha = 1.0f;
        setSurfaceBehindAppearAmount(1.0f, false);
        try {
            ILauncherUnlockAnimationController$Stub$Proxy iLauncherUnlockAnimationController$Stub$Proxy = this.launcherUnlockController;
            if (iLauncherUnlockAnimationController$Stub$Proxy != null) {
                Flags.fastUnlockTransition();
                iLauncherUnlockAnimationController$Stub$Proxy.playUnlockAnimation(633L, true, 100L);
            }
        } catch (DeadObjectException unused) {
            android.util.Log.e("KeyguardUnlock", "launcherUnlockAnimationController was dead, but non-null. Catching exception as this should mean Launcher is in the process of being destroyed, but the IPC to System UI telling us hasn't arrived yet.");
        }
        View view2 = this.lockscreenSmartspace;
        if (view2 != null && view2.getVisibility() == 0 && (view = this.lockscreenSmartspace) != null) {
            view.setVisibility(4);
        }
        Handler handler = this.handler;
        Runnable runnable = new Runnable() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$unlockToLauncherWithInWindowAnimations$1
            @Override // java.lang.Runnable
            public final void run() {
                if (((KeyguardViewMediator) KeyguardUnlockAnimationController.this.keyguardViewMediator.get()).isShowingAndNotOccluded() && !((KeyguardStateControllerImpl) KeyguardUnlockAnimationController.this.keyguardStateController).mKeyguardGoingAway) {
                    android.util.Log.e("KeyguardUnlock", "Finish keyguard exit animation delayed Runnable ran, but we are showing and not going away.");
                    return;
                }
                KeyguardUnlockAnimationController keyguardUnlockAnimationController = KeyguardUnlockAnimationController.this;
                RemoteAnimationTarget[] remoteAnimationTargetArr = keyguardUnlockAnimationController.wallpaperTargets;
                if (remoteAnimationTargetArr != null) {
                    if (!(remoteAnimationTargetArr.length == 0)) {
                        android.util.Log.d("KeyguardUnlock", "fadeInWallpaper");
                        keyguardUnlockAnimationController.wallpaperCannedUnlockAnimator.cancel();
                        keyguardUnlockAnimationController.wallpaperCannedUnlockAnimator.start();
                        KeyguardUnlockAnimationController.this.hideKeyguardViewAfterRemoteAnimation();
                        return;
                    }
                }
                ((KeyguardViewMediator) keyguardUnlockAnimationController.keyguardViewMediator.get()).exitKeyguardAndFinishSurfaceBehindRemoteAnimation();
            }
        };
        Flags.fastUnlockTransition();
        handler.postDelayed(runnable, 100L);
    }

    public final void updateSurfaceBehindAppearAmount() {
        if (this.surfaceBehindRemoteAnimationTargets == null || this.playingCannedUnlockAnimation) {
            return;
        }
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
        if (keyguardStateControllerImpl.mFlingingToDismissKeyguard) {
            setSurfaceBehindAppearAmount(keyguardStateControllerImpl.mDismissAmount, true);
        } else if (keyguardStateControllerImpl.mDismissingFromTouch || keyguardStateControllerImpl.mSnappingKeyguardBackAfterSwipe) {
            setSurfaceBehindAppearAmount((keyguardStateControllerImpl.mDismissAmount - 0.15f) / 0.15f, true);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface KeyguardUnlockAnimationListener {
        default void onUnlockAnimationFinished() {
        }

        default void onUnlockAnimationStarted(boolean z, boolean z2) {
        }
    }

    public static /* synthetic */ void getSurfaceBehindAlphaAnimator$annotations() {
    }

    public static /* synthetic */ void getSurfaceBehindEntryAnimator$annotations() {
    }

    public static /* synthetic */ void getSurfaceTransactionApplier$annotations() {
    }

    public static /* synthetic */ void getWillUnlockWithInWindowLauncherAnimations$annotations() {
    }

    @Override // com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController
    public final void onLauncherSmartspaceStateUpdated(SmartspaceState smartspaceState) {
    }
}
