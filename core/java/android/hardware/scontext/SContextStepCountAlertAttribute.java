package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
public class SContextStepCountAlertAttribute extends SContextAttribute {
    private static final String TAG = "SContextStepCountAlertAttribute";
    private int mStepCount;

    SContextStepCountAlertAttribute() {
        this.mStepCount = 10;
        setAttribute();
    }

    public SContextStepCountAlertAttribute(int stepCount) {
        this.mStepCount = 10;
        this.mStepCount = stepCount;
        setAttribute();
    }

    public int getStepCount() {
        return this.mStepCount;
    }

    @Override // android.hardware.scontext.SContextAttribute,
    // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mStepCount < 0) {
            Log.e(TAG, "The step count is wrong.");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        Bundle attribute2 = new Bundle();
        attribute.putInt("step_count", this.mStepCount);
        super.setAttribute(3, attribute);
        attribute2.putInt("interrupt_gyro", this.mStepCount);
        super.setAttribute(48, attribute2);
    }
}
