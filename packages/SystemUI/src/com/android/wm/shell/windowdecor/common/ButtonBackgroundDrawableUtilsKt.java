package com.android.wm.shell.windowdecor.common;

import android.R;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import kotlin.Unit;

/* loaded from: classes3.dex */
public abstract class ButtonBackgroundDrawableUtilsKt {
    public static final Drawable createBackgroundDrawable(int i, int i2, DrawableInsets drawableInsets) {
        float[] fArr = new float[8];
        for (int i3 = 0; i3 < 8; i3++) {
            fArr[i3] = i2;
        }
        return createBackgroundDrawable(i, fArr, drawableInsets);
    }

    public static final Drawable createBackgroundDrawable(int i, float[] fArr, DrawableInsets drawableInsets) {
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.setShape(new RoundRectShape(fArr, null, null));
        shapeDrawable.setTintList(new ColorStateList(new int[][]{new int[]{R.attr.state_hovered}, new int[]{R.attr.state_pressed}}, new int[]{Color.argb(28, Color.red(i), Color.green(i), Color.blue(i)), Color.argb(38, Color.red(i), Color.green(i), Color.blue(i))}));
        Unit unit = Unit.INSTANCE;
        LayerDrawable layerDrawable = new LayerDrawable(new ShapeDrawable[]{shapeDrawable});
        if (layerDrawable.getNumberOfLayers() == 1) {
            layerDrawable.setLayerInset(0, drawableInsets.l, drawableInsets.t, drawableInsets.r, drawableInsets.b);
            return layerDrawable;
        }
        throw new IllegalArgumentException("Must only contain one layer");
    }
}
