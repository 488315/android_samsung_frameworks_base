package android.util;

import android.os.SystemClock;
import java.util.concurrent.TimeoutException;

/* loaded from: classes4.dex */
public abstract class TimedRemoteCaller<T> {
    public static final long DEFAULT_CALL_TIMEOUT_MILLIS = 5000;
    private final long mCallTimeoutMillis;
    private int mSequenceCounter;
    private final Object mLock = new Object();
    private final SparseIntArray mAwaitedCalls = new SparseIntArray(1);
    private final SparseArray<T> mReceivedCalls = new SparseArray<>(1);

    public TimedRemoteCaller(long j) {
        this.mCallTimeoutMillis = j;
    }

    protected final int onBeforeRemoteCall() {
        int i;
        synchronized (this.mLock) {
            do {
                i = this.mSequenceCounter;
                this.mSequenceCounter = i + 1;
            } while (this.mAwaitedCalls.get(i) != 0);
            this.mAwaitedCalls.put(i, 1);
        }
        return i;
    }

    protected final void onRemoteMethodResult(T t, int i) {
        synchronized (this.mLock) {
            if (this.mAwaitedCalls.get(i) != 0) {
                this.mAwaitedCalls.delete(i);
                this.mReceivedCalls.put(i, t);
                this.mLock.notifyAll();
            }
        }
    }

    protected final T getResultTimed(int i) throws TimeoutException {
        long jUptimeMillis = SystemClock.uptimeMillis();
        while (true) {
            try {
                synchronized (this.mLock) {
                    if (this.mReceivedCalls.indexOfKey(i) >= 0) {
                        return this.mReceivedCalls.removeReturnOld(i);
                    }
                    long jUptimeMillis2 = this.mCallTimeoutMillis - (SystemClock.uptimeMillis() - jUptimeMillis);
                    if (jUptimeMillis2 <= 0) {
                        this.mAwaitedCalls.delete(i);
                        throw new TimeoutException("No response for sequence: " + i);
                    }
                    this.mLock.wait(jUptimeMillis2);
                }
            } catch (InterruptedException unused) {
            }
        }
    }
}
