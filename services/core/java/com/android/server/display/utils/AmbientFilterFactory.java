package com.android.server.display.utils;

import android.R;
import android.content.res.Resources;
import android.util.TypedValue;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class AmbientFilterFactory {
    public static AmbientFilter$WeightedMovingAverageAmbientFilter createBrightnessFilter(Resources resources, String str) {
        int integer = resources.getInteger(R.integer.config_esim_bootstrap_data_limit_bytes);
        TypedValue typedValue = new TypedValue();
        resources.getValue(R.dimen.config_minScalingSpan, typedValue, true);
        float f = typedValue.type != 4 ? Float.NaN : typedValue.getFloat();
        if (Float.isNaN(f)) {
            throw new IllegalArgumentException("missing configurations: expected config_displayWhiteBalanceBrightnessFilterIntercept");
        }
        return new AmbientFilter$WeightedMovingAverageAmbientFilter(str, f, integer);
    }
}
