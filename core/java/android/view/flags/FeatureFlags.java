package android.view.flags;

public interface FeatureFlags {
    boolean addSchandleToVriSurface();

    boolean customizableWindowHeaders();

    boolean enableArrowIconOnHoverWhenClickable();

    boolean enableInvalidateCheckThread();

    boolean enableSurfaceNativeAllocRegistrationRo();

    boolean enableUseMeasureCacheDuringForceLayout();

    boolean enableVectorCursorA11ySettings();

    boolean enableVectorCursors();

    boolean expectedPresentationTimeApi();

    boolean expectedPresentationTimeReadOnly();

    boolean scrollFeedbackApi();

    boolean sensitiveContentAppProtection();

    boolean sensitiveContentAppProtectionApi();

    boolean sensitiveContentPrematureProtectionRemovedFix();

    boolean setFrameRateCallback();

    boolean toolkitFrameRateBySizeReadOnly();

    boolean toolkitFrameRateDefaultNormalReadOnly();

    boolean toolkitFrameRateFunctionEnablingReadOnly();

    boolean toolkitFrameRateSmallUsesPercentReadOnly();

    boolean toolkitFrameRateTypingReadOnly();

    boolean toolkitFrameRateVelocityMappingReadOnly();

    boolean toolkitFrameRateViewEnablingReadOnly();

    boolean toolkitMetricsForFrameRateDecision();

    boolean toolkitSetFrameRate();

    boolean toolkitSetFrameRateReadOnly();

    boolean useViewBasedRotaryEncoderScrollHaptics();

    boolean viewVelocityApi();
}
