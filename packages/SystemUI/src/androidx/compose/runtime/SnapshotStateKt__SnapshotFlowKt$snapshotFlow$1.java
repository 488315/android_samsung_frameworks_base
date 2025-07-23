package androidx.compose.runtime;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SnapshotStateKt__SnapshotFlowKt$snapshotFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function0 $block;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    Object L$5;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SnapshotStateKt__SnapshotFlowKt$snapshotFlow$1(Function0 function0, Continuation continuation) {
        super(2, continuation);
        this.$block = function0;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SnapshotStateKt__SnapshotFlowKt$snapshotFlow$1 snapshotStateKt__SnapshotFlowKt$snapshotFlow$1 = new SnapshotStateKt__SnapshotFlowKt$snapshotFlow$1(this.$block, continuation);
        snapshotStateKt__SnapshotFlowKt$snapshotFlow$1.L$0 = obj;
        return snapshotStateKt__SnapshotFlowKt$snapshotFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SnapshotStateKt__SnapshotFlowKt$snapshotFlow$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00f3 A[Catch: all -> 0x005a, TryCatch #3 {all -> 0x005a, blocks: (B:14:0x00ef, B:16:0x00f3, B:19:0x0100, B:21:0x0117, B:23:0x0125, B:25:0x012f, B:30:0x015e, B:35:0x016d, B:41:0x018a, B:43:0x0193, B:54:0x01bb, B:55:0x01be, B:65:0x0140, B:75:0x014d, B:84:0x0052, B:37:0x017f, B:40:0x0187, B:49:0x01b6, B:50:0x01b9, B:39:0x0183), top: B:83:0x0052, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x01c6 A[LOOP:0: B:15:0x00f1->B:32:0x01c6, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x016b A[EDGE_INSN: B:33:0x016b->B:34:0x016b BREAK  A[LOOP:0: B:15:0x00f1->B:32:0x01c6], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x016d A[Catch: all -> 0x005a, TRY_LEAVE, TryCatch #3 {all -> 0x005a, blocks: (B:14:0x00ef, B:16:0x00f3, B:19:0x0100, B:21:0x0117, B:23:0x0125, B:25:0x012f, B:30:0x015e, B:35:0x016d, B:41:0x018a, B:43:0x0193, B:54:0x01bb, B:55:0x01be, B:65:0x0140, B:75:0x014d, B:84:0x0052, B:37:0x017f, B:40:0x0187, B:49:0x01b6, B:50:0x01b9, B:39:0x0183), top: B:83:0x0052, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0158  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r24) {
        /*
            Method dump skipped, instructions count: 476
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.SnapshotStateKt__SnapshotFlowKt$snapshotFlow$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
