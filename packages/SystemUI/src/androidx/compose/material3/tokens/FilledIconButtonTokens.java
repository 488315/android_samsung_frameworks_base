package androidx.compose.material3.tokens;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FilledIconButtonTokens {
    public static final ColorSchemeKeyTokens Color;
    public static final ColorSchemeKeyTokens DisabledColor;
    public static final ColorSchemeKeyTokens DisabledContainerColor;
    public static final float DisabledContainerOpacity;
    public static final float DisabledOpacity;
    public static final FilledIconButtonTokens INSTANCE = new FilledIconButtonTokens();
    public static final ColorSchemeKeyTokens ContainerColor = ColorSchemeKeyTokens.Primary;

    static {
        ColorSchemeKeyTokens colorSchemeKeyTokens = ColorSchemeKeyTokens.OnSurface;
        DisabledContainerColor = colorSchemeKeyTokens;
        DisabledContainerOpacity = 0.1f;
        DisabledColor = colorSchemeKeyTokens;
        DisabledOpacity = 0.38f;
        Color = ColorSchemeKeyTokens.OnPrimary;
        ColorSchemeKeyTokens colorSchemeKeyTokens2 = ColorSchemeKeyTokens.Background;
        ColorSchemeKeyTokens colorSchemeKeyTokens3 = ColorSchemeKeyTokens.Background;
    }

    private FilledIconButtonTokens() {
    }
}
