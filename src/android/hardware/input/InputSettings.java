package android.hardware.input;

import android.app.AppGlobals;
import android.content.Context;
import android.provider.Settings;
import android.sysprop.InputProperties;
import android.view.ViewConfiguration;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.Flags;

/* loaded from: classes2.dex */
public class InputSettings {
    public static final int DEFAULT_BOUNCE_KEYS_THRESHOLD_MILLIS = 500;
    public static final float DEFAULT_MAXIMUM_OBSCURING_OPACITY_FOR_TOUCH = 0.8f;
    public static final int DEFAULT_MOUSE_SCROLLING_SPEED = 0;
    public static final int DEFAULT_POINTER_SPEED = 0;
    public static final int DEFAULT_SLOW_KEYS_THRESHOLD_MILLIS = 500;
    public static final int DEFAULT_STYLUS_POINTER_ICON_ENABLED = 1;
    public static final int MAX_ACCESSIBILITY_BOUNCE_KEYS_THRESHOLD_MILLIS = 5000;
    public static final int MAX_ACCESSIBILITY_SLOW_KEYS_THRESHOLD_MILLIS = 5000;
    public static final int MAX_KEY_REPEAT_DELAY_MILLIS = 2000;
    public static final int MAX_KEY_REPEAT_TIMEOUT_MILLIS = 2000;
    public static final int MAX_MOUSE_SCROLLING_SPEED = 7;
    public static final int MAX_POINTER_SPEED = 7;
    public static final int MIN_KEY_REPEAT_DELAY_MILLIS = 20;
    public static final int MIN_KEY_REPEAT_TIMEOUT_MILLIS = 150;
    public static final int MIN_MOUSE_SCROLLING_SPEED = -7;
    public static final int MIN_POINTER_SPEED = -7;

    private InputSettings() {
    }

