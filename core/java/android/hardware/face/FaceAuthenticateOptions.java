package android.hardware.face;

import android.annotation.NonNull;
import android.hardware.biometrics.AuthenticateOptions;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
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
        public FaceAuthenticateOptions[] newArray(int size) {
            return new FaceAuthenticateOptions[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceAuthenticateOptions createFromParcel(Parcel in) {
            return new FaceAuthenticateOptions(in);
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

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultUserId() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultSensorId() {
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultDisplayState() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultAuthenticateReason() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultWakeReason() {
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

    public static String authenticateReasonToString(int value) {
        switch (value) {
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
                return Integer.toHexString(value);
        }
    }

    FaceAuthenticateOptions(int userId, int sensorId, int displayState, int authenticateReason, int wakeReason, String opPackageName, String attributionTag, boolean isMandatoryBiometrics) {
        this.mUserId = userId;
        this.mSensorId = sensorId;
        this.mDisplayState = displayState;
        AnnotationValidations.validate((Class<? extends Annotation>) AuthenticateOptions.DisplayState.class, (Annotation) null, this.mDisplayState);
        this.mAuthenticateReason = authenticateReason;
        if (this.mAuthenticateReason == 0 || this.mAuthenticateReason == 1 || this.mAuthenticateReason == 2 || this.mAuthenticateReason == 3 || this.mAuthenticateReason == 4 || this.mAuthenticateReason == 5 || this.mAuthenticateReason == 6 || this.mAuthenticateReason == 7 || this.mAuthenticateReason == 8 || this.mAuthenticateReason == 9 || this.mAuthenticateReason == 10) {
            this.mWakeReason = wakeReason;
            AnnotationValidations.validate((Class<? extends Annotation>) PowerManager.WakeReason.class, (Annotation) null, this.mWakeReason);
            this.mOpPackageName = opPackageName;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mOpPackageName);
            this.mAttributionTag = attributionTag;
            this.mIsMandatoryBiometrics = isMandatoryBiometrics;
            return;
        }
        throw new IllegalArgumentException("authenticateReason was " + this.mAuthenticateReason + " but must be one of: AUTHENTICATE_REASON_UNKNOWN(0), AUTHENTICATE_REASON_STARTED_WAKING_UP(1), AUTHENTICATE_REASON_PRIMARY_BOUNCER_SHOWN(2), AUTHENTICATE_REASON_ASSISTANT_VISIBLE(3), AUTHENTICATE_REASON_ALTERNATE_BIOMETRIC_BOUNCER_SHOWN(4), AUTHENTICATE_REASON_NOTIFICATION_PANEL_CLICKED(5), AUTHENTICATE_REASON_OCCLUDING_APP_REQUESTED(6), AUTHENTICATE_REASON_PICK_UP_GESTURE_TRIGGERED(7), AUTHENTICATE_REASON_QS_EXPANDED(8), AUTHENTICATE_REASON_SWIPE_UP_ON_BOUNCER(9), AUTHENTICATE_REASON_UDFPS_POINTER_DOWN(10" + NavigationBarInflaterView.KEY_CODE_END);
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

    public FaceAuthenticateOptions setSensorId(int value) {
        this.mSensorId = value;
        return this;
    }

    public FaceAuthenticateOptions setOpPackageName(String value) {
        this.mOpPackageName = value;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mOpPackageName);
        return this;
    }

    public FaceAuthenticateOptions setAttributionTag(String value) {
        this.mAttributionTag = value;
        return this;
    }

    public FaceAuthenticateOptions setIsMandatoryBiometrics(boolean value) {
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
        FaceAuthenticateOptions that = (FaceAuthenticateOptions) o;
        if (this.mUserId == that.mUserId && this.mSensorId == that.mSensorId && this.mDisplayState == that.mDisplayState && this.mAuthenticateReason == that.mAuthenticateReason && this.mWakeReason == that.mWakeReason && Objects.equals(this.mOpPackageName, that.mOpPackageName) && Objects.equals(this.mAttributionTag, that.mAttributionTag) && this.mIsMandatoryBiometrics == that.mIsMandatoryBiometrics) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + this.mUserId;
        return (((((((((((((_hash * 31) + this.mSensorId) * 31) + this.mDisplayState) * 31) + this.mAuthenticateReason) * 31) + this.mWakeReason) * 31) + Objects.hashCode(this.mOpPackageName)) * 31) + Objects.hashCode(this.mAttributionTag)) * 31) + Boolean.hashCode(this.mIsMandatoryBiometrics);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        int flg = this.mIsMandatoryBiometrics ? 0 | 128 : 0;
        if (this.mAttributionTag != null) {
            flg |= 64;
        }
        dest.writeInt(flg);
        dest.writeInt(this.mUserId);
        dest.writeInt(this.mSensorId);
        dest.writeInt(this.mDisplayState);
        dest.writeInt(this.mAuthenticateReason);
        dest.writeInt(this.mWakeReason);
        dest.writeString(this.mOpPackageName);
        if (this.mAttributionTag != null) {
            dest.writeString(this.mAttributionTag);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected FaceAuthenticateOptions(Parcel in) {
        int flg = in.readInt();
        boolean isMandatoryBiometrics = (flg & 128) != 0;
        int userId = in.readInt();
        int sensorId = in.readInt();
        int displayState = in.readInt();
        int authenticateReason = in.readInt();
        int wakeReason = in.readInt();
        String opPackageName = in.readString();
        String attributionTag = (flg & 64) == 0 ? null : in.readString();
        this.mUserId = userId;
        this.mSensorId = sensorId;
        this.mDisplayState = displayState;
        AnnotationValidations.validate((Class<? extends Annotation>) AuthenticateOptions.DisplayState.class, (Annotation) null, this.mDisplayState);
        this.mAuthenticateReason = authenticateReason;
        if (this.mAuthenticateReason != 0 && this.mAuthenticateReason != 1 && this.mAuthenticateReason != 2 && this.mAuthenticateReason != 3 && this.mAuthenticateReason != 4 && this.mAuthenticateReason != 5 && this.mAuthenticateReason != 6 && this.mAuthenticateReason != 7 && this.mAuthenticateReason != 8 && this.mAuthenticateReason != 9 && this.mAuthenticateReason != 10) {
            throw new IllegalArgumentException("authenticateReason was " + this.mAuthenticateReason + " but must be one of: AUTHENTICATE_REASON_UNKNOWN(0), AUTHENTICATE_REASON_STARTED_WAKING_UP(1), AUTHENTICATE_REASON_PRIMARY_BOUNCER_SHOWN(2), AUTHENTICATE_REASON_ASSISTANT_VISIBLE(3), AUTHENTICATE_REASON_ALTERNATE_BIOMETRIC_BOUNCER_SHOWN(4), AUTHENTICATE_REASON_NOTIFICATION_PANEL_CLICKED(5), AUTHENTICATE_REASON_OCCLUDING_APP_REQUESTED(6), AUTHENTICATE_REASON_PICK_UP_GESTURE_TRIGGERED(7), AUTHENTICATE_REASON_QS_EXPANDED(8), AUTHENTICATE_REASON_SWIPE_UP_ON_BOUNCER(9), AUTHENTICATE_REASON_UDFPS_POINTER_DOWN(10" + NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mWakeReason = wakeReason;
        AnnotationValidations.validate((Class<? extends Annotation>) PowerManager.WakeReason.class, (Annotation) null, this.mWakeReason);
        this.mOpPackageName = opPackageName;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mOpPackageName);
        this.mAttributionTag = attributionTag;
        this.mIsMandatoryBiometrics = isMandatoryBiometrics;
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

        public Builder setDisplayState(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mDisplayState = value;
            return this;
        }

        public Builder setAuthenticateReason(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mAuthenticateReason = value;
            return this;
        }

        public Builder setWakeReason(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mWakeReason = value;
            return this;
        }

        public Builder setOpPackageName(String value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mOpPackageName = value;
            return this;
        }

        public Builder setAttributionTag(String value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            this.mAttributionTag = value;
            return this;
        }

        public Builder setIsMandatoryBiometrics(boolean value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 128;
            this.mIsMandatoryBiometrics = value;
            return this;
        }

        public FaceAuthenticateOptions build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 256;
            if ((this.mBuilderFieldsSet & 1) == 0) {
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
            FaceAuthenticateOptions o = new FaceAuthenticateOptions(this.mUserId, this.mSensorId, this.mDisplayState, this.mAuthenticateReason, this.mWakeReason, this.mOpPackageName, this.mAttributionTag, this.mIsMandatoryBiometrics);
            return o;
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 256) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @Deprecated
    private void __metadata() {
    }
}
