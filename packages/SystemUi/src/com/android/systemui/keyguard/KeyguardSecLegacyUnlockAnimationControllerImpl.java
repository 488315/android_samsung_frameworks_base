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
import android.view.ViewRootImpl;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.math.MathUtils;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.LsRune;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl;
import com.android.systemui.keyguard.KeyguardSurfaceController;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeStateListener;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.samsung.android.os.SemPerfManager;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardSecLegacyUnlockAnimationControllerImpl extends KeyguardUnlockAnimationController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ValueAnimator alphaAnimator;
    public KeyguardViewMediatorHelperImpl$setupLocked$5 callback;
    public AnimatorSet cannedAnimatorSet;
    public final Lazy centralSurfacesLazy;
    public final Context context;
    public SurfaceControl curLeash;
    public float curLeashAlpha;
    public float curLeashHeight;
    public float curLeashScale;
    public float curLeashWidth;
    public SurfaceControl.Transaction curTransaction;
    public boolean forceEnded;
    public int frameUpdatedCount;
    public final InteractionJankMonitor jankMonitor;
    public JankMonitorContext jankMonitorContext;
    public final Lazy keyguardSurfaceControllerLazy;
    public final KeyguardViewController keyguardViewController;
    public final Lazy keyguardViewMediatorLazy;
    public final Executor mainExecutor;
    public final Lazy panelExpansionStateManagerLazy;
    public final C1467x6fa9398a panelStateListener;
    public float reqLeashAlpha;
    public float reqLeashScale;
    public final ValueAnimator scaleAnimator;
    public final SettingsHelper settingsHelper;
    public int skipFrameCount;
    public final Matrix surfaceBehindMatrix;
    public IRemoteAnimationFinishedCallback surfaceBehindRemoteAnimationFinishedCallback;
    public final float[] tmpFloat9;
    public String traceTag;
    public final Executor unlockAnimationExecutor;
    public final KeyguardWallpaperController wallpaperController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$panelStateListener$1] */
    public KeyguardSecLegacyUnlockAnimationControllerImpl(Context context, Executor executor, Executor executor2, KeyguardStateController keyguardStateController, Lazy lazy, KeyguardViewController keyguardViewController, FeatureFlags featureFlags, Lazy lazy2, SysuiStatusBarStateController sysuiStatusBarStateController, NotificationShadeWindowController notificationShadeWindowController, InteractionJankMonitor interactionJankMonitor, Lazy lazy3, Lazy lazy4, Lazy lazy5, PowerManager powerManager, WallpaperManager wallpaperManager, SettingsHelper settingsHelper, KeyguardWallpaperController keyguardWallpaperController) {
        super(context, keyguardStateController, lazy, keyguardViewController, featureFlags, lazy2, sysuiStatusBarStateController, notificationShadeWindowController, powerManager, wallpaperManager);
        this.context = context;
        this.mainExecutor = executor;
        this.unlockAnimationExecutor = executor2;
        this.keyguardViewMediatorLazy = lazy;
        this.keyguardViewController = keyguardViewController;
        this.jankMonitor = interactionJankMonitor;
        this.centralSurfacesLazy = lazy3;
        this.keyguardSurfaceControllerLazy = lazy4;
        this.panelExpansionStateManagerLazy = lazy5;
        this.settingsHelper = settingsHelper;
        this.wallpaperController = keyguardWallpaperController;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 0.5f);
        this.scaleAnimator = ofFloat;
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.alphaAnimator = ofFloat2;
        this.surfaceBehindMatrix = new Matrix();
        this.tmpFloat9 = new float[9];
        this.panelStateListener = new ShadeStateListener() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$panelStateListener$1
            @Override // com.android.systemui.shade.ShadeStateListener
            public final void onPanelStateChanged(int i) {
                int i2 = KeyguardSecLegacyUnlockAnimationControllerImpl.$r8$clinit;
                final KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                keyguardSecLegacyUnlockAnimationControllerImpl.getClass();
                if (i != 0) {
                    Log.m138d("KeyguardUnlock", "onPanelStateChanged " + i);
                    keyguardSecLegacyUnlockAnimationControllerImpl.unlockAnimationExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$onPanelStateChanged$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl2 = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                            keyguardSecLegacyUnlockAnimationControllerImpl2.forceEnded = true;
                            AnimatorSet animatorSet = keyguardSecLegacyUnlockAnimationControllerImpl2.cannedAnimatorSet;
                            if (animatorSet != null) {
                                animatorSet.end();
                            }
                        }
                    });
                    KeyguardSurfaceControllerImpl keyguardSurfaceControllerImpl = (KeyguardSurfaceControllerImpl) ((KeyguardSurfaceController) keyguardSecLegacyUnlockAnimationControllerImpl.keyguardSurfaceControllerLazy.get());
                    keyguardSurfaceControllerImpl.internalRestoreKeyguardSurfaceIfVisible(((ViewRootImpl) keyguardSurfaceControllerImpl.viewRootImpl$delegate.getValue()).getView().getVisibility() == 0);
                    keyguardSecLegacyUnlockAnimationControllerImpl.keyguardViewController.getViewRootImpl().getView().post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$onPanelStateChanged$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            ShadeExpansionStateManager shadeExpansionStateManager = (ShadeExpansionStateManager) KeyguardSecLegacyUnlockAnimationControllerImpl.this.panelExpansionStateManagerLazy.get();
                            shadeExpansionStateManager.stateListeners.remove(KeyguardSecLegacyUnlockAnimationControllerImpl.this.panelStateListener);
                        }
                    });
                }
            }
        };
        this.reqLeashAlpha = -1.0f;
        this.reqLeashScale = -1.0f;
        this.curLeashAlpha = -1.0f;
        this.curLeashScale = -1.0f;
        ofFloat.setInterpolator(KeyguardSecLegacyUnlockAnimationControllerImplKt.CUSTOM_INTERPOLATOR);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                SurfaceControl surfaceControl = keyguardSecLegacyUnlockAnimationControllerImpl.curLeash;
                if (surfaceControl == null || keyguardSecLegacyUnlockAnimationControllerImpl.curTransaction == null) {
                    Log.m142w("KeyguardUnlock", "updateLeashScale " + surfaceControl + " " + keyguardSecLegacyUnlockAnimationControllerImpl.curTransaction);
                } else if (surfaceControl.isValid()) {
                    keyguardSecLegacyUnlockAnimationControllerImpl.reqLeashScale = 1.5f - MathUtils.clamp(floatValue, 0.0f, 0.5f);
                } else {
                    Log.m142w("KeyguardUnlock", "invalid leash");
                }
                KeyguardSecLegacyUnlockAnimationControllerImpl.this.applyTransaction();
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                Log.m138d("KeyguardUnlock", "onAnimationCancel");
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                int i = keyguardSecLegacyUnlockAnimationControllerImpl.frameUpdatedCount;
                int i2 = keyguardSecLegacyUnlockAnimationControllerImpl.skipFrameCount;
                boolean z = keyguardSecLegacyUnlockAnimationControllerImpl.forceEnded;
                StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("onAnimationEnd frameUpdatedCount=", i, " skip=", i2, " forceEnded=");
                m45m.append(z);
                Log.m138d("KeyguardUnlock", m45m.toString());
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
                                    Log.m138d("KeyguardUnlock", "IRemoteAnimationFinishedCallback#onAnimationFinished");
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl4 = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                                keyguardSecLegacyUnlockAnimationControllerImpl4.surfaceBehindRemoteAnimationFinishedCallback = null;
                                ShadeExpansionStateManager shadeExpansionStateManager = (ShadeExpansionStateManager) keyguardSecLegacyUnlockAnimationControllerImpl4.panelExpansionStateManagerLazy.get();
                                shadeExpansionStateManager.stateListeners.remove(KeyguardSecLegacyUnlockAnimationControllerImpl.this.panelStateListener);
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
                Log.m138d("KeyguardUnlock", "onAnimationStart " + ((animatorSet == null || (childAnimations2 = animatorSet.getChildAnimations()) == null) ? null : Integer.valueOf(childAnimations2.size())));
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl2 = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                SurfaceControl surfaceControl = keyguardSecLegacyUnlockAnimationControllerImpl2.keyguardViewController.getViewRootImpl().getSurfaceControl();
                if (keyguardSecLegacyUnlockAnimationControllerImpl2.curTransaction != null && surfaceControl.isValid()) {
                    long j = surfaceControl.mNativeObject;
                    SurfaceControl surfaceControl2 = LsRune.SECURITY_BOUNCER_WINDOW ? ((CentralSurfacesImpl) ((CentralSurfaces) keyguardSecLegacyUnlockAnimationControllerImpl2.centralSurfacesLazy.get())).mBouncerContainer.getViewRootImpl().getSurfaceControl() : null;
                    SurfaceControl.Transaction transaction = keyguardSecLegacyUnlockAnimationControllerImpl2.curTransaction;
                    if (transaction != null) {
                        if (surfaceControl.isValid()) {
                            try {
                                transaction.setAlpha(surfaceControl, 0.01f);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                                Log.m138d("KeyguardUnlock", "previousSurface : " + surfaceControl + ", id : " + Long.toHexString(j) + ", currentSurface : " + keyguardSecLegacyUnlockAnimationControllerImpl2.keyguardViewController.getViewRootImpl().getSurfaceControl() + ", id : " + Long.toHexString(keyguardSecLegacyUnlockAnimationControllerImpl2.keyguardViewController.getViewRootImpl().getSurfaceControl().mNativeObject));
                            }
                        }
                        if (surfaceControl2 != null && surfaceControl2.isValid()) {
                            transaction.setAlpha(surfaceControl2, 0.0f);
                        }
                        transaction.apply();
                    }
                    if (((KeyguardViewMediator) keyguardSecLegacyUnlockAnimationControllerImpl2.keyguardViewMediatorLazy.get()).getViewMediatorCallback().isScreenOn()) {
                        keyguardSecLegacyUnlockAnimationControllerImpl2.wallpaperController.hideLockOnlyLiveWallpaperImmediately();
                    }
                }
                AnimatorSet animatorSet2 = keyguardSecLegacyUnlockAnimationControllerImpl2.cannedAnimatorSet;
                if (animatorSet2 != null && (childAnimations = animatorSet2.getChildAnimations()) != null) {
                    num = Integer.valueOf(childAnimations.size());
                }
                if (num != null && num.intValue() == 1) {
                    keyguardSecLegacyUnlockAnimationControllerImpl2.updateLeashAlpha(1.0f);
                    keyguardSecLegacyUnlockAnimationControllerImpl2.applyTransaction();
                }
            }
        });
        ofFloat2.setInterpolator(KeyguardSecLegacyUnlockAnimationControllerImplKt.SINE_IN_OUT_33);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$2$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                float floatValue = (((Float) valueAnimator.getAnimatedValue()).floatValue() / 2) + 0.5f;
                int i = KeyguardSecLegacyUnlockAnimationControllerImpl.$r8$clinit;
                keyguardSecLegacyUnlockAnimationControllerImpl.updateLeashAlpha(floatValue);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void applyTransaction() {
        boolean z;
        float f;
        final SurfaceControl.Transaction transaction = this.curTransaction;
        if (transaction != null) {
            float f2 = this.reqLeashAlpha;
            boolean z2 = false;
            if (!(f2 == -1.0f)) {
                if (!(f2 == this.curLeashAlpha)) {
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
                    if (!(f != -1.0f)) {
                        if (!(f == this.curLeashScale)) {
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
            }
            z = false;
            f = this.reqLeashScale;
            if (!(f != -1.0f)) {
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

    @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController
    public final long getUnlockAnimationDuration() {
        return (long) (this.settingsHelper.getTransitionAnimationScale() * 250);
    }

    @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController
    public final void notifyFinishedKeyguardExitAnimation(boolean z) {
        Log.m138d("KeyguardUnlock", "notifyFinishedKeyguardExitAnimation");
        this.surfaceBehindRemoteAnimationTargets = null;
        super.notifyFinishedKeyguardExitAnimation(z);
    }

    @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController
    public final void notifyStartSurfaceBehindRemoteAnimation(RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, long j, boolean z) {
        this.surfaceBehindRemoteAnimationTargets = remoteAnimationTargetArr;
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
        this.playingCannedUnlockAnimation = false;
        RemoteAnimationTarget[] remoteAnimationTargetArr = this.surfaceBehindRemoteAnimationTargets;
        final RemoteAnimationTarget remoteAnimationTarget = remoteAnimationTargetArr != null ? remoteAnimationTargetArr[0] : null;
        KeyguardViewMediatorHelperImpl$setupLocked$5 keyguardViewMediatorHelperImpl$setupLocked$5 = this.callback;
        if (keyguardViewMediatorHelperImpl$setupLocked$5 != null) {
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = keyguardViewMediatorHelperImpl$setupLocked$5.this$0;
            this.surfaceBehindRemoteAnimationFinishedCallback = (IRemoteAnimationFinishedCallback) keyguardViewMediatorHelperImpl.getViewMediatorProvider().getSurfaceBehindRemoteAnimationFinishedCallback.invoke();
            keyguardViewMediatorHelperImpl.getViewMediatorProvider().resetSurfaceBehindRemoteAnimationFinishedCallback.invoke();
        }
        KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this.keyguardViewMediatorLazy.get();
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = keyguardViewMediator.mHelper;
        String str = LsRune.VALUE_CONFIG_CARRIER_TEXT_POLICY;
        keyguardViewMediatorHelperImpl2.getViewMediatorProvider().adjustStatusBarLocked.invoke();
        keyguardViewMediator.exitKeyguardAndFinishSurfaceBehindRemoteAnimation(false);
        KeyguardSurfaceController.DefaultImpls.setKeyguardSurfaceAppearAmount$default((KeyguardSurfaceController) this.keyguardSurfaceControllerLazy.get());
        ((ShadeExpansionStateManager) this.panelExpansionStateManagerLazy.get()).stateListeners.add(this.panelStateListener);
        this.unlockAnimationExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSecLegacyUnlockAnimationControllerImpl$playCannedUnlockAnimation$3
            @Override // java.lang.Runnable
            public final void run() {
                ActivityManager.RunningTaskInfo runningTaskInfo;
                ComponentName componentName;
                SemPerfManager.sendCommandToSsrm("TASK_BOOST", Process.myTid() + "/300");
                AnimatorSet animatorSet = KeyguardSecLegacyUnlockAnimationControllerImpl.this.cannedAnimatorSet;
                if (animatorSet != null) {
                    animatorSet.cancel();
                }
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                AnimatorSet animatorSet2 = new AnimatorSet();
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl2 = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                RemoteAnimationTarget remoteAnimationTarget2 = remoteAnimationTarget;
                animatorSet2.setDuration(keyguardSecLegacyUnlockAnimationControllerImpl2.getUnlockAnimationDuration());
                KeyguardSecLegacyUnlockAnimationControllerImpl.JankMonitorContext jankMonitorContext = null;
                if (Intrinsics.areEqual((remoteAnimationTarget2 == null || (runningTaskInfo = remoteAnimationTarget2.taskInfo) == null || (componentName = runningTaskInfo.topActivity) == null) ? null : componentName.getClassName(), "com.sec.android.app.launcher.Launcher")) {
                    animatorSet2.playTogether(keyguardSecLegacyUnlockAnimationControllerImpl2.alphaAnimator, keyguardSecLegacyUnlockAnimationControllerImpl2.scaleAnimator);
                } else {
                    animatorSet2.play(keyguardSecLegacyUnlockAnimationControllerImpl2.scaleAnimator);
                }
                keyguardSecLegacyUnlockAnimationControllerImpl.cannedAnimatorSet = animatorSet2;
                KeyguardSecLegacyUnlockAnimationControllerImpl.this.curTransaction = new SurfaceControl.Transaction();
                RemoteAnimationTarget remoteAnimationTarget3 = remoteAnimationTarget;
                if (remoteAnimationTarget3 != null) {
                    KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl3 = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                    SurfaceControl surfaceControl = remoteAnimationTarget3.leash;
                    keyguardSecLegacyUnlockAnimationControllerImpl3.curLeash = surfaceControl;
                    keyguardSecLegacyUnlockAnimationControllerImpl3.traceTag = surfaceControl.toString();
                    Rect rect = remoteAnimationTarget3.screenSpaceBounds;
                    keyguardSecLegacyUnlockAnimationControllerImpl3.curLeashWidth = rect.width() / 2.0f;
                    keyguardSecLegacyUnlockAnimationControllerImpl3.curLeashHeight = rect.height() / 2.0f;
                }
                KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl4 = KeyguardSecLegacyUnlockAnimationControllerImpl.this;
                keyguardSecLegacyUnlockAnimationControllerImpl4.skipFrameCount = 0;
                keyguardSecLegacyUnlockAnimationControllerImpl4.frameUpdatedCount = 0;
                keyguardSecLegacyUnlockAnimationControllerImpl4.forceEnded = false;
                keyguardSecLegacyUnlockAnimationControllerImpl4.reqLeashAlpha = -1.0f;
                keyguardSecLegacyUnlockAnimationControllerImpl4.reqLeashScale = -1.0f;
                keyguardSecLegacyUnlockAnimationControllerImpl4.curLeashAlpha = -1.0f;
                keyguardSecLegacyUnlockAnimationControllerImpl4.curLeashScale = -1.0f;
                InteractionJankMonitor interactionJankMonitor = keyguardSecLegacyUnlockAnimationControllerImpl4.jankMonitor;
                KeyguardSecLegacyUnlockAnimationControllerImpl.JankMonitorContext jankMonitorContext2 = keyguardSecLegacyUnlockAnimationControllerImpl4.jankMonitorContext;
                if (jankMonitorContext2 == null) {
                    Looper myLooper = Looper.myLooper();
                    if (myLooper != null) {
                        jankMonitorContext = new KeyguardSecLegacyUnlockAnimationControllerImpl.JankMonitorContext(keyguardSecLegacyUnlockAnimationControllerImpl4.context, new Handler(myLooper));
                        keyguardSecLegacyUnlockAnimationControllerImpl4.jankMonitorContext = jankMonitorContext;
                    }
                } else {
                    jankMonitorContext = jankMonitorContext2;
                }
                interactionJankMonitor.begin(InteractionJankMonitor.Configuration.Builder.withSurface(29, jankMonitorContext, keyguardSecLegacyUnlockAnimationControllerImpl4.curLeash).setTag("KeyguardUnlock"));
                AnimatorSet animatorSet3 = KeyguardSecLegacyUnlockAnimationControllerImpl.this.cannedAnimatorSet;
                if (animatorSet3 != null) {
                    animatorSet3.start();
                }
            }
        });
    }

    @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController
    public final void setCallback(KeyguardViewMediatorHelperImpl$setupLocked$5 keyguardViewMediatorHelperImpl$setupLocked$5) {
        this.callback = keyguardViewMediatorHelperImpl$setupLocked$5;
    }

    public final void trace(Runnable runnable, String str) {
        String m15m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(this.traceTag, "#", str);
        if (m15m.length() > 127) {
            m15m = m15m.substring(0, 126);
        }
        Trace.beginSection(m15m);
        runnable.run();
        Trace.endSection();
    }

    public final void updateLeashAlpha(float f) {
        SurfaceControl surfaceControl = this.curLeash;
        if (surfaceControl != null && this.curTransaction != null) {
            Intrinsics.checkNotNull(surfaceControl);
            if (surfaceControl.isValid()) {
                this.reqLeashAlpha = f;
                return;
            } else {
                Log.m142w("KeyguardUnlock", "invalid leash");
                return;
            }
        }
        Log.m142w("KeyguardUnlock", "updateLeashAlpha " + surfaceControl + " " + this.curTransaction);
    }

    @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController, com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public final void onKeyguardDismissAmountChanged() {
    }

    @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController
    public final void setSurfaceBehindAppearAmount(float f, boolean z) {
    }
}
