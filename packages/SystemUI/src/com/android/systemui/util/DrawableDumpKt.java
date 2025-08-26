package com.android.systemui.util;

import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.Log;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import java.io.IOException;
import java.lang.reflect.Field;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes3.dex */
public final class DrawableDumpKt {
    private static final String TAG = "DrawableDump";

    private static final void appendColorFilter(Appendable appendable, ColorFilter colorFilter) throws IOException {
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

    private static final void appendColors(Appendable appendable, ColorStateList colorStateList) throws IOException {
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

    /* JADX WARN: Removed duplicated region for block: B:66:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0180  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01af  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01c0  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01fd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static final StringBuilder appendDrawable(StringBuilder sb, Drawable drawable) throws NoSuchFieldException, IOException {
        ColorStateList colorStateList;
        Integer num;
        Drawable.ConstantState constantState;
        ColorStateList colorStateList2;
        Drawable.ConstantState constantState2;
        int[] colors;
        BlendMode blendMode;
        if (drawable == null) {
            sb.append("null");
            return sb;
        }
        sb.append("<");
        sb.append(drawable.getClass().getSimpleName());
        Drawable.ConstantState constantState3 = drawable.getConstantState();
        ColorStateList colorStateList3 = null;
        if (constantState3 == null) {
            colorStateList = null;
        } else {
            try {
                Field declaredField = constantState3.getClass().getDeclaredField("mTint");
                declaredField.setAccessible(true);
                colorStateList = (ColorStateList) declaredField.get(constantState3);
            } catch (Exception unused) {
            }
        }
        if (colorStateList != null) {
            sb.append(" tint=");
            appendColors(sb, colorStateList);
            sb.append(" blendMode=");
            Drawable.ConstantState constantState4 = drawable.getConstantState();
            if (constantState4 == null) {
                blendMode = null;
                sb.append(blendMode);
            } else {
                Class<?> cls = constantState4.getClass();
                try {
                    Field declaredField2 = cls.getDeclaredField("mBlendMode");
                    declaredField2.setAccessible(true);
                    blendMode = (BlendMode) declaredField2.get(constantState4);
                } catch (Exception e) {
                    Log.w(TAG, AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Missing ", cls.getSimpleName(), ".mBlendMode: ", Reflection.getOrCreateKotlinClass(BlendMode.class).getSimpleName()), e);
                }
                sb.append(blendMode);
            }
        }
        ColorFilter colorFilter = drawable.getColorFilter();
        if (colorFilter != null) {
            if (drawable instanceof DrawableWrapper) {
                colorFilter = null;
            }
            if (colorFilter != null) {
                sb.append(" colorFilter=");
                appendColorFilter(sb, colorFilter);
            }
        }
        if (drawable instanceof DrawableWrapper) {
            sb.append(" wrapped=");
            appendDrawable(sb, ((DrawableWrapper) drawable).getDrawable());
        } else {
            int i = 0;
            if (drawable instanceof LayerDrawable) {
                if (drawable instanceof RippleDrawable) {
                    Drawable.ConstantState constantState5 = drawable.getConstantState();
                    if (constantState5 != null) {
                        Class<?> cls2 = constantState5.getClass();
                        try {
                            Field declaredField3 = cls2.getDeclaredField("mColor");
                            declaredField3.setAccessible(true);
                            colorStateList3 = (ColorStateList) declaredField3.get(constantState5);
                        } catch (Exception e2) {
                            Log.w(TAG, AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Missing ", cls2.getSimpleName(), ".mColor: ", Reflection.getOrCreateKotlinClass(ColorStateList.class).getSimpleName()), e2);
                        }
                    }
                    if (colorStateList3 != null) {
                        sb.append(" color=");
                        appendColors(sb, colorStateList3);
                    }
                    ColorStateList effectColor = ((RippleDrawable) drawable).getEffectColor();
                    if (effectColor != null) {
                        sb.append(" effectColor=");
                        appendColors(sb, effectColor);
                    }
                }
                sb.append(" layers=[");
                LayerDrawable layerDrawable = (LayerDrawable) drawable;
                int numberOfLayers = layerDrawable.getNumberOfLayers();
                while (i < numberOfLayers) {
                    if (i != 0) {
                        sb.append(", ");
                    }
                    appendDrawable(sb, layerDrawable.getDrawable(i));
                    i++;
                }
                sb.append("]");
            } else if (drawable instanceof GradientDrawable) {
                Drawable.ConstantState constantState6 = drawable.getConstantState();
                if (constantState6 == null) {
                    num = null;
                    if (num != null) {
                        if (num.intValue() == 0) {
                            num = null;
                        }
                        if (num != null) {
                            int iIntValue = num.intValue();
                            sb.append(" shape=");
                            sb.append(iIntValue);
                        }
                    }
                    constantState = drawable.getConstantState();
                    if (constantState != null) {
                        colorStateList2 = null;
                        if (colorStateList2 != null) {
                            sb.append(" solidColors=");
                            appendColors(sb, colorStateList2);
                        }
                        constantState2 = drawable.getConstantState();
                        if (constantState2 != null) {
                            Class<?> cls3 = constantState2.getClass();
                            try {
                                Field declaredField4 = cls3.getDeclaredField("mStrokeColors");
                                declaredField4.setAccessible(true);
                                colorStateList3 = (ColorStateList) declaredField4.get(constantState2);
                            } catch (Exception e3) {
                                Log.w(TAG, AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Missing ", cls3.getSimpleName(), ".mStrokeColors: ", Reflection.getOrCreateKotlinClass(ColorStateList.class).getSimpleName()), e3);
                            }
                        }
                        if (colorStateList3 != null) {
                            sb.append(" strokeColors=");
                            appendColors(sb, colorStateList3);
                        }
                        colors = ((GradientDrawable) drawable).getColors();
                        if (colors != null) {
                            sb.append(" gradientColors=[");
                            int length = colors.length;
                            int i2 = 0;
                            while (i < length) {
                                int i3 = colors[i];
                                int i4 = i2 + 1;
                                if (i2 != 0) {
                                    sb.append(", ");
                                }
                                sb.append(ColorUtilKt.hexColorString(Integer.valueOf(i3)));
                                i++;
                                i2 = i4;
                            }
                            sb.append("]");
                        }
                    } else {
                        Class<?> cls4 = constantState.getClass();
                        try {
                            Field declaredField5 = cls4.getDeclaredField("mSolidColors");
                            declaredField5.setAccessible(true);
                            colorStateList2 = (ColorStateList) declaredField5.get(constantState);
                        } catch (Exception e4) {
                            Log.w(TAG, AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Missing ", cls4.getSimpleName(), ".mSolidColors: ", Reflection.getOrCreateKotlinClass(ColorStateList.class).getSimpleName()), e4);
                        }
                        if (colorStateList2 != null) {
                        }
                        constantState2 = drawable.getConstantState();
                        if (constantState2 != null) {
                        }
                        if (colorStateList3 != null) {
                        }
                        colors = ((GradientDrawable) drawable).getColors();
                        if (colors != null) {
                        }
                    }
                } else {
                    Class<?> cls5 = constantState6.getClass();
                    try {
                        Field declaredField6 = cls5.getDeclaredField("mShape");
                        declaredField6.setAccessible(true);
                        num = (Integer) declaredField6.get(constantState6);
                    } catch (Exception e5) {
                        Log.w(TAG, AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Missing ", cls5.getSimpleName(), ".mShape: ", Reflection.getOrCreateKotlinClass(Integer.class).getSimpleName()), e5);
                    }
                    if (num != null) {
                    }
                    constantState = drawable.getConstantState();
                    if (constantState != null) {
                    }
                }
            }
        }
        sb.append(">");
        return sb;
    }

    public static final String dumpToString(Drawable drawable) {
        return String.valueOf(drawable);
    }

    public static final String getSolidColor(Drawable drawable) {
        return drawable == null ? "null" : "?";
    }

    private static final ColorStateList getSolidColors(Drawable drawable) throws NoSuchFieldException {
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
                Log.w(TAG, AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Missing ", cls.getSimpleName(), ".mSolidColors: ", Reflection.getOrCreateKotlinClass(ColorStateList.class).getSimpleName()), e);
                return null;
            }
        }
        if (!(drawable instanceof LayerDrawable)) {
            if (drawable instanceof DrawableWrapper) {
                return getSolidColors(((DrawableWrapper) drawable).getDrawable());
            }
            return null;
        }
        LayerDrawable layerDrawable = (LayerDrawable) drawable;
        int numberOfLayers = layerDrawable.getNumberOfLayers();
        for (int i = 0; i < numberOfLayers; i++) {
            ColorStateList solidColors = getSolidColors(layerDrawable.getDrawable(i));
            if (solidColors != null) {
                return solidColors;
            }
        }
        return null;
    }

    private static final <T> T getStateField(Drawable drawable, String str, boolean z) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
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

    public static Object getStateField$default(Drawable drawable, String str, boolean z, int i, Object obj) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
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
