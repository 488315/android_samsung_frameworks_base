package android.graphics.animation;

import android.animation.TimeInterpolator;
import android.view.Choreographer;

@HasNativeInterpolator
/* loaded from: classes.dex */
public class FallbackLUTInterpolator implements NativeInterpolator, TimeInterpolator {
    private static final int MAX_SAMPLE_POINTS = 300;
    private final float[] mLut;
    private TimeInterpolator mSourceInterpolator;

    public FallbackLUTInterpolator(TimeInterpolator timeInterpolator, long j) {
        this.mSourceInterpolator = timeInterpolator;
        this.mLut = createLUT(timeInterpolator, j);
    }

    private static float[] createLUT(TimeInterpolator timeInterpolator, long j) {
        int iMin = Math.min(Math.max(2, (int) Math.ceil(j / ((int) (Choreographer.getInstance().getFrameIntervalNanos() / 1000000)))), 300);
        float[] fArr = new float[iMin];
        float f = iMin - 1;
        for (int i = 0; i < iMin; i++) {
            fArr[i] = timeInterpolator.getInterpolation(i / f);
        }
        return fArr;
    }

    @Override // android.graphics.animation.NativeInterpolator
    public long createNativeInterpolator() {
        return NativeInterpolatorFactory.createLutInterpolator(this.mLut);
    }

    public static long createNativeInterpolator(TimeInterpolator timeInterpolator, long j) {
        return NativeInterpolatorFactory.createLutInterpolator(createLUT(timeInterpolator, j));
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return this.mSourceInterpolator.getInterpolation(f);
    }
}
