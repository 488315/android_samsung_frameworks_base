package com.android.systemui.statusbar.phone;

import androidx.compose.ui.unit.Dp;

/* loaded from: classes3.dex */
public final class DraggableBottomSheet {
    public static final DraggableBottomSheet INSTANCE = new DraggableBottomSheet();
    public static final float MaxWidth;

    static {
        Dp.Companion companion = Dp.Companion;
        MaxWidth = 640;
    }

    private DraggableBottomSheet() {
    }
}
