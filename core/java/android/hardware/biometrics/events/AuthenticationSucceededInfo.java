package android.hardware.biometrics.events;

import android.annotation.NonNull;
import android.hardware.biometrics.BiometricRequestConstants;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

import java.lang.annotation.Annotation;
import java.util.Objects;

public final class AuthenticationSucceededInfo implements Parcelable {
    public static final Parcelable.Creator<AuthenticationSucceededInfo> CREATOR =
            new Parcelable.Creator<AuthenticationSucceededInfo>() { // from class:
                // android.hardware.biometrics.events.AuthenticationSucceededInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AuthenticationSucceededInfo[] newArray(int size) {
                    return new AuthenticationSucceededInfo[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AuthenticationSucceededInfo createFromParcel(Parcel in) {
                    return new AuthenticationSucceededInfo(in);
                }
            };
    private final BiometricSourceType mBiometricSourceType;
    private final boolean mIsStrongBiometric;
    private final int mRequestReason;
    private final int mUserId;

    AuthenticationSucceededInfo(
            BiometricSourceType biometricSourceType,
            int requestReason,
            boolean isStrongBiometric,
            int userId) {
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mBiometricSourceType);
        this.mRequestReason = requestReason;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class,
                (Annotation) null,
                this.mRequestReason);
        this.mIsStrongBiometric = isStrongBiometric;
        this.mUserId = userId;
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthenticationSucceededInfo that = (AuthenticationSucceededInfo) o;
        if (Objects.equals(this.mBiometricSourceType, that.mBiometricSourceType)
                && this.mRequestReason == that.mRequestReason
                && this.mIsStrongBiometric == that.mIsStrongBiometric
                && this.mUserId == that.mUserId) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + Objects.hashCode(this.mBiometricSourceType);
        return (((((_hash * 31) + this.mRequestReason) * 31)
                                + Boolean.hashCode(this.mIsStrongBiometric))
                        * 31)
                + this.mUserId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        byte flg = this.mIsStrongBiometric ? (byte) (0 | 4) : (byte) 0;
        dest.writeByte(flg);
        dest.writeTypedObject(this.mBiometricSourceType, flags);
        dest.writeInt(this.mRequestReason);
        dest.writeInt(this.mUserId);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AuthenticationSucceededInfo(Parcel in) {
        byte flg = in.readByte();
        boolean isStrongBiometric = (flg & 4) != 0;
        BiometricSourceType biometricSourceType =
                (BiometricSourceType) in.readTypedObject(BiometricSourceType.CREATOR);
        int requestReason = in.readInt();
        int userId = in.readInt();
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mBiometricSourceType);
        this.mRequestReason = requestReason;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class,
                (Annotation) null,
                this.mRequestReason);
        this.mIsStrongBiometric = isStrongBiometric;
        this.mUserId = userId;
    }

    public static final class Builder {
        private BiometricSourceType mBiometricSourceType;
        private long mBuilderFieldsSet = 0;
        private boolean mIsStrongBiometric;
        private int mRequestReason;
        private int mUserId;

        public Builder(
                BiometricSourceType biometricSourceType,
                int requestReason,
                boolean isStrongBiometric,
                int userId) {
            this.mBiometricSourceType = biometricSourceType;
            AnnotationValidations.validate(
                    (Class<NonNull>) NonNull.class,
                    (NonNull) null,
                    (Object) this.mBiometricSourceType);
            this.mRequestReason = requestReason;
            AnnotationValidations.validate(
                    (Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class,
                    (Annotation) null,
                    this.mRequestReason);
            this.mIsStrongBiometric = isStrongBiometric;
            this.mUserId = userId;
        }

        public Builder setBiometricSourceType(BiometricSourceType value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mBiometricSourceType = value;
            return this;
        }

        public Builder setRequestReason(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mRequestReason = value;
            return this;
        }

        public Builder setIsStrongBiometric(boolean value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mIsStrongBiometric = value;
            return this;
        }

        public Builder setUserId(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mUserId = value;
            return this;
        }

        public AuthenticationSucceededInfo build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            AuthenticationSucceededInfo o =
                    new AuthenticationSucceededInfo(
                            this.mBiometricSourceType,
                            this.mRequestReason,
                            this.mIsStrongBiometric,
                            this.mUserId);
            return o;
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16) != 0) {
                throw new IllegalStateException(
                        "This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @Deprecated
    private void __metadata() {}
}
