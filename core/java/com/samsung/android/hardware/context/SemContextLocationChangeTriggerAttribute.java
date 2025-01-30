package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.Log;

/* loaded from: classes5.dex */
public class SemContextLocationChangeTriggerAttribute extends SemContextAttribute {
  public static final Parcelable.Creator<SemContextLocationChangeTriggerAttribute> CREATOR =
      new Parcelable.Creator<
          SemContextLocationChangeTriggerAttribute>() { // from class:
                                                        // com.samsung.android.hardware.context.SemContextLocationChangeTriggerAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextLocationChangeTriggerAttribute createFromParcel(Parcel in) {
          return new SemContextLocationChangeTriggerAttribute(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextLocationChangeTriggerAttribute[] newArray(int size) {
          return new SemContextLocationChangeTriggerAttribute[size];
        }
      };
  private static final String TAG = "SemContextLocationChangeTriggerAttribute";
  private int mDuration;
  private int mTriggerType;

  SemContextLocationChangeTriggerAttribute() {
    this.mTriggerType = 1;
    this.mDuration = 10;
    setAttribute();
  }

  SemContextLocationChangeTriggerAttribute(Parcel src) {
    super(src);
    this.mTriggerType = 1;
    this.mDuration = 10;
  }

  public SemContextLocationChangeTriggerAttribute(int type, int duration) {
    this.mTriggerType = 1;
    this.mDuration = 10;
    this.mTriggerType = type;
    this.mDuration = duration;
    setAttribute();
  }

  @Override // com.samsung.android.hardware.context.SemContextAttribute
  public boolean checkAttribute() {
    int i = this.mTriggerType;
    if (i >= 1 && i <= 3) {
      return this.mDuration >= 0;
    }
    Log.m96e(TAG, "The display status is wrong.");
    return false;
  }

  private void setAttribute() {
    Bundle attribute = new Bundle();
    attribute.putInt("trigger_type", this.mTriggerType);
    attribute.putInt("duration", this.mDuration);
    super.setAttribute(54, attribute);
  }
}
