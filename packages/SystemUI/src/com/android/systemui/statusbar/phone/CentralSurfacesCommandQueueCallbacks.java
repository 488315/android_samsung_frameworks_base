package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Binder;
import android.os.Bundle;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.util.Slog;
import android.view.KeyEvent;
import android.view.WindowManager;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.charging.WirelessChargingAnimation;
import com.android.systemui.cover.CoverHost;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.emergency.EmergencyGestureModule;
import com.android.systemui.emergency.EmergencyGestureModule$emergencyGestureIntentFactory$1;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.shared.model.CameraLaunchSourceModel;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.cover.PluginCover;
import com.android.systemui.plugins.subscreen.PluginSubScreen;
import com.android.systemui.qp.flashlight.SubroomFlashLightSettingsActivity;
import com.android.systemui.qp.flashlight.SubscreenFlashLightController;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSPanelHost;
import com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda0;
import com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda17;
import com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda6;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.flags.QsInCompose;
import com.android.systemui.recents.ScreenPinningRequest;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.searcle.SearcleManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.BaseShadeControllerImpl;
import com.android.systemui.shade.CameraLauncher;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl.AnonymousClass7;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
import com.android.systemui.statusbar.policy.SecFlashlightControllerImpl;
import com.android.systemui.statusbar.policy.SecFlashlightControllerImpl$$ExternalSyntheticLambda0;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.NoRemeasureMotionLayout;
import com.android.systemui.util.concurrency.MessageRouter;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import com.android.systemui.wallet.controller.QuickAccessWalletController$$ExternalSyntheticLambda2;
import com.samsung.android.cover.CoverState;
import dagger.Lazy;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CentralSurfacesCommandQueueCallbacks implements CommandQueue.Callbacks {
    public final ActivityStarter mActivityStarter;
    public final AssistManager mAssistManager;
    public final Lazy mCameraLauncherLazy;
    public final CentralSurfaces mCentralSurfaces;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final CoverHost mCoverHost;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public int mDisabled1;
    public int mDisabled2;
    public final int mDisplayId;
    public final DozeServiceHost mDozeServiceHost;
    public final EmergencyGestureModule.EmergencyGestureIntentFactory mEmergencyGestureIntentFactory;
    public final HeadsUpManager mHeadsUpManager;
    public final KeyguardInteractor mKeyguardInteractor;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final MetricsLogger mMetricsLogger;
    public final NotificationStackScrollLayoutController mNotificationStackScrollLayoutController;
    public final PanelExpansionInteractor mPanelExpansionInteractor;
    public final PowerManager mPowerManager;
    public final QSHost mQSHost;
    public final QuickSettingsController mQsController;
    public final RemoteInputQuickSettingsDisabler mRemoteInputQuickSettingsDisabler;
    public final ScreenPinningRequest mScreenPinningRequest;
    public final SearcleManager mSearcleManager;
    public final ShadeController mShadeController;
    public final ShadeHeaderController mShadeHeaderController;
    public final Lazy mShadeInteractorLazy;
    public final StatusBarHideIconsForBouncerManager mStatusBarHideIconsForBouncerManager;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final Lazy mSubScreenManagerLazy;
    public final UserTracker mUserTracker;
    public final boolean mVibrateOnOpening;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final QuickAccessWalletController mWalletController;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum PowerButtonLaunchGestureTarget {
        LAUNCH_CAMERA_ON_GESTURE,
        LAUNCH_WALLET_ON_GESTURE
    }

    static {
        VibrationAttributes.createForUsage(50);
    }

    public CentralSurfacesCommandQueueCallbacks(CentralSurfaces centralSurfaces, QuickSettingsController quickSettingsController, Context context, Resources resources, ScreenPinningRequest screenPinningRequest, ShadeController shadeController, CommandQueue commandQueue, PanelExpansionInteractor panelExpansionInteractor, Lazy lazy, ShadeHeaderController shadeHeaderController, RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler, MetricsLogger metricsLogger, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, HeadsUpManager headsUpManager, WakefulnessLifecycle wakefulnessLifecycle, DeviceProvisionedController deviceProvisionedController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, AssistManager assistManager, DozeServiceHost dozeServiceHost, NotificationStackScrollLayoutController notificationStackScrollLayoutController, StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager, PowerManager powerManager, Optional<Vibrator> optional, int i, Lazy lazy2, UserTracker userTracker, QSHost qSHost, ActivityStarter activityStarter, KeyguardInteractor keyguardInteractor, EmergencyGestureModule.EmergencyGestureIntentFactory emergencyGestureIntentFactory, QuickAccessWalletController quickAccessWalletController, SearcleManager searcleManager, CoverHost coverHost, Lazy lazy3) {
        this.mCentralSurfaces = centralSurfaces;
        this.mQsController = quickSettingsController;
        this.mContext = context;
        this.mScreenPinningRequest = screenPinningRequest;
        this.mShadeController = shadeController;
        this.mCommandQueue = commandQueue;
        this.mPanelExpansionInteractor = panelExpansionInteractor;
        this.mShadeInteractorLazy = lazy;
        this.mShadeHeaderController = shadeHeaderController;
        this.mRemoteInputQuickSettingsDisabler = remoteInputQuickSettingsDisabler;
        this.mMetricsLogger = metricsLogger;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        this.mHeadsUpManager = headsUpManager;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mAssistManager = assistManager;
        this.mDozeServiceHost = dozeServiceHost;
        this.mNotificationStackScrollLayoutController = notificationStackScrollLayoutController;
        this.mStatusBarHideIconsForBouncerManager = statusBarHideIconsForBouncerManager;
        this.mPowerManager = powerManager;
        this.mDisplayId = i;
        this.mCameraLauncherLazy = lazy2;
        this.mUserTracker = userTracker;
        this.mQSHost = qSHost;
        this.mKeyguardInteractor = keyguardInteractor;
        this.mVibrateOnOpening = resources.getBoolean(R.bool.config_vibrateOnIconAnimation);
        if (optional.isPresent() && optional.get().areAllPrimitivesSupported(4, 1)) {
            VibrationEffect.startComposition().addPrimitive(4).addPrimitive(1, 1.0f, 50).compose();
        } else if (optional.isPresent() && optional.get().hasAmplitudeControl()) {
            VibrationEffect.createWaveform(CentralSurfaces.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS, CentralSurfaces.CAMERA_LAUNCH_GESTURE_VIBRATION_AMPLITUDES, -1);
        } else {
            int[] intArray = resources.getIntArray(R.array.config_cameraLaunchGestureVibePattern);
            long[] jArr = new long[intArray.length];
            for (int i2 = 0; i2 < intArray.length; i2++) {
                jArr[i2] = intArray[i2];
            }
            VibrationEffect.createWaveform(jArr, -1);
        }
        this.mActivityStarter = activityStarter;
        this.mEmergencyGestureIntentFactory = emergencyGestureIntentFactory;
        this.mWalletController = quickAccessWalletController;
        if (BasicRune.SEARCLE) {
            this.mSearcleManager = searcleManager;
        } else {
            this.mSearcleManager = null;
        }
        if (LsRune.COVER_SUPPORTED) {
            this.mCoverHost = coverHost;
        }
        if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
            this.mSubScreenManagerLazy = lazy3;
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void addQsTileToFrontOrEnd(ComponentName componentName, boolean z) {
        this.mQSHost.addTile(componentName, z);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void animateCollapsePanels(int i, boolean z) {
        this.mShadeController.animateCollapseShade(1.0f, i, z, false);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void animateExpandNotificationsPanel() {
        ((BaseShadeControllerImpl) this.mShadeController).animateExpandShade();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void animateExpandSettingsPanel(String str) {
        ((BaseShadeControllerImpl) this.mShadeController).animateExpandQs();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void clickTile(ComponentName componentName) {
        int i = QsInCompose.$r8$clinit;
        SecQSPanelController secQSPanelController = ((CentralSurfacesImpl) this.mCentralSurfaces).mQSPanelController;
        if (secQSPanelController != null) {
            QSPanelHost qSPanelHost = secQSPanelController.mQsPanelHost;
            qSPanelHost.getClass();
            qSPanelHost.mRecords.stream().map(new QSPanelHost$$ExternalSyntheticLambda6(5)).filter(new QSPanelHost$$ExternalSyntheticLambda17(CustomTile.toSpec(componentName), 2)).findFirst().ifPresent(new QSPanelHost$$ExternalSyntheticLambda0(3));
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void disable(int i, int i2, int i3, boolean z) {
        if (i != this.mDisplayId) {
            return;
        }
        int i4 = this.mDisabled1 ^ i2;
        this.mDisabled1 = i2;
        this.mRemoteInputQuickSettingsDisabler.getClass();
        int i5 = this.mDisabled2 ^ i3;
        this.mDisabled2 = i3;
        int i6 = i4 & 65536;
        ShadeController shadeController = this.mShadeController;
        if (i6 != 0 && (65536 & i2) != 0) {
            shadeController.animateCollapseShade(0);
        }
        if ((i4 & 262144) != 0 && (i2 & 262144) != 0) {
            ((HeadsUpManagerImpl) this.mHeadsUpManager).releaseAllImmediately();
        }
        if ((i5 & 4) != 0 && (i3 & 4) != 0) {
            shadeController.animateCollapseShade(0);
        }
        int i7 = SceneContainerFlag.$r8$clinit;
        ShadeHeaderController shadeHeaderController = this.mShadeHeaderController;
        shadeHeaderController.getClass();
        boolean z2 = (i3 & 1) != 0;
        if (z2 == shadeHeaderController.qsDisabled) {
            return;
        }
        shadeHeaderController.qsDisabled = z2;
        shadeHeaderController.updateVisibility$7();
        ((NoRemeasureMotionLayout) shadeHeaderController.header).setDisable(z2);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void dismissKeyboardShortcutsMenu() {
        MessageRouter messageRouter = ((CentralSurfacesImpl) this.mCentralSurfaces).mMessageRouter;
        messageRouter.cancelMessages(1027);
        messageRouter.sendMessage(1027);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void handleSystemKey(KeyEvent keyEvent) {
        if (this.mCommandQueue.panelsEnabled() && this.mKeyguardUpdateMonitor.mDeviceInteractive) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
            if ((!keyguardStateControllerImpl.mShowing || keyguardStateControllerImpl.mOccluded) && ((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).isCurrentUserSetup()) {
                int keyCode = keyEvent.getKeyCode();
                ShadeController shadeController = this.mShadeController;
                if (280 == keyCode) {
                    this.mMetricsLogger.action(493);
                    shadeController.animateCollapseShade(0);
                    return;
                }
                if (281 == keyEvent.getKeyCode()) {
                    this.mMetricsLogger.action(494);
                    if (!this.mPanelExpansionInteractor.isFullyCollapsed()) {
                        if (this.mQsController.getExpanded() || shadeController.isExpandingOrCollapsing()) {
                            return;
                        }
                        ((BaseShadeControllerImpl) shadeController).animateExpandQs();
                        this.mMetricsLogger.count("panel_open_qs", 1);
                        return;
                    }
                    if (this.mVibrateOnOpening) {
                        vibrateOnNavigationKeyDown();
                    }
                    ((BaseShadeControllerImpl) shadeController).animateExpandShade();
                    this.mNotificationStackScrollLayoutController.mView.mWillExpand = true;
                    ((HeadsUpManagerImpl) this.mHeadsUpManager).unpinAll();
                    this.mMetricsLogger.count("panel_open", 1);
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onCameraLaunchGestureDetected(int i) {
        onPowerButtonLaunchGestureTriggered(PowerButtonLaunchGestureTarget.LAUNCH_CAMERA_ON_GESTURE, i);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onEmergencyActionLaunchGestureDetected() {
        Intent invoke = ((EmergencyGestureModule$emergencyGestureIntentFactory$1) this.mEmergencyGestureIntentFactory).invoke();
        if (invoke == null) {
            Log.wtf("CentralSurfaces", "Couldn't find an app to process the emergency intent.");
            return;
        }
        WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifecycle;
        int i = wakefulnessLifecycle.mWakefulness;
        CentralSurfaces centralSurfaces = this.mCentralSurfaces;
        if (i == 3) {
            ((CentralSurfacesImpl) centralSurfaces).mLaunchEmergencyActionOnFinishedGoingToSleep = true;
            return;
        }
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfaces;
        if (!centralSurfacesImpl.mDeviceInteractive) {
            this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), 4, "com.android.systemui:EMERGENCY_GESTURE");
        }
        boolean z = ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing;
        UserTracker userTracker = this.mUserTracker;
        if (!z) {
            this.mActivityStarter.startActivityDismissingKeyguard(invoke, false, true, true, null, 0, null, ((UserTrackerImpl) userTracker).getUserHandle());
            return;
        }
        if (!centralSurfacesImpl.mDeviceInteractive) {
            centralSurfacesImpl.mGestureWakeLock.acquire(6000L);
        }
        int i2 = wakefulnessLifecycle.mWakefulness;
        if (i2 != 2 && i2 != 1) {
            centralSurfacesImpl.mLaunchEmergencyActionWhenFinishedWaking = true;
            return;
        }
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager;
        if (statusBarKeyguardViewManager.isBouncerShowing()) {
            statusBarKeyguardViewManager.reset(true);
        }
        this.mContext.startActivityAsUser(invoke, ((UserTrackerImpl) userTracker).getUserHandle());
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onFlashlightKeyPressed(int i) {
        DisplayLifecycle displayLifecycle;
        SubscreenFlashLightController subscreenFlashLightController;
        SecFlashlightControllerImpl secFlashlightControllerImpl = ((FlashlightControllerImpl) ((FlashlightController) Dependency.sDependency.getDependencyInner(FlashlightController.class))).mSecFlashlightController;
        secFlashlightControllerImpl.getClass();
        Log.d("SecFlashlightController", "onFlashlightKeyPressed: " + i);
        FlashlightControllerImpl flashlightControllerImpl = secFlashlightControllerImpl.mFlashlightController;
        if (!flashlightControllerImpl.isAvailable()) {
            if (secFlashlightControllerImpl.isThermalRestricted()) {
                secFlashlightControllerImpl.showWarningMessage(secFlashlightControllerImpl.mContext.getString(R.string.unable_to_turn_on_by_high_temperature));
                return;
            } else {
                secFlashlightControllerImpl.showUnavailableMessage();
                return;
            }
        }
        if (secFlashlightControllerImpl.mIsLowBattery) {
            secFlashlightControllerImpl.showWarningMessage(secFlashlightControllerImpl.mContext.getString(R.string.flash_light_disabled_by_low_battery));
            return;
        }
        if (ActivityManager.isUserAMonkey()) {
            return;
        }
        if (!QpRune.QUICK_SUBSCREEN_SETTINGS || (displayLifecycle = secFlashlightControllerImpl.mDisplayLifecycle) == null || displayLifecycle.mIsFolderOpened || (subscreenFlashLightController = secFlashlightControllerImpl.mSubscreenFlashlightController) == null) {
            flashlightControllerImpl.setFlashlight(!secFlashlightControllerImpl.mIsEnabled);
            return;
        }
        SubroomFlashLightSettingsActivity subroomFlashLightSettingsActivity = subscreenFlashLightController.mFlashLightPresentationView;
        if (subroomFlashLightSettingsActivity == null || subroomFlashLightSettingsActivity.getActivityState() == 0) {
            subscreenFlashLightController.startFlashActivity();
            return;
        }
        ((SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class)).closeSubscreenPanel();
        if (secFlashlightControllerImpl.mIsEnabled) {
            flashlightControllerImpl.setFlashlight(false);
        }
        secFlashlightControllerImpl.mUiHandler.post(new SecFlashlightControllerImpl$$ExternalSyntheticLambda0(secFlashlightControllerImpl, 1));
    }

    public final void onPowerButtonLaunchGestureTriggered(PowerButtonLaunchGestureTarget powerButtonLaunchGestureTarget, int i) {
        CoverState coverState;
        Intent intent;
        PowerButtonLaunchGestureTarget powerButtonLaunchGestureTarget2 = PowerButtonLaunchGestureTarget.LAUNCH_CAMERA_ON_GESTURE;
        CentralSurfaces centralSurfaces = this.mCentralSurfaces;
        if (powerButtonLaunchGestureTarget == powerButtonLaunchGestureTarget2) {
            ((CentralSurfacesImpl) centralSurfaces).mLastCameraLaunchSource = i;
        }
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfaces;
        if (centralSurfacesImpl.isGoingToSleep()) {
            int ordinal = powerButtonLaunchGestureTarget.ordinal();
            if (ordinal == 0) {
                Slog.d("CentralSurfaces", "setLaunchCameraOnFinishedGoingToSleep");
                centralSurfacesImpl.mLaunchCameraOnFinishedGoingToSleep = true;
                return;
            } else {
                if (ordinal != 1) {
                    return;
                }
                Slog.d("CentralSurfaces", "setLaunchWalletOnFinishedGoingToSleep");
                centralSurfacesImpl.mLaunchWalletOnFinishedGoingToSleep = true;
                return;
            }
        }
        Lazy lazy = this.mCameraLauncherLazy;
        CameraLauncher cameraLauncher = (CameraLauncher) lazy.get();
        PanelExpansionInteractor panelExpansionInteractor = this.mPanelExpansionInteractor;
        if (cameraLauncher.mCameraGestureHelper.canCameraGestureBeLaunched(panelExpansionInteractor.getBarState())) {
            if (powerButtonLaunchGestureTarget == powerButtonLaunchGestureTarget2) {
                ((KeyguardRepositoryImpl) this.mKeyguardInteractor.repository).onCameraLaunchDetected.updateState(null, new CameraLaunchSourceModel(KeyguardInteractor.cameraLaunchSourceIntToType(i), 0L, 2, null));
            }
            if (!centralSurfacesImpl.mDeviceInteractive) {
                this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), powerButtonLaunchGestureTarget == powerButtonLaunchGestureTarget2 ? 5 : 4, powerButtonLaunchGestureTarget == powerButtonLaunchGestureTarget2 ? "com.android.systemui:CAMERA_GESTURE" : "com.android.systemui:WALLET_GESTURE");
            }
            if (LsRune.COVER_SUPPORTED) {
                CoverHostImpl coverHostImpl = (CoverHostImpl) this.mCoverHost;
                if (coverHostImpl.mIsCoverClosed && !coverHostImpl.mIsCoverAppCovered && (coverState = coverHostImpl.mCoverState) != null && DeviceState.isCoverUIType(coverState.type)) {
                    int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                    KeyguardUpdateMonitor keyguardUpdateMonitor = coverHostImpl.mKeyguardUpdateMonitor;
                    boolean userCanSkipBouncer = keyguardUpdateMonitor.getUserCanSkipBouncer(currentUser);
                    if (!keyguardUpdateMonitor.isSecure() || userCanSkipBouncer) {
                        KeyguardShortcutManager.Companion.getClass();
                        intent = KeyguardShortcutManager.INSECURE_CAMERA_INTENT;
                        intent.putExtra("isSecure", false);
                    } else {
                        KeyguardShortcutManager.Companion.getClass();
                        intent = KeyguardShortcutManager.SECURE_CAMERA_INTENT;
                        intent.putExtra("isSecure", true);
                    }
                    Intent intent2 = intent;
                    intent2.putExtra("isQuickLaunchMode", true);
                    intent2.putExtra("com.android.systemui.camera_launch_source", 1);
                    if (((CentralSurfacesImpl) coverHostImpl.mCentralSurfaces).isForegroundComponentName(intent2.getComponent())) {
                        intent2.setFlags(270532608);
                    } else {
                        intent2.addFlags(872480768);
                    }
                    PendingIntent activityAsUser = PendingIntent.getActivityAsUser(coverHostImpl.mContext, 0, intent2, 201326592, null, UserHandle.of(KeyguardUpdateMonitor.getCurrentUser()));
                    Slog.d("CoverHostImpl", "make pending intent for cover toast.");
                    PluginAODManager pluginAODManager = (PluginAODManager) coverHostImpl.mPluginAODManagerLazy.get();
                    pluginAODManager.getClass();
                    Log.d("PluginAODManager", "showCoverToast() notiKey = ");
                    PluginCover pluginCover = pluginAODManager.mCoverPlugin;
                    if (pluginCover != null) {
                        pluginCover.showCoverToast(activityAsUser, true);
                    }
                    PluginSubScreen pluginSubScreen = pluginAODManager.mSubScreenPlugin;
                    if (pluginSubScreen != null) {
                        pluginSubScreen.requestOpenAppPopup(activityAsUser, true, "");
                        return;
                    }
                    return;
                }
            }
            if (powerButtonLaunchGestureTarget == powerButtonLaunchGestureTarget2 && i == 1) {
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = this.mKeyguardUpdateMonitor;
                keyguardUpdateMonitor2.mSecureCameraLaunched = true;
                keyguardUpdateMonitor2.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_CAMERA_LAUNCHED);
                if (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY) {
                    SubScreenManager subScreenManager = (SubScreenManager) this.mSubScreenManagerLazy.get();
                    if (subScreenManager.mSubScreenPlugin == null) {
                        Log.w("SubScreenManager", "onCameraLaunchedDoubleTap() no plugin");
                    } else {
                        Log.i("SubScreenManager", "onCameraLaunchedDoubleTap() ");
                        subScreenManager.mSubScreenPlugin.onCameraLaunchedDoubleTap();
                    }
                }
            }
            boolean z = ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing;
            QuickAccessWalletController quickAccessWalletController = this.mWalletController;
            ActivityStarter activityStarter = this.mActivityStarter;
            if (!z) {
                int ordinal2 = powerButtonLaunchGestureTarget.ordinal();
                if (ordinal2 == 0) {
                    KeyguardShortcutManager.Companion.getClass();
                    this.mActivityStarter.startActivityDismissingKeyguard(KeyguardShortcutManager.INSECURE_CAMERA_INTENT, false, true, true, null, 0, null, ((UserTrackerImpl) this.mUserTracker).getUserHandle(), i);
                    return;
                } else {
                    if (ordinal2 != 1) {
                        return;
                    }
                    quickAccessWalletController.mQuickAccessWalletClient.getGestureTargetActivityPendingIntent(quickAccessWalletController.mExecutor, new QuickAccessWalletController$$ExternalSyntheticLambda2(quickAccessWalletController, activityStarter));
                    return;
                }
            }
            if (!centralSurfacesImpl.mDeviceInteractive) {
                centralSurfacesImpl.mGestureWakeLock.acquire(6000L);
            }
            int i2 = this.mWakefulnessLifecycle.mWakefulness;
            if (i2 != 2 && i2 != 1) {
                int ordinal3 = powerButtonLaunchGestureTarget.ordinal();
                if (ordinal3 == 0) {
                    centralSurfacesImpl.mLaunchCameraWhenFinishedWaking = true;
                    return;
                } else {
                    if (ordinal3 != 1) {
                        return;
                    }
                    centralSurfacesImpl.mLaunchWalletWhenFinishedWaking = true;
                    return;
                }
            }
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager;
            if (statusBarKeyguardViewManager.isBouncerShowing()) {
                statusBarKeyguardViewManager.reset(true);
            }
            centralSurfacesImpl.mMessageRouter.sendMessageDelayed(1003, 5000L);
            int ordinal4 = powerButtonLaunchGestureTarget.ordinal();
            if (ordinal4 == 0) {
                ((CameraLauncher) lazy.get()).launchCamera(i, panelExpansionInteractor.isFullyCollapsed());
            } else if (ordinal4 == 1) {
                quickAccessWalletController.mQuickAccessWalletClient.getGestureTargetActivityPendingIntent(quickAccessWalletController.mExecutor, new QuickAccessWalletController$$ExternalSyntheticLambda2(quickAccessWalletController, activityStarter));
            }
            centralSurfaces.updateScrimController();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onRecentsAnimationStateChanged(boolean z) {
        ((CentralSurfacesImpl) this.mCentralSurfaces).setInteracting(2, z);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onWalletLaunchGestureDetected() {
        onPowerButtonLaunchGestureTriggered(PowerButtonLaunchGestureTarget.LAUNCH_WALLET_ON_GESTURE, -1);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void remQsTile(ComponentName componentName) {
        this.mQSHost.removeTileByUser(componentName);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setQsTiles(String[] strArr) {
        QSHost qSHost = this.mQSHost;
        qSHost.changeTilesByUser(qSHost.getSpecs(), Arrays.stream(strArr).toList());
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setTopAppHidesStatusBar(boolean z) {
        StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager = this.mStatusBarHideIconsForBouncerManager;
        statusBarHideIconsForBouncerManager.topAppHidesStatusBar = z;
        if (!z && statusBarHideIconsForBouncerManager.wereIconsJustHidden) {
            statusBarHideIconsForBouncerManager.wereIconsJustHidden = false;
            statusBarHideIconsForBouncerManager.commandQueue.recomputeDisableFlags(statusBarHideIconsForBouncerManager.displayId, true);
        }
        statusBarHideIconsForBouncerManager.updateHideIconsForBouncer(true);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showAssistDisclosure() {
        this.mAssistManager.showDisclosure();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showScreenPinningRequest(int i) {
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
            return;
        }
        ScreenPinningRequest screenPinningRequest = this.mScreenPinningRequest;
        screenPinningRequest.getClass();
        try {
            screenPinningRequest.clearPrompt();
        } catch (IllegalArgumentException unused) {
        }
        screenPinningRequest.taskId = i;
        ScreenPinningRequest.RequestWindowView requestWindowView = new ScreenPinningRequest.RequestWindowView(screenPinningRequest, screenPinningRequest.mContext);
        screenPinningRequest.mRequestWindow = requestWindowView;
        requestWindowView.setSystemUiVisibility(256);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2024, 264, -3);
        layoutParams.token = new Binder();
        layoutParams.privateFlags |= 16;
        layoutParams.setTitle("ScreenPinningConfirmation");
        layoutParams.gravity = 119;
        layoutParams.setFitInsetsTypes(0);
        screenPinningRequest.mWindowManager.addView(screenPinningRequest.mRequestWindow, layoutParams);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showWirelessChargingAnimation(int i) {
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
        WirelessChargingAnimation.WirelessChargingView wirelessChargingView = WirelessChargingAnimation.makeWirelessChargingAnimation(centralSurfacesImpl.mContext, i, centralSurfacesImpl.new AnonymousClass7(), RippleShader.RippleShape.CIRCLE, CentralSurfacesImpl.sUiEventLogger, centralSurfacesImpl.mWindowManager, centralSurfacesImpl.mWindowManagerProvider).mCurrentWirelessChargingView;
        if (wirelessChargingView == null || wirelessChargingView.mNextView == null) {
            throw new RuntimeException("setView must have been called");
        }
        WirelessChargingAnimation.WirelessChargingView wirelessChargingView2 = WirelessChargingAnimation.mPreviousWirelessChargingView;
        if (wirelessChargingView2 != null) {
            wirelessChargingView2.hide(0L);
        }
        WirelessChargingAnimation.mPreviousWirelessChargingView = wirelessChargingView;
        if (WirelessChargingAnimation.DEBUG) {
            Slog.d("WirelessChargingView", "SHOW: " + wirelessChargingView);
        }
        WirelessChargingAnimation.WirelessChargingView.AnonymousClass1 anonymousClass1 = wirelessChargingView.mHandler;
        anonymousClass1.sendMessageDelayed(Message.obtain(anonymousClass1, 0), 0L);
        wirelessChargingView.hide(1500L);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void startAssist(Bundle bundle) {
        this.mAssistManager.startAssist(bundle);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void startSearcleByHomeKey(Boolean bool, Boolean bool2) {
        if (BasicRune.SEARCLE) {
            this.mSearcleManager.startSearcleByHomeKey(bool.booleanValue(), bool2.booleanValue());
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void suppressAmbientDisplay(boolean z) {
        DozeServiceHost dozeServiceHost = this.mDozeServiceHost;
        if (z == dozeServiceHost.mAlwaysOnSuppressed) {
            return;
        }
        dozeServiceHost.mAlwaysOnSuppressed = z;
        Assert.isMainThread();
        Iterator<E> it = dozeServiceHost.mCallbacks.iterator();
        while (it.hasNext()) {
            ((DozeHost.Callback) it.next()).onAlwaysOnSuppressedChanged(z);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void toggleKeyboardShortcutsMenu(int i) {
        CentralSurfaces.KeyboardShortcutsMessage keyboardShortcutsMessage = new CentralSurfaces.KeyboardShortcutsMessage(i);
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
        centralSurfacesImpl.getClass();
        MessageRouter messageRouter = centralSurfacesImpl.mMessageRouter;
        messageRouter.cancelMessages(CentralSurfaces.KeyboardShortcutsMessage.class);
        messageRouter.sendMessage(keyboardShortcutsMessage);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void toggleNotificationsPanel() {
        boolean booleanValue = ((Boolean) ((ShadeInteractorImpl) ((ShadeInteractor) this.mShadeInteractorLazy.get())).baseShadeInteractor.isAnyExpanded().getValue()).booleanValue();
        ShadeController shadeController = this.mShadeController;
        if (booleanValue) {
            shadeController.animateCollapseShade(0);
        } else {
            ((BaseShadeControllerImpl) shadeController).animateExpandShade();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void toggleQuickSettingsPanel() {
        boolean booleanValue = ((Boolean) ((ShadeInteractorImpl) ((ShadeInteractor) this.mShadeInteractorLazy.get())).baseShadeInteractor.isQsExpanded().getValue()).booleanValue();
        ShadeController shadeController = this.mShadeController;
        if (booleanValue) {
            shadeController.animateCollapseShade(0);
        } else {
            ((BaseShadeControllerImpl) shadeController).animateExpandQs();
        }
    }

    public void vibrateOnNavigationKeyDown() {
        this.mShadeController.performHapticFeedback(12);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void appTransitionCancelled(int i) {
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void appTransitionFinished(int i) {
    }
}
