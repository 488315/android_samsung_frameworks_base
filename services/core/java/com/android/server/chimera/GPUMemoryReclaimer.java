package com.android.server.chimera;

import android.os.FileUtils;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.SystemProperties;
import android.p005os.IInstalld;
import android.system.Os;
import android.system.OsConstants;
import android.util.BoostFramework;
import android.util.Slog;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.MemInfoReader;
import com.android.server.ServiceThread;
import com.android.server.chimera.GPUMemoryReclaimer;
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
import java.util.Comparator;
import java.util.Iterator;

/* loaded from: classes.dex */
public class GPUMemoryReclaimer {
    public static boolean FEATURE_ENABLED = SystemProperties.getBoolean("ro.sys.kernelmemory.gmr.enabled", false);
    public static final boolean IS_SHIP_BUILD = "true".equals(SystemProperties.get("ro.product_ship", "false"));
    public static String __VendorPluginName = null;
    public static Boolean __EnabledSwapOut = null;
    public static Boolean __EnabledSwapIn = null;
    public static long __MaxReclaimSize = 0;
    public static Boolean __Async = null;
    public static GPUMemoryReclaimer INSTANCE = null;
    public VendorPlugin mVendorPlugin = null;
    public ServiceThread mSwapInThread = null;
    public ServiceThread mSwapOutThread = null;
    public SwapInHandler mSwapInHandler = null;
    public SwapOutHandler mSwapOutHandler = null;
    public final Dump mDump = new Dump(this);
    public final Utils mUtils = new Utils(this);

    public static final String getVendorPluginName() {
        if (__VendorPluginName == null) {
            __VendorPluginName = SystemProperties.get("ro.sys.kernelmemory.gmr.vendor_plugin", "");
        }
        return __VendorPluginName;
    }

    public static final Boolean isEnabledSwapOut() {
        if (__EnabledSwapOut == null) {
            __EnabledSwapOut = Boolean.valueOf(SystemProperties.getBoolean("ro.sys.kernelmemory.gmr.enabled_swap_out", true));
        }
        return __EnabledSwapOut;
    }

    public static final Boolean isEnabledSwapIn() {
        if (__EnabledSwapIn == null) {
            __EnabledSwapIn = Boolean.valueOf(SystemProperties.getBoolean("ro.sys.kernelmemory.gmr.enabled_swap_in", true));
        }
        return __EnabledSwapIn;
    }

    public static final long getMaxReclaimSize() {
        if (__MaxReclaimSize == 0) {
            __MaxReclaimSize = SystemProperties.getLong("ro.sys.kernelmemory.gmr.max_size", 536870912L);
        }
        return __MaxReclaimSize;
    }

    public static final Boolean isAsync() {
        if (__Async == null) {
            __Async = Boolean.valueOf(SystemProperties.getBoolean("ro.sys.kernelmemory.gmr.async", false));
        }
        return __Async;
    }

