package com.android.systemui.scene.shared.model;

import com.android.compose.animation.scene.TransitionKey;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TransitionKeys {
    public static final TransitionKey SlightlyFasterShadeCollapse;
    public static final TransitionKeys INSTANCE = new TransitionKeys();
    public static final TransitionKey ToSplitShade = new TransitionKey("GoneToSplitShade", null, 2, null);

    static {
        new TransitionKey("CollapseShadeInstantly", null, 2, null);
        SlightlyFasterShadeCollapse = new TransitionKey("SlightlyFasterShadeCollapse", null, 2, null);
    }

    private TransitionKeys() {
    }
}
