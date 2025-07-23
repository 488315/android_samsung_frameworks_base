package com.google.android.material.resources;

import android.content.res.Configuration;
import android.graphics.Typeface;
import androidx.core.math.MathUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class TypefaceUtils {
    private TypefaceUtils() {
    }

    public static Typeface maybeCopyWithFontWeightAdjustment(Configuration configuration, Typeface typeface) {
        int i = configuration.fontWeightAdjustment;
        if (i == Integer.MAX_VALUE || i == 0 || typeface == null) {
            return null;
        }
        return Typeface.create(typeface, MathUtils.clamp(typeface.getWeight() + configuration.fontWeightAdjustment, 1, 1000), typeface.isItalic());
    }
}
