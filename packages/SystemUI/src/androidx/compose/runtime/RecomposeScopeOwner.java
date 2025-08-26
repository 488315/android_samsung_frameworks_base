package androidx.compose.runtime;

/* loaded from: classes.dex */
public interface RecomposeScopeOwner {
    InvalidationResult invalidate(RecomposeScopeImpl recomposeScopeImpl, Object obj);

    void recomposeScopeReleased();

    void recordReadOf(Object obj);
}
