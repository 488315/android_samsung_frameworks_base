package android.telephony.satellite;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.util.TelephonyUtils;
import vendor.samsung.hardware.radio.satellite.SehSatSimAuthRespData;

/* loaded from: classes4.dex */
public final class SemSatSimAuthResultData implements Parcelable {
    public static final Parcelable.Creator<SemSatSimAuthResultData> CREATOR = new Parcelable.Creator<SemSatSimAuthResultData>() { // from class: android.telephony.satellite.SemSatSimAuthResultData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSatSimAuthResultData createFromParcel(Parcel parcel) {
            return new SemSatSimAuthResultData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSatSimAuthResultData[] newArray(int i) {
            return new SemSatSimAuthResultData[i];
        }
    };
    private static final String LOG_TAG = "SemSatSimAuthResultData";
    public static int SIM_AUTH_MAC_FAILURE = 152;
    public static int SIM_AUTH_NO_SIM = 255;
    public static int SIM_AUTH_SUCCESSFUL = 219;
    public static int SIM_AUTH_SYNC_FAILURE = 220;
    private String mAuts;
    private int mAutsLen;
    private String mCk;
    private int mCkLen;
    private String mIk;
    private int mIkLen;
    private String mKc;
    private int mKcLen;
    private String mRes;
    private int mResLen;
    private int mResult;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemSatSimAuthResultData(int i, int i2, String str, int i3, String str2, int i4, String str3, int i5, String str4, int i6, String str5) {
        this.mResult = i;
        this.mResLen = i2;
        this.mRes = str;
        this.mCkLen = i3;
        this.mCk = str2;
        this.mIkLen = i4;
        this.mIk = str3;
        this.mKcLen = i5;
        this.mKc = str4;
        this.mAutsLen = i6;
        this.mAuts = str5;
    }

    private SemSatSimAuthResultData(Parcel parcel) {
        this.mResult = parcel.readInt();
        this.mResLen = parcel.readInt();
        this.mRes = parcel.readString();
        this.mCkLen = parcel.readInt();
        this.mCk = parcel.readString();
        this.mIkLen = parcel.readInt();
        this.mIk = parcel.readString();
        this.mKcLen = parcel.readInt();
        this.mKc = parcel.readString();
        this.mAutsLen = parcel.readInt();
        this.mAuts = parcel.readString();
    }

    public SehSatSimAuthRespData toSimAuthRespDataAidl() {
        SehSatSimAuthRespData sehSatSimAuthRespData = new SehSatSimAuthRespData();
        sehSatSimAuthRespData.result = this.mResult;
        sehSatSimAuthRespData.resLen = this.mResLen;
        sehSatSimAuthRespData.res = this.mRes;
        sehSatSimAuthRespData.ckLen = this.mCkLen;
        sehSatSimAuthRespData.ck = this.mCk;
        sehSatSimAuthRespData.ikLen = this.mIkLen;
        sehSatSimAuthRespData.ik = this.mIk;
        sehSatSimAuthRespData.kcLen = this.mKcLen;
        sehSatSimAuthRespData.kc = this.mKc;
        sehSatSimAuthRespData.autsLen = this.mAutsLen;
        sehSatSimAuthRespData.auts = this.mAuts;
        return sehSatSimAuthRespData;
    }

