package androidx.compose.animation.core;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class TransitionState<S> {
    public final MutableState isRunning$delegate;

    public /* synthetic */ TransitionState(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract Object getCurrentState();

    public abstract Object getTargetState();

    public abstract void setCurrentState$animation_core(Object obj);

    public abstract void transitionConfigured$animation_core(Transition transition);

    public abstract void transitionRemoved$animation_core();

    private TransitionState() {
        this.isRunning$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
    }
}
