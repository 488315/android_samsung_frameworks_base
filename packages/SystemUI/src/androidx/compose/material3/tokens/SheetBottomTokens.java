package androidx.compose.material3.tokens;

import androidx.compose.ui.unit.Dp;

/* loaded from: classes.dex */
public final class SheetBottomTokens {
    public static final float DockedDragHandleHeight;
    public static final float DockedDragHandleWidth;
    public static final float DockedModalContainerElevation;
    public static final SheetBottomTokens INSTANCE = new SheetBottomTokens();
    public static final ColorSchemeKeyTokens DockedContainerColor = ColorSchemeKeyTokens.SurfaceContainerLow;
    public static final ShapeKeyTokens DockedContainerShape = ShapeKeyTokens.CornerExtraLargeTop;
    public static final ColorSchemeKeyTokens DockedDragHandleColor = ColorSchemeKeyTokens.OnSurfaceVariant;

    static {
        Dp.Companion companion = Dp.Companion;
        DockedDragHandleHeight = (float) 4.0d;
        DockedDragHandleWidth = (float) 32.0d;
        ShapeKeyTokens shapeKeyTokens = ShapeKeyTokens.CornerExtraLarge;
        ElevationTokens elevationTokens = ElevationTokens.INSTANCE;
        elevationTokens.getClass();
        DockedModalContainerElevation = ElevationTokens.Level1;
        elevationTokens.getClass();
        ColorSchemeKeyTokens colorSchemeKeyTokens = ColorSchemeKeyTokens.Background;
    }

    private SheetBottomTokens() {
    }
}
