package com.android.internal.accessibility.util;

import android.content.ComponentName;
import android.content.Context;
import android.provider.Settings;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.internal.util.FrameworkStatsLog;

/* loaded from: classes5.dex */
public final class AccessibilityStatsLogUtils {
    public static int ACCESSIBILITY_PRIVACY_WARNING_STATUS_CLICKED = 2;
    public static int ACCESSIBILITY_PRIVACY_WARNING_STATUS_SERVICE_DISABLED = 3;
    public static int ACCESSIBILITY_PRIVACY_WARNING_STATUS_SHOWN = 1;
    private static final int UNKNOWN_STATUS = 0;

    private static int convertToLoggingMagnificationMode(int i) {
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    return 0;
                }
            }
        }
        return i2;
    }

    private static int convertToLoggingMagnificationScale(float f) {
        return ((int) (f * 10.0f)) * 10;
    }

    private static int convertToLoggingServiceStatus(boolean z) {
        return z ? 1 : 2;
    }

    private AccessibilityStatsLogUtils() {
    }

    public static void logAccessibilityShortcutActivated(Context context, ComponentName componentName, int i) {
        logAccessibilityShortcutActivatedInternal(componentName, convertToLoggingShortcutType(context, i), 0);
    }

    public static void logAccessibilityShortcutActivated(Context context, ComponentName componentName, int i, boolean z) {
        logAccessibilityShortcutActivatedInternal(componentName, convertToLoggingShortcutType(context, i), convertToLoggingServiceStatus(z));
    }

    private static void logAccessibilityShortcutActivatedInternal(ComponentName componentName, int i, int i2) {
        FrameworkStatsLog.write(266, componentName.flattenToString(), i, i2);
    }

    public static void logMagnificationTripleTap(boolean z) {
        FrameworkStatsLog.write(266, AccessibilityShortcutController.MAGNIFICATION_COMPONENT_NAME.flattenToString(), 3, convertToLoggingServiceStatus(z));
    }

    public static void logMagnificationTwoFingerTripleTap(boolean z) {
        FrameworkStatsLog.write(266, AccessibilityShortcutController.MAGNIFICATION_COMPONENT_NAME.flattenToString(), 8, convertToLoggingServiceStatus(z));
    }

    public static void logAccessibilityButtonLongPressStatus(ComponentName componentName) {
        FrameworkStatsLog.write(266, componentName.flattenToString(), 4, 0);
    }

    public static void logMagnificationUsageState(int i, long j, float f) {
        FrameworkStatsLog.write(345, convertToLoggingMagnificationMode(i), j, convertToLoggingMagnificationScale(f));
    }

    public static void logMagnificationModeWithImeOn(int i) {
        FrameworkStatsLog.write(346, convertToLoggingMagnificationMode(i));
    }

    public static void logMagnificationFollowTypingFocusSession(long j) {
        FrameworkStatsLog.write(453, j);
    }

    public static void logMagnificationTripleTapAndHoldSession(long j) {
        FrameworkStatsLog.write(452, j);
    }

    public static void logNonA11yToolServiceWarningReported(String str, int i, long j) {
        FrameworkStatsLog.write(384, str, i, j);
    }

    private static boolean isAccessibilityFloatingMenuEnabled(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_BUTTON_MODE, -1) == 1;
    }

    private static boolean isAccessibilityGestureEnabled(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_BUTTON_MODE, -1) == 2;
    }

    private static int convertToLoggingShortcutType(Context context, int i) {
        if (i == 1) {
            if (isAccessibilityFloatingMenuEnabled(context)) {
                return 5;
            }
            return isAccessibilityGestureEnabled(context) ? 6 : 1;
        }
        if (i == 2) {
            return 2;
        }
        if (i != 16) {
            return i != 32 ? 0 : 6;
        }
        return 9;
    }
}
