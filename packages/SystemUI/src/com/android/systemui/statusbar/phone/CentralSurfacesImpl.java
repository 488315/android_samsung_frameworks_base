package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.IWallpaperManager;
import android.app.Notification;
import android.app.NotificationManager;
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
import android.graphics.drawable.Drawable;
import android.hardware.devicestate.DeviceStateManager;
import android.metrics.LogMaker;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.dreams.IDreamManager;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.EventLog;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.widget.FrameLayout;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.SceneKey;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.RegisterStatusBarResult;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.keyguard.KeyguardBouncerContainer;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
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
import com.android.systemui.Flags;
import com.android.systemui.InitController;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.Rune;
import com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController;
import com.android.systemui.aiagent.AiAgentEffect;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.back.domain.interactor.BackActionInteractor;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.charging.WirelessChargingAnimation;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dock.DockManager;
import com.android.systemui.doze.AODParameters;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.edgelighting.routine.EdgelightingRoutineActionHandler;
import com.android.systemui.emergency.EmergencyGestureModule;
import com.android.systemui.emergency.EmergencyGestureModule$emergencyGestureIntentFactory$1;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.fragments.ExtensionFragmentListener;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.MigrateClocksToBlueprint;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.QuickPanelLogger;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.mdm.MdmOverlayContainer;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.SecTaskBarManagerImpl;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.bandaid.BandAidPackFactory;
import com.android.systemui.navigationbar.interactor.ButtonOrderInteractor;
import com.android.systemui.navigationbar.interactor.ButtonPositionInteractor;
import com.android.systemui.navigationbar.interactor.ButtonToHideKeyboardInteractor;
import com.android.systemui.navigationbar.interactor.CoverDisplayWidgetInteractor;
import com.android.systemui.navigationbar.interactor.DeviceStateInteractor;
import com.android.systemui.navigationbar.interactor.DeviceStateInteractor.CoverTask;
import com.android.systemui.navigationbar.interactor.DeviceStateInteractor.MultimodalTask;
import com.android.systemui.navigationbar.interactor.EdgeBackGesturePolicyInteractor;
import com.android.systemui.navigationbar.interactor.GestureNavigationSettingsInteractor;
import com.android.systemui.navigationbar.interactor.InteractorFactory;
import com.android.systemui.navigationbar.interactor.KeyboardButtonPositionInteractor;
import com.android.systemui.navigationbar.interactor.KnoxStateMonitorInteractor;
import com.android.systemui.navigationbar.interactor.KnoxStateMonitorInteractor$addCallback$2;
import com.android.systemui.navigationbar.interactor.NavigationModeInteractor;
import com.android.systemui.navigationbar.interactor.OneHandModeInteractor;
import com.android.systemui.navigationbar.interactor.OpenThemeInteractor;
import com.android.systemui.navigationbar.interactor.PackageRemovedInteractor;
import com.android.systemui.navigationbar.interactor.PackageRemovedInteractor$addCallback$2;
import com.android.systemui.navigationbar.interactor.RotationLockInteractor;
import com.android.systemui.navigationbar.interactor.RotationLockInteractor$addCallback$2;
import com.android.systemui.navigationbar.interactor.SettingsSoftResetInteractor;
import com.android.systemui.navigationbar.interactor.SettingsSoftResetInteractor$addCallback$2;
import com.android.systemui.navigationbar.interactor.TaskBarInteractor;
import com.android.systemui.navigationbar.interactor.UseThemeDefaultInteractor;
import com.android.systemui.navigationbar.layout.NavBarCoverLayoutParams;
import com.android.systemui.navigationbar.model.NavBarStates;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$14;
import com.android.systemui.noticenter.NotiCenterPlugin;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.OverlayPlugin;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginDependencyProvider;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.ScreenPowerState;
import com.android.systemui.qs.QSFragmentLegacy;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQuickQSPanelController;
import com.android.systemui.qs.buttons.QSTooltipWindow;
import com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.ui.view.WindowRootView;
import com.android.systemui.scrim.ScrimDrawable;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.DisplayTrackerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.settings.brightness.BrightnessSliderController;
import com.android.systemui.shade.BaseShadeControllerImpl;
import com.android.systemui.shade.CameraLauncher;
import com.android.systemui.shade.GlanceableHubContainerController;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda11;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda3;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.NotificationShadeWindowViewController;
import com.android.systemui.shade.NotificationShadeWindowViewController$$ExternalSyntheticLambda5;
import com.android.systemui.shade.NotificationShadeWindowViewController.AnonymousClass1;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeLogger;
import com.android.systemui.shade.ShadeSurface;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.statusbar.AutoHideUiElement;
import com.android.systemui.statusbar.CircleReveal;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.KeyboardShortcuts;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.LiftReveal;
import com.android.systemui.statusbar.LightRevealScrim;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.PowerButtonReveal;
import com.android.systemui.statusbar.PulseExpansionHandler;
import com.android.systemui.statusbar.SecLightRevealScrimHelper;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.statusbar.core.StatusBarInitializer;
import com.android.systemui.statusbar.data.model.StatusBarMode;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryImpl;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.NotificationLaunchAnimatorControllerProvider;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotifRemoteViewCache;
import com.android.systemui.statusbar.notification.row.NotifRemoteViewCacheImpl;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.shared.NotificationIconContainerRefactor;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.BarTransitions;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.PhoneStatusBarTransitions;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ScrimController.AnonymousClass3;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentComponent;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BrightnessMirrorController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.statusbar.policy.ExtensionControllerImpl;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.statusbar.policy.SecurityControllerImpl;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.WallpaperController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.MessageRouter;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.android.systemui.volume.VolumeComponent;
import com.android.systemui.volume.VolumeDialogComponent;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.KeyguardWallpaperController$$ExternalSyntheticLambda1;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda10;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.common.HandlerExecutor;
import com.samsung.android.desktopsystemui.sharedlib.system.ActivityManagerWrapper;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.sdk.routines.v3.internal.RoutineSdkImpl;
import com.samsung.android.view.SemWindowManager;
import com.samsung.systemui.splugins.navigationbar.ColorSetting;
import com.samsung.systemui.splugins.noticenter.PluginNotiCenter;
import com.sec.ims.volte2.data.VolteConstants;
import dagger.Lazy;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.inject.Provider;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;

