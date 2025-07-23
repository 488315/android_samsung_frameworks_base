package com.android.internal.hidden_from_bootclasspath.com.android.hardware.input;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ABORT_SLOW_MULTI_PRESS = "com.android.hardware.input.abort_slow_multi_press";
    public static final String FLAG_CAN_WINDOW_OVERRIDE_POWER_GESTURE_API = "com.android.hardware.input.can_window_override_power_gesture_api";
    public static final String FLAG_ENABLE_BACKUP_AND_RESTORE_FOR_INPUT_GESTURES = "com.android.hardware.input.enable_backup_and_restore_for_input_gestures";
    public static final String FLAG_ENABLE_CUSTOMIZABLE_INPUT_GESTURES = "com.android.hardware.input.enable_customizable_input_gestures";
    public static final String FLAG_ENABLE_DISPLAY_COLOR_INVERSION_KEY_GESTURES = "com.android.hardware.input.enable_display_color_inversion_key_gestures";
    public static final String FLAG_ENABLE_NEW_25Q2_KEYCODES = "com.android.hardware.input.enable_new_25q2_keycodes";
    public static final String FLAG_ENABLE_TALKBACK_AND_MAGNIFIER_KEY_GESTURES = "com.android.hardware.input.enable_talkback_and_magnifier_key_gestures";
    public static final String FLAG_ENABLE_VOICE_ACCESS_KEY_GESTURES = "com.android.hardware.input.enable_voice_access_key_gestures";
    public static final String FLAG_FIX_SEARCH_MODIFIER_FALLBACKS = "com.android.hardware.input.fix_search_modifier_fallbacks";
    public static final String FLAG_INPUT_MANAGER_LIFECYCLE_SUPPORT = "com.android.hardware.input.input_manager_lifecycle_support";
    public static final String FLAG_KEYBOARD_A11Y_MOUSE_KEYS = "com.android.hardware.input.keyboard_a11y_mouse_keys";
    public static final String FLAG_KEYBOARD_A11Y_SHORTCUT_CONTROL = "com.android.hardware.input.keyboard_a11y_shortcut_control";
    public static final String FLAG_KEYBOARD_GLYPH_MAP = "com.android.hardware.input.keyboard_glyph_map";
    public static final String FLAG_KEYBOARD_REPEAT_KEYS = "com.android.hardware.input.keyboard_repeat_keys";
    public static final String FLAG_KEY_EVENT_ACTIVITY_DETECTION = "com.android.hardware.input.key_event_activity_detection";
    public static final String FLAG_MANAGE_KEY_GESTURES = "com.android.hardware.input.manage_key_gestures";
    public static final String FLAG_MODIFIER_SHORTCUT_DUMP = "com.android.hardware.input.modifier_shortcut_dump";
    public static final String FLAG_MODIFIER_SHORTCUT_MANAGER_REFACTOR = "com.android.hardware.input.modifier_shortcut_manager_refactor";
    public static final String FLAG_MOUSE_REVERSE_VERTICAL_SCROLLING = "com.android.hardware.input.mouse_reverse_vertical_scrolling";
    public static final String FLAG_MOUSE_SCROLLING_ACCELERATION = "com.android.hardware.input.mouse_scrolling_acceleration";
    public static final String FLAG_MOUSE_SWAP_PRIMARY_BUTTON = "com.android.hardware.input.mouse_swap_primary_button";
    public static final String FLAG_OVERRIDE_POWER_KEY_BEHAVIOR_IN_FOCUSED_WINDOW = "com.android.hardware.input.override_power_key_behavior_in_focused_window";
    public static final String FLAG_POINTER_ACCELERATION = "com.android.hardware.input.pointer_acceleration";
    public static final String FLAG_REMOVE_FALLBACK_MODIFIERS = "com.android.hardware.input.remove_fallback_modifiers";
    public static final String FLAG_REQUEST_KEY_CAPTURE_API = "com.android.hardware.input.request_key_capture_api";
    public static final String FLAG_TOUCHPAD_SYSTEM_GESTURE_DISABLE = "com.android.hardware.input.touchpad_system_gesture_disable";
    public static final String FLAG_TOUCHPAD_THREE_FINGER_TAP_SHORTCUT = "com.android.hardware.input.touchpad_three_finger_tap_shortcut";
    public static final String FLAG_TOUCHPAD_VISUALIZER = "com.android.hardware.input.touchpad_visualizer";
    public static final String FLAG_USE_KEY_GESTURE_EVENT_HANDLER = "com.android.hardware.input.use_key_gesture_event_handler";
    public static final String FLAG_USE_KEY_GESTURE_EVENT_HANDLER_MULTI_KEY_GESTURES = "com.android.hardware.input.use_key_gesture_event_handler_multi_key_gestures";

    public static boolean abortSlowMultiPress() {
        return FEATURE_FLAGS.abortSlowMultiPress();
    }

    public static boolean canWindowOverridePowerGestureApi() {
        return FEATURE_FLAGS.canWindowOverridePowerGestureApi();
    }

    public static boolean enableBackupAndRestoreForInputGestures() {
        return FEATURE_FLAGS.enableBackupAndRestoreForInputGestures();
    }

    public static boolean enableCustomizableInputGestures() {
        return FEATURE_FLAGS.enableCustomizableInputGestures();
    }

    public static boolean enableDisplayColorInversionKeyGestures() {
        return FEATURE_FLAGS.enableDisplayColorInversionKeyGestures();
    }

    public static boolean enableNew25q2Keycodes() {
        return FEATURE_FLAGS.enableNew25q2Keycodes();
    }

    public static boolean enableTalkbackAndMagnifierKeyGestures() {
        return FEATURE_FLAGS.enableTalkbackAndMagnifierKeyGestures();
    }

    public static boolean enableVoiceAccessKeyGestures() {
        return FEATURE_FLAGS.enableVoiceAccessKeyGestures();
    }

    public static boolean fixSearchModifierFallbacks() {
        return FEATURE_FLAGS.fixSearchModifierFallbacks();
    }

    public static boolean inputManagerLifecycleSupport() {
        return FEATURE_FLAGS.inputManagerLifecycleSupport();
    }

    public static boolean keyEventActivityDetection() {
        return FEATURE_FLAGS.keyEventActivityDetection();
    }

    public static boolean keyboardA11yMouseKeys() {
        return FEATURE_FLAGS.keyboardA11yMouseKeys();
    }

    public static boolean keyboardA11yShortcutControl() {
        return FEATURE_FLAGS.keyboardA11yShortcutControl();
    }

    public static boolean keyboardGlyphMap() {
        return FEATURE_FLAGS.keyboardGlyphMap();
    }

    public static boolean keyboardRepeatKeys() {
        return FEATURE_FLAGS.keyboardRepeatKeys();
    }

    public static boolean manageKeyGestures() {
        return FEATURE_FLAGS.manageKeyGestures();
    }

    public static boolean modifierShortcutDump() {
        return FEATURE_FLAGS.modifierShortcutDump();
    }

    public static boolean modifierShortcutManagerRefactor() {
        return FEATURE_FLAGS.modifierShortcutManagerRefactor();
    }

    public static boolean mouseReverseVerticalScrolling() {
        return FEATURE_FLAGS.mouseReverseVerticalScrolling();
    }

    public static boolean mouseScrollingAcceleration() {
        return FEATURE_FLAGS.mouseScrollingAcceleration();
    }

    public static boolean mouseSwapPrimaryButton() {
        return FEATURE_FLAGS.mouseSwapPrimaryButton();
    }

    public static boolean overridePowerKeyBehaviorInFocusedWindow() {
        return FEATURE_FLAGS.overridePowerKeyBehaviorInFocusedWindow();
    }

    public static boolean pointerAcceleration() {
        return FEATURE_FLAGS.pointerAcceleration();
    }

    public static boolean removeFallbackModifiers() {
        return FEATURE_FLAGS.removeFallbackModifiers();
    }

    public static boolean requestKeyCaptureApi() {
        return FEATURE_FLAGS.requestKeyCaptureApi();
    }

    public static boolean touchpadSystemGestureDisable() {
        return FEATURE_FLAGS.touchpadSystemGestureDisable();
    }

    public static boolean touchpadThreeFingerTapShortcut() {
        return FEATURE_FLAGS.touchpadThreeFingerTapShortcut();
    }

    public static boolean touchpadVisualizer() {
        return FEATURE_FLAGS.touchpadVisualizer();
    }

    public static boolean useKeyGestureEventHandler() {
        return FEATURE_FLAGS.useKeyGestureEventHandler();
    }

    public static boolean useKeyGestureEventHandlerMultiKeyGestures() {
        return FEATURE_FLAGS.useKeyGestureEventHandlerMultiKeyGestures();
    }
}
