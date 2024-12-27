package com.android.systemui.util;

import android.content.res.ColorStateList;
import android.graphics.BlendModeColorFilter;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import java.lang.reflect.Field;
import kotlin.jvm.internal.Reflection;

public final class DrawableDumpKt {
    private static final String TAG = "DrawableDump";

    private static final void appendColorFilter(Appendable appendable, ColorFilter colorFilter) {
        if (colorFilter == null) {
            appendable.append("null");
            return;
        }
        appendable.append("<");
        appendable.append(colorFilter.getClass().getSimpleName());
        if (colorFilter instanceof PorterDuffColorFilter) {
            appendable.append(" color=");
            PorterDuffColorFilter porterDuffColorFilter = (PorterDuffColorFilter) colorFilter;
            appendable.append(ColorUtilKt.hexColorString(Integer.valueOf(porterDuffColorFilter.getColor())));
            appendable.append(" mode=");
            appendable.append(porterDuffColorFilter.getMode().toString());
        } else if (colorFilter instanceof BlendModeColorFilter) {
            appendable.append(" color=");
            BlendModeColorFilter blendModeColorFilter = (BlendModeColorFilter) colorFilter;
            appendable.append(ColorUtilKt.hexColorString(Integer.valueOf(blendModeColorFilter.getColor())));
            appendable.append(" mode=");
            appendable.append(blendModeColorFilter.getMode().toString());
        } else if (colorFilter instanceof LightingColorFilter) {
            appendable.append(" multiply=");
            LightingColorFilter lightingColorFilter = (LightingColorFilter) colorFilter;
            appendable.append(ColorUtilKt.hexColorString(Integer.valueOf(lightingColorFilter.getColorMultiply())));
            appendable.append(" add=");
            appendable.append(ColorUtilKt.hexColorString(Integer.valueOf(lightingColorFilter.getColorAdd())));
        } else {
            appendable.append(" unhandled");
        }
        appendable.append(">");
    }

    private static final void appendColors(Appendable appendable, ColorStateList colorStateList) {
        if (colorStateList == null) {
            appendable.append("null");
            return;
        }
        int[] colors = colorStateList.getColors();
        if (colors.length == 1) {
            appendable.append(ColorUtilKt.hexColorString(Integer.valueOf(colors[0])));
            return;
        }
        appendable.append("<ColorStateList size=");
        appendable.append(String.valueOf(colors.length));
        appendable.append(" default=");
        appendable.append(ColorUtilKt.hexColorString(Integer.valueOf(colorStateList.getDefaultColor())));
        appendable.append(">");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static final java.lang.StringBuilder appendDrawable(java.lang.StringBuilder r11, android.graphics.drawable.Drawable r12) {
        /*
            Method dump skipped, instructions count: 551
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.DrawableDumpKt.appendDrawable(java.lang.StringBuilder, android.graphics.drawable.Drawable):java.lang.StringBuilder");
    }

    public static final String dumpToString(Drawable drawable) {
        return String.valueOf(drawable);
    }

    public static final String getSolidColor(Drawable drawable) {
        return drawable == null ? "null" : "?";
    }

    private static final ColorStateList getSolidColors(Drawable drawable) {
        if (drawable instanceof GradientDrawable) {
            Drawable.ConstantState constantState = drawable.getConstantState();
            if (constantState == null) {
                return null;
            }
            Class<?> cls = constantState.getClass();
            try {
                Field declaredField = cls.getDeclaredField("mSolidColors");
                declaredField.setAccessible(true);
                return (ColorStateList) declaredField.get(constantState);
            } catch (Exception e) {
                Log.w(TAG, FontProvider$$ExternalSyntheticOutline0.m("Missing ", cls.getSimpleName(), ".mSolidColors: ", Reflection.getOrCreateKotlinClass(ColorStateList.class).getSimpleName()), e);
                return null;
            }
        }
        if (drawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            int numberOfLayers = layerDrawable.getNumberOfLayers();
            for (int i = 0; i < numberOfLayers; i++) {
                ColorStateList solidColors = getSolidColors(layerDrawable.getDrawable(i));
                if (solidColors != null) {
                    return solidColors;
                }
            }
        } else if (drawable instanceof DrawableWrapper) {
            return getSolidColors(((DrawableWrapper) drawable).getDrawable());
        }
        return null;
    }

    private static final <T> T getStateField(Drawable drawable, String str, boolean z) {
        Drawable.ConstantState constantState = drawable.getConstantState();
        if (constantState == null) {
            return null;
        }
        try {
            Field declaredField = constantState.getClass().getDeclaredField(str);
            declaredField.setAccessible(true);
            declaredField.get(constantState);
            throw new UnsupportedOperationException("This function has a reified type parameter and thus can only be inlined at compilation time, not called directly.");
        } catch (Exception unused) {
            if (z) {
                throw new UnsupportedOperationException("This function has a reified type parameter and thus can only be inlined at compilation time, not called directly.");
            }
            return null;
        }
    }

    public static Object getStateField$default(Drawable drawable, String str, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        Drawable.ConstantState constantState = drawable.getConstantState();
        if (constantState == null) {
            return null;
        }
        try {
            Field declaredField = constantState.getClass().getDeclaredField(str);
            declaredField.setAccessible(true);
            declaredField.get(constantState);
            throw new UnsupportedOperationException("This function has a reified type parameter and thus can only be inlined at compilation time, not called directly.");
        } catch (Exception unused) {
            if (z) {
                throw new UnsupportedOperationException("This function has a reified type parameter and thus can only be inlined at compilation time, not called directly.");
            }
            return null;
        }
    }
}
