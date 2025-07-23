package android.app.time;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class TimeZoneCapabilitiesAndConfig implements Parcelable {
    public static final Parcelable.Creator<TimeZoneCapabilitiesAndConfig> CREATOR = new Parcelable.Creator<TimeZoneCapabilitiesAndConfig>() { // from class: android.app.time.TimeZoneCapabilitiesAndConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeZoneCapabilitiesAndConfig createFromParcel(Parcel parcel) {
            return TimeZoneCapabilitiesAndConfig.createFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeZoneCapabilitiesAndConfig[] newArray(int i) {
            return new TimeZoneCapabilitiesAndConfig[i];
        }
    };
    private final TimeZoneCapabilities mCapabilities;
    private final TimeZoneConfiguration mConfiguration;
    private final TimeZoneDetectorStatus mDetectorStatus;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TimeZoneCapabilitiesAndConfig(TimeZoneDetectorStatus timeZoneDetectorStatus, TimeZoneCapabilities timeZoneCapabilities, TimeZoneConfiguration timeZoneConfiguration) {
        this.mDetectorStatus = (TimeZoneDetectorStatus) Objects.requireNonNull(timeZoneDetectorStatus);
        this.mCapabilities = (TimeZoneCapabilities) Objects.requireNonNull(timeZoneCapabilities);
        this.mConfiguration = (TimeZoneConfiguration) Objects.requireNonNull(timeZoneConfiguration);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TimeZoneCapabilitiesAndConfig createFromParcel(Parcel parcel) {
        return new TimeZoneCapabilitiesAndConfig((TimeZoneDetectorStatus) parcel.readParcelable(null, TimeZoneDetectorStatus.class), (TimeZoneCapabilities) parcel.readParcelable(null, TimeZoneCapabilities.class), (TimeZoneConfiguration) parcel.readParcelable(null, TimeZoneConfiguration.class));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mDetectorStatus, i);
        parcel.writeParcelable(this.mCapabilities, i);
        parcel.writeParcelable(this.mConfiguration, i);
    }

    public TimeZoneDetectorStatus getDetectorStatus() {
        return this.mDetectorStatus;
    }

    public TimeZoneCapabilities getCapabilities() {
        return this.mCapabilities;
    }

    public TimeZoneConfiguration getConfiguration() {
        return this.mConfiguration;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            TimeZoneCapabilitiesAndConfig timeZoneCapabilitiesAndConfig = (TimeZoneCapabilitiesAndConfig) obj;
            if (this.mDetectorStatus.equals(timeZoneCapabilitiesAndConfig.mDetectorStatus) && this.mCapabilities.equals(timeZoneCapabilitiesAndConfig.mCapabilities) && this.mConfiguration.equals(timeZoneCapabilitiesAndConfig.mConfiguration)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mCapabilities, this.mConfiguration);
    }

    public String toString() {
        return "TimeZoneCapabilitiesAndConfig{mDetectorStatus=" + this.mDetectorStatus + ", mCapabilities=" + this.mCapabilities + ", mConfiguration=" + this.mConfiguration + '}';
    }
}
