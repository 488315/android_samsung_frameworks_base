package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextShakeMotionAttribute extends SContextAttribute {
    private static final String TAG = "SContextShakeMotionAttribute";
    private int mDuration;
    private int mStrength;

    SContextShakeMotionAttribute() {
        this.mStrength = 2;
        this.mDuration = 800;
        setAttribute();
    }

    public SContextShakeMotionAttribute(int i, int i2) {
        this.mStrength = i;
        this.mDuration = i2;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mStrength < 0) {
            Log.e(TAG, "The strength is wrong.");
            return false;
        }
        if (this.mDuration >= 0) {
            return true;
        }
        Log.e(TAG, "The duration is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("strength", this.mStrength);
        bundle.putInt("duration", this.mDuration);
        super.setAttribute(12, bundle);
    }
}
