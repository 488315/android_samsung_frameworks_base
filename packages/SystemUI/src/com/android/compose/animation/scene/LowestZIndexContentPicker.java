package com.android.compose.animation.scene;

import com.android.compose.animation.scene.content.state.TransitionState;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LowestZIndexContentPicker implements ElementContentPicker {
    public static final LowestZIndexContentPicker INSTANCE = new LowestZIndexContentPicker();

    private LowestZIndexContentPicker() {
    }

    @Override // com.android.compose.animation.scene.ElementContentPicker
    public final ContentKey contentDuringTransition(TransitionState.Transition transition, long j, long j2) {
        return j < j2 ? transition.fromContent : transition.toContent;
    }
}
