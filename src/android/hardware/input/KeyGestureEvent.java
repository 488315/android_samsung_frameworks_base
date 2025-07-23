package android.hardware.input;

import android.hardware.input.AppLaunchData;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class KeyGestureEvent {
    public static final int ACTION_GESTURE_COMPLETE = 2;
    public static final int ACTION_GESTURE_LONG_PRESS = 3;
    public static final int ACTION_GESTURE_START = 1;

    @Deprecated
    public static final int DEPRECATED_KEY_GESTURE_TYPE_ACCESSIBILITY_ALL_APPS = 24;

    @Deprecated
    public static final int DEPRECATED_KEY_GESTURE_TYPE_TV_ACCESSIBILITY_SHORTCUT_CHORD = 58;
    public static final int FLAG_CANCELLED = 1;
    public static final int KEY_GESTURE_TYPE_ACCESSIBILITY_DIRECT_ACCESS = 1001;
    public static final int KEY_GESTURE_TYPE_ACCESSIBILITY_SHORTCUT = 60;
    public static final int KEY_GESTURE_TYPE_ACCESSIBILITY_SHORTCUT_CHORD = 55;
    public static final int KEY_GESTURE_TYPE_ACTIVATE_SELECT_TO_SPEAK = 73;
    public static final int KEY_GESTURE_TYPE_ALL_APPS = 21;
    public static final int KEY_GESTURE_TYPE_APP_SWITCH = 4;
    public static final int KEY_GESTURE_TYPE_BACK = 3;
    public static final int KEY_GESTURE_TYPE_BRIGHTNESS_DOWN = 14;
    public static final int KEY_GESTURE_TYPE_BRIGHTNESS_UP = 13;
    public static final int KEY_GESTURE_TYPE_CHANGE_SPLITSCREEN_FOCUS_LEFT = 29;
    public static final int KEY_GESTURE_TYPE_CHANGE_SPLITSCREEN_FOCUS_RIGHT = 30;
    public static final int KEY_GESTURE_TYPE_CLOSE_ALL_DIALOGS = 61;
    public static final int KEY_GESTURE_TYPE_DESKTOP_MODE = 52;
    public static final int KEY_GESTURE_TYPE_GLOBAL_ACTIONS = 57;
    public static final int KEY_GESTURE_TYPE_HOME = 1;
    public static final int KEY_GESTURE_TYPE_KEYBOARD_BACKLIGHT_DOWN = 16;
    public static final int KEY_GESTURE_TYPE_KEYBOARD_BACKLIGHT_TOGGLE = 17;
    public static final int KEY_GESTURE_TYPE_KEYBOARD_BACKLIGHT_UP = 15;
    public static final int KEY_GESTURE_TYPE_LANGUAGE_SWITCH = 23;
    public static final int KEY_GESTURE_TYPE_LAUNCH_APPLICATION = 51;
    public static final int KEY_GESTURE_TYPE_LAUNCH_ASSISTANT = 5;
    public static final int KEY_GESTURE_TYPE_LAUNCH_DEFAULT_BROWSER = 39;
    public static final int KEY_GESTURE_TYPE_LAUNCH_DEFAULT_CALCULATOR = 43;
    public static final int KEY_GESTURE_TYPE_LAUNCH_DEFAULT_CALENDAR = 42;
    public static final int KEY_GESTURE_TYPE_LAUNCH_DEFAULT_CONTACTS = 41;
    public static final int KEY_GESTURE_TYPE_LAUNCH_DEFAULT_EMAIL = 40;
    public static final int KEY_GESTURE_TYPE_LAUNCH_DEFAULT_FILES = 48;
    public static final int KEY_GESTURE_TYPE_LAUNCH_DEFAULT_FITNESS = 50;
    public static final int KEY_GESTURE_TYPE_LAUNCH_DEFAULT_GALLERY = 47;
    public static final int KEY_GESTURE_TYPE_LAUNCH_DEFAULT_MAPS = 45;
    public static final int KEY_GESTURE_TYPE_LAUNCH_DEFAULT_MESSAGING = 46;
    public static final int KEY_GESTURE_TYPE_LAUNCH_DEFAULT_MUSIC = 44;
    public static final int KEY_GESTURE_TYPE_LAUNCH_DEFAULT_WEATHER = 49;
    public static final int KEY_GESTURE_TYPE_LAUNCH_SEARCH = 22;
    public static final int KEY_GESTURE_TYPE_LAUNCH_SYSTEM_SETTINGS = 7;
    public static final int KEY_GESTURE_TYPE_LAUNCH_VOICE_ASSISTANT = 6;
    public static final int KEY_GESTURE_TYPE_LOCK_SCREEN = 32;
    public static final int KEY_GESTURE_TYPE_LOCK_SCREEN_WITH_SHIFT = 1002;
    public static final int KEY_GESTURE_TYPE_LOCK_TASK_MODE_PINNED_RECENT_BACK = 1003;
    public static final int KEY_GESTURE_TYPE_MAXIMIZE_FREEFORM_WINDOW = 74;
    public static final int KEY_GESTURE_TYPE_MEDIA_KEY = 38;
    public static final int KEY_GESTURE_TYPE_MINIMIZE_FREEFORM_WINDOW = 70;
    public static final int KEY_GESTURE_TYPE_MOVE_TO_NEXT_DISPLAY = 62;
    public static final int KEY_GESTURE_TYPE_MULTI_WINDOW_NAVIGATION = 53;
    public static final int KEY_GESTURE_TYPE_OPEN_NOTES = 33;
    public static final int KEY_GESTURE_TYPE_OPEN_SHORTCUT_HELPER = 12;
    public static final int KEY_GESTURE_TYPE_RECENT_APPS = 2;
    public static final int KEY_GESTURE_TYPE_RECENT_APPS_SWITCHER = 54;
    public static final int KEY_GESTURE_TYPE_RINGER_TOGGLE_CHORD = 56;
    public static final int KEY_GESTURE_TYPE_SCREENSHOT_CHORD = 11;
    public static final int KEY_GESTURE_TYPE_SLEEP = 36;
    public static final int KEY_GESTURE_TYPE_SNAP_LEFT_FREEFORM_WINDOW = 68;
    public static final int KEY_GESTURE_TYPE_SNAP_RIGHT_FREEFORM_WINDOW = 69;
    public static final int KEY_GESTURE_TYPE_SPLIT_SCREEN_NAVIGATION_LEFT = 27;
    public static final int KEY_GESTURE_TYPE_SPLIT_SCREEN_NAVIGATION_RIGHT = 28;
    public static final int KEY_GESTURE_TYPE_SYSTEM_MUTE = 26;
    public static final int KEY_GESTURE_TYPE_SYSTEM_NAVIGATION = 35;
    public static final int KEY_GESTURE_TYPE_SYSTEM_RESERVED = -1;
    public static final int KEY_GESTURE_TYPE_TAKE_SCREENSHOT = 10;
    public static final int KEY_GESTURE_TYPE_TOGGLE_BOUNCE_KEYS = 65;
    public static final int KEY_GESTURE_TYPE_TOGGLE_CAPS_LOCK = 25;
    public static final int KEY_GESTURE_TYPE_TOGGLE_DO_NOT_DISTURB = 75;
    public static final int KEY_GESTURE_TYPE_TOGGLE_MAGNIFICATION = 72;
    public static final int KEY_GESTURE_TYPE_TOGGLE_MAXIMIZE_FREEFORM_WINDOW = 71;
    public static final int KEY_GESTURE_TYPE_TOGGLE_MOUSE_KEYS = 67;
    public static final int KEY_GESTURE_TYPE_TOGGLE_NOTIFICATION_PANEL = 8;
    public static final int KEY_GESTURE_TYPE_TOGGLE_POWER = 34;
    public static final int KEY_GESTURE_TYPE_TOGGLE_SLOW_KEYS = 66;
    public static final int KEY_GESTURE_TYPE_TOGGLE_STICKY_KEYS = 64;
    public static final int KEY_GESTURE_TYPE_TOGGLE_TALKBACK = 63;
    public static final int KEY_GESTURE_TYPE_TOGGLE_TASKBAR = 9;
    public static final int KEY_GESTURE_TYPE_TOGGLE_VOICE_ACCESS = 76;
    public static final int KEY_GESTURE_TYPE_TRIGGER_BUG_REPORT = 31;
    public static final int KEY_GESTURE_TYPE_TV_TRIGGER_BUG_REPORT = 59;
    public static final int KEY_GESTURE_TYPE_UNSPECIFIED = 0;
    public static final int KEY_GESTURE_TYPE_VOLUME_DOWN = 19;
    public static final int KEY_GESTURE_TYPE_VOLUME_MUTE = 20;
    public static final int KEY_GESTURE_TYPE_VOLUME_UP = 18;
    public static final int KEY_GESTURE_TYPE_WAKEUP = 37;
    private static final int LOG_EVENT_UNSPECIFIED = 0;
    private AidlKeyGestureEvent mKeyGestureEvent;

    @Retention(RetentionPolicy.SOURCE)
    public @interface KeyGestureType {
    }

    public static boolean isVisibleBackgrounduserAllowedGesture(int i) {
        return (i == 2 || i == 20 || i == 4 || i == 5 || i == 6 || i == 36 || i == 37) ? false : true;
    }

    private static int keyGestureTypeToLogEvent(int i) {
        if (i == 62) {
            return 51;
        }
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
            case 8:
                return 8;
            case 9:
                return 9;
            case 10:
            case 11:
                return 10;
            case 12:
                return 11;
            case 13:
                return 12;
            case 14:
                return 13;
            case 15:
                return 14;
            case 16:
                return 15;
            case 17:
                return 16;
            case 18:
                return 17;
            case 19:
                return 18;
            case 20:
                return 19;
            case 21:
                return 20;
            case 22:
                return 21;
            case 23:
                return 22;
            default:
                switch (i) {
                    case 25:
                        return 24;
                    case 26:
                        return 25;
                    case 27:
                    case 28:
                        return 26;
                    case 29:
                    case 30:
                        return 50;
                    case 31:
                        return 27;
                    case 32:
                        return 28;
                    case 33:
                        return 29;
                    case 34:
                        return 30;
                    case 35:
                        return 31;
                    case 36:
                        return 32;
                    case 37:
                        return 33;
                    case 38:
                        return 34;
                    case 39:
                        return 35;
                    case 40:
                        return 36;
                    case 41:
                        return 37;
                    case 42:
                        return 38;
                    case 43:
                        return 39;
                    case 44:
                        return 40;
                    case 45:
                        return 41;
                    case 46:
                        return 42;
                    case 47:
                        return 43;
                    case 48:
                        return 44;
                    case 49:
                        return 45;
                    case 50:
                        return 46;
                    case 51:
                        return 47;
                    case 52:
                        return 48;
                    case 53:
                        return 49;
                    case 54:
                        return 2;
                    default:
                        return 0;
                }
        }
    }

    public KeyGestureEvent(AidlKeyGestureEvent aidlKeyGestureEvent) {
        this.mKeyGestureEvent = aidlKeyGestureEvent;
    }

    public boolean hasModifiers(int i) {
        return (getModifierState() & i) == i;
    }

    public static class Builder {
        private int mDeviceId = -1;
        private int[] mKeycodes = new int[0];
        private int mModifierState = 0;
        private int mKeyGestureType = 0;
        private int mAction = 2;
        private int mDisplayId = 0;
        private int mFlags = 0;
        private AppLaunchData mAppLaunchData = null;

        public Builder setDeviceId(int i) {
            this.mDeviceId = i;
            return this;
        }

        public Builder setKeycodes(int[] iArr) {
            this.mKeycodes = iArr;
            return this;
        }

        public Builder setModifierState(int i) {
            this.mModifierState = i;
            return this;
        }

        public Builder setKeyGestureType(int i) {
            this.mKeyGestureType = i;
            return this;
        }

        public Builder setAction(int i) {
            this.mAction = i;
            return this;
        }

        public Builder setDisplayId(int i) {
            this.mDisplayId = i;
            return this;
        }

        public Builder setFlags(int i) {
            this.mFlags = i;
            return this;
        }

        public Builder setAppLaunchData(AppLaunchData appLaunchData) {
            this.mAppLaunchData = appLaunchData;
            return this;
        }

        public KeyGestureEvent build() {
            AidlKeyGestureEvent aidlKeyGestureEvent = new AidlKeyGestureEvent();
            aidlKeyGestureEvent.deviceId = this.mDeviceId;
            aidlKeyGestureEvent.keycodes = this.mKeycodes;
            aidlKeyGestureEvent.modifierState = this.mModifierState;
            aidlKeyGestureEvent.gestureType = this.mKeyGestureType;
            aidlKeyGestureEvent.action = this.mAction;
            aidlKeyGestureEvent.displayId = this.mDisplayId;
            aidlKeyGestureEvent.flags = this.mFlags;
            AppLaunchData appLaunchData = this.mAppLaunchData;
            if (appLaunchData != null) {
                if (appLaunchData instanceof AppLaunchData.CategoryData) {
                    aidlKeyGestureEvent.appLaunchCategory = ((AppLaunchData.CategoryData) appLaunchData).getCategory();
                } else if (appLaunchData instanceof AppLaunchData.RoleData) {
                    aidlKeyGestureEvent.appLaunchRole = ((AppLaunchData.RoleData) appLaunchData).getRole();
                } else if (appLaunchData instanceof AppLaunchData.ComponentData) {
                    aidlKeyGestureEvent.appLaunchPackageName = ((AppLaunchData.ComponentData) appLaunchData).getPackageName();
                    aidlKeyGestureEvent.appLaunchClassName = ((AppLaunchData.ComponentData) this.mAppLaunchData).getClassName();
                } else {
                    throw new IllegalArgumentException("AppLaunchData type is invalid!");
                }
            }
            return new KeyGestureEvent(aidlKeyGestureEvent);
        }
    }

    public int getDeviceId() {
        return this.mKeyGestureEvent.deviceId;
    }

    public int[] getKeycodes() {
        return this.mKeyGestureEvent.keycodes;
    }

    public long[] getEventTimes() {
        return this.mKeyGestureEvent.eventTimes;
    }

    public int getModifierState() {
        return this.mKeyGestureEvent.modifierState;
    }

    public int getKeyGestureType() {
        return this.mKeyGestureEvent.gestureType;
    }

    public int getAction() {
        return this.mKeyGestureEvent.action;
    }

    public int getDisplayId() {
        return this.mKeyGestureEvent.displayId;
    }

    public int getFlags() {
        return this.mKeyGestureEvent.flags;
    }

    public boolean isCancelled() {
        return (this.mKeyGestureEvent.flags & 1) != 0;
    }

    public int getLogEvent() {
        if (getKeyGestureType() == 51) {
            return getLogEventFromLaunchAppData(getAppLaunchData());
        }
        return keyGestureTypeToLogEvent(getKeyGestureType());
    }

    public AppLaunchData getAppLaunchData() {
        if (this.mKeyGestureEvent.gestureType != 51) {
            return null;
        }
        return AppLaunchData.createLaunchData(this.mKeyGestureEvent.appLaunchCategory, this.mKeyGestureEvent.appLaunchRole, this.mKeyGestureEvent.appLaunchPackageName, this.mKeyGestureEvent.appLaunchClassName);
    }

    public String toString() {
        return "KeyGestureEvent { deviceId = " + this.mKeyGestureEvent.deviceId + ", keycodes = " + Arrays.toString(this.mKeyGestureEvent.keycodes) + ", modifierState = " + this.mKeyGestureEvent.modifierState + ", keyGestureType = " + keyGestureTypeToString(this.mKeyGestureEvent.gestureType) + ", action = " + this.mKeyGestureEvent.action + ", displayId = " + this.mKeyGestureEvent.displayId + ", flags = " + this.mKeyGestureEvent.flags + ", appLaunchData = " + getAppLaunchData() + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            KeyGestureEvent keyGestureEvent = (KeyGestureEvent) obj;
            if (this.mKeyGestureEvent.deviceId == keyGestureEvent.mKeyGestureEvent.deviceId && Arrays.equals(this.mKeyGestureEvent.keycodes, keyGestureEvent.mKeyGestureEvent.keycodes) && this.mKeyGestureEvent.modifierState == keyGestureEvent.mKeyGestureEvent.modifierState && this.mKeyGestureEvent.gestureType == keyGestureEvent.mKeyGestureEvent.gestureType && this.mKeyGestureEvent.action == keyGestureEvent.mKeyGestureEvent.action && this.mKeyGestureEvent.displayId == keyGestureEvent.mKeyGestureEvent.displayId && this.mKeyGestureEvent.flags == keyGestureEvent.mKeyGestureEvent.flags && Objects.equals(this.mKeyGestureEvent.appLaunchCategory, keyGestureEvent.mKeyGestureEvent.appLaunchCategory) && Objects.equals(this.mKeyGestureEvent.appLaunchRole, keyGestureEvent.mKeyGestureEvent.appLaunchRole) && Objects.equals(this.mKeyGestureEvent.appLaunchPackageName, keyGestureEvent.mKeyGestureEvent.appLaunchPackageName) && Objects.equals(this.mKeyGestureEvent.appLaunchClassName, keyGestureEvent.mKeyGestureEvent.appLaunchClassName)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((((((((this.mKeyGestureEvent.deviceId + 31) * 31) + Arrays.hashCode(this.mKeyGestureEvent.keycodes)) * 31) + this.mKeyGestureEvent.modifierState) * 31) + this.mKeyGestureEvent.gestureType) * 31) + this.mKeyGestureEvent.action) * 31) + this.mKeyGestureEvent.displayId) * 31) + this.mKeyGestureEvent.flags) * 31) + (this.mKeyGestureEvent.appLaunchCategory != null ? this.mKeyGestureEvent.appLaunchCategory.hashCode() : 0)) * 31) + (this.mKeyGestureEvent.appLaunchRole != null ? this.mKeyGestureEvent.appLaunchRole.hashCode() : 0)) * 31) + (this.mKeyGestureEvent.appLaunchPackageName != null ? this.mKeyGestureEvent.appLaunchPackageName.hashCode() : 0)) * 31) + (this.mKeyGestureEvent.appLaunchClassName != null ? this.mKeyGestureEvent.appLaunchClassName.hashCode() : 0);
    }

    private static int getLogEventFromLaunchAppData(AppLaunchData appLaunchData) {
        if (appLaunchData == null) {
            return 0;
        }
        if (appLaunchData instanceof AppLaunchData.CategoryData) {
            return getLogEventFromSelectorCategory(((AppLaunchData.CategoryData) appLaunchData).getCategory());
        }
        if (appLaunchData instanceof AppLaunchData.RoleData) {
            return getLogEventFromRole(((AppLaunchData.RoleData) appLaunchData).getRole());
        }
        if (appLaunchData instanceof AppLaunchData.ComponentData) {
            return 47;
        }
        throw new IllegalArgumentException("AppLaunchData type is invalid!");
    }

    private static int getLogEventFromSelectorCategory(String str) {
        str.hashCode();
        switch (str) {
            case "android.intent.category.APP_MAPS":
                return 41;
            case "android.intent.category.APP_BROWSER":
                return 35;
            case "android.intent.category.APP_WEATHER":
                return 45;
            case "android.intent.category.APP_MESSAGING":
                return 42;
            case "android.intent.category.APP_CONTACTS":
                return 37;
            case "android.intent.category.APP_EMAIL":
                return 36;
            case "android.intent.category.APP_FILES":
                return 44;
            case "android.intent.category.APP_MUSIC":
                return 40;
            case "android.intent.category.APP_CALCULATOR":
                return 39;
            case "android.intent.category.APP_CALENDAR":
                return 38;
            case "android.intent.category.APP_FITNESS":
                return 46;
            case "android.intent.category.APP_GALLERY":
                return 43;
            default:
                return 0;
        }
    }

    private static int getLogEventFromRole(String str) {
        if ("android.app.role.BROWSER".equals(str)) {
            return 35;
        }
        return "android.app.role.SMS".equals(str) ? 42 : 0;
    }

    private static String keyGestureTypeToString(int i) {
        if (i == 1002) {
            return "KEY_GESTURE_TYPE_LOCK_SCREEN_WITH_SHIFT";
        }
        if (i != 1003) {
            switch (i) {
                case -1:
                    return "KEY_GESTURE_TYPE_SYSTEM_RESERVED";
                case 0:
                    return "KEY_GESTURE_TYPE_UNSPECIFIED";
                case 1:
                    return "KEY_GESTURE_TYPE_HOME";
                case 2:
                    return "KEY_GESTURE_TYPE_RECENT_APPS";
                case 3:
                    return "KEY_GESTURE_TYPE_BACK";
                case 4:
                    return "KEY_GESTURE_TYPE_APP_SWITCH";
                case 5:
                    return "KEY_GESTURE_TYPE_LAUNCH_ASSISTANT";
                case 6:
                    return "KEY_GESTURE_TYPE_LAUNCH_VOICE_ASSISTANT";
                case 7:
                    return "KEY_GESTURE_TYPE_LAUNCH_SYSTEM_SETTINGS";
                case 8:
                    return "KEY_GESTURE_TYPE_TOGGLE_NOTIFICATION_PANEL";
                case 9:
                    return "KEY_GESTURE_TYPE_TOGGLE_TASKBAR";
                case 10:
                    return "KEY_GESTURE_TYPE_TAKE_SCREENSHOT";
                case 11:
                    return "KEY_GESTURE_TYPE_SCREENSHOT_CHORD";
                case 12:
                    return "KEY_GESTURE_TYPE_OPEN_SHORTCUT_HELPER";
                case 13:
                    return "KEY_GESTURE_TYPE_BRIGHTNESS_UP";
                case 14:
                    return "KEY_GESTURE_TYPE_BRIGHTNESS_DOWN";
                case 15:
                    return "KEY_GESTURE_TYPE_KEYBOARD_BACKLIGHT_UP";
                case 16:
                    return "KEY_GESTURE_TYPE_KEYBOARD_BACKLIGHT_DOWN";
                case 17:
                    return "KEY_GESTURE_TYPE_KEYBOARD_BACKLIGHT_TOGGLE";
                case 18:
                    return "KEY_GESTURE_TYPE_VOLUME_UP";
                case 19:
                    return "KEY_GESTURE_TYPE_VOLUME_DOWN";
                case 20:
                    return "KEY_GESTURE_TYPE_VOLUME_MUTE";
                case 21:
                    return "KEY_GESTURE_TYPE_ALL_APPS";
                case 22:
                    return "KEY_GESTURE_TYPE_LAUNCH_SEARCH";
                case 23:
                    return "KEY_GESTURE_TYPE_LANGUAGE_SWITCH";
                default:
                    switch (i) {
                        case 25:
                            return "KEY_GESTURE_TYPE_TOGGLE_CAPS_LOCK";
                        case 26:
                            return "KEY_GESTURE_TYPE_SYSTEM_MUTE";
                        case 27:
                            return "KEY_GESTURE_TYPE_SPLIT_SCREEN_NAVIGATION_LEFT";
                        case 28:
                            return "KEY_GESTURE_TYPE_SPLIT_SCREEN_NAVIGATION_RIGHT";
                        case 29:
                            return "KEY_GESTURE_TYPE_CHANGE_SPLITSCREEN_FOCUS_LEFT";
                        case 30:
                            return "KEY_GESTURE_TYPE_CHANGE_SPLITSCREEN_FOCUS_RIGHT";
                        case 31:
                            return "KEY_GESTURE_TYPE_TRIGGER_BUG_REPORT";
                        case 32:
                            return "KEY_GESTURE_TYPE_LOCK_SCREEN";
                        case 33:
                            return "KEY_GESTURE_TYPE_OPEN_NOTES";
                        case 34:
                            return "KEY_GESTURE_TYPE_TOGGLE_POWER";
                        case 35:
                            return "KEY_GESTURE_TYPE_SYSTEM_NAVIGATION";
                        case 36:
                            return "KEY_GESTURE_TYPE_SLEEP";
                        case 37:
                            return "KEY_GESTURE_TYPE_WAKEUP";
                        case 38:
                            return "KEY_GESTURE_TYPE_MEDIA_KEY";
                        case 39:
                            return "KEY_GESTURE_TYPE_LAUNCH_DEFAULT_BROWSER";
                        case 40:
                            return "KEY_GESTURE_TYPE_LAUNCH_DEFAULT_EMAIL";
                        case 41:
                            return "KEY_GESTURE_TYPE_LAUNCH_DEFAULT_CONTACTS";
                        case 42:
                            return "KEY_GESTURE_TYPE_LAUNCH_DEFAULT_CALENDAR";
                        case 43:
                            return "KEY_GESTURE_TYPE_LAUNCH_DEFAULT_CALCULATOR";
                        case 44:
                            return "KEY_GESTURE_TYPE_LAUNCH_DEFAULT_MUSIC";
                        case 45:
                            return "KEY_GESTURE_TYPE_LAUNCH_DEFAULT_MAPS";
                        case 46:
                            return "KEY_GESTURE_TYPE_LAUNCH_DEFAULT_MESSAGING";
                        case 47:
                            return "KEY_GESTURE_TYPE_LAUNCH_DEFAULT_GALLERY";
                        case 48:
                            return "KEY_GESTURE_TYPE_LAUNCH_DEFAULT_FILES";
                        case 49:
                            return "KEY_GESTURE_TYPE_LAUNCH_DEFAULT_WEATHER";
                        case 50:
                            return "KEY_GESTURE_TYPE_LAUNCH_DEFAULT_FITNESS";
                        case 51:
                            return "KEY_GESTURE_TYPE_LAUNCH_APPLICATION";
                        case 52:
                            return "KEY_GESTURE_TYPE_DESKTOP_MODE";
                        case 53:
                            return "KEY_GESTURE_TYPE_MULTI_WINDOW_NAVIGATION";
                        case 54:
                            return "KEY_GESTURE_TYPE_RECENT_APPS_SWITCHER";
                        case 55:
                            return "KEY_GESTURE_TYPE_ACCESSIBILITY_SHORTCUT_CHORD";
                        case 56:
                            return "KEY_GESTURE_TYPE_RINGER_TOGGLE_CHORD";
                        case 57:
                            return "KEY_GESTURE_TYPE_GLOBAL_ACTIONS";
                        default:
                            switch (i) {
                                case 59:
                                    return "KEY_GESTURE_TYPE_TV_TRIGGER_BUG_REPORT";
                                case 60:
                                    return "KEY_GESTURE_TYPE_ACCESSIBILITY_SHORTCUT";
                                case 61:
                                    return "KEY_GESTURE_TYPE_CLOSE_ALL_DIALOGS";
                                case 62:
                                    return "KEY_GESTURE_TYPE_MOVE_TO_NEXT_DISPLAY";
                                case 63:
                                    return "KEY_GESTURE_TYPE_TOGGLE_TALKBACK";
                                case 64:
                                    return "KEY_GESTURE_TYPE_TOGGLE_STICKY_KEYS";
                                case 65:
                                    return "KEY_GESTURE_TYPE_TOGGLE_BOUNCE_KEYS";
                                case 66:
                                    return "KEY_GESTURE_TYPE_TOGGLE_SLOW_KEYS";
                                case 67:
                                    return "KEY_GESTURE_TYPE_TOGGLE_MOUSE_KEYS";
                                case 68:
                                    return "KEY_GESTURE_TYPE_SNAP_LEFT_FREEFORM_WINDOW";
                                case 69:
                                    return "KEY_GESTURE_TYPE_SNAP_RIGHT_FREEFORM_WINDOW";
                                case 70:
                                    return "KEY_GESTURE_TYPE_MINIMIZE_FREEFORM_WINDOW";
                                case 71:
                                    return "KEY_GESTURE_TYPE_TOGGLE_MAXIMIZE_FREEFORM_WINDOW";
                                case 72:
                                    return "KEY_GESTURE_TYPE_TOGGLE_MAGNIFICATION";
                                case 73:
                                    return "KEY_GESTURE_TYPE_ACTIVATE_SELECT_TO_SPEAK";
                                case 74:
                                    return "KEY_GESTURE_TYPE_MAXIMIZE_FREEFORM_WINDOW";
                                case 75:
                                    return "KEY_GESTURE_TYPE_TOGGLE_DO_NOT_DISTURB";
                                case 76:
                                    return "KEY_GESTURE_TYPE_TOGGLE_VOICE_ACCESS";
                                default:
                                    return Integer.toHexString(i);
                            }
                    }
            }
        }
        return "KEY_GESTURE_TYPE_LOCK_TASK_MODE_PINNED_RECENT_BACK";
    }
}
