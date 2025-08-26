package androidx.compose.animation.core;

/* loaded from: classes.dex */
public interface DurationBasedAnimationSpec<T> extends FiniteAnimationSpec<T> {
    @Override // androidx.compose.animation.core.FiniteAnimationSpec, androidx.compose.animation.core.AnimationSpec
    VectorizedDurationBasedAnimationSpec vectorize(TwoWayConverter twoWayConverter);
}
