package com.android.input.flags;

public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.input.flags.FeatureFlags
    public boolean a11yCrashOnInconsistentEventStream() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean deviceAssociations() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean disableRejectTouchOnStylusHover() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableGesturesLibraryTimerProvider() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableInboundEventVerification() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableInputEventTracing() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableInputFilterRustImpl() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableKeyboardClassifier() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableMultiDeviceInput() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableMultiDeviceSameWindowStream() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableNewMousePointerBallistics() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableOutboundEventVerification() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enablePredictionPruningViaJerkThresholding() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableTouchpadFlingStop() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableTouchpadTypingPalmRejection() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableV2TouchpadTypingPalmRejection() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean hidePointerIndicatorsForSecureWindows() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean inputDeviceViewBehaviorApi() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean overrideKeyBehaviorPermissionApis() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean rateLimitUserActivityPokeInDispatcher() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean removePointerEventTrackingInWm() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean reportPalmsToGesturesLibrary() {
        return true;
    }
}
