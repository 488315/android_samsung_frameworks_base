package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes5.dex */
public class SemContextSpecificPoseAlertAttribute extends SemContextAttribute {
  public static final Parcelable.Creator<SemContextSpecificPoseAlertAttribute> CREATOR =
      new Parcelable.Creator<
          SemContextSpecificPoseAlertAttribute>() { // from class:
                                                    // com.samsung.android.hardware.context.SemContextSpecificPoseAlertAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSpecificPoseAlertAttribute createFromParcel(Parcel in) {
          return new SemContextSpecificPoseAlertAttribute(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSpecificPoseAlertAttribute[] newArray(int size) {
          return new SemContextSpecificPoseAlertAttribute[size];
        }
      };
  private static final String TAG = "SemContextSpecificPoseAlertAttribute";
  private int mMaximumAngle;
  private int mMinimumAngle;
  private int mMovingThrs;
  private int mRetentionTime;

  SemContextSpecificPoseAlertAttribute() {
    this.mRetentionTime = 1;
    this.mMinimumAngle = -90;
    this.mMaximumAngle = 90;
    this.mMovingThrs = 1;
    setAttribute();
  }

  SemContextSpecificPoseAlertAttribute(Parcel src) {
    super(src);
    this.mRetentionTime = 1;
    this.mMinimumAngle = -90;
    this.mMaximumAngle = 90;
    this.mMovingThrs = 1;
  }

  public SemContextSpecificPoseAlertAttribute(
      int retentionTime, int minimumAngle, int maximumAngle, int movingThrs) {
    this.mRetentionTime = 1;
    this.mMinimumAngle = -90;
    this.mMaximumAngle = 90;
    this.mMovingThrs = 1;
    this.mRetentionTime = retentionTime;
    this.mMinimumAngle = minimumAngle;
    this.mMaximumAngle = maximumAngle;
    this.mMovingThrs = movingThrs;
    setAttribute();
  }

  @Override // com.samsung.android.hardware.context.SemContextAttribute
  public boolean checkAttribute() {
    if (this.mRetentionTime < 0) {
      Log.m96e(TAG, "The retention time is wrong.");
      return false;
    }
    int i = this.mMinimumAngle;
    if (i < -90 || i > 90) {
      Log.m96e(TAG, "The minimum angle is wrong. The angle must be between -90 and 90.");
      return false;
    }
    int i2 = this.mMaximumAngle;
    if (i2 < -90 || i2 > 90) {
      Log.m96e(TAG, "The maximum angle is wrong. The angle must be between -90 and 90.");
      return false;
    }
    if (i > i2) {
      Log.m96e(TAG, "The minimum angle must be less than the maximum angle.");
      return false;
    }
    if (this.mMovingThrs < 0) {
      Log.m96e(TAG, "The moving threshold is wrong.");
      return false;
    }
    return true;
  }

  private void setAttribute() {
    Bundle attribute = new Bundle();
    attribute.putInt("retention_time", this.mRetentionTime);
    attribute.putInt("minimum_angle", this.mMinimumAngle);
    attribute.putInt("maximum_angle", this.mMaximumAngle);
    attribute.putInt("moving_thrs", this.mMovingThrs);
    super.setAttribute(28, attribute);
  }
}
