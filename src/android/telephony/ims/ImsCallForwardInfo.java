package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.SemTelephonyUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes4.dex */
public final class ImsCallForwardInfo implements Parcelable {
    public static final int CDIV_CF_REASON_ALL = 4;
    public static final int CDIV_CF_REASON_ALL_CONDITIONAL = 5;
    public static final int CDIV_CF_REASON_BUSY = 1;
    public static final int CDIV_CF_REASON_NOT_LOGGED_IN = 6;
    public static final int CDIV_CF_REASON_NOT_REACHABLE = 3;
    public static final int CDIV_CF_REASON_NO_REPLY = 2;
    public static final int CDIV_CF_REASON_UNCONDITIONAL = 0;
    public static final Parcelable.Creator<ImsCallForwardInfo> CREATOR = new Parcelable.Creator<ImsCallForwardInfo>() { // from class: android.telephony.ims.ImsCallForwardInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsCallForwardInfo createFromParcel(Parcel parcel) {
            return new ImsCallForwardInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsCallForwardInfo[] newArray(int i) {
            return new ImsCallForwardInfo[i];
        }
    };
    public static final int STATUS_ACTIVE = 1;
    public static final int STATUS_NOT_ACTIVE = 0;
    public static final int TYPE_OF_ADDRESS_INTERNATIONAL = 145;
    public static final int TYPE_OF_ADDRESS_UNKNOWN = 129;
    public int mCondition;
    public String mNumber;
    public int mServiceClass;
    public int mStatus;
    public int mTimeSeconds;
    public int mToA;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CallForwardReasons {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CallForwardStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TypeOfAddress {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ImsCallForwardInfo() {
    }

    public ImsCallForwardInfo(int i, int i2, int i3, int i4, String str, int i5) {
        this.mCondition = i;
        this.mStatus = i2;
        this.mToA = i3;
        this.mServiceClass = i4;
        this.mNumber = str;
        this.mTimeSeconds = i5;
    }

    public ImsCallForwardInfo(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCondition);
        parcel.writeInt(this.mStatus);
        parcel.writeInt(this.mToA);
        parcel.writeString(this.mNumber);
        parcel.writeInt(this.mTimeSeconds);
        parcel.writeInt(this.mServiceClass);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(", Condition: ");
        sb.append(this.mCondition);
        sb.append(", Status: ");
        sb.append(this.mStatus == 0 ? "disabled" : "enabled");
        sb.append(", ToA: ");
        sb.append(this.mToA);
        sb.append(", Service Class: ");
        sb.append(this.mServiceClass);
        sb.append(", Number=");
        sb.append(SemTelephonyUtils.maskPii(this.mNumber));
        sb.append(", Time (seconds): ");
        sb.append(this.mTimeSeconds);
        return sb.toString();
    }

    private void readFromParcel(Parcel parcel) {
        this.mCondition = parcel.readInt();
        this.mStatus = parcel.readInt();
        this.mToA = parcel.readInt();
        this.mNumber = parcel.readString();
        this.mTimeSeconds = parcel.readInt();
        this.mServiceClass = parcel.readInt();
    }

    public int getCondition() {
        return this.mCondition;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public int getToA() {
        return this.mToA;
    }

    public int getServiceClass() {
        return this.mServiceClass;
    }

    public String getNumber() {
        return this.mNumber;
    }

    public int getTimeSeconds() {
        return this.mTimeSeconds;
    }
}
