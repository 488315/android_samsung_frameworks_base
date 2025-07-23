package androidx.compose.animation.core;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MutableTransitionState<S> extends TransitionState<S> {
    public final MutableState currentState$delegate;
    public final MutableState targetState$delegate;

    public MutableTransitionState(S s) {
        super(null);
        this.currentState$delegate = SnapshotStateKt.mutableStateOf$default(s);
        this.targetState$delegate = SnapshotStateKt.mutableStateOf$default(s);
    }

    @Override // androidx.compose.animation.core.TransitionState
    public final Object getCurrentState() {
        return ((SnapshotMutableStateImpl) this.currentState$delegate).getValue();
    }

    @Override // androidx.compose.animation.core.TransitionState
    public final Object getTargetState() {
        return ((SnapshotMutableStateImpl) this.targetState$delegate).getValue();
    }

    @Override // androidx.compose.animation.core.TransitionState
    public final void setCurrentState$animation_core(Object obj) {
        ((SnapshotMutableStateImpl) this.currentState$delegate).setValue(obj);
    }

    @Override // androidx.compose.animation.core.TransitionState
    public final void transitionRemoved$animation_core() {
    }

    @Override // androidx.compose.animation.core.TransitionState
    public final void transitionConfigured$animation_core(Transition transition) {
    }
}
