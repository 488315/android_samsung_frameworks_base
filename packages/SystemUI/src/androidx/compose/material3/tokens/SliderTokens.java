package androidx.compose.material3.tokens;

import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SliderTokens {
    public static final float ActiveHandleLeadingSpace;
    public static final ColorSchemeKeyTokens ActiveTrackColor;
    public static final ColorSchemeKeyTokens DisabledActiveTrackColor;
    public static final float DisabledActiveTrackOpacity;
    public static final ColorSchemeKeyTokens DisabledHandleColor;
    public static final float DisabledHandleOpacity;
    public static final ColorSchemeKeyTokens DisabledInactiveTrackColor;
    public static final float DisabledInactiveTrackOpacity;
    public static final ColorSchemeKeyTokens HandleColor;
    public static final float HandleHeight;
    public static final ShapeKeyTokens HandleShape;
    public static final float HandleWidth;
    public static final SliderTokens INSTANCE = new SliderTokens();
    public static final ColorSchemeKeyTokens InactiveTrackColor;
    public static final float InactiveTrackHeight;
    public static final float StopIndicatorSize;

    static {
        Dp.Companion companion = Dp.Companion;
        ActiveHandleLeadingSpace = (float) 6.0d;
        ShapeKeyTokens shapeKeyTokens = ShapeKeyTokens.CornerFull;
        float f = (float) 4.0d;
        ColorSchemeKeyTokens colorSchemeKeyTokens = ColorSchemeKeyTokens.Primary;
        ActiveTrackColor = colorSchemeKeyTokens;
        ColorSchemeKeyTokens colorSchemeKeyTokens2 = ColorSchemeKeyTokens.OnSurface;
        DisabledActiveTrackColor = colorSchemeKeyTokens2;
        DisabledActiveTrackOpacity = 0.38f;
        DisabledHandleColor = colorSchemeKeyTokens2;
        DisabledHandleOpacity = 0.38f;
        DisabledInactiveTrackColor = colorSchemeKeyTokens2;
        DisabledInactiveTrackOpacity = 0.12f;
        ColorSchemeKeyTokens colorSchemeKeyTokens3 = ColorSchemeKeyTokens.SecondaryContainer;
        HandleColor = colorSchemeKeyTokens;
        HandleHeight = (float) 44.0d;
        HandleShape = shapeKeyTokens;
        HandleWidth = f;
        InactiveTrackColor = colorSchemeKeyTokens3;
        InactiveTrackHeight = (float) 16.0d;
        ColorSchemeKeyTokens colorSchemeKeyTokens4 = ColorSchemeKeyTokens.Background;
        StopIndicatorSize = f;
        ColorSchemeKeyTokens colorSchemeKeyTokens5 = ColorSchemeKeyTokens.Background;
        TypographyKeyTokens typographyKeyTokens = TypographyKeyTokens.BodyLarge;
    }

    private SliderTokens() {
    }
}
