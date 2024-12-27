package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
public class SContextActivityLocationLoggingAttribute extends SContextAttribute {
    private static final String TAG = "SContextActivityLocationLoggingAttribute";
    private int mAreaRadius;
    private int mLppResolution;
    private int mStayingRadius;
    private int mStopPeriod;
    private int mWaitPeriod;

    SContextActivityLocationLoggingAttribute() {
        this.mStopPeriod = 60;
        this.mWaitPeriod = 120;
        this.mStayingRadius = 50;
        this.mAreaRadius = 150;
        this.mLppResolution = 0;
        setAttribute();
    }

    public SContextActivityLocationLoggingAttribute(
            int stopPeriod, int waitPeriod, int statyingRadius, int areaRadius, int lppResolution) {
        this.mStopPeriod = 60;
        this.mWaitPeriod = 120;
        this.mStayingRadius = 50;
        this.mAreaRadius = 150;
        this.mLppResolution = 0;
        this.mStopPeriod = stopPeriod;
        this.mWaitPeriod = waitPeriod;
        this.mStayingRadius = statyingRadius;
        this.mAreaRadius = areaRadius;
        this.mLppResolution = lppResolution;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute,
    // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mStopPeriod < 0) {
            Log.e(TAG, "The stop period is wrong.");
            return false;
        }
        if (this.mWaitPeriod < 0) {
            Log.e(TAG, "The wait period is wrong.");
            return false;
        }
        if (this.mStayingRadius < 0) {
            Log.e(TAG, "The staying radius is wrong.");
            return false;
        }
        if (this.mAreaRadius < 0) {
            Log.e(TAG, "The area radius is wrong.");
            return false;
        }
        if (this.mLppResolution < 0 || this.mLppResolution > 2) {
            Log.e(TAG, "The lpp resolution is wrong.");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("stop_period", this.mStopPeriod);
        attribute.putInt("wait_period", this.mWaitPeriod);
        attribute.putInt("staying_radius", this.mStayingRadius);
        attribute.putInt("area_radius", this.mAreaRadius);
        attribute.putInt("lpp_resolution", this.mLppResolution);
        super.setAttribute(24, attribute);
    }
}
