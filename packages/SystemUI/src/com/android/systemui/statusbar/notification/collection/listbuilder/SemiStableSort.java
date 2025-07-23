package com.android.systemui.statusbar.notification.collection.listbuilder;

import com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.ToIntFunction;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SemiStableSort {
    public static final Companion Companion = new Companion(null);
    public final Lazy preallocatedAdditions$delegate;
    public final Lazy preallocatedMapToIndex$delegate;
    public final Lazy preallocatedMapToIndexComparator$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$$ExternalSyntheticLambda3
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            SemiStableSort.Companion companion = SemiStableSort.Companion;
            final SemiStableSort semiStableSort = SemiStableSort.this;
            return Comparator.comparingInt(new ToIntFunction() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$preallocatedMapToIndexComparator$2$1
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    Integer num = (Integer) ((HashMap) SemiStableSort.this.preallocatedMapToIndex$delegate.getValue()).get(obj);
                    if (num != null) {
                        return num.intValue();
                    }
                    return -1;
                }
            });
        }
    });
    public final Lazy preallocatedWorkspace$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final void access$insertPreSortedElementsWithFewestMisOrderings(Companion companion, List list, Iterable iterable, Comparator comparator) {
            companion.getClass();
            int i = 0;
            for (Object obj : iterable) {
                int size = list.size();
                int i2 = 0;
                int i3 = 0;
                int i4 = i;
                while (i < size) {
                    i2 += Integer.signum(comparator.compare(obj, list.get(i)));
                    if (i2 > i3) {
                        i4 = i + 1;
                        i3 = i2;
                    }
                    i++;
                }
                list.add(i4, obj);
                i = i4 + 1;
            }
        }

        public final <T> boolean isSorted(List<? extends T> list, Comparator<T> comparator) {
            if (list.size() <= 1) {
                return true;
            }
            Iterator<? extends T> it = list.iterator();
            T next = it.next();
            while (it.hasNext()) {
                T next2 = it.next();
                if (comparator.compare(next, next2) > 0) {
                    return false;
                }
                next = next2;
            }
            return true;
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface StableOrder {
    }

    public SemiStableSort() {
        final int i = 0;
        this.preallocatedWorkspace$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        SemiStableSort.Companion companion = SemiStableSort.Companion;
                        return new ArrayList();
                    case 1:
                        SemiStableSort.Companion companion2 = SemiStableSort.Companion;
                        return new ArrayList();
                    default:
                        SemiStableSort.Companion companion3 = SemiStableSort.Companion;
                        return new HashMap();
                }
            }
        });
        final int i2 = 1;
        this.preallocatedAdditions$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        SemiStableSort.Companion companion = SemiStableSort.Companion;
                        return new ArrayList();
                    case 1:
                        SemiStableSort.Companion companion2 = SemiStableSort.Companion;
                        return new ArrayList();
                    default:
                        SemiStableSort.Companion companion3 = SemiStableSort.Companion;
                        return new HashMap();
                }
            }
        });
        final int i3 = 2;
        this.preallocatedMapToIndex$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        SemiStableSort.Companion companion = SemiStableSort.Companion;
                        return new ArrayList();
                    case 1:
                        SemiStableSort.Companion companion2 = SemiStableSort.Companion;
                        return new ArrayList();
                    default:
                        SemiStableSort.Companion companion3 = SemiStableSort.Companion;
                        return new HashMap();
                }
            }
        });
    }

    public final ArrayList getPreallocatedAdditions() {
        return (ArrayList) this.preallocatedAdditions$delegate.getValue();
    }
}
