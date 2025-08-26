package com.android.systemui.qs.panels.ui.compose.selection;

import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;

/* loaded from: classes2.dex */
public final class SelectionDefaults {
    public static final long BadgeIconSize;
    public static final float BadgeSize;
    public static final float BadgeXOffset;
    public static final float BadgeYOffset;
    public static final SelectionDefaults INSTANCE = new SelectionDefaults();
    public static final float ResizingPillHeight;
    public static final float ResizingPillWidth;
    public static final float SelectedBorderWidth;

    static {
        Dp.Companion companion = Dp.Companion;
        SelectedBorderWidth = 2;
        BadgeSize = 24;
        BadgeIconSize = TextUnitKt.getSp(16);
        float f = 4;
        BadgeXOffset = -f;
        BadgeYOffset = f;
        ResizingPillWidth = 8;
        ResizingPillHeight = 16;
    }

    private SelectionDefaults() {
    }
}
