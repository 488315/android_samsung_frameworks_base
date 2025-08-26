package com.android.systemui.shade.ui.composable;

import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.LowestZIndexContentPicker;

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
