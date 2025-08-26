package com.android.systemui.communal.ui.compose;

import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.LowestZIndexContentPicker;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;

/* loaded from: classes2.dex */
public final class Communal$Elements {
    public static final Communal$Elements INSTANCE = new Communal$Elements();
    public static final ElementKey Scrim = new ElementKey("Scrim", null, LowestZIndexContentPicker.INSTANCE, false, 10, null);
    public static final ElementKey Grid = new ElementKey("CommunalContent", null, null, false, 14, null);
    public static final ElementKey LockIcon = new ElementKey("CommunalLockIcon", null, null, false, 14, null);
    public static final ElementKey IndicationArea = new ElementKey("CommunalIndicationArea", null, null, false, 14, null);
    public static final ElementKey StatusBar = new ElementKey(PluginLockStar.STATUS_BAR_TYPE, null, null, false, 14, null);

    private Communal$Elements() {
    }
}
