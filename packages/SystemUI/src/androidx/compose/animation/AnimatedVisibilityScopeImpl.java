package androidx.compose.animation;

import androidx.compose.animation.core.Transition;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.unit.IntSize;

/* loaded from: classes.dex */
public final class AnimatedVisibilityScopeImpl implements AnimatedVisibilityScope {
    public final MutableState targetSize;
    public final Transition transition;

    public AnimatedVisibilityScopeImpl(Transition<EnterExitState> transition) {
        this.transition = transition;
        IntSize.Companion.getClass();
        this.targetSize = SnapshotStateKt.mutableStateOf$default(IntSize.m861boximpl(0L));
    }

    @Override // androidx.compose.animation.AnimatedVisibilityScope
    public final Transition getTransition() {
        return this.transition;
    }
}
