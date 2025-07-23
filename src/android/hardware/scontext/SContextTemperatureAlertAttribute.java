package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextTemperatureAlertAttribute extends SContextAttribute {
    private static final String TAG = "SContextTemperatureAlertAttribute";
    private int mHighTemperature;
    private boolean mIsIncluding;
    private int mLowTemperature;

    SContextTemperatureAlertAttribute() {
        this.mLowTemperature = 70;
        this.mHighTemperature = 127;
        this.mIsIncluding = true;
        setAttribute();
    }

    public SContextTemperatureAlertAttribute(int i, int i2, boolean z) {
        this.mLowTemperature = i;
        this.mHighTemperature = i2;
        this.mIsIncluding = z;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mLowTemperature;
        if (i < -128 || i > 127) {
            Log.e(TAG, "The low temperature is wrong.");
            return false;
        }
        int i2 = this.mHighTemperature;
        if (i2 < -128 || i2 > 127) {
            Log.e(TAG, "The high temperature is wrong.");
            return false;
        }
        if (i <= i2) {
            return true;
        }
        Log.e(TAG, "The low temperature must be less than the high temperature.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("low_temperature", this.mLowTemperature);
        bundle.putInt("high_temperature", this.mLowTemperature);
        bundle.putBoolean("including", this.mIsIncluding);
        super.setAttribute(23, bundle);
    }
}
