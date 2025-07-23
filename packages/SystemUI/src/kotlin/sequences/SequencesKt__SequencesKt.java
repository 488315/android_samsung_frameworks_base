package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SequencesKt__SequencesKt extends SequencesKt__SequencesJVMKt {
    public static ConstrainedOnceSequence asSequence(final Iterator it) {
        return new ConstrainedOnceSequence(new Sequence() { // from class: kotlin.sequences.SequencesKt__SequencesKt$asSequence$$inlined$Sequence$1
            @Override // kotlin.sequences.Sequence
            public final Iterator iterator() {
                return it;
            }
        });
    }

    public static final FlatteningSequence flatten(Sequence sequence) {
        SequencesKt__SequencesKt$$ExternalSyntheticLambda2 sequencesKt__SequencesKt$$ExternalSyntheticLambda2 = new SequencesKt__SequencesKt$$ExternalSyntheticLambda2(0);
        if (!(sequence instanceof TransformingSequence)) {
            return new FlatteningSequence(sequence, new SequencesKt__SequencesKt$$ExternalSyntheticLambda2(1), sequencesKt__SequencesKt$$ExternalSyntheticLambda2);
        }
        TransformingSequence transformingSequence = (TransformingSequence) sequence;
        return new FlatteningSequence(transformingSequence.sequence, transformingSequence.transformer, sequencesKt__SequencesKt$$ExternalSyntheticLambda2);
    }

    public static Sequence generateSequence(final Object obj, Function1 function1) {
        return obj == null ? EmptySequence.INSTANCE : new GeneratorSequence(new Function0() { // from class: kotlin.sequences.SequencesKt__SequencesKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return obj;
            }
        }, function1);
    }
}
