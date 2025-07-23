package com.android.systemui.shade.ui.composable;

import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.LowestZIndexContentPicker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class Shade$Elements {
    public static final ElementKey SplitShadeStartColumn = null;
    public static final Shade$Elements INSTANCE = new Shade$Elements();
    public static final ElementKey BackgroundScrim = new ElementKey("ShadeBackgroundScrim", null, LowestZIndexContentPicker.INSTANCE, false, 10, null);

    static {
        new ElementKey("SplitShadeStartColumn", null, null, false, 14, null);
    }

    private Shade$Elements() {
    }
}
