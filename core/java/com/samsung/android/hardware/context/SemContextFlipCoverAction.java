package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextFlipCoverAction extends SemContextEventContext {
  public static final int CLOSE = 1;
  public static final Parcelable.Creator<SemContextFlipCoverAction> CREATOR =
      new Parcelable.Creator<
          SemContextFlipCoverAction>() { // from class:
                                         // com.samsung.android.hardware.context.SemContextFlipCoverAction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextFlipCoverAction createFromParcel(Parcel in) {
          return new SemContextFlipCoverAction(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextFlipCoverAction[] newArray(int size) {
          return new SemContextFlipCoverAction[size];
        }
      };
  public static final int OPEN = 0;
  public static final int UNKNOWN = -1;
  private Bundle mContext;

  SemContextFlipCoverAction() {
    this.mContext = new Bundle();
  }

  SemContextFlipCoverAction(Parcel src) {
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
