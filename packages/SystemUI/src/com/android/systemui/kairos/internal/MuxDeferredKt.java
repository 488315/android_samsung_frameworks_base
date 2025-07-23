package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.MuxLifecycleState;
import com.android.systemui.kairos.internal.store.MutableMapK;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1;
import kotlin.sequences.TransformingIndexedSequence;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class MuxDeferredKt {
    public static final SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1 asIterableWithIndex(Iterable iterable) {
        return new SequencesKt___SequencesKt$asIterable$$inlined$Iterable$1(new TransformingIndexedSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(iterable), new MuxDeferredKt$$ExternalSyntheticLambda0()));
    }

    public static final MuxLifecycle switchDeferredImpl(Function1 function1, Function1 function12, MutableMapK.Factory factory) {
        return new MuxLifecycle(new MuxLifecycleState.Inactive(new MuxDeferredActivator(null, function1, factory, function12)));
    }
}
