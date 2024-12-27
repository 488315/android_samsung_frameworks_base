package com.android.server;

import android.R;
import android.app.ActivityThread;
import android.app.AppCompatCallbacks;
import android.app.usage.UsageStatsManagerInternal;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.hardware.display.DisplayManagerInternal;
import android.os.ArtModuleServiceManager;
import android.os.Binder;
import android.os.Build;
import android.os.Debug;
import android.os.FactoryTest;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.IServiceCreator;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.Process;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Dumpable;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.TimeUtils;
import com.android.internal.app.IAppOpsActiveCallback;
import com.android.internal.app.IAppOpsCallback;
import com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags.Flags;
import com.android.internal.policy.AttributeCache;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.Preconditions;
import com.android.server.BinderCallsStatsService;
import com.android.server.IcccManagerService;
import com.android.server.LooperStatsService;
import com.android.server.RescueParty;
import com.android.server.VaultKeeperService;
import com.android.server.Watchdog.RebootRequestReceiver;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.AppProfiler;
import com.android.server.am.AppProfiler.CpuBinder;
import com.android.server.am.BatteryStatsService;
import com.android.server.am.BatteryStatsService.WakeupReasonThread;
import com.android.server.am.HostingRecord;
import com.android.server.am.ProcessRecord;
import com.android.server.appop.AppOpMigrationHelperImpl;
import com.android.server.art.ArtModuleServiceInitializer;
import com.android.server.art.DexUseManagerLocal;
import com.android.server.asks.ASKSManagerService;
import com.android.server.attention.AttentionManagerService;
import com.android.server.blockchain.BlockchainTZService;
import com.android.server.clipboard.ClipboardService;
import com.android.server.compat.PlatformCompat;
import com.android.server.compat.PlatformCompatNative;
import com.android.server.contentcapture.ContentCaptureManagerService;
import com.android.server.cpu.CpuMonitorService;
import com.android.server.display.DisplayManagerService;
import com.android.server.emailksproxy.EmailKeystoreService;
import com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl;
import com.android.server.enterprise.container.KnoxMUMRCPPolicyService;
import com.android.server.enterprise.mpos.MPOSService;
import com.android.server.flags.FeatureFlagsService;
import com.android.server.gpu.GpuService;
import com.android.server.isrb.IsrbManagerService;
import com.android.server.knox.dar.DarManagerService;
import com.android.server.lights.LightsService;
import com.android.server.om.OverlayManagerService;
import com.android.server.ondeviceintelligence.OnDeviceIntelligenceManagerService;
import com.android.server.os.BugreportManagerService;
import com.android.server.os.DeviceIdentifiersPolicyService;
import com.android.server.os.NativeTombstoneManagerService;
import com.android.server.pdp.PdpService;
import com.android.server.permission.access.AccessCheckingService;
import com.android.server.pm.ApexManager;
import com.android.server.pm.ApexSystemServiceInfo;
import com.android.server.pm.Installer;
import com.android.server.pm.OtaDexoptService;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.permission.PermissionMigrationHelperImpl;
import com.android.server.pm.verify.domain.DomainVerificationService;
import com.android.server.power.ShutdownThread;
import com.android.server.power.ThermalManagerService;
import com.android.server.power.stats.BatteryStatsImpl;
import com.android.server.powerstats.PowerStatsService;
import com.android.server.recoverysystem.RecoverySystemService;
import com.android.server.resources.ResourcesManagerService;
import com.android.server.rollback.RollbackManagerService;
import com.android.server.rotationresolver.RotationResolverManagerService;
import com.android.server.security.FileIntegrityService;
import com.android.server.security.rkp.RemoteProvisioningService;
import com.android.server.sensorprivacy.SensorPrivacyService;
import com.android.server.sensors.SensorService;
import com.android.server.systemcaptions.SystemCaptionsManagerService;
import com.android.server.texttospeech.TextToSpeechManagerService;
import com.android.server.uri.UriGrantsManagerService;
import com.android.server.usage.UsageStatsService;
import com.android.server.utils.TimingsTraceAndSlog;
import com.android.server.wearable.WearableSensingManagerService;
import com.android.server.webkit.WebViewUpdateService;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.ActivityTaskSupervisor;
import com.android.server.wm.SurfaceAnimationThread;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerService;
import com.samsung.accessory.manager.SAccessoryManager;
import com.samsung.android.authnrservice.service.SemAuthnrService;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.rune.CoreRune;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.Future;

public final class SystemServer implements Dumpable {
    public static final File HEAP_DUMP_PATH = new File("/data/system/heapdump/");
    public static LinkedList sPendingWtfs;
    public ASKSManagerService mASKSManagerService;
    public ActivityManagerService mActivityManagerService;
    public ContentResolver mContentResolver;
    public DisplayManagerService mDisplayManagerService;
    public boolean mFirstBoot;
    public PackageManager mPackageManager;
    public PackageManagerService mPackageManagerService;
    public final boolean mRuntimeRestart;
    public final long mRuntimeStartElapsedTime;
    public final long mRuntimeStartUptime;
    public final int mStartCount;
    public Context mSystemContext;
    public SystemServiceManager mSystemServiceManager;
    public WebViewUpdateService mWebViewUpdateService;
    public Future mZygotePreload;
    public EnterpriseDeviceManagerServiceImpl enterprisePolicy = null;
    public KnoxCustomManagerService knoxCustomPolicy = null;
    public long mIncrementalServiceHandle = 0;
    public DualAppManagerService mDualAppService = null;
    public SAccessoryManager sAccessoryManager = null;
    public final SystemServerDumper mDumper = new SystemServerDumper();
    public final int mFactoryTestMode = FactoryTest.getMode();

    /* renamed from: com.android.server.SystemServer$4, reason: invalid class name */
    public final class AnonymousClass4 implements IServiceCreator {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ AnonymousClass4(int i) {
            this.$r8$classId = i;
        }

