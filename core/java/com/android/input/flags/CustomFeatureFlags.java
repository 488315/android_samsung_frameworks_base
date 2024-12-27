package com.android.input.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_A11Y_CRASH_ON_INCONSISTENT_EVENT_STREAM,
                            Flags.FLAG_DEVICE_ASSOCIATIONS,
                            Flags.FLAG_DISABLE_REJECT_TOUCH_ON_STYLUS_HOVER,
                            Flags.FLAG_ENABLE_GESTURES_LIBRARY_TIMER_PROVIDER,
                            Flags.FLAG_ENABLE_INBOUND_EVENT_VERIFICATION,
                            Flags.FLAG_ENABLE_INPUT_EVENT_TRACING,
                            Flags.FLAG_ENABLE_INPUT_FILTER_RUST_IMPL,
                            Flags.FLAG_ENABLE_KEYBOARD_CLASSIFIER,
                            Flags.FLAG_ENABLE_MULTI_DEVICE_INPUT,
                            Flags.FLAG_ENABLE_MULTI_DEVICE_SAME_WINDOW_STREAM,
                            Flags.FLAG_ENABLE_NEW_MOUSE_POINTER_BALLISTICS,
                            Flags.FLAG_ENABLE_OUTBOUND_EVENT_VERIFICATION,
                            Flags.FLAG_ENABLE_PREDICTION_PRUNING_VIA_JERK_THRESHOLDING,
                            Flags.FLAG_ENABLE_TOUCHPAD_FLING_STOP,
                            Flags.FLAG_ENABLE_TOUCHPAD_TYPING_PALM_REJECTION,
                            Flags.FLAG_ENABLE_V2_TOUCHPAD_TYPING_PALM_REJECTION,
                            Flags.FLAG_HIDE_POINTER_INDICATORS_FOR_SECURE_WINDOWS,
                            Flags.FLAG_INPUT_DEVICE_VIEW_BEHAVIOR_API,
                            Flags.FLAG_OVERRIDE_KEY_BEHAVIOR_PERMISSION_APIS,
                            Flags.FLAG_RATE_LIMIT_USER_ACTIVITY_POKE_IN_DISPATCHER,
                            Flags.FLAG_REMOVE_POINTER_EVENT_TRACKING_IN_WM,
                            Flags.FLAG_REPORT_PALMS_TO_GESTURES_LIBRARY,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean a11yCrashOnInconsistentEventStream() {
        return getValue(
                Flags.FLAG_A11Y_CRASH_ON_INCONSISTENT_EVENT_STREAM,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).a11yCrashOnInconsistentEventStream();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean deviceAssociations() {
        return getValue(
                Flags.FLAG_DEVICE_ASSOCIATIONS,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).deviceAssociations();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean disableRejectTouchOnStylusHover() {
        return getValue(
                Flags.FLAG_DISABLE_REJECT_TOUCH_ON_STYLUS_HOVER,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).disableRejectTouchOnStylusHover();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableGesturesLibraryTimerProvider() {
        return getValue(
                Flags.FLAG_ENABLE_GESTURES_LIBRARY_TIMER_PROVIDER,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableGesturesLibraryTimerProvider();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableInboundEventVerification() {
        return getValue(
                Flags.FLAG_ENABLE_INBOUND_EVENT_VERIFICATION,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableInboundEventVerification();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableInputEventTracing() {
        return getValue(
                Flags.FLAG_ENABLE_INPUT_EVENT_TRACING,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableInputEventTracing();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableInputFilterRustImpl() {
        return getValue(
                Flags.FLAG_ENABLE_INPUT_FILTER_RUST_IMPL,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableInputFilterRustImpl();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableKeyboardClassifier() {
        return getValue(
                Flags.FLAG_ENABLE_KEYBOARD_CLASSIFIER,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableKeyboardClassifier();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableMultiDeviceInput() {
        return getValue(
                Flags.FLAG_ENABLE_MULTI_DEVICE_INPUT,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableMultiDeviceInput();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableMultiDeviceSameWindowStream() {
        return getValue(
                Flags.FLAG_ENABLE_MULTI_DEVICE_SAME_WINDOW_STREAM,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableMultiDeviceSameWindowStream();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableNewMousePointerBallistics() {
        return getValue(
                Flags.FLAG_ENABLE_NEW_MOUSE_POINTER_BALLISTICS,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableNewMousePointerBallistics();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableOutboundEventVerification() {
        return getValue(
                Flags.FLAG_ENABLE_OUTBOUND_EVENT_VERIFICATION,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableOutboundEventVerification();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enablePredictionPruningViaJerkThresholding() {
        return getValue(
                Flags.FLAG_ENABLE_PREDICTION_PRUNING_VIA_JERK_THRESHOLDING,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enablePredictionPruningViaJerkThresholding();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableTouchpadFlingStop() {
        return getValue(
                Flags.FLAG_ENABLE_TOUCHPAD_FLING_STOP,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableTouchpadFlingStop();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableTouchpadTypingPalmRejection() {
        return getValue(
                Flags.FLAG_ENABLE_TOUCHPAD_TYPING_PALM_REJECTION,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableTouchpadTypingPalmRejection();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableV2TouchpadTypingPalmRejection() {
        return getValue(
                Flags.FLAG_ENABLE_V2_TOUCHPAD_TYPING_PALM_REJECTION,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableV2TouchpadTypingPalmRejection();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean hidePointerIndicatorsForSecureWindows() {
        return getValue(
                Flags.FLAG_HIDE_POINTER_INDICATORS_FOR_SECURE_WINDOWS,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).hidePointerIndicatorsForSecureWindows();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean inputDeviceViewBehaviorApi() {
        return getValue(
                Flags.FLAG_INPUT_DEVICE_VIEW_BEHAVIOR_API,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).inputDeviceViewBehaviorApi();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean overrideKeyBehaviorPermissionApis() {
        return getValue(
                Flags.FLAG_OVERRIDE_KEY_BEHAVIOR_PERMISSION_APIS,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).overrideKeyBehaviorPermissionApis();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean rateLimitUserActivityPokeInDispatcher() {
        return getValue(
                Flags.FLAG_RATE_LIMIT_USER_ACTIVITY_POKE_IN_DISPATCHER,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).rateLimitUserActivityPokeInDispatcher();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean removePointerEventTrackingInWm() {
        return getValue(
                Flags.FLAG_REMOVE_POINTER_EVENT_TRACKING_IN_WM,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).removePointerEventTrackingInWm();
                    }
                });
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean reportPalmsToGesturesLibrary() {
        return getValue(
                Flags.FLAG_REPORT_PALMS_TO_GESTURES_LIBRARY,
                new Predicate() { // from class:
                                  // com.android.input.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).reportPalmsToGesturesLibrary();
                    }
                });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(
                Flags.FLAG_A11Y_CRASH_ON_INCONSISTENT_EVENT_STREAM,
                Flags.FLAG_DEVICE_ASSOCIATIONS,
                Flags.FLAG_DISABLE_REJECT_TOUCH_ON_STYLUS_HOVER,
                Flags.FLAG_ENABLE_GESTURES_LIBRARY_TIMER_PROVIDER,
                Flags.FLAG_ENABLE_INBOUND_EVENT_VERIFICATION,
                Flags.FLAG_ENABLE_INPUT_EVENT_TRACING,
                Flags.FLAG_ENABLE_INPUT_FILTER_RUST_IMPL,
                Flags.FLAG_ENABLE_KEYBOARD_CLASSIFIER,
                Flags.FLAG_ENABLE_MULTI_DEVICE_INPUT,
                Flags.FLAG_ENABLE_MULTI_DEVICE_SAME_WINDOW_STREAM,
                Flags.FLAG_ENABLE_NEW_MOUSE_POINTER_BALLISTICS,
                Flags.FLAG_ENABLE_OUTBOUND_EVENT_VERIFICATION,
                Flags.FLAG_ENABLE_PREDICTION_PRUNING_VIA_JERK_THRESHOLDING,
                Flags.FLAG_ENABLE_TOUCHPAD_FLING_STOP,
                Flags.FLAG_ENABLE_TOUCHPAD_TYPING_PALM_REJECTION,
                Flags.FLAG_ENABLE_V2_TOUCHPAD_TYPING_PALM_REJECTION,
                Flags.FLAG_HIDE_POINTER_INDICATORS_FOR_SECURE_WINDOWS,
                Flags.FLAG_INPUT_DEVICE_VIEW_BEHAVIOR_API,
                Flags.FLAG_OVERRIDE_KEY_BEHAVIOR_PERMISSION_APIS,
                Flags.FLAG_RATE_LIMIT_USER_ACTIVITY_POKE_IN_DISPATCHER,
                Flags.FLAG_REMOVE_POINTER_EVENT_TRACKING_IN_WM,
                Flags.FLAG_REPORT_PALMS_TO_GESTURES_LIBRARY);
    }
}
