package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
@Deprecated
/* loaded from: classes4.dex */
public final class CallAttributes implements Parcelable {
    public static final Parcelable.Creator<CallAttributes> CREATOR = new Parcelable.Creator() { // from class: android.telephony.CallAttributes.1
        @Override // android.os.Parcelable.Creator
        public CallAttributes createFromParcel(Parcel parcel) {
            return new CallAttributes(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public CallAttributes[] newArray(int i) {
            return new CallAttributes[i];
        }
    };
    private CallQuality mCallQuality;
    private int mNetworkType;
    private PreciseCallState mPreciseCallState;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CallAttributes(PreciseCallState preciseCallState, int i, CallQuality callQuality) {
        this.mPreciseCallState = preciseCallState;
        this.mNetworkType = i;
        this.mCallQuality = callQuality;
    }

    public String toString() {
        return "mPreciseCallState=" + this.mPreciseCallState + " mNetworkType=" + this.mNetworkType + " mCallQuality=" + this.mCallQuality;
    }

    private CallAttributes(Parcel parcel) {
        this.mPreciseCallState = (PreciseCallState) parcel.readParcelable(PreciseCallState.class.getClassLoader(), PreciseCallState.class);
        this.mNetworkType = parcel.readInt();
        this.mCallQuality = (CallQuality) parcel.readParcelable(CallQuality.class.getClassLoader(), CallQuality.class);
    }

    public PreciseCallState getPreciseCallState() {
        return this.mPreciseCallState;
    }

    public int getNetworkType() {
        return this.mNetworkType;
    }

    public CallQuality getCallQuality() {
        return this.mCallQuality;
    }

    public int hashCode() {
        return Objects.hash(this.mPreciseCallState, Integer.valueOf(this.mNetworkType), this.mCallQuality);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof CallAttributes) && hashCode() == obj.hashCode()) {
            if (this == obj) {
                return true;
            }
            CallAttributes callAttributes = (CallAttributes) obj;
            if (Objects.equals(this.mPreciseCallState, callAttributes.mPreciseCallState) && this.mNetworkType == callAttributes.mNetworkType && Objects.equals(this.mCallQuality, callAttributes.mCallQuality)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mPreciseCallState, i);
        parcel.writeInt(this.mNetworkType);
        parcel.writeParcelable(this.mCallQuality, i);
    }
}
