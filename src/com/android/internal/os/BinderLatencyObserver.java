package com.android.internal.os;

import android.os.Binder;
import android.os.Handler;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.os.BinderInternal;
import com.android.internal.util.FrameworkStatsLog;
import java.util.Random;

/* loaded from: classes5.dex */
public class BinderLatencyObserver {
    public static final int BUCKET_COUNT_DEFAULT = 100;
    public static final float BUCKET_SCALE_FACTOR_DEFAULT = 1.125f;
    public static final int FIRST_BUCKET_SIZE_DEFAULT = 5;
    private static final int LAST_HISTOGRAM_BUFFER_SIZE_BYTES = 1000;
    private static final int MAX_ATOM_SIZE_BYTES = 4064;
    public static final int PERIODIC_SAMPLING_INTERVAL_DEFAULT = 10;
    public static final int SHARDING_MODULO_DEFAULT = 1;
    public static final int STATSD_PUSH_INTERVAL_MINUTES_DEFAULT = 360;
    private static final String TAG = "BinderLatencyObserver";
    private BinderLatencyBuckets mLatencyBuckets;
    private final Handler mLatencyObserverHandler;
    private final int mProcessSource;
    private final Random mRandom;
    private int mShardingOffset;
    private final ArrayMap<LatencyDims, int[]> mLatencyHistograms = new ArrayMap<>();
    private final Object mLock = new Object();
    private int mPeriodicSamplingInterval = 10;
    private int mShardingModulo = 1;
    private int mBucketCount = 100;
    private int mFirstBucketSize = 5;
    private float mBucketScaleFactor = 1.125f;
    private int mStatsdPushIntervalMinutes = 360;
    private Runnable mLatencyObserverRunnable = new Runnable() { // from class: com.android.internal.os.BinderLatencyObserver.1
        @Override // java.lang.Runnable
        public void run() throws NoSuchMethodException, SecurityException {
            ArrayMap arrayMap;
            BinderLatencyObserver.this.noteLatencyDelayed();
            synchronized (BinderLatencyObserver.this.mLock) {
                arrayMap = new ArrayMap(BinderLatencyObserver.this.mLatencyHistograms);
                BinderLatencyObserver.this.mLatencyHistograms.clear();
            }
            BinderTransactionNameResolver binderTransactionNameResolver = new BinderTransactionNameResolver();
            ProtoOutputStream protoOutputStream = new ProtoOutputStream();
            int i = 0;
            for (LatencyDims latencyDims : arrayMap.keySet()) {
                if (protoOutputStream.getRawSize() + 1000 > BinderLatencyObserver.this.getMaxAtomSizeBytes()) {
                    if (i > 0) {
                        BinderLatencyObserver.this.writeAtomToStatsd(protoOutputStream);
                    }
                    protoOutputStream = new ProtoOutputStream();
                    i = 0;
                }
                BinderLatencyObserver.this.fillApiStatsProto(protoOutputStream, latencyDims, binderTransactionNameResolver.getMethodName(latencyDims.getBinderClass(), latencyDims.getTransactionCode()), (int[]) arrayMap.get(latencyDims));
                i++;
            }
            if (i > 0) {
                BinderLatencyObserver.this.writeAtomToStatsd(protoOutputStream);
            }
        }
    };

