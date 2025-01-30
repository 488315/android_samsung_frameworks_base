package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextApproachAttribute extends SemContextAttribute {
  public static final Parcelable.Creator<SemContextApproachAttribute> CREATOR =
      new Parcelable.Creator<
          SemContextApproachAttribute>() { // from class:
                                           // com.samsung.android.hardware.context.SemContextApproachAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextApproachAttribute createFromParcel(Parcel in) {
          return new SemContextApproachAttribute(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextApproachAttribute[] newArray(int size) {
          return new SemContextApproachAttribute[size];
        }
      };
  private int mUserID;

  SemContextApproachAttribute() {
    this.mUserID = -1;
    setAttribute();
  }

  SemContextApproachAttribute(Parcel src) {
    super(src);
    this.mUserID = -1;
  }

  public SemContextApproachAttribute(int userID) {
    this.mUserID = -1;
    this.mUserID = userID;
    setAttribute();
  }

  @Override // com.samsung.android.hardware.context.SemContextAttribute
  public boolean checkAttribute() {
    return true;
  }

  private void setAttribute() {
    Bundle attribute = new Bundle();
    attribute.putInt("UserID", this.mUserID);
    super.setAttribute(1, attribute);
  }
}
