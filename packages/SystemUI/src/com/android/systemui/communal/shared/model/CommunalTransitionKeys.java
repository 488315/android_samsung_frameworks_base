package com.android.systemui.communal.shared.model;

import com.android.compose.animation.scene.TransitionKey;

public final class CommunalTransitionKeys {
    public static final CommunalTransitionKeys INSTANCE = new CommunalTransitionKeys();
    public static final TransitionKey SimpleFade = new TransitionKey("SimpleFade", null, 2, null);
    public static final TransitionKey ToEditMode = new TransitionKey("ToEditMode", null, 2, null);
    public static final TransitionKey FromEditMode = new TransitionKey("FromEditMode", null, 2, null);

    static {
        new TransitionKey("Immediately", null, 2, null);
    }

    private CommunalTransitionKeys() {
    }
}
