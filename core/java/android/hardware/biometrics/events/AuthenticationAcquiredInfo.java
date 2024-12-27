package android.hardware.biometrics.events;

import android.annotation.NonNull;
import android.hardware.biometrics.BiometricRequestConstants;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

import java.lang.annotation.Annotation;
import java.util.Objects;

public final class AuthenticationAcquiredInfo implements Parcelable {
    public static final Parcelable.Creator<AuthenticationAcquiredInfo> CREATOR =
            new Parcelable.Creator<AuthenticationAcquiredInfo>() { // from class:
                // android.hardware.biometrics.events.AuthenticationAcquiredInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AuthenticationAcquiredInfo[] newArray(int size) {
                    return new AuthenticationAcquiredInfo[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AuthenticationAcquiredInfo createFromParcel(Parcel in) {
                    return new AuthenticationAcquiredInfo(in);
                }
            };
    private final int mAcquiredInfo;
    private final BiometricSourceType mBiometricSourceType;
    private final int mRequestReason;

    AuthenticationAcquiredInfo(
            BiometricSourceType biometricSourceType, int requestReason, int acquiredInfo) {
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mBiometricSourceType);
        this.mRequestReason = requestReason;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class,
                (Annotation) null,
                this.mRequestReason);
        this.mAcquiredInfo = acquiredInfo;
    }

    public BiometricSourceType getBiometricSourceType() {
        return this.mBiometricSourceType;
    }

    public int getRequestReason() {
        return this.mRequestReason;
    }

    public int getAcquiredInfo() {
        return this.mAcquiredInfo;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthenticationAcquiredInfo that = (AuthenticationAcquiredInfo) o;
        if (Objects.equals(this.mBiometricSourceType, that.mBiometricSourceType)
                && this.mRequestReason == that.mRequestReason
                && this.mAcquiredInfo == that.mAcquiredInfo) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + Objects.hashCode(this.mBiometricSourceType);
        return (((_hash * 31) + this.mRequestReason) * 31) + this.mAcquiredInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedObject(this.mBiometricSourceType, flags);
        dest.writeInt(this.mRequestReason);
        dest.writeInt(this.mAcquiredInfo);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AuthenticationAcquiredInfo(Parcel in) {
        BiometricSourceType biometricSourceType =
                (BiometricSourceType) in.readTypedObject(BiometricSourceType.CREATOR);
        int requestReason = in.readInt();
        int acquiredInfo = in.readInt();
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mBiometricSourceType);
        this.mRequestReason = requestReason;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class,
                (Annotation) null,
                this.mRequestReason);
        this.mAcquiredInfo = acquiredInfo;
    }

    public static final class Builder {
        private int mAcquiredInfo;
        private BiometricSourceType mBiometricSourceType;
        private long mBuilderFieldsSet = 0;
        private int mRequestReason;

        public Builder(
                BiometricSourceType biometricSourceType, int requestReason, int acquiredInfo) {
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
            this.mAcquiredInfo = acquiredInfo;
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

        public Builder setAcquiredInfo(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mAcquiredInfo = value;
            return this;
        }

        public AuthenticationAcquiredInfo build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            AuthenticationAcquiredInfo o =
                    new AuthenticationAcquiredInfo(
                            this.mBiometricSourceType, this.mRequestReason, this.mAcquiredInfo);
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
