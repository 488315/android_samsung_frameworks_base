package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextSpecificPoseAlertAttribute extends SContextAttribute {
    private static final String TAG = "SContextSpecificPoseAlertAttribute";
    private int mMaximumAngle;
    private int mMinimumAngle;
    private int mMovingThrs;
    private int mRetentionTime;

    SContextSpecificPoseAlertAttribute() {
        this.mRetentionTime = 1;
        this.mMinimumAngle = -90;
        this.mMaximumAngle = 90;
        this.mMovingThrs = 1;
        setAttribute();
    }

    public SContextSpecificPoseAlertAttribute(int i, int i2, int i3, int i4) {
        this.mRetentionTime = i;
        this.mMinimumAngle = i2;
        this.mMaximumAngle = i3;
        this.mMovingThrs = i4;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mRetentionTime < 0) {
            Log.e(TAG, "The retention time is wrong.");
            return false;
        }
        int i = this.mMinimumAngle;
        if (i < -90 || i > 90) {
            Log.e(TAG, "The minimum angle is wrong. The angle must be between -90 and 90.");
            return false;
        }
        int i2 = this.mMaximumAngle;
        if (i2 < -90 || i2 > 90) {
            Log.e(TAG, "The maximum angle is wrong. The angle must be between -90 and 90.");
            return false;
        }
        if (i > i2) {
            Log.e(TAG, "The minimum angle must be less than the maximum angle.");
            return false;
        }
        if (this.mMovingThrs >= 0) {
            return true;
        }
        Log.e(TAG, "The moving threshold is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("retention_time", this.mRetentionTime);
        bundle.putInt("minimum_angle", this.mMinimumAngle);
        bundle.putInt("maximum_angle", this.mMaximumAngle);
        bundle.putInt("moving_thrs", this.mMovingThrs);
        super.setAttribute(28, bundle);
    }
}
