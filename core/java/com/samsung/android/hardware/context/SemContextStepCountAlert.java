package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextStepCountAlert extends SemContextEventContext {
  public static final Parcelable.Creator<SemContextStepCountAlert> CREATOR =
      new Parcelable.Creator<
          SemContextStepCountAlert>() { // from class:
                                        // com.samsung.android.hardware.context.SemContextStepCountAlert.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextStepCountAlert createFromParcel(Parcel in) {
          return new SemContextStepCountAlert(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextStepCountAlert[] newArray(int size) {
          return new SemContextStepCountAlert[size];
        }
      };
  public static final int EXPIRED = 1;
  public static final int UNKNOWN = 0;
  private Bundle mContext;

  SemContextStepCountAlert() {
    this.mContext = new Bundle();
  }

  SemContextStepCountAlert(Parcel src) {
    readFromParcel(src);
  }

  public int getAlert() {
    return this.mContext.getInt("Action") == 1 ? 1 : 0;
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
