package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class CallState implements Parcelable {
    public static final int CALL_CLASSIFICATION_BACKGROUND = 2;
    public static final int CALL_CLASSIFICATION_FOREGROUND = 1;
    public static final int CALL_CLASSIFICATION_MAX = 3;
    public static final int CALL_CLASSIFICATION_RINGING = 0;
    public static final int CALL_CLASSIFICATION_UNKNOWN = -1;
    public static final Parcelable.Creator<CallState> CREATOR = new Parcelable.Creator() { // from class: android.telephony.CallState.1
        @Override // android.os.Parcelable.Creator
        public CallState createFromParcel(Parcel parcel) {
            return new CallState(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public CallState[] newArray(int i) {
            return new CallState[i];
        }
    };
    private final int mCallClassification;
    private final CallQuality mCallQuality;
    private String mImsCallId;
    private int mImsCallServiceType;
    private int mImsCallType;
    private final int mNetworkType;
    private final int mPreciseCallState;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private CallState(int i, int i2, CallQuality callQuality, int i3, String str, int i4, int i5) {
        this.mPreciseCallState = i;
        this.mNetworkType = i2;
        this.mCallQuality = callQuality;
        this.mCallClassification = i3;
        this.mImsCallId = str;
        this.mImsCallServiceType = i4;
        this.mImsCallType = i5;
    }

    public String toString() {
        return "mPreciseCallState=" + this.mPreciseCallState + " mNetworkType=" + this.mNetworkType + " mCallQuality=" + this.mCallQuality + " mCallClassification" + this.mCallClassification + " mImsCallId=" + this.mImsCallId + " mImsCallServiceType=" + this.mImsCallServiceType + " mImsCallType=" + this.mImsCallType;
    }

    private CallState(Parcel parcel) {
        this.mPreciseCallState = parcel.readInt();
        this.mNetworkType = parcel.readInt();
        this.mCallQuality = (CallQuality) parcel.readParcelable(CallQuality.class.getClassLoader(), CallQuality.class);
        this.mCallClassification = parcel.readInt();
        this.mImsCallId = parcel.readString();
        this.mImsCallServiceType = parcel.readInt();
        this.mImsCallType = parcel.readInt();
    }

    public int getCallState() {
        return this.mPreciseCallState;
    }

    public int getNetworkType() {
        return this.mNetworkType;
    }

    public CallQuality getCallQuality() {
        return this.mCallQuality;
    }

    public int getCallClassification() {
        return this.mCallClassification;
    }

    public String getImsCallSessionId() {
        return this.mImsCallId;
    }

    public int getImsCallServiceType() {
        return this.mImsCallServiceType;
    }

    public int getImsCallType() {
        return this.mImsCallType;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mPreciseCallState), Integer.valueOf(this.mNetworkType), this.mCallQuality, Integer.valueOf(this.mCallClassification), this.mImsCallId, Integer.valueOf(this.mImsCallServiceType), Integer.valueOf(this.mImsCallType));
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof CallState) && hashCode() == obj.hashCode()) {
            if (this == obj) {
                return true;
            }
            CallState callState = (CallState) obj;
            if (this.mPreciseCallState == callState.mPreciseCallState && this.mNetworkType == callState.mNetworkType && Objects.equals(this.mCallQuality, callState.mCallQuality) && this.mCallClassification == callState.mCallClassification && Objects.equals(this.mImsCallId, callState.mImsCallId) && this.mImsCallType == callState.mImsCallType && this.mImsCallServiceType == callState.mImsCallServiceType) {
                return true;
            }
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mPreciseCallState);
        parcel.writeInt(this.mNetworkType);
        parcel.writeParcelable(this.mCallQuality, i);
        parcel.writeInt(this.mCallClassification);
        parcel.writeString(this.mImsCallId);
        parcel.writeInt(this.mImsCallServiceType);
        parcel.writeInt(this.mImsCallType);
    }

    public static final class Builder {
        private String mImsCallId;
        private int mPreciseCallState;
        private int mNetworkType = 0;
        private CallQuality mCallQuality = null;
        private int mCallClassification = -1;
        private int mImsCallServiceType = 0;
        private int mImsCallType = 0;

        public Builder(int i) {
            this.mPreciseCallState = i;
        }

        public Builder setNetworkType(int i) {
            this.mNetworkType = i;
            return this;
        }

        public Builder setCallQuality(CallQuality callQuality) {
            this.mCallQuality = callQuality;
            return this;
        }

        public Builder setCallClassification(int i) {
            this.mCallClassification = i;
            return this;
        }

        public Builder setImsCallSessionId(String str) {
            this.mImsCallId = str;
            return this;
        }

        public Builder setImsCallServiceType(int i) {
            this.mImsCallServiceType = i;
            return this;
        }

        public Builder setImsCallType(int i) {
            this.mImsCallType = i;
            return this;
        }

        public CallState build() {
            return new CallState(this.mPreciseCallState, this.mNetworkType, this.mCallQuality, this.mCallClassification, this.mImsCallId, this.mImsCallServiceType, this.mImsCallType);
        }
    }
}
