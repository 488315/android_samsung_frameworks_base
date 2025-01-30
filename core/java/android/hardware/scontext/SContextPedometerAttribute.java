package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextPedometerAttribute extends SContextAttribute {
  private static final String TAG = "SContextPedometerAttribute";
  private int mExerciseMode;
  private int mGender;
  private double mHeight;
  private int mMode;
  private double mWeight;
  private static int MODE_USER_INFO = 0;
  private static int MODE_EXERCISE = 1;

  SContextPedometerAttribute() {
    this.mGender = 1;
    this.mHeight = 170.0d;
    this.mWeight = 60.0d;
    this.mExerciseMode = -1;
    this.mMode = -1;
    this.mMode = MODE_USER_INFO;
    setAttribute();
  }

  public SContextPedometerAttribute(int gender, double height, double weight) {
    this.mGender = 1;
    this.mHeight = 170.0d;
    this.mWeight = 60.0d;
    this.mExerciseMode = -1;
    this.mMode = -1;
    this.mMode = MODE_USER_INFO;
    this.mGender = gender;
    this.mHeight = height;
    this.mWeight = weight;
    setAttribute();
  }

  public SContextPedometerAttribute(int exerciseMode) {
    this.mGender = 1;
    this.mHeight = 170.0d;
    this.mWeight = 60.0d;
    this.mExerciseMode = -1;
    this.mMode = -1;
    this.mMode = MODE_EXERCISE;
    this.mExerciseMode = exerciseMode;
    setAttribute();
  }

  @Override // android.hardware.scontext.SContextAttribute,
            // com.samsung.android.hardware.context.SemContextAttribute
  public boolean checkAttribute() {
    int i = this.mGender;
    if (i < 1 || i > 2) {
      Log.m96e(TAG, "The gender is wrong.");
      return false;
    }
    if (this.mHeight <= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
      Log.m96e(TAG, "The height is wrong.");
      return false;
    }
    if (this.mWeight <= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
      Log.m96e(TAG, "The weight is wrong.");
      return false;
    }
    if (this.mExerciseMode >= -1) {
      return true;
    }
    Log.m96e(TAG, "The exercise mode is wrong.");
    return false;
  }

  private void setAttribute() {
    Bundle attribute = new Bundle();
    attribute.putInt("mode", this.mMode);
    if (this.mMode == MODE_USER_INFO) {
      attribute.putInt("gender", this.mGender);
      attribute.putDouble("height", this.mHeight);
      attribute.putDouble("weight", this.mWeight);
    } else {
      attribute.putInt("exercise_mode", this.mExerciseMode);
    }
    super.setAttribute(2, attribute);
  }
}
