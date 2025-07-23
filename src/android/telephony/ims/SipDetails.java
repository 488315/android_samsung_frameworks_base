package android.telephony.ims;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class SipDetails implements Parcelable {
    public static final Parcelable.Creator<SipDetails> CREATOR = new Parcelable.Creator<SipDetails>() { // from class: android.telephony.ims.SipDetails.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SipDetails createFromParcel(Parcel parcel) {
            return new SipDetails(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SipDetails[] newArray(int i) {
            return new SipDetails[i];
        }
    };
    public static final int METHOD_PUBLISH = 2;
    public static final int METHOD_REGISTER = 1;
    public static final int METHOD_SUBSCRIBE = 3;
    public static final int METHOD_UNKNOWN = 0;
    private final String mCallId;
    private final int mCseq;
    private final int mMethod;
    private final int mReasonHeaderCause;
    private final String mReasonHeaderText;
    private final int mResponseCode;
    private final String mResponsePhrase;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Method {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private String mCallId;
        private int mMethod;
        private int mCseq = 0;
        private int mResponseCode = 0;
        private String mResponsePhrase = "";
        private int mReasonHeaderCause = 0;
        private String mReasonHeaderText = "";

        public Builder(int i) {
            this.mMethod = i;
        }

        public Builder setCSeq(int i) {
            this.mCseq = i;
            return this;
        }

        public Builder setSipResponseCode(int i, String str) {
            this.mResponseCode = i;
            this.mResponsePhrase = str;
            return this;
        }

        public Builder setSipResponseReasonHeader(int i, String str) {
            this.mReasonHeaderCause = i;
            this.mReasonHeaderText = str;
            return this;
        }

        public Builder setCallId(String str) {
            this.mCallId = str;
            return this;
        }

        public SipDetails build() {
            return new SipDetails(this);
        }
    }

    private SipDetails(Builder builder) {
        this.mMethod = builder.mMethod;
        this.mCseq = builder.mCseq;
        this.mResponseCode = builder.mResponseCode;
        this.mResponsePhrase = builder.mResponsePhrase;
        this.mReasonHeaderCause = builder.mReasonHeaderCause;
        this.mReasonHeaderText = builder.mReasonHeaderText;
        this.mCallId = builder.mCallId;
    }

    public int getMethod() {
        return this.mMethod;
    }

    public int getCSeq() {
        return this.mCseq;
    }

    public int getResponseCode() {
        return this.mResponseCode;
    }

    public String getResponsePhrase() {
        return this.mResponsePhrase;
    }

    public int getReasonHeaderCause() {
        return this.mReasonHeaderCause;
    }

    public String getReasonHeaderText() {
        return this.mReasonHeaderText;
    }

    public String getCallId() {
        return this.mCallId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mMethod);
        parcel.writeInt(this.mCseq);
        parcel.writeInt(this.mResponseCode);
        parcel.writeString(this.mResponsePhrase);
        parcel.writeInt(this.mReasonHeaderCause);
        parcel.writeString(this.mReasonHeaderText);
        parcel.writeString(this.mCallId);
    }

    private SipDetails(Parcel parcel) {
        this.mMethod = parcel.readInt();
        this.mCseq = parcel.readInt();
        this.mResponseCode = parcel.readInt();
        this.mResponsePhrase = parcel.readString();
        this.mReasonHeaderCause = parcel.readInt();
        this.mReasonHeaderText = parcel.readString();
        this.mCallId = parcel.readString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            SipDetails sipDetails = (SipDetails) obj;
            if (this.mMethod == sipDetails.mMethod && this.mCseq == sipDetails.mCseq && this.mResponseCode == sipDetails.mResponseCode && TextUtils.equals(this.mResponsePhrase, sipDetails.mResponsePhrase) && this.mReasonHeaderCause == sipDetails.mReasonHeaderCause && TextUtils.equals(this.mReasonHeaderText, sipDetails.mReasonHeaderText) && TextUtils.equals(this.mCallId, sipDetails.mCallId)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mMethod), Integer.valueOf(this.mCseq), Integer.valueOf(this.mResponseCode), this.mResponsePhrase, Integer.valueOf(this.mReasonHeaderCause), this.mReasonHeaderText, this.mCallId);
    }

    public String toString() {
        return "SipDetails { methodType= " + this.mMethod + ", cSeq=" + this.mCseq + ", ResponseCode=" + this.mResponseCode + ", ResponseCPhrase=" + this.mResponsePhrase + ", ReasonHeaderCause=" + this.mReasonHeaderCause + ", ReasonHeaderText=" + this.mReasonHeaderText + ", callId=" + this.mCallId + "}";
    }
}
