package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextDualDisplayAngleAttribute extends SContextAttribute {
    private static final String TAG = "SContextDualDisplayAngleAttribute";
    private int mOffAngle;
    private int mOnAngle;

    SContextDualDisplayAngleAttribute() {
        this.mOnAngle = 210;
        this.mOffAngle = 240;
        setAttribute();
    }

    public SContextDualDisplayAngleAttribute(int i, int i2) {
        this.mOnAngle = i;
        this.mOffAngle = i2;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mOnAngle;
        if (i < 0 || i > 360) {
            Log.d(TAG, "Value of onAngle is wrong!!");
            return false;
        }
        int i2 = this.mOffAngle;
        if (i2 < 0 || i2 > 360) {
            Log.d(TAG, "Value of offAngle is wrong!!");
            return false;
        }
        if (i <= i2) {
            return true;
        }
        Log.d(TAG, "onAngle is above offAngle!!");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("onAngle", this.mOnAngle);
        bundle.putInt("offAngle", this.mOffAngle);
        Log.d(TAG, "onAngle : " + bundle.getInt("onAngle"));
        Log.d(TAG, "offAngle : " + bundle.getInt("offAngle"));
        super.setAttribute(45, bundle);
    }
}
