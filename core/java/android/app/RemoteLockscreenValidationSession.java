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
        public RemoteLockscreenValidationSession createFromParcel(Parcel source) {
            return new RemoteLockscreenValidationSession(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteLockscreenValidationSession[] newArray(int size) {
            return new RemoteLockscreenValidationSession[size];
        }
    };
    private int mLockType;
    private int mRemainingAttempts;
    private byte[] mSourcePublicKey;

    public static final class Builder {
        private RemoteLockscreenValidationSession mInstance = new RemoteLockscreenValidationSession();

        public Builder setLockType(int lockType) {
            this.mInstance.mLockType = lockType;
            return this;
        }

        public Builder setSourcePublicKey(byte[] publicKey) {
            this.mInstance.mSourcePublicKey = publicKey;
            return this;
        }

        public Builder setRemainingAttempts(int remainingAttempts) {
            this.mInstance.mRemainingAttempts = remainingAttempts;
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
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mLockType);
        out.writeByteArray(this.mSourcePublicKey);
        out.writeInt(this.mRemainingAttempts);
    }

    private RemoteLockscreenValidationSession() {
    }

    private RemoteLockscreenValidationSession(Parcel in) {
        this.mLockType = in.readInt();
        this.mSourcePublicKey = in.createByteArray();
        this.mRemainingAttempts = in.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
