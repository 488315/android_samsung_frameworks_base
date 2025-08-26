package androidx.transition;

import java.util.Arrays;

/* loaded from: classes.dex */
public class VelocityTracker1D {
    public final float[] mDataSamples = new float[20];
    public int mIndex = 0;
    public final long[] mTimeSamples;

    public VelocityTracker1D() {
        long[] jArr = new long[20];
        this.mTimeSamples = jArr;
        Arrays.fill(jArr, Long.MIN_VALUE);
    }
}
