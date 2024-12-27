package android.view.flags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ADD_SCHANDLE_TO_VRI_SURFACE, Flags.FLAG_CUSTOMIZABLE_WINDOW_HEADERS, Flags.FLAG_ENABLE_ARROW_ICON_ON_HOVER_WHEN_CLICKABLE, Flags.FLAG_ENABLE_INVALIDATE_CHECK_THREAD, Flags.FLAG_ENABLE_SURFACE_NATIVE_ALLOC_REGISTRATION_RO, Flags.FLAG_ENABLE_USE_MEASURE_CACHE_DURING_FORCE_LAYOUT, Flags.FLAG_ENABLE_VECTOR_CURSOR_A11Y_SETTINGS, Flags.FLAG_ENABLE_VECTOR_CURSORS, Flags.FLAG_EXPECTED_PRESENTATION_TIME_API, Flags.FLAG_EXPECTED_PRESENTATION_TIME_READ_ONLY, Flags.FLAG_SCROLL_FEEDBACK_API, Flags.FLAG_SENSITIVE_CONTENT_APP_PROTECTION, Flags.FLAG_SENSITIVE_CONTENT_APP_PROTECTION_API, Flags.FLAG_SENSITIVE_CONTENT_PREMATURE_PROTECTION_REMOVED_FIX, Flags.FLAG_SET_FRAME_RATE_CALLBACK, Flags.FLAG_TOOLKIT_FRAME_RATE_BY_SIZE_READ_ONLY, Flags.FLAG_TOOLKIT_FRAME_RATE_DEFAULT_NORMAL_READ_ONLY, Flags.FLAG_TOOLKIT_FRAME_RATE_FUNCTION_ENABLING_READ_ONLY, Flags.FLAG_TOOLKIT_FRAME_RATE_SMALL_USES_PERCENT_READ_ONLY, Flags.FLAG_TOOLKIT_FRAME_RATE_TYPING_READ_ONLY, Flags.FLAG_TOOLKIT_FRAME_RATE_VELOCITY_MAPPING_READ_ONLY, Flags.FLAG_TOOLKIT_FRAME_RATE_VIEW_ENABLING_READ_ONLY, Flags.FLAG_TOOLKIT_METRICS_FOR_FRAME_RATE_DECISION, Flags.FLAG_TOOLKIT_SET_FRAME_RATE, Flags.FLAG_TOOLKIT_SET_FRAME_RATE_READ_ONLY, Flags.FLAG_USE_VIEW_BASED_ROTARY_ENCODER_SCROLL_HAPTICS, Flags.FLAG_VIEW_VELOCITY_API, ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // android.view.flags.FeatureFlags
    public boolean addSchandleToVriSurface() {
        return getValue(Flags.FLAG_ADD_SCHANDLE_TO_VRI_SURFACE, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).addSchandleToVriSurface();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean customizableWindowHeaders() {
        return getValue(Flags.FLAG_CUSTOMIZABLE_WINDOW_HEADERS, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).customizableWindowHeaders();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean enableArrowIconOnHoverWhenClickable() {
        return getValue(Flags.FLAG_ENABLE_ARROW_ICON_ON_HOVER_WHEN_CLICKABLE, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableArrowIconOnHoverWhenClickable();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean enableInvalidateCheckThread() {
        return getValue(Flags.FLAG_ENABLE_INVALIDATE_CHECK_THREAD, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableInvalidateCheckThread();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean enableSurfaceNativeAllocRegistrationRo() {
        return getValue(Flags.FLAG_ENABLE_SURFACE_NATIVE_ALLOC_REGISTRATION_RO, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableSurfaceNativeAllocRegistrationRo();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean enableUseMeasureCacheDuringForceLayout() {
        return getValue(Flags.FLAG_ENABLE_USE_MEASURE_CACHE_DURING_FORCE_LAYOUT, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableUseMeasureCacheDuringForceLayout();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean enableVectorCursorA11ySettings() {
        return getValue(Flags.FLAG_ENABLE_VECTOR_CURSOR_A11Y_SETTINGS, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableVectorCursorA11ySettings();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean enableVectorCursors() {
        return getValue(Flags.FLAG_ENABLE_VECTOR_CURSORS, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableVectorCursors();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean expectedPresentationTimeApi() {
        return getValue(Flags.FLAG_EXPECTED_PRESENTATION_TIME_API, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).expectedPresentationTimeApi();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean expectedPresentationTimeReadOnly() {
        return getValue(Flags.FLAG_EXPECTED_PRESENTATION_TIME_READ_ONLY, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).expectedPresentationTimeReadOnly();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean scrollFeedbackApi() {
        return getValue(Flags.FLAG_SCROLL_FEEDBACK_API, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).scrollFeedbackApi();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean sensitiveContentAppProtection() {
        return getValue(Flags.FLAG_SENSITIVE_CONTENT_APP_PROTECTION, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sensitiveContentAppProtection();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean sensitiveContentAppProtectionApi() {
        return getValue(Flags.FLAG_SENSITIVE_CONTENT_APP_PROTECTION_API, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sensitiveContentAppProtectionApi();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean sensitiveContentPrematureProtectionRemovedFix() {
        return getValue(Flags.FLAG_SENSITIVE_CONTENT_PREMATURE_PROTECTION_REMOVED_FIX, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).sensitiveContentPrematureProtectionRemovedFix();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean setFrameRateCallback() {
        return getValue(Flags.FLAG_SET_FRAME_RATE_CALLBACK, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).setFrameRateCallback();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean toolkitFrameRateBySizeReadOnly() {
        return getValue(Flags.FLAG_TOOLKIT_FRAME_RATE_BY_SIZE_READ_ONLY, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).toolkitFrameRateBySizeReadOnly();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean toolkitFrameRateDefaultNormalReadOnly() {
        return getValue(Flags.FLAG_TOOLKIT_FRAME_RATE_DEFAULT_NORMAL_READ_ONLY, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).toolkitFrameRateDefaultNormalReadOnly();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean toolkitFrameRateFunctionEnablingReadOnly() {
        return getValue(Flags.FLAG_TOOLKIT_FRAME_RATE_FUNCTION_ENABLING_READ_ONLY, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).toolkitFrameRateFunctionEnablingReadOnly();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean toolkitFrameRateSmallUsesPercentReadOnly() {
        return getValue(Flags.FLAG_TOOLKIT_FRAME_RATE_SMALL_USES_PERCENT_READ_ONLY, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).toolkitFrameRateSmallUsesPercentReadOnly();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean toolkitFrameRateTypingReadOnly() {
        return getValue(Flags.FLAG_TOOLKIT_FRAME_RATE_TYPING_READ_ONLY, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).toolkitFrameRateTypingReadOnly();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean toolkitFrameRateVelocityMappingReadOnly() {
        return getValue(Flags.FLAG_TOOLKIT_FRAME_RATE_VELOCITY_MAPPING_READ_ONLY, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).toolkitFrameRateVelocityMappingReadOnly();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean toolkitFrameRateViewEnablingReadOnly() {
        return getValue(Flags.FLAG_TOOLKIT_FRAME_RATE_VIEW_ENABLING_READ_ONLY, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).toolkitFrameRateViewEnablingReadOnly();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean toolkitMetricsForFrameRateDecision() {
        return getValue(Flags.FLAG_TOOLKIT_METRICS_FOR_FRAME_RATE_DECISION, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).toolkitMetricsForFrameRateDecision();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean toolkitSetFrameRate() {
        return getValue(Flags.FLAG_TOOLKIT_SET_FRAME_RATE, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).toolkitSetFrameRate();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean toolkitSetFrameRateReadOnly() {
        return getValue(Flags.FLAG_TOOLKIT_SET_FRAME_RATE_READ_ONLY, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).toolkitSetFrameRateReadOnly();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean useViewBasedRotaryEncoderScrollHaptics() {
        return getValue(Flags.FLAG_USE_VIEW_BASED_ROTARY_ENCODER_SCROLL_HAPTICS, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useViewBasedRotaryEncoderScrollHaptics();
            }
        });
    }

    @Override // android.view.flags.FeatureFlags
    public boolean viewVelocityApi() {
        return getValue(Flags.FLAG_VIEW_VELOCITY_API, new Predicate() { // from class: android.view.flags.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).viewVelocityApi();
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
        return Arrays.asList(Flags.FLAG_ADD_SCHANDLE_TO_VRI_SURFACE, Flags.FLAG_CUSTOMIZABLE_WINDOW_HEADERS, Flags.FLAG_ENABLE_ARROW_ICON_ON_HOVER_WHEN_CLICKABLE, Flags.FLAG_ENABLE_INVALIDATE_CHECK_THREAD, Flags.FLAG_ENABLE_SURFACE_NATIVE_ALLOC_REGISTRATION_RO, Flags.FLAG_ENABLE_USE_MEASURE_CACHE_DURING_FORCE_LAYOUT, Flags.FLAG_ENABLE_VECTOR_CURSOR_A11Y_SETTINGS, Flags.FLAG_ENABLE_VECTOR_CURSORS, Flags.FLAG_EXPECTED_PRESENTATION_TIME_API, Flags.FLAG_EXPECTED_PRESENTATION_TIME_READ_ONLY, Flags.FLAG_SCROLL_FEEDBACK_API, Flags.FLAG_SENSITIVE_CONTENT_APP_PROTECTION, Flags.FLAG_SENSITIVE_CONTENT_APP_PROTECTION_API, Flags.FLAG_SENSITIVE_CONTENT_PREMATURE_PROTECTION_REMOVED_FIX, Flags.FLAG_SET_FRAME_RATE_CALLBACK, Flags.FLAG_TOOLKIT_FRAME_RATE_BY_SIZE_READ_ONLY, Flags.FLAG_TOOLKIT_FRAME_RATE_DEFAULT_NORMAL_READ_ONLY, Flags.FLAG_TOOLKIT_FRAME_RATE_FUNCTION_ENABLING_READ_ONLY, Flags.FLAG_TOOLKIT_FRAME_RATE_SMALL_USES_PERCENT_READ_ONLY, Flags.FLAG_TOOLKIT_FRAME_RATE_TYPING_READ_ONLY, Flags.FLAG_TOOLKIT_FRAME_RATE_VELOCITY_MAPPING_READ_ONLY, Flags.FLAG_TOOLKIT_FRAME_RATE_VIEW_ENABLING_READ_ONLY, Flags.FLAG_TOOLKIT_METRICS_FOR_FRAME_RATE_DECISION, Flags.FLAG_TOOLKIT_SET_FRAME_RATE, Flags.FLAG_TOOLKIT_SET_FRAME_RATE_READ_ONLY, Flags.FLAG_USE_VIEW_BASED_ROTARY_ENCODER_SCROLL_HAPTICS, Flags.FLAG_VIEW_VELOCITY_API);
    }
}
