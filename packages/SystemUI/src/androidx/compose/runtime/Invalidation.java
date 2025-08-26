package androidx.compose.runtime;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class Invalidation {
    public Object instances;
    public final int location;
    public final RecomposeScopeImpl scope;

    public Invalidation(RecomposeScopeImpl recomposeScopeImpl, int i, Object obj) {
        this.scope = recomposeScopeImpl;
        this.location = i;
        this.instances = obj;
    }
}
