package com.android.systemui.qs.panels.shared.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;

/* loaded from: classes2.dex */
public abstract class TileRowKt {

    /* renamed from: com.android.systemui.qs.panels.shared.model.TileRowKt$splitInRowsSequence$1, reason: invalid class name */
    final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ int $columns;
        final /* synthetic */ List<SizedTile> $tiles;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass1(int i, List<? extends SizedTile> list, Continuation continuation) {
            super(2, continuation);
            this.$columns = i;
            this.$tiles = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$columns, this.$tiles, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((SequenceScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:17:0x005e, code lost:
        
            r9 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r5._tiles);
            r8.L$0 = r6;
            r8.L$1 = r5;
            r8.L$2 = r4;
            r8.L$3 = r1;
            r8.label = 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x0072, code lost:
        
            if (r6.yield(r9, r8) != r0) goto L20;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x00af, code lost:
        
            if (r6.yield(r9, r8) == r0) goto L27;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x00b1, code lost:
        
            return r0;
         */
        /* JADX WARN: Removed duplicated region for block: B:13:0x0049  */
        /* JADX WARN: Removed duplicated region for block: B:31:0x008c A[SYNTHETIC] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x0072 -> B:20:0x0075). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            Iterator<SizedTile> it;
            SequenceScope sequenceScope;
            TileRow tileRow;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SequenceScope sequenceScope2 = (SequenceScope) this.L$0;
                TileRow tileRow2 = new TileRow(this.$columns);
                it = this.$tiles.iterator();
                sequenceScope = sequenceScope2;
                tileRow = tileRow2;
                while (true) {
                    if (!it.hasNext()) {
                    }
                }
            } else {
                if (i != 1) {
                    if (i != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                SizedTile next = (SizedTile) this.L$3;
                it = (Iterator) this.L$2;
                tileRow = (TileRow) this.L$1;
                sequenceScope = (SequenceScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                ((ArrayList) tileRow._tiles).clear();
                tileRow.availableColumns = tileRow.columns;
                tileRow.maybeAddTile(next);
                while (true) {
                    if (!it.hasNext()) {
                        next = it.next();
                        if (next.getWidth() > this.$columns) {
                            throw new IllegalStateException("Check failed.");
                        }
                        if (!tileRow.maybeAddTile(next)) {
                            break;
                        }
                    } else if (!CollectionsKt___CollectionsKt.toList(tileRow._tiles).isEmpty()) {
                        List list = CollectionsKt___CollectionsKt.toList(tileRow._tiles);
                        this.L$0 = null;
                        this.L$1 = null;
                        this.L$2 = null;
                        this.L$3 = null;
                        this.label = 2;
                    }
                }
            }
        }
    }

    public static final SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 splitInRowsSequence(int i, List list) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new AnonymousClass1(i, list, null));
    }
}
