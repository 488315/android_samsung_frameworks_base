package com.android.systemui.statusbar.lockscreen;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.app.WallpaperManager;
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
import android.os.Handler;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda5;
import com.android.settingslib.Utils;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.SysPropBooleanFlag;
import com.android.systemui.flags.SystemPropertiesHelper;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.BcSmartspaceConfigPlugin;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.WeatherData;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.regionsampling.RegionSampler;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.concurrency.ExecutionImpl;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SecureSettingsImpl;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
    public final FeatureFlags featureFlags;
    public boolean isRegionSamplersCreated;
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
    public final SystemClock systemClock;
    public final Executor uiExecutor;
    public final UserTracker userTracker;
    public final LockscreenSmartspaceController$userTrackerCallback$1 userTrackerCallback;
    public final BcSmartspaceDataPlugin weatherPlugin;
    public final Set smartspaceViews = new LinkedHashSet();
    public final Map regionSamplers = new LinkedHashMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v22, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$stateChangeListener$1] */
    /* JADX WARN: Type inference failed for: r2v23, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$sessionListener$1] */
    /* JADX WARN: Type inference failed for: r2v24, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$userTrackerCallback$1] */
    /* JADX WARN: Type inference failed for: r2v25, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$settingsObserver$1] */
    /* JADX WARN: Type inference failed for: r2v26, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$configChangeListener$1] */
    /* JADX WARN: Type inference failed for: r2v28, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$deviceProvisionedListener$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v11, types: [com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$bypassStateChangedListener$1] */
    public LockscreenSmartspaceController(Context context, FeatureFlags featureFlags, SmartspaceManager smartspaceManager, ActivityStarter activityStarter, FalsingManager falsingManager, SystemClock systemClock, SecureSettings secureSettings, UserTracker userTracker, ContentResolver contentResolver, ConfigurationController configurationController, StatusBarStateController statusBarStateController, DeviceProvisionedController deviceProvisionedController, KeyguardBypassController keyguardBypassController, KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager, Execution execution, Executor executor, Executor executor2, final Handler handler, Optional<BcSmartspaceDataPlugin> optional, Optional<BcSmartspaceDataPlugin> optional2, Optional<BcSmartspaceDataPlugin> optional3, Optional<BcSmartspaceConfigPlugin> optional4) {
        this.context = context;
        this.featureFlags = featureFlags;
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
                LockscreenSmartspaceController.access$updateTextColorFromWallpaper(LockscreenSmartspaceController.this);
                LockscreenSmartspaceController lockscreenSmartspaceController = LockscreenSmartspaceController.this;
                lockscreenSmartspaceController.statusBarStateListener.onDozeAmountChanged(0.0f, lockscreenSmartspaceController.statusBarStateController.getDozeAmount());
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
            /* JADX WARN: Code restructure failed: missing block: B:28:0x00a3, code lost:
            
                if (r5.getFeatureType() != 1) goto L52;
             */
            /* JADX WARN: Code restructure failed: missing block: B:36:0x00ea, code lost:
            
                r5 = true;
             */
            /* JADX WARN: Code restructure failed: missing block: B:40:0x00ae, code lost:
            
                if (r5.getFeatureType() == 1) goto L52;
             */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onTargetsAvailable(List list) {
                Object obj;
                boolean z;
                WeatherData fromBundle;
                ((ExecutionImpl) LockscreenSmartspaceController.this.execution).assertIsMainThread();
                BcSmartspaceDataPlugin bcSmartspaceDataPlugin = LockscreenSmartspaceController.this.weatherPlugin;
                if (bcSmartspaceDataPlugin != null) {
                    bcSmartspaceDataPlugin.onTargetsAvailable(list);
                }
                ((SystemClockImpl) LockscreenSmartspaceController.this.systemClock).getClass();
                Instant ofEpochMilli = Instant.ofEpochMilli(System.currentTimeMillis());
                Iterator it = list.iterator();
                while (true) {
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
                if (smartspaceTarget2 != null && (fromBundle = WeatherData.Companion.fromBundle(smartspaceTarget2.getBaseAction().getExtras())) != null) {
                    KeyguardUpdateMonitor keyguardUpdateMonitor2 = LockscreenSmartspaceController.this.keyguardUpdateMonitor;
                    keyguardUpdateMonitor2.mHandler.post(new KeyguardUpdateMonitor$$ExternalSyntheticLambda5(keyguardUpdateMonitor2, fromBundle, 0));
                }
                LockscreenSmartspaceController lockscreenSmartspaceController = LockscreenSmartspaceController.this;
                ArrayList arrayList = new ArrayList();
                for (Object obj2 : list) {
                    SmartspaceTarget smartspaceTarget3 = (SmartspaceTarget) obj2;
                    if (!lockscreenSmartspaceController.isDateWeatherDecoupled()) {
                        if (lockscreenSmartspaceController.showNotifications) {
                            UserHandle userHandle = smartspaceTarget3.getUserHandle();
                            UserTrackerImpl userTrackerImpl = (UserTrackerImpl) lockscreenSmartspaceController.userTracker;
                            z = !Intrinsics.areEqual(userHandle, userTrackerImpl.getUserHandle()) ? false : false;
                        }
                    }
                    if (z) {
                        arrayList.add(obj2);
                    }
                }
                BcSmartspaceDataPlugin bcSmartspaceDataPlugin2 = LockscreenSmartspaceController.this.plugin;
                if (bcSmartspaceDataPlugin2 != null) {
                    bcSmartspaceDataPlugin2.onTargetsAvailable(arrayList);
                }
                LockscreenSmartspaceController lockscreenSmartspaceController2 = LockscreenSmartspaceController.this;
                if (lockscreenSmartspaceController2.isRegionSamplersCreated) {
                    return;
                }
                for (BcSmartspaceDataPlugin.SmartspaceView smartspaceView : lockscreenSmartspaceController2.smartspaceViews) {
                    LockscreenSmartspaceController.this.getClass();
                }
                LockscreenSmartspaceController.this.isRegionSamplersCreated = true;
            }
        };
        this.userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$userTrackerCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                LockscreenSmartspaceController lockscreenSmartspaceController = LockscreenSmartspaceController.this;
                ((ExecutionImpl) lockscreenSmartspaceController.execution).assertIsMainThread();
                lockscreenSmartspaceController.reloadSmartspace();
            }
        };
        this.settingsObserver = new ContentObserver(handler) { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$settingsObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                ((ExecutionImpl) LockscreenSmartspaceController.this.execution).assertIsMainThread();
                LockscreenSmartspaceController.this.reloadSmartspace();
            }
        };
        this.configChangeListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$configChangeListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                LockscreenSmartspaceController lockscreenSmartspaceController = LockscreenSmartspaceController.this;
                ((ExecutionImpl) lockscreenSmartspaceController.execution).assertIsMainThread();
                LockscreenSmartspaceController.access$updateTextColorFromWallpaper(lockscreenSmartspaceController);
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
                LockscreenSmartspaceController.this.updateBypassEnabled();
            }
        };
        ((DeviceProvisionedControllerImpl) deviceProvisionedController).addCallback(r2);
        dumpManager.registerDumpable(this);
    }

    public static final void access$updateTextColorFromWallpaper(LockscreenSmartspaceController lockscreenSmartspaceController) {
        lockscreenSmartspaceController.getClass();
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(R.attr.wallpaperTextColor, lockscreenSmartspaceController.context, 0);
        Iterator it = lockscreenSmartspaceController.smartspaceViews.iterator();
        while (it.hasNext()) {
            ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setPrimaryTextColor(colorAttrDefaultColor);
        }
    }

    public final View buildAndConnectDateView(ViewGroup viewGroup) {
        ((ExecutionImpl) this.execution).assertIsMainThread();
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
        view.registerDataProvider(bcSmartspaceDataPlugin);
        view.setIntentStarter(new BcSmartspaceDataPlugin.IntentStarter() { // from class: com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController$buildView$2
            @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.IntentStarter
            public final void startIntent(View view2, Intent intent, boolean z) {
                LockscreenSmartspaceController lockscreenSmartspaceController = LockscreenSmartspaceController.this;
                if (z) {
                    lockscreenSmartspaceController.activityStarter.startActivity(intent, true, (ActivityLaunchAnimator.Controller) null, true);
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
        view2.addOnAttachStateChangeListener(this.stateChangeListener);
        return view2;
    }

    public final void connectSession() {
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.plugin;
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin2 = this.weatherPlugin;
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin3 = this.datePlugin;
        if ((bcSmartspaceDataPlugin3 == null && bcSmartspaceDataPlugin2 == null && bcSmartspaceDataPlugin == null) || this.session != null || this.smartspaceViews.isEmpty()) {
            return;
        }
        DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = (DeviceProvisionedControllerImpl) this.deviceProvisionedController;
        if (deviceProvisionedControllerImpl.isDeviceProvisioned() && deviceProvisionedControllerImpl.isCurrentUserSetup()) {
            SmartspaceSession createSmartspaceSession = this.smartspaceManager.createSmartspaceSession(new SmartspaceConfig.Builder(this.context, BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD).build());
            Log.d("LockscreenSmartspaceController", "Starting smartspace session for lockscreen");
            Executor executor = this.uiExecutor;
            createSmartspaceSession.addOnTargetsAvailableListener(executor, this.sessionListener);
            this.session = createSmartspaceSession;
            deviceProvisionedControllerImpl.removeCallback(this.deviceProvisionedListener);
            ((UserTrackerImpl) this.userTracker).addCallback(this.userTrackerCallback, executor);
            SecureSettingsImpl secureSettingsImpl = (SecureSettingsImpl) this.secureSettings;
            secureSettingsImpl.getClass();
            Uri uriFor = Settings.Secure.getUriFor("lock_screen_allow_private_notifications");
            ContentResolver contentResolver = this.contentResolver;
            LockscreenSmartspaceController$settingsObserver$1 lockscreenSmartspaceController$settingsObserver$1 = this.settingsObserver;
            contentResolver.registerContentObserver(uriFor, true, lockscreenSmartspaceController$settingsObserver$1, -1);
            secureSettingsImpl.getClass();
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("lock_screen_show_notifications"), true, lockscreenSmartspaceController$settingsObserver$1, -1);
            ((ConfigurationControllerImpl) this.configurationController).addCallback(this.configChangeListener);
            this.statusBarStateController.addCallback(this.statusBarStateListener);
            this.bypassController.registerOnBypassStateChangedListener(this.bypassStateChangedListener);
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
            updateBypassEnabled();
            reloadSmartspace();
        }
    }

    public final void disconnect() {
        if (this.smartspaceViews.isEmpty()) {
            ((ExecutionImpl) this.execution).assertIsMainThread();
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
            KeyguardBypassController keyguardBypassController = this.bypassController;
            ArrayList arrayList = (ArrayList) keyguardBypassController.listeners;
            arrayList.remove(this.bypassStateChangedListener);
            if (arrayList.isEmpty()) {
                ((KeyguardStateControllerImpl) keyguardBypassController.mKeyguardStateController).removeCallback(keyguardBypassController.faceAuthEnabledChangedCallback);
            }
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
    public final void dump(PrintWriter printWriter, String[] strArr) {
        Map map = this.regionSamplers;
        printWriter.println("Region Samplers: " + map.size());
        ArrayList arrayList = new ArrayList(map.size());
        Iterator it = ((LinkedHashMap) map).entrySet().iterator();
        while (it.hasNext()) {
            RegionSampler regionSampler = (RegionSampler) ((Map.Entry) it.next()).getValue();
            regionSampler.getClass();
            printWriter.println("[RegionSampler]");
            ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("regionSamplingEnabled: ", regionSampler.regionSamplingEnabled, printWriter);
            printWriter.println("regionDarkness: " + regionSampler.regionDarkness);
            FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("lightForegroundColor: ", Integer.toHexString(regionSampler.lightForegroundColor), printWriter);
            FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("darkForegroundColor: ", Integer.toHexString(regionSampler.darkForegroundColor), printWriter);
            printWriter.println("passed-in sampledView: " + regionSampler.sampledView);
            printWriter.println("calculated samplingBounds: " + regionSampler.samplingBounds);
            printWriter.println("sampledView width: " + regionSampler.sampledView.getWidth() + ", sampledView height: " + regionSampler.sampledView.getHeight());
            Point point = regionSampler.displaySize;
            printWriter.println("screen width: " + point.x + ", screen height: " + point.y);
            RectF calculateScreenLocation = regionSampler.calculateScreenLocation(regionSampler.sampledView);
            if (calculateScreenLocation == null) {
                calculateScreenLocation = new RectF();
            }
            printWriter.println("sampledRegionWithOffset: " + regionSampler.convertBounds(calculateScreenLocation));
            String str = regionSampler.isLockscreen ? BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD : "homescreen";
            printWriter.println("initialSampling for " + str + ": " + regionSampler.initialSampling);
            arrayList.add(Unit.INSTANCE);
        }
    }

    public final boolean isDateWeatherDecoupled() {
        ((ExecutionImpl) this.execution).assertIsMainThread();
        SysPropBooleanFlag sysPropBooleanFlag = Flags.SMARTSPACE_DATE_WEATHER_DECOUPLED;
        FeatureFlagsRelease featureFlagsRelease = (FeatureFlagsRelease) this.featureFlags;
        featureFlagsRelease.getClass();
        String str = sysPropBooleanFlag.name;
        SystemPropertiesHelper systemPropertiesHelper = featureFlagsRelease.mSystemProperties;
        boolean booleanValue = sysPropBooleanFlag.getDefault().booleanValue();
        systemPropertiesHelper.getClass();
        return (!featureFlagsRelease.isEnabledInternal(str, SystemProperties.getBoolean(str, booleanValue)) || this.datePlugin == null || this.weatherPlugin == null) ? false : true;
    }

    public final boolean isEnabled() {
        ((ExecutionImpl) this.execution).assertIsMainThread();
        return this.plugin != null;
    }

    public final void reloadSmartspace() {
        UserHandle userHandle;
        UserTracker userTracker = this.userTracker;
        int userId = ((UserTrackerImpl) userTracker).getUserId();
        SecureSettings secureSettings = this.secureSettings;
        this.showNotifications = secureSettings.getIntForUser(0, userId, "lock_screen_show_notifications") == 1;
        this.showSensitiveContentForCurrentUser = secureSettings.getIntForUser(0, ((UserTrackerImpl) userTracker).getUserId(), "lock_screen_allow_private_notifications") == 1;
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
            this.showSensitiveContentForManagedUser = secureSettings.getIntForUser(0, valueOf.intValue(), "lock_screen_allow_private_notifications") == 1;
        }
        SmartspaceSession smartspaceSession = this.session;
        if (smartspaceSession != null) {
            smartspaceSession.requestSmartspaceUpdate();
        }
    }

    public final void updateBypassEnabled() {
        boolean bypassEnabled = this.bypassController.getBypassEnabled();
        Iterator it = this.smartspaceViews.iterator();
        while (it.hasNext()) {
            ((BcSmartspaceDataPlugin.SmartspaceView) it.next()).setKeyguardBypassEnabled(bypassEnabled);
        }
    }
}
