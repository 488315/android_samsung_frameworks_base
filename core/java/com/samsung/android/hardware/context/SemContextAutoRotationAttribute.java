package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.Log;

@Deprecated(forRemoval = true, since = "13.0")
/* loaded from: classes5.dex */
public class SemContextAutoRotationAttribute extends SemContextAttribute {
  public static final Parcelable.Creator<SemContextAutoRotationAttribute> CREATOR =
      new Parcelable.Creator<
          SemContextAutoRotationAttribute>() { // from class:
                                               // com.samsung.android.hardware.context.SemContextAutoRotationAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAutoRotationAttribute createFromParcel(Parcel in) {
          return new SemContextAutoRotationAttribute(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAutoRotationAttribute[] newArray(int size) {
          return new SemContextAutoRotationAttribute[size];
        }
      };
  private static final String TAG = "SemContextAutoRotationAttribute";
  private int mDeviceType;

  SemContextAutoRotationAttribute() {
    this.mDeviceType = 0;
    setAttribute();
  }

  SemContextAutoRotationAttribute(Parcel src) {
    super(src);
    this.mDeviceType = 0;
  }

  public SemContextAutoRotationAttribute(int deviceType) {
    this.mDeviceType = 0;
    this.mDeviceType = deviceType;
    setAttribute();
  }

  @Override // com.samsung.android.hardware.context.SemContextAttribute
  public boolean checkAttribute() {
    int i = this.mDeviceType;
    if (i != 0 && i != 2 && i != 4) {
      Log.m96e(TAG, "The device type is wrong.");
      return false;
    }
    return true;
  }

  private void setAttribute() {
    Bundle attribute = new Bundle();
    attribute.putInt("device_type", this.mDeviceType);
    super.setAttribute(6, attribute);
  }
}
