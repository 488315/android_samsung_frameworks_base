package com.android.server.input;

import android.hardware.input.KeyboardLayout;
import android.hardware.input.KeyboardLayoutSelectionResult;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.view.InputDevice;

import com.android.internal.util.FrameworkStatsLog;

import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class KeyboardMetricsCollector {
    public static final boolean DEBUG = Log.isLoggable("KeyboardMetricCollector", 3);
    public static final String DEFAULT_LANGUAGE_TAG = "None";
    static final String DEFAULT_LAYOUT_NAME = "Default";

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KeyboardConfigurationEvent {
        public final InputDevice mInputDevice;
        public final boolean mIsFirstConfiguration;
        public final List mLayoutConfigurations;

        public KeyboardConfigurationEvent(InputDevice inputDevice, boolean z, List list) {
            this.mInputDevice = inputDevice;
            this.mIsFirstConfiguration = z;
            this.mLayoutConfigurations = list;
        }

        public final String toString() {
            return "InputDevice = {VendorId = "
                    + Integer.toHexString(this.mInputDevice.getVendorId())
                    + ", ProductId = "
                    + Integer.toHexString(this.mInputDevice.getProductId())
                    + ", Device Bus = "
                    + Integer.toHexString(this.mInputDevice.getDeviceBus())
                    + "}, isFirstConfiguration = "
                    + this.mIsFirstConfiguration
                    + ", LayoutConfigurations = "
                    + this.mLayoutConfigurations;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum KeyboardLogEvent {
        /* JADX INFO: Fake field, exist only in values array */
        EF0("UNSPECIFIED", "INVALID_KEYBOARD_EVENT"),
        HOME("HOME", "HOME"),
        RECENT_APPS("RECENT_APPS", "RECENT_APPS"),
        BACK("BACK", "BACK"),
        APP_SWITCH("APP_SWITCH", "APP_SWITCH"),
        LAUNCH_ASSISTANT("LAUNCH_ASSISTANT", "LAUNCH_ASSISTANT"),
        LAUNCH_VOICE_ASSISTANT("LAUNCH_VOICE_ASSISTANT", "LAUNCH_VOICE_ASSISTANT"),
        LAUNCH_SYSTEM_SETTINGS("LAUNCH_SYSTEM_SETTINGS", "LAUNCH_SYSTEM_SETTINGS"),
        TOGGLE_NOTIFICATION_PANEL("TOGGLE_NOTIFICATION_PANEL", "TOGGLE_NOTIFICATION_PANEL"),
        /* JADX INFO: Fake field, exist only in values array */
        EF9("TOGGLE_TASKBAR", "TOGGLE_TASKBAR"),
        TAKE_SCREENSHOT("TAKE_SCREENSHOT", "TAKE_SCREENSHOT"),
        OPEN_SHORTCUT_HELPER("OPEN_SHORTCUT_HELPER", "OPEN_SHORTCUT_HELPER"),
        BRIGHTNESS_UP("BRIGHTNESS_UP", "BRIGHTNESS_UP"),
        BRIGHTNESS_DOWN("BRIGHTNESS_DOWN", "BRIGHTNESS_DOWN"),
        KEYBOARD_BACKLIGHT_UP("KEYBOARD_BACKLIGHT_UP", "KEYBOARD_BACKLIGHT_UP"),
        KEYBOARD_BACKLIGHT_DOWN("KEYBOARD_BACKLIGHT_DOWN", "KEYBOARD_BACKLIGHT_DOWN"),
        KEYBOARD_BACKLIGHT_TOGGLE("KEYBOARD_BACKLIGHT_TOGGLE", "KEYBOARD_BACKLIGHT_TOGGLE"),
        VOLUME_UP("VOLUME_UP", "VOLUME_UP"),
        VOLUME_DOWN("VOLUME_DOWN", "VOLUME_DOWN"),
        VOLUME_MUTE("VOLUME_MUTE", "VOLUME_MUTE"),
        ALL_APPS("ALL_APPS", "ALL_APPS"),
        LAUNCH_SEARCH("LAUNCH_SEARCH", "LAUNCH_SEARCH"),
        LANGUAGE_SWITCH("LANGUAGE_SWITCH", "LANGUAGE_SWITCH"),
        ACCESSIBILITY_ALL_APPS("ACCESSIBILITY_ALL_APPS", "ACCESSIBILITY_ALL_APPS"),
        TOGGLE_CAPS_LOCK("TOGGLE_CAPS_LOCK", "TOGGLE_CAPS_LOCK"),
        SYSTEM_MUTE("SYSTEM_MUTE", "SYSTEM_MUTE"),
        SPLIT_SCREEN_NAVIGATION("SPLIT_SCREEN_NAVIGATION", "SPLIT_SCREEN_NAVIGATION"),
        CHANGE_SPLITSCREEN_FOCUS("CHANGE_SPLITSCREEN_FOCUS", "CHANGE_SPLITSCREEN_FOCUS"),
        TRIGGER_BUG_REPORT("TRIGGER_BUG_REPORT", "TRIGGER_BUG_REPORT"),
        LOCK_SCREEN("LOCK_SCREEN", "LOCK_SCREEN"),
        OPEN_NOTES("OPEN_NOTES", "OPEN_NOTES"),
        TOGGLE_POWER("TOGGLE_POWER", "TOGGLE_POWER"),
        SYSTEM_NAVIGATION("SYSTEM_NAVIGATION", "SYSTEM_NAVIGATION"),
        SLEEP("SLEEP", "SLEEP"),
        WAKEUP("WAKEUP", "WAKEUP"),
        MEDIA_KEY("MEDIA_KEY", "MEDIA_KEY"),
        LAUNCH_DEFAULT_BROWSER("LAUNCH_DEFAULT_BROWSER", "LAUNCH_DEFAULT_BROWSER"),
        LAUNCH_DEFAULT_EMAIL("LAUNCH_DEFAULT_EMAIL", "LAUNCH_DEFAULT_EMAIL"),
        LAUNCH_DEFAULT_CONTACTS("LAUNCH_DEFAULT_CONTACTS", "LAUNCH_DEFAULT_CONTACTS"),
        LAUNCH_DEFAULT_CALENDAR("LAUNCH_DEFAULT_CALENDAR", "LAUNCH_DEFAULT_CALENDAR"),
        LAUNCH_DEFAULT_CALCULATOR("LAUNCH_DEFAULT_CALCULATOR", "LAUNCH_DEFAULT_CALCULATOR"),
        LAUNCH_DEFAULT_MUSIC("LAUNCH_DEFAULT_MUSIC", "LAUNCH_DEFAULT_MUSIC"),
        LAUNCH_DEFAULT_MAPS("LAUNCH_DEFAULT_MAPS", "LAUNCH_DEFAULT_MAPS"),
        LAUNCH_DEFAULT_MESSAGING("LAUNCH_DEFAULT_MESSAGING", "LAUNCH_DEFAULT_MESSAGING"),
        LAUNCH_DEFAULT_GALLERY("LAUNCH_DEFAULT_GALLERY", "LAUNCH_DEFAULT_GALLERY"),
        LAUNCH_DEFAULT_FILES("LAUNCH_DEFAULT_FILES", "LAUNCH_DEFAULT_FILES"),
        LAUNCH_DEFAULT_WEATHER("LAUNCH_DEFAULT_WEATHER", "LAUNCH_DEFAULT_WEATHER"),
        LAUNCH_DEFAULT_FITNESS("LAUNCH_DEFAULT_FITNESS", "LAUNCH_DEFAULT_FITNESS"),
        LAUNCH_APPLICATION_BY_PACKAGE_NAME(
                "LAUNCH_APPLICATION_BY_PACKAGE_NAME", "LAUNCH_APPLICATION_BY_PACKAGE_NAME"),
        DESKTOP_MODE("DESKTOP_MODE", "DESKTOP_MODE"),
        MULTI_WINDOW_NAVIGATION("MULTI_WINDOW_NAVIGATION", "MULTIWINDOW_NAVIGATION");

        public static final SparseArray VALUE_TO_ENUM_MAP = new SparseArray();
        private final String mName;
        private final int mValue;

        static {
            for (KeyboardLogEvent keyboardLogEvent : values()) {
                VALUE_TO_ENUM_MAP.put(keyboardLogEvent.mValue, keyboardLogEvent);
            }
        }

        KeyboardLogEvent(String str, String str2) {
            this.mValue = r2;
            this.mName = str2;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:49:0x00c6  */
        /* JADX WARN: Removed duplicated region for block: B:52:0x00ed A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:55:? A[LOOP:0: B:8:0x001b->B:55:?, LOOP_END, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:56:0x00c8  */
        /* JADX WARN: Removed duplicated region for block: B:58:0x00cb  */
        /* JADX WARN: Removed duplicated region for block: B:60:0x00ce  */
        /* JADX WARN: Removed duplicated region for block: B:62:0x00d1  */
        /* JADX WARN: Removed duplicated region for block: B:64:0x00d4  */
        /* JADX WARN: Removed duplicated region for block: B:66:0x00d7  */
        /* JADX WARN: Removed duplicated region for block: B:68:0x00da  */
        /* JADX WARN: Removed duplicated region for block: B:70:0x00dd  */
        /* JADX WARN: Removed duplicated region for block: B:72:0x00e0  */
        /* JADX WARN: Removed duplicated region for block: B:74:0x00e2  */
        /* JADX WARN: Removed duplicated region for block: B:76:0x00e5  */
        /* JADX WARN: Removed duplicated region for block: B:78:0x00e7  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent
                getLogEventFromIntent(android.content.Intent r7) {
            /*
                Method dump skipped, instructions count: 406
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.input.KeyboardMetricsCollector.KeyboardLogEvent.getLogEventFromIntent(android.content.Intent):com.android.server.input.KeyboardMetricsCollector$KeyboardLogEvent");
        }

        public final int getIntValue() {
            return this.mValue;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class LayoutConfiguration {
        public final String imeLanguageTag;
        public final int imeLayoutType;
        public final String keyboardLanguageTag;
        public final String keyboardLayoutName;
        public final int keyboardLayoutType;
        public final int layoutSelectionCriteria;

        public LayoutConfiguration(int i, int i2, String str, String str2, String str3, int i3) {
            this.keyboardLayoutType = i;
            this.keyboardLanguageTag = str;
            this.keyboardLayoutName = str2;
            this.layoutSelectionCriteria = i2;
            this.imeLayoutType = i3;
            this.imeLanguageTag = str3;
        }

        public final String toString() {
            return "{keyboardLanguageTag = "
                    + this.keyboardLanguageTag
                    + " keyboardLayoutType = "
                    + KeyboardLayout.LayoutType.getLayoutNameFromValue(this.keyboardLayoutType)
                    + " keyboardLayoutName = "
                    + this.keyboardLayoutName
                    + " layoutSelectionCriteria = "
                    + KeyboardLayoutSelectionResult.layoutSelectionCriteriaToString(
                            this.layoutSelectionCriteria)
                    + " imeLanguageTag = "
                    + this.imeLanguageTag
                    + " imeLayoutType = "
                    + KeyboardLayout.LayoutType.getLayoutNameFromValue(this.imeLayoutType)
                    + "}";
        }
    }

    public static void logKeyboardSystemsEventReportedAtom(
            InputDevice inputDevice, KeyboardLogEvent keyboardLogEvent, int i, int... iArr) {
        if (inputDevice == null || inputDevice.isVirtual() || !inputDevice.isFullKeyboard()) {
            return;
        }
        if (keyboardLogEvent == null) {
            Slog.w(
                    "KeyboardMetricCollector",
                    "Invalid keyboard event logging, keycode = "
                            + Arrays.toString(iArr)
                            + ", modifier state = "
                            + i);
            return;
        }
        FrameworkStatsLog.write(
                FrameworkStatsLog.KEYBOARD_SYSTEMS_EVENT_REPORTED,
                inputDevice.getVendorId(),
                inputDevice.getProductId(),
                keyboardLogEvent.getIntValue(),
                iArr,
                i,
                inputDevice.getDeviceBus());
        if (DEBUG) {
            Slog.d(
                    "KeyboardMetricCollector",
                    "Logging Keyboard system event: " + keyboardLogEvent.mName);
        }
    }
}
