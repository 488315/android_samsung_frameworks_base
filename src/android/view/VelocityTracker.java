package android.view;

import android.hardware.input.InputManagerGlobal;
import android.util.ArrayMap;
import android.util.Pools;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

/* loaded from: classes4.dex */
public final class VelocityTracker {
    private static final int ACTIVE_POINTER_ID = -1;
    private static final Map<String, Integer> STRATEGIES;
    public static final int VELOCITY_TRACKER_STRATEGY_DEFAULT = -1;
    public static final int VELOCITY_TRACKER_STRATEGY_IMPULSE = 0;
    public static final int VELOCITY_TRACKER_STRATEGY_INT1 = 7;
    public static final int VELOCITY_TRACKER_STRATEGY_INT2 = 8;
    public static final int VELOCITY_TRACKER_STRATEGY_LEGACY = 9;
    public static final int VELOCITY_TRACKER_STRATEGY_LSQ1 = 1;
    public static final int VELOCITY_TRACKER_STRATEGY_LSQ2 = 2;
    public static final int VELOCITY_TRACKER_STRATEGY_LSQ3 = 3;
    public static final int VELOCITY_TRACKER_STRATEGY_WLSQ2_CENTRAL = 5;
    public static final int VELOCITY_TRACKER_STRATEGY_WLSQ2_DELTA = 4;
    public static final int VELOCITY_TRACKER_STRATEGY_WLSQ2_RECENT = 6;
    private static final Pools.SynchronizedPool<VelocityTracker> sPool = new Pools.SynchronizedPool<>(2);
    private long mPtr;
    private final int mStrategy;

    @Retention(RetentionPolicy.SOURCE)
    public @interface VelocityTrackableMotionEventAxis {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VelocityTrackerStrategy {
    }

    private static native void nativeAddMovement(long j, MotionEvent motionEvent);

    private static native void nativeClear(long j);

    private static native void nativeComputeCurrentVelocity(long j, int i, float f);

    private static native void nativeDispose(long j);

    private static native float nativeGetVelocity(long j, int i, int i2);

    private static native long nativeInitialize(int i);

    private static native boolean nativeIsAxisSupported(int i);

    static {
        ArrayMap arrayMap = new ArrayMap();
        STRATEGIES = arrayMap;
        arrayMap.put("impulse", 0);
        arrayMap.put("lsq1", 1);
        arrayMap.put("lsq2", 2);
        arrayMap.put("lsq3", 3);
        arrayMap.put("wlsq2-delta", 4);
        arrayMap.put("wlsq2-central", 5);
        arrayMap.put("wlsq2-recent", 6);
        arrayMap.put("int1", 7);
        arrayMap.put("int2", 8);
        arrayMap.put("legacy", 9);
    }

    private static int toStrategyId(String str) {
        Map<String, Integer> map = STRATEGIES;
        if (map.containsKey(str)) {
            return map.get(str).intValue();
        }
        return -1;
    }

    public static VelocityTracker obtain() {
        VelocityTracker acquire = sPool.acquire();
        return acquire != null ? acquire : new VelocityTracker(-1);
    }

    @Deprecated
    public static VelocityTracker obtain(String str) {
        if (str == null) {
            return obtain();
        }
        return new VelocityTracker(toStrategyId(str));
    }

    public static VelocityTracker obtain(int i) {
        if (i == -1) {
            return obtain();
        }
        return new VelocityTracker(i);
    }

    public void recycle() {
        if (this.mStrategy == -1) {
            clear();
            sPool.release(this);
        }
    }

    public int getStrategyId() {
        return this.mStrategy;
    }

    private VelocityTracker(int i) {
        if (i == -1) {
            String velocityTrackerStrategy = InputManagerGlobal.getInstance().getVelocityTrackerStrategy();
            if (velocityTrackerStrategy == null || velocityTrackerStrategy.isEmpty()) {
                this.mStrategy = i;
            } else {
                this.mStrategy = toStrategyId(velocityTrackerStrategy);
            }
        } else {
            this.mStrategy = i;
        }
        this.mPtr = nativeInitialize(this.mStrategy);
    }

    protected void finalize() throws Throwable {
        try {
            long j = this.mPtr;
            if (j != 0) {
                nativeDispose(j);
                this.mPtr = 0L;
            }
        } finally {
            super.finalize();
        }
    }

    public boolean isAxisSupported(int i) {
        return nativeIsAxisSupported(i);
    }

    public void clear() {
        nativeClear(this.mPtr);
    }

    public void addMovement(MotionEvent motionEvent) {
        if (motionEvent == null) {
            throw new IllegalArgumentException("event must not be null");
        }
        nativeAddMovement(this.mPtr, motionEvent);
    }

    public void computeCurrentVelocity(int i) {
        nativeComputeCurrentVelocity(this.mPtr, i, Float.MAX_VALUE);
    }

    public void computeCurrentVelocity(int i, float f) {
        nativeComputeCurrentVelocity(this.mPtr, i, f);
    }

    public float getXVelocity() {
        return getXVelocity(-1);
    }

    public float getYVelocity() {
        return getYVelocity(-1);
    }

    public float getXVelocity(int i) {
        return nativeGetVelocity(this.mPtr, 0, i);
    }

    public float getYVelocity(int i) {
        return nativeGetVelocity(this.mPtr, 1, i);
    }

    public float getAxisVelocity(int i, int i2) {
        return nativeGetVelocity(this.mPtr, i, i2);
    }

    public float getAxisVelocity(int i) {
        return nativeGetVelocity(this.mPtr, i, -1);
    }
}
