package com.android.server.cpu;

import android.os.SystemClock;
import android.system.Os;
import android.system.OsConstants;
import android.util.IndentingPrintWriter;
import android.util.IntArray;
import android.util.LongSparseLongArray;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.utils.Slogf;
import java.io.File;
import java.io.FileFilter;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class CpuInfoReader {
    public File mCpuFreqDir;
    public final SparseArray mCpuFreqPolicyDirsById;
    public final SparseIntArray mCpusetCategoriesByCpus;
    public final File mCpusetDir;
    public SparseArray mCumulativeCpuUsageStats;
    public boolean mHasTimeInStateFile;
    public boolean mIsEnabled;
    public SparseArray mLastReadCpuInfos;
    public long mLastReadUptimeMillis;
    public final long mMinReadIntervalMillis;
    public File mProcStatFile;
    public final SparseArray mStaticPolicyInfoById;
    public final SparseArray mTimeInStateByPolicyId;
    public static final Pattern PROC_STAT_PATTERN = Pattern.compile("cpu(?<core>[0-9]+)\\s(?<userClockTicks>[0-9]+)\\s(?<niceClockTicks>[0-9]+)\\s(?<sysClockTicks>[0-9]+)\\s(?<idleClockTicks>[0-9]+)\\s(?<iowaitClockTicks>[0-9]+)\\s(?<irqClockTicks>[0-9]+)\\s(?<softirqClockTicks>[0-9]+)\\s(?<stealClockTicks>[0-9]+)\\s(?<guestClockTicks>[0-9]+)\\s(?<guestNiceClockTicks>[0-9]+)");
    public static final Pattern TIME_IN_STATE_PATTERN = Pattern.compile("(?<freqKHz>[0-9]+)\\s(?<time>[0-9]+)");
    public static final long MILLIS_PER_CLOCK_TICK = 1000 / Os.sysconf(OsConstants._SC_CLK_TCK);

    public CpuInfoReader() {
        this(new File("/dev/cpuset"), new File("/sys/devices/system/cpu/cpufreq"), new File("/proc/stat"), 500L);
    }

    public CpuInfoReader(File file, File file2, File file3, long j) {
        this.mCpusetCategoriesByCpus = new SparseIntArray();
        this.mCpuFreqPolicyDirsById = new SparseArray();
        this.mStaticPolicyInfoById = new SparseArray();
        this.mTimeInStateByPolicyId = new SparseArray();
        this.mCumulativeCpuUsageStats = new SparseArray();
        this.mCpusetDir = file;
        this.mCpuFreqDir = file2;
        this.mProcStatFile = file3;
        this.mMinReadIntervalMillis = j;
    }

    public boolean init() {
        boolean z;
        if (this.mCpuFreqPolicyDirsById.size() > 0) {
            Slogf.m102w(CpuMonitorService.TAG, "Ignoring duplicate CpuInfoReader init request");
            return this.mIsEnabled;
        }
        File[] listFiles = this.mCpuFreqDir.listFiles(new FileFilter() { // from class: com.android.server.cpu.CpuInfoReader$$ExternalSyntheticLambda1
            @Override // java.io.FileFilter
            public final boolean accept(File file) {
                boolean lambda$init$0;
                lambda$init$0 = CpuInfoReader.lambda$init$0(file);
                return lambda$init$0;
            }
        });
        if (listFiles == null || listFiles.length == 0) {
            Slogf.m105w(CpuMonitorService.TAG, "Missing CPU frequency policy directories at %s", this.mCpuFreqDir.getAbsolutePath());
            return false;
        }
        populateCpuFreqPolicyDirsById(listFiles);
        if (this.mCpuFreqPolicyDirsById.size() == 0) {
            Slogf.m92e(CpuMonitorService.TAG, "Failed to parse CPU frequency policy directory paths: %s", Arrays.toString(listFiles));
            return false;
        }
        readStaticPolicyInfo();
        if (this.mStaticPolicyInfoById.size() == 0) {
            Slogf.m92e(CpuMonitorService.TAG, "Failed to read static CPU frequency policy info from policy dirs: %s", Arrays.toString(listFiles));
            return false;
        }
        if (!this.mProcStatFile.exists()) {
            Slogf.m92e(CpuMonitorService.TAG, "Missing proc stat file at %s", this.mProcStatFile.getAbsolutePath());
            return false;
        }
        readCpusetCategories();
        if (this.mCpusetCategoriesByCpus.size() == 0) {
            Slogf.m92e(CpuMonitorService.TAG, "Failed to read cpuset information from %s", this.mCpusetDir.getAbsolutePath());
            return false;
        }
        for (int i = 0; i < this.mCpuFreqPolicyDirsById.size() && !(z = this.mHasTimeInStateFile); i++) {
            this.mHasTimeInStateFile = z | new File((File) this.mCpuFreqPolicyDirsById.valueAt(i), "stats/time_in_state").exists();
        }
        if (!this.mHasTimeInStateFile) {
            Slogf.m90e(CpuMonitorService.TAG, "Time in state file not available for any cpufreq policy");
        }
        this.mIsEnabled = true;
        return true;
    }

    public static /* synthetic */ boolean lambda$init$0(File file) {
        return file.isDirectory() && file.getName().startsWith("policy");
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x00e4, code lost:
    
        if (r12.isOnline != false) goto L46;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SparseArray readCpuInfos() {
        SparseArray sparseArray;
        SparseArray sparseArray2;
        CpuInfoReader cpuInfoReader = this;
        if (!cpuInfoReader.mIsEnabled) {
            return null;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        long j = cpuInfoReader.mLastReadUptimeMillis;
        long j2 = 0;
        if (j > 0 && uptimeMillis - j < cpuInfoReader.mMinReadIntervalMillis) {
            Slogf.m105w(CpuMonitorService.TAG, "Skipping reading from device and returning the last read CpuInfos. Last read was %d ms ago, min read interval is %d ms", Long.valueOf(uptimeMillis - j), Long.valueOf(cpuInfoReader.mMinReadIntervalMillis));
            return cpuInfoReader.mLastReadCpuInfos;
        }
        cpuInfoReader.mLastReadUptimeMillis = uptimeMillis;
        cpuInfoReader.mLastReadCpuInfos = null;
        SparseArray readLatestCpuUsageStats = readLatestCpuUsageStats();
        if (readLatestCpuUsageStats == null || readLatestCpuUsageStats.size() == 0) {
            Slogf.m90e(CpuMonitorService.TAG, "Failed to read latest CPU usage stats");
            return null;
        }
        SparseArray readDynamicPolicyInfo = readDynamicPolicyInfo();
        if (readDynamicPolicyInfo.size() == 0) {
            Slogf.m90e(CpuMonitorService.TAG, "Failed to read dynamic policy infos");
            return null;
        }
        SparseArray sparseArray3 = new SparseArray();
        int i = 0;
        while (i < cpuInfoReader.mStaticPolicyInfoById.size()) {
            int keyAt = cpuInfoReader.mStaticPolicyInfoById.keyAt(i);
            StaticPolicyInfo staticPolicyInfo = (StaticPolicyInfo) cpuInfoReader.mStaticPolicyInfoById.valueAt(i);
            DynamicPolicyInfo dynamicPolicyInfo = (DynamicPolicyInfo) readDynamicPolicyInfo.get(keyAt);
            if (dynamicPolicyInfo == null) {
                Slogf.m105w(CpuMonitorService.TAG, "Missing dynamic policy info for policy ID %d", Integer.valueOf(keyAt));
            } else {
                long j3 = dynamicPolicyInfo.curCpuFreqKHz;
                if (j3 != j2) {
                    long j4 = staticPolicyInfo.maxCpuFreqKHz;
                    if (j4 != j2) {
                        if (j3 > j4) {
                            Slogf.m105w(CpuMonitorService.TAG, "Current CPU frequency (%d) is greater than maximum CPU frequency (%d) for policy ID (%d). Skipping CPU frequency policy", Long.valueOf(j3), Long.valueOf(staticPolicyInfo.maxCpuFreqKHz), Integer.valueOf(keyAt));
                        } else {
                            int i2 = 0;
                            while (i2 < staticPolicyInfo.relatedCpuCores.size()) {
                                int i3 = staticPolicyInfo.relatedCpuCores.get(i2);
                                CpuInfo cpuInfo = (CpuInfo) sparseArray3.get(i3);
                                if (cpuInfo != null) {
                                    Slogf.wtf(CpuMonitorService.TAG, "CPU info already available for the CPU core %d", Integer.valueOf(i3));
                                }
                                int i4 = cpuInfoReader.mCpusetCategoriesByCpus.get(i3, -1);
                                if (i4 < 0) {
                                    Slogf.m105w(CpuMonitorService.TAG, "Missing cpuset information for the CPU core %d", Integer.valueOf(i3));
                                } else {
                                    CpuUsageStats cpuUsageStats = (CpuUsageStats) readLatestCpuUsageStats.get(i3);
                                    if (dynamicPolicyInfo.affectedCpuCores.indexOf(i3) < 0) {
                                        sparseArray3.append(i3, new CpuInfo(i3, i4, false, 0L, staticPolicyInfo.maxCpuFreqKHz, 0L, cpuUsageStats));
                                    } else if (cpuUsageStats == null) {
                                        Slogf.m105w(CpuMonitorService.TAG, "Missing CPU usage information for online CPU core %d", Integer.valueOf(i3));
                                    } else {
                                        sparseArray2 = readLatestCpuUsageStats;
                                        CpuInfo cpuInfo2 = new CpuInfo(i3, i4, true, dynamicPolicyInfo.curCpuFreqKHz, staticPolicyInfo.maxCpuFreqKHz, dynamicPolicyInfo.avgTimeInStateCpuFreqKHz, cpuUsageStats);
                                        sparseArray3.append(i3, cpuInfo2);
                                        if (CpuMonitorService.DEBUG) {
                                            Slogf.m88d(CpuMonitorService.TAG, "Added %s for CPU core %d", cpuInfo2, Integer.valueOf(i3));
                                        }
                                        i2++;
                                        cpuInfoReader = this;
                                        readLatestCpuUsageStats = sparseArray2;
                                    }
                                }
                                sparseArray2 = readLatestCpuUsageStats;
                                i2++;
                                cpuInfoReader = this;
                                readLatestCpuUsageStats = sparseArray2;
                            }
                        }
                    }
                }
                sparseArray = readLatestCpuUsageStats;
                Slogf.m105w(CpuMonitorService.TAG, "Current and maximum CPU frequency information mismatch/missing for policy ID %d", Integer.valueOf(keyAt));
                i++;
                j2 = 0;
                cpuInfoReader = this;
                readLatestCpuUsageStats = sparseArray;
            }
            sparseArray = readLatestCpuUsageStats;
            i++;
            j2 = 0;
            cpuInfoReader = this;
            readLatestCpuUsageStats = sparseArray;
        }
        cpuInfoReader.mLastReadCpuInfos = sparseArray3;
        return sparseArray3;
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.printf("*%s*\n", new Object[]{CpuInfoReader.class.getSimpleName()});
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printf("mCpusetDir = %s\n", new Object[]{this.mCpusetDir.getAbsolutePath()});
        indentingPrintWriter.printf("mCpuFreqDir = %s\n", new Object[]{this.mCpuFreqDir.getAbsolutePath()});
        indentingPrintWriter.printf("mProcStatFile = %s\n", new Object[]{this.mProcStatFile.getAbsolutePath()});
        indentingPrintWriter.printf("mIsEnabled = %s\n", new Object[]{Boolean.valueOf(this.mIsEnabled)});
        indentingPrintWriter.printf("mHasTimeInStateFile = %s\n", new Object[]{Boolean.valueOf(this.mHasTimeInStateFile)});
        indentingPrintWriter.printf("mLastReadUptimeMillis = %d\n", new Object[]{Long.valueOf(this.mLastReadUptimeMillis)});
        indentingPrintWriter.printf("mMinReadIntervalMillis = %d\n", new Object[]{Long.valueOf(this.mMinReadIntervalMillis)});
        indentingPrintWriter.printf("Cpuset categories by CPU core:\n", new Object[0]);
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mCpusetCategoriesByCpus.size(); i++) {
            indentingPrintWriter.printf("CPU core id = %d, %s\n", new Object[]{Integer.valueOf(this.mCpusetCategoriesByCpus.keyAt(i)), toCpusetCategoriesStr(this.mCpusetCategoriesByCpus.valueAt(i))});
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Cpu frequency policy directories by policy id:");
        indentingPrintWriter.increaseIndent();
        for (int i2 = 0; i2 < this.mCpuFreqPolicyDirsById.size(); i2++) {
            indentingPrintWriter.printf("Policy id = %d, Dir = %s\n", new Object[]{Integer.valueOf(this.mCpuFreqPolicyDirsById.keyAt(i2)), this.mCpuFreqPolicyDirsById.valueAt(i2)});
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Static cpu frequency policy infos by policy id:");
        indentingPrintWriter.increaseIndent();
        for (int i3 = 0; i3 < this.mStaticPolicyInfoById.size(); i3++) {
            indentingPrintWriter.printf("Policy id = %d, %s\n", new Object[]{Integer.valueOf(this.mStaticPolicyInfoById.keyAt(i3)), this.mStaticPolicyInfoById.valueAt(i3)});
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Cpu time in frequency state by policy id:");
        indentingPrintWriter.increaseIndent();
        for (int i4 = 0; i4 < this.mTimeInStateByPolicyId.size(); i4++) {
            indentingPrintWriter.printf("Policy id = %d, Time(millis) in state by CPU frequency(KHz) = %s\n", new Object[]{Integer.valueOf(this.mTimeInStateByPolicyId.keyAt(i4)), this.mTimeInStateByPolicyId.valueAt(i4)});
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Last read CPU infos:");
        indentingPrintWriter.increaseIndent();
        for (int i5 = 0; i5 < this.mLastReadCpuInfos.size(); i5++) {
            indentingPrintWriter.printf("%s\n", new Object[]{this.mLastReadCpuInfos.valueAt(i5)});
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Latest cumulative CPU usage stats by CPU core:");
        indentingPrintWriter.increaseIndent();
        for (int i6 = 0; i6 < this.mCumulativeCpuUsageStats.size(); i6++) {
            indentingPrintWriter.printf("CPU core id = %d, %s\n", new Object[]{Integer.valueOf(this.mCumulativeCpuUsageStats.keyAt(i6)), this.mCumulativeCpuUsageStats.valueAt(i6)});
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    public boolean setCpuFreqDir(File file) {
        File[] listFiles = file.listFiles(new FileFilter() { // from class: com.android.server.cpu.CpuInfoReader$$ExternalSyntheticLambda0
            @Override // java.io.FileFilter
            public final boolean accept(File file2) {
                boolean lambda$setCpuFreqDir$1;
                lambda$setCpuFreqDir$1 = CpuInfoReader.lambda$setCpuFreqDir$1(file2);
                return lambda$setCpuFreqDir$1;
            }
        });
        if (listFiles == null || listFiles.length == 0) {
            Slogf.m105w(CpuMonitorService.TAG, "Failed to set CPU frequency directory. Missing policy directories at %s", file.getAbsolutePath());
            return false;
        }
        populateCpuFreqPolicyDirsById(listFiles);
        int size = this.mCpuFreqPolicyDirsById.size();
        int size2 = this.mStaticPolicyInfoById.size();
        if (size == 0 || size != size2) {
            Slogf.m92e(CpuMonitorService.TAG, "Failed to set CPU frequency directory to %s. Total CPU frequency policies (%d) under new path is either 0 or not equal to initial total CPU frequency policies. Clearing CPU frequency policy directories", file.getAbsolutePath(), Integer.valueOf(size), Integer.valueOf(size2));
            this.mCpuFreqPolicyDirsById.clear();
            return false;
        }
        this.mCpuFreqDir = file;
        return true;
    }

    public static /* synthetic */ boolean lambda$setCpuFreqDir$1(File file) {
        return file.isDirectory() && file.getName().startsWith("policy");
    }

    public boolean setProcStatFile(File file) {
        if (!file.exists()) {
            Slogf.m92e(CpuMonitorService.TAG, "Missing proc stat file at %s", file.getAbsolutePath());
            return false;
        }
        this.mProcStatFile = file;
        return true;
    }

    public final void populateCpuFreqPolicyDirsById(File[] fileArr) {
        this.mCpuFreqPolicyDirsById.clear();
        for (File file : fileArr) {
            String substring = file.getName().substring(6);
            if (!substring.isEmpty()) {
                this.mCpuFreqPolicyDirsById.append(Integer.parseInt(substring), file);
                if (CpuMonitorService.DEBUG) {
                    Slogf.m88d(CpuMonitorService.TAG, "Cached policy directory %s for policy id %s", file, substring);
                }
            }
        }
    }

    public final void readCpusetCategories() {
        int i;
        File[] listFiles = this.mCpusetDir.listFiles(new CpuInfoReader$$ExternalSyntheticLambda2());
        if (listFiles == null) {
            Slogf.m92e(CpuMonitorService.TAG, "Missing cpuset directories at %s", this.mCpusetDir.getAbsolutePath());
            return;
        }
        for (File file : listFiles) {
            String name = file.getName();
            name.hashCode();
            if (name.equals("background")) {
                i = 2;
            } else if (name.equals("top-app")) {
                i = 1;
            }
            File file2 = new File(file.getPath(), "cpus");
            IntArray readCpuCores = readCpuCores(file2);
            if (readCpuCores == null || readCpuCores.size() == 0) {
                Slogf.m92e(CpuMonitorService.TAG, "Failed to read CPU cores from %s", file2.getAbsolutePath());
            } else {
                for (int i2 = 0; i2 < readCpuCores.size(); i2++) {
                    int i3 = this.mCpusetCategoriesByCpus.get(readCpuCores.get(i2)) | i;
                    this.mCpusetCategoriesByCpus.append(readCpuCores.get(i2), i3);
                    if (CpuMonitorService.DEBUG) {
                        Slogf.m88d(CpuMonitorService.TAG, "Mapping CPU core id %d with cpuset categories [%s]", Integer.valueOf(readCpuCores.get(i2)), toCpusetCategoriesStr(i3));
                    }
                }
            }
        }
    }

    public final void readStaticPolicyInfo() {
        for (int i = 0; i < this.mCpuFreqPolicyDirsById.size(); i++) {
            int keyAt = this.mCpuFreqPolicyDirsById.keyAt(i);
            File file = (File) this.mCpuFreqPolicyDirsById.valueAt(i);
            long readCpuFreqKHz = readCpuFreqKHz(new File(file, "scaling_max_freq"));
            if (readCpuFreqKHz == 0) {
                Slogf.m105w(CpuMonitorService.TAG, "Missing max CPU frequency information at %s", file.getAbsolutePath());
            } else {
                File file2 = new File(file, "related_cpus");
                IntArray readCpuCores = readCpuCores(file2);
                if (readCpuCores == null || readCpuCores.size() == 0) {
                    Slogf.m92e(CpuMonitorService.TAG, "Failed to read related CPU cores from %s", file2.getAbsolutePath());
                } else {
                    StaticPolicyInfo staticPolicyInfo = new StaticPolicyInfo(readCpuFreqKHz, readCpuCores);
                    this.mStaticPolicyInfoById.append(keyAt, staticPolicyInfo);
                    if (CpuMonitorService.DEBUG) {
                        Slogf.m88d(CpuMonitorService.TAG, "Added static policy info %s for policy id %d", staticPolicyInfo, Integer.valueOf(keyAt));
                    }
                }
            }
        }
    }

    public final SparseArray readDynamicPolicyInfo() {
        SparseArray sparseArray = new SparseArray();
        for (int i = 0; i < this.mCpuFreqPolicyDirsById.size(); i++) {
            int keyAt = this.mCpuFreqPolicyDirsById.keyAt(i);
            File file = (File) this.mCpuFreqPolicyDirsById.valueAt(i);
            long readCpuFreqKHz = readCpuFreqKHz(new File(file, "scaling_cur_freq"));
            if (readCpuFreqKHz == 0) {
                Slogf.m105w(CpuMonitorService.TAG, "Missing current frequency information at %s", file.getAbsolutePath());
            } else {
                long readAvgTimeInStateCpuFrequency = readAvgTimeInStateCpuFrequency(keyAt, file);
                File file2 = new File(file, "affected_cpus");
                IntArray readCpuCores = readCpuCores(file2);
                if (readCpuCores == null || readCpuCores.size() == 0) {
                    Slogf.m92e(CpuMonitorService.TAG, "Failed to read CPU cores from %s", file2.getAbsolutePath());
                } else {
                    DynamicPolicyInfo dynamicPolicyInfo = new DynamicPolicyInfo(readCpuFreqKHz, readAvgTimeInStateCpuFrequency, readCpuCores);
                    sparseArray.append(keyAt, dynamicPolicyInfo);
                    if (CpuMonitorService.DEBUG) {
                        Slogf.m88d(CpuMonitorService.TAG, "Read dynamic policy info %s for policy id %d", dynamicPolicyInfo, Integer.valueOf(keyAt));
                    }
                }
            }
        }
        return sparseArray;
    }

    public final long readAvgTimeInStateCpuFrequency(int i, File file) {
        LongSparseLongArray readTimeInState = readTimeInState(file);
        if (readTimeInState == null || readTimeInState.size() == 0) {
            return 0L;
        }
        LongSparseLongArray longSparseLongArray = (LongSparseLongArray) this.mTimeInStateByPolicyId.get(i);
        if (longSparseLongArray == null) {
            this.mTimeInStateByPolicyId.put(i, readTimeInState);
            if (CpuMonitorService.DEBUG) {
                Slogf.m88d(CpuMonitorService.TAG, "Added aggregated time in state info for policy id %d", Integer.valueOf(i));
            }
            return calculateAvgCpuFreq(readTimeInState);
        }
        LongSparseLongArray calculateDeltaTimeInState = calculateDeltaTimeInState(longSparseLongArray, readTimeInState);
        this.mTimeInStateByPolicyId.put(i, readTimeInState);
        if (CpuMonitorService.DEBUG) {
            Slogf.m88d(CpuMonitorService.TAG, "Added latest delta time in state info for policy id %d", Integer.valueOf(i));
        }
        return calculateAvgCpuFreq(calculateDeltaTimeInState);
    }

    public final LongSparseLongArray readTimeInState(File file) {
        if (!this.mHasTimeInStateFile) {
            return null;
        }
        File file2 = new File(file, "stats/time_in_state");
        try {
            List<String> readAllLines = Files.readAllLines(file2.toPath());
            if (readAllLines.isEmpty()) {
                Slogf.m105w(CpuMonitorService.TAG, "Empty time in state file at %s", file2.getAbsolutePath());
                return null;
            }
            LongSparseLongArray longSparseLongArray = new LongSparseLongArray();
            for (int i = 0; i < readAllLines.size(); i++) {
                Matcher matcher = TIME_IN_STATE_PATTERN.matcher(readAllLines.get(i).trim());
                if (matcher.find()) {
                    longSparseLongArray.put(Long.parseLong(matcher.group("freqKHz")), clockTickStrToMillis(matcher.group("time")));
                }
            }
            return longSparseLongArray;
        } catch (Exception e) {
            Slogf.m93e(CpuMonitorService.TAG, e, "Failed to read CPU time in state from file: %s", file2.getAbsolutePath());
            return null;
        }
    }

    public static long readCpuFreqKHz(File file) {
        if (!file.exists()) {
            Slogf.m92e(CpuMonitorService.TAG, "CPU frequency file %s doesn't exist", file.getAbsolutePath());
            return 0L;
        }
        try {
            List<String> readAllLines = Files.readAllLines(file.toPath());
            if (!readAllLines.isEmpty()) {
                long parseLong = Long.parseLong(readAllLines.get(0).trim());
                if (parseLong > 0) {
                    return parseLong;
                }
                return 0L;
            }
        } catch (Exception e) {
            Slogf.m93e(CpuMonitorService.TAG, e, "Failed to read integer content from file: %s", file.getAbsolutePath());
        }
        return 0L;
    }

    public static LongSparseLongArray calculateDeltaTimeInState(LongSparseLongArray longSparseLongArray, LongSparseLongArray longSparseLongArray2) {
        int size = longSparseLongArray2.size();
        LongSparseLongArray longSparseLongArray3 = new LongSparseLongArray(size);
        for (int i = 0; i < size; i++) {
            long keyAt = longSparseLongArray2.keyAt(i);
            long valueAt = longSparseLongArray2.valueAt(i);
            long j = longSparseLongArray.get(keyAt);
            if (valueAt > j) {
                valueAt -= j;
            }
            longSparseLongArray3.put(keyAt, valueAt);
        }
        return longSparseLongArray3;
    }

    public static long calculateAvgCpuFreq(LongSparseLongArray longSparseLongArray) {
        double d = 0.0d;
        double d2 = 0.0d;
        for (int i = 0; i < longSparseLongArray.size(); i++) {
            d2 += longSparseLongArray.valueAt(i);
        }
        if (d2 == 0.0d) {
            return 0L;
        }
        for (int i2 = 0; i2 < longSparseLongArray.size(); i2++) {
            d += (longSparseLongArray.keyAt(i2) * longSparseLongArray.valueAt(i2)) / d2;
        }
        return (long) d;
    }

    public static IntArray readCpuCores(File file) {
        if (!file.exists()) {
            Slogf.m92e(CpuMonitorService.TAG, "Failed to read CPU cores as the file '%s' doesn't exist", file.getAbsolutePath());
            return null;
        }
        try {
            List<String> readAllLines = Files.readAllLines(file.toPath());
            IntArray intArray = new IntArray(0);
            for (int i = 0; i < readAllLines.size(); i++) {
                String str = readAllLines.get(i);
                String[] split = str.contains(",") ? str.trim().split(",") : str.trim().split(" ");
                for (int i2 = 0; i2 < split.length; i2++) {
                    String[] split2 = split[i2].split(PackageManagerShellCommandDataLoader.STDIN_PATH);
                    if (split2.length >= 2) {
                        int parseInt = Integer.parseInt(split2[0]);
                        int parseInt2 = Integer.parseInt(split2[1]);
                        if (parseInt <= parseInt2) {
                            while (parseInt <= parseInt2) {
                                intArray.add(parseInt);
                                parseInt++;
                            }
                        }
                    } else if (split2.length == 1) {
                        intArray.add(Integer.parseInt(split2[0]));
                    } else {
                        Slogf.m105w(CpuMonitorService.TAG, "Invalid CPU core range format %s", split[i2]);
                    }
                }
            }
            return intArray;
        } catch (Exception e) {
            Slogf.m93e(CpuMonitorService.TAG, e, "Failed to read CPU cores from %s", file.getAbsolutePath());
            return null;
        }
    }

    public final SparseArray readLatestCpuUsageStats() {
        SparseArray readCumulativeCpuUsageStats = readCumulativeCpuUsageStats();
        if (readCumulativeCpuUsageStats.size() == 0) {
            Slogf.m90e(CpuMonitorService.TAG, "Failed to read cumulative CPU usage stats");
            return null;
        }
        SparseArray sparseArray = new SparseArray();
        for (int i = 0; i < readCumulativeCpuUsageStats.size(); i++) {
            int keyAt = readCumulativeCpuUsageStats.keyAt(i);
            CpuUsageStats cpuUsageStats = (CpuUsageStats) readCumulativeCpuUsageStats.valueAt(i);
            CpuUsageStats cpuUsageStats2 = (CpuUsageStats) this.mCumulativeCpuUsageStats.get(keyAt);
            if (cpuUsageStats2 != null) {
                cpuUsageStats = cpuUsageStats.delta(cpuUsageStats2);
            }
            sparseArray.append(keyAt, cpuUsageStats);
        }
        this.mCumulativeCpuUsageStats = readCumulativeCpuUsageStats;
        return sparseArray;
    }

    public final SparseArray readCumulativeCpuUsageStats() {
        SparseArray sparseArray = new SparseArray();
        try {
            List<String> readAllLines = Files.readAllLines(this.mProcStatFile.toPath());
            for (int i = 0; i < readAllLines.size(); i++) {
                Matcher matcher = PROC_STAT_PATTERN.matcher(readAllLines.get(i).trim());
                if (matcher.find()) {
                    sparseArray.append(Integer.parseInt(matcher.group("core")), new CpuUsageStats(clockTickStrToMillis(matcher.group("userClockTicks")), clockTickStrToMillis(matcher.group("niceClockTicks")), clockTickStrToMillis(matcher.group("sysClockTicks")), clockTickStrToMillis(matcher.group("idleClockTicks")), clockTickStrToMillis(matcher.group("iowaitClockTicks")), clockTickStrToMillis(matcher.group("irqClockTicks")), clockTickStrToMillis(matcher.group("softirqClockTicks")), clockTickStrToMillis(matcher.group("stealClockTicks")), clockTickStrToMillis(matcher.group("guestClockTicks")), clockTickStrToMillis(matcher.group("guestNiceClockTicks"))));
                }
            }
        } catch (Exception e) {
            Slogf.m93e(CpuMonitorService.TAG, e, "Failed to read cpu usage stats from %s", this.mProcStatFile.getAbsolutePath());
        }
        return sparseArray;
    }

    public static long clockTickStrToMillis(String str) {
        return Long.parseLong(str) * MILLIS_PER_CLOCK_TICK;
    }

    public static String toCpusetCategoriesStr(int i) {
        StringBuilder sb = new StringBuilder();
        if ((i & 1) != 0) {
            sb.append("FLAG_CPUSET_CATEGORY_TOP_APP");
        }
        if ((i & 2) != 0) {
            if (sb.length() > 0) {
                sb.append('|');
            }
            sb.append("FLAG_CPUSET_CATEGORY_BACKGROUND");
        }
        return sb.toString();
    }

    public final class CpuInfo {
        public final long avgTimeInStateCpuFreqKHz;
        public final int cpuCore;
        public final int cpusetCategories;
        public final long curCpuFreqKHz;
        public final boolean isOnline;
        public final CpuUsageStats latestCpuUsageStats;
        public long mNormalizedAvailableCpuFreqKHz;
        public final long maxCpuFreqKHz;

        public CpuInfo(int i, int i2, boolean z, long j, long j2, long j3, CpuUsageStats cpuUsageStats) {
            this(i, i2, z, j, j2, j3, 0L, cpuUsageStats);
            this.mNormalizedAvailableCpuFreqKHz = computeNormalizedAvailableCpuFreqKHz();
        }

        public CpuInfo(int i, int i2, boolean z, long j, long j2, long j3, long j4, CpuUsageStats cpuUsageStats) {
            this.cpuCore = i;
            this.cpusetCategories = i2;
            this.isOnline = z;
            this.curCpuFreqKHz = j;
            this.maxCpuFreqKHz = j2;
            this.avgTimeInStateCpuFreqKHz = j3;
            this.latestCpuUsageStats = cpuUsageStats;
            this.mNormalizedAvailableCpuFreqKHz = j4;
        }

        public long getNormalizedAvailableCpuFreqKHz() {
            return this.mNormalizedAvailableCpuFreqKHz;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("CpuInfo{ cpuCore = ");
            sb.append(this.cpuCore);
            sb.append(", cpusetCategories = [");
            sb.append(CpuInfoReader.toCpusetCategoriesStr(this.cpusetCategories));
            sb.append("], isOnline = ");
            sb.append(this.isOnline ? "Yes" : "No");
            sb.append(", curCpuFreqKHz = ");
            long j = this.curCpuFreqKHz;
            sb.append(j == 0 ? "missing" : Long.valueOf(j));
            sb.append(", maxCpuFreqKHz = ");
            long j2 = this.maxCpuFreqKHz;
            sb.append(j2 == 0 ? "missing" : Long.valueOf(j2));
            sb.append(", avgTimeInStateCpuFreqKHz = ");
            long j3 = this.avgTimeInStateCpuFreqKHz;
            sb.append(j3 != 0 ? Long.valueOf(j3) : "missing");
            sb.append(", latestCpuUsageStats = ");
            sb.append(this.latestCpuUsageStats);
            sb.append(", mNormalizedAvailableCpuFreqKHz = ");
            sb.append(this.mNormalizedAvailableCpuFreqKHz);
            sb.append(" }");
            return sb.toString();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CpuInfo)) {
                return false;
            }
            CpuInfo cpuInfo = (CpuInfo) obj;
            return this.cpuCore == cpuInfo.cpuCore && this.cpusetCategories == cpuInfo.cpusetCategories && this.isOnline == cpuInfo.isOnline && this.curCpuFreqKHz == cpuInfo.curCpuFreqKHz && this.maxCpuFreqKHz == cpuInfo.maxCpuFreqKHz && this.avgTimeInStateCpuFreqKHz == cpuInfo.avgTimeInStateCpuFreqKHz && this.latestCpuUsageStats.equals(cpuInfo.latestCpuUsageStats) && this.mNormalizedAvailableCpuFreqKHz == cpuInfo.mNormalizedAvailableCpuFreqKHz;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.cpuCore), Integer.valueOf(this.cpusetCategories), Boolean.valueOf(this.isOnline), Long.valueOf(this.curCpuFreqKHz), Long.valueOf(this.maxCpuFreqKHz), Long.valueOf(this.avgTimeInStateCpuFreqKHz), this.latestCpuUsageStats, Long.valueOf(this.mNormalizedAvailableCpuFreqKHz));
        }

        public final long computeNormalizedAvailableCpuFreqKHz() {
            if (!this.isOnline) {
                return 0L;
            }
            long totalTimeMillis = this.latestCpuUsageStats.getTotalTimeMillis();
            if (totalTimeMillis == 0) {
                Slogf.wtf(CpuMonitorService.TAG, "Total CPU time millis is 0. This shouldn't happen unless stats are polled too frequently");
                return 0L;
            }
            double d = totalTimeMillis;
            double d2 = ((d - this.latestCpuUsageStats.idleTimeMillis) * 100.0d) / d;
            long j = this.avgTimeInStateCpuFreqKHz;
            if (j == 0) {
                j = this.curCpuFreqKHz;
            }
            double d3 = d2 * j;
            long j2 = this.maxCpuFreqKHz;
            return (long) (((100.0d - (d3 / j2)) * j2) / 100.0d);
        }
    }

    public final class CpuUsageStats {
        public final long guestNiceTimeMillis;
        public final long guestTimeMillis;
        public final long idleTimeMillis;
        public final long iowaitTimeMillis;
        public final long irqTimeMillis;
        public final long niceTimeMillis;
        public final long softirqTimeMillis;
        public final long stealTimeMillis;
        public final long systemTimeMillis;
        public final long userTimeMillis;

        public static long diff(long j, long j2) {
            if (j > j2) {
                return j - j2;
            }
            return 0L;
        }

        public CpuUsageStats(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10) {
            this.userTimeMillis = j;
            this.niceTimeMillis = j2;
            this.systemTimeMillis = j3;
            this.idleTimeMillis = j4;
            this.iowaitTimeMillis = j5;
            this.irqTimeMillis = j6;
            this.softirqTimeMillis = j7;
            this.stealTimeMillis = j8;
            this.guestTimeMillis = j9;
            this.guestNiceTimeMillis = j10;
        }

        public long getTotalTimeMillis() {
            return this.userTimeMillis + this.niceTimeMillis + this.systemTimeMillis + this.idleTimeMillis + this.iowaitTimeMillis + this.irqTimeMillis + this.softirqTimeMillis + this.stealTimeMillis + this.guestTimeMillis + this.guestNiceTimeMillis;
        }

        public String toString() {
            return "CpuUsageStats{ userTimeMillis = " + this.userTimeMillis + ", niceTimeMillis = " + this.niceTimeMillis + ", systemTimeMillis = " + this.systemTimeMillis + ", idleTimeMillis = " + this.idleTimeMillis + ", iowaitTimeMillis = " + this.iowaitTimeMillis + ", irqTimeMillis = " + this.irqTimeMillis + ", softirqTimeMillis = " + this.softirqTimeMillis + ", stealTimeMillis = " + this.stealTimeMillis + ", guestTimeMillis = " + this.guestTimeMillis + ", guestNiceTimeMillis = " + this.guestNiceTimeMillis + " }";
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CpuUsageStats)) {
                return false;
            }
            CpuUsageStats cpuUsageStats = (CpuUsageStats) obj;
            return this.userTimeMillis == cpuUsageStats.userTimeMillis && this.niceTimeMillis == cpuUsageStats.niceTimeMillis && this.systemTimeMillis == cpuUsageStats.systemTimeMillis && this.idleTimeMillis == cpuUsageStats.idleTimeMillis && this.iowaitTimeMillis == cpuUsageStats.iowaitTimeMillis && this.irqTimeMillis == cpuUsageStats.irqTimeMillis && this.softirqTimeMillis == cpuUsageStats.softirqTimeMillis && this.stealTimeMillis == cpuUsageStats.stealTimeMillis && this.guestTimeMillis == cpuUsageStats.guestTimeMillis && this.guestNiceTimeMillis == cpuUsageStats.guestNiceTimeMillis;
        }

        public int hashCode() {
            return Objects.hash(Long.valueOf(this.userTimeMillis), Long.valueOf(this.niceTimeMillis), Long.valueOf(this.systemTimeMillis), Long.valueOf(this.idleTimeMillis), Long.valueOf(this.iowaitTimeMillis), Long.valueOf(this.irqTimeMillis), Long.valueOf(this.softirqTimeMillis), Long.valueOf(this.stealTimeMillis), Long.valueOf(this.guestTimeMillis), Long.valueOf(this.guestNiceTimeMillis));
        }

        public CpuUsageStats delta(CpuUsageStats cpuUsageStats) {
            return new CpuUsageStats(diff(this.userTimeMillis, cpuUsageStats.userTimeMillis), diff(this.niceTimeMillis, cpuUsageStats.niceTimeMillis), diff(this.systemTimeMillis, cpuUsageStats.systemTimeMillis), diff(this.idleTimeMillis, cpuUsageStats.idleTimeMillis), diff(this.iowaitTimeMillis, cpuUsageStats.iowaitTimeMillis), diff(this.irqTimeMillis, cpuUsageStats.irqTimeMillis), diff(this.softirqTimeMillis, cpuUsageStats.softirqTimeMillis), diff(this.stealTimeMillis, cpuUsageStats.stealTimeMillis), diff(this.guestTimeMillis, cpuUsageStats.guestTimeMillis), diff(this.guestNiceTimeMillis, cpuUsageStats.guestNiceTimeMillis));
        }
    }

    public final class StaticPolicyInfo {
        public final long maxCpuFreqKHz;
        public final IntArray relatedCpuCores;

        public StaticPolicyInfo(long j, IntArray intArray) {
            this.maxCpuFreqKHz = j;
            this.relatedCpuCores = intArray;
        }

        public String toString() {
            return "StaticPolicyInfo{maxCpuFreqKHz = " + this.maxCpuFreqKHz + ", relatedCpuCores = " + this.relatedCpuCores + '}';
        }
    }

    public final class DynamicPolicyInfo {
        public final IntArray affectedCpuCores;
        public final long avgTimeInStateCpuFreqKHz;
        public final long curCpuFreqKHz;

        public DynamicPolicyInfo(long j, long j2, IntArray intArray) {
            this.curCpuFreqKHz = j;
            this.avgTimeInStateCpuFreqKHz = j2;
            this.affectedCpuCores = intArray;
        }

        public String toString() {
            return "DynamicPolicyInfo{curCpuFreqKHz = " + this.curCpuFreqKHz + ", avgTimeInStateCpuFreqKHz = " + this.avgTimeInStateCpuFreqKHz + ", affectedCpuCores = " + this.affectedCpuCores + '}';
        }
    }
}
