package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextActivityCalibrationAttribute extends SContextAttribute {
    private static final String TAG = "SContextActivityCalibrationAttribute";
    private int mData;
    private int mStatus;

    SContextActivityCalibrationAttribute() {
        this.mStatus = 0;
        this.mData = 0;
        setAttribute();
    }

    public SContextActivityCalibrationAttribute(int i, int i2) {
        this.mStatus = i;
        this.mData = i2;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mStatus;
        if (i < 0 || i > 2) {
            Log.e(TAG, "Moving Status is wrong!!");
            return false;
        }
        int i2 = this.mData;
        if (i2 >= 0 && i2 <= 3) {
            return true;
        }
        Log.e(TAG, "Data of calibration is wrong!!");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        byte[] bArr = {(byte) this.mStatus, (byte) this.mData};
        bundle.putByteArray("activity_calibration", bArr);
        Log.d(TAG, "Activity Status Data : " + ((int) bArr[0]) + ((int) bArr[1]));
        super.setAttribute(53, bundle);
    }
}
