package androidx.core.view;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class VelocityTrackerFallback {
    public final float[] mMovements = new float[20];
    public final long[] mEventTimes = new long[20];
    public float mLastComputedVelocity = 0.0f;
    public int mDataPointsBufferSize = 0;
    public final int mDataPointsBufferLastUsedIndex = 0;
}
