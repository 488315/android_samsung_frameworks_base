package com.samsung.android.smartface;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public final class FaceInfo implements Parcelable {
  public static final Parcelable.Creator<FaceInfo> CREATOR =
      new Parcelable.Creator<FaceInfo>() { // from class: com.samsung.android.smartface.FaceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceInfo createFromParcel(Parcel in) {
          return new FaceInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceInfo[] newArray(int size) {
          return new FaceInfo[size];
        }
      };
  public static final int NEED_TO_SLEEP = 0;
  public static final int NEED_TO_STAY = 1;
  public int needToRotate;
  public int needToStay;

  public FaceInfo() {}

  public FaceInfo(Parcel in) {
    readFromParcel(in);
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel out, int flags) {
    out.writeInt(this.needToRotate);
    out.writeInt(this.needToStay);
  }

  public void readFromParcel(Parcel in) {
    this.needToRotate = in.readInt();
    this.needToStay = in.readInt();
  }
}
