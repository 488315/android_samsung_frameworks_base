package com.sec.ims;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;

/* loaded from: classes4.dex */
public class LastEndedImsCallInfo implements Parcelable {
    public static final Parcelable.Creator<LastEndedImsCallInfo> CREATOR = new Parcelable.Creator<LastEndedImsCallInfo>() { // from class: com.sec.ims.LastEndedImsCallInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LastEndedImsCallInfo createFromParcel(Parcel parcel) {
            return new LastEndedImsCallInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LastEndedImsCallInfo[] newArray(int i) {
            return new LastEndedImsCallInfo[i];
        }
    };
    private int mErrorCode;
    private String mErrorMessage;
    private boolean mIsCallDrop;

    public class Builder {
        protected boolean mIsCallDrop = false;
        protected int mErrorCode = 0;
        protected String mErrorMessage = "";

        public LastEndedImsCallInfo build() {
            return new LastEndedImsCallInfo(this);
        }

        public void setErrorCode(int i) {
            this.mErrorCode = i;
        }

        public void setErrorMessage(String str) {
            this.mErrorMessage = str;
        }

        public void setIsCallDrop(boolean z) {
            this.mIsCallDrop = z;
        }
    }

    public LastEndedImsCallInfo() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getLastEndedErrorCode() {
        return this.mErrorCode;
    }

    public String getLastEndedErrorMessage() {
        return this.mErrorMessage;
    }

    public boolean isIsLastEndedCallDrop() {
        return this.mIsCallDrop;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("LastEndedImsCallInfo [mIsCallDrop=");
        sb.append(this.mIsCallDrop);
        sb.append(", mErrorCode=");
        sb.append(this.mErrorCode);
        sb.append(", mErrorMessage=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.mErrorMessage, "]");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mIsCallDrop ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mErrorCode);
        parcel.writeString(this.mErrorMessage);
    }

    public LastEndedImsCallInfo(Parcel parcel) {
        this.mIsCallDrop = parcel.readByte() != 0;
        this.mErrorCode = parcel.readInt();
        this.mErrorMessage = parcel.readString();
    }

    public LastEndedImsCallInfo(Builder builder) {
        this.mIsCallDrop = builder.mIsCallDrop;
        this.mErrorCode = builder.mErrorCode;
        this.mErrorMessage = builder.mErrorMessage;
    }
}
