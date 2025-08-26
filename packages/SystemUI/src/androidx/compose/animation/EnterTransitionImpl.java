package androidx.compose.animation;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class EnterTransitionImpl extends EnterTransition {
    public final TransitionData data;

    public EnterTransitionImpl(TransitionData transitionData) {
        super(null);
        this.data = transitionData;
    }

    @Override // androidx.compose.animation.EnterTransition
    public final TransitionData getData$animation() {
        return this.data;
    }
}
