package android.telephony.ims.feature;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.KeyProperties;
import android.util.SparseArray;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes4.dex */
public final class ConnectionFailureInfo implements Parcelable {
    public static final Parcelable.Creator<ConnectionFailureInfo> CREATOR;
    public static final int REASON_ACCESS_DENIED = 1;
    public static final int REASON_NAS_FAILURE = 2;
    public static final int REASON_NONE = 0;
    public static final int REASON_NO_SERVICE = 7;
    public static final int REASON_PDN_NOT_AVAILABLE = 8;
    public static final int REASON_RACH_FAILURE = 3;
    public static final int REASON_RF_BUSY = 9;
    public static final int REASON_RLC_FAILURE = 4;
    public static final int REASON_RRC_REJECT = 5;
    public static final int REASON_RRC_TIMEOUT = 6;
    public static final int REASON_UNSPECIFIED = 65535;
    private static final SparseArray<String> sReasonMap;
    private final int mCauseCode;
    private final int mReason;
    private final int mWaitTimeMillis;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FailureReason {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static {
        SparseArray<String> sparseArray = new SparseArray<>();
        sReasonMap = sparseArray;
        sparseArray.set(0, KeyProperties.DIGEST_NONE);
        sparseArray.set(1, "ACCESS_DENIED");
        sparseArray.set(2, "NAS_FAILURE");
        sparseArray.set(3, "RACH_FAILURE");
        sparseArray.set(4, "RLC_FAILURE");
        sparseArray.set(5, "RRC_REJECT");
        sparseArray.set(6, "RRC_TIMEOUT");
        sparseArray.set(7, "NO_SERVICE");
        sparseArray.set(8, "PDN_NOT_AVAILABLE");
        sparseArray.set(9, "RF_BUSY");
        sparseArray.set(65535, "UNSPECIFIED");
        CREATOR = new Parcelable.Creator<ConnectionFailureInfo>() { // from class: android.telephony.ims.feature.ConnectionFailureInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ConnectionFailureInfo createFromParcel(Parcel parcel) {
                return new ConnectionFailureInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ConnectionFailureInfo[] newArray(int i) {
                return new ConnectionFailureInfo[i];
            }
        };
    }

    private ConnectionFailureInfo(Parcel parcel) {
        this.mReason = parcel.readInt();
        this.mCauseCode = parcel.readInt();
        this.mWaitTimeMillis = parcel.readInt();
    }

    public ConnectionFailureInfo(int i, int i2, int i3) {
        this.mReason = i;
        this.mCauseCode = i2;
        this.mWaitTimeMillis = i3;
    }

    public int getReason() {
        return this.mReason;
    }

    public int getCauseCode() {
        return this.mCauseCode;
    }

    public int getWaitTimeMillis() {
        return this.mWaitTimeMillis;
    }

    public String toString() {
        return "ConnectionFailureInfo :: {" + this.mReason + " : " + sReasonMap.get(this.mReason, "UNKNOWN") + ", " + this.mCauseCode + ", " + this.mWaitTimeMillis + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mReason);
        parcel.writeInt(this.mCauseCode);
        parcel.writeInt(this.mWaitTimeMillis);
    }
}
