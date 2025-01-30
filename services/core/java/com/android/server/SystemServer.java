package com.android.server;

import android.R;
import android.app.ActivityThread;
import android.app.AppCompatCallbacks;
import android.app.AppGlobals;
import android.app.ApplicationErrorReport;
import android.app.ContextImpl;
import android.app.IActivityManager;
import android.app.INotificationManager;
import android.app.SystemServiceRegistry;
import android.app.admin.DevicePolicySafetyChecker;
import android.app.usage.UsageStatsManagerInternal;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteCompatibilityWalFlags;
import android.database.sqlite.SQLiteGlobal;
import android.graphics.GraphicsStatsService;
import android.graphics.Typeface;
import android.hardware.display.DisplayManagerInternal;
import android.location.ICountryDetector;
import android.media.IMediaRouterService;
import android.net.ConnectivityManager;
import android.net.ConnectivityModuleConnector;
import android.net.INetworkPolicyManager;
import android.net.IVpnManager;
import android.net.NetworkStackClient;
import android.net.vcn.IVcnManagementService;
import android.os.ArtModuleServiceManager;
import android.os.BaseBundle;
import android.os.Binder;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.os.FactoryTest;
import android.os.FileUtils;
import android.os.IBinder;
import android.os.IIncidentManager;
import android.os.INetworkManagementService;
import android.os.IServiceCreator;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.storage.IStorageManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Dumpable;
import android.util.EventLog;
import android.util.IndentingPrintWriter;
import android.util.Pair;
import android.util.Slog;
import android.util.TimeUtils;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.os.BinderInternal;
import com.android.internal.os.RuntimeInit;
import com.android.internal.policy.AttributeCache;
import com.android.internal.telephony.ITelephonyRegistry;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.LockSettingsInternal;
import com.android.server.BinderCallsStatsService;
import com.android.server.LooperStatsService;
import com.android.server.NetworkScoreService;
import com.android.server.TelephonyRegistry;
import com.android.server.VaultKeeperService;
import com.android.server.ambientcontext.AmbientContextManagerService;
import com.android.server.appbinding.AppBindingService;
import com.android.server.appprelauncher.AppPrelaunchManagerService;
import com.android.server.art.ArtModuleServiceInitializer;
import com.android.server.art.DexUseManagerLocal;
import com.android.server.asks.ASKSManagerService;
import com.android.server.attention.AttentionManagerService;
import com.android.server.audio.AudioService;
import com.android.server.biometrics.AuthService;
import com.android.server.biometrics.BiometricService;
import com.android.server.biometrics.sensors.iris.IrisService;
import com.android.server.broadcastradio.BroadcastRadioService;
import com.android.server.camera.CameraServiceProxy;
import com.android.server.chimera.ChimeraManagerService;
import com.android.server.clipboard.ClipboardService;
import com.android.server.compat.PlatformCompat;
import com.android.server.compat.PlatformCompatNative;
import com.android.server.connectivity.IpConnectivityMetrics;
import com.android.server.connectivity.PacProxyService;
import com.android.server.contentcapture.ContentCaptureManagerInternal;
import com.android.server.coverage.CoverageService;
import com.android.server.cpu.CpuMonitorService;
import com.android.server.desktopmode.DesktopModeService;
import com.android.server.devicepolicy.DevicePolicyManagerService;
import com.android.server.devicestate.DeviceStateManagerService;
import com.android.server.display.DisplayManagerService;
import com.android.server.display.color.ColorDisplayService;
import com.android.server.display.exynos.ExynosDisplaySolutionManagerService;
import com.android.server.dreams.DreamManagerService;
import com.android.server.emailksproxy.EmailKeystoreService;
import com.android.server.emergency.EmergencyAffordanceService;
import com.android.server.enterprise.EnterpriseDeviceManagerService;
import com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.container.KnoxMUMContainerPolicy;
import com.android.server.enterprise.container.KnoxMUMRCPPolicyService;
import com.android.server.enterprise.ucm.UniversalCredentialManagerService;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnEngineService;
import com.android.server.gpu.GpuService;
import com.android.server.grammaticalinflection.GrammaticalInflectionService;
import com.android.server.graphics.fonts.FontManagerService;
import com.android.server.hdmi.HdmiControlService;
import com.android.server.ibs.IntelligentBatterySaverService;
import com.android.server.incident.IncidentCompanionService;
import com.android.server.input.InputManagerService;
import com.android.server.inputmethod.InputMethodManagerService;
import com.android.server.integrity.AppIntegrityManagerService;
import com.android.server.knox.dar.DarManagerService;
import com.android.server.ledcover.LedBackCoverService;
import com.android.server.ledcoverapp.LedCoverAppService;
import com.android.server.lights.LightsService;
import com.android.server.locales.LocaleManagerService;
import com.android.server.location.LocationManagerService;
import com.android.server.location.altitude.AltitudeService;
import com.android.server.logcat.LogcatManagerService;
import com.android.server.media.MediaRouterService;
import com.android.server.media.metrics.MediaMetricsManagerService;
import com.android.server.media.projection.MediaProjectionManagerService;
import com.android.server.multicontrol.MultiControlManagerService;
import com.android.server.net.NetworkManagementService;
import com.android.server.net.NetworkPolicyManagerService;
import com.android.server.net.UrspService;
import com.android.server.net.watchlist.NetworkWatchlistService;
import com.android.server.notification.NotificationManagerService;
import com.android.server.oemlock.OemLockService;
import com.android.server.am.ActivityManagerService;
import com.android.server.om.OverlayManagerService;
import com.android.server.os.BugreportManagerService;
import com.android.server.os.DeviceIdentifiersPolicyService;
import com.android.server.os.NativeTombstoneManagerService;
import com.android.server.os.SchedulingPolicyService;
import com.android.server.pm.ApexManager;
import com.android.server.pm.ApexSystemServiceInfo;
import com.android.server.pm.BackgroundInstallControlService;
import com.android.server.pm.CrossProfileAppsService;
import com.android.server.pm.DataLoaderManagerService;
import com.android.server.pm.DexOptHelper;
import com.android.server.pm.DynamicCodeLoggingService;
import com.android.server.pm.Installer;
import com.android.server.pm.LauncherAppsService;
import com.android.server.pm.OtaDexoptService;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PersonaManagerService;
import com.android.server.pm.ShortcutService;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.dex.OdsignStatsLogger;
import com.android.server.pm.verify.domain.DomainVerificationService;
import com.android.server.tv.TvInputManagerService;
import com.android.server.tv.TvRemoteService;
import com.android.server.tv.interactive.TvInteractiveAppManagerService;
import com.android.server.tv.tunerresourcemanager.TunerResourceManagerService;
import com.android.server.vr.VrManagerService;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerService;
import com.android.server.pdp.PdpService;
import com.android.server.people.PeopleService;
import com.android.server.permission.access.AccessCheckingService;
import com.android.server.policy.AppOpsPolicy;
import com.android.server.policy.PermissionPolicyService;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.policy.role.RoleServicePlatformHelperImpl;
import com.android.server.power.PowerManagerService;
import com.android.server.power.ShutdownThread;
import com.android.server.power.ThermalManagerService;
import com.android.server.power.hint.HintManagerService;
import com.android.server.powerstats.PowerStatsService;
import com.android.server.profcollect.ProfcollectForwardingService;
import com.android.server.recoverysystem.RecoverySystemService;
import com.android.server.remoteappmode.RemoteAppModeService;
import com.android.server.resources.ResourcesManagerService;
import com.android.server.restrictions.RestrictionsManagerService;
import com.android.server.role.RoleServicePlatformHelper;
import com.android.server.rotationresolver.RotationResolverManagerService;
import com.android.server.samsungnotes.SamsungNotesService;
import com.android.server.security.AttestationVerificationManagerService;
import com.android.server.security.FileIntegrityService;
import com.android.server.security.KeyAttestationApplicationIdProviderService;
import com.android.server.security.KeyChainSystemService;
import com.android.server.security.rkp.RemoteProvisioningService;
import com.android.server.semclipboard.SemClipboardService;
import com.android.server.sensorprivacy.SensorPrivacyService;
import com.android.server.sensors.SensorService;
import com.android.server.sepunion.SemUnionMainService;
import com.android.server.shealth.SamsungHealthService;
import com.android.server.signedconfig.SignedConfigService;
import com.android.server.smartclip.SpenGestureManagerService;
import com.android.server.smartthings.SmartThingsService;
import com.android.server.soundtrigger.SoundTriggerService;
import com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareService;
import com.android.server.statusbar.StatusBarManagerService;
import com.android.server.storage.DeviceStorageMonitorService;
import com.android.server.telecom.TelecomLoaderService;
import com.android.server.testharness.TestHarnessModeService;
import com.android.server.textclassifier.TextClassificationManagerService;
import com.android.server.textservices.TextServicesManagerService;
import com.android.server.timedetector.NetworkTimeUpdateService;
import com.android.server.tracing.TracingServiceProxy;
import com.android.server.trust.TrustManagerService;
import com.android.server.twilight.TwilightService;
import com.android.server.uri.UriGrantsManagerService;
import com.android.server.usage.UsageStatsService;
import com.android.server.utils.TimingsTraceAndSlog;
import com.android.server.vibrator.VibratorManagerService;
import com.android.server.voicenote.VoiceNoteService;
import com.android.server.wearable.WearableSensingManagerService;
import com.android.server.webkit.WebViewUpdateService;
import com.samsung.accessory.manager.SAccessoryManager;
import com.samsung.accessory.manager.SAccessoryManagerInternal;
import com.samsung.android.authnrservice.service.SemAuthnrService;
import com.samsung.android.battauthmanager.BattAuthManager;
import com.samsung.android.camera.CameraServiceWorker;
import com.samsung.android.displayquality.SemDisplayQualityFeature;
import com.samsung.android.displayquality.SemDisplayQualityManagerService;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.jdsms.DsmsService;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.ISemPersonaManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.service.KnoxAnalyticsSystemService;
import com.samsung.android.knox.container.IKnoxContainerManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.net.vpn.IKnoxVpnPolicy;
import com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager;
import com.samsung.android.knoxguard.service.KnoxGuardSeService;
import com.samsung.android.knoxguard.service.KnoxGuardService;
import com.samsung.android.localeoverlaymanager.LocaleOverlayManagerWrapper;
import com.samsung.android.media.codec.VideoTranscodingService;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.security.SemSdCardEncryption;
import com.samsung.android.security.mdf.MdfUtils;
import com.samsung.android.sepunion.UnionUtils;
import com.samsung.android.server.continuity.SemContinuityService;
import com.samsung.android.server.hwrs.SemHwrsService;
import com.samsung.iqi.IQIServiceBrokerExt;
import com.samsung.ucm.ucmservice.CredentialManagerService;
import dalvik.system.PathClassLoader;
import dalvik.system.VMRuntime;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/* loaded from: classes.dex */
public final class SystemServer implements Dumpable {
    public static final File HEAP_DUMP_PATH = new File("/data/system/heapdump/");
    public static LinkedList sPendingWtfs;
    public ASKSManagerService mASKSManagerService;
    public ActivityManagerService mActivityManagerService;
    public ContentResolver mContentResolver;
    public DataLoaderManagerService mDataLoaderManagerService;
    public DisplayManagerService mDisplayManagerService;
    public EntropyMixer mEntropyMixer;
    public boolean mFirstBoot;
    public LedBackCoverService mLedBackCoverService;
    public LedCoverAppService mLedCoverAppService;
    public PackageManager mPackageManager;
    public PackageManagerService mPackageManagerService;
    public PowerManagerService mPowerManagerService;
    public final boolean mRuntimeRestart;
    public final long mRuntimeStartElapsedTime;
    public final long mRuntimeStartUptime;
    public SamsungHealthService mSHealthService;
    public SamsungNotesService mSamsungNotesService;
    public SmartThingsService mSmartThingsService;
    public final int mStartCount;
    public Context mSystemContext;
    public SystemServiceManager mSystemServiceManager;
    public VoiceNoteService mVoiceNoteService;
    public WebViewUpdateService mWebViewUpdateService;
    public WindowManagerGlobalLock mWindowManagerGlobalLock;
    public Future mZygotePreload;
    public EnterpriseDeviceManagerService enterprisePolicy = null;
    public KnoxCustomManagerService knoxCustomPolicy = null;
    public long mIncrementalServiceHandle = 0;
    public DualAppManagerService mDualAppService = null;
    public SAccessoryManager sAccessoryManager = null;
    public BattAuthManager mBattAuthManager = null;
    public final SystemServerDumper mDumper = new SystemServerDumper();
    public final int mFactoryTestMode = FactoryTest.getMode();

    private static native void fdtrackAbort();

    private static native void initZygoteChildHeapProfiling();

    private static native void setIncrementalServiceSystemReady(long j);

    private static native void startHidlServices();

    private static native void startISensorManagerService();

    private static native void startIStatsService();

    private static native long startIncrementalService();

    private static native void startMemtrackProxyService();

    public static int getMaxFd() {
        FileDescriptor fileDescriptor = null;
        try {
            try {
                fileDescriptor = Os.open("/dev/null", OsConstants.O_RDONLY | OsConstants.O_CLOEXEC, 0);
                int int$ = fileDescriptor.getInt$();
                try {
                    Os.close(fileDescriptor);
                    return int$;
                } catch (ErrnoException e) {
                    throw new RuntimeException(e);
                }
            } catch (ErrnoException e2) {
                Slog.e("System", "Failed to get maximum fd: " + e2);
                if (fileDescriptor == null) {
                    return Integer.MAX_VALUE;
                }
                try {
                    Os.close(fileDescriptor);
                    return Integer.MAX_VALUE;
                } catch (ErrnoException e3) {
                    throw new RuntimeException(e3);
                }
            }
        } catch (Throwable th) {
            if (fileDescriptor != null) {
                try {
                    Os.close(fileDescriptor);
                } catch (ErrnoException e4) {
                    throw new RuntimeException(e4);
                }
            }
            throw th;
        }
    }

    public static void dumpHprof() {
        TreeSet treeSet = new TreeSet();
        for (File file : HEAP_DUMP_PATH.listFiles()) {
            if (file.isFile() && file.getName().startsWith("fdtrack-")) {
                treeSet.add(file);
            }
        }
        if (treeSet.size() >= 2) {
            treeSet.pollLast();
            Iterator it = treeSet.iterator();
            while (it.hasNext()) {
                File file2 = (File) it.next();
                if (!file2.delete()) {
                    Slog.w("System", "Failed to clean up hprof " + file2);
                }
            }
        }
        try {
            Debug.dumpHprofData("/data/system/heapdump/fdtrack-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".hprof");
        } catch (IOException e) {
            Slog.e("System", "Failed to dump fdtrack hprof", e);
        }
    }

    public static void spawnFdLeakCheckThread() {
        final int i = SystemProperties.getInt("persist.sys.debug.fdtrack_enable_threshold", 1600);
        final int i2 = SystemProperties.getInt("persist.sys.debug.fdtrack_abort_threshold", 3000);
        final int i3 = SystemProperties.getInt("persist.sys.debug.fdtrack_interval", 120);
        new Thread(new Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SystemServer.lambda$spawnFdLeakCheckThread$0(i, i2, i3);
            }
        }).start();
    }

    public static /* synthetic */ void lambda$spawnFdLeakCheckThread$0(int i, int i2, int i3) {
        boolean z = false;
        long j = 0;
        while (true) {
            int maxFd = getMaxFd();
            if (maxFd > i) {
                System.gc();
                System.runFinalization();
                maxFd = getMaxFd();
            }
            if (maxFd > i && !z) {
                Slog.i("System", "fdtrack enable threshold reached, enabling");
                FrameworkStatsLog.write(364, 2, maxFd);
                System.loadLibrary("fdtrack");
                z = true;
            } else if (maxFd > i2) {
                Slog.i("System", "fdtrack abort threshold reached, dumping and aborting");
                FrameworkStatsLog.write(364, 3, maxFd);
                dumpHprof();
                fdtrackAbort();
            } else {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (elapsedRealtime > j) {
                    long j2 = elapsedRealtime + ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
                    FrameworkStatsLog.write(364, z ? 2 : 1, maxFd);
                    j = j2;
                }
            }
            try {
                Thread.sleep(i3 * 1000);
            } catch (InterruptedException unused) {
            }
        }
    }

    public static void main(String[] strArr) {
        new SystemServer().run();
    }

    public SystemServer() {
        int i = SystemProperties.getInt("sys.system_server.start_count", 0) + 1;
        this.mStartCount = i;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mRuntimeStartElapsedTime = elapsedRealtime;
        long uptimeMillis = SystemClock.uptimeMillis();
        this.mRuntimeStartUptime = uptimeMillis;
        Process.setStartTimes(elapsedRealtime, uptimeMillis, elapsedRealtime, uptimeMillis);
        this.mRuntimeRestart = i > 1;
    }

    @Override // android.util.Dumpable
    public String getDumpableName() {
        return SystemServer.class.getSimpleName();
    }

    @Override // android.util.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.printf("Runtime restart: %b\n", Boolean.valueOf(this.mRuntimeRestart));
        printWriter.printf("Start count: %d\n", Integer.valueOf(this.mStartCount));
        printWriter.print("Runtime start-up time: ");
        TimeUtils.formatDuration(this.mRuntimeStartUptime, printWriter);
        printWriter.println();
        printWriter.print("Runtime start-elapsed time: ");
        TimeUtils.formatDuration(this.mRuntimeStartElapsedTime, printWriter);
        printWriter.println();
    }

    public final class SystemServerDumper extends Binder {
        public final ArrayMap mDumpables;

        public SystemServerDumper() {
            this.mDumpables = new ArrayMap(4);
        }

        @Override // android.os.Binder
        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            IndentingPrintWriter indentingPrintWriter;
            boolean z = strArr != null && strArr.length > 0;
            synchronized (this.mDumpables) {
                if (z) {
                    try {
                        if ("--list".equals(strArr[0])) {
                            int size = this.mDumpables.size();
                            for (int i = 0; i < size; i++) {
                                printWriter.println((String) this.mDumpables.keyAt(i));
                            }
                            return;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (z && "--name".equals(strArr[0])) {
                    if (strArr.length < 2) {
                        printWriter.println("Must pass at least one argument to --name");
                        return;
                    }
                    String str = strArr[1];
                    Dumpable dumpable = (Dumpable) this.mDumpables.get(str);
                    if (dumpable == null) {
                        printWriter.printf("No dumpable named %s\n", str);
                        return;
                    }
                    indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
                    try {
                        dumpable.dump(indentingPrintWriter, (String[]) Arrays.copyOfRange(strArr, 2, strArr.length));
                        indentingPrintWriter.close();
                        return;
                    } finally {
                    }
                }
                int size2 = this.mDumpables.size();
                indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
                for (int i2 = 0; i2 < size2; i2++) {
                    try {
                        Dumpable dumpable2 = (Dumpable) this.mDumpables.valueAt(i2);
                        indentingPrintWriter.printf("%s:\n", new Object[]{dumpable2.getDumpableName()});
                        indentingPrintWriter.increaseIndent();
                        dumpable2.dump(indentingPrintWriter, strArr);
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println();
                    } finally {
                    }
                }
                indentingPrintWriter.close();
                return;
                throw th;
            }
        }

        public final void addDumpable(Dumpable dumpable) {
            synchronized (this.mDumpables) {
                this.mDumpables.put(dumpable.getDumpableName(), dumpable);
            }
        }
    }

    public final void run() {
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
        try {
            timingsTraceAndSlog.traceBegin("InitBeforeStartServices");
            SystemProperties.set("sys.system_server.start_count", String.valueOf(this.mStartCount));
            SystemProperties.set("sys.system_server.start_elapsed", String.valueOf(this.mRuntimeStartElapsedTime));
            SystemProperties.set("sys.system_server.start_uptime", String.valueOf(this.mRuntimeStartUptime));
            SystemProperties.set("sys.system_server.pid", String.valueOf(Process.myPid()));
            Slog.i("SystemServer", "!@Boot_EBS_F: SYSTEM_SERVER_START");
            EventLog.writeEvent(3011, Integer.valueOf(this.mStartCount), Long.valueOf(this.mRuntimeStartUptime), Long.valueOf(this.mRuntimeStartElapsedTime));
            SystemTimeZone.initializeTimeZoneSettingsIfRequired();
            if (!SystemProperties.get("persist.sys.language").isEmpty()) {
                SystemProperties.set("persist.sys.locale", Locale.getDefault().toLanguageTag());
                SystemProperties.set("persist.sys.language", "");
                SystemProperties.set("persist.sys.country", "");
                SystemProperties.set("persist.sys.localevar", "");
            }
            Binder.setSystemServerProcess();
            Binder.setWarnOnBlocking(true);
            PackageItemInfo.forceSafeLabels();
            SQLiteGlobal.sDefaultSyncMode = "FULL";
            SQLiteCompatibilityWalFlags.init((String) null);
            Slog.i("SystemServer", "Entered the Android system server!");
            Slog.i("SystemServer", "!@Boot: Entered the Android system server!");
            Slog.i("SystemServer", "!@Boot_EBS_F: BOOT_PROGRESS_SYSTEM_RUN");
            long elapsedRealtime = SystemClock.elapsedRealtime();
            EventLog.writeEvent(3010, elapsedRealtime);
            if (!this.mRuntimeRestart) {
                FrameworkStatsLog.write(240, 19, elapsedRealtime);
            }
            SystemProperties.set("persist.sys.dalvik.vm.lib.2", VMRuntime.getRuntime().vmLibrary());
            VMRuntime.getRuntime().clearGrowthLimit();
            Build.ensureFingerprintProperty();
            Environment.setUserRequired(true);
            BaseBundle.setShouldDefuse(true);
            Parcel.setStackTraceParceling(true);
            BinderInternal.disableBackgroundScheduling(true);
            BinderInternal.setMaxThreads(31);
            Process.setThreadPriority(-2);
            Process.setCanSelfBackground(false);
            Looper.prepareMainLooper();
            Looper.getMainLooper().setSlowLogThresholdMs(100L, 200L);
            SystemServiceRegistry.sEnableServiceNotFoundWtf = true;
            System.loadLibrary("android_servers");
            initZygoteChildHeapProfiling();
            if (Build.IS_DEBUGGABLE) {
                spawnFdLeakCheckThread();
            }
            performPendingShutdown();
            createSystemContext();
            ActivityThread.initializeMainlineModules();
            ServiceManager.addService("system_server_dumper", this.mDumper);
            this.mDumper.addDumpable(this);
            SystemServiceManager systemServiceManager = new SystemServiceManager(this.mSystemContext);
            this.mSystemServiceManager = systemServiceManager;
            systemServiceManager.setStartInfo(this.mRuntimeRestart, this.mRuntimeStartElapsedTime, this.mRuntimeStartUptime);
            this.mDumper.addDumpable(this.mSystemServiceManager);
            LocalServices.addService(SystemServiceManager.class, this.mSystemServiceManager);
            this.mDumper.addDumpable(SystemServerInitThreadPool.start());
            Typeface.loadPreinstalledSystemFontMap();
            if (Build.IS_DEBUGGABLE) {
                String str = SystemProperties.get("persist.sys.dalvik.jvmtiagent");
                if (!str.isEmpty()) {
                    int indexOf = str.indexOf(61);
                    try {
                        Debug.attachJvmtiAgent(str.substring(0, indexOf), str.substring(indexOf + 1, str.length()), null);
                    } catch (Exception unused) {
                        Slog.e("System", "*************************************************");
                        Slog.e("System", "********** Failed to load jvmti plugin: " + str);
                    }
                }
            }
            timingsTraceAndSlog.traceEnd();
            RuntimeInit.setDefaultApplicationWtfHandler(new RuntimeInit.ApplicationWtfHandler() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda0
                public final boolean handleApplicationWtf(IBinder iBinder, String str2, boolean z, ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo, int i) {
                    boolean handleEarlySystemWtf;
                    handleEarlySystemWtf = SystemServer.handleEarlySystemWtf(iBinder, str2, z, parcelableCrashInfo, i);
                    return handleEarlySystemWtf;
                }
            });
            try {
                timingsTraceAndSlog.traceBegin("StartServices");
                Slog.i("SystemServer", "!@Boot_EBS_F: startBootstrapServices");
                startBootstrapServices(timingsTraceAndSlog);
                Slog.i("SystemServer", "!@Boot_EBS_F: startCoreServices");
                startCoreServices(timingsTraceAndSlog);
                Slog.i("SystemServer", "!@Boot_EBS_F: startOtherServices");
                startOtherServices(timingsTraceAndSlog);
                startApexServices(timingsTraceAndSlog);
                updateWatchdogTimeout(timingsTraceAndSlog);
                timingsTraceAndSlog.traceEnd();
                StrictMode.initVmDefaults(null);
                try {
                    if (AppGlobals.getPackageManager().getApplicationInfo("com.samsung.android.voc", 128L, UserHandle.getCallingUserId()) != null) {
                        SystemProperties.set("sys.members.installed", "true");
                    }
                } catch (RemoteException unused2) {
                }
                if (!this.mRuntimeRestart && !isFirstBootOrUpgrade()) {
                    long elapsedRealtime2 = SystemClock.elapsedRealtime();
                    FrameworkStatsLog.write(240, 20, elapsedRealtime2);
                    if (elapsedRealtime2 > 60000) {
                        Slog.wtf("SystemServerTiming", "SystemServer init took too long. uptimeMillis=" + elapsedRealtime2);
                    }
                }
                if (CoreRune.SYSPERF_BOOST_OPT) {
                    final int myTid = Process.myTid();
                    new Timer().schedule(new TimerTask() { // from class: com.android.server.SystemServer.1
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            Process.setThreadGroupAndCpuset(myTid, 10);
                        }
                    }, 30000L);
                }
                Slog.i("SystemServer", "!@Boot: Loop forever");
                Slog.i("SystemServer", "!@Boot_EBS_D: Loop forever");
                Looper.loop();
                throw new RuntimeException("Main thread loop unexpectedly exited");
            } finally {
            }
        } finally {
        }
    }

    public final boolean isFirstBootOrUpgrade() {
        return this.mPackageManagerService.isFirstBoot() || this.mPackageManagerService.isDeviceUpgrading();
    }

    public final void reportWtf(String str, Throwable th) {
        Slog.w("SystemServer", "***********************************************");
        Slog.wtf("SystemServer", "BOOT FAILURE " + str, th);
    }

    public final void performPendingShutdown() {
        String str = SystemProperties.get("sys.shutdown.requested", "");
        if (str == null || str.length() <= 0) {
            return;
        }
        final boolean z = str.charAt(0) == '1';
        String str2 = null;
        final String substring = str.length() > 1 ? str.substring(1, str.length()) : null;
        if (substring != null && substring.startsWith("recovery-update")) {
            File file = new File("/cache/recovery/uncrypt_file");
            if (file.exists()) {
                try {
                    str2 = FileUtils.readTextFile(file, 0, null);
                } catch (IOException e) {
                    Slog.e("SystemServer", "Error reading uncrypt package file", e);
                }
                if (str2 != null && str2.startsWith("/data") && !new File("/cache/recovery/block.map").exists()) {
                    Slog.e("SystemServer", "Can't find block map file, uncrypt failed or unexpected runtime restart?");
                    return;
                }
            }
        }
        Message obtain = Message.obtain(UiThread.getHandler(), new Runnable() { // from class: com.android.server.SystemServer.2
            @Override // java.lang.Runnable
            public void run() {
                ShutdownThread.rebootOrShutdown(null, z, substring);
            }
        });
        obtain.setAsynchronous(true);
        UiThread.getHandler().sendMessage(obtain);
    }

    public final void createSystemContext() {
        ActivityThread systemMain = ActivityThread.systemMain();
        ContextImpl systemContext = systemMain.getSystemContext();
        this.mSystemContext = systemContext;
        systemContext.setTheme(R.style.Theme.Leanback.Settings.Dialog.Alert);
        systemMain.getSystemUiContext().setTheme(R.style.Theme.Leanback.Settings.Dialog.Alert);
    }

    /* JADX WARN: Type inference failed for: r3v4, types: [android.os.IBinder, com.android.server.compat.PlatformCompat] */
    public final void startBootstrapServices(TimingsTraceAndSlog timingsTraceAndSlog) {
        timingsTraceAndSlog.traceBegin("startBootstrapServices");
        timingsTraceAndSlog.traceBegin("ArtModuleServiceInitializer");
        ArtModuleServiceInitializer.setArtModuleServiceManager(new ArtModuleServiceManager());
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartWatchdog");
        Watchdog watchdog = Watchdog.getInstance();
        watchdog.start();
        this.mDumper.addDumpable(watchdog);
        timingsTraceAndSlog.traceEnd();
        Slog.i("SystemServer", "Reading configuration...");
        timingsTraceAndSlog.traceBegin("ReadingSystemConfig");
        SystemServerInitThreadPool.submit(new Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SystemConfig.getInstance();
            }
        }, "ReadingSystemConfig");
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("PlatformCompat");
        ?? platformCompat = new PlatformCompat(this.mSystemContext);
        ServiceManager.addService("platform_compat", (IBinder) platformCompat);
        ServiceManager.addService("platform_compat_native", new PlatformCompatNative(platformCompat));
        AppCompatCallbacks.install(new long[0]);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartFileIntegrityService");
        this.mSystemServiceManager.startService(FileIntegrityService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartInstaller");
        Installer installer = (Installer) this.mSystemServiceManager.startService(Installer.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("DeviceIdentifiersPolicyService");
        this.mSystemServiceManager.startService(DeviceIdentifiersPolicyService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("UriGrantsManagerService");
        this.mSystemServiceManager.startService(UriGrantsManagerService.Lifecycle.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartPowerStatsService");
        this.mSystemServiceManager.startService(PowerStatsService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartIStatsService");
        startIStatsService();
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MemtrackProxyService");
        startMemtrackProxyService();
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartAccessCheckingService");
        this.mSystemServiceManager.startService(AccessCheckingService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartActivityManager");
        ActivityTaskManagerService service = ((ActivityTaskManagerService.Lifecycle) this.mSystemServiceManager.startService(ActivityTaskManagerService.Lifecycle.class)).getService();
        ActivityManagerService startService = ActivityManagerService.Lifecycle.startService(this.mSystemServiceManager, service);
        this.mActivityManagerService = startService;
        startService.setSystemServiceManager(this.mSystemServiceManager);
        this.mActivityManagerService.setInstaller(installer);
        this.mWindowManagerGlobalLock = service.getGlobalLock();
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartDataLoaderManagerService");
        this.mDataLoaderManagerService = (DataLoaderManagerService) this.mSystemServiceManager.startService(DataLoaderManagerService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartIncrementalService");
        this.mIncrementalServiceHandle = startIncrementalService();
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartPowerManager");
        this.mPowerManagerService = (PowerManagerService) this.mSystemServiceManager.startService(PowerManagerService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartThermalManager");
        this.mSystemServiceManager.startService(ThermalManagerService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartHintManager");
        this.mSystemServiceManager.startService(HintManagerService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("InitPowerManagement");
        this.mActivityManagerService.initPowerManagement();
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("VaultKeeperService");
        this.mSystemServiceManager.startService(VaultKeeperService.LifeCycle.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartRecoverySystemService");
        this.mSystemServiceManager.startService(RecoverySystemService.Lifecycle.class);
        timingsTraceAndSlog.traceEnd();
        RescueParty.registerHealthObserver(this.mSystemContext);
        PackageWatchdog.getInstance(this.mSystemContext).noteBoot();
        timingsTraceAndSlog.traceBegin("StartLightsService");
        this.mSystemServiceManager.startService(LightsService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartPdpService");
        this.mSystemServiceManager.startService(PdpService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartDisplayOffloadService");
        if (SystemProperties.getBoolean("config.enable_display_offload", false)) {
            this.mSystemServiceManager.startService("com.android.clockwork.displayoffload.DisplayOffloadService");
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartSidekickService");
        if (SystemProperties.getBoolean("config.enable_sidekick_graphics", false)) {
            this.mSystemServiceManager.startService("com.google.android.clockwork.sidekick.SidekickService");
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartDisplayManager");
        this.mDisplayManagerService = (DisplayManagerService) this.mSystemServiceManager.startService(DisplayManagerService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("WaitForDisplay");
        Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_WAIT_FOR_DEFAULT_DISPLAY");
        this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 100);
        timingsTraceAndSlog.traceEnd();
        if (!this.mRuntimeRestart) {
            FrameworkStatsLog.write(240, 14, SystemClock.elapsedRealtime());
        }
        timingsTraceAndSlog.traceBegin("StartDomainVerificationService");
        DomainVerificationService domainVerificationService = new DomainVerificationService(this.mSystemContext, SystemConfig.getInstance(), platformCompat);
        this.mSystemServiceManager.startService(domainVerificationService);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartPackageManagerService");
        Slog.i("SystemServer", "!@Boot: Start PackageManagerService");
        try {
            Watchdog.getInstance().pauseWatchingCurrentThread("packagemanagermain");
            this.mPackageManagerService = PackageManagerService.main(this.mSystemContext, installer, domainVerificationService, this.mFactoryTestMode != 0);
            Watchdog.getInstance().resumeWatchingCurrentThread("packagemanagermain");
            Slog.i("SystemServer", "!@Boot: End PackageManagerService");
            this.mFirstBoot = this.mPackageManagerService.isFirstBoot();
            this.mPackageManager = this.mSystemContext.getPackageManager();
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("DexUseManagerLocal");
            LocalManagerRegistry.addManager(DexUseManagerLocal.class, DexUseManagerLocal.createInstance(this.mSystemContext));
            timingsTraceAndSlog.traceEnd();
            if (!this.mRuntimeRestart && !isFirstBootOrUpgrade()) {
                FrameworkStatsLog.write(240, 15, SystemClock.elapsedRealtime());
            }
            timingsTraceAndSlog.traceBegin("StartASKSManagerService");
            this.mASKSManagerService = ASKSManagerService.main(this.mSystemContext);
            timingsTraceAndSlog.traceEnd();
            if (!SystemProperties.getBoolean("config.disable_otadexopt", false)) {
                timingsTraceAndSlog.traceBegin("StartOtaDexOptService");
                try {
                    Watchdog.getInstance().pauseWatchingCurrentThread("moveab");
                    OtaDexoptService.main(this.mSystemContext, this.mPackageManagerService);
                } finally {
                    try {
                    } finally {
                    }
                }
            }
            timingsTraceAndSlog.traceBegin("StartUserManagerService");
            this.mSystemServiceManager.startService(UserManagerService.LifeCycle.class);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("InitAttributerCache");
            AttributeCache.init(this.mSystemContext);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("SetSystemProcess");
            this.mActivityManagerService.setSystemProcess();
            timingsTraceAndSlog.traceEnd();
            platformCompat.registerPackageReceiver(this.mSystemContext);
            timingsTraceAndSlog.traceBegin("InitWatchdog");
            Slog.i("SystemServer", "!@Boot_EBS_D: InitWatchdog");
            watchdog.init(this.mSystemContext, this.mActivityManagerService);
            timingsTraceAndSlog.traceEnd();
            this.mDisplayManagerService.setupSchedulerPolicies();
            timingsTraceAndSlog.traceBegin("StartOverlayManagerService");
            this.mSystemServiceManager.startService(new OverlayManagerService(this.mSystemContext));
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartResourcesManagerService");
            ResourcesManagerService resourcesManagerService = new ResourcesManagerService(this.mSystemContext);
            resourcesManagerService.setActivityManagerService(this.mActivityManagerService);
            this.mSystemServiceManager.startService(resourcesManagerService);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartSensorPrivacyService");
            this.mSystemServiceManager.startService(new SensorPrivacyService(this.mSystemContext));
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("PACMService");
            this.mSystemServiceManager.startService("com.android.server.PACMService");
            timingsTraceAndSlog.traceEnd();
            if (SystemProperties.getInt("persist.sys.displayinset.top", 0) > 0) {
                this.mActivityManagerService.updateSystemUiContext();
                ((DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class)).onOverlayChanged();
            }
            timingsTraceAndSlog.traceBegin("StartSensorService");
            this.mSystemServiceManager.startService(SensorService.class);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceEnd();
        } catch (Throwable th) {
            Watchdog.getInstance().resumeWatchingCurrentThread("packagemanagermain");
            throw th;
        }
    }

    public final void startCoreServices(TimingsTraceAndSlog timingsTraceAndSlog) {
        timingsTraceAndSlog.traceBegin("startCoreServices");
        if (SystemProperties.getBoolean("persist.sys.enable_isrb", false)) {
            timingsTraceAndSlog.traceBegin("StartISRBService");
            this.mSystemServiceManager.startService("com.android.server.isrb.IsrbManagerService");
            timingsTraceAndSlog.traceEnd();
        }
        timingsTraceAndSlog.traceBegin("StartSystemConfigService");
        this.mSystemServiceManager.startService(SystemConfigService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartBatteryService");
        this.mSystemServiceManager.startService(BatteryService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartUsageService");
        this.mSystemServiceManager.startService(UsageStatsService.class);
        this.mActivityManagerService.setUsageStatsManager((UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class));
        timingsTraceAndSlog.traceEnd();
        if (this.mPackageManager.hasSystemFeature("android.software.webview")) {
            timingsTraceAndSlog.traceBegin("StartWebViewUpdateService");
            this.mWebViewUpdateService = (WebViewUpdateService) this.mSystemServiceManager.startService(WebViewUpdateService.class);
            timingsTraceAndSlog.traceEnd();
        }
        timingsTraceAndSlog.traceBegin("StartCachedDeviceStateService");
        this.mSystemServiceManager.startService(CachedDeviceStateService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartBinderCallsStatsService");
        this.mSystemServiceManager.startService(BinderCallsStatsService.LifeCycle.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartLooperStatsService");
        this.mSystemServiceManager.startService(LooperStatsService.Lifecycle.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartRollbackManagerService");
        this.mSystemServiceManager.startService("com.android.server.rollback.RollbackManagerService");
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartNativeTombstoneManagerService");
        this.mSystemServiceManager.startService(NativeTombstoneManagerService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartBugreportManagerService");
        this.mSystemServiceManager.startService(BugreportManagerService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("GpuService");
        this.mSystemServiceManager.startService(GpuService.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartRemoteProvisioningService");
        this.mSystemServiceManager.startService(RemoteProvisioningService.class);
        timingsTraceAndSlog.traceEnd();
        if (Build.IS_DEBUGGABLE || Build.IS_ENG) {
            timingsTraceAndSlog.traceBegin(CpuMonitorService.TAG);
            this.mSystemServiceManager.startService(CpuMonitorService.class);
            timingsTraceAndSlog.traceEnd();
        }
        timingsTraceAndSlog.traceEnd();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(140:9|10|(2:11|12)|13|(4:15|16|17|18)(1:1093)|19|(2:20|21)|22|(2:23|24)|25|26|(1:1077)|32|(2:33|34)|35|36|(9:1045|1046|1047|1048|1049|1050|1051|1052|1053)|38|(4:39|40|41|42)|(5:44|45|46|47|48)|49|(1:51)|52|(1:54)|55|(2:56|57)|58|59|(1:61)|62|(2:63|64)|65|(1:67)(1:1014)|68|(1:71)|72|(1:74)(2:1010|(1:1012)(1:1013))|75|(2:76|77)|78|(6:80|81|82|(1:84)(1:88)|85|86)|92|(1:96)|97|(2:98|99)|100|101|(3:989|990|991)|103|(1:105)(2:986|(1:988))|106|(6:108|(1:110)(5:974|975|976|977|978)|111|112|113|114)(1:985)|115|(2:116|117)|118|(4:122|123|124|125)|129|130|131|132|(2:133|134)|135|(2:136|137)|138|139|140|(1:142)(1:956)|143|144|(4:145|146|(1:148)(1:952)|149)|150|(6:152|153|154|(1:156)(1:160)|157|158)|163|(11:167|168|169|170|171|172|173|174|175|(1:177)|179)|188|(2:189|190)|191|(2:192|193)|194|(2:195|196)|197|(2:198|199)|200|(1:202)(236:353|354|355|356|(1:358)|359|(1:929)|363|364|365|(1:367)|369|370|371|372|373|(1:375)(1:923)|376|377|378|379|(2:915|916)(1:381)|382|383|384|385|386|387|388|(1:390)(1:908)|391|(1:393)(1:907)|394|395|396|397|398|(1:885)|402|403|404|(1:408)|410|411|412|413|414|415|416|417|418|419|420|421|(7:423|424|425|426|427|428|(4:431|432|433|434))|444|(1:446)|447|448|449|450|451|452|453|454|455|456|457|(1:459)(1:855)|460|(1:462)|463|(1:465)|466|(1:468)|469|(1:471)|472|473|474|475|(6:477|478|479|(1:481)(1:485)|482|483)|488|489|490|491|492|493|494|495|496|497|498|499|500|501|502|503|504|505|506|507|508|509|510|511|512|513|514|515|516|517|518|519|520|521|522|523|524|525|526|527|528|529|530|531|532|533|534|535|536|537|538|539|(4:541|542|543|544)|548|549|550|(1:552)|554|555|(4:557|558|559|560)|564|(1:566)(1:791)|567|(1:569)(3:785|786|787)|570|(1:572)|(1:574)|(1:576)|(4:578|579|580|581)|585|(1:587)|588|589|590|591|(1:782)|(4:597|598|599|600)|604|605|606|607|(1:609)|610|(1:612)|613|(1:778)|617|(1:619)|620|(1:622)|623|624|625|626|627|628|629|(1:771)(6:632|633|634|635|636|637)|638|639|640|641|(1:643)|644|(1:646)|647|(1:649)|650|651|652|653|(2:655|(1:657))|658|659|660|(1:670)|672|673|674|(1:678)|680|(1:682)|683|(1:753)|687|(1:752)|691|(1:693)|694|(1:696)|697|(1:699)|700|701|702|703|704|705|(1:707)|(1:709)|(1:711)|712|(4:714|715|716|717)|(4:722|723|724|725)|729|730|731|732|(1:734)|735|(1:737)|738|(1:740)|741)|203|(4:205|206|207|208)(1:352)|(1:210)|211|(1:213)|214|(1:216)|217|218|219|220|(1:222)|223|(1:225)|226|(1:345)(1:229)|230|(1:232)|233|(2:235|(1:237)(1:238))|239|(1:241)(1:344)|242|(2:339|340)|244|(4:329|330|332|333)|246|(1:248)|249|250|251|252|253|254|255|1cde|(1:263)|264|(1:266)|267|(3:268|269|(1:271))|273|(2:274|275)|276|(6:278|279|280|281|282|283)|288|289|(4:291|292|293|294)(1:312)|295|(1:297)|298|(1:300)|301|(2:302|303)|304|305) */
    /* JADX WARN: Can't wrap try/catch for region: R(144:9|10|(2:11|12)|13|(4:15|16|17|18)(1:1093)|19|20|21|22|(2:23|24)|25|26|(1:1077)|32|(2:33|34)|35|36|(9:1045|1046|1047|1048|1049|1050|1051|1052|1053)|38|(4:39|40|41|42)|(5:44|45|46|47|48)|49|(1:51)|52|(1:54)|55|(2:56|57)|58|59|(1:61)|62|(2:63|64)|65|(1:67)(1:1014)|68|(1:71)|72|(1:74)(2:1010|(1:1012)(1:1013))|75|(2:76|77)|78|(6:80|81|82|(1:84)(1:88)|85|86)|92|(1:96)|97|(2:98|99)|100|101|(3:989|990|991)|103|(1:105)(2:986|(1:988))|106|(6:108|(1:110)(5:974|975|976|977|978)|111|112|113|114)(1:985)|115|(2:116|117)|118|(4:122|123|124|125)|129|130|131|132|(2:133|134)|135|(2:136|137)|138|139|140|(1:142)(1:956)|143|144|(4:145|146|(1:148)(1:952)|149)|150|(6:152|153|154|(1:156)(1:160)|157|158)|163|(11:167|168|169|170|171|172|173|174|175|(1:177)|179)|188|(2:189|190)|191|(2:192|193)|194|(2:195|196)|197|(2:198|199)|200|(1:202)(236:353|354|355|356|(1:358)|359|(1:929)|363|364|365|(1:367)|369|370|371|372|373|(1:375)(1:923)|376|377|378|379|(2:915|916)(1:381)|382|383|384|385|386|387|388|(1:390)(1:908)|391|(1:393)(1:907)|394|395|396|397|398|(1:885)|402|403|404|(1:408)|410|411|412|413|414|415|416|417|418|419|420|421|(7:423|424|425|426|427|428|(4:431|432|433|434))|444|(1:446)|447|448|449|450|451|452|453|454|455|456|457|(1:459)(1:855)|460|(1:462)|463|(1:465)|466|(1:468)|469|(1:471)|472|473|474|475|(6:477|478|479|(1:481)(1:485)|482|483)|488|489|490|491|492|493|494|495|496|497|498|499|500|501|502|503|504|505|506|507|508|509|510|511|512|513|514|515|516|517|518|519|520|521|522|523|524|525|526|527|528|529|530|531|532|533|534|535|536|537|538|539|(4:541|542|543|544)|548|549|550|(1:552)|554|555|(4:557|558|559|560)|564|(1:566)(1:791)|567|(1:569)(3:785|786|787)|570|(1:572)|(1:574)|(1:576)|(4:578|579|580|581)|585|(1:587)|588|589|590|591|(1:782)|(4:597|598|599|600)|604|605|606|607|(1:609)|610|(1:612)|613|(1:778)|617|(1:619)|620|(1:622)|623|624|625|626|627|628|629|(1:771)(6:632|633|634|635|636|637)|638|639|640|641|(1:643)|644|(1:646)|647|(1:649)|650|651|652|653|(2:655|(1:657))|658|659|660|(1:670)|672|673|674|(1:678)|680|(1:682)|683|(1:753)|687|(1:752)|691|(1:693)|694|(1:696)|697|(1:699)|700|701|702|703|704|705|(1:707)|(1:709)|(1:711)|712|(4:714|715|716|717)|(4:722|723|724|725)|729|730|731|732|(1:734)|735|(1:737)|738|(1:740)|741)|203|(4:205|206|207|208)(1:352)|(1:210)|211|(1:213)|214|(1:216)|217|218|219|220|(1:222)|223|(1:225)|226|(1:345)(1:229)|230|(1:232)|233|(2:235|(1:237)(1:238))|239|(1:241)(1:344)|242|(2:339|340)|244|(4:329|330|332|333)|246|(1:248)|249|250|251|252|253|254|255|1cde|(1:263)|264|(1:266)|267|268|269|(1:271)|273|(2:274|275)|276|(6:278|279|280|281|282|283)|288|289|(4:291|292|293|294)(1:312)|295|(1:297)|298|(1:300)|301|302|303|304|305) */
    /* JADX WARN: Can't wrap try/catch for region: R(160:8|9|10|(2:11|12)|13|(4:15|16|17|18)(1:1093)|19|20|21|22|23|24|25|26|(1:1077)|32|(2:33|34)|35|36|(9:1045|1046|1047|1048|1049|1050|1051|1052|1053)|38|39|40|41|42|44|45|46|47|48|49|(1:51)|52|(1:54)|55|(2:56|57)|58|59|(1:61)|62|(2:63|64)|65|(1:67)(1:1014)|68|(1:71)|72|(1:74)(2:1010|(1:1012)(1:1013))|75|(2:76|77)|78|(6:80|81|82|(1:84)(1:88)|85|86)|92|(1:96)|97|98|99|100|101|(3:989|990|991)|103|(1:105)(2:986|(1:988))|106|(6:108|(1:110)(5:974|975|976|977|978)|111|112|113|114)(1:985)|115|(2:116|117)|118|(4:122|123|124|125)|129|130|131|132|133|134|135|(2:136|137)|138|139|140|(1:142)(1:956)|143|144|145|146|(1:148)(1:952)|149|150|(6:152|153|154|(1:156)(1:160)|157|158)|163|(11:167|168|169|170|171|172|173|174|175|(1:177)|179)|188|189|190|191|(2:192|193)|194|(2:195|196)|197|(2:198|199)|200|(1:202)(236:353|354|355|356|(1:358)|359|(1:929)|363|364|365|(1:367)|369|370|371|372|373|(1:375)(1:923)|376|377|378|379|(2:915|916)(1:381)|382|383|384|385|386|387|388|(1:390)(1:908)|391|(1:393)(1:907)|394|395|396|397|398|(1:885)|402|403|404|(1:408)|410|411|412|413|414|415|416|417|418|419|420|421|(7:423|424|425|426|427|428|(4:431|432|433|434))|444|(1:446)|447|448|449|450|451|452|453|454|455|456|457|(1:459)(1:855)|460|(1:462)|463|(1:465)|466|(1:468)|469|(1:471)|472|473|474|475|(6:477|478|479|(1:481)(1:485)|482|483)|488|489|490|491|492|493|494|495|496|497|498|499|500|501|502|503|504|505|506|507|508|509|510|511|512|513|514|515|516|517|518|519|520|521|522|523|524|525|526|527|528|529|530|531|532|533|534|535|536|537|538|539|(4:541|542|543|544)|548|549|550|(1:552)|554|555|(4:557|558|559|560)|564|(1:566)(1:791)|567|(1:569)(3:785|786|787)|570|(1:572)|(1:574)|(1:576)|(4:578|579|580|581)|585|(1:587)|588|589|590|591|(1:782)|(4:597|598|599|600)|604|605|606|607|(1:609)|610|(1:612)|613|(1:778)|617|(1:619)|620|(1:622)|623|624|625|626|627|628|629|(1:771)(6:632|633|634|635|636|637)|638|639|640|641|(1:643)|644|(1:646)|647|(1:649)|650|651|652|653|(2:655|(1:657))|658|659|660|(1:670)|672|673|674|(1:678)|680|(1:682)|683|(1:753)|687|(1:752)|691|(1:693)|694|(1:696)|697|(1:699)|700|701|702|703|704|705|(1:707)|(1:709)|(1:711)|712|(4:714|715|716|717)|(4:722|723|724|725)|729|730|731|732|(1:734)|735|(1:737)|738|(1:740)|741)|203|(4:205|206|207|208)(1:352)|(1:210)|211|(1:213)|214|(1:216)|217|218|219|220|(1:222)|223|(1:225)|226|(1:345)(1:229)|230|(1:232)|233|(2:235|(1:237)(1:238))|239|(1:241)(1:344)|242|(2:339|340)|244|(4:329|330|332|333)|246|(1:248)|249|250|251|252|253|254|255|1cde|(1:263)|264|(1:266)|267|268|269|(1:271)|273|274|275|276|(6:278|279|280|281|282|283)|288|289|(4:291|292|293|294)(1:312)|295|(1:297)|298|(1:300)|301|302|303|304|305) */
    /* JADX WARN: Can't wrap try/catch for region: R(163:8|9|10|11|12|13|(4:15|16|17|18)(1:1093)|19|20|21|22|23|24|25|26|(1:1077)|32|(2:33|34)|35|36|(9:1045|1046|1047|1048|1049|1050|1051|1052|1053)|38|39|40|41|42|44|45|46|47|48|49|(1:51)|52|(1:54)|55|(2:56|57)|58|59|(1:61)|62|(2:63|64)|65|(1:67)(1:1014)|68|(1:71)|72|(1:74)(2:1010|(1:1012)(1:1013))|75|(2:76|77)|78|(6:80|81|82|(1:84)(1:88)|85|86)|92|(1:96)|97|98|99|100|101|(3:989|990|991)|103|(1:105)(2:986|(1:988))|106|(6:108|(1:110)(5:974|975|976|977|978)|111|112|113|114)(1:985)|115|(2:116|117)|118|(4:122|123|124|125)|129|130|131|132|133|134|135|136|137|138|139|140|(1:142)(1:956)|143|144|145|146|(1:148)(1:952)|149|150|(6:152|153|154|(1:156)(1:160)|157|158)|163|(11:167|168|169|170|171|172|173|174|175|(1:177)|179)|188|189|190|191|192|193|194|(2:195|196)|197|(2:198|199)|200|(1:202)(236:353|354|355|356|(1:358)|359|(1:929)|363|364|365|(1:367)|369|370|371|372|373|(1:375)(1:923)|376|377|378|379|(2:915|916)(1:381)|382|383|384|385|386|387|388|(1:390)(1:908)|391|(1:393)(1:907)|394|395|396|397|398|(1:885)|402|403|404|(1:408)|410|411|412|413|414|415|416|417|418|419|420|421|(7:423|424|425|426|427|428|(4:431|432|433|434))|444|(1:446)|447|448|449|450|451|452|453|454|455|456|457|(1:459)(1:855)|460|(1:462)|463|(1:465)|466|(1:468)|469|(1:471)|472|473|474|475|(6:477|478|479|(1:481)(1:485)|482|483)|488|489|490|491|492|493|494|495|496|497|498|499|500|501|502|503|504|505|506|507|508|509|510|511|512|513|514|515|516|517|518|519|520|521|522|523|524|525|526|527|528|529|530|531|532|533|534|535|536|537|538|539|(4:541|542|543|544)|548|549|550|(1:552)|554|555|(4:557|558|559|560)|564|(1:566)(1:791)|567|(1:569)(3:785|786|787)|570|(1:572)|(1:574)|(1:576)|(4:578|579|580|581)|585|(1:587)|588|589|590|591|(1:782)|(4:597|598|599|600)|604|605|606|607|(1:609)|610|(1:612)|613|(1:778)|617|(1:619)|620|(1:622)|623|624|625|626|627|628|629|(1:771)(6:632|633|634|635|636|637)|638|639|640|641|(1:643)|644|(1:646)|647|(1:649)|650|651|652|653|(2:655|(1:657))|658|659|660|(1:670)|672|673|674|(1:678)|680|(1:682)|683|(1:753)|687|(1:752)|691|(1:693)|694|(1:696)|697|(1:699)|700|701|702|703|704|705|(1:707)|(1:709)|(1:711)|712|(4:714|715|716|717)|(4:722|723|724|725)|729|730|731|732|(1:734)|735|(1:737)|738|(1:740)|741)|203|(4:205|206|207|208)(1:352)|(1:210)|211|(1:213)|214|(1:216)|217|218|219|220|(1:222)|223|(1:225)|226|(1:345)(1:229)|230|(1:232)|233|(2:235|(1:237)(1:238))|239|(1:241)(1:344)|242|(2:339|340)|244|(4:329|330|332|333)|246|(1:248)|249|250|251|252|253|254|255|1cde|(1:263)|264|(1:266)|267|268|269|(1:271)|273|274|275|276|(6:278|279|280|281|282|283)|288|289|(4:291|292|293|294)(1:312)|295|(1:297)|298|(1:300)|301|302|303|304|305) */
    /* JADX WARN: Can't wrap try/catch for region: R(164:8|9|10|11|12|13|(4:15|16|17|18)(1:1093)|19|20|21|22|23|24|25|26|(1:1077)|32|33|34|35|36|(9:1045|1046|1047|1048|1049|1050|1051|1052|1053)|38|39|40|41|42|44|45|46|47|48|49|(1:51)|52|(1:54)|55|(2:56|57)|58|59|(1:61)|62|(2:63|64)|65|(1:67)(1:1014)|68|(1:71)|72|(1:74)(2:1010|(1:1012)(1:1013))|75|(2:76|77)|78|(6:80|81|82|(1:84)(1:88)|85|86)|92|(1:96)|97|98|99|100|101|(3:989|990|991)|103|(1:105)(2:986|(1:988))|106|(6:108|(1:110)(5:974|975|976|977|978)|111|112|113|114)(1:985)|115|(2:116|117)|118|(4:122|123|124|125)|129|130|131|132|133|134|135|136|137|138|139|140|(1:142)(1:956)|143|144|145|146|(1:148)(1:952)|149|150|(6:152|153|154|(1:156)(1:160)|157|158)|163|(11:167|168|169|170|171|172|173|174|175|(1:177)|179)|188|189|190|191|192|193|194|(2:195|196)|197|(2:198|199)|200|(1:202)(236:353|354|355|356|(1:358)|359|(1:929)|363|364|365|(1:367)|369|370|371|372|373|(1:375)(1:923)|376|377|378|379|(2:915|916)(1:381)|382|383|384|385|386|387|388|(1:390)(1:908)|391|(1:393)(1:907)|394|395|396|397|398|(1:885)|402|403|404|(1:408)|410|411|412|413|414|415|416|417|418|419|420|421|(7:423|424|425|426|427|428|(4:431|432|433|434))|444|(1:446)|447|448|449|450|451|452|453|454|455|456|457|(1:459)(1:855)|460|(1:462)|463|(1:465)|466|(1:468)|469|(1:471)|472|473|474|475|(6:477|478|479|(1:481)(1:485)|482|483)|488|489|490|491|492|493|494|495|496|497|498|499|500|501|502|503|504|505|506|507|508|509|510|511|512|513|514|515|516|517|518|519|520|521|522|523|524|525|526|527|528|529|530|531|532|533|534|535|536|537|538|539|(4:541|542|543|544)|548|549|550|(1:552)|554|555|(4:557|558|559|560)|564|(1:566)(1:791)|567|(1:569)(3:785|786|787)|570|(1:572)|(1:574)|(1:576)|(4:578|579|580|581)|585|(1:587)|588|589|590|591|(1:782)|(4:597|598|599|600)|604|605|606|607|(1:609)|610|(1:612)|613|(1:778)|617|(1:619)|620|(1:622)|623|624|625|626|627|628|629|(1:771)(6:632|633|634|635|636|637)|638|639|640|641|(1:643)|644|(1:646)|647|(1:649)|650|651|652|653|(2:655|(1:657))|658|659|660|(1:670)|672|673|674|(1:678)|680|(1:682)|683|(1:753)|687|(1:752)|691|(1:693)|694|(1:696)|697|(1:699)|700|701|702|703|704|705|(1:707)|(1:709)|(1:711)|712|(4:714|715|716|717)|(4:722|723|724|725)|729|730|731|732|(1:734)|735|(1:737)|738|(1:740)|741)|203|(4:205|206|207|208)(1:352)|(1:210)|211|(1:213)|214|(1:216)|217|218|219|220|(1:222)|223|(1:225)|226|(1:345)(1:229)|230|(1:232)|233|(2:235|(1:237)(1:238))|239|(1:241)(1:344)|242|(2:339|340)|244|(4:329|330|332|333)|246|(1:248)|249|250|251|252|253|254|255|1cde|(1:263)|264|(1:266)|267|268|269|(1:271)|273|274|275|276|(6:278|279|280|281|282|283)|288|289|(4:291|292|293|294)(1:312)|295|(1:297)|298|(1:300)|301|302|303|304|305) */
    /* JADX WARN: Can't wrap try/catch for region: R(223:353|(2:354|355)|356|(1:358)|359|(1:929)|363|(7:364|365|(1:367)|369|370|371|372)|373|(1:375)(1:923)|376|377|378|379|(2:915|916)(1:381)|382|(2:383|384)|385|(2:386|387)|388|(1:390)(1:908)|391|(1:393)(1:907)|(2:394|395)|396|(3:397|398|(1:885))|402|403|404|(1:408)|410|411|412|413|(2:414|415)|416|417|418|419|420|421|(7:423|424|425|426|427|428|(4:431|432|433|434))|444|(1:446)|447|448|449|450|451|452|453|454|455|456|457|(1:459)(1:855)|460|(1:462)|463|(1:465)|466|(1:468)|469|(1:471)|472|473|474|475|(6:477|478|479|(1:481)(1:485)|482|483)|488|489|490|491|492|493|494|495|496|497|498|499|500|501|502|503|504|505|506|507|508|509|510|511|512|513|514|515|516|517|518|519|520|521|522|523|524|525|526|527|528|529|530|531|532|533|534|535|536|537|538|539|(4:541|542|543|544)|548|549|550|(1:552)|554|555|(4:557|558|559|560)|564|(1:566)(1:791)|567|(1:569)(3:785|786|787)|570|(1:572)|(1:574)|(1:576)|(4:578|579|580|581)|585|(1:587)|588|589|590|591|(1:782)|(4:597|598|599|600)|604|605|606|607|(1:609)|610|(1:612)|613|(1:778)|617|(1:619)|620|(1:622)|623|624|625|626|627|628|629|(1:771)(6:632|633|634|635|636|637)|638|639|640|641|(1:643)|644|(1:646)|647|(1:649)|650|651|652|653|(2:655|(1:657))|658|659|660|(1:670)|672|673|674|(1:678)|680|(1:682)|683|(1:753)|687|(1:752)|691|(1:693)|694|(1:696)|697|(1:699)|700|701|702|703|704|705|(1:707)|(1:709)|(1:711)|712|(4:714|715|716|717)|(4:722|723|724|725)|729|730|731|732|(1:734)|735|(1:737)|738|(1:740)|741) */
    /* JADX WARN: Can't wrap try/catch for region: R(224:353|354|355|356|(1:358)|359|(1:929)|363|(7:364|365|(1:367)|369|370|371|372)|373|(1:375)(1:923)|376|377|378|379|(2:915|916)(1:381)|382|(2:383|384)|385|(2:386|387)|388|(1:390)(1:908)|391|(1:393)(1:907)|(2:394|395)|396|(3:397|398|(1:885))|402|403|404|(1:408)|410|411|412|413|(2:414|415)|416|417|418|419|420|421|(7:423|424|425|426|427|428|(4:431|432|433|434))|444|(1:446)|447|448|449|450|451|452|453|454|455|456|457|(1:459)(1:855)|460|(1:462)|463|(1:465)|466|(1:468)|469|(1:471)|472|473|474|475|(6:477|478|479|(1:481)(1:485)|482|483)|488|489|490|491|492|493|494|495|496|497|498|499|500|501|502|503|504|505|506|507|508|509|510|511|512|513|514|515|516|517|518|519|520|521|522|523|524|525|526|527|528|529|530|531|532|533|534|535|536|537|538|539|(4:541|542|543|544)|548|549|550|(1:552)|554|555|(4:557|558|559|560)|564|(1:566)(1:791)|567|(1:569)(3:785|786|787)|570|(1:572)|(1:574)|(1:576)|(4:578|579|580|581)|585|(1:587)|588|589|590|591|(1:782)|(4:597|598|599|600)|604|605|606|607|(1:609)|610|(1:612)|613|(1:778)|617|(1:619)|620|(1:622)|623|624|625|626|627|628|629|(1:771)(6:632|633|634|635|636|637)|638|639|640|641|(1:643)|644|(1:646)|647|(1:649)|650|651|652|653|(2:655|(1:657))|658|659|660|(1:670)|672|673|674|(1:678)|680|(1:682)|683|(1:753)|687|(1:752)|691|(1:693)|694|(1:696)|697|(1:699)|700|701|702|703|704|705|(1:707)|(1:709)|(1:711)|712|(4:714|715|716|717)|(4:722|723|724|725)|729|730|731|732|(1:734)|735|(1:737)|738|(1:740)|741) */
    /* JADX WARN: Can't wrap try/catch for region: R(225:353|354|355|356|(1:358)|359|(1:929)|363|(7:364|365|(1:367)|369|370|371|372)|373|(1:375)(1:923)|376|377|378|379|(2:915|916)(1:381)|382|383|384|385|(2:386|387)|388|(1:390)(1:908)|391|(1:393)(1:907)|(2:394|395)|396|(3:397|398|(1:885))|402|403|404|(1:408)|410|411|412|413|(2:414|415)|416|417|418|419|420|421|(7:423|424|425|426|427|428|(4:431|432|433|434))|444|(1:446)|447|448|449|450|451|452|453|454|455|456|457|(1:459)(1:855)|460|(1:462)|463|(1:465)|466|(1:468)|469|(1:471)|472|473|474|475|(6:477|478|479|(1:481)(1:485)|482|483)|488|489|490|491|492|493|494|495|496|497|498|499|500|501|502|503|504|505|506|507|508|509|510|511|512|513|514|515|516|517|518|519|520|521|522|523|524|525|526|527|528|529|530|531|532|533|534|535|536|537|538|539|(4:541|542|543|544)|548|549|550|(1:552)|554|555|(4:557|558|559|560)|564|(1:566)(1:791)|567|(1:569)(3:785|786|787)|570|(1:572)|(1:574)|(1:576)|(4:578|579|580|581)|585|(1:587)|588|589|590|591|(1:782)|(4:597|598|599|600)|604|605|606|607|(1:609)|610|(1:612)|613|(1:778)|617|(1:619)|620|(1:622)|623|624|625|626|627|628|629|(1:771)(6:632|633|634|635|636|637)|638|639|640|641|(1:643)|644|(1:646)|647|(1:649)|650|651|652|653|(2:655|(1:657))|658|659|660|(1:670)|672|673|674|(1:678)|680|(1:682)|683|(1:753)|687|(1:752)|691|(1:693)|694|(1:696)|697|(1:699)|700|701|702|703|704|705|(1:707)|(1:709)|(1:711)|712|(4:714|715|716|717)|(4:722|723|724|725)|729|730|731|732|(1:734)|735|(1:737)|738|(1:740)|741) */
    /* JADX WARN: Can't wrap try/catch for region: R(226:353|354|355|356|(1:358)|359|(1:929)|363|(7:364|365|(1:367)|369|370|371|372)|373|(1:375)(1:923)|376|377|378|379|(2:915|916)(1:381)|382|383|384|385|386|387|388|(1:390)(1:908)|391|(1:393)(1:907)|(2:394|395)|396|(3:397|398|(1:885))|402|403|404|(1:408)|410|411|412|413|(2:414|415)|416|417|418|419|420|421|(7:423|424|425|426|427|428|(4:431|432|433|434))|444|(1:446)|447|448|449|450|451|452|453|454|455|456|457|(1:459)(1:855)|460|(1:462)|463|(1:465)|466|(1:468)|469|(1:471)|472|473|474|475|(6:477|478|479|(1:481)(1:485)|482|483)|488|489|490|491|492|493|494|495|496|497|498|499|500|501|502|503|504|505|506|507|508|509|510|511|512|513|514|515|516|517|518|519|520|521|522|523|524|525|526|527|528|529|530|531|532|533|534|535|536|537|538|539|(4:541|542|543|544)|548|549|550|(1:552)|554|555|(4:557|558|559|560)|564|(1:566)(1:791)|567|(1:569)(3:785|786|787)|570|(1:572)|(1:574)|(1:576)|(4:578|579|580|581)|585|(1:587)|588|589|590|591|(1:782)|(4:597|598|599|600)|604|605|606|607|(1:609)|610|(1:612)|613|(1:778)|617|(1:619)|620|(1:622)|623|624|625|626|627|628|629|(1:771)(6:632|633|634|635|636|637)|638|639|640|641|(1:643)|644|(1:646)|647|(1:649)|650|651|652|653|(2:655|(1:657))|658|659|660|(1:670)|672|673|674|(1:678)|680|(1:682)|683|(1:753)|687|(1:752)|691|(1:693)|694|(1:696)|697|(1:699)|700|701|702|703|704|705|(1:707)|(1:709)|(1:711)|712|(4:714|715|716|717)|(4:722|723|724|725)|729|730|731|732|(1:734)|735|(1:737)|738|(1:740)|741) */
    /* JADX WARN: Can't wrap try/catch for region: R(232:353|354|355|356|(1:358)|359|(1:929)|363|364|365|(1:367)|369|370|371|372|373|(1:375)(1:923)|376|377|378|379|(2:915|916)(1:381)|382|383|384|385|386|387|388|(1:390)(1:908)|391|(1:393)(1:907)|(2:394|395)|396|(3:397|398|(1:885))|402|403|404|(1:408)|410|411|412|413|(2:414|415)|416|417|418|419|420|421|(7:423|424|425|426|427|428|(4:431|432|433|434))|444|(1:446)|447|448|449|450|451|452|453|454|455|456|457|(1:459)(1:855)|460|(1:462)|463|(1:465)|466|(1:468)|469|(1:471)|472|473|474|475|(6:477|478|479|(1:481)(1:485)|482|483)|488|489|490|491|492|493|494|495|496|497|498|499|500|501|502|503|504|505|506|507|508|509|510|511|512|513|514|515|516|517|518|519|520|521|522|523|524|525|526|527|528|529|530|531|532|533|534|535|536|537|538|539|(4:541|542|543|544)|548|549|550|(1:552)|554|555|(4:557|558|559|560)|564|(1:566)(1:791)|567|(1:569)(3:785|786|787)|570|(1:572)|(1:574)|(1:576)|(4:578|579|580|581)|585|(1:587)|588|589|590|591|(1:782)|(4:597|598|599|600)|604|605|606|607|(1:609)|610|(1:612)|613|(1:778)|617|(1:619)|620|(1:622)|623|624|625|626|627|628|629|(1:771)(6:632|633|634|635|636|637)|638|639|640|641|(1:643)|644|(1:646)|647|(1:649)|650|651|652|653|(2:655|(1:657))|658|659|660|(1:670)|672|673|674|(1:678)|680|(1:682)|683|(1:753)|687|(1:752)|691|(1:693)|694|(1:696)|697|(1:699)|700|701|702|703|704|705|(1:707)|(1:709)|(1:711)|712|(4:714|715|716|717)|(4:722|723|724|725)|729|730|731|732|(1:734)|735|(1:737)|738|(1:740)|741) */
    /* JADX WARN: Code restructure failed: missing block: B:324:0x1cd2, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:325:0x1cd3, code lost:
    
        reportWtf("RegisterLogMteState", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:327:0x1cbe, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:328:0x1cbf, code lost:
    
        reportWtf("making Window Manager Service ready", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:347:0x1aa4, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:348:0x1aa5, code lost:
    
        reportWtf("starting SpenGestureManagerService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:743:0x195b, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:744:0x195c, code lost:
    
        android.util.Slog.e("SystemServer", "Failure adding ChimeraManagerService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:746:0x1830, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:747:0x1831, code lost:
    
        r5 = r0;
        r2 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:748:0x1836, code lost:
    
        reportWtf("starting MediaRouterService", r5);
        r2 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:750:0x1833, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:751:0x1834, code lost:
    
        r5 = r0;
        r2 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:754:0x175b, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:755:0x175c, code lost:
    
        reportWtf("starting BattAuthManager", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:756:0x1729, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:757:0x172a, code lost:
    
        reportWtf("starting SAccessoryManager", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:759:0x16a8, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:760:0x16a9, code lost:
    
        reportWtf("Failed To Call SemInputDeviceManagerService SystemReady loader ", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:762:0x15b7, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:763:0x15b8, code lost:
    
        reportWtf("starting CertBlacklister", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:773:0x157b, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:774:0x157c, code lost:
    
        reportWtf("starting RuntimeService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:776:0x155f, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:777:0x1560, code lost:
    
        reportWtf("starting DiskStats Service", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:780:0x1467, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:781:0x1468, code lost:
    
        android.util.Slog.e("SystemServer", "Failure starting HardwarePropertiesManagerService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:784:0x1409, code lost:
    
        android.util.Slog.e("SystemServer", "Failure starting AdbService");
     */
    /* JADX WARN: Code restructure failed: missing block: B:792:0x12ba, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:793:0x12bb, code lost:
    
        r30 = r1;
        android.util.Slog.e("SystemServer", "Failure starting KnoxVpnEngineService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:795:0x125e, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:796:0x125f, code lost:
    
        reportWtf("starting LocationTimeZoneManagerService service", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:798:0x1246, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:799:0x1247, code lost:
    
        reportWtf("starting AltitudeService service", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:801:0x122e, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:802:0x122f, code lost:
    
        reportWtf("starting TimeZoneDetectorService service", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:804:0x1212, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:805:0x1213, code lost:
    
        r2 = r0;
        r1 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:806:0x1218, code lost:
    
        reportWtf("starting Country Detector", r2);
        r1 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:808:0x1215, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:809:0x1216, code lost:
    
        r2 = r0;
        r1 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:811:0x11f8, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:812:0x11f9, code lost:
    
        reportWtf("Failed To Start SemDisplaySolution Service ", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:814:0x11bf, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:815:0x11c0, code lost:
    
        reportWtf("Failed To Start Mdnie Service ", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:817:0x117a, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:818:0x117b, code lost:
    
        r1 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:819:0x1186, code lost:
    
        reportWtf("Starting SLocationService", r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:821:0x117d, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:822:0x1184, code lost:
    
        r1 = r0;
        r3 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:824:0x117f, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:825:0x1180, code lost:
    
        r28 = r2;
        r29 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:827:0x1128, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:828:0x1129, code lost:
    
        reportWtf("starting TimeDetectorService service", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:830:0x10e2, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:831:0x10e3, code lost:
    
        reportWtf("starting UpdateLockService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:833:0x10c6, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:834:0x10c7, code lost:
    
        reportWtf("starting SystemUpdateManagerService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:836:0x10a2, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:837:0x10a3, code lost:
    
        r10 = r0;
        r27 = r1;
        r4 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:838:0x10ac, code lost:
    
        reportWtf("starting VCN Management Service", r10);
        r4 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:840:0x10a7, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:841:0x10a8, code lost:
    
        r10 = r0;
        r27 = r1;
        r4 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:843:0x1081, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:844:0x1082, code lost:
    
        r4 = r0;
        r2 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:845:0x1087, code lost:
    
        reportWtf("starting VPN Manager Service", r4);
        r2 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:847:0x1084, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:848:0x1085, code lost:
    
        r4 = r0;
        r2 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:850:0x1065, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:851:0x1066, code lost:
    
        android.util.Slog.e("SystemServer", "Failed to start ExtendedEthernetService.", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:853:0x0fe9, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:854:0x0fea, code lost:
    
        reportWtf("starting PacProxyService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:857:0x0f24, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:858:0x0f25, code lost:
    
        r4 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:859:0x0f2a, code lost:
    
        reportWtf("starting URSP Service", r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:861:0x0f27, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:862:0x0f28, code lost:
    
        r4 = r0;
        r3 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:864:0x0f04, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:865:0x0f05, code lost:
    
        r3 = r0;
        r1 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:866:0x0f0a, code lost:
    
        reportWtf("starting NetworkPolicy Service", r3);
        r1 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:868:0x0f07, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:869:0x0f08, code lost:
    
        r3 = r0;
        r1 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:871:0x0e39, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:872:0x0e3a, code lost:
    
        r3 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:873:0x0e3f, code lost:
    
        reportWtf("starting NetworkManagement Service", r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:875:0x0e3c, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:876:0x0e3d, code lost:
    
        r3 = r0;
        r2 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:881:0x0e08, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:882:0x0e09, code lost:
    
        reportWtf("initializing ConnectivityModuleConnector", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:883:0x0def, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:884:0x0df0, code lost:
    
        android.util.Slog.e("SystemServer", "Failure starting MotionRecognitionService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:957:0x0920, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:958:0x0921, code lost:
    
        android.util.Slog.e("SystemServer", "Failed to start SehCodecSolutionService ", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:966:0x0838, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:967:0x0839, code lost:
    
        android.util.Slog.e("SystemServer", "Failed to start SamsungGameManager.", r0);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:1010:0x04da A[Catch: all -> 0x1f60, TryCatch #40 {all -> 0x1f60, blocks: (B:10:0x008d, B:13:0x00b2, B:15:0x00c6, B:18:0x00dd, B:19:0x00e8, B:21:0x0100, B:22:0x0119, B:25:0x015c, B:26:0x016a, B:28:0x0174, B:30:0x017e, B:32:0x0197, B:35:0x01d6, B:1047:0x0200, B:1053:0x0235, B:1061:0x0246, B:1062:0x0249, B:38:0x024e, B:49:0x02a5, B:51:0x031e, B:52:0x032d, B:54:0x035a, B:55:0x036c, B:58:0x0387, B:59:0x039e, B:61:0x03cb, B:62:0x03e9, B:65:0x0421, B:68:0x043e, B:71:0x048f, B:72:0x049e, B:74:0x04d2, B:75:0x04ff, B:77:0x0515, B:78:0x054e, B:80:0x0560, B:82:0x0565, B:84:0x0599, B:86:0x05c7, B:88:0x05b5, B:91:0x05bd, B:92:0x05ca, B:94:0x05fb, B:96:0x0601, B:97:0x0610, B:100:0x0673, B:991:0x0704, B:1000:0x0714, B:1001:0x0717, B:1005:0x066e, B:1009:0x0544, B:1010:0x04da, B:1012:0x04e6, B:1013:0x04ee, B:1018:0x041c, B:1027:0x1f55, B:1028:0x1f58, B:1034:0x029a, B:1040:0x026b, B:1073:0x024a, B:1074:0x024d, B:1076:0x01cf, B:1077:0x0188, B:1085:0x1f5a, B:1086:0x1f5f, B:1090:0x0112, B:1092:0x00d6, B:1093:0x00e1, B:1095:0x00ab, B:1049:0x0211, B:1051:0x0225, B:1052:0x0232, B:1058:0x022b, B:1065:0x023e, B:1082:0x0162, B:24:0x014d, B:99:0x0640, B:12:0x00a0, B:34:0x01c4, B:17:0x00cb, B:1046:0x01ea, B:1069:0x0209, B:990:0x06b1, B:996:0x070a, B:64:0x03ef), top: B:9:0x008d, inners: #12, #21, #33, #39, #47, #64, #76, #89, #93, #106, #110, #113, #118, #120 }] */
    /* JADX WARN: Removed duplicated region for block: B:1014:0x043d  */
    /* JADX WARN: Removed duplicated region for block: B:1045:0x01ea A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x072a  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0754  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x07ef  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x08e1 A[Catch: all -> 0x0920, TryCatch #1 {all -> 0x0920, blocks: (B:140:0x08cb, B:142:0x08e1, B:956:0x08e9), top: B:139:0x08cb }] */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0947 A[Catch: all -> 0x0986, TryCatch #57 {all -> 0x0986, blocks: (B:146:0x0931, B:148:0x0947, B:952:0x094f), top: B:145:0x0931 }] */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0996  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x09ff  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0a53 A[Catch: all -> 0x0a68, TRY_LEAVE, TryCatch #44 {all -> 0x0a68, blocks: (B:175:0x0a4d, B:177:0x0a53), top: B:174:0x0a4d }] */
    /* JADX WARN: Removed duplicated region for block: B:202:0x0b18  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x19e1  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x1a05  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x1a69  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x1a84  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x1b2d  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x1b48  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x1b62 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:232:0x1b86  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x1b9f  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x1bca  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x1c94  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x1cdf A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:329:0x1c4c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:339:0x1c25 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:344:0x1bda  */
    /* JADX WARN: Removed duplicated region for block: B:352:0x19fc  */
    /* JADX WARN: Removed duplicated region for block: B:353:0x0b2d  */
    /* JADX WARN: Removed duplicated region for block: B:375:0x0bef  */
    /* JADX WARN: Removed duplicated region for block: B:381:0x0c4b  */
    /* JADX WARN: Removed duplicated region for block: B:390:0x0ccc  */
    /* JADX WARN: Removed duplicated region for block: B:393:0x0cec  */
    /* JADX WARN: Removed duplicated region for block: B:400:0x0d55 A[Catch: all -> 0x0d96, Exception -> 0x0d9b, TryCatch #122 {Exception -> 0x0d9b, all -> 0x0d96, blocks: (B:398:0x0d44, B:400:0x0d55, B:885:0x0d61), top: B:397:0x0d44 }] */
    /* JADX WARN: Removed duplicated region for block: B:406:0x0db2 A[Catch: Exception -> 0x0def, TryCatch #2 {Exception -> 0x0def, blocks: (B:404:0x0dac, B:406:0x0db2, B:408:0x0dba), top: B:403:0x0dac }] */
    /* JADX WARN: Removed duplicated region for block: B:423:0x0e4c  */
    /* JADX WARN: Removed duplicated region for block: B:446:0x0ec2  */
    /* JADX WARN: Removed duplicated region for block: B:459:0x0f3f  */
    /* JADX WARN: Removed duplicated region for block: B:462:0x0f72  */
    /* JADX WARN: Removed duplicated region for block: B:465:0x0f8f  */
    /* JADX WARN: Removed duplicated region for block: B:468:0x0fac  */
    /* JADX WARN: Removed duplicated region for block: B:471:0x0fc9  */
    /* JADX WARN: Removed duplicated region for block: B:477:0x100c  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x031e A[Catch: all -> 0x1f60, TryCatch #40 {all -> 0x1f60, blocks: (B:10:0x008d, B:13:0x00b2, B:15:0x00c6, B:18:0x00dd, B:19:0x00e8, B:21:0x0100, B:22:0x0119, B:25:0x015c, B:26:0x016a, B:28:0x0174, B:30:0x017e, B:32:0x0197, B:35:0x01d6, B:1047:0x0200, B:1053:0x0235, B:1061:0x0246, B:1062:0x0249, B:38:0x024e, B:49:0x02a5, B:51:0x031e, B:52:0x032d, B:54:0x035a, B:55:0x036c, B:58:0x0387, B:59:0x039e, B:61:0x03cb, B:62:0x03e9, B:65:0x0421, B:68:0x043e, B:71:0x048f, B:72:0x049e, B:74:0x04d2, B:75:0x04ff, B:77:0x0515, B:78:0x054e, B:80:0x0560, B:82:0x0565, B:84:0x0599, B:86:0x05c7, B:88:0x05b5, B:91:0x05bd, B:92:0x05ca, B:94:0x05fb, B:96:0x0601, B:97:0x0610, B:100:0x0673, B:991:0x0704, B:1000:0x0714, B:1001:0x0717, B:1005:0x066e, B:1009:0x0544, B:1010:0x04da, B:1012:0x04e6, B:1013:0x04ee, B:1018:0x041c, B:1027:0x1f55, B:1028:0x1f58, B:1034:0x029a, B:1040:0x026b, B:1073:0x024a, B:1074:0x024d, B:1076:0x01cf, B:1077:0x0188, B:1085:0x1f5a, B:1086:0x1f5f, B:1090:0x0112, B:1092:0x00d6, B:1093:0x00e1, B:1095:0x00ab, B:1049:0x0211, B:1051:0x0225, B:1052:0x0232, B:1058:0x022b, B:1065:0x023e, B:1082:0x0162, B:24:0x014d, B:99:0x0640, B:12:0x00a0, B:34:0x01c4, B:17:0x00cb, B:1046:0x01ea, B:1069:0x0209, B:990:0x06b1, B:996:0x070a, B:64:0x03ef), top: B:9:0x008d, inners: #12, #21, #33, #39, #47, #64, #76, #89, #93, #106, #110, #113, #118, #120 }] */
    /* JADX WARN: Removed duplicated region for block: B:541:0x1276  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x035a A[Catch: all -> 0x1f60, TryCatch #40 {all -> 0x1f60, blocks: (B:10:0x008d, B:13:0x00b2, B:15:0x00c6, B:18:0x00dd, B:19:0x00e8, B:21:0x0100, B:22:0x0119, B:25:0x015c, B:26:0x016a, B:28:0x0174, B:30:0x017e, B:32:0x0197, B:35:0x01d6, B:1047:0x0200, B:1053:0x0235, B:1061:0x0246, B:1062:0x0249, B:38:0x024e, B:49:0x02a5, B:51:0x031e, B:52:0x032d, B:54:0x035a, B:55:0x036c, B:58:0x0387, B:59:0x039e, B:61:0x03cb, B:62:0x03e9, B:65:0x0421, B:68:0x043e, B:71:0x048f, B:72:0x049e, B:74:0x04d2, B:75:0x04ff, B:77:0x0515, B:78:0x054e, B:80:0x0560, B:82:0x0565, B:84:0x0599, B:86:0x05c7, B:88:0x05b5, B:91:0x05bd, B:92:0x05ca, B:94:0x05fb, B:96:0x0601, B:97:0x0610, B:100:0x0673, B:991:0x0704, B:1000:0x0714, B:1001:0x0717, B:1005:0x066e, B:1009:0x0544, B:1010:0x04da, B:1012:0x04e6, B:1013:0x04ee, B:1018:0x041c, B:1027:0x1f55, B:1028:0x1f58, B:1034:0x029a, B:1040:0x026b, B:1073:0x024a, B:1074:0x024d, B:1076:0x01cf, B:1077:0x0188, B:1085:0x1f5a, B:1086:0x1f5f, B:1090:0x0112, B:1092:0x00d6, B:1093:0x00e1, B:1095:0x00ab, B:1049:0x0211, B:1051:0x0225, B:1052:0x0232, B:1058:0x022b, B:1065:0x023e, B:1082:0x0162, B:24:0x014d, B:99:0x0640, B:12:0x00a0, B:34:0x01c4, B:17:0x00cb, B:1046:0x01ea, B:1069:0x0209, B:990:0x06b1, B:996:0x070a, B:64:0x03ef), top: B:9:0x008d, inners: #12, #21, #33, #39, #47, #64, #76, #89, #93, #106, #110, #113, #118, #120 }] */
    /* JADX WARN: Removed duplicated region for block: B:552:0x1297 A[Catch: all -> 0x12ba, TRY_LEAVE, TryCatch #81 {all -> 0x12ba, blocks: (B:550:0x1293, B:552:0x1297), top: B:549:0x1293 }] */
    /* JADX WARN: Removed duplicated region for block: B:557:0x12ca  */
    /* JADX WARN: Removed duplicated region for block: B:566:0x12ef  */
    /* JADX WARN: Removed duplicated region for block: B:569:0x131c  */
    /* JADX WARN: Removed duplicated region for block: B:572:0x1397  */
    /* JADX WARN: Removed duplicated region for block: B:574:0x13a8  */
    /* JADX WARN: Removed duplicated region for block: B:576:0x13b9  */
    /* JADX WARN: Removed duplicated region for block: B:578:0x13ca  */
    /* JADX WARN: Removed duplicated region for block: B:587:0x13ed  */
    /* JADX WARN: Removed duplicated region for block: B:593:0x141d  */
    /* JADX WARN: Removed duplicated region for block: B:597:0x143a  */
    /* JADX WARN: Removed duplicated region for block: B:609:0x1475  */
    /* JADX WARN: Removed duplicated region for block: B:612:0x14ca  */
    /* JADX WARN: Removed duplicated region for block: B:615:0x14e3  */
    /* JADX WARN: Removed duplicated region for block: B:619:0x1518  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x03cb A[Catch: all -> 0x1f60, TryCatch #40 {all -> 0x1f60, blocks: (B:10:0x008d, B:13:0x00b2, B:15:0x00c6, B:18:0x00dd, B:19:0x00e8, B:21:0x0100, B:22:0x0119, B:25:0x015c, B:26:0x016a, B:28:0x0174, B:30:0x017e, B:32:0x0197, B:35:0x01d6, B:1047:0x0200, B:1053:0x0235, B:1061:0x0246, B:1062:0x0249, B:38:0x024e, B:49:0x02a5, B:51:0x031e, B:52:0x032d, B:54:0x035a, B:55:0x036c, B:58:0x0387, B:59:0x039e, B:61:0x03cb, B:62:0x03e9, B:65:0x0421, B:68:0x043e, B:71:0x048f, B:72:0x049e, B:74:0x04d2, B:75:0x04ff, B:77:0x0515, B:78:0x054e, B:80:0x0560, B:82:0x0565, B:84:0x0599, B:86:0x05c7, B:88:0x05b5, B:91:0x05bd, B:92:0x05ca, B:94:0x05fb, B:96:0x0601, B:97:0x0610, B:100:0x0673, B:991:0x0704, B:1000:0x0714, B:1001:0x0717, B:1005:0x066e, B:1009:0x0544, B:1010:0x04da, B:1012:0x04e6, B:1013:0x04ee, B:1018:0x041c, B:1027:0x1f55, B:1028:0x1f58, B:1034:0x029a, B:1040:0x026b, B:1073:0x024a, B:1074:0x024d, B:1076:0x01cf, B:1077:0x0188, B:1085:0x1f5a, B:1086:0x1f5f, B:1090:0x0112, B:1092:0x00d6, B:1093:0x00e1, B:1095:0x00ab, B:1049:0x0211, B:1051:0x0225, B:1052:0x0232, B:1058:0x022b, B:1065:0x023e, B:1082:0x0162, B:24:0x014d, B:99:0x0640, B:12:0x00a0, B:34:0x01c4, B:17:0x00cb, B:1046:0x01ea, B:1069:0x0209, B:990:0x06b1, B:996:0x070a, B:64:0x03ef), top: B:9:0x008d, inners: #12, #21, #33, #39, #47, #64, #76, #89, #93, #106, #110, #113, #118, #120 }] */
    /* JADX WARN: Removed duplicated region for block: B:622:0x1540  */
    /* JADX WARN: Removed duplicated region for block: B:631:0x1588 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:643:0x1606  */
    /* JADX WARN: Removed duplicated region for block: B:646:0x1622  */
    /* JADX WARN: Removed duplicated region for block: B:649:0x164a  */
    /* JADX WARN: Removed duplicated region for block: B:655:0x16b7  */
    /* JADX WARN: Removed duplicated region for block: B:662:0x16f0 A[Catch: all -> 0x1729, TryCatch #9 {all -> 0x1729, blocks: (B:660:0x16ea, B:662:0x16f0, B:664:0x16f8, B:666:0x1700, B:668:0x1708, B:670:0x1710), top: B:659:0x16ea }] */
    /* JADX WARN: Removed duplicated region for block: B:676:0x173f A[Catch: all -> 0x175b, TryCatch #35 {all -> 0x175b, blocks: (B:674:0x1739, B:676:0x173f, B:678:0x1747), top: B:673:0x1739 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x043b  */
    /* JADX WARN: Removed duplicated region for block: B:682:0x177f  */
    /* JADX WARN: Removed duplicated region for block: B:685:0x1798  */
    /* JADX WARN: Removed duplicated region for block: B:689:0x17bb  */
    /* JADX WARN: Removed duplicated region for block: B:693:0x17de  */
    /* JADX WARN: Removed duplicated region for block: B:696:0x17f7  */
    /* JADX WARN: Removed duplicated region for block: B:699:0x1810  */
    /* JADX WARN: Removed duplicated region for block: B:707:0x1859  */
    /* JADX WARN: Removed duplicated region for block: B:709:0x186d  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x048d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:711:0x187e  */
    /* JADX WARN: Removed duplicated region for block: B:714:0x18b0  */
    /* JADX WARN: Removed duplicated region for block: B:722:0x18c6  */
    /* JADX WARN: Removed duplicated region for block: B:734:0x1973  */
    /* JADX WARN: Removed duplicated region for block: B:737:0x198e  */
    /* JADX WARN: Removed duplicated region for block: B:740:0x19a9  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x04d2 A[Catch: all -> 0x1f60, TryCatch #40 {all -> 0x1f60, blocks: (B:10:0x008d, B:13:0x00b2, B:15:0x00c6, B:18:0x00dd, B:19:0x00e8, B:21:0x0100, B:22:0x0119, B:25:0x015c, B:26:0x016a, B:28:0x0174, B:30:0x017e, B:32:0x0197, B:35:0x01d6, B:1047:0x0200, B:1053:0x0235, B:1061:0x0246, B:1062:0x0249, B:38:0x024e, B:49:0x02a5, B:51:0x031e, B:52:0x032d, B:54:0x035a, B:55:0x036c, B:58:0x0387, B:59:0x039e, B:61:0x03cb, B:62:0x03e9, B:65:0x0421, B:68:0x043e, B:71:0x048f, B:72:0x049e, B:74:0x04d2, B:75:0x04ff, B:77:0x0515, B:78:0x054e, B:80:0x0560, B:82:0x0565, B:84:0x0599, B:86:0x05c7, B:88:0x05b5, B:91:0x05bd, B:92:0x05ca, B:94:0x05fb, B:96:0x0601, B:97:0x0610, B:100:0x0673, B:991:0x0704, B:1000:0x0714, B:1001:0x0717, B:1005:0x066e, B:1009:0x0544, B:1010:0x04da, B:1012:0x04e6, B:1013:0x04ee, B:1018:0x041c, B:1027:0x1f55, B:1028:0x1f58, B:1034:0x029a, B:1040:0x026b, B:1073:0x024a, B:1074:0x024d, B:1076:0x01cf, B:1077:0x0188, B:1085:0x1f5a, B:1086:0x1f5f, B:1090:0x0112, B:1092:0x00d6, B:1093:0x00e1, B:1095:0x00ab, B:1049:0x0211, B:1051:0x0225, B:1052:0x0232, B:1058:0x022b, B:1065:0x023e, B:1082:0x0162, B:24:0x014d, B:99:0x0640, B:12:0x00a0, B:34:0x01c4, B:17:0x00cb, B:1046:0x01ea, B:1069:0x0209, B:990:0x06b1, B:996:0x070a, B:64:0x03ef), top: B:9:0x008d, inners: #12, #21, #33, #39, #47, #64, #76, #89, #93, #106, #110, #113, #118, #120 }] */
    /* JADX WARN: Removed duplicated region for block: B:785:0x1324  */
    /* JADX WARN: Removed duplicated region for block: B:791:0x12ff  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0560 A[Catch: all -> 0x1f60, TRY_LEAVE, TryCatch #40 {all -> 0x1f60, blocks: (B:10:0x008d, B:13:0x00b2, B:15:0x00c6, B:18:0x00dd, B:19:0x00e8, B:21:0x0100, B:22:0x0119, B:25:0x015c, B:26:0x016a, B:28:0x0174, B:30:0x017e, B:32:0x0197, B:35:0x01d6, B:1047:0x0200, B:1053:0x0235, B:1061:0x0246, B:1062:0x0249, B:38:0x024e, B:49:0x02a5, B:51:0x031e, B:52:0x032d, B:54:0x035a, B:55:0x036c, B:58:0x0387, B:59:0x039e, B:61:0x03cb, B:62:0x03e9, B:65:0x0421, B:68:0x043e, B:71:0x048f, B:72:0x049e, B:74:0x04d2, B:75:0x04ff, B:77:0x0515, B:78:0x054e, B:80:0x0560, B:82:0x0565, B:84:0x0599, B:86:0x05c7, B:88:0x05b5, B:91:0x05bd, B:92:0x05ca, B:94:0x05fb, B:96:0x0601, B:97:0x0610, B:100:0x0673, B:991:0x0704, B:1000:0x0714, B:1001:0x0717, B:1005:0x066e, B:1009:0x0544, B:1010:0x04da, B:1012:0x04e6, B:1013:0x04ee, B:1018:0x041c, B:1027:0x1f55, B:1028:0x1f58, B:1034:0x029a, B:1040:0x026b, B:1073:0x024a, B:1074:0x024d, B:1076:0x01cf, B:1077:0x0188, B:1085:0x1f5a, B:1086:0x1f5f, B:1090:0x0112, B:1092:0x00d6, B:1093:0x00e1, B:1095:0x00ab, B:1049:0x0211, B:1051:0x0225, B:1052:0x0232, B:1058:0x022b, B:1065:0x023e, B:1082:0x0162, B:24:0x014d, B:99:0x0640, B:12:0x00a0, B:34:0x01c4, B:17:0x00cb, B:1046:0x01ea, B:1069:0x0209, B:990:0x06b1, B:996:0x070a, B:64:0x03ef), top: B:9:0x008d, inners: #12, #21, #33, #39, #47, #64, #76, #89, #93, #106, #110, #113, #118, #120 }] */
    /* JADX WARN: Removed duplicated region for block: B:855:0x0f64  */
    /* JADX WARN: Removed duplicated region for block: B:907:0x0cfc  */
    /* JADX WARN: Removed duplicated region for block: B:908:0x0cdc  */
    /* JADX WARN: Removed duplicated region for block: B:915:0x0c31 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:923:0x0bff  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x05fb A[Catch: all -> 0x1f60, TryCatch #40 {all -> 0x1f60, blocks: (B:10:0x008d, B:13:0x00b2, B:15:0x00c6, B:18:0x00dd, B:19:0x00e8, B:21:0x0100, B:22:0x0119, B:25:0x015c, B:26:0x016a, B:28:0x0174, B:30:0x017e, B:32:0x0197, B:35:0x01d6, B:1047:0x0200, B:1053:0x0235, B:1061:0x0246, B:1062:0x0249, B:38:0x024e, B:49:0x02a5, B:51:0x031e, B:52:0x032d, B:54:0x035a, B:55:0x036c, B:58:0x0387, B:59:0x039e, B:61:0x03cb, B:62:0x03e9, B:65:0x0421, B:68:0x043e, B:71:0x048f, B:72:0x049e, B:74:0x04d2, B:75:0x04ff, B:77:0x0515, B:78:0x054e, B:80:0x0560, B:82:0x0565, B:84:0x0599, B:86:0x05c7, B:88:0x05b5, B:91:0x05bd, B:92:0x05ca, B:94:0x05fb, B:96:0x0601, B:97:0x0610, B:100:0x0673, B:991:0x0704, B:1000:0x0714, B:1001:0x0717, B:1005:0x066e, B:1009:0x0544, B:1010:0x04da, B:1012:0x04e6, B:1013:0x04ee, B:1018:0x041c, B:1027:0x1f55, B:1028:0x1f58, B:1034:0x029a, B:1040:0x026b, B:1073:0x024a, B:1074:0x024d, B:1076:0x01cf, B:1077:0x0188, B:1085:0x1f5a, B:1086:0x1f5f, B:1090:0x0112, B:1092:0x00d6, B:1093:0x00e1, B:1095:0x00ab, B:1049:0x0211, B:1051:0x0225, B:1052:0x0232, B:1058:0x022b, B:1065:0x023e, B:1082:0x0162, B:24:0x014d, B:99:0x0640, B:12:0x00a0, B:34:0x01c4, B:17:0x00cb, B:1046:0x01ea, B:1069:0x0209, B:990:0x06b1, B:996:0x070a, B:64:0x03ef), top: B:9:0x008d, inners: #12, #21, #33, #39, #47, #64, #76, #89, #93, #106, #110, #113, #118, #120 }] */
    /* JADX WARN: Removed duplicated region for block: B:952:0x094f A[Catch: all -> 0x0986, TRY_LEAVE, TryCatch #57 {all -> 0x0986, blocks: (B:146:0x0931, B:148:0x0947, B:952:0x094f), top: B:145:0x0931 }] */
    /* JADX WARN: Removed duplicated region for block: B:956:0x08e9 A[Catch: all -> 0x0920, TRY_LEAVE, TryCatch #1 {all -> 0x0920, blocks: (B:140:0x08cb, B:142:0x08e1, B:956:0x08e9), top: B:139:0x08cb }] */
    /* JADX WARN: Removed duplicated region for block: B:985:0x07c9  */
    /* JADX WARN: Removed duplicated region for block: B:986:0x0735  */
    /* JADX WARN: Removed duplicated region for block: B:989:0x06b1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r11v53, types: [android.os.IBinder, com.android.server.wm.WindowManagerService] */
    /* JADX WARN: Type inference failed for: r12v10, types: [android.os.IBinder, com.android.server.input.InputManagerService] */
    /* JADX WARN: Type inference failed for: r2v438, types: [android.os.IBinder, com.samsung.android.knox.custom.KnoxCustomManagerService] */
    /* JADX WARN: Type inference failed for: r2v511, types: [android.os.IBinder, com.android.server.DirEncryptService] */
    /* JADX WARN: Type inference failed for: r2v7, types: [android.os.IBinder, com.samsung.ucm.ucmservice.CredentialManagerService] */
    /* JADX WARN: Type inference failed for: r4v114, types: [android.os.IBinder, com.android.server.statusbar.StatusBarManagerService] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startOtherServices(final TimingsTraceAndSlog timingsTraceAndSlog) {
        ITelephonyRegistry.Stub telephonyRegistry;
        Throwable th;
        PersonaManagerService personaManagerService;
        PersonaManagerService personaManagerService2;
        Throwable th2;
        IKnoxContainerManager.Stub stub;
        TelephonyRegistry telephonyRegistry2;
        IKnoxContainerManager.Stub stub2;
        Throwable th3;
        ?? main;
        final boolean detectSafeMode;
        boolean z;
        String string;
        ILockSettings iLockSettings;
        ILockSettings iLockSettings2;
        DevicePolicyManagerService.Lifecycle lifecycle;
        Throwable th4;
        INetworkManagementService iNetworkManagementService;
        VpnManagerService vpnManagerService;
        UrspService urspService;
        CountryDetectorService countryDetectorService;
        NetworkTimeUpdateService networkTimeUpdateService;
        boolean hasSystemFeature;
        boolean hasSystemFeature2;
        boolean hasSystemFeature3;
        IBinder iBinder;
        final NetworkPolicyManagerService networkPolicyManagerService;
        MediaRouterService mediaRouterService;
        ILockSettings iLockSettings3;
        VcnManagementService vcnManagementService;
        final DevicePolicyManagerService.Lifecycle lifecycle2;
        NetworkTimeUpdateService networkTimeUpdateService2;
        PackageManager packageManager;
        PackageManager packageManager2;
        Throwable th5;
        RuntimeException runtimeException;
        SpegService spegService;
        PackageManager packageManager3;
        Object startService;
        MmsServiceBroker mmsServiceBroker;
        final HsumBootUserInitializer createInstance;
        int i;
        Class loadClass;
        Class loadClass2;
        ISemPersonaManager.Stub personaManagerService3;
        timingsTraceAndSlog.traceBegin("startOtherServices");
        this.mSystemServiceManager.updateOtherServicesStartIndex();
        final Context context = this.mSystemContext;
        boolean z2 = SystemProperties.getBoolean("config.disable_systemtextclassifier", false);
        boolean z3 = SystemProperties.getBoolean("config.disable_networktime", false);
        boolean z4 = SystemProperties.getBoolean("config.disable_cameraservice", false);
        boolean equals = SystemProperties.get("ro.boot.qemu").equals("1");
        final boolean hasSystemFeature4 = context.getPackageManager().hasSystemFeature("android.hardware.type.watch");
        boolean hasSystemFeature5 = context.getPackageManager().hasSystemFeature("org.chromium.arc");
        boolean hasSystemFeature6 = context.getPackageManager().hasSystemFeature("android.software.leanback");
        boolean hasSystemFeature7 = context.getPackageManager().hasSystemFeature("android.hardware.vr.high_performance");
        final boolean equals2 = "factory".equals(SystemProperties.get("ro.factory.factory_binary"));
        if (Build.IS_DEBUGGABLE && SystemProperties.getBoolean("debug.crash_system", false)) {
            throw new RuntimeException();
        }
        timingsTraceAndSlog.traceBegin("lazy_service");
        new ServiceManager().initLazyServiceManager(this.mSystemContext);
        timingsTraceAndSlog.traceEnd();
        try {
            this.mZygotePreload = SystemServerInitThreadPool.submit(new Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    SystemServer.lambda$startOtherServices$1();
                }
            }, "SecondaryZygotePreload");
            timingsTraceAndSlog.traceBegin("start Reactive Service");
            try {
                ServiceManager.addService("ReactiveService", new ReactiveService(context));
            } catch (Throwable unused) {
                Slog.e("SystemServer", "Failed to add Reactive Service.");
            }
            timingsTraceAndSlog.traceEnd();
            Slog.i("SystemServer", "MAINLINE_API_LEVEL: 33");
            if (Integer.parseInt("33") >= 34) {
                timingsTraceAndSlog.traceBegin("RealTimeTokenService: Real Time Token Service");
                try {
                    ServiceManager.addService("RealTimeTokenService", new RealTimeTokenService(context));
                } catch (Throwable unused2) {
                    Slog.e("SystemServer", "Failed to add RealTimeTokenService");
                }
                timingsTraceAndSlog.traceEnd();
            } else {
                Slog.i("SystemServer", "RealTimeTokenService not supported MAINLINE_API_LEVEL < 34");
            }
            timingsTraceAndSlog.traceBegin("StartKeyAttestationApplicationIdProviderService");
            ServiceManager.addService("sec_key_att_app_id_provider", new KeyAttestationApplicationIdProviderService(context));
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartSpqrService");
            try {
                LocalServices.addService(SpqrService.class, (SpqrService) this.mSystemServiceManager.startService(SpqrService.class));
            } catch (RuntimeException e) {
                Slog.e("SystemServer", "Failure starting Spqr Service", e);
            }
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartKeyChainSystemService");
            this.mSystemServiceManager.startService(KeyChainSystemService.class);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartBinaryTransparencyService");
            this.mSystemServiceManager.startService(BinaryTransparencyService.class);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartSchedulingPolicyService");
            ServiceManager.addService("scheduling_policy", new SchedulingPolicyService());
            timingsTraceAndSlog.traceEnd();
            try {
                timingsTraceAndSlog.traceBegin("SEAMS");
                ServiceManager.addService("SEAMService", new SEAMService(context));
            } finally {
                try {
                    timingsTraceAndSlog.traceEnd();
                    if (!this.mPackageManager.hasSystemFeature("android.hardware.microphone")) {
                    }
                    timingsTraceAndSlog.traceBegin("StartTelecomLoaderService");
                    this.mSystemServiceManager.startService(TelecomLoaderService.class);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartTelephonyRegistry");
                    telephonyRegistry = new TelephonyRegistry(context, new TelephonyRegistry.ConfigurationProvider());
                    ServiceManager.addService("telephony.registry", telephonyRegistry);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartEntropyMixer");
                    this.mEntropyMixer = new EntropyMixer(context);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("start Secure AT Service");
                    ServiceManager.addService("SatsService", new SatsService(context));
                    timingsTraceAndSlog.traceEnd();
                    if (!"factory".equals(SystemProperties.get("ro.factory.factory_binary"))) {
                    }
                    timingsTraceAndSlog.traceBegin("StartPersonaManager");
                    Slog.i("SystemServer", "Persona Service");
                    personaManagerService3 = PersonaManagerService.getInstance();
                    try {
                        ServiceManager.addService("persona", personaManagerService3);
                        personaManagerService2 = personaManagerService3;
                    } catch (Throwable th6) {
                        th = th6;
                        personaManagerService = personaManagerService3;
                        PersonaManagerService personaManagerService4 = personaManagerService;
                        Slog.e("SystemServer", "Failure starting Persona Manager Service", th);
                        personaManagerService2 = personaManagerService4;
                        Slog.i("SystemServer", "KnoxMUMContainerPolicy Service");
                        stub2 = new KnoxMUMContainerPolicy(context);
                        ServiceManager.addService("mum_container_policy", stub2);
                        ServiceManager.addService("mum_container_policy", stub2);
                        telephonyRegistry2 = telephonyRegistry;
                        timingsTraceAndSlog.traceEnd();
                        startRCPService(context, timingsTraceAndSlog, false);
                        startDualAppManagerService(context, timingsTraceAndSlog);
                        this.mContentResolver = context.getContentResolver();
                        timingsTraceAndSlog.traceBegin("StartAccountManagerService");
                        this.mSystemServiceManager.startService("com.android.server.accounts.AccountManagerService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartContentService");
                        this.mSystemServiceManager.startService("com.android.server.content.ContentService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("InstallSystemProviders");
                        this.mActivityManagerService.getContentProviderHelper().installSystemProviders();
                        this.mSystemServiceManager.startService("com.android.server.deviceconfig.DeviceConfigInit$Lifecycle");
                        SQLiteCompatibilityWalFlags.reset();
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartDropBoxManager");
                        this.mSystemServiceManager.startService(DropBoxManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartRoleManagerService");
                        IKnoxContainerManager.Stub stub3 = stub2;
                        LocalManagerRegistry.addManager(RoleServicePlatformHelper.class, new RoleServicePlatformHelperImpl(this.mSystemContext));
                        this.mSystemServiceManager.startService("com.android.role.RoleService");
                        timingsTraceAndSlog.traceEnd();
                        if (UnionUtils.FEATURE_ENABLED) {
                        }
                        timingsTraceAndSlog.traceBegin("StartVibratorManagerService");
                        this.mSystemServiceManager.startService(VibratorManagerService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartDynamicSystemService");
                        ServiceManager.addService("dynamic_system", new DynamicSystemService(context));
                        timingsTraceAndSlog.traceEnd();
                        if (context.getPackageManager().hasSystemFeature("android.hardware.consumerir")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartResourceEconomy");
                        this.mSystemServiceManager.startService("com.android.server.tare.InternalResourceService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("SSRM Service");
                        this.mSystemServiceManager.startService("com.android.server.ssrm.CustomFrequencyManagerService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartAlarmManagerService");
                        this.mSystemServiceManager.startService("com.android.server.alarm.AlarmManagerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartInputManagerService");
                        final ?? inputManagerService = new InputManagerService(context);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("DeviceStateManagerService");
                        this.mSystemServiceManager.startService(DeviceStateManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (!z4) {
                        }
                        timingsTraceAndSlog.traceBegin("powerSolutionManagerService");
                        Slog.i("SystemServer", "powerSolutionManagerService");
                        ServiceManager.addService("PowerSolution_Framework_service", (IBinder) Class.forName("com.samsung.android.powerSolution.powerSolution").getConstructor(Context.class).newInstance(context));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartWindowManagerService");
                        Slog.i("SystemServer", "!@Boot_EBS_F: Start WindowManagerService");
                        this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 200);
                        main = WindowManagerService.main(context, inputManagerService, this.mFirstBoot, new PhoneWindowManager(), this.mActivityManagerService.mActivityTaskManager);
                        PersonaManagerService personaManagerService5 = personaManagerService2;
                        ServiceManager.addService("window", (IBinder) main, false, 17);
                        ServiceManager.addService("input", (IBinder) inputManagerService, false, 1);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("SetWindowManagerService");
                        this.mActivityManagerService.setWindowManager(main);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("WindowManagerServiceOnInitReady");
                        main.onInitReady();
                        timingsTraceAndSlog.traceEnd();
                        SystemServerInitThreadPool.submit(new Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                SystemServer.lambda$startOtherServices$2();
                            }
                        }, "StartISensorManagerService");
                        SystemServerInitThreadPool.submit(new Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                SystemServer.lambda$startOtherServices$3();
                            }
                        }, "StartHidlServices");
                        if (!hasSystemFeature4) {
                        }
                        timingsTraceAndSlog.traceBegin("startDesktopModeService");
                        this.mSystemServiceManager.startService(DesktopModeService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartInputManager");
                        inputManagerService.setWindowManagerCallbacks(main.getInputManagerCallback());
                        inputManagerService.start();
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("DisplayManagerWindowManagerAndInputReady");
                        this.mDisplayManagerService.windowManagerAndInputReady();
                        timingsTraceAndSlog.traceEnd();
                        if (this.mFactoryTestMode != 1) {
                        }
                        timingsTraceAndSlog.traceBegin("StartMultiControlManagerService");
                        this.mSystemServiceManager.startService(MultiControlManagerService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        Slog.i("SystemServer", "Hqm Service");
                        ServiceManager.addService("HqmManagerService", (IBinder) new PathClassLoader("/system/framework/hqm.jar", ClassLoader.getSystemClassLoader()).loadClass("com.samsung.android.hqm.HqmManagerService").getConstructor(Context.class).newInstance(context));
                        if (!"0".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SYSTEM_CONFIG_HCM_AI_POWER_SAVING_LEVEL"))) {
                        }
                        timingsTraceAndSlog.traceBegin(IpConnectivityMetrics.TAG);
                        this.mSystemServiceManager.startService("com.android.server.connectivity.IpConnectivityMetrics");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("NetworkWatchlistService");
                        this.mSystemServiceManager.startService(NetworkWatchlistService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("PinnerService");
                        this.mSystemServiceManager.startService(PinnerService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (Build.IS_DEBUGGABLE) {
                        }
                        timingsTraceAndSlog.traceBegin("SignedConfigService");
                        SignedConfigService.registerUpdateReceiver(this.mSystemContext);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("AppIntegrityService");
                        this.mSystemServiceManager.startService(AppIntegrityManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartLogcatManager");
                        this.mSystemServiceManager.startService(LogcatManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("SemInputDeviceManagerService");
                        Slog.i("SystemServer", "SemInputDeviceManagerService loader");
                        Class<?> cls = Class.forName("com.samsung.android.hardware.secinputdev.SemInputDeviceManagerLoader");
                        ServiceManager.addService("SemInputDeviceManagerService", (IBinder) cls.getDeclaredMethod("getService", Context.class).invoke(cls, context));
                        timingsTraceAndSlog.traceEnd();
                        Slog.i("SystemServer", "start samsung apex services");
                        timingsTraceAndSlog.traceBegin("SecIpmManagerService");
                        this.mSystemServiceManager.startService("com.android.server.ipm.SecIpmManagerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("SemPrivilegeManagerService");
                        this.mSystemServiceManager.startService("com.android.server.privilege.SemPrivilegeManagerService");
                        timingsTraceAndSlog.traceEnd();
                        Slog.i("SystemServer", "finish samsung apex services");
                        if (Boolean.parseBoolean(SystemProperties.get("sys.config.hardcoder.enable"))) {
                        }
                        timingsTraceAndSlog.traceBegin("detectSafeMode");
                        Slog.i("SystemServer", "!@Boot_EBS_D: detectSafeMode");
                        detectSafeMode = main.detectSafeMode();
                        if (!detectSafeMode) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        if (this.mFactoryTestMode == 1) {
                        }
                        timingsTraceAndSlog.traceBegin("MakeDisplayReady");
                        main.displayReady();
                        timingsTraceAndSlog.traceEnd();
                        string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_CHIP_VENDOR");
                        if (string.length() > 0) {
                        }
                        timingsTraceAndSlog.traceBegin("SamsungGameManager");
                        this.mSystemServiceManager.startService(new PathClassLoader("/system/framework/gamemanager.jar", SystemServer.class.getClassLoader()).loadClass("com.samsung.android.game.GameManagerService$Lifecycle"));
                        Slog.i("SystemServer", "SamsungGameManager Started");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("GameSDKService");
                        Slog.i("SystemServer", "GameSDKService");
                        ServiceManager.addService("gamesdk", (IBinder) new PathClassLoader("/system/framework/gamesdk.jar", SystemServer.class.getClassLoader()).loadClass("com.samsung.android.gamesdk.GameSDKService").getConstructor(Context.class, IActivityManager.class).newInstance(context, this.mActivityManagerService));
                        Slog.i("SystemServer", "GameSDKService loaded");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartRemoteAppModeService");
                        this.mSystemServiceManager.startService(RemoteAppModeService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("IcccManagerService");
                        ServiceManager.addService("iccc", new IServiceCreator() { // from class: com.android.server.SystemServer.3
                            public IBinder createService(Context context2) {
                                return new IcccManagerService(context2);
                            }
                        });
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("SehCodecSolutionService");
                        loadClass2 = new PathClassLoader("/system/framework/vendor.samsung.frameworks.codecsolution-service.jar", SystemServer.class.getClassLoader()).loadClass("vendor.samsung.frameworks.codecsolution.SehCodecSolutionService");
                        if (loadClass2 == null) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("SehHdrSolutionService");
                        loadClass = new PathClassLoader("/system/framework/vendor.samsung.frameworks.hdrsolution-service.jar", SystemServer.class.getClassLoader()).loadClass("vendor.samsung.frameworks.hdrsolution.SehHdrSolutionService");
                        if (loadClass == null) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        if (!CoreRune.SYSPERF_JDM_MODEL) {
                        }
                        if (this.mFactoryTestMode != 1) {
                        }
                        timingsTraceAndSlog.traceBegin("StartUiModeManager");
                        this.mSystemServiceManager.startService(UiModeManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartLocaleManagerService");
                        this.mSystemServiceManager.startService(LocaleManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartGrammarInflectionService");
                        this.mSystemServiceManager.startService(GrammaticalInflectionService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartAppHibernationService");
                        this.mSystemServiceManager.startService("com.android.server.apphibernation.AppHibernationService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("ArtManagerLocal");
                        DexOptHelper.initializeArtManagerLocal(context, this.mPackageManagerService);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("UpdatePackagesIfNeeded");
                        Slog.i("SystemServer", "!@Boot_EBS_D: UpdatePackagesIfNeeded");
                        Watchdog.getInstance().pauseWatchingCurrentThread("dexopt");
                        this.mPackageManagerService.updatePackagesIfNeeded();
                        Watchdog.getInstance().resumeWatchingCurrentThread("dexopt");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("PerformFstrimIfNeeded");
                        this.mPackageManagerService.performFstrimIfNeeded();
                        timingsTraceAndSlog.traceEnd();
                        if (this.mFactoryTestMode != 1) {
                        }
                        final IBinder iBinder2 = null;
                        timingsTraceAndSlog.traceBegin("StartMediaProjectionManager");
                        this.mSystemServiceManager.startService(MediaProjectionManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (!this.mPackageManager.hasSystemFeature("att.devicehealth.support")) {
                        }
                        if (hasSystemFeature4) {
                        }
                        if (!this.mPackageManager.hasSystemFeature("android.software.slices_disabled")) {
                        }
                        if (context.getPackageManager().hasSystemFeature("android.hardware.type.embedded")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartSpenGestureManagerService");
                        ServiceManager.addService("spengestureservice", new SpenGestureManagerService(context, main));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartSamsungNotesService");
                        this.mSamsungNotesService = new SamsungNotesService(context);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartStatsCompanion");
                        this.mSystemServiceManager.startServiceFromJar("com.android.server.stats.StatsCompanion$Lifecycle", "/apex/com.android.os.statsd/javalib/service-statsd.jar");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartRebootReadinessManagerService");
                        this.mSystemServiceManager.startServiceFromJar("com.android.server.scheduling.RebootReadinessManagerService$Lifecycle", "/apex/com.android.scheduling/javalib/service-scheduling.jar");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartStatsPullAtomService");
                        this.mSystemServiceManager.startService("com.android.server.stats.pull.StatsPullAtomService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StatsBootstrapAtomService");
                        this.mSystemServiceManager.startService("com.android.server.stats.bootstrap.StatsBootstrapAtomService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartIncidentCompanionService");
                        this.mSystemServiceManager.startService(IncidentCompanionService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StarSdkSandboxManagerService");
                        this.mSystemServiceManager.startService("com.android.server.sdksandbox.SdkSandboxManagerService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartAdServicesManagerService");
                        startService = this.mSystemServiceManager.startService("com.android.server.adservices.AdServicesManagerService$Lifecycle");
                        if (startService instanceof Dumpable) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartOnDevicePersonalizationSystemService");
                        this.mSystemServiceManager.startService("com.android.server.ondevicepersonalization.OnDevicePersonalizationSystemService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        if (detectSafeMode) {
                        }
                        final boolean z5 = context.getResources().getBoolean(R.bool.config_restartRadioAfterProvisioning);
                        if (this.mPackageManager.hasSystemFeature("android.hardware.telephony")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartMmsService");
                        MmsServiceBroker mmsServiceBroker2 = (MmsServiceBroker) this.mSystemServiceManager.startService(MmsServiceBroker.class);
                        timingsTraceAndSlog.traceEnd();
                        mmsServiceBroker = mmsServiceBroker2;
                        if (this.mPackageManager.hasSystemFeature("android.software.autofill")) {
                        }
                        if (this.mPackageManager.hasSystemFeature("android.software.credentials")) {
                        }
                        if (!deviceHasConfigString(context, R.string.default_audio_route_category_name)) {
                        }
                        timingsTraceAndSlog.traceBegin("StartSelectionToolbarManagerService");
                        this.mSystemServiceManager.startService("com.android.server.selectiontoolbar.SelectionToolbarManagerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartClipboardService");
                        this.mSystemServiceManager.startService(ClipboardService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("AppServiceManager");
                        this.mSystemServiceManager.startService(AppBindingService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("startTracingServiceProxy");
                        this.mSystemServiceManager.startService(TracingServiceProxy.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("MakeLockSettingsServiceReady");
                        if (iLockSettings3 != null) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartPersonaSystemready");
                        if ("2.0".equals(SemPersonaManager.getKnoxInfo().getString("version"))) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartBootPhaseLockSettingsReady");
                        Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_LOCK_SETTINGS_READY");
                        this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, SystemService.PHASE_LOCK_SETTINGS_READY);
                        timingsTraceAndSlog.traceEnd();
                        createInstance = HsumBootUserInitializer.createInstance(this.mActivityManagerService, this.mPackageManagerService, this.mContentResolver, context.getResources().getBoolean(R.bool.config_focusScrollContainersInTouchMode));
                        if (createInstance != null) {
                        }
                        timingsTraceAndSlog.traceBegin("StartBootPhaseSystemServicesReady");
                        Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_SYSTEM_SERVICES_READY");
                        this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 500);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("MakeWindowManagerServiceReady");
                        main.systemReady();
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("RegisterLogMteState");
                        LogMteState.register(context);
                        timingsTraceAndSlog.traceEnd();
                        synchronized (SystemService.class) {
                        }
                    }
                    Slog.i("SystemServer", "KnoxMUMContainerPolicy Service");
                    stub2 = new KnoxMUMContainerPolicy(context);
                    try {
                        ServiceManager.addService("mum_container_policy", stub2);
                        ServiceManager.addService("mum_container_policy", stub2);
                        telephonyRegistry2 = telephonyRegistry;
                    } catch (Throwable th7) {
                        th2 = th7;
                        stub = stub2;
                        telephonyRegistry2 = telephonyRegistry;
                        Slog.e("SystemServer", "Failure starting KnoxMUMContainerPolicy Service", th2);
                        stub2 = stub;
                        timingsTraceAndSlog.traceEnd();
                        startRCPService(context, timingsTraceAndSlog, false);
                        startDualAppManagerService(context, timingsTraceAndSlog);
                        this.mContentResolver = context.getContentResolver();
                        timingsTraceAndSlog.traceBegin("StartAccountManagerService");
                        this.mSystemServiceManager.startService("com.android.server.accounts.AccountManagerService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartContentService");
                        this.mSystemServiceManager.startService("com.android.server.content.ContentService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("InstallSystemProviders");
                        this.mActivityManagerService.getContentProviderHelper().installSystemProviders();
                        this.mSystemServiceManager.startService("com.android.server.deviceconfig.DeviceConfigInit$Lifecycle");
                        SQLiteCompatibilityWalFlags.reset();
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartDropBoxManager");
                        this.mSystemServiceManager.startService(DropBoxManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartRoleManagerService");
                        IKnoxContainerManager.Stub stub32 = stub2;
                        LocalManagerRegistry.addManager(RoleServicePlatformHelper.class, new RoleServicePlatformHelperImpl(this.mSystemContext));
                        this.mSystemServiceManager.startService("com.android.role.RoleService");
                        timingsTraceAndSlog.traceEnd();
                        if (UnionUtils.FEATURE_ENABLED) {
                        }
                        timingsTraceAndSlog.traceBegin("StartVibratorManagerService");
                        this.mSystemServiceManager.startService(VibratorManagerService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartDynamicSystemService");
                        ServiceManager.addService("dynamic_system", new DynamicSystemService(context));
                        timingsTraceAndSlog.traceEnd();
                        if (context.getPackageManager().hasSystemFeature("android.hardware.consumerir")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartResourceEconomy");
                        this.mSystemServiceManager.startService("com.android.server.tare.InternalResourceService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("SSRM Service");
                        this.mSystemServiceManager.startService("com.android.server.ssrm.CustomFrequencyManagerService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartAlarmManagerService");
                        this.mSystemServiceManager.startService("com.android.server.alarm.AlarmManagerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartInputManagerService");
                        final ?? inputManagerService2 = new InputManagerService(context);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("DeviceStateManagerService");
                        this.mSystemServiceManager.startService(DeviceStateManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (!z4) {
                        }
                        timingsTraceAndSlog.traceBegin("powerSolutionManagerService");
                        Slog.i("SystemServer", "powerSolutionManagerService");
                        ServiceManager.addService("PowerSolution_Framework_service", (IBinder) Class.forName("com.samsung.android.powerSolution.powerSolution").getConstructor(Context.class).newInstance(context));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartWindowManagerService");
                        Slog.i("SystemServer", "!@Boot_EBS_F: Start WindowManagerService");
                        this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 200);
                        main = WindowManagerService.main(context, inputManagerService2, this.mFirstBoot, new PhoneWindowManager(), this.mActivityManagerService.mActivityTaskManager);
                        PersonaManagerService personaManagerService52 = personaManagerService2;
                        ServiceManager.addService("window", (IBinder) main, false, 17);
                        ServiceManager.addService("input", (IBinder) inputManagerService2, false, 1);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("SetWindowManagerService");
                        this.mActivityManagerService.setWindowManager(main);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("WindowManagerServiceOnInitReady");
                        main.onInitReady();
                        timingsTraceAndSlog.traceEnd();
                        SystemServerInitThreadPool.submit(new Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                SystemServer.lambda$startOtherServices$2();
                            }
                        }, "StartISensorManagerService");
                        SystemServerInitThreadPool.submit(new Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                SystemServer.lambda$startOtherServices$3();
                            }
                        }, "StartHidlServices");
                        if (!hasSystemFeature4) {
                        }
                        timingsTraceAndSlog.traceBegin("startDesktopModeService");
                        this.mSystemServiceManager.startService(DesktopModeService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartInputManager");
                        inputManagerService2.setWindowManagerCallbacks(main.getInputManagerCallback());
                        inputManagerService2.start();
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("DisplayManagerWindowManagerAndInputReady");
                        this.mDisplayManagerService.windowManagerAndInputReady();
                        timingsTraceAndSlog.traceEnd();
                        if (this.mFactoryTestMode != 1) {
                        }
                        timingsTraceAndSlog.traceBegin("StartMultiControlManagerService");
                        this.mSystemServiceManager.startService(MultiControlManagerService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        Slog.i("SystemServer", "Hqm Service");
                        ServiceManager.addService("HqmManagerService", (IBinder) new PathClassLoader("/system/framework/hqm.jar", ClassLoader.getSystemClassLoader()).loadClass("com.samsung.android.hqm.HqmManagerService").getConstructor(Context.class).newInstance(context));
                        if (!"0".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SYSTEM_CONFIG_HCM_AI_POWER_SAVING_LEVEL"))) {
                        }
                        timingsTraceAndSlog.traceBegin(IpConnectivityMetrics.TAG);
                        this.mSystemServiceManager.startService("com.android.server.connectivity.IpConnectivityMetrics");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("NetworkWatchlistService");
                        this.mSystemServiceManager.startService(NetworkWatchlistService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("PinnerService");
                        this.mSystemServiceManager.startService(PinnerService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (Build.IS_DEBUGGABLE) {
                        }
                        timingsTraceAndSlog.traceBegin("SignedConfigService");
                        SignedConfigService.registerUpdateReceiver(this.mSystemContext);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("AppIntegrityService");
                        this.mSystemServiceManager.startService(AppIntegrityManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartLogcatManager");
                        this.mSystemServiceManager.startService(LogcatManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("SemInputDeviceManagerService");
                        Slog.i("SystemServer", "SemInputDeviceManagerService loader");
                        Class<?> cls2 = Class.forName("com.samsung.android.hardware.secinputdev.SemInputDeviceManagerLoader");
                        ServiceManager.addService("SemInputDeviceManagerService", (IBinder) cls2.getDeclaredMethod("getService", Context.class).invoke(cls2, context));
                        timingsTraceAndSlog.traceEnd();
                        Slog.i("SystemServer", "start samsung apex services");
                        timingsTraceAndSlog.traceBegin("SecIpmManagerService");
                        this.mSystemServiceManager.startService("com.android.server.ipm.SecIpmManagerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("SemPrivilegeManagerService");
                        this.mSystemServiceManager.startService("com.android.server.privilege.SemPrivilegeManagerService");
                        timingsTraceAndSlog.traceEnd();
                        Slog.i("SystemServer", "finish samsung apex services");
                        if (Boolean.parseBoolean(SystemProperties.get("sys.config.hardcoder.enable"))) {
                        }
                        timingsTraceAndSlog.traceBegin("detectSafeMode");
                        Slog.i("SystemServer", "!@Boot_EBS_D: detectSafeMode");
                        detectSafeMode = main.detectSafeMode();
                        if (!detectSafeMode) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        if (this.mFactoryTestMode == 1) {
                        }
                        timingsTraceAndSlog.traceBegin("MakeDisplayReady");
                        main.displayReady();
                        timingsTraceAndSlog.traceEnd();
                        string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_CHIP_VENDOR");
                        if (string.length() > 0) {
                        }
                        timingsTraceAndSlog.traceBegin("SamsungGameManager");
                        this.mSystemServiceManager.startService(new PathClassLoader("/system/framework/gamemanager.jar", SystemServer.class.getClassLoader()).loadClass("com.samsung.android.game.GameManagerService$Lifecycle"));
                        Slog.i("SystemServer", "SamsungGameManager Started");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("GameSDKService");
                        Slog.i("SystemServer", "GameSDKService");
                        ServiceManager.addService("gamesdk", (IBinder) new PathClassLoader("/system/framework/gamesdk.jar", SystemServer.class.getClassLoader()).loadClass("com.samsung.android.gamesdk.GameSDKService").getConstructor(Context.class, IActivityManager.class).newInstance(context, this.mActivityManagerService));
                        Slog.i("SystemServer", "GameSDKService loaded");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartRemoteAppModeService");
                        this.mSystemServiceManager.startService(RemoteAppModeService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("IcccManagerService");
                        ServiceManager.addService("iccc", new IServiceCreator() { // from class: com.android.server.SystemServer.3
                            public IBinder createService(Context context2) {
                                return new IcccManagerService(context2);
                            }
                        });
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("SehCodecSolutionService");
                        loadClass2 = new PathClassLoader("/system/framework/vendor.samsung.frameworks.codecsolution-service.jar", SystemServer.class.getClassLoader()).loadClass("vendor.samsung.frameworks.codecsolution.SehCodecSolutionService");
                        if (loadClass2 == null) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("SehHdrSolutionService");
                        loadClass = new PathClassLoader("/system/framework/vendor.samsung.frameworks.hdrsolution-service.jar", SystemServer.class.getClassLoader()).loadClass("vendor.samsung.frameworks.hdrsolution.SehHdrSolutionService");
                        if (loadClass == null) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        if (!CoreRune.SYSPERF_JDM_MODEL) {
                        }
                        if (this.mFactoryTestMode != 1) {
                        }
                        timingsTraceAndSlog.traceBegin("StartUiModeManager");
                        this.mSystemServiceManager.startService(UiModeManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartLocaleManagerService");
                        this.mSystemServiceManager.startService(LocaleManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartGrammarInflectionService");
                        this.mSystemServiceManager.startService(GrammaticalInflectionService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartAppHibernationService");
                        this.mSystemServiceManager.startService("com.android.server.apphibernation.AppHibernationService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("ArtManagerLocal");
                        DexOptHelper.initializeArtManagerLocal(context, this.mPackageManagerService);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("UpdatePackagesIfNeeded");
                        Slog.i("SystemServer", "!@Boot_EBS_D: UpdatePackagesIfNeeded");
                        Watchdog.getInstance().pauseWatchingCurrentThread("dexopt");
                        this.mPackageManagerService.updatePackagesIfNeeded();
                        Watchdog.getInstance().resumeWatchingCurrentThread("dexopt");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("PerformFstrimIfNeeded");
                        this.mPackageManagerService.performFstrimIfNeeded();
                        timingsTraceAndSlog.traceEnd();
                        if (this.mFactoryTestMode != 1) {
                        }
                        final IBinder iBinder22 = null;
                        timingsTraceAndSlog.traceBegin("StartMediaProjectionManager");
                        this.mSystemServiceManager.startService(MediaProjectionManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (!this.mPackageManager.hasSystemFeature("att.devicehealth.support")) {
                        }
                        if (hasSystemFeature4) {
                        }
                        if (!this.mPackageManager.hasSystemFeature("android.software.slices_disabled")) {
                        }
                        if (context.getPackageManager().hasSystemFeature("android.hardware.type.embedded")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartSpenGestureManagerService");
                        ServiceManager.addService("spengestureservice", new SpenGestureManagerService(context, main));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartSamsungNotesService");
                        this.mSamsungNotesService = new SamsungNotesService(context);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartStatsCompanion");
                        this.mSystemServiceManager.startServiceFromJar("com.android.server.stats.StatsCompanion$Lifecycle", "/apex/com.android.os.statsd/javalib/service-statsd.jar");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartRebootReadinessManagerService");
                        this.mSystemServiceManager.startServiceFromJar("com.android.server.scheduling.RebootReadinessManagerService$Lifecycle", "/apex/com.android.scheduling/javalib/service-scheduling.jar");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartStatsPullAtomService");
                        this.mSystemServiceManager.startService("com.android.server.stats.pull.StatsPullAtomService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StatsBootstrapAtomService");
                        this.mSystemServiceManager.startService("com.android.server.stats.bootstrap.StatsBootstrapAtomService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartIncidentCompanionService");
                        this.mSystemServiceManager.startService(IncidentCompanionService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StarSdkSandboxManagerService");
                        this.mSystemServiceManager.startService("com.android.server.sdksandbox.SdkSandboxManagerService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartAdServicesManagerService");
                        startService = this.mSystemServiceManager.startService("com.android.server.adservices.AdServicesManagerService$Lifecycle");
                        if (startService instanceof Dumpable) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartOnDevicePersonalizationSystemService");
                        this.mSystemServiceManager.startService("com.android.server.ondevicepersonalization.OnDevicePersonalizationSystemService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        if (detectSafeMode) {
                        }
                        final boolean z52 = context.getResources().getBoolean(R.bool.config_restartRadioAfterProvisioning);
                        if (this.mPackageManager.hasSystemFeature("android.hardware.telephony")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartMmsService");
                        MmsServiceBroker mmsServiceBroker22 = (MmsServiceBroker) this.mSystemServiceManager.startService(MmsServiceBroker.class);
                        timingsTraceAndSlog.traceEnd();
                        mmsServiceBroker = mmsServiceBroker22;
                        if (this.mPackageManager.hasSystemFeature("android.software.autofill")) {
                        }
                        if (this.mPackageManager.hasSystemFeature("android.software.credentials")) {
                        }
                        if (!deviceHasConfigString(context, R.string.default_audio_route_category_name)) {
                        }
                        timingsTraceAndSlog.traceBegin("StartSelectionToolbarManagerService");
                        this.mSystemServiceManager.startService("com.android.server.selectiontoolbar.SelectionToolbarManagerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartClipboardService");
                        this.mSystemServiceManager.startService(ClipboardService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("AppServiceManager");
                        this.mSystemServiceManager.startService(AppBindingService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("startTracingServiceProxy");
                        this.mSystemServiceManager.startService(TracingServiceProxy.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("MakeLockSettingsServiceReady");
                        if (iLockSettings3 != null) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartPersonaSystemready");
                        if ("2.0".equals(SemPersonaManager.getKnoxInfo().getString("version"))) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartBootPhaseLockSettingsReady");
                        Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_LOCK_SETTINGS_READY");
                        this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, SystemService.PHASE_LOCK_SETTINGS_READY);
                        timingsTraceAndSlog.traceEnd();
                        createInstance = HsumBootUserInitializer.createInstance(this.mActivityManagerService, this.mPackageManagerService, this.mContentResolver, context.getResources().getBoolean(R.bool.config_focusScrollContainersInTouchMode));
                        if (createInstance != null) {
                        }
                        timingsTraceAndSlog.traceBegin("StartBootPhaseSystemServicesReady");
                        Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_SYSTEM_SERVICES_READY");
                        this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 500);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("MakeWindowManagerServiceReady");
                        main.systemReady();
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("RegisterLogMteState");
                        LogMteState.register(context);
                        timingsTraceAndSlog.traceEnd();
                        synchronized (SystemService.class) {
                        }
                    }
                    timingsTraceAndSlog.traceEnd();
                    startRCPService(context, timingsTraceAndSlog, false);
                    startDualAppManagerService(context, timingsTraceAndSlog);
                    this.mContentResolver = context.getContentResolver();
                    timingsTraceAndSlog.traceBegin("StartAccountManagerService");
                    this.mSystemServiceManager.startService("com.android.server.accounts.AccountManagerService$Lifecycle");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartContentService");
                    this.mSystemServiceManager.startService("com.android.server.content.ContentService$Lifecycle");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("InstallSystemProviders");
                    this.mActivityManagerService.getContentProviderHelper().installSystemProviders();
                    this.mSystemServiceManager.startService("com.android.server.deviceconfig.DeviceConfigInit$Lifecycle");
                    SQLiteCompatibilityWalFlags.reset();
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartDropBoxManager");
                    this.mSystemServiceManager.startService(DropBoxManagerService.class);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartRoleManagerService");
                    IKnoxContainerManager.Stub stub322 = stub2;
                    LocalManagerRegistry.addManager(RoleServicePlatformHelper.class, new RoleServicePlatformHelperImpl(this.mSystemContext));
                    this.mSystemServiceManager.startService("com.android.role.RoleService");
                    timingsTraceAndSlog.traceEnd();
                    if (UnionUtils.FEATURE_ENABLED) {
                    }
                    timingsTraceAndSlog.traceBegin("StartVibratorManagerService");
                    this.mSystemServiceManager.startService(VibratorManagerService.Lifecycle.class);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartDynamicSystemService");
                    ServiceManager.addService("dynamic_system", new DynamicSystemService(context));
                    timingsTraceAndSlog.traceEnd();
                    if (context.getPackageManager().hasSystemFeature("android.hardware.consumerir")) {
                    }
                    timingsTraceAndSlog.traceBegin("StartResourceEconomy");
                    this.mSystemServiceManager.startService("com.android.server.tare.InternalResourceService");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("SSRM Service");
                    this.mSystemServiceManager.startService("com.android.server.ssrm.CustomFrequencyManagerService$Lifecycle");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartAlarmManagerService");
                    this.mSystemServiceManager.startService("com.android.server.alarm.AlarmManagerService");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartInputManagerService");
                    final ?? inputManagerService22 = new InputManagerService(context);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("DeviceStateManagerService");
                    this.mSystemServiceManager.startService(DeviceStateManagerService.class);
                    timingsTraceAndSlog.traceEnd();
                    if (!z4) {
                    }
                    timingsTraceAndSlog.traceBegin("powerSolutionManagerService");
                    Slog.i("SystemServer", "powerSolutionManagerService");
                    ServiceManager.addService("PowerSolution_Framework_service", (IBinder) Class.forName("com.samsung.android.powerSolution.powerSolution").getConstructor(Context.class).newInstance(context));
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartWindowManagerService");
                    Slog.i("SystemServer", "!@Boot_EBS_F: Start WindowManagerService");
                    this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 200);
                    main = WindowManagerService.main(context, inputManagerService22, this.mFirstBoot, new PhoneWindowManager(), this.mActivityManagerService.mActivityTaskManager);
                    PersonaManagerService personaManagerService522 = personaManagerService2;
                    ServiceManager.addService("window", (IBinder) main, false, 17);
                    ServiceManager.addService("input", (IBinder) inputManagerService22, false, 1);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("SetWindowManagerService");
                    this.mActivityManagerService.setWindowManager(main);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("WindowManagerServiceOnInitReady");
                    main.onInitReady();
                    timingsTraceAndSlog.traceEnd();
                    SystemServerInitThreadPool.submit(new Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            SystemServer.lambda$startOtherServices$2();
                        }
                    }, "StartISensorManagerService");
                    SystemServerInitThreadPool.submit(new Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            SystemServer.lambda$startOtherServices$3();
                        }
                    }, "StartHidlServices");
                    if (!hasSystemFeature4) {
                        timingsTraceAndSlog.traceBegin("StartVrManagerService");
                        this.mSystemServiceManager.startService(VrManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                    }
                    timingsTraceAndSlog.traceBegin("startDesktopModeService");
                    this.mSystemServiceManager.startService(DesktopModeService.Lifecycle.class);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartInputManager");
                    inputManagerService22.setWindowManagerCallbacks(main.getInputManagerCallback());
                    inputManagerService22.start();
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("DisplayManagerWindowManagerAndInputReady");
                    this.mDisplayManagerService.windowManagerAndInputReady();
                    timingsTraceAndSlog.traceEnd();
                    if (this.mFactoryTestMode != 1) {
                    }
                    timingsTraceAndSlog.traceBegin("StartMultiControlManagerService");
                    this.mSystemServiceManager.startService(MultiControlManagerService.Lifecycle.class);
                    timingsTraceAndSlog.traceEnd();
                    Slog.i("SystemServer", "Hqm Service");
                    ServiceManager.addService("HqmManagerService", (IBinder) new PathClassLoader("/system/framework/hqm.jar", ClassLoader.getSystemClassLoader()).loadClass("com.samsung.android.hqm.HqmManagerService").getConstructor(Context.class).newInstance(context));
                    if (!"0".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SYSTEM_CONFIG_HCM_AI_POWER_SAVING_LEVEL"))) {
                    }
                    timingsTraceAndSlog.traceBegin(IpConnectivityMetrics.TAG);
                    this.mSystemServiceManager.startService("com.android.server.connectivity.IpConnectivityMetrics");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("NetworkWatchlistService");
                    this.mSystemServiceManager.startService(NetworkWatchlistService.Lifecycle.class);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("PinnerService");
                    this.mSystemServiceManager.startService(PinnerService.class);
                    timingsTraceAndSlog.traceEnd();
                    if (Build.IS_DEBUGGABLE) {
                        timingsTraceAndSlog.traceBegin("ProfcollectForwardingService");
                        this.mSystemServiceManager.startService(ProfcollectForwardingService.class);
                        timingsTraceAndSlog.traceEnd();
                    }
                    timingsTraceAndSlog.traceBegin("SignedConfigService");
                    SignedConfigService.registerUpdateReceiver(this.mSystemContext);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("AppIntegrityService");
                    this.mSystemServiceManager.startService(AppIntegrityManagerService.class);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartLogcatManager");
                    this.mSystemServiceManager.startService(LogcatManagerService.class);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("SemInputDeviceManagerService");
                    Slog.i("SystemServer", "SemInputDeviceManagerService loader");
                    Class<?> cls22 = Class.forName("com.samsung.android.hardware.secinputdev.SemInputDeviceManagerLoader");
                    ServiceManager.addService("SemInputDeviceManagerService", (IBinder) cls22.getDeclaredMethod("getService", Context.class).invoke(cls22, context));
                    timingsTraceAndSlog.traceEnd();
                    Slog.i("SystemServer", "start samsung apex services");
                    timingsTraceAndSlog.traceBegin("SecIpmManagerService");
                    this.mSystemServiceManager.startService("com.android.server.ipm.SecIpmManagerService");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("SemPrivilegeManagerService");
                    this.mSystemServiceManager.startService("com.android.server.privilege.SemPrivilegeManagerService");
                    timingsTraceAndSlog.traceEnd();
                    Slog.i("SystemServer", "finish samsung apex services");
                    if (Boolean.parseBoolean(SystemProperties.get("sys.config.hardcoder.enable"))) {
                    }
                    timingsTraceAndSlog.traceBegin("detectSafeMode");
                    Slog.i("SystemServer", "!@Boot_EBS_D: detectSafeMode");
                    detectSafeMode = main.detectSafeMode();
                    if (!detectSafeMode) {
                    }
                    timingsTraceAndSlog.traceEnd();
                    if (this.mFactoryTestMode == 1) {
                    }
                    timingsTraceAndSlog.traceBegin("MakeDisplayReady");
                    main.displayReady();
                    timingsTraceAndSlog.traceEnd();
                    string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_CHIP_VENDOR");
                    if (string.length() > 0) {
                        timingsTraceAndSlog.traceBegin("Add SEM_FM_RADIO_SERVICE");
                        try {
                            ServiceManager.addService("FMPlayer", FMRadioService.class);
                            Slog.i("SystemServer", "FMRadio service added..");
                        } catch (Throwable th8) {
                            reportWtf("Failure starting FM Radio Service", th8);
                        }
                        timingsTraceAndSlog.traceEnd();
                    }
                    timingsTraceAndSlog.traceBegin("SamsungGameManager");
                    this.mSystemServiceManager.startService(new PathClassLoader("/system/framework/gamemanager.jar", SystemServer.class.getClassLoader()).loadClass("com.samsung.android.game.GameManagerService$Lifecycle"));
                    Slog.i("SystemServer", "SamsungGameManager Started");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("GameSDKService");
                    Slog.i("SystemServer", "GameSDKService");
                    ServiceManager.addService("gamesdk", (IBinder) new PathClassLoader("/system/framework/gamesdk.jar", SystemServer.class.getClassLoader()).loadClass("com.samsung.android.gamesdk.GameSDKService").getConstructor(Context.class, IActivityManager.class).newInstance(context, this.mActivityManagerService));
                    Slog.i("SystemServer", "GameSDKService loaded");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartRemoteAppModeService");
                    this.mSystemServiceManager.startService(RemoteAppModeService.Lifecycle.class);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("IcccManagerService");
                    ServiceManager.addService("iccc", new IServiceCreator() { // from class: com.android.server.SystemServer.3
                        public IBinder createService(Context context2) {
                            return new IcccManagerService(context2);
                        }
                    });
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("SehCodecSolutionService");
                    loadClass2 = new PathClassLoader("/system/framework/vendor.samsung.frameworks.codecsolution-service.jar", SystemServer.class.getClassLoader()).loadClass("vendor.samsung.frameworks.codecsolution.SehCodecSolutionService");
                    if (loadClass2 == null) {
                    }
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("SehHdrSolutionService");
                    loadClass = new PathClassLoader("/system/framework/vendor.samsung.frameworks.hdrsolution-service.jar", SystemServer.class.getClassLoader()).loadClass("vendor.samsung.frameworks.hdrsolution.SehHdrSolutionService");
                    if (loadClass == null) {
                    }
                    timingsTraceAndSlog.traceEnd();
                    if (!CoreRune.SYSPERF_JDM_MODEL) {
                    }
                    if (this.mFactoryTestMode != 1) {
                        timingsTraceAndSlog.traceBegin("StartStorageManagerService");
                        try {
                            this.mSystemServiceManager.startService("com.android.server.StorageManagerService$Lifecycle");
                            IStorageManager.Stub.asInterface(ServiceManager.getService("mount"));
                        } catch (Throwable th9) {
                            reportWtf("starting StorageManagerService", th9);
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartStorageStatsService");
                        try {
                            this.mSystemServiceManager.startService("com.android.server.usage.StorageStatsService$Lifecycle");
                        } catch (Throwable th10) {
                            reportWtf("starting StorageStatsService", th10);
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("DirEncryptSerrvice");
                        try {
                            if (SemSdCardEncryption.isEncryptionFeatureEnabled()) {
                            }
                        } catch (Throwable th11) {
                            reportWtf("starting DirEncryption service", th11);
                        }
                    }
                    timingsTraceAndSlog.traceBegin("StartUiModeManager");
                    this.mSystemServiceManager.startService(UiModeManagerService.class);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartLocaleManagerService");
                    this.mSystemServiceManager.startService(LocaleManagerService.class);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartGrammarInflectionService");
                    this.mSystemServiceManager.startService(GrammaticalInflectionService.class);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartAppHibernationService");
                    this.mSystemServiceManager.startService("com.android.server.apphibernation.AppHibernationService");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("ArtManagerLocal");
                    DexOptHelper.initializeArtManagerLocal(context, this.mPackageManagerService);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("UpdatePackagesIfNeeded");
                    Slog.i("SystemServer", "!@Boot_EBS_D: UpdatePackagesIfNeeded");
                    Watchdog.getInstance().pauseWatchingCurrentThread("dexopt");
                    this.mPackageManagerService.updatePackagesIfNeeded();
                    Watchdog.getInstance().resumeWatchingCurrentThread("dexopt");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("PerformFstrimIfNeeded");
                    this.mPackageManagerService.performFstrimIfNeeded();
                    timingsTraceAndSlog.traceEnd();
                    if (this.mFactoryTestMode != 1) {
                    }
                    final IBinder iBinder222 = null;
                    timingsTraceAndSlog.traceBegin("StartMediaProjectionManager");
                    this.mSystemServiceManager.startService(MediaProjectionManagerService.class);
                    timingsTraceAndSlog.traceEnd();
                    if (!this.mPackageManager.hasSystemFeature("att.devicehealth.support")) {
                    }
                    if (hasSystemFeature4) {
                    }
                    if (!this.mPackageManager.hasSystemFeature("android.software.slices_disabled")) {
                    }
                    if (context.getPackageManager().hasSystemFeature("android.hardware.type.embedded")) {
                    }
                    timingsTraceAndSlog.traceBegin("StartSpenGestureManagerService");
                    ServiceManager.addService("spengestureservice", new SpenGestureManagerService(context, main));
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartSamsungNotesService");
                    this.mSamsungNotesService = new SamsungNotesService(context);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartStatsCompanion");
                    this.mSystemServiceManager.startServiceFromJar("com.android.server.stats.StatsCompanion$Lifecycle", "/apex/com.android.os.statsd/javalib/service-statsd.jar");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartRebootReadinessManagerService");
                    this.mSystemServiceManager.startServiceFromJar("com.android.server.scheduling.RebootReadinessManagerService$Lifecycle", "/apex/com.android.scheduling/javalib/service-scheduling.jar");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartStatsPullAtomService");
                    this.mSystemServiceManager.startService("com.android.server.stats.pull.StatsPullAtomService");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StatsBootstrapAtomService");
                    this.mSystemServiceManager.startService("com.android.server.stats.bootstrap.StatsBootstrapAtomService$Lifecycle");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartIncidentCompanionService");
                    this.mSystemServiceManager.startService(IncidentCompanionService.class);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StarSdkSandboxManagerService");
                    this.mSystemServiceManager.startService("com.android.server.sdksandbox.SdkSandboxManagerService$Lifecycle");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartAdServicesManagerService");
                    startService = this.mSystemServiceManager.startService("com.android.server.adservices.AdServicesManagerService$Lifecycle");
                    if (startService instanceof Dumpable) {
                    }
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartOnDevicePersonalizationSystemService");
                    this.mSystemServiceManager.startService("com.android.server.ondevicepersonalization.OnDevicePersonalizationSystemService$Lifecycle");
                    timingsTraceAndSlog.traceEnd();
                    if (detectSafeMode) {
                    }
                    final boolean z522 = context.getResources().getBoolean(R.bool.config_restartRadioAfterProvisioning);
                    if (this.mPackageManager.hasSystemFeature("android.hardware.telephony")) {
                    }
                    timingsTraceAndSlog.traceBegin("StartMmsService");
                    MmsServiceBroker mmsServiceBroker222 = (MmsServiceBroker) this.mSystemServiceManager.startService(MmsServiceBroker.class);
                    timingsTraceAndSlog.traceEnd();
                    mmsServiceBroker = mmsServiceBroker222;
                    if (this.mPackageManager.hasSystemFeature("android.software.autofill")) {
                    }
                    if (this.mPackageManager.hasSystemFeature("android.software.credentials")) {
                    }
                    if (!deviceHasConfigString(context, R.string.default_audio_route_category_name)) {
                    }
                    timingsTraceAndSlog.traceBegin("StartSelectionToolbarManagerService");
                    this.mSystemServiceManager.startService("com.android.server.selectiontoolbar.SelectionToolbarManagerService");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartClipboardService");
                    this.mSystemServiceManager.startService(ClipboardService.class);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("AppServiceManager");
                    this.mSystemServiceManager.startService(AppBindingService.Lifecycle.class);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("startTracingServiceProxy");
                    this.mSystemServiceManager.startService(TracingServiceProxy.class);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("MakeLockSettingsServiceReady");
                    if (iLockSettings3 != null) {
                    }
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartPersonaSystemready");
                    if ("2.0".equals(SemPersonaManager.getKnoxInfo().getString("version"))) {
                    }
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartBootPhaseLockSettingsReady");
                    Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_LOCK_SETTINGS_READY");
                    this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, SystemService.PHASE_LOCK_SETTINGS_READY);
                    timingsTraceAndSlog.traceEnd();
                    createInstance = HsumBootUserInitializer.createInstance(this.mActivityManagerService, this.mPackageManagerService, this.mContentResolver, context.getResources().getBoolean(R.bool.config_focusScrollContainersInTouchMode));
                    if (createInstance != null) {
                    }
                    timingsTraceAndSlog.traceBegin("StartBootPhaseSystemServicesReady");
                    Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_SYSTEM_SERVICES_READY");
                    this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 500);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("MakeWindowManagerServiceReady");
                    main.systemReady();
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("RegisterLogMteState");
                    LogMteState.register(context);
                    timingsTraceAndSlog.traceEnd();
                    synchronized (SystemService.class) {
                    }
                } finally {
                }
            }
            timingsTraceAndSlog.traceEnd();
            if (!this.mPackageManager.hasSystemFeature("android.hardware.microphone") || this.mPackageManager.hasSystemFeature("android.software.telecom") || this.mPackageManager.hasSystemFeature("android.hardware.telephony")) {
                timingsTraceAndSlog.traceBegin("StartTelecomLoaderService");
                this.mSystemServiceManager.startService(TelecomLoaderService.class);
                timingsTraceAndSlog.traceEnd();
            }
            timingsTraceAndSlog.traceBegin("StartTelephonyRegistry");
            telephonyRegistry = new TelephonyRegistry(context, new TelephonyRegistry.ConfigurationProvider());
            ServiceManager.addService("telephony.registry", telephonyRegistry);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartEntropyMixer");
            this.mEntropyMixer = new EntropyMixer(context);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("start Secure AT Service");
            try {
                ServiceManager.addService("SatsService", new SatsService(context));
            } catch (Throwable unused3) {
                Slog.e("SystemServer", "Failed to add SATService.");
            }
            timingsTraceAndSlog.traceEnd();
            if (!"factory".equals(SystemProperties.get("ro.factory.factory_binary"))) {
                try {
                    try {
                        timingsTraceAndSlog.traceBegin("UCM Policy Service");
                        IUniversalCredentialManager.Stub universalCredentialManagerService = new UniversalCredentialManagerService(context);
                        ServiceManager.addService("knox_ucsm_policy", universalCredentialManagerService);
                        EnterpriseService.addPolicyService("knox_ucsm_policy", universalCredentialManagerService);
                    } catch (Exception e2) {
                        Slog.e("SystemServer", "Failure adding UniversalCredentialManagerService", e2);
                    }
                    try {
                        try {
                            timingsTraceAndSlog.traceBegin("CredentialManagerService Service");
                            ?? credentialManagerService = new CredentialManagerService(context);
                            ServiceManager.addService("com.samsung.ucs.ucsservice", (IBinder) credentialManagerService);
                            timingsTraceAndSlog.traceBegin("credentialManagerServiceReady");
                            try {
                                credentialManagerService.systemReady();
                            } catch (Exception e3) {
                                Slog.e("SystemServer", "Failed to call UCMService systemReady", e3);
                            }
                            timingsTraceAndSlog.traceEnd();
                        } catch (Throwable th12) {
                            throw th12;
                        }
                    } catch (Exception e4) {
                        Slog.e("SystemServer", "Failure adding CredentialManagerService", e4);
                    }
                    timingsTraceAndSlog.traceEnd();
                } finally {
                }
            }
            timingsTraceAndSlog.traceBegin("StartPersonaManager");
            try {
                Slog.i("SystemServer", "Persona Service");
                personaManagerService3 = PersonaManagerService.getInstance();
                ServiceManager.addService("persona", personaManagerService3);
                personaManagerService2 = personaManagerService3;
            } catch (Throwable th13) {
                th = th13;
                personaManagerService = null;
            }
            try {
                Slog.i("SystemServer", "KnoxMUMContainerPolicy Service");
                stub2 = new KnoxMUMContainerPolicy(context);
                ServiceManager.addService("mum_container_policy", stub2);
                ServiceManager.addService("mum_container_policy", stub2);
                telephonyRegistry2 = telephonyRegistry;
            } catch (Throwable th14) {
                th2 = th14;
                stub = null;
            }
            timingsTraceAndSlog.traceEnd();
            startRCPService(context, timingsTraceAndSlog, false);
            startDualAppManagerService(context, timingsTraceAndSlog);
            this.mContentResolver = context.getContentResolver();
            timingsTraceAndSlog.traceBegin("StartAccountManagerService");
            this.mSystemServiceManager.startService("com.android.server.accounts.AccountManagerService$Lifecycle");
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartContentService");
            this.mSystemServiceManager.startService("com.android.server.content.ContentService$Lifecycle");
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("InstallSystemProviders");
            this.mActivityManagerService.getContentProviderHelper().installSystemProviders();
            this.mSystemServiceManager.startService("com.android.server.deviceconfig.DeviceConfigInit$Lifecycle");
            SQLiteCompatibilityWalFlags.reset();
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartDropBoxManager");
            this.mSystemServiceManager.startService(DropBoxManagerService.class);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartRoleManagerService");
            IKnoxContainerManager.Stub stub3222 = stub2;
            LocalManagerRegistry.addManager(RoleServicePlatformHelper.class, new RoleServicePlatformHelperImpl(this.mSystemContext));
            this.mSystemServiceManager.startService("com.android.role.RoleService");
            timingsTraceAndSlog.traceEnd();
            if (UnionUtils.FEATURE_ENABLED) {
                timingsTraceAndSlog.traceBegin(SemUnionMainService.TAG);
                this.mSystemServiceManager.startService("com.android.server.sepunion.SemUnionMainService");
                timingsTraceAndSlog.traceEnd();
            }
            timingsTraceAndSlog.traceBegin("StartVibratorManagerService");
            this.mSystemServiceManager.startService(VibratorManagerService.Lifecycle.class);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartDynamicSystemService");
            ServiceManager.addService("dynamic_system", new DynamicSystemService(context));
            timingsTraceAndSlog.traceEnd();
            if (context.getPackageManager().hasSystemFeature("android.hardware.consumerir")) {
                timingsTraceAndSlog.traceBegin("StartConsumerIrService");
                ServiceManager.addService("consumer_ir", new ConsumerIrService(context));
                timingsTraceAndSlog.traceEnd();
            }
            timingsTraceAndSlog.traceBegin("StartResourceEconomy");
            this.mSystemServiceManager.startService("com.android.server.tare.InternalResourceService");
            timingsTraceAndSlog.traceEnd();
            try {
                timingsTraceAndSlog.traceBegin("SSRM Service");
                this.mSystemServiceManager.startService("com.android.server.ssrm.CustomFrequencyManagerService$Lifecycle");
            } catch (Exception e5) {
                try {
                    Slog.e("SystemServer", "ssrm.jar not found");
                    e5.printStackTrace();
                } catch (Throwable th15) {
                    th3 = th15;
                    throw th3;
                }
            } catch (Throwable th16) {
                th3 = th16;
                throw th3;
            }
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartAlarmManagerService");
            this.mSystemServiceManager.startService("com.android.server.alarm.AlarmManagerService");
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartInputManagerService");
            final ?? inputManagerService222 = new InputManagerService(context);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("DeviceStateManagerService");
            this.mSystemServiceManager.startService(DeviceStateManagerService.class);
            timingsTraceAndSlog.traceEnd();
            if (!z4) {
                timingsTraceAndSlog.traceBegin("StartCameraServiceProxy");
                this.mSystemServiceManager.startService(CameraServiceProxy.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartCameraServiceWorker");
                this.mSystemServiceManager.startService(CameraServiceWorker.class);
                timingsTraceAndSlog.traceEnd();
            }
            timingsTraceAndSlog.traceBegin("powerSolutionManagerService");
            try {
                Slog.i("SystemServer", "powerSolutionManagerService");
                ServiceManager.addService("PowerSolution_Framework_service", (IBinder) Class.forName("com.samsung.android.powerSolution.powerSolution").getConstructor(Context.class).newInstance(context));
            } catch (Throwable th17) {
                reportWtf("Failed To Start powerSolutionManagerService Service ", th17);
            }
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartWindowManagerService");
            Slog.i("SystemServer", "!@Boot_EBS_F: Start WindowManagerService");
            this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 200);
            main = WindowManagerService.main(context, inputManagerService222, this.mFirstBoot, new PhoneWindowManager(), this.mActivityManagerService.mActivityTaskManager);
            PersonaManagerService personaManagerService5222 = personaManagerService2;
            ServiceManager.addService("window", (IBinder) main, false, 17);
            ServiceManager.addService("input", (IBinder) inputManagerService222, false, 1);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("SetWindowManagerService");
            this.mActivityManagerService.setWindowManager(main);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("WindowManagerServiceOnInitReady");
            main.onInitReady();
            timingsTraceAndSlog.traceEnd();
            SystemServerInitThreadPool.submit(new Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    SystemServer.lambda$startOtherServices$2();
                }
            }, "StartISensorManagerService");
            SystemServerInitThreadPool.submit(new Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    SystemServer.lambda$startOtherServices$3();
                }
            }, "StartHidlServices");
            if (!hasSystemFeature4 && hasSystemFeature7) {
                timingsTraceAndSlog.traceBegin("StartVrManagerService");
                this.mSystemServiceManager.startService(VrManagerService.class);
                timingsTraceAndSlog.traceEnd();
            }
            timingsTraceAndSlog.traceBegin("startDesktopModeService");
            this.mSystemServiceManager.startService(DesktopModeService.Lifecycle.class);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartInputManager");
            inputManagerService222.setWindowManagerCallbacks(main.getInputManagerCallback());
            inputManagerService222.start();
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("DisplayManagerWindowManagerAndInputReady");
            this.mDisplayManagerService.windowManagerAndInputReady();
            timingsTraceAndSlog.traceEnd();
            if (this.mFactoryTestMode != 1) {
                Slog.i("SystemServer", "No Bluetooth Service (factory test)");
            } else if (!context.getPackageManager().hasSystemFeature("android.hardware.bluetooth")) {
                Slog.i("SystemServer", "No Bluetooth Service (Bluetooth Hardware Not Present)");
            } else {
                timingsTraceAndSlog.traceBegin("StartBluetoothService");
                this.mSystemServiceManager.startServiceFromJar("com.android.server.bluetooth.BluetoothService", "/apex/com.android.btservices/javalib/service-bluetooth.jar");
                timingsTraceAndSlog.traceEnd();
            }
            timingsTraceAndSlog.traceBegin("StartMultiControlManagerService");
            this.mSystemServiceManager.startService(MultiControlManagerService.Lifecycle.class);
            timingsTraceAndSlog.traceEnd();
            Slog.i("SystemServer", "Hqm Service");
            try {
                ServiceManager.addService("HqmManagerService", (IBinder) new PathClassLoader("/system/framework/hqm.jar", ClassLoader.getSystemClassLoader()).loadClass("com.samsung.android.hqm.HqmManagerService").getConstructor(Context.class).newInstance(context));
            } catch (Exception e6) {
                Slog.e("SystemServer", "hqm.jar not found");
                e6.printStackTrace();
            }
            if (!"0".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SYSTEM_CONFIG_HCM_AI_POWER_SAVING_LEVEL"))) {
                timingsTraceAndSlog.traceBegin("HcmManagerService");
                try {
                    int i2 = Settings.Global.getInt(context.getContentResolver(), "adaptive_power_saving_setting", 0);
                    Slog.e("SystemServer", "HcmManagerService ApmSwitch = " + i2);
                    Class loadClass3 = new PathClassLoader("/system/framework/hcm.jar", ClassLoader.getSystemClassLoader()).loadClass("com.samsung.android.hcm.HcmManagerService");
                    if (i2 > 0) {
                        ServiceManager.addService("HcmManagerService", (IBinder) loadClass3.getConstructor(Context.class).newInstance(context));
                    } else {
                        ServiceManager.addService("HcmManagerService", loadClass3);
                    }
                } catch (Exception e7) {
                    Slog.e("SystemServer", "Failed to add HcmManagerService.");
                    e7.printStackTrace();
                }
                timingsTraceAndSlog.traceEnd();
            }
            timingsTraceAndSlog.traceBegin(IpConnectivityMetrics.TAG);
            this.mSystemServiceManager.startService("com.android.server.connectivity.IpConnectivityMetrics");
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("NetworkWatchlistService");
            this.mSystemServiceManager.startService(NetworkWatchlistService.Lifecycle.class);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("PinnerService");
            this.mSystemServiceManager.startService(PinnerService.class);
            timingsTraceAndSlog.traceEnd();
            if (Build.IS_DEBUGGABLE && ProfcollectForwardingService.enabled()) {
                timingsTraceAndSlog.traceBegin("ProfcollectForwardingService");
                this.mSystemServiceManager.startService(ProfcollectForwardingService.class);
                timingsTraceAndSlog.traceEnd();
            }
            timingsTraceAndSlog.traceBegin("SignedConfigService");
            SignedConfigService.registerUpdateReceiver(this.mSystemContext);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("AppIntegrityService");
            this.mSystemServiceManager.startService(AppIntegrityManagerService.class);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartLogcatManager");
            this.mSystemServiceManager.startService(LogcatManagerService.class);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("SemInputDeviceManagerService");
            try {
                Slog.i("SystemServer", "SemInputDeviceManagerService loader");
                Class<?> cls222 = Class.forName("com.samsung.android.hardware.secinputdev.SemInputDeviceManagerLoader");
                ServiceManager.addService("SemInputDeviceManagerService", (IBinder) cls222.getDeclaredMethod("getService", Context.class).invoke(cls222, context));
            } catch (Throwable th18) {
                reportWtf("Failed To Start SemInputDeviceManagerService loader", th18);
            }
            timingsTraceAndSlog.traceEnd();
            Slog.i("SystemServer", "start samsung apex services");
            timingsTraceAndSlog.traceBegin("SecIpmManagerService");
            this.mSystemServiceManager.startService("com.android.server.ipm.SecIpmManagerService");
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("SemPrivilegeManagerService");
            this.mSystemServiceManager.startService("com.android.server.privilege.SemPrivilegeManagerService");
            timingsTraceAndSlog.traceEnd();
            Slog.i("SystemServer", "finish samsung apex services");
            if (Boolean.parseBoolean(SystemProperties.get("sys.config.hardcoder.enable"))) {
                try {
                    timingsTraceAndSlog.traceBegin("HardcoderService");
                    Constructor constructor = new PathClassLoader("/system/framework/HardcoderService.jar", SystemServer.class.getClassLoader()).loadClass("com.samsung.android.hardcoder.HardcoderService").getConstructor(Context.class, IActivityManager.class);
                    IBinder iBinder3 = (IBinder) constructor.newInstance(context, this.mActivityManagerService);
                    constructor.newInstance(context, this.mActivityManagerService);
                    ServiceManager.addService("HardcoderService", iBinder3);
                    Slog.i("SystemServer", "HardcoderService loaded");
                } finally {
                    try {
                        timingsTraceAndSlog.traceEnd();
                    } finally {
                    }
                }
                timingsTraceAndSlog.traceEnd();
            }
            timingsTraceAndSlog.traceBegin("detectSafeMode");
            Slog.i("SystemServer", "!@Boot_EBS_D: detectSafeMode");
            detectSafeMode = main.detectSafeMode();
            if (!detectSafeMode) {
                Settings.Global.putInt(context.getContentResolver(), "airplane_mode_on", 1);
            } else if (context.getResources().getBoolean(R.bool.config_autoPowerModePreferWristTilt)) {
                Settings.Global.putInt(context.getContentResolver(), "airplane_mode_on", 0);
            }
            timingsTraceAndSlog.traceEnd();
            if (this.mFactoryTestMode == 1) {
                timingsTraceAndSlog.traceBegin("StartInputMethodManagerLifecycle");
                String string2 = context.getResources().getString(R.string.deleted_key);
                if (string2.isEmpty()) {
                    this.mSystemServiceManager.startService(InputMethodManagerService.Lifecycle.class);
                    z = z3;
                } else {
                    try {
                        StringBuilder sb = new StringBuilder();
                        z = z3;
                        try {
                            sb.append("Starting custom IMMS: ");
                            sb.append(string2);
                            Slog.i("SystemServer", sb.toString());
                            this.mSystemServiceManager.startService(string2);
                        } catch (Throwable th19) {
                            th = th19;
                            reportWtf("starting " + string2, th);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartAccessibilityManagerService");
                            this.mSystemServiceManager.startService("com.android.server.accessibility.AccessibilityManagerService$Lifecycle");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("MakeDisplayReady");
                            main.displayReady();
                            timingsTraceAndSlog.traceEnd();
                            string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_CHIP_VENDOR");
                            if (string.length() > 0) {
                            }
                            timingsTraceAndSlog.traceBegin("SamsungGameManager");
                            this.mSystemServiceManager.startService(new PathClassLoader("/system/framework/gamemanager.jar", SystemServer.class.getClassLoader()).loadClass("com.samsung.android.game.GameManagerService$Lifecycle"));
                            Slog.i("SystemServer", "SamsungGameManager Started");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("GameSDKService");
                            Slog.i("SystemServer", "GameSDKService");
                            ServiceManager.addService("gamesdk", (IBinder) new PathClassLoader("/system/framework/gamesdk.jar", SystemServer.class.getClassLoader()).loadClass("com.samsung.android.gamesdk.GameSDKService").getConstructor(Context.class, IActivityManager.class).newInstance(context, this.mActivityManagerService));
                            Slog.i("SystemServer", "GameSDKService loaded");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartRemoteAppModeService");
                            this.mSystemServiceManager.startService(RemoteAppModeService.Lifecycle.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("IcccManagerService");
                            ServiceManager.addService("iccc", new IServiceCreator() { // from class: com.android.server.SystemServer.3
                                public IBinder createService(Context context2) {
                                    return new IcccManagerService(context2);
                                }
                            });
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("SehCodecSolutionService");
                            loadClass2 = new PathClassLoader("/system/framework/vendor.samsung.frameworks.codecsolution-service.jar", SystemServer.class.getClassLoader()).loadClass("vendor.samsung.frameworks.codecsolution.SehCodecSolutionService");
                            if (loadClass2 == null) {
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("SehHdrSolutionService");
                            loadClass = new PathClassLoader("/system/framework/vendor.samsung.frameworks.hdrsolution-service.jar", SystemServer.class.getClassLoader()).loadClass("vendor.samsung.frameworks.hdrsolution.SehHdrSolutionService");
                            if (loadClass == null) {
                            }
                            timingsTraceAndSlog.traceEnd();
                            if (!CoreRune.SYSPERF_JDM_MODEL) {
                            }
                            if (this.mFactoryTestMode != 1) {
                            }
                            timingsTraceAndSlog.traceBegin("StartUiModeManager");
                            this.mSystemServiceManager.startService(UiModeManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartLocaleManagerService");
                            this.mSystemServiceManager.startService(LocaleManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartGrammarInflectionService");
                            this.mSystemServiceManager.startService(GrammaticalInflectionService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartAppHibernationService");
                            this.mSystemServiceManager.startService("com.android.server.apphibernation.AppHibernationService");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("ArtManagerLocal");
                            DexOptHelper.initializeArtManagerLocal(context, this.mPackageManagerService);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("UpdatePackagesIfNeeded");
                            Slog.i("SystemServer", "!@Boot_EBS_D: UpdatePackagesIfNeeded");
                            Watchdog.getInstance().pauseWatchingCurrentThread("dexopt");
                            this.mPackageManagerService.updatePackagesIfNeeded();
                            Watchdog.getInstance().resumeWatchingCurrentThread("dexopt");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("PerformFstrimIfNeeded");
                            this.mPackageManagerService.performFstrimIfNeeded();
                            timingsTraceAndSlog.traceEnd();
                            if (this.mFactoryTestMode != 1) {
                            }
                            final IBinder iBinder2222 = null;
                            timingsTraceAndSlog.traceBegin("StartMediaProjectionManager");
                            this.mSystemServiceManager.startService(MediaProjectionManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            if (!this.mPackageManager.hasSystemFeature("att.devicehealth.support")) {
                            }
                            if (hasSystemFeature4) {
                            }
                            if (!this.mPackageManager.hasSystemFeature("android.software.slices_disabled")) {
                            }
                            if (context.getPackageManager().hasSystemFeature("android.hardware.type.embedded")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartSpenGestureManagerService");
                            ServiceManager.addService("spengestureservice", new SpenGestureManagerService(context, main));
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartSamsungNotesService");
                            this.mSamsungNotesService = new SamsungNotesService(context);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartStatsCompanion");
                            this.mSystemServiceManager.startServiceFromJar("com.android.server.stats.StatsCompanion$Lifecycle", "/apex/com.android.os.statsd/javalib/service-statsd.jar");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartRebootReadinessManagerService");
                            this.mSystemServiceManager.startServiceFromJar("com.android.server.scheduling.RebootReadinessManagerService$Lifecycle", "/apex/com.android.scheduling/javalib/service-scheduling.jar");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartStatsPullAtomService");
                            this.mSystemServiceManager.startService("com.android.server.stats.pull.StatsPullAtomService");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StatsBootstrapAtomService");
                            this.mSystemServiceManager.startService("com.android.server.stats.bootstrap.StatsBootstrapAtomService$Lifecycle");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartIncidentCompanionService");
                            this.mSystemServiceManager.startService(IncidentCompanionService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StarSdkSandboxManagerService");
                            this.mSystemServiceManager.startService("com.android.server.sdksandbox.SdkSandboxManagerService$Lifecycle");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartAdServicesManagerService");
                            startService = this.mSystemServiceManager.startService("com.android.server.adservices.AdServicesManagerService$Lifecycle");
                            if (startService instanceof Dumpable) {
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartOnDevicePersonalizationSystemService");
                            this.mSystemServiceManager.startService("com.android.server.ondevicepersonalization.OnDevicePersonalizationSystemService$Lifecycle");
                            timingsTraceAndSlog.traceEnd();
                            if (detectSafeMode) {
                            }
                            final boolean z5222 = context.getResources().getBoolean(R.bool.config_restartRadioAfterProvisioning);
                            if (this.mPackageManager.hasSystemFeature("android.hardware.telephony")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartMmsService");
                            MmsServiceBroker mmsServiceBroker2222 = (MmsServiceBroker) this.mSystemServiceManager.startService(MmsServiceBroker.class);
                            timingsTraceAndSlog.traceEnd();
                            mmsServiceBroker = mmsServiceBroker2222;
                            if (this.mPackageManager.hasSystemFeature("android.software.autofill")) {
                            }
                            if (this.mPackageManager.hasSystemFeature("android.software.credentials")) {
                            }
                            if (!deviceHasConfigString(context, R.string.default_audio_route_category_name)) {
                            }
                            timingsTraceAndSlog.traceBegin("StartSelectionToolbarManagerService");
                            this.mSystemServiceManager.startService("com.android.server.selectiontoolbar.SelectionToolbarManagerService");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartClipboardService");
                            this.mSystemServiceManager.startService(ClipboardService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("AppServiceManager");
                            this.mSystemServiceManager.startService(AppBindingService.Lifecycle.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("startTracingServiceProxy");
                            this.mSystemServiceManager.startService(TracingServiceProxy.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("MakeLockSettingsServiceReady");
                            if (iLockSettings3 != null) {
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartPersonaSystemready");
                            if ("2.0".equals(SemPersonaManager.getKnoxInfo().getString("version"))) {
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartBootPhaseLockSettingsReady");
                            Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_LOCK_SETTINGS_READY");
                            this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, SystemService.PHASE_LOCK_SETTINGS_READY);
                            timingsTraceAndSlog.traceEnd();
                            createInstance = HsumBootUserInitializer.createInstance(this.mActivityManagerService, this.mPackageManagerService, this.mContentResolver, context.getResources().getBoolean(R.bool.config_focusScrollContainersInTouchMode));
                            if (createInstance != null) {
                            }
                            timingsTraceAndSlog.traceBegin("StartBootPhaseSystemServicesReady");
                            Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_SYSTEM_SERVICES_READY");
                            this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 500);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("MakeWindowManagerServiceReady");
                            main.systemReady();
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("RegisterLogMteState");
                            LogMteState.register(context);
                            timingsTraceAndSlog.traceEnd();
                            synchronized (SystemService.class) {
                            }
                        }
                    } catch (Throwable th20) {
                        th = th20;
                        z = z3;
                    }
                }
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartAccessibilityManagerService");
                try {
                    this.mSystemServiceManager.startService("com.android.server.accessibility.AccessibilityManagerService$Lifecycle");
                } catch (Throwable th21) {
                    reportWtf("starting Accessibility Manager", th21);
                }
                timingsTraceAndSlog.traceEnd();
            } else {
                z = z3;
            }
            timingsTraceAndSlog.traceBegin("MakeDisplayReady");
            try {
                main.displayReady();
            } catch (Throwable th22) {
                reportWtf("making display ready", th22);
            }
            timingsTraceAndSlog.traceEnd();
            string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_CHIP_VENDOR");
            if (string.length() > 0 && Integer.parseInt(string) > 0) {
                timingsTraceAndSlog.traceBegin("Add SEM_FM_RADIO_SERVICE");
                ServiceManager.addService("FMPlayer", FMRadioService.class);
                Slog.i("SystemServer", "FMRadio service added..");
                timingsTraceAndSlog.traceEnd();
            }
            timingsTraceAndSlog.traceBegin("SamsungGameManager");
            this.mSystemServiceManager.startService(new PathClassLoader("/system/framework/gamemanager.jar", SystemServer.class.getClassLoader()).loadClass("com.samsung.android.game.GameManagerService$Lifecycle"));
            Slog.i("SystemServer", "SamsungGameManager Started");
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("GameSDKService");
            try {
                Slog.i("SystemServer", "GameSDKService");
                ServiceManager.addService("gamesdk", (IBinder) new PathClassLoader("/system/framework/gamesdk.jar", SystemServer.class.getClassLoader()).loadClass("com.samsung.android.gamesdk.GameSDKService").getConstructor(Context.class, IActivityManager.class).newInstance(context, this.mActivityManagerService));
                Slog.i("SystemServer", "GameSDKService loaded");
            } catch (Throwable th23) {
                Slog.e("SystemServer", "Failed to add GameSDKService.", th23);
            }
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartRemoteAppModeService");
            this.mSystemServiceManager.startService(RemoteAppModeService.Lifecycle.class);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("IcccManagerService");
            try {
                ServiceManager.addService("iccc", new IServiceCreator() { // from class: com.android.server.SystemServer.3
                    public IBinder createService(Context context2) {
                        return new IcccManagerService(context2);
                    }
                });
            } catch (Throwable th24) {
                reportWtf("Failure starting IcccManagerService ", th24);
            }
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("SehCodecSolutionService");
            loadClass2 = new PathClassLoader("/system/framework/vendor.samsung.frameworks.codecsolution-service.jar", SystemServer.class.getClassLoader()).loadClass("vendor.samsung.frameworks.codecsolution.SehCodecSolutionService");
            if (loadClass2 == null) {
                Slog.i("SystemServer", "Can't load SehCodecSolutionService class");
            } else {
                ServiceManager.addService("vendor.samsung.frameworks.codecsolution.ISehCodecSolution/default", (IBinder) loadClass2.getConstructor(Context.class, IActivityManager.class).newInstance(context, this.mActivityManagerService));
                Slog.i("SystemServer", "SehCodecSolutionService loaded");
                SystemProperties.set("secmm.codecsolution.ready", "1");
            }
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("SehHdrSolutionService");
            try {
                loadClass = new PathClassLoader("/system/framework/vendor.samsung.frameworks.hdrsolution-service.jar", SystemServer.class.getClassLoader()).loadClass("vendor.samsung.frameworks.hdrsolution.SehHdrSolutionService");
                if (loadClass == null) {
                    Slog.i("SystemServer", "Can't load SehHdrSolutionService class");
                } else {
                    ServiceManager.addService("vendor.samsung.frameworks.hdrsolution.ISehHdrSolution/default", (IBinder) loadClass.getConstructor(Context.class, IActivityManager.class).newInstance(context, this.mActivityManagerService));
                    Slog.i("SystemServer", "SehHdrSolutionService loaded");
                    SystemProperties.set("secmm.hdrsolution.ready", "1");
                }
            } catch (Throwable th25) {
                Slog.e("SystemServer", "Failed to start SehHdrSolutionService ", th25);
            }
            timingsTraceAndSlog.traceEnd();
            if (!CoreRune.SYSPERF_JDM_MODEL) {
                Slog.i("SystemServer", "PerfSDKService");
                timingsTraceAndSlog.traceBegin("PerfSDKService");
                try {
                    Class loadClass4 = new PathClassLoader("/system/framework/perfsdkservice.jar", SystemServer.class.getClassLoader()).loadClass("com.samsung.android.perfsdkservice.PerfSDKService");
                    if (loadClass4 == null) {
                        Slog.i("SystemServer", "Can't load PerfSdkService class");
                    } else {
                        ServiceManager.addService("perfsdkservice", (IBinder) loadClass4.getConstructor(Context.class, IActivityManager.class).newInstance(context, this.mActivityManagerService));
                        Slog.i("SystemServer", "PerfSDKService loaded");
                    }
                } catch (Throwable th26) {
                    Slog.e("SystemServer", "Failed to add PerfSDKService.", th26);
                }
                timingsTraceAndSlog.traceEnd();
            }
            if (this.mFactoryTestMode != 1 && !"0".equals(SystemProperties.get("system_init.startmountservice"))) {
                timingsTraceAndSlog.traceBegin("StartStorageManagerService");
                this.mSystemServiceManager.startService("com.android.server.StorageManagerService$Lifecycle");
                IStorageManager.Stub.asInterface(ServiceManager.getService("mount"));
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartStorageStatsService");
                this.mSystemServiceManager.startService("com.android.server.usage.StorageStatsService$Lifecycle");
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("DirEncryptSerrvice");
                if (SemSdCardEncryption.isEncryptionFeatureEnabled()) {
                    ?? dirEncryptService = new DirEncryptService(context);
                    Slog.i("SystemServer", "DirEncryptService.SystemReady");
                    dirEncryptService.systemReady();
                    ServiceManager.addService("DirEncryptService", (IBinder) dirEncryptService);
                }
            }
            timingsTraceAndSlog.traceBegin("StartUiModeManager");
            this.mSystemServiceManager.startService(UiModeManagerService.class);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartLocaleManagerService");
            try {
                this.mSystemServiceManager.startService(LocaleManagerService.class);
            } catch (Throwable th27) {
                reportWtf("starting LocaleManagerService service", th27);
            }
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartGrammarInflectionService");
            try {
                this.mSystemServiceManager.startService(GrammaticalInflectionService.class);
            } catch (Throwable th28) {
                reportWtf("starting GrammarInflectionService service", th28);
            }
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartAppHibernationService");
            this.mSystemServiceManager.startService("com.android.server.apphibernation.AppHibernationService");
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("ArtManagerLocal");
            DexOptHelper.initializeArtManagerLocal(context, this.mPackageManagerService);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("UpdatePackagesIfNeeded");
            Slog.i("SystemServer", "!@Boot_EBS_D: UpdatePackagesIfNeeded");
            try {
                Watchdog.getInstance().pauseWatchingCurrentThread("dexopt");
                this.mPackageManagerService.updatePackagesIfNeeded();
            } finally {
                try {
                    Watchdog.getInstance().resumeWatchingCurrentThread("dexopt");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("PerformFstrimIfNeeded");
                    this.mPackageManagerService.performFstrimIfNeeded();
                    timingsTraceAndSlog.traceEnd();
                    if (this.mFactoryTestMode != 1) {
                    }
                    final IBinder iBinder22222 = null;
                    timingsTraceAndSlog.traceBegin("StartMediaProjectionManager");
                    this.mSystemServiceManager.startService(MediaProjectionManagerService.class);
                    timingsTraceAndSlog.traceEnd();
                    if (!this.mPackageManager.hasSystemFeature("att.devicehealth.support")) {
                    }
                    if (hasSystemFeature4) {
                    }
                    if (!this.mPackageManager.hasSystemFeature("android.software.slices_disabled")) {
                    }
                    if (context.getPackageManager().hasSystemFeature("android.hardware.type.embedded")) {
                    }
                    timingsTraceAndSlog.traceBegin("StartSpenGestureManagerService");
                    ServiceManager.addService("spengestureservice", new SpenGestureManagerService(context, main));
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartSamsungNotesService");
                    this.mSamsungNotesService = new SamsungNotesService(context);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartStatsCompanion");
                    this.mSystemServiceManager.startServiceFromJar("com.android.server.stats.StatsCompanion$Lifecycle", "/apex/com.android.os.statsd/javalib/service-statsd.jar");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartRebootReadinessManagerService");
                    this.mSystemServiceManager.startServiceFromJar("com.android.server.scheduling.RebootReadinessManagerService$Lifecycle", "/apex/com.android.scheduling/javalib/service-scheduling.jar");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartStatsPullAtomService");
                    this.mSystemServiceManager.startService("com.android.server.stats.pull.StatsPullAtomService");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StatsBootstrapAtomService");
                    this.mSystemServiceManager.startService("com.android.server.stats.bootstrap.StatsBootstrapAtomService$Lifecycle");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartIncidentCompanionService");
                    this.mSystemServiceManager.startService(IncidentCompanionService.class);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StarSdkSandboxManagerService");
                    this.mSystemServiceManager.startService("com.android.server.sdksandbox.SdkSandboxManagerService$Lifecycle");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartAdServicesManagerService");
                    startService = this.mSystemServiceManager.startService("com.android.server.adservices.AdServicesManagerService$Lifecycle");
                    if (startService instanceof Dumpable) {
                    }
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartOnDevicePersonalizationSystemService");
                    this.mSystemServiceManager.startService("com.android.server.ondevicepersonalization.OnDevicePersonalizationSystemService$Lifecycle");
                    timingsTraceAndSlog.traceEnd();
                    if (detectSafeMode) {
                    }
                    final boolean z52222 = context.getResources().getBoolean(R.bool.config_restartRadioAfterProvisioning);
                    if (this.mPackageManager.hasSystemFeature("android.hardware.telephony")) {
                    }
                    timingsTraceAndSlog.traceBegin("StartMmsService");
                    MmsServiceBroker mmsServiceBroker22222 = (MmsServiceBroker) this.mSystemServiceManager.startService(MmsServiceBroker.class);
                    timingsTraceAndSlog.traceEnd();
                    mmsServiceBroker = mmsServiceBroker22222;
                    if (this.mPackageManager.hasSystemFeature("android.software.autofill")) {
                    }
                    if (this.mPackageManager.hasSystemFeature("android.software.credentials")) {
                    }
                    if (!deviceHasConfigString(context, R.string.default_audio_route_category_name)) {
                    }
                    timingsTraceAndSlog.traceBegin("StartSelectionToolbarManagerService");
                    this.mSystemServiceManager.startService("com.android.server.selectiontoolbar.SelectionToolbarManagerService");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartClipboardService");
                    this.mSystemServiceManager.startService(ClipboardService.class);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("AppServiceManager");
                    this.mSystemServiceManager.startService(AppBindingService.Lifecycle.class);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("startTracingServiceProxy");
                    this.mSystemServiceManager.startService(TracingServiceProxy.class);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("MakeLockSettingsServiceReady");
                    if (iLockSettings3 != null) {
                    }
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartPersonaSystemready");
                    if ("2.0".equals(SemPersonaManager.getKnoxInfo().getString("version"))) {
                    }
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartBootPhaseLockSettingsReady");
                    Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_LOCK_SETTINGS_READY");
                    this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, SystemService.PHASE_LOCK_SETTINGS_READY);
                    timingsTraceAndSlog.traceEnd();
                    createInstance = HsumBootUserInitializer.createInstance(this.mActivityManagerService, this.mPackageManagerService, this.mContentResolver, context.getResources().getBoolean(R.bool.config_focusScrollContainersInTouchMode));
                    if (createInstance != null) {
                    }
                    timingsTraceAndSlog.traceBegin("StartBootPhaseSystemServicesReady");
                    Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_SYSTEM_SERVICES_READY");
                    this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 500);
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("MakeWindowManagerServiceReady");
                    main.systemReady();
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("RegisterLogMteState");
                    LogMteState.register(context);
                    timingsTraceAndSlog.traceEnd();
                    synchronized (SystemService.class) {
                    }
                } catch (Throwable th29) {
                }
            }
            Watchdog.getInstance().resumeWatchingCurrentThread("dexopt");
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("PerformFstrimIfNeeded");
            try {
                this.mPackageManagerService.performFstrimIfNeeded();
            } catch (Throwable th30) {
                reportWtf("performing fstrim", th30);
            }
            timingsTraceAndSlog.traceEnd();
            if (this.mFactoryTestMode != 1) {
                iLockSettings3 = null;
                lifecycle2 = null;
                networkPolicyManagerService = null;
                vcnManagementService = null;
                iBinder = null;
                networkTimeUpdateService2 = null;
                iNetworkManagementService = null;
                mediaRouterService = null;
                vpnManagerService = null;
                urspService = null;
                countryDetectorService = null;
            } else {
                timingsTraceAndSlog.traceBegin("StartLockSettingsService");
                try {
                    this.mSystemServiceManager.startService("com.android.server.locksettings.LockSettingsService$Lifecycle");
                    iLockSettings = ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
                } catch (Throwable th31) {
                    reportWtf("starting LockSettingsService service", th31);
                    iLockSettings = null;
                }
                timingsTraceAndSlog.traceEnd();
                boolean z6 = !SystemProperties.get("ro.frp.pst").equals("");
                if (z6) {
                    timingsTraceAndSlog.traceBegin("StartPersistentDataBlock");
                    this.mSystemServiceManager.startService(PersistentDataBlockService.class);
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("StartTestHarnessMode");
                this.mSystemServiceManager.startService(TestHarnessModeService.class);
                timingsTraceAndSlog.traceEnd();
                if (z6 || OemLockService.isHalPresent()) {
                    timingsTraceAndSlog.traceBegin("StartOemLockService");
                    this.mSystemServiceManager.startService(OemLockService.class);
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("StartDeviceIdleController");
                this.mSystemServiceManager.startService("com.android.server.DeviceIdleController");
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartDevicePolicyManager");
                DevicePolicyManagerService.Lifecycle lifecycle3 = (DevicePolicyManagerService.Lifecycle) this.mSystemServiceManager.startService(DevicePolicyManagerService.Lifecycle.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartStatusBarManagerService");
                try {
                    ?? statusBarManagerService = new StatusBarManagerService(context);
                    if (!hasSystemFeature4) {
                        statusBarManagerService.publishGlobalActionsProvider();
                    }
                    iLockSettings2 = iLockSettings;
                    lifecycle = lifecycle3;
                    try {
                        ServiceManager.addService("statusbar", (IBinder) statusBarManagerService, false, 20);
                    } catch (Throwable th32) {
                        th = th32;
                        reportWtf("starting StatusBarManagerService", th);
                        timingsTraceAndSlog.traceEnd();
                        if (!deviceHasConfigString(context, R.string.date_picker_increment_day_button)) {
                        }
                        timingsTraceAndSlog.traceBegin("StartEnterpriseDeviceManagerService");
                        this.mSystemServiceManager.startService(EnterpriseDeviceManagerServiceImpl.Lifecycle.class);
                        this.enterprisePolicy = EnterpriseDeviceManagerServiceImpl.getInstance();
                        Slog.i("SystemServer", "Enterprise Policy manager service created...");
                        timingsTraceAndSlog.traceEnd();
                        if (EnterpriseDeviceManager.getAPILevel() <= 0) {
                        }
                        timingsTraceAndSlog.traceBegin("KnoxCustom Policy");
                        ?? knoxCustomManagerService = new KnoxCustomManagerService(context);
                        this.knoxCustomPolicy = knoxCustomManagerService;
                        ServiceManager.addService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE, (IBinder) knoxCustomManagerService);
                        EnterpriseService.addPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE, this.knoxCustomPolicy);
                        Slog.i("SystemServer", "KnoxCustom Policy added.");
                        timingsTraceAndSlog.traceEnd();
                        Slog.i("SystemServer", "DarManagerService");
                        timingsTraceAndSlog.traceBegin("DarManagerService");
                        ServiceManager.addService("dar", DarManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        startContentCaptureService(context, timingsTraceAndSlog);
                        startAttentionService(context, timingsTraceAndSlog);
                        startRotationResolverService(context, timingsTraceAndSlog);
                        startSystemCaptionsManagerService(context, timingsTraceAndSlog);
                        startTextToSpeechManagerService(context, timingsTraceAndSlog);
                        startAmbientContextService(timingsTraceAndSlog);
                        startWearableSensingService(timingsTraceAndSlog);
                        timingsTraceAndSlog.traceBegin("StartSpeechRecognitionManagerService");
                        this.mSystemServiceManager.startService("com.android.server.speech.SpeechRecognitionManagerService");
                        timingsTraceAndSlog.traceEnd();
                        if (!deviceHasConfigString(context, R.string.data_usage_limit_body)) {
                        }
                        if (!deviceHasConfigString(context, R.string.data_usage_restricted_title)) {
                        }
                        timingsTraceAndSlog.traceBegin("SemClipboardService");
                        Slog.i("SystemServer", "SemClipboardService");
                        ServiceManager.addService("semclipboard", new SemClipboardService(context));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartSearchUiService");
                        this.mSystemServiceManager.startService("com.android.server.searchui.SearchUiManagerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartSmartspaceService");
                        this.mSystemServiceManager.startService("com.android.server.smartspace.SmartspaceManagerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartSemContextService");
                        if (!context.getPackageManager().hasSystemFeature("com.sec.feature.sensorhub")) {
                        }
                        Slog.i("SystemServer", "SemContextService Service");
                        ServiceManager.addService("scontext", (IBinder) new PathClassLoader("/system/framework/semcontextservice.jar", ClassLoader.getSystemClassLoader()).loadClass("com.samsung.android.hardware.context.SemContextService").getConstructor(Context.class).newInstance(context));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartMotionRecognitionService");
                        packageManager3 = context.getPackageManager();
                        if (packageManager3 != null) {
                            ServiceManager.addService("motion_recognition", (IBinder) new PathClassLoader("/system/framework/motionrecognitionservice.jar", ClassLoader.getSystemClassLoader()).loadClass("com.samsung.android.gesture.MotionRecognitionService").getConstructor(Context.class).newInstance(context));
                            Slog.i("SystemServer", "MotionRecognitionService Service!");
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("InitConnectivityModuleConnector");
                        ConnectivityModuleConnector.getInstance().init(context);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("InitNetworkStackClient");
                        NetworkStackClient.getInstance().init();
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartNetworkManagementService");
                        INetworkManagementService iNetworkManagementService2 = NetworkManagementService.create(context);
                        ServiceManager.addService("network_management", iNetworkManagementService2);
                        timingsTraceAndSlog.traceEnd();
                        if (CoreRune.SYSFW_APP_SPEG) {
                        }
                        timingsTraceAndSlog.traceBegin("StartFontManagerService");
                        this.mSystemServiceManager.startService(new FontManagerService.Lifecycle(context, detectSafeMode));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartTextServicesManager");
                        this.mSystemServiceManager.startService(TextServicesManagerService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        if (!z2) {
                        }
                        timingsTraceAndSlog.traceBegin("StartNetworkScoreService");
                        this.mSystemServiceManager.startService(NetworkScoreService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartNetworkStatsService");
                        this.mSystemServiceManager.startServiceFromJar("com.android.server.NetworkStatsServiceInitializer", "/apex/com.android.tethering/javalib/service-connectivity.jar");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartNetworkPolicyManagerService");
                        INetworkPolicyManager.Stub networkPolicyManagerService2 = new NetworkPolicyManagerService(context, this.mActivityManagerService, iNetworkManagementService2);
                        ServiceManager.addService("netpolicy", networkPolicyManagerService2);
                        NetworkPolicyManagerService networkPolicyManagerService3 = networkPolicyManagerService2;
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartUrspService");
                        UrspService urspService2 = new UrspService(context);
                        ServiceManager.addService("urspservice", urspService2);
                        timingsTraceAndSlog.traceEnd();
                        if (context.getPackageManager().hasSystemFeature("android.hardware.wifi")) {
                        }
                        if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.rtt")) {
                        }
                        if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.aware")) {
                        }
                        if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.direct")) {
                        }
                        if (context.getPackageManager().hasSystemFeature("android.hardware.lowpan")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartPacProxyService");
                        ServiceManager.addService("pac_proxy", new PacProxyService(context));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartIntelligentBatterySaverService");
                        ServiceManager.addService("IntelligentBatterySaverService", new IntelligentBatterySaverService(context));
                        timingsTraceAndSlog.traceEnd();
                        if (!FactoryTest.isFactoryBinary()) {
                        }
                        timingsTraceAndSlog.traceBegin("StartConnectivityService");
                        this.mSystemServiceManager.startServiceFromJar("com.android.server.ConnectivityServiceInitializer", "/apex/com.android.tethering/javalib/service-connectivity.jar");
                        networkPolicyManagerService3.bindConnectivityManager();
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartExtendedEthernetService");
                        this.mSystemServiceManager.startService("com.android.server.ExtendedEthernetService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartVpnManagerService");
                        IVpnManager.Stub create = VpnManagerService.create(context);
                        ServiceManager.addService("vpn_management", create);
                        VpnManagerService vpnManagerService2 = create;
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartVcnManagementService");
                        IVcnManagementService.Stub create2 = VcnManagementService.create(context);
                        ServiceManager.addService("vcn_management", create2);
                        NetworkPolicyManagerService networkPolicyManagerService4 = networkPolicyManagerService3;
                        VcnManagementService vcnManagementService2 = create2;
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartSystemUpdateManagerService");
                        ServiceManager.addService("system_update", new SystemUpdateManagerService(context));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartUpdateLockService");
                        ServiceManager.addService("updatelock", new UpdateLockService(context));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartNotificationManager");
                        this.mSystemServiceManager.startService(NotificationManagerService.class);
                        SystemNotificationChannels.removeDeprecated(context);
                        SystemNotificationChannels.createAll(context);
                        INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartDeviceMonitor");
                        this.mSystemServiceManager.startService(DeviceStorageMonitorService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartTimeDetectorService");
                        this.mSystemServiceManager.startService("com.android.server.timedetector.TimeDetectorService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartLocationManagerService");
                        this.mSystemServiceManager.startService(LocationManagerService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartSLocationService");
                        Slog.i("SystemServer", "SLocation Manager");
                        vpnManagerService = vpnManagerService2;
                        urspService = urspService2;
                        IBinder iBinder4 = (IBinder) Class.forName("com.samsung.android.location.SLocationLoader").getDeclaredMethod("getSLocationService", Context.class).invoke(null, context);
                        ServiceManager.addService("sec_location", iBinder4);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("SemMdnieManagerService");
                        Slog.i("SystemServer", "mDNIe Service");
                        ServiceManager.addService("mDNIe", (IBinder) Class.forName("com.samsung.android.hardware.display.SemMdnieManagerService").getConstructor(Context.class).newInstance(context));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("SemDisplaySolution");
                        Slog.i("SystemServer", "SemDisplaySolution Service");
                        ServiceManager.addService("DisplaySolution", (IBinder) Class.forName("com.samsung.android.displaysolution.SemDisplaySolutionManagerService").getConstructor(Context.class).newInstance(context));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartCountryDetectorService");
                        ICountryDetector.Stub countryDetectorService2 = new CountryDetectorService(context);
                        ServiceManager.addService("country_detector", countryDetectorService2);
                        CountryDetectorService countryDetectorService3 = countryDetectorService2;
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartTimeZoneDetectorService");
                        this.mSystemServiceManager.startService("com.android.server.timezonedetector.TimeZoneDetectorService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartAltitudeService");
                        this.mSystemServiceManager.startService(AltitudeService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartLocationTimeZoneManagerService");
                        this.mSystemServiceManager.startService("com.android.server.timezonedetector.location.LocationTimeZoneManagerService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        if (context.getResources().getBoolean(R.bool.config_dreamsEnabledOnBattery)) {
                        }
                        timingsTraceAndSlog.traceBegin("KnoxVpnService");
                        if (this.enterprisePolicy != null) {
                        }
                        countryDetectorService = countryDetectorService3;
                        timingsTraceAndSlog.traceEnd();
                        if (!hasSystemFeature4) {
                        }
                        if (!context.getResources().getBoolean(R.bool.config_enableFusedLocationOverlay)) {
                        }
                        timingsTraceAndSlog.traceBegin("StartWallpaperEffectsGenerationService");
                        this.mSystemServiceManager.startService("com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartAudioService");
                        if (hasSystemFeature5) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartSoundTriggerMiddlewareService");
                        this.mSystemServiceManager.startService(SoundTriggerMiddlewareService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("LedCoverAppService");
                        this.mLedCoverAppService = new LedCoverAppService(context);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartLedBackCoverService");
                        this.mLedBackCoverService = new LedBackCoverService(context);
                        timingsTraceAndSlog.traceEnd();
                        if (this.mPackageManager.hasSystemFeature("android.hardware.broadcastradio")) {
                        }
                        if (!hasSystemFeature6) {
                        }
                        if (hasSystemFeature4) {
                        }
                        if (!hasSystemFeature4) {
                        }
                        if (this.mPackageManager.hasSystemFeature("android.software.midi")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartAdbService");
                        this.mSystemServiceManager.startService("com.android.server.adb.AdbService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        if (!this.mPackageManager.hasSystemFeature("android.hardware.usb.host")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartUsbService");
                        this.mSystemServiceManager.startService("com.android.server.usb.UsbService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        if (!hasSystemFeature4) {
                        }
                        timingsTraceAndSlog.traceBegin("StartHardwarePropertiesManagerService");
                        ServiceManager.addService("hardware_properties", new HardwarePropertiesManagerService(context));
                        timingsTraceAndSlog.traceEnd();
                        if (!hasSystemFeature4) {
                        }
                        timingsTraceAndSlog.traceBegin("StartColorDisplay");
                        this.mSystemServiceManager.startService(ColorDisplayService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartJobScheduler");
                        this.mSystemServiceManager.startService("com.android.server.job.JobSchedulerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartSoundTrigger");
                        this.mSystemServiceManager.startService(SoundTriggerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartTrustManager");
                        this.mSystemServiceManager.startService(TrustManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (this.mPackageManager.hasSystemFeature("android.software.backup")) {
                        }
                        if (!this.mPackageManager.hasSystemFeature("android.software.app_widgets")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartAppWidgetService");
                        this.mSystemServiceManager.startService("com.android.server.appwidget.AppWidgetService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartVoiceRecognitionManager");
                        this.mSystemServiceManager.startService("com.android.server.voiceinteraction.VoiceInteractionManagerService");
                        timingsTraceAndSlog.traceEnd();
                        if (GestureLauncherService.isGestureLauncherEnabled(context.getResources())) {
                        }
                        timingsTraceAndSlog.traceBegin("StartSensorNotification");
                        this.mSystemServiceManager.startService(SensorNotificationService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (this.mPackageManager.hasSystemFeature("android.hardware.context_hub")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartDiskStatsService");
                        ServiceManager.addService("diskstats", new DiskStatsService(context));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("RuntimeService");
                        ServiceManager.addService("runtime", new RuntimeService(context));
                        timingsTraceAndSlog.traceEnd();
                        if (hasSystemFeature4) {
                        }
                        networkTimeUpdateService = null;
                        timingsTraceAndSlog.traceBegin("CertBlacklister");
                        new CertBlacklister(context);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartEmergencyAffordanceService");
                        this.mSystemServiceManager.startService(EmergencyAffordanceService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("startBlobStoreManagerService");
                        this.mSystemServiceManager.startService("com.android.server.blob.BlobStoreManagerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartDreamManager");
                        this.mSystemServiceManager.startService(DreamManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("AddGraphicsStatsService");
                        ServiceManager.addService("graphicsstats", new GraphicsStatsService(context));
                        timingsTraceAndSlog.traceEnd();
                        if (CoverageService.ENABLED) {
                        }
                        if (this.mPackageManager.hasSystemFeature("android.software.print")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartAttestationVerificationService");
                        this.mSystemServiceManager.startService(AttestationVerificationManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (this.mPackageManager.hasSystemFeature("android.software.companion_device_setup")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartRestrictionManager");
                        this.mSystemServiceManager.startService(RestrictionsManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("CocktailBarService");
                        this.mSystemServiceManager.startService("com.android.server.cocktailbar.CocktailBarManagerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("SemInputDeviceManagerService SystemReady");
                        Slog.i("SystemServer", "SemInputDeviceManagerService SystemReady loader");
                        Class<?> cls3 = Class.forName("com.samsung.android.hardware.secinputdev.SemInputDeviceManagerLoader");
                        cls3.getDeclaredMethod("systemReady", new Class[0]).invoke(cls3, new Object[0]);
                        timingsTraceAndSlog.traceEnd();
                        if (this.mFactoryTestMode != 1) {
                        }
                        timingsTraceAndSlog.traceBegin("StartAODManagerService");
                        this.mSystemServiceManager.startService("com.android.server.aod.AODManagerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("Samsung Accessory Manager");
                        packageManager2 = context.getPackageManager();
                        if (packageManager2 != null) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("BattAuthManager");
                        packageManager = context.getPackageManager();
                        if (packageManager != null) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartMediaSessionService");
                        this.mSystemServiceManager.startService("com.android.server.media.MediaSessionService");
                        timingsTraceAndSlog.traceEnd();
                        if (this.mPackageManager.hasSystemFeature("android.hardware.hdmi.cec")) {
                        }
                        if (!this.mPackageManager.hasSystemFeature("android.software.live_tv")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartTvInteractiveAppManager");
                        this.mSystemServiceManager.startService(TvInteractiveAppManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (!this.mPackageManager.hasSystemFeature("android.software.live_tv")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartTvInputManager");
                        this.mSystemServiceManager.startService(TvInputManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (this.mPackageManager.hasSystemFeature("android.hardware.tv.tuner")) {
                        }
                        if (this.mPackageManager.hasSystemFeature("android.software.picture_in_picture")) {
                        }
                        if (this.mPackageManager.hasSystemFeature("android.software.leanback")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartMediaRouterService");
                        IMediaRouterService.Stub mediaRouterService2 = new MediaRouterService(context);
                        ServiceManager.addService("media_router", mediaRouterService2);
                        MediaRouterService mediaRouterService3 = mediaRouterService2;
                        timingsTraceAndSlog.traceEnd();
                        hasSystemFeature = this.mPackageManager.hasSystemFeature("android.hardware.biometrics.face");
                        hasSystemFeature2 = this.mPackageManager.hasSystemFeature("android.hardware.biometrics.iris");
                        hasSystemFeature3 = this.mPackageManager.hasSystemFeature("android.hardware.fingerprint");
                        if (hasSystemFeature) {
                        }
                        if (hasSystemFeature2) {
                        }
                        if (hasSystemFeature3) {
                        }
                        timingsTraceAndSlog.traceBegin("StartBiometricService");
                        this.mSystemServiceManager.startService(BiometricService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartAuthService");
                        this.mSystemServiceManager.startService(AuthService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (!hasSystemFeature4) {
                        }
                        if (!hasSystemFeature4) {
                        }
                        timingsTraceAndSlog.traceBegin("StartSmartThingsService");
                        this.mSmartThingsService = new SmartThingsService(context);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartShortcutServiceLifecycle");
                        this.mSystemServiceManager.startService(ShortcutService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartLauncherAppsService");
                        this.mSystemServiceManager.startService(LauncherAppsService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartCrossProfileAppsService");
                        this.mSystemServiceManager.startService(CrossProfileAppsService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartPeopleService");
                        this.mSystemServiceManager.startService(PeopleService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartMediaMetricsManager");
                        this.mSystemServiceManager.startService(MediaMetricsManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartBackgroundInstallControlService");
                        this.mSystemServiceManager.startService(BackgroundInstallControlService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartChimeraService");
                        ServiceManager.addService("ChimeraManagerService", new ChimeraManagerService(context, this.mActivityManagerService));
                        Slog.i("SystemServer", "ChimeraManagerService loaded");
                        timingsTraceAndSlog.traceEnd();
                        if (context.getPackageManager().hasSystemFeature("android.hardware.wifi")) {
                        }
                        if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.direct")) {
                        }
                        if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.aware")) {
                        }
                        iBinder = iBinder4;
                        networkPolicyManagerService = networkPolicyManagerService4;
                        mediaRouterService = mediaRouterService3;
                        iLockSettings3 = iLockSettings2;
                        vcnManagementService = vcnManagementService2;
                        lifecycle2 = lifecycle;
                        networkTimeUpdateService2 = networkTimeUpdateService;
                        final IBinder iBinder222222 = null;
                        timingsTraceAndSlog.traceBegin("StartMediaProjectionManager");
                        this.mSystemServiceManager.startService(MediaProjectionManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (!this.mPackageManager.hasSystemFeature("att.devicehealth.support")) {
                        }
                        if (hasSystemFeature4) {
                        }
                        if (!this.mPackageManager.hasSystemFeature("android.software.slices_disabled")) {
                        }
                        if (context.getPackageManager().hasSystemFeature("android.hardware.type.embedded")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartSpenGestureManagerService");
                        ServiceManager.addService("spengestureservice", new SpenGestureManagerService(context, main));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartSamsungNotesService");
                        this.mSamsungNotesService = new SamsungNotesService(context);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartStatsCompanion");
                        this.mSystemServiceManager.startServiceFromJar("com.android.server.stats.StatsCompanion$Lifecycle", "/apex/com.android.os.statsd/javalib/service-statsd.jar");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartRebootReadinessManagerService");
                        this.mSystemServiceManager.startServiceFromJar("com.android.server.scheduling.RebootReadinessManagerService$Lifecycle", "/apex/com.android.scheduling/javalib/service-scheduling.jar");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartStatsPullAtomService");
                        this.mSystemServiceManager.startService("com.android.server.stats.pull.StatsPullAtomService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StatsBootstrapAtomService");
                        this.mSystemServiceManager.startService("com.android.server.stats.bootstrap.StatsBootstrapAtomService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartIncidentCompanionService");
                        this.mSystemServiceManager.startService(IncidentCompanionService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StarSdkSandboxManagerService");
                        this.mSystemServiceManager.startService("com.android.server.sdksandbox.SdkSandboxManagerService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartAdServicesManagerService");
                        startService = this.mSystemServiceManager.startService("com.android.server.adservices.AdServicesManagerService$Lifecycle");
                        if (startService instanceof Dumpable) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartOnDevicePersonalizationSystemService");
                        this.mSystemServiceManager.startService("com.android.server.ondevicepersonalization.OnDevicePersonalizationSystemService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        if (detectSafeMode) {
                        }
                        final boolean z522222 = context.getResources().getBoolean(R.bool.config_restartRadioAfterProvisioning);
                        if (this.mPackageManager.hasSystemFeature("android.hardware.telephony")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartMmsService");
                        MmsServiceBroker mmsServiceBroker222222 = (MmsServiceBroker) this.mSystemServiceManager.startService(MmsServiceBroker.class);
                        timingsTraceAndSlog.traceEnd();
                        mmsServiceBroker = mmsServiceBroker222222;
                        if (this.mPackageManager.hasSystemFeature("android.software.autofill")) {
                        }
                        if (this.mPackageManager.hasSystemFeature("android.software.credentials")) {
                        }
                        if (!deviceHasConfigString(context, R.string.default_audio_route_category_name)) {
                        }
                        timingsTraceAndSlog.traceBegin("StartSelectionToolbarManagerService");
                        this.mSystemServiceManager.startService("com.android.server.selectiontoolbar.SelectionToolbarManagerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartClipboardService");
                        this.mSystemServiceManager.startService(ClipboardService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("AppServiceManager");
                        this.mSystemServiceManager.startService(AppBindingService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("startTracingServiceProxy");
                        this.mSystemServiceManager.startService(TracingServiceProxy.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("MakeLockSettingsServiceReady");
                        if (iLockSettings3 != null) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartPersonaSystemready");
                        if ("2.0".equals(SemPersonaManager.getKnoxInfo().getString("version"))) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartBootPhaseLockSettingsReady");
                        Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_LOCK_SETTINGS_READY");
                        this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, SystemService.PHASE_LOCK_SETTINGS_READY);
                        timingsTraceAndSlog.traceEnd();
                        createInstance = HsumBootUserInitializer.createInstance(this.mActivityManagerService, this.mPackageManagerService, this.mContentResolver, context.getResources().getBoolean(R.bool.config_focusScrollContainersInTouchMode));
                        if (createInstance != null) {
                        }
                        timingsTraceAndSlog.traceBegin("StartBootPhaseSystemServicesReady");
                        Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_SYSTEM_SERVICES_READY");
                        this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 500);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("MakeWindowManagerServiceReady");
                        main.systemReady();
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("RegisterLogMteState");
                        LogMteState.register(context);
                        timingsTraceAndSlog.traceEnd();
                        synchronized (SystemService.class) {
                        }
                    }
                } catch (Throwable th33) {
                    th = th33;
                    iLockSettings2 = iLockSettings;
                    lifecycle = lifecycle3;
                }
                timingsTraceAndSlog.traceEnd();
                if (!deviceHasConfigString(context, R.string.date_picker_increment_day_button)) {
                    timingsTraceAndSlog.traceBegin("StartMusicRecognitionManagerService");
                    this.mSystemServiceManager.startService("com.android.server.musicrecognition.MusicRecognitionManagerService");
                    timingsTraceAndSlog.traceEnd();
                } else {
                    Slog.d("SystemServer", "MusicRecognitionManagerService not defined by OEM or disabled by flag");
                }
                timingsTraceAndSlog.traceBegin("StartEnterpriseDeviceManagerService");
                try {
                    this.mSystemServiceManager.startService(EnterpriseDeviceManagerServiceImpl.Lifecycle.class);
                    this.enterprisePolicy = EnterpriseDeviceManagerServiceImpl.getInstance();
                    Slog.i("SystemServer", "Enterprise Policy manager service created...");
                } catch (Throwable th34) {
                    reportWtf("starting EnterpriseDeviceManagerService", th34);
                }
                timingsTraceAndSlog.traceEnd();
                if (EnterpriseDeviceManager.getAPILevel() <= 0) {
                    try {
                        timingsTraceAndSlog.traceBegin("[KnoxAnalytics] System Service");
                        this.mSystemServiceManager.startService(KnoxAnalyticsSystemService.class);
                        timingsTraceAndSlog.traceEnd();
                    } catch (Throwable th35) {
                        Slog.e("SystemServer", "[KnoxAnalytics] Failure starting System Service", th35);
                    }
                } else {
                    Slog.d("SystemServer", "KnoxAnalyticsSystemService not defined by OEM");
                }
                timingsTraceAndSlog.traceBegin("KnoxCustom Policy");
                try {
                    ?? knoxCustomManagerService2 = new KnoxCustomManagerService(context);
                    this.knoxCustomPolicy = knoxCustomManagerService2;
                    ServiceManager.addService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE, (IBinder) knoxCustomManagerService2);
                    EnterpriseService.addPolicyService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE, this.knoxCustomPolicy);
                    Slog.i("SystemServer", "KnoxCustom Policy added.");
                } catch (Throwable th36) {
                    Slog.e("SystemServer", "Fail KnoxCustom Policy.", th36);
                }
                timingsTraceAndSlog.traceEnd();
                try {
                    Slog.i("SystemServer", "DarManagerService");
                    timingsTraceAndSlog.traceBegin("DarManagerService");
                    ServiceManager.addService("dar", DarManagerService.class);
                    timingsTraceAndSlog.traceEnd();
                } catch (Throwable th37) {
                    reportWtf("starting DarManagerService", th37);
                }
                startContentCaptureService(context, timingsTraceAndSlog);
                startAttentionService(context, timingsTraceAndSlog);
                startRotationResolverService(context, timingsTraceAndSlog);
                startSystemCaptionsManagerService(context, timingsTraceAndSlog);
                startTextToSpeechManagerService(context, timingsTraceAndSlog);
                startAmbientContextService(timingsTraceAndSlog);
                startWearableSensingService(timingsTraceAndSlog);
                timingsTraceAndSlog.traceBegin("StartSpeechRecognitionManagerService");
                this.mSystemServiceManager.startService("com.android.server.speech.SpeechRecognitionManagerService");
                timingsTraceAndSlog.traceEnd();
                if (!deviceHasConfigString(context, R.string.data_usage_limit_body)) {
                    timingsTraceAndSlog.traceBegin("StartAppPredictionService");
                    this.mSystemServiceManager.startService("com.android.server.appprediction.AppPredictionManagerService");
                    timingsTraceAndSlog.traceEnd();
                } else {
                    Slog.d("SystemServer", "AppPredictionService not defined by OEM");
                }
                if (!deviceHasConfigString(context, R.string.data_usage_restricted_title)) {
                    timingsTraceAndSlog.traceBegin("StartContentSuggestionsService");
                    this.mSystemServiceManager.startService("com.android.server.contentsuggestions.ContentSuggestionsManagerService");
                    timingsTraceAndSlog.traceEnd();
                } else {
                    Slog.d("SystemServer", "ContentSuggestionsService not defined by OEM");
                }
                try {
                    timingsTraceAndSlog.traceBegin("SemClipboardService");
                    Slog.i("SystemServer", "SemClipboardService");
                    ServiceManager.addService("semclipboard", new SemClipboardService(context));
                } finally {
                    try {
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartSearchUiService");
                        this.mSystemServiceManager.startService("com.android.server.searchui.SearchUiManagerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartSmartspaceService");
                        this.mSystemServiceManager.startService("com.android.server.smartspace.SmartspaceManagerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartSemContextService");
                        if (!context.getPackageManager().hasSystemFeature("com.sec.feature.sensorhub")) {
                        }
                        Slog.i("SystemServer", "SemContextService Service");
                        ServiceManager.addService("scontext", (IBinder) new PathClassLoader("/system/framework/semcontextservice.jar", ClassLoader.getSystemClassLoader()).loadClass("com.samsung.android.hardware.context.SemContextService").getConstructor(Context.class).newInstance(context));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartMotionRecognitionService");
                        packageManager3 = context.getPackageManager();
                        if (packageManager3 != null) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("InitConnectivityModuleConnector");
                        ConnectivityModuleConnector.getInstance().init(context);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("InitNetworkStackClient");
                        NetworkStackClient.getInstance().init();
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartNetworkManagementService");
                        INetworkManagementService iNetworkManagementService22 = NetworkManagementService.create(context);
                        ServiceManager.addService("network_management", iNetworkManagementService22);
                        timingsTraceAndSlog.traceEnd();
                        if (CoreRune.SYSFW_APP_SPEG) {
                        }
                        timingsTraceAndSlog.traceBegin("StartFontManagerService");
                        this.mSystemServiceManager.startService(new FontManagerService.Lifecycle(context, detectSafeMode));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartTextServicesManager");
                        this.mSystemServiceManager.startService(TextServicesManagerService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        if (!z2) {
                        }
                        timingsTraceAndSlog.traceBegin("StartNetworkScoreService");
                        this.mSystemServiceManager.startService(NetworkScoreService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartNetworkStatsService");
                        this.mSystemServiceManager.startServiceFromJar("com.android.server.NetworkStatsServiceInitializer", "/apex/com.android.tethering/javalib/service-connectivity.jar");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartNetworkPolicyManagerService");
                        INetworkPolicyManager.Stub networkPolicyManagerService22 = new NetworkPolicyManagerService(context, this.mActivityManagerService, iNetworkManagementService22);
                        ServiceManager.addService("netpolicy", networkPolicyManagerService22);
                        NetworkPolicyManagerService networkPolicyManagerService32 = networkPolicyManagerService22;
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartUrspService");
                        UrspService urspService22 = new UrspService(context);
                        ServiceManager.addService("urspservice", urspService22);
                        timingsTraceAndSlog.traceEnd();
                        if (context.getPackageManager().hasSystemFeature("android.hardware.wifi")) {
                        }
                        if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.rtt")) {
                        }
                        if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.aware")) {
                        }
                        if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.direct")) {
                        }
                        if (context.getPackageManager().hasSystemFeature("android.hardware.lowpan")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartPacProxyService");
                        ServiceManager.addService("pac_proxy", new PacProxyService(context));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartIntelligentBatterySaverService");
                        ServiceManager.addService("IntelligentBatterySaverService", new IntelligentBatterySaverService(context));
                        timingsTraceAndSlog.traceEnd();
                        if (!FactoryTest.isFactoryBinary()) {
                        }
                        timingsTraceAndSlog.traceBegin("StartConnectivityService");
                        this.mSystemServiceManager.startServiceFromJar("com.android.server.ConnectivityServiceInitializer", "/apex/com.android.tethering/javalib/service-connectivity.jar");
                        networkPolicyManagerService32.bindConnectivityManager();
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartExtendedEthernetService");
                        this.mSystemServiceManager.startService("com.android.server.ExtendedEthernetService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartVpnManagerService");
                        IVpnManager.Stub create3 = VpnManagerService.create(context);
                        ServiceManager.addService("vpn_management", create3);
                        VpnManagerService vpnManagerService22 = create3;
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartVcnManagementService");
                        IVcnManagementService.Stub create22 = VcnManagementService.create(context);
                        ServiceManager.addService("vcn_management", create22);
                        NetworkPolicyManagerService networkPolicyManagerService42 = networkPolicyManagerService32;
                        VcnManagementService vcnManagementService22 = create22;
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartSystemUpdateManagerService");
                        ServiceManager.addService("system_update", new SystemUpdateManagerService(context));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartUpdateLockService");
                        ServiceManager.addService("updatelock", new UpdateLockService(context));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartNotificationManager");
                        this.mSystemServiceManager.startService(NotificationManagerService.class);
                        SystemNotificationChannels.removeDeprecated(context);
                        SystemNotificationChannels.createAll(context);
                        INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartDeviceMonitor");
                        this.mSystemServiceManager.startService(DeviceStorageMonitorService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartTimeDetectorService");
                        this.mSystemServiceManager.startService("com.android.server.timedetector.TimeDetectorService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartLocationManagerService");
                        this.mSystemServiceManager.startService(LocationManagerService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartSLocationService");
                        Slog.i("SystemServer", "SLocation Manager");
                        vpnManagerService = vpnManagerService22;
                        urspService = urspService22;
                        IBinder iBinder42 = (IBinder) Class.forName("com.samsung.android.location.SLocationLoader").getDeclaredMethod("getSLocationService", Context.class).invoke(null, context);
                        ServiceManager.addService("sec_location", iBinder42);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("SemMdnieManagerService");
                        Slog.i("SystemServer", "mDNIe Service");
                        ServiceManager.addService("mDNIe", (IBinder) Class.forName("com.samsung.android.hardware.display.SemMdnieManagerService").getConstructor(Context.class).newInstance(context));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("SemDisplaySolution");
                        Slog.i("SystemServer", "SemDisplaySolution Service");
                        ServiceManager.addService("DisplaySolution", (IBinder) Class.forName("com.samsung.android.displaysolution.SemDisplaySolutionManagerService").getConstructor(Context.class).newInstance(context));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartCountryDetectorService");
                        ICountryDetector.Stub countryDetectorService22 = new CountryDetectorService(context);
                        ServiceManager.addService("country_detector", countryDetectorService22);
                        CountryDetectorService countryDetectorService32 = countryDetectorService22;
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartTimeZoneDetectorService");
                        this.mSystemServiceManager.startService("com.android.server.timezonedetector.TimeZoneDetectorService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartAltitudeService");
                        this.mSystemServiceManager.startService(AltitudeService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartLocationTimeZoneManagerService");
                        this.mSystemServiceManager.startService("com.android.server.timezonedetector.location.LocationTimeZoneManagerService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        if (context.getResources().getBoolean(R.bool.config_dreamsEnabledOnBattery)) {
                        }
                        timingsTraceAndSlog.traceBegin("KnoxVpnService");
                        if (this.enterprisePolicy != null) {
                        }
                        countryDetectorService = countryDetectorService32;
                        timingsTraceAndSlog.traceEnd();
                        if (!hasSystemFeature4) {
                        }
                        if (!context.getResources().getBoolean(R.bool.config_enableFusedLocationOverlay)) {
                        }
                        timingsTraceAndSlog.traceBegin("StartWallpaperEffectsGenerationService");
                        this.mSystemServiceManager.startService("com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartAudioService");
                        if (hasSystemFeature5) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartSoundTriggerMiddlewareService");
                        this.mSystemServiceManager.startService(SoundTriggerMiddlewareService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("LedCoverAppService");
                        this.mLedCoverAppService = new LedCoverAppService(context);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartLedBackCoverService");
                        this.mLedBackCoverService = new LedBackCoverService(context);
                        timingsTraceAndSlog.traceEnd();
                        if (this.mPackageManager.hasSystemFeature("android.hardware.broadcastradio")) {
                        }
                        if (!hasSystemFeature6) {
                        }
                        if (hasSystemFeature4) {
                        }
                        if (!hasSystemFeature4) {
                        }
                        if (this.mPackageManager.hasSystemFeature("android.software.midi")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartAdbService");
                        this.mSystemServiceManager.startService("com.android.server.adb.AdbService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        if (!this.mPackageManager.hasSystemFeature("android.hardware.usb.host")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartUsbService");
                        this.mSystemServiceManager.startService("com.android.server.usb.UsbService$Lifecycle");
                        timingsTraceAndSlog.traceEnd();
                        if (!hasSystemFeature4) {
                        }
                        timingsTraceAndSlog.traceBegin("StartHardwarePropertiesManagerService");
                        ServiceManager.addService("hardware_properties", new HardwarePropertiesManagerService(context));
                        timingsTraceAndSlog.traceEnd();
                        if (!hasSystemFeature4) {
                        }
                        timingsTraceAndSlog.traceBegin("StartColorDisplay");
                        this.mSystemServiceManager.startService(ColorDisplayService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartJobScheduler");
                        this.mSystemServiceManager.startService("com.android.server.job.JobSchedulerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartSoundTrigger");
                        this.mSystemServiceManager.startService(SoundTriggerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartTrustManager");
                        this.mSystemServiceManager.startService(TrustManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (this.mPackageManager.hasSystemFeature("android.software.backup")) {
                        }
                        if (!this.mPackageManager.hasSystemFeature("android.software.app_widgets")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartAppWidgetService");
                        this.mSystemServiceManager.startService("com.android.server.appwidget.AppWidgetService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartVoiceRecognitionManager");
                        this.mSystemServiceManager.startService("com.android.server.voiceinteraction.VoiceInteractionManagerService");
                        timingsTraceAndSlog.traceEnd();
                        if (GestureLauncherService.isGestureLauncherEnabled(context.getResources())) {
                        }
                        timingsTraceAndSlog.traceBegin("StartSensorNotification");
                        this.mSystemServiceManager.startService(SensorNotificationService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (this.mPackageManager.hasSystemFeature("android.hardware.context_hub")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartDiskStatsService");
                        ServiceManager.addService("diskstats", new DiskStatsService(context));
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("RuntimeService");
                        ServiceManager.addService("runtime", new RuntimeService(context));
                        timingsTraceAndSlog.traceEnd();
                        if (hasSystemFeature4) {
                        }
                        networkTimeUpdateService = null;
                        timingsTraceAndSlog.traceBegin("CertBlacklister");
                        new CertBlacklister(context);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartEmergencyAffordanceService");
                        this.mSystemServiceManager.startService(EmergencyAffordanceService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("startBlobStoreManagerService");
                        this.mSystemServiceManager.startService("com.android.server.blob.BlobStoreManagerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartDreamManager");
                        this.mSystemServiceManager.startService(DreamManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("AddGraphicsStatsService");
                        ServiceManager.addService("graphicsstats", new GraphicsStatsService(context));
                        timingsTraceAndSlog.traceEnd();
                        if (CoverageService.ENABLED) {
                        }
                        if (this.mPackageManager.hasSystemFeature("android.software.print")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartAttestationVerificationService");
                        this.mSystemServiceManager.startService(AttestationVerificationManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (this.mPackageManager.hasSystemFeature("android.software.companion_device_setup")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartRestrictionManager");
                        this.mSystemServiceManager.startService(RestrictionsManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("CocktailBarService");
                        this.mSystemServiceManager.startService("com.android.server.cocktailbar.CocktailBarManagerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("SemInputDeviceManagerService SystemReady");
                        Slog.i("SystemServer", "SemInputDeviceManagerService SystemReady loader");
                        Class<?> cls32 = Class.forName("com.samsung.android.hardware.secinputdev.SemInputDeviceManagerLoader");
                        cls32.getDeclaredMethod("systemReady", new Class[0]).invoke(cls32, new Object[0]);
                        timingsTraceAndSlog.traceEnd();
                        if (this.mFactoryTestMode != 1) {
                        }
                        timingsTraceAndSlog.traceBegin("StartAODManagerService");
                        this.mSystemServiceManager.startService("com.android.server.aod.AODManagerService");
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("Samsung Accessory Manager");
                        packageManager2 = context.getPackageManager();
                        if (packageManager2 != null) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("BattAuthManager");
                        packageManager = context.getPackageManager();
                        if (packageManager != null) {
                        }
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartMediaSessionService");
                        this.mSystemServiceManager.startService("com.android.server.media.MediaSessionService");
                        timingsTraceAndSlog.traceEnd();
                        if (this.mPackageManager.hasSystemFeature("android.hardware.hdmi.cec")) {
                        }
                        if (!this.mPackageManager.hasSystemFeature("android.software.live_tv")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartTvInteractiveAppManager");
                        this.mSystemServiceManager.startService(TvInteractiveAppManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (!this.mPackageManager.hasSystemFeature("android.software.live_tv")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartTvInputManager");
                        this.mSystemServiceManager.startService(TvInputManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (this.mPackageManager.hasSystemFeature("android.hardware.tv.tuner")) {
                        }
                        if (this.mPackageManager.hasSystemFeature("android.software.picture_in_picture")) {
                        }
                        if (this.mPackageManager.hasSystemFeature("android.software.leanback")) {
                        }
                        timingsTraceAndSlog.traceBegin("StartMediaRouterService");
                        IMediaRouterService.Stub mediaRouterService22 = new MediaRouterService(context);
                        ServiceManager.addService("media_router", mediaRouterService22);
                        MediaRouterService mediaRouterService32 = mediaRouterService22;
                        timingsTraceAndSlog.traceEnd();
                        hasSystemFeature = this.mPackageManager.hasSystemFeature("android.hardware.biometrics.face");
                        hasSystemFeature2 = this.mPackageManager.hasSystemFeature("android.hardware.biometrics.iris");
                        hasSystemFeature3 = this.mPackageManager.hasSystemFeature("android.hardware.fingerprint");
                        if (hasSystemFeature) {
                        }
                        if (hasSystemFeature2) {
                        }
                        if (hasSystemFeature3) {
                        }
                        timingsTraceAndSlog.traceBegin("StartBiometricService");
                        this.mSystemServiceManager.startService(BiometricService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartAuthService");
                        this.mSystemServiceManager.startService(AuthService.class);
                        timingsTraceAndSlog.traceEnd();
                        if (!hasSystemFeature4) {
                        }
                        if (!hasSystemFeature4) {
                        }
                        timingsTraceAndSlog.traceBegin("StartSmartThingsService");
                        this.mSmartThingsService = new SmartThingsService(context);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartShortcutServiceLifecycle");
                        this.mSystemServiceManager.startService(ShortcutService.Lifecycle.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartLauncherAppsService");
                        this.mSystemServiceManager.startService(LauncherAppsService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartCrossProfileAppsService");
                        this.mSystemServiceManager.startService(CrossProfileAppsService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartPeopleService");
                        this.mSystemServiceManager.startService(PeopleService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartMediaMetricsManager");
                        this.mSystemServiceManager.startService(MediaMetricsManagerService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartBackgroundInstallControlService");
                        this.mSystemServiceManager.startService(BackgroundInstallControlService.class);
                        timingsTraceAndSlog.traceEnd();
                        timingsTraceAndSlog.traceBegin("StartChimeraService");
                        ServiceManager.addService("ChimeraManagerService", new ChimeraManagerService(context, this.mActivityManagerService));
                        Slog.i("SystemServer", "ChimeraManagerService loaded");
                        timingsTraceAndSlog.traceEnd();
                        if (context.getPackageManager().hasSystemFeature("android.hardware.wifi")) {
                        }
                        if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.direct")) {
                        }
                        if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.aware")) {
                        }
                        iBinder = iBinder42;
                        networkPolicyManagerService = networkPolicyManagerService42;
                        mediaRouterService = mediaRouterService32;
                        iLockSettings3 = iLockSettings2;
                        vcnManagementService = vcnManagementService22;
                        lifecycle2 = lifecycle;
                        networkTimeUpdateService2 = networkTimeUpdateService;
                    } finally {
                    }
                }
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartSearchUiService");
                this.mSystemServiceManager.startService("com.android.server.searchui.SearchUiManagerService");
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartSmartspaceService");
                this.mSystemServiceManager.startService("com.android.server.smartspace.SmartspaceManagerService");
                timingsTraceAndSlog.traceEnd();
                try {
                    timingsTraceAndSlog.traceBegin("StartSemContextService");
                    if (!context.getPackageManager().hasSystemFeature("com.sec.feature.sensorhub") || context.getPackageManager().hasSystemFeature("com.sec.feature.scontext_lite")) {
                        Slog.i("SystemServer", "SemContextService Service");
                        ServiceManager.addService("scontext", (IBinder) new PathClassLoader("/system/framework/semcontextservice.jar", ClassLoader.getSystemClassLoader()).loadClass("com.samsung.android.hardware.context.SemContextService").getConstructor(Context.class).newInstance(context));
                    }
                } catch (Exception e8) {
                    try {
                        Slog.e("SystemServer", "Failure starting SemContextService", e8);
                    } catch (Throwable th38) {
                        th4 = th38;
                        throw th4;
                    }
                } catch (Throwable th39) {
                    th4 = th39;
                    throw th4;
                }
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartMotionRecognitionService");
                packageManager3 = context.getPackageManager();
                if (packageManager3 != null && packageManager3.hasSystemFeature("com.sec.feature.motionrecognition_service")) {
                    ServiceManager.addService("motion_recognition", (IBinder) new PathClassLoader("/system/framework/motionrecognitionservice.jar", ClassLoader.getSystemClassLoader()).loadClass("com.samsung.android.gesture.MotionRecognitionService").getConstructor(Context.class).newInstance(context));
                    Slog.i("SystemServer", "MotionRecognitionService Service!");
                }
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("InitConnectivityModuleConnector");
                ConnectivityModuleConnector.getInstance().init(context);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("InitNetworkStackClient");
                try {
                    NetworkStackClient.getInstance().init();
                } catch (Throwable th40) {
                    reportWtf("initializing NetworkStackClient", th40);
                }
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartNetworkManagementService");
                INetworkManagementService iNetworkManagementService222 = NetworkManagementService.create(context);
                ServiceManager.addService("network_management", iNetworkManagementService222);
                timingsTraceAndSlog.traceEnd();
                if (CoreRune.SYSFW_APP_SPEG) {
                    timingsTraceAndSlog.traceBegin("StartSpegService");
                    try {
                        spegService = (SpegService) this.mSystemServiceManager.startService(SpegService.class);
                        try {
                            LocalServices.addService(SpegService.class, spegService);
                        } catch (RuntimeException e9) {
                            runtimeException = e9;
                            Slog.e("SystemServer", "Failure starting SpegService helper", runtimeException);
                            spegService = spegService;
                            timingsTraceAndSlog.traceEnd();
                            if (CoreRune.SYSFW_APP_PREL) {
                                timingsTraceAndSlog.traceBegin("AppPrelaunchService");
                                try {
                                    AppPrelaunchManagerService appPrelaunchManagerService = (AppPrelaunchManagerService) this.mSystemServiceManager.startService(AppPrelaunchManagerService.class);
                                    appPrelaunchManagerService.initPrelauncher(spegService, this.mActivityManagerService);
                                    LocalServices.addService(AppPrelaunchManagerService.class, appPrelaunchManagerService);
                                } catch (RuntimeException unused4) {
                                    Slog.e("SystemServer", "Failed to start prelaunch service");
                                }
                                timingsTraceAndSlog.traceEnd();
                            }
                            timingsTraceAndSlog.traceBegin("StartFontManagerService");
                            this.mSystemServiceManager.startService(new FontManagerService.Lifecycle(context, detectSafeMode));
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartTextServicesManager");
                            this.mSystemServiceManager.startService(TextServicesManagerService.Lifecycle.class);
                            timingsTraceAndSlog.traceEnd();
                            if (!z2) {
                            }
                            timingsTraceAndSlog.traceBegin("StartNetworkScoreService");
                            this.mSystemServiceManager.startService(NetworkScoreService.Lifecycle.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartNetworkStatsService");
                            this.mSystemServiceManager.startServiceFromJar("com.android.server.NetworkStatsServiceInitializer", "/apex/com.android.tethering/javalib/service-connectivity.jar");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartNetworkPolicyManagerService");
                            INetworkPolicyManager.Stub networkPolicyManagerService222 = new NetworkPolicyManagerService(context, this.mActivityManagerService, iNetworkManagementService222);
                            ServiceManager.addService("netpolicy", networkPolicyManagerService222);
                            NetworkPolicyManagerService networkPolicyManagerService322 = networkPolicyManagerService222;
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartUrspService");
                            UrspService urspService222 = new UrspService(context);
                            ServiceManager.addService("urspservice", urspService222);
                            timingsTraceAndSlog.traceEnd();
                            if (context.getPackageManager().hasSystemFeature("android.hardware.wifi")) {
                            }
                            if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.rtt")) {
                            }
                            if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.aware")) {
                            }
                            if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.direct")) {
                            }
                            if (context.getPackageManager().hasSystemFeature("android.hardware.lowpan")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartPacProxyService");
                            ServiceManager.addService("pac_proxy", new PacProxyService(context));
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartIntelligentBatterySaverService");
                            ServiceManager.addService("IntelligentBatterySaverService", new IntelligentBatterySaverService(context));
                            timingsTraceAndSlog.traceEnd();
                            if (!FactoryTest.isFactoryBinary()) {
                            }
                            timingsTraceAndSlog.traceBegin("StartConnectivityService");
                            this.mSystemServiceManager.startServiceFromJar("com.android.server.ConnectivityServiceInitializer", "/apex/com.android.tethering/javalib/service-connectivity.jar");
                            networkPolicyManagerService322.bindConnectivityManager();
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartExtendedEthernetService");
                            this.mSystemServiceManager.startService("com.android.server.ExtendedEthernetService");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartVpnManagerService");
                            IVpnManager.Stub create32 = VpnManagerService.create(context);
                            ServiceManager.addService("vpn_management", create32);
                            VpnManagerService vpnManagerService222 = create32;
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartVcnManagementService");
                            IVcnManagementService.Stub create222 = VcnManagementService.create(context);
                            ServiceManager.addService("vcn_management", create222);
                            NetworkPolicyManagerService networkPolicyManagerService422 = networkPolicyManagerService322;
                            VcnManagementService vcnManagementService222 = create222;
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartSystemUpdateManagerService");
                            ServiceManager.addService("system_update", new SystemUpdateManagerService(context));
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartUpdateLockService");
                            ServiceManager.addService("updatelock", new UpdateLockService(context));
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartNotificationManager");
                            this.mSystemServiceManager.startService(NotificationManagerService.class);
                            SystemNotificationChannels.removeDeprecated(context);
                            SystemNotificationChannels.createAll(context);
                            INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartDeviceMonitor");
                            this.mSystemServiceManager.startService(DeviceStorageMonitorService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartTimeDetectorService");
                            this.mSystemServiceManager.startService("com.android.server.timedetector.TimeDetectorService$Lifecycle");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartLocationManagerService");
                            this.mSystemServiceManager.startService(LocationManagerService.Lifecycle.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartSLocationService");
                            Slog.i("SystemServer", "SLocation Manager");
                            vpnManagerService = vpnManagerService222;
                            urspService = urspService222;
                            IBinder iBinder422 = (IBinder) Class.forName("com.samsung.android.location.SLocationLoader").getDeclaredMethod("getSLocationService", Context.class).invoke(null, context);
                            ServiceManager.addService("sec_location", iBinder422);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("SemMdnieManagerService");
                            Slog.i("SystemServer", "mDNIe Service");
                            ServiceManager.addService("mDNIe", (IBinder) Class.forName("com.samsung.android.hardware.display.SemMdnieManagerService").getConstructor(Context.class).newInstance(context));
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("SemDisplaySolution");
                            Slog.i("SystemServer", "SemDisplaySolution Service");
                            ServiceManager.addService("DisplaySolution", (IBinder) Class.forName("com.samsung.android.displaysolution.SemDisplaySolutionManagerService").getConstructor(Context.class).newInstance(context));
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartCountryDetectorService");
                            ICountryDetector.Stub countryDetectorService222 = new CountryDetectorService(context);
                            ServiceManager.addService("country_detector", countryDetectorService222);
                            CountryDetectorService countryDetectorService322 = countryDetectorService222;
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartTimeZoneDetectorService");
                            this.mSystemServiceManager.startService("com.android.server.timezonedetector.TimeZoneDetectorService$Lifecycle");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartAltitudeService");
                            this.mSystemServiceManager.startService(AltitudeService.Lifecycle.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartLocationTimeZoneManagerService");
                            this.mSystemServiceManager.startService("com.android.server.timezonedetector.location.LocationTimeZoneManagerService$Lifecycle");
                            timingsTraceAndSlog.traceEnd();
                            if (context.getResources().getBoolean(R.bool.config_dreamsEnabledOnBattery)) {
                            }
                            timingsTraceAndSlog.traceBegin("KnoxVpnService");
                            if (this.enterprisePolicy != null) {
                            }
                            countryDetectorService = countryDetectorService322;
                            timingsTraceAndSlog.traceEnd();
                            if (!hasSystemFeature4) {
                            }
                            if (!context.getResources().getBoolean(R.bool.config_enableFusedLocationOverlay)) {
                            }
                            timingsTraceAndSlog.traceBegin("StartWallpaperEffectsGenerationService");
                            this.mSystemServiceManager.startService("com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartAudioService");
                            if (hasSystemFeature5) {
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartSoundTriggerMiddlewareService");
                            this.mSystemServiceManager.startService(SoundTriggerMiddlewareService.Lifecycle.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("LedCoverAppService");
                            this.mLedCoverAppService = new LedCoverAppService(context);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartLedBackCoverService");
                            this.mLedBackCoverService = new LedBackCoverService(context);
                            timingsTraceAndSlog.traceEnd();
                            if (this.mPackageManager.hasSystemFeature("android.hardware.broadcastradio")) {
                            }
                            if (!hasSystemFeature6) {
                            }
                            if (hasSystemFeature4) {
                            }
                            if (!hasSystemFeature4) {
                            }
                            if (this.mPackageManager.hasSystemFeature("android.software.midi")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartAdbService");
                            this.mSystemServiceManager.startService("com.android.server.adb.AdbService$Lifecycle");
                            timingsTraceAndSlog.traceEnd();
                            if (!this.mPackageManager.hasSystemFeature("android.hardware.usb.host")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartUsbService");
                            this.mSystemServiceManager.startService("com.android.server.usb.UsbService$Lifecycle");
                            timingsTraceAndSlog.traceEnd();
                            if (!hasSystemFeature4) {
                            }
                            timingsTraceAndSlog.traceBegin("StartHardwarePropertiesManagerService");
                            ServiceManager.addService("hardware_properties", new HardwarePropertiesManagerService(context));
                            timingsTraceAndSlog.traceEnd();
                            if (!hasSystemFeature4) {
                            }
                            timingsTraceAndSlog.traceBegin("StartColorDisplay");
                            this.mSystemServiceManager.startService(ColorDisplayService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartJobScheduler");
                            this.mSystemServiceManager.startService("com.android.server.job.JobSchedulerService");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartSoundTrigger");
                            this.mSystemServiceManager.startService(SoundTriggerService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartTrustManager");
                            this.mSystemServiceManager.startService(TrustManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            if (this.mPackageManager.hasSystemFeature("android.software.backup")) {
                            }
                            if (!this.mPackageManager.hasSystemFeature("android.software.app_widgets")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartAppWidgetService");
                            this.mSystemServiceManager.startService("com.android.server.appwidget.AppWidgetService");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartVoiceRecognitionManager");
                            this.mSystemServiceManager.startService("com.android.server.voiceinteraction.VoiceInteractionManagerService");
                            timingsTraceAndSlog.traceEnd();
                            if (GestureLauncherService.isGestureLauncherEnabled(context.getResources())) {
                            }
                            timingsTraceAndSlog.traceBegin("StartSensorNotification");
                            this.mSystemServiceManager.startService(SensorNotificationService.class);
                            timingsTraceAndSlog.traceEnd();
                            if (this.mPackageManager.hasSystemFeature("android.hardware.context_hub")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartDiskStatsService");
                            ServiceManager.addService("diskstats", new DiskStatsService(context));
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("RuntimeService");
                            ServiceManager.addService("runtime", new RuntimeService(context));
                            timingsTraceAndSlog.traceEnd();
                            if (hasSystemFeature4) {
                            }
                            networkTimeUpdateService = null;
                            timingsTraceAndSlog.traceBegin("CertBlacklister");
                            new CertBlacklister(context);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartEmergencyAffordanceService");
                            this.mSystemServiceManager.startService(EmergencyAffordanceService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("startBlobStoreManagerService");
                            this.mSystemServiceManager.startService("com.android.server.blob.BlobStoreManagerService");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartDreamManager");
                            this.mSystemServiceManager.startService(DreamManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("AddGraphicsStatsService");
                            ServiceManager.addService("graphicsstats", new GraphicsStatsService(context));
                            timingsTraceAndSlog.traceEnd();
                            if (CoverageService.ENABLED) {
                            }
                            if (this.mPackageManager.hasSystemFeature("android.software.print")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartAttestationVerificationService");
                            this.mSystemServiceManager.startService(AttestationVerificationManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            if (this.mPackageManager.hasSystemFeature("android.software.companion_device_setup")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartRestrictionManager");
                            this.mSystemServiceManager.startService(RestrictionsManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("CocktailBarService");
                            this.mSystemServiceManager.startService("com.android.server.cocktailbar.CocktailBarManagerService");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("SemInputDeviceManagerService SystemReady");
                            Slog.i("SystemServer", "SemInputDeviceManagerService SystemReady loader");
                            Class<?> cls322 = Class.forName("com.samsung.android.hardware.secinputdev.SemInputDeviceManagerLoader");
                            cls322.getDeclaredMethod("systemReady", new Class[0]).invoke(cls322, new Object[0]);
                            timingsTraceAndSlog.traceEnd();
                            if (this.mFactoryTestMode != 1) {
                            }
                            timingsTraceAndSlog.traceBegin("StartAODManagerService");
                            this.mSystemServiceManager.startService("com.android.server.aod.AODManagerService");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("Samsung Accessory Manager");
                            packageManager2 = context.getPackageManager();
                            if (packageManager2 != null) {
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("BattAuthManager");
                            packageManager = context.getPackageManager();
                            if (packageManager != null) {
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartMediaSessionService");
                            this.mSystemServiceManager.startService("com.android.server.media.MediaSessionService");
                            timingsTraceAndSlog.traceEnd();
                            if (this.mPackageManager.hasSystemFeature("android.hardware.hdmi.cec")) {
                            }
                            if (!this.mPackageManager.hasSystemFeature("android.software.live_tv")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartTvInteractiveAppManager");
                            this.mSystemServiceManager.startService(TvInteractiveAppManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            if (!this.mPackageManager.hasSystemFeature("android.software.live_tv")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartTvInputManager");
                            this.mSystemServiceManager.startService(TvInputManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            if (this.mPackageManager.hasSystemFeature("android.hardware.tv.tuner")) {
                            }
                            if (this.mPackageManager.hasSystemFeature("android.software.picture_in_picture")) {
                            }
                            if (this.mPackageManager.hasSystemFeature("android.software.leanback")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartMediaRouterService");
                            IMediaRouterService.Stub mediaRouterService222 = new MediaRouterService(context);
                            ServiceManager.addService("media_router", mediaRouterService222);
                            MediaRouterService mediaRouterService322 = mediaRouterService222;
                            timingsTraceAndSlog.traceEnd();
                            hasSystemFeature = this.mPackageManager.hasSystemFeature("android.hardware.biometrics.face");
                            hasSystemFeature2 = this.mPackageManager.hasSystemFeature("android.hardware.biometrics.iris");
                            hasSystemFeature3 = this.mPackageManager.hasSystemFeature("android.hardware.fingerprint");
                            if (hasSystemFeature) {
                            }
                            if (hasSystemFeature2) {
                            }
                            if (hasSystemFeature3) {
                            }
                            timingsTraceAndSlog.traceBegin("StartBiometricService");
                            this.mSystemServiceManager.startService(BiometricService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartAuthService");
                            this.mSystemServiceManager.startService(AuthService.class);
                            timingsTraceAndSlog.traceEnd();
                            if (!hasSystemFeature4) {
                            }
                            if (!hasSystemFeature4) {
                            }
                            timingsTraceAndSlog.traceBegin("StartSmartThingsService");
                            this.mSmartThingsService = new SmartThingsService(context);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartShortcutServiceLifecycle");
                            this.mSystemServiceManager.startService(ShortcutService.Lifecycle.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartLauncherAppsService");
                            this.mSystemServiceManager.startService(LauncherAppsService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartCrossProfileAppsService");
                            this.mSystemServiceManager.startService(CrossProfileAppsService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartPeopleService");
                            this.mSystemServiceManager.startService(PeopleService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartMediaMetricsManager");
                            this.mSystemServiceManager.startService(MediaMetricsManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartBackgroundInstallControlService");
                            this.mSystemServiceManager.startService(BackgroundInstallControlService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartChimeraService");
                            ServiceManager.addService("ChimeraManagerService", new ChimeraManagerService(context, this.mActivityManagerService));
                            Slog.i("SystemServer", "ChimeraManagerService loaded");
                            timingsTraceAndSlog.traceEnd();
                            if (context.getPackageManager().hasSystemFeature("android.hardware.wifi")) {
                            }
                            if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.direct")) {
                            }
                            if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.aware")) {
                            }
                            iBinder = iBinder422;
                            networkPolicyManagerService = networkPolicyManagerService422;
                            mediaRouterService = mediaRouterService322;
                            iLockSettings3 = iLockSettings2;
                            vcnManagementService = vcnManagementService222;
                            lifecycle2 = lifecycle;
                            networkTimeUpdateService2 = networkTimeUpdateService;
                            final IBinder iBinder2222222 = null;
                            timingsTraceAndSlog.traceBegin("StartMediaProjectionManager");
                            this.mSystemServiceManager.startService(MediaProjectionManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            if (!this.mPackageManager.hasSystemFeature("att.devicehealth.support")) {
                            }
                            if (hasSystemFeature4) {
                            }
                            if (!this.mPackageManager.hasSystemFeature("android.software.slices_disabled")) {
                            }
                            if (context.getPackageManager().hasSystemFeature("android.hardware.type.embedded")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartSpenGestureManagerService");
                            ServiceManager.addService("spengestureservice", new SpenGestureManagerService(context, main));
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartSamsungNotesService");
                            this.mSamsungNotesService = new SamsungNotesService(context);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartStatsCompanion");
                            this.mSystemServiceManager.startServiceFromJar("com.android.server.stats.StatsCompanion$Lifecycle", "/apex/com.android.os.statsd/javalib/service-statsd.jar");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartRebootReadinessManagerService");
                            this.mSystemServiceManager.startServiceFromJar("com.android.server.scheduling.RebootReadinessManagerService$Lifecycle", "/apex/com.android.scheduling/javalib/service-scheduling.jar");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartStatsPullAtomService");
                            this.mSystemServiceManager.startService("com.android.server.stats.pull.StatsPullAtomService");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StatsBootstrapAtomService");
                            this.mSystemServiceManager.startService("com.android.server.stats.bootstrap.StatsBootstrapAtomService$Lifecycle");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartIncidentCompanionService");
                            this.mSystemServiceManager.startService(IncidentCompanionService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StarSdkSandboxManagerService");
                            this.mSystemServiceManager.startService("com.android.server.sdksandbox.SdkSandboxManagerService$Lifecycle");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartAdServicesManagerService");
                            startService = this.mSystemServiceManager.startService("com.android.server.adservices.AdServicesManagerService$Lifecycle");
                            if (startService instanceof Dumpable) {
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartOnDevicePersonalizationSystemService");
                            this.mSystemServiceManager.startService("com.android.server.ondevicepersonalization.OnDevicePersonalizationSystemService$Lifecycle");
                            timingsTraceAndSlog.traceEnd();
                            if (detectSafeMode) {
                            }
                            final boolean z5222222 = context.getResources().getBoolean(R.bool.config_restartRadioAfterProvisioning);
                            if (this.mPackageManager.hasSystemFeature("android.hardware.telephony")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartMmsService");
                            MmsServiceBroker mmsServiceBroker2222222 = (MmsServiceBroker) this.mSystemServiceManager.startService(MmsServiceBroker.class);
                            timingsTraceAndSlog.traceEnd();
                            mmsServiceBroker = mmsServiceBroker2222222;
                            if (this.mPackageManager.hasSystemFeature("android.software.autofill")) {
                            }
                            if (this.mPackageManager.hasSystemFeature("android.software.credentials")) {
                            }
                            if (!deviceHasConfigString(context, R.string.default_audio_route_category_name)) {
                            }
                            timingsTraceAndSlog.traceBegin("StartSelectionToolbarManagerService");
                            this.mSystemServiceManager.startService("com.android.server.selectiontoolbar.SelectionToolbarManagerService");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartClipboardService");
                            this.mSystemServiceManager.startService(ClipboardService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("AppServiceManager");
                            this.mSystemServiceManager.startService(AppBindingService.Lifecycle.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("startTracingServiceProxy");
                            this.mSystemServiceManager.startService(TracingServiceProxy.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("MakeLockSettingsServiceReady");
                            if (iLockSettings3 != null) {
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartPersonaSystemready");
                            if ("2.0".equals(SemPersonaManager.getKnoxInfo().getString("version"))) {
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartBootPhaseLockSettingsReady");
                            Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_LOCK_SETTINGS_READY");
                            this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, SystemService.PHASE_LOCK_SETTINGS_READY);
                            timingsTraceAndSlog.traceEnd();
                            createInstance = HsumBootUserInitializer.createInstance(this.mActivityManagerService, this.mPackageManagerService, this.mContentResolver, context.getResources().getBoolean(R.bool.config_focusScrollContainersInTouchMode));
                            if (createInstance != null) {
                            }
                            timingsTraceAndSlog.traceBegin("StartBootPhaseSystemServicesReady");
                            Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_SYSTEM_SERVICES_READY");
                            this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 500);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("MakeWindowManagerServiceReady");
                            main.systemReady();
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("RegisterLogMteState");
                            LogMteState.register(context);
                            timingsTraceAndSlog.traceEnd();
                            synchronized (SystemService.class) {
                            }
                        }
                    } catch (RuntimeException e10) {
                        runtimeException = e10;
                        spegService = null;
                    }
                    timingsTraceAndSlog.traceEnd();
                    if (CoreRune.SYSFW_APP_PREL && spegService != null) {
                        timingsTraceAndSlog.traceBegin("AppPrelaunchService");
                        AppPrelaunchManagerService appPrelaunchManagerService2 = (AppPrelaunchManagerService) this.mSystemServiceManager.startService(AppPrelaunchManagerService.class);
                        appPrelaunchManagerService2.initPrelauncher(spegService, this.mActivityManagerService);
                        LocalServices.addService(AppPrelaunchManagerService.class, appPrelaunchManagerService2);
                        timingsTraceAndSlog.traceEnd();
                    }
                }
                timingsTraceAndSlog.traceBegin("StartFontManagerService");
                this.mSystemServiceManager.startService(new FontManagerService.Lifecycle(context, detectSafeMode));
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartTextServicesManager");
                this.mSystemServiceManager.startService(TextServicesManagerService.Lifecycle.class);
                timingsTraceAndSlog.traceEnd();
                if (!z2) {
                    timingsTraceAndSlog.traceBegin("StartTextClassificationManagerService");
                    this.mSystemServiceManager.startService(TextClassificationManagerService.Lifecycle.class);
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("StartNetworkScoreService");
                this.mSystemServiceManager.startService(NetworkScoreService.Lifecycle.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartNetworkStatsService");
                this.mSystemServiceManager.startServiceFromJar("com.android.server.NetworkStatsServiceInitializer", "/apex/com.android.tethering/javalib/service-connectivity.jar");
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartNetworkPolicyManagerService");
                INetworkPolicyManager.Stub networkPolicyManagerService2222 = new NetworkPolicyManagerService(context, this.mActivityManagerService, iNetworkManagementService222);
                ServiceManager.addService("netpolicy", networkPolicyManagerService2222);
                NetworkPolicyManagerService networkPolicyManagerService3222 = networkPolicyManagerService2222;
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartUrspService");
                UrspService urspService2222 = new UrspService(context);
                ServiceManager.addService("urspservice", urspService2222);
                timingsTraceAndSlog.traceEnd();
                if (context.getPackageManager().hasSystemFeature("android.hardware.wifi")) {
                    timingsTraceAndSlog.traceBegin("StartWifi");
                    iNetworkManagementService = iNetworkManagementService222;
                    this.mSystemServiceManager.startServiceFromJar("com.android.server.wifi.WifiService", "/apex/com.android.wifi/javalib/service-wifi.jar");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartWifiScanning");
                    this.mSystemServiceManager.startServiceFromJar("com.android.server.wifi.scanner.WifiScanningService", "/apex/com.android.wifi/javalib/service-wifi.jar");
                    timingsTraceAndSlog.traceEnd();
                } else {
                    iNetworkManagementService = iNetworkManagementService222;
                }
                if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.rtt")) {
                    timingsTraceAndSlog.traceBegin("StartRttService");
                    this.mSystemServiceManager.startServiceFromJar("com.android.server.wifi.rtt.RttService", "/apex/com.android.wifi/javalib/service-wifi.jar");
                    timingsTraceAndSlog.traceEnd();
                }
                if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.aware")) {
                    timingsTraceAndSlog.traceBegin("StartWifiAware");
                    this.mSystemServiceManager.startServiceFromJar("com.android.server.wifi.aware.WifiAwareService", "/apex/com.android.wifi/javalib/service-wifi.jar");
                    timingsTraceAndSlog.traceEnd();
                }
                if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.direct")) {
                    timingsTraceAndSlog.traceBegin("StartWifiP2P");
                    this.mSystemServiceManager.startServiceFromJar("com.android.server.wifi.p2p.WifiP2pService", "/apex/com.android.wifi/javalib/service-wifi.jar");
                    timingsTraceAndSlog.traceEnd();
                }
                if (context.getPackageManager().hasSystemFeature("android.hardware.lowpan")) {
                    timingsTraceAndSlog.traceBegin("StartLowpan");
                    this.mSystemServiceManager.startService("com.android.server.lowpan.LowpanService");
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("StartPacProxyService");
                ServiceManager.addService("pac_proxy", new PacProxyService(context));
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartIntelligentBatterySaverService");
                ServiceManager.addService("IntelligentBatterySaverService", new IntelligentBatterySaverService(context));
                timingsTraceAndSlog.traceEnd();
                if (!FactoryTest.isFactoryBinary()) {
                    timingsTraceAndSlog.traceBegin("StartKnoxGuard");
                    try {
                        if (SystemProperties.getInt("ro.product.first_api_level", 0) >= 30) {
                            ServiceManager.addService("knoxguard_service", new KnoxGuardSeService(context));
                        } else {
                            ServiceManager.addService("knoxguard_service", new KnoxGuardService(context));
                        }
                    } catch (Throwable th41) {
                        Slog.e("SystemServer", "Failed to add KnoxGuardService.");
                        th41.printStackTrace();
                    }
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("StartConnectivityService");
                this.mSystemServiceManager.startServiceFromJar("com.android.server.ConnectivityServiceInitializer", "/apex/com.android.tethering/javalib/service-connectivity.jar");
                networkPolicyManagerService3222.bindConnectivityManager();
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartExtendedEthernetService");
                this.mSystemServiceManager.startService("com.android.server.ExtendedEthernetService");
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartVpnManagerService");
                IVpnManager.Stub create322 = VpnManagerService.create(context);
                ServiceManager.addService("vpn_management", create322);
                VpnManagerService vpnManagerService2222 = create322;
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartVcnManagementService");
                IVcnManagementService.Stub create2222 = VcnManagementService.create(context);
                ServiceManager.addService("vcn_management", create2222);
                NetworkPolicyManagerService networkPolicyManagerService4222 = networkPolicyManagerService3222;
                VcnManagementService vcnManagementService2222 = create2222;
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartSystemUpdateManagerService");
                ServiceManager.addService("system_update", new SystemUpdateManagerService(context));
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartUpdateLockService");
                ServiceManager.addService("updatelock", new UpdateLockService(context));
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartNotificationManager");
                this.mSystemServiceManager.startService(NotificationManagerService.class);
                SystemNotificationChannels.removeDeprecated(context);
                SystemNotificationChannels.createAll(context);
                INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartDeviceMonitor");
                this.mSystemServiceManager.startService(DeviceStorageMonitorService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartTimeDetectorService");
                this.mSystemServiceManager.startService("com.android.server.timedetector.TimeDetectorService$Lifecycle");
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartLocationManagerService");
                this.mSystemServiceManager.startService(LocationManagerService.Lifecycle.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartSLocationService");
                Slog.i("SystemServer", "SLocation Manager");
                vpnManagerService = vpnManagerService2222;
                urspService = urspService2222;
                IBinder iBinder4222 = (IBinder) Class.forName("com.samsung.android.location.SLocationLoader").getDeclaredMethod("getSLocationService", Context.class).invoke(null, context);
                ServiceManager.addService("sec_location", iBinder4222);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("SemMdnieManagerService");
                Slog.i("SystemServer", "mDNIe Service");
                ServiceManager.addService("mDNIe", (IBinder) Class.forName("com.samsung.android.hardware.display.SemMdnieManagerService").getConstructor(Context.class).newInstance(context));
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("SemDisplaySolution");
                Slog.i("SystemServer", "SemDisplaySolution Service");
                ServiceManager.addService("DisplaySolution", (IBinder) Class.forName("com.samsung.android.displaysolution.SemDisplaySolutionManagerService").getConstructor(Context.class).newInstance(context));
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartCountryDetectorService");
                ICountryDetector.Stub countryDetectorService2222 = new CountryDetectorService(context);
                ServiceManager.addService("country_detector", countryDetectorService2222);
                CountryDetectorService countryDetectorService3222 = countryDetectorService2222;
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartTimeZoneDetectorService");
                this.mSystemServiceManager.startService("com.android.server.timezonedetector.TimeZoneDetectorService$Lifecycle");
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartAltitudeService");
                this.mSystemServiceManager.startService(AltitudeService.Lifecycle.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartLocationTimeZoneManagerService");
                this.mSystemServiceManager.startService("com.android.server.timezonedetector.location.LocationTimeZoneManagerService$Lifecycle");
                timingsTraceAndSlog.traceEnd();
                if (context.getResources().getBoolean(R.bool.config_dreamsEnabledOnBattery)) {
                    timingsTraceAndSlog.traceBegin("StartGnssTimeUpdateService");
                    try {
                        this.mSystemServiceManager.startService("com.android.server.timedetector.GnssTimeUpdateService$Lifecycle");
                    } catch (Throwable th42) {
                        reportWtf("starting GnssTimeUpdateService service", th42);
                    }
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("KnoxVpnService");
                if (this.enterprisePolicy != null) {
                    timingsTraceAndSlog.traceBegin("Adding KnoxVpnEngineService");
                    Slog.i("SystemServer", "Adding KnoxVpnEngineService");
                    IKnoxVpnPolicy.Stub knoxVpnEngineService = new KnoxVpnEngineService(context);
                    ServiceManager.addService("knox_vpn_policy", knoxVpnEngineService);
                    EnterpriseService.addPolicyService("knox_vpn_policy", knoxVpnEngineService);
                    timingsTraceAndSlog.traceEnd();
                }
                countryDetectorService = countryDetectorService3222;
                timingsTraceAndSlog.traceEnd();
                if (!hasSystemFeature4) {
                    timingsTraceAndSlog.traceBegin("StartSearchManagerService");
                    try {
                        this.mSystemServiceManager.startService("com.android.server.search.SearchManagerService$Lifecycle");
                    } catch (Throwable th43) {
                        reportWtf("starting Search Service", th43);
                    }
                    timingsTraceAndSlog.traceEnd();
                }
                if (!context.getResources().getBoolean(R.bool.config_enableFusedLocationOverlay)) {
                    timingsTraceAndSlog.traceBegin("StartWallpaperManagerService");
                    this.mSystemServiceManager.startService("com.android.server.wallpaper.WallpaperManagerService$Lifecycle");
                    timingsTraceAndSlog.traceEnd();
                } else {
                    Slog.i("SystemServer", "Wallpaper service disabled by config");
                }
                timingsTraceAndSlog.traceBegin("StartWallpaperEffectsGenerationService");
                this.mSystemServiceManager.startService("com.android.server.wallpapereffectsgeneration.WallpaperEffectsGenerationManagerService");
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartAudioService");
                if (hasSystemFeature5) {
                    this.mSystemServiceManager.startService(AudioService.Lifecycle.class);
                } else {
                    String string3 = context.getResources().getString(R.string.default_sms_application);
                    try {
                        this.mSystemServiceManager.startService(string3 + "$Lifecycle");
                    } catch (Throwable th44) {
                        reportWtf("starting " + string3, th44);
                    }
                }
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartSoundTriggerMiddlewareService");
                this.mSystemServiceManager.startService(SoundTriggerMiddlewareService.Lifecycle.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("LedCoverAppService");
                this.mLedCoverAppService = new LedCoverAppService(context);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartLedBackCoverService");
                this.mLedBackCoverService = new LedBackCoverService(context);
                timingsTraceAndSlog.traceEnd();
                if (this.mPackageManager.hasSystemFeature("android.hardware.broadcastradio")) {
                    timingsTraceAndSlog.traceBegin("StartBroadcastRadioService");
                    this.mSystemServiceManager.startService(BroadcastRadioService.class);
                    timingsTraceAndSlog.traceEnd();
                }
                if (!hasSystemFeature6) {
                    timingsTraceAndSlog.traceBegin("StartDockObserver");
                    this.mSystemServiceManager.startService(DockObserver.class);
                    timingsTraceAndSlog.traceEnd();
                }
                if (hasSystemFeature4) {
                    timingsTraceAndSlog.traceBegin("StartThermalObserver");
                    this.mSystemServiceManager.startService("com.android.clockwork.ThermalObserver");
                    timingsTraceAndSlog.traceEnd();
                }
                if (!hasSystemFeature4) {
                    timingsTraceAndSlog.traceBegin("StartWiredAccessoryManager");
                    try {
                        inputManagerService222.setWiredAccessoryCallbacks(new WiredAccessoryManager(context, inputManagerService222));
                    } catch (Throwable th45) {
                        reportWtf("starting WiredAccessoryManager", th45);
                    }
                    timingsTraceAndSlog.traceEnd();
                }
                if (this.mPackageManager.hasSystemFeature("android.software.midi")) {
                    timingsTraceAndSlog.traceBegin("StartMidiManager");
                    this.mSystemServiceManager.startService("com.android.server.midi.MidiService$Lifecycle");
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("StartAdbService");
                this.mSystemServiceManager.startService("com.android.server.adb.AdbService$Lifecycle");
                timingsTraceAndSlog.traceEnd();
                if (!this.mPackageManager.hasSystemFeature("android.hardware.usb.host") || this.mPackageManager.hasSystemFeature("android.hardware.usb.accessory") || equals) {
                    timingsTraceAndSlog.traceBegin("StartUsbService");
                    this.mSystemServiceManager.startService("com.android.server.usb.UsbService$Lifecycle");
                    timingsTraceAndSlog.traceEnd();
                }
                if (!hasSystemFeature4) {
                    timingsTraceAndSlog.traceBegin("StartSerialService");
                    try {
                        ServiceManager.addService("serial", new SerialService(context));
                    } catch (Throwable th46) {
                        Slog.e("SystemServer", "Failure starting SerialService", th46);
                    }
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("StartHardwarePropertiesManagerService");
                ServiceManager.addService("hardware_properties", new HardwarePropertiesManagerService(context));
                timingsTraceAndSlog.traceEnd();
                if (!hasSystemFeature4) {
                    timingsTraceAndSlog.traceBegin("StartTwilightService");
                    this.mSystemServiceManager.startService(TwilightService.class);
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("StartColorDisplay");
                this.mSystemServiceManager.startService(ColorDisplayService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartJobScheduler");
                this.mSystemServiceManager.startService("com.android.server.job.JobSchedulerService");
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartSoundTrigger");
                this.mSystemServiceManager.startService(SoundTriggerService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartTrustManager");
                this.mSystemServiceManager.startService(TrustManagerService.class);
                timingsTraceAndSlog.traceEnd();
                if (this.mPackageManager.hasSystemFeature("android.software.backup")) {
                    timingsTraceAndSlog.traceBegin("StartBackupManager");
                    this.mSystemServiceManager.startService("com.android.server.backup.BackupManagerService$Lifecycle");
                    timingsTraceAndSlog.traceEnd();
                }
                if (!this.mPackageManager.hasSystemFeature("android.software.app_widgets") || context.getResources().getBoolean(R.bool.config_dozeAfterScreenOffByDefault)) {
                    timingsTraceAndSlog.traceBegin("StartAppWidgetService");
                    this.mSystemServiceManager.startService("com.android.server.appwidget.AppWidgetService");
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("StartVoiceRecognitionManager");
                this.mSystemServiceManager.startService("com.android.server.voiceinteraction.VoiceInteractionManagerService");
                timingsTraceAndSlog.traceEnd();
                if (GestureLauncherService.isGestureLauncherEnabled(context.getResources())) {
                    timingsTraceAndSlog.traceBegin("StartGestureLauncher");
                    this.mSystemServiceManager.startService(GestureLauncherService.class);
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("StartSensorNotification");
                this.mSystemServiceManager.startService(SensorNotificationService.class);
                timingsTraceAndSlog.traceEnd();
                if (this.mPackageManager.hasSystemFeature("android.hardware.context_hub")) {
                    timingsTraceAndSlog.traceBegin("StartContextHubSystemService");
                    this.mSystemServiceManager.startService(ContextHubSystemService.class);
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("StartDiskStatsService");
                ServiceManager.addService("diskstats", new DiskStatsService(context));
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("RuntimeService");
                ServiceManager.addService("runtime", new RuntimeService(context));
                timingsTraceAndSlog.traceEnd();
                if (!hasSystemFeature4 || z) {
                    networkTimeUpdateService = null;
                } else {
                    timingsTraceAndSlog.traceBegin("StartNetworkTimeUpdateService");
                    try {
                        networkTimeUpdateService = new NetworkTimeUpdateService(context);
                        try {
                            ServiceManager.addService("network_time_update_service", networkTimeUpdateService);
                        } catch (Throwable th47) {
                            th5 = th47;
                            reportWtf("starting NetworkTimeUpdate service", th5);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("CertBlacklister");
                            new CertBlacklister(context);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartEmergencyAffordanceService");
                            this.mSystemServiceManager.startService(EmergencyAffordanceService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("startBlobStoreManagerService");
                            this.mSystemServiceManager.startService("com.android.server.blob.BlobStoreManagerService");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartDreamManager");
                            this.mSystemServiceManager.startService(DreamManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("AddGraphicsStatsService");
                            ServiceManager.addService("graphicsstats", new GraphicsStatsService(context));
                            timingsTraceAndSlog.traceEnd();
                            if (CoverageService.ENABLED) {
                            }
                            if (this.mPackageManager.hasSystemFeature("android.software.print")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartAttestationVerificationService");
                            this.mSystemServiceManager.startService(AttestationVerificationManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            if (this.mPackageManager.hasSystemFeature("android.software.companion_device_setup")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartRestrictionManager");
                            this.mSystemServiceManager.startService(RestrictionsManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("CocktailBarService");
                            this.mSystemServiceManager.startService("com.android.server.cocktailbar.CocktailBarManagerService");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("SemInputDeviceManagerService SystemReady");
                            Slog.i("SystemServer", "SemInputDeviceManagerService SystemReady loader");
                            Class<?> cls3222 = Class.forName("com.samsung.android.hardware.secinputdev.SemInputDeviceManagerLoader");
                            cls3222.getDeclaredMethod("systemReady", new Class[0]).invoke(cls3222, new Object[0]);
                            timingsTraceAndSlog.traceEnd();
                            if (this.mFactoryTestMode != 1) {
                            }
                            timingsTraceAndSlog.traceBegin("StartAODManagerService");
                            this.mSystemServiceManager.startService("com.android.server.aod.AODManagerService");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("Samsung Accessory Manager");
                            packageManager2 = context.getPackageManager();
                            if (packageManager2 != null) {
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("BattAuthManager");
                            packageManager = context.getPackageManager();
                            if (packageManager != null) {
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartMediaSessionService");
                            this.mSystemServiceManager.startService("com.android.server.media.MediaSessionService");
                            timingsTraceAndSlog.traceEnd();
                            if (this.mPackageManager.hasSystemFeature("android.hardware.hdmi.cec")) {
                            }
                            if (!this.mPackageManager.hasSystemFeature("android.software.live_tv")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartTvInteractiveAppManager");
                            this.mSystemServiceManager.startService(TvInteractiveAppManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            if (!this.mPackageManager.hasSystemFeature("android.software.live_tv")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartTvInputManager");
                            this.mSystemServiceManager.startService(TvInputManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            if (this.mPackageManager.hasSystemFeature("android.hardware.tv.tuner")) {
                            }
                            if (this.mPackageManager.hasSystemFeature("android.software.picture_in_picture")) {
                            }
                            if (this.mPackageManager.hasSystemFeature("android.software.leanback")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartMediaRouterService");
                            IMediaRouterService.Stub mediaRouterService2222 = new MediaRouterService(context);
                            ServiceManager.addService("media_router", mediaRouterService2222);
                            MediaRouterService mediaRouterService3222 = mediaRouterService2222;
                            timingsTraceAndSlog.traceEnd();
                            hasSystemFeature = this.mPackageManager.hasSystemFeature("android.hardware.biometrics.face");
                            hasSystemFeature2 = this.mPackageManager.hasSystemFeature("android.hardware.biometrics.iris");
                            hasSystemFeature3 = this.mPackageManager.hasSystemFeature("android.hardware.fingerprint");
                            if (hasSystemFeature) {
                            }
                            if (hasSystemFeature2) {
                            }
                            if (hasSystemFeature3) {
                            }
                            timingsTraceAndSlog.traceBegin("StartBiometricService");
                            this.mSystemServiceManager.startService(BiometricService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartAuthService");
                            this.mSystemServiceManager.startService(AuthService.class);
                            timingsTraceAndSlog.traceEnd();
                            if (!hasSystemFeature4) {
                            }
                            if (!hasSystemFeature4) {
                            }
                            timingsTraceAndSlog.traceBegin("StartSmartThingsService");
                            this.mSmartThingsService = new SmartThingsService(context);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartShortcutServiceLifecycle");
                            this.mSystemServiceManager.startService(ShortcutService.Lifecycle.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartLauncherAppsService");
                            this.mSystemServiceManager.startService(LauncherAppsService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartCrossProfileAppsService");
                            this.mSystemServiceManager.startService(CrossProfileAppsService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartPeopleService");
                            this.mSystemServiceManager.startService(PeopleService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartMediaMetricsManager");
                            this.mSystemServiceManager.startService(MediaMetricsManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartBackgroundInstallControlService");
                            this.mSystemServiceManager.startService(BackgroundInstallControlService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartChimeraService");
                            ServiceManager.addService("ChimeraManagerService", new ChimeraManagerService(context, this.mActivityManagerService));
                            Slog.i("SystemServer", "ChimeraManagerService loaded");
                            timingsTraceAndSlog.traceEnd();
                            if (context.getPackageManager().hasSystemFeature("android.hardware.wifi")) {
                            }
                            if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.direct")) {
                            }
                            if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.aware")) {
                            }
                            iBinder = iBinder4222;
                            networkPolicyManagerService = networkPolicyManagerService4222;
                            mediaRouterService = mediaRouterService3222;
                            iLockSettings3 = iLockSettings2;
                            vcnManagementService = vcnManagementService2222;
                            lifecycle2 = lifecycle;
                            networkTimeUpdateService2 = networkTimeUpdateService;
                            final IBinder iBinder22222222 = null;
                            timingsTraceAndSlog.traceBegin("StartMediaProjectionManager");
                            this.mSystemServiceManager.startService(MediaProjectionManagerService.class);
                            timingsTraceAndSlog.traceEnd();
                            if (!this.mPackageManager.hasSystemFeature("att.devicehealth.support")) {
                            }
                            if (hasSystemFeature4) {
                            }
                            if (!this.mPackageManager.hasSystemFeature("android.software.slices_disabled")) {
                            }
                            if (context.getPackageManager().hasSystemFeature("android.hardware.type.embedded")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartSpenGestureManagerService");
                            ServiceManager.addService("spengestureservice", new SpenGestureManagerService(context, main));
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartSamsungNotesService");
                            this.mSamsungNotesService = new SamsungNotesService(context);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartStatsCompanion");
                            this.mSystemServiceManager.startServiceFromJar("com.android.server.stats.StatsCompanion$Lifecycle", "/apex/com.android.os.statsd/javalib/service-statsd.jar");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartRebootReadinessManagerService");
                            this.mSystemServiceManager.startServiceFromJar("com.android.server.scheduling.RebootReadinessManagerService$Lifecycle", "/apex/com.android.scheduling/javalib/service-scheduling.jar");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartStatsPullAtomService");
                            this.mSystemServiceManager.startService("com.android.server.stats.pull.StatsPullAtomService");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StatsBootstrapAtomService");
                            this.mSystemServiceManager.startService("com.android.server.stats.bootstrap.StatsBootstrapAtomService$Lifecycle");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartIncidentCompanionService");
                            this.mSystemServiceManager.startService(IncidentCompanionService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StarSdkSandboxManagerService");
                            this.mSystemServiceManager.startService("com.android.server.sdksandbox.SdkSandboxManagerService$Lifecycle");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartAdServicesManagerService");
                            startService = this.mSystemServiceManager.startService("com.android.server.adservices.AdServicesManagerService$Lifecycle");
                            if (startService instanceof Dumpable) {
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartOnDevicePersonalizationSystemService");
                            this.mSystemServiceManager.startService("com.android.server.ondevicepersonalization.OnDevicePersonalizationSystemService$Lifecycle");
                            timingsTraceAndSlog.traceEnd();
                            if (detectSafeMode) {
                            }
                            final boolean z52222222 = context.getResources().getBoolean(R.bool.config_restartRadioAfterProvisioning);
                            if (this.mPackageManager.hasSystemFeature("android.hardware.telephony")) {
                            }
                            timingsTraceAndSlog.traceBegin("StartMmsService");
                            MmsServiceBroker mmsServiceBroker22222222 = (MmsServiceBroker) this.mSystemServiceManager.startService(MmsServiceBroker.class);
                            timingsTraceAndSlog.traceEnd();
                            mmsServiceBroker = mmsServiceBroker22222222;
                            if (this.mPackageManager.hasSystemFeature("android.software.autofill")) {
                            }
                            if (this.mPackageManager.hasSystemFeature("android.software.credentials")) {
                            }
                            if (!deviceHasConfigString(context, R.string.default_audio_route_category_name)) {
                            }
                            timingsTraceAndSlog.traceBegin("StartSelectionToolbarManagerService");
                            this.mSystemServiceManager.startService("com.android.server.selectiontoolbar.SelectionToolbarManagerService");
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartClipboardService");
                            this.mSystemServiceManager.startService(ClipboardService.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("AppServiceManager");
                            this.mSystemServiceManager.startService(AppBindingService.Lifecycle.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("startTracingServiceProxy");
                            this.mSystemServiceManager.startService(TracingServiceProxy.class);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("MakeLockSettingsServiceReady");
                            if (iLockSettings3 != null) {
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartPersonaSystemready");
                            if ("2.0".equals(SemPersonaManager.getKnoxInfo().getString("version"))) {
                            }
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("StartBootPhaseLockSettingsReady");
                            Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_LOCK_SETTINGS_READY");
                            this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, SystemService.PHASE_LOCK_SETTINGS_READY);
                            timingsTraceAndSlog.traceEnd();
                            createInstance = HsumBootUserInitializer.createInstance(this.mActivityManagerService, this.mPackageManagerService, this.mContentResolver, context.getResources().getBoolean(R.bool.config_focusScrollContainersInTouchMode));
                            if (createInstance != null) {
                            }
                            timingsTraceAndSlog.traceBegin("StartBootPhaseSystemServicesReady");
                            Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_SYSTEM_SERVICES_READY");
                            this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 500);
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("MakeWindowManagerServiceReady");
                            main.systemReady();
                            timingsTraceAndSlog.traceEnd();
                            timingsTraceAndSlog.traceBegin("RegisterLogMteState");
                            LogMteState.register(context);
                            timingsTraceAndSlog.traceEnd();
                            synchronized (SystemService.class) {
                            }
                        }
                    } catch (Throwable th48) {
                        th5 = th48;
                        networkTimeUpdateService = null;
                    }
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("CertBlacklister");
                new CertBlacklister(context);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartEmergencyAffordanceService");
                this.mSystemServiceManager.startService(EmergencyAffordanceService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("startBlobStoreManagerService");
                this.mSystemServiceManager.startService("com.android.server.blob.BlobStoreManagerService");
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartDreamManager");
                this.mSystemServiceManager.startService(DreamManagerService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("AddGraphicsStatsService");
                ServiceManager.addService("graphicsstats", new GraphicsStatsService(context));
                timingsTraceAndSlog.traceEnd();
                if (CoverageService.ENABLED) {
                    timingsTraceAndSlog.traceBegin("AddCoverageService");
                    ServiceManager.addService("coverage", new CoverageService());
                    timingsTraceAndSlog.traceEnd();
                }
                if (this.mPackageManager.hasSystemFeature("android.software.print")) {
                    timingsTraceAndSlog.traceBegin("StartPrintManager");
                    this.mSystemServiceManager.startService("com.android.server.print.PrintManagerService");
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("StartAttestationVerificationService");
                this.mSystemServiceManager.startService(AttestationVerificationManagerService.class);
                timingsTraceAndSlog.traceEnd();
                if (this.mPackageManager.hasSystemFeature("android.software.companion_device_setup")) {
                    timingsTraceAndSlog.traceBegin("StartCompanionDeviceManager");
                    this.mSystemServiceManager.startService("com.android.server.companion.CompanionDeviceManagerService");
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("StartVirtualDeviceManager");
                    this.mSystemServiceManager.startService("com.android.server.companion.virtual.VirtualDeviceManagerService");
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("StartRestrictionManager");
                this.mSystemServiceManager.startService(RestrictionsManagerService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("CocktailBarService");
                this.mSystemServiceManager.startService("com.android.server.cocktailbar.CocktailBarManagerService");
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("SemInputDeviceManagerService SystemReady");
                Slog.i("SystemServer", "SemInputDeviceManagerService SystemReady loader");
                Class<?> cls32222 = Class.forName("com.samsung.android.hardware.secinputdev.SemInputDeviceManagerLoader");
                cls32222.getDeclaredMethod("systemReady", new Class[0]).invoke(cls32222, new Object[0]);
                timingsTraceAndSlog.traceEnd();
                if (this.mFactoryTestMode != 1) {
                    SystemProperties.get("vold.decrypt");
                    if (this.mPackageManager.hasSystemFeature("com.sec.feature.cover")) {
                        timingsTraceAndSlog.traceBegin("StartCoverService");
                        this.mSystemServiceManager.startService("com.android.server.cover.CoverManagerService");
                        timingsTraceAndSlog.traceEnd();
                    }
                }
                timingsTraceAndSlog.traceBegin("StartAODManagerService");
                this.mSystemServiceManager.startService("com.android.server.aod.AODManagerService");
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("Samsung Accessory Manager");
                packageManager2 = context.getPackageManager();
                if (packageManager2 != null && (packageManager2.hasSystemFeature("com.sec.feature.nfc_authentication") || packageManager2.hasSystemFeature("com.sec.feature.nfc_authentication_cover") || packageManager2.hasSystemFeature("com.sec.feature.usb_authentication") || packageManager2.hasSystemFeature("com.sec.feature.wirelesscharger_authentication"))) {
                    Slog.i("SystemServer", "Samsung Accessory Manager");
                    SAccessoryManager sAccessoryManager = new SAccessoryManager(context, inputManagerService222);
                    this.sAccessoryManager = sAccessoryManager;
                    LocalServices.addService(SAccessoryManagerInternal.class, sAccessoryManager);
                    inputManagerService222.setSecAccessoryManagerCallbacks(this.sAccessoryManager);
                }
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("BattAuthManager");
                packageManager = context.getPackageManager();
                if (packageManager != null && packageManager.hasSystemFeature("com.sec.feature.wirelesscharger_authentication")) {
                    Slog.i("SystemServer", "BattAuthManager");
                    BattAuthManager battAuthManager = new BattAuthManager(context);
                    this.mBattAuthManager = battAuthManager;
                    LocalServices.addService(BattAuthManager.class, battAuthManager);
                }
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartMediaSessionService");
                this.mSystemServiceManager.startService("com.android.server.media.MediaSessionService");
                timingsTraceAndSlog.traceEnd();
                if (this.mPackageManager.hasSystemFeature("android.hardware.hdmi.cec")) {
                    timingsTraceAndSlog.traceBegin("StartHdmiControlService");
                    this.mSystemServiceManager.startService(HdmiControlService.class);
                    timingsTraceAndSlog.traceEnd();
                }
                if (!this.mPackageManager.hasSystemFeature("android.software.live_tv") || this.mPackageManager.hasSystemFeature("android.software.leanback")) {
                    timingsTraceAndSlog.traceBegin("StartTvInteractiveAppManager");
                    this.mSystemServiceManager.startService(TvInteractiveAppManagerService.class);
                    timingsTraceAndSlog.traceEnd();
                }
                if (!this.mPackageManager.hasSystemFeature("android.software.live_tv") || this.mPackageManager.hasSystemFeature("android.software.leanback")) {
                    timingsTraceAndSlog.traceBegin("StartTvInputManager");
                    this.mSystemServiceManager.startService(TvInputManagerService.class);
                    timingsTraceAndSlog.traceEnd();
                }
                if (this.mPackageManager.hasSystemFeature("android.hardware.tv.tuner")) {
                    timingsTraceAndSlog.traceBegin("StartTunerResourceManager");
                    this.mSystemServiceManager.startService(TunerResourceManagerService.class);
                    timingsTraceAndSlog.traceEnd();
                }
                if (this.mPackageManager.hasSystemFeature("android.software.picture_in_picture")) {
                    timingsTraceAndSlog.traceBegin("StartMediaResourceMonitor");
                    this.mSystemServiceManager.startService("com.android.server.media.MediaResourceMonitorService");
                    timingsTraceAndSlog.traceEnd();
                }
                if (this.mPackageManager.hasSystemFeature("android.software.leanback")) {
                    timingsTraceAndSlog.traceBegin("StartTvRemoteService");
                    this.mSystemServiceManager.startService(TvRemoteService.class);
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("StartMediaRouterService");
                IMediaRouterService.Stub mediaRouterService22222 = new MediaRouterService(context);
                ServiceManager.addService("media_router", mediaRouterService22222);
                MediaRouterService mediaRouterService32222 = mediaRouterService22222;
                timingsTraceAndSlog.traceEnd();
                hasSystemFeature = this.mPackageManager.hasSystemFeature("android.hardware.biometrics.face");
                hasSystemFeature2 = this.mPackageManager.hasSystemFeature("android.hardware.biometrics.iris");
                hasSystemFeature3 = this.mPackageManager.hasSystemFeature("android.hardware.fingerprint");
                if (hasSystemFeature) {
                    timingsTraceAndSlog.traceBegin("StartFaceSensor");
                    timingsTraceAndSlog.traceEnd();
                }
                if (hasSystemFeature2) {
                    timingsTraceAndSlog.traceBegin("StartIrisSensor");
                    this.mSystemServiceManager.startService(IrisService.class);
                    timingsTraceAndSlog.traceEnd();
                }
                if (hasSystemFeature3) {
                    timingsTraceAndSlog.traceBegin("StartFingerprintSensor");
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("StartBiometricService");
                this.mSystemServiceManager.startService(BiometricService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartAuthService");
                this.mSystemServiceManager.startService(AuthService.class);
                timingsTraceAndSlog.traceEnd();
                if (!hasSystemFeature4) {
                    timingsTraceAndSlog.traceBegin("StartDynamicCodeLoggingService");
                    try {
                        DynamicCodeLoggingService.schedule(context);
                    } catch (Throwable th49) {
                        reportWtf("starting DynamicCodeLoggingService", th49);
                    }
                    timingsTraceAndSlog.traceEnd();
                }
                if (!hasSystemFeature4) {
                    timingsTraceAndSlog.traceBegin("StartPruneInstantAppsJobService");
                    try {
                        PruneInstantAppsJobService.schedule(context);
                    } catch (Throwable th50) {
                        reportWtf("StartPruneInstantAppsJobService", th50);
                    }
                    timingsTraceAndSlog.traceEnd();
                }
                timingsTraceAndSlog.traceBegin("StartSmartThingsService");
                this.mSmartThingsService = new SmartThingsService(context);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartShortcutServiceLifecycle");
                this.mSystemServiceManager.startService(ShortcutService.Lifecycle.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartLauncherAppsService");
                this.mSystemServiceManager.startService(LauncherAppsService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartCrossProfileAppsService");
                this.mSystemServiceManager.startService(CrossProfileAppsService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartPeopleService");
                this.mSystemServiceManager.startService(PeopleService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartMediaMetricsManager");
                this.mSystemServiceManager.startService(MediaMetricsManagerService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartBackgroundInstallControlService");
                this.mSystemServiceManager.startService(BackgroundInstallControlService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartChimeraService");
                ServiceManager.addService("ChimeraManagerService", new ChimeraManagerService(context, this.mActivityManagerService));
                Slog.i("SystemServer", "ChimeraManagerService loaded");
                timingsTraceAndSlog.traceEnd();
                if (context.getPackageManager().hasSystemFeature("android.hardware.wifi")) {
                    timingsTraceAndSlog.traceBegin("StartOemWifi");
                    this.mSystemServiceManager.startService("com.samsung.android.server.wifi.SemWifiService");
                    timingsTraceAndSlog.traceEnd();
                }
                if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.direct")) {
                    timingsTraceAndSlog.traceBegin("StartOemWifiP2p");
                    this.mSystemServiceManager.startService("com.samsung.android.server.wifi.p2p.SemWifiP2pService");
                    timingsTraceAndSlog.traceEnd();
                }
                if (context.getPackageManager().hasSystemFeature("android.hardware.wifi.aware")) {
                    timingsTraceAndSlog.traceBegin("StartOemWifiAware");
                    this.mSystemServiceManager.startService("com.samsung.android.server.wifi.aware.SemWifiAwareService");
                    timingsTraceAndSlog.traceEnd();
                }
                iBinder = iBinder4222;
                networkPolicyManagerService = networkPolicyManagerService4222;
                mediaRouterService = mediaRouterService32222;
                iLockSettings3 = iLockSettings2;
                vcnManagementService = vcnManagementService2222;
                lifecycle2 = lifecycle;
                networkTimeUpdateService2 = networkTimeUpdateService;
            }
            final IBinder iBinder222222222 = null;
            timingsTraceAndSlog.traceBegin("StartMediaProjectionManager");
            this.mSystemServiceManager.startService(MediaProjectionManagerService.class);
            timingsTraceAndSlog.traceEnd();
            if (!this.mPackageManager.hasSystemFeature("att.devicehealth.support")) {
                timingsTraceAndSlog.traceBegin("SetupDeviceHealthSupport");
                try {
                    new IQIServiceBrokerExt(context).startIqi();
                } catch (Exception e11) {
                    Slog.w("SystemServer", "Unable to start com.samsung.iqi.IQIServiceBrokerExt", e11);
                }
                timingsTraceAndSlog.traceEnd();
            } else {
                Slog.i("SystemServer", "System feature for device health not found");
            }
            if (hasSystemFeature4) {
                timingsTraceAndSlog.traceBegin("StartWearPowerService");
                this.mSystemServiceManager.startService("com.android.clockwork.power.WearPowerService");
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartHealthService");
                this.mSystemServiceManager.startService("com.android.clockwork.healthservices.HealthService");
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartWearConnectivityService");
                this.mSystemServiceManager.startService("com.android.clockwork.connectivity.WearConnectivityService");
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartWearDisplayService");
                this.mSystemServiceManager.startService("com.android.clockwork.display.WearDisplayService");
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartWearTimeService");
                this.mSystemServiceManager.startService("com.android.clockwork.time.WearTimeService");
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartWearGlobalActionsService");
                this.mSystemServiceManager.startService("com.android.clockwork.globalactions.GlobalActionsService");
                timingsTraceAndSlog.traceEnd();
            }
            if (!this.mPackageManager.hasSystemFeature("android.software.slices_disabled")) {
                timingsTraceAndSlog.traceBegin("StartSliceManagerService");
                this.mSystemServiceManager.startService("com.android.server.slice.SliceManagerService$Lifecycle");
                timingsTraceAndSlog.traceEnd();
            }
            if (context.getPackageManager().hasSystemFeature("android.hardware.type.embedded")) {
                timingsTraceAndSlog.traceBegin("StartIoTSystemService");
                this.mSystemServiceManager.startService("com.android.things.server.IoTSystemService");
                timingsTraceAndSlog.traceEnd();
            }
            timingsTraceAndSlog.traceBegin("StartSpenGestureManagerService");
            ServiceManager.addService("spengestureservice", new SpenGestureManagerService(context, main));
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartSamsungNotesService");
            this.mSamsungNotesService = new SamsungNotesService(context);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartStatsCompanion");
            this.mSystemServiceManager.startServiceFromJar("com.android.server.stats.StatsCompanion$Lifecycle", "/apex/com.android.os.statsd/javalib/service-statsd.jar");
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartRebootReadinessManagerService");
            this.mSystemServiceManager.startServiceFromJar("com.android.server.scheduling.RebootReadinessManagerService$Lifecycle", "/apex/com.android.scheduling/javalib/service-scheduling.jar");
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartStatsPullAtomService");
            this.mSystemServiceManager.startService("com.android.server.stats.pull.StatsPullAtomService");
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StatsBootstrapAtomService");
            this.mSystemServiceManager.startService("com.android.server.stats.bootstrap.StatsBootstrapAtomService$Lifecycle");
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartIncidentCompanionService");
            this.mSystemServiceManager.startService(IncidentCompanionService.class);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StarSdkSandboxManagerService");
            this.mSystemServiceManager.startService("com.android.server.sdksandbox.SdkSandboxManagerService$Lifecycle");
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartAdServicesManagerService");
            startService = this.mSystemServiceManager.startService("com.android.server.adservices.AdServicesManagerService$Lifecycle");
            if (startService instanceof Dumpable) {
                this.mDumper.addDumpable((Dumpable) startService);
            }
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartOnDevicePersonalizationSystemService");
            this.mSystemServiceManager.startService("com.android.server.ondevicepersonalization.OnDevicePersonalizationSystemService$Lifecycle");
            timingsTraceAndSlog.traceEnd();
            if (detectSafeMode) {
                this.mActivityManagerService.enterSafeMode();
            }
            final boolean z522222222 = context.getResources().getBoolean(R.bool.config_restartRadioAfterProvisioning);
            if (!this.mPackageManager.hasSystemFeature("android.hardware.telephony") || z522222222) {
                timingsTraceAndSlog.traceBegin("StartMmsService");
                MmsServiceBroker mmsServiceBroker222222222 = (MmsServiceBroker) this.mSystemServiceManager.startService(MmsServiceBroker.class);
                timingsTraceAndSlog.traceEnd();
                mmsServiceBroker = mmsServiceBroker222222222;
            } else {
                mmsServiceBroker = null;
            }
            if (this.mPackageManager.hasSystemFeature("android.software.autofill")) {
                timingsTraceAndSlog.traceBegin("StartAutoFillService");
                this.mSystemServiceManager.startService("com.android.server.autofill.AutofillManagerService");
                timingsTraceAndSlog.traceEnd();
            }
            if (this.mPackageManager.hasSystemFeature("android.software.credentials")) {
                if (DeviceConfig.getBoolean("credential_manager", "enable_credential_manager", true)) {
                    timingsTraceAndSlog.traceBegin("StartCredentialManagerService");
                    this.mSystemServiceManager.startService("com.android.server.credentials.CredentialManagerService");
                    timingsTraceAndSlog.traceEnd();
                } else {
                    Slog.d("SystemServer", "CredentialManager disabled.");
                }
            }
            if (!deviceHasConfigString(context, R.string.default_audio_route_category_name)) {
                timingsTraceAndSlog.traceBegin("StartTranslationManagerService");
                this.mSystemServiceManager.startService("com.android.server.translation.TranslationManagerService");
                timingsTraceAndSlog.traceEnd();
            } else {
                Slog.d("SystemServer", "TranslationService not defined by OEM");
            }
            timingsTraceAndSlog.traceBegin("StartSelectionToolbarManagerService");
            this.mSystemServiceManager.startService("com.android.server.selectiontoolbar.SelectionToolbarManagerService");
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartClipboardService");
            this.mSystemServiceManager.startService(ClipboardService.class);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("AppServiceManager");
            this.mSystemServiceManager.startService(AppBindingService.Lifecycle.class);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("startTracingServiceProxy");
            this.mSystemServiceManager.startService(TracingServiceProxy.class);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("MakeLockSettingsServiceReady");
            if (iLockSettings3 != null) {
                try {
                    iLockSettings3.systemReady();
                } catch (Throwable th51) {
                    reportWtf("making Lock Settings Service ready", th51);
                }
            }
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartPersonaSystemready");
            if ("2.0".equals(SemPersonaManager.getKnoxInfo().getString("version"))) {
                try {
                    personaManagerService5222.systemReady();
                } catch (Throwable th52) {
                    reportWtf("making Persona Manager Service ready", th52);
                }
                try {
                    stub3222.systemReady();
                } catch (Throwable th53) {
                    reportWtf("making KnoxMUMContainerPolicy Service ready", th53);
                }
            }
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartBootPhaseLockSettingsReady");
            Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_LOCK_SETTINGS_READY");
            this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, SystemService.PHASE_LOCK_SETTINGS_READY);
            timingsTraceAndSlog.traceEnd();
            createInstance = HsumBootUserInitializer.createInstance(this.mActivityManagerService, this.mPackageManagerService, this.mContentResolver, context.getResources().getBoolean(R.bool.config_focusScrollContainersInTouchMode));
            if (createInstance != null) {
                timingsTraceAndSlog.traceBegin("HsumBootUserInitializer.init");
                createInstance.init(timingsTraceAndSlog);
            }
            timingsTraceAndSlog.traceBegin("StartBootPhaseSystemServicesReady");
            Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_SYSTEM_SERVICES_READY");
            this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 500);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("MakeWindowManagerServiceReady");
            main.systemReady();
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("RegisterLogMteState");
            LogMteState.register(context);
            timingsTraceAndSlog.traceEnd();
            synchronized (SystemService.class) {
                LinkedList linkedList = sPendingWtfs;
                if (linkedList != null) {
                    this.mActivityManagerService.schedulePendingSystemServerWtfs(linkedList);
                    sPendingWtfs = null;
                }
            }
            if (detectSafeMode) {
                this.mActivityManagerService.showSafeModeOverlay();
                this.mActivityManagerService.showRescuePartyDialog();
            }
            Configuration computeNewConfiguration = main.computeNewConfiguration(0);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            context.getDisplay().getMetrics(displayMetrics);
            context.getResources().updateConfiguration(computeNewConfiguration, displayMetrics);
            Resources.Theme theme = context.getTheme();
            if (theme.getChangingConfigurations() != 0) {
                theme.rebase();
            }
            timingsTraceAndSlog.traceBegin("DualAppManagerService");
            try {
                DualAppManagerService dualAppManagerService = this.mDualAppService;
                if (dualAppManagerService != null) {
                    dualAppManagerService.systemReady();
                }
            } catch (Throwable th54) {
                reportWtf("making DualAppManagerService ready", th54);
            }
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartPermissionPolicyService");
            this.mSystemServiceManager.startService(PermissionPolicyService.class);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("MakePackageManagerServiceReady");
            this.mPackageManagerService.systemReady();
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("MakeASKSManagerServiceReady");
            this.mASKSManagerService.systemReady();
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("MakeDisplayManagerServiceReady");
            try {
                this.mDisplayManagerService.systemReady(detectSafeMode);
            } catch (Throwable th55) {
                reportWtf("making Display Manager Service ready", th55);
            }
            timingsTraceAndSlog.traceEnd();
            this.mSystemServiceManager.setSafeMode(detectSafeMode);
            timingsTraceAndSlog.traceBegin("StartDeviceSpecificServices");
            String[] stringArray = this.mSystemContext.getResources().getStringArray(R.array.config_smallAreaDetectionAllowlist);
            int length = stringArray.length;
            int i3 = 0;
            while (i3 < length) {
                String str = stringArray[i3];
                StringBuilder sb2 = new StringBuilder();
                String[] strArr = stringArray;
                sb2.append("StartDeviceSpecificServices ");
                sb2.append(str);
                timingsTraceAndSlog.traceBegin(sb2.toString());
                try {
                    this.mSystemServiceManager.startService(str);
                    i = length;
                } catch (Throwable th56) {
                    StringBuilder sb3 = new StringBuilder();
                    i = length;
                    sb3.append("starting ");
                    sb3.append(str);
                    reportWtf(sb3.toString(), th56);
                }
                timingsTraceAndSlog.traceEnd();
                i3++;
                stringArray = strArr;
                length = i;
            }
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("GameManagerService");
            this.mSystemServiceManager.startService("com.android.server.app.GameManagerService$Lifecycle");
            timingsTraceAndSlog.traceEnd();
            context.getPackageManager().hasSystemFeature("android.hardware.uwb");
            timingsTraceAndSlog.traceBegin("StartBootPhaseDeviceSpecificServicesReady");
            Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_DEVICE_SPECIFIC_SERVICES_READY");
            this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, SystemService.PHASE_DEVICE_SPECIFIC_SERVICES_READY);
            timingsTraceAndSlog.traceEnd();
            if (MdfUtils.isMdfEnforced()) {
                timingsTraceAndSlog.traceBegin("MdfService");
                try {
                    ServiceManager.addService("MdfService", new MdfService(context));
                } catch (Throwable th57) {
                    Slog.e("SystemServer", "Failed to add MdfService", th57);
                }
                timingsTraceAndSlog.traceEnd();
            } else {
                Slog.i("SystemServer", "MdfService is ready");
            }
            timingsTraceAndSlog.traceBegin("StartSafetyCenterService");
            this.mSystemServiceManager.startService("com.android.safetycenter.SafetyCenterService");
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("AppSearchModule");
            this.mSystemServiceManager.startService("com.android.server.appsearch.AppSearchModule$Lifecycle");
            timingsTraceAndSlog.traceEnd();
            if (SystemProperties.getBoolean("ro.config.isolated_compilation_enabled", false)) {
                timingsTraceAndSlog.traceBegin("IsolatedCompilationService");
                this.mSystemServiceManager.startService("com.android.server.compos.IsolatedCompilationService");
                timingsTraceAndSlog.traceEnd();
            }
            timingsTraceAndSlog.traceBegin("StartMediaCommunicationService");
            this.mSystemServiceManager.startService("com.android.server.media.MediaCommunicationService");
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("AppCompatOverridesService");
            this.mSystemServiceManager.startService("com.android.server.compat.overrides.AppCompatOverridesService$Lifecycle");
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("HealthConnectManagerService");
            this.mSystemServiceManager.startService("com.android.server.healthconnect.HealthConnectManagerService");
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("SemContinuityService");
            this.mSystemServiceManager.startService(SemContinuityService.class);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("SemHwrsService");
            this.mSystemServiceManager.startService(SemHwrsService.class);
            timingsTraceAndSlog.traceEnd();
            final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            final SAccessoryManager sAccessoryManager2 = this.sAccessoryManager;
            final INetworkManagementService iNetworkManagementService3 = iNetworkManagementService;
            final VpnManagerService vpnManagerService3 = vpnManagerService;
            final TelephonyRegistry telephonyRegistry3 = telephonyRegistry2;
            final VcnManagementService vcnManagementService3 = vcnManagementService;
            final UrspService urspService3 = urspService;
            final IBinder iBinder5 = iBinder;
            final CountryDetectorService countryDetectorService4 = countryDetectorService;
            final NetworkTimeUpdateService networkTimeUpdateService3 = networkTimeUpdateService2;
            final MediaRouterService mediaRouterService4 = mediaRouterService;
            final MmsServiceBroker mmsServiceBroker3 = mmsServiceBroker;
            this.mActivityManagerService.systemReady(new Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    SystemServer.this.lambda$startOtherServices$7(timingsTraceAndSlog, lifecycle2, hasSystemFeature4, context, detectSafeMode, connectivityManager, iNetworkManagementService3, networkPolicyManagerService, vpnManagerService3, vcnManagementService3, urspService3, createInstance, iBinder5, iBinder222222222, sAccessoryManager2, countryDetectorService4, networkTimeUpdateService3, inputManagerService222, telephonyRegistry3, mediaRouterService4, z522222222, mmsServiceBroker3, equals2);
                }
            }, timingsTraceAndSlog);
            timingsTraceAndSlog.traceBegin("LockSettingsThirdPartyAppsStarted");
            LockSettingsInternal lockSettingsInternal = (LockSettingsInternal) LocalServices.getService(LockSettingsInternal.class);
            if (lockSettingsInternal != null) {
                lockSettingsInternal.onThirdPartyAppsStarted();
            }
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("StartSystemUI");
            Slog.i("SystemServer", "!@Boot_EBS_F: StartSystemUI");
            try {
                startSystemUi(context, main);
            } catch (Throwable th58) {
                reportWtf("starting System UI", th58);
            }
            timingsTraceAndSlog.traceEnd();
        } catch (Throwable th59) {
            Slog.e("System", "******************************************");
            Slog.e("System", "************ Failure starting core service");
            throw th59;
        }
    }

    public static /* synthetic */ void lambda$startOtherServices$1() {
        try {
            Slog.i("SystemServer", "SecondaryZygotePreload");
            TimingsTraceAndSlog newAsyncLog = TimingsTraceAndSlog.newAsyncLog();
            newAsyncLog.traceBegin("SecondaryZygotePreload");
            String[] strArr = Build.SUPPORTED_32_BIT_ABIS;
            if (strArr.length > 0 && !Process.ZYGOTE_PROCESS.preloadDefault(strArr[0])) {
                Slog.e("SystemServer", "Unable to preload default resources for secondary");
            }
            newAsyncLog.traceEnd();
        } catch (Exception e) {
            Slog.e("SystemServer", "Exception preloading default resources", e);
        }
    }

    public static /* synthetic */ void lambda$startOtherServices$2() {
        TimingsTraceAndSlog newAsyncLog = TimingsTraceAndSlog.newAsyncLog();
        newAsyncLog.traceBegin("StartISensorManagerService");
        startISensorManagerService();
        newAsyncLog.traceEnd();
    }

    public static /* synthetic */ void lambda$startOtherServices$3() {
        TimingsTraceAndSlog newAsyncLog = TimingsTraceAndSlog.newAsyncLog();
        newAsyncLog.traceBegin("StartHidlServices");
        startHidlServices();
        newAsyncLog.traceEnd();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startOtherServices$7(TimingsTraceAndSlog timingsTraceAndSlog, DevicePolicyManagerService.Lifecycle lifecycle, boolean z, Context context, boolean z2, ConnectivityManager connectivityManager, NetworkManagementService networkManagementService, NetworkPolicyManagerService networkPolicyManagerService, VpnManagerService vpnManagerService, VcnManagementService vcnManagementService, UrspService urspService, HsumBootUserInitializer hsumBootUserInitializer, IBinder iBinder, IBinder iBinder2, SAccessoryManager sAccessoryManager, CountryDetectorService countryDetectorService, NetworkTimeUpdateService networkTimeUpdateService, InputManagerService inputManagerService, TelephonyRegistry telephonyRegistry, MediaRouterService mediaRouterService, boolean z3, MmsServiceBroker mmsServiceBroker, final boolean z4) {
        Slog.i("SystemServer", "Making services ready");
        timingsTraceAndSlog.traceBegin("StartActivityManagerReadyPhase");
        Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_ACTIVITY_MANAGER_READY");
        this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, SystemService.PHASE_ACTIVITY_MANAGER_READY);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartObservingNativeCrashes");
        try {
            this.mActivityManagerService.startObservingNativeCrashes();
        } catch (Throwable th) {
            reportWtf("observing native crashes", th);
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("RegisterAppOpsPolicy");
        try {
            this.mActivityManagerService.setAppOpsPolicy(new AppOpsPolicy(this.mSystemContext));
        } catch (Throwable th2) {
            reportWtf("registering app ops policy", th2);
        }
        timingsTraceAndSlog.traceEnd();
        Future submit = this.mWebViewUpdateService != null ? SystemServerInitThreadPool.submit(new Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                SystemServer.this.lambda$startOtherServices$4();
            }
        }, "WebViewFactoryPreparation") : null;
        if (this.mPackageManager.hasSystemFeature("android.hardware.type.automotive")) {
            timingsTraceAndSlog.traceBegin("StartCarServiceHelperService");
            DevicePolicySafetyChecker startService = this.mSystemServiceManager.startService("com.android.internal.car.CarServiceHelperService");
            if (startService instanceof Dumpable) {
                this.mDumper.addDumpable((Dumpable) startService);
            }
            if (startService instanceof DevicePolicySafetyChecker) {
                lifecycle.setDevicePolicySafetyChecker(startService);
            }
            timingsTraceAndSlog.traceEnd();
        }
        if (z) {
            timingsTraceAndSlog.traceBegin("StartWearService");
            String string = context.getString(R.string.face_acquired_dark_glasses_detected);
            if (!TextUtils.isEmpty(string)) {
                ComponentName unflattenFromString = ComponentName.unflattenFromString(string);
                if (unflattenFromString != null) {
                    Intent intent = new Intent();
                    intent.setComponent(unflattenFromString);
                    intent.addFlags(256);
                    context.startServiceAsUser(intent, UserHandle.SYSTEM);
                } else {
                    Slog.d("SystemServer", "Null wear service component name.");
                }
            }
            timingsTraceAndSlog.traceEnd();
        }
        timingsTraceAndSlog.traceBegin("startResourceOverlayService");
        try {
            startResourceOverlayService(z2);
        } catch (Throwable th3) {
            reportWtf("starting Resource Overlay Service", th3);
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("startThemeService");
        try {
            startThemeService(z2);
        } catch (Throwable th4) {
            reportWtf("starting Theme Service", th4);
        }
        timingsTraceAndSlog.traceEnd();
        if (z2) {
            timingsTraceAndSlog.traceBegin("EnableAirplaneModeInSafeMode");
            try {
                connectivityManager.setAirplaneMode(true);
            } catch (Throwable th5) {
                reportWtf("enabling Airplane Mode during Safe Mode bootup", th5);
            }
            timingsTraceAndSlog.traceEnd();
        }
        timingsTraceAndSlog.traceBegin("MakeNetworkManagementServiceReady");
        if (networkManagementService != null) {
            try {
                Slog.i("SystemServer", "!@Boot_DEBUG: start networkManagement");
                Slog.i("SystemServer", "!@Boot_EBS_D: start networkManagement");
                networkManagementService.systemReady();
                Slog.i("SystemServer", "!@Boot_DEBUG: end networkManagement");
                Slog.i("SystemServer", "!@Boot_EBS_D: end networkManagement");
            } catch (Throwable th6) {
                reportWtf("making Network Managment Service ready", th6);
            }
        }
        CountDownLatch networkScoreAndNetworkManagementServiceReady = networkPolicyManagerService != null ? networkPolicyManagerService.networkScoreAndNetworkManagementServiceReady() : null;
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MakeConnectivityServiceReady");
        if (connectivityManager != null) {
            try {
                connectivityManager.systemReady();
            } catch (Throwable th7) {
                reportWtf("making Connectivity Service ready", th7);
            }
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MakeVpnManagerServiceReady");
        if (vpnManagerService != null) {
            try {
                vpnManagerService.systemReady();
            } catch (Throwable th8) {
                reportWtf("making VpnManagerService ready", th8);
            }
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MakeVcnManagementServiceReady");
        if (vcnManagementService != null) {
            try {
                vcnManagementService.systemReady();
            } catch (Throwable th9) {
                reportWtf("making VcnManagementService ready", th9);
            }
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MakeNetworkPolicyServiceReady");
        if (networkPolicyManagerService != null) {
            try {
                networkPolicyManagerService.systemReady(networkScoreAndNetworkManagementServiceReady);
            } catch (Throwable th10) {
                reportWtf("making Network Policy Service ready", th10);
            }
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MakeUrspServiceReady");
        if (urspService != null) {
            try {
                urspService.systemReady();
            } catch (Throwable th11) {
                reportWtf("making ursp Service ready", th11);
            }
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("WaitForAppDataPrepared");
        this.mPackageManagerService.waitForAppDataPrepared();
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("PhaseThirdPartyAppsCanStart");
        if (submit != null) {
            ConcurrentUtils.waitForFutureNoInterrupt(submit, "WebViewFactoryPreparation");
        }
        Slog.i("SystemServer", "!@Boot_EBS_D: PHASE_THIRD_PARTY_APPS_CAN_START");
        this.mSystemServiceManager.startBootPhase(timingsTraceAndSlog, 600);
        timingsTraceAndSlog.traceEnd();
        if (hsumBootUserInitializer != null) {
            timingsTraceAndSlog.traceBegin("HsumBootUserInitializer.systemRunning");
            hsumBootUserInitializer.systemRunning(timingsTraceAndSlog);
            timingsTraceAndSlog.traceEnd();
        }
        timingsTraceAndSlog.traceBegin("StartNetworkStack");
        try {
            NetworkStackClient.getInstance().start();
        } catch (Throwable th12) {
            reportWtf("starting Network Stack", th12);
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartTethering");
        try {
            ConnectivityModuleConnector.getInstance().startModuleService("android.net.ITetheringConnector", "android.permission.MAINLINE_NETWORK_STACK", new ConnectivityModuleConnector.ModuleServiceCallback() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda8
                @Override // android.net.ConnectivityModuleConnector.ModuleServiceCallback
                public final void onModuleServiceConnected(IBinder iBinder3) {
                    ServiceManager.addService("tethering", iBinder3, false, 6);
                }
            });
        } catch (Throwable th13) {
            reportWtf("starting Tethering", th13);
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("SLocationServiceReady");
        boolean z5 = false;
        if (iBinder != null) {
            try {
                Class.forName("com.samsung.android.location.SLocationLoader").getDeclaredMethod("systemReady", Context.class, IBinder.class).invoke(null, context, iBinder);
            } catch (Throwable th14) {
                reportWtf("making SLocation Service ready : ", th14);
            }
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("SAccessoryManager");
        if (sAccessoryManager != null) {
            try {
                sAccessoryManager.systemReady();
            } catch (Exception e) {
                reportWtf("Notifying SAccessoryManager running", e);
            }
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MakeCountryDetectionServiceReady");
        if (countryDetectorService != null) {
            try {
                countryDetectorService.systemRunning();
            } catch (Throwable th15) {
                reportWtf("Notifying CountryDetectorService running", th15);
            }
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MakeNetworkTimeUpdateReady");
        if (networkTimeUpdateService != null) {
            try {
                networkTimeUpdateService.systemRunning();
            } catch (Throwable th16) {
                reportWtf("Notifying NetworkTimeService running", th16);
            }
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MakeInputManagerServiceReady");
        if (inputManagerService != null) {
            try {
                inputManagerService.systemRunning();
            } catch (Throwable th17) {
                reportWtf("Notifying InputManagerService running", th17);
            }
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MakeTelephonyRegistryReady");
        if (telephonyRegistry != null) {
            try {
                telephonyRegistry.systemRunning();
            } catch (Throwable th18) {
                reportWtf("Notifying TelephonyRegistry running", th18);
            }
        }
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MakeMediaRouterServiceReady");
        if (mediaRouterService != null) {
            try {
                mediaRouterService.systemRunning();
            } catch (Throwable th19) {
                reportWtf("Notifying MediaRouterService running", th19);
            }
        }
        timingsTraceAndSlog.traceEnd();
        try {
            startEmergencyModeService(context);
        } catch (Exception e2) {
            reportWtf("Notifying EmergencyModeService running", e2);
        }
        if (this.mPackageManager.hasSystemFeature("android.hardware.telephony") || z3) {
            timingsTraceAndSlog.traceBegin("MakeMmsServiceReady");
            if (mmsServiceBroker != null) {
                try {
                    mmsServiceBroker.systemRunning();
                } catch (Throwable th20) {
                    reportWtf("Notifying MmsService running", th20);
                }
            }
            timingsTraceAndSlog.traceEnd();
        }
        timingsTraceAndSlog.traceBegin("IncidentDaemonReady");
        try {
            IIncidentManager asInterface = IIncidentManager.Stub.asInterface(ServiceManager.getService("incident"));
            if (asInterface != null) {
                asInterface.systemRunning();
            }
        } catch (Throwable th21) {
            reportWtf("Notifying incident daemon running", th21);
        }
        timingsTraceAndSlog.traceEnd();
        if (this.mIncrementalServiceHandle != 0) {
            timingsTraceAndSlog.traceBegin("MakeIncrementalServiceReady");
            setIncrementalServiceSystemReady(this.mIncrementalServiceHandle);
            timingsTraceAndSlog.traceEnd();
        }
        try {
            z5 = context.getResources().getBoolean(R.bool.config_dozeWakeLockScreenSensorAvailable);
        } catch (Exception e3) {
            Slog.e("SystemServer", "Not starting ExynosDisplaySolutionService", e3);
        }
        if (z5) {
            timingsTraceAndSlog.traceBegin("ExynosDisplaySolution");
            try {
                Slog.i("SystemServer", "ExynosDisplaySolution Service");
                ServiceManager.addService("exynos_display", new ExynosDisplaySolutionManagerService(context));
            } catch (Throwable th22) {
                reportWtf("Failed To Start ExynosDisplaySolution Service ", th22);
            }
            timingsTraceAndSlog.traceEnd();
        }
        Slog.i("SystemServer", "SemDisplayQualityFeature.ENABLED:" + SemDisplayQualityFeature.ENABLED + ",PLATFORM:" + SemDisplayQualityFeature.PLATFORM);
        if (SemDisplayQualityFeature.ENABLED) {
            timingsTraceAndSlog.traceBegin("SemDisplayQuality");
            try {
                Slog.i("SystemServer", "SemDisplayQuality Service");
                ServiceManager.addService("DisplayQuality", new SemDisplayQualityManagerService(context));
            } catch (Throwable th23) {
                reportWtf("Failed To Start SemDisplayQuality Service ", th23);
            }
            timingsTraceAndSlog.traceEnd();
        }
        timingsTraceAndSlog.traceBegin("OdsignStatsLogger");
        try {
            OdsignStatsLogger.triggerStatsWrite();
        } catch (Throwable th24) {
            reportWtf("Triggering OdsignStatsLogger", th24);
        }
        timingsTraceAndSlog.traceEnd();
        Future submit2 = SystemServerInitThreadPool.submit(new Runnable() { // from class: com.android.server.SystemServer$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                SystemServer.this.lambda$startOtherServices$6(z4);
            }
        }, "Load of Classes of Lazy Services");
        timingsTraceAndSlog.traceBegin("Mobile Payment Service");
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartSamsungHealthService");
        this.mSHealthService = new SamsungHealthService(context);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartVoiceNoteService");
        this.mVoiceNoteService = new VoiceNoteService(context);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("LazyService Wait Here");
        ConcurrentUtils.waitForFutureNoInterrupt(submit2, "Lazy Service");
        timingsTraceAndSlog.traceEnd();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startOtherServices$4() {
        Slog.i("SystemServer", "WebViewFactoryPreparation");
        TimingsTraceAndSlog newAsyncLog = TimingsTraceAndSlog.newAsyncLog();
        newAsyncLog.traceBegin("WebViewFactoryPreparation");
        ConcurrentUtils.waitForFutureNoInterrupt(this.mZygotePreload, "Zygote preload");
        this.mZygotePreload = null;
        this.mWebViewUpdateService.prepareWebViewInSystemServer();
        newAsyncLog.traceEnd();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startOtherServices$6(boolean z) {
        TimingsTraceAndSlog newAsyncLog = TimingsTraceAndSlog.newAsyncLog();
        newAsyncLog.traceBegin("Email Keystore Service");
        try {
            Slog.i("SystemServer", "Email Keystore Service");
            ServiceManager.addService("emailksproxy", new IServiceCreator() { // from class: com.android.server.SystemServer.6
                public IBinder createService(Context context) {
                    return new EmailKeystoreService(context);
                }
            });
        } catch (Throwable th) {
            Slog.e("SystemServer", "Failure starting Email Keystore Service", th);
        }
        newAsyncLog.traceEnd();
        newAsyncLog.traceBegin("SemAuthnrService");
        try {
            ServiceManager.addService("SemAuthnrService", new IServiceCreator() { // from class: com.android.server.SystemServer.7
                public IBinder createService(Context context) {
                    Slog.e("SystemServer", "before SemAuthnrService adding");
                    return new SemAuthnrService(context);
                }
            });
        } catch (Throwable unused) {
            Slog.e("SystemServer", "Failed to add SemAuthnrService.");
        }
        newAsyncLog.traceEnd();
        startRCPService(null, newAsyncLog, true);
        newAsyncLog.traceBegin("VideoTranscodingService");
        try {
            ServiceManager.addService("SemVideoTranscodingService", VideoTranscodingService.class);
        } catch (Throwable th2) {
            Slog.e("SystemServer", "Failed to start VideoTranscodingService ", th2);
        }
        newAsyncLog.traceEnd();
        if (z) {
            return;
        }
        newAsyncLog.traceBegin("DsmsService");
        try {
            ServiceManager.addService("dsms", DsmsService.class);
        } catch (Throwable th3) {
            reportWtf("DsmsService", th3);
        }
        newAsyncLog.traceEnd();
    }

    public static final void startEmergencyModeService(Context context) {
        try {
            SemEmergencyManager semEmergencyManager = SemEmergencyManager.getInstance(context);
            if (semEmergencyManager != null) {
                semEmergencyManager.readyEmergencyMode();
            } else {
                Slog.d("SystemServer", "Starting emergency service failed: emMgr is null");
            }
        } catch (Exception e) {
            Slog.d("SystemServer", "Starting emergency service failed: " + e);
        }
    }

    public final void startApexServices(TimingsTraceAndSlog timingsTraceAndSlog) {
        timingsTraceAndSlog.traceBegin("startApexServices");
        for (ApexSystemServiceInfo apexSystemServiceInfo : ApexManager.getInstance().getApexSystemServices()) {
            String name = apexSystemServiceInfo.getName();
            String jarPath = apexSystemServiceInfo.getJarPath();
            timingsTraceAndSlog.traceBegin("starting " + name);
            if (TextUtils.isEmpty(jarPath)) {
                this.mSystemServiceManager.startService(name);
            } else {
                this.mSystemServiceManager.startServiceFromJar(name, jarPath);
            }
            timingsTraceAndSlog.traceEnd();
        }
        this.mSystemServiceManager.sealStartedServices();
        timingsTraceAndSlog.traceEnd();
    }

    public final void updateWatchdogTimeout(TimingsTraceAndSlog timingsTraceAndSlog) {
        timingsTraceAndSlog.traceBegin("UpdateWatchdogTimeout");
        Watchdog.getInstance().registerSettingsObserver(this.mSystemContext);
        timingsTraceAndSlog.traceEnd();
    }

    public final boolean deviceHasConfigString(Context context, int i) {
        return !TextUtils.isEmpty(context.getString(i));
    }

    public final void startSystemCaptionsManagerService(Context context, TimingsTraceAndSlog timingsTraceAndSlog) {
        if (!deviceHasConfigString(context, R.string.decline)) {
            Slog.d("SystemServer", "SystemCaptionsManagerService disabled because resource is not overlaid");
            return;
        }
        timingsTraceAndSlog.traceBegin("StartSystemCaptionsManagerService");
        this.mSystemServiceManager.startService("com.android.server.systemcaptions.SystemCaptionsManagerService");
        timingsTraceAndSlog.traceEnd();
    }

    public final void startTextToSpeechManagerService(Context context, TimingsTraceAndSlog timingsTraceAndSlog) {
        timingsTraceAndSlog.traceBegin("StartTextToSpeechManagerService");
        this.mSystemServiceManager.startService("com.android.server.texttospeech.TextToSpeechManagerService");
        timingsTraceAndSlog.traceEnd();
    }

    public final void startContentCaptureService(Context context, TimingsTraceAndSlog timingsTraceAndSlog) {
        boolean z;
        ActivityManagerService activityManagerService;
        String property = DeviceConfig.getProperty("content_capture", "service_explicitly_enabled");
        if (property == null || property.equalsIgnoreCase("default")) {
            z = false;
        } else {
            z = Boolean.parseBoolean(property);
            if (z) {
                Slog.d("SystemServer", "ContentCaptureService explicitly enabled by DeviceConfig");
            } else {
                Slog.d("SystemServer", "ContentCaptureService explicitly disabled by DeviceConfig");
                return;
            }
        }
        if (!z && !deviceHasConfigString(context, R.string.data_usage_restricted_body)) {
            Slog.d("SystemServer", "ContentCaptureService disabled because resource is not overlaid");
            return;
        }
        timingsTraceAndSlog.traceBegin("StartContentCaptureService");
        this.mSystemServiceManager.startService("com.android.server.contentcapture.ContentCaptureManagerService");
        ContentCaptureManagerInternal contentCaptureManagerInternal = (ContentCaptureManagerInternal) LocalServices.getService(ContentCaptureManagerInternal.class);
        if (contentCaptureManagerInternal != null && (activityManagerService = this.mActivityManagerService) != null) {
            activityManagerService.setContentCaptureManager(contentCaptureManagerInternal);
        }
        timingsTraceAndSlog.traceEnd();
    }

    public final void startAttentionService(Context context, TimingsTraceAndSlog timingsTraceAndSlog) {
        if (!AttentionManagerService.isServiceConfigured(context)) {
            Slog.d("SystemServer", "AttentionService is not configured on this device");
            return;
        }
        timingsTraceAndSlog.traceBegin("StartAttentionManagerService");
        this.mSystemServiceManager.startService(AttentionManagerService.class);
        timingsTraceAndSlog.traceEnd();
    }

    public final void startRotationResolverService(Context context, TimingsTraceAndSlog timingsTraceAndSlog) {
        if (!RotationResolverManagerService.isServiceConfigured(context)) {
            Slog.d("SystemServer", "RotationResolverService is not configured on this device");
            return;
        }
        timingsTraceAndSlog.traceBegin("StartRotationResolverService");
        this.mSystemServiceManager.startService(RotationResolverManagerService.class);
        timingsTraceAndSlog.traceEnd();
    }

    public final void startAmbientContextService(TimingsTraceAndSlog timingsTraceAndSlog) {
        timingsTraceAndSlog.traceBegin("StartAmbientContextService");
        this.mSystemServiceManager.startService(AmbientContextManagerService.class);
        timingsTraceAndSlog.traceEnd();
    }

    public final void startWearableSensingService(TimingsTraceAndSlog timingsTraceAndSlog) {
        timingsTraceAndSlog.traceBegin("startWearableSensingService");
        this.mSystemServiceManager.startService(WearableSensingManagerService.class);
        timingsTraceAndSlog.traceEnd();
    }

    public static void startSystemUi(Context context, WindowManagerService windowManagerService) {
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        Intent intent = new Intent();
        intent.setComponent(packageManagerInternal.getSystemUiServiceComponent());
        intent.addFlags(256);
        context.startServiceAsUser(intent, UserHandle.SYSTEM);
        windowManagerService.onSystemUiStarted();
    }

    public static boolean handleEarlySystemWtf(IBinder iBinder, String str, boolean z, ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo, int i) {
        int myPid = Process.myPid();
        com.android.server.am.EventLogTags.writeAmWtf(UserHandle.getUserId(1000), myPid, "system_server", -1, str, parcelableCrashInfo.exceptionMessage);
        FrameworkStatsLog.write(80, 1000, str, "system_server", myPid, 3);
        synchronized (SystemServer.class) {
            if (sPendingWtfs == null) {
                sPendingWtfs = new LinkedList();
            }
            sPendingWtfs.add(new Pair(str, parcelableCrashInfo));
        }
        return false;
    }

    /* JADX WARN: Type inference failed for: r3v2, types: [android.os.IBinder, com.android.server.DualAppManagerService] */
    public final void startDualAppManagerService(Context context, TimingsTraceAndSlog timingsTraceAndSlog) {
        try {
            timingsTraceAndSlog.traceBegin("DualAppManagerService");
            Slog.d("SystemServer", "startDualAppManagerService | add Service : startDualAppManagerService");
        } finally {
            try {
            } finally {
            }
        }
        if (context == null) {
            Slog.d("SystemServer", "startDualAppManagerService | context is null");
            return;
        }
        if (this.mDualAppService == null) {
            ?? dualAppManagerService = DualAppManagerService.getInstance(context);
            this.mDualAppService = dualAppManagerService;
            ServiceManager.addService("dual_app", (IBinder) dualAppManagerService);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00ae A[Catch: all -> 0x00be, TRY_LEAVE, TryCatch #3 {all -> 0x00be, blocks: (B:26:0x008d, B:30:0x009a, B:33:0x00a6, B:35:0x00ae, B:38:0x00b5), top: B:25:0x008d }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00b5 A[Catch: all -> 0x00be, TRY_ENTER, TRY_LEAVE, TryCatch #3 {all -> 0x00be, blocks: (B:26:0x008d, B:30:0x009a, B:33:0x00a6, B:35:0x00ae, B:38:0x00b5), top: B:25:0x008d }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startRCPService(Context context, TimingsTraceAndSlog timingsTraceAndSlog, boolean z) {
        String str = SystemProperties.get("persist.sys.knox.userinfo");
        boolean z2 = str != null && str.length() > 0;
        String str2 = SystemProperties.get("persist.sys.knox.device_owner");
        boolean z3 = str2 != null && str2.equals("true");
        Slog.d("SystemServer", "startRCPService | KnoxPresentInDevice : " + z2 + ", DoEnabled : " + z3);
        try {
            timingsTraceAndSlog.traceBegin("RCPManagerService");
            if (!z && (z2 || z3)) {
                Slog.d("SystemServer", "startRCPService | add Service : RCPManagerService , rcp");
                if (context == null) {
                    Slog.d("SystemServer", "startRCPService | context is null");
                    return;
                }
                ServiceManager.addService("rcp", new RCPManagerService(context));
            } else if (z && !z2 && !z3) {
                Slog.d("SystemServer", "startRCPService | add Lazy Service : RCPManagerService , rcp");
                ServiceManager.addService("rcp", RCPManagerService.class);
            }
        } finally {
            try {
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("KnoxMUMRCPPolicyService");
                if (!z2) {
                }
                Slog.d("SystemServer", "startRCPService | add Service : KnoxMUMRCPPolicyService , mumrcppolicy");
                if (context != null) {
                }
            } finally {
            }
        }
        timingsTraceAndSlog.traceEnd();
        try {
            timingsTraceAndSlog.traceBegin("KnoxMUMRCPPolicyService");
            if (!z2 || z3) {
                Slog.d("SystemServer", "startRCPService | add Service : KnoxMUMRCPPolicyService , mumrcppolicy");
                if (context != null) {
                    Slog.d("SystemServer", "startRCPService | context is null");
                    return;
                }
                ServiceManager.addService("mum_container_rcp_policy", new KnoxMUMRCPPolicyService(context));
            } else {
                Slog.d("SystemServer", "startRCPService | add Lazy Service : KnoxMUMRCPPolicyService , mumrcppolicy");
                ServiceManager.addService("mum_container_rcp_policy", KnoxMUMRCPPolicyService.class);
            }
        } finally {
            try {
            } finally {
            }
        }
    }

    public final void startThemeService(boolean z) {
        Context context = this.mSystemContext;
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.samsung.android.themecenter", "com.samsung.android.thememanager.ThemeManagerService"));
        intent.putExtra("safeMode", z);
        intent.putExtra("isStartedBySystemServer", true);
        context.startServiceAsUser(intent, UserHandle.OWNER);
    }

    public final void startResourceOverlayService(boolean z) {
        try {
            LocaleOverlayManagerWrapper.getInstance(this.mSystemContext).initializeOverlays(z);
        } catch (Exception e) {
            Slog.e("SystemServer", "Error while starting LOM: " + e);
        }
    }
}
