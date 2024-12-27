package com.android.server;

import android.app.ActivityManagerInternal;
import android.app.ActivityThread;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.net.util.NetworkConstants;
import android.os.BatteryStatsInternal;
import android.os.Binder;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.SemHqmManager;
import android.os.ShellCommand;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.KeyValueListParser;
import android.util.Slog;

import com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.Flags;
import com.android.internal.os.AppIdToPackageMap;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.BinderCallsStats;
import com.android.internal.os.BinderInternal;
import com.android.internal.os.CachedDeviceState;
import com.android.internal.util.DumpUtils;
import com.android.server.backup.BackupManagerConstants;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;

public final class BinderCallsStatsService extends Binder {
    public final BinderCallsStats mBinderCallsStats;
    public final AuthorizedWorkSourceProvider mWorkSourceProvider;

    public final class AuthorizedWorkSourceProvider implements BinderInternal.WorkSourceProvider {
        public ArraySet mAppIdTrustlist;

        public final int resolveWorkSourceUid(int i) {
            int callingUid = Binder.getCallingUid();
            return (!this.mAppIdTrustlist.contains(Integer.valueOf(UserHandle.getAppId(callingUid)))
                            || i == -1)
                    ? callingUid
                    : i;
        }
    }

    public final class BinderCallsStatsShellCommand extends ShellCommand {
        public final PrintWriter mPrintWriter;

        public BinderCallsStatsShellCommand(PrintWriter printWriter) {
            this.mPrintWriter = printWriter;
        }

