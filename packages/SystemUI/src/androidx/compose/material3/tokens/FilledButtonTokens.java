package androidx.compose.material3.tokens;

/* loaded from: classes.dex */
public final class FilledButtonTokens {
    public static final float ContainerElevation;
    public static final ColorSchemeKeyTokens DisabledContainerColor;
    public static final float DisabledContainerElevation;
    public static final float DisabledContainerOpacity;
    public static final ColorSchemeKeyTokens DisabledLabelTextColor;
    public static final float DisabledLabelTextOpacity;
    public static final float FocusedContainerElevation;
    public static final float HoveredContainerElevation;
    public static final ColorSchemeKeyTokens LabelTextColor;
    public static final float PressedContainerElevation;
    public static final FilledButtonTokens INSTANCE = new FilledButtonTokens();
    public static final ColorSchemeKeyTokens ContainerColor = ColorSchemeKeyTokens.Primary;

    static {
        ElevationTokens elevationTokens = ElevationTokens.INSTANCE;
        elevationTokens.getClass();
        float f = ElevationTokens.Level0;
        ContainerElevation = f;
        DisabledContainerColor = ColorSchemeKeyTokens.OnSurface;
        elevationTokens.getClass();
        DisabledContainerElevation = f;
        DisabledContainerOpacity = 0.1f;
        DisabledLabelTextColor = ColorSchemeKeyTokens.OnSurfaceVariant;
        DisabledLabelTextOpacity = 0.38f;
        elevationTokens.getClass();
        FocusedContainerElevation = f;
        ColorSchemeKeyTokens colorSchemeKeyTokens = ColorSchemeKeyTokens.OnPrimary;
        elevationTokens.getClass();
        HoveredContainerElevation = ElevationTokens.Level1;
        LabelTextColor = colorSchemeKeyTokens;
        elevationTokens.getClass();
        PressedContainerElevation = f;
        ColorSchemeKeyTokens colorSchemeKeyTokens2 = ColorSchemeKeyTokens.Background;
    }

    private FilledButtonTokens() {
    }
}
