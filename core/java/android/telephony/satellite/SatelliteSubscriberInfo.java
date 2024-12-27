package android.telephony.satellite;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.telephony.SemTelephonyUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class SatelliteSubscriberInfo implements Parcelable {
    public static final Parcelable.Creator<SatelliteSubscriberInfo> CREATOR =
            new Parcelable.Creator<
                    SatelliteSubscriberInfo>() { // from class:
                                                 // android.telephony.satellite.SatelliteSubscriberInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SatelliteSubscriberInfo createFromParcel(Parcel in) {
                    return new SatelliteSubscriberInfo(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SatelliteSubscriberInfo[] newArray(int size) {
                    return new SatelliteSubscriberInfo[size];
                }
            };
    public static final int ICCID = 0;
    public static final int IMSI_MSISDN = 1;
    private int mCarrierId;
    private String mNiddApn;
    private int mSubId;
    private String mSubscriberId;
    private int mSubscriberIdType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SubscriberIdType {}

    private SatelliteSubscriberInfo(Parcel in) {
        readFromParcel(in);
    }

    public SatelliteSubscriberInfo(Builder builder) {
        this.mSubscriberId = builder.mSubscriberId;
        this.mCarrierId = builder.mCarrierId;
        this.mNiddApn = builder.mNiddApn;
        this.mSubId = builder.mSubId;
        this.mSubscriberIdType = builder.mSubscriberIdType;
    }

    public static class Builder {
        private int mCarrierId;
        private String mNiddApn;
        private int mSubId;
        private String mSubscriberId;
        private int mSubscriberIdType;

        public Builder setSubscriberId(String subscriberId) {
            this.mSubscriberId = subscriberId;
            return this;
        }

        public Builder setCarrierId(int carrierId) {
            this.mCarrierId = carrierId;
            return this;
        }

        public Builder setNiddApn(String niddApn) {
            this.mNiddApn = niddApn;
            return this;
        }

        public Builder setSubId(int subId) {
            this.mSubId = subId;
            return this;
        }

        public Builder setSubscriberIdType(int subscriberIdType) {
            this.mSubscriberIdType = subscriberIdType;
            return this;
        }

        public SatelliteSubscriberInfo build() {
            return new SatelliteSubscriberInfo(this);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mSubscriberId);
        out.writeInt(this.mCarrierId);
        out.writeString(this.mNiddApn);
        out.writeInt(this.mSubId);
        out.writeInt(this.mSubscriberIdType);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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

    public int getSubId() {
        return this.mSubId;
    }

    public int getSubscriberIdType() {
        return this.mSubscriberIdType;
    }

    public String toString() {
        return "SubscriberId:"
                + SemTelephonyUtils.maskPii(this.mSubscriberId)
                + ",CarrierId:"
                + this.mCarrierId
                + ",NiddApn:"
                + this.mNiddApn
                + ",SubId:"
                + this.mSubId
                + ",SubscriberIdType:"
                + this.mSubscriberIdType;
    }

    public int hashCode() {
        return Objects.hash(
                this.mSubscriberId,
                Integer.valueOf(this.mCarrierId),
                this.mNiddApn,
                Integer.valueOf(this.mSubId),
                Integer.valueOf(this.mSubscriberIdType));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SatelliteSubscriberInfo)) {
            return false;
        }
        SatelliteSubscriberInfo that = (SatelliteSubscriberInfo) o;
        return Objects.equals(this.mSubscriberId, that.mSubscriberId)
                && this.mCarrierId == that.mCarrierId
                && Objects.equals(this.mNiddApn, that.mNiddApn)
                && this.mSubId == that.mSubId
                && this.mSubscriberIdType == that.mSubscriberIdType;
    }

    private void readFromParcel(Parcel in) {
        this.mSubscriberId = in.readString();
        this.mCarrierId = in.readInt();
        this.mNiddApn = in.readString();
        this.mSubId = in.readInt();
        this.mSubscriberIdType = in.readInt();
    }
}
