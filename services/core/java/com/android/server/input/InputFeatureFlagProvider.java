package com.android.server.input;

import android.sysprop.InputProperties;

import java.util.Optional;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class InputFeatureFlagProvider {
    public static final boolean AMBIENT_KEYBOARD_BACKLIGHT_CONTROL_ENABLED;
    public static final boolean KEYBOARD_BACKLIGHT_ANIMATION_ENABLED;
    public static final boolean KEYBOARD_BACKLIGHT_CONTROL_ENABLED;
    public static final boolean KEYBOARD_BACKLIGHT_CUSTOM_LEVELS_ENABLED;
    public static final Optional sAmbientKeyboardBacklightControlOverride;
    public static final Optional sKeyboardBacklightAnimationOverride;
    public static final Optional sKeyboardBacklightControlOverride;
    public static final Optional sKeyboardBacklightCustomLevelsOverride;

    static {
        Optional enable_keyboard_backlight_control =
                InputProperties.enable_keyboard_backlight_control();
        Boolean bool = Boolean.TRUE;
        KEYBOARD_BACKLIGHT_CONTROL_ENABLED =
                ((Boolean) enable_keyboard_backlight_control.orElse(bool)).booleanValue();
        KEYBOARD_BACKLIGHT_ANIMATION_ENABLED =
                ((Boolean)
                                InputProperties.enable_keyboard_backlight_animation()
                                        .orElse(Boolean.FALSE))
                        .booleanValue();
        KEYBOARD_BACKLIGHT_CUSTOM_LEVELS_ENABLED =
                ((Boolean) InputProperties.enable_keyboard_backlight_custom_levels().orElse(bool))
                        .booleanValue();
        AMBIENT_KEYBOARD_BACKLIGHT_CONTROL_ENABLED =
                ((Boolean) InputProperties.enable_ambient_keyboard_backlight_control().orElse(bool))
                        .booleanValue();
        sKeyboardBacklightControlOverride = Optional.empty();
        sKeyboardBacklightAnimationOverride = Optional.empty();
        sKeyboardBacklightCustomLevelsOverride = Optional.empty();
        sAmbientKeyboardBacklightControlOverride = Optional.empty();
    }

    public static boolean isAmbientKeyboardBacklightControlEnabled() {
        return ((Boolean)
                        sAmbientKeyboardBacklightControlOverride.orElse(
                                Boolean.valueOf(AMBIENT_KEYBOARD_BACKLIGHT_CONTROL_ENABLED)))
                .booleanValue();
    }
}
