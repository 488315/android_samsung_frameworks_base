package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.RectangleShapeKt$RectangleShape$1;

/* loaded from: classes2.dex */
public final class ShortcutHelper$Shapes {
    public static final ShortcutHelper$Shapes INSTANCE = new ShortcutHelper$Shapes();
    public static final RectangleShapeKt$RectangleShape$1 singlePaneCategory;
    public static final RoundedCornerShape singlePaneFirstCategory;
    public static final RoundedCornerShape singlePaneLastCategory;
    public static final RoundedCornerShape singlePaneSingleCategory;

    static {
        ShortcutHelper$Dimensions.INSTANCE.getClass();
        float f = ShortcutHelper$Dimensions.SinglePaneCategoryCornerRadius;
        singlePaneFirstCategory = RoundedCornerShapeKt.m189RoundedCornerShapea9UjIt4$default(f, f, 0.0f, 0.0f, 12);
        singlePaneLastCategory = RoundedCornerShapeKt.m189RoundedCornerShapea9UjIt4$default(0.0f, 0.0f, f, f, 3);
        singlePaneSingleCategory = RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(f);
        singlePaneCategory = RectangleShapeKt.RectangleShape;
    }

    private ShortcutHelper$Shapes() {
    }
}
