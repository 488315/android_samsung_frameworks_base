package androidx.compose.material3.tokens;

import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SnackbarTokens {
    public static final float ContainerElevation;
    public static final ShapeKeyTokens ContainerShape;
    public static final ColorSchemeKeyTokens IconColor;
    public static final float SingleLineContainerHeight;
    public static final ColorSchemeKeyTokens SupportingTextColor;
    public static final TypographyKeyTokens SupportingTextFont;
    public static final float TwoLinesContainerHeight;
    public static final SnackbarTokens INSTANCE = new SnackbarTokens();
    public static final ColorSchemeKeyTokens ActionLabelTextColor = ColorSchemeKeyTokens.InversePrimary;
    public static final TypographyKeyTokens ActionLabelTextFont = TypographyKeyTokens.LabelLarge;
    public static final ColorSchemeKeyTokens ContainerColor = ColorSchemeKeyTokens.InverseSurface;

    static {
        ElevationTokens.INSTANCE.getClass();
        ContainerElevation = ElevationTokens.Level3;
        ContainerShape = ShapeKeyTokens.CornerExtraSmall;
        ColorSchemeKeyTokens colorSchemeKeyTokens = ColorSchemeKeyTokens.InverseOnSurface;
        IconColor = colorSchemeKeyTokens;
        Dp.Companion companion = Dp.Companion;
        SupportingTextColor = colorSchemeKeyTokens;
        SupportingTextFont = TypographyKeyTokens.BodyMedium;
        SingleLineContainerHeight = (float) 48.0d;
        TwoLinesContainerHeight = (float) 68.0d;
    }

    private SnackbarTokens() {
    }
}
