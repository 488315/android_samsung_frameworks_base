package androidx.compose.material3.tokens;

import androidx.compose.ui.unit.Dp;

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
