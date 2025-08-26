package androidx.compose.material3.tokens;

import androidx.compose.ui.unit.Dp;

/* loaded from: classes.dex */
public final class FilledCardTokens {
    public static final float ContainerElevation;
    public static final ShapeKeyTokens ContainerShape;
    public static final ColorSchemeKeyTokens DisabledContainerColor;
    public static final float DisabledContainerElevation;
    public static final float DisabledContainerOpacity;
    public static final float DraggedContainerElevation;
    public static final float FocusContainerElevation;
    public static final float HoverContainerElevation;
    public static final float PressedContainerElevation;
    public static final FilledCardTokens INSTANCE = new FilledCardTokens();
    public static final ColorSchemeKeyTokens ContainerColor = ColorSchemeKeyTokens.SurfaceContainerHighest;

    static {
        ElevationTokens elevationTokens = ElevationTokens.INSTANCE;
        elevationTokens.getClass();
        float f = ElevationTokens.Level0;
        ContainerElevation = f;
        ContainerShape = ShapeKeyTokens.CornerMedium;
        DisabledContainerColor = ColorSchemeKeyTokens.SurfaceVariant;
        elevationTokens.getClass();
        DisabledContainerElevation = f;
        DisabledContainerOpacity = 0.38f;
        elevationTokens.getClass();
        DraggedContainerElevation = ElevationTokens.Level3;
        elevationTokens.getClass();
        FocusContainerElevation = f;
        ColorSchemeKeyTokens colorSchemeKeyTokens = ColorSchemeKeyTokens.Background;
        elevationTokens.getClass();
        HoverContainerElevation = ElevationTokens.Level1;
        ColorSchemeKeyTokens colorSchemeKeyTokens2 = ColorSchemeKeyTokens.Background;
        Dp.Companion companion = Dp.Companion;
        elevationTokens.getClass();
        PressedContainerElevation = f;
    }

    private FilledCardTokens() {
    }
}
