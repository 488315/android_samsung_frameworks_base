package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextMovement extends SemContextEventContext {
  public static final int ACTION = 1;
  public static final Parcelable.Creator<SemContextMovement> CREATOR =
      new Parcelable.Creator<
          SemContextMovement>() { // from class:
                                  // com.samsung.android.hardware.context.SemContextMovement.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextMovement createFromParcel(Parcel in) {
          return new SemContextMovement(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextMovement[] newArray(int size) {
          return new SemContextMovement[size];
        }
      };
  public static final int NONE = 0;
  private Bundle mContext;

  SemContextMovement() {
    this.mContext = new Bundle();
  }

  SemContextMovement(Parcel src) {
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
            // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeBundle(this.mContext);
  }

  private void readFromParcel(Parcel src) {
    this.mContext = src.readBundle(getClass().getClassLoader());
  }
}
