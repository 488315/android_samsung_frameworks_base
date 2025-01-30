package com.google.android.material.color;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.resources.MaterialAttributes;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MaterialColors {
    private MaterialColors() {
    }

    public static int compositeARGBWithAlpha(int i, int i2) {
        return ColorUtils.setAlphaComponent(i, (Color.alpha(i) * i2) / 255);
    }

    public static int getColor(View view, int i) {
        Context context = view.getContext();
        TypedValue resolveTypedValueOrThrow = MaterialAttributes.resolveTypedValueOrThrow(view.getContext(), view.getClass().getCanonicalName(), i);
        int i2 = resolveTypedValueOrThrow.resourceId;
        if (i2 == 0) {
            return resolveTypedValueOrThrow.data;
        }
        Object obj = ContextCompat.sLock;
        return context.getColor(i2);
    }

    public static boolean isColorLight(int i) {
        return i != 0 && ColorUtils.calculateLuminance(i) > 0.5d;
    }

    public static int layer(float f, int i, int i2) {
        return ColorUtils.compositeColors(ColorUtils.setAlphaComponent(i2, Math.round(Color.alpha(i2) * f)), i);
    }

    public static int getColor(int i, Context context, int i2) {
        TypedValue resolve = MaterialAttributes.resolve(i, context);
        if (resolve == null) {
            return i2;
        }
        int i3 = resolve.resourceId;
        if (i3 != 0) {
            Object obj = ContextCompat.sLock;
            return context.getColor(i3);
        }
        return resolve.data;
    }
}
