package com.android.internal.hidden_from_bootclasspath.com.android.hardware.input;

public interface FeatureFlags {
    boolean emojiAndScreenshotKeycodesAvailable();

    boolean keyboardA11yBounceKeysFlag();

    boolean keyboardA11ySlowKeysFlag();

    boolean keyboardA11yStickyKeysFlag();

    boolean keyboardLayoutPreviewFlag();

    boolean pointerCoordsIsResampledApi();

    boolean touchpadTapDragging();
}
