package com.android.internal.os;

import android.util.Slog;
import com.android.modules.expresslog.Counter;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes5.dex */
public class KernelSingleProcessCpuThreadReader {
    private static final boolean DEBUG = false;
    private static final String TAG = "KernelSingleProcCpuThreadRdr";
    private final CpuTimeInStateReader mCpuTimeInStateReader;
    private int mFrequencyCount;
    private boolean mIsTracking;
    private final int mPid;
    private int[] mSelectedThreadNativeTids = new int[0];

    public interface CpuTimeInStateReader {
        String[] getAggregatedTaskCpuFreqTimes(int i);

        int getCpuFrequencyCount();

        boolean startAggregatingTaskCpuTimes(int i, int i2);

        boolean startTrackingProcessCpuTimes(int i);
    }

    private native int getCpuFrequencyCount(CpuTimeInStateReader cpuTimeInStateReader);

    private native boolean readProcessCpuUsage(int i, long[] jArr, long[] jArr2, CpuTimeInStateReader cpuTimeInStateReader);

    private native boolean startAggregatingThreadCpuTimes(int[] iArr, CpuTimeInStateReader cpuTimeInStateReader);

    private native boolean startTrackingProcessCpuTimes(int i, CpuTimeInStateReader cpuTimeInStateReader);

    public KernelSingleProcessCpuThreadReader(int i, CpuTimeInStateReader cpuTimeInStateReader) throws IOException {
        this.mPid = i;
        this.mCpuTimeInStateReader = cpuTimeInStateReader;
    }

    public static KernelSingleProcessCpuThreadReader create(int i) {
        try {
            return new KernelSingleProcessCpuThreadReader(i, null);
        } catch (IOException e) {
            Slog.e(TAG, "Failed to initialize KernelSingleProcessCpuThreadReader", e);
            return null;
        }
    }

    public void startTrackingThreadCpuTimes() {
        if (this.mIsTracking) {
            return;
        }
        if (!startTrackingProcessCpuTimes(this.mPid, this.mCpuTimeInStateReader)) {
            Slog.wtf(TAG, "Failed to start tracking process CPU times for " + this.mPid);
            Counter.logIncrement("cpu.value_process_tracking_start_failure_count");
        }
        int[] iArr = this.mSelectedThreadNativeTids;
        if (iArr.length > 0 && !startAggregatingThreadCpuTimes(iArr, this.mCpuTimeInStateReader)) {
            Slog.wtf(TAG, "Failed to start tracking aggregated thread CPU times for " + Arrays.toString(this.mSelectedThreadNativeTids));
            Counter.logIncrement("cpu.value_aggregated_thread_tracking_start_failure_count");
        }
        this.mIsTracking = true;
    }

    public void setSelectedThreadIds(int[] iArr) {
        int[] iArr2 = (int[]) iArr.clone();
        this.mSelectedThreadNativeTids = iArr2;
        if (this.mIsTracking) {
            startAggregatingThreadCpuTimes(iArr2, this.mCpuTimeInStateReader);
        }
    }

    public int getCpuFrequencyCount() {
        if (this.mFrequencyCount == 0) {
            this.mFrequencyCount = getCpuFrequencyCount(this.mCpuTimeInStateReader);
        }
        return this.mFrequencyCount;
    }

    public ProcessCpuUsage getProcessCpuUsage() {
        ProcessCpuUsage processCpuUsage = new ProcessCpuUsage(getCpuFrequencyCount());
        if (readProcessCpuUsage(this.mPid, processCpuUsage.threadCpuTimesMillis, processCpuUsage.selectedThreadCpuTimesMillis, this.mCpuTimeInStateReader)) {
            return processCpuUsage;
        }
        return null;
    }

    public static class ProcessCpuUsage {
        public long[] selectedThreadCpuTimesMillis;
        public long[] threadCpuTimesMillis;

        public ProcessCpuUsage(int i) {
            this.threadCpuTimesMillis = new long[i];
            this.selectedThreadCpuTimesMillis = new long[i];
        }
    }
}
