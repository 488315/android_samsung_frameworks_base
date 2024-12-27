package android.os.vibrator;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADAPTIVE_HAPTICS_ENABLED =
            "android.os.vibrator.adaptive_haptics_enabled";
    public static final String FLAG_HAPTIC_FEEDBACK_VIBRATION_OEM_CUSTOMIZATION_ENABLED =
            "android.os.vibrator.haptic_feedback_vibration_oem_customization_enabled";
    public static final String FLAG_KEYBOARD_CATEGORY_ENABLED =
            "android.os.vibrator.keyboard_category_enabled";
    public static final String FLAG_USE_VIBRATOR_HAPTIC_FEEDBACK =
            "android.os.vibrator.use_vibrator_haptic_feedback";

    public static boolean adaptiveHapticsEnabled() {
        return FEATURE_FLAGS.adaptiveHapticsEnabled();
    }

    public static boolean hapticFeedbackVibrationOemCustomizationEnabled() {
        return FEATURE_FLAGS.hapticFeedbackVibrationOemCustomizationEnabled();
    }

    public static boolean keyboardCategoryEnabled() {
        return FEATURE_FLAGS.keyboardCategoryEnabled();
    }

    public static boolean useVibratorHapticFeedback() {
        return FEATURE_FLAGS.useVibratorHapticFeedback();
    }
}
