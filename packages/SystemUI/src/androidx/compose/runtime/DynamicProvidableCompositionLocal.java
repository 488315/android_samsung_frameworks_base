package androidx.compose.runtime;

import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final class DynamicProvidableCompositionLocal<T> extends ProvidableCompositionLocal<T> {
    public final SnapshotMutationPolicy policy;

    public DynamicProvidableCompositionLocal(SnapshotMutationPolicy<T> snapshotMutationPolicy, Function0 function0) {
        super(function0);
        this.policy = snapshotMutationPolicy;
    }

    public final ProvidedValue defaultProvidedValue$runtime_release(Object obj) {
        return new ProvidedValue(this, obj, obj == null, this.policy, null, null, true);
    }
}
