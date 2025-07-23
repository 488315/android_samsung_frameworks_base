package android.telephony.satellite;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class PointingInfo implements Parcelable {
    public static final Parcelable.Creator<PointingInfo> CREATOR = new Parcelable.Creator<PointingInfo>() { // from class: android.telephony.satellite.PointingInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PointingInfo createFromParcel(Parcel parcel) {
            return new PointingInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PointingInfo[] newArray(int i) {
            return new PointingInfo[i];
        }
    };
    private float mSatelliteAzimuthDegrees;
    private float mSatelliteElevationDegrees;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PointingInfo(float f, float f2) {
        this.mSatelliteAzimuthDegrees = f;
        this.mSatelliteElevationDegrees = f2;
    }

    private PointingInfo(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.mSatelliteAzimuthDegrees);
        parcel.writeFloat(this.mSatelliteElevationDegrees);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PointingInfo pointingInfo = (PointingInfo) obj;
            if (this.mSatelliteAzimuthDegrees == pointingInfo.mSatelliteAzimuthDegrees && this.mSatelliteElevationDegrees == pointingInfo.mSatelliteElevationDegrees) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Float.valueOf(this.mSatelliteAzimuthDegrees), Float.valueOf(this.mSatelliteElevationDegrees));
    }

    public String toString() {
        return "SatelliteAzimuthDegrees:" + this.mSatelliteAzimuthDegrees + ",SatelliteElevationDegrees:" + this.mSatelliteElevationDegrees;
    }

    public float getSatelliteAzimuthDegrees() {
        return this.mSatelliteAzimuthDegrees;
    }

    public float getSatelliteElevationDegrees() {
        return this.mSatelliteElevationDegrees;
    }

    private void readFromParcel(Parcel parcel) {
        this.mSatelliteAzimuthDegrees = parcel.readFloat();
        this.mSatelliteElevationDegrees = parcel.readFloat();
    }
}
