package android.os.vibrator;

/* loaded from: classes3.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADAPTIVE_HAPTICS_ENABLED = "android.os.vibrator.adaptive_haptics_enabled";
    public static final String FLAG_CANCEL_BY_APPOPS = "android.os.vibrator.cancel_by_appops";
    public static final String FLAG_FIX_AUDIO_COUPLED_HAPTICS_SCALING = "android.os.vibrator.fix_audio_coupled_haptics_scaling";
    public static final String FLAG_FIX_EXTERNAL_VIBRATION_SYSTEM_UPDATE_AWARE = "android.os.vibrator.fix_external_vibration_system_update_aware";
    public static final String FLAG_FIX_VIBRATION_THREAD_CALLBACK_HANDLING = "android.os.vibrator.fix_vibration_thread_callback_handling";
    public static final String FLAG_HAPTICS_SCALE_V2_ENABLED = "android.os.vibrator.haptics_scale_v2_enabled";
    public static final String FLAG_HAPTIC_FEEDBACK_INPUT_SOURCE_CUSTOMIZATION_ENABLED = "android.os.vibrator.haptic_feedback_input_source_customization_enabled";
    public static final String FLAG_HAPTIC_FEEDBACK_VIBRATION_OEM_CUSTOMIZATION_ENABLED = "android.os.vibrator.haptic_feedback_vibration_oem_customization_enabled";
    public static final String FLAG_LOAD_HAPTIC_FEEDBACK_VIBRATION_CUSTOMIZATION_FROM_RESOURCES = "android.os.vibrator.load_haptic_feedback_vibration_customization_from_resources";
    public static final String FLAG_NORMALIZED_PWLE_EFFECTS = "android.os.vibrator.normalized_pwle_effects";
    public static final String FLAG_PRIMITIVE_COMPOSITION_ABSOLUTE_DELAY = "android.os.vibrator.primitive_composition_absolute_delay";
    public static final String FLAG_REMOVE_HIDL_SUPPORT = "android.os.vibrator.remove_hidl_support";
    public static final String FLAG_THROTTLE_VIBRATION_PARAMS_REQUESTS = "android.os.vibrator.throttle_vibration_params_requests";
    public static final String FLAG_VENDOR_VIBRATION_EFFECTS = "android.os.vibrator.vendor_vibration_effects";
    public static final String FLAG_VIBRATION_ATTRIBUTE_IME_USAGE_API = "android.os.vibrator.vibration_attribute_ime_usage_api";
    public static final String FLAG_VIBRATION_PIPELINE_ENABLED = "android.os.vibrator.vibration_pipeline_enabled";
    public static final String FLAG_VIBRATION_XML_APIS = "android.os.vibrator.vibration_xml_apis";

    public static boolean adaptiveHapticsEnabled() {
        return FEATURE_FLAGS.adaptiveHapticsEnabled();
    }

    public static boolean cancelByAppops() {
        return FEATURE_FLAGS.cancelByAppops();
    }

    public static boolean fixAudioCoupledHapticsScaling() {
        return FEATURE_FLAGS.fixAudioCoupledHapticsScaling();
    }

    public static boolean fixExternalVibrationSystemUpdateAware() {
        return FEATURE_FLAGS.fixExternalVibrationSystemUpdateAware();
    }

    public static boolean fixVibrationThreadCallbackHandling() {
        return FEATURE_FLAGS.fixVibrationThreadCallbackHandling();
    }

    public static boolean hapticFeedbackInputSourceCustomizationEnabled() {
        return FEATURE_FLAGS.hapticFeedbackInputSourceCustomizationEnabled();
    }

    public static boolean hapticFeedbackVibrationOemCustomizationEnabled() {
        return FEATURE_FLAGS.hapticFeedbackVibrationOemCustomizationEnabled();
    }

    public static boolean hapticsScaleV2Enabled() {
        return FEATURE_FLAGS.hapticsScaleV2Enabled();
    }

    public static boolean loadHapticFeedbackVibrationCustomizationFromResources() {
        return FEATURE_FLAGS.loadHapticFeedbackVibrationCustomizationFromResources();
    }

    public static boolean normalizedPwleEffects() {
        return FEATURE_FLAGS.normalizedPwleEffects();
    }

    public static boolean primitiveCompositionAbsoluteDelay() {
        return FEATURE_FLAGS.primitiveCompositionAbsoluteDelay();
    }

    public static boolean removeHidlSupport() {
        return FEATURE_FLAGS.removeHidlSupport();
    }

    public static boolean throttleVibrationParamsRequests() {
        return FEATURE_FLAGS.throttleVibrationParamsRequests();
    }

    public static boolean vendorVibrationEffects() {
        return FEATURE_FLAGS.vendorVibrationEffects();
    }

    public static boolean vibrationAttributeImeUsageApi() {
        return FEATURE_FLAGS.vibrationAttributeImeUsageApi();
    }

    public static boolean vibrationPipelineEnabled() {
        return FEATURE_FLAGS.vibrationPipelineEnabled();
    }

    public static boolean vibrationXmlApis() {
        return FEATURE_FLAGS.vibrationXmlApis();
    }
}
