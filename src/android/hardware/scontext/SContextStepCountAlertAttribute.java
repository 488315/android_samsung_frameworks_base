package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextStepCountAlertAttribute extends SContextAttribute {
    private static final String TAG = "SContextStepCountAlertAttribute";
    private int mStepCount;

    SContextStepCountAlertAttribute() {
        this.mStepCount = 10;
        setAttribute();
    }

    public SContextStepCountAlertAttribute(int i) {
        this.mStepCount = i;
        setAttribute();
    }

    public int getStepCount() {
        return this.mStepCount;
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mStepCount >= 0) {
            return true;
        }
        Log.e(TAG, "The step count is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        Bundle bundle2 = new Bundle();
        bundle.putInt("step_count", this.mStepCount);
        super.setAttribute(3, bundle);
        bundle2.putInt("interrupt_gyro", this.mStepCount);
        super.setAttribute(48, bundle2);
    }
}
