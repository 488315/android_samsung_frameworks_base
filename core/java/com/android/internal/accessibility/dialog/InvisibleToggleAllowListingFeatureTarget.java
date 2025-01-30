package com.android.internal.accessibility.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;

/* loaded from: classes4.dex */
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
        context.getPackageManager().semGetDrawableForIconTray(icon, 1),
        key);
  }
}
