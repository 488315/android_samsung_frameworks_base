package android.hardware.camera2.params;

import android.hardware.camera2.utils.HashCodeHelpers;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class LensIntrinsicsSample {
    private final float[] mLensIntrinsics;
    private final long mTimestampNs;

    public LensIntrinsicsSample(long j, float[] fArr) {
        this.mTimestampNs = j;
        Preconditions.checkArgument(fArr.length == 5);
        this.mLensIntrinsics = fArr;
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
        if (obj instanceof LensIntrinsicsSample) {
            LensIntrinsicsSample lensIntrinsicsSample = (LensIntrinsicsSample) obj;
            if (this.mTimestampNs == lensIntrinsicsSample.mTimestampNs && Arrays.equals(this.mLensIntrinsics, lensIntrinsicsSample.getLensIntrinsics())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCode(Arrays.hashCode(this.mLensIntrinsics), HashCodeHelpers.hashCode(this.mTimestampNs));
    }

    public String toString() {
        return TextUtils.formatSimple("LensIntrinsicsSample{timestamp:%d, sample:%s}", Long.valueOf(this.mTimestampNs), Arrays.toString(this.mLensIntrinsics));
    }
}
