package com.android.systemui.scene.shared.model;

import com.android.compose.animation.scene.TransitionKey;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TransitionKeys {
    public static final TransitionKeys INSTANCE = new TransitionKeys();
    public static final TransitionKey ToSplitShade = new TransitionKey("GoneToSplitShade", null, 2, null);
    public static final TransitionKey SlightlyFasterShadeCollapse = new TransitionKey("SlightlyFasterShadeCollapse", null, 2, null);
    public static final TransitionKey Instant = new TransitionKey("Instant", null, 2, null);

    private TransitionKeys() {
    }
}
