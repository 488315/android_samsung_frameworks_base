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
    private long mPtr;
    private final int mStrategy;
    private static final Pools.SynchronizedPool<VelocityTracker> sPool =
            new Pools.SynchronizedPool<>(2);
    private static final Map<String, Integer> STRATEGIES = new ArrayMap();

    @Retention(RetentionPolicy.SOURCE)
    public @interface VelocityTrackableMotionEventAxis {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface VelocityTrackerStrategy {}

    private static native void nativeAddMovement(long j, MotionEvent motionEvent);

    private static native void nativeClear(long j);

    private static native void nativeComputeCurrentVelocity(long j, int i, float f);

    private static native void nativeDispose(long j);

    private static native float nativeGetVelocity(long j, int i, int i2);

    private static native long nativeInitialize(int i);

    private static native boolean nativeIsAxisSupported(int i);

    static {
        STRATEGIES.put("impulse", 0);
        STRATEGIES.put("lsq1", 1);
        STRATEGIES.put("lsq2", 2);
        STRATEGIES.put("lsq3", 3);
        STRATEGIES.put("wlsq2-delta", 4);
        STRATEGIES.put("wlsq2-central", 5);
        STRATEGIES.put("wlsq2-recent", 6);
        STRATEGIES.put("int1", 7);
        STRATEGIES.put("int2", 8);
        STRATEGIES.put("legacy", 9);
    }

    private static int toStrategyId(String strStrategy) {
        if (STRATEGIES.containsKey(strStrategy)) {
            return STRATEGIES.get(strStrategy).intValue();
        }
        return -1;
    }

    public static VelocityTracker obtain() {
        VelocityTracker instance = sPool.acquire();
        return instance != null ? instance : new VelocityTracker(-1);
    }

    @Deprecated
    public static VelocityTracker obtain(String strategy) {
        if (strategy == null) {
            return obtain();
        }
        return new VelocityTracker(toStrategyId(strategy));
    }

    public static VelocityTracker obtain(int strategy) {
        if (strategy == -1) {
            return obtain();
        }
        return new VelocityTracker(strategy);
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

    private VelocityTracker(int strategy) {
        if (strategy == -1) {
            String strategyProperty = InputManagerGlobal.getInstance().getVelocityTrackerStrategy();
            if (strategyProperty == null || strategyProperty.isEmpty()) {
                this.mStrategy = strategy;
            } else {
                this.mStrategy = toStrategyId(strategyProperty);
            }
        } else {
            this.mStrategy = strategy;
        }
        this.mPtr = nativeInitialize(this.mStrategy);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mPtr != 0) {
                nativeDispose(this.mPtr);
                this.mPtr = 0L;
            }
        } finally {
            super.finalize();
        }
    }

    public boolean isAxisSupported(int axis) {
        return nativeIsAxisSupported(axis);
    }

    public void clear() {
        nativeClear(this.mPtr);
    }

    public void addMovement(MotionEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("event must not be null");
        }
        nativeAddMovement(this.mPtr, event);
    }

    public void computeCurrentVelocity(int units) {
        nativeComputeCurrentVelocity(this.mPtr, units, Float.MAX_VALUE);
    }

    public void computeCurrentVelocity(int units, float maxVelocity) {
        nativeComputeCurrentVelocity(this.mPtr, units, maxVelocity);
    }

    public float getXVelocity() {
        return getXVelocity(-1);
    }

    public float getYVelocity() {
        return getYVelocity(-1);
    }

    public float getXVelocity(int id) {
        return nativeGetVelocity(this.mPtr, 0, id);
    }

    public float getYVelocity(int id) {
        return nativeGetVelocity(this.mPtr, 1, id);
    }

    public float getAxisVelocity(int axis, int id) {
        return nativeGetVelocity(this.mPtr, axis, id);
    }

    public float getAxisVelocity(int axis) {
        return nativeGetVelocity(this.mPtr, axis, -1);
    }
}
