package com.android.systemui.statusbar.phone;

import android.app.WallpaperManager;
import android.content.Context;
import android.hardware.display.IDisplayManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.LsRune;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.aod.AODTouchModeManager;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.facewidget.plugin.FaceWidgetContainerWrapper;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.plugins.aod.PluginAOD;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.hardware.display.IRefreshRateToken;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecUnlockedScreenOffAnimationHelper implements KeyguardFoldController.StateListener {
    public final AODAmbientWallpaperHelper aodAmbientWallpaperHelper;
    private final SettingsHelper.OnChangedCallback aodStateCallback;
    public final AODTouchModeManager aodTouchModeManager;
    public final CoroutineDispatcher backgroundDispatcher;
    public CentralSurfacesImpl centralSurfaces;
    public UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0 clearDecidedToAnimateGoingToSleep;
    public final List conditions;
    public final Context context;
    public int curRotation;
    public boolean deviceInteractive;
    public final Lazy displayManager$delegate;
    public final dagger.Lazy dozeParameters;
    public UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0 isFalseDecidedToAnimateGoingToSleep;
    public boolean isPanelOpenedOnGoingToSleep;
    public StandaloneCoroutine job;
    public final KeyguardFoldController keyguardFoldController;
    public final dagger.Lazy keyguardUpdateMonitorLazy;
    public final dagger.Lazy keyguardViewMediatorLazy;
    public final dagger.Lazy keyguardVisibilityMonitorLazy;
    public int lastReason;
    public boolean lastShouldPlay;
    public final CoroutineDispatcher mainDispatcher;
    public final Handler mainHandler;
    public final Lazy maxRefreshRate$delegate;
    public final boolean moreLog;
    public boolean needUpdateSetLockScreenShown;
    public final dagger.Lazy pluginAODManagerLazy;
    public final dagger.Lazy pluginFaceWidgetManagerLazy;
    public final dagger.Lazy pluginLockMediatorLazy;
    public final dagger.Lazy pluginLockStarManagerLazy;
    public final List reasonLog;
    public IRefreshRateToken refreshRateToken;
    public final CoroutineScope scope;
    public final ScreenLifecycle screenLifecycle;
    private final SettingsHelper settingsHelper;
    public boolean skipAnimationInOthers;
    public final dagger.Lazy statusBarKeyguardViewManagerLazy;
    public final StatusBarStateControllerImpl statusBarStateControllerImpl;
    public final Lazy token$delegate;
    public final dagger.Lazy unlockedScreenOffAnimationController;
    public final SecUnlockedScreenOffAnimationHelper$updateSetLockScreenShownRunnable$1 updateSetLockScreenShownRunnable;
    public final WakefulnessLifecycle wakefulnessLifecycle;
    public final WallpaperManager wallpaperManager;

    public static boolean $r8$lambda$eEKgOiEEeeWpWotBHBwR98uuBco(SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper) {
        boolean z;
        if (!LsRune.KEYGUARD_FIX_ROTATION_FOR_FACTORY) {
            int rotation = secUnlockedScreenOffAnimationHelper.context.getDisplay().getRotation();
            secUnlockedScreenOffAnimationHelper.curRotation = rotation;
            KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("getRotation: curRotation=", rotation, ", settingsHelper.isRotationLocked=", secUnlockedScreenOffAnimationHelper.settingsHelper.isRotationLocked(), "UnlockedScreenOffAnimation");
            int i = secUnlockedScreenOffAnimationHelper.curRotation;
            boolean isLockScreenRotationAllowed = secUnlockedScreenOffAnimationHelper.settingsHelper.isLockScreenRotationAllowed();
            boolean isRotationLocked = secUnlockedScreenOffAnimationHelper.settingsHelper.isRotationLocked();
            if (((!isLockScreenRotationAllowed || isRotationLocked) && i != 0) || ((isLockScreenRotationAllowed && i != 0 && (WallpaperUtils.isVideoWallpaper(secUnlockedScreenOffAnimationHelper.context) || ((PluginLockMediator) secUnlockedScreenOffAnimationHelper.pluginLockMediatorLazy.get()).isRotateMenuHide())) || (!isRotationLocked && isLockScreenRotationAllowed && i == 2))) {
                z = false;
                return !z;
            }
        }
        z = true;
        return !z;
    }

    /* JADX WARN: Type inference failed for: r14v33, types: [com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$updateSetLockScreenShownRunnable$1] */
    public SecUnlockedScreenOffAnimationHelper(AODAmbientWallpaperHelper aODAmbientWallpaperHelper, dagger.Lazy lazy, dagger.Lazy lazy2, dagger.Lazy lazy3, SettingsHelper settingsHelper, StatusBarStateControllerImpl statusBarStateControllerImpl, Context context, dagger.Lazy lazy4, WakefulnessLifecycle wakefulnessLifecycle, dagger.Lazy lazy5, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, dagger.Lazy lazy6, Handler handler, WallpaperManager wallpaperManager, dagger.Lazy lazy7, dagger.Lazy lazy8, ScreenLifecycle screenLifecycle, dagger.Lazy lazy9, AODTouchModeManager aODTouchModeManager, dagger.Lazy lazy10, KeyguardFoldController keyguardFoldController) {
        this.aodAmbientWallpaperHelper = aODAmbientWallpaperHelper;
        this.pluginAODManagerLazy = lazy;
        this.dozeParameters = lazy2;
        this.unlockedScreenOffAnimationController = lazy3;
        this.settingsHelper = settingsHelper;
        this.statusBarStateControllerImpl = statusBarStateControllerImpl;
        this.context = context;
        this.keyguardVisibilityMonitorLazy = lazy4;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.keyguardUpdateMonitorLazy = lazy5;
        this.scope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundDispatcher = coroutineDispatcher2;
        this.keyguardViewMediatorLazy = lazy6;
        this.mainHandler = handler;
        this.wallpaperManager = wallpaperManager;
        this.pluginFaceWidgetManagerLazy = lazy7;
        this.statusBarKeyguardViewManagerLazy = lazy8;
        this.screenLifecycle = screenLifecycle;
        this.pluginLockStarManagerLazy = lazy9;
        this.aodTouchModeManager = aODTouchModeManager;
        this.pluginLockMediatorLazy = lazy10;
        this.keyguardFoldController = keyguardFoldController;
        final int i = 0;
        this.displayManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
                    case 1:
                        return Integer.valueOf(Intrinsics.areEqual(Build.TYPE, "user") ? 60 : SystemProperties.getInt("debug.aod.screen_off_animation_refresh_rate", 60));
                    default:
                        return new Binder();
                }
            }
        });
        final int i2 = 1;
        this.maxRefreshRate$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
                    case 1:
                        return Integer.valueOf(Intrinsics.areEqual(Build.TYPE, "user") ? 60 : SystemProperties.getInt("debug.aod.screen_off_animation_refresh_rate", 60));
                    default:
                        return new Binder();
                }
            }
        });
        this.moreLog = !"user".equals(Build.TYPE) || Debug.semIsProductDev() || LogUtil.isDebugLevelMid() || LogUtil.isDebugLevelHigh();
        final int i3 = 2;
        this.token$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        return IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
                    case 1:
                        return Integer.valueOf(Intrinsics.areEqual(Build.TYPE, "user") ? 60 : SystemProperties.getInt("debug.aod.screen_off_animation_refresh_rate", 60));
                    default:
                        return new Binder();
                }
            }
        });
        this.curRotation = -1;
        this.deviceInteractive = true;
        final int i4 = 10;
        final int i5 = 11;
        final int i6 = 1;
        final int i7 = 2;
        final int i8 = 3;
        final int i9 = 4;
        final int i10 = 5;
        final int i11 = 0;
        final int i12 = 6;
        final int i13 = 7;
        final int i14 = 8;
        final int i15 = 9;
        this.conditions = Arrays.asList(new Function0(this) { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$$ExternalSyntheticLambda1
            public final /* synthetic */ SecUnlockedScreenOffAnimationHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isAnimationRemoved;
                boolean isUltraPowerSavingMode;
                switch (i4) {
                    case 0:
                        return Boolean.valueOf(SecUnlockedScreenOffAnimationHelper.$r8$lambda$eEKgOiEEeeWpWotBHBwR98uuBco(this.f$0));
                    case 1:
                        return Boolean.valueOf(!((DozeParameters) this.f$0.dozeParameters.get()).canControlUnlockedScreenOff());
                    case 2:
                        UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0 unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = this.f$0.isFalseDecidedToAnimateGoingToSleep;
                        if (unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 == null) {
                            unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = null;
                        }
                        return (Boolean) unlockedScreenOffAnimationController$$ExternalSyntheticLambda0.invoke();
                    case 3:
                        isAnimationRemoved = this.f$0.settingsHelper.isAnimationRemoved();
                        return Boolean.valueOf(isAnimationRemoved);
                    case 4:
                        return Boolean.valueOf(this.f$0.statusBarStateControllerImpl.mState != 0);
                    case 5:
                        SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = this.f$0;
                        return Boolean.valueOf((secUnlockedScreenOffAnimationHelper.centralSurfaces == null || secUnlockedScreenOffAnimationHelper.statusBarStateControllerImpl.mIsExpanded) && !((UnlockedScreenOffAnimationController) secUnlockedScreenOffAnimationHelper.unlockedScreenOffAnimationController.get()).lightRevealAnimationPlaying);
                    case 6:
                        return Boolean.valueOf(((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).isCoverClosed());
                    case 7:
                        return Boolean.valueOf(this.f$0.isPanelOpenedOnGoingToSleep);
                    case 8:
                        isUltraPowerSavingMode = this.f$0.settingsHelper.isUltraPowerSavingMode();
                        return Boolean.valueOf(isUltraPowerSavingMode);
                    case 9:
                        return Boolean.valueOf(((PluginAODManager) this.f$0.pluginAODManagerLazy.get()).mStartedByFolderClosed);
                    case 10:
                        return Boolean.valueOf(!this.f$0.aodAmbientWallpaperHelper.isAODFullScreenMode());
                    default:
                        return Boolean.valueOf(!((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).mDeviceProvisioned);
                }
            }
        }, new Function0(this) { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$$ExternalSyntheticLambda1
            public final /* synthetic */ SecUnlockedScreenOffAnimationHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isAnimationRemoved;
                boolean isUltraPowerSavingMode;
                switch (i5) {
                    case 0:
                        return Boolean.valueOf(SecUnlockedScreenOffAnimationHelper.$r8$lambda$eEKgOiEEeeWpWotBHBwR98uuBco(this.f$0));
                    case 1:
                        return Boolean.valueOf(!((DozeParameters) this.f$0.dozeParameters.get()).canControlUnlockedScreenOff());
                    case 2:
                        UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0 unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = this.f$0.isFalseDecidedToAnimateGoingToSleep;
                        if (unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 == null) {
                            unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = null;
                        }
                        return (Boolean) unlockedScreenOffAnimationController$$ExternalSyntheticLambda0.invoke();
                    case 3:
                        isAnimationRemoved = this.f$0.settingsHelper.isAnimationRemoved();
                        return Boolean.valueOf(isAnimationRemoved);
                    case 4:
                        return Boolean.valueOf(this.f$0.statusBarStateControllerImpl.mState != 0);
                    case 5:
                        SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = this.f$0;
                        return Boolean.valueOf((secUnlockedScreenOffAnimationHelper.centralSurfaces == null || secUnlockedScreenOffAnimationHelper.statusBarStateControllerImpl.mIsExpanded) && !((UnlockedScreenOffAnimationController) secUnlockedScreenOffAnimationHelper.unlockedScreenOffAnimationController.get()).lightRevealAnimationPlaying);
                    case 6:
                        return Boolean.valueOf(((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).isCoverClosed());
                    case 7:
                        return Boolean.valueOf(this.f$0.isPanelOpenedOnGoingToSleep);
                    case 8:
                        isUltraPowerSavingMode = this.f$0.settingsHelper.isUltraPowerSavingMode();
                        return Boolean.valueOf(isUltraPowerSavingMode);
                    case 9:
                        return Boolean.valueOf(((PluginAODManager) this.f$0.pluginAODManagerLazy.get()).mStartedByFolderClosed);
                    case 10:
                        return Boolean.valueOf(!this.f$0.aodAmbientWallpaperHelper.isAODFullScreenMode());
                    default:
                        return Boolean.valueOf(!((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).mDeviceProvisioned);
                }
            }
        }, new Function0(this) { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$$ExternalSyntheticLambda1
            public final /* synthetic */ SecUnlockedScreenOffAnimationHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isAnimationRemoved;
                boolean isUltraPowerSavingMode;
                switch (i6) {
                    case 0:
                        return Boolean.valueOf(SecUnlockedScreenOffAnimationHelper.$r8$lambda$eEKgOiEEeeWpWotBHBwR98uuBco(this.f$0));
                    case 1:
                        return Boolean.valueOf(!((DozeParameters) this.f$0.dozeParameters.get()).canControlUnlockedScreenOff());
                    case 2:
                        UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0 unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = this.f$0.isFalseDecidedToAnimateGoingToSleep;
                        if (unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 == null) {
                            unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = null;
                        }
                        return (Boolean) unlockedScreenOffAnimationController$$ExternalSyntheticLambda0.invoke();
                    case 3:
                        isAnimationRemoved = this.f$0.settingsHelper.isAnimationRemoved();
                        return Boolean.valueOf(isAnimationRemoved);
                    case 4:
                        return Boolean.valueOf(this.f$0.statusBarStateControllerImpl.mState != 0);
                    case 5:
                        SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = this.f$0;
                        return Boolean.valueOf((secUnlockedScreenOffAnimationHelper.centralSurfaces == null || secUnlockedScreenOffAnimationHelper.statusBarStateControllerImpl.mIsExpanded) && !((UnlockedScreenOffAnimationController) secUnlockedScreenOffAnimationHelper.unlockedScreenOffAnimationController.get()).lightRevealAnimationPlaying);
                    case 6:
                        return Boolean.valueOf(((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).isCoverClosed());
                    case 7:
                        return Boolean.valueOf(this.f$0.isPanelOpenedOnGoingToSleep);
                    case 8:
                        isUltraPowerSavingMode = this.f$0.settingsHelper.isUltraPowerSavingMode();
                        return Boolean.valueOf(isUltraPowerSavingMode);
                    case 9:
                        return Boolean.valueOf(((PluginAODManager) this.f$0.pluginAODManagerLazy.get()).mStartedByFolderClosed);
                    case 10:
                        return Boolean.valueOf(!this.f$0.aodAmbientWallpaperHelper.isAODFullScreenMode());
                    default:
                        return Boolean.valueOf(!((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).mDeviceProvisioned);
                }
            }
        }, new Function0(this) { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$$ExternalSyntheticLambda1
            public final /* synthetic */ SecUnlockedScreenOffAnimationHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isAnimationRemoved;
                boolean isUltraPowerSavingMode;
                switch (i7) {
                    case 0:
                        return Boolean.valueOf(SecUnlockedScreenOffAnimationHelper.$r8$lambda$eEKgOiEEeeWpWotBHBwR98uuBco(this.f$0));
                    case 1:
                        return Boolean.valueOf(!((DozeParameters) this.f$0.dozeParameters.get()).canControlUnlockedScreenOff());
                    case 2:
                        UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0 unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = this.f$0.isFalseDecidedToAnimateGoingToSleep;
                        if (unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 == null) {
                            unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = null;
                        }
                        return (Boolean) unlockedScreenOffAnimationController$$ExternalSyntheticLambda0.invoke();
                    case 3:
                        isAnimationRemoved = this.f$0.settingsHelper.isAnimationRemoved();
                        return Boolean.valueOf(isAnimationRemoved);
                    case 4:
                        return Boolean.valueOf(this.f$0.statusBarStateControllerImpl.mState != 0);
                    case 5:
                        SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = this.f$0;
                        return Boolean.valueOf((secUnlockedScreenOffAnimationHelper.centralSurfaces == null || secUnlockedScreenOffAnimationHelper.statusBarStateControllerImpl.mIsExpanded) && !((UnlockedScreenOffAnimationController) secUnlockedScreenOffAnimationHelper.unlockedScreenOffAnimationController.get()).lightRevealAnimationPlaying);
                    case 6:
                        return Boolean.valueOf(((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).isCoverClosed());
                    case 7:
                        return Boolean.valueOf(this.f$0.isPanelOpenedOnGoingToSleep);
                    case 8:
                        isUltraPowerSavingMode = this.f$0.settingsHelper.isUltraPowerSavingMode();
                        return Boolean.valueOf(isUltraPowerSavingMode);
                    case 9:
                        return Boolean.valueOf(((PluginAODManager) this.f$0.pluginAODManagerLazy.get()).mStartedByFolderClosed);
                    case 10:
                        return Boolean.valueOf(!this.f$0.aodAmbientWallpaperHelper.isAODFullScreenMode());
                    default:
                        return Boolean.valueOf(!((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).mDeviceProvisioned);
                }
            }
        }, new Function0(this) { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$$ExternalSyntheticLambda1
            public final /* synthetic */ SecUnlockedScreenOffAnimationHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isAnimationRemoved;
                boolean isUltraPowerSavingMode;
                switch (i8) {
                    case 0:
                        return Boolean.valueOf(SecUnlockedScreenOffAnimationHelper.$r8$lambda$eEKgOiEEeeWpWotBHBwR98uuBco(this.f$0));
                    case 1:
                        return Boolean.valueOf(!((DozeParameters) this.f$0.dozeParameters.get()).canControlUnlockedScreenOff());
                    case 2:
                        UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0 unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = this.f$0.isFalseDecidedToAnimateGoingToSleep;
                        if (unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 == null) {
                            unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = null;
                        }
                        return (Boolean) unlockedScreenOffAnimationController$$ExternalSyntheticLambda0.invoke();
                    case 3:
                        isAnimationRemoved = this.f$0.settingsHelper.isAnimationRemoved();
                        return Boolean.valueOf(isAnimationRemoved);
                    case 4:
                        return Boolean.valueOf(this.f$0.statusBarStateControllerImpl.mState != 0);
                    case 5:
                        SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = this.f$0;
                        return Boolean.valueOf((secUnlockedScreenOffAnimationHelper.centralSurfaces == null || secUnlockedScreenOffAnimationHelper.statusBarStateControllerImpl.mIsExpanded) && !((UnlockedScreenOffAnimationController) secUnlockedScreenOffAnimationHelper.unlockedScreenOffAnimationController.get()).lightRevealAnimationPlaying);
                    case 6:
                        return Boolean.valueOf(((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).isCoverClosed());
                    case 7:
                        return Boolean.valueOf(this.f$0.isPanelOpenedOnGoingToSleep);
                    case 8:
                        isUltraPowerSavingMode = this.f$0.settingsHelper.isUltraPowerSavingMode();
                        return Boolean.valueOf(isUltraPowerSavingMode);
                    case 9:
                        return Boolean.valueOf(((PluginAODManager) this.f$0.pluginAODManagerLazy.get()).mStartedByFolderClosed);
                    case 10:
                        return Boolean.valueOf(!this.f$0.aodAmbientWallpaperHelper.isAODFullScreenMode());
                    default:
                        return Boolean.valueOf(!((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).mDeviceProvisioned);
                }
            }
        }, new Function0(this) { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$$ExternalSyntheticLambda1
            public final /* synthetic */ SecUnlockedScreenOffAnimationHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isAnimationRemoved;
                boolean isUltraPowerSavingMode;
                switch (i9) {
                    case 0:
                        return Boolean.valueOf(SecUnlockedScreenOffAnimationHelper.$r8$lambda$eEKgOiEEeeWpWotBHBwR98uuBco(this.f$0));
                    case 1:
                        return Boolean.valueOf(!((DozeParameters) this.f$0.dozeParameters.get()).canControlUnlockedScreenOff());
                    case 2:
                        UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0 unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = this.f$0.isFalseDecidedToAnimateGoingToSleep;
                        if (unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 == null) {
                            unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = null;
                        }
                        return (Boolean) unlockedScreenOffAnimationController$$ExternalSyntheticLambda0.invoke();
                    case 3:
                        isAnimationRemoved = this.f$0.settingsHelper.isAnimationRemoved();
                        return Boolean.valueOf(isAnimationRemoved);
                    case 4:
                        return Boolean.valueOf(this.f$0.statusBarStateControllerImpl.mState != 0);
                    case 5:
                        SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = this.f$0;
                        return Boolean.valueOf((secUnlockedScreenOffAnimationHelper.centralSurfaces == null || secUnlockedScreenOffAnimationHelper.statusBarStateControllerImpl.mIsExpanded) && !((UnlockedScreenOffAnimationController) secUnlockedScreenOffAnimationHelper.unlockedScreenOffAnimationController.get()).lightRevealAnimationPlaying);
                    case 6:
                        return Boolean.valueOf(((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).isCoverClosed());
                    case 7:
                        return Boolean.valueOf(this.f$0.isPanelOpenedOnGoingToSleep);
                    case 8:
                        isUltraPowerSavingMode = this.f$0.settingsHelper.isUltraPowerSavingMode();
                        return Boolean.valueOf(isUltraPowerSavingMode);
                    case 9:
                        return Boolean.valueOf(((PluginAODManager) this.f$0.pluginAODManagerLazy.get()).mStartedByFolderClosed);
                    case 10:
                        return Boolean.valueOf(!this.f$0.aodAmbientWallpaperHelper.isAODFullScreenMode());
                    default:
                        return Boolean.valueOf(!((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).mDeviceProvisioned);
                }
            }
        }, new Function0(this) { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$$ExternalSyntheticLambda1
            public final /* synthetic */ SecUnlockedScreenOffAnimationHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isAnimationRemoved;
                boolean isUltraPowerSavingMode;
                switch (i10) {
                    case 0:
                        return Boolean.valueOf(SecUnlockedScreenOffAnimationHelper.$r8$lambda$eEKgOiEEeeWpWotBHBwR98uuBco(this.f$0));
                    case 1:
                        return Boolean.valueOf(!((DozeParameters) this.f$0.dozeParameters.get()).canControlUnlockedScreenOff());
                    case 2:
                        UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0 unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = this.f$0.isFalseDecidedToAnimateGoingToSleep;
                        if (unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 == null) {
                            unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = null;
                        }
                        return (Boolean) unlockedScreenOffAnimationController$$ExternalSyntheticLambda0.invoke();
                    case 3:
                        isAnimationRemoved = this.f$0.settingsHelper.isAnimationRemoved();
                        return Boolean.valueOf(isAnimationRemoved);
                    case 4:
                        return Boolean.valueOf(this.f$0.statusBarStateControllerImpl.mState != 0);
                    case 5:
                        SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = this.f$0;
                        return Boolean.valueOf((secUnlockedScreenOffAnimationHelper.centralSurfaces == null || secUnlockedScreenOffAnimationHelper.statusBarStateControllerImpl.mIsExpanded) && !((UnlockedScreenOffAnimationController) secUnlockedScreenOffAnimationHelper.unlockedScreenOffAnimationController.get()).lightRevealAnimationPlaying);
                    case 6:
                        return Boolean.valueOf(((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).isCoverClosed());
                    case 7:
                        return Boolean.valueOf(this.f$0.isPanelOpenedOnGoingToSleep);
                    case 8:
                        isUltraPowerSavingMode = this.f$0.settingsHelper.isUltraPowerSavingMode();
                        return Boolean.valueOf(isUltraPowerSavingMode);
                    case 9:
                        return Boolean.valueOf(((PluginAODManager) this.f$0.pluginAODManagerLazy.get()).mStartedByFolderClosed);
                    case 10:
                        return Boolean.valueOf(!this.f$0.aodAmbientWallpaperHelper.isAODFullScreenMode());
                    default:
                        return Boolean.valueOf(!((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).mDeviceProvisioned);
                }
            }
        }, new Function0(this) { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$$ExternalSyntheticLambda1
            public final /* synthetic */ SecUnlockedScreenOffAnimationHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isAnimationRemoved;
                boolean isUltraPowerSavingMode;
                switch (i11) {
                    case 0:
                        return Boolean.valueOf(SecUnlockedScreenOffAnimationHelper.$r8$lambda$eEKgOiEEeeWpWotBHBwR98uuBco(this.f$0));
                    case 1:
                        return Boolean.valueOf(!((DozeParameters) this.f$0.dozeParameters.get()).canControlUnlockedScreenOff());
                    case 2:
                        UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0 unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = this.f$0.isFalseDecidedToAnimateGoingToSleep;
                        if (unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 == null) {
                            unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = null;
                        }
                        return (Boolean) unlockedScreenOffAnimationController$$ExternalSyntheticLambda0.invoke();
                    case 3:
                        isAnimationRemoved = this.f$0.settingsHelper.isAnimationRemoved();
                        return Boolean.valueOf(isAnimationRemoved);
                    case 4:
                        return Boolean.valueOf(this.f$0.statusBarStateControllerImpl.mState != 0);
                    case 5:
                        SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = this.f$0;
                        return Boolean.valueOf((secUnlockedScreenOffAnimationHelper.centralSurfaces == null || secUnlockedScreenOffAnimationHelper.statusBarStateControllerImpl.mIsExpanded) && !((UnlockedScreenOffAnimationController) secUnlockedScreenOffAnimationHelper.unlockedScreenOffAnimationController.get()).lightRevealAnimationPlaying);
                    case 6:
                        return Boolean.valueOf(((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).isCoverClosed());
                    case 7:
                        return Boolean.valueOf(this.f$0.isPanelOpenedOnGoingToSleep);
                    case 8:
                        isUltraPowerSavingMode = this.f$0.settingsHelper.isUltraPowerSavingMode();
                        return Boolean.valueOf(isUltraPowerSavingMode);
                    case 9:
                        return Boolean.valueOf(((PluginAODManager) this.f$0.pluginAODManagerLazy.get()).mStartedByFolderClosed);
                    case 10:
                        return Boolean.valueOf(!this.f$0.aodAmbientWallpaperHelper.isAODFullScreenMode());
                    default:
                        return Boolean.valueOf(!((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).mDeviceProvisioned);
                }
            }
        }, new Function0(this) { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$$ExternalSyntheticLambda1
            public final /* synthetic */ SecUnlockedScreenOffAnimationHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isAnimationRemoved;
                boolean isUltraPowerSavingMode;
                switch (i12) {
                    case 0:
                        return Boolean.valueOf(SecUnlockedScreenOffAnimationHelper.$r8$lambda$eEKgOiEEeeWpWotBHBwR98uuBco(this.f$0));
                    case 1:
                        return Boolean.valueOf(!((DozeParameters) this.f$0.dozeParameters.get()).canControlUnlockedScreenOff());
                    case 2:
                        UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0 unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = this.f$0.isFalseDecidedToAnimateGoingToSleep;
                        if (unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 == null) {
                            unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = null;
                        }
                        return (Boolean) unlockedScreenOffAnimationController$$ExternalSyntheticLambda0.invoke();
                    case 3:
                        isAnimationRemoved = this.f$0.settingsHelper.isAnimationRemoved();
                        return Boolean.valueOf(isAnimationRemoved);
                    case 4:
                        return Boolean.valueOf(this.f$0.statusBarStateControllerImpl.mState != 0);
                    case 5:
                        SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = this.f$0;
                        return Boolean.valueOf((secUnlockedScreenOffAnimationHelper.centralSurfaces == null || secUnlockedScreenOffAnimationHelper.statusBarStateControllerImpl.mIsExpanded) && !((UnlockedScreenOffAnimationController) secUnlockedScreenOffAnimationHelper.unlockedScreenOffAnimationController.get()).lightRevealAnimationPlaying);
                    case 6:
                        return Boolean.valueOf(((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).isCoverClosed());
                    case 7:
                        return Boolean.valueOf(this.f$0.isPanelOpenedOnGoingToSleep);
                    case 8:
                        isUltraPowerSavingMode = this.f$0.settingsHelper.isUltraPowerSavingMode();
                        return Boolean.valueOf(isUltraPowerSavingMode);
                    case 9:
                        return Boolean.valueOf(((PluginAODManager) this.f$0.pluginAODManagerLazy.get()).mStartedByFolderClosed);
                    case 10:
                        return Boolean.valueOf(!this.f$0.aodAmbientWallpaperHelper.isAODFullScreenMode());
                    default:
                        return Boolean.valueOf(!((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).mDeviceProvisioned);
                }
            }
        }, new Function0(this) { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$$ExternalSyntheticLambda1
            public final /* synthetic */ SecUnlockedScreenOffAnimationHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isAnimationRemoved;
                boolean isUltraPowerSavingMode;
                switch (i13) {
                    case 0:
                        return Boolean.valueOf(SecUnlockedScreenOffAnimationHelper.$r8$lambda$eEKgOiEEeeWpWotBHBwR98uuBco(this.f$0));
                    case 1:
                        return Boolean.valueOf(!((DozeParameters) this.f$0.dozeParameters.get()).canControlUnlockedScreenOff());
                    case 2:
                        UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0 unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = this.f$0.isFalseDecidedToAnimateGoingToSleep;
                        if (unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 == null) {
                            unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = null;
                        }
                        return (Boolean) unlockedScreenOffAnimationController$$ExternalSyntheticLambda0.invoke();
                    case 3:
                        isAnimationRemoved = this.f$0.settingsHelper.isAnimationRemoved();
                        return Boolean.valueOf(isAnimationRemoved);
                    case 4:
                        return Boolean.valueOf(this.f$0.statusBarStateControllerImpl.mState != 0);
                    case 5:
                        SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = this.f$0;
                        return Boolean.valueOf((secUnlockedScreenOffAnimationHelper.centralSurfaces == null || secUnlockedScreenOffAnimationHelper.statusBarStateControllerImpl.mIsExpanded) && !((UnlockedScreenOffAnimationController) secUnlockedScreenOffAnimationHelper.unlockedScreenOffAnimationController.get()).lightRevealAnimationPlaying);
                    case 6:
                        return Boolean.valueOf(((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).isCoverClosed());
                    case 7:
                        return Boolean.valueOf(this.f$0.isPanelOpenedOnGoingToSleep);
                    case 8:
                        isUltraPowerSavingMode = this.f$0.settingsHelper.isUltraPowerSavingMode();
                        return Boolean.valueOf(isUltraPowerSavingMode);
                    case 9:
                        return Boolean.valueOf(((PluginAODManager) this.f$0.pluginAODManagerLazy.get()).mStartedByFolderClosed);
                    case 10:
                        return Boolean.valueOf(!this.f$0.aodAmbientWallpaperHelper.isAODFullScreenMode());
                    default:
                        return Boolean.valueOf(!((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).mDeviceProvisioned);
                }
            }
        }, new Function0(this) { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$$ExternalSyntheticLambda1
            public final /* synthetic */ SecUnlockedScreenOffAnimationHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isAnimationRemoved;
                boolean isUltraPowerSavingMode;
                switch (i14) {
                    case 0:
                        return Boolean.valueOf(SecUnlockedScreenOffAnimationHelper.$r8$lambda$eEKgOiEEeeWpWotBHBwR98uuBco(this.f$0));
                    case 1:
                        return Boolean.valueOf(!((DozeParameters) this.f$0.dozeParameters.get()).canControlUnlockedScreenOff());
                    case 2:
                        UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0 unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = this.f$0.isFalseDecidedToAnimateGoingToSleep;
                        if (unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 == null) {
                            unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = null;
                        }
                        return (Boolean) unlockedScreenOffAnimationController$$ExternalSyntheticLambda0.invoke();
                    case 3:
                        isAnimationRemoved = this.f$0.settingsHelper.isAnimationRemoved();
                        return Boolean.valueOf(isAnimationRemoved);
                    case 4:
                        return Boolean.valueOf(this.f$0.statusBarStateControllerImpl.mState != 0);
                    case 5:
                        SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = this.f$0;
                        return Boolean.valueOf((secUnlockedScreenOffAnimationHelper.centralSurfaces == null || secUnlockedScreenOffAnimationHelper.statusBarStateControllerImpl.mIsExpanded) && !((UnlockedScreenOffAnimationController) secUnlockedScreenOffAnimationHelper.unlockedScreenOffAnimationController.get()).lightRevealAnimationPlaying);
                    case 6:
                        return Boolean.valueOf(((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).isCoverClosed());
                    case 7:
                        return Boolean.valueOf(this.f$0.isPanelOpenedOnGoingToSleep);
                    case 8:
                        isUltraPowerSavingMode = this.f$0.settingsHelper.isUltraPowerSavingMode();
                        return Boolean.valueOf(isUltraPowerSavingMode);
                    case 9:
                        return Boolean.valueOf(((PluginAODManager) this.f$0.pluginAODManagerLazy.get()).mStartedByFolderClosed);
                    case 10:
                        return Boolean.valueOf(!this.f$0.aodAmbientWallpaperHelper.isAODFullScreenMode());
                    default:
                        return Boolean.valueOf(!((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).mDeviceProvisioned);
                }
            }
        }, new Function0(this) { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$$ExternalSyntheticLambda1
            public final /* synthetic */ SecUnlockedScreenOffAnimationHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean isAnimationRemoved;
                boolean isUltraPowerSavingMode;
                switch (i15) {
                    case 0:
                        return Boolean.valueOf(SecUnlockedScreenOffAnimationHelper.$r8$lambda$eEKgOiEEeeWpWotBHBwR98uuBco(this.f$0));
                    case 1:
                        return Boolean.valueOf(!((DozeParameters) this.f$0.dozeParameters.get()).canControlUnlockedScreenOff());
                    case 2:
                        UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0 unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = this.f$0.isFalseDecidedToAnimateGoingToSleep;
                        if (unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 == null) {
                            unlockedScreenOffAnimationController$$ExternalSyntheticLambda0 = null;
                        }
                        return (Boolean) unlockedScreenOffAnimationController$$ExternalSyntheticLambda0.invoke();
                    case 3:
                        isAnimationRemoved = this.f$0.settingsHelper.isAnimationRemoved();
                        return Boolean.valueOf(isAnimationRemoved);
                    case 4:
                        return Boolean.valueOf(this.f$0.statusBarStateControllerImpl.mState != 0);
                    case 5:
                        SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = this.f$0;
                        return Boolean.valueOf((secUnlockedScreenOffAnimationHelper.centralSurfaces == null || secUnlockedScreenOffAnimationHelper.statusBarStateControllerImpl.mIsExpanded) && !((UnlockedScreenOffAnimationController) secUnlockedScreenOffAnimationHelper.unlockedScreenOffAnimationController.get()).lightRevealAnimationPlaying);
                    case 6:
                        return Boolean.valueOf(((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).isCoverClosed());
                    case 7:
                        return Boolean.valueOf(this.f$0.isPanelOpenedOnGoingToSleep);
                    case 8:
                        isUltraPowerSavingMode = this.f$0.settingsHelper.isUltraPowerSavingMode();
                        return Boolean.valueOf(isUltraPowerSavingMode);
                    case 9:
                        return Boolean.valueOf(((PluginAODManager) this.f$0.pluginAODManagerLazy.get()).mStartedByFolderClosed);
                    case 10:
                        return Boolean.valueOf(!this.f$0.aodAmbientWallpaperHelper.isAODFullScreenMode());
                    default:
                        return Boolean.valueOf(!((KeyguardUpdateMonitor) this.f$0.keyguardUpdateMonitorLazy.get()).mDeviceProvisioned);
                }
            }
        });
        this.reasonLog = Arrays.asList("not AOD fullscreen", "not provisioned", "canControlUnlockedScreenOff is false", "decidedToAnimateGoingToSleep is false", "animation is disabled", "not SHADE state", "not initialized or panel is expanded", "rotation condition is not matched", "cover closed", "panel is already opened", "ultra power saving", "AOD started by Fold Close");
        this.lastReason = -1;
        this.updateSetLockScreenShownRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$updateSetLockScreenShownRunnable$1
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
        this.aodStateCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$aodStateCallback$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                SettingsHelper settingsHelper2;
                SettingsHelper settingsHelper3;
                SettingsHelper settingsHelper4;
                if (uri != null) {
                    boolean areEqual = Intrinsics.areEqual(Settings.System.getUriFor(SettingsHelper.INDEX_AOD_SHOW_STATE), uri);
                    SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper = SecUnlockedScreenOffAnimationHelper.this;
                    if (!areEqual) {
                        if (Intrinsics.areEqual(Settings.System.getUriFor(SettingsHelper.INDEX_AOD_SHOW_LOCKSCREEN_WALLPAPER), uri)) {
                            settingsHelper2 = secUnlockedScreenOffAnimationHelper.settingsHelper;
                            EmergencyButtonController$$ExternalSyntheticOutline0.m("aodStateCallback isAODShowLockWallpaper=", "UnlockedScreenOffAnimation", settingsHelper2.isAODShowLockWallpaper());
                            CentralSurfacesImpl centralSurfacesImpl = secUnlockedScreenOffAnimationHelper.centralSurfaces;
                            (centralSurfacesImpl != null ? centralSurfacesImpl : null).mLightRevealScrim.setAlpha(secUnlockedScreenOffAnimationHelper.aodAmbientWallpaperHelper.getAlpha());
                            return;
                        }
                        return;
                    }
                    settingsHelper3 = secUnlockedScreenOffAnimationHelper.settingsHelper;
                    ActionBarContextView$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("aodStateCallback isAODShown=", ", needUpdateSetLockScreenShown=", " deviceInteractive=", settingsHelper3.isAODShown(), secUnlockedScreenOffAnimationHelper.needUpdateSetLockScreenShown), secUnlockedScreenOffAnimationHelper.deviceInteractive, "UnlockedScreenOffAnimation");
                    if (secUnlockedScreenOffAnimationHelper.deviceInteractive) {
                        return;
                    }
                    AODAmbientWallpaperHelper aODAmbientWallpaperHelper2 = secUnlockedScreenOffAnimationHelper.aodAmbientWallpaperHelper;
                    if (aODAmbientWallpaperHelper2.isAODFullScreenAndShowing()) {
                        CentralSurfacesImpl centralSurfacesImpl2 = secUnlockedScreenOffAnimationHelper.centralSurfaces;
                        if (centralSurfacesImpl2 == null) {
                            centralSurfacesImpl2 = null;
                        }
                        centralSurfacesImpl2.mLightRevealScrim.setAlpha(aODAmbientWallpaperHelper2.getAlpha());
                        CentralSurfacesImpl centralSurfacesImpl3 = secUnlockedScreenOffAnimationHelper.centralSurfaces;
                        CentralSurfacesImpl centralSurfacesImpl4 = centralSurfacesImpl3 != null ? centralSurfacesImpl3 : null;
                        StringBuilder sb = new StringBuilder("setBehindScrimAlpha: mBehindAlpha=");
                        ScrimController scrimController = centralSurfacesImpl4.mScrimController;
                        sb.append(scrimController.mBehindAlpha);
                        sb.append(" alpha=0.0");
                        Log.d("ScrimController", sb.toString());
                        if (scrimController.mBehindAlpha != 0.0f) {
                            scrimController.mBehindAlpha = 0.0f;
                            scrimController.mScrimBehind.setAlpha(0.0f);
                        }
                        if (secUnlockedScreenOffAnimationHelper.needUpdateSetLockScreenShown) {
                            return;
                        }
                        secUnlockedScreenOffAnimationHelper.needUpdateSetLockScreenShown = true;
                        settingsHelper4 = secUnlockedScreenOffAnimationHelper.settingsHelper;
                        secUnlockedScreenOffAnimationHelper.updateSetLockScreenShown(true ^ settingsHelper4.isAODShown());
                    }
                }
            }
        };
    }

    public static final String access$getReasonLog(SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper, int i) {
        if (i != 7) {
            return (String) CollectionsKt___CollectionsKt.getOrNull(i, secUnlockedScreenOffAnimationHelper.reasonLog);
        }
        return CollectionsKt___CollectionsKt.getOrNull(i, secUnlockedScreenOffAnimationHelper.reasonLog) + " allowRotation=" + secUnlockedScreenOffAnimationHelper.settingsHelper.isLockScreenRotationAllowed() + ", rotationLock=" + secUnlockedScreenOffAnimationHelper.settingsHelper.isRotationLocked() + ", rotation=" + secUnlockedScreenOffAnimationHelper.curRotation;
    }

    public static void logD(String str) {
        com.android.systemui.keyguard.Log.d("UnlockedScreenOffAnimation", str);
    }

    public final void init(CentralSurfacesImpl centralSurfacesImpl, UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0 unlockedScreenOffAnimationController$$ExternalSyntheticLambda0, UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0 unlockedScreenOffAnimationController$$ExternalSyntheticLambda02) {
        this.centralSurfaces = centralSurfacesImpl;
        this.isFalseDecidedToAnimateGoingToSleep = unlockedScreenOffAnimationController$$ExternalSyntheticLambda0;
        this.clearDecidedToAnimateGoingToSleep = unlockedScreenOffAnimationController$$ExternalSyntheticLambda02;
        this.settingsHelper.registerCallback(this.aodStateCallback, Settings.System.getUriFor(SettingsHelper.INDEX_AOD_SHOW_STATE), Settings.System.getUriFor(SettingsHelper.INDEX_AOD_SHOW_LOCKSCREEN_WALLPAPER));
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
                StringBuilder m = EmergencyButtonController$$ExternalSyntheticOutline0.m("onStartedGoingToSleep: isAODFullScreenMode=", ", shouldPlayUnlockedScreenOffAnimation=", " / reason=", isAODFullScreenMode, shouldPlayUnlockedScreenOffAnimation);
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
                StandaloneCoroutine standaloneCoroutine = secUnlockedScreenOffAnimationHelper.job;
                if (standaloneCoroutine != null && standaloneCoroutine.isActive()) {
                    standaloneCoroutine.cancel(null);
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
                SecUnlockedScreenOffAnimationHelper$updateSetLockScreenShownRunnable$1 secUnlockedScreenOffAnimationHelper$updateSetLockScreenShownRunnable$1 = secUnlockedScreenOffAnimationHelper.updateSetLockScreenShownRunnable;
                if (handler.hasCallbacks(secUnlockedScreenOffAnimationHelper$updateSetLockScreenShownRunnable$1)) {
                    handler.removeCallbacks(secUnlockedScreenOffAnimationHelper$updateSetLockScreenShownRunnable$1);
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
        ((KeyguardFoldControllerImpl) this.keyguardFoldController).addCallback(this, 4, false);
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

    @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
    public final void onFoldStateChanged(boolean z) {
        EmergencyButtonController$$ExternalSyntheticOutline0.m("onFoldStateChanged: isOpened=", "UnlockedScreenOffAnimation", z);
        if (LsRune.AOD_SUB_FULLSCREEN && z) {
            CentralSurfacesImpl centralSurfacesImpl = this.centralSurfaces;
            if (centralSurfacesImpl == null) {
                centralSurfacesImpl = null;
            }
            centralSurfacesImpl.mLightRevealScrim.setAlpha(this.aodAmbientWallpaperHelper.getAlpha());
        }
    }

    public final void onPrepare() {
        int refreshRateMode = this.settingsHelper.getRefreshRateMode(false);
        if (refreshRateMode == 1 || refreshRateMode == 2) {
            if (this.refreshRateToken == null) {
                try {
                    IDisplayManager iDisplayManager = (IDisplayManager) this.displayManager$delegate.getValue();
                    IBinder iBinder = (IBinder) this.token$delegate.getValue();
                    Lazy lazy = this.maxRefreshRate$delegate;
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
                    StandaloneCoroutine standaloneCoroutine = this.job;
                    if (standaloneCoroutine != null && standaloneCoroutine.isActive()) {
                        standaloneCoroutine.cancel(null);
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
        com.android.systemui.keyguard.Log.i("UnlockedScreenOffAnimation", "updateSetLockScreenShown: wakingUp=" + z);
        this.mainHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$updateSetLockScreenShown$1
            @Override // java.lang.Runnable
            public final void run() {
                ((KeyguardViewMediator) SecUnlockedScreenOffAnimationHelper.this.keyguardViewMediatorLazy.get()).setDozing(!z);
            }
        });
    }
}
