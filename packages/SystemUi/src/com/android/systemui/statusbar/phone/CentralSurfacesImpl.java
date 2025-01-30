package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.IWallpaperManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.StatusBarManager;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.metrics.LogMaker;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.dreams.IDreamManager;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.EventLog;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.widget.DateTimeView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import android.window.OnBackInvokedCallback;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat$$ExternalSyntheticLambda0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.policy.SystemBarUtils;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.RegisterStatusBarResult;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.keyguard.KeyguardBouncerContainer;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.keyguard.ViewMediatorCallback;
import com.android.keyguard.punchhole.VIDirector$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.bubbles.Bubbles;
import com.android.p038wm.shell.startingsurface.StartingWindowController;
import com.android.settingslib.Utils;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.AutoReinflateContainer;
import com.android.systemui.BasicRune;
import com.android.systemui.CoreStartable;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.DisplayCutoutBaseView$$ExternalSyntheticOutline0;
import com.android.systemui.InitController;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.Rune;
import com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.biometrics.AuthRippleController;
import com.android.systemui.bixby2.SystemUICommandActionHandler;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.camera.CameraIntents;
import com.android.systemui.charging.WiredChargingRippleController;
import com.android.systemui.charging.WirelessChargingAnimation;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.classifier.FalsingCollectorImpl;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dock.DockManager;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.fragments.ExtensionFragmentListener;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardSysDumpTrigger;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.keyguard.p009ui.binder.KeyguardBouncerViewBinder;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.log.SecPanelLogger;
import com.android.systemui.log.SecPanelLoggerImpl;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.mdm.MdmOverlayContainer;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.ScreenPinningNotify;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.bandaid.BandAidPackFactory;
import com.android.systemui.navigationbar.interactor.ButtonOrderInteractor;
import com.android.systemui.navigationbar.interactor.ButtonOrderInteractor$addCallback$2;
import com.android.systemui.navigationbar.interactor.ButtonPositionInteractor;
import com.android.systemui.navigationbar.interactor.ButtonPositionInteractor$addCallback$2;
import com.android.systemui.navigationbar.interactor.ButtonToHideKeyboardInteractor;
import com.android.systemui.navigationbar.interactor.ButtonToHideKeyboardInteractor$addCallback$2;
import com.android.systemui.navigationbar.interactor.CoverDisplayWidgetInteractor;
import com.android.systemui.navigationbar.interactor.CoverDisplayWidgetInteractor$addCallback$2;
import com.android.systemui.navigationbar.interactor.DeviceStateInteractor;
import com.android.systemui.navigationbar.interactor.DeviceStateInteractor.CoverTask;
import com.android.systemui.navigationbar.interactor.EdgeBackGesturePolicyInteractor;
import com.android.systemui.navigationbar.interactor.EdgeBackGesturePolicyInteractor$addCallback$2$1;
import com.android.systemui.navigationbar.interactor.GestureNavigationSettingsInteractor;
import com.android.systemui.navigationbar.interactor.InteractorFactory;
import com.android.systemui.navigationbar.interactor.KnoxStateMonitorInteractor;
import com.android.systemui.navigationbar.interactor.KnoxStateMonitorInteractor$addCallback$2;
import com.android.systemui.navigationbar.interactor.NavigationModeInteractor;
import com.android.systemui.navigationbar.interactor.NavigationModeInteractor$addCallback$2;
import com.android.systemui.navigationbar.interactor.OneHandModeInteractor;
import com.android.systemui.navigationbar.interactor.OneHandModeInteractor$addCallback$2;
import com.android.systemui.navigationbar.interactor.OpenThemeInteractor;
import com.android.systemui.navigationbar.interactor.OpenThemeInteractor$addCallback$2;
import com.android.systemui.navigationbar.interactor.OpenThemeInteractor$addCallback$5;
import com.android.systemui.navigationbar.interactor.PackageRemovedInteractor;
import com.android.systemui.navigationbar.interactor.PackageRemovedInteractor$addCallback$2;
import com.android.systemui.navigationbar.interactor.RotationLockInteractor;
import com.android.systemui.navigationbar.interactor.RotationLockInteractor$addCallback$2;
import com.android.systemui.navigationbar.interactor.SettingsSoftResetInteractor;
import com.android.systemui.navigationbar.interactor.SettingsSoftResetInteractor$addCallback$2;
import com.android.systemui.navigationbar.interactor.TaskBarInteractor;
import com.android.systemui.navigationbar.interactor.TaskBarInteractor$addCallback$2;
import com.android.systemui.navigationbar.interactor.TaskBarInteractor$addCallback$5;
import com.android.systemui.navigationbar.interactor.TaskBarInteractor$addCallback$7;
import com.android.systemui.navigationbar.interactor.UseThemeDefaultInteractor;
import com.android.systemui.navigationbar.interactor.UseThemeDefaultInteractor$addCallback$2;
import com.android.systemui.navigationbar.layout.NavBarCoverLayoutParams;
import com.android.systemui.navigationbar.plugin.PluginBarInteractionManager;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$20;
import com.android.systemui.noticenter.NotiCenterPlugin;
import com.android.systemui.p014qp.util.SubscreenUtil;
import com.android.systemui.p016qs.QSFragment;
import com.android.systemui.p016qs.SecQSPanelController;
import com.android.systemui.p016qs.SecQuickQSPanelController;
import com.android.systemui.p016qs.buttons.QSTooltipWindow;
import com.android.systemui.pluginlock.PluginLockDelegateApp;
import com.android.systemui.pluginlock.PluginLockManagerImpl;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.pluginlock.listener.KeyguardListener$SPlugin;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.OverlayPlugin;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginDependencyProvider;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.p013qs.InterfaceC1922QS;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.popup.SamsungScreenPinningRequest;
import com.android.systemui.privacy.PrivacyType;
import com.android.systemui.recents.ScreenPinningRequest;
import com.android.systemui.scrim.ScrimDrawable;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.DisplayTrackerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.settings.brightness.BrightnessSliderController;
import com.android.systemui.shade.CameraLauncher;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda12;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda6;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.NotificationShadeWindowViewController;
import com.android.systemui.shade.NotificationShadeWindowViewController.C24461;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.shade.RunnableC2460x13d85fb4;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.shade.ShadeControllerImpl$$ExternalSyntheticLambda0;
import com.android.systemui.shade.ShadeControllerImpl.C24622;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeFullExpansionListener;
import com.android.systemui.shade.ShadeLogger;
import com.android.systemui.shade.ShadeSurface;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shared.launcher.WindowManagerWrapper;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.system.PackageManagerWrapper;
import com.android.systemui.statusbar.AutoHideUiElement;
import com.android.systemui.statusbar.BackDropView;
import com.android.systemui.statusbar.CircleReveal;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.KeyboardShortcuts;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.KeyguardShortcutManager;
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
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.NotificationShelfController;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.PowerButtonReveal;
import com.android.systemui.statusbar.PulseExpansionHandler;
import com.android.systemui.statusbar.SecLightRevealScrimHelper;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.statusbar.core.StatusBarInitializer;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
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
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.logging.NotificationLogger$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotifRemoteViewCache;
import com.android.systemui.statusbar.notification.row.NotifRemoteViewCacheImpl;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.ViewOnClickListenerC2949xbae1b0c7;
import com.android.systemui.statusbar.phone.BarTransitions;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.LetterboxAppearanceCalculator;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ScrimController.C31063;
import com.android.systemui.statusbar.phone.StatusBarIconList;
import com.android.systemui.statusbar.phone.dagger.CentralSurfacesComponent;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentComponent;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.BrightnessMirrorController;
import com.android.systemui.statusbar.policy.CastControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DataSaverControllerImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.statusbar.policy.ExtensionControllerImpl;
import com.android.systemui.statusbar.policy.HotspotControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherController;
import com.android.systemui.statusbar.policy.LocationControllerImpl;
import com.android.systemui.statusbar.policy.NextAlarmControllerImpl;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.statusbar.policy.SBluetoothControllerImpl;
import com.android.systemui.statusbar.policy.SecurityControllerImpl;
import com.android.systemui.statusbar.policy.SensorPrivacyControllerImpl;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.statusbar.window.StatusBarWindowStateListener;
import com.android.systemui.user.data.model.UserSwitcherSettingsModel;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.RingerModeTrackerImpl;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.WallpaperController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.concurrency.MessageRouter;
import com.android.systemui.util.concurrency.MessageRouterImpl;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.android.systemui.volume.VolumeComponent;
import com.android.systemui.volume.VolumeDialogComponent;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.view.KeyguardImageWallpaper;
import com.android.systemui.wallpaper.view.SystemUIWallpaperBase;
import com.android.systemui.widget.SystemUIWidgetUtil;
import com.samsung.android.desktopsystemui.sharedlib.system.ActivityManagerWrapper;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.view.SemWindowManager;
import com.samsung.systemui.splugins.SPluginListener;
import com.samsung.systemui.splugins.SPluginManager;
import com.samsung.systemui.splugins.navigationbar.ColorSetting;
import com.samsung.systemui.splugins.navigationbar.PluginNavigationBar;
import com.samsung.systemui.splugins.noticenter.PluginNotiCenter;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import com.sec.ims.gls.GlsIntent;
import com.sec.ims.volte2.data.VolteConstants;
import dagger.Lazy;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.ExecutorsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CentralSurfacesImpl implements CoreStartable, CentralSurfaces, SecBrightnessMirrorControllerProvider {
    public AODAmbientWallpaperHelper mAODAmbientWallpaperHelper;
    public final AccessibilityFloatingMenuController mAccessibilityFloatingMenuController;
    public final ActivityLaunchAnimator mActivityLaunchAnimator;
    public final ActivityStarter mActivityStarter;
    public final AlternateBouncerInteractor mAlternateBouncerInteractor;
    public View mAmbientIndicationContainer;
    public int mAppearance;
    public final Lazy mAssistManagerLazy;
    public final AutoHideController mAutoHideController;
    public IStatusBarService mBarService;
    public final BatteryController mBatteryController;
    public BiometricUnlockController mBiometricUnlockController;
    public final Lazy mBiometricUnlockControllerLazy;
    public final SecQpBlurController mBlurController;
    public FrameLayout mBouncerContainer;
    public boolean mBouncerShowing;
    public boolean mBouncerShowingOverDream;
    public BrightnessMirrorController mBrightnessMirrorController;
    public final BrightnessSliderController.Factory mBrightnessSliderFactory;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Optional mBubblesOptional;
    public final Lazy mCameraLauncherLazy;
    public CentralSurfacesComponent mCentralSurfacesComponent;
    public final CentralSurfacesComponent.Factory mCentralSurfacesComponentFactory;
    public final CentralSurfacesImpl$$ExternalSyntheticLambda5 mCheckBarModes;
    public boolean mCloseQsBeforeScreenOff;
    public final SysuiColorExtractor mColorExtractor;
    public final CommandQueue mCommandQueue;
    public CentralSurfacesCommandQueueCallbacks mCommandQueueCallbacks;
    public final ConfigurationController mConfigurationController;
    public final Context mContext;
    public final DemoModeController mDemoModeController;
    public boolean mDeviceInteractive;
    public DevicePolicyManager mDevicePolicyManager;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public final DeviceStateManager mDeviceStateManager;
    public Display mDisplay;
    public int mDisplayId;
    public final DisplayLifecycle mDisplayLifecycle;
    public final DisplayMetrics mDisplayMetrics;
    public final DozeParameters mDozeParameters;
    public final DozeScrimController mDozeScrimController;
    DozeServiceHost mDozeServiceHost;
    public boolean mDozing;
    public final IDreamManager mDreamManager;
    public final ExtensionController mExtensionController;
    public final FalsingCollector mFalsingCollector;
    public final FalsingManager mFalsingManager;
    public final FeatureFlags mFeatureFlags;
    public final Provider mFingerprintManager;
    public final FragmentService mFragmentService;
    public PowerManager.WakeLock mGestureWakeLock;
    public final NotificationGutsManager mGutsManager;
    public final HeadsUpManagerPhone mHeadsUpManager;
    public final PhoneStatusBarPolicy mIconPolicy;
    public final InitController mInitController;
    public int mInteractingWindows;
    public boolean mIsDlsOverlay;
    public boolean mIsFolded;
    public boolean mIsLaunchingActivityOverLockscreen;
    public final boolean mIsShortcutListSearchEnabled;
    public final InteractionJankMonitor mJankMonitor;
    public final KeyguardBypassController mKeyguardBypassController;
    public final KeyguardDismissUtil mKeyguardDismissUtil;
    public final KeyguardIndicationController mKeyguardIndicationController;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardViewMediator mKeyguardViewMediator;
    public final ViewMediatorCallback mKeyguardViewMediatorCallback;
    public final KeyguardWallpaper mKeyguardWallpaper;
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
    public LockscreenWallpaper mLockscreenWallpaper;
    public final Lazy mLockscreenWallpaperLazy;
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
    public NotificationListContainer mNotifListContainer;
    public final NotifRemoteViewCache mNotifRemoteViewCache;
    public NotificationActivityStarter mNotificationActivityStarter;
    public NotificationLaunchAnimatorControllerProvider mNotificationAnimationProvider;
    public final NotificationIconAreaController mNotificationIconAreaController;
    public final NotificationLogger mNotificationLogger;
    public NotificationPanelViewController mNotificationPanelViewController;
    public final Lazy mNotificationShadeDepthControllerLazy;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public NotificationShadeWindowView mNotificationShadeWindowView;
    public NotificationShadeWindowViewController mNotificationShadeWindowViewController;
    public NotificationShelfController mNotificationShelfController;
    public final NotificationsController mNotificationsController;
    public boolean mPanelExpanded;
    public final SecPanelLogger mPanelLogger;
    public PhoneStatusBarViewController mPhoneStatusBarViewController;
    public final PluginDependencyProvider mPluginDependencyProvider;
    public final PluginManager mPluginManager;
    public PowerButtonReveal mPowerButtonReveal;
    public final PowerManager mPowerManager;
    public NotificationPresenter mPresenter;
    public final PulseExpansionHandler mPulseExpansionHandler;
    public SecQSPanelController mQSPanelController;
    QuickSettingsController mQsController;
    public SecQuickQSPanelController mQuickQSPanelController;
    public final NotificationRemoteInputManager mRemoteInputManager;
    public View mReportRejectedTouch;
    public final SamsungScreenPinningRequest mSamsungScreenPinningRequest;
    public final SamsungStatusBarGrayIconHelper mSamsungStatusBarGrayIconHelper;
    public final ScreenLifecycle mScreenLifecycle;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public final ScreenPinningRequest mScreenPinningRequest;
    public final ScrimController mScrimController;
    public SecLightRevealScrimHelper mSecLightRevealScrimHelper;
    public final CentralSurfacesImpl$$ExternalSyntheticLambda5 mSetWallpaperSupportsAmbientMode;
    public final ShadeController mShadeController;
    public final ShadeExpansionStateManager mShadeExpansionStateManager;
    public final ShadeLogger mShadeLogger;
    ShadeSurface mShadeSurface;
    public NotificationStackScrollLayout mStackScroller;
    public NotificationStackScrollLayoutController mStackScrollerController;
    public final Optional mStartingSurfaceOptional;
    public int mState;
    public final StatusBarHideIconsForBouncerManager mStatusBarHideIconsForBouncerManager;
    public final StatusBarInitializer mStatusBarInitializer;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public int mStatusBarMode;
    public final StatusBarSignalPolicy mStatusBarSignalPolicy;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public LogMaker mStatusBarStateLog;
    public final StatusBarTouchableRegionManager mStatusBarTouchableRegionManager;
    public PhoneStatusBarTransitions mStatusBarTransitions;
    public final StatusBarWindowController mStatusBarWindowController;
    public SubscreenNotificationController mSubscreenNotificationController;
    public final KeyguardSysDumpTrigger mSysDumpTrigger;
    public final QSTooltipWindow mToolTipWindow;
    public boolean mTransientShown;
    public final Executor mUiBgExecutor;
    public UiModeManager mUiModeManager;
    public final UserInfoControllerImpl mUserInfoControllerImpl;
    public final UserSwitcherController mUserSwitcherController;
    public final UserTracker mUserTracker;
    public boolean mVisible;
    public boolean mVisibleToUser;
    public final VolumeComponent mVolumeComponent;
    public boolean mWakeUpComingFromTouch;
    public final NotificationWakeUpCoordinator mWakeUpCoordinator;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final WallpaperController mWallpaperController;
    public final WallpaperManager mWallpaperManager;
    public boolean mWallpaperSupported;
    public static final UiEventLogger sUiEventLogger = new UiEventLoggerImpl();
    public static final Integer[] IGNORED_EXT_KEYCODE = {26, 27, 126, 85, 85, 91, 79, 86, 87, 88, 89, 130, 90, 222, 24, 25, 164};
    public float mTransitionToFullShadeProgress = 0.0f;
    public final C30021 mKeyguardStateControllerCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.1
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
    public int mDisabled1 = 0;
    public int mDisabled2 = 0;
    public boolean mIsBackCallbackRegistered = false;
    protected boolean mUserSetup = false;
    public final LifecycleRegistry mLifecycle = new LifecycleRegistry(this);
    public final CentralSurfacesImpl$$ExternalSyntheticLambda2 mOnColorsChangedListener = new ColorExtractor.OnColorsChangedListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda2
        public final void onColorsChanged(ColorExtractor colorExtractor, int i) {
            CentralSurfacesImpl.this.updateTheme();
        }
    };
    public Boolean mIsKeyDownInDozing = null;
    public int mKeyUpCountInDozing = 0;
    public final CentralSurfacesImpl$$ExternalSyntheticLambda4 mOnBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda4
        @Override // android.window.OnBackInvokedCallback
        public final void onBackInvoked() {
            CentralSurfacesImpl.this.onBackPressed();
        }
    };
    public final C30122 mOnBackAnimationCallback = new OnBackAnimationCallback() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.2
        @Override // android.window.OnBackInvokedCallback
        public final void onBackInvoked() {
            CentralSurfacesImpl.this.onBackPressed();
        }

        @Override // android.window.OnBackAnimationCallback
        public final void onBackProgressed(BackEvent backEvent) {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
            int i = centralSurfacesImpl.mState;
            if (((i == 1 || i == 2 || centralSurfacesImpl.mBouncerShowingOverDream) ? false : true) && ((NotificationPanelViewController) centralSurfacesImpl.mShadeSurface).canBeCollapsed()) {
                float progress = backEvent.getProgress();
                NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) CentralSurfacesImpl.this.mShadeSurface;
                notificationPanelViewController.mCurrentBackProgress = progress;
                notificationPanelViewController.applyBackScaling(progress);
                notificationPanelViewController.mQsController.setClippingBounds();
            }
        }
    };
    public final StringBuilder mLogBuilder = new StringBuilder();
    public final C30279 mBroadcastReceiver = new C30279();
    public final C300310 mDemoReceiver = new BroadcastReceiver(this) { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.10
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            "fake_artwork".equals(intent.getAction());
        }
    };
    final WakefulnessLifecycle.Observer mWakefulnessObserver = new C300411();
    public final C300512 mScreenObserver = new ScreenLifecycle.Observer() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.12
        @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
        public final void onScreenTurnedOff() {
            AssistManager assistManager;
            AlertDialog alertDialog;
            Trace.beginSection("CentralSurfaces#onScreenTurnedOff");
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            FalsingCollectorImpl falsingCollectorImpl = (FalsingCollectorImpl) centralSurfacesImpl.mFalsingCollector;
            falsingCollectorImpl.getClass();
            falsingCollectorImpl.sessionEnd();
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
            centralSurfacesImpl.mFalsingCollector.getClass();
            ((NotificationPanelViewController) centralSurfacesImpl.mShadeSurface).mKeyguardStatusViewController.dozeTimeTick();
        }
    };
    public final C300714 mBannerActionBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.14
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.android.systemui.statusbar.banner_action_cancel".equals(action) || "com.android.systemui.statusbar.banner_action_setup".equals(action)) {
                ((NotificationManager) CentralSurfacesImpl.this.mContext.getSystemService(SubRoom.EXTRA_VALUE_NOTIFICATION)).cancel(5);
                Settings.Secure.putInt(CentralSurfacesImpl.this.mContext.getContentResolver(), "show_note_about_notification_hiding", 0);
                if ("com.android.systemui.statusbar.banner_action_setup".equals(action)) {
                    ((ShadeControllerImpl) CentralSurfacesImpl.this.mShadeController).animateCollapsePanels(1.0f, 0, true, false);
                    CentralSurfacesImpl.this.mContext.startActivity(new Intent("android.settings.ACTION_APP_NOTIFICATION_REDACTION").addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE));
                }
            }
        }
    };
    public final KeyguardUpdateMonitorCallback mUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.16
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBackDropViewShowing(boolean z, boolean z2) {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) centralSurfacesImpl.mNotificationShadeWindowController;
            NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
            notificationShadeWindowState.mediaBackdropShowing = z;
            notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
            if (z2) {
                centralSurfacesImpl.mNotificationShadeWindowController.getClass();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onDreamingStateChanged(boolean z) {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.updateScrimController();
            if (z) {
                CentralSurfacesImpl.m1713$$Nest$mmaybeEscalateHeadsUp(centralSurfacesImpl);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onWallpaperTypeChanged() {
            WindowManager.LayoutParams layoutParams;
            StatusBarWindowController statusBarWindowController = CentralSurfacesImpl.this.mStatusBarWindowController;
            WindowManager.LayoutParams layoutParams2 = statusBarWindowController.mLpChanged;
            if (layoutParams2 == null || (layoutParams = statusBarWindowController.mLp) == null || layoutParams.copyFrom(layoutParams2) == 0) {
                return;
            }
            statusBarWindowController.mWindowManager.updateViewLayout(statusBarWindowController.mStatusBarWindowView, statusBarWindowController.mLp);
        }
    };
    public final C300917 mFalsingBeliefListener = new FalsingManager.FalsingBeliefListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.17
        @Override // com.android.systemui.plugins.FalsingManager.FalsingBeliefListener
        public final void onFalse() {
            CentralSurfacesImpl.this.mStatusBarKeyguardViewManager.reset(true);
        }
    };
    public final C301018 mUnlockScrimCallback = new C301018();
    public final C301119 mUserSetupObserver = new C301119();
    public final C301320 mWallpaperChangedReceiver = new C301320();
    public final CentralSurfacesImpl$$ExternalSyntheticLambda6 mLockScreenWallpaperChangeCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda6
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.getClass();
            Log.d("CentralSurfaces", "onLockScreenWallpaperChanged: uri=" + uri);
            if (uri == null) {
                return;
            }
            if (Settings.System.getUriFor("lockscreen_wallpaper").equals(uri) || Settings.System.getUriFor("lockscreen_wallpaper_sub").equals(uri)) {
                centralSurfacesImpl.mWallpaperChangedReceiver.onReceive(centralSurfacesImpl.mContext, null);
            }
        }
    };
    public final C301421 mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.21
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onConfigChanged(Configuration configuration) {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.updateResources();
            centralSurfacesImpl.updateDisplaySize();
            centralSurfacesImpl.mScreenPinningRequest.getClass();
            QSTooltipWindow qSTooltipWindow = centralSurfacesImpl.mToolTipWindow;
            if (qSTooltipWindow != null) {
                qSTooltipWindow.hideToolTip();
            }
            if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                DeviceState.setInDisplayFingerprintSensorPosition(centralSurfacesImpl.mContext.getResources().getDisplayMetrics());
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDensityOrFontScaleChanged() {
            FrameLayout frameLayout;
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            BrightnessMirrorController brightnessMirrorController = centralSurfacesImpl.mBrightnessMirrorController;
            if (brightnessMirrorController != null) {
                brightnessMirrorController.reinflate();
            }
            centralSurfacesImpl.mUserInfoControllerImpl.reloadUserInfo();
            centralSurfacesImpl.mNotificationIconAreaController.updateIconLayoutParams(centralSurfacesImpl.mContext);
            centralSurfacesImpl.mHeadsUpManager.getClass();
            NotificationShadeWindowViewController notificationShadeWindowViewController = centralSurfacesImpl.mNotificationShadeWindowViewController;
            notificationShadeWindowViewController.getClass();
            boolean z = LsRune.SECURITY_BOUNCER_WINDOW;
            NotificationShadeWindowView notificationShadeWindowView = notificationShadeWindowViewController.mView;
            if (z) {
                CentralSurfaces centralSurfaces = notificationShadeWindowViewController.mService;
                CentralSurfacesImpl centralSurfacesImpl2 = (CentralSurfacesImpl) centralSurfaces;
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
                frameLayout = new KeyguardBouncerContainer(notificationShadeWindowView.getContext(), centralSurfaces, notificationShadeWindowViewController.mStatusBarStateController);
                centralSurfacesImpl2.mBouncerContainer = frameLayout;
                ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).mHelper.addBouncer(frameLayout);
            } else {
                FrameLayout frameLayout3 = (FrameLayout) notificationShadeWindowView.findViewById(R.id.keyguard_bouncer_container);
                if (frameLayout3 != null) {
                    frameLayout3.removeAllViews();
                    notificationShadeWindowView.removeView(frameLayout3);
                    frameLayout = (FrameLayout) LayoutInflater.from(notificationShadeWindowView.getContext()).inflate(R.layout.keyguard_sec_bouncer_container, (ViewGroup) null);
                    notificationShadeWindowView.addView(frameLayout);
                } else {
                    frameLayout = frameLayout3;
                }
            }
            KeyguardBouncerViewBinder.bind(frameLayout, notificationShadeWindowViewController.mKeyguardBouncerViewModel, notificationShadeWindowViewController.mPrimaryBouncerToGoneTransitionViewModel, notificationShadeWindowViewController.mKeyguardBouncerComponentFactory);
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDisplayDeviceTypeChanged() {
            if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                centralSurfacesImpl.mNotificationIconAreaController.updateIconLayoutParams(centralSurfacesImpl.mContext);
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onThemeChanged() {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            BrightnessMirrorController brightnessMirrorController = centralSurfacesImpl.mBrightnessMirrorController;
            if (brightnessMirrorController != null) {
                brightnessMirrorController.reinflate();
            }
            ((NotificationPanelViewController) centralSurfacesImpl.mShadeSurface).mConfigurationListener.onThemeChanged();
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = centralSurfacesImpl.mStatusBarKeyguardViewManager;
            if (statusBarKeyguardViewManager != null) {
                statusBarKeyguardViewManager.onThemeChanged();
            }
            View view = centralSurfacesImpl.mAmbientIndicationContainer;
            if (view instanceof AutoReinflateContainer) {
                ((AutoReinflateContainer) view).inflateLayout();
            }
            NotificationIconAreaController notificationIconAreaController = centralSurfacesImpl.mNotificationIconAreaController;
            notificationIconAreaController.mAodIconTint = Utils.getColorAttrDefaultColor(R.attr.wallpaperTextColor, notificationIconAreaController.mContext, -1);
            notificationIconAreaController.updateAodIconColors();
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onUiModeChanged() {
            BrightnessMirrorController brightnessMirrorController = CentralSurfacesImpl.this.mBrightnessMirrorController;
            if (brightnessMirrorController != null) {
                brightnessMirrorController.reinflate();
            }
        }
    };
    public final C301522 mStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.22
        /* JADX WARN: Code restructure failed: missing block: B:14:0x0034, code lost:
        
            if ((r5 == 0.0f) == false) goto L18;
         */
        /* JADX WARN: Removed duplicated region for block: B:18:0x003f  */
        /* JADX WARN: Removed duplicated region for block: B:29:? A[RETURN, SYNTHETIC] */
        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onDozeAmountChanged(float f, float f2) {
            SecLightRevealScrimHelper secLightRevealScrimHelper = CentralSurfacesImpl.this.mSecLightRevealScrimHelper;
            secLightRevealScrimHelper.getClass();
            if (!LsRune.AOD_LIGHT_REVEAL) {
                return;
            }
            LightRevealScrim lightRevealScrim = secLightRevealScrimHelper.lightRevealScrim;
            if (lightRevealScrim.revealEffect instanceof CircleReveal) {
                return;
            }
            if (LsRune.AOD_FULLSCREEN) {
                if (!secLightRevealScrimHelper.screenOffAnimationController.shouldHideLightRevealScrimOnWakeUp()) {
                    if (secLightRevealScrimHelper.biometricUnlockController.isWakeAndUnlock()) {
                    }
                }
                if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
                    return;
                }
                if ((f == 1.0f) && secLightRevealScrimHelper.isFolded) {
                    ((PluginAODManager) secLightRevealScrimHelper.pluginAODManagerLazy.get()).onAodTransitionEnd();
                    return;
                }
                return;
            }
            lightRevealScrim.setRevealAmount(1.0f - f);
            if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
            }
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onDozingChanged(boolean z) {
            Trace.beginSection("CentralSurfaces#updateDozing");
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.mDozing = z;
            boolean z2 = centralSurfacesImpl.mDozeServiceHost.mDozingRequested && centralSurfacesImpl.mDozeParameters.mControlScreenOffAnimation;
            ((NotificationPanelViewController) centralSurfacesImpl.mShadeSurface).resetViews(z2 && z);
            if (!centralSurfacesImpl.mBiometricUnlockController.isWakeAndUnlock()) {
                centralSurfacesImpl.mMdmOverlayContainer.updateMdmPolicy();
            }
            centralSurfacesImpl.updateQsExpansionEnabled();
            centralSurfacesImpl.mKeyguardViewMediator.setDozing(centralSurfacesImpl.mDozing);
            centralSurfacesImpl.updateDozingState();
            centralSurfacesImpl.mDozeServiceHost.updateDozing();
            centralSurfacesImpl.updateScrimController();
            if (centralSurfacesImpl.mBiometricUnlockController.isWakeAndUnlock()) {
                centralSurfacesImpl.updateIsKeyguard();
            }
            centralSurfacesImpl.updateReportRejectedTouchVisibility();
            Trace.endSection();
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onFullscreenStateChanged(boolean z) {
            UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.getClass();
            centralSurfacesImpl.maybeUpdateBarMode();
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStateChanged(int i) {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.mState = i;
            centralSurfacesImpl.updateReportRejectedTouchVisibility();
            centralSurfacesImpl.mDozeServiceHost.updateDozing();
            centralSurfacesImpl.updateTheme();
            centralSurfacesImpl.mNavigationBarController.touchAutoDim(centralSurfacesImpl.mDisplayId);
            Trace.beginSection("CentralSurfaces#updateKeyguardState");
            if (centralSurfacesImpl.mState == 1) {
                ((NotificationPanelViewController) centralSurfacesImpl.mShadeSurface).cancelPendingCollapse(true);
            }
            centralSurfacesImpl.updateDozingState();
            centralSurfacesImpl.checkBarModes();
            centralSurfacesImpl.updateScrimController();
            ((StatusBarNotificationPresenter) centralSurfacesImpl.mPresenter).mMediaManager.updateMediaMetaData(false, centralSurfacesImpl.mState != 1);
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
            if (centralSurfacesImpl.mVisible && (i2 == 2 || ((StatusBarStateControllerImpl) centralSurfacesImpl.mStatusBarStateController).goingToFullShade())) {
                try {
                    centralSurfacesImpl.mBarService.clearNotificationEffects();
                } catch (RemoteException unused) {
                }
            }
            if (i2 == 1) {
                RemoteInputCoordinator remoteInputCoordinator = centralSurfacesImpl.mRemoteInputManager.mRemoteInputListener;
                if (remoteInputCoordinator != null) {
                    remoteInputCoordinator.mRemoteInputActiveExtender.endAllLifetimeExtensions();
                }
                CentralSurfacesImpl.m1713$$Nest$mmaybeEscalateHeadsUp(centralSurfacesImpl);
            }
        }
    };
    public final C301623 mBatteryStateChangeCallback = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.23
        @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
        public final void onPowerSaveChanged(boolean z) {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            ((ExecutorImpl) centralSurfacesImpl.mMainExecutor).execute(centralSurfacesImpl.mCheckBarModes);
            DozeServiceHost dozeServiceHost = centralSurfacesImpl.mDozeServiceHost;
            if (dozeServiceHost != null) {
                Assert.isMainThread();
                Iterator it = dozeServiceHost.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((DozeHost.Callback) it.next()).onPowerSaveChanged();
                }
            }
        }
    };
    public final C301724 mActivityLaunchAnimatorCallback = new C301724();
    public final C301825 mActivityLaunchAnimatorListener = new ActivityLaunchAnimator.Listener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.25
        @Override // com.android.systemui.animation.ActivityLaunchAnimator.Listener
        public final void onLaunchAnimationEnd() {
            CentralSurfacesImpl.this.mKeyguardViewMediator.setBlursDisabledForAppLaunch(false);
        }

        @Override // com.android.systemui.animation.ActivityLaunchAnimator.Listener
        public final void onLaunchAnimationStart() {
            CentralSurfacesImpl.this.mKeyguardViewMediator.setBlursDisabledForAppLaunch(true);
        }
    };
    public final C301926 mDemoModeCallback = new DemoMode() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.26
        @Override // com.android.systemui.demomode.DemoModeCommandReceiver
        public final void onDemoModeFinished() {
            CentralSurfacesImpl.this.checkBarModes();
        }

        @Override // com.android.systemui.demomode.DemoModeCommandReceiver
        public final void dispatchDemoCommand(Bundle bundle, String str) {
        }
    };
    public final C302027 mRemoteInputActionBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.27
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Objects.toString(intent);
            if ("com.samsung.systemui.action.REQUEST_REMOTE_INPUT".equals(intent.getAction())) {
                CentralSurfacesImpl.this.checkRemoteInputRequest(intent.getStringExtra("key"), null);
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$11 */
    public final class C300411 implements WakefulnessLifecycle.Observer {
        public C300411() {
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedGoingToSleep() {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            ((CameraLauncher) centralSurfacesImpl.mCameraLauncherLazy.get()).mKeyguardBypassController.launchingAffordance = false;
            centralSurfacesImpl.releaseGestureWakeLock();
            centralSurfacesImpl.mLaunchCameraWhenFinishedWaking = false;
            centralSurfacesImpl.mDeviceInteractive = false;
            centralSurfacesImpl.mWakeUpComingFromTouch = false;
            centralSurfacesImpl.updateVisibleToUser();
            centralSurfacesImpl.updateNotificationPanelTouchState();
            centralSurfacesImpl.mNotificationShadeWindowViewController.cancelCurrentTouch();
            if (centralSurfacesImpl.mLaunchCameraOnFinishedGoingToSleep) {
                centralSurfacesImpl.mLaunchCameraOnFinishedGoingToSleep = false;
                ((ExecutorImpl) centralSurfacesImpl.mMainExecutor).execute(new CentralSurfacesImpl$11$$ExternalSyntheticLambda0(this, 2));
            }
            if (centralSurfacesImpl.mLaunchEmergencyActionOnFinishedGoingToSleep) {
                centralSurfacesImpl.mLaunchEmergencyActionOnFinishedGoingToSleep = false;
                ((ExecutorImpl) centralSurfacesImpl.mMainExecutor).execute(new CentralSurfacesImpl$11$$ExternalSyntheticLambda0(this, 3));
            }
            centralSurfacesImpl.updateIsKeyguard();
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedWakingUp() {
            UserHandle userHandle;
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            int i = 1;
            if (centralSurfacesImpl.mShouldDelayLockscreenTransitionFromAod) {
                ((NotificationShadeWindowControllerImpl) centralSurfacesImpl.mNotificationShadeWindowController).batchApplyWindowLayoutParams(new CentralSurfacesImpl$11$$ExternalSyntheticLambda0(this, i));
            }
            NotificationWakeUpCoordinator notificationWakeUpCoordinator = centralSurfacesImpl.mWakeUpCoordinator;
            notificationWakeUpCoordinator.fullyAwake = true;
            notificationWakeUpCoordinator.setWakingUp(false, false);
            if (((KeyguardStateControllerImpl) centralSurfacesImpl.mKeyguardStateController).mOccluded && !centralSurfacesImpl.mDozeParameters.canControlUnlockedScreenOff()) {
                ((NotificationLockscreenUserManagerImpl) centralSurfacesImpl.mLockscreenUserManager).updatePublicMode();
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = ((NotificationPanelViewController) centralSurfacesImpl.mShadeSurface).mNotificationStackScrollLayoutController;
                notificationStackScrollLayoutController.mView.updateSensitiveness(false, ((NotificationLockscreenUserManagerImpl) notificationStackScrollLayoutController.mLockscreenUserManager).isAnyProfilePublicMode());
            }
            if (centralSurfacesImpl.mLaunchCameraWhenFinishedWaking) {
                ((CameraLauncher) centralSurfacesImpl.mCameraLauncherLazy.get()).launchCamera(centralSurfacesImpl.mLastCameraLaunchSource, ((NotificationPanelViewController) centralSurfacesImpl.mShadeSurface).isFullyCollapsed());
                centralSurfacesImpl.mLaunchCameraWhenFinishedWaking = false;
            }
            if (centralSurfacesImpl.mLaunchEmergencyActionWhenFinishedWaking) {
                centralSurfacesImpl.mLaunchEmergencyActionWhenFinishedWaking = false;
                Intent emergencyActionIntent = centralSurfacesImpl.getEmergencyActionIntent();
                if (emergencyActionIntent != null) {
                    Context context = centralSurfacesImpl.mContext;
                    for (String str : context.getResources().getStringArray(R.array.system_ui_packages)) {
                        if (emergencyActionIntent.getComponent() == null) {
                            break;
                        }
                        if (str.equals(emergencyActionIntent.getComponent().getPackageName())) {
                            userHandle = new UserHandle(UserHandle.myUserId());
                            break;
                        }
                    }
                    userHandle = ((UserTrackerImpl) centralSurfacesImpl.mUserTracker).getUserHandle();
                    context.startActivityAsUser(emergencyActionIntent, userHandle);
                }
            }
            centralSurfacesImpl.updateScrimController();
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedGoingToSleep() {
            DejankUtils.startDetectingBlockingIpcs("CentralSurfaces#onStartedGoingToSleep");
            UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.getClass();
            CentralSurfacesImpl.m1714$$Nest$mupdateRevealEffect(centralSurfacesImpl, false);
            centralSurfacesImpl.updateNotificationPanelTouchState();
            CentralSurfacesImpl.m1713$$Nest$mmaybeEscalateHeadsUp(centralSurfacesImpl);
            VolumeComponent volumeComponent = centralSurfacesImpl.mVolumeComponent;
            if (volumeComponent != null) {
                ((VolumeDialogComponent) volumeComponent).mController.mCallbacks.onDismissRequested(2);
            }
            centralSurfacesImpl.mWakeUpCoordinator.fullyAwake = false;
            centralSurfacesImpl.mKeyguardBypassController.pendingUnlock = null;
            centralSurfacesImpl.mStatusBarTouchableRegionManager.updateTouchableRegion();
            if (centralSurfacesImpl.mDozeParameters.shouldShowLightRevealScrim()) {
                ((ShadeControllerImpl) centralSurfacesImpl.mShadeController).makeExpandedVisible(true);
            }
            NotificationPanelViewController notificationPanelViewController = centralSurfacesImpl.mNotificationPanelViewController;
            if (notificationPanelViewController.mTracking) {
                notificationPanelViewController.onTrackingStopped(true);
            }
            DejankUtils.stopDetectingBlockingIpcs("CentralSurfaces#onStartedGoingToSleep");
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedWakingUp() {
            DejankUtils.startDetectingBlockingIpcs("CentralSurfaces#onStartedWakingUp");
            ((NotificationShadeWindowControllerImpl) CentralSurfacesImpl.this.mNotificationShadeWindowController).batchApplyWindowLayoutParams(new CentralSurfacesImpl$11$$ExternalSyntheticLambda0(this, 0));
            DejankUtils.stopDetectingBlockingIpcs("CentralSurfaces#onStartedWakingUp");
        }

        public final void startLockscreenTransitionFromAod() {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            centralSurfacesImpl.mDozeServiceHost.stopDozing();
            CentralSurfacesImpl.m1714$$Nest$mupdateRevealEffect(centralSurfacesImpl, true);
            centralSurfacesImpl.updateNotificationPanelTouchState();
            centralSurfacesImpl.mStatusBarTouchableRegionManager.updateTouchableRegion();
            if (centralSurfacesImpl.mScreenOffAnimationController.shouldHideLightRevealScrimOnWakeUp()) {
                ((ShadeControllerImpl) centralSurfacesImpl.mShadeController).makeExpandedInvisible();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$18 */
    public final class C301018 implements ScrimController.Callback {
        public C301018() {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$19 */
    public final class C301119 implements DeviceProvisionedController.DeviceProvisionedListener {
        public C301119() {
        }

        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public final void onFrpActiveChanged() {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            if (((DeviceProvisionedControllerImpl) centralSurfacesImpl.mDeviceProvisionedController).isFrpActive()) {
                return;
            }
            centralSurfacesImpl.updateNotificationPanelTouchState();
        }

        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public final void onUserSetupChanged() {
            CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
            boolean isCurrentUserSetup = ((DeviceProvisionedControllerImpl) centralSurfacesImpl.mDeviceProvisionedController).isCurrentUserSetup();
            Log.d("CentralSurfaces", "mUserSetupObserver - DeviceProvisionedListener called for current user");
            if (isCurrentUserSetup != centralSurfacesImpl.mUserSetup) {
                centralSurfacesImpl.mUserSetup = isCurrentUserSetup;
                if (!isCurrentUserSetup && centralSurfacesImpl.mState == 0) {
                    ((NotificationPanelViewController) centralSurfacesImpl.mShadeSurface).collapse(1.0f, true, false);
                }
                ShadeSurface shadeSurface = centralSurfacesImpl.mShadeSurface;
                if (shadeSurface != null) {
                    boolean z = centralSurfacesImpl.mUserSetup;
                    NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) shadeSurface;
                    notificationPanelViewController.mUserSetupComplete = z;
                    notificationPanelViewController.mKeyguardBottomAreaViewController.setUserSetupComplete(z);
                }
                centralSurfacesImpl.updateQsExpansionEnabled();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$20 */
    public final class C301320 extends BroadcastReceiver {
        public C301320() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (CentralSurfacesImpl.this.mWallpaperSupported) {
                new Thread(CentralSurfacesImpl.this.mSetWallpaperSupportsAmbientMode).start();
            } else {
                Log.wtf("CentralSurfaces", "WallpaperManager not supported");
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$24 */
    public final class C301724 implements ActivityLaunchAnimator.Callback {
        public C301724() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$4 */
    public final class C30224 implements PluginListener {
        public final ArraySet mOverlays = new ArraySet();

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$4$Callback */
        public final class Callback implements OverlayPlugin.Callback {
            public final OverlayPlugin mPlugin;

            public Callback(OverlayPlugin overlayPlugin) {
                this.mPlugin = overlayPlugin;
            }

            @Override // com.android.systemui.plugins.OverlayPlugin.Callback
            public final void onHoldStatusBarOpenChange() {
                OverlayPlugin overlayPlugin = this.mPlugin;
                boolean holdStatusBarOpen = overlayPlugin.holdStatusBarOpen();
                C30224 c30224 = C30224.this;
                if (holdStatusBarOpen) {
                    c30224.mOverlays.add(overlayPlugin);
                } else {
                    c30224.mOverlays.remove(overlayPlugin);
                }
                ((ExecutorImpl) CentralSurfacesImpl.this.mMainExecutor).execute(new CentralSurfacesImpl$9$$ExternalSyntheticLambda0(this, 1));
            }
        }

        public C30224() {
        }

        @Override // com.android.systemui.plugins.PluginListener
        public final void onPluginConnected(Plugin plugin, Context context) {
            ((ExecutorImpl) CentralSurfacesImpl.this.mMainExecutor).execute(new CentralSurfacesImpl$4$$ExternalSyntheticLambda0(this, (OverlayPlugin) plugin, 1));
        }

        @Override // com.android.systemui.plugins.PluginListener
        public final void onPluginDisconnected(Plugin plugin) {
            ((ExecutorImpl) CentralSurfacesImpl.this.mMainExecutor).execute(new CentralSurfacesImpl$4$$ExternalSyntheticLambda0(this, (OverlayPlugin) plugin, 0));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$5 */
    public final class C30235 {
        public C30235() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$8 */
    public final class C30268 implements WirelessChargingAnimation.Callback {
        public C30268() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.CentralSurfacesImpl$9 */
    public final class C30279 extends BroadcastReceiver {
        public C30279() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Trace.beginSection("CentralSurfaces#onReceive");
            String action = intent.getAction();
            String stringExtra = intent.getStringExtra("reason");
            if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(action)) {
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                if (centralSurfacesImpl.mIsShortcutListSearchEnabled) {
                    Utilities.isLargeScreen(centralSurfacesImpl.mContext);
                }
                KeyboardShortcuts.dismiss();
                CentralSurfacesImpl.this.mRemoteInputManager.closeRemoteInputs(false);
                if (((NotificationLockscreenUserManagerImpl) CentralSurfacesImpl.this.mLockscreenUserManager).isCurrentProfile(getSendingUserId())) {
                    CentralSurfacesImpl.this.mShadeLogger.m189d("ACTION_CLOSE_SYSTEM_DIALOGS intent: closing shade");
                    if (stringExtra != null) {
                        r1 = stringExtra.equals(ActivityManagerWrapper.CLOSE_SYSTEM_WINDOWS_REASON_RECENTS) ? 2 : 0;
                        if (stringExtra.equals(BcSmartspaceDataPlugin.UI_SURFACE_DREAM) && CentralSurfacesImpl.this.mScreenOffAnimationController.shouldExpandNotifications()) {
                            r1 |= 4;
                        }
                    }
                    ((ShadeControllerImpl) CentralSurfacesImpl.this.mShadeController).animateCollapseShade(r1);
                } else {
                    CentralSurfacesImpl.this.mShadeLogger.m189d("ACTION_CLOSE_SYSTEM_DIALOGS intent: non-matching user ID");
                }
            } else if ("android.intent.action.SCREEN_OFF".equals(action)) {
                CentralSurfacesImpl.this.mQsController.closeQs();
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
                centralSurfacesImpl2.mNavigationBarController.finishBarAnimations(centralSurfacesImpl2.mDisplayId);
                CentralSurfacesImpl.this.mNotificationsController.resetUserExpandedStates();
            } else if ("com.sec.aecmonitor.ONE_CYCLE_FINISH".equals(action)) {
                CentralSurfacesImpl centralSurfacesImpl3 = CentralSurfacesImpl.this;
                if (!((KeyguardStateControllerImpl) centralSurfacesImpl3.mKeyguardStateController).mShowing) {
                    centralSurfacesImpl3.mCommandQueueCallbacks.animateExpandNotificationsPanel();
                }
                CentralSurfacesImpl.this.mMainHandler.postDelayed(new CentralSurfacesImpl$9$$ExternalSyntheticLambda0(this, r1), 300L);
            }
            Trace.endSection();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AnimateExpandSettingsPanelMessage {
        public final String mSubpanel;

        public AnimateExpandSettingsPanelMessage(String str) {
            this.mSubpanel = str;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
    public static void m1713$$Nest$mmaybeEscalateHeadsUp(CentralSurfacesImpl centralSurfacesImpl) {
        HeadsUpManagerPhone headsUpManagerPhone = centralSurfacesImpl.mHeadsUpManager;
        headsUpManagerPhone.getAllEntries().forEach(new CentralSurfacesImpl$$ExternalSyntheticLambda13(centralSurfacesImpl, 1));
        headsUpManagerPhone.releaseAllImmediately();
    }

    /* renamed from: -$$Nest$mupdateRevealEffect, reason: not valid java name */
    public static void m1714$$Nest$mupdateRevealEffect(CentralSurfacesImpl centralSurfacesImpl, boolean z) {
        boolean z2;
        LightRevealScrim lightRevealScrim = centralSurfacesImpl.mLightRevealScrim;
        if (lightRevealScrim == null) {
            return;
        }
        Flags flags = Flags.INSTANCE;
        centralSurfacesImpl.mFeatureFlags.getClass();
        if (LsRune.AOD_FULLSCREEN && LsRune.AOD_LIGHT_REVEAL && ((KeyguardFastBioUnlockController) Dependency.get(KeyguardFastBioUnlockController.class)).isFastWakeAndUnlockMode() && ((KeyguardFastBioUnlockController) Dependency.get(KeyguardFastBioUnlockController.class)).isInvisibleAfterGoingAwayTransStarted) {
            return;
        }
        WakefulnessLifecycle wakefulnessLifecycle = centralSurfacesImpl.mWakefulnessLifecycle;
        boolean z3 = false;
        boolean z4 = z && !(lightRevealScrim.revealEffect instanceof CircleReveal) && wakefulnessLifecycle.mLastWakeReason == 1;
        boolean z5 = !z && wakefulnessLifecycle.mLastSleepReason == 4;
        boolean z6 = LsRune.AOD_LIGHT_REVEAL;
        if (z6) {
            boolean z7 = z && wakefulnessLifecycle.mLastWakeReason == 113;
            z2 = !z && wakefulnessLifecycle.mLastSleepReason == 23;
            StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("updateRevealEffect: wakingUp=", z, " wakingUpFromPowerButton=", z4, " sleepingFromPowerButton=");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m69m, z5, " wakingUpFromDoubleTap=", z7, " sleepingFromDoubleTap=");
            m69m.append(z2);
            m69m.append(" isAODAmbientWallpaperMode=");
            m69m.append(centralSurfacesImpl.mAODAmbientWallpaperHelper.isAODAmbientWallpaperMode());
            Log.i("CentralSurfaces", m69m.toString());
            z3 = z7;
        } else {
            z2 = false;
        }
        SysuiStatusBarStateController sysuiStatusBarStateController = centralSurfacesImpl.mStatusBarStateController;
        if (z4 || z5) {
            lightRevealScrim.setRevealEffect(centralSurfacesImpl.mPowerButtonReveal);
            lightRevealScrim.setRevealAmount(1.0f - ((StatusBarStateControllerImpl) sysuiStatusBarStateController).mDozeAmount);
            return;
        }
        if (!z6) {
            if (z && (lightRevealScrim.revealEffect instanceof CircleReveal)) {
                return;
            }
            lightRevealScrim.setRevealEffect(LiftReveal.INSTANCE);
            lightRevealScrim.setRevealAmount(1.0f - ((StatusBarStateControllerImpl) sysuiStatusBarStateController).mDozeAmount);
            return;
        }
        SecLightRevealScrimHelper secLightRevealScrimHelper = centralSurfacesImpl.mSecLightRevealScrimHelper;
        NotificationPanelViewController notificationPanelViewController = centralSurfacesImpl.mNotificationPanelViewController;
        secLightRevealScrimHelper.getClass();
        if (!z3 && !z2) {
            SecLightRevealScrimHelper.SecCircleReveal secCircleReveal = secLightRevealScrimHelper.secCircleReveal;
            if (secCircleReveal != null) {
                secCircleReveal.centerX = secLightRevealScrimHelper.secRevealCenterY;
            }
        } else if (z2) {
            if (notificationPanelViewController.mKeyguardTouchAnimator.doubleTapDownEvent == null) {
                SecLightRevealScrimHelper.SecCircleReveal secCircleReveal2 = secLightRevealScrimHelper.secCircleReveal;
                if (secCircleReveal2 != null) {
                    secCircleReveal2.centerX = secLightRevealScrimHelper.secRevealCenterX;
                    secCircleReveal2.centerY = secLightRevealScrimHelper.secRevealCenterY;
                }
            } else {
                SecLightRevealScrimHelper.SecCircleReveal secCircleReveal3 = secLightRevealScrimHelper.secCircleReveal;
                if (secCircleReveal3 != null) {
                    secCircleReveal3.centerX = (int) r2.getX();
                    secCircleReveal3.centerY = (int) r2.getY();
                }
            }
        }
        lightRevealScrim.setRevealEffect(centralSurfacesImpl.mSecLightRevealScrimHelper.secCircleReveal);
        lightRevealScrim.setRevealAmount(1.0f - ((StatusBarStateControllerImpl) sysuiStatusBarStateController).mDozeAmount);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x030d, code lost:
    
        if (r7.intValue() != 2) goto L17;
     */
    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$1] */
    /* JADX WARN: Type inference failed for: r7v2, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r8v0, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda4] */
    /* JADX WARN: Type inference failed for: r8v1, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$2] */
    /* JADX WARN: Type inference failed for: r8v10, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$17] */
    /* JADX WARN: Type inference failed for: r8v15, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda6] */
    /* JADX WARN: Type inference failed for: r8v16, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$21] */
    /* JADX WARN: Type inference failed for: r8v17, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$22] */
    /* JADX WARN: Type inference failed for: r8v18, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$23] */
    /* JADX WARN: Type inference failed for: r8v20, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$25] */
    /* JADX WARN: Type inference failed for: r8v21, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$26] */
    /* JADX WARN: Type inference failed for: r8v22, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$27] */
    /* JADX WARN: Type inference failed for: r8v5, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$10] */
    /* JADX WARN: Type inference failed for: r8v7, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$12] */
    /* JADX WARN: Type inference failed for: r8v8, types: [com.android.systemui.statusbar.phone.CentralSurfacesImpl$14] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CentralSurfacesImpl(KeyguardSysDumpTrigger keyguardSysDumpTrigger, MdmOverlayContainer mdmOverlayContainer, Handler handler, DisplayLifecycle displayLifecycle, KeyguardWallpaper keyguardWallpaper, SystemUICommandActionHandler systemUICommandActionHandler, Lazy lazy, Lazy lazy2, SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper, Context context, NotificationsController notificationsController, FragmentService fragmentService, LightBarController lightBarController, AutoHideController autoHideController, StatusBarInitializer statusBarInitializer, StatusBarWindowController statusBarWindowController, StatusBarWindowStateController statusBarWindowStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, StatusBarSignalPolicy statusBarSignalPolicy, PulseExpansionHandler pulseExpansionHandler, NotificationWakeUpCoordinator notificationWakeUpCoordinator, KeyguardBypassController keyguardBypassController, KeyguardStateController keyguardStateController, HeadsUpManagerPhone headsUpManagerPhone, DynamicPrivacyController dynamicPrivacyController, FalsingManager falsingManager, FalsingCollector falsingCollector, BroadcastDispatcher broadcastDispatcher, NotificationGutsManager notificationGutsManager, NotificationLogger notificationLogger, NotificationInterruptStateProvider notificationInterruptStateProvider, ShadeExpansionStateManager shadeExpansionStateManager, KeyguardViewMediator keyguardViewMediator, DisplayMetrics displayMetrics, MetricsLogger metricsLogger, ShadeLogger shadeLogger, Executor executor, NotificationMediaManager notificationMediaManager, NotificationLockscreenUserManager notificationLockscreenUserManager, NotificationRemoteInputManager notificationRemoteInputManager, UserSwitcherController userSwitcherController, BatteryController batteryController, SysuiColorExtractor sysuiColorExtractor, ScreenLifecycle screenLifecycle, WakefulnessLifecycle wakefulnessLifecycle, SysuiStatusBarStateController sysuiStatusBarStateController, Optional<Bubbles> optional, Lazy lazy3, DeviceProvisionedController deviceProvisionedController, NavigationBarController navigationBarController, AccessibilityFloatingMenuController accessibilityFloatingMenuController, Lazy lazy4, ConfigurationController configurationController, NotificationShadeWindowController notificationShadeWindowController, DozeParameters dozeParameters, ScrimController scrimController, Lazy lazy5, Lazy lazy6, AuthRippleController authRippleController, DozeServiceHost dozeServiceHost, PowerManager powerManager, ScreenPinningRequest screenPinningRequest, SamsungScreenPinningRequest samsungScreenPinningRequest, DozeScrimController dozeScrimController, VolumeComponent volumeComponent, CommandQueue commandQueue, CentralSurfacesComponent.Factory factory, PluginManager pluginManager, ShadeController shadeController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, ViewMediatorCallback viewMediatorCallback, InitController initController, Handler handler2, PluginDependencyProvider pluginDependencyProvider, KeyguardDismissUtil keyguardDismissUtil, ExtensionController extensionController, UserInfoControllerImpl userInfoControllerImpl, PhoneStatusBarPolicy phoneStatusBarPolicy, KeyguardIndicationController keyguardIndicationController, DemoModeController demoModeController, Lazy lazy7, StatusBarTouchableRegionManager statusBarTouchableRegionManager, NotificationIconAreaController notificationIconAreaController, BrightnessSliderController.Factory factory2, ScreenOffAnimationController screenOffAnimationController, WallpaperController wallpaperController, OngoingCallController ongoingCallController, StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, FeatureFlags featureFlags, KeyguardUnlockAnimationController keyguardUnlockAnimationController, DelayableExecutor delayableExecutor, MessageRouter messageRouter, WallpaperManager wallpaperManager, Optional<StartingWindowController.StartingSurfaceImpl> optional2, ActivityLaunchAnimator activityLaunchAnimator, InteractionJankMonitor interactionJankMonitor, DeviceStateManager deviceStateManager, WiredChargingRippleController wiredChargingRippleController, IDreamManager iDreamManager, Lazy lazy8, Lazy lazy9, LightRevealScrim lightRevealScrim, AlternateBouncerInteractor alternateBouncerInteractor, UserTracker userTracker, Provider provider, ActivityStarter activityStarter, Lazy lazy10, SecQpBlurController secQpBlurController, NotifRemoteViewCache notifRemoteViewCache, CommonNotifCollection commonNotifCollection, SecPanelLogger secPanelLogger) {
        Context context2;
        final int i = 0;
        this.mCheckBarModes = new CentralSurfacesImpl$$ExternalSyntheticLambda5(this, i);
        final int i2 = 1;
        this.mSetWallpaperSupportsAmbientMode = new CentralSurfacesImpl$$ExternalSyntheticLambda5(this, i2);
        this.mSysDumpTrigger = keyguardSysDumpTrigger;
        this.mMdmOverlayContainer = mdmOverlayContainer;
        this.mMainHandler = handler;
        this.mDisplayLifecycle = displayLifecycle;
        this.mContext = context;
        this.mNotificationsController = notificationsController;
        this.mFragmentService = fragmentService;
        this.mLightBarController = lightBarController;
        this.mAutoHideController = autoHideController;
        this.mStatusBarInitializer = statusBarInitializer;
        this.mStatusBarWindowController = statusBarWindowController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mPulseExpansionHandler = pulseExpansionHandler;
        this.mWakeUpCoordinator = notificationWakeUpCoordinator;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mKeyguardStateController = keyguardStateController;
        this.mHeadsUpManager = headsUpManagerPhone;
        this.mKeyguardIndicationController = keyguardIndicationController;
        this.mStatusBarTouchableRegionManager = statusBarTouchableRegionManager;
        this.mFalsingCollector = falsingCollector;
        this.mFalsingManager = falsingManager;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mGutsManager = notificationGutsManager;
        this.mNotificationLogger = notificationLogger;
        this.mShadeExpansionStateManager = shadeExpansionStateManager;
        this.mKeyguardViewMediator = keyguardViewMediator;
        this.mDisplayMetrics = displayMetrics;
        this.mMetricsLogger = metricsLogger;
        this.mShadeLogger = shadeLogger;
        this.mUiBgExecutor = executor;
        this.mMediaManager = notificationMediaManager;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.mUserSwitcherController = userSwitcherController;
        this.mBatteryController = batteryController;
        this.mColorExtractor = sysuiColorExtractor;
        this.mScreenLifecycle = screenLifecycle;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mBubblesOptional = optional;
        this.mNoteTaskControllerLazy = lazy3;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mNavigationBarController = navigationBarController;
        this.mAccessibilityFloatingMenuController = accessibilityFloatingMenuController;
        this.mAssistManagerLazy = lazy4;
        this.mConfigurationController = configurationController;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mDozeServiceHost = dozeServiceHost;
        this.mPowerManager = powerManager;
        this.mDozeParameters = dozeParameters;
        this.mScrimController = scrimController;
        this.mLockscreenWallpaperLazy = lazy5;
        this.mScreenPinningRequest = screenPinningRequest;
        this.mSamsungScreenPinningRequest = samsungScreenPinningRequest;
        this.mDozeScrimController = dozeScrimController;
        this.mBiometricUnlockControllerLazy = lazy6;
        this.mNotificationShadeDepthControllerLazy = lazy7;
        this.mVolumeComponent = volumeComponent;
        this.mCommandQueue = commandQueue;
        this.mCentralSurfacesComponentFactory = factory;
        this.mPluginManager = pluginManager;
        this.mShadeController = shadeController;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mKeyguardViewMediatorCallback = viewMediatorCallback;
        this.mInitController = initController;
        this.mPluginDependencyProvider = pluginDependencyProvider;
        this.mKeyguardDismissUtil = keyguardDismissUtil;
        this.mExtensionController = extensionController;
        this.mUserInfoControllerImpl = userInfoControllerImpl;
        this.mIconPolicy = phoneStatusBarPolicy;
        this.mDemoModeController = demoModeController;
        this.mNotificationIconAreaController = notificationIconAreaController;
        this.mBrightnessSliderFactory = factory2;
        this.mWallpaperController = wallpaperController;
        this.mStatusBarSignalPolicy = statusBarSignalPolicy;
        this.mStatusBarHideIconsForBouncerManager = statusBarHideIconsForBouncerManager;
        this.mFeatureFlags = featureFlags;
        this.mIsShortcutListSearchEnabled = ((FeatureFlagsRelease) featureFlags).isEnabled(Flags.SHORTCUT_LIST_SEARCH_LAYOUT);
        this.mMainExecutor = delayableExecutor;
        this.mMessageRouter = messageRouter;
        this.mWallpaperManager = wallpaperManager;
        this.mJankMonitor = interactionJankMonitor;
        this.mCameraLauncherLazy = lazy8;
        this.mAlternateBouncerInteractor = alternateBouncerInteractor;
        this.mUserTracker = userTracker;
        this.mFingerprintManager = provider;
        this.mActivityStarter = activityStarter;
        this.mToolTipWindow = QSTooltipWindow.getInstance(context);
        this.mKeyguardWallpaper = keyguardWallpaper;
        this.mLockscreenShadeTransitionController = lockscreenShadeTransitionController;
        this.mStartingSurfaceOptional = optional2;
        this.mDreamManager = iDreamManager;
        lockscreenShadeTransitionController.centralSurfaces = this;
        ((HashSet) statusBarWindowStateController.listeners).add(new StatusBarWindowStateListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda7
            @Override // com.android.systemui.statusbar.window.StatusBarWindowStateListener
            public final void onStatusBarWindowStateChanged(int i3) {
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                centralSurfacesImpl.mStatusBarWindowState = i3;
                centralSurfacesImpl.mBubblesOptional.ifPresent(new CentralSurfacesImpl$$ExternalSyntheticLambda13(centralSurfacesImpl, 0));
            }
        });
        this.mScreenOffAnimationController = screenOffAnimationController;
        ShadeExpansionListener shadeExpansionListener = new ShadeExpansionListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda8
            @Override // com.android.systemui.shade.ShadeExpansionListener
            public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                centralSurfacesImpl.getClass();
                if (centralSurfacesImpl.isKeyguardShowing() && !centralSurfacesImpl.mStatusBarKeyguardViewManager.primaryBouncerIsOrWillBeShowing() && !centralSurfacesImpl.isOccluded() && ((KeyguardStateControllerImpl) centralSurfacesImpl.mKeyguardStateController).mCanDismissLockScreen && !centralSurfacesImpl.mKeyguardViewMediator.isAnySimPinSecure() && (!centralSurfacesImpl.mQsController.mExpanded || !shadeExpansionChangeEvent.tracking)) {
                    int i3 = ((NotificationPanelViewController) centralSurfacesImpl.mShadeSurface).mBarState;
                }
                float f = shadeExpansionChangeEvent.fraction;
                if (f == 0.0f || f == 1.0f) {
                    if (centralSurfacesImpl.getNavigationBarView() != null) {
                        centralSurfacesImpl.getNavigationBarView().updateSlippery();
                    }
                    ShadeSurface shadeSurface = centralSurfacesImpl.mShadeSurface;
                    if (shadeSurface != null) {
                        ((NotificationPanelViewController) shadeSurface).updateSystemUiStateFlags();
                    }
                }
            }
        };
        shadeExpansionListener.onPanelExpansionChanged(shadeExpansionStateManager.addExpansionListener(shadeExpansionListener));
        shadeExpansionStateManager.addFullExpansionListener(new ShadeFullExpansionListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda9
            @Override // com.android.systemui.shade.ShadeFullExpansionListener
            public final void onShadeExpansionFullyChanged(boolean z) {
                CentralSurfacesImpl.this.onShadeExpansionFullyChanged(Boolean.valueOf(z));
            }
        });
        new ActivityIntentHelper(context);
        this.mActivityLaunchAnimator = activityLaunchAnimator;
        ongoingCallController.addCallback(new OngoingCallListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda10
            @Override // com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener
            public final void onOngoingCallStateChanged() {
                CentralSurfacesImpl.this.maybeUpdateBarMode();
            }
        });
        DateTimeView.setReceiverHandler(handler2);
        MessageRouterImpl messageRouterImpl = (MessageRouterImpl) messageRouter;
        messageRouterImpl.subscribeTo(CentralSurfaces.KeyboardShortcutsMessage.class, new CentralSurfacesImpl$$ExternalSyntheticLambda11(this, 0));
        messageRouterImpl.subscribeTo(1027, new MessageRouter.SimpleMessageListener(this) { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda3
            public final /* synthetic */ CentralSurfacesImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.systemui.util.concurrency.MessageRouter.SimpleMessageListener
            public final void onMessage() {
                int i3 = i;
                CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                switch (i3) {
                    case 0:
                        if (centralSurfacesImpl.mIsShortcutListSearchEnabled) {
                            Utilities.isLargeScreen(centralSurfacesImpl.mContext);
                        }
                        KeyboardShortcuts.dismiss();
                        break;
                    default:
                        centralSurfacesImpl.getClass();
                        Log.w("CentralSurfaces", "Launch transition: Timeout!");
                        ((CameraLauncher) centralSurfacesImpl.mCameraLauncherLazy.get()).mKeyguardBypassController.launchingAffordance = false;
                        NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) centralSurfacesImpl.mShadeSurface;
                        notificationPanelViewController.mLaunchingAffordance = false;
                        NotificationPanelViewController.KeyguardAffordanceHelperCallback keyguardAffordanceHelperCallback = notificationPanelViewController.mKeyguardAffordanceHelperCallback;
                        NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                        ((KeyguardSecAffordanceView) (notificationPanelViewController2.mView.getLayoutDirection() == 1 ? notificationPanelViewController2.mKeyguardBottomArea.getRightView() : notificationPanelViewController2.mKeyguardBottomArea.getLeftView())).getClass();
                        NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
                        ((KeyguardSecAffordanceView) (notificationPanelViewController3.mView.getLayoutDirection() == 1 ? notificationPanelViewController3.mKeyguardBottomArea.getLeftView() : notificationPanelViewController3.mKeyguardBottomArea.getRightView())).getClass();
                        notificationPanelViewController.mKeyguardBypassController.launchingAffordance = false;
                        centralSurfacesImpl.releaseGestureWakeLock();
                        ((NotificationPanelViewController) centralSurfacesImpl.mShadeSurface).resetViews(false);
                        break;
                }
            }
        });
        messageRouterImpl.subscribeTo(AnimateExpandSettingsPanelMessage.class, new CentralSurfacesImpl$$ExternalSyntheticLambda11(this, 1));
        messageRouterImpl.subscribeTo(1003, new MessageRouter.SimpleMessageListener(this) { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda3
            public final /* synthetic */ CentralSurfacesImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.systemui.util.concurrency.MessageRouter.SimpleMessageListener
            public final void onMessage() {
                int i3 = i2;
                CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                switch (i3) {
                    case 0:
                        if (centralSurfacesImpl.mIsShortcutListSearchEnabled) {
                            Utilities.isLargeScreen(centralSurfacesImpl.mContext);
                        }
                        KeyboardShortcuts.dismiss();
                        break;
                    default:
                        centralSurfacesImpl.getClass();
                        Log.w("CentralSurfaces", "Launch transition: Timeout!");
                        ((CameraLauncher) centralSurfacesImpl.mCameraLauncherLazy.get()).mKeyguardBypassController.launchingAffordance = false;
                        NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) centralSurfacesImpl.mShadeSurface;
                        notificationPanelViewController.mLaunchingAffordance = false;
                        NotificationPanelViewController.KeyguardAffordanceHelperCallback keyguardAffordanceHelperCallback = notificationPanelViewController.mKeyguardAffordanceHelperCallback;
                        NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
                        ((KeyguardSecAffordanceView) (notificationPanelViewController2.mView.getLayoutDirection() == 1 ? notificationPanelViewController2.mKeyguardBottomArea.getRightView() : notificationPanelViewController2.mKeyguardBottomArea.getLeftView())).getClass();
                        NotificationPanelViewController notificationPanelViewController3 = NotificationPanelViewController.this;
                        ((KeyguardSecAffordanceView) (notificationPanelViewController3.mView.getLayoutDirection() == 1 ? notificationPanelViewController3.mKeyguardBottomArea.getLeftView() : notificationPanelViewController3.mKeyguardBottomArea.getRightView())).getClass();
                        notificationPanelViewController.mKeyguardBypassController.launchingAffordance = false;
                        centralSurfacesImpl.releaseGestureWakeLock();
                        ((NotificationPanelViewController) centralSurfacesImpl.mShadeSurface).resetViews(false);
                        break;
                }
            }
        });
        this.mDeviceStateManager = deviceStateManager;
        this.mLightRevealScrim = lightRevealScrim;
        featureFlags.getClass();
        String str = LsRune.VALUE_CONFIG_CARRIER_TEXT_POLICY;
        this.mNavBarHelperLazy = lazy10;
        NotiCenterPlugin.INSTANCE.getClass();
        NotiCenterPlugin.centralSurfaces = this;
        String str2 = NotiCenterPlugin.TAG;
        Log.d(str2, "register observer");
        CentralSurfaces centralSurfaces = NotiCenterPlugin.centralSurfaces;
        NotiCenterPlugin.packageManager = (centralSurfaces == null || (context2 = ((CentralSurfacesImpl) centralSurfaces).mContext) == null) ? null : context2.getPackageManager();
        ComponentName componentName = new ComponentName("com.samsung.systemui.notilus", "com.samsung.systemui.notilus.service.NotificationListener");
        try {
            PackageManager packageManager = NotiCenterPlugin.packageManager;
            Integer valueOf = packageManager != null ? Integer.valueOf(packageManager.getComponentEnabledSetting(componentName)) : null;
            if (valueOf != null) {
            }
            PackageManager packageManager2 = NotiCenterPlugin.packageManager;
            if (packageManager2 != null) {
                packageManager2.setComponentEnabledSetting(componentName, 2, 1);
            }
        } catch (IllegalArgumentException unused) {
            Log.d(str2, "There is no Listener");
        }
        SPluginManager sPluginManager = (SPluginManager) Dependency.get(SPluginManager.class);
        NotiCenterPlugin.INSTANCE.getClass();
        sPluginManager.addPluginListener((SPluginListener) NotiCenterPlugin.notiCenterPluginListener, PluginNotiCenter.class, false);
        if (QpRune.QUICK_PANEL_BLUR) {
            this.mBlurController = secQpBlurController;
        }
        this.mSamsungStatusBarGrayIconHelper = samsungStatusBarGrayIconHelper;
        this.mNotifRemoteViewCache = notifRemoteViewCache;
        this.mNotifCollection = commonNotifCollection;
        this.mPanelLogger = secPanelLogger;
    }

    public static int barMode(int i, boolean z) {
        if (z) {
            return 1;
        }
        if ((i & 5) == 5) {
            return 3;
        }
        if ((i & 4) != 0) {
            return 6;
        }
        if ((i & 1) != 0) {
            return 4;
        }
        return (i & 32) != 0 ? 1 : 0;
    }

    public final void awakenDreams() {
        this.mUiBgExecutor.execute(new CentralSurfacesImpl$$ExternalSyntheticLambda5(this, 3));
    }

    public final void checkBarModes() {
        this.mDemoModeController.getClass();
        PhoneStatusBarTransitions phoneStatusBarTransitions = this.mStatusBarTransitions;
        if (phoneStatusBarTransitions != null) {
            phoneStatusBarTransitions.transitionTo(this.mStatusBarMode, (this.mNoAnimationOnNextBarModeChange || !this.mDeviceInteractive || this.mStatusBarWindowState == 2) ? false : true);
        }
        this.mNavigationBarController.checkNavBarModes(this.mDisplayId);
        this.mNoAnimationOnNextBarModeChange = false;
    }

    public final void checkRemoteInputRequest(String str, String str2) {
        boolean z;
        if (str == null) {
            Log.d("CentralSurfaces", " RemoteInput: extra value is null");
            return;
        }
        if ((this.mDisabled2 & 4) != 0) {
            Log.d("CentralSurfaces", " RemoteInput: disabled panel ".concat(str));
            return;
        }
        NotificationEntry entry = ((NotifCollection) Dependency.get(NotifCollection.class)).getEntry(str);
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
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                z = false;
                break;
            } else {
                if (notification2.actions[i2].getRemoteInputs() != null) {
                    z = true;
                    break;
                }
                i2++;
            }
        }
        if (!z) {
            Log.d("CentralSurfaces", " RemoteInput: no remote input for ".concat(str));
            return;
        }
        wakeUpIfDozing(SystemClock.uptimeMillis(), "REMOTE_INPUT_CLICK", 4);
        this.mMainHandler.postDelayed(new CentralSurfacesImpl$$ExternalSyntheticLambda15(this, expandableNotificationRow, str2, i), 500L);
    }

    public final void collapsePanelOnMainThread() {
        boolean isCurrentThread = Looper.getMainLooper().isCurrentThread();
        ShadeController shadeController = this.mShadeController;
        if (isCurrentThread) {
            ((ShadeControllerImpl) shadeController).collapseShade();
            return;
        }
        Executor mainExecutor = this.mContext.getMainExecutor();
        Objects.requireNonNull(shadeController);
        mainExecutor.execute(new CentralSurfacesImpl$$ExternalSyntheticLambda1(shadeController, 2));
    }

    public final void collapseShade() {
        if (((NotificationPanelViewController) this.mShadeSurface).mTracking) {
            this.mNotificationShadeWindowViewController.cancelCurrentTouch();
        }
        if (this.mPanelExpanded && this.mState == 0) {
            ((ShadeControllerImpl) this.mShadeController).animateCollapseShade(0);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v41, types: [com.android.systemui.statusbar.LockscreenShadeTransitionController$bindController$1] */
    public final void createAndAddWindows(RegisterStatusBarResult registerStatusBarResult) {
        int i;
        int i2;
        int i3;
        updateDisplaySize();
        updateResources();
        updateTheme();
        if (this.mCentralSurfacesComponent != null) {
            Log.e("CentralSurfaces", "CentralSurfacesComponent being recreated; this is unexpected.");
        }
        final CentralSurfacesComponent create = this.mCentralSurfacesComponentFactory.create();
        this.mCentralSurfacesComponent = create;
        Objects.requireNonNull(create);
        Provider provider = new Provider() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda25
            @Override // javax.inject.Provider
            public final Object get() {
                return CentralSurfacesComponent.this.createCollapsedStatusBarFragment();
            }
        };
        FragmentService fragmentService = this.mFragmentService;
        fragmentService.addFragmentInstantiationProvider(CollapsedStatusBarFragment.class, provider);
        this.mNotificationShadeWindowView = this.mCentralSurfacesComponent.getNotificationShadeWindowView();
        this.mNotificationShadeWindowViewController = this.mCentralSurfacesComponent.getNotificationShadeWindowViewController();
        NotificationShadeWindowView notificationShadeWindowView = this.mNotificationShadeWindowView;
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
        notificationShadeWindowControllerImpl.mNotificationShadeView = notificationShadeWindowView;
        final int i4 = 1;
        Rune.runIf((Runnable) new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda6(notificationShadeWindowControllerImpl, notificationShadeWindowView, i4), true);
        final NotificationShadeWindowViewController notificationShadeWindowViewController = this.mNotificationShadeWindowViewController;
        NotificationShadeWindowView notificationShadeWindowView2 = notificationShadeWindowViewController.mView;
        notificationShadeWindowViewController.mStackScrollLayout = (NotificationStackScrollLayout) notificationShadeWindowView2.findViewById(R.id.notification_stack_scroller);
        notificationShadeWindowView2.mLayoutInsetProvider = notificationShadeWindowViewController.mNotificationInsetsController;
        notificationShadeWindowView2.mInteractionEventHandler = notificationShadeWindowViewController.new C24461();
        notificationShadeWindowView2.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() { // from class: com.android.systemui.shade.NotificationShadeWindowViewController.2
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
        notificationShadeDepthController.root = notificationShadeWindowView2;
        notificationShadeDepthController.onPanelExpansionChanged(notificationShadeWindowViewController.mShadeExpansionStateManager.addExpansionListener(notificationShadeDepthController));
        NotificationPanelViewController notificationPanelViewController = this.mCentralSurfacesComponent.getNotificationPanelViewController();
        this.mNotificationPanelViewController = notificationPanelViewController;
        this.mShadeSurface = notificationPanelViewController;
        ShadeController shadeController = this.mShadeController;
        ShadeControllerImpl shadeControllerImpl = (ShadeControllerImpl) shadeController;
        shadeControllerImpl.mNotificationPanelViewController = notificationPanelViewController;
        notificationPanelViewController.mTrackingStartedListener = new ShadeControllerImpl$$ExternalSyntheticLambda0(shadeControllerImpl);
        notificationPanelViewController.mOpenCloseListener = shadeControllerImpl.new C24622();
        shadeControllerImpl.mNotificationShadeWindowViewController = this.mNotificationShadeWindowViewController;
        this.mStackScrollerController = this.mCentralSurfacesComponent.getNotificationStackScrollLayoutController();
        this.mQsController = this.mCentralSurfacesComponent.getQuickSettingsController();
        this.mStackScroller = this.mStackScrollerController.mView;
        this.mNotifListContainer = this.mCentralSurfacesComponent.getNotificationListContainer();
        this.mPresenter = this.mCentralSurfacesComponent.getNotificationPresenter();
        this.mNotificationActivityStarter = this.mCentralSurfacesComponent.getNotificationActivityStarter();
        this.mNotificationShelfController = this.mCentralSurfacesComponent.getNotificationShelfController();
        StatusBarHeadsUpChangeListener statusBarHeadsUpChangeListener = this.mCentralSurfacesComponent.getStatusBarHeadsUpChangeListener();
        HeadsUpManagerPhone headsUpManagerPhone = this.mHeadsUpManager;
        headsUpManagerPhone.addListener(statusBarHeadsUpChangeListener);
        this.mDemoModeController.addCallback((DemoMode) this.mDemoModeCallback);
        CentralSurfacesCommandQueueCallbacks centralSurfacesCommandQueueCallbacks = this.mCommandQueueCallbacks;
        CommandQueue commandQueue = this.mCommandQueue;
        if (centralSurfacesCommandQueueCallbacks != null) {
            commandQueue.removeCallback((CommandQueue.Callbacks) centralSurfacesCommandQueueCallbacks);
        }
        CentralSurfacesCommandQueueCallbacks centralSurfacesCommandQueueCallbacks2 = this.mCentralSurfacesComponent.getCentralSurfacesCommandQueueCallbacks();
        this.mCommandQueueCallbacks = centralSurfacesCommandQueueCallbacks2;
        commandQueue.addCallback((CommandQueue.Callbacks) centralSurfacesCommandQueueCallbacks2);
        if (QpRune.QUICK_PANEL_BLUR) {
            NotificationShadeWindowView notificationShadeWindowView3 = this.mNotificationShadeWindowViewController.mView;
            SecQpBlurController secQpBlurController = this.mBlurController;
            secQpBlurController.mRoot = notificationShadeWindowView3;
            SecPanelBackgroundController secPanelBackgroundController = this.mCentralSurfacesComponent.getSecPanelBackgroundController();
            secQpBlurController.mBackgroundController = secPanelBackgroundController;
            SecQpBlurController.C11322 c11322 = secQpBlurController.mBlurUtils;
            secPanelBackgroundController.mBlurUtils = c11322;
            CapturedBlurContainerController capturedBlurContainerController = this.mCentralSurfacesComponent.getCapturedBlurContainerController();
            secQpBlurController.mCapturedBlurController = capturedBlurContainerController;
            capturedBlurContainerController.mBlurUtils = c11322;
        }
        updateResources();
        this.mNotificationShadeWindowView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda23
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                centralSurfacesImpl.mAutoHideController.checkUserAutoHide(motionEvent);
                NotificationRemoteInputManager notificationRemoteInputManager = centralSurfacesImpl.mRemoteInputManager;
                notificationRemoteInputManager.getClass();
                if (motionEvent.getAction() == 4 && motionEvent.getX() == 0.0f && motionEvent.getY() == 0.0f && notificationRemoteInputManager.isRemoteInputActive()) {
                    notificationRemoteInputManager.closeRemoteInputs(false);
                }
                ShadeControllerImpl shadeControllerImpl2 = (ShadeControllerImpl) centralSurfacesImpl.mShadeController;
                shadeControllerImpl2.getClass();
                if (motionEvent.getAction() == 1) {
                    if (shadeControllerImpl2.mExpandedVisible) {
                        ((NotificationGutsManager) Dependency.get(NotificationGutsManager.class)).getClass();
                    }
                    ((NotificationGutsManager) Dependency.get(NotificationGutsManager.class)).getClass();
                }
                return centralSurfacesImpl.mNotificationShadeWindowView.onTouchEvent(motionEvent);
            }
        });
        this.mWallpaperController.rootView = this.mNotificationShadeWindowView;
        Flags flags = Flags.INSTANCE;
        this.mFeatureFlags.getClass();
        NotificationShelfController notificationShelfController = this.mNotificationShelfController;
        NotificationIconAreaController notificationIconAreaController = this.mNotificationIconAreaController;
        FeatureFlags featureFlags = notificationIconAreaController.mFeatureFlags;
        NotificationShelfController.assertRefactorFlagDisabled();
        notificationIconAreaController.mShelfIcons = notificationShelfController.getShelfIcons();
        ShadeExpansionStateManager shadeExpansionStateManager = this.mShadeExpansionStateManager;
        NotificationWakeUpCoordinator notificationWakeUpCoordinator = this.mWakeUpCoordinator;
        notificationWakeUpCoordinator.onPanelExpansionChanged(shadeExpansionStateManager.addExpansionListener(notificationWakeUpCoordinator));
        PluginDependencyProvider pluginDependencyProvider = this.mPluginDependencyProvider;
        pluginDependencyProvider.allowPluginDependency(DarkIconDispatcher.class);
        pluginDependencyProvider.allowPluginDependency(StatusBarStateController.class);
        CentralSurfacesImpl$$ExternalSyntheticLambda0 centralSurfacesImpl$$ExternalSyntheticLambda0 = new CentralSurfacesImpl$$ExternalSyntheticLambda0(this);
        final StatusBarInitializer statusBarInitializer = this.mStatusBarInitializer;
        statusBarInitializer.statusBarViewUpdatedListener = centralSurfacesImpl$$ExternalSyntheticLambda0;
        CentralSurfacesComponent centralSurfacesComponent = this.mCentralSurfacesComponent;
        Objects.requireNonNull(centralSurfacesComponent);
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
                    centralSurfacesImpl$$ExternalSyntheticLambda02.onStatusBarViewUpdated(statusBarFragmentComponent.getPhoneStatusBarViewController(), statusBarFragmentComponent.getPhoneStatusBarTransitions());
                }
                for (LetterboxAppearanceCalculator letterboxAppearanceCalculator : statusBarInitializer2.creationListeners) {
                    letterboxAppearanceCalculator.getClass();
                    letterboxAppearanceCalculator.statusBarBoundsProvider = statusBarFragmentComponent.getBoundsProvider();
                }
            }

            @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
            public final void onFragmentViewDestroyed(Fragment fragment) {
            }
        });
        fragmentHostManager.getFragmentManager().beginTransaction().replace(R.id.status_bar_container, centralSurfacesComponent.createCollapsedStatusBarFragment(), "CollapsedStatusBarFragment").commit();
        NotificationShadeWindowView notificationShadeWindowView4 = this.mNotificationShadeWindowView;
        StatusBarTouchableRegionManager statusBarTouchableRegionManager = this.mStatusBarTouchableRegionManager;
        statusBarTouchableRegionManager.mCentralSurfaces = this;
        statusBarTouchableRegionManager.mNotificationShadeWindowView = notificationShadeWindowView4;
        statusBarTouchableRegionManager.mNotificationPanelView = notificationShadeWindowView4.findViewById(R.id.notification_panel);
        NavigationBarController navigationBarController = this.mNavigationBarController;
        navigationBarController.updateAccessibilityButtonModeIfNeeded();
        final int i5 = 0;
        boolean z = BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY || !navigationBarController.initializeTaskbarIfNecessary();
        DisplayTracker displayTracker = navigationBarController.mDisplayTracker;
        for (Display display : ((DisplayTrackerImpl) displayTracker).displayManager.getDisplays()) {
            if (!z) {
                int displayId = display.getDisplayId();
                displayTracker.getClass();
                i = displayId == 0 ? i + 1 : 0;
            }
            navigationBarController.createNavigationBar(display, null, registerStatusBarResult);
        }
        if (this.mWallpaperSupported) {
            this.mLockscreenWallpaper = (LockscreenWallpaper) this.mLockscreenWallpaperLazy.get();
        }
        boolean z2 = LsRune.AOD_SUB_DISPLAY_LOCK;
        CentralSurfacesImpl$$ExternalSyntheticLambda6 centralSurfacesImpl$$ExternalSyntheticLambda6 = this.mLockScreenWallpaperChangeCallback;
        if (z2) {
            ((SettingsHelper) Dependency.get(SettingsHelper.class)).registerCallback(centralSurfacesImpl$$ExternalSyntheticLambda6, Settings.System.getUriFor("lockscreen_wallpaper"), Settings.System.getUriFor("lockscreen_wallpaper_sub"));
        } else {
            ((SettingsHelper) Dependency.get(SettingsHelper.class)).registerCallback(centralSurfacesImpl$$ExternalSyntheticLambda6, Settings.System.getUriFor("lockscreen_wallpaper"));
        }
        this.mKeyguardIndicationController.setUpperTextView((KeyguardIndicationTextView) this.mNotificationShadeWindowView.findViewById(R.id.keyguard_upper_fingerprint_indication));
        this.mAmbientIndicationContainer = this.mNotificationShadeWindowView.findViewById(R.id.ambient_indication_container);
        this.mAutoHideController.mStatusBar = new AutoHideUiElement() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.6
            @Override // com.android.systemui.statusbar.AutoHideUiElement
            public final void hide() {
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                if (centralSurfacesImpl.mTransientShown) {
                    centralSurfacesImpl.mTransientShown = false;
                    centralSurfacesImpl.maybeUpdateBarMode();
                }
            }

            @Override // com.android.systemui.statusbar.AutoHideUiElement
            public final boolean isVisible() {
                return CentralSurfacesImpl.this.mTransientShown;
            }

            @Override // com.android.systemui.statusbar.AutoHideUiElement
            public final boolean shouldHideOnTouch() {
                return !CentralSurfacesImpl.this.mRemoteInputManager.isRemoteInputActive();
            }

            @Override // com.android.systemui.statusbar.AutoHideUiElement
            public final void synchronizeState() {
                CentralSurfacesImpl.this.checkBarModes();
            }
        };
        ScrimView scrimView = (ScrimView) this.mNotificationShadeWindowView.findViewById(R.id.scrim_behind);
        ScrimView scrimView2 = (ScrimView) this.mNotificationShadeWindowView.findViewById(R.id.scrim_notifications);
        ScrimView scrimView3 = (ScrimView) this.mNotificationShadeWindowView.findViewById(R.id.scrim_in_front);
        int i6 = 4;
        CentralSurfacesImpl$$ExternalSyntheticLambda13 centralSurfacesImpl$$ExternalSyntheticLambda13 = new CentralSurfacesImpl$$ExternalSyntheticLambda13(this, i6);
        final ScrimController scrimController = this.mScrimController;
        scrimController.mScrimVisibleListener = centralSurfacesImpl$$ExternalSyntheticLambda13;
        ((KeyguardFastBioUnlockController) Dependency.get(KeyguardFastBioUnlockController.class)).scrimUpdater = new CentralSurfacesImpl$$ExternalSyntheticLambda5(this, 5);
        scrimController.mNotificationsScrim = scrimView2;
        scrimController.mScrimBehind = scrimView;
        scrimController.mScrimInFront = scrimView3;
        final SecLsScrimControlHelper secLsScrimControlHelper = scrimController.mSecLsScrimControlHelper;
        final int i7 = 2;
        final int i8 = 3;
        SecLsScrimControlProvider secLsScrimControlProvider = new SecLsScrimControlProvider(new Supplier() { // from class: com.android.systemui.statusbar.phone.ScrimController$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i5) {
                    case 0:
                        return scrimController.mNotificationsScrim;
                    case 1:
                        return scrimController.mScrimBehind;
                    case 2:
                        return scrimController.mScrimInFront;
                    default:
                        return Boolean.valueOf(scrimController.mKeyguardOccluded);
                }
            }
        }, new Supplier() { // from class: com.android.systemui.statusbar.phone.ScrimController$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i4) {
                    case 0:
                        return scrimController.mNotificationsScrim;
                    case 1:
                        return scrimController.mScrimBehind;
                    case 2:
                        return scrimController.mScrimInFront;
                    default:
                        return Boolean.valueOf(scrimController.mKeyguardOccluded);
                }
            }
        }, new Supplier() { // from class: com.android.systemui.statusbar.phone.ScrimController$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i7) {
                    case 0:
                        return scrimController.mNotificationsScrim;
                    case 1:
                        return scrimController.mScrimBehind;
                    case 2:
                        return scrimController.mScrimInFront;
                    default:
                        return Boolean.valueOf(scrimController.mKeyguardOccluded);
                }
            }
        }, new Supplier() { // from class: com.android.systemui.statusbar.phone.ScrimController$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i8) {
                    case 0:
                        return scrimController.mNotificationsScrim;
                    case 1:
                        return scrimController.mScrimBehind;
                    case 2:
                        return scrimController.mScrimInFront;
                    default:
                        return Boolean.valueOf(scrimController.mKeyguardOccluded);
                }
            }
        }, new ScrimController$$ExternalSyntheticLambda0(scrimController, i6), new ScrimController$$ExternalSyntheticLambda2(scrimController, 1));
        secLsScrimControlHelper.mProvider = secLsScrimControlProvider;
        secLsScrimControlHelper.mScrimInFront = (ScrimView) secLsScrimControlProvider.mFrontScrimSupplier.get();
        secLsScrimControlHelper.mScrimBehind = (ScrimView) secLsScrimControlProvider.mBehindScrimSupplier.get();
        secLsScrimControlHelper.mNotificationsScrim = (ScrimView) secLsScrimControlProvider.mNotificationsScrimSupplier.get();
        if (LsRune.SECURITY_OPEN_THEME) {
            WallpaperUtils.registerSystemUIWidgetCallback(secLsScrimControlHelper, SystemUIWidgetUtil.convertFlag("background"));
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
        PanelScreenShotLogger.INSTANCE.addLogProvider("ScrimController", secLsScrimControlHelper);
        scrimController.updateThemeColors();
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
        Runnable runnable = scrimController.mScrimBehindChangeRunnable;
        if (runnable != null) {
            ScrimView scrimView4 = scrimController.mScrimBehind;
            Executor executor = scrimController.mMainExecutor;
            scrimView4.mChangeRunnable = runnable;
            scrimView4.mChangeRunnableExecutor = executor;
            scrimController.mScrimBehindChangeRunnable = null;
        }
        ScrimState[] values = ScrimState.values();
        for (int i9 = 0; i9 < values.length; i9++) {
            ScrimState scrimState = values[i9];
            ScrimView scrimView5 = scrimController.mScrimInFront;
            ScrimView scrimView6 = scrimController.mScrimBehind;
            DozeParameters dozeParameters = scrimController.mDozeParameters;
            DockManager dockManager = scrimController.mDockManager;
            AODAmbientWallpaperHelper aODAmbientWallpaperHelper = scrimController.mAODAmbientWallpaperHelper;
            scrimState.mScrimInFront = scrimView5;
            scrimState.mScrimBehind = scrimView6;
            scrimState.mDozeParameters = dozeParameters;
            scrimState.mDockManager = dockManager;
            scrimState.mDisplayRequiresBlanking = dozeParameters.getDisplayNeedsBlanking();
            if (LsRune.AOD_FULLSCREEN) {
                scrimState.mAODAmbientWallpaperHelper = aODAmbientWallpaperHelper;
            }
            ScrimState scrimState2 = values[i9];
            scrimState2.mScrimBehindAlphaKeyguard = scrimController.mScrimBehindAlphaKeyguard;
            scrimState2.mDefaultScrimAlpha = scrimController.mDefaultScrimAlpha;
        }
        scrimController.mScrimColorState = new ScrimStateLogger(scrimController.mScrimInFront, scrimController.mNotificationsScrim, scrimController.mScrimBehind, scrimController.new C31063());
        scrimController.mScrimBehind.setDefaultFocusHighlightEnabled(false);
        scrimController.mNotificationsScrim.setDefaultFocusHighlightEnabled(false);
        scrimController.mScrimInFront.setDefaultFocusHighlightEnabled(false);
        scrimController.mTransparentScrimBackground = scrimView2.getResources().getBoolean(R.bool.notification_scrim_transparent);
        scrimController.updateScrims();
        scrimController.mKeyguardUpdateMonitor.registerCallback(scrimController.mKeyguardVisibilityCallback);
        for (ScrimState scrimState3 : ScrimState.values()) {
            scrimState3.prepare(scrimState3);
        }
        ScrimController$$ExternalSyntheticLambda2 scrimController$$ExternalSyntheticLambda2 = new ScrimController$$ExternalSyntheticLambda2(scrimController, 2);
        scrimController.mPrimaryBouncerToGoneTransition = scrimController$$ExternalSyntheticLambda2;
        JavaAdapterKt.collectFlow(scrimView, scrimController.mKeyguardTransitionInteractor.primaryBouncerToGoneTransition, scrimController$$ExternalSyntheticLambda2, scrimController.mMainDispatcher);
        JavaAdapterKt.collectFlow(scrimView, scrimController.mPrimaryBouncerToGoneTransitionViewModel.scrimAlpha, scrimController.mScrimAlphaConsumer, scrimController.mMainDispatcher);
        Flags flags2 = Flags.INSTANCE;
        CentralSurfacesImpl$$ExternalSyntheticLambda13 centralSurfacesImpl$$ExternalSyntheticLambda132 = new CentralSurfacesImpl$$ExternalSyntheticLambda13(this, 5);
        LightRevealScrim lightRevealScrim = this.mLightRevealScrim;
        lightRevealScrim.isScrimOpaqueChangedListener = centralSurfacesImpl$$ExternalSyntheticLambda132;
        ScreenOffAnimationController screenOffAnimationController = this.mScreenOffAnimationController;
        Iterator it = ((ArrayList) screenOffAnimationController.animations).iterator();
        while (it.hasNext()) {
            ((ScreenOffAnimation) it.next()).initialize(this, lightRevealScrim);
        }
        screenOffAnimationController.wakefulnessLifecycle.addObserver(screenOffAnimationController);
        updateLightRevealScrimVisibility();
        ShadeSurface shadeSurface = this.mShadeSurface;
        Objects.requireNonNull(shadeController);
        CentralSurfacesImpl$$ExternalSyntheticLambda1 centralSurfacesImpl$$ExternalSyntheticLambda1 = new CentralSurfacesImpl$$ExternalSyntheticLambda1(shadeController, 3);
        NotificationShelfController notificationShelfController2 = this.mNotificationShelfController;
        NotificationPanelViewController notificationPanelViewController2 = (NotificationPanelViewController) shadeSurface;
        notificationPanelViewController2.mHeadsUpManager = headsUpManagerPhone;
        headsUpManagerPhone.addListener(notificationPanelViewController2.mOnHeadsUpChangedListener);
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController2.mNotificationStackScrollLayoutController;
        notificationPanelViewController2.mHeadsUpTouchHelper = new HeadsUpTouchHelper(headsUpManagerPhone, notificationStackScrollLayoutController.mView.mHeadsUpCallback, new NotificationPanelViewController.HeadsUpNotificationViewControllerImpl(notificationPanelViewController2, 0));
        notificationPanelViewController2.mCentralSurfaces = this;
        notificationPanelViewController2.mGestureRecorder = null;
        notificationPanelViewController2.mHideExpandedRunnable = centralSurfacesImpl$$ExternalSyntheticLambda1;
        notificationPanelViewController2.mNotificationShelfController = notificationShelfController2;
        Flags flags3 = Flags.INSTANCE;
        notificationPanelViewController2.mFeatureFlags.getClass();
        NotificationShelfController.assertRefactorFlagDisabled();
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        FeatureFlags featureFlags2 = notificationStackScrollLayout.mAmbientState.mFeatureFlags;
        NotificationShelfController.assertRefactorFlagDisabled();
        NotificationShelf notificationShelf = notificationStackScrollLayout.mShelf;
        if (notificationShelf != null) {
            i2 = notificationStackScrollLayout.indexOfChild(notificationShelf);
            notificationStackScrollLayout.removeView(notificationStackScrollLayout.mShelf);
        } else {
            i2 = -1;
        }
        NotificationShelf view = notificationShelfController2.getView();
        notificationStackScrollLayout.mShelf = view;
        notificationStackScrollLayout.addView(view, i2);
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        NotificationShelf notificationShelf2 = notificationStackScrollLayout.mShelf;
        ambientState.mShelf = notificationShelf2;
        notificationStackScrollLayout.mStateAnimator.mShelf = notificationShelf2;
        notificationShelfController2.bind(ambientState, notificationStackScrollLayout.mController);
        NotificationShelfManager notificationShelfManager = notificationStackScrollLayout.mShelfManager;
        NotificationStackScrollLayout$$ExternalSyntheticLambda0 notificationStackScrollLayout$$ExternalSyntheticLambda0 = new NotificationStackScrollLayout$$ExternalSyntheticLambda0(notificationStackScrollLayout, 1);
        TextView textView = notificationShelfManager.mClearAllButton;
        if (textView != null) {
            textView.setOnClickListener(notificationStackScrollLayout$$ExternalSyntheticLambda0);
        }
        NotificationShelfManager notificationShelfManager2 = notificationStackScrollLayout.mShelfManager;
        boolean z3 = !notificationShelfManager2.settingsHelper.isEmergencyMode();
        LinearLayout linearLayout = notificationShelfManager2.mNotiSettingContainer;
        if (linearLayout != null) {
            linearLayout.setEnabled(z3);
            if (linearLayout.getVisibility() == 0) {
                if (z3) {
                    linearLayout.setAlpha(1.0f);
                } else {
                    linearLayout.setAlpha(0.3f);
                }
            }
        }
        notificationShelfManager2.updateShelfButtonBackground();
        ViewOnClickListenerC2949xbae1b0c7 viewOnClickListenerC2949xbae1b0c7 = new ViewOnClickListenerC2949xbae1b0c7(notificationStackScrollLayoutController, 2);
        LinearLayout linearLayout2 = notificationStackScrollLayoutController.mShelfManager.mNotiSettingContainer;
        if (linearLayout2 != null) {
            linearLayout2.setOnClickListener(viewOnClickListenerC2949xbae1b0c7);
        }
        final LockscreenShadeTransitionController lockscreenShadeTransitionController = notificationPanelViewController2.mLockscreenShadeTransitionController;
        lockscreenShadeTransitionController.getClass();
        notificationShelfController2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$bindController$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LockscreenShadeTransitionController lockscreenShadeTransitionController2 = LockscreenShadeTransitionController.this;
                if (((StatusBarStateControllerImpl) lockscreenShadeTransitionController2.statusBarStateController).mState == 1) {
                    CentralSurfaces centralSurfaces = lockscreenShadeTransitionController2.centralSurfaces;
                    if (centralSurfaces == null) {
                        centralSurfaces = null;
                    }
                    ((CentralSurfacesImpl) centralSurfaces).wakeUpIfDozing(SystemClock.uptimeMillis(), "SHADE_CLICK", 4);
                    LockscreenShadeTransitionController.this.goToLockedShade(view2, false);
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "1005", "Tap more strip");
                }
            }
        });
        notificationPanelViewController2.updateMaxDisplayedNotifications(true);
        BackDropView backDropView = (BackDropView) this.mNotificationShadeWindowView.findViewById(R.id.backdrop);
        KeyguardWallpaper keyguardWallpaper = this.mKeyguardWallpaper;
        backDropView.mKeyguardWallpaper = keyguardWallpaper;
        if (!backDropView.mIsKwpInitiated && keyguardWallpaper != null) {
            backDropView.mIsKwpInitiated = true;
            ((KeyguardWallpaperController) keyguardWallpaper).setRootView(backDropView);
        }
        boolean isLockscreenLiveWallpaperEnabled = this.mWallpaperManager.isLockscreenLiveWallpaperEnabled();
        NotificationMediaManager notificationMediaManager = this.mMediaManager;
        if (isLockscreenLiveWallpaperEnabled) {
            notificationMediaManager.mBackdrop = null;
            notificationMediaManager.mBackdropFront = null;
            notificationMediaManager.mBackdropBack = null;
            notificationMediaManager.mLockscreenWallpaper = null;
        } else {
            ImageView imageView = (ImageView) backDropView.findViewById(R.id.backdrop_front);
            ImageView imageView2 = (ImageView) backDropView.findViewById(R.id.backdrop_back);
            LockscreenWallpaper lockscreenWallpaper = this.mLockscreenWallpaper;
            notificationMediaManager.mBackdrop = backDropView;
            notificationMediaManager.mBackdropFront = imageView;
            notificationMediaManager.mBackdropBack = imageView2;
            notificationMediaManager.mLockscreenWallpaper = lockscreenWallpaper;
        }
        Context context = this.mContext;
        context.getResources().getFloat(android.R.dimen.config_scrollbarSize);
        Lazy lazy = this.mNotificationShadeDepthControllerLazy;
        ((ArrayList) ((NotificationShadeDepthController) lazy.get()).listeners).add(new ViewCompat$$ExternalSyntheticLambda0());
        ShadeSurface shadeSurface2 = this.mShadeSurface;
        boolean z4 = this.mUserSetup;
        NotificationPanelViewController notificationPanelViewController3 = (NotificationPanelViewController) shadeSurface2;
        notificationPanelViewController3.mUserSetupComplete = z4;
        notificationPanelViewController3.mKeyguardBottomAreaViewController.setUserSetupComplete(z4);
        View findViewById = this.mNotificationShadeWindowView.findViewById(R.id.qs_frame);
        if (findViewById != null) {
            FragmentHostManager fragmentHostManager2 = fragmentService.getFragmentHostManager(findViewById);
            ExtensionControllerImpl extensionControllerImpl = (ExtensionControllerImpl) this.mExtensionController;
            extensionControllerImpl.getClass();
            ExtensionControllerImpl.ExtensionBuilder extensionBuilder = new ExtensionControllerImpl.ExtensionBuilder(extensionControllerImpl, 0);
            extensionBuilder.withPlugin(InterfaceC1922QS.class);
            Supplier supplier = new Supplier() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda21
                @Override // java.util.function.Supplier
                public final Object get() {
                    CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                    FragmentHostManager fragmentHostManager3 = centralSurfacesImpl.mFragmentService.getFragmentHostManager(centralSurfacesImpl.mNotificationShadeWindowView);
                    fragmentHostManager3.getClass();
                    String name = QSFragment.class.getName();
                    return (InterfaceC1922QS) fragmentHostManager3.mPlugins.instantiate(fragmentHostManager3.mContext, name, null);
                }
            };
            ExtensionControllerImpl.ExtensionImpl extensionImpl = extensionBuilder.mExtension;
            extensionImpl.mProducers.add(new ExtensionControllerImpl.ExtensionImpl.Default(extensionImpl, supplier));
            ExtensionFragmentListener.attachExtensonToFragment(fragmentService, findViewById, extensionBuilder.build());
            this.mBrightnessMirrorController = new BrightnessMirrorController(this.mNotificationShadeWindowView, this.mShadeSurface, (NotificationShadeDepthController) lazy.get(), this.mBrightnessSliderFactory, new CentralSurfacesImpl$$ExternalSyntheticLambda13(this, 6), this.mBlurController);
            fragmentHostManager2.addTagListener(InterfaceC1922QS.TAG, new FragmentHostManager.FragmentListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda22
                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
                public final void onFragmentViewCreated(Fragment fragment) {
                    CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                    centralSurfacesImpl.getClass();
                    InterfaceC1922QS interfaceC1922QS = (InterfaceC1922QS) fragment;
                    if (interfaceC1922QS instanceof QSFragment) {
                        QSFragment qSFragment = (QSFragment) interfaceC1922QS;
                        centralSurfacesImpl.mQSPanelController = qSFragment.mQSPanelController;
                        centralSurfacesImpl.mQuickQSPanelController = qSFragment.mQuickQSPanelController;
                    }
                }
            });
        }
        View findViewById2 = this.mNotificationShadeWindowView.findViewById(R.id.report_rejected_touch);
        this.mReportRejectedTouch = findViewById2;
        if (findViewById2 != null) {
            updateReportRejectedTouchVisibility();
            this.mReportRejectedTouch.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda20
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
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
        PowerManager powerManager = this.mPowerManager;
        if (!powerManager.isInteractive()) {
            this.mBroadcastReceiver.onReceive(context, new Intent("android.intent.action.SCREEN_OFF"));
        }
        this.mGestureWakeLock = powerManager.newWakeLock(10, "sysui:GestureWakeLock");
        registerBroadcastReceiver();
        this.mContext.registerReceiverAsUser(this.mDemoReceiver, UserHandle.ALL, new IntentFilter(), "android.permission.DUMP", null, 2);
        DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = (DeviceProvisionedControllerImpl) this.mDeviceProvisionedController;
        C301119 c301119 = this.mUserSetupObserver;
        deviceProvisionedControllerImpl.addCallback(c301119);
        c301119.onUserSetupChanged();
        ThreadedRenderer.overrideProperty("disableProfileBars", "true");
        ThreadedRenderer.overrideProperty("ambientRatio", String.valueOf(1.5f));
        this.mBroadcastDispatcher.registerReceiver(this.mRemoteInputActionBroadcastReceiver, new IntentFilter("com.samsung.systemui.action.REQUEST_REMOTE_INPUT"), null, UserHandle.ALL);
        notificationShadeWindowControllerImpl.getClass();
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
        layoutParams3.privateFlags = layoutParams3.privateFlags | 512 | 134217728;
        layoutParams3.insetsFlags.behavior = 2;
        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY_OPTICAL) {
            layoutParams3.samsungFlags |= 131072;
        }
        if (notificationShadeWindowControllerImpl.mIndicatorCutoutUtil.isUDCModel) {
            layoutParams3.semAddExtensionFlags(8192);
        }
        notificationShadeWindowControllerImpl.mWindowManager.addView(notificationShadeWindowControllerImpl.mNotificationShadeView, notificationShadeWindowControllerImpl.mLp);
        notificationShadeWindowControllerImpl.mLpChanged.copyFrom(notificationShadeWindowControllerImpl.mLp);
        notificationShadeWindowControllerImpl.onThemeChanged();
        if (notificationShadeWindowControllerImpl.mKeyguardViewMediator.isShowingAndNotOccluded()) {
            NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
            i3 = 1;
            notificationShadeWindowState.keyguardShowing = true;
            notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
        } else {
            i3 = 1;
        }
        NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda12 notificationShadeWindowControllerImpl$$ExternalSyntheticLambda12 = new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda12(notificationShadeWindowControllerImpl, i3);
        final SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = notificationShadeWindowControllerImpl.mHelper;
        PluginLockMediator pluginLockMediator = secNotificationShadeWindowControllerHelperImpl.pluginLockMediator;
        if (pluginLockMediator != null) {
            ViewGroup viewGroup = secNotificationShadeWindowControllerHelperImpl.notificationShadeView;
            PluginLockMediatorImpl pluginLockMediatorImpl = (PluginLockMediatorImpl) pluginLockMediator;
            Log.d("PluginLockMediatorImpl", "onRootViewAttached");
            SPluginManager sPluginManager = pluginLockMediatorImpl.mSPluginManager;
            if (sPluginManager != null) {
                sPluginManager.addPluginListener((SPluginListener) pluginLockMediatorImpl, PluginLock.class, true);
            }
            KeyguardListener$SPlugin keyguardListener$SPlugin = pluginLockMediatorImpl.mSPluginListener;
            if (keyguardListener$SPlugin != null) {
                Log.d("PluginLockManagerImpl", "onRootViewAttached : " + viewGroup);
                PluginLockDelegateApp pluginLockDelegateApp = ((PluginLockManagerImpl) keyguardListener$SPlugin).mDelegateApp;
                pluginLockDelegateApp.getClass();
                Log.d("PluginLockDelegateApp", "onRootViewAttached");
                pluginLockDelegateApp.mRootView = viewGroup;
            }
        }
        Consumer consumer = new Consumer() { // from class: com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl$attach$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SecNotificationShadeWindowControllerHelperImpl.access$setScreenOrientation(SecNotificationShadeWindowControllerHelperImpl.this, ((Boolean) obj).booleanValue());
            }
        };
        KeyguardWallpaperController keyguardWallpaperController = (KeyguardWallpaperController) secNotificationShadeWindowControllerHelperImpl.keyguardWallpaper;
        keyguardWallpaperController.getClass();
        Log.d("KeyguardWallpaperController", "setNoSensorConsumer() consumer:" + consumer);
        keyguardWallpaperController.mNoSensorConsumer = consumer;
        keyguardWallpaperController.disableRotateIfNeeded();
        Log.d("KeyguardWallpaperController", "setWideColorGamutConsumer() consumer:" + notificationShadeWindowControllerImpl$$ExternalSyntheticLambda12);
        keyguardWallpaperController.mWcgConsumer = notificationShadeWindowControllerImpl$$ExternalSyntheticLambda12;
        SystemUIWallpaperBase systemUIWallpaperBase = keyguardWallpaperController.mWallpaperView;
        if (systemUIWallpaperBase != null) {
            notificationShadeWindowControllerImpl$$ExternalSyntheticLambda12.accept(Boolean.valueOf(systemUIWallpaperBase instanceof KeyguardImageWallpaper));
        }
        final StatusBarWindowController statusBarWindowController2 = this.mStatusBarWindowController;
        statusBarWindowController2.getClass();
        Trace.beginSection("StatusBarWindowController.getBarLayoutParams");
        WindowManager.LayoutParams barLayoutParamsForRotation = statusBarWindowController2.getBarLayoutParamsForRotation(statusBarWindowController2.mContext.getDisplay().getRotation());
        barLayoutParamsForRotation.paramsForRotation = new WindowManager.LayoutParams[4];
        for (int i10 = 0; i10 <= 3; i10++) {
            barLayoutParamsForRotation.paramsForRotation[i10] = statusBarWindowController2.getBarLayoutParamsForRotation(i10);
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
        statusBarWindowController2.apply(statusBarWindowController2.mCurrentState, false);
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        SubscreenNotificationController subscreenNotificationController;
        Unit unit;
        PrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        synchronized (this.mQueueLock) {
            asIndenting.println("Current Status Bar state:");
            asIndenting.println("  mExpandedVisible=" + ((ShadeControllerImpl) this.mShadeController).mExpandedVisible);
            asIndenting.println("  mDisplayMetrics=" + this.mDisplayMetrics);
            asIndenting.print("  mStackScroller: " + CentralSurfaces.viewInfo(this.mStackScroller));
            asIndenting.print(" scroll " + this.mStackScroller.getScrollX() + "," + this.mStackScroller.getScrollY());
            StringBuilder sb = new StringBuilder(" translationX ");
            sb.append(this.mStackScroller.getTranslationX());
            asIndenting.println(sb.toString());
        }
        asIndenting.print("  mInteractingWindows=");
        asIndenting.println(this.mInteractingWindows);
        asIndenting.print("  mStatusBarWindowState=");
        asIndenting.println(StatusBarManager.windowStateToString(this.mStatusBarWindowState));
        asIndenting.print("  mStatusBarMode=");
        asIndenting.println(BarTransitions.modeToString(this.mStatusBarMode));
        asIndenting.print("  mDozing=");
        asIndenting.println(this.mDozing);
        asIndenting.print("  mWallpaperSupported= ");
        asIndenting.println(this.mWallpaperSupported);
        asIndenting.println("  ShadeWindowView: ");
        NotificationShadeWindowViewController notificationShadeWindowViewController = this.mNotificationShadeWindowViewController;
        if (notificationShadeWindowViewController != null) {
            asIndenting.print("  mExpandAnimationRunning=");
            asIndenting.println(notificationShadeWindowViewController.mExpandAnimationRunning);
            asIndenting.print("  mTouchCancelled=");
            asIndenting.println(notificationShadeWindowViewController.mTouchCancelled);
            asIndenting.print("  mTouchActive=");
            asIndenting.println(notificationShadeWindowViewController.mTouchActive);
            CentralSurfaces.dumpBarTransitions(asIndenting, "PhoneStatusBarTransitions", this.mStatusBarTransitions);
        }
        asIndenting.println("  mMediaManager: ");
        NotificationMediaManager notificationMediaManager = this.mMediaManager;
        if (notificationMediaManager != null) {
            notificationMediaManager.dump(asIndenting, strArr);
        }
        asIndenting.println("  Panels: ");
        asIndenting.println("  mStackScroller: " + this.mStackScroller + " (dump moved)");
        asIndenting.println("  Theme:");
        asIndenting.println("    dark theme: " + (this.mUiModeManager == null ? "null" : this.mUiModeManager.getNightMode() + "") + " (auto: 0, yes: 2, no: 1)");
        DisplayCutoutBaseView$$ExternalSyntheticOutline0.m93m("    light wallpaper theme: ", this.mContext.getThemeResId() == 2132018537, asIndenting);
        KeyguardIndicationController keyguardIndicationController = this.mKeyguardIndicationController;
        if (keyguardIndicationController != null) {
            keyguardIndicationController.dump(asIndenting, strArr);
        }
        ScrimController scrimController = this.mScrimController;
        if (scrimController != null) {
            scrimController.dump(asIndenting, strArr);
        }
        if (this.mLightRevealScrim != null) {
            asIndenting.println("mLightRevealScrim.getRevealEffect(): " + this.mLightRevealScrim.revealEffect);
            asIndenting.println("mLightRevealScrim.getRevealAmount(): " + this.mLightRevealScrim.revealAmount);
        }
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager;
        if (statusBarKeyguardViewManager != null) {
            statusBarKeyguardViewManager.dump(asIndenting);
        }
        HeadsUpManagerPhone headsUpManagerPhone = this.mHeadsUpManager;
        if (headsUpManagerPhone != null) {
            headsUpManagerPhone.dump(asIndenting, strArr);
        } else {
            asIndenting.println("  mHeadsUpManager: null");
        }
        StatusBarTouchableRegionManager statusBarTouchableRegionManager = this.mStatusBarTouchableRegionManager;
        if (statusBarTouchableRegionManager != null) {
            statusBarTouchableRegionManager.dump(asIndenting, strArr);
        } else {
            asIndenting.println("  mStatusBarTouchableRegionManager: null");
        }
        LightBarController lightBarController = this.mLightBarController;
        if (lightBarController != null) {
            lightBarController.dump(asIndenting, strArr);
        }
        asIndenting.println("SharedPreferences:");
        Context context = this.mContext;
        for (Map.Entry<String, ?> entry : context.getSharedPreferences(context.getPackageName(), 0).getAll().entrySet()) {
            asIndenting.print("  ");
            asIndenting.print(entry.getKey());
            asIndenting.print("=");
            asIndenting.println(entry.getValue());
        }
        asIndenting.println("Camera gesture intents:");
        StringBuilder sb2 = new StringBuilder("   Insecure camera: ");
        CameraIntents.Companion.getClass();
        KeyguardShortcutManager.Companion companion = KeyguardShortcutManager.Companion;
        companion.getClass();
        sb2.append(KeyguardShortcutManager.INSECURE_CAMERA_INTENT);
        asIndenting.println(sb2.toString());
        StringBuilder sb3 = new StringBuilder("   Secure camera: ");
        companion.getClass();
        sb3.append(KeyguardShortcutManager.SECURE_CAMERA_INTENT);
        asIndenting.println(sb3.toString());
        StringBuilder sb4 = new StringBuilder("   Override package: ");
        String string = this.mContext.getResources().getString(R.string.config_cameraGesturePackage);
        if (string == null || TextUtils.isEmpty(string)) {
            string = null;
        }
        sb4.append(string);
        asIndenting.println(sb4.toString());
        if (BasicRune.NAVBAR_ENABLED) {
            NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) this.mNavBarStore;
            navBarStoreImpl.getClass();
            asIndenting.println("Dump of NavBarStoreImpl : ");
            asIndenting.increaseIndent();
            asIndenting.print("Number of created navigation bar : ");
            HashMap hashMap = navBarStoreImpl.navStateManager;
            asIndenting.println(hashMap.size());
            for (Map.Entry entry2 : hashMap.entrySet()) {
                int intValue = ((Number) entry2.getKey()).intValue();
                NavBarStateManager navBarStateManager = (NavBarStateManager) entry2.getValue();
                asIndenting.println("Navigationbar " + intValue + " states : ");
                asIndenting.increaseIndent();
                if (navBarStateManager != null) {
                    asIndenting.println(navBarStateManager.states.toString());
                    unit = Unit.INSTANCE;
                } else {
                    unit = null;
                }
                if (unit == null) {
                    asIndenting.println("NavBarStateManager is null.");
                }
                asIndenting.decreaseIndent();
            }
            PluginBarInteractionManager pluginBarInteractionManager = navBarStoreImpl.pluginBarInteractionManager;
            pluginBarInteractionManager.getClass();
            try {
                PluginNavigationBar pluginNavigationBar = pluginBarInteractionManager.pluginNavigationBar;
                if (pluginNavigationBar != null) {
                    pluginNavigationBar.dump(asIndenting);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            asIndenting.decreaseIndent();
        }
        if (NotiRune.NOTI_SUBSCREEN_ALL && (subscreenNotificationController = this.mSubscreenNotificationController) != null && subscreenNotificationController.mDeviceModel != null) {
            asIndenting.println("Current SubscreenNotificationController state:");
        }
        NotiCenterPlugin.INSTANCE.dump(asIndenting, strArr);
        AODAmbientWallpaperHelper aODAmbientWallpaperHelper = this.mAODAmbientWallpaperHelper;
        aODAmbientWallpaperHelper.getClass();
        asIndenting.print("  isMainWonderLandWallpaper=");
        asIndenting.println(aODAmbientWallpaperHelper.isMainWonderLandWallpaper);
        asIndenting.print("  isSubWonderLandWallpaper=");
        asIndenting.println(aODAmbientWallpaperHelper.isSubWonderLandWallpaper);
    }

    public final void finishKeyguardFadingAway() {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        keyguardStateControllerImpl.notifyKeyguardGoingAway(false);
        keyguardStateControllerImpl.setKeyguardFadingAway(false);
        this.mScrimController.mExpansionAffectsAlpha = true;
        this.mKeyguardViewMediator.maybeHandlePendingLock();
    }

    public final Intent getEmergencyActionIntent() {
        ResolveInfo resolveInfo;
        Intent intent = new Intent("com.android.systemui.action.LAUNCH_EMERGENCY");
        Context context = this.mContext;
        List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING);
        if (queryIntentActivities != null && !queryIntentActivities.isEmpty()) {
            String string = context.getString(R.string.config_preferredEmergencySosPackage);
            if (!TextUtils.isEmpty(string)) {
                Iterator<ResolveInfo> it = queryIntentActivities.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        resolveInfo = queryIntentActivities.get(0);
                        break;
                    }
                    ResolveInfo next = it.next();
                    if (TextUtils.equals(next.activityInfo.packageName, string)) {
                        resolveInfo = next;
                        break;
                    }
                }
            } else {
                resolveInfo = queryIntentActivities.get(0);
            }
        } else {
            resolveInfo = null;
        }
        if (resolveInfo == null) {
            Log.wtf("CentralSurfaces", "Couldn't find an app to process the emergency intent.");
            return null;
        }
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
        intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        return intent;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final LifecycleRegistry getLifecycle() {
        return this.mLifecycle;
    }

    public final NavigationBarView getNavigationBarView() {
        return this.mNavigationBarController.getNavigationBarView(this.mDisplayId);
    }

    public final ShadeViewController getShadeViewController() {
        return this.mShadeSurface;
    }

    public final boolean hideKeyguard() {
        ((StatusBarStateControllerImpl) this.mStatusBarStateController).mKeyguardRequested = false;
        return updateIsKeyguard(false);
    }

    public void initShadeVisibilityListener() {
        ((ShadeControllerImpl) this.mShadeController).mShadeVisibilityListener = new C30235();
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0037, code lost:
    
        if (r6 != r10[r8 - 1]) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean interceptMediaKey(KeyEvent keyEvent) {
        boolean z;
        int i = this.mState;
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager;
        if (i == 1 || i == 2 || statusBarKeyguardViewManager.isBouncerShowing()) {
            KeyguardSysDumpTrigger keyguardSysDumpTrigger = this.mSysDumpTrigger;
            if (keyguardSysDumpTrigger.isEnabled()) {
                int keyCode = keyEvent.getKeyCode();
                int action = keyEvent.getAction();
                long eventTime = keyEvent.getEventTime();
                int[] iArr = KeyguardSysDumpTrigger.KEY;
                if (action != 0) {
                    if (action == 1) {
                        int i2 = keyguardSysDumpTrigger.keyIndex;
                        if (i2 > 0) {
                        }
                    }
                    z = true;
                } else {
                    if (keyguardSysDumpTrigger.wakefulnessLifecycle.mWakefulness == 2) {
                        int i3 = keyguardSysDumpTrigger.keyIndex;
                        if (keyCode == iArr[i3] && (i3 == 0 || eventTime - keyguardSysDumpTrigger.prevEventTime <= 800)) {
                            keyguardSysDumpTrigger.prevEventTime = eventTime;
                            keyguardSysDumpTrigger.keyIndex = i3 + 1;
                            z = false;
                        }
                    }
                    z = true;
                }
                if (keyguardSysDumpTrigger.isDebug) {
                    ActionBarContextView$$ExternalSyntheticOutline0.m9m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("interceptKey action=", action, " index=", keyguardSysDumpTrigger.keyIndex, " reset="), z, "KeyguardSysDumpTrigger");
                }
                if (action == 0 && keyguardSysDumpTrigger.keyIndex % (iArr.length / 2) == 1) {
                    keyguardSysDumpTrigger.powerManager.userActivity(SystemClock.uptimeMillis(), false);
                }
                if (action == 0 && keyguardSysDumpTrigger.keyIndex == iArr.length) {
                    Log.d("KeyguardSysDumpTrigger", "matched keys");
                    keyguardSysDumpTrigger.start(1, 0L, System.currentTimeMillis());
                    z = true;
                }
                if (z) {
                    keyguardSysDumpTrigger.keyIndex = 0;
                    keyguardSysDumpTrigger.prevEventTime = 0L;
                }
            }
        }
        return this.mState == 1 && statusBarKeyguardViewManager.interceptMediaKey(keyEvent);
    }

    public final boolean isCameraAllowedByAdmin() {
        DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
        NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) this.mLockscreenUserManager;
        if (devicePolicyManager.getCameraDisabled(null, notificationLockscreenUserManagerImpl.mCurrentUserId)) {
            return false;
        }
        return (isKeyguardShowing() && this.mStatusBarKeyguardViewManager.isSecure() && (this.mDevicePolicyManager.getKeyguardDisabledFeatures(null, notificationLockscreenUserManagerImpl.mCurrentUserId) & 2) != 0) ? false : true;
    }

    public final boolean isForegroundComponentName(ComponentName componentName) {
        boolean z = false;
        if (isKeyguardShowing() && !isOccluded()) {
            if (!LsRune.LOCKUI_SUB_DISPLAY_COVER) {
                Log.d("CentralSurfaces", "Checking ForegroundComponent - Lockscreen Shown");
                return false;
            }
            if (this.mDisplayLifecycle.mIsFolderOpened) {
                Log.d("CentralSurfaces", "Checking ForegroundComponent - fold opened");
                return false;
            }
        }
        String str = LsRune.VALUE_CONFIG_CARRIER_TEXT_POLICY;
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) this.mContext.getSystemService(ActivityManager.class)).getRunningTasks(1);
        if (runningTasks != null && !runningTasks.isEmpty() && runningTasks.get(0) != null && componentName.equals(runningTasks.get(0).topActivity)) {
            z = true;
        }
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("Foreground component state :: ", z, "CentralSurfaces");
        return z;
    }

    public final boolean isGoingToSleep() {
        return this.mWakefulnessLifecycle.mWakefulness == 3;
    }

    public final boolean isKeyguardShowing() {
        return ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing;
    }

    public final boolean isOccluded() {
        return ((KeyguardStateControllerImpl) this.mKeyguardStateController).mOccluded;
    }

    public final boolean isPulsing() {
        return this.mDozeServiceHost.mPulsing;
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

    public final void maybeUpdateBarMode() {
        boolean z;
        int barMode = barMode(this.mAppearance, this.mTransientShown);
        int i = 0;
        if (this.mStatusBarMode != barMode) {
            this.mStatusBarMode = barMode;
            checkBarModes();
            this.mAutoHideController.touchAutoHide();
            z = true;
        } else {
            z = false;
        }
        if (z) {
            LightBarController lightBarController = this.mLightBarController;
            lightBarController.mStatusBarMode = barMode;
            ((Handler) Dependency.get(Dependency.MAIN_HANDLER)).post(new LightBarController$$ExternalSyntheticLambda1(lightBarController, "updateBarMode"));
            this.mBubblesOptional.ifPresent(new CentralSurfacesImpl$$ExternalSyntheticLambda13(this, i));
        }
    }

    public final boolean onBackPressed() {
        QSTooltipWindow qSTooltipWindow = this.mToolTipWindow;
        if (qSTooltipWindow != null) {
            qSTooltipWindow.hideToolTip();
        }
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager;
        boolean canHandleBackPressed = statusBarKeyguardViewManager.canHandleBackPressed();
        SysuiStatusBarStateController sysuiStatusBarStateController = this.mStatusBarStateController;
        if (canHandleBackPressed) {
            statusBarKeyguardViewManager.onBackPressed();
            if (this.mState == 2) {
                sysuiStatusBarStateController.setState$1(1);
            }
            return true;
        }
        QuickSettingsController quickSettingsController = this.mQsController;
        if (quickSettingsController.isQsFragmentCreated() && quickSettingsController.mQs.isCustomizing()) {
            this.mQsController.mQs.closeCustomizer();
            return true;
        }
        InterfaceC1922QS interfaceC1922QS = (InterfaceC1922QS) this.mQsController.mSecQuickSettingsController.qsSupplier.get();
        if (interfaceC1922QS != null ? interfaceC1922QS.isShowingDetail() : false) {
            InterfaceC1922QS interfaceC1922QS2 = (InterfaceC1922QS) this.mQsController.mSecQuickSettingsController.qsSupplier.get();
            if (interfaceC1922QS2 != null) {
                interfaceC1922QS2.closeDetail();
            }
            return true;
        }
        QuickSettingsController quickSettingsController2 = this.mQsController;
        boolean z = quickSettingsController2.mSecQuickSettingsController.expandQSAtOnceController.mShouldCloseAtOnce;
        ShadeController shadeController = this.mShadeController;
        if (z) {
            ((ShadeControllerImpl) shadeController).animateCollapseShade(0);
            return true;
        }
        if (quickSettingsController2.mExpanded) {
            ((NotificationPanelViewController) this.mShadeSurface).animateCollapseQs(false);
            return true;
        }
        KeyguardUserSwitcherController keyguardUserSwitcherController = ((NotificationPanelViewController) this.mShadeSurface).mKeyguardUserSwitcherController;
        if (keyguardUserSwitcherController != null ? keyguardUserSwitcherController.closeSwitcherIfOpenAndNotSimple(true) : false) {
            return true;
        }
        if (this.mIsDlsOverlay && this.mState == 1) {
            NotificationPanelViewController notificationPanelViewController = this.mNotificationPanelViewController;
            notificationPanelViewController.getClass();
            Bundle bundle = new Bundle();
            bundle.putString("action", PluginLock.ACTION_BACK_KEY);
            ((PluginLockMediatorImpl) notificationPanelViewController.mPluginLockMediator).onEventReceived(bundle);
            return true;
        }
        int i = this.mState;
        if ((i == 1 || i == 2 || this.mBouncerShowingOverDream) ? false : true) {
            if (((NotificationPanelViewController) this.mShadeSurface).canBeCollapsed()) {
                ((NotificationPanelViewController) this.mShadeSurface).closeQsIfPossible();
                ((ShadeControllerImpl) shadeController).animateCollapseShade(0);
            }
            return true;
        }
        if (i != 2) {
            return false;
        }
        sysuiStatusBarStateController.setState$1(1);
        return true;
    }

    public final void onInputFocusTransfer(float f, boolean z, boolean z2) {
        if (this.mCommandQueue.panelsEnabled()) {
            if (this.mShadeSurface == null) {
                Log.d("CentralSurfaces", "onInputFocusTransfer: mShadeSurface is null");
                return;
            }
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m44m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("onInputFocusTransfer start : ", z, " cancel : ", z2, " velocity : "), f, "CentralSurfaces");
            if (z) {
                NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this.mShadeSurface;
                if (notificationPanelViewController.isFullyCollapsed()) {
                    notificationPanelViewController.mExpectingSynthesizedDown = true;
                    notificationPanelViewController.onTrackingStarted();
                    notificationPanelViewController.updatePanelExpanded();
                    return;
                }
                return;
            }
            NotificationPanelViewController notificationPanelViewController2 = (NotificationPanelViewController) this.mShadeSurface;
            if (notificationPanelViewController2.mExpectingSynthesizedDown) {
                notificationPanelViewController2.mExpectingSynthesizedDown = false;
                if (z2) {
                    notificationPanelViewController2.collapse(1.0f, false);
                } else {
                    notificationPanelViewController2.maybeVibrateOnOpening(false);
                    notificationPanelViewController2.fling(f > 1.0f ? f * 1000.0f : 0.0f);
                    notificationPanelViewController2.mHeadsUpManager.unpinAll();
                }
                notificationPanelViewController2.onTrackingStopped(false);
            }
        }
    }

    public final boolean onMenuPressed() {
        if (!(this.mDeviceInteractive && this.mState != 0 && this.mStatusBarKeyguardViewManager.shouldDismissOnMenuPressed())) {
            return false;
        }
        ((ShadeControllerImpl) this.mShadeController).makeExpandedInvisible();
        return true;
    }

    public void onShadeExpansionFullyChanged(Boolean bool) {
        RemoteInputCoordinator remoteInputCoordinator;
        if (this.mPanelExpanded != bool.booleanValue()) {
            this.mPanelExpanded = bool.booleanValue();
            ShadeSurface shadeSurface = this.mShadeSurface;
            if (shadeSurface != null) {
                ((NotificationPanelViewController) shadeSurface).updateSystemUiStateFlags();
            }
            if (bool.booleanValue() && ((StatusBarStateControllerImpl) this.mStatusBarStateController).mState != 1) {
                try {
                    this.mBarService.clearNotificationEffects();
                } catch (RemoteException unused) {
                }
            }
            if (bool.booleanValue() || (remoteInputCoordinator = this.mRemoteInputManager.mRemoteInputListener) == null) {
                return;
            }
            remoteInputCoordinator.mRemoteInputActiveExtender.endAllLifetimeExtensions();
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void onTrimMemory(int i) {
        NotifRemoteViewCache notifRemoteViewCache;
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("SYSUI_RAM_OPTIMIZATION onTrimMemory=", i, "CentralSurfaces");
        if ((i == 5 || i == 10 || i == 15) && (notifRemoteViewCache = this.mNotifRemoteViewCache) != null) {
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
        if (isKeyguardShowing()) {
            this.mQSPanelController.mCollapseExpandAction.run();
            return;
        }
        NotificationPanelViewController notificationPanelViewController = this.mNotificationPanelViewController;
        notificationPanelViewController.setExpandSettingsPanel(true);
        notificationPanelViewController.expandToQs();
        this.mMainHandler.postDelayed(new CentralSurfacesImpl$$ExternalSyntheticLambda12(0, this, str), 200L);
    }

    public final void postAnimateCollapsePanels() {
        if (QpRune.QUICK_PANEL_SUBSCREEN && !this.mDisplayLifecycle.mIsFolderOpened) {
            ((SubscreenUtil) Dependency.get(SubscreenUtil.class)).closeSubscreenPanel();
            return;
        }
        ShadeController shadeController = this.mShadeController;
        Objects.requireNonNull(shadeController);
        ((ExecutorImpl) this.mMainExecutor).execute(new CentralSurfacesImpl$$ExternalSyntheticLambda1(shadeController, 0));
    }

    public final void postAnimateForceCollapsePanels() {
        ShadeController shadeController = this.mShadeController;
        Objects.requireNonNull(shadeController);
        ((ExecutorImpl) this.mMainExecutor).execute(new CentralSurfacesImpl$$ExternalSyntheticLambda1(shadeController, 1));
    }

    public void registerBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("com.sec.aecmonitor.ONE_CYCLE_FINISH");
        this.mBroadcastDispatcher.registerReceiver(this.mBroadcastReceiver, intentFilter, null, UserHandle.ALL);
    }

    public void registerCallbacks() {
        this.mDeviceStateManager.registerCallback(this.mMainExecutor, new FoldStateListener(this.mContext, new CentralSurfacesImpl$$ExternalSyntheticLambda0(this)));
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
        int i = z ? 4 : 0;
        PhoneStatusBarViewController phoneStatusBarViewController = this.mPhoneStatusBarViewController;
        if (phoneStatusBarViewController != null) {
            ((PhoneStatusBarView) phoneStatusBarViewController.mView).setImportantForAccessibility(i);
        }
        ((NotificationPanelViewController) this.mShadeSurface).mView.setImportantForAccessibility(i);
        NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this.mShadeSurface;
        notificationPanelViewController.mBouncerShowing = z;
        notificationPanelViewController.mNotificationStackScrollLayoutController.updateShowEmptyShadeView();
        notificationPanelViewController.updateVisibility();
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
        checkBarModes();
    }

    public final void setNextUpdateHorizontalPosition(float f) {
        SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper = this.mQsController.mSecQuickSettingsController.tabletHorizontalPanelPositionHelper;
        if (secTabletHorizontalPanelPositionHelper.updateHorizontalPositionRunnable != null) {
            return;
        }
        secTabletHorizontalPanelPositionHelper.updateHorizontalPositionRunnable = new RunnableC2460x13d85fb4(secTabletHorizontalPanelPositionHelper, f);
    }

    public void setNotificationShadeWindowViewController(NotificationShadeWindowViewController notificationShadeWindowViewController) {
        this.mNotificationShadeWindowViewController = notificationShadeWindowViewController;
    }

    public final void setShowSwipeBouncer(boolean z) {
        StatusBarKeyguardViewManager statusBarKeyguardViewManager;
        if (!LsRune.SECURITY_SWIPE_BOUNCER || (statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager) == null) {
            return;
        }
        SysuiStatusBarStateController sysuiStatusBarStateController = this.mStatusBarStateController;
        if (((StatusBarStateControllerImpl) sysuiStatusBarStateController).mState == 2 || ((StatusBarStateControllerImpl) sysuiStatusBarStateController).mState == 1) {
            statusBarKeyguardViewManager.setShowSwipeBouncer(z);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldAnimateDozeWakeup() {
        boolean z;
        int i;
        if (!this.mDozeServiceHost.mAnimateWakeup || this.mBiometricUnlockController.mMode == 1) {
            return false;
        }
        SecLightRevealScrimHelper secLightRevealScrimHelper = this.mSecLightRevealScrimHelper;
        secLightRevealScrimHelper.getClass();
        if (!LsRune.AOD_SUB_DISPLAY_COVER || !secLightRevealScrimHelper.isFolded) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) secLightRevealScrimHelper.keyguardStateController;
            if (!keyguardStateControllerImpl.mShowing || keyguardStateControllerImpl.mOccluded || (i = secLightRevealScrimHelper.wakefulnessLifecycle.mLastWakeReason) == 103 || i == 9 || secLightRevealScrimHelper.screenLifecycle.mScreenState != 2) {
                z = true;
                return z;
            }
        }
        z = false;
        if (z) {
        }
    }

    public final boolean shouldIgnoreTouch() {
        return (((StatusBarStateControllerImpl) this.mStatusBarStateController).mIsDozing && this.mDozeServiceHost.mIgnoreTouchWhilePulsing) || this.mScreenOffAnimationController.shouldIgnoreKeyguardTouches();
    }

    public final void showKeyguard() {
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.mStatusBarStateController;
        statusBarStateControllerImpl.mKeyguardRequested = true;
        statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide = false;
        updateIsKeyguard();
        final AssistManager assistManager = (AssistManager) this.mAssistManagerLazy.get();
        assistManager.getClass();
        AsyncTask.execute(new Runnable() { // from class: com.android.systemui.assist.AssistManager.4
            @Override // java.lang.Runnable
            public final void run() {
                AssistManager.this.mAssistUtils.onLockscreenShown();
            }
        });
    }

    public final void showScreenPinningRequest(int i, String str, boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        ActivityInfo activityInfo;
        SamsungScreenPinningRequest samsungScreenPinningRequest = this.mSamsungScreenPinningRequest;
        samsungScreenPinningRequest.mLogWrapper.m98d("SamsungScreenPinningRequest", "Old taskId: " + String.valueOf(samsungScreenPinningRequest.mTaskId));
        AlertDialog alertDialog = samsungScreenPinningRequest.mDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            if (samsungScreenPinningRequest.mTaskId == i) {
                return;
            } else {
                samsungScreenPinningRequest.clearPrompt();
            }
        }
        List recentTasks = samsungScreenPinningRequest.mActivityManagerWrapper.mAtm.getRecentTasks(ActivityTaskManager.getMaxRecentTasksStatic(), 2, UserHandle.myUserId());
        int size = recentTasks.size();
        int i2 = 0;
        while (true) {
            if (i2 < size) {
                ActivityManager.RecentTaskInfo recentTaskInfo = (ActivityManager.RecentTaskInfo) recentTasks.get(i2);
                ComponentName componentName = recentTaskInfo.origActivity;
                if (componentName == null) {
                    componentName = recentTaskInfo.realActivity;
                }
                PackageManagerWrapper packageManagerWrapper = samsungScreenPinningRequest.mPackageManagerWrapper;
                int i3 = recentTaskInfo.userId;
                packageManagerWrapper.getClass();
                try {
                    activityInfo = PackageManagerWrapper.mIPackageManager.getActivityInfo(componentName, 128L, i3);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    activityInfo = null;
                }
                if (activityInfo == null) {
                    break;
                }
                if (recentTaskInfo.persistentId == i) {
                    com.android.systemui.shared.system.ActivityManagerWrapper activityManagerWrapper = samsungScreenPinningRequest.mActivityManagerWrapper;
                    Context context = samsungScreenPinningRequest.mContext;
                    int i4 = recentTaskInfo.id;
                    activityManagerWrapper.getClass();
                    PackageManager packageManager = context.getPackageManager();
                    String charSequence = activityInfo.loadLabel(packageManager).toString();
                    if (i4 != UserHandle.myUserId()) {
                        charSequence = packageManager.getUserBadgedLabel(charSequence, new UserHandle(i4)).toString();
                    }
                    samsungScreenPinningRequest.mAppName = charSequence;
                    boolean z6 = (recentTaskInfo.baseIntent.getFlags() & QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED) != 0;
                    samsungScreenPinningRequest.mIsExcluded = z6;
                    if (z6) {
                        samsungScreenPinningRequest.mLogWrapper.m98d("SamsungScreenPinningRequest", "flag:" + String.valueOf(recentTaskInfo.baseIntent.getFlags()) + " / intent:" + String.valueOf(QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED));
                    }
                } else {
                    i2++;
                }
            } else {
                break;
            }
        }
        samsungScreenPinningRequest.mTaskId = i;
        samsungScreenPinningRequest.mLogWrapper.m98d("SamsungScreenPinningRequest", "New taskId: " + String.valueOf(samsungScreenPinningRequest.mTaskId));
        samsungScreenPinningRequest.mAppName = str;
        samsungScreenPinningRequest.mIsExcluded = z;
        samsungScreenPinningRequest.mTaskId = i;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("com.samsung.systemui.statusbar.ANIMATING");
        samsungScreenPinningRequest.mBroadcastDispatcher.registerReceiver(intentFilter, samsungScreenPinningRequest.mPinWindowsReceiver);
        if (samsungScreenPinningRequest.mIsExcluded) {
            samsungScreenPinningRequest.createDialog(1, null);
        } else {
            boolean isGesturalMode = BasicRune.NAVBAR_GESTURE ? com.android.systemui.shared.system.QuickStepContract.isGesturalMode(samsungScreenPinningRequest.mNavBarMode) : false;
            LayoutInflater from = LayoutInflater.from(new ContextThemeWrapper(samsungScreenPinningRequest.mContext, 2132018528));
            WindowManagerWrapper windowManagerWrapper = samsungScreenPinningRequest.mWindowManagerWrapper;
            int displayId = samsungScreenPinningRequest.mContext.getDisplayId();
            windowManagerWrapper.getClass();
            try {
                z2 = WindowManagerGlobal.getWindowManagerService().hasNavigationBar(displayId);
            } catch (RemoteException unused) {
                z2 = false;
            }
            LinearLayout linearLayout = (LinearLayout) from.inflate(z2 ? isGesturalMode ? R.layout.screen_pinning_content_view_gesture : R.layout.screen_pinning_content_view_swkey : R.layout.screen_pinning_content_view_hwkey, (ViewGroup) null);
            TextView textView = (TextView) linearLayout.findViewById(R.id.pinning_desc_optional);
            samsungScreenPinningRequest.mTouchExplorationEnabled = samsungScreenPinningRequest.onTouchExplorationEnabled();
            WindowManagerWrapper windowManagerWrapper2 = samsungScreenPinningRequest.mWindowManagerWrapper;
            int displayId2 = samsungScreenPinningRequest.mContext.getDisplayId();
            windowManagerWrapper2.getClass();
            try {
                z3 = WindowManagerGlobal.getWindowManagerService().hasNavigationBar(displayId2);
            } catch (RemoteException unused2) {
                z3 = false;
            }
            if (z3) {
                textView.setText(samsungScreenPinningRequest.mContext.getString(isGesturalMode ? R.string.screen_pinning_msg_in_gesture : samsungScreenPinningRequest.mTouchExplorationEnabled ? R.string.lock_to_app_recent_and_back_softkey_accessibility : R.string.lock_to_app_recent_and_back_softkey));
            } else {
                textView.setText(samsungScreenPinningRequest.mContext.getString(samsungScreenPinningRequest.mTouchExplorationEnabled ? R.string.lock_to_app_recent_and_back_accessibility : R.string.lock_to_app_recent_and_back));
            }
            if (!isGesturalMode) {
                int i5 = Settings.Global.getInt(samsungScreenPinningRequest.mContext.getContentResolver(), "navigationbar_key_order", 0);
                ImageView imageView = (ImageView) linearLayout.findViewById(R.id.left_key);
                ImageView imageView2 = (ImageView) linearLayout.findViewById(R.id.right_key);
                int i6 = R.drawable.pin_windows_ic_recent;
                imageView.setImageResource(i5 == 0 ? R.drawable.pin_windows_ic_recent : R.drawable.pin_windows_ic_back);
                if (i5 == 0) {
                    i6 = R.drawable.pin_windows_ic_back;
                }
                imageView2.setImageResource(i6);
                Resources resources = samsungScreenPinningRequest.mContext.getResources();
                WindowManagerWrapper windowManagerWrapper3 = samsungScreenPinningRequest.mWindowManagerWrapper;
                int displayId3 = samsungScreenPinningRequest.mContext.getDisplayId();
                windowManagerWrapper3.getClass();
                try {
                    z4 = WindowManagerGlobal.getWindowManagerService().hasNavigationBar(displayId3);
                } catch (RemoteException unused3) {
                    z4 = false;
                }
                int color = resources.getColor(z4 ? R.color.screen_pinning_dialog_button : R.color.screen_pinning_dialog_button_hw, null);
                imageView.setImageTintList(ColorStateList.valueOf(color));
                imageView2.setImageTintList(ColorStateList.valueOf(color));
                boolean z7 = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m11m(samsungScreenPinningRequest.mContext) == 1;
                WindowManagerWrapper windowManagerWrapper4 = samsungScreenPinningRequest.mWindowManagerWrapper;
                int displayId4 = samsungScreenPinningRequest.mContext.getDisplayId();
                windowManagerWrapper4.getClass();
                try {
                    z5 = WindowManagerGlobal.getWindowManagerService().hasNavigationBar(displayId4);
                } catch (RemoteException unused4) {
                    z5 = false;
                }
                if (z5 && z7) {
                    if (i5 == 0) {
                        imageView2.setScaleX(-1.0f);
                    } else {
                        imageView.setScaleX(-1.0f);
                    }
                }
                samsungScreenPinningRequest.mTouchExplorationEnabled = samsungScreenPinningRequest.onTouchExplorationEnabled();
                ImageView imageView3 = (ImageView) linearLayout.findViewById(R.id.right_gesture);
                ImageView imageView4 = (ImageView) linearLayout.findViewById(R.id.left_gesture);
                if (!samsungScreenPinningRequest.mTouchExplorationEnabled) {
                    imageView3.setVisibility(0);
                    imageView4.setVisibility(0);
                } else if (i5 == 0) {
                    imageView4.setVisibility(0);
                    imageView3.setVisibility(4);
                } else {
                    imageView4.setVisibility(4);
                    imageView3.setVisibility(0);
                }
            }
            samsungScreenPinningRequest.createDialog(2, linearLayout);
        }
        samsungScreenPinningRequest.mLogWrapper.m98d("SamsungScreenPinningRequest", "New taskId: " + String.valueOf(samsungScreenPinningRequest.mTaskId) + "mIsExcluded: " + samsungScreenPinningRequest.mIsExcluded);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v3, types: [android.content.BroadcastReceiver, com.android.systemui.navigationbar.interactor.PackageRemovedInteractor$addCallback$2] */
    /* JADX WARN: Type inference failed for: r4v60, types: [com.android.systemui.navigationbar.interactor.NavigationModeInteractor$addCallback$2, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    /* JADX WARN: Type inference failed for: r4v63, types: [com.android.systemui.navigationbar.interactor.CoverDisplayWidgetInteractor$addCallback$2, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    /* JADX WARN: Type inference failed for: r4v85, types: [com.android.systemui.navigationbar.interactor.EdgeBackGesturePolicyInteractor$addCallback$2$1, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    /* JADX WARN: Type inference failed for: r4v87, types: [com.android.systemui.navigationbar.interactor.ButtonToHideKeyboardInteractor$addCallback$2, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    /* JADX WARN: Type inference failed for: r4v89, types: [com.android.systemui.navigationbar.interactor.ButtonPositionInteractor$addCallback$2, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    /* JADX WARN: Type inference failed for: r4v91, types: [com.android.systemui.navigationbar.interactor.ButtonOrderInteractor$addCallback$2, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    /* JADX WARN: Type inference failed for: r5v44, types: [android.content.BroadcastReceiver, com.android.systemui.navigationbar.interactor.SettingsSoftResetInteractor$addCallback$2] */
    /* JADX WARN: Type inference failed for: r5v46, types: [com.android.systemui.navigationbar.interactor.OneHandModeInteractor$addCallback$2, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    /* JADX WARN: Type inference failed for: r5v49, types: [com.android.systemui.navigationbar.interactor.RotationLockInteractor$addCallback$2, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v51, types: [com.android.systemui.navigationbar.interactor.UseThemeDefaultInteractor$addCallback$2, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    /* JADX WARN: Type inference failed for: r5v53, types: [android.content.BroadcastReceiver, com.android.systemui.navigationbar.interactor.OpenThemeInteractor$addCallback$2] */
    /* JADX WARN: Type inference failed for: r5v55, types: [com.android.systemui.navigationbar.interactor.OpenThemeInteractor$addCallback$5, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    /* JADX WARN: Type inference failed for: r5v58, types: [android.content.BroadcastReceiver, com.android.systemui.navigationbar.interactor.TaskBarInteractor$addCallback$2] */
    /* JADX WARN: Type inference failed for: r5v60, types: [com.android.systemui.navigationbar.interactor.TaskBarInteractor$addCallback$5, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    /* JADX WARN: Type inference failed for: r5v62, types: [com.android.systemui.navigationbar.interactor.TaskBarInteractor$addCallback$7] */
    /* JADX WARN: Type inference failed for: r6v19, types: [com.android.systemui.navigationbar.interactor.KnoxStateMonitorInteractor$addCallback$2] */
    @Override // com.android.systemui.CoreStartable
    public final void start() {
        RegisterStatusBarResult registerStatusBarResult;
        boolean z;
        final DeviceStateInteractor deviceStateInteractor;
        PackageRemovedInteractor packageRemovedInteractor;
        final CoverDisplayWidgetInteractor coverDisplayWidgetInteractor;
        final TaskBarInteractor taskBarInteractor;
        this.mScreenLifecycle.addObserver(this.mScreenObserver);
        this.mWakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        this.mUiModeManager = (UiModeManager) this.mContext.getSystemService(UiModeManager.class);
        this.mBubblesOptional.ifPresent(new CentralSurfacesImpl$$ExternalSyntheticLambda13(this, 2));
        StatusBarSignalPolicy statusBarSignalPolicy = this.mStatusBarSignalPolicy;
        final int i = 1;
        if (!statusBarSignalPolicy.mInitialized) {
            statusBarSignalPolicy.mInitialized = true;
            statusBarSignalPolicy.mTunerService.addTunable(statusBarSignalPolicy, "icon_blacklist");
            ((NetworkControllerImpl) statusBarSignalPolicy.mNetworkController).addCallback(statusBarSignalPolicy);
            ((SecurityControllerImpl) statusBarSignalPolicy.mSecurityController).addCallback(statusBarSignalPolicy);
            ((DesktopManagerImpl) statusBarSignalPolicy.mDesktopManager).setDesktopStatusBarIconCallback(statusBarSignalPolicy.mDesktopStatusBarIconUpdateCallback);
        }
        this.mKeyguardIndicationController.init();
        this.mColorExtractor.addOnColorsChangedListener(this.mOnColorsChangedListener);
        Display display = this.mContext.getDisplay();
        this.mDisplay = display;
        this.mDisplayId = display.getDisplayId();
        updateDisplaySize();
        this.mStatusBarHideIconsForBouncerManager.displayId = this.mDisplayId;
        initShadeVisibilityListener();
        WindowManagerGlobal.getWindowManagerService();
        this.mDevicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        this.mKeyguardUpdateMonitor.mKeyguardBypassController = this.mKeyguardBypassController;
        this.mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        this.mWallpaperSupported = this.mWallpaperManager.isWallpaperSupported();
        final int i2 = 0;
        if (BasicRune.NAVBAR_ENABLED) {
            NavBarStore navBarStore = (NavBarStore) Dependency.get(NavBarStore.class);
            this.mNavBarStore = navBarStore;
            final NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore;
            ColorSetting colorSetting = (ColorSetting) navBarStoreImpl.interactorFactory.get(ColorSetting.class);
            if (colorSetting != null) {
                colorSetting.addColorCallback(null);
            }
            InteractorFactory interactorFactory = navBarStoreImpl.interactorFactory;
            final ButtonOrderInteractor buttonOrderInteractor = (ButtonOrderInteractor) interactorFactory.get(ButtonOrderInteractor.class);
            if (buttonOrderInteractor != null) {
                final Consumer consumer = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnButtonOrderChanged(false, 1, null));
                    }
                };
                ButtonOrderInteractor$addCallback$2 buttonOrderInteractor$addCallback$2 = buttonOrderInteractor.callback;
                SettingsHelper settingsHelper = buttonOrderInteractor.settingsHelper;
                if (buttonOrderInteractor$addCallback$2 != null) {
                    settingsHelper.unregisterCallback(buttonOrderInteractor$addCallback$2);
                }
                ?? r4 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.ButtonOrderInteractor$addCallback$2
                    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                    public final void onChanged(Uri uri) {
                        Consumer consumer2 = consumer;
                        Intrinsics.checkNotNull(consumer2);
                        consumer2.accept(Boolean.valueOf(buttonOrderInteractor.settingsHelper.isNavBarButtonOrderDefault()));
                    }
                };
                buttonOrderInteractor.callback = r4;
                settingsHelper.registerCallback(r4, Settings.Global.getUriFor("navigationbar_key_order"));
            }
            final ButtonPositionInteractor buttonPositionInteractor = (ButtonPositionInteractor) interactorFactory.get(ButtonPositionInteractor.class);
            if (buttonPositionInteractor != null) {
                final Consumer consumer2 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnButtonPositionChanged(false, 1, null));
                    }
                };
                ButtonPositionInteractor$addCallback$2 buttonPositionInteractor$addCallback$2 = buttonPositionInteractor.callback;
                SettingsHelper settingsHelper2 = buttonPositionInteractor.settingsHelper;
                if (buttonPositionInteractor$addCallback$2 != null) {
                    settingsHelper2.unregisterCallback(buttonPositionInteractor$addCallback$2);
                }
                ?? r42 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.ButtonPositionInteractor$addCallback$2
                    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                    public final void onChanged(Uri uri) {
                        Consumer consumer3 = consumer2;
                        Intrinsics.checkNotNull(consumer3);
                        consumer3.accept(Integer.valueOf(buttonPositionInteractor.settingsHelper.getNavigationBarAlignPosition()));
                    }
                };
                buttonPositionInteractor.callback = r42;
                settingsHelper2.registerCallback(r42, Settings.Global.getUriFor("navigationbar_key_position"));
            }
            final ButtonToHideKeyboardInteractor buttonToHideKeyboardInteractor = (ButtonToHideKeyboardInteractor) interactorFactory.get(ButtonToHideKeyboardInteractor.class);
            if (buttonToHideKeyboardInteractor != null) {
                final Consumer consumer3 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnButtonToHideKeyboardChanged(false, 1, null));
                    }
                };
                ButtonToHideKeyboardInteractor$addCallback$2 buttonToHideKeyboardInteractor$addCallback$2 = buttonToHideKeyboardInteractor.callback;
                SettingsHelper settingsHelper3 = buttonToHideKeyboardInteractor.settingsHelper;
                if (buttonToHideKeyboardInteractor$addCallback$2 != null) {
                    settingsHelper3.unregisterCallback(buttonToHideKeyboardInteractor$addCallback$2);
                }
                ?? r43 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.ButtonToHideKeyboardInteractor$addCallback$2
                    /* JADX WARN: Code restructure failed: missing block: B:9:0x0029, code lost:
                    
                        if ((r0 && r3.mItemLists.get("show_keyboard_button").getIntValue() != 0) != false) goto L14;
                     */
                    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void onChanged(Uri uri) {
                        Consumer consumer4 = consumer3;
                        Intrinsics.checkNotNull(consumer4);
                        SettingsHelper settingsHelper4 = buttonToHideKeyboardInteractor.settingsHelper;
                        boolean z2 = true;
                        if (!settingsHelper4.isNavigationBarHideKeyboardButtonEnabled()) {
                            boolean z3 = BasicRune.NAVBAR_MULTI_MODAL_ICON;
                            if (z3) {
                            }
                            z2 = false;
                        }
                        consumer4.accept(Boolean.valueOf(z2));
                    }
                };
                buttonToHideKeyboardInteractor.callback = r43;
                settingsHelper3.registerCallback(r43, Settings.Global.getUriFor("navigation_bar_button_to_hide_keyboard"));
                settingsHelper3.registerCallback(buttonToHideKeyboardInteractor.callback, Settings.Secure.getUriFor("show_keyboard_button"));
            }
            final EdgeBackGesturePolicyInteractor edgeBackGesturePolicyInteractor = (EdgeBackGesturePolicyInteractor) interactorFactory.get(EdgeBackGesturePolicyInteractor.class);
            if (edgeBackGesturePolicyInteractor != null) {
                final Consumer consumer4 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$4
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnEdgeBackGestureDisabledByPolicyChanged(((Boolean) obj).booleanValue()));
                    }
                };
                EdgeBackGesturePolicyInteractor$addCallback$2$1 edgeBackGesturePolicyInteractor$addCallback$2$1 = edgeBackGesturePolicyInteractor.callback;
                SettingsHelper settingsHelper4 = edgeBackGesturePolicyInteractor.settingsHelper;
                if (edgeBackGesturePolicyInteractor$addCallback$2$1 != null) {
                    settingsHelper4.unregisterCallback(edgeBackGesturePolicyInteractor$addCallback$2$1);
                }
                Settings.Global.putInt(settingsHelper4.mResolver, "navigation_bar_gesture_disabled_by_policy", 0);
                ?? r44 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.EdgeBackGesturePolicyInteractor$addCallback$2$1
                    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                    public final void onChanged(Uri uri) {
                        Consumer consumer5 = consumer4;
                        Intrinsics.checkNotNull(consumer5);
                        SettingsHelper settingsHelper5 = edgeBackGesturePolicyInteractor.settingsHelper;
                        settingsHelper5.getClass();
                        consumer5.accept(Boolean.valueOf(BasicRune.NAVBAR_GESTURE && settingsHelper5.mItemLists.get("navigation_bar_gesture_disabled_by_policy").getIntValue() != 0));
                    }
                };
                edgeBackGesturePolicyInteractor.callback = r44;
                settingsHelper4.registerCallback(r44, Settings.Global.getUriFor("navigation_bar_gesture_disabled_by_policy"));
            }
            GestureNavigationSettingsInteractor gestureNavigationSettingsInteractor = (GestureNavigationSettingsInteractor) interactorFactory.get(GestureNavigationSettingsInteractor.class);
            if (gestureNavigationSettingsInteractor != null) {
                Consumer consumer5 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnNavBarButtonForcedVisibleChanged(false, 1, null));
                    }
                };
                Consumer consumer6 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnBottomSensitivityChanged(false, 1, null));
                    }
                };
                gestureNavigationSettingsInteractor.forcedVisibleCallback = consumer5;
                gestureNavigationSettingsInteractor.bottomSensitivityCallback = consumer6;
                gestureNavigationSettingsInteractor.observer.register();
            }
            boolean z2 = BasicRune.NAVBAR_SUPPORT_TASKBAR;
            if (z2 && (taskBarInteractor = (TaskBarInteractor) interactorFactory.get(TaskBarInteractor.class)) != null) {
                final Runnable runnable = new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$7
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnUpdateTaskbarAvailable(false, 1, null));
                    }
                };
                TaskBarInteractor$addCallback$2 taskBarInteractor$addCallback$2 = taskBarInteractor.broadcastReceiver;
                if (taskBarInteractor$addCallback$2 != null) {
                    taskBarInteractor.broadcastDispatcher.unregisterReceiver(taskBarInteractor$addCallback$2);
                }
                ?? r5 = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.interactor.TaskBarInteractor$addCallback$2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        UserManager userManager;
                        String action = intent != null ? intent.getAction() : null;
                        if (Intrinsics.areEqual(action, "android.intent.action.USER_UNLOCKED")) {
                            TaskBarInteractor.this.userUnlocked = true;
                            Runnable runnable2 = runnable;
                            if (runnable2 != null) {
                                runnable2.run();
                            }
                        } else if (Intrinsics.areEqual(action, "android.intent.action.USER_SWITCHED")) {
                            TaskBarInteractor.this.userUnlocked = (context == null || (userManager = (UserManager) context.getSystemService(UserManager.class)) == null) ? false : userManager.isUserUnlocked(ActivityManager.getCurrentUser());
                        }
                        LogWrapper logWrapper = TaskBarInteractor.this.logWrapper;
                        String action2 = intent != null ? intent.getAction() : null;
                        logWrapper.m98d("TaskBarInteractor", "Receive " + action2 + " userUnlocked=" + TaskBarInteractor.this.userUnlocked + " for userid=" + ActivityManager.getCurrentUser());
                    }
                };
                BroadcastDispatcher.registerReceiverWithHandler$default(taskBarInteractor.broadcastDispatcher, r5, taskBarInteractor.intentFilter, taskBarInteractor.bgHandler, UserHandle.ALL, 48);
                taskBarInteractor.broadcastReceiver = r5;
                TaskBarInteractor$addCallback$5 taskBarInteractor$addCallback$5 = taskBarInteractor.callback;
                SettingsHelper settingsHelper5 = taskBarInteractor.settingsHelper;
                if (taskBarInteractor$addCallback$5 != null) {
                    settingsHelper5.unregisterCallback(taskBarInteractor$addCallback$5);
                }
                ?? r52 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.TaskBarInteractor$addCallback$5
                    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                    public final void onChanged(Uri uri) {
                        Runnable runnable2 = runnable;
                        if (runnable2 != null) {
                            runnable2.run();
                        }
                    }
                };
                taskBarInteractor.callback = r52;
                settingsHelper5.registerCallback(r52, Settings.Global.getUriFor("task_bar"), Settings.System.getUriFor("minimal_battery_use"), Settings.Secure.getUriFor("user_setup_complete"));
                TaskBarInteractor$addCallback$7 taskBarInteractor$addCallback$7 = taskBarInteractor.roleCallback;
                RoleManager roleManager = taskBarInteractor.roleManager;
                if (taskBarInteractor$addCallback$7 != null) {
                    roleManager.removeOnRoleHoldersChangedListenerAsUser(taskBarInteractor$addCallback$7, UserHandle.ALL);
                }
                taskBarInteractor.roleCallback = new OnRoleHoldersChangedListener() { // from class: com.android.systemui.navigationbar.interactor.TaskBarInteractor$addCallback$7
                    public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
                        if (str.hashCode() == 854448779 && str.equals("android.app.role.HOME")) {
                            TaskBarInteractor taskBarInteractor2 = TaskBarInteractor.this;
                            taskBarInteractor2.isDefaultHome = taskBarInteractor2.updateHomeStatus();
                            TaskBarInteractor taskBarInteractor3 = TaskBarInteractor.this;
                            taskBarInteractor3.logWrapper.m98d("TaskBarInteractor", "OnRoleHoldersChangedListener: defaultHome: " + taskBarInteractor3.isDefaultHome);
                            Runnable runnable2 = runnable;
                            if (runnable2 != null) {
                                runnable2.run();
                            }
                        }
                    }
                };
                Executor asExecutor = ExecutorsKt.asExecutor(Dispatchers.Default);
                TaskBarInteractor$addCallback$7 taskBarInteractor$addCallback$72 = taskBarInteractor.roleCallback;
                Intrinsics.checkNotNull(taskBarInteractor$addCallback$72);
                roleManager.addOnRoleHoldersChangedListenerAsUser(asExecutor, taskBarInteractor$addCallback$72, UserHandle.ALL);
            }
            KnoxStateMonitorInteractor knoxStateMonitorInteractor = (KnoxStateMonitorInteractor) interactorFactory.get(KnoxStateMonitorInteractor.class);
            if (knoxStateMonitorInteractor != null) {
                final Consumer consumer7 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnNavBarKnoxPolicyChanged(false, 1, null));
                    }
                };
                final Consumer consumer8 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$9
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnHardKeyIntentPolicyChanged(((Boolean) obj).booleanValue()));
                    }
                };
                KnoxStateMonitorInteractor$addCallback$2 knoxStateMonitorInteractor$addCallback$2 = knoxStateMonitorInteractor.knoxStateMonitorCallback;
                if (knoxStateMonitorInteractor$addCallback$2 != null) {
                    ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).removeCallback(knoxStateMonitorInteractor$addCallback$2);
                }
                knoxStateMonitorInteractor.knoxStateMonitorCallback = new KnoxStateMonitorCallback() { // from class: com.android.systemui.navigationbar.interactor.KnoxStateMonitorInteractor$addCallback$2
                    @Override // com.android.systemui.knox.KnoxStateMonitorCallback
                    public final void onSetHardKeyIntentState(boolean z3) {
                        Consumer consumer9 = consumer8;
                        Intrinsics.checkNotNull(consumer9);
                        consumer9.accept(Boolean.valueOf(z3));
                    }

                    @Override // com.android.systemui.knox.KnoxStateMonitorCallback
                    public final void onUpdateNavigationBarHidden() {
                        Consumer consumer9 = consumer7;
                        Intrinsics.checkNotNull(consumer9);
                        consumer9.accept(Boolean.TRUE);
                    }
                };
                ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).registerCallback(knoxStateMonitorInteractor.knoxStateMonitorCallback);
            }
            OpenThemeInteractor openThemeInteractor = (OpenThemeInteractor) interactorFactory.get(OpenThemeInteractor.class);
            if (openThemeInteractor != null) {
                final Runnable runnable2 = new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$10
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnOpenThemeChanged(false, 1, null));
                    }
                };
                OpenThemeInteractor$addCallback$2 openThemeInteractor$addCallback$2 = openThemeInteractor.broadcastReceiver;
                if (openThemeInteractor$addCallback$2 != null) {
                    openThemeInteractor.broadcastDispatcher.unregisterReceiver(openThemeInteractor$addCallback$2);
                }
                ?? r53 = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.interactor.OpenThemeInteractor$addCallback$2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        Runnable runnable3 = runnable2;
                        if (runnable3 != null) {
                            runnable3.run();
                        }
                    }
                };
                BroadcastDispatcher.registerReceiver$default(openThemeInteractor.broadcastDispatcher, r53, openThemeInteractor.intentFilter, null, null, 0, null, 60);
                openThemeInteractor.broadcastReceiver = r53;
                OpenThemeInteractor$addCallback$5 openThemeInteractor$addCallback$5 = openThemeInteractor.callback;
                SettingsHelper settingsHelper6 = openThemeInteractor.settingsHelper;
                if (openThemeInteractor$addCallback$5 != null) {
                    settingsHelper6.unregisterCallback(openThemeInteractor$addCallback$5);
                }
                ?? r54 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.OpenThemeInteractor$addCallback$5
                    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                    public final void onChanged(Uri uri) {
                        Runnable runnable3 = runnable2;
                        if (runnable3 != null) {
                            runnable3.run();
                        }
                    }
                };
                settingsHelper6.registerCallback(r54, Settings.System.getUriFor("wallpapertheme_state"));
                openThemeInteractor.callback = r54;
            }
            UseThemeDefaultInteractor useThemeDefaultInteractor = (UseThemeDefaultInteractor) interactorFactory.get(UseThemeDefaultInteractor.class);
            if (useThemeDefaultInteractor != null) {
                final Runnable runnable3 = new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$11
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnUseThemeDefaultChanged(false, 1, null));
                    }
                };
                UseThemeDefaultInteractor$addCallback$2 useThemeDefaultInteractor$addCallback$2 = useThemeDefaultInteractor.callback;
                SettingsHelper settingsHelper7 = useThemeDefaultInteractor.settingsHelper;
                if (useThemeDefaultInteractor$addCallback$2 != null) {
                    settingsHelper7.unregisterCallback(useThemeDefaultInteractor$addCallback$2);
                }
                ?? r55 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.UseThemeDefaultInteractor$addCallback$2
                    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                    public final void onChanged(Uri uri) {
                        Runnable runnable4 = runnable3;
                        if (runnable4 != null) {
                            runnable4.run();
                        }
                    }
                };
                settingsHelper7.registerCallback(r55, Settings.Global.getUriFor("navigationbar_use_theme_default"));
                useThemeDefaultInteractor.callback = r55;
            }
            final RotationLockInteractor rotationLockInteractor = (RotationLockInteractor) interactorFactory.get(RotationLockInteractor.class);
            if (rotationLockInteractor != null) {
                final Consumer consumer9 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$12
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
                ?? r56 = new RotationLockController.RotationLockControllerCallback() { // from class: com.android.systemui.navigationbar.interactor.RotationLockInteractor$addCallback$2
                    @Override // com.android.systemui.statusbar.policy.RotationLockController.RotationLockControllerCallback
                    public final void onRotationLockStateChanged(boolean z3) {
                        Consumer consumer10 = consumer9;
                        Intrinsics.checkNotNull(consumer10);
                        consumer10.accept(Boolean.valueOf(rotationLockInteractor.rotationLockController.isRotationLocked()));
                    }
                };
                rotationLockController.addCallback(r56);
                rotationLockInteractor.rotationLockCallback = r56;
            }
            final OneHandModeInteractor oneHandModeInteractor = (OneHandModeInteractor) interactorFactory.get(OneHandModeInteractor.class);
            if (oneHandModeInteractor != null) {
                final Consumer consumer10 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$13
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnOneHandModeChanged(obj instanceof String ? (String) obj : null));
                    }
                };
                OneHandModeInteractor$addCallback$2 oneHandModeInteractor$addCallback$2 = oneHandModeInteractor.callback;
                SettingsHelper settingsHelper8 = oneHandModeInteractor.settingsHelper;
                if (oneHandModeInteractor$addCallback$2 != null) {
                    settingsHelper8.unregisterCallback(oneHandModeInteractor$addCallback$2);
                }
                ?? r57 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.OneHandModeInteractor$addCallback$2
                    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                    public final void onChanged(Uri uri) {
                        Consumer consumer11 = consumer10;
                        if (consumer11 != null) {
                            SettingsHelper settingsHelper9 = oneHandModeInteractor.settingsHelper;
                            settingsHelper9.getClass();
                            consumer11.accept(BasicRune.NAVBAR_GESTURE ? settingsHelper9.mItemLists.get("reduce_screen_running_info").getStringValue() : null);
                        }
                    }
                };
                oneHandModeInteractor.callback = r57;
                settingsHelper8.registerCallback(r57, Settings.System.getUriFor("any_screen_running"), Settings.System.getUriFor("reduce_screen_running_info"));
                consumer10.accept(BasicRune.NAVBAR_GESTURE ? settingsHelper8.mItemLists.get("reduce_screen_running_info").getStringValue() : null);
            }
            SettingsSoftResetInteractor settingsSoftResetInteractor = (SettingsSoftResetInteractor) interactorFactory.get(SettingsSoftResetInteractor.class);
            if (settingsSoftResetInteractor != null) {
                final Runnable runnable4 = new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$14
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
                ?? r58 = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.interactor.SettingsSoftResetInteractor$addCallback$2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        Runnable runnable5 = runnable4;
                        if (runnable5 != null) {
                            runnable5.run();
                        }
                    }
                };
                BroadcastDispatcher.registerReceiver$default(settingsSoftResetInteractor.broadcastDispatcher, r58, settingsSoftResetInteractor.intentFilter, null, null, 0, null, 60);
                settingsSoftResetInteractor.broadcastReceiver = r58;
            }
            if (BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY && !BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && (coverDisplayWidgetInteractor = (CoverDisplayWidgetInteractor) interactorFactory.get(CoverDisplayWidgetInteractor.class)) != null) {
                CoverDisplayWidgetInteractor$addCallback$2 coverDisplayWidgetInteractor$addCallback$2 = coverDisplayWidgetInteractor.callback;
                SettingsHelper settingsHelper9 = coverDisplayWidgetInteractor.settingsHelper;
                if (coverDisplayWidgetInteractor$addCallback$2 != null) {
                    settingsHelper9.unregisterCallback(coverDisplayWidgetInteractor$addCallback$2);
                }
                ?? r45 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.CoverDisplayWidgetInteractor$addCallback$2
                    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                    public final void onChanged(Uri uri) {
                        CoverDisplayWidgetInteractor.this.displayReadyRunnable.run();
                    }
                };
                coverDisplayWidgetInteractor.callback = r45;
                settingsHelper9.registerCallback(r45, Settings.Secure.getUriFor("show_navigation_for_subscreen"));
            }
            if (BasicRune.NAVBAR_REMOTEVIEW && (packageRemovedInteractor = (PackageRemovedInteractor) interactorFactory.get(PackageRemovedInteractor.class)) != null) {
                final Consumer consumer11 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$15
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
                ?? r14 = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.interactor.PackageRemovedInteractor$addCallback$2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        if (Intrinsics.areEqual("android.intent.action.PACKAGE_REMOVED", intent.getAction())) {
                            Uri data = intent.getData();
                            Intrinsics.checkNotNull(data);
                            String encodedSchemeSpecificPart = data.getEncodedSchemeSpecificPart();
                            Consumer consumer12 = consumer11;
                            if (consumer12 != null) {
                                consumer12.accept(encodedSchemeSpecificPart);
                            }
                        }
                    }
                };
                packageRemovedInteractor.broadcastReceiver = r14;
                BroadcastDispatcher.registerReceiver$default(packageRemovedInteractor.broadcastDispatcher, r14, packageRemovedInteractor.intentFilter, null, ((UserTrackerImpl) packageRemovedInteractor.userTracker).getUserHandle(), 0, null, 48);
            }
            if ((BasicRune.BASIC_FOLDABLE_TYPE_FOLD || BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN || BasicRune.NAVBAR_MULTI_MODAL_ICON) && (deviceStateInteractor = (DeviceStateInteractor) interactorFactory.get(DeviceStateInteractor.class)) != null) {
                final Consumer consumer12 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$16
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                        navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnFoldStateChanged(((Boolean) obj).booleanValue()));
                        Handler handler = (Handler) Dependency.get(Dependency.MAIN_HANDLER);
                        final NavBarStoreImpl navBarStoreImpl3 = NavBarStoreImpl.this;
                        handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$16.1
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
                final Consumer consumer13 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$17
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        if (NavBarStoreImpl.this.navStateManager.get(1) != null) {
                            boolean isVisible = ((WindowManager) ((Context) NavBarStoreImpl.this.getModule(Context.class, 1)).getSystemService(WindowManager.class)).getCurrentWindowMetrics().getWindowInsets().isVisible(WindowInsets.Type.ime());
                            NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                            navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnNavBarLargeCoverScreenVisibilityChanged(isVisible, ((Boolean) obj).booleanValue()), 1);
                        }
                    }
                };
                Consumer consumer14 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$18
                    /* JADX WARN: Code restructure failed: missing block: B:7:0x0023, code lost:
                    
                        if (r2 == true) goto L12;
                     */
                    @Override // java.util.function.Consumer
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void accept(Object obj) {
                        boolean z3;
                        NavBarStateManager navBarStateManager = (NavBarStateManager) NavBarStoreImpl.this.navStateManager.get(0);
                        if (navBarStateManager != null) {
                            int intValue = ((Integer) obj).intValue();
                            NavBarStateManager.States states = navBarStateManager.states;
                            z3 = true;
                            boolean z4 = intValue != states.lastTaskUserId;
                            states.lastTaskUserId = intValue;
                        }
                        z3 = false;
                        if (z3) {
                            NavBarStoreImpl.this.logWrapper.printLog(0, "Force update provided inset because Task's userId was changed.");
                            NavigationBar navigationBar = (NavigationBar) NavBarStoreImpl.this.getModule(NavigationBar.class, 0);
                            if (navigationBar != null) {
                                navigationBar.updateNavBarLayoutParams();
                            }
                        }
                    }
                };
                Consumer consumer15 = new Consumer() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$19
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavBarStateManager navBarStateManager = (NavBarStateManager) NavBarStoreImpl.this.navStateManager.get(1);
                        if (navBarStateManager != null) {
                            NavBarStoreImpl navBarStoreImpl2 = NavBarStoreImpl.this;
                            if (navBarStateManager.isGestureMode()) {
                                navBarStoreImpl2.handleEvent(navBarStoreImpl2, new EventTypeFactory.EventType.OnCoverRotationChanged(((Number) obj).intValue()), 1);
                            }
                        }
                    }
                };
                DeviceStateManager.FoldStateListener foldStateListener = deviceStateInteractor.foldStateListener;
                DeviceStateManager deviceStateManager = deviceStateInteractor.deviceStateManager;
                if (foldStateListener != null) {
                    deviceStateManager.unregisterCallback(foldStateListener);
                }
                Consumer consumer16 = new Consumer() { // from class: com.android.systemui.navigationbar.interactor.DeviceStateInteractor$addCallback$2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        DeviceStateInteractor.this.foldCache = ((Boolean) obj).booleanValue();
                        DeviceStateInteractor deviceStateInteractor2 = DeviceStateInteractor.this;
                        Consumer consumer17 = consumer12;
                        if (consumer17 != null) {
                            consumer17.accept(Boolean.valueOf(deviceStateInteractor2.foldCache));
                        } else {
                            deviceStateInteractor2.getClass();
                        }
                        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
                            DeviceStateInteractor deviceStateInteractor3 = DeviceStateInteractor.this;
                            Consumer consumer18 = consumer13;
                            deviceStateInteractor3.getClass();
                            NavigationBarController navigationBarController = (NavigationBarController) Dependency.get(NavigationBarController.class);
                            if (navigationBarController != null) {
                                boolean z3 = deviceStateInteractor3.foldCache;
                                DeviceStateInteractor$displayListener$1 deviceStateInteractor$displayListener$1 = deviceStateInteractor3.displayListener;
                                DeviceStateInteractor$componentCallbacks$1 deviceStateInteractor$componentCallbacks$1 = deviceStateInteractor3.componentCallbacks;
                                Context context = deviceStateInteractor3.windowContext;
                                DisplayManager displayManager = deviceStateInteractor3.displayManager;
                                if (z3) {
                                    navigationBarController.onDisplayReady(1);
                                    displayManager.registerDisplayListener(deviceStateInteractor$displayListener$1, deviceStateInteractor3.handler);
                                    deviceStateInteractor3.coverTask = deviceStateInteractor3.new CoverTask(consumer18);
                                    ActivityTaskManager.getService().registerTaskStackListener(deviceStateInteractor3.coverTask);
                                    if (context != null) {
                                        context.registerComponentCallbacks(deviceStateInteractor$componentCallbacks$1);
                                        return;
                                    }
                                    return;
                                }
                                navigationBarController.removeNavigationBar(1);
                                displayManager.unregisterDisplayListener(deviceStateInteractor$displayListener$1);
                                deviceStateInteractor3.lastRotation = -1;
                                deviceStateInteractor3.lastCoverRotation = 0;
                                deviceStateInteractor3.coverTaskCache = false;
                                ActivityTaskManager.getService().unregisterTaskStackListener(deviceStateInteractor3.coverTask);
                                if (context != null) {
                                    context.unregisterComponentCallbacks(deviceStateInteractor$componentCallbacks$1);
                                }
                            }
                        }
                    }
                };
                Context context = deviceStateInteractor.context;
                deviceStateInteractor.foldStateListener = new DeviceStateManager.FoldStateListener(context, consumer16);
                if (BasicRune.NAVBAR_MULTI_MODAL_ICON) {
                    DeviceStateInteractor.MultimodalTask multimodalTask = deviceStateInteractor.multimodalTask;
                    if (multimodalTask != null) {
                        ActivityTaskManager.getService().unregisterTaskStackListener(multimodalTask);
                    }
                    deviceStateInteractor.multimodalTask = new DeviceStateInteractor.MultimodalTask(deviceStateInteractor, consumer14);
                    ActivityTaskManager.getService().registerTaskStackListener(deviceStateInteractor.multimodalTask);
                }
                deviceStateManager.registerCallback(context.getMainExecutor(), deviceStateInteractor.foldStateListener);
                deviceStateInteractor.largeCoverRotationCallback = consumer15;
            }
            NavigationModeInteractor navigationModeInteractor = (NavigationModeInteractor) interactorFactory.get(NavigationModeInteractor.class);
            if (navigationModeInteractor != null) {
                final NavBarStoreImpl$initInteractor$20 navBarStoreImpl$initInteractor$20 = new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarStoreImpl$initInteractor$20
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((NavigationModeController) Dependency.get(NavigationModeController.class)).updateCurrentInteractionMode(true);
                    }
                };
                NavigationModeInteractor$addCallback$2 navigationModeInteractor$addCallback$2 = navigationModeInteractor.callback;
                SettingsHelper settingsHelper10 = navigationModeInteractor.settingsHelper;
                if (navigationModeInteractor$addCallback$2 != null) {
                    settingsHelper10.unregisterCallback(navigationModeInteractor$addCallback$2);
                }
                ?? r46 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.interactor.NavigationModeInteractor$addCallback$2
                    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
                    public final void onChanged(Uri uri) {
                        Runnable runnable5 = navBarStoreImpl$initInteractor$20;
                        if (runnable5 != null) {
                            runnable5.run();
                        }
                    }
                };
                navigationModeInteractor.callback = r46;
                settingsHelper10.registerCallback(r46, Settings.Global.getUriFor("navigationbar_splugin_flags"));
            }
            navBarStoreImpl.packs = ((BandAidPackFactory) navBarStoreImpl.bandAidPackFactory).getPacks(navBarStoreImpl);
            if (z2) {
                TaskbarDelegate taskbarDelegate = (TaskbarDelegate) Dependency.get(TaskbarDelegate.class);
                navBarStoreImpl.taskbarDelegate = taskbarDelegate;
                if (taskbarDelegate != null) {
                    taskbarDelegate.mNavBarRemoteViewManager = navBarStoreImpl.navBarRemoteViewManager;
                }
                Intrinsics.checkNotNull(taskbarDelegate);
                navBarStoreImpl.putModule(TaskbarDelegate.class, taskbarDelegate, 0);
            }
        } else {
            new ScreenPinningNotify(this.mContext);
        }
        try {
            registerStatusBarResult = this.mBarService.registerStatusBar(this.mCommandQueue);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            registerStatusBarResult = null;
        }
        createAndAddWindows(registerStatusBarResult);
        if (this.mWallpaperSupported) {
            this.mBroadcastDispatcher.registerReceiver(this.mWallpaperChangedReceiver, new IntentFilter("android.intent.action.WALLPAPER_CHANGED"), null, UserHandle.ALL);
            this.mWallpaperChangedReceiver.onReceive(this.mContext, null);
        }
        C301724 c301724 = this.mActivityLaunchAnimatorCallback;
        ActivityLaunchAnimator activityLaunchAnimator = this.mActivityLaunchAnimator;
        activityLaunchAnimator.callback = c301724;
        activityLaunchAnimator.listeners.add(this.mActivityLaunchAnimatorListener);
        this.mNotificationAnimationProvider = new NotificationLaunchAnimatorControllerProvider(this.mNotificationShadeWindowViewController, this.mNotifListContainer, this.mHeadsUpManager, this.mJankMonitor);
        this.mRemoteInputManager.addControllerCallback(this.mNotificationShadeWindowController);
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mStackScrollerController;
        NotificationActivityStarter notificationActivityStarter = this.mNotificationActivityStarter;
        notificationStackScrollLayoutController.mNotificationActivityStarter = notificationActivityStarter;
        this.mGutsManager.mNotificationActivityStarter = notificationActivityStarter;
        NotificationPresenter notificationPresenter = this.mPresenter;
        ((ShadeControllerImpl) this.mShadeController).mPresenter = notificationPresenter;
        this.mNotificationsController.initialize(this, notificationPresenter, this.mNotifListContainer, notificationStackScrollLayoutController.mNotifStackController, notificationActivityStarter, this.mCentralSurfacesComponent.getBindRowCallback());
        if (NotiRune.NOTI_SUBSCREEN_ALL) {
            SubscreenNotificationController subscreenNotificationController = (SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class);
            this.mSubscreenNotificationController = subscreenNotificationController;
            NotificationActivityStarter notificationActivityStarter2 = this.mNotificationActivityStarter;
            SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationController.mDeviceModel;
            if (subscreenDeviceModelParent != null) {
                subscreenDeviceModelParent.mNotificationActivityStarter = notificationActivityStarter2;
            }
        }
        if ((registerStatusBarResult.mTransientBarTypes & WindowInsets.Type.statusBars()) != 0 && !this.mTransientShown) {
            this.mTransientShown = true;
            this.mNoAnimationOnNextBarModeChange = true;
            maybeUpdateBarMode();
        }
        this.mCommandQueueCallbacks.onSystemBarAttributesChanged(this.mDisplayId, registerStatusBarResult.mAppearance, registerStatusBarResult.mAppearanceRegions, registerStatusBarResult.mNavbarColorManagedByIme, registerStatusBarResult.mBehavior, registerStatusBarResult.mRequestedVisibleTypes, registerStatusBarResult.mPackageName, registerStatusBarResult.mLetterboxDetails);
        this.mCommandQueueCallbacks.getClass();
        int size = registerStatusBarResult.mIcons.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.mCommandQueue.setIcon((String) registerStatusBarResult.mIcons.keyAt(i3), (StatusBarIcon) registerStatusBarResult.mIcons.valueAt(i3));
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.systemui.statusbar.banner_action_cancel");
        intentFilter.addAction("com.android.systemui.statusbar.banner_action_setup");
        this.mContext.registerReceiver(this.mBannerActionBroadcastReceiver, intentFilter, "com.android.systemui.permission.SELF", null, 2);
        if (this.mWallpaperSupported) {
            try {
                IWallpaperManager.Stub.asInterface(ServiceManager.getService("wallpaper")).setInAmbientMode(false, 0L);
            } catch (RemoteException unused) {
            }
        }
        final PhoneStatusBarPolicy phoneStatusBarPolicy = this.mIconPolicy;
        phoneStatusBarPolicy.getClass();
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.HEADSET_PLUG");
        intentFilter2.addAction("android.intent.action.SIM_STATE_CHANGED");
        intentFilter2.addAction("android.telecom.action.CURRENT_TTY_MODE_CHANGED");
        intentFilter2.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
        intentFilter2.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
        intentFilter2.addAction("android.intent.action.MANAGED_PROFILE_REMOVED");
        intentFilter2.addAction("android.intent.action.LOCALE_CHANGED");
        intentFilter2.addAction("android.app.action.NOTIFICATION_POLICY_CHANGED");
        intentFilter2.addAction("com.android.systemui.action.dnd_off");
        intentFilter2.addAction("android.intent.action.TIME_SET");
        intentFilter2.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter2.addAction("android.intent.action.DATE_CHANGED");
        phoneStatusBarPolicy.mBroadcastDispatcher.registerReceiverWithHandler(phoneStatusBarPolicy.mIntentReceiver, intentFilter2, phoneStatusBarPolicy.mHandler, UserHandle.ALL);
        Observer observer = new Observer() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                final PhoneStatusBarPolicy phoneStatusBarPolicy2 = PhoneStatusBarPolicy.this;
                phoneStatusBarPolicy2.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        PhoneStatusBarPolicy.this.updateVolumeZen();
                    }
                });
            }
        };
        ((RingerModeTrackerImpl) phoneStatusBarPolicy.mRingerModeTracker).ringerMode.observeForever(observer);
        ((RingerModeTrackerImpl) phoneStatusBarPolicy.mRingerModeTracker).ringerModeInternal.observeForever(observer);
        ((UserTrackerImpl) phoneStatusBarPolicy.mUserTracker).addCallback(phoneStatusBarPolicy.mUserSwitchListener, phoneStatusBarPolicy.mMainExecutor);
        TelecomManager telecomManager = phoneStatusBarPolicy.mTelecomManager;
        if (telecomManager == null) {
            phoneStatusBarPolicy.updateTTY(0);
        } else {
            phoneStatusBarPolicy.updateTTY(telecomManager.getCurrentTtyMode());
        }
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIcon(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_quick_settings_bluetooth_on), phoneStatusBarPolicy.mSlotBluetooth, R.drawable.stat_sys_data_bluetooth);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIcon(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_bluetooth_connected), phoneStatusBarPolicy.mSlotBluetoothConnected, R.drawable.stat_sys_data_bluetooth_connected);
        phoneStatusBarPolicy.updateBluetooth();
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIcon(phoneStatusBarPolicy.mResources.getString(R.string.status_bar_alarm), phoneStatusBarPolicy.mSlotAlarmClock, R.drawable.stat_sys_alarm);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotAlarmClock, false);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIcon(null, phoneStatusBarPolicy.mSlotZen, R.drawable.stat_sys_dnd);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotZen, false);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIcon(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_ringer_vibrate), phoneStatusBarPolicy.mSlotVibrate, R.drawable.stat_sys_ringer_vibrate);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotVibrate, false);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIcon(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_ringer_silent), phoneStatusBarPolicy.mSlotMute, R.drawable.stat_sys_ringer_silent);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotMute, false);
        phoneStatusBarPolicy.updateVolumeZen();
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIcon(null, phoneStatusBarPolicy.mSlotCast, R.drawable.stat_sys_cast);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotCast, false);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIcon(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_status_bar_hotspot), phoneStatusBarPolicy.mSlotHotspot, R.drawable.stat_sys_hotspot);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotHotspot, ((HotspotControllerImpl) phoneStatusBarPolicy.mHotspot).isHotspotEnabled());
        phoneStatusBarPolicy.updateManagedProfile();
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIcon(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_data_saver_on), phoneStatusBarPolicy.mSlotDataSaver, R.drawable.stat_sys_data_saver);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotDataSaver, false);
        Resources resources = phoneStatusBarPolicy.mResources;
        PrivacyType privacyType = PrivacyType.TYPE_MICROPHONE;
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIcon(phoneStatusBarPolicy.mResources.getString(R.string.ongoing_privacy_chip_content_multiple_apps, resources.getString(privacyType.getNameId())), phoneStatusBarPolicy.mSlotMicrophone, privacyType.getIconId());
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotMicrophone, false);
        Resources resources2 = phoneStatusBarPolicy.mResources;
        PrivacyType privacyType2 = PrivacyType.TYPE_CAMERA;
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIcon(phoneStatusBarPolicy.mResources.getString(R.string.ongoing_privacy_chip_content_multiple_apps, resources2.getString(privacyType2.getNameId())), phoneStatusBarPolicy.mSlotCamera, privacyType2.getIconId());
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotCamera, false);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIcon(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_location_active), phoneStatusBarPolicy.mSlotLocation, R.drawable.stat_sys_location);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotLocation, false);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIcon(phoneStatusBarPolicy.mResources.getString(R.string.accessibility_sensors_off_active), phoneStatusBarPolicy.mSlotSensorsOff, R.drawable.stat_sys_sensors_off);
        StatusBarIconController statusBarIconController = phoneStatusBarPolicy.mIconController;
        String str = phoneStatusBarPolicy.mSlotSensorsOff;
        SensorPrivacyControllerImpl sensorPrivacyControllerImpl = (SensorPrivacyControllerImpl) phoneStatusBarPolicy.mSensorPrivacyController;
        synchronized (sensorPrivacyControllerImpl.mLock) {
            z = sensorPrivacyControllerImpl.mSensorPrivacyEnabled;
        }
        ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, z);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIcon(null, phoneStatusBarPolicy.mSlotScreenRecord, R.drawable.stat_sys_screen_record);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotScreenRecord, false);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIcon(null, phoneStatusBarPolicy.mSlotBTTethering, R.drawable.stat_sys_tether_bluetooth);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotBTTethering, false);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIcon(phoneStatusBarPolicy.mResources.getString(R.string.status_bar_power_saving_description), phoneStatusBarPolicy.mSlotPowerSave, R.drawable.stat_sys_power_saver);
        ((StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController).setIconVisibility(phoneStatusBarPolicy.mSlotPowerSave, false);
        phoneStatusBarPolicy.mRotationLockController.addCallback(phoneStatusBarPolicy);
        ((SBluetoothControllerImpl) phoneStatusBarPolicy.mBluetooth).addCallback(phoneStatusBarPolicy);
        ((DeviceProvisionedControllerImpl) phoneStatusBarPolicy.mProvisionedController).addCallback(phoneStatusBarPolicy);
        phoneStatusBarPolicy.mCurrentUserSetup = ((DeviceProvisionedControllerImpl) phoneStatusBarPolicy.mProvisionedController).isCurrentUserSetup();
        phoneStatusBarPolicy.mCurrentUserSetup = ((DeviceProvisionedControllerImpl) phoneStatusBarPolicy.mProvisionedController).isCurrentUserSetup();
        ((ZenModeControllerImpl) phoneStatusBarPolicy.mZenController).addCallback(phoneStatusBarPolicy);
        ((CastControllerImpl) phoneStatusBarPolicy.mCast).addCallback(phoneStatusBarPolicy.mCastCallback);
        ((HotspotControllerImpl) phoneStatusBarPolicy.mHotspot).addCallback(phoneStatusBarPolicy.mHotspotCallback);
        ((NextAlarmControllerImpl) phoneStatusBarPolicy.mNextAlarmController).addCallback(phoneStatusBarPolicy.mNextAlarmCallback);
        ((DataSaverControllerImpl) phoneStatusBarPolicy.mDataSaver).addCallback(phoneStatusBarPolicy);
        ((KeyguardStateControllerImpl) phoneStatusBarPolicy.mKeyguardStateController).addCallback(phoneStatusBarPolicy);
        phoneStatusBarPolicy.mPrivacyItemController.addCallback(phoneStatusBarPolicy);
        ((SensorPrivacyControllerImpl) phoneStatusBarPolicy.mSensorPrivacyController).addCallback(phoneStatusBarPolicy.mSensorPrivacyListener);
        ((LocationControllerImpl) phoneStatusBarPolicy.mLocationController).addCallback(phoneStatusBarPolicy);
        phoneStatusBarPolicy.mRecordingController.mListeners.add(phoneStatusBarPolicy);
        phoneStatusBarPolicy.mCommandQueue.addCallback((CommandQueue.Callbacks) phoneStatusBarPolicy);
        ((ConfigurationControllerImpl) phoneStatusBarPolicy.mConfigurationController).addCallback(phoneStatusBarPolicy.mConfigurationListener);
        ((BatteryControllerImpl) phoneStatusBarPolicy.mBatteryController).addCallback(phoneStatusBarPolicy.mBatteryStateChangeCallback);
        phoneStatusBarPolicy.mSettingsHelper.registerCallback(phoneStatusBarPolicy.mOnChangedCallback, Settings.System.getUriFor("emergency_mode"));
        phoneStatusBarPolicy.mActivityManager.semRegisterProcessListener(phoneStatusBarPolicy.mProcessListener);
        if (DeviceType.isEngOrUTBinary() && SystemProperties.getBoolean("debug.status_bar.show_icons", false)) {
            final StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) phoneStatusBarPolicy.mIconController;
            statusBarIconControllerImpl.mStatusBarIconList.mViewOnlySlots.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.StatusBarIconControllerImpl$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    StatusBarIconControllerImpl statusBarIconControllerImpl2 = StatusBarIconControllerImpl.this;
                    statusBarIconControllerImpl2.getClass();
                    statusBarIconControllerImpl2.setIconVisibility(((StatusBarIconList.Slot) obj).mName, true);
                }
            });
        }
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.3
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardGoingAwayChanged() {
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                FeatureFlags featureFlags = centralSurfacesImpl.mFeatureFlags;
                Flags flags = Flags.INSTANCE;
                featureFlags.getClass();
                if (((KeyguardStateControllerImpl) centralSurfacesImpl.mKeyguardStateController).mKeyguardGoingAway || centralSurfacesImpl.mLightRevealScrim.revealAmount == 1.0f) {
                    return;
                }
                Log.e("CentralSurfaces", "Keyguard is done going away, but someone left the light reveal scrim at reveal amount: " + centralSurfacesImpl.mLightRevealScrim.revealAmount);
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onUnlockedChanged() {
                UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
                CentralSurfacesImpl.this.logStateToEventlog();
            }
        });
        Trace.beginSection("CentralSurfaces#startKeyguard");
        SysuiStatusBarStateController sysuiStatusBarStateController = this.mStatusBarStateController;
        C301522 c301522 = this.mStateListener;
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) sysuiStatusBarStateController;
        synchronized (statusBarStateControllerImpl.mListeners) {
            statusBarStateControllerImpl.addListenerInternalLocked(c301522, 0);
        }
        BiometricUnlockController biometricUnlockController = (BiometricUnlockController) this.mBiometricUnlockControllerLazy.get();
        this.mBiometricUnlockController = biometricUnlockController;
        ((HashSet) biometricUnlockController.mBiometricUnlockEventsListeners).add(new BiometricUnlockController.BiometricUnlockEventsListener() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.7
            @Override // com.android.systemui.statusbar.phone.BiometricUnlockController.BiometricUnlockEventsListener
            public final void onModeChanged(int i4) {
                if (i4 == 1 || i4 == 2 || i4 == 6) {
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

            public final void setWakeAndUnlocking(boolean z3) {
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                if (centralSurfacesImpl.getNavigationBarView() != null) {
                    NavigationBarView navigationBarView = centralSurfacesImpl.getNavigationBarView();
                    WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) ((ViewGroup) navigationBarView.getParent()).getLayoutParams();
                    if (layoutParams != null) {
                        boolean z4 = layoutParams.windowAnimations != 0;
                        if (!z4 && z3) {
                            layoutParams.windowAnimations = 2132017167;
                        } else if (z4 && !z3) {
                            layoutParams.windowAnimations = 0;
                        }
                        ((WindowManager) navigationBarView.getContext().getSystemService(WindowManager.class)).updateViewLayout((View) navigationBarView.getParent(), layoutParams);
                    }
                    navigationBarView.mWakeAndUnlocking = z3;
                    navigationBarView.updateLayoutTransitionsEnabled();
                }
            }
        });
        KeyguardViewMediator keyguardViewMediator = this.mKeyguardViewMediator;
        ShadeSurface shadeSurface = this.mShadeSurface;
        ShadeExpansionStateManager shadeExpansionStateManager = this.mShadeExpansionStateManager;
        BiometricUnlockController biometricUnlockController2 = this.mBiometricUnlockController;
        ViewGroup viewGroup = (ViewGroup) this.mNotificationShadeWindowView.findViewById(R.id.sec_lock_icon_view);
        NotificationStackScrollLayout notificationStackScrollLayout = this.mStackScroller;
        KeyguardBypassController keyguardBypassController = this.mKeyguardBypassController;
        keyguardViewMediator.mCentralSurfaces = this;
        Lazy lazy = keyguardViewMediator.mKeyguardViewControllerLazy;
        ((KeyguardViewController) lazy.get()).registerCentralSurfaces(this, shadeSurface, shadeExpansionStateManager, biometricUnlockController2, viewGroup, notificationStackScrollLayout, keyguardBypassController);
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).addCallback(this.mKeyguardStateControllerCallback);
        KeyguardIndicationController keyguardIndicationController = this.mKeyguardIndicationController;
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager;
        keyguardIndicationController.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        BiometricUnlockController biometricUnlockController3 = this.mBiometricUnlockController;
        biometricUnlockController3.mKeyguardViewController = statusBarKeyguardViewManager;
        if (!SafeUIState.isSysUiSafeModeEnabled()) {
            biometricUnlockController3.mUpdateMonitor.registerPreCallback(biometricUnlockController3);
        }
        this.mRemoteInputManager.addControllerCallback(this.mStatusBarKeyguardViewManager);
        LightBarController lightBarController = this.mLightBarController;
        BiometricUnlockController biometricUnlockController4 = this.mBiometricUnlockController;
        lightBarController.mBiometricUnlockController = biometricUnlockController4;
        this.mMediaManager.mBiometricUnlockController = biometricUnlockController4;
        this.mKeyguardDismissUtil.mDismissHandler = new CentralSurfacesImpl$$ExternalSyntheticLambda0(this);
        int i4 = 3;
        if (LsRune.SECURITY_DEFAULT_LANDSCAPE) {
            DeviceState.ROTATION_0 = 1;
            DeviceState.ROTATION_90 = 2;
            DeviceState.ROTATION_180 = 3;
            DeviceState.ROTATION_270 = 0;
        }
        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
            DeviceState.setInDisplayFingerprintSensorPosition(this.mContext.getResources().getDisplayMetrics());
        }
        Trace.endSection();
        this.mKeyguardUpdateMonitor.registerCallback(this.mUpdateCallback);
        DozeServiceHost dozeServiceHost = this.mDozeServiceHost;
        StatusBarKeyguardViewManager statusBarKeyguardViewManager2 = this.mStatusBarKeyguardViewManager;
        NotificationShadeWindowViewController notificationShadeWindowViewController = this.mNotificationShadeWindowViewController;
        ShadeSurface shadeSurface2 = this.mShadeSurface;
        View view = this.mAmbientIndicationContainer;
        dozeServiceHost.mCentralSurfaces = this;
        dozeServiceHost.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager2;
        dozeServiceHost.mNotificationPanel = shadeSurface2;
        dozeServiceHost.mNotificationShadeWindowViewController = notificationShadeWindowViewController;
        dozeServiceHost.mAmbientIndicationContainer = view;
        updateLightRevealScrimVisibility();
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        this.mBatteryController.observe(this.mLifecycle, this.mBatteryStateChangeCallback);
        this.mLifecycle.setCurrentState(Lifecycle.State.RESUMED);
        this.mAccessibilityFloatingMenuController.init();
        final int i5 = registerStatusBarResult.mDisabledFlags1;
        final int i6 = registerStatusBarResult.mDisabledFlags2;
        InitController initController = this.mInitController;
        Runnable runnable5 = new Runnable() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                int i7 = i5;
                int i8 = i6;
                Context context2 = centralSurfacesImpl.mContext;
                int[] iArr = new int[2];
                try {
                    iArr = centralSurfacesImpl.mBarService.getDisableFlags((IBinder) null, -1);
                    Log.d("CentralSurfaces", "QUICK_RELOAD_DISABLE_FLAGS_ON_INIT sec(" + iArr[0] + "," + iArr[1] + ") ged(" + i7 + "," + i8 + ")");
                } catch (RemoteException unused2) {
                    Log.e("CentralSurfaces", "addPostInitTask failed by mBarService.getDisableFlags");
                    iArr[0] = i7;
                    iArr[1] = i8;
                }
                centralSurfacesImpl.mCommandQueue.disable(centralSurfacesImpl.mDisplayId, iArr[0], iArr[1], false);
                try {
                    Binder binder = new Binder();
                    centralSurfacesImpl.mBarService.disable(QuickStepContract.SYSUI_STATE_DEVICE_DOZING, binder, context2.getPackageName());
                    centralSurfacesImpl.mBarService.disable(0, binder, context2.getPackageName());
                } catch (RemoteException e2) {
                    e2.rethrowFromSystemServer();
                }
            }
        };
        if (initController.mTasksExecuted) {
            throw new IllegalStateException("post init tasks have already been executed!");
        }
        initController.mTasks.add(runnable5);
        registerCallbacks();
        this.mFalsingManager.addFalsingBeliefListener(this.mFalsingBeliefListener);
        this.mPluginManager.addPluginListener((PluginListener) new C30224(), OverlayPlugin.class, true);
        this.mStartingSurfaceOptional.ifPresent(new CentralSurfacesImpl$$ExternalSyntheticLambda13(this, i4));
        final MdmOverlayContainer mdmOverlayContainer = this.mMdmOverlayContainer;
        mdmOverlayContainer.mStatusBar = this;
        mdmOverlayContainer.mView = (FrameLayout) this.mNotificationShadeWindowView.findViewById(R.id.keyguard_mdm_overlay_container);
        ((StatusBarStateControllerImpl) ((SysuiStatusBarStateController) mdmOverlayContainer.mStatusBarStateControllerLazy.get())).addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.mdm.MdmOverlayContainer.1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i7) {
                MdmOverlayContainer mdmOverlayContainer2 = MdmOverlayContainer.this;
                if (mdmOverlayContainer2.mPreviousState == 2 && i7 == 1) {
                    mdmOverlayContainer2.updateMdmPolicy();
                }
                mdmOverlayContainer2.mPreviousState = i7;
            }
        });
        this.mContext.sendBroadcast(new Intent("com.samsung.systemui.statusbar.STARTED"));
        if (LsRune.AOD_LIGHT_REVEAL) {
            final SecLightRevealScrimHelper secLightRevealScrimHelper = this.mSecLightRevealScrimHelper;
            final Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda19
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    int i7 = i2;
                    Object obj = this;
                    switch (i7) {
                        case 0:
                            return ((CentralSurfacesImpl) obj).mCurrentDisplaySize;
                        case 1:
                            return Integer.valueOf(((CentralSurfacesImpl) obj).mDisplay.getRotation());
                        default:
                            return ((CentralSurfacesComponent) obj).createCollapsedStatusBarFragment();
                    }
                }
            };
            final Function0 function02 = new Function0() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda19
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    int i7 = i;
                    Object obj = this;
                    switch (i7) {
                        case 0:
                            return ((CentralSurfacesImpl) obj).mCurrentDisplaySize;
                        case 1:
                            return Integer.valueOf(((CentralSurfacesImpl) obj).mDisplay.getRotation());
                        default:
                            return ((CentralSurfacesComponent) obj).createCollapsedStatusBarFragment();
                    }
                }
            };
            SemWindowManager semWindowManager = (SemWindowManager) secLightRevealScrimHelper.semWindowManager$delegate.getValue();
            Point point = secLightRevealScrimHelper.physicalDisplaySize;
            semWindowManager.getInitialDisplaySize(point);
            Log.d("SecLightRevealScrimHelper", "start physicalDisplaySize=" + point);
            IntentFilter intentFilter3 = new IntentFilter();
            intentFilter3.addAction("com.samsung.android.app.aodservice.intent.action.CHANGE_AOD_MODE");
            BroadcastDispatcher.registerReceiver$default(secLightRevealScrimHelper.broadcastDispatcher, new BroadcastReceiver() { // from class: com.android.systemui.statusbar.SecLightRevealScrimHelper$start$broadcastReceiver$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, Intent intent) {
                    SecLightRevealScrimHelper secLightRevealScrimHelper2 = SecLightRevealScrimHelper.this;
                    Point point2 = (Point) function0.invoke();
                    int intValue = ((Number) function02.invoke()).intValue();
                    secLightRevealScrimHelper2.getClass();
                    if (intent.getIntExtra("info", -1) != 11) {
                        Log.d("SecLightRevealScrimHelper", "updateDoubleTap no double tap");
                        return;
                    }
                    Point point3 = secLightRevealScrimHelper2.physicalDisplaySize;
                    float min = Math.min(point3.x, point3.y) / Math.min(point2.x, point2.y);
                    float[] floatArrayExtra = intent.getFloatArrayExtra(GlsIntent.Extras.EXTRA_LOCATION);
                    if (floatArrayExtra != null && floatArrayExtra.length == 2) {
                        if (intValue == 0) {
                            secLightRevealScrimHelper2.secRevealDoubleTapX = floatArrayExtra[0] / min;
                            secLightRevealScrimHelper2.secRevealDoubleTapY = floatArrayExtra[1] / min;
                        } else if (intValue == 1) {
                            secLightRevealScrimHelper2.secRevealDoubleTapX = floatArrayExtra[1] / min;
                            secLightRevealScrimHelper2.secRevealDoubleTapY = point2.y - (floatArrayExtra[0] / min);
                        } else if (intValue == 2) {
                            secLightRevealScrimHelper2.secRevealDoubleTapX = point2.x - (floatArrayExtra[0] / min);
                            secLightRevealScrimHelper2.secRevealDoubleTapY = point2.y - (floatArrayExtra[1] / min);
                        } else if (intValue == 3) {
                            secLightRevealScrimHelper2.secRevealDoubleTapX = point2.x - (floatArrayExtra[1] / min);
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
                    int i7 = point2.x;
                    int i8 = point2.y;
                    int i9 = point3.x;
                    int i10 = point3.y;
                    StringBuilder m88m = VIDirector$$ExternalSyntheticOutline0.m88m("updateDoubleTap: secRevealDoubleTapX=", f, " secRevealDoubleTapY=", f2, " currentDisplaySize.x=");
                    AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m88m, i7, " currentDisplaySize.y=", i8, " initialDisplaySize.x=");
                    AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m88m, i9, " initialDisplaySize.y=", i10, " screenSizeRatio=");
                    m88m.append(min);
                    m88m.append(" rotation=");
                    m88m.append(intValue);
                    Log.i("SecLightRevealScrimHelper", m88m.toString());
                }
            }, intentFilter3, null, UserHandle.ALL, 0, null, 48);
        }
    }

    public final void updateDisplaySize() {
        this.mDisplay.getMetrics(this.mDisplayMetrics);
        this.mDisplay.getSize(this.mCurrentDisplaySize);
        this.mMediaManager.getClass();
        Trace.beginSection("NotificationMediaManager#onDisplayUpdated");
        Trace.endSection();
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
        if ((!this.mDozing && shouldAnimateDozeWakeup()) || (this.mDozing && dozeParameters.mControlScreenOffAnimation && (z2 || (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY && this.mIsFolded)))) {
            z = true;
        }
        ((NotificationPanelViewController) this.mShadeSurface).setDozing(this.mDozing, z);
        updateQsExpansionEnabled();
        Trace.endSection();
    }

    public final boolean updateIsKeyguard() {
        return updateIsKeyguard(false);
    }

    public final void updateLightRevealScrimVisibility() {
        LightRevealScrim lightRevealScrim = this.mLightRevealScrim;
        if (lightRevealScrim == null) {
            return;
        }
        Flags flags = Flags.INSTANCE;
        this.mFeatureFlags.getClass();
        lightRevealScrim.setAlpha(this.mScrimController.mState.getMaxLightRevealScrimAlpha());
        if (LsRune.AOD_LIGHT_REVEAL) {
            return;
        }
        lightRevealScrim.setVisibility(8);
    }

    public final void updateNotificationPanelTouchState() {
        boolean z = isGoingToSleep() && !this.mDozeParameters.mControlScreenOffAnimation;
        boolean z2 = this.mDeviceInteractive;
        DeviceProvisionedController deviceProvisionedController = this.mDeviceProvisionedController;
        boolean z3 = !(z2 || this.mDozeServiceHost.mPulsing) || z || ((DeviceProvisionedControllerImpl) deviceProvisionedController).isFrpActive();
        this.mShadeLogger.logUpdateNotificationPanelTouchState(z3, isGoingToSleep(), !r1.mControlScreenOffAnimation, !this.mDeviceInteractive, !this.mDozeServiceHost.mPulsing, ((DeviceProvisionedControllerImpl) deviceProvisionedController).isFrpActive());
        NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) this.mShadeSurface;
        StringBuilder sb = notificationPanelViewController.mLogBuilder;
        sb.setLength(0);
        sb.append("setTouchAndAnimationDisabled: ");
        sb.append(notificationPanelViewController.mTouchDisabled);
        sb.append(" -> ");
        sb.append(z3);
        ((SecPanelLoggerImpl) notificationPanelViewController.mPanelLogger).addPanelStateInfoLog(sb, false);
        notificationPanelViewController.mTouchDisabled = z3;
        if (z3) {
            notificationPanelViewController.mSecAffordanceHelper.getClass();
            notificationPanelViewController.cancelHeightAnimator();
            if (notificationPanelViewController.mTracking) {
                notificationPanelViewController.onTrackingStopped(true);
            }
            notificationPanelViewController.notifyExpandingFinished();
        }
        boolean z4 = !z3;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationPanelViewController.mNotificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mAnimationsEnabled = z4;
        notificationStackScrollLayout.updateNotificationAnimationStates();
        if (!z4) {
            notificationStackScrollLayout.mSwipedOutViews.clear();
            notificationStackScrollLayout.mChildrenToRemoveAnimated.clear();
            NotificationStackScrollLayout.clearTemporaryViewsInGroup(notificationStackScrollLayout);
        }
        NotificationIconAreaController notificationIconAreaController = this.mNotificationIconAreaController;
        notificationIconAreaController.mAnimationsEnabled = z4;
        notificationIconAreaController.updateAnimations();
    }

    public final void updatePanelExpansionForKeyguard() {
        if (this.mState != 1 || this.mBiometricUnlockController.mMode == 1 || this.mBouncerShowing) {
            return;
        }
        if ((!isKeyguardShowing() || isOccluded()) && !this.mDozing) {
            return;
        }
        ShadeControllerImpl shadeControllerImpl = (ShadeControllerImpl) this.mShadeController;
        shadeControllerImpl.makeExpandedVisible(true);
        shadeControllerImpl.mNotificationPanelViewController.expand(false);
        shadeControllerImpl.mCommandQueue.recomputeDisableFlags(shadeControllerImpl.mDisplayId, false);
    }

    public final void updateQsExpansionEnabled() {
        UserSwitcherController userSwitcherController;
        boolean z = false;
        if (((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).isDeviceProvisioned() && (this.mUserSetup || (userSwitcherController = this.mUserSwitcherController) == null || !((UserSwitcherSettingsModel) ((UserRepositoryImpl) userSwitcherController.getUserInteractor().repository)._userSwitcherSettings.getValue()).isSimpleUserSwitcher)) {
            int i = this.mDisabled2;
            if (!((i & 4) != 0) && (i & 1) == 0 && !this.mDozing) {
                z = true;
            }
        }
        QuickSettingsController quickSettingsController = this.mQsController;
        quickSettingsController.mExpansionEnabledPolicy = z;
        InterfaceC1922QS interfaceC1922QS = quickSettingsController.mQs;
        if (interfaceC1922QS != null) {
            interfaceC1922QS.setHeaderClickable(quickSettingsController.isExpansionEnabled());
        }
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("updateQsExpansionEnabled - QS Expand enabled: ", z, "CentralSurfaces");
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

    public final void updateResources() {
        float f;
        Context context;
        SecQSPanelController secQSPanelController = this.mQSPanelController;
        if (secQSPanelController != null) {
            secQSPanelController.updateResources();
        }
        SecQuickQSPanelController secQuickQSPanelController = this.mQuickQSPanelController;
        if (secQuickQSPanelController != null) {
            secQuickQSPanelController.updateResources();
        }
        StatusBarWindowController statusBarWindowController = this.mStatusBarWindowController;
        if (statusBarWindowController != null) {
            StatusBarWindowController.GardenHeight gardenHeight = statusBarWindowController.mGardenHeight;
            gardenHeight.getClass();
            int i = 0;
            boolean z = false;
            while (true) {
                context = statusBarWindowController.mContext;
                if (i > 3) {
                    break;
                }
                int statusBarHeightForRotation = SystemBarUtils.getStatusBarHeightForRotation(context, i);
                int[] iArr = gardenHeight.heights;
                if (iArr[i] != statusBarHeightForRotation) {
                    iArr[i] = statusBarHeightForRotation;
                    z = true;
                }
                i++;
            }
            StatusBarWindowController.State state = statusBarWindowController.mCurrentState;
            if (z && statusBarWindowController.mIsAttached) {
                statusBarWindowController.mBarHeight = SystemBarUtils.getStatusBarHeight(context);
                statusBarWindowController.apply(state, true);
            } else {
                int statusBarHeight = SystemBarUtils.getStatusBarHeight(context);
                if (statusBarWindowController.mBarHeight != statusBarHeight) {
                    statusBarWindowController.mBarHeight = statusBarHeight;
                    statusBarWindowController.apply(state, false);
                }
            }
        }
        ShadeSurface shadeSurface = this.mShadeSurface;
        if (shadeSurface != null) {
            ((NotificationPanelViewController) shadeSurface).updateResources();
        }
        BrightnessMirrorController brightnessMirrorController = this.mBrightnessMirrorController;
        if (brightnessMirrorController != null) {
            brightnessMirrorController.updateLayout();
        }
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mStatusBarKeyguardViewManager;
        if (statusBarKeyguardViewManager != null) {
            statusBarKeyguardViewManager.updateResources();
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
            boolean z2 = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
            Point point2 = secLightRevealScrimHelper.physicalDisplaySize;
            if (z2) {
                ((SemWindowManager) secLightRevealScrimHelper.semWindowManager$delegate.getValue()).getInitialDisplaySize(point2);
            }
            int min = Math.min(point2.x, point2.y);
            int min2 = Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);
            f = min / min2;
            Log.i("SecLightRevealScrimHelper", "getPositionCorrectionRatio screenSizeRatio=" + f + " physicalScreenSize.x=" + point2.x + " baseWidthPixels = " + min + " currentWidthPixels = " + min2);
        } catch (Exception e) {
            Log.i("SecLightRevealScrimHelper", "getPositionCorrectionRatio exception = " + e);
            f = 1.0f;
        }
        secLightRevealScrimHelper.powerKeyYPos = (int) (r4.getResources().getDimensionPixelSize(android.R.dimen.resolver_profile_tab_margin) / f);
        int i2 = point.x;
        secLightRevealScrimHelper.secRevealCenterX = i2 / 2.0f;
        int i3 = point.y;
        secLightRevealScrimHelper.secRevealCenterY = i3 / 2.0f;
        float hypot = (float) Math.hypot(i2, i3);
        secLightRevealScrimHelper.secCircleReveal = new SecLightRevealScrimHelper.SecCircleReveal(secLightRevealScrimHelper.secRevealCenterX, secLightRevealScrimHelper.secRevealCenterY, hypot / 4, hypot / 2);
        float f2 = secLightRevealScrimHelper.secRevealCenterX;
        float f3 = secLightRevealScrimHelper.secRevealCenterY;
        int i4 = point.x;
        int i5 = point.y;
        int i6 = secLightRevealScrimHelper.powerKeyYPos;
        StringBuilder m88m = VIDirector$$ExternalSyntheticOutline0.m88m("updateResources: secRevealCenterX=", f2, " secRevealCenterY=", f3, " currentDisplaySize.x=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m88m, i4, " currentDisplaySize.y=", i5, " powerKeyY=");
        m88m.append(i6);
        m88m.append(" radius=");
        m88m.append(hypot);
        Log.i("SecLightRevealScrimHelper", m88m.toString());
        this.mPowerButtonReveal = new PowerButtonReveal(this.mSecLightRevealScrimHelper.powerKeyYPos);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002b, code lost:
    
        if (r1.isAnimatingBetweenKeyguardAndSurfaceBehind() == false) goto L15;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0093  */
    @Override // com.android.systemui.statusbar.phone.CentralSurfaces
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateScrimController() {
        boolean z;
        boolean isVisibleState;
        int i;
        Trace.beginSection("CentralSurfaces#updateScrimController");
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z2 = true;
        boolean z3 = false;
        if (keyguardStateControllerImpl.mShowing) {
            if (!this.mBiometricUnlockController.isWakeAndUnlock() && !keyguardStateControllerImpl.mKeyguardFadingAway && !keyguardStateControllerImpl.mKeyguardGoingAway) {
                KeyguardViewMediator keyguardViewMediator = this.mKeyguardViewMediator;
                if (!keyguardViewMediator.requestedShowSurfaceBehindKeyguard()) {
                }
            }
            z = true;
            BiometricUnlockController biometricUnlockController = this.mBiometricUnlockController;
            boolean z4 = biometricUnlockController.mAuthenticatedBioSourceType == BiometricSourceType.FACE ? biometricUnlockController.isWakeAndUnlock() || biometricUnlockController.mMode == 5 : biometricUnlockController.mKeyguardBypassController.getBypassEnabled() && (biometricUnlockController.isWakeAndUnlock() || biometricUnlockController.mMode == 5);
            ScrimController scrimController = this.mScrimController;
            scrimController.mExpansionAffectsAlpha = !z4;
            isVisibleState = this.mAlternateBouncerInteractor.isVisibleState();
            C301018 c301018 = this.mUnlockScrimCallback;
            if (!isVisibleState) {
                if ((!isOccluded() || this.mPanelExpanded) && ((i = this.mState) == 0 || i == 2 || this.mTransitionToFullShadeProgress > 0.0f)) {
                    scrimController.transitionTo(null, ScrimState.AUTH_SCRIMMED_SHADE);
                } else {
                    scrimController.transitionTo(null, ScrimState.AUTH_SCRIMMED);
                }
                c301018.onFinished();
            } else if (!this.mBouncerShowing || z) {
                DozeServiceHost dozeServiceHost = this.mDozeServiceHost;
                if (dozeServiceHost.mPulsing) {
                    scrimController.transitionTo(this.mDozeScrimController.mScrimCallback, ScrimState.PULSING);
                } else {
                    if (dozeServiceHost.mPendingScreenOffCallback != null) {
                        scrimController.transitionTo(new ScrimController.Callback() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl.13
                            @Override // com.android.systemui.statusbar.phone.ScrimController.Callback
                            public final void onFinished() {
                                DozeServiceHost dozeServiceHost2 = CentralSurfacesImpl.this.mDozeServiceHost;
                                Runnable runnable = dozeServiceHost2.mPendingScreenOffCallback;
                                if (runnable == null) {
                                    return;
                                }
                                runnable.run();
                                dozeServiceHost2.mPendingScreenOffCallback = null;
                            }
                        }, ScrimState.OFF);
                    } else {
                        KeyguardFastBioUnlockController keyguardFastBioUnlockController = (KeyguardFastBioUnlockController) Dependency.get(KeyguardFastBioUnlockController.class);
                        int mode = keyguardFastBioUnlockController.getMode();
                        int i2 = KeyguardFastBioUnlockController.MODE_FLAG_ENABLED | KeyguardFastBioUnlockController.MODE_FLAG_STARTED_DISPLAY_DOZE_OR_OFF;
                        if (mode != i2 && mode != (i2 | KeyguardFastBioUnlockController.MODE_FLAG_FRAME_REQUEST)) {
                            z2 = false;
                        }
                        if (!z2 || (((BiometricUnlockController) keyguardFastBioUnlockController.biometricUnlockControllerLazy.get()).mMode != 6 && !keyguardFastBioUnlockController.needsBlankScreen)) {
                            z3 = z2;
                        }
                        if (KeyguardFastBioUnlockController.DEBUG) {
                            KeyguardFastBioUnlockController.logD("isAODScrimState " + z3);
                        }
                        if (z3 || (this.mDozing && !z)) {
                            scrimController.transitionTo(null, ScrimState.AOD);
                            c301018.onFinished();
                        } else if (!keyguardStateControllerImpl.mShowing || isOccluded() || z) {
                            scrimController.transitionTo(c301018, ScrimState.UNLOCKED);
                        } else {
                            scrimController.transitionTo(null, ScrimState.KEYGUARD);
                        }
                    }
                }
            } else {
                scrimController.transitionTo(null, this.mStatusBarKeyguardViewManager.primaryBouncerNeedsScrimming() ? ScrimState.BOUNCER_SCRIMMED : ScrimState.BOUNCER);
                scrimController.mSecLsScrimControlHelper.setScrimAlphaForKeyguard(false);
            }
            updateLightRevealScrimVisibility();
            Trace.endSection();
        }
        z = false;
        BiometricUnlockController biometricUnlockController2 = this.mBiometricUnlockController;
        if (biometricUnlockController2.mAuthenticatedBioSourceType == BiometricSourceType.FACE) {
        }
        ScrimController scrimController2 = this.mScrimController;
        scrimController2.mExpansionAffectsAlpha = !z4;
        isVisibleState = this.mAlternateBouncerInteractor.isVisibleState();
        C301018 c3010182 = this.mUnlockScrimCallback;
        if (!isVisibleState) {
        }
        updateLightRevealScrimVisibility();
        Trace.endSection();
    }

    public final void updateTheme() {
        this.mUiBgExecutor.execute(new CentralSurfacesImpl$$ExternalSyntheticLambda5(this, 4));
        int i = this.mColorExtractor.mNeutralColorsLock.supportsDarkText() ? 2132018537 : 2132018524;
        Context context = this.mContext;
        if (context.getThemeResId() != i) {
            context.setTheme(i);
            ConfigurationControllerImpl configurationControllerImpl = (ConfigurationControllerImpl) this.mConfigurationController;
            configurationControllerImpl.getClass();
            List list = configurationControllerImpl.listeners;
            Iterator it = new ArrayList(list).iterator();
            while (it.hasNext()) {
                ConfigurationController.ConfigurationListener configurationListener = (ConfigurationController.ConfigurationListener) it.next();
                if (((ArrayList) list).contains(configurationListener)) {
                    configurationListener.onThemeChanged();
                }
            }
        }
    }

    public final void updateVisibleToUser() {
        ViewRootImpl viewRootImpl;
        int i;
        boolean z = this.mVisibleToUser;
        boolean z2 = this.mVisible && this.mDeviceInteractive;
        this.mVisibleToUser = z2;
        if (z != z2) {
            CentralSurfacesImpl$$ExternalSyntheticLambda4 centralSurfacesImpl$$ExternalSyntheticLambda4 = this.mOnBackInvokedCallback;
            Executor executor = this.mUiBgExecutor;
            int i2 = 2;
            NotificationLogger notificationLogger = this.mNotificationLogger;
            if (!z2) {
                notificationLogger.stopNotificationLogging();
                executor.execute(new CentralSurfacesImpl$$ExternalSyntheticLambda5(this, i2));
                if (this.mIsBackCallbackRegistered) {
                    NotificationShadeWindowView notificationShadeWindowView = this.mNotificationShadeWindowView;
                    viewRootImpl = notificationShadeWindowView != null ? notificationShadeWindowView.getViewRootImpl() : null;
                    if (viewRootImpl != null) {
                        viewRootImpl.getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(centralSurfacesImpl$$ExternalSyntheticLambda4);
                        this.mIsBackCallbackRegistered = false;
                        return;
                    }
                    return;
                }
                return;
            }
            boolean z3 = this.mHeadsUpManager.mHasPinnedNotification;
            final boolean z4 = !((StatusBarNotificationPresenter) this.mPresenter).isPresenterFullyCollapsed() && ((i = this.mState) == 0 || i == 2);
            final int activeNotificationsCount = this.mNotificationsController.getActiveNotificationsCount();
            if (z3 && ((StatusBarNotificationPresenter) this.mPresenter).isPresenterFullyCollapsed()) {
                activeNotificationsCount = 1;
            }
            executor.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    CentralSurfacesImpl centralSurfacesImpl = CentralSurfacesImpl.this;
                    boolean z5 = z4;
                    int i3 = activeNotificationsCount;
                    centralSurfacesImpl.getClass();
                    try {
                        centralSurfacesImpl.mBarService.onPanelRevealed(z5, i3);
                    } catch (RemoteException unused) {
                    }
                }
            });
            if (!notificationLogger.mLogging) {
                notificationLogger.mLogging = true;
                NotificationStackScrollLayoutController.this.mView.mListener = new NotificationLogger$$ExternalSyntheticLambda2(notificationLogger);
                notificationLogger.onChildLocationsChanged();
            }
            if (this.mIsBackCallbackRegistered) {
                return;
            }
            NotificationShadeWindowView notificationShadeWindowView2 = this.mNotificationShadeWindowView;
            viewRootImpl = notificationShadeWindowView2 != null ? notificationShadeWindowView2.getViewRootImpl() : null;
            if (viewRootImpl != null) {
                viewRootImpl.getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, centralSurfacesImpl$$ExternalSyntheticLambda4);
                this.mIsBackCallbackRegistered = true;
            }
        }
    }

    public final void userActivity() {
        if (this.mState == 1) {
            String str = LsRune.VALUE_CONFIG_CARRIER_TEXT_POLICY;
            this.mKeyguardViewMediatorCallback.userActivity();
        }
    }

    public final void wakeUpForFullScreenIntent(String str) {
        if ("com.android.cts.verifier".equals(str)) {
            if (isGoingToSleep() || this.mDozing) {
                this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), 2, "com.android.systemui:full_screen_intent");
                this.mWakeUpComingFromTouch = false;
            }
        }
    }

    public final void wakeUpIfDozing(long j, String str, int i) {
        boolean z;
        if (this.mDozing) {
            List list = this.mScreenOffAnimationController.animations;
            if (!(list instanceof Collection) || !((ArrayList) list).isEmpty()) {
                Iterator it = ((ArrayList) list).iterator();
                while (it.hasNext()) {
                    if (!(!((ScreenOffAnimation) it.next()).isAnimationPlaying())) {
                        z = false;
                        break;
                    }
                }
            }
            z = true;
            if (z) {
                this.mPowerManager.wakeUp(j, i, "com.android.systemui:".concat(str));
                this.mWakeUpComingFromTouch = true;
                this.mFalsingCollector.getClass();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0032, code lost:
    
        if (isOccluded() != false) goto L18;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0058 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x010f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean updateIsKeyguard(boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean isWakeAndUnlock = this.mBiometricUnlockController.isWakeAndUnlock();
        boolean z6 = this.mDozeServiceHost.mDozingRequested;
        ScreenLifecycle screenLifecycle = this.mScreenLifecycle;
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        if (z6) {
            if (this.mDeviceInteractive) {
                if (isGoingToSleep()) {
                    if (!(screenLifecycle.mScreenState == 0)) {
                        if (((KeyguardStateControllerImpl) keyguardStateController).mShowing) {
                        }
                    }
                }
            }
            z2 = true;
            if (isOccluded()) {
                int i = this.mWakefulnessLifecycle.mWakefulness;
                if (i == 1 || i == 2) {
                    z3 = true;
                    SysuiStatusBarStateController sysuiStatusBarStateController = this.mStatusBarStateController;
                    StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) sysuiStatusBarStateController;
                    z4 = ((statusBarStateControllerImpl.mKeyguardRequested && !z2) || isWakeAndUnlock || z3) ? false : true;
                    if (z2) {
                        updatePanelExpansionForKeyguard();
                    }
                    Lazy lazy = this.mCameraLauncherLazy;
                    MessageRouter messageRouter = this.mMessageRouter;
                    LockscreenShadeTransitionController lockscreenShadeTransitionController = this.mLockscreenShadeTransitionController;
                    MdmOverlayContainer mdmOverlayContainer = this.mMdmOverlayContainer;
                    ScreenOffAnimationController screenOffAnimationController = this.mScreenOffAnimationController;
                    if (!z4) {
                        List list = screenOffAnimationController.animations;
                        if (!(list instanceof Collection) || !((ArrayList) list).isEmpty()) {
                            Iterator it = ((ArrayList) list).iterator();
                            while (it.hasNext()) {
                                if (((ScreenOffAnimation) it.next()).isKeyguardHideDelayed()) {
                                    z5 = true;
                                    break;
                                }
                            }
                        }
                        z5 = false;
                        if (!z5 && !this.mKeyguardViewMediator.isOccludeAnimationPlaying()) {
                            Trace.beginSection("CentralSurfaces#hideKeyguard");
                            boolean z7 = statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide;
                            int i2 = statusBarStateControllerImpl.mState;
                            if (!statusBarStateControllerImpl.setState(0, z)) {
                                ((NotificationLockscreenUserManagerImpl) this.mLockscreenUserManager).updatePublicMode();
                            }
                            mdmOverlayContainer.updateMdmPolicy();
                            StringBuilder sb = this.mLogBuilder;
                            sb.setLength(0);
                            sb.append("hideKeyguardImpl leaveOpenOnKeyguardHide() : ");
                            sb.append(statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide);
                            sb.append("  isCollapsing : ");
                            sb.append(((NotificationPanelViewController) this.mShadeSurface).isCollapsing());
                            ((SecPanelLoggerImpl) this.mPanelLogger).addPanelStateInfoLog(sb, true);
                            if (statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide) {
                                if (!statusBarStateControllerImpl.mKeyguardRequested) {
                                    statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide = false;
                                }
                                KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardStateController;
                                long j = keyguardStateControllerImpl.mKeyguardFadingAwayDelay + keyguardStateControllerImpl.mKeyguardFadingAwayDuration;
                                lockscreenShadeTransitionController.logger.logOnHideKeyguard();
                                Function1 function1 = lockscreenShadeTransitionController.animationHandlerOnKeyguardDismiss;
                                if (function1 != null) {
                                    function1.invoke(Long.valueOf(j));
                                    lockscreenShadeTransitionController.animationHandlerOnKeyguardDismiss = null;
                                } else if (lockscreenShadeTransitionController.nextHideKeyguardNeedsNoAnimation) {
                                    lockscreenShadeTransitionController.nextHideKeyguardNeedsNoAnimation = false;
                                } else if (i2 != 2) {
                                    lockscreenShadeTransitionController.performDefaultGoToFullShadeAnimation(j);
                                }
                                NotificationEntry notificationEntry = lockscreenShadeTransitionController.draggedDownEntry;
                                if (notificationEntry != null) {
                                    ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                                    if (expandableNotificationRow != null) {
                                        expandableNotificationRow.setUserLocked(false);
                                    }
                                    lockscreenShadeTransitionController.draggedDownEntry = null;
                                }
                                this.mNavigationBarController.disableAnimationsDuringHide(this.mDisplayId, j);
                            } else if (!((NotificationPanelViewController) this.mShadeSurface).isCollapsing()) {
                                ((ShadeControllerImpl) this.mShadeController).instantCollapseShade();
                                this.mRemoteInputManager.closeRemoteInputs(true);
                            }
                            SecQSPanelController secQSPanelController = this.mQSPanelController;
                            if (secQSPanelController != null) {
                                secQSPanelController.refreshAllTiles();
                            }
                            ((MessageRouterImpl) messageRouter).cancelMessages(1003);
                            releaseGestureWakeLock();
                            ((CameraLauncher) lazy.get()).mKeyguardBypassController.launchingAffordance = false;
                            ((NotificationPanelViewController) this.mShadeSurface).mView.setAlpha(1.0f);
                            ((NotificationPanelViewController) this.mShadeSurface).mView.setTranslationX(0.0f);
                            ((NotificationPanelViewController) this.mShadeSurface).resetViewGroupFade();
                            updateDozingState();
                            updateScrimController();
                            Trace.endSection();
                            return z7;
                        }
                    } else if (!screenOffAnimationController.isKeyguardShowDelayed() && (!isGoingToSleep() || screenLifecycle.mScreenState != 3)) {
                        Trace.beginSection("CentralSurfaces#showKeyguard");
                        KeyguardStateControllerImpl keyguardStateControllerImpl2 = (KeyguardStateControllerImpl) keyguardStateController;
                        if (keyguardStateControllerImpl2.mLaunchTransitionFadingAway) {
                            ((NotificationPanelViewController) this.mShadeSurface).mView.animate().cancel();
                            ((NotificationPanelViewController) this.mShadeSurface).mView.setAlpha(1.0f);
                            ((CameraLauncher) lazy.get()).mKeyguardBypassController.launchingAffordance = false;
                            releaseGestureWakeLock();
                            keyguardStateControllerImpl2.mLaunchTransitionFadingAway = false;
                            new ArrayList(keyguardStateControllerImpl2.mCallbacks).forEach(new KeyguardStateControllerImpl$$ExternalSyntheticLambda1(3));
                            ((StatusBarNotificationPresenter) this.mPresenter).mMediaManager.updateMediaMetaData(true, true);
                        }
                        ((MessageRouterImpl) messageRouter).cancelMessages(1003);
                        if (!lockscreenShadeTransitionController.isWakingToShadeLocked) {
                            sysuiStatusBarStateController.setState$1(1);
                        }
                        if (statusBarStateControllerImpl.mState == 1 && this.mQsController.mExpanded) {
                            ((NotificationPanelViewController) this.mShadeSurface).resetViews(false);
                        }
                        mdmOverlayContainer.updateMdmPolicy();
                        updatePanelExpansionForKeyguard();
                        KeyguardWallpaper keyguardWallpaper = this.mKeyguardWallpaper;
                        if (keyguardWallpaper != null) {
                            KeyguardWallpaperController keyguardWallpaperController = (KeyguardWallpaperController) keyguardWallpaper;
                            if (keyguardWallpaperController.mWallpaperView != null) {
                                Log.d("KeyguardWallpaperController", "mWallpaperView.reset()");
                                keyguardWallpaperController.mWallpaperView.reset();
                            }
                        }
                        Trace.endSection();
                    }
                    return false;
                }
            }
            z3 = false;
            SysuiStatusBarStateController sysuiStatusBarStateController2 = this.mStatusBarStateController;
            StatusBarStateControllerImpl statusBarStateControllerImpl2 = (StatusBarStateControllerImpl) sysuiStatusBarStateController2;
            if (statusBarStateControllerImpl2.mKeyguardRequested) {
            }
            if (z2) {
            }
            Lazy lazy2 = this.mCameraLauncherLazy;
            MessageRouter messageRouter2 = this.mMessageRouter;
            LockscreenShadeTransitionController lockscreenShadeTransitionController2 = this.mLockscreenShadeTransitionController;
            MdmOverlayContainer mdmOverlayContainer2 = this.mMdmOverlayContainer;
            ScreenOffAnimationController screenOffAnimationController2 = this.mScreenOffAnimationController;
            if (!z4) {
            }
            return false;
        }
        z2 = false;
        if (isOccluded()) {
        }
        z3 = false;
        SysuiStatusBarStateController sysuiStatusBarStateController22 = this.mStatusBarStateController;
        StatusBarStateControllerImpl statusBarStateControllerImpl22 = (StatusBarStateControllerImpl) sysuiStatusBarStateController22;
        if (statusBarStateControllerImpl22.mKeyguardRequested) {
        }
        if (z2) {
        }
        Lazy lazy22 = this.mCameraLauncherLazy;
        MessageRouter messageRouter22 = this.mMessageRouter;
        LockscreenShadeTransitionController lockscreenShadeTransitionController22 = this.mLockscreenShadeTransitionController;
        MdmOverlayContainer mdmOverlayContainer22 = this.mMdmOverlayContainer;
        ScreenOffAnimationController screenOffAnimationController22 = this.mScreenOffAnimationController;
        if (!z4) {
        }
        return false;
    }
}
