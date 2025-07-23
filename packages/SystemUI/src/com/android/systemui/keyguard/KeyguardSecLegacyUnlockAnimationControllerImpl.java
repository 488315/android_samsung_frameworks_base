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
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 0.120000005f);
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.alphaAnimator = ofFloat2;
        this.surfaceBehindMatrix = new Matrix();
        this.tmpFloat9 = new float[9];
        this.reqKeyguardAlpha = -1.0f;
        this.curKeyguardAlpha = -1.0f;
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
        ofFloat2.setInterpolator(KeyguardSecLegacyUnlockAnimationControllerImplKt.ALPHA_INTERPOLATOR);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$2$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                int i = KeyguardSecLegacyUnlockAnimationControllerImpl.$r8$clinit;
                keyguardSecLegacyUnlockAnimationControllerImpl.updateLeashAlpha(floatValue);
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl2 = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                float floatValue2 = 1 - ((Float) valueAnimator.getAnimatedValue()).floatValue();
                SurfaceControl surfaceControl = keyguardSecLegacyUnlockAnimationControllerImpl2.keyguardViewController.getViewRootImpl().getSurfaceControl();
                keyguardSecLegacyUnlockAnimationControllerImpl2.keyguardSurface = surfaceControl;
                if (surfaceControl == null || keyguardSecLegacyUnlockAnimationControllerImpl2.curTransaction == null) {
                    Log.w("KeyguardUnlock", "updateKeyguardAlpha " + surfaceControl + " " + keyguardSecLegacyUnlockAnimationControllerImpl2.curTransaction);
                } else if (surfaceControl.isValid()) {
                    Log.d("KeyguardUnlock", "updateKeyguardAlpha: updateKeyguardAlpha=" + floatValue2);
                    keyguardSecLegacyUnlockAnimationControllerImpl2.reqKeyguardAlpha = floatValue2;
                } else {
                    Log.w("KeyguardUnlock", "updateKeyguardAlpha invalid leash");
                }
                KeyguardSecLegacyUnlockAnimationControllerImpl.this.applyTransaction();
            }
        });
        ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$2$2
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
                StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "onAnimationEnd frameUpdatedCount=", " skip=", " forceEnded=");
                m.append(z);
                Log.d("KeyguardUnlock", m.toString());
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl2 = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                keyguardSecLegacyUnlockAnimationControllerImpl2.getClass();
                Choreographer.getInstance().postCallback(1, new KeyguardSecLegacyUnlockAnimationControllerImpl$onFinished$1(keyguardSecLegacyUnlockAnimationControllerImpl2), null);
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
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl2 = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                if (keyguardSecLegacyUnlockAnimationControllerImpl2.isPrimaryBouncerShowing) {
                    AnimatorSet animatorSet2 = keyguardSecLegacyUnlockAnimationControllerImpl2.cannedAnimatorSet;
                    if (animatorSet2 != null && (childAnimations = animatorSet2.getChildAnimations()) != null) {
                        num = Integer.valueOf(childAnimations.size());
                    }
                    if (num != null && num.intValue() == 1) {
                        keyguardSecLegacyUnlockAnimationControllerImpl2.updateLeashAlpha(1.0f);
                        keyguardSecLegacyUnlockAnimationControllerImpl2.applyTransaction();
                    }
                    keyguardSecLegacyUnlockAnimationControllerImpl2.updateKeyguardSurface(false);
                }
            }
        });
    }

    /* JADX WARN: Can't wrap try/catch for region: R(13:3|4|5|(8:36|37|9|10|(2:28|29)|13|(1:17)|(2:23|24)(2:20|21))|8|9|10|(1:12)(3:25|28|29)|13|(2:15|17)|(0)|23|24) */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0079, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x007a, code lost:
    
        r3.printStackTrace();
        com.android.systemui.keyguard.Log.d("KeyguardUnlock", "applyTransaction catch the exception");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void applyTransaction() {
        /*
            r11 = this;
            java.lang.String r0 = "applyTransaction catch the exception"
            java.lang.String r1 = "KeyguardUnlock"
            java.lang.String r2 = "%.2f"
            java.lang.String r3 = "setAlpha "
            android.view.SurfaceControl$Transaction r4 = r11.curTransaction
            if (r4 == 0) goto Ld7
            r5 = 0
            r6 = -1082130432(0xffffffffbf800000, float:-1.0)
            r7 = 1
            float r8 = r11.reqLeashAlpha     // Catch: java.lang.Exception -> L41
            int r9 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r9 != 0) goto L18
            goto L48
        L18:
            float r9 = r11.curLeashAlpha     // Catch: java.lang.Exception -> L41
            int r9 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r9 != 0) goto L1f
            goto L48
        L1f:
            java.lang.Float r8 = java.lang.Float.valueOf(r8)     // Catch: java.lang.Exception -> L41
            java.lang.Object[] r8 = new java.lang.Object[]{r8}     // Catch: java.lang.Exception -> L41
            java.lang.Object[] r8 = java.util.Arrays.copyOf(r8, r7)     // Catch: java.lang.Exception -> L41
            java.lang.String r8 = java.lang.String.format(r2, r8)     // Catch: java.lang.Exception -> L41
            java.lang.String r8 = r3.concat(r8)     // Catch: java.lang.Exception -> L41
            com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$applyTransaction$1$1 r9 = new com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$applyTransaction$1$1     // Catch: java.lang.Exception -> L41
            r9.<init>()     // Catch: java.lang.Exception -> L41
            r11.trace(r9, r8)     // Catch: java.lang.Exception -> L41
            float r8 = r11.reqLeashAlpha     // Catch: java.lang.Exception -> L41
            r11.curLeashAlpha = r8     // Catch: java.lang.Exception -> L41
            r8 = r7
            goto L49
        L41:
            r8 = move-exception
            r8.printStackTrace()
            com.android.systemui.keyguard.Log.d(r1, r0)
        L48:
            r8 = r5
        L49:
            float r9 = r11.reqKeyguardAlpha     // Catch: java.lang.Exception -> L79
            int r10 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
            if (r10 != 0) goto L50
            goto L80
        L50:
            float r10 = r11.curKeyguardAlpha     // Catch: java.lang.Exception -> L79
            int r10 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r10 != 0) goto L57
            goto L80
        L57:
            java.lang.Float r9 = java.lang.Float.valueOf(r9)     // Catch: java.lang.Exception -> L79
            java.lang.Object[] r9 = new java.lang.Object[]{r9}     // Catch: java.lang.Exception -> L79
            java.lang.Object[] r9 = java.util.Arrays.copyOf(r9, r7)     // Catch: java.lang.Exception -> L79
            java.lang.String r9 = java.lang.String.format(r2, r9)     // Catch: java.lang.Exception -> L79
            java.lang.String r3 = r3.concat(r9)     // Catch: java.lang.Exception -> L79
            com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$applyTransaction$1$2 r9 = new com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$applyTransaction$1$2     // Catch: java.lang.Exception -> L79
            r9.<init>()     // Catch: java.lang.Exception -> L79
            r11.trace(r9, r3)     // Catch: java.lang.Exception -> L79
            float r3 = r11.reqKeyguardAlpha     // Catch: java.lang.Exception -> L79
            r11.curKeyguardAlpha = r3     // Catch: java.lang.Exception -> L79
            r8 = r7
            goto L80
        L79:
            r3 = move-exception
            r3.printStackTrace()
            com.android.systemui.keyguard.Log.d(r1, r0)
        L80:
            float r0 = r11.reqLeashScale
            int r1 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r1 != 0) goto L87
            goto Lbd
        L87:
            float r1 = r11.curLeashScale
            int r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r1 != 0) goto L8e
            goto Lbd
        L8e:
            android.graphics.Matrix r1 = r11.surfaceBehindMatrix
            float r3 = r11.curLeashWidth
            float r5 = r11.curLeashHeight
            r1.setScale(r0, r0, r3, r5)
            float r0 = r11.reqLeashScale
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            java.lang.Object[] r0 = new java.lang.Object[]{r0}
            java.lang.Object[] r0 = java.util.Arrays.copyOf(r0, r7)
            java.lang.String r0 = java.lang.String.format(r2, r0)
            java.lang.String r1 = "setMatrix "
            java.lang.String r0 = r1.concat(r0)
            com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$applyTransaction$1$3 r1 = new com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$applyTransaction$1$3
            r1.<init>()
            r11.trace(r1, r0)
            float r0 = r11.reqLeashScale
            r11.curLeashScale = r0
            r5 = r7
        Lbd:
            if (r8 != 0) goto Lc8
            if (r5 == 0) goto Lc2
            goto Lc8
        Lc2:
            int r0 = r11.skipFrameCount
            int r0 = r0 + r7
            r11.skipFrameCount = r0
            goto Ld7
        Lc8:
            com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$applyTransaction$1$4 r0 = new com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$applyTransaction$1$4
            r0.<init>()
            java.lang.String r1 = "apply"
            r11.trace(r0, r1)
            int r0 = r11.frameUpdatedCount
            int r0 = r0 + r7
            r11.frameUpdatedCount = r0
        Ld7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl.applyTransaction():void");
    }

    @Override // com.android.systemui.keyguard.KeyguardSecUnlockAnimationController
    public final long getUnlockAnimationDuration() {
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
        boolean areEqual = Intrinsics.areEqual((remoteAnimationTarget3 == null || (runningTaskInfo = remoteAnimationTarget3.taskInfo) == null || (componentName = runningTaskInfo.topActivity) == null) ? null : componentName.getClassName(), "com.sec.android.app.launcher.Launcher");
        this.isLauncherActivity = areEqual;
        boolean z2 = ((KeyguardStateControllerImpl) this.keyguardStateController).mPrimaryBouncerShowing;
        this.isPrimaryBouncerShowing = z2;
        Log.d("KeyguardUnlock", "playCannedUnlockAnimation: isLauncherActivity=" + areEqual + ", isPrimaryBouncerShowing=" + z2);
        RemoteAnimationTarget[] remoteAnimationTargetArr3 = this.openingWallpaperTargets;
        this.openingWallpaperLeash = (remoteAnimationTargetArr3 == null || (remoteAnimationTarget2 = remoteAnimationTargetArr3[0]) == null) ? null : remoteAnimationTarget2.leash;
        RemoteAnimationTarget[] remoteAnimationTargetArr4 = this.closingWallpaperTargets;
        this.closingWallpaperLeash = (remoteAnimationTargetArr4 == null || (remoteAnimationTarget = remoteAnimationTargetArr4[0]) == null) ? null : remoteAnimationTarget.leash;
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
                KeyguardSurfaceControllerImpl.setKeyguardSurfaceAppearAmount$default((KeyguardSurfaceControllerImpl) this.keyguardSurfaceControllerLazy.get(), null, 6);
            }
            if (this.shadeExpansionCollectorJob != null) {
                Log.e("KeyguardUnlock", "Already enabled");
            } else if (((Boolean) ((ShadeInteractorImpl) ((ShadeInteractor) this.shadeInteractorLazy.get())).isUserInteracting.$$delegate_0.getValue()).booleanValue()) {
                Log.e("KeyguardUnlock", "isUserInteracting already true, skipping enable");
            } else {
                StandaloneCoroutine launch$default = BuildersKt.launch$default(this.scope, null, null, new KeyguardSecLegacyUnlockAnimationControllerImpl$enablePanelDetector$1(this, null), 3);
                this.shadeExpansionCollectorJob = launch$default;
                launch$default.invokeOnCompletion(new Function1() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        KeyguardSecLegacyUnlockAnimationControllerImpl.this.shadeExpansionCollectorJob = null;
                        return Unit.INSTANCE;
                    }
                });
            }
        }
        this.unlockAnimationExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$playCannedUnlockAnimation$5
            @Override // java.lang.Runnable
            public final void run() {
                StandaloneCoroutine standaloneCoroutine;
                SemPerfManager.sendCommandToSsrm("TASK_BOOST", Process.myTid() + "/300");
                RemoteAnimationTarget remoteAnimationTarget4 = remoteAnimationTarget3;
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
                    } catch (RemoteException e) {
                        e.printStackTrace();
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
                    SurfaceControl surfaceControl2 = keyguardSecLegacyUnlockAnimationControllerImpl2.openingWallpaperLeash;
                    if (surfaceControl2 != null) {
                        SurfaceControl.Transaction transaction = keyguardSecLegacyUnlockAnimationControllerImpl2.curTransaction;
                        transaction.getClass();
                        transaction.setAlpha(surfaceControl2, 1.0f);
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
                        ((KeyguardViewMediator) KeyguardSecLegacyUnlockAnimationControllerImpl.this.keyguardViewMediatorLazy.get()).exitKeyguardAndFinishSurfaceBehindRemoteAnimation();
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
        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.traceTag, "#", str);
        if (m.length() > 127) {
            m = m.substring(0, 126);
        }
        Trace.beginSection(m);
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
                                ((KeyguardSurfaceControllerImpl) KeyguardSecLegacyUnlockAnimationControllerImpl.this.keyguardSurfaceControllerLazy.get()).restoreKeyguardSurfaceByTransaction();
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
