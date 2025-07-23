package com.android.internal.accessibility.util;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import android.view.HapticFeedbackConstants;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.R;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.samsung.android.vibrator.VibRune;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

/* loaded from: classes5.dex */
public final class ShortcutUtils {
    private static final String TAG = "AccessibilityShortcutUtils";
    private static final TextUtils.SimpleStringSplitter sStringColonSplitter = new TextUtils.SimpleStringSplitter(ShortcutConstants.SERVICES_SEPARATOR);

    private ShortcutUtils() {
    }

    @Deprecated
    public static void optInValueToSettings(Context context, int i, String str) {
        StringJoiner stringJoiner = new StringJoiner(String.valueOf(ShortcutConstants.SERVICES_SEPARATOR));
        String convertToKey = convertToKey(i);
        String string = Settings.Secure.getString(context.getContentResolver(), convertToKey);
        if (isComponentIdExistingInSettings(context, i, str)) {
            return;
        }
        if (!TextUtils.isEmpty(string)) {
            stringJoiner.add(string);
        }
        stringJoiner.add(str);
        Settings.Secure.putString(context.getContentResolver(), convertToKey, stringJoiner.toString());
    }

    @Deprecated
    public static void optOutValueFromSettings(Context context, int i, String str) {
        StringJoiner stringJoiner = new StringJoiner(String.valueOf(ShortcutConstants.SERVICES_SEPARATOR));
        String convertToKey = convertToKey(i);
        String string = Settings.Secure.getString(context.getContentResolver(), convertToKey);
        if (TextUtils.isEmpty(string)) {
            return;
        }
        sStringColonSplitter.setString(string);
        while (true) {
            TextUtils.SimpleStringSplitter simpleStringSplitter = sStringColonSplitter;
            if (simpleStringSplitter.hasNext()) {
                String next = simpleStringSplitter.next();
                if (!TextUtils.isEmpty(next) && !str.equals(next)) {
                    stringJoiner.add(next);
                }
            } else {
                Settings.Secure.putString(context.getContentResolver(), convertToKey, stringJoiner.toString());
                return;
            }
        }
    }

    public static boolean isComponentIdExistingInSettings(Context context, int i, String str) {
        TextUtils.SimpleStringSplitter simpleStringSplitter;
        String string = Settings.Secure.getString(context.getContentResolver(), convertToKey(i));
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        sStringColonSplitter.setString(string);
        do {
            simpleStringSplitter = sStringColonSplitter;
            if (!simpleStringSplitter.hasNext()) {
                return false;
            }
        } while (!str.equals(simpleStringSplitter.next()));
        return true;
    }

