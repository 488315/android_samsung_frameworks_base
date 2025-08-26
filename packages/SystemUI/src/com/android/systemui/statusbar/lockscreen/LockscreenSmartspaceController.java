package com.android.systemui.statusbar.lockscreen;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceConfig;
import android.app.smartspace.SmartspaceManager;
import android.app.smartspace.SmartspaceSession;
import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.SmartspaceTargetEvent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda24;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.Utils;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.BcSmartspaceConfigPlugin;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.clocks.WeatherData;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.regionsampling.RegionSampler;
import com.android.systemui.smartspace.ui.binder.SmartspaceViewBinder;
import com.android.systemui.smartspace.ui.viewmodel.SmartspaceViewModel;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class LockscreenSmartspaceController implements Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public final Executor bgExecutor;
    public final Handler bgHandler;
    public final KeyguardBypassController bypassController;
    public final LockscreenSmartspaceController$bypassStateChangedListener$1 bypassStateChangedListener;
    public final LockscreenSmartspaceController$configChangeListener$1 configChangeListener;
    public final BcSmartspaceConfigPlugin configPlugin;
    public final ConfigurationController configurationController;
    public final ContentResolver contentResolver;
    public final Context context;
    public final BcSmartspaceDataPlugin datePlugin;
    public final DeviceProvisionedController deviceProvisionedController;
    public final LockscreenSmartspaceController$deviceProvisionedListener$1 deviceProvisionedListener;
    public final Execution execution;
    public final FalsingManager falsingManager;
    public final boolean isDateWeatherDecoupled;
    public final boolean isEnabled;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public UserHandle managedUserHandle;
    public final BcSmartspaceDataPlugin plugin;
    public final Deque recentSmartspaceData;
    public final Map regionSamplers;
    public final SecureSettings secureSettings;
    public SmartspaceSession session;
    public final LockscreenSmartspaceController$sessionListener$1 sessionListener;
    public final LockscreenSmartspaceController$settingsObserver$1 settingsObserver;
    public boolean showNotifications;
    public boolean showSensitiveContentForCurrentUser;
    public boolean showSensitiveContentForManagedUser;
    public final SmartspaceViewModel.Factory smartspaceViewModelFactory;
    public final Set smartspaceViews;
    public final LockscreenSmartspaceController$stateChangeListener$1 stateChangeListener;
    public final StatusBarStateController statusBarStateController;
    public final LockscreenSmartspaceController$statusBarStateListener$1 statusBarStateListener;
    public boolean suppressDisconnects;
    public final SystemClock systemClock;
    public final Executor uiExecutor;
    public SmartspaceManager userSmartspaceManager;
    public final UserTracker userTracker;
    public final LockscreenSmartspaceController$userTrackerCallback$1 userTrackerCallback;
    public final BcSmartspaceDataPlugin weatherPlugin;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class SmartspaceTimeChangedDelegate implements BcSmartspaceDataPlugin.TimeChangedDelegate {
        public final KeyguardUpdateMonitor keyguardUpdateMonitor;
        public LockscreenSmartspaceController$SmartspaceTimeChangedDelegate$register$1 keyguardUpdateMonitorCallback;

        public SmartspaceTimeChangedDelegate(KeyguardUpdateMonitor keyguardUpdateMonitor) {
            this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v1, types: [com.android.keyguard.KeyguardUpdateMonitorCallback, com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$SmartspaceTimeChangedDelegate$register$1] */
        @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.TimeChangedDelegate
        public final void register(final Runnable runnable) {
            if (this.keyguardUpdateMonitorCallback != null) {
                unregister();
            }
            ?? r0 = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$SmartspaceTimeChangedDelegate$register$1
                @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
                public final void onTimeChanged() {
                    runnable.run();
                }
            };
            this.keyguardUpdateMonitorCallback = r0;
            this.keyguardUpdateMonitor.registerCallback(r0);
            runnable.run();
        }

        @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.TimeChangedDelegate
        public final void unregister() {
            this.keyguardUpdateMonitor.removeCallback(this.keyguardUpdateMonitorCallback);
            this.keyguardUpdateMonitorCallback = null;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v13, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$stateChangeListener$1] */
    /* JADX WARN: Type inference failed for: r1v14, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$sessionListener$1] */
    /* JADX WARN: Type inference failed for: r1v15, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$userTrackerCallback$1] */
    /* JADX WARN: Type inference failed for: r1v16, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$settingsObserver$1] */
    /* JADX WARN: Type inference failed for: r1v17, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$configChangeListener$1] */
    /* JADX WARN: Type inference failed for: r1v19, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$deviceProvisionedListener$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$bypassStateChangedListener$1] */
    public LockscreenSmartspaceController(Context context, FeatureFlags featureFlags, ActivityStarter activityStarter, FalsingManager falsingManager, SystemClock systemClock, SecureSettings secureSettings, UserTracker userTracker, ContentResolver contentResolver, ConfigurationController configurationController, StatusBarStateController statusBarStateController, DeviceProvisionedController deviceProvisionedController, KeyguardBypassController keyguardBypassController, KeyguardUpdateMonitor keyguardUpdateMonitor, WakefulnessLifecycle wakefulnessLifecycle, SmartspaceViewModel.Factory factory, DumpManager dumpManager, Execution execution, Executor executor, Executor executor2, final Handler handler, Handler handler2, Optional<BcSmartspaceDataPlugin> optional, Optional<BcSmartspaceDataPlugin> optional2, Optional<BcSmartspaceDataPlugin> optional3, Optional<BcSmartspaceConfigPlugin> optional4) {
        this.context = context;
        this.activityStarter = activityStarter;
        this.falsingManager = falsingManager;
        this.systemClock = systemClock;
        this.secureSettings = secureSettings;
        this.userTracker = userTracker;
        this.contentResolver = contentResolver;
        this.configurationController = configurationController;
        this.statusBarStateController = statusBarStateController;
        this.deviceProvisionedController = deviceProvisionedController;
        this.bypassController = keyguardBypassController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.smartspaceViewModelFactory = factory;
        this.execution = execution;
        this.uiExecutor = executor;
        this.bgExecutor = executor2;
        this.bgHandler = handler2;
        BcSmartspaceDataPlugin bcSmartspaceDataPluginOrElse = optional.orElse(null);
        this.datePlugin = bcSmartspaceDataPluginOrElse;
        BcSmartspaceDataPlugin bcSmartspaceDataPluginOrElse2 = optional2.orElse(null);
        this.weatherPlugin = bcSmartspaceDataPluginOrElse2;
        BcSmartspaceDataPlugin bcSmartspaceDataPluginOrElse3 = optional3.orElse(null);
        this.plugin = bcSmartspaceDataPluginOrElse3;
        this.configPlugin = optional4.orElse(null);
        this.recentSmartspaceData = new LinkedList();
        this.smartspaceViews = new LinkedHashSet();
        this.regionSamplers = new LinkedHashMap();
        Flags.INSTANCE.getClass();
        featureFlags.getClass();
        this.stateChangeListener = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$stateChangeListener$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                BcSmartspaceDataPlugin.SmartspaceView smartspaceView = (BcSmartspaceDataPlugin.SmartspaceView) view;
                LockscreenSmartspaceController lockscreenSmartspaceController = this.this$0;
                int i = LockscreenSmartspaceController.$r8$clinit;
                lockscreenSmartspaceController.getClass();
                smartspaceView.setSplitShadeEnabled(false);
                this.this$0.smartspaceViews.add(smartspaceView);
                this.this$0.connectSession();
                LockscreenSmartspaceController.access$updateTextColorFromWallpaper(this.this$0);
                LockscreenSmartspaceController lockscreenSmartspaceController2 = this.this$0;
                lockscreenSmartspaceController2.statusBarStateListener.onDozeAmountChanged(0.0f, lockscreenSmartspaceController2.statusBarStateController.getDozeAmount());
                this.this$0.getClass();
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                BcSmartspaceDataPlugin.SmartspaceView smartspaceView = (BcSmartspaceDataPlugin.SmartspaceView) view;
                this.this$0.smartspaceViews.remove(smartspaceView);
                RegionSampler regionSampler = (RegionSampler) ((LinkedHashMap) this.this$0.regionSamplers).get(view);
                if (regionSampler != null) {
                    WallpaperManager wallpaperManager = regionSampler.wallpaperManager;
                    if (wallpaperManager != null) {
                        wallpaperManager.removeOnColorsChangedListener(regionSampler);
                    }
                    regionSampler.sampledView.removeOnLayoutChangeListener(regionSampler.layoutChangedListener);
                }
                this.this$0.regionSamplers.remove(smartspaceView);
                if (this.this$0.smartspaceViews.isEmpty()) {
                    this.this$0.disconnect();
                }
            }
        };
        this.sessionListener = new SmartspaceSession.OnTargetsAvailableListener() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$sessionListener$1
            public final void onTargetsAvailable(List list) {
                WeatherData weatherDataFromBundle;
                Object next;
                Bundle extras;
                this.this$0.execution.assertIsMainThread();
                BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.this$0.weatherPlugin;
                if (bcSmartspaceDataPlugin != null) {
                    bcSmartspaceDataPlugin.onTargetsAvailable(list);
                }
                Instant instantOfEpochMilli = Instant.ofEpochMilli(this.this$0.systemClock.currentTimeMillis());
                List list2 = list;
                Iterator it = list2.iterator();
                while (true) {
                    weatherDataFromBundle = null;
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    SmartspaceTarget smartspaceTarget = (SmartspaceTarget) next;
                    if (smartspaceTarget.getFeatureType() == 1 && instantOfEpochMilli.isAfter(Instant.ofEpochMilli(smartspaceTarget.getCreationTimeMillis())) && instantOfEpochMilli.isBefore(Instant.ofEpochMilli(smartspaceTarget.getExpiryTimeMillis()))) {
                        break;
                    }
                }
                SmartspaceTarget smartspaceTarget2 = (SmartspaceTarget) next;
                if (smartspaceTarget2 != null) {
                    SmartspaceAction headerAction = smartspaceTarget2.getHeaderAction();
                    final Intent intent = headerAction != null ? headerAction.getIntent() : null;
                    SmartspaceAction baseAction = smartspaceTarget2.getBaseAction();
                    if (baseAction != null && (extras = baseAction.getExtras()) != null) {
                        final LockscreenSmartspaceController lockscreenSmartspaceController = this.this$0;
                        weatherDataFromBundle = WeatherData.Companion.fromBundle(extras, new Function1() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$sessionListener$1$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                Intent intent2 = intent;
                                LockscreenSmartspaceController lockscreenSmartspaceController2 = lockscreenSmartspaceController;
                                if (!lockscreenSmartspaceController2.falsingManager.isFalseTap(1)) {
                                    lockscreenSmartspaceController2.activityStarter.startActivity(intent2, true, (ActivityTransitionAnimator.Controller) null, false);
                                }
                                return Unit.INSTANCE;
                            }
                        });
                    }
                    if (weatherDataFromBundle != null) {
                        KeyguardUpdateMonitor keyguardUpdateMonitor2 = this.this$0.keyguardUpdateMonitor;
                        keyguardUpdateMonitor2.mHandler.post(new KeyguardUpdateMonitor$$ExternalSyntheticLambda24(keyguardUpdateMonitor2, weatherDataFromBundle, 1));
                    }
                }
                LockscreenSmartspaceController lockscreenSmartspaceController2 = this.this$0;
                ArrayList arrayList = new ArrayList();
                for (Object obj : list2) {
                    SmartspaceTarget smartspaceTarget3 = (SmartspaceTarget) obj;
                    if (!lockscreenSmartspaceController2.isDateWeatherDecoupled || smartspaceTarget3.getFeatureType() != 1) {
                        if (lockscreenSmartspaceController2.showNotifications) {
                            UserHandle userHandle = smartspaceTarget3.getUserHandle();
                            UserTrackerImpl userTrackerImpl = (UserTrackerImpl) lockscreenSmartspaceController2.userTracker;
                            if (Intrinsics.areEqual(userHandle, userTrackerImpl.getUserHandle())) {
                                if (!smartspaceTarget3.isSensitive() || lockscreenSmartspaceController2.showSensitiveContentForCurrentUser) {
                                    arrayList.add(obj);
                                }
                            } else if (Intrinsics.areEqual(userHandle, lockscreenSmartspaceController2.managedUserHandle) && userTrackerImpl.getUserHandle().getIdentifier() == 0 && (!smartspaceTarget3.isSensitive() || lockscreenSmartspaceController2.showSensitiveContentForManagedUser)) {
                                arrayList.add(obj);
                            }
                        } else if (smartspaceTarget3.getFeatureType() == 1) {
                            arrayList.add(obj);
                        }
                    }
                }
                LockscreenSmartspaceController lockscreenSmartspaceController3 = this.this$0;
                synchronized (lockscreenSmartspaceController3.recentSmartspaceData) {
                    ((LinkedList) lockscreenSmartspaceController3.recentSmartspaceData).offerLast(arrayList);
                    if (((LinkedList) lockscreenSmartspaceController3.recentSmartspaceData).size() > 5) {
                        ((LinkedList) lockscreenSmartspaceController3.recentSmartspaceData).pollFirst();
                    }
                    Unit unit = Unit.INSTANCE;
                }
                BcSmartspaceDataPlugin bcSmartspaceDataPlugin2 = this.this$0.plugin;
                if (bcSmartspaceDataPlugin2 != null) {
                    bcSmartspaceDataPlugin2.onTargetsAvailable(arrayList);
                }
            }
        };
        this.userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$userTrackerCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                LockscreenSmartspaceController lockscreenSmartspaceController = this.this$0;
                lockscreenSmartspaceController.execution.assertIsMainThread();
                lockscreenSmartspaceController.reloadSmartspace();
            }
        };
        this.settingsObserver = new ContentObserver(handler) { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$settingsObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                this.this$0.execution.assertIsMainThread();
                this.this$0.reloadSmartspace();
            }
        };
        this.configChangeListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$configChangeListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                LockscreenSmartspaceController lockscreenSmartspaceController = this.this$0;
                lockscreenSmartspaceController.execution.assertIsMainThread();
                LockscreenSmartspaceController.access$updateTextColorFromWallpaper(lockscreenSmartspaceController);
            }
        };
        this.statusBarStateListener = new LockscreenSmartspaceController$statusBarStateListener$1(this);
        ?? r1 = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$deviceProvisionedListener$1
            @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
            public final void onDeviceProvisionedChanged() {
                int i = LockscreenSmartspaceController.$r8$clinit;
                this.this$0.connectSession();
            }

            @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
            public final void onUserSetupChanged() {
                int i = LockscreenSmartspaceController.$r8$clinit;
                this.this$0.connectSession();
            }
        };
        this.deviceProvisionedListener = r1;
        this.bypassStateChangedListener = new KeyguardBypassController.OnBypassStateChangedListener() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$bypassStateChangedListener$1
            @Override // com.android.systemui.statusbar.phone.KeyguardBypassController.OnBypassStateChangedListener
            public final void onBypassStateChanged(boolean z) {
                int i = LockscreenSmartspaceController.$r8$clinit;
                LockscreenSmartspaceController lockscreenSmartspaceController = this.this$0;
                boolean bypassEnabled = lockscreenSmartspaceController.bypassController.getBypassEnabled();
                Iterator it = lockscreenSmartspaceController.smartspaceViews.iterator();
                while (it.hasNext()) {
                    ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setKeyguardBypassEnabled(bypassEnabled);
                }
            }
        };
        new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$wakefulnessLifecycleObserver$1
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedGoingToSleep() {
                Iterator it = this.this$0.smartspaceViews.iterator();
                while (it.hasNext()) {
                    ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setScreenOn(false);
                }
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                Iterator it = this.this$0.smartspaceViews.iterator();
                while (it.hasNext()) {
                    ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setScreenOn(true);
                }
            }
        };
        ((DeviceProvisionedControllerImpl) deviceProvisionedController).addCallback(r1);
        dumpManager.registerDumpable(this);
        boolean z = false;
        this.isEnabled = bcSmartspaceDataPluginOrElse3 != null;
        if (bcSmartspaceDataPluginOrElse != null && bcSmartspaceDataPluginOrElse2 != null) {
            z = true;
        }
        this.isDateWeatherDecoupled = z;
    }

    public static final void access$updateTextColorFromWallpaper(LockscreenSmartspaceController lockscreenSmartspaceController) {
        lockscreenSmartspaceController.getClass();
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(lockscreenSmartspaceController.context, R.attr.wallpaperTextColor, 0);
        Iterator it = lockscreenSmartspaceController.smartspaceViews.iterator();
        while (it.hasNext()) {
            ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setPrimaryTextColor(colorAttrDefaultColor);
        }
    }

    public final View buildAndConnectDateView(ViewGroup viewGroup) {
        this.execution.assertIsMainThread();
        if (!this.isEnabled) {
            throw new RuntimeException("Cannot build view when not enabled");
        }
        if (!this.isDateWeatherDecoupled) {
            throw new RuntimeException("Cannot build date view when not decoupled");
        }
        View viewBuildView = buildView("date_view", viewGroup, this.datePlugin, null);
        connectSession();
        return viewBuildView;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final View buildView(String str, ViewGroup viewGroup, BcSmartspaceDataPlugin bcSmartspaceDataPlugin, BcSmartspaceConfigPlugin bcSmartspaceConfigPlugin) {
        if (bcSmartspaceDataPlugin == null) {
            return null;
        }
        BcSmartspaceDataPlugin.SmartspaceView view = bcSmartspaceDataPlugin.getView(viewGroup);
        if (bcSmartspaceConfigPlugin != null) {
            view.registerConfigProvider(bcSmartspaceConfigPlugin);
        }
        view.setBgHandler(this.bgHandler);
        view.setUiSurface(BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD);
        view.setTimeChangedDelegate(new SmartspaceTimeChangedDelegate(this.keyguardUpdateMonitor));
        view.registerDataProvider(bcSmartspaceDataPlugin);
        view.setIntentStarter(new BcSmartspaceDataPlugin.IntentStarter() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController.buildView.2
            @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.IntentStarter
            public final void startIntent(View view2, Intent intent, boolean z) {
                LockscreenSmartspaceController lockscreenSmartspaceController = LockscreenSmartspaceController.this;
                if (z) {
                    lockscreenSmartspaceController.activityStarter.startActivity(intent, true, (ActivityTransitionAnimator.Controller) null, true);
                } else {
                    lockscreenSmartspaceController.activityStarter.postStartActivityDismissingKeyguard(intent, 0);
                }
            }

            @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.IntentStarter
            public final void startPendingIntent(View view2, PendingIntent pendingIntent, boolean z) throws PendingIntent.CanceledException {
                if (z) {
                    pendingIntent.send(ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle());
                } else {
                    LockscreenSmartspaceController.this.activityStarter.postStartActivityDismissingKeyguard(pendingIntent);
                }
            }
        });
        view.setFalsingManager(this.falsingManager);
        view.setKeyguardBypassEnabled(this.bypassController.getBypassEnabled());
        View view2 = (View) view;
        view2.setTag(R.id.tag_smartspace_view, new Object());
        view2.addOnAttachStateChangeListener(this.stateChangeListener);
        SmartspaceViewModel smartspaceViewModelCreate = this.smartspaceViewModelFactory.create(str);
        SmartspaceViewBinder.INSTANCE.getClass();
        SmartspaceViewBinder.bind(view, smartspaceViewModelCreate);
        return view2;
    }

    public final void connectSession() {
        SmartspaceManager smartspaceManager = this.userSmartspaceManager;
        UserTracker userTracker = this.userTracker;
        if (smartspaceManager == null) {
            this.userSmartspaceManager = (SmartspaceManager) ((UserTrackerImpl) userTracker).getUserContext().getSystemService(SmartspaceManager.class);
        }
        if (this.userSmartspaceManager == null) {
            return;
        }
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.plugin;
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin2 = this.weatherPlugin;
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin3 = this.datePlugin;
        if ((bcSmartspaceDataPlugin3 == null && bcSmartspaceDataPlugin2 == null && bcSmartspaceDataPlugin == null) || this.session != null || this.smartspaceViews.isEmpty()) {
            return;
        }
        DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = (DeviceProvisionedControllerImpl) this.deviceProvisionedController;
        if (deviceProvisionedControllerImpl.deviceProvisioned.get() && deviceProvisionedControllerImpl.isCurrentUserSetup()) {
            SmartspaceManager smartspaceManager2 = this.userSmartspaceManager;
            SmartspaceSession smartspaceSessionCreateSmartspaceSession = smartspaceManager2 != null ? smartspaceManager2.createSmartspaceSession(new SmartspaceConfig.Builder(((UserTrackerImpl) userTracker).getUserContext(), BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD).build()) : null;
            Log.d("LockscreenSmartspaceController", "Starting smartspace session for lockscreen");
            if (smartspaceSessionCreateSmartspaceSession != null) {
                smartspaceSessionCreateSmartspaceSession.addOnTargetsAvailableListener(this.uiExecutor, this.sessionListener);
            }
            this.session = smartspaceSessionCreateSmartspaceSession;
            deviceProvisionedControllerImpl.removeCallback(this.deviceProvisionedListener);
            ((UserTrackerImpl) userTracker).addCallback(this.userTrackerCallback, this.uiExecutor);
            ContentResolver contentResolver = this.contentResolver;
            SecureSettings secureSettings = this.secureSettings;
            Uri uriFor = secureSettings.getUriFor("lock_screen_allow_private_notifications");
            LockscreenSmartspaceController$settingsObserver$1 lockscreenSmartspaceController$settingsObserver$1 = this.settingsObserver;
            contentResolver.registerContentObserver(uriFor, true, lockscreenSmartspaceController$settingsObserver$1, -1);
            this.contentResolver.registerContentObserver(secureSettings.getUriFor(SettingsHelper.INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS), true, lockscreenSmartspaceController$settingsObserver$1, -1);
            ((ConfigurationControllerImpl) this.configurationController).addCallback(this.configChangeListener);
            this.statusBarStateController.addCallback(this.statusBarStateListener);
            LockscreenSmartspaceController$bypassStateChangedListener$1 lockscreenSmartspaceController$bypassStateChangedListener$1 = this.bypassStateChangedListener;
            KeyguardBypassController keyguardBypassController = this.bypassController;
            keyguardBypassController.registerOnBypassStateChangedListener(lockscreenSmartspaceController$bypassStateChangedListener$1);
            if (bcSmartspaceDataPlugin3 != null) {
                bcSmartspaceDataPlugin3.registerSmartspaceEventNotifier(new BcSmartspaceDataPlugin.SmartspaceEventNotifier() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController.connectSession.1
                    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceEventNotifier
                    public final void notifySmartspaceEvent(SmartspaceTargetEvent smartspaceTargetEvent) {
                        SmartspaceSession smartspaceSession = LockscreenSmartspaceController.this.session;
                        if (smartspaceSession != null) {
                            smartspaceSession.notifySmartspaceEvent(smartspaceTargetEvent);
                        }
                    }
                });
            }
            if (bcSmartspaceDataPlugin2 != null) {
                bcSmartspaceDataPlugin2.registerSmartspaceEventNotifier(new BcSmartspaceDataPlugin.SmartspaceEventNotifier() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController.connectSession.2
                    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceEventNotifier
                    public final void notifySmartspaceEvent(SmartspaceTargetEvent smartspaceTargetEvent) {
                        SmartspaceSession smartspaceSession = LockscreenSmartspaceController.this.session;
                        if (smartspaceSession != null) {
                            smartspaceSession.notifySmartspaceEvent(smartspaceTargetEvent);
                        }
                    }
                });
            }
            if (bcSmartspaceDataPlugin != null) {
                bcSmartspaceDataPlugin.registerSmartspaceEventNotifier(new BcSmartspaceDataPlugin.SmartspaceEventNotifier() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController.connectSession.3
                    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceEventNotifier
                    public final void notifySmartspaceEvent(SmartspaceTargetEvent smartspaceTargetEvent) {
                        SmartspaceSession smartspaceSession = LockscreenSmartspaceController.this.session;
                        if (smartspaceSession != null) {
                            smartspaceSession.notifySmartspaceEvent(smartspaceTargetEvent);
                        }
                    }
                });
            }
            boolean bypassEnabled = keyguardBypassController.getBypassEnabled();
            Iterator it = this.smartspaceViews.iterator();
            while (it.hasNext()) {
                ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setKeyguardBypassEnabled(bypassEnabled);
            }
            reloadSmartspace();
        }
    }

    public final void disconnect() {
        if (this.smartspaceViews.isEmpty() && !this.suppressDisconnects) {
            this.execution.assertIsMainThread();
            SmartspaceSession smartspaceSession = this.session;
            if (smartspaceSession == null) {
                return;
            }
            smartspaceSession.removeOnTargetsAvailableListener(this.sessionListener);
            smartspaceSession.close();
            ((UserTrackerImpl) this.userTracker).removeCallback(this.userTrackerCallback);
            this.contentResolver.unregisterContentObserver(this.settingsObserver);
            ((ConfigurationControllerImpl) this.configurationController).removeCallback(this.configChangeListener);
            this.statusBarStateController.removeCallback(this.statusBarStateListener);
            this.bypassController.unregisterOnBypassStateChangedListener(this.bypassStateChangedListener);
            this.session = null;
            BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.datePlugin;
            if (bcSmartspaceDataPlugin != null) {
                bcSmartspaceDataPlugin.registerSmartspaceEventNotifier(null);
            }
            BcSmartspaceDataPlugin bcSmartspaceDataPlugin2 = this.weatherPlugin;
            if (bcSmartspaceDataPlugin2 != null) {
                bcSmartspaceDataPlugin2.registerSmartspaceEventNotifier(null);
            }
            if (bcSmartspaceDataPlugin2 != null) {
                bcSmartspaceDataPlugin2.onTargetsAvailable(EmptyList.INSTANCE);
            }
            BcSmartspaceDataPlugin bcSmartspaceDataPlugin3 = this.plugin;
            if (bcSmartspaceDataPlugin3 != null) {
                bcSmartspaceDataPlugin3.registerSmartspaceEventNotifier(null);
            }
            if (bcSmartspaceDataPlugin3 != null) {
                bcSmartspaceDataPlugin3.onTargetsAvailable(EmptyList.INSTANCE);
            }
            Log.d("LockscreenSmartspaceController", "Ended smartspace session for lockscreen");
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(final PrintWriter printWriter, String[] strArr) {
        PrintWriter printWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        Collection collectionValues = ((LinkedHashMap) this.regionSamplers).values();
        printWriterAsIndenting.append("Region Samplers").append((CharSequence) ": ").println(collectionValues.size());
        printWriterAsIndenting.increaseIndent();
        try {
            Iterator it = collectionValues.iterator();
            while (it.hasNext()) {
                ((RegionSampler) it.next()).dump(printWriterAsIndenting);
            }
            printWriterAsIndenting.decreaseIndent();
            printWriter.println("Recent BC Smartspace Targets (most recent first)");
            synchronized (this.recentSmartspaceData) {
                if (((LinkedList) this.recentSmartspaceData).size() == 0) {
                    printWriter.println("   No data\n");
                    return;
                }
                Iterator itDescendingIterator = ((LinkedList) this.recentSmartspaceData).descendingIterator();
                final Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        PrintWriter printWriter2 = printWriter;
                        List list = (List) obj;
                        int i = LockscreenSmartspaceController.$r8$clinit;
                        printWriter2.println("   Number of targets: " + list.size());
                        Iterator it2 = list.iterator();
                        while (it2.hasNext()) {
                            printWriter2.println("      " + ((SmartspaceTarget) it2.next()));
                        }
                        printWriter2.println();
                        return Unit.INSTANCE;
                    }
                };
                itDescendingIterator.forEachRemaining(new Consumer() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$sam$java_util_function_Consumer$0
                    @Override // java.util.function.Consumer
                    public final /* synthetic */ void accept(Object obj) {
                        function1.mo781invoke(obj);
                    }
                });
                Unit unit = Unit.INSTANCE;
            }
        } catch (Throwable th) {
            printWriterAsIndenting.decreaseIndent();
            throw th;
        }
    }

    public final void reloadSmartspace() {
        UserHandle userHandle;
        UserTracker userTracker = this.userTracker;
        int userId = ((UserTrackerImpl) userTracker).getUserId();
        SecureSettings secureSettings = this.secureSettings;
        this.showNotifications = secureSettings.getIntForUser(SettingsHelper.INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS, 0, userId) == 1;
        this.showSensitiveContentForCurrentUser = secureSettings.getIntForUser("lock_screen_allow_private_notifications", 0, ((UserTrackerImpl) userTracker).getUserId()) == 1;
        Iterator it = ((UserTrackerImpl) userTracker).getUserProfiles().iterator();
        while (true) {
            if (!it.hasNext()) {
                userHandle = null;
                break;
            }
            UserInfo userInfo = (UserInfo) it.next();
            if (userInfo.isManagedProfile()) {
                userHandle = userInfo.getUserHandle();
                break;
            }
        }
        this.managedUserHandle = userHandle;
        Integer numValueOf = userHandle != null ? Integer.valueOf(userHandle.getIdentifier()) : null;
        if (numValueOf != null) {
            this.showSensitiveContentForManagedUser = secureSettings.getIntForUser("lock_screen_allow_private_notifications", 0, numValueOf.intValue()) == 1;
        }
        SmartspaceSession smartspaceSession = this.session;
        if (smartspaceSession != null) {
            smartspaceSession.requestSmartspaceUpdate();
        }
    }

    public static /* synthetic */ void getSmartspaceViews$annotations() {
    }
}
