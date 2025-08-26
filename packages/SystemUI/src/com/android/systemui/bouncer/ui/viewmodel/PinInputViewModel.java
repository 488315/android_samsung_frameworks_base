package com.android.systemui.bouncer.ui.viewmodel;

import com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0;
import com.android.systemui.bouncer.ui.viewmodel.EntryToken;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.builders.ListBuilder;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class PinInputViewModel {
    public static final Companion Companion = new Companion(null);
    public final List input;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public PinInputViewModel(List<? extends EntryToken> list) {
        Iterable iterable;
        this.input = list;
        if (!(CollectionsKt___CollectionsKt.firstOrNull((List) list) instanceof EntryToken.ClearAll)) {
            throw new IllegalArgumentException("input does not begin with a ClearAll token");
        }
        Iterator<T> it = list.iterator();
        if (it.hasNext()) {
            ArrayList arrayList = new ArrayList();
            Object next = it.next();
            while (it.hasNext()) {
                Object next2 = it.next();
                arrayList.add(new Pair(next, next2));
                next = next2;
            }
            iterable = arrayList;
        } else {
            iterable = EmptyList.INSTANCE;
        }
        Iterable<Pair> iterable2 = iterable;
        if ((iterable2 instanceof Collection) && ((Collection) iterable2).isEmpty()) {
            return;
        }
        for (Pair pair : iterable2) {
            if (ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(((EntryToken) pair.getFirst()).getSequenceNumber()), Integer.valueOf(((EntryToken) pair.getSecond()).getSequenceNumber())) >= 0) {
                throw new IllegalArgumentException("EntryTokens are not sorted by their sequenceNumber");
            }
        }
    }

    public final PinInputViewModel append(int i) {
        return new PinInputViewModel(this.input, new EntryToken.Digit(i, 0, 2, null));
    }

    public final PinInputViewModel clearAll() {
        return CollectionsKt___CollectionsKt.last(this.input) instanceof EntryToken.ClearAll ? this : new PinInputViewModel(this.input, new EntryToken.ClearAll(0, 1, null));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof PinInputViewModel) && Intrinsics.areEqual(this.input, ((PinInputViewModel) obj).input);
    }

    public final List getDigits(EntryToken.ClearAll clearAll) {
        int iIndexOf = this.input.indexOf(clearAll) + 1;
        if (iIndexOf == 0 || iIndexOf == this.input.size()) {
            return EmptyList.INSTANCE;
        }
        List list = this.input;
        List listSubList = list.subList(iIndexOf, list.size());
        ArrayList arrayList = new ArrayList();
        for (Object obj : listSubList) {
            if (!(((EntryToken) obj) instanceof EntryToken.Digit)) {
                break;
            }
            arrayList.add(obj);
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj2 = arrayList.get(i);
            i++;
            arrayList2.add((EntryToken.Digit) ((EntryToken) obj2));
        }
        return arrayList2;
    }

    public final List<EntryToken> getInput() {
        return this.input;
    }

    public final List getPin() {
        List digits = getDigits(mostRecentClearAll());
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(digits, 10));
        Iterator it = digits.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(((EntryToken.Digit) it.next()).input));
        }
        return arrayList;
    }

    public final int hashCode() {
        return this.input.hashCode();
    }

    public final EntryToken.ClearAll mostRecentClearAll() {
        List list = this.input;
        ListIterator listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            Object objPrevious = listIterator.previous();
            if (((EntryToken) objPrevious) instanceof EntryToken.ClearAll) {
                return (EntryToken.ClearAll) objPrevious;
            }
        }
        throw new NoSuchElementException("List contains no element matching the predicate.");
    }

    public final String toString() {
        return LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0.m("PinInputViewModel(input=", this.input, ")");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private PinInputViewModel(List<? extends EntryToken> list, EntryToken entryToken) {
        ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        if (!list.isEmpty() && (CollectionsKt___CollectionsKt.first((List) list) instanceof EntryToken.ClearAll)) {
            int size = list.size() - 1;
            int i = 0;
            int i2 = 0;
            while (true) {
                if (-1 < size) {
                    if ((list.get(size) instanceof EntryToken.ClearAll) && (i2 = i2 + 1) == 2) {
                        i = size;
                        break;
                    }
                    size--;
                } else {
                    break;
                }
            }
            listBuilderCreateListBuilder.addAll(list.subList(i, list.size()));
            listBuilderCreateListBuilder.add(entryToken);
            this(listBuilderCreateListBuilder.build());
            return;
        }
        throw new IllegalArgumentException("Failed requirement.");
    }
}
