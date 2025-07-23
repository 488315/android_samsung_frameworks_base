package com.android.input.flags;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean a11yCrashOnInconsistentEventStream();

    boolean allowTransferOfEntireGesture();

    boolean collectPalmRejectionQualityMetrics();

    boolean connectedDisplaysAssociatedDisplayCursorBugfix();

    boolean connectedDisplaysCursor();

    boolean deprecateSplitTouchApis();

    boolean deviceAssociations();

    boolean disableRejectTouchOnStylusHover();

    boolean disableTouchInputMapperPointerUsage();

    boolean enableAlphabeticKeyboardWake();

    boolean enableButtonStateVerification();

    boolean enableDisplayTopologyValidation();

    boolean enableInboundEventVerification();

    boolean enableInputEventTracing();

    boolean enableInputPolicyProfile();

    boolean enableKeyboardClassifier();

    boolean enableMultiDeviceInput();

    boolean enableMultiDeviceSameWindowStream();

    boolean enableOutboundEventVerification();

    boolean enablePerDeviceInputLatencyMetrics();

    boolean enablePredictionPruningViaJerkThresholding();

    boolean enableTouchpadNoFocusChange();

    boolean enableV2TouchpadTypingPalmRejection();

    boolean inputDeviceViewBehaviorApi();

    boolean keyboardRepeatKeys();

    boolean overrideKeyBehaviorPermissionApis();

    boolean preventMergingInputPointerDevices();

    boolean rateLimitUserActivityPokeInDispatcher();

    boolean rotaryInputTelemetry();

    boolean setInputDeviceKernelWake();

    boolean showPointersForPartialScreenshare();

    boolean useClonedScreenCoordinatesAsRaw();
}