    public static synchronized GPUMemoryReclaimer getInstance() {
        GPUMemoryReclaimer gPUMemoryReclaimer;
        synchronized (GPUMemoryReclaimer.class) {
            if (INSTANCE == null) {
                INSTANCE = new GPUMemoryReclaimer();
            }
            gPUMemoryReclaimer = INSTANCE;
        }
        return gPUMemoryReclaimer;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public GPUMemoryReclaimer() {
        init();
    }

    public final void init() {
        try {
            Slog.i("GMR", "init start");
            if (!FEATURE_ENABLED) {
                Slog.w("GMR", "feature disabled");
                return;
            }
            Slog.i("GMR", "feature enabled");
            Slog.i("GMR", "Configurations");
            Slog.i("GMR", "  feature enable: " + FEATURE_ENABLED);
            Slog.i("GMR", "  vendor_plugin: " + getVendorPluginName());
            Slog.i("GMR", "  enabled_swap_out: " + isEnabledSwapOut());
            Slog.i("GMR", "  enabled_swap_in: " + isEnabledSwapIn());
            Slog.i("GMR", "  async: " + isAsync());
            String vendorPluginName = getVendorPluginName();
            if ("kgsl".equals(vendorPluginName)) {
                this.mVendorPlugin = new KGSLPlugin(this);
            } else if ("sgpu".equals(vendorPluginName)) {
                this.mVendorPlugin = new SGPUPlugin(this);
            }
            VendorPlugin vendorPlugin = this.mVendorPlugin;
            if (vendorPlugin != null && vendorPlugin.isInitialized()) {
                initAsyncThreads();
                FEATURE_ENABLED = true;
                Slog.i("GMR", "init success");
                return;
            }
            Slog.e("GMR", "vendor_plugin is not defined or invalid: " + vendorPluginName);
            this.mVendorPlugin = null;
            FEATURE_ENABLED = false;
        } catch (Exception e) {
            Slog.e("GMR", "init failed " + e.toString());
            FEATURE_ENABLED = false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void initAsyncThreads() {
        if (isAsync().booleanValue()) {
            Object[] objArr = 0;
            if (this.mSwapInThread == null) {
                ServiceThread serviceThread = new ServiceThread("GMRSwapInThread", 0, true);
                this.mSwapInThread = serviceThread;
                serviceThread.start();
                this.mSwapInHandler = new SwapInHandler(this);
                Process.setThreadGroupAndCpuset(this.mSwapInThread.getThreadId(), 0);
            }
            if (this.mSwapOutThread == null) {
                ServiceThread serviceThread2 = new ServiceThread("GMRSwapOutThread", 2, true);
                this.mSwapOutThread = serviceThread2;
                serviceThread2.start();
                this.mSwapOutHandler = new SwapOutHandler(this);
                Process.setThreadGroupAndCpuset(this.mSwapOutThread.getThreadId(), 2);
            }
        }
    }

    public final class SwapInHandler extends Handler {
        public GPUMemoryReclaimer self;

        public SwapInHandler(GPUMemoryReclaimer gPUMemoryReclaimer) {
            super(GPUMemoryReclaimer.this.mSwapInThread.getLooper());
            this.self = gPUMemoryReclaimer;
        }

        public void swapInAsync(int i, String str) {
            GPUMemoryReclaimer.this.mSwapInHandler.sendMessage(GPUMemoryReclaimer.this.mSwapInHandler.obtainMessage(1, i, 0, str));
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                if (message.what == 1) {
                    int i = message.arg1;
                    String str = (String) message.obj;
                    Slog.d("GMR", "fg : " + i + " " + str);
                    this.self.mVendorPlugin.swapIn(i, str);
                }
            } catch (Exception unused) {
                Slog.e("GMR", "failed to handleMessage " + message.what);
            }
        }
    }

    public final class SwapOutHandler extends Handler {
        public GPUMemoryReclaimer self;

        public SwapOutHandler(GPUMemoryReclaimer gPUMemoryReclaimer) {
            super(GPUMemoryReclaimer.this.mSwapOutThread.getLooper());
            this.self = gPUMemoryReclaimer;
        }

        public void swapOutAsync(int i, String str) {
            GPUMemoryReclaimer.this.mSwapOutHandler.sendMessage(GPUMemoryReclaimer.this.mSwapOutHandler.obtainMessage(1, i, 0, str));
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                if (message.what == 1) {
                    int i = message.arg1;
                    String str = (String) message.obj;
                    Slog.d("GMR", "bg : " + i + " " + str);
                    this.self.mVendorPlugin.swapOut(i, str);
                }
            } catch (Exception unused) {
                Slog.e("GMR", "failed to handleMessage " + message.what);
            }
        }
    }

    public void onOomAdjChanged(int i, String str, int i2, int i3, boolean z, boolean z2) {
        try {
            if (!FEATURE_ENABLED || this.mVendorPlugin == null || i <= 0 || i2 == i3) {
                return;
            }
            if (i3 >= 830 && i3 <= 999 && i2 == 0 && z2 && isEnabledSwapIn().booleanValue()) {
                if (isAsync().booleanValue() && this.mSwapInHandler != null) {
                    Slog.d("GMR", "fg_async : " + i + " " + str);
                    this.mSwapInHandler.swapInAsync(i, str);
                } else {
                    Slog.d("GMR", "fg : " + i + " " + str);
                    this.mVendorPlugin.swapIn(i, str);
                }
            }
            if (i3 == 700 && i2 >= 830 && i2 <= 999 && z && isEnabledSwapOut().booleanValue()) {
                if (isAsync().booleanValue() && this.mSwapOutHandler != null) {
                    Slog.d("GMR", "bg_async : " + i + " " + str);
                    this.mSwapOutHandler.swapOutAsync(i, str);
                    return;
                }
                Slog.d("GMR", "bg : " + i + " " + str);
                this.mVendorPlugin.swapOut(i, str);
            }
        } catch (Exception e) {
            Slog.e("GMR", "Failed to handle onOomAdjChanged: " + e.toString());
        }
    }

    public void dumpInfo(PrintWriter printWriter, String[] strArr) {
        this.mDump.run(printWriter, strArr);
    }

    public class Dump {
        public GPUMemoryReclaimer self;

        public Dump(GPUMemoryReclaimer gPUMemoryReclaimer) {
            this.self = gPUMemoryReclaimer;
        }

        public void run(PrintWriter printWriter, String[] strArr) {
            try {
                if (strArr.length <= 1) {
                    printWriter.println("== GMR dump start ==");
                    printWriter.println("Configurations");
                    printWriter.println("  feature enable: " + GPUMemoryReclaimer.FEATURE_ENABLED);
                    printWriter.println("  vendor_plugin: " + GPUMemoryReclaimer.getVendorPluginName());
                    printWriter.println("  enabled_swap_out: " + GPUMemoryReclaimer.isEnabledSwapOut());
                    printWriter.println("  enabled_swap_in: " + GPUMemoryReclaimer.isEnabledSwapIn());
                    printWriter.println("  async: " + GPUMemoryReclaimer.isAsync());
                    meminfo(printWriter);
                    printWriter.println("== GMR dump end ==");
                } else if (GPUMemoryReclaimer.IS_SHIP_BUILD) {
                    printWriter.println("gmr: command failed in ship build");
                } else {
                    runCommand(printWriter, strArr);
                }
            } catch (Exception e) {
                printWriter.println("gmr: exception " + e.toString());
                e.printStackTrace();
            }
        }

        public void runCommand(PrintWriter printWriter, String[] strArr) {
            String str = strArr[1];
            try {
                if ("setprop".equals(str) && strArr.length == 4) {
                    setprop(printWriter, strArr[2], strArr[3]);
                } else if ("meminfo".equals(str)) {
                    if (strArr.length == 2) {
                        meminfo(printWriter);
                    } else if (strArr.length == 3) {
                        meminfoPid(printWriter, Integer.parseInt(strArr[2]));
                    } else {
                        printWriter.println("dump " + str + " : invalid command");
                    }
                } else if ("swapOut".equals(str) && strArr.length == 3) {
                    int parseInt = Integer.parseInt(strArr[2]);
                    swapOut(printWriter, parseInt, this.self.mUtils.getProcessName(parseInt));
                } else if ("swapIn".equals(str) && strArr.length == 3) {
                    int parseInt2 = Integer.parseInt(strArr[2]);
                    swapIn(printWriter, parseInt2, this.self.mUtils.getProcessName(parseInt2));
                } else if ("enabled_swap_out".equals(str) && strArr.length == 3) {
                    GPUMemoryReclaimer.__EnabledSwapOut = Boolean.valueOf(Boolean.parseBoolean(strArr[2]));
                } else if ("enabled_swap_in".equals(str) && strArr.length == 3) {
                    GPUMemoryReclaimer.__EnabledSwapIn = Boolean.valueOf(Boolean.parseBoolean(strArr[2]));
                } else if ("async".equals(str) && strArr.length == 3) {
                    GPUMemoryReclaimer.__Async = Boolean.valueOf(Boolean.parseBoolean(strArr[2]));
                    GPUMemoryReclaimer.this.initAsyncThreads();
                } else {
                    printWriter.println("dump " + str + " : invalid command");
                }
            } catch (Exception e) {
                printWriter.println("dump " + str + " failed: " + e.toString());
                e.printStackTrace();
            }
        }

        public final void setprop(PrintWriter printWriter, String str, String str2) {
            try {
                if ("vendor_plugin".equals(str)) {
                    String vendorPluginName = GPUMemoryReclaimer.getVendorPluginName();
                    GPUMemoryReclaimer.__VendorPluginName = str2;
                    printWriter.println("dump setprop " + str + " " + vendorPluginName + " -> " + str2);
                    this.self.init();
                    return;
                }
                printWriter.println("dump setprop " + str + " " + str2 + " failed: invalid key");
            } catch (Exception e) {
                printWriter.println("dump setprop " + str + " " + str2 + " failed: " + e.toString());
                e.printStackTrace();
            }
        }

        public void meminfo(PrintWriter printWriter) {
            Iterator it;
            String processName;
            long[] meminfoRaw;
            long resident;
            long reclaimed;
            if (this.self.mVendorPlugin == null) {
                printWriter.println("VendorPlugin is not initialized");
                return;
            }
            ArrayList reclaimableTasks = this.self.mVendorPlugin.getReclaimableTasks();
            reclaimableTasks.sort(new Comparator() { // from class: com.android.server.chimera.GPUMemoryReclaimer$Dump$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int lambda$meminfo$0;
                    lambda$meminfo$0 = GPUMemoryReclaimer.Dump.lambda$meminfo$0((GPUMemoryReclaimer.ReclaimableTask) obj, (GPUMemoryReclaimer.ReclaimableTask) obj2);
                    return lambda$meminfo$0;
                }
            });
            printWriter.println("MemInfo");
            ArrayList arrayList = new ArrayList();
            arrayList.add(new ArrayList(Arrays.asList("pid", "name", "resident", "reclaimed", "raw")));
            Iterator it2 = reclaimableTasks.iterator();
            long j = 0;
            long j2 = 0;
            while (it2.hasNext()) {
                ReclaimableTask reclaimableTask = (ReclaimableTask) it2.next();
                int pid = reclaimableTask.getPid();
                try {
                    processName = this.self.mUtils.getProcessName(pid);
                    if (processName == null) {
                        processName = "(unknown)";
                    }
                    meminfoRaw = reclaimableTask.getMeminfoRaw();
                    resident = reclaimableTask.getResident();
                    reclaimed = reclaimableTask.getReclaimed();
                    it = it2;
                } catch (Exception e) {
                    e = e;
                    it = it2;
                }
                try {
                    arrayList.add(new ArrayList(Arrays.asList(Integer.toString(pid), processName, Long.toString(resident), Long.toString(reclaimed), Arrays.toString(meminfoRaw))));
                    j += resident;
                    j2 += reclaimed;
                } catch (Exception e2) {
                    e = e2;
                    printWriter.println("pid=" + pid + " : cannot find GPU memory info: " + e.toString());
                    it2 = it;
                }
                it2 = it;
            }
            arrayList.add(new ArrayList(Arrays.asList("TOTAL", PackageManagerShellCommandDataLoader.STDIN_PATH, Long.toString(j), Long.toString(j2), "")));
            printTable(printWriter, arrayList);
        }

        public static /* synthetic */ int lambda$meminfo$0(ReclaimableTask reclaimableTask, ReclaimableTask reclaimableTask2) {
            return reclaimableTask.getPid() - reclaimableTask2.getPid();
        }

        public final void meminfoPid(PrintWriter printWriter, int i) {
            if (this.self.mVendorPlugin == null) {
                printWriter.println("VendorPlugin is not initialized");
                return;
            }
            try {
                String processName = this.self.mUtils.getProcessName(i);
                if (processName == null) {
                    processName = "(unknown)";
                }
                long[] meminfoRaw = this.self.mVendorPlugin.getMeminfoRaw(i);
                printWriter.println(Integer.toString(i) + " " + processName + " " + Long.toString(this.self.mVendorPlugin.calculateResident(meminfoRaw)) + " " + Long.toString(this.self.mVendorPlugin.calculateReclaimed(meminfoRaw)) + " " + Arrays.toString(meminfoRaw));
            } catch (Exception unused) {
                printWriter.println("gmr: failed to get meminfo pid=" + i);
            }
        }

        public final void printTable(PrintWriter printWriter, ArrayList arrayList) {
            int[] iArr = new int[((ArrayList) arrayList.get(0)).size()];
            Arrays.fill(iArr, 0);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ArrayList arrayList2 = (ArrayList) it.next();
                for (int i = 0; i < arrayList2.size(); i++) {
                    int length = ((String) arrayList2.get(i)).length();
                    int i2 = iArr[i];
                    if (i2 > length) {
                        length = i2;
                    }
                    iArr[i] = length;
                }
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ArrayList arrayList3 = (ArrayList) it2.next();
                StringBuilder sb = new StringBuilder();
                for (int i3 = 0; i3 < arrayList3.size(); i3++) {
                    String str = (String) arrayList3.get(i3);
                    for (int length2 = (iArr[i3] - str.length()) + 2; length2 > 0; length2--) {
                        sb.append(" ");
                    }
                    sb.append(str);
                }
                printWriter.println(sb);
            }
        }

