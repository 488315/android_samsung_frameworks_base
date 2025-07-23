package android.hardware.biometrics.events;

import android.annotation.NonNull;
import android.hardware.biometrics.BiometricRequestConstants;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Annotation;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class AuthenticationSucceededInfo implements Parcelable {
    public static final Parcelable.Creator<AuthenticationSucceededInfo> CREATOR = new Parcelable.Creator<AuthenticationSucceededInfo>() { // from class: android.hardware.biometrics.events.AuthenticationSucceededInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthenticationSucceededInfo[] newArray(int i) {
            return new AuthenticationSucceededInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthenticationSucceededInfo createFromParcel(Parcel parcel) {
            return new AuthenticationSucceededInfo(parcel);
        }
    };
    private final BiometricSourceType mBiometricSourceType;
    private final boolean mIsStrongBiometric;
    private final int mRequestReason;
    private final int mUserId;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AuthenticationSucceededInfo(BiometricSourceType biometricSourceType, int i, boolean z, int i2) {
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) biometricSourceType);
        this.mRequestReason = i;
        AnnotationValidations.validate((Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class, (Annotation) null, i);
        this.mIsStrongBiometric = z;
        this.mUserId = i2;
    }

    public BiometricSourceType getBiometricSourceType() {
        return this.mBiometricSourceType;
    }

    public int getRequestReason() {
        return this.mRequestReason;
    }

    public boolean isIsStrongBiometric() {
        return this.mIsStrongBiometric;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AuthenticationSucceededInfo authenticationSucceededInfo = (AuthenticationSucceededInfo) obj;
            if (Objects.equals(this.mBiometricSourceType, authenticationSucceededInfo.mBiometricSourceType) && this.mRequestReason == authenticationSucceededInfo.mRequestReason && this.mIsStrongBiometric == authenticationSucceededInfo.mIsStrongBiometric && this.mUserId == authenticationSucceededInfo.mUserId) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((Objects.hashCode(this.mBiometricSourceType) + 31) * 31) + this.mRequestReason) * 31) + Boolean.hashCode(this.mIsStrongBiometric)) * 31) + this.mUserId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mIsStrongBiometric ? (byte) 4 : (byte) 0);
        parcel.writeTypedObject(this.mBiometricSourceType, i);
        parcel.writeInt(this.mRequestReason);
        parcel.writeInt(this.mUserId);
    }

    AuthenticationSucceededInfo(Parcel parcel) {
        boolean z = (parcel.readByte() & 4) != 0;
        BiometricSourceType biometricSourceType = (BiometricSourceType) parcel.readTypedObject(BiometricSourceType.CREATOR);
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) biometricSourceType);
        this.mRequestReason = readInt;
        AnnotationValidations.validate((Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class, (Annotation) null, readInt);
        this.mIsStrongBiometric = z;
        this.mUserId = readInt2;
    }

    public static final class Builder {
        private BiometricSourceType mBiometricSourceType;
        private long mBuilderFieldsSet = 0;
        private boolean mIsStrongBiometric;
        private int mRequestReason;
        private int mUserId;

        public Builder(BiometricSourceType biometricSourceType, int i, boolean z, int i2) {
            this.mBiometricSourceType = biometricSourceType;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) biometricSourceType);
            this.mRequestReason = i;
            AnnotationValidations.validate((Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class, (Annotation) null, i);
            this.mIsStrongBiometric = z;
            this.mUserId = i2;
        }

        public Builder setBiometricSourceType(BiometricSourceType biometricSourceType) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mBiometricSourceType = biometricSourceType;
            return this;
        }

        public Builder setRequestReason(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mRequestReason = i;
            return this;
        }

        public Builder setIsStrongBiometric(boolean z) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mIsStrongBiometric = z;
            return this;
        }

        public Builder setUserId(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mUserId = i;
            return this;
        }

        public AuthenticationSucceededInfo build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            return new AuthenticationSucceededInfo(this.mBiometricSourceType, this.mRequestReason, this.mIsStrongBiometric, this.mUserId);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
