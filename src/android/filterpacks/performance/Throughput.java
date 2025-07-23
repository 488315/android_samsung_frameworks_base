package android.filterpacks.performance;

/* loaded from: classes.dex */
public class Throughput {
    private final int mPeriodFrames;
    private final int mPeriodTime;
    private final int mPixels;
    private final int mTotalFrames;

    public Throughput(int i, int i2, int i3, int i4) {
        this.mTotalFrames = i;
        this.mPeriodFrames = i2;
        this.mPeriodTime = i3;
        this.mPixels = i4;
    }

    public int getTotalFrameCount() {
        return this.mTotalFrames;
    }

    public int getPeriodFrameCount() {
        return this.mPeriodFrames;
    }

    public int getPeriodTime() {
        return this.mPeriodTime;
    }

    public float getFramesPerSecond() {
        return this.mPeriodFrames / this.mPeriodTime;
    }

    public float getNanosPerPixel() {
        return (float) (((this.mPeriodTime / this.mPeriodFrames) * 1000000.0d) / this.mPixels);
    }

    public String toString() {
        return getFramesPerSecond() + " FPS";
    }
}
