package android.security.keystore;

import android.os.Parcel;
import android.os.Parcelable;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes3.dex */
public final class ParcelableKeyGenParameterSpec implements Parcelable {
    private static final int ALGORITHM_PARAMETER_SPEC_EC = 3;
    private static final int ALGORITHM_PARAMETER_SPEC_NONE = 1;
    private static final int ALGORITHM_PARAMETER_SPEC_RSA = 2;
    public static final Parcelable.Creator<ParcelableKeyGenParameterSpec> CREATOR = new Parcelable.Creator<ParcelableKeyGenParameterSpec>() { // from class: android.security.keystore.ParcelableKeyGenParameterSpec.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableKeyGenParameterSpec createFromParcel(Parcel parcel) {
            return new ParcelableKeyGenParameterSpec(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableKeyGenParameterSpec[] newArray(int i) {
            return new ParcelableKeyGenParameterSpec[i];
        }
    };
    private final KeyGenParameterSpec mSpec;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ParcelableKeyGenParameterSpec(KeyGenParameterSpec keyGenParameterSpec) {
        this.mSpec = keyGenParameterSpec;
    }

    private static void writeOptionalDate(Parcel parcel, Date date) {
        if (date != null) {
            parcel.writeBoolean(true);
            parcel.writeLong(date.getTime());
        } else {
            parcel.writeBoolean(false);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mSpec.getKeystoreAlias());
        parcel.writeInt(this.mSpec.getPurposes());
        parcel.writeInt(this.mSpec.getNamespace());
        parcel.writeInt(this.mSpec.getKeySize());
        AlgorithmParameterSpec algorithmParameterSpec = this.mSpec.getAlgorithmParameterSpec();
        if (algorithmParameterSpec == null) {
            parcel.writeInt(1);
        } else if (algorithmParameterSpec instanceof RSAKeyGenParameterSpec) {
            RSAKeyGenParameterSpec rSAKeyGenParameterSpec = (RSAKeyGenParameterSpec) algorithmParameterSpec;
            parcel.writeInt(2);
            parcel.writeInt(rSAKeyGenParameterSpec.getKeysize());
            parcel.writeByteArray(rSAKeyGenParameterSpec.getPublicExponent().toByteArray());
        } else if (algorithmParameterSpec instanceof ECGenParameterSpec) {
            parcel.writeInt(3);
            parcel.writeString(((ECGenParameterSpec) algorithmParameterSpec).getName());
        } else {
            throw new IllegalArgumentException(String.format("Unknown algorithm parameter spec: %s", algorithmParameterSpec.getClass()));
        }
        parcel.writeByteArray(this.mSpec.getCertificateSubject().getEncoded());
        parcel.writeByteArray(this.mSpec.getCertificateSerialNumber().toByteArray());
        parcel.writeLong(this.mSpec.getCertificateNotBefore().getTime());
        parcel.writeLong(this.mSpec.getCertificateNotAfter().getTime());
        writeOptionalDate(parcel, this.mSpec.getKeyValidityStart());
        writeOptionalDate(parcel, this.mSpec.getKeyValidityForOriginationEnd());
        writeOptionalDate(parcel, this.mSpec.getKeyValidityForConsumptionEnd());
        if (this.mSpec.isDigestsSpecified()) {
            parcel.writeStringArray(this.mSpec.getDigests());
        } else {
            parcel.writeStringArray(null);
        }
        if (this.mSpec.isMgf1DigestsSpecified()) {
            parcel.writeStringList(List.copyOf(this.mSpec.getMgf1Digests()));
        } else {
            parcel.writeStringList(null);
        }
        parcel.writeStringArray(this.mSpec.getEncryptionPaddings());
        parcel.writeStringArray(this.mSpec.getSignaturePaddings());
        parcel.writeStringArray(this.mSpec.getBlockModes());
        parcel.writeBoolean(this.mSpec.isRandomizedEncryptionRequired());
        parcel.writeBoolean(this.mSpec.isUserAuthenticationRequired());
        parcel.writeInt(this.mSpec.getUserAuthenticationValidityDurationSeconds());
        parcel.writeInt(this.mSpec.getUserAuthenticationType());
        parcel.writeBoolean(this.mSpec.isUserPresenceRequired());
        parcel.writeByteArray(this.mSpec.getAttestationChallenge());
        parcel.writeBoolean(this.mSpec.isDevicePropertiesAttestationIncluded());
        parcel.writeIntArray(this.mSpec.getAttestationIds());
        parcel.writeBoolean(this.mSpec.isUniqueIdIncluded());
        parcel.writeBoolean(this.mSpec.isUserAuthenticationValidWhileOnBody());
        parcel.writeBoolean(this.mSpec.isInvalidatedByBiometricEnrollment());
        parcel.writeBoolean(this.mSpec.isStrongBoxBacked());
        parcel.writeBoolean(this.mSpec.isUserConfirmationRequired());
        parcel.writeBoolean(this.mSpec.isUnlockedDeviceRequired());
        parcel.writeBoolean(this.mSpec.isCriticalToDeviceEncryption());
        parcel.writeInt(this.mSpec.getMaxUsageCount());
        parcel.writeString(this.mSpec.getAttestKeyAlias());
        parcel.writeLong(this.mSpec.getBoundToSpecificSecureUserId());
    }

    private static Date readDateOrNull(Parcel parcel) {
        if (parcel.readBoolean()) {
            return new Date(parcel.readLong());
        }
        return null;
    }

    private ParcelableKeyGenParameterSpec(Parcel parcel) {
        AlgorithmParameterSpec eCGenParameterSpec;
        String readString = parcel.readString();
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        int readInt4 = parcel.readInt();
        if (readInt4 == 1) {
            eCGenParameterSpec = null;
        } else if (readInt4 == 2) {
            eCGenParameterSpec = new RSAKeyGenParameterSpec(parcel.readInt(), new BigInteger(parcel.createByteArray()));
        } else if (readInt4 == 3) {
            eCGenParameterSpec = new ECGenParameterSpec(parcel.readString());
        } else {
            throw new IllegalArgumentException(String.format("Unknown algorithm parameter spec: %d", Integer.valueOf(readInt4)));
        }
        X500Principal x500Principal = new X500Principal(parcel.createByteArray());
        BigInteger bigInteger = new BigInteger(parcel.createByteArray());
        Date date = new Date(parcel.readLong());
        Date date2 = new Date(parcel.readLong());
        Date readDateOrNull = readDateOrNull(parcel);
        Date readDateOrNull2 = readDateOrNull(parcel);
        Date readDateOrNull3 = readDateOrNull(parcel);
        String[] createStringArray = parcel.createStringArray();
        ArrayList<String> createStringArrayList = parcel.createStringArrayList();
        this.mSpec = new KeyGenParameterSpec(readString, readInt2, readInt3, eCGenParameterSpec, x500Principal, bigInteger, date, date2, readDateOrNull, readDateOrNull2, readDateOrNull3, readInt, createStringArray, createStringArrayList != null ? Set.copyOf(createStringArrayList) : Collections.EMPTY_SET, parcel.createStringArray(), parcel.createStringArray(), parcel.createStringArray(), parcel.readBoolean(), parcel.readBoolean(), parcel.readInt(), parcel.readInt(), parcel.readBoolean(), parcel.createByteArray(), parcel.readBoolean(), parcel.createIntArray(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readInt(), parcel.readString(), parcel.readLong());
    }

    public KeyGenParameterSpec getSpec() {
        return this.mSpec;
    }
}
