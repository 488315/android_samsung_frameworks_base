package com.android.server.chimera;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.SystemProperties;
import android.system.Os;
import android.system.OsConstants;
import android.util.BoostFramework;
import android.util.Slog;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.MemInfoReader;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class GPUMemoryReclaimer {
    public final Dump mDump;
    public final Utils mUtils;
    public final VendorPlugin mVendorPlugin;
    public static boolean FEATURE_ENABLED =
            SystemProperties.getBoolean("ro.sys.kernelmemory.gmr.enabled", false);
    public static String __VendorPluginName = null;
    public static Boolean __EnabledSwapOut = null;
    public static Boolean __EnabledSwapIn = null;
    public static long __MaxReclaimSize = 0;
    public static Boolean __Async = null;
    public static GPUMemoryReclaimer INSTANCE = null;
    public ServiceThread mSwapInThread = null;
    public ServiceThread mSwapOutThread = null;
    public SwapInHandler mSwapInHandler = null;
    public SwapInHandler mSwapOutHandler = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Dump {
        public GPUMemoryReclaimer self;

        public final void meminfo(PrintWriter printWriter) {
            GPUMemoryReclaimer gPUMemoryReclaimer;
            Iterator it;
            String str;
            PrintWriter printWriter2 = printWriter;
            GPUMemoryReclaimer gPUMemoryReclaimer2 = this.self;
            VendorPlugin vendorPlugin = gPUMemoryReclaimer2.mVendorPlugin;
            if (vendorPlugin == null) {
                printWriter2.println("VendorPlugin is not initialized");
                return;
            }
            ArrayList reclaimableTasks = vendorPlugin.getReclaimableTasks();
            reclaimableTasks.sort(new GPUMemoryReclaimer$Dump$$ExternalSyntheticLambda0());
            printWriter2.println("MemInfo");
            ArrayList arrayList = new ArrayList();
            arrayList.add(
                    new ArrayList(Arrays.asList("pid", "name", "resident", "reclaimed", "raw")));
            Iterator it2 = reclaimableTasks.iterator();
            long j = 0;
            long j2 = 0;
            while (it2.hasNext()) {
                ReclaimableTask reclaimableTask = (ReclaimableTask) it2.next();
                int i = reclaimableTask.mPid;
                try {
                    gPUMemoryReclaimer2.mUtils.getClass();
                    try {
                        str = Utils.readFileToString("/proc/" + i + "/comm");
                    } catch (Exception unused) {
                        str = null;
                    }
                    if (str == null) {
                        str = "(unknown)";
                    }
                    long[] jArr = reclaimableTask.mMeminfoRaw;
                    long j3 = reclaimableTask.mResident;
                    long j4 = reclaimableTask.mReclaimed;
                    gPUMemoryReclaimer = gPUMemoryReclaimer2;
                    try {
                        it = it2;
                        try {
                            arrayList.add(
                                    new ArrayList(
                                            Arrays.asList(
                                                    Integer.toString(i),
                                                    str,
                                                    Long.toString(j3),
                                                    Long.toString(j4),
                                                    Arrays.toString(jArr))));
                            j += j3;
                            j2 += j4;
                            printWriter2 = printWriter;
                        } catch (Exception e) {
                            e = e;
                            StringBuilder m =
                                    BatteryService$$ExternalSyntheticOutline0.m(
                                            i, "pid=", " : cannot find GPU memory info: ");
                            m.append(e.toString());
                            String sb = m.toString();
                            printWriter2 = printWriter;
                            printWriter2.println(sb);
                            gPUMemoryReclaimer2 = gPUMemoryReclaimer;
                            it2 = it;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        it = it2;
                        StringBuilder m2 =
                                BatteryService$$ExternalSyntheticOutline0.m(
                                        i, "pid=", " : cannot find GPU memory info: ");
                        m2.append(e.toString());
                        String sb2 = m2.toString();
                        printWriter2 = printWriter;
                        printWriter2.println(sb2);
                        gPUMemoryReclaimer2 = gPUMemoryReclaimer;
                        it2 = it;
                    }
                } catch (Exception e3) {
                    e = e3;
                    gPUMemoryReclaimer = gPUMemoryReclaimer2;
                }
                gPUMemoryReclaimer2 = gPUMemoryReclaimer;
                it2 = it;
            }
            arrayList.add(
                    new ArrayList(
                            Arrays.asList(
                                    "TOTAL",
                                    PackageManagerShellCommandDataLoader.STDIN_PATH,
                                    Long.toString(j),
                                    Long.toString(j2),
                                    "")));
            int[] iArr = new int[((ArrayList) arrayList.get(0)).size()];
            Arrays.fill(iArr, 0);
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                ArrayList arrayList2 = (ArrayList) it3.next();
                for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                    int length = ((String) arrayList2.get(i2)).length();
                    int i3 = iArr[i2];
                    if (i3 > length) {
                        length = i3;
                    }
                    iArr[i2] = length;
                }
            }
            Iterator it4 = arrayList.iterator();
            while (it4.hasNext()) {
                ArrayList arrayList3 = (ArrayList) it4.next();
                StringBuilder sb3 = new StringBuilder();
                for (int i4 = 0; i4 < arrayList3.size(); i4++) {
                    String str2 = (String) arrayList3.get(i4);
                    for (int length2 = (iArr[i4] - str2.length()) + 2; length2 > 0; length2--) {
                        sb3.append(" ");
                    }
                    sb3.append(str2);
                }
                printWriter2.println(sb3);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KGSLPlugin extends VendorPlugin {
        public final String TAG;
        public final BoostFramework mPerf;
        public final /* synthetic */ GPUMemoryReclaimer this$0;

        public KGSLPlugin(
                GPUMemoryReclaimer gPUMemoryReclaimer, GPUMemoryReclaimer gPUMemoryReclaimer2) {
            String concat = "GMR ".concat(KGSLPlugin.class.getSimpleName());
            this.TAG = concat;
            this.self = gPUMemoryReclaimer2;
            BoostFramework boostFramework = new BoostFramework();
            this.mPerf = boostFramework;
            if (Boolean.parseBoolean(
                    boostFramework.perfGetProp("vendor.perf.bgt.enable", "false"))) {
                this.mIsInitialized = true;
            } else {
                this.mPerf = null;
                Slog.e(concat, "perf-hal bgt disabled");
            }
            try {
                File file = new File("/sys/class/kgsl/kgsl/max_reclaim_limit");
                long maxReclaimSize = GPUMemoryReclaimer.getMaxReclaimSize();
                int i = OsConstants._SC_PAGESIZE;
                FileUtils.stringToFile(file, String.valueOf(maxReclaimSize / Os.sysconf(i)));
                Slog.i(
                        concat,
                        "GMR: Success write max reclaim limit: "
                                + (GPUMemoryReclaimer.getMaxReclaimSize() / Os.sysconf(i)));
            } catch (IOException unused) {
                Slog.e(
                        this.TAG,
                        "GMR: Failed to write max recaim limit to"
                            + " /sys/class/kgsl/kgsl/max_reclaim_limit");
            }
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public final long calculateReclaimed(long[] jArr) {
            return jArr[2];
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public final long calculateResident(long[] jArr) {
            return (jArr[0] + jArr[1]) - jArr[2];
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public final long[] getMeminfoRaw(int i) {
            String m =
                    VibrationParam$1$$ExternalSyntheticOutline0.m(i, "/sys/class/kgsl/kgsl/proc/");
            String m$1 =
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m, "/gpumem_mapped");
            String m$12 =
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                            m, "/gpumem_unmapped");
            String m$13 =
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                            m, "/gpumem_reclaimed");
            this.self.mUtils.getClass();
            long parseLong = Long.parseLong(Utils.readFileToString(m$1));
            this.self.mUtils.getClass();
            long parseLong2 = Long.parseLong(Utils.readFileToString(m$12));
            this.self.mUtils.getClass();
            return new long[] {parseLong, parseLong2, Long.parseLong(Utils.readFileToString(m$13))};
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public final ArrayList getReclaimableTasks() {
            ArrayList arrayList = new ArrayList();
            try {
                File[] listFiles = new File("/sys/class/kgsl/kgsl/proc").listFiles();
                if (listFiles != null) {
                    for (File file : listFiles) {
                        if (file.isDirectory()) {
                            ReclaimableTask reclaimableTask =
                                    new ReclaimableTask(Integer.parseInt(file.getName()), this);
                            if (reclaimableTask.mIsSuccess) {
                                arrayList.add(reclaimableTask);
                            }
                        }
                    }
                }
            } catch (SecurityException e) {
                Slog.e(this.TAG, "Cannot access /sys/class/kgsl/kgsl/proc : " + e.toString());
            }
            return arrayList;
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public final int swapInImpl(int i) {
            BoostFramework boostFramework = this.mPerf;
            if (boostFramework == null) {
                return -1;
            }
            boostFramework.perfLockAcquire(10, new int[] {1115815936, i});
            return 0;
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public final int swapOutImpl(int i, String str) {
            BoostFramework boostFramework = this.mPerf;
            if (boostFramework == null) {
                return -1;
            }
            boostFramework.perfLockAcquire(10, new int[] {1115832320, i});
            return 0;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ReclaimableTask {
        public final boolean mIsSuccess;
        public final long[] mMeminfoRaw;
        public final int mPid;
        public final long mReclaimed;
        public final long mResident;

        public ReclaimableTask(int i, VendorPlugin vendorPlugin) {
            long j;
            long[] jArr = new long[0];
            long j2 = -1;
            try {
                jArr = vendorPlugin.getMeminfoRaw(i);
                long calculateResident = vendorPlugin.calculateResident(jArr);
                try {
                    j2 = vendorPlugin.calculateReclaimed(jArr);
                    this.mIsSuccess = true;
                    this.mPid = i;
                    this.mMeminfoRaw = jArr;
                    this.mResident = calculateResident;
                    this.mReclaimed = j2;
                } catch (Exception e) {
                    e = e;
                    long j3 = j2;
                    j2 = calculateResident;
                    j = j3;
                    try {
                        Slog.w("GMR", "Ignore " + i + " due to exception " + e.toString());
                        this.mPid = i;
                        this.mMeminfoRaw = jArr;
                        this.mResident = j2;
                        this.mReclaimed = j;
                    } catch (Throwable th) {
                        th = th;
                        this.mPid = i;
                        this.mMeminfoRaw = jArr;
                        this.mResident = j2;
                        this.mReclaimed = j;
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    long j4 = j2;
                    j2 = calculateResident;
                    j = j4;
                    this.mPid = i;
                    this.mMeminfoRaw = jArr;
                    this.mResident = j2;
                    this.mReclaimed = j;
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                j = -1;
            } catch (Throwable th3) {
                th = th3;
                j = -1;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SGPUPlugin extends VendorPlugin {
        public static final String[] mSkiplistPackages = {"com.sec.android.gallery3d"};
        public final String TAG = "GMR ".concat(SGPUPlugin.class.getSimpleName());
        public final /* synthetic */ GPUMemoryReclaimer this$0;

        public SGPUPlugin(
                GPUMemoryReclaimer gPUMemoryReclaimer, GPUMemoryReclaimer gPUMemoryReclaimer2) {
            this.self = gPUMemoryReclaimer2;
            this.mIsInitialized = true;
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public final long calculateReclaimed(long[] jArr) {
            return jArr[2] - jArr[3];
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public final long calculateResident(long[] jArr) {
            return jArr[3];
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public final long[] getMeminfoRaw(int i) {
            String m =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i, "/sys/kernel/gpu/proc/", "/summary");
            this.self.mUtils.getClass();
            String[] split = Utils.readFileToString(m).split("\\s+");
            return new long[] {
                Long.parseLong(split[1]),
                Long.parseLong(split[3]),
                Long.parseLong(split[5]),
                Long.parseLong(split[7])
            };
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public final ArrayList getReclaimableTasks() {
            ArrayList arrayList = new ArrayList();
            try {
                File[] listFiles = new File("/sys/kernel/gpu/proc").listFiles();
                if (listFiles != null) {
                    for (File file : listFiles) {
                        if (file.isDirectory()) {
                            ReclaimableTask reclaimableTask =
                                    new ReclaimableTask(Integer.parseInt(file.getName()), this);
                            if (reclaimableTask.mIsSuccess) {
                                arrayList.add(reclaimableTask);
                            }
                        }
                    }
                }
            } catch (SecurityException e) {
                Slog.e(this.TAG, "Cannot access /sys/kernel/gpu/proc : " + e.toString());
            }
            return arrayList;
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public final int swapInImpl(int i) {
            try {
                this.self.mUtils.getClass();
                FileWriter fileWriter = new FileWriter("/sys/kernel/gpu/sgpu_swap_ctrl");
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter, 2048);
                bufferedWriter.write("" + i + " 0");
                bufferedWriter.flush();
                bufferedWriter.close();
                fileWriter.close();
                return 0;
            } catch (IOException unused) {
                return -1;
            } catch (Exception e) {
                Slog.e(this.TAG, "Failed to swap-in: " + e.toString());
                return -1;
            }
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public final int swapOutImpl(int i, String str) {
            String str2 = this.TAG;
            if (str != null) {
                try {
                    if (ArrayUtils.contains(mSkiplistPackages, str)) {
                        Slog.d(str2, "Skip GMR BG for Skiplist package (" + str + ")");
                        return -1;
                    }
                } catch (IOException unused) {
                    return -1;
                } catch (Exception e) {
                    Slog.e(str2, "Failed to swap-out: " + e.toString());
                    return -1;
                }
            }
            this.self.mUtils.getClass();
            long parseLong =
                    Long.parseLong(Utils.readFileToString("/sys/kernel/gpu/proc/" + i + "/swap"));
            this.self.mUtils.getClass();
            MemInfoReader memInfoReader = new MemInfoReader();
            memInfoReader.readMemInfo();
            long swapFreeSizeKb = memInfoReader.getSwapFreeSizeKb() * 1024;
            long maxReclaimSize = GPUMemoryReclaimer.getMaxReclaimSize();
            if (parseLong > maxReclaimSize) {
                parseLong = maxReclaimSize;
            }
            if (parseLong <= swapFreeSizeKb) {
                swapFreeSizeKb = parseLong;
            }
            this.self.mUtils.getClass();
            FileWriter fileWriter = new FileWriter("/sys/kernel/gpu/sgpu_swap_ctrl");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter, 2048);
            bufferedWriter.write("" + i + " " + swapFreeSizeKb);
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
            return 0;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SwapInHandler extends Handler {
        public final /* synthetic */ int $r8$classId;
        public final GPUMemoryReclaimer self;
        public final /* synthetic */ GPUMemoryReclaimer this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SwapInHandler(
                GPUMemoryReclaimer gPUMemoryReclaimer,
                GPUMemoryReclaimer gPUMemoryReclaimer2,
                int i) {
            super(gPUMemoryReclaimer.mSwapInThread.getLooper());
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this.this$0 = gPUMemoryReclaimer;
                    super(gPUMemoryReclaimer.mSwapOutThread.getLooper());
                    this.self = gPUMemoryReclaimer2;
                    break;
                default:
                    this.this$0 = gPUMemoryReclaimer;
                    this.self = gPUMemoryReclaimer2;
                    break;
            }
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            switch (this.$r8$classId) {
                case 0:
                    try {
                        if (message.what == 1) {
                            int i = message.arg1;
                            Slog.d("GMR", "fg : " + i + " " + ((String) message.obj));
                            this.self.mVendorPlugin.swapInImpl(i);
                            break;
                        }
                    } catch (Exception unused) {
                        VaultKeeperService$$ExternalSyntheticOutline0.m(
                                new StringBuilder("failed to handleMessage "), message.what, "GMR");
                        return;
                    }
                    break;
                default:
                    try {
                        if (message.what == 1) {
                            int i2 = message.arg1;
                            String str = (String) message.obj;
                            Slog.d("GMR", "bg : " + i2 + " " + str);
                            this.self.mVendorPlugin.swapOut(i2, str);
                            break;
                        }
                    } catch (Exception unused2) {
                        VaultKeeperService$$ExternalSyntheticOutline0.m(
                                new StringBuilder("failed to handleMessage "), message.what, "GMR");
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Utils {
        public static String readFileToString(String str) {
            FileReader fileReader = new FileReader(str);
            BufferedReader bufferedReader = new BufferedReader(fileReader, 2048);
            String readLine = bufferedReader.readLine();
            bufferedReader.close();
            fileReader.close();
            return readLine;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class VendorPlugin {
        public boolean mIsInitialized = false;
        public GPUMemoryReclaimer self;

        public abstract long calculateReclaimed(long[] jArr);

        public abstract long calculateResident(long[] jArr);

        public abstract long[] getMeminfoRaw(int i);

        public abstract ArrayList getReclaimableTasks();

        public abstract int swapInImpl(int i);

        public final void swapOut(int i, String str) {
            this.self.mUtils.getClass();
            MemInfoReader memInfoReader = new MemInfoReader();
            memInfoReader.readMemInfo();
            long swapFreeSizeKb = memInfoReader.getSwapFreeSizeKb();
            long swapTotalSizeKb = memInfoReader.getSwapTotalSizeKb();
            long j = swapTotalSizeKb <= 0 ? 0L : (swapFreeSizeKb * 100) / swapTotalSizeKb;
            long j2 = j > 0 ? j : 0L;
            if (((int) (j2 < 100 ? j2 : 100L)) <= 1) {
                return;
            }
            swapOutImpl(i, str);
        }

        public abstract int swapOutImpl(int i, String str);
    }

    public GPUMemoryReclaimer() {
        this.mVendorPlugin = null;
        Dump dump = new Dump();
        dump.self = this;
        this.mDump = dump;
        this.mUtils = new Utils();
        try {
            Slog.i("GMR", "init start");
            if (!FEATURE_ENABLED) {
                Slog.w("GMR", "feature disabled");
                return;
            }
            Slog.i("GMR", "feature enabled");
            Slog.i("GMR", "Configurations");
            Slog.i("GMR", "  feature enable: " + FEATURE_ENABLED);
            StringBuilder sb = new StringBuilder("  vendor_plugin: ");
            if (__VendorPluginName == null) {
                __VendorPluginName =
                        SystemProperties.get("ro.sys.kernelmemory.gmr.vendor_plugin", "");
            }
            sb.append(__VendorPluginName);
            Slog.i("GMR", sb.toString());
            Slog.i("GMR", "  enabled_swap_out: " + isEnabledSwapOut());
            Slog.i("GMR", "  enabled_swap_in: " + isEnabledSwapIn());
            Slog.i("GMR", "  async: " + isAsync());
            if (__VendorPluginName == null) {
                __VendorPluginName =
                        SystemProperties.get("ro.sys.kernelmemory.gmr.vendor_plugin", "");
            }
            String str = __VendorPluginName;
            if ("kgsl".equals(str)) {
                this.mVendorPlugin = new KGSLPlugin(this, this);
            } else if ("sgpu".equals(str)) {
                this.mVendorPlugin = new SGPUPlugin(this, this);
            }
            VendorPlugin vendorPlugin = this.mVendorPlugin;
            if (vendorPlugin != null && vendorPlugin.mIsInitialized) {
                initAsyncThreads();
                FEATURE_ENABLED = true;
                Slog.i("GMR", "init success");
                return;
            }
            Slog.e("GMR", "vendor_plugin is not defined or invalid: " + str);
            this.mVendorPlugin = null;
            FEATURE_ENABLED = false;
        } catch (Exception e) {
            Slog.e("GMR", "init failed " + e.toString());
            FEATURE_ENABLED = false;
        }
    }

    public static synchronized GPUMemoryReclaimer getInstance() {
        GPUMemoryReclaimer gPUMemoryReclaimer;
        synchronized (GPUMemoryReclaimer.class) {
            try {
                if (INSTANCE == null) {
                    INSTANCE = new GPUMemoryReclaimer();
                }
                gPUMemoryReclaimer = INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        return gPUMemoryReclaimer;
    }

    public static final long getMaxReclaimSize() {
        if (__MaxReclaimSize == 0) {
            __MaxReclaimSize =
                    SystemProperties.getLong("ro.sys.kernelmemory.gmr.max_size", 536870912L);
        }
        return __MaxReclaimSize;
    }

    public static final Boolean isAsync() {
        if (__Async == null) {
            __Async =
                    Boolean.valueOf(
                            SystemProperties.getBoolean("ro.sys.kernelmemory.gmr.async", false));
        }
        return __Async;
    }

    public static final Boolean isEnabledSwapIn() {
        if (__EnabledSwapIn == null) {
            __EnabledSwapIn =
                    Boolean.valueOf(
                            SystemProperties.getBoolean(
                                    "ro.sys.kernelmemory.gmr.enabled_swap_in", true));
        }
        return __EnabledSwapIn;
    }

    public static final Boolean isEnabledSwapOut() {
        if (__EnabledSwapOut == null) {
            __EnabledSwapOut =
                    Boolean.valueOf(
                            SystemProperties.getBoolean(
                                    "ro.sys.kernelmemory.gmr.enabled_swap_out", true));
        }
        return __EnabledSwapOut;
    }

    public final void dumpInfo(PrintWriter printWriter, String[] strArr) {
        Dump dump = this.mDump;
        dump.getClass();
        try {
            if (strArr.length > 1) {
                printWriter.println("gmr: command failed in ship build");
                return;
            }
            printWriter.println("== GMR dump start ==");
            printWriter.println("Configurations");
            printWriter.println("  feature enable: " + FEATURE_ENABLED);
            StringBuilder sb = new StringBuilder("  vendor_plugin: ");
            if (__VendorPluginName == null) {
                __VendorPluginName =
                        SystemProperties.get("ro.sys.kernelmemory.gmr.vendor_plugin", "");
            }
            sb.append(__VendorPluginName);
            printWriter.println(sb.toString());
            printWriter.println("  enabled_swap_out: " + isEnabledSwapOut());
            printWriter.println("  enabled_swap_in: " + isEnabledSwapIn());
            printWriter.println("  async: " + isAsync());
            dump.meminfo(printWriter);
            printWriter.println("== GMR dump end ==");
        } catch (Exception e) {
            printWriter.println("gmr: exception " + e.toString());
            e.printStackTrace();
        }
    }

    public final void initAsyncThreads() {
        if (isAsync().booleanValue()) {
            if (this.mSwapInThread == null) {
                ServiceThread serviceThread = new ServiceThread(0, "GMRSwapInThread", true);
                this.mSwapInThread = serviceThread;
                serviceThread.start();
                this.mSwapInHandler = new SwapInHandler(this, this, 0);
                Process.setThreadGroupAndCpuset(this.mSwapInThread.getThreadId(), 0);
            }
            if (this.mSwapOutThread == null) {
                ServiceThread serviceThread2 = new ServiceThread(2, "GMRSwapOutThread", true);
                this.mSwapOutThread = serviceThread2;
                serviceThread2.start();
                this.mSwapOutHandler = new SwapInHandler(this, this, 1);
                Process.setThreadGroupAndCpuset(this.mSwapOutThread.getThreadId(), 2);
            }
        }
    }
}
