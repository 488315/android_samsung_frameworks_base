package androidx.compose.material3.tokens;

/* loaded from: classes.dex */
public final class OutlinedButtonTokens {
    public static final ColorSchemeKeyTokens DisabledLabelTextColor;
    public static final float DisabledLabelTextOpacity;
    public static final ColorSchemeKeyTokens LabelTextColor;
    public static final ColorSchemeKeyTokens OutlineColor;
    public static final OutlinedButtonTokens INSTANCE = new OutlinedButtonTokens();
    public static final float DisabledContainerOpacity = 0.1f;

    static {
        ColorSchemeKeyTokens colorSchemeKeyTokens = ColorSchemeKeyTokens.OnSurfaceVariant;
        DisabledLabelTextColor = colorSchemeKeyTokens;
        DisabledLabelTextOpacity = 0.38f;
        ColorSchemeKeyTokens colorSchemeKeyTokens2 = ColorSchemeKeyTokens.OutlineVariant;
        LabelTextColor = colorSchemeKeyTokens;
        OutlineColor = colorSchemeKeyTokens2;
        ColorSchemeKeyTokens colorSchemeKeyTokens3 = ColorSchemeKeyTokens.Background;
        ColorSchemeKeyTokens colorSchemeKeyTokens4 = ColorSchemeKeyTokens.Background;
        ColorSchemeKeyTokens colorSchemeKeyTokens5 = ColorSchemeKeyTokens.Background;
    }

    private OutlinedButtonTokens() {
    }
}
