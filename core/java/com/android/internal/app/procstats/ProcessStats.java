package com.android.internal.app.procstats;

import android.content.ComponentName;
import android.hardware.scontext.SContextConstants;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Debug;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DebugUtils;
import android.util.LongSparseArray;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;

import com.android.internal.app.ProcessMap;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.util.function.QuintConsumer;

import dalvik.system.VMRuntime;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public final class ProcessStats implements Parcelable {
    public static final int ADD_PSS_EXTERNAL = 3;
    public static final int ADD_PSS_EXTERNAL_SLOW = 4;
    public static final int ADD_PSS_INTERNAL_ALL_MEM = 1;
    public static final int ADD_PSS_INTERNAL_ALL_POLL = 2;
    public static final int ADD_PSS_INTERNAL_SINGLE = 0;
    public static final int ADJ_COUNT = 8;
    public static final int ADJ_MEM_FACTOR_COUNT = 4;
    public static final int ADJ_MEM_FACTOR_CRITICAL = 3;
    public static final int ADJ_MEM_FACTOR_LOW = 2;
    public static final int ADJ_MEM_FACTOR_MODERATE = 1;
    public static final int ADJ_MEM_FACTOR_NORMAL = 0;
    public static final int ADJ_NOTHING = -1;
    public static final int ADJ_SCREEN_MOD = 4;
    public static final int ADJ_SCREEN_OFF = 0;
    public static final int ADJ_SCREEN_ON = 4;
    static final boolean DEBUG = false;
    static final boolean DEBUG_PARCEL = false;
    public static final int FLAG_COMPLETE = 1;
    public static final int FLAG_SHUTDOWN = 2;
    public static final int FLAG_SYSPROPS = 4;
    private static final long INVERSE_PROC_STATE_WARNING_MIN_INTERVAL_MS = 10000;
    private static final int MAGIC = 1347638356;
    private static final int PARCEL_VERSION = 41;
    public static final int PSS_AVERAGE = 2;
    public static final int PSS_COUNT = 10;
    public static final int PSS_MAXIMUM = 3;
    public static final int PSS_MINIMUM = 1;
    public static final int PSS_RSS_AVERAGE = 8;
    public static final int PSS_RSS_MAXIMUM = 9;
    public static final int PSS_RSS_MINIMUM = 7;
    public static final int PSS_SAMPLE_COUNT = 0;
    public static final int PSS_USS_AVERAGE = 5;
    public static final int PSS_USS_MAXIMUM = 6;
    public static final int PSS_USS_MINIMUM = 4;
    public static final int REPORT_ALL = 31;
    public static final int REPORT_PKG_ASC_STATS = 8;
    public static final int REPORT_PKG_PROC_STATS = 2;
    public static final int REPORT_PKG_STATS = 14;
    public static final int REPORT_PKG_SVC_STATS = 4;
    public static final int REPORT_PROC_STATS = 1;
    public static final int REPORT_UID_STATS = 16;
    public static final String SERVICE_NAME = "procstats";
    public static final int STATE_BACKUP = 7;
    public static final int STATE_BOUND_FGS = 4;
    public static final int STATE_BOUND_TOP = 2;
    public static final int STATE_CACHED = 14;
    public static final int STATE_COUNT = 16;
    public static final int STATE_FGS = 3;
    public static final int STATE_FROZEN = 15;
    public static final int STATE_HEAVY_WEIGHT = 11;
    public static final int STATE_HOME = 12;
    public static final int STATE_IMPORTANT_BACKGROUND = 6;
    public static final int STATE_IMPORTANT_FOREGROUND = 5;
    public static final int STATE_LAST_ACTIVITY = 13;
    public static final int STATE_NOTHING = -1;
    public static final int STATE_PERSISTENT = 0;
    public static final int STATE_RECEIVER = 10;
    public static final int STATE_SERVICE = 8;
    public static final int STATE_SERVICE_RESTARTING = 9;
    public static final int STATE_TOP = 1;
    public static final int SYS_MEM_USAGE_CACHED_AVERAGE = 2;
    public static final int SYS_MEM_USAGE_CACHED_MAXIMUM = 3;
    public static final int SYS_MEM_USAGE_CACHED_MINIMUM = 1;
    public static final int SYS_MEM_USAGE_COUNT = 16;
    public static final int SYS_MEM_USAGE_FREE_AVERAGE = 5;
    public static final int SYS_MEM_USAGE_FREE_MAXIMUM = 6;
    public static final int SYS_MEM_USAGE_FREE_MINIMUM = 4;
    public static final int SYS_MEM_USAGE_KERNEL_AVERAGE = 11;
    public static final int SYS_MEM_USAGE_KERNEL_MAXIMUM = 12;
    public static final int SYS_MEM_USAGE_KERNEL_MINIMUM = 10;
    public static final int SYS_MEM_USAGE_NATIVE_AVERAGE = 14;
    public static final int SYS_MEM_USAGE_NATIVE_MAXIMUM = 15;
    public static final int SYS_MEM_USAGE_NATIVE_MINIMUM = 13;
    public static final int SYS_MEM_USAGE_SAMPLE_COUNT = 0;
    public static final int SYS_MEM_USAGE_ZRAM_AVERAGE = 8;
    public static final int SYS_MEM_USAGE_ZRAM_MAXIMUM = 9;
    public static final int SYS_MEM_USAGE_ZRAM_MINIMUM = 7;
    public static final String TAG = "ProcessStats";
    ArrayMap<String, Integer> mCommonStringToIndex;
    public long mExternalPssCount;
    public long mExternalPssTime;
    public long mExternalSlowPssCount;
    public long mExternalSlowPssTime;
    public int mFlags;
    boolean mHasSwappedOutPss;
    ArrayList<String> mIndexToCommonString;
    public long mInternalAllMemPssCount;
    public long mInternalAllMemPssTime;
    public long mInternalAllPollPssCount;
    public long mInternalAllPollPssTime;
    public long mInternalSinglePssCount;
    public long mInternalSinglePssTime;
    public int mMemFactor;
    public final long[] mMemFactorDurations;
    private long mNextInverseProcStateWarningUptime;
    public int mNumAggregated;
    public final ProcessMap<LongSparseArray<PackageState>> mPackages;
    private final ArrayList<String> mPageTypeLabels;
    private final ArrayList<Integer> mPageTypeNodes;
    private final ArrayList<int[]> mPageTypeSizes;
    private final ArrayList<String> mPageTypeZones;
    public final ProcessMap<ProcessState> mProcesses;
    public String mReadError;
    boolean mRunning;
    String mRuntime;
    private int mSkippedInverseProcStateWarningCount;
    public long mStartTime;
    public final SysMemUsageTable mSysMemUsage;
    public final long[] mSysMemUsageArgs;
    public final SparseMappingTable mTableData;
    public long mTimePeriodEndRealtime;
    public long mTimePeriodEndUptime;
    public long mTimePeriodStartClock;
    public String mTimePeriodStartClockStr;
    public long mTimePeriodStartRealtime;
    public long mTimePeriodStartUptime;
    public final ArrayList<AssociationState.SourceState> mTrackingAssociations;
    public final SparseArray<UidState> mUidStates;
    public static long COMMIT_PERIOD = 10800000;
    public static long COMMIT_UPTIME_PERIOD = 3600000;
    public static final int[] ALL_MEM_ADJ = {0, 1, 2, 3};
    public static final int[] ALL_SCREEN_ADJ = {0, 4};
    public static final int[] NON_CACHED_PROC_STATES = {0, 1, 3, 5, 6, 7, 8, 9, 10, 11, 2, 4};
    public static final int[] BACKGROUND_PROC_STATES = {5, 6, 7, 11, 8, 9, 10};
    public static final int[] ALL_PROC_STATES = {
        0, 1, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 2, 4, 15
    };
    public static final int[] OPTIONS = {1, 2, 4, 8, 14, 16, 31};
    public static final String[] OPTIONS_STR = {
        "proc", "pkg-proc", "pkg-svc", "pkg-asc", "pkg-all", "uid", "all"
    };
    private static final Pattern sPageTypeRegex =
            Pattern.compile(
                    "^Node\\s+(\\d+),.* zone\\s+(\\w+),.* type\\s+(\\w+)\\s+([\\s\\d]+?)\\s*$");
    public static final Parcelable.Creator<ProcessStats> CREATOR =
            new Parcelable.Creator<
                    ProcessStats>() { // from class:
                                      // com.android.internal.app.procstats.ProcessStats.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ProcessStats createFromParcel(Parcel in) {
                    return new ProcessStats(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ProcessStats[] newArray(int size) {
                    return new ProcessStats[size];
                }
            };
    static final int[] BAD_TABLE = new int[0];
    static final Comparator<AssociationDumpContainer> ASSOCIATION_COMPARATOR =
            new Comparator() { // from class:
                               // com.android.internal.app.procstats.ProcessStats$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ProcessStats.lambda$static$0(
                            (ProcessStats.AssociationDumpContainer) obj,
                            (ProcessStats.AssociationDumpContainer) obj2);
                }
            };

    public ProcessStats(boolean running) {
        this.mPackages = new ProcessMap<>();
        this.mProcesses = new ProcessMap<>();
        this.mUidStates = new SparseArray<>();
        this.mTrackingAssociations = new ArrayList<>();
        this.mMemFactorDurations = new long[8];
        this.mMemFactor = -1;
        this.mNumAggregated = 1;
        this.mTableData = new SparseMappingTable();
        this.mSysMemUsageArgs = new long[16];
        this.mSysMemUsage = new SysMemUsageTable(this.mTableData);
        this.mPageTypeNodes = new ArrayList<>();
        this.mPageTypeZones = new ArrayList<>();
        this.mPageTypeLabels = new ArrayList<>();
        this.mPageTypeSizes = new ArrayList<>();
        this.mRunning = running;
        reset();
        if (running) {
            Debug.MemoryInfo info = new Debug.MemoryInfo();
            Debug.getMemoryInfo(Process.myPid(), info);
            this.mHasSwappedOutPss = info.hasSwappedOutPss();
        }
    }

    public ProcessStats(Parcel in) {
        this.mPackages = new ProcessMap<>();
        this.mProcesses = new ProcessMap<>();
        this.mUidStates = new SparseArray<>();
        this.mTrackingAssociations = new ArrayList<>();
        this.mMemFactorDurations = new long[8];
        this.mMemFactor = -1;
        this.mNumAggregated = 1;
        this.mTableData = new SparseMappingTable();
        this.mSysMemUsageArgs = new long[16];
        this.mSysMemUsage = new SysMemUsageTable(this.mTableData);
        this.mPageTypeNodes = new ArrayList<>();
        this.mPageTypeZones = new ArrayList<>();
        this.mPageTypeLabels = new ArrayList<>();
        this.mPageTypeSizes = new ArrayList<>();
        reset();
        readFromParcel(in);
    }

    public ProcessStats() {
        this(false);
    }

    public void add(ProcessStats other) {
        SparseArray<UidState> uidStates;
        ProcessState thisProc;
        int NPROCS;
        PackageState otherState;
        SparseArray<LongSparseArray<PackageState>> uids;
        int NSRVS;
        LongSparseArray<PackageState> versions;
        int NASCS;
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap;
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap2 =
                other.mPackages.getMap();
        for (int ip = 0; ip < pkgMap2.size(); ip++) {
            String pkgName = pkgMap2.keyAt(ip);
            SparseArray<LongSparseArray<PackageState>> uids2 = pkgMap2.valueAt(ip);
            for (int iu = 0; iu < uids2.size(); iu++) {
                int uid = uids2.keyAt(iu);
                LongSparseArray<PackageState> versions2 = uids2.valueAt(iu);
                int iv = 0;
                while (iv < versions2.size()) {
                    long vers = versions2.keyAt(iv);
                    PackageState otherState2 = versions2.valueAt(iv);
                    int NPROCS2 = otherState2.mProcesses.size();
                    int NSRVS2 = otherState2.mServices.size();
                    int NASCS2 = otherState2.mAssociations.size();
                    int iv2 = iv;
                    int iv3 = 0;
                    while (iv3 < NPROCS2) {
                        int NASCS3 = NASCS2;
                        ProcessState otherProc = otherState2.mProcesses.valueAt(iv3);
                        int NSRVS3 = NSRVS2;
                        if (otherProc.getCommonProcess() == otherProc) {
                            NPROCS = NPROCS2;
                            otherState = otherState2;
                            uids = uids2;
                            NSRVS = NSRVS3;
                            versions = versions2;
                            NASCS = NASCS3;
                            pkgMap = pkgMap2;
                        } else {
                            pkgMap = pkgMap2;
                            uids = uids2;
                            NSRVS = NSRVS3;
                            versions = versions2;
                            NASCS = NASCS3;
                            NPROCS = NPROCS2;
                            long vers2 = vers;
                            otherState = otherState2;
                            ProcessState thisProc2 =
                                    getProcessStateLocked(pkgName, uid, vers, otherProc.getName());
                            if (thisProc2.getCommonProcess() != thisProc2) {
                                vers = vers2;
                            } else {
                                thisProc2.setMultiPackage(true);
                                long now = SystemClock.uptimeMillis();
                                vers = vers2;
                                PackageState pkgState = getPackageStateLocked(pkgName, uid, vers);
                                thisProc2 = thisProc2.clone(now);
                                pkgState.mProcesses.put(thisProc2.getName(), thisProc2);
                            }
                            thisProc2.add(otherProc);
                        }
                        iv3++;
                        otherState2 = otherState;
                        NSRVS2 = NSRVS;
                        NASCS2 = NASCS;
                        pkgMap2 = pkgMap;
                        versions2 = versions;
                        uids2 = uids;
                        NPROCS2 = NPROCS;
                    }
                    PackageState otherState3 = otherState2;
                    ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap3 = pkgMap2;
                    SparseArray<LongSparseArray<PackageState>> uids3 = uids2;
                    LongSparseArray<PackageState> versions3 = versions2;
                    int NASCS4 = NASCS2;
                    int isvc = 0;
                    for (int NSRVS4 = NSRVS2; isvc < NSRVS4; NSRVS4 = NSRVS4) {
                        ServiceState otherSvc = otherState3.mServices.valueAt(isvc);
                        ServiceState thisSvc =
                                getServiceStateLocked(
                                        pkgName,
                                        uid,
                                        vers,
                                        otherSvc.getProcessName(),
                                        otherSvc.getName());
                        thisSvc.add(otherSvc);
                        isvc++;
                    }
                    long vers3 = vers;
                    for (int iasc = 0; iasc < NASCS4; iasc++) {
                        AssociationState otherAsc = otherState3.mAssociations.valueAt(iasc);
                        AssociationState thisAsc =
                                getAssociationStateLocked(
                                        pkgName,
                                        uid,
                                        vers3,
                                        otherAsc.getProcessName(),
                                        otherAsc.getName());
                        thisAsc.add(otherAsc);
                    }
                    iv = iv2 + 1;
                    pkgMap2 = pkgMap3;
                    versions2 = versions3;
                    uids2 = uids3;
                }
            }
        }
        SparseArray<UidState> uidStates2 = other.mUidStates;
        int size = uidStates2.size();
        for (int ip2 = 0; ip2 < size; ip2++) {
            int uid2 = uidStates2.keyAt(ip2);
            UidState uidState = this.mUidStates.get(uid2);
            if (uidState == null) {
                this.mUidStates.put(uid2, uidStates2.valueAt(ip2).m7634clone());
            } else {
                uidState.add(uidStates2.valueAt(ip2));
            }
        }
        ArrayMap<String, SparseArray<ProcessState>> procMap = other.mProcesses.getMap();
        for (int ip3 = 0; ip3 < procMap.size(); ip3++) {
            SparseArray<ProcessState> uids4 = procMap.valueAt(ip3);
            int iu2 = 0;
            while (iu2 < uids4.size()) {
                int uid3 = uids4.keyAt(iu2);
                ProcessState otherProc2 = uids4.valueAt(iu2);
                String name = otherProc2.getName();
                String pkg = otherProc2.getPackage();
                long vers4 = otherProc2.getVersion();
                ProcessState thisProc3 = this.mProcesses.get(name, uid3);
                if (thisProc3 != null) {
                    uidStates = uidStates2;
                    thisProc = thisProc3;
                } else {
                    uidStates = uidStates2;
                    thisProc = new ProcessState(this, pkg, uid3, vers4, name);
                    this.mProcesses.put(name, uid3, thisProc);
                    PackageState thisState = getPackageStateLocked(pkg, uid3, vers4);
                    if (!thisState.mProcesses.containsKey(name)) {
                        thisState.mProcesses.put(name, thisProc);
                    }
                }
                thisProc.add(otherProc2);
                UidState uidState2 = this.mUidStates.get(uid3);
                if (uidState2 == null) {
                    uidState2 = new UidState(this, uid3);
                    this.mUidStates.put(uid3, uidState2);
                }
                uidState2.addProcess(thisProc);
                iu2++;
                uidStates2 = uidStates;
            }
        }
        int size2 = this.mUidStates.size();
        for (int ip4 = 0; ip4 < size2; ip4++) {
            this.mUidStates.valueAt(ip4).updateCombinedState(-1L);
        }
        for (int i = 0; i < 8; i++) {
            long[] jArr = this.mMemFactorDurations;
            jArr[i] = jArr[i] + other.mMemFactorDurations[i];
        }
        this.mSysMemUsage.mergeStats(other.mSysMemUsage);
        this.mNumAggregated += other.mNumAggregated;
        if (other.mTimePeriodStartClock < this.mTimePeriodStartClock) {
            this.mTimePeriodStartClock = other.mTimePeriodStartClock;
            this.mTimePeriodStartClockStr = other.mTimePeriodStartClockStr;
        }
        this.mTimePeriodEndRealtime +=
                other.mTimePeriodEndRealtime - other.mTimePeriodStartRealtime;
        this.mTimePeriodEndUptime += other.mTimePeriodEndUptime - other.mTimePeriodStartUptime;
        this.mInternalSinglePssCount += other.mInternalSinglePssCount;
        this.mInternalSinglePssTime += other.mInternalSinglePssTime;
        this.mInternalAllMemPssCount += other.mInternalAllMemPssCount;
        this.mInternalAllMemPssTime += other.mInternalAllMemPssTime;
        this.mInternalAllPollPssCount += other.mInternalAllPollPssCount;
        this.mInternalAllPollPssTime += other.mInternalAllPollPssTime;
        this.mExternalPssCount += other.mExternalPssCount;
        this.mExternalPssTime += other.mExternalPssTime;
        this.mExternalSlowPssCount += other.mExternalSlowPssCount;
        this.mExternalSlowPssTime += other.mExternalSlowPssTime;
        this.mHasSwappedOutPss |= other.mHasSwappedOutPss;
    }

    public void addSysMemUsage(
            long cachedMem, long freeMem, long zramMem, long kernelMem, long nativeMem) {
        if (this.mMemFactor != -1) {
            int state = this.mMemFactor * 16;
            this.mSysMemUsageArgs[0] = 1;
            for (int i = 0; i < 3; i++) {
                this.mSysMemUsageArgs[i + 1] = cachedMem;
                this.mSysMemUsageArgs[i + 4] = freeMem;
                this.mSysMemUsageArgs[i + 7] = zramMem;
                this.mSysMemUsageArgs[i + 10] = kernelMem;
                this.mSysMemUsageArgs[i + 13] = nativeMem;
            }
            this.mSysMemUsage.mergeStats(state, this.mSysMemUsageArgs, 0);
        }
    }

    public void computeTotalMemoryUse(TotalMemoryUseCollection data, long now) {
        int i;
        long[] totalMemUsage;
        long j = now;
        data.totalTime = 0L;
        int i2 = 0;
        while (true) {
            i = 0;
            if (i2 >= 16) {
                break;
            }
            data.processStateWeight[i2] = 0.0d;
            data.processStatePss[i2] = 0;
            data.processStateTime[i2] = 0;
            data.processStateSamples[i2] = 0;
            i2++;
        }
        for (int i3 = 0; i3 < 16; i3++) {
            data.sysMemUsage[i3] = 0;
        }
        data.sysMemCachedWeight = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        data.sysMemFreeWeight = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        data.sysMemZRamWeight = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        data.sysMemKernelWeight = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        data.sysMemNativeWeight = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        data.sysMemSamples = 0;
        long[] totalMemUsage2 = this.mSysMemUsage.getTotalMemUsage();
        int is = 0;
        while (is < data.screenStates.length) {
            int im = 0;
            while (im < data.memStates.length) {
                int memBucket = data.screenStates[is] + data.memStates[im];
                int stateBucket = memBucket * 16;
                long memTime = this.mMemFactorDurations[memBucket];
                if (this.mMemFactor == memBucket) {
                    memTime += j - this.mStartTime;
                }
                data.totalTime += memTime;
                int sysKey = this.mSysMemUsage.getKey((byte) stateBucket);
                long[] longs = totalMemUsage2;
                int idx = 0;
                if (sysKey == -1) {
                    totalMemUsage = totalMemUsage2;
                } else {
                    long[] tmpLongs = this.mSysMemUsage.getArrayForKey(sysKey);
                    int tmpIndex = SparseMappingTable.getIndexFromKey(sysKey);
                    if (tmpLongs[tmpIndex + 0] < 3) {
                        totalMemUsage = totalMemUsage2;
                    } else {
                        totalMemUsage = totalMemUsage2;
                        long[] totalMemUsage3 = data.sysMemUsage;
                        SysMemUsageTable.mergeSysMemUsage(totalMemUsage3, i, longs, 0);
                        longs = tmpLongs;
                        idx = tmpIndex;
                    }
                }
                data.sysMemCachedWeight += longs[idx + 2] * memTime;
                data.sysMemFreeWeight += longs[idx + 5] * memTime;
                data.sysMemZRamWeight += longs[idx + 8] * memTime;
                data.sysMemKernelWeight += longs[idx + 11] * memTime;
                data.sysMemNativeWeight += longs[idx + 14] * memTime;
                data.sysMemSamples = (int) (data.sysMemSamples + longs[idx + 0]);
                im++;
                j = now;
                totalMemUsage2 = totalMemUsage;
                is = is;
                i = 0;
            }
            is++;
            j = now;
            i = 0;
        }
        data.hasSwappedOutPss = this.mHasSwappedOutPss;
        ArrayMap<String, SparseArray<ProcessState>> procMap = this.mProcesses.getMap();
        for (int iproc = 0; iproc < procMap.size(); iproc++) {
            SparseArray<ProcessState> uids = procMap.valueAt(iproc);
            for (int iu = 0; iu < uids.size(); iu++) {
                ProcessState proc = uids.valueAt(iu);
                proc.aggregatePss(data, now);
            }
        }
    }

    public void reset() {
        resetCommon();
        this.mPackages.getMap().clear();
        this.mProcesses.getMap().clear();
        this.mUidStates.clear();
        this.mMemFactor = -1;
        this.mStartTime = 0L;
    }

    public void resetSafely() {
        resetCommon();
        long now = SystemClock.uptimeMillis();
        ArrayMap<String, SparseArray<ProcessState>> procMap = this.mProcesses.getMap();
        for (int ip = procMap.size() - 1; ip >= 0; ip--) {
            SparseArray<ProcessState> uids = procMap.valueAt(ip);
            for (int iu = uids.size() - 1; iu >= 0; iu--) {
                uids.valueAt(iu).tmpNumInUse = 0;
            }
        }
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap =
                this.mPackages.getMap();
        for (int ip2 = pkgMap.size() - 1; ip2 >= 0; ip2--) {
            SparseArray<LongSparseArray<PackageState>> uids2 = pkgMap.valueAt(ip2);
            for (int iu2 = uids2.size() - 1; iu2 >= 0; iu2--) {
                LongSparseArray<PackageState> vpkgs = uids2.valueAt(iu2);
                for (int iv = vpkgs.size() - 1; iv >= 0; iv--) {
                    PackageState pkgState = vpkgs.valueAt(iv);
                    for (int iproc = pkgState.mProcesses.size() - 1; iproc >= 0; iproc--) {
                        ProcessState ps = pkgState.mProcesses.valueAt(iproc);
                        if (ps.isInUse()) {
                            ps.resetSafely(now);
                            ps.getCommonProcess().tmpNumInUse++;
                            ps.getCommonProcess().tmpFoundSubProc = ps;
                        } else {
                            pkgState.mProcesses.valueAt(iproc).makeDead();
                            pkgState.mProcesses.removeAt(iproc);
                        }
                    }
                    for (int isvc = pkgState.mServices.size() - 1; isvc >= 0; isvc--) {
                        ServiceState ss = pkgState.mServices.valueAt(isvc);
                        if (ss.isInUse()) {
                            ss.resetSafely(now);
                        } else {
                            pkgState.mServices.removeAt(isvc);
                        }
                    }
                    for (int iasc = pkgState.mAssociations.size() - 1; iasc >= 0; iasc--) {
                        AssociationState as = pkgState.mAssociations.valueAt(iasc);
                        if (as.isInUse()) {
                            as.resetSafely(now);
                        } else {
                            pkgState.mAssociations.removeAt(iasc);
                        }
                    }
                    if (pkgState.mProcesses.size() <= 0
                            && pkgState.mServices.size() <= 0
                            && pkgState.mAssociations.size() <= 0) {
                        vpkgs.removeAt(iv);
                    }
                }
                int iv2 = vpkgs.size();
                if (iv2 <= 0) {
                    uids2.removeAt(iu2);
                }
            }
            int iu3 = uids2.size();
            if (iu3 <= 0) {
                pkgMap.removeAt(ip2);
            }
        }
        int ip3 = procMap.size();
        for (int ip4 = ip3 - 1; ip4 >= 0; ip4--) {
            SparseArray<ProcessState> uids3 = procMap.valueAt(ip4);
            for (int iu4 = uids3.size() - 1; iu4 >= 0; iu4--) {
                ProcessState ps2 = uids3.valueAt(iu4);
                if (ps2.isInUse() || ps2.tmpNumInUse > 0) {
                    if (!ps2.isActive() && ps2.isMultiPackage() && ps2.tmpNumInUse == 1) {
                        ProcessState ps3 = ps2.tmpFoundSubProc;
                        ps3.makeStandalone();
                        uids3.setValueAt(iu4, ps3);
                    } else {
                        ps2.resetSafely(now);
                    }
                } else {
                    ps2.makeDead();
                    uids3.removeAt(iu4);
                }
            }
            int iu5 = uids3.size();
            if (iu5 <= 0) {
                procMap.removeAt(ip4);
            }
        }
        for (int ip5 = this.mUidStates.size() - 1; ip5 >= 0; ip5--) {
            UidState uidState = this.mUidStates.valueAt(ip5);
            if (uidState.isInUse()) {
                this.mUidStates.valueAt(ip5).resetSafely(now);
            } else {
                this.mUidStates.removeAt(ip5);
            }
        }
        this.mStartTime = now;
    }

    private void resetCommon() {
        this.mNumAggregated = 1;
        this.mTimePeriodStartClock = System.currentTimeMillis();
        buildTimePeriodStartClockStr();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mTimePeriodEndRealtime = elapsedRealtime;
        this.mTimePeriodStartRealtime = elapsedRealtime;
        long uptimeMillis = SystemClock.uptimeMillis();
        this.mTimePeriodEndUptime = uptimeMillis;
        this.mTimePeriodStartUptime = uptimeMillis;
        this.mInternalSinglePssCount = 0L;
        this.mInternalSinglePssTime = 0L;
        this.mInternalAllMemPssCount = 0L;
        this.mInternalAllMemPssTime = 0L;
        this.mInternalAllPollPssCount = 0L;
        this.mInternalAllPollPssTime = 0L;
        this.mExternalPssCount = 0L;
        this.mExternalPssTime = 0L;
        this.mExternalSlowPssCount = 0L;
        this.mExternalSlowPssTime = 0L;
        this.mTableData.reset();
        Arrays.fill(this.mMemFactorDurations, 0L);
        this.mSysMemUsage.resetTable();
        this.mStartTime = 0L;
        this.mReadError = null;
        this.mFlags = 0;
        evaluateSystemProperties(true);
        updateFragmentation();
    }

    public boolean evaluateSystemProperties(boolean update) {
        boolean changed = false;
        String runtime =
                SystemProperties.get(
                        "persist.sys.dalvik.vm.lib.2", VMRuntime.getRuntime().vmLibrary());
        if (!Objects.equals(runtime, this.mRuntime)) {
            changed = true;
            if (update) {
                this.mRuntime = runtime;
            }
        }
        return changed;
    }

    private void buildTimePeriodStartClockStr() {
        this.mTimePeriodStartClockStr =
                DateFormat.format("yyyy-MM-dd-HH-mm-ss", this.mTimePeriodStartClock).toString();
    }

    public void updateFragmentation() {
        Integer node;
        BufferedReader reader = null;
        try {
            try {
                reader = new BufferedReader(new FileReader("/proc/pagetypeinfo"));
                Matcher matcher = sPageTypeRegex.matcher("");
                this.mPageTypeNodes.clear();
                this.mPageTypeZones.clear();
                this.mPageTypeLabels.clear();
                this.mPageTypeSizes.clear();
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        try {
                            reader.close();
                            return;
                        } catch (IOException e) {
                            return;
                        }
                    }
                    matcher.reset(line);
                    if (matcher.matches()
                            && (node = Integer.valueOf(matcher.group(1), 10)) != null) {
                        this.mPageTypeNodes.add(node);
                        this.mPageTypeZones.add(matcher.group(2));
                        this.mPageTypeLabels.add(matcher.group(3));
                        this.mPageTypeSizes.add(splitAndParseNumbers(matcher.group(4)));
                    }
                }
            } catch (Throwable th) {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e2) {
                    }
                }
                throw th;
            }
        } catch (IOException e3) {
            this.mPageTypeNodes.clear();
            this.mPageTypeZones.clear();
            this.mPageTypeLabels.clear();
            this.mPageTypeSizes.clear();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e4) {
                }
            }
        }
    }

    private static int[] splitAndParseNumbers(String s) {
        boolean digit = false;
        int count = 0;
        int N = s.length();
        for (int i = 0; i < N; i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                if (!digit) {
                    digit = true;
                    count++;
                }
            } else {
                digit = false;
            }
        }
        int[] result = new int[count];
        int p = 0;
        int val = 0;
        for (int i2 = 0; i2 < N; i2++) {
            char c2 = s.charAt(i2);
            if (c2 >= '0' && c2 <= '9') {
                if (!digit) {
                    digit = true;
                    val = c2 - '0';
                } else {
                    val = (val * 10) + (c2 - '0');
                }
            } else if (digit) {
                digit = false;
                result[p] = val;
                p++;
            }
        }
        if (count > 0) {
            result[count - 1] = val;
        }
        return result;
    }

    private void writeCompactedLongArray(Parcel out, long[] array, int num) {
        for (int i = 0; i < num; i++) {
            long val = array[i];
            if (val < 0) {
                Slog.w(TAG, "Time val negative: " + val);
                val = 0;
            }
            if (val > 2147483647L) {
                int top = ~((int) (2147483647L & (val >> 32)));
                int bottom = (int) (4294967295L & val);
                out.writeInt(top);
                out.writeInt(bottom);
            } else {
                out.writeInt((int) val);
            }
        }
    }

    private void readCompactedLongArray(Parcel in, int version, long[] array, int num) {
        if (version <= 10) {
            in.readLongArray(array);
            return;
        }
        int alen = array.length;
        if (num > alen) {
            throw new RuntimeException("bad array lengths: got " + num + " array is " + alen);
        }
        int i = 0;
        while (i < num) {
            int val = in.readInt();
            if (val >= 0) {
                array[i] = val;
            } else {
                int bottom = in.readInt();
                array[i] = ((~val) << 32) | bottom;
            }
            i++;
        }
        while (i < alen) {
            array[i] = 0;
            i++;
        }
    }

    void writeCommonString(Parcel out, String name) {
        Integer index = this.mCommonStringToIndex.get(name);
        if (index != null) {
            out.writeInt(index.intValue());
            return;
        }
        Integer index2 = Integer.valueOf(this.mCommonStringToIndex.size());
        this.mCommonStringToIndex.put(name, index2);
        out.writeInt(~index2.intValue());
        out.writeString(name);
    }

    String readCommonString(Parcel in, int version) {
        if (version <= 9) {
            return in.readString();
        }
        int index = in.readInt();
        if (index >= 0) {
            return this.mIndexToCommonString.get(index);
        }
        int index2 = ~index;
        String name = in.readString();
        while (this.mIndexToCommonString.size() <= index2) {
            this.mIndexToCommonString.add(null);
        }
        this.mIndexToCommonString.set(index2, name);
        return name;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        writeToParcel(out, SystemClock.uptimeMillis(), flags);
    }

    public void writeToParcel(Parcel parcel, long j, int i) {
        parcel.writeInt(MAGIC);
        parcel.writeInt(41);
        parcel.writeInt(16);
        parcel.writeInt(8);
        parcel.writeInt(10);
        parcel.writeInt(16);
        parcel.writeInt(4096);
        this.mCommonStringToIndex = new ArrayMap<>(this.mProcesses.size());
        ArrayMap<String, SparseArray<ProcessState>> map = this.mProcesses.getMap();
        int size = map.size();
        for (int i2 = 0; i2 < size; i2++) {
            SparseArray<ProcessState> valueAt = map.valueAt(i2);
            int size2 = valueAt.size();
            for (int i3 = 0; i3 < size2; i3++) {
                valueAt.valueAt(i3).commitStateTime(j);
            }
        }
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> map2 = this.mPackages.getMap();
        int size3 = map2.size();
        for (int i4 = 0; i4 < size3; i4++) {
            SparseArray<LongSparseArray<PackageState>> valueAt2 = map2.valueAt(i4);
            int size4 = valueAt2.size();
            for (int i5 = 0; i5 < size4; i5++) {
                LongSparseArray<PackageState> valueAt3 = valueAt2.valueAt(i5);
                int size5 = valueAt3.size();
                int i6 = 0;
                while (i6 < size5) {
                    PackageState valueAt4 = valueAt3.valueAt(i6);
                    SparseArray<LongSparseArray<PackageState>> sparseArray = valueAt2;
                    int size6 = valueAt4.mProcesses.size();
                    int i7 = size4;
                    int i8 = 0;
                    while (i8 < size6) {
                        int i9 = size6;
                        ProcessState valueAt5 = valueAt4.mProcesses.valueAt(i8);
                        LongSparseArray<PackageState> longSparseArray = valueAt3;
                        if (valueAt5.getCommonProcess() != valueAt5) {
                            valueAt5.commitStateTime(j);
                        }
                        i8++;
                        size6 = i9;
                        valueAt3 = longSparseArray;
                    }
                    LongSparseArray<PackageState> longSparseArray2 = valueAt3;
                    int size7 = valueAt4.mServices.size();
                    for (int i10 = 0; i10 < size7; i10++) {
                        valueAt4.mServices.valueAt(i10).commitStateTime(j);
                    }
                    int size8 = valueAt4.mAssociations.size();
                    int i11 = 0;
                    while (i11 < size8) {
                        valueAt4.mAssociations.valueAt(i11).commitStateTime(j);
                        i11++;
                        size7 = size7;
                    }
                    i6++;
                    valueAt2 = sparseArray;
                    size4 = i7;
                    valueAt3 = longSparseArray2;
                }
            }
        }
        parcel.writeInt(this.mNumAggregated);
        parcel.writeLong(this.mTimePeriodStartClock);
        parcel.writeLong(this.mTimePeriodStartRealtime);
        parcel.writeLong(this.mTimePeriodEndRealtime);
        parcel.writeLong(this.mTimePeriodStartUptime);
        parcel.writeLong(this.mTimePeriodEndUptime);
        parcel.writeLong(this.mInternalSinglePssCount);
        parcel.writeLong(this.mInternalSinglePssTime);
        parcel.writeLong(this.mInternalAllMemPssCount);
        parcel.writeLong(this.mInternalAllMemPssTime);
        parcel.writeLong(this.mInternalAllPollPssCount);
        parcel.writeLong(this.mInternalAllPollPssTime);
        parcel.writeLong(this.mExternalPssCount);
        parcel.writeLong(this.mExternalPssTime);
        parcel.writeLong(this.mExternalSlowPssCount);
        parcel.writeLong(this.mExternalSlowPssTime);
        parcel.writeString(this.mRuntime);
        parcel.writeInt(this.mHasSwappedOutPss ? 1 : 0);
        parcel.writeInt(this.mFlags);
        this.mTableData.writeToParcel(parcel);
        if (this.mMemFactor != -1) {
            long[] jArr = this.mMemFactorDurations;
            int i12 = this.mMemFactor;
            jArr[i12] = jArr[i12] + (j - this.mStartTime);
            this.mStartTime = j;
        }
        writeCompactedLongArray(parcel, this.mMemFactorDurations, this.mMemFactorDurations.length);
        this.mSysMemUsage.writeToParcel(parcel);
        int size9 = this.mUidStates.size();
        parcel.writeInt(size9);
        for (int i13 = 0; i13 < size9; i13++) {
            parcel.writeInt(this.mUidStates.keyAt(i13));
            this.mUidStates.valueAt(i13).writeToParcel(parcel, j);
        }
        parcel.writeInt(size);
        for (int i14 = 0; i14 < size; i14++) {
            writeCommonString(parcel, map.keyAt(i14));
            SparseArray<ProcessState> valueAt6 = map.valueAt(i14);
            int size10 = valueAt6.size();
            parcel.writeInt(size10);
            for (int i15 = 0; i15 < size10; i15++) {
                parcel.writeInt(valueAt6.keyAt(i15));
                ProcessState valueAt7 = valueAt6.valueAt(i15);
                writeCommonString(parcel, valueAt7.getPackage());
                parcel.writeLong(valueAt7.getVersion());
                valueAt7.writeToParcel(parcel, j);
            }
        }
        parcel.writeInt(size3);
        for (int i16 = 0; i16 < size3; i16++) {
            writeCommonString(parcel, map2.keyAt(i16));
            SparseArray<LongSparseArray<PackageState>> valueAt8 = map2.valueAt(i16);
            int size11 = valueAt8.size();
            parcel.writeInt(size11);
            for (int i17 = 0; i17 < size11; i17++) {
                parcel.writeInt(valueAt8.keyAt(i17));
                LongSparseArray<PackageState> valueAt9 = valueAt8.valueAt(i17);
                int size12 = valueAt9.size();
                parcel.writeInt(size12);
                int i18 = 0;
                while (i18 < size12) {
                    ArrayMap<String, SparseArray<ProcessState>> arrayMap = map;
                    int i19 = size;
                    parcel.writeLong(valueAt9.keyAt(i18));
                    PackageState valueAt10 = valueAt9.valueAt(i18);
                    int size13 = valueAt10.mProcesses.size();
                    parcel.writeInt(size13);
                    ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> arrayMap2 = map2;
                    int i20 = 0;
                    while (i20 < size13) {
                        int i21 = size13;
                        writeCommonString(parcel, valueAt10.mProcesses.keyAt(i20));
                        ProcessState valueAt11 = valueAt10.mProcesses.valueAt(i20);
                        int i22 = size3;
                        if (valueAt11.getCommonProcess() == valueAt11) {
                            parcel.writeInt(0);
                        } else {
                            parcel.writeInt(1);
                            valueAt11.writeToParcel(parcel, j);
                        }
                        i20++;
                        size13 = i21;
                        size3 = i22;
                    }
                    int i23 = size3;
                    int size14 = valueAt10.mServices.size();
                    parcel.writeInt(size14);
                    int i24 = 0;
                    while (i24 < size14) {
                        parcel.writeString(valueAt10.mServices.keyAt(i24));
                        ServiceState valueAt12 = valueAt10.mServices.valueAt(i24);
                        writeCommonString(parcel, valueAt12.getProcessName());
                        valueAt12.writeToParcel(parcel, j);
                        i24++;
                        size14 = size14;
                    }
                    int size15 = valueAt10.mAssociations.size();
                    parcel.writeInt(size15);
                    int i25 = 0;
                    while (i25 < size15) {
                        writeCommonString(parcel, valueAt10.mAssociations.keyAt(i25));
                        AssociationState valueAt13 = valueAt10.mAssociations.valueAt(i25);
                        writeCommonString(parcel, valueAt13.getProcessName());
                        valueAt13.writeToParcel(this, parcel, j);
                        i25++;
                        valueAt10 = valueAt10;
                    }
                    i18++;
                    map = arrayMap;
                    size = i19;
                    map2 = arrayMap2;
                    size3 = i23;
                }
            }
        }
        int size16 = this.mPageTypeLabels.size();
        parcel.writeInt(size16);
        for (int i26 = 0; i26 < size16; i26++) {
            parcel.writeInt(this.mPageTypeNodes.get(i26).intValue());
            parcel.writeString(this.mPageTypeZones.get(i26));
            parcel.writeString(this.mPageTypeLabels.get(i26));
            parcel.writeIntArray(this.mPageTypeSizes.get(i26));
        }
        this.mCommonStringToIndex = null;
    }

    private boolean readCheckedInt(Parcel in, int val, String what) {
        int got = in.readInt();
        if (got != val) {
            this.mReadError = "bad " + what + ": " + got;
            return false;
        }
        return true;
    }

    static byte[] readFully(InputStream stream, int[] outLen) throws IOException {
        int pos = 0;
        int initialAvail = stream.available();
        byte[] data = new byte[initialAvail > 0 ? initialAvail + 1 : 16384];
        while (true) {
            int amt = stream.read(data, pos, data.length - pos);
            if (amt < 0) {
                outLen[0] = pos;
                return data;
            }
            pos += amt;
            if (pos >= data.length) {
                byte[] newData = new byte[pos + 16384];
                System.arraycopy(data, 0, newData, 0, pos);
                data = newData;
            }
        }
    }

    public void read(InputStream stream) {
        try {
            int[] len = new int[1];
            byte[] raw = readFully(stream, len);
            Parcel in = Parcel.obtain();
            in.unmarshall(raw, 0, len[0]);
            in.setDataPosition(0);
            stream.close();
            readFromParcel(in);
        } catch (IOException e) {
            this.mReadError = "caught exception: " + e;
        }
    }

    public void readFromParcel(Parcel in) {
        LongSparseArray<PackageState> vpkg;
        int NSRVS;
        String associationName;
        AssociationState asc;
        long vers;
        String serviceName;
        int uid;
        PackageState pkgState;
        ServiceState serv;
        int NPKG;
        int uid2;
        String procName;
        boolean z = false;
        boolean hadData =
                this.mPackages.getMap().size() > 0
                        || this.mProcesses.getMap().size() > 0
                        || this.mUidStates.size() > 0;
        if (hadData) {
            resetSafely();
        }
        if (!readCheckedInt(in, MAGIC, "magic number")) {
            return;
        }
        int version = in.readInt();
        if (version != 41) {
            this.mReadError = "bad version: " + version;
            return;
        }
        if (readCheckedInt(in, 16, "state count")
                && readCheckedInt(in, 8, "adj count")
                && readCheckedInt(in, 10, "pss count")
                && readCheckedInt(in, 16, "sys mem usage count")
                && readCheckedInt(in, 4096, "longs size")) {
            this.mIndexToCommonString = new ArrayList<>();
            this.mNumAggregated = in.readInt();
            this.mTimePeriodStartClock = in.readLong();
            buildTimePeriodStartClockStr();
            this.mTimePeriodStartRealtime = in.readLong();
            this.mTimePeriodEndRealtime = in.readLong();
            this.mTimePeriodStartUptime = in.readLong();
            this.mTimePeriodEndUptime = in.readLong();
            this.mInternalSinglePssCount = in.readLong();
            this.mInternalSinglePssTime = in.readLong();
            this.mInternalAllMemPssCount = in.readLong();
            this.mInternalAllMemPssTime = in.readLong();
            this.mInternalAllPollPssCount = in.readLong();
            this.mInternalAllPollPssTime = in.readLong();
            this.mExternalPssCount = in.readLong();
            this.mExternalPssTime = in.readLong();
            this.mExternalSlowPssCount = in.readLong();
            this.mExternalSlowPssTime = in.readLong();
            this.mRuntime = in.readString();
            this.mHasSwappedOutPss = in.readInt() != 0;
            this.mFlags = in.readInt();
            this.mTableData.readFromParcel(in);
            readCompactedLongArray(
                    in, version, this.mMemFactorDurations, this.mMemFactorDurations.length);
            if (!this.mSysMemUsage.readFromParcel(in)) {
                return;
            }
            int numOfUids = in.readInt();
            for (int ip = 0; ip < numOfUids; ip++) {
                int uid3 = in.readInt();
                UidState uidState = new UidState(this, uid3);
                if (!uidState.readFromParcel(in)) {
                    return;
                }
                this.mUidStates.put(uid3, uidState);
            }
            int NPROC = in.readInt();
            if (NPROC < 0) {
                this.mReadError = "bad process count: " + NPROC;
                return;
            }
            int NPROC2 = NPROC;
            while (true) {
                ProcessState processState = null;
                if (NPROC2 > 0) {
                    int NPROC3 = NPROC2 - 1;
                    String procName2 = readCommonString(in, version);
                    if (procName2 == null) {
                        this.mReadError = "bad process name";
                        return;
                    }
                    int NUID = in.readInt();
                    if (NUID < 0) {
                        this.mReadError = "bad uid count: " + NUID;
                        return;
                    }
                    while (NUID > 0) {
                        int NUID2 = NUID - 1;
                        int uid4 = in.readInt();
                        if (uid4 < 0) {
                            this.mReadError = "bad uid: " + uid4;
                            return;
                        }
                        String pkgName = readCommonString(in, version);
                        if (pkgName == null) {
                            this.mReadError = "bad process package name";
                            return;
                        }
                        long vers2 = in.readLong();
                        ProcessState proc =
                                hadData ? this.mProcesses.get(procName2, uid4) : processState;
                        if (proc != null) {
                            if (proc.readFromParcel(in, version, z)) {
                                uid2 = uid4;
                                procName = procName2;
                            } else {
                                return;
                            }
                        } else {
                            uid2 = uid4;
                            procName = procName2;
                            proc = new ProcessState(this, pkgName, uid4, vers2, procName2);
                            if (!proc.readFromParcel(in, version, true)) {
                                return;
                            }
                        }
                        String procName3 = procName;
                        this.mProcesses.put(procName3, uid2, proc);
                        UidState uidState2 = this.mUidStates.get(uid2);
                        if (uidState2 == null) {
                            uidState2 = new UidState(this, uid2);
                            this.mUidStates.put(uid2, uidState2);
                        }
                        uidState2.addProcess(proc);
                        procName2 = procName3;
                        NUID = NUID2;
                        processState = null;
                        z = false;
                    }
                    NPROC2 = NPROC3;
                    z = false;
                } else {
                    for (int ip2 = 0; ip2 < numOfUids; ip2++) {
                        this.mUidStates.valueAt(ip2).updateCombinedState(-1L);
                    }
                    int NUID3 = in.readInt();
                    if (NUID3 < 0) {
                        this.mReadError = "bad package count: " + NUID3;
                        return;
                    }
                    while (NUID3 > 0) {
                        int NPKG2 = NUID3 - 1;
                        String pkgName2 = readCommonString(in, version);
                        if (pkgName2 == null) {
                            this.mReadError = "bad package name";
                            return;
                        }
                        int NVERS = in.readInt();
                        if (NVERS < 0) {
                            this.mReadError = "bad uid count: " + NVERS;
                            return;
                        }
                        while (NVERS > 0) {
                            int NUID4 = NVERS - 1;
                            int uid5 = in.readInt();
                            if (uid5 < 0) {
                                this.mReadError = "bad uid: " + uid5;
                                return;
                            }
                            int NVERS2 = in.readInt();
                            if (NVERS2 < 0) {
                                this.mReadError = "bad versions count: " + NVERS2;
                                return;
                            }
                            while (NVERS2 > 0) {
                                int NVERS3 = NVERS2 - 1;
                                long vers3 = in.readLong();
                                String pkgName3 = pkgName2;
                                int NPROC4 = NPROC2;
                                int uid6 = uid5;
                                PackageState pkgState2 =
                                        new PackageState(this, pkgName2, uid5, vers3);
                                LongSparseArray<PackageState> vpkg2 =
                                        this.mPackages.get(pkgName3, uid6);
                                if (vpkg2 != null) {
                                    vpkg = vpkg2;
                                } else {
                                    LongSparseArray<PackageState> vpkg3 = new LongSparseArray<>();
                                    this.mPackages.put(pkgName3, uid6, vpkg3);
                                    vpkg = vpkg3;
                                }
                                long vers4 = vers3;
                                vpkg.put(vers4, pkgState2);
                                int NPROCS = in.readInt();
                                if (NPROCS < 0) {
                                    this.mReadError = "bad package process count: " + NPROCS;
                                    return;
                                }
                                int NPROCS2 = NPROCS;
                                while (NPROCS2 > 0) {
                                    NPROCS2--;
                                    String procName4 = readCommonString(in, version);
                                    if (procName4 == null) {
                                        this.mReadError = "bad package process name";
                                        return;
                                    }
                                    int hasProc = in.readInt();
                                    LongSparseArray<PackageState> vpkg4 = vpkg;
                                    ProcessState commonProc = this.mProcesses.get(procName4, uid6);
                                    if (commonProc == null) {
                                        this.mReadError = "no common proc: " + procName4;
                                        return;
                                    }
                                    if (hasProc != 0) {
                                        ProcessState proc2 =
                                                hadData
                                                        ? pkgState2.mProcesses.get(procName4)
                                                        : null;
                                        if (proc2 != null) {
                                            NPKG = NPKG2;
                                            if (!proc2.readFromParcel(in, version, false)) {
                                                return;
                                            }
                                        } else {
                                            NPKG = NPKG2;
                                            proc2 =
                                                    new ProcessState(
                                                            commonProc,
                                                            pkgName3,
                                                            uid6,
                                                            vers4,
                                                            procName4,
                                                            0L);
                                            if (!proc2.readFromParcel(in, version, true)) {
                                                return;
                                            }
                                        }
                                        pkgState2.mProcesses.put(procName4, proc2);
                                    } else {
                                        NPKG = NPKG2;
                                        pkgState2.mProcesses.put(procName4, commonProc);
                                    }
                                    vpkg = vpkg4;
                                    NPKG2 = NPKG;
                                }
                                int NPKG3 = NPKG2;
                                int NSRVS2 = in.readInt();
                                if (NSRVS2 < 0) {
                                    this.mReadError = "bad package service count: " + NSRVS2;
                                    return;
                                }
                                int NSRVS3 = NSRVS2;
                                while (NSRVS3 > 0) {
                                    NSRVS3--;
                                    String serviceName2 = in.readString();
                                    if (serviceName2 == null) {
                                        this.mReadError = "bad package service name";
                                        return;
                                    }
                                    String processName =
                                            version > 9 ? readCommonString(in, version) : null;
                                    ServiceState serv2 =
                                            hadData ? pkgState2.mServices.get(serviceName2) : null;
                                    if (serv2 != null) {
                                        vers = vers4;
                                        serviceName = serviceName2;
                                        uid = uid6;
                                        pkgState = pkgState2;
                                        serv = serv2;
                                    } else {
                                        vers = vers4;
                                        serviceName = serviceName2;
                                        uid = uid6;
                                        pkgState = pkgState2;
                                        serv =
                                                new ServiceState(
                                                        this,
                                                        pkgName3,
                                                        serviceName2,
                                                        processName,
                                                        null);
                                    }
                                    if (!serv.readFromParcel(in)) {
                                        return;
                                    }
                                    pkgState.mServices.put(serviceName, serv);
                                    pkgState2 = pkgState;
                                    vers4 = vers;
                                    uid6 = uid;
                                }
                                int uid7 = uid6;
                                PackageState pkgState3 = pkgState2;
                                int NASCS = in.readInt();
                                if (NASCS < 0) {
                                    this.mReadError = "bad package association count: " + NASCS;
                                    return;
                                }
                                while (NASCS > 0) {
                                    int NASCS2 = NASCS - 1;
                                    String associationName2 = readCommonString(in, version);
                                    if (associationName2 == null) {
                                        this.mReadError = "bad package association name";
                                        return;
                                    }
                                    String processName2 = readCommonString(in, version);
                                    AssociationState asc2 =
                                            hadData
                                                    ? pkgState3.mAssociations.get(associationName2)
                                                    : null;
                                    if (asc2 != null) {
                                        NSRVS = NSRVS3;
                                        associationName = associationName2;
                                        asc = asc2;
                                    } else {
                                        NSRVS = NSRVS3;
                                        associationName = associationName2;
                                        asc =
                                                new AssociationState(
                                                        this,
                                                        pkgState3,
                                                        associationName2,
                                                        processName2,
                                                        null);
                                    }
                                    String errorMsg = asc.readFromParcel(this, in, version);
                                    if (errorMsg != null) {
                                        this.mReadError = errorMsg;
                                        return;
                                    } else {
                                        pkgState3.mAssociations.put(associationName, asc);
                                        NASCS = NASCS2;
                                        NSRVS3 = NSRVS;
                                    }
                                }
                                pkgName2 = pkgName3;
                                NVERS2 = NVERS3;
                                uid5 = uid7;
                                NPROC2 = NPROC4;
                                NPKG2 = NPKG3;
                            }
                            NVERS = NUID4;
                        }
                        NUID3 = NPKG2;
                    }
                    int NPAGETYPES = in.readInt();
                    this.mPageTypeNodes.clear();
                    this.mPageTypeNodes.ensureCapacity(NPAGETYPES);
                    this.mPageTypeZones.clear();
                    this.mPageTypeZones.ensureCapacity(NPAGETYPES);
                    this.mPageTypeLabels.clear();
                    this.mPageTypeLabels.ensureCapacity(NPAGETYPES);
                    this.mPageTypeSizes.clear();
                    this.mPageTypeSizes.ensureCapacity(NPAGETYPES);
                    for (int i = 0; i < NPAGETYPES; i++) {
                        this.mPageTypeNodes.add(Integer.valueOf(in.readInt()));
                        this.mPageTypeZones.add(in.readString());
                        this.mPageTypeLabels.add(in.readString());
                        this.mPageTypeSizes.add(in.createIntArray());
                    }
                    this.mIndexToCommonString = null;
                    return;
                }
            }
        }
    }

    public PackageState getPackageStateLocked(String packageName, int uid, long vers) {
        LongSparseArray<PackageState> vpkg = this.mPackages.get(packageName, uid);
        if (vpkg == null) {
            vpkg = new LongSparseArray<>();
            this.mPackages.put(packageName, uid, vpkg);
        }
        PackageState as = vpkg.get(vers);
        if (as != null) {
            return as;
        }
        PackageState as2 = new PackageState(this, packageName, uid, vers);
        vpkg.put(vers, as2);
        return as2;
    }

    public ProcessState getProcessStateLocked(
            String packageName, int uid, long vers, String processName) {
        return getProcessStateLocked(getPackageStateLocked(packageName, uid, vers), processName);
    }

    public ProcessState getProcessStateLocked(PackageState pkgState, String processName) {
        ProcessState commonProc;
        String str;
        ProcessState ps;
        ProcessState ps2 = pkgState.mProcesses.get(processName);
        if (ps2 != null) {
            return ps2;
        }
        ProcessState commonProc2 = this.mProcesses.get(processName, pkgState.mUid);
        if (commonProc2 != null) {
            commonProc = commonProc2;
        } else {
            ProcessState commonProc3 =
                    new ProcessState(
                            this,
                            pkgState.mPackageName,
                            pkgState.mUid,
                            pkgState.mVersionCode,
                            processName);
            this.mProcesses.put(processName, pkgState.mUid, commonProc3);
            UidState uidState = this.mUidStates.get(pkgState.mUid);
            if (uidState == null) {
                uidState = new UidState(this, pkgState.mUid);
                this.mUidStates.put(pkgState.mUid, uidState);
            }
            uidState.addProcess(commonProc3);
            commonProc = commonProc3;
        }
        if (!commonProc.isMultiPackage()) {
            if (pkgState.mPackageName.equals(commonProc.getPackage())
                    && pkgState.mVersionCode == commonProc.getVersion()) {
                ps = commonProc;
                str = processName;
            } else {
                commonProc.setMultiPackage(true);
                long now = SystemClock.uptimeMillis();
                PackageState commonPkgState =
                        getPackageStateLocked(
                                commonProc.getPackage(), pkgState.mUid, commonProc.getVersion());
                if (commonPkgState != null) {
                    ProcessState cloned = commonProc.clone(now);
                    commonPkgState.mProcesses.put(commonProc.getName(), cloned);
                    for (int i = commonPkgState.mServices.size() - 1; i >= 0; i--) {
                        ServiceState ss = commonPkgState.mServices.valueAt(i);
                        if (ss.getProcess() == commonProc) {
                            ss.setProcess(cloned);
                        }
                    }
                    for (int i2 = commonPkgState.mAssociations.size() - 1; i2 >= 0; i2--) {
                        AssociationState as = commonPkgState.mAssociations.valueAt(i2);
                        if (as.getProcess() == commonProc) {
                            as.setProcess(cloned);
                        }
                    }
                } else {
                    Slog.w(
                            TAG,
                            "Cloning proc state: no package state "
                                    + commonProc.getPackage()
                                    + "/"
                                    + pkgState.mUid
                                    + " for proc "
                                    + commonProc.getName());
                }
                str = processName;
                ps =
                        new ProcessState(
                                commonProc,
                                pkgState.mPackageName,
                                pkgState.mUid,
                                pkgState.mVersionCode,
                                processName,
                                now);
            }
        } else {
            str = processName;
            ps =
                    new ProcessState(
                            commonProc,
                            pkgState.mPackageName,
                            pkgState.mUid,
                            pkgState.mVersionCode,
                            processName,
                            SystemClock.uptimeMillis());
        }
        pkgState.mProcesses.put(str, ps);
        return ps;
    }

    public ServiceState getServiceStateLocked(
            String packageName, int uid, long vers, String processName, String className) {
        PackageState as = getPackageStateLocked(packageName, uid, vers);
        ServiceState ss = as.mServices.get(className);
        if (ss != null) {
            return ss;
        }
        ProcessState ps =
                processName != null
                        ? getProcessStateLocked(packageName, uid, vers, processName)
                        : null;
        ServiceState ss2 = new ServiceState(this, packageName, className, processName, ps);
        as.mServices.put(className, ss2);
        return ss2;
    }

    public AssociationState getAssociationStateLocked(
            String packageName, int uid, long vers, String processName, String className) {
        PackageState pkgs = getPackageStateLocked(packageName, uid, vers);
        AssociationState as = pkgs.mAssociations.get(className);
        if (as != null) {
            return as;
        }
        ProcessState procs =
                processName != null
                        ? getProcessStateLocked(packageName, uid, vers, processName)
                        : null;
        AssociationState as2 = new AssociationState(this, pkgs, className, processName, procs);
        pkgs.mAssociations.put(className, as2);
        return as2;
    }

    public void updateTrackingAssociationsLocked(int curSeq, long now) {
        int NUM = this.mTrackingAssociations.size();
        for (int i = NUM - 1; i >= 0; i--) {
            AssociationState.SourceState act = this.mTrackingAssociations.get(i);
            if (act.stopActiveIfNecessary(curSeq, now)) {
                this.mTrackingAssociations.remove(i);
            } else {
                AssociationState asc = act.getAssociationState();
                if (asc == null) {
                    Slog.wtf(TAG, act.toString() + " shouldn't be in the tracking list.");
                } else {
                    ProcessState proc = asc.getProcess();
                    if (proc == null) {
                        Slog.wtf(
                                TAG, "Tracking association without process: " + act + " in " + asc);
                    } else {
                        int procState = proc.getCombinedState() % 16;
                        if (act.mProcState == procState) {
                            act.startActive(now);
                        } else {
                            act.stopActive(now);
                            if (act.mProcState < procState) {
                                long nowUptime = SystemClock.uptimeMillis();
                                if (this.mNextInverseProcStateWarningUptime <= nowUptime) {
                                    Slog.w(
                                            TAG,
                                            "Tracking association "
                                                    + act
                                                    + " whose proc state "
                                                    + act.mProcState
                                                    + " is better than process "
                                                    + proc
                                                    + " proc state "
                                                    + procState
                                                    + " ("
                                                    + this.mSkippedInverseProcStateWarningCount
                                                    + " skipped)");
                                    this.mSkippedInverseProcStateWarningCount = 0;
                                    this.mNextInverseProcStateWarningUptime = 10000 + nowUptime;
                                } else {
                                    this.mSkippedInverseProcStateWarningCount++;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    final class AssociationDumpContainer {
        long mActiveTime;
        ArrayList<Pair<AssociationState.SourceKey, AssociationState.SourceDumpContainer>> mSources;
        final AssociationState mState;
        long mTotalTime;

        AssociationDumpContainer(AssociationState state) {
            this.mState = state;
        }
    }

    static /* synthetic */ int lambda$static$0(
            AssociationDumpContainer o1, AssociationDumpContainer o2) {
        int diff = o1.mState.getProcessName().compareTo(o2.mState.getProcessName());
        if (diff != 0) {
            return diff;
        }
        if (o1.mActiveTime != o2.mActiveTime) {
            return o1.mActiveTime > o2.mActiveTime ? -1 : 1;
        }
        if (o1.mTotalTime != o2.mTotalTime) {
            return o1.mTotalTime > o2.mTotalTime ? -1 : 1;
        }
        int diff2 = o1.mState.getName().compareTo(o2.mState.getName());
        if (diff2 != 0) {
            return diff2;
        }
        return 0;
    }

    public void dumpLocked(
            PrintWriter pw,
            String reqPackage,
            long now,
            boolean dumpSummary,
            boolean dumpDetails,
            boolean dumpAll,
            boolean activeOnly,
            int section) {
        boolean partial;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        long totalTime;
        String str7;
        String str8;
        String str9;
        String str10;
        String str11;
        PrintWriter printWriter;
        String str12;
        String str13;
        String str14;
        String str15;
        String str16;
        int size;
        int iu;
        ArrayMap<String, SparseArray<ProcessState>> procMap;
        String str17;
        int iu2;
        SparseArray<ProcessState> uids;
        String procName;
        int ip;
        String str18;
        String str19;
        String str20;
        String str21;
        int ip2;
        int iv;
        String str22;
        boolean printedHeader;
        String pkgName;
        int uid;
        boolean printedHeader2;
        PackageState pkgState;
        String pkgName2;
        int NSRVS;
        String str23;
        String str24;
        int ip3;
        String str25;
        long totalTime2;
        String str26;
        int NASCS;
        String str27;
        String str28;
        int iu3;
        String str29;
        int NCONT;
        int iasc;
        int NASCS2;
        int iu4;
        String str30;
        String str31;
        int NSRVS2;
        String str32;
        String str33;
        String pkgName3;
        String str34;
        String str35;
        int NPROCS;
        String str36;
        int uid2;
        ProcessStats processStats = this;
        long totalTime3 =
                DumpUtils.dumpSingleTime(
                        null,
                        null,
                        processStats.mMemFactorDurations,
                        processStats.mMemFactor,
                        processStats.mStartTime,
                        now);
        pw.print("          Start time: ");
        pw.print(DateFormat.format("yyyy-MM-dd HH:mm:ss", processStats.mTimePeriodStartClock));
        pw.println();
        pw.print("        Total uptime: ");
        TimeUtils.formatDuration(
                (processStats.mRunning
                                ? SystemClock.uptimeMillis()
                                : processStats.mTimePeriodEndUptime)
                        - processStats.mTimePeriodStartUptime,
                pw);
        pw.println();
        pw.print("  Total elapsed time: ");
        TimeUtils.formatDuration(
                (processStats.mRunning
                                ? SystemClock.elapsedRealtime()
                                : processStats.mTimePeriodEndRealtime)
                        - processStats.mTimePeriodStartRealtime,
                pw);
        boolean partial2 = true;
        if ((processStats.mFlags & 2) != 0) {
            pw.print(" (shutdown)");
            partial2 = false;
        }
        if ((processStats.mFlags & 4) != 0) {
            pw.print(" (sysprops)");
            partial2 = false;
        }
        if ((processStats.mFlags & 1) == 0) {
            partial = partial2;
        } else {
            pw.print(" (complete)");
            partial = false;
        }
        if (partial) {
            pw.print(" (partial)");
        }
        if (processStats.mHasSwappedOutPss) {
            pw.print(" (swapped-out-pss)");
        }
        pw.print(' ');
        pw.print(processStats.mRuntime);
        pw.println();
        pw.print("     Aggregated over: ");
        pw.println(processStats.mNumAggregated);
        if (processStats.mSysMemUsage.getKeyCount() > 0) {
            pw.println();
            pw.println("System memory usage:");
            processStats.mSysMemUsage.dump(pw, "  ", ALL_SCREEN_ADJ, ALL_MEM_ADJ);
        }
        boolean printedHeader3 = false;
        int i = section & 14;
        String str37 = " / ";
        String str38 = "      (Not active: ";
        String str39 = " entries)";
        String str40 = "  * ";
        String str41 = NavigationBarInflaterView.KEY_CODE_END;
        String str42 = ":";
        if (i != 0) {
            ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap =
                    processStats.mPackages.getMap();
            int ip4 = 0;
            while (true) {
                boolean printedHeader4 = printedHeader3;
                if (ip4 >= pkgMap.size()) {
                    break;
                }
                String pkgName4 = pkgMap.keyAt(ip4);
                ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap2 = pkgMap;
                SparseArray<LongSparseArray<PackageState>> uids2 = pkgMap.valueAt(ip4);
                int iu5 = 0;
                while (iu5 < uids2.size()) {
                    int uid3 = uids2.keyAt(iu5);
                    int iu6 = iu5;
                    LongSparseArray<PackageState> vpkgs = uids2.valueAt(iu5);
                    SparseArray<LongSparseArray<PackageState>> uids3 = uids2;
                    int iv2 = 0;
                    while (true) {
                        ip2 = ip4;
                        int ip5 = vpkgs.size();
                        if (iv2 < ip5) {
                            long totalTime4 = totalTime3;
                            long vers = vpkgs.keyAt(iv2);
                            PackageState pkgState2 = vpkgs.valueAt(iv2);
                            LongSparseArray<PackageState> vpkgs2 = vpkgs;
                            int NPROCS2 = pkgState2.mProcesses.size();
                            String str43 = str41;
                            int NSRVS3 = pkgState2.mServices.size();
                            String str44 = str39;
                            int NASCS3 = pkgState2.mAssociations.size();
                            boolean pkgMatch = reqPackage == null || reqPackage.equals(pkgName4);
                            boolean onlyAssociations = false;
                            boolean procMatch = false;
                            if (pkgMatch) {
                                iv = iv2;
                                str22 = str38;
                            } else {
                                str22 = str38;
                                int iproc = 0;
                                while (true) {
                                    if (iproc >= NPROCS2) {
                                        iv = iv2;
                                        break;
                                    }
                                    iv = iv2;
                                    if (!reqPackage.equals(
                                            pkgState2.mProcesses.valueAt(iproc).getName())) {
                                        iproc++;
                                        iv2 = iv;
                                    } else {
                                        procMatch = true;
                                        break;
                                    }
                                }
                                if (!procMatch) {
                                    int iasc2 = 0;
                                    while (true) {
                                        if (iasc2 >= NASCS3) {
                                            break;
                                        }
                                        if (!pkgState2
                                                .mAssociations
                                                .valueAt(iasc2)
                                                .hasProcessOrPackage(reqPackage)) {
                                            iasc2++;
                                        } else {
                                            onlyAssociations = true;
                                            break;
                                        }
                                    }
                                    if (!onlyAssociations) {
                                        processStats = this;
                                        pkgName = pkgName4;
                                        uid = uid3;
                                        pkgName2 = str42;
                                        str23 = str40;
                                        str24 = str37;
                                        ip3 = ip2;
                                        str25 = str43;
                                        totalTime2 = totalTime4;
                                        str27 = str44;
                                        iu3 = iu6;
                                        iv2 = iv + 1;
                                        str42 = pkgName2;
                                        str41 = str25;
                                        vpkgs = vpkgs2;
                                        iu6 = iu3;
                                        str38 = str22;
                                        pkgName4 = pkgName;
                                        uid3 = uid;
                                        ip4 = ip3;
                                        str39 = str27;
                                        str40 = str23;
                                        str37 = str24;
                                        totalTime3 = totalTime2;
                                    }
                                }
                            }
                            if (NPROCS2 > 0 || NSRVS3 > 0 || NASCS3 > 0) {
                                if (!printedHeader4) {
                                    pw.println();
                                    pw.println("Per-Package Stats:");
                                    printedHeader4 = true;
                                }
                                pw.print(str40);
                                pw.print(pkgName4);
                                pw.print(str37);
                                UserHandle.formatUid(pw, uid3);
                                pw.print(" / v");
                                pw.print(vers);
                                pw.println(str42);
                                printedHeader = printedHeader4;
                            } else {
                                printedHeader = printedHeader4;
                            }
                            if ((section & 2) == 0 || onlyAssociations) {
                                pkgName = pkgName4;
                                uid = uid3;
                                printedHeader2 = printedHeader;
                                pkgState = pkgState2;
                                pkgName2 = str42;
                                NSRVS = NSRVS3;
                                str23 = str40;
                                str24 = str37;
                                ip3 = ip2;
                                str25 = str43;
                                totalTime2 = totalTime4;
                                str26 = str44;
                                NASCS = NASCS3;
                            } else {
                                if (!dumpSummary) {
                                    pkgName = pkgName4;
                                    uid = uid3;
                                    printedHeader2 = printedHeader;
                                    pkgState = pkgState2;
                                    str32 = str42;
                                    NSRVS = NSRVS3;
                                    str23 = str40;
                                    str24 = str37;
                                    ip3 = ip2;
                                    str33 = str43;
                                    totalTime2 = totalTime4;
                                    str26 = str44;
                                    pkgName3 = str22;
                                    NASCS = NASCS3;
                                } else if (dumpAll) {
                                    pkgName = pkgName4;
                                    uid = uid3;
                                    printedHeader2 = printedHeader;
                                    pkgState = pkgState2;
                                    str32 = str42;
                                    NSRVS = NSRVS3;
                                    str23 = str40;
                                    str24 = str37;
                                    ip3 = ip2;
                                    str33 = str43;
                                    totalTime2 = totalTime4;
                                    str26 = str44;
                                    pkgName3 = str22;
                                    NASCS = NASCS3;
                                } else {
                                    ArrayList<ProcessState> procs = new ArrayList<>();
                                    int iproc2 = 0;
                                    while (iproc2 < NPROCS2) {
                                        String pkgName5 = pkgName4;
                                        ProcessState proc = pkgState2.mProcesses.valueAt(iproc2);
                                        if (pkgMatch) {
                                            uid2 = uid3;
                                        } else {
                                            uid2 = uid3;
                                            if (!reqPackage.equals(proc.getName())) {
                                                iproc2++;
                                                pkgName4 = pkgName5;
                                                uid3 = uid2;
                                            }
                                        }
                                        if (!activeOnly || proc.isInUse()) {
                                            procs.add(proc);
                                        }
                                        iproc2++;
                                        pkgName4 = pkgName5;
                                        uid3 = uid2;
                                    }
                                    pkgName = pkgName4;
                                    uid = uid3;
                                    printedHeader2 = printedHeader;
                                    pkgState = pkgState2;
                                    ip3 = ip2;
                                    NSRVS = NSRVS3;
                                    str23 = str40;
                                    str26 = str44;
                                    NASCS = NASCS3;
                                    str24 = str37;
                                    totalTime2 = totalTime4;
                                    DumpUtils.dumpProcessSummaryLocked(
                                            pw,
                                            "      ",
                                            "Prc ",
                                            procs,
                                            ALL_SCREEN_ADJ,
                                            ALL_MEM_ADJ,
                                            NON_CACHED_PROC_STATES,
                                            now,
                                            totalTime2);
                                    pkgName2 = str42;
                                    str25 = str43;
                                }
                                int iproc3 = 0;
                                while (iproc3 < NPROCS2) {
                                    ProcessState proc2 = pkgState.mProcesses.valueAt(iproc3);
                                    if (!pkgMatch && !reqPackage.equals(proc2.getName())) {
                                        str34 = pkgName3;
                                        NPROCS = NPROCS2;
                                        str35 = str32;
                                        str36 = str33;
                                    } else if (!activeOnly || proc2.isInUse()) {
                                        String str45 = str33;
                                        pw.print("      Process ");
                                        pw.print(pkgState.mProcesses.keyAt(iproc3));
                                        if (proc2.getCommonProcess().isMultiPackage()) {
                                            pw.print(" (multi, ");
                                        } else {
                                            pw.print(" (unique, ");
                                        }
                                        pw.print(proc2.getDurationsBucketCount());
                                        pw.print(str26);
                                        String str46 = str32;
                                        pw.println(str46);
                                        str34 = pkgName3;
                                        str35 = str46;
                                        proc2.dumpProcessState(
                                                pw,
                                                "        ",
                                                ALL_SCREEN_ADJ,
                                                ALL_MEM_ADJ,
                                                ALL_PROC_STATES,
                                                now);
                                        proc2.dumpPss(
                                                pw,
                                                "        ",
                                                ALL_SCREEN_ADJ,
                                                ALL_MEM_ADJ,
                                                ALL_PROC_STATES,
                                                now);
                                        NPROCS = NPROCS2;
                                        str36 = str45;
                                        proc2.dumpInternalLocked(
                                                pw,
                                                "        ",
                                                reqPackage,
                                                totalTime2,
                                                now,
                                                dumpAll);
                                    } else {
                                        pw.print(pkgName3);
                                        pw.print(pkgState.mProcesses.keyAt(iproc3));
                                        String str47 = str33;
                                        pw.println(str47);
                                        str34 = pkgName3;
                                        NPROCS = NPROCS2;
                                        str36 = str47;
                                        str35 = str32;
                                    }
                                    iproc3++;
                                    str32 = str35;
                                    str33 = str36;
                                    pkgName3 = str34;
                                    NPROCS2 = NPROCS;
                                }
                                str22 = pkgName3;
                                pkgName2 = str32;
                                str25 = str33;
                            }
                            String str48 = "        Process: ";
                            if ((section & 4) == 0 || onlyAssociations) {
                                str27 = str26;
                                str28 = "        Process: ";
                            } else {
                                int isvc = 0;
                                while (true) {
                                    int NSRVS4 = NSRVS;
                                    if (isvc >= NSRVS4) {
                                        break;
                                    }
                                    ServiceState svc = pkgState.mServices.valueAt(isvc);
                                    if (!pkgMatch && !reqPackage.equals(svc.getProcessName())) {
                                        str30 = str26;
                                        str31 = str48;
                                        NSRVS2 = NSRVS4;
                                    } else if (activeOnly && !svc.isInUse()) {
                                        pw.print("      (Not active service: ");
                                        pw.print(pkgState.mServices.keyAt(isvc));
                                        pw.println(str25);
                                        str30 = str26;
                                        str31 = str48;
                                        NSRVS2 = NSRVS4;
                                    } else {
                                        if (dumpAll) {
                                            pw.print("      Service ");
                                        } else {
                                            pw.print("      * Svc ");
                                        }
                                        pw.print(pkgState.mServices.keyAt(isvc));
                                        pw.println(pkgName2);
                                        pw.print(str48);
                                        pw.println(svc.getProcessName());
                                        str30 = str26;
                                        str31 = str48;
                                        NSRVS2 = NSRVS4;
                                        svc.dumpStats(
                                                pw,
                                                "        ",
                                                "          ",
                                                "    ",
                                                now,
                                                totalTime2,
                                                dumpSummary,
                                                dumpAll);
                                    }
                                    isvc++;
                                    str48 = str31;
                                    str26 = str30;
                                    NSRVS = NSRVS2;
                                }
                                str27 = str26;
                                str28 = str48;
                            }
                            if ((section & 8) == 0) {
                                processStats = this;
                                iu3 = iu6;
                            } else {
                                int NASCS4 = NASCS;
                                ArrayList<AssociationDumpContainer> associations =
                                        new ArrayList<>(NASCS4);
                                int iasc3 = 0;
                                while (iasc3 < NASCS4) {
                                    AssociationState asc = pkgState.mAssociations.valueAt(iasc3);
                                    if (!pkgMatch && !reqPackage.equals(asc.getProcessName())) {
                                        if (!onlyAssociations) {
                                            iu4 = iu6;
                                        } else if (!asc.hasProcessOrPackage(reqPackage)) {
                                            iu4 = iu6;
                                        }
                                        iasc3++;
                                        iu6 = iu4;
                                    }
                                    AssociationDumpContainer cont =
                                            new AssociationDumpContainer(asc);
                                    iu4 = iu6;
                                    cont.mSources =
                                            AssociationState.createSortedAssociations(
                                                    now, totalTime2, asc.mSources);
                                    cont.mTotalTime = asc.getTotalDuration(now);
                                    cont.mActiveTime = asc.getActiveDuration(now);
                                    associations.add(cont);
                                    iasc3++;
                                    iu6 = iu4;
                                }
                                iu3 = iu6;
                                Collections.sort(associations, ASSOCIATION_COMPARATOR);
                                int NCONT2 = associations.size();
                                int iasc4 = 0;
                                while (iasc4 < NCONT2) {
                                    AssociationDumpContainer cont2 = associations.get(iasc4);
                                    AssociationState asc2 = cont2.mState;
                                    if (activeOnly && !asc2.isInUse()) {
                                        pw.print("      (Not active association: ");
                                        pw.print(pkgState.mAssociations.keyAt(iasc4));
                                        pw.println(str25);
                                        str29 = str28;
                                        NCONT = NCONT2;
                                        iasc = iasc4;
                                        NASCS2 = NASCS4;
                                    } else {
                                        if (dumpAll) {
                                            pw.print("      Association ");
                                        } else {
                                            pw.print("      * Asc ");
                                        }
                                        pw.print(cont2.mState.getName());
                                        pw.println(pkgName2);
                                        pw.print(str28);
                                        pw.println(asc2.getProcessName());
                                        str29 = str28;
                                        NCONT = NCONT2;
                                        iasc = iasc4;
                                        NASCS2 = NASCS4;
                                        asc2.dumpStats(
                                                pw,
                                                "        ",
                                                "          ",
                                                "    ",
                                                cont2.mSources,
                                                now,
                                                totalTime2,
                                                (!onlyAssociations
                                                                || pkgMatch
                                                                || procMatch
                                                                || asc2.getProcessName()
                                                                        .equals(reqPackage))
                                                        ? null
                                                        : reqPackage,
                                                dumpDetails,
                                                dumpAll);
                                    }
                                    iasc4 = iasc + 1;
                                    str28 = str29;
                                    NCONT2 = NCONT;
                                    NASCS4 = NASCS2;
                                }
                                processStats = this;
                            }
                            printedHeader4 = printedHeader2;
                            iv2 = iv + 1;
                            str42 = pkgName2;
                            str41 = str25;
                            vpkgs = vpkgs2;
                            iu6 = iu3;
                            str38 = str22;
                            pkgName4 = pkgName;
                            uid3 = uid;
                            ip4 = ip3;
                            str39 = str27;
                            str40 = str23;
                            str37 = str24;
                            totalTime3 = totalTime2;
                        }
                    }
                    iu5 = iu6 + 1;
                    uids2 = uids3;
                    pkgName4 = pkgName4;
                    ip4 = ip2;
                }
                ip4++;
                printedHeader3 = printedHeader4;
                pkgMap = pkgMap2;
            }
            str = str42;
            str2 = str41;
            str3 = str40;
            str4 = str39;
            str6 = str37;
            totalTime = totalTime3;
            str5 = str38;
        } else {
            str = ":";
            str2 = NavigationBarInflaterView.KEY_CODE_END;
            str3 = "  * ";
            str4 = " entries)";
            str5 = "      (Not active: ";
            str6 = " / ";
            totalTime = totalTime3;
        }
        String str49 = " (";
        String str50 = " total";
        String str51 = " shown of ";
        if ((section & 1) == 0) {
            str7 = str;
            str8 = " total";
            str9 = " shown of ";
            str10 = " (";
        } else {
            ArrayMap<String, SparseArray<ProcessState>> procMap2 = processStats.mProcesses.getMap();
            int numShownProcs = 0;
            int numTotalProcs = 0;
            boolean printedHeader5 = false;
            int ip6 = 0;
            while (ip6 < procMap2.size()) {
                String procName2 = procMap2.keyAt(ip6);
                SparseArray<ProcessState> uids4 = procMap2.valueAt(ip6);
                int i2 = numTotalProcs;
                int numTotalProcs2 = 0;
                int numTotalProcs3 = i2;
                while (true) {
                    procMap = procMap2;
                    if (numTotalProcs2 < uids4.size()) {
                        int uid4 = uids4.keyAt(numTotalProcs2);
                        int numTotalProcs4 = numTotalProcs3 + 1;
                        ProcessState proc3 = uids4.valueAt(numTotalProcs2);
                        if (!proc3.hasAnyData()
                                || !proc3.isMultiPackage()
                                || (reqPackage != null
                                        && !reqPackage.equals(procName2)
                                        && !reqPackage.equals(proc3.getPackage()))) {
                            str17 = str;
                            iu2 = numTotalProcs2;
                            uids = uids4;
                            procName = procName2;
                            ip = ip6;
                            str18 = str50;
                            str19 = str51;
                            str20 = str49;
                            str21 = str6;
                        } else {
                            int numShownProcs2 = numShownProcs + 1;
                            pw.println();
                            if (!printedHeader5) {
                                pw.println("Multi-Package Common Processes:");
                                printedHeader5 = true;
                            }
                            if (activeOnly && !proc3.isInUse()) {
                                pw.print(str5);
                                pw.print(procName2);
                                pw.println(str2);
                                str17 = str;
                                iu2 = numTotalProcs2;
                                uids = uids4;
                                procName = procName2;
                                ip = ip6;
                                str18 = str50;
                                str19 = str51;
                                str20 = str49;
                                str21 = str6;
                            } else {
                                String str52 = str3;
                                pw.print(str52);
                                pw.print(procName2);
                                String str53 = str6;
                                pw.print(str53);
                                UserHandle.formatUid(pw, uid4);
                                pw.print(str49);
                                pw.print(proc3.getDurationsBucketCount());
                                pw.print(str4);
                                pw.println(str);
                                str21 = str53;
                                str17 = str;
                                iu2 = numTotalProcs2;
                                uids = uids4;
                                procName = procName2;
                                ip = ip6;
                                str3 = str52;
                                str18 = str50;
                                str19 = str51;
                                proc3.dumpProcessState(
                                        pw,
                                        "        ",
                                        ALL_SCREEN_ADJ,
                                        ALL_MEM_ADJ,
                                        ALL_PROC_STATES,
                                        now);
                                proc3.dumpPss(
                                        pw,
                                        "        ",
                                        ALL_SCREEN_ADJ,
                                        ALL_MEM_ADJ,
                                        ALL_PROC_STATES,
                                        now);
                                str20 = str49;
                                proc3.dumpInternalLocked(
                                        pw, "        ", reqPackage, totalTime, now, dumpAll);
                            }
                            numShownProcs = numShownProcs2;
                        }
                        numTotalProcs2 = iu2 + 1;
                        str51 = str19;
                        str50 = str18;
                        procMap2 = procMap;
                        numTotalProcs3 = numTotalProcs4;
                        procName2 = procName;
                        ip6 = ip;
                        uids4 = uids;
                        str6 = str21;
                        str = str17;
                        str49 = str20;
                    }
                }
                ip6++;
                numTotalProcs = numTotalProcs3;
                procMap2 = procMap;
                str = str;
            }
            str7 = str;
            str8 = str50;
            str9 = str51;
            str10 = str49;
            pw.print("  Total procs: ");
            pw.print(numShownProcs);
            pw.print(str9);
            pw.print(numTotalProcs);
            pw.println(str8);
        }
        if ((section & 16) == 0) {
            str11 = str7;
        } else {
            SparseArray<UidState> uidStates = processStats.mUidStates;
            int numShownUids = 0;
            int numTotalUids = 0;
            int size2 = uidStates.size();
            boolean printedHeader6 = false;
            int iu7 = 0;
            while (iu7 < size2) {
                int uid5 = uidStates.keyAt(iu7);
                UidState uidState = uidStates.valueAt(iu7);
                int numTotalUids2 = numTotalUids + 1;
                if (reqPackage != null && !uidState.hasPackage(reqPackage)) {
                    str12 = str2;
                    size = size2;
                    iu = iu7;
                    str13 = str7;
                    str14 = str4;
                    str16 = str3;
                    str15 = str10;
                } else {
                    int numShownUids2 = numShownUids + 1;
                    pw.println();
                    if (!printedHeader6) {
                        pw.println("Per-UID Stats:");
                        printedHeader6 = true;
                    }
                    if (activeOnly && !uidState.isInUse()) {
                        pw.print(str5);
                        pw.print(UserHandle.formatUid(uid5));
                        pw.println(str2);
                        str12 = str2;
                        size = size2;
                        iu = iu7;
                        str13 = str7;
                        str14 = str4;
                        str16 = str3;
                        str15 = str10;
                    } else {
                        String str54 = str3;
                        pw.print(str54);
                        UserHandle.formatUid(pw, uid5);
                        String str55 = str10;
                        pw.print(str55);
                        pw.print(uidState.getDurationsBucketCount());
                        String str56 = str4;
                        pw.print(str56);
                        String str57 = str7;
                        pw.println(str57);
                        str12 = str2;
                        str13 = str57;
                        str14 = str56;
                        str15 = str55;
                        str16 = str54;
                        size = size2;
                        iu = iu7;
                        uidState.dumpState(
                                pw, "        ", ALL_SCREEN_ADJ, ALL_MEM_ADJ, ALL_PROC_STATES, now);
                    }
                    numShownUids = numShownUids2;
                }
                iu7 = iu + 1;
                str7 = str13;
                numTotalUids = numTotalUids2;
                size2 = size;
                str3 = str16;
                str4 = str14;
                str10 = str15;
                str2 = str12;
            }
            str11 = str7;
            pw.print("  Total UIDs: ");
            pw.print(numShownUids);
            pw.print(str9);
            pw.print(numTotalUids);
            pw.println(str8);
        }
        if (dumpAll) {
            pw.println();
            if (processStats.mTrackingAssociations.size() > 0) {
                pw.println();
                pw.println("Tracking associations:");
                for (int i3 = 0; i3 < processStats.mTrackingAssociations.size(); i3++) {
                    AssociationState.SourceState src = processStats.mTrackingAssociations.get(i3);
                    AssociationState asc3 = src.getAssociationState();
                    if (asc3 == null) {
                        Slog.wtf(TAG, src.toString() + " shouldn't be in the tracking list.");
                    } else {
                        pw.print("  #");
                        pw.print(i3);
                        pw.print(": ");
                        pw.print(asc3.getProcessName());
                        pw.print("/");
                        UserHandle.formatUid(pw, asc3.getUid());
                        pw.print(" <- ");
                        pw.print(src.getProcessName());
                        pw.print("/");
                        UserHandle.formatUid(pw, src.getUid());
                        pw.println(str11);
                        pw.print("    Tracking for: ");
                        TimeUtils.formatDuration(now - src.mTrackingUptime, pw);
                        pw.println();
                        pw.print("    Component: ");
                        pw.print(
                                new ComponentName(asc3.getPackage(), asc3.getName())
                                        .flattenToShortString());
                        pw.println();
                        pw.print("    Proc state: ");
                        if (src.mProcState != -1) {
                            pw.print(DumpUtils.STATE_NAMES[src.mProcState]);
                        } else {
                            pw.print("--");
                        }
                        pw.print(" #");
                        pw.println(src.mProcStateSeq);
                        pw.print("    Process: ");
                        pw.println(asc3.getProcess());
                        if (src.mActiveCount > 0) {
                            pw.print("    Active count ");
                            pw.print(src.mActiveCount);
                            pw.print(": ");
                            AssociationState.dumpActiveDurationSummary(
                                    pw, src, totalTime, now, dumpAll);
                            pw.println();
                        }
                    }
                }
            }
        }
        pw.println();
        if (dumpSummary) {
            pw.println("Process summary:");
            printWriter = pw;
            dumpSummaryLocked(pw, reqPackage, now, activeOnly);
        } else {
            printWriter = pw;
            processStats.dumpTotalsLocked(printWriter, now);
        }
        if (dumpAll) {
            pw.println();
            printWriter.println("Internal state:");
            printWriter.print("  mRunning=");
            printWriter.println(processStats.mRunning);
        }
        if (reqPackage == null) {
            dumpFragmentationLocked(pw);
        }
    }

    public void dumpSummaryLocked(PrintWriter pw, String reqPackage, long now, boolean activeOnly) {
        long totalTime =
                DumpUtils.dumpSingleTime(
                        null,
                        null,
                        this.mMemFactorDurations,
                        this.mMemFactor,
                        this.mStartTime,
                        now);
        dumpFilteredSummaryLocked(
                pw,
                null,
                "  ",
                null,
                ALL_SCREEN_ADJ,
                ALL_MEM_ADJ,
                ALL_PROC_STATES,
                NON_CACHED_PROC_STATES,
                now,
                totalTime,
                reqPackage,
                activeOnly);
        pw.println();
        dumpTotalsLocked(pw, now);
    }

    private void dumpFragmentationLocked(PrintWriter pw) {
        pw.println();
        pw.println("Available pages by page size:");
        int NPAGETYPES = this.mPageTypeLabels.size();
        for (int i = 0; i < NPAGETYPES; i++) {
            pw.format(
                    "Node %3d Zone %7s  %14s ",
                    this.mPageTypeNodes.get(i),
                    this.mPageTypeZones.get(i),
                    this.mPageTypeLabels.get(i));
            int[] sizes = this.mPageTypeSizes.get(i);
            int N = sizes == null ? 0 : sizes.length;
            for (int j = 0; j < N; j++) {
                pw.format("%6d", Integer.valueOf(sizes[j]));
            }
            pw.println();
        }
    }

    long printMemoryCategory(
            PrintWriter pw,
            String prefix,
            String label,
            double memWeight,
            long totalTime,
            long curTotalMem,
            int samples) {
        if (memWeight != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            long mem = (long) ((1024.0d * memWeight) / totalTime);
            pw.print(prefix);
            pw.print(label);
            pw.print(": ");
            DebugUtils.printSizeValue(pw, mem);
            pw.print(" (");
            pw.print(samples);
            pw.print(" samples)");
            pw.println();
            return curTotalMem + mem;
        }
        return curTotalMem;
    }

    void dumpTotalsLocked(PrintWriter pw, long now) {
        int i;
        pw.println("Run time Stats:");
        DumpUtils.dumpSingleTime(
                pw, "  ", this.mMemFactorDurations, this.mMemFactor, this.mStartTime, now);
        pw.println();
        pw.println("Memory usage:");
        TotalMemoryUseCollection totalMem =
                new TotalMemoryUseCollection(ALL_SCREEN_ADJ, ALL_MEM_ADJ);
        computeTotalMemoryUse(totalMem, now);
        long totalPss =
                printMemoryCategory(
                        pw,
                        "  ",
                        "Kernel ",
                        totalMem.sysMemKernelWeight,
                        totalMem.totalTime,
                        0L,
                        totalMem.sysMemSamples);
        long totalPss2 =
                printMemoryCategory(
                        pw,
                        "  ",
                        "Native ",
                        totalMem.sysMemNativeWeight,
                        totalMem.totalTime,
                        totalPss,
                        totalMem.sysMemSamples);
        int i2 = 0;
        while (i2 < 16) {
            if (i2 == 9) {
                i = i2;
            } else {
                i = i2;
                totalPss2 =
                        printMemoryCategory(
                                pw,
                                "  ",
                                DumpUtils.STATE_NAMES[i2],
                                totalMem.processStateWeight[i2],
                                totalMem.totalTime,
                                totalPss2,
                                totalMem.processStateSamples[i2]);
            }
            i2 = i + 1;
        }
        long totalPss3 =
                printMemoryCategory(
                        pw,
                        "  ",
                        "Z-Ram  ",
                        totalMem.sysMemZRamWeight,
                        totalMem.totalTime,
                        printMemoryCategory(
                                pw,
                                "  ",
                                "Free   ",
                                totalMem.sysMemFreeWeight,
                                totalMem.totalTime,
                                printMemoryCategory(
                                        pw,
                                        "  ",
                                        "Cached ",
                                        totalMem.sysMemCachedWeight,
                                        totalMem.totalTime,
                                        totalPss2,
                                        totalMem.sysMemSamples),
                                totalMem.sysMemSamples),
                        totalMem.sysMemSamples);
        pw.print("  TOTAL  : ");
        DebugUtils.printSizeValue(pw, totalPss3);
        pw.println();
        printMemoryCategory(
                pw,
                "  ",
                DumpUtils.STATE_NAMES[9],
                totalMem.processStateWeight[9],
                totalMem.totalTime,
                totalPss3,
                totalMem.processStateSamples[9]);
        pw.println();
        pw.println("PSS collection stats:");
        pw.print("  Internal Single: ");
        pw.print(this.mInternalSinglePssCount);
        pw.print("x over ");
        TimeUtils.formatDuration(this.mInternalSinglePssTime, pw);
        pw.println();
        pw.print("  Internal All Procs (Memory Change): ");
        pw.print(this.mInternalAllMemPssCount);
        pw.print("x over ");
        TimeUtils.formatDuration(this.mInternalAllMemPssTime, pw);
        pw.println();
        pw.print("  Internal All Procs (Polling): ");
        pw.print(this.mInternalAllPollPssCount);
        pw.print("x over ");
        TimeUtils.formatDuration(this.mInternalAllPollPssTime, pw);
        pw.println();
        pw.print("  External: ");
        pw.print(this.mExternalPssCount);
        pw.print("x over ");
        TimeUtils.formatDuration(this.mExternalPssTime, pw);
        pw.println();
        pw.print("  External Slow: ");
        pw.print(this.mExternalSlowPssCount);
        pw.print("x over ");
        TimeUtils.formatDuration(this.mExternalSlowPssTime, pw);
        pw.println();
    }

    void dumpFilteredSummaryLocked(
            PrintWriter pw,
            String header,
            String prefix,
            String prcLabel,
            int[] screenStates,
            int[] memStates,
            int[] procStates,
            int[] sortProcStates,
            long now,
            long totalTime,
            String reqPackage,
            boolean activeOnly) {
        ArrayList<ProcessState> procs =
                collectProcessesLocked(
                        screenStates,
                        memStates,
                        procStates,
                        sortProcStates,
                        now,
                        reqPackage,
                        activeOnly);
        if (procs.size() > 0) {
            if (header != null) {
                pw.println();
                pw.println(header);
            }
            DumpUtils.dumpProcessSummaryLocked(
                    pw,
                    prefix,
                    prcLabel,
                    procs,
                    screenStates,
                    memStates,
                    sortProcStates,
                    now,
                    totalTime);
        }
    }

    public ArrayList<ProcessState> collectProcessesLocked(
            int[] screenStates,
            int[] memStates,
            int[] procStates,
            int[] sortProcStates,
            long now,
            String reqPackage,
            boolean activeOnly) {
        ArraySet<ProcessState> foundProcs = new ArraySet<>();
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap =
                this.mPackages.getMap();
        for (int ip = 0; ip < pkgMap.size(); ip++) {
            String pkgName = pkgMap.keyAt(ip);
            SparseArray<LongSparseArray<PackageState>> procs = pkgMap.valueAt(ip);
            for (int iu = 0; iu < procs.size(); iu++) {
                LongSparseArray<PackageState> vpkgs = procs.valueAt(iu);
                int NVERS = vpkgs.size();
                for (int iv = 0; iv < NVERS; iv++) {
                    PackageState state = vpkgs.valueAt(iv);
                    int NPROCS = state.mProcesses.size();
                    boolean pkgMatch = reqPackage == null || reqPackage.equals(pkgName);
                    for (int iproc = 0; iproc < NPROCS; iproc++) {
                        ProcessState proc = state.mProcesses.valueAt(iproc);
                        if ((pkgMatch || reqPackage.equals(proc.getName()))
                                && (!activeOnly || proc.isInUse())) {
                            foundProcs.add(proc.getCommonProcess());
                        }
                    }
                }
            }
        }
        ArrayList<ProcessState> outProcs = new ArrayList<>(foundProcs.size());
        for (int i = 0; i < foundProcs.size(); i++) {
            ProcessState proc2 = foundProcs.valueAt(i);
            if (proc2.computeProcessTimeLocked(screenStates, memStates, procStates, now) > 0) {
                outProcs.add(proc2);
                if (procStates != sortProcStates) {
                    proc2.computeProcessTimeLocked(screenStates, memStates, sortProcStates, now);
                }
            }
        }
        Collections.sort(outProcs, ProcessState.COMPARATOR);
        return outProcs;
    }

    public void dumpCheckinLocked(PrintWriter pw, String reqPackage, int section) {
        boolean partial;
        String str;
        ProcessStats processStats;
        String str2;
        LongSparseArray<PackageState> vpkgs;
        SparseArray<LongSparseArray<PackageState>> uids;
        int iu;
        String pkgName;
        int NSRVS;
        int ip;
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap;
        String str3;
        PackageState pkgState;
        int NASCS;
        String str4 = reqPackage;
        long now = SystemClock.uptimeMillis();
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap2 =
                this.mPackages.getMap();
        pw.println("vers,5");
        pw.print("period,");
        pw.print(this.mTimePeriodStartClockStr);
        String str5 = ",";
        pw.print(",");
        pw.print(this.mTimePeriodStartRealtime);
        pw.print(",");
        pw.print(this.mRunning ? SystemClock.elapsedRealtime() : this.mTimePeriodEndRealtime);
        boolean partial2 = true;
        if ((this.mFlags & 2) != 0) {
            pw.print(",shutdown");
            partial2 = false;
        }
        if ((this.mFlags & 4) != 0) {
            pw.print(",sysprops");
            partial2 = false;
        }
        if ((this.mFlags & 1) == 0) {
            partial = partial2;
        } else {
            pw.print(",complete");
            partial = false;
        }
        if (partial) {
            pw.print(",partial");
        }
        if (this.mHasSwappedOutPss) {
            pw.print(",swapped-out-pss");
        }
        pw.println();
        pw.print("config,");
        pw.println(this.mRuntime);
        if ((section & 14) == 0) {
            str = ",";
        } else {
            int ip2 = 0;
            while (ip2 < pkgMap2.size()) {
                String pkgName2 = pkgMap2.keyAt(ip2);
                if (str4 == null || str4.equals(pkgName2)) {
                    SparseArray<LongSparseArray<PackageState>> uids2 = pkgMap2.valueAt(ip2);
                    int iu2 = 0;
                    while (iu2 < uids2.size()) {
                        int uid = uids2.keyAt(iu2);
                        LongSparseArray<PackageState> vpkgs2 = uids2.valueAt(iu2);
                        int iv = 0;
                        while (iv < vpkgs2.size()) {
                            long vers = vpkgs2.keyAt(iv);
                            PackageState pkgState2 = vpkgs2.valueAt(iv);
                            int NPROCS = pkgState2.mProcesses.size();
                            int NSRVS2 = pkgState2.mServices.size();
                            int iv2 = iv;
                            int NASCS2 = pkgState2.mAssociations.size();
                            if ((section & 2) != 0) {
                                int iproc = 0;
                                while (iproc < NPROCS) {
                                    ProcessState proc = pkgState2.mProcesses.valueAt(iproc);
                                    proc.dumpPackageProcCheckin(
                                            pw,
                                            pkgName2,
                                            uid,
                                            vers,
                                            pkgState2.mProcesses.keyAt(iproc),
                                            now);
                                    iproc++;
                                    NSRVS2 = NSRVS2;
                                    pkgName2 = pkgName2;
                                    pkgState2 = pkgState2;
                                    NASCS2 = NASCS2;
                                    ip2 = ip2;
                                    NPROCS = NPROCS;
                                    pkgMap2 = pkgMap2;
                                    str5 = str5;
                                    vpkgs2 = vpkgs2;
                                    uids2 = uids2;
                                    iu2 = iu2;
                                }
                                vpkgs = vpkgs2;
                                uids = uids2;
                                iu = iu2;
                                pkgName = pkgName2;
                                NSRVS = NSRVS2;
                                ip = ip2;
                                pkgMap = pkgMap2;
                                str3 = str5;
                                pkgState = pkgState2;
                                NASCS = NASCS2;
                            } else {
                                vpkgs = vpkgs2;
                                uids = uids2;
                                iu = iu2;
                                pkgName = pkgName2;
                                NSRVS = NSRVS2;
                                ip = ip2;
                                pkgMap = pkgMap2;
                                str3 = str5;
                                pkgState = pkgState2;
                                NASCS = NASCS2;
                            }
                            int NPROCS2 = section & 4;
                            if (NPROCS2 != 0) {
                                for (int isvc = 0; isvc < NSRVS; isvc++) {
                                    String serviceName =
                                            DumpUtils.collapseString(
                                                    pkgName, pkgState.mServices.keyAt(isvc));
                                    ServiceState svc = pkgState.mServices.valueAt(isvc);
                                    svc.dumpTimesCheckin(pw, pkgName, uid, vers, serviceName, now);
                                }
                            }
                            if ((section & 8) != 0) {
                                for (int iasc = 0; iasc < NASCS; iasc++) {
                                    String associationName =
                                            DumpUtils.collapseString(
                                                    pkgName, pkgState.mAssociations.keyAt(iasc));
                                    AssociationState asc = pkgState.mAssociations.valueAt(iasc);
                                    asc.dumpTimesCheckin(
                                            pw, pkgName, uid, vers, associationName, now);
                                }
                            }
                            iv = iv2 + 1;
                            pkgName2 = pkgName;
                            ip2 = ip;
                            pkgMap2 = pkgMap;
                            str5 = str3;
                            vpkgs2 = vpkgs;
                            uids2 = uids;
                            iu2 = iu;
                        }
                        iu2++;
                    }
                }
                ip2++;
                str4 = reqPackage;
                pkgMap2 = pkgMap2;
                str5 = str5;
            }
            str = str5;
        }
        if ((section & 1) == 0) {
            processStats = this;
        } else {
            processStats = this;
            ArrayMap<String, SparseArray<ProcessState>> procMap = processStats.mProcesses.getMap();
            for (int ip3 = 0; ip3 < procMap.size(); ip3++) {
                String procName = procMap.keyAt(ip3);
                SparseArray<ProcessState> uids3 = procMap.valueAt(ip3);
                for (int iu3 = 0; iu3 < uids3.size(); iu3++) {
                    int uid2 = uids3.keyAt(iu3);
                    ProcessState procState = uids3.valueAt(iu3);
                    procState.dumpProcCheckin(pw, procName, uid2, now);
                }
            }
        }
        pw.print("total");
        DumpUtils.dumpAdjTimesCheckin(
                pw,
                ",",
                processStats.mMemFactorDurations,
                processStats.mMemFactor,
                processStats.mStartTime,
                now);
        pw.println();
        int sysMemUsageCount = processStats.mSysMemUsage.getKeyCount();
        if (sysMemUsageCount <= 0) {
            str2 = str;
        } else {
            pw.print("sysmemusage");
            int i = 0;
            while (i < sysMemUsageCount) {
                int key = processStats.mSysMemUsage.getKeyAt(i);
                int type = SparseMappingTable.getIdFromKey(key);
                String str6 = str;
                pw.print(str6);
                DumpUtils.printProcStateTag(pw, type);
                for (int j = 0; j < 16; j++) {
                    if (j > 1) {
                        pw.print(":");
                    }
                    pw.print(processStats.mSysMemUsage.getValue(key, j));
                }
                i++;
                str = str6;
            }
            str2 = str;
        }
        pw.println();
        TotalMemoryUseCollection totalMem =
                new TotalMemoryUseCollection(ALL_SCREEN_ADJ, ALL_MEM_ADJ);
        processStats.computeTotalMemoryUse(totalMem, now);
        pw.print("weights,");
        pw.print(totalMem.totalTime);
        pw.print(str2);
        pw.print(totalMem.sysMemCachedWeight);
        pw.print(":");
        pw.print(totalMem.sysMemSamples);
        pw.print(str2);
        pw.print(totalMem.sysMemFreeWeight);
        pw.print(":");
        pw.print(totalMem.sysMemSamples);
        pw.print(str2);
        pw.print(totalMem.sysMemZRamWeight);
        pw.print(":");
        pw.print(totalMem.sysMemSamples);
        pw.print(str2);
        pw.print(totalMem.sysMemKernelWeight);
        pw.print(":");
        pw.print(totalMem.sysMemSamples);
        pw.print(str2);
        pw.print(totalMem.sysMemNativeWeight);
        pw.print(":");
        pw.print(totalMem.sysMemSamples);
        for (int i2 = 0; i2 < 16; i2++) {
            pw.print(str2);
            pw.print(totalMem.processStateWeight[i2]);
            pw.print(":");
            pw.print(totalMem.processStateSamples[i2]);
        }
        pw.println();
        int NPAGETYPES = processStats.mPageTypeLabels.size();
        for (int i3 = 0; i3 < NPAGETYPES; i3++) {
            pw.print("availablepages,");
            pw.print(processStats.mPageTypeLabels.get(i3));
            pw.print(str2);
            pw.print(processStats.mPageTypeZones.get(i3));
            pw.print(str2);
            int[] sizes = processStats.mPageTypeSizes.get(i3);
            int N = sizes == null ? 0 : sizes.length;
            for (int j2 = 0; j2 < N; j2++) {
                if (j2 != 0) {
                    pw.print(str2);
                }
                pw.print(sizes[j2]);
            }
            pw.println();
        }
    }

    public void dumpDebug(ProtoOutputStream proto, long now, int section) {
        dumpProtoPreamble(proto);
        int NPAGETYPES = this.mPageTypeLabels.size();
        for (int i = 0; i < NPAGETYPES; i++) {
            long token = proto.start(2246267895818L);
            proto.write(1120986464257L, this.mPageTypeNodes.get(i).intValue());
            proto.write(1138166333442L, this.mPageTypeZones.get(i));
            proto.write(1138166333443L, this.mPageTypeLabels.get(i));
            int[] sizes = this.mPageTypeSizes.get(i);
            int N = sizes == null ? 0 : sizes.length;
            for (int j = 0; j < N; j++) {
                proto.write(2220498092036L, sizes[j]);
            }
            proto.end(token);
        }
        ArrayMap<String, SparseArray<ProcessState>> procMap = this.mProcesses.getMap();
        if ((section & 1) != 0) {
            for (int ip = 0; ip < procMap.size(); ip++) {
                String procName = procMap.keyAt(ip);
                SparseArray<ProcessState> uids = procMap.valueAt(ip);
                for (int iu = 0; iu < uids.size(); iu++) {
                    int uid = uids.keyAt(iu);
                    ProcessState procState = uids.valueAt(iu);
                    procState.dumpDebug(proto, 2246267895816L, procName, uid, now);
                }
            }
        }
        if ((section & 14) != 0) {
            ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap =
                    this.mPackages.getMap();
            for (int ip2 = 0; ip2 < pkgMap.size(); ip2++) {
                SparseArray<LongSparseArray<PackageState>> uids2 = pkgMap.valueAt(ip2);
                for (int iu2 = 0; iu2 < uids2.size(); iu2++) {
                    LongSparseArray<PackageState> vers = uids2.valueAt(iu2);
                    for (int iv = 0; iv < vers.size(); iv++) {
                        PackageState pkgState = vers.valueAt(iv);
                        pkgState.dumpDebug(proto, 2246267895817L, now, section);
                    }
                }
            }
        }
    }

    public void dumpAggregatedProtoForStatsd(
            ProtoOutputStream[] protoStreams, long maxRawShardSizeBytes) {
        int shardIndex = 0;
        dumpProtoPreamble(protoStreams[0]);
        ArrayMap<String, SparseArray<ProcessState>> procMap = this.mProcesses.getMap();
        ProcessMap<ArraySet<PackageState>> procToPkgMap = new ProcessMap<>();
        SparseArray<ArraySet<String>> uidToPkgMap = new SparseArray<>();
        collectProcessPackageMaps(null, false, procToPkgMap, uidToPkgMap);
        int ip = 0;
        while (true) {
            if (ip >= procMap.size()) {
                break;
            }
            String procName = procMap.keyAt(ip);
            if (protoStreams[shardIndex].getRawSize() > maxRawShardSizeBytes) {
                shardIndex++;
                if (shardIndex < protoStreams.length) {
                    dumpProtoPreamble(protoStreams[shardIndex]);
                } else {
                    Slog.d(
                            TAG,
                            String.format(
                                    "Dropping process indices from %d to %d from statsd proto (too"
                                        + " large)",
                                    Integer.valueOf(ip), Integer.valueOf(procMap.size())));
                    break;
                }
            }
            SparseArray<ProcessState> uids = procMap.valueAt(ip);
            int iu = 0;
            while (iu < uids.size()) {
                int uid = uids.keyAt(iu);
                ProcessState procState = uids.valueAt(iu);
                procState.dumpAggregatedProtoForStatsd(
                        protoStreams[shardIndex],
                        2246267895816L,
                        procName,
                        uid,
                        this.mTimePeriodEndRealtime,
                        procToPkgMap,
                        uidToPkgMap);
                iu++;
                uids = uids;
                ip = ip;
            }
            ip++;
        }
        for (int i = 0; i <= shardIndex; i++) {
            protoStreams[i].flush();
        }
    }

    void forEachProcess(Consumer<ProcessState> consumer) {
        ArrayMap<String, SparseArray<ProcessState>> procMap = this.mProcesses.getMap();
        int size = procMap.size();
        for (int ip = 0; ip < size; ip++) {
            SparseArray<ProcessState> uids = procMap.valueAt(ip);
            int uidsSize = uids.size();
            for (int iu = 0; iu < uidsSize; iu++) {
                ProcessState processState = uids.valueAt(iu);
                consumer.accept(processState);
            }
        }
    }

    void forEachAssociation(
            QuintConsumer<
                            AssociationState,
                            Integer,
                            String,
                            AssociationState.SourceKey,
                            AssociationState.SourceState>
                    consumer) {
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap =
                this.mPackages.getMap();
        int size = pkgMap.size();
        for (int ip = 0; ip < size; ip++) {
            SparseArray<LongSparseArray<PackageState>> uids = pkgMap.valueAt(ip);
            int uidsSize = uids.size();
            for (int iu = 0; iu < uidsSize; iu++) {
                int uid = uids.keyAt(iu);
                LongSparseArray<PackageState> versions = uids.valueAt(iu);
                int versionsSize = versions.size();
                for (int iv = 0; iv < versionsSize; iv++) {
                    PackageState state = versions.valueAt(iv);
                    int iasc = 0;
                    int ascSize = state.mAssociations.size();
                    while (iasc < ascSize) {
                        String serviceName = state.mAssociations.keyAt(iasc);
                        AssociationState asc = state.mAssociations.valueAt(iasc);
                        int sourcesSize = asc.mSources.size();
                        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap2 =
                                pkgMap;
                        int is = 0;
                        while (is < sourcesSize) {
                            int sourcesSize2 = sourcesSize;
                            AssociationState.SourceState src = asc.mSources.valueAt(is);
                            int size2 = size;
                            AssociationState.SourceKey key = asc.mSources.keyAt(is);
                            AssociationState asc2 = asc;
                            consumer.accept(asc2, Integer.valueOf(uid), serviceName, key, src);
                            is++;
                            sourcesSize = sourcesSize2;
                            size = size2;
                            asc = asc2;
                        }
                        iasc++;
                        pkgMap = pkgMap2;
                    }
                }
            }
        }
    }

    public void dumpProcessState(final int atomTag, final StatsEventOutput statsEventOutput) {
        forEachProcess(
                new Consumer() { // from class:
                                 // com.android.internal.app.procstats.ProcessStats$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ProcessStats.this.lambda$dumpProcessState$1(
                                atomTag, statsEventOutput, (ProcessState) obj);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dumpProcessState$1(
            int atomTag, StatsEventOutput statsEventOutput, ProcessState processState) {
        if (processState.isMultiPackage() && processState.getCommonProcess() != processState) {
            return;
        }
        processState.dumpStateDurationToStatsd(atomTag, this, statsEventOutput);
    }

    public void dumpProcessAssociation(final int atomTag, final StatsEventOutput statsEventOutput) {
        forEachAssociation(
                new QuintConsumer() { // from class:
                                      // com.android.internal.app.procstats.ProcessStats$$ExternalSyntheticLambda0
                    @Override // com.android.internal.util.function.QuintConsumer
                    public final void accept(
                            Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                        ProcessStats.this.lambda$dumpProcessAssociation$2(
                                statsEventOutput,
                                atomTag,
                                (AssociationState) obj,
                                (Integer) obj2,
                                (String) obj3,
                                (AssociationState.SourceKey) obj4,
                                (AssociationState.SourceState) obj5);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dumpProcessAssociation$2(
            StatsEventOutput statsEventOutput,
            int atomTag,
            AssociationState asc,
            Integer serviceUid,
            String serviceName,
            AssociationState.SourceKey key,
            AssociationState.SourceState src) {
        statsEventOutput.write(
                atomTag,
                key.mUid,
                key.mProcess,
                serviceUid.intValue(),
                serviceName,
                (int) TimeUnit.MILLISECONDS.toSeconds(this.mTimePeriodStartUptime),
                (int) TimeUnit.MILLISECONDS.toSeconds(this.mTimePeriodEndUptime),
                (int)
                        TimeUnit.MILLISECONDS.toSeconds(
                                this.mTimePeriodEndUptime - this.mTimePeriodStartUptime),
                (int) TimeUnit.MILLISECONDS.toSeconds(src.mDuration),
                src.mActiveCount,
                asc.getProcessName());
    }

    private void dumpProtoPreamble(ProtoOutputStream proto) {
        proto.write(1112396529665L, this.mTimePeriodStartRealtime);
        proto.write(
                1112396529666L,
                this.mRunning ? SystemClock.elapsedRealtime() : this.mTimePeriodEndRealtime);
        proto.write(1112396529667L, this.mTimePeriodStartUptime);
        proto.write(1112396529668L, this.mTimePeriodEndUptime);
        proto.write(1138166333445L, this.mRuntime);
        proto.write(1133871366150L, this.mHasSwappedOutPss);
        boolean partial = true;
        if ((this.mFlags & 2) != 0) {
            proto.write(2259152797703L, 3);
            partial = false;
        }
        if ((this.mFlags & 4) != 0) {
            proto.write(2259152797703L, 4);
            partial = false;
        }
        if ((this.mFlags & 1) != 0) {
            proto.write(2259152797703L, 1);
            partial = false;
        }
        if (partial) {
            proto.write(2259152797703L, 2);
        }
    }

    private void collectProcessPackageMaps(
            String reqPackage,
            boolean activeOnly,
            ProcessMap<ArraySet<PackageState>> procToPkgMap,
            SparseArray<ArraySet<String>> uidToPkgMap) {
        ArraySet<PackageState> pkgStates;
        ArraySet<String> packages;
        String str = reqPackage;
        ProcessMap<ArraySet<PackageState>> processMap = procToPkgMap;
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> pkgMap =
                this.mPackages.getMap();
        int i = 1;
        int ip = pkgMap.size() - 1;
        while (ip >= 0) {
            String pkgName = pkgMap.keyAt(ip);
            SparseArray<LongSparseArray<PackageState>> procs = pkgMap.valueAt(ip);
            int iu = procs.size() - i;
            while (iu >= 0) {
                LongSparseArray<PackageState> vpkgs = procs.valueAt(iu);
                int iv = vpkgs.size() - i;
                while (iv >= 0) {
                    PackageState state = vpkgs.valueAt(iv);
                    int i2 = (str == null || str.equals(pkgName)) ? i : 0;
                    int iproc = state.mProcesses.size() - i;
                    while (iproc >= 0) {
                        ProcessState proc = state.mProcesses.valueAt(iproc);
                        if ((i2 != 0 || str.equals(proc.getName()))
                                && (!activeOnly || proc.isInUse())) {
                            String name = proc.getName();
                            int uid = proc.getUid();
                            ArraySet<PackageState> pkgStates2 = processMap.get(name, uid);
                            if (pkgStates2 != null) {
                                pkgStates = pkgStates2;
                            } else {
                                pkgStates = new ArraySet<>();
                                processMap.put(name, uid, pkgStates);
                            }
                            pkgStates.add(state);
                            ArraySet<String> packages2 = uidToPkgMap.get(uid);
                            if (packages2 != null) {
                                packages = packages2;
                            } else {
                                packages = new ArraySet<>();
                                uidToPkgMap.put(uid, packages);
                            }
                            packages.add(state.mPackageName);
                        }
                        iproc--;
                        str = reqPackage;
                        processMap = procToPkgMap;
                    }
                    iv--;
                    str = reqPackage;
                    processMap = procToPkgMap;
                    i = 1;
                }
                iu--;
                str = reqPackage;
                processMap = procToPkgMap;
                i = 1;
            }
            ip--;
            str = reqPackage;
            processMap = procToPkgMap;
            i = 1;
        }
    }

    public void dumpFilteredAssociationStatesProtoForProc(
            ProtoOutputStream proto,
            long fieldId,
            long now,
            ProcessState procState,
            SparseArray<ArraySet<String>> uidToPkgMap) {
        ArrayMap<AssociationState.SourceKey, AssociationState.SourceState> sources;
        IProcessStats procStatsService;
        long duration;
        int idx;
        boolean z;
        int i;
        if ((!procState.isMultiPackage() || procState.getCommonProcess() == procState)
                && (sources = procState.mCommonSources) != null
                && !sources.isEmpty()
                && (procStatsService =
                                IProcessStats.Stub.asInterface(
                                        ServiceManager.getService(SERVICE_NAME)))
                        != null) {
            try {
                long minimum = procStatsService.getMinAssociationDumpDuration();
                int i2 = 1;
                int i3 = sources.size() - 1;
                while (i3 >= 0) {
                    AssociationState.SourceState src = sources.valueAt(i3);
                    long duration2 = src.mDuration;
                    if (src.mNesting <= 0) {
                        duration = duration2;
                    } else {
                        duration = duration2 + (now - src.mStartUptime);
                    }
                    if (duration < minimum) {
                        i = i2;
                    } else {
                        AssociationState.SourceKey key = sources.keyAt(i3);
                        long token = proto.start(fieldId);
                        int idx2 = uidToPkgMap.indexOfKey(key.mUid);
                        String str = key.mProcess;
                        String str2 = key.mPackage;
                        if (idx2 >= 0) {
                            idx = 1;
                            if (uidToPkgMap.valueAt(idx2).size() > 1) {
                                z = true;
                                i = idx;
                                ProcessState.writeCompressedProcessName(
                                        proto, 1138166333441L, str, str2, z);
                                proto.write(1120986464261L, key.mUid);
                                proto.write(1120986464259L, src.mCount);
                                proto.write(1120986464260L, (int) (duration / 1000));
                                proto.end(token);
                            }
                        } else {
                            idx = i2;
                        }
                        z = false;
                        i = idx;
                        ProcessState.writeCompressedProcessName(
                                proto, 1138166333441L, str, str2, z);
                        proto.write(1120986464261L, key.mUid);
                        proto.write(1120986464259L, src.mCount);
                        proto.write(1120986464260L, (int) (duration / 1000));
                        proto.end(token);
                    }
                    i3--;
                    i2 = i;
                }
            } catch (RemoteException e) {
            }
        }
    }

    public static final class ProcessStateHolder {
        public final long appVersion;
        public PackageState pkg;
        public ProcessState state;

        public ProcessStateHolder(long _appVersion) {
            this.appVersion = _appVersion;
        }
    }

    public static final class PackageState {
        public final String mPackageName;
        public final ProcessStats mProcessStats;
        public final int mUid;
        public final long mVersionCode;
        public final ArrayMap<String, ProcessState> mProcesses = new ArrayMap<>();
        public final ArrayMap<String, ServiceState> mServices = new ArrayMap<>();
        public final ArrayMap<String, AssociationState> mAssociations = new ArrayMap<>();

        public PackageState(ProcessStats procStats, String packageName, int uid, long versionCode) {
            this.mProcessStats = procStats;
            this.mUid = uid;
            this.mPackageName = packageName;
            this.mVersionCode = versionCode;
        }

        public AssociationState getAssociationStateLocked(ProcessState proc, String className) {
            AssociationState as = this.mAssociations.get(className);
            if (as != null) {
                if (proc != null) {
                    as.setProcess(proc);
                }
                return as;
            }
            AssociationState as2 =
                    new AssociationState(this.mProcessStats, this, className, proc.getName(), proc);
            this.mAssociations.put(className, as2);
            return as2;
        }

        public void dumpDebug(ProtoOutputStream proto, long fieldId, long now, int section) {
            long token = proto.start(fieldId);
            proto.write(1138166333441L, this.mPackageName);
            proto.write(1120986464258L, this.mUid);
            proto.write(1112396529667L, this.mVersionCode);
            if ((section & 2) != 0) {
                for (int ip = 0; ip < this.mProcesses.size(); ip++) {
                    String procName = this.mProcesses.keyAt(ip);
                    ProcessState procState = this.mProcesses.valueAt(ip);
                    procState.dumpDebug(proto, 2246267895812L, procName, this.mUid, now);
                }
            }
            if ((section & 4) != 0) {
                for (int is = 0; is < this.mServices.size(); is++) {
                    ServiceState serviceState = this.mServices.valueAt(is);
                    serviceState.dumpDebug(proto, 2246267895813L, now);
                }
            }
            if ((section & 8) != 0) {
                for (int ia = 0; ia < this.mAssociations.size(); ia++) {
                    AssociationState ascState = this.mAssociations.valueAt(ia);
                    ascState.dumpDebug(proto, 2246267895814L, now);
                }
            }
            proto.end(token);
        }
    }

    public static final class ProcessDataCollection {
        public long avgPss;
        public long avgRss;
        public long avgUss;
        public long maxPss;
        public long maxRss;
        public long maxUss;
        final int[] memStates;
        public long minPss;
        public long minRss;
        public long minUss;
        public long numPss;
        final int[] procStates;
        final int[] screenStates;
        public long totalTime;

        public ProcessDataCollection(int[] _screenStates, int[] _memStates, int[] _procStates) {
            this.screenStates = _screenStates;
            this.memStates = _memStates;
            this.procStates = _procStates;
        }

        void print(PrintWriter pw, long overallTime, boolean full) {
            if (this.totalTime > overallTime) {
                pw.print("*");
            }
            DumpUtils.printPercent(pw, this.totalTime / overallTime);
            if (this.numPss > 0) {
                pw.print(" (");
                DebugUtils.printSizeValue(pw, this.minPss * 1024);
                pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                DebugUtils.printSizeValue(pw, this.avgPss * 1024);
                pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                DebugUtils.printSizeValue(pw, this.maxPss * 1024);
                pw.print("/");
                DebugUtils.printSizeValue(pw, this.minUss * 1024);
                pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                DebugUtils.printSizeValue(pw, this.avgUss * 1024);
                pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                DebugUtils.printSizeValue(pw, this.maxUss * 1024);
                pw.print("/");
                DebugUtils.printSizeValue(pw, this.minRss * 1024);
                pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                DebugUtils.printSizeValue(pw, this.avgRss * 1024);
                pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                DebugUtils.printSizeValue(pw, this.maxRss * 1024);
                if (full) {
                    pw.print(" over ");
                    pw.print(this.numPss);
                }
                pw.print(NavigationBarInflaterView.KEY_CODE_END);
            }
        }
    }

    public static class TotalMemoryUseCollection {
        public boolean hasSwappedOutPss;
        final int[] memStates;
        final int[] screenStates;
        public double sysMemCachedWeight;
        public double sysMemFreeWeight;
        public double sysMemKernelWeight;
        public double sysMemNativeWeight;
        public int sysMemSamples;
        public double sysMemZRamWeight;
        public long totalTime;
        public long[] processStatePss = new long[16];
        public double[] processStateWeight = new double[16];
        public long[] processStateTime = new long[16];
        public int[] processStateSamples = new int[16];
        public long[] sysMemUsage = new long[16];

        public TotalMemoryUseCollection(int[] _screenStates, int[] _memStates) {
            this.screenStates = _screenStates;
            this.memStates = _memStates;
        }
    }
}
