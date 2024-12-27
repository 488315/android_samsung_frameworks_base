package com.android.server.chimera.ppn;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.EventLog;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.os.ProcessCpuTracker;
import com.android.internal.util.MemInfoReader;
import com.android.internal.util.RingBuffer;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticLambda20;
import com.android.server.am.ProcessRecord;
import com.android.server.chimera.ChimeraCommonUtil;
import com.android.server.chimera.ChimeraStrategy;
import com.android.server.chimera.SystemRepository;
import com.android.server.chimera.umr.UnifiedMemoryReclaimer;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.rune.CoreRune;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PerProcessNandswap {
    public ChimeraStrategy mChimeraStrategy;
    public QuickSwap mQuickSwap;
    public SystemRepository mSystemRepository;
    public static final boolean FAST_MADVISE_ENABLED = CoreRune.FAST_MADVISE_ENABLED;
    public static final boolean IS_DEBUG_LEVEL_LOW = "0x4f4c".equals(SystemProperties.get("ro.boot.debug_level", "0x4f4c"));
    public static Boolean __DebugEnabled = null;
    public static Integer __PolicyVersion = null;
    public static Integer __MinSwapFreePercentage = null;
    public static Boolean __PrefetchActionEnabled = null;
    public static Boolean __WritebackOnBGEnabled = null;
    public static Boolean __WritebackOnFreezeEnabled = null;
    public static final int SWAP_AFTER_BOOT_LOW_SWAP_PERCENT = SystemProperties.getInt("ro.sys.kernelmemory.nandswap.swap_after_boot_low_swap_percent", 60);
    public static int __SlotCount = 0;
    public static ArrayList __SlotCountMap = null;
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
    public Boolean __SwapAfterBootEnable = null;
    public final RingBuffer mSwapAfterBootLog = new RingBuffer(String.class, 5);
    public final ConcurrentHashMap mNandswapMap = new ConcurrentHashMap();
    public final ArrayList mPendingNandswapNonActivityApp = new ArrayList();
    public final ArrayList mPendingNandswapNonActivityAppDelayed = new ArrayList();
    public ServiceThread mNandswapThread = null;
    public ServiceThread mMsgThread = null;
    public NandswapHandler mNandswapHandler = null;
    public NandswapMsgHandler mMsgHandler = null;
    public final LinkedHashMap mLastNandswapStats = new LinkedHashMap() { // from class: com.android.server.chimera.ppn.PerProcessNandswap.1
        @Override // java.util.LinkedHashMap
        public final boolean removeEldestEntry(Map.Entry entry) {
            return size() > 100;
        }
    };
    public final PriorityQueue mPendingNandswapActivityApp = new PriorityQueue();
    public final ArrayList mPendingNandswapActivityAppDelayed = new ArrayList();
    public final Map mKeyApps = new LinkedHashMap() { // from class: com.android.server.chimera.ppn.PerProcessNandswap.2
        @Override // java.util.LinkedHashMap
        public final boolean removeEldestEntry(Map.Entry entry) {
            return size() > 30;
        }
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.chimera.ppn.PerProcessNandswap$3, reason: invalid class name */
    public final class AnonymousClass3 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            long j = ((MemoryItem) obj).writeback;
            long j2 = ((MemoryItem) obj2).writeback;
            if (j < j2) {
                return 1;
            }
            return j > j2 ? -1 : 0;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LastNandswapStats {
        public final String mName;
        public final long[] mRssAfterNandswap;

        public LastNandswapStats(String str, long[] jArr) {
            this.mName = str;
            this.mRssAfterNandswap = jArr;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MemoryItem {
        public final String label;
        public final long lastNandswapTimeDiff;
        public final int ppnState;
        public ArrayList subitems;
        public final long swap;
        public final long writeback;

        public MemoryItem(int i, long j, long j2, long j3, String str) {
            this.label = str;
            this.swap = j;
            this.writeback = j2;
            this.ppnState = i;
            this.lastNandswapTimeDiff = j3;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NandswapHandler extends Handler {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final String TAG;
        public final String[] nativePackageNameFilter;

        public NandswapHandler() {
            super(PerProcessNandswap.this.mNandswapThread.getLooper());
            this.nativePackageNameFilter = new String[]{"vendor.samsung.hardware.camera.provider", "vendor.samsung.hardware.camera.provider-service_64", "cameraserver"};
            this.TAG = NandswapHandler.class.getSimpleName();
        }

        public static void quitNandSwapAlwaysRunningProcessWithWriteEventLog(int i, String str, long j, int i2, long j2, List list) {
            long uptimeMillis = SystemClock.uptimeMillis();
            ArrayList arrayList = (ArrayList) list;
            if (arrayList.size() > 30) {
                list = arrayList.subList(0, 30);
            }
            EventLog.writeEvent(1300201, Integer.valueOf(i), str, AudioConfig$$ExternalSyntheticOutline0.m(new StringBuilder(), uptimeMillis - j, "ms"), Integer.valueOf(i2), Long.valueOf(j2), list.toString());
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0032, code lost:
        
            if (com.android.server.am.FreecessController.FreecessControllerHolder.INSTANCE.isFreezedPackage(r4) != false) goto L13;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean canDoNandswapProcess(com.android.server.chimera.ppn.PerProcessNandswap.NandswapRecord r6, int r7) {
            /*
                r5 = this;
                com.android.server.chimera.ppn.PerProcessNandswap r0 = com.android.server.chimera.ppn.PerProcessNandswap.this
                com.android.server.chimera.SystemRepository r1 = r0.mSystemRepository
                int r2 = r6.pid
                int[] r1 = r1.getProcStateAndOomScoreForPid(r2)
                r2 = 0
                r3 = r1[r2]
                r6.procState = r3
                r3 = 1
                r1 = r1[r3]
                r6.adj = r1
                int r4 = r6.ppnState
                if (r4 != r3) goto L36
                r4 = 850(0x352, float:1.191E-42)
                if (r1 >= r4) goto L34
                int r4 = r6.uid
                r0.getClass()
                r0 = 16
                if (r7 == r0) goto L26
                goto L36
            L26:
                r7 = 100
                if (r1 < r7) goto L36
                boolean r7 = com.android.server.am.FreecessController.IS_MINIMIZE_OLAF_LOCK
                com.android.server.am.FreecessController r7 = com.android.server.am.FreecessController.FreecessControllerHolder.INSTANCE
                boolean r7 = r7.isFreezedPackage(r4)
                if (r7 == 0) goto L36
            L34:
                r2 = r3
                goto L6a
            L36:
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                java.lang.String r0 = "canDoNandswapProcess fail: "
                r7.<init>(r0)
                java.lang.String r0 = r6.processName
                r7.append(r0)
                java.lang.String r0 = " pid:"
                r7.append(r0)
                int r0 = r6.pid
                r7.append(r0)
                java.lang.String r0 = " state:"
                r7.append(r0)
                int r0 = r6.ppnState
                r7.append(r0)
                java.lang.String r0 = " adj:"
                r7.append(r0)
                int r6 = r6.adj
                r7.append(r6)
                java.lang.String r6 = r7.toString()
                java.lang.String r5 = r5.TAG
                android.util.Slog.i(r5, r6)
            L6a:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.chimera.ppn.PerProcessNandswap.NandswapHandler.canDoNandswapProcess(com.android.server.chimera.ppn.PerProcessNandswap$NandswapRecord, int):boolean");
        }

        public final long doNandswapApp(NandswapRecord nandswapRecord, int i, int i2, boolean z) {
            long j;
            long j2;
            PerProcessNandswap perProcessNandswap;
            long[] jArr;
            int i3;
            int i4;
            String str;
            int i5;
            long j3;
            long j4;
            String str2;
            long j5;
            String str3;
            String str4;
            int i6;
            long j6;
            long j7;
            int i7;
            int i8;
            int i9;
            String str5 = this.TAG;
            int i10 = nandswapRecord.pid;
            String str6 = nandswapRecord.processName;
            int i11 = nandswapRecord.procState;
            int i12 = nandswapRecord.adj;
            String str7 = z ? "activity" : "non-activity";
            long j8 = -1;
            try {
                try {
                    Trace.traceBegin(64L, "nandswap" + str7 + " : " + str6 + ", " + (i2 == 1 ? "swaponly" : i2 == 2 ? "writebackonly" : "all"));
                    long[] m335$$Nest$smreadZramBdstat = ZramInfo.m335$$Nest$smreadZramBdstat();
                    long readZramWritebackLimit = ZramInfo.readZramWritebackLimit();
                    long[] rss = Process.getRss(i10);
                    long zramFreeKb = Debug.getZramFreeKb();
                    StringBuilder sb = new StringBuilder("nandswap start for ");
                    sb.append(str7);
                    sb.append(" ");
                    sb.append(str6);
                    sb.append("(pid ");
                    sb.append(i10);
                    sb.append(") action:");
                    j = 64;
                    try {
                        sb.append(i);
                        sb.append(" adj:");
                        sb.append(i12);
                        sb.append(" type:");
                        sb.append(i2);
                        Slog.d(str5, sb.toString());
                        long uptimeMillis = SystemClock.uptimeMillis();
                        long compactProcessForWriteback = PerProcessNandswap.compactProcessForWriteback(i10, i2);
                        try {
                            long uptimeMillis2 = SystemClock.uptimeMillis();
                            Slog.d(str5, "nandswap end for " + str7 + " " + str6 + "(pid " + i10 + ") ret:" + compactProcessForWriteback + " adj:" + i12);
                            long j9 = uptimeMillis2 - uptimeMillis;
                            long[] m335$$Nest$smreadZramBdstat2 = ZramInfo.m335$$Nest$smreadZramBdstat();
                            long readZramWritebackLimit2 = ZramInfo.readZramWritebackLimit();
                            long[] rss2 = Process.getRss(i10);
                            long zramFreeKb2 = Debug.getZramFreeKb();
                            long j10 = zramFreeKb2 - zramFreeKb;
                            long j11 = (readZramWritebackLimit2 - readZramWritebackLimit) * 4;
                            PerProcessNandswap perProcessNandswap2 = PerProcessNandswap.this;
                            if (compactProcessForWriteback > 0) {
                                j3 = zramFreeKb2;
                                j2 = compactProcessForWriteback;
                                j4 = j9;
                                i3 = i12;
                                i4 = i10;
                                jArr = rss2;
                                str2 = "nandswap ";
                                i5 = i11;
                                str = str5;
                                j5 = j10;
                                str3 = ", ";
                                str4 = str6;
                                try {
                                    NandswapLogger.saveSwapOutLog(str6, i10, i, i2, j4, j10, j11);
                                    if (perProcessNandswap2.isKeyAppEnable()) {
                                        perProcessNandswap = perProcessNandswap2;
                                        ((HashMap) perProcessNandswap.mKeyApps).put(str4, PerProcessNandswap.STATUS_ALREADY_PPN);
                                    } else {
                                        perProcessNandswap = perProcessNandswap2;
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
                                perProcessNandswap = perProcessNandswap2;
                                jArr = rss2;
                                i3 = i12;
                                i4 = i10;
                                str = str5;
                                i5 = i11;
                                j3 = zramFreeKb2;
                                j4 = j9;
                                j2 = compactProcessForWriteback;
                                str2 = "nandswap ";
                                j5 = j10;
                                str3 = ", ";
                                str4 = str6;
                            }
                            if (PerProcessNandswap.isDebugEnabled()) {
                                StringBuilder sb2 = new StringBuilder(str2);
                                sb2.append(str7);
                                sb2.append(" for ");
                                sb2.append(str4);
                                sb2.append("(pid ");
                                i7 = i4;
                                sb2.append(i7);
                                sb2.append(") action:");
                                i6 = i;
                                sb2.append(i6);
                                sb2.append(", adj:");
                                i9 = i3;
                                sb2.append(i9);
                                sb2.append(", state:");
                                i8 = i5;
                                sb2.append(i8);
                                sb2.append(str3);
                                sb2.append(jArr[1]);
                                sb2.append(str3);
                                sb2.append(jArr[2]);
                                sb2.append(str3);
                                sb2.append(jArr[3]);
                                sb2.append(str3);
                                long j12 = j3;
                                sb2.append(j12);
                                sb2.append(str3);
                                j7 = j4;
                                sb2.append(j7);
                                sb2.append(" ms, ");
                                j6 = uptimeMillis2;
                                sb2.append(i2);
                                sb2.append(str3);
                                j3 = j12;
                                sb2.append(jArr[1] - rss[1]);
                                sb2.append(str3);
                                sb2.append(jArr[2] - rss[2]);
                                sb2.append(str3);
                                sb2.append(jArr[3] - rss[3]);
                                sb2.append(str3);
                                sb2.append(j5);
                                sb2.append(str3);
                                sb2.append(j11);
                                Slog.d(str, sb2.toString());
                            } else {
                                i6 = i;
                                j6 = uptimeMillis2;
                                j7 = j4;
                                i7 = i4;
                                i8 = i5;
                                i9 = i3;
                            }
                            if (m335$$Nest$smreadZramBdstat != null && m335$$Nest$smreadZramBdstat2 != null) {
                                EventLog.writeEvent(1300200, Integer.valueOf(i7), str4, Integer.valueOf(i), Long.valueOf(j2), Integer.valueOf(i9), Integer.valueOf(i8), String.valueOf(j7) + "ms", Integer.valueOf(i2), Arrays.toString(m335$$Nest$smreadZramBdstat), Long.valueOf(readZramWritebackLimit), Long.valueOf(zramFreeKb), Arrays.toString(m335$$Nest$smreadZramBdstat2), Long.valueOf(readZramWritebackLimit2), Long.valueOf(j3));
                            }
                            if (i6 != 15) {
                                nandswapRecord.lastNandswapTime = j6;
                            }
                            if (!z) {
                                PerProcessNandswap.m330$$Nest$mupdateLastNandswapStats(perProcessNandswap, i7, str4, jArr);
                            }
                            Trace.traceEnd(64L);
                            return j2;
                        } catch (Exception unused3) {
                            j2 = compactProcessForWriteback;
                        } catch (Throwable unused4) {
                            j2 = compactProcessForWriteback;
                        }
                    } catch (Exception unused5) {
                    } catch (Throwable unused6) {
                    }
                } catch (Exception unused7) {
                    j = 64;
                } catch (Throwable unused8) {
                    j = 64;
                }
            } catch (Exception unused9) {
                j = 64;
            } catch (Throwable unused10) {
                j = 64;
            }
        }

        public final long doSwapAfterBootFull() {
            String str;
            String str2;
            long uptimeMillis = SystemClock.uptimeMillis();
            Set reclaimSetForNandswap = getReclaimSetForNandswap(new PerProcessNandswap$NandswapHandler$$ExternalSyntheticLambda0(1), false);
            boolean isDebugEnabled = PerProcessNandswap.isDebugEnabled();
            String m331$$Nest$smgetMemoryInfoIfDebugEnabled = PerProcessNandswap.m331$$Nest$smgetMemoryInfoIfDebugEnabled(isDebugEnabled);
            Iterator it = ((TreeSet) reclaimSetForNandswap).iterator();
            int i = 0;
            long j = 0;
            while (true) {
                boolean hasNext = it.hasNext();
                str = this.TAG;
                if (!hasNext) {
                    str2 = "Swap After boot(Full) successfully";
                    break;
                }
                Integer num = (Integer) ((Pair) it.next()).first;
                int intValue = num.intValue();
                PerProcessNandswap perProcessNandswap = PerProcessNandswap.this;
                perProcessNandswap.getClass();
                if (UnifiedMemoryReclaimer.isInAppLaunch()) {
                    if (isDebugEnabled) {
                        AnyMotionDetector$$ExternalSyntheticOutline0.m(intValue, "Swap After boot(Full) skip: app launch ", str);
                    }
                    str2 = "Swap After boot(Full) skip by AppLaunch";
                } else {
                    NandswapRecord nandswapRecord = (NandswapRecord) perProcessNandswap.mNandswapMap.get(num);
                    if (nandswapRecord != null) {
                        long[] anonSizeAndZramSize = ChimeraCommonUtil.getAnonSizeAndZramSize(intValue);
                        Iterator it2 = it;
                        if (doNandswapApp(nandswapRecord, 15, 1, false) > 0) {
                            i++;
                            j = (anonSizeAndZramSize[0] - ChimeraCommonUtil.getAnonSizeAndZramSize(intValue)[0]) + j;
                        }
                        it = it2;
                    } else if (isDebugEnabled) {
                        AnyMotionDetector$$ExternalSyntheticOutline0.m(intValue, "Swap After boot(Full) skip: no nandswap record ", str);
                    }
                }
            }
            Slog.i(str, PerProcessNandswap.m328$$Nest$mappendLogMessage(PerProcessNandswap.this, str2, uptimeMillis, j, i, isDebugEnabled, m331$$Nest$smgetMemoryInfoIfDebugEnabled));
            return j;
        }

        public final long doSwapAfterBootSome() {
            String str;
            String str2;
            long uptimeMillis = SystemClock.uptimeMillis();
            boolean isDebugEnabled = PerProcessNandswap.isDebugEnabled();
            ArrayList arrayList = new ArrayList();
            HashSet hashSet = new HashSet();
            PerProcessNandswap perProcessNandswap = PerProcessNandswap.this;
            Iterator it = ((ArrayList) perProcessNandswap.mSystemRepository.getRunningAppProcesses()).iterator();
            while (true) {
                boolean hasNext = it.hasNext();
                str = this.TAG;
                if (!hasNext) {
                    break;
                }
                SystemRepository.RunningAppProcessInfo runningAppProcessInfo = (SystemRepository.RunningAppProcessInfo) it.next();
                hashSet.add(Integer.valueOf(runningAppProcessInfo.pid));
                if (!PerProcessNandswap.m329$$Nest$misSamsungProtectApps(perProcessNandswap, runningAppProcessInfo.processName) && !"system".equals(runningAppProcessInfo.processName)) {
                    NandswapRecord nandswapRecord = (NandswapRecord) perProcessNandswap.mNandswapMap.get(Integer.valueOf(runningAppProcessInfo.pid));
                    if (nandswapRecord != null) {
                        if (!PerProcessNandswap.m332$$Nest$smisAnonPageNotEnough(runningAppProcessInfo.pid)) {
                            arrayList.add(nandswapRecord);
                        } else if (isDebugEnabled) {
                            DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Swap After boot(Some) skip: not enough anonPages "), runningAppProcessInfo.pid, str);
                        }
                    } else if (uptimeMillis - runningAppProcessInfo.lastActivityTime >= 3000) {
                        nandswapRecord = new NandswapRecord(runningAppProcessInfo.processName, runningAppProcessInfo.pid, -10000, -1);
                        arrayList.add(nandswapRecord);
                    } else if (isDebugEnabled) {
                        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Swap After boot(Some) skip: newly start "), runningAppProcessInfo.pid, str);
                    }
                }
            }
            if (!arrayList.isEmpty()) {
                Pair processStatesAndOomScoresForPIDs = perProcessNandswap.mSystemRepository.getProcessStatesAndOomScoresForPIDs(arrayList.stream().mapToInt(new PerProcessNandswap$$ExternalSyntheticLambda0()).toArray());
                if (processStatesAndOomScoresForPIDs != null) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        NandswapRecord nandswapRecord2 = (NandswapRecord) arrayList.get(i);
                        nandswapRecord2.procState = ((int[]) processStatesAndOomScoresForPIDs.first)[i];
                        nandswapRecord2.adj = ((int[]) processStatesAndOomScoresForPIDs.second)[i];
                    }
                }
            }
            Iterator it2 = ((ArrayList) perProcessNandswap.mSystemRepository.getNativeProcesses(hashSet)).iterator();
            while (it2.hasNext()) {
                ProcessCpuTracker.Stats stats = (ProcessCpuTracker.Stats) it2.next();
                NandswapRecord nandswapRecord3 = (NandswapRecord) perProcessNandswap.mNandswapMap.get(Integer.valueOf(stats.pid));
                if (nandswapRecord3 == null) {
                    nandswapRecord3 = new NandswapRecord(stats.name, stats.pid, -1000, -1);
                } else if (PerProcessNandswap.m332$$Nest$smisAnonPageNotEnough(stats.pid)) {
                    if (isDebugEnabled) {
                        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Swap After boot(Some) skip: not enough anonPages "), stats.pid, str);
                    }
                }
                arrayList.add(nandswapRecord3);
            }
            if (arrayList.isEmpty()) {
                perProcessNandswap.mSwapAfterBootLog.append("Swap After boot(Some) no reclaim target!");
                Slog.i(str, "Swap After boot(Some) no reclaim target!");
                return 0L;
            }
            String m331$$Nest$smgetMemoryInfoIfDebugEnabled = PerProcessNandswap.m331$$Nest$smgetMemoryInfoIfDebugEnabled(isDebugEnabled);
            Iterator it3 = arrayList.iterator();
            int i2 = 0;
            long j = 0;
            while (true) {
                if (!it3.hasNext()) {
                    str2 = "Swap After boot(Some) successfully";
                    break;
                }
                NandswapRecord nandswapRecord4 = (NandswapRecord) it3.next();
                if (UnifiedMemoryReclaimer.isInAppLaunch()) {
                    if (isDebugEnabled) {
                        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Swap After boot(Some) skip: app launch "), nandswapRecord4.pid, str);
                    }
                    str2 = "Swap After boot(Some) skip by AppLaunch";
                } else {
                    NandswapRecord nandswapRecord5 = (NandswapRecord) perProcessNandswap.mNandswapMap.get(Integer.valueOf(nandswapRecord4.pid));
                    if (nandswapRecord5 == null) {
                        perProcessNandswap.mNandswapMap.put(Integer.valueOf(nandswapRecord4.pid), nandswapRecord4);
                    } else {
                        nandswapRecord5.adj = nandswapRecord4.adj;
                        nandswapRecord5.procState = nandswapRecord4.procState;
                        nandswapRecord4 = nandswapRecord5;
                    }
                    long[] anonSizeAndZramSize = ChimeraCommonUtil.getAnonSizeAndZramSize(nandswapRecord4.pid);
                    if (doNandswapApp(nandswapRecord4, 15, 1, false) > 0) {
                        i2++;
                        j += anonSizeAndZramSize[0] - ChimeraCommonUtil.getAnonSizeAndZramSize(nandswapRecord4.pid)[0];
                    }
                }
            }
            Slog.i(str, PerProcessNandswap.m328$$Nest$mappendLogMessage(PerProcessNandswap.this, str2, uptimeMillis, j, i2, isDebugEnabled, m331$$Nest$smgetMemoryInfoIfDebugEnabled));
            return j;
        }

        /* JADX WARN: Code restructure failed: missing block: B:24:0x005d, code lost:
        
            if (1 == r21) goto L35;
         */
        /* JADX WARN: Code restructure failed: missing block: B:86:0x006e, code lost:
        
            if (r5 != false) goto L35;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int getNandswapNonActivityAppType(com.android.server.chimera.ppn.PerProcessNandswap.NandswapRecord r20, int r21, int r22) {
            /*
                Method dump skipped, instructions count: 328
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.chimera.ppn.PerProcessNandswap.NandswapHandler.getNandswapNonActivityAppType(com.android.server.chimera.ppn.PerProcessNandswap$NandswapRecord, int, int):int");
        }

        public final Set getReclaimSetForNandswap(Function function, boolean z) {
            int i;
            String[] strArr;
            TreeSet treeSet = new TreeSet(new PerProcessNandswap$NandswapHandler$$ExternalSyntheticLambda2());
            PerProcessNandswap perProcessNandswap = PerProcessNandswap.this;
            ArrayList arrayList = (ArrayList) perProcessNandswap.mSystemRepository.getRunningAppProcesses();
            int size = arrayList.size();
            int[] iArr = new int[size];
            HashSet hashSet = new HashSet();
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                int i3 = ((SystemRepository.RunningAppProcessInfo) arrayList.get(i2)).pid;
                hashSet.add(Integer.valueOf(i3));
                iArr[i2] = i3;
            }
            Pair processStatesAndOomScoresForPIDs = perProcessNandswap.mSystemRepository.getProcessStatesAndOomScoresForPIDs(iArr);
            int[] iArr2 = (int[]) processStatesAndOomScoresForPIDs.first;
            int[] iArr3 = (int[]) processStatesAndOomScoresForPIDs.second;
            for (int i4 = 0; i4 < size; i4++) {
                SystemRepository.RunningAppProcessInfo runningAppProcessInfo = (SystemRepository.RunningAppProcessInfo) arrayList.get(i4);
                int i5 = iArr3[i4];
                if (((Boolean) function.apply(Integer.valueOf(i5))).booleanValue() && !PerProcessNandswap.m329$$Nest$misSamsungProtectApps(perProcessNandswap, runningAppProcessInfo.processName)) {
                    treeSet.add(new Pair(Integer.valueOf(runningAppProcessInfo.pid), Integer.valueOf(i5)));
                    String str = runningAppProcessInfo.processName;
                    int i6 = runningAppProcessInfo.pid;
                    int i7 = iArr2[i4];
                    NandswapRecord nandswapRecord = (NandswapRecord) perProcessNandswap.mNandswapMap.get(Integer.valueOf(i6));
                    if (nandswapRecord == null) {
                        perProcessNandswap.mNandswapMap.put(Integer.valueOf(i6), new NandswapRecord(str, i6, i5, i7));
                    } else {
                        nandswapRecord.adj = i5;
                        nandswapRecord.procState = i7;
                    }
                }
            }
            if (((Boolean) function.apply(-1000)).booleanValue()) {
                Iterator it = ((ArrayList) perProcessNandswap.mSystemRepository.getNativeProcesses(hashSet)).iterator();
                while (it.hasNext()) {
                    ProcessCpuTracker.Stats stats = (ProcessCpuTracker.Stats) it.next();
                    if (z) {
                        SystemRepository systemRepository = perProcessNandswap.mSystemRepository;
                        String[] packagesForUid = systemRepository.mPackageManager.getPackagesForUid(stats.uid);
                        if (packagesForUid != null) {
                            strArr = packagesForUid;
                            i = 0;
                        } else {
                            i = 0;
                            strArr = new String[0];
                        }
                        if (strArr.length < 1) {
                            strArr = new String[1];
                            strArr[i] = stats.name;
                        }
                        String[] strArr2 = this.nativePackageNameFilter;
                        int length = strArr2.length;
                        int i8 = i;
                        while (true) {
                            if (i8 < length) {
                                if (strArr[i].contains(strArr2[i8])) {
                                    break;
                                }
                                i8++;
                            } else if (PerProcessNandswap.m329$$Nest$misSamsungProtectApps(perProcessNandswap, stats.name)) {
                            }
                        }
                    }
                    treeSet.add(new Pair(Integer.valueOf(stats.pid), -1000));
                    perProcessNandswap.checkProcessStatusForNandswap(stats.pid, -1000, stats.name);
                }
            }
            return treeSet;
        }

        /* JADX WARN: Removed duplicated region for block: B:52:0x0152 A[Catch: Exception -> 0x0230, TryCatch #0 {Exception -> 0x0230, blocks: (B:3:0x000e, B:7:0x0015, B:17:0x002d, B:19:0x0042, B:21:0x004a, B:24:0x0057, B:25:0x0060, B:29:0x0069, B:33:0x005c, B:34:0x0077, B:36:0x0084, B:39:0x008f, B:41:0x0095, B:43:0x00b0, B:44:0x00b4, B:52:0x0152, B:55:0x015b, B:57:0x0175, B:59:0x00da, B:62:0x00e5, B:65:0x00ee, B:67:0x00fa, B:77:0x00c3, B:78:0x00c4, B:79:0x00c8, B:86:0x017e, B:88:0x017f, B:92:0x0199, B:93:0x01ae, B:95:0x01b6, B:98:0x01ef, B:100:0x01fe, B:102:0x020d, B:104:0x0213, B:108:0x021b, B:110:0x0221, B:112:0x0229, B:116:0x01a4, B:46:0x00b5, B:47:0x00bf, B:81:0x00c9, B:82:0x00d3), top: B:2:0x000e, inners: #1, #2 }] */
        /* JADX WARN: Removed duplicated region for block: B:54:0x0159  */
        /* JADX WARN: Removed duplicated region for block: B:59:0x00da A[Catch: Exception -> 0x0230, TRY_ENTER, TryCatch #0 {Exception -> 0x0230, blocks: (B:3:0x000e, B:7:0x0015, B:17:0x002d, B:19:0x0042, B:21:0x004a, B:24:0x0057, B:25:0x0060, B:29:0x0069, B:33:0x005c, B:34:0x0077, B:36:0x0084, B:39:0x008f, B:41:0x0095, B:43:0x00b0, B:44:0x00b4, B:52:0x0152, B:55:0x015b, B:57:0x0175, B:59:0x00da, B:62:0x00e5, B:65:0x00ee, B:67:0x00fa, B:77:0x00c3, B:78:0x00c4, B:79:0x00c8, B:86:0x017e, B:88:0x017f, B:92:0x0199, B:93:0x01ae, B:95:0x01b6, B:98:0x01ef, B:100:0x01fe, B:102:0x020d, B:104:0x0213, B:108:0x021b, B:110:0x0221, B:112:0x0229, B:116:0x01a4, B:46:0x00b5, B:47:0x00bf, B:81:0x00c9, B:82:0x00d3), top: B:2:0x000e, inners: #1, #2 }] */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r17) {
            /*
                Method dump skipped, instructions count: 576
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.chimera.ppn.PerProcessNandswap.NandswapHandler.handleMessage(android.os.Message):void");
        }

        /* JADX WARN: Removed duplicated region for block: B:106:0x02e9  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x0307  */
        /* JADX WARN: Removed duplicated region for block: B:79:0x0226  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void nandSwapAlwaysRunningProcess(long r39) {
            /*
                Method dump skipped, instructions count: 842
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.chimera.ppn.PerProcessNandswap.NandswapHandler.nandSwapAlwaysRunningProcess(long):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class NandswapLogger {
        public static final ArrayList mHistory = new ArrayList();

        public static void saveSwapOutLog(String str, int i, int i2, int i3, long j, long j2, long j3) {
            StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i, "[OUT] ", str, " ", " ");
            ServiceKeeper$$ExternalSyntheticOutline0.m(i2, i3, " ", " ", m);
            m.append(j);
            BootReceiver$$ExternalSyntheticOutline0.m(m, " ", j2, " ");
            m.append(j3);
            String sb = m.toString();
            ArrayList arrayList = mHistory;
            synchronized (arrayList) {
                try {
                    arrayList.add(new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date()) + " " + sb);
                    if (arrayList.size() > 128) {
                        arrayList.remove(0);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NandswapMsgHandler extends Handler {
        public final String[] LAUNCHER_APP_PKGNAME;
        public final String TAG;
        public final ArrayList recentEntryProcessNames;

        public NandswapMsgHandler() {
            super(PerProcessNandswap.this.mMsgThread.getLooper());
            this.LAUNCHER_APP_PKGNAME = new String[]{KnoxCustomManagerService.LAUNCHER_PACKAGE};
            this.recentEntryProcessNames = new ArrayList();
            this.TAG = NandswapMsgHandler.class.getSimpleName();
        }

        public final void changePPRState(NandswapRecord nandswapRecord, int i) {
            if (PerProcessNandswap.isDebugEnabled()) {
                Slog.d(this.TAG, "changePPRState: " + nandswapRecord.processName + " pid:" + nandswapRecord.pid + " ppnState:" + i);
            }
            synchronized (nandswapRecord.ppnStateLock) {
                nandswapRecord.ppnState = i;
            }
        }

        public final boolean checkCanDoPPRForCachedApp(NandswapRecord nandswapRecord) {
            NandswapRecord nandswapRecord2 = (NandswapRecord) PerProcessNandswap.this.mNandswapMap.get(Integer.valueOf(nandswapRecord.pid));
            if (nandswapRecord2 == null) {
                return true;
            }
            if (nandswapRecord2.ppnState == 0) {
                return nandswapRecord2.lastNandswapTime == 0 || SystemClock.uptimeMillis() - nandswapRecord2.lastNandswapTime > 60000;
            }
            return false;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            NandswapRecord nandswapRecord;
            NandswapRecord nandswapRecord2;
            PerProcessNandswap perProcessNandswap = PerProcessNandswap.this;
            try {
                if (perProcessNandswap.WRITEBACK_ENABLED && PerProcessNandswap.__SlotCount > 0) {
                    int i = message.what;
                    boolean z = true;
                    String str = this.TAG;
                    if (i == 1) {
                        NandswapRecord nandswapRecord3 = (NandswapRecord) message.obj;
                        int i2 = message.arg1;
                        int i3 = message.arg2;
                        String str2 = nandswapRecord3.processName;
                        int i4 = nandswapRecord3.pid;
                        if (PerProcessNandswap.isDebugEnabled()) {
                            Slog.d(str, "change_ppr_state_msg: " + str2 + " pid:" + i4 + " ppnState:" + i2 + " action:" + i3);
                        }
                        changePPRState(nandswapRecord3, i2);
                        return;
                    }
                    if (i == 2) {
                        int i5 = message.arg1;
                        NandswapRecord nandswapRecord4 = (NandswapRecord) perProcessNandswap.mNandswapMap.get(Integer.valueOf(i5));
                        if (nandswapRecord4 == null) {
                            return;
                        }
                        if (perProcessNandswap.isKeyAppEnable()) {
                            if (PerProcessNandswap.STATUS_ALREADY_PPN.equals(((LinkedHashMap) perProcessNandswap.mKeyApps).get(nandswapRecord4.processName)) && !PerProcessNandswap.m329$$Nest$misSamsungProtectApps(perProcessNandswap, nandswapRecord4.processName)) {
                                ((HashMap) perProcessNandswap.mKeyApps).put(nandswapRecord4.processName, PerProcessNandswap.STATUS_KEY_APP);
                            }
                        }
                        int i6 = nandswapRecord4.ppnState;
                        if (i6 == 1 || i6 == 2 || i6 == 4) {
                            if (PerProcessNandswap.isDebugEnabled()) {
                                Slog.d(str, "app_reentry_msg: " + nandswapRecord4.processName + " pid:" + i5);
                            }
                            if (PerProcessNandswap.isPrefetchActionEnabled()) {
                                Slog.i(str, "madvise_prefetch for " + nandswapRecord4.processName);
                                if (PerProcessNandswap.FAST_MADVISE_ENABLED) {
                                    PerProcessNandswap.prefetchProcessFast(i5);
                                } else {
                                    PerProcessNandswap.prefetchProcess(i5);
                                }
                            }
                            changePPRState(nandswapRecord4, 0);
                            return;
                        }
                        return;
                    }
                    if (i == 3) {
                        int i7 = message.arg1;
                        NandswapRecord nandswapRecord5 = (NandswapRecord) perProcessNandswap.mNandswapMap.get(Integer.valueOf(i7));
                        if (nandswapRecord5 == null) {
                            return;
                        }
                        if (perProcessNandswap.isKeyAppEnable()) {
                            ((HashMap) perProcessNandswap.mKeyApps).remove(nandswapRecord5.processName);
                        }
                        if (PerProcessNandswap.isDebugEnabled()) {
                            Slog.d(str, "app_died_msg: " + nandswapRecord5.processName + " pid:" + nandswapRecord5.pid);
                        }
                        perProcessNandswap.mNandswapMap.remove(Integer.valueOf(i7));
                        return;
                    }
                    if (i != 4) {
                        if (i == 6) {
                            if (PerProcessNandswap.isWritebackOnBGEnabled() && (nandswapRecord = (NandswapRecord) message.obj) != null) {
                                if (PerProcessNandswap.isDebugEnabled()) {
                                    Slog.d(str, "try_to_nandswap_by_bg_event_msg: " + nandswapRecord.processName + " pid: " + nandswapRecord.pid);
                                }
                                tryToNandswapByBgEvent(nandswapRecord);
                                return;
                            }
                            return;
                        }
                        if (i != 7) {
                            Slog.w(str, "default: " + message.what);
                            return;
                        } else {
                            if (PerProcessNandswap.isWritebackOnFreezeEnabled() && (nandswapRecord2 = (NandswapRecord) message.obj) != null) {
                                if (PerProcessNandswap.isDebugEnabled()) {
                                    Slog.d(str, "try_to_nandswap_by_freeze_event_msg: " + nandswapRecord2.processName + " pid: " + nandswapRecord2.pid);
                                }
                                tryToNandswapByFreezeEvent(nandswapRecord2);
                                return;
                            }
                            return;
                        }
                    }
                    String str3 = (String) message.obj;
                    String[] strArr = this.LAUNCHER_APP_PKGNAME;
                    int length = strArr.length;
                    int i8 = 0;
                    while (true) {
                        if (i8 >= length) {
                            break;
                        }
                        if (str3.equals(strArr[i8])) {
                            z = false;
                            break;
                        }
                        i8++;
                    }
                    if (!z) {
                        if (PerProcessNandswap.isDebugEnabled()) {
                            Slog.d(str, "app_entry_msg: reject launcher app " + str3);
                            return;
                        }
                        return;
                    }
                    int i9 = 0;
                    while (true) {
                        if (i9 >= this.recentEntryProcessNames.size()) {
                            break;
                        }
                        if (str3.equals((String) this.recentEntryProcessNames.get(i9))) {
                            this.recentEntryProcessNames.remove(i9);
                            z = false;
                            break;
                        }
                        i9++;
                    }
                    this.recentEntryProcessNames.add(str3);
                    if (!z) {
                        if (PerProcessNandswap.isDebugEnabled()) {
                            Slog.d(str, "app_entry_msg: reject recent app " + str3);
                            return;
                        }
                        return;
                    }
                    if (this.recentEntryProcessNames.size() > 2) {
                        this.recentEntryProcessNames.remove(0);
                    }
                    if (PerProcessNandswap.isDebugEnabled()) {
                        Slog.d(str, "app_entry_msg: " + str3);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public final void tryToNandswapByBgEvent(NandswapRecord nandswapRecord) {
            try {
                if (checkCanDoPPRForCachedApp(nandswapRecord) && !tryToNandswapProcess(nandswapRecord, false) && PerProcessNandswap.isDebugEnabled()) {
                    Slog.w(this.TAG, "try_to_nandswap_by_bg_event not accepted " + nandswapRecord.processName + " pid: " + nandswapRecord.pid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public final void tryToNandswapByFreezeEvent(NandswapRecord nandswapRecord) {
            try {
                if (checkCanDoPPRForCachedApp(nandswapRecord) && !tryToNandswapProcess(nandswapRecord, true) && PerProcessNandswap.isDebugEnabled()) {
                    Slog.w(this.TAG, "try_to_nandswap_by_freeze_event not accepted " + nandswapRecord.processName + " pid: " + nandswapRecord.pid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:63:0x01f9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean tryToNandswapProcess(com.android.server.chimera.ppn.PerProcessNandswap.NandswapRecord r11, boolean r12) {
            /*
                Method dump skipped, instructions count: 542
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.chimera.ppn.PerProcessNandswap.NandswapMsgHandler.tryToNandswapProcess(com.android.server.chimera.ppn.PerProcessNandswap$NandswapRecord, boolean):boolean");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NandswapRecord implements Comparable {
        public int adj;
        public long lastNandswapTime;
        public long pendingNandswapTime;
        public final int pid;
        public int ppnState;
        public final Object ppnStateLock;
        public int procState;
        public final String processName;
        public final int uid;

        public NandswapRecord(int i, String str, int i2, int i3) {
            this(str, i2, i3, -1);
            this.uid = i;
        }

        public NandswapRecord(String str, int i, int i2, int i3) {
            this.lastNandswapTime = 0L;
            this.pendingNandswapTime = 0L;
            this.ppnState = 0;
            this.ppnStateLock = new Object();
            this.processName = str;
            this.pid = i;
            this.adj = i2;
            this.procState = i3;
        }

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            return Long.compare(this.pendingNandswapTime, ((NandswapRecord) obj).pendingNandswapTime);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class ZramInfo {
        public static final String TAG;
        public static int mWritebackEnabled;
        public static int mWritebackLimitEnabled;

        /* renamed from: -$$Nest$smreadZramBdstat, reason: not valid java name */
        public static long[] m335$$Nest$smreadZramBdstat() {
            BufferedReader bufferedReader;
            long[] array;
            String str = TAG;
            long[] jArr = null;
            try {
                bufferedReader = new BufferedReader(new FileReader("/sys/block/zram0/bd_stat"));
                try {
                    array = Arrays.stream(bufferedReader.readLine().trim().split("\\s+")).mapToLong(new PerProcessNandswap$ZramInfo$$ExternalSyntheticLambda0()).toArray();
                } finally {
                }
            } catch (Exception unused) {
                if (PerProcessNandswap.isDebugEnabled()) {
                    Slog.d(str, "failed to read /sys/block/zram0/bd_stat");
                }
            }
            if (array.length > 10) {
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
            if (array.length <= 7) {
                array = null;
            }
            bufferedReader.close();
            jArr = array;
            if (PerProcessNandswap.isDebugEnabled()) {
                Slog.d(str, "bdstats : " + Arrays.toString(jArr));
            }
            return jArr;
        }

        static {
            boolean z = PerProcessNandswap.FAST_MADVISE_ENABLED;
            TAG = "PerProcessNandswap";
            mWritebackEnabled = -1;
            mWritebackLimitEnabled = -1;
        }

        public static boolean isWritebackEnabled() {
            if (mWritebackEnabled == -1) {
                boolean m45m = BatteryService$$ExternalSyntheticOutline0.m45m("/sys/block/zram0/writeback_limit");
                String str = TAG;
                if (!m45m) {
                    Slog.w(str, "/sys/block/zram0/writeback_limit not exist");
                    mWritebackEnabled = 0;
                } else if (BatteryService$$ExternalSyntheticOutline0.m45m("/data/per_boot/zram_swap")) {
                    mWritebackEnabled = 1;
                } else {
                    Slog.w(str, "/data/per_boot/zram_swap not exist");
                    mWritebackEnabled = 0;
                }
                SystemServiceManager$$ExternalSyntheticOutline0.m(new StringBuilder("isWritebackEnabled: "), mWritebackEnabled, str);
            }
            return mWritebackEnabled == 1;
        }

        public static boolean isWritebackLimitEnabled() {
            String str = TAG;
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
                    Slog.w(str, "failed to read /sys/block/zram0/writeback_limit_enable");
                }
                SystemServiceManager$$ExternalSyntheticOutline0.m(new StringBuilder("isWritebackLimitEnabled: "), mWritebackLimitEnabled, str);
            }
            return mWritebackLimitEnabled == 1;
        }

        public static boolean isWritebackQuotaAvailable() {
            return isWritebackEnabled() && (!isWritebackLimitEnabled() || readZramWritebackLimit() > 0);
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
    }

    /* renamed from: -$$Nest$mappendLogMessage, reason: not valid java name */
    public static String m328$$Nest$mappendLogMessage(PerProcessNandswap perProcessNandswap, String str, long j, long j2, int i, boolean z, String str2) {
        perProcessNandswap.getClass();
        String str3 = str + ", cost " + (SystemClock.uptimeMillis() - j) + "ms, reclaim " + (j2 / 1024) + "MB, success count: " + i;
        if (z) {
            MemInfoReader memInfoReader = new MemInfoReader();
            memInfoReader.readMemInfo();
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str2, " After SwapFree: ");
            m.append(memInfoReader.getSwapFreeSizeKb() / 1024);
            m.append(", AvailableMem: ");
            m.append(memInfoReader.getAvailableSize() / 1048576);
            str3 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str3, m.toString());
        }
        perProcessNandswap.mSwapAfterBootLog.append(new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()) + " " + str3);
        return str3;
    }

    /* renamed from: -$$Nest$misSamsungProtectApps, reason: not valid java name */
    public static boolean m329$$Nest$misSamsungProtectApps(PerProcessNandswap perProcessNandswap, String str) {
        perProcessNandswap.getClass();
        return KnoxCustomManagerService.LAUNCHER_PACKAGE.equals(str) || KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME.equals(str) || Constants.SYSTEMUI_PACKAGE_NAME.equals(str);
    }

    /* renamed from: -$$Nest$mupdateLastNandswapStats, reason: not valid java name */
    public static void m330$$Nest$mupdateLastNandswapStats(PerProcessNandswap perProcessNandswap, int i, String str, long[] jArr) {
        perProcessNandswap.mLastNandswapStats.remove(Integer.valueOf(i));
        perProcessNandswap.mLastNandswapStats.put(Integer.valueOf(i), new LastNandswapStats(str, jArr));
    }

    /* renamed from: -$$Nest$smgetMemoryInfoIfDebugEnabled, reason: not valid java name */
    public static String m331$$Nest$smgetMemoryInfoIfDebugEnabled(boolean z) {
        if (!z) {
            return " ";
        }
        MemInfoReader memInfoReader = new MemInfoReader();
        memInfoReader.readMemInfo();
        return " SwapFree: " + (memInfoReader.getSwapFreeSizeKb() / 1024) + ", AvailableMem: " + (memInfoReader.getAvailableSize() / 1048576);
    }

    /* renamed from: -$$Nest$smisAnonPageNotEnough, reason: not valid java name */
    public static boolean m332$$Nest$smisAnonPageNotEnough(int i) {
        long[] smapsRollupParams = ChimeraCommonUtil.getSmapsRollupParams(i, new String[]{"Pss_Anon:", "SwapPss:"});
        long j = smapsRollupParams[0];
        long j2 = smapsRollupParams[1];
        return j < 5000 || (100 * j2) / (j + j2) > ((long) SWAP_AFTER_BOOT_LOW_SWAP_PERCENT);
    }

    static {
        Slog.i("PerProcessNandswap", "Static Initialization of PerProcessNandswap");
    }

    private static native long __compactProcessForWriteback(int i, int i2);

    private static native long __compactProcessForWritebackFast(int i, int i2);

    private static native boolean __setWriteBoosterPath();

    public static long compactProcessForWriteback(int i, int i2) {
        return FAST_MADVISE_ENABLED ? __compactProcessForWritebackFast(i, i2) : __compactProcessForWriteback(i, i2);
    }

    public static final void decideSlotCount() {
        new MemInfoReader().readMemInfo();
        int swapTotalSizeKb = ((int) (((r0.getSwapTotalSizeKb() / 1024) + 127) / 128.0f)) * 128;
        if (__SlotCountMap == null) {
            String str = SystemProperties.get("ro.sys.kernelmemory.nandswap.slot_count_map", getPolicyVersion() >= 2 ? "default" : "");
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
                        throw new Exception("Invalid slot_count_map: ".concat(str));
                    }
                    Slog.i("PerProcessNandswap", "slot_cout_map: ".concat(str));
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
                    __SlotCountMap = arrayList;
                }
            }
            Slog.w("PerProcessNandswap", "Empty slot_count_map");
            arrayList.clear();
            arrayList.add(new Pair(0, 0));
            __SlotCountMap = arrayList;
        }
        ArrayList arrayList2 = __SlotCountMap;
        if (arrayList2 == null || arrayList2.size() <= 0) {
            Slog.e("PerProcessNandswap", "invalid slotCountMap");
        } else {
            __SlotCount = ((Integer) ((Pair) arrayList2.get(0)).second).intValue();
            int i4 = 0;
            while (true) {
                if (i4 >= arrayList2.size()) {
                    break;
                }
                Pair pair = (Pair) arrayList2.get(i4);
                if (pair == null) {
                    StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i4, "invalid slotCountMap item: ", "/");
                    m.append(arrayList2.size());
                    Slog.e("PerProcessNandswap", m.toString());
                    __SlotCount = 0;
                    break;
                }
                int intValue = ((Integer) pair.first).intValue();
                int intValue2 = ((Integer) pair.second).intValue();
                if (swapTotalSizeKb >= intValue * 1024) {
                    __SlotCount = intValue2;
                }
                i4++;
            }
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m(swapTotalSizeKb, "swap_total: ", ", slot_count: "), __SlotCount, "PerProcessNandswap");
    }

    public static synchronized PerProcessNandswap getInstance() {
        PerProcessNandswap perProcessNandswap;
        synchronized (PerProcessNandswap.class) {
            try {
                if (INSTANCE == null) {
                    INSTANCE = new PerProcessNandswap();
                }
                perProcessNandswap = INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        return perProcessNandswap;
    }

    public static final int getMinSwapFreePercentage() {
        if (__MinSwapFreePercentage == null) {
            __MinSwapFreePercentage = Integer.valueOf(SystemProperties.getInt("ro.sys.kernelmemory.nandswap.min_swap_free_percentage", 2));
        }
        return __MinSwapFreePercentage.intValue();
    }

    public static final int getPolicyVersion() {
        if (__PolicyVersion == null) {
            int i = isPrefetchSupported() ? 3 : 1;
            Integer valueOf = Integer.valueOf(SystemProperties.getInt("ro.sys.kernelmemory.nandswap.policy_version", i));
            __PolicyVersion = valueOf;
            if (valueOf.intValue() < 1 || __PolicyVersion.intValue() > 3) {
                Slog.e("PerProcessNandswap", "Invalid policy_version (" + __PolicyVersion + ").Reset it as " + i + ".");
                __PolicyVersion = Integer.valueOf(i);
            }
        }
        return __PolicyVersion.intValue();
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

    public static int getSwapFreePercentage(MemInfoReader memInfoReader) {
        long swapFreeSizeKb = memInfoReader.getSwapFreeSizeKb();
        long swapTotalSizeKb = memInfoReader.getSwapTotalSizeKb();
        long j = swapTotalSizeKb <= 0 ? 0L : (swapFreeSizeKb * 100) / swapTotalSizeKb;
        long j2 = j > 0 ? j : 0L;
        return (int) (j2 < 100 ? j2 : 100L);
    }

    public static long[] getWritebackSizePid(int i) {
        if (i <= 0) {
            return null;
        }
        String[] strArr = {"Swap:", "Writeback:"};
        String m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "/proc/", "/smaps_rollup");
        long[] jArr = new long[2];
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(m));
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
                BinaryTransparencyService$$ExternalSyntheticOutline0.m("failed to read ", m, "PerProcessNandswap");
            }
        }
        if (jArr[1] == 0) {
            return null;
        }
        return jArr;
    }

    public static final boolean isDebugEnabled() {
        if (__DebugEnabled == null) {
            __DebugEnabled = Boolean.valueOf(SystemProperties.getBoolean("ro.sys.kernelmemory.nandswap.debug", false));
        }
        return __DebugEnabled.booleanValue();
    }

    public static final boolean isPrefetchActionEnabled() {
        if (__PrefetchActionEnabled == null) {
            __PrefetchActionEnabled = Boolean.valueOf(SystemProperties.getBoolean("ro.sys.kernelmemory.nandswap.prefetch_action", getPolicyVersion() >= 3));
        }
        return __PrefetchActionEnabled.booleanValue();
    }

    public static final boolean isPrefetchSupported() {
        if (__PrefetchSupported == null) {
            __PrefetchSupported = Boolean.FALSE;
            int i = 0;
            while (true) {
                if (i >= 5) {
                    break;
                }
                int myPid = Process.myPid();
                long prefetchProcessFast = FAST_MADVISE_ENABLED ? prefetchProcessFast(myPid) : prefetchProcess(myPid);
                BatteryService$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(myPid, i, "prefetch trial: pid=", " (", ") ret="), prefetchProcessFast, "PerProcessNandswap");
                if (prefetchProcessFast > 0) {
                    __PrefetchSupported = Boolean.TRUE;
                    DeviceIdleController$$ExternalSyntheticOutline0.m(i, "prefetch supported (", ")", "PerProcessNandswap");
                    break;
                }
                i++;
            }
            if (!__PrefetchSupported.booleanValue()) {
                Slog.d("PerProcessNandswap", "prefetch not supported");
            }
        }
        return __PrefetchSupported.booleanValue();
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

    /* JADX INFO: Access modifiers changed from: private */
    public static native long prefetchProcess(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long prefetchProcessFast(int i);

    public final void checkProcessStatusForNandswap(int i, int i2, String str) {
        NandswapRecord nandswapRecord = (NandswapRecord) this.mNandswapMap.get(Integer.valueOf(i));
        if (nandswapRecord != null) {
            nandswapRecord.adj = i2;
        } else {
            this.mNandswapMap.put(Integer.valueOf(i), new NandswapRecord(str, i, i2, -1));
        }
    }

    public final void dumpInfo(PrintWriter printWriter, String[] strArr) {
        QuickSwap quickSwap;
        try {
            printWriter.println("== PerProcessNandswap dump start ==");
            if (strArr.length <= 1) {
                printWriter.println("Configurations");
                printWriter.println("  feature enable: true");
                printWriter.println("  debug: " + isDebugEnabled());
                printWriter.println("  min_swap_free_percentage: " + getMinSwapFreePercentage());
                printWriter.println("  policy_version: " + getPolicyVersion());
                printWriter.println("    slot_count_map: " + getSlotCountMapString());
                printWriter.println("    writeback_on_bg: " + isWritebackOnBGEnabled());
                printWriter.println("    writeback_on_freeze: " + isWritebackOnFreezeEnabled());
                printWriter.println("    pageout_cached_empty: " + isPageoutCachedEmptyEnable());
                printWriter.println("    swap_after_boot: " + isSwapAfterBootEnable());
                if (isSwapAfterBootEnable()) {
                    printWriter.println("      swap_after_boot log: ");
                    for (String str : (String[]) this.mSwapAfterBootLog.toArray()) {
                        printWriter.println("      " + str);
                    }
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
                    ArrayList arrayList = NandswapLogger.mHistory;
                    synchronized (arrayList) {
                        try {
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                printWriter.println((String) it.next());
                            }
                        } finally {
                        }
                    }
                }
            } else if ("writeback".equals(strArr[1])) {
                printWriter.println("writeback cmd is not supported with ship build");
            } else if ("prefetch".equals(strArr[1])) {
                printWriter.println("prefetch cmd is not supported with ship build");
            } else if (!"quotaPPN".equals(strArr[1]) && !"quickswap".equals(strArr[1]) && !"swap_after_boot".equals(strArr[1]) && "setprop".equals(strArr[1])) {
                printWriter.println("setprop cmd is not supported with ship build");
            }
        } catch (Exception unused) {
            printWriter.println("failed to dumpInfo");
        }
        printWriter.println("\n== PerProcessNandswap dump end ==");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.util.ArrayList] */
    public final void dumpProcessList(PrintWriter printWriter) {
        int i;
        ?? emptyList;
        ActivityManagerService activityManagerService;
        int i2;
        long[] jArr;
        int i3;
        char c;
        long[] writebackSizePid;
        int i4 = 0;
        if (IS_DEBUG_LEVEL_LOW) {
            return;
        }
        printWriter.println("\nProcessList state");
        ActivityManagerService activityManagerService2 = this.mSystemRepository.mActivityManagerService;
        ArrayList collectProcesses = activityManagerService2.collectProcesses(0, false, null);
        if (collectProcesses == null || collectProcesses.isEmpty()) {
            i = 0;
            emptyList = Collections.emptyList();
        } else {
            PerProcessNandswap perProcessNandswap = getInstance();
            int length = ActivityManagerService.DUMP_MEM_OOM_LABEL.length;
            int size = collectProcesses.size();
            long[] jArr2 = new long[length];
            ArrayList[] arrayListArr = new ArrayList[length];
            SparseArray sparseArray = new SparseArray();
            long uptimeMillis = SystemClock.uptimeMillis();
            int i5 = 0;
            while (i5 < size) {
                ProcessRecord processRecord = (ProcessRecord) collectProcesses.get(i5);
                String str = processRecord.processName;
                int i6 = processRecord.mPid;
                int setAdjWithServices = processRecord.mState.getSetAdjWithServices();
                ArrayList arrayList = collectProcesses;
                NandswapRecord nandswapRecord = (NandswapRecord) perProcessNandswap.mNandswapMap.get(Integer.valueOf(i6));
                if (nandswapRecord == null) {
                    activityManagerService = activityManagerService2;
                    i2 = i5;
                    c = 1;
                    jArr = null;
                    i3 = 0;
                } else {
                    activityManagerService = activityManagerService2;
                    i2 = i5;
                    i3 = 0;
                    c = 1;
                    jArr = new long[]{nandswapRecord.ppnState, nandswapRecord.lastNandswapTime};
                }
                if (jArr != null) {
                    int i7 = (int) jArr[i3];
                    long j = uptimeMillis - jArr[c];
                    if (i6 != 0 && (writebackSizePid = getWritebackSizePid(i6)) != null) {
                        if (writebackSizePid[c] != 0 || i7 != 0) {
                            i3 = 0;
                            MemoryItem memoryItem = new MemoryItem(i7, writebackSizePid[0], writebackSizePid[1], j, str + " (pid " + i6 + ")");
                            sparseArray.put(i6, memoryItem);
                            for (int i8 = 0; i8 < length; i8++) {
                                if (i8 != length - 1) {
                                    int[] iArr = ActivityManagerService.DUMP_MEM_OOM_ADJ;
                                    if (setAdjWithServices < iArr[i8] || setAdjWithServices >= iArr[i8 + 1]) {
                                    }
                                }
                                jArr2[i8] = jArr2[i8] + writebackSizePid[1];
                                if (arrayListArr[i8] == null) {
                                    arrayListArr[i8] = new ArrayList();
                                }
                                arrayListArr[i8].add(memoryItem);
                            }
                        }
                    }
                    i3 = 0;
                }
                i5 = i2 + 1;
                i4 = i3;
                collectProcesses = arrayList;
                activityManagerService2 = activityManagerService;
            }
            i = i4;
            activityManagerService2.mAppProfiler.forAllCpuStats(new ActivityManagerService$$ExternalSyntheticLambda20(sparseArray, jArr2, arrayListArr, 1));
            emptyList = new ArrayList();
            for (int i9 = i; i9 < length; i9++) {
                long j2 = jArr2[i9];
                if (j2 != 0) {
                    MemoryItem memoryItem2 = new MemoryItem(0, 0L, j2, -1L, ActivityManagerService.DUMP_MEM_OOM_LABEL[i9]);
                    memoryItem2.subitems = arrayListArr[i9];
                    emptyList.add(memoryItem2);
                }
            }
        }
        for (int i10 = i; i10 < emptyList.size(); i10++) {
            MemoryItem memoryItem3 = (MemoryItem) emptyList.get(i10);
            if (memoryItem3.writeback != 0) {
                printWriter.println("    " + memoryItem3.label + ": " + memoryItem3.writeback);
                ArrayList arrayList2 = memoryItem3.subitems;
                if (arrayList2 != null) {
                    Collections.sort(arrayList2, new AnonymousClass3());
                    for (int i11 = i; i11 < memoryItem3.subitems.size(); i11++) {
                        MemoryItem memoryItem4 = (MemoryItem) memoryItem3.subitems.get(i11);
                        printWriter.print("        ");
                        printWriter.println(memoryItem4.label + ": " + memoryItem4.writeback + " " + memoryItem4.swap + " " + memoryItem4.ppnState + " " + memoryItem4.lastNandswapTimeDiff);
                    }
                }
            }
        }
    }

    public final void init(SystemRepository systemRepository, ChimeraStrategy chimeraStrategy) {
        int i;
        try {
            Slog.i("PerProcessNandswap", "init start");
            if (!ZramInfo.isWritebackEnabled()) {
                Slog.w("PerProcessNandswap", "Writeback is disabled");
                this.WRITEBACK_ENABLED = false;
                return;
            }
            decideSlotCount();
            __setWriteBoosterPath();
            this.mSystemRepository = systemRepository;
            this.mChimeraStrategy = chimeraStrategy;
            initThreadAndHandler();
            if (isQuickSwapEnable()) {
                this.mQuickSwap = new QuickSwap(this, systemRepository);
            }
            this.WRITEBACK_ENABLED = true;
            Slog.i("PerProcessNandswap", "WritebackEnabled: " + ZramInfo.isWritebackEnabled());
            Slog.i("PerProcessNandswap", "PrefetchSupported: " + isPrefetchSupported());
            Slog.i("PerProcessNandswap", "WritebackLimitEnabled: " + ZramInfo.isWritebackLimitEnabled());
            Slog.i("PerProcessNandswap", "WritebackQuotaAvailable: " + ZramInfo.isWritebackQuotaAvailable());
            Slog.i("PerProcessNandswap", "quota: " + ZramInfo.readZramWritebackLimit());
            Slog.i("PerProcessNandswap", "policy_version: " + getPolicyVersion());
            Slog.i("PerProcessNandswap", "  slot_count: " + __SlotCount);
            Slog.i("PerProcessNandswap", "  writeback_on_bg: " + isWritebackOnBGEnabled());
            Slog.i("PerProcessNandswap", "  writeback_on_freeze: " + isWritebackOnFreezeEnabled());
            Slog.i("PerProcessNandswap", "  prefetch_action: " + isPrefetchActionEnabled());
            Slog.i("PerProcessNandswap", "init success");
            if (isSwapAfterBootEnable() && (i = SystemProperties.getInt("ro.sys.kernelmemory.nandswap.swap_after_boot_delay_mills", KnoxCustomManagerService.ONE_UI_VERSION_SEP_VERSION_GAP)) >= 0) {
                this.mNandswapHandler.sendEmptyMessageDelayed(5, i);
            }
        } catch (Exception unused) {
            Slog.e("PerProcessNandswap", "init failed");
            this.WRITEBACK_ENABLED = false;
        }
    }

    public final void initThreadAndHandler() {
        if (this.mNandswapThread == null) {
            ServiceThread serviceThread = new ServiceThread(10, "PerProcessNandswapThread", true);
            this.mNandswapThread = serviceThread;
            serviceThread.start();
            this.mNandswapHandler = new NandswapHandler();
            Process.setThreadGroupAndCpuset(this.mNandswapThread.getThreadId(), 2);
        }
        if (__SlotCount <= 0 || this.mMsgThread != null) {
            return;
        }
        ServiceThread serviceThread2 = new ServiceThread(0, "PPNandswapMsgThread", true);
        this.mMsgThread = serviceThread2;
        serviceThread2.start();
        this.mMsgHandler = new NandswapMsgHandler();
    }

    public final boolean isKeyAppEnable() {
        Boolean bool = this.__KeyAppEnable;
        if (bool != null) {
            return bool.booleanValue();
        }
        Boolean valueOf = Boolean.valueOf(SystemProperties.getBoolean("ro.sys.kernelmemory.nandswap.key_app", false));
        this.__KeyAppEnable = valueOf;
        return valueOf.booleanValue();
    }

    public final boolean isPageoutCachedEmptyEnable() {
        if (this.__PageoutCachedEmptyEnable == null) {
            this.__PageoutCachedEmptyEnable = Boolean.valueOf(SystemProperties.getBoolean("ro.sys.kernelmemory.nandswap.pageout_cached_empty", getPolicyVersion() >= 3));
        }
        return this.__PageoutCachedEmptyEnable.booleanValue() && this.mSystemRepository.mActivityManagerService.mOomAdjuster.mCachedAppOptimizer.useCompaction();
    }

    public final boolean isQuickSwapEnable() {
        Boolean bool = this.__QuickSwapEnable;
        if (bool != null) {
            return bool.booleanValue();
        }
        Boolean valueOf = Boolean.valueOf(SystemProperties.getBoolean("ro.sys.kernelmemory.nandswap.quickswap", false));
        this.__QuickSwapEnable = valueOf;
        return valueOf.booleanValue();
    }

    public final boolean isSwapAfterBootEnable() {
        if (this.__SwapAfterBootEnable == null) {
            this.__SwapAfterBootEnable = Boolean.valueOf(SystemProperties.getBoolean("ro.sys.kernelmemory.nandswap.swap_after_boot", false));
        }
        return this.__SwapAfterBootEnable.booleanValue();
    }

    public final void notifyDiedAppToPPR(int i) {
        try {
            if (this.WRITEBACK_ENABLED && __SlotCount > 0) {
                NandswapMsgHandler nandswapMsgHandler = this.mMsgHandler;
                nandswapMsgHandler.sendMessage(nandswapMsgHandler.obtainMessage(3, i, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void notifyReentryAppToPPR(int i) {
        try {
            if (this.WRITEBACK_ENABLED && __SlotCount > 0) {
                NandswapMsgHandler nandswapMsgHandler = this.mMsgHandler;
                nandswapMsgHandler.sendMessage(nandswapMsgHandler.obtainMessage(2, i, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void onProcessFrozen(int i, int i2, String str, int i3, boolean z) {
        if (this.WRITEBACK_ENABLED && __SlotCount > 0 && i3 >= 100 && i3 <= 999 && z) {
            try {
                if (isWritebackOnFreezeEnabled()) {
                    NandswapRecord nandswapRecord = new NandswapRecord(i, str, i2, i3);
                    NandswapMsgHandler nandswapMsgHandler = this.mMsgHandler;
                    nandswapMsgHandler.sendMessage(nandswapMsgHandler.obtainMessage(7, 0, 0, nandswapRecord));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void requestChangePPRState(NandswapRecord nandswapRecord, int i) {
        if (__SlotCount > 0) {
            NandswapMsgHandler nandswapMsgHandler = this.mMsgHandler;
            nandswapMsgHandler.sendMessage(nandswapMsgHandler.obtainMessage(1, i, 0, nandswapRecord));
        }
    }
}
