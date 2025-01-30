package com.samsung.android.camera.iris;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public final class Iris implements Parcelable {
  public static final Parcelable.Creator<Iris> CREATOR =
      new Parcelable.Creator<Iris>() { // from class: com.samsung.android.camera.iris.Iris.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Iris createFromParcel(Parcel in) {
          return new Iris(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Iris[] newArray(int size) {
          return new Iris[size];
        }
      };
  private long mDeviceId;
  private int mGroupId;
  private int mIrisId;
  private CharSequence mName;

  public Iris(CharSequence name, int groupId, int irisId, long deviceId) {
    this.mName = name;
    this.mGroupId = groupId;
    this.mIrisId = irisId;
    this.mDeviceId = deviceId;
  }

  private Iris(Parcel in) {
    this.mName = in.readString();
    this.mGroupId = in.readInt();
    this.mIrisId = in.readInt();
    this.mDeviceId = in.readLong();
  }

  public CharSequence getName() {
    return this.mName;
  }

  public int getIrisId() {
    return this.mIrisId;
  }

  public int getGroupId() {
    return this.mGroupId;
  }

  public long getDeviceId() {
    return this.mDeviceId;
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel out, int flags) {
    out.writeString(this.mName.toString());
    out.writeInt(this.mGroupId);
    out.writeInt(this.mIrisId);
    out.writeLong(this.mDeviceId);
  }
}
