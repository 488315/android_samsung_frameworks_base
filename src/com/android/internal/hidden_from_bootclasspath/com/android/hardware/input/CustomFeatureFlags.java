package com.android.internal.hidden_from_bootclasspath.com.android.hardware.input;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ABORT_SLOW_MULTI_PRESS, Flags.FLAG_CAN_WINDOW_OVERRIDE_POWER_GESTURE_API, Flags.FLAG_ENABLE_BACKUP_AND_RESTORE_FOR_INPUT_GESTURES, Flags.FLAG_ENABLE_CUSTOMIZABLE_INPUT_GESTURES, Flags.FLAG_ENABLE_DISPLAY_COLOR_INVERSION_KEY_GESTURES, Flags.FLAG_ENABLE_NEW_25Q2_KEYCODES, Flags.FLAG_ENABLE_TALKBACK_AND_MAGNIFIER_KEY_GESTURES, Flags.FLAG_ENABLE_VOICE_ACCESS_KEY_GESTURES, Flags.FLAG_FIX_SEARCH_MODIFIER_FALLBACKS, Flags.FLAG_INPUT_MANAGER_LIFECYCLE_SUPPORT, Flags.FLAG_KEY_EVENT_ACTIVITY_DETECTION, Flags.FLAG_KEYBOARD_A11Y_MOUSE_KEYS, Flags.FLAG_KEYBOARD_A11Y_SHORTCUT_CONTROL, Flags.FLAG_KEYBOARD_GLYPH_MAP, Flags.FLAG_KEYBOARD_REPEAT_KEYS, Flags.FLAG_MANAGE_KEY_GESTURES, Flags.FLAG_MODIFIER_SHORTCUT_DUMP, Flags.FLAG_MODIFIER_SHORTCUT_MANAGER_REFACTOR, Flags.FLAG_MOUSE_REVERSE_VERTICAL_SCROLLING, Flags.FLAG_MOUSE_SCROLLING_ACCELERATION, Flags.FLAG_MOUSE_SWAP_PRIMARY_BUTTON, Flags.FLAG_OVERRIDE_POWER_KEY_BEHAVIOR_IN_FOCUSED_WINDOW, Flags.FLAG_POINTER_ACCELERATION, Flags.FLAG_REMOVE_FALLBACK_MODIFIERS, Flags.FLAG_REQUEST_KEY_CAPTURE_API, Flags.FLAG_TOUCHPAD_SYSTEM_GESTURE_DISABLE, Flags.FLAG_TOUCHPAD_THREE_FINGER_TAP_SHORTCUT, Flags.FLAG_TOUCHPAD_VISUALIZER, Flags.FLAG_USE_KEY_GESTURE_EVENT_HANDLER, Flags.FLAG_USE_KEY_GESTURE_EVENT_HANDLER_MULTI_KEY_GESTURES, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean abortSlowMultiPress() {
        return getValue(Flags.FLAG_ABORT_SLOW_MULTI_PRESS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).abortSlowMultiPress();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean canWindowOverridePowerGestureApi() {
        return getValue(Flags.FLAG_CAN_WINDOW_OVERRIDE_POWER_GESTURE_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).canWindowOverridePowerGestureApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean enableBackupAndRestoreForInputGestures() {
        return getValue(Flags.FLAG_ENABLE_BACKUP_AND_RESTORE_FOR_INPUT_GESTURES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableBackupAndRestoreForInputGestures();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean enableCustomizableInputGestures() {
        return getValue(Flags.FLAG_ENABLE_CUSTOMIZABLE_INPUT_GESTURES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableCustomizableInputGestures();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean enableDisplayColorInversionKeyGestures() {
        return getValue(Flags.FLAG_ENABLE_DISPLAY_COLOR_INVERSION_KEY_GESTURES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableDisplayColorInversionKeyGestures();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean enableNew25q2Keycodes() {
        return getValue(Flags.FLAG_ENABLE_NEW_25Q2_KEYCODES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableNew25q2Keycodes();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean enableTalkbackAndMagnifierKeyGestures() {
        return getValue(Flags.FLAG_ENABLE_TALKBACK_AND_MAGNIFIER_KEY_GESTURES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableTalkbackAndMagnifierKeyGestures();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean enableVoiceAccessKeyGestures() {
        return getValue(Flags.FLAG_ENABLE_VOICE_ACCESS_KEY_GESTURES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda19
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enableVoiceAccessKeyGestures();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean fixSearchModifierFallbacks() {
        return getValue(Flags.FLAG_FIX_SEARCH_MODIFIER_FALLBACKS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).fixSearchModifierFallbacks();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean inputManagerLifecycleSupport() {
        return getValue(Flags.FLAG_INPUT_MANAGER_LIFECYCLE_SUPPORT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).inputManagerLifecycleSupport();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean keyEventActivityDetection() {
        return getValue(Flags.FLAG_KEY_EVENT_ACTIVITY_DETECTION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).keyEventActivityDetection();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean keyboardA11yMouseKeys() {
        return getValue(Flags.FLAG_KEYBOARD_A11Y_MOUSE_KEYS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).keyboardA11yMouseKeys();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean keyboardA11yShortcutControl() {
        return getValue(Flags.FLAG_KEYBOARD_A11Y_SHORTCUT_CONTROL, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda22
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).keyboardA11yShortcutControl();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean keyboardGlyphMap() {
        return getValue(Flags.FLAG_KEYBOARD_GLYPH_MAP, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).keyboardGlyphMap();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean keyboardRepeatKeys() {
        return getValue(Flags.FLAG_KEYBOARD_REPEAT_KEYS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).keyboardRepeatKeys();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean manageKeyGestures() {
        return getValue(Flags.FLAG_MANAGE_KEY_GESTURES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).manageKeyGestures();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean modifierShortcutDump() {
        return getValue(Flags.FLAG_MODIFIER_SHORTCUT_DUMP, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).modifierShortcutDump();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean modifierShortcutManagerRefactor() {
        return getValue(Flags.FLAG_MODIFIER_SHORTCUT_MANAGER_REFACTOR, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).modifierShortcutManagerRefactor();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean mouseReverseVerticalScrolling() {
        return getValue(Flags.FLAG_MOUSE_REVERSE_VERTICAL_SCROLLING, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).mouseReverseVerticalScrolling();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean mouseScrollingAcceleration() {
        return getValue(Flags.FLAG_MOUSE_SCROLLING_ACCELERATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).mouseScrollingAcceleration();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean mouseSwapPrimaryButton() {
        return getValue(Flags.FLAG_MOUSE_SWAP_PRIMARY_BUTTON, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).mouseSwapPrimaryButton();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean overridePowerKeyBehaviorInFocusedWindow() {
        return getValue(Flags.FLAG_OVERRIDE_POWER_KEY_BEHAVIOR_IN_FOCUSED_WINDOW, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).overridePowerKeyBehaviorInFocusedWindow();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean pointerAcceleration() {
        return getValue(Flags.FLAG_POINTER_ACCELERATION, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).pointerAcceleration();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean removeFallbackModifiers() {
        return getValue(Flags.FLAG_REMOVE_FALLBACK_MODIFIERS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).removeFallbackModifiers();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean requestKeyCaptureApi() {
        return getValue(Flags.FLAG_REQUEST_KEY_CAPTURE_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).requestKeyCaptureApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean touchpadSystemGestureDisable() {
        return getValue(Flags.FLAG_TOUCHPAD_SYSTEM_GESTURE_DISABLE, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).touchpadSystemGestureDisable();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean touchpadThreeFingerTapShortcut() {
        return getValue(Flags.FLAG_TOUCHPAD_THREE_FINGER_TAP_SHORTCUT, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).touchpadThreeFingerTapShortcut();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean touchpadVisualizer() {
        return getValue(Flags.FLAG_TOUCHPAD_VISUALIZER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).touchpadVisualizer();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean useKeyGestureEventHandler() {
        return getValue(Flags.FLAG_USE_KEY_GESTURE_EVENT_HANDLER, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda26
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useKeyGestureEventHandler();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.FeatureFlags
    public boolean useKeyGestureEventHandlerMultiKeyGestures() {
        return getValue(Flags.FLAG_USE_KEY_GESTURE_EVENT_HANDLER_MULTI_KEY_GESTURES, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.CustomFeatureFlags$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).useKeyGestureEventHandlerMultiKeyGestures();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ABORT_SLOW_MULTI_PRESS, Flags.FLAG_CAN_WINDOW_OVERRIDE_POWER_GESTURE_API, Flags.FLAG_ENABLE_BACKUP_AND_RESTORE_FOR_INPUT_GESTURES, Flags.FLAG_ENABLE_CUSTOMIZABLE_INPUT_GESTURES, Flags.FLAG_ENABLE_DISPLAY_COLOR_INVERSION_KEY_GESTURES, Flags.FLAG_ENABLE_NEW_25Q2_KEYCODES, Flags.FLAG_ENABLE_TALKBACK_AND_MAGNIFIER_KEY_GESTURES, Flags.FLAG_ENABLE_VOICE_ACCESS_KEY_GESTURES, Flags.FLAG_FIX_SEARCH_MODIFIER_FALLBACKS, Flags.FLAG_INPUT_MANAGER_LIFECYCLE_SUPPORT, Flags.FLAG_KEY_EVENT_ACTIVITY_DETECTION, Flags.FLAG_KEYBOARD_A11Y_MOUSE_KEYS, Flags.FLAG_KEYBOARD_A11Y_SHORTCUT_CONTROL, Flags.FLAG_KEYBOARD_GLYPH_MAP, Flags.FLAG_KEYBOARD_REPEAT_KEYS, Flags.FLAG_MANAGE_KEY_GESTURES, Flags.FLAG_MODIFIER_SHORTCUT_DUMP, Flags.FLAG_MODIFIER_SHORTCUT_MANAGER_REFACTOR, Flags.FLAG_MOUSE_REVERSE_VERTICAL_SCROLLING, Flags.FLAG_MOUSE_SCROLLING_ACCELERATION, Flags.FLAG_MOUSE_SWAP_PRIMARY_BUTTON, Flags.FLAG_OVERRIDE_POWER_KEY_BEHAVIOR_IN_FOCUSED_WINDOW, Flags.FLAG_POINTER_ACCELERATION, Flags.FLAG_REMOVE_FALLBACK_MODIFIERS, Flags.FLAG_REQUEST_KEY_CAPTURE_API, Flags.FLAG_TOUCHPAD_SYSTEM_GESTURE_DISABLE, Flags.FLAG_TOUCHPAD_THREE_FINGER_TAP_SHORTCUT, Flags.FLAG_TOUCHPAD_VISUALIZER, Flags.FLAG_USE_KEY_GESTURE_EVENT_HANDLER, Flags.FLAG_USE_KEY_GESTURE_EVENT_HANDLER_MULTI_KEY_GESTURES);
    }
}
