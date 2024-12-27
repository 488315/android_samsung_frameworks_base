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
public final class AuthenticationErrorInfo implements Parcelable {
    public static final Parcelable.Creator<AuthenticationErrorInfo> CREATOR =
            new Parcelable.Creator<AuthenticationErrorInfo>() { // from class:
                // android.hardware.biometrics.events.AuthenticationErrorInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AuthenticationErrorInfo[] newArray(int size) {
                    return new AuthenticationErrorInfo[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AuthenticationErrorInfo createFromParcel(Parcel in) {
                    return new AuthenticationErrorInfo(in);
                }
            };
    private final BiometricSourceType mBiometricSourceType;
    private final int mErrCode;
    private final String mErrString;
    private final int mRequestReason;

    AuthenticationErrorInfo(
            BiometricSourceType biometricSourceType,
            int requestReason,
            String errString,
            int errCode) {
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mBiometricSourceType);
        this.mRequestReason = requestReason;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class,
                (Annotation) null,
                this.mRequestReason);
        this.mErrString = errString;
        this.mErrCode = errCode;
    }

    public BiometricSourceType getBiometricSourceType() {
        return this.mBiometricSourceType;
    }

    public int getRequestReason() {
        return this.mRequestReason;
    }

    public String getErrString() {
        return this.mErrString;
    }

    public int getErrCode() {
        return this.mErrCode;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthenticationErrorInfo that = (AuthenticationErrorInfo) o;
        if (Objects.equals(this.mBiometricSourceType, that.mBiometricSourceType)
                && this.mRequestReason == that.mRequestReason
                && Objects.equals(this.mErrString, that.mErrString)
                && this.mErrCode == that.mErrCode) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + Objects.hashCode(this.mBiometricSourceType);
        return (((((_hash * 31) + this.mRequestReason) * 31) + Objects.hashCode(this.mErrString))
                        * 31)
                + this.mErrCode;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        byte flg = this.mErrString != null ? (byte) (0 | 4) : (byte) 0;
        dest.writeByte(flg);
        dest.writeTypedObject(this.mBiometricSourceType, flags);
        dest.writeInt(this.mRequestReason);
        if (this.mErrString != null) {
            dest.writeString(this.mErrString);
        }
        dest.writeInt(this.mErrCode);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AuthenticationErrorInfo(Parcel in) {
        byte flg = in.readByte();
        BiometricSourceType biometricSourceType =
                (BiometricSourceType) in.readTypedObject(BiometricSourceType.CREATOR);
        int requestReason = in.readInt();
        String errString = (flg & 4) == 0 ? null : in.readString();
        int errCode = in.readInt();
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mBiometricSourceType);
        this.mRequestReason = requestReason;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class,
                (Annotation) null,
                this.mRequestReason);
        this.mErrString = errString;
        this.mErrCode = errCode;
    }

    public static final class Builder {
        private BiometricSourceType mBiometricSourceType;
        private long mBuilderFieldsSet = 0;
        private int mErrCode;
        private String mErrString;
        private int mRequestReason;

        public Builder(
                BiometricSourceType biometricSourceType,
                int requestReason,
                String errString,
                int errCode) {
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
            this.mErrString = errString;
            this.mErrCode = errCode;
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

        public Builder setErrString(String value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mErrString = value;
            return this;
        }

        public Builder setErrCode(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mErrCode = value;
            return this;
        }

        public AuthenticationErrorInfo build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            AuthenticationErrorInfo o =
                    new AuthenticationErrorInfo(
                            this.mBiometricSourceType,
                            this.mRequestReason,
                            this.mErrString,
                            this.mErrCode);
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
