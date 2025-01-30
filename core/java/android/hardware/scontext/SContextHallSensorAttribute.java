package android.hardware.scontext;

import android.p009os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextHallSensorAttribute extends SContextAttribute {
  private static final String TAG = "SContextHallSensorAttribute";
  private int mDisplayStatus;

  SContextHallSensorAttribute() {
    this.mDisplayStatus = 0;
    setAttribute();
  }

  public SContextHallSensorAttribute(int displayStatus) {
    this.mDisplayStatus = 0;
    this.mDisplayStatus = displayStatus;
    setAttribute();
    Log.m94d(TAG, "constructor + " + displayStatus);
  }

  public int getDisplayStatus() {
    return this.mDisplayStatus;
  }

  @Override // android.hardware.scontext.SContextAttribute,
            // com.samsung.android.hardware.context.SemContextAttribute
  public boolean checkAttribute() {
    int i = this.mDisplayStatus;
    if (i < 0 || i > 2) {
      Log.m96e(TAG, "The display status is wrong.");
      return false;
    }
    return true;
  }

  private void setAttribute() {
    Bundle attribute = new Bundle();
    attribute.putInt("display_status", this.mDisplayStatus);
    Log.m94d(TAG, "hall sensor status   + " + attribute.getInt("display_status"));
    super.setAttribute(43, attribute);
  }
}
