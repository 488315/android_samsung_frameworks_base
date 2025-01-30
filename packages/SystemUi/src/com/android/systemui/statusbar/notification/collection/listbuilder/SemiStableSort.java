package com.android.systemui.statusbar.notification.collection.listbuilder;

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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SemiStableSort {
    public static final Companion Companion = new Companion(null);
    public final Lazy preallocatedWorkspace$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$preallocatedWorkspace$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new ArrayList();
        }
    });
    public final Lazy preallocatedAdditions$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$preallocatedAdditions$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new ArrayList();
        }
    });
    public final Lazy preallocatedMapToIndex$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$preallocatedMapToIndex$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new HashMap();
        }
    });
    public final Lazy preallocatedMapToIndexComparator$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$preallocatedMapToIndexComparator$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            final SemiStableSort semiStableSort = SemiStableSort.this;
            return Comparator.comparingInt(new ToIntFunction() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$preallocatedMapToIndexComparator$2.1
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    Integer num = (Integer) ((HashMap) SemiStableSort.this.preallocatedMapToIndex$delegate.getValue()).get(obj);
                    if (num == null) {
                        return -1;
                    }
                    return num.intValue();
                }
            });
        }
    });

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

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
                    int compare = comparator.compare(obj, list.get(i));
                    i2 += compare < 0 ? -1 : compare > 0 ? 1 : 0;
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
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface StableOrder {
    }

    public final ArrayList getPreallocatedAdditions() {
        return (ArrayList) this.preallocatedAdditions$delegate.getValue();
    }
}
