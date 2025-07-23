package com.android.input.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_A11Y_CRASH_ON_INCONSISTENT_EVENT_STREAM = "com.android.input.flags.a11y_crash_on_inconsistent_event_stream";
    public static final String FLAG_ALLOW_TRANSFER_OF_ENTIRE_GESTURE = "com.android.input.flags.allow_transfer_of_entire_gesture";
    public static final String FLAG_COLLECT_PALM_REJECTION_QUALITY_METRICS = "com.android.input.flags.collect_palm_rejection_quality_metrics";
    public static final String FLAG_CONNECTED_DISPLAYS_ASSOCIATED_DISPLAY_CURSOR_BUGFIX = "com.android.input.flags.connected_displays_associated_display_cursor_bugfix";
    public static final String FLAG_CONNECTED_DISPLAYS_CURSOR = "com.android.input.flags.connected_displays_cursor";
    public static final String FLAG_DEPRECATE_SPLIT_TOUCH_APIS = "com.android.input.flags.deprecate_split_touch_apis";
    public static final String FLAG_DEVICE_ASSOCIATIONS = "com.android.input.flags.device_associations";
    public static final String FLAG_DISABLE_REJECT_TOUCH_ON_STYLUS_HOVER = "com.android.input.flags.disable_reject_touch_on_stylus_hover";
    public static final String FLAG_DISABLE_TOUCH_INPUT_MAPPER_POINTER_USAGE = "com.android.input.flags.disable_touch_input_mapper_pointer_usage";
    public static final String FLAG_ENABLE_ALPHABETIC_KEYBOARD_WAKE = "com.android.input.flags.enable_alphabetic_keyboard_wake";
    public static final String FLAG_ENABLE_BUTTON_STATE_VERIFICATION = "com.android.input.flags.enable_button_state_verification";
    public static final String FLAG_ENABLE_DISPLAY_TOPOLOGY_VALIDATION = "com.android.input.flags.enable_display_topology_validation";
    public static final String FLAG_ENABLE_INBOUND_EVENT_VERIFICATION = "com.android.input.flags.enable_inbound_event_verification";
    public static final String FLAG_ENABLE_INPUT_EVENT_TRACING = "com.android.input.flags.enable_input_event_tracing";
    public static final String FLAG_ENABLE_INPUT_POLICY_PROFILE = "com.android.input.flags.enable_input_policy_profile";
    public static final String FLAG_ENABLE_KEYBOARD_CLASSIFIER = "com.android.input.flags.enable_keyboard_classifier";
    public static final String FLAG_ENABLE_MULTI_DEVICE_INPUT = "com.android.input.flags.enable_multi_device_input";
    public static final String FLAG_ENABLE_MULTI_DEVICE_SAME_WINDOW_STREAM = "com.android.input.flags.enable_multi_device_same_window_stream";
    public static final String FLAG_ENABLE_OUTBOUND_EVENT_VERIFICATION = "com.android.input.flags.enable_outbound_event_verification";
    public static final String FLAG_ENABLE_PER_DEVICE_INPUT_LATENCY_METRICS = "com.android.input.flags.enable_per_device_input_latency_metrics";
    public static final String FLAG_ENABLE_PREDICTION_PRUNING_VIA_JERK_THRESHOLDING = "com.android.input.flags.enable_prediction_pruning_via_jerk_thresholding";
    public static final String FLAG_ENABLE_TOUCHPAD_NO_FOCUS_CHANGE = "com.android.input.flags.enable_touchpad_no_focus_change";
    public static final String FLAG_ENABLE_V2_TOUCHPAD_TYPING_PALM_REJECTION = "com.android.input.flags.enable_v2_touchpad_typing_palm_rejection";
    public static final String FLAG_INPUT_DEVICE_VIEW_BEHAVIOR_API = "com.android.input.flags.input_device_view_behavior_api";
    public static final String FLAG_KEYBOARD_REPEAT_KEYS = "com.android.input.flags.keyboard_repeat_keys";
    public static final String FLAG_OVERRIDE_KEY_BEHAVIOR_PERMISSION_APIS = "com.android.input.flags.override_key_behavior_permission_apis";
    public static final String FLAG_PREVENT_MERGING_INPUT_POINTER_DEVICES = "com.android.input.flags.prevent_merging_input_pointer_devices";
    public static final String FLAG_RATE_LIMIT_USER_ACTIVITY_POKE_IN_DISPATCHER = "com.android.input.flags.rate_limit_user_activity_poke_in_dispatcher";
    public static final String FLAG_ROTARY_INPUT_TELEMETRY = "com.android.input.flags.rotary_input_telemetry";
    public static final String FLAG_SET_INPUT_DEVICE_KERNEL_WAKE = "com.android.input.flags.set_input_device_kernel_wake";
    public static final String FLAG_SHOW_POINTERS_FOR_PARTIAL_SCREENSHARE = "com.android.input.flags.show_pointers_for_partial_screenshare";
    public static final String FLAG_USE_CLONED_SCREEN_COORDINATES_AS_RAW = "com.android.input.flags.use_cloned_screen_coordinates_as_raw";

    public static boolean a11yCrashOnInconsistentEventStream() {
        return FEATURE_FLAGS.a11yCrashOnInconsistentEventStream();
    }

    public static boolean allowTransferOfEntireGesture() {
        return FEATURE_FLAGS.allowTransferOfEntireGesture();
    }

    public static boolean collectPalmRejectionQualityMetrics() {
        return FEATURE_FLAGS.collectPalmRejectionQualityMetrics();
    }

    public static boolean connectedDisplaysAssociatedDisplayCursorBugfix() {
        return FEATURE_FLAGS.connectedDisplaysAssociatedDisplayCursorBugfix();
    }

    public static boolean connectedDisplaysCursor() {
        return FEATURE_FLAGS.connectedDisplaysCursor();
    }

    public static boolean deprecateSplitTouchApis() {
        return FEATURE_FLAGS.deprecateSplitTouchApis();
    }

    public static boolean deviceAssociations() {
        return FEATURE_FLAGS.deviceAssociations();
    }

    public static boolean disableRejectTouchOnStylusHover() {
        return FEATURE_FLAGS.disableRejectTouchOnStylusHover();
    }

    public static boolean disableTouchInputMapperPointerUsage() {
        return FEATURE_FLAGS.disableTouchInputMapperPointerUsage();
    }

    public static boolean enableAlphabeticKeyboardWake() {
        return FEATURE_FLAGS.enableAlphabeticKeyboardWake();
    }

    public static boolean enableButtonStateVerification() {
        return FEATURE_FLAGS.enableButtonStateVerification();
    }

    public static boolean enableDisplayTopologyValidation() {
        return FEATURE_FLAGS.enableDisplayTopologyValidation();
    }

    public static boolean enableInboundEventVerification() {
        return FEATURE_FLAGS.enableInboundEventVerification();
    }

    public static boolean enableInputEventTracing() {
        return FEATURE_FLAGS.enableInputEventTracing();
    }

    public static boolean enableInputPolicyProfile() {
        return FEATURE_FLAGS.enableInputPolicyProfile();
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

    public static boolean enableOutboundEventVerification() {
        return FEATURE_FLAGS.enableOutboundEventVerification();
    }

    public static boolean enablePerDeviceInputLatencyMetrics() {
        return FEATURE_FLAGS.enablePerDeviceInputLatencyMetrics();
    }

    public static boolean enablePredictionPruningViaJerkThresholding() {
        return FEATURE_FLAGS.enablePredictionPruningViaJerkThresholding();
    }

    public static boolean enableTouchpadNoFocusChange() {
        return FEATURE_FLAGS.enableTouchpadNoFocusChange();
    }

    public static boolean enableV2TouchpadTypingPalmRejection() {
        return FEATURE_FLAGS.enableV2TouchpadTypingPalmRejection();
    }

    public static boolean inputDeviceViewBehaviorApi() {
        return FEATURE_FLAGS.inputDeviceViewBehaviorApi();
    }

    public static boolean keyboardRepeatKeys() {
        return FEATURE_FLAGS.keyboardRepeatKeys();
    }

    public static boolean overrideKeyBehaviorPermissionApis() {
        return FEATURE_FLAGS.overrideKeyBehaviorPermissionApis();
    }

    public static boolean preventMergingInputPointerDevices() {
        return FEATURE_FLAGS.preventMergingInputPointerDevices();
    }

    public static boolean rateLimitUserActivityPokeInDispatcher() {
        return FEATURE_FLAGS.rateLimitUserActivityPokeInDispatcher();
    }

    public static boolean rotaryInputTelemetry() {
        return FEATURE_FLAGS.rotaryInputTelemetry();
    }

    public static boolean setInputDeviceKernelWake() {
        return FEATURE_FLAGS.setInputDeviceKernelWake();
    }

    public static boolean showPointersForPartialScreenshare() {
        return FEATURE_FLAGS.showPointersForPartialScreenshare();
    }

    public static boolean useClonedScreenCoordinatesAsRaw() {
        return FEATURE_FLAGS.useClonedScreenCoordinatesAsRaw();
    }
}
