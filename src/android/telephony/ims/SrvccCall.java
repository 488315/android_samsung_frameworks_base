package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class SrvccCall implements Parcelable {
    public static final Parcelable.Creator<SrvccCall> CREATOR = new Parcelable.Creator<SrvccCall>() { // from class: android.telephony.ims.SrvccCall.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SrvccCall createFromParcel(Parcel parcel) {
            return new SrvccCall(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SrvccCall[] newArray(int i) {
            return new SrvccCall[i];
        }
    };
    private static final String TAG = "SrvccCall";
    private String mCallId;
    private int mCallState;
    private ImsCallProfile mImsCallProfile;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SrvccCall(Parcel parcel) {
        readFromParcel(parcel);
    }

    public SrvccCall(String str, int i, ImsCallProfile imsCallProfile) {
        if (str == null) {
            throw new IllegalArgumentException("callId is null");
        }
        if (imsCallProfile == null) {
            throw new IllegalArgumentException("imsCallProfile is null");
        }
        this.mCallId = str;
        this.mCallState = i;
        this.mImsCallProfile = imsCallProfile;
    }

    public ImsCallProfile getImsCallProfile() {
        return this.mImsCallProfile;
    }

    public String getCallId() {
        return this.mCallId;
    }

    public int getPreciseCallState() {
        return this.mCallState;
    }

    public String toString() {
        return "{ callId=" + this.mCallId + ", callState=" + this.mCallState + ", imsCallProfile=" + this.mImsCallProfile + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            SrvccCall srvccCall = (SrvccCall) obj;
            if (this.mImsCallProfile.equals(srvccCall.mImsCallProfile) && this.mCallId.equals(srvccCall.mCallId) && this.mCallState == srvccCall.mCallState) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (Objects.hash(this.mImsCallProfile, this.mCallId) * 31) + this.mCallState;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mCallId);
        parcel.writeInt(this.mCallState);
        parcel.writeParcelable(this.mImsCallProfile, 0);
    }

    private void readFromParcel(Parcel parcel) {
        this.mCallId = parcel.readString();
        this.mCallState = parcel.readInt();
        this.mImsCallProfile = (ImsCallProfile) parcel.readParcelable(ImsCallProfile.class.getClassLoader(), ImsCallProfile.class);
    }
}
