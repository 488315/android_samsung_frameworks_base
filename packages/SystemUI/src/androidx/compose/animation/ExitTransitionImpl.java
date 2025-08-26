package androidx.compose.animation;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class ExitTransitionImpl extends ExitTransition {
    public final TransitionData data;

    public ExitTransitionImpl(TransitionData transitionData) {
        super(null);
        this.data = transitionData;
    }

    @Override // androidx.compose.animation.ExitTransition
    public final TransitionData getData$animation() {
        return this.data;
    }
}
