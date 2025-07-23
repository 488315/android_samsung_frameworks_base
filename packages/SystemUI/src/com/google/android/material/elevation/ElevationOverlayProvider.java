package com.google.android.material.elevation;

import android.content.Context;
import android.graphics.Color;
import androidx.core.graphics.ColorUtils;
import com.android.systemui.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.resources.MaterialAttributes;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ElevationOverlayProvider {
    public static final int OVERLAY_ACCENT_COLOR_ALPHA = (int) Math.round(5.1000000000000005d);
    public final int colorSurface;
    public final float displayDensity;
    public final int elevationOverlayAccentColor;
    public final int elevationOverlayColor;
    public final boolean elevationOverlayEnabled;

    public ElevationOverlayProvider(Context context) {
        this(MaterialAttributes.resolveBoolean(context, R.attr.elevationOverlayEnabled, false), MaterialColors.getColor(context, R.attr.elevationOverlayColor, 0), MaterialColors.getColor(context, R.attr.elevationOverlayAccentColor, 0), MaterialColors.getColor(context, R.attr.colorSurface, 0), context.getResources().getDisplayMetrics().density);
    }

    public final int compositeOverlayIfNeeded(float f, int i) {
        int i2;
        if (!this.elevationOverlayEnabled || ColorUtils.setAlphaComponent(i, 255) != this.colorSurface) {
            return i;
        }
        float min = (this.displayDensity <= 0.0f || f <= 0.0f) ? 0.0f : Math.min(((((float) Math.log1p(f / r1)) * 4.5f) + 2.0f) / 100.0f, 1.0f);
        int alpha = Color.alpha(i);
        int layer = MaterialColors.layer(min, ColorUtils.setAlphaComponent(i, 255), this.elevationOverlayColor);
        if (min > 0.0f && (i2 = this.elevationOverlayAccentColor) != 0) {
            layer = ColorUtils.compositeColors(ColorUtils.setAlphaComponent(i2, OVERLAY_ACCENT_COLOR_ALPHA), layer);
        }
        return ColorUtils.setAlphaComponent(layer, alpha);
    }

    public ElevationOverlayProvider(boolean z, int i, int i2, int i3, float f) {
        this.elevationOverlayEnabled = z;
        this.elevationOverlayColor = i;
        this.elevationOverlayAccentColor = i2;
        this.colorSurface = i3;
        this.displayDensity = f;
    }
}
