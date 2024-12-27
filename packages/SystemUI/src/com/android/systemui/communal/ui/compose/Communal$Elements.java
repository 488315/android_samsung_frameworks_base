package com.android.systemui.communal.ui.compose;

import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.LowestZIndexScenePicker;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class Communal$Elements {
    public static final Communal$Elements INSTANCE = new Communal$Elements();
    public static final ElementKey Scrim = new ElementKey("Scrim", null, LowestZIndexScenePicker.INSTANCE, 2, null);
    public static final ElementKey Grid = new ElementKey("CommunalContent", null, null, 6, null);
    public static final ElementKey LockIcon = new ElementKey("CommunalLockIcon", null, null, 6, null);
    public static final ElementKey IndicationArea = new ElementKey("CommunalIndicationArea", null, null, 6, null);
    public static final ElementKey StatusBar = new ElementKey(PluginLockStar.STATUS_BAR_TYPE, null, null, 6, null);

    private Communal$Elements() {
    }
}
