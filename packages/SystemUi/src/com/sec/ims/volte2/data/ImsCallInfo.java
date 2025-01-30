package com.sec.ims.volte2.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ImsCallInfo implements Parcelable {
    public static final Parcelable.Creator<ImsCallInfo> CREATOR = new Parcelable.Creator<ImsCallInfo>() { // from class: com.sec.ims.volte2.data.ImsCallInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsCallInfo createFromParcel(Parcel parcel) {
            return new ImsCallInfo(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsCallInfo[] newArray(int i) {
            return new ImsCallInfo[i];
        }
    };
    private int mCallId;
    private int mCallType;
    private String mCmcCallTime;
    private String mCmcDeviceId;
    private int mDirection;
    private int mErrorCode;
    private String mErrorMessage;
    private boolean mIsConferenceCall;
    private boolean mIsDowngradedAtEstablish;
    private boolean mIsDowngradedVideoCall;
    private boolean mIsSamsungMdmnCall;
    private String mNumberPlus;
    private String mPeerUri;
    private int mRatInfo;
    private int mVideoBearerState;
    private int mVideoNGbrBearerState;
    private int mVoiceBearerState;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class BearerState {
        public static final int BEARER_STATE_CLOSED = 3;
        public static final int BEARER_STATE_ESTABLISHED = 1;
        public static final int BEARER_STATE_MODIFIED = 2;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class CallType {
        public static final int CALL_TYPE_AUDIO = 1;
        public static final int CALL_TYPE_AUDIO_CONFERENCE = 5;
        public static final int CALL_TYPE_E911_AUDIO = 7;
        public static final int CALL_TYPE_E911_VIDEO = 8;
        public static final int CALL_TYPE_RTT_AUDIO = 14;
        public static final int CALL_TYPE_RTT_VOICE = 15;
        public static final int CALL_TYPE_TTY_FULL = 9;
        public static final int CALL_TYPE_TTY_HCO = 10;
        public static final int CALL_TYPE_TTY_VCO = 11;
        public static final int CALL_TYPE_UNKNOWN = 0;
        public static final int CALL_TYPE_USSD = 12;
        public static final int CALL_TYPE_VIDEO = 2;
        public static final int CALL_TYPE_VIDEO_CONFERENCE = 6;
        public static final int CALL_TYPE_VIDEO_SHARE_RX = 4;
        public static final int CALL_TYPE_VIDEO_SHARE_TX = 3;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Direction {
        public static final int DIRECTION_MO = 0;
        public static final int DIRECTION_MT = 1;
        public static final int DIRECTION_PULLED_MO = 2;
        public static final int DIRECTION_PULLED_MT = 3;
        public static final int DIRECTION_UNKNOWN = -1;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Qci {
        public static final int QCI_AUDIO = 1;
        public static final int QCI_VIDEO_GBR = 2;
        public static final int QCI_VIDEO_NGBR = 8;
        public static final int QCI_VIDEO_NGBR_7 = 7;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Radio {
        public static final int RADIO_IWLAN = 18;
        public static final int RADIO_LTE = 14;
        public static final int RADIO_NONE = 0;
    }

    public /* synthetic */ ImsCallInfo(Parcel parcel, int i) {
        this(parcel);
    }

    private void readFromParcel(Parcel parcel) {
        this.mCallId = parcel.readInt();
        this.mCallType = parcel.readInt();
        this.mIsDowngradedVideoCall = parcel.readInt() == 1;
        this.mIsDowngradedAtEstablish = parcel.readInt() == 1;
        this.mVoiceBearerState = parcel.readInt();
        this.mVideoBearerState = parcel.readInt();
        this.mVideoNGbrBearerState = parcel.readInt();
        this.mErrorCode = parcel.readInt();
        this.mErrorMessage = parcel.readString();
        this.mPeerUri = parcel.readString();
        this.mDirection = parcel.readInt();
        this.mIsConferenceCall = parcel.readInt() == 1;
        this.mIsSamsungMdmnCall = parcel.readInt() == 1;
        this.mCmcDeviceId = parcel.readString();
        this.mRatInfo = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getCallId() {
        return this.mCallId;
    }

    public int getCallType() {
        return this.mCallType;
    }

    public String getCmcCallTime() {
        return this.mCmcCallTime;
    }

    public String getCmcDeviceId() {
        return this.mCmcDeviceId;
    }

    public int getDedicatedBearerState(int i) {
        if (i == 1) {
            return this.mVoiceBearerState;
        }
        if (i == 2) {
            return this.mVideoBearerState;
        }
        if (i == 7 || i == 8) {
            return this.mVideoNGbrBearerState;
        }
        return 3;
    }

    public int getDirection() {
        return this.mDirection;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    public String getPeerUri() {
        return this.mPeerUri;
    }

    public int getRatInfo() {
        return this.mRatInfo;
    }

    public boolean isConferenceCall() {
        return this.mIsConferenceCall;
    }

    public boolean isDowngradedAtEstablish() {
        return this.mIsDowngradedAtEstablish;
    }

    public boolean isDowngradedVideoCall() {
        return this.mIsDowngradedVideoCall;
    }

    public boolean isMOCall() {
        int i = this.mDirection;
        return i == 0 || i == 2;
    }

    public boolean isMTCall() {
        int i = this.mDirection;
        return i == 1 || i == 3;
    }

    public boolean isSamsungMdmnCall() {
        return this.mIsSamsungMdmnCall;
    }

    public void setCmcCallTime(String str) {
        this.mCmcCallTime = str;
    }

    public void setCmcDeviceId(String str) {
        this.mCmcDeviceId = str;
    }

    public void setNumberPlus(String str) {
        this.mNumberPlus = str;
    }

    public void setRatInfo(int i) {
        this.mRatInfo = i;
    }

    public void setSamsungMdmnCall(boolean z) {
        this.mIsSamsungMdmnCall = z;
    }

    public String toString() {
        String m19m = ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("callId: ["), this.mCallId, "], callType: [");
        int i = this.mCallType;
        String m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(i != 1 ? i != 2 ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m19m, "UNKNOWN") : AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m19m, "CALL_TYPE_VIDEO") : AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m19m, "CALL_TYPE_VOICE"), "], Direction: [");
        int i2 = this.mDirection;
        StringBuilder m2m = AbstractC0000x2c234b15.m2m(i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m14m, "UNKNOWN") : AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m14m, "PULLED_MT") : AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m14m, "PULLED_MO") : AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m14m, "MT") : AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m14m, "MO"), "], isDowngradedVideoCall : [");
        m2m.append(this.mIsDowngradedVideoCall);
        m2m.append("], isDowngradedAtEstablish : [");
        m2m.append(this.mIsDowngradedAtEstablish);
        m2m.append("], VoiceBearerState : [");
        m2m.append(this.mVoiceBearerState);
        m2m.append("], VideoBearerState : [");
        m2m.append(this.mVideoBearerState);
        m2m.append("], VideoNGbrBearerState : [");
        m2m.append(this.mVideoNGbrBearerState);
        m2m.append("], isConferenceCall : [");
        m2m.append(this.mIsConferenceCall);
        m2m.append("], mIsSamsungMdmnCall : [");
        m2m.append(this.mIsSamsungMdmnCall);
        m2m.append("], mRatInfo : [");
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(m2m, this.mRatInfo, "]");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            return;
        }
        parcel.writeInt(this.mCallId);
        parcel.writeInt(this.mCallType);
        parcel.writeInt(this.mIsDowngradedVideoCall ? 1 : 0);
        parcel.writeInt(this.mIsDowngradedAtEstablish ? 1 : 0);
        parcel.writeInt(this.mVoiceBearerState);
        parcel.writeInt(this.mVideoBearerState);
        parcel.writeInt(this.mVideoNGbrBearerState);
        parcel.writeInt(this.mErrorCode);
        parcel.writeString(this.mErrorMessage);
        parcel.writeString(this.mPeerUri);
        parcel.writeInt(this.mDirection);
        parcel.writeInt(this.mIsConferenceCall ? 1 : 0);
        parcel.writeInt(this.mIsSamsungMdmnCall ? 1 : 0);
        parcel.writeString(this.mCmcDeviceId);
        parcel.writeInt(this.mRatInfo);
    }

    private ImsCallInfo(Parcel parcel) {
        this.mCallType = 0;
        this.mCallId = -1;
        this.mIsDowngradedVideoCall = false;
        this.mIsDowngradedAtEstablish = false;
        this.mVoiceBearerState = 3;
        this.mVideoBearerState = 3;
        this.mVideoNGbrBearerState = 3;
        this.mErrorCode = 0;
        this.mErrorMessage = "";
        this.mPeerUri = "";
        this.mDirection = -1;
        this.mIsConferenceCall = false;
        this.mIsSamsungMdmnCall = false;
        this.mNumberPlus = "";
        this.mCmcDeviceId = "";
        this.mCmcCallTime = "";
        this.mRatInfo = 0;
        readFromParcel(parcel);
    }

    public ImsCallInfo(int i, int i2, boolean z, boolean z2, int i3, int i4, int i5, int i6, String str, String str2, int i7, boolean z3) {
        this.mIsSamsungMdmnCall = false;
        this.mNumberPlus = "";
        this.mCmcDeviceId = "";
        this.mCmcCallTime = "";
        this.mRatInfo = 0;
        this.mCallId = i;
        this.mCallType = i2;
        this.mIsDowngradedVideoCall = z;
        this.mIsDowngradedAtEstablish = z2;
        this.mVoiceBearerState = i3;
        this.mVideoBearerState = i4;
        this.mVideoNGbrBearerState = i5;
        this.mErrorCode = i6;
        this.mErrorMessage = str;
        this.mPeerUri = str2;
        this.mDirection = i7;
        this.mIsConferenceCall = z3;
    }
}
