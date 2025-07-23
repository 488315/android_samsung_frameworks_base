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
public final class AuthenticationStartedInfo implements Parcelable {
    public static final Parcelable.Creator<AuthenticationStartedInfo> CREATOR = new Parcelable.Creator<AuthenticationStartedInfo>() { // from class: android.hardware.biometrics.events.AuthenticationStartedInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthenticationStartedInfo[] newArray(int i) {
            return new AuthenticationStartedInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthenticationStartedInfo createFromParcel(Parcel parcel) {
            return new AuthenticationStartedInfo(parcel);
        }
    };
    private final BiometricSourceType mBiometricSourceType;
    private final int mRequestReason;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AuthenticationStartedInfo(BiometricSourceType biometricSourceType, int i) {
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) biometricSourceType);
        this.mRequestReason = i;
        AnnotationValidations.validate((Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class, (Annotation) null, i);
    }

    public BiometricSourceType getBiometricSourceType() {
        return this.mBiometricSourceType;
    }

    public int getRequestReason() {
        return this.mRequestReason;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AuthenticationStartedInfo authenticationStartedInfo = (AuthenticationStartedInfo) obj;
            if (Objects.equals(this.mBiometricSourceType, authenticationStartedInfo.mBiometricSourceType) && this.mRequestReason == authenticationStartedInfo.mRequestReason) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((Objects.hashCode(this.mBiometricSourceType) + 31) * 31) + this.mRequestReason;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mBiometricSourceType, i);
        parcel.writeInt(this.mRequestReason);
    }

    AuthenticationStartedInfo(Parcel parcel) {
        BiometricSourceType biometricSourceType = (BiometricSourceType) parcel.readTypedObject(BiometricSourceType.CREATOR);
        int readInt = parcel.readInt();
        this.mBiometricSourceType = biometricSourceType;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) biometricSourceType);
        this.mRequestReason = readInt;
        AnnotationValidations.validate((Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class, (Annotation) null, readInt);
    }

    public static final class Builder {
        private BiometricSourceType mBiometricSourceType;
        private long mBuilderFieldsSet = 0;
        private int mRequestReason;

        public Builder(BiometricSourceType biometricSourceType, int i) {
            this.mBiometricSourceType = biometricSourceType;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) biometricSourceType);
            this.mRequestReason = i;
            AnnotationValidations.validate((Class<? extends Annotation>) BiometricRequestConstants.RequestReason.class, (Annotation) null, i);
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

        public AuthenticationStartedInfo build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            return new AuthenticationStartedInfo(this.mBiometricSourceType, this.mRequestReason);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 4) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
