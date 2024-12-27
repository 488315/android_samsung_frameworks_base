package android.hardware.camera2.params;

import android.hardware.camera2.utils.HashCodeHelpers;
import android.text.TextUtils;

import com.android.internal.util.Preconditions;

import java.util.Arrays;

/* loaded from: classes2.dex */
public final class LensIntrinsicsSample {
    private final float[] mLensIntrinsics;
    private final long mTimestampNs;

    public LensIntrinsicsSample(long timestampNs, float[] lensIntrinsics) {
        this.mTimestampNs = timestampNs;
        Preconditions.checkArgument(lensIntrinsics.length == 5);
        this.mLensIntrinsics = lensIntrinsics;
    }

    public long getTimestampNanos() {
        return this.mTimestampNs;
    }

    public float[] getLensIntrinsics() {
        return this.mLensIntrinsics;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LensIntrinsicsSample)) {
            return false;
        }
        LensIntrinsicsSample other = (LensIntrinsicsSample) obj;
        if (this.mTimestampNs != other.mTimestampNs
                || !Arrays.equals(this.mLensIntrinsics, other.getLensIntrinsics())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int timestampHash = HashCodeHelpers.hashCode(this.mTimestampNs);
        return HashCodeHelpers.hashCode(Arrays.hashCode(this.mLensIntrinsics), timestampHash);
    }

    public String toString() {
        return TextUtils.formatSimple(
                "LensIntrinsicsSample{timestamp:%d, sample:%s}",
                Long.valueOf(this.mTimestampNs), Arrays.toString(this.mLensIntrinsics));
    }
}
