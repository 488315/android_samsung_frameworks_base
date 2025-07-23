package com.android.systemui.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.WallpaperManager;
import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.devicestate.DeviceStateManager;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.Trace;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.core.math.MathUtils;
import com.android.app.animation.Interpolators;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
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
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class KeyguardUnlockAnimationController extends ISysuiUnlockAnimationController.Stub implements KeyguardStateController.Callback, KeyguardSecUnlockAnimationController {
    public final Lazy biometricUnlockControllerLazy;
    public RemoteAnimationTarget[] closingWallpaperTargets;
    public final DeviceStateManager deviceStateManager;
    public boolean dismissAmountThresholdsReached;
    public final FeatureFlags featureFlags;
    public final Handler handler;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardViewController keyguardViewController;
    public final Lazy keyguardViewMediator;
    public ILauncherUnlockAnimationController$Stub$Proxy launcherUnlockController;
    public View lockscreenSmartspace;
    public final NotificationShadeWindowController notificationShadeWindowController;
    public RemoteAnimationTarget[] openingWallpaperTargets;
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
    public final ValueAnimator wallpaperFadeOutUnlockAnimator;
    public final ArrayList listeners = new ArrayList();
    public float surfaceBehindAlpha = 1.0f;

    public KeyguardUnlockAnimationController(WindowManager windowManager, Resources resources, KeyguardStateController keyguardStateController, Lazy lazy, KeyguardViewController keyguardViewController, FeatureFlags featureFlags, Lazy lazy2, SysuiStatusBarStateController sysuiStatusBarStateController, NotificationShadeWindowController notificationShadeWindowController, PowerManager powerManager, WallpaperManager wallpaperManager, DeviceStateManager deviceStateManager) {
        this.resources = resources;
        this.keyguardStateController = keyguardStateController;
        this.keyguardViewMediator = lazy;
        this.keyguardViewController = keyguardViewController;
        this.featureFlags = featureFlags;
        this.biometricUnlockControllerLazy = lazy2;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.powerManager = powerManager;
        this.deviceStateManager = deviceStateManager;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.surfaceBehindAlphaAnimator = ofFloat;
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.wallpaperCannedUnlockAnimator = ofFloat2;
        ValueAnimator ofFloat3 = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.wallpaperFadeOutUnlockAnimator = ofFloat3;
        this.surfaceBehindMatrix = new Matrix();
        ValueAnimator ofFloat4 = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.surfaceBehindEntryAnimator = ofFloat4;
        this.handler = new Handler();
        this.tmpFloat = new float[9];
        ofFloat.setDuration(175L);
        Interpolator interpolator = Interpolators.LINEAR;
        ofFloat.setInterpolator(interpolator);
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
                    keyguardUnlockAnimationController.openingWallpaperTargets = null;
                    keyguardUnlockAnimationController.closingWallpaperTargets = null;
                    ((KeyguardViewMediator) keyguardUnlockAnimationController.keyguardViewMediator.get()).finishSurfaceBehindRemoteAnimation(false);
                }
            }
        });
        ofFloat2.setDuration(300L);
        ofFloat2.setInterpolator(interpolator);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$2$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardUnlockAnimationController.this.setWallpaperAppearAmount(((Float) valueAnimator.getAnimatedValue()).floatValue(), KeyguardUnlockAnimationController.this.openingWallpaperTargets);
            }
        });
        ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$2$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                android.util.Log.d("KeyguardUnlock", "wallpaperCannedUnlockAnimator#onAnimationEnd");
                ((KeyguardViewMediator) KeyguardUnlockAnimationController.this.keyguardViewMediator.get()).exitKeyguardAndFinishSurfaceBehindRemoteAnimation();
                Trace.asyncTraceEnd(4096L, "WallpaperAlphaAnimation", 0);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                Trace.asyncTraceBegin(4096L, "WallpaperAlphaAnimation", 0);
            }
        });
        ofFloat3.setDuration(150L);
        ofFloat3.setStartDelay(150L);
        ofFloat3.setInterpolator(interpolator);
        ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$3$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardUnlockAnimationController.this.setWallpaperAppearAmount(((Float) valueAnimator.getAnimatedValue()).floatValue(), KeyguardUnlockAnimationController.this.closingWallpaperTargets);
            }
        });
        ofFloat4.setDuration(300L);
        ofFloat4.setStartDelay(67L);
        ofFloat4.setInterpolator(Interpolators.TOUCH_RESPONSE);
        ofFloat4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$4$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardUnlockAnimationController.this.surfaceBehindAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                KeyguardUnlockAnimationController.this.setSurfaceBehindAppearAmount(((Float) valueAnimator.getAnimatedValue()).floatValue(), true);
            }
        });
        ofFloat4.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$4$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                android.util.Log.d("KeyguardUnlock", "surfaceBehindEntryAnimator#onAnimationEnd");
                KeyguardUnlockAnimationController keyguardUnlockAnimationController = KeyguardUnlockAnimationController.this;
                keyguardUnlockAnimationController.playingCannedUnlockAnimation = false;
                ((KeyguardViewMediator) keyguardUnlockAnimationController.keyguardViewMediator.get()).exitKeyguardAndFinishSurfaceBehindRemoteAnimation();
            }
        });
        ((KeyguardStateControllerImpl) keyguardStateController).addCallback(this);
        this.roundedCornerRadius = resources.getDimensionPixelSize(17105918);
    }

    public final void finishKeyguardExitRemoteAnimationIfReachThreshold() {
        if (((KeyguardStateControllerImpl) this.keyguardStateController).mShowing && !this.dismissAmountThresholdsReached && ((KeyguardViewMediator) this.keyguardViewMediator.get()).requestedShowSurfaceBehindKeyguard() && ((KeyguardViewMediator) this.keyguardViewMediator.get()).isAnimatingBetweenKeyguardAndSurfaceBehindOrWillBe()) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
            if (keyguardStateControllerImpl.mDismissAmount < 1.0f) {
                keyguardStateControllerImpl.getClass();
                return;
            }
            setSurfaceBehindAppearAmount(1.0f, true);
            this.dismissAmountThresholdsReached = true;
            ((KeyguardViewMediator) this.keyguardViewMediator.get()).exitKeyguardAndFinishSurfaceBehindRemoteAnimation();
        }
    }

    public final void hideKeyguardViewAfterRemoteAnimation() {
        if (((KeyguardStateControllerImpl) this.keyguardStateController).mShowing) {
            this.keyguardViewController.hide(this.surfaceBehindRemoteAnimationStartTime, 0L);
        } else {
            android.util.Log.i("KeyguardUnlock", "#hideKeyguardViewAfterRemoteAnimation called when keyguard view is not showing. Ignoring...");
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
                    iLauncherUnlockAnimationController$Stub$Proxy.setUnlockAmount(false);
                }
            } catch (RemoteException e) {
                android.util.Log.e("KeyguardUnlock", "Remote exception in notifyFinishedKeyguardExitAnimation", e);
            }
        }
        ArrayList arrayList = this.listeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((KeyguardUnlockAnimationListener) obj).onUnlockAnimationFinished();
        }
        this.surfaceBehindAlphaAnimator.cancel();
        this.surfaceBehindEntryAnimator.cancel();
        this.wallpaperCannedUnlockAnimator.cancel();
        this.wallpaperFadeOutUnlockAnimator.cancel();
        this.surfaceBehindRemoteAnimationTargets = null;
        this.openingWallpaperTargets = null;
        this.closingWallpaperTargets = null;
        this.playingCannedUnlockAnimation = false;
        this.dismissAmountThresholdsReached = false;
    }

    public void notifyStartSurfaceBehindRemoteAnimation(RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, long j, boolean z) {
        if (this.surfaceTransactionApplier == null) {
            this.surfaceTransactionApplier = new SyncRtSurfaceTransactionApplier(this.keyguardViewController.getViewRootImpl().getView());
        }
        this.surfaceBehindRemoteAnimationTargets = remoteAnimationTargetArr;
        this.openingWallpaperTargets = remoteAnimationTargetArr2;
        this.closingWallpaperTargets = remoteAnimationTargetArr3;
        this.surfaceBehindRemoteAnimationStartTime = j;
        if (z) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
            if (keyguardStateControllerImpl.mFlingingToDismissKeyguard) {
                playCannedUnlockAnimation();
            } else {
                keyguardStateControllerImpl.getClass();
                android.util.Log.d("KeyguardUnlock", "fadeInSurfaceBehind");
                this.surfaceBehindAlphaAnimator.cancel();
                this.surfaceBehindAlphaAnimator.start();
            }
        } else {
            playCannedUnlockAnimation();
        }
        int i = 0;
        boolean z2 = ((BiometricUnlockController) this.biometricUnlockControllerLazy.get()).isWakeAndUnlock() && ((BiometricUnlockController) this.biometricUnlockControllerLazy.get()).mMode != 6;
        ArrayList arrayList = this.listeners;
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((KeyguardUnlockAnimationListener) obj).onUnlockAnimationStarted(this.playingCannedUnlockAnimation, z2);
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
        Flags.INSTANCE.getClass();
        if (((FeatureFlagsClassicRelease) featureFlags).isEnabled(Flags.NEW_UNLOCK_SWIPE_ANIMATION) && !this.playingCannedUnlockAnimation && !this.dismissAmountThresholdsReached) {
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
        if (((KeyguardStateControllerImpl) this.keyguardStateController).mKeyguardGoingAway) {
            boolean z = ((StatusBarStateControllerImpl) this.statusBarStateController).mLeaveOpenOnKeyguardHide;
        }
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
                int height = remoteAnimationTarget.screenSpaceBounds.height();
                float clamp = (MathUtils.clamp(f, 0.0f, 1.0f) * 0.050000012f) + 0.95f;
                ((KeyguardStateControllerImpl) this.keyguardStateController).getClass();
                Matrix matrix = this.surfaceBehindMatrix;
                Rect rect = remoteAnimationTarget.screenSpaceBounds;
                float f3 = height;
                matrix.setTranslate(rect.left, DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(1.0f, f, f3 * 0.05f, rect.top));
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
                    syncRtSurfaceTransactionApplier.getClass();
                    syncRtSurfaceTransactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{build});
                }
            }
        }
        if (z) {
            float f4 = 25 / 325.0f;
            float max = Math.max(0.0f, (f - f4) / (1.0f - f4));
            float f5 = 150 / 325.0f;
            float coerceIn = RangesKt___RangesKt.coerceIn((f - f5) / ((f5 + f5) - f5), 0.0f, 1.0f);
            setWallpaperAppearAmount(max, this.openingWallpaperTargets);
            setWallpaperAppearAmount(1 - coerceIn, this.closingWallpaperTargets);
        }
    }

    public final void setWallpaperAppearAmount(float f, RemoteAnimationTarget[] remoteAnimationTargetArr) {
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
                    syncRtSurfaceTransactionApplier.getClass();
                    syncRtSurfaceTransactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{build});
                }
            }
        }
    }

    public void unlockAnimationReady() {
        android.util.Log.d("KeyguardUnlock", "unlockAnimationReady called");
    }

    public final void unlockToLauncherWithInWindowAnimations() {
        this.surfaceBehindAlpha = 1.0f;
        setSurfaceBehindAppearAmount(1.0f, false);
        try {
            ILauncherUnlockAnimationController$Stub$Proxy iLauncherUnlockAnimationController$Stub$Proxy = this.launcherUnlockController;
            if (iLauncherUnlockAnimationController$Stub$Proxy != null) {
                iLauncherUnlockAnimationController$Stub$Proxy.playUnlockAnimation(633L, 25L);
            }
        } catch (DeadObjectException unused) {
            android.util.Log.e("KeyguardUnlock", "launcherUnlockAnimationController was dead, but non-null. Catching exception as this should mean Launcher is in the process of being destroyed, but the IPC to System UI telling us hasn't arrived yet.");
        }
        FeatureFlags featureFlags = this.featureFlags;
        Flags.INSTANCE.getClass();
        ((FeatureFlagsClassicRelease) featureFlags).isEnabled(Flags.SMARTSPACE_SHARED_ELEMENT_TRANSITION_ENABLED);
        RemoteAnimationTarget[] remoteAnimationTargetArr = this.openingWallpaperTargets;
        if (remoteAnimationTargetArr != null) {
            if (!(remoteAnimationTargetArr.length == 0)) {
                android.util.Log.d("KeyguardUnlock", "fadeOutWallpaper");
                this.wallpaperFadeOutUnlockAnimator.cancel();
                this.wallpaperFadeOutUnlockAnimator.start();
            }
        }
        this.handler.postDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$unlockToLauncherWithInWindowAnimations$1
            @Override // java.lang.Runnable
            public final void run() {
                if (((KeyguardViewMediator) KeyguardUnlockAnimationController.this.keyguardViewMediator.get()).isShowingAndNotOccluded() && !((KeyguardStateControllerImpl) KeyguardUnlockAnimationController.this.keyguardStateController).mKeyguardGoingAway) {
                    android.util.Log.e("KeyguardUnlock", "Finish keyguard exit animation delayed Runnable ran, but we are showing and not going away.");
                    return;
                }
                KeyguardUnlockAnimationController keyguardUnlockAnimationController = KeyguardUnlockAnimationController.this;
                RemoteAnimationTarget[] remoteAnimationTargetArr2 = keyguardUnlockAnimationController.openingWallpaperTargets;
                if (remoteAnimationTargetArr2 != null) {
                    if (!(remoteAnimationTargetArr2.length == 0)) {
                        android.util.Log.d("KeyguardUnlock", "fadeInWallpaper");
                        keyguardUnlockAnimationController.wallpaperCannedUnlockAnimator.cancel();
                        keyguardUnlockAnimationController.wallpaperCannedUnlockAnimator.start();
                        KeyguardUnlockAnimationController.this.hideKeyguardViewAfterRemoteAnimation();
                        return;
                    }
                }
                ((KeyguardViewMediator) keyguardUnlockAnimationController.keyguardViewMediator.get()).exitKeyguardAndFinishSurfaceBehindRemoteAnimation();
            }
        }, 25L);
    }

    public final void updateSurfaceBehindAppearAmount() {
        if (this.surfaceBehindRemoteAnimationTargets == null || this.playingCannedUnlockAnimation) {
            return;
        }
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
        if (keyguardStateControllerImpl.mFlingingToDismissKeyguard) {
            setSurfaceBehindAppearAmount(keyguardStateControllerImpl.mDismissAmount, true);
            return;
        }
        keyguardStateControllerImpl.getClass();
        if (keyguardStateControllerImpl.mSnappingKeyguardBackAfterSwipe) {
            setSurfaceBehindAppearAmount((keyguardStateControllerImpl.mDismissAmount - 0.15f) / 0.15f, true);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface KeyguardUnlockAnimationListener {
        void onUnlockAnimationStarted(boolean z, boolean z2);

        default void onUnlockAnimationFinished() {
        }
    }

    @Override // com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController
    public final void onLauncherSmartspaceStateUpdated(SmartspaceState smartspaceState) {
    }

    public static /* synthetic */ void getSurfaceBehindAlphaAnimator$annotations() {
    }

    public static /* synthetic */ void getSurfaceBehindEntryAnimator$annotations() {
    }

    public static /* synthetic */ void getSurfaceTransactionApplier$annotations() {
    }

    public static /* synthetic */ void getWillUnlockWithInWindowLauncherAnimations$annotations() {
    }
}
