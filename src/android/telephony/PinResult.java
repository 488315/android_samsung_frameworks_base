package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class PinResult implements Parcelable {
    public static final int PIN_RESULT_TYPE_ABORTED = 3;
    public static final int PIN_RESULT_TYPE_FAILURE = 2;
    public static final int PIN_RESULT_TYPE_INCORRECT = 1;
    public static final int PIN_RESULT_TYPE_SUCCESS = 0;
    private final int mAttemptsRemaining;
    private final int mResult;
    private static final PinResult sFailedResult = new PinResult(2, -1);
    public static final Parcelable.Creator<PinResult> CREATOR = new Parcelable.Creator<PinResult>() { // from class: android.telephony.PinResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PinResult createFromParcel(Parcel parcel) {
            return new PinResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PinResult[] newArray(int i) {
            return new PinResult[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface PinResultType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getResult() {
        return this.mResult;
    }

    public int getAttemptsRemaining() {
        return this.mAttemptsRemaining;
    }

    public static PinResult getDefaultFailedResult() {
        return sFailedResult;
    }

    public PinResult(int i, int i2) {
        this.mResult = i;
        this.mAttemptsRemaining = i2;
    }

    private PinResult(Parcel parcel) {
        this.mResult = parcel.readInt();
        this.mAttemptsRemaining = parcel.readInt();
    }

    public String toString() {
        return "result: " + getResult() + ", attempts remaining: " + getAttemptsRemaining();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResult);
        parcel.writeInt(this.mAttemptsRemaining);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mAttemptsRemaining), Integer.valueOf(this.mResult));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PinResult pinResult = (PinResult) obj;
        return this.mResult == pinResult.mResult && this.mAttemptsRemaining == pinResult.mAttemptsRemaining;
    }
}
