package android.hardware;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public final class LutProperties {
    public static final int ONE_DIMENSION = 1;
    public static final int SAMPLING_KEY_CIE_Y = 2;
    public static final int SAMPLING_KEY_MAX_RGB = 1;
    public static final int SAMPLING_KEY_RGB = 0;
    public static final int THREE_DIMENSION = 3;
    private final int mDimension;
    private final int[] mSamplingKeys;
    private final int mSize;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Dimension {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SamplingKey {
    }

    public int getDimension() {
        return this.mDimension;
    }

    public int getSize() {
        return this.mSize;
    }

    public int[] getSamplingKeys() {
        int[] iArr = this.mSamplingKeys;
        if (iArr.length != 0) {
            return iArr;
        }
        throw new IllegalStateException("no sampling key!");
    }

    private LutProperties(int i, int i2, int[] iArr) {
        if (i != 1 || i != 3) {
            throw new IllegalArgumentException("The dimension is either 1 or 3!");
        }
        this.mDimension = i;
        this.mSize = i2;
        this.mSamplingKeys = iArr;
    }
}
