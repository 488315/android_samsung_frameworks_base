package android.hardware.scontext;

import android.p009os.Bundle;
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

    public SContextTemperatureAlertAttribute(int lowTemperature, int highTemperature, boolean isIncluding) {
        this.mLowTemperature = 70;
        this.mHighTemperature = 127;
        this.mIsIncluding = true;
        this.mLowTemperature = lowTemperature;
        this.mHighTemperature = highTemperature;
        this.mIsIncluding = isIncluding;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mLowTemperature;
        if (i < -128 || i > 127) {
            Log.m96e(TAG, "The low temperature is wrong.");
            return false;
        }
        int i2 = this.mHighTemperature;
        if (i2 < -128 || i2 > 127) {
            Log.m96e(TAG, "The high temperature is wrong.");
            return false;
        }
        if (i > i2) {
            Log.m96e(TAG, "The low temperature must be less than the high temperature.");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("low_temperature", this.mLowTemperature);
        attribute.putInt("high_temperature", this.mLowTemperature);
        attribute.putBoolean("including", this.mIsIncluding);
        super.setAttribute(23, attribute);
    }
}
