package com.google.android.material.theme.overlay;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.view.ContextThemeWrapper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MaterialThemeOverlay {
    public static final int[] ANDROID_THEME_OVERLAY_ATTRS = {R.attr.theme, com.android.systemui.R.attr.theme};
    public static final int[] MATERIAL_THEME_OVERLAY_ATTR = {com.android.systemui.R.attr.materialThemeOverlay};

    private MaterialThemeOverlay() {
    }

    public static Context wrap(Context context, AttributeSet attributeSet, int i, int i2) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, MATERIAL_THEME_OVERLAY_ATTR, i, i2);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
        boolean z = (context instanceof ContextThemeWrapper) && ((ContextThemeWrapper) context).mThemeResource == resourceId;
        if (resourceId == 0 || z) {
            return context;
        }
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, resourceId);
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, ANDROID_THEME_OVERLAY_ATTRS);
        int resourceId2 = obtainStyledAttributes2.getResourceId(0, 0);
        int resourceId3 = obtainStyledAttributes2.getResourceId(1, 0);
        obtainStyledAttributes2.recycle();
        if (resourceId2 == 0) {
            resourceId2 = resourceId3;
        }
        if (resourceId2 != 0) {
            contextThemeWrapper.getTheme().applyStyle(resourceId2, true);
        }
        return contextThemeWrapper;
    }
}
