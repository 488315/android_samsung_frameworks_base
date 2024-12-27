package com.android.systemui.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.Trace;
import android.view.Choreographer;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.WindowManager;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.LsRune;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.doze.AODParameters;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl;
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
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.os.SemPerfManager;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobSupport;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardSecLegacyUnlockAnimationControllerImpl extends KeyguardUnlockAnimationController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public long animStartDelay;
    public final AODAmbientWallpaperHelper aodAmbientWallpaperHelper;
    public KeyguardViewMediatorHelperImpl$setupLocked$5 callback;
    public AnimatorSet cannedAnimatorSet;
    public final Lazy centralSurfacesLazy;
    public SurfaceControl closingWallpaperLeash;
    public final Context context;
    public SurfaceControl curLeash;
    public float curLeashAlpha;
    public float curLeashHeight;
    public float curLeashScale;
    public float curLeashWidth;
    public SurfaceControl.Transaction curTransaction;
    public final DozeParameters dozeParameters;
    public boolean forceEnded;
    public int frameUpdatedCount;
    public boolean isLauncherActivity;
    public final InteractionJankMonitor jankMonitor;
    public JankMonitorContext jankMonitorContext;
    public final Lazy keyguardFastBioUnlockControllerLazy;
    public final Lazy keyguardSurfaceControllerLazy;
    public final KeyguardViewController keyguardViewController;
    public final Lazy keyguardViewMediatorLazy;
    public final Executor mainExecutor;
    public final NotificationShadeWindowController notificationShadeWindowController;
    public SurfaceControl openingWallpaperLeash;
    public float reqLeashAlpha;
    public float reqLeashScale;
    public final ValueAnimator scaleAnimator;
    public final CoroutineScope scope;
    private final SettingsHelper settingsHelper;
    public Job shadeExpansionCollectorJob;
    public final Lazy shadeInteractorLazy;
    public int skipFrameCount;
    public final Matrix surfaceBehindMatrix;
    public IRemoteAnimationFinishedCallback surfaceBehindRemoteAnimationFinishedCallback;
    public final float[] tmpFloat9;
    public String traceTag;
    public final Executor unlockAnimationExecutor;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class JankMonitorContext extends ContextWrapper {
        public final Handler handler;

        public JankMonitorContext(Context context, Handler handler) {
            super(context);
            this.handler = handler;
        }

        public final Handler getMainThreadHandler() {
            return this.handler;
        }
    }

    public KeyguardSecLegacyUnlockAnimationControllerImpl(Context context, WindowManager windowManager, Resources resources, Executor executor, Executor executor2, KeyguardStateController keyguardStateController, Lazy lazy, KeyguardViewController keyguardViewController, FeatureFlags featureFlags, Lazy lazy2, SysuiStatusBarStateController sysuiStatusBarStateController, NotificationShadeWindowController notificationShadeWindowController, InteractionJankMonitor interactionJankMonitor, Lazy lazy3, Lazy lazy4, PowerManager powerManager, WallpaperManager wallpaperManager, SettingsHelper settingsHelper, CoroutineScope coroutineScope, Lazy lazy5, AODAmbientWallpaperHelper aODAmbientWallpaperHelper, Lazy lazy6, DozeParameters dozeParameters) {
        super(windowManager, resources, keyguardStateController, lazy, keyguardViewController, featureFlags, lazy2, sysuiStatusBarStateController, notificationShadeWindowController, powerManager, wallpaperManager);
        this.context = context;
        this.mainExecutor = executor;
        this.unlockAnimationExecutor = executor2;
        this.keyguardViewMediatorLazy = lazy;
        this.keyguardViewController = keyguardViewController;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.jankMonitor = interactionJankMonitor;
        this.centralSurfacesLazy = lazy3;
        this.keyguardSurfaceControllerLazy = lazy4;
        this.settingsHelper = settingsHelper;
        this.scope = coroutineScope;
        this.shadeInteractorLazy = lazy5;
        this.aodAmbientWallpaperHelper = aODAmbientWallpaperHelper;
        this.keyguardFastBioUnlockControllerLazy = lazy6;
        this.dozeParameters = dozeParameters;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 0.120000005f);
        this.scaleAnimator = ofFloat;
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.surfaceBehindMatrix = new Matrix();
        this.tmpFloat9 = new float[9];
        this.reqLeashAlpha = -1.0f;
        this.reqLeashScale = -1.0f;
        this.curLeashAlpha = -1.0f;
        this.curLeashScale = -1.0f;
        ofFloat.setInterpolator(KeyguardSecLegacyUnlockAnimationControllerImplKt.SCALE_INTERPOLATOR);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                SurfaceControl surfaceControl = keyguardSecLegacyUnlockAnimationControllerImpl.curLeash;
                if (surfaceControl == null || keyguardSecLegacyUnlockAnimationControllerImpl.curTransaction == null) {
                    Log.w("KeyguardUnlock", "updateLeashScale " + surfaceControl + " " + keyguardSecLegacyUnlockAnimationControllerImpl.curTransaction);
                } else if (surfaceControl.isValid()) {
                    keyguardSecLegacyUnlockAnimationControllerImpl.reqLeashScale = (keyguardSecLegacyUnlockAnimationControllerImpl.isLauncherActivity ? 0.88f : 0.95f) + floatValue;
                } else {
                    Log.w("KeyguardUnlock", "invalid leash");
                }
                KeyguardSecLegacyUnlockAnimationControllerImpl.this.applyTransaction();
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                Log.d("KeyguardUnlock", "onAnimationCancel");
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                int i = keyguardSecLegacyUnlockAnimationControllerImpl.frameUpdatedCount;
                int i2 = keyguardSecLegacyUnlockAnimationControllerImpl.skipFrameCount;
                boolean z = keyguardSecLegacyUnlockAnimationControllerImpl.forceEnded;
                StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "onAnimationEnd frameUpdatedCount=", " skip=", " forceEnded=");
                m.append(z);
                Log.d("KeyguardUnlock", m.toString());
                final KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl2 = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                keyguardSecLegacyUnlockAnimationControllerImpl2.getClass();
                Choreographer.getInstance().postCallback(1, new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$onFinished$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardSecLegacyUnlockAnimationControllerImpl.this.jankMonitor.end(29);
                        SurfaceControl.Transaction transaction = KeyguardSecLegacyUnlockAnimationControllerImpl.this.curTransaction;
                        if (transaction != null) {
                            transaction.close();
                        }
                        final KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl3 = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                        keyguardSecLegacyUnlockAnimationControllerImpl3.curTransaction = null;
                        keyguardSecLegacyUnlockAnimationControllerImpl3.curLeash = null;
                        keyguardSecLegacyUnlockAnimationControllerImpl3.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$onFinished$1.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                try {
                                    IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = KeyguardSecLegacyUnlockAnimationControllerImpl.this.surfaceBehindRemoteAnimationFinishedCallback;
                                    if (iRemoteAnimationFinishedCallback != null) {
                                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                                    }
                                    Log.d("KeyguardUnlock", "IRemoteAnimationFinishedCallback#onAnimationFinished");
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl4 = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                                keyguardSecLegacyUnlockAnimationControllerImpl4.surfaceBehindRemoteAnimationFinishedCallback = null;
                                Log.i("KeyguardUnlock", "Disable panel detector");
                                Job job = keyguardSecLegacyUnlockAnimationControllerImpl4.shadeExpansionCollectorJob;
                                if (job != null) {
                                    job.cancel(null);
                                }
                                ((KeyguardSurfaceControllerImpl) KeyguardSecLegacyUnlockAnimationControllerImpl.this.keyguardSurfaceControllerLazy.get()).restoreKeyguardSurfaceIfVisible();
                            }
                        });
                    }
                }, null);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator, boolean z) {
                ArrayList<Animator> childAnimations;
                ArrayList<Animator> childAnimations2;
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                int i = KeyguardSecLegacyUnlockAnimationControllerImpl.$r8$clinit;
                AnimatorSet animatorSet = keyguardSecLegacyUnlockAnimationControllerImpl.cannedAnimatorSet;
                Integer num = null;
                Log.d("KeyguardUnlock", "onAnimationStart " + ((animatorSet == null || (childAnimations2 = animatorSet.getChildAnimations()) == null) ? null : Integer.valueOf(childAnimations2.size())));
                final KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl2 = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                AnimatorSet animatorSet2 = keyguardSecLegacyUnlockAnimationControllerImpl2.cannedAnimatorSet;
                if (animatorSet2 != null && (childAnimations = animatorSet2.getChildAnimations()) != null) {
                    num = Integer.valueOf(childAnimations.size());
                }
                if (num != null && num.intValue() == 1) {
                    keyguardSecLegacyUnlockAnimationControllerImpl2.updateLeashAlpha(1.0f);
                    keyguardSecLegacyUnlockAnimationControllerImpl2.applyTransaction();
                }
                keyguardSecLegacyUnlockAnimationControllerImpl2.updateKeyguardSurface();
                final Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$onStarted$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        if (!KeyguardSecLegacyUnlockAnimationControllerImpl.this.aodAmbientWallpaperHelper.isAODFullScreenAndShowing()) {
                            ((KeyguardFastBioUnlockController) KeyguardSecLegacyUnlockAnimationControllerImpl.this.keyguardFastBioUnlockControllerLazy.get()).reset();
                        }
                        if (LsRune.AOD_LIGHT_REVEAL && ((NotificationShadeWindowControllerImpl) KeyguardSecLegacyUnlockAnimationControllerImpl.this.notificationShadeWindowController).mHelper.getCurrentState().forceVisibleForUnlockAnimation) {
                            ((CentralSurfacesImpl) ((CentralSurfaces) KeyguardSecLegacyUnlockAnimationControllerImpl.this.centralSurfacesLazy.get())).mLightRevealScrim.setRevealAmount(1.0f);
                        }
                        ((NotificationShadeWindowControllerImpl) KeyguardSecLegacyUnlockAnimationControllerImpl.this.notificationShadeWindowController).mHelper.resetForceVisibleForUnlockAnimation();
                        KeyguardSecLegacyUnlockAnimationControllerImpl.this.animStartDelay = 0L;
                        return Unit.INSTANCE;
                    }
                };
                if (Looper.getMainLooper().isCurrentThread()) {
                    function0.invoke();
                } else {
                    keyguardSecLegacyUnlockAnimationControllerImpl2.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImplKt$sam$java_lang_Runnable$0
                        @Override // java.lang.Runnable
                        public final /* synthetic */ void run() {
                            Function0.this.invoke();
                        }
                    });
                }
            }
        });
        ofFloat2.setInterpolator(KeyguardSecLegacyUnlockAnimationControllerImplKt.ALPHA_INTERPOLATOR);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$2$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                int i = KeyguardSecLegacyUnlockAnimationControllerImpl.$r8$clinit;
                keyguardSecLegacyUnlockAnimationControllerImpl.updateLeashAlpha(floatValue);
            }
        });
    }

    public final void applyTransaction() {
        boolean z;
        float f;
        float f2;
        final SurfaceControl.Transaction transaction = this.curTransaction;
        if (transaction != null) {
            boolean z2 = false;
            try {
                f2 = this.reqLeashAlpha;
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("KeyguardUnlock", "applyTransaction catch the exception");
            }
            if (f2 != -1.0f && f2 != this.curLeashAlpha) {
                trace(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$applyTransaction$1$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SurfaceControl.Transaction transaction2 = transaction;
                        SurfaceControl surfaceControl = this.curLeash;
                        Intrinsics.checkNotNull(surfaceControl);
                        transaction2.setAlpha(surfaceControl, this.reqLeashAlpha);
                    }
                }, "setAlpha ".concat(String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(f2)}, 1))));
                this.curLeashAlpha = this.reqLeashAlpha;
                z = true;
                f = this.reqLeashScale;
                if (f != -1.0f && f != this.curLeashScale) {
                    this.surfaceBehindMatrix.setScale(f, f, this.curLeashWidth, this.curLeashHeight);
                    trace(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$applyTransaction$1$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            SurfaceControl.Transaction transaction2 = transaction;
                            SurfaceControl surfaceControl = this.curLeash;
                            Intrinsics.checkNotNull(surfaceControl);
                            KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = this;
                            transaction2.setMatrix(surfaceControl, keyguardSecLegacyUnlockAnimationControllerImpl.surfaceBehindMatrix, keyguardSecLegacyUnlockAnimationControllerImpl.tmpFloat9);
                        }
                    }, "setMatrix ".concat(String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(this.reqLeashScale)}, 1))));
                    this.curLeashScale = this.reqLeashScale;
                    z2 = true;
                }
                if (z && !z2) {
                    this.skipFrameCount++;
                    return;
                } else {
                    trace(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$applyTransaction$1$3
                        @Override // java.lang.Runnable
                        public final void run() {
                            transaction.apply();
                        }
                    }, "apply");
                    this.frameUpdatedCount++;
                }
            }
            z = false;
            f = this.reqLeashScale;
            if (f != -1.0f) {
                this.surfaceBehindMatrix.setScale(f, f, this.curLeashWidth, this.curLeashHeight);
                trace(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$applyTransaction$1$2
                    @Override // java.lang.Runnable
                    public final void run() {
                        SurfaceControl.Transaction transaction2 = transaction;
                        SurfaceControl surfaceControl = this.curLeash;
                        Intrinsics.checkNotNull(surfaceControl);
                        KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = this;
                        transaction2.setMatrix(surfaceControl, keyguardSecLegacyUnlockAnimationControllerImpl.surfaceBehindMatrix, keyguardSecLegacyUnlockAnimationControllerImpl.tmpFloat9);
                    }
                }, "setMatrix ".concat(String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(this.reqLeashScale)}, 1))));
                this.curLeashScale = this.reqLeashScale;
                z2 = true;
            }
            if (z) {
            }
            trace(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$applyTransaction$1$3
                @Override // java.lang.Runnable
                public final void run() {
                    transaction.apply();
                }
            }, "apply");
            this.frameUpdatedCount++;
        }
    }

    @Override // com.android.systemui.keyguard.KeyguardSecUnlockAnimationController
    public final long getUnlockAnimationDuration() {
        float f;
        float transitionAnimationScale;
        if (this.isLauncherActivity) {
            f = 650L;
            transitionAnimationScale = this.settingsHelper.getTransitionAnimationScale();
        } else {
            f = 0;
            transitionAnimationScale = this.settingsHelper.getTransitionAnimationScale();
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
    public final void notifyStartSurfaceBehindRemoteAnimation(RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, long j, boolean z) {
        this.surfaceBehindRemoteAnimationTargets = remoteAnimationTargetArr;
        this.wallpaperTargets = remoteAnimationTargetArr2;
        this.surfaceBehindRemoteAnimationStartTime = j;
        playCannedUnlockAnimation();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((KeyguardUnlockAnimationController.KeyguardUnlockAnimationListener) it.next()).onUnlockAnimationStarted(true, true);
        }
        finishKeyguardExitRemoteAnimationIfReachThreshold();
    }

    @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController
    public final void playCannedUnlockAnimation() {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        ComponentName componentName;
        if (((KeyguardFastBioUnlockController) this.keyguardFastBioUnlockControllerLazy.get()).isFastWakeAndUnlockMode()) {
            AODParameters aODParameters = this.dozeParameters.mAODParameters;
            boolean z = aODParameters != null && aODParameters.mDozeUiState;
            if (!z) {
                ((KeyguardViewMediator) this.keyguardViewMediatorLazy.get()).mHelper.onForegroundShown();
            }
            this.animStartDelay = 100L;
            if (LsRune.AOD_FULLSCREEN && this.aodAmbientWallpaperHelper.isAODFullScreenAndShowing()) {
                ((KeyguardFastBioUnlockController) this.keyguardFastBioUnlockControllerLazy.get()).reset();
            }
            SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = ((NotificationShadeWindowControllerImpl) this.notificationShadeWindowController).mHelper;
            NotificationShadeWindowState currentState = secNotificationShadeWindowControllerHelperImpl.getCurrentState();
            if (!currentState.forceVisibleForUnlockAnimation) {
                Log.d(SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG, "setForceVisibleForUnlockAnimation true");
                currentState.forceVisibleForUnlockAnimation = true;
                secNotificationShadeWindowControllerHelperImpl.apply(currentState);
            }
            Log.i("KeyguardUnlock", "playCannedUnlockAnimation animStartDelay=" + this.animStartDelay + ", dozeUiStat=" + z + ", aodAmbientWallpaperHelper.isAODFullScreenAndShowing()=" + this.aodAmbientWallpaperHelper.isAODFullScreenAndShowing());
        }
        this.playingCannedUnlockAnimation = false;
        RemoteAnimationTarget[] remoteAnimationTargetArr = this.surfaceBehindRemoteAnimationTargets;
        final RemoteAnimationTarget remoteAnimationTarget = remoteAnimationTargetArr != null ? remoteAnimationTargetArr[0] : null;
        boolean areEqual = Intrinsics.areEqual((remoteAnimationTarget == null || (runningTaskInfo = remoteAnimationTarget.taskInfo) == null || (componentName = runningTaskInfo.topActivity) == null) ? null : componentName.getClassName(), "com.sec.android.app.launcher.Launcher");
        this.isLauncherActivity = areEqual;
        Log.d("KeyguardUnlock", "playCannedUnlockAnimation: isLauncherActivity=" + areEqual);
        if (this.isLauncherActivity) {
            try {
                ILauncherUnlockAnimationController$Stub$Proxy iLauncherUnlockAnimationController$Stub$Proxy = this.launcherUnlockController;
                if (iLauncherUnlockAnimationController$Stub$Proxy != null) {
                    iLauncherUnlockAnimationController$Stub$Proxy.prepareForUnlock(0, false, new Rect());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        RemoteAnimationTarget[] remoteAnimationTargetArr2 = this.wallpaperTargets;
        if (remoteAnimationTargetArr2 != null) {
            ArrayList arrayList = new ArrayList();
            for (RemoteAnimationTarget remoteAnimationTarget2 : remoteAnimationTargetArr2) {
                if (remoteAnimationTarget2.mode == 0) {
                    arrayList.add(remoteAnimationTarget2);
                }
            }
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.openingWallpaperLeash = ((RemoteAnimationTarget) it.next()).leash;
                arrayList2.add(Unit.INSTANCE);
            }
        }
        RemoteAnimationTarget[] remoteAnimationTargetArr3 = this.wallpaperTargets;
        if (remoteAnimationTargetArr3 != null) {
            ArrayList arrayList3 = new ArrayList();
            for (RemoteAnimationTarget remoteAnimationTarget3 : remoteAnimationTargetArr3) {
                if (remoteAnimationTarget3.mode == 1) {
                    arrayList3.add(remoteAnimationTarget3);
                }
            }
            ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList3, 10));
            Iterator it2 = arrayList3.iterator();
            while (it2.hasNext()) {
                this.closingWallpaperLeash = ((RemoteAnimationTarget) it2.next()).leash;
                arrayList4.add(Unit.INSTANCE);
            }
        }
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
        if (this.animStartDelay == 0) {
            ((KeyguardSurfaceControllerImpl) this.keyguardSurfaceControllerLazy.get()).setKeyguardSurfaceAppearAmount(0.01f, null);
            if (this.shadeExpansionCollectorJob != null) {
                Log.e("KeyguardUnlock", "Already enabled");
            } else if (((Boolean) ((ShadeInteractorImpl) ((ShadeInteractor) this.shadeInteractorLazy.get())).isUserInteracting.$$delegate_0.getValue()).booleanValue()) {
                Log.e("KeyguardUnlock", "isUserInteracting already true, skipping enable");
            } else {
                Job launch$default = BuildersKt.launch$default(this.scope, null, null, new KeyguardSecLegacyUnlockAnimationControllerImpl$enablePanelDetector$1(this, null), 3);
                this.shadeExpansionCollectorJob = launch$default;
                ((JobSupport) launch$default).invokeOnCompletion(new Function1() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$enablePanelDetector$2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        KeyguardSecLegacyUnlockAnimationControllerImpl.this.shadeExpansionCollectorJob = null;
                        return Unit.INSTANCE;
                    }
                });
            }
        }
        this.unlockAnimationExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$playCannedUnlockAnimation$7
            @Override // java.lang.Runnable
            public final void run() {
                SemPerfManager.sendCommandToSsrm("TASK_BOOST", Process.myTid() + "/300");
                RemoteAnimationTarget remoteAnimationTarget4 = remoteAnimationTarget;
                if (remoteAnimationTarget4 != null) {
                    KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = this;
                    SurfaceControl surfaceControl = remoteAnimationTarget4.leash;
                    keyguardSecLegacyUnlockAnimationControllerImpl.curLeash = surfaceControl;
                    keyguardSecLegacyUnlockAnimationControllerImpl.traceTag = surfaceControl.toString();
                    Rect rect = remoteAnimationTarget4.screenSpaceBounds;
                    keyguardSecLegacyUnlockAnimationControllerImpl.curLeashWidth = rect.width() / 2.0f;
                    keyguardSecLegacyUnlockAnimationControllerImpl.curLeashHeight = rect.height() / 2.0f;
                }
                this.curTransaction = new SurfaceControl.Transaction();
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl2 = this;
                if (keyguardSecLegacyUnlockAnimationControllerImpl2.isLauncherActivity) {
                    try {
                        ILauncherUnlockAnimationController$Stub$Proxy iLauncherUnlockAnimationController$Stub$Proxy2 = keyguardSecLegacyUnlockAnimationControllerImpl2.launcherUnlockController;
                        if (iLauncherUnlockAnimationController$Stub$Proxy2 != null) {
                            iLauncherUnlockAnimationController$Stub$Proxy2.playUnlockAnimation(200L, true, keyguardSecLegacyUnlockAnimationControllerImpl2.animStartDelay);
                        }
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                    }
                    final KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl3 = this;
                    keyguardSecLegacyUnlockAnimationControllerImpl3.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$playCannedUnlockAnimation$7.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            if (!KeyguardSecLegacyUnlockAnimationControllerImpl.this.aodAmbientWallpaperHelper.isAODFullScreenAndShowing()) {
                                ((KeyguardFastBioUnlockController) KeyguardSecLegacyUnlockAnimationControllerImpl.this.keyguardFastBioUnlockControllerLazy.get()).reset();
                            }
                            if (LsRune.AOD_LIGHT_REVEAL && ((NotificationShadeWindowControllerImpl) KeyguardSecLegacyUnlockAnimationControllerImpl.this.notificationShadeWindowController).mHelper.getCurrentState().forceVisibleForUnlockAnimation) {
                                ((CentralSurfacesImpl) ((CentralSurfaces) KeyguardSecLegacyUnlockAnimationControllerImpl.this.centralSurfacesLazy.get())).mLightRevealScrim.setRevealAmount(1.0f);
                            }
                            ((NotificationShadeWindowControllerImpl) KeyguardSecLegacyUnlockAnimationControllerImpl.this.notificationShadeWindowController).mHelper.resetForceVisibleForUnlockAnimation();
                            KeyguardSecLegacyUnlockAnimationControllerImpl.this.updateLeashAlpha(1.0f);
                            KeyguardSecLegacyUnlockAnimationControllerImpl.this.applyTransaction();
                            KeyguardSecLegacyUnlockAnimationControllerImpl.this.updateKeyguardSurface();
                            SurfaceControl.Transaction transaction = KeyguardSecLegacyUnlockAnimationControllerImpl.this.curTransaction;
                            if (transaction != null) {
                                transaction.close();
                            }
                            KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl4 = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                            keyguardSecLegacyUnlockAnimationControllerImpl4.curTransaction = null;
                            keyguardSecLegacyUnlockAnimationControllerImpl4.curLeash = null;
                            try {
                                IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = keyguardSecLegacyUnlockAnimationControllerImpl4.surfaceBehindRemoteAnimationFinishedCallback;
                                if (iRemoteAnimationFinishedCallback != null) {
                                    iRemoteAnimationFinishedCallback.onAnimationFinished();
                                }
                                Log.d("KeyguardUnlock", "IRemoteAnimationFinishedCallback#onAnimationFinished");
                            } catch (RemoteException e3) {
                                e3.printStackTrace();
                            }
                            KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl5 = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                            keyguardSecLegacyUnlockAnimationControllerImpl5.surfaceBehindRemoteAnimationFinishedCallback = null;
                            Log.i("KeyguardUnlock", "Disable panel detector");
                            Job job = keyguardSecLegacyUnlockAnimationControllerImpl5.shadeExpansionCollectorJob;
                            if (job != null) {
                                job.cancel(null);
                            }
                            ((KeyguardSurfaceControllerImpl) KeyguardSecLegacyUnlockAnimationControllerImpl.this.keyguardSurfaceControllerLazy.get()).restoreKeyguardSurfaceIfVisible();
                            KeyguardSecLegacyUnlockAnimationControllerImpl.this.animStartDelay = 0L;
                        }
                    });
                    return;
                }
                AnimatorSet animatorSet = keyguardSecLegacyUnlockAnimationControllerImpl2.cannedAnimatorSet;
                if (animatorSet != null) {
                    animatorSet.cancel();
                }
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl4 = this;
                AnimatorSet animatorSet2 = new AnimatorSet();
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl5 = this;
                animatorSet2.setDuration(keyguardSecLegacyUnlockAnimationControllerImpl5.getUnlockAnimationDuration());
                animatorSet2.setStartDelay(keyguardSecLegacyUnlockAnimationControllerImpl5.animStartDelay);
                keyguardSecLegacyUnlockAnimationControllerImpl5.scaleAnimator.setFloatValues(0.0f, 0.050000012f);
                animatorSet2.play(keyguardSecLegacyUnlockAnimationControllerImpl5.scaleAnimator);
                keyguardSecLegacyUnlockAnimationControllerImpl4.cannedAnimatorSet = animatorSet2;
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl6 = this;
                SurfaceControl surfaceControl2 = keyguardSecLegacyUnlockAnimationControllerImpl6.openingWallpaperLeash;
                if (surfaceControl2 != null) {
                    SurfaceControl.Transaction transaction = keyguardSecLegacyUnlockAnimationControllerImpl6.curTransaction;
                    Intrinsics.checkNotNull(transaction);
                    transaction.setAlpha(surfaceControl2, 1.0f);
                }
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl7 = this;
                keyguardSecLegacyUnlockAnimationControllerImpl7.skipFrameCount = 0;
                keyguardSecLegacyUnlockAnimationControllerImpl7.frameUpdatedCount = 0;
                keyguardSecLegacyUnlockAnimationControllerImpl7.forceEnded = false;
                keyguardSecLegacyUnlockAnimationControllerImpl7.reqLeashAlpha = -1.0f;
                keyguardSecLegacyUnlockAnimationControllerImpl7.reqLeashScale = -1.0f;
                keyguardSecLegacyUnlockAnimationControllerImpl7.curLeashAlpha = -1.0f;
                keyguardSecLegacyUnlockAnimationControllerImpl7.curLeashScale = -1.0f;
                InteractionJankMonitor interactionJankMonitor = keyguardSecLegacyUnlockAnimationControllerImpl7.jankMonitor;
                KeyguardSecLegacyUnlockAnimationControllerImpl.JankMonitorContext jankMonitorContext = keyguardSecLegacyUnlockAnimationControllerImpl7.jankMonitorContext;
                if (jankMonitorContext == null) {
                    Looper myLooper = Looper.myLooper();
                    if (myLooper != null) {
                        KeyguardSecLegacyUnlockAnimationControllerImpl.JankMonitorContext jankMonitorContext2 = new KeyguardSecLegacyUnlockAnimationControllerImpl.JankMonitorContext(keyguardSecLegacyUnlockAnimationControllerImpl7.context, new Handler(myLooper));
                        keyguardSecLegacyUnlockAnimationControllerImpl7.jankMonitorContext = jankMonitorContext2;
                        jankMonitorContext = jankMonitorContext2;
                    } else {
                        jankMonitorContext = null;
                    }
                }
                Intrinsics.checkNotNull(jankMonitorContext);
                SurfaceControl surfaceControl3 = keyguardSecLegacyUnlockAnimationControllerImpl7.curLeash;
                Intrinsics.checkNotNull(surfaceControl3);
                interactionJankMonitor.begin(InteractionJankMonitor.Configuration.Builder.withSurface(29, jankMonitorContext, surfaceControl3).setTag("KeyguardUnlock"));
                AnimatorSet animatorSet3 = this.cannedAnimatorSet;
                if (animatorSet3 != null) {
                    animatorSet3.start();
                }
            }
        });
    }

    @Override // com.android.systemui.keyguard.KeyguardSecUnlockAnimationController
    public final void setCallback(KeyguardViewMediatorHelperImpl$setupLocked$5 keyguardViewMediatorHelperImpl$setupLocked$5) {
        this.callback = keyguardViewMediatorHelperImpl$setupLocked$5;
    }

    public final void trace(Runnable runnable, String str) {
        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.traceTag, "#", str);
        if (m.length() > 127) {
            m = m.substring(0, 126);
        }
        Trace.beginSection(m);
        runnable.run();
        Trace.endSection();
    }

    public final void updateKeyguardSurface() {
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
                    Intrinsics.checkNotNull(surfaceControl4);
                    transaction.setAlpha(surfaceControl4, 0.0f);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (surfaceControl.isValid() && this.animStartDelay == 0) {
                try {
                    Log.d("KeyguardUnlock", "updateKeyguardSurface: hide keyguardSurface");
                    transaction.setAlpha(surfaceControl, 0.01f);
                } catch (NullPointerException e2) {
                    e2.printStackTrace();
                    Log.d("KeyguardUnlock", "updateKeyguardSurface previousSurface : " + surfaceControl + ", id : " + Long.toHexString(j) + ", currentSurface : " + this.keyguardViewController.getViewRootImpl().getSurfaceControl() + ", id : " + Long.toHexString(this.keyguardViewController.getViewRootImpl().getSurfaceControl().mNativeObject));
                }
            }
            if (surfaceControl2 != null && surfaceControl2.isValid()) {
                try {
                    transaction.setAlpha(surfaceControl2, 0.0f);
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
        Intrinsics.checkNotNull(surfaceControl);
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
