package com.android.input.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_A11Y_CRASH_ON_INCONSISTENT_EVENT_STREAM, Flags.FLAG_ALLOW_TRANSFER_OF_ENTIRE_GESTURE, Flags.FLAG_COLLECT_PALM_REJECTION_QUALITY_METRICS, Flags.FLAG_CONNECTED_DISPLAYS_ASSOCIATED_DISPLAY_CURSOR_BUGFIX, Flags.FLAG_CONNECTED_DISPLAYS_CURSOR, Flags.FLAG_DEPRECATE_SPLIT_TOUCH_APIS, Flags.FLAG_DEVICE_ASSOCIATIONS, Flags.FLAG_DISABLE_REJECT_TOUCH_ON_STYLUS_HOVER, Flags.FLAG_DISABLE_TOUCH_INPUT_MAPPER_POINTER_USAGE, Flags.FLAG_ENABLE_ALPHABETIC_KEYBOARD_WAKE, Flags.FLAG_ENABLE_BUTTON_STATE_VERIFICATION, Flags.FLAG_ENABLE_DISPLAY_TOPOLOGY_VALIDATION, Flags.FLAG_ENABLE_INBOUND_EVENT_VERIFICATION, Flags.FLAG_ENABLE_INPUT_EVENT_TRACING, Flags.FLAG_ENABLE_INPUT_POLICY_PROFILE, Flags.FLAG_ENABLE_KEYBOARD_CLASSIFIER, Flags.FLAG_ENABLE_MULTI_DEVICE_INPUT, Flags.FLAG_ENABLE_MULTI_DEVICE_SAME_WINDOW_STREAM, Flags.FLAG_ENABLE_OUTBOUND_EVENT_VERIFICATION, Flags.FLAG_ENABLE_PER_DEVICE_INPUT_LATENCY_METRICS, Flags.FLAG_ENABLE_PREDICTION_PRUNING_VIA_JERK_THRESHOLDING, Flags.FLAG_ENABLE_TOUCHPAD_NO_FOCUS_CHANGE, Flags.FLAG_ENABLE_V2_TOUCHPAD_TYPING_PALM_REJECTION, Flags.FLAG_INPUT_DEVICE_VIEW_BEHAVIOR_API, Flags.FLAG_KEYBOARD_REPEAT_KEYS, Flags.FLAG_OVERRIDE_KEY_BEHAVIOR_PERMISSION_APIS, Flags.FLAG_PREVENT_MERGING_INPUT_POINTER_DEVICES, Flags.FLAG_RATE_LIMIT_USER_ACTIVITY_POKE_IN_DISPATCHER, Flags.FLAG_ROTARY_INPUT_TELEMETRY, Flags.FLAG_SET_INPUT_DEVICE_KERNEL_WAKE, Flags.FLAG_SHOW_POINTERS_FOR_PARTIAL_SCREENSHARE, Flags.FLAG_USE_CLONED_SCREEN_COORDINATES_AS_RAW, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean a11yCrashOnInconsistentEventStream() {
        return getValue(Flags.FLAG_A11Y_CRASH_ON_INCONSISTENT_EVENT_STREAM, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).a11yCrashOnInconsistentEventStream();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean allowTransferOfEntireGesture() {
        return getValue(Flags.FLAG_ALLOW_TRANSFER_OF_ENTIRE_GESTURE, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).allowTransferOfEntireGesture();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean collectPalmRejectionQualityMetrics() {
        return getValue(Flags.FLAG_COLLECT_PALM_REJECTION_QUALITY_METRICS, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).collectPalmRejectionQualityMetrics();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean connectedDisplaysAssociatedDisplayCursorBugfix() {
        return getValue(Flags.FLAG_CONNECTED_DISPLAYS_ASSOCIATED_DISPLAY_CURSOR_BUGFIX, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).connectedDisplaysAssociatedDisplayCursorBugfix();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean connectedDisplaysCursor() {
        return getValue(Flags.FLAG_CONNECTED_DISPLAYS_CURSOR, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).connectedDisplaysCursor();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean deprecateSplitTouchApis() {
        return getValue(Flags.FLAG_DEPRECATE_SPLIT_TOUCH_APIS, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deprecateSplitTouchApis();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean deviceAssociations() {
        return getValue(Flags.FLAG_DEVICE_ASSOCIATIONS, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).deviceAssociations();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean disableRejectTouchOnStylusHover() {
        return getValue(Flags.FLAG_DISABLE_REJECT_TOUCH_ON_STYLUS_HOVER, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableRejectTouchOnStylusHover();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean disableTouchInputMapperPointerUsage() {
        return getValue(Flags.FLAG_DISABLE_TOUCH_INPUT_MAPPER_POINTER_USAGE, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).disableTouchInputMapperPointerUsage();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableAlphabeticKeyboardWake() {
        return getValue(Flags.FLAG_ENABLE_ALPHABETIC_KEYBOARD_WAKE, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableAlphabeticKeyboardWake();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableButtonStateVerification() {
        return getValue(Flags.FLAG_ENABLE_BUTTON_STATE_VERIFICATION, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableButtonStateVerification();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableDisplayTopologyValidation() {
        return getValue(Flags.FLAG_ENABLE_DISPLAY_TOPOLOGY_VALIDATION, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDisplayTopologyValidation();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableInboundEventVerification() {
        return getValue(Flags.FLAG_ENABLE_INBOUND_EVENT_VERIFICATION, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableInboundEventVerification();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableInputEventTracing() {
        return getValue(Flags.FLAG_ENABLE_INPUT_EVENT_TRACING, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableInputEventTracing();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableInputPolicyProfile() {
        return getValue(Flags.FLAG_ENABLE_INPUT_POLICY_PROFILE, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableInputPolicyProfile();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableKeyboardClassifier() {
        return getValue(Flags.FLAG_ENABLE_KEYBOARD_CLASSIFIER, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableKeyboardClassifier();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableMultiDeviceInput() {
        return getValue(Flags.FLAG_ENABLE_MULTI_DEVICE_INPUT, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableMultiDeviceInput();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableMultiDeviceSameWindowStream() {
        return getValue(Flags.FLAG_ENABLE_MULTI_DEVICE_SAME_WINDOW_STREAM, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableMultiDeviceSameWindowStream();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableOutboundEventVerification() {
        return getValue(Flags.FLAG_ENABLE_OUTBOUND_EVENT_VERIFICATION, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableOutboundEventVerification();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enablePerDeviceInputLatencyMetrics() {
        return getValue(Flags.FLAG_ENABLE_PER_DEVICE_INPUT_LATENCY_METRICS, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePerDeviceInputLatencyMetrics();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enablePredictionPruningViaJerkThresholding() {
        return getValue(Flags.FLAG_ENABLE_PREDICTION_PRUNING_VIA_JERK_THRESHOLDING, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enablePredictionPruningViaJerkThresholding();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableTouchpadNoFocusChange() {
        return getValue(Flags.FLAG_ENABLE_TOUCHPAD_NO_FOCUS_CHANGE, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableTouchpadNoFocusChange();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableV2TouchpadTypingPalmRejection() {
        return getValue(Flags.FLAG_ENABLE_V2_TOUCHPAD_TYPING_PALM_REJECTION, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableV2TouchpadTypingPalmRejection();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean inputDeviceViewBehaviorApi() {
        return getValue(Flags.FLAG_INPUT_DEVICE_VIEW_BEHAVIOR_API, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).inputDeviceViewBehaviorApi();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean keyboardRepeatKeys() {
        return getValue(Flags.FLAG_KEYBOARD_REPEAT_KEYS, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).keyboardRepeatKeys();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean overrideKeyBehaviorPermissionApis() {
        return getValue(Flags.FLAG_OVERRIDE_KEY_BEHAVIOR_PERMISSION_APIS, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).overrideKeyBehaviorPermissionApis();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean preventMergingInputPointerDevices() {
        return getValue(Flags.FLAG_PREVENT_MERGING_INPUT_POINTER_DEVICES, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).preventMergingInputPointerDevices();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean rateLimitUserActivityPokeInDispatcher() {
        return getValue(Flags.FLAG_RATE_LIMIT_USER_ACTIVITY_POKE_IN_DISPATCHER, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda31
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).rateLimitUserActivityPokeInDispatcher();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean rotaryInputTelemetry() {
        return getValue(Flags.FLAG_ROTARY_INPUT_TELEMETRY, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).rotaryInputTelemetry();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean setInputDeviceKernelWake() {
        return getValue(Flags.FLAG_SET_INPUT_DEVICE_KERNEL_WAKE, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setInputDeviceKernelWake();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean showPointersForPartialScreenshare() {
        return getValue(Flags.FLAG_SHOW_POINTERS_FOR_PARTIAL_SCREENSHARE, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).showPointersForPartialScreenshare();
            }
        });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean useClonedScreenCoordinatesAsRaw() {
        return getValue(Flags.FLAG_USE_CLONED_SCREEN_COORDINATES_AS_RAW, new Predicate() { // from class: com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useClonedScreenCoordinatesAsRaw();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_A11Y_CRASH_ON_INCONSISTENT_EVENT_STREAM, Flags.FLAG_ALLOW_TRANSFER_OF_ENTIRE_GESTURE, Flags.FLAG_COLLECT_PALM_REJECTION_QUALITY_METRICS, Flags.FLAG_CONNECTED_DISPLAYS_ASSOCIATED_DISPLAY_CURSOR_BUGFIX, Flags.FLAG_CONNECTED_DISPLAYS_CURSOR, Flags.FLAG_DEPRECATE_SPLIT_TOUCH_APIS, Flags.FLAG_DEVICE_ASSOCIATIONS, Flags.FLAG_DISABLE_REJECT_TOUCH_ON_STYLUS_HOVER, Flags.FLAG_DISABLE_TOUCH_INPUT_MAPPER_POINTER_USAGE, Flags.FLAG_ENABLE_ALPHABETIC_KEYBOARD_WAKE, Flags.FLAG_ENABLE_BUTTON_STATE_VERIFICATION, Flags.FLAG_ENABLE_DISPLAY_TOPOLOGY_VALIDATION, Flags.FLAG_ENABLE_INBOUND_EVENT_VERIFICATION, Flags.FLAG_ENABLE_INPUT_EVENT_TRACING, Flags.FLAG_ENABLE_INPUT_POLICY_PROFILE, Flags.FLAG_ENABLE_KEYBOARD_CLASSIFIER, Flags.FLAG_ENABLE_MULTI_DEVICE_INPUT, Flags.FLAG_ENABLE_MULTI_DEVICE_SAME_WINDOW_STREAM, Flags.FLAG_ENABLE_OUTBOUND_EVENT_VERIFICATION, Flags.FLAG_ENABLE_PER_DEVICE_INPUT_LATENCY_METRICS, Flags.FLAG_ENABLE_PREDICTION_PRUNING_VIA_JERK_THRESHOLDING, Flags.FLAG_ENABLE_TOUCHPAD_NO_FOCUS_CHANGE, Flags.FLAG_ENABLE_V2_TOUCHPAD_TYPING_PALM_REJECTION, Flags.FLAG_INPUT_DEVICE_VIEW_BEHAVIOR_API, Flags.FLAG_KEYBOARD_REPEAT_KEYS, Flags.FLAG_OVERRIDE_KEY_BEHAVIOR_PERMISSION_APIS, Flags.FLAG_PREVENT_MERGING_INPUT_POINTER_DEVICES, Flags.FLAG_RATE_LIMIT_USER_ACTIVITY_POKE_IN_DISPATCHER, Flags.FLAG_ROTARY_INPUT_TELEMETRY, Flags.FLAG_SET_INPUT_DEVICE_KERNEL_WAKE, Flags.FLAG_SHOW_POINTERS_FOR_PARTIAL_SCREENSHARE, Flags.FLAG_USE_CLONED_SCREEN_COORDINATES_AS_RAW);
    }
}