    public boolean isValid() {
        int i = this.mResult;
        return i == SIM_AUTH_SUCCESSFUL ? this.mResLen > 0 && this.mCkLen > 0 && this.mIkLen > 0 && this.mKcLen > 0 : i == SIM_AUTH_SYNC_FAILURE ? this.mAutsLen > 0 : i == SIM_AUTH_MAC_FAILURE || i == SIM_AUTH_NO_SIM;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResult);
        parcel.writeInt(this.mResLen);
        parcel.writeString(this.mRes);
        parcel.writeInt(this.mCkLen);
        parcel.writeString(this.mCk);
        parcel.writeInt(this.mIkLen);
        parcel.writeString(this.mIk);
        parcel.writeInt(this.mKcLen);
        parcel.writeString(this.mKc);
        parcel.writeInt(this.mAutsLen);
        parcel.writeString(this.mAuts);
    }

    public String toString() {
        if (TelephonyUtils.IS_DEBUGGABLE) {
            return "SemSatSimAuthResultData result: " + this.mResult + " reslen: " + this.mResLen + " res: " + this.mRes + " cklen: " + this.mCkLen + " ck: " + this.mCk + " iklen: " + this.mIkLen + " ik: " + this.mIk + " kclen: " + this.mKcLen + " kc: " + this.mKc + " autslen: " + this.mAutsLen + " auts: " + this.mAuts;
        }
        return LOG_TAG;
    }

    public static final class Builder {
        private String mAuts;
        private int mAutsLen;
        private String mCk;
        private int mCkLen;
        private String mIk;
        private int mIkLen;
        private String mKc;
        private int mKcLen;
        private String mRes;
        private int mResLen;
        private int mResult;

        public Builder() {
            this.mResult = 0;
            this.mResLen = 0;
            this.mRes = "";
            this.mCkLen = 0;
            this.mCk = "";
            this.mIkLen = 0;
            this.mIk = "";
            this.mKcLen = 0;
            this.mKc = "";
            this.mAutsLen = 0;
            this.mAuts = "";
        }

        public Builder(SemSatSimAuthResultData semSatSimAuthResultData) {
            this.mResult = semSatSimAuthResultData.mResult;
            this.mResLen = semSatSimAuthResultData.mResLen;
            this.mRes = semSatSimAuthResultData.mRes;
            this.mCkLen = semSatSimAuthResultData.mCkLen;
            this.mCk = semSatSimAuthResultData.mCk;
            this.mIkLen = semSatSimAuthResultData.mIkLen;
            this.mIk = semSatSimAuthResultData.mIk;
            this.mKcLen = semSatSimAuthResultData.mKcLen;
            this.mKc = semSatSimAuthResultData.mKc;
            this.mAutsLen = semSatSimAuthResultData.mAutsLen;
            this.mAuts = semSatSimAuthResultData.mAuts;
        }

        public Builder setResult(int i) {
            this.mResult = i;
            return this;
        }

        public Builder setResLen(int i) {
            this.mResLen = i;
            return this;
        }

        public Builder setRes(byte[] bArr) {
            this.mRes = SemSatSimAuthResultData.byteArrayToHexString(bArr);
            return this;
        }

        public Builder setCkLen(int i) {
            this.mCkLen = i;
            return this;
        }

        public Builder setCk(byte[] bArr) {
            this.mCk = SemSatSimAuthResultData.byteArrayToHexString(bArr);
            return this;
        }

        public Builder setIkLen(int i) {
            this.mIkLen = i;
            return this;
        }

        public Builder setIk(byte[] bArr) {
            this.mIk = SemSatSimAuthResultData.byteArrayToHexString(bArr);
            return this;
        }

        public Builder setKcLen(int i) {
            this.mKcLen = i;
            return this;
        }

        public Builder setKc(byte[] bArr) {
            this.mKc = SemSatSimAuthResultData.byteArrayToHexString(bArr);
            return this;
        }

        public Builder setAutsLen(int i) {
            this.mAutsLen = i;
            return this;
        }

        public Builder setAuts(byte[] bArr) {
            this.mAuts = SemSatSimAuthResultData.byteArrayToHexString(bArr);
            return this;
        }

        public SemSatSimAuthResultData build() {
            return new SemSatSimAuthResultData(this.mResult, this.mResLen, this.mRes, this.mCkLen, this.mCk, this.mIkLen, this.mIk, this.mKcLen, this.mKc, this.mAutsLen, this.mAuts);
        }
    }

    public static String byteArrayToHexString(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%02x", Integer.valueOf(b & 255)));
        }
        return sb.toString();
    }
}
