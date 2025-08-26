package android.service.carrier;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.telephony.Rlog;
import java.util.Objects;

/* loaded from: classes3.dex */
public class CarrierIdentifier implements Parcelable {
    public static final Parcelable.Creator<CarrierIdentifier> CREATOR = new Parcelable.Creator<CarrierIdentifier>() { // from class: android.service.carrier.CarrierIdentifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CarrierIdentifier createFromParcel(Parcel parcel) {
            return new CarrierIdentifier(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CarrierIdentifier[] newArray(int i) {
            return new CarrierIdentifier[i];
        }
    };
    private int mCarrierId;
    private String mGid1;
    private String mGid2;
    private String mImsi;
    private String mMcc;
    private String mMnc;
    private int mSpecificCarrierId;
    private String mSpn;

    public interface MatchType {
        public static final int ALL = 0;
        public static final int GID1 = 3;
        public static final int GID2 = 4;
        public static final int IMSI_PREFIX = 2;
        public static final int SPN = 1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CarrierIdentifier(String str, String str2, String str3, String str4, String str5, String str6) {
        this(str, str2, str3, str4, str5, str6, -1, -1);
    }

    public CarrierIdentifier(String str, String str2, String str3, String str4, String str5, String str6, int i, int i2) {
        this.mMcc = str;
        this.mMnc = str2;
        this.mSpn = str3;
        this.mImsi = str4;
        this.mGid1 = str5;
        this.mGid2 = str6;
        this.mCarrierId = i;
        this.mSpecificCarrierId = i2;
    }

    public CarrierIdentifier(byte[] bArr, String str, String str2) {
        this.mCarrierId = -1;
        this.mSpecificCarrierId = -1;
        if (bArr.length != 3) {
            throw new IllegalArgumentException("MCC & MNC must be set by a 3-byte array: byte[" + bArr.length + NavigationBarInflaterView.SIZE_MOD_END);
        }
        String strBytesToHexString = IccUtils.bytesToHexString(bArr);
        this.mMcc = new String(new char[]{strBytesToHexString.charAt(1), strBytesToHexString.charAt(0), strBytesToHexString.charAt(3)});
        if (strBytesToHexString.charAt(2) == 'F') {
            this.mMnc = new String(new char[]{strBytesToHexString.charAt(5), strBytesToHexString.charAt(4)});
        } else {
            this.mMnc = new String(new char[]{strBytesToHexString.charAt(5), strBytesToHexString.charAt(4), strBytesToHexString.charAt(2)});
        }
        this.mGid1 = str;
        this.mGid2 = str2;
        this.mSpn = null;
        this.mImsi = null;
    }

    public CarrierIdentifier(Parcel parcel) {
        this.mCarrierId = -1;
        this.mSpecificCarrierId = -1;
        readFromParcel(parcel);
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

    public String getImsi() {
        return this.mImsi;
    }

    public String getGid1() {
        return this.mGid1;
    }

    public String getGid2() {
        return this.mGid2;
    }

    public int getCarrierId() {
        return this.mCarrierId;
    }

    public int getSpecificCarrierId() {
        return this.mSpecificCarrierId;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            CarrierIdentifier carrierIdentifier = (CarrierIdentifier) obj;
            if (Objects.equals(this.mMcc, carrierIdentifier.mMcc) && Objects.equals(this.mMnc, carrierIdentifier.mMnc) && Objects.equals(this.mSpn, carrierIdentifier.mSpn) && Objects.equals(this.mImsi, carrierIdentifier.mImsi) && Objects.equals(this.mGid1, carrierIdentifier.mGid1) && Objects.equals(this.mGid2, carrierIdentifier.mGid2) && Objects.equals(Integer.valueOf(this.mCarrierId), Integer.valueOf(carrierIdentifier.mCarrierId)) && Objects.equals(Integer.valueOf(this.mSpecificCarrierId), Integer.valueOf(carrierIdentifier.mSpecificCarrierId))) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mMcc, this.mMnc, this.mSpn, this.mImsi, this.mGid1, this.mGid2, Integer.valueOf(this.mCarrierId), Integer.valueOf(this.mSpecificCarrierId));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mMcc);
        parcel.writeString(this.mMnc);
        parcel.writeString(this.mSpn);
        parcel.writeString(this.mImsi);
        parcel.writeString(this.mGid1);
        parcel.writeString(this.mGid2);
        parcel.writeInt(this.mCarrierId);
        parcel.writeInt(this.mSpecificCarrierId);
    }

    public String toString() {
        return "CarrierIdentifier{mcc=" + this.mMcc + ",mnc=" + this.mMnc + ",spn=" + this.mSpn + ",imsi=" + Rlog.pii(false, (Object) this.mImsi) + ",gid1=" + this.mGid1 + ",gid2=" + this.mGid2 + ",carrierid=" + this.mCarrierId + ",specificCarrierId=" + this.mSpecificCarrierId + "}";
    }

    public void readFromParcel(Parcel parcel) {
        this.mMcc = parcel.readString();
        this.mMnc = parcel.readString();
        this.mSpn = parcel.readString();
        this.mImsi = parcel.readString();
        this.mGid1 = parcel.readString();
        this.mGid2 = parcel.readString();
        this.mCarrierId = parcel.readInt();
        this.mSpecificCarrierId = parcel.readInt();
    }
}
