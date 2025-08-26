package com.android.compose.animation.scene;

import com.android.compose.animation.scene.content.state.TransitionState;

/* loaded from: classes.dex */
public final class HighestZIndexContentPicker implements ElementContentPicker {
    public static final HighestZIndexContentPicker INSTANCE = new HighestZIndexContentPicker();

    private HighestZIndexContentPicker() {
    }

    @Override // com.android.compose.animation.scene.ElementContentPicker
    public final ContentKey contentDuringTransition(TransitionState.Transition transition, long j, long j2) {
        return j > j2 ? transition.fromContent : transition.toContent;
    }
}
