package com.android.internal.os;

import android.util.SparseArray;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;

/* loaded from: classes5.dex */
public class KernelSingleUidTimeReader {
    private static final boolean DBG = false;
    private static final String PROC_FILE_DIR = "/proc/uid/";
    private static final String PROC_FILE_NAME = "/time_in_state";
    private static final String TAG = "com.android.internal.os.KernelSingleUidTimeReader";
    public static final int TOTAL_READ_ERROR_COUNT = 5;
    private static final String UID_TIMES_PROC_FILE = "/proc/uid_time_in_state";
    private boolean mBpfTimesAvailable;
    private final int mCpuFreqsCount;
    private boolean mCpuFreqsCountVerified;
    private final Injector mInjector;
    private SparseArray<long[]> mLastUidCpuTimeMs;
    private int mReadErrorCounter;
    private boolean mSingleUidCpuTimesAvailable;

    private static final native boolean canReadBpfTimes();

    public KernelSingleUidTimeReader(int i) {
        this(i, new Injector());
    }

    public KernelSingleUidTimeReader(int i, Injector injector) {
        this.mLastUidCpuTimeMs = new SparseArray<>();
        this.mSingleUidCpuTimesAvailable = true;
        this.mBpfTimesAvailable = true;
        this.mInjector = injector;
        this.mCpuFreqsCount = i;
        if (i == 0) {
            this.mSingleUidCpuTimesAvailable = false;
        }
    }

    public boolean singleUidCpuTimesAvailable() {
        return this.mSingleUidCpuTimesAvailable;
    }

    public long[] readDeltaMs(int i) {
        synchronized (this) {
            if (!this.mSingleUidCpuTimesAvailable) {
                return null;
            }
            if (this.mBpfTimesAvailable) {
                long[] readBpfData = this.mInjector.readBpfData(i);
                if (readBpfData.length == 0) {
                    this.mBpfTimesAvailable = false;
                } else {
                    if (!this.mCpuFreqsCountVerified && readBpfData.length != this.mCpuFreqsCount) {
                        this.mSingleUidCpuTimesAvailable = false;
                        return null;
                    }
                    this.mCpuFreqsCountVerified = true;
                    return computeDelta(i, readBpfData);
                }
            }
            String str = PROC_FILE_DIR + i + PROC_FILE_NAME;
            try {
                byte[] readData = this.mInjector.readData(str);
                if (!this.mCpuFreqsCountVerified) {
                    verifyCpuFreqsCount(readData.length, str);
                }
                ByteBuffer wrap = ByteBuffer.wrap(readData);
                wrap.order(ByteOrder.nativeOrder());
                return computeDelta(i, readCpuTimesFromByteBuffer(wrap));
            } catch (Exception unused) {
                int i2 = this.mReadErrorCounter + 1;
                this.mReadErrorCounter = i2;
                if (i2 >= 5) {
                    this.mSingleUidCpuTimesAvailable = false;
                }
                return null;
            }
        }
    }

    private void verifyCpuFreqsCount(int i, String str) {
        int i2 = i / 8;
        if (this.mCpuFreqsCount != i2) {
            this.mSingleUidCpuTimesAvailable = false;
            throw new IllegalStateException("Freq count didn't match,count from /proc/uid_time_in_state=" + this.mCpuFreqsCount + ", butcount from " + str + "=" + i2);
        }
        this.mCpuFreqsCountVerified = true;
    }

    private long[] readCpuTimesFromByteBuffer(ByteBuffer byteBuffer) {
        long[] jArr = new long[this.mCpuFreqsCount];
        for (int i = 0; i < this.mCpuFreqsCount; i++) {
            jArr[i] = byteBuffer.getLong() * 10;
        }
        return jArr;
    }

    public long[] computeDelta(int i, long[] jArr) {
        synchronized (this) {
            if (!this.mSingleUidCpuTimesAvailable) {
                return null;
            }
            long[] deltaLocked = getDeltaLocked(this.mLastUidCpuTimeMs.get(i), jArr);
            if (deltaLocked == null) {
                return null;
            }
            for (int length = deltaLocked.length - 1; length >= 0; length--) {
                if (deltaLocked[length] > 0) {
                    this.mLastUidCpuTimeMs.put(i, jArr);
                    return deltaLocked;
                }
            }
            return null;
        }
    }

    public long[] getDeltaLocked(long[] jArr, long[] jArr2) {
        int length = jArr2.length;
        do {
            length--;
            if (length < 0) {
                if (jArr == null) {
                    return jArr2;
                }
                long[] jArr3 = new long[jArr2.length];
                for (int length2 = jArr2.length - 1; length2 >= 0; length2--) {
                    long j = jArr2[length2] - jArr[length2];
                    jArr3[length2] = j;
                    if (j < 0) {
                        return null;
                    }
                }
                return jArr3;
            }
        } while (jArr2[length] >= 0);
        return null;
    }

    public void setAllUidsCpuTimesMs(SparseArray<long[]> sparseArray) {
        synchronized (this) {
            this.mLastUidCpuTimeMs.clear();
            for (int size = sparseArray.size() - 1; size >= 0; size--) {
                long[] valueAt = sparseArray.valueAt(size);
                if (valueAt != null) {
                    this.mLastUidCpuTimeMs.put(sparseArray.keyAt(size), (long[]) valueAt.clone());
                }
            }
        }
    }

    public void removeUid(int i) {
        synchronized (this) {
            this.mLastUidCpuTimeMs.delete(i);
        }
    }

    public void removeUidsInRange(int i, int i2) {
        if (i2 < i) {
            return;
        }
        synchronized (this) {
            this.mLastUidCpuTimeMs.put(i, null);
            this.mLastUidCpuTimeMs.put(i2, null);
            int indexOfKey = this.mLastUidCpuTimeMs.indexOfKey(i);
            this.mLastUidCpuTimeMs.removeAtRange(indexOfKey, (this.mLastUidCpuTimeMs.indexOfKey(i2) - indexOfKey) + 1);
        }
    }

    public void addDelta(int i, LongArrayMultiStateCounter longArrayMultiStateCounter, long j) {
        this.mInjector.addDelta(i, longArrayMultiStateCounter, j, null);
    }

    public void addDelta(int i, LongArrayMultiStateCounter longArrayMultiStateCounter, long j, long[] jArr) {
        this.mInjector.addDelta(i, longArrayMultiStateCounter, j, jArr);
    }

    public static class Injector {
        private static native boolean addDeltaForTest(int i, long j, long j2, long[][] jArr, long[] jArr2);

        private static native boolean addDeltaFromBpf(int i, long j, long j2, long[] jArr);

        public native long[] readBpfData(int i);

        public byte[] readData(String str) throws IOException {
            return Files.readAllBytes(Paths.get(str, new String[0]));
        }

        public boolean addDelta(int i, LongArrayMultiStateCounter longArrayMultiStateCounter, long j, long[] jArr) {
            return addDeltaFromBpf(i, longArrayMultiStateCounter.mNativeObject, j, jArr);
        }

        public boolean addDeltaForTest(int i, LongArrayMultiStateCounter longArrayMultiStateCounter, long j, long[][] jArr, long[] jArr2) {
            return addDeltaForTest(i, longArrayMultiStateCounter.mNativeObject, j, jArr, jArr2);
        }
    }

    public SparseArray<long[]> getLastUidCpuTimeMs() {
        return this.mLastUidCpuTimeMs;
    }

    public void setSingleUidCpuTimesAvailable(boolean z) {
        this.mSingleUidCpuTimesAvailable = z;
    }
}
