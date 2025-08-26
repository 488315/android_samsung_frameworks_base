package com.android.systemui.kairos.internal;

import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1;
import kotlin.sequences.TransformingSequence;

/* loaded from: classes2.dex */
public final /* synthetic */ class StateImplKt$$ExternalSyntheticLambda6 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StateImplKt$$ExternalSyntheticLambda6(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return MuxDeferredKt.asIterableWithIndex((Iterable) ((Init) this.f$0).connect((NetworkScope) obj));
            case 1:
                return new SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1(new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1((Iterable) ((Init) this.f$0).connect((EvalScope) obj)), new StateImplKt$$ExternalSyntheticLambda12(1)));
            default:
                return (StateImpl) this.f$0;
        }
    }
}
