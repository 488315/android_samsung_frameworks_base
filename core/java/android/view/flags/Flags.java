package android.view.flags;

/* loaded from: classes4.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADD_SCHANDLE_TO_VRI_SURFACE =
            "android.view.flags.add_schandle_to_vri_surface";
    public static final String FLAG_CUSTOMIZABLE_WINDOW_HEADERS =
            "android.view.flags.customizable_window_headers";
    public static final String FLAG_ENABLE_ARROW_ICON_ON_HOVER_WHEN_CLICKABLE =
            "android.view.flags.enable_arrow_icon_on_hover_when_clickable";
    public static final String FLAG_ENABLE_INVALIDATE_CHECK_THREAD =
            "android.view.flags.enable_invalidate_check_thread";
    public static final String FLAG_ENABLE_SURFACE_NATIVE_ALLOC_REGISTRATION_RO =
            "android.view.flags.enable_surface_native_alloc_registration_ro";
    public static final String FLAG_ENABLE_USE_MEASURE_CACHE_DURING_FORCE_LAYOUT =
            "android.view.flags.enable_use_measure_cache_during_force_layout";
    public static final String FLAG_ENABLE_VECTOR_CURSORS =
            "android.view.flags.enable_vector_cursors";
    public static final String FLAG_ENABLE_VECTOR_CURSOR_A11Y_SETTINGS =
            "android.view.flags.enable_vector_cursor_a11y_settings";
    public static final String FLAG_EXPECTED_PRESENTATION_TIME_API =
            "android.view.flags.expected_presentation_time_api";
    public static final String FLAG_EXPECTED_PRESENTATION_TIME_READ_ONLY =
            "android.view.flags.expected_presentation_time_read_only";
    public static final String FLAG_SCROLL_FEEDBACK_API = "android.view.flags.scroll_feedback_api";
    public static final String FLAG_SENSITIVE_CONTENT_APP_PROTECTION =
            "android.view.flags.sensitive_content_app_protection";
    public static final String FLAG_SENSITIVE_CONTENT_APP_PROTECTION_API =
            "android.view.flags.sensitive_content_app_protection_api";
    public static final String FLAG_SENSITIVE_CONTENT_PREMATURE_PROTECTION_REMOVED_FIX =
            "android.view.flags.sensitive_content_premature_protection_removed_fix";
    public static final String FLAG_SET_FRAME_RATE_CALLBACK =
            "android.view.flags.set_frame_rate_callback";
    public static final String FLAG_TOOLKIT_FRAME_RATE_BY_SIZE_READ_ONLY =
            "android.view.flags.toolkit_frame_rate_by_size_read_only";
    public static final String FLAG_TOOLKIT_FRAME_RATE_DEFAULT_NORMAL_READ_ONLY =
            "android.view.flags.toolkit_frame_rate_default_normal_read_only";
    public static final String FLAG_TOOLKIT_FRAME_RATE_FUNCTION_ENABLING_READ_ONLY =
            "android.view.flags.toolkit_frame_rate_function_enabling_read_only";
    public static final String FLAG_TOOLKIT_FRAME_RATE_SMALL_USES_PERCENT_READ_ONLY =
            "android.view.flags.toolkit_frame_rate_small_uses_percent_read_only";
    public static final String FLAG_TOOLKIT_FRAME_RATE_TYPING_READ_ONLY =
            "android.view.flags.toolkit_frame_rate_typing_read_only";
    public static final String FLAG_TOOLKIT_FRAME_RATE_VELOCITY_MAPPING_READ_ONLY =
            "android.view.flags.toolkit_frame_rate_velocity_mapping_read_only";
    public static final String FLAG_TOOLKIT_FRAME_RATE_VIEW_ENABLING_READ_ONLY =
            "android.view.flags.toolkit_frame_rate_view_enabling_read_only";
    public static final String FLAG_TOOLKIT_METRICS_FOR_FRAME_RATE_DECISION =
            "android.view.flags.toolkit_metrics_for_frame_rate_decision";
    public static final String FLAG_TOOLKIT_SET_FRAME_RATE =
            "android.view.flags.toolkit_set_frame_rate";
    public static final String FLAG_TOOLKIT_SET_FRAME_RATE_READ_ONLY =
            "android.view.flags.toolkit_set_frame_rate_read_only";
    public static final String FLAG_USE_VIEW_BASED_ROTARY_ENCODER_SCROLL_HAPTICS =
            "android.view.flags.use_view_based_rotary_encoder_scroll_haptics";
    public static final String FLAG_VIEW_VELOCITY_API = "android.view.flags.view_velocity_api";

    public static boolean addSchandleToVriSurface() {
        return FEATURE_FLAGS.addSchandleToVriSurface();
    }

    public static boolean customizableWindowHeaders() {
        return FEATURE_FLAGS.customizableWindowHeaders();
    }

    public static boolean enableArrowIconOnHoverWhenClickable() {
        return FEATURE_FLAGS.enableArrowIconOnHoverWhenClickable();
    }

    public static boolean enableInvalidateCheckThread() {
        return FEATURE_FLAGS.enableInvalidateCheckThread();
    }

    public static boolean enableSurfaceNativeAllocRegistrationRo() {
        return FEATURE_FLAGS.enableSurfaceNativeAllocRegistrationRo();
    }

    public static boolean enableUseMeasureCacheDuringForceLayout() {
        return FEATURE_FLAGS.enableUseMeasureCacheDuringForceLayout();
    }

    public static boolean enableVectorCursorA11ySettings() {
        return FEATURE_FLAGS.enableVectorCursorA11ySettings();
    }

    public static boolean enableVectorCursors() {
        return FEATURE_FLAGS.enableVectorCursors();
    }

    public static boolean expectedPresentationTimeApi() {
        return FEATURE_FLAGS.expectedPresentationTimeApi();
    }

    public static boolean expectedPresentationTimeReadOnly() {
        return FEATURE_FLAGS.expectedPresentationTimeReadOnly();
    }

    public static boolean scrollFeedbackApi() {
        return FEATURE_FLAGS.scrollFeedbackApi();
    }

    public static boolean sensitiveContentAppProtection() {
        return FEATURE_FLAGS.sensitiveContentAppProtection();
    }

    public static boolean sensitiveContentAppProtectionApi() {
        return FEATURE_FLAGS.sensitiveContentAppProtectionApi();
    }

    public static boolean sensitiveContentPrematureProtectionRemovedFix() {
        return FEATURE_FLAGS.sensitiveContentPrematureProtectionRemovedFix();
    }

    public static boolean setFrameRateCallback() {
        return FEATURE_FLAGS.setFrameRateCallback();
    }

    public static boolean toolkitFrameRateBySizeReadOnly() {
        return FEATURE_FLAGS.toolkitFrameRateBySizeReadOnly();
    }

    public static boolean toolkitFrameRateDefaultNormalReadOnly() {
        return FEATURE_FLAGS.toolkitFrameRateDefaultNormalReadOnly();
    }

    public static boolean toolkitFrameRateFunctionEnablingReadOnly() {
        return FEATURE_FLAGS.toolkitFrameRateFunctionEnablingReadOnly();
    }

    public static boolean toolkitFrameRateSmallUsesPercentReadOnly() {
        return FEATURE_FLAGS.toolkitFrameRateSmallUsesPercentReadOnly();
    }

    public static boolean toolkitFrameRateTypingReadOnly() {
        return FEATURE_FLAGS.toolkitFrameRateTypingReadOnly();
    }

    public static boolean toolkitFrameRateVelocityMappingReadOnly() {
        return FEATURE_FLAGS.toolkitFrameRateVelocityMappingReadOnly();
    }

    public static boolean toolkitFrameRateViewEnablingReadOnly() {
        return FEATURE_FLAGS.toolkitFrameRateViewEnablingReadOnly();
    }

    public static boolean toolkitMetricsForFrameRateDecision() {
        return FEATURE_FLAGS.toolkitMetricsForFrameRateDecision();
    }

    public static boolean toolkitSetFrameRate() {
        return FEATURE_FLAGS.toolkitSetFrameRate();
    }

    public static boolean toolkitSetFrameRateReadOnly() {
        return FEATURE_FLAGS.toolkitSetFrameRateReadOnly();
    }

    public static boolean useViewBasedRotaryEncoderScrollHaptics() {
        return FEATURE_FLAGS.useViewBasedRotaryEncoderScrollHaptics();
    }

    public static boolean viewVelocityApi() {
        return FEATURE_FLAGS.viewVelocityApi();
    }
}
