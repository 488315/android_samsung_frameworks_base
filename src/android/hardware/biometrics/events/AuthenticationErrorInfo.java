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
    public static final Parcelable.Creator<AuthenticationErrorInfo> CREATOR = new Parcelable.Creator<AuthenticationErrorInfo>() { // from class: android.hardware.biometrics.events.AuthenticationErrorInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthenticationErrorInfo[] newArray(int i) {
            return new AuthenticationErrorInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthenticationErrorInfo createFromParcel(Parcel parcel) {
            return new AuthenticationErrorInfo(parcel);
        }
    };
    private final BiometricSourceType mBiometricSourceType;
    private final int mErrCode;
    private final String mErrString;
    private final int mRequestReason;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AuthenticationErrorInfo(BiometricSourceType biometricSourceType, int i, String str, int i2) {
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) biometricSourceType);
        this.mRequestReason = i;
        AnnotationValidations.validate((Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class, (Annotation) null, i);
        this.mErrString = str;
        this.mErrCode = i2;
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AuthenticationErrorInfo authenticationErrorInfo = (AuthenticationErrorInfo) obj;
            if (Objects.equals(this.mBiometricSourceType, authenticationErrorInfo.mBiometricSourceType) && this.mRequestReason == authenticationErrorInfo.mRequestReason && Objects.equals(this.mErrString, authenticationErrorInfo.mErrString) && this.mErrCode == authenticationErrorInfo.mErrCode) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((Objects.hashCode(this.mBiometricSourceType) + 31) * 31) + this.mRequestReason) * 31) + Objects.hashCode(this.mErrString)) * 31) + this.mErrCode;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mErrString != null ? (byte) 4 : (byte) 0);
        parcel.writeTypedObject(this.mBiometricSourceType, i);
        parcel.writeInt(this.mRequestReason);
        String str = this.mErrString;
        if (str != null) {
            parcel.writeString(str);
        }
        parcel.writeInt(this.mErrCode);
    }

    AuthenticationErrorInfo(Parcel parcel) {
        byte b = parcel.readByte();
        BiometricSourceType biometricSourceType = (BiometricSourceType) parcel.readTypedObject(BiometricSourceType.CREATOR);
        int i = parcel.readInt();
        String string = (b & 4) == 0 ? null : parcel.readString();
        int i2 = parcel.readInt();
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) biometricSourceType);
        this.mRequestReason = i;
        AnnotationValidations.validate((Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class, (Annotation) null, i);
        this.mErrString = string;
        this.mErrCode = i2;
    }

    public static final class Builder {
        private BiometricSourceType mBiometricSourceType;
        private long mBuilderFieldsSet = 0;
        private int mErrCode;
        private String mErrString;
        private int mRequestReason;

        public Builder(BiometricSourceType biometricSourceType, int i, String str, int i2) {
            this.mBiometricSourceType = biometricSourceType;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) biometricSourceType);
            this.mRequestReason = i;
            AnnotationValidations.validate((Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class, (Annotation) null, i);
            this.mErrString = str;
            this.mErrCode = i2;
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

        public Builder setErrString(String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mErrString = str;
            return this;
        }

        public Builder setErrCode(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mErrCode = i;
            return this;
        }

        public AuthenticationErrorInfo build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            return new AuthenticationErrorInfo(this.mBiometricSourceType, this.mRequestReason, this.mErrString, this.mErrCode);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
