package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextEnvironmentAttribute extends SContextAttribute {
    private static final String TAG = "SContextEnvironmentAttribute";
    private int mSensorType;
    private int mUpdateInterval;

    SContextEnvironmentAttribute() {
        this.mSensorType = 1;
        this.mUpdateInterval = 60;
        setAttribute();
    }

    public SContextEnvironmentAttribute(int i, int i2) {
        this.mSensorType = i;
        this.mUpdateInterval = i2;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mSensorType != 1) {
            Log.e(TAG, "The sensor type is wrong.");
            return false;
        }
        if (this.mUpdateInterval >= 0) {
            return true;
        }
        Log.e(TAG, "The update interval is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("sensor_type", this.mSensorType);
        bundle.putInt("update_interval", this.mUpdateInterval);
        super.setAttribute(8, bundle);
    }
}
