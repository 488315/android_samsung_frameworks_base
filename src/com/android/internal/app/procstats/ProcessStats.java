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
import com.android.internal.app.procstats.AssociationState;
import com.android.internal.app.procstats.IProcessStats;
import com.android.internal.app.procstats.ProcessStats;
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
    public static long COMMIT_PERIOD = 10800000;
    public static long COMMIT_UPTIME_PERIOD = 3600000;
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
    public static final int[] ALL_MEM_ADJ = {0, 1, 2, 3};
    public static final int[] ALL_SCREEN_ADJ = {0, 4};
    public static final int[] NON_CACHED_PROC_STATES = {0, 1, 3, 5, 6, 7, 8, 9, 10, 11, 2, 4};
    public static final int[] BACKGROUND_PROC_STATES = {5, 6, 7, 11, 8, 9, 10};
    public static final int[] ALL_PROC_STATES = {0, 1, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 2, 4, 15};
    public static final int[] OPTIONS = {1, 2, 4, 8, 14, 16, 31};
    public static final String[] OPTIONS_STR = {"proc", "pkg-proc", "pkg-svc", "pkg-asc", "pkg-all", "uid", "all"};
    private static final Pattern sPageTypeRegex = Pattern.compile("^Node\\s+(\\d+),.* zone\\s+(\\w+),.* type\\s+(\\w+)\\s+([\\s\\d]+?)\\s*$");
    public static final Parcelable.Creator<ProcessStats> CREATOR = new Parcelable.Creator<ProcessStats>() { // from class: com.android.internal.app.procstats.ProcessStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProcessStats createFromParcel(Parcel parcel) {
            return new ProcessStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProcessStats[] newArray(int i) {
            return new ProcessStats[i];
        }
    };
    static final int[] BAD_TABLE = new int[0];
    static final Comparator<AssociationDumpContainer> ASSOCIATION_COMPARATOR = new Comparator() { // from class: com.android.internal.app.procstats.ProcessStats$$ExternalSyntheticLambda1
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ProcessStats.lambda$static$0((ProcessStats.AssociationDumpContainer) obj, (ProcessStats.AssociationDumpContainer) obj2);
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ProcessStats(boolean z) throws Throwable {
        this.mPackages = new ProcessMap<>();
        this.mProcesses = new ProcessMap<>();
        this.mUidStates = new SparseArray<>();
        this.mTrackingAssociations = new ArrayList<>();
        this.mMemFactorDurations = new long[8];
        this.mMemFactor = -1;
        this.mNumAggregated = 1;
        SparseMappingTable sparseMappingTable = new SparseMappingTable();
        this.mTableData = sparseMappingTable;
        this.mSysMemUsageArgs = new long[16];
        this.mSysMemUsage = new SysMemUsageTable(sparseMappingTable);
        this.mPageTypeNodes = new ArrayList<>();
        this.mPageTypeZones = new ArrayList<>();
        this.mPageTypeLabels = new ArrayList<>();
        this.mPageTypeSizes = new ArrayList<>();
        this.mRunning = z;
        reset();
        if (z) {
            Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
            Debug.getMemoryInfo(Process.myPid(), memoryInfo);
            this.mHasSwappedOutPss = memoryInfo.hasSwappedOutPss();
        }
    }

    public ProcessStats(Parcel parcel) throws Throwable {
        this.mPackages = new ProcessMap<>();
        this.mProcesses = new ProcessMap<>();
        this.mUidStates = new SparseArray<>();
        this.mTrackingAssociations = new ArrayList<>();
        this.mMemFactorDurations = new long[8];
        this.mMemFactor = -1;
        this.mNumAggregated = 1;
        SparseMappingTable sparseMappingTable = new SparseMappingTable();
        this.mTableData = sparseMappingTable;
        this.mSysMemUsageArgs = new long[16];
        this.mSysMemUsage = new SysMemUsageTable(sparseMappingTable);
        this.mPageTypeNodes = new ArrayList<>();
        this.mPageTypeZones = new ArrayList<>();
        this.mPageTypeLabels = new ArrayList<>();
        this.mPageTypeSizes = new ArrayList<>();
        reset();
        readFromParcel(parcel);
    }

    public ProcessStats() {
        this(false);
    }

    public void add(ProcessStats processStats) {
        ProcessStats processStats2;
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> arrayMap;
        int i;
        SparseArray<LongSparseArray<PackageState>> sparseArray;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> map = processStats.mPackages.getMap();
        int i7 = 0;
        while (i7 < map.size()) {
            String strKeyAt = map.keyAt(i7);
            SparseArray<LongSparseArray<PackageState>> sparseArrayValueAt = map.valueAt(i7);
            int i8 = 0;
            while (i8 < sparseArrayValueAt.size()) {
                int iKeyAt = sparseArrayValueAt.keyAt(i8);
                LongSparseArray<PackageState> longSparseArrayValueAt = sparseArrayValueAt.valueAt(i8);
                int i9 = 0;
                while (i9 < longSparseArrayValueAt.size()) {
                    long jKeyAt = longSparseArrayValueAt.keyAt(i9);
                    PackageState packageStateValueAt = longSparseArrayValueAt.valueAt(i9);
                    int size = packageStateValueAt.mProcesses.size();
                    int size2 = packageStateValueAt.mServices.size();
                    int size3 = packageStateValueAt.mAssociations.size();
                    int i10 = 0;
                    while (i10 < size) {
                        int i11 = size2;
                        ProcessState processStateValueAt = packageStateValueAt.mProcesses.valueAt(i10);
                        String str = strKeyAt;
                        if (processStateValueAt.getCommonProcess() != processStateValueAt) {
                            arrayMap = map;
                            i3 = i11;
                            i5 = i10;
                            i6 = size3;
                            strKeyAt = str;
                            i4 = size;
                            ProcessState processStateLocked = getProcessStateLocked(strKeyAt, iKeyAt, jKeyAt, processStateValueAt.getName());
                            i = i7;
                            if (processStateLocked.getCommonProcess() == processStateLocked) {
                                processStateLocked.setMultiPackage(true);
                                sparseArray = sparseArrayValueAt;
                                long jUptimeMillis = SystemClock.uptimeMillis();
                                i2 = i8;
                                PackageState packageStateLocked = getPackageStateLocked(strKeyAt, iKeyAt, jKeyAt);
                                processStateLocked = processStateLocked.clone(jUptimeMillis);
                                packageStateLocked.mProcesses.put(processStateLocked.getName(), processStateLocked);
                            } else {
                                sparseArray = sparseArrayValueAt;
                                i2 = i8;
                            }
                            processStateLocked.add(processStateValueAt);
                        } else {
                            arrayMap = map;
                            i = i7;
                            sparseArray = sparseArrayValueAt;
                            i2 = i8;
                            i3 = i11;
                            strKeyAt = str;
                            i4 = size;
                            i5 = i10;
                            i6 = size3;
                        }
                        int i12 = i6;
                        i10 = i5 + 1;
                        size3 = i12;
                        size2 = i3;
                        size = i4;
                        map = arrayMap;
                        i7 = i;
                        sparseArrayValueAt = sparseArray;
                        i8 = i2;
                    }
                    int i13 = size3;
                    ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> arrayMap2 = map;
                    int i14 = i7;
                    SparseArray<LongSparseArray<PackageState>> sparseArray2 = sparseArrayValueAt;
                    int i15 = i8;
                    int i16 = size2;
                    for (int i17 = 0; i17 < i16; i17++) {
                        ServiceState serviceStateValueAt = packageStateValueAt.mServices.valueAt(i17);
                        getServiceStateLocked(strKeyAt, iKeyAt, jKeyAt, serviceStateValueAt.getProcessName(), serviceStateValueAt.getName()).add(serviceStateValueAt);
                    }
                    for (int i18 = 0; i18 < i13; i18++) {
                        AssociationState associationStateValueAt = packageStateValueAt.mAssociations.valueAt(i18);
                        getAssociationStateLocked(strKeyAt, iKeyAt, jKeyAt, associationStateValueAt.getProcessName(), associationStateValueAt.getName()).add(associationStateValueAt);
                    }
                    i9++;
                    map = arrayMap2;
                    i7 = i14;
                    sparseArrayValueAt = sparseArray2;
                    i8 = i15;
                }
                i8++;
            }
            i7++;
        }
        ProcessStats processStats3 = this;
        SparseArray<UidState> sparseArray3 = processStats.mUidStates;
        int size4 = sparseArray3.size();
        for (int i19 = 0; i19 < size4; i19++) {
            int iKeyAt2 = sparseArray3.keyAt(i19);
            UidState uidState = processStats3.mUidStates.get(iKeyAt2);
            if (uidState == null) {
                processStats3.mUidStates.put(iKeyAt2, sparseArray3.valueAt(i19).m7978clone());
            } else {
                uidState.add(sparseArray3.valueAt(i19));
            }
        }
        ArrayMap<String, SparseArray<ProcessState>> map2 = processStats.mProcesses.getMap();
        for (int i20 = 0; i20 < map2.size(); i20++) {
            SparseArray<ProcessState> sparseArrayValueAt2 = map2.valueAt(i20);
            int i21 = 0;
            while (i21 < sparseArrayValueAt2.size()) {
                int iKeyAt3 = sparseArrayValueAt2.keyAt(i21);
                ProcessState processStateValueAt2 = sparseArrayValueAt2.valueAt(i21);
                String name = processStateValueAt2.getName();
                String str2 = processStateValueAt2.getPackage();
                long version = processStateValueAt2.getVersion();
                ProcessState processState = processStats3.mProcesses.get(name, iKeyAt3);
                if (processState == null) {
                    ProcessState processState2 = new ProcessState(this, str2, iKeyAt3, version, name);
                    processStats2 = this;
                    processStats2.mProcesses.put(name, iKeyAt3, processState2);
                    PackageState packageStateLocked2 = processStats2.getPackageStateLocked(str2, iKeyAt3, version);
                    if (!packageStateLocked2.mProcesses.containsKey(name)) {
                        packageStateLocked2.mProcesses.put(name, processState2);
                    }
                    processState = processState2;
                } else {
                    processStats2 = processStats3;
                }
                processState.add(processStateValueAt2);
                UidState uidState2 = processStats2.mUidStates.get(iKeyAt3);
                if (uidState2 == null) {
                    uidState2 = new UidState(processStats2, iKeyAt3);
                    processStats2.mUidStates.put(iKeyAt3, uidState2);
                }
                uidState2.addProcess(processState);
                i21++;
                processStats3 = processStats2;
            }
        }
        ProcessStats processStats4 = processStats3;
        int size5 = processStats4.mUidStates.size();
        for (int i22 = 0; i22 < size5; i22++) {
            processStats4.mUidStates.valueAt(i22).updateCombinedState(-1L);
        }
        for (int i23 = 0; i23 < 8; i23++) {
            long[] jArr = processStats4.mMemFactorDurations;
            jArr[i23] = jArr[i23] + processStats.mMemFactorDurations[i23];
        }
        processStats4.mSysMemUsage.mergeStats(processStats.mSysMemUsage);
        processStats4.mNumAggregated += processStats.mNumAggregated;
        long j = processStats.mTimePeriodStartClock;
        if (j < processStats4.mTimePeriodStartClock) {
            processStats4.mTimePeriodStartClock = j;
            processStats4.mTimePeriodStartClockStr = processStats.mTimePeriodStartClockStr;
        }
        processStats4.mTimePeriodEndRealtime += processStats.mTimePeriodEndRealtime - processStats.mTimePeriodStartRealtime;
        processStats4.mTimePeriodEndUptime += processStats.mTimePeriodEndUptime - processStats.mTimePeriodStartUptime;
        processStats4.mInternalSinglePssCount += processStats.mInternalSinglePssCount;
        processStats4.mInternalSinglePssTime += processStats.mInternalSinglePssTime;
        processStats4.mInternalAllMemPssCount += processStats.mInternalAllMemPssCount;
        processStats4.mInternalAllMemPssTime += processStats.mInternalAllMemPssTime;
        processStats4.mInternalAllPollPssCount += processStats.mInternalAllPollPssCount;
        processStats4.mInternalAllPollPssTime += processStats.mInternalAllPollPssTime;
        processStats4.mExternalPssCount += processStats.mExternalPssCount;
        processStats4.mExternalPssTime += processStats.mExternalPssTime;
        processStats4.mExternalSlowPssCount += processStats.mExternalSlowPssCount;
        processStats4.mExternalSlowPssTime += processStats.mExternalSlowPssTime;
        processStats4.mHasSwappedOutPss |= processStats.mHasSwappedOutPss;
    }

    public void addSysMemUsage(long j, long j2, long j3, long j4, long j5) {
        int i = this.mMemFactor;
        if (i != -1) {
            int i2 = i * 16;
            this.mSysMemUsageArgs[0] = 1;
            int i3 = 0;
            while (i3 < 3) {
                long[] jArr = this.mSysMemUsageArgs;
                int i4 = i3 + 1;
                jArr[i4] = j;
                jArr[i3 + 4] = j2;
                jArr[i3 + 7] = j3;
                jArr[i3 + 10] = j4;
                jArr[i3 + 13] = j5;
                i3 = i4;
            }
            this.mSysMemUsage.mergeStats(i2, this.mSysMemUsageArgs, 0);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0086  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void computeTotalMemoryUse(TotalMemoryUseCollection totalMemoryUseCollection, long j) {
        long[] arrayForKey;
        int indexFromKey;
        totalMemoryUseCollection.totalTime = 0L;
        for (int i = 0; i < 16; i++) {
            totalMemoryUseCollection.processStateWeight[i] = 0.0d;
            totalMemoryUseCollection.processStatePss[i] = 0;
            totalMemoryUseCollection.processStateTime[i] = 0;
            totalMemoryUseCollection.processStateSamples[i] = 0;
        }
        for (int i2 = 0; i2 < 16; i2++) {
            totalMemoryUseCollection.sysMemUsage[i2] = 0;
        }
        totalMemoryUseCollection.sysMemCachedWeight = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        totalMemoryUseCollection.sysMemFreeWeight = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        totalMemoryUseCollection.sysMemZRamWeight = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        totalMemoryUseCollection.sysMemKernelWeight = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        totalMemoryUseCollection.sysMemNativeWeight = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        totalMemoryUseCollection.sysMemSamples = 0;
        long[] totalMemUsage = this.mSysMemUsage.getTotalMemUsage();
        for (int i3 = 0; i3 < totalMemoryUseCollection.screenStates.length; i3++) {
            for (int i4 = 0; i4 < totalMemoryUseCollection.memStates.length; i4++) {
                int i5 = totalMemoryUseCollection.screenStates[i3] + totalMemoryUseCollection.memStates[i4];
                int i6 = i5 * 16;
                long j2 = this.mMemFactorDurations[i5];
                if (this.mMemFactor == i5) {
                    j2 += j - this.mStartTime;
                }
                totalMemoryUseCollection.totalTime += j2;
                int key = this.mSysMemUsage.getKey((byte) i6);
                if (key != -1) {
                    arrayForKey = this.mSysMemUsage.getArrayForKey(key);
                    indexFromKey = SparseMappingTable.getIndexFromKey(key);
                    if (arrayForKey[indexFromKey] >= 3) {
                        SysMemUsageTable.mergeSysMemUsage(totalMemoryUseCollection.sysMemUsage, 0, totalMemUsage, 0);
                    } else {
                        arrayForKey = totalMemUsage;
                        indexFromKey = 0;
                    }
                }
                double d = j2;
                totalMemoryUseCollection.sysMemCachedWeight += arrayForKey[indexFromKey + 2] * d;
                totalMemoryUseCollection.sysMemFreeWeight += arrayForKey[indexFromKey + 5] * d;
                totalMemoryUseCollection.sysMemZRamWeight += arrayForKey[indexFromKey + 8] * d;
                totalMemoryUseCollection.sysMemKernelWeight += arrayForKey[indexFromKey + 11] * d;
                totalMemoryUseCollection.sysMemNativeWeight += arrayForKey[indexFromKey + 14] * d;
                totalMemoryUseCollection.sysMemSamples = (int) (totalMemoryUseCollection.sysMemSamples + arrayForKey[indexFromKey]);
            }
        }
        totalMemoryUseCollection.hasSwappedOutPss = this.mHasSwappedOutPss;
        ArrayMap<String, SparseArray<ProcessState>> map = this.mProcesses.getMap();
        for (int i7 = 0; i7 < map.size(); i7++) {
            SparseArray<ProcessState> sparseArrayValueAt = map.valueAt(i7);
            for (int i8 = 0; i8 < sparseArrayValueAt.size(); i8++) {
                sparseArrayValueAt.valueAt(i8).aggregatePss(totalMemoryUseCollection, j);
            }
        }
    }

    public void reset() throws Throwable {
        resetCommon();
        this.mPackages.getMap().clear();
        this.mProcesses.getMap().clear();
        this.mUidStates.clear();
        this.mMemFactor = -1;
        this.mStartTime = 0L;
    }

    public void resetSafely() throws Throwable {
        resetCommon();
        long jUptimeMillis = SystemClock.uptimeMillis();
        ArrayMap<String, SparseArray<ProcessState>> map = this.mProcesses.getMap();
        for (int size = map.size() - 1; size >= 0; size--) {
            SparseArray<ProcessState> sparseArrayValueAt = map.valueAt(size);
            for (int size2 = sparseArrayValueAt.size() - 1; size2 >= 0; size2--) {
                sparseArrayValueAt.valueAt(size2).tmpNumInUse = 0;
            }
        }
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> map2 = this.mPackages.getMap();
        for (int size3 = map2.size() - 1; size3 >= 0; size3--) {
            SparseArray<LongSparseArray<PackageState>> sparseArrayValueAt2 = map2.valueAt(size3);
            for (int size4 = sparseArrayValueAt2.size() - 1; size4 >= 0; size4--) {
                LongSparseArray<PackageState> longSparseArrayValueAt = sparseArrayValueAt2.valueAt(size4);
                for (int size5 = longSparseArrayValueAt.size() - 1; size5 >= 0; size5--) {
                    PackageState packageStateValueAt = longSparseArrayValueAt.valueAt(size5);
                    for (int size6 = packageStateValueAt.mProcesses.size() - 1; size6 >= 0; size6--) {
                        ProcessState processStateValueAt = packageStateValueAt.mProcesses.valueAt(size6);
                        if (processStateValueAt.isInUse()) {
                            processStateValueAt.resetSafely(jUptimeMillis);
                            processStateValueAt.getCommonProcess().tmpNumInUse++;
                            processStateValueAt.getCommonProcess().tmpFoundSubProc = processStateValueAt;
                        } else {
                            packageStateValueAt.mProcesses.valueAt(size6).makeDead();
                            packageStateValueAt.mProcesses.removeAt(size6);
                        }
                    }
                    for (int size7 = packageStateValueAt.mServices.size() - 1; size7 >= 0; size7--) {
                        ServiceState serviceStateValueAt = packageStateValueAt.mServices.valueAt(size7);
                        if (serviceStateValueAt.isInUse()) {
                            serviceStateValueAt.resetSafely(jUptimeMillis);
                        } else {
                            packageStateValueAt.mServices.removeAt(size7);
                        }
                    }
                    for (int size8 = packageStateValueAt.mAssociations.size() - 1; size8 >= 0; size8--) {
                        AssociationState associationStateValueAt = packageStateValueAt.mAssociations.valueAt(size8);
                        if (associationStateValueAt.isInUse()) {
                            associationStateValueAt.resetSafely(jUptimeMillis);
                        } else {
                            packageStateValueAt.mAssociations.removeAt(size8);
                        }
                    }
                    if (packageStateValueAt.mProcesses.size() <= 0 && packageStateValueAt.mServices.size() <= 0 && packageStateValueAt.mAssociations.size() <= 0) {
                        longSparseArrayValueAt.removeAt(size5);
                    }
                }
                if (longSparseArrayValueAt.size() <= 0) {
                    sparseArrayValueAt2.removeAt(size4);
                }
            }
            if (sparseArrayValueAt2.size() <= 0) {
                map2.removeAt(size3);
            }
        }
        for (int size9 = map.size() - 1; size9 >= 0; size9--) {
            SparseArray<ProcessState> sparseArrayValueAt3 = map.valueAt(size9);
            for (int size10 = sparseArrayValueAt3.size() - 1; size10 >= 0; size10--) {
                ProcessState processStateValueAt2 = sparseArrayValueAt3.valueAt(size10);
                if (processStateValueAt2.isInUse() || processStateValueAt2.tmpNumInUse > 0) {
                    if (!processStateValueAt2.isActive() && processStateValueAt2.isMultiPackage() && processStateValueAt2.tmpNumInUse == 1) {
                        ProcessState processState = processStateValueAt2.tmpFoundSubProc;
                        processState.makeStandalone();
                        sparseArrayValueAt3.setValueAt(size10, processState);
                    } else {
                        processStateValueAt2.resetSafely(jUptimeMillis);
                    }
                } else {
                    processStateValueAt2.makeDead();
                    sparseArrayValueAt3.removeAt(size10);
                }
            }
            if (sparseArrayValueAt3.size() <= 0) {
                map.removeAt(size9);
            }
        }
        for (int size11 = this.mUidStates.size() - 1; size11 >= 0; size11--) {
            if (this.mUidStates.valueAt(size11).isInUse()) {
                this.mUidStates.valueAt(size11).resetSafely(jUptimeMillis);
            } else {
                this.mUidStates.removeAt(size11);
            }
        }
        this.mStartTime = jUptimeMillis;
    }

    private void resetCommon() throws Throwable {
        this.mNumAggregated = 1;
        this.mTimePeriodStartClock = System.currentTimeMillis();
        buildTimePeriodStartClockStr();
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        this.mTimePeriodEndRealtime = jElapsedRealtime;
        this.mTimePeriodStartRealtime = jElapsedRealtime;
        long jUptimeMillis = SystemClock.uptimeMillis();
        this.mTimePeriodEndUptime = jUptimeMillis;
        this.mTimePeriodStartUptime = jUptimeMillis;
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

    public boolean evaluateSystemProperties(boolean z) {
        String str = SystemProperties.get("persist.sys.dalvik.vm.lib.2", VMRuntime.getRuntime().vmLibrary());
        if (Objects.equals(str, this.mRuntime)) {
            return false;
        }
        if (z) {
            this.mRuntime = str;
        }
        return true;
    }

    private void buildTimePeriodStartClockStr() {
        this.mTimePeriodStartClockStr = DateFormat.format("yyyy-MM-dd-HH-mm-ss", this.mTimePeriodStartClock).toString();
    }

    public void updateFragmentation() throws Throwable {
        BufferedReader bufferedReader;
        Integer numValueOf;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                try {
                    bufferedReader = new BufferedReader(new FileReader("/proc/pagetypeinfo"));
                } catch (IOException unused) {
                }
            } catch (Throwable th) {
                th = th;
            }
            try {
                Matcher matcher = sPageTypeRegex.matcher("");
                this.mPageTypeNodes.clear();
                this.mPageTypeZones.clear();
                this.mPageTypeLabels.clear();
                this.mPageTypeSizes.clear();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line != null) {
                        matcher.reset(line);
                        if (matcher.matches() && (numValueOf = Integer.valueOf(matcher.group(1), 10)) != null) {
                            this.mPageTypeNodes.add(numValueOf);
                            this.mPageTypeZones.add(matcher.group(2));
                            this.mPageTypeLabels.add(matcher.group(3));
                            this.mPageTypeSizes.add(splitAndParseNumbers(matcher.group(4)));
                        }
                    } else {
                        bufferedReader.close();
                        return;
                    }
                }
            } catch (IOException unused2) {
                bufferedReader2 = bufferedReader;
                this.mPageTypeNodes.clear();
                this.mPageTypeZones.clear();
                this.mPageTypeLabels.clear();
                this.mPageTypeSizes.clear();
                if (bufferedReader2 != null) {
                    bufferedReader2.close();
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedReader2 = bufferedReader;
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (IOException unused3) {
                    }
                }
                throw th;
            }
        } catch (IOException unused4) {
        }
    }

    private static int[] splitAndParseNumbers(String str) {
        int length = str.length();
        int i = 0;
        boolean z = false;
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt < '0' || cCharAt > '9') {
                z = false;
            } else if (!z) {
                i++;
                z = true;
            }
        }
        int[] iArr = new int[i];
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < length; i5++) {
            char cCharAt2 = str.charAt(i5);
            if (cCharAt2 < '0' || cCharAt2 > '9') {
                if (z) {
                    iArr[i4] = i3;
                    i4++;
                    z = false;
                }
            } else if (z) {
                i3 = (i3 * 10) + (cCharAt2 - '0');
            } else {
                i3 = cCharAt2 - '0';
                z = true;
            }
        }
        if (i > 0) {
            iArr[i - 1] = i3;
        }
        return iArr;
    }

    private void writeCompactedLongArray(Parcel parcel, long[] jArr, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            long j = jArr[i2];
            if (j < 0) {
                Slog.w(TAG, "Time val negative: " + j);
                j = 0L;
            }
            if (j <= 2147483647L) {
                parcel.writeInt((int) j);
            } else {
                parcel.writeInt(~((int) (2147483647L & (j >> 32))));
                parcel.writeInt((int) (j & 4294967295L));
            }
        }
    }

    private void readCompactedLongArray(Parcel parcel, int i, long[] jArr, int i2) {
        if (i <= 10) {
            parcel.readLongArray(jArr);
            return;
        }
        int length = jArr.length;
        if (i2 > length) {
            throw new RuntimeException("bad array lengths: got " + i2 + " array is " + length);
        }
        int i3 = 0;
        while (i3 < i2) {
            int i4 = parcel.readInt();
            if (i4 >= 0) {
                jArr[i3] = i4;
            } else {
                jArr[i3] = parcel.readInt() | ((~i4) << 32);
            }
            i3++;
        }
        while (i3 < length) {
            jArr[i3] = 0;
            i3++;
        }
    }

    void writeCommonString(Parcel parcel, String str) {
        Integer num = this.mCommonStringToIndex.get(str);
        if (num != null) {
            parcel.writeInt(num.intValue());
            return;
        }
        int size = this.mCommonStringToIndex.size();
        Integer numValueOf = Integer.valueOf(size);
        this.mCommonStringToIndex.put(str, numValueOf);
        numValueOf.getClass();
        parcel.writeInt(~size);
        parcel.writeString(str);
    }

    String readCommonString(Parcel parcel, int i) {
        if (i <= 9) {
            return parcel.readString();
        }
        int i2 = parcel.readInt();
        if (i2 >= 0) {
            return this.mIndexToCommonString.get(i2);
        }
        int i3 = ~i2;
        String string = parcel.readString();
        while (this.mIndexToCommonString.size() <= i3) {
            this.mIndexToCommonString.add(null);
        }
        this.mIndexToCommonString.set(i3, string);
        return string;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        writeToParcel(parcel, SystemClock.uptimeMillis(), i);
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
            SparseArray<ProcessState> sparseArrayValueAt = map.valueAt(i2);
            int size2 = sparseArrayValueAt.size();
            for (int i3 = 0; i3 < size2; i3++) {
                sparseArrayValueAt.valueAt(i3).commitStateTime(j);
            }
        }
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> map2 = this.mPackages.getMap();
        int size3 = map2.size();
        int i4 = 0;
        while (i4 < size3) {
            SparseArray<LongSparseArray<PackageState>> sparseArrayValueAt2 = map2.valueAt(i4);
            int size4 = sparseArrayValueAt2.size();
            for (int i5 = 0; i5 < size4; i5++) {
                LongSparseArray<PackageState> longSparseArrayValueAt = sparseArrayValueAt2.valueAt(i5);
                int size5 = longSparseArrayValueAt.size();
                int i6 = 0;
                while (i6 < size5) {
                    PackageState packageStateValueAt = longSparseArrayValueAt.valueAt(i6);
                    int i7 = i4;
                    int size6 = packageStateValueAt.mProcesses.size();
                    SparseArray<LongSparseArray<PackageState>> sparseArray = sparseArrayValueAt2;
                    int i8 = 0;
                    while (i8 < size6) {
                        int i9 = size6;
                        ProcessState processStateValueAt = packageStateValueAt.mProcesses.valueAt(i8);
                        int i10 = i8;
                        if (processStateValueAt.getCommonProcess() != processStateValueAt) {
                            processStateValueAt.commitStateTime(j);
                        }
                        i8 = i10 + 1;
                        size6 = i9;
                    }
                    int i11 = 0;
                    for (int size7 = packageStateValueAt.mServices.size(); i11 < size7; size7 = size7) {
                        packageStateValueAt.mServices.valueAt(i11).commitStateTime(j);
                        i11++;
                    }
                    int i12 = 0;
                    for (int size8 = packageStateValueAt.mAssociations.size(); i12 < size8; size8 = size8) {
                        packageStateValueAt.mAssociations.valueAt(i12).commitStateTime(j);
                        i12++;
                    }
                    i6++;
                    i4 = i7;
                    sparseArrayValueAt2 = sparseArray;
                }
            }
            i4++;
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
        int i13 = this.mMemFactor;
        if (i13 != -1) {
            long[] jArr = this.mMemFactorDurations;
            jArr[i13] = jArr[i13] + (j - this.mStartTime);
            this.mStartTime = j;
        }
        long[] jArr2 = this.mMemFactorDurations;
        writeCompactedLongArray(parcel, jArr2, jArr2.length);
        this.mSysMemUsage.writeToParcel(parcel);
        int size9 = this.mUidStates.size();
        parcel.writeInt(size9);
        for (int i14 = 0; i14 < size9; i14++) {
            parcel.writeInt(this.mUidStates.keyAt(i14));
            this.mUidStates.valueAt(i14).writeToParcel(parcel, j);
        }
        parcel.writeInt(size);
        for (int i15 = 0; i15 < size; i15++) {
            writeCommonString(parcel, map.keyAt(i15));
            SparseArray<ProcessState> sparseArrayValueAt3 = map.valueAt(i15);
            int size10 = sparseArrayValueAt3.size();
            parcel.writeInt(size10);
            for (int i16 = 0; i16 < size10; i16++) {
                parcel.writeInt(sparseArrayValueAt3.keyAt(i16));
                ProcessState processStateValueAt2 = sparseArrayValueAt3.valueAt(i16);
                writeCommonString(parcel, processStateValueAt2.getPackage());
                parcel.writeLong(processStateValueAt2.getVersion());
                processStateValueAt2.writeToParcel(parcel, j);
            }
        }
        parcel.writeInt(size3);
        int i17 = 0;
        while (i17 < size3) {
            writeCommonString(parcel, map2.keyAt(i17));
            SparseArray<LongSparseArray<PackageState>> sparseArrayValueAt4 = map2.valueAt(i17);
            int size11 = sparseArrayValueAt4.size();
            parcel.writeInt(size11);
            for (int i18 = 0; i18 < size11; i18++) {
                parcel.writeInt(sparseArrayValueAt4.keyAt(i18));
                LongSparseArray<PackageState> longSparseArrayValueAt2 = sparseArrayValueAt4.valueAt(i18);
                int size12 = longSparseArrayValueAt2.size();
                parcel.writeInt(size12);
                int i19 = 0;
                while (i19 < size12) {
                    parcel.writeLong(longSparseArrayValueAt2.keyAt(i19));
                    PackageState packageStateValueAt2 = longSparseArrayValueAt2.valueAt(i19);
                    int size13 = packageStateValueAt2.mProcesses.size();
                    parcel.writeInt(size13);
                    int i20 = 0;
                    while (i20 < size13) {
                        int i21 = i17;
                        writeCommonString(parcel, packageStateValueAt2.mProcesses.keyAt(i20));
                        ProcessState processStateValueAt3 = packageStateValueAt2.mProcesses.valueAt(i20);
                        SparseArray<LongSparseArray<PackageState>> sparseArray2 = sparseArrayValueAt4;
                        if (processStateValueAt3.getCommonProcess() == processStateValueAt3) {
                            parcel.writeInt(0);
                        } else {
                            parcel.writeInt(1);
                            processStateValueAt3.writeToParcel(parcel, j);
                        }
                        i20++;
                        i17 = i21;
                        sparseArrayValueAt4 = sparseArray2;
                    }
                    int i22 = i17;
                    SparseArray<LongSparseArray<PackageState>> sparseArray3 = sparseArrayValueAt4;
                    int size14 = packageStateValueAt2.mServices.size();
                    parcel.writeInt(size14);
                    for (int i23 = 0; i23 < size14; i23++) {
                        parcel.writeString(packageStateValueAt2.mServices.keyAt(i23));
                        ServiceState serviceStateValueAt = packageStateValueAt2.mServices.valueAt(i23);
                        writeCommonString(parcel, serviceStateValueAt.getProcessName());
                        serviceStateValueAt.writeToParcel(parcel, j);
                    }
                    int size15 = packageStateValueAt2.mAssociations.size();
                    parcel.writeInt(size15);
                    for (int i24 = 0; i24 < size15; i24++) {
                        writeCommonString(parcel, packageStateValueAt2.mAssociations.keyAt(i24));
                        AssociationState associationStateValueAt = packageStateValueAt2.mAssociations.valueAt(i24);
                        writeCommonString(parcel, associationStateValueAt.getProcessName());
                        associationStateValueAt.writeToParcel(this, parcel, j);
                    }
                    i19++;
                    i17 = i22;
                    sparseArrayValueAt4 = sparseArray3;
                }
            }
            i17++;
        }
        int size16 = this.mPageTypeLabels.size();
        parcel.writeInt(size16);
        for (int i25 = 0; i25 < size16; i25++) {
            parcel.writeInt(this.mPageTypeNodes.get(i25).intValue());
            parcel.writeString(this.mPageTypeZones.get(i25));
            parcel.writeString(this.mPageTypeLabels.get(i25));
            parcel.writeIntArray(this.mPageTypeSizes.get(i25));
        }
        this.mCommonStringToIndex = null;
    }

    private boolean readCheckedInt(Parcel parcel, int i, String str) {
        int i2 = parcel.readInt();
        if (i2 == i) {
            return true;
        }
        this.mReadError = "bad " + str + ": " + i2;
        return false;
    }

    static byte[] readFully(InputStream inputStream, int[] iArr) throws IOException {
        int iAvailable = inputStream.available();
        byte[] bArr = new byte[iAvailable > 0 ? iAvailable + 1 : 16384];
        int i = 0;
        while (true) {
            int i2 = inputStream.read(bArr, i, bArr.length - i);
            if (i2 < 0) {
                iArr[0] = i;
                return bArr;
            }
            i += i2;
            if (i >= bArr.length) {
                byte[] bArr2 = new byte[i + 16384];
                System.arraycopy(bArr, 0, bArr2, 0, i);
                bArr = bArr2;
            }
        }
    }

    public void read(InputStream inputStream) throws Throwable {
        try {
            int[] iArr = new int[1];
            byte[] fully = readFully(inputStream, iArr);
            Parcel parcelObtain = Parcel.obtain();
            parcelObtain.unmarshall(fully, 0, iArr[0]);
            parcelObtain.setDataPosition(0);
            inputStream.close();
            readFromParcel(parcelObtain);
        } catch (IOException e) {
            this.mReadError = "caught exception: " + e;
        }
    }

    public void readFromParcel(Parcel parcel) throws Throwable {
        int i;
        int i2;
        long j;
        int i3;
        boolean z;
        ProcessState processState;
        String str;
        String str2;
        ProcessState processState2;
        int i4 = 0;
        boolean z2 = true;
        boolean z3 = this.mPackages.getMap().size() > 0 || this.mProcesses.getMap().size() > 0 || this.mUidStates.size() > 0;
        if (z3) {
            resetSafely();
        }
        if (readCheckedInt(parcel, MAGIC, "magic number")) {
            int i5 = parcel.readInt();
            if (i5 != 41) {
                this.mReadError = "bad version: " + i5;
                return;
            }
            if (readCheckedInt(parcel, 16, "state count") && readCheckedInt(parcel, 8, "adj count") && readCheckedInt(parcel, 10, "pss count") && readCheckedInt(parcel, 16, "sys mem usage count") && readCheckedInt(parcel, 4096, "longs size")) {
                this.mIndexToCommonString = new ArrayList<>();
                this.mNumAggregated = parcel.readInt();
                this.mTimePeriodStartClock = parcel.readLong();
                buildTimePeriodStartClockStr();
                this.mTimePeriodStartRealtime = parcel.readLong();
                this.mTimePeriodEndRealtime = parcel.readLong();
                this.mTimePeriodStartUptime = parcel.readLong();
                this.mTimePeriodEndUptime = parcel.readLong();
                this.mInternalSinglePssCount = parcel.readLong();
                this.mInternalSinglePssTime = parcel.readLong();
                this.mInternalAllMemPssCount = parcel.readLong();
                this.mInternalAllMemPssTime = parcel.readLong();
                this.mInternalAllPollPssCount = parcel.readLong();
                this.mInternalAllPollPssTime = parcel.readLong();
                this.mExternalPssCount = parcel.readLong();
                this.mExternalPssTime = parcel.readLong();
                this.mExternalSlowPssCount = parcel.readLong();
                this.mExternalSlowPssTime = parcel.readLong();
                this.mRuntime = parcel.readString();
                this.mHasSwappedOutPss = parcel.readInt() != 0;
                this.mFlags = parcel.readInt();
                this.mTableData.readFromParcel(parcel);
                long[] jArr = this.mMemFactorDurations;
                readCompactedLongArray(parcel, i5, jArr, jArr.length);
                if (this.mSysMemUsage.readFromParcel(parcel)) {
                    int i6 = parcel.readInt();
                    for (int i7 = 0; i7 < i6; i7++) {
                        int i8 = parcel.readInt();
                        UidState uidState = new UidState(this, i8);
                        if (!uidState.readFromParcel(parcel)) {
                            return;
                        }
                        this.mUidStates.put(i8, uidState);
                    }
                    int i9 = parcel.readInt();
                    if (i9 < 0) {
                        this.mReadError = "bad process count: " + i9;
                        return;
                    }
                    while (i9 > 0) {
                        int i10 = i9 - 1;
                        String commonString = readCommonString(parcel, i5);
                        if (commonString == null) {
                            this.mReadError = "bad process name";
                            return;
                        }
                        int i11 = parcel.readInt();
                        if (i11 < 0) {
                            this.mReadError = "bad uid count: " + i11;
                            return;
                        }
                        while (i11 > 0) {
                            int i12 = i11 - 1;
                            int i13 = parcel.readInt();
                            if (i13 < 0) {
                                this.mReadError = "bad uid: " + i13;
                                return;
                            }
                            String commonString2 = readCommonString(parcel, i5);
                            if (commonString2 == null) {
                                this.mReadError = "bad process package name";
                                return;
                            }
                            long j2 = parcel.readLong();
                            ProcessState processState3 = z3 ? this.mProcesses.get(commonString, i13) : null;
                            if (processState3 != null) {
                                if (!processState3.readFromParcel(parcel, i5, false)) {
                                    return;
                                }
                                ProcessState processState4 = processState3;
                                str2 = commonString;
                                processState2 = processState4;
                            } else {
                                str2 = commonString;
                                processState2 = new ProcessState(this, commonString2, i13, j2, str2);
                                if (!processState2.readFromParcel(parcel, i5, true)) {
                                    return;
                                }
                            }
                            this.mProcesses.put(str2, i13, processState2);
                            UidState uidState2 = this.mUidStates.get(i13);
                            if (uidState2 == null) {
                                uidState2 = new UidState(this, i13);
                                this.mUidStates.put(i13, uidState2);
                            }
                            uidState2.addProcess(processState2);
                            commonString = str2;
                            i11 = i12;
                        }
                        i9 = i10;
                    }
                    for (int i14 = 0; i14 < i6; i14++) {
                        this.mUidStates.valueAt(i14).updateCombinedState(-1L);
                    }
                    int i15 = parcel.readInt();
                    if (i15 < 0) {
                        this.mReadError = "bad package count: " + i15;
                        return;
                    }
                    while (i15 > 0) {
                        int i16 = i15 - 1;
                        String commonString3 = readCommonString(parcel, i5);
                        if (commonString3 == null) {
                            this.mReadError = "bad package name";
                            return;
                        }
                        int i17 = parcel.readInt();
                        if (i17 < 0) {
                            this.mReadError = "bad uid count: " + i17;
                            return;
                        }
                        while (i17 > 0) {
                            int i18 = i17 - 1;
                            int i19 = parcel.readInt();
                            if (i19 < 0) {
                                this.mReadError = "bad uid: " + i19;
                                return;
                            }
                            int i20 = parcel.readInt();
                            if (i20 < 0) {
                                this.mReadError = "bad versions count: " + i20;
                                return;
                            }
                            while (i20 > 0) {
                                int i21 = i20 - 1;
                                long j3 = parcel.readLong();
                                PackageState packageState = new PackageState(this, commonString3, i19, j3);
                                LongSparseArray<PackageState> longSparseArray = this.mPackages.get(commonString3, i19);
                                if (longSparseArray == null) {
                                    longSparseArray = new LongSparseArray<>();
                                    this.mPackages.put(commonString3, i19, longSparseArray);
                                }
                                longSparseArray.put(j3, packageState);
                                int i22 = parcel.readInt();
                                if (i22 < 0) {
                                    this.mReadError = "bad package process count: " + i22;
                                    return;
                                }
                                while (i22 > 0) {
                                    i22--;
                                    String commonString4 = readCommonString(parcel, i5);
                                    if (commonString4 == null) {
                                        this.mReadError = "bad package process name";
                                        return;
                                    }
                                    int i23 = parcel.readInt();
                                    ProcessState processState5 = this.mProcesses.get(commonString4, i19);
                                    if (processState5 == null) {
                                        this.mReadError = "no common proc: " + commonString4;
                                        return;
                                    }
                                    if (i23 != 0) {
                                        ProcessState processState6 = z3 ? packageState.mProcesses.get(commonString4) : null;
                                        if (processState6 != null) {
                                            j = j3;
                                            i3 = 0;
                                            if (!processState6.readFromParcel(parcel, i5, false)) {
                                                return;
                                            }
                                            i2 = i19;
                                            str = commonString4;
                                            processState = processState6;
                                            z = true;
                                        } else {
                                            j = j3;
                                            i3 = 0;
                                            i2 = i19;
                                            processState = new ProcessState(processState5, commonString3, i2, j, commonString4, 0L);
                                            str = commonString4;
                                            z = true;
                                            if (!processState.readFromParcel(parcel, i5, true)) {
                                                return;
                                            }
                                        }
                                        packageState.mProcesses.put(str, processState);
                                    } else {
                                        i2 = i19;
                                        j = j3;
                                        i3 = i4;
                                        z = true;
                                        packageState.mProcesses.put(commonString4, processState5);
                                    }
                                    z2 = z;
                                    i19 = i2;
                                    i4 = i3;
                                    j3 = j;
                                }
                                int i24 = i19;
                                int i25 = i4;
                                boolean z4 = z2;
                                int i26 = parcel.readInt();
                                if (i26 < 0) {
                                    this.mReadError = "bad package service count: " + i26;
                                    return;
                                }
                                while (i26 > 0) {
                                    int i27 = i26 - 1;
                                    String string = parcel.readString();
                                    if (string == null) {
                                        this.mReadError = "bad package service name";
                                        return;
                                    }
                                    String commonString5 = i5 > 9 ? readCommonString(parcel, i5) : null;
                                    ServiceState serviceState = z3 ? packageState.mServices.get(string) : null;
                                    PackageState packageState2 = packageState;
                                    if (serviceState == null) {
                                        i = i25;
                                        serviceState = new ServiceState(this, commonString3, string, commonString5, null);
                                    } else {
                                        i = i25;
                                    }
                                    String str3 = commonString3;
                                    if (!serviceState.readFromParcel(parcel)) {
                                        return;
                                    }
                                    packageState2.mServices.put(string, serviceState);
                                    i26 = i27;
                                    packageState = packageState2;
                                    commonString3 = str3;
                                    i25 = i;
                                }
                                PackageState packageState3 = packageState;
                                String str4 = commonString3;
                                int i28 = i25;
                                int i29 = parcel.readInt();
                                if (i29 < 0) {
                                    this.mReadError = "bad package association count: " + i29;
                                    return;
                                }
                                while (i29 > 0) {
                                    int i30 = i29 - 1;
                                    String commonString6 = readCommonString(parcel, i5);
                                    if (commonString6 == null) {
                                        this.mReadError = "bad package association name";
                                        return;
                                    }
                                    String commonString7 = readCommonString(parcel, i5);
                                    AssociationState associationState = z3 ? packageState3.mAssociations.get(commonString6) : null;
                                    if (associationState == null) {
                                        associationState = new AssociationState(this, packageState3, commonString6, commonString7, null);
                                    }
                                    String fromParcel = associationState.readFromParcel(this, parcel, i5);
                                    if (fromParcel != null) {
                                        this.mReadError = fromParcel;
                                        return;
                                    } else {
                                        packageState3.mAssociations.put(commonString6, associationState);
                                        i29 = i30;
                                    }
                                }
                                z2 = z4;
                                commonString3 = str4;
                                i19 = i24;
                                i20 = i21;
                                i4 = i28;
                            }
                            i17 = i18;
                        }
                        i15 = i16;
                    }
                    int i31 = parcel.readInt();
                    this.mPageTypeNodes.clear();
                    this.mPageTypeNodes.ensureCapacity(i31);
                    this.mPageTypeZones.clear();
                    this.mPageTypeZones.ensureCapacity(i31);
                    this.mPageTypeLabels.clear();
                    this.mPageTypeLabels.ensureCapacity(i31);
                    this.mPageTypeSizes.clear();
                    this.mPageTypeSizes.ensureCapacity(i31);
                    while (i4 < i31) {
                        this.mPageTypeNodes.add(Integer.valueOf(parcel.readInt()));
                        this.mPageTypeZones.add(parcel.readString());
                        this.mPageTypeLabels.add(parcel.readString());
                        this.mPageTypeSizes.add(parcel.createIntArray());
                        i4++;
                    }
                    this.mIndexToCommonString = null;
                }
            }
        }
    }

    public PackageState getPackageStateLocked(String str, int i, long j) {
        LongSparseArray<PackageState> longSparseArray = this.mPackages.get(str, i);
        if (longSparseArray == null) {
            longSparseArray = new LongSparseArray<>();
            this.mPackages.put(str, i, longSparseArray);
        }
        PackageState packageState = longSparseArray.get(j);
        if (packageState != null) {
            return packageState;
        }
        PackageState packageState2 = new PackageState(this, str, i, j);
        longSparseArray.put(j, packageState2);
        return packageState2;
    }

    public ProcessState getProcessStateLocked(String str, int i, long j, String str2) {
        return getProcessStateLocked(getPackageStateLocked(str, i, j), str2);
    }

    public ProcessState getProcessStateLocked(PackageState packageState, String str) {
        ProcessStats processStats;
        String str2;
        ProcessState processState;
        ProcessState processState2;
        ProcessState processState3 = packageState.mProcesses.get(str);
        if (processState3 != null) {
            return processState3;
        }
        ProcessState processState4 = this.mProcesses.get(str, packageState.mUid);
        if (processState4 == null) {
            processStats = this;
            ProcessState processState5 = new ProcessState(processStats, packageState.mPackageName, packageState.mUid, packageState.mVersionCode, str);
            str2 = str;
            processStats.mProcesses.put(str2, packageState.mUid, processState5);
            UidState uidState = processStats.mUidStates.get(packageState.mUid);
            if (uidState == null) {
                uidState = new UidState(processStats, packageState.mUid);
                processStats.mUidStates.put(packageState.mUid, uidState);
            }
            uidState.addProcess(processState5);
            processState = processState5;
        } else {
            processStats = this;
            str2 = str;
            processState = processState4;
        }
        if (!processState.isMultiPackage()) {
            if (!packageState.mPackageName.equals(processState.getPackage()) || packageState.mVersionCode != processState.getVersion()) {
                processState.setMultiPackage(true);
                long jUptimeMillis = SystemClock.uptimeMillis();
                PackageState packageStateLocked = processStats.getPackageStateLocked(processState.getPackage(), packageState.mUid, processState.getVersion());
                if (packageStateLocked != null) {
                    ProcessState processStateClone = processState.clone(jUptimeMillis);
                    packageStateLocked.mProcesses.put(processState.getName(), processStateClone);
                    for (int size = packageStateLocked.mServices.size() - 1; size >= 0; size--) {
                        ServiceState serviceStateValueAt = packageStateLocked.mServices.valueAt(size);
                        if (serviceStateValueAt.getProcess() == processState) {
                            serviceStateValueAt.setProcess(processStateClone);
                        }
                    }
                    for (int size2 = packageStateLocked.mAssociations.size() - 1; size2 >= 0; size2--) {
                        AssociationState associationStateValueAt = packageStateLocked.mAssociations.valueAt(size2);
                        if (associationStateValueAt.getProcess() == processState) {
                            associationStateValueAt.setProcess(processStateClone);
                        }
                    }
                } else {
                    Slog.w(TAG, "Cloning proc state: no package state " + processState.getPackage() + "/" + packageState.mUid + " for proc " + processState.getName());
                }
                processState2 = new ProcessState(processState, packageState.mPackageName, packageState.mUid, packageState.mVersionCode, str2, jUptimeMillis);
            }
            packageState.mProcesses.put(str2, processState);
            return processState;
        }
        processState2 = new ProcessState(processState, packageState.mPackageName, packageState.mUid, packageState.mVersionCode, str2, SystemClock.uptimeMillis());
        processState = processState2;
        packageState.mProcesses.put(str2, processState);
        return processState;
    }

    public ServiceState getServiceStateLocked(String str, int i, long j, String str2, String str3) {
        String str4;
        ProcessState processStateLocked;
        PackageState packageStateLocked = getPackageStateLocked(str, i, j);
        ServiceState serviceState = packageStateLocked.mServices.get(str3);
        if (serviceState != null) {
            return serviceState;
        }
        if (str2 != null) {
            processStateLocked = getProcessStateLocked(str, i, j, str2);
            str4 = str2;
        } else {
            str4 = str2;
            processStateLocked = null;
        }
        ServiceState serviceState2 = new ServiceState(this, str, str3, str4, processStateLocked);
        packageStateLocked.mServices.put(str3, serviceState2);
        return serviceState2;
    }

    public AssociationState getAssociationStateLocked(String str, int i, long j, String str2, String str3) {
        ProcessStats processStats;
        String str4;
        ProcessState processStateLocked;
        PackageState packageStateLocked = getPackageStateLocked(str, i, j);
        AssociationState associationState = packageStateLocked.mAssociations.get(str3);
        if (associationState != null) {
            return associationState;
        }
        if (str2 != null) {
            processStateLocked = getProcessStateLocked(str, i, j, str2);
            processStats = this;
            str4 = str2;
        } else {
            processStats = this;
            str4 = str2;
            processStateLocked = null;
        }
        AssociationState associationState2 = new AssociationState(processStats, packageStateLocked, str3, str4, processStateLocked);
        packageStateLocked.mAssociations.put(str3, associationState2);
        return associationState2;
    }

    public void updateTrackingAssociationsLocked(int i, long j) {
        for (int size = this.mTrackingAssociations.size() - 1; size >= 0; size--) {
            AssociationState.SourceState sourceState = this.mTrackingAssociations.get(size);
            if (sourceState.stopActiveIfNecessary(i, j)) {
                this.mTrackingAssociations.remove(size);
            } else {
                AssociationState associationState = sourceState.getAssociationState();
                if (associationState == null) {
                    Slog.wtf(TAG, sourceState.toString() + " shouldn't be in the tracking list.");
                } else {
                    ProcessState process = associationState.getProcess();
                    if (process == null) {
                        Slog.wtf(TAG, "Tracking association without process: " + sourceState + " in " + associationState);
                    } else {
                        int combinedState = process.getCombinedState() % 16;
                        if (sourceState.mProcState == combinedState) {
                            sourceState.startActive(j);
                        } else {
                            sourceState.stopActive(j);
                            if (sourceState.mProcState < combinedState) {
                                long jUptimeMillis = SystemClock.uptimeMillis();
                                if (this.mNextInverseProcStateWarningUptime > jUptimeMillis) {
                                    this.mSkippedInverseProcStateWarningCount++;
                                } else {
                                    Slog.w(TAG, "Tracking association " + sourceState + " whose proc state " + sourceState.mProcState + " is better than process " + process + " proc state " + combinedState + " (" + this.mSkippedInverseProcStateWarningCount + " skipped)");
                                    this.mSkippedInverseProcStateWarningCount = 0;
                                    this.mNextInverseProcStateWarningUptime = jUptimeMillis + 10000;
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

        AssociationDumpContainer(ProcessStats processStats, AssociationState associationState) {
            this.mState = associationState;
        }
    }

    static /* synthetic */ int lambda$static$0(AssociationDumpContainer associationDumpContainer, AssociationDumpContainer associationDumpContainer2) {
        int iCompareTo = associationDumpContainer.mState.getProcessName().compareTo(associationDumpContainer2.mState.getProcessName());
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (associationDumpContainer.mActiveTime != associationDumpContainer2.mActiveTime) {
            return associationDumpContainer.mActiveTime > associationDumpContainer2.mActiveTime ? -1 : 1;
        }
        if (associationDumpContainer.mTotalTime != associationDumpContainer2.mTotalTime) {
            return associationDumpContainer.mTotalTime > associationDumpContainer2.mTotalTime ? -1 : 1;
        }
        int iCompareTo2 = associationDumpContainer.mState.getName().compareTo(associationDumpContainer2.mState.getName());
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        return 0;
    }

    public void dumpLocked(PrintWriter printWriter, String str, long j, boolean z, boolean z2, boolean z3, boolean z4, int i) {
        boolean z5;
        long j2;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        boolean z6;
        int i2;
        String str8;
        int i3;
        ArrayMap<String, SparseArray<ProcessState>> arrayMap;
        boolean z7;
        int i4;
        int i5;
        String str9;
        SparseArray<ProcessState> sparseArray;
        String str10;
        String str11;
        String str12;
        ArrayMap<String, SparseArray<ProcessState>> arrayMap2;
        String str13;
        String str14;
        int i6;
        String str15;
        int i7;
        boolean z8;
        boolean z9;
        String str16;
        long j3;
        int i8;
        String str17;
        String str18;
        int i9;
        String str19;
        String str20;
        int i10;
        String str21;
        String str22;
        String str23;
        String str24;
        String str25;
        String str26;
        String str27;
        PackageState packageState;
        String str28;
        String str29;
        int i11;
        String str30;
        String str31;
        int i12;
        ArrayList arrayList;
        String str32;
        ProcessStats processStats;
        int i13;
        int i14;
        int i15;
        String str33;
        String str34;
        String str35;
        String str36;
        int i16;
        boolean z10;
        boolean z11;
        ProcessStats processStats2 = this;
        PrintWriter printWriter2 = printWriter;
        String str37 = str;
        long jDumpSingleTime = DumpUtils.dumpSingleTime(null, null, processStats2.mMemFactorDurations, processStats2.mMemFactor, processStats2.mStartTime, j);
        printWriter2.print("          Start time: ");
        printWriter2.print(DateFormat.format("yyyy-MM-dd HH:mm:ss", processStats2.mTimePeriodStartClock));
        printWriter2.println();
        printWriter2.print("        Total uptime: ");
        TimeUtils.formatDuration((processStats2.mRunning ? SystemClock.uptimeMillis() : processStats2.mTimePeriodEndUptime) - processStats2.mTimePeriodStartUptime, printWriter2);
        printWriter2.println();
        printWriter2.print("  Total elapsed time: ");
        TimeUtils.formatDuration((processStats2.mRunning ? SystemClock.elapsedRealtime() : processStats2.mTimePeriodEndRealtime) - processStats2.mTimePeriodStartRealtime, printWriter2);
        if ((processStats2.mFlags & 2) != 0) {
            printWriter2.print(" (shutdown)");
            z5 = false;
        } else {
            z5 = true;
        }
        if ((processStats2.mFlags & 4) != 0) {
            printWriter2.print(" (sysprops)");
            z5 = false;
        }
        if ((processStats2.mFlags & 1) != 0) {
            printWriter2.print(" (complete)");
            z5 = false;
        }
        if (z5) {
            printWriter2.print(" (partial)");
        }
        if (processStats2.mHasSwappedOutPss) {
            printWriter2.print(" (swapped-out-pss)");
        }
        printWriter2.print(' ');
        printWriter2.print(processStats2.mRuntime);
        printWriter2.println();
        printWriter2.print("     Aggregated over: ");
        printWriter2.println(processStats2.mNumAggregated);
        if (processStats2.mSysMemUsage.getKeyCount() > 0) {
            printWriter2.println();
            printWriter2.println("System memory usage:");
            processStats2.mSysMemUsage.dump(printWriter2, "  ", ALL_SCREEN_ADJ, ALL_MEM_ADJ);
        }
        int i17 = i & 14;
        String str38 = " / ";
        String str39 = "      (Not active: ";
        String str40 = " entries)";
        String str41 = "  * ";
        String str42 = NavigationBarInflaterView.KEY_CODE_END;
        String str43 = ":";
        if (i17 != 0) {
            ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> map = processStats2.mPackages.getMap();
            int i18 = 0;
            boolean z12 = false;
            while (i18 < map.size()) {
                String strKeyAt = map.keyAt(i18);
                SparseArray<LongSparseArray<PackageState>> sparseArrayValueAt = map.valueAt(i18);
                ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> arrayMap3 = map;
                int i19 = 0;
                while (i19 < sparseArrayValueAt.size()) {
                    int iKeyAt = sparseArrayValueAt.keyAt(i19);
                    int i20 = i19;
                    LongSparseArray<PackageState> longSparseArrayValueAt = sparseArrayValueAt.valueAt(i19);
                    String str44 = str39;
                    SparseArray<LongSparseArray<PackageState>> sparseArray2 = sparseArrayValueAt;
                    int i21 = 0;
                    while (i21 < longSparseArrayValueAt.size()) {
                        String str45 = str40;
                        long jKeyAt = longSparseArrayValueAt.keyAt(i21);
                        LongSparseArray<PackageState> longSparseArray = longSparseArrayValueAt;
                        PackageState packageStateValueAt = longSparseArrayValueAt.valueAt(i21);
                        long j4 = jDumpSingleTime;
                        int size = packageStateValueAt.mProcesses.size();
                        int size2 = packageStateValueAt.mServices.size();
                        int size3 = packageStateValueAt.mAssociations.size();
                        boolean z13 = str37 == null || str37.equals(strKeyAt);
                        if (z13) {
                            i6 = size3;
                            str15 = str42;
                            i7 = i18;
                            z8 = false;
                            z9 = false;
                        } else {
                            str15 = str42;
                            int i22 = 0;
                            while (true) {
                                i7 = i18;
                                if (i22 >= size) {
                                    z10 = false;
                                    break;
                                } else if (str37.equals(packageStateValueAt.mProcesses.valueAt(i22).getName())) {
                                    z10 = true;
                                    break;
                                } else {
                                    i22++;
                                    i18 = i7;
                                }
                            }
                            if (z10) {
                                i6 = size3;
                                z9 = z10;
                                z8 = false;
                            } else {
                                int i23 = 0;
                                while (true) {
                                    i6 = size3;
                                    if (i23 >= size3) {
                                        z11 = false;
                                        break;
                                    } else if (packageStateValueAt.mAssociations.valueAt(i23).hasProcessOrPackage(str37)) {
                                        z11 = true;
                                        break;
                                    } else {
                                        i23++;
                                        size3 = i6;
                                    }
                                }
                                if (z11) {
                                    z8 = z11;
                                    z9 = z10;
                                } else {
                                    str18 = str41;
                                    str25 = str43;
                                    str26 = str38;
                                    str17 = strKeyAt;
                                    str23 = str44;
                                    str20 = str45;
                                    j3 = j4;
                                    str27 = str15;
                                    i10 = iKeyAt;
                                    i9 = i21;
                                    str24 = str37;
                                    jDumpSingleTime = j3;
                                    str42 = str27;
                                    str37 = str24;
                                    strKeyAt = str17;
                                    longSparseArrayValueAt = longSparseArray;
                                    i18 = i7;
                                    str41 = str18;
                                    str40 = str20;
                                    str43 = str25;
                                    str38 = str26;
                                    i21 = i9 + 1;
                                    iKeyAt = i10;
                                    str44 = str23;
                                }
                            }
                        }
                        if (size > 0 || size2 > 0 || i6 > 0) {
                            if (!z12) {
                                printWriter2.println();
                                printWriter2.println("Per-Package Stats:");
                                z12 = true;
                            }
                            printWriter2.print(str41);
                            printWriter2.print(strKeyAt);
                            printWriter2.print(str38);
                            UserHandle.formatUid(printWriter2, iKeyAt);
                            printWriter2.print(" / v");
                            printWriter2.print(jKeyAt);
                            printWriter2.println(str43);
                        }
                        boolean z14 = z12;
                        if ((i & 2) == 0 || z8) {
                            String str46 = strKeyAt;
                            str16 = str43;
                            j3 = j4;
                            i8 = size2;
                            str17 = str46;
                            str18 = str41;
                            i9 = i21;
                            str19 = str44;
                            str20 = str45;
                            i10 = iKeyAt;
                            str21 = str38;
                            str22 = str15;
                        } else if (!z || z3) {
                            str18 = str41;
                            String str47 = str43;
                            j3 = j4;
                            i9 = i21;
                            str19 = str44;
                            i8 = size2;
                            i10 = iKeyAt;
                            str17 = strKeyAt;
                            String str48 = str45;
                            str21 = str38;
                            str22 = str15;
                            int i24 = 0;
                            while (i24 < size) {
                                ProcessState processStateValueAt = packageStateValueAt.mProcesses.valueAt(i24);
                                if (!z13 && !str37.equals(processStateValueAt.getName())) {
                                    String str49 = str47;
                                    str35 = str48;
                                    str36 = str49;
                                    i16 = size;
                                } else if (z4 && !processStateValueAt.isInUse()) {
                                    printWriter2.print(str19);
                                    printWriter2.print(packageStateValueAt.mProcesses.keyAt(i24));
                                    printWriter2.println(str22);
                                    String str492 = str47;
                                    str35 = str48;
                                    str36 = str492;
                                    i16 = size;
                                } else {
                                    printWriter2.print("      Process ");
                                    printWriter2.print(packageStateValueAt.mProcesses.keyAt(i24));
                                    if (processStateValueAt.getCommonProcess().isMultiPackage()) {
                                        printWriter2.print(" (multi, ");
                                    } else {
                                        printWriter2.print(" (unique, ");
                                    }
                                    printWriter2.print(processStateValueAt.getDurationsBucketCount());
                                    printWriter2.print(str48);
                                    printWriter2.println(str47);
                                    int[] iArr = ALL_SCREEN_ADJ;
                                    int i25 = size;
                                    int[] iArr2 = ALL_MEM_ADJ;
                                    int[] iArr3 = ALL_PROC_STATES;
                                    String str50 = str47;
                                    str35 = str48;
                                    str36 = str50;
                                    i16 = i25;
                                    processStateValueAt.dumpProcessState(printWriter2, "        ", iArr, iArr2, iArr3, j);
                                    printWriter2 = printWriter;
                                    processStateValueAt.dumpPss(printWriter2, "        ", iArr, iArr2, iArr3, j);
                                    long j5 = j3;
                                    processStateValueAt.dumpInternalLocked(printWriter2, "        ", str37, j5, j, z3);
                                    j3 = j5;
                                }
                                i24++;
                                String str51 = str35;
                                str47 = str36;
                                str48 = str51;
                                size = i16;
                            }
                            String str52 = str47;
                            str20 = str48;
                            str16 = str52;
                        } else {
                            ArrayList arrayList2 = new ArrayList();
                            for (int i26 = 0; i26 < size; i26++) {
                                ProcessState processStateValueAt2 = packageStateValueAt.mProcesses.valueAt(i26);
                                if ((z13 || str37.equals(processStateValueAt2.getName())) && (!z4 || processStateValueAt2.isInUse())) {
                                    arrayList2.add(processStateValueAt2);
                                }
                            }
                            str18 = str41;
                            String str53 = str43;
                            i9 = i21;
                            str19 = str44;
                            i8 = size2;
                            i10 = iKeyAt;
                            str17 = strKeyAt;
                            str21 = str38;
                            str22 = str15;
                            DumpUtils.dumpProcessSummaryLocked(printWriter, "      ", "Prc ", arrayList2, ALL_SCREEN_ADJ, ALL_MEM_ADJ, NON_CACHED_PROC_STATES, j, j4);
                            j3 = j4;
                            str20 = str45;
                            str16 = str53;
                            printWriter2 = printWriter;
                        }
                        String str54 = "        Process: ";
                        if ((i & 4) != 0 && !z8) {
                            int i27 = 0;
                            while (true) {
                                int i28 = i8;
                                if (i27 >= i28) {
                                    break;
                                }
                                ServiceState serviceStateValueAt = packageStateValueAt.mServices.valueAt(i27);
                                if (!z13 && !str37.equals(serviceStateValueAt.getProcessName())) {
                                    i8 = i28;
                                    i15 = i27;
                                    str33 = str19;
                                    str34 = str54;
                                } else if (z4 && !serviceStateValueAt.isInUse()) {
                                    printWriter2.print("      (Not active service: ");
                                    printWriter2.print(packageStateValueAt.mServices.keyAt(i27));
                                    printWriter2.println(str22);
                                    i8 = i28;
                                    i15 = i27;
                                    str33 = str19;
                                    str34 = str54;
                                } else {
                                    if (z3) {
                                        printWriter2.print("      Service ");
                                    } else {
                                        printWriter2.print("      * Svc ");
                                    }
                                    printWriter2.print(packageStateValueAt.mServices.keyAt(i27));
                                    printWriter2.println(str16);
                                    printWriter2.print(str54);
                                    printWriter2.println(serviceStateValueAt.getProcessName());
                                    i8 = i28;
                                    i15 = i27;
                                    long j6 = j3;
                                    str33 = str19;
                                    str34 = str54;
                                    serviceStateValueAt.dumpStats(printWriter2, "        ", "          ", "    ", j, j6, z, z3);
                                    j3 = j6;
                                }
                                i27 = i15 + 1;
                                str54 = str34;
                                str19 = str33;
                            }
                        }
                        long j7 = j;
                        str23 = str19;
                        String str55 = str54;
                        if ((i & 8) != 0) {
                            int i29 = i6;
                            ArrayList arrayList3 = new ArrayList(i29);
                            int i30 = 0;
                            while (i30 < i29) {
                                AssociationState associationStateValueAt = packageStateValueAt.mAssociations.valueAt(i30);
                                if (z13 || str37.equals(associationStateValueAt.getProcessName()) || (z8 && associationStateValueAt.hasProcessOrPackage(str37))) {
                                    AssociationDumpContainer associationDumpContainer = new AssociationDumpContainer(this, associationStateValueAt);
                                    i13 = i29;
                                    associationDumpContainer.mSources = AssociationState.createSortedAssociations(j7, j3, associationStateValueAt.mSources);
                                    i14 = i30;
                                    associationDumpContainer.mTotalTime = associationStateValueAt.getTotalDuration(j7);
                                    associationDumpContainer.mActiveTime = associationStateValueAt.getActiveDuration(j7);
                                    arrayList3.add(associationDumpContainer);
                                } else {
                                    i13 = i29;
                                    i14 = i30;
                                }
                                i30 = i14 + 1;
                                i29 = i13;
                            }
                            ProcessStats processStats3 = this;
                            Collections.sort(arrayList3, ASSOCIATION_COMPARATOR);
                            int size4 = arrayList3.size();
                            int i31 = 0;
                            while (i31 < size4) {
                                AssociationDumpContainer associationDumpContainer2 = (AssociationDumpContainer) arrayList3.get(i31);
                                ArrayList arrayList4 = arrayList3;
                                AssociationState associationState = associationDumpContainer2.mState;
                                if (z4 && !associationState.isInUse()) {
                                    printWriter2.print("      (Not active association: ");
                                    printWriter2.print(packageStateValueAt.mAssociations.keyAt(i31));
                                    printWriter2.println(str22);
                                    packageState = packageStateValueAt;
                                    i12 = size4;
                                    i11 = i31;
                                    arrayList = arrayList4;
                                    processStats = processStats3;
                                    str28 = str16;
                                    str29 = str21;
                                    str30 = str22;
                                    str31 = str55;
                                    str32 = str37;
                                } else {
                                    if (z3) {
                                        packageState = packageStateValueAt;
                                        printWriter2.print("      Association ");
                                    } else {
                                        packageState = packageStateValueAt;
                                        printWriter2.print("      * Asc ");
                                    }
                                    printWriter2.print(associationDumpContainer2.mState.getName());
                                    printWriter2.println(str16);
                                    printWriter2.print(str55);
                                    printWriter2.println(associationState.getProcessName());
                                    str28 = str16;
                                    str29 = str21;
                                    i11 = i31;
                                    str30 = str22;
                                    str31 = str55;
                                    i12 = size4;
                                    arrayList = arrayList4;
                                    str32 = str37;
                                    processStats = this;
                                    associationState.dumpStats(printWriter2, "        ", "          ", "    ", associationDumpContainer2.mSources, j7, j3, (!z8 || z13 || z9 || associationState.getProcessName().equals(str37)) ? null : str37, z2, z3);
                                }
                                i31 = i11 + 1;
                                j7 = j;
                                processStats3 = processStats;
                                str22 = str30;
                                str37 = str32;
                                str55 = str31;
                                packageStateValueAt = packageState;
                                arrayList3 = arrayList;
                                size4 = i12;
                                str16 = str28;
                                str21 = str29;
                            }
                        }
                        str24 = str37;
                        str25 = str16;
                        str26 = str21;
                        str27 = str22;
                        z12 = z14;
                        jDumpSingleTime = j3;
                        str42 = str27;
                        str37 = str24;
                        strKeyAt = str17;
                        longSparseArrayValueAt = longSparseArray;
                        i18 = i7;
                        str41 = str18;
                        str40 = str20;
                        str43 = str25;
                        str38 = str26;
                        i21 = i9 + 1;
                        iKeyAt = i10;
                        str44 = str23;
                    }
                    i19 = i20 + 1;
                    sparseArrayValueAt = sparseArray2;
                    strKeyAt = strKeyAt;
                    i18 = i18;
                    str39 = str44;
                    str43 = str43;
                }
                processStats2 = this;
                str43 = str43;
                i18++;
                map = arrayMap3;
            }
        }
        String str56 = str39;
        String str57 = str40;
        String str58 = str41;
        String str59 = str42;
        String str60 = str43;
        String str61 = str37;
        String str62 = str38;
        long j8 = jDumpSingleTime;
        if ((i & 1) != 0) {
            ArrayMap<String, SparseArray<ProcessState>> map2 = processStats2.mProcesses.getMap();
            int i32 = 0;
            int i33 = 0;
            int i34 = 0;
            boolean z15 = false;
            while (i32 < map2.size()) {
                String strKeyAt2 = map2.keyAt(i32);
                SparseArray<ProcessState> sparseArrayValueAt2 = map2.valueAt(i32);
                long j9 = j8;
                int i35 = 0;
                while (i35 < sparseArrayValueAt2.size()) {
                    int iKeyAt2 = sparseArrayValueAt2.keyAt(i35);
                    int i36 = i34 + 1;
                    ProcessState processStateValueAt3 = sparseArrayValueAt2.valueAt(i35);
                    if (processStateValueAt3.hasAnyData() && processStateValueAt3.isMultiPackage()) {
                        if (str61 == null || str61.equals(strKeyAt2)) {
                            arrayMap = map2;
                        } else {
                            arrayMap = map2;
                            if (!str61.equals(processStateValueAt3.getPackage())) {
                            }
                            str58 = str11;
                            str56 = str12;
                            i34 = i36;
                            i32 = i5;
                            strKeyAt2 = str9;
                            sparseArrayValueAt2 = sparseArray;
                            str60 = str10;
                            str57 = str13;
                            i35 = i4 + 1;
                            str61 = str14;
                            map2 = arrayMap2;
                        }
                        int i37 = i33 + 1;
                        printWriter2.println();
                        if (z15) {
                            z7 = z15;
                        } else {
                            printWriter2.println("Multi-Package Common Processes:");
                            z7 = true;
                        }
                        if (z4 && !processStateValueAt3.isInUse()) {
                            String str63 = str56;
                            printWriter2.print(str63);
                            printWriter2.print(strKeyAt2);
                            printWriter2.println(str59);
                            arrayMap2 = arrayMap;
                            i5 = i32;
                            str9 = strKeyAt2;
                            sparseArray = sparseArrayValueAt2;
                            i4 = i35;
                            str14 = str61;
                            str11 = str58;
                            str13 = str57;
                            str10 = str60;
                            str12 = str63;
                        } else {
                            String str64 = str58;
                            printWriter2.print(str64);
                            printWriter2.print(strKeyAt2);
                            String str65 = str62;
                            printWriter2.print(str65);
                            UserHandle.formatUid(printWriter2, iKeyAt2);
                            printWriter2.print(" (");
                            printWriter2.print(processStateValueAt3.getDurationsBucketCount());
                            String str66 = str57;
                            printWriter2.print(str66);
                            i4 = i35;
                            String str67 = str60;
                            printWriter2.println(str67);
                            int[] iArr4 = ALL_SCREEN_ADJ;
                            int[] iArr5 = ALL_MEM_ADJ;
                            int[] iArr6 = ALL_PROC_STATES;
                            i5 = i32;
                            str9 = strKeyAt2;
                            sparseArray = sparseArrayValueAt2;
                            str10 = str67;
                            str62 = str65;
                            str11 = str64;
                            str12 = str56;
                            arrayMap2 = arrayMap;
                            processStateValueAt3.dumpProcessState(printWriter2, "        ", iArr4, iArr5, iArr6, j);
                            printWriter2 = printWriter;
                            processStateValueAt3.dumpPss(printWriter2, "        ", iArr4, iArr5, iArr6, j);
                            str13 = str66;
                            processStateValueAt3.dumpInternalLocked(printWriter2, "        ", str, j9, j, z3);
                            str14 = str;
                        }
                        i33 = i37;
                        z15 = z7;
                        str58 = str11;
                        str56 = str12;
                        i34 = i36;
                        i32 = i5;
                        strKeyAt2 = str9;
                        sparseArrayValueAt2 = sparseArray;
                        str60 = str10;
                        str57 = str13;
                        i35 = i4 + 1;
                        str61 = str14;
                        map2 = arrayMap2;
                    } else {
                        arrayMap = map2;
                    }
                    arrayMap2 = arrayMap;
                    i5 = i32;
                    str9 = strKeyAt2;
                    sparseArray = sparseArrayValueAt2;
                    i4 = i35;
                    str14 = str61;
                    str11 = str58;
                    str13 = str57;
                    str12 = str56;
                    str10 = str60;
                    str58 = str11;
                    str56 = str12;
                    i34 = i36;
                    i32 = i5;
                    strKeyAt2 = str9;
                    sparseArrayValueAt2 = sparseArray;
                    str60 = str10;
                    str57 = str13;
                    i35 = i4 + 1;
                    str61 = str14;
                    map2 = arrayMap2;
                }
                i32++;
                str57 = str57;
                str61 = str61;
                j8 = j9;
            }
            j2 = j8;
            str2 = str61;
            str3 = str58;
            str4 = str57;
            str5 = str56;
            str6 = str60;
            printWriter2.print("  Total procs: ");
            printWriter2.print(i33);
            printWriter2.print(" shown of ");
            printWriter2.print(i34);
            printWriter2.println(" total");
        } else {
            j2 = j8;
            str2 = str61;
            str3 = str58;
            str4 = str57;
            str5 = str56;
            str6 = str60;
        }
        if ((i & 16) != 0) {
            SparseArray<UidState> sparseArray3 = processStats2.mUidStates;
            int size5 = sparseArray3.size();
            int i38 = 0;
            int i39 = 0;
            int i40 = 0;
            boolean z16 = false;
            while (i38 < size5) {
                int iKeyAt3 = sparseArray3.keyAt(i38);
                SparseArray<UidState> sparseArray4 = sparseArray3;
                UidState uidStateValueAt = sparseArray3.valueAt(i38);
                int i41 = i40 + 1;
                if (str2 == null || uidStateValueAt.hasPackage(str2)) {
                    int i42 = i39 + 1;
                    printWriter2.println();
                    if (z16) {
                        z6 = z16;
                    } else {
                        printWriter2.println("Per-UID Stats:");
                        z6 = true;
                    }
                    if (z4 && !uidStateValueAt.isInUse()) {
                        printWriter2.print(str5);
                        printWriter2.print(UserHandle.formatUid(iKeyAt3));
                        printWriter2.println(str59);
                        i3 = size5;
                        i2 = i38;
                        str8 = str6;
                    } else {
                        printWriter2.print(str3);
                        UserHandle.formatUid(printWriter2, iKeyAt3);
                        printWriter2.print(" (");
                        printWriter2.print(uidStateValueAt.getDurationsBucketCount());
                        String str68 = str4;
                        printWriter2.print(str68);
                        printWriter2.println(str6);
                        i2 = i38;
                        str4 = str68;
                        str8 = str6;
                        i3 = size5;
                        uidStateValueAt.dumpState(printWriter2, "        ", ALL_SCREEN_ADJ, ALL_MEM_ADJ, ALL_PROC_STATES, j);
                    }
                    i39 = i42;
                    z16 = z6;
                } else {
                    i3 = size5;
                    i2 = i38;
                    str8 = str6;
                }
                i38 = i2 + 1;
                str6 = str8;
                i40 = i41;
                sparseArray3 = sparseArray4;
                size5 = i3;
                str2 = str;
            }
            str7 = str6;
            printWriter2.print("  Total UIDs: ");
            printWriter2.print(i39);
            printWriter2.print(" shown of ");
            printWriter2.print(i40);
            printWriter2.println(" total");
        } else {
            str7 = str6;
        }
        if (z3) {
            printWriter2.println();
            if (processStats2.mTrackingAssociations.size() > 0) {
                printWriter2.println();
                printWriter2.println("Tracking associations:");
                for (int i43 = 0; i43 < processStats2.mTrackingAssociations.size(); i43++) {
                    AssociationState.SourceState sourceState = processStats2.mTrackingAssociations.get(i43);
                    AssociationState associationState2 = sourceState.getAssociationState();
                    if (associationState2 == null) {
                        Slog.wtf(TAG, sourceState.toString() + " shouldn't be in the tracking list.");
                    } else {
                        printWriter2.print("  #");
                        printWriter2.print(i43);
                        printWriter2.print(": ");
                        printWriter2.print(associationState2.getProcessName());
                        printWriter2.print("/");
                        UserHandle.formatUid(printWriter2, associationState2.getUid());
                        printWriter2.print(" <- ");
                        printWriter2.print(sourceState.getProcessName());
                        printWriter2.print("/");
                        UserHandle.formatUid(printWriter2, sourceState.getUid());
                        printWriter2.println(str7);
                        printWriter2.print("    Tracking for: ");
                        TimeUtils.formatDuration(j - sourceState.mTrackingUptime, printWriter2);
                        printWriter2.println();
                        printWriter2.print("    Component: ");
                        printWriter2.print(new ComponentName(associationState2.getPackage(), associationState2.getName()).flattenToShortString());
                        printWriter2.println();
                        printWriter2.print("    Proc state: ");
                        if (sourceState.mProcState != -1) {
                            printWriter2.print(DumpUtils.STATE_NAMES[sourceState.mProcState]);
                        } else {
                            printWriter2.print("--");
                        }
                        printWriter2.print(" #");
                        printWriter2.println(sourceState.mProcStateSeq);
                        printWriter2.print("    Process: ");
                        printWriter2.println(associationState2.getProcess());
                        if (sourceState.mActiveCount > 0) {
                            printWriter2.print("    Active count ");
                            printWriter2.print(sourceState.mActiveCount);
                            printWriter2.print(": ");
                            PrintWriter printWriter3 = printWriter2;
                            AssociationState.dumpActiveDurationSummary(printWriter3, sourceState, j2, j, z3);
                            printWriter2 = printWriter3;
                            printWriter2.println();
                        }
                    }
                }
            }
        }
        printWriter2.println();
        if (z) {
            printWriter2.println("Process summary:");
            PrintWriter printWriter4 = printWriter2;
            processStats2.dumpSummaryLocked(printWriter4, str, j, z4);
            printWriter2 = printWriter4;
        } else {
            processStats2.dumpTotalsLocked(printWriter2, j);
        }
        if (z3) {
            printWriter2.println();
            printWriter2.println("Internal state:");
            printWriter2.print("  mRunning=");
            printWriter2.println(processStats2.mRunning);
        }
        if (str == null) {
            dumpFragmentationLocked(printWriter);
        }
    }

    public void dumpSummaryLocked(PrintWriter printWriter, String str, long j, boolean z) {
        dumpFilteredSummaryLocked(printWriter, null, "  ", null, ALL_SCREEN_ADJ, ALL_MEM_ADJ, ALL_PROC_STATES, NON_CACHED_PROC_STATES, j, DumpUtils.dumpSingleTime(null, null, this.mMemFactorDurations, this.mMemFactor, this.mStartTime, j), str, z);
        printWriter.println();
        dumpTotalsLocked(printWriter, j);
    }

    private void dumpFragmentationLocked(PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("Available pages by page size:");
        int size = this.mPageTypeLabels.size();
        for (int i = 0; i < size; i++) {
            printWriter.format("Node %3d Zone %7s  %14s ", this.mPageTypeNodes.get(i), this.mPageTypeZones.get(i), this.mPageTypeLabels.get(i));
            int[] iArr = this.mPageTypeSizes.get(i);
            int length = iArr == null ? 0 : iArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                printWriter.format("%6d", Integer.valueOf(iArr[i2]));
            }
            printWriter.println();
        }
    }

    long printMemoryCategory(PrintWriter printWriter, String str, String str2, double d, long j, long j2, int i) {
        if (d == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            return j2;
        }
        long j3 = (long) ((d * 1024.0d) / j);
        printWriter.print(str);
        printWriter.print(str2);
        printWriter.print(": ");
        DebugUtils.printSizeValue(printWriter, j3);
        printWriter.print(" (");
        printWriter.print(i);
        printWriter.print(" samples)");
        printWriter.println();
        return j2 + j3;
    }

    void dumpTotalsLocked(PrintWriter printWriter, long j) {
        printWriter.println("Run time Stats:");
        DumpUtils.dumpSingleTime(printWriter, "  ", this.mMemFactorDurations, this.mMemFactor, this.mStartTime, j);
        printWriter.println();
        printWriter.println("Memory usage:");
        TotalMemoryUseCollection totalMemoryUseCollection = new TotalMemoryUseCollection(ALL_SCREEN_ADJ, ALL_MEM_ADJ);
        computeTotalMemoryUse(totalMemoryUseCollection, j);
        long jPrintMemoryCategory = printMemoryCategory(printWriter, "  ", "Native ", totalMemoryUseCollection.sysMemNativeWeight, totalMemoryUseCollection.totalTime, printMemoryCategory(printWriter, "  ", "Kernel ", totalMemoryUseCollection.sysMemKernelWeight, totalMemoryUseCollection.totalTime, 0L, totalMemoryUseCollection.sysMemSamples), totalMemoryUseCollection.sysMemSamples);
        for (int i = 0; i < 16; i++) {
            if (i != 9) {
                jPrintMemoryCategory = printMemoryCategory(printWriter, "  ", DumpUtils.STATE_NAMES[i], totalMemoryUseCollection.processStateWeight[i], totalMemoryUseCollection.totalTime, jPrintMemoryCategory, totalMemoryUseCollection.processStateSamples[i]);
            }
        }
        long jPrintMemoryCategory2 = printMemoryCategory(printWriter, "  ", "Z-Ram  ", totalMemoryUseCollection.sysMemZRamWeight, totalMemoryUseCollection.totalTime, printMemoryCategory(printWriter, "  ", "Free   ", totalMemoryUseCollection.sysMemFreeWeight, totalMemoryUseCollection.totalTime, printMemoryCategory(printWriter, "  ", "Cached ", totalMemoryUseCollection.sysMemCachedWeight, totalMemoryUseCollection.totalTime, jPrintMemoryCategory, totalMemoryUseCollection.sysMemSamples), totalMemoryUseCollection.sysMemSamples), totalMemoryUseCollection.sysMemSamples);
        printWriter.print("  TOTAL  : ");
        DebugUtils.printSizeValue(printWriter, jPrintMemoryCategory2);
        printWriter.println();
        printMemoryCategory(printWriter, "  ", DumpUtils.STATE_NAMES[9], totalMemoryUseCollection.processStateWeight[9], totalMemoryUseCollection.totalTime, jPrintMemoryCategory2, totalMemoryUseCollection.processStateSamples[9]);
        printWriter.println();
        printWriter.println("PSS collection stats:");
        printWriter.print("  Internal Single: ");
        printWriter.print(this.mInternalSinglePssCount);
        printWriter.print("x over ");
        TimeUtils.formatDuration(this.mInternalSinglePssTime, printWriter);
        printWriter.println();
        printWriter.print("  Internal All Procs (Memory Change): ");
        printWriter.print(this.mInternalAllMemPssCount);
        printWriter.print("x over ");
        TimeUtils.formatDuration(this.mInternalAllMemPssTime, printWriter);
        printWriter.println();
        printWriter.print("  Internal All Procs (Polling): ");
        printWriter.print(this.mInternalAllPollPssCount);
        printWriter.print("x over ");
        TimeUtils.formatDuration(this.mInternalAllPollPssTime, printWriter);
        printWriter.println();
        printWriter.print("  External: ");
        printWriter.print(this.mExternalPssCount);
        printWriter.print("x over ");
        TimeUtils.formatDuration(this.mExternalPssTime, printWriter);
        printWriter.println();
        printWriter.print("  External Slow: ");
        printWriter.print(this.mExternalSlowPssCount);
        printWriter.print("x over ");
        TimeUtils.formatDuration(this.mExternalSlowPssTime, printWriter);
        printWriter.println();
    }

    void dumpFilteredSummaryLocked(PrintWriter printWriter, String str, String str2, String str3, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, long j, long j2, String str4, boolean z) {
        ArrayList<ProcessState> arrayListCollectProcessesLocked = collectProcessesLocked(iArr, iArr2, iArr3, iArr4, j, str4, z);
        if (arrayListCollectProcessesLocked.size() > 0) {
            if (str != null) {
                printWriter.println();
                printWriter.println(str);
            }
            DumpUtils.dumpProcessSummaryLocked(printWriter, str2, str3, arrayListCollectProcessesLocked, iArr, iArr2, iArr4, j, j2);
        }
    }

    public ArrayList<ProcessState> collectProcessesLocked(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, long j, String str, boolean z) {
        ArraySet arraySet = new ArraySet();
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> map = this.mPackages.getMap();
        for (int i = 0; i < map.size(); i++) {
            String strKeyAt = map.keyAt(i);
            SparseArray<LongSparseArray<PackageState>> sparseArrayValueAt = map.valueAt(i);
            for (int i2 = 0; i2 < sparseArrayValueAt.size(); i2++) {
                LongSparseArray<PackageState> longSparseArrayValueAt = sparseArrayValueAt.valueAt(i2);
                int size = longSparseArrayValueAt.size();
                for (int i3 = 0; i3 < size; i3++) {
                    PackageState packageStateValueAt = longSparseArrayValueAt.valueAt(i3);
                    int size2 = packageStateValueAt.mProcesses.size();
                    boolean z2 = str == null || str.equals(strKeyAt);
                    for (int i4 = 0; i4 < size2; i4++) {
                        ProcessState processStateValueAt = packageStateValueAt.mProcesses.valueAt(i4);
                        if ((z2 || str.equals(processStateValueAt.getName())) && (!z || processStateValueAt.isInUse())) {
                            arraySet.add(processStateValueAt.getCommonProcess());
                        }
                    }
                }
            }
        }
        ArrayList<ProcessState> arrayList = new ArrayList<>(arraySet.size());
        for (int i5 = 0; i5 < arraySet.size(); i5++) {
            ProcessState processState = (ProcessState) arraySet.valueAt(i5);
            if (processState.computeProcessTimeLocked(iArr, iArr2, iArr3, j) > 0) {
                arrayList.add(processState);
                if (iArr3 != iArr4) {
                    processState.computeProcessTimeLocked(iArr, iArr2, iArr4, j);
                }
            }
        }
        Collections.sort(arrayList, ProcessState.COMPARATOR);
        return arrayList;
    }

    public void dumpCheckinLocked(PrintWriter printWriter, String str, int i) {
        boolean z;
        PrintWriter printWriter2 = printWriter;
        String str2 = str;
        long jUptimeMillis = SystemClock.uptimeMillis();
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> map = this.mPackages.getMap();
        printWriter2.println("vers,5");
        printWriter2.print("period,");
        printWriter2.print(this.mTimePeriodStartClockStr);
        printWriter2.print(",");
        printWriter2.print(this.mTimePeriodStartRealtime);
        printWriter2.print(",");
        printWriter2.print(this.mRunning ? SystemClock.elapsedRealtime() : this.mTimePeriodEndRealtime);
        int i2 = 1;
        if ((this.mFlags & 2) != 0) {
            printWriter2.print(",shutdown");
            z = false;
        } else {
            z = true;
        }
        if ((this.mFlags & 4) != 0) {
            printWriter2.print(",sysprops");
            z = false;
        }
        if ((this.mFlags & 1) != 0) {
            printWriter2.print(",complete");
            z = false;
        }
        if (z) {
            printWriter2.print(",partial");
        }
        if (this.mHasSwappedOutPss) {
            printWriter2.print(",swapped-out-pss");
        }
        printWriter2.println();
        printWriter2.print("config,");
        printWriter2.println(this.mRuntime);
        if ((i & 14) != 0) {
            int i3 = 0;
            while (i3 < map.size()) {
                String strKeyAt = map.keyAt(i3);
                if (str2 == null || str2.equals(strKeyAt)) {
                    SparseArray<LongSparseArray<PackageState>> sparseArrayValueAt = map.valueAt(i3);
                    int i4 = 0;
                    while (i4 < sparseArrayValueAt.size()) {
                        int iKeyAt = sparseArrayValueAt.keyAt(i4);
                        LongSparseArray<PackageState> longSparseArrayValueAt = sparseArrayValueAt.valueAt(i4);
                        int i5 = 0;
                        while (i5 < longSparseArrayValueAt.size()) {
                            long jKeyAt = longSparseArrayValueAt.keyAt(i5);
                            PackageState packageStateValueAt = longSparseArrayValueAt.valueAt(i5);
                            int i6 = i2;
                            int size = packageStateValueAt.mProcesses.size();
                            SparseArray<LongSparseArray<PackageState>> sparseArray = sparseArrayValueAt;
                            int size2 = packageStateValueAt.mServices.size();
                            int size3 = packageStateValueAt.mAssociations.size();
                            if ((i & 2) != 0) {
                                int i7 = 0;
                                while (i7 < size) {
                                    long j = jKeyAt;
                                    int i8 = i4;
                                    int i9 = iKeyAt;
                                    packageStateValueAt.mProcesses.valueAt(i7).dumpPackageProcCheckin(printWriter2, strKeyAt, i9, j, packageStateValueAt.mProcesses.keyAt(i7), jUptimeMillis);
                                    printWriter2 = printWriter;
                                    i5 = i5;
                                    size2 = size2;
                                    i7++;
                                    size3 = size3;
                                    map = map;
                                    iKeyAt = i9;
                                    i4 = i8;
                                    longSparseArrayValueAt = longSparseArrayValueAt;
                                    jKeyAt = j;
                                }
                            }
                            ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> arrayMap = map;
                            int i10 = size2;
                            int i11 = size3;
                            int i12 = i5;
                            long j2 = jKeyAt;
                            int i13 = i4;
                            int i14 = iKeyAt;
                            LongSparseArray<PackageState> longSparseArray = longSparseArrayValueAt;
                            if ((i & 4) != 0) {
                                for (int i15 = 0; i15 < i10; i15++) {
                                    packageStateValueAt.mServices.valueAt(i15).dumpTimesCheckin(printWriter, strKeyAt, i14, j2, DumpUtils.collapseString(strKeyAt, packageStateValueAt.mServices.keyAt(i15)), jUptimeMillis);
                                }
                            }
                            if ((i & 8) != 0) {
                                for (int i16 = 0; i16 < i11; i16++) {
                                    packageStateValueAt.mAssociations.valueAt(i16).dumpTimesCheckin(printWriter, strKeyAt, i14, j2, DumpUtils.collapseString(strKeyAt, packageStateValueAt.mAssociations.keyAt(i16)), jUptimeMillis);
                                }
                            }
                            i5 = i12 + 1;
                            printWriter2 = printWriter;
                            iKeyAt = i14;
                            i4 = i13;
                            longSparseArrayValueAt = longSparseArray;
                            i2 = i6;
                            sparseArrayValueAt = sparseArray;
                            map = arrayMap;
                        }
                        i4++;
                        printWriter2 = printWriter;
                    }
                }
                i3++;
                printWriter2 = printWriter;
                str2 = str;
                i2 = i2;
                map = map;
            }
        }
        int i17 = i2;
        if ((i & 1) != 0) {
            ArrayMap<String, SparseArray<ProcessState>> map2 = this.mProcesses.getMap();
            for (int i18 = 0; i18 < map2.size(); i18++) {
                String strKeyAt2 = map2.keyAt(i18);
                SparseArray<ProcessState> sparseArrayValueAt2 = map2.valueAt(i18);
                for (int i19 = 0; i19 < sparseArrayValueAt2.size(); i19++) {
                    sparseArrayValueAt2.valueAt(i19).dumpProcCheckin(printWriter, strKeyAt2, sparseArrayValueAt2.keyAt(i19), jUptimeMillis);
                }
            }
        }
        printWriter.print("total");
        DumpUtils.dumpAdjTimesCheckin(printWriter, ",", this.mMemFactorDurations, this.mMemFactor, this.mStartTime, jUptimeMillis);
        printWriter.println();
        int keyCount = this.mSysMemUsage.getKeyCount();
        if (keyCount > 0) {
            printWriter.print("sysmemusage");
            for (int i20 = 0; i20 < keyCount; i20++) {
                int keyAt = this.mSysMemUsage.getKeyAt(i20);
                byte idFromKey = SparseMappingTable.getIdFromKey(keyAt);
                printWriter.print(",");
                DumpUtils.printProcStateTag(printWriter, idFromKey);
                int i21 = 0;
                while (i21 < 16) {
                    int i22 = i17;
                    if (i21 > i22) {
                        printWriter.print(":");
                    }
                    printWriter.print(this.mSysMemUsage.getValue(keyAt, i21));
                    i21++;
                    i17 = i22;
                }
            }
        }
        printWriter.println();
        TotalMemoryUseCollection totalMemoryUseCollection = new TotalMemoryUseCollection(ALL_SCREEN_ADJ, ALL_MEM_ADJ);
        computeTotalMemoryUse(totalMemoryUseCollection, jUptimeMillis);
        printWriter.print("weights,");
        printWriter.print(totalMemoryUseCollection.totalTime);
        printWriter.print(",");
        printWriter.print(totalMemoryUseCollection.sysMemCachedWeight);
        printWriter.print(":");
        printWriter.print(totalMemoryUseCollection.sysMemSamples);
        printWriter.print(",");
        printWriter.print(totalMemoryUseCollection.sysMemFreeWeight);
        printWriter.print(":");
        printWriter.print(totalMemoryUseCollection.sysMemSamples);
        printWriter.print(",");
        printWriter.print(totalMemoryUseCollection.sysMemZRamWeight);
        printWriter.print(":");
        printWriter.print(totalMemoryUseCollection.sysMemSamples);
        printWriter.print(",");
        printWriter.print(totalMemoryUseCollection.sysMemKernelWeight);
        printWriter.print(":");
        printWriter.print(totalMemoryUseCollection.sysMemSamples);
        printWriter.print(",");
        printWriter.print(totalMemoryUseCollection.sysMemNativeWeight);
        printWriter.print(":");
        printWriter.print(totalMemoryUseCollection.sysMemSamples);
        for (int i23 = 0; i23 < 16; i23++) {
            printWriter.print(",");
            printWriter.print(totalMemoryUseCollection.processStateWeight[i23]);
            printWriter.print(":");
            printWriter.print(totalMemoryUseCollection.processStateSamples[i23]);
        }
        printWriter.println();
        int size4 = this.mPageTypeLabels.size();
        for (int i24 = 0; i24 < size4; i24++) {
            printWriter.print("availablepages,");
            printWriter.print(this.mPageTypeLabels.get(i24));
            printWriter.print(",");
            printWriter.print(this.mPageTypeZones.get(i24));
            printWriter.print(",");
            int[] iArr = this.mPageTypeSizes.get(i24);
            int length = iArr == null ? 0 : iArr.length;
            for (int i25 = 0; i25 < length; i25++) {
                if (i25 != 0) {
                    printWriter.print(",");
                }
                printWriter.print(iArr[i25]);
            }
            printWriter.println();
        }
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
        dumpProtoPreamble(protoOutputStream);
        int size = this.mPageTypeLabels.size();
        for (int i2 = 0; i2 < size; i2++) {
            long jStart = protoOutputStream.start(2246267895818L);
            protoOutputStream.write(1120986464257L, this.mPageTypeNodes.get(i2).intValue());
            protoOutputStream.write(1138166333442L, this.mPageTypeZones.get(i2));
            protoOutputStream.write(1138166333443L, this.mPageTypeLabels.get(i2));
            int[] iArr = this.mPageTypeSizes.get(i2);
            int length = iArr == null ? 0 : iArr.length;
            for (int i3 = 0; i3 < length; i3++) {
                protoOutputStream.write(2220498092036L, iArr[i3]);
            }
            protoOutputStream.end(jStart);
        }
        ArrayMap<String, SparseArray<ProcessState>> map = this.mProcesses.getMap();
        if ((i & 1) != 0) {
            for (int i4 = 0; i4 < map.size(); i4++) {
                String strKeyAt = map.keyAt(i4);
                SparseArray<ProcessState> sparseArrayValueAt = map.valueAt(i4);
                for (int i5 = 0; i5 < sparseArrayValueAt.size(); i5++) {
                    sparseArrayValueAt.valueAt(i5).dumpDebug(protoOutputStream, 2246267895816L, strKeyAt, sparseArrayValueAt.keyAt(i5), j);
                }
            }
        }
        if ((i & 14) != 0) {
            ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> map2 = this.mPackages.getMap();
            for (int i6 = 0; i6 < map2.size(); i6++) {
                SparseArray<LongSparseArray<PackageState>> sparseArrayValueAt2 = map2.valueAt(i6);
                for (int i7 = 0; i7 < sparseArrayValueAt2.size(); i7++) {
                    LongSparseArray<PackageState> longSparseArrayValueAt = sparseArrayValueAt2.valueAt(i7);
                    for (int i8 = 0; i8 < longSparseArrayValueAt.size(); i8++) {
                        longSparseArrayValueAt.valueAt(i8).dumpDebug(protoOutputStream, 2246267895817L, j, i);
                    }
                }
            }
        }
    }

    public void dumpAggregatedProtoForStatsd(ProtoOutputStream[] protoOutputStreamArr, long j) throws IOException {
        dumpProtoPreamble(protoOutputStreamArr[0]);
        ArrayMap<String, SparseArray<ProcessState>> map = this.mProcesses.getMap();
        ProcessMap<ArraySet<PackageState>> processMap = new ProcessMap<>();
        SparseArray<ArraySet<String>> sparseArray = new SparseArray<>();
        collectProcessPackageMaps(null, false, processMap, sparseArray);
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= map.size()) {
                break;
            }
            String strKeyAt = map.keyAt(i2);
            if (protoOutputStreamArr[i].getRawSize() > j) {
                i++;
                if (i >= protoOutputStreamArr.length) {
                    Slog.d(TAG, String.format("Dropping process indices from %d to %d from statsd proto (too large)", Integer.valueOf(i2), Integer.valueOf(map.size())));
                    break;
                }
                dumpProtoPreamble(protoOutputStreamArr[i]);
            }
            int i3 = i;
            int i4 = 0;
            for (SparseArray<ProcessState> sparseArrayValueAt = map.valueAt(i2); i4 < sparseArrayValueAt.size(); sparseArrayValueAt = sparseArrayValueAt) {
                sparseArrayValueAt.valueAt(i4).dumpAggregatedProtoForStatsd(protoOutputStreamArr[i3], 2246267895816L, strKeyAt, sparseArrayValueAt.keyAt(i4), this.mTimePeriodEndRealtime, processMap, sparseArray);
                i4++;
            }
            i2++;
            i = i3;
        }
        for (int i5 = 0; i5 <= i; i5++) {
            protoOutputStreamArr[i5].flush();
        }
    }

    void forEachProcess(Consumer<ProcessState> consumer) {
        ArrayMap<String, SparseArray<ProcessState>> map = this.mProcesses.getMap();
        int size = map.size();
        for (int i = 0; i < size; i++) {
            SparseArray<ProcessState> sparseArrayValueAt = map.valueAt(i);
            int size2 = sparseArrayValueAt.size();
            for (int i2 = 0; i2 < size2; i2++) {
                consumer.accept(sparseArrayValueAt.valueAt(i2));
            }
        }
    }

    void forEachAssociation(QuintConsumer<AssociationState, Integer, String, AssociationState.SourceKey, AssociationState.SourceState> quintConsumer) {
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> map = this.mPackages.getMap();
        int size = map.size();
        for (int i = 0; i < size; i++) {
            SparseArray<LongSparseArray<PackageState>> sparseArrayValueAt = map.valueAt(i);
            int size2 = sparseArrayValueAt.size();
            for (int i2 = 0; i2 < size2; i2++) {
                int iKeyAt = sparseArrayValueAt.keyAt(i2);
                LongSparseArray<PackageState> longSparseArrayValueAt = sparseArrayValueAt.valueAt(i2);
                int size3 = longSparseArrayValueAt.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    PackageState packageStateValueAt = longSparseArrayValueAt.valueAt(i3);
                    int size4 = packageStateValueAt.mAssociations.size();
                    for (int i4 = 0; i4 < size4; i4++) {
                        String strKeyAt = packageStateValueAt.mAssociations.keyAt(i4);
                        AssociationState associationStateValueAt = packageStateValueAt.mAssociations.valueAt(i4);
                        int size5 = associationStateValueAt.mSources.size();
                        int i5 = 0;
                        while (i5 < size5) {
                            AssociationState.SourceState sourceStateValueAt = associationStateValueAt.mSources.valueAt(i5);
                            quintConsumer.accept(associationStateValueAt, Integer.valueOf(iKeyAt), strKeyAt, associationStateValueAt.mSources.keyAt(i5), sourceStateValueAt);
                            i5++;
                            size5 = size5;
                            map = map;
                        }
                    }
                }
            }
        }
    }

    public void dumpProcessState(final int i, final StatsEventOutput statsEventOutput) {
        forEachProcess(new Consumer() { // from class: com.android.internal.app.procstats.ProcessStats$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$dumpProcessState$1(i, statsEventOutput, (ProcessState) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dumpProcessState$1(int i, StatsEventOutput statsEventOutput, ProcessState processState) {
        if (!processState.isMultiPackage() || processState.getCommonProcess() == processState) {
            processState.dumpStateDurationToStatsd(i, this, statsEventOutput);
        }
    }

    public void dumpProcessAssociation(final int i, final StatsEventOutput statsEventOutput) {
        forEachAssociation(new QuintConsumer() { // from class: com.android.internal.app.procstats.ProcessStats$$ExternalSyntheticLambda0
            @Override // com.android.internal.util.function.QuintConsumer
            public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                this.f$0.lambda$dumpProcessAssociation$2(statsEventOutput, i, (AssociationState) obj, (Integer) obj2, (String) obj3, (AssociationState.SourceKey) obj4, (AssociationState.SourceState) obj5);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dumpProcessAssociation$2(StatsEventOutput statsEventOutput, int i, AssociationState associationState, Integer num, String str, AssociationState.SourceKey sourceKey, AssociationState.SourceState sourceState) {
        statsEventOutput.write(i, sourceKey.mUid, sourceKey.mProcess, num.intValue(), str, (int) TimeUnit.MILLISECONDS.toSeconds(this.mTimePeriodStartUptime), (int) TimeUnit.MILLISECONDS.toSeconds(this.mTimePeriodEndUptime), (int) TimeUnit.MILLISECONDS.toSeconds(this.mTimePeriodEndUptime - this.mTimePeriodStartUptime), (int) TimeUnit.MILLISECONDS.toSeconds(sourceState.mDuration), sourceState.mActiveCount, associationState.getProcessName());
    }

    private void dumpProtoPreamble(ProtoOutputStream protoOutputStream) {
        boolean z;
        protoOutputStream.write(1112396529665L, this.mTimePeriodStartRealtime);
        protoOutputStream.write(1112396529666L, this.mRunning ? SystemClock.elapsedRealtime() : this.mTimePeriodEndRealtime);
        protoOutputStream.write(1112396529667L, this.mTimePeriodStartUptime);
        protoOutputStream.write(1112396529668L, this.mTimePeriodEndUptime);
        protoOutputStream.write(1138166333445L, this.mRuntime);
        protoOutputStream.write(1133871366150L, this.mHasSwappedOutPss);
        boolean z2 = false;
        if ((this.mFlags & 2) != 0) {
            protoOutputStream.write(2259152797703L, 3);
            z = false;
        } else {
            z = true;
        }
        if ((this.mFlags & 4) != 0) {
            protoOutputStream.write(2259152797703L, 4);
            z = false;
        }
        if ((this.mFlags & 1) != 0) {
            protoOutputStream.write(2259152797703L, 1);
        } else {
            z2 = z;
        }
        if (z2) {
            protoOutputStream.write(2259152797703L, 2);
        }
    }

    private void collectProcessPackageMaps(String str, boolean z, ProcessMap<ArraySet<PackageState>> processMap, SparseArray<ArraySet<String>> sparseArray) {
        ArraySet<PackageState> arraySet;
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> map = this.mPackages.getMap();
        int i = 1;
        int size = map.size() - 1;
        while (size >= 0) {
            String strKeyAt = map.keyAt(size);
            SparseArray<LongSparseArray<PackageState>> sparseArrayValueAt = map.valueAt(size);
            int size2 = sparseArrayValueAt.size() - i;
            while (size2 >= 0) {
                LongSparseArray<PackageState> longSparseArrayValueAt = sparseArrayValueAt.valueAt(size2);
                int size3 = longSparseArrayValueAt.size() - i;
                while (size3 >= 0) {
                    PackageState packageStateValueAt = longSparseArrayValueAt.valueAt(size3);
                    int i2 = (str == null || str.equals(strKeyAt)) ? i : 0;
                    for (int size4 = packageStateValueAt.mProcesses.size() - i; size4 >= 0; size4--) {
                        ProcessState processStateValueAt = packageStateValueAt.mProcesses.valueAt(size4);
                        if ((i2 != 0 || str.equals(processStateValueAt.getName())) && (!z || processStateValueAt.isInUse())) {
                            String name = processStateValueAt.getName();
                            int uid = processStateValueAt.getUid();
                            ArraySet<PackageState> arraySet2 = processMap.get(name, uid);
                            if (arraySet2 == null) {
                                arraySet = new ArraySet<>();
                                processMap.put(name, uid, arraySet);
                            } else {
                                arraySet = arraySet2;
                            }
                            arraySet.add(packageStateValueAt);
                            ArraySet<String> arraySet3 = sparseArray.get(uid);
                            if (arraySet3 == null) {
                                arraySet3 = new ArraySet<>();
                                sparseArray.put(uid, arraySet3);
                            }
                            arraySet3.add(packageStateValueAt.mPackageName);
                        }
                    }
                    size3--;
                    i = 1;
                }
                size2--;
                i = 1;
            }
            size--;
            i = 1;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v3 */
    public void dumpFilteredAssociationStatesProtoForProc(ProtoOutputStream protoOutputStream, long j, long j2, ProcessState processState, SparseArray<ArraySet<String>> sparseArray) {
        ArrayMap<AssociationState.SourceKey, AssociationState.SourceState> arrayMap;
        IProcessStats iProcessStatsAsInterface;
        int i;
        if ((processState.isMultiPackage() && processState.getCommonProcess() != processState) || (arrayMap = processState.mCommonSources) == null || arrayMap.isEmpty() || (iProcessStatsAsInterface = IProcessStats.Stub.asInterface(ServiceManager.getService(SERVICE_NAME))) == null) {
            return;
        }
        try {
            long minAssociationDumpDuration = iProcessStatsAsInterface.getMinAssociationDumpDuration();
            ?? r10 = 1;
            int size = arrayMap.size() - 1;
            while (size >= 0) {
                AssociationState.SourceState sourceStateValueAt = arrayMap.valueAt(size);
                long j3 = sourceStateValueAt.mDuration;
                if (sourceStateValueAt.mNesting > 0) {
                    j3 += j2 - sourceStateValueAt.mStartUptime;
                }
                long j4 = j3;
                if (j4 < minAssociationDumpDuration) {
                    i = size;
                } else {
                    AssociationState.SourceKey sourceKeyKeyAt = arrayMap.keyAt(size);
                    long jStart = protoOutputStream.start(j);
                    int iIndexOfKey = sparseArray.indexOfKey(sourceKeyKeyAt.mUid);
                    i = size;
                    ProcessState.writeCompressedProcessName(protoOutputStream, 1138166333441L, sourceKeyKeyAt.mProcess, sourceKeyKeyAt.mPackage, (iIndexOfKey < 0 || sparseArray.valueAt(iIndexOfKey).size() <= r10) ? false : r10);
                    protoOutputStream.write(1120986464261L, sourceKeyKeyAt.mUid);
                    protoOutputStream.write(1120986464259L, sourceStateValueAt.mCount);
                    protoOutputStream.write(1120986464260L, (int) (j4 / 1000));
                    protoOutputStream.end(jStart);
                }
                size = i - 1;
                r10 = 1;
            }
        } catch (RemoteException unused) {
        }
    }

    public static final class ProcessStateHolder {
        public final long appVersion;
        public PackageState pkg;
        public ProcessState state;

        public ProcessStateHolder(long j) {
            this.appVersion = j;
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

        public PackageState(ProcessStats processStats, String str, int i, long j) {
            this.mProcessStats = processStats;
            this.mUid = i;
            this.mPackageName = str;
            this.mVersionCode = j;
        }

        public AssociationState getAssociationStateLocked(ProcessState processState, String str) {
            AssociationState associationState = this.mAssociations.get(str);
            if (associationState != null) {
                if (processState != null) {
                    associationState.setProcess(processState);
                }
                return associationState;
            }
            AssociationState associationState2 = new AssociationState(this.mProcessStats, this, str, processState.getName(), processState);
            this.mAssociations.put(str, associationState2);
            return associationState2;
        }

        public void dumpDebug(ProtoOutputStream protoOutputStream, long j, long j2, int i) {
            long jStart = protoOutputStream.start(j);
            protoOutputStream.write(1138166333441L, this.mPackageName);
            protoOutputStream.write(1120986464258L, this.mUid);
            protoOutputStream.write(1112396529667L, this.mVersionCode);
            if ((i & 2) != 0) {
                for (int i2 = 0; i2 < this.mProcesses.size(); i2++) {
                    this.mProcesses.valueAt(i2).dumpDebug(protoOutputStream, 2246267895812L, this.mProcesses.keyAt(i2), this.mUid, j2);
                }
            }
            if ((i & 4) != 0) {
                for (int i3 = 0; i3 < this.mServices.size(); i3++) {
                    this.mServices.valueAt(i3).dumpDebug(protoOutputStream, 2246267895813L, j2);
                }
            }
            if ((i & 8) != 0) {
                for (int i4 = 0; i4 < this.mAssociations.size(); i4++) {
                    this.mAssociations.valueAt(i4).dumpDebug(protoOutputStream, 2246267895814L, j2);
                }
            }
            protoOutputStream.end(jStart);
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

        public ProcessDataCollection(int[] iArr, int[] iArr2, int[] iArr3) {
            this.screenStates = iArr;
            this.memStates = iArr2;
            this.procStates = iArr3;
        }

        void print(PrintWriter printWriter, long j, boolean z) {
            if (this.totalTime > j) {
                printWriter.print("*");
            }
            DumpUtils.printPercent(printWriter, this.totalTime / j);
            if (this.numPss > 0) {
                printWriter.print(" (");
                DebugUtils.printSizeValue(printWriter, this.minPss * 1024);
                printWriter.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                DebugUtils.printSizeValue(printWriter, this.avgPss * 1024);
                printWriter.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                DebugUtils.printSizeValue(printWriter, this.maxPss * 1024);
                printWriter.print("/");
                DebugUtils.printSizeValue(printWriter, this.minUss * 1024);
                printWriter.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                DebugUtils.printSizeValue(printWriter, this.avgUss * 1024);
                printWriter.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                DebugUtils.printSizeValue(printWriter, this.maxUss * 1024);
                printWriter.print("/");
                DebugUtils.printSizeValue(printWriter, this.minRss * 1024);
                printWriter.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                DebugUtils.printSizeValue(printWriter, this.avgRss * 1024);
                printWriter.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                DebugUtils.printSizeValue(printWriter, this.maxRss * 1024);
                if (z) {
                    printWriter.print(" over ");
                    printWriter.print(this.numPss);
                }
                printWriter.print(NavigationBarInflaterView.KEY_CODE_END);
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

        public TotalMemoryUseCollection(int[] iArr, int[] iArr2) {
            this.screenStates = iArr;
            this.memStates = iArr2;
        }
    }
}
