package com.android.systemui.util;

import android.graphics.Rect;
import android.util.IndentingPrintWriter;
import android.view.View;
import android.view.ViewGroup;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.Grouping;
import kotlin.collections.GroupingKt__GroupingJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;

/* loaded from: classes3.dex */
public final class ConvenienceExtensionsKt {

    /* renamed from: com.android.systemui.util.ConvenienceExtensionsKt$takeUntil$1, reason: invalid class name */
    final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ Function1 $pred;
        final /* synthetic */ Sequence $this_takeUntil;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Sequence sequence, Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$this_takeUntil = sequence;
            this.$pred = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_takeUntil, this.$pred, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0034  */
        /* JADX WARN: Removed duplicated region for block: B:9:0x002e A[PHI: r3 r4
          0x002e: PHI (r3v1 java.util.Iterator) = (r3v0 java.util.Iterator), (r3v2 java.util.Iterator) binds: [B:8:0x001f, B:15:0x0053] A[DONT_GENERATE, DONT_INLINE]
          0x002e: PHI (r4v1 kotlin.sequences.SequenceScope) = (r4v0 kotlin.sequences.SequenceScope), (r4v2 kotlin.sequences.SequenceScope) binds: [B:8:0x001f, B:15:0x0053] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:12:0x0044 -> B:14:0x0047). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            SequenceScope sequenceScope;
            Iterator it;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                sequenceScope = (SequenceScope) this.L$0;
                it = this.$this_takeUntil.iterator();
                if (it.hasNext()) {
                }
                return Unit.INSTANCE;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Object next = this.L$2;
            it = (Iterator) this.L$1;
            sequenceScope = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            if (!((Boolean) this.$pred.mo781invoke(next)).booleanValue()) {
                if (it.hasNext()) {
                    next = it.next();
                    this.L$0 = sequenceScope;
                    this.L$1 = it;
                    this.L$2 = next;
                    this.label = 1;
                    if (sequenceScope.yield(next, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    if (!((Boolean) this.$pred.mo781invoke(next)).booleanValue()) {
                    }
                }
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(SequenceScope sequenceScope, Continuation continuation) {
            return ((AnonymousClass1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    public static final <T> boolean containsExactly(Collection<? extends T> collection, T... tArr) {
        return Intrinsics.areEqual(eachCountMap(collection), eachCountMap(Arrays.asList(tArr)));
    }

    public static final <T> Map<T, Integer> eachCountMap(Collection<? extends T> collection) {
        final Collection<? extends T> collection2 = collection;
        return GroupingKt__GroupingJVMKt.eachCount(new Grouping() { // from class: com.android.systemui.util.ConvenienceExtensionsKt$eachCountMap$$inlined$groupingBy$1
            @Override // kotlin.collections.Grouping
            public Iterator<Object> sourceIterator() {
                return collection2.iterator();
            }

            @Override // kotlin.collections.Grouping
            public Object keyOf(Object obj) {
                return obj;
            }
        });
    }

    public static final Rect getBoundsOnScreen(View view) {
        Rect rect = new Rect();
        view.getBoundsOnScreen(rect);
        return rect;
    }

    public static final Sequence getChildren(ViewGroup viewGroup) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new ConvenienceExtensionsKt$children$1(viewGroup, null));
    }

    public static final void indentIfPossible(PrintWriter printWriter, Function1 function1) {
        boolean z = printWriter instanceof IndentingPrintWriter;
        if (z) {
            ((IndentingPrintWriter) printWriter).increaseIndent();
        }
        function1.mo781invoke(printWriter);
        if (z) {
            ((IndentingPrintWriter) printWriter).decreaseIndent();
        }
    }

    public static final <T> Sequence takeUntil(Sequence sequence, Function1 function1) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new AnonymousClass1(sequence, function1, null));
    }
}
