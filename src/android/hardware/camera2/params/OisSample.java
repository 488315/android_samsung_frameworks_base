package android.hardware.camera2.params;

import android.hardware.camera2.utils.HashCodeHelpers;
import com.android.internal.util.Preconditions;

/* loaded from: classes2.dex */
public final class OisSample {
    private final long mTimestampNs;
    private final float mXShift;
    private final float mYShift;

    public OisSample(long j, float f, float f2) {
        this.mTimestampNs = j;
        this.mXShift = Preconditions.checkArgumentFinite(f, "xShift must be finite");
        this.mYShift = Preconditions.checkArgumentFinite(f2, "yShift must be finite");
    }

    public long getTimestamp() {
        return this.mTimestampNs;
    }

    public float getXshift() {
        return this.mXShift;
    }

    public float getYshift() {
        return this.mYShift;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof OisSample) {
            OisSample oisSample = (OisSample) obj;
            if (this.mTimestampNs == oisSample.mTimestampNs && this.mXShift == oisSample.mXShift && this.mYShift == oisSample.mYShift) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCode(this.mXShift, this.mYShift, HashCodeHelpers.hashCode(this.mTimestampNs));
    }

    public String toString() {
        return String.format("OisSample{timestamp:%d, shift_x:%f, shift_y:%f}", Long.valueOf(this.mTimestampNs), Float.valueOf(this.mXShift), Float.valueOf(this.mYShift));
    }
}
