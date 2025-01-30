package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextApproach extends SemContextEventContext {
  public static final Parcelable.Creator<SemContextApproach> CREATOR =
      new Parcelable.Creator<
          SemContextApproach>() { // from class:
                                  // com.samsung.android.hardware.context.SemContextApproach.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextApproach createFromParcel(Parcel in) {
          return new SemContextApproach(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextApproach[] newArray(int size) {
          return new SemContextApproach[size];
        }
      };
  public static final int FAR = 0;
  public static final int NEAR = 1;
  private Bundle mContext;

  SemContextApproach() {
    this.mContext = new Bundle();
  }

  SemContextApproach(Parcel src) {
    readFromParcel(src);
  }

  public int getApproach() {
    return this.mContext.getInt("Proximity");
  }

  public int getUserID() {
    return this.mContext.getInt("UserID");
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
