package android.telephony.satellite;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class AntennaDirection implements Parcelable {
    public static final Parcelable.Creator<AntennaDirection> CREATOR = new Parcelable.Creator<AntennaDirection>() { // from class: android.telephony.satellite.AntennaDirection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AntennaDirection createFromParcel(Parcel parcel) {
            return new AntennaDirection(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AntennaDirection[] newArray(int i) {
            return new AntennaDirection[i];
        }
    };
    private float mX;
    private float mY;
    private float mZ;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AntennaDirection(float f, float f2, float f3) {
        this.mX = f;
        this.mY = f2;
        this.mZ = f3;
    }

    private AntennaDirection(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.mX);
        parcel.writeFloat(this.mY);
        parcel.writeFloat(this.mZ);
    }

    public String toString() {
        return "X:" + this.mX + ",Y:" + this.mY + ",Z:" + this.mZ;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AntennaDirection antennaDirection = (AntennaDirection) obj;
            if (this.mX == antennaDirection.mX && this.mY == antennaDirection.mY && this.mZ == antennaDirection.mZ) {
                return true;
            }
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

    private void readFromParcel(Parcel parcel) {
        this.mX = parcel.readFloat();
        this.mY = parcel.readFloat();
        this.mZ = parcel.readFloat();
    }
}
