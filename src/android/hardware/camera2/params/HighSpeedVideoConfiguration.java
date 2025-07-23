package android.hardware.camera2.params;

import android.hardware.camera2.utils.HashCodeHelpers;
import android.util.Range;
import android.util.Size;
import com.android.internal.util.Preconditions;

/* loaded from: classes2.dex */
public final class HighSpeedVideoConfiguration {
    private static final int HIGH_SPEED_MAX_MINIMAL_FPS = 120;
    private final int mBatchSizeMax;
    private final int mFpsMax;
    private final int mFpsMin;
    private final Range<Integer> mFpsRange;
    private final int mHeight;
    private final Size mSize;
    private final int mWidth;

    public HighSpeedVideoConfiguration(int i, int i2, int i3, int i4, int i5) {
        if (i4 < 120) {
            throw new IllegalArgumentException("fpsMax must be at least 120");
        }
        this.mFpsMax = i4;
        int checkArgumentPositive = Preconditions.checkArgumentPositive(i, "width must be positive");
        this.mWidth = checkArgumentPositive;
        int checkArgumentPositive2 = Preconditions.checkArgumentPositive(i2, "height must be positive");
        this.mHeight = checkArgumentPositive2;
        int checkArgumentPositive3 = Preconditions.checkArgumentPositive(i3, "fpsMin must be positive");
        this.mFpsMin = checkArgumentPositive3;
        this.mSize = new Size(checkArgumentPositive, checkArgumentPositive2);
        this.mBatchSizeMax = Preconditions.checkArgumentPositive(i5, "batchSizeMax must be positive");
        this.mFpsRange = new Range<>(Integer.valueOf(checkArgumentPositive3), Integer.valueOf(i4));
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getFpsMin() {
        return this.mFpsMin;
    }

    public int getFpsMax() {
        return this.mFpsMax;
    }

    public Size getSize() {
        return this.mSize;
    }

    public int getBatchSizeMax() {
        return this.mBatchSizeMax;
    }

    public Range<Integer> getFpsRange() {
        return this.mFpsRange;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof HighSpeedVideoConfiguration) {
            HighSpeedVideoConfiguration highSpeedVideoConfiguration = (HighSpeedVideoConfiguration) obj;
            if (this.mWidth == highSpeedVideoConfiguration.mWidth && this.mHeight == highSpeedVideoConfiguration.mHeight && this.mFpsMin == highSpeedVideoConfiguration.mFpsMin && this.mFpsMax == highSpeedVideoConfiguration.mFpsMax && this.mBatchSizeMax == highSpeedVideoConfiguration.mBatchSizeMax) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCode(this.mWidth, this.mHeight, this.mFpsMin, this.mFpsMax);
    }
}
