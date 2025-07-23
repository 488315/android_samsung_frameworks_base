package android.os;

import android.os.Debug;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class PerformanceCollector {
    public static final String METRIC_KEY_CPU_TIME = "cpu_time";
    public static final String METRIC_KEY_EXECUTION_TIME = "execution_time";
    public static final String METRIC_KEY_GC_INVOCATION_COUNT = "gc_invocation_count";
    public static final String METRIC_KEY_GLOBAL_ALLOC_COUNT = "global_alloc_count";
    public static final String METRIC_KEY_GLOBAL_ALLOC_SIZE = "global_alloc_size";
    public static final String METRIC_KEY_GLOBAL_FREED_COUNT = "global_freed_count";
    public static final String METRIC_KEY_GLOBAL_FREED_SIZE = "global_freed_size";
    public static final String METRIC_KEY_ITERATIONS = "iterations";
    public static final String METRIC_KEY_JAVA_ALLOCATED = "java_allocated";
    public static final String METRIC_KEY_JAVA_FREE = "java_free";
    public static final String METRIC_KEY_JAVA_PRIVATE_DIRTY = "java_private_dirty";
    public static final String METRIC_KEY_JAVA_PSS = "java_pss";
    public static final String METRIC_KEY_JAVA_SHARED_DIRTY = "java_shared_dirty";
    public static final String METRIC_KEY_JAVA_SIZE = "java_size";
    public static final String METRIC_KEY_LABEL = "label";
    public static final String METRIC_KEY_NATIVE_ALLOCATED = "native_allocated";
    public static final String METRIC_KEY_NATIVE_FREE = "native_free";
    public static final String METRIC_KEY_NATIVE_PRIVATE_DIRTY = "native_private_dirty";
    public static final String METRIC_KEY_NATIVE_PSS = "native_pss";
    public static final String METRIC_KEY_NATIVE_SHARED_DIRTY = "native_shared_dirty";
    public static final String METRIC_KEY_NATIVE_SIZE = "native_size";
    public static final String METRIC_KEY_OTHER_PRIVATE_DIRTY = "other_private_dirty";
    public static final String METRIC_KEY_OTHER_PSS = "other_pss";
    public static final String METRIC_KEY_OTHER_SHARED_DIRTY = "other_shared_dirty";
    public static final String METRIC_KEY_PRE_RECEIVED_TRANSACTIONS = "pre_received_transactions";
    public static final String METRIC_KEY_PRE_SENT_TRANSACTIONS = "pre_sent_transactions";
    public static final String METRIC_KEY_RECEIVED_TRANSACTIONS = "received_transactions";
    public static final String METRIC_KEY_SENT_TRANSACTIONS = "sent_transactions";
    private long mCpuTime;
    private long mExecTime;
    private Bundle mPerfMeasurement;
    private Bundle mPerfSnapshot;
    private PerformanceResultsWriter mPerfWriter;
    private long mSnapshotCpuTime;
    private long mSnapshotExecTime;

    public interface PerformanceResultsWriter {
        void writeBeginSnapshot(String str);

        void writeEndSnapshot(Bundle bundle);

        void writeMeasurement(String str, float f);

        void writeMeasurement(String str, long j);

        void writeMeasurement(String str, String str2);

        void writeStartTiming(String str);

        void writeStopTiming(Bundle bundle);
    }

    public PerformanceCollector() {
    }

    public PerformanceCollector(PerformanceResultsWriter performanceResultsWriter) {
        setPerformanceResultsWriter(performanceResultsWriter);
    }

    public void setPerformanceResultsWriter(PerformanceResultsWriter performanceResultsWriter) {
        this.mPerfWriter = performanceResultsWriter;
    }

    public void beginSnapshot(String str) {
        PerformanceResultsWriter performanceResultsWriter = this.mPerfWriter;
        if (performanceResultsWriter != null) {
            performanceResultsWriter.writeBeginSnapshot(str);
        }
        startPerformanceSnapshot();
    }

    public Bundle endSnapshot() {
        endPerformanceSnapshot();
        PerformanceResultsWriter performanceResultsWriter = this.mPerfWriter;
        if (performanceResultsWriter != null) {
            performanceResultsWriter.writeEndSnapshot(this.mPerfSnapshot);
        }
        return this.mPerfSnapshot;
    }

    public void startTiming(String str) {
        PerformanceResultsWriter performanceResultsWriter = this.mPerfWriter;
        if (performanceResultsWriter != null) {
            performanceResultsWriter.writeStartTiming(str);
        }
        Bundle bundle = new Bundle();
        this.mPerfMeasurement = bundle;
        bundle.putParcelableArrayList(METRIC_KEY_ITERATIONS, new ArrayList<>());
        this.mExecTime = SystemClock.uptimeMillis();
        this.mCpuTime = Process.getElapsedCpuTime();
    }

    public Bundle addIteration(String str) {
        this.mCpuTime = Process.getElapsedCpuTime() - this.mCpuTime;
        this.mExecTime = SystemClock.uptimeMillis() - this.mExecTime;
        Bundle bundle = new Bundle();
        bundle.putString("label", str);
        bundle.putLong(METRIC_KEY_EXECUTION_TIME, this.mExecTime);
        bundle.putLong(METRIC_KEY_CPU_TIME, this.mCpuTime);
        this.mPerfMeasurement.getParcelableArrayList(METRIC_KEY_ITERATIONS).add(bundle);
        this.mExecTime = SystemClock.uptimeMillis();
        this.mCpuTime = Process.getElapsedCpuTime();
        return bundle;
    }

    public Bundle stopTiming(String str) {
        addIteration(str);
        PerformanceResultsWriter performanceResultsWriter = this.mPerfWriter;
        if (performanceResultsWriter != null) {
            performanceResultsWriter.writeStopTiming(this.mPerfMeasurement);
        }
        return this.mPerfMeasurement;
    }

    public void addMeasurement(String str, long j) {
        PerformanceResultsWriter performanceResultsWriter = this.mPerfWriter;
        if (performanceResultsWriter != null) {
            performanceResultsWriter.writeMeasurement(str, j);
        }
    }

    public void addMeasurement(String str, float f) {
        PerformanceResultsWriter performanceResultsWriter = this.mPerfWriter;
        if (performanceResultsWriter != null) {
            performanceResultsWriter.writeMeasurement(str, f);
        }
    }

    public void addMeasurement(String str, String str2) {
        PerformanceResultsWriter performanceResultsWriter = this.mPerfWriter;
        if (performanceResultsWriter != null) {
            performanceResultsWriter.writeMeasurement(str, str2);
        }
    }

    private void startPerformanceSnapshot() {
        this.mPerfSnapshot = new Bundle();
        Bundle binderCounts = getBinderCounts();
        for (String str : binderCounts.keySet()) {
            this.mPerfSnapshot.putLong("pre_" + str, binderCounts.getLong(str));
        }
        startAllocCounting();
        this.mSnapshotExecTime = SystemClock.uptimeMillis();
        this.mSnapshotCpuTime = Process.getElapsedCpuTime();
    }

    private void endPerformanceSnapshot() {
        this.mSnapshotCpuTime = Process.getElapsedCpuTime() - this.mSnapshotCpuTime;
        this.mSnapshotExecTime = SystemClock.uptimeMillis() - this.mSnapshotExecTime;
        stopAllocCounting();
        long nativeHeapSize = Debug.getNativeHeapSize() / 1024;
        long nativeHeapAllocatedSize = Debug.getNativeHeapAllocatedSize() / 1024;
        long nativeHeapFreeSize = Debug.getNativeHeapFreeSize() / 1024;
        Debug.getMemoryInfo(new Debug.MemoryInfo());
        Runtime runtime = Runtime.getRuntime();
        long j = runtime.totalMemory() / 1024;
        long freeMemory = runtime.freeMemory() / 1024;
        long j2 = j - freeMemory;
        Bundle binderCounts = getBinderCounts();
        Iterator<String> it = binderCounts.keySet().iterator();
        while (it.hasNext()) {
            Iterator<String> it2 = it;
            String next = it.next();
            this.mPerfSnapshot.putLong(next, binderCounts.getLong(next));
            it = it2;
            freeMemory = freeMemory;
            j2 = j2;
        }
        long j3 = j2;
        long j4 = freeMemory;
        Bundle allocCounts = getAllocCounts();
        for (String str : allocCounts.keySet()) {
            this.mPerfSnapshot.putLong(str, allocCounts.getLong(str));
        }
        this.mPerfSnapshot.putLong(METRIC_KEY_EXECUTION_TIME, this.mSnapshotExecTime);
        this.mPerfSnapshot.putLong(METRIC_KEY_CPU_TIME, this.mSnapshotCpuTime);
        this.mPerfSnapshot.putLong(METRIC_KEY_NATIVE_SIZE, nativeHeapSize);
        this.mPerfSnapshot.putLong(METRIC_KEY_NATIVE_ALLOCATED, nativeHeapAllocatedSize);
        this.mPerfSnapshot.putLong(METRIC_KEY_NATIVE_FREE, nativeHeapFreeSize);
        this.mPerfSnapshot.putLong(METRIC_KEY_NATIVE_PSS, r9.nativePss);
        this.mPerfSnapshot.putLong(METRIC_KEY_NATIVE_PRIVATE_DIRTY, r9.nativePrivateDirty);
        this.mPerfSnapshot.putLong(METRIC_KEY_NATIVE_SHARED_DIRTY, r9.nativeSharedDirty);
        this.mPerfSnapshot.putLong(METRIC_KEY_JAVA_SIZE, j);
        this.mPerfSnapshot.putLong(METRIC_KEY_JAVA_ALLOCATED, j3);
        this.mPerfSnapshot.putLong(METRIC_KEY_JAVA_FREE, j4);
        this.mPerfSnapshot.putLong(METRIC_KEY_JAVA_PSS, r9.dalvikPss);
        this.mPerfSnapshot.putLong(METRIC_KEY_JAVA_PRIVATE_DIRTY, r9.dalvikPrivateDirty);
        this.mPerfSnapshot.putLong(METRIC_KEY_JAVA_SHARED_DIRTY, r9.dalvikSharedDirty);
        this.mPerfSnapshot.putLong(METRIC_KEY_OTHER_PSS, r9.otherPss);
        this.mPerfSnapshot.putLong(METRIC_KEY_OTHER_PRIVATE_DIRTY, r9.otherPrivateDirty);
        this.mPerfSnapshot.putLong(METRIC_KEY_OTHER_SHARED_DIRTY, r9.otherSharedDirty);
    }

    private static void startAllocCounting() {
        Runtime.getRuntime().gc();
        Runtime.getRuntime().runFinalization();
        Runtime.getRuntime().gc();
        Debug.resetAllCounts();
        Debug.startAllocCounting();
    }

    private static void stopAllocCounting() {
        Runtime.getRuntime().gc();
        Runtime.getRuntime().runFinalization();
        Runtime.getRuntime().gc();
        Debug.stopAllocCounting();
    }

    private static Bundle getAllocCounts() {
        Bundle bundle = new Bundle();
        bundle.putLong(METRIC_KEY_GLOBAL_ALLOC_COUNT, Debug.getGlobalAllocCount());
        bundle.putLong(METRIC_KEY_GLOBAL_ALLOC_SIZE, Debug.getGlobalAllocSize());
        bundle.putLong(METRIC_KEY_GLOBAL_FREED_COUNT, Debug.getGlobalFreedCount());
        bundle.putLong(METRIC_KEY_GLOBAL_FREED_SIZE, Debug.getGlobalFreedSize());
        bundle.putLong(METRIC_KEY_GC_INVOCATION_COUNT, Debug.getGlobalGcInvocationCount());
        return bundle;
    }

    private static Bundle getBinderCounts() {
        Bundle bundle = new Bundle();
        bundle.putLong(METRIC_KEY_SENT_TRANSACTIONS, Debug.getBinderSentTransactions());
        bundle.putLong(METRIC_KEY_RECEIVED_TRANSACTIONS, Debug.getBinderReceivedTransactions());
        return bundle;
    }
}
