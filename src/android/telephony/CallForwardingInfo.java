package android.telephony;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class CallForwardingInfo implements Parcelable {
    public static final Parcelable.Creator<CallForwardingInfo> CREATOR = new Parcelable.Creator<CallForwardingInfo>() { // from class: android.telephony.CallForwardingInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallForwardingInfo createFromParcel(Parcel parcel) {
            return new CallForwardingInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallForwardingInfo[] newArray(int i) {
            return new CallForwardingInfo[i];
        }
    };
    public static final int REASON_ALL = 4;
    public static final int REASON_ALL_CONDITIONAL = 5;
    public static final int REASON_BUSY = 1;
    public static final int REASON_NOT_REACHABLE = 3;
    public static final int REASON_NO_REPLY = 2;
    public static final int REASON_UNCONDITIONAL = 0;
    private static final String TAG = "CallForwardingInfo";
    private boolean mEnabled;
    private String mNumber;
    private int mReason;
    private int mTimeSeconds;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CallForwardingReason {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CallForwardingInfo(boolean z, int i, String str, int i2) {
        this.mEnabled = z;
        this.mReason = i;
        this.mNumber = str;
        this.mTimeSeconds = i2;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public int getReason() {
        return this.mReason;
    }

    public String getNumber() {
        return this.mNumber;
    }

    public int getTimeoutSeconds() {
        return this.mTimeSeconds;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mNumber);
        parcel.writeBoolean(this.mEnabled);
        parcel.writeInt(this.mReason);
        parcel.writeInt(this.mTimeSeconds);
    }

    private CallForwardingInfo(Parcel parcel) {
        this.mNumber = parcel.readString();
        this.mEnabled = parcel.readBoolean();
        this.mReason = parcel.readInt();
        this.mTimeSeconds = parcel.readInt();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CallForwardingInfo)) {
            return false;
        }
        CallForwardingInfo callForwardingInfo = (CallForwardingInfo) obj;
        return this.mEnabled == callForwardingInfo.mEnabled && this.mNumber == callForwardingInfo.mNumber && this.mReason == callForwardingInfo.mReason && this.mTimeSeconds == callForwardingInfo.mTimeSeconds;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mEnabled), this.mNumber, Integer.valueOf(this.mReason), Integer.valueOf(this.mTimeSeconds));
    }

    public String toString() {
        return "[CallForwardingInfo: enabled=" + this.mEnabled + ", reason= " + this.mReason + ", timeSec= " + this.mTimeSeconds + " seconds, number=" + com.android.telephony.Rlog.pii(TAG, this.mNumber) + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
