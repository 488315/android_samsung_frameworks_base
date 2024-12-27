package com.android.server.am;

import android.content.Context;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;

import com.android.server.am.mars.HistoryBuffer;
import com.android.server.am.mars.filter.FilterManager;
import com.android.server.am.mars.filter.filter.ActiveMusicRecordFilter;
import com.android.server.am.mars.util.UidStateMgr;
import com.android.server.am.pds.PDSHandler;
import com.android.server.am.pds.PDSHistoryBuffer;
import com.android.server.am.pds.PDSHistoryLog;
import com.android.server.am.pds.PDSPackageInfo;
import com.android.server.am.pds.PDSPkgMap;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PDSController {
    public static final boolean DEBUG_ENG;
    public ActivityManagerService mAm;
    public Context mContext;
    public HistoryBuffer mHistoryBufferArray;
    public boolean mIsPDSEnable;
    public PDSPkgMap mPDSRestrictedPackages;
    public PDSPkgMap mPDSTargetPackages;
    public List mPDSTargetlist;
    public boolean mScreenOn;
    public Policy mpsmPolicy;
    public Policy udsPolicy;
    public static final Lock PDSLock = new Lock();
    public static final boolean DEBUG_MID =
            SystemProperties.get("ro.boot.debug_level", "").equals("0x494d");
    public static final boolean DEBUG_HIGH =
            SystemProperties.get("ro.boot.debug_level", "").equals("0x4948");

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lock {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class PDSControllerHolder {
        public static final PDSController INSTANCE;

        static {
            PDSController pDSController = new PDSController();
            pDSController.mIsPDSEnable = false;
            pDSController.mScreenOn = false;
            pDSController.mPDSTargetlist = new ArrayList();
            pDSController.mPDSTargetPackages = new PDSPkgMap();
            pDSController.mPDSRestrictedPackages = new PDSPkgMap();
            pDSController.udsPolicy = null;
            pDSController.mpsmPolicy = null;
            INSTANCE = pDSController;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PkgStatusInfo {
        public String name;
        public int uid;
        public int userId;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Policy {
        public final boolean enabled;
        public final String name;
        public final int num;

        public Policy(int i, String str, boolean z) {
            this.name = str;
            this.num = i;
            this.enabled = z;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.name);
            sb.append("(");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.num, sb, ")");
        }
    }

    static {
        DEBUG_ENG =
                (SystemProperties.get("ro.build.type", "user").equals("user")
                                && SystemProperties.get("ro.boot.debug_level", "0x4f4c")
                                        .equals("0x4f4c"))
                        ? false
                        : true;
    }

    public static String formatDateTimeWithoutYear(long j) {
        return j == 0
                ? String.format("%18s", "null")
                : new SimpleDateFormat("MM/dd HH:mm:ss.SSS").format(new Date(j));
    }

    public final void dumpPDS(PrintWriter printWriter) {
        Slog.v("PDSController", "dumpPDS");
        printWriter.println("ACTIVITY MANAGER PDS (dumpsys activity PDS)");
        synchronized (PDSLock) {
            try {
                printWriter.println(
                        "UDS = " + this.udsPolicy.enabled + ", MP = " + this.mpsmPolicy.enabled);
                StringBuilder sb = new StringBuilder("mPDSTargetPackages --- size ");
                sb.append(this.mPDSTargetPackages.totalSize());
                printWriter.println(sb.toString());
                if (this.mPDSTargetPackages.mMap.size() == 0) {
                    printWriter.println("No Policy Trigger !!!");
                }
                for (int i = 0; i < this.mPDSTargetPackages.mMap.size(); i++) {
                    SparseArray sparseArray = (SparseArray) this.mPDSTargetPackages.mMap.valueAt(i);
                    for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                        PDSPackageInfo pDSPackageInfo = (PDSPackageInfo) sparseArray.valueAt(i2);
                        if (pDSPackageInfo != null) {
                            printWriter.print("-RST ");
                            long j = pDSPackageInfo.disableResetTime;
                            printWriter.print(
                                    j == 0
                                            ? String.format("%23s", "null")
                                            : new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS")
                                                    .format(new Date(j)));
                            printWriter.print("-Uid ");
                            printWriter.print(
                                    String.format("%8d", Integer.valueOf(pDSPackageInfo.uid)));
                            if (DEBUG_ENG) {
                                printWriter.print("-curLv ");
                            }
                            printWriter.print(pDSPackageInfo.curLevel);
                            printWriter.print("(");
                            printWriter.print(
                                    (pDSPackageInfo.fasType & 129) != 0
                                            ? "A"
                                            : PackageManagerShellCommandDataLoader.STDIN_PATH);
                            printWriter.print(")");
                            printWriter.print("-Pkg ");
                            printWriter.print(pDSPackageInfo.name);
                            if (UidStateMgr.UidStateMgrHolder.INSTANCE.isUidRunning(
                                    pDSPackageInfo.uid)) {
                                printWriter.print("--(R)");
                            }
                            printWriter.println(" ");
                        }
                    }
                }
                printWriter.println(
                        "mPDSRestrictedPackages --- size "
                                + this.mPDSRestrictedPackages.totalSize());
                for (int i3 = 0; i3 < this.mPDSRestrictedPackages.mMap.size(); i3++) {
                    SparseArray sparseArray2 =
                            (SparseArray) this.mPDSRestrictedPackages.mMap.valueAt(i3);
                    for (int i4 = 0; i4 < sparseArray2.size(); i4++) {
                        PDSPackageInfo pDSPackageInfo2 = (PDSPackageInfo) sparseArray2.valueAt(i4);
                        printWriter.print("-Uid ");
                        printWriter.print(
                                String.format("%8d", Integer.valueOf(pDSPackageInfo2.uid)));
                        printWriter.print("-Pkg ");
                        printWriter.print(pDSPackageInfo2.name);
                        printWriter.print("-POL ");
                        Policy policy = pDSPackageInfo2.appliedPolicy;
                        if (policy != null) {
                            String policy2 = policy.toString();
                            printWriter.print(
                                    policy2.substring(policy2.length() - 3, policy2.length()));
                        } else {
                            printWriter.print("null");
                        }
                        printWriter.println(" ");
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        printWriter.println(" ");
        PDSHandler.MainHandler mainHandler = PDSHandler.PDSHandlerHolder.INSTANCE.mMainHandler;
        if (mainHandler != null) {
            mainHandler.dump(new PrintWriterPrinter(printWriter), "PDSHandler");
        }
    }

    public final void dumpPDSHistory(PrintWriter printWriter) {
        ArrayList log;
        printWriter.println("");
        printWriter.println("ACTIVITY MANAGER PDS HISTORY (dumpsys activity PDS history)");
        int i = 0;
        if (DEBUG_MID || DEBUG_HIGH) {
            synchronized (PDSHistoryBuffer.PDSHistoryBufferHolder.INSTANCE) {
                log = PDSHistoryLog.PDSHistoryLogHolder.INSTANCE.getLog();
            }
            while (i < log.size()) {
                printWriter.print((String) log.get(i));
                i++;
            }
            printWriter.println("");
            return;
        }
        HistoryBuffer historyBuffer = this.mHistoryBufferArray;
        if (historyBuffer != null) {
            String[] buffer = historyBuffer.getBuffer();
            while (i < this.mHistoryBufferArray.size) {
                printWriter.print(buffer[i]);
                i++;
            }
            printWriter.println("");
        }
    }

    public final void forceRunPolicyForSpecificPolicy(int i) {
        PDSPackageInfo pDSPackageInfo;
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        int i2 = 5;
        int i3 = 10;
        Policy policy = i == 5 ? this.udsPolicy : i == 10 ? this.mpsmPolicy : null;
        if (policy == null || !policy.enabled) {
            Slog.v("PDSController", "policy is not exist or not enabled!");
            return;
        }
        int i4 = policy.num;
        int i5 = i4 != 5 ? i4 != 10 ? 0 : 9 : 6;
        System.currentTimeMillis();
        ActiveMusicRecordFilter.ActiveMusicRecordFilterHolder.INSTANCE.getUidListUsingAudio();
        synchronized (PDSLock) {
            for (int i6 = 0; i6 < this.mPDSTargetPackages.mMap.size(); i6++) {
                try {
                    SparseArray sparseArray =
                            (SparseArray) this.mPDSTargetPackages.mMap.valueAt(i6);
                    for (int i7 = 0; i7 < sparseArray.size(); i7++) {
                        PDSPackageInfo pDSPackageInfo2 = (PDSPackageInfo) sparseArray.valueAt(i7);
                        String str = pDSPackageInfo2.name;
                        int i8 = policy.num;
                        if (!str.equals("com.samsung.android.app.routineplus") || i8 != i3) {
                            FilterManager filterManager =
                                    FilterManager.FilterManagerHolder.INSTANCE;
                            int i9 = policy.num;
                            int i10 = i9 != i2 ? i9 != i3 ? -1 : 16 : i2;
                            String str2 = pDSPackageInfo2.name;
                            int i11 = pDSPackageInfo2.userId;
                            int i12 = pDSPackageInfo2.uid;
                            filterManager.getClass();
                            if (FilterManager.filterForSpecificPolicy(i10, i11, i12, str2) > 0) {
                                Slog.v(
                                        "PDSController",
                                        "FilterManager package "
                                                + pDSPackageInfo2.name
                                                + " inFilterManager, don't execute this policy "
                                                + policy);
                                i2 = 5;
                            } else {
                                String str3 = pDSPackageInfo2.name;
                                int i13 = pDSPackageInfo2.uid;
                                int i14 = pDSPackageInfo2.userId;
                                PkgStatusInfo pkgStatusInfo = new PkgStatusInfo();
                                pkgStatusInfo.name = str3;
                                pkgStatusInfo.uid = i13;
                                pkgStatusInfo.userId = i14;
                                i2 = 5;
                                if (i != 5) {
                                    i3 = 10;
                                    if (i == 10 && pDSPackageInfo2.mpsm == 1) {
                                        arrayList.add(pkgStatusInfo);
                                    }
                                } else if (pDSPackageInfo2.uds == 1) {
                                    arrayList.add(pkgStatusInfo);
                                }
                            }
                            i3 = 10;
                        } else if (DEBUG_ENG) {
                            Slog.v(
                                    "PDSController",
                                    "package "
                                            + pDSPackageInfo2.name
                                            + " inPolicyAllowList, don't execute this policy "
                                            + policy);
                        }
                    }
                } finally {
                }
            }
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        int size = arrayList.size() - 1;
        boolean z = false;
        while (size >= 0) {
            if (SystemClock.uptimeMillis() - uptimeMillis >= 50) {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                uptimeMillis = SystemClock.uptimeMillis();
            }
            ActivityManagerService activityManagerService = this.mAm;
            ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    PkgStatusInfo pkgStatusInfo2 = (PkgStatusInfo) arrayList.get(size);
                    this.mAm.forceStopPackage(pkgStatusInfo2.name, pkgStatusInfo2.userId);
                    sb.append(" " + pkgStatusInfo2.uid);
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
            size--;
            z = true;
        }
        synchronized (PDSLock) {
            for (int i15 = 0; i15 < arrayList.size(); i15++) {
                try {
                    PkgStatusInfo pkgStatusInfo3 = (PkgStatusInfo) arrayList.get(i15);
                    PDSPkgMap pDSPkgMap = this.mPDSTargetPackages;
                    String str4 = pkgStatusInfo3.name;
                    int i16 = pkgStatusInfo3.userId;
                    if (pDSPkgMap == null || pDSPkgMap.totalSize() == 0) {
                        pDSPackageInfo = null;
                    } else {
                        SparseArray sparseArray2 = (SparseArray) pDSPkgMap.mMap.get(str4);
                        pDSPackageInfo =
                                (PDSPackageInfo)
                                        (sparseArray2 == null ? null : sparseArray2.get(i16));
                    }
                    if (pDSPackageInfo != null) {
                        pDSPackageInfo.appliedPolicy = policy;
                        pDSPackageInfo.curLevel = i5;
                        PDSPkgMap pDSPkgMap2 = this.mPDSRestrictedPackages;
                        String str5 = pDSPackageInfo.name;
                        int i17 = pDSPackageInfo.userId;
                        SparseArray sparseArray3 = (SparseArray) pDSPkgMap2.mMap.get(str5);
                        if ((sparseArray3 == null ? null : sparseArray3.get(i17)) == null) {
                            PDSPkgMap pDSPkgMap3 = this.mPDSRestrictedPackages;
                            String str6 = pDSPackageInfo.name;
                            int i18 = pDSPackageInfo.userId;
                            SparseArray sparseArray4 = (SparseArray) pDSPkgMap3.mMap.get(str6);
                            if (sparseArray4 == null) {
                                sparseArray4 = new SparseArray(2);
                                pDSPkgMap3.mMap.put(str6, sparseArray4);
                            }
                            sparseArray4.put(i18, pDSPackageInfo);
                        }
                        if (DEBUG_ENG) {
                            Slog.v(
                                    "PDSController",
                                    "add mPDSRestrictedPackages "
                                            + pDSPackageInfo.name
                                            + " policy --"
                                            + policy);
                        }
                    }
                } finally {
                }
            }
        }
        if (z) {
            String str7 = policy.name + " " + sb.toString();
            if (!DEBUG_MID && !DEBUG_HIGH) {
                if (this.mHistoryBufferArray != null) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("[" + formatDateTimeWithoutYear(System.currentTimeMillis()) + "] ");
                    sb2.append("[EXE] ");
                    sb2.append("[" + str7 + "]\n");
                    this.mHistoryBufferArray.put(sb2.toString());
                    return;
                }
                return;
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append("[" + formatDateTimeWithoutYear(System.currentTimeMillis()) + "] ");
            sb3.append("[EXE] ");
            sb3.append("[" + str7 + "]\n");
            PDSHistoryBuffer pDSHistoryBuffer = PDSHistoryBuffer.PDSHistoryBufferHolder.INSTANCE;
            String sb4 = sb3.toString();
            synchronized (pDSHistoryBuffer) {
                String[] strArr = pDSHistoryBuffer.buffer;
                int i19 = pDSHistoryBuffer.pointer;
                int i20 = i19 + 1;
                pDSHistoryBuffer.pointer = i20;
                strArr[i19] = sb4;
                if (i20 >= pDSHistoryBuffer.size) {
                    PDSHistoryLog.PDSHistoryLogHolder.INSTANCE.saveLogToFile(true);
                    pDSHistoryBuffer.pointer = 0;
                }
            }
        }
    }
}
