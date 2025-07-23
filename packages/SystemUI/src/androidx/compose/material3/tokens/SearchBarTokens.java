package androidx.compose.material3.tokens;

import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SearchBarTokens {
    public static final ColorSchemeKeyTokens ContainerColor;
    public static final float ContainerHeight;
    public static final ShapeKeyTokens ContainerShape;
    public static final SearchBarTokens INSTANCE = new SearchBarTokens();
    public static final ColorSchemeKeyTokens InputTextColor;
    public static final ColorSchemeKeyTokens LeadingIconColor;
    public static final ColorSchemeKeyTokens SupportingTextColor;
    public static final ColorSchemeKeyTokens TrailingIconColor;

    static {
        ShapeKeyTokens shapeKeyTokens = ShapeKeyTokens.CornerFull;
        Dp.Companion companion = Dp.Companion;
        ContainerColor = ColorSchemeKeyTokens.SurfaceContainerHigh;
        ElevationTokens.INSTANCE.getClass();
        ContainerHeight = (float) 56.0d;
        ContainerShape = shapeKeyTokens;
        ColorSchemeKeyTokens colorSchemeKeyTokens = ColorSchemeKeyTokens.Background;
        ColorSchemeKeyTokens colorSchemeKeyTokens2 = ColorSchemeKeyTokens.OnSurfaceVariant;
        ColorSchemeKeyTokens colorSchemeKeyTokens3 = ColorSchemeKeyTokens.OnSurface;
        InputTextColor = colorSchemeKeyTokens3;
        TypographyKeyTokens typographyKeyTokens = TypographyKeyTokens.BodyLarge;
        LeadingIconColor = colorSchemeKeyTokens3;
        SupportingTextColor = colorSchemeKeyTokens2;
        TrailingIconColor = colorSchemeKeyTokens2;
    }

    private SearchBarTokens() {
    }
}
