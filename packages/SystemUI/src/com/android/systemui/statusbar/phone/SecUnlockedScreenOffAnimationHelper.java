package com.android.systemui.statusbar.phone;

import android.app.WallpaperManager;
import android.content.Context;
import android.hardware.display.IDisplayManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Debug;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.WindowManager;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
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
import com.android.systemui.plugins.aod.PluginAOD;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.SettingsHelper;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecUnlockedScreenOffAnimationHelper {
    public final AODAmbientWallpaperHelper aodAmbientWallpaperHelper;
    private final SettingsHelper.OnChangedCallback aodShowStateCallback;
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
    public final Lazy keyguardUpdateMonitorLazy;
    public final Lazy keyguardViewMediatorLazy;
    public final KeyguardVisibilityMonitor keyguardVisibilityMonitor;
    public int lastReason;
    public boolean lastShouldPlay;
    public final CoroutineDispatcher mainDispatcher;
    public final Handler mainHandler;
    public final boolean moreLog;
    public boolean needUpdateSetLockScreenShown;
    public final Lazy pluginAODManagerLazy;
    public final Lazy pluginFaceWidgetManagerLazy;
    public final Lazy pluginLockMediatorLazy;
    public final Lazy pluginLockStarManagerLazy;
    public final List reasonLog;
    public IRefreshRateToken refreshRateToken;
    public final CoroutineScope scope;
    public final ScreenLifecycle screenLifecycle;
    private final SettingsHelper settingsHelper;
    public boolean skipAnimationInOthers;
    public final Lazy statusBarKeyguardViewManagerLazy;
    public final StatusBarStateControllerImpl statusBarStateControllerImpl;
    public final kotlin.Lazy token$delegate;
    public final Lazy unlockedScreenOffAnimationController;
    public final SecUnlockedScreenOffAnimationHelper$special$$inlined$Runnable$1 updateSetLockScreenShownRunnable;
    public final WakefulnessLifecycle wakefulnessLifecycle;
    public final WallpaperManager wallpaperManager;
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

    /* JADX WARN: Type inference failed for: r1v33, types: [com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$special$$inlined$Runnable$1] */
    public SecUnlockedScreenOffAnimationHelper(AODAmbientWallpaperHelper aODAmbientWallpaperHelper, Lazy lazy, Lazy lazy2, Lazy lazy3, SettingsHelper settingsHelper, StatusBarStateControllerImpl statusBarStateControllerImpl, Context context, KeyguardStateController keyguardStateController, KeyguardVisibilityMonitor keyguardVisibilityMonitor, WakefulnessLifecycle wakefulnessLifecycle, Lazy lazy4, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, Lazy lazy5, Handler handler, WindowManager windowManager, WallpaperManager wallpaperManager, Lazy lazy6, Lazy lazy7, ScreenLifecycle screenLifecycle, Lazy lazy8, AODTouchModeManager aODTouchModeManager, Lazy lazy9) {
        this.aodAmbientWallpaperHelper = aODAmbientWallpaperHelper;
        this.pluginAODManagerLazy = lazy;
        this.dozeParameters = lazy2;
        this.unlockedScreenOffAnimationController = lazy3;
        this.settingsHelper = settingsHelper;
        this.statusBarStateControllerImpl = statusBarStateControllerImpl;
        this.context = context;
        this.keyguardVisibilityMonitor = keyguardVisibilityMonitor;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.keyguardUpdateMonitorLazy = lazy4;
        this.scope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundDispatcher = coroutineDispatcher2;
        this.keyguardViewMediatorLazy = lazy5;
        this.mainHandler = handler;
        this.wallpaperManager = wallpaperManager;
        this.pluginFaceWidgetManagerLazy = lazy6;
        this.statusBarKeyguardViewManagerLazy = lazy7;
        this.screenLifecycle = screenLifecycle;
        this.pluginLockStarManagerLazy = lazy8;
        this.aodTouchModeManager = aODTouchModeManager;
        this.pluginLockMediatorLazy = lazy9;
        this.moreLog = !"user".equals(Build.TYPE) || Debug.semIsProductDev() || LogUtil.isDebugLevelMid() || LogUtil.isDebugLevelHigh();
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
                SettingsHelper settingsHelper2;
                settingsHelper2 = SecUnlockedScreenOffAnimationHelper.this.settingsHelper;
                return Boolean.valueOf(settingsHelper2.isAnimationRemoved());
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
                return Boolean.valueOf((secUnlockedScreenOffAnimationHelper.centralSurfaces == null || secUnlockedScreenOffAnimationHelper.statusBarStateControllerImpl.mIsExpanded) && !((UnlockedScreenOffAnimationController) secUnlockedScreenOffAnimationHelper.unlockedScreenOffAnimationController.get()).isAnimationPlaying());
            }
        }, new Function0() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$conditions$8
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SettingsHelper settingsHelper2;
                SettingsHelper settingsHelper3;
                int access$getRotation = SecUnlockedScreenOffAnimationHelper.access$getRotation(SecUnlockedScreenOffAnimationHelper.this);
                settingsHelper2 = SecUnlockedScreenOffAnimationHelper.this.settingsHelper;
                boolean isLockScreenRotationAllowed = settingsHelper2.isLockScreenRotationAllowed();
                settingsHelper3 = SecUnlockedScreenOffAnimationHelper.this.settingsHelper;
                boolean isRotationLocked = settingsHelper3.isRotationLocked();
                return Boolean.valueOf(((!isLockScreenRotationAllowed || isRotationLocked) && access$getRotation != 0) || (isLockScreenRotationAllowed && access$getRotation != 0 && (WallpaperUtils.isVideoWallpaper(SecUnlockedScreenOffAnimationHelper.this.context) || ((PluginLockMediator) SecUnlockedScreenOffAnimationHelper.this.pluginLockMediatorLazy.get()).isRotateMenuHide())) || (!isRotationLocked && isLockScreenRotationAllowed && access$getRotation == 2));
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
                SettingsHelper settingsHelper2;
                settingsHelper2 = SecUnlockedScreenOffAnimationHelper.this.settingsHelper;
                return Boolean.valueOf(settingsHelper2.isUltraPowerSavingMode());
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
        this.updateSetLockScreenShownRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$special$$inlined$Runnable$1
            @Override // java.lang.Runnable
            public final void run() {
                if (((KeyguardViewMediator) SecUnlockedScreenOffAnimationHelper.this.keyguardViewMediatorLazy.get()).getViewMediatorCallback().isScreenOn()) {
                    SecUnlockedScreenOffAnimationHelper.this.getClass();
                    SecUnlockedScreenOffAnimationHelper.logD("updateSetLockScreenShownRunnable do not run after onStartedWakingUp");
                    return;
                }
                SecUnlockedScreenOffAnimationHelper.logD("updateSetLockScreenShownRunnable called needUpdateSetLockScreenShown=" + SecUnlockedScreenOffAnimationHelper.this.needUpdateSetLockScreenShown);
                SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = SecUnlockedScreenOffAnimationHelper.this;
                if (secUnlockedScreenOffAnimationHelper.needUpdateSetLockScreenShown) {
                    return;
                }
                secUnlockedScreenOffAnimationHelper.needUpdateSetLockScreenShown = true;
                secUnlockedScreenOffAnimationHelper.updateSetLockScreenShown(false);
            }
        };
        this.aodShowStateCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$aodShowStateCallback$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                SettingsHelper settingsHelper2;
                SettingsHelper settingsHelper3;
                SettingsHelper settingsHelper4;
                if (uri == null || !Intrinsics.areEqual(Settings.System.getUriFor(SettingsHelper.INDEX_AOD_SHOW_STATE), uri)) {
                    return;
                }
                SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = SecUnlockedScreenOffAnimationHelper.this;
                settingsHelper2 = secUnlockedScreenOffAnimationHelper.settingsHelper;
                boolean isAODShown = settingsHelper2.isAODShown();
                settingsHelper3 = secUnlockedScreenOffAnimationHelper.settingsHelper;
                KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("aodShowStateCallback.isAODShown=", ", settingsHelper.isAODShown=", ", needUpdateSetLockScreenShown=", isAODShown, settingsHelper3.isAODShown()), secUnlockedScreenOffAnimationHelper.needUpdateSetLockScreenShown, " deviceInteractive=", secUnlockedScreenOffAnimationHelper.deviceInteractive, "UnlockedScreenOffAnimation");
                if (secUnlockedScreenOffAnimationHelper.deviceInteractive || !secUnlockedScreenOffAnimationHelper.aodAmbientWallpaperHelper.isAODFullScreenAndShowing()) {
                    return;
                }
                CentralSurfaces centralSurfaces = secUnlockedScreenOffAnimationHelper.centralSurfaces;
                if (centralSurfaces == null) {
                    centralSurfaces = null;
                }
                ((CentralSurfacesImpl) centralSurfaces).mLightRevealScrim.setAlpha(FactoryTest.isFactoryBinary() ? 0.1f : 0.6f);
                if (secUnlockedScreenOffAnimationHelper.needUpdateSetLockScreenShown) {
                    return;
                }
                secUnlockedScreenOffAnimationHelper.needUpdateSetLockScreenShown = true;
                settingsHelper4 = secUnlockedScreenOffAnimationHelper.settingsHelper;
                secUnlockedScreenOffAnimationHelper.updateSetLockScreenShown(true ^ settingsHelper4.isAODShown());
            }
        };
    }

    public static final String access$getReasonLog(SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper, int i) {
        if (i != 7) {
            return (String) CollectionsKt___CollectionsKt.getOrNull(i, secUnlockedScreenOffAnimationHelper.reasonLog);
        }
        return CollectionsKt___CollectionsKt.getOrNull(i, secUnlockedScreenOffAnimationHelper.reasonLog) + " allowRotation=" + secUnlockedScreenOffAnimationHelper.settingsHelper.isLockScreenRotationAllowed() + ", rotationLock=" + secUnlockedScreenOffAnimationHelper.settingsHelper.isRotationLocked() + ", rotation=" + secUnlockedScreenOffAnimationHelper.curRotation;
    }

    public static final int access$getRotation(SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper) {
        int rotation = secUnlockedScreenOffAnimationHelper.context.getDisplay().getRotation();
        secUnlockedScreenOffAnimationHelper.curRotation = rotation;
        KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("getRotation: curRotation=", rotation, ", settingsHelper.isRotationLocked=", secUnlockedScreenOffAnimationHelper.settingsHelper.isRotationLocked(), "UnlockedScreenOffAnimation");
        return secUnlockedScreenOffAnimationHelper.curRotation;
    }

    public static void logD(String str) {
        Log.d("UnlockedScreenOffAnimation", str);
    }

    public final void init(CentralSurfaces centralSurfaces, Function0 function0, Function0 function02) {
        this.centralSurfaces = centralSurfaces;
        this.isFalseDecidedToAnimateGoingToSleep = function0;
        this.clearDecidedToAnimateGoingToSleep = function02;
        this.settingsHelper.registerCallback(this.aodShowStateCallback, Settings.System.getUriFor(SettingsHelper.INDEX_AOD_SHOW_STATE));
        this.wakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$init$1
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedGoingToSleep() {
                SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = SecUnlockedScreenOffAnimationHelper.this;
                secUnlockedScreenOffAnimationHelper.deviceInteractive = false;
                boolean shouldPlayUnlockedScreenOffAnimation = secUnlockedScreenOffAnimationHelper.shouldPlayUnlockedScreenOffAnimation();
                AODAmbientWallpaperHelper aODAmbientWallpaperHelper = secUnlockedScreenOffAnimationHelper.aodAmbientWallpaperHelper;
                boolean isAODFullScreenMode = aODAmbientWallpaperHelper.isAODFullScreenMode();
                String access$getReasonLog = SecUnlockedScreenOffAnimationHelper.access$getReasonLog(secUnlockedScreenOffAnimationHelper, secUnlockedScreenOffAnimationHelper.lastReason);
                boolean z = secUnlockedScreenOffAnimationHelper.needUpdateSetLockScreenShown;
                StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("onStartedGoingToSleep: isAODFullScreenMode=", ", shouldPlayUnlockedScreenOffAnimation=", " / reason=", isAODFullScreenMode, shouldPlayUnlockedScreenOffAnimation);
                m.append(access$getReasonLog);
                m.append(", needUpdateSetLockScreenShown=");
                m.append(z);
                SecUnlockedScreenOffAnimationHelper.logD(m.toString());
                if (!aODAmbientWallpaperHelper.isAODFullScreenMode()) {
                    secUnlockedScreenOffAnimationHelper.playWallpaperAnimation();
                } else if (shouldPlayUnlockedScreenOffAnimation) {
                    ((StatusBarKeyguardViewManager) secUnlockedScreenOffAnimationHelper.statusBarKeyguardViewManagerLazy.get()).updateNavigationBarVisibility(false);
                } else {
                    secUnlockedScreenOffAnimationHelper.playWallpaperAnimation();
                }
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = SecUnlockedScreenOffAnimationHelper.this;
                secUnlockedScreenOffAnimationHelper.deviceInteractive = true;
                secUnlockedScreenOffAnimationHelper.setSkipAnimationInOthers(false);
                secUnlockedScreenOffAnimationHelper.isPanelOpenedOnGoingToSleep = false;
                Job job = secUnlockedScreenOffAnimationHelper.job;
                if (job != null && job.isActive()) {
                    job.cancel(null);
                }
                secUnlockedScreenOffAnimationHelper.job = null;
                IRefreshRateToken iRefreshRateToken = secUnlockedScreenOffAnimationHelper.refreshRateToken;
                if (iRefreshRateToken != null) {
                    try {
                        iRefreshRateToken.release();
                        SecUnlockedScreenOffAnimationHelper.logD("clearMaxRefreshRate");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    secUnlockedScreenOffAnimationHelper.refreshRateToken = null;
                }
                Handler handler = secUnlockedScreenOffAnimationHelper.mainHandler;
                SecUnlockedScreenOffAnimationHelper$special$$inlined$Runnable$1 secUnlockedScreenOffAnimationHelper$special$$inlined$Runnable$1 = secUnlockedScreenOffAnimationHelper.updateSetLockScreenShownRunnable;
                if (handler.hasCallbacks(secUnlockedScreenOffAnimationHelper$special$$inlined$Runnable$1)) {
                    handler.removeCallbacks(secUnlockedScreenOffAnimationHelper$special$$inlined$Runnable$1);
                }
                SecUnlockedScreenOffAnimationHelper.logD("onStartedWakingUp: needUpdateSetLockScreenShown=" + secUnlockedScreenOffAnimationHelper.needUpdateSetLockScreenShown);
                if (secUnlockedScreenOffAnimationHelper.needUpdateSetLockScreenShown) {
                    secUnlockedScreenOffAnimationHelper.needUpdateSetLockScreenShown = false;
                    secUnlockedScreenOffAnimationHelper.updateSetLockScreenShown(true);
                }
            }
        });
        this.screenLifecycle.addObserver(new ScreenLifecycle.Observer() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$init$2
            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurningOff() {
                SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = SecUnlockedScreenOffAnimationHelper.this;
                SecUnlockedScreenOffAnimationHelper.logD("onScreenTurningOff: isAODFullScreenMode=" + secUnlockedScreenOffAnimationHelper.aodAmbientWallpaperHelper.isAODFullScreenMode() + ", needUpdateSetLockScreenShown=" + secUnlockedScreenOffAnimationHelper.needUpdateSetLockScreenShown);
                if (secUnlockedScreenOffAnimationHelper.aodAmbientWallpaperHelper.isAODFullScreenMode()) {
                    secUnlockedScreenOffAnimationHelper.mainHandler.post(secUnlockedScreenOffAnimationHelper.updateSetLockScreenShownRunnable);
                }
            }
        });
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

    public final void onPrepare() {
        int refreshRateMode = this.settingsHelper.getRefreshRateMode(false);
        if (refreshRateMode == 1 || refreshRateMode == 2) {
            if (this.refreshRateToken == null) {
                try {
                    IDisplayManager iDisplayManager = (IDisplayManager) this.displayManager$delegate.getValue();
                    IBinder iBinder = (IBinder) this.token$delegate.getValue();
                    kotlin.Lazy lazy = this.maxRefreshRate$delegate;
                    this.refreshRateToken = iDisplayManager.acquireRefreshRateMaxLimitToken(iBinder, ((Number) lazy.getValue()).intValue(), "UnlockedScreenOffAnimation");
                    logD("setMaxRefreshRate " + ((Number) lazy.getValue()).intValue() + "Hz");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            if (this.refreshRateToken == null) {
                logD("setMaxRefreshRate failed");
            }
        }
    }

    public final void playWallpaperAnimation() {
        BuildersKt.launch$default(this.scope, this.backgroundDispatcher, null, new SecUnlockedScreenOffAnimationHelper$playWallpaperAnimation$1(this, null), 2);
    }

    public final void setSkipAnimationInOthers(boolean z) {
        if (this.skipAnimationInOthers != z && !z) {
            logD("skipAnimationInOthers false");
        }
        this.skipAnimationInOthers = z;
    }

    public final boolean shouldPlayUnlockedScreenOffAnimation() {
        if (SafeUIState.isSysUiSafeModeEnabled()) {
            logD("shouldPlayUnlockedScreenOffAnimation do not play UnlockedScreenOffAnimation");
            return false;
        }
        Iterator it = this.conditions.iterator();
        int i = -1;
        while (it.hasNext()) {
            i++;
            if (((Boolean) ((Function0) it.next()).invoke()).booleanValue()) {
                int i2 = this.lastReason;
                WakefulnessLifecycle wakefulnessLifecycle = this.wakefulnessLifecycle;
                if ((i2 != i && wakefulnessLifecycle.mWakefulness != 0) || this.lastShouldPlay) {
                    logD("shouldPlayUnlockedScreenOffAnimation false / " + (this.moreLog ? (String) CollectionsKt___CollectionsKt.getOrNull(i, this.reasonLog) : MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "reason: ")));
                }
                if (this.lastShouldPlay && wakefulnessLifecycle.mWakefulness == 0) {
                    setSkipAnimationInOthers(true);
                    Job job = this.job;
                    if (job != null && job.isActive()) {
                        job.cancel(null);
                    }
                    this.job = null;
                    this.job = BuildersKt.launch$default(this.scope, this.mainDispatcher, null, new SecUnlockedScreenOffAnimationHelper$shouldPlayUnlockedScreenOffAnimation$1$1(this, null), 2);
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

    public final void updateSetLockScreenShown(final boolean z) {
        Log.i("UnlockedScreenOffAnimation", "updateSetLockScreenShown: wakingUp=" + z);
        this.mainHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$updateSetLockScreenShown$1
            @Override // java.lang.Runnable
            public final void run() {
                ((KeyguardViewMediator) SecUnlockedScreenOffAnimationHelper.this.keyguardViewMediatorLazy.get()).setDozing(!z);
            }
        });
    }
}
