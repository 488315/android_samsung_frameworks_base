package android.content.res;

/* loaded from: classes.dex */
public class TagCounter {
    private static final int DEFAULT_MAX_COUNT = 512;
    private int mMaxValue = 512;
    private int mCount = 0;

    void reset(int maxValue) {
        this.mMaxValue = maxValue;
        this.mCount = 0;
    }

    void increment() {
        this.mCount++;
    }

    public boolean isValid() {
        return this.mCount <= this.mMaxValue;
    }
}
