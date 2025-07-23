package android.service.trust;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class GrantTrustResult implements Parcelable {
    public static final Parcelable.Creator<GrantTrustResult> CREATOR = new Parcelable.Creator<GrantTrustResult>() { // from class: android.service.trust.GrantTrustResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GrantTrustResult[] newArray(int i) {
            return new GrantTrustResult[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GrantTrustResult createFromParcel(Parcel parcel) {
            return new GrantTrustResult(parcel);
        }
    };
    public static final int STATUS_UNKNOWN = 0;
    public static final int STATUS_UNLOCKED_BY_GRANT = 1;
    private int mStatus;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static String statusToString(int i) {
        if (i == 0) {
            return "STATUS_UNKNOWN";
        }
        if (i == 1) {
            return "STATUS_UNLOCKED_BY_GRANT";
        }
        return Integer.toHexString(i);
    }

    public GrantTrustResult(int i) {
        this.mStatus = i;
        if (i == 0 || i == 1) {
            return;
        }
        throw new IllegalArgumentException("status was " + this.mStatus + " but must be one of: STATUS_UNKNOWN(0), STATUS_UNLOCKED_BY_GRANT(1)");
    }

    public int getStatus() {
        return this.mStatus;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mStatus);
    }

    GrantTrustResult(Parcel parcel) {
        int readInt = parcel.readInt();
        this.mStatus = readInt;
        if (readInt == 0 || readInt == 1) {
            return;
        }
        throw new IllegalArgumentException("status was " + this.mStatus + " but must be one of: STATUS_UNKNOWN(0), STATUS_UNLOCKED_BY_GRANT(1)");
    }
}
