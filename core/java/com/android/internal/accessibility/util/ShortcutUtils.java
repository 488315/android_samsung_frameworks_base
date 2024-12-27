package com.android.internal.accessibility.util;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.text.TextUtils;
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

public final class ShortcutUtils {
    private static final TextUtils.SimpleStringSplitter sStringColonSplitter =
            new TextUtils.SimpleStringSplitter(ShortcutConstants.SERVICES_SEPARATOR);

    private ShortcutUtils() {}

    @Deprecated
    public static void optInValueToSettings(Context context, int shortcutType, String componentId) {
        StringJoiner joiner =
                new StringJoiner(String.valueOf(ShortcutConstants.SERVICES_SEPARATOR));
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

    @Deprecated
    public static void optOutValueFromSettings(
            Context context, int shortcutType, String componentId) {
        StringJoiner joiner =
                new StringJoiner(String.valueOf(ShortcutConstants.SERVICES_SEPARATOR));
        String targetsKey = convertToKey(shortcutType);
        String targetsValue = Settings.Secure.getString(context.getContentResolver(), targetsKey);
        if (TextUtils.isEmpty(targetsValue)) {
            return;
        }
        sStringColonSplitter.setString(targetsValue);
        while (sStringColonSplitter.hasNext()) {
            String id = sStringColonSplitter.next();
            if (!TextUtils.isEmpty(id) && !componentId.equals(id)) {
                joiner.add(id);
            }
        }
        Settings.Secure.putString(context.getContentResolver(), targetsKey, joiner.toString());
    }

    public static boolean isComponentIdExistingInSettings(
            Context context, int shortcutType, String componentId) {
        String targetKey = convertToKey(shortcutType);
        String targetString = Settings.Secure.getString(context.getContentResolver(), targetKey);
        if (TextUtils.isEmpty(targetString)) {
            return false;
        }
        sStringColonSplitter.setString(targetString);
        while (sStringColonSplitter.hasNext()) {
            String id = sStringColonSplitter.next();
            if (componentId.equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isShortcutContained(
            Context context, int shortcutType, String componentId) {
        AccessibilityManager am =
                (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<String> requiredTargets = am.getAccessibilityShortcutTargets(shortcutType);
        return requiredTargets.contains(componentId);
    }

    public static String convertToKey(int type) {
        switch (type) {
            case 1:
                return Settings.Secure.ACCESSIBILITY_BUTTON_TARGETS;
            case 2:
                return Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE;
            case 4:
                return "accessibility_display_magnification_enabled";
            case 8:
                return Settings.Secure.ACCESSIBILITY_MAGNIFICATION_TWO_FINGER_TRIPLE_TAP_ENABLED;
            case 16:
                return Settings.Secure.ACCESSIBILITY_QS_TARGETS;
            case 512:
                return Settings.Secure.ACCESSIBILITY_DIRECT_ACCESS_TARGET_SERVICE;
            default:
                throw new IllegalArgumentException("Unsupported user shortcut type: " + type);
        }
    }

    public static void updateInvisibleToggleAccessibilityServiceEnableState(
            Context context, Set<String> componentNames, int userId) {
        AccessibilityManager am =
                (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (am == null) {
            return;
        }
        List<AccessibilityServiceInfo> installedServices =
                am.getInstalledAccessibilityServiceList();
        Set<String> invisibleToggleServices = new LinkedHashSet<>();
        for (AccessibilityServiceInfo serviceInfo : installedServices) {
            if (AccessibilityUtils.getAccessibilityServiceFragmentType(serviceInfo) == 1) {
                invisibleToggleServices.add(serviceInfo.getComponentName().flattenToString());
            }
        }
        Set<String> servicesWithShortcuts = new LinkedHashSet<>();
        for (int shortcutType : ShortcutConstants.USER_SHORTCUT_TYPES) {
            servicesWithShortcuts.addAll(
                    getShortcutTargetsFromSettings(context, shortcutType, userId));
        }
        for (String componentName : componentNames) {
            if (invisibleToggleServices.contains(componentName)) {
                boolean enableA11yService = servicesWithShortcuts.contains(componentName);
                AccessibilityUtils.setAccessibilityServiceState(
                        context,
                        ComponentName.unflattenFromString(componentName),
                        enableA11yService);
            }
        }
    }

    public static Set<String> getShortcutTargetsFromSettings(
            Context context, int shortcutType, int userId) {
        String targetKey = convertToKey(shortcutType);
        if ("accessibility_display_magnification_enabled".equals(targetKey)
                || Settings.Secure.ACCESSIBILITY_MAGNIFICATION_TWO_FINGER_TRIPLE_TAP_ENABLED.equals(
                        targetKey)) {
            boolean magnificationEnabled =
                    Settings.Secure.getIntForUser(
                                    context.getContentResolver(), targetKey, 0, userId)
                            == 1;
            return magnificationEnabled
                    ? Set.of("com.android.server.accessibility.MagnificationController")
                    : Collections.emptySet();
        }
        String targetString =
                Settings.Secure.getStringForUser(context.getContentResolver(), targetKey, userId);
        if (TextUtils.isEmpty(targetString)) {
            return Collections.emptySet();
        }
        Set<String> targets = new LinkedHashSet<>();
        sStringColonSplitter.setString(targetString);
        while (sStringColonSplitter.hasNext()) {
            targets.add(sStringColonSplitter.next());
        }
        return Collections.unmodifiableSet(targets);
    }

    public static int getPrimaryDarkColorId(Context context) {
        return context.getColor(R.color.sem_color_primary_dark);
    }

    public static int getSummaryColor(Context context) {
        return context.getColor(R.color.tw_searchview_hint_text_material);
    }

    public static boolean isSupportDCMotorHapticFeedback(Vibrator vibrator) {
        return VibRune.SUPPORT_HAPTIC_FEEDBACK_ON_DC_MOTOR
                && vibrator.semGetSupportedVibrationType() == 1;
    }

    public static void vibrateDCMotorHapticFeedback(Context context, Vibrator vibrator) {
        boolean hapticEnabled =
                Settings.System.getIntForUser(
                                context.getContentResolver(),
                                Settings.System.HAPTIC_FEEDBACK_ENABLED,
                                1,
                                -2)
                        != 0;
        if (hapticEnabled) {
            VibrationEffect effect =
                    VibrationEffect.semCreateWaveform(
                            HapticFeedbackConstants.semGetVibrationIndex(100),
                            -1,
                            VibrationEffect.SemMagnitudeType.TYPE_TOUCH);
            vibrator.vibrate(effect);
        }
    }
}
