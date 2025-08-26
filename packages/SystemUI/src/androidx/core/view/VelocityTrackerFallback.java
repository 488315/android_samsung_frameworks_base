package androidx.core.view;

/* loaded from: classes.dex */
public class VelocityTrackerFallback {
    public final float[] mMovements = new float[20];
    public final long[] mEventTimes = new long[20];
    public float mLastComputedVelocity = 0.0f;
    public int mDataPointsBufferSize = 0;
    public final int mDataPointsBufferLastUsedIndex = 0;
}
