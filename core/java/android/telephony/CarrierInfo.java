package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class CarrierInfo implements Parcelable {
    public static final Parcelable.Creator<CarrierInfo> CREATOR = new Parcelable.Creator<CarrierInfo>() { // from class: android.telephony.CarrierInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CarrierInfo createFromParcel(Parcel source) {
            return new CarrierInfo(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CarrierInfo[] newArray(int size) {
            return new CarrierInfo[size];
        }
    };
    private List<String> mEhplmn;
    private String mGid1;
    private String mGid2;
    private String mIccid;
    private String mImpi;
    private String mImsiPrefix;
    private String mMcc;
    private String mMnc;
    private String mSpn;

    public String getMcc() {
        return this.mMcc;
    }

    public String getMnc() {
        return this.mMnc;
    }

    public String getSpn() {
        return this.mSpn;
    }

    public String getGid1() {
        return this.mGid1;
    }

    public String getGid2() {
        return this.mGid2;
    }

    public String getImsiPrefix() {
        return this.mImsiPrefix;
    }

    public String getIccid() {
        return this.mIccid;
    }

    public String getImpi() {
        return this.mImpi;
    }

    public List<String> getEhplmn() {
        return this.mEhplmn;
    }

    public CarrierInfo(String mcc, String mnc, String spn, String gid1, String gid2, String imsi, String iccid, String impi, List<String> plmnArrayList) {
        this.mMcc = mcc;
        this.mMnc = mnc;
        this.mSpn = spn;
        this.mGid1 = gid1;
        this.mGid2 = gid2;
        this.mImsiPrefix = imsi;
        this.mIccid = iccid;
        this.mImpi = impi;
        this.mEhplmn = plmnArrayList;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString8(this.mMcc);
        dest.writeString8(this.mMnc);
        dest.writeString8(this.mSpn);
        dest.writeString8(this.mGid1);
        dest.writeString8(this.mGid2);
        dest.writeString8(this.mImsiPrefix);
        dest.writeString8(this.mIccid);
        dest.writeString8(this.mImpi);
        dest.writeStringList(this.mEhplmn);
    }

    public CarrierInfo(Parcel in) {
        this.mEhplmn = new ArrayList();
        this.mMcc = in.readString8();
        this.mMnc = in.readString8();
        this.mSpn = in.readString8();
        this.mGid1 = in.readString8();
        this.mGid2 = in.readString8();
        this.mImsiPrefix = in.readString8();
        this.mIccid = in.readString8();
        this.mImpi = in.readString8();
        in.readStringList(this.mEhplmn);
    }

    public String toString() {
        return "CarrierInfo MCC = " + this.mMcc + "   MNC = " + this.mMnc + "  SPN = " + this.mSpn + "   GID1 = " + this.mGid1 + "   GID2 = " + this.mGid2 + "   IMSI = " + getPrintableImsi() + "   ICCID = " + SubscriptionInfo.getPrintableId(this.mIccid) + "  IMPI = " + this.mImpi + "  EHPLMN = [ " + getEhplmn_toString() + " ]";
    }

    private String getEhplmn_toString() {
        return String.join("  ", this.mEhplmn);
    }

    private String getPrintableImsi() {
        boolean enablePiiLog = com.android.telephony.Rlog.isLoggable("CarrierInfo", 2);
        return (this.mImsiPrefix == null || this.mImsiPrefix.length() <= 6) ? this.mImsiPrefix : this.mImsiPrefix.substring(0, 6) + com.android.telephony.Rlog.pii(enablePiiLog, this.mImsiPrefix.substring(6));
    }
}
