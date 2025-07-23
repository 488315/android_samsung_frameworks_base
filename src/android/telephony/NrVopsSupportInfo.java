package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class NrVopsSupportInfo extends VopsSupportInfo {
    public static final Parcelable.Creator<NrVopsSupportInfo> CREATOR = new Parcelable.Creator<NrVopsSupportInfo>() { // from class: android.telephony.NrVopsSupportInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NrVopsSupportInfo createFromParcel(Parcel parcel) {
            parcel.readInt();
            return new NrVopsSupportInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NrVopsSupportInfo[] newArray(int i) {
            return new NrVopsSupportInfo[i];
        }
    };
    public static final int NR_STATUS_EMC_5GCN_ONLY = 1;
    public static final int NR_STATUS_EMC_EUTRA_5GCN_ONLY = 2;
    public static final int NR_STATUS_EMC_NOT_SUPPORTED = 0;
    public static final int NR_STATUS_EMC_NR_EUTRA_5GCN = 3;
    public static final int NR_STATUS_EMF_5GCN_ONLY = 1;
    public static final int NR_STATUS_EMF_EUTRA_5GCN_ONLY = 2;
    public static final int NR_STATUS_EMF_NOT_SUPPORTED = 0;
    public static final int NR_STATUS_EMF_NR_EUTRA_5GCN = 3;
    public static final int NR_STATUS_VOPS_3GPP_SUPPORTED = 1;
    public static final int NR_STATUS_VOPS_NON_3GPP_SUPPORTED = 2;
    public static final int NR_STATUS_VOPS_NOT_SUPPORTED = 0;
    private final int mEmcSupport;
    private final int mEmfSupport;
    private final int mVopsSupport;

    @Retention(RetentionPolicy.SOURCE)
    public @interface NrEmcStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NrEmfStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NrVopsStatus {
    }

    @Override // android.telephony.VopsSupportInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NrVopsSupportInfo(int i, int i2, int i3) {
        this.mVopsSupport = i;
        this.mEmcSupport = i2;
        this.mEmfSupport = i3;
    }

    public int getVopsSupport() {
        return this.mVopsSupport;
    }

    public int getEmcSupport() {
        return this.mEmcSupport;
    }

    public int getEmfSupport() {
        return this.mEmfSupport;
    }

    @Override // android.telephony.VopsSupportInfo
    public boolean isVopsSupported() {
        return this.mVopsSupport != 0;
    }

    @Override // android.telephony.VopsSupportInfo
    public boolean isEmergencyServiceSupported() {
        return this.mEmcSupport != 0;
    }

    @Override // android.telephony.VopsSupportInfo
    public boolean isEmergencyServiceFallbackSupported() {
        return this.mEmfSupport != 0;
    }

    @Override // android.telephony.VopsSupportInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i, 6);
        parcel.writeInt(this.mVopsSupport);
        parcel.writeInt(this.mEmcSupport);
        parcel.writeInt(this.mEmfSupport);
    }

    @Override // android.telephony.VopsSupportInfo
    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof NrVopsSupportInfo)) {
            if (this == obj) {
                return true;
            }
            NrVopsSupportInfo nrVopsSupportInfo = (NrVopsSupportInfo) obj;
            if (this.mVopsSupport == nrVopsSupportInfo.mVopsSupport && this.mEmcSupport == nrVopsSupportInfo.mEmcSupport && this.mEmfSupport == nrVopsSupportInfo.mEmfSupport) {
                return true;
            }
        }
        return false;
    }

    @Override // android.telephony.VopsSupportInfo
    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mVopsSupport), Integer.valueOf(this.mEmcSupport), Integer.valueOf(this.mEmfSupport));
    }

    public String toString() {
        return "NrVopsSupportInfo :  mVopsSupport = " + this.mVopsSupport + " mEmcSupport = " + this.mEmcSupport + " mEmfSupport = " + this.mEmfSupport;
    }

    protected static NrVopsSupportInfo createFromParcelBody(Parcel parcel) {
        return new NrVopsSupportInfo(parcel);
    }

    private NrVopsSupportInfo(Parcel parcel) {
        this.mVopsSupport = parcel.readInt();
        this.mEmcSupport = parcel.readInt();
        this.mEmfSupport = parcel.readInt();
    }
}
