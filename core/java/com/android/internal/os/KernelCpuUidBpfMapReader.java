package com.android.internal.os;

import android.os.SystemClock;
import android.util.Slog;
import android.util.SparseArray;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes5.dex */
public abstract class KernelCpuUidBpfMapReader {
    private static final int ERROR_THRESHOLD = 5;
    private static final long FRESHNESS_MS = 500;
    private static final KernelCpuUidBpfMapReader FREQ_TIME_READER = new KernelCpuUidFreqTimeBpfMapReader();
    private static final KernelCpuUidBpfMapReader FULL_TIME_READER = new KernelCpuUidFullTimeBpfMapReader();
    private static final KernelCpuUidBpfMapReader ACTIVE_TIME_READER = new KernelCpuUidActiveTimeBpfMapReader();
    private static final KernelCpuUidBpfMapReader CLUSTER_TIME_READER = new KernelCpuUidClusterTimeBpfMapReader();
    final String mTag = getClass().getSimpleName();
    private int mErrors = 0;
    protected SparseArray<long[]> mData = new SparseArray<>();
    private long mLastReadTime = 0;
    protected final ReentrantReadWriteLock mLock = new ReentrantReadWriteLock();
    protected final ReentrantReadWriteLock.ReadLock mReadLock = this.mLock.readLock();
    protected final ReentrantReadWriteLock.WriteLock mWriteLock = this.mLock.writeLock();

    public static class KernelCpuUidActiveTimeBpfMapReader extends KernelCpuUidBpfMapReader {
        @Override // com.android.internal.os.KernelCpuUidBpfMapReader
        public final native long[] getDataDimensions();

        @Override // com.android.internal.os.KernelCpuUidBpfMapReader
        protected final native boolean readBpfData();
    }

    public static class KernelCpuUidClusterTimeBpfMapReader extends KernelCpuUidBpfMapReader {
        @Override // com.android.internal.os.KernelCpuUidBpfMapReader
        public final native long[] getDataDimensions();

        @Override // com.android.internal.os.KernelCpuUidBpfMapReader
        protected final native boolean readBpfData();
    }

    public static class KernelCpuUidFullTimeBpfMapReader extends KernelCpuUidBpfMapReader {
        @Override // com.android.internal.os.KernelCpuUidBpfMapReader
        public final native long[] getDataDimensions();

        @Override // com.android.internal.os.KernelCpuUidBpfMapReader
        protected final native boolean readBpfData();
    }

    public abstract long[] getDataDimensions();

    protected abstract boolean readBpfData();

    static KernelCpuUidBpfMapReader getFreqTimeReaderInstance() {
        return FREQ_TIME_READER;
    }

    static KernelCpuUidBpfMapReader getFullTimeReaderInstance() {
        return FULL_TIME_READER;
    }

    static KernelCpuUidBpfMapReader getActiveTimeReaderInstance() {
        return ACTIVE_TIME_READER;
    }

    static KernelCpuUidBpfMapReader getClusterTimeReaderInstance() {
        return CLUSTER_TIME_READER;
    }

    public boolean startTrackingBpfTimes() {
        return KernelCpuBpfTracking.startTracking();
    }

    public void removeUidsInRange(int startUid, int endUid) {
        if (this.mErrors > 5 || endUid < startUid || startUid < 0) {
            return;
        }
        this.mWriteLock.lock();
        int firstIndex = this.mData.indexOfKey(startUid);
        if (firstIndex < 0) {
            this.mData.put(startUid, null);
            firstIndex = this.mData.indexOfKey(startUid);
        }
        int lastIndex = this.mData.indexOfKey(endUid);
        if (lastIndex < 0) {
            this.mData.put(endUid, null);
            lastIndex = this.mData.indexOfKey(endUid);
        }
        this.mData.removeAtRange(firstIndex, (lastIndex - firstIndex) + 1);
        this.mWriteLock.unlock();
    }

    public BpfMapIterator open() {
        return open(false);
    }

    public BpfMapIterator open(boolean ignoreCache) {
        if (this.mErrors > 5) {
            return null;
        }
        if (!startTrackingBpfTimes()) {
            Slog.w(this.mTag, "Failed to start tracking");
            this.mErrors++;
            return null;
        }
        if (ignoreCache) {
            this.mWriteLock.lock();
        } else {
            this.mReadLock.lock();
            if (dataValid()) {
                return new BpfMapIterator();
            }
            this.mReadLock.unlock();
            this.mWriteLock.lock();
            if (dataValid()) {
                this.mReadLock.lock();
                this.mWriteLock.unlock();
                return new BpfMapIterator();
            }
        }
        if (readBpfData()) {
            this.mLastReadTime = SystemClock.elapsedRealtime();
            this.mReadLock.lock();
            this.mWriteLock.unlock();
            return new BpfMapIterator();
        }
        this.mWriteLock.unlock();
        this.mErrors++;
        Slog.w(this.mTag, "Failed to read bpf times");
        return null;
    }

    private boolean dataValid() {
        return this.mData.size() > 0 && SystemClock.elapsedRealtime() - this.mLastReadTime < FRESHNESS_MS;
    }

    public class BpfMapIterator implements AutoCloseable {
        private int mPos;

        public BpfMapIterator() {
        }

        public boolean getNextUid(long[] buf) {
            if (this.mPos >= KernelCpuUidBpfMapReader.this.mData.size()) {
                return false;
            }
            buf[0] = KernelCpuUidBpfMapReader.this.mData.keyAt(this.mPos);
            System.arraycopy(KernelCpuUidBpfMapReader.this.mData.valueAt(this.mPos), 0, buf, 1, KernelCpuUidBpfMapReader.this.mData.valueAt(this.mPos).length);
            this.mPos++;
            return true;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            KernelCpuUidBpfMapReader.this.mReadLock.unlock();
        }
    }

    public static class KernelCpuUidFreqTimeBpfMapReader extends KernelCpuUidBpfMapReader {
        private final native boolean removeUidRange(int i, int i2);

        @Override // com.android.internal.os.KernelCpuUidBpfMapReader
        protected final native boolean readBpfData();

        @Override // com.android.internal.os.KernelCpuUidBpfMapReader
        public final long[] getDataDimensions() {
            return KernelCpuBpfTracking.getFreqsInternal();
        }

        @Override // com.android.internal.os.KernelCpuUidBpfMapReader
        public void removeUidsInRange(int startUid, int endUid) {
            this.mWriteLock.lock();
            super.removeUidsInRange(startUid, endUid);
            removeUidRange(startUid, endUid);
            this.mWriteLock.unlock();
        }
    }
}
