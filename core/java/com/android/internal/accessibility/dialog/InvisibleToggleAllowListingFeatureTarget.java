package com.android.internal.accessibility.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.android.internal.accessibility.util.AccessibilityUtils;

class InvisibleToggleAllowListingFeatureTarget extends AccessibilityTarget {
    InvisibleToggleAllowListingFeatureTarget(
            Context context,
            int shortcutType,
            boolean isShortcutSwitched,
            String id,
            int uid,
            CharSequence label,
            Drawable icon,
            String key) {
        super(
                context,
                shortcutType,
                1,
                isShortcutSwitched,
                id,
                uid,
                label,
                AccessibilityUtils.isDefaultTheme(context)
                        ? icon
                        : context.getPackageManager().semGetDrawableForIconTray(icon, 1),
                key);
    }
}
