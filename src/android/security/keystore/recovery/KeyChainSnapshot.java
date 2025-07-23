package android.security.keystore.recovery;

import android.annotation.SystemApi;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.security.cert.CertPath;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class KeyChainSnapshot implements Parcelable {
    public static final Parcelable.Creator<KeyChainSnapshot> CREATOR = new Parcelable.Creator<KeyChainSnapshot>() { // from class: android.security.keystore.recovery.KeyChainSnapshot.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyChainSnapshot createFromParcel(Parcel parcel) {
            return new KeyChainSnapshot(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyChainSnapshot[] newArray(int i) {
            return new KeyChainSnapshot[i];
        }
    };
    private static final long DEFAULT_COUNTER_ID = 1;
    private static final int DEFAULT_MAX_ATTEMPTS = 10;
    private RecoveryCertPath mCertPath;
    private long mCounterId;
    private byte[] mEncryptedRecoveryKeyBlob;
    private List<WrappedApplicationKey> mEntryRecoveryData;
    private List<KeyChainProtectionParams> mKeyChainProtectionParams;
    private int mMaxAttempts;
    private byte[] mServerParams;
    private int mSnapshotVersion;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private KeyChainSnapshot() {
        this.mMaxAttempts = 10;
        this.mCounterId = 1L;
    }

    public int getSnapshotVersion() {
        return this.mSnapshotVersion;
    }

    public int getMaxAttempts() {
        return this.mMaxAttempts;
    }

    public long getCounterId() {
        return this.mCounterId;
    }

    public byte[] getServerParams() {
        return this.mServerParams;
    }

    public CertPath getTrustedHardwareCertPath() {
        try {
            return this.mCertPath.getCertPath();
        } catch (CertificateException e) {
            throw new BadParcelableException(e);
        }
    }

    public List<KeyChainProtectionParams> getKeyChainProtectionParams() {
        return this.mKeyChainProtectionParams;
    }

    public List<WrappedApplicationKey> getWrappedApplicationKeys() {
        return this.mEntryRecoveryData;
    }

    public byte[] getEncryptedRecoveryKeyBlob() {
        return this.mEncryptedRecoveryKeyBlob;
    }

    public static class Builder {
        private KeyChainSnapshot mInstance = new KeyChainSnapshot();

        public Builder setSnapshotVersion(int i) {
            this.mInstance.mSnapshotVersion = i;
            return this;
        }

        public Builder setMaxAttempts(int i) {
            this.mInstance.mMaxAttempts = i;
            return this;
        }

        public Builder setCounterId(long j) {
            this.mInstance.mCounterId = j;
            return this;
        }

        public Builder setServerParams(byte[] bArr) {
            this.mInstance.mServerParams = bArr;
            return this;
        }

        public Builder setTrustedHardwareCertPath(CertPath certPath) throws CertificateException {
            this.mInstance.mCertPath = RecoveryCertPath.createRecoveryCertPath(certPath);
            return this;
        }

        public Builder setKeyChainProtectionParams(List<KeyChainProtectionParams> list) {
            this.mInstance.mKeyChainProtectionParams = list;
            return this;
        }

        public Builder setWrappedApplicationKeys(List<WrappedApplicationKey> list) {
            this.mInstance.mEntryRecoveryData = list;
            return this;
        }

        public Builder setEncryptedRecoveryKeyBlob(byte[] bArr) {
            this.mInstance.mEncryptedRecoveryKeyBlob = bArr;
            return this;
        }

        public KeyChainSnapshot build() {
            Preconditions.checkCollectionElementsNotNull(this.mInstance.mKeyChainProtectionParams, "keyChainProtectionParams");
            Preconditions.checkCollectionElementsNotNull(this.mInstance.mEntryRecoveryData, "entryRecoveryData");
            Objects.requireNonNull(this.mInstance.mEncryptedRecoveryKeyBlob);
            Objects.requireNonNull(this.mInstance.mServerParams);
            Objects.requireNonNull(this.mInstance.mCertPath);
            return this.mInstance;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSnapshotVersion);
        parcel.writeTypedList(this.mKeyChainProtectionParams);
        parcel.writeByteArray(this.mEncryptedRecoveryKeyBlob);
        parcel.writeTypedList(this.mEntryRecoveryData);
        parcel.writeInt(this.mMaxAttempts);
        parcel.writeLong(this.mCounterId);
        parcel.writeByteArray(this.mServerParams);
        parcel.writeTypedObject(this.mCertPath, 0);
    }

    protected KeyChainSnapshot(Parcel parcel) {
        this.mMaxAttempts = 10;
        this.mCounterId = 1L;
        this.mSnapshotVersion = parcel.readInt();
        this.mKeyChainProtectionParams = parcel.createTypedArrayList(KeyChainProtectionParams.CREATOR);
        this.mEncryptedRecoveryKeyBlob = parcel.createByteArray();
        this.mEntryRecoveryData = parcel.createTypedArrayList(WrappedApplicationKey.CREATOR);
        this.mMaxAttempts = parcel.readInt();
        this.mCounterId = parcel.readLong();
        this.mServerParams = parcel.createByteArray();
        this.mCertPath = (RecoveryCertPath) parcel.readTypedObject(RecoveryCertPath.CREATOR);
    }
}
