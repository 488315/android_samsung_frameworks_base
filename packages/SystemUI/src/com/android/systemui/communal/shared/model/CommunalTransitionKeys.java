package com.android.systemui.communal.shared.model;

import com.android.compose.animation.scene.TransitionKey;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalTransitionKeys {
    public static final CommunalTransitionKeys INSTANCE = new CommunalTransitionKeys();
    public static final TransitionKey SimpleFade = new TransitionKey("SimpleFade", null, 2, null);
    public static final TransitionKey ToEditMode = new TransitionKey("ToEditMode", null, 2, null);
    public static final TransitionKey FromEditMode = new TransitionKey("FromEditMode", null, 2, null);
    public static final TransitionKey Swipe = new TransitionKey("Swipe", null, 2, null);
    public static final TransitionKey SwipeInLandscape = new TransitionKey("SwipeInLandscape", null, 2, null);

    private CommunalTransitionKeys() {
    }
}
