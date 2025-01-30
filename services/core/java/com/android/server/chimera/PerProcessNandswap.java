package com.android.server.chimera;

import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.os.ProcessCpuTracker;
import com.android.internal.util.MemInfoReader;
import com.android.server.ServiceThread;
import com.android.server.chimera.PerProcessNandswap;
import com.android.server.chimera.SystemRepository;
import com.android.server.chimera.umr.UnifiedMemoryReclaimer;
import com.android.server.am.DynamicHiddenApp;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.rune.CoreRune;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public final class PerProcessNandswap {
    public static final String TAG = "PerProcessNandswap";
    public ChimeraStrategy mChimeraStrategy;
    public QuickSwap mQuickSwap;
    public SystemRepository mSystemRepository;
    public static final boolean FAST_MADVISE_ENABLED = CoreRune.FAST_MADVISE_ENABLED;
    public static final boolean IS_SHIP_BUILD = "true".equals(SystemProperties.get("ro.product_ship", "false"));
    public static final boolean IS_DEBUG_LEVEL_LOW = "0x4f4c".equals(SystemProperties.get("ro.boot.debug_level", "0x4f4c"));
    public static Boolean __DebugEnabled = null;
    public static Integer __PolicyVersion = null;
    public static Integer __MinSwapFreePercentage = null;
    public static Boolean __PrefetchActionEnabled = null;
    public static Boolean __WritebackOnBGEnabled = null;
    public static Boolean __WritebackOnFreezeEnabled = null;
    public static int __SlotCount = 0;
    public static ArrayList __SlotCountMap = null;
    public static Boolean __PsiEnabled = null;
    public static Integer __PsiThrottlingMS = null;
    public static Integer __PsiLowStallUS = null;
    public static Integer __PsiHighStallUS = null;
    public static Boolean __PrefetchSupported = null;
    public static final String[] NANDSWAP_EXCEPTION_PKGNAMES = {"com.sec.android.app.camera", "com.sec.android.gallery3d", "com.samsung.android.messaging"};
    public static PerProcessNandswap INSTANCE = null;
    public static int mAlwaysRunningQuotaPPNTriggerCnt = 0;
    public static int mAlwaysRunningQuotaPPNCnt = 0;
    public static final Integer STATUS_ALREADY_PPN = 0;
    public static final Integer STATUS_KEY_APP = 1;
    public boolean WRITEBACK_ENABLED = false;
    public Boolean __KeyAppEnable = null;
    public Boolean __QuickSwapEnable = null;
    public Boolean __PageoutCachedEmptyEnable = null;
    public ConcurrentHashMap mNandswapMap = new ConcurrentHashMap();
    public final ArrayList mPendingNandswapNonActivityApp = new ArrayList();
    public final ArrayList mPendingNandswapNonActivityAppDelayed = new ArrayList();
    public ServiceThread mNandswapThread = null;
    public ServiceThread mMsgThread = null;
    public MemoryPressureDetector mMemoryPressureDetector = null;
    public Handler mNandswapHandler = null;
    public NandswapMsgHandler mMsgHandler = null;
    public LinkedHashMap mLastNandswapStats = new LinkedHashMap() { // from class: com.android.server.chimera.PerProcessNandswap.1
        @Override // java.util.LinkedHashMap
        public boolean removeEldestEntry(Map.Entry entry) {
            return size() > 100;
        }
    };
    public final ArrayList mPendingNandswapActivityApp = new ArrayList();
    public final ArrayList mPendingNandswapActivityAppDelayed = new ArrayList();
    public final Map mKeyApps = new LinkedHashMap() { // from class: com.android.server.chimera.PerProcessNandswap.2
        @Override // java.util.LinkedHashMap
        public boolean removeEldestEntry(Map.Entry entry) {
            return size() > 30;
        }
    };

    private static native long __compactProcessForWriteback(int i, int i2);

    private static native long __compactProcessForWritebackFast(int i, int i2);

    private static native boolean __setWriteBoosterPath();

    /* JADX INFO: Access modifiers changed from: private */
    public native int initMemoryPressureDetectorNative(int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long prefetchProcess(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long prefetchProcessFast(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native int waitForMemoryPressure();

    static {
        staticInitialize();
    }

    public static final boolean isDebugEnabled() {
        if (__DebugEnabled == null) {
            __DebugEnabled = Boolean.valueOf(SystemProperties.getBoolean("ro.sys.kernelmemory.nandswap.debug", false));
        }
        return __DebugEnabled.booleanValue();
    }

    public static final int getPolicyVersion() {
        if (__PolicyVersion == null) {
            int i = isPrefetchSupported() ? 3 : 1;
            Integer valueOf = Integer.valueOf(SystemProperties.getInt("ro.sys.kernelmemory.nandswap.policy_version", i));
            __PolicyVersion = valueOf;
            if (valueOf.intValue() < 1 || __PolicyVersion.intValue() > 3) {
                Slog.e(TAG, "Invalid policy_version (" + __PolicyVersion + ").Reset it as " + i + ".");
                __PolicyVersion = Integer.valueOf(i);
            }
        }
        return __PolicyVersion.intValue();
    }

    public static final int getMinSwapFreePercentage() {
        if (__MinSwapFreePercentage == null) {
            __MinSwapFreePercentage = Integer.valueOf(SystemProperties.getInt("ro.sys.kernelmemory.nandswap.min_swap_free_percentage", 2));
        }
        return __MinSwapFreePercentage.intValue();
    }

    public static final boolean isPrefetchActionEnabled() {
        if (__PrefetchActionEnabled == null) {
            __PrefetchActionEnabled = Boolean.valueOf(SystemProperties.getBoolean("ro.sys.kernelmemory.nandswap.prefetch_action", getPolicyVersion() >= 3));
        }
        return __PrefetchActionEnabled.booleanValue();
    }

    public static final boolean isWritebackOnBGEnabled() {
        if (__WritebackOnBGEnabled == null) {
            __WritebackOnBGEnabled = Boolean.valueOf(SystemProperties.getBoolean("ro.sys.kernelmemory.nandswap.writeback_on_bg", getPolicyVersion() >= 3));
        }
        return __WritebackOnBGEnabled.booleanValue();
    }

    public static final boolean isWritebackOnFreezeEnabled() {
        if (__WritebackOnFreezeEnabled == null) {
            __WritebackOnFreezeEnabled = Boolean.valueOf(SystemProperties.getBoolean("ro.sys.kernelmemory.nandswap.writeback_on_bg", getPolicyVersion() >= 3));
        }
        return __WritebackOnFreezeEnabled.booleanValue();
    }

    public boolean isKeyAppEnable() {
        Boolean bool = this.__KeyAppEnable;
        if (bool == null) {
            Boolean valueOf = Boolean.valueOf(SystemProperties.getBoolean("ro.sys.kernelmemory.nandswap.key_app", false));
            this.__KeyAppEnable = valueOf;
            return valueOf.booleanValue();
        }
        return bool.booleanValue();
    }

    public boolean isQuickSwapEnable() {
        Boolean bool = this.__QuickSwapEnable;
        if (bool == null) {
            Boolean valueOf = Boolean.valueOf(SystemProperties.getBoolean("ro.sys.kernelmemory.nandswap.quickswap", false));
            this.__QuickSwapEnable = valueOf;
            return valueOf.booleanValue();
        }
        return bool.booleanValue();
    }

    public boolean isPageoutCachedEmptyEnable() {
        if (this.__PageoutCachedEmptyEnable == null) {
            this.__PageoutCachedEmptyEnable = Boolean.valueOf(SystemProperties.getBoolean("ro.sys.kernelmemory.nandswap.pageout_cached_empty", getPolicyVersion() >= 3));
        }
        return this.__PageoutCachedEmptyEnable.booleanValue() && this.mSystemRepository.useCompaction();
    }

    public static final int getSlotCount() {
        return __SlotCount;
    }

    public static final boolean decideSlotCount() {
        new MemInfoReader().readMemInfo();
        int swapTotalSizeKb = ((int) (((r0.getSwapTotalSizeKb() / 1024) + 127) / 128.0f)) * 128;
        ArrayList slotCountMap = getSlotCountMap();
        if (slotCountMap == null || slotCountMap.size() <= 0) {
            Slog.e(TAG, "invalid slotCountMap");
        } else {
            __SlotCount = ((Integer) ((Pair) slotCountMap.get(0)).second).intValue();
            int i = 0;
            while (true) {
                if (i >= slotCountMap.size()) {
                    break;
                }
                Pair pair = (Pair) slotCountMap.get(i);
                if (pair == null) {
                    Slog.e(TAG, "invalid slotCountMap item: " + i + "/" + slotCountMap.size());
                    __SlotCount = 0;
                    break;
                }
                int intValue = ((Integer) pair.first).intValue();
                int intValue2 = ((Integer) pair.second).intValue();
                if (swapTotalSizeKb >= intValue * 1024) {
                    __SlotCount = intValue2;
                }
                i++;
            }
        }
        Slog.d(TAG, "swap_total: " + swapTotalSizeKb + ", slot_count: " + __SlotCount);
        return true;
    }

    public static final ArrayList getSlotCountMap() {
        if (__SlotCountMap == null) {
            __SlotCountMap = stringToSlotCountMap(SystemProperties.get("ro.sys.kernelmemory.nandswap.slot_count_map", getPolicyVersion() >= 2 ? "default" : ""));
        }
        return __SlotCountMap;
    }

    public static ArrayList stringToSlotCountMap(String str) {
        String str2 = getPolicyVersion() >= 3 ? "5,6,8,8,12" : "5,6,7,8,9";
        ArrayList arrayList = new ArrayList();
        if (str != null) {
            try {
            } catch (Exception e) {
                e.printStackTrace();
                arrayList.clear();
                arrayList.add(new Pair(0, 0));
            }
            if (str.length() > 0) {
                if (str.equals("default")) {
                    str = str2;
                }
                String[] split = str.split(",");
                if (split.length % 2 == 0) {
                    throw new Exception("Invalid slot_count_map: " + str);
                }
                Slog.i(TAG, "slot_cout_map: " + str);
                int parseInt = Integer.parseInt(split[0]);
                if (parseInt < 0 || parseInt >= 100) {
                    throw new Exception("Invalid slot count: " + parseInt + " in " + str);
                }
                arrayList.add(new Pair(0, Integer.valueOf(parseInt)));
                int i = 0;
                int i2 = 0;
                while (i < split.length / 2) {
                    int i3 = i * 2;
                    int parseInt2 = Integer.parseInt(split[i3 + 1]);
                    int parseInt3 = Integer.parseInt(split[i3 + 2]);
                    if (parseInt2 <= 0 || parseInt2 >= 100 || i2 >= parseInt2) {
                        throw new Exception("Invalid thresholdGB: " + parseInt2 + " in " + str);
                    }
                    if (parseInt3 < 0 || parseInt3 >= 100 || parseInt >= parseInt3) {
                        throw new Exception("Invalid slot count: " + parseInt3 + " in " + str);
                    }
                    arrayList.add(new Pair(Integer.valueOf(parseInt2), Integer.valueOf(parseInt3)));
                    i++;
                    parseInt = parseInt3;
                    i2 = parseInt2;
                }
                return arrayList;
            }
        }
        Slog.w(TAG, "Empty slot_count_map");
        arrayList.clear();
        arrayList.add(new Pair(0, 0));
        return arrayList;
    }

    public static final String getSlotCountMapString() {
        ArrayList arrayList = __SlotCountMap;
        if (arrayList == null || arrayList.size() <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder("" + ((Pair) __SlotCountMap.get(0)).second);
        for (int i = 1; i < __SlotCountMap.size(); i++) {
            Pair pair = (Pair) __SlotCountMap.get(i);
            if (pair == null) {
                return "";
            }
            sb.append(",");
            sb.append(pair.first);
            sb.append(",");
            sb.append(pair.second);
        }
        return sb.toString();
    }

    public static final boolean isPsiEnabled() {
        boolean z = getPolicyVersion() == 2;
        if (__PsiEnabled == null) {
            __PsiEnabled = Boolean.valueOf(SystemProperties.getBoolean("ro.sys.kernelmemory.nandswap.psi", z));
        }
        return __PsiEnabled.booleanValue();
    }

    public static final int getPsiThrottlingMS() {
        if (__PsiThrottlingMS == null) {
            __PsiThrottlingMS = Integer.valueOf(SystemProperties.getInt("ro.sys.kernelmemory.nandswap.psi_throttling_ms", 5000));
        }
        return __PsiThrottlingMS.intValue();
    }

    public static final int getPsiLowStallUS() {
        if (__PsiLowStallUS == null) {
            __PsiLowStallUS = Integer.valueOf(SystemProperties.getInt("ro.sys.kernelmemory.nandswap.psi_low_stall_us", 80000));
        }
        return __PsiLowStallUS.intValue();
    }

    public static final int getPsiHighStallUS() {
        if (__PsiHighStallUS == null) {
            __PsiHighStallUS = Integer.valueOf(SystemProperties.getInt("ro.sys.kernelmemory.nandswap.psi_high_stall_us", 95000));
        }
        return __PsiHighStallUS.intValue();
    }

    public static final boolean isPrefetchSupported() {
        long prefetchProcess;
        if (__PrefetchSupported == null) {
            __PrefetchSupported = Boolean.FALSE;
            int i = 0;
            while (true) {
                if (i >= 5) {
                    break;
                }
                int myPid = Process.myPid();
                if (FAST_MADVISE_ENABLED) {
                    prefetchProcess = prefetchProcessFast(myPid);
                } else {
                    prefetchProcess = prefetchProcess(myPid);
                }
                String str = TAG;
                Slog.d(str, "prefetch trial: pid=" + myPid + " (" + i + ") ret=" + prefetchProcess);
                if (prefetchProcess > 0) {
                    __PrefetchSupported = Boolean.TRUE;
                    Slog.d(str, "prefetch supported (" + i + ")");
                    break;
                }
                i++;
            }
            if (!__PrefetchSupported.booleanValue()) {
                Slog.d(TAG, "prefetch not supported");
            }
        }
        return __PrefetchSupported.booleanValue();
    }

    public final void updateLastNandswapStats(int i, String str, long[] jArr) {
        this.mLastNandswapStats.remove(Integer.valueOf(i));
        this.mLastNandswapStats.put(Integer.valueOf(i), new LastNandswapStats(str, jArr));
    }

    public static void staticInitialize() {
        Slog.i(TAG, "Static Initialization of PerProcessNandswap");
    }

    public static synchronized PerProcessNandswap getInstance() {
        PerProcessNandswap perProcessNandswap;
        synchronized (PerProcessNandswap.class) {
            if (INSTANCE == null) {
                INSTANCE = new PerProcessNandswap();
            }
            perProcessNandswap = INSTANCE;
        }
        return perProcessNandswap;
    }

    public void notifyDiedAppToPPR(int i) {
        try {
            if (this.WRITEBACK_ENABLED && getSlotCount() > 0) {
                NandswapMsgHandler nandswapMsgHandler = this.mMsgHandler;
                nandswapMsgHandler.sendMessage(nandswapMsgHandler.obtainMessage(3, i, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyReentryAppToPPR(int i) {
        try {
            if (this.WRITEBACK_ENABLED && getSlotCount() > 0) {
                NandswapMsgHandler nandswapMsgHandler = this.mMsgHandler;
                nandswapMsgHandler.sendMessage(nandswapMsgHandler.obtainMessage(2, i, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onOomAdjChanged(int i, String str, int i2, int i3, int i4, int i5, boolean z, long j) {
        if (isWritebackEnabled()) {
            if (!z) {
                tryNandswapNonActivityApp(i, str, i3, i5, j);
            }
            if (getSlotCount() > 0) {
                if (i2 == 700 && i3 >= 850 && i3 <= 999 && z) {
                    notifyAppBecomeBG(i, str, i3);
                }
                if ((i2 > 200 || i4 > 5) && i3 >= 100 && i3 < 200 && i5 == 5) {
                    notifyReentryAppToPPR(i);
                }
            }
        }
    }

    public void onProcessFrozen(int i, String str, int i2, boolean z) {
        if (isWritebackEnabled() && getSlotCount() > 0 && i2 >= 850 && i2 <= 999 && z) {
            notifyAppBecomeFrozen(i, str, i2);
        }
    }

    public void notifyAppBecomeBG(int i, String str, int i2) {
        try {
            if (isWritebackOnBGEnabled()) {
                NandswapRecord nandswapRecord = new NandswapRecord(this, str, i, i2);
                NandswapMsgHandler nandswapMsgHandler = this.mMsgHandler;
                nandswapMsgHandler.sendMessage(nandswapMsgHandler.obtainMessage(6, 0, 0, nandswapRecord));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyAppBecomeFrozen(int i, String str, int i2) {
        try {
            if (isWritebackOnFreezeEnabled()) {
                NandswapRecord nandswapRecord = new NandswapRecord(this, str, i, i2);
                NandswapMsgHandler nandswapMsgHandler = this.mMsgHandler;
                nandswapMsgHandler.sendMessage(nandswapMsgHandler.obtainMessage(7, 0, 0, nandswapRecord));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyEntryAppToPPR(String str) {
        try {
            if (this.WRITEBACK_ENABLED && getSlotCount() > 0 && str != null) {
                NandswapMsgHandler nandswapMsgHandler = this.mMsgHandler;
                nandswapMsgHandler.sendMessage(nandswapMsgHandler.obtainMessage(4, 0, 0, str));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long compactProcessForWriteback(int i, int i2) {
        if (FAST_MADVISE_ENABLED) {
            return __compactProcessForWritebackFast(i, i2);
        }
        return __compactProcessForWriteback(i, i2);
    }

    public final class MemoryItem {
        public final String label;
        public final long lastNandswapTimeDiff;
        public final int ppnState;
        public ArrayList subitems;
        public final long swap;
        public final long writeback;

        public MemoryItem(String str, long j, long j2, int i, long j3) {
            this.label = str;
            this.swap = j;
            this.writeback = j2;
            this.ppnState = i;
            this.lastNandswapTimeDiff = j3;
        }
    }

    public static long[] getWritebackSizePid(int i) {
        if (i <= 0) {
            return null;
        }
        String[] strArr = {"Swap:", "Writeback:"};
        String str = "/proc/" + i + "/smaps_rollup";
        long[] jArr = new long[2];
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(str));
            int i2 = 0;
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    if (readLine.startsWith(strArr[i2])) {
                        if (readLine.split("\\s+").length == 3) {
                            jArr[i2] = Integer.parseInt(r7[1]);
                        }
                        i2++;
                        if (i2 == 2) {
                            break;
                        }
                    }
                } finally {
                }
            }
            bufferedReader.close();
        } catch (Exception unused) {
            if (isDebugEnabled()) {
                Slog.d(TAG, "failed to read " + str);
            }
        }
        if (jArr[1] == 0) {
            return null;
        }
        return jArr;
    }

    public static void sortMemoryItems(List list) {
        Collections.sort(list, new Comparator() { // from class: com.android.server.chimera.PerProcessNandswap.3
            @Override // java.util.Comparator
            public int compare(MemoryItem memoryItem, MemoryItem memoryItem2) {
                long j = memoryItem.writeback;
                long j2 = memoryItem2.writeback;
                if (j < j2) {
                    return 1;
                }
                return j > j2 ? -1 : 0;
            }
        });
    }

    public static void dumpMemoryItems(PrintWriter printWriter, List list) {
        for (int i = 0; i < list.size(); i++) {
            MemoryItem memoryItem = (MemoryItem) list.get(i);
            if (memoryItem.writeback != 0) {
                printWriter.println("    " + memoryItem.label + ": " + memoryItem.writeback);
                ArrayList arrayList = memoryItem.subitems;
                if (arrayList != null) {
                    sortMemoryItems(arrayList);
                    for (int i2 = 0; i2 < memoryItem.subitems.size(); i2++) {
                        MemoryItem memoryItem2 = (MemoryItem) memoryItem.subitems.get(i2);
                        printWriter.print("        ");
                        printWriter.println(memoryItem2.label + ": " + memoryItem2.writeback + " " + memoryItem2.swap + " " + memoryItem2.ppnState + " " + memoryItem2.lastNandswapTimeDiff);
                    }
                }
            }
        }
    }

    public final long[] getFieldFromNandswapMap(int i) {
        NandswapRecord nandswapRecord = (NandswapRecord) this.mNandswapMap.get(Integer.valueOf(i));
        if (nandswapRecord == null) {
            return null;
        }
        return new long[]{nandswapRecord.ppnState, nandswapRecord.lastNandswapTime};
    }

    public final void dumpProcessList(PrintWriter printWriter) {
        if (IS_SHIP_BUILD && IS_DEBUG_LEVEL_LOW) {
            return;
        }
        printWriter.println("\nProcessList state");
        dumpMemoryItems(printWriter, this.mSystemRepository.dumpProcessListForPPN(0, new BiFunction() { // from class: com.android.server.chimera.PerProcessNandswap$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                long[] fieldFromNandswapMap;
                fieldFromNandswapMap = ((PerProcessNandswap) obj).getFieldFromNandswapMap(((Integer) obj2).intValue());
                return fieldFromNandswapMap;
            }
        }));
    }

    public void dumpInfo(PrintWriter printWriter, String[] strArr) {
        QuickSwap quickSwap;
        String str;
        String str2;
        long prefetchProcess;
        NandswapRecord nandswapRecord;
        try {
            printWriter.println("== PerProcessNandswap dump start ==");
            if (strArr.length > 1) {
                int i = 3;
                if ("writeback".equals(strArr[1])) {
                    if (!IS_SHIP_BUILD) {
                        if (this.WRITEBACK_ENABLED && strArr.length >= 3) {
                            int parseInt = Integer.parseInt(strArr[2]);
                            int parseInt2 = strArr.length == 4 ? Integer.parseInt(strArr[3]) : 3;
                            if (parseInt2 > 0 && parseInt2 <= 3) {
                                i = parseInt2;
                            }
                            printWriter.println("writeback start " + parseInt + " " + i);
                            printWriter.println("writeback end " + parseInt + " " + i + " ret:" + compactProcessForWriteback(parseInt, i));
                            if ((i & 2) != 0 && (nandswapRecord = (NandswapRecord) this.mNandswapMap.get(Integer.valueOf(parseInt))) != null) {
                                nandswapRecord.ppnState = 2;
                                printWriter.println("changed ppnState " + parseInt);
                            }
                        }
                    } else {
                        printWriter.println("writeback cmd is not supported with ship build");
                    }
                } else if ("prefetch".equals(strArr[1])) {
                    if (!IS_SHIP_BUILD) {
                        if (this.WRITEBACK_ENABLED && isPrefetchActionEnabled() && strArr.length == 3) {
                            int parseInt3 = Integer.parseInt(strArr[2]);
                            printWriter.println("prefetch start " + parseInt3);
                            if (FAST_MADVISE_ENABLED) {
                                prefetchProcess = prefetchProcessFast(parseInt3);
                            } else {
                                prefetchProcess = prefetchProcess(parseInt3);
                            }
                            printWriter.println("prefetch end " + parseInt3 + " ret=" + prefetchProcess);
                        }
                    } else {
                        printWriter.println("prefetch cmd is not supported with ship build");
                    }
                } else if ("quotaPPN".equals(strArr[1])) {
                    if (!IS_SHIP_BUILD) {
                        printWriter.println("handleAlwaysRunningQuotaPPN " + strArr[2]);
                        handleAlwaysRunningQuotaPPN(Long.parseLong(strArr[2]));
                    }
                } else if ("quickswap".equals(strArr[1])) {
                    if (!IS_SHIP_BUILD) {
                        QuickSwap quickSwap2 = this.mQuickSwap;
                        if (quickSwap2 == null) {
                            printWriter.println("quickswap not enable!");
                        } else {
                            printWriter.println("quickswap trigger ret=" + quickSwap2.tryQuickSwap(1));
                        }
                    }
                } else if ("setprop".equals(strArr[1])) {
                    if (!IS_SHIP_BUILD) {
                        if (strArr.length >= 3 && (str = strArr[2]) != null && (str2 = strArr[3]) != null) {
                            if ("debug".equals(str)) {
                                __DebugEnabled = Boolean.valueOf(Boolean.parseBoolean(str2));
                            } else if ("slot_count_map".equals(str)) {
                                __SlotCountMap = stringToSlotCountMap(str2);
                                if (decideSlotCount() && getSlotCount() > 0) {
                                    initThreadAndHandler();
                                }
                            } else if ("psi".equals(str)) {
                                __PsiEnabled = Boolean.valueOf(Boolean.parseBoolean(str2));
                                if (isPsiEnabled()) {
                                    initThreadAndHandler();
                                }
                            } else if ("psi_throttling_ms".equals(str)) {
                                __PsiThrottlingMS = Integer.valueOf(Integer.parseInt(str2));
                                this.mMemoryPressureDetector.setTrialThrottlingMS(getPsiThrottlingMS());
                            } else if ("prefetch_action".equals(str)) {
                                __PrefetchActionEnabled = Boolean.valueOf(Boolean.parseBoolean(str2));
                            } else if ("writeback_on_bg".equals(str)) {
                                __WritebackOnBGEnabled = Boolean.valueOf(Boolean.parseBoolean(str2));
                            } else if ("writeback_on_freeze".equals(str)) {
                                __WritebackOnFreezeEnabled = Boolean.valueOf(Boolean.parseBoolean(str2));
                            } else if ("key_app".equals(str)) {
                                boolean isKeyAppEnable = isKeyAppEnable();
                                boolean parseBoolean = Boolean.parseBoolean(str2);
                                if (parseBoolean != isKeyAppEnable) {
                                    if (isKeyAppEnable) {
                                        this.mKeyApps.clear();
                                    }
                                    this.__KeyAppEnable = Boolean.valueOf(parseBoolean);
                                }
                            } else if ("quickswap".equals(str)) {
                                Boolean valueOf = Boolean.valueOf(Boolean.parseBoolean(str2));
                                this.__QuickSwapEnable = valueOf;
                                if (valueOf.booleanValue()) {
                                    if (this.mQuickSwap == null) {
                                        this.mQuickSwap = new QuickSwap(this, this.mSystemRepository);
                                    }
                                } else if (this.mQuickSwap != null) {
                                    this.mQuickSwap = null;
                                }
                            } else if (str.startsWith("quickswap.") && this.mQuickSwap != null) {
                                if (this.mQuickSwap.setProperty(str.substring(10, str.length()), str2)) {
                                    printWriter.println("reboot required to apply the quickswap config");
                                } else {
                                    printWriter.println("failed to apply the quickswap config");
                                }
                            } else if ("pageout_cached_empty".equals(str)) {
                                this.__PageoutCachedEmptyEnable = Boolean.valueOf(Boolean.parseBoolean(str2));
                            } else {
                                printWriter.println("invalid key for ppnandswap setprop: " + str);
                                return;
                            }
                            printWriter.println("set property " + str + "=" + str2 + " done");
                        } else {
                            printWriter.println("usage: dumpsys activity ppnandswap setprop <key> <value>");
                        }
                    } else {
                        printWriter.println("setprop cmd is not supported with ship build");
                    }
                }
            } else {
                printWriter.println("Configurations");
                printWriter.println("  feature enable: true");
                printWriter.println("  debug: " + isDebugEnabled());
                printWriter.println("  min_swap_free_percentage: " + getMinSwapFreePercentage());
                printWriter.println("  policy_version: " + getPolicyVersion());
                printWriter.println("    slot_count_map: " + getSlotCountMapString());
                printWriter.println("    writeback_on_bg: " + isWritebackOnBGEnabled());
                printWriter.println("    writeback_on_freeze: " + isWritebackOnFreezeEnabled());
                printWriter.println("    pageout_cached_empty: " + isPageoutCachedEmptyEnable());
                printWriter.println("    psi: " + isPsiEnabled());
                if (isPsiEnabled()) {
                    printWriter.println("      psi_throttling_ms: " + getPsiThrottlingMS());
                    printWriter.println("      psi_low_stall_us: " + getPsiLowStallUS());
                    printWriter.println("      psi_high_stall_us: " + getPsiHighStallUS());
                }
                printWriter.println("    key app enable: " + isKeyAppEnable());
                printWriter.println("    writeback enable: " + this.WRITEBACK_ENABLED);
                if (this.WRITEBACK_ENABLED) {
                    printWriter.println("      writeback limit enable: " + ZramInfo.isWritebackLimitEnabled());
                    printWriter.println("      prefetch_action: " + isPrefetchActionEnabled());
                    printWriter.println("      quota: " + ZramInfo.readZramWritebackLimit());
                    printWriter.println("      pending cached apps to be PPR count=" + this.mPendingNandswapActivityApp.size());
                    printWriter.println("      pending & delayed cached apps to be PPR count=" + this.mPendingNandswapActivityAppDelayed.size());
                }
                printWriter.println("  quickswap enable: " + isQuickSwapEnable());
                if (isQuickSwapEnable() && (quickSwap = this.mQuickSwap) != null) {
                    quickSwap.dump(printWriter);
                }
                if (this.WRITEBACK_ENABLED) {
                    dumpProcessList(printWriter);
                    printWriter.println("\nPPNandswap history");
                    NandswapLogger.print(printWriter);
                }
            }
        } catch (Exception unused) {
            printWriter.println("failed to dumpInfo");
        }
        printWriter.println("\n== PerProcessNandswap dump end ==");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void initThreadAndHandler() {
        Object[] objArr = 0;
        if (this.mNandswapThread == null) {
            ServiceThread serviceThread = new ServiceThread("PerProcessNandswapThread", 2, true);
            this.mNandswapThread = serviceThread;
            serviceThread.start();
            this.mNandswapHandler = new NandswapHandler();
            Process.setThreadGroupAndCpuset(this.mNandswapThread.getThreadId(), 2);
        }
        if (getSlotCount() > 0) {
            if (this.mMsgThread == null) {
                ServiceThread serviceThread2 = new ServiceThread("PPNandswapMsgThread", 0, true);
                this.mMsgThread = serviceThread2;
                serviceThread2.start();
                this.mMsgHandler = new NandswapMsgHandler(this);
            }
            if (isPsiEnabled() && this.mMemoryPressureDetector == null) {
                this.mMemoryPressureDetector = new MemoryPressureDetector(this, getPsiThrottlingMS(), getPsiLowStallUS(), getPsiHighStallUS());
            }
        }
    }

    public void init(SystemRepository systemRepository, ChimeraStrategy chimeraStrategy) {
        try {
            String str = TAG;
            Slog.i(str, "init start");
            if (!ZramInfo.isWritebackEnabled()) {
                Slog.w(str, "Writeback is disabled");
                this.WRITEBACK_ENABLED = false;
                return;
            }
            if (!decideSlotCount()) {
                Slog.w(str, "swap_total not found");
                this.WRITEBACK_ENABLED = false;
                return;
            }
            __setWriteBoosterPath();
            this.mSystemRepository = systemRepository;
            this.mChimeraStrategy = chimeraStrategy;
            initThreadAndHandler();
            if (isQuickSwapEnable()) {
                this.mQuickSwap = new QuickSwap(this, systemRepository);
            }
            this.WRITEBACK_ENABLED = true;
            Slog.i(str, "WritebackEnabled: " + ZramInfo.isWritebackEnabled());
            Slog.i(str, "PrefetchSupported: " + isPrefetchSupported());
            Slog.i(str, "WritebackLimitEnabled: " + ZramInfo.isWritebackLimitEnabled());
            Slog.i(str, "WritebackQuotaAvailable: " + ZramInfo.isWritebackQuotaAvailable());
            Slog.i(str, "quota: " + ZramInfo.readZramWritebackLimit());
            Slog.i(str, "policy_version: " + getPolicyVersion());
            Slog.i(str, "  slot_count: " + getSlotCount());
            Slog.i(str, "  psi: " + isPsiEnabled());
            Slog.i(str, "  writeback_on_bg: " + isWritebackOnBGEnabled());
            Slog.i(str, "  writeback_on_freeze: " + isWritebackOnFreezeEnabled());
            Slog.i(str, "  prefetch_action: " + isPrefetchActionEnabled());
            Slog.i(str, "init success");
        } catch (Exception unused) {
            Slog.e(TAG, "init failed");
            this.WRITEBACK_ENABLED = false;
        }
    }

    public boolean isWritebackEnabled() {
        return this.WRITEBACK_ENABLED;
    }

    public int getSwapFreePercentage(MemInfoReader memInfoReader) {
        long swapFreeSizeKb = memInfoReader.getSwapFreeSizeKb();
        long swapTotalSizeKb = memInfoReader.getSwapTotalSizeKb();
        long j = swapTotalSizeKb <= 0 ? 0L : (swapFreeSizeKb * 100) / swapTotalSizeKb;
        long j2 = j > 0 ? j : 0L;
        return (int) (j2 < 100 ? j2 : 100L);
    }

    public final int getActionFromAdj(int i, int i2) {
        if (isPageoutCachedEmptyEnable() && i2 >= 16 && i2 <= 19 && i >= 900 && i <= 999) {
            return 14;
        }
        if (!ChimeraCommonUtil.isQuotaEnable() && i >= 250 && i2 == 5) {
            return 8;
        }
        if (i == -800 || i == -700) {
            return 7;
        }
        if (i == 100) {
            return 6;
        }
        if (i == 200) {
            return 5;
        }
        if (i == 225) {
            return 4;
        }
        if (i == 250) {
            return 3;
        }
        if (i != 500) {
            return i != 800 ? 0 : 1;
        }
        return 2;
    }

    public void tryNandswapNonActivityApp(int i, String str, int i2, int i3, long j) {
        try {
            int actionFromAdj = getActionFromAdj(i2, i3);
            if (actionFromAdj != 0) {
                NandswapRecord nandswapRecord = (NandswapRecord) this.mNandswapMap.get(Integer.valueOf(i));
                if (nandswapRecord == null) {
                    nandswapRecord = new NandswapRecord(str, i, i2, i3);
                    this.mNandswapMap.put(Integer.valueOf(i), nandswapRecord);
                } else {
                    nandswapRecord.adj = i2;
                }
                if (shouldNandswapNonActivityApp(nandswapRecord, j)) {
                    if (isDebugEnabled()) {
                        Slog.i(TAG, "nandswap " + actionFromAdj + " pkg:" + str + " pid:" + i + " curAdj:" + i2);
                    }
                    nandswapNonActivityApp(nandswapRecord, i2, actionFromAdj);
                }
            }
        } catch (Exception unused) {
            Slog.e(TAG, "failed to tryNandswapNonActivityApp " + str + " " + i);
        }
    }

    public final boolean shouldNandswapNonActivityApp(NandswapRecord nandswapRecord, long j) {
        long j2 = nandswapRecord.lastNandswapTime;
        if (j2 == 0) {
            nandswapRecord.lastNandswapTime = j - 540000;
        } else if (j - j2 > 600000) {
            nandswapRecord.lastNandswapTime = j;
            return true;
        }
        return false;
    }

    public final void nandswapNonActivityApp(NandswapRecord nandswapRecord, int i, int i2) {
        if (this.mPendingNandswapNonActivityApp.size() < 20) {
            this.mPendingNandswapNonActivityApp.add(nandswapRecord);
            Handler handler = this.mNandswapHandler;
            handler.sendMessage(handler.obtainMessage(0, i, i2));
        } else if (isDebugEnabled()) {
            Slog.d(TAG, "Skipping send NANDSWAP_NON_ACTIVITY_APP_MSG");
        }
    }

    public final void nandswapActivityAppDelayed(NandswapRecord nandswapRecord) {
        this.mPendingNandswapActivityAppDelayed.add(nandswapRecord);
        Handler handler = this.mNandswapHandler;
        handler.sendMessageDelayed(handler.obtainMessage(3), 1000L);
    }

    public void requestChangePPRState(NandswapRecord nandswapRecord, int i) {
        requestChangePPRState(nandswapRecord, i, 0);
    }

    public final void requestChangePPRState(NandswapRecord nandswapRecord, int i, int i2) {
        if (getSlotCount() > 0) {
            NandswapMsgHandler nandswapMsgHandler = this.mMsgHandler;
            nandswapMsgHandler.sendMessage(nandswapMsgHandler.obtainMessage(1, i, i2, nandswapRecord));
        }
    }

    public boolean isAppLaunch() {
        return UnifiedMemoryReclaimer.isInAppLaunch();
    }

    public final class LastNandswapStats {
        public final String mName;
        public final long[] mRssAfterNandswap;

        public LastNandswapStats(String str, long[] jArr) {
            this.mName = str;
            this.mRssAfterNandswap = jArr;
        }

        public long[] getRssAfterNandswap() {
            return this.mRssAfterNandswap;
        }

        public String getName() {
            return this.mName;
        }
    }

    public final class NandswapHandler extends Handler {
        public final String TAG;
        public final String[] nativePackageNameFilter;

        public NandswapHandler() {
            super(PerProcessNandswap.this.mNandswapThread.getLooper());
            this.nativePackageNameFilter = new String[]{"vendor.samsung.hardware.camera.provider", "vendor.samsung.hardware.camera.provider-service_64", "cameraserver"};
            this.TAG = NandswapHandler.class.getSimpleName();
        }

        /* JADX WARN: Removed duplicated region for block: B:34:0x0122 A[Catch: Exception -> 0x01ca, TryCatch #0 {Exception -> 0x01ca, blocks: (B:2:0x0000, B:6:0x0009, B:14:0x001c, B:16:0x0036, B:18:0x0043, B:21:0x0050, B:23:0x0056, B:25:0x0076, B:26:0x007c, B:34:0x0122, B:37:0x012b, B:39:0x0132, B:41:0x00a8, B:44:0x00b1, B:46:0x00c1, B:55:0x008d, B:56:0x008e, B:57:0x0094, B:64:0x013b, B:66:0x013c, B:70:0x0152, B:71:0x016b, B:73:0x0173, B:76:0x01b2, B:79:0x01b6, B:81:0x01bc, B:83:0x01c4, B:87:0x015f, B:59:0x0095, B:60:0x00a1, B:28:0x007d, B:29:0x0089), top: B:1:0x0000, inners: #1, #2 }] */
        /* JADX WARN: Removed duplicated region for block: B:36:0x0129  */
        /* JADX WARN: Removed duplicated region for block: B:41:0x00a8 A[Catch: Exception -> 0x01ca, TRY_ENTER, TryCatch #0 {Exception -> 0x01ca, blocks: (B:2:0x0000, B:6:0x0009, B:14:0x001c, B:16:0x0036, B:18:0x0043, B:21:0x0050, B:23:0x0056, B:25:0x0076, B:26:0x007c, B:34:0x0122, B:37:0x012b, B:39:0x0132, B:41:0x00a8, B:44:0x00b1, B:46:0x00c1, B:55:0x008d, B:56:0x008e, B:57:0x0094, B:64:0x013b, B:66:0x013c, B:70:0x0152, B:71:0x016b, B:73:0x0173, B:76:0x01b2, B:79:0x01b6, B:81:0x01bc, B:83:0x01c4, B:87:0x015f, B:59:0x0095, B:60:0x00a1, B:28:0x007d, B:29:0x0089), top: B:1:0x0000, inners: #1, #2 }] */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void handleMessage(Message message) {
            NandswapRecord nandswapRecord;
            NandswapRecord nandswapRecord2;
            boolean z;
            try {
                if (!PerProcessNandswap.this.WRITEBACK_ENABLED) {
                    return;
                }
                int i = message.what;
                boolean z2 = true;
                if (i == 0 || i == 1) {
                    int i2 = message.arg1;
                    int i3 = message.arg2;
                    long uptimeMillis = SystemClock.uptimeMillis();
                    boolean isAppLaunch = PerProcessNandswap.this.isAppLaunch();
                    if (message.what != 1) {
                        z2 = false;
                    }
                    if (z2) {
                        nandswapRecord = (NandswapRecord) PerProcessNandswap.this.mPendingNandswapNonActivityAppDelayed.remove(0);
                    } else {
                        nandswapRecord = (NandswapRecord) PerProcessNandswap.this.mPendingNandswapNonActivityApp.remove(0);
                    }
                    nandswapRecord.lastNandswapTime = uptimeMillis;
                    if (PerProcessNandswap.isDebugEnabled()) {
                        Slog.d(this.TAG, "non_activity_app_msg: delayed:" + z2 + " " + nandswapRecord.processName + "(pid " + nandswapRecord.pid + ") action:" + i3 + " app launch:" + isAppLaunch);
                    }
                    if (isAppLaunch) {
                        if (z2) {
                            return;
                        }
                        nandswapNonActivityAppDelayed(nandswapRecord, i3, i2);
                        return;
                    } else {
                        int nandswapNonActivityAppType = getNandswapNonActivityAppType(nandswapRecord, i3, i2);
                        if (nandswapNonActivityAppType == 0 || doNandswapApp(nandswapRecord, i3, nandswapNonActivityAppType, false) <= 0) {
                            return;
                        }
                        PerProcessNandswap.this.requestChangePPRState(nandswapRecord, 2);
                        return;
                    }
                }
                if (i != 2 && i != 3) {
                    if (i == 4) {
                        nandSwapAlwaysRunningProcess(((Long) message.obj).longValue());
                        return;
                    }
                    Slog.w(this.TAG, "default: " + message.what);
                    return;
                }
                boolean isAppLaunch2 = PerProcessNandswap.this.isAppLaunch();
                boolean z3 = message.what == 3;
                if (PerProcessNandswap.isDebugEnabled()) {
                    Slog.d(this.TAG, "activity_app_msg: delayed:" + z3 + " app launch:" + isAppLaunch2);
                }
                if (z3) {
                    synchronized (PerProcessNandswap.this.mPendingNandswapActivityAppDelayed) {
                        nandswapRecord2 = (NandswapRecord) PerProcessNandswap.this.mPendingNandswapActivityAppDelayed.remove(0);
                    }
                    if (!isAppLaunch2) {
                        if (canDoNandswapProcess(nandswapRecord2)) {
                            long doNandswapApp = doNandswapApp(nandswapRecord2, 9, getNandswapActivityAppType(), true);
                            if (PerProcessNandswap.isDebugEnabled()) {
                                Slog.d(this.TAG, "activity_app_msg: delayed:" + z3 + " iter: " + nandswapRecord2.processName + " pid:" + nandswapRecord2.pid + " ret:" + doNandswapApp + " pending apps:" + PerProcessNandswap.this.mPendingNandswapActivityApp.size() + " delayed apps:" + PerProcessNandswap.this.mPendingNandswapActivityAppDelayed.size());
                            }
                            if (doNandswapApp > 0) {
                                z = false;
                                if (!z2) {
                                    PerProcessNandswap.this.requestChangePPRState(nandswapRecord2, 2);
                                    return;
                                } else if (z) {
                                    PerProcessNandswap.this.nandswapActivityAppDelayed(nandswapRecord2);
                                    return;
                                } else {
                                    PerProcessNandswap.this.requestChangePPRState(nandswapRecord2, 0);
                                    return;
                                }
                            }
                            if (doNandswapApp < 0) {
                            }
                        }
                        z = false;
                        z2 = false;
                        if (!z2) {
                        }
                    }
                    z = true;
                    z2 = false;
                    if (!z2) {
                    }
                } else {
                    synchronized (PerProcessNandswap.this.mPendingNandswapActivityApp) {
                        nandswapRecord2 = (NandswapRecord) PerProcessNandswap.this.mPendingNandswapActivityApp.remove(0);
                    }
                    if (!isAppLaunch2) {
                    }
                    z = true;
                    z2 = false;
                    if (!z2) {
                    }
                }
            } catch (Exception unused) {
                Slog.e(this.TAG, "failed to handleMessage " + message.what);
            }
        }

        public final int getNandswapNonActivityAppType(NandswapRecord nandswapRecord, int i, int i2) {
            int i3;
            boolean z;
            boolean isWritebackQuotaAvailable = ZramInfo.isWritebackQuotaAvailable();
            int i4 = nandswapRecord.pid;
            String str = nandswapRecord.processName;
            if (i4 == 0) {
                return 0;
            }
            if (Process.myPid() == i4) {
                if (PerProcessNandswap.isDebugEnabled()) {
                    Slog.d(this.TAG, "Skipping nandswap as process " + str + " is system_server.");
                }
                return 0;
            }
            if (PerProcessNandswap.this.isSamsungProtectApps(str)) {
                return 0;
            }
            if (PerProcessNandswap.this.mSystemRepository.getProcStateAndOomScoreForPid(i4)[1] != i2) {
                if (PerProcessNandswap.isDebugEnabled()) {
                    Slog.d(this.TAG, "Skipping nandswap for process" + str + " adj is changed");
                }
                return 0;
            }
            if (ChimeraCommonUtil.isQuotaEnable()) {
                if (isWritebackQuotaAvailable && (2 == i || 1 == i)) {
                    z = false;
                    i3 = 3;
                } else {
                    z = false;
                    i3 = 1;
                }
            } else if (i < 3 || i > 6) {
                i3 = isWritebackQuotaAvailable ? 3 : 1;
                z = false;
            } else {
                i3 = isWritebackQuotaAvailable ? 2 : 0;
                if (i3 == 0) {
                    return 0;
                }
                z = true;
            }
            long[] rss = Process.getRss(i4);
            if (rss[0] == 0 && rss[1] == 0 && rss[2] == 0 && rss[3] == 0) {
                if (PerProcessNandswap.isDebugEnabled()) {
                    Slog.d(this.TAG, "Skipping nandswap for process " + str + " with no memory usage. Dead?");
                }
                return 0;
            }
            if ((z ? rss[3] : rss[2]) < (z ? 10000L : 5000L)) {
                if (PerProcessNandswap.isDebugEnabled()) {
                    Slog.d(this.TAG, "Skipping nandswap for process " + str + "; anon RSS is too small: " + rss[2] + "KB.");
                }
                return 0;
            }
            LastNandswapStats lastNandswapStats = (LastNandswapStats) PerProcessNandswap.this.mLastNandswapStats.get(Integer.valueOf(i4));
            if (lastNandswapStats != null && !lastNandswapStats.getName().equals(str)) {
                if (PerProcessNandswap.isDebugEnabled()) {
                    Slog.d(this.TAG, "ignore lastNandswapStats because it is old stats for name: " + str + " stats.name: " + lastNandswapStats.getName());
                }
                lastNandswapStats = null;
            }
            if (lastNandswapStats != null) {
                long[] rssAfterNandswap = lastNandswapStats.getRssAfterNandswap();
                long j = rss[2] - rssAfterNandswap[2];
                long j2 = rss[3] - rssAfterNandswap[3];
                long j3 = i3 == 1 ? j : i3 == 2 ? j2 : j + j2;
                if (j3 <= 5000) {
                    if (PerProcessNandswap.isDebugEnabled()) {
                        Slog.d(this.TAG, "Skipping nandswap for process " + str + "; " + j + "," + j2 + " diff is too small: " + j3 + "KB.");
                    }
                    return 0;
                }
            }
            return i3;
        }

        public final int getNandswapActivityAppType() {
            return ZramInfo.isWritebackQuotaAvailable() ? 3 : 1;
        }

        public final void nandswapNonActivityAppDelayed(NandswapRecord nandswapRecord, int i, int i2) {
            if (PerProcessNandswap.this.mPendingNandswapNonActivityAppDelayed.size() < 20) {
                PerProcessNandswap.this.mPendingNandswapNonActivityAppDelayed.add(nandswapRecord);
                Handler handler = PerProcessNandswap.this.mNandswapHandler;
                handler.sendMessageDelayed(handler.obtainMessage(1, i2, i), 5000L);
            } else if (PerProcessNandswap.isDebugEnabled()) {
                Slog.d(this.TAG, "Skipping send NANDSWAP_NON_ACTIVITY_APP_DELAYED_MSG");
            }
        }

        public final boolean canDoNandswapProcess(NandswapRecord nandswapRecord) {
            int[] procStateAndOomScoreForPid = PerProcessNandswap.this.mSystemRepository.getProcStateAndOomScoreForPid(nandswapRecord.pid);
            if (procStateAndOomScoreForPid != null) {
                nandswapRecord.procState = procStateAndOomScoreForPid[0];
                nandswapRecord.adj = procStateAndOomScoreForPid[1];
            }
            if (nandswapRecord.ppnState == 1 && nandswapRecord.adj >= 830) {
                return true;
            }
            Slog.i(this.TAG, "canDoNandswapProcess fail: " + nandswapRecord.processName + " pid:" + nandswapRecord.pid + " state:" + nandswapRecord.ppnState + " adj:" + nandswapRecord.adj);
            return false;
        }

        public final long doNandswapApp(NandswapRecord nandswapRecord, int i, int i2, boolean z) {
            long j;
            long j2;
            NandswapHandler nandswapHandler;
            long j3;
            long j4;
            long[] jArr;
            int i3;
            String str;
            int i4;
            long j5;
            int i5;
            String str2;
            char c;
            char c2;
            String str3;
            long j6;
            long[] jArr2;
            int i6;
            long j7;
            int i7 = nandswapRecord.pid;
            String str4 = nandswapRecord.processName;
            int i8 = nandswapRecord.procState;
            int i9 = nandswapRecord.adj;
            String str5 = z ? "activity" : "non-activity";
            long j8 = -1;
            try {
                try {
                    Trace.traceBegin(64L, "nandswap" + str5 + " : " + str4 + ", " + (i2 == 1 ? "swaponly" : i2 == 2 ? "writebackonly" : "all"));
                    long[] readZramBdstat = ZramInfo.readZramBdstat();
                    long readZramWritebackLimit = ZramInfo.readZramWritebackLimit();
                    long[] rss = Process.getRss(i7);
                    long zramFreeKb = Debug.getZramFreeKb();
                    Slog.d(this.TAG, "nandswap start for " + str5 + " " + str4 + "(pid " + i7 + ") action:" + i + " adj:" + i9 + " type:" + i2);
                    long uptimeMillis = SystemClock.uptimeMillis();
                    long compactProcessForWriteback = PerProcessNandswap.compactProcessForWriteback(i7, i2);
                    try {
                        long uptimeMillis2 = SystemClock.uptimeMillis();
                        Slog.d(this.TAG, "nandswap end for " + str5 + " " + str4 + "(pid " + i7 + ") ret:" + compactProcessForWriteback + " adj:" + i9);
                        long j9 = uptimeMillis2 - uptimeMillis;
                        long[] readZramBdstat2 = ZramInfo.readZramBdstat();
                        long readZramWritebackLimit2 = ZramInfo.readZramWritebackLimit();
                        long[] rss2 = Process.getRss(i7);
                        long zramFreeKb2 = Debug.getZramFreeKb();
                        long j10 = zramFreeKb2 - zramFreeKb;
                        long j11 = (readZramWritebackLimit2 - readZramWritebackLimit) * 4;
                        if (compactProcessForWriteback > 0) {
                            j4 = zramFreeKb2;
                            c = 1;
                            jArr = rss2;
                            c2 = 2;
                            j = 64;
                            i3 = i9;
                            str = str5;
                            j3 = j9;
                            j2 = compactProcessForWriteback;
                            i5 = i8;
                            i4 = i7;
                            j5 = j10;
                            str2 = ", ";
                            str3 = str4;
                            try {
                                NandswapLogger.saveSwapOutLog(str4, i7, i, i2, j9, j10, j11);
                                nandswapHandler = this;
                                if (PerProcessNandswap.this.isKeyAppEnable()) {
                                    PerProcessNandswap.this.mKeyApps.put(str3, PerProcessNandswap.STATUS_ALREADY_PPN);
                                }
                            } catch (Exception unused) {
                                j8 = j2;
                                Trace.traceEnd(j);
                                return j8;
                            } catch (Throwable unused2) {
                                j8 = j2;
                                Trace.traceEnd(j);
                                return j8;
                            }
                        } else {
                            nandswapHandler = this;
                            j3 = j9;
                            j4 = zramFreeKb2;
                            jArr = rss2;
                            i3 = i9;
                            str = str5;
                            j2 = compactProcessForWriteback;
                            i4 = i7;
                            j5 = j10;
                            i5 = i8;
                            str2 = ", ";
                            c = 1;
                            c2 = 2;
                            j = 64;
                            str3 = str4;
                        }
                        if (PerProcessNandswap.isDebugEnabled()) {
                            String str6 = nandswapHandler.TAG;
                            StringBuilder sb = new StringBuilder();
                            sb.append("nandswap ");
                            sb.append(str);
                            sb.append(" for ");
                            sb.append(str3);
                            sb.append("(pid ");
                            sb.append(i4);
                            sb.append(") action:");
                            sb.append(i);
                            sb.append(", adj:");
                            i6 = i3;
                            sb.append(i6);
                            sb.append(", state:");
                            sb.append(i5);
                            sb.append(str2);
                            jArr2 = jArr;
                            sb.append(jArr2[c]);
                            sb.append(str2);
                            sb.append(jArr2[c2]);
                            sb.append(str2);
                            sb.append(jArr2[3]);
                            sb.append(str2);
                            long j12 = j4;
                            sb.append(j12);
                            sb.append(str2);
                            j4 = j12;
                            j6 = j3;
                            sb.append(j6);
                            sb.append(" ms, ");
                            sb.append(i2);
                            sb.append(str2);
                            j7 = uptimeMillis2;
                            sb.append(jArr2[c] - rss[c]);
                            sb.append(str2);
                            sb.append(jArr2[c2] - rss[c2]);
                            sb.append(str2);
                            sb.append(jArr2[3] - rss[3]);
                            sb.append(str2);
                            sb.append(j5);
                            sb.append(str2);
                            sb.append(j11);
                            Slog.d(str6, sb.toString());
                        } else {
                            j6 = j3;
                            jArr2 = jArr;
                            i6 = i3;
                            j7 = uptimeMillis2;
                        }
                        if (readZramBdstat != null && readZramBdstat2 != null) {
                            Object[] objArr = new Object[14];
                            objArr[0] = Integer.valueOf(i4);
                            objArr[c] = str3;
                            objArr[c2] = Integer.valueOf(i);
                            objArr[3] = Long.valueOf(j2);
                            objArr[4] = Integer.valueOf(i6);
                            objArr[5] = Integer.valueOf(i5);
                            objArr[6] = String.valueOf(j6) + "ms";
                            objArr[7] = Integer.valueOf(i2);
                            objArr[8] = Arrays.toString(readZramBdstat);
                            objArr[9] = Long.valueOf(readZramWritebackLimit);
                            objArr[10] = Long.valueOf(zramFreeKb);
                            objArr[11] = Arrays.toString(readZramBdstat2);
                            objArr[12] = Long.valueOf(readZramWritebackLimit2);
                            objArr[13] = Long.valueOf(j4);
                            EventLog.writeEvent(1300200, objArr);
                        }
                        nandswapRecord.lastNandswapTime = j7;
                        if (!z) {
                            PerProcessNandswap.this.updateLastNandswapStats(i4, str3, jArr2);
                        }
                        Trace.traceEnd(j);
                        return j2;
                    } catch (Exception unused3) {
                        j2 = compactProcessForWriteback;
                        j = 64;
                    } catch (Throwable unused4) {
                        j2 = compactProcessForWriteback;
                        j = 64;
                    }
                } catch (Exception unused5) {
                    j = 64;
                } catch (Throwable unused6) {
                    j = 64;
                }
            } catch (Exception unused7) {
                j = 64;
            } catch (Throwable unused8) {
                j = 64;
            }
        }

        public final void nandSwapAlwaysRunningProcess(long j) {
            int i;
            int i2;
            long j2;
            boolean z;
            long j3;
            long j4;
            int i3;
            boolean z2;
            long j5;
            long uptimeMillis = SystemClock.uptimeMillis();
            PerProcessNandswap.mAlwaysRunningQuotaPPNTriggerCnt++;
            ArrayList arrayList = new ArrayList();
            if (j > 0) {
                long resizeRemainReleaseTargetIfNesseray = resizeRemainReleaseTargetIfNesseray(j);
                boolean isWritebackQuotaAvailable = ZramInfo.isWritebackQuotaAvailable();
                long j6 = 0;
                int i4 = 0;
                for (Pair pair : getReclaimSetForNandswap()) {
                    int intValue = ((Integer) pair.first).intValue();
                    int intValue2 = ((Integer) pair.second).intValue();
                    if (intValue2 < 200 || intValue2 > 300) {
                        if (isWritebackQuotaAvailable) {
                            if (intValue2 >= 100 && intValue2 < 200) {
                                i2 = 11;
                            } else if (intValue2 == -700 || intValue2 == -800) {
                                i2 = 12;
                            } else if (intValue2 == -1000) {
                                i2 = 13;
                            } else {
                                i = 2;
                            }
                            i = 2;
                        } else {
                            i = 0;
                        }
                        i2 = 0;
                    } else {
                        i = isWritebackQuotaAvailable ? 3 : 1;
                        i2 = 10;
                    }
                    if (i == 0) {
                        if (PerProcessNandswap.isDebugEnabled()) {
                            Slog.d(this.TAG, "Quota PPN skip: none_type " + intValue + " " + intValue2 + " " + isWritebackQuotaAvailable);
                        }
                        quitNandSwapAlwaysRunningProcessWithWriteEventLog(-1, "NANDSWAP_TYPE_NONE", uptimeMillis, i4, j6, arrayList);
                        return;
                    }
                    if (PerProcessNandswap.this.isAppLaunch()) {
                        if (PerProcessNandswap.isDebugEnabled()) {
                            Slog.d(this.TAG, "Quota PPN skip: app launch " + intValue);
                        }
                        long j7 = j6;
                        quitNandSwapAlwaysRunningProcessWithWriteEventLog(-1, "app launched", uptimeMillis, i4, j7, arrayList);
                        long j8 = resizeRemainReleaseTargetIfNesseray - j7;
                        if (j8 > 51200) {
                            PerProcessNandswap.this.delayAlwaysRunningQuotaPPN(j8);
                            return;
                        }
                        return;
                    }
                    if (!isReachReclaimProcThrottleQuick(intValue, i)) {
                        if (PerProcessNandswap.isDebugEnabled()) {
                            Slog.d(this.TAG, "Quota PPN skip: reclaim throttle quick " + intValue);
                        }
                    } else {
                        NandswapRecord nandswapRecord = (NandswapRecord) PerProcessNandswap.this.mNandswapMap.get(Integer.valueOf(intValue));
                        if (nandswapRecord == null) {
                            if (PerProcessNandswap.isDebugEnabled()) {
                                Slog.d(this.TAG, "Quota PPN skip: no nandswap record" + intValue);
                            }
                        } else {
                            long[] anonSizeAndZramSize = ChimeraCommonUtil.getAnonSizeAndZramSize(intValue);
                            j3 = uptimeMillis;
                            j2 = j6;
                            i3 = i4;
                            z = isWritebackQuotaAvailable;
                            j4 = resizeRemainReleaseTargetIfNesseray;
                            if (!isReachReclaimProcThrottleSlow(intValue, i, anonSizeAndZramSize[0], anonSizeAndZramSize[1])) {
                                if (PerProcessNandswap.isDebugEnabled()) {
                                    Slog.d(this.TAG, "Quota PPN skip: reclaim throttle slow " + intValue);
                                }
                                i4 = i3;
                                isWritebackQuotaAvailable = z;
                                resizeRemainReleaseTargetIfNesseray = j4;
                                uptimeMillis = j3;
                                j6 = j2;
                            } else {
                                long doNandswapApp = doNandswapApp(nandswapRecord, i2, i, false);
                                if (doNandswapApp > 0) {
                                    i4 = i3 + 1;
                                    PerProcessNandswap.mAlwaysRunningQuotaPPNCnt++;
                                    arrayList.add(Integer.valueOf(intValue));
                                    PerProcessNandswap.this.requestChangePPRState(nandswapRecord, 2);
                                    long[] anonSizeAndZramSize2 = ChimeraCommonUtil.getAnonSizeAndZramSize(intValue);
                                    long j9 = (anonSizeAndZramSize[0] + anonSizeAndZramSize[1]) - anonSizeAndZramSize2[0];
                                    if (PerProcessNandswap.isDebugEnabled()) {
                                        String str = this.TAG;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append("Quota PPN reclaimed: ");
                                        sb.append(intValue);
                                        sb.append(" ");
                                        sb.append(j9);
                                        sb.append(" ");
                                        sb.append(anonSizeAndZramSize[0]);
                                        sb.append(" ");
                                        z2 = true;
                                        sb.append(anonSizeAndZramSize[1]);
                                        sb.append(" ");
                                        sb.append(anonSizeAndZramSize2[0]);
                                        sb.append(" ");
                                        sb.append(anonSizeAndZramSize2[1]);
                                        Slog.d(str, sb.toString());
                                    } else {
                                        z2 = true;
                                    }
                                    j5 = 0;
                                    if (j9 > 0) {
                                        long j10 = j2 + j9;
                                        if (j10 >= j4) {
                                            if (PerProcessNandswap.isDebugEnabled()) {
                                                Slog.d(this.TAG, "Quota PPN complete: nandswap " + i4 + " process, reclaim " + j10 + " KB");
                                            }
                                            quitNandSwapAlwaysRunningProcessWithWriteEventLog(1, "complete", j3, i4, j10, arrayList);
                                            return;
                                        }
                                        j6 = j10;
                                    } else {
                                        j6 = j2;
                                    }
                                } else {
                                    z2 = true;
                                    j5 = 0;
                                    if (PerProcessNandswap.isDebugEnabled()) {
                                        Slog.d(this.TAG, "Quota PPN failed: ret " + doNandswapApp + "pid " + intValue + " nandswapAction " + i2 + " nandswapType " + i);
                                    }
                                    j6 = j2;
                                    i4 = i3;
                                }
                                isWritebackQuotaAvailable = z;
                                resizeRemainReleaseTargetIfNesseray = j4;
                                uptimeMillis = j3;
                            }
                        }
                    }
                    j2 = j6;
                    z = isWritebackQuotaAvailable;
                    j3 = uptimeMillis;
                    j4 = resizeRemainReleaseTargetIfNesseray;
                    i3 = i4;
                    i4 = i3;
                    isWritebackQuotaAvailable = z;
                    resizeRemainReleaseTargetIfNesseray = j4;
                    uptimeMillis = j3;
                    j6 = j2;
                }
                long j11 = uptimeMillis;
                int i5 = i4;
                long j12 = j6;
                if (PerProcessNandswap.isDebugEnabled()) {
                    Slog.d(this.TAG, "Quota PPN reclaim not enough: nandswap " + i5 + " process, reclaim " + j12 + " KB");
                }
                quitNandSwapAlwaysRunningProcessWithWriteEventLog(0, "not enough", j11, i5, j12, arrayList);
                return;
            }
            quitNandSwapAlwaysRunningProcessWithWriteEventLog(-1, "Target < 0", uptimeMillis, 0, 0L, arrayList);
        }

        public final long resizeRemainReleaseTargetIfNesseray(long j) {
            if (j > 512000) {
                if (PerProcessNandswap.isDebugEnabled()) {
                    Slog.d(this.TAG, "sizeToReclaim is too big: " + j + ",reset to max value: 512000");
                }
                j = 512000;
            }
            long readZramWritebackLimit = ZramInfo.readZramWritebackLimit() * 4;
            if (j <= readZramWritebackLimit) {
                return j;
            }
            if (PerProcessNandswap.isDebugEnabled()) {
                Slog.d(this.TAG, "sizeToReclaim is too big: " + j + ",reset to zramWriteBacklimitKb: " + readZramWritebackLimit);
            }
            return readZramWritebackLimit;
        }

        public final Set getReclaimSetForNandswap() {
            TreeSet treeSet = new TreeSet(new Comparator() { // from class: com.android.server.chimera.PerProcessNandswap$NandswapHandler$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int lambda$getReclaimSetForNandswap$0;
                    lambda$getReclaimSetForNandswap$0 = PerProcessNandswap.NandswapHandler.lambda$getReclaimSetForNandswap$0((Pair) obj, (Pair) obj2);
                    return lambda$getReclaimSetForNandswap$0;
                }
            });
            List<SystemRepository.RunningAppProcessInfo> runningAppProcesses = PerProcessNandswap.this.mSystemRepository.getRunningAppProcesses();
            List<ProcessCpuTracker.Stats> nativeProcesses = PerProcessNandswap.this.mSystemRepository.getNativeProcesses((Set) runningAppProcesses.stream().map(new Function() { // from class: com.android.server.chimera.PerProcessNandswap$NandswapHandler$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    Integer lambda$getReclaimSetForNandswap$1;
                    lambda$getReclaimSetForNandswap$1 = PerProcessNandswap.NandswapHandler.lambda$getReclaimSetForNandswap$1((SystemRepository.RunningAppProcessInfo) obj);
                    return lambda$getReclaimSetForNandswap$1;
                }
            }).collect(Collectors.toSet()));
            for (SystemRepository.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                int i = PerProcessNandswap.this.mSystemRepository.getProcStateAndOomScoreForPid(runningAppProcessInfo.pid)[1];
                if ((i >= 100 && i < 300) || i == -700 || i == -800) {
                    if (!PerProcessNandswap.this.isSamsungProtectApps(runningAppProcessInfo.processName)) {
                        treeSet.add(new Pair(Integer.valueOf(runningAppProcessInfo.pid), Integer.valueOf(i)));
                        PerProcessNandswap.this.checkProcessStatusForNandswap(runningAppProcessInfo.processName, runningAppProcessInfo.pid, i);
                    }
                }
            }
            for (ProcessCpuTracker.Stats stats : nativeProcesses) {
                String[] packageNameFromUid = PerProcessNandswap.this.mSystemRepository.getPackageNameFromUid(stats.uid);
                if (packageNameFromUid.length < 1) {
                    packageNameFromUid = new String[]{stats.name};
                }
                if (!isFilteredNativePackage(packageNameFromUid) && !PerProcessNandswap.this.isSamsungProtectApps(stats.name)) {
                    treeSet.add(new Pair(Integer.valueOf(stats.pid), -1000));
                    PerProcessNandswap.this.checkProcessStatusForNandswap(stats.name, stats.pid, -1000);
                }
            }
            return treeSet;
        }

        public static /* synthetic */ int lambda$getReclaimSetForNandswap$0(Pair pair, Pair pair2) {
            if (((Integer) pair2.second).intValue() - ((Integer) pair.second).intValue() == 0) {
                return 1;
            }
            return ((Integer) pair2.second).intValue() - ((Integer) pair.second).intValue();
        }

        public static /* synthetic */ Integer lambda$getReclaimSetForNandswap$1(SystemRepository.RunningAppProcessInfo runningAppProcessInfo) {
            return Integer.valueOf(runningAppProcessInfo.pid);
        }

        public final boolean isReachReclaimProcThrottleQuick(int i, int i2) {
            long j;
            long[] rss = Process.getRss(i);
            if (rss[0] == 0 && rss[1] == 0 && rss[2] == 0 && rss[3] == 0) {
                return false;
            }
            if (i2 == 3) {
                j = rss[2] + rss[3];
            } else if (i2 == 1) {
                j = rss[2];
            } else {
                j = rss[3];
            }
            if (PerProcessNandswap.isDebugEnabled() && j <= 5000) {
                Slog.d(this.TAG, "Quota PPN skip: reclaim throttle quick " + i + " " + j + " 5000");
            }
            return j > 5000;
        }

        public final boolean isReachReclaimProcThrottleSlow(int i, long j, long j2, long j3) {
            if (j2 == 0 && j3 == 0) {
                return false;
            }
            long j4 = 5000;
            if (j == 3) {
                j2 += j3;
            } else if (j != 1) {
                j4 = 1650;
                j2 = j3;
            }
            if (PerProcessNandswap.isDebugEnabled() && j2 <= j4) {
                Slog.d(this.TAG, "Quota PPN skip: reclaim throttle slow " + i + " " + j2 + " " + j4);
            }
            return j2 > j4;
        }

        public final boolean isFilteredNativePackage(String[] strArr) {
            for (String str : this.nativePackageNameFilter) {
                if (strArr[0].contains(str)) {
                    return true;
                }
            }
            return false;
        }

        public final void quitNandSwapAlwaysRunningProcessWithWriteEventLog(int i, String str, long j, int i2, long j2, List list) {
            long uptimeMillis = SystemClock.uptimeMillis();
            List subList = list.size() > 30 ? list.subList(0, 30) : list;
            EventLog.writeEvent(1300201, Integer.valueOf(i), str, (uptimeMillis - j) + "ms", Integer.valueOf(i2), Long.valueOf(j2), subList.toString());
        }
    }

    public void checkProcessStatusForNandswap(String str, int i, int i2) {
        NandswapRecord nandswapRecord = (NandswapRecord) this.mNandswapMap.get(Integer.valueOf(i));
        if (nandswapRecord == null) {
            this.mNandswapMap.put(Integer.valueOf(i), new NandswapRecord(this, str, i, i2));
        } else {
            nandswapRecord.adj = i2;
        }
    }

    public void handleAlwaysRunningQuotaPPN(long j) {
        Handler handler;
        if (!isWritebackEnabled() || (handler = this.mNandswapHandler) == null) {
            return;
        }
        Message obtain = Message.obtain(handler, 4);
        obtain.obj = Long.valueOf(j);
        this.mNandswapHandler.sendMessage(obtain);
    }

    public final void delayAlwaysRunningQuotaPPN(long j) {
        Message obtain = Message.obtain(this.mNandswapHandler, 4);
        obtain.obj = Long.valueOf(j);
        this.mNandswapHandler.sendMessageDelayed(obtain, 5000L);
    }

    public final boolean isSamsungProtectApps(String str) {
        return KnoxCustomManagerService.LAUNCHER_PACKAGE.equals(str) || KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME.equals(str) || "com.android.systemui".equals(str);
    }

    public final boolean isKeyApp(String str) {
        if (!isKeyAppEnable() || !STATUS_KEY_APP.equals(this.mKeyApps.get(str))) {
            return false;
        }
        long availableMemory = this.mSystemRepository.getAvailableMemory();
        return availableMemory > this.mChimeraStrategy.getFreeMemTarget(availableMemory);
    }

    public final class NandswapMsgHandler extends Handler {
        public final String[] LAUNCHER_APP_PKGNAME;
        public final int RECENT_ENTRY_PROCESS_COUNT;
        public final String TAG;
        public ArrayList recentEntryProcessNames;
        public final PerProcessNandswap self;

        public NandswapMsgHandler(PerProcessNandswap perProcessNandswap) {
            super(PerProcessNandswap.this.mMsgThread.getLooper());
            this.LAUNCHER_APP_PKGNAME = new String[]{KnoxCustomManagerService.LAUNCHER_PACKAGE};
            this.recentEntryProcessNames = new ArrayList();
            this.RECENT_ENTRY_PROCESS_COUNT = 2;
            this.TAG = NandswapMsgHandler.class.getSimpleName();
            this.self = perProcessNandswap;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            NandswapRecord nandswapRecord;
            NandswapRecord nandswapRecord2;
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (PerProcessNandswap.this.WRITEBACK_ENABLED && PerProcessNandswap.getSlotCount() > 0) {
                boolean z = true;
                switch (message.what) {
                    case 1:
                        NandswapRecord nandswapRecord3 = (NandswapRecord) message.obj;
                        int i = message.arg1;
                        int i2 = message.arg2;
                        String str = nandswapRecord3.processName;
                        int i3 = nandswapRecord3.pid;
                        if (PerProcessNandswap.isDebugEnabled()) {
                            Slog.d(this.TAG, "change_ppr_state_msg: " + str + " pid:" + i3 + " ppnState:" + i + " action:" + i2);
                        }
                        changePPRState(nandswapRecord3, i);
                        break;
                    case 2:
                        int i4 = message.arg1;
                        NandswapRecord nandswapRecord4 = (NandswapRecord) PerProcessNandswap.this.mNandswapMap.get(Integer.valueOf(i4));
                        if (nandswapRecord4 != null) {
                            if (PerProcessNandswap.this.isKeyAppEnable() && PerProcessNandswap.STATUS_ALREADY_PPN.equals(PerProcessNandswap.this.mKeyApps.get(nandswapRecord4.processName)) && !PerProcessNandswap.this.isSamsungProtectApps(nandswapRecord4.processName)) {
                                PerProcessNandswap.this.mKeyApps.put(nandswapRecord4.processName, PerProcessNandswap.STATUS_KEY_APP);
                            }
                            int i5 = nandswapRecord4.ppnState;
                            if (i5 == 1 || i5 == 2 || i5 == 4) {
                                if (PerProcessNandswap.isDebugEnabled()) {
                                    Slog.d(this.TAG, "app_reentry_msg: " + nandswapRecord4.processName + " pid:" + i4);
                                }
                                if (PerProcessNandswap.isPrefetchActionEnabled()) {
                                    Slog.i(this.TAG, "madvise_prefetch for " + nandswapRecord4.processName);
                                    if (PerProcessNandswap.FAST_MADVISE_ENABLED) {
                                        PerProcessNandswap.prefetchProcessFast(i4);
                                    } else {
                                        PerProcessNandswap.prefetchProcess(i4);
                                    }
                                }
                                changePPRState(nandswapRecord4, 0);
                                break;
                            }
                        }
                        break;
                    case 3:
                        int i6 = message.arg1;
                        NandswapRecord nandswapRecord5 = (NandswapRecord) PerProcessNandswap.this.mNandswapMap.get(Integer.valueOf(i6));
                        if (nandswapRecord5 != null) {
                            if (PerProcessNandswap.this.isKeyAppEnable()) {
                                PerProcessNandswap.this.mKeyApps.remove(nandswapRecord5.processName);
                            }
                            if (PerProcessNandswap.isDebugEnabled()) {
                                Slog.d(this.TAG, "app_died_msg: " + nandswapRecord5.processName + " pid:" + nandswapRecord5.pid);
                            }
                            PerProcessNandswap.this.mNandswapMap.remove(Integer.valueOf(i6));
                            break;
                        }
                        break;
                    case 4:
                        String str2 = (String) message.obj;
                        String[] strArr = this.LAUNCHER_APP_PKGNAME;
                        int length = strArr.length;
                        int i7 = 0;
                        while (true) {
                            if (i7 < length) {
                                if (str2.equals(strArr[i7])) {
                                    z = false;
                                } else {
                                    i7++;
                                }
                            }
                        }
                        if (!z) {
                            if (PerProcessNandswap.isDebugEnabled()) {
                                Slog.d(this.TAG, "app_entry_msg: reject launcher app " + str2);
                                break;
                            }
                        } else {
                            int i8 = 0;
                            while (true) {
                                if (i8 < this.recentEntryProcessNames.size()) {
                                    if (str2.equals((String) this.recentEntryProcessNames.get(i8))) {
                                        this.recentEntryProcessNames.remove(i8);
                                        z = false;
                                    } else {
                                        i8++;
                                    }
                                }
                            }
                            this.recentEntryProcessNames.add(str2);
                            if (!z) {
                                if (PerProcessNandswap.isDebugEnabled()) {
                                    Slog.d(this.TAG, "app_entry_msg: reject recent app " + str2);
                                    break;
                                }
                            } else {
                                if (this.recentEntryProcessNames.size() > 2) {
                                    this.recentEntryProcessNames.remove(0);
                                }
                                if (PerProcessNandswap.isDebugEnabled()) {
                                    Slog.d(this.TAG, "app_entry_msg: " + str2);
                                    break;
                                }
                            }
                        }
                        break;
                    case 5:
                        if (PerProcessNandswap.isPsiEnabled()) {
                            if (PerProcessNandswap.isDebugEnabled()) {
                                int i9 = message.arg1;
                                Slog.d(this.TAG, "try_to_nandswap_by_psi_event_msg: " + i9);
                            }
                            tryToNandswapByPsiEvent();
                            break;
                        }
                        break;
                    case 6:
                        if (PerProcessNandswap.isWritebackOnBGEnabled() && (nandswapRecord = (NandswapRecord) message.obj) != null) {
                            if (PerProcessNandswap.isDebugEnabled()) {
                                Slog.d(this.TAG, "try_to_nandswap_by_bg_event_msg: " + nandswapRecord.processName + " pid: " + nandswapRecord.pid);
                            }
                            tryToNandswapByBgEvent(nandswapRecord);
                            break;
                        }
                        break;
                    case 7:
                        if (PerProcessNandswap.isWritebackOnFreezeEnabled() && (nandswapRecord2 = (NandswapRecord) message.obj) != null) {
                            if (PerProcessNandswap.isDebugEnabled()) {
                                Slog.d(this.TAG, "try_to_nandswap_by_freeze_event_msg: " + nandswapRecord2.processName + " pid: " + nandswapRecord2.pid);
                            }
                            tryToNandswapByFreezeEvent(nandswapRecord2);
                            break;
                        }
                        break;
                    default:
                        Slog.w(this.TAG, "default: " + message.what);
                        break;
                }
            }
        }

        public final boolean checkCanDoPPRForCachedAppPsi(int i) {
            NandswapRecord nandswapRecord = (NandswapRecord) PerProcessNandswap.this.mNandswapMap.get(Integer.valueOf(i));
            return nandswapRecord == null || nandswapRecord.ppnState == 0;
        }

        public final boolean checkCanDoPPRForCachedApp(NandswapRecord nandswapRecord) {
            NandswapRecord nandswapRecord2 = (NandswapRecord) PerProcessNandswap.this.mNandswapMap.get(Integer.valueOf(nandswapRecord.pid));
            if (nandswapRecord2 == null) {
                return true;
            }
            return nandswapRecord2.ppnState == 0 && checkTimeoutPPRForCachedApp(nandswapRecord2);
        }

        public final boolean checkTimeoutPPRForCachedApp(NandswapRecord nandswapRecord) {
            return nandswapRecord.lastNandswapTime == 0 || SystemClock.uptimeMillis() - nandswapRecord.lastNandswapTime > 60000;
        }

        public final boolean checkDHAHeavyProcess(NandswapRecord nandswapRecord) {
            DynamicHiddenApp dynamicHiddenApp = DynamicHiddenApp.getInstance();
            return dynamicHiddenApp != null && dynamicHiddenApp.IsForceKillHeavyProcess(nandswapRecord.processName);
        }

        public final boolean isExceptionApp(NandswapRecord nandswapRecord) {
            for (String str : PerProcessNandswap.NANDSWAP_EXCEPTION_PKGNAMES) {
                if (str.equals(nandswapRecord.processName)) {
                    return true;
                }
            }
            return false;
        }

        public final void tryToNandswapByBgEvent(NandswapRecord nandswapRecord) {
            try {
                if (checkCanDoPPRForCachedApp(nandswapRecord) && !tryToNandswapProcess(nandswapRecord) && PerProcessNandswap.isDebugEnabled()) {
                    Slog.w(this.TAG, "try_to_nandswap_by_bg_event not accepted " + nandswapRecord.processName + " pid: " + nandswapRecord.pid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public final void tryToNandswapByFreezeEvent(NandswapRecord nandswapRecord) {
            try {
                if (checkCanDoPPRForCachedApp(nandswapRecord) && !tryToNandswapProcess(nandswapRecord) && PerProcessNandswap.isDebugEnabled()) {
                    Slog.w(this.TAG, "try_to_nandswap_by_freeze_event not accepted " + nandswapRecord.processName + " pid: " + nandswapRecord.pid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public final void tryToNandswapByPsiEvent() {
            List runningAppProcesses;
            int i;
            try {
                if (checkPPRCondition() && (runningAppProcesses = PerProcessNandswap.this.mSystemRepository.getRunningAppProcesses()) != null) {
                    String currentHomePackageName = PerProcessNandswap.this.mSystemRepository.getCurrentHomePackageName();
                    int i2 = 1;
                    for (int size = runningAppProcesses.size() - 1; size >= 0 && i2 > 0; size--) {
                        SystemRepository.RunningAppProcessInfo runningAppProcessInfo = (SystemRepository.RunningAppProcessInfo) runningAppProcesses.get(size);
                        String[] strArr = runningAppProcessInfo.pkgList;
                        if (strArr != null && strArr.length > 0 && !TextUtils.equals(strArr[0], currentHomePackageName) && (runningAppProcessInfo.flags & 4) > 0 && (i = PerProcessNandswap.this.mSystemRepository.getProcStateAndOomScoreForPid(runningAppProcessInfo.pid)[1]) >= 850 && checkCanDoPPRForCachedAppPsi(runningAppProcessInfo.pid) && tryToNandswapProcess(new NandswapRecord(PerProcessNandswap.this, runningAppProcessInfo.processName, runningAppProcessInfo.pid, i))) {
                            i2--;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public final boolean tryToNandswapProcess(NandswapRecord nandswapRecord) {
            String str = "Nandswap check_ppr (pid:" + nandswapRecord.pid + " / name: " + nandswapRecord.processName + ")";
            if (!checkPPRCondition()) {
                denyPPRRequest(nandswapRecord);
                return false;
            }
            if (!checkPPRAppCondition(nandswapRecord)) {
                denyPPRRequest(nandswapRecord);
                return false;
            }
            if (PerProcessNandswap.this.isKeyApp(nandswapRecord.processName)) {
                if (PerProcessNandswap.isDebugEnabled()) {
                    Slog.d(this.TAG, str + " deny by key app");
                }
                denyPPRRequest(nandswapRecord);
                return false;
            }
            if (PerProcessNandswap.isDebugEnabled()) {
                Slog.d(this.TAG, str + " accept");
            }
            startNandswapProcess(acceptPPRRequest(nandswapRecord));
            return true;
        }

        public final boolean checkPPRCondition() {
            if (!PerProcessNandswap.this.WRITEBACK_ENABLED || PerProcessNandswap.getSlotCount() <= 0) {
                if (PerProcessNandswap.isDebugEnabled()) {
                    Slog.d(this.TAG, "Nandswap check_ppr:  deny reason: disabled");
                }
                return false;
            }
            if (PerProcessNandswap.this.mPendingNandswapActivityApp.size() >= 20) {
                if (PerProcessNandswap.isDebugEnabled()) {
                    Slog.d(this.TAG, "Nandswap check_ppr:  deny reason: pending app count " + PerProcessNandswap.this.mPendingNandswapActivityApp.size());
                }
                return false;
            }
            if (PerProcessNandswap.this.mPendingNandswapActivityAppDelayed.size() >= 20) {
                if (PerProcessNandswap.isDebugEnabled()) {
                    Slog.d(this.TAG, "Nandswap check_ppr:  deny reason: delayed app count " + PerProcessNandswap.this.mPendingNandswapActivityAppDelayed.size());
                }
                return false;
            }
            MemInfoReader memInfoReader = new MemInfoReader();
            memInfoReader.readMemInfo();
            if (PerProcessNandswap.this.getSwapFreePercentage(memInfoReader) >= PerProcessNandswap.getMinSwapFreePercentage()) {
                return true;
            }
            if (PerProcessNandswap.isDebugEnabled()) {
                Slog.d(this.TAG, "Nandswap check_ppr:  deny reason: swap space not enough - free " + memInfoReader.getSwapFreeSizeKb() + " KB over " + memInfoReader.getSwapTotalSizeKb() + " KB");
            }
            return false;
        }

        public final boolean checkPPRAppCondition(NandswapRecord nandswapRecord) {
            String str = "Nandswap check_ppr_app: pid " + nandswapRecord.pid + " / name " + nandswapRecord.processName;
            if (checkDHAHeavyProcess(nandswapRecord)) {
                if (PerProcessNandswap.isDebugEnabled()) {
                    Slog.d(this.TAG, str + " deny reason: DHA heavy process");
                }
                return false;
            }
            if (!isExceptionApp(nandswapRecord)) {
                return true;
            }
            if (PerProcessNandswap.isDebugEnabled()) {
                Slog.d(this.TAG, str + " deny reason: exception app");
            }
            return false;
        }

        public final void startNandswapProcess(NandswapRecord nandswapRecord) {
            synchronized (PerProcessNandswap.this.mPendingNandswapActivityApp) {
                PerProcessNandswap.this.mPendingNandswapActivityApp.add(nandswapRecord);
            }
            Handler handler = PerProcessNandswap.this.mNandswapHandler;
            handler.sendMessage(handler.obtainMessage(2));
        }

        public final void changePPRState(NandswapRecord nandswapRecord, int i) {
            if (PerProcessNandswap.isDebugEnabled()) {
                Slog.d(this.TAG, "changePPRState: " + nandswapRecord.processName + " pid:" + nandswapRecord.pid + " ppnState:" + i);
            }
            synchronized (nandswapRecord.ppnStateLock) {
                nandswapRecord.ppnState = i;
            }
        }

        public final void denyPPRRequest(NandswapRecord nandswapRecord) {
            changePPRState(nandswapRecord, 0);
        }

        public final NandswapRecord acceptPPRRequest(NandswapRecord nandswapRecord) {
            NandswapRecord nandswapRecord2 = (NandswapRecord) PerProcessNandswap.this.mNandswapMap.get(Integer.valueOf(nandswapRecord.pid));
            if (nandswapRecord2 == null) {
                PerProcessNandswap.this.mNandswapMap.put(Integer.valueOf(nandswapRecord.pid), nandswapRecord);
            } else {
                nandswapRecord = nandswapRecord2;
            }
            synchronized (nandswapRecord.ppnStateLock) {
                nandswapRecord.ppnState = 1;
            }
            return nandswapRecord;
        }
    }

    public final class NandswapRecord {
        public int adj;
        public long lastNandswapTime;
        public int pid;
        public int ppnState;
        public final Object ppnStateLock;
        public int procState;
        public String processName;

        public NandswapRecord(PerProcessNandswap perProcessNandswap, String str, int i, int i2) {
            this(str, i, i2, -1);
        }

        public NandswapRecord(String str, int i, int i2, int i3) {
            this.lastNandswapTime = 0L;
            this.ppnState = 0;
            this.ppnStateLock = new Object();
            this.processName = str;
            this.pid = i;
            this.adj = i2;
            this.procState = i3;
        }
    }

    public final void tryToPerProcessNandswapByPsi(int i) {
        if (getSlotCount() <= 0 || !isPsiEnabled()) {
            return;
        }
        this.mMsgHandler.sendMessage(this.mMsgHandler.obtainMessage(5, i, 0));
    }

    public final class MemoryPressureDetector {
        public boolean mAvailable;
        public final DetectorThread mDetectorThread;
        public int mTrialThrottlingMS;
        public final PerProcessNandswap self;
        public long mRecentNandswapMS = 0;
        public final Object mPressureStateLock = new Object();
        public int mPressureState = 0;

        public MemoryPressureDetector(PerProcessNandswap perProcessNandswap, int i, int i2, int i3) {
            this.self = perProcessNandswap;
            this.mTrialThrottlingMS = i;
            if (perProcessNandswap.initMemoryPressureDetectorNative(i2, i3) == 0) {
                DetectorThread detectorThread = new DetectorThread();
                this.mDetectorThread = detectorThread;
                detectorThread.start();
                this.mAvailable = true;
                return;
            }
            this.mDetectorThread = null;
            this.mAvailable = false;
        }

        public final void setTrialThrottlingMS(int i) {
            this.mTrialThrottlingMS = i;
        }

        public final class DetectorThread extends Thread {
            public DetectorThread() {
            }

            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    Slog.i("MemoryPressureDetector", "nandswap: MemoryPressureDetector thread starts");
                    while (true) {
                        int waitForMemoryPressure = PerProcessNandswap.this.waitForMemoryPressure();
                        if (waitForMemoryPressure == -1) {
                            break;
                        }
                        synchronized (MemoryPressureDetector.this.mPressureStateLock) {
                            MemoryPressureDetector.this.mPressureState = waitForMemoryPressure;
                        }
                        if (waitForMemoryPressure < 1 || waitForMemoryPressure >= 2) {
                            if (PerProcessNandswap.isDebugEnabled()) {
                                Slog.d("MemoryPressureDetector", "nandswap: ignored ppr-trial by psi: level=" + waitForMemoryPressure);
                            }
                        } else if (PerProcessNandswap.this.isAppLaunch()) {
                            if (PerProcessNandswap.isDebugEnabled()) {
                                Slog.d("MemoryPressureDetector", "nandswap: ignored in app launch");
                            }
                        } else {
                            long uptimeMillis = SystemClock.uptimeMillis();
                            if (uptimeMillis - MemoryPressureDetector.this.mRecentNandswapMS > MemoryPressureDetector.this.mTrialThrottlingMS) {
                                MemoryPressureDetector.this.self.tryToPerProcessNandswapByPsi(waitForMemoryPressure);
                                MemoryPressureDetector.this.mRecentNandswapMS = uptimeMillis;
                            }
                        }
                    }
                    MemoryPressureDetector.this.mAvailable = false;
                } catch (Exception e) {
                    Slog.e("MemoryPressureDetector", "nandswap: MemoryPressureDetector thread crashed!");
                    e.printStackTrace();
                }
                Slog.e("MemoryPressureDetector", "nandswap: MemoryPressureDetector thread terminated!");
            }
        }
    }

    public abstract class ZramInfo {
        public static final String TAG = PerProcessNandswap.TAG;
        public static int mWritebackEnabled = -1;
        public static int mWritebackLimitEnabled = -1;

        public static boolean isWritebackEnabled() {
            if (mWritebackEnabled == -1) {
                if (new File("/sys/block/zram0/writeback_limit").exists()) {
                    if (new File("/data/per_boot/zram_swap").exists()) {
                        mWritebackEnabled = 1;
                    } else {
                        Slog.w(TAG, "/data/per_boot/zram_swap not exist");
                        mWritebackEnabled = 0;
                    }
                } else {
                    Slog.w(TAG, "/sys/block/zram0/writeback_limit not exist");
                    mWritebackEnabled = 0;
                }
                Slog.i(TAG, "isWritebackEnabled: " + mWritebackEnabled);
            }
            return mWritebackEnabled == 1;
        }

        public static boolean isWritebackLimitEnabled() {
            if (mWritebackLimitEnabled == -1) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader("/sys/block/zram0/writeback_limit_enable"));
                    try {
                        mWritebackLimitEnabled = Integer.parseInt(bufferedReader.readLine());
                        bufferedReader.close();
                    } finally {
                    }
                } catch (Exception unused) {
                    mWritebackLimitEnabled = 0;
                    Slog.w(TAG, "failed to read /sys/block/zram0/writeback_limit_enable");
                }
                Slog.i(TAG, "isWritebackLimitEnabled: " + mWritebackLimitEnabled);
            }
            return mWritebackLimitEnabled == 1;
        }

        public static long readZramWritebackLimit() {
            long j = 0;
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("/sys/block/zram0/writeback_limit"));
                try {
                    j = Long.parseLong(bufferedReader.readLine());
                    bufferedReader.close();
                } finally {
                }
            } catch (Exception unused) {
                Slog.w(TAG, "failed to read /sys/block/zram0/writeback_limit");
            }
            return j;
        }

        public static boolean isWritebackQuotaAvailable() {
            return isWritebackEnabled() && (!isWritebackLimitEnabled() || readZramWritebackLimit() > 0);
        }

        public static long[] readZramBdstat() {
            BufferedReader bufferedReader;
            long[] array;
            long[] jArr = null;
            try {
                bufferedReader = new BufferedReader(new FileReader("/sys/block/zram0/bd_stat"));
                try {
                    array = Arrays.stream(bufferedReader.readLine().trim().split("\\s+")).mapToLong(new ToLongFunction() { // from class: com.android.server.chimera.PerProcessNandswap$ZramInfo$$ExternalSyntheticLambda0
                        @Override // java.util.function.ToLongFunction
                        public final long applyAsLong(Object obj) {
                            return Long.parseLong((String) obj);
                        }
                    }).toArray();
                } finally {
                }
            } catch (Exception unused) {
                if (PerProcessNandswap.isDebugEnabled()) {
                    Slog.d(TAG, "failed to read /sys/block/zram0/bd_stat");
                }
            }
            if (array.length <= 10) {
                if (array.length <= 7) {
                    array = null;
                }
                bufferedReader.close();
                jArr = array;
                if (PerProcessNandswap.isDebugEnabled()) {
                    Slog.d(TAG, "bdstats : " + Arrays.toString(jArr));
                }
                return jArr;
            }
            long[] jArr2 = new long[8];
            for (int i = 0; i < 8; i++) {
                if (i < 5) {
                    jArr2[i] = array[i];
                } else {
                    jArr2[i] = array[i + 4];
                }
            }
            bufferedReader.close();
            return jArr2;
        }
    }

    public abstract class NandswapLogger {
        public static ArrayList mHistory = new ArrayList();

        public static void saveSwapOutLog(String str, int i, int i2, int i3, long j, long j2, long j3) {
            add("[OUT] " + str + " " + i + " " + i2 + " " + i3 + " " + j + " " + j2 + " " + j3);
        }

        public static void add(String str) {
            synchronized (mHistory) {
                String format = new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date());
                mHistory.add(format + " " + str);
                if (mHistory.size() > 128) {
                    mHistory.remove(0);
                }
            }
        }

        public static void print(PrintWriter printWriter) {
            synchronized (mHistory) {
                Iterator it = mHistory.iterator();
                while (it.hasNext()) {
                    printWriter.println((String) it.next());
                }
            }
        }
    }
}
