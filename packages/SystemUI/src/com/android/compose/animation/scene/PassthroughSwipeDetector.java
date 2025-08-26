package com.android.compose.animation.scene;

import androidx.compose.ui.input.pointer.PointerInputChange;

/* loaded from: classes.dex */
public final class PassthroughSwipeDetector implements SwipeDetector {
    @Override // com.android.compose.animation.scene.SwipeDetector
    public final boolean detectSwipe(PointerInputChange pointerInputChange) {
        return true;
    }
}