        public final PrintWriter getOutPrintWriter() {
            PrintWriter printWriter = this.mPrintWriter;
            return printWriter != null ? printWriter : super.getOutPrintWriter();
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public final int onCommand(String str) {
            char c;
            PrintWriter outPrintWriter = getOutPrintWriter();
            if (str == null) {
                return -1;
            }
            switch (str.hashCode()) {
                case -1982051596:
                    if (str.equals("--history")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1768939845:
                    if (str.equals("--set-cpu-threshold")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -1615291473:
                    if (str.equals("--reset")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -1289263917:
                    if (str.equals("--no-sampling")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -1237677752:
                    if (str.equals("--disable")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -534486470:
                    if (str.equals("--work-source-uid")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -106516359:
                    if (str.equals("--dump-worksource-provider")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -17753872:
                    if (str.equals("--set-limit")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 186382994:
                    if (str.equals("--set-sampling")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 1101165347:
                    if (str.equals("--enable")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 1448286703:
                    if (str.equals("--disable-detailed-tracking")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case 2041864970:
                    if (str.equals("--enable-detailed-tracking")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    BinderCallsStatsService.this.mBinderCallsStats.dumpStats(outPrintWriter);
                    return 0;
                case 1:
                    try {
                        int parseInt = Integer.parseInt(peekNextArg());
                        if (BinderCallsStatsService.this.mBinderCallsStats.setCpuUsageThreshold(
                                parseInt)) {
                            outPrintWriter.println("cpu usage threshold is set to " + parseInt);
                        }
                    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                        Slog.e("ShellCommand", "--set-cpu-threshold execution failed", e);
                    }
                    return 0;
                case 2:
                    BinderCallsStatsService binderCallsStatsService = BinderCallsStatsService.this;
                    binderCallsStatsService.getClass();
                    Slog.i("BinderCallsStatsService", "Resetting stats");
                    binderCallsStatsService.mBinderCallsStats.reset(new boolean[0]);
                    outPrintWriter.println("binder_calls_stats reset.");
                    return 0;
                case 3:
                    BinderCallsStatsService.this.mBinderCallsStats.setSamplingInterval(1);
                    return 0;
                case 4:
                    Binder.setObserver(null);
                    return 0;
                case 5:
                    String nextArgRequired = getNextArgRequired();
                    try {
                        BinderCallsStatsService.this.mBinderCallsStats
                                .recordAllCallsForWorkSourceUid(Integer.parseInt(nextArgRequired));
                        return 0;
                    } catch (NumberFormatException unused) {
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(
                                outPrintWriter, "Invalid UID: ", nextArgRequired);
                        return -1;
                    }
                case 6:
                    BinderCallsStatsService.this.mBinderCallsStats.setDetailedTracking(true);
                    AuthorizedWorkSourceProvider authorizedWorkSourceProvider =
                            BinderCallsStatsService.this.mWorkSourceProvider;
                    AppIdToPackageMap snapshot = AppIdToPackageMap.getSnapshot();
                    authorizedWorkSourceProvider.getClass();
                    outPrintWriter.println("AppIds of apps that can set the work source:");
                    Iterator it = authorizedWorkSourceProvider.mAppIdTrustlist.iterator();
                    while (it.hasNext()) {
                        outPrintWriter.println(
                                "\t- " + snapshot.mapAppId(((Integer) it.next()).intValue()));
                    }
                    return 0;
                case 7:
                    try {
                        int parseInt2 = Integer.parseInt(peekNextArg());
                        BinderCallsStatsService.this.mBinderCallsStats.setMaxBinderCallStats(
                                parseInt2);
                        outPrintWriter.println("Limit is set to " + parseInt2);
                    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e2) {
                        Slog.e("ShellCommand", "--set-limit execution failed", e2);
                    }
                    return 0;
                case '\b':
                    try {
                        int parseInt3 = Integer.parseInt(peekNextArg());
                        BinderCallsStatsService.this.mBinderCallsStats.setSamplingInterval(
                                parseInt3);
                        outPrintWriter.println("sampling internal is set to " + parseInt3);
                    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e3) {
                        Slog.e("ShellCommand", "--set-sampling execution failed", e3);
                    }
                    return 0;
                case '\t':
                    Binder.setObserver(BinderCallsStatsService.this.mBinderCallsStats);
                    return 0;
                case '\n':
                    SystemProperties.set("persist.sys.binder_calls_detailed_tracking", "");
                    BinderCallsStatsService.this.mBinderCallsStats.setDetailedTracking(false);
                    outPrintWriter.println("Detailed tracking disabled");
                    return 0;
                case 11:
                    SystemProperties.set("persist.sys.binder_calls_detailed_tracking", "1");
                    BinderCallsStatsService.this.mBinderCallsStats.setDetailedTracking(true);
                    outPrintWriter.println("Detailed tracking enabled");
                    return 0;
                default:
                    return handleDefaultCommands(str);
            }
        }

        public final void onHelp() {
            PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("binder_calls_stats commands:");
            outPrintWriter.println("  --reset: Reset stats");
            outPrintWriter.println("  --enable: Enable tracking binder calls");
            outPrintWriter.println("  --disable: Disables tracking binder calls");
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "  --no-sampling: Tracks all calls",
                    "  --enable-detailed-tracking: Enables detailed tracking",
                    "  --disable-detailed-tracking: Disables detailed tracking",
                    "  --work-source-uid <UID>: Track all binder calls from the UID");
            outPrintWriter.println("  --set-cpu-threshold <threshold>: Set new CPU threshold");
            outPrintWriter.println("  --set-sampling <sampling value>: Set new sampling ratio");
        }
    }

    public class Internal {
        public final BinderCallsStats mBinderCallsStats;
        public final CustomizedBinderCallsStatsInternal mCustomizedBinderCallsStatsInternal;

        public Internal(BinderCallsStats binderCallsStats, Context context) {
            this.mBinderCallsStats = binderCallsStats;
            this.mCustomizedBinderCallsStatsInternal =
                    new CustomizedBinderCallsStatsInternal(binderCallsStats, context);
        }

        public void reportCpuUsage(final int i) {
            final CustomizedBinderCallsStatsInternal customizedBinderCallsStatsInternal =
                    this.mCustomizedBinderCallsStatsInternal;
            if (customizedBinderCallsStatsInternal.mLastStoreTime > 0
                    && System.currentTimeMillis()
                                    - customizedBinderCallsStatsInternal.mLastStoreTime
                            < 55000) {
                Slog.i(
                        "CustomizedBinderCallsStatsInternal",
                        "1 minute has NOT pass since last binder stats.");
                return;
            }
            Slog.d("CustomizedBinderCallsStatsInternal", "reportCpuUsage() : " + i + "%");
            BackgroundThread.getHandler()
                    .post(
                            new Runnable() { // from class:
                                // com.android.server.CustomizedBinderCallsStatsInternal$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BinderCallsStats.HeavyBinderCallerInfo heaviestApplicationUid;
                                    final CustomizedBinderCallsStatsInternal
                                            customizedBinderCallsStatsInternal2 =
                                                    CustomizedBinderCallsStatsInternal.this;
                                    int i2 = i;
                                    customizedBinderCallsStatsInternal2.mBinderCallsStats.store(
                                            5, i2);
                                    customizedBinderCallsStatsInternal2.mLastStoreTime =
                                            System.currentTimeMillis();
                                    if (customizedBinderCallsStatsInternal2.mLastWriteTime == 0
                                            || System.currentTimeMillis()
                                                            - customizedBinderCallsStatsInternal2
                                                                    .mLastWriteTime
                                                    >= 1800000) {
                                        Slog.i(
                                                "CustomizedBinderCallsStatsInternal",
                                                "should write the current data!!");
                                        IoThread.getHandler()
                                                .post(
                                                        new Runnable() { // from class:
                                                            // com.android.server.CustomizedBinderCallsStatsInternal$$ExternalSyntheticLambda2
                                                            @Override // java.lang.Runnable
                                                            public final void run() {
                                                                CustomizedBinderCallsStatsInternal
                                                                        .this
                                                                        .mBinderCallsStats
                                                                        .writeToFile();
                                                            }
                                                        });
                                        customizedBinderCallsStatsInternal2.mLastWriteTime =
                                                System.currentTimeMillis();
                                    }
                                    if (i2 >= 20
                                            && (heaviestApplicationUid =
                                                            customizedBinderCallsStatsInternal2
                                                                    .mBinderCallsStats
                                                                    .getHeaviestApplicationUid(90))
                                                    != null) {
                                        StringBuilder sb =
                                                new StringBuilder("heavy binder caller : ");
                                        sb.append(heaviestApplicationUid.mPackageName);
                                        sb.append("(");
                                        CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0
                                                .m(
                                                        sb,
                                                        heaviestApplicationUid.mUid,
                                                        ")",
                                                        "CustomizedBinderCallsStatsInternal");
                                        if (customizedBinderCallsStatsInternal2.mLastNotifyTime == 0
                                                || System.currentTimeMillis()
                                                                - customizedBinderCallsStatsInternal2
                                                                        .mLastNotifyTime
                                                        >= BackupManagerConstants
                                                                .DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS) {
                                            if (customizedBinderCallsStatsInternal2.mAm == null) {
                                                customizedBinderCallsStatsInternal2.mAm =
                                                        (ActivityManagerInternal)
                                                                LocalServices.getService(
                                                                        ActivityManagerInternal
                                                                                .class);
                                            }
                                            ActivityManagerInternal activityManagerInternal =
                                                    customizedBinderCallsStatsInternal2.mAm;
                                            Intent intent =
                                                    new Intent(
                                                            "com.sec.android.sdhms.action.APP_ERROR");
                                            intent.putExtra(
                                                    "pkgName", heaviestApplicationUid.mPackageName);
                                            intent.putExtra("userId", 0);
                                            intent.putExtra("uid", heaviestApplicationUid.mUid);
                                            intent.putExtra("type", "excessive_binder");
                                            intent.setPackage("com.sec.android.sdhms");
                                            activityManagerInternal.broadcastIntent(
                                                    intent,
                                                    (IIntentReceiver) null,
                                                    (String[]) null,
                                                    false,
                                                    0,
                                                    (int[]) null,
                                                    (BiFunction) null,
                                                    (Bundle) null);
                                            SemHqmManager semHqmManager =
                                                    (SemHqmManager)
                                                            customizedBinderCallsStatsInternal2
                                                                    .mContext.getSystemService(
                                                                    "HqmManagerService");
                                            if (semHqmManager != null) {
                                                StringBuilder sb2 = new StringBuilder();
                                                sb2.append(
                                                        "\"PKG\":\""
                                                                + heaviestApplicationUid
                                                                        .mPackageName
                                                                + "\",");
                                                sb2.append("\"SSCPU\":\"" + i2 + "\",");
                                                sb2.append(
                                                        "\"RATIO\":\""
                                                                + String.format(
                                                                        "%.2f",
                                                                        Float.valueOf(
                                                                                heaviestApplicationUid
                                                                                        .mRatio))
                                                                + "\",");
                                                int length =
                                                        heaviestApplicationUid.mExtraInfo.length()
                                                                + sb2.length();
                                                if (length + 10 > 1000) {
                                                    sb2.append(
                                                            "\"EXTRA\":\""
                                                                    + heaviestApplicationUid
                                                                            .mExtraInfo.substring(
                                                                            0,
                                                                            heaviestApplicationUid
                                                                                            .mExtraInfo
                                                                                            .length()
                                                                                    - (length
                                                                                            - 990))
                                                                    + "\"");
                                                } else {
                                                    sb2.append(
                                                            "\"EXTRA\":\""
                                                                    + heaviestApplicationUid
                                                                            .mExtraInfo
                                                                    + "\"");
                                                }
                                                Slog.i(
                                                        "CustomizedBinderCallsStatsInternal",
                                                        "Data is : " + sb2.toString());
                                                if (!semHqmManager.sendHWParamToHQM(
                                                        0,
                                                        "AP",
                                                        "HBCA",
                                                        "sm",
                                                        "0.0",
                                                        "",
                                                        "",
                                                        sb2.toString(),
                                                        "")) {
                                                    Slog.e(
                                                            "CustomizedBinderCallsStatsInternal",
                                                            "Failed to send anomaly application"
                                                                + " info. to HWParam");
                                                }
                                            } else {
                                                Slog.e(
                                                        "CustomizedBinderCallsStatsInternal",
                                                        "Cannot get HqmManagerService !!!");
                                            }
                                            customizedBinderCallsStatsInternal2.mLastNotifyTime =
                                                    System.currentTimeMillis();
                                        }
                                    }
                                    if (customizedBinderCallsStatsInternal2.mBinderCallsStats
                                            .isNeededResetData()) {
                                        Slog.i(
                                                "CustomizedBinderCallsStatsInternal",
                                                "Clear all data");
                                        customizedBinderCallsStatsInternal2.mBinderCallsStats.reset(
                                                new boolean[] {true});
                                    }
                                }
                            });
        }

        public void reportProcessDied(int i, int i2, String str) {
            this.mCustomizedBinderCallsStatsInternal.mBinderCallsStats.reportProcessDied(
                    i, i2, str);
        }

        public void shutdown() {
            this.mCustomizedBinderCallsStatsInternal.mBinderCallsStats.writeToFile();
        }
    }

    public final class LifeCycle extends SystemService {
        public BinderCallsStats mBinderCallsStats;
        public BinderCallsStatsService mService;
        public AuthorizedWorkSourceProvider mWorkSourceProvider;

        public LifeCycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            if (500 != i) {
                if (1000 == i) {
                    IoThread.getHandler()
                            .post(
                                    new Runnable() { // from class:
                                                     // com.android.server.BinderCallsStatsService$LifeCycle$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            BinderCallsStatsService.LifeCycle.this.mBinderCallsStats
                                                    .init();
                                        }
                                    });
                    return;
                }
                return;
            }
            this.mBinderCallsStats.setDeviceState(
                    (CachedDeviceState.Readonly) getLocalService(CachedDeviceState.Readonly.class));
            this.mBinderCallsStats.enablePackageStats(true);
            if (!Flags.disableSystemServicePowerAttr()) {
                final BatteryStatsInternal batteryStatsInternal =
                        (BatteryStatsInternal) getLocalService(BatteryStatsInternal.class);
                this.mBinderCallsStats.setCallStatsObserver(
                        new BinderInternal
                                .CallStatsObserver() { // from class:
                                                       // com.android.server.BinderCallsStatsService.LifeCycle.1
                            public final void noteBinderThreadNativeIds(int[] iArr) {
                                BatteryStatsInternal.this.noteBinderThreadNativeIds(iArr);
                            }

                            public final void noteCallStats(int i2, long j, Collection collection) {
                                BatteryStatsInternal.this.noteBinderCallStats(i2, j, collection);
                            }
                        });
            }
            AuthorizedWorkSourceProvider authorizedWorkSourceProvider = this.mWorkSourceProvider;
            Context context = getContext();
            authorizedWorkSourceProvider.getClass();
            ArraySet arraySet = new ArraySet();
            arraySet.add(Integer.valueOf(UserHandle.getAppId(Process.myUid())));
            PackageManager packageManager = context.getPackageManager();
            List<PackageInfo> packagesHoldingPermissions =
                    packageManager.getPackagesHoldingPermissions(
                            new String[] {"android.permission.UPDATE_DEVICE_STATS"}, 786432);
            int size = packagesHoldingPermissions.size();
            for (int i2 = 0; i2 < size; i2++) {
                PackageInfo packageInfo = packagesHoldingPermissions.get(i2);
                try {
                    arraySet.add(
                            Integer.valueOf(
                                    UserHandle.getAppId(
                                            packageManager.getPackageUid(
                                                    packageInfo.packageName, 786432))));
                } catch (PackageManager.NameNotFoundException e) {
                    Slog.e(
                            "BinderCallsStatsService",
                            "Cannot find uid for package name " + packageInfo.packageName,
                            e);
                }
            }
            authorizedWorkSourceProvider.mAppIdTrustlist = arraySet;
            BinderCallsStatsService binderCallsStatsService = this.mService;
            Context context2 = getContext();
            binderCallsStatsService.getClass();
            new SettingsObserver(
                    context2,
                    binderCallsStatsService.mBinderCallsStats,
                    binderCallsStatsService.mWorkSourceProvider);
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            this.mBinderCallsStats = new BinderCallsStats(new BinderCallsStats.Injector());
            AuthorizedWorkSourceProvider authorizedWorkSourceProvider =
                    new AuthorizedWorkSourceProvider();
            authorizedWorkSourceProvider.mAppIdTrustlist = new ArraySet();
            this.mWorkSourceProvider = authorizedWorkSourceProvider;
            this.mService =
                    new BinderCallsStatsService(this.mBinderCallsStats, this.mWorkSourceProvider);
            publishLocalService(Internal.class, new Internal(this.mBinderCallsStats, getContext()));
            publishBinderService("binder_calls_stats", this.mService);
            if (SystemProperties.getBoolean("persist.sys.binder_calls_detailed_tracking", false)) {
                Slog.i(
                        "BinderCallsStatsService",
                        "Enabled CPU usage tracking for binder calls. Controlled by"
                            + " persist.sys.binder_calls_detailed_tracking or via dumpsys"
                            + " binder_calls_stats --enable-detailed-tracking");
                this.mBinderCallsStats.setDetailedTracking(true);
            }
        }
    }

    public final class SettingsObserver extends ContentObserver {
        public final BinderCallsStats mBinderCallsStats;
        public final Context mContext;
        public boolean mEnabled;
        public final KeyValueListParser mParser;
        public final Uri mUri;
        public final AuthorizedWorkSourceProvider mWorkSourceProvider;

        public SettingsObserver(
                Context context,
                BinderCallsStats binderCallsStats,
                AuthorizedWorkSourceProvider authorizedWorkSourceProvider) {
            super(BackgroundThread.getHandler());
            Uri uriFor = Settings.Global.getUriFor("binder_calls_stats");
            this.mUri = uriFor;
            this.mParser = new KeyValueListParser(',');
            this.mContext = context;
            context.getContentResolver().registerContentObserver(uriFor, false, this, 0);
            this.mBinderCallsStats = binderCallsStats;
            this.mWorkSourceProvider = authorizedWorkSourceProvider;
            onChange();
        }

        public final void onChange() {
            if (SystemProperties.get("persist.sys.binder_calls_detailed_tracking").isEmpty()) {
                try {
                    this.mParser.setString(
                            Settings.Global.getString(
                                    this.mContext.getContentResolver(), "binder_calls_stats"));
                } catch (IllegalArgumentException e) {
                    Slog.e("BinderCallsStatsService", "Bad binder call stats settings", e);
                }
                this.mBinderCallsStats.setDetailedTracking(
                        this.mParser.getBoolean("detailed_tracking", true));
                this.mBinderCallsStats.setSamplingInterval(
                        this.mParser.getInt("sampling_interval", 1000));
                this.mBinderCallsStats.setMaxBinderCallStats(
                        this.mParser.getInt("max_call_stats_count", NetworkConstants.ETHER_MTU));
                this.mBinderCallsStats.setTrackScreenInteractive(
                        this.mParser.getBoolean("track_screen_state", false));
                this.mBinderCallsStats.setTrackDirectCallerUid(
                        this.mParser.getBoolean("track_calling_uid", true));
                this.mBinderCallsStats.setIgnoreBatteryStatus(
                        this.mParser.getBoolean("ignore_battery_status", false));
                this.mBinderCallsStats.setShardingModulo(this.mParser.getInt("sharding_modulo", 1));
                this.mBinderCallsStats.setCollectLatencyData(
                        this.mParser.getBoolean("collect_latency_data", true));
                BinderCallsStats.SettingsObserver.configureLatencyObserver(
                        this.mParser, this.mBinderCallsStats.getLatencyObserver());
                boolean z = this.mParser.getBoolean("enabled", true);
                if (this.mEnabled != z) {
                    if (z) {
                        Binder.setObserver(this.mBinderCallsStats);
                        Binder.setProxyTransactListener(
                                new Binder.PropagateWorkSourceTransactListener());
                        Binder.setWorkSourceProvider(this.mWorkSourceProvider);
                    } else {
                        Binder.setObserver(null);
                        Binder.setProxyTransactListener(null);
                        Binder.setWorkSourceProvider(
                                new BinderCallsStatsService$SettingsObserver$$ExternalSyntheticLambda0());
                    }
                    this.mEnabled = z;
                    this.mBinderCallsStats.reset(new boolean[0]);
                    this.mBinderCallsStats.setAddDebugEntries(z);
                    this.mBinderCallsStats.getLatencyObserver().reset();
                }
            }
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            if (this.mUri.equals(uri)) {
                onChange();
            }
        }
    }

    public BinderCallsStatsService(
            BinderCallsStats binderCallsStats,
            AuthorizedWorkSourceProvider authorizedWorkSourceProvider) {
        this.mBinderCallsStats = binderCallsStats;
        this.mWorkSourceProvider = authorizedWorkSourceProvider;
    }

    @Override // android.os.Binder
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpAndUsageStatsPermission(
                ActivityThread.currentApplication(), "binder_calls_stats", printWriter)) {
            boolean z = false;
            if (strArr != null) {
                boolean z2 = false;
                for (String str : strArr) {
                    if ("-a".equals(str)) {
                        z2 = true;
                    } else if ("-h".equals(str)) {
                        BatteryService$$ExternalSyntheticOutline0.m(
                                printWriter,
                                "dumpsys binder_calls_stats options:",
                                "  -a: Verbose",
                                "  --work-source-uid <UID>: Dump binder calls from the UID",
                                "  --set-cpu-threshold <threshold>: Set new CPU threshold");
                        printWriter.println(
                                "  --set-sampling <sampling value>: Set new sampling ratio");
                        printWriter.println(
                                "  --set-limit <limit value>: Set new limit value(default 1500)");
                        return;
                    } else if ("--work-source-uid".equals(str)) {
                        BatteryService$$ExternalSyntheticOutline0.m(
                                printWriter,
                                "Currently SEC debugging feature is enabled.",
                                "It collects the incoming binder transactions depending on sampling"
                                    + " ratio per package.",
                                "And it clears the collected binder stats every 1 or 5 minutes.",
                                "So it can be meaningless to invoke dump() with"
                                    + " '--work-source-uid'.");
                        return;
                    }
                }
                if (strArr.length > 0
                        && new BinderCallsStatsShellCommand(printWriter)
                                        .exec(
                                                this,
                                                (FileDescriptor) null,
                                                FileDescriptor.out,
                                                FileDescriptor.err,
                                                strArr)
                                == 0) {
                    return;
                } else {
                    z = z2;
                }
            }
            this.mBinderCallsStats.dump(printWriter, AppIdToPackageMap.getSnapshot(), -1, z);
            this.mBinderCallsStats.dumpStats(printWriter);
        }
    }

    public final int handleShellCommand(
            ParcelFileDescriptor parcelFileDescriptor,
            ParcelFileDescriptor parcelFileDescriptor2,
            ParcelFileDescriptor parcelFileDescriptor3,
            String[] strArr) {
        BinderCallsStatsShellCommand binderCallsStatsShellCommand =
                new BinderCallsStatsShellCommand(null);
        int exec =
                binderCallsStatsShellCommand.exec(
                        this,
                        parcelFileDescriptor.getFileDescriptor(),
                        parcelFileDescriptor2.getFileDescriptor(),
                        parcelFileDescriptor3.getFileDescriptor(),
                        strArr);
        if (exec != 0) {
            binderCallsStatsShellCommand.onHelp();
        }
        return exec;
    }
}
