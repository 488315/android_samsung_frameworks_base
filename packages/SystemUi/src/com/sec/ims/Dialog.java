package com.sec.ims;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import com.android.systemui.appops.AppOpItem$$ExternalSyntheticOutline0;
import com.sec.ims.util.IMSLog;
import com.sec.ims.util.ImsUri;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class Dialog implements Parcelable {
    public static final int CALL_STATE_ACTIVE = 1;
    public static final int CALL_STATE_HOLD = 2;
    public static final int CALL_STATE_INVALID = 0;
    public static final Parcelable.Creator<Dialog> CREATOR = new Parcelable.Creator<Dialog>() { // from class: com.sec.ims.Dialog.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Dialog createFromParcel(Parcel parcel) {
            return new Dialog(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Dialog[] newArray(int i) {
            return new Dialog[i];
        }
    };
    public static final int DIALOG_STATE_CONFIRMED = 2;
    public static final int DIALOG_STATE_REJECTED = 4;
    public static final int DIALOG_STATE_TERMINATED = 3;
    public static final int DIALOG_STATE_TRYING = 1;
    public static final int DIALOG_STATE_UNKNOWN = 0;
    public static final int DIRECTION_INITIATOR = 0;
    public static final int DIRECTION_RECIPIENT = 1;
    public static final int MEDIA_DIRECTION_INACTIVE = 0;
    public static final int MEDIA_DIRECTION_RECVONLY = 2;
    public static final int MEDIA_DIRECTION_SENDONLY = 1;
    public static final int MEDIA_DIRECTION_SENDRECV = 3;
    public static final int STATE_CONNECTED = 1;
    public static final int STATE_DIALING = 4;
    public static final int STATE_DISCONNECTED = 2;
    public static final int STATE_REJECTED = 3;
    public static final int STATE_RINGING = 0;
    private int mAudioDirection;
    private int mCallSlot;
    private int mCallState;
    private int mCallType;
    private String mDeviceId;
    private String mDialogId;
    private int mDirection;
    private boolean mIsExclusive;
    private boolean mIsPullAvailable;
    private boolean mIsVideoPortZero;
    private String mLocalDispName;
    private String mLocalUri;
    private String mMdmnExtNumber;
    private String mRemoteDispName;
    private String mRemoteUri;
    private String mSessionDescription;
    private String mSipCallId;
    private String mSipLocalTag;
    private String mSipRemoteTag;
    private int mState;
    private int mVideoDirection;

    public /* synthetic */ Dialog(Parcel parcel, int i) {
        this(parcel);
    }

    private String convertDirection(int i) {
        return i != 1 ? "initiator" : "recipient";
    }

    private String convertMediaDirection(int i) {
        return i != 1 ? i != 2 ? i != 3 ? "inactive" : "sendrecv" : "recvonly" : "sendonly";
    }

    private String convertState(int i) {
        return i != 1 ? i != 2 ? i != 4 ? "terminated" : "rejected" : "confirmed" : "trying";
    }

    private String getMsisdn(String str) {
        ImsUri parse = ImsUri.parse(str);
        if (parse == null) {
            return "";
        }
        String msisdn = parse.getMsisdn();
        return msisdn == null ? parse.getUser() : msisdn;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getAudioDirection() {
        return this.mAudioDirection;
    }

    public int getCallSlot() {
        return this.mCallSlot;
    }

    public int getCallState() {
        return this.mCallState;
    }

    public int getCallType() {
        return this.mCallType;
    }

    public String getDeviceId() {
        return this.mDeviceId;
    }

    public String getDialogId() {
        return this.mDialogId;
    }

    public int getDirection() {
        return this.mDirection;
    }

    public String getLocalDispName() {
        return this.mLocalDispName;
    }

    public String getLocalNumber() {
        return getMsisdn(this.mLocalUri);
    }

    public String getLocalUri() {
        return this.mLocalUri;
    }

    public String getMdmnExtNumber() {
        return this.mMdmnExtNumber;
    }

    public String getRemoteDispName() {
        return this.mRemoteDispName;
    }

    public String getRemoteNumber() {
        return getMsisdn(this.mRemoteUri);
    }

    public String getRemoteUri() {
        return this.mRemoteUri;
    }

    public String getSessionDescription() {
        return this.mSessionDescription;
    }

    public String getSipCallId() {
        return this.mSipCallId;
    }

    public String getSipLocalTag() {
        return this.mSipLocalTag;
    }

    public String getSipRemoteTag() {
        return this.mSipRemoteTag;
    }

    public int getState() {
        return this.mState;
    }

    public int getTelephonyState() {
        int i = this.mState;
        if (i == 0) {
            return 1;
        }
        return (i == 1 || i == 4) ? 2 : 0;
    }

    public int getVideoDirection() {
        return this.mVideoDirection;
    }

    public boolean isExclusive() {
        return this.mIsExclusive;
    }

    public boolean isHeld() {
        return this.mCallState == 2;
    }

    public boolean isPullAvailable() {
        return this.mIsPullAvailable;
    }

    public boolean isVideoPortZero() {
        return this.mIsVideoPortZero;
    }

    public void setCallType(int i) {
        this.mCallType = i;
    }

    public void setIsExclusive(boolean z) {
        this.mIsExclusive = z;
    }

    public void setIsPullAvailable(boolean z) {
        this.mIsPullAvailable = z;
    }

    public void setMdmnExtNumber(String str) {
        this.mMdmnExtNumber = str;
    }

    public void setSessionDescription(String str) {
        this.mSessionDescription = str;
    }

    public void setState(int i) {
        this.mState = i;
    }

    public String toString() {
        return "Dialog [mDialogId=" + this.mDialogId + ", mSipCallId=" + this.mSipCallId + ", mSipLocalTag=" + this.mSipLocalTag + ", mSipRemoteTag=" + this.mSipRemoteTag + ", mLocalUri=" + this.mLocalUri + ", mRemoteUri=" + IMSLog.checker(this.mRemoteUri) + ", mLocalDispName=" + this.mLocalDispName + ", mRemoteDispName=" + this.mRemoteDispName + ", mState=" + this.mState + ", mDirection=" + this.mDirection + ", mCallType=" + this.mCallType + ", mCallState=" + this.mCallState + ", mCallSlot=" + this.mCallSlot + ", mAudioDirection=" + this.mAudioDirection + ", mVideoDirection=" + this.mVideoDirection + ", mIsExclusive=" + this.mIsExclusive + ", mIsPullAvailable=" + this.mIsPullAvailable + ", mIsVideoPortZero=" + this.mIsVideoPortZero + ", mSessionDescription=" + IMSLog.checker(this.mSessionDescription) + ", mMdmnExtNumber=" + IMSLog.checker(this.mMdmnExtNumber) + "]";
    }

    public String toXmlString() {
        String m16m;
        String m16m2;
        if (TextUtils.isEmpty(this.mLocalDispName)) {
            m16m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("\t\t\t<identity>"), this.mDeviceId, "</identity>\n");
        } else {
            StringBuilder sb = new StringBuilder("\t\t\t<identity display-name=\"");
            sb.append(this.mLocalDispName);
            sb.append("\">");
            m16m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, this.mDeviceId, "</identity>\n");
        }
        if (TextUtils.isEmpty(this.mRemoteDispName)) {
            m16m2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("\t\t\t<identity>"), this.mRemoteUri, "</identity>\n");
        } else {
            StringBuilder sb2 = new StringBuilder("\t\t\t<identity display-name=\"");
            sb2.append(this.mRemoteDispName);
            sb2.append("\">");
            m16m2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb2, this.mRemoteUri, "</identity>\n");
        }
        String m29m = this.mAudioDirection > 0 ? PathParser$$ExternalSyntheticOutline0.m29m("\t\t\t<mediaAttributes>\n", this.mAudioDirection > 0 ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("\t\t\t\t<mediaType>audio</mediaType>\n\t\t\t\t<mediaDirection>"), convertMediaDirection(this.mAudioDirection), "</mediaDirection>\n") : "", "\t\t\t</mediaAttributes>\n") : "";
        String m29m2 = this.mVideoDirection > 0 ? PathParser$$ExternalSyntheticOutline0.m29m("\t\t\t<mediaAttributes>\n", this.mVideoDirection > 0 ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("\t\t\t\t<mediaType>video</mediaType>\n\t\t\t\t<mediaDirection>"), convertMediaDirection(this.mVideoDirection), "</mediaDirection>\n") : "", "\t\t\t</mediaAttributes>\n") : "";
        String str = this.mCallState != 2 ? "yes" : "no";
        StringBuilder sb3 = new StringBuilder("\t<dialog id=\"");
        sb3.append(this.mSipCallId);
        sb3.append("\"\n\t\tcall-id=\"");
        sb3.append(this.mSipCallId);
        sb3.append("\"\n\t\tlocal-tag=\"");
        sb3.append(this.mSipLocalTag);
        sb3.append("\" remote-tag=\"");
        sb3.append(this.mSipRemoteTag);
        sb3.append("\" direction=\"");
        sb3.append(convertDirection(this.mDirection));
        sb3.append("\">\n\t\t<sa:exclusive>");
        sb3.append(this.mIsExclusive);
        sb3.append("</sa:exclusive>\n\t\t<state>");
        AppOpItem$$ExternalSyntheticOutline0.m97m(sb3, convertState(this.mState), "</state>\n\t\t<local>\n", m16m, "\t\t\t<target uri=\"");
        AppOpItem$$ExternalSyntheticOutline0.m97m(sb3, this.mLocalUri, "\">\n\t\t\t\t<param pname=\"+sip.rendering\" pval=\"", str, "\"/>\n\t\t\t</target>\n");
        AppOpItem$$ExternalSyntheticOutline0.m97m(sb3, m29m, m29m2, "\t\t</local>\n\t\t<remote>\n", m16m2);
        sb3.append("\t\t</remote>\n\t\t<calltype>");
        sb3.append(this.mCallType);
        sb3.append("</calltype>\n\t\t<callslot>");
        sb3.append(this.mCallSlot);
        sb3.append("</callslot>\n\t\t<session-description>");
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb3, this.mSessionDescription, "</session-description>\n\t</dialog>\n");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            return;
        }
        parcel.writeString(this.mDialogId);
        parcel.writeString(this.mDeviceId);
        parcel.writeString(this.mSipCallId);
        parcel.writeString(this.mSipLocalTag);
        parcel.writeString(this.mSipRemoteTag);
        parcel.writeString(this.mLocalUri);
        parcel.writeString(this.mRemoteUri);
        parcel.writeString(this.mLocalDispName);
        parcel.writeString(this.mRemoteDispName);
        parcel.writeString(this.mMdmnExtNumber);
        parcel.writeString(this.mSessionDescription);
        parcel.writeInt(this.mState);
        parcel.writeInt(this.mDirection);
        parcel.writeInt(this.mCallType);
        parcel.writeInt(this.mCallState);
        parcel.writeInt(this.mCallSlot);
        parcel.writeInt(this.mAudioDirection);
        parcel.writeInt(this.mVideoDirection);
        parcel.writeInt(this.mIsExclusive ? 1 : 0);
        parcel.writeInt(this.mIsPullAvailable ? 1 : 0);
        parcel.writeInt(this.mIsVideoPortZero ? 1 : 0);
    }

    public Dialog(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z, boolean z2) {
        this.mDialogId = str;
        this.mDeviceId = str2;
        this.mSipCallId = str3;
        this.mSipLocalTag = str4;
        this.mSipRemoteTag = str5;
        this.mLocalUri = str6;
        this.mRemoteUri = str7;
        this.mLocalDispName = str8;
        this.mRemoteDispName = str9;
        this.mSessionDescription = str10;
        this.mState = i;
        this.mDirection = i2;
        this.mCallType = i3;
        this.mCallState = i4;
        this.mCallSlot = i5;
        this.mAudioDirection = i6;
        this.mVideoDirection = i7;
        this.mIsExclusive = z;
        this.mIsPullAvailable = !z;
        this.mIsVideoPortZero = z2;
        this.mMdmnExtNumber = "";
    }

    private Dialog(Parcel parcel) {
        this.mDialogId = parcel.readString();
        this.mDeviceId = parcel.readString();
        this.mSipCallId = parcel.readString();
        this.mSipLocalTag = parcel.readString();
        this.mSipRemoteTag = parcel.readString();
        this.mLocalUri = parcel.readString();
        this.mRemoteUri = parcel.readString();
        this.mLocalDispName = parcel.readString();
        this.mRemoteDispName = parcel.readString();
        this.mMdmnExtNumber = parcel.readString();
        this.mSessionDescription = parcel.readString();
        this.mState = parcel.readInt();
        this.mDirection = parcel.readInt();
        this.mCallType = parcel.readInt();
        this.mCallState = parcel.readInt();
        this.mCallSlot = parcel.readInt();
        this.mAudioDirection = parcel.readInt();
        this.mVideoDirection = parcel.readInt();
        this.mIsExclusive = parcel.readInt() == 1;
        this.mIsPullAvailable = parcel.readInt() == 1;
        this.mIsVideoPortZero = parcel.readInt() == 1;
    }
}
