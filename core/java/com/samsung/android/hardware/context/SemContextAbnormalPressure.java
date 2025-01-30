package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextAbnormalPressure extends SemContextEventContext {
  public static final Parcelable.Creator<SemContextAbnormalPressure> CREATOR =
      new Parcelable.Creator<
          SemContextAbnormalPressure>() { // from class:
                                          // com.samsung.android.hardware.context.SemContextAbnormalPressure.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAbnormalPressure createFromParcel(Parcel in) {
          return new SemContextAbnormalPressure(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAbnormalPressure[] newArray(int size) {
          return new SemContextAbnormalPressure[size];
        }
      };
  private Bundle mContext;

  SemContextAbnormalPressure() {
    this.mContext = new Bundle();
  }

  SemContextAbnormalPressure(Parcel src) {
    readFromParcel(src);
  }

  public float getPressure() {
    return this.mContext.getFloat("barometer");
  }

  public float getAccX() {
    return this.mContext.getFloat("xaxis");
  }

  public float getAccY() {
    return this.mContext.getFloat("yaxis");
  }

  public float getAccZ() {
    return this.mContext.getFloat("zaxis");
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
