package com.android.input.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.input.flags.FeatureFlags
    public boolean a11yCrashOnInconsistentEventStream() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean allowTransferOfEntireGesture() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean collectPalmRejectionQualityMetrics() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean connectedDisplaysAssociatedDisplayCursorBugfix() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean connectedDisplaysCursor() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean deprecateSplitTouchApis() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean deviceAssociations() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean disableRejectTouchOnStylusHover() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean disableTouchInputMapperPointerUsage() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableAlphabeticKeyboardWake() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableButtonStateVerification() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableDisplayTopologyValidation() {
        return false;
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
    public boolean enableInputPolicyProfile() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableKeyboardClassifier() {
        return true;
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
    public boolean enableOutboundEventVerification() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enablePerDeviceInputLatencyMetrics() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enablePredictionPruningViaJerkThresholding() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableTouchpadNoFocusChange() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean enableV2TouchpadTypingPalmRejection() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean inputDeviceViewBehaviorApi() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean keyboardRepeatKeys() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean overrideKeyBehaviorPermissionApis() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean preventMergingInputPointerDevices() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean rateLimitUserActivityPokeInDispatcher() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean rotaryInputTelemetry() {
        return true;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean setInputDeviceKernelWake() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean showPointersForPartialScreenshare() {
        return false;
    }

    @Override // com.android.input.flags.FeatureFlags
    public boolean useClonedScreenCoordinatesAsRaw() {
        return true;
    }
}
