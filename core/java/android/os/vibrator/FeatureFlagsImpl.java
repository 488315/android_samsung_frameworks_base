package android.os.vibrator;

/* loaded from: classes3.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // android.os.vibrator.FeatureFlags
    public boolean adaptiveHapticsEnabled() {
        return true;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean hapticFeedbackVibrationOemCustomizationEnabled() {
        return true;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean keyboardCategoryEnabled() {
        return true;
    }

    @Override // android.os.vibrator.FeatureFlags
    public boolean useVibratorHapticFeedback() {
        return true;
    }
}
