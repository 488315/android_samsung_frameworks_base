package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.IWallpaperManager;
import android.app.Notification;
import android.app.StatusBarManager;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricSourceType;
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
import android.service.dreams.IDreamManager;
import android.text.TextUtils;
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
import android.widget.DateTimeView;
import android.widget.FrameLayout;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.app.tracing.coroutines.TrackTracer;
import com.android.compose.animation.scene.SceneKey;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.RegisterStatusBarResult;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBouncerContainer;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.AutoReinflateContainer;
import com.android.systemui.BasicRune;
import com.android.systemui.CoreStartable;
import com.android.systemui.CoverScreenDecorHwcLayer$$ExternalSyntheticOutline0;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.InitController;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.Prefs;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.Rune;
import com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController;
import com.android.systemui.aiagent.AiAgentEffect;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.back.domain.interactor.BackActionInteractor;
import com.android.systemui.biometrics.AuthRippleController;
import com.android.systemui.bixby2.SystemUICommandActionHandler;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.camera.CameraIntents;
import com.android.systemui.charging.WiredChargingRippleController;
import com.android.systemui.charging.WirelessChargingAnimation;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
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
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.fragments.ExtensionFragmentListener;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
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
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.bandaid.BandAidPackFactory;
import com.android.systemui.navigationbar.interactor.ButtonOrderInteractor;
import com.android.systemui.navigationbar.interactor.ButtonPositionInteractor;
import com.android.systemui.navigationbar.interactor.ButtonToHideKeyboardInteractor;
import com.android.systemui.navigationbar.interactor.CoverDisplayWidgetInteractor;
import com.android.systemui.navigationbar.interactor.DesktopModeInteractor;
import com.android.systemui.navigationbar.interactor.DesktopModeInteractor$addCallback$2;
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
import com.android.systemui.navigationbar.plugin.PluginBarInteractionManager;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$14;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$20;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$21;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$5;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$6;
import com.android.systemui.navigationbar.views.NavigationBar;
import com.android.systemui.navigationbar.views.NavigationBarView;
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
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.ScreenPowerState;
import com.android.systemui.qs.QSFragmentLegacy;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQuickQSPanelController;
import com.android.systemui.qs.buttons.QSTooltipWindow;
import com.android.systemui.qs.flags.QSComposeFragment;
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
import com.android.systemui.settings.brightness.data.repository.BrightnessMirrorShowingRepository;
import com.android.systemui.shade.BaseShadeControllerImpl;
import com.android.systemui.shade.CameraLauncher;
import com.android.systemui.shade.GlanceableHubContainerController;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda1;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda7;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.NotificationShadeWindowViewController;
import com.android.systemui.shade.NotificationShadeWindowViewController$$ExternalSyntheticLambda6;
import com.android.systemui.shade.NotificationShadeWindowViewController.AnonymousClass1;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$attach$2;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeLogger;
import com.android.systemui.shade.ShadeLogger$$ExternalSyntheticLambda0;
import com.android.systemui.shade.ShadeSurface;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.statusbar.phone.BarTransitions;
import com.android.systemui.statusbar.AutoHideUiElement;
import com.android.systemui.statusbar.CircleReveal;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.KeyboardShortcuts;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.LiftReveal;
import com.android.systemui.statusbar.LightRevealScrim;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.PowerButtonReveal;
import com.android.systemui.statusbar.PulseExpansionHandler;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.SecLightRevealScrimHelper;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import com.android.systemui.statusbar.core.StatusBarInitializer;
import com.android.systemui.statusbar.core.StatusBarInitializerImpl;
import com.android.systemui.statusbar.core.StatusBarRootModernization;
import com.android.systemui.statusbar.data.model.StatusBarMode;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepository;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore;
import com.android.systemui.statusbar.domain.interactor.SecStatusBarWindowViewTouchedInteractor;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsChangedListener;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProviderImpl;
import com.android.systemui.statusbar.model.KshDataUtils;
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
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.DozeScrimController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ScrimController.AnonymousClass3;
import com.android.systemui.statusbar.phone.fragment.dagger.HomeStatusBarComponent;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BrightnessMirrorController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.statusbar.policy.ExtensionControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.statusbar.window.StatusBarWindowStateListener;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.WallpaperController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.MessageRouter;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.android.systemui.utils.windowmanager.WindowManagerProvider;
import com.android.systemui.utils.windowmanager.WindowManagerUtils;
import com.android.systemui.volume.VolumeComponent;
import com.android.systemui.volume.VolumeDialogComponent;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import com.android.systemui.wallet.controller.QuickAccessWalletController$$ExternalSyntheticLambda2;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.window.domain.interactor.WindowRootViewBlurInteractor;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda10;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.desktopmode.DesktopMode;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$addDefaultDisplayDesktopModeChangeListener$1;
import com.android.wm.shell.startingsurface.StartingWindowController;
import com.samsung.android.sdk.routines.v3.internal.RoutineSdkImpl;
import com.samsung.android.view.SemWindowManager;
import com.samsung.systemui.splugins.SPluginListener;
import com.samsung.systemui.splugins.SPluginManager;
import com.samsung.systemui.splugins.navigationbar.ColorSetting;
import com.samsung.systemui.splugins.navigationbar.PluginNavigationBar;
import com.samsung.systemui.splugins.noticenter.PluginNotiCenter;
import com.sec.ims.volte2.data.VolteConstants;
import dagger.Lazy;
import dalvik.annotation.optimization.NeverCompile;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

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
    public final CentralSurfacesImpl$$ExternalSyntheticLambda3 mOnColorsChangedListener = new ColorExtractor.OnColorsChangedListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda3
        public final void onColorsChanged(ColorExtractor colorExtractor, int i) {
            CentralSurfacesImpl centralSurfacesImpl = this.f$0;
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
                CentralSurfacesImpl.m3085$$Nest$mmaybeEscalateHeadsUp(centralSurfacesImpl);
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
        public final void onConfigChanged(Configuration configuration) throws Throwable {
            AiAgentEffect aiAgentEffect;
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.updateResources$1();
            centralSurfacesImpl.mDisplay.getMetrics(centralSurfacesImpl.mDisplayMetrics);
            centralSurfacesImpl.mDisplay.getSize(centralSurfacesImpl.mCurrentDisplaySize);
            QSTooltipWindow qSTooltipWindow = QSTooltipWindow.getInstance(centralSurfacesImpl.mContext);
            qSTooltipWindow.hideToolTip();
            if (QSTooltipWindow.configChanges.applyNewConfig(qSTooltipWindow.mContext.getResources())) {
                QSTooltipWindow.sInstance = new QSTooltipWindow(qSTooltipWindow.mContext);
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
            FrameLayout keyguardBouncerContainer;
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            BrightnessMirrorController brightnessMirrorController = centralSurfacesImpl.mBrightnessMirrorController;
            if (brightnessMirrorController != null) {
                brightnessMirrorController.reinflate$1();
            }
            centralSurfacesImpl.mUserInfoControllerImpl.reloadUserInfo();
            int i = NotificationIconContainerRefactor.$r8$clinit;
            centralSurfacesImpl.mNotificationIconAreaController.onDensityOrFontScaleChanged(centralSurfacesImpl.mContext);
            if (LsRune.SECURITY_SUB_DISPLAY_LOCK && centralSurfacesImpl.mBouncerShowing) {
                Log.d("CentralSurfaces", "onDensityOrFontScaleChanged - Skip call createBouncer on fold device");
                return;
            }
            NotificationShadeWindowViewController notificationShadeWindowViewController = centralSurfacesImpl.getNotificationShadeWindowViewController();
            notificationShadeWindowViewController.getClass();
            boolean z = LsRune.SECURITY_BOUNCER_WINDOW;
            NotificationShadeWindowView notificationShadeWindowView = notificationShadeWindowViewController.mView;
            if (z) {
                CentralSurfacesImpl centralSurfacesImpl2 = (CentralSurfacesImpl) notificationShadeWindowViewController.mService;
                FrameLayout frameLayout = centralSurfacesImpl2.mBouncerContainer;
                NotificationShadeWindowController notificationShadeWindowController = notificationShadeWindowViewController.mNotificationShadeWindowController;
                if (frameLayout != null) {
                    frameLayout.removeAllViews();
                    SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).mHelper;
                    ViewGroup viewGroup = secNotificationShadeWindowControllerHelperImpl.bouncerContainer;
                    if (viewGroup != null) {
                        secNotificationShadeWindowControllerHelperImpl.windowManager.removeView(viewGroup);
                    }
                    secNotificationShadeWindowControllerHelperImpl.bouncerContainer = null;
                    secNotificationShadeWindowControllerHelperImpl.bouncerLp = null;
                    secNotificationShadeWindowControllerHelperImpl.bouncerLpChanged = null;
                }
                keyguardBouncerContainer = new KeyguardBouncerContainer(notificationShadeWindowView.getContext(), notificationShadeWindowViewController.mSysUIKeyEventHandler, notificationShadeWindowViewController.mKeyguardSysDumpTrigger);
                centralSurfacesImpl2.mBouncerContainer = keyguardBouncerContainer;
                ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).mHelper.addBouncer(keyguardBouncerContainer);
            } else {
                FrameLayout frameLayout2 = (FrameLayout) notificationShadeWindowView.findViewById(R.id.keyguard_bouncer_container);
                if (frameLayout2 != null) {
                    frameLayout2.removeAllViews();
                    notificationShadeWindowView.removeView(frameLayout2);
                }
                keyguardBouncerContainer = (FrameLayout) LayoutInflater.from(notificationShadeWindowView.getContext()).inflate(R.layout.keyguard_sec_bouncer_container, (ViewGroup) null);
                notificationShadeWindowView.addView(keyguardBouncerContainer);
            }
            notificationShadeWindowViewController.mBouncerViewBinder.bind(keyguardBouncerContainer);
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

        /* JADX WARN: Removed duplicated region for block: B:19:0x0023 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onStatePreChange(int i, int i2) {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            if (((Boolean) centralSurfacesImpl.mWindowRootViewVisibilityInteractor.isLockscreenOrShadeVisible.$$delegate_0.getValue()).booleanValue()) {
                if (i2 != 2) {
                    StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) centralSurfacesImpl.mStatusBarStateController;
                    if (statusBarStateControllerImpl.mState == 0 && statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide) {
                        try {
                            centralSurfacesImpl.mBarService.clearNotificationEffects();
                        } catch (RemoteException unused) {
                        }
                    }
                }
            }
            if (i2 == 1) {
                RemoteInputCoordinator remoteInputCoordinator = centralSurfacesImpl.mRemoteInputManager.mRemoteInputListener;
                if (remoteInputCoordinator != null) {
                    remoteInputCoordinator.onPanelCollapsed();
                }
                CentralSurfacesImpl.m3085$$Nest$mmaybeEscalateHeadsUp(centralSurfacesImpl);
            }
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

    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$15, reason: invalid class name */
    public class AnonymousClass15 implements DeviceProvisionedController.DeviceProvisionedListener {
        public AnonymousClass15() {
        }

        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public final void onUserSetupChanged() {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            boolean zIsCurrentUserSetup = ((DeviceProvisionedControllerImpl) centralSurfacesImpl.mDeviceProvisionedController).isCurrentUserSetup();
            Log.d("CentralSurfaces", "mUserSetupObserver - DeviceProvisionedListener called for current user");
            if (zIsCurrentUserSetup != centralSurfacesImpl.mUserSetup) {
                centralSurfacesImpl.mUserSetup = zIsCurrentUserSetup;
                if (!zIsCurrentUserSetup && centralSurfacesImpl.mState == 0) {
                    centralSurfacesImpl.mShadeController.animateCollapseShade(0);
                }
                ShadeSurface shadeSurface = centralSurfacesImpl.mShadeSurface;
                if (shadeSurface != null) {
                    shadeSurface.setUserSetupComplete(centralSurfacesImpl.mUserSetup);
                }
            }
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$19, reason: invalid class name */
    public class AnonymousClass19 implements ActivityTransitionAnimator.Callback {
        public AnonymousClass19() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$3, reason: invalid class name */
    public class AnonymousClass3 implements PluginListener {
        public final ArraySet mOverlays = new ArraySet();

        /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$3$Callback */
        public class Callback implements OverlayPlugin.Callback {
            public final OverlayPlugin mPlugin;

            public Callback(OverlayPlugin overlayPlugin) {
                this.mPlugin = overlayPlugin;
            }

            @Override // com.android.systemui.plugins.OverlayPlugin.Callback
            public final void onHoldStatusBarOpenChange() {
                OverlayPlugin overlayPlugin = this.mPlugin;
                boolean zHoldStatusBarOpen = overlayPlugin.holdStatusBarOpen();
                AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                if (zHoldStatusBarOpen) {
                    anonymousClass3.mOverlays.add(overlayPlugin);
                } else {
                    anonymousClass3.mOverlays.remove(overlayPlugin);
                }
                CentralSurfacesImpl.this.mMainExecutor.execute(new CentralSurfacesImpl$$ExternalSyntheticLambda30(this, 1));
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
                    int i2 = SceneContainerFlag.$r8$clinit;
                    statusBarKeyguardViewManager.mAlternateBouncerInteractor.getClass();
                    statusBarKeyguardViewManager.showPrimaryBouncer("showBouncerOrLockScreenIfKeyguard(SHADE_LOCKED) SECURITY_SUB_DISPLAY_LOCK", true);
                    return;
                }
                return;
            }
            if (i == 1 && !statusBarKeyguardViewManager.primaryBouncerIsOrWillBeShowing()) {
                Log.d("CentralSurfaces", "showBouncerOrLockScreenIfKeyguard, showingBouncer");
                int i3 = SceneContainerFlag.$r8$clinit;
                statusBarKeyguardViewManager.mAlternateBouncerInteractor.getClass();
                statusBarKeyguardViewManager.showPrimaryBouncer("showBouncerOrLockScreenIfKeyguard(KEYGUARD) KEYGUARD_ALL_DIRECTIONS_SWIPE_UNLOCK", true);
                return;
            }
            if (centralSurfacesImpl.mState == 1 && !statusBarKeyguardViewManager.primaryBouncerIsOrWillBeShowing() && statusBarKeyguardViewManager.isSecure()) {
                int i4 = SceneContainerFlag.$r8$clinit;
                statusBarKeyguardViewManager.mAlternateBouncerInteractor.getClass();
                statusBarKeyguardViewManager.showPrimaryBouncer("CentralSurfacesImpl#showBouncerOrLockScreenIfKeyguard", true);
            }
        }
    }

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

    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$7, reason: invalid class name */
    public class AnonymousClass7 implements WirelessChargingAnimation.Callback {
        public AnonymousClass7() {
        }
    }

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
                if (!new KshDataUtils(context).isDexDisplay()) {
                    KeyboardShortcuts.dismiss();
                }
                CentralSurfacesImpl.this.mRemoteInputManager.closeRemoteInputs(false);
                if (((NotificationLockscreenUserManagerImpl) CentralSurfacesImpl.this.mLockscreenUserManager).isCurrentProfile(getSendingUserId())) {
                    CentralSurfacesImpl.this.mShadeLogger.d("ACTION_CLOSE_SYSTEM_DIALOGS intent: closing shade");
                    if (stringExtra != null) {
                        i = stringExtra.equals("recentapps") ? 2 : 0;
                        i = (stringExtra.equals(BcSmartspaceDataPlugin.UI_SURFACE_DREAM) && CentralSurfacesImpl.this.mScreenOffAnimationController.shouldExpandNotifications()) ? i | 4 : i;
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
                CentralSurfacesImpl.this.mMainHandler.postDelayed(new CentralSurfacesImpl$$ExternalSyntheticLambda30(this, i), 300L);
            }
            Trace.endSection();
        }
    }

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
        public final void onFinishedWakingUp() throws Resources.NotFoundException {
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
                Intent intentInvoke = ((EmergencyGestureModule$emergencyGestureIntentFactory$1) centralSurfacesImpl.mEmergencyGestureIntentFactory).invoke();
                if (intentInvoke != null) {
                    Context context = centralSurfacesImpl.mContext;
                    for (String str : context.getResources().getStringArray(R.array.system_ui_packages)) {
                        if (intentInvoke.getComponent() == null) {
                            break;
                        }
                        if (str.equals(intentInvoke.getComponent().getPackageName())) {
                            userHandle = new UserHandle(UserHandle.myUserId());
                            break;
                        }
                    }
                    userHandle = ((UserTrackerImpl) centralSurfacesImpl.mUserTracker).getUserHandle();
                    context.startActivityAsUser(intentInvoke, userHandle);
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
            CentralSurfacesImpl.m3086$$Nest$mupdateRevealEffect(centralSurfacesImpl, false);
            centralSurfacesImpl.updateNotificationPanelTouchState();
            CentralSurfacesImpl.m3085$$Nest$mmaybeEscalateHeadsUp(centralSurfacesImpl);
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
            CentralSurfacesImpl.m3086$$Nest$mupdateRevealEffect(centralSurfacesImpl, true);
            centralSurfacesImpl.updateNotificationPanelTouchState();
            centralSurfacesImpl.mShadeTouchableRegionManager.updateTouchableRegion();
            if (centralSurfacesImpl.mScreenOffAnimationController.shouldHideLightRevealScrimOnWakeUp()) {
                centralSurfacesImpl.mShadeController.makeExpandedInvisible();
            }
        }
    }

    public class AnimateExpandSettingsPanelMessage {
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
    public static void m3085$$Nest$mmaybeEscalateHeadsUp(CentralSurfacesImpl centralSurfacesImpl) {
        HeadsUpManagerImpl headsUpManagerImpl = (HeadsUpManagerImpl) centralSurfacesImpl.mHeadsUpManager;
        headsUpManagerImpl.getAllEntries().forEach(new CentralSurfacesImpl$$ExternalSyntheticLambda2(centralSurfacesImpl, 1));
        headsUpManagerImpl.releaseAllImmediately();
    }

    /* renamed from: -$$Nest$mupdateRevealEffect, reason: not valid java name */
    public static void m3086$$Nest$mupdateRevealEffect(CentralSurfacesImpl centralSurfacesImpl, boolean z) {
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
            StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("updateRevealEffect: wakingUp=", " wakingUpFromPowerButton=", " sleepingFromPowerButton=", z, z5);
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, z6, " wakingUpFromDoubleTap=", z7, " sleepingFromDoubleTap=");
            KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(sbM, z2, "CentralSurfaces");
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

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$1] */
    /* JADX WARN: Type inference failed for: r4v12, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$16] */
    /* JADX WARN: Type inference failed for: r4v13, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$17] */
    /* JADX WARN: Type inference failed for: r4v14, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$18] */
    /* JADX WARN: Type inference failed for: r4v16, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$20] */
    /* JADX WARN: Type inference failed for: r4v17, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$21] */
    /* JADX WARN: Type inference failed for: r4v18, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$22] */
    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r4v7, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$10] */
    /* JADX WARN: Type inference failed for: r4v9, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$13] */
    public CentralSurfacesImpl(MdmOverlayContainer mdmOverlayContainer, Lazy lazy, SystemUICommandActionHandler systemUICommandActionHandler, DisplayLifecycle displayLifecycle, SubScreenManager subScreenManager, Context context, NotificationsController notificationsController, FragmentService fragmentService, LightBarController lightBarController, AutoHideController autoHideController, StatusBarInitializer statusBarInitializer, StatusBarWindowControllerStore statusBarWindowControllerStore, StatusBarWindowStateController statusBarWindowStateController, StatusBarModeRepositoryStore statusBarModeRepositoryStore, KeyguardUpdateMonitor keyguardUpdateMonitor, StatusBarSignalPolicy statusBarSignalPolicy, PulseExpansionHandler pulseExpansionHandler, NotificationWakeUpCoordinator notificationWakeUpCoordinator, KeyguardBypassController keyguardBypassController, KeyguardStateController keyguardStateController, HeadsUpManager headsUpManager, FalsingManager falsingManager, FalsingCollector falsingCollector, BroadcastDispatcher broadcastDispatcher, NotificationGutsManager notificationGutsManager, ShadeExpansionStateManager shadeExpansionStateManager, KeyguardViewMediator keyguardViewMediator, DisplayMetrics displayMetrics, MetricsLogger metricsLogger, ShadeLogger shadeLogger, JavaAdapter javaAdapter, Executor executor, ShadeSurface shadeSurface, NotificationMediaManager notificationMediaManager, NotificationLockscreenUserManager notificationLockscreenUserManager, NotificationRemoteInputManager notificationRemoteInputManager, QuickSettingsController quickSettingsController, BatteryController batteryController, SysuiColorExtractor sysuiColorExtractor, ScreenLifecycle screenLifecycle, WakefulnessLifecycle wakefulnessLifecycle, PowerInteractor powerInteractor, CommunalInteractor communalInteractor, SysuiStatusBarStateController sysuiStatusBarStateController, Optional<Bubbles> optional, Lazy lazy2, DeviceProvisionedController deviceProvisionedController, NavigationBarController navigationBarController, AccessibilityFloatingMenuController accessibilityFloatingMenuController, Lazy lazy3, ConfigurationController configurationController, NotificationShadeWindowController notificationShadeWindowController, Lazy lazy4, NotificationStackScrollLayoutController notificationStackScrollLayoutController, Lazy lazy5, Lazy lazy6, NotificationLaunchAnimatorControllerProvider notificationLaunchAnimatorControllerProvider, DozeParameters dozeParameters, ScrimController scrimController, Lazy lazy7, AuthRippleController authRippleController, DozeServiceHost dozeServiceHost, BackActionInteractor backActionInteractor, PowerManager powerManager, DozeScrimController dozeScrimController, VolumeComponent volumeComponent, CommandQueue commandQueue, Lazy lazy8, PluginManager pluginManager, ShadeController shadeController, WindowRootViewVisibilityInteractor windowRootViewVisibilityInteractor, StatusBarKeyguardViewManager statusBarKeyguardViewManager, ViewMediatorCallback viewMediatorCallback, InitController initController, Handler handler, PluginDependencyProvider pluginDependencyProvider, ExtensionController extensionController, UserInfoControllerImpl userInfoControllerImpl, PhoneStatusBarPolicy phoneStatusBarPolicy, KeyguardIndicationController keyguardIndicationController, DemoModeController demoModeController, Lazy lazy9, ShadeTouchableRegionManager shadeTouchableRegionManager, NotificationIconAreaController notificationIconAreaController, BrightnessSliderController.Factory factory, ScreenOffAnimationController screenOffAnimationController, WallpaperController wallpaperController, OngoingActivityController ongoingActivityController, StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, FeatureFlags featureFlags, KeyguardUnlockAnimationController keyguardUnlockAnimationController, DelayableExecutor delayableExecutor, MessageRouter messageRouter, WallpaperManager wallpaperManager, Optional<StartingWindowController.StartingSurfaceImpl> optional2, ActivityTransitionAnimator activityTransitionAnimator, DeviceStateManager deviceStateManager, WiredChargingRippleController wiredChargingRippleController, IDreamManager iDreamManager, Lazy lazy10, Lazy lazy11, LightRevealScrim lightRevealScrim, AlternateBouncerInteractor alternateBouncerInteractor, UserTracker userTracker, ActivityStarter activityStarter, BrightnessMirrorShowingRepository brightnessMirrorShowingRepository, GlanceableHubContainerController glanceableHubContainerController, EmergencyGestureModule.EmergencyGestureIntentFactory emergencyGestureIntentFactory, QuickAccessWalletController quickAccessWalletController, WindowManager windowManager, WindowManagerProvider windowManagerProvider, SecQpBlurController secQpBlurController, Lazy lazy12, Handler handler2, NotifRemoteViewCache notifRemoteViewCache, CommonNotifCollection commonNotifCollection, KeyguardFoldController keyguardFoldController, Lazy lazy13, AiAgentEffect aiAgentEffect) {
        Context context2;
        final int i = 1;
        final int i2 = 0;
        this.mIdleOnCommunalConsumer = new CentralSurfacesImpl$$ExternalSyntheticLambda2(this, i2);
        this.mCheckBarModes = new CentralSurfacesImpl$$ExternalSyntheticLambda4(this, i2);
        this.mMdmOverlayContainer = mdmOverlayContainer;
        this.mDisplayLifecycle = displayLifecycle;
        this.mContext = context;
        this.mNotificationsController = notificationsController;
        this.mFragmentService = fragmentService;
        this.mLightBarController = lightBarController;
        this.mAutoHideController = autoHideController;
        this.mStatusBarInitializer = statusBarInitializer;
        this.mStatusBarWindowControllerStore = statusBarWindowControllerStore;
        this.mStatusBarModeRepository = statusBarModeRepositoryStore;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mPulseExpansionHandler = pulseExpansionHandler;
        this.mWakeUpCoordinator = notificationWakeUpCoordinator;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mKeyguardStateController = keyguardStateController;
        this.mHeadsUpManager = headsUpManager;
        this.mKeyguardIndicationController = keyguardIndicationController;
        this.mShadeTouchableRegionManager = shadeTouchableRegionManager;
        this.mFalsingCollector = falsingCollector;
        this.mFalsingManager = falsingManager;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mGutsManager = notificationGutsManager;
        this.mShadeExpansionStateManager = shadeExpansionStateManager;
        this.mKeyguardViewMediator = keyguardViewMediator;
        this.mDisplayMetrics = displayMetrics;
        this.mMetricsLogger = metricsLogger;
        this.mShadeLogger = shadeLogger;
        this.mJavaAdapter = javaAdapter;
        this.mUiBgExecutor = executor;
        this.mShadeSurface = shadeSurface;
        this.mMediaManager = notificationMediaManager;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.mQsController = quickSettingsController;
        this.mBatteryController = batteryController;
        this.mColorExtractor = sysuiColorExtractor;
        this.mScreenLifecycle = screenLifecycle;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mPowerInteractor = powerInteractor;
        this.mCommunalInteractor = communalInteractor;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mBubblesOptional = optional;
        this.mNoteTaskControllerLazy = lazy2;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mNavigationBarController = navigationBarController;
        this.mAccessibilityFloatingMenuController = accessibilityFloatingMenuController;
        this.mAssistManagerLazy = lazy3;
        this.mConfigurationController = configurationController;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mNotificationShadeWindowViewControllerLazy = lazy4;
        this.mStackScrollerController = notificationStackScrollLayoutController;
        this.mStackScroller = notificationStackScrollLayoutController.mView;
        this.mNotifListContainer = notificationStackScrollLayoutController.mNotificationListContainer;
        this.mPresenterLazy = lazy5;
        this.mNotificationActivityStarterLazy = lazy6;
        this.mNotificationAnimationProvider = notificationLaunchAnimatorControllerProvider;
        this.mDozeServiceHost = dozeServiceHost;
        this.mPowerManager = powerManager;
        this.mDozeParameters = dozeParameters;
        this.mScrimController = scrimController;
        this.mDozeScrimController = dozeScrimController;
        this.mBiometricUnlockControllerLazy = lazy7;
        this.mNotificationShadeDepthControllerLazy = lazy9;
        this.mVolumeComponent = volumeComponent;
        this.mCommandQueue = commandQueue;
        this.mCommandQueueCallbacksLazy = lazy8;
        this.mPluginManager = pluginManager;
        this.mShadeController = shadeController;
        this.mWindowRootViewVisibilityInteractor = windowRootViewVisibilityInteractor;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mKeyguardViewMediatorCallback = viewMediatorCallback;
        this.mInitController = initController;
        this.mPluginDependencyProvider = pluginDependencyProvider;
        this.mExtensionController = extensionController;
        this.mUserInfoControllerImpl = userInfoControllerImpl;
        this.mIconPolicy = phoneStatusBarPolicy;
        this.mDemoModeController = demoModeController;
        this.mNotificationIconAreaController = notificationIconAreaController;
        this.mBrightnessSliderFactory = factory;
        this.mWallpaperController = wallpaperController;
        this.mStatusBarSignalPolicy = statusBarSignalPolicy;
        this.mStatusBarHideIconsForBouncerManager = statusBarHideIconsForBouncerManager;
        this.mFeatureFlags = featureFlags;
        this.mMainExecutor = delayableExecutor;
        this.mMessageRouter = messageRouter;
        this.mWallpaperManager = wallpaperManager;
        this.mCameraLauncherLazy = lazy10;
        this.mAlternateBouncerInteractor = alternateBouncerInteractor;
        this.mUserTracker = userTracker;
        this.mActivityStarter = activityStarter;
        int i3 = SceneContainerFlag.$r8$clinit;
        this.mGlanceableHubContainerController = glanceableHubContainerController;
        this.mEmergencyGestureIntentFactory = emergencyGestureIntentFactory;
        this.mWalletController = quickAccessWalletController;
        this.mLockscreenShadeTransitionController = lockscreenShadeTransitionController;
        this.mStartingSurfaceOptional = optional2;
        this.mDreamManager = iDreamManager;
        lockscreenShadeTransitionController.centralSurfaces = this;
        int i4 = StatusBarConnectedDisplays.$r8$clinit;
        ((HashSet) statusBarWindowStateController.listeners).add(new StatusBarWindowStateListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda5
            @Override // com.android.systemui.statusbar.window.StatusBarWindowStateListener
            public final void onStatusBarWindowStateChanged(int i5) {
                CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                int i6 = StatusBarConnectedDisplays.$r8$clinit;
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                centralSurfacesImpl.mStatusBarWindowState = i5;
                centralSurfacesImpl.updateBubblesVisibility();
            }
        });
        this.mScreenOffAnimationController = screenOffAnimationController;
        ShadeExpansionListener shadeExpansionListener = new ShadeExpansionListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda6
            @Override // com.android.systemui.shade.ShadeExpansionListener
            public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
                UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
                CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) centralSurfacesImpl.mKeyguardStateController;
                boolean z = keyguardStateControllerImpl.mShowing;
                ShadeSurface shadeSurface2 = centralSurfacesImpl.mShadeSurface;
                if (z && !centralSurfacesImpl.mStatusBarKeyguardViewManager.primaryBouncerIsOrWillBeShowing() && !keyguardStateControllerImpl.mOccluded && keyguardStateControllerImpl.mCanDismissLockScreen && !centralSurfacesImpl.mKeyguardViewMediator.isAnySimPinSecure() && (!centralSurfacesImpl.mQsController.getExpanded() || !shadeExpansionChangeEvent.tracking)) {
                    shadeSurface2.getBarState();
                }
                float f = shadeExpansionChangeEvent.fraction;
                if (f == 0.0f || f == 1.0f) {
                    if (centralSurfacesImpl.getNavigationBarView() != null) {
                        centralSurfacesImpl.getNavigationBarView().updateSlippery();
                    }
                    if (shadeSurface2 != null) {
                        shadeSurface2.updateSystemUiStateFlags();
                    }
                }
                if (f == 1.0f) {
                    SeslColorSpectrumView$$ExternalSyntheticOutline0.m(centralSurfacesImpl.mLightRevealScrim.revealAmount, "CentralSurfaces", new StringBuilder("onPanelExpansionChanged: lightReveal amount="));
                }
            }
        };
        shadeExpansionListener.onPanelExpansionChanged(shadeExpansionStateManager.addExpansionListener(shadeExpansionListener));
        new ActivityIntentHelper(context);
        this.mActivityTransitionAnimator = activityTransitionAnimator;
        DateTimeView.setReceiverHandler(handler);
        messageRouter.subscribeTo(CentralSurfaces.KeyboardShortcutsMessage.class, new MessageRouter.DataMessageListener(this) { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda7
            public final /* synthetic */ CentralSurfacesImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.systemui.util.concurrency.MessageRouter.DataMessageListener
            public final void onMessage(Object obj) {
                CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                switch (i2) {
                    case 0:
                        UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
                        int i5 = ((CentralSurfaces.KeyboardShortcutsMessage) obj).mDeviceId;
                        centralSurfacesImpl.shouldUseTabletKeyboardShortcuts();
                        KeyboardShortcuts.toggle(centralSurfacesImpl.mContext, i5, centralSurfacesImpl.mWindowManagerProvider);
                        break;
                    default:
                        centralSurfacesImpl.mCommandQueueCallbacks.animateExpandSettingsPanel(((CentralSurfacesImpl.AnimateExpandSettingsPanelMessage) obj).mSubpanel);
                        break;
                }
            }
        });
        messageRouter.subscribeTo(1027, new MessageRouter.SimpleMessageListener(this) { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda8
            public final /* synthetic */ CentralSurfacesImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.systemui.util.concurrency.MessageRouter.SimpleMessageListener
            public final void onMessage(int i5) {
                CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                switch (i2) {
                    case 0:
                        UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
                        centralSurfacesImpl.shouldUseTabletKeyboardShortcuts();
                        KeyboardShortcuts.dismiss();
                        break;
                    default:
                        UiEventLogger uiEventLogger2 = CentralSurfacesImpl.sUiEventLogger;
                        Log.w("CentralSurfaces", "Launch transition: Timeout!");
                        ((CameraLauncher) centralSurfacesImpl.mCameraLauncherLazy.get()).setLaunchingAffordance(false);
                        ShadeSurface shadeSurface2 = centralSurfacesImpl.mShadeSurface;
                        shadeSurface2.onAffordanceLaunchEnded();
                        centralSurfacesImpl.releaseGestureWakeLock();
                        shadeSurface2.resetViews(false);
                        break;
                }
            }
        });
        messageRouter.subscribeTo(AnimateExpandSettingsPanelMessage.class, new MessageRouter.DataMessageListener(this) { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda7
            public final /* synthetic */ CentralSurfacesImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.systemui.util.concurrency.MessageRouter.DataMessageListener
            public final void onMessage(Object obj) {
                CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                switch (i) {
                    case 0:
                        UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
                        int i5 = ((CentralSurfaces.KeyboardShortcutsMessage) obj).mDeviceId;
                        centralSurfacesImpl.shouldUseTabletKeyboardShortcuts();
                        KeyboardShortcuts.toggle(centralSurfacesImpl.mContext, i5, centralSurfacesImpl.mWindowManagerProvider);
                        break;
                    default:
                        centralSurfacesImpl.mCommandQueueCallbacks.animateExpandSettingsPanel(((CentralSurfacesImpl.AnimateExpandSettingsPanelMessage) obj).mSubpanel);
                        break;
                }
            }
        });
        messageRouter.subscribeTo(1003, new MessageRouter.SimpleMessageListener(this) { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda8
            public final /* synthetic */ CentralSurfacesImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.systemui.util.concurrency.MessageRouter.SimpleMessageListener
            public final void onMessage(int i5) {
                CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                switch (i) {
                    case 0:
                        UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
                        centralSurfacesImpl.shouldUseTabletKeyboardShortcuts();
                        KeyboardShortcuts.dismiss();
                        break;
                    default:
                        UiEventLogger uiEventLogger2 = CentralSurfacesImpl.sUiEventLogger;
                        Log.w("CentralSurfaces", "Launch transition: Timeout!");
                        ((CameraLauncher) centralSurfacesImpl.mCameraLauncherLazy.get()).setLaunchingAffordance(false);
                        ShadeSurface shadeSurface2 = centralSurfacesImpl.mShadeSurface;
                        shadeSurface2.onAffordanceLaunchEnded();
                        centralSurfacesImpl.releaseGestureWakeLock();
                        shadeSurface2.resetViews(false);
                        break;
                }
            }
        });
        this.mDeviceStateManager = deviceStateManager;
        this.mLightRevealScrim = lightRevealScrim;
        this.mWindowManager = windowManager;
        this.mWindowManagerProvider = windowManagerProvider;
        this.mQuickPanelLogger = new QuickPanelLogger("CS");
        this.mQuickPanelLogBuilder = new StringBuilder();
        this.mBlurController = secQpBlurController;
        this.mStatusBarWindowViewTouchedInteractor = (SecStatusBarWindowViewTouchedInteractor) Dependency.sDependency.getDependencyInner(SecStatusBarWindowViewTouchedInteractor.class);
        if (LsRune.SUBSCREEN_UI) {
            this.mSubScreenManager = subScreenManager;
        }
        this.mNavBarHelperLazy = lazy12;
        this.mMainHandler = handler2;
        this.mNotifRemoteViewCache = notifRemoteViewCache;
        this.mNotifCollection = commonNotifCollection;
        NotiCenterPlugin.INSTANCE.getClass();
        NotiCenterPlugin.centralSurfaces = this;
        String str = NotiCenterPlugin.TAG;
        Log.d(str, "register observer");
        CentralSurfacesImpl centralSurfacesImpl = NotiCenterPlugin.centralSurfaces;
        NotiCenterPlugin.packageManager = (centralSurfacesImpl == null || (context2 = centralSurfacesImpl.mContext) == null) ? null : context2.getPackageManager();
        ComponentName componentName = new ComponentName("com.samsung.systemui.notilus", "com.samsung.systemui.notilus.service.NotificationListener");
        try {
            PackageManager packageManager = NotiCenterPlugin.packageManager;
            Integer numValueOf = packageManager != null ? Integer.valueOf(packageManager.getComponentEnabledSetting(componentName)) : null;
            if (numValueOf == null || numValueOf.intValue() != 2) {
                PackageManager packageManager2 = NotiCenterPlugin.packageManager;
                if (packageManager2 != null) {
                    packageManager2.setComponentEnabledSetting(componentName, 2, 1);
                }
            }
        } catch (IllegalArgumentException unused) {
            Log.d(str, "There is no Listener");
        }
        SPluginManager sPluginManager = (SPluginManager) Dependency.sDependency.getDependencyInner(SPluginManager.class);
        NotiCenterPlugin.INSTANCE.getClass();
        sPluginManager.addPluginListener((SPluginListener) NotiCenterPlugin.notiCenterPluginListener, PluginNotiCenter.class, false);
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            this.mKeyguardFoldController = keyguardFoldController;
        }
        if (BasicRune.AI_AGENT_EFFECT) {
            this.mAiAgentEffect = aiAgentEffect;
        }
    }

    public final void awakenDreams() {
        this.mUiBgExecutor.execute(new CentralSurfacesImpl$$ExternalSyntheticLambda4(this, 2));
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
                this.mMainHandler.postDelayed(new CentralSurfacesImpl$$ExternalSyntheticLambda13(this, expandableNotificationRow, str2, 0), 500L);
                return;
            }
        }
        Log.d("CentralSurfaces", " RemoteInput: no remote input for ".concat(str));
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x025f  */
    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    @NeverCompile
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dump(PrintWriter printWriter, String[] strArr) throws Resources.NotFoundException {
        SubscreenNotificationController subscreenNotificationController;
        PrintWriter printWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        synchronized (this.mQueueLock) {
            printWriterAsIndenting.println("Current Status Bar state:");
            printWriterAsIndenting.println("  mExpandedVisible=" + this.mShadeController.isExpandedVisible());
            printWriterAsIndenting.println("  mDisplayMetrics=" + this.mDisplayMetrics);
            printWriterAsIndenting.print("  mStackScroller: " + CentralSurfaces.viewInfo(this.mStackScroller));
            printWriterAsIndenting.print(" scroll " + this.mStackScroller.getScrollX() + "," + this.mStackScroller.getScrollY());
            StringBuilder sb = new StringBuilder(" translationX ");
            sb.append(this.mStackScroller.getTranslationX());
            printWriterAsIndenting.println(sb.toString());
        }
        printWriterAsIndenting.print("  mInteractingWindows=");
        printWriterAsIndenting.println(this.mInteractingWindows);
        int i = StatusBarConnectedDisplays.$r8$clinit;
        printWriterAsIndenting.print("  mStatusBarWindowState=");
        printWriterAsIndenting.println(StatusBarManager.windowStateToString(this.mStatusBarWindowState));
        printWriterAsIndenting.print("  mDozing=");
        printWriterAsIndenting.println(this.mDozing);
        printWriterAsIndenting.print("  mWallpaperSupported= ");
        printWriterAsIndenting.println(this.mWallpaperSupported);
        CentralSurfaces.dumpBarTransitions(printWriterAsIndenting, "PhoneStatusBarTransitions", this.mStatusBarTransitions);
        printWriterAsIndenting.println("  mMediaManager: ");
        NotificationMediaManager notificationMediaManager = this.mMediaManager;
        if (notificationMediaManager != null) {
            notificationMediaManager.dump(printWriterAsIndenting, strArr);
        }
        printWriterAsIndenting.println("  Panels: ");
        printWriterAsIndenting.println("  mStackScroller: " + this.mStackScroller + " (dump moved)");
        printWriterAsIndenting.println("  Theme:");
        printWriterAsIndenting.println("    dark theme: " + (this.mUiModeManager == null ? "null" : this.mUiModeManager.getNightMode() + "") + " (auto: 0, yes: 2, no: 1)");
        CoverScreenDecorHwcLayer$$ExternalSyntheticOutline0.m("    light wallpaper theme: ", this.mContext.getThemeResId() == 2132018949, printWriterAsIndenting);
        KeyguardIndicationController keyguardIndicationController = this.mKeyguardIndicationController;
        if (keyguardIndicationController != null) {
            keyguardIndicationController.dump(printWriterAsIndenting, strArr);
        }
        ScrimController scrimController = this.mScrimController;
        if (scrimController != null) {
            scrimController.dump(printWriterAsIndenting, strArr);
        }
        if (this.mLightRevealScrim != null) {
            printWriterAsIndenting.println("mLightRevealScrim.getRevealEffect(): " + this.mLightRevealScrim.revealEffect);
            printWriterAsIndenting.println("mLightRevealScrim.getRevealAmount(): " + this.mLightRevealScrim.revealAmount);
        }
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager;
        if (statusBarKeyguardViewManager != null) {
            statusBarKeyguardViewManager.dump(printWriterAsIndenting);
        }
        HeadsUpManager headsUpManager = this.mHeadsUpManager;
        if (headsUpManager != null) {
            ((HeadsUpManagerImpl) headsUpManager).dump(printWriterAsIndenting, strArr);
        } else {
            printWriterAsIndenting.println("  mHeadsUpManager: null");
        }
        ShadeTouchableRegionManager shadeTouchableRegionManager = this.mShadeTouchableRegionManager;
        if (shadeTouchableRegionManager != null) {
            shadeTouchableRegionManager.dump(printWriterAsIndenting, strArr);
        } else {
            printWriterAsIndenting.println("  mShadeTouchableRegionManager: null");
        }
        LightBarController lightBarController = this.mLightBarController;
        if (lightBarController != null) {
            ((LightBarControllerImpl) lightBarController).dump(printWriterAsIndenting, strArr);
        }
        printWriterAsIndenting.println("SharedPreferences:");
        for (Map.Entry<String, ?> entry : Prefs.get(this.mContext).getAll().entrySet()) {
            printWriterAsIndenting.print("  ");
            printWriterAsIndenting.print(entry.getKey());
            printWriterAsIndenting.print("=");
            printWriterAsIndenting.println(entry.getValue());
        }
        printWriterAsIndenting.println("Camera gesture intents:");
        StringBuilder sb2 = new StringBuilder("   Insecure camera: ");
        ((UserTrackerImpl) this.mUserTracker).getUserId();
        CameraIntents.Companion.getClass();
        KeyguardShortcutManager.Companion companion = KeyguardShortcutManager.Companion;
        companion.getClass();
        sb2.append(KeyguardShortcutManager.INSECURE_CAMERA_INTENT);
        printWriterAsIndenting.println(sb2.toString());
        StringBuilder sb3 = new StringBuilder("   Secure camera: ");
        ((UserTrackerImpl) this.mUserTracker).getUserId();
        companion.getClass();
        sb3.append(KeyguardShortcutManager.SECURE_CAMERA_INTENT);
        printWriterAsIndenting.println(sb3.toString());
        StringBuilder sb4 = new StringBuilder("   Override package: ");
        Context context = this.mContext;
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        String string = context.getResources().getString(R.string.config_cameraGesturePackage);
        string.getClass();
        try {
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("CameraIntents", "Missing cameraGesturePackage ".concat(string), e);
        }
        if (!TextUtils.isEmpty(string)) {
            if (!context.getPackageManager().getApplicationInfoAsUser(string, 0, userId).enabled) {
                string = null;
            }
        }
        sb4.append(string);
        printWriterAsIndenting.println(sb4.toString());
        if (BasicRune.NAVBAR_ENABLED) {
            NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) this.mNavBarStore;
            navBarStoreImpl.getClass();
            printWriterAsIndenting.println("Dump of NavBarStoreImpl : ");
            printWriterAsIndenting.increaseIndent();
            printWriterAsIndenting.print("Number of created navigation bar : ");
            printWriterAsIndenting.println(navBarStoreImpl.navStateManager.size());
            for (Map.Entry entry2 : navBarStoreImpl.navStateManager.entrySet()) {
                int iIntValue = ((Number) entry2.getKey()).intValue();
                NavBarStateManager navBarStateManager = (NavBarStateManager) entry2.getValue();
                printWriterAsIndenting.println("Navigationbar " + iIntValue + " states : ");
                printWriterAsIndenting.increaseIndent();
                if (navBarStateManager != null) {
                    printWriterAsIndenting.println(((NavBarStateManagerImpl) navBarStateManager).states.toString());
                } else {
                    printWriterAsIndenting.println("NavBarStateManager is null.");
                }
                printWriterAsIndenting.decreaseIndent();
            }
            PluginBarInteractionManager pluginBarInteractionManager = navBarStoreImpl.pluginBarInteractionManager;
            pluginBarInteractionManager.getClass();
            try {
                PluginNavigationBar pluginNavigationBar = pluginBarInteractionManager.pluginNavigationBar;
                if (pluginNavigationBar != null) {
                    pluginNavigationBar.dump(printWriterAsIndenting);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            printWriterAsIndenting.decreaseIndent();
        }
        if (NotiRune.NOTI_SUBSCREEN_ALL && (subscreenNotificationController = this.mSubscreenNotificationController) != null && subscreenNotificationController.mDeviceModel != null) {
            printWriterAsIndenting.println("Current SubscreenNotificationController state:");
        }
        this.mNotificationIconAreaController.dump(printWriterAsIndenting);
        NotiCenterPlugin.INSTANCE.dump(printWriterAsIndenting, strArr);
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

    public final boolean isForegroundComponentName(ComponentName componentName) throws SecurityException {
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
        boolean zIsBouncerShowing = this.mStatusBarKeyguardViewManager.isBouncerShowing();
        boolean z3 = keyguardStateControllerImpl.mSecure;
        boolean z4 = keyguardStateControllerImpl.mCanDismissLockScreen;
        int i = (this.mState & 255) | ((z ? 1 : 0) << 8) | ((z2 ? 1 : 0) << 9) | ((zIsBouncerShowing ? 1 : 0) << 10) | ((z3 ? 1 : 0) << 11) | ((z4 ? 1 : 0) << 12);
        if (i != this.mLastLoggedStateFingerprint) {
            if (this.mStatusBarStateLog == null) {
                this.mStatusBarStateLog = new LogMaker(0);
            }
            this.mMetricsLogger.write(this.mStatusBarStateLog.setCategory(zIsBouncerShowing ? 197 : 196).setType(z ? 1 : 2).setSubtype(z3 ? 1 : 0));
            EventLog.writeEvent(36004, Integer.valueOf(this.mState), Integer.valueOf(z ? 1 : 0), Integer.valueOf(z2 ? 1 : 0), Integer.valueOf(zIsBouncerShowing ? 1 : 0), Integer.valueOf(z3 ? 1 : 0), Integer.valueOf(z4 ? 1 : 0));
            this.mLastLoggedStateFingerprint = i;
            StringBuilder sb = new StringBuilder();
            sb.append(zIsBouncerShowing ? "BOUNCER" : "LOCKSCREEN");
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
        this.mDeviceStateManager.registerCallback(this.mMainExecutor, new FoldStateListener(this.mContext, new CentralSurfacesImpl$$ExternalSyntheticLambda24(this)));
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0574  */
    /* JADX WARN: Type inference failed for: r15v10, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$7] */
    /* JADX WARN: Type inference failed for: r15v12, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$4] */
    /* JADX WARN: Type inference failed for: r15v13, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$3] */
    /* JADX WARN: Type inference failed for: r15v14, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$2] */
    /* JADX WARN: Type inference failed for: r15v15, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$1] */
    /* JADX WARN: Type inference failed for: r15v9, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$8] */
    /* JADX WARN: Type inference failed for: r4v129, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$12] */
    /* JADX WARN: Type inference failed for: r4v130, types: [com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$11] */
    /* JADX WARN: Type inference failed for: r4v131, types: [com.android.systemui.navigationbar.interactor.KnoxStateMonitorInteractor$addCallback$2] */
    /* JADX WARN: Type inference failed for: r7v11, types: [android.content.BroadcastReceiver, com.android.systemui.navigationbar.interactor.DesktopModeInteractor$addCallback$2] */
    /* JADX WARN: Type inference failed for: r8v61, types: [android.content.BroadcastReceiver, com.android.systemui.navigationbar.interactor.PackageRemovedInteractor$addCallback$2] */
    /* JADX WARN: Type inference failed for: r8v63, types: [android.content.BroadcastReceiver, com.android.systemui.navigationbar.interactor.SettingsSoftResetInteractor$addCallback$2] */
    /* JADX WARN: Type inference failed for: r8v65, types: [com.android.systemui.navigationbar.interactor.RotationLockInteractor$addCallback$2, java.lang.Object] */
    @Override // com.android.systemui.CoreStartable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void start() throws Throwable {
        RegisterStatusBarResult registerStatusBarResultRegisterStatusBar;
        int i;
        final DeviceStateInteractor deviceStateInteractor;
        DeviceStateManager deviceStateManager;
        final DesktopModeInteractor desktopModeInteractor;
        PackageRemovedInteractor packageRemovedInteractor;
        CoverDisplayWidgetInteractor coverDisplayWidgetInteractor;
        TaskBarInteractor taskBarInteractor;
        int i2 = 0;
        CommandQueue commandQueue = this.mCommandQueue;
        this.mScreenLifecycle.addObserver(this.mScreenObserver);
        this.mWakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        this.mUiModeManager = (UiModeManager) this.mContext.getSystemService(UiModeManager.class);
        this.mBubblesOptional.ifPresent(new CentralSurfacesImpl$$ExternalSyntheticLambda2(this, 2));
        KeyguardBypassController keyguardBypassController = this.mKeyguardBypassController;
        keyguardBypassController.getClass();
        CoroutineTracingKt.launchTraced$default(keyguardBypassController.applicationScope, null, null, new KeyguardBypassController$listenForQsExpandedChange$1(keyguardBypassController, null), 6);
        this.mStatusBarSignalPolicy.getClass();
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
                        NavBarStoreImpl navBarStoreImpl2 = navBarStoreImpl;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnButtonOrderChanged(false, 1, null));
                    }
                });
            }
            ButtonPositionInteractor buttonPositionInteractor = (ButtonPositionInteractor) interactorFactory.get(ButtonPositionInteractor.class);
            if (buttonPositionInteractor != 0) {
                buttonPositionInteractor.addCallback(new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = navBarStoreImpl;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnButtonPositionChanged(false, 1, null));
                    }
                });
            }
            ButtonToHideKeyboardInteractor buttonToHideKeyboardInteractor = (ButtonToHideKeyboardInteractor) interactorFactory.get(ButtonToHideKeyboardInteractor.class);
            if (buttonToHideKeyboardInteractor != 0) {
                buttonToHideKeyboardInteractor.addCallback(new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = navBarStoreImpl;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnButtonToHideKeyboardChanged(false, 1, null));
                    }
                });
            }
            EdgeBackGesturePolicyInteractor edgeBackGesturePolicyInteractor = (EdgeBackGesturePolicyInteractor) interactorFactory.get(EdgeBackGesturePolicyInteractor.class);
            if (edgeBackGesturePolicyInteractor != 0) {
                edgeBackGesturePolicyInteractor.addCallback(new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$4
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = navBarStoreImpl;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnEdgeBackGestureDisablePolicyChanged(((Integer) obj).intValue()));
                    }
                });
            }
            GestureNavigationSettingsInteractor gestureNavigationSettingsInteractor = (GestureNavigationSettingsInteractor) interactorFactory.get(GestureNavigationSettingsInteractor.class);
            if (gestureNavigationSettingsInteractor != null) {
                NavBarStoreImpl$initInteractor$5 navBarStoreImpl$initInteractor$5 = new NavBarStoreImpl$initInteractor$5(navBarStoreImpl);
                NavBarStoreImpl$initInteractor$6 navBarStoreImpl$initInteractor$6 = new NavBarStoreImpl$initInteractor$6(navBarStoreImpl);
                gestureNavigationSettingsInteractor.forcedVisibleCallback = navBarStoreImpl$initInteractor$5;
                gestureNavigationSettingsInteractor.bottomSensitivityCallback = navBarStoreImpl$initInteractor$6;
                gestureNavigationSettingsInteractor.observer.register();
                gestureNavigationSettingsInteractor.onNavigationSettingsChanged();
            }
            boolean z = BasicRune.NAVBAR_TASKBAR;
            if (z && (taskBarInteractor = (TaskBarInteractor) interactorFactory.get(TaskBarInteractor.class)) != 0) {
                taskBarInteractor.addCallback(new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$7
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavBarStoreImpl navBarStoreImpl2 = navBarStoreImpl;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnUpdateTaskbarAvailable(false, 1, null));
                    }
                });
            }
            KeyboardButtonPositionInteractor keyboardButtonPositionInteractor = (KeyboardButtonPositionInteractor) interactorFactory.get(KeyboardButtonPositionInteractor.class);
            if (keyboardButtonPositionInteractor != 0) {
                keyboardButtonPositionInteractor.addCallback(new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = navBarStoreImpl;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnButtonOrderChanged(false, 1, null));
                    }
                });
            }
            KnoxStateMonitorInteractor knoxStateMonitorInteractor = (KnoxStateMonitorInteractor) interactorFactory.get(KnoxStateMonitorInteractor.class);
            if (knoxStateMonitorInteractor != null) {
                final Consumer consumer = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$9
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = navBarStoreImpl;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnNavBarKnoxPolicyChanged(false, 1, null));
                    }
                };
                final Consumer consumer2 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$10
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = navBarStoreImpl;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnHardKeyIntentPolicyChanged(((Boolean) obj).booleanValue()));
                    }
                };
                KnoxStateMonitorInteractor$addCallback$2 knoxStateMonitorInteractor$addCallback$2 = knoxStateMonitorInteractor.knoxStateMonitorCallback;
                if (knoxStateMonitorInteractor$addCallback$2 != null) {
                    ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).removeCallback(knoxStateMonitorInteractor$addCallback$2);
                }
                knoxStateMonitorInteractor.knoxStateMonitorCallback = new KnoxStateMonitorCallback() { // from class: com.android.systemui.navigationbar.interactor.KnoxStateMonitorInteractor$addCallback$2
                    @Override // com.android.systemui.knox.KnoxStateMonitorCallback
                    public final void onSetHardKeyIntentState(boolean z2) {
                        Consumer consumer3 = consumer2;
                        consumer3.getClass();
                        consumer3.accept(Boolean.valueOf(z2));
                    }

                    @Override // com.android.systemui.knox.KnoxStateMonitorCallback
                    public final void onUpdateNavigationBarHidden() {
                        Consumer consumer3 = consumer;
                        consumer3.getClass();
                        consumer3.accept(Boolean.TRUE);
                    }
                };
                ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).registerCallback(knoxStateMonitorInteractor.knoxStateMonitorCallback);
            }
            OpenThemeInteractor openThemeInteractor = (OpenThemeInteractor) interactorFactory.get(OpenThemeInteractor.class);
            if (openThemeInteractor != 0) {
                openThemeInteractor.addCallback(new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$11
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavBarStoreImpl navBarStoreImpl2 = navBarStoreImpl;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnOpenThemeChanged(false, 1, null));
                    }
                });
            }
            UseThemeDefaultInteractor useThemeDefaultInteractor = (UseThemeDefaultInteractor) interactorFactory.get(UseThemeDefaultInteractor.class);
            if (useThemeDefaultInteractor != 0) {
                useThemeDefaultInteractor.addCallback(new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$12
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavBarStoreImpl navBarStoreImpl2 = navBarStoreImpl;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnUseThemeDefaultChanged(false, 1, null));
                    }
                });
            }
            final RotationLockInteractor rotationLockInteractor = (RotationLockInteractor) interactorFactory.get(RotationLockInteractor.class);
            if (rotationLockInteractor != null) {
                final Consumer consumer3 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$13
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = navBarStoreImpl;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnRotationLockedChanged(((Boolean) obj).booleanValue()));
                    }
                };
                RotationLockInteractor$addCallback$2 rotationLockInteractor$addCallback$2 = rotationLockInteractor.rotationLockCallback;
                RotationLockController rotationLockController = rotationLockInteractor.rotationLockController;
                if (rotationLockInteractor$addCallback$2 != null) {
                    rotationLockController.removeCallback(rotationLockInteractor$addCallback$2);
                }
                ?? r8 = new RotationLockController.RotationLockControllerCallback() { // from class: com.android.systemui.navigationbar.interactor.RotationLockInteractor$addCallback$2
                    @Override // com.android.systemui.statusbar.policy.RotationLockController.RotationLockControllerCallback
                    public final void onRotationLockStateChanged(boolean z2, boolean z3) {
                        Consumer consumer4 = consumer3;
                        consumer4.getClass();
                        consumer4.accept(Boolean.valueOf(rotationLockInteractor.rotationLockController.isRotationLocked()));
                    }
                };
                rotationLockController.addCallback(r8);
                rotationLockInteractor.rotationLockCallback = r8;
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
                        NavBarStoreImpl navBarStoreImpl2 = navBarStoreImpl;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnSettingsSoftReset(false, 1, null));
                    }
                };
                SettingsSoftResetInteractor$addCallback$2 settingsSoftResetInteractor$addCallback$2 = settingsSoftResetInteractor.broadcastReceiver;
                if (settingsSoftResetInteractor$addCallback$2 != null) {
                    settingsSoftResetInteractor.broadcastDispatcher.unregisterReceiver(settingsSoftResetInteractor$addCallback$2);
                }
                ?? r82 = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.interactor.SettingsSoftResetInteractor$addCallback$2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        Runnable runnable2 = runnable;
                        if (runnable2 != null) {
                            runnable2.run();
                        }
                    }
                };
                BroadcastDispatcher.registerReceiver$default(settingsSoftResetInteractor.broadcastDispatcher, r82, settingsSoftResetInteractor.intentFilter, null, null, 0, null, 60);
                settingsSoftResetInteractor.broadcastReceiver = r82;
            }
            if (BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY && !BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && (coverDisplayWidgetInteractor = (CoverDisplayWidgetInteractor) interactorFactory.get(CoverDisplayWidgetInteractor.class)) != null) {
                coverDisplayWidgetInteractor.addCallback();
            }
            if (BasicRune.NAVBAR_REMOTEVIEW && (packageRemovedInteractor = (PackageRemovedInteractor) interactorFactory.get(PackageRemovedInteractor.class)) != null) {
                final Consumer consumer4 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$16
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = navBarStoreImpl;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnPackageRemoved((String) obj));
                    }
                };
                PackageRemovedInteractor$addCallback$2 packageRemovedInteractor$addCallback$2 = packageRemovedInteractor.broadcastReceiver;
                if (packageRemovedInteractor$addCallback$2 != null) {
                    packageRemovedInteractor.broadcastDispatcher.unregisterReceiver(packageRemovedInteractor$addCallback$2);
                }
                ?? r83 = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.interactor.PackageRemovedInteractor$addCallback$2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        if ("android.intent.action.PACKAGE_REMOVED".equals(intent.getAction()) || "android.intent.action.PACKAGE_RESTARTED".equals(intent.getAction())) {
                            Uri data = intent.getData();
                            data.getClass();
                            String encodedSchemeSpecificPart = data.getEncodedSchemeSpecificPart();
                            Consumer consumer5 = consumer4;
                            if (consumer5 != null) {
                                consumer5.accept(encodedSchemeSpecificPart);
                            }
                        }
                    }
                };
                packageRemovedInteractor.broadcastReceiver = r83;
                BroadcastDispatcher.registerReceiver$default(packageRemovedInteractor.broadcastDispatcher, r83, packageRemovedInteractor.intentFilter, null, ((UserTrackerImpl) packageRemovedInteractor.userTracker).getUserHandle(), 0, null, 48);
            }
            if ((BasicRune.BASIC_FOLDABLE_TYPE_FOLD || BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN || BasicRune.NAVBAR_MULTI_MODAL_ICON) && (deviceStateInteractor = (DeviceStateInteractor) interactorFactory.get(DeviceStateInteractor.class)) != null) {
                final Consumer consumer5 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$17
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = navBarStoreImpl;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnFoldStateChanged(((Boolean) obj).booleanValue()));
                        final NavBarStoreImpl navBarStoreImpl3 = navBarStoreImpl;
                        navBarStoreImpl3.handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$17.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                NavBarStateManager navBarStateManager = (NavBarStateManager) navBarStoreImpl3.navStateManager.get(1);
                                if (navBarStateManager != null) {
                                    NavBarStoreImpl navBarStoreImpl4 = navBarStoreImpl3;
                                    navBarStoreImpl4.handleEvent(navBarStoreImpl4, new EventTypeFactory.EventType.OnBarLayoutParamsProviderChanged(new NavBarCoverLayoutParams((Context) navBarStoreImpl4.getModule(Context.class, 1), navBarStateManager)), 1);
                                }
                            }
                        });
                    }
                };
                final Consumer consumer6 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$18
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStateManager navBarStateManager = (NavBarStateManager) navBarStoreImpl.navStateManager.get(1);
                        if (navBarStateManager != null) {
                            NavBarStoreImpl navBarStoreImpl2 = navBarStoreImpl;
                            navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnNavBarLargeCoverScreenVisibilityChanged(NavBarStateManager.isIMEShowing$default(navBarStateManager), ((Boolean) obj).booleanValue()), 1);
                        }
                    }
                };
                Consumer consumer7 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$19
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        boolean z2 = false;
                        NavBarStateManager navBarStateManager = (NavBarStateManager) navBarStoreImpl.navStateManager.get(0);
                        if (navBarStateManager != null) {
                            int iIntValue = ((Integer) obj).intValue();
                            NavBarStates navBarStates = ((NavBarStateManagerImpl) navBarStateManager).states;
                            int i3 = 1;
                            boolean z3 = iIntValue != navBarStates.lastTaskUserId;
                            navBarStates.lastTaskUserId = iIntValue;
                            if (z3) {
                                navBarStoreImpl.logWrapper.printLog(0, "Force update provided inset because Task's userId was changed. userId=" + ((Integer) obj));
                                NavigationBar navigationBar = (NavigationBar) navBarStoreImpl.getModule(NavigationBar.class, 0);
                                if (navigationBar != null) {
                                    navigationBar.updateNavBarLayoutParams();
                                }
                                if (BasicRune.NAVBAR_MULTI_MODAL_ICON) {
                                    NavBarStoreImpl navBarStoreImpl2 = navBarStoreImpl;
                                    navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnButtonOrderChanged(z2, i3, null));
                                }
                            }
                        }
                    }
                };
                NavBarStoreImpl$initInteractor$20 navBarStoreImpl$initInteractor$20 = new NavBarStoreImpl$initInteractor$20(navBarStoreImpl);
                DeviceStateManager.FoldStateListener foldStateListener = deviceStateInteractor.foldStateListener;
                if (foldStateListener != null && (deviceStateManager = deviceStateInteractor.deviceStateManager) != null) {
                    deviceStateManager.unregisterCallback(foldStateListener);
                }
                deviceStateInteractor.foldStateListener = new DeviceStateManager.FoldStateListener(deviceStateInteractor.context, new Consumer() { // from class: com.android.systemui.navigationbar.interactor.DeviceStateInteractor$addCallback$2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        deviceStateInteractor.foldCache = ((Boolean) obj).booleanValue();
                        DeviceStateInteractor deviceStateInteractor2 = deviceStateInteractor;
                        Consumer consumer8 = consumer5;
                        if (consumer8 != null) {
                            consumer8.accept(Boolean.valueOf(deviceStateInteractor2.foldCache));
                        } else {
                            deviceStateInteractor2.getClass();
                        }
                        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
                            DeviceStateInteractor deviceStateInteractor3 = deviceStateInteractor;
                            Consumer consumer9 = consumer6;
                            deviceStateInteractor3.getClass();
                            NavigationBarController navigationBarController = (NavigationBarController) Dependency.sDependency.getDependencyInner(NavigationBarController.class);
                            if (navigationBarController != null) {
                                boolean z2 = deviceStateInteractor3.foldCache;
                                DeviceStateInteractor$displayListener$1 deviceStateInteractor$displayListener$1 = deviceStateInteractor3.displayListener;
                                DeviceStateInteractor$componentCallbacks$1 deviceStateInteractor$componentCallbacks$1 = deviceStateInteractor3.componentCallbacks;
                                if (z2) {
                                    ((NavigationBarControllerImpl) navigationBarController).mCommandQueueCallbacks.onDisplayAddSystemDecorations(1);
                                    deviceStateInteractor3.displayManager.registerDisplayListener(deviceStateInteractor$displayListener$1, deviceStateInteractor3.handler);
                                    deviceStateInteractor3.coverTask = deviceStateInteractor3.new CoverTask(consumer9);
                                    ActivityTaskManager.getService().registerTaskStackListener(deviceStateInteractor3.coverTask);
                                    Context context = deviceStateInteractor3.windowContext;
                                    if (context != null) {
                                        context.registerComponentCallbacks(deviceStateInteractor$componentCallbacks$1);
                                        return;
                                    }
                                    return;
                                }
                                ((NavigationBarControllerImpl) navigationBarController).mCommandQueueCallbacks.onDisplayRemoveSystemDecorations(1);
                                deviceStateInteractor3.displayManager.unregisterDisplayListener(deviceStateInteractor$displayListener$1);
                                deviceStateInteractor3.lastRotation = -1;
                                deviceStateInteractor3.lastCoverRotation = 0;
                                deviceStateInteractor3.coverTaskCache = false;
                                ActivityTaskManager.getService().unregisterTaskStackListener(deviceStateInteractor3.coverTask);
                                Context context2 = deviceStateInteractor3.windowContext;
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
                    deviceStateInteractor.multimodalTask = deviceStateInteractor.new MultimodalTask(consumer7);
                    ActivityTaskManager.getService().registerTaskStackListener(deviceStateInteractor.multimodalTask);
                }
                DeviceStateManager deviceStateManager2 = deviceStateInteractor.deviceStateManager;
                if (deviceStateManager2 != null) {
                    Executor mainExecutor = deviceStateInteractor.context.getMainExecutor();
                    DeviceStateManager.FoldStateListener foldStateListener2 = deviceStateInteractor.foldStateListener;
                    foldStateListener2.getClass();
                    deviceStateManager2.registerCallback(mainExecutor, foldStateListener2);
                }
                deviceStateInteractor.largeCoverRotationCallback = navBarStoreImpl$initInteractor$20;
            }
            NavigationModeInteractor navigationModeInteractor = (NavigationModeInteractor) interactorFactory.get(NavigationModeInteractor.class);
            if (navigationModeInteractor != null) {
                navigationModeInteractor.addCallback(NavBarStoreImpl$initInteractor$21.INSTANCE);
            }
            if (BasicRune.NAVBAR_DESKTOP && (desktopModeInteractor = (DesktopModeInteractor) interactorFactory.get(DesktopModeInteractor.class)) != null) {
                final Consumer consumer8 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$22
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = navBarStoreImpl;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnUpdateTaskbarAvailable(false, 1, null));
                    }
                };
                DesktopModeInteractor$addCallback$2 desktopModeInteractor$addCallback$2 = desktopModeInteractor.broadcastReceiver;
                if (desktopModeInteractor$addCallback$2 != null) {
                    desktopModeInteractor.broadcastDispatcher.unregisterReceiver(desktopModeInteractor$addCallback$2);
                }
                ?? r7 = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.interactor.DesktopModeInteractor$addCallback$2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        String action = intent != null ? intent.getAction() : null;
                        if (action != null && action.hashCode() == 833559602 && action.equals("android.intent.action.USER_UNLOCKED")) {
                            DesktopModeInteractor desktopModeInteractor2 = desktopModeInteractor;
                            desktopModeInteractor2.userUnlocked = Boolean.TRUE;
                            Consumer consumer9 = consumer8;
                            if (consumer9 != null) {
                                consumer9.accept(Boolean.valueOf(desktopModeInteractor2.isEnabled()));
                            }
                        }
                    }
                };
                BroadcastDispatcher.registerReceiverWithHandler$default(desktopModeInteractor.broadcastDispatcher, r7, desktopModeInteractor.intentFilter, desktopModeInteractor.bgHandler, UserHandle.ALL, null, 48);
                desktopModeInteractor.broadcastReceiver = r7;
                DesktopMode desktopMode = desktopModeInteractor.desktopMode;
                if (desktopMode != null) {
                    DesktopTasksController.DefaultDisplayDesktopModeChangeListener defaultDisplayDesktopModeChangeListener = new DesktopTasksController.DefaultDisplayDesktopModeChangeListener() { // from class: com.android.systemui.navigationbar.interactor.DesktopModeInteractor$addCallback$4
                        @Override // com.android.wm.shell.desktopmode.DesktopTasksController.DefaultDisplayDesktopModeChangeListener
                        public final void onDefaultDisplayDesktopModeChanged(boolean z2) {
                            DesktopModeInteractor desktopModeInteractor2 = desktopModeInteractor;
                            if (desktopModeInteractor2.isDefaultDisplayDesktopMode != z2) {
                                desktopModeInteractor2.isDefaultDisplayDesktopMode = z2;
                                Consumer consumer9 = consumer8;
                                if (consumer9 != null) {
                                    consumer9.accept(Boolean.valueOf(desktopModeInteractor2.isEnabled()));
                                }
                            }
                        }
                    };
                    Executor executor = desktopModeInteractor.mainExecutor;
                    DesktopTasksController desktopTasksController = DesktopTasksController.this;
                    desktopTasksController.mainExecutor.execute(new DesktopTasksController$DesktopModeImpl$addDefaultDisplayDesktopModeChangeListener$1(desktopTasksController, defaultDisplayDesktopModeChangeListener, executor));
                }
                consumer8.accept(Boolean.valueOf(desktopModeInteractor.isEnabled()));
            }
            navBarStoreImpl.packs = ((BandAidPackFactory) navBarStoreImpl.bandAidPackFactory).getPacks(navBarStoreImpl);
            if (z) {
                TaskbarDelegate taskbarDelegate = (TaskbarDelegate) Dependency.sDependency.getDependencyInner(TaskbarDelegate.class);
                navBarStoreImpl.taskbarDelegate = taskbarDelegate;
                taskbarDelegate.getClass();
                navBarStoreImpl.putModule(TaskbarDelegate.class, taskbarDelegate, 0);
                TaskbarDelegate taskbarDelegate2 = navBarStoreImpl.taskbarDelegate;
                if (taskbarDelegate2 != null) {
                    taskbarDelegate2.mNavBarRemoteViewManager = navBarStoreImpl.navBarRemoteViewManager;
                }
            }
        }
        int i3 = StatusBarConnectedDisplays.$r8$clinit;
        try {
            registerStatusBarResultRegisterStatusBar = this.mBarService.registerStatusBar(commandQueue);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            registerStatusBarResultRegisterStatusBar = null;
        }
        this.mDisplay.getMetrics(this.mDisplayMetrics);
        this.mDisplay.getSize(this.mCurrentDisplaySize);
        updateResources$1();
        updateTheme();
        NotificationShadeWindowController notificationShadeWindowController = this.mNotificationShadeWindowController;
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) notificationShadeWindowController;
        WindowRootView windowRootView = ((DaggerReferenceGlobalRootComponent.WindowRootViewComponentImpl) notificationShadeWindowControllerImpl.mWindowRootViewComponentFactory.create()).getWindowRootView();
        notificationShadeWindowControllerImpl.mWindowRootView = windowRootView;
        Lazy lazy = notificationShadeWindowControllerImpl.mShadeInteractorLazy;
        JavaAdapterKt.collectFlow(windowRootView, ((ShadeInteractorImpl) ((ShadeInteractor) lazy.get())).baseShadeInteractor.isAnyExpanded(), new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda7(notificationShadeWindowControllerImpl, 2));
        JavaAdapterKt.collectFlow(notificationShadeWindowControllerImpl.mWindowRootView, ((ShadeInteractorImpl) ((ShadeInteractor) lazy.get())).baseShadeInteractor.isQsExpanded(), new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda7(notificationShadeWindowControllerImpl, 3));
        JavaAdapterKt.collectFlow(notificationShadeWindowControllerImpl.mWindowRootView, ((CommunalInteractor) notificationShadeWindowControllerImpl.mCommunalInteractor.get()).isCommunalVisible, new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda7(notificationShadeWindowControllerImpl, 4));
        int i4 = SceneContainerFlag.$r8$clinit;
        JavaAdapterKt.collectFlow(notificationShadeWindowControllerImpl.mWindowRootView, notificationShadeWindowControllerImpl.mNotificationShadeWindowModel.isKeyguardOccluded, new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda7(notificationShadeWindowControllerImpl, 5));
        NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda1 notificationShadeWindowControllerImpl$$ExternalSyntheticLambda1 = new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda1(notificationShadeWindowControllerImpl, 1);
        boolean z2 = Rune.SYSUI_MULTI_SIM;
        notificationShadeWindowControllerImpl$$ExternalSyntheticLambda1.run();
        ComposeBouncerFlags.INSTANCE.getClass();
        final NotificationShadeWindowViewController notificationShadeWindowViewController = getNotificationShadeWindowViewController();
        NotificationShadeWindowView notificationShadeWindowView = notificationShadeWindowViewController.mView;
        notificationShadeWindowViewController.mStackScrollLayout = (NotificationStackScrollLayout) notificationShadeWindowView.findViewById(R.id.notification_stack_scroller);
        notificationShadeWindowView.layoutInsetsController = notificationShadeWindowViewController.mNotificationInsetsController;
        notificationShadeWindowView.windowRootViewKeyEventHandler = notificationShadeWindowViewController.mWindowRootViewKeyEventHandler;
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
        notificationShadeDepthController.onPanelExpansionChanged(notificationShadeWindowViewController.mShadeExpansionStateManager.addExpansionListener(notificationShadeDepthController));
        NotificationShadeWindowViewController notificationShadeWindowViewController2 = getNotificationShadeWindowViewController();
        notificationShadeWindowViewController2.getClass();
        GlanceableHubContainerController glanceableHubContainerController = notificationShadeWindowViewController2.mGlanceableHubContainerController;
        glanceableHubContainerController.getClass();
        BooleanFlowOperators booleanFlowOperators = BooleanFlowOperators.INSTANCE;
        CommunalInteractor communalInteractor = glanceableHubContainerController.communalInteractor;
        JavaAdapterKt.collectFlow(notificationShadeWindowViewController2.mView, booleanFlowOperators.anyOf(communalInteractor.isCommunalAvailable(), communalInteractor.editModeOpen), new NotificationShadeWindowViewController$$ExternalSyntheticLambda6(notificationShadeWindowViewController2, i2));
        getNotificationShadeWindowViewController().mView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda36
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                ((AutoHideControllerImpl) centralSurfacesImpl.mAutoHideController).checkUserAutoHide(motionEvent);
                NotificationRemoteInputManager notificationRemoteInputManager = centralSurfacesImpl.mRemoteInputManager;
                notificationRemoteInputManager.getClass();
                int i5 = SceneContainerFlag.$r8$clinit;
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                if (motionEvent.getAction() == 4 && motionEvent.getX() == 0.0f && motionEvent.getY() == 0.0f && notificationRemoteInputManager.isRemoteInputActive()) {
                    notificationRemoteInputManager.closeRemoteInputs(false);
                }
                if (centralSurfacesImpl.mQsController.isCustomizing()) {
                    centralSurfacesImpl.mShadeController.onStatusBarTouch(motionEvent);
                }
                SecStatusBarWindowViewTouchedInteractor secStatusBarWindowViewTouchedInteractor = centralSurfacesImpl.mStatusBarWindowViewTouchedInteractor;
                if (secStatusBarWindowViewTouchedInteractor == null || !secStatusBarWindowViewTouchedInteractor.isTouched()) {
                    return centralSurfacesImpl.getNotificationShadeWindowViewController().mView.onTouchEvent(motionEvent);
                }
                QuickPanelLogger quickPanelLogger = centralSurfacesImpl.mQuickPanelLogger;
                if (quickPanelLogger == null) {
                    return true;
                }
                quickPanelLogger.logPanelState("StatusBarWindowView Touched");
                return true;
            }
        });
        this.mWallpaperController.setRootView(getNotificationShadeWindowViewController().mView);
        this.mDemoModeController.addCallback((DemoMode) this.mDemoModeCallback);
        int i5 = StatusBarConnectedDisplays.$r8$clinit;
        StatusBarModeRepositoryStore statusBarModeRepositoryStore = this.mStatusBarModeRepository;
        this.mJavaAdapter.alwaysCollectFlow(((StatusBarModePerDisplayRepositoryImpl) ((StatusBarModePerDisplayRepository) statusBarModeRepositoryStore.getDefaultDisplay())).statusBarMode, new CentralSurfacesImpl$$ExternalSyntheticLambda2(this, 4));
        CentralSurfacesCommandQueueCallbacks centralSurfacesCommandQueueCallbacks = (CentralSurfacesCommandQueueCallbacks) this.mCommandQueueCallbacksLazy.get();
        this.mCommandQueueCallbacks = centralSurfacesCommandQueueCallbacks;
        commandQueue.addCallback((CommandQueue.Callbacks) centralSurfacesCommandQueueCallbacks);
        ShadeExpansionStateManager shadeExpansionStateManager = this.mShadeExpansionStateManager;
        NotificationWakeUpCoordinator notificationWakeUpCoordinator = this.mWakeUpCoordinator;
        notificationWakeUpCoordinator.onPanelExpansionChanged(shadeExpansionStateManager.addExpansionListener(notificationWakeUpCoordinator));
        PluginDependencyProvider pluginDependencyProvider = this.mPluginDependencyProvider;
        pluginDependencyProvider.allowPluginDependency(DarkIconDispatcher.class);
        pluginDependencyProvider.allowPluginDependency(StatusBarStateController.class);
        CentralSurfacesImpl$$ExternalSyntheticLambda24 centralSurfacesImpl$$ExternalSyntheticLambda24 = new CentralSurfacesImpl$$ExternalSyntheticLambda24(this);
        StatusBarInitializerImpl statusBarInitializerImpl = (StatusBarInitializerImpl) this.mStatusBarInitializer;
        statusBarInitializerImpl.statusBarViewUpdatedListener = centralSurfacesImpl$$ExternalSyntheticLambda24;
        HomeStatusBarComponent homeStatusBarComponent = statusBarInitializerImpl.component;
        if (homeStatusBarComponent != null) {
            centralSurfacesImpl$$ExternalSyntheticLambda24.onStatusBarViewUpdated(homeStatusBarComponent.getPhoneStatusBarViewController(), homeStatusBarComponent.getPhoneStatusBarTransitions());
        }
        int i6 = StatusBarRootModernization.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        statusBarInitializerImpl.doStart();
        NotificationShadeWindowView notificationShadeWindowView2 = getNotificationShadeWindowViewController().mView;
        ShadeTouchableRegionManager shadeTouchableRegionManager = this.mShadeTouchableRegionManager;
        shadeTouchableRegionManager.mNotificationShadeWindowView = notificationShadeWindowView2;
        shadeTouchableRegionManager.mNotificationPanelView = notificationShadeWindowView2.findViewById(R.id.notification_panel);
        NavigationBarControllerImpl navigationBarControllerImpl = (NavigationBarControllerImpl) this.mNavigationBarController;
        navigationBarControllerImpl.getClass();
        boolean z3 = BasicRune.NAVBAR_POLICY_VISIBILITY || !navigationBarControllerImpl.initializeTaskbarIfNecessary();
        DisplayTracker displayTracker = navigationBarControllerImpl.mDisplayTracker;
        for (Display display2 : ((DisplayTrackerImpl) displayTracker).displayManager.getDisplays()) {
            if (!z3) {
                int displayId = display2.getDisplayId();
                displayTracker.getClass();
                if (displayId != 0) {
                    navigationBarControllerImpl.createNavigationBar(display2, null, registerStatusBarResultRegisterStatusBar);
                }
            }
        }
        keyguardIndicationController.setUpperTextView((KeyguardIndicationTextView) getNotificationShadeWindowViewController().mView.findViewById(R.id.keyguard_upper_fingerprint_indication));
        this.mAmbientIndicationContainer = getNotificationShadeWindowViewController().mView.findViewById(R.id.ambient_indication_container);
        ((AutoHideControllerImpl) this.mAutoHideController).mStatusBar = new AutoHideUiElement() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.5
            @Override // com.android.systemui.statusbar.AutoHideUiElement
            public final void hide() {
                StatusBarModePerDisplayRepositoryImpl statusBarModePerDisplayRepositoryImpl = (StatusBarModePerDisplayRepositoryImpl) ((StatusBarModePerDisplayRepository) CentralSurfacesImpl.this.mStatusBarModeRepository.getDefaultDisplay());
                statusBarModePerDisplayRepositoryImpl.getClass();
                statusBarModePerDisplayRepositoryImpl._isTransientShown.updateState(null, Boolean.FALSE);
            }

            @Override // com.android.systemui.statusbar.AutoHideUiElement
            public final boolean isVisible() {
                return ((Boolean) ((StatusBarModePerDisplayRepositoryImpl) ((StatusBarModePerDisplayRepository) CentralSurfacesImpl.this.mStatusBarModeRepository.getDefaultDisplay())).isTransientShown.$$delegate_0.getValue()).booleanValue();
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
        final int i7 = 0;
        Supplier supplier = new Supplier() { // from class: com.android.systemui.statusbar.phone.ScrimController$$ExternalSyntheticLambda6
            @Override // java.util.function.Supplier
            public final Object get() {
                int i8 = i7;
                ScrimController scrimController2 = scrimController;
                switch (i8) {
                    case 0:
                        return scrimController2.mNotificationsScrim;
                    case 1:
                        Boolean bool = (Boolean) ((WindowRootViewBlurInteractor) scrimController2.mWindowRootViewBlurInteractor.get()).isBlurCurrentlySupported.$$delegate_0.getValue();
                        bool.getClass();
                        return bool;
                    case 2:
                        return scrimController2.mScrimBehind;
                    case 3:
                        return scrimController2.mScrimInFront;
                    default:
                        return Boolean.valueOf(scrimController2.mKeyguardOccluded);
                }
            }
        };
        final int i8 = 2;
        Supplier supplier2 = new Supplier() { // from class: com.android.systemui.statusbar.phone.ScrimController$$ExternalSyntheticLambda6
            @Override // java.util.function.Supplier
            public final Object get() {
                int i82 = i8;
                ScrimController scrimController2 = scrimController;
                switch (i82) {
                    case 0:
                        return scrimController2.mNotificationsScrim;
                    case 1:
                        Boolean bool = (Boolean) ((WindowRootViewBlurInteractor) scrimController2.mWindowRootViewBlurInteractor.get()).isBlurCurrentlySupported.$$delegate_0.getValue();
                        bool.getClass();
                        return bool;
                    case 2:
                        return scrimController2.mScrimBehind;
                    case 3:
                        return scrimController2.mScrimInFront;
                    default:
                        return Boolean.valueOf(scrimController2.mKeyguardOccluded);
                }
            }
        };
        final int i9 = 3;
        final int i10 = 4;
        SecLsScrimControlProvider secLsScrimControlProvider = new SecLsScrimControlProvider(supplier, supplier2, new Supplier() { // from class: com.android.systemui.statusbar.phone.ScrimController$$ExternalSyntheticLambda6
            @Override // java.util.function.Supplier
            public final Object get() {
                int i82 = i9;
                ScrimController scrimController2 = scrimController;
                switch (i82) {
                    case 0:
                        return scrimController2.mNotificationsScrim;
                    case 1:
                        Boolean bool = (Boolean) ((WindowRootViewBlurInteractor) scrimController2.mWindowRootViewBlurInteractor.get()).isBlurCurrentlySupported.$$delegate_0.getValue();
                        bool.getClass();
                        return bool;
                    case 2:
                        return scrimController2.mScrimBehind;
                    case 3:
                        return scrimController2.mScrimInFront;
                    default:
                        return Boolean.valueOf(scrimController2.mKeyguardOccluded);
                }
            }
        }, new Supplier() { // from class: com.android.systemui.statusbar.phone.ScrimController$$ExternalSyntheticLambda6
            @Override // java.util.function.Supplier
            public final Object get() {
                int i82 = i10;
                ScrimController scrimController2 = scrimController;
                switch (i82) {
                    case 0:
                        return scrimController2.mNotificationsScrim;
                    case 1:
                        Boolean bool = (Boolean) ((WindowRootViewBlurInteractor) scrimController2.mWindowRootViewBlurInteractor.get()).isBlurCurrentlySupported.$$delegate_0.getValue();
                        bool.getClass();
                        return bool;
                    case 2:
                        return scrimController2.mScrimBehind;
                    case 3:
                        return scrimController2.mScrimInFront;
                    default:
                        return Boolean.valueOf(scrimController2.mKeyguardOccluded);
                }
            }
        }, new ScrimController$$ExternalSyntheticLambda3(scrimController, 1), new ScrimController$$ExternalSyntheticLambda1(scrimController, 2));
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
                    boolean zIsFingerprintOptionEnabled = secLsScrimControlHelper2.mKeyguardUpdateMonitor.isFingerprintOptionEnabled();
                    if (secLsScrimControlHelper2.mIsFingerprintOptionEnabled != zIsFingerprintOptionEnabled) {
                        secLsScrimControlHelper2.mIsFingerprintOptionEnabled = zIsFingerprintOptionEnabled;
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
        ScrimState[] scrimStateArrValues = ScrimState.values();
        int i11 = 0;
        while (i11 < scrimStateArrValues.length) {
            ScrimState scrimState = scrimStateArrValues[i11];
            ScrimView scrimView7 = scrimController.mScrimInFront;
            ScrimView scrimView8 = scrimController.mScrimBehind;
            DozeParameters dozeParameters = scrimController.mDozeParameters;
            DockManager dockManager = scrimController.mDockManager;
            ScrimState[] scrimStateArr = scrimStateArrValues;
            int i12 = i11;
            final int i13 = 1;
            Supplier<Boolean> supplier3 = new Supplier() { // from class: com.android.systemui.statusbar.phone.ScrimController$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final Object get() {
                    int i82 = i13;
                    ScrimController scrimController2 = scrimController;
                    switch (i82) {
                        case 0:
                            return scrimController2.mNotificationsScrim;
                        case 1:
                            Boolean bool = (Boolean) ((WindowRootViewBlurInteractor) scrimController2.mWindowRootViewBlurInteractor.get()).isBlurCurrentlySupported.$$delegate_0.getValue();
                            bool.getClass();
                            return bool;
                        case 2:
                            return scrimController2.mScrimBehind;
                        case 3:
                            return scrimController2.mScrimInFront;
                        default:
                            return Boolean.valueOf(scrimController2.mKeyguardOccluded);
                    }
                }
            };
            AODAmbientWallpaperHelper aODAmbientWallpaperHelper = scrimController.mAODAmbientWallpaperHelper;
            scrimState.getClass();
            KeyguardUpdateMonitor keyguardUpdateMonitor2 = keyguardUpdateMonitor;
            KeyguardIndicationController keyguardIndicationController2 = keyguardIndicationController;
            scrimState.mBackgroundColor = scrimView8.getContext().getColor(R.color.shade_scrim_background_dark);
            scrimState.mScrimInFront = scrimView7;
            scrimState.mScrimBehind = scrimView8;
            scrimState.mDozeParameters = dozeParameters;
            scrimState.mDockManager = dockManager;
            scrimState.mDisplayRequiresBlanking = dozeParameters.getDisplayNeedsBlanking();
            scrimState.mIsBlurSupported = supplier3;
            if (LsRune.AOD_FULLSCREEN) {
                scrimState.mAODAmbientWallpaperHelper = aODAmbientWallpaperHelper;
            }
            ScrimState scrimState2 = scrimStateArr[i12];
            scrimState2.mScrimBehindAlphaKeyguard = scrimController.mScrimBehindAlphaKeyguard;
            scrimState2.setDefaultScrimAlpha();
            i11 = i12 + 1;
            scrimStateArrValues = scrimStateArr;
            keyguardUpdateMonitor = keyguardUpdateMonitor2;
            keyguardIndicationController = keyguardIndicationController2;
        }
        KeyguardIndicationController keyguardIndicationController3 = keyguardIndicationController;
        KeyguardUpdateMonitor keyguardUpdateMonitor3 = keyguardUpdateMonitor;
        scrimController.mScrimColorState = new ScrimStateLogger(scrimController.mScrimInFront, scrimController.mNotificationsScrim, scrimController.mScrimBehind, scrimController.new AnonymousClass3());
        scrimController.mTransparentScrimBackground = scrimView2.getResources().getBoolean(R.bool.notification_scrim_transparent);
        scrimController.updateScrims();
        scrimController.mKeyguardUpdateMonitor.registerCallback(scrimController.mKeyguardVisibilityCallback);
        for (ScrimState scrimState3 : ScrimState.values()) {
            scrimState3.prepare(scrimState3);
        }
        int i14 = SceneContainerFlag.$r8$clinit;
        scrimController.mBouncerToGoneTransition = new ScrimController$$ExternalSyntheticLambda1(scrimController, 3);
        KeyguardTransitionInteractor keyguardTransitionInteractor = scrimController.mKeyguardTransitionInteractor;
        Edge.Companion.getClass();
        KeyguardState keyguardState = KeyguardState.PRIMARY_BOUNCER;
        KeyguardState keyguardState2 = KeyguardState.GONE;
        Edge.StateToState stateToState = new Edge.StateToState(keyguardState, keyguardState2);
        keyguardTransitionInteractor.getClass();
        JavaAdapterKt.collectFlow(scrimView, keyguardTransitionInteractor.transition(stateToState), scrimController.mBouncerToGoneTransition, scrimController.mMainDispatcher);
        JavaAdapterKt.collectFlow(scrimView, scrimController.mPrimaryBouncerToGoneTransitionViewModel.scrimAlpha, scrimController.mScrimAlphaConsumer, scrimController.mMainDispatcher);
        KeyguardTransitionInteractor keyguardTransitionInteractor2 = scrimController.mKeyguardTransitionInteractor;
        KeyguardState keyguardState3 = KeyguardState.ALTERNATE_BOUNCER;
        new Edge.StateToContent(keyguardState3, Scenes.Gone);
        Edge.StateToState stateToState2 = new Edge.StateToState(keyguardState3, keyguardState2);
        keyguardTransitionInteractor2.getClass();
        JavaAdapterKt.collectFlow(scrimView, keyguardTransitionInteractor2.transition(stateToState2), scrimController.mBouncerToGoneTransition, scrimController.mMainDispatcher);
        JavaAdapterKt.collectFlow(scrimView, scrimController.mAlternateBouncerToGoneTransitionViewModel.scrimAlpha, scrimController.mScrimAlphaConsumer, scrimController.mMainDispatcher);
        KeyguardTransitionInteractor keyguardTransitionInteractor3 = scrimController.mKeyguardTransitionInteractor;
        KeyguardState keyguardState4 = KeyguardState.LOCKSCREEN;
        SceneKey sceneKey = Scenes.Communal;
        new Edge.StateToContent(keyguardState4, sceneKey);
        KeyguardState keyguardState5 = KeyguardState.GLANCEABLE_HUB;
        Edge.StateToState stateToState3 = new Edge.StateToState(keyguardState4, keyguardState5);
        keyguardTransitionInteractor3.getClass();
        JavaAdapterKt.collectFlow(scrimView, keyguardTransitionInteractor3.transition(stateToState3), scrimController.mGlanceableHubConsumer, scrimController.mMainDispatcher);
        KeyguardTransitionInteractor keyguardTransitionInteractor4 = scrimController.mKeyguardTransitionInteractor;
        new Edge.ContentToState(sceneKey, keyguardState4);
        Edge.StateToState stateToState4 = new Edge.StateToState(keyguardState5, keyguardState4);
        keyguardTransitionInteractor4.getClass();
        JavaAdapterKt.collectFlow(scrimView, keyguardTransitionInteractor4.transition(stateToState4), scrimController.mGlanceableHubConsumer, scrimController.mMainDispatcher);
        CentralSurfacesImpl$$ExternalSyntheticLambda2 centralSurfacesImpl$$ExternalSyntheticLambda22 = new CentralSurfacesImpl$$ExternalSyntheticLambda2(this, 6);
        LightRevealScrim lightRevealScrim = this.mLightRevealScrim;
        lightRevealScrim.isScrimOpaqueChangedListener = centralSurfacesImpl$$ExternalSyntheticLambda22;
        boolean z4 = this.mUserSetup;
        ShadeSurface shadeSurface = this.mShadeSurface;
        shadeSurface.setUserSetupComplete(z4);
        ScreenOffAnimationController screenOffAnimationController = this.mScreenOffAnimationController;
        Iterator it = screenOffAnimationController.animations.iterator();
        while (it.hasNext()) {
            ((ScreenOffAnimation) it.next()).initialize(this, shadeSurface, lightRevealScrim);
        }
        screenOffAnimationController.wakefulnessLifecycle.addObserver(screenOffAnimationController);
        LightRevealScrim lightRevealScrim2 = this.mLightRevealScrim;
        if (lightRevealScrim2 != null && !LsRune.AOD_LIGHT_REVEAL) {
            lightRevealScrim2.setVisibility(8);
        }
        int i15 = SceneContainerFlag.$r8$clinit;
        ShadeController shadeController = this.mShadeController;
        Objects.requireNonNull(shadeController);
        shadeSurface.initDependencies(this, new CentralSurfacesImpl$$ExternalSyntheticLambda30(shadeController, 0), this.mHeadsUpManager);
        View viewFindViewById = getNotificationShadeWindowViewController().mView.findViewById(R.id.qs_frame);
        if (viewFindViewById != null) {
            FragmentService fragmentService = this.mFragmentService;
            FragmentHostManager fragmentHostManager = fragmentService.getFragmentHostManager(viewFindViewById);
            ExtensionControllerImpl extensionControllerImpl = (ExtensionControllerImpl) this.mExtensionController;
            extensionControllerImpl.getClass();
            ExtensionControllerImpl.ExtensionBuilder extensionBuilder = new ExtensionControllerImpl.ExtensionBuilder(extensionControllerImpl, 0);
            extensionBuilder.withPlugin(QS.class);
            Supplier supplier4 = new Supplier() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda31
                @Override // java.util.function.Supplier
                public final Object get() {
                    CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                    centralSurfacesImpl.getClass();
                    int i16 = QSComposeFragment.$r8$clinit;
                    FragmentHostManager fragmentHostManager2 = centralSurfacesImpl.mFragmentService.getFragmentHostManager(centralSurfacesImpl.getNotificationShadeWindowViewController().mView);
                    return (QS) fragmentHostManager2.mPlugins.instantiate(fragmentHostManager2.mContext, QSFragmentLegacy.class.getName(), null);
                }
            };
            ExtensionControllerImpl.ExtensionImpl extensionImpl = extensionBuilder.mExtension;
            extensionImpl.mProducers.add(new ExtensionControllerImpl.ExtensionImpl.Default(extensionImpl, supplier4));
            ExtensionFragmentListener.attachExtensonToFragment(fragmentService, viewFindViewById, extensionBuilder.build());
            this.mBrightnessMirrorController = new BrightnessMirrorController(getNotificationShadeWindowViewController().mView, this.mShadeSurface, (NotificationShadeDepthController) this.mNotificationShadeDepthControllerLazy.get(), this.mBrightnessSliderFactory, new CentralSurfacesImpl$$ExternalSyntheticLambda2(this, 7));
            fragmentHostManager.addTagListener(QS.TAG, new FragmentHostManager.FragmentListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda33
                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
                public final void onFragmentViewCreated(Fragment fragment) {
                    UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
                    CentralSurfacesImpl centralSurfacesImpl = this.f$0;
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
        View viewFindViewById2 = getNotificationShadeWindowViewController().mView.findViewById(R.id.report_rejected_touch);
        this.mReportRejectedTouch = viewFindViewById2;
        if (viewFindViewById2 != null) {
            updateReportRejectedTouchVisibility();
            this.mReportRejectedTouch.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda34
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                    Uri uriReportRejectedTouch = centralSurfacesImpl.mFalsingManager.reportRejectedTouch();
                    if (uriReportRejectedTouch == null) {
                        return;
                    }
                    StringWriter stringWriter = new StringWriter();
                    stringWriter.write("Build info: ");
                    stringWriter.write(SystemProperties.get("ro.build.description"));
                    stringWriter.write("\nSerial number: ");
                    stringWriter.write(SystemProperties.get("ro.serialno"));
                    stringWriter.write("\n");
                    centralSurfacesImpl.mActivityStarter.startActivityDismissingKeyguard(Intent.createChooser(new Intent("android.intent.action.SEND").setType("*/*").putExtra("android.intent.extra.SUBJECT", "Rejected touch report").putExtra("android.intent.extra.STREAM", uriReportRejectedTouch).putExtra("android.intent.extra.TEXT", stringWriter.toString()), "Share rejected touch report").addFlags(268435456), true, true, null);
                }
            });
        }
        if (!this.mPowerManager.isInteractive()) {
            this.mBroadcastReceiver.onReceive(this.mContext, new Intent("android.intent.action.SCREEN_OFF"));
        }
        this.mGestureWakeLock = this.mPowerManager.newWakeLock(10, "sysui:GestureWakeLock");
        registerBroadcastReceiver();
        AnonymousClass15 anonymousClass15 = this.mUserSetupObserver;
        ((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).addCallback(anonymousClass15);
        anonymousClass15.onUserSetupChanged();
        ThreadedRenderer.overrideProperty("disableProfileBars", "true");
        ThreadedRenderer.overrideProperty("ambientRatio", String.valueOf(1.5f));
        this.mBroadcastDispatcher.registerReceiver(this.mRemoteInputActionBroadcastReceiver, new IntentFilter("com.samsung.systemui.action.REQUEST_REMOTE_INPUT"), null, UserHandle.ALL);
        WindowManager.LayoutParams layoutParams = notificationShadeWindowControllerImpl.mShadeWindowLayoutParams;
        notificationShadeWindowControllerImpl.mLp = layoutParams;
        String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
        if (notificationShadeWindowControllerImpl.mIndicatorCutoutUtil.isUDCModel) {
            layoutParams.semAddExtensionFlags(8192);
        }
        notificationShadeWindowControllerImpl.mWindowManager.addView(notificationShadeWindowControllerImpl.mWindowRootView, notificationShadeWindowControllerImpl.mLp);
        if (notificationShadeWindowControllerImpl.mWindowRootView.getWindowInsetsController() != null) {
            notificationShadeWindowControllerImpl.mWindowRootView.getWindowInsetsController().setSystemBarsBehavior(2);
        }
        notificationShadeWindowControllerImpl.mLpChanged.copyFrom(notificationShadeWindowControllerImpl.mLp);
        notificationShadeWindowControllerImpl.onThemeChanged();
        if (notificationShadeWindowControllerImpl.mKeyguardViewMediator.isShowingAndNotOccluded()) {
            i = 1;
            notificationShadeWindowControllerImpl.setKeyguardShowing(true);
        } else {
            i = 1;
        }
        NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda7 notificationShadeWindowControllerImpl$$ExternalSyntheticLambda7 = new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda7(notificationShadeWindowControllerImpl, i);
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
                    SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl2 = secNotificationShadeWindowControllerHelperImpl;
                    bool.getClass();
                    SecNotificationShadeWindowControllerHelperImpl.access$setScreenOrientation(secNotificationShadeWindowControllerHelperImpl2, bool.booleanValue());
                }
            });
        }
        SecNotificationShadeWindowControllerHelperImpl$attach$2 secNotificationShadeWindowControllerHelperImpl$attach$2 = new SecNotificationShadeWindowControllerHelperImpl$attach$2(secNotificationShadeWindowControllerHelperImpl);
        KeyguardWallpaperController keyguardWallpaperController = (KeyguardWallpaperController) secNotificationShadeWindowControllerHelperImpl.keyguardWallpaper;
        keyguardWallpaperController.getClass();
        Log.d("KeyguardWallpaperController", "setNoSensorConsumer() consumer:" + secNotificationShadeWindowControllerHelperImpl$attach$2);
        keyguardWallpaperController.mNoSensorConsumer = secNotificationShadeWindowControllerHelperImpl$attach$2;
        keyguardWallpaperController.disableRotateIfNeeded();
        Log.d("KeyguardWallpaperController", "setWideColorGamutConsumer() consumer:" + notificationShadeWindowControllerImpl$$ExternalSyntheticLambda7);
        int i16 = StatusBarConnectedDisplays.$r8$clinit;
        final StatusBarWindowControllerImpl statusBarWindowControllerImpl = (StatusBarWindowControllerImpl) ((StatusBarWindowController) this.mStatusBarWindowControllerStore.getDefaultDisplay());
        statusBarWindowControllerImpl.getClass();
        Trace.beginSection("StatusBarWindowController.getBarLayoutParams");
        WindowManager.LayoutParams barLayoutParamsForRotation = statusBarWindowControllerImpl.getBarLayoutParamsForRotation(statusBarWindowControllerImpl.mContext.getDisplay().getRotation());
        barLayoutParamsForRotation.paramsForRotation = new WindowManager.LayoutParams[4];
        for (int i17 = 0; i17 <= 3; i17++) {
            barLayoutParamsForRotation.paramsForRotation[i17] = statusBarWindowControllerImpl.getBarLayoutParamsForRotation(i17);
        }
        statusBarWindowControllerImpl.mLp = barLayoutParamsForRotation;
        Trace.endSection();
        try {
            statusBarWindowControllerImpl.mWindowManager.addView(statusBarWindowControllerImpl.mStatusBarWindowView, statusBarWindowControllerImpl.mLp);
        } catch (WindowManager.InvalidDisplayException e2) {
            Log.e("StatusBarWindowController", "Unable to add view to WindowManager. Display with id " + statusBarWindowControllerImpl.mContext.getDisplayId() + " doesn't exist anymore.", e2);
        }
        statusBarWindowControllerImpl.mLpChanged.copyFrom(statusBarWindowControllerImpl.mLp);
        ((StatusBarContentInsetsProviderImpl) statusBarWindowControllerImpl.mContentInsetsProvider).addCallback(new StatusBarContentInsetsChangedListener() { // from class: com.android.systemui.statusbar.window.StatusBarWindowControllerImpl$$ExternalSyntheticLambda2
            @Override // com.android.systemui.statusbar.layout.StatusBarContentInsetsChangedListener
            public final void onStatusBarContentInsetsChanged() {
                statusBarWindowControllerImpl.calculateStatusBarLocationsForAllRotations();
            }
        });
        statusBarWindowControllerImpl.calculateStatusBarLocationsForAllRotations();
        statusBarWindowControllerImpl.mIsAttached = true;
        statusBarWindowControllerImpl.apply(statusBarWindowControllerImpl.mCurrentState);
        AnonymousClass19 anonymousClass19 = this.mActivityTransitionAnimatorCallback;
        ActivityTransitionAnimator activityTransitionAnimator = this.mActivityTransitionAnimator;
        activityTransitionAnimator.callback = anonymousClass19;
        activityTransitionAnimator.listeners.add(this.mActivityTransitionAnimatorListener);
        NotificationRemoteInputManager notificationRemoteInputManager = this.mRemoteInputManager;
        RemoteInputController remoteInputController = notificationRemoteInputManager.mRemoteInputController;
        if (remoteInputController != null) {
            Objects.requireNonNull(notificationShadeWindowController);
            remoteInputController.mCallbacks.add(notificationShadeWindowController);
        } else {
            ((ArrayList) notificationRemoteInputManager.mControllerCallbacks).add(notificationShadeWindowController);
        }
        Lazy lazy2 = this.mNotificationActivityStarterLazy;
        this.mGutsManager.mNotificationActivityStarter = (NotificationActivityStarter) lazy2.get();
        Lazy lazy3 = this.mPresenterLazy;
        ((BaseShadeControllerImpl) shadeController).notifPresenter = (NotificationPresenter) lazy3.get();
        this.mNotificationsController.initialize((NotificationPresenter) lazy3.get(), this.mNotifListContainer, (NotificationActivityStarter) lazy2.get());
        this.mWindowRootViewVisibilityInteractor.notificationPresenter = (NotificationPresenter) lazy3.get();
        if (NotiRune.NOTI_SUBSCREEN_ALL) {
            SubscreenNotificationController subscreenNotificationController = (SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class);
            this.mSubscreenNotificationController = subscreenNotificationController;
            NotificationActivityStarter notificationActivityStarter = (NotificationActivityStarter) lazy2.get();
            SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationController.mDeviceModel;
            if (subscreenDeviceModelParent != null) {
                subscreenDeviceModelParent.mNotificationActivityStarter = notificationActivityStarter;
            }
        }
        if ((registerStatusBarResultRegisterStatusBar.mTransientBarTypes & WindowInsets.Type.statusBars()) != 0) {
            StatusBarModePerDisplayRepositoryImpl statusBarModePerDisplayRepositoryImpl = (StatusBarModePerDisplayRepositoryImpl) ((StatusBarModePerDisplayRepository) statusBarModeRepositoryStore.getDefaultDisplay());
            statusBarModePerDisplayRepositoryImpl.getClass();
            CentralSurfaces centralSurfaces = (CentralSurfaces) Dependency.sDependency.getDependencyInner(CentralSurfaces.class);
            if (centralSurfaces != null) {
                ((CentralSurfacesImpl) centralSurfaces).mNoAnimationOnNextBarModeChange = true;
            }
            statusBarModePerDisplayRepositoryImpl._isTransientShown.updateState(null, Boolean.TRUE);
        }
        this.mCommandQueueCallbacks.getClass();
        this.mCommandQueueCallbacks.getClass();
        int size = registerStatusBarResultRegisterStatusBar.mIcons.size();
        for (int i18 = 0; i18 < size; i18++) {
            commandQueue.setIcon((String) registerStatusBarResultRegisterStatusBar.mIcons.keyAt(i18), (StatusBarIcon) registerStatusBarResultRegisterStatusBar.mIcons.valueAt(i18));
        }
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
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                if (((KeyguardStateControllerImpl) centralSurfacesImpl.mKeyguardStateController).mKeyguardGoingAway) {
                    return;
                }
                LightRevealScrim lightRevealScrim3 = centralSurfacesImpl.mLightRevealScrim;
                if (lightRevealScrim3.revealAmount != 1.0f) {
                    Log.e("CentralSurfaces", "Keyguard is done going away, but someone left the light reveal scrim at reveal amount: " + lightRevealScrim3.revealAmount);
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
        AnonymousClass17 anonymousClass17 = this.mStateListener;
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.mStatusBarStateController;
        synchronized (statusBarStateControllerImpl.mListeners) {
            statusBarStateControllerImpl.addListenerInternalLocked(anonymousClass17, 0);
        }
        BiometricUnlockController biometricUnlockController = (BiometricUnlockController) this.mBiometricUnlockControllerLazy.get();
        this.mBiometricUnlockController = biometricUnlockController;
        ((HashSet) biometricUnlockController.mBiometricUnlockEventsListeners).add(new AnonymousClass6());
        this.mSecLockIconView = (ViewGroup) getNotificationShadeWindowViewController().mView.findViewById(R.id.sec_lock_icon_view);
        this.mKeyguardViewMediator.registerCentralSurfaces$1(this, this.mShadeSurface, this.mShadeExpansionStateManager, this.mBiometricUnlockController, this.mStackScroller);
        keyguardStateControllerImpl.addCallback(this.mKeyguardStateControllerCallback);
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager;
        keyguardIndicationController3.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        BiometricUnlockController biometricUnlockController2 = this.mBiometricUnlockController;
        biometricUnlockController2.mKeyguardViewController = statusBarKeyguardViewManager;
        if (!SafeUIState.isSysUiSafeModeEnabled()) {
            biometricUnlockController2.mUpdateMonitor.registerPreCallback(biometricUnlockController2);
        }
        RemoteInputController remoteInputController2 = notificationRemoteInputManager.mRemoteInputController;
        if (remoteInputController2 != null) {
            Objects.requireNonNull(statusBarKeyguardViewManager);
            remoteInputController2.mCallbacks.add(statusBarKeyguardViewManager);
        } else {
            ((ArrayList) notificationRemoteInputManager.mControllerCallbacks).add(statusBarKeyguardViewManager);
        }
        if (LsRune.SECURITY_DEFAULT_LANDSCAPE) {
            DeviceState.setLandscapeDefaultRotation();
        }
        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
            DeviceState.setInDisplayFingerprintSensorPosition(this.mContext.getResources().getDisplayMetrics());
        }
        Trace.endSection();
        keyguardUpdateMonitor3.registerCallback(this.mUpdateCallback);
        DozeServiceHost dozeServiceHost = this.mDozeServiceHost;
        NotificationShadeWindowViewController notificationShadeWindowViewController3 = getNotificationShadeWindowViewController();
        View view = this.mAmbientIndicationContainer;
        dozeServiceHost.mCentralSurfaces = this;
        dozeServiceHost.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        dozeServiceHost.mNotificationShadeWindowViewController = notificationShadeWindowViewController3;
        dozeServiceHost.mNotificationPanelViewController = shadeSurface;
        dozeServiceHost.mAmbientIndicationContainer = view;
        LightRevealScrim lightRevealScrim3 = this.mLightRevealScrim;
        if (lightRevealScrim3 != null && !LsRune.AOD_LIGHT_REVEAL) {
            lightRevealScrim3.setVisibility(8);
        }
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        LifecycleRegistry lifecycleRegistry = this.mLifecycle;
        this.mBatteryController.observe(lifecycleRegistry, this.mBatteryStateChangeCallback);
        lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
        this.mAccessibilityFloatingMenuController.init();
        int i19 = StatusBarConnectedDisplays.$r8$clinit;
        final int i20 = registerStatusBarResultRegisterStatusBar.mDisabledFlags1;
        final int i21 = registerStatusBarResultRegisterStatusBar.mDisabledFlags2;
        Runnable runnable2 = new Runnable() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                int i22 = i20;
                int i23 = i21;
                UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
                centralSurfacesImpl.getClass();
                int[] disableFlags = new int[2];
                try {
                    disableFlags = centralSurfacesImpl.mBarService.getDisableFlags((IBinder) null, -1);
                    Log.d("CentralSurfaces", "QUICK_RELOAD_DISABLE_FLAGS_ON_INIT sec(" + disableFlags[0] + "," + disableFlags[1] + ") ged(" + i22 + "," + i23 + ")");
                } catch (RemoteException unused2) {
                    Log.e("CentralSurfaces", "addPostInitTask failed by mBarService.getDisableFlags");
                    disableFlags[0] = i22;
                    disableFlags[1] = i23;
                }
                int i24 = disableFlags[0];
                int i25 = disableFlags[1];
                int i26 = StatusBarConnectedDisplays.$r8$clinit;
                RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                centralSurfacesImpl.mCommandQueue.disable(centralSurfacesImpl.mDisplayId, i24, i25, false);
                try {
                    Binder binder = new Binder();
                    centralSurfacesImpl.mBarService.disable(2097152, binder, centralSurfacesImpl.mContext.getPackageName());
                    centralSurfacesImpl.mBarService.disable(0, binder, centralSurfacesImpl.mContext.getPackageName());
                } catch (RemoteException e3) {
                    e3.rethrowFromSystemServer();
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
        ((SysuiStatusBarStateController) mdmOverlayContainer.mStatusBarStateControllerLazy.get()).addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.mdm.MdmOverlayContainer.1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i22) {
                MdmOverlayContainer mdmOverlayContainer2 = MdmOverlayContainer.this;
                if (mdmOverlayContainer2.mPreviousState == 2 && i22 == 1) {
                    mdmOverlayContainer2.updateMdmPolicy();
                }
                mdmOverlayContainer2.mPreviousState = i22;
            }
        });
        this.mContext.sendBroadcast(new Intent("com.samsung.systemui.statusbar.STARTED"));
        if (LsRune.AOD_LIGHT_REVEAL) {
            final SecLightRevealScrimHelper secLightRevealScrimHelper = this.mSecLightRevealScrimHelper;
            final int i22 = 0;
            final Function0 function0 = new Function0(this) { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda21
                public final /* synthetic */ CentralSurfacesImpl f$0;

                {
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    switch (i22) {
                        case 0:
                            return this.f$0.mCurrentDisplaySize;
                        default:
                            return Integer.valueOf(this.f$0.mDisplay.getRotation());
                    }
                }
            };
            final int i23 = 1;
            final Function0 function02 = new Function0(this) { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda21
                public final /* synthetic */ CentralSurfacesImpl f$0;

                {
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    switch (i23) {
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
                    SecLightRevealScrimHelper secLightRevealScrimHelper2 = secLightRevealScrimHelper;
                    Point point = (Point) function0.invoke();
                    int iIntValue = ((Number) function02.invoke()).intValue();
                    secLightRevealScrimHelper2.getClass();
                    if (intent.getIntExtra("info", -1) != 11) {
                        Log.d("SecLightRevealScrimHelper", "updateDoubleTap no double tap");
                        return;
                    }
                    Point point2 = secLightRevealScrimHelper2.physicalDisplaySize;
                    float fMin = Math.min(point2.x, point2.y) / Math.min(point.x, point.y);
                    float[] floatArrayExtra = intent.getFloatArrayExtra("location");
                    if (floatArrayExtra != null && floatArrayExtra.length == 2) {
                        if (iIntValue == 0) {
                            secLightRevealScrimHelper2.secRevealDoubleTapX = floatArrayExtra[0] / fMin;
                            secLightRevealScrimHelper2.secRevealDoubleTapY = floatArrayExtra[1] / fMin;
                        } else if (iIntValue == 1) {
                            secLightRevealScrimHelper2.secRevealDoubleTapX = floatArrayExtra[1] / fMin;
                            secLightRevealScrimHelper2.secRevealDoubleTapY = point.y - (floatArrayExtra[0] / fMin);
                        } else if (iIntValue == 2) {
                            secLightRevealScrimHelper2.secRevealDoubleTapX = point.x - (floatArrayExtra[0] / fMin);
                            secLightRevealScrimHelper2.secRevealDoubleTapY = point.y - (floatArrayExtra[1] / fMin);
                        } else if (iIntValue == 3) {
                            secLightRevealScrimHelper2.secRevealDoubleTapX = point.x - (floatArrayExtra[1] / fMin);
                            secLightRevealScrimHelper2.secRevealDoubleTapY = floatArrayExtra[0] / fMin;
                        }
                    }
                    SecLightRevealScrimHelper.SecCircleReveal secCircleReveal = secLightRevealScrimHelper2.secCircleReveal;
                    if (secCircleReveal != null) {
                        secCircleReveal.centerX = secLightRevealScrimHelper2.secRevealDoubleTapX;
                        secCircleReveal.centerY = secLightRevealScrimHelper2.secRevealDoubleTapY;
                    }
                    float f = secLightRevealScrimHelper2.secRevealDoubleTapX;
                    float f2 = secLightRevealScrimHelper2.secRevealDoubleTapY;
                    int i24 = point.x;
                    int i25 = point.y;
                    Point point3 = secLightRevealScrimHelper2.physicalDisplaySize;
                    int i26 = point3.x;
                    int i27 = point3.y;
                    StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("updateDoubleTap: secRevealDoubleTapX=", f, " secRevealDoubleTapY=", f2, " currentDisplaySize.x=");
                    ViewPager$$ExternalSyntheticOutline0.m(sbM, i24, " currentDisplaySize.y=", i25, " initialDisplaySize.x=");
                    ViewPager$$ExternalSyntheticOutline0.m(sbM, i26, " initialDisplaySize.y=", i27, " screenSizeRatio=");
                    sbM.append(fMin);
                    sbM.append(" rotation=");
                    sbM.append(iIntValue);
                    Log.i("SecLightRevealScrimHelper", sbM.toString());
                }
            }, intentFilter, null, UserHandle.ALL, 0, null, 48);
        }
    }

    public final void updateBubblesVisibility() {
        final StatusBarMode statusBarMode = (StatusBarMode) ((StatusBarModePerDisplayRepositoryImpl) ((StatusBarModePerDisplayRepository) this.mStatusBarModeRepository.getDefaultDisplay())).statusBarMode.$$delegate_0.getValue();
        this.mBubblesOptional.ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda17
            /* JADX WARN: Removed duplicated region for block: B:10:0x002e  */
            @Override // java.util.function.Consumer
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void accept(Object obj) {
                boolean z;
                int i = 2;
                CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                StatusBarMode statusBarMode2 = statusBarMode;
                Bubbles bubbles = (Bubbles) obj;
                UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
                centralSurfacesImpl.getClass();
                if (statusBarMode2 == StatusBarMode.LIGHTS_OUT || statusBarMode2 == StatusBarMode.LIGHTS_OUT_TRANSPARENT || centralSurfacesImpl.mStatusBarWindowState == 2) {
                    NavBarHelper navBarHelper = (NavBarHelper) centralSurfacesImpl.mNavBarHelperLazy.get();
                    int i2 = centralSurfacesImpl.mDisplayId;
                    navBarHelper.getClass();
                    z = new NavBarHelper.CurrentSysuiState(navBarHelper, i2).mWindowState != 2;
                }
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubbles;
                BubbleController.this.mMainExecutor.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda10(bubblesImpl, z, i));
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
        boolean zIsVisible = this.mKeyguardStateController.isVisible();
        DozeParameters dozeParameters = this.mDozeParameters;
        boolean z2 = zIsVisible || (this.mDozing && dozeParameters.mScreenOffAnimationController.shouldDelayKeyguardShow());
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
        boolean zIsWakeAndUnlock = this.mBiometricUnlockController.isWakeAndUnlock();
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z3 = keyguardStateControllerImpl.mShowing && !keyguardStateControllerImpl.mOccluded;
        boolean z4 = this.mDozeServiceHost.mDozingRequested;
        ScreenLifecycle screenLifecycle = this.mScreenLifecycle;
        boolean z5 = z4 && (!this.mDeviceInteractive || (isGoingToSleep() && (screenLifecycle.mScreenState == 0 || z3)));
        boolean z6 = keyguardStateControllerImpl.mOccluded && ((i2 = this.mWakefulnessLifecycle.mWakefulness) == 1 || i2 == 2);
        SysuiStatusBarStateController sysuiStatusBarStateController = this.mStatusBarStateController;
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) sysuiStatusBarStateController;
        boolean z7 = ((!statusBarStateControllerImpl.mKeyguardRequested && !z5) || zIsWakeAndUnlock || z6) ? false : true;
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
        CarrierTextManager$$ExternalSyntheticOutline0.m(sb2, zIsWakeAndUnlock, " isWakingAndOccluded ", z6, "CentralSurfaces");
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
                function1.mo781invoke(Long.valueOf(j));
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
        boolean zIsGoingToSleep = isGoingToSleep();
        DozeParameters dozeParameters = this.mDozeParameters;
        boolean z = !(this.mDeviceInteractive || this.mDozeServiceHost.mPulsing) || (zIsGoingToSleep && !dozeParameters.mControlScreenOffAnimation);
        boolean zIsGoingToSleep2 = isGoingToSleep();
        boolean z2 = !dozeParameters.mControlScreenOffAnimation;
        boolean z3 = !this.mDeviceInteractive;
        boolean z4 = !this.mDozeServiceHost.mPulsing;
        ShadeLogger shadeLogger = this.mShadeLogger;
        shadeLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        ShadeLogger$$ExternalSyntheticLambda0 shadeLogger$$ExternalSyntheticLambda0 = new ShadeLogger$$ExternalSyntheticLambda0(5);
        LogBuffer logBuffer = shadeLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = zIsGoingToSleep2;
        logMessageImpl.bool3 = z2;
        logMessageImpl.bool4 = z3;
        logMessageImpl.str1 = String.valueOf(z4);
        logBuffer.commit(logMessageObtain);
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
            int iMin = Math.min(point2.x, point2.y);
            int iMin2 = Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);
            f = iMin / iMin2;
            Log.i("SecLightRevealScrimHelper", "getPositionCorrectionRatio screenSizeRatio=" + f + " physicalScreenSize.x=" + secLightRevealScrimHelper.physicalDisplaySize.x + " baseWidthPixels = " + iMin + " currentWidthPixels = " + iMin2);
        } catch (Exception e) {
            Log.i("SecLightRevealScrimHelper", "getPositionCorrectionRatio exception = " + e);
            f = 1.0f;
        }
        secLightRevealScrimHelper.powerKeyYPos = (int) (secLightRevealScrimHelper.context.getResources().getDimensionPixelSize(17105835) / f);
        int i = point.x;
        secLightRevealScrimHelper.secRevealCenterX = i / 2.0f;
        int i2 = point.y;
        secLightRevealScrimHelper.secRevealCenterY = i2 / 2.0f;
        float fHypot = (float) Math.hypot(i, i2);
        secLightRevealScrimHelper.secCircleReveal = new SecLightRevealScrimHelper.SecCircleReveal(secLightRevealScrimHelper.secRevealCenterX, secLightRevealScrimHelper.secRevealCenterY, fHypot / 4, fHypot / 2);
        float f2 = secLightRevealScrimHelper.secRevealCenterX;
        float f3 = secLightRevealScrimHelper.secRevealCenterY;
        int i3 = point.x;
        int i4 = point.y;
        int i5 = secLightRevealScrimHelper.powerKeyYPos;
        StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("updateResources: secRevealCenterX=", f2, " secRevealCenterY=", f3, " currentDisplaySize.x=");
        ViewPager$$ExternalSyntheticOutline0.m(sbM, i3, " currentDisplaySize.y=", i4, " powerKeyY=");
        sbM.append(i5);
        sbM.append(" radius=");
        sbM.append(fHypot);
        Log.i("SecLightRevealScrimHelper", sbM.toString());
        this.mPowerButtonReveal = new PowerButtonReveal(this.mSecLightRevealScrimHelper.powerKeyYPos);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0031  */
    @Override // com.android.systemui.statusbar.phone.CentralSurfaces
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateScrimController() {
        boolean z;
        int i;
        int i2 = SceneContainerFlag.$r8$clinit;
        Trace.beginSection("CentralSurfaces#updateScrimController");
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z2 = false;
        boolean z3 = true;
        if (keyguardStateControllerImpl.mShowing) {
            if (!this.mBiometricUnlockController.isWakeAndUnlock() && !keyguardStateControllerImpl.mKeyguardFadingAway && !keyguardStateControllerImpl.mKeyguardGoingAway) {
                KeyguardViewMediator keyguardViewMediator = this.mKeyguardViewMediator;
                if (keyguardViewMediator.requestedShowSurfaceBehindKeyguard() || keyguardViewMediator.isAnimatingBetweenKeyguardAndSurfaceBehind()) {
                }
            }
            z = true;
        } else {
            z = false;
        }
        boolean z4 = keyguardStateControllerImpl.mShowing && this.mKeyguardUpdateMonitor.mIsDreaming && !z;
        BiometricUnlockController biometricUnlockController = this.mBiometricUnlockController;
        boolean z5 = biometricUnlockController.mAuthenticatedBioSourceType != BiometricSourceType.FACE ? biometricUnlockController.isWakeAndUnlock() || biometricUnlockController.mMode == 5 : biometricUnlockController.mKeyguardBypassController.getBypassEnabled() && (biometricUnlockController.isWakeAndUnlock() || biometricUnlockController.mMode == 5);
        ScrimController scrimController = this.mScrimController;
        scrimController.mExpansionAffectsAlpha = !z5;
        boolean zIsVisibleState = this.mAlternateBouncerInteractor.isVisibleState();
        AnonymousClass14 anonymousClass14 = this.mUnlockScrimCallback;
        if (zIsVisibleState) {
            if ((keyguardStateControllerImpl.mOccluded && !this.mShadeSurface.isPanelExpanded()) || ((i = this.mState) != 0 && i != 2 && this.mTransitionToFullShadeProgress <= 0.0f)) {
                scrimController.legacyTransitionTo(ScrimState.KEYGUARD);
            }
            anonymousClass14.onFinished();
        } else if (this.mBouncerShowing && !z) {
            scrimController.legacyTransitionTo(this.mStatusBarKeyguardViewManager.primaryBouncerNeedsScrimming() ? ScrimState.BOUNCER_SCRIMMED : ScrimState.BOUNCER);
            scrimController.mSecLsScrimControlHelper.setScrimAlphaForKeyguard(false);
        } else if (this.mState == 2) {
            scrimController.legacyTransitionTo(ScrimState.SHADE_LOCKED);
        } else if (this.mDozeServiceHost.mPulsing) {
            ScrimState scrimState = ScrimState.PULSING;
            DozeScrimController.AnonymousClass1 anonymousClass1 = this.mDozeScrimController.mScrimCallback;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            scrimController.internalTransitionTo(anonymousClass1, scrimState);
        } else {
            KeyguardFastBioUnlockController keyguardFastBioUnlockController = (KeyguardFastBioUnlockController) Dependency.sDependency.getDependencyInner(KeyguardFastBioUnlockController.class);
            int mode = keyguardFastBioUnlockController.getMode();
            int i3 = KeyguardFastBioUnlockController.MODE_FLAG_ENABLED | KeyguardFastBioUnlockController.MODE_FLAG_STARTED_DISPLAY_DOZE_OR_OFF;
            if (mode != i3 && mode != (KeyguardFastBioUnlockController.MODE_FLAG_FRAME_REQUEST | i3) && mode != (i3 | KeyguardFastBioUnlockController.MODE_FLAG_UNLOCK_ANIM_AOD_FULLSCREEN)) {
                z3 = false;
            }
            if (!z3 || (((BiometricUnlockController) keyguardFastBioUnlockController.biometricUnlockControllerLazy.get()).mMode != 6 && !keyguardFastBioUnlockController.needsBlankScreen)) {
                z2 = z3;
            }
            if (KeyguardFastBioUnlockController.DEBUG) {
                KeyguardFastBioUnlockController.logD("isAODScrimState " + z2);
            }
            if (z2 || (this.mDozing && !z)) {
                scrimController.legacyTransitionTo(ScrimState.AOD);
                anonymousClass14.onFinished();
            } else if (this.mIsIdleOnCommunal) {
                if (z4) {
                    scrimController.legacyTransitionTo(ScrimState.GLANCEABLE_HUB_OVER_DREAM);
                } else {
                    scrimController.legacyTransitionTo(ScrimState.GLANCEABLE_HUB);
                }
            } else if (!keyguardStateControllerImpl.mShowing || keyguardStateControllerImpl.mOccluded || z) {
                ScrimState scrimState2 = ScrimState.UNLOCKED;
                RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                scrimController.internalTransitionTo(anonymousClass14, scrimState2);
            } else {
                scrimController.legacyTransitionTo(ScrimState.KEYGUARD);
            }
        }
        LightRevealScrim lightRevealScrim = this.mLightRevealScrim;
        if (lightRevealScrim != null && !LsRune.AOD_LIGHT_REVEAL) {
            lightRevealScrim.setVisibility(8);
        }
        Trace.endSection();
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
