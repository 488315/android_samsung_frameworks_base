package android.hardware.camera2.params;

import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class BlackLevelPattern {
    public static final int COUNT = 4;
    private final int[] mCfaOffsets;

    public BlackLevelPattern(int[] iArr) {
        if (iArr == null) {
            throw new NullPointerException("Null offsets array passed to constructor");
        }
        if (iArr.length < 4) {
            throw new IllegalArgumentException("Invalid offsets array length");
        }
        this.mCfaOffsets = Arrays.copyOf(iArr, 4);
    }

    public int getOffsetForIndex(int i, int i2) {
        if (i2 < 0 || i < 0) {
            throw new IllegalArgumentException("column, row arguments must be positive");
        }
        return this.mCfaOffsets[(i & 1) | ((i2 & 1) << 1)];
    }

    public void copyTo(int[] iArr, int i) {
        Objects.requireNonNull(iArr, "destination must not be null");
        if (i < 0) {
            throw new IllegalArgumentException("Null offset passed to copyTo");
        }
        if (iArr.length - i < 4) {
            throw new ArrayIndexOutOfBoundsException("destination too small to fit elements");
        }
        for (int i2 = 0; i2 < 4; i2++) {
            iArr[i + i2] = this.mCfaOffsets[i2];
        }
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof BlackLevelPattern) {
            return Arrays.equals(((BlackLevelPattern) obj).mCfaOffsets, this.mCfaOffsets);
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(this.mCfaOffsets);
    }

    public String toString() {
        return String.format("BlackLevelPattern([%d, %d], [%d, %d])", Integer.valueOf(this.mCfaOffsets[0]), Integer.valueOf(this.mCfaOffsets[1]), Integer.valueOf(this.mCfaOffsets[2]), Integer.valueOf(this.mCfaOffsets[3]));
    }
}
