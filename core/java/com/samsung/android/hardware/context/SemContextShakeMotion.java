package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextShakeMotion extends SemContextEventContext {
  public static final Parcelable.Creator<SemContextShakeMotion> CREATOR =
      new Parcelable.Creator<
          SemContextShakeMotion>() { // from class:
                                     // com.samsung.android.hardware.context.SemContextShakeMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextShakeMotion createFromParcel(Parcel in) {
          return new SemContextShakeMotion(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextShakeMotion[] newArray(int size) {
          return new SemContextShakeMotion[size];
        }
      };
  public static final int NONE = 0;
  public static final int START = 1;
  public static final int STOP = 2;
  private Bundle mContext;

  SemContextShakeMotion() {
    this.mContext = new Bundle();
  }

  SemContextShakeMotion(Parcel src) {
    readFromParcel(src);
  }

  public int getShakeStatus() {
    return this.mContext.getInt("Action");
  }

  @Override // com.samsung.android.hardware.context.SemContextEventContext
  public void setValues(Bundle context) {
    this.mContext = context;
  }

  @Override // com.samsung.android.hardware.context.SemContextEventContext,
            // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeBundle(this.mContext);
  }

  private void readFromParcel(Parcel src) {
    this.mContext = src.readBundle(getClass().getClassLoader());
  }
}
