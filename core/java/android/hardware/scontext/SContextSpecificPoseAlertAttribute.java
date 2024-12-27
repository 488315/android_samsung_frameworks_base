package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
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

    public SContextSpecificPoseAlertAttribute(
            int retentionTime, int minimumAngle, int maximumAngle, int movingThrs) {
        this.mRetentionTime = 1;
        this.mMinimumAngle = -90;
        this.mMaximumAngle = 90;
        this.mMovingThrs = 1;
        this.mRetentionTime = retentionTime;
        this.mMinimumAngle = minimumAngle;
        this.mMaximumAngle = maximumAngle;
        this.mMovingThrs = movingThrs;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute,
    // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mRetentionTime < 0) {
            Log.e(TAG, "The retention time is wrong.");
            return false;
        }
        if (this.mMinimumAngle < -90 || this.mMinimumAngle > 90) {
            Log.e(TAG, "The minimum angle is wrong. The angle must be between -90 and 90.");
            return false;
        }
        if (this.mMaximumAngle < -90 || this.mMaximumAngle > 90) {
            Log.e(TAG, "The maximum angle is wrong. The angle must be between -90 and 90.");
            return false;
        }
        if (this.mMinimumAngle > this.mMaximumAngle) {
            Log.e(TAG, "The minimum angle must be less than the maximum angle.");
            return false;
        }
        if (this.mMovingThrs < 0) {
            Log.e(TAG, "The moving threshold is wrong.");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("retention_time", this.mRetentionTime);
        attribute.putInt("minimum_angle", this.mMinimumAngle);
        attribute.putInt("maximum_angle", this.mMaximumAngle);
        attribute.putInt("moving_thrs", this.mMovingThrs);
        super.setAttribute(28, attribute);
    }
}
