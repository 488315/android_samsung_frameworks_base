package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextWirelessChargingDetection extends SemContextEventContext {
  public static final Parcelable.Creator<SemContextWirelessChargingDetection> CREATOR =
      new Parcelable.Creator<
          SemContextWirelessChargingDetection>() { // from class:
                                                   // com.samsung.android.hardware.context.SemContextWirelessChargingDetection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextWirelessChargingDetection createFromParcel(Parcel in) {
          return new SemContextWirelessChargingDetection(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextWirelessChargingDetection[] newArray(int size) {
          return new SemContextWirelessChargingDetection[size];
        }
      };
  public static final int MOVE = 1;
  public static final int NOMOVE = 0;
  private Bundle mContext;

  SemContextWirelessChargingDetection() {
    this.mContext = new Bundle();
  }

  SemContextWirelessChargingDetection(Parcel src) {
    readFromParcel(src);
  }

  public int getAction() {
    return this.mContext.getInt("Status");
  }

  @Override // com.samsung.android.hardware.context.SemContextEventContext
  public void setValues(Bundle context) {
    this.mContext = context;
  }

  @Override // com.samsung.android.hardware.context.SemContextEventContext,
            // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeBundle(this.mContext);
  }

  private void readFromParcel(Parcel src) {
    this.mContext = src.readBundle(getClass().getClassLoader());
  }
}
