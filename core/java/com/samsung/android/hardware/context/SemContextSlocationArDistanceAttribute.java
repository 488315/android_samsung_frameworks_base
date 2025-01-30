package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.Log;

/* loaded from: classes5.dex */
public class SemContextSlocationArDistanceAttribute extends SemContextAttribute {
  public static final Parcelable.Creator<SemContextSlocationArDistanceAttribute> CREATOR =
      new Parcelable.Creator<
          SemContextSlocationArDistanceAttribute>() { // from class:
                                                      // com.samsung.android.hardware.context.SemContextSlocationArDistanceAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSlocationArDistanceAttribute createFromParcel(Parcel in) {
          return new SemContextSlocationArDistanceAttribute(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSlocationArDistanceAttribute[] newArray(int size) {
          return new SemContextSlocationArDistanceAttribute[size];
        }
      };
  private static final String TAG = "SemContextSlocationArDistanceAttribute";
  private int[] mDistanceCmd;
  private int mMode;

  SemContextSlocationArDistanceAttribute() {
    this.mMode = -1;
    this.mDistanceCmd = null;
    setAttribute();
  }

  private SemContextSlocationArDistanceAttribute(Parcel src) {
    super(src);
    this.mMode = -1;
    this.mDistanceCmd = null;
  }

  public SemContextSlocationArDistanceAttribute(int mode, int[] data) {
    this.mMode = -1;
    this.mDistanceCmd = null;
    this.mMode = mode;
    int[] iArr = new int[data.length];
    this.mDistanceCmd = iArr;
    System.arraycopy(data, 0, iArr, 0, data.length);
    setAttribute();
  }

  @Override // com.samsung.android.hardware.context.SemContextAttribute
  public boolean checkAttribute() {
    int i = this.mMode;
    if (i >= -1 && i <= 1) {
      return true;
    }
    Log.m94d(TAG, "Mode value is wrong!!");
    return false;
  }

  private void setAttribute() {
    Bundle attribute = new Bundle();
    switch (this.mMode) {
      case 1:
        int[] iArr = this.mDistanceCmd;
        int[] intType = new int[iArr.length];
        System.arraycopy(iArr, 0, intType, 0, iArr.length);
        attribute.putIntArray("distance_cmd_array", intType);
        break;
    }
    attribute.putInt("mode", this.mMode);
    Log.m94d(TAG, "setAttribute() mode : " + attribute.getInt("mode"));
    super.setAttribute(56, attribute);
  }
}
