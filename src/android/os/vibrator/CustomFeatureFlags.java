package android.os.vibrator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ADAPTIVE_HAPTICS_ENABLED, Flags.FLAG_CANCEL_BY_APPOPS, Flags.FLAG_FIX_AUDIO_COUPLED_HAPTICS_SCALING, Flags.FLAG_FIX_EXTERNAL_VIBRATION_SYSTEM_UPDATE_AWARE, Flags.FLAG_FIX_VIBRATION_THREAD_CALLBACK_HANDLING, Flags.FLAG_HAPTIC_FEEDBACK_INPUT_SOURCE_CUSTOMIZATION_ENABLED, Flags.FLAG_HAPTIC_FEEDBACK_VIBRATION_OEM_CUSTOMIZATION_ENABLED, Flags.FLAG_HAPTICS_SCALE_V2_ENABLED, Flags.FLAG_LOAD_HAPTIC_FEEDBACK_VIBRATION_CUSTOMIZATION_FROM_RESOURCES, Flags.FLAG_NORMALIZED_PWLE_EFFECTS, Flags.FLAG_PRIMITIVE_COMPOSITION_ABSOLUTE_DELAY, Flags.FLAG_REMOVE_HIDL_SUPPORT, Flags.FLAG_THROTTLE_VIBRATION_PARAMS_REQUESTS, Flags.FLAG_VENDOR_VIBRATION_EFFECTS, Flags.FLAG_VIBRATION_ATTRIBUTE_IME_USAGE_API, Flags.FLAG_VIBRATION_PIPELINE_ENABLED, Flags.FLAG_VIBRATION_XML_APIS, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean adaptiveHapticsEnabled() {
        return getValue(Flags.FLAG_ADAPTIVE_HAPTICS_ENABLED, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).adaptiveHapticsEnabled();
            }
        });
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean cancelByAppops() {
        return getValue(Flags.FLAG_CANCEL_BY_APPOPS, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).cancelByAppops();
            }
        });
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean fixAudioCoupledHapticsScaling() {
        return getValue(Flags.FLAG_FIX_AUDIO_COUPLED_HAPTICS_SCALING, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixAudioCoupledHapticsScaling();
            }
        });
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean fixExternalVibrationSystemUpdateAware() {
        return getValue(Flags.FLAG_FIX_EXTERNAL_VIBRATION_SYSTEM_UPDATE_AWARE, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixExternalVibrationSystemUpdateAware();
            }
        });
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean fixVibrationThreadCallbackHandling() {
        return getValue(Flags.FLAG_FIX_VIBRATION_THREAD_CALLBACK_HANDLING, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixVibrationThreadCallbackHandling();
            }
        });
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean hapticFeedbackInputSourceCustomizationEnabled() {
        return getValue(Flags.FLAG_HAPTIC_FEEDBACK_INPUT_SOURCE_CUSTOMIZATION_ENABLED, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hapticFeedbackInputSourceCustomizationEnabled();
            }
        });
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean hapticFeedbackVibrationOemCustomizationEnabled() {
        return getValue(Flags.FLAG_HAPTIC_FEEDBACK_VIBRATION_OEM_CUSTOMIZATION_ENABLED, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hapticFeedbackVibrationOemCustomizationEnabled();
            }
        });
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean hapticsScaleV2Enabled() {
        return getValue(Flags.FLAG_HAPTICS_SCALE_V2_ENABLED, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).hapticsScaleV2Enabled();
            }
        });
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean loadHapticFeedbackVibrationCustomizationFromResources() {
        return getValue(Flags.FLAG_LOAD_HAPTIC_FEEDBACK_VIBRATION_CUSTOMIZATION_FROM_RESOURCES, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).loadHapticFeedbackVibrationCustomizationFromResources();
            }
        });
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean normalizedPwleEffects() {
        return getValue(Flags.FLAG_NORMALIZED_PWLE_EFFECTS, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).normalizedPwleEffects();
            }
        });
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean primitiveCompositionAbsoluteDelay() {
        return getValue(Flags.FLAG_PRIMITIVE_COMPOSITION_ABSOLUTE_DELAY, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).primitiveCompositionAbsoluteDelay();
            }
        });
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean removeHidlSupport() {
        return getValue(Flags.FLAG_REMOVE_HIDL_SUPPORT, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).removeHidlSupport();
            }
        });
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean throttleVibrationParamsRequests() {
        return getValue(Flags.FLAG_THROTTLE_VIBRATION_PARAMS_REQUESTS, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).throttleVibrationParamsRequests();
            }
        });
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean vendorVibrationEffects() {
        return getValue(Flags.FLAG_VENDOR_VIBRATION_EFFECTS, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).vendorVibrationEffects();
            }
        });
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean vibrationAttributeImeUsageApi() {
        return getValue(Flags.FLAG_VIBRATION_ATTRIBUTE_IME_USAGE_API, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).vibrationAttributeImeUsageApi();
            }
        });
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean vibrationPipelineEnabled() {
        return getValue(Flags.FLAG_VIBRATION_PIPELINE_ENABLED, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).vibrationPipelineEnabled();
            }
        });
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean vibrationXmlApis() {
        return getValue(Flags.FLAG_VIBRATION_XML_APIS, new Predicate() { // from class: android.os.vibrator.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).vibrationXmlApis();
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
        return Arrays.asList(Flags.FLAG_ADAPTIVE_HAPTICS_ENABLED, Flags.FLAG_CANCEL_BY_APPOPS, Flags.FLAG_FIX_AUDIO_COUPLED_HAPTICS_SCALING, Flags.FLAG_FIX_EXTERNAL_VIBRATION_SYSTEM_UPDATE_AWARE, Flags.FLAG_FIX_VIBRATION_THREAD_CALLBACK_HANDLING, Flags.FLAG_HAPTIC_FEEDBACK_INPUT_SOURCE_CUSTOMIZATION_ENABLED, Flags.FLAG_HAPTIC_FEEDBACK_VIBRATION_OEM_CUSTOMIZATION_ENABLED, Flags.FLAG_HAPTICS_SCALE_V2_ENABLED, Flags.FLAG_LOAD_HAPTIC_FEEDBACK_VIBRATION_CUSTOMIZATION_FROM_RESOURCES, Flags.FLAG_NORMALIZED_PWLE_EFFECTS, Flags.FLAG_PRIMITIVE_COMPOSITION_ABSOLUTE_DELAY, Flags.FLAG_REMOVE_HIDL_SUPPORT, Flags.FLAG_THROTTLE_VIBRATION_PARAMS_REQUESTS, Flags.FLAG_VENDOR_VIBRATION_EFFECTS, Flags.FLAG_VIBRATION_ATTRIBUTE_IME_USAGE_API, Flags.FLAG_VIBRATION_PIPELINE_ENABLED, Flags.FLAG_VIBRATION_XML_APIS);
    }
}
