package androidx.compose.runtime.snapshots;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SnapshotKt$takeNewSnapshot$1 extends Lambda implements Function1 {
    final /* synthetic */ Function1 $block;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SnapshotKt$takeNewSnapshot$1(Function1 function1) {
        super(1);
        this.$block = function1;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        Snapshot snapshot = (Snapshot) this.$block.mo779invoke((SnapshotIdSet) obj);
        synchronized (SnapshotKt.lock) {
            SnapshotKt.openSnapshots = SnapshotKt.openSnapshots.set(snapshot.getSnapshotId());
            Unit unit = Unit.INSTANCE;
        }
        return snapshot;
    }
}