    public static int getPointerSpeed(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.POINTER_SPEED, 0);
    }

    public static void setPointerSpeed(Context context, int i) {
        if (i < -7 || i > 7) {
            throw new IllegalArgumentException("speed out of range");
        }
        Settings.System.putInt(context.getContentResolver(), Settings.System.POINTER_SPEED, i);
    }

    public static float getMaximumObscuringOpacityForTouch(Context context) {
        return Settings.Global.getFloat(context.getContentResolver(), Settings.Global.MAXIMUM_OBSCURING_OPACITY_FOR_TOUCH, 0.8f);
    }

    public static void setMaximumObscuringOpacityForTouch(Context context, float f) {
        if (f < 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("Maximum obscuring opacity for touch should be >= 0 and <= 1");
        }
        Settings.Global.putFloat(context.getContentResolver(), Settings.Global.MAXIMUM_OBSCURING_OPACITY_FOR_TOUCH, f);
    }

    public static boolean isStylusEverUsed(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), Settings.Global.STYLUS_EVER_USED, 0) == 1;
    }

    public static void setStylusEverUsed(Context context, boolean z) {
        Settings.Global.putInt(context.getContentResolver(), Settings.Global.STYLUS_EVER_USED, z ? 1 : 0);
    }

    public static int getTouchpadPointerSpeed(Context context) {
        return Settings.System.getIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_POINTER_SPEED, 0, -2);
    }

    public static void setTouchpadPointerSpeed(Context context, int i) {
        if (i < -7 || i > 7) {
            throw new IllegalArgumentException("speed out of range");
        }
        Settings.System.putIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_POINTER_SPEED, i, -2);
    }

    public static boolean useTouchpadNaturalScrolling(Context context) {
        return Settings.System.getIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_NATURAL_SCROLLING, 1, -2) == 1;
    }

    public static void setTouchpadNaturalScrolling(Context context, boolean z) {
        Settings.System.putIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_NATURAL_SCROLLING, z ? 1 : 0, -2);
    }

    public static boolean useTouchpadTapToClick(Context context) {
        return Settings.System.getIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_TAP_TO_CLICK, 1, -2) == 1;
    }

    public static void setTouchpadTapToClick(Context context, boolean z) {
        Settings.System.putIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_TAP_TO_CLICK, z ? 1 : 0, -2);
    }

    public static boolean isTouchpadAccelerationEnabled(Context context) {
        return !isPointerAccelerationFeatureFlagEnabled() || Settings.System.getIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_ACCELERATION_ENABLED, 1, -2) == 1;
    }

    public static void setTouchpadAccelerationEnabled(Context context, boolean z) {
        if (isPointerAccelerationFeatureFlagEnabled()) {
            Settings.System.putIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_ACCELERATION_ENABLED, z ? 1 : 0, -2);
        }
    }

    public static boolean isTouchpadSystemGestureDisableFeatureFlagEnabled() {
        return Flags.touchpadSystemGestureDisable();
    }

    public static boolean isTouchpadVisualizerFeatureFlagEnabled() {
        return Flags.touchpadVisualizer();
    }

    public static boolean isTouchpadThreeFingerTapShortcutFeatureFlagEnabled() {
        return isCustomizableInputGesturesFeatureFlagEnabled() && Flags.touchpadThreeFingerTapShortcut();
    }

    public static boolean isMouseScrollingAccelerationFeatureFlagEnabled() {
        return Flags.mouseScrollingAcceleration();
    }

    public static boolean isMouseReverseVerticalScrollingFeatureFlagEnabled() {
        return Flags.mouseReverseVerticalScrolling();
    }

    public static boolean isMouseSwapPrimaryButtonFeatureFlagEnabled() {
        return Flags.mouseSwapPrimaryButton();
    }

    public static boolean isPointerAccelerationFeatureFlagEnabled() {
        return Flags.pointerAcceleration();
    }

    public static boolean useTouchpadVisualizer(Context context) {
        return isTouchpadVisualizerFeatureFlagEnabled() && Settings.System.getIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_VISUALIZER, 0, -2) == 1;
    }

    public static void setTouchpadVisualizer(Context context, boolean z) {
        if (isTouchpadVisualizerFeatureFlagEnabled()) {
            Settings.System.putIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_VISUALIZER, z ? 1 : 0, -2);
        }
    }

    public static boolean useTouchpadTapDragging(Context context) {
        return Settings.System.getIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_TAP_DRAGGING, 0, -2) == 1;
    }

    public static void setTouchpadTapDragging(Context context, boolean z) {
        Settings.System.putIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_TAP_DRAGGING, z ? 1 : 0, -2);
    }

    public static boolean useTouchpadRightClickZone(Context context) {
        return Settings.System.getIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_RIGHT_CLICK_ZONE, 0, -2) == 1;
    }

    public static void setTouchpadRightClickZone(Context context, boolean z) {
        Settings.System.putIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_RIGHT_CLICK_ZONE, z ? 1 : 0, -2);
    }

    public static boolean useTouchpadThreeFingerTapShortcut(Context context) {
        return Settings.System.getIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_THREE_FINGER_TAP_CUSTOMIZATION, 0, -2) != 0 && isTouchpadThreeFingerTapShortcutFeatureFlagEnabled();
    }

    public static boolean useTouchpadSystemGestures(Context context) {
        return !isTouchpadSystemGestureDisableFeatureFlagEnabled() || Settings.System.getIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_SYSTEM_GESTURES, 1, -2) == 1;
    }

    public static void setTouchpadSystemGesturesEnabled(Context context, boolean z) {
        if (isTouchpadSystemGestureDisableFeatureFlagEnabled()) {
            Settings.System.putIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_SYSTEM_GESTURES, z ? 1 : 0, -2);
        }
    }

    public static boolean isStylusPointerIconEnabled(Context context, boolean z) {
        if (InputProperties.force_enable_stylus_pointer_icon().orElse(false).booleanValue()) {
            return true;
        }
        if (context.getResources().getBoolean(R.bool.config_enableStylusPointerIcon)) {
            return z ? Settings.Secure.getIntForUser(context.getContentResolver(), Settings.Secure.STYLUS_POINTER_ICON_ENABLED, 1, -3) != 0 : AppGlobals.getIntCoreSetting(Settings.Secure.STYLUS_POINTER_ICON_ENABLED, 1) != 0;
        }
        return false;
    }

    public static boolean isStylusPointerIconEnabled(Context context) {
        return isStylusPointerIconEnabled(context, false);
    }

    public static boolean isMouseScrollingAccelerationEnabled(Context context) {
        return (isMouseScrollingAccelerationFeatureFlagEnabled() && Settings.System.getIntForUser(context.getContentResolver(), Settings.System.MOUSE_SCROLLING_ACCELERATION, 0, -2) == 0) ? false : true;
    }

    public static void setMouseScrollingAcceleration(Context context, boolean z) {
        if (isMouseScrollingAccelerationFeatureFlagEnabled()) {
            Settings.System.putIntForUser(context.getContentResolver(), Settings.System.MOUSE_SCROLLING_ACCELERATION, z ? 1 : 0, -2);
        }
    }

    public static int getMouseScrollingSpeed(Context context) {
        if (isMouseScrollingAccelerationFeatureFlagEnabled()) {
            return Settings.System.getIntForUser(context.getContentResolver(), Settings.System.MOUSE_SCROLLING_SPEED, 0, -2);
        }
        return 0;
    }

    public static void setMouseScrollingSpeed(Context context, int i) {
        if (isMouseScrollingAccelerationEnabled(context)) {
            return;
        }
        if (i < -7 || i > 7) {
            throw new IllegalArgumentException("speed out of range");
        }
        Settings.System.putIntForUser(context.getContentResolver(), Settings.System.MOUSE_SCROLLING_SPEED, i, -2);
    }

    public static boolean isMouseReverseVerticalScrollingEnabled(Context context) {
        return isMouseReverseVerticalScrollingFeatureFlagEnabled() && Settings.System.getIntForUser(context.getContentResolver(), Settings.System.MOUSE_REVERSE_VERTICAL_SCROLLING, 0, -2) != 0;
    }

    public static void setMouseReverseVerticalScrolling(Context context, boolean z) {
        if (isMouseReverseVerticalScrollingFeatureFlagEnabled()) {
            Settings.System.putIntForUser(context.getContentResolver(), Settings.System.MOUSE_REVERSE_VERTICAL_SCROLLING, z ? 1 : 0, -2);
        }
    }

    public static boolean isMouseSwapPrimaryButtonEnabled(Context context) {
        return isMouseSwapPrimaryButtonFeatureFlagEnabled() && Settings.System.getIntForUser(context.getContentResolver(), Settings.System.MOUSE_SWAP_PRIMARY_BUTTON, 0, -2) != 0;
    }

    public static void setMouseSwapPrimaryButton(Context context, boolean z) {
        if (isMouseSwapPrimaryButtonFeatureFlagEnabled()) {
            Settings.System.putIntForUser(context.getContentResolver(), Settings.System.MOUSE_SWAP_PRIMARY_BUTTON, z ? 1 : 0, -2);
        }
    }

    public static boolean isMousePointerAccelerationEnabled(Context context) {
        return !isPointerAccelerationFeatureFlagEnabled() || Settings.System.getIntForUser(context.getContentResolver(), Settings.System.MOUSE_POINTER_ACCELERATION_ENABLED, 1, -2) == 1;
    }

    public static void setMouseAccelerationEnabled(Context context, boolean z) {
        if (isPointerAccelerationFeatureFlagEnabled()) {
            Settings.System.putIntForUser(context.getContentResolver(), Settings.System.MOUSE_POINTER_ACCELERATION_ENABLED, z ? 1 : 0, -2);
        }
    }

    public static boolean isAccessibilityBounceKeysEnabled(Context context) {
        return getAccessibilityBounceKeysThreshold(context) != 0;
    }

    public static int getAccessibilityBounceKeysThreshold(Context context) {
        return Settings.Secure.getIntForUser(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_BOUNCE_KEYS, 0, -2);
    }

    public static void setAccessibilityBounceKeysThreshold(Context context, int i) {
        if (i < 0 || i > 5000) {
            throw new IllegalArgumentException("Provided Bounce keys threshold should be in range [0, 5000]");
        }
        Settings.Secure.putIntForUser(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_BOUNCE_KEYS, i, -2);
    }

    public static boolean isAccessibilitySlowKeysEnabled(Context context) {
        return getAccessibilitySlowKeysThreshold(context) != 0;
    }

    public static int getAccessibilitySlowKeysThreshold(Context context) {
        return Settings.Secure.getIntForUser(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_SLOW_KEYS, 0, -2);
    }

    public static void setAccessibilitySlowKeysThreshold(Context context, int i) {
        if (i < 0 || i > 5000) {
            throw new IllegalArgumentException("Provided Slow keys threshold should be in range [0, 5000]");
        }
        Settings.Secure.putIntForUser(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_SLOW_KEYS, i, -2);
    }

    public static boolean isAccessibilityStickyKeysEnabled(Context context) {
        return Settings.Secure.getIntForUser(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_STICKY_KEYS, 0, -2) != 0;
    }

    public static void setAccessibilityStickyKeysEnabled(Context context, boolean z) {
        Settings.Secure.putIntForUser(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_STICKY_KEYS, z ? 1 : 0, -2);
    }

    public static boolean isAccessibilityMouseKeysFeatureFlagEnabled() {
        return Flags.keyboardA11yMouseKeys();
    }

    public static boolean isAccessibilityMouseKeysEnabled(Context context) {
        return isAccessibilityMouseKeysFeatureFlagEnabled() && Settings.Secure.getIntForUser(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_MOUSE_KEYS_ENABLED, 0, -2) != 0;
    }

    public static void setAccessibilityMouseKeysEnabled(Context context, boolean z) {
        if (isAccessibilityMouseKeysFeatureFlagEnabled()) {
            Settings.Secure.putIntForUser(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_MOUSE_KEYS_ENABLED, z ? 1 : 0, -2);
        }
    }

    public static boolean isRepeatKeysFeatureFlagEnabled() {
        return com.android.input.flags.Flags.keyboardRepeatKeys();
    }

    public static boolean isRepeatKeysEnabled(Context context) {
        return (isRepeatKeysFeatureFlagEnabled() && Settings.Secure.getIntForUser(context.getContentResolver(), Settings.Secure.KEY_REPEAT_ENABLED, 1, -2) == 0) ? false : true;
    }

    public static int getRepeatKeysTimeout(Context context) {
        if (!isRepeatKeysFeatureFlagEnabled()) {
            return ViewConfiguration.getKeyRepeatTimeout();
        }
        return Settings.Secure.getIntForUser(context.getContentResolver(), Settings.Secure.KEY_REPEAT_TIMEOUT_MS, ViewConfiguration.getKeyRepeatTimeout(), -2);
    }

    public static int getRepeatKeysDelay(Context context) {
        if (!isRepeatKeysFeatureFlagEnabled()) {
            return ViewConfiguration.getKeyRepeatDelay();
        }
        return Settings.Secure.getIntForUser(context.getContentResolver(), Settings.Secure.KEY_REPEAT_DELAY_MS, ViewConfiguration.getKeyRepeatDelay(), -2);
    }

    public static void setRepeatKeysEnabled(Context context, boolean z) {
        if (isRepeatKeysFeatureFlagEnabled()) {
            Settings.Secure.putIntForUser(context.getContentResolver(), Settings.Secure.KEY_REPEAT_ENABLED, z ? 1 : 0, -2);
        }
    }

    public static void setRepeatKeysTimeout(Context context, int i) {
        if (isRepeatKeysFeatureFlagEnabled() || isRepeatKeysEnabled(context)) {
            if (i < 150 || i > 2000) {
                throw new IllegalArgumentException("Provided repeat keys timeout should be in range (150,2000)");
            }
            Settings.Secure.putIntForUser(context.getContentResolver(), Settings.Secure.KEY_REPEAT_TIMEOUT_MS, i, -2);
        }
    }

    public static void setRepeatKeysDelay(Context context, int i) {
        if (isRepeatKeysFeatureFlagEnabled() || isRepeatKeysEnabled(context)) {
            if (i < 20 || i > 2000) {
                throw new IllegalArgumentException("Provided repeat keys delay should be in range (20,2000)");
            }
            Settings.Secure.putIntForUser(context.getContentResolver(), Settings.Secure.KEY_REPEAT_DELAY_MS, i, -2);
        }
    }

    public static boolean isCustomizableInputGesturesFeatureFlagEnabled() {
        return Flags.enableCustomizableInputGestures() && Flags.useKeyGestureEventHandler();
    }

    public static boolean doesKeyGestureEventHandlerSupportMultiKeyGestures() {
        return Flags.useKeyGestureEventHandler() && Flags.useKeyGestureEventHandlerMultiKeyGestures();
    }
}
