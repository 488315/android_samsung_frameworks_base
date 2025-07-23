package android.telephony.satellite;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.util.TelephonyUtils;

/* loaded from: classes4.dex */
public final class SemSatSimAuthReqData implements Parcelable {
    public static final Parcelable.Creator<SemSatSimAuthReqData> CREATOR = new Parcelable.Creator<SemSatSimAuthReqData>() { // from class: android.telephony.satellite.SemSatSimAuthReqData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSatSimAuthReqData createFromParcel(Parcel parcel) {
            return new SemSatSimAuthReqData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSatSimAuthReqData[] newArray(int i) {
            return new SemSatSimAuthReqData[i];
        }
    };
    private static final String LOG_TAG = "SemSatSimAuthReqData";
    private String mAuth;
    private int mAuthLen;
    private String mRand;
    private int mRandLen;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemSatSimAuthReqData(int i, String str, int i2, String str2) {
        this.mRandLen = i;
        this.mRand = str;
        this.mAuthLen = i2;
        this.mAuth = str2;
    }

    private SemSatSimAuthReqData(Parcel parcel) {
        this.mRandLen = parcel.readInt();
        this.mRand = parcel.readString();
        this.mAuthLen = parcel.readInt();
        this.mAuth = parcel.readString();
    }

    public int getRandLen() {
        return this.mRandLen;
    }

    public String getRand() {
        return this.mRand;
    }

    public int getAuthLen() {
        return this.mAuthLen;
    }

    public String getAuth() {
        return this.mAuth;
    }

    public byte[] getRandBytes() {
        return hexStringToByteArray(this.mRand);
    }

    public byte[] getAuthBytes() {
        return hexStringToByteArray(this.mAuth);
    }

    public static byte[] hexStringToByteArray(String str) {
        int length = str.length();
        if (length % 2 != 0) {
            str = str + "0";
            length++;
        }
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRandLen);
        parcel.writeString(this.mRand);
        parcel.writeInt(this.mAuthLen);
        parcel.writeString(this.mAuth);
    }

    public String toString() {
        if (TelephonyUtils.IS_DEBUGGABLE) {
            return "SemSatSimAuthReqData randlen: " + this.mRandLen + " rand: " + this.mRand + " authlen: " + this.mAuthLen + " auth: " + this.mAuth;
        }
        return LOG_TAG;
    }
}
