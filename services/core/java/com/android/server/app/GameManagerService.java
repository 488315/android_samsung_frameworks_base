package com.android.server.app;

import android.app.ActivityManager;
import android.app.GameManagerInternal;
import android.app.GameModeConfiguration;
import android.app.GameModeInfo;
import android.app.GameState;
import android.app.IGameManagerService;
import android.app.IGameModeListener;
import android.app.IGameStateListener;
import android.app.StatsManager;
import android.app.UidObserver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PermissionEnforcer;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.server.app.Flags;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.KeyValueListParser;
import android.util.Slog;

import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.CompatModePackages;
import com.android.server.wm.CompatScaleProvider;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerService;

import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;

public final class GameManagerService extends IGameManagerService.Stub {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayMap mConfigs;
    public final Context mContext;
    public DeviceConfigListener mDeviceConfigListener;
    public final Object mDeviceConfigLock;
    public float mGameDefaultFrameRateValue;
    public final Set mGameForegroundUids;
    final AtomicFile mGameModeInterventionListFile;
    public final Object mGameModeListenerLock;
    public final ArrayMap mGameModeListeners;
    public final GameServiceController mGameServiceController;
    public final Object mGameStateListenerLock;
    public final ArrayMap mGameStateListeners;
    final Handler mHandler;
    public final Object mLock;
    public final Set mNonGameForegroundUids;
    public final PackageManager mPackageManager;
    public final PowerManagerInternal mPowerManagerInternal;
    public final ArrayMap mSettings;
    public final Injector.AnonymousClass1 mSysProps;
    final MyUidObserver mUidObserver;
    public final Object mUidObserverLock;
    public final UserManager mUserManager;

    /* renamed from: com.android.server.app.GameManagerService$1, reason: invalid class name */
    public final class AnonymousClass1 implements IBinder.DeathRecipient {
        public final /* synthetic */ int $r8$classId = 0;
        public final /* synthetic */ Object val$listener;
        public final /* synthetic */ IBinder val$listenerBinder;

        public AnonymousClass1(IGameModeListener iGameModeListener, IBinder iBinder) {
            this.val$listener = iGameModeListener;
            this.val$listenerBinder = iBinder;
        }

        public AnonymousClass1(IGameStateListener iGameStateListener, IBinder iBinder) {
            this.val$listener = iGameStateListener;
            this.val$listenerBinder = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            switch (this.$r8$classId) {
                case 0:
                    GameManagerService gameManagerService = GameManagerService.this;
                    IGameModeListener iGameModeListener = (IGameModeListener) this.val$listener;
                    int i = GameManagerService.$r8$clinit;
                    synchronized (gameManagerService.mGameModeListenerLock) {
                        gameManagerService.mGameModeListeners.remove(iGameModeListener);
                    }
                    this.val$listenerBinder.unlinkToDeath(this, 0);
                    return;
                default:
                    GameManagerService gameManagerService2 = GameManagerService.this;
                    IGameStateListener iGameStateListener = (IGameStateListener) this.val$listener;
                    int i2 = GameManagerService.$r8$clinit;
                    synchronized (gameManagerService2.mGameStateListenerLock) {
                        gameManagerService2.mGameStateListeners.remove(iGameStateListener);
                    }
                    this.val$listenerBinder.unlinkToDeath(this, 0);
                    return;
            }
        }
    }

    /* renamed from: com.android.server.app.GameManagerService$3, reason: invalid class name */
    public final class AnonymousClass3 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ GameManagerService this$0;

