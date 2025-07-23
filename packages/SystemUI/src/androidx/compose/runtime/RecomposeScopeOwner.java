package androidx.compose.runtime;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface RecomposeScopeOwner {
    InvalidationResult invalidate(RecomposeScopeImpl recomposeScopeImpl, Object obj);

    void recomposeScopeReleased();

    void recordReadOf(Object obj);
}