public final class CentralSurfacesImpl implements CoreStartable, CentralSurfaces, SecBrightnessMirrorControllerProvider {
    public static final UiEventLogger sUiEventLogger = new UiEventLoggerImpl();
    public final AccessibilityFloatingMenuController mAccessibilityFloatingMenuController;
    public final ActivityStarter mActivityStarter;
    public final ActivityTransitionAnimator mActivityTransitionAnimator;
    public final AiAgentEffect mAiAgentEffect;
    public final AlternateBouncerInteractor mAlternateBouncerInteractor;
    public View mAmbientIndicationContainer;
    public final Lazy mAssistManagerLazy;
    public final AutoHideController mAutoHideController;
    public final BackActionInteractor mBackActionInteractor;
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
    public final CentralSurfacesImpl$$ExternalSyntheticLambda4 mCheckBarModes;
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
    public final Provider mFingerprintManager;
    public final FragmentService mFragmentService;
    public PowerManager.WakeLock mGestureWakeLock;
    public final GlanceableHubContainerController mGlanceableHubContainerController;
    public final NotificationGutsManager mGutsManager;
    public final HeadsUpManager mHeadsUpManager;
    public final PhoneStatusBarPolicy mIconPolicy;
    public final CentralSurfacesImpl$$ExternalSyntheticLambda2 mIdleOnCommunalConsumer;
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
    public SecQuickQSPanelController mQuickQSPanelController;
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
    public final StatusBarTouchableRegionManager mStatusBarTouchableRegionManager;
    public PhoneStatusBarTransitions mStatusBarTransitions;
    public final StatusBarWindowController mStatusBarWindowController;
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
    public final WallpaperController mWallpaperController;
    public final WallpaperManager mWallpaperManager;
    public boolean mWallpaperSupported;
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
    public boolean mShouldDelayWakeUpAnimation = false;
    public boolean mShouldDelayLockscreenTransitionFromAod = false;
    public final Object mQueueLock = new Object();
    protected boolean mUserSetup = false;
    public final LifecycleRegistry mLifecycle = new LifecycleRegistry(this);
    public boolean mIsIdleOnCommunal = false;
    public final CentralSurfacesImpl$$ExternalSyntheticLambda3 mOnColorsChangedListener = new ColorExtractor.OnColorsChangedListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda3
        public final void onColorsChanged(ColorExtractor colorExtractor, int i) {
            CentralSurfacesImpl.this.updateTheme();
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
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.mFalsingCollector.onScreenTurningOn();
            centralSurfacesImpl.mShadeSurface.onScreenTurningOn();
        }
    };
    public final AnonymousClass12 mBannerActionBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.12
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.android.systemui.statusbar.banner_action_cancel".equals(action) || "com.android.systemui.statusbar.banner_action_setup".equals(action)) {
                ((NotificationManager) CentralSurfacesImpl.this.mContext.getSystemService(SubRoom.EXTRA_VALUE_NOTIFICATION)).cancel(5);
                Settings.Secure.putInt(CentralSurfacesImpl.this.mContext.getContentResolver(), "show_note_about_notification_hiding", 0);
                if ("com.android.systemui.statusbar.banner_action_setup".equals(action)) {
                    CentralSurfacesImpl.this.mShadeController.animateCollapseShade(1.0f, 0, true, false);
                    CentralSurfacesImpl.this.mContext.startActivity(new Intent("android.settings.ACTION_APP_NOTIFICATION_REDACTION").addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE));
                }
            }
        }
    };
    public final KeyguardUpdateMonitorCallback mUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.13
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onDreamingStateChanged(boolean z) {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.updateScrimController();
            if (z) {
                CentralSurfacesImpl.m2224$$Nest$mmaybeEscalateHeadsUp(centralSurfacesImpl);
            }
        }
    };
    public final AnonymousClass14 mFalsingBeliefListener = new FalsingManager.FalsingBeliefListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.14
        @Override // com.android.systemui.plugins.FalsingManager.FalsingBeliefListener
        public final void onFalse() {
            CentralSurfacesImpl.this.mStatusBarKeyguardViewManager.reset(true);
        }
    };
    public final AnonymousClass15 mUnlockScrimCallback = new AnonymousClass15();
    public final AnonymousClass16 mUserSetupObserver = new AnonymousClass16();
    public final AnonymousClass17 mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.17
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onConfigChanged(Configuration configuration) {
            AiAgentEffect aiAgentEffect;
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.updateResources$1();
            centralSurfacesImpl.mDisplay.getMetrics(centralSurfacesImpl.mDisplayMetrics);
            centralSurfacesImpl.mDisplay.getSize(centralSurfacesImpl.mCurrentDisplaySize);
            PredictiveBackSysUiFlag predictiveBackSysUiFlag = PredictiveBackSysUiFlag.INSTANCE;
            Flags.FEATURE_FLAGS.getClass();
            if (LsRune.SECURITY_BOUNCER_WINDOW) {
                Log.d("CentralSurfaces", "onConfigChanged setEnableOnBackInvokedCallback true");
            }
            centralSurfacesImpl.mContext.getApplicationInfo().setEnableOnBackInvokedCallback(true);
            QSTooltipWindow qSTooltipWindow = centralSurfacesImpl.mToolTipWindow;
            if (qSTooltipWindow != null) {
                qSTooltipWindow.hideToolTip();
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
    public final AnonymousClass18 mStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.18
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

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStatePreChange(int i, int i2) {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            if (((Boolean) centralSurfacesImpl.mWindowRootViewVisibilityInteractor.isLockscreenOrShadeVisible.$$delegate_0.getValue()).booleanValue() && (i2 == 2 || ((StatusBarStateControllerImpl) centralSurfacesImpl.mStatusBarStateController).goingToFullShade())) {
                try {
                    centralSurfacesImpl.mBarService.clearNotificationEffects();
                } catch (RemoteException unused) {
                }
            }
            if (i2 == 1) {
                RemoteInputCoordinator remoteInputCoordinator = centralSurfacesImpl.mRemoteInputManager.mRemoteInputListener;
                if (remoteInputCoordinator != null) {
                    remoteInputCoordinator.onPanelCollapsed();
                }
                CentralSurfacesImpl.m2224$$Nest$mmaybeEscalateHeadsUp(centralSurfacesImpl);
            }
        }
    };
    public final AnonymousClass19 mBatteryStateChangeCallback = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.19
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
    public final AnonymousClass20 mActivityTransitionAnimatorCallback = new AnonymousClass20();
    public final AnonymousClass21 mActivityTransitionAnimatorListener = new ActivityTransitionAnimator.Listener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.21
        @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
        public final void onTransitionAnimationEnd() {
            CentralSurfacesImpl.this.mKeyguardViewMediator.setBlursDisabledForAppLaunch(false);
        }

        @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
        public final void onTransitionAnimationStart() {
            CentralSurfacesImpl.this.mKeyguardViewMediator.setBlursDisabledForAppLaunch(true);
        }
    };
    public final AnonymousClass22 mDemoModeCallback = new DemoMode() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.22
        @Override // com.android.systemui.demomode.DemoModeCommandReceiver
        public final void onDemoModeFinished() {
            CentralSurfacesImpl.this.checkBarModes$1();
        }

        @Override // com.android.systemui.demomode.DemoModeCommandReceiver
        public final void dispatchDemoCommand(Bundle bundle, String str) {
        }
    };
    public final AnonymousClass23 mRemoteInputActionBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.23
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Objects.toString(intent);
            if ("com.samsung.systemui.action.REQUEST_REMOTE_INPUT".equals(intent.getAction())) {
                CentralSurfacesImpl.this.checkRemoteInputRequest(intent.getStringExtra("key"), null);
            }
        }
    };

    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$15, reason: invalid class name */
    public final class AnonymousClass15 implements ScrimController.Callback {
        public AnonymousClass15() {
        }

        @Override // com.android.systemui.statusbar.phone.ScrimController.Callback
        public final void onCancelled() {
            onFinished();
        }

        @Override // com.android.systemui.statusbar.phone.ScrimController.Callback
        public final void onFinished() {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            if (((KeyguardStateControllerImpl) centralSurfacesImpl.mKeyguardStateController).mKeyguardFadingAway) {
                centralSurfacesImpl.mStatusBarKeyguardViewManager.onKeyguardFadedAway$1();
            }
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$16, reason: invalid class name */
    public final class AnonymousClass16 implements DeviceProvisionedController.DeviceProvisionedListener {
        public AnonymousClass16() {
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
                    shadeSurface.setUserSetupComplete$1(centralSurfacesImpl.mUserSetup);
                }
            }
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$20, reason: invalid class name */
    public final class AnonymousClass20 implements ActivityTransitionAnimator.Callback {
        public AnonymousClass20() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$3, reason: invalid class name */
    public final class AnonymousClass3 implements PluginListener {
        public final ArraySet mOverlays = new ArraySet();

        /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$3$Callback */
        public final class Callback implements OverlayPlugin.Callback {
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
            CentralSurfacesImpl.this.mMainExecutor.execute(new CentralSurfacesImpl$3$$ExternalSyntheticLambda0(this, (OverlayPlugin) plugin, 0));
        }

        @Override // com.android.systemui.plugins.PluginListener
        public final void onPluginDisconnected(Plugin plugin) {
            CentralSurfacesImpl.this.mMainExecutor.execute(new CentralSurfacesImpl$3$$ExternalSyntheticLambda0(this, (OverlayPlugin) plugin, 1));
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$4, reason: invalid class name */
    public final class AnonymousClass4 implements ShadeController.ShadeVisibilityListener {
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
                    statusBarKeyguardViewManager.showBouncer();
                    return;
                }
                return;
            }
            if (i != 1 || statusBarKeyguardViewManager.primaryBouncerIsOrWillBeShowing()) {
                return;
            }
            Log.d("CentralSurfaces", "showBouncerOrLockScreenIfKeyguard, showingBouncer");
            statusBarKeyguardViewManager.showBouncer();
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$7, reason: invalid class name */
    public final class AnonymousClass7 implements WirelessChargingAnimation.Callback {
        public AnonymousClass7() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$8, reason: invalid class name */
    public final class AnonymousClass8 extends BroadcastReceiver {
        public AnonymousClass8() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Trace.beginSection("CentralSurfaces#onReceive");
            String action = intent.getAction();
            String stringExtra = intent.getStringExtra("reason");
            int i = 0;
            if (PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
                Flags.FEATURE_FLAGS.getClass();
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
                centralSurfacesImpl.shouldUseTabletKeyboardShortcuts();
                KeyboardShortcuts.dismiss();
                CentralSurfacesImpl.this.mRemoteInputManager.closeRemoteInputs(false);
                if (((NotificationLockscreenUserManagerImpl) CentralSurfacesImpl.this.mLockscreenUserManager).isCurrentProfile(getSendingUserId())) {
                    CentralSurfacesImpl.this.mShadeLogger.d("ACTION_CLOSE_SYSTEM_DIALOGS intent: closing shade");
                    if (stringExtra != null) {
                        r4 = stringExtra.equals(ActivityManagerWrapper.CLOSE_SYSTEM_WINDOWS_REASON_RECENTS) ? 2 : 0;
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

    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$9, reason: invalid class name */
    public final class AnonymousClass9 implements WakefulnessLifecycle.Observer {
        public AnonymousClass9() {
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedGoingToSleep() {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            ((CameraLauncher) centralSurfacesImpl.mCameraLauncherLazy.get()).mKeyguardBypassController.launchingAffordance = false;
            centralSurfacesImpl.releaseGestureWakeLock();
            centralSurfacesImpl.mLaunchCameraWhenFinishedWaking = false;
            centralSurfacesImpl.mDeviceInteractive = false;
            centralSurfacesImpl.updateNotificationPanelTouchState();
            centralSurfacesImpl.getNotificationShadeWindowViewController().cancelCurrentTouch();
            if (centralSurfacesImpl.mLaunchCameraOnFinishedGoingToSleep) {
                centralSurfacesImpl.mLaunchCameraOnFinishedGoingToSleep = false;
                centralSurfacesImpl.mMainExecutor.execute(new CentralSurfacesImpl$9$$ExternalSyntheticLambda2(this, 1));
            }
            if (centralSurfacesImpl.mLaunchEmergencyActionOnFinishedGoingToSleep) {
                centralSurfacesImpl.mLaunchEmergencyActionOnFinishedGoingToSleep = false;
                centralSurfacesImpl.mMainExecutor.execute(new CentralSurfacesImpl$9$$ExternalSyntheticLambda2(this, 2));
            }
            centralSurfacesImpl.updateIsKeyguard(false);
            boolean z = QpRune.QUICK_PANEL_BLUR_DEFAULT;
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedWakingUp() {
            UserHandle userHandle;
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            if (centralSurfacesImpl.mShouldDelayLockscreenTransitionFromAod) {
                ((NotificationShadeWindowControllerImpl) centralSurfacesImpl.mNotificationShadeWindowController).batchApplyWindowLayoutParams(new CentralSurfacesImpl$9$$ExternalSyntheticLambda2(this, 3));
            }
            NotificationWakeUpCoordinator notificationWakeUpCoordinator = centralSurfacesImpl.mWakeUpCoordinator;
            notificationWakeUpCoordinator.fullyAwake = true;
            notificationWakeUpCoordinator.setWakingUp(false, false);
            if (((KeyguardStateControllerImpl) centralSurfacesImpl.mKeyguardStateController).mOccluded && !centralSurfacesImpl.mDozeParameters.canControlUnlockedScreenOff()) {
                ((NotificationLockscreenUserManagerImpl) centralSurfacesImpl.mLockscreenUserManager).updatePublicMode();
                centralSurfacesImpl.mStackScrollerController.updateSensitivenessWithAnimation(false);
            }
            if (centralSurfacesImpl.mLaunchCameraWhenFinishedWaking) {
                ((CameraLauncher) centralSurfacesImpl.mCameraLauncherLazy.get()).launchCamera(centralSurfacesImpl.mLastCameraLaunchSource, centralSurfacesImpl.mShadeSurface.isFullyCollapsed());
                centralSurfacesImpl.mLaunchCameraWhenFinishedWaking = false;
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
            CentralSurfacesImpl.m2225$$Nest$mupdateRevealEffect(centralSurfacesImpl, false);
            centralSurfacesImpl.updateNotificationPanelTouchState();
            CentralSurfacesImpl.m2224$$Nest$mmaybeEscalateHeadsUp(centralSurfacesImpl);
            VolumeComponent volumeComponent = centralSurfacesImpl.mVolumeComponent;
            if (volumeComponent != null) {
                ((VolumeDialogComponent) volumeComponent).mController.mCallbacks.onDismissRequested(2);
            }
            centralSurfacesImpl.mWakeUpCoordinator.fullyAwake = false;
            centralSurfacesImpl.mKeyguardBypassController.pendingUnlock = null;
            centralSurfacesImpl.mStatusBarTouchableRegionManager.updateTouchableRegion();
            if (centralSurfacesImpl.mDozeParameters.shouldShowLightRevealScrim()) {
                centralSurfacesImpl.mShadeController.makeExpandedVisible(true);
            }
            DejankUtils.stopDetectingBlockingIpcs("CentralSurfaces#onStartedGoingToSleep");
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedWakingUp() {
            DejankUtils.startDetectingBlockingIpcs("CentralSurfaces#onStartedWakingUp");
            ((NotificationShadeWindowControllerImpl) CentralSurfacesImpl.this.mNotificationShadeWindowController).batchApplyWindowLayoutParams(new CentralSurfacesImpl$9$$ExternalSyntheticLambda2(this, 0));
            DejankUtils.stopDetectingBlockingIpcs("CentralSurfaces#onStartedWakingUp");
        }

        public final void startLockscreenTransitionFromAod() {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.mDozeServiceHost.stopDozing();
            CentralSurfacesImpl.m2225$$Nest$mupdateRevealEffect(centralSurfacesImpl, true);
            centralSurfacesImpl.updateNotificationPanelTouchState();
            centralSurfacesImpl.mStatusBarTouchableRegionManager.updateTouchableRegion();
            if (centralSurfacesImpl.mScreenOffAnimationController.shouldHideLightRevealScrimOnWakeUp()) {
                centralSurfacesImpl.mShadeController.makeExpandedInvisible();
            }
        }
    }

    public final class AnimateExpandSettingsPanelMessage {
        public final String mSubpanel;

        public AnimateExpandSettingsPanelMessage(String str) {
            this.mSubpanel = str;
        }
    }

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
    public static void m2224$$Nest$mmaybeEscalateHeadsUp(CentralSurfacesImpl centralSurfacesImpl) {
        BaseHeadsUpManager baseHeadsUpManager = (BaseHeadsUpManager) centralSurfacesImpl.mHeadsUpManager;
        baseHeadsUpManager.getHeadsUpEntryList().stream().map(new BaseHeadsUpManager$$ExternalSyntheticLambda0()).forEach(new CentralSurfacesImpl$$ExternalSyntheticLambda2(centralSurfacesImpl, 1));
        baseHeadsUpManager.releaseAllImmediately();
    }

    /* renamed from: -$$Nest$mupdateRevealEffect, reason: not valid java name */
    public static void m2225$$Nest$mupdateRevealEffect(CentralSurfacesImpl centralSurfacesImpl, boolean z) {
        boolean z2;
        LightRevealScrim lightRevealScrim = centralSurfacesImpl.mLightRevealScrim;
        if (lightRevealScrim == null) {
            return;
        }
        Flags.lightRevealMigration();
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
            StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("updateRevealEffect: wakingUp=", " wakingUpFromPowerButton=", " sleepingFromPowerButton=", z, z5);
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, z6, " wakingUpFromDoubleTap=", z7, " sleepingFromDoubleTap=");
            KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(m, z2, "CentralSurfaces");
            z4 = z7;
        } else {
            z2 = false;
        }
        SysuiStatusBarStateController sysuiStatusBarStateController = centralSurfacesImpl.mStatusBarStateController;
        if (z5 || z6) {
            lightRevealScrim.setRevealEffect(centralSurfacesImpl.mPowerButtonReveal);
            lightRevealScrim.setRevealAmount(1.0f - ((StatusBarStateControllerImpl) sysuiStatusBarStateController).mDozeAmount);
            return;
        }
        if (!z3) {
            if (z && (lightRevealScrim.revealEffect instanceof CircleReveal)) {
                return;
            }
            lightRevealScrim.setRevealEffect(LiftReveal.INSTANCE);
            lightRevealScrim.setRevealAmount(1.0f - ((StatusBarStateControllerImpl) sysuiStatusBarStateController).mDozeAmount);
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
        lightRevealScrim.setRevealAmount(1.0f - ((StatusBarStateControllerImpl) sysuiStatusBarStateController).mDozeAmount);
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0315, code lost:
    
        if (r3.intValue() != 2) goto L23;
     */
    /* JADX WARN: Type inference failed for: r7v1, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$1] */
    /* JADX WARN: Type inference failed for: r8v10, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$14] */
    /* JADX WARN: Type inference failed for: r8v13, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$17] */
    /* JADX WARN: Type inference failed for: r8v14, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$18] */
    /* JADX WARN: Type inference failed for: r8v15, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$19] */
    /* JADX WARN: Type inference failed for: r8v17, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$21] */
    /* JADX WARN: Type inference failed for: r8v18, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$22] */
    /* JADX WARN: Type inference failed for: r8v19, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$23] */
    /* JADX WARN: Type inference failed for: r8v3, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r8v7, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$10] */
    /* JADX WARN: Type inference failed for: r8v8, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$12] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public CentralSurfacesImpl(com.android.systemui.mdm.MdmOverlayContainer r10, dagger.Lazy r11, com.android.systemui.bixby2.SystemUICommandActionHandler r12, com.android.systemui.keyguard.DisplayLifecycle r13, com.android.systemui.subscreen.SubScreenManager r14, android.content.Context r15, com.android.systemui.statusbar.notification.init.NotificationsController r16, com.android.systemui.fragments.FragmentService r17, com.android.systemui.statusbar.phone.LightBarController r18, com.android.systemui.statusbar.phone.AutoHideController r19, com.android.systemui.statusbar.core.StatusBarInitializer r20, com.android.systemui.statusbar.window.StatusBarWindowController r21, com.android.systemui.statusbar.window.StatusBarWindowStateController r22, com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore r23, com.android.keyguard.KeyguardUpdateMonitor r24, com.android.systemui.statusbar.phone.StatusBarSignalPolicy r25, com.android.systemui.statusbar.PulseExpansionHandler r26, com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator r27, com.android.systemui.statusbar.phone.KeyguardBypassController r28, com.android.systemui.statusbar.policy.KeyguardStateController r29, com.android.systemui.statusbar.policy.HeadsUpManager r30, com.android.systemui.plugins.FalsingManager r31, com.android.systemui.classifier.FalsingCollector r32, com.android.systemui.broadcast.BroadcastDispatcher r33, com.android.systemui.statusbar.notification.row.NotificationGutsManager r34, com.android.systemui.shade.ShadeExpansionStateManager r35, com.android.systemui.keyguard.KeyguardViewMediator r36, android.util.DisplayMetrics r37, com.android.internal.logging.MetricsLogger r38, com.android.systemui.shade.ShadeLogger r39, com.android.systemui.util.kotlin.JavaAdapter r40, java.util.concurrent.Executor r41, com.android.systemui.shade.ShadeSurface r42, com.android.systemui.statusbar.NotificationMediaManager r43, com.android.systemui.statusbar.NotificationLockscreenUserManager r44, com.android.systemui.statusbar.NotificationRemoteInputManager r45, com.android.systemui.shade.QuickSettingsController r46, com.android.systemui.statusbar.policy.BatteryController r47, com.android.systemui.colorextraction.SysuiColorExtractor r48, com.android.systemui.keyguard.ScreenLifecycle r49, com.android.systemui.keyguard.WakefulnessLifecycle r50, com.android.systemui.power.domain.interactor.PowerInteractor r51, com.android.systemui.communal.domain.interactor.CommunalInteractor r52, com.android.systemui.statusbar.SysuiStatusBarStateController r53, java.util.Optional<com.android.wm.shell.bubbles.Bubbles> r54, dagger.Lazy r55, com.android.systemui.statusbar.policy.DeviceProvisionedController r56, com.android.systemui.navigationbar.NavigationBarController r57, com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController r58, dagger.Lazy r59, com.android.systemui.statusbar.policy.ConfigurationController r60, com.android.systemui.statusbar.NotificationShadeWindowController r61, dagger.Lazy r62, com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r63, dagger.Lazy r64, dagger.Lazy r65, com.android.systemui.statusbar.notification.NotificationLaunchAnimatorControllerProvider r66, com.android.systemui.statusbar.phone.DozeParameters r67, com.android.systemui.statusbar.phone.ScrimController r68, dagger.Lazy r69, com.android.systemui.biometrics.AuthRippleController r70, com.android.systemui.statusbar.phone.DozeServiceHost r71, com.android.systemui.back.domain.interactor.BackActionInteractor r72, android.os.PowerManager r73, com.android.systemui.statusbar.phone.DozeScrimController r74, com.android.systemui.volume.VolumeComponent r75, com.android.systemui.statusbar.CommandQueue r76, dagger.Lazy r77, com.android.systemui.plugins.PluginManager r78, com.android.systemui.shade.ShadeController r79, com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor r80, com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager r81, com.android.keyguard.ViewMediatorCallback r82, com.android.systemui.InitController r83, android.os.Handler r84, com.android.systemui.plugins.PluginDependencyProvider r85, com.android.systemui.statusbar.policy.ExtensionController r86, com.android.systemui.statusbar.policy.UserInfoControllerImpl r87, com.android.systemui.statusbar.phone.PhoneStatusBarPolicy r88, com.android.systemui.statusbar.KeyguardIndicationController r89, com.android.systemui.demomode.DemoModeController r90, dagger.Lazy r91, com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager r92, com.android.systemui.statusbar.phone.NotificationIconAreaController r93, com.android.systemui.settings.brightness.BrightnessSliderController.Factory r94, com.android.systemui.statusbar.phone.ScreenOffAnimationController r95, com.android.systemui.util.WallpaperController r96, com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController r97, com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager r98, com.android.systemui.statusbar.LockscreenShadeTransitionController r99, com.android.systemui.flags.FeatureFlags r100, com.android.systemui.keyguard.KeyguardUnlockAnimationController r101, com.android.systemui.util.concurrency.DelayableExecutor r102, com.android.systemui.util.concurrency.MessageRouter r103, android.app.WallpaperManager r104, java.util.Optional<com.android.wm.shell.startingsurface.StartingWindowController.StartingSurfaceImpl> r105, com.android.systemui.animation.ActivityTransitionAnimator r106, android.hardware.devicestate.DeviceStateManager r107, com.android.systemui.charging.WiredChargingRippleController r108, android.service.dreams.IDreamManager r109, dagger.Lazy r110, dagger.Lazy r111, com.android.systemui.statusbar.LightRevealScrim r112, com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor r113, com.android.systemui.settings.UserTracker r114, javax.inject.Provider r115, com.android.systemui.plugins.ActivityStarter r116, com.android.systemui.settings.brightness.domain.interactor.BrightnessMirrorShowingInteractor r117, com.android.systemui.shade.GlanceableHubContainerController r118, com.android.systemui.emergency.EmergencyGestureModule.EmergencyGestureIntentFactory r119, com.android.systemui.blur.SecQpBlurController r120, dagger.Lazy r121, android.os.Handler r122, com.android.systemui.statusbar.notification.row.NotifRemoteViewCache r123, com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection r124, com.android.systemui.keyguard.KeyguardFoldController r125, dagger.Lazy r126, com.android.systemui.aiagent.AiAgentEffect r127) {
        /*
            Method dump skipped, instructions count: 843
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.CentralSurfacesImpl.<init>(com.android.systemui.mdm.MdmOverlayContainer, dagger.Lazy, com.android.systemui.bixby2.SystemUICommandActionHandler, com.android.systemui.keyguard.DisplayLifecycle, com.android.systemui.subscreen.SubScreenManager, android.content.Context, com.android.systemui.statusbar.notification.init.NotificationsController, com.android.systemui.fragments.FragmentService, com.android.systemui.statusbar.phone.LightBarController, com.android.systemui.statusbar.phone.AutoHideController, com.android.systemui.statusbar.core.StatusBarInitializer, com.android.systemui.statusbar.window.StatusBarWindowController, com.android.systemui.statusbar.window.StatusBarWindowStateController, com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore, com.android.keyguard.KeyguardUpdateMonitor, com.android.systemui.statusbar.phone.StatusBarSignalPolicy, com.android.systemui.statusbar.PulseExpansionHandler, com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator, com.android.systemui.statusbar.phone.KeyguardBypassController, com.android.systemui.statusbar.policy.KeyguardStateController, com.android.systemui.statusbar.policy.HeadsUpManager, com.android.systemui.plugins.FalsingManager, com.android.systemui.classifier.FalsingCollector, com.android.systemui.broadcast.BroadcastDispatcher, com.android.systemui.statusbar.notification.row.NotificationGutsManager, com.android.systemui.shade.ShadeExpansionStateManager, com.android.systemui.keyguard.KeyguardViewMediator, android.util.DisplayMetrics, com.android.internal.logging.MetricsLogger, com.android.systemui.shade.ShadeLogger, com.android.systemui.util.kotlin.JavaAdapter, java.util.concurrent.Executor, com.android.systemui.shade.ShadeSurface, com.android.systemui.statusbar.NotificationMediaManager, com.android.systemui.statusbar.NotificationLockscreenUserManager, com.android.systemui.statusbar.NotificationRemoteInputManager, com.android.systemui.shade.QuickSettingsController, com.android.systemui.statusbar.policy.BatteryController, com.android.systemui.colorextraction.SysuiColorExtractor, com.android.systemui.keyguard.ScreenLifecycle, com.android.systemui.keyguard.WakefulnessLifecycle, com.android.systemui.power.domain.interactor.PowerInteractor, com.android.systemui.communal.domain.interactor.CommunalInteractor, com.android.systemui.statusbar.SysuiStatusBarStateController, java.util.Optional, dagger.Lazy, com.android.systemui.statusbar.policy.DeviceProvisionedController, com.android.systemui.navigationbar.NavigationBarController, com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController, dagger.Lazy, com.android.systemui.statusbar.policy.ConfigurationController, com.android.systemui.statusbar.NotificationShadeWindowController, dagger.Lazy, com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController, dagger.Lazy, dagger.Lazy, com.android.systemui.statusbar.notification.NotificationLaunchAnimatorControllerProvider, com.android.systemui.statusbar.phone.DozeParameters, com.android.systemui.statusbar.phone.ScrimController, dagger.Lazy, com.android.systemui.biometrics.AuthRippleController, com.android.systemui.statusbar.phone.DozeServiceHost, com.android.systemui.back.domain.interactor.BackActionInteractor, android.os.PowerManager, com.android.systemui.statusbar.phone.DozeScrimController, com.android.systemui.volume.VolumeComponent, com.android.systemui.statusbar.CommandQueue, dagger.Lazy, com.android.systemui.plugins.PluginManager, com.android.systemui.shade.ShadeController, com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor, com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager, com.android.keyguard.ViewMediatorCallback, com.android.systemui.InitController, android.os.Handler, com.android.systemui.plugins.PluginDependencyProvider, com.android.systemui.statusbar.policy.ExtensionController, com.android.systemui.statusbar.policy.UserInfoControllerImpl, com.android.systemui.statusbar.phone.PhoneStatusBarPolicy, com.android.systemui.statusbar.KeyguardIndicationController, com.android.systemui.demomode.DemoModeController, dagger.Lazy, com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager, com.android.systemui.statusbar.phone.NotificationIconAreaController, com.android.systemui.settings.brightness.BrightnessSliderController$Factory, com.android.systemui.statusbar.phone.ScreenOffAnimationController, com.android.systemui.util.WallpaperController, com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController, com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager, com.android.systemui.statusbar.LockscreenShadeTransitionController, com.android.systemui.flags.FeatureFlags, com.android.systemui.keyguard.KeyguardUnlockAnimationController, com.android.systemui.util.concurrency.DelayableExecutor, com.android.systemui.util.concurrency.MessageRouter, android.app.WallpaperManager, java.util.Optional, com.android.systemui.animation.ActivityTransitionAnimator, android.hardware.devicestate.DeviceStateManager, com.android.systemui.charging.WiredChargingRippleController, android.service.dreams.IDreamManager, dagger.Lazy, dagger.Lazy, com.android.systemui.statusbar.LightRevealScrim, com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor, com.android.systemui.settings.UserTracker, javax.inject.Provider, com.android.systemui.plugins.ActivityStarter, com.android.systemui.settings.brightness.domain.interactor.BrightnessMirrorShowingInteractor, com.android.systemui.shade.GlanceableHubContainerController, com.android.systemui.emergency.EmergencyGestureModule$EmergencyGestureIntentFactory, com.android.systemui.blur.SecQpBlurController, dagger.Lazy, android.os.Handler, com.android.systemui.statusbar.notification.row.NotifRemoteViewCache, com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection, com.android.systemui.keyguard.KeyguardFoldController, dagger.Lazy, com.android.systemui.aiagent.AiAgentEffect):void");
    }

    public final void animateExpandNotificationsPanel(StatusBarNotification statusBarNotification, boolean z) {
        QuickPanelLogger quickPanelLogger;
        StringBuilder sb;
        CommonNotifCollection commonNotifCollection;
        ExpandableNotificationRow expandableNotificationRow;
        ExpandableNotificationRow expandableNotificationRow2 = null;
        if (statusBarNotification != null && (commonNotifCollection = this.mNotifCollection) != null) {
            NotificationEntry entry = ((NotifPipeline) commonNotifCollection).mNotifCollection.getEntry(statusBarNotification.getKey());
            if (entry != null && (expandableNotificationRow = entry.row) != null) {
                expandableNotificationRow2 = expandableNotificationRow;
            }
        }
        boolean z2 = ((StatusBarStateControllerImpl) this.mStatusBarStateController).mState == 1;
        if (z2) {
            if (z && !this.mCommandQueue.panelsEnabled()) {
                return;
            } else {
                this.mLockscreenShadeTransitionController.goToLockedShade(expandableNotificationRow2, true);
            }
        } else if (!this.mShadeController.isExpandedVisible()) {
            this.mCommandQueueCallbacks.animateExpandNotificationsPanel();
        }
        if (expandableNotificationRow2 == null || !expandableNotificationRow2.mIsSummaryWithChildren || (quickPanelLogger = this.mQuickPanelLogger) == null || (sb = this.mQuickPanelLogBuilder) == null) {
            return;
        }
        sb.setLength(0);
        sb.append("animateExpandNotificationsPanel: isKeyguardState: ");
        sb.append(z2);
        quickPanelLogger.logPanelState(sb.toString());
    }

    public final void awakenDreams() {
        this.mUiBgExecutor.execute(new CentralSurfacesImpl$$ExternalSyntheticLambda4(this, 2));
    }

    public final void checkBarModes$1() {
        this.mDemoModeController.getClass();
        if (this.mStatusBarTransitions != null) {
            this.mStatusBarTransitions.transitionTo(((StatusBarMode) ((StatusBarModeRepositoryImpl) this.mStatusBarModeRepository).defaultDisplay.statusBarMode.$$delegate_0.getValue()).toTransitionModeInt(), (this.mNoAnimationOnNextBarModeChange || !this.mDeviceInteractive || this.mStatusBarWindowState == 2) ? false : true);
        }
        ((NavigationBarControllerImpl) this.mNavigationBarController).checkNavBarModes(this.mDisplayId);
        this.mNoAnimationOnNextBarModeChange = false;
    }

    public final void checkRemoteInputRequest(String str, String str2) {
        if (str == null) {
            Log.d("CentralSurfaces", " RemoteInput: extra value is null");
            return;
        }
        if ((this.mCommandQueueCallbacks.mDisabled2 & 4) != 0) {
            Log.d("CentralSurfaces", " RemoteInput: disabled panel ".concat(str));
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
                this.mMainHandler.postDelayed(new CentralSurfacesImpl$$ExternalSyntheticLambda13(this, expandableNotificationRow, str2, 0), 500L);
                return;
            }
        }
        Log.d("CentralSurfaces", " RemoteInput: no remote input for ".concat(str));
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x0257, code lost:
    
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
            Method dump skipped, instructions count: 795
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.CentralSurfacesImpl.dump(java.io.PrintWriter, java.lang.String[]):void");
    }

    public final void finishKeyguardFadingAway() {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        keyguardStateControllerImpl.notifyKeyguardGoingAway(false);
        if (keyguardStateControllerImpl.mKeyguardFadingAway) {
            Trace.traceCounter(4096L, "keyguardFadingAway", 0);
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

    public final boolean isCameraAllowedByAdmin() {
        DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
        NotificationLockscreenUserManager notificationLockscreenUserManager = this.mLockscreenUserManager;
        if (devicePolicyManager.getCameraDisabled(null, ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mCurrentUserId)) {
            return false;
        }
        return (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing && this.mStatusBarKeyguardViewManager.isSecure() && (this.mDevicePolicyManager.getKeyguardDisabledFeatures(null, ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mCurrentUserId) & 2) != 0) ? false : true;
    }

    public final boolean isForegroundComponentName(ComponentName componentName) {
        View decorView;
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        boolean z = ((KeyguardStateControllerImpl) keyguardStateController).mShowing;
        DisplayLifecycle displayLifecycle = this.mDisplayLifecycle;
        boolean z2 = false;
        if (z && !((KeyguardStateControllerImpl) keyguardStateController).mOccluded) {
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
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("Foreground component state :: ", "CentralSurfaces", z2);
        return z2;
    }

    public final boolean isGoingToSleep() {
        return this.mWakefulnessLifecycle.mWakefulness == 3;
    }

    public final void logStateToEventlog() {
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        boolean z = ((KeyguardStateControllerImpl) keyguardStateController).mShowing;
        boolean z2 = ((KeyguardStateControllerImpl) keyguardStateController).mOccluded;
        boolean isBouncerShowing = this.mStatusBarKeyguardViewManager.isBouncerShowing();
        boolean z3 = ((KeyguardStateControllerImpl) keyguardStateController).mSecure;
        boolean z4 = ((KeyguardStateControllerImpl) keyguardStateController).mCanDismissLockScreen;
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

    public final void openQSPanelWithDetail(String str) {
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
            this.mQSPanelController.mCollapseExpandAction.run();
            return;
        }
        this.mShadeSurface.expandQSForOpenDetail();
        this.mMainExecutor.executeDelayed(new CentralSurfacesImpl$$ExternalSyntheticLambda1(0, this, str), 200L);
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
        this.mDeviceStateManager.registerCallback(this.mMainExecutor, new FoldStateListener(this.mContext, new CentralSurfacesImpl$$ExternalSyntheticLambda0(this)));
        this.mJavaAdapter.alwaysCollectFlow(this.mCommunalInteractor.isIdleOnCommunal, this.mIdleOnCommunalConsumer);
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
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
            autoHideController.suspendAutoHide();
        } else {
            autoHideController.resumeSuspendedAutoHide();
        }
        checkBarModes$1();
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
        if (!((StatusBarStateControllerImpl) this.mStatusBarStateController).mIsDozing || !this.mDozeServiceHost.mIgnoreTouchWhilePulsing) {
            List list = this.mScreenOffAnimationController.animations;
            if (!(list instanceof Collection) || !((ArrayList) list).isEmpty()) {
                Iterator it = ((ArrayList) list).iterator();
                while (it.hasNext()) {
                    if (((ScreenOffAnimation) it.next()).isAnimationPlaying()) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public final boolean shouldUseTabletKeyboardShortcuts() {
        return ((FeatureFlagsClassicRelease) this.mFeatureFlags).isEnabled(com.android.systemui.flags.Flags.SHORTCUT_LIST_SEARCH_LAYOUT) && Utilities.isLargeScreen(this.mContext);
    }

    public final void showKeyguard() {
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.mStatusBarStateController;
        statusBarStateControllerImpl.mKeyguardRequested = true;
        statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide = false;
        updateIsKeyguard(false);
        final AssistManager assistManager = (AssistManager) this.mAssistManagerLazy.get();
        assistManager.getClass();
        AsyncTask.execute(new Runnable() { // from class: com.android.systemui.assist.AssistManager.6
            @Override // java.lang.Runnable
            public final void run() {
                AssistManager.this.mAssistUtils.onLockscreenShown();
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v35, types: [android.content.BroadcastReceiver, com.android.systemui.navigationbar.interactor.PackageRemovedInteractor$addCallback$2] */
    /* JADX WARN: Type inference failed for: r10v37, types: [android.content.BroadcastReceiver, com.android.systemui.navigationbar.interactor.SettingsSoftResetInteractor$addCallback$2] */
    /* JADX WARN: Type inference failed for: r10v39, types: [com.android.systemui.navigationbar.interactor.RotationLockInteractor$addCallback$2, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r13v37, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$4] */
    /* JADX WARN: Type inference failed for: r13v38, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$3] */
    /* JADX WARN: Type inference failed for: r13v39, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$2] */
    /* JADX WARN: Type inference failed for: r13v40, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$1] */
    /* JADX WARN: Type inference failed for: r14v12, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$8] */
    /* JADX WARN: Type inference failed for: r14v13, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$7] */
    /* JADX WARN: Type inference failed for: r1v176, types: [com.android.systemui.navigationbar.interactor.KnoxStateMonitorInteractor$addCallback$2] */
    /* JADX WARN: Type inference failed for: r5v37, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$12] */
    /* JADX WARN: Type inference failed for: r5v38, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$11] */
    @Override // com.android.systemui.CoreStartable
    public final void start() {
        RegisterStatusBarResult registerStatusBarResult;
        boolean z;
        final DeviceStateInteractor deviceStateInteractor;
        DeviceStateManager deviceStateManager;
        PackageRemovedInteractor packageRemovedInteractor;
        CoverDisplayWidgetInteractor coverDisplayWidgetInteractor;
        TaskBarInteractor taskBarInteractor;
        int i = 0;
        CommandQueue commandQueue = this.mCommandQueue;
        this.mScreenLifecycle.addObserver(this.mScreenObserver);
        this.mWakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        this.mUiModeManager = (UiModeManager) this.mContext.getSystemService(UiModeManager.class);
        this.mBubblesOptional.ifPresent(new CentralSurfacesImpl$$ExternalSyntheticLambda2(this, 2));
        KeyguardBypassController keyguardBypassController = this.mKeyguardBypassController;
        keyguardBypassController.getClass();
        BuildersKt.launch$default(keyguardBypassController.applicationScope, null, null, new KeyguardBypassController$listenForQsExpandedChange$1(keyguardBypassController, null), 3);
        StatusBarSignalPolicy statusBarSignalPolicy = this.mStatusBarSignalPolicy;
        if (!statusBarSignalPolicy.mInitialized) {
            statusBarSignalPolicy.mInitialized = true;
            statusBarSignalPolicy.mTunerService.addTunable(statusBarSignalPolicy, "icon_blacklist");
            ((NetworkControllerImpl) statusBarSignalPolicy.mNetworkController).addCallback(statusBarSignalPolicy);
            ((SecurityControllerImpl) statusBarSignalPolicy.mSecurityController).addCallback(statusBarSignalPolicy);
            statusBarSignalPolicy.mDesktopManager.setDesktopStatusBarIconCallback(statusBarSignalPolicy.mDesktopStatusBarIconUpdateCallback);
        }
        KeyguardIndicationController keyguardIndicationController = this.mKeyguardIndicationController;
        keyguardIndicationController.init();
        this.mColorExtractor.addOnColorsChangedListener(this.mOnColorsChangedListener);
        Display display = this.mContext.getDisplay();
        this.mDisplay = display;
        this.mDisplayId = display.getDisplayId();
        this.mDisplay.getMetrics(this.mDisplayMetrics);
        this.mDisplay.getSize(this.mCurrentDisplaySize);
        this.mStatusBarHideIconsForBouncerManager.displayId = this.mDisplayId;
        initShadeVisibilityListener();
        RoutineSdkImpl routineSdkImpl = RoutineSdkImpl.LazyHolder.a;
        EdgelightingRoutineActionHandler edgelightingRoutineActionHandler = new EdgelightingRoutineActionHandler();
        routineSdkImpl.getClass();
        com.samsung.android.sdk.routines.v3.internal.Log.b("RoutineSdkImpl", "setActionHandler - tag=edge_lighting_v3, actionHandler=" + edgelightingRoutineActionHandler);
        routineSdkImpl.e.set(edgelightingRoutineActionHandler, "edge_lighting_v3");
        WindowManagerGlobal.getWindowManagerService();
        this.mDevicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        keyguardUpdateMonitor.mKeyguardBypassController = keyguardBypassController;
        this.mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        this.mWallpaperSupported = this.mWallpaperManager.isWallpaperSupported();
        if (BasicRune.NAVBAR_ENABLED) {
            NavBarStore navBarStore = (NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class);
            this.mNavBarStore = navBarStore;
            final NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore;
            ColorSetting colorSetting = (ColorSetting) navBarStoreImpl.interactorFactory.get(ColorSetting.class);
            if (colorSetting != null) {
                colorSetting.addColorCallback(null);
            }
            InteractorFactory interactorFactory = navBarStoreImpl.interactorFactory;
            ButtonOrderInteractor buttonOrderInteractor = (ButtonOrderInteractor) interactorFactory.get(ButtonOrderInteractor.class);
            if (buttonOrderInteractor != 0) {
                buttonOrderInteractor.addCallback(new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnButtonOrderChanged(false, 1, null));
                    }
                });
            }
            ButtonPositionInteractor buttonPositionInteractor = (ButtonPositionInteractor) interactorFactory.get(ButtonPositionInteractor.class);
            if (buttonPositionInteractor != 0) {
                buttonPositionInteractor.addCallback(new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnButtonPositionChanged(false, 1, null));
                    }
                });
            }
            ButtonToHideKeyboardInteractor buttonToHideKeyboardInteractor = (ButtonToHideKeyboardInteractor) interactorFactory.get(ButtonToHideKeyboardInteractor.class);
            if (buttonToHideKeyboardInteractor != 0) {
                buttonToHideKeyboardInteractor.addCallback(new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnButtonToHideKeyboardChanged(false, 1, null));
                    }
                });
            }
            EdgeBackGesturePolicyInteractor edgeBackGesturePolicyInteractor = (EdgeBackGesturePolicyInteractor) interactorFactory.get(EdgeBackGesturePolicyInteractor.class);
            if (edgeBackGesturePolicyInteractor != 0) {
                edgeBackGesturePolicyInteractor.addCallback(new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$4
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnEdgeBackGestureDisabledByPolicyChanged(((Boolean) obj).booleanValue()));
                    }
                });
            }
            GestureNavigationSettingsInteractor gestureNavigationSettingsInteractor = (GestureNavigationSettingsInteractor) interactorFactory.get(GestureNavigationSettingsInteractor.class);
            if (gestureNavigationSettingsInteractor != null) {
                Consumer consumer = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnNavBarButtonForcedVisibleChanged(((Boolean) obj).booleanValue()));
                    }
                };
                Consumer consumer2 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnBottomSensitivityChanged(false, 1, null));
                    }
                };
                gestureNavigationSettingsInteractor.forcedVisibleCallback = consumer;
                gestureNavigationSettingsInteractor.bottomSensitivityCallback = consumer2;
                gestureNavigationSettingsInteractor.observer.register();
            }
            boolean z2 = BasicRune.NAVBAR_TASKBAR;
            if (z2 && (taskBarInteractor = (TaskBarInteractor) interactorFactory.get(TaskBarInteractor.class)) != 0) {
                taskBarInteractor.addCallback(new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$7
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnUpdateTaskbarAvailable(false, 1, null));
                    }
                });
            }
            KeyboardButtonPositionInteractor keyboardButtonPositionInteractor = (KeyboardButtonPositionInteractor) interactorFactory.get(KeyboardButtonPositionInteractor.class);
            if (keyboardButtonPositionInteractor != 0) {
                keyboardButtonPositionInteractor.addCallback(new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnButtonOrderChanged(false, 1, null));
                    }
                });
            }
            KnoxStateMonitorInteractor knoxStateMonitorInteractor = (KnoxStateMonitorInteractor) interactorFactory.get(KnoxStateMonitorInteractor.class);
            if (knoxStateMonitorInteractor != null) {
                final Consumer consumer3 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$9
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnNavBarKnoxPolicyChanged(false, 1, null));
                    }
                };
                final Consumer consumer4 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$10
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnHardKeyIntentPolicyChanged(((Boolean) obj).booleanValue()));
                    }
                };
                KnoxStateMonitorInteractor$addCallback$2 knoxStateMonitorInteractor$addCallback$2 = knoxStateMonitorInteractor.knoxStateMonitorCallback;
                if (knoxStateMonitorInteractor$addCallback$2 != null) {
                    ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).removeCallback(knoxStateMonitorInteractor$addCallback$2);
                }
                knoxStateMonitorInteractor.knoxStateMonitorCallback = new KnoxStateMonitorCallback() { // from class: com.android.systemui.navigationbar.interactor.KnoxStateMonitorInteractor$addCallback$2
                    @Override // com.android.systemui.knox.KnoxStateMonitorCallback
                    public final void onSetHardKeyIntentState(boolean z3) {
                        Consumer consumer5 = consumer4;
                        Intrinsics.checkNotNull(consumer5);
                        consumer5.accept(Boolean.valueOf(z3));
                    }

                    @Override // com.android.systemui.knox.KnoxStateMonitorCallback
                    public final void onUpdateNavigationBarHidden() {
                        Consumer consumer5 = consumer3;
                        Intrinsics.checkNotNull(consumer5);
                        consumer5.accept(Boolean.TRUE);
                    }
                };
                ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).registerCallback(knoxStateMonitorInteractor.knoxStateMonitorCallback);
            }
            OpenThemeInteractor openThemeInteractor = (OpenThemeInteractor) interactorFactory.get(OpenThemeInteractor.class);
            if (openThemeInteractor != 0) {
                openThemeInteractor.addCallback(new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$11
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnOpenThemeChanged(false, 1, null));
                    }
                });
            }
            UseThemeDefaultInteractor useThemeDefaultInteractor = (UseThemeDefaultInteractor) interactorFactory.get(UseThemeDefaultInteractor.class);
            if (useThemeDefaultInteractor != 0) {
                useThemeDefaultInteractor.addCallback(new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$12
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnUseThemeDefaultChanged(false, 1, null));
                    }
                });
            }
            final RotationLockInteractor rotationLockInteractor = (RotationLockInteractor) interactorFactory.get(RotationLockInteractor.class);
            if (rotationLockInteractor != null) {
                final Consumer consumer5 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$13
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnRotationLockedChanged(((Boolean) obj).booleanValue()));
                    }
                };
                RotationLockInteractor$addCallback$2 rotationLockInteractor$addCallback$2 = rotationLockInteractor.rotationLockCallback;
                RotationLockController rotationLockController = rotationLockInteractor.rotationLockController;
                if (rotationLockInteractor$addCallback$2 != null) {
                    rotationLockController.removeCallback(rotationLockInteractor$addCallback$2);
                }
                ?? r10 = new RotationLockController.RotationLockControllerCallback() { // from class: com.android.systemui.navigationbar.interactor.RotationLockInteractor$addCallback$2
                    @Override // com.android.systemui.statusbar.policy.RotationLockController.RotationLockControllerCallback
                    public final void onRotationLockStateChanged(boolean z3, boolean z4) {
                        Consumer consumer6 = consumer5;
                        Intrinsics.checkNotNull(consumer6);
                        consumer6.accept(Boolean.valueOf(rotationLockInteractor.rotationLockController.isRotationLocked()));
                    }
                };
                rotationLockController.addCallback(r10);
                rotationLockInteractor.rotationLockCallback = r10;
            }
            OneHandModeInteractor oneHandModeInteractor = (OneHandModeInteractor) interactorFactory.get(OneHandModeInteractor.class);
            if (oneHandModeInteractor != null) {
                oneHandModeInteractor.addCallback(new NavBarStoreImpl$initInteractor$14(navBarStoreImpl));
            }
            SettingsSoftResetInteractor settingsSoftResetInteractor = (SettingsSoftResetInteractor) interactorFactory.get(SettingsSoftResetInteractor.class);
            if (settingsSoftResetInteractor != null) {
                final Runnable runnable = new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$15
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnSettingsSoftReset(false, 1, null));
                    }
                };
                SettingsSoftResetInteractor$addCallback$2 settingsSoftResetInteractor$addCallback$2 = settingsSoftResetInteractor.broadcastReceiver;
                if (settingsSoftResetInteractor$addCallback$2 != null) {
                    settingsSoftResetInteractor.broadcastDispatcher.unregisterReceiver(settingsSoftResetInteractor$addCallback$2);
                }
                ?? r102 = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.interactor.SettingsSoftResetInteractor$addCallback$2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        Runnable runnable2 = runnable;
                        if (runnable2 != null) {
                            runnable2.run();
                        }
                    }
                };
                BroadcastDispatcher.registerReceiver$default(settingsSoftResetInteractor.broadcastDispatcher, r102, settingsSoftResetInteractor.intentFilter, null, null, 0, null, 60);
                settingsSoftResetInteractor.broadcastReceiver = r102;
            }
            if (BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY && !BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && (coverDisplayWidgetInteractor = (CoverDisplayWidgetInteractor) interactorFactory.get(CoverDisplayWidgetInteractor.class)) != null) {
                coverDisplayWidgetInteractor.addCallback();
            }
            if (BasicRune.NAVBAR_REMOTEVIEW && (packageRemovedInteractor = (PackageRemovedInteractor) interactorFactory.get(PackageRemovedInteractor.class)) != null) {
                final Consumer consumer6 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$16
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnPackageRemoved((String) obj));
                    }
                };
                PackageRemovedInteractor$addCallback$2 packageRemovedInteractor$addCallback$2 = packageRemovedInteractor.broadcastReceiver;
                if (packageRemovedInteractor$addCallback$2 != null) {
                    packageRemovedInteractor.broadcastDispatcher.unregisterReceiver(packageRemovedInteractor$addCallback$2);
                }
                ?? r103 = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.interactor.PackageRemovedInteractor$addCallback$2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        if ("android.intent.action.PACKAGE_REMOVED".equals(intent.getAction())) {
                            Uri data = intent.getData();
                            Intrinsics.checkNotNull(data);
                            String encodedSchemeSpecificPart = data.getEncodedSchemeSpecificPart();
                            Consumer consumer7 = consumer6;
                            if (consumer7 != null) {
                                consumer7.accept(encodedSchemeSpecificPart);
                            }
                        }
                    }
                };
                packageRemovedInteractor.broadcastReceiver = r103;
                BroadcastDispatcher.registerReceiver$default(packageRemovedInteractor.broadcastDispatcher, r103, packageRemovedInteractor.intentFilter, null, ((UserTrackerImpl) packageRemovedInteractor.userTracker).getUserHandle(), 0, null, 48);
            }
            if ((BasicRune.BASIC_FOLDABLE_TYPE_FOLD || BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN || BasicRune.NAVBAR_MULTI_MODAL_ICON) && (deviceStateInteractor = (DeviceStateInteractor) interactorFactory.get(DeviceStateInteractor.class)) != null) {
                final Consumer consumer7 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$17
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnFoldStateChanged(((Boolean) obj).booleanValue()));
                        final NavBarStoreImpl navBarStoreImpl3 = NavBarStoreImpl.this;
                        navBarStoreImpl3.handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$17.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                NavBarStateManager navBarStateManager = (NavBarStateManager) NavBarStoreImpl.this.navStateManager.get(1);
                                if (navBarStateManager != null) {
                                    NavBarStoreImpl navBarStoreImpl4 = NavBarStoreImpl.this;
                                    navBarStoreImpl4.handleEvent(navBarStoreImpl4, new EventTypeFactory.EventType.OnBarLayoutParamsProviderChanged(new NavBarCoverLayoutParams((Context) navBarStoreImpl4.getModule(Context.class, 1), navBarStateManager)), 1);
                                }
                            }
                        });
                    }
                };
                final Consumer consumer8 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$18
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        if (NavBarStoreImpl.this.navStateManager.get(1) != null) {
                            NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                            navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnNavBarLargeCoverScreenVisibilityChanged(false, ((Boolean) obj).booleanValue()), 1);
                        }
                    }
                };
                Consumer consumer9 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$19
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        boolean z3 = false;
                        NavBarStateManager navBarStateManager = (NavBarStateManager) NavBarStoreImpl.this.navStateManager.get(0);
                        if (navBarStateManager != null) {
                            int intValue = ((Integer) obj).intValue();
                            NavBarStates navBarStates = ((NavBarStateManagerImpl) navBarStateManager).states;
                            int i2 = 1;
                            boolean z4 = intValue != navBarStates.lastTaskUserId;
                            navBarStates.lastTaskUserId = intValue;
                            if (z4) {
                                NavBarStoreImpl.this.logWrapper.printLog(0, "Force update provided inset because Task's userId was changed. userId=" + ((Integer) obj));
                                ((NavigationBar) NavBarStoreImpl.this.getModule(NavigationBar.class, 0)).updateNavBarLayoutParams();
                                if (BasicRune.NAVBAR_MULTI_MODAL_ICON) {
                                    NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                                    navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnButtonOrderChanged(z3, i2, null));
                                }
                            }
                        }
                    }
                };
                Consumer consumer10 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$20
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStateManager navBarStateManager = (NavBarStateManager) NavBarStoreImpl.this.navStateManager.get(1);
                        if (navBarStateManager != null) {
                            NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                            if (((NavBarStateManagerImpl) navBarStateManager).isGestureMode()) {
                                navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnCoverRotationChanged(((Number) obj).intValue()), 1);
                            }
                        }
                    }
                };
                DeviceStateManager.FoldStateListener foldStateListener = deviceStateInteractor.foldStateListener;
                if (foldStateListener != null && (deviceStateManager = deviceStateInteractor.deviceStateManager) != null) {
                    deviceStateManager.unregisterCallback(foldStateListener);
                }
                deviceStateInteractor.foldStateListener = new DeviceStateManager.FoldStateListener(deviceStateInteractor.context, new Consumer() { // from class: com.android.systemui.navigationbar.interactor.DeviceStateInteractor$addCallback$2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        Boolean bool = (Boolean) obj;
                        DeviceStateInteractor deviceStateInteractor2 = DeviceStateInteractor.this;
                        Intrinsics.checkNotNull(bool);
                        deviceStateInteractor2.foldCache = bool.booleanValue();
                        DeviceStateInteractor deviceStateInteractor3 = DeviceStateInteractor.this;
                        Consumer consumer11 = consumer7;
                        if (consumer11 != null) {
                            consumer11.accept(Boolean.valueOf(deviceStateInteractor3.foldCache));
                        } else {
                            deviceStateInteractor3.getClass();
                        }
                        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
                            DeviceStateInteractor deviceStateInteractor4 = DeviceStateInteractor.this;
                            Consumer consumer12 = consumer8;
                            deviceStateInteractor4.getClass();
                            NavigationBarController navigationBarController = (NavigationBarController) Dependency.sDependency.getDependencyInner(NavigationBarController.class);
                            if (navigationBarController != null) {
                                boolean z3 = deviceStateInteractor4.foldCache;
                                DeviceStateInteractor$displayListener$1 deviceStateInteractor$displayListener$1 = deviceStateInteractor4.displayListener;
                                DeviceStateInteractor$componentCallbacks$1 deviceStateInteractor$componentCallbacks$1 = deviceStateInteractor4.componentCallbacks;
                                if (z3) {
                                    ((NavigationBarControllerImpl) navigationBarController).mCommandQueueCallbacks.onDisplayReady(1);
                                    deviceStateInteractor4.displayManager.registerDisplayListener(deviceStateInteractor$displayListener$1, deviceStateInteractor4.handler);
                                    deviceStateInteractor4.coverTask = deviceStateInteractor4.new CoverTask(consumer12);
                                    ActivityTaskManager.getService().registerTaskStackListener(deviceStateInteractor4.coverTask);
                                    Context context = deviceStateInteractor4.windowContext;
                                    if (context != null) {
                                        context.registerComponentCallbacks(deviceStateInteractor$componentCallbacks$1);
                                        return;
                                    }
                                    return;
                                }
                                NavigationBarControllerImpl navigationBarControllerImpl = (NavigationBarControllerImpl) navigationBarController;
                                navigationBarControllerImpl.removeNavigationBar(1);
                                navigationBarControllerImpl.mCommandQueueCallbacks.onDisplayRemoved(1);
                                deviceStateInteractor4.displayManager.unregisterDisplayListener(deviceStateInteractor$displayListener$1);
                                deviceStateInteractor4.lastRotation = -1;
                                deviceStateInteractor4.lastCoverRotation = 0;
                                deviceStateInteractor4.coverTaskCache = false;
                                ActivityTaskManager.getService().unregisterTaskStackListener(deviceStateInteractor4.coverTask);
                                Context context2 = deviceStateInteractor4.windowContext;
                                if (context2 != null) {
                                    context2.unregisterComponentCallbacks(deviceStateInteractor$componentCallbacks$1);
                                }
                            }
                        }
                    }
                });
                if (BasicRune.NAVBAR_MULTI_MODAL_ICON) {
                    DeviceStateInteractor.MultimodalTask multimodalTask = deviceStateInteractor.multimodalTask;
                    if (multimodalTask != null) {
                        ActivityTaskManager.getService().unregisterTaskStackListener(multimodalTask);
                    }
                    deviceStateInteractor.multimodalTask = deviceStateInteractor.new MultimodalTask(consumer9);
                    ActivityTaskManager.getService().registerTaskStackListener(deviceStateInteractor.multimodalTask);
                }
                DeviceStateManager deviceStateManager2 = deviceStateInteractor.deviceStateManager;
                if (deviceStateManager2 != null) {
                    Executor mainExecutor = deviceStateInteractor.context.getMainExecutor();
                    DeviceStateManager.FoldStateListener foldStateListener2 = deviceStateInteractor.foldStateListener;
                    Intrinsics.checkNotNull(foldStateListener2);
                    deviceStateManager2.registerCallback(mainExecutor, foldStateListener2);
                }
                deviceStateInteractor.largeCoverRotationCallback = consumer10;
            }
            NavigationModeInteractor navigationModeInteractor = (NavigationModeInteractor) interactorFactory.get(NavigationModeInteractor.class);
            if (navigationModeInteractor != null) {
                navigationModeInteractor.addCallback(new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$21
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((NavigationModeController) Dependency.sDependency.getDependencyInner(NavigationModeController.class)).updateCurrentInteractionMode(true);
                    }
                });
            }
            navBarStoreImpl.packs = ((BandAidPackFactory) navBarStoreImpl.bandAidPackFactory).getPacks(navBarStoreImpl);
            if (z2) {
                TaskbarDelegate taskbarDelegate = (TaskbarDelegate) Dependency.sDependency.getDependencyInner(TaskbarDelegate.class);
                navBarStoreImpl.taskbarDelegate = taskbarDelegate;
                Intrinsics.checkNotNull(taskbarDelegate);
                navBarStoreImpl.putModule(TaskbarDelegate.class, taskbarDelegate, 0);
                TaskbarDelegate taskbarDelegate2 = navBarStoreImpl.taskbarDelegate;
                if (taskbarDelegate2 != null) {
                    ((SecTaskBarManagerImpl) taskbarDelegate2).navBarRemoteViewManager = navBarStoreImpl.navBarRemoteViewManager;
                }
            }
        }
        try {
            registerStatusBarResult = this.mBarService.registerStatusBar(commandQueue);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            registerStatusBarResult = null;
        }
        this.mDisplay.getMetrics(this.mDisplayMetrics);
        this.mDisplay.getSize(this.mCurrentDisplaySize);
        updateResources$1();
        updateTheme();
        NotificationShadeWindowController notificationShadeWindowController = this.mNotificationShadeWindowController;
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) notificationShadeWindowController;
        WindowRootView windowRootView = notificationShadeWindowControllerImpl.mWindowRootViewComponentFactory.create().getWindowRootView();
        notificationShadeWindowControllerImpl.mWindowRootView = windowRootView;
        Lazy lazy = notificationShadeWindowControllerImpl.mShadeInteractorLazy;
        JavaAdapterKt.collectFlow(windowRootView, ((ShadeInteractorImpl) ((ShadeInteractor) lazy.get())).baseShadeInteractor.isAnyExpanded(), new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda11(notificationShadeWindowControllerImpl, 2));
        JavaAdapterKt.collectFlow(notificationShadeWindowControllerImpl.mWindowRootView, ((ShadeInteractorImpl) ((ShadeInteractor) lazy.get())).baseShadeInteractor.isQsExpanded(), new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda11(notificationShadeWindowControllerImpl, 3));
        JavaAdapterKt.collectFlow(notificationShadeWindowControllerImpl.mWindowRootView, ((CommunalInteractor) notificationShadeWindowControllerImpl.mCommunalInteractor.get()).isCommunalVisible, new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda11(notificationShadeWindowControllerImpl, 4));
        Rune.runIf((Runnable) new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda3(notificationShadeWindowControllerImpl, 1), true);
        final NotificationShadeWindowViewController notificationShadeWindowViewController = getNotificationShadeWindowViewController();
        NotificationShadeWindowView notificationShadeWindowView = notificationShadeWindowViewController.mView;
        notificationShadeWindowViewController.mStackScrollLayout = (NotificationStackScrollLayout) notificationShadeWindowView.findViewById(R.id.notification_stack_scroller);
        com.android.systemui.flags.Flags flags = com.android.systemui.flags.Flags.INSTANCE;
        notificationShadeWindowViewController.mFeatureFlagsClassic.getClass();
        notificationShadeWindowView.layoutInsetsController = notificationShadeWindowViewController.mNotificationInsetsController;
        notificationShadeWindowView.mInteractionEventHandler = notificationShadeWindowViewController.new AnonymousClass1();
        notificationShadeWindowView.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() { // from class: com.android.systemui.shade.NotificationShadeWindowViewController.2
            @Override // android.view.ViewGroup.OnHierarchyChangeListener
            public final void onChildViewAdded(View view, View view2) {
                if (view2.getId() == R.id.brightness_mirror_container) {
                    NotificationShadeWindowViewController.this.mBrightnessMirror = view2;
                }
            }

            @Override // android.view.ViewGroup.OnHierarchyChangeListener
            public final void onChildViewRemoved(View view, View view2) {
            }
        });
        notificationShadeWindowViewController.setDragDownHelper(notificationShadeWindowViewController.mLockscreenShadeTransitionController.touchHelper);
        NotificationShadeDepthController notificationShadeDepthController = notificationShadeWindowViewController.mDepthController;
        notificationShadeDepthController.root = notificationShadeWindowView;
        notificationShadeWindowViewController.mShadeExpansionStateManager.addExpansionListener(notificationShadeDepthController);
        NotificationShadeWindowViewController notificationShadeWindowViewController2 = getNotificationShadeWindowViewController();
        notificationShadeWindowViewController2.getClass();
        int i2 = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        GlanceableHubContainerController glanceableHubContainerController = notificationShadeWindowViewController2.mGlanceableHubContainerController;
        glanceableHubContainerController.getClass();
        BooleanFlowOperators booleanFlowOperators = BooleanFlowOperators.INSTANCE;
        CommunalInteractor communalInteractor = glanceableHubContainerController.communalInteractor;
        JavaAdapterKt.collectFlow(notificationShadeWindowViewController2.mView, booleanFlowOperators.anyOf(communalInteractor.isCommunalAvailable, communalInteractor.editModeOpen), new NotificationShadeWindowViewController$$ExternalSyntheticLambda5(notificationShadeWindowViewController2, i));
        NotificationShadeWindowViewController notificationShadeWindowViewController3 = getNotificationShadeWindowViewController();
        ShadeController shadeController = this.mShadeController;
        shadeController.setNotificationShadeWindowViewController(notificationShadeWindowViewController3);
        QuickSettingsController quickSettingsController = this.mQsController;
        BackActionInteractor backActionInteractor = this.mBackActionInteractor;
        backActionInteractor.qsController = quickSettingsController;
        ShadeSurface shadeSurface = this.mShadeSurface;
        backActionInteractor.shadeBackActionInteractor = shadeSurface;
        getNotificationShadeWindowViewController().mView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda35
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                centralSurfacesImpl.mAutoHideController.checkUserAutoHide(motionEvent);
                NotificationRemoteInputManager notificationRemoteInputManager = centralSurfacesImpl.mRemoteInputManager;
                notificationRemoteInputManager.getClass();
                if (motionEvent.getAction() == 4 && motionEvent.getX() == 0.0f && motionEvent.getY() == 0.0f && notificationRemoteInputManager.isRemoteInputActive()) {
                    notificationRemoteInputManager.closeRemoteInputs(false);
                }
                MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
                Flags.migrateClocksToBlueprint();
                centralSurfacesImpl.mShadeController.onStatusBarTouch(motionEvent);
                return centralSurfacesImpl.getNotificationShadeWindowViewController().mView.onTouchEvent(motionEvent);
            }
        });
        this.mWallpaperController.setRootView(getNotificationShadeWindowViewController().mView);
        this.mDemoModeController.addCallback((DemoMode) this.mDemoModeCallback);
        StatusBarModeRepositoryImpl statusBarModeRepositoryImpl = (StatusBarModeRepositoryImpl) this.mStatusBarModeRepository;
        this.mJavaAdapter.alwaysCollectFlow(statusBarModeRepositoryImpl.defaultDisplay.statusBarMode, new CentralSurfacesImpl$$ExternalSyntheticLambda2(this, 4));
        CentralSurfacesCommandQueueCallbacks centralSurfacesCommandQueueCallbacks = (CentralSurfacesCommandQueueCallbacks) this.mCommandQueueCallbacksLazy.get();
        this.mCommandQueueCallbacks = centralSurfacesCommandQueueCallbacks;
        commandQueue.addCallback((CommandQueue.Callbacks) centralSurfacesCommandQueueCallbacks);
        ShadeExpansionStateManager shadeExpansionStateManager = this.mShadeExpansionStateManager;
        NotificationWakeUpCoordinator notificationWakeUpCoordinator = this.mWakeUpCoordinator;
        notificationWakeUpCoordinator.onPanelExpansionChanged(shadeExpansionStateManager.addExpansionListener(notificationWakeUpCoordinator));
        PluginDependencyProvider pluginDependencyProvider = this.mPluginDependencyProvider;
        pluginDependencyProvider.allowPluginDependency(DarkIconDispatcher.class);
        pluginDependencyProvider.allowPluginDependency(StatusBarStateController.class);
        CentralSurfacesImpl$$ExternalSyntheticLambda0 centralSurfacesImpl$$ExternalSyntheticLambda0 = new CentralSurfacesImpl$$ExternalSyntheticLambda0(this);
        final StatusBarInitializer statusBarInitializer = this.mStatusBarInitializer;
        statusBarInitializer.statusBarViewUpdatedListener = centralSurfacesImpl$$ExternalSyntheticLambda0;
        StatusBarWindowController statusBarWindowController = statusBarInitializer.windowController;
        FragmentHostManager fragmentHostManager = statusBarWindowController.mFragmentService.getFragmentHostManager(statusBarWindowController.mStatusBarWindowView);
        fragmentHostManager.addTagListener("CollapsedStatusBarFragment", new FragmentHostManager.FragmentListener() { // from class: com.android.systemui.statusbar.core.StatusBarInitializer$initializeStatusBar$1
            @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
            public final void onFragmentViewCreated(Fragment fragment) {
                StatusBarFragmentComponent statusBarFragmentComponent = ((CollapsedStatusBarFragment) fragment).mStatusBarFragmentComponent;
                if (statusBarFragmentComponent == null) {
                    throw new IllegalStateException();
                }
                StatusBarInitializer statusBarInitializer2 = StatusBarInitializer.this;
                CentralSurfacesImpl$$ExternalSyntheticLambda0 centralSurfacesImpl$$ExternalSyntheticLambda02 = statusBarInitializer2.statusBarViewUpdatedListener;
                if (centralSurfacesImpl$$ExternalSyntheticLambda02 != null) {
                    statusBarFragmentComponent.getPhoneStatusBarView();
                    PhoneStatusBarViewController phoneStatusBarViewController = statusBarFragmentComponent.getPhoneStatusBarViewController();
                    PhoneStatusBarTransitions phoneStatusBarTransitions = statusBarFragmentComponent.getPhoneStatusBarTransitions();
                    CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfacesImpl$$ExternalSyntheticLambda02.f$0;
                    centralSurfacesImpl.mPhoneStatusBarViewController = phoneStatusBarViewController;
                    centralSurfacesImpl.mStatusBarTransitions = phoneStatusBarTransitions;
                    centralSurfacesImpl.getNotificationShadeWindowViewController().mStatusBarViewController = centralSurfacesImpl.mPhoneStatusBarViewController;
                    ShadeSurface shadeSurface2 = centralSurfacesImpl.mShadeSurface;
                    shadeSurface2.updateExpansionAndVisibility();
                    boolean z3 = centralSurfacesImpl.mBouncerShowing;
                    int i3 = z3 ? 4 : 0;
                    PhoneStatusBarViewController phoneStatusBarViewController2 = centralSurfacesImpl.mPhoneStatusBarViewController;
                    if (phoneStatusBarViewController2 != null) {
                        phoneStatusBarViewController2.setImportantForAccessibility(i3);
                    }
                    shadeSurface2.setImportantForAccessibility(i3);
                    shadeSurface2.setBouncerShowing(z3);
                    centralSurfacesImpl.checkBarModes$1();
                }
                Iterator it = statusBarInitializer2.creationListeners.iterator();
                while (it.hasNext()) {
                    ((StatusBarInitializer.OnStatusBarViewInitializedListener) it.next()).onStatusBarViewInitialized(statusBarFragmentComponent);
                }
            }

            @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
            public final void onFragmentViewDestroyed(Fragment fragment) {
            }
        });
        fragmentHostManager.mFragments.getFragmentManager().beginTransaction().replace(R.id.status_bar_container, (Fragment) statusBarInitializer.collapsedStatusBarFragmentProvider.get(), "CollapsedStatusBarFragment").commit();
        NotificationShadeWindowView notificationShadeWindowView2 = getNotificationShadeWindowViewController().mView;
        StatusBarTouchableRegionManager statusBarTouchableRegionManager = this.mStatusBarTouchableRegionManager;
        statusBarTouchableRegionManager.mNotificationShadeWindowView = notificationShadeWindowView2;
        statusBarTouchableRegionManager.mNotificationPanelView = notificationShadeWindowView2.findViewById(R.id.notification_panel);
        NavigationBarControllerImpl navigationBarControllerImpl = (NavigationBarControllerImpl) this.mNavigationBarController;
        navigationBarControllerImpl.updateAccessibilityButtonModeIfNeeded();
        boolean z3 = BasicRune.NAVBAR_POLICY_VISIBILITY || !navigationBarControllerImpl.initializeTaskbarIfNecessary();
        DisplayTracker displayTracker = navigationBarControllerImpl.mDisplayTracker;
        Display[] displays = ((DisplayTrackerImpl) displayTracker).displayManager.getDisplays();
        int length = displays.length;
        while (i < length) {
            int i3 = length;
            Display display2 = displays[i];
            if (!z3) {
                int displayId = display2.getDisplayId();
                displayTracker.getClass();
                if (displayId == 0) {
                    z = z3;
                    i++;
                    length = i3;
                    z3 = z;
                }
            }
            z = z3;
            navigationBarControllerImpl.createNavigationBar(display2, null, registerStatusBarResult);
            i++;
            length = i3;
            z3 = z;
        }
        keyguardIndicationController.setUpperTextView((KeyguardIndicationTextView) getNotificationShadeWindowViewController().mView.findViewById(R.id.keyguard_upper_fingerprint_indication));
        this.mAmbientIndicationContainer = getNotificationShadeWindowViewController().mView.findViewById(R.id.ambient_indication_container);
        this.mAutoHideController.mStatusBar = new AutoHideUiElement() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.5
            @Override // com.android.systemui.statusbar.AutoHideUiElement
            public final void hide() {
                StatusBarModePerDisplayRepositoryImpl statusBarModePerDisplayRepositoryImpl = ((StatusBarModeRepositoryImpl) CentralSurfacesImpl.this.mStatusBarModeRepository).defaultDisplay;
                statusBarModePerDisplayRepositoryImpl.getClass();
                statusBarModePerDisplayRepositoryImpl._isTransientShown.updateState(null, Boolean.FALSE);
            }

            @Override // com.android.systemui.statusbar.AutoHideUiElement
            public final boolean isVisible() {
                return ((Boolean) ((StatusBarModeRepositoryImpl) CentralSurfacesImpl.this.mStatusBarModeRepository).defaultDisplay.isTransientShown.$$delegate_0.getValue()).booleanValue();
            }

            @Override // com.android.systemui.statusbar.AutoHideUiElement
            public final boolean shouldHideOnTouch() {
                return !CentralSurfacesImpl.this.mRemoteInputManager.isRemoteInputActive();
            }

            @Override // com.android.systemui.statusbar.AutoHideUiElement
            public final void synchronizeState() {
                CentralSurfacesImpl.this.checkBarModes$1();
            }
        };
        ScrimView scrimView = (ScrimView) getNotificationShadeWindowViewController().mView.findViewById(R.id.scrim_behind);
        ScrimView scrimView2 = (ScrimView) getNotificationShadeWindowViewController().mView.findViewById(R.id.scrim_notifications);
        ScrimView scrimView3 = (ScrimView) getNotificationShadeWindowViewController().mView.findViewById(R.id.scrim_in_front);
        CentralSurfacesImpl$$ExternalSyntheticLambda2 centralSurfacesImpl$$ExternalSyntheticLambda2 = new CentralSurfacesImpl$$ExternalSyntheticLambda2(this, 5);
        final ScrimController scrimController = this.mScrimController;
        scrimController.mScrimVisibleListener = centralSurfacesImpl$$ExternalSyntheticLambda2;
        ((KeyguardFastBioUnlockController) Dependency.sDependency.getDependencyInner(KeyguardFastBioUnlockController.class)).scrimUpdater = new CentralSurfacesImpl$$ExternalSyntheticLambda4(this, 3);
        scrimController.mNotificationsScrim = scrimView2;
        scrimController.mScrimBehind = scrimView;
        scrimController.mScrimInFront = scrimView3;
        final SecLsScrimControlHelper secLsScrimControlHelper = scrimController.mSecLsScrimControlHelper;
        final int i4 = 0;
        final int i5 = 1;
        final int i6 = 2;
        final int i7 = 3;
        SecLsScrimControlProvider secLsScrimControlProvider = new SecLsScrimControlProvider(new Supplier() { // from class: com.android.systemui.statusbar.phone.ScrimController$$ExternalSyntheticLambda11
            @Override // java.util.function.Supplier
            public final Object get() {
                int i8 = i4;
                ScrimController scrimController2 = scrimController;
                switch (i8) {
                    case 0:
                        return scrimController2.mNotificationsScrim;
                    case 1:
                        return scrimController2.mScrimBehind;
                    case 2:
                        return scrimController2.mScrimInFront;
                    default:
                        return Boolean.valueOf(scrimController2.mKeyguardOccluded);
                }
            }
        }, new Supplier() { // from class: com.android.systemui.statusbar.phone.ScrimController$$ExternalSyntheticLambda11
            @Override // java.util.function.Supplier
            public final Object get() {
                int i8 = i5;
                ScrimController scrimController2 = scrimController;
                switch (i8) {
                    case 0:
                        return scrimController2.mNotificationsScrim;
                    case 1:
                        return scrimController2.mScrimBehind;
                    case 2:
                        return scrimController2.mScrimInFront;
                    default:
                        return Boolean.valueOf(scrimController2.mKeyguardOccluded);
                }
            }
        }, new Supplier() { // from class: com.android.systemui.statusbar.phone.ScrimController$$ExternalSyntheticLambda11
            @Override // java.util.function.Supplier
            public final Object get() {
                int i8 = i6;
                ScrimController scrimController2 = scrimController;
                switch (i8) {
                    case 0:
                        return scrimController2.mNotificationsScrim;
                    case 1:
                        return scrimController2.mScrimBehind;
                    case 2:
                        return scrimController2.mScrimInFront;
                    default:
                        return Boolean.valueOf(scrimController2.mKeyguardOccluded);
                }
            }
        }, new Supplier() { // from class: com.android.systemui.statusbar.phone.ScrimController$$ExternalSyntheticLambda11
            @Override // java.util.function.Supplier
            public final Object get() {
                int i8 = i7;
                ScrimController scrimController2 = scrimController;
                switch (i8) {
                    case 0:
                        return scrimController2.mNotificationsScrim;
                    case 1:
                        return scrimController2.mScrimBehind;
                    case 2:
                        return scrimController2.mScrimInFront;
                    default:
                        return Boolean.valueOf(scrimController2.mKeyguardOccluded);
                }
            }
        }, new ScrimController$$ExternalSyntheticLambda0(scrimController, 1), new ScrimController$$ExternalSyntheticLambda3(scrimController, 2));
        secLsScrimControlHelper.mProvider = secLsScrimControlProvider;
        secLsScrimControlHelper.mScrimInFront = (ScrimView) secLsScrimControlProvider.mFrontScrimSupplier.get();
        secLsScrimControlHelper.mScrimBehind = (ScrimView) secLsScrimControlProvider.mBehindScrimSupplier.get();
        if (LsRune.SECURITY_OPEN_THEME) {
            WallpaperUtils.registerSystemUIWidgetCallback(secLsScrimControlHelper, 512L);
        }
        secLsScrimControlHelper.mKeyguardUpdateMonitor.registerCallback(new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.SecLsScrimControlHelper.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onLockModeChanged() {
                if (LsRune.SECURITY_CAPTURED_BLUR && DeviceState.isCapturedBlurAllowed()) {
                    SecLsScrimControlHelper secLsScrimControlHelper2 = SecLsScrimControlHelper.this;
                    boolean isFingerprintOptionEnabled = secLsScrimControlHelper2.mKeyguardUpdateMonitor.isFingerprintOptionEnabled();
                    if (secLsScrimControlHelper2.mIsFingerprintOptionEnabled != isFingerprintOptionEnabled) {
                        secLsScrimControlHelper2.mIsFingerprintOptionEnabled = isFingerprintOptionEnabled;
                        secLsScrimControlHelper2.setScrimAlphaForKeyguard(true);
                    }
                }
            }
        });
        ((KeyguardStateControllerImpl) secLsScrimControlHelper.mKeyguardStateController).addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.phone.SecLsScrimControlHelper.2
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onPrimaryBouncerShowingChanged() {
                SecLsScrimControlHelper secLsScrimControlHelper2 = SecLsScrimControlHelper.this;
                if (secLsScrimControlHelper2.mState != ScrimState.UNLOCKED || ((KeyguardStateControllerImpl) secLsScrimControlHelper2.mKeyguardStateController).mPrimaryBouncerShowing) {
                    return;
                }
                ScrimView scrimView4 = secLsScrimControlHelper2.mScrimInFront;
                if (scrimView4.mViewAlpha != 0.0f) {
                    scrimView4.setViewAlpha(0.0f);
                    secLsScrimControlHelper2.mProvider.mUpdateScrimsRunnable.run();
                }
            }
        });
        scrimController.updateThemeColors();
        ScrimView scrimView4 = scrimController.mNotificationsScrim;
        scrimView4.setScrimName(scrimController.getScrimName(scrimView4));
        ScrimView scrimView5 = scrimController.mScrimBehind;
        scrimView5.setScrimName(scrimController.getScrimName(scrimView5));
        ScrimView scrimView6 = scrimController.mScrimInFront;
        scrimView6.setScrimName(scrimController.getScrimName(scrimView6));
        Drawable drawable = scrimView.mDrawable;
        if (drawable instanceof ScrimDrawable) {
            ScrimDrawable scrimDrawable = (ScrimDrawable) drawable;
            scrimDrawable.mConcaveInfo = null;
            scrimDrawable.invalidateSelf();
        }
        Drawable drawable2 = scrimController.mNotificationsScrim.mDrawable;
        if (drawable2 instanceof ScrimDrawable) {
            ScrimDrawable scrimDrawable2 = (ScrimDrawable) drawable2;
            if (!scrimDrawable2.mCornerRadiusEnabled) {
                scrimDrawable2.mCornerRadiusEnabled = true;
                scrimDrawable2.invalidateSelf();
            }
        }
        ScrimState[] values = ScrimState.values();
        int i8 = 0;
        while (i8 < values.length) {
            ScrimState scrimState = values[i8];
            ScrimView scrimView7 = scrimController.mScrimInFront;
            ScrimView scrimView8 = scrimController.mScrimBehind;
            DozeParameters dozeParameters = scrimController.mDozeParameters;
            DockManager dockManager = scrimController.mDockManager;
            AODAmbientWallpaperHelper aODAmbientWallpaperHelper = scrimController.mAODAmbientWallpaperHelper;
            scrimState.getClass();
            RegisterStatusBarResult registerStatusBarResult2 = registerStatusBarResult;
            scrimState.mBackgroundColor = scrimView8.getContext().getColor(R.color.shade_scrim_background_dark);
            scrimState.mScrimInFront = scrimView7;
            scrimState.mScrimBehind = scrimView8;
            scrimState.mDozeParameters = dozeParameters;
            scrimState.mDockManager = dockManager;
            scrimState.mDisplayRequiresBlanking = dozeParameters.getDisplayNeedsBlanking();
            if (LsRune.AOD_FULLSCREEN) {
                scrimState.mAODAmbientWallpaperHelper = aODAmbientWallpaperHelper;
            }
            ScrimState scrimState2 = values[i8];
            scrimState2.mScrimBehindAlphaKeyguard = scrimController.mScrimBehindAlphaKeyguard;
            scrimState2.mDefaultScrimAlpha = scrimController.mDefaultScrimAlpha;
            i8++;
            registerStatusBarResult = registerStatusBarResult2;
        }
        RegisterStatusBarResult registerStatusBarResult3 = registerStatusBarResult;
        scrimController.mScrimColorState = new ScrimStateLogger(scrimController.mScrimInFront, scrimController.mNotificationsScrim, scrimController.mScrimBehind, scrimController.new AnonymousClass3());
        scrimController.mTransparentScrimBackground = scrimView2.getResources().getBoolean(R.bool.notification_scrim_transparent);
        scrimController.updateScrims();
        scrimController.mKeyguardUpdateMonitor.registerCallback(scrimController.mKeyguardVisibilityCallback);
        for (ScrimState scrimState3 : ScrimState.values()) {
            scrimState3.prepare(scrimState3);
        }
        int i9 = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        scrimController.mBouncerToGoneTransition = new ScrimController$$ExternalSyntheticLambda3(scrimController, 3);
        KeyguardTransitionInteractor keyguardTransitionInteractor = scrimController.mKeyguardTransitionInteractor;
        Edge.Companion companion = Edge.Companion;
        KeyguardState keyguardState = KeyguardState.PRIMARY_BOUNCER;
        KeyguardState keyguardState2 = KeyguardState.GONE;
        companion.getClass();
        JavaAdapterKt.collectFlow(scrimView, keyguardTransitionInteractor.transition((Edge) new Edge.StateToState(keyguardState, keyguardState2)), scrimController.mBouncerToGoneTransition, scrimController.mMainDispatcher);
        JavaAdapterKt.collectFlow(scrimView, scrimController.mPrimaryBouncerToGoneTransitionViewModel.scrimAlpha, scrimController.mScrimAlphaConsumer, scrimController.mMainDispatcher);
        KeyguardTransitionInteractor keyguardTransitionInteractor2 = scrimController.mKeyguardTransitionInteractor;
        KeyguardState keyguardState3 = KeyguardState.ALTERNATE_BOUNCER;
        new Edge.StateToScene(keyguardState3, Scenes.Gone);
        JavaAdapterKt.collectFlow(scrimView, keyguardTransitionInteractor2.transition(new Edge.StateToState(keyguardState3, keyguardState2)), scrimController.mBouncerToGoneTransition, scrimController.mMainDispatcher);
        JavaAdapterKt.collectFlow(scrimView, scrimController.mAlternateBouncerToGoneTransitionViewModel.scrimAlpha, scrimController.mScrimAlphaConsumer, scrimController.mMainDispatcher);
        KeyguardTransitionInteractor keyguardTransitionInteractor3 = scrimController.mKeyguardTransitionInteractor;
        KeyguardState keyguardState4 = KeyguardState.LOCKSCREEN;
        SceneKey sceneKey = Scenes.Communal;
        new Edge.StateToScene(keyguardState4, sceneKey);
        KeyguardState keyguardState5 = KeyguardState.GLANCEABLE_HUB;
        JavaAdapterKt.collectFlow(scrimView, keyguardTransitionInteractor3.transition(new Edge.StateToState(keyguardState4, keyguardState5)), scrimController.mGlanceableHubConsumer, scrimController.mMainDispatcher);
        KeyguardTransitionInteractor keyguardTransitionInteractor4 = scrimController.mKeyguardTransitionInteractor;
        new Edge.SceneToState(sceneKey, keyguardState4);
        JavaAdapterKt.collectFlow(scrimView, keyguardTransitionInteractor4.transition(new Edge.StateToState(keyguardState5, keyguardState4)), scrimController.mGlanceableHubConsumer, scrimController.mMainDispatcher);
        Flags.lightRevealMigration();
        CentralSurfacesImpl$$ExternalSyntheticLambda2 centralSurfacesImpl$$ExternalSyntheticLambda22 = new CentralSurfacesImpl$$ExternalSyntheticLambda2(this, 6);
        LightRevealScrim lightRevealScrim = this.mLightRevealScrim;
        lightRevealScrim.isScrimOpaqueChangedListener = centralSurfacesImpl$$ExternalSyntheticLambda22;
        shadeSurface.setUserSetupComplete$1(this.mUserSetup);
        ScreenOffAnimationController screenOffAnimationController = this.mScreenOffAnimationController;
        Iterator it = ((ArrayList) screenOffAnimationController.animations).iterator();
        while (it.hasNext()) {
            ((ScreenOffAnimation) it.next()).initialize(this, shadeSurface, lightRevealScrim);
        }
        screenOffAnimationController.wakefulnessLifecycle.addObserver(screenOffAnimationController);
        updateLightRevealScrimVisibility();
        Flags.sceneContainer();
        shadeSurface.initDependencies(this, new CentralSurfacesImpl$$ExternalSyntheticLambda29(shadeController, 0), this.mHeadsUpManager);
        View findViewById = getNotificationShadeWindowViewController().mView.findViewById(R.id.qs_frame);
        if (findViewById != null) {
            Flags.sceneContainer();
            FragmentService fragmentService = this.mFragmentService;
            FragmentHostManager fragmentHostManager2 = fragmentService.getFragmentHostManager(findViewById);
            ExtensionControllerImpl extensionControllerImpl = (ExtensionControllerImpl) this.mExtensionController;
            extensionControllerImpl.getClass();
            ExtensionControllerImpl.ExtensionBuilder extensionBuilder = new ExtensionControllerImpl.ExtensionBuilder(extensionControllerImpl, 0);
            extensionBuilder.withPlugin(QS.class);
            Supplier supplier = new Supplier() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda30
                @Override // java.util.function.Supplier
                public final Object get() {
                    CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                    FragmentHostManager fragmentHostManager3 = centralSurfacesImpl.mFragmentService.getFragmentHostManager(centralSurfacesImpl.getNotificationShadeWindowViewController().mView);
                    return (QS) fragmentHostManager3.mPlugins.instantiate(fragmentHostManager3.mContext, QSFragmentLegacy.class.getName(), null);
                }
            };
            ExtensionControllerImpl.ExtensionImpl extensionImpl = extensionBuilder.mExtension;
            extensionImpl.mProducers.add(new ExtensionControllerImpl.ExtensionImpl.Default(extensionImpl, supplier));
            ExtensionFragmentListener.attachExtensonToFragment(fragmentService, findViewById, extensionBuilder.build());
            this.mBrightnessMirrorController = new BrightnessMirrorController(getNotificationShadeWindowViewController().mView, this.mShadeSurface, (NotificationShadeDepthController) this.mNotificationShadeDepthControllerLazy.get(), this.mBrightnessSliderFactory, new CentralSurfacesImpl$$ExternalSyntheticLambda2(this, 7));
            fragmentHostManager2.addTagListener(QS.TAG, new FragmentHostManager.FragmentListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda32
                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
                public final void onFragmentViewCreated(Fragment fragment) {
                    CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                    centralSurfacesImpl.getClass();
                    QS qs = (QS) fragment;
                    if (qs instanceof QSFragmentLegacy) {
                        QSImpl qSImpl = ((QSFragmentLegacy) qs).mQsImpl;
                        centralSurfacesImpl.mQSPanelController = qSImpl != null ? qSImpl.mQSPanelController : null;
                        centralSurfacesImpl.mQuickQSPanelController = qSImpl != null ? qSImpl.mQuickQSPanelController : null;
                    }
                }
            });
        }
        View findViewById2 = getNotificationShadeWindowViewController().mView.findViewById(R.id.report_rejected_touch);
        this.mReportRejectedTouch = findViewById2;
        if (findViewById2 != null) {
            updateReportRejectedTouchVisibility();
            this.mReportRejectedTouch.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda33
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                    Uri reportRejectedTouch = centralSurfacesImpl.mFalsingManager.reportRejectedTouch();
                    if (reportRejectedTouch == null) {
                        return;
                    }
                    StringWriter stringWriter = new StringWriter();
                    stringWriter.write("Build info: ");
                    stringWriter.write(SystemProperties.get("ro.build.description"));
                    stringWriter.write("\nSerial number: ");
                    stringWriter.write(SystemProperties.get("ro.serialno"));
                    stringWriter.write("\n");
                    centralSurfacesImpl.mActivityStarter.startActivityDismissingKeyguard(Intent.createChooser(new Intent("android.intent.action.SEND").setType("*/*").putExtra("android.intent.extra.SUBJECT", "Rejected touch report").putExtra("android.intent.extra.STREAM", reportRejectedTouch).putExtra("android.intent.extra.TEXT", stringWriter.toString()), "Share rejected touch report").addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE), true, true);
                }
            });
        }
        if (!this.mPowerManager.isInteractive()) {
            this.mBroadcastReceiver.onReceive(this.mContext, new Intent("android.intent.action.SCREEN_OFF"));
        }
        this.mGestureWakeLock = this.mPowerManager.newWakeLock(10, "sysui:GestureWakeLock");
        registerBroadcastReceiver();
        AnonymousClass16 anonymousClass16 = this.mUserSetupObserver;
        ((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).addCallback(anonymousClass16);
        anonymousClass16.onUserSetupChanged();
        ThreadedRenderer.overrideProperty("disableProfileBars", "true");
        ThreadedRenderer.overrideProperty("ambientRatio", String.valueOf(1.5f));
        this.mBroadcastDispatcher.registerReceiver(this.mRemoteInputActionBroadcastReceiver, new IntentFilter("com.samsung.systemui.action.REQUEST_REMOTE_INPUT"), null, UserHandle.ALL);
        SecQpBlurController secQpBlurController = this.mBlurController;
        if (secQpBlurController != null) {
            secQpBlurController.mRoot = getNotificationShadeWindowViewController().mView;
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2040, -2138832824, -3);
        notificationShadeWindowControllerImpl.mLp = layoutParams;
        layoutParams.token = new Binder();
        WindowManager.LayoutParams layoutParams2 = notificationShadeWindowControllerImpl.mLp;
        layoutParams2.gravity = 48;
        layoutParams2.setFitInsetsTypes(0);
        notificationShadeWindowControllerImpl.mLp.setTitle("NotificationShade");
        notificationShadeWindowControllerImpl.mLp.packageName = notificationShadeWindowControllerImpl.mContext.getPackageName();
        WindowManager.LayoutParams layoutParams3 = notificationShadeWindowControllerImpl.mLp;
        layoutParams3.layoutInDisplayCutoutMode = 3;
        layoutParams3.privateFlags |= 512;
        int i10 = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
        if (notificationShadeWindowControllerImpl.mIndicatorCutoutUtil.isUDCModel) {
            notificationShadeWindowControllerImpl.mLp.semAddExtensionFlags(8192);
        }
        notificationShadeWindowControllerImpl.mWindowManager.addView(notificationShadeWindowControllerImpl.mWindowRootView, notificationShadeWindowControllerImpl.mLp);
        if (notificationShadeWindowControllerImpl.mWindowRootView.getWindowInsetsController() != null) {
            notificationShadeWindowControllerImpl.mWindowRootView.getWindowInsetsController().setSystemBarsBehavior(2);
        }
        notificationShadeWindowControllerImpl.mLpChanged.copyFrom(notificationShadeWindowControllerImpl.mLp);
        notificationShadeWindowControllerImpl.onThemeChanged();
        int i11 = 1;
        if (notificationShadeWindowControllerImpl.mKeyguardViewMediator.isShowingAndNotOccluded()) {
            notificationShadeWindowControllerImpl.setKeyguardShowing(true);
        }
        NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda11 notificationShadeWindowControllerImpl$$ExternalSyntheticLambda11 = new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda11(notificationShadeWindowControllerImpl, i11);
        final SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = notificationShadeWindowControllerImpl.mHelper;
        PluginLockMediator pluginLockMediator = secNotificationShadeWindowControllerHelperImpl.pluginLockMediator;
        if (pluginLockMediator != null) {
            pluginLockMediator.onRootViewAttached(secNotificationShadeWindowControllerHelperImpl.notificationShadeView);
        }
        if (pluginLockMediator != null) {
            pluginLockMediator.setNoSensorConsumer(new Consumer() { // from class: com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$attach$1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Boolean bool = (Boolean) obj;
                    SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl2 = SecNotificationShadeWindowControllerHelperImpl.this;
                    Intrinsics.checkNotNull(bool);
                    SecNotificationShadeWindowControllerHelperImpl.access$setScreenOrientation(secNotificationShadeWindowControllerHelperImpl2, bool.booleanValue());
                }
            });
        }
        Consumer consumer11 = new Consumer() { // from class: com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$attach$2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Boolean bool = (Boolean) obj;
                SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl2 = SecNotificationShadeWindowControllerHelperImpl.this;
                Intrinsics.checkNotNull(bool);
                SecNotificationShadeWindowControllerHelperImpl.access$setScreenOrientation(secNotificationShadeWindowControllerHelperImpl2, bool.booleanValue());
            }
        };
        KeyguardWallpaperController keyguardWallpaperController = (KeyguardWallpaperController) secNotificationShadeWindowControllerHelperImpl.keyguardWallpaper;
        keyguardWallpaperController.getClass();
        Log.d("KeyguardWallpaperController", "setNoSensorConsumer() consumer:" + consumer11);
        keyguardWallpaperController.mNoSensorConsumer = consumer11;
        keyguardWallpaperController.mMainHandler.post(new KeyguardWallpaperController$$ExternalSyntheticLambda1(keyguardWallpaperController, 2));
        Log.d("KeyguardWallpaperController", "setWideColorGamutConsumer() consumer:" + notificationShadeWindowControllerImpl$$ExternalSyntheticLambda11);
        final StatusBarWindowController statusBarWindowController2 = this.mStatusBarWindowController;
        statusBarWindowController2.getClass();
        Trace.beginSection("StatusBarWindowController.getBarLayoutParams");
        WindowManager.LayoutParams barLayoutParamsForRotation = statusBarWindowController2.getBarLayoutParamsForRotation(statusBarWindowController2.mContext.getDisplay().getRotation());
        barLayoutParamsForRotation.paramsForRotation = new WindowManager.LayoutParams[4];
        for (int i12 = 0; i12 <= 3; i12++) {
            barLayoutParamsForRotation.paramsForRotation[i12] = statusBarWindowController2.getBarLayoutParamsForRotation(i12);
        }
        statusBarWindowController2.mLp = barLayoutParamsForRotation;
        Trace.endSection();
        statusBarWindowController2.mWindowManager.addView(statusBarWindowController2.mStatusBarWindowView, statusBarWindowController2.mLp);
        statusBarWindowController2.mLpChanged.copyFrom(statusBarWindowController2.mLp);
        statusBarWindowController2.mContentInsetsProvider.listeners.add(new StatusBarContentInsetsChangedListener() { // from class: com.android.systemui.statusbar.window.StatusBarWindowController$$ExternalSyntheticLambda2
            @Override // com.android.systemui.statusbar.phone.StatusBarContentInsetsChangedListener
            public final void onStatusBarContentInsetsChanged() {
                StatusBarWindowController.this.calculateStatusBarLocationsForAllRotations();
            }
        });
        statusBarWindowController2.calculateStatusBarLocationsForAllRotations();
        statusBarWindowController2.mIsAttached = true;
        statusBarWindowController2.apply(statusBarWindowController2.mCurrentState);
        AnonymousClass20 anonymousClass20 = this.mActivityTransitionAnimatorCallback;
        ActivityTransitionAnimator activityTransitionAnimator = this.mActivityTransitionAnimator;
        activityTransitionAnimator.callback = anonymousClass20;
        activityTransitionAnimator.listeners.add(this.mActivityTransitionAnimatorListener);
        NotificationRemoteInputManager notificationRemoteInputManager = this.mRemoteInputManager;
        notificationRemoteInputManager.addControllerCallback(notificationShadeWindowController);
        Lazy lazy2 = this.mNotificationActivityStarterLazy;
        NotificationActivityStarter notificationActivityStarter = (NotificationActivityStarter) lazy2.get();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mStackScrollerController;
        notificationStackScrollLayoutController.mNotificationActivityStarter = notificationActivityStarter;
        this.mGutsManager.mNotificationActivityStarter = (NotificationActivityStarter) lazy2.get();
        Lazy lazy3 = this.mPresenterLazy;
        ((BaseShadeControllerImpl) shadeController).notifPresenter = (NotificationPresenter) lazy3.get();
        NotificationPresenter notificationPresenter = (NotificationPresenter) lazy3.get();
        NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl = this.mNotifListContainer;
        NotificationStackScrollLayoutController.NotifStackControllerImpl notifStackControllerImpl = notificationStackScrollLayoutController.mNotifStackController;
        NotificationActivityStarter notificationActivityStarter2 = (NotificationActivityStarter) lazy2.get();
        NotificationsController notificationsController = this.mNotificationsController;
        notificationsController.initialize(notificationPresenter, notificationListContainerImpl, notifStackControllerImpl, notificationActivityStarter2);
        NotificationPresenter notificationPresenter2 = (NotificationPresenter) lazy3.get();
        WindowRootViewVisibilityInteractor windowRootViewVisibilityInteractor = this.mWindowRootViewVisibilityInteractor;
        windowRootViewVisibilityInteractor.notificationPresenter = notificationPresenter2;
        windowRootViewVisibilityInteractor.notificationsController = notificationsController;
        if (NotiRune.NOTI_SUBSCREEN_ALL) {
            SubscreenNotificationController subscreenNotificationController = (SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class);
            this.mSubscreenNotificationController = subscreenNotificationController;
            NotificationActivityStarter notificationActivityStarter3 = (NotificationActivityStarter) lazy2.get();
            SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationController.mDeviceModel;
            if (subscreenDeviceModelParent != null) {
                subscreenDeviceModelParent.mNotificationActivityStarter = notificationActivityStarter3;
            }
        }
        if ((registerStatusBarResult3.mTransientBarTypes & WindowInsets.Type.statusBars()) != 0) {
            StatusBarModePerDisplayRepositoryImpl statusBarModePerDisplayRepositoryImpl = statusBarModeRepositoryImpl.defaultDisplay;
            statusBarModePerDisplayRepositoryImpl.getClass();
            CentralSurfaces centralSurfaces = (CentralSurfaces) Dependency.sDependency.getDependencyInner(CentralSurfaces.class);
            if (centralSurfaces != null) {
                ((CentralSurfacesImpl) centralSurfaces).mNoAnimationOnNextBarModeChange = true;
            }
            statusBarModePerDisplayRepositoryImpl._isTransientShown.updateState(null, Boolean.TRUE);
        }
        this.mCommandQueueCallbacks.getClass();
        this.mCommandQueueCallbacks.getClass();
        int size = registerStatusBarResult3.mIcons.size();
        for (int i13 = 0; i13 < size; i13++) {
            commandQueue.setIcon((String) registerStatusBarResult3.mIcons.keyAt(i13), (StatusBarIcon) registerStatusBarResult3.mIcons.valueAt(i13));
        }
        this.mContext.registerReceiver(this.mBannerActionBroadcastReceiver, KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("com.android.systemui.statusbar.banner_action_cancel", "com.android.systemui.statusbar.banner_action_setup"), "com.android.systemui.permission.SELF", null, 2);
        if (this.mWallpaperSupported) {
            try {
                IWallpaperManager.Stub.asInterface(ServiceManager.getService("wallpaper")).setInAmbientMode(false, 0L);
            } catch (RemoteException unused) {
            }
        }
        this.mIconPolicy.init();
        KeyguardStateController.Callback callback = new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.2
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardGoingAwayChanged() {
                Flags.lightRevealMigration();
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                if (((KeyguardStateControllerImpl) centralSurfacesImpl.mKeyguardStateController).mKeyguardGoingAway) {
                    return;
                }
                LightRevealScrim lightRevealScrim2 = centralSurfacesImpl.mLightRevealScrim;
                if (lightRevealScrim2.revealAmount != 1.0f) {
                    Log.e("CentralSurfaces", "Keyguard is done going away, but someone left the light reveal scrim at reveal amount: " + lightRevealScrim2.revealAmount);
                }
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onUnlockedChanged() {
                UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
                CentralSurfacesImpl.this.logStateToEventlog();
            }
        };
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        keyguardStateControllerImpl.addCallback(callback);
        Trace.beginSection("CentralSurfaces#startKeyguard");
        AnonymousClass18 anonymousClass18 = this.mStateListener;
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.mStatusBarStateController;
        synchronized (statusBarStateControllerImpl.mListeners) {
            statusBarStateControllerImpl.addListenerInternalLocked(anonymousClass18, 0);
        }
        BiometricUnlockController biometricUnlockController = (BiometricUnlockController) this.mBiometricUnlockControllerLazy.get();
        this.mBiometricUnlockController = biometricUnlockController;
        ((HashSet) biometricUnlockController.mBiometricUnlockEventsListeners).add(new BiometricUnlockController.BiometricUnlockEventsListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.6
            @Override // com.android.systemui.statusbar.phone.BiometricUnlockController.BiometricUnlockEventsListener
            public final void onModeChanged(int i14) {
                if (i14 == 1 || i14 == 2 || i14 == 6) {
                    setWakeAndUnlocking(true);
                }
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                centralSurfacesImpl.mDozeServiceHost.updateDozing();
                if (centralSurfacesImpl.mBiometricUnlockController.mMode == 7) {
                    return;
                }
                centralSurfacesImpl.updateScrimController();
            }

            @Override // com.android.systemui.statusbar.phone.BiometricUnlockController.BiometricUnlockEventsListener
            public final void onResetMode() {
                setWakeAndUnlocking(false);
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                centralSurfacesImpl.mDozeServiceHost.updateDozing();
                if (centralSurfacesImpl.mBiometricUnlockController.mMode == 7) {
                    return;
                }
                centralSurfacesImpl.updateScrimController();
            }

            public final void setWakeAndUnlocking(boolean z4) {
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                if (centralSurfacesImpl.getNavigationBarView() != null) {
                    NavigationBarView navigationBarView = centralSurfacesImpl.getNavigationBarView();
                    WindowManager.LayoutParams layoutParams4 = (WindowManager.LayoutParams) ((ViewGroup) navigationBarView.getParent()).getLayoutParams();
                    if (layoutParams4 != null) {
                        boolean z5 = layoutParams4.windowAnimations != 0;
                        if (!z5 && z4) {
                            layoutParams4.windowAnimations = R.style.Animation_NavigationBarFadeIn;
                        } else if (z5 && !z4) {
                            layoutParams4.windowAnimations = 0;
                        }
                        WindowManager windowManager = (WindowManager) navigationBarView.getContext().getSystemService(WindowManager.class);
                        if (BasicRune.NAVBAR_ENABLED) {
                            layoutParams4.setTitle("NavigationBarView");
                        }
                        windowManager.updateViewLayout((View) navigationBarView.getParent(), layoutParams4);
                    }
                    navigationBarView.mWakeAndUnlocking = z4;
                    navigationBarView.updateLayoutTransitionsEnabled();
                }
            }
        });
        this.mSecLockIconView = (ViewGroup) getNotificationShadeWindowViewController().mView.findViewById(R.id.sec_lock_icon_view);
        this.mKeyguardViewMediator.registerCentralSurfaces$1(this, this.mShadeSurface, this.mShadeExpansionStateManager, this.mBiometricUnlockController, this.mStackScroller);
        keyguardStateControllerImpl.addCallback(this.mKeyguardStateControllerCallback);
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager;
        keyguardIndicationController.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        BiometricUnlockController biometricUnlockController2 = this.mBiometricUnlockController;
        biometricUnlockController2.mKeyguardViewController = statusBarKeyguardViewManager;
        if (!SafeUIState.isSysUiSafeModeEnabled()) {
            biometricUnlockController2.mUpdateMonitor.registerPreCallback(biometricUnlockController2);
        }
        notificationRemoteInputManager.addControllerCallback(statusBarKeyguardViewManager);
        this.mLightBarController.mBiometricUnlockController = this.mBiometricUnlockController;
        if (LsRune.SECURITY_DEFAULT_LANDSCAPE) {
            DeviceState.setLandscapeDefaultRotation();
        }
        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
            DeviceState.setInDisplayFingerprintSensorPosition(this.mContext.getResources().getDisplayMetrics());
        }
        Trace.endSection();
        keyguardUpdateMonitor.registerCallback(this.mUpdateCallback);
        DozeServiceHost dozeServiceHost = this.mDozeServiceHost;
        NotificationShadeWindowViewController notificationShadeWindowViewController4 = getNotificationShadeWindowViewController();
        View view = this.mAmbientIndicationContainer;
        dozeServiceHost.mCentralSurfaces = this;
        dozeServiceHost.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        dozeServiceHost.mNotificationShadeWindowViewController = notificationShadeWindowViewController4;
        dozeServiceHost.mNotificationPanelViewController = shadeSurface;
        dozeServiceHost.mAmbientIndicationContainer = view;
        updateLightRevealScrimVisibility();
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        LifecycleRegistry lifecycleRegistry = this.mLifecycle;
        this.mBatteryController.observe(lifecycleRegistry, this.mBatteryStateChangeCallback);
        lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
        this.mAccessibilityFloatingMenuController.init();
        final int i14 = registerStatusBarResult3.mDisabledFlags1;
        final int i15 = registerStatusBarResult3.mDisabledFlags2;
        Runnable runnable2 = new Runnable() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                int i16 = i14;
                int i17 = i15;
                centralSurfacesImpl.getClass();
                int[] iArr = new int[2];
                try {
                    iArr = centralSurfacesImpl.mBarService.getDisableFlags((IBinder) null, -1);
                    Log.d("CentralSurfaces", "QUICK_RELOAD_DISABLE_FLAGS_ON_INIT sec(" + iArr[0] + "," + iArr[1] + ") ged(" + i16 + "," + i17 + ")");
                } catch (RemoteException unused2) {
                    Log.e("CentralSurfaces", "addPostInitTask failed by mBarService.getDisableFlags");
                    iArr[0] = i16;
                    iArr[1] = i17;
                }
                centralSurfacesImpl.mCommandQueue.disable(centralSurfacesImpl.mDisplayId, iArr[0], iArr[1], false);
                try {
                    Binder binder = new Binder();
                    centralSurfacesImpl.mBarService.disable(QuickStepContract.SYSUI_STATE_DEVICE_DOZING, binder, centralSurfacesImpl.mContext.getPackageName());
                    centralSurfacesImpl.mBarService.disable(0, binder, centralSurfacesImpl.mContext.getPackageName());
                } catch (RemoteException e2) {
                    e2.rethrowFromSystemServer();
                }
            }
        };
        InitController initController = this.mInitController;
        if (initController.mTasksExecuted) {
            throw new IllegalStateException("post init tasks have already been executed!");
        }
        initController.mTasks.add(runnable2);
        registerCallbacks();
        this.mFalsingManager.addFalsingBeliefListener(this.mFalsingBeliefListener);
        this.mPluginManager.addPluginListener((PluginListener) new AnonymousClass3(), OverlayPlugin.class, true);
        this.mStartingSurfaceOptional.ifPresent(new CentralSurfacesImpl$$ExternalSyntheticLambda2(this, 3));
        final MdmOverlayContainer mdmOverlayContainer = this.mMdmOverlayContainer;
        mdmOverlayContainer.mStatusBar = this;
        mdmOverlayContainer.mView = (FrameLayout) getNotificationShadeWindowViewController().mView.findViewById(R.id.keyguard_mdm_overlay_container);
        ((StatusBarStateControllerImpl) ((SysuiStatusBarStateController) mdmOverlayContainer.mStatusBarStateControllerLazy.get())).addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.mdm.MdmOverlayContainer.1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i16) {
                MdmOverlayContainer mdmOverlayContainer2 = MdmOverlayContainer.this;
                if (mdmOverlayContainer2.mPreviousState == 2 && i16 == 1) {
                    mdmOverlayContainer2.updateMdmPolicy();
                }
                mdmOverlayContainer2.mPreviousState = i16;
            }
        });
        this.mContext.sendBroadcast(new Intent("com.samsung.systemui.statusbar.STARTED"));
        if (LsRune.AOD_LIGHT_REVEAL) {
            final SecLightRevealScrimHelper secLightRevealScrimHelper = this.mSecLightRevealScrimHelper;
            final int i16 = 0;
            final Function0 function0 = new Function0(this) { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda21
                public final /* synthetic */ CentralSurfacesImpl f$0;

                {
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    switch (i16) {
                        case 0:
                            return this.f$0.mCurrentDisplaySize;
                        default:
                            return Integer.valueOf(this.f$0.mDisplay.getRotation());
                    }
                }
            };
            final int i17 = 1;
            final Function0 function02 = new Function0(this) { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda21
                public final /* synthetic */ CentralSurfacesImpl f$0;

                {
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    switch (i17) {
                        case 0:
                            return this.f$0.mCurrentDisplaySize;
                        default:
                            return Integer.valueOf(this.f$0.mDisplay.getRotation());
                    }
                }
            };
            ((SemWindowManager) secLightRevealScrimHelper.semWindowManager$delegate.getValue()).getInitialDisplaySize(secLightRevealScrimHelper.physicalDisplaySize);
            Log.d("SecLightRevealScrimHelper", "start physicalDisplaySize=" + secLightRevealScrimHelper.physicalDisplaySize);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.samsung.android.app.aodservice.intent.action.CHANGE_AOD_MODE");
            BroadcastDispatcher.registerReceiver$default(secLightRevealScrimHelper.broadcastDispatcher, new BroadcastReceiver() { // from class: com.android.systemui.statusbar.SecLightRevealScrimHelper$start$broadcastReceiver$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    SecLightRevealScrimHelper secLightRevealScrimHelper2 = SecLightRevealScrimHelper.this;
                    Point point = (Point) function0.invoke();
                    int intValue = ((Number) function02.invoke()).intValue();
                    secLightRevealScrimHelper2.getClass();
                    if (intent.getIntExtra("info", -1) != 11) {
                        Log.d("SecLightRevealScrimHelper", "updateDoubleTap no double tap");
                        return;
                    }
                    Point point2 = secLightRevealScrimHelper2.physicalDisplaySize;
                    float min = Math.min(point2.x, point2.y) / Math.min(point.x, point.y);
                    float[] floatArrayExtra = intent.getFloatArrayExtra("location");
                    if (floatArrayExtra != null && floatArrayExtra.length == 2) {
                        if (intValue == 0) {
                            secLightRevealScrimHelper2.secRevealDoubleTapX = floatArrayExtra[0] / min;
                            secLightRevealScrimHelper2.secRevealDoubleTapY = floatArrayExtra[1] / min;
                        } else if (intValue == 1) {
                            secLightRevealScrimHelper2.secRevealDoubleTapX = floatArrayExtra[1] / min;
                            secLightRevealScrimHelper2.secRevealDoubleTapY = point.y - (floatArrayExtra[0] / min);
                        } else if (intValue == 2) {
                            secLightRevealScrimHelper2.secRevealDoubleTapX = point.x - (floatArrayExtra[0] / min);
                            secLightRevealScrimHelper2.secRevealDoubleTapY = point.y - (floatArrayExtra[1] / min);
                        } else if (intValue == 3) {
                            secLightRevealScrimHelper2.secRevealDoubleTapX = point.x - (floatArrayExtra[1] / min);
                            secLightRevealScrimHelper2.secRevealDoubleTapY = floatArrayExtra[0] / min;
                        }
                    }
                    SecLightRevealScrimHelper.SecCircleReveal secCircleReveal = secLightRevealScrimHelper2.secCircleReveal;
                    if (secCircleReveal != null) {
                        secCircleReveal.centerX = secLightRevealScrimHelper2.secRevealDoubleTapX;
                        secCircleReveal.centerY = secLightRevealScrimHelper2.secRevealDoubleTapY;
                    }
                    float f = secLightRevealScrimHelper2.secRevealDoubleTapX;
                    float f2 = secLightRevealScrimHelper2.secRevealDoubleTapY;
                    int i18 = point.x;
                    int i19 = point.y;
                    Point point3 = secLightRevealScrimHelper2.physicalDisplaySize;
                    int i20 = point3.x;
                    int i21 = point3.y;
                    StringBuilder m = CubicBezierEasing$$ExternalSyntheticOutline0.m("updateDoubleTap: secRevealDoubleTapX=", f, " secRevealDoubleTapY=", f2, " currentDisplaySize.x=");
                    AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i18, " currentDisplaySize.y=", i19, " initialDisplaySize.x=");
                    AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i20, " initialDisplaySize.y=", i21, " screenSizeRatio=");
                    m.append(min);
                    m.append(" rotation=");
                    m.append(intValue);
                    Log.i("SecLightRevealScrimHelper", m.toString());
                }
            }, intentFilter, null, UserHandle.ALL, 0, null, 48);
        }
    }

    public final void updateBubblesVisibility() {
        final StatusBarMode statusBarMode = (StatusBarMode) ((StatusBarModeRepositoryImpl) this.mStatusBarModeRepository).defaultDisplay.statusBarMode.$$delegate_0.getValue();
        this.mBubblesOptional.ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                boolean z;
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                StatusBarMode statusBarMode2 = statusBarMode;
                Bubbles bubbles = (Bubbles) obj;
                centralSurfacesImpl.getClass();
                if (statusBarMode2 == StatusBarMode.LIGHTS_OUT || statusBarMode2 == StatusBarMode.LIGHTS_OUT_TRANSPARENT || centralSurfacesImpl.mStatusBarWindowState == 2) {
                    NavBarHelper navBarHelper = (NavBarHelper) centralSurfacesImpl.mNavBarHelperLazy.get();
                    navBarHelper.getClass();
                    if (new NavBarHelper.CurrentSysuiState(navBarHelper).mWindowState == 2) {
                        z = false;
                        BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubbles;
                        ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda10(bubblesImpl, z, 2));
                    }
                }
                z = true;
                BubbleController.BubblesImpl bubblesImpl2 = (BubbleController.BubblesImpl) bubbles;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda10(bubblesImpl2, z, 2));
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
        boolean z2;
        StringBuilder sb;
        int i;
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
        Lazy lazy = this.mCameraLauncherLazy;
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
            if (keyguardStateControllerImpl.mLaunchTransitionFadingAway) {
                shadeSurface.cancelAnimation();
                shadeSurface.resetAlpha();
                ((CameraLauncher) lazy.get()).mKeyguardBypassController.launchingAffordance = false;
                releaseGestureWakeLock();
                keyguardStateControllerImpl.mLaunchTransitionFadingAway = false;
                keyguardStateControllerImpl.invokeForEachCallback(new KeyguardStateControllerImpl$$ExternalSyntheticLambda0(6));
            }
            messageRouter.cancelMessages(1003);
            if (lockscreenShadeTransitionController.isWakingToShadeLocked) {
                i = 1;
            } else {
                i = 1;
                sysuiStatusBarStateController.setState(1);
            }
            if (statusBarStateControllerImpl.mState == i && this.mQsController.getExpanded$1()) {
                shadeSurface.resetViews(false);
            }
            mdmOverlayContainer.updateMdmPolicy();
            updatePanelExpansionForKeyguard();
            Trace.endSection();
            return false;
        }
        StringBuilder sb2 = new StringBuilder("!shouldBeKeyguard mStatusBarStateController.isKeyguardRequested() ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb2, statusBarStateControllerImpl.mKeyguardRequested, " keyguardForDozing ", z5, " wakeAndUnlocking ");
        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(sb2, isWakeAndUnlock, " isWakingAndOccluded ", z6, "CentralSurfaces");
        List list = screenOffAnimationController.animations;
        if (!(list instanceof Collection) || !((ArrayList) list).isEmpty()) {
            Iterator it = ((ArrayList) list).iterator();
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
        int i3 = statusBarStateControllerImpl.mState;
        if (!statusBarStateControllerImpl.setState(0, z)) {
            ((NotificationLockscreenUserManagerImpl) this.mLockscreenUserManager).updatePublicMode();
            mdmOverlayContainer.updateMdmPolicy();
        }
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (quickPanelLogger != null && (sb = this.mQuickPanelLogBuilder) != null) {
            sb.setLength(0);
            sb.append("hideKeyguardImpl: mStatusBarStateController.leaveOpenOnKeyguardHide(): ");
            sb.append(statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide);
            sb.append(", !mShadeSurface.isCollapsing(): ");
            sb.append(!shadeSurface.isCollapsing());
            quickPanelLogger.logPanelState(sb.toString());
        }
        if (statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide) {
            if (!statusBarStateControllerImpl.mKeyguardRequested) {
                MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
                Flags.migrateClocksToBlueprint();
                statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide = false;
            }
            long j = keyguardStateControllerImpl.mKeyguardFadingAwayDelay + keyguardStateControllerImpl.mKeyguardFadingAwayDuration;
            LSShadeTransitionLogger lSShadeTransitionLogger = lockscreenShadeTransitionController.logger;
            lSShadeTransitionLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            LSShadeTransitionLogger$logOnHideKeyguard$2 lSShadeTransitionLogger$logOnHideKeyguard$2 = new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logOnHideKeyguard$2
                @Override // kotlin.jvm.functions.Function1
                public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    return "Notified that the keyguard is being hidden";
                }
            };
            LogBuffer logBuffer = lSShadeTransitionLogger.buffer;
            logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$logOnHideKeyguard$2, null));
            Function1 function1 = lockscreenShadeTransitionController.animationHandlerOnKeyguardDismiss;
            if (function1 != null) {
                function1.invoke(Long.valueOf(j));
                lockscreenShadeTransitionController.animationHandlerOnKeyguardDismiss = null;
                z2 = false;
            } else if (lockscreenShadeTransitionController.nextHideKeyguardNeedsNoAnimation) {
                z2 = false;
                lockscreenShadeTransitionController.nextHideKeyguardNeedsNoAnimation = false;
            } else {
                z2 = false;
                if (i3 != 2) {
                    lockscreenShadeTransitionController.performDefaultGoToFullShadeAnimation(j);
                }
            }
            NotificationEntry notificationEntry = lockscreenShadeTransitionController.draggedDownEntry;
            if (notificationEntry != null) {
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                if (expandableNotificationRow != null) {
                    expandableNotificationRow.setUserLocked(z2);
                }
                lockscreenShadeTransitionController.draggedDownEntry = null;
            }
            ((NavigationBarControllerImpl) this.mNavigationBarController).disableAnimationsDuringHide(this.mDisplayId, j);
        } else if (!shadeSurface.isCollapsing()) {
            this.mShadeController.instantCollapseShade();
            this.mRemoteInputManager.closeRemoteInputs(true);
        }
        messageRouter.cancelMessages(1003);
        releaseGestureWakeLock();
        ((CameraLauncher) lazy.get()).mKeyguardBypassController.launchingAffordance = false;
        shadeSurface.resetAlpha();
        shadeSurface.resetTranslation();
        shadeSurface.resetViewGroupFade();
        updateDozingState();
        updateScrimController();
        Trace.endSection();
        return z8;
    }

    public final void updateLightRevealScrimVisibility() {
        LightRevealScrim lightRevealScrim = this.mLightRevealScrim;
        if (lightRevealScrim == null) {
            return;
        }
        Flags.lightRevealMigration();
        lightRevealScrim.setAlpha(this.mScrimController.mState.getMaxLightRevealScrimAlpha());
        if (LsRune.AOD_LIGHT_REVEAL) {
            return;
        }
        lightRevealScrim.setVisibility(8);
    }

    public final void updateNotificationPanelTouchState() {
        boolean z = !(this.mDeviceInteractive || this.mDozeServiceHost.mPulsing) || (isGoingToSleep() && !this.mDozeParameters.mControlScreenOffAnimation);
        this.mShadeLogger.logUpdateNotificationPanelTouchState(z, isGoingToSleep(), !r2.mControlScreenOffAnimation, !this.mDeviceInteractive, !this.mDozeServiceHost.mPulsing);
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
        SecQSPanelController secQSPanelController = this.mQSPanelController;
        if (secQSPanelController != null && (qSTileLayout = secQSPanelController.mTileLayout) != null) {
            qSTileLayout.updateResources();
            secQSPanelController.updatePaddingAndMargins();
        }
        Flags.FEATURE_FLAGS.getClass();
        ShadeSurface shadeSurface = this.mShadeSurface;
        if (shadeSurface != null) {
            shadeSurface.updateResources$1();
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
        secLightRevealScrimHelper.powerKeyYPos = (int) (secLightRevealScrimHelper.context.getResources().getDimensionPixelSize(17105737) / f);
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
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i3, " currentDisplaySize.y=", i4, " powerKeyY=");
        m.append(i5);
        m.append(" radius=");
        m.append(hypot);
        Log.i("SecLightRevealScrimHelper", m.toString());
        this.mPowerButtonReveal = new PowerButtonReveal(this.mSecLightRevealScrimHelper.powerKeyYPos);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0030, code lost:
    
        if (r1.isAnimatingBetweenKeyguardAndSurfaceBehind() == false) goto L15;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00a7  */
    @Override // com.android.systemui.statusbar.phone.CentralSurfaces
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateScrimController() {
        /*
            Method dump skipped, instructions count: 388
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.CentralSurfacesImpl.updateScrimController():void");
    }

    public final void updateTheme() {
        ArrayList arrayList;
        this.mUiBgExecutor.execute(new CentralSurfacesImpl$$ExternalSyntheticLambda4(this, 1));
        int i = this.mColorExtractor.mNeutralColorsLock.supportsDarkText() ? R.style.Theme_SystemUI_LightWallpaper : R.style.Theme_SystemUI;
        if (this.mContext.getThemeResId() != i) {
            this.mContext.setTheme(i);
            ConfigurationControllerImpl configurationControllerImpl = (ConfigurationControllerImpl) this.mConfigurationController;
            synchronized (configurationControllerImpl.listeners) {
                arrayList = new ArrayList(configurationControllerImpl.listeners);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ConfigurationController.ConfigurationListener configurationListener = (ConfigurationController.ConfigurationListener) it.next();
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
