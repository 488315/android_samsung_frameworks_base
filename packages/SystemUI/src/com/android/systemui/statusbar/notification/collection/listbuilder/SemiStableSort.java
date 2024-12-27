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
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
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
                    i2 += MathKt__MathJVMKt.getSign(comparator.compare(obj, list.get(i)));
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

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface StableOrder {
    }

    public final ArrayList getPreallocatedAdditions() {
        return (ArrayList) this.preallocatedAdditions$delegate.getValue();
    }
}
