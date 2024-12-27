package android.telephony.satellite;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

@SystemApi
public final class PointingInfo implements Parcelable {
    public static final Parcelable.Creator<PointingInfo> CREATOR =
            new Parcelable.Creator<
                    PointingInfo>() { // from class: android.telephony.satellite.PointingInfo.1
                @Override // android.os.Parcelable.Creator
                public PointingInfo createFromParcel(Parcel in) {
                    return new PointingInfo(in);
                }

                @Override // android.os.Parcelable.Creator
                public PointingInfo[] newArray(int size) {
                    return new PointingInfo[size];
                }
            };
    private float mSatelliteAzimuthDegrees;
    private float mSatelliteElevationDegrees;

    public PointingInfo(float satelliteAzimuthDegrees, float satelliteElevationDegrees) {
        this.mSatelliteAzimuthDegrees = satelliteAzimuthDegrees;
        this.mSatelliteElevationDegrees = satelliteElevationDegrees;
    }

    private PointingInfo(Parcel in) {
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloat(this.mSatelliteAzimuthDegrees);
        out.writeFloat(this.mSatelliteElevationDegrees);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PointingInfo that = (PointingInfo) o;
        if (this.mSatelliteAzimuthDegrees == that.mSatelliteAzimuthDegrees
                && this.mSatelliteElevationDegrees == that.mSatelliteElevationDegrees) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(
                Float.valueOf(this.mSatelliteAzimuthDegrees),
                Float.valueOf(this.mSatelliteElevationDegrees));
    }

    public String toString() {
        return "SatelliteAzimuthDegrees:"
                + this.mSatelliteAzimuthDegrees
                + ",SatelliteElevationDegrees:"
                + this.mSatelliteElevationDegrees;
    }

    public float getSatelliteAzimuthDegrees() {
        return this.mSatelliteAzimuthDegrees;
    }

    public float getSatelliteElevationDegrees() {
        return this.mSatelliteElevationDegrees;
    }

    private void readFromParcel(Parcel in) {
        this.mSatelliteAzimuthDegrees = in.readFloat();
        this.mSatelliteElevationDegrees = in.readFloat();
    }
}
