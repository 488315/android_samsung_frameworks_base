package android.telephony.satellite;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class SatelliteModemEnableRequestAttributes implements Parcelable {
    public static final Parcelable.Creator<SatelliteModemEnableRequestAttributes> CREATOR = new Parcelable.Creator<SatelliteModemEnableRequestAttributes>() { // from class: android.telephony.satellite.SatelliteModemEnableRequestAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteModemEnableRequestAttributes createFromParcel(Parcel parcel) {
            return new SatelliteModemEnableRequestAttributes(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteModemEnableRequestAttributes[] newArray(int i) {
            return new SatelliteModemEnableRequestAttributes[i];
        }
    };
    private final boolean mIsEnabled;
    private final boolean mIsForDemoMode;
    private final boolean mIsForEmergencyMode;
    private final SatelliteSubscriptionInfo mSatelliteSubscriptionInfo;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SatelliteModemEnableRequestAttributes(boolean z, boolean z2, boolean z3, SatelliteSubscriptionInfo satelliteSubscriptionInfo) {
        this.mIsEnabled = z;
        this.mIsForDemoMode = z2;
        this.mIsForEmergencyMode = z3;
        this.mSatelliteSubscriptionInfo = satelliteSubscriptionInfo;
    }

    private SatelliteModemEnableRequestAttributes(Parcel parcel) {
        this.mIsEnabled = parcel.readBoolean();
        this.mIsForDemoMode = parcel.readBoolean();
        this.mIsForEmergencyMode = parcel.readBoolean();
        this.mSatelliteSubscriptionInfo = (SatelliteSubscriptionInfo) parcel.readParcelable(SatelliteSubscriptionInfo.class.getClassLoader(), SatelliteSubscriptionInfo.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(this.mIsEnabled);
        parcel.writeBoolean(this.mIsForDemoMode);
        parcel.writeBoolean(this.mIsForEmergencyMode);
        this.mSatelliteSubscriptionInfo.writeToParcel(parcel, i);
    }

    public String toString() {
        return "SatelliteModemEnableRequestAttributes{, mIsEnabled=" + this.mIsEnabled + ", mIsForDemoMode=" + this.mIsForDemoMode + ", mIsForEmergencyMode=" + this.mIsForEmergencyMode + "mSatelliteSubscriptionInfo=" + this.mSatelliteSubscriptionInfo + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            SatelliteModemEnableRequestAttributes satelliteModemEnableRequestAttributes = (SatelliteModemEnableRequestAttributes) obj;
            if (this.mIsEnabled == satelliteModemEnableRequestAttributes.mIsEnabled && this.mIsForDemoMode == satelliteModemEnableRequestAttributes.mIsForDemoMode && this.mIsForEmergencyMode == satelliteModemEnableRequestAttributes.mIsForEmergencyMode && this.mSatelliteSubscriptionInfo.equals(satelliteModemEnableRequestAttributes.mSatelliteSubscriptionInfo)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mIsEnabled), Boolean.valueOf(this.mIsForDemoMode), Boolean.valueOf(this.mIsForEmergencyMode), this.mSatelliteSubscriptionInfo);
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public boolean isForDemoMode() {
        return this.mIsForDemoMode;
    }

    public boolean isForEmergencyMode() {
        return this.mIsForEmergencyMode;
    }

    public SatelliteSubscriptionInfo getSatelliteSubscriptionInfo() {
        return this.mSatelliteSubscriptionInfo;
    }
}
