package com.android.systemui.scene.shared.model;

import com.android.compose.animation.scene.TransitionKey;

/* loaded from: classes2.dex */
public final class TransitionKeys {
    public static final TransitionKeys INSTANCE = new TransitionKeys();
    public static final TransitionKey ToSplitShade = new TransitionKey("GoneToSplitShade", null, 2, null);
    public static final TransitionKey SlightlyFasterShadeCollapse = new TransitionKey("SlightlyFasterShadeCollapse", null, 2, null);
    public static final TransitionKey Instant = new TransitionKey("Instant", null, 2, null);

    private TransitionKeys() {
    }
}
