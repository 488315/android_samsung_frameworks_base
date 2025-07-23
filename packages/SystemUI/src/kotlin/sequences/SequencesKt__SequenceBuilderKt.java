package kotlin.sequences;

import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SequencesKt__SequenceBuilderKt {
    public static SequenceBuilderIterator iterator(Function2 function2) {
        SequenceBuilderIterator sequenceBuilderIterator = new SequenceBuilderIterator();
        sequenceBuilderIterator.nextStep = IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted(function2, sequenceBuilderIterator, sequenceBuilderIterator);
        return sequenceBuilderIterator;
    }
}
