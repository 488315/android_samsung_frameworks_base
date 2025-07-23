package androidx.compose.animation.core;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
