package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
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
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.view.AppearanceRegion;
import com.android.keyguard.FaceAuthUiEvent;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.SysUIToast;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.camera.CameraGestureHelper;
import com.android.systemui.charging.WirelessChargingAnimation;
import com.android.systemui.cover.CoverHost;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.ScreenPinningNotify;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.p014qp.SubscreenQSControllerContract$FlashLightView;
import com.android.systemui.p014qp.flashlight.SubroomFlashLightSettingsActivity;
import com.android.systemui.p014qp.flashlight.SubscreenFlashLightController;
import com.android.systemui.p014qp.util.SubscreenUtil;
import com.android.systemui.p016qs.QSHost;
import com.android.systemui.p016qs.QSPanelHost;
import com.android.systemui.p016qs.QSPanelHost$$ExternalSyntheticLambda0;
import com.android.systemui.p016qs.QSPanelHost$$ExternalSyntheticLambda1;
import com.android.systemui.p016qs.QSPanelHost$$ExternalSyntheticLambda5;
import com.android.systemui.p016qs.SecQSPanelController;
import com.android.systemui.p016qs.external.CustomTile;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.cover.PluginCover;
import com.android.systemui.plugins.subscreen.PluginSubScreen;
import com.android.systemui.searcle.SearcleManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.CameraLauncher;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.shade.SecPanelBlockExpandingHelper;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.KeyboardShortcuts;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.disableflags.DisableFlagsLogger;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda11;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl.C30268;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.NoRemeasureMotionLayout;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.concurrency.MessageRouter;
import com.android.systemui.util.concurrency.MessageRouterImpl;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CentralSurfacesCommandQueueCallbacks implements CommandQueue.Callbacks {
    public final ActivityStarter mActivityStarter;
    public final AssistManager mAssistManager;
    public final Lazy mCameraLauncherLazy;
    public final CentralSurfaces mCentralSurfaces;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final CoverHost mCoverHost;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public final DisableFlagsLogger mDisableFlagsLogger;
    public final int mDisplayId;
    public final DozeServiceHost mDozeServiceHost;
    public final HeadsUpManager mHeadsUpManager;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final MetricsLogger mMetricsLogger;
    public final NotificationStackScrollLayoutController mNotificationStackScrollLayoutController;
    public final SecPanelBlockExpandingHelper mPanelBlockExpandingHelper;
    public final PowerManager mPowerManager;
    public final QSHost mQSHost;
    public final QuickSettingsController mQsController;
    public final RemoteInputQuickSettingsDisabler mRemoteInputQuickSettingsDisabler;
    public final SearcleManager mSearcleManager;
    public final ShadeController mShadeController;
    public final ShadeViewController mShadeViewController;
    public final StatusBarHideIconsForBouncerManager mStatusBarHideIconsForBouncerManager;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final SystemBarAttributesListener mSystemBarAttributesListener;
    public final UserTracker mUserTracker;
    public final boolean mVibrateOnOpening;
    public final VibratorHelper mVibratorHelper;
    public final WakefulnessLifecycle mWakefulnessLifecycle;

    static {
        VibrationAttributes.createForUsage(50);
    }

    public CentralSurfacesCommandQueueCallbacks(CoverHost coverHost, CentralSurfaces centralSurfaces, QuickSettingsController quickSettingsController, Context context, Resources resources, ShadeController shadeController, CommandQueue commandQueue, ShadeViewController shadeViewController, RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler, MetricsLogger metricsLogger, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, HeadsUpManager headsUpManager, WakefulnessLifecycle wakefulnessLifecycle, DeviceProvisionedController deviceProvisionedController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, AssistManager assistManager, DozeServiceHost dozeServiceHost, NotificationStackScrollLayoutController notificationStackScrollLayoutController, StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager, PowerManager powerManager, VibratorHelper vibratorHelper, Optional<Vibrator> optional, DisableFlagsLogger disableFlagsLogger, int i, SystemBarAttributesListener systemBarAttributesListener, Lazy lazy, UserTracker userTracker, QSHost qSHost, ActivityStarter activityStarter, SearcleManager searcleManager) {
        this.mCentralSurfaces = centralSurfaces;
        this.mQsController = quickSettingsController;
        this.mContext = context;
        this.mShadeController = shadeController;
        this.mCommandQueue = commandQueue;
        this.mShadeViewController = shadeViewController;
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
        this.mVibratorHelper = vibratorHelper;
        this.mDisableFlagsLogger = disableFlagsLogger;
        this.mDisplayId = i;
        this.mCameraLauncherLazy = lazy;
        this.mUserTracker = userTracker;
        this.mQSHost = qSHost;
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
        this.mSystemBarAttributesListener = systemBarAttributesListener;
        if (LsRune.COVER_SUPPORTED) {
            this.mCoverHost = coverHost;
        }
        this.mActivityStarter = activityStarter;
        this.mPanelBlockExpandingHelper = (SecPanelBlockExpandingHelper) Dependency.get(SecPanelBlockExpandingHelper.class);
        if (BasicRune.SEARCLE) {
            this.mSearcleManager = searcleManager;
        } else {
            this.mSearcleManager = null;
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void abortTransient(int i, int i2) {
        if (i == this.mDisplayId && (WindowInsets.Type.statusBars() & i2) != 0) {
            CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
            if (centralSurfacesImpl.mTransientShown) {
                centralSurfacesImpl.mTransientShown = false;
                centralSurfacesImpl.maybeUpdateBarMode();
            }
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void addQsTile(ComponentName componentName) {
        componentName.getClassName();
        this.mQSHost.addTile(componentName);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void animateCollapsePanels(int i, boolean z) {
        ((ShadeControllerImpl) this.mShadeController).animateCollapsePanels(i, z, false, 1.0f, true);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void animateExpandNotificationsPanel() {
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("animateExpand: mExpandedVisible="), ((ShadeControllerImpl) this.mShadeController).mExpandedVisible, "CentralSurfaces");
        if (this.mCommandQueue.panelsEnabled()) {
            ((NotificationPanelViewController) this.mShadeViewController).expandToNotifications();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void animateExpandSettingsPanel(String str) {
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("animateExpand: mExpandedVisible="), ((ShadeControllerImpl) this.mShadeController).mExpandedVisible, "CentralSurfaces");
        if (this.mCommandQueue.panelsEnabled() && ((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).isCurrentUserSetup()) {
            if (!QpRune.QUICK_PANEL_SUBSCREEN || ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) {
                ((NotificationPanelViewController) this.mShadeViewController).expandToQs();
            }
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void clickTile(ComponentName componentName) {
        componentName.getClassName();
        SecQSPanelController secQSPanelController = ((CentralSurfacesImpl) this.mCentralSurfaces).mQSPanelController;
        if (secQSPanelController != null) {
            QSPanelHost qSPanelHost = secQSPanelController.mQsPanelHost;
            qSPanelHost.getClass();
            qSPanelHost.mRecords.stream().map(new QSPanelHost$$ExternalSyntheticLambda0(5)).filter(new QSPanelHost$$ExternalSyntheticLambda5(CustomTile.toSpec(componentName), 1)).findFirst().ifPresent(new QSPanelHost$$ExternalSyntheticLambda1(4));
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void disable(int i, int i2, int i3, boolean z) {
        View view;
        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("disable ", i, " ", i2, " ");
        m45m.append(i3);
        m45m.append(" ");
        m45m.append(z);
        Log.d("CentralSurfaces", m45m.toString());
        if (i != this.mDisplayId) {
            return;
        }
        this.mRemoteInputQuickSettingsDisabler.getClass();
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
        Log.d("CentralSurfaces", this.mDisableFlagsLogger.getDisableFlagsString(new DisableFlagsLogger.DisableState(centralSurfacesImpl.mDisabled1, centralSurfacesImpl.mDisabled2), new DisableFlagsLogger.DisableState(i2, i3), new DisableFlagsLogger.DisableState(i2, i3)));
        int i4 = centralSurfacesImpl.mDisabled1 ^ i2;
        centralSurfacesImpl.mDisabled1 = i2;
        int i5 = centralSurfacesImpl.mDisabled2 ^ i3;
        centralSurfacesImpl.mDisabled2 = i3;
        int i6 = i4 & 65536;
        HeadsUpManager headsUpManager = this.mHeadsUpManager;
        ShadeController shadeController = this.mShadeController;
        if (i6 != 0 && (65536 & i2) != 0) {
            Log.d("CentralSurfaces", "disable DISABLE_EXPAND");
            if (!this.mKeyguardStateController.isVisible()) {
                ShadeControllerImpl shadeControllerImpl = (ShadeControllerImpl) shadeController;
                if (shadeControllerImpl.mNotificationPanelViewController.isExpandingOrCollapsing()) {
                    shadeControllerImpl.instantCollapseShade();
                } else {
                    shadeControllerImpl.animateCollapseShade(0);
                }
            }
            headsUpManager.releaseAllImmediately();
        }
        if ((i4 & 262144) != 0) {
            Log.d("CentralSurfaces", "disable DISABLE_NOTIFICATION_ALERTS");
            if ((262144 & centralSurfacesImpl.mDisabled1) != 0) {
                headsUpManager.releaseAllImmediately();
            }
        }
        if ((i5 & 1) != 0) {
            Log.d("CentralSurfaces", "disable DISABLE2_QUICK_SETTINGS");
            centralSurfacesImpl.updateQsExpansionEnabled();
        }
        if ((i5 & 4) != 0) {
            centralSurfacesImpl.updateQsExpansionEnabled();
            if ((i3 & 4) != 0) {
                Log.d("CentralSurfaces", "disable DISABLE2_NOTIFICATION_SHADE");
                ((ShadeControllerImpl) shadeController).animateCollapseShade(0);
            }
        }
        ShadeHeaderController shadeHeaderController = ((NotificationPanelViewController) this.mShadeViewController).mShadeHeaderController;
        shadeHeaderController.getClass();
        boolean z2 = (i3 & 1) != 0;
        if (z2 != shadeHeaderController.qsDisabled) {
            ((NoRemeasureMotionLayout) shadeHeaderController.header).disabled = z2;
            shadeHeaderController.qsDisabled = z2;
            shadeHeaderController.updateVisibility();
        }
        SecPanelBlockExpandingHelper secPanelBlockExpandingHelper = this.mPanelBlockExpandingHelper;
        secPanelBlockExpandingHelper.getClass();
        if ((i4 & QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT) == 0) {
            return;
        }
        boolean z3 = (i2 & QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT) == 0;
        NavigationBar defaultNavigationBar = secPanelBlockExpandingHelper.mNavigationBarController.getDefaultNavigationBar();
        if (defaultNavigationBar != null && BasicRune.NAVBAR_DISABLE_TOUCH && (view = defaultNavigationBar.mView) != null && ((NavigationBarView) view).isAttachedToWindow()) {
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) ((View) ((NavigationBarView) defaultNavigationBar.mView).getParent()).getLayoutParams();
            SysUiState sysUiState = defaultNavigationBar.mSysUiFlagsContainer;
            if (z3) {
                layoutParams.flags &= -17;
                ((NavigationBarView) defaultNavigationBar.mView).updateDisabledSystemUiStateFlags(sysUiState);
            } else {
                layoutParams.flags |= 16;
                sysUiState.setFlag(128L, true);
                sysUiState.setFlag(256L, true);
                sysUiState.setFlag(4194304L, true);
                sysUiState.commitUpdate(defaultNavigationBar.mDisplayId);
            }
            defaultNavigationBar.mWindowManager.updateViewLayout((View) ((NavigationBarView) defaultNavigationBar.mView).getParent(), layoutParams);
        }
        StatusBarWindowController statusBarWindowController = secPanelBlockExpandingHelper.mStatusBarWindowController;
        WindowManager.LayoutParams layoutParams2 = statusBarWindowController.mLpChanged;
        if (z3) {
            layoutParams2.flags &= -17;
        } else {
            layoutParams2.flags |= 16;
        }
        WindowManager.LayoutParams layoutParams3 = statusBarWindowController.mLp;
        if (layoutParams3 == null || layoutParams3.copyFrom(layoutParams2) == 0) {
            return;
        }
        statusBarWindowController.mWindowManager.updateViewLayout(statusBarWindowController.mStatusBarWindowView, statusBarWindowController.mLp);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void dismissKeyboardShortcutsMenu() {
        MessageRouter messageRouter = ((CentralSurfacesImpl) this.mCentralSurfaces).mMessageRouter;
        ((MessageRouterImpl) messageRouter).cancelMessages(1027);
        messageRouter.getClass();
        ((MessageRouterImpl) messageRouter).sendMessageDelayed(1027, 0L);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void handleSystemKey(KeyEvent keyEvent) {
        if (this.mCommandQueue.panelsEnabled() && this.mKeyguardUpdateMonitor.mDeviceInteractive) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
            if ((!keyguardStateControllerImpl.mShowing || keyguardStateControllerImpl.mOccluded) && ((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).isCurrentUserSetup()) {
                int keyCode = keyEvent.getKeyCode();
                ShadeViewController shadeViewController = this.mShadeViewController;
                MetricsLogger metricsLogger = this.mMetricsLogger;
                if (280 == keyCode) {
                    metricsLogger.action(493);
                    ((NotificationPanelViewController) shadeViewController).collapse(1.0f, false);
                    return;
                }
                if (281 == keyEvent.getKeyCode()) {
                    metricsLogger.action(494);
                    NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) shadeViewController;
                    if (notificationPanelViewController.isFullyCollapsed()) {
                        if (this.mVibrateOnOpening) {
                            this.mVibratorHelper.vibrate(2);
                        }
                        notificationPanelViewController.expand(true);
                        this.mNotificationStackScrollLayoutController.mView.mWillExpand = true;
                        this.mHeadsUpManager.unpinAll();
                        metricsLogger.count("panel_open", 1);
                        return;
                    }
                    QuickSettingsController quickSettingsController = this.mQsController;
                    if (quickSettingsController.mExpanded || notificationPanelViewController.isExpandingOrCollapsing()) {
                        return;
                    }
                    quickSettingsController.flingQs(0.0f, 0);
                    metricsLogger.count("panel_open_qs", 1);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0060 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0061  */
    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onCameraLaunchGestureDetected(int i) {
        boolean z;
        boolean z2;
        CoverState coverState;
        Intent intent;
        ActivityInfo activityInfo;
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
        centralSurfacesImpl.mLastCameraLaunchSource = i;
        if (centralSurfacesImpl.isGoingToSleep()) {
            centralSurfacesImpl.mLaunchCameraOnFinishedGoingToSleep = true;
            return;
        }
        Lazy lazy = this.mCameraLauncherLazy;
        CameraLauncher cameraLauncher = (CameraLauncher) lazy.get();
        NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this.mShadeViewController;
        int i2 = notificationPanelViewController.mBarState;
        CameraGestureHelper cameraGestureHelper = cameraLauncher.mCameraGestureHelper;
        if (((CentralSurfacesImpl) cameraGestureHelper.centralSurfaces).isCameraAllowedByAdmin()) {
            ResolveInfo resolveActivityAsUser = cameraGestureHelper.packageManager.resolveActivityAsUser(cameraGestureHelper.getStartCameraIntent(), 65536, KeyguardUpdateMonitor.getCurrentUser());
            if (((resolveActivityAsUser == null || (activityInfo = resolveActivityAsUser.activityInfo) == null) ? null : activityInfo.packageName) != null && (i2 != 0 || (!r7.isForegroundComponentName(resolveActivityAsUser.activityInfo.getComponentName())))) {
                z = true;
                if (z) {
                    return;
                }
                if (!centralSurfacesImpl.mDeviceInteractive) {
                    this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), 5, "com.android.systemui:CAMERA_GESTURE");
                }
                if (LsRune.COVER_SUPPORTED) {
                    CoverHostImpl coverHostImpl = (CoverHostImpl) this.mCoverHost;
                    if (!coverHostImpl.mIsCoverClosed || coverHostImpl.mIsCoverAppCovered || (coverState = coverHostImpl.mCoverState) == null || !DeviceState.isCoverUIType(coverState.type)) {
                        z2 = false;
                    } else {
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
                        KeyguardBottomAreaView.Companion.getClass();
                        intent2.putExtra("com.android.systemui.camera_launch_source", KeyguardBottomAreaView.CAMERA_LAUNCH_SOURCE_POWER_DOUBLE_TAP);
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
                        }
                        z2 = true;
                    }
                    if (z2) {
                        return;
                    }
                }
                if (i == 1) {
                    KeyguardUpdateMonitor keyguardUpdateMonitor2 = this.mKeyguardUpdateMonitor;
                    keyguardUpdateMonitor2.mSecureCameraLaunched = true;
                    keyguardUpdateMonitor2.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_CAMERA_LAUNCHED);
                    if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
                        SubScreenManager subScreenManager = (SubScreenManager) Dependency.get(SubScreenManager.class);
                        if (subScreenManager.mSubScreenPlugin == null) {
                            Log.w("SubScreenManager", "onCameraLaunchedDoubleTap() no plugin");
                        } else {
                            Log.i("SubScreenManager", "onCameraLaunchedDoubleTap() ");
                            subScreenManager.mSubScreenPlugin.onCameraLaunchedDoubleTap();
                        }
                    }
                }
                if (!((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
                    ActivityStarter activityStarter = this.mActivityStarter;
                    KeyguardShortcutManager.Companion.getClass();
                    activityStarter.startActivityDismissingKeyguard(KeyguardShortcutManager.INSECURE_CAMERA_INTENT, false, true, true, null, 0, null, ((UserTrackerImpl) this.mUserTracker).getUserHandle(), i);
                    return;
                }
                if (!centralSurfacesImpl.mDeviceInteractive) {
                    centralSurfacesImpl.mGestureWakeLock.acquire(6000L);
                }
                int i3 = this.mWakefulnessLifecycle.mWakefulness;
                if (!(i3 == 2 || i3 == 1)) {
                    centralSurfacesImpl.mLaunchCameraWhenFinishedWaking = true;
                    return;
                }
                StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager;
                if (statusBarKeyguardViewManager.isBouncerShowing()) {
                    statusBarKeyguardViewManager.reset(true);
                }
                ((CameraLauncher) lazy.get()).launchCamera(i, notificationPanelViewController.isFullyCollapsed());
                centralSurfacesImpl.updateScrimController();
                return;
            }
        }
        z = false;
        if (z) {
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onEmergencyActionLaunchGestureDetected() {
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
        Intent emergencyActionIntent = centralSurfacesImpl.getEmergencyActionIntent();
        if (emergencyActionIntent == null) {
            Log.wtf("CentralSurfaces", "Couldn't find an app to process the emergency intent.");
            return;
        }
        WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifecycle;
        if (wakefulnessLifecycle.mWakefulness == 3) {
            centralSurfacesImpl.mLaunchEmergencyActionOnFinishedGoingToSleep = true;
            return;
        }
        if (!centralSurfacesImpl.mDeviceInteractive) {
            this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), 4, "com.android.systemui:EMERGENCY_GESTURE");
        }
        boolean z = ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing;
        UserTracker userTracker = this.mUserTracker;
        if (!z) {
            this.mActivityStarter.startActivityDismissingKeyguard(emergencyActionIntent, false, true, true, null, 0, null, ((UserTrackerImpl) userTracker).getUserHandle());
            return;
        }
        if (!centralSurfacesImpl.mDeviceInteractive) {
            centralSurfacesImpl.mGestureWakeLock.acquire(6000L);
        }
        int i = wakefulnessLifecycle.mWakefulness;
        if (!(i == 2 || i == 1)) {
            centralSurfacesImpl.mLaunchEmergencyActionWhenFinishedWaking = true;
            return;
        }
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager;
        if (statusBarKeyguardViewManager.isBouncerShowing()) {
            statusBarKeyguardViewManager.reset(true);
        }
        this.mContext.startActivityAsUser(emergencyActionIntent, ((UserTrackerImpl) userTracker).getUserHandle());
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onFlashlightKeyPressed(int i) {
        DisplayLifecycle displayLifecycle;
        SubscreenFlashLightController subscreenFlashLightController;
        boolean z;
        FlashlightControllerImpl flashlightControllerImpl = (FlashlightControllerImpl) ((FlashlightController) Dependency.get(FlashlightController.class));
        flashlightControllerImpl.getClass();
        Log.d("FlashlightControllerImpl", "onFlashlightKeyPressed: " + i);
        if (!(flashlightControllerImpl.mCameraId != null)) {
            Log.d("FlashlightControllerImpl", "CameraManager is not ready");
            flashlightControllerImpl.updateTorchCallback();
        }
        boolean isAvailable = flashlightControllerImpl.isAvailable();
        Context context = flashlightControllerImpl.mContext;
        if (!isAvailable) {
            synchronized (flashlightControllerImpl) {
                z = flashlightControllerImpl.mIsThermalRestricted;
            }
            if (z) {
                flashlightControllerImpl.showWarningMessage(context.getString(R.string.unable_to_turn_on_by_high_temperature));
                return;
            } else {
                flashlightControllerImpl.showUnavailableMessage();
                return;
            }
        }
        if (flashlightControllerImpl.mIsLowBattery) {
            flashlightControllerImpl.showWarningMessage(context.getString(R.string.flash_light_disabled_by_low_battery));
            return;
        }
        if (ActivityManager.isUserAMonkey()) {
            return;
        }
        if (!QpRune.QUICK_SETTINGS_SUBSCREEN || (displayLifecycle = flashlightControllerImpl.mDisplayLifecycle) == null || displayLifecycle.mIsFolderOpened || (subscreenFlashLightController = flashlightControllerImpl.mSubscreenFlashlightController) == null) {
            flashlightControllerImpl.setFlashlight(!flashlightControllerImpl.isEnabled());
            return;
        }
        SubscreenQSControllerContract$FlashLightView subscreenQSControllerContract$FlashLightView = subscreenFlashLightController.mFlashLightPresentationView;
        if (!((subscreenQSControllerContract$FlashLightView == null || ((SubroomFlashLightSettingsActivity) subscreenQSControllerContract$FlashLightView).getActivityState() == 0) ? false : true)) {
            subscreenFlashLightController.startFlashActivity();
            return;
        }
        ((SubscreenUtil) Dependency.get(SubscreenUtil.class)).closeSubscreenPanel();
        if (flashlightControllerImpl.isEnabled()) {
            flashlightControllerImpl.setFlashlight(false);
        }
        flashlightControllerImpl.mUiHandler.post(new FlashlightControllerImpl$$ExternalSyntheticLambda0(flashlightControllerImpl, 4));
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onRecentsAnimationStateChanged(boolean z) {
        ((CentralSurfacesImpl) this.mCentralSurfaces).setInteracting(2, z);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) {
        if (i != this.mDisplayId) {
            return;
        }
        this.mSystemBarAttributesListener.onSystemBarAttributesChanged(i, i2, appearanceRegionArr, z, i3, i4, str, letterboxDetailsArr);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void remQsTile(ComponentName componentName) {
        componentName.getClassName();
        this.mQSHost.removeTileByUser(componentName);
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
    public final void showPinningEnterExitToast(boolean z) {
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
        int i = centralSurfacesImpl.mDisplayId;
        NavigationBarController navigationBarController = centralSurfacesImpl.mNavigationBarController;
        NavigationBarView navigationBarView = navigationBarController.getNavigationBarView(i);
        if (navigationBarView != null) {
            if (z) {
                SysUIToast.makeText(R.string.sec_screen_pinning_start, navigationBarView.mScreenPinningNotify.mContext, 1).show();
                return;
            } else {
                SysUIToast.makeText(R.string.sec_screen_pinning_exit, navigationBarView.mScreenPinningNotify.mContext, 1).show();
                return;
            }
        }
        boolean z2 = BasicRune.NAVBAR_AOSP_BUG_FIX;
        TaskbarDelegate taskbarDelegate = navigationBarController.mTaskbarDelegate;
        if (!z2 && i == 0 && taskbarDelegate.mInitialized) {
            taskbarDelegate.showPinningEnterExitToast(z);
            return;
        }
        if (!BasicRune.NAVBAR_ENABLED_HARD_KEY || taskbarDelegate.mInitialized) {
            return;
        }
        ScreenPinningNotify screenPinningNotify = navigationBarController.mScreenPinningNotify;
        if (z) {
            SysUIToast.makeText(R.string.sec_screen_pinning_start, screenPinningNotify.mContext, 1).show();
        } else {
            SysUIToast.makeText(R.string.sec_screen_pinning_exit, screenPinningNotify.mContext, 1).show();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showPinningEscapeToast() {
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
        int i = centralSurfacesImpl.mDisplayId;
        NavigationBarController navigationBarController = centralSurfacesImpl.mNavigationBarController;
        NavigationBarView navigationBarView = navigationBarController.getNavigationBarView(i);
        TaskbarDelegate taskbarDelegate = navigationBarController.mTaskbarDelegate;
        if (navigationBarView != null && !taskbarDelegate.mInitialized) {
            navigationBarView.showPinningEscapeToast();
            return;
        }
        if (!BasicRune.NAVBAR_AOSP_BUG_FIX && i == 0 && taskbarDelegate.mInitialized) {
            taskbarDelegate.showPinningEscapeToast();
        } else {
            if (!BasicRune.NAVBAR_ENABLED_HARD_KEY || taskbarDelegate.mInitialized) {
                return;
            }
            navigationBarController.mScreenPinningNotify.showEscapeToast(false, true);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showScreenPinningRequest(int i) {
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
            return;
        }
        ((CentralSurfacesImpl) this.mCentralSurfaces).showScreenPinningRequest(i, null, false);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showTransient(int i, int i2, boolean z) {
        if (i == this.mDisplayId && (WindowInsets.Type.statusBars() & i2) != 0) {
            CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
            if (centralSurfacesImpl.mTransientShown) {
                return;
            }
            centralSurfacesImpl.mTransientShown = true;
            centralSurfacesImpl.mNoAnimationOnNextBarModeChange = true;
            centralSurfacesImpl.maybeUpdateBarMode();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showWirelessChargingAnimation(int i) {
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
        WirelessChargingAnimation.WirelessChargingView wirelessChargingView = WirelessChargingAnimation.makeWirelessChargingAnimation(centralSurfacesImpl.mContext, i, centralSurfacesImpl.new C30268(), RippleShader.RippleShape.CIRCLE, CentralSurfacesImpl.sUiEventLogger).mCurrentWirelessChargingView;
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
        WirelessChargingAnimation.WirelessChargingView.HandlerC11371 handlerC11371 = wirelessChargingView.mHandler;
        handlerC11371.sendMessageDelayed(Message.obtain(handlerC11371, 0), 0L);
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
        Iterator it = dozeServiceHost.mCallbacks.iterator();
        while (it.hasNext()) {
            ((DozeHost.Callback) it.next()).onAlwaysOnSuppressedChanged(z);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void toggleKeyboardShortcutsMenu(int i) {
        CentralSurfaces centralSurfaces = this.mCentralSurfaces;
        final CentralSurfaces.KeyboardShortcutsMessage keyboardShortcutsMessage = new CentralSurfaces.KeyboardShortcutsMessage(i);
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfaces;
        MessageRouterImpl messageRouterImpl = (MessageRouterImpl) centralSurfacesImpl.mMessageRouter;
        synchronized (messageRouterImpl.mDataMessageCancelers) {
            if (((HashMap) messageRouterImpl.mDataMessageCancelers).containsKey(CentralSurfaces.KeyboardShortcutsMessage.class)) {
                Iterator it = ((List) ((HashMap) messageRouterImpl.mDataMessageCancelers).get(CentralSurfaces.KeyboardShortcutsMessage.class)).iterator();
                while (it.hasNext()) {
                    ((Runnable) it.next()).run();
                }
                ((HashMap) messageRouterImpl.mDataMessageCancelers).remove(CentralSurfaces.KeyboardShortcutsMessage.class);
            }
        }
        MessageRouter messageRouter = centralSurfacesImpl.mMessageRouter;
        messageRouter.getClass();
        final MessageRouterImpl messageRouterImpl2 = (MessageRouterImpl) messageRouter;
        ExecutorImpl.ExecutionToken executeDelayed = messageRouterImpl2.mDelayableExecutor.executeDelayed(0L, new Runnable() { // from class: com.android.systemui.util.concurrency.MessageRouterImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                MessageRouterImpl messageRouterImpl3 = MessageRouterImpl.this;
                Object obj = keyboardShortcutsMessage;
                synchronized (messageRouterImpl3.mDataMessageListenerMap) {
                    if (((HashMap) messageRouterImpl3.mDataMessageListenerMap).containsKey(obj.getClass())) {
                        Iterator it2 = ((List) ((HashMap) messageRouterImpl3.mDataMessageListenerMap).get(obj.getClass())).iterator();
                        while (it2.hasNext()) {
                            CentralSurfacesImpl$$ExternalSyntheticLambda11 centralSurfacesImpl$$ExternalSyntheticLambda11 = (CentralSurfacesImpl$$ExternalSyntheticLambda11) ((MessageRouter.DataMessageListener) it2.next());
                            int i2 = centralSurfacesImpl$$ExternalSyntheticLambda11.$r8$classId;
                            CentralSurfacesImpl centralSurfacesImpl2 = centralSurfacesImpl$$ExternalSyntheticLambda11.f$0;
                            switch (i2) {
                                case 0:
                                    centralSurfacesImpl2.getClass();
                                    int i3 = ((CentralSurfaces.KeyboardShortcutsMessage) obj).mDeviceId;
                                    boolean z = centralSurfacesImpl2.mIsShortcutListSearchEnabled;
                                    Context context = centralSurfacesImpl2.mContext;
                                    if (z) {
                                        Utilities.isLargeScreen(context);
                                    }
                                    KeyboardShortcuts.toggle(i3, context);
                                    break;
                                default:
                                    centralSurfacesImpl2.mCommandQueueCallbacks.animateExpandSettingsPanel(((CentralSurfacesImpl.AnimateExpandSettingsPanelMessage) obj).mSubpanel);
                                    break;
                            }
                        }
                    }
                }
                synchronized (messageRouterImpl3.mDataMessageCancelers) {
                    if (((HashMap) messageRouterImpl3.mDataMessageCancelers).containsKey(obj.getClass())) {
                        if (!((List) ((HashMap) messageRouterImpl3.mDataMessageCancelers).get(obj.getClass())).isEmpty()) {
                            ((List) ((HashMap) messageRouterImpl3.mDataMessageCancelers).get(obj.getClass())).remove(0);
                            if (((List) ((HashMap) messageRouterImpl3.mDataMessageCancelers).get(obj.getClass())).isEmpty()) {
                                ((HashMap) messageRouterImpl3.mDataMessageCancelers).remove(obj.getClass());
                            }
                        }
                    }
                }
            }
        });
        synchronized (messageRouterImpl2.mDataMessageCancelers) {
            ((HashMap) messageRouterImpl2.mDataMessageCancelers).putIfAbsent(CentralSurfaces.KeyboardShortcutsMessage.class, new ArrayList());
            ((List) ((HashMap) messageRouterImpl2.mDataMessageCancelers).get(CentralSurfaces.KeyboardShortcutsMessage.class)).add(executeDelayed);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void togglePanel() {
        if (((CentralSurfacesImpl) this.mCentralSurfaces).mPanelExpanded) {
            ((ShadeControllerImpl) this.mShadeController).animateCollapseShade(0);
        } else {
            animateExpandNotificationsPanel();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void appTransitionCancelled(int i) {
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void appTransitionFinished(int i) {
    }
}
