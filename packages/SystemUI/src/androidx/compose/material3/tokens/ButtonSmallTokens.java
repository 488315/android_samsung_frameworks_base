package androidx.compose.material3.tokens;

import androidx.compose.ui.unit.Dp;

/* loaded from: classes.dex */
public final class ButtonSmallTokens {
    public static final float ContainerHeight;
    public static final ShapeKeyTokens ContainerShapeRound;
    public static final ButtonSmallTokens INSTANCE = new ButtonSmallTokens();
    public static final float IconLabelSpace;
    public static final float OutlinedOutlineWidth;

    static {
        Dp.Companion companion = Dp.Companion;
        ContainerHeight = (float) 40.0d;
        ContainerShapeRound = ShapeKeyTokens.CornerFull;
        IconLabelSpace = (float) 8.0d;
        OutlinedOutlineWidth = (float) 1.0d;
    }

    private ButtonSmallTokens() {
    }
}
