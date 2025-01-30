package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.Log;

/* loaded from: classes5.dex */
public class SemContextDeviceActivityDetectorAttribute extends SemContextAttribute {
  public static final Parcelable.Creator<SemContextDeviceActivityDetectorAttribute> CREATOR =
      new Parcelable.Creator<
          SemContextDeviceActivityDetectorAttribute>() { // from class:
                                                         // com.samsung.android.hardware.context.SemContextDeviceActivityDetectorAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextDeviceActivityDetectorAttribute createFromParcel(Parcel in) {
          return new SemContextDeviceActivityDetectorAttribute(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextDeviceActivityDetectorAttribute[] newArray(int size) {
          return new SemContextDeviceActivityDetectorAttribute[size];
        }
      };
  private static final String TAG = "SemContextDeviceActivityDetectorAttribute";
  private int mActivity;
  private int mDuration;
  private boolean mNeedsRequestToUpdate;
  private int mPosture;

  SemContextDeviceActivityDetectorAttribute() {
    this.mActivity = 1;
    this.mDuration = 10;
    this.mPosture = 0;
    this.mNeedsRequestToUpdate = false;
    setAttribute();
  }

  SemContextDeviceActivityDetectorAttribute(Parcel src) {
    super(src);
    this.mActivity = 1;
    this.mDuration = 10;
    this.mPosture = 0;
    this.mNeedsRequestToUpdate = false;
  }

  public SemContextDeviceActivityDetectorAttribute(
      int activity, int duration, boolean needsRequestToUpdate) {
    this.mActivity = 1;
    this.mDuration = 10;
    this.mPosture = 0;
    this.mNeedsRequestToUpdate = false;
    this.mPosture = (65280 & activity) >> 8;
    this.mDuration = duration;
    this.mActivity = activity & 255;
    this.mNeedsRequestToUpdate = needsRequestToUpdate;
    Log.m94d(
        TAG,
        "SemContextDeviceActivityDetectorAttribute activity, posture : "
            + this.mActivity
            + " , "
            + this.mPosture);
    setAttribute();
  }

  @Override // com.samsung.android.hardware.context.SemContextAttribute
  public boolean checkAttribute() {
    int i = this.mActivity;
    if (i < 1 || i > 2) {
      Log.m96e(TAG, "SemContextDeviceActivityDetector activity is wrong.");
      return false;
    }
    if ((i == 1 && !this.mNeedsRequestToUpdate) || (i == 2 && this.mDuration > 0)) {
      Log.m96e(
          TAG,
          "This option is NOT supported, activity : "
              + this.mActivity
              + ", duration : "
              + this.mDuration
              + ", request : "
              + this.mNeedsRequestToUpdate);
      return false;
    }
    Log.m94d(TAG, "SemContextDeviceActivityDetector checkAttribute : " + this.mActivity);
    return this.mDuration >= 0;
  }

  private void setAttribute() {
    Bundle attribute = new Bundle();
    int i = this.mActivity;
    if (i == 2 && !this.mNeedsRequestToUpdate) {
      attribute.putInt("trigger_type", 3);
      attribute.putInt("duration", 0);
    } else if (i == 2 && this.mNeedsRequestToUpdate) {
      attribute.putInt("trigger_type", 2);
      attribute.putInt("duration", 0);
    } else if (i == 1 && this.mNeedsRequestToUpdate) {
      attribute.putInt("trigger_type", 1);
      attribute.putInt("duration", this.mDuration);
    } else {
      Log.m96e(TAG, "The attribute is wrong.");
      return;
    }
    attribute.putInt("posture", this.mPosture);
    super.setAttribute(54, attribute);
  }
}
