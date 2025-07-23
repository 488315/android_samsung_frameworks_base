package android.hardware.camera2.params;

import android.hardware.camera2.utils.HashCodeHelpers;
import android.util.Range;
import android.util.Size;
import com.android.internal.util.Preconditions;

/* loaded from: classes2.dex */
public final class Capability {
    public static final int COUNT = 3;
    private final Size mMaxStreamingSize;
    private final int mMode;
    private final Range<Float> mZoomRatioRange;

    public Capability(int i, Size size, Range<Float> range) {
        this.mMode = i;
        Preconditions.checkArgumentNonnegative(size.getWidth(), "maxStreamingSize.getWidth() must be nonnegative");
        Preconditions.checkArgumentNonnegative(size.getHeight(), "maxStreamingSize.getHeight() must be nonnegative");
        this.mMaxStreamingSize = size;
        if (range.getLower().floatValue() > range.getUpper().floatValue()) {
            throw new IllegalArgumentException("zoomRatioRange.getLower() " + range.getLower() + " is greater than zoomRatioRange.getUpper() " + range.getUpper());
        }
        Preconditions.checkArgumentPositive(range.getLower().floatValue(), "zoomRatioRange.getLower() must be positive");
        Preconditions.checkArgumentPositive(range.getUpper().floatValue(), "zoomRatioRange.getUpper() must be positive");
        this.mZoomRatioRange = range;
    }

    public int getMode() {
        return this.mMode;
    }

    public Size getMaxStreamingSize() {
        return this.mMaxStreamingSize;
    }

    public Range<Float> getZoomRatioRange() {
        return this.mZoomRatioRange;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Capability) {
            Capability capability = (Capability) obj;
            if (this.mMode == capability.mMode && this.mMaxStreamingSize.equals(capability.mMaxStreamingSize) && this.mZoomRatioRange.equals(capability.mZoomRatioRange)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCode(this.mMode, this.mMaxStreamingSize.getWidth(), this.mMaxStreamingSize.getHeight(), this.mZoomRatioRange.getLower().floatValue(), this.mZoomRatioRange.getUpper().floatValue());
    }

    public String toString() {
        return String.format("(mode:%d, maxStreamingSize:%d x %d, zoomRatio: %f-%f)", Integer.valueOf(this.mMode), Integer.valueOf(this.mMaxStreamingSize.getWidth()), Integer.valueOf(this.mMaxStreamingSize.getHeight()), this.mZoomRatioRange.getLower(), this.mZoomRatioRange.getUpper());
    }
}
