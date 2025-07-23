package com.google.android.material.color;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.resources.MaterialAttributes;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MaterialColors {
    private MaterialColors() {
    }

    public static int compositeARGBWithAlpha(int i, int i2) {
        return ColorUtils.setAlphaComponent(i, (Color.alpha(i) * i2) / 255);
    }

    public static int getColor(View view, int i) {
        Context context = view.getContext();
        TypedValue resolveTypedValueOrThrow = MaterialAttributes.resolveTypedValueOrThrow(view.getContext(), view.getClass().getCanonicalName(), i);
        int i2 = resolveTypedValueOrThrow.resourceId;
        return i2 != 0 ? context.getColor(i2) : resolveTypedValueOrThrow.data;
    }

    public static int layer(float f, int i, int i2) {
        return ColorUtils.compositeColors(ColorUtils.setAlphaComponent(i2, Math.round(Color.alpha(i2) * f)), i);
    }

    public static int getColor(Context context, int i, int i2) {
        Integer num;
        int i3;
        TypedValue resolve = MaterialAttributes.resolve(i, context);
        if (resolve != null) {
            int i4 = resolve.resourceId;
            if (i4 != 0) {
                i3 = context.getColor(i4);
            } else {
                i3 = resolve.data;
            }
            num = Integer.valueOf(i3);
        } else {
            num = null;
        }
        return num != null ? num.intValue() : i2;
    }
}
