package kotlin.sequences;

import com.android.systemui.qs.panels.ui.model.TileGridCellKt$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.coordinator.GroupCountCoordinator$$ExternalSyntheticLambda0;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.text.StringsKt__AppendableKt;

/* loaded from: classes4.dex */
public class SequencesKt___SequencesKt extends SequencesKt___SequencesJvmKt {

    /* renamed from: kotlin.sequences.SequencesKt___SequencesKt$flatMap$1, reason: invalid class name */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1 {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(1, Iterable.class, "iterator", "iterator()Ljava/util/Iterator;", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return ((Iterable) obj).iterator();
        }
    }

    /* renamed from: kotlin.sequences.SequencesKt___SequencesKt$flatMap$2, reason: invalid class name */
    final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1 {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(1, Sequence.class, "iterator", "iterator()Ljava/util/Iterator;", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return ((Sequence) obj).iterator();
        }
    }

    public static int count(Sequence sequence) {
        Iterator it = sequence.iterator();
        int i = 0;
        while (it.hasNext()) {
            it.next();
            i++;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwCountOverflow();
                throw null;
            }
        }
        return i;
    }

    public static FilteringSequence filter(Sequence sequence, Function1 function1) {
        return new FilteringSequence(sequence, true, function1);
    }

    public static FilteringSequence filterNot(Sequence sequence, Function1 function1) {
        return new FilteringSequence(sequence, false, function1);
    }

    public static Object firstOrNull(Sequence sequence) {
        Iterator it = sequence.iterator();
        if (it.hasNext()) {
            return it.next();
        }
        return null;
    }

    public static FlatteningSequence flatMap(Sequence sequence, Function1 function1) {
        return new FlatteningSequence(sequence, function1, AnonymousClass2.INSTANCE);
    }

    public static SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 flatMapIndexedIterable(SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1, TileGridCellKt$$ExternalSyntheticLambda0 tileGridCellKt$$ExternalSyntheticLambda0) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new SequencesKt__SequencesKt$flatMapIndexed$1(sequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1, tileGridCellKt$$ExternalSyntheticLambda0, SequencesKt___SequencesKt$flatMapIndexed$1.INSTANCE, null));
    }

    public static FlatteningSequence flatMapIterable(CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 collectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1, GroupCountCoordinator$$ExternalSyntheticLambda0 groupCountCoordinator$$ExternalSyntheticLambda0) {
        return new FlatteningSequence(collectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1, groupCountCoordinator$$ExternalSyntheticLambda0, AnonymousClass1.INSTANCE);
    }

    public static String joinToString$default(Sequence sequence, CharSequence charSequence, CharSequence charSequence2, int i) throws IOException {
        if ((i & 2) != 0) {
            charSequence2 = "";
        }
        String str = (i & 4) == 0 ? ")" : "";
        StringBuilder sb = new StringBuilder();
        sb.append(charSequence2);
        int i2 = 0;
        for (Object obj : sequence) {
            i2++;
            if (i2 > 1) {
                sb.append(charSequence);
            }
            StringsKt__AppendableKt.appendElement(sb, obj, null);
        }
        sb.append((CharSequence) str);
        return sb.toString();
    }

    public static Object last(Sequence sequence) {
        Iterator it = sequence.iterator();
        if (!it.hasNext()) {
            throw new NoSuchElementException("Sequence is empty.");
        }
        Object next = it.next();
        while (it.hasNext()) {
            next = it.next();
        }
        return next;
    }

    public static FilteringSequence mapNotNull(Sequence sequence, Function1 function1) {
        return filterNot(new TransformingSequence(sequence, function1), new SequencesKt___SequencesKt$$ExternalSyntheticLambda1());
    }

    public static List toList(Sequence sequence) {
        Iterator it = sequence.iterator();
        if (!it.hasNext()) {
            return EmptyList.INSTANCE;
        }
        Object next = it.next();
        if (!it.hasNext()) {
            return Collections.singletonList(next);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(next);
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList;
    }

    public static List toMutableList(Sequence sequence) {
        ArrayList arrayList = new ArrayList();
        Iterator it = sequence.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList;
    }

    public static Set toSet(Sequence sequence) {
        Iterator it = sequence.iterator();
        if (!it.hasNext()) {
            return EmptySet.INSTANCE;
        }
        Object next = it.next();
        if (!it.hasNext()) {
            return Collections.singleton(next);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.add(next);
        while (it.hasNext()) {
            linkedHashSet.add(it.next());
        }
        return linkedHashSet;
    }

    public static MergingSequence zip(Sequence sequence, Sequence sequence2) {
        return new MergingSequence(sequence, sequence2, new SequencesKt___SequencesKt$$ExternalSyntheticLambda0());
    }
}
