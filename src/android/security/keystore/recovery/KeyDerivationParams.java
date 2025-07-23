package android.security.keystore.recovery;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class KeyDerivationParams implements Parcelable {
    public static final int ALGORITHM_SCRYPT = 2;
    public static final int ALGORITHM_SHA256 = 1;
    public static final Parcelable.Creator<KeyDerivationParams> CREATOR = new Parcelable.Creator<KeyDerivationParams>() { // from class: android.security.keystore.recovery.KeyDerivationParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyDerivationParams createFromParcel(Parcel parcel) {
            return new KeyDerivationParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyDerivationParams[] newArray(int i) {
            return new KeyDerivationParams[i];
        }
    };
    private final int mAlgorithm;
    private final int mMemoryDifficulty;
    private final byte[] mSalt;

    @Retention(RetentionPolicy.SOURCE)
    public @interface KeyDerivationAlgorithm {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static KeyDerivationParams createSha256Params(byte[] bArr) {
        return new KeyDerivationParams(1, bArr);
    }

    public static KeyDerivationParams createScryptParams(byte[] bArr, int i) {
        return new KeyDerivationParams(2, bArr, i);
    }

    private KeyDerivationParams(int i, byte[] bArr) {
        this(i, bArr, -1);
    }

    private KeyDerivationParams(int i, byte[] bArr, int i2) {
        this.mAlgorithm = i;
        this.mSalt = (byte[]) Objects.requireNonNull(bArr);
        this.mMemoryDifficulty = i2;
    }

    public int getAlgorithm() {
        return this.mAlgorithm;
    }

    public byte[] getSalt() {
        return this.mSalt;
    }

    public int getMemoryDifficulty() {
        return this.mMemoryDifficulty;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mAlgorithm);
        parcel.writeByteArray(this.mSalt);
        parcel.writeInt(this.mMemoryDifficulty);
    }

    protected KeyDerivationParams(Parcel parcel) {
        this.mAlgorithm = parcel.readInt();
        this.mSalt = parcel.createByteArray();
        this.mMemoryDifficulty = parcel.readInt();
    }
}
