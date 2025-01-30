package com.android.server.chimera;

import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.StatFs;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.EventLog;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.os.ProcessCpuTracker;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.MemInfoReader;
import com.android.server.ServiceThread;
import com.android.server.chimera.PerProcessNandswap;
import com.android.server.chimera.SystemRepository;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public final class QuickSwap {
    public static final List GENAI_PROCESSES_LIST = Arrays.asList("com.google.android.aicore", "com.samsung.android.wallpaper.magician", "com.samsung.android.offline.languagemodel");
    public static final String TAG = "QuickSwap";
    public GenAIReclaimMode mGenAIReclaimMode;
    public PerProcessNandswap mPPN;
    public QuickSwapHandler mQuickSwapHandler;
    public ServiceThread mQuickSwapThread;
    public SystemRepository mSystemRepository;
    public final int DEFAULT_THREAD_POOL_SIZE = 4;
    public int mThreadPoolSize = 4;
    public int mTriggeredCnt = 0;
    public int mPageoutCnt = 0;
    public int mWritebackCnt = 0;
    public int mLatestQuickSwapType = 0;
    public String mLatestResult = "N/A";
    public long mLatestTimestampMS = 0;
    public int mLatestPageoutCnt = 0;
    public int mLatestWritebackCnt = 0;
    public long mLatestAnonZramDiff = 0;
    public long mLatestLatency = 0;
    public boolean isDoingQuickSwap = false;
    public Object isDoingQuickSwapLock = new Object();
    public boolean mIsNandswapOn = SystemProperties.getBoolean("ro.sys.kernelmemory.nandswap.quickswap.nandswap", true);
    public final ThreadPoolExecutor mThreadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(this.mThreadPoolSize);

    public static native boolean __setRbinRefillModePath();

    public static native boolean __writeRbinRefillMode(int i);

    public QuickSwap(PerProcessNandswap perProcessNandswap, SystemRepository systemRepository) {
        this.mPPN = perProcessNandswap;
        this.mSystemRepository = systemRepository;
        Slog.i(TAG, "thread pool created, size is " + this.mThreadPoolSize);
        ServiceThread serviceThread = new ServiceThread("QuickSwapThread", 10, true);
        this.mQuickSwapThread = serviceThread;
        if (!serviceThread.isAlive()) {
            this.mQuickSwapThread.start();
        }
        this.mQuickSwapHandler = new QuickSwapHandler();
        this.mGenAIReclaimMode = new GenAIReclaimMode(this, systemRepository);
    }

    public boolean tryQuickSwap(int i) {
        if (this.mQuickSwapHandler == null) {
            return false;
        }
        synchronized (this.isDoingQuickSwapLock) {
            if (this.isDoingQuickSwap) {
                Slog.i(TAG, "QuickSwap is skipped because QuickSwap is already running.");
                return false;
            }
            this.isDoingQuickSwap = true;
            this.mQuickSwapHandler.sendEmptyMessage(i);
            return true;
        }
    }

    public void doQuickSwap(int i) {
        try {
            try {
                doQuickSwap(i, -1000, FrameworkStatsLog.f446x58c6c32, new int[]{0, -900});
                synchronized (this.isDoingQuickSwapLock) {
                    this.isDoingQuickSwap = false;
                }
            } catch (Exception e) {
                Slog.e(TAG, "do QuickSwap execute with exception!" + e.getMessage());
                synchronized (this.isDoingQuickSwapLock) {
                    this.isDoingQuickSwap = false;
                }
            }
        } catch (Throwable th) {
            synchronized (this.isDoingQuickSwapLock) {
                this.isDoingQuickSwap = false;
                throw th;
            }
        }
    }

    public void doQuickSwap(int i, int i2, int i3, int[] iArr) {
        boolean z;
        String str;
        long j;
        String str2;
        String sb;
        int i4;
        int i5;
        String str3;
        String str4;
        int i6;
        String str5;
        Iterator it;
        long j2;
        String str6;
        int i7;
        String str7;
        String str8;
        String str9;
        StringBuilder sb2;
        String str10;
        StringBuilder sb3;
        String str11;
        String str12;
        int i8;
        String str13;
        String str14;
        long j3;
        String str15;
        boolean z2;
        long uptimeMillis = SystemClock.uptimeMillis();
        if (uptimeMillis - this.mLatestTimestampMS <= 60000) {
            Slog.i(TAG, "QuickSwap throttled!");
            return;
        }
        MemInfoReader memInfoReader = new MemInfoReader();
        memInfoReader.readMemInfo();
        boolean z3 = true;
        try {
            StatFs statFs = new StatFs("/data");
            double freeBytes = statFs.getFreeBytes() / statFs.getTotalBytes();
            if (freeBytes < 0.1d) {
                Slog.i(TAG, "storageFreeRatio: " + String.format("%.2f", Double.valueOf(freeBytes)) + " threshold: 0.1");
                z2 = false;
            } else {
                z2 = true;
            }
            z = z2;
        } catch (IllegalArgumentException unused) {
            Slog.d(TAG, "Read StatFs Failed!");
            z = true;
        }
        long targetReclaimedKb = getTargetReclaimedKb(i, memInfoReader, 1572864L);
        if (targetReclaimedKb <= 0) {
            return;
        }
        long totalAnonDRAMUsedKb = getTotalAnonDRAMUsedKb(memInfoReader);
        String str16 = TAG;
        Slog.i(str16, "QuickSwap start");
        this.mTriggeredCnt++;
        Set<Pair> reclaimSet = getReclaimSet(i2, i3, iArr, targetReclaimedKb);
        if (reclaimSet.size() == 0) {
            Slog.e(str16, "reclaimSet size 0!!");
            return;
        }
        String str17 = "QuickSwap skip: no nandswap record";
        String str18 = "QuickSwap skip: app launch ";
        String str19 = "";
        if (this.mPPN.getSwapFreePercentage(memInfoReader) >= PerProcessNandswap.getMinSwapFreePercentage()) {
            ArrayList arrayList = new ArrayList();
            boolean z4 = false;
            for (Pair pair : reclaimSet) {
                final int intValue = ((Integer) pair.first).intValue();
                final long longValue = ((Long) pair.second).longValue();
                if (this.mPPN.isAppLaunch()) {
                    if (PerProcessNandswap.isDebugEnabled()) {
                        Slog.d(TAG, str18 + intValue);
                    }
                    z4 = z3;
                } else {
                    final PerProcessNandswap.NandswapRecord nandswapRecord = (PerProcessNandswap.NandswapRecord) this.mPPN.mNandswapMap.get(Integer.valueOf(intValue));
                    if (nandswapRecord == null) {
                        if (PerProcessNandswap.isDebugEnabled()) {
                            Slog.d(TAG, str17 + intValue);
                        }
                        str13 = str19;
                        str14 = str17;
                        j3 = uptimeMillis;
                        str15 = str18;
                    } else {
                        try {
                            i8 = intValue;
                            str13 = str19;
                            str14 = str17;
                            j3 = uptimeMillis;
                            str15 = str18;
                        } catch (RejectedExecutionException unused2) {
                            i8 = intValue;
                            str13 = str19;
                            str14 = str17;
                            j3 = uptimeMillis;
                            str15 = str18;
                        }
                        try {
                            arrayList.add(this.mThreadPoolExecutor.submit(new Callable() { // from class: com.android.server.chimera.QuickSwap$$ExternalSyntheticLambda0
                                @Override // java.util.concurrent.Callable
                                public final Object call() {
                                    Integer lambda$doQuickSwap$0;
                                    lambda$doQuickSwap$0 = QuickSwap.this.lambda$doQuickSwap$0(intValue, nandswapRecord, longValue);
                                    return lambda$doQuickSwap$0;
                                }
                            }));
                        } catch (RejectedExecutionException unused3) {
                            Slog.w(TAG, "QuickSwap Skip: rejected submit " + i8);
                            str18 = str15;
                            str19 = str13;
                            str17 = str14;
                            uptimeMillis = j3;
                            z3 = true;
                        }
                    }
                    str18 = str15;
                    str19 = str13;
                    str17 = str14;
                    uptimeMillis = j3;
                    z3 = true;
                }
            }
            String str20 = str19;
            str = str17;
            j = uptimeMillis;
            str2 = str18;
            Iterator it2 = arrayList.iterator();
            int i9 = 0;
            while (it2.hasNext()) {
                try {
                    i9 += ((Integer) ((Future) it2.next()).get(3L, TimeUnit.SECONDS)).intValue();
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    Slog.w(TAG, "QuickSwap async task execute with exception! " + e.getMessage());
                }
            }
            if (reclaimSet.size() == i9) {
                str12 = str20 + "PO=comp";
            } else if (z4) {
                str12 = str20 + "PO=laun";
            } else {
                str12 = str20 + "PO=skip";
            }
            sb = str12;
            i4 = i9;
        } else {
            str = "QuickSwap skip: no nandswap record";
            j = uptimeMillis;
            str2 = "QuickSwap skip: app launch ";
            Slog.w(str16, "QuickSwap Skip: swap is full (free is " + this.mPPN.getSwapFreePercentage(memInfoReader) + "%)");
            StringBuilder sb4 = new StringBuilder();
            sb4.append("");
            sb4.append("PO=full");
            sb = sb4.toString();
            i4 = 0;
        }
        String str21 = ", ";
        if (z && PerProcessNandswap.ZramInfo.isWritebackQuotaAvailable() && this.mIsNandswapOn) {
            Iterator it3 = reclaimSet.iterator();
            i6 = 0;
            boolean z5 = false;
            while (it3.hasNext()) {
                Pair pair2 = (Pair) it3.next();
                int intValue2 = ((Integer) pair2.first).intValue();
                long longValue2 = ((Long) pair2.second).longValue();
                if (this.mPPN.isAppLaunch()) {
                    if (PerProcessNandswap.isDebugEnabled()) {
                        Slog.d(TAG, str2 + intValue2);
                    }
                    z5 = true;
                } else {
                    PerProcessNandswap.NandswapRecord nandswapRecord2 = (PerProcessNandswap.NandswapRecord) this.mPPN.mNandswapMap.get(Integer.valueOf(intValue2));
                    if (nandswapRecord2 == null) {
                        if (PerProcessNandswap.isDebugEnabled()) {
                            Slog.d(TAG, str + intValue2);
                        }
                    } else {
                        String str22 = str2;
                        try {
                            try {
                                String str23 = nandswapRecord2.processName;
                                int i10 = nandswapRecord2.procState;
                                int i11 = nandswapRecord2.adj;
                                it = it3;
                                try {
                                    sb2 = new StringBuilder();
                                    str6 = str22;
                                } catch (Exception e2) {
                                    e = e2;
                                    str6 = str22;
                                    i7 = i4;
                                    str7 = str;
                                    str8 = sb;
                                    str9 = str21;
                                    e.printStackTrace();
                                    Trace.traceEnd(64L);
                                    it3 = it;
                                    str21 = str9;
                                    sb = str8;
                                    str2 = str6;
                                    i4 = i7;
                                    str = str7;
                                }
                                try {
                                    sb2.append("QuickSwap WB : ");
                                    sb2.append(str23);
                                    i7 = i4;
                                    try {
                                        try {
                                            Trace.traceBegin(64L, sb2.toString());
                                            if (PerProcessNandswap.isDebugEnabled()) {
                                                try {
                                                    str10 = TAG;
                                                    sb3 = new StringBuilder();
                                                    str7 = str;
                                                } catch (Exception e3) {
                                                    e = e3;
                                                    str7 = str;
                                                    str8 = sb;
                                                    str9 = str21;
                                                    e.printStackTrace();
                                                    Trace.traceEnd(64L);
                                                    it3 = it;
                                                    str21 = str9;
                                                    sb = str8;
                                                    str2 = str6;
                                                    i4 = i7;
                                                    str = str7;
                                                }
                                                try {
                                                    sb3.append("QuickSwap WB start for ");
                                                    sb3.append(str23);
                                                    sb3.append("(pid ");
                                                    sb3.append(intValue2);
                                                    sb3.append(") adj:");
                                                    sb3.append(i11);
                                                    sb3.append(" pss:");
                                                    sb3.append(longValue2);
                                                    Slog.d(str10, sb3.toString());
                                                } catch (Exception e4) {
                                                    e = e4;
                                                    str8 = sb;
                                                    str9 = str21;
                                                    e.printStackTrace();
                                                    Trace.traceEnd(64L);
                                                    it3 = it;
                                                    str21 = str9;
                                                    sb = str8;
                                                    str2 = str6;
                                                    i4 = i7;
                                                    str = str7;
                                                }
                                            } else {
                                                str7 = str;
                                            }
                                            long uptimeMillis2 = SystemClock.uptimeMillis();
                                            str8 = sb;
                                            String str24 = str21;
                                            try {
                                                long compactProcessForWriteback = PerProcessNandswap.compactProcessForWriteback(intValue2, 2);
                                                long uptimeMillis3 = SystemClock.uptimeMillis() - uptimeMillis2;
                                                if (compactProcessForWriteback > 0) {
                                                    str11 = str24;
                                                    try {
                                                        this.mPPN.requestChangePPRState(nandswapRecord2, 2);
                                                    } catch (Exception e5) {
                                                        e = e5;
                                                        str9 = str11;
                                                        e.printStackTrace();
                                                        Trace.traceEnd(64L);
                                                        it3 = it;
                                                        str21 = str9;
                                                        sb = str8;
                                                        str2 = str6;
                                                        i4 = i7;
                                                        str = str7;
                                                    }
                                                } else {
                                                    str11 = str24;
                                                }
                                                if (PerProcessNandswap.isDebugEnabled()) {
                                                    String str25 = TAG;
                                                    StringBuilder sb5 = new StringBuilder();
                                                    sb5.append("QuickSwap WB end for ");
                                                    sb5.append(str23);
                                                    sb5.append("(pid ");
                                                    sb5.append(intValue2);
                                                    sb5.append(") ret: ");
                                                    sb5.append(compactProcessForWriteback);
                                                    sb5.append(", adj:");
                                                    sb5.append(i11);
                                                    sb5.append(", state:");
                                                    sb5.append(i10);
                                                    str9 = str11;
                                                    try {
                                                        sb5.append(str9);
                                                        sb5.append(uptimeMillis3);
                                                        sb5.append(" ms");
                                                        Slog.d(str25, sb5.toString());
                                                    } catch (Exception e6) {
                                                        e = e6;
                                                        e.printStackTrace();
                                                        Trace.traceEnd(64L);
                                                        it3 = it;
                                                        str21 = str9;
                                                        sb = str8;
                                                        str2 = str6;
                                                        i4 = i7;
                                                        str = str7;
                                                    }
                                                } else {
                                                    str9 = str11;
                                                }
                                                i6++;
                                            } catch (Exception e7) {
                                                e = e7;
                                                str9 = str24;
                                            }
                                        } catch (Throwable th) {
                                            th = th;
                                            j2 = 64;
                                            Trace.traceEnd(j2);
                                            throw th;
                                        }
                                    } catch (Exception e8) {
                                        e = e8;
                                        str8 = sb;
                                        str9 = str21;
                                        str7 = str;
                                    }
                                } catch (Exception e9) {
                                    e = e9;
                                    i7 = i4;
                                    str7 = str;
                                    str8 = sb;
                                    str9 = str21;
                                    e.printStackTrace();
                                    Trace.traceEnd(64L);
                                    it3 = it;
                                    str21 = str9;
                                    sb = str8;
                                    str2 = str6;
                                    i4 = i7;
                                    str = str7;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                j2 = 64;
                            }
                        } catch (Exception e10) {
                            e = e10;
                            it = it3;
                        }
                        Trace.traceEnd(64L);
                        it3 = it;
                        str21 = str9;
                        sb = str8;
                        str2 = str6;
                        i4 = i7;
                        str = str7;
                    }
                }
            }
            i5 = i4;
            String str26 = sb;
            str3 = str21;
            if (reclaimSet.size() == i6) {
                str5 = str26 + " WB=comp";
            } else if (z5) {
                str5 = str26 + " WB=laun";
            } else {
                str5 = str26 + " WB=skip";
            }
            str4 = str5;
        } else {
            i5 = i4;
            String str27 = sb;
            str3 = ", ";
            Slog.w(TAG, "QuickSwap Skip: writeback_limit is over");
            str4 = str27 + " WB=over";
            i6 = 0;
        }
        long totalAnonDRAMUsedKb2 = totalAnonDRAMUsedKb - getTotalAnonDRAMUsedKb(null);
        long j4 = totalAnonDRAMUsedKb2 < 0 ? 0L : totalAnonDRAMUsedKb2;
        long uptimeMillis4 = SystemClock.uptimeMillis();
        String str28 = TAG;
        StringBuilder sb6 = new StringBuilder();
        sb6.append("QuickSwap done: ");
        sb6.append(i);
        sb6.append(str3);
        sb6.append(str4);
        sb6.append(", PO ");
        int i12 = i5;
        sb6.append(i12);
        sb6.append(", WB ");
        sb6.append(i6);
        sb6.append(str3);
        sb6.append(j4);
        sb6.append("KB in ");
        long j5 = uptimeMillis4 - j;
        sb6.append(j5);
        sb6.append("ms");
        Slog.i(str28, sb6.toString());
        finishQuickSwap(i, str4, j, i12, i6, j4, j5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$doQuickSwap$0(int i, PerProcessNandswap.NandswapRecord nandswapRecord, long j) {
        try {
            if (this.mPPN.isAppLaunch()) {
                if (PerProcessNandswap.isDebugEnabled()) {
                    Slog.d(TAG, "QuickSwap skip: app launch (in sub-thread) " + i);
                }
                return 0;
            }
            String str = nandswapRecord.processName;
            int i2 = nandswapRecord.procState;
            int i3 = nandswapRecord.adj;
            Trace.traceBegin(64L, "QuickSwap PO : " + str);
            if (PerProcessNandswap.isDebugEnabled()) {
                Slog.d(TAG, "QuickSwap PO start for " + str + "(pid " + i + ") adj:" + i3 + " pss:" + j);
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            long compactProcessForWriteback = PerProcessNandswap.compactProcessForWriteback(i, 1);
            long uptimeMillis2 = SystemClock.uptimeMillis() - uptimeMillis;
            if (PerProcessNandswap.isDebugEnabled()) {
                Slog.d(TAG, "QuickSwap PO end for " + str + "(pid " + i + ") ret: " + compactProcessForWriteback + ", adj:" + i3 + ", state:" + i2 + ", " + uptimeMillis2 + " ms");
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            Trace.traceEnd(64L);
        }
    }

    public final long getTargetReclaimedKb(int i, MemInfoReader memInfoReader, long j) {
        long[] rawInfo = memInfoReader.getRawInfo();
        long j2 = rawInfo[1];
        long j3 = rawInfo[29];
        long j4 = rawInfo[6];
        long j5 = (j2 - j3) - j4;
        long j6 = rawInfo[26];
        long j7 = rawInfo[27];
        long j8 = j6 + j7;
        long j9 = j5 + j8;
        long j10 = j > j9 ? j - j9 : 0L;
        Slog.i(TAG, "QuickSwap : tr: " + j10 + " = ts " + j + " - " + j5 + " (" + j2 + ", " + j3 + ", " + j4 + ")  - " + j8 + " (" + j6 + ", " + j7 + ")");
        return j10;
    }

    public final long getTotalAnonDRAMUsedKb(MemInfoReader memInfoReader) {
        if (memInfoReader == null) {
            memInfoReader = new MemInfoReader();
            memInfoReader.readMemInfo();
        }
        long[] rawInfo = memInfoReader.getRawInfo();
        long j = rawInfo[24];
        long j2 = rawInfo[25];
        long zramTotalSizeKb = memInfoReader.getZramTotalSizeKb();
        long j3 = j + j2 + zramTotalSizeKb;
        Slog.d(TAG, "du: " + j3 + " = aa " + j + " + ia " + j2 + " + zr " + zramTotalSizeKb);
        return j3;
    }

    public final Set getReclaimSet(int i, int i2, int[] iArr, long j) {
        String str;
        Iterator it;
        String str2;
        TreeSet treeSet;
        int[] iArr2 = iArr;
        Comparator comparator = new Comparator() { // from class: com.android.server.chimera.QuickSwap$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int lambda$getReclaimSet$1;
                lambda$getReclaimSet$1 = QuickSwap.lambda$getReclaimSet$1((Pair) obj, (Pair) obj2);
                return lambda$getReclaimSet$1;
            }
        };
        TreeSet<Pair> treeSet2 = new TreeSet(comparator);
        TreeSet treeSet3 = new TreeSet(comparator);
        List runningAppProcesses = this.mSystemRepository.getRunningAppProcesses();
        List nativeProcesses = this.mSystemRepository.getNativeProcesses((Set) runningAppProcesses.stream().map(new Function() { // from class: com.android.server.chimera.QuickSwap$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Integer lambda$getReclaimSet$2;
                lambda$getReclaimSet$2 = QuickSwap.lambda$getReclaimSet$2((SystemRepository.RunningAppProcessInfo) obj);
                return lambda$getReclaimSet$2;
            }
        }).collect(Collectors.toSet()));
        Iterator it2 = runningAppProcesses.iterator();
        while (true) {
            str = "reclaimCadidate: ";
            if (!it2.hasNext()) {
                break;
            }
            SystemRepository.RunningAppProcessInfo runningAppProcessInfo = (SystemRepository.RunningAppProcessInfo) it2.next();
            if (!GENAI_PROCESSES_LIST.contains(runningAppProcessInfo.processName)) {
                int i3 = this.mSystemRepository.getProcStateAndOomScoreForPid(runningAppProcessInfo.pid)[1];
                if (i3 < i) {
                    iArr2 = iArr;
                } else if (i3 <= i2) {
                    boolean z = false;
                    Iterator it3 = it2;
                    for (int i4 : iArr2) {
                        if (i3 == i4) {
                            z = true;
                        }
                    }
                    if (!z) {
                        long[] rss = Process.getRss(runningAppProcessInfo.pid);
                        if (rss == null || rss.length < 4) {
                            treeSet = treeSet3;
                            Slog.d(TAG, "Process.getRss(" + runningAppProcessInfo.pid + ") error.");
                        } else {
                            long j2 = rss[2];
                            long j3 = rss[3];
                            long j4 = (j2 + (j3 / 4)) / 4;
                            if (PerProcessNandswap.isDebugEnabled()) {
                                treeSet = treeSet3;
                                Slog.d(TAG, "reclaimCadidate: " + runningAppProcessInfo.pid + " adj: " + i3 + " anonrss: " + j2 + " swaprss " + j3 + " expected_reclaimed: " + j4);
                            } else {
                                treeSet = treeSet3;
                            }
                            Integer valueOf = Integer.valueOf(runningAppProcessInfo.pid);
                            if (j4 <= 0) {
                                j4 = 1;
                            }
                            treeSet2.add(new Pair(valueOf, Long.valueOf(j4)));
                            this.mPPN.checkProcessStatusForNandswap(runningAppProcessInfo.processName, runningAppProcessInfo.pid, i3);
                        }
                        iArr2 = iArr;
                        treeSet3 = treeSet;
                    }
                    it2 = it3;
                }
            }
        }
        TreeSet treeSet4 = treeSet3;
        Iterator it4 = nativeProcesses.iterator();
        while (it4.hasNext()) {
            ProcessCpuTracker.Stats stats = (ProcessCpuTracker.Stats) it4.next();
            if (!new File("/proc/" + stats.pid).exists()) {
                if (PerProcessNandswap.isDebugEnabled()) {
                    Slog.d(TAG, "Skipped by dead process pid: " + stats.pid);
                }
            } else {
                long[] rss2 = Process.getRss(stats.pid);
                if (rss2 == null || rss2.length < 4) {
                    it = it4;
                    str2 = str;
                    Slog.d(TAG, "Process.getRss(" + stats.pid + ") error.");
                } else {
                    long j5 = rss2[2];
                    long j6 = rss2[3];
                    long j7 = (j5 + (j6 / 4)) / 4;
                    if (PerProcessNandswap.isDebugEnabled()) {
                        String str3 = TAG;
                        it = it4;
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        str2 = str;
                        sb.append(stats.pid);
                        sb.append(" adj: -1000 anonrss: ");
                        sb.append(j5);
                        sb.append(" swaprss ");
                        sb.append(j6);
                        sb.append(" expected_reclaimed: ");
                        sb.append(j7);
                        Slog.d(str3, sb.toString());
                    } else {
                        it = it4;
                        str2 = str;
                    }
                    treeSet2.add(new Pair(Integer.valueOf(stats.pid), Long.valueOf(j7)));
                    this.mPPN.checkProcessStatusForNandswap(stats.name, stats.pid, -1000);
                }
                it4 = it;
                str = str2;
            }
        }
        long j8 = j;
        for (Pair pair : treeSet2) {
            TreeSet treeSet5 = treeSet4;
            treeSet5.add(pair);
            j8 -= ((Long) pair.second).longValue();
            if (PerProcessNandswap.isDebugEnabled()) {
                Slog.d(TAG, "reclaimSet remaining: " + j8 + " expected_reclaimed " + pair.second);
            }
            if (j8 <= 0) {
                return treeSet5;
            }
            treeSet4 = treeSet5;
        }
        return treeSet4;
    }

    public static /* synthetic */ int lambda$getReclaimSet$1(Pair pair, Pair pair2) {
        if (((Long) pair2.second).longValue() - ((Long) pair.second).longValue() == 0) {
            return ((Integer) pair2.first).intValue() > ((Integer) pair.first).intValue() ? -1 : 1;
        }
        return (int) (((Long) pair2.second).longValue() - ((Long) pair.second).longValue());
    }

    public static /* synthetic */ Integer lambda$getReclaimSet$2(SystemRepository.RunningAppProcessInfo runningAppProcessInfo) {
        return Integer.valueOf(runningAppProcessInfo.pid);
    }

    public final void finishQuickSwap(int i, String str, long j, int i2, int i3, long j2, long j3) {
        long uptimeMillis = SystemClock.uptimeMillis();
        EventLog.writeEvent(1300202, Integer.valueOf(i), str, (uptimeMillis - j) + "ms", Integer.valueOf(i2), Integer.valueOf(i3), Long.valueOf(j2));
        this.mPageoutCnt = this.mPageoutCnt + i2;
        this.mWritebackCnt = this.mWritebackCnt + i3;
        this.mLatestQuickSwapType = i;
        this.mLatestResult = str;
        this.mLatestTimestampMS = SystemClock.uptimeMillis();
        this.mLatestPageoutCnt = i2;
        this.mLatestWritebackCnt = i3;
        this.mLatestAnonZramDiff = j2;
        this.mLatestLatency = j3;
    }

    public final class QuickSwapHandler extends Handler {
        public QuickSwapHandler() {
            super(QuickSwap.this.mQuickSwapThread.getLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1 || i == 2) {
                QuickSwap.this.doQuickSwap(i);
            } else {
                if (i != 3) {
                    return;
                }
                Slog.i(QuickSwap.TAG, "GenAIReclaim Mode Timeout");
                QuickSwap.this.mGenAIReclaimMode.setModeOn(false);
            }
        }
    }

    public boolean setProperty(String str, String str2) {
        if (!"nandswap".equals(str)) {
            return false;
        }
        this.mIsNandswapOn = Boolean.parseBoolean(str2);
        return true;
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("    configs");
        printWriter.println("      threadpoolsize: " + this.mThreadPoolSize);
        printWriter.println("      NS: " + this.mIsNandswapOn);
        printWriter.println("    stats");
        printWriter.println("      triggerd_cnt: " + this.mTriggeredCnt);
        printWriter.println("      PO_cnt: " + this.mPageoutCnt);
        printWriter.println("      WB_cnt: " + this.mWritebackCnt);
        printWriter.println("    latest");
        printWriter.println("      type: " + this.mLatestQuickSwapType);
        printWriter.println("      Result: " + this.mLatestResult);
        printWriter.println("      PO_cnt: " + this.mLatestPageoutCnt);
        printWriter.println("      WB_cnt: " + this.mLatestWritebackCnt);
        printWriter.println("      anonZramDiff: " + this.mLatestAnonZramDiff);
        printWriter.println("      latency: " + this.mLatestLatency);
        printWriter.println("      time: " + (SystemClock.uptimeMillis() - this.mLatestTimestampMS));
        this.mGenAIReclaimMode.dump(printWriter);
    }

    public void setGenAIReclaimModeTimeout(long j) {
        QuickSwapHandler quickSwapHandler = this.mQuickSwapHandler;
        quickSwapHandler.sendMessageDelayed(quickSwapHandler.obtainMessage(3), j);
    }
}
