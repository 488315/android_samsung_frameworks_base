package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
public class SContextActivityCalibrationAttribute extends SContextAttribute {
    private static final String TAG = "SContextActivityCalibrationAttribute";
    private int mData;
    private int mStatus;

    SContextActivityCalibrationAttribute() {
        this.mStatus = 0;
        this.mData = 0;
        setAttribute();
    }

    public SContextActivityCalibrationAttribute(int status, int data) {
        this.mStatus = 0;
        this.mData = 0;
        this.mStatus = status;
        this.mData = data;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute,
    // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mStatus < 0 || this.mStatus > 2) {
            Log.e(TAG, "Moving Status is wrong!!");
            return false;
        }
        if (this.mData < 0 || this.mData > 3) {
            Log.e(TAG, "Data of calibration is wrong!!");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        byte[] acData = {(byte) this.mStatus, (byte) this.mData};
        attribute.putByteArray("activity_calibration", acData);
        Log.d(TAG, "Activity Status Data : " + ((int) acData[0]) + ((int) acData[1]));
        super.setAttribute(53, attribute);
    }
}
