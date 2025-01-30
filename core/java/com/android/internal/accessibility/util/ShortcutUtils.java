package com.android.internal.accessibility.util;

import android.content.Context;
import android.p009os.VibrationEffect;
import android.p009os.Vibrator;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.HapticFeedbackConstants;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.C4337R;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.samsung.android.vibrator.VibRune;
import java.util.List;
import java.util.StringJoiner;

/* loaded from: classes4.dex */
public final class ShortcutUtils {
    private static final TextUtils.SimpleStringSplitter sStringColonSplitter = new TextUtils.SimpleStringSplitter(ShortcutConstants.SERVICES_SEPARATOR);

    private ShortcutUtils() {
    }

    public static void optInValueToSettings(Context context, int shortcutType, String componentId) {
        StringJoiner joiner = new StringJoiner(String.valueOf(ShortcutConstants.SERVICES_SEPARATOR));
        String targetKey = convertToKey(shortcutType);
        String targetString = Settings.Secure.getString(context.getContentResolver(), targetKey);
        if (isComponentIdExistingInSettings(context, shortcutType, componentId)) {
            return;
        }
        if (!TextUtils.isEmpty(targetString)) {
            joiner.add(targetString);
        }
        joiner.add(componentId);
        Settings.Secure.putString(context.getContentResolver(), targetKey, joiner.toString());
    }

    public static void optOutValueFromSettings(Context context, int shortcutType, String componentId) {
        StringJoiner joiner = new StringJoiner(String.valueOf(ShortcutConstants.SERVICES_SEPARATOR));
        String targetsKey = convertToKey(shortcutType);
        String targetsValue = Settings.Secure.getString(context.getContentResolver(), targetsKey);
        if (TextUtils.isEmpty(targetsValue)) {
            return;
        }
        sStringColonSplitter.setString(targetsValue);
        while (true) {
            TextUtils.SimpleStringSplitter simpleStringSplitter = sStringColonSplitter;
            if (simpleStringSplitter.hasNext()) {
                String id = simpleStringSplitter.next();
                if (!TextUtils.isEmpty(id) && !componentId.equals(id)) {
                    joiner.add(id);
                }
            } else {
                Settings.Secure.putString(context.getContentResolver(), targetsKey, joiner.toString());
                return;
            }
        }
    }

    public static boolean isComponentIdExistingInSettings(Context context, int shortcutType, String componentId) {
        String id;
        String targetKey = convertToKey(shortcutType);
        String targetString = Settings.Secure.getString(context.getContentResolver(), targetKey);
        if (TextUtils.isEmpty(targetString)) {
            return false;
        }
        sStringColonSplitter.setString(targetString);
        do {
            TextUtils.SimpleStringSplitter simpleStringSplitter = sStringColonSplitter;
            if (!simpleStringSplitter.hasNext()) {
                return false;
            }
            id = simpleStringSplitter.next();
        } while (!componentId.equals(id));
        return true;
    }

    public static boolean isShortcutContained(Context context, int shortcutType, String componentId) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<String> requiredTargets = am.getAccessibilityShortcutTargets(shortcutType);
        return requiredTargets.contains(componentId);
    }

    public static String convertToKey(int type) {
        switch (type) {
            case 1:
                return Settings.Secure.ACCESSIBILITY_BUTTON_TARGETS;
            case 2:
                return Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE;
            case 3:
                return Settings.Secure.ACCESSIBILITY_DIRECT_ACCESS_TARGET_SERVICE;
            case 4:
                return "accessibility_display_magnification_enabled";
            default:
                throw new IllegalArgumentException("Unsupported user shortcut type: " + type);
        }
    }

    public static int convertToUserType(int type) {
        switch (type) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            default:
                throw new IllegalArgumentException("Unsupported shortcut type:" + type);
        }
    }

    public static int getPrimaryDarkColorId(Context context) {
        return context.getColor(C4337R.color.sem_color_primary_dark);
    }

    public static int getSummaryColor(Context context) {
        return context.getColor(C4337R.color.tw_searchview_hint_text_material);
    }

    public static boolean isSupportDCMotorHapticFeedback(Vibrator vibrator) {
        return VibRune.SUPPORT_HAPTIC_FEEDBACK_ON_DC_MOTOR && vibrator.semGetSupportedVibrationType() == 1;
    }

    public static void vibrateDCMotorHapticFeedback(Context context, Vibrator vibrator) {
        boolean hapticEnabled = Settings.System.getIntForUser(context.getContentResolver(), Settings.System.HAPTIC_FEEDBACK_ENABLED, 1, -2) != 0;
        if (hapticEnabled) {
            VibrationEffect effect = VibrationEffect.semCreateWaveform(HapticFeedbackConstants.semGetVibrationIndex(100), -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH);
            vibrator.vibrate(effect);
        }
    }
}
