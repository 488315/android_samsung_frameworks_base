package androidx.compose.material3.tokens;

import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.RectangleShapeKt$RectangleShape$1;
import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ShapeTokens {
    public static final RoundedCornerShape CornerExtraLarge;
    public static final RoundedCornerShape CornerExtraSmall;
    public static final RoundedCornerShape CornerLarge;
    public static final RoundedCornerShape CornerMedium;
    public static final RoundedCornerShape CornerSmall;
    public static final ShapeTokens INSTANCE = new ShapeTokens();

    static {
        float f = (float) 28.0d;
        Dp.Companion companion = Dp.Companion;
        CornerExtraLarge = RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(f);
        float f2 = (float) 0.0d;
        RoundedCornerShapeKt.m187RoundedCornerShapea9UjIt4(f, f, f2, f2);
        float f3 = (float) 4.0d;
        CornerExtraSmall = RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(f3);
        RoundedCornerShapeKt.m187RoundedCornerShapea9UjIt4(f3, f3, f2, f2);
        float f4 = (float) 16.0d;
        CornerLarge = RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(f4);
        RoundedCornerShapeKt.m187RoundedCornerShapea9UjIt4(f2, f4, f4, f2);
        RoundedCornerShapeKt.m187RoundedCornerShapea9UjIt4(f4, f4, f2, f2);
        CornerMedium = RoundedCornerShapeKt.m186RoundedCornerShape0680j_4((float) 12.0d);
        RectangleShapeKt$RectangleShape$1 rectangleShapeKt$RectangleShape$1 = RectangleShapeKt.RectangleShape;
        CornerSmall = RoundedCornerShapeKt.m186RoundedCornerShape0680j_4((float) 8.0d);
    }

    private ShapeTokens() {
    }
}
