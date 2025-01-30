package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextActivityNotificationEx extends SemContextEventContext {
  public static final int ACCURACY_HIGH = 2;
  public static final int ACCURACY_LOW = 0;
  public static final int ACCURACY_MID = 1;
  public static final Parcelable.Creator<SemContextActivityNotificationEx> CREATOR =
      new Parcelable.Creator<
          SemContextActivityNotificationEx>() { // from class:
                                                // com.samsung.android.hardware.context.SemContextActivityNotificationEx.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityNotificationEx createFromParcel(Parcel in) {
          return new SemContextActivityNotificationEx(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextActivityNotificationEx[] newArray(int size) {
          return new SemContextActivityNotificationEx[size];
        }
      };
  public static final int STATUS_CYCLE = 5;
  public static final int STATUS_MOVEMENT = 30;
  public static final int STATUS_RUN = 3;
  public static final int STATUS_STATIONARY = 1;
  public static final int STATUS_UNKNOWN = 0;
  public static final int STATUS_VEHICLE = 4;
  public static final int STATUS_WALK = 2;
  private Bundle mContext;

  SemContextActivityNotificationEx() {
    this.mContext = new Bundle();
  }

  SemContextActivityNotificationEx(Parcel src) {
    readFromParcel(src);
  }

  public long getTimeStamp() {
    return this.mContext.getLong("TimeStamp");
  }

  public int getStatus() {
    return this.mContext.getInt("ActivityType");
  }

  public int getAccuracy() {
    return this.mContext.getInt("Accuracy");
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
