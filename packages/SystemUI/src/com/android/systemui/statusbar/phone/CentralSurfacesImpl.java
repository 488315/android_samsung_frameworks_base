package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.devicestate.DeviceStateManager;
import android.metrics.LogMaker;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.service.dreams.IDreamManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.EventLog;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.TrackTracer;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.statusbar.IStatusBarService;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBouncerContainer;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.AutoReinflateContainer;
import com.android.systemui.BasicRune;
import com.android.systemui.CoreStartable;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.InitController;
import com.android.systemui.LsRune;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController;
import com.android.systemui.aiagent.AiAgentEffect;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.charging.WirelessChargingAnimation;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.doze.AODParameters;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.emergency.EmergencyGestureModule;
import com.android.systemui.emergency.EmergencyGestureModule$emergencyGestureIntentFactory$1;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.QuickPanelLogger;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.mdm.MdmOverlayContainer;
import com.android.systemui.media.NotificationMediaManager;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.views.NavigationBarView;
import com.android.systemui.noticenter.NotiCenterPlugin;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.OverlayPlugin;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginDependencyProvider;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.ScreenPowerState;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.buttons.QSTooltipWindow;
import com.android.systemui.qs.flags.QSComposeFragment;
import com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.settings.brightness.BrightnessSliderController;
import com.android.systemui.shade.CameraLauncher;
import com.android.systemui.shade.GlanceableHubContainerController;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.NotificationShadeWindowViewController;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeLogger;
import com.android.systemui.shade.ShadeLogger$$ExternalSyntheticLambda0;
import com.android.systemui.shade.ShadeSurface;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.statusbar.phone.BarTransitions;
import com.android.systemui.statusbar.CircleReveal;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.KeyboardShortcuts;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.LiftReveal;
import com.android.systemui.statusbar.LightRevealScrim;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.PowerButtonReveal;
import com.android.systemui.statusbar.PulseExpansionHandler;
import com.android.systemui.statusbar.SecLightRevealScrimHelper;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import com.android.systemui.statusbar.core.StatusBarInitializer;
import com.android.systemui.statusbar.data.model.StatusBarMode;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepository;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore;
import com.android.systemui.statusbar.domain.interactor.SecStatusBarWindowViewTouchedInteractor;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.NotificationLaunchAnimatorControllerProvider;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotifRemoteViewCache;
import com.android.systemui.statusbar.notification.row.NotifRemoteViewCacheImpl;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.shared.NotificationIconContainerRefactor;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BrightnessMirrorController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.WallpaperController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.MessageRouter;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.utils.windowmanager.WindowManagerProvider;
import com.android.systemui.utils.windowmanager.WindowManagerUtils;
import com.android.systemui.volume.VolumeComponent;
import com.android.systemui.volume.VolumeDialogComponent;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import com.android.systemui.wallet.controller.QuickAccessWalletController$$ExternalSyntheticLambda2;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda10;
import com.android.wm.shell.bubbles.Bubbles;
import com.samsung.android.view.SemWindowManager;
import com.samsung.systemui.splugins.noticenter.PluginNotiCenter;
import com.sec.ims.volte2.data.VolteConstants;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CentralSurfacesImpl implements CoreStartable, CentralSurfaces, SecBrightnessMirrorControllerProvider {
    public static final UiEventLogger sUiEventLogger = new UiEventLoggerImpl();
    public final AccessibilityFloatingMenuController mAccessibilityFloatingMenuController;
    public final ActivityStarter mActivityStarter;
    public final ActivityTransitionAnimator mActivityTransitionAnimator;
    public final AiAgentEffect mAiAgentEffect;
    public final AlternateBouncerInteractor mAlternateBouncerInteractor;
    public View mAmbientIndicationContainer;
    public final Lazy mAssistManagerLazy;
    public final AutoHideController mAutoHideController;
    public IStatusBarService mBarService;
    public final BatteryController mBatteryController;
    public BiometricUnlockController mBiometricUnlockController;
    public final Lazy mBiometricUnlockControllerLazy;
    public final SecQpBlurController mBlurController;
    public FrameLayout mBouncerContainer;
    public boolean mBouncerShowing;
    public BrightnessMirrorController mBrightnessMirrorController;
    public final BrightnessSliderController.Factory mBrightnessSliderFactory;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Optional mBubblesOptional;
    public final Lazy mCameraLauncherLazy;
    public final CentralSurfacesImpl$$ExternalSyntheticLambda3 mCheckBarModes;
    public boolean mCloseQsBeforeScreenOff;
    public final SysuiColorExtractor mColorExtractor;
    public final CommandQueue mCommandQueue;
    public CentralSurfacesCommandQueueCallbacks mCommandQueueCallbacks;
    public final Lazy mCommandQueueCallbacksLazy;
    public final CommunalInteractor mCommunalInteractor;
    public final ConfigurationController mConfigurationController;
    public final Context mContext;
    public final DemoModeController mDemoModeController;
    public boolean mDeviceInteractive;
    public DevicePolicyManager mDevicePolicyManager;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public final DeviceStateManager mDeviceStateManager;
    public boolean mDismissingShadeForActivityLaunch;
    public Display mDisplay;
    public int mDisplayId;
    public final DisplayLifecycle mDisplayLifecycle;
    public final DisplayMetrics mDisplayMetrics;
    public final DozeParameters mDozeParameters;
    public final DozeScrimController mDozeScrimController;
    DozeServiceHost mDozeServiceHost;
    public boolean mDozing;
    public final IDreamManager mDreamManager;
    public final EmergencyGestureModule.EmergencyGestureIntentFactory mEmergencyGestureIntentFactory;
    public final ExtensionController mExtensionController;
    public final FalsingCollector mFalsingCollector;
    public final FalsingManager mFalsingManager;
    public final FeatureFlags mFeatureFlags;
    public final FragmentService mFragmentService;
    public PowerManager.WakeLock mGestureWakeLock;
    public final GlanceableHubContainerController mGlanceableHubContainerController;
    public final NotificationGutsManager mGutsManager;
    public final HeadsUpManager mHeadsUpManager;
    public final PhoneStatusBarPolicy mIconPolicy;
    public final CentralSurfacesImpl$$ExternalSyntheticLambda1 mIdleOnCommunalConsumer;
    public final InitController mInitController;
    public int mInteractingWindows;
    public boolean mIsDlsOverlay;
    public boolean mIsFolded;
    public boolean mIsLaunchingActivityOverLockscreen;
    public final JavaAdapter mJavaAdapter;
    public final KeyguardBypassController mKeyguardBypassController;
    public KeyguardFastBioUnlockController mKeyguardFastBioUnlockController;
    public final KeyguardFoldController mKeyguardFoldController;
    public final KeyguardIndicationController mKeyguardIndicationController;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardViewMediator mKeyguardViewMediator;
    public final ViewMediatorCallback mKeyguardViewMediatorCallback;
    public int mLastCameraLaunchSource;
    public int mLastLoggedStateFingerprint;
    public boolean mLaunchCameraOnFinishedGoingToSleep;
    public boolean mLaunchCameraWhenFinishedWaking;
    public boolean mLaunchEmergencyActionOnFinishedGoingToSleep;
    public boolean mLaunchEmergencyActionWhenFinishedWaking;
    public boolean mLaunchWalletOnFinishedGoingToSleep;
    public boolean mLaunchWalletWhenFinishedWaking;
    public final LightBarController mLightBarController;
    public final LightRevealScrim mLightRevealScrim;
    public final LockscreenShadeTransitionController mLockscreenShadeTransitionController;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final DelayableExecutor mMainExecutor;
    public final Handler mMainHandler;
    public final MdmOverlayContainer mMdmOverlayContainer;
    public final NotificationMediaManager mMediaManager;
    public final MessageRouter mMessageRouter;
    public final MetricsLogger mMetricsLogger;
    public final Lazy mNavBarHelperLazy;
    public NavBarStore mNavBarStore;
    public final NavigationBarController mNavigationBarController;
    public boolean mNoAnimationOnNextBarModeChange;
    public final Lazy mNoteTaskControllerLazy;
    public final CommonNotifCollection mNotifCollection;
    public final NotificationStackScrollLayoutController.NotificationListContainerImpl mNotifListContainer;
    public final NotifRemoteViewCache mNotifRemoteViewCache;
    public final Lazy mNotificationActivityStarterLazy;
    public final NotificationLaunchAnimatorControllerProvider mNotificationAnimationProvider;
    public final NotificationIconAreaController mNotificationIconAreaController;
    public final Lazy mNotificationShadeDepthControllerLazy;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final Lazy mNotificationShadeWindowViewControllerLazy;
    public final NotificationsController mNotificationsController;
    public PhoneStatusBarViewController mPhoneStatusBarViewController;
    public final PluginDependencyProvider mPluginDependencyProvider;
    public final PluginManager mPluginManager;
    public PowerButtonReveal mPowerButtonReveal;
    public final PowerInteractor mPowerInteractor;
    public final PowerManager mPowerManager;
    public final Lazy mPresenterLazy;
    public final PulseExpansionHandler mPulseExpansionHandler;
    public SecQSPanelController mQSPanelController;
    public final QuickSettingsController mQsController;
    public final StringBuilder mQuickPanelLogBuilder;
    public final QuickPanelLogger mQuickPanelLogger;
    public final NotificationRemoteInputManager mRemoteInputManager;
    public View mReportRejectedTouch;
    public final ScreenLifecycle mScreenLifecycle;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public final ScrimController mScrimController;
    public SecLightRevealScrimHelper mSecLightRevealScrimHelper;
    public ViewGroup mSecLockIconView;
    public final ShadeController mShadeController;
    public final ShadeExpansionStateManager mShadeExpansionStateManager;
    public final ShadeLogger mShadeLogger;
    public final ShadeSurface mShadeSurface;
    public final ShadeTouchableRegionManager mShadeTouchableRegionManager;
    public final NotificationStackScrollLayout mStackScroller;
    public final NotificationStackScrollLayoutController mStackScrollerController;
    public final Optional mStartingSurfaceOptional;
    public int mState;
    public final StatusBarHideIconsForBouncerManager mStatusBarHideIconsForBouncerManager;
    public final StatusBarInitializer mStatusBarInitializer;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final StatusBarModeRepositoryStore mStatusBarModeRepository;
    public final StatusBarSignalPolicy mStatusBarSignalPolicy;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public LogMaker mStatusBarStateLog;
    public PhoneStatusBarTransitions mStatusBarTransitions;
    public final StatusBarWindowControllerStore mStatusBarWindowControllerStore;
    public final SecStatusBarWindowViewTouchedInteractor mStatusBarWindowViewTouchedInteractor;
    public final SubScreenManager mSubScreenManager;
    public SubscreenNotificationController mSubscreenNotificationController;
    public final QSTooltipWindow mToolTipWindow;
    public final Executor mUiBgExecutor;
    public UiModeManager mUiModeManager;
    public final UserInfoControllerImpl mUserInfoControllerImpl;
    public final UserTracker mUserTracker;
    public final VolumeComponent mVolumeComponent;
    public final NotificationWakeUpCoordinator mWakeUpCoordinator;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final QuickAccessWalletController mWalletController;
    public final WallpaperController mWallpaperController;
    public final WallpaperManager mWallpaperManager;
    public boolean mWallpaperSupported;
    public final WindowManager mWindowManager;
    public final WindowManagerProvider mWindowManagerProvider;
    public final WindowRootViewVisibilityInteractor mWindowRootViewVisibilityInteractor;
    public float mTransitionToFullShadeProgress = 0.0f;
    public final AnonymousClass1 mKeyguardStateControllerCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.1
        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public final void onKeyguardShowingChanged() {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            boolean z = ((KeyguardStateControllerImpl) centralSurfacesImpl.mKeyguardStateController).mOccluded;
            StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager = centralSurfacesImpl.mStatusBarHideIconsForBouncerManager;
            statusBarHideIconsForBouncerManager.isOccluded = z;
            statusBarHideIconsForBouncerManager.updateHideIconsForBouncer(false);
            ScrimController scrimController = centralSurfacesImpl.mScrimController;
            if (scrimController.mKeyguardOccluded == z) {
                return;
            }
            scrimController.mKeyguardOccluded = z;
            scrimController.updateScrims();
            scrimController.mSecLsScrimControlHelper.setScrimAlphaForKeyguard(true);
        }
    };
    public final Point mCurrentDisplaySize = new Point();
    public int mStatusBarWindowState = 0;
    public boolean mShouldDelayLockscreenTransitionFromAod = false;
    public final Object mQueueLock = new Object();
    protected boolean mUserSetup = false;
    public final LifecycleRegistry mLifecycle = new LifecycleRegistry(this);
    public boolean mIsIdleOnCommunal = false;
    public final CentralSurfacesImpl$$ExternalSyntheticLambda2 mOnColorsChangedListener = new ColorExtractor.OnColorsChangedListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda2
        public final void onColorsChanged(ColorExtractor colorExtractor, int i) {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
            centralSurfacesImpl.updateTheme();
        }
    };
    public final AnonymousClass8 mBroadcastReceiver = new AnonymousClass8();
    final WakefulnessLifecycle.Observer mWakefulnessObserver = new AnonymousClass9();
    public final AnonymousClass10 mScreenObserver = new ScreenLifecycle.Observer() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.10
        @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
        public final void onScreenTurnedOff() {
            AssistManager assistManager;
            AlertDialog alertDialog;
            Trace.beginSection("CentralSurfaces#onScreenTurnedOff");
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.mFalsingCollector.onScreenOff();
            centralSurfacesImpl.mScrimController.mScreenOn = false;
            if (centralSurfacesImpl.mCloseQsBeforeScreenOff) {
                centralSurfacesImpl.mQsController.closeQs();
                centralSurfacesImpl.mCloseQsBeforeScreenOff = false;
            }
            if (BasicRune.ASSIST_ASSISTANCE_APP_SETTING_POPUP && (alertDialog = (assistManager = (AssistManager) centralSurfacesImpl.mAssistManagerLazy.get()).mAssistanceAppSettingAlertDialog) != null && alertDialog.isShowing()) {
                assistManager.mAssistanceAppSettingAlertDialog.dismiss();
            }
            Trace.endSection();
        }

        @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
        public final void onScreenTurnedOn() {
            CentralSurfacesImpl.this.mScrimController.onScreenTurnedOn();
        }

        @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
        public final void onScreenTurningOn() {
            CentralSurfacesImpl.this.mFalsingCollector.onScreenTurningOn();
        }
    };
    public final KeyguardUpdateMonitorCallback mUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.12
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onDreamingStateChanged(boolean z) {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.updateScrimController();
            if (z) {
                CentralSurfacesImpl.m3068$$Nest$mmaybeEscalateHeadsUp(centralSurfacesImpl);
            }
        }
    };
    public final AnonymousClass13 mFalsingBeliefListener = new FalsingManager.FalsingBeliefListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.13
        @Override // com.android.systemui.plugins.FalsingManager.FalsingBeliefListener
        public final void onFalse() {
            CentralSurfacesImpl.this.mStatusBarKeyguardViewManager.reset(true, true);
        }
    };
    public final AnonymousClass14 mUnlockScrimCallback = new AnonymousClass14();
    public final AnonymousClass15 mUserSetupObserver = new AnonymousClass15();
    public final AnonymousClass16 mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.16
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onConfigChanged(Configuration configuration) {
            AiAgentEffect aiAgentEffect;
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.updateResources$1();
            centralSurfacesImpl.mDisplay.getMetrics(centralSurfacesImpl.mDisplayMetrics);
            centralSurfacesImpl.mDisplay.getSize(centralSurfacesImpl.mCurrentDisplaySize);
            QSTooltipWindow qSTooltipWindow = centralSurfacesImpl.mToolTipWindow;
            if (qSTooltipWindow != null) {
                qSTooltipWindow.hideToolTip();
                if (QSTooltipWindow.configChanges.applyNewConfig(qSTooltipWindow.mContext.getResources())) {
                    QSTooltipWindow.sInstance = new QSTooltipWindow(qSTooltipWindow.mContext);
                }
            }
            if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                DeviceState.setInDisplayFingerprintSensorPosition(centralSurfacesImpl.mContext.getResources().getDisplayMetrics());
            }
            if (!BasicRune.AI_AGENT_EFFECT || (aiAgentEffect = centralSurfacesImpl.mAiAgentEffect) == null) {
                return;
            }
            aiAgentEffect.updateConfiguration(configuration);
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDensityOrFontScaleChanged() {
            FrameLayout frameLayout;
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            BrightnessMirrorController brightnessMirrorController = centralSurfacesImpl.mBrightnessMirrorController;
            if (brightnessMirrorController != null) {
                brightnessMirrorController.reinflate$1();
            }
            centralSurfacesImpl.mUserInfoControllerImpl.reloadUserInfo();
            int i = NotificationIconContainerRefactor.$r8$clinit;
            centralSurfacesImpl.mNotificationIconAreaController.onDensityOrFontScaleChanged(centralSurfacesImpl.mContext);
            NotificationShadeWindowViewController notificationShadeWindowViewController = centralSurfacesImpl.getNotificationShadeWindowViewController();
            notificationShadeWindowViewController.getClass();
            boolean z = LsRune.SECURITY_BOUNCER_WINDOW;
            NotificationShadeWindowView notificationShadeWindowView = notificationShadeWindowViewController.mView;
            if (z) {
                CentralSurfacesImpl centralSurfacesImpl2 = (CentralSurfacesImpl) notificationShadeWindowViewController.mService;
                FrameLayout frameLayout2 = centralSurfacesImpl2.mBouncerContainer;
                NotificationShadeWindowController notificationShadeWindowController = notificationShadeWindowViewController.mNotificationShadeWindowController;
                if (frameLayout2 != null) {
                    frameLayout2.removeAllViews();
                    SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).mHelper;
                    ViewGroup viewGroup = secNotificationShadeWindowControllerHelperImpl.bouncerContainer;
                    if (viewGroup != null) {
                        secNotificationShadeWindowControllerHelperImpl.windowManager.removeView(viewGroup);
                    }
                    secNotificationShadeWindowControllerHelperImpl.bouncerContainer = null;
                    secNotificationShadeWindowControllerHelperImpl.bouncerLp = null;
                    secNotificationShadeWindowControllerHelperImpl.bouncerLpChanged = null;
                }
                frameLayout = new KeyguardBouncerContainer(notificationShadeWindowView.getContext(), notificationShadeWindowViewController.mSysUIKeyEventHandler, notificationShadeWindowViewController.mKeyguardSysDumpTrigger);
                centralSurfacesImpl2.mBouncerContainer = frameLayout;
                ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).mHelper.addBouncer(frameLayout);
            } else {
                FrameLayout frameLayout3 = (FrameLayout) notificationShadeWindowView.findViewById(R.id.keyguard_bouncer_container);
                if (frameLayout3 != null) {
                    frameLayout3.removeAllViews();
                    notificationShadeWindowView.removeView(frameLayout3);
                }
                frameLayout = (FrameLayout) LayoutInflater.from(notificationShadeWindowView.getContext()).inflate(R.layout.keyguard_sec_bouncer_container, (ViewGroup) null);
                notificationShadeWindowView.addView(frameLayout);
            }
            notificationShadeWindowViewController.mBouncerViewBinder.bind(frameLayout);
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDisplayDeviceTypeChanged() {
            if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                centralSurfacesImpl.mNotificationIconAreaController.onDensityOrFontScaleChanged(centralSurfacesImpl.mContext);
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onThemeChanged() {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            BrightnessMirrorController brightnessMirrorController = centralSurfacesImpl.mBrightnessMirrorController;
            if (brightnessMirrorController != null) {
                brightnessMirrorController.reinflate$1();
            }
            centralSurfacesImpl.mShadeSurface.onThemeChanged();
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = centralSurfacesImpl.mStatusBarKeyguardViewManager;
            if (statusBarKeyguardViewManager != null) {
                statusBarKeyguardViewManager.onThemeChanged();
            }
            View view = centralSurfacesImpl.mAmbientIndicationContainer;
            if (view instanceof AutoReinflateContainer) {
                ((AutoReinflateContainer) view).inflateLayout();
            }
            int i = NotificationIconContainerRefactor.$r8$clinit;
            centralSurfacesImpl.mNotificationIconAreaController.onThemeChanged();
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onUiModeChanged() {
            BrightnessMirrorController brightnessMirrorController = CentralSurfacesImpl.this.mBrightnessMirrorController;
            if (brightnessMirrorController != null) {
                brightnessMirrorController.reinflate$1();
            }
        }
    };
    public final AnonymousClass17 mStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.17
        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onDozeAmountChanged(float f, float f2) {
            SecLightRevealScrimHelper secLightRevealScrimHelper = CentralSurfacesImpl.this.mSecLightRevealScrimHelper;
            secLightRevealScrimHelper.getClass();
            if (LsRune.AOD_LIGHT_REVEAL) {
                LightRevealScrim lightRevealScrim = secLightRevealScrimHelper.lightRevealScrim;
                if (lightRevealScrim.revealEffect instanceof CircleReveal) {
                    return;
                }
                if (!secLightRevealScrimHelper.screenOffAnimationController.shouldHideLightRevealScrimOnWakeUp() && (!secLightRevealScrimHelper.biometricUnlockController.isWakeAndUnlock() || f != 0.0f)) {
                    lightRevealScrim.setRevealAmount(1.0f - f);
                }
                if (LsRune.KEYGUARD_SUB_DISPLAY_LARGE_FRONT && f == 1.0f && secLightRevealScrimHelper.isFolded) {
                    PluginAODManager pluginAODManager = (PluginAODManager) secLightRevealScrimHelper.pluginAODManagerLazy.get();
                    if (pluginAODManager.mAODMachine == null || !LsRune.SUBSCREEN_WATCHFACE || pluginAODManager.mDisplayLifeCycle.mIsFolderOpened) {
                        return;
                    }
                    Log.d("PluginAODManager", "onAodTransitionEnd() in folded state");
                    pluginAODManager.onTransitionEnded();
                }
            }
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onDozingChanged(boolean z) {
            Trace.beginSection("CentralSurfaces#updateDozing");
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.mDozing = z;
            boolean z2 = centralSurfacesImpl.mDozeServiceHost.mDozingRequested;
            DozeParameters dozeParameters = centralSurfacesImpl.mDozeParameters;
            boolean z3 = z2 && dozeParameters.mControlScreenOffAnimation;
            AODParameters aODParameters = dozeParameters.mAODParameters;
            centralSurfacesImpl.mShadeSurface.resetViews(z3 && z, ((aODParameters != null ? aODParameters.mDozeUiState : false) || centralSurfacesImpl.mPowerInteractor.screenPowerState.$$delegate_0.getValue() == ScreenPowerState.SCREEN_ON) ? false : true);
            if (!centralSurfacesImpl.mBiometricUnlockController.isWakeAndUnlock()) {
                centralSurfacesImpl.mMdmOverlayContainer.updateMdmPolicy();
            }
            centralSurfacesImpl.mKeyguardViewMediator.setDozing(centralSurfacesImpl.mDozing);
            centralSurfacesImpl.updateDozingState();
            centralSurfacesImpl.mDozeServiceHost.updateDozing();
            centralSurfacesImpl.updateScrimController();
            if (centralSurfacesImpl.mBiometricUnlockController.isWakeAndUnlock()) {
                centralSurfacesImpl.updateIsKeyguard(false);
            }
            centralSurfacesImpl.updateReportRejectedTouchVisibility();
            Trace.endSection();
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStateChanged(int i) {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.mState = i;
            centralSurfacesImpl.updateReportRejectedTouchVisibility();
            centralSurfacesImpl.mDozeServiceHost.updateDozing();
            centralSurfacesImpl.updateTheme();
            ((NavigationBarControllerImpl) centralSurfacesImpl.mNavigationBarController).touchAutoDim(centralSurfacesImpl.mDisplayId);
            Trace.beginSection("CentralSurfaces#updateKeyguardState");
            if (centralSurfacesImpl.mState == 1) {
                centralSurfacesImpl.mShadeSurface.cancelPendingCollapse(true);
            }
            centralSurfacesImpl.updateDozingState();
            centralSurfacesImpl.checkBarModes$1();
            centralSurfacesImpl.updateScrimController();
            if (LsRune.SECURITY_SWIPE_BOUNCER) {
                centralSurfacesImpl.mStatusBarKeyguardViewManager.setShowSwipeBouncer(false);
            }
            int i2 = centralSurfacesImpl.mState;
            boolean z = i2 == 2;
            if (z || i2 == 1) {
                Log.d("CentralSurfaces", "setBarState( dispatchStatusBarState to " + z + " )");
                centralSurfacesImpl.mKeyguardUpdateMonitor.dispatchStatusBarState(z);
            }
            Trace.endSection();
            NotiCenterPlugin.INSTANCE.getClass();
            if (NotiCenterPlugin.isNotiCenterPluginConnected()) {
                if (centralSurfacesImpl.mState == 1) {
                    PluginNotiCenter pluginNotiCenter = NotiCenterPlugin.plugin;
                    if (pluginNotiCenter != null) {
                        pluginNotiCenter.enterKeyguard();
                        return;
                    }
                    return;
                }
                PluginNotiCenter pluginNotiCenter2 = NotiCenterPlugin.plugin;
                if (pluginNotiCenter2 != null) {
                    pluginNotiCenter2.unLock();
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:8:0x0021, code lost:
        
            if (r2.mLeaveOpenOnKeyguardHide != false) goto L19;
         */
        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onStatePreChange(int r2, int r3) {
            /*
                r1 = this;
                com.android.systemui.statusbar.phone.CentralSurfacesImpl r1 = com.android.systemui.statusbar.phone.CentralSurfacesImpl.this
                com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor r2 = r1.mWindowRootViewVisibilityInteractor
                kotlinx.coroutines.flow.ReadonlyStateFlow r2 = r2.isLockscreenOrShadeVisible
                kotlinx.coroutines.flow.StateFlow r2 = r2.$$delegate_0
                java.lang.Object r2 = r2.getValue()
                java.lang.Boolean r2 = (java.lang.Boolean) r2
                boolean r2 = r2.booleanValue()
                if (r2 == 0) goto L28
                r2 = 2
                if (r3 == r2) goto L23
                com.android.systemui.statusbar.SysuiStatusBarStateController r2 = r1.mStatusBarStateController
                com.android.systemui.statusbar.StatusBarStateControllerImpl r2 = (com.android.systemui.statusbar.StatusBarStateControllerImpl) r2
                int r0 = r2.mState
                if (r0 != 0) goto L28
                boolean r2 = r2.mLeaveOpenOnKeyguardHide
                if (r2 == 0) goto L28
            L23:
                com.android.internal.statusbar.IStatusBarService r2 = r1.mBarService     // Catch: android.os.RemoteException -> L28
                r2.clearNotificationEffects()     // Catch: android.os.RemoteException -> L28
            L28:
                r2 = 1
                if (r3 != r2) goto L37
                com.android.systemui.statusbar.NotificationRemoteInputManager r2 = r1.mRemoteInputManager
                com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator r2 = r2.mRemoteInputListener
                if (r2 == 0) goto L34
                r2.onPanelCollapsed()
            L34:
                com.android.systemui.statusbar.phone.CentralSurfacesImpl.m3068$$Nest$mmaybeEscalateHeadsUp(r1)
            L37:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.CentralSurfacesImpl.AnonymousClass17.onStatePreChange(int, int):void");
        }
    };
    public final AnonymousClass18 mBatteryStateChangeCallback = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.18
        @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
        public final void onPowerSaveChanged(boolean z) {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.mMainExecutor.execute(centralSurfacesImpl.mCheckBarModes);
            DozeServiceHost dozeServiceHost = centralSurfacesImpl.mDozeServiceHost;
            if (dozeServiceHost != null) {
                Assert.isMainThread();
                Iterator<E> it = dozeServiceHost.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((DozeHost.Callback) it.next()).onPowerSaveChanged();
                }
            }
        }
    };
    public final AnonymousClass19 mActivityTransitionAnimatorCallback = new AnonymousClass19();
    public final AnonymousClass20 mActivityTransitionAnimatorListener = new ActivityTransitionAnimator.Listener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.20
        @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
        public final void onTransitionAnimationEnd() {
            CentralSurfacesImpl.this.mKeyguardViewMediator.setBlursDisabledForAppLaunch(false);
        }

        @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
        public final void onTransitionAnimationStart() {
            CentralSurfacesImpl.this.mKeyguardViewMediator.setBlursDisabledForAppLaunch(true);
        }

        @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
        public final void onTransitionAnimationProgress(float f) {
        }
    };
    public final AnonymousClass21 mDemoModeCallback = new DemoMode() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.21
        @Override // com.android.systemui.demomode.DemoModeCommandReceiver
        public final void onDemoModeFinished() {
            CentralSurfacesImpl.this.checkBarModes$1();
        }

        @Override // com.android.systemui.demomode.DemoModeCommandReceiver
        public final void dispatchDemoCommand(Bundle bundle, String str) {
        }
    };
    public final AnonymousClass22 mRemoteInputActionBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.22
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Objects.toString(intent);
            if ("com.samsung.systemui.action.REQUEST_REMOTE_INPUT".equals(intent.getAction())) {
                CentralSurfacesImpl.this.checkRemoteInputRequest(intent.getStringExtra("key"), null);
            }
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$14, reason: invalid class name */
    public class AnonymousClass14 implements ScrimController.Callback {
        public AnonymousClass14() {
        }

        @Override // com.android.systemui.statusbar.phone.ScrimController.Callback
        public final void onCancelled() {
            onFinished();
        }

        @Override // com.android.systemui.statusbar.phone.ScrimController.Callback
        public final void onFinished() {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            if (((KeyguardStateControllerImpl) centralSurfacesImpl.mKeyguardStateController).mKeyguardFadingAway) {
                centralSurfacesImpl.mStatusBarKeyguardViewManager.onKeyguardFadedAway();
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$15, reason: invalid class name */
    public class AnonymousClass15 implements DeviceProvisionedController.DeviceProvisionedListener {
        public AnonymousClass15() {
        }

        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public final void onUserSetupChanged() {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            boolean isCurrentUserSetup = ((DeviceProvisionedControllerImpl) centralSurfacesImpl.mDeviceProvisionedController).isCurrentUserSetup();
            Log.d("CentralSurfaces", "mUserSetupObserver - DeviceProvisionedListener called for current user");
            if (isCurrentUserSetup != centralSurfacesImpl.mUserSetup) {
                centralSurfacesImpl.mUserSetup = isCurrentUserSetup;
                if (!isCurrentUserSetup && centralSurfacesImpl.mState == 0) {
                    centralSurfacesImpl.mShadeController.animateCollapseShade(0);
                }
                ShadeSurface shadeSurface = centralSurfacesImpl.mShadeSurface;
                if (shadeSurface != null) {
                    shadeSurface.setUserSetupComplete(centralSurfacesImpl.mUserSetup);
                }
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$19, reason: invalid class name */
    public class AnonymousClass19 implements ActivityTransitionAnimator.Callback {
        public AnonymousClass19() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$3, reason: invalid class name */
    public class AnonymousClass3 implements PluginListener {
        public final ArraySet mOverlays = new ArraySet();

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$3$Callback */
        public class Callback implements OverlayPlugin.Callback {
            public final OverlayPlugin mPlugin;

            public Callback(OverlayPlugin overlayPlugin) {
                this.mPlugin = overlayPlugin;
            }

            @Override // com.android.systemui.plugins.OverlayPlugin.Callback
            public final void onHoldStatusBarOpenChange() {
                OverlayPlugin overlayPlugin = this.mPlugin;
                boolean holdStatusBarOpen = overlayPlugin.holdStatusBarOpen();
                AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                if (holdStatusBarOpen) {
                    anonymousClass3.mOverlays.add(overlayPlugin);
                } else {
                    anonymousClass3.mOverlays.remove(overlayPlugin);
                }
                CentralSurfacesImpl.this.mMainExecutor.execute(new CentralSurfacesImpl$$ExternalSyntheticLambda29(this, 1));
            }
        }

        public AnonymousClass3() {
        }

        @Override // com.android.systemui.plugins.PluginListener
        public final void onPluginConnected(Plugin plugin, Context context) {
            CentralSurfacesImpl.this.mMainExecutor.execute(new CentralSurfacesImpl$3$$ExternalSyntheticLambda0(0, this, (OverlayPlugin) plugin));
        }

        @Override // com.android.systemui.plugins.PluginListener
        public final void onPluginDisconnected(Plugin plugin) {
            CentralSurfacesImpl.this.mMainExecutor.execute(new CentralSurfacesImpl$3$$ExternalSyntheticLambda0(1, this, (OverlayPlugin) plugin));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$4, reason: invalid class name */
    public class AnonymousClass4 implements ShadeController.ShadeVisibilityListener {
        public AnonymousClass4() {
        }

        public final void expandedVisibleChanged(boolean z) {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            if (z) {
                centralSurfacesImpl.setInteracting(1, true);
                return;
            }
            UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
            centralSurfacesImpl.setInteracting(1, false);
            if (((StatusBarNotificationActivityStarter) ((NotificationActivityStarter) centralSurfacesImpl.mNotificationActivityStarterLazy.get())).mIsCollapsingToShowActivityOverLockscreen || centralSurfacesImpl.mKeyguardViewMediator.isHiding() || centralSurfacesImpl.mKeyguardUpdateMonitor.mKeyguardGoingAway) {
                return;
            }
            int i = centralSurfacesImpl.mState;
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = centralSurfacesImpl.mStatusBarKeyguardViewManager;
            if (i == 2) {
                statusBarKeyguardViewManager.reset(true);
                if (LsRune.SECURITY_SUB_DISPLAY_LOCK && ((KeyguardFoldControllerImpl) centralSurfacesImpl.mKeyguardFoldController).isBouncerOnFoldOpened()) {
                    statusBarKeyguardViewManager.showBouncer("showBouncerOrLockScreenIfKeyguard(SHADE_LOCKED) SECURITY_SUB_DISPLAY_LOCK");
                    return;
                }
                return;
            }
            if (i == 1 && !statusBarKeyguardViewManager.primaryBouncerIsOrWillBeShowing()) {
                Log.d("CentralSurfaces", "showBouncerOrLockScreenIfKeyguard, showingBouncer");
                statusBarKeyguardViewManager.showBouncer("showBouncerOrLockScreenIfKeyguard(KEYGUARD) KEYGUARD_ALL_DIRECTIONS_SWIPE_UNLOCK");
            } else if (centralSurfacesImpl.mState == 1 && !statusBarKeyguardViewManager.primaryBouncerIsOrWillBeShowing() && statusBarKeyguardViewManager.isSecure()) {
                int i2 = SceneContainerFlag.$r8$clinit;
                statusBarKeyguardViewManager.showBouncer("CentralSurfacesImpl#showBouncerOrLockScreenIfKeyguard");
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$6, reason: invalid class name */
    public class AnonymousClass6 {
        public AnonymousClass6() {
        }

        public final void setWakeAndUnlocking(boolean z) {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            if (centralSurfacesImpl.getNavigationBarView() != null) {
                NavigationBarView navigationBarView = centralSurfacesImpl.getNavigationBarView();
                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) ((ViewGroup) navigationBarView.getParent()).getLayoutParams();
                if (layoutParams != null) {
                    boolean z2 = layoutParams.windowAnimations != 0;
                    if (!z2 && z) {
                        layoutParams.windowAnimations = R.style.Animation_NavigationBarFadeIn;
                    } else if (z2 && !z) {
                        layoutParams.windowAnimations = 0;
                    }
                    WindowManager windowManager = WindowManagerUtils.getWindowManager(navigationBarView.getContext());
                    if (BasicRune.NAVBAR_ENABLED) {
                        layoutParams.setTitle("NavigationBarView");
                    }
                    windowManager.updateViewLayout((View) navigationBarView.getParent(), layoutParams);
                }
                navigationBarView.mWakeAndUnlocking = z;
                navigationBarView.updateLayoutTransitionsEnabled();
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$7, reason: invalid class name */
    public class AnonymousClass7 implements WirelessChargingAnimation.Callback {
        public AnonymousClass7() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$8, reason: invalid class name */
    public class AnonymousClass8 extends BroadcastReceiver {
        public static final /* synthetic */ int $r8$clinit = 0;

        public AnonymousClass8() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Trace.beginSection("CentralSurfaces#onReceive");
            String action = intent.getAction();
            String stringExtra = intent.getStringExtra("reason");
            int i = 0;
            if (PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
                centralSurfacesImpl.shouldUseTabletKeyboardShortcuts();
                KeyboardShortcuts.dismiss();
                CentralSurfacesImpl.this.mRemoteInputManager.closeRemoteInputs(false);
                if (((NotificationLockscreenUserManagerImpl) CentralSurfacesImpl.this.mLockscreenUserManager).isCurrentProfile(getSendingUserId())) {
                    CentralSurfacesImpl.this.mShadeLogger.d("ACTION_CLOSE_SYSTEM_DIALOGS intent: closing shade");
                    if (stringExtra != null) {
                        r4 = stringExtra.equals("recentapps") ? 2 : 0;
                        i = (stringExtra.equals(BcSmartspaceDataPlugin.UI_SURFACE_DREAM) && CentralSurfacesImpl.this.mScreenOffAnimationController.shouldExpandNotifications()) ? r4 | 4 : r4;
                    }
                    CentralSurfacesImpl.this.mShadeController.animateCollapseShade(i);
                } else {
                    CentralSurfacesImpl.this.mShadeLogger.d("ACTION_CLOSE_SYSTEM_DIALOGS intent: non-matching user ID");
                }
            } else if ("android.intent.action.SCREEN_OFF".equals(action)) {
                NotificationShadeWindowController notificationShadeWindowController = CentralSurfacesImpl.this.mNotificationShadeWindowController;
                if (notificationShadeWindowController != null) {
                    NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) notificationShadeWindowController;
                    NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                    notificationShadeWindowState.windowNotTouchable = false;
                    notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
                }
                CentralSurfacesImpl centralSurfacesImpl2 = CentralSurfacesImpl.this;
                centralSurfacesImpl2.getClass();
                int i2 = StatusBarConnectedDisplays.$r8$clinit;
                PhoneStatusBarTransitions phoneStatusBarTransitions = centralSurfacesImpl2.mStatusBarTransitions;
                if (phoneStatusBarTransitions != null) {
                    BarTransitions.BarBackgroundDrawable barBackgroundDrawable = phoneStatusBarTransitions.mBarBackground;
                    if (barBackgroundDrawable.mAnimating) {
                        barBackgroundDrawable.mAnimating = false;
                        barBackgroundDrawable.invalidateSelf();
                    }
                }
                ((NavigationBarControllerImpl) centralSurfacesImpl2.mNavigationBarController).finishBarAnimations(centralSurfacesImpl2.mDisplayId);
                CentralSurfacesImpl.this.mNotificationsController.resetUserExpandedStates();
            } else if ("com.sec.aecmonitor.ONE_CYCLE_FINISH".equals(action)) {
                CentralSurfacesImpl centralSurfacesImpl3 = CentralSurfacesImpl.this;
                if (!((KeyguardStateControllerImpl) centralSurfacesImpl3.mKeyguardStateController).mShowing) {
                    centralSurfacesImpl3.mCommandQueueCallbacks.animateExpandNotificationsPanel();
                }
                CentralSurfacesImpl.this.mMainHandler.postDelayed(new CentralSurfacesImpl$$ExternalSyntheticLambda29(this, r4), 300L);
            }
            Trace.endSection();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$9, reason: invalid class name */
    public class AnonymousClass9 implements WakefulnessLifecycle.Observer {
        public AnonymousClass9() {
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedGoingToSleep() {
            int i = 0;
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            ((CameraLauncher) centralSurfacesImpl.mCameraLauncherLazy.get()).setLaunchingAffordance(false);
            centralSurfacesImpl.releaseGestureWakeLock();
            centralSurfacesImpl.mLaunchCameraWhenFinishedWaking = false;
            centralSurfacesImpl.mLaunchWalletWhenFinishedWaking = false;
            centralSurfacesImpl.mDeviceInteractive = false;
            centralSurfacesImpl.updateNotificationPanelTouchState();
            centralSurfacesImpl.getNotificationShadeWindowViewController().cancelCurrentTouch();
            boolean z = centralSurfacesImpl.mLaunchCameraOnFinishedGoingToSleep;
            DelayableExecutor delayableExecutor = centralSurfacesImpl.mMainExecutor;
            if (z) {
                centralSurfacesImpl.mLaunchCameraOnFinishedGoingToSleep = false;
                delayableExecutor.execute(new CentralSurfacesImpl$9$$ExternalSyntheticLambda0(this, i));
            }
            if (centralSurfacesImpl.mLaunchWalletOnFinishedGoingToSleep) {
                centralSurfacesImpl.mLaunchWalletOnFinishedGoingToSleep = false;
                delayableExecutor.execute(new CentralSurfacesImpl$9$$ExternalSyntheticLambda0(this, 1));
            }
            if (centralSurfacesImpl.mLaunchEmergencyActionOnFinishedGoingToSleep) {
                centralSurfacesImpl.mLaunchEmergencyActionOnFinishedGoingToSleep = false;
                delayableExecutor.execute(new CentralSurfacesImpl$9$$ExternalSyntheticLambda0(this, 2));
            }
            centralSurfacesImpl.updateIsKeyguard(false);
            boolean z2 = QpRune.QUICK_PANEL_BLUR_DEFAULT;
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedWakingUp() {
            UserHandle userHandle;
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            if (centralSurfacesImpl.mShouldDelayLockscreenTransitionFromAod) {
                ((NotificationShadeWindowControllerImpl) centralSurfacesImpl.mNotificationShadeWindowController).batchApplyWindowLayoutParams(new CentralSurfacesImpl$9$$ExternalSyntheticLambda0(this, 4));
            }
            NotificationWakeUpCoordinator notificationWakeUpCoordinator = centralSurfacesImpl.mWakeUpCoordinator;
            notificationWakeUpCoordinator.fullyAwake = true;
            notificationWakeUpCoordinator.setWakingUp(false);
            if (((KeyguardStateControllerImpl) centralSurfacesImpl.mKeyguardStateController).mOccluded && !centralSurfacesImpl.mDozeParameters.canControlUnlockedScreenOff()) {
                ((NotificationLockscreenUserManagerImpl) centralSurfacesImpl.mLockscreenUserManager).updatePublicMode();
                centralSurfacesImpl.mStackScrollerController.updateSensitivenessWithAnimation(false);
            }
            if (centralSurfacesImpl.mLaunchCameraWhenFinishedWaking) {
                ((CameraLauncher) centralSurfacesImpl.mCameraLauncherLazy.get()).launchCamera(centralSurfacesImpl.mLastCameraLaunchSource, centralSurfacesImpl.mShadeSurface.isFullyCollapsed());
                centralSurfacesImpl.mLaunchCameraWhenFinishedWaking = false;
            }
            if (centralSurfacesImpl.mLaunchWalletWhenFinishedWaking) {
                centralSurfacesImpl.mLaunchWalletWhenFinishedWaking = false;
                QuickAccessWalletController quickAccessWalletController = centralSurfacesImpl.mWalletController;
                quickAccessWalletController.mQuickAccessWalletClient.getGestureTargetActivityPendingIntent(quickAccessWalletController.mExecutor, new QuickAccessWalletController$$ExternalSyntheticLambda2(quickAccessWalletController, centralSurfacesImpl.mActivityStarter));
            }
            if (centralSurfacesImpl.mLaunchEmergencyActionWhenFinishedWaking) {
                centralSurfacesImpl.mLaunchEmergencyActionWhenFinishedWaking = false;
                Intent invoke = ((EmergencyGestureModule$emergencyGestureIntentFactory$1) centralSurfacesImpl.mEmergencyGestureIntentFactory).invoke();
                if (invoke != null) {
                    Context context = centralSurfacesImpl.mContext;
                    for (String str : context.getResources().getStringArray(R.array.system_ui_packages)) {
                        if (invoke.getComponent() == null) {
                            break;
                        }
                        if (str.equals(invoke.getComponent().getPackageName())) {
                            userHandle = new UserHandle(UserHandle.myUserId());
                            break;
                        }
                    }
                    userHandle = ((UserTrackerImpl) centralSurfacesImpl.mUserTracker).getUserHandle();
                    context.startActivityAsUser(invoke, userHandle);
                }
            }
            centralSurfacesImpl.updateScrimController();
            boolean z = QpRune.QUICK_PANEL_BLUR_DEFAULT;
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedGoingToSleep() {
            DejankUtils.startDetectingBlockingIpcs("CentralSurfaces#onStartedGoingToSleep");
            UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.getClass();
            CentralSurfacesImpl.m3069$$Nest$mupdateRevealEffect(centralSurfacesImpl, false);
            centralSurfacesImpl.updateNotificationPanelTouchState();
            CentralSurfacesImpl.m3068$$Nest$mmaybeEscalateHeadsUp(centralSurfacesImpl);
            VolumeComponent volumeComponent = centralSurfacesImpl.mVolumeComponent;
            if (volumeComponent != null) {
                ((VolumeDialogComponent) volumeComponent).mController.mCallbacks.onDismissRequested(2);
            }
            centralSurfacesImpl.mWakeUpCoordinator.fullyAwake = false;
            centralSurfacesImpl.mKeyguardBypassController.pendingUnlock = null;
            centralSurfacesImpl.mShadeTouchableRegionManager.updateTouchableRegion();
            if (centralSurfacesImpl.mDozeParameters.shouldShowLightRevealScrim()) {
                centralSurfacesImpl.mShadeController.makeExpandedVisible(true);
            }
            DejankUtils.stopDetectingBlockingIpcs("CentralSurfaces#onStartedGoingToSleep");
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedWakingUp() {
            DejankUtils.startDetectingBlockingIpcs("CentralSurfaces#onStartedWakingUp");
            ((NotificationShadeWindowControllerImpl) CentralSurfacesImpl.this.mNotificationShadeWindowController).batchApplyWindowLayoutParams(new CentralSurfacesImpl$9$$ExternalSyntheticLambda0(this, 3));
            DejankUtils.stopDetectingBlockingIpcs("CentralSurfaces#onStartedWakingUp");
        }

        public final void startLockscreenTransitionFromAod() {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.mDozeServiceHost.stopDozing();
            CentralSurfacesImpl.m3069$$Nest$mupdateRevealEffect(centralSurfacesImpl, true);
            centralSurfacesImpl.updateNotificationPanelTouchState();
            centralSurfacesImpl.mShadeTouchableRegionManager.updateTouchableRegion();
            if (centralSurfacesImpl.mScreenOffAnimationController.shouldHideLightRevealScrimOnWakeUp()) {
                centralSurfacesImpl.mShadeController.makeExpandedInvisible();
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AnimateExpandSettingsPanelMessage {
        public final String mSubpanel;

        public AnimateExpandSettingsPanelMessage(String str) {
            this.mSubpanel = str;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum StatusBarUiEvent implements UiEventLogger.UiEventEnum {
        /* JADX INFO: Fake field, exist only in values array */
        LOCKSCREEN_OPEN_SECURE(405),
        /* JADX INFO: Fake field, exist only in values array */
        LOCKSCREEN_OPEN_INSECURE(VolteConstants.ErrorCode.NOT_ACCEPTABLE),
        /* JADX INFO: Fake field, exist only in values array */
        LOCKSCREEN_CLOSE_SECURE(407),
        /* JADX INFO: Fake field, exist only in values array */
        LOCKSCREEN_CLOSE_INSECURE(VolteConstants.ErrorCode.REQUEST_TIMEOUT),
        /* JADX INFO: Fake field, exist only in values array */
        BOUNCER_OPEN_SECURE(409),
        /* JADX INFO: Fake field, exist only in values array */
        BOUNCER_OPEN_INSECURE(410),
        /* JADX INFO: Fake field, exist only in values array */
        BOUNCER_CLOSE_SECURE(411),
        /* JADX INFO: Fake field, exist only in values array */
        BOUNCER_CLOSE_INSECURE(412);

        private final int mId;

        StatusBarUiEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    /* renamed from: -$$Nest$mmaybeEscalateHeadsUp, reason: not valid java name */
    public static void m3068$$Nest$mmaybeEscalateHeadsUp(CentralSurfacesImpl centralSurfacesImpl) {
        HeadsUpManagerImpl headsUpManagerImpl = (HeadsUpManagerImpl) centralSurfacesImpl.mHeadsUpManager;
        headsUpManagerImpl.getAllEntries().forEach(new CentralSurfacesImpl$$ExternalSyntheticLambda1(centralSurfacesImpl, 1));
        headsUpManagerImpl.releaseAllImmediately();
    }

    /* renamed from: -$$Nest$mupdateRevealEffect, reason: not valid java name */
    public static void m3069$$Nest$mupdateRevealEffect(CentralSurfacesImpl centralSurfacesImpl, boolean z) {
        boolean z2;
        LightRevealScrim lightRevealScrim = centralSurfacesImpl.mLightRevealScrim;
        if (lightRevealScrim == null) {
            return;
        }
        boolean z3 = LsRune.AOD_LIGHT_REVEAL;
        if (z3 && centralSurfacesImpl.mKeyguardFastBioUnlockController.isFastWakeAndUnlockMode() && centralSurfacesImpl.mKeyguardFastBioUnlockController.isInvisibleAfterGoingAwayTransStarted) {
            return;
        }
        WakefulnessLifecycle wakefulnessLifecycle = centralSurfacesImpl.mWakefulnessLifecycle;
        boolean z4 = false;
        boolean z5 = z && !(lightRevealScrim.revealEffect instanceof CircleReveal) && wakefulnessLifecycle.mLastWakeReason == 1;
        boolean z6 = !z && wakefulnessLifecycle.mLastSleepReason == 4;
        if (z3) {
            boolean z7 = z && wakefulnessLifecycle.mLastWakeReason == 113;
            z2 = !z && wakefulnessLifecycle.mLastSleepReason == 23;
            StringBuilder m = EmergencyButtonController$$ExternalSyntheticOutline0.m("updateRevealEffect: wakingUp=", " wakingUpFromPowerButton=", " sleepingFromPowerButton=", z, z5);
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, z6, " wakingUpFromDoubleTap=", z7, " sleepingFromDoubleTap=");
            KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(m, z2, "CentralSurfaces");
            z4 = z7;
        } else {
            z2 = false;
        }
        SysuiStatusBarStateController sysuiStatusBarStateController = centralSurfacesImpl.mStatusBarStateController;
        if (z5 || z6) {
            lightRevealScrim.setRevealEffect(centralSurfacesImpl.mPowerButtonReveal);
            lightRevealScrim.setRevealAmount(1.0f - sysuiStatusBarStateController.getDozeAmount());
            return;
        }
        if (!z3) {
            if (z && (lightRevealScrim.revealEffect instanceof CircleReveal)) {
                return;
            }
            lightRevealScrim.setRevealEffect(LiftReveal.INSTANCE);
            lightRevealScrim.setRevealAmount(1.0f - sysuiStatusBarStateController.getDozeAmount());
            return;
        }
        SecLightRevealScrimHelper secLightRevealScrimHelper = centralSurfacesImpl.mSecLightRevealScrimHelper;
        secLightRevealScrimHelper.getClass();
        if (!z4 && !z2) {
            SecLightRevealScrimHelper.SecCircleReveal secCircleReveal = secLightRevealScrimHelper.secCircleReveal;
            if (secCircleReveal != null) {
                secCircleReveal.centerX = secLightRevealScrimHelper.secRevealCenterY;
            }
        } else if (z2) {
            if (centralSurfacesImpl.mShadeSurface.getTouchAnimator().doubleTapDownEvent == null) {
                SecLightRevealScrimHelper.SecCircleReveal secCircleReveal2 = secLightRevealScrimHelper.secCircleReveal;
                if (secCircleReveal2 != null) {
                    secCircleReveal2.centerX = secLightRevealScrimHelper.secRevealCenterX;
                    secCircleReveal2.centerY = secLightRevealScrimHelper.secRevealCenterY;
                }
            } else {
                SecLightRevealScrimHelper.SecCircleReveal secCircleReveal3 = secLightRevealScrimHelper.secCircleReveal;
                if (secCircleReveal3 != null) {
                    secCircleReveal3.centerX = (int) r1.getX();
                    secCircleReveal3.centerY = (int) r1.getY();
                }
            }
        }
        lightRevealScrim.setRevealEffect(centralSurfacesImpl.mSecLightRevealScrimHelper.secCircleReveal);
        if (((NotificationShadeWindowControllerImpl) centralSurfacesImpl.mNotificationShadeWindowController).mHelper.getCurrentState().forceVisibleForUnlockAnimation) {
            return;
        }
        lightRevealScrim.setRevealAmount(1.0f - sysuiStatusBarStateController.getDozeAmount());
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x02f6, code lost:
    
        if (r8.intValue() != 2) goto L20;
     */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$1] */
    /* JADX WARN: Type inference failed for: r4v12, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$16] */
    /* JADX WARN: Type inference failed for: r4v13, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$17] */
    /* JADX WARN: Type inference failed for: r4v14, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$18] */
    /* JADX WARN: Type inference failed for: r4v16, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$20] */
    /* JADX WARN: Type inference failed for: r4v17, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$21] */
    /* JADX WARN: Type inference failed for: r4v18, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$22] */
    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r4v7, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$10] */
    /* JADX WARN: Type inference failed for: r4v9, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$13] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public CentralSurfacesImpl(com.android.systemui.mdm.MdmOverlayContainer r6, dagger.Lazy r7, com.android.systemui.bixby2.SystemUICommandActionHandler r8, com.android.systemui.keyguard.DisplayLifecycle r9, com.android.systemui.subscreen.SubScreenManager r10, android.content.Context r11, com.android.systemui.statusbar.notification.init.NotificationsController r12, com.android.systemui.fragments.FragmentService r13, com.android.systemui.statusbar.phone.LightBarController r14, com.android.systemui.statusbar.phone.AutoHideController r15, com.android.systemui.statusbar.core.StatusBarInitializer r16, com.android.systemui.statusbar.window.StatusBarWindowControllerStore r17, com.android.systemui.statusbar.window.StatusBarWindowStateController r18, com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore r19, com.android.keyguard.KeyguardUpdateMonitor r20, com.android.systemui.statusbar.phone.StatusBarSignalPolicy r21, com.android.systemui.statusbar.PulseExpansionHandler r22, com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator r23, com.android.systemui.statusbar.phone.KeyguardBypassController r24, com.android.systemui.statusbar.policy.KeyguardStateController r25, com.android.systemui.statusbar.notification.headsup.HeadsUpManager r26, com.android.systemui.plugins.FalsingManager r27, com.android.systemui.classifier.FalsingCollector r28, com.android.systemui.broadcast.BroadcastDispatcher r29, com.android.systemui.statusbar.notification.row.NotificationGutsManager r30, com.android.systemui.shade.ShadeExpansionStateManager r31, com.android.systemui.keyguard.KeyguardViewMediator r32, android.util.DisplayMetrics r33, com.android.internal.logging.MetricsLogger r34, com.android.systemui.shade.ShadeLogger r35, com.android.systemui.util.kotlin.JavaAdapter r36, java.util.concurrent.Executor r37, com.android.systemui.shade.ShadeSurface r38, com.android.systemui.media.NotificationMediaManager r39, com.android.systemui.statusbar.NotificationLockscreenUserManager r40, com.android.systemui.statusbar.NotificationRemoteInputManager r41, com.android.systemui.shade.QuickSettingsController r42, com.android.systemui.statusbar.policy.BatteryController r43, com.android.systemui.colorextraction.SysuiColorExtractor r44, com.android.systemui.keyguard.ScreenLifecycle r45, com.android.systemui.keyguard.WakefulnessLifecycle r46, com.android.systemui.power.domain.interactor.PowerInteractor r47, com.android.systemui.communal.domain.interactor.CommunalInteractor r48, com.android.systemui.statusbar.SysuiStatusBarStateController r49, java.util.Optional<com.android.wm.shell.bubbles.Bubbles> r50, dagger.Lazy r51, com.android.systemui.statusbar.policy.DeviceProvisionedController r52, com.android.systemui.navigationbar.NavigationBarController r53, com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController r54, dagger.Lazy r55, com.android.systemui.statusbar.policy.ConfigurationController r56, com.android.systemui.statusbar.NotificationShadeWindowController r57, dagger.Lazy r58, com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r59, dagger.Lazy r60, dagger.Lazy r61, com.android.systemui.statusbar.notification.NotificationLaunchAnimatorControllerProvider r62, com.android.systemui.statusbar.phone.DozeParameters r63, com.android.systemui.statusbar.phone.ScrimController r64, dagger.Lazy r65, com.android.systemui.biometrics.AuthRippleController r66, com.android.systemui.statusbar.phone.DozeServiceHost r67, com.android.systemui.back.domain.interactor.BackActionInteractor r68, android.os.PowerManager r69, com.android.systemui.statusbar.phone.DozeScrimController r70, com.android.systemui.volume.VolumeComponent r71, com.android.systemui.statusbar.CommandQueue r72, dagger.Lazy r73, com.android.systemui.plugins.PluginManager r74, com.android.systemui.shade.ShadeController r75, com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor r76, com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager r77, com.android.keyguard.ViewMediatorCallback r78, com.android.systemui.InitController r79, android.os.Handler r80, com.android.systemui.plugins.PluginDependencyProvider r81, com.android.systemui.statusbar.policy.ExtensionController r82, com.android.systemui.statusbar.policy.UserInfoControllerImpl r83, com.android.systemui.statusbar.phone.PhoneStatusBarPolicy r84, com.android.systemui.statusbar.KeyguardIndicationController r85, com.android.systemui.demomode.DemoModeController r86, dagger.Lazy r87, com.android.systemui.statusbar.phone.ShadeTouchableRegionManager r88, com.android.systemui.statusbar.phone.NotificationIconAreaController r89, com.android.systemui.settings.brightness.BrightnessSliderController.Factory r90, com.android.systemui.statusbar.phone.ScreenOffAnimationController r91, com.android.systemui.util.WallpaperController r92, com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController r93, com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager r94, com.android.systemui.statusbar.LockscreenShadeTransitionController r95, com.android.systemui.flags.FeatureFlags r96, com.android.systemui.keyguard.KeyguardUnlockAnimationController r97, com.android.systemui.util.concurrency.DelayableExecutor r98, com.android.systemui.util.concurrency.MessageRouter r99, android.app.WallpaperManager r100, java.util.Optional<com.android.wm.shell.startingsurface.StartingWindowController.StartingSurfaceImpl> r101, com.android.systemui.animation.ActivityTransitionAnimator r102, android.hardware.devicestate.DeviceStateManager r103, com.android.systemui.charging.WiredChargingRippleController r104, android.service.dreams.IDreamManager r105, dagger.Lazy r106, dagger.Lazy r107, com.android.systemui.statusbar.LightRevealScrim r108, com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor r109, com.android.systemui.settings.UserTracker r110, com.android.systemui.plugins.ActivityStarter r111, com.android.systemui.settings.brightness.data.repository.BrightnessMirrorShowingRepository r112, com.android.systemui.shade.GlanceableHubContainerController r113, com.android.systemui.emergency.EmergencyGestureModule.EmergencyGestureIntentFactory r114, com.android.systemui.wallet.controller.QuickAccessWalletController r115, android.view.WindowManager r116, com.android.systemui.utils.windowmanager.WindowManagerProvider r117, com.android.systemui.blur.SecQpBlurController r118, dagger.Lazy r119, android.os.Handler r120, com.android.systemui.statusbar.notification.row.NotifRemoteViewCache r121, com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection r122, com.android.systemui.keyguard.KeyguardFoldController r123, dagger.Lazy r124, com.android.systemui.aiagent.AiAgentEffect r125) {
        /*
            Method dump skipped, instructions count: 812
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.CentralSurfacesImpl.<init>(com.android.systemui.mdm.MdmOverlayContainer, dagger.Lazy, com.android.systemui.bixby2.SystemUICommandActionHandler, com.android.systemui.keyguard.DisplayLifecycle, com.android.systemui.subscreen.SubScreenManager, android.content.Context, com.android.systemui.statusbar.notification.init.NotificationsController, com.android.systemui.fragments.FragmentService, com.android.systemui.statusbar.phone.LightBarController, com.android.systemui.statusbar.phone.AutoHideController, com.android.systemui.statusbar.core.StatusBarInitializer, com.android.systemui.statusbar.window.StatusBarWindowControllerStore, com.android.systemui.statusbar.window.StatusBarWindowStateController, com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore, com.android.keyguard.KeyguardUpdateMonitor, com.android.systemui.statusbar.phone.StatusBarSignalPolicy, com.android.systemui.statusbar.PulseExpansionHandler, com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator, com.android.systemui.statusbar.phone.KeyguardBypassController, com.android.systemui.statusbar.policy.KeyguardStateController, com.android.systemui.statusbar.notification.headsup.HeadsUpManager, com.android.systemui.plugins.FalsingManager, com.android.systemui.classifier.FalsingCollector, com.android.systemui.broadcast.BroadcastDispatcher, com.android.systemui.statusbar.notification.row.NotificationGutsManager, com.android.systemui.shade.ShadeExpansionStateManager, com.android.systemui.keyguard.KeyguardViewMediator, android.util.DisplayMetrics, com.android.internal.logging.MetricsLogger, com.android.systemui.shade.ShadeLogger, com.android.systemui.util.kotlin.JavaAdapter, java.util.concurrent.Executor, com.android.systemui.shade.ShadeSurface, com.android.systemui.media.NotificationMediaManager, com.android.systemui.statusbar.NotificationLockscreenUserManager, com.android.systemui.statusbar.NotificationRemoteInputManager, com.android.systemui.shade.QuickSettingsController, com.android.systemui.statusbar.policy.BatteryController, com.android.systemui.colorextraction.SysuiColorExtractor, com.android.systemui.keyguard.ScreenLifecycle, com.android.systemui.keyguard.WakefulnessLifecycle, com.android.systemui.power.domain.interactor.PowerInteractor, com.android.systemui.communal.domain.interactor.CommunalInteractor, com.android.systemui.statusbar.SysuiStatusBarStateController, java.util.Optional, dagger.Lazy, com.android.systemui.statusbar.policy.DeviceProvisionedController, com.android.systemui.navigationbar.NavigationBarController, com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController, dagger.Lazy, com.android.systemui.statusbar.policy.ConfigurationController, com.android.systemui.statusbar.NotificationShadeWindowController, dagger.Lazy, com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController, dagger.Lazy, dagger.Lazy, com.android.systemui.statusbar.notification.NotificationLaunchAnimatorControllerProvider, com.android.systemui.statusbar.phone.DozeParameters, com.android.systemui.statusbar.phone.ScrimController, dagger.Lazy, com.android.systemui.biometrics.AuthRippleController, com.android.systemui.statusbar.phone.DozeServiceHost, com.android.systemui.back.domain.interactor.BackActionInteractor, android.os.PowerManager, com.android.systemui.statusbar.phone.DozeScrimController, com.android.systemui.volume.VolumeComponent, com.android.systemui.statusbar.CommandQueue, dagger.Lazy, com.android.systemui.plugins.PluginManager, com.android.systemui.shade.ShadeController, com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor, com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.ViewMediatorCallback, com.android.systemui.InitController, android.os.Handler, com.android.systemui.plugins.PluginDependencyProvider, com.android.systemui.statusbar.policy.ExtensionController, com.android.systemui.statusbar.policy.UserInfoControllerImpl, com.android.systemui.statusbar.phone.PhoneStatusBarPolicy, com.android.systemui.statusbar.KeyguardIndicationController, com.android.systemui.demomode.DemoModeController, dagger.Lazy, com.android.systemui.statusbar.phone.ShadeTouchableRegionManager, com.android.systemui.statusbar.phone.NotificationIconAreaController, com.android.systemui.settings.brightness.BrightnessSliderController$Factory, com.android.systemui.statusbar.phone.ScreenOffAnimationController, com.android.systemui.util.WallpaperController, com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController, com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager, com.android.systemui.statusbar.LockscreenShadeTransitionController, com.android.systemui.flags.FeatureFlags, com.android.systemui.keyguard.KeyguardUnlockAnimationController, com.android.systemui.util.concurrency.DelayableExecutor, com.android.systemui.util.concurrency.MessageRouter, android.app.WallpaperManager, java.util.Optional, com.android.systemui.animation.ActivityTransitionAnimator, android.hardware.devicestate.DeviceStateManager, com.android.systemui.charging.WiredChargingRippleController, android.service.dreams.IDreamManager, dagger.Lazy, dagger.Lazy, com.android.systemui.statusbar.LightRevealScrim, com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor, com.android.systemui.settings.UserTracker, com.android.systemui.plugins.ActivityStarter, com.android.systemui.settings.brightness.data.repository.BrightnessMirrorShowingRepository, com.android.systemui.shade.GlanceableHubContainerController, com.android.systemui.emergency.EmergencyGestureModule$EmergencyGestureIntentFactory, com.android.systemui.wallet.controller.QuickAccessWalletController, android.view.WindowManager, com.android.systemui.utils.windowmanager.WindowManagerProvider, com.android.systemui.blur.SecQpBlurController, dagger.Lazy, android.os.Handler, com.android.systemui.statusbar.notification.row.NotifRemoteViewCache, com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection, com.android.systemui.keyguard.KeyguardFoldController, dagger.Lazy, com.android.systemui.aiagent.AiAgentEffect):void");
    }

    public final void awakenDreams() {
        this.mUiBgExecutor.execute(new CentralSurfacesImpl$$ExternalSyntheticLambda3(this, 2));
    }

    public final void checkBarModes$1() {
        this.mDemoModeController.getClass();
        int i = StatusBarConnectedDisplays.$r8$clinit;
        if (this.mStatusBarTransitions != null) {
            this.mStatusBarTransitions.transitionTo(((StatusBarMode) ((StatusBarModePerDisplayRepositoryImpl) ((StatusBarModePerDisplayRepository) this.mStatusBarModeRepository.getDefaultDisplay())).statusBarMode.$$delegate_0.getValue()).toTransitionModeInt(), (this.mNoAnimationOnNextBarModeChange || !this.mDeviceInteractive || this.mStatusBarWindowState == 2) ? false : true);
            this.mNoAnimationOnNextBarModeChange = false;
        }
        ((NavigationBarControllerImpl) this.mNavigationBarController).checkNavBarModes(this.mDisplayId);
    }

    public final void checkRemoteInputRequest(String str, String str2) {
        if (str == null) {
            Log.d("CentralSurfaces", " RemoteInput: extra value is null");
            return;
        }
        NotificationEntry entry = ((NotifCollection) Dependency.sDependency.getDependencyInner(NotifCollection.class)).getEntry(str);
        if (entry == null) {
            Log.d("CentralSurfaces", " RemoteInput: no entry for ".concat(str));
            return;
        }
        ExpandableNotificationRow expandableNotificationRow = entry.row;
        Notification notification2 = entry.mSbn.getNotification();
        Notification.Action[] actionArr = notification2.actions;
        if (actionArr == null || actionArr.length == 0) {
            Log.d("CentralSurfaces", " RemoteInput: no actions for ".concat(str));
            return;
        }
        int length = actionArr.length;
        for (int i = 0; i < length; i++) {
            if (notification2.actions[i].getRemoteInputs() != null) {
                this.mPowerInteractor.wakeUpIfDozing(4, "REMOTE_INPUT_CLICK");
                this.mMainHandler.postDelayed(new CentralSurfacesImpl$$ExternalSyntheticLambda12(this, expandableNotificationRow, str2, 0), 500L);
                return;
            }
        }
        Log.d("CentralSurfaces", " RemoteInput: no remote input for ".concat(str));
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x0250, code lost:
    
        if (r1.getPackageManager().getApplicationInfoAsUser(r4, 0, r3).enabled != false) goto L53;
     */
    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    @dalvik.annotation.optimization.NeverCompile
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dump(java.io.PrintWriter r8, java.lang.String[] r9) {
        /*
            Method dump skipped, instructions count: 788
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.CentralSurfacesImpl.dump(java.io.PrintWriter, java.lang.String[]):void");
    }

    public final void finishKeyguardFadingAway() {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        keyguardStateControllerImpl.notifyKeyguardGoingAway(false);
        if (keyguardStateControllerImpl.mKeyguardFadingAway) {
            TrackTracer.instantForGroup(0, "keyguard", "FadingAway");
            keyguardStateControllerImpl.mKeyguardFadingAway = false;
            keyguardStateControllerImpl.invokeForEachCallback(new KeyguardStateControllerImpl$$ExternalSyntheticLambda0(0));
        }
        this.mScrimController.mExpansionAffectsAlpha = true;
        this.mKeyguardViewMediator.maybeHandlePendingLock();
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.mLifecycle;
    }

    public final NavigationBarView getNavigationBarView() {
        return ((NavigationBarControllerImpl) this.mNavigationBarController).getNavigationBarView(this.mDisplayId);
    }

    public final NotificationShadeWindowViewController getNotificationShadeWindowViewController() {
        return (NotificationShadeWindowViewController) this.mNotificationShadeWindowViewControllerLazy.get();
    }

    public final boolean hideKeyguard() {
        ((StatusBarStateControllerImpl) this.mStatusBarStateController).mKeyguardRequested = false;
        return updateIsKeyguard(false);
    }

    @Deprecated
    public void initShadeVisibilityListener() {
        this.mShadeController.setVisibilityListener(new AnonymousClass4());
    }

    public final boolean isForegroundComponentName(ComponentName componentName) {
        View decorView;
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z = keyguardStateControllerImpl.mShowing;
        DisplayLifecycle displayLifecycle = this.mDisplayLifecycle;
        boolean z2 = false;
        if (z && !keyguardStateControllerImpl.mOccluded) {
            if (!LsRune.LOCKUI_SUB_DISPLAY_COVER) {
                Log.d("CentralSurfaces", "Checking ForegroundComponent - Lockscreen Shown");
                return false;
            }
            if (displayLifecycle.mIsFolderOpened) {
                Log.d("CentralSurfaces", "Checking ForegroundComponent - fold opened");
                return false;
            }
        }
        if (LsRune.SUBSCREEN_UI && !displayLifecycle.mIsFolderOpened) {
            Window window$1 = this.mSubScreenManager.getWindow$1();
            if ((window$1 == null || (decorView = window$1.getDecorView()) == null) ? false : decorView.hasWindowFocus()) {
                Log.d("CentralSurfaces", "Checking ForegroundComponent - SubScreen is focused");
                return false;
            }
        }
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) this.mContext.getSystemService(ActivityManager.class)).getRunningTasks(1);
        if (runningTasks != null && !runningTasks.isEmpty() && runningTasks.get(0) != null && componentName.equals(runningTasks.get(0).topActivity)) {
            z2 = true;
        }
        EmergencyButtonController$$ExternalSyntheticOutline0.m("Foreground component state :: ", "CentralSurfaces", z2);
        return z2;
    }

    public final boolean isGoingToSleep() {
        return this.mWakefulnessLifecycle.mWakefulness == 3;
    }

    public final void logStateToEventlog() {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z = keyguardStateControllerImpl.mShowing;
        boolean z2 = keyguardStateControllerImpl.mOccluded;
        boolean isBouncerShowing = this.mStatusBarKeyguardViewManager.isBouncerShowing();
        boolean z3 = keyguardStateControllerImpl.mSecure;
        boolean z4 = keyguardStateControllerImpl.mCanDismissLockScreen;
        int i = (this.mState & 255) | ((z ? 1 : 0) << 8) | ((z2 ? 1 : 0) << 9) | ((isBouncerShowing ? 1 : 0) << 10) | ((z3 ? 1 : 0) << 11) | ((z4 ? 1 : 0) << 12);
        if (i != this.mLastLoggedStateFingerprint) {
            if (this.mStatusBarStateLog == null) {
                this.mStatusBarStateLog = new LogMaker(0);
            }
            this.mMetricsLogger.write(this.mStatusBarStateLog.setCategory(isBouncerShowing ? 197 : 196).setType(z ? 1 : 2).setSubtype(z3 ? 1 : 0));
            EventLog.writeEvent(36004, Integer.valueOf(this.mState), Integer.valueOf(z ? 1 : 0), Integer.valueOf(z2 ? 1 : 0), Integer.valueOf(isBouncerShowing ? 1 : 0), Integer.valueOf(z3 ? 1 : 0), Integer.valueOf(z4 ? 1 : 0));
            this.mLastLoggedStateFingerprint = i;
            StringBuilder sb = new StringBuilder();
            sb.append(isBouncerShowing ? "BOUNCER" : "LOCKSCREEN");
            sb.append(z ? "_OPEN" : "_CLOSE");
            sb.append(z3 ? "_SECURE" : "_INSECURE");
            sUiEventLogger.log(StatusBarUiEvent.valueOf(sb.toString()));
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void onTrimMemory(int i) {
        NotifRemoteViewCache notifRemoteViewCache;
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "SYSUI_RAM_OPTIMIZATION onTrimMemory=", "CentralSurfaces");
        if (i == 40 && (notifRemoteViewCache = this.mNotifRemoteViewCache) != null) {
            Iterator it = ((NotifPipeline) this.mNotifCollection).getAllNotifs().iterator();
            while (it.hasNext()) {
                SparseArray sparseArray = (SparseArray) ((ArrayMap) ((NotifRemoteViewCacheImpl) notifRemoteViewCache).mNotifCachedContentViews).get((NotificationEntry) it.next());
                if (sparseArray != null) {
                    sparseArray.clear();
                }
            }
        }
    }

    public void registerBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("com.sec.aecmonitor.ONE_CYCLE_FINISH");
        this.mBroadcastDispatcher.registerReceiver(this.mBroadcastReceiver, intentFilter, null, UserHandle.ALL);
    }

    public void registerCallbacks() {
        AiAgentEffect aiAgentEffect;
        this.mDeviceStateManager.registerCallback(this.mMainExecutor, new FoldStateListener(this.mContext, new CentralSurfacesImpl$$ExternalSyntheticLambda23(this)));
        this.mJavaAdapter.alwaysCollectFlow(this.mCommunalInteractor.isIdleOnCommunal, this.mIdleOnCommunalConsumer);
        int i = SceneContainerFlag.$r8$clinit;
        int i2 = QSComposeFragment.$r8$clinit;
        if (!BasicRune.AI_AGENT_EFFECT || (aiAgentEffect = this.mAiAgentEffect) == null) {
            return;
        }
        aiAgentEffect.privacyController.addCallback(aiAgentEffect.callback);
    }

    public final void releaseGestureWakeLock() {
        if (this.mGestureWakeLock.isHeld()) {
            this.mGestureWakeLock.release();
        }
    }

    @Override // com.android.systemui.statusbar.phone.CentralSurfaces
    public void setBarStateForTest(int i) {
        this.mState = i;
    }

    public final void setBouncerShowingForStatusBarComponents(boolean z) {
        int i = StatusBarConnectedDisplays.$r8$clinit;
        PhoneStatusBarViewController phoneStatusBarViewController = this.mPhoneStatusBarViewController;
        if (phoneStatusBarViewController != null) {
            phoneStatusBarViewController.setImportantForAccessibility(z ? 4 : 0);
        }
        int i2 = z ? 4 : 2;
        ShadeSurface shadeSurface = this.mShadeSurface;
        shadeSurface.setImportantForAccessibility(i2);
        shadeSurface.setBouncerShowing(z);
    }

    public final void setInteracting(int i, boolean z) {
        int i2;
        if (z) {
            i2 = i | this.mInteractingWindows;
        } else {
            i2 = (~i) & this.mInteractingWindows;
        }
        this.mInteractingWindows = i2;
        AutoHideController autoHideController = this.mAutoHideController;
        if (i2 != 0) {
            ((AutoHideControllerImpl) autoHideController).suspendAutoHide();
        } else {
            ((AutoHideControllerImpl) autoHideController).resumeSuspendedAutoHide();
        }
        checkBarModes$1();
    }

    public final void setIsLaunchingActivityOverLockscreen(boolean z, boolean z2) {
        this.mIsLaunchingActivityOverLockscreen = z;
        this.mDismissingShadeForActivityLaunch = z2;
        this.mKeyguardViewMediator.launchingActivityOverLockscreen(z);
    }

    public final void setPrimaryBouncerHiddenFraction(float f) {
        ScrimController scrimController = this.mScrimController;
        if (scrimController.mBouncerHiddenFraction == f) {
            return;
        }
        scrimController.mBouncerHiddenFraction = f;
        ScrimState scrimState = scrimController.mState;
        if (scrimState == ScrimState.DREAMING || scrimState == ScrimState.GLANCEABLE_HUB || scrimState == ScrimState.GLANCEABLE_HUB_OVER_DREAM) {
            scrimController.applyAndDispatchState();
        }
    }

    public final boolean shouldIgnoreTouch() {
        if (this.mStatusBarStateController.isDozing() && this.mDozeServiceHost.mIgnoreTouchWhilePulsing) {
            return true;
        }
        List list = this.mScreenOffAnimationController.animations;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (((ScreenOffAnimation) it.next()).isAnimationPlaying()) {
                return true;
            }
        }
        return false;
    }

    public final boolean shouldUseTabletKeyboardShortcuts() {
        return ((FeatureFlagsClassicRelease) this.mFeatureFlags).isEnabled(Flags.SHORTCUT_LIST_SEARCH_LAYOUT) && Utilities.isLargeScreen(this.mWindowManager, this.mContext.getResources());
    }

    public final void showKeyguard() {
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.mStatusBarStateController;
        statusBarStateControllerImpl.mKeyguardRequested = true;
        statusBarStateControllerImpl.setLeaveOpenOnKeyguardHide(false);
        updateIsKeyguard(false);
        final AssistManager assistManager = (AssistManager) this.mAssistManagerLazy.get();
        assistManager.getClass();
        AsyncTask.execute(new Runnable() { // from class: com.android.systemui.assist.AssistManager.7
            @Override // java.lang.Runnable
            public final void run() {
                AssistManager.this.mAssistUtils.onLockscreenShown();
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:119:0x0514, code lost:
    
        if (r18 != 0) goto L124;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v10, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$7] */
    /* JADX WARN: Type inference failed for: r15v12, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$4] */
    /* JADX WARN: Type inference failed for: r15v13, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$3] */
    /* JADX WARN: Type inference failed for: r15v14, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$2] */
    /* JADX WARN: Type inference failed for: r15v15, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$1] */
    /* JADX WARN: Type inference failed for: r15v9, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$8] */
    /* JADX WARN: Type inference failed for: r4v119, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$12] */
    /* JADX WARN: Type inference failed for: r4v120, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$11] */
    /* JADX WARN: Type inference failed for: r7v10, types: [android.content.BroadcastReceiver, com.android.systemui.navigationbar.interactor.PackageRemovedInteractor$addCallback$2] */
    /* JADX WARN: Type inference failed for: r7v12, types: [android.content.BroadcastReceiver, com.android.systemui.navigationbar.interactor.SettingsSoftResetInteractor$addCallback$2] */
    /* JADX WARN: Type inference failed for: r7v14, types: [com.android.systemui.navigationbar.interactor.RotationLockInteractor$addCallback$2, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v15, types: [com.android.systemui.navigationbar.interactor.KnoxStateMonitorInteractor$addCallback$2] */
    @Override // com.android.systemui.CoreStartable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void start() {
        /*
            Method dump skipped, instructions count: 3173
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.CentralSurfacesImpl.start():void");
    }

    public final void updateBubblesVisibility() {
        final StatusBarMode statusBarMode = (StatusBarMode) ((StatusBarModePerDisplayRepositoryImpl) ((StatusBarModePerDisplayRepository) this.mStatusBarModeRepository.getDefaultDisplay())).statusBarMode.$$delegate_0.getValue();
        this.mBubblesOptional.ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                boolean z;
                int i = 2;
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                StatusBarMode statusBarMode2 = statusBarMode;
                Bubbles bubbles = (Bubbles) obj;
                UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
                centralSurfacesImpl.getClass();
                if (statusBarMode2 == StatusBarMode.LIGHTS_OUT || statusBarMode2 == StatusBarMode.LIGHTS_OUT_TRANSPARENT || centralSurfacesImpl.mStatusBarWindowState == 2) {
                    NavBarHelper navBarHelper = (NavBarHelper) centralSurfacesImpl.mNavBarHelperLazy.get();
                    int i2 = centralSurfacesImpl.mDisplayId;
                    navBarHelper.getClass();
                    if (new NavBarHelper.CurrentSysuiState(navBarHelper, i2).mWindowState == 2) {
                        z = false;
                        BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubbles;
                        BubbleController.this.mMainExecutor.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda10(bubblesImpl, z, i));
                    }
                }
                z = true;
                BubbleController.BubblesImpl bubblesImpl2 = (BubbleController.BubblesImpl) bubbles;
                BubbleController.this.mMainExecutor.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda10(bubblesImpl2, z, i));
            }
        });
    }

    public final void updateDozingState() {
        boolean z = false;
        if (Trace.isTagEnabled(4096L)) {
            Trace.asyncTraceForTrackEnd(4096L, "Dozing", 0);
            Trace.asyncTraceForTrackBegin(4096L, "Dozing", String.valueOf(this.mDozing), 0);
        }
        Trace.beginSection("CentralSurfaces#updateDozingState");
        boolean isVisible = this.mKeyguardStateController.isVisible();
        DozeParameters dozeParameters = this.mDozeParameters;
        boolean z2 = isVisible || (this.mDozing && dozeParameters.mScreenOffAnimationController.shouldDelayKeyguardShow());
        if ((!this.mDozing && this.mDozeServiceHost.mAnimateWakeup && this.mBiometricUnlockController.mMode != 1 && !this.mSecLightRevealScrimHelper.disableLightRevealAnimation()) || (this.mDozing && dozeParameters.mControlScreenOffAnimation && (z2 || (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY && this.mIsFolded)))) {
            z = true;
        }
        this.mShadeSurface.setDozing(this.mDozing, z);
        Trace.endSection();
    }

    public final boolean updateIsKeyguard(boolean z) {
        StringBuilder sb;
        int i;
        boolean z2;
        int i2;
        boolean isWakeAndUnlock = this.mBiometricUnlockController.isWakeAndUnlock();
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z3 = keyguardStateControllerImpl.mShowing && !keyguardStateControllerImpl.mOccluded;
        boolean z4 = this.mDozeServiceHost.mDozingRequested;
        ScreenLifecycle screenLifecycle = this.mScreenLifecycle;
        boolean z5 = z4 && (!this.mDeviceInteractive || (isGoingToSleep() && (screenLifecycle.mScreenState == 0 || z3)));
        boolean z6 = keyguardStateControllerImpl.mOccluded && ((i2 = this.mWakefulnessLifecycle.mWakefulness) == 1 || i2 == 2);
        SysuiStatusBarStateController sysuiStatusBarStateController = this.mStatusBarStateController;
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) sysuiStatusBarStateController;
        boolean z7 = ((!statusBarStateControllerImpl.mKeyguardRequested && !z5) || isWakeAndUnlock || z6) ? false : true;
        if (z5) {
            updatePanelExpansionForKeyguard();
        }
        MdmOverlayContainer mdmOverlayContainer = this.mMdmOverlayContainer;
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.mLockscreenShadeTransitionController;
        MessageRouter messageRouter = this.mMessageRouter;
        ShadeSurface shadeSurface = this.mShadeSurface;
        ScreenOffAnimationController screenOffAnimationController = this.mScreenOffAnimationController;
        if (z7) {
            if (screenOffAnimationController.isKeyguardShowDelayed()) {
                return false;
            }
            if (isGoingToSleep() && screenLifecycle.mScreenState == 3) {
                return false;
            }
            Trace.beginSection("CentralSurfaces#showKeyguard");
            keyguardStateControllerImpl.getClass();
            messageRouter.cancelMessages(1003);
            if (lockscreenShadeTransitionController.isWakingToShadeLocked) {
                i = 1;
            } else {
                i = 1;
                sysuiStatusBarStateController.setState(1);
            }
            if (sysuiStatusBarStateController.getState() == i && this.mQsController.getExpanded()) {
                z2 = false;
                shadeSurface.resetViews(false);
            } else {
                z2 = false;
            }
            mdmOverlayContainer.updateMdmPolicy();
            updatePanelExpansionForKeyguard();
            Trace.endSection();
            return z2;
        }
        StringBuilder sb2 = new StringBuilder("!shouldBeKeyguard mStatusBarStateController.isKeyguardRequested() ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb2, statusBarStateControllerImpl.mKeyguardRequested, " keyguardForDozing ", z5, " wakeAndUnlocking ");
        CarrierTextManager$$ExternalSyntheticOutline0.m(sb2, isWakeAndUnlock, " isWakingAndOccluded ", z6, "CentralSurfaces");
        List list = screenOffAnimationController.animations;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (((ScreenOffAnimation) it.next()).isKeyguardHideDelayed()) {
                    return false;
                }
            }
        }
        if (this.mKeyguardViewMediator.isOccludeAnimationPlaying()) {
            return false;
        }
        Log.d("CentralSurfaces", "hideKeyguardImpl " + z);
        Trace.beginSection("CentralSurfaces#hideKeyguard");
        boolean z8 = statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide;
        int state = sysuiStatusBarStateController.getState();
        StatusBarStateControllerImpl statusBarStateControllerImpl2 = (StatusBarStateControllerImpl) sysuiStatusBarStateController;
        if (!statusBarStateControllerImpl2.setState(0, z)) {
            ((NotificationLockscreenUserManagerImpl) this.mLockscreenUserManager).updatePublicMode();
            int i3 = SceneContainerFlag.$r8$clinit;
            mdmOverlayContainer.updateMdmPolicy();
        }
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (quickPanelLogger != null && (sb = this.mQuickPanelLogBuilder) != null) {
            sb.setLength(0);
            sb.append("hideKeyguardImpl: mStatusBarStateController.leaveOpenOnKeyguardHide(): ");
            sb.append(statusBarStateControllerImpl2.mLeaveOpenOnKeyguardHide);
            sb.append(", !mShadeSurface.isCollapsing(): ");
            sb.append(!shadeSurface.isCollapsing());
            quickPanelLogger.logPanelState(sb.toString());
        }
        if (statusBarStateControllerImpl2.mLeaveOpenOnKeyguardHide) {
            long j = keyguardStateControllerImpl.mKeyguardFadingAwayDelay + keyguardStateControllerImpl.mKeyguardFadingAwayDuration;
            LSShadeTransitionLogger lSShadeTransitionLogger = lockscreenShadeTransitionController.logger;
            lSShadeTransitionLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            LSShadeTransitionLogger$$ExternalSyntheticLambda0 lSShadeTransitionLogger$$ExternalSyntheticLambda0 = new LSShadeTransitionLogger$$ExternalSyntheticLambda0(1);
            LogBuffer logBuffer = lSShadeTransitionLogger.buffer;
            logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$$ExternalSyntheticLambda0, null));
            Function1 function1 = lockscreenShadeTransitionController.animationHandlerOnKeyguardDismiss;
            if (function1 != null) {
                function1.mo779invoke(Long.valueOf(j));
                lockscreenShadeTransitionController.animationHandlerOnKeyguardDismiss = null;
            } else if (lockscreenShadeTransitionController.nextHideKeyguardNeedsNoAnimation) {
                lockscreenShadeTransitionController.nextHideKeyguardNeedsNoAnimation = false;
            } else if (state != 2) {
                lockscreenShadeTransitionController.performDefaultGoToFullShadeAnimation(j);
            }
            ((NavigationBarControllerImpl) this.mNavigationBarController).disableAnimationsDuringHide(this.mDisplayId, j);
        } else if (!shadeSurface.isCollapsing()) {
            this.mShadeController.instantCollapseShade();
            this.mRemoteInputManager.closeRemoteInputs(true);
        }
        messageRouter.cancelMessages(1003);
        releaseGestureWakeLock();
        ((CameraLauncher) this.mCameraLauncherLazy.get()).setLaunchingAffordance(false);
        shadeSurface.resetAlpha();
        shadeSurface.resetTranslation();
        shadeSurface.resetViewGroupFade();
        updateDozingState();
        updateScrimController();
        Trace.endSection();
        return z8;
    }

    public final void updateNotificationPanelTouchState() {
        boolean isGoingToSleep = isGoingToSleep();
        DozeParameters dozeParameters = this.mDozeParameters;
        boolean z = !(this.mDeviceInteractive || this.mDozeServiceHost.mPulsing) || (isGoingToSleep && !dozeParameters.mControlScreenOffAnimation);
        boolean isGoingToSleep2 = isGoingToSleep();
        boolean z2 = !dozeParameters.mControlScreenOffAnimation;
        boolean z3 = !this.mDeviceInteractive;
        boolean z4 = !this.mDozeServiceHost.mPulsing;
        ShadeLogger shadeLogger = this.mShadeLogger;
        shadeLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        ShadeLogger$$ExternalSyntheticLambda0 shadeLogger$$ExternalSyntheticLambda0 = new ShadeLogger$$ExternalSyntheticLambda0(5);
        LogBuffer logBuffer = shadeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = isGoingToSleep2;
        logMessageImpl.bool3 = z2;
        logMessageImpl.bool4 = z3;
        logMessageImpl.str1 = String.valueOf(z4);
        logBuffer.commit(obtain);
        this.mShadeSurface.setTouchAndAnimationDisabled(z);
        int i = NotificationIconContainerRefactor.$r8$clinit;
        this.mNotificationIconAreaController.setAnimationsEnabled(!z);
    }

    public final void updatePanelExpansionForKeyguard() {
        if (this.mState != 1 || this.mBiometricUnlockController.mMode == 1 || this.mBouncerShowing) {
            return;
        }
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        if ((!((KeyguardStateControllerImpl) keyguardStateController).mShowing || ((KeyguardStateControllerImpl) keyguardStateController).mOccluded) && !this.mDozing) {
            return;
        }
        this.mShadeController.instantExpandShade();
    }

    public final void updateReportRejectedTouchVisibility() {
        View view = this.mReportRejectedTouch;
        if (view == null) {
            return;
        }
        if (this.mState == 1 && !this.mDozing) {
            this.mFalsingCollector.getClass();
        }
        view.setVisibility(4);
    }

    public final void updateResources$1() {
        float f;
        SecQSPanel.QSTileLayout qSTileLayout;
        if (!ShadeWindowGoesAround.isEnabled()) {
            SecQSPanelController secQSPanelController = this.mQSPanelController;
            if (secQSPanelController != null && (qSTileLayout = secQSPanelController.mTileLayout) != null) {
                qSTileLayout.updateResources();
                secQSPanelController.updatePaddingAndMargins();
            }
            ShadeSurface shadeSurface = this.mShadeSurface;
            if (shadeSurface != null) {
                shadeSurface.updateResources$1();
            }
        }
        BrightnessMirrorController brightnessMirrorController = this.mBrightnessMirrorController;
        if (brightnessMirrorController != null) {
            brightnessMirrorController.updateResources$1();
        }
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager;
        if (statusBarKeyguardViewManager != null) {
            statusBarKeyguardViewManager.updateResources$1();
        }
        if (!LsRune.AOD_LIGHT_REVEAL) {
            this.mPowerButtonReveal = new PowerButtonReveal(this.mContext.getResources().getDimensionPixelSize(R.dimen.physical_power_button_center_screen_location_y));
            return;
        }
        SecLightRevealScrimHelper secLightRevealScrimHelper = this.mSecLightRevealScrimHelper;
        secLightRevealScrimHelper.getClass();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Point point = new Point();
        Display display = secLightRevealScrimHelper.context.getDisplay();
        if (display != null) {
            display.getMetrics(displayMetrics);
            display.getSize(point);
        }
        try {
            if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK) {
                ((SemWindowManager) secLightRevealScrimHelper.semWindowManager$delegate.getValue()).getInitialDisplaySize(secLightRevealScrimHelper.physicalDisplaySize);
            }
            Point point2 = secLightRevealScrimHelper.physicalDisplaySize;
            int min = Math.min(point2.x, point2.y);
            int min2 = Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);
            f = min / min2;
            Log.i("SecLightRevealScrimHelper", "getPositionCorrectionRatio screenSizeRatio=" + f + " physicalScreenSize.x=" + secLightRevealScrimHelper.physicalDisplaySize.x + " baseWidthPixels = " + min + " currentWidthPixels = " + min2);
        } catch (Exception e) {
            Log.i("SecLightRevealScrimHelper", "getPositionCorrectionRatio exception = " + e);
            f = 1.0f;
        }
        secLightRevealScrimHelper.powerKeyYPos = (int) (secLightRevealScrimHelper.context.getResources().getDimensionPixelSize(17105834) / f);
        int i = point.x;
        secLightRevealScrimHelper.secRevealCenterX = i / 2.0f;
        int i2 = point.y;
        secLightRevealScrimHelper.secRevealCenterY = i2 / 2.0f;
        float hypot = (float) Math.hypot(i, i2);
        secLightRevealScrimHelper.secCircleReveal = new SecLightRevealScrimHelper.SecCircleReveal(secLightRevealScrimHelper.secRevealCenterX, secLightRevealScrimHelper.secRevealCenterY, hypot / 4, hypot / 2);
        float f2 = secLightRevealScrimHelper.secRevealCenterX;
        float f3 = secLightRevealScrimHelper.secRevealCenterY;
        int i3 = point.x;
        int i4 = point.y;
        int i5 = secLightRevealScrimHelper.powerKeyYPos;
        StringBuilder m = CubicBezierEasing$$ExternalSyntheticOutline0.m("updateResources: secRevealCenterX=", f2, " secRevealCenterY=", f3, " currentDisplaySize.x=");
        ViewPager$$ExternalSyntheticOutline0.m(m, i3, " currentDisplaySize.y=", i4, " powerKeyY=");
        m.append(i5);
        m.append(" radius=");
        m.append(hypot);
        Log.i("SecLightRevealScrimHelper", m.toString());
        this.mPowerButtonReveal = new PowerButtonReveal(this.mSecLightRevealScrimHelper.powerKeyYPos);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002d, code lost:
    
        if (r1.isAnimatingBetweenKeyguardAndSurfaceBehind() == false) goto L15;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00a0  */
    @Override // com.android.systemui.statusbar.phone.CentralSurfaces
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateScrimController() {
        /*
            Method dump skipped, instructions count: 371
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.CentralSurfacesImpl.updateScrimController():void");
    }

    public final void updateTheme() {
        ArrayList arrayList;
        this.mUiBgExecutor.execute(new CentralSurfacesImpl$$ExternalSyntheticLambda3(this, 1));
        int i = this.mColorExtractor.mNeutralColorsLock.supportsDarkText() ? R.style.Theme_SystemUI_LightWallpaper : R.style.Theme_SystemUI;
        if (this.mContext.getThemeResId() != i) {
            this.mContext.setTheme(i);
            ConfigurationControllerImpl configurationControllerImpl = (ConfigurationControllerImpl) this.mConfigurationController;
            synchronized (configurationControllerImpl.listeners) {
                arrayList = new ArrayList(configurationControllerImpl.listeners);
            }
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                ConfigurationController.ConfigurationListener configurationListener = (ConfigurationController.ConfigurationListener) obj;
                if (((ArrayList) configurationControllerImpl.listeners).contains(configurationListener)) {
                    configurationListener.onThemeChanged();
                }
            }
        }
    }

    public final void userActivity() {
        if (this.mState == 1) {
            if (LsRune.KEYGUARD_ADJUST_REFRESH_RATE_USER_ACTIVITY) {
                this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 2, 0);
            } else {
                this.mKeyguardViewMediatorCallback.userActivity();
            }
        }
    }
}
