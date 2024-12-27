package android.hardware.biometrics.events;

import android.annotation.NonNull;
import android.hardware.biometrics.BiometricRequestConstants;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

import java.lang.annotation.Annotation;
import java.util.Objects;

public final class AuthenticationHelpInfo implements Parcelable {
    public static final Parcelable.Creator<AuthenticationHelpInfo> CREATOR =
            new Parcelable.Creator<AuthenticationHelpInfo>() { // from class:
                // android.hardware.biometrics.events.AuthenticationHelpInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AuthenticationHelpInfo[] newArray(int size) {
                    return new AuthenticationHelpInfo[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AuthenticationHelpInfo createFromParcel(Parcel in) {
                    return new AuthenticationHelpInfo(in);
                }
            };
    private final BiometricSourceType mBiometricSourceType;
    private final int mHelpCode;
    private final String mHelpString;
    private final int mRequestReason;

    AuthenticationHelpInfo(
            BiometricSourceType biometricSourceType,
            int requestReason,
            String helpString,
            int helpCode) {
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mBiometricSourceType);
        this.mRequestReason = requestReason;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class,
                (Annotation) null,
                this.mRequestReason);
        this.mHelpString = helpString;
        this.mHelpCode = helpCode;
    }

    public BiometricSourceType getBiometricSourceType() {
        return this.mBiometricSourceType;
    }

    public int getRequestReason() {
        return this.mRequestReason;
    }

    public String getHelpString() {
        return this.mHelpString;
    }

    public int getHelpCode() {
        return this.mHelpCode;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthenticationHelpInfo that = (AuthenticationHelpInfo) o;
        if (Objects.equals(this.mBiometricSourceType, that.mBiometricSourceType)
                && this.mRequestReason == that.mRequestReason
                && Objects.equals(this.mHelpString, that.mHelpString)
                && this.mHelpCode == that.mHelpCode) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + Objects.hashCode(this.mBiometricSourceType);
        return (((((_hash * 31) + this.mRequestReason) * 31) + Objects.hashCode(this.mHelpString))
                        * 31)
                + this.mHelpCode;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        byte flg = this.mHelpString != null ? (byte) (0 | 4) : (byte) 0;
        dest.writeByte(flg);
        dest.writeTypedObject(this.mBiometricSourceType, flags);
        dest.writeInt(this.mRequestReason);
        if (this.mHelpString != null) {
            dest.writeString(this.mHelpString);
        }
        dest.writeInt(this.mHelpCode);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AuthenticationHelpInfo(Parcel in) {
        byte flg = in.readByte();
        BiometricSourceType biometricSourceType =
                (BiometricSourceType) in.readTypedObject(BiometricSourceType.CREATOR);
        int requestReason = in.readInt();
        String helpString = (flg & 4) == 0 ? null : in.readString();
        int helpCode = in.readInt();
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mBiometricSourceType);
        this.mRequestReason = requestReason;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class,
                (Annotation) null,
                this.mRequestReason);
        this.mHelpString = helpString;
        this.mHelpCode = helpCode;
    }

    public static final class Builder {
        private BiometricSourceType mBiometricSourceType;
        private long mBuilderFieldsSet = 0;
        private int mHelpCode;
        private String mHelpString;
        private int mRequestReason;

        public Builder(
                BiometricSourceType biometricSourceType,
                int requestReason,
                String helpString,
                int helpCode) {
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
            this.mHelpString = helpString;
            this.mHelpCode = helpCode;
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

        public Builder setHelpString(String value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mHelpString = value;
            return this;
        }

        public Builder setHelpCode(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mHelpCode = value;
            return this;
        }

        public AuthenticationHelpInfo build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            AuthenticationHelpInfo o =
                    new AuthenticationHelpInfo(
                            this.mBiometricSourceType,
                            this.mRequestReason,
                            this.mHelpString,
                            this.mHelpCode);
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
