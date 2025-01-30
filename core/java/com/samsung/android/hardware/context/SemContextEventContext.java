package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextEventContext implements Parcelable {
  public static final Parcelable.Creator<SemContextEventContext> CREATOR =
      new Parcelable.Creator<
          SemContextEventContext>() { // from class:
                                      // com.samsung.android.hardware.context.SemContextEventContext.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextEventContext createFromParcel(Parcel in) {
          return new SemContextEventContext(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextEventContext[] newArray(int size) {
          return new SemContextEventContext[size];
        }
      };
  protected static final long serialVersionUID = 4514449696888150558L;

  public SemContextEventContext() {}

  public SemContextEventContext(Parcel src) {}

  public void setValues(Bundle context) {}

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {}
}
