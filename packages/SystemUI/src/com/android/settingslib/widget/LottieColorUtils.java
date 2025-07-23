package com.android.settingslib.widget;

import android.content.Context;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.systemui.R;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class LottieColorUtils {
    public static final Map DARK_TO_LIGHT_THEME_COLOR_MAP;
    public static final Map MATERIAL_COLOR_MAP;

    static {
        Integer valueOf = Integer.valueOf(R.color.settingslib_color_red500);
        Integer valueOf2 = Integer.valueOf(R.color.settingslib_color_green500);
        DARK_TO_LIGHT_THEME_COLOR_MAP = Map.ofEntries(Map.entry(".grey200", Integer.valueOf(R.color.settingslib_color_grey800)), Map.entry(".grey600", Integer.valueOf(R.color.settingslib_color_grey400)), Map.entry(".grey800", Integer.valueOf(R.color.settingslib_color_grey300)), Map.entry(".grey900", Integer.valueOf(R.color.settingslib_color_grey50)), Map.entry(".red100", valueOf), Map.entry(".red200", valueOf), Map.entry(".red400", Integer.valueOf(R.color.settingslib_color_red600)), Map.entry(".black", Integer.valueOf(android.R.color.white)), Map.entry(".blue200", Integer.valueOf(R.color.settingslib_color_blue700)), Map.entry(".blue400", Integer.valueOf(R.color.settingslib_color_blue600)), Map.entry(".green100", valueOf2), Map.entry(".green200", valueOf2), Map.entry(".green400", Integer.valueOf(R.color.settingslib_color_green600)), Map.entry(".cream", Integer.valueOf(R.color.settingslib_color_charcoal)));
        MATERIAL_COLOR_MAP = Map.ofEntries(Map.entry(".primary", Integer.valueOf(R.color.settingslib_materialColorPrimary)), Map.entry(".onPrimary", Integer.valueOf(R.color.settingslib_materialColorOnPrimary)), Map.entry(".primaryContainer", Integer.valueOf(R.color.settingslib_materialColorPrimaryContainer)), Map.entry(".onPrimaryContainer", Integer.valueOf(R.color.settingslib_materialColorOnPrimaryContainer)), Map.entry(".primaryInverse", Integer.valueOf(R.color.settingslib_materialColorPrimaryInverse)), Map.entry(".primaryFixed", Integer.valueOf(R.color.settingslib_materialColorPrimaryFixed)), Map.entry(".primaryFixedDim", Integer.valueOf(R.color.settingslib_materialColorPrimaryFixedDim)), Map.entry(".onPrimaryFixed", Integer.valueOf(R.color.settingslib_materialColorOnPrimaryFixed)), Map.entry(".onPrimaryFixedVariant", Integer.valueOf(R.color.settingslib_materialColorOnPrimaryFixedVariant)), Map.entry(".secondary", Integer.valueOf(R.color.settingslib_materialColorSecondary)), Map.entry(".onSecondary", Integer.valueOf(R.color.settingslib_materialColorOnSecondary)), Map.entry(".secondaryContainer", Integer.valueOf(R.color.settingslib_materialColorSecondaryContainer)), Map.entry(".onSecondaryContainer", Integer.valueOf(R.color.settingslib_materialColorOnSecondaryContainer)), Map.entry(".secondaryFixed", Integer.valueOf(R.color.settingslib_materialColorSecondaryFixed)), Map.entry(".secondaryFixedDim", Integer.valueOf(R.color.settingslib_materialColorSecondaryFixedDim)), Map.entry(".onSecondaryFixed", Integer.valueOf(R.color.settingslib_materialColorOnSecondaryFixed)), Map.entry(".onSecondaryFixedVariant", Integer.valueOf(R.color.settingslib_materialColorOnSecondaryFixedVariant)), Map.entry(".tertiary", Integer.valueOf(R.color.settingslib_materialColorTertiary)), Map.entry(".onTertiary", Integer.valueOf(R.color.settingslib_materialColorOnTertiary)), Map.entry(".tertiaryContainer", Integer.valueOf(R.color.settingslib_materialColorTertiaryContainer)), Map.entry(".onTertiaryContainer", Integer.valueOf(R.color.settingslib_materialColorOnTertiaryContainer)), Map.entry(".tertiaryFixed", Integer.valueOf(R.color.settingslib_materialColorTertiaryFixed)), Map.entry(".tertiaryFixedDim", Integer.valueOf(R.color.settingslib_materialColorTertiaryFixedDim)), Map.entry(".onTertiaryFixed", Integer.valueOf(R.color.settingslib_materialColorOnTertiaryFixed)), Map.entry(".onTertiaryFixedVariant", Integer.valueOf(R.color.settingslib_materialColorOnTertiaryFixedVariant)), Map.entry(".error", Integer.valueOf(R.color.settingslib_materialColorError)), Map.entry(".onError", Integer.valueOf(R.color.settingslib_materialColorOnError)), Map.entry(".errorContainer", Integer.valueOf(R.color.settingslib_materialColorErrorContainer)), Map.entry(".onErrorContainer", Integer.valueOf(R.color.settingslib_materialColorOnErrorContainer)), Map.entry(".outline", Integer.valueOf(R.color.settingslib_materialColorOutline)), Map.entry(".outlineVariant", Integer.valueOf(R.color.settingslib_materialColorOutlineVariant)), Map.entry(".background", Integer.valueOf(R.color.settingslib_materialColorBackground)), Map.entry(".onBackground", Integer.valueOf(R.color.settingslib_materialColorOnBackground)), Map.entry(".surface", Integer.valueOf(R.color.settingslib_materialColorSurface)), Map.entry(".onSurface", Integer.valueOf(R.color.settingslib_materialColorOnSurface)), Map.entry(".surfaceVariant", Integer.valueOf(R.color.settingslib_materialColorSurfaceVariant)), Map.entry(".onSurfaceVariant", Integer.valueOf(R.color.settingslib_materialColorOnSurfaceVariant)), Map.entry(".surfaceInverse", Integer.valueOf(R.color.settingslib_materialColorSurfaceInverse)), Map.entry(".onSurfaceInverse", Integer.valueOf(R.color.settingslib_materialColorOnSurfaceInverse)), Map.entry(".surfaceBright", Integer.valueOf(R.color.settingslib_materialColorSurfaceBright)), Map.entry(".surfaceDim", Integer.valueOf(R.color.settingslib_materialColorSurfaceDim)), Map.entry(".surfaceContainer", Integer.valueOf(R.color.settingslib_materialColorSurfaceContainer)), Map.entry(".surfaceContainerLow", Integer.valueOf(R.color.settingslib_materialColorSurfaceContainerLow)), Map.entry(".surfaceContainerLowest", Integer.valueOf(R.color.settingslib_materialColorSurfaceContainerLowest)), Map.entry(".surfaceContainerHigh", Integer.valueOf(R.color.settingslib_materialColorSurfaceContainerHigh)), Map.entry(".surfaceContainerHighest", Integer.valueOf(R.color.settingslib_materialColorSurfaceContainerHighest)));
    }

    private LottieColorUtils() {
    }

    public static void applyDynamicColors(Context context, LottieAnimationView lottieAnimationView) {
        if ((context.getResources().getConfiguration().uiMode & 48) == 32) {
            return;
        }
        for (String str : DARK_TO_LIGHT_THEME_COLOR_MAP.keySet()) {
            lottieAnimationView.addValueCallback(new KeyPath("**", str, "**"), (KeyPath) LottieProperty.COLOR_FILTER, (SimpleLottieValueCallback) new LottieColorUtils$$ExternalSyntheticLambda0(context.getColor(((Integer) DARK_TO_LIGHT_THEME_COLOR_MAP.get(str)).intValue()), 1));
        }
    }
}
