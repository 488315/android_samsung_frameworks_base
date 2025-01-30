package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextAirMotion extends SemContextEventContext {
  public static final Parcelable.Creator<SemContextAirMotion> CREATOR =
      new Parcelable.Creator<
          SemContextAirMotion>() { // from class:
                                   // com.samsung.android.hardware.context.SemContextAirMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAirMotion createFromParcel(Parcel in) {
          return new SemContextAirMotion(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAirMotion[] newArray(int size) {
          return new SemContextAirMotion[size];
        }
      };
  public static final int DOWN = 3;
  public static final int LEFT = 2;
  public static final int RIGHT = 1;
  public static final int UNKNOWN = 0;

  /* renamed from: UP */
  public static final int f2999UP = 4;
  private Bundle mContext;

  SemContextAirMotion() {
    this.mContext = new Bundle();
  }

  SemContextAirMotion(Parcel src) {
    readFromParcel(src);
  }

  public int getDirection() {
    return this.mContext.getInt("Direction");
  }

  public int getAngle() {
    return this.mContext.getInt("Angle");
  }

  public int getSpeed() {
    return this.mContext.getInt("Speed");
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
