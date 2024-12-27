package com.android.server.chimera.ppn;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Slog;

import com.android.internal.util.MemInfoReader;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.chimera.SystemRepository;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class QuickSwap {
    public static final List QUICKSWAP_BLOCKLIST =
            Arrays.asList(
                    "com.google.android.aicore",
                    "com.samsung.android.wallpaper.magician",
                    "com.samsung.android.offline.languagemodel",
                    "com.android.bluetooth");
    public final PerProcessNandswap mPPN;
    public final QuickSwapHandler mQuickSwapHandler;
    public final ServiceThread mQuickSwapThread;
    public final SystemRepository mSystemRepository;
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
    public ArrayList mGenieDump = null;
    public boolean isDoingQuickSwap = false;
    public final Object isDoingQuickSwapLock = new Object();
    public final boolean mIsNandswapOn =
            SystemProperties.getBoolean("ro.sys.kernelmemory.nandswap.quickswap.nandswap", true);
    public final ThreadPoolExecutor mThreadPoolExecutor =
            (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class QuickSwapHandler extends Handler {
        public QuickSwapHandler() {
            super(QuickSwap.this.mQuickSwapThread.getLooper());
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i != 1) {
                return;
            }
            Object obj = message.obj;
            QuickSwap quickSwap = QuickSwap.this;
            if (obj == null) {
                quickSwap.doQuickSwap(i, 2097152L);
            } else {
                quickSwap.doQuickSwap(i, ((Long) obj).longValue());
            }
        }
    }

    public QuickSwap(PerProcessNandswap perProcessNandswap, SystemRepository systemRepository) {
        this.mPPN = perProcessNandswap;
        this.mSystemRepository = systemRepository;
        Slog.i("QuickSwap", "thread pool created, size is 4");
        ServiceThread serviceThread = new ServiceThread(10, "QuickSwapThread", true);
        this.mQuickSwapThread = serviceThread;
        if (!serviceThread.isAlive()) {
            serviceThread.start();
        }
        this.mQuickSwapHandler = new QuickSwapHandler();
    }

    public static native boolean __setRbinRefillModePath();

    public static native boolean __writeRbinRefillMode(int i);

    public static long getTotalAnonDRAMUsedKb(MemInfoReader memInfoReader) {
        long[] rawInfo = memInfoReader.getRawInfo();
        long j = rawInfo[24];
        long j2 = rawInfo[25];
        long zramTotalSizeKb = memInfoReader.getZramTotalSizeKb();
        long j3 = j + j2 + zramTotalSizeKb;
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("du: ", j3, " = aa ");
        m.append(j);
        BootReceiver$$ExternalSyntheticOutline0.m(m, " + ia ", j2, " + zr ");
        BatteryService$$ExternalSyntheticOutline0.m(m, zramTotalSizeKb, "QuickSwap");
        return j3;
    }

    public static long getTotalFileUsedKb(MemInfoReader memInfoReader) {
        long[] rawInfo = memInfoReader.getRawInfo();
        long j = rawInfo[26];
        long j2 = rawInfo[27];
        long j3 = j + j2;
        BatteryService$$ExternalSyntheticOutline0.m(
                BatteryService$$ExternalSyntheticOutline0.m(" af ", j, " + if "), j2, "QuickSwap");
        return j3;
    }

    public final void doQuickSwap(int i, long j) {
        try {
            try {
                doQuickSwap(j, new int[] {0, -900}, i);
                synchronized (this.isDoingQuickSwapLock) {
                    this.isDoingQuickSwap = false;
                }
            } catch (Exception e) {
                Slog.e("QuickSwap", "do QuickSwap execute with exception!" + e.getMessage());
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

    /* JADX WARN: Removed duplicated region for block: B:110:0x0333  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0372  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0379  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x036a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:197:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0264 A[EDGE_INSN: B:75:0x0264->B:76:0x0264 BREAK  A[LOOP:0: B:29:0x015d->B:62:0x015d], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0279  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void doQuickSwap(long r42, int[] r44, int r45) {
        /*
            Method dump skipped, instructions count: 1333
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.chimera.ppn.QuickSwap.doQuickSwap(long,"
                    + " int[], int):void");
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.println("    configs");
        printWriter.println("      threadpoolsize: 4");
        printWriter.println("      NS: " + this.mIsNandswapOn);
        printWriter.println("    stats");
        StringBuilder m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                new StringBuilder("      triggerd_cnt: "),
                                this.mTriggeredCnt,
                                printWriter,
                                "      PO_cnt: "),
                        this.mPageoutCnt,
                        printWriter,
                        "      WB_cnt: ");
        m.append(this.mWritebackCnt);
        printWriter.println(m.toString());
        printWriter.println("    latest");
        StringBuilder m2 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                BinaryTransparencyService$$ExternalSyntheticOutline0
                                                        .m(
                                                                printWriter,
                                                                this.mLatestResult,
                                                                "      PO_cnt: ",
                                                                BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                        .m(
                                                                                new StringBuilder(
                                                                                        "     "
                                                                                            + " type:"
                                                                                            + " "),
                                                                                this
                                                                                        .mLatestQuickSwapType,
                                                                                printWriter,
                                                                                "      Result: ")),
                                                this.mLatestPageoutCnt,
                                                printWriter,
                                                "      WB_cnt: "),
                                        this.mLatestWritebackCnt,
                                        printWriter,
                                        "      anonZramDiff: "),
                                this.mLatestAnonZramDiff,
                                printWriter,
                                "      latency: "),
                        this.mLatestLatency,
                        printWriter,
                        "      time: ");
        m2.append(SystemClock.uptimeMillis() - this.mLatestTimestampMS);
        printWriter.println(m2.toString());
    }
}
