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
    int i = this.mStatus;
    if (i < 0 || i > 2) {
      Log.m96e(TAG, "Moving Status is wrong!!");
      return false;
    }
    int i2 = this.mData;
    if (i2 < 0 || i2 > 3) {
      Log.m96e(TAG, "Data of calibration is wrong!!");
      return false;
    }
    return true;
  }

  private void setAttribute() {
    Bundle attribute = new Bundle();
    byte[] acData = {(byte) this.mStatus, (byte) this.mData};
    attribute.putByteArray("activity_calibration", acData);
    Log.m94d(TAG, "Activity Status Data : " + ((int) acData[0]) + ((int) acData[1]));
    super.setAttribute(53, attribute);
  }
}
