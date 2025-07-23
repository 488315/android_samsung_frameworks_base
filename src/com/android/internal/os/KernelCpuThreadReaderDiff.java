package com.android.internal.os;

import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.os.KernelCpuThreadReader;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes5.dex */
public class KernelCpuThreadReaderDiff {
    private static final int OTHER_THREADS_ID = -1;
    private static final String OTHER_THREADS_NAME = "__OTHER_THREADS";
    private static final String TAG = "KernelCpuThreadReaderDiff";
    private int mMinimumTotalCpuUsageMillis;
    private Map<ThreadKey, int[]> mPreviousCpuUsage = null;
    private final KernelCpuThreadReader mReader;

    public KernelCpuThreadReaderDiff(KernelCpuThreadReader kernelCpuThreadReader, int i) {
        this.mReader = (KernelCpuThreadReader) Preconditions.checkNotNull(kernelCpuThreadReader);
        this.mMinimumTotalCpuUsageMillis = i;
    }

    public ArrayList<KernelCpuThreadReader.ProcessCpuUsage> getProcessCpuUsageDiffed() {
        Map<ThreadKey, int[]> map;
        Throwable th;
        ArrayList<KernelCpuThreadReader.ProcessCpuUsage> processCpuUsage;
        try {
            processCpuUsage = this.mReader.getProcessCpuUsage();
            map = createCpuUsageMap(processCpuUsage);
        } catch (Throwable th2) {
            map = null;
            th = th2;
        }
        try {
            if (this.mPreviousCpuUsage != null) {
                for (int i = 0; i < processCpuUsage.size(); i++) {
                    KernelCpuThreadReader.ProcessCpuUsage processCpuUsage2 = processCpuUsage.get(i);
                    changeToDiffs(this.mPreviousCpuUsage, processCpuUsage2);
                    applyThresholding(processCpuUsage2);
                }
                this.mPreviousCpuUsage = map;
                return processCpuUsage;
            }
            this.mPreviousCpuUsage = map;
            return null;
        } catch (Throwable th3) {
            th = th3;
            this.mPreviousCpuUsage = map;
            throw th;
        }
    }

    public int[] getCpuFrequenciesKhz() {
        return this.mReader.getCpuFrequenciesKhz();
    }

    void setMinimumTotalCpuUsageMillis(int i) {
        if (i < 0) {
            Slog.w(TAG, "Negative minimumTotalCpuUsageMillis: " + i);
            return;
        }
        this.mMinimumTotalCpuUsageMillis = i;
    }

    private static Map<ThreadKey, int[]> createCpuUsageMap(List<KernelCpuThreadReader.ProcessCpuUsage> list) {
        ArrayMap arrayMap = new ArrayMap();
        for (int i = 0; i < list.size(); i++) {
            KernelCpuThreadReader.ProcessCpuUsage processCpuUsage = list.get(i);
            for (int i2 = 0; i2 < processCpuUsage.threadCpuUsages.size(); i2++) {
                KernelCpuThreadReader.ThreadCpuUsage threadCpuUsage = processCpuUsage.threadCpuUsages.get(i2);
                arrayMap.put(new ThreadKey(processCpuUsage.processId, threadCpuUsage.threadId, processCpuUsage.processName, threadCpuUsage.threadName), threadCpuUsage.usageTimesMillis);
            }
        }
        return arrayMap;
    }

    private static void changeToDiffs(Map<ThreadKey, int[]> map, KernelCpuThreadReader.ProcessCpuUsage processCpuUsage) {
        for (int i = 0; i < processCpuUsage.threadCpuUsages.size(); i++) {
            KernelCpuThreadReader.ThreadCpuUsage threadCpuUsage = processCpuUsage.threadCpuUsages.get(i);
            int[] iArr = map.get(new ThreadKey(processCpuUsage.processId, threadCpuUsage.threadId, processCpuUsage.processName, threadCpuUsage.threadName));
            if (iArr == null) {
                iArr = new int[threadCpuUsage.usageTimesMillis.length];
            }
            threadCpuUsage.usageTimesMillis = cpuTimeDiff(threadCpuUsage.usageTimesMillis, iArr);
        }
    }

    private void applyThresholding(KernelCpuThreadReader.ProcessCpuUsage processCpuUsage) {
        ArrayList<KernelCpuThreadReader.ThreadCpuUsage> arrayList = new ArrayList<>();
        int[] iArr = null;
        for (int i = 0; i < processCpuUsage.threadCpuUsages.size(); i++) {
            KernelCpuThreadReader.ThreadCpuUsage threadCpuUsage = processCpuUsage.threadCpuUsages.get(i);
            if (this.mMinimumTotalCpuUsageMillis > totalCpuUsage(threadCpuUsage.usageTimesMillis)) {
                if (iArr == null) {
                    iArr = new int[threadCpuUsage.usageTimesMillis.length];
                }
                addToCpuUsage(iArr, threadCpuUsage.usageTimesMillis);
            } else {
                arrayList.add(threadCpuUsage);
            }
        }
        if (iArr != null) {
            arrayList.add(new KernelCpuThreadReader.ThreadCpuUsage(-1, OTHER_THREADS_NAME, iArr));
        }
        processCpuUsage.threadCpuUsages = arrayList;
    }

    private static int totalCpuUsage(int[] iArr) {
        int i = 0;
        for (int i2 : iArr) {
            i += i2;
        }
        return i;
    }

    private static void addToCpuUsage(int[] iArr, int[] iArr2) {
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = iArr[i] + iArr2[i];
        }
    }

    private static int[] cpuTimeDiff(int[] iArr, int[] iArr2) {
        int[] iArr3 = new int[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr3[i] = iArr[i] - iArr2[i];
        }
        return iArr3;
    }

    private static class ThreadKey {
        private final int mProcessId;
        private final int mProcessNameHash;
        private final int mThreadId;
        private final int mThreadNameHash;

        ThreadKey(int i, int i2, String str, String str2) {
            this.mProcessId = i;
            this.mThreadId = i2;
            this.mProcessNameHash = Objects.hash(str);
            this.mThreadNameHash = Objects.hash(str2);
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mProcessId), Integer.valueOf(this.mThreadId), Integer.valueOf(this.mProcessNameHash), Integer.valueOf(this.mThreadNameHash));
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ThreadKey)) {
                return false;
            }
            ThreadKey threadKey = (ThreadKey) obj;
            return this.mProcessId == threadKey.mProcessId && this.mThreadId == threadKey.mThreadId && this.mProcessNameHash == threadKey.mProcessNameHash && this.mThreadNameHash == threadKey.mThreadNameHash;
        }
    }
}
