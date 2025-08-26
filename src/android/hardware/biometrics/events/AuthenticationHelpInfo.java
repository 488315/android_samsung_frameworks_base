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
public final class AuthenticationHelpInfo implements Parcelable {
    public static final Parcelable.Creator<AuthenticationHelpInfo> CREATOR = new Parcelable.Creator<AuthenticationHelpInfo>() { // from class: android.hardware.biometrics.events.AuthenticationHelpInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthenticationHelpInfo[] newArray(int i) {
            return new AuthenticationHelpInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthenticationHelpInfo createFromParcel(Parcel parcel) {
            return new AuthenticationHelpInfo(parcel);
        }
    };
    private final BiometricSourceType mBiometricSourceType;
    private final int mHelpCode;
    private final String mHelpString;
    private final int mRequestReason;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AuthenticationHelpInfo(BiometricSourceType biometricSourceType, int i, String str, int i2) {
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) biometricSourceType);
        this.mRequestReason = i;
        AnnotationValidations.validate((Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class, (Annotation) null, i);
        this.mHelpString = str;
        this.mHelpCode = i2;
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AuthenticationHelpInfo authenticationHelpInfo = (AuthenticationHelpInfo) obj;
            if (Objects.equals(this.mBiometricSourceType, authenticationHelpInfo.mBiometricSourceType) && this.mRequestReason == authenticationHelpInfo.mRequestReason && Objects.equals(this.mHelpString, authenticationHelpInfo.mHelpString) && this.mHelpCode == authenticationHelpInfo.mHelpCode) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((Objects.hashCode(this.mBiometricSourceType) + 31) * 31) + this.mRequestReason) * 31) + Objects.hashCode(this.mHelpString)) * 31) + this.mHelpCode;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mHelpString != null ? (byte) 4 : (byte) 0);
        parcel.writeTypedObject(this.mBiometricSourceType, i);
        parcel.writeInt(this.mRequestReason);
        String str = this.mHelpString;
        if (str != null) {
            parcel.writeString(str);
        }
        parcel.writeInt(this.mHelpCode);
    }

    AuthenticationHelpInfo(Parcel parcel) {
        byte b = parcel.readByte();
        BiometricSourceType biometricSourceType = (BiometricSourceType) parcel.readTypedObject(BiometricSourceType.CREATOR);
        int i = parcel.readInt();
        String string = (b & 4) == 0 ? null : parcel.readString();
        int i2 = parcel.readInt();
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) biometricSourceType);
        this.mRequestReason = i;
        AnnotationValidations.validate((Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class, (Annotation) null, i);
        this.mHelpString = string;
        this.mHelpCode = i2;
    }

    public static final class Builder {
        private BiometricSourceType mBiometricSourceType;
        private long mBuilderFieldsSet = 0;
        private int mHelpCode;
        private String mHelpString;
        private int mRequestReason;

        public Builder(BiometricSourceType biometricSourceType, int i, String str, int i2) {
            this.mBiometricSourceType = biometricSourceType;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) biometricSourceType);
            this.mRequestReason = i;
            AnnotationValidations.validate((Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class, (Annotation) null, i);
            this.mHelpString = str;
            this.mHelpCode = i2;
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

        public Builder setHelpString(String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mHelpString = str;
            return this;
        }

        public Builder setHelpCode(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mHelpCode = i;
            return this;
        }

        public AuthenticationHelpInfo build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            return new AuthenticationHelpInfo(this.mBiometricSourceType, this.mRequestReason, this.mHelpString, this.mHelpCode);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