    public static boolean isShortcutContained(Context context, int i, String str) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        if (accessibilityManager != null) {
            return accessibilityManager.getAccessibilityShortcutTargets(i).contains(str);
        }
        return false;
    }

    public static int getEnabledShortcutTypes(Context context, String str) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        if (accessibilityManager == null) {
            return 0;
        }
        int i = 0;
        for (int i2 : ShortcutConstants.USER_SHORTCUT_TYPES) {
            if (accessibilityManager.getAccessibilityShortcutTargets(i2).contains(str)) {
                i |= i2;
            }
        }
        return i;
    }

    public static String convertToKey(int i) {
        if (i == 1) {
            return Settings.Secure.ACCESSIBILITY_BUTTON_TARGETS;
        }
        if (i == 2) {
            return Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE;
        }
        if (i == 4) {
            return "accessibility_display_magnification_enabled";
        }
        if (i == 8) {
            return Settings.Secure.ACCESSIBILITY_MAGNIFICATION_TWO_FINGER_TRIPLE_TAP_ENABLED;
        }
        if (i == 16) {
            return Settings.Secure.ACCESSIBILITY_QS_TARGETS;
        }
        if (i == 32) {
            return Settings.Secure.ACCESSIBILITY_GESTURE_TARGETS;
        }
        if (i == 64) {
            return Settings.Secure.ACCESSIBILITY_KEY_GESTURE_TARGETS;
        }
        if (i == 512) {
            return Settings.Secure.ACCESSIBILITY_DIRECT_ACCESS_TARGET_SERVICE;
        }
        throw new IllegalArgumentException("Unsupported user shortcut type: " + i);
    }

    public static int convertToType(String str) {
        str.hashCode();
        switch (str) {
            case "accessibility_shortcut_target_service":
                return 2;
            case "accessibility_gesture_targets":
                return 32;
            case "accessibility_qs_targets":
                return 16;
            case "accessibility_button_targets":
                return 1;
            case "accessibility_magnification_two_finger_triple_tap_enabled":
                return 8;
            case "accessibility_key_gesture_targets":
                return 64;
            case "accessibility_display_magnification_enabled":
                return 4;
            case "accessibility_direct_access_target_service":
                return 512;
            default:
                throw new IllegalArgumentException("Unsupported user shortcut key: " + str);
        }
    }

    public static void updateInvisibleToggleAccessibilityServiceEnableState(Context context, Set<String> set, int i) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        if (accessibilityManager == null) {
            return;
        }
        List<AccessibilityServiceInfo> installedAccessibilityServiceList = accessibilityManager.getInstalledAccessibilityServiceList();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (AccessibilityServiceInfo accessibilityServiceInfo : installedAccessibilityServiceList) {
            if (AccessibilityUtils.getAccessibilityServiceFragmentType(accessibilityServiceInfo) == 1) {
                linkedHashSet.add(accessibilityServiceInfo.getComponentName().flattenToString());
            }
        }
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        for (int i2 : ShortcutConstants.USER_SHORTCUT_TYPES) {
            linkedHashSet2.addAll(getShortcutTargetsFromSettings(context, i2, i));
        }
        for (String str : set) {
            if (linkedHashSet.contains(str)) {
                AccessibilityUtils.setAccessibilityServiceState(context, ComponentName.unflattenFromString(str), linkedHashSet2.contains(str), i);
            }
        }
    }

    public static Set<String> getShortcutTargetsFromSettings(Context context, int i, int i2) {
        String convertToKey = convertToKey(i);
        if ("accessibility_display_magnification_enabled".equals(convertToKey) || Settings.Secure.ACCESSIBILITY_MAGNIFICATION_TWO_FINGER_TRIPLE_TAP_ENABLED.equals(convertToKey)) {
            if (Settings.Secure.getIntForUser(context.getContentResolver(), convertToKey, 0, i2) == 1) {
                return Set.of("com.android.server.accessibility.MagnificationController");
            }
            return Collections.EMPTY_SET;
        }
        String stringForUser = Settings.Secure.getStringForUser(context.getContentResolver(), convertToKey, i2);
        if (TextUtils.isEmpty(stringForUser)) {
            return Collections.EMPTY_SET;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        sStringColonSplitter.setString(stringForUser);
        while (true) {
            TextUtils.SimpleStringSplitter simpleStringSplitter = sStringColonSplitter;
            if (simpleStringSplitter.hasNext()) {
                linkedHashSet.add(simpleStringSplitter.next());
            } else {
                return Collections.unmodifiableSet(linkedHashSet);
            }
        }
    }

    public static int getButtonMode(Context context, int i) {
        return Settings.Secure.getIntForUser(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_BUTTON_MODE, -1, i);
    }

    public static boolean setButtonMode(Context context, int i, int i2) {
        if (getButtonMode(context, i2) == i) {
            return false;
        }
        if ((i & 3) != i) {
            Slog.w(TAG, "Tried to set button mode to unexpected value " + i);
            return false;
        }
        return Settings.Secure.putIntForUser(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_BUTTON_MODE, i, i2);
    }

    public static int getPrimaryDarkColorId(Context context) {
        return context.getColor(R.color.sem_color_primary_dark);
    }

    public static int getSummaryColor(Context context) {
        return context.getColor(R.color.tw_searchview_hint_text_material);
    }

    public static boolean isSupportDCMotorHapticFeedback(Vibrator vibrator) {
        return VibRune.SUPPORT_HAPTIC_FEEDBACK_ON_DC_MOTOR && vibrator.semGetSupportedVibrationType() == 1;
    }

    public static void vibrateDCMotorHapticFeedback(Context context, Vibrator vibrator) {
        if (Settings.System.getIntForUser(context.getContentResolver(), Settings.System.HAPTIC_FEEDBACK_ENABLED, 1, -2) != 0) {
            vibrator.vibrate(VibrationEffect.semCreateWaveform(HapticFeedbackConstants.semGetVibrationIndex(100), -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH));
        }
    }
}
