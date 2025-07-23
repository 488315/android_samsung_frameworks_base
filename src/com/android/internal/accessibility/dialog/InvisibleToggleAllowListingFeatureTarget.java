package com.android.internal.accessibility.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.android.internal.accessibility.util.AccessibilityUtils;

/* loaded from: classes5.dex */
class InvisibleToggleAllowListingFeatureTarget extends AccessibilityTarget {
    InvisibleToggleAllowListingFeatureTarget(Context context, int i, boolean z, String str, int i2, CharSequence charSequence, Drawable drawable, String str2) {
        super(context, i, 1, z, str, i2, charSequence, AccessibilityUtils.isDefaultTheme(context) ? drawable : context.getPackageManager().semGetDrawableForIconTray(drawable, 1), str2);
    }
}
