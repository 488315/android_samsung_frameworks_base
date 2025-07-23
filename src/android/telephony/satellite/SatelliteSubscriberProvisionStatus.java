package android.telephony.satellite;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.Rlog;
import com.android.internal.telephony.util.TelephonyUtils;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class SatelliteSubscriberProvisionStatus implements Parcelable {
    public static final Parcelable.Creator<SatelliteSubscriberProvisionStatus> CREATOR = new Parcelable.Creator<SatelliteSubscriberProvisionStatus>() { // from class: android.telephony.satellite.SatelliteSubscriberProvisionStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteSubscriberProvisionStatus createFromParcel(Parcel parcel) {
            return new SatelliteSubscriberProvisionStatus(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteSubscriberProvisionStatus[] newArray(int i) {
            return new SatelliteSubscriberProvisionStatus[i];
        }
    };
    private boolean mProvisioned;
    private SatelliteSubscriberInfo mSubscriberInfo;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SatelliteSubscriberProvisionStatus(Builder builder) {
        this.mSubscriberInfo = builder.mSubscriberInfo;
        this.mProvisioned = builder.mProvisioned;
    }

    public static final class Builder {
        private boolean mProvisioned;
        private SatelliteSubscriberInfo mSubscriberInfo;

        public Builder setSatelliteSubscriberInfo(SatelliteSubscriberInfo satelliteSubscriberInfo) {
            this.mSubscriberInfo = satelliteSubscriberInfo;
            return this;
        }

        public Builder setProvisioned(boolean z) {
            this.mProvisioned = z;
            return this;
        }

        public SatelliteSubscriberProvisionStatus build() {
            return new SatelliteSubscriberProvisionStatus(this);
        }
    }

    private SatelliteSubscriberProvisionStatus(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mSubscriberInfo, i);
        parcel.writeBoolean(this.mProvisioned);
    }

    public SatelliteSubscriberInfo getSatelliteSubscriberInfo() {
        return this.mSubscriberInfo;
    }

    public boolean isProvisioned() {
        return this.mProvisioned;
    }

    public String toString() {
        return "SatelliteSubscriberInfo:" + Rlog.pii(TelephonyUtils.IS_DEBUGGABLE, this.mSubscriberInfo) + ",ProvisionStatus:" + this.mProvisioned;
    }

    public int hashCode() {
        return Objects.hash(this.mSubscriberInfo, Boolean.valueOf(this.mProvisioned));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SatelliteSubscriberProvisionStatus)) {
            return false;
        }
        SatelliteSubscriberProvisionStatus satelliteSubscriberProvisionStatus = (SatelliteSubscriberProvisionStatus) obj;
        return Objects.equals(this.mSubscriberInfo, satelliteSubscriberProvisionStatus.mSubscriberInfo) && this.mProvisioned == satelliteSubscriberProvisionStatus.mProvisioned;
    }

    private void readFromParcel(Parcel parcel) {
        this.mSubscriberInfo = (SatelliteSubscriberInfo) parcel.readParcelable(SatelliteSubscriberInfo.class.getClassLoader(), SatelliteSubscriberInfo.class);
        this.mProvisioned = parcel.readBoolean();
    }
}
