package android.view;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public interface WindowManagerPolicyConstants {
    public static final String ACTION_HDMI_PLUGGED = "android.intent.action.HDMI_PLUGGED";
    public static final int APPLICATION_ABOVE_SUB_PANEL_SUBLAYER = 3;
    public static final int APPLICATION_LAYER = 2;
    public static final int APPLICATION_MEDIA_OVERLAY_SUBLAYER = -1;
    public static final int APPLICATION_MEDIA_SUBLAYER = -2;
    public static final int APPLICATION_PANEL_SUBLAYER = 1;
    public static final int APPLICATION_SUB_PANEL_SUBLAYER = 2;
    public static final String EXTRA_FROM_BRIGHTNESS_KEY = "android.intent.extra.FROM_BRIGHTNESS_KEY";
    public static final String EXTRA_FROM_HOME_KEY = "android.intent.extra.FROM_HOME_KEY";
    public static final String EXTRA_HDMI_PLUGGED_STATE = "state";
    public static final String EXTRA_START_REASON = "android.intent.extra.EXTRA_START_REASON";
    public static final int FLAG_DISABLE_KEY_REPEAT = 134217728;
    public static final int FLAG_FILTERED = 67108864;
    public static final int FLAG_INJECTED = 16777216;
    public static final int FLAG_INJECTED_FROM_ACCESSIBILITY = 131072;
    public static final int FLAG_INJECTED_FROM_ACCESSIBILITY_TOOL = 524288;
    public static final int FLAG_INTERACTIVE = 536870912;
    public static final int FLAG_KEY_GESTURE_COMBINATION_TRIGGERED = 1048576;
    public static final int FLAG_KEY_GESTURE_TRIGGERED = 262144;
    public static final int FLAG_PASS_TO_USER = 1073741824;
    public static final int FLAG_TRUSTED = 33554432;
    public static final int FLAG_VIRTUAL = 2;
    public static final int FLAG_WAKE = 1;
    public static final int KEYGUARD_GOING_AWAY_FLAG_NO_WINDOW_ANIMATIONS = 2;
    public static final int KEYGUARD_GOING_AWAY_FLAG_SUBTLE_WINDOW_ANIMATIONS = 8;
    public static final int KEYGUARD_GOING_AWAY_FLAG_TO_LAUNCHER_CLEAR_SNAPSHOT = 16;
    public static final int KEYGUARD_GOING_AWAY_FLAG_TO_SHADE = 1;
    public static final int KEYGUARD_GOING_AWAY_FLAG_UNLOCK_BY_FINGERPRINT = 512;
    public static final int KEYGUARD_GOING_AWAY_FLAG_UNLOCK_BY_UNFOLDING = 32;
    public static final int KEYGUARD_GOING_AWAY_FLAG_WAKE_AND_UNLOCK = 256;
    public static final int KEYGUARD_GOING_AWAY_FLAG_WITH_WALLPAPER = 4;
    public static final int LAYER_OFFSET_THUMBNAIL = 4;
    public static final int NAV_BAR_BOTTOM = 4;
    public static final int NAV_BAR_INVALID = -1;
    public static final int NAV_BAR_LEFT = 1;
    public static final int NAV_BAR_MODE_2BUTTON = 1;
    public static final String NAV_BAR_MODE_2BUTTON_OVERLAY = "com.android.internal.systemui.navbar.twobutton";
    public static final int NAV_BAR_MODE_3BUTTON = 0;
    public static final String NAV_BAR_MODE_3BUTTON_OVERLAY = "com.android.internal.systemui.navbar.threebutton";
    public static final int NAV_BAR_MODE_GESTURAL = 2;
    public static final String NAV_BAR_MODE_GESTURAL_OVERLAY = "com.android.internal.systemui.navbar.gestural";
    public static final int NAV_BAR_RIGHT = 2;
    public static final int NAV_BAR_TOP = 8;
    public static final int OFF_BECAUSE_OF_ADMIN = 1;
    public static final int OFF_BECAUSE_OF_FOLD = 4;
    public static final int OFF_BECAUSE_OF_LID_SWITCH = 4;
    public static final int OFF_BECAUSE_OF_TIMEOUT = 3;
    public static final int OFF_BECAUSE_OF_USER = 2;
    public static final int ON_BECAUSE_OF_APPLICATION = 2;
    public static final int ON_BECAUSE_OF_UNKNOWN = 3;
    public static final int ON_BECAUSE_OF_USER = 1;
    public static final int POLICY_FLAG_TOUCHSCREEN_DEVICE = 2048;
    public static final int PRESENCE_EXTERNAL = 2;
    public static final int PRESENCE_INTERNAL = 1;
    public static final int SCREEN_FREEZE_LAYER_BASE = 2010000;
    public static final int STRICT_MODE_LAYER = 1010000;
    public static final int TYPE_LAYER_MULTIPLIER = 10000;
    public static final int TYPE_LAYER_OFFSET = 1000;
    public static final int WATERMARK_LAYER = 1000000;
    public static final int WINDOW_FREEZE_LAYER = 2000000;
    public static final int WINDOW_LAYER_MULTIPLIER = 5;

    @Retention(RetentionPolicy.SOURCE)
    public @interface OffReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OnReason {
    }

    public interface PointerEventListener {
        void onPointerEvent(MotionEvent motionEvent);
    }

    static int translateSleepReasonToOffReason(int i) {
        if (i == 1) {
            return 1;
        }
        if (i == 2 || i == 9) {
            return 3;
        }
        return i != 13 ? 2 : 4;
    }

    static int translateWakeReasonToOnReason(int i) {
        if (i == 9) {
            return 1;
        }
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return 1;
            case 2:
                return 2;
            default:
                switch (i) {
                    case 15:
                    case 16:
                    case 17:
                        return 1;
                    default:
                        return 3;
                }
        }
    }

    static String onReasonToString(int i) {
        if (i == 1) {
            return "ON_BECAUSE_OF_USER";
        }
        if (i == 2) {
            return "ON_BECAUSE_OF_APPLICATION";
        }
        if (i == 3) {
            return "ON_BECAUSE_OF_UNKNOWN";
        }
        return Integer.toString(i);
    }

    static String offReasonToString(int i) {
        if (i == 1) {
            return "OFF_BECAUSE_OF_ADMIN";
        }
        if (i == 2) {
            return "OFF_BECAUSE_OF_USER";
        }
        if (i == 3) {
            return "OFF_BECAUSE_OF_TIMEOUT";
        }
        if (i == 4) {
            return "OFF_BECAUSE_OF_FOLD";
        }
        return Integer.toString(i);
    }
}
