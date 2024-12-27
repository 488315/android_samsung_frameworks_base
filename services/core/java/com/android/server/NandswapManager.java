package com.android.server;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.FileUtils;
import android.os.Process;
import android.os.SemHqmManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Slog;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.input.KeyboardMetricsCollector;

import com.samsung.android.knoxguard.service.utils.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public final class NandswapManager extends JobService {
    public static final boolean SUPPORT_RAM_EXPAND_SWITCH;
    public static final AnonymousClass1 intentReceiver;
    public static final ComponentName sNandswapManager =
            new ComponentName("android", NandswapManager.class.getName());
    public static Context mContext = null;
    public static NandswapClient mClient = null;
    public static NandSwapBigdataManager mNandBigData = null;

    /* renamed from: com.android.server.NandswapManager$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            boolean z;
            int i;
            String action = intent.getAction();
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    "received action: ", action, "NandswapManager");
            try {
                if ("com.sec.android.intent.action.HQM_UPDATE_REQ".equals(action)) {
                    if (NandswapManager.mNandBigData != null) {
                        NandSwapBigdataManager.m77$$Nest$smuploadBigdataToHQM();
                        return;
                    }
                    return;
                }
                if (!"android.intent.action.ACTION_SHUTDOWN".equals(action)
                        && !"android.intent.action.REBOOT".equals(action)) {
                    if ("com.samsung.intent.action.LAZY_BOOT_COMPLETE".equals(action)) {
                        Slog.d(
                                "NandswapManager",
                                "support ramExpandSwitch: "
                                        + NandswapManager.SUPPORT_RAM_EXPAND_SWITCH
                                        + " for "
                                        + Build.VERSION.SEM_PLATFORM_INT);
                        int i2 =
                                Settings.Global.getInt(
                                        context.getContentResolver(), "ram_expand_size", -1);
                        String string =
                                Settings.Global.getString(
                                        context.getContentResolver(), "ram_expand_size_list");
                        if (i2 == -1) {
                            Slog.d("NandswapManager", "ram_expand_size was not set");
                            z = true;
                        } else {
                            z = false;
                        }
                        if (string == null) {
                            Slog.d("NandswapManager", "ram_expand_size_list was not set");
                            z = true;
                        }
                        if (!NandswapManager.m74$$Nest$smisBackingDevSet()) {
                            Slog.d("NandswapManager", "zram backing_dev is not set");
                            z = false;
                        }
                        if (z) {
                            try {
                                i =
                                        (int)
                                                (((new File("/data/per_boot/zram_swap").length()
                                                                        / 1024)
                                                                / 1024)
                                                        * 4);
                            } catch (Exception unused) {
                                Slog.e("NandswapManager", "error on file length");
                                i = 0;
                            }
                            NandswapManager.m76$$Nest$smsetExpandSizeAndList(context, i);
                        }
                        if (SystemProperties.getBoolean("ro.sys.kernelmemory.gmr.enabled", false)
                                && "kgsl"
                                        .equals(
                                                SystemProperties.get(
                                                        "ro.sys.kernelmemory.gmr.vendor_plugin",
                                                        ""))) {
                            NandswapManager.m73$$Nest$smgetRamExpandSizePersistProp();
                            try {
                                FileUtils.stringToFile(
                                        new File("/sys/class/kgsl/kgsl/max_reclaim_limit"),
                                        String.valueOf(128000));
                                Slog.i(
                                        "NandswapManager",
                                        "GMR: Success write max reclaim limit: 128000");
                                return;
                            } catch (IOException unused2) {
                                Slog.e(
                                        "NandswapManager",
                                        "GMR: Failed to write max recaim limit to"
                                            + " /sys/class/kgsl/kgsl/max_reclaim_limit");
                                return;
                            }
                        }
                        return;
                    }
                    return;
                }
                ComponentName componentName = NandswapManager.sNandswapManager;
                if (Settings.Global.getInt(context.getContentResolver(), "ram_expand_size", -1)
                                == -1
                        && NandswapManager.m74$$Nest$smisBackingDevSet()) {
                    NandswapManager.putRamExpandSize(
                            context, NandswapManager.getDefaultRamExpandSize());
                }
                int m73$$Nest$smgetRamExpandSizePersistProp =
                        NandswapManager.m73$$Nest$smgetRamExpandSizePersistProp();
                if (m73$$Nest$smgetRamExpandSizePersistProp == -1
                        || m73$$Nest$smgetRamExpandSizePersistProp
                                == Settings.Global.getInt(
                                        context.getContentResolver(), "ram_expand_size", -1)) {
                    NandswapManager.m75$$Nest$smsaveClientsBigdataInfoInReboot();
                    return;
                }
                NandswapClient nandswapClient = NandswapManager.mClient;
                if (nandswapClient.state == 1) {
                    nandswapClient.initBigdataInfoProp();
                }
                Slog.i("NandswapManager", "Ramplus Option is changed. clear bigdata's info.");
            } catch (Exception e) {
                NandswapManager$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("intent exception msg : "), "NandswapManager");
            }
        }
    }

    public final class NandSwapBigdataManager {
        public static final String[] bigdataJsonFormat = {
            "count_average",
            "size_average",
            "reads",
            "writes",
            "objcnt",
            "max_count",
            "max_size",
            "ppr_count_average",
            "ppr_size_average",
            "ppr_reads",
            "ppr_writes",
            "ppr_objcnt",
            "ppr_max_count",
            "ppr_max_size",
            "objreads",
            "objwrites",
            "gpu_total_mem",
            "gpu_reclaimed_mem",
            "swap_used"
        };
        public static SemHqmManager semHqmManager;

        /* renamed from: -$$Nest$smuploadBigdataToHQM, reason: not valid java name */
        public static void m77$$Nest$smuploadBigdataToHQM() {
            String format;
            if (semHqmManager == null) {
                Slog.e("NandswapManager", "Bigdata semHqmManager isn't initialized...");
                return;
            }
            if (NandswapManager.mClient.supportBigdataState) {
                Slog.i("NandswapManager", "Upload Bigdata...");
                String str = "";
                HashMap bdStat = NandswapManager.mClient.getBdStat();
                HashMap bigdataInfoProp = NandswapManager.mClient.getBigdataInfoProp();
                HashMap hashMap = new HashMap();
                try {
                    BufferedReader bufferedReader =
                            new BufferedReader(new FileReader("/proc/meminfo"));
                    while (true) {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            int indexOf = readLine.indexOf(":");
                            if (indexOf > 0) {
                                hashMap.put(
                                        readLine.substring(0, indexOf),
                                        Integer.valueOf(
                                                Integer.parseInt(
                                                        readLine.substring(indexOf)
                                                                .replaceAll("\\D+", ""))));
                            }
                        } finally {
                        }
                    }
                    bufferedReader.close();
                } catch (Exception unused) {
                    Slog.e("NandswapManager", "Failed to read stats from /proc/meminfo");
                    hashMap.clear();
                }
                if (bigdataInfoProp.isEmpty() || bdStat.isEmpty() || hashMap.isEmpty()) {
                    Locale locale = Locale.US;
                    NandswapManager$$ExternalSyntheticOutline0.m(
                            ArrayUtils$$ExternalSyntheticOutline0.m(
                                    bigdataInfoProp.size(),
                                    bdStat.size(),
                                    "persist.sys.zram0.bigdata_info(",
                                    ") or bd_stat(",
                                    ") or meminfo("),
                            hashMap.size(),
                            ") is empty...",
                            "NandswapManager");
                } else {
                    String str2 =
                            "\"option\":\""
                                    + NandswapManager.m73$$Nest$smgetRamExpandSizePersistProp()
                                    + "\",";
                    String[] strArr = bigdataJsonFormat;
                    int i = 0;
                    while (true) {
                        if (i < 19) {
                            String str3 = strArr[i];
                            if (!str3.equals("count_average")) {
                                if (!str3.equals("size_average")) {
                                    if (!str3.equals("ppr_count_average")) {
                                        if (!str3.equals("ppr_size_average")) {
                                            if (!str3.equals("objcnt")
                                                    && !str3.equals("ppr_objcnt")) {
                                                if (!str3.equals("max_count")
                                                        && !str3.equals("max_size")
                                                        && !str3.equals("ppr_max_count")
                                                        && !str3.equals("ppr_max_size")) {
                                                    if (!str3.equals("reads")) {
                                                        if (!str3.equals("writes")) {
                                                            if (!str3.equals("ppr_reads")) {
                                                                if (!str3.equals("ppr_writes")) {
                                                                    if (!str3.equals("objreads")) {
                                                                        if (!str3.equals(
                                                                                "objwrites")) {
                                                                            if (!str3.equals(
                                                                                    "gpu_total_mem")) {
                                                                                if (!str3.equals(
                                                                                        "gpu_reclaimed_mem")) {
                                                                                    if (!str3
                                                                                            .equals(
                                                                                                    "swap_used")) {
                                                                                        break;
                                                                                    } else {
                                                                                        format =
                                                                                                String
                                                                                                        .valueOf(
                                                                                                                ((Integer)
                                                                                                                                        hashMap
                                                                                                                                                .get(
                                                                                                                                                        "SwapTotal"))
                                                                                                                                .intValue()
                                                                                                                        - ((Integer)
                                                                                                                                        hashMap
                                                                                                                                                .get(
                                                                                                                                                        "SwapFree"))
                                                                                                                                .intValue());
                                                                                    }
                                                                                } else {
                                                                                    if (hashMap.get(
                                                                                                    "KgslReclaimed")
                                                                                            != null) {
                                                                                        format =
                                                                                                String
                                                                                                        .valueOf(
                                                                                                                hashMap
                                                                                                                        .get(
                                                                                                                                "KgslReclaimed"));
                                                                                    }
                                                                                    format = "-1";
                                                                                }
                                                                            } else {
                                                                                if (hashMap.get(
                                                                                                "KgslShmemUsage")
                                                                                        != null) {
                                                                                    format =
                                                                                            String
                                                                                                    .valueOf(
                                                                                                            hashMap
                                                                                                                    .get(
                                                                                                                            "KgslShmemUsage"));
                                                                                }
                                                                                format = "-1";
                                                                            }
                                                                        } else {
                                                                            Locale locale2 =
                                                                                    Locale.US;
                                                                            int intValue =
                                                                                    (((Integer)
                                                                                                                    bdStat
                                                                                                                            .get(
                                                                                                                                    str3))
                                                                                                            .intValue()
                                                                                                    - NandswapManager
                                                                                                            .mClient
                                                                                                            .normalStat
                                                                                                            .objWrites)
                                                                                            + ((Integer)
                                                                                                            bigdataInfoProp
                                                                                                                    .get(
                                                                                                                            str3))
                                                                                                    .intValue();
                                                                            StringBuilder sb =
                                                                                    new StringBuilder();
                                                                            sb.append(intValue);
                                                                            format = sb.toString();
                                                                        }
                                                                    } else {
                                                                        Locale locale3 = Locale.US;
                                                                        int intValue2 =
                                                                                (((Integer)
                                                                                                                bdStat
                                                                                                                        .get(
                                                                                                                                str3))
                                                                                                        .intValue()
                                                                                                - NandswapManager
                                                                                                        .mClient
                                                                                                        .normalStat
                                                                                                        .objReads)
                                                                                        + ((Integer)
                                                                                                        bigdataInfoProp
                                                                                                                .get(
                                                                                                                        str3))
                                                                                                .intValue();
                                                                        StringBuilder sb2 =
                                                                                new StringBuilder();
                                                                        sb2.append(intValue2);
                                                                        format = sb2.toString();
                                                                    }
                                                                } else {
                                                                    Locale locale4 = Locale.US;
                                                                    int intValue3 =
                                                                            (((Integer)
                                                                                                            bdStat
                                                                                                                    .get(
                                                                                                                            str3))
                                                                                                    .intValue()
                                                                                            - NandswapManager
                                                                                                    .mClient
                                                                                                    .pprStat
                                                                                                    .writes)
                                                                                    + ((Integer)
                                                                                                    bigdataInfoProp
                                                                                                            .get(
                                                                                                                    str3))
                                                                                            .intValue();
                                                                    StringBuilder sb3 =
                                                                            new StringBuilder();
                                                                    sb3.append(intValue3);
                                                                    format = sb3.toString();
                                                                }
                                                            } else {
                                                                Locale locale5 = Locale.US;
                                                                int intValue4 =
                                                                        (((Integer)
                                                                                                        bdStat
                                                                                                                .get(
                                                                                                                        str3))
                                                                                                .intValue()
                                                                                        - NandswapManager
                                                                                                .mClient
                                                                                                .pprStat
                                                                                                .reads)
                                                                                + ((Integer)
                                                                                                bigdataInfoProp
                                                                                                        .get(
                                                                                                                str3))
                                                                                        .intValue();
                                                                StringBuilder sb4 =
                                                                        new StringBuilder();
                                                                sb4.append(intValue4);
                                                                format = sb4.toString();
                                                            }
                                                        } else {
                                                            Locale locale6 = Locale.US;
                                                            int intValue5 =
                                                                    (((Integer) bdStat.get(str3))
                                                                                            .intValue()
                                                                                    - NandswapManager
                                                                                            .mClient
                                                                                            .normalStat
                                                                                            .writes)
                                                                            + ((Integer)
                                                                                            bigdataInfoProp
                                                                                                    .get(
                                                                                                            str3))
                                                                                    .intValue();
                                                            StringBuilder sb5 = new StringBuilder();
                                                            sb5.append(intValue5);
                                                            format = sb5.toString();
                                                        }
                                                    } else {
                                                        Locale locale7 = Locale.US;
                                                        int intValue6 =
                                                                (((Integer) bdStat.get(str3))
                                                                                        .intValue()
                                                                                - NandswapManager
                                                                                        .mClient
                                                                                        .normalStat
                                                                                        .reads)
                                                                        + ((Integer)
                                                                                        bigdataInfoProp
                                                                                                .get(
                                                                                                        str3))
                                                                                .intValue();
                                                        StringBuilder sb6 = new StringBuilder();
                                                        sb6.append(intValue6);
                                                        format = sb6.toString();
                                                    }
                                                } else {
                                                    Locale locale8 = Locale.US;
                                                    int max =
                                                            Math.max(
                                                                    ((Integer)
                                                                                    bigdataInfoProp
                                                                                            .get(
                                                                                                    str3))
                                                                            .intValue(),
                                                                    ((Integer) bdStat.get(str3))
                                                                            .intValue());
                                                    StringBuilder sb7 = new StringBuilder();
                                                    sb7.append(max);
                                                    format = sb7.toString();
                                                }
                                            } else {
                                                format =
                                                        String.format(
                                                                Locale.US, "%d", bdStat.get(str3));
                                            }
                                        } else {
                                            Locale locale9 = Locale.US;
                                            NandswapClient nandswapClient = NandswapManager.mClient;
                                            format =
                                                    String.format(
                                                            locale9,
                                                            "%.3f",
                                                            Double.valueOf(
                                                                    nandswapClient
                                                                                    .supportBigdataState
                                                                            ? nandswapClient.pprStat
                                                                                    .calcAverage(2)
                                                                            : 0.0d));
                                        }
                                    } else {
                                        Locale locale10 = Locale.US;
                                        NandswapClient nandswapClient2 = NandswapManager.mClient;
                                        format =
                                                String.format(
                                                        locale10,
                                                        "%.3f",
                                                        Double.valueOf(
                                                                nandswapClient2.supportBigdataState
                                                                        ? nandswapClient2.pprStat
                                                                                .calcAverage(1)
                                                                        : 0.0d));
                                    }
                                } else {
                                    Locale locale11 = Locale.US;
                                    NandswapClient nandswapClient3 = NandswapManager.mClient;
                                    format =
                                            String.format(
                                                    locale11,
                                                    "%.3f",
                                                    Double.valueOf(
                                                            nandswapClient3.supportBigdataState
                                                                    ? nandswapClient3.normalStat
                                                                            .calcAverage(2)
                                                                    : 0.0d));
                                }
                            } else {
                                Locale locale12 = Locale.US;
                                NandswapClient nandswapClient4 = NandswapManager.mClient;
                                format =
                                        String.format(
                                                locale12,
                                                "%.3f",
                                                Double.valueOf(
                                                        nandswapClient4.supportBigdataState
                                                                ? nandswapClient4.normalStat
                                                                        .calcAverage(1)
                                                                : 0.0d));
                            }
                            str2 = str2 + "\"" + str3 + "\":\"" + format + "\",";
                            i++;
                        } else {
                            if (str2.length() != 0) {
                                str2 =
                                        DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0
                                                .m(1, 0, str2);
                            }
                            str = str2;
                        }
                    }
                }
                semHqmManager.sendHWParamToHQM(0, "AP", "NSST", "sm", "0.0", "sec", "", str, "");
                Slog.i("NandswapManager", "Initialize bigdata information...");
                NandswapManager.mClient.initBigdataInfoProp();
                NandswapClient nandswapClient5 = NandswapManager.mClient;
                if (nandswapClient5.supportBigdataState) {
                    ProcessingManager processingManager = nandswapClient5.normalStat;
                    processingManager.countList.clear();
                    processingManager.sizeList.clear();
                    ProcessingManager processingManager2 = nandswapClient5.pprStat;
                    processingManager2.countList.clear();
                    processingManager2.sizeList.clear();
                }
                NandswapManager.mClient.setPreviousInfo();
                NandswapManager.mClient.getClass();
                try {
                    FileUtils.stringToFile(new File("/sys/block/zram0/bd_stat"), String.valueOf(0));
                } catch (IOException unused2) {
                    Slog.e(
                            "NandswapManager",
                            "Failed to reset stats from /sys/block/zram0/bd_stat");
                }
            }
        }
    }

    public final class NandswapClient {
        public final int state;
        public final boolean supportBigdataState;
        public int dailyQuota = 0;
        public int dailyQuotaLimit = 0;
        public final HashMap bdStatMap = new HashMap();
        public final HashMap bigdataPersistPropMap = new HashMap();
        public final ProcessingManager normalStat = new ProcessingManager();
        public final ProcessingManager pprStat = new ProcessingManager();

        /* JADX WARN: Can't wrap try/catch for region: R(10:0|1|(2:2|3)|(7:8|9|10|11|(1:13)(1:17)|14|15)|20|10|11|(0)(0)|14|15) */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x0081, code lost:

           android.util.Slog.e("NandswapManager", "Failed to read stats from /sys/block/zram0/bd_stat");
        */
        /* JADX WARN: Removed duplicated region for block: B:13:0x007b A[Catch: IOException -> 0x0081, TryCatch #1 {IOException -> 0x0081, blocks: (B:11:0x005f, B:13:0x007b, B:17:0x007e), top: B:10:0x005f }] */
        /* JADX WARN: Removed duplicated region for block: B:17:0x007e A[Catch: IOException -> 0x0081, TRY_LEAVE, TryCatch #1 {IOException -> 0x0081, blocks: (B:11:0x005f, B:13:0x007b, B:17:0x007e), top: B:10:0x005f }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public NandswapClient() {
            /*
                r7 = this;
                java.lang.String r0 = ""
                java.lang.String r1 = "NandswapManager"
                r7.<init>()
                r2 = 0
                r7.dailyQuota = r2
                r7.dailyQuotaLimit = r2
                r3 = -1
                r7.state = r3
                r7.supportBigdataState = r2
                java.util.HashMap r3 = new java.util.HashMap
                r3.<init>()
                r7.bdStatMap = r3
                java.util.HashMap r3 = new java.util.HashMap
                r3.<init>()
                r7.bigdataPersistPropMap = r3
                com.android.server.NandswapManager$ProcessingManager r3 = new com.android.server.NandswapManager$ProcessingManager
                r3.<init>()
                r7.normalStat = r3
                com.android.server.NandswapManager$ProcessingManager r3 = new com.android.server.NandswapManager$ProcessingManager
                r3.<init>()
                r7.pprStat = r3
                r3 = 1
                java.io.File r4 = new java.io.File     // Catch: java.io.IOException -> L58
                java.lang.String r5 = "/sys/block/zram0/backing_dev"
                r4.<init>(r5)     // Catch: java.io.IOException -> L58
                r5 = 128(0x80, float:1.794E-43)
                java.lang.String r5 = android.os.FileUtils.readTextFile(r4, r5, r0)     // Catch: java.io.IOException -> L58
                java.lang.String r6 = "none"
                java.lang.String r5 = r5.trim()     // Catch: java.io.IOException -> L58
                boolean r5 = r6.equals(r5)     // Catch: java.io.IOException -> L58
                if (r5 == 0) goto L55
                boolean r4 = r4.exists()     // Catch: java.io.IOException -> L58
                if (r4 == 0) goto L4f
                goto L55
            L4f:
                java.lang.String r4 = "Writeback device is not set"
                android.util.Slog.w(r1, r4)     // Catch: java.io.IOException -> L58
                goto L5d
            L55:
                r7.state = r3
                goto L5f
            L58:
                java.lang.String r4 = "Writeback is not enabled on /sys/block/zram0/backing_dev"
                android.util.Slog.w(r1, r4)
            L5d:
                r7.state = r2
            L5f:
                java.io.File r4 = new java.io.File     // Catch: java.io.IOException -> L81
                java.lang.String r5 = "/sys/block/zram0/bd_stat"
                r4.<init>(r5)     // Catch: java.io.IOException -> L81
                r5 = 4096(0x1000, float:5.74E-42)
                java.lang.String r0 = android.os.FileUtils.readTextFile(r4, r5, r0)     // Catch: java.io.IOException -> L81
                java.lang.String r0 = r0.trim()     // Catch: java.io.IOException -> L81
                java.lang.String r4 = "\\s+"
                java.lang.String[] r0 = r0.split(r4)     // Catch: java.io.IOException -> L81
                int r0 = r0.length     // Catch: java.io.IOException -> L81
                r4 = 10
                if (r0 < r4) goto L7e
                r7.supportBigdataState = r3     // Catch: java.io.IOException -> L81
                goto L86
            L7e:
                r7.supportBigdataState = r2     // Catch: java.io.IOException -> L81
                goto L86
            L81:
                java.lang.String r0 = "Failed to read stats from /sys/block/zram0/bd_stat"
                android.util.Slog.e(r1, r0)
            L86:
                r7.setPreviousInfo()
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.NandswapManager.NandswapClient.<init>():void");
        }

        public static int getQuotaSysNode() {
            try {
                return Integer.parseInt(
                        FileUtils.readTextFile(
                                        new File("/sys/block/zram0/writeback_limit"), 128, "")
                                .trim());
            } catch (Exception unused) {
                Slog.e(
                        "NandswapManager",
                        "Failed to read quota from /sys/block/zram0/writeback_limit");
                return 0;
            }
        }

        public static void setQuotaSysNode(int i) {
            try {
                FileUtils.stringToFile(
                        new File("/sys/block/zram0/writeback_limit"), String.valueOf(i));
            } catch (IOException unused) {
                Slog.e(
                        "NandswapManager",
                        "Failed to write new quota to /sys/block/zram0/writeback_limit");
            }
        }

        public final HashMap getBdStat() {
            String[] strArr = {
                "expire",
                "count",
                "reads",
                "writes",
                "objcnt",
                "size",
                "max_count",
                "max_size",
                "ppr_count",
                "ppr_reads",
                "ppr_writes",
                "ppr_objcnt",
                "ppr_size",
                "ppr_max_count",
                "ppr_max_size",
                "objreads",
                "objwrites"
            };
            this.bdStatMap.clear();
            try {
                String[] split =
                        FileUtils.readTextFile(new File("/sys/block/zram0/bd_stat"), 4096, "")
                                .trim()
                                .split("\\s+");
                for (int i = 0; i < split.length; i++) {
                    this.bdStatMap.put(strArr[i], Integer.valueOf(Integer.parseInt(split[i])));
                }
            } catch (IOException unused) {
                Slog.e("NandswapManager", "Failed to read stats from /sys/block/zram0/bd_stat");
            }
            return this.bdStatMap;
        }

        public final HashMap getBigdataInfoProp() {
            String[] strArr = {
                "reads",
                "writes",
                "max_count",
                "max_size",
                "ppr_reads",
                "ppr_writes",
                "ppr_max_count",
                "ppr_max_size",
                "objreads",
                "objwrites"
            };
            this.bigdataPersistPropMap.clear();
            if (this.supportBigdataState) {
                String str =
                        SystemProperties.get(
                                "persist.sys.zram0.bigdata_info",
                                KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
                if (!str.equals(KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG)) {
                    String[] split = str.split(",");
                    for (int i = 0; i < 10; i++) {
                        try {
                            this.bigdataPersistPropMap.put(
                                    strArr[i], Integer.valueOf(Integer.parseInt(split[i])));
                        } catch (Exception e) {
                            Slog.d(
                                    "NandswapManager",
                                    "getBigdataInfo parseInt err - " + e.getMessage());
                            this.bigdataPersistPropMap.clear();
                            for (int i2 = 0; i2 < 10; i2++) {
                                this.bigdataPersistPropMap.put(strArr[i2], 0);
                            }
                        }
                    }
                }
            }
            return this.bigdataPersistPropMap;
        }

        public final void initBigdataInfoProp() {
            if (this.supportBigdataState) {
                try {
                    SystemProperties.set("persist.sys.zram0.bigdata_info", "0,0,0,0,0,0,0,0,0,0");
                } catch (Exception unused) {
                    Slog.e("NandswapManager", "Failed to init Bigdata Info...");
                }
            }
        }

        public final void setPreviousInfo() {
            if (this.supportBigdataState) {
                HashMap bdStat = getBdStat();
                int intValue = ((Integer) bdStat.get("reads")).intValue();
                int intValue2 = ((Integer) bdStat.get("writes")).intValue();
                int intValue3 = ((Integer) bdStat.get("objreads")).intValue();
                int intValue4 = ((Integer) bdStat.get("objwrites")).intValue();
                ProcessingManager processingManager = this.normalStat;
                processingManager.reads = intValue;
                processingManager.writes = intValue2;
                processingManager.objReads = intValue3;
                processingManager.objWrites = intValue4;
                int intValue5 = ((Integer) bdStat.get("ppr_reads")).intValue();
                int intValue6 = ((Integer) bdStat.get("ppr_writes")).intValue();
                int intValue7 = ((Integer) bdStat.get("objreads")).intValue();
                int intValue8 = ((Integer) bdStat.get("objwrites")).intValue();
                ProcessingManager processingManager2 = this.pprStat;
                processingManager2.reads = intValue5;
                processingManager2.writes = intValue6;
                processingManager2.objReads = intValue7;
                processingManager2.objWrites = intValue8;
            }
        }
    }

    public final class ProcessingManager {
        public final ArrayList countList = new ArrayList();
        public final ArrayList sizeList = new ArrayList();
        public int reads = 0;
        public int writes = 0;
        public int objReads = 0;
        public int objWrites = 0;

        public final double calcAverage(int i) {
            double d;
            double d2;
            if (i == 1) {
                d = this.countList.size();
                d2 = 0.0d;
                while (this.countList.iterator().hasNext()) {
                    d2 += ((Integer) r9.next()).intValue();
                }
            } else if (i == 2) {
                d = this.sizeList.size();
                d2 = 0.0d;
                while (this.sizeList.iterator().hasNext()) {
                    d2 += ((Integer) r9.next()).intValue();
                }
            } else {
                d = 0.0d;
                d2 = 0.0d;
            }
            if (d == 0.0d) {
                return 0.0d;
            }
            return d2 / d;
        }
    }

    /* renamed from: -$$Nest$smgetRamExpandSizePersistProp, reason: not valid java name */
    public static int m73$$Nest$smgetRamExpandSizePersistProp() {
        try {
            return SystemProperties.getInt("persist.sys.zram.ram_expand_size", -1);
        } catch (Exception unused) {
            Slog.e("NandswapManager", "error on get SystemProperties");
            return -1;
        }
    }

    /* renamed from: -$$Nest$smisBackingDevSet, reason: not valid java name */
    public static boolean m74$$Nest$smisBackingDevSet() {
        try {
            return !"none"
                    .equals(
                            FileUtils.readTextFile(
                                            new File("/sys/block/zram0/backing_dev"), 128, "")
                                    .trim());
        } catch (IOException unused) {
            Slog.w(
                    "NandswapManager",
                    "exception on checking backing_dev /sys/block/zram0/backing_dev");
            return false;
        }
    }

    /* renamed from: -$$Nest$smsaveClientsBigdataInfoInReboot, reason: not valid java name */
    public static void m75$$Nest$smsaveClientsBigdataInfoInReboot() {
        NandswapClient nandswapClient = mClient;
        if (nandswapClient.state == 1 && nandswapClient.supportBigdataState) {
            HashMap bigdataInfoProp = nandswapClient.getBigdataInfoProp();
            HashMap bdStat = nandswapClient.getBdStat();
            if (bigdataInfoProp.isEmpty() || bdStat.isEmpty()) {
                Locale locale = Locale.US;
                Slog.e(
                        "NandswapManager",
                        DualAppManagerService$$ExternalSyntheticOutline0.m(
                                bigdataInfoProp.size(),
                                bdStat.size(),
                                "persist.sys.zram0.bigdata_info(",
                                ") or bd_stat(",
                                ") is empty..."));
                return;
            }
            Locale locale2 = Locale.US;
            int intValue = ((Integer) bigdataInfoProp.get("reads")).intValue();
            int intValue2 = ((Integer) bdStat.get("reads")).intValue();
            ProcessingManager processingManager = nandswapClient.normalStat;
            StringBuilder m =
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            NandswapManager$$ExternalSyntheticOutline0.m(
                                    (intValue2 - processingManager.reads) + intValue, ","));
            m.append(
                    ((((Integer) bdStat.get("writes")).intValue() - processingManager.writes)
                                    + ((Integer) bigdataInfoProp.get("writes")).intValue())
                            + ",");
            StringBuilder m2 = BootReceiver$$ExternalSyntheticOutline0.m(m.toString());
            m2.append(
                    Math.max(
                                    ((Integer) bigdataInfoProp.get("max_count")).intValue(),
                                    ((Integer) bdStat.get("max_count")).intValue())
                            + ",");
            StringBuilder m3 = BootReceiver$$ExternalSyntheticOutline0.m(m2.toString());
            m3.append(
                    Math.max(
                                    ((Integer) bigdataInfoProp.get("max_size")).intValue(),
                                    ((Integer) bdStat.get("max_size")).intValue())
                            + ",");
            StringBuilder m4 = BootReceiver$$ExternalSyntheticOutline0.m(m3.toString());
            int intValue3 = ((Integer) bigdataInfoProp.get("ppr_reads")).intValue();
            int intValue4 = ((Integer) bdStat.get("ppr_reads")).intValue();
            ProcessingManager processingManager2 = nandswapClient.pprStat;
            m4.append(((intValue4 - processingManager2.reads) + intValue3) + ",");
            StringBuilder m5 = BootReceiver$$ExternalSyntheticOutline0.m(m4.toString());
            m5.append(
                    ((((Integer) bdStat.get("ppr_writes")).intValue() - processingManager2.writes)
                                    + ((Integer) bigdataInfoProp.get("ppr_writes")).intValue())
                            + ",");
            StringBuilder m6 = BootReceiver$$ExternalSyntheticOutline0.m(m5.toString());
            m6.append(
                    Math.max(
                                    ((Integer) bigdataInfoProp.get("ppr_max_count")).intValue(),
                                    ((Integer) bdStat.get("ppr_max_count")).intValue())
                            + ",");
            StringBuilder m7 = BootReceiver$$ExternalSyntheticOutline0.m(m6.toString());
            m7.append(
                    Math.max(
                                    ((Integer) bigdataInfoProp.get("ppr_max_size")).intValue(),
                                    ((Integer) bdStat.get("ppr_max_size")).intValue())
                            + ",");
            StringBuilder m8 = BootReceiver$$ExternalSyntheticOutline0.m(m7.toString());
            m8.append(
                    ((((Integer) bdStat.get("objreads")).intValue() - processingManager.objReads)
                                    + ((Integer) bigdataInfoProp.get("objreads")).intValue())
                            + ",");
            StringBuilder m9 = BootReceiver$$ExternalSyntheticOutline0.m(m8.toString());
            int intValue5 =
                    (((Integer) bdStat.get("objwrites")).intValue() - processingManager.objWrites)
                            + ((Integer) bigdataInfoProp.get("objwrites")).intValue();
            StringBuilder sb = new StringBuilder();
            sb.append(intValue5);
            m9.append(sb.toString());
            try {
                SystemProperties.set("persist.sys.zram0.bigdata_info", m9.toString());
                Slog.i("NandswapManager", "Saved Bigdata Info successfully!!");
            } catch (Exception unused) {
                Slog.e("NandswapManager", "Failed to saved Bigdata Info...");
            }
        }
    }

    /* renamed from: -$$Nest$smsetExpandSizeAndList, reason: not valid java name */
    public static void m76$$Nest$smsetExpandSizeAndList(Context context, int i) {
        boolean z;
        int storageSize = getStorageSize();
        boolean z2 = false;
        if (storageSize < 32) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    storageSize, "no ramExpandSwitch for low storage ", "NandswapManager");
            z = false;
        } else {
            z = true;
        }
        if (SUPPORT_RAM_EXPAND_SWITCH) {
            z2 = z;
        } else {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    new StringBuilder("no ramExpandSwitch for version "),
                    Build.VERSION.SEM_PLATFORM_INT,
                    "NandswapManager");
        }
        putRamExpandSize(context, i);
        setRamExpandSizePersistProp(i);
        if (z2) {
            String availSizeList = getAvailSizeList();
            Slog.d("NandswapManager", "ramExpandSizeMb: " + i + " avail: " + availSizeList);
            putRamExpandSizeList(context, availSizeList);
        }
    }

    static {
        SUPPORT_RAM_EXPAND_SWITCH = Build.VERSION.SEM_PLATFORM_INT >= 130100;
        intentReceiver = new AnonymousClass1();
    }

    public static String getAvailSizeList() {
        int totalMemory = (int) (Process.getTotalMemory() >> 30);
        return totalMemory < 4 ? "2,4" : totalMemory < 6 ? "2,4,6" : "2,4,6,8";
    }

    public static int getDefaultRamExpandSize() {
        int storageSize = getStorageSize();
        if (SystemProperties.getBoolean("ro.sys.kernelmemory.nandswap.higher_max_size", false)
                && storageSize >= 256) {
            return 8192;
        }
        if (storageSize > 32) {
            return 4096;
        }
        return storageSize == 32 ? 2048 : 0;
    }

    public static int getStorageSize() {
        int i;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/partitions"));
            try {
                String str = isUfs() ? ".*(sda|sdc)$" : ".*(mmcblk0)$";
                i = 0;
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    if (readLine.matches(str)) {
                        String[] split = readLine.trim().split("\\s+");
                        if (split.length == 4) {
                            i = Math.max(Integer.parseInt(split[2]), i);
                        }
                    }
                }
                bufferedReader.close();
            } finally {
            }
        } catch (Exception unused) {
            Slog.e("NandswapManager", "Failed to read storage size from /proc/partitions");
            i = 0;
        }
        if (i == 0) {
            return 0;
        }
        return 1 << (((int) (Math.log(i / 1048576) / Math.log(2.0d))) + 1);
    }

    public static void initNandswapClient() {
        int i;
        Slog.i("NandswapManager", "Init Nandswap Client");
        if (isRemainStorageLifeTime()) {
            HashMap hashMap = new HashMap();
            HashMap hashMap2 = new HashMap();
            int[] iArr = {0, 0, 8, 12};
            int[] iArr2 = {0, 1, 8, 12};
            int[] iArr3 = {16, 32, 64, 128};
            int storageSize = getStorageSize();
            Slog.i("NandswapManager", "This device is in-house model");
            for (int i2 = 0; i2 < 4; i2++) {
                hashMap.put(Integer.valueOf(iArr3[i2]), Integer.valueOf(iArr[i2]));
                hashMap2.put(Integer.valueOf(iArr3[i2]), Integer.valueOf(iArr2[i2]));
            }
            int i3 = storageSize <= 128 ? storageSize : 128;
            int i4 = 16 <= i3 ? i3 : 16;
            try {
                i =
                        isUfs()
                                ? ((Integer) hashMap.get(Integer.valueOf(i4))).intValue() * 262144
                                : ((Integer) hashMap2.get(Integer.valueOf(i4))).intValue() * 262144;
            } catch (NullPointerException unused) {
                Slog.w(
                        "NandswapManager",
                        "Storage size is not in Quota table! size:" + Integer.toString(i4));
                i = 262144;
            }
            if (SystemProperties.getInt("persist.sys.zram.daily_quota", -1) == -1) {
                SystemProperties.set("persist.sys.zram.daily_quota", Integer.toString(i));
                Slog.i("NandswapManager", "First boot, set daily quota...");
            }
            if (SystemProperties.getInt("persist.sys.zram.daily_quota_remain", -1) == -1) {
                SystemProperties.set("persist.sys.zram.daily_quota_remain", Integer.toString(i));
                Slog.i("NandswapManager", "First boot, set daily quota remaining...");
            }
            int i5 = SystemProperties.getInt("persist.sys.zram.daily_quota", 262144);
            int i6 = i5 * 3;
            NandswapClient nandswapClient = mClient;
            if (nandswapClient.state == 1) {
                nandswapClient.dailyQuota = i5;
                nandswapClient.dailyQuotaLimit = i6;
                if (NandswapClient.getQuotaSysNode() == 0) {
                    mClient.getClass();
                    int i7 = SystemProperties.getInt("persist.sys.zram.daily_quota_remain", 0);
                    mClient.getClass();
                    NandswapClient.setQuotaSysNode(i7);
                }
            }
        }
    }

    public static boolean isRemainStorageLifeTime() {
        String str =
                new File("/sys/class/scsi_host/host0/lt").exists()
                        ? "/sys/class/scsi_host/host0/lt"
                        : new File("/sys/class/sec/ufs/lt").exists()
                                ? "/sys/class/sec/ufs/lt"
                                : "/sys/block/mmcblk0/device/life_time";
        Slog.d("NandswapManager", "try to check lifetime via ".concat(str));
        try {
            for (String str2 : FileUtils.readTextFile(new File(str), 16, "").trim().split("\\s+")) {
                int parseInt = Integer.parseInt(str2.replaceFirst("0x", ""), 16);
                if (parseInt > 8) {
                    Locale locale = Locale.US;
                    Slog.e("NandswapManager", "abort: lifetime below 20%(val=" + parseInt + ")");
                    return false;
                }
            }
            return true;
        } catch (Exception unused) {
            Slog.e("NandswapManager", "Failed to read ".concat(str));
            return false;
        }
    }

    public static boolean isUfs() {
        try {
        } catch (Exception unused) {
            Slog.w("NandswapManager", "Failed to read /sys/class/scsi_host/host0/proc_name");
        }
        if (new File(Constants.UFS_UN_R).exists()) {
            return true;
        }
        if (new File(Constants.EMMC_UN_R).exists()) {
            return false;
        }
        File file = new File("/sys/class/scsi_host/host0/proc_name");
        if (file.exists()) {
            if ("ufshcd".equals(FileUtils.readTextFile(file, 64, "").trim())) {
                return true;
            }
        }
        return false;
    }

    public static boolean makeNandswapBigdataManager(Context context) {
        try {
            NandSwapBigdataManager nandSwapBigdataManager = new NandSwapBigdataManager();
            NandSwapBigdataManager.semHqmManager =
                    (SemHqmManager) context.getSystemService("HqmManagerService");
            boolean z = mClient.supportBigdataState;
            if (z
                    && (!z
                            || SystemProperties.get(
                                            "persist.sys.zram0.bigdata_info",
                                            KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG)
                                    .equals(KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG))) {
                mClient.initBigdataInfoProp();
            }
            mNandBigData = nandSwapBigdataManager;
            return true;
        } catch (Exception e) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Unexpected error while create bigdataManager: "),
                    "NandswapManager");
            mNandBigData = null;
            return false;
        }
    }

    public static void putRamExpandSize(Context context, int i) {
        try {
            Settings.Global.putInt(context.getContentResolver(), "ram_expand_size", i);
        } catch (Exception unused) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    i, "error on ram_expand_size: ", "NandswapManager");
        }
    }

    public static void putRamExpandSizeList(Context context, String str) {
        try {
            Settings.Global.putString(context.getContentResolver(), "ram_expand_size_list", str);
        } catch (Exception unused) {
            Slog.e("NandswapManager", "error on ram_expand_size_list ".concat(str));
        }
    }

    public static void saveClientsStorageUsage() {
        NandswapClient nandswapClient = mClient;
        if (nandswapClient.state == 1 && nandswapClient.supportBigdataState) {
            HashMap bdStat = nandswapClient.getBdStat();
            Integer num = (Integer) bdStat.get("count");
            num.getClass();
            ProcessingManager processingManager = nandswapClient.normalStat;
            processingManager.countList.add(num);
            Integer num2 = (Integer) bdStat.get("size");
            num2.getClass();
            processingManager.sizeList.add(num2);
            Integer num3 = (Integer) bdStat.get("ppr_count");
            num3.getClass();
            ProcessingManager processingManager2 = nandswapClient.pprStat;
            processingManager2.countList.add(num3);
            Integer num4 = (Integer) bdStat.get("ppr_size");
            num4.getClass();
            processingManager2.sizeList.add(num4);
            Slog.i("NandswapManager", "Saved storage usage successfully!!");
        }
    }

    public static void schedNextLimitReset() {
        JobScheduler jobScheduler = (JobScheduler) mContext.getSystemService("jobscheduler");
        JobInfo.Builder builder = new JobInfo.Builder(813, sNandswapManager);
        TimeUnit timeUnit = TimeUnit.HOURS;
        jobScheduler.schedule(
                builder.setMinimumLatency(timeUnit.toMillis(24L))
                        .setOverrideDeadline(timeUnit.toMillis(24L))
                        .build());
    }

    public static void schedNextUpdateAvgerage() {
        JobScheduler jobScheduler = (JobScheduler) mContext.getSystemService("jobscheduler");
        JobInfo.Builder builder = new JobInfo.Builder(814, sNandswapManager);
        TimeUnit timeUnit = TimeUnit.HOURS;
        jobScheduler.schedule(
                builder.setMinimumLatency(timeUnit.toMillis(4L))
                        .setOverrideDeadline(timeUnit.toMillis(4L))
                        .build());
    }

    public static void setRamExpandSizePersistProp(int i) {
        try {
            SystemProperties.set("persist.sys.zram.ram_expand_size", String.valueOf(i));
        } catch (Exception unused) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    i, "error on set SystemProperties: ", "NandswapManager");
        }
    }

    public static void testRestoreExpandSizeAndList(Context context) {
        int i = Settings.Global.getInt(context.getContentResolver(), "ram_expand_size", -1);
        if (i == -1) {
            return;
        }
        if (!SUPPORT_RAM_EXPAND_SWITCH) {
            Slog.d(
                    "NandswapManager",
                    "no ramExpandSwitch for version " + Build.VERSION.SEM_PLATFORM_INT);
            int defaultRamExpandSize = getDefaultRamExpandSize();
            if (i != defaultRamExpandSize) {
                putRamExpandSize(context, defaultRamExpandSize);
                i = defaultRamExpandSize;
            }
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    i, "ramExpandSizeMb: ", "NandswapManager");
            return;
        }
        String availSizeList = getAvailSizeList();
        if (!availSizeList.equals(
                Settings.Global.getString(context.getContentResolver(), "ram_expand_size_list"))) {
            putRamExpandSizeList(context, availSizeList);
        }
        String availSizeList2 = getAvailSizeList();
        if (i != 0 && (i % 1024 != 0 || availSizeList2.indexOf(String.valueOf(i / 1024)) == -1)) {
            i = getDefaultRamExpandSize();
            putRamExpandSize(context, i);
        }
        setRamExpandSizePersistProp(i);
        Slog.d("NandswapManager", "ramExpandSizeMb: " + i + " avail: " + availSizeList);
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        Slog.d("NandswapManager", "onStartJob");
        try {
        } catch (Exception e) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("scheduler exception occurred : "), "NandswapManager");
        }
        if (!isRemainStorageLifeTime()) {
            jobFinished(jobParameters, false);
            return false;
        }
        if (jobParameters.getJobId() == 813) {
            Slog.i("NandswapManager", "Reset Client Quotas");
            NandswapClient nandswapClient = mClient;
            if (nandswapClient.state == 1) {
                nandswapClient.getClass();
                int quotaSysNode = NandswapClient.getQuotaSysNode();
                NandswapClient nandswapClient2 = mClient;
                int i = quotaSysNode + nandswapClient2.dailyQuota;
                int i2 = nandswapClient2.dailyQuotaLimit;
                if (i > i2) {
                    i = i2;
                }
                NandswapClient.setQuotaSysNode(i);
            }
            schedNextLimitReset();
        } else if (jobParameters.getJobId() == 814) {
            saveClientsStorageUsage();
            schedNextUpdateAvgerage();
        }
        jobFinished(jobParameters, false);
        return false;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        Slog.d("NandswapManager", "onStopJob");
        return false;
    }
}
