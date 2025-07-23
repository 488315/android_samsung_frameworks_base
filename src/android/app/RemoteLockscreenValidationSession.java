package android.app;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class RemoteLockscreenValidationSession implements Parcelable {
    public static final Parcelable.Creator<RemoteLockscreenValidationSession> CREATOR = new Parcelable.Creator<RemoteLockscreenValidationSession>() { // from class: android.app.RemoteLockscreenValidationSession.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteLockscreenValidationSession createFromParcel(Parcel parcel) {
            return new RemoteLockscreenValidationSession(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteLockscreenValidationSession[] newArray(int i) {
            return new RemoteLockscreenValidationSession[i];
        }
    };
    private int mLockType;
    private int mRemainingAttempts;
    private byte[] mSourcePublicKey;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private RemoteLockscreenValidationSession mInstance = new RemoteLockscreenValidationSession();

        public Builder setLockType(int i) {
            this.mInstance.mLockType = i;
            return this;
        }

        public Builder setSourcePublicKey(byte[] bArr) {
            this.mInstance.mSourcePublicKey = bArr;
            return this;
        }

        public Builder setRemainingAttempts(int i) {
            this.mInstance.mRemainingAttempts = i;
            return this;
        }

        public RemoteLockscreenValidationSession build() {
            Objects.requireNonNull(this.mInstance.mSourcePublicKey);
            return this.mInstance;
        }
    }

    public int getLockType() {
        return this.mLockType;
    }

    public byte[] getSourcePublicKey() {
        return this.mSourcePublicKey;
    }

    public int getRemainingAttempts() {
        return this.mRemainingAttempts;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mLockType);
        parcel.writeByteArray(this.mSourcePublicKey);
        parcel.writeInt(this.mRemainingAttempts);
    }

    private RemoteLockscreenValidationSession() {
    }

    private RemoteLockscreenValidationSession(Parcel parcel) {
        this.mLockType = parcel.readInt();
        this.mSourcePublicKey = parcel.createByteArray();
        this.mRemainingAttempts = parcel.readInt();
    }
}
