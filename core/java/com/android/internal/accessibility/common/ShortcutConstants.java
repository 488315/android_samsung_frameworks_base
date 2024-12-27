package com.android.internal.accessibility.common;

import android.content.ComponentName;

import com.android.internal.accessibility.AccessibilityShortcutController;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

public final class ShortcutConstants {
    public static final String CHOOSER_PACKAGE_NAME = "android";
    public static final char SERVICES_SEPARATOR = ':';
    public static final int[] USER_SHORTCUT_TYPES = {1, 2, 4, 8, 16, 512};
    public static final Map<ComponentName, ComponentName> A11Y_FEATURE_TO_FRAMEWORK_TILE =
            Map.of(
                    AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME,
                    AccessibilityShortcutController.COLOR_INVERSION_TILE_COMPONENT_NAME,
                    AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME,
                    AccessibilityShortcutController.DALTONIZER_TILE_COMPONENT_NAME,
                    AccessibilityShortcutController.ONE_HANDED_COMPONENT_NAME,
                    AccessibilityShortcutController.ONE_HANDED_TILE_COMPONENT_NAME,
                    AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_COMPONENT_NAME,
                    AccessibilityShortcutController
                            .REDUCE_BRIGHT_COLORS_TILE_SERVICE_COMPONENT_NAME,
                    AccessibilityShortcutController.FONT_SIZE_COMPONENT_NAME,
                    AccessibilityShortcutController.FONT_SIZE_TILE_COMPONENT_NAME,
                    AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME,
                    AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_TILE_COMPONENT_NAME,
                    AccessibilityShortcutController.COLOR_LENS_COMPONENT_NAME,
                    AccessibilityShortcutController.COLOR_LENS_TILE_COMPONENT_NAME,
                    AccessibilityShortcutController.HIGH_CONTRAST_FONTS_COMPONENT_NAME,
                    AccessibilityShortcutController.HIGH_CONTRAST_FONTS_TILE_COMPONENT_NAME);

    @Retention(RetentionPolicy.SOURCE)
    public @interface AccessibilityFragmentType {
        public static final int INVISIBLE_TOGGLE = 1;
        public static final int LAUNCH_ACTIVITY = 3;
        public static final int TOGGLE = 2;
        public static final int VOLUME_SHORTCUT_TOGGLE = 0;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FloatingMenuSize {
        public static final int LARGE = 1;
        public static final int SMALL = 0;
        public static final int UNKNOWN = -1;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShortcutMenuMode {
        public static final int DISABLED = 5;
        public static final int EDIT = 1;
        public static final int LAUNCH = 0;
        public static final int NONE = 2;
        public static final int OFF = 4;
        public static final int ON = 3;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UserShortcutType {
        public static final int DEFAULT = 0;
        public static final int DIRECTACCESS = 512;
        public static final int HARDWARE = 2;
        public static final int QUICK_SETTINGS = 16;
        public static final int SOFTWARE = 1;
        public static final int TRIPLETAP = 4;
        public static final int TWOFINGER_DOUBLETAP = 8;
    }

    private ShortcutConstants() {}
}
