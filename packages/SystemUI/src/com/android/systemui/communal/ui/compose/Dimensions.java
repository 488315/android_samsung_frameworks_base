package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.ui.unit.Dp;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class Dimensions {
    public static final PaddingValuesImpl ButtonPadding;
    public static final float CardHeightFull;
    public static final float CardHeightHalf;
    public static final float CardHeightThird;
    public static final float CardOutlineWidth;
    public static final float CardWidth;
    public static final float GridHeight;
    public static final float GridTopSpacing;
    public static final Dimensions INSTANCE = new Dimensions();
    public static final float IconSize;
    public static final float ItemSpacing;
    public static final float SlideOffsetY;
    public static final float Spacing;
    public static final float ToolbarPaddingHorizontal;
    public static final float ToolbarPaddingTop;

    static {
        float f = 530;
        Dp.Companion companion = Dp.Companion;
        CardHeightFull = f;
        float f2 = 114;
        GridTopSpacing = f2;
        GridHeight = f2 + f;
        float f3 = 50;
        ItemSpacing = f3;
        float f4 = 2;
        CardHeightHalf = (f - f3) / f4;
        float f5 = 3;
        CardHeightThird = (f - (f4 * f3)) / f5;
        CardWidth = 360;
        CardOutlineWidth = f5;
        Spacing = f3 / f4;
        ToolbarPaddingTop = 27;
        ToolbarPaddingHorizontal = f3;
        float f6 = 24;
        float f7 = 16;
        ButtonPadding = new PaddingValuesImpl(f6, f7, f6, f7, null);
        IconSize = 40;
        SlideOffsetY = 30;
    }

    private Dimensions() {
    }
}
