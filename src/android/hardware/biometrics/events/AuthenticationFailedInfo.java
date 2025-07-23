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
public final class AuthenticationFailedInfo implements Parcelable {
    public static final Parcelable.Creator<AuthenticationFailedInfo> CREATOR = new Parcelable.Creator<AuthenticationFailedInfo>() { // from class: android.hardware.biometrics.events.AuthenticationFailedInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthenticationFailedInfo[] newArray(int i) {
            return new AuthenticationFailedInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthenticationFailedInfo createFromParcel(Parcel parcel) {
            return new AuthenticationFailedInfo(parcel);
        }
    };
    private final BiometricSourceType mBiometricSourceType;
    private final int mRequestReason;
    private final int mUserId;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AuthenticationFailedInfo(BiometricSourceType biometricSourceType, int i, int i2) {
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) biometricSourceType);
        this.mRequestReason = i;
        AnnotationValidations.validate((Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class, (Annotation) null, i);
        this.mUserId = i2;
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AuthenticationFailedInfo authenticationFailedInfo = (AuthenticationFailedInfo) obj;
            if (Objects.equals(this.mBiometricSourceType, authenticationFailedInfo.mBiometricSourceType) && this.mRequestReason == authenticationFailedInfo.mRequestReason && this.mUserId == authenticationFailedInfo.mUserId) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((Objects.hashCode(this.mBiometricSourceType) + 31) * 31) + this.mRequestReason) * 31) + this.mUserId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mBiometricSourceType, i);
        parcel.writeInt(this.mRequestReason);
        parcel.writeInt(this.mUserId);
    }

    AuthenticationFailedInfo(Parcel parcel) {
        BiometricSourceType biometricSourceType = (BiometricSourceType) parcel.readTypedObject(BiometricSourceType.CREATOR);
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) biometricSourceType);
        this.mRequestReason = readInt;
        AnnotationValidations.validate((Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class, (Annotation) null, readInt);
        this.mUserId = readInt2;
    }

    public static final class Builder {
        private BiometricSourceType mBiometricSourceType;
        private long mBuilderFieldsSet = 0;
        private int mRequestReason;
        private int mUserId;

        public Builder(BiometricSourceType biometricSourceType, int i, int i2) {
            this.mBiometricSourceType = biometricSourceType;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) biometricSourceType);
            this.mRequestReason = i;
            AnnotationValidations.validate((Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class, (Annotation) null, i);
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

        public Builder setUserId(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mUserId = i;
            return this;
        }

        public AuthenticationFailedInfo build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            return new AuthenticationFailedInfo(this.mBiometricSourceType, this.mRequestReason, this.mUserId);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 8) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