        public /* synthetic */ AnonymousClass3(GameManagerService gameManagerService, int i) {
            this.$r8$classId = i;
            this.this$0 = gameManagerService;
        }

        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceive(android.content.Context r7, android.content.Intent r8) {
            /*
                Method dump skipped, instructions count: 280
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.app.GameManagerService.AnonymousClass3.onReceive(android.content.Context,"
                        + " android.content.Intent):void");
        }
    }

    public final class DeviceConfigListener implements DeviceConfig.OnPropertiesChangedListener {
        public DeviceConfigListener() {
            DeviceConfig.addOnPropertiesChangedListener(
                    "game_overlay", GameManagerService.this.mContext.getMainExecutor(), this);
        }

        public final void finalize() {
            DeviceConfig.removeOnPropertiesChangedListener(this);
        }

        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            String[] strArr = (String[]) properties.getKeyset().toArray(new String[0]);
            Slog.v(
                    "GameManagerService",
                    "Device config changed for packages: " + Arrays.toString(strArr));
            GameManagerService.this.updateConfigsForUser(
                    ActivityManager.getCurrentUser(), true, strArr);
        }
    }

    public final class GamePackageConfiguration {
        public boolean mAllowAngle;
        public boolean mAllowDownscale;
        public boolean mAllowFpsOverride;
        public boolean mBatteryModeOverridden;
        public final Object mModeConfigLock;
        public final ArrayMap mModeConfigs;
        public final String mPackageName;
        public boolean mPerfModeOverridden;

        public final class GameModeConfiguration {
            public String mFps;
            public final int mGameMode;
            public int mLoadingBoostDuration;
            public float mScaling;
            public boolean mUseAngle;

            public GameModeConfiguration(int i) {
                this.mScaling = -1.0f;
                this.mFps = "";
                this.mGameMode = i;
                this.mUseAngle = false;
                this.mLoadingBoostDuration = -1;
            }

            public GameModeConfiguration(KeyValueListParser keyValueListParser) {
                float f = -1.0f;
                this.mScaling = -1.0f;
                String str = "";
                this.mFps = "";
                boolean z = false;
                int i = keyValueListParser.getInt("mode", 0);
                this.mGameMode = i;
                if (GamePackageConfiguration.this.mAllowDownscale
                        && !GamePackageConfiguration.this.willGamePerformOptimizations(i)) {
                    f = keyValueListParser.getFloat("downscaleFactor", -1.0f);
                }
                this.mScaling = f;
                if (GamePackageConfiguration.this.mAllowFpsOverride
                        && !GamePackageConfiguration.this.willGamePerformOptimizations(i)) {
                    str = keyValueListParser.getString("fps", "");
                }
                this.mFps = str;
                if (GamePackageConfiguration.this.mAllowAngle
                        && !GamePackageConfiguration.this.willGamePerformOptimizations(i)
                        && keyValueListParser.getBoolean("useAngle", false)) {
                    z = true;
                }
                this.mUseAngle = z;
                this.mLoadingBoostDuration =
                        GamePackageConfiguration.this.willGamePerformOptimizations(i)
                                ? -1
                                : keyValueListParser.getInt("loadingBoost", -1);
            }

            public final synchronized int getFps() {
                try {
                } catch (NumberFormatException unused) {
                    return 0;
                }
                return Integer.parseInt(this.mFps);
            }

            public final synchronized float getScaling() {
                return this.mScaling;
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("[Game Mode:");
                sb.append(this.mGameMode);
                sb.append(",Scaling:");
                sb.append(this.mScaling);
                sb.append(",Use Angle:");
                sb.append(this.mUseAngle);
                sb.append(",Fps:");
                sb.append(this.mFps);
                sb.append(",Loading Boost Duration:");
                return AmFmBandRange$$ExternalSyntheticOutline0.m(
                        this.mLoadingBoostDuration, sb, "]");
            }
        }

        public GamePackageConfiguration(PackageManager packageManager, String str, int i) {
            Bundle bundle;
            this.mModeConfigLock = new Object();
            this.mModeConfigs = new ArrayMap();
            this.mPerfModeOverridden = false;
            this.mBatteryModeOverridden = false;
            this.mAllowDownscale = true;
            this.mAllowAngle = true;
            this.mAllowFpsOverride = true;
            this.mPackageName = str;
            try {
                ApplicationInfo applicationInfoAsUser =
                        packageManager.getApplicationInfoAsUser(str, 128, i);
                if (!parseInterventionFromXml(applicationInfoAsUser, packageManager, str)
                        && (bundle = applicationInfoAsUser.metaData) != null) {
                    this.mPerfModeOverridden =
                            bundle.getBoolean("com.android.app.gamemode.performance.enabled");
                    this.mBatteryModeOverridden =
                            applicationInfoAsUser.metaData.getBoolean(
                                    "com.android.app.gamemode.battery.enabled");
                    this.mAllowDownscale =
                            applicationInfoAsUser.metaData.getBoolean(
                                    "com.android.graphics.intervention.wm.allowDownscale", true);
                    this.mAllowAngle =
                            applicationInfoAsUser.metaData.getBoolean(
                                    "com.android.graphics.intervention.angle.allowAngle", true);
                }
            } catch (PackageManager.NameNotFoundException unused) {
                Slog.v(
                        "GameManagerService_GamePackageConfiguration",
                        "Failed to get package metadata");
            }
            String property = DeviceConfig.getProperty("game_overlay", str);
            if (property != null) {
                for (String str2 : property.split(":")) {
                    try {
                        KeyValueListParser keyValueListParser = new KeyValueListParser(',');
                        keyValueListParser.setString(str2);
                        addModeConfig(new GameModeConfiguration(keyValueListParser));
                    } catch (IllegalArgumentException unused2) {
                        Slog.e(
                                "GameManagerService_GamePackageConfiguration",
                                "Invalid config string");
                    }
                }
            }
        }

        public GamePackageConfiguration(String str) {
            this.mModeConfigLock = new Object();
            this.mModeConfigs = new ArrayMap();
            this.mPerfModeOverridden = false;
            this.mBatteryModeOverridden = false;
            this.mAllowDownscale = true;
            this.mAllowAngle = true;
            this.mAllowFpsOverride = true;
            this.mPackageName = str;
        }

        public final void addModeConfig(GameModeConfiguration gameModeConfiguration) {
            int i = gameModeConfiguration.mGameMode;
            if ((i == 1 || i == 2 || i == 3 || i == 4)
                    && !GamePackageConfiguration.this.willGamePerformOptimizations(i)) {
                synchronized (this.mModeConfigLock) {
                    this.mModeConfigs.put(
                            Integer.valueOf(gameModeConfiguration.mGameMode),
                            gameModeConfiguration);
                }
            } else {
                Slog.w(
                        "GameManagerService_GamePackageConfiguration",
                        "Attempt to add inactive game mode config for "
                                + this.mPackageName
                                + ":"
                                + gameModeConfiguration.toString());
            }
        }

        public final GamePackageConfiguration copyAndApplyOverride(
                GamePackageConfiguration gamePackageConfiguration) {
            GamePackageConfiguration gamePackageConfiguration2 =
                    new GamePackageConfiguration(this.mPackageName);
            boolean z = false;
            gamePackageConfiguration2.mPerfModeOverridden =
                    this.mPerfModeOverridden
                            && gamePackageConfiguration.getGameModeConfiguration(2) == null;
            if (this.mBatteryModeOverridden
                    && gamePackageConfiguration.getGameModeConfiguration(3) == null) {
                z = true;
            }
            gamePackageConfiguration2.mBatteryModeOverridden = z;
            gamePackageConfiguration2.mAllowDownscale = true;
            gamePackageConfiguration2.mAllowAngle = true;
            gamePackageConfiguration2.mAllowFpsOverride = true;
            synchronized (gamePackageConfiguration2.mModeConfigLock) {
                synchronized (this.mModeConfigLock) {
                    try {
                        for (Map.Entry entry : this.mModeConfigs.entrySet()) {
                            gamePackageConfiguration2.mModeConfigs.put(
                                    (Integer) entry.getKey(),
                                    (GameModeConfiguration) entry.getValue());
                        }
                    } finally {
                    }
                }
                synchronized (gamePackageConfiguration.mModeConfigLock) {
                    try {
                        for (Map.Entry entry2 : gamePackageConfiguration.mModeConfigs.entrySet()) {
                            gamePackageConfiguration2.mModeConfigs.put(
                                    (Integer) entry2.getKey(),
                                    (GameModeConfiguration) entry2.getValue());
                        }
                    } finally {
                    }
                }
            }
            return gamePackageConfiguration2;
        }

        public final int[] getAvailableGameModes() {
            int availableGameModesBitfield = getAvailableGameModesBitfield();
            int[] iArr = new int[Integer.bitCount(availableGameModesBitfield)];
            int numberOfTrailingZeros =
                    Integer.numberOfTrailingZeros(
                            Integer.highestOneBit(availableGameModesBitfield));
            int i = 0;
            for (int i2 = 0; i2 <= numberOfTrailingZeros; i2++) {
                if (((availableGameModesBitfield >> i2) & 1) != 0) {
                    iArr[i] = i2;
                    i++;
                }
            }
            return iArr;
        }

        public final int getAvailableGameModesBitfield() {
            int i;
            int i2 = GameManagerService.$r8$clinit;
            synchronized (this.mModeConfigLock) {
                try {
                    Iterator it = this.mModeConfigs.keySet().iterator();
                    i = 18;
                    while (it.hasNext()) {
                        i |= 1 << ((Integer) it.next()).intValue();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (this.mBatteryModeOverridden) {
                i |= 8;
            }
            return this.mPerfModeOverridden ? i | 4 : i;
        }

        public final GameModeConfiguration getGameModeConfiguration(int i) {
            GameModeConfiguration gameModeConfiguration;
            synchronized (this.mModeConfigLock) {
                gameModeConfiguration =
                        (GameModeConfiguration) this.mModeConfigs.get(Integer.valueOf(i));
            }
            return gameModeConfiguration;
        }

        public final GameModeConfiguration getOrAddDefaultGameModeConfiguration(int i) {
            GameModeConfiguration gameModeConfiguration;
            synchronized (this.mModeConfigLock) {
                this.mModeConfigs.putIfAbsent(Integer.valueOf(i), new GameModeConfiguration(i));
                gameModeConfiguration =
                        (GameModeConfiguration) this.mModeConfigs.get(Integer.valueOf(i));
            }
            return gameModeConfiguration;
        }

        public final int[] getOverriddenGameModes() {
            boolean z = this.mBatteryModeOverridden;
            return (z && this.mPerfModeOverridden)
                    ? new int[] {3, 2}
                    : z ? new int[] {3} : this.mPerfModeOverridden ? new int[] {2} : new int[0];
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean parseInterventionFromXml(
                android.content.pm.ApplicationInfo r7,
                android.content.pm.PackageManager r8,
                java.lang.String r9) {
            /*
                r6 = this;
                java.lang.String r0 = "GameManagerService_GamePackageConfiguration"
                java.lang.String r1 = "No android.game_mode_config meta-data found for package "
                r2 = 1
                r3 = 0
                java.lang.String r4 = "android.game_mode_config"
                android.content.res.XmlResourceParser r7 = r7.loadXmlMetaData(r8, r4)     // Catch: java.lang.Throwable -> L8a
                if (r7 != 0) goto L24
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L21
                r8.<init>(r1)     // Catch: java.lang.Throwable -> L21
                java.lang.String r9 = r6.mPackageName     // Catch: java.lang.Throwable -> L21
                r8.append(r9)     // Catch: java.lang.Throwable -> L21
                java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> L21
                android.util.Slog.v(r0, r8)     // Catch: java.lang.Throwable -> L21
                r8 = r3
                goto L76
            L21:
                r8 = move-exception
                r9 = r3
                goto L7c
            L24:
                android.content.res.Resources r8 = r8.getResourcesForApplication(r9)     // Catch: java.lang.Throwable -> L49
                android.util.AttributeSet r9 = android.util.Xml.asAttributeSet(r7)     // Catch: java.lang.Throwable -> L49
            L2c:
                int r1 = r7.next()     // Catch: java.lang.Throwable -> L49
                r4 = 2
                if (r1 == r2) goto L36
                if (r1 == r4) goto L36
                goto L2c
            L36:
                java.lang.String r1 = "game-mode-config"
                java.lang.String r5 = r7.getName()     // Catch: java.lang.Throwable -> L49
                boolean r1 = r1.equals(r5)     // Catch: java.lang.Throwable -> L49
                if (r1 != 0) goto L4c
                java.lang.String r8 = "Meta-data does not start with game-mode-config tag"
                android.util.Slog.w(r0, r8)     // Catch: java.lang.Throwable -> L49
                goto L75
            L49:
                r8 = move-exception
                r9 = r2
                goto L7c
            L4c:
                int[] r1 = com.android.internal.R.styleable.GameModeConfig     // Catch: java.lang.Throwable -> L49
                android.content.res.TypedArray r8 = r8.obtainAttributes(r9, r1)     // Catch: java.lang.Throwable -> L49
                boolean r9 = r8.getBoolean(r2, r3)     // Catch: java.lang.Throwable -> L49
                r6.mPerfModeOverridden = r9     // Catch: java.lang.Throwable -> L49
                boolean r9 = r8.getBoolean(r3, r3)     // Catch: java.lang.Throwable -> L49
                r6.mBatteryModeOverridden = r9     // Catch: java.lang.Throwable -> L49
                r9 = 3
                boolean r9 = r8.getBoolean(r9, r2)     // Catch: java.lang.Throwable -> L49
                r6.mAllowDownscale = r9     // Catch: java.lang.Throwable -> L49
                boolean r9 = r8.getBoolean(r4, r2)     // Catch: java.lang.Throwable -> L49
                r6.mAllowAngle = r9     // Catch: java.lang.Throwable -> L49
                r9 = 4
                boolean r9 = r8.getBoolean(r9, r2)     // Catch: java.lang.Throwable -> L49
                r6.mAllowFpsOverride = r9     // Catch: java.lang.Throwable -> L49
                r8.recycle()     // Catch: java.lang.Throwable -> L49
            L75:
                r8 = r2
            L76:
                if (r7 == 0) goto L9a
                r7.close()     // Catch: java.lang.Throwable -> L8b
                goto L9a
            L7c:
                if (r7 == 0) goto L89
                r7.close()     // Catch: java.lang.Throwable -> L82
                goto L89
            L82:
                r7 = move-exception
                r8.addSuppressed(r7)     // Catch: java.lang.Throwable -> L87
                goto L89
            L87:
                r8 = r9
                goto L8b
            L89:
                throw r8     // Catch: java.lang.Throwable -> L87
            L8a:
                r8 = r3
            L8b:
                r6.mPerfModeOverridden = r3
                r6.mBatteryModeOverridden = r3
                r6.mAllowDownscale = r2
                r6.mAllowAngle = r2
                r6.mAllowFpsOverride = r2
                java.lang.String r6 = "Error while parsing XML meta-data for android.game_mode_config"
                android.util.Slog.e(r0, r6)
            L9a:
                return r8
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.app.GameManagerService.GamePackageConfiguration.parseInterventionFromXml(android.content.pm.ApplicationInfo,"
                        + " android.content.pm.PackageManager, java.lang.String):boolean");
        }

        public final String toString() {
            String str;
            synchronized (this.mModeConfigLock) {
                str =
                        "[Name:"
                                + this.mPackageName
                                + " Modes: "
                                + this.mModeConfigs.toString()
                                + "]";
            }
            return str;
        }

        public final boolean willGamePerformOptimizations(int i) {
            return (this.mBatteryModeOverridden && i == 3) || (this.mPerfModeOverridden && i == 2);
        }
    }

    class Injector {

        /* renamed from: com.android.server.app.GameManagerService$Injector$1, reason: invalid class name */
        public final class AnonymousClass1 {}
    }

