package com.android.systemui.util;

import android.os.Trace;
import com.android.systemui.util.NamedListenerSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt__SequenceBuilderKt;

/* loaded from: classes3.dex */
public final class NamedListenerSet<E> implements IListenerSet<E> {
    public static final int $stable = 8;
    private final Function1 getName;
    private final CopyOnWriteArrayList<NamedListener> listeners;

    public final class NamedListener {
        private final Object listener;
        private final String name;

        public NamedListener(Object obj) {
            this.listener = obj;
            this.name = (String) NamedListenerSet.this.getName.mo781invoke(obj);
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            return (obj instanceof NamedListener) && Intrinsics.areEqual(this.listener, ((NamedListener) obj).listener);
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

    /* renamed from: com.android.systemui.util.NamedListenerSet$iterator$1, reason: invalid class name */
    final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;
        final /* synthetic */ NamedListenerSet<Object> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(NamedListenerSet<Object> namedListenerSet, Continuation continuation) {
            super(2, continuation);
            this.this$0 = namedListenerSet;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Iterator<E> it;
            SequenceScope sequenceScope;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SequenceScope sequenceScope2 = (SequenceScope) this.L$0;
                it = ((NamedListenerSet) this.this$0).listeners.iterator();
                sequenceScope = sequenceScope2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                it = (Iterator) this.L$1;
                sequenceScope = (SequenceScope) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            while (it.hasNext()) {
                Object listener = ((NamedListener) it.next()).getListener();
                this.L$0 = sequenceScope;
                this.L$1 = it;
                this.label = 1;
                if (sequenceScope.yield(listener, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(SequenceScope sequenceScope, Continuation continuation) {
            return ((AnonymousClass1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public NamedListenerSet() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String _init_$lambda$0(Object obj) {
        return obj.getClass().getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean remove$lambda$3(Object obj, NamedListener namedListener) {
        return Intrinsics.areEqual(namedListener.getListener(), obj);
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
        Object next;
        if (obj == null) {
            return false;
        }
        Iterator<T> it = this.listeners.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (Intrinsics.areEqual(((NamedListener) next).getListener(), obj)) {
                break;
            }
        }
        return next != null;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean containsAll(Collection<? extends Object> collection) {
        int i;
        CopyOnWriteArrayList<NamedListener> copyOnWriteArrayList = this.listeners;
        if (copyOnWriteArrayList == null || !copyOnWriteArrayList.isEmpty()) {
            Iterator<T> it = copyOnWriteArrayList.iterator();
            i = 0;
            while (it.hasNext()) {
                if (collection.contains(((NamedListener) it.next()).getListener()) && (i = i + 1) < 0) {
                    CollectionsKt__CollectionsKt.throwCountOverflow();
                    throw null;
                }
            }
        } else {
            i = 0;
        }
        return i == collection.size();
    }

    public final void forEachNamed(Function2 function2) {
        Iterator<NamedListener> itNamedIterator = namedIterator();
        while (itNamedIterator.hasNext()) {
            NamedListener next = itNamedIterator.next();
            function2.invoke(next.getName(), next.getListener());
        }
    }

    public final void forEachTraced(Function1 function1) {
        Iterator<NamedListener> itNamedIterator = namedIterator();
        while (itNamedIterator.hasNext()) {
            NamedListener next = itNamedIterator.next();
            String name = next.getName();
            Object listener = next.getListener();
            boolean zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
                com.android.app.tracing.TraceUtilsKt.beginSlice(name);
            }
            try {
                function1.mo781invoke(listener);
                Unit unit = Unit.INSTANCE;
                if (zIsEnabled) {
                    com.android.app.tracing.TraceUtilsKt.endSlice();
                }
            } catch (Throwable th) {
                if (zIsEnabled) {
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
        return SequencesKt__SequenceBuilderKt.iterator(new AnonymousClass1(this, null));
    }

    public final Iterator<NamedListener> namedIterator() {
        return this.listeners.iterator();
    }

    @Override // com.android.systemui.util.IListenerSet, java.util.Set
    public boolean remove(final E e) {
        if (e == null) {
            return false;
        }
        CopyOnWriteArrayList<NamedListener> copyOnWriteArrayList = this.listeners;
        final Function1 function1 = new Function1() { // from class: com.android.systemui.util.NamedListenerSet$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return Boolean.valueOf(NamedListenerSet.remove$lambda$3(e, (NamedListenerSet.NamedListener) obj));
            }
        };
        return copyOnWriteArrayList.removeIf(new Predicate() { // from class: com.android.systemui.util.NamedListenerSet$sam$java_util_function_Predicate$0
            @Override // java.util.function.Predicate
            public final /* synthetic */ boolean test(Object obj) {
                return ((Boolean) function1.mo781invoke(obj)).booleanValue();
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
        this((i & 1) != 0 ? new NamedListenerSet$$ExternalSyntheticLambda1() : function1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void forEachTraced(Consumer<E> consumer) {
        Iterator<NamedListener> itNamedIterator = namedIterator();
        while (itNamedIterator.hasNext()) {
            NamedListener next = itNamedIterator.next();
            String name = next.getName();
            Object listener = next.getListener();
            boolean zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
                com.android.app.tracing.TraceUtilsKt.beginSlice(name);
            }
            try {
                consumer.accept(listener);
                Unit unit = Unit.INSTANCE;
                if (zIsEnabled) {
                    com.android.app.tracing.TraceUtilsKt.endSlice();
                }
            } catch (Throwable th) {
                if (zIsEnabled) {
                    com.android.app.tracing.TraceUtilsKt.endSlice();
                }
                throw th;
            }
        }
    }
}
