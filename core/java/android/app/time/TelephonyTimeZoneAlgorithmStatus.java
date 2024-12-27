package android.app.time;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public final class TelephonyTimeZoneAlgorithmStatus implements Parcelable {
    public static final Parcelable.Creator<TelephonyTimeZoneAlgorithmStatus> CREATOR =
            new Parcelable.Creator<TelephonyTimeZoneAlgorithmStatus>() { // from class:
                // android.app.time.TelephonyTimeZoneAlgorithmStatus.1
                @Override // android.os.Parcelable.Creator
                public TelephonyTimeZoneAlgorithmStatus createFromParcel(Parcel in) {
                    int algorithmStatus = in.readInt();
                    return new TelephonyTimeZoneAlgorithmStatus(algorithmStatus);
                }

                @Override // android.os.Parcelable.Creator
                public TelephonyTimeZoneAlgorithmStatus[] newArray(int size) {
                    return new TelephonyTimeZoneAlgorithmStatus[size];
                }
            };
    private final int mAlgorithmStatus;

    public TelephonyTimeZoneAlgorithmStatus(int algorithmStatus) {
        this.mAlgorithmStatus =
                DetectorStatusTypes.requireValidDetectionAlgorithmStatus(algorithmStatus);
    }

    public int getAlgorithmStatus() {
        return this.mAlgorithmStatus;
    }

    public String toString() {
        return "TelephonyTimeZoneAlgorithmStatus{mAlgorithmStatus="
                + DetectorStatusTypes.detectionAlgorithmStatusToString(this.mAlgorithmStatus)
                + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.mAlgorithmStatus);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TelephonyTimeZoneAlgorithmStatus that = (TelephonyTimeZoneAlgorithmStatus) o;
        if (this.mAlgorithmStatus == that.mAlgorithmStatus) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mAlgorithmStatus));
    }
}
