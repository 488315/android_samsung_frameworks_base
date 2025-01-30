package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextFlatMotion extends SemContextEventContext {
  public static final Parcelable.Creator<SemContextFlatMotion> CREATOR =
      new Parcelable.Creator<
          SemContextFlatMotion>() { // from class:
                                    // com.samsung.android.hardware.context.SemContextFlatMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextFlatMotion createFromParcel(Parcel in) {
          return new SemContextFlatMotion(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextFlatMotion[] newArray(int size) {
          return new SemContextFlatMotion[size];
        }
      };
  public static final int FALSE = 2;
  public static final int TRUE = 1;
  public static final int UNKNOWN = 0;
  private Bundle mContext;

  SemContextFlatMotion() {
    this.mContext = new Bundle();
  }

  SemContextFlatMotion(Parcel src) {
    readFromParcel(src);
  }

  public int getAction() {
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
