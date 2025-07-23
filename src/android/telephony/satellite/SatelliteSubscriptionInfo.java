package android.telephony.satellite;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.SemTelephonyUtils;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class SatelliteSubscriptionInfo implements Parcelable {
    public static final Parcelable.Creator<SatelliteSubscriptionInfo> CREATOR = new Parcelable.Creator<SatelliteSubscriptionInfo>() { // from class: android.telephony.satellite.SatelliteSubscriptionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteSubscriptionInfo createFromParcel(Parcel parcel) {
            return new SatelliteSubscriptionInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SatelliteSubscriptionInfo[] newArray(int i) {
            return new SatelliteSubscriptionInfo[i];
        }
    };
    private final String mIccId;
    private final String mNiddApn;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SatelliteSubscriptionInfo(String str, String str2) {
        this.mIccId = str;
        this.mNiddApn = str2;
    }

    private SatelliteSubscriptionInfo(Parcel parcel) {
        this.mIccId = parcel.readString8();
        this.mNiddApn = parcel.readString8();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mIccId);
        parcel.writeString8(this.mNiddApn);
    }

    public String toString() {
        return "SatelliteSubscriptionInfo{IccId=" + SemTelephonyUtils.maskPii(this.mIccId) + ", NiddApn=" + this.mNiddApn + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            SatelliteSubscriptionInfo satelliteSubscriptionInfo = (SatelliteSubscriptionInfo) obj;
            if (this.mIccId.equals(satelliteSubscriptionInfo.getIccId()) && this.mNiddApn.equals(satelliteSubscriptionInfo.getNiddApn())) {
                return true;
            }
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
