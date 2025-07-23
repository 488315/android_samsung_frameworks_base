package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextMovementForPositioningAttribute extends SContextAttribute {
    private static final String TAG = "SContextMovementForPositioningAttribute";
    private double mMoveDistanceThrs;
    private int mMoveDurationThrs;
    private int mMoveMinDurationThrs;
    private int mNomoveDurationThrs;

    SContextMovementForPositioningAttribute() {
        this.mNomoveDurationThrs = 60;
        this.mMoveDurationThrs = 60;
        this.mMoveDistanceThrs = 100.0d;
        this.mMoveMinDurationThrs = 5;
        setAttribute();
    }

    public SContextMovementForPositioningAttribute(int i, int i2, double d, int i3) {
        this.mNomoveDurationThrs = i;
        this.mMoveDurationThrs = i2;
        this.mMoveDistanceThrs = d;
        this.mMoveMinDurationThrs = i3;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mNomoveDurationThrs < 0) {
            Log.e(TAG, "The nomove duration threshold is wrong.");
            return false;
        }
        if (this.mMoveDurationThrs < 0) {
            Log.e(TAG, "The move duration threshold is wrong.");
            return false;
        }
        if (this.mMoveDistanceThrs < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            Log.e(TAG, "The move distance threshold is wrong.");
            return false;
        }
        if (this.mMoveMinDurationThrs >= 0) {
            return true;
        }
        Log.e(TAG, "The move minimum duration threshold is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("nomove_duration_thrs", this.mNomoveDurationThrs);
        bundle.putInt("move_duration_thrs", this.mMoveDurationThrs);
        bundle.putDouble("move_distance_thrs", this.mMoveDistanceThrs);
        bundle.putInt("move_min_duration_trhs", this.mMoveMinDurationThrs);
        super.setAttribute(9, bundle);
    }
}
