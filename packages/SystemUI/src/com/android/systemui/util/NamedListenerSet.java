package com.android.systemui.util;

import android.os.Trace;
import com.android.systemui.util.NamedListenerSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt__SequenceBuilderKt;

public final class NamedListenerSet<E> implements IListenerSet<E> {
    public static final int $stable = 8;
    private final Function1 getName;
    private final CopyOnWriteArrayList<NamedListener> listeners;

    public final class NamedListener {
        private final Object listener;
        private final String name;

        public NamedListener(Object obj) {
            this.listener = obj;
            this.name = (String) NamedListenerSet.this.getName.invoke(obj);
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            return obj == this || ((obj instanceof NamedListener) && Intrinsics.areEqual(this.listener, ((NamedListener) obj).listener));
        }

        public final Object getListener() {
            return this.listener;
        }

        public final String getName() {
            return this.name;
        }

        public int hashCode() {
            return this.listener.hashCode();
        }
    }

    public NamedListenerSet() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean add(E e) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // com.android.systemui.util.IListenerSet
    public boolean addIfAbsent(E e) {
        return this.listeners.addIfAbsent(new NamedListener(e));
    }

    @Override // java.util.Set, java.util.Collection
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public boolean contains(Object obj) {
        Object obj2;
        if (obj == null) {
            return false;
        }
        Iterator<T> it = this.listeners.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj2 = null;
                break;
            }
            obj2 = it.next();
            if (Intrinsics.areEqual(((NamedListener) obj2).getListener(), obj)) {
                break;
            }
        }
        return obj2 != null;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean containsAll(Collection<? extends Object> collection) {
        int i;
        CopyOnWriteArrayList<NamedListener> copyOnWriteArrayList = this.listeners;
        if ((copyOnWriteArrayList instanceof Collection) && copyOnWriteArrayList.isEmpty()) {
            i = 0;
        } else {
            Iterator<T> it = copyOnWriteArrayList.iterator();
            i = 0;
            while (it.hasNext()) {
                if (collection.contains(((NamedListener) it.next()).getListener()) && (i = i + 1) < 0) {
                    CollectionsKt__CollectionsKt.throwCountOverflow();
                    throw null;
                }
            }
        }
        return i == collection.size();
    }

    public final void forEachNamed(Function2 function2) {
        Iterator<NamedListener> namedIterator = namedIterator();
        while (namedIterator.hasNext()) {
            NamedListener next = namedIterator.next();
            function2.invoke(next.getName(), next.getListener());
        }
    }

    public final void forEachTraced(Function1 function1) {
        Iterator<NamedListener> namedIterator = namedIterator();
        while (namedIterator.hasNext()) {
            NamedListener next = namedIterator.next();
            String name = next.getName();
            Object listener = next.getListener();
            boolean isEnabled = Trace.isEnabled();
            if (isEnabled) {
                com.android.app.tracing.TraceUtilsKt.beginSlice(name);
            }
            try {
                function1.invoke(listener);
                Unit unit = Unit.INSTANCE;
                if (isEnabled) {
                    com.android.app.tracing.TraceUtilsKt.endSlice();
                }
            } catch (Throwable th) {
                if (isEnabled) {
                    com.android.app.tracing.TraceUtilsKt.endSlice();
                }
                throw th;
            }
        }
    }

    public int getSize() {
        return this.listeners.size();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean isEmpty() {
        return this.listeners.isEmpty();
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return SequencesKt__SequenceBuilderKt.iterator(new NamedListenerSet$iterator$1(this, null));
    }

    public final Iterator<NamedListener> namedIterator() {
        return this.listeners.iterator();
    }

    @Override // com.android.systemui.util.IListenerSet, java.util.Set
    public boolean remove(final E e) {
        if (e == null) {
            return false;
        }
        return this.listeners.removeIf(new Predicate() { // from class: com.android.systemui.util.NamedListenerSet$remove$1
            @Override // java.util.function.Predicate
            public final boolean test(NamedListenerSet.NamedListener namedListener) {
                return Intrinsics.areEqual(namedListener.getListener(), e);
            }
        });
    }

    @Override // java.util.Set, java.util.Collection
    public boolean removeAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public boolean retainAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Set, java.util.Collection
    public final /* bridge */ int size() {
        return getSize();
    }

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    public NamedListenerSet(Function1 function1) {
        this.getName = function1;
        this.listeners = new CopyOnWriteArrayList<>();
    }

    @Override // java.util.Set, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        return (T[]) CollectionToArray.toArray(this, tArr);
    }

    public /* synthetic */ NamedListenerSet(Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new Function1() { // from class: com.android.systemui.util.NamedListenerSet.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(Object obj) {
                return obj.getClass().getName();
            }
        } : function1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void forEachTraced(Consumer<E> consumer) {
        Iterator<NamedListener> namedIterator = namedIterator();
        while (namedIterator.hasNext()) {
            NamedListener next = namedIterator.next();
            String name = next.getName();
            Object listener = next.getListener();
            boolean isEnabled = Trace.isEnabled();
            if (isEnabled) {
                com.android.app.tracing.TraceUtilsKt.beginSlice(name);
            }
            try {
                consumer.accept(listener);
                Unit unit = Unit.INSTANCE;
                if (isEnabled) {
                    com.android.app.tracing.TraceUtilsKt.endSlice();
                }
            } catch (Throwable th) {
                if (isEnabled) {
                    com.android.app.tracing.TraceUtilsKt.endSlice();
                }
                throw th;
            }
        }
    }
}
