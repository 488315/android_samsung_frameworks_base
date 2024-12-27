package android.telephony.satellite;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.util.TelephonyUtils;
import vendor.samsung.hardware.radio.satellite.SehSatSimAuthRespData;

/* loaded from: classes4.dex */
public final class SemSatSimAuthResultData implements Parcelable {
    private static final String LOG_TAG = "SemSatSimAuthResultData";
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
    public static int SIM_AUTH_MAC_FAILURE = 152;
    public static int SIM_AUTH_SUCCESSFUL = 219;
    public static int SIM_AUTH_SYNC_FAILURE = 220;
    public static int SIM_AUTH_NO_SIM = 255;
    public static final Parcelable.Creator<SemSatSimAuthResultData> CREATOR = new Parcelable.Creator<SemSatSimAuthResultData>() { // from class: android.telephony.satellite.SemSatSimAuthResultData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSatSimAuthResultData createFromParcel(Parcel source) {
            return new SemSatSimAuthResultData(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSatSimAuthResultData[] newArray(int size) {
            return new SemSatSimAuthResultData[size];
        }
    };

    public SemSatSimAuthResultData(int result, int resLen, String res, int ckLen, String ck, int ikLen, String ik, int kcLen, String kc, int autsLen, String auts) {
        this.mResult = result;
        this.mResLen = resLen;
        this.mRes = res;
        this.mCkLen = ckLen;
        this.mCk = ck;
        this.mIkLen = ikLen;
        this.mIk = ik;
        this.mKcLen = kcLen;
        this.mKc = kc;
        this.mAutsLen = autsLen;
        this.mAuts = auts;
    }

    private SemSatSimAuthResultData(Parcel in) {
        this.mResult = in.readInt();
        this.mResLen = in.readInt();
        this.mRes = in.readString();
        this.mCkLen = in.readInt();
        this.mCk = in.readString();
        this.mIkLen = in.readInt();
        this.mIk = in.readString();
        this.mKcLen = in.readInt();
        this.mKc = in.readString();
        this.mAutsLen = in.readInt();
        this.mAuts = in.readString();
    }

    public SehSatSimAuthRespData toSimAuthRespDataAidl() {
        SehSatSimAuthRespData data = new SehSatSimAuthRespData();
        data.result = this.mResult;
        data.resLen = this.mResLen;
        data.res = this.mRes;
        data.ckLen = this.mCkLen;
        data.ck = this.mCk;
        data.ikLen = this.mIkLen;
        data.ik = this.mIk;
        data.kcLen = this.mKcLen;
        data.kc = this.mKc;
        data.autsLen = this.mAutsLen;
        data.auts = this.mAuts;
        return data;
    }

    public boolean isValid() {
        return this.mResult == SIM_AUTH_SUCCESSFUL ? this.mResLen > 0 && this.mCkLen > 0 && this.mIkLen > 0 && this.mKcLen > 0 : this.mResult == SIM_AUTH_SYNC_FAILURE ? this.mAutsLen > 0 : this.mResult == SIM_AUTH_MAC_FAILURE || this.mResult == SIM_AUTH_NO_SIM;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mResult);
        dest.writeInt(this.mResLen);
        dest.writeString(this.mRes);
        dest.writeInt(this.mCkLen);
        dest.writeString(this.mCk);
        dest.writeInt(this.mIkLen);
        dest.writeString(this.mIk);
        dest.writeInt(this.mKcLen);
        dest.writeString(this.mKc);
        dest.writeInt(this.mAutsLen);
        dest.writeString(this.mAuts);
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

        public Builder(SemSatSimAuthResultData data) {
            this.mResult = data.mResult;
            this.mResLen = data.mResLen;
            this.mRes = data.mRes;
            this.mCkLen = data.mCkLen;
            this.mCk = data.mCk;
            this.mIkLen = data.mIkLen;
            this.mIk = data.mIk;
            this.mKcLen = data.mKcLen;
            this.mKc = data.mKc;
            this.mAutsLen = data.mAutsLen;
            this.mAuts = data.mAuts;
        }

        public Builder setResult(int result) {
            this.mResult = result;
            return this;
        }

        public Builder setResLen(int resLen) {
            this.mResLen = resLen;
            return this;
        }

        public Builder setRes(byte[] res) {
            this.mRes = SemSatSimAuthResultData.byteArrayToHexString(res);
            return this;
        }

        public Builder setCkLen(int ckLen) {
            this.mCkLen = ckLen;
            return this;
        }

        public Builder setCk(byte[] ck) {
            this.mCk = SemSatSimAuthResultData.byteArrayToHexString(ck);
            return this;
        }

        public Builder setIkLen(int ikLen) {
            this.mIkLen = ikLen;
            return this;
        }

        public Builder setIk(byte[] ik) {
            this.mIk = SemSatSimAuthResultData.byteArrayToHexString(ik);
            return this;
        }

        public Builder setKcLen(int kcLen) {
            this.mKcLen = kcLen;
            return this;
        }

        public Builder setKc(byte[] kc) {
            this.mKc = SemSatSimAuthResultData.byteArrayToHexString(kc);
            return this;
        }

        public Builder setAutsLen(int autsLen) {
            this.mAutsLen = autsLen;
            return this;
        }

        public Builder setAuts(byte[] auts) {
            this.mAuts = SemSatSimAuthResultData.byteArrayToHexString(auts);
            return this;
        }

        public SemSatSimAuthResultData build() {
            return new SemSatSimAuthResultData(this.mResult, this.mResLen, this.mRes, this.mCkLen, this.mCk, this.mIkLen, this.mIk, this.mKcLen, this.mKc, this.mAutsLen, this.mAuts);
        }
    }

    public static String byteArrayToHexString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", Integer.valueOf(b & 255)));
        }
        return sb.toString();
    }
}
