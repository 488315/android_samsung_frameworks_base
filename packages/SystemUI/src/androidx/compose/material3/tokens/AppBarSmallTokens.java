package androidx.compose.material3.tokens;

import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AppBarSmallTokens {
    public static final float ContainerHeight;
    public static final AppBarSmallTokens INSTANCE = new AppBarSmallTokens();
    public static final TypographyKeyTokens TitleFont;

    static {
        Dp.Companion companion = Dp.Companion;
        ContainerHeight = (float) 64.0d;
        TypographyKeyTokens typographyKeyTokens = TypographyKeyTokens.BodyLarge;
        TitleFont = TypographyKeyTokens.TitleLarge;
    }

    private AppBarSmallTokens() {
    }
}
