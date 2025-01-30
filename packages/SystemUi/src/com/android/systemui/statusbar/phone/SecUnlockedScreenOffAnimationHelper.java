package com.android.systemui.statusbar.phone;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.display.IDisplayManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.view.WindowManager;
import com.android.keyguard.AbstractC0731x5bb8a836;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.aod.AODTouchModeManager;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.facewidget.plugin.FaceWidgetContainerWrapper;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor;
import com.android.systemui.keyguard.Log;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.plugins.aod.PluginAOD;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.hardware.display.IRefreshRateToken;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import dagger.Lazy;
import java.util.Iterator;
import java.util.List;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecUnlockedScreenOffAnimationHelper {
    public final AODAmbientWallpaperHelper aodAmbientWallpaperHelper;
    public final SecUnlockedScreenOffAnimationHelper$aodShowStateCallback$1 aodShowStateCallback;
    public final AODTouchModeManager aodTouchModeManager;
    public final CoroutineDispatcher backgroundDispatcher;
    public CentralSurfaces centralSurfaces;
    public Function0 clearDecidedToAnimateGoingToSleep;
    public final List conditions;
    public final Context context;
    public int curRotation;
    public boolean deviceInteractive;
    public final Lazy dozeParameters;
    public Function0 isFalseDecidedToAnimateGoingToSleep;
    public boolean isPanelOpenedOnGoingToSleep;
    public Job job;
    public final KeyguardStateController keyguardStateController;
    public final Lazy keyguardUpdateMonitorLazy;
    public final Lazy keyguardViewMediatorLazy;
    public final KeyguardVisibilityMonitor keyguardVisibilityMonitor;
    public int lastReason;
    public boolean lastShouldPlay;
    public final CoroutineDispatcher mainDispatcher;
    public final Handler mainHandler;
    public final boolean moreLog;
    public boolean needUpdateWallpaperVisibility;
    public final Lazy pluginAODManagerLazy;
    public final Lazy pluginFaceWidgetManagerLazy;
    public final Lazy pluginLockMediatorLazy;
    public final Lazy pluginLockStarManagerLazy;
    public final List reasonLog;
    public IRefreshRateToken refreshRateToken;
    public final CoroutineScope scope;
    public final ScreenLifecycle screenLifecycle;
    public SecUnlockedScreenOffCapturedView secUnlockedScreenOffCapturedView;
    public final SettingsHelper settingsHelper;
    public boolean skipAnimationInOthers;
    public final Lazy statusBarKeyguardViewManagerLazy;
    public final StatusBarStateControllerImpl statusBarStateControllerImpl;
    public final kotlin.Lazy token$delegate;
    public final Lazy unlockedScreenOffAnimationController;
    public final SecUnlockedScreenOffAnimationHelper$special$$inlined$Runnable$1 updateWallpaperVisibilityRunnable;
    public final WakefulnessLifecycle wakefulnessLifecycle;
    public final WallpaperManager wallpaperManager;
    public final WindowManager windowManager;
    public final kotlin.Lazy displayManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$displayManager$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
        }
    });
    public final kotlin.Lazy maxRefreshRate$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$maxRefreshRate$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Integer.valueOf(Intrinsics.areEqual(Build.TYPE, "user") ? 60 : SystemProperties.getInt("debug.aod.screen_off_animation_refresh_rate", 60));
        }
    });

    /* JADX WARN: Type inference failed for: r1v35, types: [com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$special$$inlined$Runnable$1] */
    /* JADX WARN: Type inference failed for: r1v36, types: [com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$aodShowStateCallback$1] */
    public SecUnlockedScreenOffAnimationHelper(AODAmbientWallpaperHelper aODAmbientWallpaperHelper, Lazy lazy, Lazy lazy2, Lazy lazy3, SettingsHelper settingsHelper, StatusBarStateControllerImpl statusBarStateControllerImpl, Context context, KeyguardStateController keyguardStateController, KeyguardVisibilityMonitor keyguardVisibilityMonitor, WakefulnessLifecycle wakefulnessLifecycle, Lazy lazy4, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, Lazy lazy5, Handler handler, WindowManager windowManager, WallpaperManager wallpaperManager, Lazy lazy6, Lazy lazy7, ScreenLifecycle screenLifecycle, Lazy lazy8, AODTouchModeManager aODTouchModeManager, Lazy lazy9) {
        this.aodAmbientWallpaperHelper = aODAmbientWallpaperHelper;
        this.pluginAODManagerLazy = lazy;
        this.dozeParameters = lazy2;
        this.unlockedScreenOffAnimationController = lazy3;
        this.settingsHelper = settingsHelper;
        this.statusBarStateControllerImpl = statusBarStateControllerImpl;
        this.context = context;
        this.keyguardStateController = keyguardStateController;
        this.keyguardVisibilityMonitor = keyguardVisibilityMonitor;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.keyguardUpdateMonitorLazy = lazy4;
        this.scope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundDispatcher = coroutineDispatcher2;
        this.keyguardViewMediatorLazy = lazy5;
        this.mainHandler = handler;
        this.windowManager = windowManager;
        this.wallpaperManager = wallpaperManager;
        this.pluginFaceWidgetManagerLazy = lazy6;
        this.statusBarKeyguardViewManagerLazy = lazy7;
        this.screenLifecycle = screenLifecycle;
        this.pluginLockStarManagerLazy = lazy8;
        this.aodTouchModeManager = aODTouchModeManager;
        this.pluginLockMediatorLazy = lazy9;
        this.moreLog = !Intrinsics.areEqual("user", Build.TYPE) || Debug.semIsProductDev() || LogUtil.isDebugLevelMid || LogUtil.isDebugLevelHigh;
        this.token$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$token$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new Binder();
            }
        });
        this.curRotation = -1;
        this.deviceInteractive = true;
        this.conditions = CollectionsKt__CollectionsKt.listOf(new Function0() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$conditions$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(!SecUnlockedScreenOffAnimationHelper.this.aodAmbientWallpaperHelper.isAODFullScreenMode());
            }
        }, new Function0() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$conditions$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(!((KeyguardUpdateMonitor) SecUnlockedScreenOffAnimationHelper.this.keyguardUpdateMonitorLazy.get()).mDeviceProvisioned);
            }
        }, new Function0() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$conditions$3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(!((DozeParameters) SecUnlockedScreenOffAnimationHelper.this.dozeParameters.get()).canControlUnlockedScreenOff());
            }
        }, new Function0() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$conditions$4
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Function0 function0 = SecUnlockedScreenOffAnimationHelper.this.isFalseDecidedToAnimateGoingToSleep;
                if (function0 == null) {
                    function0 = null;
                }
                return (Boolean) function0.invoke();
            }
        }, new Function0() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$conditions$5
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(SecUnlockedScreenOffAnimationHelper.this.settingsHelper.isAnimationRemoved());
            }
        }, new Function0() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$conditions$6
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(SecUnlockedScreenOffAnimationHelper.this.statusBarStateControllerImpl.mState != 0);
            }
        }, new Function0() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$conditions$7
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = SecUnlockedScreenOffAnimationHelper.this;
                CentralSurfaces centralSurfaces = secUnlockedScreenOffAnimationHelper.centralSurfaces;
                return Boolean.valueOf((centralSurfaces == null || ((NotificationPanelViewController) ((CentralSurfacesImpl) centralSurfaces).mShadeSurface).mPanelExpanded) && !((UnlockedScreenOffAnimationController) secUnlockedScreenOffAnimationHelper.unlockedScreenOffAnimationController.get()).isAnimationPlaying());
            }
        }, new Function0() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$conditions$8
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = SecUnlockedScreenOffAnimationHelper.this;
                int rotation = secUnlockedScreenOffAnimationHelper.context.getDisplay().getRotation();
                secUnlockedScreenOffAnimationHelper.curRotation = rotation;
                AbstractC0731x5bb8a836.m72m("getRotation: curRotation=", rotation, ", settingsHelper.isRotationLocked=", secUnlockedScreenOffAnimationHelper.settingsHelper.isRotationLocked(), "UnlockedScreenOffAnimation");
                int i = secUnlockedScreenOffAnimationHelper.curRotation;
                boolean isLockScreenRotationAllowed = SecUnlockedScreenOffAnimationHelper.this.settingsHelper.isLockScreenRotationAllowed();
                boolean isRotationLocked = SecUnlockedScreenOffAnimationHelper.this.settingsHelper.isRotationLocked();
                return Boolean.valueOf(((!isLockScreenRotationAllowed || isRotationLocked) && i != 0) || (isLockScreenRotationAllowed && i != 0 && (WallpaperUtils.isVideoWallpaper() || ((PluginLockMediatorImpl) ((PluginLockMediator) SecUnlockedScreenOffAnimationHelper.this.pluginLockMediatorLazy.get())).isRotateMenuHide())) || (!isRotationLocked && isLockScreenRotationAllowed && i == 2));
            }
        }, new Function0() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$conditions$9
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(((KeyguardUpdateMonitor) SecUnlockedScreenOffAnimationHelper.this.keyguardUpdateMonitorLazy.get()).isCoverClosed());
            }
        }, new Function0() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$conditions$10
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(SecUnlockedScreenOffAnimationHelper.this.isPanelOpenedOnGoingToSleep);
            }
        }, new Function0() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$conditions$11
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(SecUnlockedScreenOffAnimationHelper.this.settingsHelper.isUltraPowerSavingMode());
            }
        }, new Function0() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$conditions$12
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(((PluginAODManager) SecUnlockedScreenOffAnimationHelper.this.pluginAODManagerLazy.get()).mStartedByFolderClosed);
            }
        });
        this.reasonLog = CollectionsKt__CollectionsKt.listOf("not AOD fullscreen", "not provisioned", "canControlUnlockedScreenOff is false", "decidedToAnimateGoingToSleep is false", "animation is disabled", "not SHADE state", "not initialized or panel is expanded", "rotation condition is not matched", "cover closed", "panel is already opened", "ultra power saving", "AOD started by Fold Close");
        this.lastReason = -1;
        this.updateWallpaperVisibilityRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$special$$inlined$Runnable$1
            @Override // java.lang.Runnable
            public final void run() {
                if (((KeyguardViewMediator) SecUnlockedScreenOffAnimationHelper.this.keyguardViewMediatorLazy.get()).getViewMediatorCallback().isScreenOn()) {
                    SecUnlockedScreenOffAnimationHelper.this.getClass();
                    SecUnlockedScreenOffAnimationHelper.logD("updateWallpaperVisibilityRunnable do not run after onStartedWakingUp");
                    return;
                }
                SecUnlockedScreenOffAnimationHelper.logD("updateWallpaperVisibilityRunnable called needUpdateWallpaperVisibility=" + SecUnlockedScreenOffAnimationHelper.this.needUpdateWallpaperVisibility);
                SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = SecUnlockedScreenOffAnimationHelper.this;
                if (secUnlockedScreenOffAnimationHelper.needUpdateWallpaperVisibility) {
                    return;
                }
                secUnlockedScreenOffAnimationHelper.needUpdateWallpaperVisibility = true;
                secUnlockedScreenOffAnimationHelper.updateWallpaperVisibility(false);
            }
        };
        this.aodShowStateCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$aodShowStateCallback$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (uri == null || !Intrinsics.areEqual(Settings.System.getUriFor("aod_show_state"), uri)) {
                    return;
                }
                SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = SecUnlockedScreenOffAnimationHelper.this;
                boolean isAODShown = secUnlockedScreenOffAnimationHelper.settingsHelper.isAODShown();
                SettingsHelper settingsHelper2 = secUnlockedScreenOffAnimationHelper.settingsHelper;
                KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("aodShowStateCallback.isAODShown=", isAODShown, ", settingsHelper.isAODShown=", settingsHelper2.isAODShown(), ", needUpdateWallpaperVisibility="), secUnlockedScreenOffAnimationHelper.needUpdateWallpaperVisibility, " deviceInteractive=", secUnlockedScreenOffAnimationHelper.deviceInteractive, "UnlockedScreenOffAnimation");
                if (!secUnlockedScreenOffAnimationHelper.deviceInteractive && secUnlockedScreenOffAnimationHelper.aodAmbientWallpaperHelper.isAODFullScreenMode() && settingsHelper2.isAODShown()) {
                    CentralSurfaces centralSurfaces = secUnlockedScreenOffAnimationHelper.centralSurfaces;
                    if (centralSurfaces == null) {
                        centralSurfaces = null;
                    }
                    ((CentralSurfacesImpl) centralSurfaces).mLightRevealScrim.setAlpha(0.6f);
                    if (secUnlockedScreenOffAnimationHelper.needUpdateWallpaperVisibility) {
                        return;
                    }
                    secUnlockedScreenOffAnimationHelper.setCapturedViewVisibility(false, null);
                    secUnlockedScreenOffAnimationHelper.needUpdateWallpaperVisibility = true;
                    secUnlockedScreenOffAnimationHelper.updateWallpaperVisibility(true ^ settingsHelper2.isAODShown());
                }
            }
        };
    }

    public static void logD(String str) {
        Log.m138d("UnlockedScreenOffAnimation", str);
    }

    public final void onAmountChanged(float f) {
        PluginAOD pluginAOD = ((PluginAODManager) this.pluginAODManagerLazy.get()).mAODPlugin;
        if (pluginAOD != null) {
            pluginAOD.onUnlockedScreenOffAmountChanged(f);
        }
        FaceWidgetContainerWrapper faceWidgetContainerWrapper = ((PluginFaceWidgetManager) this.pluginFaceWidgetManagerLazy.get()).mFaceWidgetContainerWrapper;
        if (faceWidgetContainerWrapper != null) {
            float f2 = 1 - f;
            PluginKeyguardStatusView pluginKeyguardStatusView = faceWidgetContainerWrapper.mPluginKeyguardStatusView;
            if (pluginKeyguardStatusView != null) {
                pluginKeyguardStatusView.setDarkAmount(f2);
            }
        }
        PluginLockStarManager pluginLockStarManager = (PluginLockStarManager) this.pluginLockStarManagerLazy.get();
        Float valueOf = Float.valueOf(1 - f);
        PluginLockStar pluginLockStar = pluginLockStarManager.mPluginLockStar;
        if (pluginLockStar == null) {
            return;
        }
        try {
            pluginLockStar.setDarkAmount(valueOf);
        } catch (Throwable unused) {
        }
    }

    public final void playWallpaperAnimation() {
        BuildersKt.launch$default(this.scope, this.backgroundDispatcher, null, new SecUnlockedScreenOffAnimationHelper$playWallpaperAnimation$1(this, null), 2);
    }

    public final void setCapturedViewVisibility(boolean z, Bitmap bitmap) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("setCapturedViewVisibility: visible=", z, "UnlockedScreenOffAnimation");
        SecUnlockedScreenOffCapturedView secUnlockedScreenOffCapturedView = this.secUnlockedScreenOffCapturedView;
        if (secUnlockedScreenOffCapturedView != null) {
            if (secUnlockedScreenOffCapturedView == null) {
                secUnlockedScreenOffCapturedView = null;
            }
            secUnlockedScreenOffCapturedView.setImageBitmap(bitmap);
            secUnlockedScreenOffCapturedView.setVisibility(z ? 0 : 4);
        }
    }

    public final void setSkipAnimationInOthers(boolean z) {
        if (this.skipAnimationInOthers != z && !z) {
            logD("skipAnimationInOthers false");
        }
        this.skipAnimationInOthers = z;
    }

    public final boolean shouldPlayUnlockedScreenOffAnimation() {
        Iterator it = this.conditions.iterator();
        int i = -1;
        while (it.hasNext()) {
            i++;
            if (((Boolean) ((Function0) it.next()).invoke()).booleanValue()) {
                int i2 = this.lastReason;
                WakefulnessLifecycle wakefulnessLifecycle = this.wakefulnessLifecycle;
                if ((i2 != i && wakefulnessLifecycle.mWakefulness != 0) || this.lastShouldPlay) {
                    logD("shouldPlayUnlockedScreenOffAnimation false / " + (this.moreLog ? (String) CollectionsKt___CollectionsKt.getOrNull(i, this.reasonLog) : AbstractC0000x2c234b15.m0m("reason: ", i)));
                }
                if (this.lastShouldPlay && wakefulnessLifecycle.mWakefulness == 0) {
                    setSkipAnimationInOthers(true);
                    Job job = this.job;
                    if (job != null && job.isActive()) {
                        job.cancel(null);
                    }
                    this.job = null;
                    this.job = BuildersKt.launch$default(this.scope, this.mainDispatcher, null, new C3129x8ca75fa6(this, null), 2);
                }
                this.lastReason = i;
                this.lastShouldPlay = false;
                return false;
            }
        }
        if (!this.lastShouldPlay) {
            logD("shouldPlayUnlockedScreenOffAnimation true");
        }
        this.lastReason = -1;
        this.lastShouldPlay = true;
        return true;
    }

    public final boolean shouldSkipAnimation() {
        return this.lastShouldPlay || this.skipAnimationInOthers;
    }

    public final void updateWallpaperVisibility(final boolean z) {
        boolean isLiveWallpaperEnabled = this.settingsHelper.isLiveWallpaperEnabled();
        KeyguardStateController keyguardStateController = this.keyguardStateController;
        boolean z2 = ((KeyguardStateControllerImpl) keyguardStateController).mShowing;
        boolean z3 = ((KeyguardStateControllerImpl) keyguardStateController).mOccluded;
        StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("updateWallpaperVisibility: wakingUp=", z, ", isLiveWallpaper=", isLiveWallpaperEnabled, ", keyguardShowing=");
        m69m.append(z2);
        m69m.append(", occlude=");
        m69m.append(z3);
        Log.m141i("UnlockedScreenOffAnimation", m69m.toString());
        if (!isLiveWallpaperEnabled && (!z2 || z3)) {
            KeyguardWallpaperController.sController.setKeyguardShowing(!z);
        }
        this.mainHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$updateWallpaperVisibility$1
            @Override // java.lang.Runnable
            public final void run() {
                ((KeyguardViewMediator) SecUnlockedScreenOffAnimationHelper.this.keyguardViewMediatorLazy.get()).setDozing(!z);
            }
        });
    }
}