        public final void swapOut(PrintWriter printWriter, int i, String str) {
            if (this.self.mVendorPlugin == null) {
                printWriter.println("Vendor plugin not initialized");
                return;
            }
            printWriter.println("Triggered swap-out for pid=" + i + " ret=" + this.self.mVendorPlugin.swapOut(i, str));
        }

        public final void swapIn(PrintWriter printWriter, int i, String str) {
            if (this.self.mVendorPlugin == null) {
                printWriter.println("Vendor plugin not initialized");
                return;
            }
            printWriter.println("Triggered swap-in for pid=" + i + " ret=" + this.self.mVendorPlugin.swapIn(i, str));
        }
    }

    public class Utils {
        public GPUMemoryReclaimer self;

        public Utils(GPUMemoryReclaimer gPUMemoryReclaimer) {
            this.self = gPUMemoryReclaimer;
        }

        public String getProcessName(int i) {
            try {
                return readFileToString("/proc/" + i + "/comm");
            } catch (Exception unused) {
                return null;
            }
        }

        public long readFileToLong(String str) {
            return Long.parseLong(readFileToString(str));
        }

        public String readFileToString(String str) {
            FileReader fileReader = new FileReader(str);
            BufferedReader bufferedReader = new BufferedReader(fileReader, IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES);
            String readLine = bufferedReader.readLine();
            bufferedReader.close();
            fileReader.close();
            return readLine;
        }

        public void writeStringToFile(String str, String str2) {
            FileWriter fileWriter = new FileWriter(str);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter, IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES);
            bufferedWriter.write(str2);
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
        }

        public final long getSwapFreeKb() {
            MemInfoReader memInfoReader = new MemInfoReader();
            memInfoReader.readMemInfo();
            return memInfoReader.getSwapFreeSizeKb();
        }

        public int getSwapFreePercentage() {
            MemInfoReader memInfoReader = new MemInfoReader();
            memInfoReader.readMemInfo();
            long swapFreeSizeKb = memInfoReader.getSwapFreeSizeKb();
            long swapTotalSizeKb = memInfoReader.getSwapTotalSizeKb();
            long j = swapTotalSizeKb <= 0 ? 0L : (swapFreeSizeKb * 100) / swapTotalSizeKb;
            long j2 = j > 0 ? j : 0L;
            return (int) (j2 < 100 ? j2 : 100L);
        }
    }

    public class ReclaimableTask {
        public boolean mIsSuccess;
        public final long[] mMeminfoRaw;
        public final int mPid;
        public final long mReclaimed;
        public final long mResident;

        public ReclaimableTask(int i, VendorPlugin vendorPlugin) {
            long j;
            long calculateResident;
            long[] jArr = new long[0];
            long j2 = -1;
            try {
                jArr = vendorPlugin.getMeminfoRaw(i);
                calculateResident = vendorPlugin.calculateResident(jArr);
            } catch (Exception e) {
                e = e;
                j = -1;
            } catch (Throwable th) {
                th = th;
                j = -1;
            }
            try {
                j2 = vendorPlugin.calculateReclaimed(jArr);
                this.mIsSuccess = true;
                this.mPid = i;
                this.mMeminfoRaw = jArr;
                this.mResident = calculateResident;
                this.mReclaimed = j2;
            } catch (Exception e2) {
                e = e2;
                long j3 = j2;
                j2 = calculateResident;
                j = j3;
                try {
                    Slog.w("GMR", "Ignore " + i + " due to exception " + e.toString());
                    this.mPid = i;
                    this.mMeminfoRaw = jArr;
                    this.mResident = j2;
                    this.mReclaimed = j;
                } catch (Throwable th2) {
                    th = th2;
                    this.mPid = i;
                    this.mMeminfoRaw = jArr;
                    this.mResident = j2;
                    this.mReclaimed = j;
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                long j4 = j2;
                j2 = calculateResident;
                j = j4;
                this.mPid = i;
                this.mMeminfoRaw = jArr;
                this.mResident = j2;
                this.mReclaimed = j;
                throw th;
            }
        }

        public boolean isSuccess() {
            return this.mIsSuccess;
        }

        public int getPid() {
            return this.mPid;
        }

        public long getResident() {
            return this.mResident;
        }

        public long getReclaimed() {
            return this.mReclaimed;
        }

        public long[] getMeminfoRaw() {
            return this.mMeminfoRaw;
        }
    }

    public abstract class VendorPlugin {
        public String mPluginName;
        public GPUMemoryReclaimer self;
        public boolean mIsInitialized = false;
        public final int SWAP_FREE_PERCENTAGE_THRESHOLD = 1;

        public abstract long calculateReclaimed(long[] jArr);

        public abstract long calculateResident(long[] jArr);

        public abstract long[] getMeminfoRaw(int i);

        public abstract ArrayList getReclaimableTasks();

        public abstract int swapInImpl(int i, String str);

        public abstract int swapOutImpl(int i, String str);

        public VendorPlugin(GPUMemoryReclaimer gPUMemoryReclaimer, String str) {
            this.mPluginName = str;
        }

        public boolean isInitialized() {
            return this.mIsInitialized;
        }

        public int swapOut(int i, String str) {
            if (this.self.mUtils.getSwapFreePercentage() <= 1) {
                return -1;
            }
            return swapOutImpl(i, str);
        }

        public int swapIn(int i, String str) {
            return swapInImpl(i, str);
        }
    }

    public class KGSLPlugin extends VendorPlugin {
        public final int COMMAND_TYPE_BG;
        public final int COMMAND_TYPE_FG;
        public final String GMR_MAX_RECLAIM_SIZE_NODE;
        public final String KGSL_PROC_PATH;
        public final String TAG;
        public BoostFramework mPerf;

        public KGSLPlugin(GPUMemoryReclaimer gPUMemoryReclaimer) {
            super(gPUMemoryReclaimer, "KGSL");
            String str = "GMR " + KGSLPlugin.class.getSimpleName();
            this.TAG = str;
            this.COMMAND_TYPE_BG = 1;
            this.COMMAND_TYPE_FG = 2;
            this.KGSL_PROC_PATH = "/sys/class/kgsl/kgsl/proc";
            this.GMR_MAX_RECLAIM_SIZE_NODE = "/sys/class/kgsl/kgsl/max_reclaim_limit";
            this.self = gPUMemoryReclaimer;
            BoostFramework boostFramework = new BoostFramework();
            this.mPerf = boostFramework;
            if (Boolean.parseBoolean(boostFramework.perfGetProp("vendor.perf.bgt.enable", "false"))) {
                this.mIsInitialized = true;
            } else {
                this.mPerf = null;
                Slog.e(str, "perf-hal bgt disabled");
            }
            try {
                FileUtils.stringToFile(new File("/sys/class/kgsl/kgsl/max_reclaim_limit"), String.valueOf(GPUMemoryReclaimer.getMaxReclaimSize() / Os.sysconf(OsConstants._SC_PAGESIZE)));
                Slog.i(str, "GMR: Success write max reclaim limit: " + (GPUMemoryReclaimer.getMaxReclaimSize() / Os.sysconf(OsConstants._SC_PAGESIZE)));
            } catch (IOException unused) {
                Slog.e(this.TAG, "GMR: Failed to write max recaim limit to /sys/class/kgsl/kgsl/max_reclaim_limit");
            }
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public int swapOutImpl(int i, String str) {
            BoostFramework boostFramework = this.mPerf;
            if (boostFramework == null) {
                return -1;
            }
            boostFramework.perfLockAcquire(10, new int[]{1115832320, i});
            return 0;
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public int swapInImpl(int i, String str) {
            BoostFramework boostFramework = this.mPerf;
            if (boostFramework == null) {
                return -1;
            }
            boostFramework.perfLockAcquire(10, new int[]{1115815936, i});
            return 0;
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public long[] getMeminfoRaw(int i) {
            String str = "/sys/class/kgsl/kgsl/proc/" + i;
            return new long[]{this.self.mUtils.readFileToLong(str + "/gpumem_mapped"), this.self.mUtils.readFileToLong(str + "/gpumem_unmapped"), this.self.mUtils.readFileToLong(str + "/gpumem_reclaimed")};
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public long calculateResident(long[] jArr) {
            return (jArr[0] + jArr[1]) - jArr[2];
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public long calculateReclaimed(long[] jArr) {
            return jArr[2];
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public ArrayList getReclaimableTasks() {
            ArrayList arrayList = new ArrayList();
            try {
                for (File file : new File("/sys/class/kgsl/kgsl/proc").listFiles()) {
                    if (file.isDirectory()) {
                        ReclaimableTask reclaimableTask = new ReclaimableTask(Integer.parseInt(file.getName()), this);
                        if (reclaimableTask.isSuccess()) {
                            arrayList.add(reclaimableTask);
                        }
                    }
                }
            } catch (SecurityException e) {
                Slog.e(this.TAG, "Cannot access /sys/class/kgsl/kgsl/proc : " + e.toString());
            }
            return arrayList;
        }
    }

    public class SGPUPlugin extends VendorPlugin {
        public static final String[] mSkiplistPackages = {"com.sec.android.gallery3d"};
        public final String SGPU_PATH;
        public final String TAG;

        public SGPUPlugin(GPUMemoryReclaimer gPUMemoryReclaimer) {
            super(gPUMemoryReclaimer, "SGPU");
            this.TAG = "GMR " + SGPUPlugin.class.getSimpleName();
            this.SGPU_PATH = "/sys/kernel/gpu";
            this.self = gPUMemoryReclaimer;
            this.mIsInitialized = true;
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public int swapOutImpl(int i, String str) {
            if (str != null) {
                try {
                    if (ArrayUtils.contains(mSkiplistPackages, str)) {
                        Slog.d(this.TAG, "Skip GMR BG for Skiplist package (" + str + ")");
                        return -1;
                    }
                } catch (IOException unused) {
                    return -1;
                } catch (Exception e) {
                    Slog.e(this.TAG, "Failed to swap-out: " + e.toString());
                    return -1;
                }
            }
            long readFileToLong = this.self.mUtils.readFileToLong("/sys/kernel/gpu/proc/" + i + "/swap");
            long swapFreeKb = this.self.mUtils.getSwapFreeKb() * 1024;
            long maxReclaimSize = GPUMemoryReclaimer.getMaxReclaimSize();
            if (readFileToLong > maxReclaimSize) {
                readFileToLong = maxReclaimSize;
            }
            if (readFileToLong <= swapFreeKb) {
                swapFreeKb = readFileToLong;
            }
            this.self.mUtils.writeStringToFile("/sys/kernel/gpu/sgpu_swap_ctrl", "" + i + " " + swapFreeKb);
            return 0;
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public int swapInImpl(int i, String str) {
            try {
                this.self.mUtils.writeStringToFile("/sys/kernel/gpu/sgpu_swap_ctrl", "" + i + " 0");
                return 0;
            } catch (IOException unused) {
                return -1;
            } catch (Exception e) {
                Slog.e(this.TAG, "Failed to swap-in: " + e.toString());
                return -1;
            }
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public long[] getMeminfoRaw(int i) {
            String[] split = this.self.mUtils.readFileToString("/sys/kernel/gpu/proc/" + i + "/summary").split("\\s+");
            return new long[]{Long.parseLong(split[1]), Long.parseLong(split[3]), Long.parseLong(split[5]), Long.parseLong(split[7])};
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public long calculateResident(long[] jArr) {
            return jArr[3];
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public long calculateReclaimed(long[] jArr) {
            return jArr[2] - jArr[3];
        }

        @Override // com.android.server.chimera.GPUMemoryReclaimer.VendorPlugin
        public ArrayList getReclaimableTasks() {
            ArrayList arrayList = new ArrayList();
            try {
                for (File file : new File("/sys/kernel/gpu/proc").listFiles()) {
                    if (file.isDirectory()) {
                        ReclaimableTask reclaimableTask = new ReclaimableTask(Integer.parseInt(file.getName()), this);
                        if (reclaimableTask.isSuccess()) {
                            arrayList.add(reclaimableTask);
                        }
                    }
                }
            } catch (SecurityException e) {
                Slog.e(this.TAG, "Cannot access /sys/kernel/gpu/proc : " + e.toString());
            }
            return arrayList;
        }
    }
}
