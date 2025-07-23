package android.app.time;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class TimeZoneDetectorStatus implements Parcelable {
    public static final Parcelable.Creator<TimeZoneDetectorStatus> CREATOR = new Parcelable.Creator<TimeZoneDetectorStatus>() { // from class: android.app.time.TimeZoneDetectorStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeZoneDetectorStatus createFromParcel(Parcel parcel) {
            return new TimeZoneDetectorStatus(parcel.readInt(), (TelephonyTimeZoneAlgorithmStatus) parcel.readParcelable(getClass().getClassLoader(), TelephonyTimeZoneAlgorithmStatus.class), (LocationTimeZoneAlgorithmStatus) parcel.readParcelable(getClass().getClassLoader(), LocationTimeZoneAlgorithmStatus.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeZoneDetectorStatus[] newArray(int i) {
            return new TimeZoneDetectorStatus[i];
        }
    };
    private final int mDetectorStatus;
    private final LocationTimeZoneAlgorithmStatus mLocationTimeZoneAlgorithmStatus;
    private final TelephonyTimeZoneAlgorithmStatus mTelephonyTimeZoneAlgorithmStatus;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TimeZoneDetectorStatus(int i, TelephonyTimeZoneAlgorithmStatus telephonyTimeZoneAlgorithmStatus, LocationTimeZoneAlgorithmStatus locationTimeZoneAlgorithmStatus) {
        this.mDetectorStatus = DetectorStatusTypes.requireValidDetectorStatus(i);
        this.mTelephonyTimeZoneAlgorithmStatus = (TelephonyTimeZoneAlgorithmStatus) Objects.requireNonNull(telephonyTimeZoneAlgorithmStatus);
        this.mLocationTimeZoneAlgorithmStatus = (LocationTimeZoneAlgorithmStatus) Objects.requireNonNull(locationTimeZoneAlgorithmStatus);
    }

    public int getDetectorStatus() {
        return this.mDetectorStatus;
    }

    public TelephonyTimeZoneAlgorithmStatus getTelephonyTimeZoneAlgorithmStatus() {
        return this.mTelephonyTimeZoneAlgorithmStatus;
    }

    public LocationTimeZoneAlgorithmStatus getLocationTimeZoneAlgorithmStatus() {
        return this.mLocationTimeZoneAlgorithmStatus;
    }

    public String toString() {
        return "TimeZoneDetectorStatus{mDetectorStatus=" + DetectorStatusTypes.detectorStatusToString(this.mDetectorStatus) + ", mTelephonyTimeZoneAlgorithmStatus=" + this.mTelephonyTimeZoneAlgorithmStatus + ", mLocationTimeZoneAlgorithmStatus=" + this.mLocationTimeZoneAlgorithmStatus + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mDetectorStatus);
        parcel.writeParcelable(this.mTelephonyTimeZoneAlgorithmStatus, i);
        parcel.writeParcelable(this.mLocationTimeZoneAlgorithmStatus, i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            TimeZoneDetectorStatus timeZoneDetectorStatus = (TimeZoneDetectorStatus) obj;
            if (this.mDetectorStatus == timeZoneDetectorStatus.mDetectorStatus && this.mTelephonyTimeZoneAlgorithmStatus.equals(timeZoneDetectorStatus.mTelephonyTimeZoneAlgorithmStatus) && this.mLocationTimeZoneAlgorithmStatus.equals(timeZoneDetectorStatus.mLocationTimeZoneAlgorithmStatus)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mDetectorStatus), this.mTelephonyTimeZoneAlgorithmStatus, this.mLocationTimeZoneAlgorithmStatus);
    }
}
