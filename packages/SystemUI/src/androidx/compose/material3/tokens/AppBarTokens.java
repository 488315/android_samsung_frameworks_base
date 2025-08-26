package androidx.compose.material3.tokens;

import androidx.compose.ui.unit.Dp;

/* loaded from: classes.dex */
public final class AppBarTokens {
    public static final ColorSchemeKeyTokens ContainerColor;
    public static final AppBarTokens INSTANCE = new AppBarTokens();
    public static final ColorSchemeKeyTokens LeadingIconColor;
    public static final ColorSchemeKeyTokens OnScrollContainerColor;
    public static final ColorSchemeKeyTokens SubtitleColor;
    public static final ColorSchemeKeyTokens TitleColor;
    public static final ColorSchemeKeyTokens TrailingIconColor;

    static {
        Dp.Companion companion = Dp.Companion;
        ContainerColor = ColorSchemeKeyTokens.Surface;
        ElevationTokens elevationTokens = ElevationTokens.INSTANCE;
        elevationTokens.getClass();
        ShapeKeyTokens shapeKeyTokens = ShapeKeyTokens.CornerExtraLarge;
        ColorSchemeKeyTokens colorSchemeKeyTokens = ColorSchemeKeyTokens.OnSurface;
        LeadingIconColor = colorSchemeKeyTokens;
        OnScrollContainerColor = ColorSchemeKeyTokens.SurfaceContainer;
        elevationTokens.getClass();
        ColorSchemeKeyTokens colorSchemeKeyTokens2 = ColorSchemeKeyTokens.OnSurfaceVariant;
        SubtitleColor = colorSchemeKeyTokens2;
        TitleColor = colorSchemeKeyTokens;
        TrailingIconColor = colorSchemeKeyTokens2;
    }

    private AppBarTokens() {
    }
}
