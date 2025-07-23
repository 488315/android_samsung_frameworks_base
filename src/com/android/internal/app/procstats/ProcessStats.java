package com.android.internal.app.procstats;

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

    public ProcessStats(boolean z) {
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

    public ProcessStats(Parcel parcel) {
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
            String keyAt = map.keyAt(i7);
            SparseArray<LongSparseArray<PackageState>> valueAt = map.valueAt(i7);
            int i8 = 0;
            while (i8 < valueAt.size()) {
                int keyAt2 = valueAt.keyAt(i8);
                LongSparseArray<PackageState> valueAt2 = valueAt.valueAt(i8);
                int i9 = 0;
                while (i9 < valueAt2.size()) {
                    long keyAt3 = valueAt2.keyAt(i9);
                    PackageState valueAt3 = valueAt2.valueAt(i9);
                    int size = valueAt3.mProcesses.size();
                    int size2 = valueAt3.mServices.size();
                    int size3 = valueAt3.mAssociations.size();
                    int i10 = 0;
                    while (i10 < size) {
                        int i11 = size2;
                        ProcessState valueAt4 = valueAt3.mProcesses.valueAt(i10);
                        String str = keyAt;
                        if (valueAt4.getCommonProcess() != valueAt4) {
                            arrayMap = map;
                            i3 = i11;
                            i5 = i10;
                            i6 = size3;
                            keyAt = str;
                            i4 = size;
                            ProcessState processStateLocked = getProcessStateLocked(keyAt, keyAt2, keyAt3, valueAt4.getName());
                            i = i7;
                            if (processStateLocked.getCommonProcess() == processStateLocked) {
                                processStateLocked.setMultiPackage(true);
                                sparseArray = valueAt;
                                long uptimeMillis = SystemClock.uptimeMillis();
                                i2 = i8;
                                PackageState packageStateLocked = getPackageStateLocked(keyAt, keyAt2, keyAt3);
                                processStateLocked = processStateLocked.clone(uptimeMillis);
                                packageStateLocked.mProcesses.put(processStateLocked.getName(), processStateLocked);
                            } else {
                                sparseArray = valueAt;
                                i2 = i8;
                            }
                            processStateLocked.add(valueAt4);
                        } else {
                            arrayMap = map;
                            i = i7;
                            sparseArray = valueAt;
                            i2 = i8;
                            i3 = i11;
                            keyAt = str;
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
                        valueAt = sparseArray;
                        i8 = i2;
                    }
                    int i13 = size3;
                    ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> arrayMap2 = map;
                    int i14 = i7;
                    SparseArray<LongSparseArray<PackageState>> sparseArray2 = valueAt;
                    int i15 = i8;
                    int i16 = size2;
                    for (int i17 = 0; i17 < i16; i17++) {
                        ServiceState valueAt5 = valueAt3.mServices.valueAt(i17);
                        getServiceStateLocked(keyAt, keyAt2, keyAt3, valueAt5.getProcessName(), valueAt5.getName()).add(valueAt5);
                    }
                    for (int i18 = 0; i18 < i13; i18++) {
                        AssociationState valueAt6 = valueAt3.mAssociations.valueAt(i18);
                        getAssociationStateLocked(keyAt, keyAt2, keyAt3, valueAt6.getProcessName(), valueAt6.getName()).add(valueAt6);
                    }
                    i9++;
                    map = arrayMap2;
                    i7 = i14;
                    valueAt = sparseArray2;
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
            int keyAt4 = sparseArray3.keyAt(i19);
            UidState uidState = processStats3.mUidStates.get(keyAt4);
            if (uidState == null) {
                processStats3.mUidStates.put(keyAt4, sparseArray3.valueAt(i19).m7967clone());
            } else {
                uidState.add(sparseArray3.valueAt(i19));
            }
        }
        ArrayMap<String, SparseArray<ProcessState>> map2 = processStats.mProcesses.getMap();
        for (int i20 = 0; i20 < map2.size(); i20++) {
            SparseArray<ProcessState> valueAt7 = map2.valueAt(i20);
            int i21 = 0;
            while (i21 < valueAt7.size()) {
                int keyAt5 = valueAt7.keyAt(i21);
                ProcessState valueAt8 = valueAt7.valueAt(i21);
                String name = valueAt8.getName();
                String str2 = valueAt8.getPackage();
                long version = valueAt8.getVersion();
                ProcessState processState = processStats3.mProcesses.get(name, keyAt5);
                if (processState == null) {
                    ProcessState processState2 = new ProcessState(this, str2, keyAt5, version, name);
                    processStats2 = this;
                    processStats2.mProcesses.put(name, keyAt5, processState2);
                    PackageState packageStateLocked2 = processStats2.getPackageStateLocked(str2, keyAt5, version);
                    if (!packageStateLocked2.mProcesses.containsKey(name)) {
                        packageStateLocked2.mProcesses.put(name, processState2);
                    }
                    processState = processState2;
                } else {
                    processStats2 = processStats3;
                }
                processState.add(valueAt8);
                UidState uidState2 = processStats2.mUidStates.get(keyAt5);
                if (uidState2 == null) {
                    uidState2 = new UidState(processStats2, keyAt5);
                    processStats2.mUidStates.put(keyAt5, uidState2);
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

    public void computeTotalMemoryUse(TotalMemoryUseCollection totalMemoryUseCollection, long j) {
        long[] jArr;
        int i;
        totalMemoryUseCollection.totalTime = 0L;
        for (int i2 = 0; i2 < 16; i2++) {
            totalMemoryUseCollection.processStateWeight[i2] = 0.0d;
            totalMemoryUseCollection.processStatePss[i2] = 0;
            totalMemoryUseCollection.processStateTime[i2] = 0;
            totalMemoryUseCollection.processStateSamples[i2] = 0;
        }
        for (int i3 = 0; i3 < 16; i3++) {
            totalMemoryUseCollection.sysMemUsage[i3] = 0;
        }
        totalMemoryUseCollection.sysMemCachedWeight = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        totalMemoryUseCollection.sysMemFreeWeight = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        totalMemoryUseCollection.sysMemZRamWeight = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        totalMemoryUseCollection.sysMemKernelWeight = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        totalMemoryUseCollection.sysMemNativeWeight = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        totalMemoryUseCollection.sysMemSamples = 0;
        long[] totalMemUsage = this.mSysMemUsage.getTotalMemUsage();
        for (int i4 = 0; i4 < totalMemoryUseCollection.screenStates.length; i4++) {
            for (int i5 = 0; i5 < totalMemoryUseCollection.memStates.length; i5++) {
                int i6 = totalMemoryUseCollection.screenStates[i4] + totalMemoryUseCollection.memStates[i5];
                int i7 = i6 * 16;
                long j2 = this.mMemFactorDurations[i6];
                if (this.mMemFactor == i6) {
                    j2 += j - this.mStartTime;
                }
                totalMemoryUseCollection.totalTime += j2;
                int key = this.mSysMemUsage.getKey((byte) i7);
                if (key != -1) {
                    jArr = this.mSysMemUsage.getArrayForKey(key);
                    i = SparseMappingTable.getIndexFromKey(key);
                    if (jArr[i] >= 3) {
                        SysMemUsageTable.mergeSysMemUsage(totalMemoryUseCollection.sysMemUsage, 0, totalMemUsage, 0);
                        double d = j2;
                        totalMemoryUseCollection.sysMemCachedWeight += jArr[i + 2] * d;
                        totalMemoryUseCollection.sysMemFreeWeight += jArr[i + 5] * d;
                        totalMemoryUseCollection.sysMemZRamWeight += jArr[i + 8] * d;
                        totalMemoryUseCollection.sysMemKernelWeight += jArr[i + 11] * d;
                        totalMemoryUseCollection.sysMemNativeWeight += jArr[i + 14] * d;
                        totalMemoryUseCollection.sysMemSamples = (int) (totalMemoryUseCollection.sysMemSamples + jArr[i]);
                    }
                }
                jArr = totalMemUsage;
                i = 0;
                double d2 = j2;
                totalMemoryUseCollection.sysMemCachedWeight += jArr[i + 2] * d2;
                totalMemoryUseCollection.sysMemFreeWeight += jArr[i + 5] * d2;
                totalMemoryUseCollection.sysMemZRamWeight += jArr[i + 8] * d2;
                totalMemoryUseCollection.sysMemKernelWeight += jArr[i + 11] * d2;
                totalMemoryUseCollection.sysMemNativeWeight += jArr[i + 14] * d2;
                totalMemoryUseCollection.sysMemSamples = (int) (totalMemoryUseCollection.sysMemSamples + jArr[i]);
            }
        }
        totalMemoryUseCollection.hasSwappedOutPss = this.mHasSwappedOutPss;
        ArrayMap<String, SparseArray<ProcessState>> map = this.mProcesses.getMap();
        for (int i8 = 0; i8 < map.size(); i8++) {
            SparseArray<ProcessState> valueAt = map.valueAt(i8);
            for (int i9 = 0; i9 < valueAt.size(); i9++) {
                valueAt.valueAt(i9).aggregatePss(totalMemoryUseCollection, j);
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
        long uptimeMillis = SystemClock.uptimeMillis();
        ArrayMap<String, SparseArray<ProcessState>> map = this.mProcesses.getMap();
        for (int size = map.size() - 1; size >= 0; size--) {
            SparseArray<ProcessState> valueAt = map.valueAt(size);
            for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                valueAt.valueAt(size2).tmpNumInUse = 0;
            }
        }
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> map2 = this.mPackages.getMap();
        for (int size3 = map2.size() - 1; size3 >= 0; size3--) {
            SparseArray<LongSparseArray<PackageState>> valueAt2 = map2.valueAt(size3);
            for (int size4 = valueAt2.size() - 1; size4 >= 0; size4--) {
                LongSparseArray<PackageState> valueAt3 = valueAt2.valueAt(size4);
                for (int size5 = valueAt3.size() - 1; size5 >= 0; size5--) {
                    PackageState valueAt4 = valueAt3.valueAt(size5);
                    for (int size6 = valueAt4.mProcesses.size() - 1; size6 >= 0; size6--) {
                        ProcessState valueAt5 = valueAt4.mProcesses.valueAt(size6);
                        if (valueAt5.isInUse()) {
                            valueAt5.resetSafely(uptimeMillis);
                            valueAt5.getCommonProcess().tmpNumInUse++;
                            valueAt5.getCommonProcess().tmpFoundSubProc = valueAt5;
                        } else {
                            valueAt4.mProcesses.valueAt(size6).makeDead();
                            valueAt4.mProcesses.removeAt(size6);
                        }
                    }
                    for (int size7 = valueAt4.mServices.size() - 1; size7 >= 0; size7--) {
                        ServiceState valueAt6 = valueAt4.mServices.valueAt(size7);
                        if (valueAt6.isInUse()) {
                            valueAt6.resetSafely(uptimeMillis);
                        } else {
                            valueAt4.mServices.removeAt(size7);
                        }
                    }
                    for (int size8 = valueAt4.mAssociations.size() - 1; size8 >= 0; size8--) {
                        AssociationState valueAt7 = valueAt4.mAssociations.valueAt(size8);
                        if (valueAt7.isInUse()) {
                            valueAt7.resetSafely(uptimeMillis);
                        } else {
                            valueAt4.mAssociations.removeAt(size8);
                        }
                    }
                    if (valueAt4.mProcesses.size() <= 0 && valueAt4.mServices.size() <= 0 && valueAt4.mAssociations.size() <= 0) {
                        valueAt3.removeAt(size5);
                    }
                }
                if (valueAt3.size() <= 0) {
                    valueAt2.removeAt(size4);
                }
            }
            if (valueAt2.size() <= 0) {
                map2.removeAt(size3);
            }
        }
        for (int size9 = map.size() - 1; size9 >= 0; size9--) {
            SparseArray<ProcessState> valueAt8 = map.valueAt(size9);
            for (int size10 = valueAt8.size() - 1; size10 >= 0; size10--) {
                ProcessState valueAt9 = valueAt8.valueAt(size10);
                if (valueAt9.isInUse() || valueAt9.tmpNumInUse > 0) {
                    if (!valueAt9.isActive() && valueAt9.isMultiPackage() && valueAt9.tmpNumInUse == 1) {
                        ProcessState processState = valueAt9.tmpFoundSubProc;
                        processState.makeStandalone();
                        valueAt8.setValueAt(size10, processState);
                    } else {
                        valueAt9.resetSafely(uptimeMillis);
                    }
                } else {
                    valueAt9.makeDead();
                    valueAt8.removeAt(size10);
                }
            }
            if (valueAt8.size() <= 0) {
                map.removeAt(size9);
            }
        }
        for (int size11 = this.mUidStates.size() - 1; size11 >= 0; size11--) {
            if (this.mUidStates.valueAt(size11).isInUse()) {
                this.mUidStates.valueAt(size11).resetSafely(uptimeMillis);
            } else {
                this.mUidStates.removeAt(size11);
            }
        }
        this.mStartTime = uptimeMillis;
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

    public void updateFragmentation() {
        Integer valueOf;
        BufferedReader bufferedReader = null;
        try {
            try {
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/proc/pagetypeinfo"));
                    try {
                        Matcher matcher = sPageTypeRegex.matcher("");
                        this.mPageTypeNodes.clear();
                        this.mPageTypeZones.clear();
                        this.mPageTypeLabels.clear();
                        this.mPageTypeSizes.clear();
                        while (true) {
                            String readLine = bufferedReader2.readLine();
                            if (readLine != null) {
                                matcher.reset(readLine);
                                if (matcher.matches() && (valueOf = Integer.valueOf(matcher.group(1), 10)) != null) {
                                    this.mPageTypeNodes.add(valueOf);
                                    this.mPageTypeZones.add(matcher.group(2));
                                    this.mPageTypeLabels.add(matcher.group(3));
                                    this.mPageTypeSizes.add(splitAndParseNumbers(matcher.group(4)));
                                }
                            } else {
                                bufferedReader2.close();
                                return;
                            }
                        }
                    } catch (IOException unused) {
                        bufferedReader = bufferedReader2;
                        this.mPageTypeNodes.clear();
                        this.mPageTypeZones.clear();
                        this.mPageTypeLabels.clear();
                        this.mPageTypeSizes.clear();
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader2;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException unused2) {
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException unused3) {
            }
        } catch (IOException unused4) {
        }
    }

    private static int[] splitAndParseNumbers(String str) {
        int length = str.length();
        int i = 0;
        boolean z = false;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (charAt < '0' || charAt > '9') {
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
            char charAt2 = str.charAt(i5);
            if (charAt2 < '0' || charAt2 > '9') {
                if (z) {
                    iArr[i4] = i3;
                    i4++;
                    z = false;
                }
            } else if (z) {
                i3 = (i3 * 10) + (charAt2 - '0');
            } else {
                i3 = charAt2 - '0';
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
            int readInt = parcel.readInt();
            if (readInt >= 0) {
                jArr[i3] = readInt;
            } else {
                jArr[i3] = parcel.readInt() | ((~readInt) << 32);
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
        Integer valueOf = Integer.valueOf(size);
        this.mCommonStringToIndex.put(str, valueOf);
        valueOf.getClass();
        parcel.writeInt(~size);
        parcel.writeString(str);
    }

    String readCommonString(Parcel parcel, int i) {
        if (i <= 9) {
            return parcel.readString();
        }
        int readInt = parcel.readInt();
        if (readInt >= 0) {
            return this.mIndexToCommonString.get(readInt);
        }
        int i2 = ~readInt;
        String readString = parcel.readString();
        while (this.mIndexToCommonString.size() <= i2) {
            this.mIndexToCommonString.add(null);
        }
        this.mIndexToCommonString.set(i2, readString);
        return readString;
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
            SparseArray<ProcessState> valueAt = map.valueAt(i2);
            int size2 = valueAt.size();
            for (int i3 = 0; i3 < size2; i3++) {
                valueAt.valueAt(i3).commitStateTime(j);
            }
        }
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> map2 = this.mPackages.getMap();
        int size3 = map2.size();
        int i4 = 0;
        while (i4 < size3) {
            SparseArray<LongSparseArray<PackageState>> valueAt2 = map2.valueAt(i4);
            int size4 = valueAt2.size();
            for (int i5 = 0; i5 < size4; i5++) {
                LongSparseArray<PackageState> valueAt3 = valueAt2.valueAt(i5);
                int size5 = valueAt3.size();
                int i6 = 0;
                while (i6 < size5) {
                    PackageState valueAt4 = valueAt3.valueAt(i6);
                    int i7 = i4;
                    int size6 = valueAt4.mProcesses.size();
                    SparseArray<LongSparseArray<PackageState>> sparseArray = valueAt2;
                    int i8 = 0;
                    while (i8 < size6) {
                        int i9 = size6;
                        ProcessState valueAt5 = valueAt4.mProcesses.valueAt(i8);
                        int i10 = i8;
                        if (valueAt5.getCommonProcess() != valueAt5) {
                            valueAt5.commitStateTime(j);
                        }
                        i8 = i10 + 1;
                        size6 = i9;
                    }
                    int i11 = 0;
                    for (int size7 = valueAt4.mServices.size(); i11 < size7; size7 = size7) {
                        valueAt4.mServices.valueAt(i11).commitStateTime(j);
                        i11++;
                    }
                    int i12 = 0;
                    for (int size8 = valueAt4.mAssociations.size(); i12 < size8; size8 = size8) {
                        valueAt4.mAssociations.valueAt(i12).commitStateTime(j);
                        i12++;
                    }
                    i6++;
                    i4 = i7;
                    valueAt2 = sparseArray;
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
            SparseArray<ProcessState> valueAt6 = map.valueAt(i15);
            int size10 = valueAt6.size();
            parcel.writeInt(size10);
            for (int i16 = 0; i16 < size10; i16++) {
                parcel.writeInt(valueAt6.keyAt(i16));
                ProcessState valueAt7 = valueAt6.valueAt(i16);
                writeCommonString(parcel, valueAt7.getPackage());
                parcel.writeLong(valueAt7.getVersion());
                valueAt7.writeToParcel(parcel, j);
            }
        }
        parcel.writeInt(size3);
        int i17 = 0;
        while (i17 < size3) {
            writeCommonString(parcel, map2.keyAt(i17));
            SparseArray<LongSparseArray<PackageState>> valueAt8 = map2.valueAt(i17);
            int size11 = valueAt8.size();
            parcel.writeInt(size11);
            for (int i18 = 0; i18 < size11; i18++) {
                parcel.writeInt(valueAt8.keyAt(i18));
                LongSparseArray<PackageState> valueAt9 = valueAt8.valueAt(i18);
                int size12 = valueAt9.size();
                parcel.writeInt(size12);
                int i19 = 0;
                while (i19 < size12) {
                    parcel.writeLong(valueAt9.keyAt(i19));
                    PackageState valueAt10 = valueAt9.valueAt(i19);
                    int size13 = valueAt10.mProcesses.size();
                    parcel.writeInt(size13);
                    int i20 = 0;
                    while (i20 < size13) {
                        int i21 = i17;
                        writeCommonString(parcel, valueAt10.mProcesses.keyAt(i20));
                        ProcessState valueAt11 = valueAt10.mProcesses.valueAt(i20);
                        SparseArray<LongSparseArray<PackageState>> sparseArray2 = valueAt8;
                        if (valueAt11.getCommonProcess() == valueAt11) {
                            parcel.writeInt(0);
                        } else {
                            parcel.writeInt(1);
                            valueAt11.writeToParcel(parcel, j);
                        }
                        i20++;
                        i17 = i21;
                        valueAt8 = sparseArray2;
                    }
                    int i22 = i17;
                    SparseArray<LongSparseArray<PackageState>> sparseArray3 = valueAt8;
                    int size14 = valueAt10.mServices.size();
                    parcel.writeInt(size14);
                    for (int i23 = 0; i23 < size14; i23++) {
                        parcel.writeString(valueAt10.mServices.keyAt(i23));
                        ServiceState valueAt12 = valueAt10.mServices.valueAt(i23);
                        writeCommonString(parcel, valueAt12.getProcessName());
                        valueAt12.writeToParcel(parcel, j);
                    }
                    int size15 = valueAt10.mAssociations.size();
                    parcel.writeInt(size15);
                    for (int i24 = 0; i24 < size15; i24++) {
                        writeCommonString(parcel, valueAt10.mAssociations.keyAt(i24));
                        AssociationState valueAt13 = valueAt10.mAssociations.valueAt(i24);
                        writeCommonString(parcel, valueAt13.getProcessName());
                        valueAt13.writeToParcel(this, parcel, j);
                    }
                    i19++;
                    i17 = i22;
                    valueAt8 = sparseArray3;
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
        int readInt = parcel.readInt();
        if (readInt == i) {
            return true;
        }
        this.mReadError = "bad " + str + ": " + readInt;
        return false;
    }

    static byte[] readFully(InputStream inputStream, int[] iArr) throws IOException {
        int available = inputStream.available();
        byte[] bArr = new byte[available > 0 ? available + 1 : 16384];
        int i = 0;
        while (true) {
            int read = inputStream.read(bArr, i, bArr.length - i);
            if (read < 0) {
                iArr[0] = i;
                return bArr;
            }
            i += read;
            if (i >= bArr.length) {
                byte[] bArr2 = new byte[i + 16384];
                System.arraycopy(bArr, 0, bArr2, 0, i);
                bArr = bArr2;
            }
        }
    }

    public void read(InputStream inputStream) {
        try {
            int[] iArr = new int[1];
            byte[] readFully = readFully(inputStream, iArr);
            Parcel obtain = Parcel.obtain();
            obtain.unmarshall(readFully, 0, iArr[0]);
            obtain.setDataPosition(0);
            inputStream.close();
            readFromParcel(obtain);
        } catch (IOException e) {
            this.mReadError = "caught exception: " + e;
        }
    }

    public void readFromParcel(Parcel parcel) {
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
            int readInt = parcel.readInt();
            if (readInt != 41) {
                this.mReadError = "bad version: " + readInt;
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
                readCompactedLongArray(parcel, readInt, jArr, jArr.length);
                if (this.mSysMemUsage.readFromParcel(parcel)) {
                    int readInt2 = parcel.readInt();
                    for (int i5 = 0; i5 < readInt2; i5++) {
                        int readInt3 = parcel.readInt();
                        UidState uidState = new UidState(this, readInt3);
                        if (!uidState.readFromParcel(parcel)) {
                            return;
                        }
                        this.mUidStates.put(readInt3, uidState);
                    }
                    int readInt4 = parcel.readInt();
                    if (readInt4 < 0) {
                        this.mReadError = "bad process count: " + readInt4;
                        return;
                    }
                    while (readInt4 > 0) {
                        int i6 = readInt4 - 1;
                        String readCommonString = readCommonString(parcel, readInt);
                        if (readCommonString == null) {
                            this.mReadError = "bad process name";
                            return;
                        }
                        int readInt5 = parcel.readInt();
                        if (readInt5 < 0) {
                            this.mReadError = "bad uid count: " + readInt5;
                            return;
                        }
                        while (readInt5 > 0) {
                            int i7 = readInt5 - 1;
                            int readInt6 = parcel.readInt();
                            if (readInt6 < 0) {
                                this.mReadError = "bad uid: " + readInt6;
                                return;
                            }
                            String readCommonString2 = readCommonString(parcel, readInt);
                            if (readCommonString2 == null) {
                                this.mReadError = "bad process package name";
                                return;
                            }
                            long readLong = parcel.readLong();
                            ProcessState processState3 = z3 ? this.mProcesses.get(readCommonString, readInt6) : null;
                            if (processState3 != null) {
                                if (!processState3.readFromParcel(parcel, readInt, false)) {
                                    return;
                                }
                                ProcessState processState4 = processState3;
                                str2 = readCommonString;
                                processState2 = processState4;
                            } else {
                                str2 = readCommonString;
                                processState2 = new ProcessState(this, readCommonString2, readInt6, readLong, str2);
                                if (!processState2.readFromParcel(parcel, readInt, true)) {
                                    return;
                                }
                            }
                            this.mProcesses.put(str2, readInt6, processState2);
                            UidState uidState2 = this.mUidStates.get(readInt6);
                            if (uidState2 == null) {
                                uidState2 = new UidState(this, readInt6);
                                this.mUidStates.put(readInt6, uidState2);
                            }
                            uidState2.addProcess(processState2);
                            readCommonString = str2;
                            readInt5 = i7;
                        }
                        readInt4 = i6;
                    }
                    for (int i8 = 0; i8 < readInt2; i8++) {
                        this.mUidStates.valueAt(i8).updateCombinedState(-1L);
                    }
                    int readInt7 = parcel.readInt();
                    if (readInt7 < 0) {
                        this.mReadError = "bad package count: " + readInt7;
                        return;
                    }
                    while (readInt7 > 0) {
                        int i9 = readInt7 - 1;
                        String readCommonString3 = readCommonString(parcel, readInt);
                        if (readCommonString3 == null) {
                            this.mReadError = "bad package name";
                            return;
                        }
                        int readInt8 = parcel.readInt();
                        if (readInt8 < 0) {
                            this.mReadError = "bad uid count: " + readInt8;
                            return;
                        }
                        while (readInt8 > 0) {
                            int i10 = readInt8 - 1;
                            int readInt9 = parcel.readInt();
                            if (readInt9 < 0) {
                                this.mReadError = "bad uid: " + readInt9;
                                return;
                            }
                            int readInt10 = parcel.readInt();
                            if (readInt10 < 0) {
                                this.mReadError = "bad versions count: " + readInt10;
                                return;
                            }
                            while (readInt10 > 0) {
                                int i11 = readInt10 - 1;
                                long readLong2 = parcel.readLong();
                                PackageState packageState = new PackageState(this, readCommonString3, readInt9, readLong2);
                                LongSparseArray<PackageState> longSparseArray = this.mPackages.get(readCommonString3, readInt9);
                                if (longSparseArray == null) {
                                    longSparseArray = new LongSparseArray<>();
                                    this.mPackages.put(readCommonString3, readInt9, longSparseArray);
                                }
                                longSparseArray.put(readLong2, packageState);
                                int readInt11 = parcel.readInt();
                                if (readInt11 < 0) {
                                    this.mReadError = "bad package process count: " + readInt11;
                                    return;
                                }
                                while (readInt11 > 0) {
                                    readInt11--;
                                    String readCommonString4 = readCommonString(parcel, readInt);
                                    if (readCommonString4 == null) {
                                        this.mReadError = "bad package process name";
                                        return;
                                    }
                                    int readInt12 = parcel.readInt();
                                    ProcessState processState5 = this.mProcesses.get(readCommonString4, readInt9);
                                    if (processState5 == null) {
                                        this.mReadError = "no common proc: " + readCommonString4;
                                        return;
                                    }
                                    if (readInt12 != 0) {
                                        ProcessState processState6 = z3 ? packageState.mProcesses.get(readCommonString4) : null;
                                        if (processState6 != null) {
                                            j = readLong2;
                                            i3 = 0;
                                            if (!processState6.readFromParcel(parcel, readInt, false)) {
                                                return;
                                            }
                                            i2 = readInt9;
                                            str = readCommonString4;
                                            processState = processState6;
                                            z = true;
                                        } else {
                                            j = readLong2;
                                            i3 = 0;
                                            i2 = readInt9;
                                            processState = new ProcessState(processState5, readCommonString3, i2, j, readCommonString4, 0L);
                                            str = readCommonString4;
                                            z = true;
                                            if (!processState.readFromParcel(parcel, readInt, true)) {
                                                return;
                                            }
                                        }
                                        packageState.mProcesses.put(str, processState);
                                    } else {
                                        i2 = readInt9;
                                        j = readLong2;
                                        i3 = i4;
                                        z = true;
                                        packageState.mProcesses.put(readCommonString4, processState5);
                                    }
                                    z2 = z;
                                    readInt9 = i2;
                                    i4 = i3;
                                    readLong2 = j;
                                }
                                int i12 = readInt9;
                                int i13 = i4;
                                boolean z4 = z2;
                                int readInt13 = parcel.readInt();
                                if (readInt13 < 0) {
                                    this.mReadError = "bad package service count: " + readInt13;
                                    return;
                                }
                                while (readInt13 > 0) {
                                    int i14 = readInt13 - 1;
                                    String readString = parcel.readString();
                                    if (readString == null) {
                                        this.mReadError = "bad package service name";
                                        return;
                                    }
                                    String readCommonString5 = readInt > 9 ? readCommonString(parcel, readInt) : null;
                                    ServiceState serviceState = z3 ? packageState.mServices.get(readString) : null;
                                    PackageState packageState2 = packageState;
                                    if (serviceState == null) {
                                        i = i13;
                                        serviceState = new ServiceState(this, readCommonString3, readString, readCommonString5, null);
                                    } else {
                                        i = i13;
                                    }
                                    String str3 = readCommonString3;
                                    if (!serviceState.readFromParcel(parcel)) {
                                        return;
                                    }
                                    packageState2.mServices.put(readString, serviceState);
                                    readInt13 = i14;
                                    packageState = packageState2;
                                    readCommonString3 = str3;
                                    i13 = i;
                                }
                                PackageState packageState3 = packageState;
                                String str4 = readCommonString3;
                                int i15 = i13;
                                int readInt14 = parcel.readInt();
                                if (readInt14 < 0) {
                                    this.mReadError = "bad package association count: " + readInt14;
                                    return;
                                }
                                while (readInt14 > 0) {
                                    int i16 = readInt14 - 1;
                                    String readCommonString6 = readCommonString(parcel, readInt);
                                    if (readCommonString6 == null) {
                                        this.mReadError = "bad package association name";
                                        return;
                                    }
                                    String readCommonString7 = readCommonString(parcel, readInt);
                                    AssociationState associationState = z3 ? packageState3.mAssociations.get(readCommonString6) : null;
                                    if (associationState == null) {
                                        associationState = new AssociationState(this, packageState3, readCommonString6, readCommonString7, null);
                                    }
                                    String readFromParcel = associationState.readFromParcel(this, parcel, readInt);
                                    if (readFromParcel != null) {
                                        this.mReadError = readFromParcel;
                                        return;
                                    } else {
                                        packageState3.mAssociations.put(readCommonString6, associationState);
                                        readInt14 = i16;
                                    }
                                }
                                z2 = z4;
                                readCommonString3 = str4;
                                readInt9 = i12;
                                readInt10 = i11;
                                i4 = i15;
                            }
                            readInt8 = i10;
                        }
                        readInt7 = i9;
                    }
                    int readInt15 = parcel.readInt();
                    this.mPageTypeNodes.clear();
                    this.mPageTypeNodes.ensureCapacity(readInt15);
                    this.mPageTypeZones.clear();
                    this.mPageTypeZones.ensureCapacity(readInt15);
                    this.mPageTypeLabels.clear();
                    this.mPageTypeLabels.ensureCapacity(readInt15);
                    this.mPageTypeSizes.clear();
                    this.mPageTypeSizes.ensureCapacity(readInt15);
                    while (i4 < readInt15) {
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
                long uptimeMillis = SystemClock.uptimeMillis();
                PackageState packageStateLocked = processStats.getPackageStateLocked(processState.getPackage(), packageState.mUid, processState.getVersion());
                if (packageStateLocked != null) {
                    ProcessState clone = processState.clone(uptimeMillis);
                    packageStateLocked.mProcesses.put(processState.getName(), clone);
                    for (int size = packageStateLocked.mServices.size() - 1; size >= 0; size--) {
                        ServiceState valueAt = packageStateLocked.mServices.valueAt(size);
                        if (valueAt.getProcess() == processState) {
                            valueAt.setProcess(clone);
                        }
                    }
                    for (int size2 = packageStateLocked.mAssociations.size() - 1; size2 >= 0; size2--) {
                        AssociationState valueAt2 = packageStateLocked.mAssociations.valueAt(size2);
                        if (valueAt2.getProcess() == processState) {
                            valueAt2.setProcess(clone);
                        }
                    }
                } else {
                    Slog.w(TAG, "Cloning proc state: no package state " + processState.getPackage() + "/" + packageState.mUid + " for proc " + processState.getName());
                }
                processState2 = new ProcessState(processState, packageState.mPackageName, packageState.mUid, packageState.mVersionCode, str2, uptimeMillis);
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
        ProcessState processState;
        PackageState packageStateLocked = getPackageStateLocked(str, i, j);
        ServiceState serviceState = packageStateLocked.mServices.get(str3);
        if (serviceState != null) {
            return serviceState;
        }
        if (str2 != null) {
            processState = getProcessStateLocked(str, i, j, str2);
            str4 = str2;
        } else {
            str4 = str2;
            processState = null;
        }
        ServiceState serviceState2 = new ServiceState(this, str, str3, str4, processState);
        packageStateLocked.mServices.put(str3, serviceState2);
        return serviceState2;
    }

    public AssociationState getAssociationStateLocked(String str, int i, long j, String str2, String str3) {
        ProcessStats processStats;
        String str4;
        ProcessState processState;
        PackageState packageStateLocked = getPackageStateLocked(str, i, j);
        AssociationState associationState = packageStateLocked.mAssociations.get(str3);
        if (associationState != null) {
            return associationState;
        }
        if (str2 != null) {
            processState = getProcessStateLocked(str, i, j, str2);
            processStats = this;
            str4 = str2;
        } else {
            processStats = this;
            str4 = str2;
            processState = null;
        }
        AssociationState associationState2 = new AssociationState(processStats, packageStateLocked, str3, str4, processState);
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
                                long uptimeMillis = SystemClock.uptimeMillis();
                                if (this.mNextInverseProcStateWarningUptime > uptimeMillis) {
                                    this.mSkippedInverseProcStateWarningCount++;
                                } else {
                                    Slog.w(TAG, "Tracking association " + sourceState + " whose proc state " + sourceState.mProcState + " is better than process " + process + " proc state " + combinedState + " (" + this.mSkippedInverseProcStateWarningCount + " skipped)");
                                    this.mSkippedInverseProcStateWarningCount = 0;
                                    this.mNextInverseProcStateWarningUptime = uptimeMillis + 10000;
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
        int compareTo = associationDumpContainer.mState.getProcessName().compareTo(associationDumpContainer2.mState.getProcessName());
        if (compareTo != 0) {
            return compareTo;
        }
        if (associationDumpContainer.mActiveTime != associationDumpContainer2.mActiveTime) {
            return associationDumpContainer.mActiveTime > associationDumpContainer2.mActiveTime ? -1 : 1;
        }
        if (associationDumpContainer.mTotalTime != associationDumpContainer2.mTotalTime) {
            return associationDumpContainer.mTotalTime > associationDumpContainer2.mTotalTime ? -1 : 1;
        }
        int compareTo2 = associationDumpContainer.mState.getName().compareTo(associationDumpContainer2.mState.getName());
        if (compareTo2 != 0) {
            return compareTo2;
        }
        return 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:211:0x058d, code lost:
    
        if (r15.equals(r5.getPackage()) == false) goto L193;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dumpLocked(java.io.PrintWriter r41, java.lang.String r42, long r43, boolean r45, boolean r46, boolean r47, boolean r48, int r49) {
        /*
            Method dump skipped, instructions count: 2181
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.app.procstats.ProcessStats.dumpLocked(java.io.PrintWriter, java.lang.String, long, boolean, boolean, boolean, boolean, int):void");
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
        long printMemoryCategory = printMemoryCategory(printWriter, "  ", "Native ", totalMemoryUseCollection.sysMemNativeWeight, totalMemoryUseCollection.totalTime, printMemoryCategory(printWriter, "  ", "Kernel ", totalMemoryUseCollection.sysMemKernelWeight, totalMemoryUseCollection.totalTime, 0L, totalMemoryUseCollection.sysMemSamples), totalMemoryUseCollection.sysMemSamples);
        for (int i = 0; i < 16; i++) {
            if (i != 9) {
                printMemoryCategory = printMemoryCategory(printWriter, "  ", DumpUtils.STATE_NAMES[i], totalMemoryUseCollection.processStateWeight[i], totalMemoryUseCollection.totalTime, printMemoryCategory, totalMemoryUseCollection.processStateSamples[i]);
            }
        }
        long printMemoryCategory2 = printMemoryCategory(printWriter, "  ", "Z-Ram  ", totalMemoryUseCollection.sysMemZRamWeight, totalMemoryUseCollection.totalTime, printMemoryCategory(printWriter, "  ", "Free   ", totalMemoryUseCollection.sysMemFreeWeight, totalMemoryUseCollection.totalTime, printMemoryCategory(printWriter, "  ", "Cached ", totalMemoryUseCollection.sysMemCachedWeight, totalMemoryUseCollection.totalTime, printMemoryCategory, totalMemoryUseCollection.sysMemSamples), totalMemoryUseCollection.sysMemSamples), totalMemoryUseCollection.sysMemSamples);
        printWriter.print("  TOTAL  : ");
        DebugUtils.printSizeValue(printWriter, printMemoryCategory2);
        printWriter.println();
        printMemoryCategory(printWriter, "  ", DumpUtils.STATE_NAMES[9], totalMemoryUseCollection.processStateWeight[9], totalMemoryUseCollection.totalTime, printMemoryCategory2, totalMemoryUseCollection.processStateSamples[9]);
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
        ArrayList<ProcessState> collectProcessesLocked = collectProcessesLocked(iArr, iArr2, iArr3, iArr4, j, str4, z);
        if (collectProcessesLocked.size() > 0) {
            if (str != null) {
                printWriter.println();
                printWriter.println(str);
            }
            DumpUtils.dumpProcessSummaryLocked(printWriter, str2, str3, collectProcessesLocked, iArr, iArr2, iArr4, j, j2);
        }
    }

    public ArrayList<ProcessState> collectProcessesLocked(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, long j, String str, boolean z) {
        ArraySet arraySet = new ArraySet();
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> map = this.mPackages.getMap();
        for (int i = 0; i < map.size(); i++) {
            String keyAt = map.keyAt(i);
            SparseArray<LongSparseArray<PackageState>> valueAt = map.valueAt(i);
            for (int i2 = 0; i2 < valueAt.size(); i2++) {
                LongSparseArray<PackageState> valueAt2 = valueAt.valueAt(i2);
                int size = valueAt2.size();
                for (int i3 = 0; i3 < size; i3++) {
                    PackageState valueAt3 = valueAt2.valueAt(i3);
                    int size2 = valueAt3.mProcesses.size();
                    boolean z2 = str == null || str.equals(keyAt);
                    for (int i4 = 0; i4 < size2; i4++) {
                        ProcessState valueAt4 = valueAt3.mProcesses.valueAt(i4);
                        if ((z2 || str.equals(valueAt4.getName())) && (!z || valueAt4.isInUse())) {
                            arraySet.add(valueAt4.getCommonProcess());
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
        long uptimeMillis = SystemClock.uptimeMillis();
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
                String keyAt = map.keyAt(i3);
                if (str2 == null || str2.equals(keyAt)) {
                    SparseArray<LongSparseArray<PackageState>> valueAt = map.valueAt(i3);
                    int i4 = 0;
                    while (i4 < valueAt.size()) {
                        int keyAt2 = valueAt.keyAt(i4);
                        LongSparseArray<PackageState> valueAt2 = valueAt.valueAt(i4);
                        int i5 = 0;
                        while (i5 < valueAt2.size()) {
                            long keyAt3 = valueAt2.keyAt(i5);
                            PackageState valueAt3 = valueAt2.valueAt(i5);
                            int i6 = i2;
                            int size = valueAt3.mProcesses.size();
                            SparseArray<LongSparseArray<PackageState>> sparseArray = valueAt;
                            int size2 = valueAt3.mServices.size();
                            int size3 = valueAt3.mAssociations.size();
                            if ((i & 2) != 0) {
                                int i7 = 0;
                                while (i7 < size) {
                                    long j = keyAt3;
                                    int i8 = i4;
                                    int i9 = keyAt2;
                                    valueAt3.mProcesses.valueAt(i7).dumpPackageProcCheckin(printWriter2, keyAt, i9, j, valueAt3.mProcesses.keyAt(i7), uptimeMillis);
                                    printWriter2 = printWriter;
                                    i5 = i5;
                                    size2 = size2;
                                    i7++;
                                    size3 = size3;
                                    map = map;
                                    keyAt2 = i9;
                                    i4 = i8;
                                    valueAt2 = valueAt2;
                                    keyAt3 = j;
                                }
                            }
                            ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> arrayMap = map;
                            int i10 = size2;
                            int i11 = size3;
                            int i12 = i5;
                            long j2 = keyAt3;
                            int i13 = i4;
                            int i14 = keyAt2;
                            LongSparseArray<PackageState> longSparseArray = valueAt2;
                            if ((i & 4) != 0) {
                                for (int i15 = 0; i15 < i10; i15++) {
                                    valueAt3.mServices.valueAt(i15).dumpTimesCheckin(printWriter, keyAt, i14, j2, DumpUtils.collapseString(keyAt, valueAt3.mServices.keyAt(i15)), uptimeMillis);
                                }
                            }
                            if ((i & 8) != 0) {
                                for (int i16 = 0; i16 < i11; i16++) {
                                    valueAt3.mAssociations.valueAt(i16).dumpTimesCheckin(printWriter, keyAt, i14, j2, DumpUtils.collapseString(keyAt, valueAt3.mAssociations.keyAt(i16)), uptimeMillis);
                                }
                            }
                            i5 = i12 + 1;
                            printWriter2 = printWriter;
                            keyAt2 = i14;
                            i4 = i13;
                            valueAt2 = longSparseArray;
                            i2 = i6;
                            valueAt = sparseArray;
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
                String keyAt4 = map2.keyAt(i18);
                SparseArray<ProcessState> valueAt4 = map2.valueAt(i18);
                for (int i19 = 0; i19 < valueAt4.size(); i19++) {
                    valueAt4.valueAt(i19).dumpProcCheckin(printWriter, keyAt4, valueAt4.keyAt(i19), uptimeMillis);
                }
            }
        }
        printWriter.print("total");
        DumpUtils.dumpAdjTimesCheckin(printWriter, ",", this.mMemFactorDurations, this.mMemFactor, this.mStartTime, uptimeMillis);
        printWriter.println();
        int keyCount = this.mSysMemUsage.getKeyCount();
        if (keyCount > 0) {
            printWriter.print("sysmemusage");
            for (int i20 = 0; i20 < keyCount; i20++) {
                int keyAt5 = this.mSysMemUsage.getKeyAt(i20);
                byte idFromKey = SparseMappingTable.getIdFromKey(keyAt5);
                printWriter.print(",");
                DumpUtils.printProcStateTag(printWriter, idFromKey);
                int i21 = 0;
                while (i21 < 16) {
                    int i22 = i17;
                    if (i21 > i22) {
                        printWriter.print(":");
                    }
                    printWriter.print(this.mSysMemUsage.getValue(keyAt5, i21));
                    i21++;
                    i17 = i22;
                }
            }
        }
        printWriter.println();
        TotalMemoryUseCollection totalMemoryUseCollection = new TotalMemoryUseCollection(ALL_SCREEN_ADJ, ALL_MEM_ADJ);
        computeTotalMemoryUse(totalMemoryUseCollection, uptimeMillis);
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
            long start = protoOutputStream.start(2246267895818L);
            protoOutputStream.write(1120986464257L, this.mPageTypeNodes.get(i2).intValue());
            protoOutputStream.write(1138166333442L, this.mPageTypeZones.get(i2));
            protoOutputStream.write(1138166333443L, this.mPageTypeLabels.get(i2));
            int[] iArr = this.mPageTypeSizes.get(i2);
            int length = iArr == null ? 0 : iArr.length;
            for (int i3 = 0; i3 < length; i3++) {
                protoOutputStream.write(2220498092036L, iArr[i3]);
            }
            protoOutputStream.end(start);
        }
        ArrayMap<String, SparseArray<ProcessState>> map = this.mProcesses.getMap();
        if ((i & 1) != 0) {
            for (int i4 = 0; i4 < map.size(); i4++) {
                String keyAt = map.keyAt(i4);
                SparseArray<ProcessState> valueAt = map.valueAt(i4);
                for (int i5 = 0; i5 < valueAt.size(); i5++) {
                    valueAt.valueAt(i5).dumpDebug(protoOutputStream, 2246267895816L, keyAt, valueAt.keyAt(i5), j);
                }
            }
        }
        if ((i & 14) != 0) {
            ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> map2 = this.mPackages.getMap();
            for (int i6 = 0; i6 < map2.size(); i6++) {
                SparseArray<LongSparseArray<PackageState>> valueAt2 = map2.valueAt(i6);
                for (int i7 = 0; i7 < valueAt2.size(); i7++) {
                    LongSparseArray<PackageState> valueAt3 = valueAt2.valueAt(i7);
                    for (int i8 = 0; i8 < valueAt3.size(); i8++) {
                        valueAt3.valueAt(i8).dumpDebug(protoOutputStream, 2246267895817L, j, i);
                    }
                }
            }
        }
    }

    public void dumpAggregatedProtoForStatsd(ProtoOutputStream[] protoOutputStreamArr, long j) {
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
            String keyAt = map.keyAt(i2);
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
            for (SparseArray<ProcessState> valueAt = map.valueAt(i2); i4 < valueAt.size(); valueAt = valueAt) {
                valueAt.valueAt(i4).dumpAggregatedProtoForStatsd(protoOutputStreamArr[i3], 2246267895816L, keyAt, valueAt.keyAt(i4), this.mTimePeriodEndRealtime, processMap, sparseArray);
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
            SparseArray<ProcessState> valueAt = map.valueAt(i);
            int size2 = valueAt.size();
            for (int i2 = 0; i2 < size2; i2++) {
                consumer.accept(valueAt.valueAt(i2));
            }
        }
    }

    void forEachAssociation(QuintConsumer<AssociationState, Integer, String, AssociationState.SourceKey, AssociationState.SourceState> quintConsumer) {
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> map = this.mPackages.getMap();
        int size = map.size();
        for (int i = 0; i < size; i++) {
            SparseArray<LongSparseArray<PackageState>> valueAt = map.valueAt(i);
            int size2 = valueAt.size();
            for (int i2 = 0; i2 < size2; i2++) {
                int keyAt = valueAt.keyAt(i2);
                LongSparseArray<PackageState> valueAt2 = valueAt.valueAt(i2);
                int size3 = valueAt2.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    PackageState valueAt3 = valueAt2.valueAt(i3);
                    int size4 = valueAt3.mAssociations.size();
                    for (int i4 = 0; i4 < size4; i4++) {
                        String keyAt2 = valueAt3.mAssociations.keyAt(i4);
                        AssociationState valueAt4 = valueAt3.mAssociations.valueAt(i4);
                        int size5 = valueAt4.mSources.size();
                        int i5 = 0;
                        while (i5 < size5) {
                            AssociationState.SourceState valueAt5 = valueAt4.mSources.valueAt(i5);
                            quintConsumer.accept(valueAt4, Integer.valueOf(keyAt), keyAt2, valueAt4.mSources.keyAt(i5), valueAt5);
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
                ProcessStats.this.lambda$dumpProcessState$1(i, statsEventOutput, (ProcessState) obj);
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
                ProcessStats.this.lambda$dumpProcessAssociation$2(statsEventOutput, i, (AssociationState) obj, (Integer) obj2, (String) obj3, (AssociationState.SourceKey) obj4, (AssociationState.SourceState) obj5);
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
            String keyAt = map.keyAt(size);
            SparseArray<LongSparseArray<PackageState>> valueAt = map.valueAt(size);
            int size2 = valueAt.size() - i;
            while (size2 >= 0) {
                LongSparseArray<PackageState> valueAt2 = valueAt.valueAt(size2);
                int size3 = valueAt2.size() - i;
                while (size3 >= 0) {
                    PackageState valueAt3 = valueAt2.valueAt(size3);
                    int i2 = (str == null || str.equals(keyAt)) ? i : 0;
                    for (int size4 = valueAt3.mProcesses.size() - i; size4 >= 0; size4--) {
                        ProcessState valueAt4 = valueAt3.mProcesses.valueAt(size4);
                        if ((i2 != 0 || str.equals(valueAt4.getName())) && (!z || valueAt4.isInUse())) {
                            String name = valueAt4.getName();
                            int uid = valueAt4.getUid();
                            ArraySet<PackageState> arraySet2 = processMap.get(name, uid);
                            if (arraySet2 == null) {
                                arraySet = new ArraySet<>();
                                processMap.put(name, uid, arraySet);
                            } else {
                                arraySet = arraySet2;
                            }
                            arraySet.add(valueAt3);
                            ArraySet<String> arraySet3 = sparseArray.get(uid);
                            if (arraySet3 == null) {
                                arraySet3 = new ArraySet<>();
                                sparseArray.put(uid, arraySet3);
                            }
                            arraySet3.add(valueAt3.mPackageName);
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
        IProcessStats asInterface;
        int i;
        if ((processState.isMultiPackage() && processState.getCommonProcess() != processState) || (arrayMap = processState.mCommonSources) == null || arrayMap.isEmpty() || (asInterface = IProcessStats.Stub.asInterface(ServiceManager.getService(SERVICE_NAME))) == null) {
            return;
        }
        try {
            long minAssociationDumpDuration = asInterface.getMinAssociationDumpDuration();
            ?? r10 = 1;
            int size = arrayMap.size() - 1;
            while (size >= 0) {
                AssociationState.SourceState valueAt = arrayMap.valueAt(size);
                long j3 = valueAt.mDuration;
                if (valueAt.mNesting > 0) {
                    j3 += j2 - valueAt.mStartUptime;
                }
                long j4 = j3;
                if (j4 < minAssociationDumpDuration) {
                    i = size;
                } else {
                    AssociationState.SourceKey keyAt = arrayMap.keyAt(size);
                    long start = protoOutputStream.start(j);
                    int indexOfKey = sparseArray.indexOfKey(keyAt.mUid);
                    i = size;
                    ProcessState.writeCompressedProcessName(protoOutputStream, 1138166333441L, keyAt.mProcess, keyAt.mPackage, (indexOfKey < 0 || sparseArray.valueAt(indexOfKey).size() <= r10) ? false : r10);
                    protoOutputStream.write(1120986464261L, keyAt.mUid);
                    protoOutputStream.write(1120986464259L, valueAt.mCount);
                    protoOutputStream.write(1120986464260L, (int) (j4 / 1000));
                    protoOutputStream.end(start);
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
            long start = protoOutputStream.start(j);
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
            protoOutputStream.end(start);
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
