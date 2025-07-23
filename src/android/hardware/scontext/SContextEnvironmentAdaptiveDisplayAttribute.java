package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextEnvironmentAdaptiveDisplayAttribute extends SContextAttribute {
    private static final String TAG = "SContextEnvironmentAdaptiveDisplayAttribute";
    private float mColorThreshold;
    private int mDuration;

    SContextEnvironmentAdaptiveDisplayAttribute() {
        this.mColorThreshold = 0.07f;
        this.mDuration = 35;
        setAttribute();
    }

    public SContextEnvironmentAdaptiveDisplayAttribute(float f, int i) {
        this.mColorThreshold = f;
        this.mDuration = i;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mColorThreshold < 0.0f) {
            Log.e(TAG, "The color threshold value is wrong.");
            return false;
        }
        int i = this.mDuration;
        if (i >= 0 && i <= 255) {
            return true;
        }
        Log.e(TAG, "The duration value is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putFloat("color_threshold", this.mColorThreshold);
        bundle.putInt("duration", this.mDuration);
        Log.d(TAG, "setAttribute() mColorThreshold : " + bundle.getFloat("color_threshold"));
        Log.d(TAG, "setAttribute() mDuration : " + bundle.getInt("duration"));
        super.setAttribute(44, bundle);
    }
}
