package com.android.systemui.keyguard.ui.composable.section;

import com.android.compose.animation.scene.ElementKey;

/* loaded from: classes2.dex */
public abstract class BottomAreaSectionKt {
    public static final ElementKey EndButtonElementKey = null;
    public static final ElementKey IndicationAreaElementKey;
    public static final ElementKey StartButtonElementKey = null;

    static {
        new ElementKey("StartButton", null, null, false, 14, null);
        new ElementKey("EndButton", null, null, false, 14, null);
        IndicationAreaElementKey = new ElementKey("IndicationArea", null, null, false, 14, null);
    }
}
