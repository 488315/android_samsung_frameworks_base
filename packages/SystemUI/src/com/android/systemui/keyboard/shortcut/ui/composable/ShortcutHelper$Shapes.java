package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.RectangleShapeKt$RectangleShape$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ShortcutHelper$Shapes {
    public static final ShortcutHelper$Shapes INSTANCE = new ShortcutHelper$Shapes();
    public static final RectangleShapeKt$RectangleShape$1 singlePaneCategory;
    public static final RoundedCornerShape singlePaneFirstCategory;
    public static final RoundedCornerShape singlePaneLastCategory;

    static {
        ShortcutHelper$Dimensions.INSTANCE.getClass();
        float f = ShortcutHelper$Dimensions.SinglePaneCategoryCornerRadius;
        singlePaneFirstCategory = RoundedCornerShapeKt.m153RoundedCornerShapea9UjIt4$default(f, f, 0.0f, 0.0f, 12);
        singlePaneLastCategory = RoundedCornerShapeKt.m153RoundedCornerShapea9UjIt4$default(0.0f, 0.0f, f, f, 3);
        singlePaneCategory = RectangleShapeKt.RectangleShape;
    }

    private ShortcutHelper$Shapes() {
    }
}
