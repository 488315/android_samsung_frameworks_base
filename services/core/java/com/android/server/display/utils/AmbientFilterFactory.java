package com.android.server.display.utils;

import android.R;
import android.content.res.Resources;
import android.util.TypedValue;

public abstract class AmbientFilterFactory {
    public static AmbientFilter$WeightedMovingAverageAmbientFilter createBrightnessFilter(
            Resources resources, String str) {
        int integer = resources.getInteger(R.integer.config_esim_bootstrap_data_limit_bytes);
        TypedValue typedValue = new TypedValue();
        resources.getValue(R.dimen.config_minScalingSpan, typedValue, true);
        float f = typedValue.type != 4 ? Float.NaN : typedValue.getFloat();
        if (Float.isNaN(f)) {
            throw new IllegalArgumentException(
                    "missing configurations: expected"
                        + " config_displayWhiteBalanceBrightnessFilterIntercept");
        }
        return new AmbientFilter$WeightedMovingAverageAmbientFilter(str, f, integer);
    }
}
