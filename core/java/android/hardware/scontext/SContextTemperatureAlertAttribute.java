package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
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

    public SContextTemperatureAlertAttribute(
            int lowTemperature, int highTemperature, boolean isIncluding) {
        this.mLowTemperature = 70;
        this.mHighTemperature = 127;
        this.mIsIncluding = true;
        this.mLowTemperature = lowTemperature;
        this.mHighTemperature = highTemperature;
        this.mIsIncluding = isIncluding;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute,
    // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mLowTemperature < -128 || this.mLowTemperature > 127) {
            Log.e(TAG, "The low temperature is wrong.");
            return false;
        }
        if (this.mHighTemperature < -128 || this.mHighTemperature > 127) {
            Log.e(TAG, "The high temperature is wrong.");
            return false;
        }
        if (this.mLowTemperature > this.mHighTemperature) {
            Log.e(TAG, "The low temperature must be less than the high temperature.");
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
