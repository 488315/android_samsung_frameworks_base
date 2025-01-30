package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextEnvironmentAdaptiveDisplayAttribute extends SContextAttribute {
  private static final String TAG = "SContextEnvironmentAdaptiveDisplayAttribute";
  private float mColorThreshold;
  private int mDuration;

  SContextEnvironmentAdaptiveDisplayAttribute() {
    this.mColorThreshold = 0.07f;
    this.mDuration = 35;
    setAttribute();
  }

  public SContextEnvironmentAdaptiveDisplayAttribute(float colorThreshold, int duration) {
    this.mColorThreshold = 0.07f;
    this.mDuration = 35;
    this.mColorThreshold = colorThreshold;
    this.mDuration = duration;
    setAttribute();
  }

  @Override // android.hardware.scontext.SContextAttribute,
            // com.samsung.android.hardware.context.SemContextAttribute
  public boolean checkAttribute() {
    if (this.mColorThreshold < 0.0f) {
      Log.m96e(TAG, "The color threshold value is wrong.");
      return false;
    }
    int i = this.mDuration;
    if (i < 0 || i > 255) {
      Log.m96e(TAG, "The duration value is wrong.");
      return false;
    }
    return true;
  }

  private void setAttribute() {
    Bundle attribute = new Bundle();
    attribute.putFloat("color_threshold", this.mColorThreshold);
    attribute.putInt("duration", this.mDuration);
    Log.m94d(TAG, "setAttribute() mColorThreshold : " + attribute.getFloat("color_threshold"));
    Log.m94d(TAG, "setAttribute() mDuration : " + attribute.getInt("duration"));
    super.setAttribute(44, attribute);
  }
}
