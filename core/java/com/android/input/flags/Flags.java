package com.android.input.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_A11Y_CRASH_ON_INCONSISTENT_EVENT_STREAM = "com.android.input.flags.a11y_crash_on_inconsistent_event_stream";
    public static final String FLAG_DEVICE_ASSOCIATIONS = "com.android.input.flags.device_associations";
    public static final String FLAG_DISABLE_REJECT_TOUCH_ON_STYLUS_HOVER = "com.android.input.flags.disable_reject_touch_on_stylus_hover";
    public static final String FLAG_ENABLE_GESTURES_LIBRARY_TIMER_PROVIDER = "com.android.input.flags.enable_gestures_library_timer_provider";
    public static final String FLAG_ENABLE_INBOUND_EVENT_VERIFICATION = "com.android.input.flags.enable_inbound_event_verification";
    public static final String FLAG_ENABLE_INPUT_EVENT_TRACING = "com.android.input.flags.enable_input_event_tracing";
    public static final String FLAG_ENABLE_INPUT_FILTER_RUST_IMPL = "com.android.input.flags.enable_input_filter_rust_impl";
    public static final String FLAG_ENABLE_KEYBOARD_CLASSIFIER = "com.android.input.flags.enable_keyboard_classifier";
    public static final String FLAG_ENABLE_MULTI_DEVICE_INPUT = "com.android.input.flags.enable_multi_device_input";
    public static final String FLAG_ENABLE_MULTI_DEVICE_SAME_WINDOW_STREAM = "com.android.input.flags.enable_multi_device_same_window_stream";
    public static final String FLAG_ENABLE_NEW_MOUSE_POINTER_BALLISTICS = "com.android.input.flags.enable_new_mouse_pointer_ballistics";
    public static final String FLAG_ENABLE_OUTBOUND_EVENT_VERIFICATION = "com.android.input.flags.enable_outbound_event_verification";
    public static final String FLAG_ENABLE_PREDICTION_PRUNING_VIA_JERK_THRESHOLDING = "com.android.input.flags.enable_prediction_pruning_via_jerk_thresholding";
    public static final String FLAG_ENABLE_TOUCHPAD_FLING_STOP = "com.android.input.flags.enable_touchpad_fling_stop";
    public static final String FLAG_ENABLE_TOUCHPAD_TYPING_PALM_REJECTION = "com.android.input.flags.enable_touchpad_typing_palm_rejection";
    public static final String FLAG_ENABLE_V2_TOUCHPAD_TYPING_PALM_REJECTION = "com.android.input.flags.enable_v2_touchpad_typing_palm_rejection";
    public static final String FLAG_HIDE_POINTER_INDICATORS_FOR_SECURE_WINDOWS = "com.android.input.flags.hide_pointer_indicators_for_secure_windows";
    public static final String FLAG_INPUT_DEVICE_VIEW_BEHAVIOR_API = "com.android.input.flags.input_device_view_behavior_api";
    public static final String FLAG_OVERRIDE_KEY_BEHAVIOR_PERMISSION_APIS = "com.android.input.flags.override_key_behavior_permission_apis";
    public static final String FLAG_RATE_LIMIT_USER_ACTIVITY_POKE_IN_DISPATCHER = "com.android.input.flags.rate_limit_user_activity_poke_in_dispatcher";
    public static final String FLAG_REMOVE_POINTER_EVENT_TRACKING_IN_WM = "com.android.input.flags.remove_pointer_event_tracking_in_wm";
    public static final String FLAG_REPORT_PALMS_TO_GESTURES_LIBRARY = "com.android.input.flags.report_palms_to_gestures_library";

    public static boolean a11yCrashOnInconsistentEventStream() {
        return FEATURE_FLAGS.a11yCrashOnInconsistentEventStream();
    }

    public static boolean deviceAssociations() {
        return FEATURE_FLAGS.deviceAssociations();
    }

    public static boolean disableRejectTouchOnStylusHover() {
        return FEATURE_FLAGS.disableRejectTouchOnStylusHover();
    }

    public static boolean enableGesturesLibraryTimerProvider() {
        return FEATURE_FLAGS.enableGesturesLibraryTimerProvider();
    }

    public static boolean enableInboundEventVerification() {
        return FEATURE_FLAGS.enableInboundEventVerification();
    }

    public static boolean enableInputEventTracing() {
        return FEATURE_FLAGS.enableInputEventTracing();
    }

    public static boolean enableInputFilterRustImpl() {
        return FEATURE_FLAGS.enableInputFilterRustImpl();
    }

    public static boolean enableKeyboardClassifier() {
        return FEATURE_FLAGS.enableKeyboardClassifier();
    }

    public static boolean enableMultiDeviceInput() {
        return FEATURE_FLAGS.enableMultiDeviceInput();
    }

    public static boolean enableMultiDeviceSameWindowStream() {
        return FEATURE_FLAGS.enableMultiDeviceSameWindowStream();
    }

    public static boolean enableNewMousePointerBallistics() {
        return FEATURE_FLAGS.enableNewMousePointerBallistics();
    }

    public static boolean enableOutboundEventVerification() {
        return FEATURE_FLAGS.enableOutboundEventVerification();
    }

    public static boolean enablePredictionPruningViaJerkThresholding() {
        return FEATURE_FLAGS.enablePredictionPruningViaJerkThresholding();
    }

    public static boolean enableTouchpadFlingStop() {
        return FEATURE_FLAGS.enableTouchpadFlingStop();
    }

    public static boolean enableTouchpadTypingPalmRejection() {
        return FEATURE_FLAGS.enableTouchpadTypingPalmRejection();
    }

    public static boolean enableV2TouchpadTypingPalmRejection() {
        return FEATURE_FLAGS.enableV2TouchpadTypingPalmRejection();
    }

    public static boolean hidePointerIndicatorsForSecureWindows() {
        return FEATURE_FLAGS.hidePointerIndicatorsForSecureWindows();
    }

    public static boolean inputDeviceViewBehaviorApi() {
        return FEATURE_FLAGS.inputDeviceViewBehaviorApi();
    }

    public static boolean overrideKeyBehaviorPermissionApis() {
        return FEATURE_FLAGS.overrideKeyBehaviorPermissionApis();
    }

    public static boolean rateLimitUserActivityPokeInDispatcher() {
        return FEATURE_FLAGS.rateLimitUserActivityPokeInDispatcher();
    }

    public static boolean removePointerEventTrackingInWm() {
        return FEATURE_FLAGS.removePointerEventTrackingInWm();
    }

    public static boolean reportPalmsToGesturesLibrary() {
        return FEATURE_FLAGS.reportPalmsToGesturesLibrary();
    }
}
