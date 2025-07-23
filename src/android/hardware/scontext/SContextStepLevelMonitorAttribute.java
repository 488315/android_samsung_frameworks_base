package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextStepLevelMonitorAttribute extends SContextAttribute {
    private static final String TAG = "SContextStepLevelMonitorAttribute";
    private int mDuration;

    SContextStepLevelMonitorAttribute() {
        this.mDuration = 300;
        setAttribute();
    }

    public SContextStepLevelMonitorAttribute(int i) {
        this.mDuration = i;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mDuration >= 0) {
            return true;
        }
        Log.e(TAG, "The duration is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("duration", this.mDuration);
        super.setAttribute(33, bundle);
    }
}
