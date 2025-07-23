package com.android.internal.os;

import android.os.Process;
import android.util.IntArray;
import android.util.Slog;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class KernelCpuThreadReader {
    private static final String CPU_STATISTICS_FILENAME = "time_in_state";
    private static final boolean DEBUG = false;
    private static final Path DEFAULT_INITIAL_TIME_IN_STATE_PATH;
    private static final String DEFAULT_PROCESS_NAME = "unknown_process";
    private static final Path DEFAULT_PROC_PATH;
    private static final String DEFAULT_THREAD_NAME = "unknown_thread";
    private static final int ID_ERROR = -1;
    private static final String PROCESS_DIRECTORY_FILTER = "[0-9]*";
    private static final String PROCESS_NAME_FILENAME = "cmdline";
    private static final String TAG = "KernelCpuThreadReader";
    private static final String THREAD_NAME_FILENAME = "comm";
    private int[] mFrequenciesKhz;
    private FrequencyBucketCreator mFrequencyBucketCreator;
    private final Injector mInjector;
    private final Path mProcPath;
    private final ProcTimeInStateReader mProcTimeInStateReader;
    private Predicate<Integer> mUidPredicate;

    static {
        Path path = Paths.get("/proc", new String[0]);
        DEFAULT_PROC_PATH = path;
        DEFAULT_INITIAL_TIME_IN_STATE_PATH = path.resolve("self/time_in_state");
    }

    public KernelCpuThreadReader(int i, Predicate<Integer> predicate, Path path, Path path2, Injector injector) throws IOException {
        this.mUidPredicate = predicate;
        this.mProcPath = path;
        this.mProcTimeInStateReader = new ProcTimeInStateReader(path2);
        this.mInjector = injector;
        setNumBuckets(i);
    }

    public static KernelCpuThreadReader create(int i, Predicate<Integer> predicate) {
        try {
            return new KernelCpuThreadReader(i, predicate, DEFAULT_PROC_PATH, DEFAULT_INITIAL_TIME_IN_STATE_PATH, new Injector());
        } catch (IOException e) {
            Slog.e(TAG, "Failed to initialize KernelCpuThreadReader", e);
            return null;
        }
    }

    public ArrayList<ProcessCpuUsage> getProcessCpuUsage() {
        ProcessCpuUsage processCpuUsage;
        ArrayList<ProcessCpuUsage> arrayList = new ArrayList<>();
        try {
            DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(this.mProcPath, PROCESS_DIRECTORY_FILTER);
            try {
                for (Path path : newDirectoryStream) {
                    int processId = getProcessId(path);
                    int uidForPid = this.mInjector.getUidForPid(processId);
                    if (uidForPid != -1 && processId != -1 && this.mUidPredicate.test(Integer.valueOf(uidForPid)) && (processCpuUsage = getProcessCpuUsage(path, processId, uidForPid)) != null) {
                        arrayList.add(processCpuUsage);
                    }
                }
                if (newDirectoryStream != null) {
                    newDirectoryStream.close();
                }
                if (!arrayList.isEmpty()) {
                    return arrayList;
                }
                Slog.w(TAG, "Didn't successfully get any process CPU information for UIDs specified");
                return null;
            } finally {
            }
        } catch (IOException e) {
            Slog.w(TAG, "Failed to iterate over process paths", e);
            return null;
        }
    }

    public int[] getCpuFrequenciesKhz() {
        return this.mFrequenciesKhz;
    }

    void setNumBuckets(int i) {
        int[] iArr = this.mFrequenciesKhz;
        if (iArr == null || iArr.length != i) {
            long[] frequenciesKhz = this.mProcTimeInStateReader.getFrequenciesKhz();
            if (i != 0) {
                FrequencyBucketCreator frequencyBucketCreator = new FrequencyBucketCreator(frequenciesKhz, i);
                this.mFrequencyBucketCreator = frequencyBucketCreator;
                this.mFrequenciesKhz = frequencyBucketCreator.bucketFrequencies(frequenciesKhz);
            } else {
                this.mFrequencyBucketCreator = null;
                this.mFrequenciesKhz = new int[frequenciesKhz.length];
                for (int i2 = 0; i2 < frequenciesKhz.length; i2++) {
                    this.mFrequenciesKhz[i2] = (int) frequenciesKhz[i2];
                }
            }
        }
    }

    public void setUidPredicate(Predicate<Integer> predicate) {
        this.mUidPredicate = predicate;
    }

    private ProcessCpuUsage getProcessCpuUsage(Path path, int i, int i2) {
        Path resolve = path.resolve("task");
        ArrayList arrayList = new ArrayList();
        try {
            DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(resolve);
            try {
                Iterator<Path> it = newDirectoryStream.iterator();
                while (it.hasNext()) {
                    ThreadCpuUsage threadCpuUsage = getThreadCpuUsage(it.next());
                    if (threadCpuUsage != null) {
                        arrayList.add(threadCpuUsage);
                    }
                }
                if (newDirectoryStream != null) {
                    newDirectoryStream.close();
                }
                if (arrayList.isEmpty()) {
                    return null;
                }
                return new ProcessCpuUsage(i, getProcessName(path), i2, arrayList);
            } catch (Throwable th) {
                if (newDirectoryStream != null) {
                    try {
                        newDirectoryStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IOException | DirectoryIteratorException unused) {
            return null;
        }
    }

    private ThreadCpuUsage getThreadCpuUsage(Path path) {
        int[] iArr;
        try {
            int parseInt = Integer.parseInt(path.getFileName().toString());
            String threadName = getThreadName(path);
            long[] usageTimesMillis = this.mProcTimeInStateReader.getUsageTimesMillis(path.resolve(CPU_STATISTICS_FILENAME));
            if (usageTimesMillis == null) {
                return null;
            }
            FrequencyBucketCreator frequencyBucketCreator = this.mFrequencyBucketCreator;
            if (frequencyBucketCreator != null) {
                iArr = frequencyBucketCreator.bucketValues(usageTimesMillis);
            } else {
                iArr = new int[usageTimesMillis.length];
                for (int i = 0; i < usageTimesMillis.length; i++) {
                    iArr[i] = (int) usageTimesMillis[i];
                }
            }
            return new ThreadCpuUsage(parseInt, threadName, iArr);
        } catch (NumberFormatException e) {
            Slog.w(TAG, "Failed to parse thread ID when iterating over /proc/*/task", e);
            return null;
        }
    }

    private String getProcessName(Path path) {
        String readSingleLineProcFile = ProcStatsUtil.readSingleLineProcFile(path.resolve(PROCESS_NAME_FILENAME).toString());
        return readSingleLineProcFile != null ? readSingleLineProcFile : DEFAULT_PROCESS_NAME;
    }

    private String getThreadName(Path path) {
        String readNullSeparatedFile = ProcStatsUtil.readNullSeparatedFile(path.resolve(THREAD_NAME_FILENAME).toString());
        return readNullSeparatedFile == null ? DEFAULT_THREAD_NAME : readNullSeparatedFile;
    }

    private int getProcessId(Path path) {
        String path2 = path.getFileName().toString();
        try {
            return Integer.parseInt(path2);
        } catch (NumberFormatException e) {
            Slog.w(TAG, "Failed to parse " + path2 + " as process ID", e);
            return -1;
        }
    }

    public static class FrequencyBucketCreator {
        private final int[] mBucketStartIndices;
        private final int mNumBuckets;
        private final int mNumFrequencies;

        public FrequencyBucketCreator(long[] jArr, int i) {
            int length = jArr.length;
            this.mNumFrequencies = length;
            int[] bucketStartIndices = getBucketStartIndices(getClusterStartIndices(jArr), i, length);
            this.mBucketStartIndices = bucketStartIndices;
            this.mNumBuckets = bucketStartIndices.length;
        }

        public int[] bucketValues(long[] jArr) {
            Preconditions.checkArgument(jArr.length == this.mNumFrequencies);
            int[] iArr = new int[this.mNumBuckets];
            for (int i = 0; i < this.mNumBuckets; i++) {
                int upperBound = getUpperBound(i, this.mBucketStartIndices, jArr.length);
                for (int lowerBound = getLowerBound(i, this.mBucketStartIndices); lowerBound < upperBound; lowerBound++) {
                    iArr[i] = (int) (iArr[i] + jArr[lowerBound]);
                }
            }
            return iArr;
        }

        public int[] bucketFrequencies(long[] jArr) {
            Preconditions.checkArgument(jArr.length == this.mNumFrequencies);
            int i = this.mNumBuckets;
            int[] iArr = new int[i];
            for (int i2 = 0; i2 < i; i2++) {
                iArr[i2] = (int) jArr[this.mBucketStartIndices[i2]];
            }
            return iArr;
        }

        private static int[] getClusterStartIndices(long[] jArr) {
            IntArray intArray = new IntArray();
            int i = 0;
            intArray.add(0);
            while (i < jArr.length - 1) {
                long j = jArr[i];
                i++;
                if (j >= jArr[i]) {
                    intArray.add(i);
                }
            }
            return intArray.toArray();
        }

        private static int[] getBucketStartIndices(int[] iArr, int i, int i2) {
            int i3;
            int length = iArr.length;
            if (length > i) {
                return Arrays.copyOfRange(iArr, 0, i);
            }
            IntArray intArray = new IntArray();
            for (int i4 = 0; i4 < length; i4++) {
                int lowerBound = getLowerBound(i4, iArr);
                int upperBound = getUpperBound(i4, iArr, i2);
                int i5 = length - 1;
                if (i4 != i5) {
                    i3 = i / length;
                } else {
                    i3 = i - ((i / length) * i5);
                }
                int max = Math.max(1, (upperBound - lowerBound) / i3);
                for (int i6 = 0; i6 < i3; i6++) {
                    int i7 = (i6 * max) + lowerBound;
                    if (i7 >= upperBound) {
                        break;
                    }
                    intArray.add(i7);
                }
            }
            return intArray.toArray();
        }

        private static int getLowerBound(int i, int[] iArr) {
            return iArr[i];
        }

        private static int getUpperBound(int i, int[] iArr, int i2) {
            return i != iArr.length + (-1) ? iArr[i + 1] : i2;
        }
    }

    public static class ProcessCpuUsage {
        public final int processId;
        public final String processName;
        public ArrayList<ThreadCpuUsage> threadCpuUsages;
        public final int uid;

        public ProcessCpuUsage(int i, String str, int i2, ArrayList<ThreadCpuUsage> arrayList) {
            this.processId = i;
            this.processName = str;
            this.uid = i2;
            this.threadCpuUsages = arrayList;
        }
    }

    public static class ThreadCpuUsage {
        public final int threadId;
        public final String threadName;
        public int[] usageTimesMillis;

        public ThreadCpuUsage(int i, String str, int[] iArr) {
            this.threadId = i;
            this.threadName = str;
            this.usageTimesMillis = iArr;
        }
    }

    public static class Injector {
        public int getUidForPid(int i) {
            return Process.getUidForPid(i);
        }
    }
}