    public final class Lifecycle extends SystemService {
        public final GameManagerService mService;

        public Lifecycle(Context context) {
            super(context);
            ServiceThread serviceThread = new ServiceThread(10, "GameManagerService", true);
            serviceThread.start();
            this.mService =
                    new GameManagerService(
                            context,
                            serviceThread.getLooper(),
                            Environment.getDataDirectory(),
                            new Injector());
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            if (i == 1000) {
                final GameManagerService gameManagerService = this.mService;
                gameManagerService.onBootCompleted();
                StatsManager statsManager =
                        (StatsManager)
                                gameManagerService.mContext.getSystemService(StatsManager.class);
                Executor executor = ConcurrentUtils.DIRECT_EXECUTOR;
                statsManager.setPullAtomCallback(
                        FrameworkStatsLog.GAME_MODE_INFO,
                        (StatsManager.PullAtomMetadata) null,
                        executor,
                        new StatsManager
                                .StatsPullAtomCallback() { // from class:
                                                           // com.android.server.app.GameManagerService$$ExternalSyntheticLambda5
                            public final int onPullAtom(int i2, List list) {
                                GameManagerService.m226$r8$lambda$LCP_4kvyEBQxmT_kuk0Xyillc(
                                        GameManagerService.this, i2, list);
                                return 0;
                            }
                        });
                statsManager.setPullAtomCallback(
                        FrameworkStatsLog.GAME_MODE_CONFIGURATION,
                        (StatsManager.PullAtomMetadata) null,
                        executor,
                        new StatsManager
                                .StatsPullAtomCallback() { // from class:
                                                           // com.android.server.app.GameManagerService$$ExternalSyntheticLambda5
                            public final int onPullAtom(int i2, List list) {
                                GameManagerService.m226$r8$lambda$LCP_4kvyEBQxmT_kuk0Xyillc(
                                        GameManagerService.this, i2, list);
                                return 0;
                            }
                        });
                statsManager.setPullAtomCallback(
                        FrameworkStatsLog.GAME_MODE_LISTENER,
                        (StatsManager.PullAtomMetadata) null,
                        executor,
                        new StatsManager
                                .StatsPullAtomCallback() { // from class:
                                                           // com.android.server.app.GameManagerService$$ExternalSyntheticLambda5
                            public final int onPullAtom(int i2, List list) {
                                GameManagerService.m226$r8$lambda$LCP_4kvyEBQxmT_kuk0Xyillc(
                                        GameManagerService.this, i2, list);
                                return 0;
                            }
                        });
            }
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            int i = 1;
            publishBinderService("game", this.mService);
            GameManagerService gameManagerService = this.mService;
            int i2 = GameManagerService.$r8$clinit;
            gameManagerService.getClass();
            LocalService localService = gameManagerService.new LocalService();
            CompatModePackages compatModePackages =
                    ActivityTaskManagerService.this.mCompatModePackages;
            WindowManagerGlobalLock windowManagerGlobalLock =
                    compatModePackages.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (compatModePackages.mProviders.contains(1)) {
                        throw new IllegalArgumentException("Duplicate id provided: 1");
                    }
                    compatModePackages.mProviders.put(1, localService);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            LocalServices.addService(GameManagerInternal.class, localService);
            GameManagerService gameManagerService2 = this.mService;
            gameManagerService2.getClass();
            gameManagerService2.mDeviceConfigListener =
                    gameManagerService2.new DeviceConfigListener();
            GameManagerService gameManagerService3 = this.mService;
            gameManagerService3.getClass();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addDataScheme("package");
            gameManagerService3.mContext.registerReceiverForAllUsers(
                    new AnonymousClass3(gameManagerService3, i), intentFilter, null, null);
        }