        public final IBinder createService(Context context) {
            switch (this.$r8$classId) {
                case 0:
                    ?? icccManagerService = new IcccManagerService();
                    IcccManagerService.AnonymousClass1 anonymousClass1 = new BroadcastReceiver() { // from class: com.android.server.IcccManagerService.1
                        public AnonymousClass1() {
                        }

                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context2, Intent intent) {
                            String action = intent.getAction();
                            Context context3 = IcccManagerService.mContext;
                            Log.d("IcccManagerService", "receive intent action is " + action);
                            if ("com.samsung.intent.action.BCS_REQUEST".equals(action)) {
                                try {
                                    if (intent.getExtras() == null) {
                                        Log.d("IcccManagerService", "data is null ");
                                    } else if (intent.hasExtra("command")) {
                                        String stringExtra = intent.getStringExtra("command");
                                        Log.d("IcccManagerService", "command: " + stringExtra);
                                        if ("AT+ICCCINFO=1,0,0".equalsIgnoreCase(stringExtra)) {
                                            Intent intent2 = new Intent("com.samsung.intent.action.BCS_RESPONSE");
                                            String m66$$Nest$mget_iccc_response_data = IcccManagerService.m66$$Nest$mget_iccc_response_data(IcccManagerService.this);
                                            Log.d("IcccManagerService", "iccc_response - " + m66$$Nest$mget_iccc_response_data);
                                            intent2.putExtra("response", m66$$Nest$mget_iccc_response_data);
                                            IcccManagerService.mContext.sendBroadcast(intent2, "com.sec.android.phone.permission.AT_COMMAND");
                                        }
                                    }
                                } catch (RuntimeException e) {
                                    Context context4 = IcccManagerService.mContext;
                                    Log.d("IcccManagerService", "onReceive:ACTION_BCS_REQUEST-exception");
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                    Log.d("IcccManagerService", "Start IcccManagerService");
                    IcccManagerService.mContext = context;
                    IcccManagerService.mContext.registerReceiver(anonymousClass1, BatteryService$$ExternalSyntheticOutline0.m("com.samsung.intent.action.BCS_REQUEST"), 2);
                    return icccManagerService;
                case 1:
                    ?? emailKeystoreService = new EmailKeystoreService();
                    emailKeystoreService.mContext = context;
                    return emailKeystoreService;
                case 2:
                    return new KnoxMUMRCPPolicyService(context);
                case 3:
                    return new DarManagerService(new DarManagerService.Injector(context));
                case 4:
                    ?? blockchainTZService = new BlockchainTZService();
                    blockchainTZService.mRegisteredFWKClientMap = new HashMap();
                    if (BlockchainTZService.DEBUG) {
                        Log.d("BlockchainTZService", "BlockchainTZService() called");
                    }
                    BlockchainTZService.mContext = context;
                    return blockchainTZService;
                case 5:
                    Slog.e("SystemServer", "before SemAuthnrService adding");
                    ?? semAuthnrService = new SemAuthnrService();
                    semAuthnrService.mContext = context;
                    return semAuthnrService;
                default:
                    return new MPOSService(context);
            }
        }
    }

    public final class SystemServerDumper extends Binder {
        public final ArrayMap mDumpables = new ArrayMap(4);

        /* renamed from: -$$Nest$maddDumpable, reason: not valid java name */
        public static void m95$$Nest$maddDumpable(SystemServerDumper systemServerDumper, Dumpable dumpable) {
            synchronized (systemServerDumper.mDumpables) {
                systemServerDumper.mDumpables.put(dumpable.getDumpableName(), dumpable);
            }
        }

        @Override // android.os.Binder
        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            IndentingPrintWriter indentingPrintWriter;
            int i = 0;
            boolean z = strArr != null && strArr.length > 0;
            synchronized (this.mDumpables) {
                if (z) {
                    try {
                        if ("--list".equals(strArr[0])) {
                            int size = this.mDumpables.size();
                            while (i < size) {
                                printWriter.println((String) this.mDumpables.keyAt(i));
                                i++;
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
                while (i < size2) {
                    try {
                        Dumpable dumpable2 = (Dumpable) this.mDumpables.valueAt(i);
                        indentingPrintWriter.printf("%s:\n", new Object[]{dumpable2.getDumpableName()});
                        indentingPrintWriter.increaseIndent();
                        dumpable2.dump(indentingPrintWriter, strArr);
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println();
                        i++;
                    } finally {
                    }
                }
                indentingPrintWriter.close();
                return;
                throw th;
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$0ek3wX68xKbgZMUwZfiBRkUNTFs() {
        TimingsTraceAndSlog newAsyncLog = TimingsTraceAndSlog.newAsyncLog();
        newAsyncLog.traceBegin("StartHidlServices");
        startHidlServices();
        newAsyncLog.traceEnd();
    }

    public static void $r8$lambda$2PdG6KuU0ZTvilD515PGrttj0sk(int i, int i2, int i3) {
        long j = 0;
        boolean z = false;
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

    /* renamed from: $r8$lambda$CJLFlg8wnqihjN12r-2Qq_1qSd8, reason: not valid java name */
    public static /* synthetic */ void m94$r8$lambda$CJLFlg8wnqihjN12r2Qq_1qSd8() {
        TimingsTraceAndSlog newAsyncLog = TimingsTraceAndSlog.newAsyncLog();
        newAsyncLog.traceBegin("StartISensorManagerService");
        startISensorManagerService();
        newAsyncLog.traceEnd();
    }

    /* JADX WARN: Code restructure failed: missing block: B:96:0x036c, code lost:
    
        com.android.server.utils.Slogf.wtf(r8, "Failed to switch to boot user since there isn't one.");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void $r8$lambda$Djmghhk0H4gEPRWdl1V39kc2N1M(final com.android.server.SystemServer r19, com.android.server.utils.TimingsTraceAndSlog r20, com.android.server.devicepolicy.DevicePolicyManagerService.Lifecycle r21, boolean r22, android.content.Context r23, boolean r24, android.net.ConnectivityManager r25, com.android.server.net.NetworkManagementService r26, final com.android.server.net.NetworkPolicyManagerService r27, com.android.server.VpnManagerService r28, com.android.server.VcnManagementService r29, com.android.server.net.UrspService r30, com.android.server.HsumBootUserInitializer r31, android.os.IBinder r32, com.samsung.accessory.manager.SAccessoryManager r33, android.os.IBinder r34, final com.android.server.CountryDetectorService r35, com.android.server.timedetector.NetworkTimeUpdateService r36, com.android.server.input.InputManagerService r37, com.android.server.TelephonyRegistry r38, final com.android.server.media.MediaRouterService r39, com.android.server.MmsServiceBroker r40, final boolean r41) {
        /*
            Method dump skipped, instructions count: 1659
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.SystemServer.$r8$lambda$Djmghhk0H4gEPRWdl1V39kc2N1M(com.android.server.SystemServer, com.android.server.utils.TimingsTraceAndSlog, com.android.server.devicepolicy.DevicePolicyManagerService$Lifecycle, boolean, android.content.Context, boolean, android.net.ConnectivityManager, com.android.server.net.NetworkManagementService, com.android.server.net.NetworkPolicyManagerService, com.android.server.VpnManagerService, com.android.server.VcnManagementService, com.android.server.net.UrspService, com.android.server.HsumBootUserInitializer, android.os.IBinder, com.samsung.accessory.manager.SAccessoryManager, android.os.IBinder, com.android.server.CountryDetectorService, com.android.server.timedetector.NetworkTimeUpdateService, com.android.server.input.InputManagerService, com.android.server.TelephonyRegistry, com.android.server.media.MediaRouterService, com.android.server.MmsServiceBroker, boolean):void");
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

    public static boolean deviceHasConfigString(Context context, int i) {
        return !TextUtils.isEmpty(context.getString(i));
    }

    private static native void fdtrackAbort();

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
            } catch (Throwable th) {
                if (fileDescriptor != null) {
                    try {
                        Os.close(fileDescriptor);
                    } catch (ErrnoException e2) {
                        throw new RuntimeException(e2);
                    }
                }
                throw th;
            }
        } catch (ErrnoException e3) {
            Slog.e("System", "Failed to get maximum fd: " + e3);
            if (fileDescriptor == null) {
                return Integer.MAX_VALUE;
            }
            try {
                Os.close(fileDescriptor);
                return Integer.MAX_VALUE;
            } catch (ErrnoException e4) {
                throw new RuntimeException(e4);
            }
        }
    }

    private static native void initZygoteChildHeapProfiling();

    /* JADX WARN: Code restructure failed: missing block: B:63:0x0301, code lost:
    
        if (r0 != null) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0303, code lost:
    
        android.os.SystemProperties.set("persist.sys.is_usertrial", "false");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void main(java.lang.String[] r13) {
        /*
            Method dump skipped, instructions count: 833
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.SystemServer.main(java.lang.String[]):void");
    }

    public static void performPendingShutdown() {
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
                if (str2 != null && str2.startsWith("/data") && !BatteryService$$ExternalSyntheticOutline0.m45m("/cache/recovery/block.map")) {
                    Slog.e("SystemServer", "Can't find block map file, uncrypt failed or unexpected runtime restart?");
                    return;
                }
            }
        }
        Message obtain = Message.obtain(UiThread.getHandler(), new Runnable() { // from class: com.android.server.SystemServer.3
            @Override // java.lang.Runnable
            public final void run() {
                ShutdownThread.rebootOrShutdown(null, substring, z);
            }
        });
        obtain.setAsynchronous(true);
        UiThread.getHandler().sendMessage(obtain);
    }

    public static void reportWtf(String str, Throwable th) {
        Slog.w("SystemServer", "***********************************************");
        Slog.wtf("SystemServer", "BOOT FAILURE " + str, th);
    }

    private static native void setIncrementalServiceSystemReady(long j);

    private static native void startHidlServices();

    private static native void startISensorManagerService();

    private static native void startIStatsService();

    private static native long startIncrementalService();

    private static native void startMemtrackProxyService();

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void startRCPService(android.content.Context r7, com.android.server.utils.TimingsTraceAndSlog r8, boolean r9) {
        /*
            java.lang.String r0 = "startRCPService | Fail to start service"
            java.lang.String r1 = "startRCPService | context is null"
            java.lang.String r2 = "persist.sys.knox.userinfo"
            java.lang.String r2 = android.os.SystemProperties.get(r2)
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L19
            int r2 = r2.length()
            if (r2 <= 0) goto L19
            r2 = r4
            goto L1a
        L19:
            r2 = r3
        L1a:
            java.lang.String r5 = "persist.sys.knox.device_owner"
            java.lang.String r5 = android.os.SystemProperties.get(r5)
            if (r5 == 0) goto L2d
            java.lang.String r6 = "true"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L2d
            r3 = r4
        L2d:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "startRCPService | KnoxPresentInDevice : "
            r4.<init>(r5)
            r4.append(r2)
            java.lang.String r5 = ", DoEnabled : "
            r4.append(r5)
            r4.append(r3)
            java.lang.String r4 = r4.toString()
            java.lang.String r5 = "SystemServer"
            android.util.Slog.d(r5, r4)
            java.lang.String r4 = "RCPManagerService"
            r8.traceBegin(r4)     // Catch: java.lang.Throwable -> L66
            java.lang.String r4 = "rcp"
            if (r9 != 0) goto L71
            if (r2 != 0) goto L57
            if (r3 == 0) goto L71
        L57:
            java.lang.String r9 = "startRCPService | add Service : RCPManagerService , rcp"
            android.util.Slog.d(r5, r9)     // Catch: java.lang.Throwable -> L66
            if (r7 != 0) goto L68
            android.util.Slog.d(r5, r1)     // Catch: java.lang.Throwable -> L66
            r8.traceEnd()
            return
        L66:
            r9 = move-exception
            goto L86
        L68:
            com.android.server.RCPManagerService r9 = new com.android.server.RCPManagerService     // Catch: java.lang.Throwable -> L66
            r9.<init>(r7)     // Catch: java.lang.Throwable -> L66
            android.os.ServiceManager.addService(r4, r9)     // Catch: java.lang.Throwable -> L66
            goto L82
        L71:
            if (r9 == 0) goto L82
            if (r2 != 0) goto L82
            if (r3 != 0) goto L82
            java.lang.String r9 = "startRCPService | add Lazy Service : RCPManagerService , rcp"
            android.util.Slog.d(r5, r9)     // Catch: java.lang.Throwable -> L66
            java.lang.Class<com.android.server.RCPManagerService> r9 = com.android.server.RCPManagerService.class
            android.os.ServiceManager.addService(r4, r9)     // Catch: java.lang.Throwable -> L66
        L82:
            r8.traceEnd()
            goto L8a
        L86:
            android.util.Slog.e(r5, r0, r9)     // Catch: java.lang.Throwable -> Lce
            goto L82
        L8a:
            java.lang.String r9 = "KnoxMUMRCPPolicyService"
            r8.traceBegin(r9)     // Catch: java.lang.Throwable -> La7
            java.lang.String r9 = "mum_container_rcp_policy"
            if (r2 != 0) goto La9
            if (r3 == 0) goto L97
            goto La9
        L97:
            java.lang.String r7 = "startRCPService | add Lazy Service : KnoxMUMRCPPolicyService , mumrcppolicy"
            android.util.Slog.d(r5, r7)     // Catch: java.lang.Throwable -> La7
            com.android.server.SystemServer$4 r7 = new com.android.server.SystemServer$4     // Catch: java.lang.Throwable -> La7
            r1 = 2
            r7.<init>(r1)     // Catch: java.lang.Throwable -> La7
            android.os.ServiceManager.addService(r9, r7)     // Catch: java.lang.Throwable -> La7
            goto Lc0
        La7:
            r7 = move-exception
            goto Lc4
        La9:
            java.lang.String r2 = "startRCPService | add Service : KnoxMUMRCPPolicyService , mumrcppolicy"
            android.util.Slog.d(r5, r2)     // Catch: java.lang.Throwable -> La7
            if (r7 != 0) goto Lb8
            android.util.Slog.d(r5, r1)     // Catch: java.lang.Throwable -> La7
            r8.traceEnd()
            return
        Lb8:
            com.android.server.enterprise.container.KnoxMUMRCPPolicyService r1 = new com.android.server.enterprise.container.KnoxMUMRCPPolicyService     // Catch: java.lang.Throwable -> La7
            r1.<init>(r7)     // Catch: java.lang.Throwable -> La7
            android.os.ServiceManager.addService(r9, r1)     // Catch: java.lang.Throwable -> La7
        Lc0:
            r8.traceEnd()
            goto Lc8
        Lc4:
            android.util.Slog.e(r5, r0, r7)     // Catch: java.lang.Throwable -> Lc9
            goto Lc0
        Lc8:
            return
        Lc9:
            r7 = move-exception
            r8.traceEnd()
            throw r7
        Lce:
            r7 = move-exception
            r8.traceEnd()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.SystemServer.startRCPService(android.content.Context, com.android.server.utils.TimingsTraceAndSlog, boolean):void");
    }

    public static void startSystemUi(Context context, WindowManagerService windowManagerService) {
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        Intent intent = new Intent();
        intent.setComponent(packageManagerInternal.getSystemUiServiceComponent());
        intent.addFlags(256);
        context.startServiceAsUser(intent, UserHandle.SYSTEM);
        windowManagerService.onSystemUiStarted();
    }

    @Override // android.util.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.printf("Runtime restart: %b\n", Boolean.valueOf(this.mRuntimeRestart));
        printWriter.printf("Start count: %d\n", Integer.valueOf(this.mStartCount));
        printWriter.print("Runtime start-up time: ");
        TimeUtils.formatDuration(this.mRuntimeStartUptime, printWriter);
        printWriter.println();
        printWriter.print("Runtime start-elapsed time: ");
        TimeUtils.formatDuration(this.mRuntimeStartElapsedTime, printWriter);
        printWriter.println();
    }

    @Override // android.util.Dumpable
    public final String getDumpableName() {
        return "SystemServer";
    }

    public final void startApexServices(TimingsTraceAndSlog timingsTraceAndSlog) {
        List list;
        if (Flags.recoverabilityDetection() && Build.IS_DEBUGGABLE && SystemProperties.getBoolean("debug.crash_system", false)) {
            throw new RuntimeException();
        }
        timingsTraceAndSlog.traceBegin("startApexServices");
        ApexManager.ApexManagerImpl apexManagerImpl = (ApexManager.ApexManagerImpl) ApexManager.getInstance();
        synchronized (apexManagerImpl.mLock) {
            Preconditions.checkState(apexManagerImpl.mApexSystemServices != null, "APEX packages have not been scanned");
            list = apexManagerImpl.mApexSystemServices;
        }
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            ApexSystemServiceInfo apexSystemServiceInfo = (ApexSystemServiceInfo) it.next();
            String str = apexSystemServiceInfo.mName;
            String str2 = apexSystemServiceInfo.mJarPath;
            timingsTraceAndSlog.traceBegin("starting " + str);
            if (TextUtils.isEmpty(str2)) {
                this.mSystemServiceManager.startService(str);
            } else {
                this.mSystemServiceManager.startServiceFromJar(str, str2);
            }
            timingsTraceAndSlog.traceEnd();
        }
        SystemServiceManager systemServiceManager = this.mSystemServiceManager;
        systemServiceManager.getClass();
        systemServiceManager.mServiceClassnames = Collections.emptySet();
        systemServiceManager.mServices = Collections.unmodifiableList(systemServiceManager.mServices);
        timingsTraceAndSlog.traceEnd();
    }

    public final void startAttentionService(Context context, TimingsTraceAndSlog timingsTraceAndSlog) {
        if (!(!TextUtils.isEmpty(context.getPackageManager().getAttentionServicePackageName()))) {
            Slog.d("SystemServer", "AttentionService is not configured on this device");
            return;
        }
        timingsTraceAndSlog.traceBegin("StartAttentionManagerService");
        this.mSystemServiceManager.startService(AttentionManagerService.class);
        timingsTraceAndSlog.traceEnd();
    }

    public final void startBootstrapServices(TimingsTraceAndSlog timingsTraceAndSlog) {
        SurfaceAnimationThread surfaceAnimationThread;
        int i = 1;
        int i2 = 0;
        timingsTraceAndSlog.traceBegin("startBootstrapServices");
        timingsTraceAndSlog.traceBegin("ArtModuleServiceInitializer");
        ArtModuleServiceInitializer.setArtModuleServiceManager(new ArtModuleServiceManager());
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartWatchdog");
        Watchdog watchdog = Watchdog.getInstance();
        watchdog.mThread.start();
        SystemServerDumper.m95$$Nest$maddDumpable(this.mDumper, watchdog);
        timingsTraceAndSlog.traceEnd();
        Slog.i("SystemServer", "Reading configuration...");
        timingsTraceAndSlog.traceBegin("ReadingSystemConfig");
        SystemServerInitThreadPool.submit("ReadingSystemConfig", new SystemServer$$ExternalSyntheticLambda3(i2));
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("PlatformCompat");
        ?? platformCompat = new PlatformCompat(this.mSystemContext);
        ServiceManager.addService("platform_compat", (IBinder) platformCompat);
        ServiceManager.addService("platform_compat_native", new PlatformCompatNative(platformCompat));
        AppCompatCallbacks.install(new long[0], new long[0]);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartFileIntegrityService");
        SystemServer$$ExternalSyntheticOutline0.m(this.mSystemServiceManager, FileIntegrityService.class, timingsTraceAndSlog, "StartInstaller");
        Installer installer = (Installer) this.mSystemServiceManager.startService(Installer.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("DeviceIdentifiersPolicyService");
        SystemServer$$ExternalSyntheticOutline0.m(this.mSystemServiceManager, DeviceIdentifiersPolicyService.class, timingsTraceAndSlog, "StartFeatureFlagsService");
        SystemServer$$ExternalSyntheticOutline0.m(this.mSystemServiceManager, FeatureFlagsService.class, timingsTraceAndSlog, "UriGrantsManagerService");
        SystemServer$$ExternalSyntheticOutline0.m(this.mSystemServiceManager, UriGrantsManagerService.Lifecycle.class, timingsTraceAndSlog, "StartPowerStatsService");
        SystemServer$$ExternalSyntheticOutline0.m(this.mSystemServiceManager, PowerStatsService.class, timingsTraceAndSlog, "StartIStatsService");
        startIStatsService();
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("MemtrackProxyService");
        startMemtrackProxyService();
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartAccessCheckingService");
        LocalServices.addService(PermissionMigrationHelperImpl.class, new PermissionMigrationHelperImpl());
        LocalServices.addService(AppOpMigrationHelperImpl.class, new AppOpMigrationHelperImpl());
        SystemServer$$ExternalSyntheticOutline0.m(this.mSystemServiceManager, AccessCheckingService.class, timingsTraceAndSlog, "StartActivityManager");
        ActivityTaskManagerService activityTaskManagerService = ((ActivityTaskManagerService.Lifecycle) this.mSystemServiceManager.startService(ActivityTaskManagerService.Lifecycle.class)).mService;
        SystemServiceManager systemServiceManager = this.mSystemServiceManager;
        ActivityManagerService.Lifecycle.sAtm = activityTaskManagerService;
        ActivityManagerService activityManagerService = ((ActivityManagerService.Lifecycle) systemServiceManager.startService(ActivityManagerService.Lifecycle.class)).mService;
        this.mActivityManagerService = activityManagerService;
        activityManagerService.mSystemServiceManager = this.mSystemServiceManager;
        activityTaskManagerService.getClass();
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartDataLoaderManagerService");
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartIncrementalService");
        this.mIncrementalServiceHandle = startIncrementalService();
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartPowerManager");
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("StartThermalManager");
        SystemServer$$ExternalSyntheticOutline0.m(this.mSystemServiceManager, ThermalManagerService.class, timingsTraceAndSlog, "InitPowerManagement");
        ActivityManagerService activityManagerService2 = this.mActivityManagerService;
        ActivityTaskManagerService activityTaskManagerService2 = activityManagerService2.mActivityTaskManager;
        WindowManagerGlobalLock windowManagerGlobalLock = activityTaskManagerService2.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityTaskSupervisor activityTaskSupervisor = activityTaskManagerService2.mTaskSupervisor;
                PowerManager powerManager = (PowerManager) activityTaskSupervisor.mService.mContext.getSystemService(PowerManager.class);
                activityTaskSupervisor.mPowerManager = powerManager;
                activityTaskSupervisor.mGoingToSleepWakeLock = powerManager.newWakeLock(1, "ActivityManager-Sleep");
                PowerManager.WakeLock newWakeLock = activityTaskSupervisor.mPowerManager.newWakeLock(1, "*launch*");
                activityTaskSupervisor.mLaunchingActivityWakeLock = newWakeLock;
                newWakeLock.setReferenceCounted(false);
                PowerManager powerManager2 = (PowerManager) activityTaskManagerService2.mContext.getSystemService("power");
                activityTaskManagerService2.mPowerManagerInternal = (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
                PowerManager.WakeLock newWakeLock2 = powerManager2.newWakeLock(1, "*voice*");
                activityTaskManagerService2.mVoiceWakeLock = newWakeLock2;
                newWakeLock2.setReferenceCounted(false);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        BatteryStatsService batteryStatsService = activityManagerService2.mBatteryStatsService;
        batteryStatsService.getClass();
        PowerManagerInternal powerManagerInternal = (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        powerManagerInternal.registerLowPowerModeObserver(batteryStatsService);
        synchronized (batteryStatsService.mStats) {
            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
            boolean z = powerManagerInternal.getLowPowerState(9).batterySaverEnabled;
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long uptimeMillis = SystemClock.uptimeMillis();
            if (batteryStatsImpl.mPowerSaveModeEnabled != z) {
                batteryStatsImpl.notePowerSaveModeLocked(elapsedRealtime, uptimeMillis, z);
            } else {
                batteryStatsImpl.mFrameworkStatsLogger.getClass();
                FrameworkStatsLog.write(20, z ? 1 : 0);
            }
        }
        batteryStatsService.new WakeupReasonThread().start();
        activityManagerService2.mLocalPowerManager = (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("VaultKeeperService");
        SystemServer$$ExternalSyntheticOutline0.m(this.mSystemServiceManager, VaultKeeperService.LifeCycle.class, timingsTraceAndSlog, "StartRecoverySystemService");
        this.mSystemServiceManager.startService(RecoverySystemService.Lifecycle.class);
        timingsTraceAndSlog.traceEnd();
        Context context = this.mSystemContext;
        int i3 = RescueParty.LEVEL_ISRB_BOOT;
        PackageWatchdog.getInstance(context).registerHealthObserver(RescueParty.RescuePartyObserver.getInstance(context));
        if (!Flags.recoverabilityDetection()) {
            PackageWatchdog.getInstance(this.mSystemContext).noteBoot();
        }
        timingsTraceAndSlog.traceBegin("StartLightsService");
        SystemServer$$ExternalSyntheticOutline0.m(this.mSystemServiceManager, LightsService.class, timingsTraceAndSlog, "StartPdpService");
        SystemServer$$ExternalSyntheticOutline0.m(this.mSystemServiceManager, PdpService.class, timingsTraceAndSlog, "StartDisplayOffloadService");
        if (SystemProperties.getBoolean("config.enable_display_offload", false)) {
            this.mSystemServiceManager.startService("com.android.clockwork.displayoffload.DisplayOffloadService");
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
            this.mFirstBoot = this.mPackageManagerService.mFirstBoot;
            this.mPackageManager = this.mSystemContext.getPackageManager();
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("DexUseManagerLocal");
            LocalManagerRegistry.addManager(DexUseManagerLocal.class, DexUseManagerLocal.createInstance(this.mSystemContext));
            timingsTraceAndSlog.traceEnd();
            if (!this.mRuntimeRestart) {
                PackageManagerService packageManagerService = this.mPackageManagerService;
                if (!packageManagerService.mFirstBoot && !packageManagerService.isDeviceUpgrading()) {
                    FrameworkStatsLog.write(240, 15, SystemClock.elapsedRealtime());
                }
            }
            timingsTraceAndSlog.traceBegin("StartASKSManagerService");
            Context context2 = this.mSystemContext;
            String str = ASKSManagerService.mASKSPolicyVersion;
            Slog.i("ASKSManager", "main starts");
            ?? aSKSManagerService = new ASKSManagerService(context2);
            ServiceManager.addService("asks", (IBinder) aSKSManagerService);
            Slog.i("ASKSManager", "main ends");
            this.mASKSManagerService = aSKSManagerService;
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
            if (Build.IS_ARC) {
                timingsTraceAndSlog.traceBegin("StartArcSystemHealthService");
                this.mSystemServiceManager.startService("com.android.server.arc.health.ArcSystemHealthService");
                timingsTraceAndSlog.traceEnd();
            }
            timingsTraceAndSlog.traceBegin("StartUserManagerService");
            SystemServer$$ExternalSyntheticOutline0.m(this.mSystemServiceManager, UserManagerService.LifeCycle.class, timingsTraceAndSlog, "InitAttributerCache");
            AttributeCache.init(this.mSystemContext);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("SetSystemProcess");
            final ?? r3 = this.mActivityManagerService;
            r3.getClass();
            try {
                ServiceManager.addService("activity", (IBinder) r3, true, 21);
                ServiceManager.addService("procstats", r3.mProcessStats);
                ServiceManager.addService("meminfo", new ActivityManagerService.MemBinder(r3), false, 2);
                ActivityManagerService.DbBinder dbBinder = new ActivityManagerService.DbBinder(2);
                dbBinder.mActivityManagerService = r3;
                ServiceManager.addService("gfxinfo", dbBinder);
                ActivityManagerService.DbBinder dbBinder2 = new ActivityManagerService.DbBinder(i2);
                dbBinder2.mActivityManagerService = r3;
                ServiceManager.addService("dbinfo", dbBinder2);
                AppProfiler appProfiler = r3.mAppProfiler;
                appProfiler.getClass();
                ServiceManager.addService("cpuinfo", appProfiler.new CpuBinder(), false, 1);
                ?? permissionController = new ActivityManagerService.PermissionController();
                permissionController.mActivityManagerService = r3;
                ServiceManager.addService("permission", (IBinder) permissionController);
                ServiceManager.addService("processinfo", new ActivityManagerService.ProcessInfoService(r3));
                ActivityManagerService.DbBinder dbBinder3 = new ActivityManagerService.DbBinder(i);
                dbBinder3.mActivityManagerService = r3;
                ServiceManager.addService("cacheinfo", dbBinder3);
                ApplicationInfo applicationInfo = r3.mContext.getPackageManager().getApplicationInfo("android", 1049600);
                r3.mSystemThread.installSystemApplicationInfo(applicationInfo, ActivityManagerService.class.getClassLoader());
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (r3) {
                    try {
                        ProcessRecord newProcessRecordLocked = r3.mProcessList.newProcessRecordLocked(applicationInfo, applicationInfo.processName, false, 0, false, 0, null, new HostingRecord("system"));
                        newProcessRecordLocked.mPersistent = true;
                        newProcessRecordLocked.mWindowProcessController.mPersistent = true;
                        newProcessRecordLocked.setPid(ActivityManagerService.MY_PID);
                        newProcessRecordLocked.mState.mMaxAdj = -900;
                        newProcessRecordLocked.makeActive(r3.mSystemThread.getApplicationThread(), r3.mProcessStats);
                        newProcessRecordLocked.mProfile.addHostingComponentType(1);
                        r3.addPidLocked(newProcessRecordLocked);
                        r3.mProcessList.updateLruProcessLocked(newProcessRecordLocked, null, false);
                        r3.updateOomAdjLocked(14);
                    } catch (Throwable th2) {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        throw th2;
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                r3.mAppOpsService.startWatchingModeWithFlags(63, null, 0, new IAppOpsCallback.Stub() { // from class: com.android.server.am.ActivityManagerService.4
                    public AnonymousClass4() {
                    }

                    public final void opChanged(int i4, int i5, String str2, String str3) {
                        if (i4 != 63 || str2 == null || ActivityManagerService.this.getAppOpsManager$1().checkOpNoThrow(i4, i5, str2) == 0) {
                            return;
                        }
                        ActivityManagerService activityManagerService3 = ActivityManagerService.this;
                        activityManagerService3.getClass();
                        ActivityManagerService.boostPriorityForLockedSection();
                        synchronized (activityManagerService3) {
                            try {
                                UidRecord uidRecord = activityManagerService3.mProcessList.mActiveUids.get(i5);
                                if (uidRecord == null) {
                                    activityManagerService3.doStopUidLocked(i5, null);
                                } else if (uidRecord.mIdle) {
                                    activityManagerService3.doStopUidLocked(uidRecord.mUid, uidRecord);
                                }
                            } catch (Throwable th3) {
                                ActivityManagerService.resetPriorityAfterLockedSection();
                                throw th3;
                            }
                        }
                        ActivityManagerService.resetPriorityAfterLockedSection();
                    }
                });
                r3.mAppOpsService.startWatchingActive(new int[]{26}, new IAppOpsActiveCallback.Stub() { // from class: com.android.server.am.ActivityManagerService.5
                    public AnonymousClass5() {
                    }

                    public final void opActiveChanged(int i4, int i5, String str2, String str3, int i6, boolean z2, int i7, int i8) {
                        ActivityManagerService activityManagerService3 = ActivityManagerService.this;
                        synchronized (activityManagerService3.mActiveCameraUids) {
                            try {
                                int indexOf = activityManagerService3.mActiveCameraUids.indexOf(i5);
                                if (z2) {
                                    if (indexOf < 0) {
                                        activityManagerService3.mActiveCameraUids.add(i5);
                                    }
                                } else if (indexOf >= 0) {
                                    activityManagerService3.mActiveCameraUids.remove(indexOf);
                                }
                            } catch (Throwable th3) {
                                throw th3;
                            }
                        }
                        if (com.android.window.flags.Flags.fifoPriorityForMajorUiProcesses()) {
                            ActivityManagerProcLock activityManagerProcLock = activityManagerService3.mProcLock;
                            ActivityManagerService.boostPriorityForProcLockedSection();
                            synchronized (activityManagerProcLock) {
                                try {
                                    activityManagerService3.adjustFifoProcessesIfNeeded(i5, !z2);
                                } catch (Throwable th4) {
                                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                                    throw th4;
                                }
                            }
                            ActivityManagerService.resetPriorityAfterProcLockedSection();
                        }
                    }
                });
                timingsTraceAndSlog.traceEnd();
                platformCompat.registerPackageReceiver(this.mSystemContext);
                timingsTraceAndSlog.traceBegin("InitWatchdog");
                Slog.i("SystemServer", "!@Boot_EBS_D: InitWatchdog");
                Context context3 = this.mSystemContext;
                watchdog.mActivity = this.mActivityManagerService;
                Watchdog.mContext = context3;
                context3.registerReceiver(watchdog.new RebootRequestReceiver(), new IntentFilter("android.intent.action.REBOOT"), "android.permission.REBOOT", null);
                HeapdumpWatcher heapdumpWatcher = watchdog.hdWatcher;
                Context context4 = Watchdog.mContext;
                ActivityManagerService activityManagerService3 = watchdog.mActivity;
                heapdumpWatcher.getClass();
                HeapdumpWatcher.mContext = context4;
                heapdumpWatcher.mActivity = activityManagerService3;
                FileDescriptorWatcher fileDescriptorWatcher = watchdog.fdWatcher;
                Context context5 = Watchdog.mContext;
                fileDescriptorWatcher.getClass();
                FileDescriptorWatcher.mContext = context5;
                timingsTraceAndSlog.traceEnd();
                this.mDisplayManagerService.getClass();
                int threadId = DisplayThread.get().getThreadId();
                boolean z2 = CoreRune.SYSPERF_BOOST_OPT;
                Process.setThreadGroupAndCpuset(threadId, z2 ? 10 : 5);
                Process.setThreadGroupAndCpuset(AnimationThread.get().getThreadId(), z2 ? 10 : 5);
                synchronized (SurfaceAnimationThread.class) {
                    SurfaceAnimationThread.ensureThreadLocked();
                    surfaceAnimationThread = SurfaceAnimationThread.sInstance;
                }
                Process.setThreadGroupAndCpuset(surfaceAnimationThread.getThreadId(), z2 ? 10 : 5);
                if (CoreRune.SYSPERF_VI_BOOST) {
                    new Handler().postDelayed(new DisplayManagerService.AnonymousClass5(), 10000L);
                }
                timingsTraceAndSlog.traceBegin("StartOverlayManagerService");
                this.mSystemServiceManager.startService(new OverlayManagerService(this.mSystemContext));
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartResourcesManagerService");
                ResourcesManagerService resourcesManagerService = new ResourcesManagerService(this.mSystemContext);
                resourcesManagerService.mActivityManagerService = this.mActivityManagerService;
                this.mSystemServiceManager.startService(resourcesManagerService);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("StartSensorPrivacyService");
                this.mSystemServiceManager.startService(new SensorPrivacyService(this.mSystemContext));
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("PACMService");
                this.mSystemServiceManager.startService(PACMService.class);
                timingsTraceAndSlog.traceEnd();
                if (SystemProperties.getInt("persist.sys.displayinset.top", 0) > 0) {
                    ActivityThread.currentActivityThread().handleSystemApplicationInfoChanged(this.mActivityManagerService.getPackageManagerInternal().getApplicationInfo(Binder.getCallingUid(), 0, 1024L, "android"));
                    ((DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class)).onOverlayChanged();
                }
                timingsTraceAndSlog.traceBegin("StartSensorService");
                this.mSystemServiceManager.startService(SensorService.class);
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceEnd();
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException("Unable to find android system package", e);
            }
        } catch (Throwable th3) {
            Watchdog.getInstance().resumeWatchingCurrentThread("packagemanagermain");
            throw th3;
        }
    }

    public final void startContentCaptureService(Context context, TimingsTraceAndSlog timingsTraceAndSlog) {
        boolean z;
        ActivityManagerService activityManagerService;
        String property = DeviceConfig.getProperty("content_capture", "service_explicitly_enabled");
        if (property == null || property.equalsIgnoreCase("default")) {
            z = false;
        } else {
            z = Boolean.parseBoolean(property);
            if (!z) {
                Slog.d("SystemServer", "ContentCaptureService explicitly disabled by DeviceConfig");
                return;
            }
            Slog.d("SystemServer", "ContentCaptureService explicitly enabled by DeviceConfig");
        }
        if (!z) {
            if (!deviceHasConfigString(context, R.string.date_picker_increment_day_button)) {
                Slog.d("SystemServer", "ContentCaptureService disabled because resource is not overlaid");
                return;
            } else if (!(!TextUtils.isEmpty(context.getString(R.string.date_picker_increment_month_button)))) {
                Slog.d("SystemServer", "ContentProtectionService disabled because resource is not overlaid, ContentCaptureService still enabled");
            }
        }
        timingsTraceAndSlog.traceBegin("StartContentCaptureService");
        this.mSystemServiceManager.startService(ContentCaptureManagerService.class);
        ContentCaptureManagerService.LocalService localService = (ContentCaptureManagerService.LocalService) LocalServices.getService(ContentCaptureManagerService.LocalService.class);
        if (localService != null && (activityManagerService = this.mActivityManagerService) != null) {
            activityManagerService.mContentCaptureService = localService;
        }
        timingsTraceAndSlog.traceEnd();
    }

    public final void startCoreServices(TimingsTraceAndSlog timingsTraceAndSlog) {
        timingsTraceAndSlog.traceBegin("startCoreServices");
        if (SystemProperties.getBoolean("persist.sys.enable_isrb", false)) {
            timingsTraceAndSlog.traceBegin("StartISRBService");
            this.mSystemServiceManager.startService(IsrbManagerService.class);
            timingsTraceAndSlog.traceEnd();
        }
        timingsTraceAndSlog.traceBegin("StartSystemConfigService");
        SystemServer$$ExternalSyntheticOutline0.m(this.mSystemServiceManager, SystemConfigService.class, timingsTraceAndSlog, "StartBatteryService");
        SystemServer$$ExternalSyntheticOutline0.m(this.mSystemServiceManager, BatteryService.class, timingsTraceAndSlog, "StartUsageService");
        this.mSystemServiceManager.startService(UsageStatsService.class);
        ActivityManagerService activityManagerService = this.mActivityManagerService;
        UsageStatsManagerInternal usageStatsManagerInternal = (UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class);
        activityManagerService.mUsageStatsService = usageStatsManagerInternal;
        ActivityTaskManagerService activityTaskManagerService = activityManagerService.mActivityTaskManager;
        WindowManagerGlobalLock windowManagerGlobalLock = activityTaskManagerService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                activityTaskManagerService.mUsageStatsInternal = usageStatsManagerInternal;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        timingsTraceAndSlog.traceEnd();
        if (this.mPackageManager.hasSystemFeature("android.software.webview")) {
            timingsTraceAndSlog.traceBegin("StartWebViewUpdateService");
            this.mWebViewUpdateService = (WebViewUpdateService) this.mSystemServiceManager.startService(WebViewUpdateService.class);
            timingsTraceAndSlog.traceEnd();
        }
        timingsTraceAndSlog.traceBegin("StartCachedDeviceStateService");
        SystemServer$$ExternalSyntheticOutline0.m(this.mSystemServiceManager, CachedDeviceStateService.class, timingsTraceAndSlog, "StartBinderCallsStatsService");
        SystemServer$$ExternalSyntheticOutline0.m(this.mSystemServiceManager, BinderCallsStatsService.LifeCycle.class, timingsTraceAndSlog, "StartLooperStatsService");
        SystemServer$$ExternalSyntheticOutline0.m(this.mSystemServiceManager, LooperStatsService.Lifecycle.class, timingsTraceAndSlog, "StartRollbackManagerService");
        SystemServer$$ExternalSyntheticOutline0.m(this.mSystemServiceManager, RollbackManagerService.class, timingsTraceAndSlog, "StartNativeTombstoneManagerService");
        SystemServer$$ExternalSyntheticOutline0.m(this.mSystemServiceManager, NativeTombstoneManagerService.class, timingsTraceAndSlog, "StartBugreportManagerService");
        SystemServer$$ExternalSyntheticOutline0.m(this.mSystemServiceManager, BugreportManagerService.class, timingsTraceAndSlog, "GpuService");
        SystemServer$$ExternalSyntheticOutline0.m(this.mSystemServiceManager, GpuService.class, timingsTraceAndSlog, "StartRemoteProvisioningService");
        this.mSystemServiceManager.startService(RemoteProvisioningService.class);
        timingsTraceAndSlog.traceEnd();
        if (Build.IS_DEBUGGABLE || Build.IS_ENG) {
            timingsTraceAndSlog.traceBegin("CpuMonitorService");
            this.mSystemServiceManager.startService(CpuMonitorService.class);
            timingsTraceAndSlog.traceEnd();
        }
        timingsTraceAndSlog.traceEnd();
    }

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

    public final void startOnDeviceIntelligenceService(TimingsTraceAndSlog timingsTraceAndSlog) {
        timingsTraceAndSlog.traceBegin("startOnDeviceIntelligenceManagerService");
        this.mSystemServiceManager.startService(OnDeviceIntelligenceManagerService.class);
        timingsTraceAndSlog.traceEnd();
    }

    /* JADX WARN: Code restructure failed: missing block: B:1000:0x0f4d, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1001:0x0f4e, code lost:
    
        r6 = r0;
        r5 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1002:0x0f55, code lost:
    
        reportWtf("starting NetworkPolicy Service", r6);
        r5 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1004:0x0f53, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1005:0x0f50, code lost:
    
        r6 = r0;
        r5 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1008:0x0eac, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1009:0x0ead, code lost:
    
        r5 = r0;
        r3 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1010:0x0eb2, code lost:
    
        reportWtf("starting NetworkManagement Service", r5);
        r3 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1012:0x0eaf, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1013:0x0eb0, code lost:
    
        r5 = r0;
        r3 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1015:0x0e91, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1016:0x0e92, code lost:
    
        reportWtf("initializing NetworkStackClient", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:1018:0x0e79, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1019:0x0e7a, code lost:
    
        reportWtf("initializing ConnectivityModuleConnector", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:1020:0x0e60, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1021:0x0e61, code lost:
    
        android.util.Slog.e("SystemServer", "Failure starting MotionRecognitionService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:1024:0x0dd2, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1027:0x0e0e, code lost:
    
        android.util.Slog.e("SystemServer", "Failure starting SemContextService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:1029:0x2136, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1030:0x2137, code lost:
    
        r1 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1032:0x213c, code lost:
    
        throw r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1033:0x0dcd, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1034:0x0dce, code lost:
    
        r1 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1076:0x0afc, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1077:0x0afd, code lost:
    
        reportWtf("performing fstrim", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:1082:0x0abf, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1089:0x2150, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1098:0x0991, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1100:0x09c7, code lost:
    
        android.util.Slog.e("SystemServer", "Failed to start SehCodecSolutionService ", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:1103:0x08c8, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1105:0x08fe, code lost:
    
        android.util.Slog.e("SystemServer", "Failed to start SehHdrSolutionService ", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:1108:0x088b, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1110:0x089b, code lost:
    
        android.util.Slog.e("SystemServer", "Failed to start DisplayAiqeManagerService.", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:1118:0x07d2, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1119:0x07d3, code lost:
    
        reportWtf("making display ready", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:1123:0x0713, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1125:0x072f, code lost:
    
        reportWtf("starting SAccessoryManager", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:1127:0x06a4, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1129:0x06a6, code lost:
    
        reportWtf("Failed To Start SemInputDeviceManagerService loader", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:1131:0x0556, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1133:0x0558, code lost:
    
        android.util.Slog.e("SystemServer", "hqm.jar not found");
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:1139:0x03dd, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1142:0x03df, code lost:
    
        android.util.Slog.e("SystemServer", "ssrm.jar not found");
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:1144:0x2151, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1145:0x2152, code lost:
    
        r1 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1147:0x2157, code lost:
    
        throw r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1148:0x03d8, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1149:0x03d9, code lost:
    
        r1 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1151:0x02b9, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1152:0x02ba, code lost:
    
        r8 = r0;
        r22 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1153:0x02c2, code lost:
    
        r23 = r13;
        android.util.Slog.e("SystemServer", "Failure starting KnoxMUMContainerPolicy Service", r8);
        r4 = r22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1155:0x02be, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1156:0x02bf, code lost:
    
        r8 = r0;
        r22 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:439:0x1d8a, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:440:0x1d8b, code lost:
    
        reportWtf("RegisterLogMteState", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:860:0x1982, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:861:0x1983, code lost:
    
        android.util.Slog.e("SystemServer", "Failure adding ChimeraManagerService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:863:0x1927, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:864:0x1928, code lost:
    
        reportWtf("starting SelinuxAuditLogsService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:866:0x1855, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:868:0x185d, code lost:
    
        reportWtf("Failed To Call SemInputDeviceManagerService SystemReady loader ", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:870:0x1859, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:871:0x185a, code lost:
    
        r25 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:873:0x1820, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:874:0x1821, code lost:
    
        r3 = r0;
        r1 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:875:0x1827, code lost:
    
        reportWtf("starting MediaRouterService", r3);
        r1 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:877:0x1824, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:878:0x1825, code lost:
    
        r3 = r0;
        r1 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:882:0x174a, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:883:0x174b, code lost:
    
        reportWtf("starting BattAuthManager", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:885:0x1727, code lost:
    
        android.util.Slog.e("SystemServer", "knox_mtd.jar not found");
     */
    /* JADX WARN: Code restructure failed: missing block: B:887:0x1623, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:888:0x1624, code lost:
    
        reportWtf("starting CertBlacklister", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:898:0x15e2, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:899:0x15e3, code lost:
    
        reportWtf("starting RuntimeService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:901:0x15c5, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:902:0x15c6, code lost:
    
        reportWtf("starting DiskStats Service", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:905:0x14dd, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:906:0x14de, code lost:
    
        android.util.Slog.e("SystemServer", "Failure starting HardwarePropertiesManagerService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:909:0x1489, code lost:
    
        android.util.Slog.e("SystemServer", "Failure starting AdbService");
     */
    /* JADX WARN: Code restructure failed: missing block: B:917:0x132c, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:919:0x1334, code lost:
    
        r31 = r3;
        android.util.Slog.e("SystemServer", "Failure starting KnoxVpnEngineService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:921:0x12d0, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:922:0x12d1, code lost:
    
        reportWtf("starting LocationTimeZoneManagerService service", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:924:0x12b7, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:925:0x12b8, code lost:
    
        reportWtf("starting AltitudeService service", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:927:0x129e, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:928:0x129f, code lost:
    
        reportWtf("starting TimeZoneDetectorService service", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:930:0x1280, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:931:0x1281, code lost:
    
        r5 = r0;
        r3 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:932:0x1287, code lost:
    
        reportWtf("starting Country Detector", r5);
        r3 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:934:0x1284, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:935:0x1285, code lost:
    
        r5 = r0;
        r3 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:937:0x124e, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:938:0x124f, code lost:
    
        r5 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:939:0x1255, code lost:
    
        r30 = r3;
        reportWtf("Starting MoccaService:" + r5.getMessage(), r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:941:0x1252, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:942:0x1253, code lost:
    
        r5 = r0;
        r4 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:944:0x1232, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:945:0x1233, code lost:
    
        reportWtf("Failed To Start SemDisplaySolution Service ", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:947:0x120b, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:948:0x120c, code lost:
    
        reportWtf("Failed To Start Mdnie Service ", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:950:0x11d9, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:951:0x11da, code lost:
    
        r4 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:952:0x11e4, code lost:
    
        reportWtf("Starting SLocationService", r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:954:0x11dc, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:955:0x11dd, code lost:
    
        r4 = r0;
        r3 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:957:0x11e0, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:958:0x11e1, code lost:
    
        r29 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:960:0x118e, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:961:0x118f, code lost:
    
        reportWtf("starting TimeDetectorService service", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:963:0x114c, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:964:0x114d, code lost:
    
        reportWtf("starting UpdateLockService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:966:0x1130, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:967:0x1131, code lost:
    
        reportWtf("starting SystemUpdateManagerService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:969:0x110c, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:970:0x110d, code lost:
    
        r8 = r0;
        r27 = r3;
        r7 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:971:0x1116, code lost:
    
        reportWtf("starting VCN Management Service", r8);
        r7 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:973:0x1111, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:974:0x1112, code lost:
    
        r8 = r0;
        r27 = r3;
        r7 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:976:0x10eb, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:977:0x10ec, code lost:
    
        r7 = r0;
        r3 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:978:0x10f1, code lost:
    
        reportWtf("starting VPN Manager Service", r7);
        r3 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:980:0x10ee, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:981:0x10ef, code lost:
    
        r7 = r0;
        r3 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:983:0x10d0, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:984:0x10d1, code lost:
    
        reportWtf("starting SecurityStateManagerService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:986:0x10b3, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:987:0x10b4, code lost:
    
        android.util.Slog.e("SystemServer", "Failed to start ExtendedEthernetService.", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:989:0x104e, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:990:0x104f, code lost:
    
        reportWtf("starting PacProxyService", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:993:0x0f6f, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:994:0x0f70, code lost:
    
        r7 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:995:0x0f75, code lost:
    
        reportWtf("starting URSP Service", r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:997:0x0f72, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:998:0x0f73, code lost:
    
        r7 = r0;
        r6 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startOtherServices(com.android.server.utils.TimingsTraceAndSlog r41) {
        /*
            Method dump skipped, instructions count: 8558
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.SystemServer.startOtherServices(com.android.server.utils.TimingsTraceAndSlog):void");
    }

    public final void startRotationResolverService(Context context, TimingsTraceAndSlog timingsTraceAndSlog) {
        if (!(!TextUtils.isEmpty(context.getPackageManager().getRotationResolverPackageName()))) {
            Slog.d("SystemServer", "RotationResolverService is not configured on this device");
            return;
        }
        timingsTraceAndSlog.traceBegin("StartRotationResolverService");
        this.mSystemServiceManager.startService(RotationResolverManagerService.class);
        timingsTraceAndSlog.traceEnd();
    }

    public final void startSystemCaptionsManagerService(Context context, TimingsTraceAndSlog timingsTraceAndSlog) {
        if (!deviceHasConfigString(context, R.string.deprecated_target_sdk_message)) {
            Slog.d("SystemServer", "SystemCaptionsManagerService disabled because resource is not overlaid");
            return;
        }
        timingsTraceAndSlog.traceBegin("StartSystemCaptionsManagerService");
        this.mSystemServiceManager.startService(SystemCaptionsManagerService.class);
        timingsTraceAndSlog.traceEnd();
    }

    public final void startTextToSpeechManagerService(TimingsTraceAndSlog timingsTraceAndSlog) {
        timingsTraceAndSlog.traceBegin("StartTextToSpeechManagerService");
        this.mSystemServiceManager.startService(TextToSpeechManagerService.class);
        timingsTraceAndSlog.traceEnd();
    }

    public final void startWearableSensingService(TimingsTraceAndSlog timingsTraceAndSlog) {
        timingsTraceAndSlog.traceBegin("startWearableSensingService");
        this.mSystemServiceManager.startService(WearableSensingManagerService.class);
        timingsTraceAndSlog.traceEnd();
    }
}
