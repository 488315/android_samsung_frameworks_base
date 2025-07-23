package com.google.common.collect;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.google.common.collect.ImmutableList;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ImmutableSet<E> extends ImmutableCollection<E> implements Set<E> {
    public static final /* synthetic */ int $r8$clinit = 0;
    private static final long serialVersionUID = 912559;
    public transient ImmutableList asList;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        final Object[] elements;

        public SerializedForm(Object[] objArr) {
            this.elements = objArr;
        }

        public Object readResolve() {
            Object[] objArr = this.elements;
            int i = ImmutableSet.$r8$clinit;
            int length = objArr.length;
            return length != 0 ? length != 1 ? ImmutableSet.construct(objArr.length, (Object[]) objArr.clone()) : new SingletonImmutableSet(objArr[0]) : RegularImmutableSet.EMPTY;
        }
    }

    public static int chooseTableSize(int i) {
        int max = Math.max(i, 2);
        if (max >= 751619276) {
            if (max < 1073741824) {
                return 1073741824;
            }
            throw new IllegalArgumentException("collection too large");
        }
        int highestOneBit = Integer.highestOneBit(max - 1) << 1;
        while (highestOneBit * 0.7d < max) {
            highestOneBit <<= 1;
        }
        return highestOneBit;
    }

    public static ImmutableSet construct(int i, Object... objArr) {
        if (i == 0) {
            return RegularImmutableSet.EMPTY;
        }
        if (i == 1) {
            Object obj = objArr[0];
            Objects.requireNonNull(obj);
            return new SingletonImmutableSet(obj);
        }
        int chooseTableSize = chooseTableSize(i);
        Object[] objArr2 = new Object[chooseTableSize];
        int i2 = chooseTableSize - 1;
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < i; i5++) {
            Object obj2 = objArr[i5];
            if (obj2 == null) {
                throw new NullPointerException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i5, "at index "));
            }
            int hashCode = obj2.hashCode();
            int smear = Hashing.smear(hashCode);
            while (true) {
                int i6 = smear & i2;
                Object obj3 = objArr2[i6];
                if (obj3 == null) {
                    objArr[i4] = obj2;
                    objArr2[i6] = obj2;
                    i3 += hashCode;
                    i4++;
                    break;
                }
                if (obj3.equals(obj2)) {
                    break;
                }
                smear++;
            }
        }
        Arrays.fill(objArr, i4, i, (Object) null);
        if (i4 == 1) {
            Object obj4 = objArr[0];
            Objects.requireNonNull(obj4);
            return new SingletonImmutableSet(obj4);
        }
        if (chooseTableSize(i4) < chooseTableSize / 2) {
            return construct(i4, objArr);
        }
        int length = objArr.length;
        if (i4 < (length >> 1) + (length >> 2)) {
            objArr = Arrays.copyOf(objArr, i4);
        }
        return new RegularImmutableSet(objArr, i3, objArr2, i2, i4);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    @Override // com.google.common.collect.ImmutableCollection
    public ImmutableList asList() {
        ImmutableList immutableList = this.asList;
        if (immutableList != null) {
            return immutableList;
        }
        ImmutableList createAsList = createAsList();
        this.asList = createAsList;
        return createAsList;
    }

    public ImmutableList createAsList() {
        Object[] array = toArray(ImmutableCollection.EMPTY_ARRAY);
        ImmutableList.Itr itr = ImmutableList.EMPTY_ITR;
        return ImmutableList.asImmutableList(array.length, array);
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if ((obj instanceof ImmutableSet) && (this instanceof RegularImmutableSet)) {
            ImmutableSet immutableSet = (ImmutableSet) obj;
            immutableSet.getClass();
            if ((immutableSet instanceof RegularImmutableSet) && hashCode() != obj.hashCode()) {
                return false;
            }
        }
        if (this != obj) {
            if (obj instanceof Set) {
                Set set = (Set) obj;
                try {
                    if (size() == set.size()) {
                        if (containsAll(set)) {
                        }
                    }
                } catch (ClassCastException | NullPointerException unused) {
                }
            }
            return false;
        }
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        Iterator<E> it = iterator();
        int i = 0;
        while (it.hasNext()) {
            E next = it.next();
            i = ~(~(i + (next != null ? next.hashCode() : 0)));
        }
        return i;
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public /* bridge */ /* synthetic */ Iterator iterator() {
        return iterator();
    }

    @Override // com.google.common.collect.ImmutableCollection
    public Object writeReplace() {
        return new SerializedForm(toArray(ImmutableCollection.EMPTY_ARRAY));
    }
}
