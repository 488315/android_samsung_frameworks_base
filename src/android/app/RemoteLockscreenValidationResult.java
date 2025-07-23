package android.app;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes.dex */
public final class RemoteLockscreenValidationResult implements Parcelable {
    public static final Parcelable.Creator<RemoteLockscreenValidationResult> CREATOR = new Parcelable.Creator<RemoteLockscreenValidationResult>() { // from class: android.app.RemoteLockscreenValidationResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteLockscreenValidationResult createFromParcel(Parcel parcel) {
            return new RemoteLockscreenValidationResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteLockscreenValidationResult[] newArray(int i) {
            return new RemoteLockscreenValidationResult[i];
        }
    };
    public static final int RESULT_GUESS_INVALID = 2;
    public static final int RESULT_GUESS_VALID = 1;
    public static final int RESULT_LOCKOUT = 3;
    public static final int RESULT_NO_REMAINING_ATTEMPTS = 4;
    public static final int RESULT_SESSION_EXPIRED = 5;
    private int mResultCode;
    private long mTimeoutMillis;

    @Retention(RetentionPolicy.SOURCE)
    @interface ResultCode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private RemoteLockscreenValidationResult mInstance = new RemoteLockscreenValidationResult();

        public Builder setResultCode(int i) {
            this.mInstance.mResultCode = i;
            return this;
        }

        public Builder setTimeoutMillis(long j) {
            this.mInstance.mTimeoutMillis = j;
            return this;
        }

        public RemoteLockscreenValidationResult build() {
            if (this.mInstance.mResultCode == 0) {
                throw new IllegalStateException("Result code must be set");
            }
            return this.mInstance;
        }
    }

    public int getResultCode() {
        return this.mResultCode;
    }

    public long getTimeoutMillis() {
        return this.mTimeoutMillis;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResultCode);
        parcel.writeLong(this.mTimeoutMillis);
    }

    private RemoteLockscreenValidationResult() {
    }

    private RemoteLockscreenValidationResult(Parcel parcel) {
        this.mResultCode = parcel.readInt();
        this.mTimeoutMillis = parcel.readLong();
    }
}