        @Override // com.android.server.SystemService
        public final void onUserStarting(SystemService.TargetUser targetUser) {
            Slog.d("GameManagerService", "Starting user " + targetUser.getUserIdentifier());
            GameManagerService gameManagerService = this.mService;
            File dataSystemDeDirectory =
                    Environment.getDataSystemDeDirectory(targetUser.getUserIdentifier());
            gameManagerService.getClass();
            int userIdentifier = targetUser.getUserIdentifier();
            synchronized (gameManagerService.mLock) {
                try {
                    if (!gameManagerService.mSettings.containsKey(
                            Integer.valueOf(userIdentifier))) {
                        GameManagerSettings gameManagerSettings =
                                new GameManagerSettings(dataSystemDeDirectory);
                        gameManagerService.mSettings.put(
                                Integer.valueOf(userIdentifier), gameManagerSettings);
                        gameManagerSettings.readPersistentDataLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            gameManagerService.sendUserMessage(userIdentifier, 3, 0, "ON_USER_STARTING");
            GameServiceController gameServiceController = gameManagerService.mGameServiceController;
            if (gameServiceController == null
                    || gameServiceController.mCurrentForegroundUser != null) {
                return;
            }
            gameServiceController.setCurrentForegroundUserAndEvaluateProvider(targetUser);
        }

        @Override // com.android.server.SystemService
        public final void onUserStopping(SystemService.TargetUser targetUser) {
            GameManagerService gameManagerService = this.mService;
            gameManagerService.getClass();
            int userIdentifier = targetUser.getUserIdentifier();
            synchronized (gameManagerService.mLock) {
                try {
                    if (gameManagerService.mSettings.containsKey(Integer.valueOf(userIdentifier))) {
                        gameManagerService.sendUserMessage(
                                userIdentifier, 2, 0, "ON_USER_STOPPING");
                        GameServiceController gameServiceController =
                                gameManagerService.mGameServiceController;
                        if (gameServiceController == null
                                || gameServiceController.mCurrentForegroundUser == null
                                || gameServiceController.mCurrentForegroundUser.getUserIdentifier()
                                        != targetUser.getUserIdentifier()) {
                            return;
                        }
                        gameServiceController.setCurrentForegroundUserAndEvaluateProvider(null);
                    }
                } finally {
                }
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserSwitching(
                SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
            GameManagerService gameManagerService = this.mService;
            gameManagerService.getClass();
            gameManagerService.sendUserMessage(
                    targetUser2.getUserIdentifier(), 3, 0, "ON_USER_SWITCHING");
            GameServiceController gameServiceController = gameManagerService.mGameServiceController;
            if (gameServiceController != null) {
                gameServiceController.setCurrentForegroundUserAndEvaluateProvider(targetUser2);
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserUnlocking(SystemService.TargetUser targetUser) {
            GameServiceController gameServiceController = this.mService.mGameServiceController;
            if (gameServiceController == null
                    || gameServiceController.mCurrentForegroundUser == null
                    || gameServiceController.mCurrentForegroundUser.getUserIdentifier()
                            != targetUser.getUserIdentifier()) {
                return;
            }
            gameServiceController.mBackgroundExecutor.execute(
                    new GameServiceController$$ExternalSyntheticLambda0(gameServiceController));
        }
    }

    public final class LocalService extends GameManagerInternal implements CompatScaleProvider {
        public LocalService() {}

        public final float getResolutionScalingFactor(String str, int i) {
            GamePackageConfiguration.GameModeConfiguration gameModeConfiguration;
            GameManagerService gameManagerService = GameManagerService.this;
            int i2 = GameManagerService.$r8$clinit;
            int gameModeFromSettingsUnchecked =
                    gameManagerService.getGameModeFromSettingsUnchecked(i, str);
            GamePackageConfiguration config = GameManagerService.this.getConfig(i, str);
            if (config == null
                    || (gameModeConfiguration =
                                    config.getGameModeConfiguration(gameModeFromSettingsUnchecked))
                            == null) {
                return -1.0f;
            }
            return gameModeConfiguration.getScaling();
        }

        public final void updateResolutionScalingFactorInternal(String str, float f) {
            synchronized (GameManagerService.this.mLock) {
                try {
                    GameManagerService gameManagerService = GameManagerService.this;
                    if (gameManagerService.mSettings.containsKey(
                            Integer.valueOf(gameManagerService.mContext.getUserId()))) {
                        GameManagerService gameManagerService2 = GameManagerService.this;
                        int gameModeFromSettingsUnchecked =
                                gameManagerService2.getGameModeFromSettingsUnchecked(
                                        gameManagerService2.mContext.getUserId(), str);
                        DeviceConfig.deleteProperty("game_overlay", str);
                        GameManagerService gameManagerService3 = GameManagerService.this;
                        gameManagerService3.updateResolutionScalingFactor(
                                str,
                                gameModeFromSettingsUnchecked,
                                f,
                                gameManagerService3.mContext.getUserId());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class MyUidObserver extends UidObserver {
        public MyUidObserver() {}

        public final void handleUidMovedOffTop(int i) {
            synchronized (GameManagerService.this.mUidObserverLock) {
                try {
                    if (((HashSet) GameManagerService.this.mGameForegroundUids)
                            .contains(Integer.valueOf(i))) {
                        ((HashSet) GameManagerService.this.mGameForegroundUids)
                                .remove(Integer.valueOf(i));
                        if (((HashSet) GameManagerService.this.mGameForegroundUids).isEmpty()) {
                            if (Flags.disableGameModeWhenAppTop()) {
                                if (((HashSet) GameManagerService.this.mNonGameForegroundUids)
                                        .isEmpty()) {}
                            }
                            Slog.v(
                                    "GameManagerService",
                                    "Game power mode OFF (no games in foreground)");
                            GameManagerService.this.mPowerManagerInternal.setPowerMode(15, false);
                        }
                    } else if (Flags.disableGameModeWhenAppTop()) {
                        if (((HashSet) GameManagerService.this.mNonGameForegroundUids)
                                .contains(Integer.valueOf(i))) {
                            ((HashSet) GameManagerService.this.mNonGameForegroundUids)
                                    .remove(Integer.valueOf(i));
                            if (((HashSet) GameManagerService.this.mNonGameForegroundUids).isEmpty()
                                    && !((HashSet) GameManagerService.this.mGameForegroundUids)
                                            .isEmpty()) {
                                Slog.v(
                                        "GameManagerService",
                                        "Game power mode ON (only games in foreground)");
                                GameManagerService.this.mPowerManagerInternal.setPowerMode(
                                        15, true);
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onUidGone(int i, boolean z) {
            synchronized (GameManagerService.this.mUidObserverLock) {
                handleUidMovedOffTop(i);
            }
        }

        public final void onUidStateChanged(int i, int i2, long j, int i3) {
            if (i2 != 2) {
                handleUidMovedOffTop(i);
                return;
            }
            String[] packagesForUid = GameManagerService.this.mPackageManager.getPackagesForUid(i);
            if (packagesForUid == null || packagesForUid.length == 0) {
                return;
            }
            boolean noneMatch =
                    Arrays.stream(packagesForUid)
                            .noneMatch(
                                    new GameManagerService$$ExternalSyntheticLambda3(
                                            this, GameManagerService.this.mContext.getUserId()));
            synchronized (GameManagerService.this.mUidObserverLock) {
                try {
                    if (noneMatch) {
                        if (Flags.disableGameModeWhenAppTop()) {
                            if (!((HashSet) GameManagerService.this.mGameForegroundUids).isEmpty()
                                    && ((HashSet) GameManagerService.this.mNonGameForegroundUids)
                                            .isEmpty()) {
                                Slog.v(
                                        "GameManagerService",
                                        "Game power mode OFF (first non-game in foreground)");
                                GameManagerService.this.mPowerManagerInternal.setPowerMode(
                                        15, false);
                            }
                            ((HashSet) GameManagerService.this.mNonGameForegroundUids)
                                    .add(Integer.valueOf(i));
                        }
                        return;
                    }
                    if (((HashSet) GameManagerService.this.mGameForegroundUids).isEmpty()
                            && (!Flags.disableGameModeWhenAppTop()
                                    || ((HashSet) GameManagerService.this.mNonGameForegroundUids)
                                            .isEmpty())) {
                        Slog.v(
                                "GameManagerService",
                                "Game power mode ON (first game in foreground)");
                        GameManagerService.this.mPowerManagerInternal.setPowerMode(15, true);
                    }
                    GameManagerService.this.mSysProps.getClass();
                    boolean z =
                            SystemProperties.getBoolean(
                                    "debug.graphics.game_default_frame_rate.disabled", false);
                    GameManagerService gameManagerService = GameManagerService.this;
                    boolean z2 = !z;
                    gameManagerService.getClass();
                    boolean gameDefaultFrameRate = Flags.gameDefaultFrameRate();
                    float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
                    if (gameDefaultFrameRate && z2) {
                        f = gameManagerService.mGameDefaultFrameRateValue;
                    }
                    gameManagerService.setGameDefaultFrameRateOverride(i, f);
                    ((HashSet) GameManagerService.this.mGameForegroundUids).add(Integer.valueOf(i));
                } finally {
                }
            }
        }
    }

    public final class SettingsHandler extends Handler {
        public SettingsHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r18) {
            /*
                Method dump skipped, instructions count: 866
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.app.GameManagerService.SettingsHandler.handleMessage(android.os.Message):void");
        }
    }

    /* renamed from: $r8$lambda$LCP_4kvy-EB-QxmT_kuk0Xyillc, reason: not valid java name */
    public static void m226$r8$lambda$LCP_4kvyEBQxmT_kuk0Xyillc(
            GameManagerService gameManagerService, int i, List list) {
        Set<String> keySet;
        int i2;
        gameManagerService.getClass();
        if (i != 10165 && i != 10166) {
            if (i == 10167) {
                synchronized (gameManagerService.mGameModeListenerLock) {
                    list.add(
                            FrameworkStatsLog.buildStatsEvent(
                                    FrameworkStatsLog.GAME_MODE_LISTENER,
                                    gameManagerService.mGameModeListeners.size()));
                }
                return;
            }
            return;
        }
        int currentUser = ActivityManager.getCurrentUser();
        synchronized (gameManagerService.mDeviceConfigLock) {
            keySet = gameManagerService.mConfigs.keySet();
        }
        for (String str : keySet) {
            GamePackageConfiguration config = gameManagerService.getConfig(currentUser, str);
            if (config != null) {
                try {
                    i2 = gameManagerService.mPackageManager.getPackageUidAsUser(str, currentUser);
                } catch (PackageManager.NameNotFoundException unused) {
                    Slog.d(
                            "GameManagerService",
                            "Cannot find UID for package "
                                    + str
                                    + " under user handle id "
                                    + currentUser);
                    i2 = -1;
                }
                int i3 = 0;
                if (i == 10165) {
                    int[] overriddenGameModes = config.getOverriddenGameModes();
                    int[] iArr = new int[overriddenGameModes.length];
                    int length = overriddenGameModes.length;
                    int i4 = 0;
                    int i5 = 0;
                    while (i4 < length) {
                        iArr[i5] = gameModeToStatsdGameMode(overriddenGameModes[i4]);
                        i4++;
                        i5++;
                    }
                    int[] availableGameModes = config.getAvailableGameModes();
                    int[] iArr2 = new int[availableGameModes.length];
                    int length2 = availableGameModes.length;
                    int i6 = 0;
                    while (i3 < length2) {
                        iArr2[i6] = gameModeToStatsdGameMode(availableGameModes[i3]);
                        i3++;
                        i6++;
                    }
                    list.add(
                            FrameworkStatsLog.buildStatsEvent(
                                    FrameworkStatsLog.GAME_MODE_INFO, i2, iArr, iArr2));
                } else if (i == 10166) {
                    int[] availableGameModes2 = config.getAvailableGameModes();
                    int length3 = availableGameModes2.length;
                    while (i3 < length3) {
                        int i7 = availableGameModes2[i3];
                        GamePackageConfiguration.GameModeConfiguration gameModeConfiguration =
                                config.getGameModeConfiguration(i7);
                        if (gameModeConfiguration != null) {
                            list.add(
                                    FrameworkStatsLog.buildStatsEvent(
                                            FrameworkStatsLog.GAME_MODE_CONFIGURATION,
                                            i2,
                                            gameModeToStatsdGameMode(i7),
                                            gameModeConfiguration.getFps(),
                                            gameModeConfiguration.getScaling()));
                        }
                        i3++;
                    }
                }
            }
        }
    }

    public GameManagerService(Context context, Looper looper, File file, Injector injector) {
        super(PermissionEnforcer.fromContext(context));
        this.mLock = new Object();
        this.mDeviceConfigLock = new Object();
        this.mGameModeListenerLock = new Object();
        this.mGameStateListenerLock = new Object();
        this.mSettings = new ArrayMap();
        this.mConfigs = new ArrayMap();
        this.mGameModeListeners = new ArrayMap();
        this.mGameStateListeners = new ArrayMap();
        this.mUidObserverLock = new Object();
        this.mGameForegroundUids = new HashSet();
        this.mNonGameForegroundUids = new HashSet();
        this.mContext = context;
        this.mHandler = new SettingsHandler(looper);
        PackageManager packageManager = context.getPackageManager();
        this.mPackageManager = packageManager;
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mPowerManagerInternal =
                (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        File file2 = new File(file, "system");
        file2.mkdirs();
        FileUtils.setPermissions(file2.toString(), 509, -1, -1);
        AtomicFile atomicFile = new AtomicFile(new File(file2, "game_mode_intervention.list"));
        this.mGameModeInterventionListFile = atomicFile;
        FileUtils.setPermissions(
                atomicFile.getBaseFile().getAbsolutePath(),
                FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED,
                -1,
                -1);
        if (packageManager.hasSystemFeature("android.software.game_service")) {
            this.mGameServiceController =
                    new GameServiceController(
                            context,
                            BackgroundThread.getExecutor(),
                            new GameServiceProviderSelectorImpl(
                                    context.getResources(), packageManager),
                            new GameServiceProviderInstanceFactoryImpl(context));
        } else {
            this.mGameServiceController = null;
        }
        MyUidObserver myUidObserver = new MyUidObserver();
        this.mUidObserver = myUidObserver;
        try {
            ActivityManager.getService().registerUidObserver(myUidObserver, 3, -1, (String) null);
        } catch (RemoteException unused) {
            Slog.w("GameManagerService", "Could not register UidObserver");
        }
        injector.getClass();
        this.mSysProps = new Injector.AnonymousClass1();
    }

    public static int gameModeToStatsdGameMode(int i) {
        if (i == 0) {
            return 1;
        }
        if (i == 1) {
            return 2;
        }
        if (i == 2) {
            return 3;
        }
        if (i != 3) {
            return i != 4 ? 0 : 5;
        }
        return 4;
    }

    private static native void nativeSetGameDefaultFrameRateOverride(int i, float f);

    private static native void nativeSetGameModeFrameRateOverride(int i, float f);

    public final void addGameModeListener(IGameModeListener iGameModeListener) {
        checkPermission("android.permission.MANAGE_GAME_MODE");
        try {
            IBinder asBinder = iGameModeListener.asBinder();
            asBinder.linkToDeath(new AnonymousClass1(iGameModeListener, asBinder), 0);
            synchronized (this.mGameModeListenerLock) {
                this.mGameModeListeners.put(
                        iGameModeListener, Integer.valueOf(Binder.getCallingUid()));
            }
        } catch (RemoteException e) {
            Slog.e(
                    "GameManagerService",
                    "Failed to link death recipient for IGameModeListener from caller "
                            + Binder.getCallingUid()
                            + ", abandoned its listener registration",
                    e);
        }
    }

    public final void addGameStateListener(IGameStateListener iGameStateListener) {
        try {
            IBinder asBinder = iGameStateListener.asBinder();
            asBinder.linkToDeath(new AnonymousClass1(iGameStateListener, asBinder), 0);
            synchronized (this.mGameStateListenerLock) {
                this.mGameStateListeners.put(
                        iGameStateListener, Integer.valueOf(Binder.getCallingUid()));
            }
        } catch (RemoteException e) {
            Slog.e(
                    "GameManagerService",
                    "Failed to link death recipient for IGameStateListener from caller "
                            + Binder.getCallingUid()
                            + ", abandoned its listener registration",
                    e);
        }
    }

    public final void checkPermission(String str) {
        if (this.mContext.checkCallingOrSelfPermission(str) == 0) {
            return;
        }
        throw new SecurityException(
                "Access denied to process: "
                        + Binder.getCallingPid()
                        + ", must have permission "
                        + str);
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println(
                    "Permission Denial: can't dump GameManagerService from from pid="
                            + Binder.getCallingPid()
                            + ", uid="
                            + Binder.getCallingUid()
                            + " without permission android.permission.DUMP");
            return;
        }
        if (strArr == null || strArr.length == 0) {
            printWriter.println("*Dump GameManagerService*");
            int currentUser = ActivityManager.getCurrentUser();
            for (String str : getInstalledGamePackageNames(currentUser)) {
                printWriter.println(getInterventionList(currentUser, str));
            }
        }
    }

    public final int[] getAvailableGameModes(String str, int i) {
        checkPermission("android.permission.MANAGE_GAME_MODE");
        if (!isPackageGame(i, str)) {
            return new int[0];
        }
        GamePackageConfiguration config = getConfig(i, str);
        return config == null ? new int[] {1, 4} : config.getAvailableGameModes();
    }

    public final GamePackageConfiguration getConfig(int i, String str) {
        GamePackageConfiguration gamePackageConfiguration;
        GamePackageConfiguration gamePackageConfiguration2;
        synchronized (this.mDeviceConfigLock) {
            gamePackageConfiguration = (GamePackageConfiguration) this.mConfigs.get(str);
        }
        synchronized (this.mLock) {
            try {
                gamePackageConfiguration2 =
                        this.mSettings.containsKey(Integer.valueOf(i))
                                ? (GamePackageConfiguration)
                                        ((GameManagerSettings)
                                                        this.mSettings.get(Integer.valueOf(i)))
                                                .mConfigOverrides.get(str)
                                : null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return (gamePackageConfiguration2 == null || gamePackageConfiguration == null)
                ? gamePackageConfiguration2 == null
                        ? gamePackageConfiguration
                        : gamePackageConfiguration2
                : gamePackageConfiguration.copyAndApplyOverride(gamePackageConfiguration2);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getGameMode(java.lang.String r12, int r13) {
        /*
            r11 = this;
            int r0 = android.os.Binder.getCallingPid()
            int r1 = android.os.Binder.getCallingUid()
            r3 = 0
            r4 = 1
            java.lang.String r5 = "getGameMode"
            java.lang.String r6 = "com.android.server.app.GameManagerService"
            r2 = r13
            int r13 = android.app.ActivityManager.handleIncomingUser(r0, r1, r2, r3, r4, r5, r6)
            boolean r0 = r11.isPackageGame(r13, r12)
            r1 = 0
            if (r0 != 0) goto L1d
            return r1
        L1d:
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            java.lang.StackTraceElement[] r0 = r0.getStackTrace()
            int r2 = r0.length
            r3 = 1
            r4 = 3
            if (r2 <= r4) goto L47
            r0 = r0[r4]
            java.lang.String r2 = r0.getClassName()
            java.lang.String r5 = "IGameManagerService"
            boolean r2 = r2.contains(r5)
            if (r2 == 0) goto L47
            java.lang.String r0 = r0.getMethodName()
            java.lang.String r2 = "onTransact"
            boolean r0 = r0.contains(r2)
            if (r0 == 0) goto L47
            r0 = r3
            goto L48
        L47:
            r0 = r1
        L48:
            android.content.pm.PackageManager r2 = r11.mPackageManager     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L55
            int r2 = r2.getPackageUidAsUser(r12, r13)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L55
            int r5 = android.os.Binder.getCallingUid()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L55
            if (r2 != r5) goto L55
            r1 = r3
        L55:
            java.lang.String r2 = "PackageName = : "
            java.lang.String r5 = "GMS_getGameMode"
            java.lang.String r6 = "GAME_MODE_UNSUPPORTED"
            java.lang.String r7 = "GAME_MODE_PERFORMANCE"
            r8 = 2
            java.lang.String r9 = "GAME_MODE_STANDARD"
            java.lang.String r10 = "GAME_MODE_BATTERY"
            if (r1 == 0) goto L7d
            if (r0 == 0) goto L78
            int r0 = r11.getGameModeFromSettingsUnchecked(r13, r12)
            if (r0 != r4) goto L6e
            r6 = r10
            goto L75
        L6e:
            if (r0 != r3) goto L72
            r6 = r9
            goto L75
        L72:
            if (r0 != r8) goto L75
            r6 = r7
        L75:
            com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m(r2, r12, r6, r5)
        L78:
            int r11 = r11.getGameModeFromSettingsUnchecked(r13, r12)
            return r11
        L7d:
            java.lang.String r1 = "android.permission.MANAGE_GAME_MODE"
            r11.checkPermission(r1)
            if (r0 == 0) goto L96
            int r0 = r11.getGameModeFromSettingsUnchecked(r13, r12)
            if (r0 != r4) goto L8c
            r6 = r10
            goto L93
        L8c:
            if (r0 != r3) goto L90
            r6 = r9
            goto L93
        L90:
            if (r0 != r8) goto L93
            r6 = r7
        L93:
            com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m(r2, r12, r6, r5)
        L96:
            int r11 = r11.getGameModeFromSettingsUnchecked(r13, r12)
            return r11
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.app.GameManagerService.getGameMode(java.lang.String,"
                    + " int):int");
    }

    public final int getGameModeFromSettingsUnchecked(int i, String str) {
        int intValue;
        synchronized (this.mLock) {
            try {
                int i2 = 1;
                if (this.mSettings.containsKey(Integer.valueOf(i))) {
                    GameManagerSettings gameManagerSettings =
                            (GameManagerSettings) this.mSettings.get(Integer.valueOf(i));
                    if (gameManagerSettings.mGameModes.containsKey(str)
                            && (intValue =
                                            ((Integer) gameManagerSettings.mGameModes.get(str))
                                                    .intValue())
                                    != 0) {
                        i2 = intValue;
                    }
                    return i2;
                }
                Slog.d(
                        "GameManagerService",
                        "User ID '"
                                + i
                                + "' does not have a Game Mode selected for package: '"
                                + str
                                + "'");
                return 1;
            } finally {
            }
        }
    }

    public final GameModeInfo getGameModeInfo(String str, int i) {
        GamePackageConfiguration.GameModeConfiguration gameModeConfiguration;
        int i2;
        int handleIncomingUser =
                ActivityManager.handleIncomingUser(
                        Binder.getCallingPid(),
                        Binder.getCallingUid(),
                        i,
                        false,
                        true,
                        "getGameModeInfo",
                        "com.android.server.app.GameManagerService");
        checkPermission("android.permission.MANAGE_GAME_MODE");
        if (!isPackageGame(handleIncomingUser, str)) {
            return null;
        }
        int gameModeFromSettingsUnchecked =
                getGameModeFromSettingsUnchecked(handleIncomingUser, str);
        GamePackageConfiguration config = getConfig(handleIncomingUser, str);
        if (config == null) {
            GameModeInfo.Builder activeGameMode =
                    new GameModeInfo.Builder().setActiveGameMode(gameModeFromSettingsUnchecked);
            GamePackageConfiguration config2 = getConfig(handleIncomingUser, str);
            return activeGameMode
                    .setAvailableGameModes(
                            config2 == null ? new int[] {1, 4} : config2.getAvailableGameModes())
                    .build();
        }
        int[] overriddenGameModes = config.getOverriddenGameModes();
        int[] availableGameModes = config.getAvailableGameModes();
        GameModeInfo.Builder fpsOverrideAllowed =
                new GameModeInfo.Builder()
                        .setActiveGameMode(gameModeFromSettingsUnchecked)
                        .setAvailableGameModes(availableGameModes)
                        .setOverriddenGameModes(overriddenGameModes)
                        .setDownscalingAllowed(config.mAllowDownscale)
                        .setFpsOverrideAllowed(config.mAllowFpsOverride);
        for (int i3 : availableGameModes) {
            if (!config.willGamePerformOptimizations(i3)
                    && (gameModeConfiguration = config.getGameModeConfiguration(i3)) != null) {
                try {
                    i2 = Integer.parseInt(gameModeConfiguration.mFps);
                } catch (NumberFormatException unused) {
                    i2 = 0;
                }
                if (i2 <= 0) {
                    i2 = 0;
                }
                float f = gameModeConfiguration.mScaling;
                if (f == -1.0f) {
                    f = 1.0f;
                }
                fpsOverrideAllowed.setGameModeConfiguration(
                        i3,
                        new GameModeConfiguration.Builder()
                                .setScalingFactor(f)
                                .setFpsOverride(i2)
                                .build());
            }
        }
        return fpsOverrideAllowed.build();
    }

    public final String[] getInstalledGamePackageNames(int i) {
        return (String[])
                this.mPackageManager.getInstalledPackagesAsUser(0, i).stream()
                        .filter(new GameManagerService$$ExternalSyntheticLambda0())
                        .map(new GameManagerService$$ExternalSyntheticLambda1())
                        .toArray(new GameManagerService$$ExternalSyntheticLambda2(0));
    }

    public final List getInstalledGamePackageNamesByAllUsers(int i) {
        HashSet hashSet = new HashSet();
        List users = this.mUserManager.getUsers();
        int size = users.size();
        int[] iArr = new int[size];
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = ((UserInfo) users.get(i2)).id;
        }
        if (i != -1) {
            iArr = ArrayUtils.appendInt(iArr, i);
        }
        for (int i3 : iArr) {
            hashSet.addAll(Arrays.asList(getInstalledGamePackageNames(i3)));
        }
        return new ArrayList(hashSet);
    }

    public final String getInterventionList(int i, String str) {
        checkPermission("android.permission.QUERY_ALL_PACKAGES");
        GamePackageConfiguration config = getConfig(i, str);
        StringBuilder sb = new StringBuilder();
        if (config == null) {
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                    sb, "\n No intervention found for package ", str);
        }
        sb.append("\n");
        sb.append(config.toString());
        return sb.toString();
    }

    public final int getLoadingBoostDuration(int i, String str) {
        GamePackageConfiguration gamePackageConfiguration;
        GamePackageConfiguration.GameModeConfiguration gameModeConfiguration;
        int i2;
        int gameMode = getGameMode(str, i);
        if (gameMode == 0) {
            return -1;
        }
        synchronized (this.mDeviceConfigLock) {
            gamePackageConfiguration = (GamePackageConfiguration) this.mConfigs.get(str);
        }
        if (gamePackageConfiguration == null
                || (gameModeConfiguration =
                                gamePackageConfiguration.getGameModeConfiguration(gameMode))
                        == null) {
            return -1;
        }
        synchronized (gameModeConfiguration) {
            i2 = gameModeConfiguration.mLoadingBoostDuration;
        }
        return i2;
    }

    public final float getResolutionScalingFactor(String str, int i, int i2) {
        GamePackageConfiguration.GameModeConfiguration gameModeConfiguration;
        checkPermission("android.permission.MANAGE_GAME_MODE");
        synchronized (this.mLock) {
            if (!this.mSettings.containsKey(Integer.valueOf(i2))) {
                throw new IllegalArgumentException("User " + i2 + " wasn't started");
            }
        }
        GamePackageConfiguration config = getConfig(i2, str);
        if (config == null
                || (gameModeConfiguration = config.getGameModeConfiguration(i)) == null) {
            return -1.0f;
        }
        return gameModeConfiguration.getScaling();
    }

    public final boolean isAngleEnabled(String str, int i) {
        boolean z;
        int gameMode = getGameMode(str, i);
        if (gameMode == 0) {
            return false;
        }
        synchronized (this.mDeviceConfigLock) {
            try {
                GamePackageConfiguration gamePackageConfiguration =
                        (GamePackageConfiguration) this.mConfigs.get(str);
                if (gamePackageConfiguration == null) {
                    return false;
                }
                GamePackageConfiguration.GameModeConfiguration gameModeConfiguration =
                        gamePackageConfiguration.getGameModeConfiguration(gameMode);
                if (gameModeConfiguration == null) {
                    return false;
                }
                synchronized (gameModeConfiguration) {
                    z = gameModeConfiguration.mUseAngle;
                }
                return z;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isPackageGame(int i, String str) {
        try {
            return this.mPackageManager.getApplicationInfoAsUser(str, 131072, i).category == 0;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final void notifyGraphicsEnvironmentSetup(String str, int i) {
        int handleIncomingUser =
                ActivityManager.handleIncomingUser(
                        Binder.getCallingPid(),
                        Binder.getCallingUid(),
                        i,
                        false,
                        true,
                        "notifyGraphicsEnvironmentSetup",
                        "com.android.server.app.GameManagerService");
        boolean z = false;
        try {
            if (this.mPackageManager.getPackageUidAsUser(str, handleIncomingUser)
                    == Binder.getCallingUid()) {
                z = true;
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        if (!z) {
            Slog.d(
                    "GameManagerService",
                    "No-op for attempt to notify graphics env setup for different packagethan"
                        + " caller with uid: "
                            + Binder.getCallingUid());
            return;
        }
        if (getGameMode(str, handleIncomingUser) == 0) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    "No-op for attempt to notify graphics env setup for non-game app: ",
                    str,
                    "GameManagerService");
            return;
        }
        int loadingBoostDuration = getLoadingBoostDuration(handleIncomingUser, str);
        if (loadingBoostDuration != -1) {
            if (loadingBoostDuration == 0 || loadingBoostDuration > 5000) {
                loadingBoostDuration = 5000;
            }
            if (this.mHandler.hasMessages(5)) {
                this.mHandler.removeMessages(5);
            } else {
                Slog.v(
                        "GameManagerService",
                        "Game loading power mode ON (loading boost on game start)");
                this.mPowerManagerInternal.setPowerMode(16, true);
            }
            Handler handler = this.mHandler;
            handler.sendMessageDelayed(handler.obtainMessage(5), loadingBoostDuration);
        }
    }

    public void onBootCompleted() {
        Slog.d("GameManagerService", "onBootCompleted");
        GameServiceController gameServiceController = this.mGameServiceController;
        if (gameServiceController != null && !gameServiceController.mHasBootCompleted) {
            gameServiceController.mHasBootCompleted = true;
            gameServiceController.mBackgroundExecutor.execute(
                    new GameServiceController$$ExternalSyntheticLambda0(gameServiceController));
        }
        this.mContext.registerReceiver(
                new AnonymousClass3(this, 0),
                new IntentFilter("android.intent.action.ACTION_SHUTDOWN"));
        Slog.v(
                "GameManagerService",
                "Game loading power mode OFF (game manager service start/restart)");
        this.mPowerManagerInternal.setPowerMode(16, false);
        Slog.v("GameManagerService", "Game power mode OFF (game manager service start/restart)");
        this.mPowerManagerInternal.setPowerMode(15, false);
        this.mSysProps.getClass();
        this.mGameDefaultFrameRateValue =
                SystemProperties.getInt("ro.surface_flinger.game_default_frame_rate_override", 60);
        Slog.v(
                "GameManagerService",
                "Game Default Frame Rate : " + this.mGameDefaultFrameRateValue);
    }

    public final void onShellCommand(
            FileDescriptor fileDescriptor,
            FileDescriptor fileDescriptor2,
            FileDescriptor fileDescriptor3,
            String[] strArr,
            ShellCallback shellCallback,
            ResultReceiver resultReceiver) {
        new GameManagerShellCommand()
                .exec(
                        this,
                        fileDescriptor,
                        fileDescriptor2,
                        fileDescriptor3,
                        strArr,
                        shellCallback,
                        resultReceiver);
    }

    public final void removeGameModeListener(IGameModeListener iGameModeListener) {
        checkPermission("android.permission.MANAGE_GAME_MODE");
        synchronized (this.mGameModeListenerLock) {
            this.mGameModeListeners.remove(iGameModeListener);
        }
    }

    public final void removeGameStateListener(IGameStateListener iGameStateListener) {
        synchronized (this.mGameStateListenerLock) {
            this.mGameStateListeners.remove(iGameStateListener);
        }
    }

    public void resetGameModeConfigOverride(String str, int i, int i2) throws SecurityException {
        boolean z;
        checkPermission("android.permission.MANAGE_GAME_MODE");
        synchronized (this.mLock) {
            try {
                if (this.mSettings.containsKey(Integer.valueOf(i))) {
                    GameManagerSettings gameManagerSettings =
                            (GameManagerSettings) this.mSettings.get(Integer.valueOf(i));
                    if (i2 != -1) {
                        GamePackageConfiguration gamePackageConfiguration =
                                (GamePackageConfiguration)
                                        gameManagerSettings.mConfigOverrides.get(str);
                        if (gamePackageConfiguration == null) {
                            return;
                        }
                        if ((gamePackageConfiguration.getAvailableGameModesBitfield() & (1 << i2))
                                == 0) {
                            return;
                        }
                        synchronized (gamePackageConfiguration.mModeConfigLock) {
                            gamePackageConfiguration.mModeConfigs.remove(Integer.valueOf(i2));
                        }
                        synchronized (gamePackageConfiguration.mModeConfigLock) {
                            z = !gamePackageConfiguration.mModeConfigs.isEmpty();
                        }
                        if (!z) {
                            gameManagerSettings.mConfigOverrides.remove(str);
                        }
                    } else {
                        gameManagerSettings.mConfigOverrides.remove(str);
                    }
                    int gameMode = getGameMode(str, i);
                    GamePackageConfiguration config = getConfig(i, str);
                    if (gameMode
                            != ((config == null
                                            || ((config.getAvailableGameModesBitfield() & (-2))
                                                            & (1 << gameMode))
                                                    == 0)
                                    ? 1
                                    : gameMode)) {
                        setGameMode(str, 1, i);
                    } else {
                        setGameMode(str, gameMode, i);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void sendUserMessage(int i, int i2, int i3, String str) {
        if (this.mHandler.sendMessageDelayed(
                this.mHandler.obtainMessage(i2, Integer.valueOf(i)), i3)) {
            return;
        }
        Slog.e("GameManagerService", "Failed to send user message " + i2 + " on " + str);
    }

    public void setGameDefaultFrameRateOverride(int i, float f) {
        Slog.v("GameManagerService", "setDefaultFrameRateOverride : " + i + " , " + f);
        nativeSetGameDefaultFrameRateOverride(i, f);
    }

    public final void setGameMode(String str, int i, int i2) {
        int i3;
        int i4;
        GamePackageConfiguration gamePackageConfiguration;
        checkPermission("android.permission.MANAGE_GAME_MODE");
        if (i == 0) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    "No-op for attempt to set UNSUPPORTED mode for app: ",
                    str,
                    "GameManagerService");
            return;
        }
        if (!isPackageGame(i2, str)) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    "No-op for attempt to set game mode for non-game app: ",
                    str,
                    "GameManagerService");
            return;
        }
        synchronized (this.mLock) {
            try {
                int handleIncomingUser =
                        ActivityManager.handleIncomingUser(
                                Binder.getCallingPid(),
                                Binder.getCallingUid(),
                                i2,
                                false,
                                true,
                                "setGameMode",
                                "com.android.server.app.GameManagerService");
                if (!this.mSettings.containsKey(Integer.valueOf(handleIncomingUser))) {
                    Slog.d(
                            "GameManagerService",
                            "Failed to set game mode for package "
                                    + str
                                    + " as user "
                                    + handleIncomingUser
                                    + " is not started");
                    return;
                }
                GameManagerSettings gameManagerSettings =
                        (GameManagerSettings)
                                this.mSettings.get(Integer.valueOf(handleIncomingUser));
                if (!gameManagerSettings.mGameModes.containsKey(str)
                        || (i3 = ((Integer) gameManagerSettings.mGameModes.get(str)).intValue())
                                == 0) {
                    i3 = 1;
                }
                gameManagerSettings.mGameModes.put(str, Integer.valueOf(i));
                if (i != i3
                        && (gamePackageConfiguration =
                                        (GamePackageConfiguration)
                                                gameManagerSettings.mConfigOverrides.get(str))
                                != null) {
                    float resolutionScalingFactor =
                            getResolutionScalingFactor(str, i3, handleIncomingUser);
                    GamePackageConfiguration.GameModeConfiguration
                            orAddDefaultGameModeConfiguration =
                                    gamePackageConfiguration.getOrAddDefaultGameModeConfiguration(
                                            i);
                    synchronized (orAddDefaultGameModeConfiguration) {
                        orAddDefaultGameModeConfiguration.mScaling = resolutionScalingFactor;
                    }
                }
                updateInterventions(i, handleIncomingUser, str);
                synchronized (this.mGameModeListenerLock) {
                    for (IGameModeListener iGameModeListener : this.mGameModeListeners.keySet()) {
                        Binder.allowBlocking(iGameModeListener.asBinder());
                        try {
                            iGameModeListener.onGameModeChanged(str, i3, i, handleIncomingUser);
                        } catch (RemoteException unused) {
                            Slog.w(
                                    "GameManagerService",
                                    "Cannot notify game mode change for listener added by "
                                            + this.mGameModeListeners.get(iGameModeListener));
                        }
                    }
                }
                sendUserMessage(handleIncomingUser, 1, 10000, "SET_GAME_MODE");
                sendUserMessage(handleIncomingUser, 6, 0, "SET_GAME_MODE");
                try {
                    i4 = this.mPackageManager.getPackageUidAsUser(str, handleIncomingUser);
                } catch (PackageManager.NameNotFoundException unused2) {
                    Slog.d(
                            "GameManagerService",
                            "Cannot find the UID for package "
                                    + str
                                    + " under user "
                                    + handleIncomingUser);
                    i4 = -1;
                }
                Slog.d("GameManagerService", "setGameMode: fromGameMode " + i3 + "toGameMode " + i);
                FrameworkStatsLog.write(
                        FrameworkStatsLog.GAME_MODE_CHANGED,
                        i4,
                        Binder.getCallingUid(),
                        gameModeToStatsdGameMode(i3),
                        gameModeToStatsdGameMode(i));
            } finally {
            }
        }
    }

    public void setGameModeConfigOverride(String str, int i, int i2, String str2, String str3)
            throws SecurityException {
        int i3;
        checkPermission("android.permission.MANAGE_GAME_MODE");
        try {
            i3 = this.mPackageManager.getPackageUidAsUser(str, i);
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.d(
                    "GameManagerService",
                    "Cannot find the UID for package " + str + " under user " + i);
            i3 = -1;
        }
        int i4 = i3;
        GamePackageConfiguration config = getConfig(i, str);
        if (config == null || config.getGameModeConfiguration(i2) == null) {
            FrameworkStatsLog.write(
                    FrameworkStatsLog.GAME_MODE_CONFIGURATION_CHANGED,
                    i4,
                    Binder.getCallingUid(),
                    gameModeToStatsdGameMode(i2),
                    -1.0f,
                    str3 == null ? -1.0f : Float.parseFloat(str3),
                    0,
                    str2 == null ? 0 : Integer.parseInt(str2));
        } else {
            GamePackageConfiguration.GameModeConfiguration gameModeConfiguration =
                    config.getGameModeConfiguration(i2);
            FrameworkStatsLog.write(
                    FrameworkStatsLog.GAME_MODE_CONFIGURATION_CHANGED,
                    i4,
                    Binder.getCallingUid(),
                    gameModeToStatsdGameMode(i2),
                    gameModeConfiguration.getScaling(),
                    str3 == null ? gameModeConfiguration.getScaling() : Float.parseFloat(str3),
                    gameModeConfiguration.getFps(),
                    str2 == null ? gameModeConfiguration.getFps() : Integer.parseInt(str2));
        }
        synchronized (this.mLock) {
            try {
                if (this.mSettings.containsKey(Integer.valueOf(i))) {
                    GameManagerSettings gameManagerSettings =
                            (GameManagerSettings) this.mSettings.get(Integer.valueOf(i));
                    GamePackageConfiguration gamePackageConfiguration =
                            (GamePackageConfiguration)
                                    gameManagerSettings.mConfigOverrides.get(str);
                    if (gamePackageConfiguration == null) {
                        gamePackageConfiguration = new GamePackageConfiguration(str);
                        gameManagerSettings.mConfigOverrides.put(str, gamePackageConfiguration);
                    }
                    GamePackageConfiguration.GameModeConfiguration
                            orAddDefaultGameModeConfiguration =
                                    gamePackageConfiguration.getOrAddDefaultGameModeConfiguration(
                                            i2);
                    if (str2 != null) {
                        synchronized (orAddDefaultGameModeConfiguration) {
                            orAddDefaultGameModeConfiguration.mFps = str2;
                        }
                    } else {
                        synchronized (orAddDefaultGameModeConfiguration) {
                            orAddDefaultGameModeConfiguration.mFps = "";
                        }
                    }
                    if (str3 != null) {
                        float parseFloat = Float.parseFloat(str3);
                        synchronized (orAddDefaultGameModeConfiguration) {
                            orAddDefaultGameModeConfiguration.mScaling = parseFloat;
                        }
                    }
                    StringBuilder m =
                            DumpUtils$$ExternalSyntheticOutline0.m("Package Name: ", str, " FPS: ");
                    m.append(String.valueOf(orAddDefaultGameModeConfiguration.getFps()));
                    m.append(" Scaling: ");
                    m.append(orAddDefaultGameModeConfiguration.getScaling());
                    Slog.i("GameManagerService", m.toString());
                    setGameMode(str, i2, i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setGameModeFrameRateOverride(int i, float f) {
        nativeSetGameModeFrameRateOverride(i, f);
    }

    public final void setGameServiceProvider(String str) {
        checkPermission("android.permission.SET_GAME_SERVICE");
        GameServiceController gameServiceController = this.mGameServiceController;
        if (gameServiceController != null
                && (!Objects.equals(gameServiceController.mGameServiceProviderOverride, str))) {
            gameServiceController.mGameServiceProviderOverride = str;
            gameServiceController.mBackgroundExecutor.execute(
                    new GameServiceController$$ExternalSyntheticLambda0(gameServiceController));
        }
    }

    public final void setGameState(String str, GameState gameState, int i) {
        if (!isPackageGame(i, str)) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    "No-op for attempt to set game state for non-game app: ",
                    str,
                    "GameManagerService");
            return;
        }
        Message obtainMessage = this.mHandler.obtainMessage(4);
        Bundle bundle = new Bundle();
        bundle.putString("packageName", str);
        bundle.putInt("userId", i);
        obtainMessage.setData(bundle);
        obtainMessage.obj = gameState;
        this.mHandler.sendMessage(obtainMessage);
    }

    public final void toggleGameDefaultFrameRate(boolean z) {
        toggleGameDefaultFrameRate_enforcePermission();
        if (Flags.gameDefaultFrameRate()) {
            Slog.v("GameManagerService", "toggleGameDefaultFrameRate : " + z);
            synchronized (this.mLock) {
                try {
                    if (z) {
                        this.mSysProps.getClass();
                        SystemProperties.set(
                                "debug.graphics.game_default_frame_rate.disabled", "false");
                    } else {
                        this.mSysProps.getClass();
                        SystemProperties.set(
                                "debug.graphics.game_default_frame_rate.disabled", "true");
                    }
                } finally {
                }
            }
            synchronized (this.mUidObserverLock) {
                try {
                    Iterator it = ((HashSet) this.mGameForegroundUids).iterator();
                    while (it.hasNext()) {
                        int intValue = ((Integer) it.next()).intValue();
                        boolean gameDefaultFrameRate = Flags.gameDefaultFrameRate();
                        float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
                        if (gameDefaultFrameRate && z) {
                            f = this.mGameDefaultFrameRateValue;
                        }
                        setGameDefaultFrameRateOverride(intValue, f);
                    }
                } finally {
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:77:0x0069, code lost:

       r8 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0122, code lost:

       throw r8;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateConfigsForUser(int r9, boolean r10, java.lang.String... r11) {
        /*
            Method dump skipped, instructions count: 319
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.app.GameManagerService.updateConfigsForUser(int,"
                    + " boolean, java.lang.String[]):void");
    }

    public final void updateCustomGameModeConfiguration(
            String str, GameModeConfiguration gameModeConfiguration, int i) {
        int i2;
        checkPermission("android.permission.MANAGE_GAME_MODE");
        if (!isPackageGame(i, str)) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    "No-op for attempt to update custom game mode for non-game app: ",
                    str,
                    "GameManagerService");
            return;
        }
        synchronized (this.mLock) {
            if (!this.mSettings.containsKey(Integer.valueOf(i))) {
                throw new IllegalArgumentException("User " + i + " wasn't started");
            }
        }
        synchronized (this.mLock) {
            try {
                if (this.mSettings.containsKey(Integer.valueOf(i))) {
                    GameManagerSettings gameManagerSettings =
                            (GameManagerSettings) this.mSettings.get(Integer.valueOf(i));
                    GamePackageConfiguration gamePackageConfiguration =
                            (GamePackageConfiguration)
                                    gameManagerSettings.mConfigOverrides.get(str);
                    if (gamePackageConfiguration == null) {
                        gamePackageConfiguration = new GamePackageConfiguration(str);
                        gameManagerSettings.mConfigOverrides.put(str, gamePackageConfiguration);
                    }
                    GamePackageConfiguration.GameModeConfiguration
                            orAddDefaultGameModeConfiguration =
                                    gamePackageConfiguration.getOrAddDefaultGameModeConfiguration(
                                            4);
                    float scaling = orAddDefaultGameModeConfiguration.getScaling();
                    int fps = orAddDefaultGameModeConfiguration.getFps();
                    orAddDefaultGameModeConfiguration.mScaling =
                            gameModeConfiguration.getScalingFactor();
                    orAddDefaultGameModeConfiguration.mFps =
                            String.valueOf(gameModeConfiguration.getFpsOverride());
                    sendUserMessage(i, 1, 10000, "UPDATE_CUSTOM_GAME_MODE_CONFIG");
                    sendUserMessage(i, 6, 10000, "UPDATE_CUSTOM_GAME_MODE_CONFIG");
                    int gameMode = getGameMode(str, i);
                    if (gameMode == 4) {
                        updateInterventions(gameMode, i, str);
                    }
                    StringBuilder m =
                            DumpUtils$$ExternalSyntheticOutline0.m(
                                    "Updated custom game mode config for package: ",
                                    str,
                                    " with FPS=");
                    m.append(orAddDefaultGameModeConfiguration.getFps());
                    m.append(";Scaling=");
                    m.append(orAddDefaultGameModeConfiguration.getScaling());
                    m.append(" under user ");
                    m.append(i);
                    Slog.i("GameManagerService", m.toString());
                    try {
                        i2 = this.mPackageManager.getPackageUidAsUser(str, i);
                    } catch (PackageManager.NameNotFoundException unused) {
                        Slog.d(
                                "GameManagerService",
                                "Cannot find the UID for package " + str + " under user " + i);
                        i2 = -1;
                    }
                    FrameworkStatsLog.write(
                            FrameworkStatsLog.GAME_MODE_CONFIGURATION_CHANGED,
                            i2,
                            Binder.getCallingUid(),
                            gameModeToStatsdGameMode(4),
                            scaling,
                            gameModeConfiguration.getScalingFactor(),
                            fps,
                            gameModeConfiguration.getFpsOverride());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateInterventions(int i, int i2, String str) {
        GamePackageConfiguration config = getConfig(i2, str);
        if (i == 1
                || i == 0
                || config == null
                || config.willGamePerformOptimizations(i)
                || config.getGameModeConfiguration(i) == null) {
            try {
                setGameModeFrameRateOverride(
                        this.mPackageManager.getPackageUidAsUser(str, i2),
                        FullScreenMagnificationGestureHandler.MAX_SCALE);
            } catch (PackageManager.NameNotFoundException unused) {
            }
            if (config == null) {
                Slog.v("GameManagerService", "Package configuration not found for " + str);
                return;
            }
            return;
        }
        if (config.getGameModeConfiguration(i) != null) {
            try {
                setGameModeFrameRateOverride(
                        this.mPackageManager.getPackageUidAsUser(str, i2), r0.getFps());
            } catch (PackageManager.NameNotFoundException unused2) {
            }
        } else {
            Slog.d("GameManagerService", "Game mode " + i + " not found for " + str);
        }
    }

    public final void updateResolutionScalingFactor(String str, int i, float f, int i2) {
        checkPermission("android.permission.MANAGE_GAME_MODE");
        synchronized (this.mLock) {
            if (!this.mSettings.containsKey(Integer.valueOf(i2))) {
                throw new IllegalArgumentException("User " + i2 + " wasn't started");
            }
        }
        setGameModeConfigOverride(str, i2, i, null, Float.toString(f));
    }
}
