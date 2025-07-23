package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.persistentOrderedSet;

import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentSet;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMap;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.EndOfChain;
import java.util.Iterator;
import kotlin.collections.AbstractSet;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PersistentOrderedSet<E> extends AbstractSet implements PersistentSet<E> {
    public static final Companion Companion = new Companion(null);
    public static final PersistentOrderedSet EMPTY;
    public final Object firstElement;
    public final PersistentHashMap hashMap;
    public final Object lastElement;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        EndOfChain endOfChain = EndOfChain.INSTANCE;
        PersistentHashMap.Companion.getClass();
        EMPTY = new PersistentOrderedSet(endOfChain, endOfChain, PersistentHashMap.EMPTY);
    }

    public PersistentOrderedSet(Object obj, Object obj2, PersistentHashMap<E, Links> persistentHashMap) {
        this.firstElement = obj;
        this.lastElement = obj2;
        this.hashMap = persistentHashMap;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection, java.util.List
    public final boolean contains(Object obj) {
        return this.hashMap.containsKey(obj);
    }

    @Override // kotlin.collections.AbstractCollection
    public final int getSize() {
        return this.hashMap.getSize();
    }

    @Override // kotlin.collections.AbstractSet, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return new PersistentOrderedSetIterator(this.firstElement, this.hashMap);
    }
}
