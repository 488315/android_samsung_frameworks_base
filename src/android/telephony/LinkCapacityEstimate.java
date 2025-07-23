package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class LinkCapacityEstimate implements Parcelable {
    public static final Parcelable.Creator<LinkCapacityEstimate> CREATOR = new Parcelable.Creator() { // from class: android.telephony.LinkCapacityEstimate.1
        @Override // android.os.Parcelable.Creator
        public LinkCapacityEstimate createFromParcel(Parcel parcel) {
            return new LinkCapacityEstimate(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public LinkCapacityEstimate[] newArray(int i) {
            return new LinkCapacityEstimate[i];
        }
    };
    public static final int INVALID = -1;
    public static final int LCE_TYPE_COMBINED = 2;
    public static final int LCE_TYPE_PRIMARY = 0;
    public static final int LCE_TYPE_SECONDARY = 1;
    private final int mDownlinkCapacityKbps;
    private final int mType;
    private final int mUplinkCapacityKbps;

    @Retention(RetentionPolicy.SOURCE)
    public @interface LceType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LinkCapacityEstimate(int i, int i2, int i3) {
        this.mDownlinkCapacityKbps = i2;
        this.mUplinkCapacityKbps = i3;
        this.mType = i;
    }

    public LinkCapacityEstimate(Parcel parcel) {
        this.mDownlinkCapacityKbps = parcel.readInt();
        this.mUplinkCapacityKbps = parcel.readInt();
        this.mType = parcel.readInt();
    }

    public int getType() {
        return this.mType;
    }

    public int getDownlinkCapacityKbps() {
        return this.mDownlinkCapacityKbps;
    }

    public int getUplinkCapacityKbps() {
        return this.mUplinkCapacityKbps;
    }

    public String toString() {
        return "{mType=" + this.mType + ", mDownlinkCapacityKbps=" + this.mDownlinkCapacityKbps + ", mUplinkCapacityKbps=" + this.mUplinkCapacityKbps + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mDownlinkCapacityKbps);
        parcel.writeInt(this.mUplinkCapacityKbps);
        parcel.writeInt(this.mType);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof LinkCapacityEstimate) && hashCode() == obj.hashCode()) {
            if (this == obj) {
                return true;
            }
            LinkCapacityEstimate linkCapacityEstimate = (LinkCapacityEstimate) obj;
            if (this.mDownlinkCapacityKbps == linkCapacityEstimate.mDownlinkCapacityKbps && this.mUplinkCapacityKbps == linkCapacityEstimate.mUplinkCapacityKbps && this.mType == linkCapacityEstimate.mType) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mDownlinkCapacityKbps), Integer.valueOf(this.mUplinkCapacityKbps), Integer.valueOf(this.mType));
    }
}
