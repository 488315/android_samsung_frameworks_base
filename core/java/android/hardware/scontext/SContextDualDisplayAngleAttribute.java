package android.hardware.scontext;

import android.p009os.Bundle;
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

  public SContextDualDisplayAngleAttribute(int onAngle, int offAngle) {
    this.mOnAngle = 210;
    this.mOffAngle = 240;
    this.mOnAngle = onAngle;
    this.mOffAngle = offAngle;
    setAttribute();
  }

  @Override // android.hardware.scontext.SContextAttribute,
            // com.samsung.android.hardware.context.SemContextAttribute
  public boolean checkAttribute() {
    int i = this.mOnAngle;
    if (i < 0 || i > 360) {
      Log.m94d(TAG, "Value of onAngle is wrong!!");
      return false;
    }
    int i2 = this.mOffAngle;
    if (i2 < 0 || i2 > 360) {
      Log.m94d(TAG, "Value of offAngle is wrong!!");
      return false;
    }
    if (i > i2) {
      Log.m94d(TAG, "onAngle is above offAngle!!");
      return false;
    }
    return true;
  }

  private void setAttribute() {
    Bundle attribute = new Bundle();
    attribute.putInt("onAngle", this.mOnAngle);
    attribute.putInt("offAngle", this.mOffAngle);
    Log.m94d(TAG, "onAngle : " + attribute.getInt("onAngle"));
    Log.m94d(TAG, "offAngle : " + attribute.getInt("offAngle"));
    super.setAttribute(45, attribute);
  }
}
