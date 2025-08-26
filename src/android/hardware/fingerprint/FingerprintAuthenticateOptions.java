package android.hardware.fingerprint;

import android.annotation.NonNull;
import android.hardware.biometrics.AuthenticateOptions;
import android.hardware.biometrics.common.AuthenticateReason;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Annotation;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class FingerprintAuthenticateOptions implements AuthenticateOptions, Parcelable {
    public static final Parcelable.Creator<FingerprintAuthenticateOptions> CREATOR = new Parcelable.Creator<FingerprintAuthenticateOptions>() { // from class: android.hardware.fingerprint.FingerprintAuthenticateOptions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FingerprintAuthenticateOptions[] newArray(int i) {
            return new FingerprintAuthenticateOptions[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FingerprintAuthenticateOptions createFromParcel(Parcel parcel) {
            return new FingerprintAuthenticateOptions(parcel);
        }
    };
    private String mAttributionTag;
    private final int mDisplayState;
    private final boolean mIgnoreEnrollmentState;
    private boolean mIsMandatoryBiometrics;
    private String mOpPackageName;
    private int mSensorId;
    private final int mUserId;
    private AuthenticateReason.Vendor mVendorReason;

    @Deprecated
    private void __metadata() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String defaultAttributionTag() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultDisplayState() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean defaultIgnoreEnrollmentState() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultSensorId() {
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultUserId() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AuthenticateReason.Vendor defaultVendorReason() {
        return null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String defaultOpPackageName() {
        return "";
    }

    FingerprintAuthenticateOptions(int i, int i2, boolean z, int i3, String str, String str2, AuthenticateReason.Vendor vendor2, boolean z2) {
        this.mUserId = i;
        this.mSensorId = i2;
        this.mIgnoreEnrollmentState = z;
        this.mDisplayState = i3;
        AnnotationValidations.validate((Class<? extends Annotation>) AuthenticateOptions.DisplayState.class, (Annotation) null, i3);
        this.mOpPackageName = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.mAttributionTag = str2;
        this.mVendorReason = vendor2;
        this.mIsMandatoryBiometrics = z2;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public int getUserId() {
        return this.mUserId;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public int getSensorId() {
        return this.mSensorId;
    }

    public boolean isIgnoreEnrollmentState() {
        return this.mIgnoreEnrollmentState;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public int getDisplayState() {
        return this.mDisplayState;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public String getOpPackageName() {
        return this.mOpPackageName;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public String getAttributionTag() {
        return this.mAttributionTag;
    }

    public AuthenticateReason.Vendor getVendorReason() {
        return this.mVendorReason;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public boolean isMandatoryBiometrics() {
        return this.mIsMandatoryBiometrics;
    }

    public FingerprintAuthenticateOptions setSensorId(int i) {
        this.mSensorId = i;
        return this;
    }

    public FingerprintAuthenticateOptions setOpPackageName(String str) {
        this.mOpPackageName = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        return this;
    }

    public FingerprintAuthenticateOptions setAttributionTag(String str) {
        this.mAttributionTag = str;
        return this;
    }

    public FingerprintAuthenticateOptions setVendorReason(AuthenticateReason.Vendor vendor2) {
        this.mVendorReason = vendor2;
        return this;
    }

    public FingerprintAuthenticateOptions setIsMandatoryBiometrics(boolean z) {
        this.mIsMandatoryBiometrics = z;
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            FingerprintAuthenticateOptions fingerprintAuthenticateOptions = (FingerprintAuthenticateOptions) obj;
            if (this.mUserId == fingerprintAuthenticateOptions.mUserId && this.mSensorId == fingerprintAuthenticateOptions.mSensorId && this.mIgnoreEnrollmentState == fingerprintAuthenticateOptions.mIgnoreEnrollmentState && this.mDisplayState == fingerprintAuthenticateOptions.mDisplayState && Objects.equals(this.mOpPackageName, fingerprintAuthenticateOptions.mOpPackageName) && Objects.equals(this.mAttributionTag, fingerprintAuthenticateOptions.mAttributionTag) && Objects.equals(this.mVendorReason, fingerprintAuthenticateOptions.mVendorReason) && this.mIsMandatoryBiometrics == fingerprintAuthenticateOptions.mIsMandatoryBiometrics) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((this.mUserId + 31) * 31) + this.mSensorId) * 31) + Boolean.hashCode(this.mIgnoreEnrollmentState)) * 31) + this.mDisplayState) * 31) + Objects.hashCode(this.mOpPackageName)) * 31) + Objects.hashCode(this.mAttributionTag)) * 31) + Objects.hashCode(this.mVendorReason)) * 31) + Boolean.hashCode(this.mIsMandatoryBiometrics);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int i2 = this.mIgnoreEnrollmentState ? 4 : 0;
        if (this.mIsMandatoryBiometrics) {
            i2 |= 128;
        }
        if (this.mAttributionTag != null) {
            i2 |= 32;
        }
        if (this.mVendorReason != null) {
            i2 |= 64;
        }
        parcel.writeInt(i2);
        parcel.writeInt(this.mUserId);
        parcel.writeInt(this.mSensorId);
        parcel.writeInt(this.mDisplayState);
        parcel.writeString(this.mOpPackageName);
        String str = this.mAttributionTag;
        if (str != null) {
            parcel.writeString(str);
        }
        AuthenticateReason.Vendor vendor2 = this.mVendorReason;
        if (vendor2 != null) {
            parcel.writeTypedObject(vendor2, i);
        }
    }

    FingerprintAuthenticateOptions(Parcel parcel) {
        int i = parcel.readInt();
        boolean z = (i & 4) != 0;
        boolean z2 = (i & 128) != 0;
        int i2 = parcel.readInt();
        int i3 = parcel.readInt();
        int i4 = parcel.readInt();
        String string = parcel.readString();
        String string2 = (i & 32) == 0 ? null : parcel.readString();
        AuthenticateReason.Vendor vendor2 = (i & 64) == 0 ? null : (AuthenticateReason.Vendor) parcel.readTypedObject(AuthenticateReason.Vendor.CREATOR);
        this.mUserId = i2;
        this.mSensorId = i3;
        this.mIgnoreEnrollmentState = z;
        this.mDisplayState = i4;
        AnnotationValidations.validate((Class<? extends Annotation>) AuthenticateOptions.DisplayState.class, (Annotation) null, i4);
        this.mOpPackageName = string;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string);
        this.mAttributionTag = string2;
        this.mVendorReason = vendor2;
        this.mIsMandatoryBiometrics = z2;
    }

    public static final class Builder {
        private String mAttributionTag;
        private long mBuilderFieldsSet = 0;
        private int mDisplayState;
        private boolean mIgnoreEnrollmentState;
        private boolean mIsMandatoryBiometrics;
        private String mOpPackageName;
        private int mSensorId;
        private int mUserId;
        private AuthenticateReason.Vendor mVendorReason;

        public Builder setUserId(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mUserId = i;
            return this;
        }

        public Builder setSensorId(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mSensorId = i;
            return this;
        }

        public Builder setIgnoreEnrollmentState(boolean z) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mIgnoreEnrollmentState = z;
            return this;
        }

        public Builder setDisplayState(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mDisplayState = i;
            return this;
        }

        public Builder setOpPackageName(String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mOpPackageName = str;
            return this;
        }

        public Builder setAttributionTag(String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mAttributionTag = str;
            return this;
        }

        public Builder setVendorReason(AuthenticateReason.Vendor vendor2) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            this.mVendorReason = vendor2;
            return this;
        }

        public Builder setIsMandatoryBiometrics(boolean z) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 128;
            this.mIsMandatoryBiometrics = z;
            return this;
        }

        public FingerprintAuthenticateOptions build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 256;
            this.mBuilderFieldsSet = j;
            if ((j & 1) == 0) {
                this.mUserId = FingerprintAuthenticateOptions.defaultUserId();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mSensorId = FingerprintAuthenticateOptions.defaultSensorId();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mIgnoreEnrollmentState = FingerprintAuthenticateOptions.defaultIgnoreEnrollmentState();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mDisplayState = FingerprintAuthenticateOptions.defaultDisplayState();
            }
            if ((this.mBuilderFieldsSet & 16) == 0) {
                this.mOpPackageName = FingerprintAuthenticateOptions.defaultOpPackageName();
            }
            if ((this.mBuilderFieldsSet & 32) == 0) {
                this.mAttributionTag = FingerprintAuthenticateOptions.defaultAttributionTag();
            }
            if ((this.mBuilderFieldsSet & 64) == 0) {
                this.mVendorReason = FingerprintAuthenticateOptions.defaultVendorReason();
            }
            return new FingerprintAuthenticateOptions(this.mUserId, this.mSensorId, this.mIgnoreEnrollmentState, this.mDisplayState, this.mOpPackageName, this.mAttributionTag, this.mVendorReason, this.mIsMandatoryBiometrics);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 256) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
