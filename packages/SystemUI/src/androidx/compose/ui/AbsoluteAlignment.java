package androidx.compose.ui;

import androidx.compose.ui.BiasAbsoluteAlignment;

/* loaded from: classes.dex */
public final class AbsoluteAlignment {
    public static final BiasAbsoluteAlignment.Horizontal Left = null;
    public static final BiasAbsoluteAlignment.Horizontal Right = null;
    public static final AbsoluteAlignment INSTANCE = new AbsoluteAlignment();
    public static final BiasAbsoluteAlignment TopLeft = new BiasAbsoluteAlignment(-1.0f, -1.0f);
    public static final BiasAbsoluteAlignment TopRight = new BiasAbsoluteAlignment(1.0f, -1.0f);

    static {
        new BiasAbsoluteAlignment(-1.0f, 0.0f);
        new BiasAbsoluteAlignment(1.0f, 0.0f);
        new BiasAbsoluteAlignment(-1.0f, 1.0f);
        new BiasAbsoluteAlignment(1.0f, 1.0f);
        new BiasAbsoluteAlignment.Horizontal(-1.0f);
        new BiasAbsoluteAlignment.Horizontal(1.0f);
    }

    private AbsoluteAlignment() {
    }
}
