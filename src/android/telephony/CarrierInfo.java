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
        public CarrierInfo createFromParcel(Parcel parcel) {
            return new CarrierInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CarrierInfo[] newArray(int i) {
            return new CarrierInfo[i];
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

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

    public CarrierInfo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, List<String> list) {
        this.mMcc = str;
        this.mMnc = str2;
        this.mSpn = str3;
        this.mGid1 = str4;
        this.mGid2 = str5;
        this.mImsiPrefix = str6;
        this.mIccid = str7;
        this.mImpi = str8;
        this.mEhplmn = list;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mMcc);
        parcel.writeString8(this.mMnc);
        parcel.writeString8(this.mSpn);
        parcel.writeString8(this.mGid1);
        parcel.writeString8(this.mGid2);
        parcel.writeString8(this.mImsiPrefix);
        parcel.writeString8(this.mIccid);
        parcel.writeString8(this.mImpi);
        parcel.writeStringList(this.mEhplmn);
    }

    public CarrierInfo(Parcel parcel) {
        this.mEhplmn = new ArrayList();
        this.mMcc = parcel.readString8();
        this.mMnc = parcel.readString8();
        this.mSpn = parcel.readString8();
        this.mGid1 = parcel.readString8();
        this.mGid2 = parcel.readString8();
        this.mImsiPrefix = parcel.readString8();
        this.mIccid = parcel.readString8();
        this.mImpi = parcel.readString8();
        parcel.readStringList(this.mEhplmn);
    }

    public String toString() {
        return "CarrierInfo MCC = " + this.mMcc + "   MNC = " + this.mMnc + "  SPN = " + this.mSpn + "   GID1 = " + this.mGid1 + "   GID2 = " + this.mGid2 + "   IMSI = " + getPrintableImsi() + "   ICCID = " + SubscriptionInfo.getPrintableId(this.mIccid) + "  IMPI = " + this.mImpi + "  EHPLMN = [ " + getEhplmn_toString() + " ]";
    }

    private String getEhplmn_toString() {
        return String.join("  ", this.mEhplmn);
    }

    private String getPrintableImsi() {
        boolean isLoggable = com.android.telephony.Rlog.isLoggable("CarrierInfo", 2);
        String str = this.mImsiPrefix;
        if (str == null || str.length() <= 6) {
            return this.mImsiPrefix;
        }
        return this.mImsiPrefix.substring(0, 6) + com.android.telephony.Rlog.pii(isLoggable, this.mImsiPrefix.substring(6));
    }
}
