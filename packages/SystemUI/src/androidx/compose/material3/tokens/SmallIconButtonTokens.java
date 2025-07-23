package androidx.compose.material3.tokens;

import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SmallIconButtonTokens {
    public static final float ContainerHeight;
    public static final ShapeKeyTokens ContainerShapeRound;
    public static final float DefaultLeadingSpace;
    public static final SmallIconButtonTokens INSTANCE = new SmallIconButtonTokens();
    public static final float IconSize;
    public static final float NarrowLeadingSpace;
    public static final float NarrowTrailingSpace;

    static {
        Dp.Companion companion = Dp.Companion;
        ContainerHeight = (float) 40.0d;
        ContainerShapeRound = ShapeKeyTokens.CornerFull;
        ShapeKeyTokens shapeKeyTokens = ShapeKeyTokens.CornerExtraLarge;
        DefaultLeadingSpace = (float) 8.0d;
        IconSize = (float) 24.0d;
        float f = (float) 4.0d;
        NarrowLeadingSpace = f;
        NarrowTrailingSpace = f;
        ShapeKeyTokens shapeKeyTokens2 = ShapeKeyTokens.CornerExtraLarge;
    }

    private SmallIconButtonTokens() {
    }
}
