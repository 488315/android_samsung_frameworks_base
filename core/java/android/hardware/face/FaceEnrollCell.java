package android.hardware.face;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class FaceEnrollCell implements Parcelable {
  public static final Parcelable.Creator<FaceEnrollCell> CREATOR =
      new Parcelable.Creator<
          FaceEnrollCell>() { // from class: android.hardware.face.FaceEnrollCell.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceEnrollCell createFromParcel(Parcel source) {
          return new FaceEnrollCell(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceEnrollCell[] newArray(int size) {
          return new FaceEnrollCell[size];
        }
      };

  /* renamed from: mX */
  private final int f139mX;

  /* renamed from: mY */
  private final int f140mY;

  /* renamed from: mZ */
  private final int f141mZ;

  public FaceEnrollCell(int x, int y, int z) {
    this.f139mX = x;
    this.f140mY = y;
    this.f141mZ = z;
  }

  public int getX() {
    return this.f139mX;
  }

  public int getY() {
    return this.f140mY;
  }

  public int getZ() {
    return this.f141mZ;
  }

  private FaceEnrollCell(Parcel source) {
    this.f139mX = source.readInt();
    this.f140mY = source.readInt();
    this.f141mZ = source.readInt();
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.f139mX);
    dest.writeInt(this.f140mY);
    dest.writeInt(this.f141mZ);
  }
}
