package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextAnyMotionDetector extends SemContextEventContext {
  public static final Parcelable.Creator<SemContextAnyMotionDetector> CREATOR =
      new Parcelable.Creator<
          SemContextAnyMotionDetector>() { // from class:
                                           // com.samsung.android.hardware.context.SemContextAnyMotionDetector.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAnyMotionDetector createFromParcel(Parcel in) {
          return new SemContextAnyMotionDetector(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextAnyMotionDetector[] newArray(int size) {
          return new SemContextAnyMotionDetector[size];
        }
      };
  public static final int STATUS_ACTION = 1;
  public static final int STATUS_NONE = 0;
  private Bundle mContext;

  SemContextAnyMotionDetector() {
    this.mContext = new Bundle();
  }

  SemContextAnyMotionDetector(Parcel src) {
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
