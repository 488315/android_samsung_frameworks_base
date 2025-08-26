package android.security.attestationverification;

import android.annotation.NonNull;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.security.attestationverification.AttestationVerificationManager;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Parcelling;
import java.lang.annotation.Annotation;
import java.time.Instant;

/* loaded from: classes3.dex */
public final class VerificationToken implements Parcelable {
    public static final Parcelable.Creator<VerificationToken> CREATOR;
    static Parcelling<Instant> sParcellingForVerificationTime;
    private final AttestationProfile mAttestationProfile;
    private final byte[] mHmac;
    private final int mLocalBindingType;
    private final Bundle mRequirements;
    private int mUid;
    private final int mVerificationResult;
    private final Instant mVerificationTime;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    VerificationToken(AttestationProfile attestationProfile, int i, Bundle bundle, int i2, Instant instant, byte[] bArr) {
        this.mAttestationProfile = attestationProfile;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) attestationProfile);
        this.mLocalBindingType = i;
        AnnotationValidations.validate((Class<? extends Annotation>) AttestationVerificationManager.LocalBindingType.class, (Annotation) null, i);
        this.mRequirements = bundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bundle);
        this.mVerificationResult = i2;
        AnnotationValidations.validate((Class<? extends Annotation>) AttestationVerificationManager.VerificationResultFlags.class, (Annotation) null, i2);
        this.mVerificationTime = instant;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) instant);
        this.mHmac = bArr;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bArr);
    }

    public AttestationProfile getAttestationProfile() {
        return this.mAttestationProfile;
    }

    public int getLocalBindingType() {
        return this.mLocalBindingType;
    }

    public Bundle getRequirements() {
        return this.mRequirements;
    }

    public int getVerificationResult() {
        return this.mVerificationResult;
    }

    public Instant getVerificationTime() {
        return this.mVerificationTime;
    }

    public byte[] getHmac() {
        return this.mHmac;
    }

    static {
        Parcelling<Instant> parcelling = Parcelling.Cache.get(Parcelling.BuiltIn.ForInstant.class);
        sParcellingForVerificationTime = parcelling;
        if (parcelling == null) {
            sParcellingForVerificationTime = Parcelling.Cache.put(new Parcelling.BuiltIn.ForInstant());
        }
        CREATOR = new Parcelable.Creator<VerificationToken>() { // from class: android.security.attestationverification.VerificationToken.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public VerificationToken[] newArray(int i) {
                return new VerificationToken[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public VerificationToken createFromParcel(Parcel parcel) {
                return new VerificationToken(parcel);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mAttestationProfile, i);
        parcel.writeInt(this.mLocalBindingType);
        parcel.writeBundle(this.mRequirements);
        parcel.writeInt(this.mVerificationResult);
        sParcellingForVerificationTime.parcel(this.mVerificationTime, parcel, i);
        parcel.writeByteArray(this.mHmac);
    }

    VerificationToken(Parcel parcel) {
        AttestationProfile attestationProfile = (AttestationProfile) parcel.readTypedObject(AttestationProfile.CREATOR);
        int i = parcel.readInt();
        Bundle bundle = parcel.readBundle();
        int i2 = parcel.readInt();
        Instant instantUnparcel = sParcellingForVerificationTime.unparcel(parcel);
        byte[] bArrCreateByteArray = parcel.createByteArray();
        this.mAttestationProfile = attestationProfile;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) attestationProfile);
        this.mLocalBindingType = i;
        AnnotationValidations.validate((Class<? extends Annotation>) AttestationVerificationManager.LocalBindingType.class, (Annotation) null, i);
        this.mRequirements = bundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bundle);
        this.mVerificationResult = i2;
        AnnotationValidations.validate((Class<? extends Annotation>) AttestationVerificationManager.VerificationResultFlags.class, (Annotation) null, i2);
        this.mVerificationTime = instantUnparcel;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) instantUnparcel);
        this.mHmac = bArrCreateByteArray;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bArrCreateByteArray);
    }

    public static final class Builder {
        private AttestationProfile mAttestationProfile;
        private long mBuilderFieldsSet = 0;
        private byte[] mHmac;
        private int mLocalBindingType;
        private Bundle mRequirements;
        private int mVerificationResult;
        private Instant mVerificationTime;

        public Builder(AttestationProfile attestationProfile, int i, Bundle bundle, int i2, Instant instant, byte[] bArr) {
            this.mAttestationProfile = attestationProfile;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) attestationProfile);
            this.mLocalBindingType = i;
            AnnotationValidations.validate((Class<? extends Annotation>) AttestationVerificationManager.LocalBindingType.class, (Annotation) null, i);
            this.mRequirements = bundle;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bundle);
            this.mVerificationResult = i2;
            AnnotationValidations.validate((Class<? extends Annotation>) AttestationVerificationManager.VerificationResultFlags.class, (Annotation) null, i2);
            this.mVerificationTime = instant;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) instant);
            this.mHmac = bArr;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bArr);
        }

        public Builder setAttestationProfile(AttestationProfile attestationProfile) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mAttestationProfile = attestationProfile;
            return this;
        }

        public Builder setLocalBindingType(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mLocalBindingType = i;
            return this;
        }

        public Builder setRequirements(Bundle bundle) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mRequirements = bundle;
            return this;
        }

        public Builder setVerificationResult(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mVerificationResult = i;
            return this;
        }

        public Builder setVerificationTime(Instant instant) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mVerificationTime = instant;
            return this;
        }

        public Builder setHmac(byte... bArr) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mHmac = bArr;
            return this;
        }

        public VerificationToken build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            return new VerificationToken(this.mAttestationProfile, this.mLocalBindingType, this.mRequirements, this.mVerificationResult, this.mVerificationTime, this.mHmac);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 64) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
