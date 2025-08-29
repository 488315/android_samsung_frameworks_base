package com.android.systemui.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.devicestate.DeviceStateManager;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.Trace;
import android.view.Choreographer;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.WindowManager;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.LsRune;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.doze.AODParameters;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController$Stub$Proxy;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.samsung.android.os.SemPerfManager;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes2.dex */
public final class KeyguardSecLegacyUnlockAnimationControllerImpl extends KeyguardUnlockAnimationController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ValueAnimator alphaAnimator;
    public long animStartDelay;
    public final AODAmbientWallpaperHelper aodAmbientWallpaperHelper;
    public KeyguardViewMediatorHelperImpl$setupLocked$5 callback;
    public AnimatorSet cannedAnimatorSet;
    public final Lazy centralSurfacesLazy;
    public SurfaceControl closingWallpaperLeash;
    public float curKeyguardAlpha;
    public SurfaceControl curLeash;
    public float curLeashAlpha;
    public float curLeashHeight;
    public float curLeashScale;
    public float curLeashWidth;
    public SurfaceControl.Transaction curTransaction;
    public final DelayableExecutor delayableExecutor;
    public final DozeParameters dozeParameters;
    public boolean forceEnded;
    public int frameUpdatedCount;
    public boolean isLauncherActivity;
    public boolean isPrimaryBouncerShowing;
    public final Lazy keyguardFastBioUnlockControllerLazy;
    public final KeyguardStateController keyguardStateController;
    public SurfaceControl keyguardSurface;
    public final Lazy keyguardSurfaceControllerLazy;
    public final KeyguardViewController keyguardViewController;
    public final Lazy keyguardViewMediatorLazy;
    public StandaloneCoroutine launcherUnlockJob;
    public final Executor mainExecutor;
    public final NotificationShadeWindowController notificationShadeWindowController;
    public SurfaceControl openingWallpaperLeash;
    public float reqKeyguardAlpha;
    public float reqLeashAlpha;
    public float reqLeashScale;
    public final CoroutineScope scope;
    private final SettingsHelper settingsHelper;
    public StandaloneCoroutine shadeExpansionCollectorJob;
    public final Lazy shadeInteractorLazy;
    public int skipFrameCount;
    public final Matrix surfaceBehindMatrix;
    public IRemoteAnimationFinishedCallback surfaceBehindRemoteAnimationFinishedCallback;
    public final float[] tmpFloat9;
    public String traceTag;
    public final Executor unlockAnimationExecutor;

    public KeyguardSecLegacyUnlockAnimationControllerImpl(Context context, WindowManager windowManager, Resources resources, Executor executor, DelayableExecutor delayableExecutor, Executor executor2, KeyguardStateController keyguardStateController, Lazy lazy, KeyguardViewController keyguardViewController, FeatureFlags featureFlags, Lazy lazy2, SysuiStatusBarStateController sysuiStatusBarStateController, NotificationShadeWindowController notificationShadeWindowController, InteractionJankMonitor interactionJankMonitor, Lazy lazy3, Lazy lazy4, PowerManager powerManager, WallpaperManager wallpaperManager, DeviceStateManager deviceStateManager, SettingsHelper settingsHelper, CoroutineScope coroutineScope, Lazy lazy5, AODAmbientWallpaperHelper aODAmbientWallpaperHelper, Lazy lazy6, DozeParameters dozeParameters) {
        super(windowManager, resources, keyguardStateController, lazy, keyguardViewController, featureFlags, lazy2, sysuiStatusBarStateController, notificationShadeWindowController, powerManager, wallpaperManager, deviceStateManager);
        this.mainExecutor = executor;
        this.delayableExecutor = delayableExecutor;
        this.unlockAnimationExecutor = executor2;
        this.keyguardStateController = keyguardStateController;
        this.keyguardViewMediatorLazy = lazy;
        this.keyguardViewController = keyguardViewController;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.centralSurfacesLazy = lazy3;
        this.keyguardSurfaceControllerLazy = lazy4;
        this.settingsHelper = settingsHelper;
        this.scope = coroutineScope;
        this.shadeInteractorLazy = lazy5;
        this.aodAmbientWallpaperHelper = aODAmbientWallpaperHelper;
        this.keyguardFastBioUnlockControllerLazy = lazy6;
        this.dozeParameters = dozeParameters;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 0.120000005f);
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.alphaAnimator = valueAnimatorOfFloat2;
        this.surfaceBehindMatrix = new Matrix();
        this.tmpFloat9 = new float[9];
        this.reqKeyguardAlpha = -1.0f;
        this.curKeyguardAlpha = -1.0f;
        this.reqLeashAlpha = -1.0f;
        this.reqLeashScale = -1.0f;
        this.curLeashAlpha = -1.0f;
        this.curLeashScale = -1.0f;
        valueAnimatorOfFloat.setInterpolator(KeyguardSecLegacyUnlockAnimationControllerImplKt.SCALE_INTERPOLATOR);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = this.this$0;
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                SurfaceControl surfaceControl = keyguardSecLegacyUnlockAnimationControllerImpl.curLeash;
                if (surfaceControl == null || keyguardSecLegacyUnlockAnimationControllerImpl.curTransaction == null) {
                    Log.w("KeyguardUnlock", "updateLeashScale " + surfaceControl + " " + keyguardSecLegacyUnlockAnimationControllerImpl.curTransaction);
                } else if (surfaceControl.isValid()) {
                    keyguardSecLegacyUnlockAnimationControllerImpl.reqLeashScale = (keyguardSecLegacyUnlockAnimationControllerImpl.isLauncherActivity ? 0.88f : 0.95f) + fFloatValue;
                } else {
                    Log.w("KeyguardUnlock", "invalid leash");
                }
                this.this$0.applyTransaction();
            }
        });
        valueAnimatorOfFloat2.setInterpolator(KeyguardSecLegacyUnlockAnimationControllerImplKt.ALPHA_INTERPOLATOR);
        valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$2$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = this.this$0;
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                int i = KeyguardSecLegacyUnlockAnimationControllerImpl.$r8$clinit;
                keyguardSecLegacyUnlockAnimationControllerImpl.updateLeashAlpha(fFloatValue);
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl2 = this.this$0;
                float fFloatValue2 = 1 - ((Float) valueAnimator.getAnimatedValue()).floatValue();
                SurfaceControl surfaceControl = keyguardSecLegacyUnlockAnimationControllerImpl2.keyguardViewController.getViewRootImpl().getSurfaceControl();
                keyguardSecLegacyUnlockAnimationControllerImpl2.keyguardSurface = surfaceControl;
                if (surfaceControl == null || keyguardSecLegacyUnlockAnimationControllerImpl2.curTransaction == null) {
                    Log.w("KeyguardUnlock", "updateKeyguardAlpha " + surfaceControl + " " + keyguardSecLegacyUnlockAnimationControllerImpl2.curTransaction);
                } else if (surfaceControl.isValid()) {
                    Log.d("KeyguardUnlock", "updateKeyguardAlpha: updateKeyguardAlpha=" + fFloatValue2);
                    keyguardSecLegacyUnlockAnimationControllerImpl2.reqKeyguardAlpha = fFloatValue2;
                } else {
                    Log.w("KeyguardUnlock", "updateKeyguardAlpha invalid leash");
                }
                this.this$0.applyTransaction();
            }
        });
        valueAnimatorOfFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$2$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                Log.d("KeyguardUnlock", "onAnimationCancel");
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = this.this$0;
                int i = keyguardSecLegacyUnlockAnimationControllerImpl.frameUpdatedCount;
                int i2 = keyguardSecLegacyUnlockAnimationControllerImpl.skipFrameCount;
                boolean z = keyguardSecLegacyUnlockAnimationControllerImpl.forceEnded;
                StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "onAnimationEnd frameUpdatedCount=", " skip=", " forceEnded=");
                sbM.append(z);
                Log.d("KeyguardUnlock", sbM.toString());
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl2 = this.this$0;
                keyguardSecLegacyUnlockAnimationControllerImpl2.getClass();
                Choreographer.getInstance().postCallback(1, new KeyguardSecLegacyUnlockAnimationControllerImpl$onFinished$1(keyguardSecLegacyUnlockAnimationControllerImpl2), null);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator, boolean z) {
                ArrayList<Animator> childAnimations;
                ArrayList<Animator> childAnimations2;
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = this.this$0;
                int i = KeyguardSecLegacyUnlockAnimationControllerImpl.$r8$clinit;
                AnimatorSet animatorSet = keyguardSecLegacyUnlockAnimationControllerImpl.cannedAnimatorSet;
                Integer numValueOf = null;
                Log.d("KeyguardUnlock", "onAnimationStart " + ((animatorSet == null || (childAnimations2 = animatorSet.getChildAnimations()) == null) ? null : Integer.valueOf(childAnimations2.size())));
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl2 = this.this$0;
                if (keyguardSecLegacyUnlockAnimationControllerImpl2.isPrimaryBouncerShowing) {
                    AnimatorSet animatorSet2 = keyguardSecLegacyUnlockAnimationControllerImpl2.cannedAnimatorSet;
                    if (animatorSet2 != null && (childAnimations = animatorSet2.getChildAnimations()) != null) {
                        numValueOf = Integer.valueOf(childAnimations.size());
                    }
                    if (numValueOf != null && numValueOf.intValue() == 1) {
                        keyguardSecLegacyUnlockAnimationControllerImpl2.updateLeashAlpha(1.0f);
                        keyguardSecLegacyUnlockAnimationControllerImpl2.applyTransaction();
                    }
                    keyguardSecLegacyUnlockAnimationControllerImpl2.updateKeyguardSurface(false);
                }
            }
        });
    }

    public final void applyTransaction() {
        boolean z;
        float f;
        float f2;
        float f3;
        final SurfaceControl.Transaction transaction = this.curTransaction;
        if (transaction != null) {
            boolean z2 = false;
            try {
                f3 = this.reqLeashAlpha;
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("KeyguardUnlock", "applyTransaction catch the exception");
            }
            if (f3 == -1.0f || f3 == this.curLeashAlpha) {
                z = false;
                f2 = this.reqKeyguardAlpha;
                if (f2 != -1.0f) {
                    trace(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$applyTransaction$1$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            SurfaceControl.Transaction transaction2 = transaction;
                            SurfaceControl surfaceControl = this.keyguardSurface;
                            surfaceControl.getClass();
                            transaction2.setAlpha(surfaceControl, this.reqKeyguardAlpha);
                        }
                    }, "setAlpha ".concat(String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(f2)}, 1))));
                    this.curKeyguardAlpha = this.reqKeyguardAlpha;
                    z = true;
                }
                f = this.reqLeashScale;
                if (f != -1.0f) {
                    this.surfaceBehindMatrix.setScale(f, f, this.curLeashWidth, this.curLeashHeight);
                    trace(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$applyTransaction$1$3
                        @Override // java.lang.Runnable
                        public final void run() {
                            SurfaceControl.Transaction transaction2 = transaction;
                            SurfaceControl surfaceControl = this.curLeash;
                            surfaceControl.getClass();
                            KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = this;
                            transaction2.setMatrix(surfaceControl, keyguardSecLegacyUnlockAnimationControllerImpl.surfaceBehindMatrix, keyguardSecLegacyUnlockAnimationControllerImpl.tmpFloat9);
                        }
                    }, "setMatrix ".concat(String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(this.reqLeashScale)}, 1))));
                    this.curLeashScale = this.reqLeashScale;
                    z2 = true;
                }
                if (z) {
                }
                trace(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$applyTransaction$1$4
                    @Override // java.lang.Runnable
                    public final void run() {
                        transaction.apply();
                    }
                }, "apply");
                this.frameUpdatedCount++;
            }
            trace(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$applyTransaction$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    SurfaceControl.Transaction transaction2 = transaction;
                    SurfaceControl surfaceControl = this.curLeash;
                    surfaceControl.getClass();
                    transaction2.setAlpha(surfaceControl, this.reqLeashAlpha);
                }
            }, "setAlpha ".concat(String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(f3)}, 1))));
            this.curLeashAlpha = this.reqLeashAlpha;
            z = true;
            try {
                f2 = this.reqKeyguardAlpha;
                if (f2 != -1.0f && f2 != this.curKeyguardAlpha) {
                    trace(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$applyTransaction$1$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            SurfaceControl.Transaction transaction2 = transaction;
                            SurfaceControl surfaceControl = this.keyguardSurface;
                            surfaceControl.getClass();
                            transaction2.setAlpha(surfaceControl, this.reqKeyguardAlpha);
                        }
                    }, "setAlpha ".concat(String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(f2)}, 1))));
                    this.curKeyguardAlpha = this.reqKeyguardAlpha;
                    z = true;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                Log.d("KeyguardUnlock", "applyTransaction catch the exception");
            }
            f = this.reqLeashScale;
            if (f != -1.0f && f != this.curLeashScale) {
                this.surfaceBehindMatrix.setScale(f, f, this.curLeashWidth, this.curLeashHeight);
                trace(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$applyTransaction$1$3
                    @Override // java.lang.Runnable
                    public final void run() {
                        SurfaceControl.Transaction transaction2 = transaction;
                        SurfaceControl surfaceControl = this.curLeash;
                        surfaceControl.getClass();
                        KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = this;
                        transaction2.setMatrix(surfaceControl, keyguardSecLegacyUnlockAnimationControllerImpl.surfaceBehindMatrix, keyguardSecLegacyUnlockAnimationControllerImpl.tmpFloat9);
                    }
                }, "setMatrix ".concat(String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(this.reqLeashScale)}, 1))));
                this.curLeashScale = this.reqLeashScale;
                z2 = true;
            }
            if (z && !z2) {
                this.skipFrameCount++;
            } else {
                trace(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$applyTransaction$1$4
                    @Override // java.lang.Runnable
                    public final void run() {
                        transaction.apply();
                    }
                }, "apply");
                this.frameUpdatedCount++;
            }
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardSecUnlockAnimationController
    public final long getUnlockAnimationDuration() throws NoSuchMethodException, ClassNotFoundException, SecurityException {
        float f;
        float transitionAnimationScale;
        if (this.isLauncherActivity) {
            f = 650L;
            transitionAnimationScale = this.settingsHelper.getTransitionAnimationScale();
        } else {
            if (this.isPrimaryBouncerShowing) {
                return 0L;
            }
            if (this.animStartDelay > 0) {
                f = 250L;
                transitionAnimationScale = this.settingsHelper.getTransitionAnimationScale();
            } else {
                f = 350L;
                transitionAnimationScale = this.settingsHelper.getTransitionAnimationScale();
            }
        }
        return (long) (transitionAnimationScale * f);
    }

    @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController
    public final void notifyFinishedKeyguardExitAnimation(boolean z) {
        Log.d("KeyguardUnlock", "notifyFinishedKeyguardExitAnimation");
        this.surfaceBehindRemoteAnimationTargets = null;
        super.notifyFinishedKeyguardExitAnimation(z);
    }

    @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController
    public final void notifyStartSurfaceBehindRemoteAnimation(RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, long j, boolean z) {
        this.surfaceBehindRemoteAnimationTargets = remoteAnimationTargetArr;
        this.openingWallpaperTargets = remoteAnimationTargetArr2;
        this.closingWallpaperTargets = remoteAnimationTargetArr3;
        this.surfaceBehindRemoteAnimationStartTime = j;
        Log.i("KeyguardUnlock", "notifyStartSurfaceBehindRemoteAnimation::target = " + remoteAnimationTargetArr);
        playCannedUnlockAnimation();
        ArrayList arrayList = this.listeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((KeyguardUnlockAnimationController.KeyguardUnlockAnimationListener) obj).onUnlockAnimationStarted(true, true);
        }
        finishKeyguardExitRemoteAnimationIfReachThreshold();
    }

    @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController
    public final void playCannedUnlockAnimation() {
        RemoteAnimationTarget remoteAnimationTarget;
        RemoteAnimationTarget remoteAnimationTarget2;
        ActivityManager.RunningTaskInfo runningTaskInfo;
        ComponentName componentName;
        this.playingCannedUnlockAnimation = true;
        if (((KeyguardFastBioUnlockController) this.keyguardFastBioUnlockControllerLazy.get()).isFastWakeAndUnlockMode()) {
            AODParameters aODParameters = this.dozeParameters.mAODParameters;
            boolean z = aODParameters != null && aODParameters.mDozeUiState;
            if (!z) {
                ((KeyguardViewMediator) this.keyguardViewMediatorLazy.get()).mHelper.onForegroundShown();
            }
            this.animStartDelay = 100L;
            if (LsRune.AOD_FULLSCREEN && ((KeyguardFastBioUnlockController) this.keyguardFastBioUnlockControllerLazy.get()).isWakeAndUnlockAnimationAODFullScreenMode()) {
                ((KeyguardFastBioUnlockController) this.keyguardFastBioUnlockControllerLazy.get()).reset();
                this.aodAmbientWallpaperHelper.needWallpaperForUnlockAnimation = true;
            }
            Log.i("KeyguardUnlock", "playCannedUnlockAnimation animStartDelay=" + this.animStartDelay + ", dozeUiStat=" + z + ", isWakeAndUnlockAnimationAODFullScreenMode=" + ((KeyguardFastBioUnlockController) this.keyguardFastBioUnlockControllerLazy.get()).isWakeAndUnlockAnimationAODFullScreenMode() + "needWallpaperForUnlockAnimation=" + this.aodAmbientWallpaperHelper.needWallpaperForUnlockAnimation);
        } else {
            this.aodAmbientWallpaperHelper.needWallpaperForUnlockAnimation = true;
        }
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = ((NotificationShadeWindowControllerImpl) this.notificationShadeWindowController).mHelper;
        NotificationShadeWindowState currentState = secNotificationShadeWindowControllerHelperImpl.getCurrentState();
        if (!currentState.forceVisibleForUnlockAnimation) {
            Log.d(SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG, "setForceVisibleForUnlockAnimation true");
            currentState.forceVisibleForUnlockAnimation = true;
            secNotificationShadeWindowControllerHelperImpl.apply(currentState);
        }
        RemoteAnimationTarget[] remoteAnimationTargetArr = this.surfaceBehindRemoteAnimationTargets;
        if (remoteAnimationTargetArr == null || Integer.valueOf(remoteAnimationTargetArr.length).intValue() < 1) {
            Log.e("KeyguardUnlock", "exitAnimParam app[0] is empty, surfaceBehindRemoteAnimationTargets = " + this.surfaceBehindRemoteAnimationTargets);
            KeyguardViewMediatorHelperImpl$setupLocked$5 keyguardViewMediatorHelperImpl$setupLocked$5 = this.callback;
            if (keyguardViewMediatorHelperImpl$setupLocked$5 != null) {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = keyguardViewMediatorHelperImpl$setupLocked$5.this$0;
                ViewMediatorProvider viewMediatorProvider = keyguardViewMediatorHelperImpl.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                this.surfaceBehindRemoteAnimationFinishedCallback = (IRemoteAnimationFinishedCallback) viewMediatorProvider.getSurfaceBehindRemoteAnimationFinishedCallback.invoke();
                ViewMediatorProvider viewMediatorProvider2 = keyguardViewMediatorHelperImpl.viewMediatorProvider;
                if (viewMediatorProvider2 == null) {
                    viewMediatorProvider2 = null;
                }
                viewMediatorProvider2.resetSurfaceBehindRemoteAnimationFinishedCallback.invoke();
            }
            KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this.keyguardViewMediatorLazy.get();
            keyguardViewMediator.mHelper.adjustStatusBarLocked$2();
            keyguardViewMediator.exitKeyguardAndFinishSurfaceBehindRemoteAnimation();
            Choreographer.getInstance().postCallback(1, new KeyguardSecLegacyUnlockAnimationControllerImpl$onFinished$1(this), null);
            return;
        }
        RemoteAnimationTarget[] remoteAnimationTargetArr2 = this.surfaceBehindRemoteAnimationTargets;
        final RemoteAnimationTarget remoteAnimationTarget3 = remoteAnimationTargetArr2 != null ? remoteAnimationTargetArr2[0] : null;
        this.isLauncherActivity = Intrinsics.areEqual((remoteAnimationTarget3 == null || (runningTaskInfo = remoteAnimationTarget3.taskInfo) == null || (componentName = runningTaskInfo.topActivity) == null) ? null : componentName.getClassName(), "com.sec.android.app.launcher.Launcher");
        this.isPrimaryBouncerShowing = ((KeyguardStateControllerImpl) this.keyguardStateController).mPrimaryBouncerShowing;
        RemoteAnimationTarget[] remoteAnimationTargetArr3 = this.openingWallpaperTargets;
        this.openingWallpaperLeash = (remoteAnimationTargetArr3 == null || (remoteAnimationTarget2 = (RemoteAnimationTarget) ArraysKt___ArraysKt.getOrNull(0, remoteAnimationTargetArr3)) == null) ? null : remoteAnimationTarget2.leash;
        RemoteAnimationTarget[] remoteAnimationTargetArr4 = this.closingWallpaperTargets;
        SurfaceControl surfaceControl = (remoteAnimationTargetArr4 == null || (remoteAnimationTarget = (RemoteAnimationTarget) ArraysKt___ArraysKt.getOrNull(0, remoteAnimationTargetArr4)) == null) ? null : remoteAnimationTarget.leash;
        this.closingWallpaperLeash = surfaceControl;
        boolean z2 = this.isLauncherActivity;
        boolean z3 = this.isPrimaryBouncerShowing;
        SurfaceControl surfaceControl2 = this.openingWallpaperLeash;
        StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("playCannedUnlockAnimation: isLauncherActivity=", ", isPrimaryBouncerShowing=", ", openingWallpaperLeash=", z2, z3);
        sbM.append(surfaceControl2);
        sbM.append(", closingWallpaperLeash=");
        sbM.append(surfaceControl);
        Log.d("KeyguardUnlock", sbM.toString());
        KeyguardViewMediatorHelperImpl$setupLocked$5 keyguardViewMediatorHelperImpl$setupLocked$52 = this.callback;
        if (keyguardViewMediatorHelperImpl$setupLocked$52 != null) {
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = keyguardViewMediatorHelperImpl$setupLocked$52.this$0;
            ViewMediatorProvider viewMediatorProvider3 = keyguardViewMediatorHelperImpl2.viewMediatorProvider;
            if (viewMediatorProvider3 == null) {
                viewMediatorProvider3 = null;
            }
            this.surfaceBehindRemoteAnimationFinishedCallback = (IRemoteAnimationFinishedCallback) viewMediatorProvider3.getSurfaceBehindRemoteAnimationFinishedCallback.invoke();
            ViewMediatorProvider viewMediatorProvider4 = keyguardViewMediatorHelperImpl2.viewMediatorProvider;
            if (viewMediatorProvider4 == null) {
                viewMediatorProvider4 = null;
            }
            viewMediatorProvider4.resetSurfaceBehindRemoteAnimationFinishedCallback.invoke();
        }
        ((KeyguardViewMediator) this.keyguardViewMediatorLazy.get()).mHelper.adjustStatusBarLocked$2();
        if (this.animStartDelay == 0) {
            if (this.isLauncherActivity) {
                if (LsRune.SECURITY_CAPTURED_BLUR && this.isPrimaryBouncerShowing) {
                    SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                    try {
                        try {
                            SurfaceControl surfaceControl3 = this.closingWallpaperLeash;
                            if (surfaceControl3 != null && surfaceControl3.isValid()) {
                                SurfaceControl surfaceControl4 = this.closingWallpaperLeash;
                                surfaceControl4.getClass();
                                transaction.setAlpha(surfaceControl4, 0.0f);
                            }
                            SurfaceControl surfaceControl5 = this.openingWallpaperLeash;
                            if (surfaceControl5 != null && surfaceControl5.isValid()) {
                                SurfaceControl surfaceControl6 = this.openingWallpaperLeash;
                                surfaceControl6.getClass();
                                transaction.setAlpha(surfaceControl6, 1.0f);
                            }
                            transaction.apply();
                        } catch (Exception e) {
                            Log.e("KeyguardUnlock", "wallpaper animation failed on SECURITY_CAPTURED_BLUR models");
                            e.printStackTrace();
                        }
                        transaction.close();
                    } catch (Throwable th) {
                        transaction.close();
                        throw th;
                    }
                }
                KeyguardSurfaceControllerImpl.setKeyguardSurfaceAppearAmount$default((KeyguardSurfaceControllerImpl) this.keyguardSurfaceControllerLazy.get(), null, 6);
            }
            if (this.shadeExpansionCollectorJob != null) {
                Log.e("KeyguardUnlock", "Already enabled");
            } else if (((Boolean) ((ShadeInteractorImpl) ((ShadeInteractor) this.shadeInteractorLazy.get())).isUserInteracting.$$delegate_0.getValue()).booleanValue()) {
                Log.e("KeyguardUnlock", "isUserInteracting already true, skipping enable");
            } else {
                StandaloneCoroutine standaloneCoroutineLaunch$default = BuildersKt.launch$default(this.scope, null, null, new KeyguardSecLegacyUnlockAnimationControllerImpl$enablePanelDetector$1(this, null), 3);
                this.shadeExpansionCollectorJob = standaloneCoroutineLaunch$default;
                standaloneCoroutineLaunch$default.invokeOnCompletion(new Function1() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        this.f$0.shadeExpansionCollectorJob = null;
                        return Unit.INSTANCE;
                    }
                });
            }
        }
        this.unlockAnimationExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl.playCannedUnlockAnimation.6
            @Override // java.lang.Runnable
            public final void run() {
                StandaloneCoroutine standaloneCoroutine;
                SemPerfManager.sendCommandToSsrm("TASK_BOOST", Process.myTid() + "/300");
                RemoteAnimationTarget remoteAnimationTarget4 = remoteAnimationTarget3;
                if (remoteAnimationTarget4 != null) {
                    KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = this;
                    SurfaceControl surfaceControl7 = remoteAnimationTarget4.leash;
                    keyguardSecLegacyUnlockAnimationControllerImpl.curLeash = surfaceControl7;
                    keyguardSecLegacyUnlockAnimationControllerImpl.traceTag = surfaceControl7.toString();
                    Rect rect = remoteAnimationTarget4.screenSpaceBounds;
                    keyguardSecLegacyUnlockAnimationControllerImpl.curLeashWidth = rect.width() / 2.0f;
                    keyguardSecLegacyUnlockAnimationControllerImpl.curLeashHeight = rect.height() / 2.0f;
                }
                this.curTransaction = new SurfaceControl.Transaction();
                final KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl2 = this;
                Log.d("KeyguardUnlock", "playUnlockAnimation: isLauncherActivity=" + keyguardSecLegacyUnlockAnimationControllerImpl2.isLauncherActivity);
                if (keyguardSecLegacyUnlockAnimationControllerImpl2.isLauncherActivity) {
                    StandaloneCoroutine standaloneCoroutine2 = keyguardSecLegacyUnlockAnimationControllerImpl2.launcherUnlockJob;
                    if (standaloneCoroutine2 != null && standaloneCoroutine2.isActive() && (standaloneCoroutine = keyguardSecLegacyUnlockAnimationControllerImpl2.launcherUnlockJob) != null) {
                        standaloneCoroutine.cancel(null);
                    }
                    keyguardSecLegacyUnlockAnimationControllerImpl2.launcherUnlockJob = BuildersKt.launch$default(keyguardSecLegacyUnlockAnimationControllerImpl2.scope, null, null, new KeyguardSecLegacyUnlockAnimationControllerImpl$playUnlockAnimation$1(keyguardSecLegacyUnlockAnimationControllerImpl2, null), 3);
                    try {
                        ILauncherUnlockAnimationController$Stub$Proxy iLauncherUnlockAnimationController$Stub$Proxy = keyguardSecLegacyUnlockAnimationControllerImpl2.launcherUnlockController;
                        if (iLauncherUnlockAnimationController$Stub$Proxy != null) {
                            iLauncherUnlockAnimationController$Stub$Proxy.prepareForUnlock(new Rect());
                            Unit unit = Unit.INSTANCE;
                        }
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                        Unit unit2 = Unit.INSTANCE;
                    }
                } else {
                    String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
                    AnimatorSet animatorSet = keyguardSecLegacyUnlockAnimationControllerImpl2.cannedAnimatorSet;
                    if (animatorSet != null) {
                        animatorSet.cancel();
                    }
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    animatorSet2.setInterpolator(KeyguardSecLegacyUnlockAnimationControllerImplKt.ALPHA_INTERPOLATOR);
                    animatorSet2.setDuration(keyguardSecLegacyUnlockAnimationControllerImpl2.getUnlockAnimationDuration());
                    Log.d("KeyguardUnlock", "playUnlockAnimation: duration=" + animatorSet2.getDuration());
                    animatorSet2.setStartDelay(keyguardSecLegacyUnlockAnimationControllerImpl2.animStartDelay);
                    animatorSet2.play(keyguardSecLegacyUnlockAnimationControllerImpl2.alphaAnimator);
                    keyguardSecLegacyUnlockAnimationControllerImpl2.cannedAnimatorSet = animatorSet2;
                    SurfaceControl surfaceControl8 = keyguardSecLegacyUnlockAnimationControllerImpl2.openingWallpaperLeash;
                    if (surfaceControl8 != null) {
                        SurfaceControl.Transaction transaction2 = keyguardSecLegacyUnlockAnimationControllerImpl2.curTransaction;
                        transaction2.getClass();
                        transaction2.setAlpha(surfaceControl8, 1.0f);
                    }
                    keyguardSecLegacyUnlockAnimationControllerImpl2.skipFrameCount = 0;
                    keyguardSecLegacyUnlockAnimationControllerImpl2.frameUpdatedCount = 0;
                    keyguardSecLegacyUnlockAnimationControllerImpl2.forceEnded = false;
                    keyguardSecLegacyUnlockAnimationControllerImpl2.reqLeashAlpha = -1.0f;
                    keyguardSecLegacyUnlockAnimationControllerImpl2.reqLeashScale = -1.0f;
                    keyguardSecLegacyUnlockAnimationControllerImpl2.curLeashAlpha = -1.0f;
                    keyguardSecLegacyUnlockAnimationControllerImpl2.curLeashScale = -1.0f;
                    AnimatorSet animatorSet3 = keyguardSecLegacyUnlockAnimationControllerImpl2.cannedAnimatorSet;
                    if (animatorSet3 != null) {
                        animatorSet3.start();
                    }
                }
                keyguardSecLegacyUnlockAnimationControllerImpl2.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$playUnlockAnimation$4
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((KeyguardViewMediator) keyguardSecLegacyUnlockAnimationControllerImpl2.keyguardViewMediatorLazy.get()).exitKeyguardAndFinishSurfaceBehindRemoteAnimation();
                    }
                });
            }
        });
    }

    @Override // com.android.systemui.keyguard.KeyguardSecUnlockAnimationController
    public final void setCallback(KeyguardViewMediatorHelperImpl$setupLocked$5 keyguardViewMediatorHelperImpl$setupLocked$5) {
        this.callback = keyguardViewMediatorHelperImpl$setupLocked$5;
    }

    public final void trace(Runnable runnable, String str) {
        String strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.traceTag, "#", str);
        if (strM.length() > 127) {
            strM = strM.substring(0, 126);
        }
        Trace.beginSection(strM);
        runnable.run();
        Trace.endSection();
    }

    @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController, com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController
    public final void unlockAnimationReady() {
        Log.d("KeyguardUnlock", "unlockAnimationReady: isLauncherActivity=" + this.isLauncherActivity + ", launcherUnlockJob=" + this.launcherUnlockJob);
        StandaloneCoroutine standaloneCoroutine = this.launcherUnlockJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        Log.d("KeyguardUnlock", "playUnlockAnimationForLauncher");
        this.mainExecutor.execute(new KeyguardSecLegacyUnlockAnimationControllerImpl$playUnlockAnimationForLauncher$1(this));
    }

    public final void updateKeyguardSurface(boolean z) {
        SurfaceControl surfaceControl = this.keyguardViewController.getViewRootImpl().getSurfaceControl();
        if (this.curTransaction == null || !surfaceControl.isValid()) {
            return;
        }
        long j = surfaceControl.mNativeObject;
        SurfaceControl surfaceControl2 = LsRune.SECURITY_BOUNCER_WINDOW ? ((CentralSurfacesImpl) ((CentralSurfaces) this.centralSurfacesLazy.get())).mBouncerContainer.getViewRootImpl().getSurfaceControl() : null;
        SurfaceControl.Transaction transaction = this.curTransaction;
        if (transaction != null) {
            SurfaceControl surfaceControl3 = this.closingWallpaperLeash;
            if (surfaceControl3 != null && surfaceControl3.isValid()) {
                try {
                    Log.d("KeyguardUnlock", "updateKeyguardSurface: hide closingWallpaperLeash");
                    SurfaceControl surfaceControl4 = this.closingWallpaperLeash;
                    surfaceControl4.getClass();
                    transaction.setAlpha(surfaceControl4, 0.0f).getClass();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (surfaceControl.isValid()) {
                try {
                    Log.d("KeyguardUnlock", "updateKeyguardSurface: hide keyguardSurface isLauncherActivity=" + z + ", isScreenOn=" + ((KeyguardViewMediator) this.keyguardViewMediatorLazy.get()).getViewMediatorCallback().isScreenOn());
                    if (((KeyguardViewMediator) this.keyguardViewMediatorLazy.get()).getViewMediatorCallback().isScreenOn()) {
                        transaction.setAlpha(surfaceControl, 0.001f);
                    }
                    if (this.animStartDelay != 0 && z) {
                        this.delayableExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$updateKeyguardSurface$1$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                ((KeyguardSurfaceControllerImpl) this.this$0.keyguardSurfaceControllerLazy.get()).restoreKeyguardSurfaceByTransaction();
                            }
                        }, this.animStartDelay);
                    }
                } catch (NullPointerException e2) {
                    e2.printStackTrace();
                    Log.d("KeyguardUnlock", "updateKeyguardSurface previousSurface : " + surfaceControl + ", id : " + Long.toHexString(j) + ", currentSurface : " + this.keyguardViewController.getViewRootImpl().getSurfaceControl() + ", id : " + Long.toHexString(this.keyguardViewController.getViewRootImpl().getSurfaceControl().mNativeObject));
                }
            }
            if (surfaceControl2 != null && surfaceControl2.isValid()) {
                try {
                    transaction.setAlpha(surfaceControl2, 0.0f).getClass();
                } catch (Exception e3) {
                    e3.printStackTrace();
                    Log.d("KeyguardUnlock", "updateKeyguardSurface bouncer SurfaceControl already released");
                }
            }
            transaction.apply();
        }
    }

    public final void updateLeashAlpha(float f) {
        SurfaceControl surfaceControl = this.curLeash;
        if (surfaceControl == null || this.curTransaction == null) {
            Log.w("KeyguardUnlock", "updateLeashAlpha " + surfaceControl + " " + this.curTransaction);
            return;
        }
        surfaceControl.getClass();
        if (!surfaceControl.isValid()) {
            Log.w("KeyguardUnlock", "updateLeashAlpha invalid leash");
            return;
        }
        Log.d("KeyguardUnlock", "updateLeashAlpha: updateLeashAlpha=" + f);
        this.reqLeashAlpha = f;
    }

    @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController, com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public final void onKeyguardDismissAmountChanged() {
    }

    @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController
    public final void setSurfaceBehindAppearAmount(float f, boolean z) {
    }
}
