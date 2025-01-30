package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextSpecificPoseAlert extends SemContextEventContext {
  public static final int ACTION = 1;
  public static final Parcelable.Creator<SemContextSpecificPoseAlert> CREATOR =
      new Parcelable.Creator<
          SemContextSpecificPoseAlert>() { // from class:
                                           // com.samsung.android.hardware.context.SemContextSpecificPoseAlert.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSpecificPoseAlert createFromParcel(Parcel in) {
          return new SemContextSpecificPoseAlert(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextSpecificPoseAlert[] newArray(int size) {
          return new SemContextSpecificPoseAlert[size];
        }
      };
  public static final int NONE = 0;
  private Bundle mContext;

  SemContextSpecificPoseAlert() {
    this.mContext = new Bundle();
  }

  SemContextSpecificPoseAlert(Parcel src) {
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
