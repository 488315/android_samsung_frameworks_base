package com.android.compose.animation.scene;

import androidx.compose.ui.input.pointer.PointerInputChange;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PassthroughSwipeDetector implements SwipeDetector {
    @Override // com.android.compose.animation.scene.SwipeDetector
    public final boolean detectSwipe(PointerInputChange pointerInputChange) {
        return true;
    }
}
