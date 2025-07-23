package androidx.compose.material3;

import androidx.compose.foundation.shape.CornerSize;
import androidx.compose.foundation.shape.CornerSizeKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.tokens.ShapeTokens;
import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ShapeDefaults {
    public static final CornerSize CornerNone;
    public static final RoundedCornerShape ExtraExtraLarge;
    public static final RoundedCornerShape ExtraLarge;
    public static final RoundedCornerShape ExtraLargeIncreased;
    public static final RoundedCornerShape ExtraSmall;
    public static final ShapeDefaults INSTANCE = new ShapeDefaults();
    public static final RoundedCornerShape Large;
    public static final RoundedCornerShape LargeIncreased;
    public static final RoundedCornerShape Medium;
    public static final RoundedCornerShape Small;

    static {
        ShapeTokens.INSTANCE.getClass();
        ExtraSmall = ShapeTokens.CornerExtraSmall;
        Small = ShapeTokens.CornerSmall;
        Medium = ShapeTokens.CornerMedium;
        Large = ShapeTokens.CornerLarge;
        float f = 20;
        Dp.Companion companion = Dp.Companion;
        LargeIncreased = RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(f);
        ExtraLarge = ShapeTokens.CornerExtraLarge;
        float f2 = 32;
        ExtraLargeIncreased = RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(f2);
        float f3 = 48;
        ExtraExtraLarge = RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(f3);
        CornerNone = CornerSizeKt.m185CornerSize0680j_4(0);
        CornerSizeKt.m185CornerSize0680j_4(4);
        CornerSizeKt.m185CornerSize0680j_4(8);
        CornerSizeKt.m185CornerSize0680j_4(12);
        CornerSizeKt.m185CornerSize0680j_4(16);
        CornerSizeKt.m185CornerSize0680j_4(f);
        CornerSizeKt.m185CornerSize0680j_4(28);
        CornerSizeKt.m185CornerSize0680j_4(f2);
        CornerSizeKt.m185CornerSize0680j_4(f3);
        CornerSizeKt.CornerSize(100);
    }

    private ShapeDefaults() {
    }
}