    protected int getMaxAtomSizeBytes() {
        return MAX_ATOM_SIZE_BYTES;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fillApiStatsProto(ProtoOutputStream protoOutputStream, LatencyDims latencyDims, String str, int[] iArr) {
        int i;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            i = this.mBucketCount;
            if (i3 >= i) {
                break;
            }
            if (iArr[i3] != 0) {
                i2 = i3;
                break;
            }
            i3++;
        }
        int i4 = i - 1;
        int i5 = i - 1;
        while (true) {
            if (i5 < 0) {
                break;
            }
            if (iArr[i5] != 0) {
                i4 = i5;
                break;
            }
            i5--;
        }
        long jStart = protoOutputStream.start(2246267895809L);
        long jStart2 = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1159641169921L, this.mProcessSource);
        protoOutputStream.write(1138166333443L, latencyDims.getBinderClass().getName());
        protoOutputStream.write(1138166333445L, str);
        protoOutputStream.end(jStart2);
        protoOutputStream.write(1120986464258L, i2);
        while (i2 <= i4) {
            protoOutputStream.write(2220498092035L, iArr[i2]);
            i2++;
        }
        protoOutputStream.end(jStart);
    }

    protected void writeAtomToStatsd(ProtoOutputStream protoOutputStream) {
        FrameworkStatsLog.write(342, protoOutputStream.getBytes(), this.mPeriodicSamplingInterval, this.mShardingModulo, this.mBucketCount, this.mFirstBucketSize, this.mBucketScaleFactor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void noteLatencyDelayed() {
        this.mLatencyObserverHandler.removeCallbacks(this.mLatencyObserverRunnable);
        this.mLatencyObserverHandler.postDelayed(this.mLatencyObserverRunnable, this.mStatsdPushIntervalMinutes * 60000);
    }

    public static class Injector {
        public Random getRandomGenerator() {
            return new Random();
        }

        public Handler getHandler() {
            return BackgroundThread.getHandler();
        }
    }

    public BinderLatencyObserver(Injector injector, int i) {
        Random randomGenerator = injector.getRandomGenerator();
        this.mRandom = randomGenerator;
        this.mLatencyObserverHandler = injector.getHandler();
        this.mLatencyBuckets = new BinderLatencyBuckets(this.mBucketCount, this.mFirstBucketSize, this.mBucketScaleFactor);
        this.mProcessSource = i;
        this.mShardingOffset = randomGenerator.nextInt(this.mShardingModulo);
        noteLatencyDelayed();
    }

    public void callEnded(BinderInternal.CallSession callSession) {
        if (callSession == null || callSession.exceptionThrown || !shouldKeepSample()) {
            return;
        }
        LatencyDims latencyDimsCreate = LatencyDims.create(callSession.binderClass, callSession.transactionCode);
        if (shouldCollect(latencyDimsCreate)) {
            long elapsedRealtimeMicro = getElapsedRealtimeMicro() - callSession.timeStarted;
            int iSampleToBucket = this.mLatencyBuckets.sampleToBucket(elapsedRealtimeMicro > 2147483647L ? Integer.MAX_VALUE : (int) elapsedRealtimeMicro);
            synchronized (this.mLock) {
                int[] iArr = this.mLatencyHistograms.get(latencyDimsCreate);
                if (iArr == null) {
                    iArr = new int[this.mBucketCount];
                    this.mLatencyHistograms.put(latencyDimsCreate, iArr);
                }
                int i = iArr[iSampleToBucket];
                if (i < Integer.MAX_VALUE) {
                    iArr[iSampleToBucket] = i + 1;
                }
            }
        }
    }

    protected long getElapsedRealtimeMicro() {
        return SystemClock.elapsedRealtimeNanos() / 1000;
    }

    protected boolean shouldCollect(LatencyDims latencyDims) {
        return (latencyDims.hashCode() + this.mShardingOffset) % this.mShardingModulo == 0;
    }

    protected boolean shouldKeepSample() {
        return this.mRandom.nextInt(this.mPeriodicSamplingInterval) == 0;
    }

    public void setSamplingInterval(int i) {
        if (i <= 0) {
            Slog.w(TAG, "Ignored invalid sampling interval (value must be positive): " + i);
        } else {
            synchronized (this.mLock) {
                if (i != this.mPeriodicSamplingInterval) {
                    this.mPeriodicSamplingInterval = i;
                    reset();
                }
            }
        }
    }

    public void setShardingModulo(int i) {
        if (i <= 0) {
            Slog.w(TAG, "Ignored invalid sharding modulo (value must be positive): " + i);
        } else {
            synchronized (this.mLock) {
                if (i != this.mShardingModulo) {
                    this.mShardingModulo = i;
                    this.mShardingOffset = this.mRandom.nextInt(i);
                    reset();
                }
            }
        }
    }

    public void setPushInterval(int i) {
        if (i <= 0) {
            Slog.w(TAG, "Ignored invalid push interval (value must be positive): " + i);
        } else {
            synchronized (this.mLock) {
                if (i != this.mStatsdPushIntervalMinutes) {
                    this.mStatsdPushIntervalMinutes = i;
                    reset();
                }
            }
        }
    }

    public void setHistogramBucketsParams(int i, int i2, float f) {
        synchronized (this.mLock) {
            if (i != this.mBucketCount || i2 != this.mFirstBucketSize || f != this.mBucketScaleFactor) {
                this.mBucketCount = i;
                this.mFirstBucketSize = i2;
                this.mBucketScaleFactor = f;
                this.mLatencyBuckets = new BinderLatencyBuckets(this.mBucketCount, this.mFirstBucketSize, this.mBucketScaleFactor);
                reset();
            }
        }
    }

    public void reset() {
        synchronized (this.mLock) {
            this.mLatencyHistograms.clear();
        }
        noteLatencyDelayed();
    }

    public static class LatencyDims {
        private Class<? extends Binder> mBinderClass;
        private int mHashCode = 0;
        private int mTransactionCode;

        public static LatencyDims create(Class<? extends Binder> cls, int i) {
            return new LatencyDims(cls, i);
        }

        private LatencyDims(Class<? extends Binder> cls, int i) {
            this.mBinderClass = cls;
            this.mTransactionCode = i;
        }

        public Class<? extends Binder> getBinderClass() {
            return this.mBinderClass;
        }

        public int getTransactionCode() {
            return this.mTransactionCode;
        }

        public boolean equals(Object obj) {
            if (obj != null && (obj instanceof LatencyDims)) {
                LatencyDims latencyDims = (LatencyDims) obj;
                if (this.mTransactionCode == latencyDims.getTransactionCode() && this.mBinderClass == latencyDims.getBinderClass()) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            int i = this.mHashCode;
            if (i != 0) {
                return i;
            }
            int iHashCode = (this.mTransactionCode * 31) + this.mBinderClass.getName().hashCode();
            this.mHashCode = iHashCode;
            return iHashCode;
        }
    }

    public ArrayMap<LatencyDims, int[]> getLatencyHistograms() {
        return this.mLatencyHistograms;
    }

    public Runnable getStatsdPushRunnable() {
        return this.mLatencyObserverRunnable;
    }

    public int getProcessSource() {
        return this.mProcessSource;
    }
}
