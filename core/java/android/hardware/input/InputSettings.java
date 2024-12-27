package android.hardware.input;

import android.app.AppGlobals;
import android.content.Context;
import android.provider.Settings;
import android.sysprop.InputProperties;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.com.android.hardware.input.Flags;

/* loaded from: classes2.dex */
public class InputSettings {
    public static final float DEFAULT_MAXIMUM_OBSCURING_OPACITY_FOR_TOUCH = 0.8f;
    public static final int DEFAULT_POINTER_SPEED = 0;
    public static final int DEFAULT_STYLUS_POINTER_ICON_ENABLED = 1;
    public static final int MAX_ACCESSIBILITY_BOUNCE_KEYS_THRESHOLD_MILLIS = 5000;
    public static final int MAX_ACCESSIBILITY_SLOW_KEYS_THRESHOLD_MILLIS = 5000;
    public static final int MAX_POINTER_SPEED = 7;
    public static final int MIN_POINTER_SPEED = -7;

    private InputSettings() {
    }

    public static int getPointerSpeed(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.POINTER_SPEED, 0);
    }

    public static void setPointerSpeed(Context context, int speed) {
        if (speed < -7 || speed > 7) {
            throw new IllegalArgumentException("speed out of range");
        }
        Settings.System.putInt(context.getContentResolver(), Settings.System.POINTER_SPEED, speed);
    }

    public static float getMaximumObscuringOpacityForTouch(Context context) {
        return Settings.Global.getFloat(context.getContentResolver(), Settings.Global.MAXIMUM_OBSCURING_OPACITY_FOR_TOUCH, 0.8f);
    }

    public static void setMaximumObscuringOpacityForTouch(Context context, float opacity) {
        if (opacity < 0.0f || opacity > 1.0f) {
            throw new IllegalArgumentException("Maximum obscuring opacity for touch should be >= 0 and <= 1");
        }
        Settings.Global.putFloat(context.getContentResolver(), Settings.Global.MAXIMUM_OBSCURING_OPACITY_FOR_TOUCH, opacity);
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

    public static void setTouchpadPointerSpeed(Context context, int speed) {
        if (speed < -7 || speed > 7) {
            throw new IllegalArgumentException("speed out of range");
        }
        Settings.System.putIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_POINTER_SPEED, speed, -2);
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

    public static boolean isTouchpadTapDraggingFeatureFlagEnabled() {
        return Flags.touchpadTapDragging();
    }

    public static boolean useTouchpadTapDragging(Context context) {
        return isTouchpadTapDraggingFeatureFlagEnabled() && Settings.System.getIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_TAP_DRAGGING, 0, -2) == 1;
    }

    public static void setTouchpadTapDragging(Context context, boolean z) {
        if (!isTouchpadTapDraggingFeatureFlagEnabled()) {
            return;
        }
        Settings.System.putIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_TAP_DRAGGING, z ? 1 : 0, -2);
    }

    public static boolean useTouchpadRightClickZone(Context context) {
        return Settings.System.getIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_RIGHT_CLICK_ZONE, 0, -2) == 1;
    }

    public static void setTouchpadRightClickZone(Context context, boolean z) {
        Settings.System.putIntForUser(context.getContentResolver(), Settings.System.TOUCHPAD_RIGHT_CLICK_ZONE, z ? 1 : 0, -2);
    }

    public static boolean isStylusPointerIconEnabled(Context context, boolean forceReloadSetting) {
        if (InputProperties.force_enable_stylus_pointer_icon().orElse(false).booleanValue()) {
            return true;
        }
        if (context.getResources().getBoolean(R.bool.config_enableStylusPointerIcon)) {
            return forceReloadSetting ? Settings.Secure.getIntForUser(context.getContentResolver(), Settings.Secure.STYLUS_POINTER_ICON_ENABLED, 1, -3) != 0 : AppGlobals.getIntCoreSetting(Settings.Secure.STYLUS_POINTER_ICON_ENABLED, 1) != 0;
        }
        return false;
    }

    public static boolean isStylusPointerIconEnabled(Context context) {
        return isStylusPointerIconEnabled(context, false);
    }

    public static boolean isAccessibilityBounceKeysFeatureEnabled() {
        return Flags.keyboardA11yBounceKeysFlag() && com.android.input.flags.Flags.enableInputFilterRustImpl();
    }

    public static boolean isAccessibilityBounceKeysEnabled(Context context) {
        return getAccessibilityBounceKeysThreshold(context) != 0;
    }

    public static int getAccessibilityBounceKeysThreshold(Context context) {
        if (isAccessibilityBounceKeysFeatureEnabled()) {
            return Settings.Secure.getIntForUser(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_BOUNCE_KEYS, 0, -2);
        }
        return 0;
    }

    public static void setAccessibilityBounceKeysThreshold(Context context, int thresholdTimeMillis) {
        if (!isAccessibilityBounceKeysFeatureEnabled()) {
            return;
        }
        if (thresholdTimeMillis < 0 || thresholdTimeMillis > 5000) {
            throw new IllegalArgumentException("Provided Bounce keys threshold should be in range [0, 5000]");
        }
        Settings.Secure.putIntForUser(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_BOUNCE_KEYS, thresholdTimeMillis, -2);
    }

    public static boolean isAccessibilitySlowKeysFeatureFlagEnabled() {
        return Flags.keyboardA11ySlowKeysFlag() && com.android.input.flags.Flags.enableInputFilterRustImpl();
    }

    public static boolean isAccessibilitySlowKeysEnabled(Context context) {
        return getAccessibilitySlowKeysThreshold(context) != 0;
    }

    public static int getAccessibilitySlowKeysThreshold(Context context) {
        if (isAccessibilitySlowKeysFeatureFlagEnabled()) {
            return Settings.Secure.getIntForUser(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_SLOW_KEYS, 0, -2);
        }
        return 0;
    }

    public static void setAccessibilitySlowKeysThreshold(Context context, int thresholdTimeMillis) {
        if (!isAccessibilitySlowKeysFeatureFlagEnabled()) {
            return;
        }
        if (thresholdTimeMillis < 0 || thresholdTimeMillis > 5000) {
            throw new IllegalArgumentException("Provided Slow keys threshold should be in range [0, 5000]");
        }
        Settings.Secure.putIntForUser(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_SLOW_KEYS, thresholdTimeMillis, -2);
    }

    public static boolean isAccessibilityStickyKeysFeatureEnabled() {
        return Flags.keyboardA11yStickyKeysFlag() && com.android.input.flags.Flags.enableInputFilterRustImpl();
    }

    public static boolean isAccessibilityStickyKeysEnabled(Context context) {
        return isAccessibilityStickyKeysFeatureEnabled() && Settings.Secure.getIntForUser(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_STICKY_KEYS, 0, -2) != 0;
    }

    public static void setAccessibilityStickyKeysEnabled(Context context, boolean z) {
        if (!isAccessibilityStickyKeysFeatureEnabled()) {
            return;
        }
        Settings.Secure.putIntForUser(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_STICKY_KEYS, z ? 1 : 0, -2);
    }
}
