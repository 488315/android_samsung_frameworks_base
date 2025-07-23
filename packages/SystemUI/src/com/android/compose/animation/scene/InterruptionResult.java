package com.android.compose.animation.scene;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class InterruptionResult {
    public final SceneKey animateFrom;
    public final boolean chain;

    public InterruptionResult(SceneKey sceneKey, boolean z) {
        this.animateFrom = sceneKey;
        this.chain = z;
    }

    public /* synthetic */ InterruptionResult(SceneKey sceneKey, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(sceneKey, (i & 2) != 0 ? true : z);
    }
}
