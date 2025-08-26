package androidx.compose.runtime.snapshots;

import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;

/* loaded from: classes.dex */
public final /* synthetic */ class Snapshot$Companion$$ExternalSyntheticLambda0 implements ObserverHandle {
    public final /* synthetic */ Lambda f$0;

    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ Snapshot$Companion$$ExternalSyntheticLambda0(Function2 function2) {
        this.f$0 = (Lambda) function2;
    }

    @Override // androidx.compose.runtime.snapshots.ObserverHandle
    public final void dispose() {
        Lambda lambda = this.f$0;
        synchronized (SnapshotKt.lock) {
            SnapshotKt.applyObservers = CollectionsKt___CollectionsKt.minus(SnapshotKt.applyObservers, lambda);
            Unit unit = Unit.INSTANCE;
        }
    }
}
