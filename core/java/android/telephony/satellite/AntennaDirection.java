package android.telephony.satellite;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

@SystemApi
public final class AntennaDirection implements Parcelable {
    public static final Parcelable.Creator<AntennaDirection> CREATOR =
            new Parcelable.Creator<
                    AntennaDirection>() { // from class:
                                          // android.telephony.satellite.AntennaDirection.1
                @Override // android.os.Parcelable.Creator
                public AntennaDirection createFromParcel(Parcel in) {
                    return new AntennaDirection(in);
                }

                @Override // android.os.Parcelable.Creator
                public AntennaDirection[] newArray(int size) {
                    return new AntennaDirection[size];
                }
            };
    private float mX;
    private float mY;
    private float mZ;

    public AntennaDirection(float x, float y, float z) {
        this.mX = x;
        this.mY = y;
        this.mZ = z;
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
        out.writeFloat(this.mX);
        out.writeFloat(this.mY);
        out.writeFloat(this.mZ);
    }

    public String toString() {
        return "X:" + this.mX + ",Y:" + this.mY + ",Z:" + this.mZ;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AntennaDirection that = (AntennaDirection) o;
        if (this.mX == that.mX && this.mY == that.mY && this.mZ == that.mZ) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Float.valueOf(this.mX), Float.valueOf(this.mY), Float.valueOf(this.mZ));
    }

    public float getX() {
        return this.mX;
    }

    public float getY() {
        return this.mY;
    }

    public float getZ() {
        return this.mZ;
    }

    private void readFromParcel(Parcel in) {
        this.mX = in.readFloat();
        this.mY = in.readFloat();
        this.mZ = in.readFloat();
    }
}
