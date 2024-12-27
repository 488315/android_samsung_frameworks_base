package com.android.systemui.scene.shared.model;

import com.android.compose.animation.scene.TransitionKey;

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
