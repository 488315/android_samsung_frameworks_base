package android.telephony.satellite;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class AntennaDirection implements Parcelable {
  public static final Parcelable.Creator<AntennaDirection> CREATOR =
      new Parcelable.Creator<
          AntennaDirection>() { // from class: android.telephony.satellite.AntennaDirection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AntennaDirection createFromParcel(Parcel in) {
          return new AntennaDirection(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AntennaDirection[] newArray(int size) {
          return new AntennaDirection[size];
        }
      };

  /* renamed from: mX */
  private float f519mX;

  /* renamed from: mY */
  private float f520mY;

  /* renamed from: mZ */
  private float f521mZ;

  public AntennaDirection(float x, float y, float z) {
    this.f519mX = x;
    this.f520mY = y;
    this.f521mZ = z;
  }

  private AntennaDirection(Parcel in) {
    readFromParcel(in);
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel out, int flags) {
    out.writeFloat(this.f519mX);
    out.writeFloat(this.f520mY);
    out.writeFloat(this.f521mZ);
  }

  public String toString() {
    return "X:" + this.f519mX + ",Y:" + this.f520mY + ",Z:" + this.f521mZ;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AntennaDirection that = (AntennaDirection) o;
    if (this.f519mX == that.f519mX && this.f520mY == that.f520mY && this.f521mZ == that.f521mZ) {
      return true;
    }
    return false;
  }

  public int hashCode() {
    return Objects.hash(
        Float.valueOf(this.f519mX), Float.valueOf(this.f520mY), Float.valueOf(this.f521mZ));
  }

  public float getX() {
    return this.f519mX;
  }

  public float getY() {
    return this.f520mY;
  }

  public float getZ() {
    return this.f521mZ;
  }

  private void readFromParcel(Parcel in) {
    this.f519mX = in.readFloat();
    this.f520mY = in.readFloat();
    this.f521mZ = in.readFloat();
  }
}
