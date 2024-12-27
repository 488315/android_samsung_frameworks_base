package com.android.input.flags;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean a11yCrashOnInconsistentEventStream();

    boolean deviceAssociations();

    boolean disableRejectTouchOnStylusHover();

    boolean enableGesturesLibraryTimerProvider();

    boolean enableInboundEventVerification();

    boolean enableInputEventTracing();

    boolean enableInputFilterRustImpl();

    boolean enableKeyboardClassifier();

    boolean enableMultiDeviceInput();

    boolean enableMultiDeviceSameWindowStream();

    boolean enableNewMousePointerBallistics();

    boolean enableOutboundEventVerification();

    boolean enablePredictionPruningViaJerkThresholding();

    boolean enableTouchpadFlingStop();

    boolean enableTouchpadTypingPalmRejection();

    boolean enableV2TouchpadTypingPalmRejection();

    boolean hidePointerIndicatorsForSecureWindows();

    boolean inputDeviceViewBehaviorApi();

    boolean overrideKeyBehaviorPermissionApis();

    boolean rateLimitUserActivityPokeInDispatcher();

    boolean removePointerEventTrackingInWm();

    boolean reportPalmsToGesturesLibrary();
}
