package android.telephony.satellite;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class SatellitePosition implements Parcelable {
    public static final Parcelable.Creator<SatellitePosition> CREATOR = new Parcelable.Creator<SatellitePosition>() { // from class: android.telephony.satellite.SatellitePosition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatellitePosition createFromParcel(Parcel parcel) {
            return new SatellitePosition(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatellitePosition[] newArray(int i) {
            return new SatellitePosition[i];
        }
    };
    private double mAltitudeKm;
    private double mLongitudeDegree;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SatellitePosition(Parcel parcel) {
        this.mLongitudeDegree = parcel.readDouble();
        this.mAltitudeKm = parcel.readDouble();
    }

    public SatellitePosition(double d, double d2) {
        this.mLongitudeDegree = d;
        this.mAltitudeKm = d2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.mLongitudeDegree);
        parcel.writeDouble(this.mAltitudeKm);
    }

    public double getLongitudeDegrees() {
        return this.mLongitudeDegree;
    }

    public double getAltitudeKm() {
        return this.mAltitudeKm;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SatellitePosition) {
            SatellitePosition satellitePosition = (SatellitePosition) obj;
            if (Double.compare(satellitePosition.mLongitudeDegree, this.mLongitudeDegree) == 0 && Double.compare(satellitePosition.mAltitudeKm, this.mAltitudeKm) == 0) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Double.valueOf(this.mLongitudeDegree), Double.valueOf(this.mAltitudeKm));
    }

    public String toString() {
        return "mLongitudeDegree: " + this.mLongitudeDegree + ", mAltitudeKm: " + this.mAltitudeKm;
    }
}
