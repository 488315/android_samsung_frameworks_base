package kotlin.sequences;

import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.text.StringsKt__AppendableKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SequencesKt___SequencesKt extends SequencesKt___SequencesJvmKt {
    public static final DistinctSequence distinct(FilteringSequence filteringSequence) {
        return new DistinctSequence(filteringSequence, new Function1() { // from class: kotlin.sequences.SequencesKt___SequencesKt$distinct$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return obj;
            }
        });
    }

    public static final FilteringSequence filter(Sequence sequence, Function1 function1) {
        return new FilteringSequence(sequence, true, function1);
    }

    public static final FilteringSequence filterNotNull(Sequence sequence) {
        return new FilteringSequence(sequence, false, new Function1() { // from class: kotlin.sequences.SequencesKt___SequencesKt$filterNotNull$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(obj == null);
            }
        });
    }

    public static final FlatteningSequence flatMap(Sequence sequence, Function1 function1) {
        return new FlatteningSequence(sequence, function1, SequencesKt___SequencesKt$flatMap$2.INSTANCE);
    }

    public static String joinToString$default(Sequence sequence, CharSequence charSequence) {
        StringBuilder sb = new StringBuilder();
        sb.append(charSequence);
        int i = 0;
        for (Object obj : sequence) {
            i++;
            if (i > 1) {
                sb.append((CharSequence) ",");
            }
            StringsKt__AppendableKt.appendElement(sb, obj, null);
        }
        sb.append((CharSequence) ")");
        return sb.toString();
    }

    public static final FilteringSequence mapNotNull(Sequence sequence, Function1 function1) {
        return filterNotNull(new TransformingSequence(sequence, function1));
    }

    public static final FlatteningSequence plus(Sequence sequence, Sequence sequence2) {
        Sequence sequenceOf = SequencesKt__SequencesKt.sequenceOf(sequence, sequence2);
        SequencesKt__SequencesKt$flatten$1 sequencesKt__SequencesKt$flatten$1 = new Function1() { // from class: kotlin.sequences.SequencesKt__SequencesKt$flatten$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ((Sequence) obj).iterator();
            }
        };
        if (!(sequenceOf instanceof TransformingSequence)) {
            return new FlatteningSequence(sequenceOf, new Function1() { // from class: kotlin.sequences.SequencesKt__SequencesKt$flatten$3
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return obj;
                }
            }, sequencesKt__SequencesKt$flatten$1);
        }
        TransformingSequence transformingSequence = (TransformingSequence) sequenceOf;
        return new FlatteningSequence(transformingSequence.sequence, transformingSequence.transformer, sequencesKt__SequencesKt$flatten$1);
    }

    public static final Sequence take(Sequence sequence, int i) {
        if (i >= 0) {
            return i == 0 ? EmptySequence.INSTANCE : sequence instanceof DropTakeSequence ? ((DropTakeSequence) sequence).take(i) : new TakeSequence(sequence, i);
        }
        throw new IllegalArgumentException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("Requested element count ", i, " is less than zero.").toString());
    }

    public static final List toList(Sequence sequence) {
        return CollectionsKt__CollectionsKt.optimizeReadOnlyList(toMutableList(sequence));
    }

    public static final List toMutableList(Sequence sequence) {
        ArrayList arrayList = new ArrayList();
        Iterator it = sequence.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList;
    }

    public static final MergingSequence zip(Sequence sequence, Sequence sequence2) {
        return new MergingSequence(sequence, sequence2, new Function2() { // from class: kotlin.sequences.SequencesKt___SequencesKt$zip$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return new Pair(obj, obj2);
            }
        });
    }
}
