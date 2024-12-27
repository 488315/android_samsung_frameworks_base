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
import android.graphics.Point;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.keyguard.KeyguardUpdateMonitor;
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
import com.android.systemui.smartspace.ui.viewmodel.SmartspaceViewModel;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
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

public final class LockscreenSmartspaceController implements Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public final Executor bgExecutor;
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
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public boolean mSplitShadeEnabled;
    public UserHandle managedUserHandle;
    public final BcSmartspaceDataPlugin plugin;
    public final SecureSettings secureSettings;
    public SmartspaceSession session;
    public final LockscreenSmartspaceController$sessionListener$1 sessionListener;
    public final LockscreenSmartspaceController$settingsObserver$1 settingsObserver;
    public boolean showNotifications;
    public boolean showSensitiveContentForCurrentUser;
    public boolean showSensitiveContentForManagedUser;
    public final SmartspaceManager smartspaceManager;
    public final LockscreenSmartspaceController$stateChangeListener$1 stateChangeListener;
    public final StatusBarStateController statusBarStateController;
    public final LockscreenSmartspaceController$statusBarStateListener$1 statusBarStateListener;
    public boolean suppressDisconnects;
    public final SystemClock systemClock;
    public final Executor uiExecutor;
    public final UserTracker userTracker;
    public final LockscreenSmartspaceController$userTrackerCallback$1 userTrackerCallback;
    public final WakefulnessLifecycle wakefulnessLifecycle;
    public final LockscreenSmartspaceController$wakefulnessLifecycleObserver$1 wakefulnessLifecycleObserver;
    public final BcSmartspaceDataPlugin weatherPlugin;
    public final Deque recentSmartspaceData = new LinkedList();
    public final Set smartspaceViews = new LinkedHashSet();
    public final Map regionSamplers = new LinkedHashMap();

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
    /* JADX WARN: Type inference failed for: r2v23, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$stateChangeListener$1] */
    /* JADX WARN: Type inference failed for: r2v24, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$sessionListener$1] */
    /* JADX WARN: Type inference failed for: r2v25, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$userTrackerCallback$1] */
    /* JADX WARN: Type inference failed for: r2v26, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$settingsObserver$1] */
    /* JADX WARN: Type inference failed for: r2v27, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$configChangeListener$1] */
    /* JADX WARN: Type inference failed for: r2v29, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$deviceProvisionedListener$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v11, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$bypassStateChangedListener$1] */
    /* JADX WARN: Type inference failed for: r3v12, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$wakefulnessLifecycleObserver$1] */
    public LockscreenSmartspaceController(Context context, FeatureFlags featureFlags, SmartspaceManager smartspaceManager, ActivityStarter activityStarter, FalsingManager falsingManager, SystemClock systemClock, SecureSettings secureSettings, UserTracker userTracker, ContentResolver contentResolver, ConfigurationController configurationController, StatusBarStateController statusBarStateController, DeviceProvisionedController deviceProvisionedController, KeyguardBypassController keyguardBypassController, KeyguardUpdateMonitor keyguardUpdateMonitor, WakefulnessLifecycle wakefulnessLifecycle, SmartspaceViewModel.Factory factory, DumpManager dumpManager, Execution execution, Executor executor, Executor executor2, final Handler handler, Optional<BcSmartspaceDataPlugin> optional, Optional<BcSmartspaceDataPlugin> optional2, Optional<BcSmartspaceDataPlugin> optional3, Optional<BcSmartspaceConfigPlugin> optional4) {
        this.context = context;
        this.smartspaceManager = smartspaceManager;
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
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.execution = execution;
        this.uiExecutor = executor;
        this.bgExecutor = executor2;
        this.datePlugin = optional.orElse(null);
        this.weatherPlugin = optional2.orElse(null);
        this.plugin = optional3.orElse(null);
        this.configPlugin = optional4.orElse(null);
        Flags.INSTANCE.getClass();
        this.stateChangeListener = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$stateChangeListener$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                BcSmartspaceDataPlugin.SmartspaceView smartspaceView = (BcSmartspaceDataPlugin.SmartspaceView) view;
                smartspaceView.setSplitShadeEnabled(LockscreenSmartspaceController.this.mSplitShadeEnabled);
                LockscreenSmartspaceController.this.smartspaceViews.add(smartspaceView);
                LockscreenSmartspaceController.this.connectSession();
                LockscreenSmartspaceController lockscreenSmartspaceController = LockscreenSmartspaceController.this;
                int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(lockscreenSmartspaceController.context, R.attr.wallpaperTextColor, 0);
                Iterator it = lockscreenSmartspaceController.smartspaceViews.iterator();
                while (it.hasNext()) {
                    ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setPrimaryTextColor(colorAttrDefaultColor);
                }
                LockscreenSmartspaceController lockscreenSmartspaceController2 = LockscreenSmartspaceController.this;
                lockscreenSmartspaceController2.statusBarStateListener.onDozeAmountChanged(0.0f, lockscreenSmartspaceController2.statusBarStateController.getDozeAmount());
                LockscreenSmartspaceController.this.getClass();
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                BcSmartspaceDataPlugin.SmartspaceView smartspaceView = (BcSmartspaceDataPlugin.SmartspaceView) view;
                LockscreenSmartspaceController.this.smartspaceViews.remove(smartspaceView);
                RegionSampler regionSampler = (RegionSampler) ((LinkedHashMap) LockscreenSmartspaceController.this.regionSamplers).get(view);
                if (regionSampler != null) {
                    WallpaperManager wallpaperManager = regionSampler.wallpaperManager;
                    if (wallpaperManager != null) {
                        wallpaperManager.removeOnColorsChangedListener(regionSampler);
                    }
                    regionSampler.sampledView.removeOnLayoutChangeListener(regionSampler.layoutChangedListener);
                }
                LockscreenSmartspaceController.this.regionSamplers.remove(smartspaceView);
                if (LockscreenSmartspaceController.this.smartspaceViews.isEmpty()) {
                    LockscreenSmartspaceController.this.disconnect();
                }
            }
        };
        this.sessionListener = new SmartspaceSession.OnTargetsAvailableListener() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$sessionListener$1
            public final void onTargetsAvailable(List list) {
                final WeatherData weatherData;
                Object obj;
                Bundle extras;
                LockscreenSmartspaceController.this.execution.assertIsMainThread();
                BcSmartspaceDataPlugin bcSmartspaceDataPlugin = LockscreenSmartspaceController.this.weatherPlugin;
                if (bcSmartspaceDataPlugin != null) {
                    bcSmartspaceDataPlugin.onTargetsAvailable(list);
                }
                Instant ofEpochMilli = Instant.ofEpochMilli(LockscreenSmartspaceController.this.systemClock.currentTimeMillis());
                List list2 = list;
                Iterator it = list2.iterator();
                while (true) {
                    weatherData = null;
                    if (!it.hasNext()) {
                        obj = null;
                        break;
                    }
                    obj = it.next();
                    SmartspaceTarget smartspaceTarget = (SmartspaceTarget) obj;
                    if (smartspaceTarget.getFeatureType() == 1 && ofEpochMilli.isAfter(Instant.ofEpochMilli(smartspaceTarget.getCreationTimeMillis())) && ofEpochMilli.isBefore(Instant.ofEpochMilli(smartspaceTarget.getExpiryTimeMillis()))) {
                        break;
                    }
                }
                SmartspaceTarget smartspaceTarget2 = (SmartspaceTarget) obj;
                if (smartspaceTarget2 != null) {
                    SmartspaceAction headerAction = smartspaceTarget2.getHeaderAction();
                    final Intent intent = headerAction != null ? headerAction.getIntent() : null;
                    SmartspaceAction baseAction = smartspaceTarget2.getBaseAction();
                    if (baseAction != null && (extras = baseAction.getExtras()) != null) {
                        final LockscreenSmartspaceController lockscreenSmartspaceController = LockscreenSmartspaceController.this;
                        weatherData = WeatherData.Companion.fromBundle(extras, new Function1() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$sessionListener$1$weatherData$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj2) {
                                if (!LockscreenSmartspaceController.this.falsingManager.isFalseTap(1)) {
                                    LockscreenSmartspaceController.this.activityStarter.startActivity(intent, true, (ActivityTransitionAnimator.Controller) null, false);
                                }
                                return Unit.INSTANCE;
                            }
                        });
                    }
                    if (weatherData != null) {
                        final KeyguardUpdateMonitor keyguardUpdateMonitor2 = LockscreenSmartspaceController.this.keyguardUpdateMonitor;
                        keyguardUpdateMonitor2.mHandler.post(new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda8
                            @Override // java.lang.Runnable
                            public final void run() {
                                KeyguardUpdateMonitor keyguardUpdateMonitor3 = KeyguardUpdateMonitor.this;
                                WeatherData weatherData2 = weatherData;
                                keyguardUpdateMonitor3.getClass();
                                Assert.isMainThread();
                                for (int i = 0; i < keyguardUpdateMonitor3.mCallbacks.size(); i++) {
                                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor3.mCallbacks.get(i)).get();
                                    if (keyguardUpdateMonitorCallback != null) {
                                        keyguardUpdateMonitorCallback.onWeatherDataChanged(weatherData2);
                                    }
                                }
                            }
                        });
                    }
                }
                LockscreenSmartspaceController lockscreenSmartspaceController2 = LockscreenSmartspaceController.this;
                ArrayList arrayList = new ArrayList();
                for (Object obj2 : list2) {
                    SmartspaceTarget smartspaceTarget3 = (SmartspaceTarget) obj2;
                    if (!lockscreenSmartspaceController2.isDateWeatherDecoupled() || smartspaceTarget3.getFeatureType() != 1) {
                        if (lockscreenSmartspaceController2.showNotifications) {
                            UserHandle userHandle = smartspaceTarget3.getUserHandle();
                            UserTrackerImpl userTrackerImpl = (UserTrackerImpl) lockscreenSmartspaceController2.userTracker;
                            if (Intrinsics.areEqual(userHandle, userTrackerImpl.getUserHandle())) {
                                if (smartspaceTarget3.isSensitive() && !lockscreenSmartspaceController2.showSensitiveContentForCurrentUser) {
                                }
                                arrayList.add(obj2);
                            } else if (Intrinsics.areEqual(userHandle, lockscreenSmartspaceController2.managedUserHandle)) {
                                if (userTrackerImpl.getUserHandle().getIdentifier() == 0) {
                                    if (smartspaceTarget3.isSensitive() && !lockscreenSmartspaceController2.showSensitiveContentForManagedUser) {
                                    }
                                    arrayList.add(obj2);
                                }
                            }
                        } else if (smartspaceTarget3.getFeatureType() == 1) {
                            arrayList.add(obj2);
                        }
                    }
                }
                LockscreenSmartspaceController lockscreenSmartspaceController3 = LockscreenSmartspaceController.this;
                synchronized (lockscreenSmartspaceController3.recentSmartspaceData) {
                    ((LinkedList) lockscreenSmartspaceController3.recentSmartspaceData).offerLast(arrayList);
                    if (((LinkedList) lockscreenSmartspaceController3.recentSmartspaceData).size() > 5) {
                        ((LinkedList) lockscreenSmartspaceController3.recentSmartspaceData).pollFirst();
                    }
                    Unit unit = Unit.INSTANCE;
                }
                BcSmartspaceDataPlugin bcSmartspaceDataPlugin2 = LockscreenSmartspaceController.this.plugin;
                if (bcSmartspaceDataPlugin2 != null) {
                    bcSmartspaceDataPlugin2.onTargetsAvailable(arrayList);
                }
            }
        };
        this.userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$userTrackerCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                LockscreenSmartspaceController lockscreenSmartspaceController = LockscreenSmartspaceController.this;
                lockscreenSmartspaceController.execution.assertIsMainThread();
                lockscreenSmartspaceController.reloadSmartspace();
            }
        };
        this.settingsObserver = new ContentObserver(handler) { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$settingsObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                LockscreenSmartspaceController.this.execution.assertIsMainThread();
                LockscreenSmartspaceController.this.reloadSmartspace();
            }
        };
        this.configChangeListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$configChangeListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                LockscreenSmartspaceController lockscreenSmartspaceController = LockscreenSmartspaceController.this;
                lockscreenSmartspaceController.execution.assertIsMainThread();
                int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(lockscreenSmartspaceController.context, R.attr.wallpaperTextColor, 0);
                Iterator it = lockscreenSmartspaceController.smartspaceViews.iterator();
                while (it.hasNext()) {
                    ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setPrimaryTextColor(colorAttrDefaultColor);
                }
            }
        };
        this.statusBarStateListener = new LockscreenSmartspaceController$statusBarStateListener$1(this);
        ?? r2 = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$deviceProvisionedListener$1
            @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
            public final void onDeviceProvisionedChanged() {
                int i = LockscreenSmartspaceController.$r8$clinit;
                LockscreenSmartspaceController.this.connectSession();
            }

            @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
            public final void onUserSetupChanged() {
                int i = LockscreenSmartspaceController.$r8$clinit;
                LockscreenSmartspaceController.this.connectSession();
            }
        };
        this.deviceProvisionedListener = r2;
        this.bypassStateChangedListener = new KeyguardBypassController.OnBypassStateChangedListener() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$bypassStateChangedListener$1
            @Override // com.android.systemui.statusbar.phone.KeyguardBypassController.OnBypassStateChangedListener
            public final void onBypassStateChanged(boolean z) {
                int i = LockscreenSmartspaceController.$r8$clinit;
                LockscreenSmartspaceController lockscreenSmartspaceController = LockscreenSmartspaceController.this;
                boolean bypassEnabled = lockscreenSmartspaceController.bypassController.getBypassEnabled();
                Iterator it = lockscreenSmartspaceController.smartspaceViews.iterator();
                while (it.hasNext()) {
                    ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setKeyguardBypassEnabled(bypassEnabled);
                }
            }
        };
        this.wakefulnessLifecycleObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$wakefulnessLifecycleObserver$1
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedGoingToSleep() {
                Iterator it = LockscreenSmartspaceController.this.smartspaceViews.iterator();
                while (it.hasNext()) {
                    ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setScreenOn(false);
                }
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                Iterator it = LockscreenSmartspaceController.this.smartspaceViews.iterator();
                while (it.hasNext()) {
                    ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setScreenOn(true);
                }
            }
        };
        ((DeviceProvisionedControllerImpl) deviceProvisionedController).addCallback(r2);
        dumpManager.registerDumpable(this);
    }

    public final View buildAndConnectDateView(ViewGroup viewGroup) {
        this.execution.assertIsMainThread();
        if (!isEnabled()) {
            throw new RuntimeException("Cannot build view when not enabled");
        }
        if (!isDateWeatherDecoupled()) {
            throw new RuntimeException("Cannot build date view when not decoupled");
        }
        View buildView = buildView(viewGroup, this.datePlugin, null);
        connectSession();
        return buildView;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final View buildView(ViewGroup viewGroup, BcSmartspaceDataPlugin bcSmartspaceDataPlugin, BcSmartspaceConfigPlugin bcSmartspaceConfigPlugin) {
        if (bcSmartspaceDataPlugin == null) {
            return null;
        }
        BcSmartspaceDataPlugin.SmartspaceView view = bcSmartspaceDataPlugin.getView(viewGroup);
        if (bcSmartspaceConfigPlugin != null) {
            view.registerConfigProvider(bcSmartspaceConfigPlugin);
        }
        view.setUiSurface(BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD);
        view.setTimeChangedDelegate(new SmartspaceTimeChangedDelegate(this.keyguardUpdateMonitor));
        view.registerDataProvider(bcSmartspaceDataPlugin);
        view.setIntentStarter(new BcSmartspaceDataPlugin.IntentStarter() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$buildView$2
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
            public final void startPendingIntent(View view2, PendingIntent pendingIntent, boolean z) {
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
        com.android.systemui.Flags.FEATURE_FLAGS.getClass();
        return view2;
    }

    public final void connectSession() {
        if (this.smartspaceManager == null) {
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
            SmartspaceSession createSmartspaceSession = this.smartspaceManager.createSmartspaceSession(new SmartspaceConfig.Builder(this.context, BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD).build());
            Log.d("LockscreenSmartspaceController", "Starting smartspace session for lockscreen");
            createSmartspaceSession.addOnTargetsAvailableListener(this.uiExecutor, this.sessionListener);
            this.session = createSmartspaceSession;
            deviceProvisionedControllerImpl.removeCallback(this.deviceProvisionedListener);
            ((UserTrackerImpl) this.userTracker).addCallback(this.userTrackerCallback, this.uiExecutor);
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
            com.android.systemui.Flags.FEATURE_FLAGS.getClass();
            this.wakefulnessLifecycle.addObserver(this.wakefulnessLifecycleObserver);
            if (bcSmartspaceDataPlugin3 != null) {
                bcSmartspaceDataPlugin3.registerSmartspaceEventNotifier(new BcSmartspaceDataPlugin.SmartspaceEventNotifier() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$connectSession$1
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
                bcSmartspaceDataPlugin2.registerSmartspaceEventNotifier(new BcSmartspaceDataPlugin.SmartspaceEventNotifier() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$connectSession$2
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
                bcSmartspaceDataPlugin.registerSmartspaceEventNotifier(new BcSmartspaceDataPlugin.SmartspaceEventNotifier() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$connectSession$3
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
            com.android.systemui.Flags.FEATURE_FLAGS.getClass();
            this.wakefulnessLifecycle.removeObserver(this.wakefulnessLifecycleObserver);
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
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        Collection<RegionSampler> values = ((LinkedHashMap) this.regionSamplers).values();
        asIndenting.append("Region Samplers").append((CharSequence) ": ").println(values.size());
        asIndenting.increaseIndent();
        try {
            for (RegionSampler regionSampler : values) {
                regionSampler.getClass();
                asIndenting.println("[RegionSampler]");
                asIndenting.println("regionSamplingEnabled: " + regionSampler.regionSamplingEnabled);
                asIndenting.println("regionDarkness: " + regionSampler.regionDarkness);
                asIndenting.println("lightForegroundColor: " + Integer.toHexString(regionSampler.lightForegroundColor));
                asIndenting.println("darkForegroundColor: " + Integer.toHexString(regionSampler.darkForegroundColor));
                asIndenting.println("passed-in sampledView: " + regionSampler.sampledView);
                asIndenting.println("calculated samplingBounds: " + regionSampler.samplingBounds);
                asIndenting.println("sampledView width: " + regionSampler.sampledView.getWidth() + ", sampledView height: " + regionSampler.sampledView.getHeight());
                Point point = regionSampler.displaySize;
                asIndenting.println("screen width: " + point.x + ", screen height: " + point.y);
                RectF convertBounds = regionSampler.convertBounds(regionSampler.calculateScreenLocation(regionSampler.sampledView));
                StringBuilder sb = new StringBuilder("sampledRegionWithOffset: ");
                sb.append(convertBounds);
                asIndenting.println(sb.toString());
                String str = regionSampler.isLockscreen ? BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD : "homescreen";
                asIndenting.println("initialSampling for " + str + ": " + regionSampler.initialSampling);
            }
            asIndenting.decreaseIndent();
            printWriter.println("Recent BC Smartspace Targets (most recent first)");
            synchronized (this.recentSmartspaceData) {
                if (((LinkedList) this.recentSmartspaceData).size() == 0) {
                    printWriter.println("   No data\n");
                } else {
                    ((LinkedList) this.recentSmartspaceData).descendingIterator().forEachRemaining(new Consumer() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$dump$2$1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            List<SmartspaceTarget> list = (List) obj;
                            printWriter.println("   Number of targets: " + list.size());
                            for (SmartspaceTarget smartspaceTarget : list) {
                                printWriter.println("      " + smartspaceTarget);
                            }
                            printWriter.println();
                        }
                    });
                    Unit unit = Unit.INSTANCE;
                }
            }
        } catch (Throwable th) {
            asIndenting.decreaseIndent();
            throw th;
        }
    }

    public final boolean isDateWeatherDecoupled() {
        this.execution.assertIsMainThread();
        return (this.datePlugin == null || this.weatherPlugin == null) ? false : true;
    }

    public final boolean isEnabled() {
        this.execution.assertIsMainThread();
        return this.plugin != null;
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
        Integer valueOf = userHandle != null ? Integer.valueOf(userHandle.getIdentifier()) : null;
        if (valueOf != null) {
            this.showSensitiveContentForManagedUser = secureSettings.getIntForUser("lock_screen_allow_private_notifications", 0, valueOf.intValue()) == 1;
        }
        SmartspaceSession smartspaceSession = this.session;
        if (smartspaceSession != null) {
            smartspaceSession.requestSmartspaceUpdate();
        }
    }

    public static /* synthetic */ void getSmartspaceViews$annotations() {
    }
}
