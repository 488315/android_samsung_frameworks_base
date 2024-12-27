package com.android.systemui.bouncer.ui.viewmodel;

import com.android.systemui.bouncer.ui.viewmodel.EntryToken;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class PinInputViewModel {
    public static final Companion Companion = new Companion(null);
    public final List input;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public PinInputViewModel(List<? extends EntryToken> list) {
        Iterable iterable;
        this.input = list;
        if (!(CollectionsKt___CollectionsKt.firstOrNull((List) list) instanceof EntryToken.ClearAll)) {
            throw new IllegalArgumentException("input does not begin with a ClearAll token".toString());
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
                throw new IllegalArgumentException("EntryTokens are not sorted by their sequenceNumber".toString());
            }
        }
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

    public final List<EntryToken> getInput() {
        return this.input;
    }

    public final int hashCode() {
        return this.input.hashCode();
    }

    public final String toString() {
        return "PinInputViewModel(input=" + this.input + ")";
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private PinInputViewModel(java.util.List<? extends com.android.systemui.bouncer.ui.viewmodel.EntryToken> r6, com.android.systemui.bouncer.ui.viewmodel.EntryToken r7) {
        /*
            r5 = this;
            kotlin.collections.builders.ListBuilder r0 = new kotlin.collections.builders.ListBuilder
            r0.<init>()
            r1 = r6
            java.util.Collection r1 = (java.util.Collection) r1
            boolean r1 = r1.isEmpty()
            r1 = r1 ^ 1
            if (r1 == 0) goto L4d
            java.lang.Object r1 = kotlin.collections.CollectionsKt___CollectionsKt.first(r6)
            boolean r1 = r1 instanceof com.android.systemui.bouncer.ui.viewmodel.EntryToken.ClearAll
            if (r1 == 0) goto L4d
            int r1 = r6.size()
            int r1 = r1 + (-1)
            r2 = 0
            r3 = r2
        L20:
            r4 = -1
            if (r4 >= r1) goto L35
            java.lang.Object r4 = r6.get(r1)
            boolean r4 = r4 instanceof com.android.systemui.bouncer.ui.viewmodel.EntryToken.ClearAll
            if (r4 == 0) goto L32
            int r3 = r3 + 1
            r4 = 2
            if (r3 != r4) goto L32
            r2 = r1
            goto L35
        L32:
            int r1 = r1 + (-1)
            goto L20
        L35:
            int r1 = r6.size()
            java.util.List r6 = r6.subList(r2, r1)
            java.util.Collection r6 = (java.util.Collection) r6
            r0.addAll(r6)
            r0.add(r7)
            kotlin.collections.builders.ListBuilder r6 = r0.build()
            r5.<init>(r6)
            return
        L4d:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "Failed requirement."
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.PinInputViewModel.<init>(java.util.List, com.android.systemui.bouncer.ui.viewmodel.EntryToken):void");
    }
}
