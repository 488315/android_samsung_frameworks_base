package com.samsung.android.biometrics;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes5.dex */
public interface SemBiometricConstants {
    public static final int CMD_SYSUI_ACQUIRED_EVENT = 201;
    public static final int CMD_SYSUI_ACQUIRED_VENDOR_EVENT = 202;
    public static final int CMD_SYSUI_CALIBRATION_MODE = 501;
    public static final int CMD_SYSUI_ERROR_EVENT = 203;
    public static final int CMD_SYSUI_ERROR_VENDOR_EVENT = 204;
    public static final int CMD_SYSUI_FP_GESTURE_EVENT = 600;
    public static final int CMD_SYSUI_MASK_VIEW_CTL = 500;
    public static final int CMD_SYSUI_MOVE_SENSOR_ICON = 114;
    public static final int CMD_SYSUI_PAUSE_SENSOR_ICON = 112;
    public static final int CMD_SYSUI_PREPARE_SESSION = 118;
    public static final int CMD_SYSUI_REQ_BOUNCER_SCREEN_STATUS = 117;
    public static final int CMD_SYSUI_REQ_SENSOR_ICON_VISIBILITY = 116;
    public static final int CMD_SYSUI_RESUME_SENSOR_ICON = 113;
    public static final int CMD_SYSUI_SCREEN_STATUS = 115;
    public static final int CMD_SYSUI_SHOW_SENSOR_ICON_FROM_LOCK = 119;
    public static final int DEFAULT_OPTICAL_SENSOR_BRIGHTNESS = 319;
    public static final int DEFAULT_OPTICAL_SENSOR_NITS = 525;
    public static final int DISMISSED_FP_GESTURE_ERROR = 3;
    public static final int DISMISSED_FP_GESTURE_SUCCEED = 1;
    public static final int DISMISSED_FP_GESTURE_USER_CANCELED = 2;
    public static final int DISPLAY_STATE_ON = 2;
    public static final int DISPLAY_STATE_UNKNOWN = 0;
    public static final int ERROR_SYSUI_BIOMETRIC_PROMPT_ERROR = 10;
    public static final int ERROR_SYSUI_CONNECTION_ERROR = 2;
    public static final int ERROR_SYSUI_DIED = 1;
    public static final int ERROR_SYSUI_GESTURE_CALIBRATION_ERROR = 11;
    public static final int ERROR_SYSUI_UNABLE_TO_PROCESS = 3;
    public static final int EVENT_FP_GESTURE_CALIBRATION_DONE = 8;
    public static final int EVENT_FP_GESTURE_CALIBRATION_UI_READY = 1;
    public static final int EVT_PROMPT_DIALOG_ANIMATED_IN = 1004;
    public static final int EVT_PROMPT_SYSTEM_EVENT = 1003;
    public static final int EVT_PROMPT_TRY_AGAIN = 1001;
    public static final int EVT_PROMPT_USE_DEVICE_CREDENTIAL = 1002;
    public static final int EVT_TSP_FOD_SINGLE_TAP = 8;
    public static final int EVT_TSP_FOD_TOUCH_DOWN = 15;
    public static final int EVT_TSP_FOD_TOUCH_OUT_OF_AREA = 17;
    public static final int EVT_TSP_FOD_TOUCH_UP = 16;
    public static final int FP_HAL_EVENT_TOUCH_DOWN = 2;
    public static final int FP_HAL_EVENT_TOUCH_UP = 1;
    public static final int FP_ICON_OPTION_ALWAYS_ON_DISPLAY = 2;
    public static final int FP_ICON_OPTION_NEVER = 0;
    public static final int FP_ICON_OPTION_NOT_SET = -1;
    public static final int FP_ICON_OPTION_TAP_TO_SHOW = 1;
    public static final String KEY_AVAILABILITY_BIOMETRIC = "KEY_AVAILABILITY_BIOMETRIC";
    public static final String KEY_BIOMETRICS_ID = "KEY_BIOMETRICS_ID";
    public static final String KEY_BIO_TPA_MODE = "biometric_tpa_mode";
    public static final String KEY_CHALLENGE_TOKEN = "KEY_CHALLENGE_TOKEN";
    public static final String KEY_FP_GESTURE_DIRECTION = "KEY_FP_GESTURE_DIRECTION";
    public static final String KEY_FP_ICON_ADAPTIVE_OPTION_WHEN_SCREEN_OFF = "fingerprint_adaptive_icon";
    public static final String KEY_FP_ICON_OLD_OPTION_WHEN_SCREEN_OFF = "fingerprint_screen_off_icon";
    public static final String KEY_FP_ICON_OPTION_WHEN_SCREEN_OFF = "fingerprint_screen_off_icon_aod";
    public static final String KEY_FP_ICON_OPTION_WHEN_SCREEN_ON = "fingerprint_screen_on_icon_lock";
    public static final String KEY_FP_MASKVIEW_DEBUG_MODE = "fingerprint_maskview_debug_mode";
    public static final String KEY_FP_PF_GUIDELINE_DISPLAYED = "fingerprint_protective_film_guideline_displayed";
    public static final String KEY_FP_VI_EFFECT_TYPE = "fingerprint_effect";
    public static final String KEY_IDENTIFIER_NAME = "KEY_IDENTIFIER_NAME";
    public static final String KEY_INDISPLAY_SENSOR_AREA = "sem_area";
    public static final String KEY_INDISPLAY_SENSOR_OPTICAL_BRIGHTNESS = "brightness";
    public static final String KEY_INDISPLAY_SENSOR_OPTICAL_LIGHT_COLOR = "nits";
    public static final String KEY_INDISPLAY_SENSOR_OPTICAL_NITS = "lightColor";
    public static final String KEY_KEYGUARD = "KEY_KEYGUARD";
    public static final String KEY_MANAGED_PROFILE = "MANAGED_PROFILE";
    public static final String KEY_MANAGED_PROFILE_COLOR = "MANAGED_PROFILE_COLOR";
    public static final String KEY_MANAGED_PROFILE_KNOX = "MANAGED_PROFILE_KNOX";
    public static final String KEY_MANAGED_PROFILE_KNOX_NAME = "MANAGED_PROFILE_KNOX_NAME";
    public static final String KEY_MANAGED_PROFILE_KNOX_ONLY_CONFIRM_BIOMETRIC = "MANAGED_PROFILE_KNOX_ONLY_CONFIRM_BIOMETRIC";
    public static final String KEY_MANAGED_PROFILE_KNOX_TWO_FACTOR = "MANAGED_PROFILE_KNOX_TWO_FACTOR";
    public static final String KEY_PACKAGE_NAME = "KEY_PACKAGE_NAME";
    public static final String KEY_SECURE_FOLDER = "SECURE_FOLDER";
    public static final String KEY_SECURE_FOLDER_NAME = "SECURE_FOLDER_NAME";
    public static final int LOCAL_HBM_MODE_OFF = 0;
    public static final int LOCAL_HBM_MODE_ON_LIGHT_OFF = 1;
    public static final int LOCAL_HBM_MODE_ON_LIGHT_ON = 2;
    public static final int REQ_CMD_AOD_GONE = 10;
    public static final int REQ_CMD_DISPLAY_STATE_LIMIT_CTRL = 1;
    public static final int REQ_CMD_DOZE_MODE_CTRL = 2;
    public static final int REQ_CMD_DVFS_CTL = 8;
    public static final int REQ_CMD_FP_KEEP_BIND = 12;
    public static final int REQ_CMD_GET_KEYGUARD_STATUS = 6;
    public static final int REQ_CMD_HLPM_MODE_CTRL = 3;
    public static final int REQ_CMD_HW_LIGHT_SOURCE_CTRL = 5;
    public static final int REQ_CMD_ICON_DRAWN_TIME = 11;
    public static final int REQ_CMD_LOCAL_HBM_CONTROL = 13;
    public static final int REQ_CMD_OPTICAL_CTL_DISPLAY_FUNC = 7;
    public static final int REQ_CMD_TOUCH_EVENT = 9;
    public static final int REQ_CMD_TSP_CTRL = 4;
    public static final int REQ_CMD_VENDOR_QCOM_FORCE_QDB = 100;
    public static final int STATE_OFF = 0;
    public static final int STATE_ON = 1;
    public static final int SYSUI_TYPE_CREDENTIAL_PROMPT = 32768;
    public static final int SYSUI_TYPE_FACE_PROMPT = 4096;
    public static final int SYSUI_TYPE_FINGERPRINT_ALPHA_MASK_SA = 16;
    public static final int SYSUI_TYPE_FINGERPRINT_CALIBRATION = 32;
    public static final int SYSUI_TYPE_FINGERPRINT_ENROLL = 64;
    public static final int SYSUI_TYPE_FINGERPRINT_KEYGUARD = 4;
    public static final int SYSUI_TYPE_FINGERPRINT_NORMAL_APP = 1;
    public static final int SYSUI_TYPE_FINGERPRINT_PRIVILEGED_APP = 2;
    public static final int SYSUI_TYPE_FINGERPRINT_PROMPT = 8;
    public static final int SYSUI_TYPE_GESTURE_CALIBRATION = 65536;
    public static final int SYSUI_TYPE_INTELLIGENT_PROMPT = 16384;
    public static final int SYSUI_TYPE_IRIS_PROMPT = 8192;
    public static final int TSP_STATE_DISABLE = 0;
    public static final int TSP_STATE_ENABLE = 1;
    public static final int TSP_STATE_ENABLE_HALF = 2;

    public @interface GestureCalibrationDismissedReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface GestureCalibrationEvent {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LocalHbmCmd {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SettingDbName {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SysUiBundleKey {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SysUiCommand {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SysUiError {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SysUiPromptEvent {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SysUiRequestCommand {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SysUiTspEvent {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SysUiType {
    }
}
