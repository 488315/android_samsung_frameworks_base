package android.telephony.satellite;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.Rlog;
import com.android.internal.telephony.util.TelephonyUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class SatelliteSubscriberInfo implements Parcelable {
    public static final Parcelable.Creator<SatelliteSubscriberInfo> CREATOR = new Parcelable.Creator<SatelliteSubscriberInfo>() { // from class: android.telephony.satellite.SatelliteSubscriberInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteSubscriberInfo createFromParcel(Parcel parcel) {
            return new SatelliteSubscriberInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteSubscriberInfo[] newArray(int i) {
            return new SatelliteSubscriberInfo[i];
        }
    };
    public static final int SUBSCRIBER_ID_TYPE_ICCID = 0;
    public static final int SUBSCRIBER_ID_TYPE_IMSI_MSISDN = 1;
    private int mCarrierId;
    private String mNiddApn;
    private String mSubscriberId;
    private int mSubscriberIdType;
    private int mSubscriptionId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SubscriberIdType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SatelliteSubscriberInfo(Parcel parcel) {
        readFromParcel(parcel);
    }

    public SatelliteSubscriberInfo(Builder builder) {
        this.mSubscriberId = builder.mSubscriberId;
        this.mCarrierId = builder.mCarrierId;
        this.mNiddApn = builder.mNiddApn;
        this.mSubscriptionId = builder.mSubscriptionId;
        this.mSubscriberIdType = builder.mSubscriberIdType;
    }

    public static final class Builder {
        private int mCarrierId;
        private String mNiddApn;
        private String mSubscriberId;
        private int mSubscriberIdType;
        private int mSubscriptionId;

        public Builder setSubscriberId(String str) {
            this.mSubscriberId = str;
            return this;
        }

        public Builder setCarrierId(int i) {
            this.mCarrierId = i;
            return this;
        }

        public Builder setNiddApn(String str) {
            this.mNiddApn = str;
            return this;
        }

        public Builder setSubscriptionId(int i) {
            this.mSubscriptionId = i;
            return this;
        }

        public Builder setSubscriberIdType(int i) {
            this.mSubscriberIdType = i;
            return this;
        }

        public SatelliteSubscriberInfo build() {
            return new SatelliteSubscriberInfo(this);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mSubscriberId);
        parcel.writeInt(this.mCarrierId);
        parcel.writeString(this.mNiddApn);
        parcel.writeInt(this.mSubscriptionId);
        parcel.writeInt(this.mSubscriberIdType);
    }

    public String getSubscriberId() {
        return this.mSubscriberId;
    }

    public int getCarrierId() {
        return this.mCarrierId;
    }

    public String getNiddApn() {
        return this.mNiddApn;
    }

    public int getSubscriptionId() {
        return this.mSubscriptionId;
    }

    public int getSubscriberIdType() {
        return this.mSubscriberIdType;
    }

    public String toString() {
        return "SubscriberId:" + Rlog.pii(TelephonyUtils.IS_DEBUGGABLE, this.mSubscriberId) + ",CarrierId:" + this.mCarrierId + ",NiddApn:" + this.mNiddApn + ",SubscriptionId:" + this.mSubscriptionId + ",SubscriberIdType:" + this.mSubscriberIdType;
    }

    public int hashCode() {
        return Objects.hash(this.mSubscriberId, Integer.valueOf(this.mCarrierId), this.mNiddApn, Integer.valueOf(this.mSubscriptionId), Integer.valueOf(this.mSubscriberIdType));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SatelliteSubscriberInfo)) {
            return false;
        }
        SatelliteSubscriberInfo satelliteSubscriberInfo = (SatelliteSubscriberInfo) obj;
        return Objects.equals(this.mSubscriberId, satelliteSubscriberInfo.mSubscriberId) && this.mCarrierId == satelliteSubscriberInfo.mCarrierId && Objects.equals(this.mNiddApn, satelliteSubscriberInfo.mNiddApn) && this.mSubscriptionId == satelliteSubscriberInfo.mSubscriptionId && this.mSubscriberIdType == satelliteSubscriberInfo.mSubscriberIdType;
    }

    private void readFromParcel(Parcel parcel) {
        this.mSubscriberId = parcel.readString();
        this.mCarrierId = parcel.readInt();
        this.mNiddApn = parcel.readString();
        this.mSubscriptionId = parcel.readInt();
        this.mSubscriberIdType = parcel.readInt();
    }
}
