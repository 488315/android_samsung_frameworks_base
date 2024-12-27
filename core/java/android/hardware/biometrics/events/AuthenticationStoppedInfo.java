package android.hardware.biometrics.events;

import android.annotation.NonNull;
import android.hardware.biometrics.BiometricRequestConstants;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

import java.lang.annotation.Annotation;
import java.util.Objects;

public final class AuthenticationStoppedInfo implements Parcelable {
    public static final Parcelable.Creator<AuthenticationStoppedInfo> CREATOR =
            new Parcelable.Creator<AuthenticationStoppedInfo>() { // from class:
                // android.hardware.biometrics.events.AuthenticationStoppedInfo.1
                @Override // android.os.Parcelable.Creator
                public AuthenticationStoppedInfo[] newArray(int size) {
                    return new AuthenticationStoppedInfo[size];
                }

                @Override // android.os.Parcelable.Creator
                public AuthenticationStoppedInfo createFromParcel(Parcel in) {
                    return new AuthenticationStoppedInfo(in);
                }
            };
    private final BiometricSourceType mBiometricSourceType;
    private final int mRequestReason;

    AuthenticationStoppedInfo(BiometricSourceType biometricSourceType, int requestReason) {
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mBiometricSourceType);
        this.mRequestReason = requestReason;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class,
                (Annotation) null,
                this.mRequestReason);
    }

    public BiometricSourceType getBiometricSourceType() {
        return this.mBiometricSourceType;
    }

    public int getRequestReason() {
        return this.mRequestReason;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthenticationStoppedInfo that = (AuthenticationStoppedInfo) o;
        if (Objects.equals(this.mBiometricSourceType, that.mBiometricSourceType)
                && this.mRequestReason == that.mRequestReason) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + Objects.hashCode(this.mBiometricSourceType);
        return (_hash * 31) + this.mRequestReason;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedObject(this.mBiometricSourceType, flags);
        dest.writeInt(this.mRequestReason);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AuthenticationStoppedInfo(Parcel in) {
        BiometricSourceType biometricSourceType =
                (BiometricSourceType) in.readTypedObject(BiometricSourceType.CREATOR);
        int requestReason = in.readInt();
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mBiometricSourceType);
        this.mRequestReason = requestReason;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class,
                (Annotation) null,
                this.mRequestReason);
    }

    public static final class Builder {
        private BiometricSourceType mBiometricSourceType;
        private long mBuilderFieldsSet = 0;
        private int mRequestReason;

        public Builder(BiometricSourceType biometricSourceType, int requestReason) {
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

        public AuthenticationStoppedInfo build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            AuthenticationStoppedInfo o =
                    new AuthenticationStoppedInfo(this.mBiometricSourceType, this.mRequestReason);
            return o;
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 4) != 0) {
                throw new IllegalStateException(
                        "This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @Deprecated
    private void __metadata() {}
}
