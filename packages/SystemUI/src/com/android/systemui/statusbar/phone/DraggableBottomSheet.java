package com.android.systemui.statusbar.phone;

import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
