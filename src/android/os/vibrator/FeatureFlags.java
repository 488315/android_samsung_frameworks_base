package android.os.vibrator;

/* loaded from: classes3.dex */
public interface FeatureFlags {
    boolean adaptiveHapticsEnabled();

    boolean cancelByAppops();

    boolean fixAudioCoupledHapticsScaling();

    boolean fixExternalVibrationSystemUpdateAware();

    boolean fixVibrationThreadCallbackHandling();

    boolean hapticFeedbackInputSourceCustomizationEnabled();

    boolean hapticFeedbackVibrationOemCustomizationEnabled();

    boolean hapticsScaleV2Enabled();

    boolean loadHapticFeedbackVibrationCustomizationFromResources();

    boolean normalizedPwleEffects();

    boolean primitiveCompositionAbsoluteDelay();

    boolean removeHidlSupport();

    boolean throttleVibrationParamsRequests();

    boolean vendorVibrationEffects();

    boolean vibrationAttributeImeUsageApi();

    boolean vibrationPipelineEnabled();

    boolean vibrationXmlApis();
}
