package android.telephony.satellite;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public class SatelliteSubscriberProvisionStatus implements Parcelable {
    public static final Parcelable.Creator<SatelliteSubscriberProvisionStatus> CREATOR = new Parcelable.Creator<SatelliteSubscriberProvisionStatus>() { // from class: android.telephony.satellite.SatelliteSubscriberProvisionStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteSubscriberProvisionStatus createFromParcel(Parcel in) {
            return new SatelliteSubscriberProvisionStatus(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteSubscriberProvisionStatus[] newArray(int size) {
            return new SatelliteSubscriberProvisionStatus[size];
        }
    };
    private boolean mProvisionStatus;
    private SatelliteSubscriberInfo mSubscriberInfo;

    public SatelliteSubscriberProvisionStatus(Builder builder) {
        this.mSubscriberInfo = builder.mSubscriberInfo;
        this.mProvisionStatus = builder.mProvisionStatus;
    }

    public static class Builder {
        private boolean mProvisionStatus;
        private SatelliteSubscriberInfo mSubscriberInfo;

        public Builder setSatelliteSubscriberInfo(SatelliteSubscriberInfo satelliteSubscriberInfo) {
            this.mSubscriberInfo = satelliteSubscriberInfo;
            return this;
        }

        public Builder setProvisionStatus(boolean provisionStatus) {
            this.mProvisionStatus = provisionStatus;
            return this;
        }

        public SatelliteSubscriberProvisionStatus build() {
            return new SatelliteSubscriberProvisionStatus(this);
        }
    }

    private SatelliteSubscriberProvisionStatus(Parcel in) {
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(this.mSubscriberInfo, flags);
        out.writeBoolean(this.mProvisionStatus);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SatelliteSubscriberInfo getSatelliteSubscriberInfo() {
        return this.mSubscriberInfo;
    }

    public boolean getProvisionStatus() {
        return this.mProvisionStatus;
    }

    public String toString() {
        return "SatelliteSubscriberInfo:" + this.mSubscriberInfo + ",ProvisionStatus:" + this.mProvisionStatus;
    }

    public int hashCode() {
        return Objects.hash(this.mSubscriberInfo, Boolean.valueOf(this.mProvisionStatus));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SatelliteSubscriberProvisionStatus)) {
            return false;
        }
        SatelliteSubscriberProvisionStatus that = (SatelliteSubscriberProvisionStatus) o;
        return Objects.equals(this.mSubscriberInfo, that.mSubscriberInfo) && this.mProvisionStatus == that.mProvisionStatus;
    }

    private void readFromParcel(Parcel in) {
        this.mSubscriberInfo = (SatelliteSubscriberInfo) in.readParcelable(SatelliteSubscriberInfo.class.getClassLoader(), SatelliteSubscriberInfo.class);
        this.mProvisionStatus = in.readBoolean();
    }
}
