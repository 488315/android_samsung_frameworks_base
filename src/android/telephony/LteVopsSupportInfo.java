package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class LteVopsSupportInfo extends VopsSupportInfo {
    public static final Parcelable.Creator<LteVopsSupportInfo> CREATOR = new Parcelable.Creator<LteVopsSupportInfo>() { // from class: android.telephony.LteVopsSupportInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LteVopsSupportInfo createFromParcel(Parcel parcel) {
            parcel.readInt();
            return new LteVopsSupportInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LteVopsSupportInfo[] newArray(int i) {
            return new LteVopsSupportInfo[i];
        }
    };

    @Deprecated
    public static final int LTE_STATUS_NOT_AVAILABLE = 1;
    public static final int LTE_STATUS_NOT_SUPPORTED = 3;
    public static final int LTE_STATUS_SUPPORTED = 2;
    private final int mEmcBearerSupport;
    private final int mVopsSupport;

    @Retention(RetentionPolicy.SOURCE)
    public @interface LteVopsStatus {
    }

    @Override // android.telephony.VopsSupportInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.telephony.VopsSupportInfo
    public boolean isEmergencyServiceFallbackSupported() {
        return false;
    }

    public LteVopsSupportInfo(int i, int i2) {
        this.mVopsSupport = i;
        this.mEmcBearerSupport = i2;
    }

    public int getVopsSupport() {
        return this.mVopsSupport;
    }

    public int getEmcBearerSupport() {
        return this.mEmcBearerSupport;
    }

    @Override // android.telephony.VopsSupportInfo
    public boolean isVopsSupported() {
        return this.mVopsSupport == 2;
    }

    @Override // android.telephony.VopsSupportInfo
    public boolean isEmergencyServiceSupported() {
        return this.mEmcBearerSupport == 2;
    }

    @Override // android.telephony.VopsSupportInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i, 3);
        parcel.writeInt(this.mVopsSupport);
        parcel.writeInt(this.mEmcBearerSupport);
    }

    @Override // android.telephony.VopsSupportInfo
    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof LteVopsSupportInfo)) {
            if (this == obj) {
                return true;
            }
            LteVopsSupportInfo lteVopsSupportInfo = (LteVopsSupportInfo) obj;
            if (this.mVopsSupport == lteVopsSupportInfo.mVopsSupport && this.mEmcBearerSupport == lteVopsSupportInfo.mEmcBearerSupport) {
                return true;
            }
        }
        return false;
    }

    @Override // android.telephony.VopsSupportInfo
    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mVopsSupport), Integer.valueOf(this.mEmcBearerSupport));
    }

    public String toString() {
        return "LteVopsSupportInfo :  mVopsSupport = " + this.mVopsSupport + " mEmcBearerSupport = " + this.mEmcBearerSupport;
    }

    protected static LteVopsSupportInfo createFromParcelBody(Parcel parcel) {
        return new LteVopsSupportInfo(parcel);
    }

    private LteVopsSupportInfo(Parcel parcel) {
        this.mVopsSupport = parcel.readInt();
        this.mEmcBearerSupport = parcel.readInt();
    }
}
