package android.security.keystore.recovery;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class WrappedApplicationKey implements Parcelable {
    public static final Parcelable.Creator<WrappedApplicationKey> CREATOR = new Parcelable.Creator<WrappedApplicationKey>() { // from class: android.security.keystore.recovery.WrappedApplicationKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WrappedApplicationKey createFromParcel(Parcel parcel) {
            return new WrappedApplicationKey(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WrappedApplicationKey[] newArray(int i) {
            return new WrappedApplicationKey[i];
        }
    };
    private String mAlias;
    private byte[] mEncryptedKeyMaterial;
    private byte[] mMetadata;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class Builder {
        private WrappedApplicationKey mInstance = new WrappedApplicationKey();

        public Builder setAlias(String str) {
            this.mInstance.mAlias = str;
            return this;
        }

        public Builder setEncryptedKeyMaterial(byte[] bArr) {
            this.mInstance.mEncryptedKeyMaterial = bArr;
            return this;
        }

        public Builder setMetadata(byte[] bArr) {
            this.mInstance.mMetadata = bArr;
            return this;
        }

        public WrappedApplicationKey build() {
            Objects.requireNonNull(this.mInstance.mAlias);
            Objects.requireNonNull(this.mInstance.mEncryptedKeyMaterial);
            return this.mInstance;
        }
    }

    private WrappedApplicationKey() {
    }

    @Deprecated
    public WrappedApplicationKey(String str, byte[] bArr) {
        this.mAlias = (String) Objects.requireNonNull(str);
        this.mEncryptedKeyMaterial = (byte[]) Objects.requireNonNull(bArr);
    }

    public String getAlias() {
        return this.mAlias;
    }

    public byte[] getEncryptedKeyMaterial() {
        return this.mEncryptedKeyMaterial;
    }

    public byte[] getMetadata() {
        return this.mMetadata;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mAlias);
        parcel.writeByteArray(this.mEncryptedKeyMaterial);
        parcel.writeByteArray(this.mMetadata);
    }

    protected WrappedApplicationKey(Parcel parcel) {
        this.mAlias = parcel.readString();
        this.mEncryptedKeyMaterial = parcel.createByteArray();
        this.mMetadata = parcel.createByteArray();
    }
}
