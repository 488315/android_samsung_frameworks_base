package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextPedometerAttribute extends SContextAttribute {
    private static int MODE_EXERCISE = 1;
    private static int MODE_USER_INFO = 0;
    private static final String TAG = "SContextPedometerAttribute";
    private int mExerciseMode;
    private int mGender;
    private double mHeight;
    private int mMode;
    private double mWeight;

    SContextPedometerAttribute() {
        this.mGender = 1;
        this.mHeight = 170.0d;
        this.mWeight = 60.0d;
        this.mExerciseMode = -1;
        this.mMode = MODE_USER_INFO;
        setAttribute();
    }

    public SContextPedometerAttribute(int i, double d, double d2) {
        this.mExerciseMode = -1;
        this.mMode = MODE_USER_INFO;
        this.mGender = i;
        this.mHeight = d;
        this.mWeight = d2;
        setAttribute();
    }

    public SContextPedometerAttribute(int i) {
        this.mGender = 1;
        this.mHeight = 170.0d;
        this.mWeight = 60.0d;
        this.mMode = MODE_EXERCISE;
        this.mExerciseMode = i;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mGender;
        if (i < 1 || i > 2) {
            Log.e(TAG, "The gender is wrong.");
            return false;
        }
        if (this.mHeight <= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            Log.e(TAG, "The height is wrong.");
            return false;
        }
        if (this.mWeight <= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            Log.e(TAG, "The weight is wrong.");
            return false;
        }
        if (this.mExerciseMode >= -1) {
            return true;
        }
        Log.e(TAG, "The exercise mode is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("mode", this.mMode);
        if (this.mMode == MODE_USER_INFO) {
            bundle.putInt("gender", this.mGender);
            bundle.putDouble("height", this.mHeight);
            bundle.putDouble("weight", this.mWeight);
        } else {
            bundle.putInt("exercise_mode", this.mExerciseMode);
        }
        super.setAttribute(2, bundle);
    }
}
