package com.android.internal.hidden_from_bootclasspath.com.android.hardware.input;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean abortSlowMultiPress();

    boolean canWindowOverridePowerGestureApi();

    boolean enableBackupAndRestoreForInputGestures();

    boolean enableCustomizableInputGestures();

    boolean enableDisplayColorInversionKeyGestures();

    boolean enableNew25q2Keycodes();

    boolean enableTalkbackAndMagnifierKeyGestures();

    boolean enableVoiceAccessKeyGestures();

    boolean fixSearchModifierFallbacks();

    boolean inputManagerLifecycleSupport();

    boolean keyEventActivityDetection();

    boolean keyboardA11yMouseKeys();

    boolean keyboardA11yShortcutControl();

    boolean keyboardGlyphMap();

    boolean keyboardRepeatKeys();

    boolean manageKeyGestures();

    boolean modifierShortcutDump();

    boolean modifierShortcutManagerRefactor();

    boolean mouseReverseVerticalScrolling();

    boolean mouseScrollingAcceleration();

    boolean mouseSwapPrimaryButton();

    boolean overridePowerKeyBehaviorInFocusedWindow();

    boolean pointerAcceleration();

    boolean removeFallbackModifiers();

    boolean requestKeyCaptureApi();

    boolean touchpadSystemGestureDisable();

    boolean touchpadThreeFingerTapShortcut();

    boolean touchpadVisualizer();

    boolean useKeyGestureEventHandler();

    boolean useKeyGestureEventHandlerMultiKeyGestures();
}
