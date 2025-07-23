package com.android.systemui.qs.panels.shared.model;

import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class TileRowKt$splitInRowsSequence$1 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ int $columns;
    final /* synthetic */ List<SizedTile> $tiles;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public TileRowKt$splitInRowsSequence$1(int i, List<? extends SizedTile> list, Continuation continuation) {
        super(2, continuation);
        this.$columns = i;
        this.$tiles = list;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TileRowKt$splitInRowsSequence$1 tileRowKt$splitInRowsSequence$1 = new TileRowKt$splitInRowsSequence$1(this.$columns, this.$tiles, continuation);
        tileRowKt$splitInRowsSequence$1.L$0 = obj;
        return tileRowKt$splitInRowsSequence$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TileRowKt$splitInRowsSequence$1) create((SequenceScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x005e, code lost:
    
        r9 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r5._tiles);
        r8.L$0 = r6;
        r8.L$1 = r5;
        r8.L$2 = r4;
        r8.L$3 = r1;
        r8.label = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0072, code lost:
    
        if (r6.yield(r9, r8) != r0) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00af, code lost:
    
        if (r6.yield(r9, r8) == r0) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x008c A[SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x0072 -> B:12:0x0075). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L2d
            if (r1 == r3) goto L19
            if (r1 != r2) goto L11
            kotlin.ResultKt.throwOnFailure(r9)
            goto Lb2
        L11:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L19:
            java.lang.Object r1 = r8.L$3
            com.android.systemui.qs.panels.shared.model.SizedTile r1 = (com.android.systemui.qs.panels.shared.model.SizedTile) r1
            java.lang.Object r4 = r8.L$2
            java.util.Iterator r4 = (java.util.Iterator) r4
            java.lang.Object r5 = r8.L$1
            com.android.systemui.qs.panels.shared.model.TileRow r5 = (com.android.systemui.qs.panels.shared.model.TileRow) r5
            java.lang.Object r6 = r8.L$0
            kotlin.sequences.SequenceScope r6 = (kotlin.sequences.SequenceScope) r6
            kotlin.ResultKt.throwOnFailure(r9)
            goto L75
        L2d:
            kotlin.ResultKt.throwOnFailure(r9)
            java.lang.Object r9 = r8.L$0
            kotlin.sequences.SequenceScope r9 = (kotlin.sequences.SequenceScope) r9
            com.android.systemui.qs.panels.shared.model.TileRow r1 = new com.android.systemui.qs.panels.shared.model.TileRow
            int r4 = r8.$columns
            r1.<init>(r4)
            java.util.List<com.android.systemui.qs.panels.shared.model.SizedTile> r4 = r8.$tiles
            java.util.Iterator r4 = r4.iterator()
            r6 = r9
            r5 = r1
        L43:
            boolean r9 = r4.hasNext()
            if (r9 == 0) goto L8c
            java.lang.Object r9 = r4.next()
            r1 = r9
            com.android.systemui.qs.panels.shared.model.SizedTile r1 = (com.android.systemui.qs.panels.shared.model.SizedTile) r1
            int r9 = r1.getWidth()
            int r7 = r8.$columns
            if (r9 > r7) goto L84
            boolean r9 = r5.maybeAddTile(r1)
            if (r9 != 0) goto L43
            java.util.List r9 = r5._tiles
            java.util.List r9 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r9)
            r8.L$0 = r6
            r8.L$1 = r5
            r8.L$2 = r4
            r8.L$3 = r1
            r8.label = r3
            kotlin.coroutines.intrinsics.CoroutineSingletons r9 = r6.yield(r9, r8)
            if (r9 != r0) goto L75
            goto Lb1
        L75:
            java.util.List r9 = r5._tiles
            java.util.ArrayList r9 = (java.util.ArrayList) r9
            r9.clear()
            int r9 = r5.columns
            r5.availableColumns = r9
            r5.maybeAddTile(r1)
            goto L43
        L84:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "Check failed."
            r8.<init>(r9)
            throw r8
        L8c:
            java.util.List r9 = r5._tiles
            java.util.List r9 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r9)
            java.util.Collection r9 = (java.util.Collection) r9
            boolean r9 = r9.isEmpty()
            if (r9 != 0) goto Lb2
            java.util.List r9 = r5._tiles
            java.util.List r9 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r9)
            r1 = 0
            r8.L$0 = r1
            r8.L$1 = r1
            r8.L$2 = r1
            r8.L$3 = r1
            r8.label = r2
            kotlin.coroutines.intrinsics.CoroutineSingletons r8 = r6.yield(r9, r8)
            if (r8 != r0) goto Lb2
        Lb1:
            return r0
        Lb2:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.shared.model.TileRowKt$splitInRowsSequence$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
