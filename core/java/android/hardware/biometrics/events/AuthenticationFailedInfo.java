package android.hardware.biometrics.events;

import android.annotation.NonNull;
import android.hardware.biometrics.BiometricRequestConstants;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

import java.lang.annotation.Annotation;
import java.util.Objects;

public final class AuthenticationFailedInfo implements Parcelable {
    public static final Parcelable.Creator<AuthenticationFailedInfo> CREATOR =
            new Parcelable.Creator<AuthenticationFailedInfo>() { // from class:
                // android.hardware.biometrics.events.AuthenticationFailedInfo.1
                @Override // android.os.Parcelable.Creator
                public AuthenticationFailedInfo[] newArray(int size) {
                    return new AuthenticationFailedInfo[size];
                }

                @Override // android.os.Parcelable.Creator
                public AuthenticationFailedInfo createFromParcel(Parcel in) {
                    return new AuthenticationFailedInfo(in);
                }
            };
    private final BiometricSourceType mBiometricSourceType;
    private final int mRequestReason;
    private final int mUserId;

    AuthenticationFailedInfo(
            BiometricSourceType biometricSourceType, int requestReason, int userId) {
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mBiometricSourceType);
        this.mRequestReason = requestReason;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class,
                (Annotation) null,
                this.mRequestReason);
        this.mUserId = userId;
    }

    public BiometricSourceType getBiometricSourceType() {
        return this.mBiometricSourceType;
    }

    public int getRequestReason() {
        return this.mRequestReason;
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
        AuthenticationFailedInfo that = (AuthenticationFailedInfo) o;
        if (Objects.equals(this.mBiometricSourceType, that.mBiometricSourceType)
                && this.mRequestReason == that.mRequestReason
                && this.mUserId == that.mUserId) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + Objects.hashCode(this.mBiometricSourceType);
        return (((_hash * 31) + this.mRequestReason) * 31) + this.mUserId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedObject(this.mBiometricSourceType, flags);
        dest.writeInt(this.mRequestReason);
        dest.writeInt(this.mUserId);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AuthenticationFailedInfo(Parcel in) {
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
        this.mUserId = userId;
    }

    public static final class Builder {
        private BiometricSourceType mBiometricSourceType;
        private long mBuilderFieldsSet = 0;
        private int mRequestReason;
        private int mUserId;

        public Builder(BiometricSourceType biometricSourceType, int requestReason, int userId) {
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

        public Builder setUserId(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mUserId = value;
            return this;
        }

        public AuthenticationFailedInfo build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            AuthenticationFailedInfo o =
                    new AuthenticationFailedInfo(
                            this.mBiometricSourceType, this.mRequestReason, this.mUserId);
            return o;
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 8) != 0) {
                throw new IllegalStateException(
                        "This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @Deprecated
    private void __metadata() {}
}
