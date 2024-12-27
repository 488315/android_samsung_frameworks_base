package android.telephony.satellite;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.SemTelephonyUtils;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class SatelliteSubscriptionInfo implements Parcelable {
    public static final Parcelable.Creator<SatelliteSubscriptionInfo> CREATOR = new Parcelable.Creator<SatelliteSubscriptionInfo>() { // from class: android.telephony.satellite.SatelliteSubscriptionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteSubscriptionInfo createFromParcel(Parcel in) {
            return new SatelliteSubscriptionInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteSubscriptionInfo[] newArray(int size) {
            return new SatelliteSubscriptionInfo[size];
        }
    };
    private final String mIccId;
    private final String mNiddApn;

    public SatelliteSubscriptionInfo(String iccId, String niddApn) {
        this.mIccId = iccId;
        this.mNiddApn = niddApn;
    }

    private SatelliteSubscriptionInfo(Parcel in) {
        this.mIccId = in.readString8();
        this.mNiddApn = in.readString8();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString8(this.mIccId);
        dest.writeString8(this.mNiddApn);
    }

    public String toString() {
        return "SatelliteSubscriptionInfo{IccId=" + SemTelephonyUtils.maskPii(this.mIccId) + ", NiddApn=" + this.mNiddApn + "}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SatelliteSubscriptionInfo that = (SatelliteSubscriptionInfo) o;
        if (this.mIccId.equals(that.getIccId()) && this.mNiddApn.equals(that.getNiddApn())) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(getIccId(), getNiddApn());
    }

    public String getIccId() {
        return this.mIccId;
    }

    public String getNiddApn() {
        return this.mNiddApn;
    }
}
