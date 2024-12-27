package com.android.systemui.communal.shared.model;

import com.android.compose.animation.scene.TransitionKey;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
