package android.telephony.satellite;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.telephony.util.TelephonyUtils;

public final class SemSatSimAuthReqData implements Parcelable {
    public static final Parcelable.Creator<SemSatSimAuthReqData> CREATOR =
            new Parcelable.Creator<
                    SemSatSimAuthReqData>() { // from class:
                                              // android.telephony.satellite.SemSatSimAuthReqData.1
                @Override // android.os.Parcelable.Creator
                public SemSatSimAuthReqData createFromParcel(Parcel source) {
                    return new SemSatSimAuthReqData(source);
                }

                @Override // android.os.Parcelable.Creator
                public SemSatSimAuthReqData[] newArray(int size) {
                    return new SemSatSimAuthReqData[size];
                }
            };
    private static final String LOG_TAG = "SemSatSimAuthReqData";
    private String mAuth;
    private int mAuthLen;
    private String mRand;
    private int mRandLen;

    public SemSatSimAuthReqData(int randLen, String rand, int authLen, String auth) {
        this.mRandLen = randLen;
        this.mRand = rand;
        this.mAuthLen = authLen;
        this.mAuth = auth;
    }

    private SemSatSimAuthReqData(Parcel in) {
        this.mRandLen = in.readInt();
        this.mRand = in.readString();
        this.mAuthLen = in.readInt();
        this.mAuth = in.readString();
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

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        if (len % 2 != 0) {
            s = s + "0";
            len++;
        }
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] =
                    (byte)
                            ((Character.digit(s.charAt(i), 16) << 4)
                                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mRandLen);
        dest.writeString(this.mRand);
        dest.writeInt(this.mAuthLen);
        dest.writeString(this.mAuth);
    }

    public String toString() {
        if (TelephonyUtils.IS_DEBUGGABLE) {
            return "SemSatSimAuthReqData randlen: "
                    + this.mRandLen
                    + " rand: "
                    + this.mRand
                    + " authlen: "
                    + this.mAuthLen
                    + " auth: "
                    + this.mAuth;
        }
        return LOG_TAG;
    }
}
