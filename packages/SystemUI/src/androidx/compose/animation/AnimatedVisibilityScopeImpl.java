package androidx.compose.animation;

import androidx.compose.animation.core.Transition;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.unit.IntSize;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AnimatedVisibilityScopeImpl implements AnimatedVisibilityScope {
    public final MutableState targetSize;
    public final Transition transition;

    public AnimatedVisibilityScopeImpl(Transition<EnterExitState> transition) {
        this.transition = transition;
        IntSize.Companion.getClass();
        this.targetSize = SnapshotStateKt.mutableStateOf$default(IntSize.m859boximpl(0L));
    }

    @Override // androidx.compose.animation.AnimatedVisibilityScope
    public final Transition getTransition() {
        return this.transition;
    }
}
