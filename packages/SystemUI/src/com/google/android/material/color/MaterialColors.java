package com.google.android.material.color;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.resources.MaterialAttributes;

/* loaded from: classes4.dex */
public class MaterialColors {
    private MaterialColors() {
    }

    public static int compositeARGBWithAlpha(int i, int i2) {
        return ColorUtils.setAlphaComponent(i, (Color.alpha(i) * i2) / 255);
    }

    public static int getColor(View view, int i) {
        Context context = view.getContext();
        TypedValue typedValueResolveTypedValueOrThrow = MaterialAttributes.resolveTypedValueOrThrow(view.getContext(), view.getClass().getCanonicalName(), i);
        int i2 = typedValueResolveTypedValueOrThrow.resourceId;
        return i2 != 0 ? context.getColor(i2) : typedValueResolveTypedValueOrThrow.data;
    }

    public static int layer(float f, int i, int i2) {
        return ColorUtils.compositeColors(ColorUtils.setAlphaComponent(i2, Math.round(Color.alpha(i2) * f)), i);
    }

    public static int getColor(Context context, int i, int i2) {
        Integer numValueOf;
        int color;
        TypedValue typedValueResolve = MaterialAttributes.resolve(i, context);
        if (typedValueResolve != null) {
            int i3 = typedValueResolve.resourceId;
            if (i3 != 0) {
                color = context.getColor(i3);
            } else {
                color = typedValueResolve.data;
            }
            numValueOf = Integer.valueOf(color);
        } else {
            numValueOf = null;
        }
        return numValueOf != null ? numValueOf.intValue() : i2;
    }
}
