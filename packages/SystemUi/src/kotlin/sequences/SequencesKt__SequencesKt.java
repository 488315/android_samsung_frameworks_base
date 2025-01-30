package kotlin.sequences;

import java.util.Iterator;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SequencesKt__SequencesKt extends SequencesKt__SequencesJVMKt {
    public static final Sequence asSequence(final Iterator it) {
        Sequence sequence = new Sequence() { // from class: kotlin.sequences.SequencesKt__SequencesKt$asSequence$$inlined$Sequence$1
            @Override // kotlin.sequences.Sequence
            public final Iterator iterator() {
                return it;
            }
        };
        return sequence instanceof ConstrainedOnceSequence ? sequence : new ConstrainedOnceSequence(sequence);
    }

    public static final Sequence generateSequence(final Object obj, Function1 function1) {
        return obj == null ? EmptySequence.INSTANCE : new GeneratorSequence(new Function0() { // from class: kotlin.sequences.SequencesKt__SequencesKt$generateSequence$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return obj;
            }
        }, function1);
    }

    public static final Sequence sequenceOf(Object... objArr) {
        return objArr.length == 0 ? EmptySequence.INSTANCE : ArraysKt___ArraysKt.asSequence(objArr);
    }
}
