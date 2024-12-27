package android.telephony.satellite;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public final class SatelliteModemEnableRequestAttributes implements Parcelable {
    public static final Parcelable.Creator<SatelliteModemEnableRequestAttributes> CREATOR =
            new Parcelable.Creator<
                    SatelliteModemEnableRequestAttributes>() { // from class:
                                                               // android.telephony.satellite.SatelliteModemEnableRequestAttributes.1
                @Override // android.os.Parcelable.Creator
                public SatelliteModemEnableRequestAttributes createFromParcel(Parcel in) {
                    return new SatelliteModemEnableRequestAttributes(in);
                }

                @Override // android.os.Parcelable.Creator
                public SatelliteModemEnableRequestAttributes[] newArray(int size) {
                    return new SatelliteModemEnableRequestAttributes[size];
                }
            };
    private final boolean mIsDemoMode;
    private final boolean mIsEmergencyMode;
    private final boolean mIsEnabled;
    private final SatelliteSubscriptionInfo mSatelliteSubscriptionInfo;

    public SatelliteModemEnableRequestAttributes(
            boolean isEnabled,
            boolean isDemoMode,
            boolean isEmergencyMode,
            SatelliteSubscriptionInfo satelliteSubscriptionInfo) {
        this.mIsEnabled = isEnabled;
        this.mIsDemoMode = isDemoMode;
        this.mIsEmergencyMode = isEmergencyMode;
        this.mSatelliteSubscriptionInfo = satelliteSubscriptionInfo;
    }

    private SatelliteModemEnableRequestAttributes(Parcel in) {
        this.mIsEnabled = in.readBoolean();
        this.mIsDemoMode = in.readBoolean();
        this.mIsEmergencyMode = in.readBoolean();
        this.mSatelliteSubscriptionInfo =
                (SatelliteSubscriptionInfo)
                        in.readParcelable(
                                SatelliteSubscriptionInfo.class.getClassLoader(),
                                SatelliteSubscriptionInfo.class);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBoolean(this.mIsEnabled);
        dest.writeBoolean(this.mIsDemoMode);
        dest.writeBoolean(this.mIsEmergencyMode);
        this.mSatelliteSubscriptionInfo.writeToParcel(dest, flags);
    }

    public String toString() {
        return "SatelliteModemEnableRequestAttributes{, mIsEnabled="
                + this.mIsEnabled
                + ", mIsDemoMode="
                + this.mIsDemoMode
                + ", mIsEmergencyMode="
                + this.mIsEmergencyMode
                + "mSatelliteSubscriptionInfo="
                + this.mSatelliteSubscriptionInfo
                + "}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SatelliteModemEnableRequestAttributes that = (SatelliteModemEnableRequestAttributes) o;
        if (this.mIsEnabled == that.mIsEnabled
                && this.mIsDemoMode == that.mIsDemoMode
                && this.mIsEmergencyMode == that.mIsEmergencyMode
                && this.mSatelliteSubscriptionInfo.equals(that.mSatelliteSubscriptionInfo)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(
                Boolean.valueOf(this.mIsEnabled),
                Boolean.valueOf(this.mIsDemoMode),
                Boolean.valueOf(this.mIsEmergencyMode),
                this.mSatelliteSubscriptionInfo);
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public boolean isDemoMode() {
        return this.mIsDemoMode;
    }

    public boolean isEmergencyMode() {
        return this.mIsEmergencyMode;
    }

    public SatelliteSubscriptionInfo getSatelliteSubscriptionInfo() {
        return this.mSatelliteSubscriptionInfo;
    }
}
