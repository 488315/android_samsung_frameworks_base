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
public final class AuthenticationAcquiredInfo implements Parcelable {
    public static final Parcelable.Creator<AuthenticationAcquiredInfo> CREATOR = new Parcelable.Creator<AuthenticationAcquiredInfo>() { // from class: android.hardware.biometrics.events.AuthenticationAcquiredInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthenticationAcquiredInfo[] newArray(int i) {
            return new AuthenticationAcquiredInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthenticationAcquiredInfo createFromParcel(Parcel parcel) {
            return new AuthenticationAcquiredInfo(parcel);
        }
    };
    private final int mAcquiredInfo;
    private final BiometricSourceType mBiometricSourceType;
    private final int mRequestReason;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AuthenticationAcquiredInfo(BiometricSourceType biometricSourceType, int i, int i2) {
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) biometricSourceType);
        this.mRequestReason = i;
        AnnotationValidations.validate((Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class, (Annotation) null, i);
        this.mAcquiredInfo = i2;
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AuthenticationAcquiredInfo authenticationAcquiredInfo = (AuthenticationAcquiredInfo) obj;
            if (Objects.equals(this.mBiometricSourceType, authenticationAcquiredInfo.mBiometricSourceType) && this.mRequestReason == authenticationAcquiredInfo.mRequestReason && this.mAcquiredInfo == authenticationAcquiredInfo.mAcquiredInfo) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((Objects.hashCode(this.mBiometricSourceType) + 31) * 31) + this.mRequestReason) * 31) + this.mAcquiredInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mBiometricSourceType, i);
        parcel.writeInt(this.mRequestReason);
        parcel.writeInt(this.mAcquiredInfo);
    }

    AuthenticationAcquiredInfo(Parcel parcel) {
        BiometricSourceType biometricSourceType = (BiometricSourceType) parcel.readTypedObject(BiometricSourceType.CREATOR);
        int i = parcel.readInt();
        int i2 = parcel.readInt();
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) biometricSourceType);
        this.mRequestReason = i;
        AnnotationValidations.validate((Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class, (Annotation) null, i);
        this.mAcquiredInfo = i2;
    }

    public static final class Builder {
        private int mAcquiredInfo;
        private BiometricSourceType mBiometricSourceType;
        private long mBuilderFieldsSet = 0;
        private int mRequestReason;

        public Builder(BiometricSourceType biometricSourceType, int i, int i2) {
            this.mBiometricSourceType = biometricSourceType;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) biometricSourceType);
            this.mRequestReason = i;
            AnnotationValidations.validate((Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class, (Annotation) null, i);
            this.mAcquiredInfo = i2;
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

        public Builder setAcquiredInfo(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mAcquiredInfo = i;
            return this;
        }

        public AuthenticationAcquiredInfo build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            return new AuthenticationAcquiredInfo(this.mBiometricSourceType, this.mRequestReason, this.mAcquiredInfo);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 8) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
