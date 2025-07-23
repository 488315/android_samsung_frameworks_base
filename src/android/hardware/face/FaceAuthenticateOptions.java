package android.hardware.face;

import android.annotation.NonNull;
import android.hardware.biometrics.AuthenticateOptions;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PowerManager;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes2.dex */
public class FaceAuthenticateOptions implements AuthenticateOptions, Parcelable {
    public static final int AUTHENTICATE_REASON_ALTERNATE_BIOMETRIC_BOUNCER_SHOWN = 4;
    public static final int AUTHENTICATE_REASON_ASSISTANT_VISIBLE = 3;
    public static final int AUTHENTICATE_REASON_NOTIFICATION_PANEL_CLICKED = 5;
    public static final int AUTHENTICATE_REASON_OCCLUDING_APP_REQUESTED = 6;
    public static final int AUTHENTICATE_REASON_PICK_UP_GESTURE_TRIGGERED = 7;
    public static final int AUTHENTICATE_REASON_PRIMARY_BOUNCER_SHOWN = 2;
    public static final int AUTHENTICATE_REASON_QS_EXPANDED = 8;
    public static final int AUTHENTICATE_REASON_STARTED_WAKING_UP = 1;
    public static final int AUTHENTICATE_REASON_SWIPE_UP_ON_BOUNCER = 9;
    public static final int AUTHENTICATE_REASON_UDFPS_POINTER_DOWN = 10;
    public static final int AUTHENTICATE_REASON_UNKNOWN = 0;
    public static final Parcelable.Creator<FaceAuthenticateOptions> CREATOR = new Parcelable.Creator<FaceAuthenticateOptions>() { // from class: android.hardware.face.FaceAuthenticateOptions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceAuthenticateOptions[] newArray(int i) {
            return new FaceAuthenticateOptions[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceAuthenticateOptions createFromParcel(Parcel parcel) {
            return new FaceAuthenticateOptions(parcel);
        }
    };
    private String mAttributionTag;
    private final int mAuthenticateReason;
    private final int mDisplayState;
    private boolean mIsMandatoryBiometrics;
    private String mOpPackageName;
    private int mSensorId;
    private final int mUserId;
    private final int mWakeReason;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AuthenticateReason {
    }

    @Deprecated
    private void __metadata() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String defaultAttributionTag() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultAuthenticateReason() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultDisplayState() {
        return 0;
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
    public static int defaultWakeReason() {
        return 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String defaultOpPackageName() {
        return "";
    }

    public static String authenticateReasonToString(int i) {
        switch (i) {
            case 0:
                return "AUTHENTICATE_REASON_UNKNOWN";
            case 1:
                return "AUTHENTICATE_REASON_STARTED_WAKING_UP";
            case 2:
                return "AUTHENTICATE_REASON_PRIMARY_BOUNCER_SHOWN";
            case 3:
                return "AUTHENTICATE_REASON_ASSISTANT_VISIBLE";
            case 4:
                return "AUTHENTICATE_REASON_ALTERNATE_BIOMETRIC_BOUNCER_SHOWN";
            case 5:
                return "AUTHENTICATE_REASON_NOTIFICATION_PANEL_CLICKED";
            case 6:
                return "AUTHENTICATE_REASON_OCCLUDING_APP_REQUESTED";
            case 7:
                return "AUTHENTICATE_REASON_PICK_UP_GESTURE_TRIGGERED";
            case 8:
                return "AUTHENTICATE_REASON_QS_EXPANDED";
            case 9:
                return "AUTHENTICATE_REASON_SWIPE_UP_ON_BOUNCER";
            case 10:
                return "AUTHENTICATE_REASON_UDFPS_POINTER_DOWN";
            default:
                return Integer.toHexString(i);
        }
    }

    FaceAuthenticateOptions(int i, int i2, int i3, int i4, int i5, String str, String str2, boolean z) {
        this.mUserId = i;
        this.mSensorId = i2;
        this.mDisplayState = i3;
        AnnotationValidations.validate((Class<? extends Annotation>) AuthenticateOptions.DisplayState.class, (Annotation) null, i3);
        this.mAuthenticateReason = i4;
        if (i4 != 0 && i4 != 1 && i4 != 2 && i4 != 3 && i4 != 4 && i4 != 5 && i4 != 6 && i4 != 7 && i4 != 8 && i4 != 9 && i4 != 10) {
            throw new IllegalArgumentException("authenticateReason was " + i4 + " but must be one of: AUTHENTICATE_REASON_UNKNOWN(0), AUTHENTICATE_REASON_STARTED_WAKING_UP(1), AUTHENTICATE_REASON_PRIMARY_BOUNCER_SHOWN(2), AUTHENTICATE_REASON_ASSISTANT_VISIBLE(3), AUTHENTICATE_REASON_ALTERNATE_BIOMETRIC_BOUNCER_SHOWN(4), AUTHENTICATE_REASON_NOTIFICATION_PANEL_CLICKED(5), AUTHENTICATE_REASON_OCCLUDING_APP_REQUESTED(6), AUTHENTICATE_REASON_PICK_UP_GESTURE_TRIGGERED(7), AUTHENTICATE_REASON_QS_EXPANDED(8), AUTHENTICATE_REASON_SWIPE_UP_ON_BOUNCER(9), AUTHENTICATE_REASON_UDFPS_POINTER_DOWN(10)");
        }
        this.mWakeReason = i5;
        AnnotationValidations.validate((Class<? extends Annotation>) PowerManager.WakeReason.class, (Annotation) null, i5);
        this.mOpPackageName = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.mAttributionTag = str2;
        this.mIsMandatoryBiometrics = z;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public int getUserId() {
        return this.mUserId;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public int getSensorId() {
        return this.mSensorId;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public int getDisplayState() {
        return this.mDisplayState;
    }

    public int getAuthenticateReason() {
        return this.mAuthenticateReason;
    }

    public int getWakeReason() {
        return this.mWakeReason;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public String getOpPackageName() {
        return this.mOpPackageName;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public String getAttributionTag() {
        return this.mAttributionTag;
    }

    @Override // android.hardware.biometrics.AuthenticateOptions
    public boolean isMandatoryBiometrics() {
        return this.mIsMandatoryBiometrics;
    }

    public FaceAuthenticateOptions setSensorId(int i) {
        this.mSensorId = i;
        return this;
    }

    public FaceAuthenticateOptions setOpPackageName(String str) {
        this.mOpPackageName = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        return this;
    }

    public FaceAuthenticateOptions setAttributionTag(String str) {
        this.mAttributionTag = str;
        return this;
    }

    public FaceAuthenticateOptions setIsMandatoryBiometrics(boolean z) {
        this.mIsMandatoryBiometrics = z;
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            FaceAuthenticateOptions faceAuthenticateOptions = (FaceAuthenticateOptions) obj;
            if (this.mUserId == faceAuthenticateOptions.mUserId && this.mSensorId == faceAuthenticateOptions.mSensorId && this.mDisplayState == faceAuthenticateOptions.mDisplayState && this.mAuthenticateReason == faceAuthenticateOptions.mAuthenticateReason && this.mWakeReason == faceAuthenticateOptions.mWakeReason && Objects.equals(this.mOpPackageName, faceAuthenticateOptions.mOpPackageName) && Objects.equals(this.mAttributionTag, faceAuthenticateOptions.mAttributionTag) && this.mIsMandatoryBiometrics == faceAuthenticateOptions.mIsMandatoryBiometrics) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((this.mUserId + 31) * 31) + this.mSensorId) * 31) + this.mDisplayState) * 31) + this.mAuthenticateReason) * 31) + this.mWakeReason) * 31) + Objects.hashCode(this.mOpPackageName)) * 31) + Objects.hashCode(this.mAttributionTag)) * 31) + Boolean.hashCode(this.mIsMandatoryBiometrics);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int i2 = this.mIsMandatoryBiometrics ? 128 : 0;
        if (this.mAttributionTag != null) {
            i2 |= 64;
        }
        parcel.writeInt(i2);
        parcel.writeInt(this.mUserId);
        parcel.writeInt(this.mSensorId);
        parcel.writeInt(this.mDisplayState);
        parcel.writeInt(this.mAuthenticateReason);
        parcel.writeInt(this.mWakeReason);
        parcel.writeString(this.mOpPackageName);
        String str = this.mAttributionTag;
        if (str != null) {
            parcel.writeString(str);
        }
    }

    protected FaceAuthenticateOptions(Parcel parcel) {
        int readInt = parcel.readInt();
        boolean z = (readInt & 128) != 0;
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        int readInt4 = parcel.readInt();
        int readInt5 = parcel.readInt();
        int readInt6 = parcel.readInt();
        String readString = parcel.readString();
        String readString2 = (readInt & 64) == 0 ? null : parcel.readString();
        this.mUserId = readInt2;
        this.mSensorId = readInt3;
        this.mDisplayState = readInt4;
        AnnotationValidations.validate((Class<? extends Annotation>) AuthenticateOptions.DisplayState.class, (Annotation) null, readInt4);
        this.mAuthenticateReason = readInt5;
        if (readInt5 != 0 && readInt5 != 1 && readInt5 != 2 && readInt5 != 3 && readInt5 != 4 && readInt5 != 5 && readInt5 != 6 && readInt5 != 7 && readInt5 != 8 && readInt5 != 9 && readInt5 != 10) {
            throw new IllegalArgumentException("authenticateReason was " + readInt5 + " but must be one of: AUTHENTICATE_REASON_UNKNOWN(0), AUTHENTICATE_REASON_STARTED_WAKING_UP(1), AUTHENTICATE_REASON_PRIMARY_BOUNCER_SHOWN(2), AUTHENTICATE_REASON_ASSISTANT_VISIBLE(3), AUTHENTICATE_REASON_ALTERNATE_BIOMETRIC_BOUNCER_SHOWN(4), AUTHENTICATE_REASON_NOTIFICATION_PANEL_CLICKED(5), AUTHENTICATE_REASON_OCCLUDING_APP_REQUESTED(6), AUTHENTICATE_REASON_PICK_UP_GESTURE_TRIGGERED(7), AUTHENTICATE_REASON_QS_EXPANDED(8), AUTHENTICATE_REASON_SWIPE_UP_ON_BOUNCER(9), AUTHENTICATE_REASON_UDFPS_POINTER_DOWN(10)");
        }
        this.mWakeReason = readInt6;
        AnnotationValidations.validate((Class<? extends Annotation>) PowerManager.WakeReason.class, (Annotation) null, readInt6);
        this.mOpPackageName = readString;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readString);
        this.mAttributionTag = readString2;
        this.mIsMandatoryBiometrics = z;
    }

    public static class Builder {
        private String mAttributionTag;
        private int mAuthenticateReason;
        private long mBuilderFieldsSet = 0;
        private int mDisplayState;
        private boolean mIsMandatoryBiometrics;
        private String mOpPackageName;
        private int mSensorId;
        private int mUserId;
        private int mWakeReason;

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

        public Builder setDisplayState(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mDisplayState = i;
            return this;
        }

        public Builder setAuthenticateReason(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mAuthenticateReason = i;
            return this;
        }

        public Builder setWakeReason(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mWakeReason = i;
            return this;
        }

        public Builder setOpPackageName(String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mOpPackageName = str;
            return this;
        }

        public Builder setAttributionTag(String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            this.mAttributionTag = str;
            return this;
        }

        public Builder setIsMandatoryBiometrics(boolean z) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 128;
            this.mIsMandatoryBiometrics = z;
            return this;
        }

        public FaceAuthenticateOptions build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 256;
            this.mBuilderFieldsSet = j;
            if ((j & 1) == 0) {
                this.mUserId = FaceAuthenticateOptions.defaultUserId();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mSensorId = FaceAuthenticateOptions.defaultSensorId();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mDisplayState = FaceAuthenticateOptions.defaultDisplayState();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mAuthenticateReason = FaceAuthenticateOptions.defaultAuthenticateReason();
            }
            if ((this.mBuilderFieldsSet & 16) == 0) {
                this.mWakeReason = FaceAuthenticateOptions.defaultWakeReason();
            }
            if ((this.mBuilderFieldsSet & 32) == 0) {
                this.mOpPackageName = FaceAuthenticateOptions.defaultOpPackageName();
            }
            if ((this.mBuilderFieldsSet & 64) == 0) {
                this.mAttributionTag = FaceAuthenticateOptions.defaultAttributionTag();
            }
            return new FaceAuthenticateOptions(this.mUserId, this.mSensorId, this.mDisplayState, this.mAuthenticateReason, this.mWakeReason, this.mOpPackageName, this.mAttributionTag, this.mIsMandatoryBiometrics);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 256) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
