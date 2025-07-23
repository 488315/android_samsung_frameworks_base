package android.os.vibrator;

/* loaded from: classes3.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.os.vibrator.FeatureFlags
    public boolean adaptiveHapticsEnabled() {
        return true;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean cancelByAppops() {
        return true;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean fixAudioCoupledHapticsScaling() {
        return true;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean fixExternalVibrationSystemUpdateAware() {
        return false;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean fixVibrationThreadCallbackHandling() {
        return false;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean hapticFeedbackInputSourceCustomizationEnabled() {
        return true;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean hapticFeedbackVibrationOemCustomizationEnabled() {
        return true;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean hapticsScaleV2Enabled() {
        return false;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean loadHapticFeedbackVibrationCustomizationFromResources() {
        return true;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean normalizedPwleEffects() {
        return true;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean primitiveCompositionAbsoluteDelay() {
        return true;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean removeHidlSupport() {
        return false;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean throttleVibrationParamsRequests() {
        return true;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean vendorVibrationEffects() {
        return true;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean vibrationAttributeImeUsageApi() {
        return false;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean vibrationPipelineEnabled() {
        return false;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean vibrationXmlApis() {
        return true;
    }
}
