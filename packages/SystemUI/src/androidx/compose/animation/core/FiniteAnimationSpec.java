package androidx.compose.animation.core;

/* loaded from: classes.dex */
public interface FiniteAnimationSpec<T> extends AnimationSpec<T> {
    @Override // androidx.compose.animation.core.AnimationSpec
    VectorizedFiniteAnimationSpec vectorize(TwoWayConverter twoWayConverter);
}
