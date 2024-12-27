package android.hardware.fingerprint;

import android.annotation.NonNull;
import android.hardware.biometrics.AuthenticateOptions;
import android.hardware.biometrics.common.AuthenticateReason;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

import java.lang.annotation.Annotation;
import java.util.Objects;

public final class FingerprintAuthenticateOptions implements AuthenticateOptions, Parcelable {
    public static final Parcelable.Creator<FingerprintAuthenticateOptions> CREATOR =
            new Parcelable.Creator<FingerprintAuthenticateOptions>() { // from class:
                // android.hardware.fingerprint.FingerprintAuthenticateOptions.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FingerprintAuthenticateOptions[] newArray(int size) {
                    return new FingerprintAuthenticateOptions[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FingerprintAuthenticateOptions createFromParcel(Parcel in) {
                    return new FingerprintAuthenticateOptions(in);
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

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultUserId() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultSensorId() {
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean defaultIgnoreEnrollmentState() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultDisplayState() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String defaultOpPackageName() {
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String defaultAttributionTag() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AuthenticateReason.Vendor defaultVendorReason() {
        return null;
    }

    FingerprintAuthenticateOptions(
            int userId,
            int sensorId,
            boolean ignoreEnrollmentState,
            int displayState,
            String opPackageName,
            String attributionTag,
            AuthenticateReason.Vendor vendorReason,
            boolean isMandatoryBiometrics) {
        this.mUserId = userId;
        this.mSensorId = sensorId;
        this.mIgnoreEnrollmentState = ignoreEnrollmentState;
        this.mDisplayState = displayState;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) AuthenticateOptions.DisplayState.class,
                (Annotation) null,
                this.mDisplayState);
        this.mOpPackageName = opPackageName;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mOpPackageName);
        this.mAttributionTag = attributionTag;
        this.mVendorReason = vendorReason;
        this.mIsMandatoryBiometrics = isMandatoryBiometrics;
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

    public FingerprintAuthenticateOptions setSensorId(int value) {
        this.mSensorId = value;
        return this;
    }

    public FingerprintAuthenticateOptions setOpPackageName(String value) {
        this.mOpPackageName = value;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mOpPackageName);
        return this;
    }

    public FingerprintAuthenticateOptions setAttributionTag(String value) {
        this.mAttributionTag = value;
        return this;
    }

    public FingerprintAuthenticateOptions setVendorReason(AuthenticateReason.Vendor value) {
        this.mVendorReason = value;
        return this;
    }

    public FingerprintAuthenticateOptions setIsMandatoryBiometrics(boolean value) {
        this.mIsMandatoryBiometrics = value;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FingerprintAuthenticateOptions that = (FingerprintAuthenticateOptions) o;
        if (this.mUserId == that.mUserId
                && this.mSensorId == that.mSensorId
                && this.mIgnoreEnrollmentState == that.mIgnoreEnrollmentState
                && this.mDisplayState == that.mDisplayState
                && Objects.equals(this.mOpPackageName, that.mOpPackageName)
                && Objects.equals(this.mAttributionTag, that.mAttributionTag)
                && Objects.equals(this.mVendorReason, that.mVendorReason)
                && this.mIsMandatoryBiometrics == that.mIsMandatoryBiometrics) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + this.mUserId;
        return (((((((((((((_hash * 31) + this.mSensorId) * 31)
                                                                                                + Boolean
                                                                                                        .hashCode(
                                                                                                                this
                                                                                                                        .mIgnoreEnrollmentState))
                                                                                        * 31)
                                                                                + this
                                                                                        .mDisplayState)
                                                                        * 31)
                                                                + Objects.hashCode(
                                                                        this.mOpPackageName))
                                                        * 31)
                                                + Objects.hashCode(this.mAttributionTag))
                                        * 31)
                                + Objects.hashCode(this.mVendorReason))
                        * 31)
                + Boolean.hashCode(this.mIsMandatoryBiometrics);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        int flg = this.mIgnoreEnrollmentState ? 0 | 4 : 0;
        if (this.mIsMandatoryBiometrics) {
            flg |= 128;
        }
        if (this.mAttributionTag != null) {
            flg |= 32;
        }
        if (this.mVendorReason != null) {
            flg |= 64;
        }
        dest.writeInt(flg);
        dest.writeInt(this.mUserId);
        dest.writeInt(this.mSensorId);
        dest.writeInt(this.mDisplayState);
        dest.writeString(this.mOpPackageName);
        if (this.mAttributionTag != null) {
            dest.writeString(this.mAttributionTag);
        }
        if (this.mVendorReason != null) {
            dest.writeTypedObject(this.mVendorReason, flags);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    FingerprintAuthenticateOptions(Parcel in) {
        int flg = in.readInt();
        boolean ignoreEnrollmentState = (flg & 4) != 0;
        boolean isMandatoryBiometrics = (flg & 128) != 0;
        int userId = in.readInt();
        int sensorId = in.readInt();
        int displayState = in.readInt();
        String opPackageName = in.readString();
        String attributionTag = (flg & 32) == 0 ? null : in.readString();
        AuthenticateReason.Vendor vendorReason =
                (flg & 64) == 0
                        ? null
                        : (AuthenticateReason.Vendor)
                                in.readTypedObject(AuthenticateReason.Vendor.CREATOR);
        this.mUserId = userId;
        this.mSensorId = sensorId;
        this.mIgnoreEnrollmentState = ignoreEnrollmentState;
        this.mDisplayState = displayState;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) AuthenticateOptions.DisplayState.class,
                (Annotation) null,
                this.mDisplayState);
        this.mOpPackageName = opPackageName;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mOpPackageName);
        this.mAttributionTag = attributionTag;
        this.mVendorReason = vendorReason;
        this.mIsMandatoryBiometrics = isMandatoryBiometrics;
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

        public Builder setUserId(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mUserId = value;
            return this;
        }

        public Builder setSensorId(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mSensorId = value;
            return this;
        }

        public Builder setIgnoreEnrollmentState(boolean value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mIgnoreEnrollmentState = value;
            return this;
        }

        public Builder setDisplayState(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mDisplayState = value;
            return this;
        }

        public Builder setOpPackageName(String value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mOpPackageName = value;
            return this;
        }

        public Builder setAttributionTag(String value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mAttributionTag = value;
            return this;
        }

        public Builder setVendorReason(AuthenticateReason.Vendor value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            this.mVendorReason = value;
            return this;
        }

        public Builder setIsMandatoryBiometrics(boolean value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 128;
            this.mIsMandatoryBiometrics = value;
            return this;
        }

        public FingerprintAuthenticateOptions build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 256;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mUserId = FingerprintAuthenticateOptions.defaultUserId();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mSensorId = FingerprintAuthenticateOptions.defaultSensorId();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mIgnoreEnrollmentState =
                        FingerprintAuthenticateOptions.defaultIgnoreEnrollmentState();
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
            FingerprintAuthenticateOptions o =
                    new FingerprintAuthenticateOptions(
                            this.mUserId,
                            this.mSensorId,
                            this.mIgnoreEnrollmentState,
                            this.mDisplayState,
                            this.mOpPackageName,
                            this.mAttributionTag,
                            this.mVendorReason,
                            this.mIsMandatoryBiometrics);
            return o;
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 256) != 0) {
                throw new IllegalStateException(
                        "This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @Deprecated
    private void __metadata() {}
}
