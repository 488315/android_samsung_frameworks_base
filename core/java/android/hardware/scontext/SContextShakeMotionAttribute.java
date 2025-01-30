package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextShakeMotionAttribute extends SContextAttribute {
  private static final String TAG = "SContextShakeMotionAttribute";
  private int mDuration;
  private int mStrength;

  SContextShakeMotionAttribute() {
    this.mStrength = 2;
    this.mDuration = 800;
    setAttribute();
  }

  public SContextShakeMotionAttribute(int strength, int duration) {
    this.mStrength = 2;
    this.mDuration = 800;
    this.mStrength = strength;
    this.mDuration = duration;
    setAttribute();
  }

  @Override // android.hardware.scontext.SContextAttribute,
            // com.samsung.android.hardware.context.SemContextAttribute
  public boolean checkAttribute() {
    if (this.mStrength < 0) {
      Log.m96e(TAG, "The strength is wrong.");
      return false;
    }
    if (this.mDuration < 0) {
      Log.m96e(TAG, "The duration is wrong.");
      return false;
    }
    return true;
  }

  private void setAttribute() {
    Bundle attribute = new Bundle();
    attribute.putInt("strength", this.mStrength);
    attribute.putInt("duration", this.mDuration);
    super.setAttribute(12, attribute);
  }
}
