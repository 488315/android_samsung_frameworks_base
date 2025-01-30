package com.android.systemui;

import android.app.AlarmManager;
import android.app.INotificationManager;
import android.app.IWallpaperManager;
import android.content.Context;
import android.content.pm.LauncherApps;
import android.content.res.Resources;
import android.hardware.SensorPrivacyManager;
import android.hardware.display.NightDisplayListener;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.IWindowManager;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.SecRotationWatcher;
import com.android.keyguard.clock.ClockManager;
import com.android.p038wm.shell.dagger.WMShellConcurrencyModule$1;
import com.android.p038wm.shell.keyguard.KeyguardTransitions;
import com.android.p038wm.shell.sysui.ShellInterface;
import com.android.p038wm.shell.transition.ShellTransitions;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.Dependency;
import com.android.systemui.accessibility.AccessibilityButtonModeObserver;
import com.android.systemui.accessibility.AccessibilityButtonTargetsObserver;
import com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.basic.util.CoverUtilWrapper;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.cover.CoverScreenManager;
import com.android.systemui.dagger.GlobalRootComponent;
import com.android.systemui.dagger.SysUIComponent;
import com.android.systemui.dagger.WMComponent;
import com.android.systemui.dock.DockManager;
import com.android.systemui.facewidget.plugin.ExternalClockProvider;
import com.android.systemui.facewidget.plugin.FaceWidgetPluginControllerImpl;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.globalactions.GlobalActionsComponent;
import com.android.systemui.indexsearch.SystemUIIndexMediator;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.log.SecPanelLogger;
import com.android.systemui.logging.NotiCinemaLogger;
import com.android.systemui.logging.PanelScreenShotBufferLogger;
import com.android.systemui.media.SubscreenMusicWidgetController;
import com.android.systemui.media.dialog.MediaOutputDialogFactory;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.notification.FullExpansionPanelNotiAlphaController;
import com.android.systemui.notification.NotificationBackupRestoreManager;
import com.android.systemui.p014qp.SubscreenQsPanelController;
import com.android.systemui.p014qp.util.AnimationUtils;
import com.android.systemui.p014qp.util.SubscreenUtil;
import com.android.systemui.p016qs.QSBackupRestoreManager;
import com.android.systemui.p016qs.ReduceBrightColorsController;
import com.android.systemui.p016qs.SecQSPanelResourcePicker;
import com.android.systemui.p016qs.tiles.dialog.InternetDialogFactory;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.pluginlock.PluginLockManager;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.plugins.PluginDependencyProvider;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.power.EnhancedEstimates;
import com.android.systemui.power.PowerUI;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.searcle.SearcleManager;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.multisim.MultiSIMController;
import com.android.systemui.shade.SecPanelBlockExpandingHelper;
import com.android.systemui.shade.SecPanelExpansionStateNotifier;
import com.android.systemui.shade.SecPanelPolicy;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.DevicePolicyManagerWrapper;
import com.android.systemui.shared.system.PackageManagerWrapper;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.statusbar.events.SystemStatusAnimationScheduler;
import com.android.systemui.statusbar.iconsOnly.NotificationIconTransitionController;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.ChannelEditorDialogController;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.row.OnUserInteractionCallback;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationSectionsManager;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger;
import com.android.systemui.statusbar.phone.ManagedProfileController;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper;
import com.android.systemui.statusbar.phone.SimpleStatusBarIconController;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.policy.AccessibilityController;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.statusbar.policy.BluetoothController;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DataSaverController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.HotspotController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.QSClockBellTower;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.statusbar.policy.SBluetoothController;
import com.android.systemui.statusbar.policy.SRotationLockControllerImpl;
import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.statusbar.policy.SensorPrivacyController;
import com.android.systemui.statusbar.policy.SmartReplyConstants;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.tracing.ProtoTracer;
import com.android.systemui.tuner.TunablePadding;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.uithreadmonitor.LooperSlowLogController;
import com.android.systemui.user.domain.interactor.UserInteractor;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.InitializationChecker;
import com.android.systemui.util.QsResetSettingsManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.leak.GarbageMonitor;
import com.android.systemui.util.leak.LeakDetector;
import com.android.systemui.util.leak.LeakReporter;
import com.android.systemui.util.sensors.AsyncSensorManager;
import com.android.systemui.wallpaper.WallpaperChangeNotifier;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.samsung.android.rune.CoreRune;
import com.samsung.systemui.splugins.SPluginDependencyProvider;
import com.samsung.systemui.splugins.SPluginManager;
import dagger.Lazy;
import java.util.Objects;
import java.util.Optional;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class SystemUIInitializer {
    private static final String TAG = "SystemUIFactory";
    private final Context mContext;
    private InitializationChecker mInitializationChecker;
    private GlobalRootComponent mRootComponent;
    private SysUIComponent mSysUIComponent;
    private WMComponent mWMComponent;

    public static /* synthetic */ void $r8$lambda$fM3ZYlHSXv2LZLPIQt0xKKN2evU(SystemUIInitializer systemUIInitializer, WMComponent.Builder builder, HandlerThread handlerThread) {
        systemUIInitializer.getClass();
        builder.setShellMainThread(handlerThread);
        systemUIInitializer.mWMComponent = builder.build();
    }

    public SystemUIInitializer(Context context) {
        this.mContext = context;
    }

    public abstract GlobalRootComponent.Builder getGlobalRootComponentBuilder();

    public GlobalRootComponent getRootComponent() {
        return this.mRootComponent;
    }

    public SysUIComponent getSysUIComponent() {
        return this.mSysUIComponent;
    }

    public String getVendorComponent(Resources resources) {
        return resources.getString(R.string.config_systemUIVendorServiceComponent);
    }

    public WMComponent getWMComponent() {
        return this.mWMComponent;
    }

    public void init(boolean z) {
        SysUIComponent.Builder displayController;
        GlobalRootComponent build = getGlobalRootComponentBuilder().context(this.mContext).instrumentationTest(z).build();
        this.mRootComponent = build;
        InitializationChecker initializationChecker = build.getInitializationChecker();
        this.mInitializationChecker = initializationChecker;
        boolean initializeComponents = initializationChecker.initializeComponents();
        Context context = this.mContext;
        final WMComponent.Builder wMComponentBuilder = this.mRootComponent.getWMComponentBuilder();
        if (this.mInitializationChecker.initializeComponents() && context.getResources().getBoolean(R.bool.config_enableShellMainThread)) {
            final HandlerThread handlerThread = new HandlerThread("wmshell.main", -4);
            if (CoreRune.SYSPERF_VI_BOOST) {
                new Handler().postDelayed(new WMShellConcurrencyModule$1(handlerThread), 10000L);
            }
            handlerThread.start();
            if (!Handler.createAsync(handlerThread.getLooper()).runWithScissors(new Runnable() { // from class: com.android.systemui.SystemUIInitializer$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    SystemUIInitializer.$r8$lambda$fM3ZYlHSXv2LZLPIQt0xKKN2evU(SystemUIInitializer.this, wMComponentBuilder, handlerThread);
                }
            }, 5000L)) {
                Log.w(TAG, "Failed to initialize WMComponent");
                throw new RuntimeException();
            }
        } else {
            this.mWMComponent = wMComponentBuilder.build();
        }
        SysUIComponent.Builder sysUIComponent = this.mRootComponent.getSysUIComponent();
        if (initializeComponents) {
            displayController = prepareSysUIComponentBuilder(sysUIComponent, this.mWMComponent).setShell(this.mWMComponent.getShell()).setPip(this.mWMComponent.getPip()).setSplitScreen(this.mWMComponent.getSplitScreen()).setOneHanded(this.mWMComponent.getOneHanded()).setBubbles(this.mWMComponent.getBubbles()).setTaskViewFactory(this.mWMComponent.getTaskViewFactory()).setTransitions(this.mWMComponent.getTransitions()).setKeyguardTransitions(this.mWMComponent.getKeyguardTransitions()).setStartingSurface(this.mWMComponent.getStartingSurface()).setDisplayAreaHelper(this.mWMComponent.getDisplayAreaHelper()).setRecentTasks(this.mWMComponent.getRecentTasks()).setBackAnimation(this.mWMComponent.getBackAnimation()).setDesktopMode(this.mWMComponent.getDesktopMode()).setEnterSplitGestureHandler(this.mWMComponent.getEnterSplitGestureHandler()).setSplitScreenController(this.mWMComponent.getSplitScreenController()).setDisplayController(this.mWMComponent.getDisplayController());
            this.mWMComponent.init();
        } else {
            displayController = prepareSysUIComponentBuilder(sysUIComponent, this.mWMComponent).setShell(new ShellInterface(this) { // from class: com.android.systemui.SystemUIInitializer.3
            }).setPip(Optional.ofNullable(null)).setSplitScreen(Optional.ofNullable(null)).setOneHanded(Optional.ofNullable(null)).setBubbles(Optional.ofNullable(null)).setTaskViewFactory(Optional.ofNullable(null)).setTransitions(new ShellTransitions(this) { // from class: com.android.systemui.SystemUIInitializer.2
            }).setKeyguardTransitions(new KeyguardTransitions(this) { // from class: com.android.systemui.SystemUIInitializer.1
            }).setDisplayAreaHelper(Optional.ofNullable(null)).setStartingSurface(Optional.ofNullable(null)).setRecentTasks(Optional.ofNullable(null)).setBackAnimation(Optional.ofNullable(null)).setDesktopMode(Optional.ofNullable(null)).setEnterSplitGestureHandler(Optional.ofNullable(null)).setSplitScreenController(Optional.ofNullable(null)).setDisplayController(this.mWMComponent.getDisplayController());
        }
        SysUIComponent build2 = displayController.build();
        this.mSysUIComponent = build2;
        if (initializeComponents) {
            build2.init();
        }
        Dependency createDependency = this.mSysUIComponent.createDependency();
        Lazy lazy = createDependency.mTimeTickHandler;
        Objects.requireNonNull(lazy);
        final int i = 0;
        Dependency$$ExternalSyntheticLambda0 dependency$$ExternalSyntheticLambda0 = new Dependency$$ExternalSyntheticLambda0(lazy, 0);
        Dependency.DependencyKey dependencyKey = Dependency.TIME_TICK_HANDLER;
        ArrayMap arrayMap = createDependency.mProviders;
        arrayMap.put(dependencyKey, dependency$$ExternalSyntheticLambda0);
        Lazy lazy2 = createDependency.mBgLooper;
        Objects.requireNonNull(lazy2);
        arrayMap.put(Dependency.BG_LOOPER, new Dependency$$ExternalSyntheticLambda3(lazy2, 0));
        Lazy lazy3 = createDependency.mMainLooper;
        Objects.requireNonNull(lazy3);
        arrayMap.put(Dependency.MAIN_LOOPER, new Dependency$$ExternalSyntheticLambda3(lazy3, 21));
        Lazy lazy4 = createDependency.mMainHandler;
        Objects.requireNonNull(lazy4);
        arrayMap.put(Dependency.MAIN_HANDLER, new Dependency$$ExternalSyntheticLambda4(lazy4, 0));
        Lazy lazy5 = createDependency.mMainExecutor;
        Objects.requireNonNull(lazy5);
        arrayMap.put(Dependency.MAIN_EXECUTOR, new Dependency$$ExternalSyntheticLambda4(lazy5, 13));
        Lazy lazy6 = createDependency.mBackgroundExecutor;
        Objects.requireNonNull(lazy6);
        arrayMap.put(Dependency.BACKGROUND_EXECUTOR, new Dependency$$ExternalSyntheticLambda4(lazy6, 24));
        Lazy lazy7 = createDependency.mActivityStarter;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy7, lazy7, 0, arrayMap, ActivityStarter.class);
        Lazy lazy8 = createDependency.mBroadcastDispatcher;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy8, lazy8, 16, arrayMap, BroadcastDispatcher.class);
        Lazy lazy9 = createDependency.mAsyncSensorManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy9, lazy9, 27, arrayMap, AsyncSensorManager.class);
        final Lazy lazy10 = createDependency.mBluetoothController;
        Objects.requireNonNull(lazy10);
        arrayMap.put(BluetoothController.class, new Dependency.LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda6
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                int i2 = i;
                Lazy lazy11 = lazy10;
                switch (i2) {
                }
                return lazy11.get();
            }
        });
        Lazy lazy11 = createDependency.mSBluetoothController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy11, lazy11, 11, arrayMap, SBluetoothController.class);
        Lazy lazy12 = createDependency.mSensorPrivacyManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy12, lazy12, 22, arrayMap, SensorPrivacyManager.class);
        Lazy lazy13 = createDependency.mLocationController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy13, lazy13, 0, arrayMap, LocationController.class);
        Lazy lazy14 = createDependency.mRotationLockController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy14, lazy14, 14, arrayMap, RotationLockController.class);
        boolean z2 = QpRune.QUICK_SETTINGS_SUBSCREEN;
        if (z2) {
            Lazy lazy15 = createDependency.mNetworkController;
            SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy15, lazy15, 25, arrayMap, NetworkController.class);
        }
        Lazy lazy16 = createDependency.mSRotationLockController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy16, lazy16, 0, arrayMap, SRotationLockControllerImpl.class);
        Lazy lazy17 = createDependency.mZenModeController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy17, lazy17, 17, arrayMap, ZenModeController.class);
        Lazy lazy18 = createDependency.mHotspotController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy18, lazy18, 28, arrayMap, HotspotController.class);
        Lazy lazy19 = createDependency.mCastController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy19, lazy19, 9, arrayMap, CastController.class);
        Lazy lazy20 = createDependency.mFlashlightController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy20, lazy20, 10, arrayMap, FlashlightController.class);
        Lazy lazy21 = createDependency.mKeyguardMonitor;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy21, lazy21, 11, arrayMap, KeyguardStateController.class);
        Lazy lazy22 = createDependency.mKeyguardUpdateMonitor;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy22, lazy22, 12, arrayMap, KeyguardUpdateMonitor.class);
        Lazy lazy23 = createDependency.mUserSwitcherController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy23, lazy23, 13, arrayMap, UserSwitcherController.class);
        Lazy lazy24 = createDependency.mUserInfoController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy24, lazy24, 14, arrayMap, UserInfoController.class);
        Lazy lazy25 = createDependency.mNightDisplayListener;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy25, lazy25, 15, arrayMap, NightDisplayListener.class);
        Lazy lazy26 = createDependency.mReduceBrightColorsController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy26, lazy26, 16, arrayMap, ReduceBrightColorsController.class);
        Lazy lazy27 = createDependency.mManagedProfileController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy27, lazy27, 17, arrayMap, ManagedProfileController.class);
        Lazy lazy28 = createDependency.mNextAlarmController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy28, lazy28, 18, arrayMap, NextAlarmController.class);
        Lazy lazy29 = createDependency.mDataSaverController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy29, lazy29, 19, arrayMap, DataSaverController.class);
        Lazy lazy30 = createDependency.mAccessibilityController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy30, lazy30, 20, arrayMap, AccessibilityController.class);
        Lazy lazy31 = createDependency.mDeviceProvisionedController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy31, lazy31, 22, arrayMap, DeviceProvisionedController.class);
        Lazy lazy32 = createDependency.mPluginManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy32, lazy32, 23, arrayMap, PluginManager.class);
        Lazy lazy33 = createDependency.mAssistManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy33, lazy33, 24, arrayMap, AssistManager.class);
        Lazy lazy34 = createDependency.mSecurityController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy34, lazy34, 25, arrayMap, SecurityController.class);
        Lazy lazy35 = createDependency.mLeakDetector;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy35, lazy35, 26, arrayMap, LeakDetector.class);
        Lazy lazy36 = createDependency.mLeakReportEmail;
        Objects.requireNonNull(lazy36);
        arrayMap.put(Dependency.LEAK_REPORT_EMAIL, new Dependency$$ExternalSyntheticLambda3(lazy36, 27));
        Lazy lazy37 = createDependency.mLeakReporter;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy37, lazy37, 28, arrayMap, LeakReporter.class);
        Lazy lazy38 = createDependency.mGarbageMonitor;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy38, lazy38, 29, arrayMap, GarbageMonitor.class);
        Lazy lazy39 = createDependency.mTunerService;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy39, lazy39, 1, arrayMap, TunerService.class);
        Lazy lazy40 = createDependency.mNotificationShadeWindowController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy40, lazy40, 2, arrayMap, NotificationShadeWindowController.class);
        Lazy lazy41 = createDependency.mTempStatusBarWindowController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy41, lazy41, 3, arrayMap, StatusBarWindowController.class);
        Lazy lazy42 = createDependency.mDarkIconDispatcher;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy42, lazy42, 4, arrayMap, DarkIconDispatcher.class);
        Lazy lazy43 = createDependency.mConfigurationController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy43, lazy43, 5, arrayMap, ConfigurationController.class);
        Lazy lazy44 = createDependency.mStatusBarIconController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy44, lazy44, 6, arrayMap, StatusBarIconController.class);
        Lazy lazy45 = createDependency.mScreenLifecycle;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy45, lazy45, 7, arrayMap, ScreenLifecycle.class);
        Lazy lazy46 = createDependency.mWakefulnessLifecycle;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy46, lazy46, 8, arrayMap, WakefulnessLifecycle.class);
        Lazy lazy47 = createDependency.mFragmentService;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy47, lazy47, 9, arrayMap, FragmentService.class);
        Lazy lazy48 = createDependency.mExtensionController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy48, lazy48, 10, arrayMap, ExtensionController.class);
        Lazy lazy49 = createDependency.mPluginDependencyProvider;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy49, lazy49, 11, arrayMap, PluginDependencyProvider.class);
        Lazy lazy50 = createDependency.mLocalBluetoothManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy50, lazy50, 12, arrayMap, LocalBluetoothManager.class);
        Lazy lazy51 = createDependency.mVolumeDialogController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy51, lazy51, 14, arrayMap, VolumeDialogController.class);
        Lazy lazy52 = createDependency.mMetricsLogger;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy52, lazy52, 15, arrayMap, MetricsLogger.class);
        Lazy lazy53 = createDependency.mAccessibilityManagerWrapper;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy53, lazy53, 16, arrayMap, AccessibilityManagerWrapper.class);
        Lazy lazy54 = createDependency.mSysuiColorExtractor;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy54, lazy54, 17, arrayMap, SysuiColorExtractor.class);
        Lazy lazy55 = createDependency.mTunablePaddingService;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy55, lazy55, 18, arrayMap, TunablePadding.TunablePaddingService.class);
        Lazy lazy56 = createDependency.mForegroundServiceController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy56, lazy56, 19, arrayMap, ForegroundServiceController.class);
        Lazy lazy57 = createDependency.mUiOffloadThread;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy57, lazy57, 20, arrayMap, UiOffloadThread.class);
        Lazy lazy58 = createDependency.mWarningsUI;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy58, lazy58, 21, arrayMap, PowerUI.WarningsUI.class);
        Lazy lazy59 = createDependency.mLightBarController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy59, lazy59, 22, arrayMap, LightBarController.class);
        Lazy lazy60 = createDependency.mIWindowManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy60, lazy60, 23, arrayMap, IWindowManager.class);
        Lazy lazy61 = createDependency.mOverviewProxyService;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy61, lazy61, 25, arrayMap, OverviewProxyService.class);
        Lazy lazy62 = createDependency.mNavBarModeController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy62, lazy62, 26, arrayMap, NavigationModeController.class);
        Lazy lazy63 = createDependency.mAccessibilityButtonModeObserver;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy63, lazy63, 27, arrayMap, AccessibilityButtonModeObserver.class);
        Lazy lazy64 = createDependency.mAccessibilityButtonListController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy64, lazy64, 28, arrayMap, AccessibilityButtonTargetsObserver.class);
        Lazy lazy65 = createDependency.mEnhancedEstimates;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$4(lazy65, lazy65, 29, arrayMap, EnhancedEstimates.class);
        Lazy lazy66 = createDependency.mVibratorHelper;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy66, lazy66, 1, arrayMap, VibratorHelper.class);
        Lazy lazy67 = createDependency.mIStatusBarService;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy67, lazy67, 2, arrayMap, IStatusBarService.class);
        Lazy lazy68 = createDependency.mDisplayMetrics;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy68, lazy68, 3, arrayMap, DisplayMetrics.class);
        Lazy lazy69 = createDependency.mLockscreenGestureLogger;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy69, lazy69, 4, arrayMap, LockscreenGestureLogger.class);
        Lazy lazy70 = createDependency.mShadeController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy70, lazy70, 5, arrayMap, ShadeController.class);
        Lazy lazy71 = createDependency.mNotificationRemoteInputManagerCallback;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy71, lazy71, 6, arrayMap, NotificationRemoteInputManager.Callback.class);
        Lazy lazy72 = createDependency.mAppOpsController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy72, lazy72, 7, arrayMap, AppOpsController.class);
        Lazy lazy73 = createDependency.mNavigationBarController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy73, lazy73, 8, arrayMap, NavigationBarController.class);
        Lazy lazy74 = createDependency.mAccessibilityFloatingMenuController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy74, lazy74, 9, arrayMap, AccessibilityFloatingMenuController.class);
        Lazy lazy75 = createDependency.mStatusBarStateController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy75, lazy75, 10, arrayMap, StatusBarStateController.class);
        Lazy lazy76 = createDependency.mNotificationLockscreenUserManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy76, lazy76, 11, arrayMap, NotificationLockscreenUserManager.class);
        Lazy lazy77 = createDependency.mNotificationMediaManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy77, lazy77, 12, arrayMap, NotificationMediaManager.class);
        Lazy lazy78 = createDependency.mNotificationGutsManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy78, lazy78, 13, arrayMap, NotificationGutsManager.class);
        Lazy lazy79 = createDependency.mNotificationRemoteInputManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy79, lazy79, 14, arrayMap, NotificationRemoteInputManager.class);
        Lazy lazy80 = createDependency.mSmartReplyConstants;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy80, lazy80, 15, arrayMap, SmartReplyConstants.class);
        Lazy lazy81 = createDependency.mNotificationListener;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy81, lazy81, 17, arrayMap, NotificationListener.class);
        Lazy lazy82 = createDependency.mNotificationLogger;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy82, lazy82, 18, arrayMap, NotificationLogger.class);
        Lazy lazy83 = createDependency.mKeyguardDismissUtil;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy83, lazy83, 19, arrayMap, KeyguardDismissUtil.class);
        Lazy lazy84 = createDependency.mSmartReplyController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy84, lazy84, 20, arrayMap, SmartReplyController.class);
        Lazy lazy85 = createDependency.mRemoteInputQuickSettingsDisabler;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy85, lazy85, 21, arrayMap, RemoteInputQuickSettingsDisabler.class);
        Lazy lazy86 = createDependency.mClockManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy86, lazy86, 22, arrayMap, ClockManager.class);
        Lazy lazy87 = createDependency.mPrivacyItemController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy87, lazy87, 23, arrayMap, PrivacyItemController.class);
        Lazy lazy88 = createDependency.mActivityManagerWrapper;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy88, lazy88, 24, arrayMap, ActivityManagerWrapper.class);
        Lazy lazy89 = createDependency.mDevicePolicyManagerWrapper;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy89, lazy89, 25, arrayMap, DevicePolicyManagerWrapper.class);
        Lazy lazy90 = createDependency.mPackageManagerWrapper;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy90, lazy90, 26, arrayMap, PackageManagerWrapper.class);
        Lazy lazy91 = createDependency.mSensorPrivacyController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy91, lazy91, 28, arrayMap, SensorPrivacyController.class);
        Lazy lazy92 = createDependency.mDockManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$5(lazy92, lazy92, 29, arrayMap, DockManager.class);
        final Lazy lazy93 = createDependency.mINotificationManager;
        Objects.requireNonNull(lazy93);
        final int i2 = 1;
        arrayMap.put(INotificationManager.class, new Dependency.LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda6
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                int i22 = i2;
                Lazy lazy112 = lazy93;
                switch (i22) {
                }
                return lazy112.get();
            }
        });
        final Lazy lazy94 = createDependency.mSysUiStateFlagsContainer;
        Objects.requireNonNull(lazy94);
        final int i3 = 2;
        arrayMap.put(SysUiState.class, new Dependency.LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda6
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                int i22 = i3;
                Lazy lazy112 = lazy94;
                switch (i22) {
                }
                return lazy112.get();
            }
        });
        final Lazy lazy95 = createDependency.mAlarmManager;
        Objects.requireNonNull(lazy95);
        final int i4 = 3;
        arrayMap.put(AlarmManager.class, new Dependency.LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda6
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                int i22 = i4;
                Lazy lazy112 = lazy95;
                switch (i22) {
                }
                return lazy112.get();
            }
        });
        final Lazy lazy96 = createDependency.mKeyguardSecurityModel;
        Objects.requireNonNull(lazy96);
        final int i5 = 4;
        arrayMap.put(KeyguardSecurityModel.class, new Dependency.LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda6
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                int i22 = i5;
                Lazy lazy112 = lazy96;
                switch (i22) {
                }
                return lazy112.get();
            }
        });
        final Lazy lazy97 = createDependency.mDozeParameters;
        Objects.requireNonNull(lazy97);
        final int i6 = 5;
        arrayMap.put(DozeParameters.class, new Dependency.LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda6
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                int i22 = i6;
                Lazy lazy112 = lazy97;
                switch (i22) {
                }
                return lazy112.get();
            }
        });
        final Lazy lazy98 = createDependency.mWallpaperManager;
        Objects.requireNonNull(lazy98);
        final int i7 = 6;
        arrayMap.put(IWallpaperManager.class, new Dependency.LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda6
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                int i22 = i7;
                Lazy lazy112 = lazy98;
                switch (i22) {
                }
                return lazy112.get();
            }
        });
        final Lazy lazy99 = createDependency.mCommandQueue;
        Objects.requireNonNull(lazy99);
        final int i8 = 7;
        arrayMap.put(CommandQueue.class, new Dependency.LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda6
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                int i22 = i8;
                Lazy lazy112 = lazy99;
                switch (i22) {
                }
                return lazy112.get();
            }
        });
        final Lazy lazy100 = createDependency.mProtoTracer;
        Objects.requireNonNull(lazy100);
        final int i9 = 8;
        arrayMap.put(ProtoTracer.class, new Dependency.LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda6
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                int i22 = i9;
                Lazy lazy112 = lazy100;
                switch (i22) {
                }
                return lazy112.get();
            }
        });
        Lazy lazy101 = createDependency.mDeviceConfigProxy;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy101, lazy101, 1, arrayMap, DeviceConfigProxy.class);
        Lazy lazy102 = createDependency.mTelephonyListenerManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy102, lazy102, 2, arrayMap, TelephonyListenerManager.class);
        Lazy lazy103 = createDependency.mAutoHideController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy103, lazy103, 3, arrayMap, AutoHideController.class);
        Lazy lazy104 = createDependency.mRecordingController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy104, lazy104, 4, arrayMap, RecordingController.class);
        Lazy lazy105 = createDependency.mMediaOutputDialogFactory;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy105, lazy105, 5, arrayMap, MediaOutputDialogFactory.class);
        Lazy lazy106 = createDependency.mSystemStatusAnimationSchedulerLazy;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy106, lazy106, 6, arrayMap, SystemStatusAnimationScheduler.class);
        Lazy lazy107 = createDependency.mPrivacyDotViewControllerLazy;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy107, lazy107, 7, arrayMap, PrivacyDotViewController.class);
        Lazy lazy108 = createDependency.mInternetDialogFactory;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy108, lazy108, 8, arrayMap, InternetDialogFactory.class);
        Lazy lazy109 = createDependency.mEdgeBackGestureHandlerFactoryLazy;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy109, lazy109, 9, arrayMap, EdgeBackGestureHandler.Factory.class);
        Lazy lazy110 = createDependency.mUiEventLogger;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy110, lazy110, 10, arrayMap, UiEventLogger.class);
        Lazy lazy111 = createDependency.mFeatureFlagsLazy;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy111, lazy111, 12, arrayMap, FeatureFlags.class);
        Lazy lazy112 = createDependency.mContentInsetsProviderLazy;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy112, lazy112, 13, arrayMap, StatusBarContentInsetsProvider.class);
        Lazy lazy113 = createDependency.mNotificationSectionsManagerLazy;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy113, lazy113, 14, arrayMap, NotificationSectionsManager.class);
        Lazy lazy114 = createDependency.mScreenOffAnimationController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy114, lazy114, 15, arrayMap, ScreenOffAnimationController.class);
        Lazy lazy115 = createDependency.mAmbientStateLazy;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy115, lazy115, 16, arrayMap, AmbientState.class);
        Lazy lazy116 = createDependency.mGroupMembershipManagerLazy;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy116, lazy116, 17, arrayMap, GroupMembershipManager.class);
        Lazy lazy117 = createDependency.mGroupExpansionManagerLazy;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy117, lazy117, 18, arrayMap, GroupExpansionManager.class);
        Lazy lazy118 = createDependency.mSystemUIDialogManagerLazy;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy118, lazy118, 19, arrayMap, SystemUIDialogManager.class);
        Lazy lazy119 = createDependency.mDialogLaunchAnimatorLazy;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy119, lazy119, 20, arrayMap, DialogLaunchAnimator.class);
        Lazy lazy120 = createDependency.mUserTrackerLazy;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy120, lazy120, 21, arrayMap, UserTracker.class);
        Lazy lazy121 = createDependency.mLooperSlowLogController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy121, lazy121, 23, arrayMap, LooperSlowLogController.class);
        Lazy lazy122 = createDependency.mSPluginManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy122, lazy122, 24, arrayMap, SPluginManager.class);
        Lazy lazy123 = createDependency.mSPluginDependencyProvider;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy123, lazy123, 25, arrayMap, SPluginDependencyProvider.class);
        Lazy lazy124 = createDependency.mUserInteractorLazy;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy124, lazy124, 26, arrayMap, UserInteractor.class);
        Lazy lazy125 = createDependency.mAnimationUtils;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy125, lazy125, 27, arrayMap, AnimationUtils.class);
        Lazy lazy126 = createDependency.mKeyguardVisibilityMonitor;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy126, lazy126, 28, arrayMap, KeyguardVisibilityMonitor.class);
        Lazy lazy127 = createDependency.mSecRotationWatcher;
        SystemUIInitializer$$ExternalSyntheticOutline0.m95m(lazy127, lazy127, 29, arrayMap, SecRotationWatcher.class);
        Lazy lazy128 = createDependency.mFastUnlockController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy128, lazy128, 1, arrayMap, KeyguardFastBioUnlockController.class);
        Lazy lazy129 = createDependency.mCentralSurfaces;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy129, lazy129, 2, arrayMap, CentralSurfaces.class);
        Lazy lazy130 = createDependency.mFoldController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy130, lazy130, 3, arrayMap, KeyguardFoldController.class);
        if (LsRune.AOD_FULLSCREEN) {
            Lazy lazy131 = createDependency.mUnlockedScreenOffAnimationHelper;
            SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy131, lazy131, 4, arrayMap, SecUnlockedScreenOffAnimationHelper.class);
        }
        if (BasicRune.NAVBAR_ENABLED) {
            Lazy lazy132 = createDependency.mNavBarStore;
            SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy132, lazy132, 5, arrayMap, NavBarStore.class);
            Lazy lazy133 = createDependency.mNavBarBgHandler;
            Objects.requireNonNull(lazy133);
            arrayMap.put(Dependency.NAVBAR_BG_HANDLER, new Dependency$$ExternalSyntheticLambda1(lazy133, 6));
            Lazy lazy134 = createDependency.mTaskbarDelegate;
            SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy134, lazy134, 7, arrayMap, TaskbarDelegate.class);
        }
        Lazy lazy135 = createDependency.mSettingsHelper;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy135, lazy135, 8, arrayMap, SettingsHelper.class);
        Lazy lazy136 = createDependency.mDisplayLifecycle;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy136, lazy136, 9, arrayMap, DisplayLifecycle.class);
        if (z2) {
            Lazy lazy137 = createDependency.mSubscreenUtil;
            SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy137, lazy137, 10, arrayMap, SubscreenUtil.class);
        }
        Lazy lazy138 = createDependency.mCoverUtilWrapper;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy138, lazy138, 11, arrayMap, CoverUtilWrapper.class);
        if (LsRune.SUBSCREEN_UI) {
            Lazy lazy139 = createDependency.mSubScreenManager;
            SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy139, lazy139, 12, arrayMap, SubScreenManager.class);
        }
        if (LsRune.COVER_SUPPORTED) {
            Lazy lazy140 = createDependency.mCoverScreenManager;
            SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy140, lazy140, 13, arrayMap, CoverScreenManager.class);
        }
        Lazy lazy141 = createDependency.mGlobalActionsComponent;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy141, lazy141, 15, arrayMap, GlobalActionsComponent.class);
        Lazy lazy142 = createDependency.mWallpaperEventNotifier;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy142, lazy142, 16, arrayMap, WallpaperEventNotifier.class);
        Lazy lazy143 = createDependency.mWallpaperChangeNotifier;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy143, lazy143, 17, arrayMap, WallpaperChangeNotifier.class);
        Lazy lazy144 = createDependency.mPluginLockManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy144, lazy144, 18, arrayMap, PluginLockManager.class);
        Lazy lazy145 = createDependency.mPluginWallpaperManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy145, lazy145, 19, arrayMap, PluginWallpaperManager.class);
        Lazy lazy146 = createDependency.mPluginLockStarManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy146, lazy146, 20, arrayMap, PluginLockStarManager.class);
        Lazy lazy147 = createDependency.mPluginFaceWidgetManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy147, lazy147, 21, arrayMap, PluginFaceWidgetManager.class);
        Lazy lazy148 = createDependency.mFaceWidgetController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy148, lazy148, 22, arrayMap, FaceWidgetPluginControllerImpl.class);
        Lazy lazy149 = createDependency.mExternalClockProvider;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy149, lazy149, 23, arrayMap, ExternalClockProvider.class);
        Lazy lazy150 = createDependency.mResetSettingsManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy150, lazy150, 24, arrayMap, QsResetSettingsManager.class);
        Lazy lazy151 = createDependency.mQSClockBellTower;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy151, lazy151, 26, arrayMap, QSClockBellTower.class);
        Lazy lazy152 = createDependency.mPanelBlockExpandingHelper;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy152, lazy152, 27, arrayMap, SecPanelBlockExpandingHelper.class);
        Lazy lazy153 = createDependency.mNotificationColorPicker;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy153, lazy153, 28, arrayMap, NotificationColorPicker.class);
        Lazy lazy154 = createDependency.mSystemUIIndexMediator;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$1(lazy154, lazy154, 29, arrayMap, SystemUIIndexMediator.class);
        Lazy lazy155 = createDependency.mSecQSPanelResourcePicker;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy155, lazy155, 1, arrayMap, SecQSPanelResourcePicker.class);
        Lazy lazy156 = createDependency.mNotificationBackupRestoreManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy156, lazy156, 2, arrayMap, NotificationBackupRestoreManager.class);
        Lazy lazy157 = createDependency.mQSBackupRestoreManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy157, lazy157, 3, arrayMap, QSBackupRestoreManager.class);
        Lazy lazy158 = createDependency.mKnoxStateMonitor;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy158, lazy158, 4, arrayMap, KnoxStateMonitor.class);
        Lazy lazy159 = createDependency.mNotifLiveDataStore;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy159, lazy159, 5, arrayMap, NotifLiveDataStore.class);
        Lazy lazy160 = createDependency.mHeadsUpManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy160, lazy160, 6, arrayMap, HeadsUpManager.class);
        Lazy lazy161 = createDependency.mSecPanelExpansionStateNotifier;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy161, lazy161, 7, arrayMap, SecPanelExpansionStateNotifier.class);
        Lazy lazy162 = createDependency.mNotificationIconTransitionManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy162, lazy162, 8, arrayMap, NotificationIconTransitionController.class);
        Lazy lazy163 = createDependency.mSecPanelLogger;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy163, lazy163, 9, arrayMap, SecPanelLogger.class);
        Lazy lazy164 = createDependency.mSecPanelPolicy;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy164, lazy164, 10, arrayMap, SecPanelPolicy.class);
        Lazy lazy165 = createDependency.mKeyguardShortcutManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy165, lazy165, 11, arrayMap, KeyguardShortcutManager.class);
        Lazy lazy166 = createDependency.mDesktopManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy166, lazy166, 12, arrayMap, DesktopManager.class);
        if (QpRune.QUICK_BAR_MULTISIM) {
            Lazy lazy167 = createDependency.mMultiSIMControllerLazy;
            SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy167, lazy167, 13, arrayMap, MultiSIMController.class);
        }
        Lazy lazy168 = createDependency.mSimpleStatusBarIconController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy168, lazy168, 14, arrayMap, SimpleStatusBarIconController.class);
        Lazy lazy169 = createDependency.mOnUserInteractionCallback;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy169, lazy169, 15, arrayMap, OnUserInteractionCallback.class);
        Lazy lazy170 = createDependency.mChannelEditorDialogController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy170, lazy170, 16, arrayMap, ChannelEditorDialogController.class);
        Lazy lazy171 = createDependency.mHighPriorityProvider;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy171, lazy171, 18, arrayMap, HighPriorityProvider.class);
        Lazy lazy172 = createDependency.mAssistantFeedbackController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy172, lazy172, 19, arrayMap, AssistantFeedbackController.class);
        Lazy lazy173 = createDependency.mPeopleSpaceWidgetManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy173, lazy173, 20, arrayMap, PeopleSpaceWidgetManager.class);
        Lazy lazy174 = createDependency.mUserContextProvider;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy174, lazy174, 21, arrayMap, UserContextProvider.class);
        Lazy lazy175 = createDependency.mBubblesManagerOptional;
        Objects.requireNonNull(lazy175);
        arrayMap.put(Dependency.BUBBLE_MANAGER, new Dependency$$ExternalSyntheticLambda2(lazy175, 22));
        Lazy lazy176 = createDependency.mBgHandler;
        Objects.requireNonNull(lazy176);
        arrayMap.put(Dependency.BG_HANDLER, new Dependency$$ExternalSyntheticLambda2(lazy176, 23));
        Lazy lazy177 = createDependency.mLauncherApps;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy177, lazy177, 24, arrayMap, LauncherApps.class);
        if (NotiRune.NOTI_SUBSCREEN_ALL) {
            Lazy lazy178 = createDependency.mSubscreenNotificationController;
            SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy178, lazy178, 25, arrayMap, SubscreenNotificationController.class);
            Lazy lazy179 = createDependency.mNotifCollection;
            SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy179, lazy179, 26, arrayMap, NotifCollection.class);
        }
        Lazy lazy180 = createDependency.mFullExpansionPanelNotiAlphaController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy180, lazy180, 27, arrayMap, FullExpansionPanelNotiAlphaController.class);
        Lazy lazy181 = createDependency.mNotificationShelfManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$2(lazy181, lazy181, 29, arrayMap, NotificationShelfManager.class);
        Lazy lazy182 = createDependency.mPanelScreenShotBufferLogger;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy182, lazy182, 1, arrayMap, PanelScreenShotBufferLogger.class);
        Lazy lazy183 = createDependency.mNotiCinemaLogger;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy183, lazy183, 2, arrayMap, NotiCinemaLogger.class);
        if (QpRune.NOTI_SUBSCREEN_NOTIFICATION_SECOND) {
            Lazy lazy184 = createDependency.mSubscreenQsPanelController;
            SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy184, lazy184, 3, arrayMap, SubscreenQsPanelController.class);
        }
        Lazy lazy185 = createDependency.mShelfToolTipManager;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy185, lazy185, 4, arrayMap, ShelfToolTipManager.class);
        Lazy lazy186 = createDependency.mSubscreenMusicWidgetController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy186, lazy186, 5, arrayMap, SubscreenMusicWidgetController.class);
        if (QpRune.QUICK_TABLET_BG) {
            Lazy lazy187 = createDependency.mNotificationsController;
            SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy187, lazy187, 6, arrayMap, NotificationsController.class);
        }
        Lazy lazy188 = createDependency.mShadeHeaderController;
        SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy188, lazy188, 7, arrayMap, ShadeHeaderController.class);
        if (BasicRune.SEARCLE) {
            Lazy lazy189 = createDependency.mSearcleManager;
            SystemUIInitializer$$ExternalSyntheticOutline0.m$3(lazy189, lazy189, 8, arrayMap, SearcleManager.class);
        }
        Dependency.setInstance(createDependency);
    }

    public SysUIComponent.Builder prepareSysUIComponentBuilder(SysUIComponent.Builder builder, WMComponent wMComponent) {
        return builder;
    }
}
